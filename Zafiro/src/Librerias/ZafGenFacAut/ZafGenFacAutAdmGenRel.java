/*
 * ZafMae20.java
 *
 * Created on 19 Agosto , 2013, 8:44 PM
 */

package Librerias.ZafGenFacAut;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafMovIngEgrInv.ZafReaMovInv;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.UltDocPrint;
import Librerias.ZafUtil.ZafCtaCtb_dat;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;



/**
 *
 * @author  José Marín 
 */
public class ZafGenFacAutAdmGenRel extends javax.swing.JInternalFrame {
    
    private ZafParSis objParSis;
    private ZafUtil objUti;//Objeto del tipo de la clase ZafUtil, el cual me va a permitir 
    private ZafTblMod objTblMod;
    private Connection con;
    private ZafGenFacAut objGenFacAut;
    private ZafReaMovInv objReaMovInv;
   private ZafCorEle objCorEle;
   
   //Tabla 
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_COD_ITM=1;
    private final int INT_TBL_DAT_COD_ALT_ITM=2;
    private final int INT_TBL_DAT_COS_ITM=3;
    private final int INT_TBL_DAT_MAR_GAN=4;
    private final int INT_TBL_DAT_PRE_VEN=5;
    private final int INT_TBL_DAT_TOT=6;
    private final int INT_TBL_DAT_CHK_VAL=7;
    private final int INT_TBL_DAT_CAN_DIS=8;
    private final int INT_TBL_DAT_VENDIDOS=9;
     private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenLbl objTblCelRenLblCod;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblPopMnu objTblPopMnu;
    private ZafColNumerada objColNum;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChkPre; 
    private String strVersion="v 0.1";
    
    private String strSQL, strAux, strSql,strNomBodSolTra;
    private Vector vecDat, vecReg, vecCab, vecAux;
    ArrayList arlDat = new ArrayList();
    ArrayList arlReg = new ArrayList();
    private double dblMarGan = 0.05;
    private Date datFecDoc; 
    
     private int intCodMnuFac = 14, intCodTipDocFacEle=228, intCodTipDocOC=2, intCodDoc, intCodDocOC; /* MENU DE FATURACION DE VENTAS */
     private String strFecSis,strFecSistema, strFecModCot,   strFecSisBase  ;
     private UltDocPrint objUltDocPrint;                                         // Para trabajar con la informacion de tipo de documento
    
    
        public ZafGenFacAutAdmGenRel(ZafParSis obj) {
            try{
                objParSis=obj;
                objGenFacAut = new ZafGenFacAut(objParSis,this);
                objReaMovInv = new ZafReaMovInv(objParSis, this);
                objCorEle = new ZafCorEle(objParSis);
                objUltDocPrint = new UltDocPrint(objParSis);
                initComponents();
                configurarFrm();
                
                 
            }
            catch (Exception e){
                this.setTitle(this.getTitle() + " [ERROR]");
            }

        }
        
 
    
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            this.setTitle(objParSis.getNombreMenu() + strVersion);
            lblTit.setText(objParSis.getNombreMenu());
            //Botón Genera Factura Automática
            if(objParSis.getCodigoUsuario()!= 1){
                System.out.println("NO ES EL ADMIN... ");
                btnObtenerItems.setVisible(false);
                btnProcesar.setEnabled(false);
            }
            
            txtMarGan.setBackground(objParSis.getColorCamposObligatorios());
            
            txtCodEmpFac.setBackground(objParSis.getColorCamposObligatorios());
            txtCodLocFac.setBackground(objParSis.getColorCamposObligatorios());
            txtCodCliFac.setBackground(objParSis.getColorCamposObligatorios());
            
            txtCodEmpOC.setBackground(objParSis.getColorCamposObligatorios());
            txtCodLocOC.setBackground(objParSis.getColorCamposObligatorios());
            txtCodCliOC.setBackground(objParSis.getColorCamposObligatorios());
            
            
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_COS_ITM,"Cos.Itm.");
            vecCab.add(INT_TBL_DAT_MAR_GAN,"Mar.Gan.");
            vecCab.add(INT_TBL_DAT_PRE_VEN,"Pre.Ven.Gan.");
            vecCab.add(INT_TBL_DAT_TOT,"Ganancia*CanDis");
            vecCab.add(INT_TBL_DAT_CHK_VAL,"empty");
            vecCab.add(INT_TBL_DAT_CAN_DIS,"Can.Dis.");
            vecCab.add(INT_TBL_DAT_VENDIDOS,"Can.Ven.Tot.HIS");

            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setCellSelectionEnabled(true);
            //tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setPegarEnabled(false);
            objTblPopMnu.setBorrarContenidoEnabled(false);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();

            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_MAR_GAN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PRE_VEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TOT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHK_VAL).setPreferredWidth(20);  // CHECK 
            tcmAux.getColumn(INT_TBL_DAT_CAN_DIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VENDIDOS).setPreferredWidth(60);
 
    
         // /Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_COS_ITM, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_MAR_GAN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_PRE_VEN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_TOT, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            
            
            objTblCelRenLblCod=new ZafTblCelRenLbl();
            objTblCelRenLblCod.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellRenderer(objTblCelRenLblCod);
            
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_VAL).setCellRenderer(objTblCelRenChk); 
            
            objTblCelEdiChkPre = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_VAL).setCellEditor(objTblCelEdiChkPre);
            objTblCelEdiChkPre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                double dblSub, dblTot = Double.parseDouble(txtTotal.getText());
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    tblDat.setValueAt(true, intNumFil, INT_TBL_DAT_CHK_VAL);
                    calcular();
                }
            });
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
 
            
//            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK_VAL);
            objTblMod.setColumnasEditables(vecAux);
