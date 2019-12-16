

package RecursosHumanos.ZafRecHum55;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRecHum.ZafRecHumDao.RRHHDao;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * Mantenimiento de Almuerzos....
 * @author  Roberto Flores
 * Guayaquil 09/11/2012
 */
public class ZafRecHum55 extends javax.swing.JInternalFrame
{
    
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_CHKSEL=1;
    private static final int INT_TBL_DAT_COD_EMP=2;
    private static final int INT_TBL_DAT_NOM_EMP=3;
    private static final int INT_TBL_DAT_COD_OFI=4;
    private static final int INT_TBL_DAT_NOM_OFI=5;
    private static final int INT_TBL_DAT_COD_TRA=6;
    private static final int INT_TBL_DAT_NOM_APE_TRA=7;
    private static final int INT_TBL_DAT_CHK_REC_ALM=8;
    private static final int INT_TBL_DAT_CHK_REC_ALM_ROL=9;
    private static final int INT_TBL_DAT_CHK_REC_ALM_COM=10;
    private static final int INT_TBL_DAT_ND_VALALM=11;
    
    
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                                            //Editor: Editor del JTable.
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                                //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                                //Editor: JTextField en celda.
    private ZafTblCelEdiChk zafTblCelEdiChkSel;                             //Editor de Check Box
    private ZafTblCelRenChk zafTblCelRenChkSel;                             //Renderer de Check Box 
    private ZafTblCelEdiChk zafTblCelEdiChkRecAlm;                             //Editor de Check Box
    private ZafTblCelRenChk zafTblCelRenChkRecAlm;                             //Renderer de Check Box 
    private ZafTblCelRenChk zafTblCelRenChkRecAlmRol;                             //Renderer de Check Box 
    private ZafTblCelRenChk zafTblCelRenChkRecAlmCom;                             //Renderer de Check Box 
    private ZafTblCelEdiChk zafTblCelEdiChkRecAlmRol;                             //Editor de Check Box
    private ZafTblCelEdiChk zafTblCelEdiChkRecAlmCom;                             //Editor de Check Box
    
    private ZafMouMotAda objMouMotAda;                                      //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                      //PopupMenu: Establecer PopupMenú en JTable.
    private ZafVenCon vcoEmp;                                               //Ventana de consulta.
    private ZafVenCon vcoOfi;                                               //Ventana de consulta.
    private ZafVenCon vcoTra;
    

    private static final int INT_TBL_DAT_NUM_TOT_CDI=16;                   //Número total de columnas dinámicas.
    
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
    
    private String strVersion="v1.02";
        
