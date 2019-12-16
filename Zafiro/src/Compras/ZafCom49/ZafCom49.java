/*
 * ZafCom49.java
 *
 * Created on 16 de enero de 2005, 17:10 PM
 */

package Compras.ZafCom49;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import java.sql.*;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
/**
 *
 * @author  Eddye Lino
 */
public class ZafCom49 extends javax.swing.JInternalFrame 
{
    //para bodegas
    static final int INT_TBL_BOD_LIN=0;                         //Línea.
    static final int INT_TBL_BOD_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_BOD_COD_BOD=2;                     //Código de la bodega.
    static final int INT_TBL_BOD_NOM_BOD=3;                     //Nombre de la bodega.

    //para empresas
    static final int INT_TBL_EMP_LIN=0;                         //Línea.
    static final int INT_TBL_EMP_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_EMP_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_EMP_NOM_EMP=3;                     //Nombre de la empresa.


    //Constantes: Columnas del JTable:
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_COD_EMP=1;
    final int INT_TBL_DAT_COD_LOC=2;
    final int INT_TBL_DAT_COD_TIP_DOC=3;
    final int INT_TBL_DAT_DES_COR=4;
    final int INT_TBL_DAT_DES_LAR=5;
    final int INT_TBL_DAT_COD_DOC=6;
    final int INT_TBL_DAT_NUM_DOC=7;
    final int INT_TBL_DAT_FEC_DOC=8;

    final int INT_TBL_DAT_COD_ITM=9;
    final int INT_TBL_DAT_COD_ALT_ITM=10;
    final int INT_TBL_DAT_COD_NOM_ITM=11;


    final int INT_TBL_DAT_COD_BOD=12;
    final int INT_TBL_DAT_NOM_BOD=13;
    final int INT_TBL_DAT_CAN_ING=14;
    final int INT_TBL_DAT_CAN_EGR=15;
    final int INT_TBL_DAT_OBS_1=16;
    final int INT_TBL_DAT_BUT_OBS_1=17;
    final int INT_TBL_DAT_OBS_2=18;
    final int INT_TBL_DAT_BUT_OBS_2=19;
    final int INT_TBL_DAT_COD_REG=20;

    
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod, objTblModBod, objTblModEmp;
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMen� en JTable.
    private ZafThreadGUI objThrGUI;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
   
    
    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnCon;                     //true: Continua la ejecuci�n del hilo.
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblBus objTblBus;
   
    private ZafSelFec objSelFec;

    private String strDesCorTipDoc, strDesLarTipDoc;
    private ZafVenCon vcoTipDoc;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenChk objTblCelRenChk;

    private boolean blnButCon, blnButCer, blnButGua;
    private ZafPerUsr objPerUsr;
    private ZafMouMotAdaEmp objMouMotAdaEmp;                  //ToolTipText en TableHeader de empresas.
    private ZafMouMotAdaBod objMouMotAdaBod;                  //ToolTipText en TableHeader de bodegas.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.


    private boolean blnMarTodChkTblBod=true, blnMarTodChkTblEmp=true;            //Marcar todas las casillas de verificación del JTable de bodegas.
    private ZafTblFilCab objTblFilCab;
    private String strCodAlt, strNomItm;
    private ZafVenCon vcoItm;                           //Ventana de consulta "Item".
    private String strTipDocUsr;
    private String strCodBodChk, strCodEmpChk;
    private ZafTblCelRenBut objTblCelRenButObs1, objTblCelRenButObs2;
    private ZafTblCelEdiButDlg objTblCelEdiButObs1, objTblCelEdiButObs2;