//            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            
 
        }
        catch(Exception e)
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
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        optGenOD = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panRpt = new javax.swing.JPanel();
        panCom = new javax.swing.JPanel();
        btnObtenerItems = new javax.swing.JButton();
        btnProcesar = new javax.swing.JButton();
        txtMarGan = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtCodEmpFac = new javax.swing.JTextField();
        txtCodLocFac = new javax.swing.JTextField();
        txtCodEmpOC = new javax.swing.JTextField();
        txtCodLocOC = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCodCliOC = new javax.swing.JTextField();
        txtCodCliFac = new javax.swing.JTextField();
        txtSub = new javax.swing.JTextField();
        txtValIva = new javax.swing.JTextField();
        txtTot = new javax.swing.JTextField();
        btnCalFac = new javax.swing.JButton();
        panDat = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Información del registro actual");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(459, 473));

        panRpt.setLayout(new java.awt.GridLayout());

        panCom.setLayout(null);

        btnObtenerItems.setText("OBTENER ITEMS");
        btnObtenerItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObtenerItemsActionPerformed(evt);
            }
        });
        panCom.add(btnObtenerItems);
        btnObtenerItems.setBounds(131, 40, 200, 23);

        btnProcesar.setText("PROCESAR");
        btnProcesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcesarActionPerformed(evt);
            }
        });
        panCom.add(btnProcesar);
        btnProcesar.setBounds(10, 280, 100, 23);
        panCom.add(txtMarGan);
        txtMarGan.setBounds(200, 10, 70, 20);

        jLabel1.setText("Margen Gananancia (0.05)");
        panCom.add(jLabel1);
        jLabel1.setBounds(30, 10, 150, 14);

        jLabel2.setText("TOTAL");
        panCom.add(jLabel2);
        jLabel2.setBounds(130, 70, 32, 14);

        txtTotal.setEditable(false);
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setText("0");
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });
        panCom.add(txtTotal);
        txtTotal.setBounds(176, 70, 130, 20);
        panCom.add(txtCodEmpFac);
        txtCodEmpFac.setBounds(70, 190, 30, 20);
        panCom.add(txtCodLocFac);
        txtCodLocFac.setBounds(110, 190, 30, 20);
        panCom.add(txtCodEmpOC);
        txtCodEmpOC.setBounds(70, 220, 30, 20);
        panCom.add(txtCodLocOC);
        txtCodLocOC.setBounds(110, 220, 30, 20);

        jLabel3.setText("FACTURA");
        panCom.add(jLabel3);
        jLabel3.setBounds(10, 200, 80, 14);

        jLabel4.setText("OC");
        panCom.add(jLabel4);
        jLabel4.setBounds(10, 220, 50, 14);

        jLabel5.setText("CLIENTES");
        panCom.add(jLabel5);
        jLabel5.setBounds(150, 210, 80, 14);
        panCom.add(txtCodCliOC);
        txtCodCliOC.setBounds(210, 220, 40, 20);
        panCom.add(txtCodCliFac);
        txtCodCliFac.setBounds(210, 190, 40, 20);
        panCom.add(txtSub);
        txtSub.setBounds(20, 100, 60, 20);
        panCom.add(txtValIva);
        txtValIva.setBounds(90, 100, 60, 20);
        panCom.add(txtTot);
        txtTot.setBounds(160, 100, 60, 20);

        btnCalFac.setText("Calcular Fac");
        btnCalFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalFacActionPerformed(evt);
            }
        });
        panCom.add(btnCalFac);
        btnCalFac.setBounds(230, 100, 100, 23);

        panRpt.add(panCom);

        panDat.setLayout(new java.awt.GridLayout());

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
        jScrollPane2.setViewportView(tblDat);

        panDat.add(jScrollPane2);

        panRpt.add(panDat);

        tabFrm.addTab("Jota", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);
        tabFrm.getAccessibleContext().setAccessibleName("Jota");

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

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

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
// TODO add your handling code here:
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                if(con!=null){
                    con.close();
                    con=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e)
        {
            dispose();
        }        
}//GEN-LAST:event_exitForm

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void btnObtenerItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObtenerItemsActionPerformed
        // TODO add your handling code here:
        if(txtMarGan.getText().length()>0 && txtCodEmpFac.getText().length()>0 && txtCodEmpOC.getText().length()>0){
            obtenerItems();
        }
        else{
            mostrarMsgInf("falta el margen...");
        }
        
    }//GEN-LAST:event_btnObtenerItemsActionPerformed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void btnProcesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcesarActionPerformed
        // TODO add your handling code here:
        MagicoProcesoDeJota();
    }//GEN-LAST:event_btnProcesarActionPerformed

    private void btnCalFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalFacActionPerformed
        // TODO add your handling code here:
        calculaFactura();
    }//GEN-LAST:event_btnCalFacActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton btnCalFac;
    private javax.swing.JButton btnObtenerItems;
    private javax.swing.JButton btnProcesar;
    private javax.swing.JButton butCer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.ButtonGroup optGenOD;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCom;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCliFac;
    private javax.swing.JTextField txtCodCliOC;
    private javax.swing.JTextField txtCodEmpFac;
    private javax.swing.JTextField txtCodEmpOC;
    private javax.swing.JTextField txtCodLocFac;
    private javax.swing.JTextField txtCodLocOC;
    private javax.swing.JTextField txtMarGan;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTot;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtValIva;
    // End of variables declaration//GEN-END:variables