    /** Crea una nueva instancia de la clase ZafRecHum52. */
    public ZafRecHum55(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            
            butCon.setVisible(false);
            butGua.setVisible(false);
            butCer.setVisible(false);
            
            if(objPerUsr.isOpcionEnabled(3377)){
                butCon.setVisible(true);
            }
            if(objPerUsr.isOpcionEnabled(3378)){
                butGua.setVisible(true);
            }
            if(objPerUsr.isOpcionEnabled(3379)){
                butCer.setVisible(true);
            }

            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e)
        {
            {objUti.mostrarMsgErr_F1(this, e);}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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
        panRpt = new javax.swing.JPanel();
        panCabRpt = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        butDes = new javax.swing.JButton();
        butAsi = new javax.swing.JButton();
        butMar = new javax.swing.JButton();
        txtValAlm = new javax.swing.JTextField();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
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

        tabFrm.addTab("Filtro", null, panFil, "Filtro");

        panRpt.setLayout(new java.awt.BorderLayout());

        panCabRpt.setPreferredSize(new java.awt.Dimension(0, 50));
        panCabRpt.setLayout(null);

        jLabel1.setText("Valor del almuerzo:");
        panCabRpt.add(jLabel1);
        jLabel1.setBounds(20, 14, 91, 14);

        butDes.setText("Desmarcar"); // NOI18N
        butDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDesActionPerformed(evt);
            }
        });
        panCabRpt.add(butDes);
        butDes.setBounds(430, 14, 100, 20);

        butAsi.setText("Asignar"); // NOI18N
        butAsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAsiActionPerformed(evt);
            }
        });
        panCabRpt.add(butAsi);
        butAsi.setBounds(230, 14, 100, 20);

        butMar.setText("Marcar"); // NOI18N
        butMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butMarActionPerformed(evt);
            }
        });
        panCabRpt.add(butMar);
        butMar.setBounds(330, 14, 100, 20);

        txtValAlm.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValAlm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValAlmActionPerformed(evt);
            }
        });
        txtValAlm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValAlmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValAlmFocusLost(evt);
            }
        });
        panCabRpt.add(txtValAlm);
        txtValAlm.setBounds(148, 14, 80, 20);

        panRpt.add(panCabRpt, java.awt.BorderLayout.PAGE_START);

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

        butGua.setText("Guardar");
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

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

        setSize(new java.awt.Dimension(700, 450));
        setLocationRelativeTo(null);
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

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblMod.isDataModelChanged())
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                if(guardarDat())
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                else
                    mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
            }
        }
        else
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
    }//GEN-LAST:event_butGuaActionPerformed
   
    private boolean guardarDat(){
        boolean blnRes=true;
        java.sql.Connection con = null; 
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSql="";
        
        try{
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
      
            if(con!=null){
                
                con.setAutoCommit(false);
                stmLoc=con.createStatement();
                
                for(int i=0; i<tblDat.getRowCount();i++){

                    if(tblDat.getValueAt(i, INT_TBL_DAT_LIN).toString().compareTo("M")==0){
                        
                        
                        String strRecAlm="";
                        if(objTblMod.getValueAt(i, INT_TBL_DAT_CHK_REC_ALM).equals(true)){
                            strRecAlm=objUti.codificar("S");
                        }else{
                            strRecAlm="null";
                        }
                        
                        String strNdValAlm=objTblMod.getValueAt(i, INT_TBL_DAT_ND_VALALM).toString();
                        if(objTblMod.getValueAt(i, INT_TBL_DAT_ND_VALALM).equals("")){
                            strNdValAlm=null;
                        }else if (objTblMod.getValueAt(i, INT_TBL_DAT_ND_VALALM).equals(null)){
                            strNdValAlm=null;
                        }else if(objTblMod.getValueAt(i, INT_TBL_DAT_ND_VALALM).toString().compareTo("0.0")==0){
                            strNdValAlm=null;
                        }else{
                            strNdValAlm=objTblMod.getValueAt(i, INT_TBL_DAT_ND_VALALM).toString();
                        }
                            
                        boolean blnRecAlmRol=(Boolean)objTblMod.getValueAt(i, INT_TBL_DAT_CHK_REC_ALM_ROL);
                        boolean blnRecAlmCom=(Boolean)objTblMod.getValueAt(i, INT_TBL_DAT_CHK_REC_ALM_COM);
                        String strTxForRecAlm=null;
                        
                        if(blnRecAlmRol){
                            strTxForRecAlm="R";
                        }
                        
                        if(blnRecAlmCom){
                            strTxForRecAlm="C";
                        }
                        
                        strSql="";
                        strSql+="update tbm_traemp";
                        strSql+=" set st_recalm = " + strRecAlm + " , ";
                        strSql+=" nd_valalm = " + strNdValAlm + " , ";
                        strSql+=" tx_forrecalm = " + objUti.codificar(strTxForRecAlm);
                        strSql+=" where co_emp = " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP);
                        strSql+=" and co_tra = " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TRA);
                        System.out.println("act recibe almuerzo:" + strSql);
                        stmLoc.executeUpdate(strSql);
                    }
                }
                //Inicializo el estado de las filas.
//                objTblMod.initRowsState();
//                objTblMod.setDataModelChanged(false);
            }
            if(blnRes){
                con.commit();
                new RRHHDao(objUti, objParSis).callServicio9();
            }
            
        }catch(java.sql.SQLException Evt) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }catch(Exception Evt) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }finally {
        try{stmLoc.close();}catch(Throwable ignore){}
        try{rstLoc.close();}catch(Throwable ignore){}
        try{con.close();}catch(Throwable ignore){}
        }
    return blnRes;
}
    
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

    private void butDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDesActionPerformed

        int[] row = tblDat.getSelectedRows();
        if(row.length>0){
            for(int intFil=0; intFil<row.length; intFil++){
                objTblMod.setValueAt(Boolean.FALSE , row[intFil], INT_TBL_DAT_CHKSEL);
            }
        }

    }//GEN-LAST:event_butDesActionPerformed

    private void butAsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAsiActionPerformed

        try{
            
            String strValAlm = txtValAlm.getText();
            if(!strValAlm.equals("")){
                
                double dblValAlm = Double.parseDouble(txtValAlm.getText());
                
                if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0){
        
                    for(int intFil=0; intFil < tblDat.getRowCount(); intFil++){

                        if(objTblMod.getValueAt(intFil, INT_TBL_DAT_CHKSEL).equals(true)){

                            objTblMod.setValueAt(Boolean.TRUE, intFil, INT_TBL_DAT_CHK_REC_ALM);
                            objTblMod.setValueAt(txtValAlm.getText(), intFil, INT_TBL_DAT_ND_VALALM);
                        }
                    }
                }
            }
        }catch(Exception e){
            txtValAlm.setText("");
            txtValAlm.requestFocus();
        }
    }//GEN-LAST:event_butAsiActionPerformed

    private void butMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMarActionPerformed
        int[] row = tblDat.getSelectedRows();
        if(row.length>0){
            for(int intFil=0; intFil<row.length; intFil++){
                objTblMod.setValueAt(Boolean.TRUE , row[intFil], INT_TBL_DAT_CHKSEL);
            }
        }
        System.out.println();
    }//GEN-LAST:event_butMarActionPerformed

    private void txtValAlmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValAlmFocusGained
        txtValAlm.selectAll();
    }//GEN-LAST:event_txtValAlmFocusGained

    private void txtValAlmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValAlmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValAlmActionPerformed

    private void txtValAlmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValAlmFocusLost
        // TODO add your handling code here:
        try{
            
            String strValAlm = txtValAlm.getText();
            if(!strValAlm.equals("")){
                double dblValAlm = Double.parseDouble(txtValAlm.getText());
            }
        }catch(Exception e){
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "Valor del almuerzo contiene formato erroneo. Revisar e intentar nuevamente.";
            JOptionPane.showMessageDialog(ZafRecHum55.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
            txtValAlm.setText("");
            txtValAlm.requestFocus();
        }
    }//GEN-LAST:event_txtValAlmFocusLost

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
    private javax.swing.JButton butAsi;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butDes;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butMar;
    private javax.swing.JButton butOfi;
    private javax.swing.JButton butTra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCabRpt;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodOfi;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomOfi;
    private javax.swing.JTextField txtNomTra;
    private javax.swing.JTextField txtValAlm;
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
            agregarColTblDat();
            
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
            vecCab.add(INT_TBL_DAT_CHKSEL,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_DAT_COD_OFI,"Cód.Ofi.");
            vecCab.add(INT_TBL_DAT_NOM_OFI,"Oficina");
            vecCab.add(INT_TBL_DAT_COD_TRA,"Código");
            vecCab.add(INT_TBL_DAT_NOM_APE_TRA,"Empleado");
            vecCab.add(INT_TBL_DAT_CHK_REC_ALM,"¿Almuerzo?");
            vecCab.add(INT_TBL_DAT_CHK_REC_ALM_ROL,"Rol");
            vecCab.add(INT_TBL_DAT_CHK_REC_ALM_COM,"Comida");
            vecCab.add(INT_TBL_DAT_ND_VALALM,"Val.Alm.");

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
        
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_ND_VALALM, objTblMod.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_DAT_LIN);

            //Configurar JTable: Establecer el msenú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CHKSEL).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_OFI).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_COD_TRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_APE_TRA).setPreferredWidth(260);
            tcmAux.getColumn(INT_TBL_DAT_CHK_REC_ALM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CHK_REC_ALM_ROL).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CHK_REC_ALM_COM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_ND_VALALM).setPreferredWidth(60);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_OFI, tblDat);
        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHKSEL);
            vecAux.add("" + INT_TBL_DAT_CHK_REC_ALM);
            vecAux.add("" + INT_TBL_DAT_CHK_REC_ALM_ROL);
            vecAux.add("" + INT_TBL_DAT_CHK_REC_ALM_COM);
            vecAux.add("" + INT_TBL_DAT_ND_VALALM);
            objTblMod.addColumnasEditables(vecAux);
            vecAux=null;

            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);

            zafTblCelRenChkSel = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHKSEL).setCellRenderer(zafTblCelRenChkSel);
            zafTblCelEdiChkSel = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHKSEL).setCellEditor(zafTblCelEdiChkSel);
        
            zafTblCelRenChkRecAlm = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_REC_ALM).setCellRenderer(zafTblCelRenChkRecAlm);
            zafTblCelEdiChkRecAlm = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_REC_ALM).setCellEditor(zafTblCelEdiChkRecAlm);
            zafTblCelEdiChkRecAlm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    
                int intMaxCol=tblDat.getColumnCount();
                    
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                }        
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    int intCol = tblDat.getSelectedColumn();//columna
                    int intFil = tblDat.getSelectedRow();//fila
                    tblDat.setValueAt( Boolean.FALSE, intFil , INT_TBL_DAT_CHK_REC_ALM);
                    tblDat.setValueAt( Boolean.FALSE, intFil , INT_TBL_DAT_CHK_REC_ALM_ROL);
                    tblDat.setValueAt( Boolean.FALSE, intFil , INT_TBL_DAT_CHK_REC_ALM_COM);
                    tblDat.setValueAt( "", intFil , INT_TBL_DAT_ND_VALALM);
                }
            });
            
            zafTblCelRenChkRecAlmRol = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_REC_ALM_ROL).setCellRenderer(zafTblCelRenChkRecAlmRol);
            zafTblCelEdiChkRecAlmRol = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_REC_ALM_ROL).setCellEditor(zafTblCelEdiChkRecAlmRol);
            zafTblCelEdiChkRecAlmRol.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    
                int intMaxCol=tblDat.getColumnCount();
                    
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                }        
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    int intCol = tblDat.getSelectedColumn();//columna
                    int intFil = tblDat.getSelectedRow();//fila
                    tblDat.setValueAt( Boolean.TRUE, intFil , INT_TBL_DAT_CHK_REC_ALM);
                    tblDat.setValueAt( Boolean.TRUE, intFil , INT_TBL_DAT_CHK_REC_ALM_ROL);
                    tblDat.setValueAt( Boolean.FALSE, intFil , INT_TBL_DAT_CHK_REC_ALM_COM);
                }
            });
            
            zafTblCelRenChkRecAlmCom = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_REC_ALM_COM).setCellRenderer(zafTblCelRenChkRecAlmCom);
            zafTblCelEdiChkRecAlmCom = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_REC_ALM_COM).setCellEditor(zafTblCelEdiChkRecAlmCom);
            zafTblCelEdiChkRecAlmCom.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    
                int intMaxCol=tblDat.getColumnCount();
                    
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                }        
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    int intCol = tblDat.getSelectedColumn();//columna
                    int intFil = tblDat.getSelectedRow();//fila
                    tblDat.setValueAt( Boolean.TRUE, intFil , INT_TBL_DAT_CHK_REC_ALM);
                    tblDat.setValueAt( Boolean.FALSE, intFil , INT_TBL_DAT_CHK_REC_ALM_ROL);
                    tblDat.setValueAt( Boolean.TRUE, intFil , INT_TBL_DAT_CHK_REC_ALM_COM);
                }
            });
        
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_ND_VALALM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_ND_VALALM).setCellRenderer(objTblCelRenLbl);
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_ND_VALALM).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
//                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//
//                    intFilSel=tblDat.getSelectedRow();
//
//                    try {
//
//                    } catch (Exception ex) {
//                        String strTit = "Mensaje del sistema Zafiro";
//                        String strMen = "Horario ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
//                        JOptionPane.showMessageDialog(ZafRecHum60.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
//                        objTblMod.setValueAt("",intFilSel, tblDat.getSelectedColumn());
//                    }
//                }
            });
                
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
                
            //Configurar JTable: Establecer el modo de operación.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            blnRes=true;
                
        }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
           
        return blnRes;
    }

    
    private boolean agregarColTblDat(){

        int i, intNumFil, intNumColTblDat;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpEmp=null;
        java.awt.Color colFonCol;
        boolean blnRes=true;

        try
        {
            intNumFil=objTblMod.getRowCountTrue();
            intNumColTblDat=objTblMod.getColumnCount();

            for (i=0; i<1; i++){
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKSEL+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_EMP+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_OFI+i*INT_TBL_DAT_NUM_TOT_CDI));
                
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_OFI+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_TRA+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_APE_TRA+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_REC_ALM+i*INT_TBL_DAT_NUM_TOT_CDI));
                

                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Forma en la que recibe el almuerzo");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_REC_ALM_ROL+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_REC_ALM_COM+i*INT_TBL_DAT_NUM_TOT_CDI));
                
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_ND_VALALM+i*INT_TBL_DAT_NUM_TOT_CDI));
                
            }
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    public static java.sql.Time SparseToTime(String hora){
        int h, m, s;

        h = Integer.parseInt(hora.charAt(0)+""+hora.charAt(1)) ;
        m = Integer.parseInt(hora.charAt(3)+""+hora.charAt(4)) ;
        s = Integer.parseInt(hora.charAt(6)+""+hora.charAt(7)) ;
        return (new java.sql.Time(h,m,s));
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

        if (!cargarDat())
        {
            //Inicializar objetos si no se pudo cargar los datos.
            //lblMsgSis.setText("Listo");
            pgrSis.setValue(0);
            butCon.setText("Consultar");
        }

        //Establecer el foco en el JTable sólo cuando haya datos.
        if (tblDat.getRowCount()>0)
        {
            tabFrm.setSelectedIndex(1);
            tblDat.setRowSelectionInterval(0, 0);
            tblDat.requestFocus();
        }

        objThrGUI=null;
    }
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
              sqlFilEmp+= " and a.co_emp  = " + txtCodEmp.getText() + " ";
          }

          if(txtCodTra.getText().compareTo("")!=0){
              sqlFilEmp+= " AND a.co_tra  = " + txtCodTra.getText() + " ";
          }

          String strOfi="";
          if(txtCodOfi.getText().compareTo("")!=0){
              sqlFilEmp+= " AND a.co_ofi  = " + txtCodOfi.getText() + " ";
          }
          
          String strEmp="";
          if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
              strEmp=" and a.co_emp = "+ objParSis.getCodigoEmpresa();
          }
          
          strSql="";
          strSql+="select a.co_emp, a.co_tra, a.co_dep, a.co_ofi, c.tx_nom as empresa , d.tx_nom as oficina, (b.tx_ape || ' ' || b.tx_nom) as empleado,";
          strSql+=" case (a.st_recalm) when 'S' then true else false end as blnrecalm,tx_forRecAlm,nd_valalm";
          strSql+=" from tbm_traemp a";
          strSql+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
          strSql+=" inner join tbm_emp c on (a.co_emp=c.co_emp)";
          strSql+=" left outer join tbm_ofi d on (a.co_ofi=d.co_ofi)";
          strSql+=" where a.st_reg='A'";
          strSql+=" " + sqlFilEmp;
          strSql+=" " + strEmp;
          strSql+=" order by co_emp, empleado";
              
          System.out.println("q consul: " + strSql);
          rstLoc=stmLoc.executeQuery(strSql);
              
          vecDat = new Vector();
          while(rstLoc.next()){

                java.util.Vector vecReg = new java.util.Vector();
                vecReg.add(INT_TBL_DAT_LIN,"");
                vecReg.add(INT_TBL_DAT_CHKSEL,Boolean.FALSE);
                vecReg.add(INT_TBL_DAT_COD_EMP,rstLoc.getInt("co_emp"));
                vecReg.add(INT_TBL_DAT_NOM_EMP,rstLoc.getString("empresa"));
                vecReg.add(INT_TBL_DAT_COD_OFI,rstLoc.getString("co_ofi"));
                vecReg.add(INT_TBL_DAT_NOM_OFI,rstLoc.getString("oficina"));
                vecReg.add(INT_TBL_DAT_COD_TRA,rstLoc.getInt("co_tra"));
                vecReg.add(INT_TBL_DAT_NOM_APE_TRA,rstLoc.getString("empleado"));
                vecReg.add(INT_TBL_DAT_CHK_REC_ALM,rstLoc.getBoolean("blnrecalm"));
                if(rstLoc.getString("tx_forRecAlm")==null){
                    vecReg.add(INT_TBL_DAT_CHK_REC_ALM_ROL,Boolean.FALSE);
                    vecReg.add(INT_TBL_DAT_CHK_REC_ALM_COM,Boolean.FALSE);
                }else{
                    if(rstLoc.getString("tx_forRecAlm").compareTo("R")==0){
                        vecReg.add(INT_TBL_DAT_CHK_REC_ALM_ROL,Boolean.TRUE);
                        vecReg.add(INT_TBL_DAT_CHK_REC_ALM_COM,Boolean.FALSE);
                    }else{
                        vecReg.add(INT_TBL_DAT_CHK_REC_ALM_ROL,Boolean.FALSE);
                        vecReg.add(INT_TBL_DAT_CHK_REC_ALM_COM,Boolean.TRUE);
                    }
                }
                
                vecReg.add(INT_TBL_DAT_ND_VALALM,rstLoc.getDouble("nd_valalm"));
                vecDataCon.add(vecReg);
            }

            objTblMod.setData(vecDataCon);
            tblDat .setModel(objTblMod);          
            lblMsgSis.setText("Listo");
            tabFrm.setSelectedIndex(1);
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
                case INT_TBL_DAT_CHKSEL:
                    strMsg="Seleccionar los registros a los que se les asignará el \"Valor del almuerzo\"";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;    
                case INT_TBL_DAT_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break; 
                case INT_TBL_DAT_COD_OFI:
                    strMsg="Código de la oficina";
                    break; 
                case INT_TBL_DAT_NOM_OFI:
                    strMsg="Nombre de la oficina";
                    break;
                case INT_TBL_DAT_COD_TRA:
                    strMsg="Código del empleado";
                    break;
                case INT_TBL_DAT_NOM_APE_TRA:
                    strMsg="Nombres y apellidos del empleado";
                    break;
                case INT_TBL_DAT_CHK_REC_ALM:
                    strMsg="¿Recibe el almuerzo?";
                    break;
                case INT_TBL_DAT_CHK_REC_ALM_ROL:
                    strMsg="Lo recibe en el \"Rol de pagos\"";
                    break;
                case INT_TBL_DAT_CHK_REC_ALM_COM:
                    strMsg="Lo recibe en \"Comida\"";
                    break;
                case INT_TBL_DAT_ND_VALALM:
                    strMsg="Valor del almuerzo";
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