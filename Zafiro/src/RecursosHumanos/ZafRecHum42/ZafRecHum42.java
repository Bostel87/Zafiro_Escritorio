package RecursosHumanos.ZafRecHum42;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.*;

/**
 * Listado de faltas de empleados (Por mes)....
 * @author  Roberto Flores
 * Guayaquil 23/09/2013
 */
public class ZafRecHum42 extends javax.swing.JInternalFrame
{
    
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_COD_EMP=1;
    private static final int INT_TBL_DAT_NOM_EMP=2;
    private static final int INT_TBL_DAT_COD_TRA=3;
    private static final int INT_TBL_DAT_CED_TRA=4;
    private static final int INT_TBL_DAT_NOM_APE_TRA=5;
    private static final int INT_TBL_DAT_NUM_FAL=6;
    private static final int INT_TBL_DAT_NUM_FAL_REV=7;
    
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;  
    private ZafTblEdi objTblEdi;                                            //Editor: Editor del JTable.
    private ZafThreadGUI objThrGUI;
    
    private ZafMouMotAda objMouMotAda;                                      //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                      //PopupMenu: Establecer PopupMenú en JTable.
    private ZafVenCon vcoEmp;                                               //Ventana de consulta.
    private ZafVenCon vcoOfi;                                               //Ventana de consulta.
    private ZafVenCon vcoTra;
    
    
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;

    private String strCodEmp, strNomEmp;
    private String strCodOfi = "";
    private String strDesLarOfi = "";
    private String strCodTra = "";
    private String strNomTra = "";
    
    private ZafPerUsr objPerUsr;
    private ZafTblBus objTblBus;
    
    private boolean blnCon;                                    //true: Continua la ejecución del hilo.
    
    private String strVersion="v1.04";
    
    private String strCodEmpAut;
    private String strNeAniAut;
    private String strNeMesAut;
    
    private boolean blnEstCarSolHSE=false;
    private boolean blnExiInfoFal=false;
        