//    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }   
    
    
    private boolean obtenerItems(){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                dblMarGan = Double.parseDouble(txtMarGan.getText());
                if(obtenerDatos(conLoc)){
                    if(consutarItemsEmpresa(conLoc)){
                        System.out.println("Cargados");
                        blnRes=true;
                    }
                }
            }
            conLoc.close();
            conLoc=null;
        }
       catch (java.sql.SQLException e) { 
           objUti.mostrarMsgErr_F1(this, e); 
           blnRes=false;
       }
       catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
           blnRes=false;
       }  
       return blnRes;
    }
  
    private void calcular(){
       
        double dblTot=0.00;
        int intNumFil=objTblMod.getRowCountTrue();
        for (int i=0; i<intNumFil;i++){
            if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_VAL)  ){
                dblTot = dblTot + objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_TOT));
            }
        }
        objUti.redondear(dblTot, objParSis.getDecimalesMostrar());
        txtTotal.setText( String.valueOf(dblTot));
        
    }
    private ZafCtaCtb_dat objZafCtaCtb_dat, objZafCtaCtb_dat_OC;  // Para obtener  los codigos y nombres de ctas ctbles
    
    
    
        //Calculos Cotización
    private double dblPorIva;                                                  //Porcentaje de Iva para la empresa enviado en ZafParSis
    private double dblTotalCot, dblIvaCot;
    private double dblSubtotalCot;
    private double bldivaEmp = 0;
    
 
    private boolean consutarItemsEmpresa(java.sql.Connection conExt){
        boolean blnRes=false ;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                pgrSis.setIndeterminate(true);
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT a.co_emp, a.co_itm, a.tx_codAlt, ROUND(a.nd_UniVen,2) as nd_uniVen, b.nd_cospro, ROUND (b.nd_canDis,2) as nd_canDis \n";
                strSql+="   , b.co_bod, b.nd_preVenGan, ROUND( (b.nd_preVenGan * b.nd_canDis),2) as totVen \n";
                strSql+=" FROM \n";
                strSql+=" ( \n";
                strSql+="       SELECT a3.co_emp,a3.co_itm, a3.tx_codAlt, a3.tx_nomItm,  \n";
                strSql+="              -SUM(CASE WHEN (a1.st_tipDev IS NULL OR a1.st_tipDev='C') THEN a2.nd_can ELSE 0 END) AS nd_uniVen   \n";
                strSql+="       FROM tbm_cabMovInv AS a1  \n";
                strSql+="       INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  \n";
                strSql+="       INNER JOIN tbm_inv a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)  \n";
                strSql+="       INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)  \n";
                strSql+="       INNER JOIN tbm_cli AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_cli=a5.co_cli)  \n";
                strSql+="       WHERE a1.co_emp="+txtCodEmpFac.getText()+" AND a1.st_reg IN ('A','R','C','F') AND  \n";
                strSql+="             a1.fe_doc BETWEEN '2016-01-01' AND '2016-10-31' AND (a4.co_cat IN (23)) AND a3.st_ser IN ('N', 'S', 'T', 'O')    \n";
                strSql+="       GROUP BY a3.co_emp,a3.co_itm, a3.tx_codAlt, a3.tx_nomItm    \n";
                strSql+="       ORDER BY nd_uniVen DESC , a3.tx_codAlt \n";
                strSql+=" ) AS a \n";
                strSql+=" INNER JOIN ( \n";
                strSql+="       select bod.co_emp, a.co_itm, ne_secemp, tx_codalt, tx_nomitm, tx_uni, nd_exi, nd_cospro, nd_valexi ,  sum(bod.nd_stkact) as nd_stkact, \n";
                strSql+="               bod.co_bod  , sum(bod.nd_canDis) as nd_canDis,  \n";
                strSql+="               ROUND(nd_cosPro * (1+"+dblMarGan+"),2) as nd_preVenGan  \n";
                strSql+="       FROM (  \n";
                strSql+="               SELECT co_itm, ne_secemp, tx_codalt, tx_nomitm, tx_uni, nd_exi, nd_cospro, nd_valexi  \n";
                strSql+="               FROM tbm_invval  \n";
                strSql+="               WHERE ne_secreg in( \n";
                strSql+="                                   select max(ne_secreg)  \n";
                strSql+="                                   FROM tbm_invval  \n";
                strSql+="                                   WHERE co_emp="+txtCodEmpFac.getText()+" and  fe_doc BETWEEN '2016-01-01' AND '2016-10-31'  \n";
                strSql+="                                   group by co_itm \n";
                strSql+="                                 )   \n";
                strSql+="           ) AS a  left join tbm_equInv  as equi on(equi.co_emp=2 and equi.co_itm=a.co_itm)   \n";
                strSql+="       left join tbm_equInv  as equi2 on(equi2.co_emp=0 and equi2.co_itmMae=equi.co_itmMae)  \n";
                strSql+="       left join tbm_invbod as bod on(bod.co_emp="+txtCodEmpFac.getText()+" and bod.co_itm=a.co_itm and bod.co_bod in ( 15 ) )  \n";
                strSql+="       WHERE bod.nd_canDis IS NOT NULL AND bod.nd_canDis > 0 \n";
                strSql+="       group by bod.co_emp, a.co_itm, ne_secemp, tx_codalt, tx_nomitm, tx_uni, nd_exi, nd_cospro, nd_valexi , bod.co_bod \n";
                strSql+="       ORDER BY nd_canDis DESC , nd_cospro DESC \n";
                strSql+=" ) as B ON (a.co_emp=b.co_emp AND a.co_itm=b.co_itm ) \n";
                strSql+=" ORDER BY totVen DESC ,nd_cospro DESC , nd_UniVen DESC, nd_canDis DESC  \n";
                strSql+=" \n";
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_ITM,rstLoc.getInt("co_itm"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM,rstLoc.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_DAT_COS_ITM,rstLoc.getString("nd_cospro")==null?"":rstLoc.getString("nd_cospro"));
                    vecReg.add(INT_TBL_DAT_MAR_GAN,dblMarGan);
                    vecReg.add(INT_TBL_DAT_PRE_VEN,rstLoc.getString("nd_preVenGan")==null?"":rstLoc.getString("nd_preVenGan"));
                    vecReg.add(INT_TBL_DAT_TOT,rstLoc.getString("totVen")==null?"":rstLoc.getString("totVen"));
                    vecReg.add(INT_TBL_DAT_CHK_VAL,"Chk");
                    vecReg.add(INT_TBL_DAT_CAN_DIS,rstLoc.getString("nd_canDis")==null?"":rstLoc.getString("nd_canDis"));
                    vecReg.add(INT_TBL_DAT_VENDIDOS,rstLoc.getString("nd_UniVen")==null?"":rstLoc.getString("nd_UniVen"));
                    vecDat.add(vecReg);
                }
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            
	
            pgrSis.setIndeterminate(false);


        }
        catch (java.sql.SQLException e) { 
           objUti.mostrarMsgErr_F1(this, e);  
           blnRes=false;
       }
       catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
           blnRes=false;
       } 
        
        return blnRes;
    }
    
    
    /**
     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    

    
    
    /**
     * Esta funci�n muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
   
    private boolean MagicoProcesoDeJota(){
       java.sql.Connection conLoc;
        boolean blnRes=false;
       try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                conLoc.setAutoCommit(false);
                if(obtenerDatos(conLoc)){
                    if(insertarCabFac(conLoc)){
                        if (insertarDetFac(conLoc)) {
                            if (insertarPagFac(conLoc)) {
                                if(insertarDiarioFac(conLoc)) {
                                    if(insertarCabOC(conLoc)){
                                        if (insertarDetOC(conLoc)) {
                                            if (insertarPagOC(conLoc)) {
                                                if(insertarDiarioOC(conLoc)) {
                                                    blnRes=true;
                                                    System.out.println("EXITO!!!!!");
                                                     mostrarMsgInf("EXITO!!!!!....");
                                                }
                                            }
                                        }
                                    }
                                    
                                }
                            }
                        }
                    }           
                }
                
            }
            
            if(blnRes){
                conLoc.commit();
            }
            else{
                conLoc.rollback();
            }
            conLoc.close();
            conLoc=null;
       }
       catch (java.sql.SQLException e) { 
           objUti.mostrarMsgErr_F1(this, e);  
           blnRes=false;
       }
       catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
           blnRes=false;
       }  
       return blnRes;
    }
    
    
    
     int intCodEmpFac, intCodLocFac, intCodCliFac, intCodEmpOC, intCodLocOC, intCodCliOC;
    private boolean obtenerDatos(java.sql.Connection conLoc){
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        boolean blnRes=true;
         try{
             if(conLoc!=null){
               stmLoc=conLoc.createStatement();
               intCodEmpFac = Integer.parseInt(txtCodEmpFac.getText());
               intCodLocFac = Integer.parseInt(txtCodLocFac.getText());
               intCodCliFac = Integer.parseInt(txtCodCliFac.getText());
               
             intCodEmpOC = Integer.parseInt(txtCodEmpOC.getText());
               intCodLocOC = Integer.parseInt(txtCodLocOC.getText());
               intCodCliOC = Integer.parseInt(txtCodCliOC.getText());
               
               // DATOS FACTURACION
//               1;8;Crédito 60 días
//                2;31;Crédito 60 días
//                3;8;Crédito 60 días
//                4;8;Crédito 60 días

               if(intCodEmpFac==1){
                   intCodForPagFac=8;strNomForPagFac="Crédito 60 días";
               }else if(intCodEmpFac==2){
                   intCodForPagFac=31;strNomForPagFac="Crédito 60 días";
               }else{
                   intCodForPagFac=8;strNomForPagFac="Crédito 60 días";
               }
               
               if(intCodEmpOC==1){
                   intCodForPagOC=8;strNomForPagOC="Crédito 60 días";
               }else if(intCodEmpOC==2){
                   intCodForPagOC=31;strNomForPagOC="Crédito 60 días";
               }else{
                   intCodForPagOC=8;strNomForPagOC="Crédito 60 días";
               }
             
             }
         }
         catch(java.sql.SQLException Evt){ 
          blnRes=false;
          objUti.mostrarMsgErr_F1(null, Evt); 
        }
        catch(Exception Evt){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, Evt); 
        }
        return blnRes;
    }
    
    private int intCodForPagFac, intCodForPagOC;
    private String strNomForPagFac, strNomForPagOC;
    
    private void calculaFactura(){
        try{
             double dblSub=0.00;
            int intNumFil=objTblMod.getRowCountTrue();
            
            objZafCtaCtb_dat = new ZafCtaCtb_dat(objParSis,intCodEmpFac,intCodLocFac, intCodTipDocFacEle);
            bldivaEmp = objZafCtaCtb_dat.getPorIvaVen();
            
            objZafCtaCtb_dat_OC = new ZafCtaCtb_dat(objParSis,intCodEmpOC,intCodLocOC, intCodTipDocOC);
            bldivaEmp = objZafCtaCtb_dat.getPorIvaVen();
            
            dblPorIva = bldivaEmp;
            System.out.println("Iva..." + bldivaEmp);

            for (int i=0; i<intNumFil;i++){
                if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_VAL)  ){
                    dblSub = dblSub + objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_TOT));
                }
            }
            
            dblSubtotalCot=dblSub;
            dblIvaCot = (dblPorIva/100) *  objUti.redondear(dblSubtotalCot, objParSis.getDecimalesMostrar());
            dblTotalCot = dblIvaCot + dblSubtotalCot;

            System.out.println("dblSubtotalCot " + dblSubtotalCot);
            System.out.println("dblIvaCot " + dblIvaCot);
            System.out.println("dblTotalCot " + dblTotalCot);

            dblSubtotalCot = objUti.redondear(dblSubtotalCot, objParSis.getDecimalesMostrar());
            dblIvaCot = objUti.redondear(dblIvaCot, objParSis.getDecimalesMostrar());
            dblTotalCot = objUti.redondear(dblTotalCot, objParSis.getDecimalesMostrar());

            txtSub.setText( String.valueOf(dblSubtotalCot));
            txtValIva.setText( String.valueOf(dblIvaCot));
            txtTot.setText( String.valueOf(dblTotalCot));
        }
         catch(Exception Evt){ 
            objUti.mostrarMsgErr_F1(null, Evt); 
        }
       
    }
    
    
    
        private boolean insertarCabFac(java.sql.Connection conn ) {
            boolean blnRes = false;
            java.sql.Statement stmLoc,  stmLocIns;
            java.sql.ResultSet rstLoc;
            String  strSqlIns = "";
            String strMerIngEgr,strTipIngEgr;
            int intSecGrp = 0;
            int intSecEmp = 0;
            String strFecSem = "";
             String strNomCli="",strIdeCli="";
             
            try {
              //  System.out.println("insertarCabFac");
                stmLoc = conn.createStatement();
                datFecDoc = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecDoc == null) {
                    return false;
                }
                strFecSistema = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
                strFecModCot = strFecSistema;

                 
                /**
                * ********************** OBTIENE MAX CODIGO DE CABMOVINVV ****************************************
                * 
                */
                strSql =" SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc ";
                strSql+=" FROM tbm_cabMovInv ";
                strSql+=" WHERE co_emp="+intCodEmpFac+" AND co_loc="+intCodLocFac+" AND co_tipDoc="+intCodTipDocFacEle;
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intCodDoc=rstLoc.getInt("co_doc");
                }        

               
                strSql = "SELECT st_meringegrfisbod, tx_natdoc FROM tbm_cabtipdoc WHERE  co_emp=" + intCodEmpFac + " "
                        + " and co_loc=" + intCodLocFac + " and co_tipDoc=" + intCodTipDocFacEle;
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    strMerIngEgr = rstLoc.getString("st_meringegrfisbod");
                    strTipIngEgr = rstLoc.getString("tx_natdoc");
                }
                rstLoc.close();
                rstLoc = null;
                
                intSecEmp = objUltDocPrint.getNumSecDoc(conn, intCodEmpFac);
                intSecGrp = objUltDocPrint.getNumSecDoc(conn, objParSis.getCodigoEmpresaGrupo());
               String strDirCli="";
                strSql="";
                strSql+=" SELECT * ";
                strSql+=" FROM tbm_cli as a1 ";
                strSql+=" WHERE a1.co_emp=" + intCodEmpFac + " AND a1.co_cli="+intCodCliFac;
                strSql+="  ";
                strSql+="  ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    strNomCli=rstLoc.getString("tx_nom");
                    strIdeCli=rstLoc.getString("tx_ide");
                    strDirCli=rstLoc.getString("tx_dir");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc = null;
                
                strSqlIns = "INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, ";
                strSqlIns+=" tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, ";
                strSqlIns+=" tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, ";
                strSqlIns+=" co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu";
                strSqlIns+=" ,st_coninvtraaut, st_excDocConVenCon, st_coninv, st_creguirem,tx_tipMov,st_genOrdDes ";
                
                strSqlIns += " ) ";
                strSqlIns+=" VALUES(" + intCodEmpFac + ", " + intCodLocFac + ", ";
                strSqlIns+= intCodTipDocFacEle + ", " + intCodDoc + ", '" + datFecDoc + "', " + intCodCliFac + " ,null,null,'";
                strSqlIns+= strNomCli + "','"+strDirCli+ "','" + strIdeCli + "',null,'Guayaquil'";
                strSqlIns+= ",null,0 ,null,";
                strSqlIns+= "  null ,'JoséMario: Generado con autorizacion Ing. Eddye Lino 28/Oct/2016 '," + dblSubtotalCot * -1 + " ," + bldivaEmp + " ," + dblTotalCot * -1 + ", ";
                strSqlIns+= dblIvaCot * -1 + " , " + intCodForPagFac + ",'"+strNomForPagFac+"','" + strFecSistema + "', ";
                strSqlIns+= "1, '" + strFecSistema + "', null , null, null,";
                strSqlIns+= " null, 'A', " + intSecGrp + ", " + intSecEmp + ", null, 'I' ,'C' , 'N', " + intCodMnuFac + " ";
                strSqlIns+= " ,'S', 'N', null ,'S','E','N' ";
                strSqlIns += " );";
                
                
                System.out.println("insertarCabFac " + strSqlIns);
                stmLocIns = conn.createStatement();
                stmLocIns.executeUpdate(strSqlIns);
                stmLocIns.close();
                stmLocIns = null;
                 
                
                blnRes = true;
            } 
            catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } 
            catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

 
