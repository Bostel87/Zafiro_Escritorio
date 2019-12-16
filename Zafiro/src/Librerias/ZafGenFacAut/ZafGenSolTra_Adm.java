/*
 * ZafGenPreEmp_Adm.java
 *
 * Created on Marzo 9, 2015, 17:30 PM
 */
package Librerias.ZafGenFacAut;
import Librerias.ZafCfgBod.ZafCfgBod;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafMovIngEgrInv.ZafMovIngEgrInv;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author  José Marín
 */
public class ZafGenSolTra_Adm extends javax.swing.JInternalFrame
{
    //Constantes: Jtable
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_COD_ITM=1;
    private static final int INT_TBL_DAT_COD_ALT_ITM=2;
    private static final int INT_TBL_DAT_COD_ALT_ITM_DOS=3;
    private static final int INT_TBL_DAT_BTN_ITM=4;
    private static final int INT_TBL_DAT_NOM_ITM=5;
    private static final int INT_TBL_DAT_UNI_MED=6;
    private static final int INT_TBL_DAT_CAN=7;
    private static final int INT_TBL_DAT_PES_UNI_ITM=8;
    private static final int INT_TBL_DAT_PES_TOT_ITM=9;
    private static final int INT_TBL_DAT_CAN_ACT_STK=10;
    
    //Constantes: Para el contenedor a enviar solicitud de transferencia JoséMario 26/Abril/2015.
    final int INT_ARL_SOL_TRA_COD_BOD_ING=0;
    final int INT_ARL_SOL_TRA_COD_BOD_EGR=1;
    final int INT_ARL_SOL_TRA_ITM_GRP=2;
    final int INT_ARL_SOL_TRA_CAN=3;
    final int INT_ARL_SOL_PES_UNI=4;
    final int INT_ARL_SOL_PES_TOT=5;
    
    // Constantes: Para el contenedor que llegara desde otro programa - Solicitudes de Transferencias de Inventario.
    final int INT_ARL_GEN_SOL_TRA_ITM_GRP=0;
    final int INT_ARL_GEN_SOL_TRA_CAN=1;
    final int INT_ARL_GEN_SOL_PES_UNI=2;
    final int INT_ARL_GEN_SOL_PES_TOT=3;
    final int INT_ARL_GEN_SOL_PES_TOT_SOLTRA=4;
    
    //Constantes: Para el contenedor a enviar solicitud de Cotizaciones de Venta JoséMario 26/Abril/2015.
    final int INT_ARL_COT_VEN_COD_EMP=0;
    final int INT_ARL_COT_VEN_COD_LOC=1;
    final int INT_ARL_COT_VEN_COD_TIP_DOC=2;
    final int INT_ARL_COT_VEN_COD_DOC=3;
    final int INT_ARL_COT_VEN_COD_BOD_EGR=4;
    
     /* 
     *    arlDatosItemEgreso/INGRESO        <HTML>Información del item:
     *                                      <BR>Código de empresa donde se guardará el documento
     *                                      <BR>Código de local donde se guardará el documento
     *                                      <BR>Código de tipo de documento donde se guardará el documento
     *                                      <BR>Cliente/Proveedor a quien se le genera el documento
     *                                      <BR>Código de bodega donde se ingresará/egresará la mercadería
     *                                      <BR>Código del item de grupo
     *                                      <BR>Código del item de empresa
     *                                      <BR>Código del item maestro
     *                                      <BR>Código del item alterno
     *                                      <BR>Nombre del item
     *                                      <BR>Unidad de medida del item
     *                                      <BR>Código del item en letras
     *                                      <BR>Cantidad del item en negativo
     *                                      <BR>Costo unitario del item
     *                                      <BR>Precio unitario del item
     *                                      <BR>Peso del item
     *                                      <BR>Estado del item para generar o no el IVA
   */
    /* CONSTANTES PARA CONTENEDOR A ENVIAR JoséMario 21/Sep/2015  */
    final int INT_ARL_COD_EMP=0;            // CODIGO DE LA EMPRESA
    final int INT_ARL_COD_LOC=1;            // CODIGO DEL LOCAL
    final int INT_ARL_COD_TIP_DOC=2;        // CODIGO DEL TIPO DE DOCUMENTO
    final int INT_ARL_COD_CLI=3;            // CODIGO DEL CLIENTE/PROVEEDOR
    final int INT_ARL_COD_BOD_ING_EGR=4;    // CODGIO DE LA BODEGA DE INGRESO/EGRESO 
    final int INT_ARL_COD_ITM_GRP=5;        // CODIGO DE ITEM DE GRUPO
    final int INT_ARL_COD_ITM=6;            // CODIGO DEL ITEM POR EMPRESA
    final int INT_ARL_COD_ITM_MAE=7;        // CODIGO DEL ITEM MAESTRO
    final int INT_ARL_COD_ITM_ALT=8;        // CODIGO DEL ITEM ALTERNO 
    final int INT_ARL_NOM_ITM=9;            // NOMBRE DEL ITEM
    final int INT_ARL_UNI_MED_ITM=10;       // UNIDAD DE MEDIDA
    final int INT_ARL_COD_LET_ITM=11;       // CODIGO ALTERNO DEL ITEM 2 (CODIGO DE TRES LETRAS)
    final int INT_ARL_CAN_MOV=12;           // CANTIDAD 
    final int INT_ARL_COS_UNI=13;           // COSTO UNITARIO DEL ITEM
    final int INT_ARL_PRE_UNI=14;           // PRECIO UNITARIO DEL ITEM
    final int INT_ARL_PES_ITM=15;           // PESO DEL ITEM
    final int INT_ARL_IVA_COM_ITM=16;       // ESTADO DEL ITEM IVA
    final int INT_ARL_EST_MER_ING_EGR_FIS_BOD=17;// SI LA MERCADERIA EGRESA FISICAMENTE / SI SE CONFIRMA
    final int INT_ARL_IND_ORG=18;           // PARA ORGANIZAR AL FINAL LOS PRESTAMOS 
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
     