    private ZafCom49_01 objCom49_01, objCom49_02;

    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCom49(ZafParSis obj) 
    {
        try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            objPerUsr=new ZafPerUsr(objParSis);

            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
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
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panCorRpt = new javax.swing.JPanel();
        panTblFil = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        panLisEmp = new javax.swing.JPanel();
        spnEmp = new javax.swing.JScrollPane();
        tblEmp = new javax.swing.JTable();
        panLisBod = new javax.swing.JPanel();
        spnBod = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        optFilReg = new javax.swing.JRadioButton();
        optTodReg = new javax.swing.JRadioButton();
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAlt = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lblCodAltItmDes = new javax.swing.JLabel();
        txtCodAltItmHas = new javax.swing.JTextField();
        lblCodAltItmHas = new javax.swing.JLabel();
        txtCodAltItmDes = new javax.swing.JTextField();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTotal = new javax.swing.JScrollPane();
        tblTotal = new javax.swing.JTable();
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
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(new java.awt.BorderLayout());

        panCorRpt.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panCorRpt.setPreferredSize(new java.awt.Dimension(560, 90));
        panCorRpt.setLayout(new java.awt.BorderLayout());
        panFil.add(panCorRpt, java.awt.BorderLayout.NORTH);
        panCorRpt.getAccessibleContext().setAccessibleName("Codigo");

        panTblFil.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(350);
        jSplitPane1.setOneTouchExpandable(true);

        panLisEmp.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de empresas"));
        panLisEmp.setLayout(new java.awt.BorderLayout());

        tblEmp.setModel(new javax.swing.table.DefaultTableModel(
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
        spnEmp.setViewportView(tblEmp);

        panLisEmp.add(spnEmp, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(panLisEmp);

        panLisBod.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de bodegas"));
        panLisBod.setLayout(new java.awt.BorderLayout());

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

        panLisBod.add(spnBod, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(panLisBod);

        panTblFil.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        panFil.add(panTblFil, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(0, 64));
        jPanel1.setLayout(null);

        optFilReg.setText("Sólo los documentos que cumplan el criterio seleccionado");
        jPanel1.add(optFilReg);
        optFilReg.setBounds(0, 20, 350, 16);

        optTodReg.setSelected(true);
        optTodReg.setText("Todos los documentos");
        optTodReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodRegActionPerformed(evt);
            }
        });
        jPanel1.add(optTodReg);
        optTodReg.setBounds(0, 3, 340, 16);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Bodega en la que se debe hacer el conteo");
        jPanel1.add(lblItm);
        lblItm.setBounds(30, 40, 50, 16);
        jPanel1.add(txtCodItm);
        txtCodItm.setBounds(40, 40, 40, 20);

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
        jPanel1.add(txtCodAlt);
        txtCodAlt.setBounds(80, 40, 80, 20);

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
        jPanel1.add(txtNomItm);
        txtNomItm.setBounds(160, 40, 280, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        jPanel1.add(butItm);
        butItm.setBounds(440, 40, 20, 20);

        jPanel3.add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        jPanel4.setPreferredSize(new java.awt.Dimension(100, 42));
        jPanel4.setLayout(null);

        lblCodAltItmDes.setText("Desde:");
        jPanel4.add(lblCodAltItmDes);
        lblCodAltItmDes.setBounds(30, 20, 60, 14);

        txtCodAltItmHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusLost(evt);
            }
        });
        jPanel4.add(txtCodAltItmHas);
        txtCodAltItmHas.setBounds(300, 16, 110, 20);

        lblCodAltItmHas.setText("Hasta:");
        jPanel4.add(lblCodAltItmHas);
        lblCodAltItmHas.setBounds(240, 20, 60, 14);

        txtCodAltItmDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusLost(evt);
            }
        });
        jPanel4.add(txtCodAltItmDes);
        txtCodAltItmDes.setBounds(90, 16, 110, 20);

        jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);

        panFil.add(jPanel3, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

        spnDat.setPreferredSize(new java.awt.Dimension(453, 418));

        tblDat.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblDat.setMaximumSize(new java.awt.Dimension(2147483647, 192));
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTotal.setPreferredSize(new java.awt.Dimension(320, 35));

        tblTotal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        spnTotal.setViewportView(tblTotal);

        panRpt.add(spnTotal, java.awt.BorderLayout.SOUTH);

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

        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
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

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
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

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    
                        
    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acci�n de acuerdo a la etiqueta del bot�n ("Consultar" o "Detener").
        objTblMod.removeAllRows();
        lblMsgSis.setText("");
        if(isCamVal()){
            if (butCon.getText().equals("Consultar")){
                blnCon=true;
                if (objThrGUI==null){
                    objThrGUI=new ZafThreadGUI();
                    objThrGUI.start();
                }
            }
            else{
                blnCon=false;
            }
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicaci�n. */
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

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        // TODO add your handling code here:
        txtCodAlt.transferFocus();
}//GEN-LAST:event_txtCodAltActionPerformed

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
        // TODO add your handling code here:
        strCodAlt=txtCodAlt.getText();
        txtCodAlt.selectAll();
}//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodAlt)) {
            if (txtCodAlt.getText().equals("")) {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtNomItm.setText("");
            } else {
                mostrarVenConItm(1);
            }
        } else
            txtCodAlt.setText(strCodAlt);

        if(txtCodAlt.getText().length()>0){
            optFilReg.setSelected(true);
            optTodReg.setSelected(false);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
        }
}//GEN-LAST:event_txtCodAltFocusLost

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        // TODO add your handling code here:
        txtNomItm.transferFocus();
}//GEN-LAST:event_txtNomItmActionPerformed

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        // TODO add your handling code here:
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
}//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm)) {
            if (txtNomItm.getText().equals("")) {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtNomItm.setText("");
            } else {
                mostrarVenConItm(2);
            }
        } else
            txtNomItm.setText(strNomItm);

        if(txtNomItm.getText().length()>0){
            optFilReg.setSelected(true);
            optTodReg.setSelected(false);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
        }
}//GEN-LAST:event_txtNomItmFocusLost

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        // TODO add your handling code here:
        mostrarVenConItm(0);
        if(txtNomItm.getText().length()>0){
            optFilReg.setSelected(true);
            optTodReg.setSelected(false);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
        }
}//GEN-LAST:event_butItmActionPerformed

    private void txtCodAltItmHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmHasFocusGained
        // TODO add your handling code here:
        txtCodAltItmHas.selectAll();
}//GEN-LAST:event_txtCodAltItmHasFocusGained

    private void txtCodAltItmHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmHasFocusLost
        // TODO add your handling code here:
        if (txtCodAltItmHas.getText().length()>0){
            optFilReg.setSelected(true);
            optTodReg.setSelected(false);
        }
}//GEN-LAST:event_txtCodAltItmHasFocusLost

    private void txtCodAltItmDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusGained
        // TODO add your handling code here:
        txtCodAltItmDes.selectAll();
}//GEN-LAST:event_txtCodAltItmDesFocusGained

    private void txtCodAltItmDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusLost
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (txtCodAltItmDes.getText().length()>0) {
            optFilReg.setSelected(true);
            optTodReg.setSelected(false);

            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtNomItm.setText("");

            if (txtCodAltItmHas.getText().length()==0)
                txtCodAltItmHas.setText(txtCodAltItmDes.getText());
        }
}//GEN-LAST:event_txtCodAltItmDesFocusLost

    private void optTodRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodRegActionPerformed
        // TODO add your handling code here:
        if(optTodReg.isSelected()){
            optFilReg.setSelected(false);
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtNomItm.setText("");
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");


         //   adicionar el seteo de campos a blancos
        }
        else{
            optFilReg.setSelected(true);
        }
    }//GEN-LAST:event_optTodRegActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        // TODO add your handling code here:
        if(guardar()){
            mostrarMsgInf("<HTML>La información se guardó correctamente.</HTML>");
        }
        else{
            mostrarMsgInf("<HTML>La información no se pudo guardar.</HTML>");
        }

    }//GEN-LAST:event_butGuaActionPerformed




    /** Cerrar la aplicaci�n. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butItm;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblCodAltItmDes;
    private javax.swing.JLabel lblCodAltItmHas;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFilReg;
    private javax.swing.JRadioButton optTodReg;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCorRpt;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLisBod;
    private javax.swing.JPanel panLisEmp;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panTblFil;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBod;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnEmp;
    private javax.swing.JScrollPane spnTotal;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblEmp;
    private javax.swing.JTable tblTotal;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAltItmDes;
    private javax.swing.JTextField txtCodAltItmHas;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtNomItm;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            blnButCon=false;
            blnButCer=false;
            blnButGua=false;
            
            if(objPerUsr.isOpcionEnabled(2194)){
                blnButCon=true;
            }
            if(objPerUsr.isOpcionEnabled(2560)){
                blnButGua=true;
            }
            if(objPerUsr.isOpcionEnabled(2195)){
                blnButCer=true;
            }

            strAux=objParSis.getNombreMenu() + "v0.1.2";
            this.setTitle(strAux);
            lblTit.setText(strAux);
            
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            panCorRpt.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);


            objSelFec.setFechaDesde(getMesAnterior(objSelFec.getFechaHasta()));
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(21);    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cod.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cod.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cod.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR,"Tip.Doc");
            vecCab.add(INT_TBL_DAT_DES_LAR,"Tipo de Documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cod.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Num.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_COD_NOM_ITM,"Nom.Itm.");

            vecCab.add(INT_TBL_DAT_COD_BOD,"Cód.Bod.");
            vecCab.add(INT_TBL_DAT_NOM_BOD,"Bodega");
            vecCab.add(INT_TBL_DAT_CAN_ING,"Ingreso");
            vecCab.add(INT_TBL_DAT_CAN_EGR,"Egreso");
            vecCab.add(INT_TBL_DAT_OBS_1,"Observación1");
            vecCab.add(INT_TBL_DAT_BUT_OBS_1,"");
            vecCab.add(INT_TBL_DAT_OBS_2,"Observación2");
            vecCab.add(INT_TBL_DAT_BUT_OBS_2,"");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");

            


            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);            

            //Configurar JTable: Establecer tipo de selecci�n.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el men� de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Tama�o de las celdas
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_NOM_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_BOD).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_OBS_1).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS_1).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_OBS_2).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS_2).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(20);



            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);                       
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setCellRenderer(objTblCelRenLbl);

                                              
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setResizable(false);

            

            objTblBus=new ZafTblBus(tblDat);
            objTblOrd=new ZafTblOrd(tblDat);
            

            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_OBS_1);
            vecAux.add("" + INT_TBL_DAT_BUT_OBS_2);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            configurarVenConItm();
            configurarTblBod();
            cargarBod();

            configurarTblEmp();
            cargarEmp();

            objTblCelRenButObs1=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS_1).setCellRenderer(objTblCelRenButObs1);
            objCom49_01=new ZafCom49_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, "N");
            objTblCelEdiButObs1= new ZafTblCelEdiButDlg(objCom49_01);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS_1).setCellEditor(objTblCelEdiButObs1);

            objTblCelEdiButObs1.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strObs1="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblCelEdiButObs1.setCancelarEdicion(false);
                    strObs1=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_OBS_1)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_OBS_1).toString();
                    objCom49_01.setContenido("" + strObs1);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });

            objTblCelRenButObs2=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS_2).setCellRenderer(objTblCelRenButObs2);
            objCom49_02=new ZafCom49_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, "S");
            objTblCelEdiButObs2= new ZafTblCelEdiButDlg(objCom49_02);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS_2).setCellEditor(objTblCelEdiButObs2);

            objTblCelEdiButObs2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strObs2="";
                String strNueObs="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblCelEdiButObs2.setCancelarEdicion(false);
                    strObs2=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_OBS_2)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_OBS_2).toString();
                    objCom49_02.setContenido("" + strObs2);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strNueObs=objCom49_02.getContenido();
                    objTblMod.setValueAt(strNueObs, tblDat.getSelectedRow(), INT_TBL_DAT_OBS_2);
                }
            });

            tcmAux=null;

            txtCodItm.setVisible(false);
            txtCodItm.setEditable(false);


            //Configurar JTable: Ocultar columnas del sistema.
//            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
//                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
//            }
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a utilizar
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
     * Esta clase crea un hilo que permite manipular la interface gr�fica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que est� ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podr�a presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estar�a informado en todo
     * momento de lo que ocurre. Si se desea hacer �sto es necesario utilizar �sta clase
     * ya que si no s�lo se apreciar�a los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable s�lo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }



    private boolean getTipDocCab(){
        boolean blnRes=true;
        strTipDocUsr="";
        try{
            if(con!=null){
                stm=con.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+="SELECT co_tipdoc FROM tbr_tipdocprg";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_tipdoc FROM tbr_tipdocprg AS a1";
                    strSQL+=" INNER JOIN tbr_tipdocusr AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_mnu=a2.co_mnu";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                }
                rst=stm.executeQuery(strSQL);
                for(int i=0; rst.next();i++){
                    if(i==0)
                        strTipDocUsr="" + (rst.getObject("co_tipdoc")==null?"":rst.getString("co_tipdoc"));
                    else
                        strTipDocUsr+="," + (rst.getObject("co_tipdoc")==null?"":rst.getString("co_tipdoc"));
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;

            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }







    /**
     * Esta función permite establecer la conexión para consultas DML
     * @return true: Si se pudo establecer conexión y cargar datos.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(){
        int i;
        boolean blnRes=true;
        
        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            
            if (con!=null){
                
                switch (objSelFec.getTipoSeleccion()){
                    case 0: //B�squeda por rangos
                        strAux=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strAux=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strAux=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }

                if(!  txtCodItm.getText().toString().equals(""))
                    strAux+="   AND a3.co_itm=" + txtCodItm.getText()  + "";

                if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a3.tx_codAlt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a3.tx_codAlt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";


                
                stm=con.createStatement();

                strSQL="";
                strSQL+=" SELECT x.*FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a2.tx_descor,";
                strSQL+=" 	a2.tx_deslar,a1.co_doc, a3.co_reg, a1.ne_numDoc, a1.fe_doc";
                strSQL+=" 	, a3.co_itm, a3.tx_codalt, a3.tx_nomitm";
                strSQL+=" 	, a3.co_bod, b1.tx_nom,";
                strSQL+="       CASE WHEN a3.nd_can<0 THEN abs(a3.nd_can) END AS nd_canEgr,";
                strSQL+="       CASE WHEN a3.nd_can>0 THEN abs(a3.nd_can) END AS nd_canIng";
                strSQL+="       , a1.tx_obs1, a1.tx_obs2";
                strSQL+=" 	FROM (tbm_cabmovinv AS a1 INNER JOIN tbm_cabtipdoc AS a2";
                strSQL+=" 	 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc)";
                strSQL+=" 	INNER JOIN (tbm_detMovInv AS a3 INNER JOIN tbm_bod AS b1 ON a3.co_emp=b1.co_emp AND a3.co_bod=b1.co_bod)";
                strSQL+=" 	 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipdoc=a3.co_tipdoc AND a1.co_doc=a3.co_doc";
                strSQL+=" 	WHERE a1.co_tipdoc IN (" + strTipDocUsr + ")";

                strSQL+=" 	AND a1.co_emp IN(" + strCodEmpChk + ")";


                strSQL+=" 	AND a1.st_reg NOT IN('E','I')";
                strSQL+="" + strAux;
                strSQL+=" 	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a2.tx_descor,";
                strSQL+=" 	a2.tx_deslar,a1.co_doc, a3.co_reg, a1.ne_numDoc, a1.ne_numDoc, a1.fe_doc";
                strSQL+=" 	, a3.co_itm, a3.tx_codalt, a3.tx_nomitm";
                strSQL+=" 	, a3.co_bod, b1.tx_nom, a3.nd_can, a1.tx_obs1, a1.tx_obs2";
                strSQL+=" 	order by a1.ne_numDoc, a1.co_tipdoc, a1.co_doc) AS x";
                strSQL+=" 	INNER JOIN (";
                strSQL+=" 	SELECT co_emp, co_bod FROM tbr_bodEmpBodGrp";
                strSQL+=" 	WHERE co_bodGrp IN(";
                strSQL+=" 		 SELECT co_bodGrp FROM tbr_bodEmpBodGrp";

                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                   strSQL+=" 		WHERE co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + "";
                   strSQL+="                 AND co_bodGrp IN(" + strCodBodChk + "))";
                }
                else{
                    strSQL+=" 		 WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 		 and co_bod IN(" + strCodBodChk + "))";
                }

                


                strSQL+=" ) AS y";
                strSQL+=" ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod";
                strSQL+=" ORDER BY x.fe_doc, x.ne_numDoc,x.co_tipdoc, x.co_doc";

                System.out.println("SQL cargarDetReg: " + strSQL);
                rst=stm.executeQuery(strSQL);

                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                //pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;                

                lblMsgSis.setText("Listo");
                while (rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_EMP,     "" + rst.getObject("co_emp")==null?"":rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,     "" + rst.getObject("co_loc")==null?"":rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC, "" + rst.getObject("co_tipdoc")==null?"":rst.getString("co_tipdoc"));
                        vecReg.add(INT_TBL_DAT_DES_COR,     "" + rst.getObject("tx_descor")==null?"":rst.getString("tx_descor"));
                        vecReg.add(INT_TBL_DAT_DES_LAR,     "" + rst.getObject("tx_deslar")==null?"":rst.getString("tx_deslar"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,     "" + rst.getObject("co_doc")==null?"":rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,     "" + rst.getObject("ne_numDoc")==null?"":rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,     "" + rst.getObject("fe_doc")==null?"":rst.getString("fe_doc"));
                        
                        vecReg.add(INT_TBL_DAT_COD_ITM,     "" + rst.getObject("co_itm")==null?"":rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM, "" + rst.getObject("tx_codalt")==null?"":rst.getString("tx_codalt"));
                        vecReg.add(INT_TBL_DAT_COD_NOM_ITM, "" + rst.getObject("tx_nomitm")==null?"":rst.getString("tx_nomitm"));

                        vecReg.add(INT_TBL_DAT_COD_BOD,     "" + rst.getObject("co_bod")==null?"":rst.getString("co_bod"));
                        vecReg.add(INT_TBL_DAT_NOM_BOD,     "" + rst.getObject("tx_nom")==null?"":rst.getString("tx_nom"));
                        vecReg.add(INT_TBL_DAT_CAN_ING,     "" + rst.getObject("nd_canIng")==null?"":rst.getString("nd_canIng"));
                        vecReg.add(INT_TBL_DAT_CAN_EGR,     "" + rst.getObject("nd_canEgr")==null?"":rst.getString("nd_canEgr"));
                        vecReg.add(INT_TBL_DAT_OBS_1,       "" + rst.getObject("tx_obs1")==null?"":rst.getString("tx_obs1"));
                        vecReg.add(INT_TBL_DAT_BUT_OBS_1,   "" + rst.getObject("tx_obs1")==null?"":rst.getString("tx_obs1"));
                        vecReg.add(INT_TBL_DAT_OBS_2,       "" + rst.getObject("tx_obs2")==null?"":rst.getString("tx_obs2"));
                        vecReg.add(INT_TBL_DAT_BUT_OBS_2,   "" + rst.getObject("tx_obs2")==null?"":rst.getString("tx_obs2"));
                        vecReg.add(INT_TBL_DAT_COD_REG,   "" + rst.getObject("co_reg")==null?"":rst.getString("co_reg"));


                        vecDat.add(vecReg);
                        i++;
                        pgrSis.setValue(i);                        
                    }
                    else{
                        break;
                    }                    
                }
                
                rst.close();
                stm.close();
                rst=null;
                stm=null;

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);                
                pgrSis.setValue(0);
                butCon.setText("Consultar");
                lblMsgSis.setText("Se encontraron " + objTblMod.getRowCountTrue() + " registros.");
//                objTblTot.calcularTotales();

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
     * Esta función permite establecer la conexión
     * @return true: Si se pudo establecer conexión y cargar datos.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if(getTipDocCab()){
                    if(cargarDetReg()){
                        objTblMod.initRowsState();
                    }
                }
                con.close();
                con=null;
            }            
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función obtiene la "fecha desde" que se presenta en el formulario.
     * @param fechaActual La fecha del presente día.
     * @return String: Contiene la fecha del primer día del mes anterior.
     */
    private String getMesAnterior(String fechaActual){
        Connection conMesAnt;
        Statement stmMesAnt;
        ResultSet rstMesAnt;
        String strMesAnt="";
        try{
            conMesAnt=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conMesAnt!=null){
                stmMesAnt=conMesAnt.createStatement();
                strSQL="";
                strSQL+=" select '01/' ||";
                strSQL+=" case when extract('month' FROM cast('";
                strSQL+="" + objUti.formatearFecha(fechaActual, "dd/MM/yyyy", "yyyy-MM-dd") + "";
                strSQL+=" ' as date))<10 then '0'||extract('month' FROM cast('";
                strSQL+="" + objUti.formatearFecha(fechaActual, "dd/MM/yyyy", "yyyy-MM-dd") + "";
                strSQL+=" ' as date))";
                strSQL+=" else ''||extract('month' FROM cast('";
                strSQL+="" + objUti.formatearFecha(fechaActual, "dd/MM/yyyy", "yyyy-MM-dd") + "";
                strSQL+=" ' as date)) end";
                strSQL+=" || '/'  ||";
                strSQL+=" extract('year' FROM cast('";
                strSQL+="" + objUti.formatearFecha(fechaActual, "dd/MM/yyyy", "yyyy-MM-dd") + "";
                strSQL+=" ' as date))  as fechaInicial";

                rstMesAnt=stmMesAnt.executeQuery(strSQL);
                if(rstMesAnt.next()){
                    strMesAnt=rstMesAnt.getString("fechaInicial");
                }
                conMesAnt.close();
                conMesAnt=null;
                stmMesAnt.close();
                stmMesAnt=null;
                rstMesAnt.close();
                rstMesAnt=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return objUti.formatearFecha(strMesAnt,"dd/MM/yyyy","dd/MM/yyyy");
    }

    /**
     * Esta funci�n determina si los campos son v�lidos.
     * @return true: Si los campos son v�lidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        strCodBodChk="";
        int intConChk=0;
        //Validar bodega
        if (objTblModBod.getRowCountChecked(INT_TBL_BOD_CHK)<=0){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Se debe seleccionar al menos una bodega para realizar la consulta<BR>Seleccione una bodega y vuelva a intentarlo.</HTML>");
            return false;
        }
        for(int i=0;i<objTblModBod.getRowCountTrue(); i++){
            if(objTblModBod.isChecked(i, INT_TBL_BOD_CHK)){
                if(intConChk==0){
                    strCodBodChk=objTblModBod.getValueAt(i, INT_TBL_BOD_COD_BOD).toString();
                    intConChk++;
                }
                else{
                     strCodBodChk+="," + objTblModBod.getValueAt(i, INT_TBL_BOD_COD_BOD).toString();
                }
            }
        }

        intConChk=0;
        //validar empresa
        if (objTblModEmp.getRowCountChecked(INT_TBL_EMP_CHK)<=0){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Se debe seleccionar al menos una empresa para realizar la consulta<BR>Seleccione una empresa y vuelva a intentarlo.</HTML>");
            return false;
        }
        for(int i=0;i<objTblModEmp.getRowCountTrue(); i++){
            if(objTblModEmp.isChecked(i, INT_TBL_EMP_CHK)){
                if(intConChk==0){
                    strCodEmpChk=objTblModEmp.getValueAt(i, INT_TBL_EMP_COD_EMP).toString();
                    intConChk++;
                }
                else{
                     strCodEmpChk+="," + objTblModEmp.getValueAt(i, INT_TBL_EMP_COD_EMP).toString();
                }
            }
        }


        

        return true;
    }


    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Codigo de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Codigo del Local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_COR:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_LAR:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;

                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código del item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_NOM_ITM:
                    strMsg="Nombre del item";
                    break;

                case INT_TBL_DAT_COD_BOD:
                    strMsg="Código de la bodega";
                    break;
                case INT_TBL_DAT_NOM_BOD:
                    strMsg="Nombre de la bodega";
                    break;
                case INT_TBL_DAT_CAN_ING:
                    strMsg="Cantidad ingresada";
                    break;
                case INT_TBL_DAT_CAN_EGR:
                    strMsg="Cantidad egresada";
                    break;
                case INT_TBL_DAT_OBS_1:
                    strMsg="Observación1";
                    break;
                case INT_TBL_DAT_OBS_2:
                    strMsg="Observación2";
                    break;
                default:
                    strMsg="";
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
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAdaBod extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblBod.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_BOD_COD_BOD:
                    strMsg="Codigo de la bodega";
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


    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAdaEmp extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblEmp.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_EMP_COD_EMP:
                    strMsg="Codigo de la empresa";
                    break;
                case INT_TBL_EMP_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblEmp.getTableHeader().setToolTipText(strMsg);
        }
    }



    /**
     * Esta función configura el JTable "tblBod".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblEmp()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(4);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_EMP_LIN,"");
            vecCab.add(INT_TBL_EMP_CHK,"");
            vecCab.add(INT_TBL_EMP_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_EMP_NOM_EMP,"Empresa");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModEmp=new ZafTblMod();
            objTblModEmp.setHeader(vecCab);
            tblEmp.setModel(objTblModEmp);
            //Configurar JTable: Establecer tipo de selección.
            tblEmp.setRowSelectionAllowed(true);
            tblEmp.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblEmp);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblEmp.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblEmp.getColumnModel();
            tcmAux.getColumn(INT_TBL_EMP_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_EMP_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_EMP_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_EMP_NOM_EMP).setPreferredWidth(231);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_EMP_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblEmp.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblEmp.getTableHeader().addMouseMotionListener(new ZafMouMotAdaEmp());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblEmp.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblEmpMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_EMP_CHK);
            objTblModEmp.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblEmp);
            tcmAux.getColumn(INT_TBL_EMP_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_EMP_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_EMP_COD_EMP).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblEmp);
            tcmAux.getColumn(INT_TBL_EMP_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            //Configurar JTable: Establecer el modo de operación.
            objTblModEmp.setModoOperacion(objTblModEmp.INT_TBL_EDI);
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
     * Esta función configura el JTable "tblBod".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblBod()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(4);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_BOD_LIN,"");
            vecCab.add(INT_TBL_BOD_CHK,"");
            vecCab.add(INT_TBL_BOD_COD_BOD,"Cód.Bod.");
            vecCab.add(INT_TBL_BOD_NOM_BOD,"Bodega");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBod=new ZafTblMod();
            objTblModBod.setHeader(vecCab);
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
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_NOM_BOD).setPreferredWidth(231);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BOD_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBod.getTableHeader().setReorderingAllowed(false);
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
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
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
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblBodMouseClicked(java.awt.event.MouseEvent evt){
        int i, intNumFil;
        try{
            intNumFil=objTblModBod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblBod.columnAtPoint(evt.getPoint())==INT_TBL_BOD_CHK){
                if (blnMarTodChkTblBod){
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++){
                        objTblModBod.setChecked(true, i, INT_TBL_BOD_CHK);
                    }
                    blnMarTodChkTblBod=false;
                }
                else{
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++){
                        objTblModBod.setChecked(false, i, INT_TBL_BOD_CHK);
                    }
                    blnMarTodChkTblBod=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }


    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la empresa seleccionada en el el JTable de empresas.
     */
    private void tblEmpMouseClicked(java.awt.event.MouseEvent evt){
        int i, intNumFil;
        try{
            intNumFil=objTblModEmp.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblEmp.columnAtPoint(evt.getPoint())==INT_TBL_EMP_CHK){
                if (blnMarTodChkTblEmp){
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++){
                        objTblModEmp.setChecked(true, i, INT_TBL_EMP_CHK);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else{
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++){
                        objTblModEmp.setChecked(false, i, INT_TBL_EMP_CHK);
                    }
                    blnMarTodChkTblEmp=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }





    /**
     * Esta función permite consultar las bodegas de acuerdo al siguiente criterio:
     * El listado de bodegas se presenta en función de la empresa a la que se ingresa (Empresa Grupo u otra empresa)
     * , el usuario que ingresa (Administrador u otro usuario) y el menú desde el cual es llamado  (237, 886 o 907).
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarBod(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    if (objParSis.getCodigoUsuario()==1){
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                        strSQL+=" FROM tbm_emp AS a1";
                        strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                        rst=stm.executeQuery(strSQL);
                    }
                    else{
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                        strSQL+=" FROM tbm_emp AS a1";
                        strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                        strSQL+=" INNER JOIN tbr_bodLocPrgUsr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod)";
                        strSQL+=" WHERE a3.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                        strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario();
                        strSQL+=" AND a3.st_reg IN ('A','P')";
                        strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                        rst=stm.executeQuery(strSQL);
                    }

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_BOD_LIN,"");
                    vecReg.add(INT_TBL_BOD_CHK,new Boolean(true));
                    vecReg.add(INT_TBL_BOD_COD_BOD,rst.getString("co_bod"));
                    vecReg.add(INT_TBL_BOD_NOM_BOD,rst.getString("a2_tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModBod.setData(vecDat);
                tblBod.setModel(objTblModBod);
                vecDat.clear();
                blnMarTodChkTblBod=false;
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
     * Esta función permite consultar las empresas de acuerdo al siguiente criterio:
     * El listado de empresas se presenta en función al permiso que tenga el usuario en tbr_usrEmp
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarEmp(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las empresas.
                    if (objParSis.getCodigoUsuario()==1){
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.tx_nom";
                        strSQL+=" FROM tbm_emp AS a1";
                        //strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" ORDER BY a1.co_emp, a1.tx_nom";
                        rst=stm.executeQuery(strSQL);
                    }
                    else{
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.tx_nom";
                        strSQL+=" FROM tbm_emp AS a1";
                        strSQL+=" INNER JOIN tbr_usremp AS a2 ON a1.co_emp=a2.co_emp";
                        //strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" WHERE a2.co_usr=" + objParSis.getCodigoUsuario();
                        strSQL+=" AND a1.st_reg NOT IN ('E','I') AND a2.st_reg NOT IN ('E','I')";
                        strSQL+=" ORDER BY a1.co_emp, a2.tx_nom";
                        rst=stm.executeQuery(strSQL);
                    }

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_EMP_LIN,"");
                    vecReg.add(INT_TBL_EMP_CHK,new Boolean(true));
                    vecReg.add(INT_TBL_EMP_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_EMP_NOM_EMP,rst.getString("tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModEmp.setData(vecDat);
                tblEmp.setModel(objTblModEmp);
                vecDat.clear();
                blnMarTodChkTblEmp=false;
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("d1.co_itm");
            arlCam.add("d1.tx_codAlt");
            arlCam.add("d1.tx_nomItm");
            arlCam.add("d4.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Alterno");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("350");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" ORDER BY a1.tx_codAlt";
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
        }
        catch (Exception e)
        {
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
    private boolean mostrarVenConItm(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.setVisible(true);
                    if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
                        }
                        else
                        {
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
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
     * Esta función establece la conexión y contiene las funciones que insertan y modifican campos de auditoria.
     * @return true: Si se pudo modificar.
     * <BR>false: En el caso contrario.
     */
    private boolean guardar(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                if(guardarDatos()){
                    con.commit();
                }
                else{
                    con.rollback();
                    blnRes=false;
                }
                con.close();
                con=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


    /**
     * Esta función permite actualizar en tbm_cabDia los campos para consignación bancaria.
     * @return true: Si se pudo modificar.
     * <BR>false: En el caso contrario.
     */
    private boolean guardarDatos(){
        boolean blnRes=true;
        String strLin="";
        String strUpd="";
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                    if(strLin.equals("M")){
                        strSQL="";
                        strSQL+="UPDATE tbm_cabMovInv";
                        strSQL+=" SET tx_obs2=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_OBS_2)) + "";
                        strSQL+=" WHERE co_emp=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP) + "";
                        strSQL+=" AND co_loc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC) + "";
                        strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                        strSQL+=" AND co_doc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC) + "";
                        strSQL+=";";
                        strUpd+=strSQL;
                    }
                }
                System.out.println("strUpd: " + strUpd);
                stm.executeUpdate(strUpd);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


}