private int intCodBodCot = 15;
    

        private boolean insertarDetFac(Connection conn) {
            boolean blnRes = false;
            Statement stmLoc, stmLocIns, stmLoc01;
            String strSqlIns = "";
            String str_MerIEFisBod = "S";
            
            int intCodItm = 0;
            int intCodBod = 0;
            int intEstIns = 0;
           
            double dlbCanMov = 0.00;
            double dlbcostouni = 0.00;
            double bldcostot = 0.00;
            double dbl_canConIE = 0.00;
 
           
            java.sql.ResultSet rstLoc01;
            
              
            String strCodAlt,strNomItm="",strIvaVen="",strDesUni="";       
            double dblPreUni,dblPorDes=0.00,dblCanTot;
            try {
                stmLoc = conn.createStatement();
                stmLoc01 = conn.createStatement();
                StringBuffer stbins = new StringBuffer(); //VARIABLE TIPO BUFFER
                 

                int intNumFil=objTblMod.getRowCountTrue();
                for (int i=0; i<intNumFil;i++){
                    if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_VAL)  ){
                        intCodItm = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString());
                        strCodAlt = objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM).toString();
                        dlbCanMov = objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_DIS)); // Cantidad a vender
                        dblPreUni = objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_PRE_VEN)); // COSTO CON LA GANANCIA a vender
                        //SUM(  ROUND(  ROUND( a1.nd_preUni* a1.nd_can,2)  - ROUND( (a1.nd_preUni* a1.nd_can)*a1.nd_porDes/100,2  )  ,2))  as subtotal
                        dblCanTot=objUti.redondear(dblPreUni*dlbCanMov,2) - objUti.redondear( (dblPreUni*dlbCanMov) * (dblPorDes/100),2 );

                        if(dblCanTot>0){  // SI ES POSITIVO
                            dblCanTot=dblCanTot*-1;
                        }
                        
                        
                        strSql = " SELECT a1.tx_nomItm, a3.tx_desCor, a1.st_ivaVen,a1.nd_cosUni ";
                        strSql+= " FROM tbm_inv as a1";
                        strSql+= " INNER JOIN tbm_var as a3 ON (a1.co_uni=a3.co_reg) ";
                        strSql+= " WHERE co_emp="+intCodEmpFac+" and co_itm=" +intCodItm;
                        System.out.println("Costos " + strSql);
                        rstLoc01 = stmLoc01.executeQuery(strSql);
                        if (rstLoc01.next()) {
                            strNomItm = rstLoc01.getString("tx_nomItm");
                            strDesUni= rstLoc01.getString("tx_desCor");
                            strIvaVen= rstLoc01.getString("st_ivaVen");
                            dlbcostouni = rstLoc01.getDouble("nd_cosUni");
                        }
                        rstLoc01.close();
                        rstLoc01=null;
                        intCodBod = intCodBodCot;
                        dlbCanMov = dlbCanMov * -1;
                        bldcostot = (dlbcostouni * dlbCanMov);
                        bldcostot = (bldcostot * -1);
                        if (intEstIns > 0) {
                            stbins.append(" UNION ALL ");
                        }
                        str_MerIEFisBod="N";
                        stbins.append("SELECT "+intCodEmpFac+ "," + intCodLocFac+","+intCodTipDocFacEle+","+intCodDoc + ","+(i+1)+",'"
                                +strCodAlt+ "','" +strCodAlt+ "',"+intCodItm+ ","+intCodItm+ ","
                                + objUti.codificar(strNomItm) + ",'"+strDesUni+ "',"+intCodBod + ","
                                + dlbCanMov + ","
                                + objUti.redondear(dblCanTot, objParSis.getDecimalesMostrar()) + ", "  
                                + dlbcostouni + ", 0 , "
                                + dlbcostouni + ","
                                + objUti.redondear(dblPreUni, 4) + ", "
                                + objUti.redondear(dblPorDes, 2) + ", '"
                                + (strIvaVen) + "' "
                                + "," + bldcostot + ",'I', '" + str_MerIEFisBod + "', " + dbl_canConIE + ", "
                                + objUti.redondear(dblPreUni, 4) + ", " /* PRECIO EN LA COTIZACION */
                                + "0.00, "
                                + " 1" );
                   
                      intEstIns = 1;                    
                    }
                }
                if (intEstIns == 1) {
                    strSqlIns += " ; INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, " + //CAMPOS PrimayKey
                            " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
                            " co_bod, nd_can,nd_tot, nd_cosUnigrp,nd_costot,nd_cosUni, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
                            ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_preunivenlis, nd_pordesvenmax , ne_numfil)" + stbins.toString();

                }

                if (conn != null) {
                    stmLocIns = conn.createStatement();
                    System.out.println("insertarDetFac \n" +strSqlIns);
                    stmLocIns.executeUpdate(strSqlIns);
                    stmLocIns.close();
                    stmLocIns = null;
                }
                stmLoc01.close();
                stmLoc01=null;
                stmLoc.close();
                stmLoc = null;
                stbins = null;
                
                 

                blnRes = true;
            } catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
                System.err.println("ERROR " + Evt.toString());
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
                System.err.println("ERROR " + Evt.toString());
            }
            return blnRes;
        }
        
  

        private boolean insertarPagFac(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLocIns;
            String strSql = "", strSqlIns = "";
            String strFec = "";
            
            try {
                if (conn != null) {
                        double dblRet1, dblRet2, dblRet3;
                        dblRet1 = objUti.redondear((dblSubtotalCot * 0.01 ),2);
                        dblRet2 = objUti.redondear((dblIvaCot * 0.1),2);
                        dblRet3 = dblTotalCot - (dblRet1+dblRet2); 
                        strFec = "2016/12/28"; // ???
                        
                        for (int x = 0; x < 3; x++) {
                            if(x==0){
                                strSqlIns += " ; INSERT INTO  tbm_pagMovInv(co_emp, co_loc, co_tipDoc, co_doc, co_reg, " + //CAMPOS PrimayKey
                                    " ne_diaCre, fe_ven, mo_pag, ne_diaGra, nd_porRet ,st_regrep , st_sop" +//<==
                                    " ,co_tipret ) VALUES ("
                                    + intCodEmpFac + ", " + intCodLocFac + ", " + intCodTipDocFacEle + ", " + intCodDoc + ", " + (x + 1) + ", "
                                    + "0, '" + strFec + "',"
                                    +(dblRet1*-1)+", "+
                                     0 + ", "
                                    + 0 + ", 'I', 'N', "
                                    + "1 ) ";
                            }
                            else if(x==1){
                                strSqlIns += " ; INSERT INTO  tbm_pagMovInv(co_emp, co_loc, co_tipDoc, co_doc, co_reg, " + //CAMPOS PrimayKey
                                    " ne_diaCre, fe_ven, mo_pag, ne_diaGra, nd_porRet ,st_regrep , st_sop" +//<==
                                    " ,co_tipret ) VALUES ("
                                    + intCodEmpFac + ", " + intCodLocFac + ", " + intCodTipDocFacEle + ", " + intCodDoc + ", " + (x + 1) + ", "
                                    + "0, '" + strFec + "',"
                                    +(dblRet2*-1)+", "+
                                     0 + ", "
                                    + 0 + ", 'I', 'N', "
                                    + "14 ) ";
                            }else{
                                strSqlIns += " ; INSERT INTO  tbm_pagMovInv(co_emp, co_loc, co_tipDoc, co_doc, co_reg, " + //CAMPOS PrimayKey
                                    " ne_diaCre, fe_ven, mo_pag, ne_diaGra, nd_porRet ,st_regrep , st_sop" +//<==
                                    " ,co_tipret ) VALUES ("
                                    + intCodEmpFac + ", " + intCodLocFac + ", " + intCodTipDocFacEle + ", " + intCodDoc + ", " + (x + 1) + ", "
                                    + "60, '" + strFec + "',"
                                    +(dblRet3*-1)+", "+
                                     0 + ", "
                                    + 0 + ", 'I', 'N', "
                                    + "0 ) ";
                            }
                            
                        }
                        blnRes = true;
                       
                        System.out.println("PAGOS!!  " + strSqlIns);
                        stmLocIns = conn.createStatement();
                        stmLocIns.executeUpdate(strSqlIns);
                        stmLocIns.close();
                        stmLocIns = null;
                }
                 
            } 
            catch (SQLException Evt) 
            {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
                System.err.println("ERROR " + Evt.toString());
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
                System.err.println("ERROR " + Evt.toString());
            }
            return blnRes;
        }
 
 

 
 
 
        private boolean insertarDiarioFac(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement   stmLocIns;
            String strSql = "", strSqlIns = "";
            String strPer = null, strMes = "";
            String strFecSistema = "";
            int intCodPer = 0;
            int intMes = 0;
            try {
                if (conn != null) {
                    
                    int intArlAni = 0;
                    int intArlMes = 0;
                    String strArlTipCie = "";
                    int intRefAniNew = 0;
                    int intRefMesNew = 0;
                    int intTipPro = 0;
                    intRefAniNew = (datFecDoc.getYear() + 1900);
                    intRefMesNew = (datFecDoc.getMonth() + 1);
                    String strMensaje;

                    /**
                     * *****************************************************************************************
                     */
                    strFecSistema = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());

                    strSqlIns = "INSERT INTO  tbm_cabdia(co_emp, co_loc, co_tipDoc, co_dia, fe_dia, tx_glo, "
                            + " fe_ing, co_usrIng, fe_ultMod, co_usrMod ) "
                            + " VALUES(" +intCodEmpFac + ", " +intCodLocFac+ ", " + intCodTipDocFacEle + ", "
                            + intCodDoc + ", '" + datFecDoc + "',null,'" + strFecSistema + "',"+1+" , "
                            + " null, null);";
                    stmLocIns = conn.createStatement();
                    System.out.println("DIARIO FACTURA " + strSqlIns);
                    stmLocIns.executeUpdate(strSqlIns);
                    stmLocIns.close();
                    stmLocIns = null;
                    
                    if(insertarBigDetDia(conn,intCodTipDocFacEle, intCodDoc )){
                        blnRes = true;
                    } else {
                        blnRes = false;
                    }

                     
                }
            } 
            catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
                System.err.println("ERROR " + Evt.toString());
            } 
            catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
                System.err.println("ERROR " + Evt.toString());
            }
            return blnRes;
        }
    
      
     
        private boolean insertarBigDetDia(java.sql.Connection conn, int intCodTipDocFacEle, int intCodDoc ) {
            boolean blnRes = false;
            java.sql.Statement   stmLocIns;
            String srtSql = "", strSqlIns = "";
            try {
                if (conn != null) {
                    srtSql = "INSERT INTO tbm_detdia(co_emp, co_loc, co_tipDoc, co_dia, co_reg, co_cta, nd_mondeb, nd_monhab )"
                            + " VALUES(" + intCodEmpFac + ", " +intCodLocFac+ ", " + intCodTipDocFacEle + ", " + intCodDoc + ", ";                   
                    if (dblTotalCot > 0) {
                        strSqlIns += srtSql + " 1," + objZafCtaCtb_dat.getCtaDeb() + "," + dblTotalCot + ", 0 )  ; ";
                    }
                    if (dblSubtotalCot > 0) {
                        strSqlIns+=srtSql+" 2, "+objZafCtaCtb_dat.getCtaHab()+", 0, "+new BigDecimal(dblSubtotalCot).setScale(2, RoundingMode.HALF_UP) +" ) ; ";
                        
                    }
                    if (dblIvaCot > 0) {
			strSqlIns+=srtSql+" 3 , "+objZafCtaCtb_dat.getCtaIvaVentas()+", 0, "+new BigDecimal(dblIvaCot).setScale(2, RoundingMode.HALF_UP)+" ) ; ";
                    }
                    System.out.println("DETALLE DE DIARIO " + strSqlIns);
                    stmLocIns = conn.createStatement();
                    stmLocIns.executeUpdate(strSqlIns);
                    stmLocIns.close();
                    stmLocIns = null;
                    blnRes = true;
                }
            } catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
                System.err.println("ERROR " + Evt.toString());
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
                System.err.println("ERROR " + Evt.toString());
            }
            return blnRes;
        }        
        
        
        
        
        private boolean insertarCabOC(java.sql.Connection conn ) {
            boolean blnRes = false;
            java.sql.Statement stmLoc,  stmLocIns;
            java.sql.ResultSet rstLoc;
            String  strSqlIns = "";
            String strMerIngEgr,strTipIngEgr;
            int intSecGrp = 0;
            int intSecEmp = 0;
            String strFecSem = "";
             String strNomCli="",strIdeCli="";
             
            try {
              //  System.out.println("insertarCabFac");
                stmLoc = conn.createStatement();
                datFecDoc = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecDoc == null) {
                    return false;
                }
                strFecSistema = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
                strFecModCot = strFecSistema;

                 
                /**
                * ********************** OBTIENE MAX CODIGO DE CABMOVINVV ****************************************
                * 
                */
                strSql =" SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc ";
                strSql+=" FROM tbm_cabMovInv ";
                strSql+=" WHERE co_emp="+intCodEmpOC+" AND co_loc="+intCodLocOC+" AND co_tipDoc="+intCodTipDocOC;
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intCodDocOC=rstLoc.getInt("co_doc");
                }        

               
                strSql = "SELECT st_meringegrfisbod, tx_natdoc FROM tbm_cabtipdoc WHERE  co_emp=" + intCodEmpOC + " "
                        + " and co_loc=" + intCodLocOC + " and co_tipDoc=" + intCodTipDocOC;
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    strMerIngEgr = rstLoc.getString("st_meringegrfisbod");
                    strTipIngEgr = rstLoc.getString("tx_natdoc");
                }
                rstLoc.close();
                rstLoc = null;
                
                intSecEmp = objUltDocPrint.getNumSecDoc(conn, intCodEmpOC);
                intSecGrp = objUltDocPrint.getNumSecDoc(conn, objParSis.getCodigoEmpresaGrupo());
                
               String strDirCli="";
                strSql="";
                strSql+=" SELECT * ";
                strSql+=" FROM tbm_cli as a1 ";
                strSql+=" WHERE a1.co_emp=" + intCodEmpOC + " AND a1.co_cli="+intCodCliOC;
                strSql+="  ";
                strSql+="  ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    strNomCli=rstLoc.getString("tx_nom");
                    strIdeCli=rstLoc.getString("tx_ide");
                    strDirCli=rstLoc.getString("tx_dir");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc = null;
                
                strSqlIns = "INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, ";
                strSqlIns+=" tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, ";
                strSqlIns+=" tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, ";
                strSqlIns+=" co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu";
                strSqlIns+=" ,st_coninvtraaut, st_excDocConVenCon, st_coninv, st_creguirem,tx_tipMov,st_genOrdDes ";
                
                strSqlIns += " ) ";
                strSqlIns+=" VALUES(" + intCodEmpOC + ", " + intCodLocOC + ", ";
                strSqlIns+= intCodTipDocOC + ", " + intCodDocOC + ", '" + datFecDoc + "', " + intCodCliOC + " ,null,null,'";
                strSqlIns+= strNomCli + "','"+strDirCli+ "','" + strIdeCli + "',null,'Guayaquil'";
                strSqlIns+= ",null,0 ,null,";
                strSqlIns+= "  null ,'JoséMario: Generado con autorizacion Ing. Eddye Lino 28/Oct/2016 '," + dblSubtotalCot + " ," + bldivaEmp + " ," + dblTotalCot + ", ";
                strSqlIns+= dblIvaCot  + " , " + intCodForPagOC + ",'"+strNomForPagOC+"','" + strFecSistema + "', ";
                strSqlIns+= "1, '" + strFecSistema + "', null , null, null,";
                strSqlIns+= " null, 'A', " + intSecGrp + ", " + intSecEmp + ", null, 'I' ,'C' , 'N', " + intCodMnuFac + " ";
                strSqlIns+= " ,'S', 'N', null ,'S','E','N' ";
                strSqlIns += " );";
                
                
                System.out.println("insertarCabFac " + strSqlIns);
                stmLocIns = conn.createStatement();
                stmLocIns.executeUpdate(strSqlIns);
                stmLocIns.close();
                stmLocIns = null;
                 
                
                blnRes = true;
            } 
            catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } 
            catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }
    
        
        
        private boolean insertarDetOC(Connection conn) {
            boolean blnRes = false;
            Statement stmLoc, stmLocIns, stmLoc01;
            String strSqlIns = "";
            String str_MerIEFisBod = "S";
            
            int intCodItm = 0;
            int intCodBod = 0;
            int intEstIns = 0;
           
            double dlbCanMov = 0.00;
            double dlbcostouni = 0.00;
            double bldcostot = 0.00;
            double dbl_canConIE = 0.00;
 
           
            java.sql.ResultSet rstLoc01;
            
              
            String strCodAlt,strNomItm="",strIvaVen="",strDesUni="";       
            double dblPreUni,dblPorDes=0.00,dblCanTot;
            try {
                stmLoc = conn.createStatement();
                stmLoc01 = conn.createStatement();
                StringBuffer stbins = new StringBuffer(); //VARIABLE TIPO BUFFER
                 

                int intNumFil=objTblMod.getRowCountTrue();
                for (int i=0; i<intNumFil;i++){
                    if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_VAL)  ){
                        intCodItm = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString());
                        strCodAlt = objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM).toString();
                        dlbCanMov = objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_DIS)); // Cantidad a vender
                        dblPreUni = objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_PRE_VEN)); // COSTO CON LA GANANCIA a vender
                        //SUM(  ROUND(  ROUND( a1.nd_preUni* a1.nd_can,2)  - ROUND( (a1.nd_preUni* a1.nd_can)*a1.nd_porDes/100,2  )  ,2))  as subtotal
                        dblCanTot=objUti.redondear(dblPreUni*dlbCanMov,2) - objUti.redondear( (dblPreUni*dlbCanMov) * (dblPorDes/100),2 );