    private ZafDocLis objDocLis;
    private ZafTblMod objTblMod;
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCodAltItm;     //Editor: TextField en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCodAltItm2;    //Editor: TextField en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;           //Editor: JButton en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    private ZafDatePicker dtpFecDoc;                            //Fecha
    private ZafRptSis objRptSis;                                //Reportes del Sistema.
    
    private ZafPerUsr objPerUsr;                                //Permisos Usuarios.
    
    //Para las Ventanas de Consulta
    private ZafVenCon vcoTipDoc;                                // tipo de documento
    private ZafVenCon vcoBodOrg;                                // bodega de origen 
    private ZafVenCon vcoBodDes;                                // bodega de destino 
    private ZafVenCon vcoItm;                                   // Items 
    private ZafVenCon vcoEmp;
    
    private Date datFecAux;
    private Date datFecDoc;  
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private boolean blnHayCam;
    private Vector vecDat, vecReg, vecCab, vecAux;
    
    private int intCodBodOrg=0;
    private int intCodEmpSolTra;
    private int intCodLocSolTra;
    
    private String strSQL;
    private String strAux;
    private String strDocCod;
    private String strCodBodOrg;
    private String strCodBodDes;
    private String strNomBodOrg;
    private String strNomBodDes;
    private String strDocNom;
    private String strDesCorTipDoc;
    
    private int intCodEmpIngImpLoc;
    private int intCodLocIngImpLoc;
    private int intCodTipDocIngImpLoc;
    private int intCodDocIngImpLoc;
    private String strNumPedEmbImp="";
    
    private int intCodEmpRepInv;
    private int intCodLocRepInv;
    private int intCodTipDocRepInv;
    private int intCodDocRepInv;
    
     private String strVersion=" v0.01.01";   
    // TODOS LOS DATOS BODEGAS E ITEMS SE TRABAJAN POR GRUPO Y AL MOMENTO DE ENVIAR SE LOS CALCULA JM
    /** Creates new form ZafGenPreEmp_Adm */
    public ZafGenSolTra_Adm(ZafParSis obj) 
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
            intCodEmpSolTra = objParSis.getCodigoEmpresa();
            intCodLocSolTra = objParSis.getCodigoLocal();
                        
            //AQUI SE VALIDA QUE SOLO SE PUEDA ABRIR POR GRUPO 
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {  
                if(objParSis.getCodigoUsuario()==1){
                    initComponents();
                    configurarFrm();
                    agregarDocLis();
                }
                else{
                    mostrarMsgInf("Este programa sólo puede ser ejecutado por el Administrador del Sistema. JOTA");
                    dispose();
                }
                
            }
            else
            {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            } 
        }
        catch (CloneNotSupportedException e){      this.setTitle(this.getTitle() + " [ERROR]");      }
    }
    private int intIsCotVen=0;
    
    /* Ventana de Cotizaciones de Venta */
    public ZafGenSolTra_Adm(ZafParSis obj, int j) 
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
             objUti=new ZafUtil();
            intCodEmpSolTra = objParSis.getCodigoEmpresa();
            intCodLocSolTra = objParSis.getCodigoLocal();
            intIsCotVen=j;
            
            initComponents();
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(570, 10, 100, 20);
            dtpFecDoc.setEnabled(false);  /// TEMPORAL!!! 