    /** Crea una nueva instancia de la clase ZafRecHum42. */
    public ZafRecHum42(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            
            butCon.setVisible(false);
            butCer.setVisible(false);
            
            if(objPerUsr.isOpcionEnabled(3263)){
                butCon.setVisible(true);
            }

            if(objPerUsr.isOpcionEnabled(3264)){
                butCer.setVisible(true);
            }
            
            cargarAños();
            
            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e)
        {
            {objUti.mostrarMsgErr_F1(this, e);}
        }
    }
    
    /** Crea una nueva instancia de la clase ZafRecHum42. */
    public ZafRecHum42(ZafParSis obj, String strCodEmp, String strNeAni, String strNeMes)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            
            butCon.setVisible(false);
            butCer.setVisible(false);

            strCodEmpAut=strCodEmp;
            strNeAniAut=strNeAni;
            strNeMesAut=strNeMes;
            
            blnEstCarSolHSE=true;

            if (!configurarFrm())
                exitForm();
            
            tabFrm.remove(0);
            butCon.setVisible(false);
            butCer.setVisible(false);
            
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }  
            
        }
        catch (CloneNotSupportedException e)
        {
            {objUti.mostrarMsgErr_F1(this, e);}
        }
    }
    
    private boolean cargarAños(){
        boolean blnRes=true;
        java.sql.Connection con = null; 
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null; 
        String strSQL="";

        try{
            con =java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(con!=null){ 
            
                stmLoc=con.createStatement();
            
                strSQL="select distinct ne_ani from tbm_datGenIngEgrMenTra order by ne_ani desc";
                rstLoc=stmLoc.executeQuery(strSQL);
                while(rstLoc.next()){
                    cboPerAAAA.addItem(rstLoc.getString("ne_ani"));
                }
            }
        }catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }finally {
            try{rstLoc.close();}catch(Throwable ignore){}
            try{stmLoc.close();}catch(Throwable ignore){}
            try{con.close();}catch(Throwable ignore){}
        }
        return blnRes;
  } 
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtCodOfi = new javax.swing.JTextField();
        txtNomOfi = new javax.swing.JTextField();
        butOfi = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        panCab = new javax.swing.JPanel();
        cboPerAAAA = new javax.swing.JComboBox();
        cboPerMes = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                exitForm(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        lblTit.setPreferredSize(new java.awt.Dimension(138, 18));
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panFrm.setAutoscrolls(true);
        panFrm.setPreferredSize(new java.awt.Dimension(475, 311));
        panFrm.setLayout(new java.awt.BorderLayout());

        tabFrm.setPreferredSize(new java.awt.Dimension(475, 311));

        panFil.setLayout(null);

        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setSelected(true);
        optTod.setText("Todos los empleados");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 110, 330, 20);

        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Sólo los empleados que cumplan el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(10, 130, 370, 20);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel4.setText("Empresa:"); // NOI18N
        panFil.add(jLabel4);
        jLabel4.setBounds(40, 150, 100, 20);

        txtCodEmp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(140, 150, 60, 20);

        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        panFil.add(txtNomEmp);
        txtNomEmp.setBounds(200, 150, 250, 20);

        butEmp.setText(".."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(450, 150, 20, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Oficina:"); // NOI18N
        panFil.add(jLabel5);
        jLabel5.setBounds(40, 170, 100, 20);

        txtCodOfi.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodOfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodOfiActionPerformed(evt);
            }
        });
        txtCodOfi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodOfiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodOfiFocusLost(evt);
            }
        });
        panFil.add(txtCodOfi);
        txtCodOfi.setBounds(140, 170, 60, 20);

        txtNomOfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomOfiActionPerformed(evt);
            }
        });
        txtNomOfi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomOfiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomOfiFocusLost(evt);
            }
        });
        panFil.add(txtNomOfi);
        txtNomOfi.setBounds(200, 170, 250, 20);

        butOfi.setText(".."); // NOI18N
        butOfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butOfiActionPerformed(evt);
            }
        });
        panFil.add(butOfi);
        butOfi.setBounds(450, 170, 20, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel6.setText("Empleado:"); // NOI18N
        panFil.add(jLabel6);
        jLabel6.setBounds(40, 190, 100, 20);

        txtCodTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTraActionPerformed(evt);
            }
        });
        txtCodTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTraFocusLost(evt);
            }
        });
        panFil.add(txtCodTra);
        txtCodTra.setBounds(140, 190, 60, 20);

        txtNomTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTraActionPerformed(evt);
            }
        });
        txtNomTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTraFocusLost(evt);
            }
        });
        panFil.add(txtNomTra);
        txtNomTra.setBounds(200, 190, 250, 20);

        butTra.setText(".."); // NOI18N
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFil.add(butTra);
        butTra.setBounds(450, 190, 20, 20);

        panCab.setPreferredSize(new java.awt.Dimension(0, 130));
        panCab.setLayout(null);

        cboPerAAAA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Año" }));
        cboPerAAAA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPerAAAAActionPerformed(evt);
            }
        });
        panCab.add(cboPerAAAA);
        cboPerAAAA.setBounds(140, 60, 70, 20);

        cboPerMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mes", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        panCab.add(cboPerMes);
        cboPerMes.setBounds(210, 60, 100, 20);

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel9.setText("Período:"); // NOI18N
        panCab.add(jLabel9);
        jLabel9.setBounds(10, 60, 110, 20);

        panFil.add(panCab);
        panCab.setBounds(0, 0, 690, 100);

        tabFrm.addTab("Filtro", null, panFil, "Filtro");

        panRpt.setLayout(new java.awt.BorderLayout());

        tblDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblDatFocusGained(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(320, 42));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 17));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
       
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }            
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm
   
    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged

   }//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected()) {
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodOfi.setText("");
            txtNomOfi.setText("");
            txtCodTra.setText("");
            txtNomTra.setText("");
        }
        optTod.setSelected(true);
        optFil.setSelected(false);
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optFilItemStateChanged

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected()) {
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodOfi.setText("");
            txtNomOfi.setText("");
            txtCodTra.setText("");
            txtNomTra.setText("");
            
        }
        optTod.setSelected(false);
        optFil.setSelected(true);
    }//GEN-LAST:event_optFilActionPerformed

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        strCodEmp = txtCodEmp.getText();
        txtCodEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp)) {
            if (txtCodEmp.getText().equals("")) {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            } else {
                BuscarEmp("a1.co_emp", txtCodEmp.getText(), 0);
            }
        } else {
            txtCodEmp.setText(strCodEmp);
        }
   }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
   }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
    if (txtNomEmp.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp))
            {
                if (txtNomEmp.getText().equals(""))
                {
                    txtCodEmp.setText("");
                    txtNomEmp.setText("");
                }
                else
                {
                    mostrarVenConEmp(2);
                }
            }
            else
                txtNomEmp.setText(strNomEmp);
        }
   }//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        strCodEmp=txtCodEmp.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtCodOfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodOfiActionPerformed
        txtCodOfi.transferFocus();
    }//GEN-LAST:event_txtCodOfiActionPerformed

    private void txtCodOfiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodOfiFocusGained
        // TODO add your handling code here:
        strCodOfi = txtCodOfi.getText();
        txtCodOfi.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodOfiFocusGained

    private void txtCodOfiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodOfiFocusLost
        // TODO add your handling code here:
        if (!txtCodOfi.getText().equalsIgnoreCase(strCodOfi)) {
            if (txtCodOfi.getText().equals("")) {
                txtCodOfi.setText("");
                txtNomOfi.setText("");
            } else {
                BuscarOfi("a1.co_ofi", txtCodOfi.getText(), 0);
            }
        } else {
            txtCodOfi.setText(strCodOfi);
        }
    }//GEN-LAST:event_txtCodOfiFocusLost

    public void BuscarEmp(String campo,String strBusqueda,int tipo){
        vcoEmp.setTitle("Listado de Empresas");
        if(vcoEmp.buscar(campo, strBusqueda )) {
            txtCodEmp.setText(vcoEmp.getValueAt(1));
            txtNomEmp.setText(vcoEmp.getValueAt(2));
        }else{
            vcoEmp.setCampoBusqueda(tipo);
            vcoEmp.cargarDatos();
            vcoEmp.show();
            if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE) {
                txtCodEmp.setText(vcoEmp.getValueAt(1));
                txtNomEmp.setText(vcoEmp.getValueAt(2));
        }else{
                txtCodEmp.setText(strCodEmp);
                txtNomEmp.setText(strNomEmp);
  }}}
    
    public void BuscarOfi(String campo,String strBusqueda,int tipo){
        
        vcoOfi.setTitle("Listado de Oficinas");
        if(vcoOfi.buscar(campo, strBusqueda )) {
            txtCodOfi.setText(vcoOfi.getValueAt(1));
            txtNomOfi.setText(vcoOfi.getValueAt(2));
        }else{
            vcoOfi.setCampoBusqueda(tipo);
            vcoOfi.cargarDatos();
            vcoOfi.show();
            if (vcoOfi.getSelectedButton()==vcoOfi.INT_BUT_ACE) {
                txtCodOfi.setText(vcoOfi.getValueAt(1));
                txtNomOfi.setText(vcoOfi.getValueAt(2));
        }else{
                txtCodOfi.setText(strCodOfi);
                txtNomOfi.setText(strDesLarOfi);
  }}}
    
    public void BuscarTra(String campo,String strBusqueda,int tipo){
        
        vcoTra.setTitle("Listado de Empleados");
        if(vcoTra.buscar(campo, strBusqueda )) {
            txtCodTra.setText(vcoTra.getValueAt(1));
            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        }else{
            vcoTra.setCampoBusqueda(tipo);
            vcoTra.cargarDatos();
            vcoTra.show();
            if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE) {
                txtCodTra.setText(vcoTra.getValueAt(1));
                txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        }else{
                txtCodTra.setText(strCodTra);
                txtNomTra.setText(strNomTra);
  }}}
    
    private void txtNomOfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomOfiActionPerformed
        // TODO add your handling code here:
        txtNomOfi.transferFocus();
    }//GEN-LAST:event_txtNomOfiActionPerformed

    private void txtNomOfiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomOfiFocusGained
        // TODO add your handling code here:
        strDesLarOfi=txtNomOfi.getText();
        txtNomOfi.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
        
    }//GEN-LAST:event_txtNomOfiFocusGained

    private void txtNomOfiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomOfiFocusLost
        // TODO add your handling code here:
        if (txtNomOfi.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomOfi.getText().equalsIgnoreCase(strDesLarOfi))
            {
                if (txtNomOfi.getText().equals(""))
                {
                    txtCodOfi.setText("");
                    txtNomOfi.setText("");
                }
                else
                {
                    mostrarVenConOfi(2);
                }
            }
            else
                txtNomOfi.setText(strDesLarOfi);
        }
    }//GEN-LAST:event_txtNomOfiFocusLost

    private void butOfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butOfiActionPerformed
        strCodOfi=txtCodOfi.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConOfi(0);
        
    }//GEN-LAST:event_butOfiActionPerformed
    
    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        txtCodTra.transferFocus();
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void txtCodTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusGained
        // TODO add your handling code here:
        strCodTra = txtCodTra.getText();
        txtCodTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodTraFocusGained

    private void txtCodTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusLost
        // TODO add your handling code here:
        if (!txtCodTra.getText().equalsIgnoreCase(strCodTra)) {
            if (txtCodTra.getText().equals("")) {
                txtCodTra.setText("");
                txtNomTra.setText("");
            } else {
                BuscarTra("a1.co_tra", txtCodTra.getText(), 0);
            }
        } else {
            txtCodTra.setText(strCodTra);
        }
    }//GEN-LAST:event_txtCodTraFocusLost

    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        // TODO add your handling code here:
        txtNomTra.transferFocus();
    }//GEN-LAST:event_txtNomTraActionPerformed

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        // TODO add your handling code here:
        strNomTra=txtNomTra.getText();
        txtNomTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusLost
        // TODO add your handling code here:
        if (txtNomTra.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomTra.getText().equalsIgnoreCase(strNomTra))
            {
                if (txtNomTra.getText().equals(""))
                {
                    txtCodTra.setText("");
                    txtNomTra.setText("");
                }
                else
                {
                    mostrarVenConTra(2);
                }
            }
            else
                txtNomTra.setText(strNomTra);
        }
    }//GEN-LAST:event_txtNomTraFocusLost

    private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed

        strCodTra=txtCodTra.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConTra(0);
    }//GEN-LAST:event_butTraActionPerformed

    private void tblDatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblDatFocusGained
        
    }//GEN-LAST:event_tblDatFocusGained

    private void cboPerAAAAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPerAAAAActionPerformed

    }//GEN-LAST:event_cboPerAAAAActionPerformed

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butOfi;
    private javax.swing.JButton butTra;
    private javax.swing.JComboBox cboPerAAAA;
    private javax.swing.JComboBox cboPerMes;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    public javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodOfi;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomOfi;
    private javax.swing.JTextField txtNomTra;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {

            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " " + strVersion);
            lblTit.setText(strAux);
            
            //Configurar las ZafVenCon.
            configurarVenConOfi();
            configurarVenConTra();
            configurarVenConEmp();

            //Configurar los JTables.
            configuraTbl();
            
             //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setTitulo("Rango de fechas");
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setCheckBoxChecked(false);
            objSelFec.setBounds(4, 20, 472, 72);

            //*****************************************************************************
            Librerias.ZafDate.ZafDatePicker txtFecDoc;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setHoy();
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
            int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if(fecDoc!=null){
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(java.util.Calendar.DATE, -30 );

            dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");

            objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );
            //*******************************************************************************
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configuraTbl(){
        
        boolean blnRes=false;
        
        try{
        
            //Configurar JTable: Establecer el modelo.
            vecCab = new Vector();
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_DAT_COD_TRA,"Código");
            vecCab.add(INT_TBL_DAT_CED_TRA,"Cédula");
            vecCab.add(INT_TBL_DAT_NOM_APE_TRA,"Empleado");
            vecCab.add(INT_TBL_DAT_NUM_FAL,"Núm.Fal.");
            vecCab.add(INT_TBL_DAT_NUM_FAL_REV,"Núm.Fal.Rev.");

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_DAT_LIN);

            //Configurar JTable: Establecer el msenú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);

            //Configurar JTable: EstableceobjTblCelRenLblr el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_TRA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_FAL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_FAL_REV).setCellRenderer(objTblCelRenLbl);
            
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_TRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CED_TRA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NOM_APE_TRA).setPreferredWidth(260);
            tcmAux.getColumn(INT_TBL_DAT_NUM_FAL).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUM_FAL_REV).setPreferredWidth(80);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);

            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
                
            //Configurar JTable: Establecer el modo de operación.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            blnRes=true;
                
        }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
           
        return blnRes;
    }
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    private void setLocationRelativeTo(Object object) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    private class ZafThreadGUI extends Thread{
    public void run(){

        lblMsgSis.setText("Obteniendo datos...");
        pgrSis.setIndeterminate(true);

        if(blnEstCarSolHSE){

            if (!cargarDat())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                //lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }  
            
        }else{
            
            if(validaCampos()){
                if (!cargarDat())
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    //lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                    tabFrm.setSelectedIndex(1);
                }
            }
            
        }
        
        //Establecer el foco en el JTable sólo cuando haya datos.
        if (tblDat.getRowCount()>0)
        {
//            tabFrm.setSelectedIndex(0);
            tblDat.setRowSelectionInterval(0, 0);
            tblDat.requestFocus();
        }else{
            mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
            tabFrm.setSelectedIndex(0);
            lblMsgSis.setText("Listo");
            pgrSis.setIndeterminate(false);
        }

        objThrGUI=null;
    }
}
    
        /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean validaCampos()
    {
        //Validar el "Año".
        int intPerAAAA=cboPerAAAA.getSelectedIndex();
        if(intPerAAAA<=0){
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Año</FONT> es obligatorio.<BR>Seleccione el año y vuelva a intentarlo.</HTML>");
            cboPerAAAA.requestFocus();
            return false;
        }
        //Validar el "Mes".
        int intPerMes=cboPerMes.getSelectedIndex();
        if(intPerMes<=0){
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Mes</FONT> es obligatorio.<BR>Seleccione el mes y vuelva a intentarlo.</HTML>");
            cboPerMes.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
 * Se encarga de cargar la informacion en la tabla
 * @return  true si esta correcto y false  si hay algun error
 */
private boolean cargarDat(){
  boolean blnRes=false;
  java.sql.Connection conn=null;
  java.sql.Statement stmLoc=null;
  java.sql.Statement stmLocAux=null;
  java.sql.ResultSet rstLoc = null;
  java.sql.ResultSet rstLocAux = null;
  String strSql="";
  String strFilFec="";
  String sqlFilEmp = "";
  Vector vecDataCon;
  Vector vecData;
  Vector vecFecCon;
  try{
   
      conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
          
      if(conn!=null){
          
          
          stmLoc=conn.createStatement();
          stmLocAux=conn.createStatement();
          vecDataCon = new Vector();
          vecData = new Vector();
          vecFecCon = new Vector();
          
          if(txtCodEmp.getText().compareTo("")!=0){
              sqlFilEmp+= " and b.co_emp  = " + txtCodEmp.getText() + " ";
          }

          if(txtCodTra.getText().compareTo("")!=0){
              sqlFilEmp+= " AND b.co_tra  = " + txtCodTra.getText() + " ";
          }

          String strOfi="";
          if(txtCodOfi.getText().compareTo("")!=0){
              sqlFilEmp+= " AND b.co_ofi  = " + txtCodOfi.getText() + " ";
          }
          
          String strEmp="";
          if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
              strEmp=" and b.co_emp = "+ objParSis.getCodigoEmpresa();
          }
          
          int intAño=-1;
          int intMes=-1;
          
          if(blnEstCarSolHSE){
              intAño=Integer.parseInt(strNeAniAut);
              intMes=Integer.parseInt(strNeMesAut);
          }else{
              intAño=Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
              intMes=cboPerMes.getSelectedIndex();
          }
          
          strSql="";
          strSql+="SELECT a.co_emp , a.co_tra , f.tx_nom as empresa , a.ne_ani , a.ne_mes , b.co_dep, b.co_ofi , c.tx_ide as cedula , (c.tx_ape || ' ' || c.tx_nom) as empleado , a.ne_numdialab , a.ne_numfal, a.ne_numfalrev" + "\n";
          strSql+="FROM tbm_datGenIngEgrMenTra a" + "\n";
          strSql+="INNER JOIN tbm_traemp b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra)" + "\n";
          strSql+="INNER JOIN tbm_tra c on (c.co_tra=b.co_tra) " + "\n";
          strSql+="INNER JOIN tbm_emp d on (d.co_emp=b.co_emp) " + "\n";
          strSql+="LEFT OUTER JOIN tbm_ofi e on (e.co_ofi=b.co_ofi) " + "\n";
          strSql+="INNER JOIN tbm_emp f on (f.co_emp=b.co_emp) " + "\n";
          strSql+="WHERE a.ne_ani = " + intAño + "\n";
          strSql+="AND a.ne_mes = " + intMes + "\n";
          strSql+="AND (a.ne_numfal is not null AND a.ne_numfal > 0) " + "\n";
          strSql+=" " + sqlFilEmp;
          strSql+=" " + strEmp;
          strSql+="ORDER BY empresa, empleado" + "\n";
          
          System.out.println("q consul: " + strSql);
          rstLoc=stmLoc.executeQuery(strSql);
          
          int intCon=0;
          vecDat = new Vector();
          
          while(rstLoc.next()){

                java.util.Vector vecReg = new java.util.Vector();
                vecReg.add(INT_TBL_DAT_LIN,"");
                vecReg.add(INT_TBL_DAT_COD_EMP,rstLoc.getInt("co_emp"));
                vecReg.add(INT_TBL_DAT_NOM_EMP,rstLoc.getString("empresa"));
                vecReg.add(INT_TBL_DAT_COD_TRA,rstLoc.getInt("co_tra"));
                vecReg.add(INT_TBL_DAT_CED_TRA,rstLoc.getString("cedula"));
                vecReg.add(INT_TBL_DAT_NOM_APE_TRA,rstLoc.getString("empleado"));
                vecReg.add(INT_TBL_DAT_NUM_FAL,rstLoc.getString("ne_numfal"));
                vecReg.add(INT_TBL_DAT_NUM_FAL_REV,rstLoc.getString("ne_numFalRev"));
                intCon++;
                vecDataCon.add(vecReg);
            }
          
          if(vecDataCon.size()>0){
              blnExiInfoFal=true;
          }

            objTblMod.setData(vecDataCon);
            tblDat .setModel(objTblMod);          
            lblMsgSis.setText("Listo");
//            tabFrm.setSelectedIndex(1);
            lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros");
            pgrSis.setIndeterminate(false);
            vecDat.clear();
      }
  }catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  Evt.printStackTrace();objUti.mostrarMsgErr_F1(this, Evt);  }
  finally {
        try{rstLoc.close();}catch(Throwable ignore){}
        try{rstLocAux.close();}catch(Throwable ignore){}
        try{stmLoc.close();}catch(Throwable ignore){}
        try{stmLocAux.close();}catch(Throwable ignore){}
        try{conn.close();}catch(Throwable ignore){}
    }
    System.gc();
    return blnRes;
}

