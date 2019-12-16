/*
 * ZafCom24.java
 *
 * 
 */
package Compras.ZafCom17;   
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafVenCon.ZafVenCon;  //**************************
import java.util.ArrayList;  //*******************
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
/**
 *
 * @author javier ayapata
 */
public class ZafCom17 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    final int INT_TBL_BOD_LIN=0;                        //Línea.
    final int INT_TBL_BOD_CHK=1;                        //Casilla de verificación.
    final int INT_TBL_BOD_COD_EMP=2;                    //Código de la empresa.
    final int INT_TBL_BOD_NOM_EMP=3;                    //Nombre de la empresa.
    final int INT_TBL_BOD_COD_BOD=4;                    //Código de la bodega.
    final int INT_TBL_BOD_NOM_BOD=5;                    //Nombre de la bodega.
    
    final int INT_TBL_DAT_LIN=0;                        //Línea
    final int INT_TBL_DAT_COD_MAE=1;                    //Código maestro del item.
    final int INT_TBL_DAT_COD_SIS=2;                    //Código del item (Sistema).
    final int INT_TBL_DAT_COD_ALT=3;                    //Código del item (Alterno).
    final int INT_TBL_DAT_NOM_ITM=4;                    //Nombre del item.
    final int INT_TBL_DAT_DEC_UNI=5;                    //Descripción corta de la unidad de medida.
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModBod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatBod, vecCabBod;
    private Vector vecAux;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.
    private String strCodAlt, strNomItm;                //Contenido del campo al obtener el foco.
    private boolean blnMarTodChkTblBod=true;            //Marcar todas las casillas de verificación del JTable de bodegas.
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    javax.swing.JTextField txtcodemp = new javax.swing.JTextField("");
    ZafVenCon objVenConEmp; //*****************  
    private String strCodCom, strDesLarCom;   
         
    private ZafTblOrd objTblOrd;  
     
    /** Crea una nueva instancia de la clase ZafCom14. */
    public ZafCom17(ZafParSis obj) 
    {
        try{
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
       
         objUti=new ZafUtil();
        
            String strFecSis;  
            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());      
     
            
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y"); 
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText(strFecSis);
            panFec.add(txtFecDoc);
            txtFecDoc.setBounds(65, 20, 100, 20);
           
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                butItm1.setVisible(false);
                txtnomemp.setVisible(false);
                lblnomemp.setVisible(false);
                txtcodemp.setText(""+objParSis.getCodigoEmpresa());
            }
              
            
        }catch(Exception e){  objUti.mostrarMsgErr_F1(this, e); }
    }
    
    
    
    
    
    public void Configura_ventana_consulta(){
        configurarVenConEmpresa();
    }
    
    
    
    private boolean configurarVenConEmpresa()
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_emp");
            arlCam.add("a.tx_nom");
          
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Emp.");
           
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("100");
            arlAncCol.add("450");
            
            String  strSQL="";
            strSQL="select a.co_emp, a.tx_nom from tbm_Emp as a where a.co_emp<> "+objParSis.getCodigoEmpresa(); 
           
            objVenConEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
      
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        panRngCodAltItm = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        lblItm = new javax.swing.JLabel();
        txtCodAlt = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        txtCodItm = new javax.swing.JTextField();
        chkSolStk = new javax.swing.JCheckBox();
        panBod = new javax.swing.JPanel();
        spnBod = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        panFec = new javax.swing.JPanel();
        lblCodAltDes1 = new javax.swing.JLabel();
        lblnomemp = new javax.swing.JLabel();
        txtnomemp = new javax.swing.JTextField();
        butItm1 = new javax.swing.JButton();
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

        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("T\u00edtulo de la ventana");
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
                formInternalFrameOpened(evt);
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("T\u00edtulo de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        optTod.setSelected(true);
        optTod.setText("Todos los items");
        bgrFil.add(optTod);
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });

        panFil.add(optTod);
        optTod.setBounds(4, 4, 400, 20);

        optFil.setText("S\u00f3lo los items que cumplan el criterio seleccionado");
        bgrFil.add(optFil);
        panFil.add(optFil);
        optFil.setBounds(4, 24, 400, 20);

        panRngCodAltItm.setLayout(null);

        panRngCodAltItm.setBorder(new javax.swing.border.TitledBorder("C\u00f3digo alterno del item"));
        lblCodAltDes.setText("Desde:");
        panRngCodAltItm.add(lblCodAltDes);
        lblCodAltDes.setBounds(12, 20, 44, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });

        panRngCodAltItm.add(txtCodAltDes);
        txtCodAltDes.setBounds(56, 20, 268, 20);

        lblCodAltHas.setText("Hasta:");
        panRngCodAltItm.add(lblCodAltHas);
        lblCodAltHas.setBounds(336, 20, 44, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });

        panRngCodAltItm.add(txtCodAltHas);
        txtCodAltHas.setBounds(380, 20, 268, 20);

        panFil.add(panRngCodAltItm);
        panRngCodAltItm.setBounds(24, 64, 660, 52);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Beneficiario");
        panFil.add(lblItm);
        lblItm.setBounds(24, 44, 120, 20);

        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });
        txtCodAlt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltFocusLost(evt);
            }
        });

        panFil.add(txtCodAlt);
        txtCodAlt.setBounds(144, 44, 92, 20);

        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });

        panFil.add(txtNomItm);
        txtNomItm.setBounds(236, 44, 424, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });

        panFil.add(butItm);
        butItm.setBounds(660, 44, 20, 20);

        panFil.add(txtCodItm);
        txtCodItm.setBounds(88, 44, 56, 20);

        chkSolStk.setText("S\u00f3lo items con stock");
        chkSolStk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolStkActionPerformed(evt);
            }
        });

        panFil.add(chkSolStk);
        chkSolStk.setBounds(30, 280, 324, 20);

        panBod.setLayout(new java.awt.BorderLayout());

        panBod.setBorder(new javax.swing.border.TitledBorder("Listado de bodegas"));
        tblBod.setModel(new javax.swing.table.DefaultTableModel(
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
        spnBod.setViewportView(tblBod);

        panBod.add(spnBod, java.awt.BorderLayout.CENTER);

        panFil.add(panBod);
        panBod.setBounds(30, 190, 660, 92);

        panFec.setLayout(null);

        panFec.setBorder(new javax.swing.border.TitledBorder(""));
        lblCodAltDes1.setText("Fecha:");
        panFec.add(lblCodAltDes1);
        lblCodAltDes1.setBounds(10, 20, 44, 20);

        lblnomemp.setText("Empresa:");
        panFec.add(lblnomemp);
        lblnomemp.setBounds(250, 20, 70, 20);

        txtnomemp.setEditable(false);
        panFec.add(txtnomemp);
        txtnomemp.setBounds(310, 20, 160, 20);

        butItm1.setText("...");
        butItm1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItm1ActionPerformed(evt);
            }
        });

        panFec.add(butItm1);
        butItm1.setBounds(470, 20, 20, 20);

        panFil.add(panFec);
        panFec.setBounds(24, 120, 660, 60);

        tabFrm.addTab("Filtro", panFil);

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
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

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

        panBarEst.setLayout(new java.awt.BorderLayout());

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        jPanel6.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        pgrSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void butItm1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItm1ActionPerformed
        // TODO add your handling code here:
        BuscarEmpresa("a.co_emp","",0);
    }//GEN-LAST:event_butItm1ActionPerformed

    
    
      public void BuscarEmpresa(String campo,String strBusqueda,int tipo){
        objVenConEmp.setTitle("Listado de Compradores"); 
        if (objVenConEmp.buscar(campo, strBusqueda ))
        {
            txtcodemp.setText(objVenConEmp.getValueAt(1));
            txtnomemp.setText(objVenConEmp.getValueAt(2));
            strCodCom=objVenConEmp.getValueAt(1);
            strDesLarCom=objVenConEmp.getValueAt(2);
        }
        else
        {     objVenConEmp.setCampoBusqueda(tipo);
              objVenConEmp.cargarDatos();
              objVenConEmp.show();
             if (objVenConEmp.getSelectedButton()==objVenConEmp.INT_BUT_ACE)
             {
                txtcodemp.setText(objVenConEmp.getValueAt(1));
                txtnomemp.setText(objVenConEmp.getValueAt(2));
                strCodCom=objVenConEmp.getValueAt(1);
                strDesLarCom=objVenConEmp.getValueAt(2);
             }
              else{
                    txtcodemp.setText(strCodCom);
                    txtnomemp.setText(strDesLarCom);
                  }
        }
    }
    
    
    
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        
         Configura_ventana_consulta();
         
          if (!configurarFrm())
            exitForm();
         
    }//GEN-LAST:event_formInternalFrameOpened

    private void chkSolStkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSolStkActionPerformed
        if (chkSolStk.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkSolStkActionPerformed

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butItmActionPerformed

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
            txtNomItm.setText(strNomItm);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomItmFocusLost

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAlt.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(1);
            }
        }
        else
            txtCodAlt.setText(strCodAlt);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodAltFocusLost

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
        strCodAlt=txtCodAlt.getText();
        txtCodAlt.selectAll();
    }//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtCodAlt.transferFocus();
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        if (txtCodAltHas.getText().length()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltHasFocusLost

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length()>0)
        {
            optFil.setSelected(true);
            if (txtCodAltHas.getText().length()==0)
                txtCodAltHas.setText(txtCodAltDes.getText());
        }
    }//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        txtCodAltHas.selectAll();
    }//GEN-LAST:event_txtCodAltHasFocusGained

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
            chkSolStk.setSelected(false);
        }
    }//GEN-LAST:event_optTodItemStateChanged

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

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butItm;
    private javax.swing.JButton butItm1;
    private javax.swing.JCheckBox chkSolStk;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltDes1;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblnomemp;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBod;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFec;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRngCodAltItm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBod;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtNomItm;
    private javax.swing.JTextField txtnomemp;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()  
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos. 
           
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.3");
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodItm.setVisible(false);
         
           
              
            configurarTblBod();
            configurarTblDat();
            cargarBod();
            agregarColTblDat();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_MAE,"Cód.Mae.");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre");
            vecCab.add(INT_TBL_DAT_DEC_UNI,"Unidad");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(257);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setPreferredWidth(50);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setResizable(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
           
            
             //Configurar JTable: Establecer el ordenamiento en la tabla.
             objTblOrd=new ZafTblOrd(tblDat);
            
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarTblBod()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatBod=new Vector();    //Almacena los datos
            vecCabBod=new Vector(6);   //Almacena las cabeceras
            vecCabBod.clear();
            vecCabBod.add(INT_TBL_BOD_LIN,"");
            vecCabBod.add(INT_TBL_BOD_CHK,"");
            vecCabBod.add(INT_TBL_BOD_COD_EMP,"Cód.Emp.");
            vecCabBod.add(INT_TBL_BOD_NOM_EMP,"Empresa");
            vecCabBod.add(INT_TBL_BOD_COD_BOD,"Cód.Bod.");
            vecCabBod.add(INT_TBL_BOD_NOM_BOD,"Bodega");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBod=new ZafTblMod();
            objTblModBod.setHeader(vecCabBod);
            tblBod.setModel(objTblModBod);
            //Configurar JTable: Establecer tipo de selección.
            tblBod.setRowSelectionAllowed(true);
            tblBod.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblBod);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblBod.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblBod.getColumnModel();
            tcmAux.getColumn(INT_TBL_BOD_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BOD_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_NOM_EMP).setPreferredWidth(231);
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_NOM_BOD).setPreferredWidth(231);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BOD_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBod.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setWidth(0);
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setResizable(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblBod.getTableHeader().addMouseMotionListener(new ZafMouMotAdaBod());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblBod.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblBodMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_BOD_CHK);
            objTblModBod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblBod);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_BOD_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiChk.isChecked())
                    {
//                        objTblMod.setValueAt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_PEN), tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                    
                        System.out.println(" "+ tblBod.getSelectedRow() );
                        
                         for(int i=0; i<tblBod.getRowCount(); i++ ){
                           if(tblBod.getSelectedRow() != i )
                             tblBod.setValueAt( new Boolean(false), i , INT_TBL_BOD_CHK );
                         }
                         
                         //tblBod.setValueAt( new Boolean(true), tblDat.getSelectedRow() , INT_TBL_BOD_CHK );
                    
                    }
                    else
                    {
                         //for(int i=0; i<tblBod.getRowCount(); i++ ){
                          //   tblBod.setValueAt( new Boolean(false), i , INT_TBL_BOD_CHK );
                         //}
                    }
                }
            });

            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblBod.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModBod.setModoOperacion(objTblModBod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite agregar columnas al "tblDat" de acuerdo al "tblBod".
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColTblDat()
    {
        int i, intNumFil, intNumColTblDat;
        javax.swing.table.TableColumn tbc;
        boolean blnRes=true;
        try
        {
            intNumFil=objTblModBod.getRowCountTrue();
            intNumColTblDat=objTblMod.getColumnCount();
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            for (i=0; i<intNumFil; i++)
            {
                tbc=new javax.swing.table.TableColumn(6+i);
                tbc.setHeaderValue("Stock (" + objTblModBod.getValueAt(i, INT_TBL_BOD_NOM_EMP) + " - " + objTblModBod.getValueAt(i, INT_TBL_BOD_NOM_BOD) + ")");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Ocultar la columna.
//                tbc.setWidth(0);
//                tbc.setMaxWidth(0);
//                tbc.setMinWidth(0);
//                tbc.setPreferredWidth(0);
//                tbc.setResizable(false);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                //Configurar JTable: Agregar la columna al JTable.
//                tblDat.addColumn(tbc);
                objTblMod.addColumn(tblDat, tbc);
            }
            objTblCelRenLbl=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite consultar las bodegas de acuerdo al siguiente criterio:
     * <UL>
     * <LI>Si se ingresa a la empresa "Grupo" se muestran todas las bodegas.
     * <LI>Si se ingresa a cualquier otra empresa se muestran sólo las bodegas de la empresa seleccionada.
     * </UL>
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarBod()
    {
        int intCodEmp;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                strSQL+=" FROM tbm_emp AS a1";
                strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                //if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
               
                
            
                
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDatBod.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_BOD_LIN,"");
                    vecReg.add(INT_TBL_BOD_CHK,null);
                    vecReg.add(INT_TBL_BOD_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_BOD_NOM_EMP,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_BOD_COD_BOD,rst.getString("co_bod"));
                    vecReg.add(INT_TBL_BOD_NOM_BOD,rst.getString("a2_tx_nom"));
                    vecDatBod.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModBod.setData(vecDatBod);
                tblBod.setModel(objTblModBod);
                vecDatBod.clear();
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intCodEmp, intNumFilTblBod, intNumTotReg, i, j;
        boolean blnRes=true;
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intNumFilTblBod=objTblModBod.getRowCountTrue();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strAux="";
                if (txtCodItm.getText().length()>0)
                    strAux+=" AND a1.co_itm=" + txtCodItm.getText();
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                if (chkSolStk.isSelected())
                    strAux+=" AND a1.nd_stkAct>0";
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Obtener los datos del "Grupo".
                    //Obtener el número total de registros.
                    strSQL="";
                    strSQL+="SELECT COUNT(*)";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT b1.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm, b1.tx_desCor";
                    for (j=0; j<intNumFilTblBod; j++)
                    {
                        if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                        {
                            strSQL+=", b" + (j+2) + ".nd_stkAct AS b" + (j+2) + "_nd_stkAct";
                        }
                        else
                        {
                            strSQL+=", Null AS b" + (j+2) + "_nd_stkAct";
                        }
                    }
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor";
                    strSQL+=" FROM tbm_inv AS a1";
                    strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                    strSQL+=" LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=strAux;
                    strSQL+=" ) AS b1";
                    for (j=0; j<intNumFilTblBod; j++)
                    {
                        if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                        {
                            strSQL+=" INNER JOIN (";
                            strSQL+=" SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.nd_stkAct";
                            strSQL+=" FROM tbm_invBod AS a1";
                            strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                            strSQL+=" WHERE a1.co_emp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP);
                            strSQL+=" AND a1.co_bod=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD);
                            strSQL+=" ) AS b" + (j+2) + " ON (b1.co_itmMae=b" + (j+2) + ".co_itmMae)";
                        }
                    }
                    strSQL+=" ) AS b1";
                    intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intNumTotReg==-1)
                        return false;
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT b1.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm, b1.tx_desCor";
                    for (j=0; j<intNumFilTblBod; j++)
                    {
                        if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                        {
                            strSQL+=", b" + (j+2) + ".nd_stkAct AS b" + (j+2) + "_nd_stkAct";
                        }
                        else
                        {
                            strSQL+=", Null AS b" + (j+2) + "_nd_stkAct";
                        }
                    }
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor";
                    strSQL+=" FROM tbm_inv AS a1";
                    strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                    strSQL+=" LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=strAux;
                    strSQL+=" ) AS b1";
                    for (j=0; j<intNumFilTblBod; j++)
                    {
                        if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                        {
                            strSQL+=" INNER JOIN (";
                            strSQL+=" SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.nd_stkAct";
                            strSQL+=" FROM tbm_invBod AS a1";
                            strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                            strSQL+=" WHERE a1.co_emp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP);
                            strSQL+=" AND a1.co_bod=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD);
                            strSQL+=" ) AS b" + (j+2) + " ON (b1.co_itmMae=b" + (j+2) + ".co_itmMae)";
                        }
                    }
                    strSQL+=" ORDER BY b1.tx_codAlt";
                 }
                else
                {
                    //Obtener los datos de la "Empresa seleccionada".
                    //Obtener el número total de registros.
                    strSQL="";
                    strSQL+="SELECT COUNT(*)";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT b1.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm, b1.tx_desCor";
                    for (j=0; j<intNumFilTblBod; j++)
                    {
                        if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                        {
                            strSQL+=", b" + (j+2) + ".nd_stkAct AS b" + (j+2) + "_nd_stkAct";
                        }
                        else
                        {
                            strSQL+=", Null AS b" + (j+2) + "_nd_stkAct";
                        }
                    }
                    strSQL+=" FROM (";
                    strSQL+=" SELECT eq.co_itmmae, a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor";
                    strSQL+=" FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS eq ON (a1.co_emp=eq.co_emp AND a1.co_itm=eq.co_itm) ";
                    strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=strAux;
                    strSQL+=" ) AS b1";
                    for (j=0; j<intNumFilTblBod; j++)
                    {
                        if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                        {
                            strSQL+=" INNER JOIN (";
                            strSQL+=" SELECT a1.co_emp, a1.co_itm, a1.nd_stkAct";
                            strSQL+=" FROM tbm_invBod AS a1";
                            strSQL+=" WHERE a1.co_emp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP);
                            strSQL+=" AND a1.co_bod=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD);
                            strSQL+=" ) AS b" + (j+2) + " ON (b1.co_emp=b" + (j+2) + ".co_emp AND b1.co_itm=b" + (j+2) + ".co_itm)";
                        }
                    }
                    strSQL+=" ) AS b1";
                    intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intNumTotReg==-1)
                        return false;
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT b1.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm, b1.tx_desCor";
                    for (j=0; j<intNumFilTblBod; j++)
                    {
                        if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                        {
                            strSQL+=", b" + (j+2) + ".nd_stkAct AS b" + (j+2) + "_nd_stkAct";
                        }
                        else
                        {
                            strSQL+=", Null AS b" + (j+2) + "_nd_stkAct";
                        }
                    }
                    strSQL+=" FROM (";
                    strSQL+=" SELECT eq.co_itmmae, a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor";
                    strSQL+=" FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS eq ON (a1.co_emp=eq.co_emp AND a1.co_itm=eq.co_itm) ";
                    strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=strAux;
                    strSQL+=" ) AS b1";
                    for (j=0; j<intNumFilTblBod; j++)
                    {
                        if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                        {
                            strSQL+=" INNER JOIN (";
                            strSQL+=" SELECT a1.co_emp, a1.co_itm, a1.nd_stkAct";
                            strSQL+=" FROM tbm_invBod AS a1";
                            strSQL+=" WHERE a1.co_emp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP);
                            strSQL+=" AND a1.co_bod=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD);
                            strSQL+=" ) AS b" + (j+2) + " ON (b1.co_emp=b" + (j+2) + ".co_emp AND b1.co_itm=b" + (j+2) + ".co_itm)";
                        }
                    }
                    strSQL+=" ORDER BY b1.tx_codAlt";
                }
                
                
                 
                 String strCodBod="";
                  for (j=0; j<intNumFilTblBod; j++)
                    {
                        if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                        {
                            strCodBod = objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD).toString();
                        }
                     }
                  
                 
                 int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
                 String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
                    
                 String   strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),"yyyy/MM/dd");
                        
                   
                strSQL = "SELECT * FROM ( "+strSQL+" ) AS x WHERE x.co_itmMae in (" +
                "" +
                " select * from (  " +
                " select a2.co_itmMae from tbm_inv as a " +
                " INNER JOIN tbm_equInv AS a2 ON (a.co_emp=a2.co_emp AND a.co_itm=a2.co_itm) " +
                " where a.co_emp="+txtcodemp.getText()+" and a.co_itm not in ( " +
                " select distinct(inv.co_itm) from tbm_inv as inv" +
                " inner join tbm_detmovinv as det on (det.co_emp=inv.co_emp and det.co_itm=inv.co_itm)" +
                " inner join tbm_cabmovinv as cab on (cab.co_emp=det.co_emp and cab.co_loc=det.co_loc and cab.co_tipdoc=det.co_tipdoc and cab.co_doc=det.co_doc)" +
                " where inv.co_emp="+txtcodemp.getText()+"   and cab.fe_doc between '"+strFecSql+"' and '"+strFecSis+"' " +
                " ) " +
                " ) as x" +
                " WHERE x.co_itmMae NOT IN (" +
                
                " select a2.co_itmMae from tbm_inv as a " +
                " INNER JOIN tbm_equInv AS a2 ON (a.co_emp=a2.co_emp AND a.co_itm=a2.co_itm) " +
                " where a.co_emp="+objParSis.getCodigoEmpresaGrupo()+" and a.co_itm  in ( " +
                " select distinct(inv.co_itm) from tbm_inv as inv" +
                " inner join tbm_detmovinv as det on (det.co_emp=inv.co_emp and det.co_itm=inv.co_itm)" +
                " inner join tbm_cabmovinv as cab on (cab.co_emp=det.co_emp and cab.co_loc=det.co_loc and cab.co_tipdoc=det.co_tipdoc and cab.co_doc=det.co_doc)" +
                " where inv.co_emp="+objParSis.getCodigoEmpresaGrupo()+" AND det.co_bod="+strCodBod+"   and cab.fe_doc between '"+strFecSql+"' and '"+strFecSis+"' " +
                " ) " +
           
                " ) "+
                
                
                " ) ";
                
                System.out.println(" >> "+ strSQL );
                     
                rst=stm.executeQuery(strSQL);
                
                  
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_MAE,rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_SIS,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_DEC_UNI,rst.getString("tx_desCor"));
                        for (j=0; j<intNumFilTblBod; j++)
                        {
                            vecReg.add(6+j,rst.getString("b" + (j+2) + "_nd_stkAct"));
                        }
                        vecDat.add(vecReg);
                        i++;
                        pgrSis.setValue(i);
                    }
                    else
                    {
                        break;
                    }
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                if (intNumTotReg==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
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
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
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
    private boolean mostrarVenConItm(int intTipBus)
    {
        String strAli, strCam;
        Librerias.ZafConsulta.ZafConsulta objVenCon;
        boolean blnRes=true;
        try
        {
            strAli="Código, Alterno, Nombre";
            strCam="a1.co_itm, a1.tx_codAlt, a1.tx_nomItm";
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            objVenCon=new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this), strAli, strCam, strSQL, "", objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objVenCon.setTitle("Listado de inventario");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    objVenCon.show();
                    if (objVenCon.acepto())
                    {
                        txtCodItm.setText(objVenCon.GetCamSel(1));
                        txtCodAlt.setText(objVenCon.GetCamSel(2));
                        txtNomItm.setText(objVenCon.GetCamSel(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (objVenCon.buscar("LOWER(a1.tx_codAlt) LIKE '" + txtCodAlt.getText().toLowerCase() + "'"))
                    {
                        txtCodItm.setText(objVenCon.GetCamSel(1));
                        txtCodAlt.setText(objVenCon.GetCamSel(2));
                        txtNomItm.setText(objVenCon.GetCamSel(3));
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtCodAlt.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(1);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodItm.setText(objVenCon.GetCamSel(1));
                            txtCodAlt.setText(objVenCon.GetCamSel(2));
                            txtNomItm.setText(objVenCon.GetCamSel(3));
                        }
                        else
                        {
                            txtCodItm.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (objVenCon.buscar("LOWER(a1.tx_nomItm) LIKE '" + txtNomItm.getText().toLowerCase() + "'"))
                    {
                        txtCodItm.setText(objVenCon.GetCamSel(1));
                        txtCodAlt.setText(objVenCon.GetCamSel(2));
                        txtNomItm.setText(objVenCon.GetCamSel(3));
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtNomItm.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(2);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodItm.setText(objVenCon.GetCamSel(1));
                            txtCodAlt.setText(objVenCon.GetCamSel(2));
                            txtNomItm.setText(objVenCon.GetCamSel(3));
                        }
                        else
                        {
                            txtNomItm.setText(strNomItm);
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
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblBodMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModBod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblBod.columnAtPoint(evt.getPoint())==INT_TBL_BOD_CHK)
            {
                if (blnMarTodChkTblBod)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBod.setChecked(true, i, INT_TBL_BOD_CHK);
                        //Configurar JTable: Mostrar la columna.
//                        tblDat.getColumnModel().getColumn(6 + i).setWidth(60);
//                        tblDat.getColumnModel().getColumn(6 + i).setMaxWidth(60);
//                        tblDat.getColumnModel().getColumn(6 + i).setMinWidth(60);
                        tblDat.getColumnModel().getColumn(6 + i).setPreferredWidth(60);
//                        tblDat.getColumnModel().getColumn(6 + i).setResizable(true);
                    }
                    blnMarTodChkTblBod=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBod.setChecked(false, i, INT_TBL_BOD_CHK);
                        //Configurar JTable: Ocultar la columna.
//                        tblDat.getColumnModel().getColumn(6 + i).setWidth(0);
//                        tblDat.getColumnModel().getColumn(6 + i).setMaxWidth(0);
//                        tblDat.getColumnModel().getColumn(6 + i).setMinWidth(0);
                        tblDat.getColumnModel().getColumn(6 + i).setPreferredWidth(0);
//                        tblDat.getColumnModel().getColumn(6 + i).setResizable(false);
                    }
                    blnMarTodChkTblBod=true;
                }
//                tblDat.updateUI();
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
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
                case INT_TBL_DAT_COD_MAE:
                    strMsg="Código maestro del item";
                    break;
                case INT_TBL_DAT_COD_SIS:
                    strMsg="Código del item (Sistema)";
                    break;
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_DEC_UNI:
                    strMsg="Unidad de medida";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaBod extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblBod.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_BOD_LIN:
                    strMsg="";
                    break;
                case INT_TBL_BOD_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_BOD_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_BOD_COD_BOD:
                    strMsg="Código de la bodega";
                    break;
                case INT_TBL_BOD_NOM_BOD:
                    strMsg="Nombre de la bodega";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblBod.getTableHeader().setToolTipText(strMsg);
        }
    }
    
//------------------------------------------------------------------------------
    private class ZafTblColModLis implements javax.swing.event.TableColumnModelListener
    {
        
        public void columnAdded(javax.swing.event.TableColumnModelEvent e)
        {
//            objTblMod.addColumn();
            System.out.println("columnAdded");
        }
        
        public void columnMarginChanged(javax.swing.event.ChangeEvent e) {
        }
        
        public void columnMoved(javax.swing.event.TableColumnModelEvent e) {
        }
        
        public void columnRemoved(javax.swing.event.TableColumnModelEvent e) {
        }
        
        public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e) {
        }
        
    }

}