//            configurarFrm();
//            agregarDocLis();
            
        }
        catch (CloneNotSupportedException e){      this.setTitle(this.getTitle() + " [ERROR]");      }
    }
     
     

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        lblBodOrg = new javax.swing.JLabel();
        txtCodBodOrg = new javax.swing.JTextField();
        txtNomBodOrg = new javax.swing.JTextField();
        butBodOrg = new javax.swing.JButton();
        lblBodDes = new javax.swing.JLabel();
        txtCodBodDes = new javax.swing.JTextField();
        txtNomBodDes = new javax.swing.JTextField();
        butBodDes = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblPeso = new javax.swing.JLabel();
        panDet = new javax.swing.JPanel();
        panTabDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenTot = new javax.swing.JPanel();
        panLblObs = new javax.swing.JPanel();
        lblObs2 = new javax.swing.JLabel();
        panTxtObs = new javax.swing.JPanel();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panBar = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();

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
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setMinimumSize(new java.awt.Dimension(148, 113));

        panFil.setPreferredSize(new java.awt.Dimension(0, 330));
        panFil.setLayout(new java.awt.BorderLayout());

        panCab.setMinimumSize(new java.awt.Dimension(0, 0));
        panCab.setPreferredSize(new java.awt.Dimension(0, 95));
        panCab.setLayout(null);

        lblBodOrg.setText("Bodega Origen:");
        panCab.add(lblBodOrg);
        lblBodOrg.setBounds(10, 30, 110, 14);

        txtCodBodOrg.setEditable(false);
        txtCodBodOrg.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodBodOrg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodOrgFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodOrgFocusLost(evt);
            }
        });
        txtCodBodOrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodOrgActionPerformed(evt);
            }
        });
        panCab.add(txtCodBodOrg);
        txtCodBodOrg.setBounds(140, 30, 50, 20);

        txtNomBodOrg.setEditable(false);
        txtNomBodOrg.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNomBodOrg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodOrgFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodOrgFocusLost(evt);
            }
        });
        txtNomBodOrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodOrgActionPerformed(evt);
            }
        });
        panCab.add(txtNomBodOrg);
        txtNomBodOrg.setBounds(190, 30, 210, 20);

        butBodOrg.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butBodOrg.setText("...");
        butBodOrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodOrgActionPerformed(evt);
            }
        });
        panCab.add(butBodOrg);
        butBodOrg.setBounds(400, 30, 20, 20);

        lblBodDes.setText("Bodega Destino:");
        panCab.add(lblBodDes);
        lblBodDes.setBounds(10, 70, 130, 14);

        txtCodBodDes.setEditable(false);
        txtCodBodDes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodBodDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodDesFocusLost(evt);
            }
        });
        txtCodBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodDesActionPerformed(evt);
            }
        });
        panCab.add(txtCodBodDes);
        txtCodBodDes.setBounds(140, 70, 50, 20);

        txtNomBodDes.setEditable(false);
        txtNomBodDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodDesFocusLost(evt);
            }
        });
        txtNomBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodDesActionPerformed(evt);
            }
        });
        panCab.add(txtNomBodDes);
        txtNomBodDes.setBounds(190, 70, 210, 20);

        butBodDes.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butBodDes.setText("...");
        butBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodDesActionPerformed(evt);
            }
        });
        panCab.add(butBodDes);
        butBodDes.setBounds(400, 70, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        panCab.add(lblFecDoc);
        lblFecDoc.setBounds(422, 10, 140, 14);

        jButton1.setText("CargarItems");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panCab.add(jButton1);
        jButton1.setBounds(540, 30, 130, 23);

        jLabel1.setText("Peso:");
        panCab.add(jLabel1);
        jLabel1.setBounds(490, 70, 27, 14);

        lblPeso.setText("jLabel2");
        panCab.add(lblPeso);
        lblPeso.setBounds(544, 70, 120, 14);

        panFil.add(panCab, java.awt.BorderLayout.NORTH);

        panDet.setLayout(new java.awt.BorderLayout());

        panTabDet.setLayout(new java.awt.BorderLayout());

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
        spnDet.setViewportView(tblDat);

        panTabDet.add(spnDet, java.awt.BorderLayout.CENTER);

        panDet.add(panTabDet, java.awt.BorderLayout.CENTER);

        panFil.add(panDet, java.awt.BorderLayout.CENTER);

        panGenTot.setMinimumSize(new java.awt.Dimension(70, 30));
        panGenTot.setPreferredSize(new java.awt.Dimension(34, 58));
        panGenTot.setLayout(new java.awt.BorderLayout());

        panLblObs.setPreferredSize(new java.awt.Dimension(100, 15));
        panLblObs.setRequestFocusEnabled(false);
        panLblObs.setLayout(new java.awt.GridLayout(2, 1));

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panLblObs.add(lblObs2);

        panGenTot.add(panLblObs, java.awt.BorderLayout.WEST);

        panTxtObs.setPreferredSize(new java.awt.Dimension(579, 70));
        panTxtObs.setLayout(new java.awt.GridLayout(0, 1));

        txaObs2.setLineWrap(true);
        txaObs2.setText("JoseMario");
        txaObs2.setPreferredSize(new java.awt.Dimension(104, 25));
        spnObs2.setViewportView(txaObs2);

        panTxtObs.add(spnObs2);

        panGenTot.add(panTxtObs, java.awt.BorderLayout.CENTER);

        panFil.add(panGenTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panFil);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);
        tabFrm.getAccessibleContext().setAccessibleName("General");

        panBar.setPreferredSize(new java.awt.Dimension(0, 58));
        panBar.setLayout(null);

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        panBar.add(btnGuardar);
        btnGuardar.setBounds(490, 10, 90, 30);

        btnExit.setText("EXIT");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        panBar.add(btnExit);
        btnExit.setBounds(590, 10, 80, 30);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexión si está abierta.
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e){
            dispose();
        }
}//GEN-LAST:event_exitForm

    private void txtCodBodOrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodOrgActionPerformed
        txtCodBodOrg.transferFocus();
    }//GEN-LAST:event_txtCodBodOrgActionPerformed

    private void txtCodBodOrgFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodOrgFocusGained
        strCodBodOrg=txtCodBodOrg.getText();
        txtCodBodOrg.selectAll();
    }//GEN-LAST:event_txtCodBodOrgFocusGained

    private void txtCodBodOrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodOrgFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
         
        if (!txtCodBodOrg.getText().equalsIgnoreCase(strCodBodOrg)) 
        {
            if (txtCodBodOrg.getText().equals(""))
            {
                txtCodBodOrg.setText("");
                txtNomBodOrg.setText("");
            } 
            else 
            {
                configurarBodegaOrigen();
                mostrarBodegaOrigen(1);
                limpiarTabla();
            }
        } 
        else
            txtCodBodOrg.setText(strCodBodOrg);

         
         
    }//GEN-LAST:event_txtCodBodOrgFocusLost

    private void txtNomBodOrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodOrgActionPerformed
        txtNomBodOrg.transferFocus();
    }//GEN-LAST:event_txtNomBodOrgActionPerformed

    private void txtNomBodOrgFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodOrgFocusGained
        strNomBodOrg=txtNomBodOrg.getText();
        txtNomBodOrg.selectAll();
    }//GEN-LAST:event_txtNomBodOrgFocusGained

    private void txtNomBodOrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodOrgFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        
   
    }//GEN-LAST:event_txtNomBodOrgFocusLost

    private void butBodOrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodOrgActionPerformed
       
        strCodBodOrg=txtCodBodOrg.getText();
        configurarBodegaOrigen();
        mostrarBodegaOrigen(0);

        if (! txtCodBodOrg.getText().equals(strCodBodOrg)) {
            limpiarTabla();
        }

      
        
        
    }//GEN-LAST:event_butBodOrgActionPerformed

    private void txtCodBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodDesActionPerformed
        
    }//GEN-LAST:event_txtCodBodDesActionPerformed

    private void txtCodBodDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodDesFocusGained
         
    }//GEN-LAST:event_txtCodBodDesFocusGained

    private void txtCodBodDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodDesFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
         
            if (!txtCodBodDes.getText().equalsIgnoreCase(strCodBodDes)){
                if (txtCodBodDes.getText().equals("")){
                    txtCodBodDes.setText("");
                    txtNomBodDes.setText("");
                } 
                else{
                    configurarBodegaDestino();
                    mostrarBodegaDestino(1);
                }
            } 
            else{
                txtCodBodDes.setText(strCodBodDes);
            }
          
 
    }//GEN-LAST:event_txtCodBodDesFocusLost

    private void txtNomBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodDesActionPerformed
          
    }//GEN-LAST:event_txtNomBodDesActionPerformed

    private void txtNomBodDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodDesFocusGained
         
    }//GEN-LAST:event_txtNomBodDesFocusGained

    private void txtNomBodDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodDesFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        
    }//GEN-LAST:event_txtNomBodDesFocusLost

    private void butBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodDesActionPerformed
        strCodBodDes=txtCodBodDes.getText();
        configurarBodegaDestino();
        mostrarBodegaDestino(0);
        
    }//GEN-LAST:event_butBodDesActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if(generaSolicitudTransferenciaInventario()){
            System.out.println("exito");
            mostrarMsgInf("OK Solicitud! ");
        }
        else{
            System.out.println("noooo");
            mostrarMsgInf("ERROR!! ");
        }        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(cargarArregloItemsGrupo()){
            System.out.println("cargar OK");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    //<editor-fold defaultstate="collapsed" desc="/* Variables declaration - do not modify */">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton butBodDes;
    private javax.swing.JButton butBodOrg;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblBodDes;
    private javax.swing.JLabel lblBodOrg;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPeso;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenTot;
    private javax.swing.JPanel panLblObs;
    private javax.swing.JPanel panTabDet;
    private javax.swing.JPanel panTxtObs;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodBodDes;
    private javax.swing.JTextField txtCodBodOrg;
    private javax.swing.JTextField txtNomBodDes;
    private javax.swing.JTextField txtNomBodOrg;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
    
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            
            objDocLis=new ZafDocLis();
            
            //Obtener los permisos por Usuario y Programa.
            objPerUsr=new ZafPerUsr(objParSis);
            
            
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);  //Titulo y version
            lblTit.setText(strAux);
             
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            
            //Configurar ZafDatePicker: Para la fecha 
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(570, 10, 100, 20);
            dtpFecDoc.setEnabled(true);   
            //fin config zafDatePicker
             
            txtCodBodOrg.setBackground(objParSis.getColorCamposObligatorios());  
            txtNomBodOrg.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBodDes.setBackground(objParSis.getColorCamposObligatorios());
            txtCodBodDes.setBackground(objParSis.getColorCamposObligatorios());
             
         
            
            configurarBodegaDestino();
            configurarBodegaOrigen();
             
            configurarTblDat();
     
        }
        catch(Exception e){       blnRes=false;         objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM_DOS,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_BTN_ITM,"");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre del Item");
            vecCab.add(INT_TBL_DAT_UNI_MED,"Unidad");
            vecCab.add(INT_TBL_DAT_CAN,"Cantidad");
            vecCab.add(INT_TBL_DAT_PES_UNI_ITM,"Pes.Uni.(Kg)");
            vecCab.add(INT_TBL_DAT_PES_TOT_ITM,"Pes.Tot.(Kg)");
            vecCab.add(INT_TBL_DAT_CAN_ACT_STK,"stock");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM_DOS).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BTN_ITM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(245);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PES_UNI_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ACT_STK).setPreferredWidth(70);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BTN_ITM).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafGenSolTra_Adm.ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Alineamiento de columnas, formato a las columnas para los decimales por ejemplo
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_PES_UNI_ITM).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_ITM).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ACT_STK).setCellRenderer(objTblCelRenLblNum);  // JoséMarín: NO se lo esta mostrando, se deja configurado por si deciden mostrar
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_COD_ALT_ITM);
            vecAux.add("" + INT_TBL_DAT_CAN);
            vecAux.add("" + INT_TBL_DAT_BTN_ITM);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
             //columnas ocultas
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_DAT_COD_ITM);
            arlColHid.add(""+INT_TBL_DAT_CAN_ACT_STK);  // 
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            
            //botones agregados, hacer una columna de botones 
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BTN_ITM).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            //setEditable(false);
            
            //new
            int intColVen[]=new int[7];
            intColVen[0]=1; //co_itm
            intColVen[1]=2; //tx_codAlt
            intColVen[2]=3; //tx_codAlt2
            intColVen[3]=4; //tx_nomItm
            intColVen[4]=5; //tx_desCor
            intColVen[5]=6; //nd_pesItmKgr
            intColVen[6]=7; //nd_canDis
            int intColTbl[]=new int[7];
            intColTbl[0]=INT_TBL_DAT_COD_ITM;
            intColTbl[1]=INT_TBL_DAT_COD_ALT_ITM;
            intColTbl[2]=INT_TBL_DAT_COD_ALT_ITM_DOS;
            intColTbl[3]=INT_TBL_DAT_NOM_ITM;
            intColTbl[4]=INT_TBL_DAT_UNI_MED;
            intColTbl[5]=INT_TBL_DAT_PES_UNI_ITM;
            intColTbl[6]=INT_TBL_DAT_CAN_ACT_STK;
            
            //Cod.Alt.
            objTblCelEdiTxtVcoCodAltItm=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellEditor(objTblCelEdiTxtVcoCodAltItm);
            objTblCelEdiTxtVcoCodAltItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {  
               
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    if (!(txtCodBodOrg.getText().length() > 0))  //Si no tiene Bodega de origen 
                    {
                        mostrarMsgInf("La Bodega de Origen es Obligatoria para poder elegir los Items");
                        objTblCelEdiTxtVcoCodAltItm.setCancelarEdicion(true);
                        txtCodBodOrg.requestFocus();
                    }
                    else if (!(txtCodBodDes.getText().length() > 0)) //Si no tiene Bodega de destino 
                    {
                        mostrarMsgInf("La Bodega de Destino es Obligatoria para poder elegir los Items");
                        objTblCelEdiTxtVcoCodAltItm.setCancelarEdicion(true);
                        txtCodBodDes.requestFocus();
                    }
                }
                
 
                 
            });
            
            //Boton ítem.
            objTblCelEdiButVcoItm=new ZafTblCelEdiButVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BTN_ITM).setCellEditor(objTblCelEdiButVcoItm);
            objTblCelEdiButVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    if (!(txtCodBodOrg.getText().length() > 0))  //Si no tiene Bodega de origen 
                    {
                        mostrarMsgInf("La Bodega de Origen es Obligatoria para poder elegir los Items");
                        objTblCelEdiButVcoItm.setCancelarEdicion(true);
                        limpiarTabla();
                        txtCodBodOrg.requestFocus();
                    }
                    else if (!(txtCodBodDes.getText().length() > 0)) //Si no tiene Bodega de destino 
                    {
                        mostrarMsgInf("La Bodega de Destino es Obligatoria para poder elegir los Items");
                        objTblCelEdiButVcoItm.setCancelarEdicion(true);
                        limpiarTabla();
                        txtCodBodDes.requestFocus();
                    }
                    
                }    
                
                
                 
                
            });
                
            intColVen=null;
            intColTbl=null;
            
            //Cantidad
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                Double dblCantidad,dblCostoUnitario,dblPesoUnitario,dblCostoTotal,dblPesoTotal;
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    int intCelSel = tblDat.getSelectedRow();
                    if(objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN))>0){
                        if (objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN))<=
                            (objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_ACT_STK))))
                        {
                            dblCantidad=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN));
                           objTblMod.insertRow();
                        }
                        else
                        {
                            mostrarMsgInf("La cantidad solicitada es menor a lo disponible");
                            objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_CAN);
                        }
                    }
                                         
                }
            });
            //objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objDocLis=new ZafDocLis();
            
            // Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);  
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            
            
        }
        catch(Exception e)   {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
      
     
     

    public void setEditable(boolean editable) 
    {
        if (editable == true) 
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
        else 
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
    }
 
 
    private void limpiarTabla() 
    {
        objTblMod.setDataModelChanged(false);
        objTblMod.removeAllRows();
    }

    
    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algún cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
    }
    
    
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        
        txtCodBodOrg.getDocument().addDocumentListener(objDocLis);
        txtNomBodOrg.getDocument().addDocumentListener(objDocLis);
        txtCodBodDes.getDocument().addDocumentListener(objDocLis);
        txtNomBodDes.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
    } 
    
    
     /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }
     
    
    
    /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        { 
            
            txtCodBodOrg.setText("");
            txtNomBodOrg.setText("");
            
            txtCodBodDes.setText("");
            txtNomBodDes.setText("");
            
            dtpFecDoc.setText("");
            
            
            txaObs2.setText("");
            
            limpiarTabla();
            
        }
        catch (Exception e){         blnRes=false;      }
        return blnRes;
    }
    Date dtpFechaDocumento;

    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private ZafMovIngEgrInv objMovInv;
    private Object objIsGenOD = null;
    private String strMerIngEgrFisBod="N";
    private ZafCfgBod objCfgBod;
    
    private boolean insertarReg()
    {
        boolean blnRes=false;
        java.sql.Connection conLoc;
        int intCodBodGrpEgr,intCodBodGrpIng;
        try
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conLoc.setAutoCommit(false);
            if (conLoc!=null){
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//                 dtpFechaDocumento = objUti.parseDate(objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()), "yyyy/MM/dd") ;
                dtpFechaDocumento =  datFecAux;
                System.out.println("fecha.."  + dtpFechaDocumento);
                  
                 
            }
            conLoc.close();
            conLoc=null;
        }
        catch (java.sql.SQLException e){        objUti.mostrarMsgErr_F1(this, e);       }
        catch (Exception e){         objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }

    
    

     /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }
 

    private boolean mostrarBodegaOrigen(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoBodOrg.setCampoBusqueda(0);
                    vcoBodOrg.setVisible(true);
                   if (vcoBodOrg.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodBodOrg.setText(vcoBodOrg.getValueAt(1));
                        txtNomBodOrg.setText(vcoBodOrg.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Busqueda por Código".
                    if (vcoBodOrg.buscar("a1.co_bod", txtCodBodOrg.getText()))
                    {
                        txtCodBodOrg.setText(vcoBodOrg.getValueAt(1));
                        txtNomBodOrg.setText(vcoBodOrg.getValueAt(2));
                    }
                    else
                    {
                        vcoBodOrg.setCampoBusqueda(1);
                        vcoBodOrg.setCriterio1(11);
                        vcoBodOrg.cargarDatos();
                        vcoBodOrg.setVisible(true);
                        if (vcoBodOrg.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodBodOrg.setText(vcoBodOrg.getValueAt(1));
                            txtNomBodOrg.setText(vcoBodOrg.getValueAt(2));
                        }
                        else
                        {
                            txtCodBodOrg.setText(strCodBodOrg);
                        }
                    }
                    break;
               case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoBodOrg.buscar("a1.tx_nom", txtNomBodOrg.getText()))
                    {
                         txtCodBodOrg.setText(vcoBodOrg.getValueAt(1));
                         txtNomBodOrg.setText(vcoBodOrg.getValueAt(2));
                    }
                    else
                    {
                         vcoBodOrg.setCampoBusqueda(2);
                        vcoBodOrg.setCriterio1(11);
                        vcoBodOrg.cargarDatos();
                        vcoBodOrg.setVisible(true);
                        if (vcoBodOrg.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodBodOrg.setText(vcoBodOrg.getValueAt(1));
                            txtNomBodOrg.setText(vcoBodOrg.getValueAt(2));
                        }
                        else
                        {
                            txtNomBodOrg.setText(strCodBodOrg);
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
    
    private boolean mostrarBodegaDestino(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoBodDes.setCampoBusqueda(0);
                    vcoBodDes.setVisible(true);
                    if (vcoBodDes.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                        txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Busqueda por Código".
                    if (vcoBodDes.buscar("a1.co_bod", txtCodBodDes.getText()))
                    {
                        txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                        txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                    }
                    else
                    {
                        vcoBodDes.setCampoBusqueda(1);
                        vcoBodDes.setCriterio1(11);
                        vcoBodDes.cargarDatos();
                        vcoBodDes.setVisible(true);
                        if (vcoBodDes.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                            txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        }
                        else
                        {
                            txtCodBodDes.setText(strCodBodDes);
                        }
                    }
                    break;
               case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoBodDes.buscar("a1.tx_nom", txtNomBodDes.getText()))
                    {
                         txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                         txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                    }
                    else
                    {
                         vcoBodDes.setCampoBusqueda(2);
                        vcoBodDes.setCriterio1(11);
                        vcoBodDes.cargarDatos();
                        vcoBodDes.setVisible(true);
                        if (vcoBodDes.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                            txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        }
                        else
                        {
                            txtNomBodDes.setText(strCodBodDes);
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
     
    
    private boolean configurarBodegaOrigen()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_bod");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("350");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1)//Admin
            {
                strSQL="";
                strSQL+=" SELECT co_bod, tx_nom ";
                strSQL+=" FROM tbm_bod";
                strSQL+=" WHERE co_emp="+ objParSis.getCodigoEmpresaGrupo()+" AND st_reg IN ('A','P')";
                if(txtCodBodDes.getText().length()>0)
                    strSQL+=" AND co_bod !="+txtCodBodDes.getText();
                strSQL+=" ORDER BY co_bod";
            }
             
            //  System.out.println("configurarBodegaOrigen: \n" + strSQL);
            vcoBodOrg=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodega Origen", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoBodOrg.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
     
    
    private boolean configurarBodegaDestino()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_bod");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("350");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1) //Admin
            {
                strSQL="";
                strSQL+=" SELECT co_bod, tx_nom ";
                strSQL+=" FROM tbm_bod";
                strSQL+=" WHERE co_emp="+ objParSis.getCodigoEmpresaGrupo()+" AND st_reg IN ('A','P')";
                strSQL+=" ORDER BY co_bod";
            }
             
            //  System.out.println("configurarBodegaDestino: \n" + strSQL);
            vcoBodDes=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodega Destino", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoBodDes.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
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
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código del Item";
                    break;
                 case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg="Código Alterno del Item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM_DOS:
                    strMsg="Código alterno 2 del ítem";
                break;
                case INT_TBL_DAT_BTN_ITM:
                    strMsg="Listado de items";
                    break;
               case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del Item";
                    break;
               case INT_TBL_DAT_UNI_MED:
                    strMsg="Unidad de medida";
                    break;
               case INT_TBL_DAT_CAN:
                    strMsg="Cantidad";
                    break;
               case INT_TBL_DAT_PES_UNI_ITM:
                    strMsg="Peso unitario (Kg)";
                    break;
               case INT_TBL_DAT_PES_TOT_ITM:
                    strMsg="Peso total (Kg)";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
     

    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     *
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     * @param blnMosBotCan Un valor booleano que deteremina si se debe mostrar
     * el botón "Cancelar".
     * @return La opción que seleccionó el usuario.
     */
    private int mostrarMsgCon(String strMsg, boolean blnMosBotCan)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, (blnMosBotCan==true?javax.swing.JOptionPane.YES_NO_CANCEL_OPTION:javax.swing.JOptionPane.YES_NO_OPTION), javax.swing.JOptionPane.QUESTION_MESSAGE);
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
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Número de documento <FONT COLOR=\"red\">ya en uso </FONT>. <BR>.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }
      
     
    private String strSql;
   
    
    
    private boolean generaSolicitudTransferenciaInventario(){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        int intCodTipDoc=269,intUltCodDoc=-1, intUltNumDoc=-1;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                conLoc.setAutoCommit(false);
                
                intUltCodDoc=getUltCodDoc(conLoc,intCodTipDoc);
                intUltNumDoc=getUltNumDoc(conLoc,intCodTipDoc);  
                if (insertarCab(conLoc,intCodTipDoc, intUltCodDoc,intUltNumDoc)){
                    if(insertarDet(conLoc,intCodTipDoc,intUltCodDoc)){
                        conLoc.commit();
                        blnRes=true;
                    }else{conLoc.rollback();}
                }else{conLoc.rollback();}
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e) {
            System.err.println("ERROR: " + e);
            objUti.mostrarMsgErr_F1(this, e);      
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            objUti.mostrarMsgErr_F1(this, e);      
        }  
        return blnRes;
    }
    
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab(java.sql.Connection conn, int intCodTipDoc, int intUltCodDoc,int intUltNumDoc)
    {
        boolean blnRes=true;
        try
        {
            if (conn!=null)
            {
                stm=conn.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null) {
                    return false;  // Si no se puede obtener, la fecha 
                }
                strSQL="";
                strSQL+=" INSERT INTO tbm_cabSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,fe_doc,ne_numDoc, co_bodOrg, co_bodDes, nd_pesTotKgr, \n";
                strSQL+="                              st_imp, tx_obs1, tx_obs2,st_reg,fe_ing,co_usrIng,tx_comIng,st_aut,st_conInv)  \n";
                strSQL+=" VALUES ("+objParSis.getCodigoEmpresaGrupo()+","+objParSis.getCodigoLocal()+","+intCodTipDoc+","+intUltCodDoc+", \n";
                strSQL+="        '"+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos())+"',";
                strSQL+="        "+intUltNumDoc+","+txtCodBodOrg.getText()+","+txtCodBodDes.getText()+","+lblPeso.getText()+",'N',";
                strSQL+="null,"+objUti.codificar(txaObs2.getText())+", 'A', \n";
                strSQL+="        '"+objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos())+"',";
                strSQL+="       "+objParSis.getCodigoUsuario()+",'"+objParSis.getNombreComputadoraConDirIP()+"',null,'P');";
                strSQL+=" UPDATE tbm_cabTipDoc set ne_ultDoc=ne_ultDoc+1 where co_emp="+objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc="+objParSis.getCodigoLocal()+" AND co_tipDoc="+intCodTipDoc+";";
                strSQL+=" \n ";
                stm.executeUpdate(strSQL);
            }
            stm.close();
            stm=null;
            datFecAux=null;
        }
        catch (java.sql.SQLException e){        
            blnRes=false;          
            objUti.mostrarMsgErr_F1(this, e);       
        }
        catch (Exception e){           
            blnRes=false;           
            objUti.mostrarMsgErr_F1(this, e);        
        }
        return blnRes;
    }
    
    
    
     
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDet(java.sql.Connection conn, int intCodTipDoc, int intUltCodDoc){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try
        {
            if (conn!=null)
            {
                strSQL="";
                stmLoc=conn.createStatement();
                //Obtener la fecha del servidor.
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    strSQL+=" INSERT INTO tbm_detSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_itm,nd_can,nd_pesUniKgr,nd_pesTotKgr) \n";
                    strSQL+=" VALUES (";
                    strSQL+=" " + objParSis.getCodigoEmpresaGrupo() + ",";
                    strSQL+=" 1,";
                    strSQL+=" " + intCodTipDoc + ",";
                    strSQL+=" " + intUltCodDoc + ",";
                    strSQL+=" " + (i+1) + ",";
                    strSQL+=" " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM) + ",";
                    strSQL+=" " + objTblMod.getValueAt(i,INT_TBL_DAT_CAN) + ",";
                    strSQL+=" " + objTblMod.getValueAt(i,INT_TBL_DAT_PES_UNI_ITM) + ",";
                    strSQL+=" " + objTblMod.getValueAt(i,INT_TBL_DAT_PES_TOT_ITM);
                    strSQL+=" );\n";
                }        
                System.out.println("insertarDet " + strSQL);
                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;
            
            }
            datFecAux=null;
        }
        catch (java.sql.SQLException e){         
            blnRes=false;         
            objUti.mostrarMsgErr_F1(this, e);      
        }
        catch (Exception e){          
            blnRes=false;           
            objUti.mostrarMsgErr_F1(this, e);        
        }
        return blnRes;
    }
    
     
    
    private int getUltCodDoc(java.sql.Connection conn, int codTipDoc) 
    {
        int intRes = 0;
        try
        {
            java.sql.Statement stmLoc; 
            java.sql.ResultSet rstLoc;
            if (conn!=null){
                stmLoc=conn.createStatement();
                strSQL="";
                strSQL+=" SELECT CASE WHEN MAX (co_doc) IS NULL THEN 1 ELSE MAX(co_doc) + 1 END AS co_doc  \n";
                strSQL+=" FROM tbm_cabSolTraInv as a1 \n";
                strSQL+=" WHERE a1.co_emp="+objParSis.getCodigoEmpresaGrupo()+" AND a1.co_loc=1 AND\n";
                strSQL+="       a1.co_tipDoc="+codTipDoc+" \n";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                       intRes = rstLoc.getInt("co_doc");
                } 
                rstLoc.close();
                rstLoc=null; 
                stmLoc.close();
                stmLoc=null;
           }
        }
        catch (java.sql.SQLException e){        
            objUti.mostrarMsgErr_F1(this, e);      
            intRes = 0;       
        }
        catch (Exception e){        
            objUti.mostrarMsgErr_F1(this, e);           
            intRes = 0;        
        }
        return intRes;
    }
    
    
    private int getUltNumDoc(java.sql.Connection conn,int codTipDoc)
    {
        int intRes=0;
        try
        {
            java.sql.Statement stmLoc; 
            java.sql.ResultSet rstLoc;
           if (conn!=null){
                stmLoc=conn.createStatement();
                strSQL="";
                strSQL+=" SELECT CASE WHEN (a1.ne_ultdoc) IS NULL THEN 1 ELSE (a1.ne_ultdoc+1) END AS ne_ultDoc \n";
                strSQL+=" FROM tbm_cabTipDoc as a1 \n";
                strSQL+=" WHERE a1.co_emp="+objParSis.getCodigoEmpresaGrupo();
                strSQL+=" AND a1.co_loc=1";
                strSQL+=" AND a1.co_tipDoc="+codTipDoc+" \n";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intRes = rstLoc.getInt("ne_ultDoc");
                } 
                rstLoc.close();
                rstLoc=null; 
                stmLoc.close();
                stmLoc=null;
           }
        }
        catch (java.sql.SQLException e){        objUti.mostrarMsgErr_F1(this, e);          intRes = 0;       }
        catch (Exception e){           objUti.mostrarMsgErr_F1(this, e);           intRes = 0;       }
        return intRes;
    }
    
    
    private boolean cargarArregloItemsGrupo(){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        Double dblPesTot=0.00;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a3.co_emp, a3.co_itm, a3.tx_codAlt, a3.tx_codAlt2, a3.tx_nomItm, a4.tx_desCor,  \n";
                strSql+="        CASE WHEN a3.nd_pesItmKgr IS NULL THEN 0 ELSE ROUND(a3.nd_pesItmKgr,2) END AS nd_pesItmKgr,   \n";
                strSql+="        CASE WHEN a3.nd_cosUni IS NULL THEN 0 ELSE ROUND(a3.nd_cosUni,2) END as nd_cosUni,  \n";
                strSql+="        ROUND(a1.nd_canDis,2) as nd_canDis,a3.st_ser   \n";
                strSql+=" FROM (  \n";
                strSql+="       SELECT z.co_empGrp AS co_emp, z.co_itmMae, SUM(ABS(z.nd_canDis)) AS nd_canDis  \n";
                strSql+="       FROM(  \n";
                strSql+="           SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_canDis, a2.co_itmMae , a3.co_empGrp    \n";
                strSql+="           FROM tbm_invBod AS a1   \n";
                strSql+="           INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)  \n";
                strSql+="           INNER JOIN tbr_bodEmpBodGrp as a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)  \n";
                strSql+="           WHERE a3.co_empGrp=0 AND a3.co_bodGrp=3 \n";
                strSql+="       ) AS z       \n";
                strSql+="       GROUP BY z.co_empGrp, z.co_itmMae   \n";
                strSql+=" ) as a1   \n";
                strSql+=" INNER JOIN tbm_equInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itmMae=a2.co_itmMae)  \n";
                strSql+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)  \n";
                strSql+=" LEFT OUTER JOIN tbm_var as a4 ON (a3.co_uni=a4.co_reg)  \n";
                strSql+=" WHERE a3.tx_codAlt NOT LIKE ('%L') AND a3.st_ser='N' AND a1.nd_canDis > 0  \n";
                strSql+=" ORDER BY a3.tx_codAlt \n";
                strSql+="   \n";
                System.out.println("cargarArregloItemsGrupo: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_ITM, rstLoc.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM, rstLoc.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM_DOS, rstLoc.getString("tx_codAlt2"));
                    vecReg.add(INT_TBL_DAT_BTN_ITM,"");
                    vecReg.add(INT_TBL_DAT_NOM_ITM,rstLoc.getString("tx_nomItm"));
                    vecReg.add(INT_TBL_DAT_UNI_MED,"");
                    vecReg.add(INT_TBL_DAT_CAN,objUti.redondear(rstLoc.getString("nd_canDis"), objParSis.getDecimalesBaseDatos()) );
                    vecReg.add(INT_TBL_DAT_PES_UNI_ITM, rstLoc.getString("nd_pesItmKgr") );
                    vecReg.add(INT_TBL_DAT_PES_TOT_ITM,objUti.redondear(rstLoc.getDouble("nd_pesItmKgr")*rstLoc.getDouble("nd_canDis"), objParSis.getDecimalesBaseDatos()));
                    vecReg.add(INT_TBL_DAT_CAN_ACT_STK,objUti.redondear(rstLoc.getString("nd_canDis"), objParSis.getDecimalesBaseDatos()));
                    vecDat.add(vecReg);
                    dblPesTot = dblPesTot + objUti.redondear(rstLoc.getDouble("nd_pesItmKgr")*rstLoc.getDouble("nd_canDis"), objParSis.getDecimalesBaseDatos());
                }
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                lblPeso.setText(dblPesTot+"");
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e) {
            System.err.println("ERROR: " + e);
            objUti.mostrarMsgErr_F1(this, e);      
            
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);      
            System.err.println("ERROR: " + e);
        }  
        return blnRes;
    }
    
    
    
      
}