//                        if(dblCanTot>0){  // SI ES POSITIVO
//                            dblCanTot=dblCanTot*-1;
//                        }
                        
                        strSql = " SELECT a1.co_itm, a1.tx_nomItm, a3.tx_desCor, a1.st_ivaVen,a1.nd_cosUni ";
                        strSql+= " FROM tbm_inv as a1";
                        strSql+= " INNER JOIN tbm_var as a3 ON (a1.co_uni=a3.co_reg) ";
                        strSql+= " INNER JOIN tbm_equInv as a4 ON (a4.co_emp="+intCodEmpFac+" and a4.co_itm="+intCodItm+" ) " ;
                        strSql+= " INNER JOIN tbm_equInv as a5 ON (a4.co_itmMae=a5.co_itmMae AND a5.co_emp=a1.co_emp AND a5.co_itm=a1.co_itm)" ;
                        strSql+= " WHERE a1.co_emp="+intCodEmpOC+" ;";
                        System.out.println("Costos OC " + strSql);
                        rstLoc01 = stmLoc01.executeQuery(strSql);
                        if (rstLoc01.next()) {
                            intCodItm = rstLoc01.getInt("co_itm");
                            strNomItm = rstLoc01.getString("tx_nomItm");
                            strDesUni= rstLoc01.getString("tx_desCor");
                            strIvaVen= rstLoc01.getString("st_ivaVen");
//                            dlbcostouni = rstLoc01.getDouble("nd_cosUni");
                           dlbcostouni = dblPreUni;
                        }
                        rstLoc01.close();
                        rstLoc01=null;
                        intCodBod = intCodBodCot;
                 //       dlbCanMov = dlbCanMov * -1;
                        bldcostot = (dlbcostouni * dlbCanMov);
                        bldcostot = (bldcostot * -1);
                        if (intEstIns > 0) {
                            stbins.append(" UNION ALL ");
                        }
                        str_MerIEFisBod="N";
                        stbins.append("SELECT "+intCodEmpOC+ "," + intCodLocOC+","+intCodTipDocOC+","+intCodDocOC + ","+(i+1)+",'"
                                +strCodAlt+ "','" +strCodAlt+ "',"+intCodItm+ ","+intCodItm+ ","
                                + objUti.codificar(strNomItm) + ",'"+strDesUni+ "',"+intCodBod + ","
                                + dlbCanMov + ","
                                + objUti.redondear(dblCanTot, objParSis.getDecimalesMostrar()) + ", "  
                                + dlbcostouni + ", 0 , "
                                + dlbcostouni + ","
                                + objUti.redondear(dblPreUni, 4) + ", "
                                + objUti.redondear(dblPorDes, 2) + ", '"
                                + (strIvaVen) + "' "
                                + "," + bldcostot + ",'I', '" + str_MerIEFisBod + "', " + dbl_canConIE + ", "
                                + objUti.redondear(dblPreUni, 4) + ", " /* PRECIO EN LA COTIZACION */
                                + "0.00, "
                                + " 1" );
                   
                      intEstIns = 1;                    
                    }
                }
                if (intEstIns == 1) {
                    strSqlIns += " ; INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, " + //CAMPOS PrimayKey
                            " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
                            " co_bod, nd_can,nd_tot, nd_cosUnigrp,nd_costot,nd_cosUni, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
                            ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_preunivenlis, nd_pordesvenmax , ne_numfil)" + stbins.toString();

                }

                if (conn != null) {
                    stmLocIns = conn.createStatement();
                    System.out.println("insertarDetOC \n" +strSqlIns);
                    stmLocIns.executeUpdate(strSqlIns);
                    stmLocIns.close();
                    stmLocIns = null;
                }
                stmLoc01.close();
                stmLoc01=null;
                stmLoc.close();
                stmLoc = null;
                stbins = null;
                
                 

                blnRes = true;
            } catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
                System.err.println("ERROR " + Evt.toString());
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
                System.err.println("ERROR " + Evt.toString());
            }
            return blnRes;
        }
        
        
        

        private boolean insertarPagOC(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLocIns;
            String strSql = "", strSqlIns = "";
            String strFec = "";
            
            try {
                if (conn != null) {
                        double dblRet1, dblRet2, dblRet3;
                        dblRet1 = objUti.redondear((dblSubtotalCot * 0.01 ),2);
                        dblRet2 = objUti.redondear((dblIvaCot * 0.1),2);
                        dblRet3 = dblTotalCot - (dblRet1+dblRet2); 
                        strFec = "2016/12/28"; // ???
                        
                        for (int x = 0; x < 3; x++) {
                            if(x==0){
                                strSqlIns += " ; INSERT INTO  tbm_pagMovInv(co_emp, co_loc, co_tipDoc, co_doc, co_reg, " + //CAMPOS PrimayKey
                                    " ne_diaCre, fe_ven, mo_pag, ne_diaGra, nd_porRet ,st_regrep , st_sop" +//<==
                                    " ,co_tipret ) VALUES ("
                                    + intCodEmpOC + ", " + intCodLocOC + ", " + intCodTipDocOC + ", " + intCodDocOC + ", " + (x + 1) + ", "
                                    + "0, '" + strFec + "',"
                                    +(dblRet1)+", "+
                                     0 + ", "
                                    + 0 + ", 'I', 'N', "
                                    + "1 ) ";
                            }
                            else if(x==1){
                                strSqlIns += " ; INSERT INTO  tbm_pagMovInv(co_emp, co_loc, co_tipDoc, co_doc, co_reg, " + //CAMPOS PrimayKey
                                    " ne_diaCre, fe_ven, mo_pag, ne_diaGra, nd_porRet ,st_regrep , st_sop" +//<==
                                    " ,co_tipret ) VALUES ("
                                    + intCodEmpOC + ", " + intCodLocOC + ", " + intCodTipDocOC + ", " + intCodDocOC + ", " + (x + 1) + ", "
                                    + "0, '" + strFec + "',"
                                    +(dblRet2)+", "+
                                     0 + ", "
                                    + 0 + ", 'I', 'N', "
                                    + "14 ) ";
                            }else{
                                strSqlIns += " ; INSERT INTO  tbm_pagMovInv(co_emp, co_loc, co_tipDoc, co_doc, co_reg, " + //CAMPOS PrimayKey
                                    " ne_diaCre, fe_ven, mo_pag, ne_diaGra, nd_porRet ,st_regrep , st_sop" +//<==
                                    " ,co_tipret ) VALUES ("
                                    + intCodEmpOC + ", " + intCodLocOC + ", " + intCodTipDocOC + ", " + intCodDocOC + ", " + (x + 1) + ", "
                                    + "60, '" + strFec + "',"
                                    +(dblRet3)+", "+
                                     0 + ", "
                                    + 0 + ", 'I', 'N', "
                                    + "0 ) ";
                            }
                            
                        }
                        blnRes = true;
                       
                        System.out.println("PAGOS!!  " + strSqlIns);
                        stmLocIns = conn.createStatement();
                        stmLocIns.executeUpdate(strSqlIns);
                        stmLocIns.close();
                        stmLocIns = null;
                }
                 
            } 
            catch (SQLException Evt) 
            {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
                System.err.println("ERROR " + Evt.toString());
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
                System.err.println("ERROR " + Evt.toString());
            }
            return blnRes;
        }
        
        
        
 
        private boolean insertarDiarioOC(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLoc, stmLocIns;
            String strSql = "", strSqlIns = "";
            String strPer = null, strMes = "";
            String strFecSistema = "";
            int intCodPer = 0;
            int intMes = 0;
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    int intArlAni = 0;
                    int intArlMes = 0;
                    String strArlTipCie = "";
                    int intRefAniNew = 0;
                    int intRefMesNew = 0;
                    int intTipPro = 0;
                    intRefAniNew = (datFecDoc.getYear() + 1900);
                    intRefMesNew = (datFecDoc.getMonth() + 1);
                    String strMensaje;

                    /**
                     * *****************************************************************************************
                     */
                    strFecSistema = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());

                    strSqlIns = "INSERT INTO  tbm_cabdia(co_emp, co_loc, co_tipDoc, co_dia, fe_dia, tx_glo, "
                            + " fe_ing, co_usrIng, fe_ultMod, co_usrMod ) "
                            + " VALUES(" +intCodEmpOC + ", " +intCodLocOC+ ", " + intCodTipDocOC + ", "
                            + intCodDocOC + ", '" + datFecDoc + "','','" + strFecSistema + "',"+1+" , "
                            + " '" + strFecSistema + "', null)";
                    
                    intMes = datFecDoc.getMonth() + 1;
                    if (intMes < 10) {
                        strMes = "0" + String.valueOf(intMes);
                    } else {
                        strMes = String.valueOf(intMes);
                    }

                    strPer = String.valueOf((datFecDoc.getYear() + 1900)) + strMes;
                    intCodPer = Integer.parseInt(strPer);
                    
                    stmLocIns = conn.createStatement();
                    stmLocIns.executeUpdate(strSqlIns);
                    stmLocIns.close();
                    stmLocIns = null;
                    
                    if(insertarBigDetDiaOC(conn,intCodTipDocOC, intCodDocOC )){
                        blnRes = true;
                    } else {
                        blnRes = false;
                    }

                    stmLoc.close();
                    stmLoc = null;
                }
            } 
            catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
                System.err.println("ERROR " + Evt.toString());
            } 
            catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
                System.err.println("ERROR " + Evt.toString());
            }
            return blnRes;
        }
    
      
     
        private boolean insertarBigDetDiaOC(java.sql.Connection conn, int intCodTipDocOC, int intCodDocOC ) {
            boolean blnRes = false;
            java.sql.Statement stmLoc, stmLocIns;
            String srtSql = "", strSqlIns = "";
            
             
            try {
                if (conn != null) {
                    
                    srtSql = "INSERT INTO tbm_detdia(co_emp, co_loc, co_tipDoc, co_dia, co_reg, co_cta, nd_mondeb, nd_monhab )"
                            + " VALUES(" + intCodEmpOC + ", " +intCodLocOC+ ", " + intCodTipDocOC + ", " + intCodDocOC + ", ";                   
                    if (dblTotalCot > 0) {
                        strSqlIns += srtSql + " 1," + objZafCtaCtb_dat_OC.getCtaDeb() + ",0," + dblTotalCot + ")  ; ";
                        strSqlIns +=   "  ; ";
                    }
                    if (dblSubtotalCot > 0) {
                        strSqlIns+=srtSql+" 2, "+objZafCtaCtb_dat_OC.getCtaHab()+","+new BigDecimal(dblSubtotalCot).setScale(2, RoundingMode.HALF_UP) +",0 ) ; ";
                        strSqlIns +=   "  ; ";
                    }
                    
                    if (dblIvaCot > 0) {
			strSqlIns+=srtSql+" 3 , "+objZafCtaCtb_dat_OC.getCtaIvaVentas()+","+new BigDecimal(dblIvaCot).setScale(2, RoundingMode.HALF_UP)+",0 ) ; ";
                        strSqlIns +=   "  ; ";
                    }
                    stmLocIns = conn.createStatement();
                    stmLocIns.executeUpdate(strSqlIns);
                    stmLocIns.close();
                    stmLocIns = null;
                    blnRes = true;
                }
            } catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
                System.err.println("ERROR " + Evt.toString());
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
                System.err.println("ERROR " + Evt.toString());
            }
            return blnRes;
        }        
        
        
        
 
    
}