public boolean existeFaltasPeriodo(){
    return blnExiInfoFal;
}

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;    
                case INT_TBL_DAT_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break; 
                case INT_TBL_DAT_COD_TRA:
                    strMsg="Código del empleado";
                    break;
                case INT_TBL_DAT_CED_TRA:
                    strMsg="Cédula del empleado";
                    break;
                case INT_TBL_DAT_NOM_APE_TRA:
                    strMsg="Nombres y apellidos del empleado";
                    break;
                case INT_TBL_DAT_NUM_FAL:
                    strMsg="Número de faltas del empleado";
                    break;
                case INT_TBL_DAT_NUM_FAL_REV:
                    strMsg="Número de faltas revisadas del empleado";
                    break;
                default:
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

     private boolean mostrarVenConEmp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else{
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else{
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else{
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else{
                            txtNomEmp.setText(strNomEmp);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
     private boolean mostrarVenConTra(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.show();
                    if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoTra.buscar("a1.co_tra", txtCodTra.getText())){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    else{
                        vcoTra.setCampoBusqueda(0);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                        }
                        else{
                            txtCodTra.setText(strCodTra);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTra.buscar("a1.tx_ape", txtNomTra.getText())){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    else{
                        vcoTra.setCampoBusqueda(1);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                        }
                        else{
                            txtNomTra.setText(strNomTra);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConEmp(){
        boolean blnRes=true;
        String strTitVenCon="";
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            
            //Armar la sentencia SQL.            
            if(objParSis.getCodigoUsuario()==1){
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }else{
                    strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp in ("+objParSis.getCodigoEmpresa() +")" + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
                
            }
            else{
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE a1.co_emp in ("+objParSis.getCodigoEmpresa() +")" + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
            }
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConOfi(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoOfi.setCampoBusqueda(2);
                    vcoOfi.setVisible(true);
                    if (vcoOfi.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    //vcoDep.setCampoBusqueda(0);
                    vcoOfi.setVisible(true);
                    if (vcoOfi.buscar("a1.co_ofi", txtCodOfi.getText()))
                    {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(3));
                    }
                    else
                    {
                        vcoOfi.setCampoBusqueda(1);
                        vcoOfi.setCriterio1(11);
                        vcoOfi.cargarDatos();
                        vcoOfi.setVisible(true);
                        if (vcoOfi.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            
                            txtCodOfi.setText(vcoOfi.getValueAt(1));
                            txtNomOfi.setText(vcoOfi.getValueAt(3));
                        }
                        else
                        {
                            txtNomOfi.setText(strDesLarOfi);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoOfi.setCampoBusqueda(2);
                    //vcoDep.setVisible(true);
                    if (vcoOfi.buscar("a1.tx_nom", txtNomOfi.getText()))
                    {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(3));
                    }
                    else
                    {
                        vcoOfi.setCampoBusqueda(2);
                        vcoOfi.setCriterio1(11);
                        vcoOfi.cargarDatos();
                        vcoOfi.setVisible(true);
                        if (vcoOfi.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodOfi.setText(vcoOfi.getValueAt(1));
                            txtNomOfi.setText(vcoOfi.getValueAt(3));
                        }
                        else
                        {
                            txtNomOfi.setText(strDesLarOfi);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConOfi()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_ofi");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            
            String strSQL="select co_ofi, tx_nom from tbm_ofi where st_reg='A'";

            vcoOfi=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado Departamentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoOfi.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Empleados".
     */
    private boolean configurarVenConTra()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ape");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");
            
            String strSQL="";
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' "+
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }else{
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' and co_emp = "+ objParSis.getCodigoEmpresa() + " " +
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }
            
            vcoTra=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
//            vcoTra.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
//            vcoTra.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
}