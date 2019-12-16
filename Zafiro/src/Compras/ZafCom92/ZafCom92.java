/*
 * ZafCom92.java
 *
 * Created on 19 Enero , 2016, 8:44 AM
 */
package Compras.ZafCom92;
import Librerias.ZafCfgBod.ZafCfgBod;
import Librerias.ZafCfgSer.ZafCfgSer;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafGenFacAut.ZafGenFacAutAdm;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafMovIngEgrInv.ZafReaMovInv;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import javax.swing.JOptionPane;

/**
 *
 * @author  José Marín 
 */
public class ZafCom92 extends javax.swing.JInternalFrame
{
    //Constantes: JTable
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_COD_EMP=1;
    private final int INT_TBL_DAT_COD_LOC=2;
    private final int INT_TBL_DAT_COD_TIP_DOC=3;
    private final int INT_TBL_DAT_DES_COR_TIP_DOC=4;
    private final int INT_TBL_DAT_DES_LAR_TIP_DOC=5;
    private final int INT_TBL_DAT_COD_DOC=6;
    private final int INT_TBL_DAT_NUM_DOC=7;
    private final int INT_TBL_DAT_FEC_DOC=8;
    private final int INT_TBL_DAT_CON_SOL_TRA=9;
    private final int INT_TBL_DAT_CHK_AUT=10;
    private final int INT_TBL_DAT_CHK_DEN=11;
    private final int INT_TBL_DAT_OBS_AUT=12;
    private final int INT_TBL_DAT_CON_OBS=13;
    
    /* CONSTANTES PARA CONTENEDOR A ENVIAR JoséMario 11/Marzo/2016  */
    final int INT_ARL_COD_EMP=0;
    final int INT_ARL_COD_LOC=1;
    final int INT_ARL_COD_TIP_DOC=2;
    final int INT_ARL_COD_BOD_GRP=3;  // TRABAJA POR GRUPO!!!! 
    final int INT_ARL_COD_ITM=4;
    final int INT_ARL_COD_ITM_MAE=5;
    final int INT_ARL_COD_BOD=6;
    final int INT_ARL_NOM_BOD=7;
    final int INT_ARL_CAN_COM=8;
    final int INT_ARL_CHK_CLI_RET=9;
    final int INT_ARL_EST_BOD=10;
    final int INT_ARL_ING_EGR_FIS_BOD=11;
    final int INT_ARL_COD_BOD_GRP_ING=12;  /// TRABAJA POR GRUPO !!!
    
    //Constantes: Para el contenedor a enviar solicitud de Cotizaciones de Venta JoséMario 26/Abril/2015.
    final int INT_ARL_COT_VEN_COD_EMP=0;
    final int INT_ARL_COT_VEN_COD_LOC=1;
    final int INT_ARL_COT_VEN_COD_TIP_DOC=2;
    final int INT_ARL_COT_VEN_COD_DOC=3;
    final int INT_ARL_COT_VEN_COD_BOD_EGR=4;
    
    //Códigos de configuración.
    private static final int INT_ARL_COD_CFG_SOL_SOTRINI=2002;  // CODIGO DE LA CONFIGURACION PARA SOLICITUDES DE IMPORTACIONES
    private static final int INT_ARL_COD_CFG_SOL_SOTRINC=2003;  // CODIGO DE LA CONFIGURACION PARA SOLICITUDES DE COMPRAS LOCALES
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafUtil objUti;                                    //Objeto del tipo de la clase ZafUtil, el cual me va a permitir 
    private ZafTblMod objTblMod;
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelRenBut objTblCelRenBut, objTblCelRenButDG1;//Render: Presentar JButton en JTable.
    private ZafTblOrd objTblOrd;
    private ZafTblBus objTblBus;
    private ZafThreadGUI objThrGUI;
    private ZafThrGua objThrGua; 
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg;        //Editor: JButton en celda (Bodega origen).
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg2;       //Editor: JButton en celda (Bodega origen).    
    private ZafTblCelEdiButDlg objTblCelEdiButDlg;              //Editor: JButton en celda.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenLbl objTblCelRenLblCod;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk; 
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    private ZafTblCelEdiButGen objTblCelEdiButGenDG1;
    private ZafTblFilCab objTblFilCab;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario.
    private ZafDocLis objDocLis;
    private ZafTblCelRenBut objTblCelRenButConGre, objTblCelRenButConOcp;  
    private ZafReaMovInv objReaMovInv ;
    private GenOD.ZafGenOdPryTra objGenOD;
    private ZafCfgBod objCfgBod;
    private ZafCfgSer objCfgSer;
    private ZafImp objImp;
    
    private java.util.Date datFecAux;                           //Auxiliar: Para almacenar fechas.
    private java.awt.Component componente;
    private java.awt.Frame Frame;
    private Vector vecDat, vecReg, vecCab, vecAux;
    
    ArrayList arlDat = new ArrayList();
    ArrayList arlReg = new ArrayList();
    
    private ArrayList arlRegSolTra, arlDatSolTra;
    
    private ArrayList arlRegTraTuv,arlRegTraCas,arlRegTraDim;
    private ArrayList arlDatTraTuv,arlDatTraCas,arlDatTraDim;
    private boolean blnTraTuv=false,blnTraCas=false,blnTraDim=false;
       
    private boolean blnHayCam;
    private boolean blnIsDueBod;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo. // Continuidad del hilo
    
    private int intCodSer=16;// Impresiones mateo
    private int intCodEmpSolTra, intCodLocSolTra, intCodItmSolTra, intCodItmMaeSolTra;
    private int intCodBodOrgSolTra, intCodBodDesSolTra;
    
    private Double dblCanFal=0.0;
    
    private String strSQL, strAux, strNomBodSolTra;
    
    private String strVersion=" v0.1.04.08"; //JM - 4Ene2018
    
    /**
     * Constructor Principal
     * @param obj 
     */
    public ZafCom92(ZafParSis obj) {
        try{
            objUti = new ZafUtil();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            objTblCelEdiButGenDG1=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            componente=this;
            objCfgSer = new ZafCfgSer(objParSis);
            objCfgSer.cargaDatosIpHostServicios(0, intCodSer);
            objGenOD = new GenOD.ZafGenOdPryTra();
            objImp=new ZafImp(objParSis, componente);
            objReaMovInv = new Librerias.ZafMovIngEgrInv.ZafReaMovInv(objParSis,componente);

            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {  //AKI SE VALIDA QUE SOLO SE PUEDA ABRIR POR GRUPO 
                 initComponents();
                 configurarFrm();
             }
             else{
                 mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                 dispose();
             } 
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
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panRpt = new javax.swing.JPanel();
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        btnAdmin = new javax.swing.JButton();
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

        panRpt.setLayout(new java.awt.BorderLayout());

        panDet.setLayout(new java.awt.BorderLayout());

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

        panDet.add(spnDet, java.awt.BorderLayout.CENTER);

        panRpt.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panRpt);

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

        btnAdmin.setText("Admin");
        btnAdmin.setPreferredSize(new java.awt.Dimension(95, 25));
        btnAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminActionPerformed(evt);
            }
        });
        panBot.add(btnAdmin);

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
        String strTit, strMsg;
        try{
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
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

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        objTblMod.removeAllRows();
        if (butCon.getText().equals("Consultar")){
            blnCon=true;
            if (objThrGUI==null){
                objThrGUI=new ZafCom92.ZafThreadGUI();
                objThrGUI.start();    
            }            
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblMod.isDataModelChanged()){
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                if(objThrGua==null){
                    objThrGua=new ZafCom92.ZafThrGua();
                    objThrGua.start();
                }
            }
        }
        else {
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
        }

    }//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void btnAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminActionPerformed
        llamarVenAdmin();
        
    }//GEN-LAST:event_btnAdminActionPerformed

    private void llamarVenAdmin(){
        ZafGenFacAutAdm objGeneraFactura = new  ZafGenFacAutAdm(objParSis);
        this.getParent().add(objGeneraFactura,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objGeneraFactura.show();
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton btnAdmin;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }   
       
    
    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            objPerUsr=new ZafPerUsr(objParSis);
            this.setTitle(objParSis.getNombreMenu() + strVersion);
            lblTit.setText(objParSis.getNombreMenu());
            //Botón Genera Factura Automática
            if(objParSis.getCodigoUsuario()!= 1){
                System.out.println("NO ES EL ADMIN... ");
                btnAdmin.setVisible(false);
                btnAdmin.setEnabled(false);
            }
            
//            3995;2078;"0";"Guardar"
            
            if(!objPerUsr.isOpcionEnabled(3995)){
                butGua.setVisible(false);
            }
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_CON_SOL_TRA,"");
            vecCab.add(INT_TBL_DAT_CHK_AUT,"Autorizar");
            vecCab.add(INT_TBL_DAT_CHK_DEN,"Denegar");
            vecCab.add(INT_TBL_DAT_OBS_AUT,"Obs.Aut.");
            vecCab.add(INT_TBL_DAT_CON_OBS,"");
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
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();

            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_CON_SOL_TRA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_OBS_AUT).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CON_OBS).setPreferredWidth(20);
            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CON_SOL_TRA).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CON_OBS).setResizable(false);
            
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            
            objTblCelRenLblCod=new ZafTblCelRenLbl();
            objTblCelRenLblCod.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setCellRenderer(objTblCelRenLblCod);
            
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CON_SOL_TRA);
            vecAux.add("" + INT_TBL_DAT_CHK_AUT);
            vecAux.add("" + INT_TBL_DAT_CHK_DEN);
            vecAux.add("" + INT_TBL_DAT_OBS_AUT);
            vecAux.add("" + INT_TBL_DAT_CON_OBS);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            objTblFilCab=null;
            
            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_CON_SOL_TRA).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_CON_OBS).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;

            objTblCelRenButConOcp=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_CON_SOL_TRA).setCellRenderer(objTblCelRenButConOcp);
            objTblCelRenButConOcp = null;
            
            new ButSolTraInv(tblDat, INT_TBL_DAT_CON_SOL_TRA);
            
            ButObs butCot = new ButObs(tblDat, INT_TBL_DAT_CON_OBS); //*****
            
            
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setCellRenderer(objTblCelRenChk);
            
            //Check: Autorizar
            objTblCelEdiChk = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()  {
                int intFilSel;
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)  {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1)  { //Si se permite editar marcar/desmarcar
                        if(!objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_AUT)) {                    
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_AUT);
                        }
                        else  {
                            objTblMod.setValueAt(true, intFilSel, INT_TBL_DAT_CHK_AUT);
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_DEN);
                        }
                    }
                }
            });
                   
            //Check: Denegar
            objTblCelEdiChk = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()  {
                int intFilSel;
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)  {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1)  { //Si se permite editar marcar/desmarcar
                        if(!objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_DEN)) {                    
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_DEN);
                        }
                        else  {
                            objTblMod.setValueAt(true, intFilSel, INT_TBL_DAT_CHK_DEN);
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_AUT);
                        }
                    }
                }
            });
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblOrd=new ZafTblOrd(tblDat);
            objTblBus=new ZafTblBus(tblDat);
            objDocLis=new ZafDocLis();

            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);           
        }
        catch(Exception e)
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
    private boolean cargarDetReg(java.sql.Connection conn)
    {
        String strAux="";
        
        objUti=new ZafUtil();
        boolean blnRes=true;
        strAux="";
        try{
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            if (conn!=null){
                stm=conn.createStatement();
            if(objParSis.getCodigoUsuario()==1){
                strSQL=" ";
                strSQL+=" SELECT  a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a4.tx_desCor as tx_desCorTipDoc\n";
                strSQL+="       , a4.tx_desLar as tx_deslarTipDoc, a1.ne_numDoc, a1.fe_doc, a1.tx_obsAut as tx_obs, a1.st_aut \n";
                strSQL+=" FROM tbm_cabSolTraInv as a1 \n";
                strSQL+=" INNER JOIN tbr_tipDocPrg as a2 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipDoc=a2.co_tipDoc)\n";
                strSQL+=" INNER JOIN tbm_cabTipDoc as a4 ON (a1.co_emp=a4.co_emp and a1.co_tipDoc=a4.co_tipDoc and a1.co_loc=a4.co_loc) \n";
                strSQL+=" WHERE a1.st_reg='A'";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresaGrupo()+ " AND a1.st_aut IS NULL AND a1.st_conInv='P' \n";
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.fe_doc, a1.co_tipDoc, a1.ne_numDoc";
            } 
            else{
                strSQL=" ";
                strSQL+=" SELECT DISTINCT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a4.tx_desCor as tx_desCorTipDoc\n";
                strSQL+="       , a4.tx_desLar as tx_deslarTipDoc, a1.ne_numDoc, a1.fe_doc, a1.tx_obsAut as tx_obs, a1.st_aut \n";
                strSQL+=" FROM tbm_cabSolTraInv as a1 \n";
                strSQL+=" INNER JOIN tbr_bodTipDocPrgUsr as a2 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipDoc=a2.co_tipDoc AND a1.co_bodDes=a2.co_bod)\n";
                strSQL+=" INNER JOIN tbr_bodTipDocPrgUsr as a3 ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipDoc=a3.co_tipDoc AND a1.co_bodOrg=a3.co_bod)\n";
                strSQL+=" INNER JOIN tbm_cabTipDoc as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc )\n";
                strSQL+=" WHERE a1.st_reg='A' AND a2.tx_natBod IN ('A','I') AND a3.tx_natBod IN ('A','E') ";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresaGrupo()+ " AND a1.st_aut IS NULL AND a1.st_conInv='P' AND a3.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + " AND a2.co_usr=" + objParSis.getCodigoUsuario()+ " AND a3.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.fe_doc, a1.co_tipDoc, a1.ne_numDoc";
            }
            //System.out.println("ZafCom92.cargarDetReg: " + strSQL);
            rst=stm.executeQuery(strSQL);
            vecDat.clear();
            lblMsgSis.setText("Cargando datos...");
            while(rst.next()){
                if (blnCon){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_tipDoc")==null?"":rst.getString("co_tipDoc"));
                    vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC,rst.getString("tx_desCorTipDoc")==null?"":rst.getString("tx_desCorTipDoc"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC,rst.getString("tx_deslarTipDoc")==null?"":rst.getString("tx_deslarTipDoc"));
                    vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc")==null?"":rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc")==null?"":rst.getString("ne_numDoc"));
                    vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc")==null?"":rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_DAT_CON_SOL_TRA,"Boton");
                    vecReg.add(INT_TBL_DAT_CHK_AUT,false);
                    vecReg.add(INT_TBL_DAT_CHK_DEN,false);
                    vecReg.add(INT_TBL_DAT_OBS_AUT,rst.getString("tx_obs")==null?"":rst.getString("tx_obs"));
                    vecReg.add(INT_TBL_DAT_CON_OBS,"Boton");
                    vecDat.add(vecReg);
                }
                else{
                    break;
                }
            }
         }     
        objTblMod.setData(vecDat);
        tblDat.setModel(objTblMod);
        vecDat.clear();
        if(blnCon){
                 lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
         }
        else{
             lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
        }
        rst.close();
        stm.close();
        rst=null;
        stm=null;
        butCon.setText("Consultar");
        pgrSis.setIndeterminate(false);
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
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_DAT_COD_EMP: strMsg="Código de la Empresa"; break;
                case INT_TBL_DAT_COD_LOC: strMsg="Código del Local"; break;
                case INT_TBL_DAT_COD_TIP_DOC: strMsg="Código del tipo de Documento"; break;
                case INT_TBL_DAT_DES_COR_TIP_DOC: strMsg="Descripción corta del tipo de Documento"; break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC: strMsg="Descripción larga del tipo de Documento"; break;
                case INT_TBL_DAT_COD_DOC: strMsg="Código del documento"; break;
                case INT_TBL_DAT_NUM_DOC: strMsg="Número del documento"; break;
                case INT_TBL_DAT_FEC_DOC: strMsg="Fecha del documento"; break;
                case INT_TBL_DAT_CON_SOL_TRA: strMsg="Muestra la Solicitud de Transferecia'"; break;
                case INT_TBL_DAT_CHK_AUT: strMsg="Autorizar"; break;
                case INT_TBL_DAT_CHK_DEN: strMsg="Denegar"; break;
                case INT_TBL_DAT_OBS_AUT: strMsg="Observación de la autorización"; break;
                case INT_TBL_DAT_CON_OBS: strMsg="Muestra la Observación de la Autorización'"; break;
                default: strMsg=""; break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
      
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podr�a utilizar
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
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe alg�n cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentar� un mensaje advirtiendo que si no guarda los cambios los perder�.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener {
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt){
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt){
            blnHayCam=true;
        }
    }
    
    private class ZafThreadGUI extends Thread{
        public void run(){
            try{
                con=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos()); 
                
                if (!cargarDetReg(con))
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);         
                    butCon.setText("Consultar");

                }

                con.close();
                objThrGUI=null;
            }
            catch (java.sql.SQLException e){
                System.err.println(e);
            }
            catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
    private class ZafThrGua extends Thread{
        public void run(){
            try{
                if (!guardarAutorizacion()){
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);         
                    butCon.setText("Consultar");
                }
                //Inicializar objetos si no se pudo cargar los datos.
                objThrGua=null;
            }
            catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
     /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener{
        public void tableChanged(javax.swing.event.TableModelEvent e){
            switch (e.getType()){
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }

    
    //<editor-fold defaultstate="collapsed" desc="/* Botón Solicitud de Transferencia de Inventario */">
    private class ButSolTraInv extends Librerias.ZafTableColBut.ZafTableColBut_uni
    {
        public ButSolTraInv(javax.swing.JTable tbl, int intIdx)
        {
            super(tbl, intIdx, "Solicitud de Transferencia de Inventario.");
        }

        public void butCLick()
        {
            cargarVentanaSolTraInv();
        }
    }
    
    private void cargarVentanaSolTraInv()
    {
        String strCodEmp = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP).toString();
        String strCodLoc = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_LOC).toString();
        String strCodTipDoc = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_TIP_DOC).toString();
        String strCodDoc = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_DOC).toString();

        Compras.ZafCom91.ZafCom91 obj = new Compras.ZafCom91.ZafCom91(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
    }
    //</editor-fold> 
 
 
    private class ButObs extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButObs(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Observación.");
           
        }
        @Override
        public void butCLick() {
           llamarVenObs(tblDat.getSelectedRow());
        }
    }
        
    private void llamarVenObs(int intFil)
    {
        String strObs = ( tblDat.getValueAt(intFil,  INT_TBL_DAT_OBS_AUT  )==null?"":tblDat.getValueAt(intFil,  INT_TBL_DAT_OBS_AUT  ).toString());
        Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiButObs obj = new Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiButObs(JOptionPane.getFrameForComponent(this), true, strObs);
        obj.show();
        if (obj.getAceptar()) 
        {
            tblDat.setValueAt(obj.getObser(), intFil, INT_TBL_DAT_OBS_AUT);
        }
        obj = null;
    }
    
     
    /**
     * Esta función permite actualizar los registros del detalle.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
     */
    
    boolean blnIsSolApr=false;
    
    
//    private boolean actualizarSolicitud(java.sql.Connection conn){
//        int intNumFil, i;
//        boolean blnRes=true;
//        java.sql.Statement stmLoc;
//        try
//        {
//            if (conn!=null)
//            {
//                stmLoc=conn.createStatement();
//                //Obtener la fecha del servidor.
//                if (datFecAux==null)
//                    return false;
//                intNumFil=objTblMod.getRowCountTrue();
//                for (i=0; i<intNumFil;i++){
//                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
//                        if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_AUT)){
//                            //Armar la sentencia SQL.
//                            strSQL="";
//                            strSQL+="UPDATE tbm_cabSolTraInv";
//                            strSQL+=" SET tx_obsAut=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_OBS_AUT).toString())+"";
//                            strSQL+=",fe_aut='" + objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos()) +"'";
//                            strSQL+=",co_usrAut=" + objParSis.getCodigoUsuario() +"";  
//                            strSQL+=",tx_comAut='" + objParSis.getNombreComputadoraConDirIP() +"'";
//                            strSQL+=",co_usrmod="+ objParSis.getCodigoUsuario() + "";
//                            if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_AUT)){
//                                strSQL+=",st_aut='A'";
//                                blnIsSolApr=true;
//                            }
//                            strSQL+=" WHERE co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC) + " AND co_emp=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP);
//                            strSQL+=" AND co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC) + " AND co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
//                            stmLoc.executeUpdate(strSQL);
//                        }
//                        else if (objTblMod.isChecked(i,INT_TBL_DAT_CHK_DEN)){
//                            //Armar la sentencia SQL.
//                            strSQL="";
//                            strSQL+="UPDATE tbm_cabSolTraInv";
//                            strSQL+=" SET ";
//                            if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_DEN)){
//                                strSQL+=" st_aut='D'";
//                            }
//                            strSQL+=" WHERE co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC) + " AND co_emp=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP);
//                            strSQL+=" AND co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC) + " AND co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
//                            stmLoc.executeUpdate(strSQL);
//                        }
//                    }
//                }
//                stmLoc.close();
//                stmLoc=null;
//            }
//        }
//        catch (java.sql.SQLException e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
 
    private boolean actualizarSolicitudAutorizar(java.sql.Connection conn, int i){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try
        {
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                //Obtener la fecha del servidor.
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabSolTraInv";
                strSQL+=" SET tx_obsAut=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_OBS_AUT).toString())+"";
                strSQL+=",fe_aut= CURRENT_TIMESTAMP ";
                strSQL+=",co_usrAut=" + objParSis.getCodigoUsuario() +"";  
                strSQL+=",tx_comAut='" + objParSis.getNombreComputadoraConDirIP() +"'";
                strSQL+=",co_usrmod="+ objParSis.getCodigoUsuario() + "";
                strSQL+=",st_aut='A'";
                blnIsSolApr=true;
                if(bodegaNecesitaConfirmacion(i)){
                    strSQL+=" , st_conInv = 'P'";
                }else{
                    strSQL+=" , st_conInv = 'N'";
                }
                strSQL+=" WHERE co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC) + " AND co_emp=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP);
                strSQL+=" AND co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC) + " AND co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                System.out.println("UPDATE " + strSQL);
                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;
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
    
    
    private boolean bodegaNecesitaConfirmacion(int i){
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc,rstLoc01;
        String strCadena="";
        boolean blnRes=false,blnConIng=false,blnConEgr=false;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strCadena = "";
                strCadena+=" SELECT * \n";
                strCadena+=" FROM tbm_cabSolTraInv as a1 \n";
                strCadena+=" WHERE co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC) + " AND co_emp=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)+" \n";
                strCadena+=" AND co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC) + " AND co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                System.out.println("bodegaNecesitaConfirmacion 1 :" + strCadena);
                rstLoc = stmLoc.executeQuery(strCadena);
                if(rstLoc.next()){
                    strCadena="";
                    strCadena+=" SELECT a1.co_emp, a1.co_cfg, a1.co_bodOrg, a1.co_bodDes, a1.st_conEgr, a1.st_conIng";
                    strCadena+=" , a1.tx_egrGen, a1.tx_ingGen, a1.tx_obs1, a1.st_reg";
                    strCadena+=" FROM tbm_cfgbod AS a1";
                    strCadena+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                    strCadena+=" AND a1.co_bodOrg=" + rstLoc.getInt("co_bodOrg") + "";
                    strCadena+=" AND a1.co_bodDes=" + rstLoc.getInt("co_bodDes") + "";
                    strCadena+=" AND a1.st_reg='A'";
                    System.out.println("bodegaNecesitaConfirmacion 2 :" + strCadena);
                    rstLoc01=stmLoc.executeQuery(strCadena);
                    if(rstLoc01.next()){
                        blnConIng=new Boolean((rstLoc01.getObject("st_conIng")==null?false:true));
                        blnConEgr=new Boolean((rstLoc01.getObject("st_conEgr")==null?false:true));
                    }
                    rstLoc01.close();
                    rstLoc01 = null;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                if(blnConIng || blnConEgr){
                    blnRes=true;
                }
            }
            conLoc.close();
            conLoc=null;
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
    
    
     private boolean actualizarSolicitudDenegar(java.sql.Connection conn, int i){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try
        {
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                //Obtener la fecha del servidor.
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabSolTraInv";
                strSQL+=" SET tx_obsAut=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_OBS_AUT).toString())+"";
                strSQL+=",fe_aut=CURRENT_TIMESTAMP ";
                strSQL+=",co_usrAut=" + objParSis.getCodigoUsuario() +"";  
                strSQL+=",tx_comAut='" + objParSis.getNombreComputadoraConDirIP() +"'";
                strSQL+=",co_usrmod="+ objParSis.getCodigoUsuario() + "";
                strSQL+=",st_aut='D'";
                strSQL+=",st_conInv='N'";
                blnIsSolApr=true;
                strSQL+=" WHERE co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC) + " AND co_emp=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP);
                strSQL+=" AND co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC) + " AND co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;
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
    
    private boolean guardarAutorizacion(){
        java.sql.Connection conLoc;
        boolean blnRes=false;
        try{
            lblMsgSis.setText("Guardando datos...");
            pgrSis.setIndeterminate(true);
            int intNumFil=objTblMod.getRowCountTrue();
            for (int i=0; i<intNumFil;i++){
                if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
                    conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    if(conLoc!=null){
                        conLoc.setAutoCommit(false);
                        if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_AUT)){
                            if(validarStockDisponible(conLoc,i)){
                                if(actualizarSolicitudAutorizar(conLoc,i)){
                                    if(insertaSeguimiento(conLoc, i)){
                                        if(llamadoGeneracionPrestamos(conLoc,i)){
                                            if(procesoImportaciones(conLoc, i)){
                                                blnRes=true;
                                                llenarArraySolTra(conLoc, i);
                                            }else{
                                                blnRes=false; 
                                                System.err.println("Error procesoImportaciones");
                                            }
                                       }else{
                                            blnRes=false;
                                            System.err.println("Error llamadoGeneracionPrestamos");
                                        }
                                    }else{
                                        blnRes=false;
                                        System.err.println("Error insertaSeguimiento");
                                    }
                                }else{
                                    blnRes=false;
                                    System.err.println("Error actualizarSolicitudAutorizar");
                                }
                            }else{
                                blnRes=false;
                                System.err.println("Error validarStockDisponible");
                            }
                        }
                        else if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_DEN)){
                            if(actualizarSolicitudDenegar(conLoc, i)){
                                blnRes=true;
                            }else{blnRes=false;}
                        }

                        if(blnRes==true){
                            conLoc.commit();
                            //Orden de Egreso
                            if(!objReaMovInv.getZafMovInvEgrInv().getDocEgrGen().equals(""))
                                objReaMovInv.getZafMovInvEgrInv().isImprimirRptEgr(conLoc);
                            //Orden de Ingreso
                            if(!objReaMovInv.getZafMovInvEgrInv().getDocIngGen().equals(""))
                                objReaMovInv.getZafMovInvEgrInv().isImprimirRptIng(conLoc);
                            //Orden de Despacho
                            objGenOD.imprimirOdxEgr(conLoc,arlDatSolTra,objCfgSer.getIpHost());
                        }
                        else{
                            conLoc.rollback();
                        }
                        conLoc.close();
                        conLoc=null;
                        blnRes=false;
                    }
                }
            }
            pgrSis.setIndeterminate(false);
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            cargarDetReg(conLoc);
            conLoc.close();
            conLoc=null;
            blnRes=true;
            
            // <editor-fold defaultstate="collapsed" desc=" /* José Marín: Se cambio la forma del programa, 24/Agosto/2016 */ ">            
//             if (actualizarSolicitud(con)) {   
//                 if(blnIsSolApr){
//                     if(validarStockDisponible()){
//                         if(insertaSeguimiento(con)){
//                             if(llamadoGeneracionPrestamos(con)){
//                                 if(insertaRelacionImportaciones(con)){
//                                     con.commit();
//                                     blnRes=true;
//                                 }
//                                 else{
//                                     con.rollback();
//                                 }
//                                 if(llenarArraySolTra(con)){
//                                     objGenOD.imprimirOdxEgr(con,arlDatSolTra,objCfgSer.getIpHost());
//                                 }
//                                 cargarDetReg(con);
//                                 blnIsSolApr=false;
//                             }
//                             else{
//                                 mostrarMsgInf("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema. CV");
//                                 con.rollback();
//                             }
//                         }
//                         else{
//                             con.rollback();        
//                         }
//                     }
//                 }
//                 else{
//                     con.commit();
//                     blnRes=true;
//                     cargarDetReg(con);
//                 }
//
//             }
//             else{
//                 mostrarMsgInf("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
//                 conLoc.rollback();
//             }
//</editor-fold>
               
       }
       catch (java.sql.SQLException e) { 
           objUti.mostrarMsgErr_F1(this, e);  
       }
       catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
       }  
       return blnRes;
    }
    /**
     * 
     *   EGRESO --  INGRESO 
     * @param conn
     * @param intCodBodOrg
     * @param intCodBodDes
     * @return 
     */
    private boolean llamaObjetoCfgBod(java.sql.Connection conn, int intCodBodOrg, int intCodBodDes){
        boolean blnRes=true;
        try{
            if(objCfgBod!=null){
                objCfgBod=null;
            }
            objCfgBod = new ZafCfgBod(objParSis,conn,objParSis.getCodigoEmpresaGrupo(),intCodBodOrg,intCodBodDes,componente);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean llamaObjetoCfgBod(java.sql.Connection conn, int intCodEmp,int intCodLoc, int intCodTipDoc, int intCodDoc){
        int intCodBodOrg=0, intCodBodDes=0;
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if (conn!=null){
                stmLoc = conn.createStatement();
                
                String strSQL="";
                strSQL+=" SELECT * FROM tbm_cabSolTraInv WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipDoc="+intCodTipDoc+" and co_doc="+intCodDoc;
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodBodOrg=rstLoc.getInt("co_bodOrg");
                    intCodBodDes=rstLoc.getInt("co_bodDes");
                }
                if(objCfgBod!=null){
                    objCfgBod=null;
                }
                objCfgBod = new ZafCfgBod(objParSis,conn,objParSis.getCodigoEmpresaGrupo(),intCodBodOrg,intCodBodDes,componente);
                
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
//    private boolean llamadoGeneracionPrestamos(java.sql.Connection conn){
//        int intNumFil, i,intCodEmp, intCodLoc, intCodTipDoc, intCodDoc;
//        boolean blnRes=true;
//        try{
//            if (conn!=null){
//                intNumFil=objTblMod.getRowCountTrue();
//                for (i=0; i<intNumFil;i++){
//                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
//                        if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_AUT)){
//                            intCodEmp=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP).toString());
//                            intCodLoc=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC).toString());
//                            intCodTipDoc=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC).toString());
//                            intCodDoc=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC).toString());
//                            if(!generaPrestamos(conn,intCodEmp,intCodLoc,intCodTipDoc,intCodDoc)){
//                                blnRes=false;
//                                System.out.println("Fallo GeneraPrestamos...");
//                            }
//                        }    
//                    }
//                }
//            }
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
    
    private boolean llamadoGeneracionPrestamos(java.sql.Connection conn,int i){
        int intCodEmp, intCodLoc, intCodTipDoc, intCodDoc;
        boolean blnRes=true;
        try{
            if (conn!=null){
                intCodEmp=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP).toString());
                intCodLoc=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC).toString());
                intCodTipDoc=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC).toString());
                intCodDoc=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC).toString());
                if(!generaPrestamos(conn,intCodEmp,intCodLoc,intCodTipDoc,intCodDoc)){
                    blnRes=false;
                    System.out.println("Fallo GeneraPrestamos...");
                }
            }
            else{
                blnRes=false;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
//  private boolean insertaRelacionImportaciones(java.sql.Connection conn){
//        boolean blnRes=true;
//        java.sql.Statement stmLoc;
//        java.sql.ResultSet rstLoc;
//        int intSolTra;
//        try{
//            if(conn!=null){
//                stmLoc = conn.createStatement();
//                arlDatTipDocCfgAut = new ArrayList();
//                strSQL=" select co_tipDoc from tbm_cfgTipDocUtiProAut where co_emp="+objParSis.getCodigoEmpresaGrupo()+" and co_cfg IN ("+INT_ARL_COD_CFG_SOL_SOTRINI+","+INT_ARL_COD_CFG_SOL_SOTRINC+")";
//                rstLoc=stmLoc.executeQuery(strSQL);
//                while(rstLoc.next()){
//                    arlDatTipDocCfgAut.add(rstLoc.getInt("co_tipDoc"));
//                }
//                rstLoc.close();
//                rstLoc=null;
//                int intNumFil=objTblMod.getRowCountTrue();
//                for (int i=0; i<intNumFil;i++){
//                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
//                        if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_AUT)){
//                            for(int R=0;R<arlDatTipDocCfgAut.size();R++){
//                                intSolTra=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC).toString());
//                                if(intSolTra==Integer.parseInt(arlDatTipDocCfgAut.get(R).toString())){
//                                    // ENTONCES SI ES IMPORTACIONES O COMPRAS LOCALES 
//                                    // BUSCAR EN SEGUIMIENTO 
//                                    strSQL="";
//                                    strSQL+=" SELECT a1.co_seg as CodSeg, TraInv.CodEmpTraInv, TraInv.CodLocTraInv, TraInv.CodTipDocTraInv, TraInv.CodDocTraInv,\n";  
//                                    strSQL+="        IngImp.CodEmpIngImp, IngImp.CodLocIngImp, IngImp.CodTipDocIngImp, IngImp.CodDocIngImp \n";
//                                    strSQL+=" FROM tbm_cabSolTraInv as a \n";    
//                                    strSQL+=" INNER JOIN tbm_cabSegMovInv as a1 ON (a1.co_empRelCabSolTraInv=a.co_emp AND a1.co_locRelCabSolTraInv=a.co_loc AND \n";
//                                    strSQL+=" a1.co_tipDocRelCabSolTraInv=a.co_tipDoc AND a1.co_docRelCabSolTraInv=a.co_doc )  \n";
//                                    strSQL+=" INNER JOIN (  \n";
//                                    strSQL+="      SELECT a.co_Emp as CodEmpTraInv, a.co_loc as CodLocTraInv, a.co_tipDoc as CodTipDocTraInv, a.co_Doc as CodDocTraInv , \n";
//                                    strSQL+="             a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc \n";
//                                    strSQL+="      FROM tbm_cabMovInv as a  \n";
//                                    strSQL+="      INNER JOIN tbm_cabSolTraInv as a1  ON (a1.co_emp=a.co_empRelCabSolTraInv AND a1.co_loc=a.co_locRelCabSolTraInv AND  \n";
//                                    strSQL+="                                             a1.co_tipDoc=a.co_tipDocRelCabSolTraInv AND a1.co_doc=a.co_docRelCabSolTraInv)  \n";
//                                    strSQL+=" ) as TraInv ON (TraInv.co_emp=a.co_emp AND TraInv.co_loc=a.co_loc AND TraInv.co_tipDoc=a.co_tipDoc AND TraInv.co_doc=a.co_doc )  \n";
//                                    strSQL+=" INNER JOIN(  \n";
//                                    strSQL+="      SELECT a.co_Emp as CodEmpIngImp, a.co_loc as CodLocIngImp, a.co_tipDoc as CodTipDocIngImp, a.co_Doc as CodDocIngImp, \n";
//                                    strSQL+="             a3.co_empRelCabSolTraInv, a3.co_locRelCabSolTraInv, a3.co_tipDocRelCabSolTraInv, a3.co_docRelCabSolTraInv \n";
//                                    strSQL+="      FROM tbm_cabMovInv as a  \n";
//                                    strSQL+="      INNER JOIN tbr_cabSolTraInvCabMovInv as a3 ON (a3.co_empRelCabMovInv=a.co_emp AND a3.co_locRelCabMovInv=a.co_loc AND a3.co_tipDocRelCabMovInv=a.co_tipDoc AND a3.co_docRelCabMovInv=a.co_Doc ) \n";
//                                    strSQL+=" ) as IngImp ON (IngImp.co_empRelCabSolTraInv=a.co_emp AND IngImp.co_locRelCabSolTraInv=a.co_loc AND IngImp.co_tipDocRelCabSolTraInv=a.co_tipDoc AND IngImp.co_docRelCabSolTraInv=a.co_doc) \n";
//                                    strSQL+=" WHERE a.co_emp="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)+" \n";
//                                    strSQL+=" AND a.co_loc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC)+" \n";
//                                    strSQL+=" AND a.co_tipDoc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC)+" \n";
//                                    strSQL+=" AND a.co_Doc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC)+" \n";
//                                    strSQL+=" GROUP BY a1.co_seg, TraInv.CodEmpTraInv, TraInv.CodLocTraInv, TraInv.CodTipDocTraInv, TraInv.CodDocTraInv,  \n";
//                                    strSQL+="          IngImp.CodEmpIngImp, IngImp.CodLocIngImp, IngImp.CodTipDocIngImp, IngImp.CodDocIngImp \n";
//
//                                    //<editor-fold defaultstate="collapsed" desc="/* Query - Anterior de tabla relacional. */">
////                                    strSQL+=" SELECT a1.co_seg as CodSeg, TraInv.CodEmpTraInv, TraInv.CodLocTraInv, TraInv.CodTipDocTraInv, TraInv.CodDocTraInv,  \n";
////                                    strSQL+="        IngImp.CodEmpIngImp, IngImp.CodLocIngImp, IngImp.CodTipDocIngImp, IngImp.CodDocIngImp  \n";
////                                    strSQL+=" FROM tbm_cabSolTraInv as a    \n";
////                                    strSQL+=" INNER JOIN tbm_cabSegMovInv as a1 ON (a1.co_empRelCabSolTraInv=a.co_emp AND a1.co_locRelCabSolTraInv=a.co_loc AND \n";
////                                    strSQL+="                                       a1.co_tipDocRelCabSolTraInv=a.co_tipDoc AND a1.co_docRelCabSolTraInv=a.co_doc )  \n";
////                                    strSQL+=" INNER JOIN (  \n";
////                                    strSQL+="    SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,   \n";
////                                    strSQL+="           a.co_Emp as CodEmpTraInv, a.co_loc as CodLocTraInv, a.co_tipDoc as CodTipDocTraInv, a.co_Doc as CodDocTraInv  \n";	 
////                                    strSQL+="    FROM tbm_cabMovInv as a  \n";
////                                    strSQL+="    INNER JOIN tbm_cabSolTraInv as a1  ON (a1.co_emp=a.co_empRelCabSolTraInv AND a1.co_loc=a.co_locRelCabSolTraInv AND  \n";
////                                    strSQL+="                                           a1.co_tipDoc=a.co_tipDocRelCabSolTraInv AND a1.co_doc=a.co_docRelCabSolTraInv)  \n";
////                                    strSQL+=" ) as TraInv ON (TraInv.co_emp=a.co_emp AND TraInv.co_loc=a.co_loc AND TraInv.co_tipDoc=a.co_tipDoc AND TraInv.co_doc=a.co_doc  )  \n";
////                                    strSQL+=" INNER JOIN(   \n";
////                                    strSQL+="    SELECT a1.co_seg, a.co_Emp as CodEmpIngImp, a.co_loc as CodLocIngImp, a.co_tipDoc as CodTipDocIngImp, a.co_Doc as CodDocIngImp   \n";
////                                    strSQL+="    FROM tbm_cabMovInv as a  \n";
////                                    strSQL+="    INNER JOIN tbm_cabSegMovInv as a1 ON (a1.co_empRelCabMovInv=a.co_emp AND a1.co_locRelCabMovInv=a.co_loc AND   \n";
////                                    strSQL+="                                          a1.co_tipDocRelCabMovInv=a.co_tipDoc AND a1.co_docRelCabMovInv=a.co_doc ) \n";
////                                    strSQL+="    INNER JOIN tbm_cabPedEmbImp as a2 ON (a2.co_emp=a.co_empRelPedEmbImp AND a2.co_loc=a.co_locRelPedEmbImp AND a2.co_tipDoc=a.co_tipDocRelPedEmbImp AND a2.co_doc=a.co_docRelPedEmbImp)  \n";
////                                    strSQL+=" ) as IngImp ON (IngImp.co_seg=a1.co_seg)"+"\n";
////                                    strSQL+=" WHERE a.co_emp="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)+" \n";
////                                    strSQL+=" AND a.co_loc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC)+" \n";
////                                    strSQL+=" AND a.co_tipDoc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC)+" \n";
////                                    strSQL+=" AND a.co_Doc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC)+" \n";
////                                    strSQL+=" GROUP BY a1.co_seg, TraInv.CodEmpTraInv, TraInv.CodLocTraInv, TraInv.CodTipDocTraInv, TraInv.CodDocTraInv,  \n";
////                                    strSQL+="          IngImp.CodEmpIngImp, IngImp.CodLocIngImp, IngImp.CodTipDocIngImp, IngImp.CodDocIngImp  \n";
//                                    //</editor-fold>
//                                    
//                                    System.out.println("insertaRelacionImportaciones: "+strSQL);
//                                    rstLoc=stmLoc.executeQuery(strSQL);
//                                    if(rstLoc.next())
//                                    {
//                                        //Relacion de la transferencia con el ingreso por importacion
//                                        strSQL="INSERT INTO tbr_cabmovinv(";
//                                        strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, st_reg, co_locrel, co_tipdocrel, ";
//                                        strSQL+="   co_docrel, st_regrep, co_emprel)";
//                                        strSQL+="VALUES (";
//                                        strSQL+="" + rstLoc.getInt("CodEmpTraInv") + ",";//co_emp
//                                        strSQL+="" + rstLoc.getInt("CodLocTraInv") + ",";//co_loc
//                                        strSQL+="" + rstLoc.getInt("CodTipDocTraInv")+ ",";//co_tipdoc
//                                        strSQL+="" + rstLoc.getInt("CodDocTraInv") + ",";//co_doc
//                                        strSQL+="'A',";//st_reg
//                                        strSQL+="" + rstLoc.getInt("CodLocIngImp") + ",";//co_locrel
//                                        strSQL+="" + rstLoc.getInt("CodTipDocIngImp") + ",";//co_tipdocrel
//                                        strSQL+="" + rstLoc.getInt("CodDocIngImp") + ",";//co_docrel
//                                        strSQL+="'I',";//st_regrep
//                                        strSQL+="" + rstLoc.getInt("CodEmpIngImp") + "";//co_emprel
//                                        strSQL+=");";
//                                        //relacion de la transferencia con la transferencia
//                                        strSQL+="INSERT INTO tbr_cabmovinv(";
//                                        strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, st_reg, co_locrel, co_tipdocrel, ";
//                                        strSQL+="   co_docrel, st_regrep, co_emprel)";
//                                        strSQL+="VALUES (";
//                                        strSQL+="" + rstLoc.getInt("CodEmpTraInv") + ",";//co_emp
//                                        strSQL+="" + rstLoc.getInt("CodLocTraInv") + ",";//co_loc
//                                        strSQL+="" + rstLoc.getInt("CodTipDocTraInv") + ",";//co_tipdoc
//                                        strSQL+="" + rstLoc.getInt("CodDocTraInv") + ",";//co_doc
//                                        strSQL+="'A',";//st_reg
//                                        strSQL+="" + rstLoc.getInt("CodLocTraInv") + ",";//co_locrel
//                                        strSQL+="" + rstLoc.getInt("CodTipDocTraInv")+ ",";//co_tipdocrel
//                                        strSQL+="" + rstLoc.getInt("CodDocTraInv") + ",";//co_docrel
//                                        strSQL+="'I',";//st_regrep
//                                        strSQL+="" + rstLoc.getInt("CodEmpTraInv") + "";//co_emprel
//                                        strSQL+=");";
//                                        stmLoc.executeUpdate(strSQL);
//                                    }
//                                    rstLoc.close();
//                                    rstLoc=null;
//                                }
//                            }
//                        }
//                    }
//                }
//                stmLoc.close();
//                stmLoc=null;
//            }
//        }
//        catch (java.sql.SQLException e) { 
//           objUti.mostrarMsgErr_F1(this, e);  
//           blnRes=false;
//       }
//       catch(Exception  Evt){ 
//           objUti.mostrarMsgErr_F1(this, Evt);
//           blnRes=false;
//       }  
//       return blnRes;
//        
//    }
    
    private boolean procesoImportaciones(java.sql.Connection conn, int i)
    {
        boolean blnRes=false;
        try{
            if(conn!=null)
            {
                if(validaIsProcesoImportaciones(conn, i))
                {
                    if(insertaRelacionImportaciones(conn, i))
                    {
                        if(cierreAjusteInventarioImportaciones(conn, i))
                        {
                            blnRes=true;
                        }
                    }
                }
                else
                    blnRes=true;
            }
        }
       catch(Exception  Evt)
       { 
           objUti.mostrarMsgErr_F1(this, Evt);
           blnRes=false;
       }  
       return blnRes;
        
    }
        
    /**
     * Valida si es proceso de importaciones.
     * @param conn
     * @param i
     * @return true: Si es proceso de importaciones.
     * false: caso contrario.
     */
    private boolean validaIsProcesoImportaciones(java.sql.Connection conn, int i)
    {
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try
        {
            if(conn!=null)
            {
                stmLoc = conn.createStatement();
                strSQL =" SELECT co_tipDoc FROM tbm_cfgTipDocUtiProAut ";
                strSQL+=" WHERE co_emp="+objParSis.getCodigoEmpresaGrupo();
                strSQL+=" AND co_cfg IN("+INT_ARL_COD_CFG_SOL_SOTRINI+","+INT_ARL_COD_CFG_SOL_SOTRINC+")";
                strSQL+=" AND co_tipDoc IN ("+objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC).toString()+")";
                rstLoc=stmLoc.executeQuery(strSQL);
                while(rstLoc.next())
                {
                    // ENTONCES SI ES IMPORTACIONES O COMPRAS LOCALES 
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e) 
        { 
           objUti.mostrarMsgErr_F1(this, e);  
           blnRes=false;
       }
       catch(Exception  Evt)
       { 
           objUti.mostrarMsgErr_F1(this, Evt);
           blnRes=false;
       }  
       return blnRes;        
    }
    
    private boolean insertaRelacionImportaciones(java.sql.Connection conn, int i)
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try
        {
            if(conn!=null)
            {
                stmLoc = conn.createStatement();
                //BUSCAR EN SEGUIMIENTO 
                strSQL="";
                strSQL+=" SELECT a1.co_seg as CodSeg, TraInv.CodEmpTraInv, TraInv.CodLocTraInv, TraInv.CodTipDocTraInv, TraInv.CodDocTraInv,\n";  
                strSQL+="        IngImp.CodEmpIngImp, IngImp.CodLocIngImp, IngImp.CodTipDocIngImp, IngImp.CodDocIngImp \n";
                strSQL+=" FROM tbm_cabSolTraInv as a \n";    
                strSQL+=" INNER JOIN tbm_cabSegMovInv as a1 ON (a1.co_empRelCabSolTraInv=a.co_emp AND a1.co_locRelCabSolTraInv=a.co_loc AND \n";
                strSQL+=" a1.co_tipDocRelCabSolTraInv=a.co_tipDoc AND a1.co_docRelCabSolTraInv=a.co_doc )  \n";
                strSQL+=" INNER JOIN (  \n";
                strSQL+="      SELECT a.co_Emp as CodEmpTraInv, a.co_loc as CodLocTraInv, a.co_tipDoc as CodTipDocTraInv, a.co_Doc as CodDocTraInv , \n";
                strSQL+="             a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc \n";
                strSQL+="      FROM tbm_cabMovInv as a  \n";
                strSQL+="      INNER JOIN tbm_cabSolTraInv as a1  ON (a1.co_emp=a.co_empRelCabSolTraInv AND a1.co_loc=a.co_locRelCabSolTraInv AND  \n";
                strSQL+="                                             a1.co_tipDoc=a.co_tipDocRelCabSolTraInv AND a1.co_doc=a.co_docRelCabSolTraInv)  \n";
                strSQL+=" ) as TraInv ON (TraInv.co_emp=a.co_emp AND TraInv.co_loc=a.co_loc AND TraInv.co_tipDoc=a.co_tipDoc AND TraInv.co_doc=a.co_doc )  \n";
                strSQL+=" INNER JOIN(  \n";
                strSQL+="      SELECT a.co_Emp as CodEmpIngImp, a.co_loc as CodLocIngImp, a.co_tipDoc as CodTipDocIngImp, a.co_Doc as CodDocIngImp, \n";
                strSQL+="             a3.co_empRelCabSolTraInv, a3.co_locRelCabSolTraInv, a3.co_tipDocRelCabSolTraInv, a3.co_docRelCabSolTraInv \n";
                strSQL+="      FROM tbm_cabMovInv as a  \n";
                strSQL+="      INNER JOIN tbr_cabSolTraInvCabMovInv as a3 ON (a3.co_empRelCabMovInv=a.co_emp AND a3.co_locRelCabMovInv=a.co_loc AND a3.co_tipDocRelCabMovInv=a.co_tipDoc AND a3.co_docRelCabMovInv=a.co_Doc ) \n";
                strSQL+=" ) as IngImp ON (IngImp.co_empRelCabSolTraInv=a.co_emp AND IngImp.co_locRelCabSolTraInv=a.co_loc AND IngImp.co_tipDocRelCabSolTraInv=a.co_tipDoc AND IngImp.co_docRelCabSolTraInv=a.co_doc) \n";
                strSQL+=" WHERE a.co_emp="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)+" \n";
                strSQL+=" AND a.co_loc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC)+" \n";
                strSQL+=" AND a.co_tipDoc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC)+" \n";
                strSQL+=" AND a.co_Doc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC)+" \n";
                strSQL+=" GROUP BY a1.co_seg, TraInv.CodEmpTraInv, TraInv.CodLocTraInv, TraInv.CodTipDocTraInv, TraInv.CodDocTraInv,  \n";
                strSQL+="          IngImp.CodEmpIngImp, IngImp.CodLocIngImp, IngImp.CodTipDocIngImp, IngImp.CodDocIngImp \n";

                System.out.println("insertaRelacionImportaciones: "+strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next())
                {
                    //Relacion de la transferencia con el ingreso por importacion
                    strSQL="INSERT INTO tbr_cabmovinv(";
                    strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, st_reg, co_locrel, co_tipdocrel, ";
                    strSQL+="   co_docrel, st_regrep, co_emprel)";
                    strSQL+="VALUES (";
                    strSQL+="" + rstLoc.getInt("CodEmpTraInv") + ",";//co_emp
                    strSQL+="" + rstLoc.getInt("CodLocTraInv") + ",";//co_loc
                    strSQL+="" + rstLoc.getInt("CodTipDocTraInv")+ ",";//co_tipdoc
                    strSQL+="" + rstLoc.getInt("CodDocTraInv") + ",";//co_doc
                    strSQL+="'A',";//st_reg
                    strSQL+="" + rstLoc.getInt("CodLocIngImp") + ",";//co_locrel
                    strSQL+="" + rstLoc.getInt("CodTipDocIngImp") + ",";//co_tipdocrel
                    strSQL+="" + rstLoc.getInt("CodDocIngImp") + ",";//co_docrel
                    strSQL+="'I',";//st_regrep
                    strSQL+="" + rstLoc.getInt("CodEmpIngImp") + "";//co_emprel
                    strSQL+=");";
                    //relacion de la transferencia con la transferencia
                    strSQL+="INSERT INTO tbr_cabmovinv(";
                    strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, st_reg, co_locrel, co_tipdocrel, ";
                    strSQL+="   co_docrel, st_regrep, co_emprel)";
                    strSQL+="VALUES (";
                    strSQL+="" + rstLoc.getInt("CodEmpTraInv") + ",";//co_emp
                    strSQL+="" + rstLoc.getInt("CodLocTraInv") + ",";//co_loc
                    strSQL+="" + rstLoc.getInt("CodTipDocTraInv") + ",";//co_tipdoc
                    strSQL+="" + rstLoc.getInt("CodDocTraInv") + ",";//co_doc
                    strSQL+="'A',";//st_reg
                    strSQL+="" + rstLoc.getInt("CodLocTraInv") + ",";//co_locrel
                    strSQL+="" + rstLoc.getInt("CodTipDocTraInv")+ ",";//co_tipdocrel
                    strSQL+="" + rstLoc.getInt("CodDocTraInv") + ",";//co_docrel
                    strSQL+="'I',";//st_regrep
                    strSQL+="" + rstLoc.getInt("CodEmpTraInv") + "";//co_emprel
                    strSQL+=");";
                    stmLoc.executeUpdate(strSQL);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
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
     * Cierre del documento de ajuste de inventario (Importaciones)
     * @param conn
     * @param i
     * @return true: Si es proceso de importaciones.
     * false: caso contrario.
     */
    private boolean cierreAjusteInventarioImportaciones(java.sql.Connection conn, int i)
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try
        {
            if(conn!=null)
            {
                stmLoc = conn.createStatement();
                
                strSQL =" UPDATE tbm_cabMovInv SET st_ingImp='C' FROM ( \n";
                strSQL+="   SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, SUM (a.nd_CanAju) AS nd_CanAju, SUM(a.nd_canTrs) AS nd_canTrs \n";
                strSQL+="   FROM (\n";         
                strSQL+="       SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.st_reg, a1.st_ingImp, a1.st_aut, a1.fe_Doc, a1.tx_numDoc2 \n";	     
                strSQL+="            , a1.co_itmMae, a1.tx_codAlt, a1.tx_codAlt2, a1.nd_CanAju\n";
                strSQL+="            , (CASE WHEN a2.nd_canTrs IS NULL THEN 0 ELSE a2.nd_canTrs END) as nd_canTrs \n";        
                strSQL+="       FROM ( \n";             
                strSQL+="  	      SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.st_reg, a.st_ingImp, a.st_aut, a.fe_Doc, a.tx_numDoc2 \n";	       
                strSQL+="  	           , a3.co_itmMae, a1.co_Reg, a1.co_itm as co_itmEmp, a1.tx_codAlt, a1.tx_codAlt2, a1.nd_Can AS nd_CanAju \n";	     
                strSQL+="  	      FROM tbm_CabMovInv as a\n"; 	    
                strSQL+="  	      INNER JOIN tbm_detMovInv AS a1 oN (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc AND a1.co_doc=a.co_doc) \n";	    
                strSQL+="  	      INNER JOIN tbm_inv AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_itm=a1.co_itm)\n"; 	    
                strSQL+="  	      INNER JOIN tbm_equInv AS a3 ON (a3.co_emp=a2.co_emp AND a3.co_itm=a2.co_itm) \n";	    
                strSQL+=" 	      WHERE a.co_tipDoc IN (select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresaGrupo();
                strSQL+="                                   and co_loc=" + objParSis.getCodigoLocal()+" and co_mnu= "+objImp.INT_COD_MNU_PRG_AJU_INV+") \n";	 
                strSQL+="             AND a.st_reg IN ('A') AND a.st_ingImp='T' AND a.st_aut='A' AND a1.nd_can>0 AND a1.st_merIngEgrFisBod IN ('S')\n";
                strSQL+="             AND (CASE WHEN a1.st_Reg IS NULL THEN 'A' ELSE a1.st_Reg END ) NOT IN ('I') \n";  //Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
                strSQL+="  	      AND EXISTS (\n";    
                strSQL+="  	         select * from tbr_cabSolTraInvCabMovInv as a4\n"; 
                strSQL+="  	         where a4.co_empRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP);
                strSQL+="  	         and a4.co_locRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC);
                strSQL+="  	         and a4.co_tipDocRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                strSQL+="  	         and a4.co_docRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);
                strSQL+="  	         and a4.co_empRelCabMovInv=a.co_emp and a4.co_locRelCabMovInv=a.co_loc and a4.co_tipDocRelCabMovInv=a.co_tipDoc and a4.co_docRelCabMovInv=a.co_doc \n";
                strSQL+="  	      ) \n";
                strSQL+="       ) AS a1\n";     
                strSQL+="       LEFT OUTER JOIN  ( \n";    
                strSQL+="  	      SELECT a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a4.co_itmMae, ABS(SUM(a2.nd_Can)) as nd_CanTrs\n";  	   
                strSQL+="             FROM tbm_CabMovInv as a \n";	  
                strSQL+="             INNER JOIN tbr_cabMovinv as a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc AND a1.co_doc=a.co_doc)\n"; 	   
                strSQL+="             INNER JOIN tbm_detMovInv AS a2 oN (a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipDoc=a.co_tipDoc AND a2.co_doc=a.co_doc)\n";	   
                strSQL+="             INNER JOIN tbm_inv AS a3 ON (a3.co_emp=a2.co_emp AND a3.co_itm=a2.co_itm)\n";	    
                strSQL+="             INNER JOIN tbm_equInv AS a4 ON (a4.co_emp=a3.co_emp AND a4.co_itm=a3.co_itm) \n";	    
                strSQL+="             WHERE a2.nd_Can<0 AND a.st_reg='A'\n"; 	 
                strSQL+="             AND a.co_tipDoc NOT IN (select co_tipDoc from tbr_tipDocPrg where co_emp="+objParSis.getCodigoEmpresaGrupo();
                strSQL+=" 	                            and co_loc="+objParSis.getCodigoLocal()+" and co_mnu="+objImp.INT_COD_MNU_PRG_AJU_INV+") \n"; 
                strSQL+="             AND EXISTS ( \n";
                strSQL+="  	         select * from tbr_cabSolTraInvCabMovInv as a5 \n";
                strSQL+="  	         where a5.co_empRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP);
                strSQL+="  	         and a5.co_locRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC);
                strSQL+="  	         and a5.co_tipDocRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                strSQL+="  	         and a5.co_docRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);
                strSQL+="  	         and a5.co_empRelCabMovInv=a1.co_empRel and a5.co_locRelCabMovInv=a1.co_locRel and a5.co_tipDocRelCabMovInv=a1.co_tipDocRel and a5.co_docRelCabMovInv=a1.co_docRel \n";
                strSQL+="             )\n";
                strSQL+="             GROUP BY a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a4.co_itmMae\n";
                strSQL+="       ) AS a2 ON a2.co_empRel=a1.co_emp AND a2.co_locRel=a1.co_loc AND a2.co_tipDocRel=a1.co_tipDoc AND a2.co_docRel=a1.co_doc AND a2.co_itmMae=a1.co_itmMae\n";
                strSQL+="   ) as a \n";
                strSQL+="   GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc\n";
                strSQL+=" ) as x \n";
                strSQL+=" WHERE x.co_emp=tbm_cabMovInv.co_emp AND x.co_loc=tbm_cabMovInv.co_loc AND x.co_tipDoc=tbm_cabMovInv.co_tipDoc AND x.co_doc=tbm_cabMovInv.co_Doc AND x.nd_canAju=x.nd_canTrs\n";
                System.out.println("Update.CierreAjuste: "+strSQL);
                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e) 
        { 
           objUti.mostrarMsgErr_F1(this, e);  
           blnRes=false;
       }
       catch(Exception  Evt)
       { 
           objUti.mostrarMsgErr_F1(this, Evt);
           blnRes=false;
       }  
       return blnRes;        
    }
    
//    private boolean llenarArraySolTra(java.sql.Connection conn){
//        boolean blnRes=true;
//        java.sql.Statement stmLoc;
//        java.sql.ResultSet rstLoc;
//        try{
//            if(conn!=null){
//                stmLoc= conn.createStatement();
//                int intNumFil=objTblMod.getRowCountTrue();
//                arlDatSolTra= new ArrayList();
//                for (int i=0; i<intNumFil;i++){
//                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
//                        if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_AUT)){
//                            strSQL="";
//                            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a1.co_bodOrg \n";
//                            strSQL+=" FROM tbm_cabSolTraInv as a1 \n";
//                            strSQL+=" WHERE a1.co_emp="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP).toString()+" AND a1.co_loc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC).toString()+" AND a1.co_tipDoc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC).toString()+" AND a1.co_doc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC).toString()+"\n";
//                            rstLoc=stmLoc.executeQuery(strSQL);
//                            if(rstLoc.next()){
//                                arlRegSolTra = new ArrayList();
//                                arlRegSolTra.add(INT_ARL_COT_VEN_COD_EMP,Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP).toString()));
//                                arlRegSolTra.add(INT_ARL_COT_VEN_COD_LOC,Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC).toString()));
//                                arlRegSolTra.add(INT_ARL_COT_VEN_COD_TIP_DOC,Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC).toString()));
//                                arlRegSolTra.add(INT_ARL_COT_VEN_COD_DOC,Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC).toString()));
//                                arlRegSolTra.add(INT_ARL_COT_VEN_COD_BOD_EGR,rstLoc.getInt("co_bodOrg")); // ORIGEN = EGRESO
//                                arlDatSolTra.add(arlRegSolTra);
//                            }
//                        }
//                    }
//                }
//            }
//            else{
//                blnRes=false;
//            }
//        }
//        catch (java.sql.SQLException e) { 
//            blnRes=false;  
//            objUti.mostrarMsgErr_F1(this, e);  
//        }
//        catch(Exception  Evt){ 
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, Evt);
//        }  
//        return blnRes;
//    }
    
    
    private void llenarArraySolTra(java.sql.Connection conn, int i){
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conn!=null){
                stmLoc= conn.createStatement();
                arlDatSolTra= new ArrayList();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a1.co_bodOrg \n";
                strSQL+=" FROM tbm_cabSolTraInv as a1 \n";
                strSQL+=" WHERE a1.co_emp="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP).toString()+" AND a1.co_loc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC).toString()+" AND a1.co_tipDoc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC).toString()+" AND a1.co_doc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC).toString()+"\n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    arlRegSolTra = new ArrayList();
                    arlRegSolTra.add(INT_ARL_COT_VEN_COD_EMP,Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP).toString()));
                    arlRegSolTra.add(INT_ARL_COT_VEN_COD_LOC,Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC).toString()));
                    arlRegSolTra.add(INT_ARL_COT_VEN_COD_TIP_DOC,Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC).toString()));
                    arlRegSolTra.add(INT_ARL_COT_VEN_COD_DOC,Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC).toString()));
                    arlRegSolTra.add(INT_ARL_COT_VEN_COD_BOD_EGR,rstLoc.getInt("co_bodOrg")); // ORIGEN = EGRESO
                    arlDatSolTra.add(arlRegSolTra);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e) { 
            objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception  Evt){ 
            objUti.mostrarMsgErr_F1(this, Evt);
        }  
    }
    
    private boolean blnIsImportacion=false;
    
    /**
     * Valida Stock Disponible 
     * 
     * Funcion para validar la cantidad disponible contra la solicitud de transferencia
     * @return 
     */
    private boolean validarStockDisponible(java.sql.Connection conExt, int i){
        boolean blnRes=true, blnIsStock=true, blnIsBodIngImp=true;
        java.sql.Statement stmLoc,stmLoc01;
        java.sql.ResultSet rstLoc, rstLoc01;
        String strMsg="", strItem="";
        try{
            blnIsImportacion=false;
            if(conExt!=null){
                stmLoc= conExt.createStatement();
                stmLoc01=conExt.createStatement();
                String strCadena="";
                strCadena+=" SELECT co_emp, co_cfg,tx_nom, co_loc, co_tipDoc, tx_obs1 FROM tbm_cfgTipDocUtiProAut ";
                strCadena+=" WHERE co_Emp="+objParSis.getCodigoEmpresaGrupo()+" AND co_cfg IN ("+INT_ARL_COD_CFG_SOL_SOTRINI+","+INT_ARL_COD_CFG_SOL_SOTRINC+") AND st_reg='A'";
                
                rstLoc01=stmLoc01.executeQuery(strCadena);
                while(rstLoc01.next()){
                    if(Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC).toString())==rstLoc01.getInt("co_tipDoc")){
                        blnIsImportacion=true;
                    }
                }
                rstLoc01.close();
                rstLoc01=null;
                stmLoc01.close();
                stmLoc01=null;
                if(blnIsImportacion){                            
                    strSQL="";
                    strSQL+=" SELECT  a9.co_emp, x1.co_itm, a3.co_itmMae, a2.co_reg, a2.nd_can, a6.co_bod, x1.co_bod as co_bodIngImp  \n";
                    strSQL+="       , CASE WHEN a6.nd_canDis IS NULL THEN 0 ELSE a6.nd_canDis END AS disponible, a7.tx_codAlt  , x1.co_itm, x1.nd_can as nd_canIngImp \n";
                    strSQL+="       , SUM( CASE WHEN x2.nd_can IS NULL THEN 0 ELSE x2.nd_can END)  as nd_canTran \n";
                    strSQL+="       , CASE WHEN a6.nd_canDis  >= a2.nd_can THEN (x1.nd_can - SUM(CASE WHEN x2.nd_can IS NULL THEN 0 ELSE x2.nd_can END )) - a2.nd_can ELSE -1 END as nd_canDis  \n";
                    strSQL+=" FROM tbm_cabSolTraInv as a1 \n";
                    strSQL+=" INNER JOIN tbm_detSolTraInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  \n";
                    strSQL+=" INNER JOIN tbm_equInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                    strSQL+=" INNER JOIN tbm_equInv as a4 ON (a3.co_itmMae=a4.co_itmMae )  \n";
                    strSQL+=" INNER JOIN tbr_bodEmpBodGrp as a5 ON (a1.co_emp=a5.co_empGrp AND a1.co_bodOrg=a5.co_bodGrp)  \n";
                    //strSQL+=" INNER JOIN tbm_invBod as a6 ON (a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm AND a5.co_bod=a6.co_bod AND a5.co_emp=a6.co_emp)  \n";  Lo cambie de lugar JM 
                    strSQL+=" INNER JOIN tbm_inv as a7 ON (a4.co_emp=a7.co_emp AND a4.co_itm=a7.co_itm)  \n";
                    strSQL+=" INNER JOIN tbr_cabSolTraInvCabMovInv as a8 ON (a1.co_emp=a8.co_empRelCabSolTraInv AND a1.co_loc=a8.co_locRelCabSolTraInv AND   \n";
                    strSQL+="                                                a1.co_tipDoc=a8.co_tipDocRelCabSolTraInv AND a1.co_doc=a8.co_docRelCabSolTraInv) \n";
                    strSQL+=" INNER JOIN tbm_cabMovInv as a9 ON (a8.co_empRelCabMovInv=a9.co_emp AND a8.co_locRelCabMovInv=a9.co_loc AND a8.co_tipDocRelCabMovInv=a9.co_tipDoc AND a8.co_docRelCabMovInv=a9.co_doc)\n";
                    strSQL+=" INNER JOIN tbm_detMovInv as x1 ON (a9.co_emp=x1.co_emp AND a9.co_loc=x1.co_loc AND a9.co_tipDoc=x1.co_tipDoc AND a9.co_doc=x1.co_doc AND a4.co_itm=x1.co_itm) \n";
                    strSQL+=" INNER JOIN tbm_invBod as a6 ON (a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm AND a5.co_bod=a6.co_bod AND a5.co_emp=a6.co_emp AND a9.co_emp=a6.co_emp)  \n";
                    strSQL+=" LEFT OUTER JOIN( \n";
                    strSQL+="       SELECT a3.co_emp, a5.co_itm,SUM(a5.nd_can) as nd_can  \n";
                    strSQL+="       FROM tbr_cabSolTraInvCabMovInv as a1  \n";
                    strSQL+="       INNER JOIN tbr_cabSolTraInvCabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_empRelCabMovInv AND a1.co_locRelCabMovinv=a2.co_locRelCabMovinv AND \n";
                    strSQL+="                                                      a1.co_tipDocRelCabMovinv=a2.co_tipDocRelCabMovinv AND a1.co_docRelCabMovinv=a2.co_docRelCabMovinv) \n";
                    strSQL+="       INNER JOIN tbm_cabMovInv as a3 ON (a3.co_empRelCabSolTraInv=a2.co_EmpRelCabSolTraInv AND a3.co_locRelCabSolTraInv=a2.co_locRelCabSolTraInv AND \n";
                    strSQL+="                                          a3.co_tipDocRelCabSolTraInv=a2.co_tipDocRelCabSolTraInv AND a3.co_docRelCabSolTraInv=a2.co_docRelCabSolTraInv)  \n";
                    strSQL+="       INNER JOIN tbm_detMovInv as a5 ON (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipDoc=a5.co_tipDoc AND a3.co_doc=a5.co_doc AND a5.nd_can>0 ) /* TRANSFERENCIAS */ \n";
                    strSQL+="       WHERE a1.co_EmpRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)+" AND  a1.co_locRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC)+"  \n";
                    strSQL+="       AND a1.co_tipDocRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC)+" AND   a1.co_docRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC)+"  \n";
                    strSQL+="       GROUP BY a3.co_emp, a5.co_itm \n";
                    strSQL+=" ) AS x2 ON (a9.co_emp=x2.co_emp AND  x1.co_itm=x2.co_itm ) \n";
                    strSQL+=" WHERE a1.co_emp="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)+" and a1.co_loc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC)+" and   \n";
                    strSQL+="       a1.co_tipDoc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC)+" and a1.co_doc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC)+"    \n";
                    strSQL+=" GROUP BY  a9.co_emp, x1.co_itm, a3.co_itmMae,a2.co_reg, a2.nd_can ,a6.co_bod, x1.co_bod ,a6.nd_canDis, a7.tx_codAlt, x1.co_itm, x1.nd_can, x2.nd_can, x1.nd_can  \n";
                    strSQL+=" ORDER BY  a2.co_reg \n";
                }
                else{
                    strSQL="";
                    strSQL+=" SELECT a.co_itmMae, (a.nd_canDis - b.nd_can) as nd_canDis, case WHEN (a.nd_canDis - b.nd_can)  <0 THEN 'N' else 'S' END as Suficiente,\n";
                    strSQL+="        a.tx_codAlt,ROUND (a.nd_canDis,2) as disponible, ROUND(b.nd_can,2) as nd_can  \n";
                    strSQL+=" FROM (\n";      
                    strSQL+="       SELECT a.co_bodGrp, a.co_itmMae,SUM(a.nd_canDis) as nd_canDis,a.tx_codAlt \n";
                    strSQL+="       FROM( \n";
                    strSQL+="           SELECT a3.co_itmMae, a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_canDis, a4.co_empGrp, a4.co_bodGrp,a2.tx_codAlt \n";
                    strSQL+="           FROM tbm_invBod AS a1   \n";
                    strSQL+="           INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                    strSQL+="           INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)  \n";
                    strSQL+="           INNER JOIN tbr_bodEmpBodGrp as a4 ON (a1.co_emp=a4.co_emp AND a1.co_bod=a4.co_bod)  \n";
                    strSQL+="           WHERE a3.co_itmMae IN (  \n";
                    strSQL+="                           SELECT co_itmMae  \n";
                    strSQL+="                           FROM tbm_detSolTraInv as a1   \n";
                    strSQL+="                           INNER JOIN tbm_equInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)  \n";
                    strSQL+="                           WHERE a1.co_doc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC)+" AND a1.co_emp="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)+" AND   \n";
                    strSQL+="                           a1.co_loc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC)+" AND a1.co_tipDoc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC)+" ) \n";
                    strSQL+="       ) as a \n";
                    strSQL+=" WHERE a.co_bodGrp = (SELECT co_bodOrg FROM tbm_cabSolTraInv  \n";
                    strSQL+="                       WHERE co_emp="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)+" and co_loc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC)+" and   \n";
                    strSQL+="                             co_tipDoc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC)+" and co_doc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC)+" ) \n";
                    strSQL+=" GROUP BY a.co_bodGrp, a.co_itmMae,a.tx_codAlt   \n";
                    strSQL+=" ) as a \n";
                    strSQL+=" INNER JOIN ( \n";
                    strSQL+="           SELECT a2.co_itmMae, SUM(a1.nd_can) as nd_can  \n";
                    strSQL+="           FROM tbm_detSolTraInv as a1  \n";
                    strSQL+="           INNER JOIN tbm_equInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)  \n";
                    strSQL+="           WHERE a1.co_emp="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)+" and a1.co_loc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC)+" and   \n";
                    strSQL+="                 a1.co_tipDoc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC)+" and a1.co_doc="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC)+"  \n";
                    strSQL+="           GROUP BY a2.co_itmMae\n";
                    strSQL+=" ) as b ON (a.co_itmMae=b.co_itmMae) \n";
                }
                System.out.println("validarStockDisponible: "+ strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                while(rstLoc.next()){
                    if(blnIsImportacion){
                        //Valida que la bodega origen de la solicitud corresponda a la bodega del ingreso por importacion.
                        if(!(rstLoc.getString("co_bod")).equals(rstLoc.getString("co_bodIngImp"))){
                            blnRes=false;
                            blnIsBodIngImp=false;
                        }
                    }
                    
                    if(blnIsBodIngImp)
                    {
                        //Valida disponible
                        if(Double.parseDouble(rstLoc.getString("nd_canDis"))<0.00){
                           blnRes=false;
                           blnIsStock=false;
                           strItem+="<tr><td>" +  rstLoc.getString("tx_codAlt") + " </td>";
                           strItem+=" <td>" + objUti.redondear(rstLoc.getString("disponible"), objParSis.getDecimalesMostrar())  + " </td>";
                           strItem+=" <td>" + objUti.redondear(rstLoc.getString("nd_can"), objParSis.getDecimalesMostrar()) + " </td> ";

                           if(blnIsImportacion){
                                strItem+=" <td>" + objUti.redondear(rstLoc.getString("nd_canIngImp"), objParSis.getDecimalesMostrar()) + " </td> ";
                                strItem+=" <td>" + objUti.redondear(rstLoc.getString("nd_canTran"), objParSis.getDecimalesMostrar()) + " </td> ";
                           }
                           strItem+=" </tr> ";
                        }
                    }
                }
                if(!blnIsBodIngImp) {
                    mostrarMsgInf("La solicitud que desea autorizar tiene la bodega origen distinta a la del ingreso por importación");
                }
                
                if(!blnIsStock){
                    strMsg="<html> La solicitud posee Items con cantidades insuficientes. <BR><BR>" ;// CAMBIA
                    strMsg+=" <table BORDER=1><tr><td> ITEM </td> <td> Disponible </td><td> Can.Sol. </td>";
                    if(blnIsImportacion){
                        strMsg+="<td> Ingreso Importación </td>";
                        strMsg+="<td> Transferencias </td>";
                    }
                    strMsg+=""+ strItem + "    ";
                    strMsg+=" </table><BR>";
                    strMsg+="No se puede realizar esta operación. <html>";
                    //System.out.println(strMsg);
                    String strTit="Mensaje del sistema Zafiro";
                    JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            else{
                blnRes=false;
            }
            
        }
        catch (java.sql.SQLException e) { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception  Evt){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }  
        return blnRes;
    } 
    
//    /**
//     * Insertar Seguimiento
//     * Despues de autorizar la solicitud esta se guarda en la tabla de seguimiento de transferencias de inventario 
//     * 
//     * @param conn
//     * @return 
//     */
//    private boolean insertaSeguimiento(java.sql.Connection conn){
//        boolean blnRes=true;
//        java.sql.Statement stmLoc,stmLoc01;
//        java.sql.ResultSet rstLoc,rstLoc01;
//         int intCodSeg=0,intCodRegSolTra=1;
//        try{
//            if(conn!=null){
//                
//                int intNumFil=objTblMod.getRowCountTrue();
//                for (int i=0; i<intNumFil;i++){
//                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
//                        if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_AUT)){
//                            stmLoc = conn.createStatement();
//                            
//                            strSQL = "";
//                            strSQL += " SELECT co_empRelCabSolTraInv \n";
//                            strSQL += " FROM tbm_cabSegMovInv \n";
//                            strSQL += " WHERE co_empRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)+" \n";
//                            strSQL += " AND co_locRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC)+" \n";
//                            strSQL += " AND co_tipDocRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC)+" \n";
//                            strSQL += " AND co_docRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC)+" " ;
//                            rstLoc = stmLoc.executeQuery(strSQL);
//                            if (rstLoc.next()) {
//                                
//                            }else{
//                                stmLoc01 = conn.createStatement();
//                                strSQL = "";
//                                strSQL+= "  SELECT (CASE WHEN MAX(a1.co_seg) IS NULL THEN 0 ELSE MAX(a1.co_seg) END +1) as co_seg   \n";
//                                strSQL+= " FROM tbm_cabSegMovInv as a1  \n";
//                                rstLoc01 = stmLoc01.executeQuery(strSQL);
//                                if (rstLoc01.next()) {
//                                    intCodSeg=rstLoc01.getInt("co_seg");
//                                }
//                                strSQL = "";
//                                strSQL+= " INSERT INTO tbm_cabSegMovInv (co_seg, co_reg, co_empRelCabSolTraInv, co_locRelCabSolTraInv, co_tipDocRelCabSolTraInv, co_docRelCabSolTraInv) \n";
//                                strSQL+= " VALUES ("+intCodSeg+","+intCodRegSolTra+","+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP);
//                                strSQL+= ","+objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC)+","+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC) +","+objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC)+"); \n";
//                                stmLoc01.executeUpdate(strSQL);
//                                rstLoc01.close();
//                                rstLoc01=null;
//                                stmLoc01.close();
//                                stmLoc01=null;
//                            }
//                            rstLoc.close();
//                            rstLoc=null;
//                            stmLoc.close();
//                            stmLoc=null;
//                        }
//                    }
//                }
//            }
//            else{
//                blnRes=false;
//            }
//        }
//        catch (java.sql.SQLException e) { 
//            blnRes=false;  
//            objUti.mostrarMsgErr_F1(this, e);  
//        }
//        catch(Exception  Evt){ 
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, Evt);
//        }  
//        return blnRes;
//    }
    
    
    /**
     * Insertar Seguimiento
     * Despues de autorizar la solicitud esta se guarda en la tabla de seguimiento de transferencias de inventario 
     * 
     * @param conn
     * @return 
     */
    private boolean insertaSeguimiento(java.sql.Connection conn, int i){
        boolean blnRes=true;
        java.sql.Statement stmLoc,stmLoc01;
        java.sql.ResultSet rstLoc,rstLoc01;
         int intCodSeg=0,intCodRegSolTra=1;
        try{
            if(conn!=null){
                stmLoc = conn.createStatement();
                strSQL = "";
                strSQL += " SELECT co_empRelCabSolTraInv \n";
                strSQL += " FROM tbm_cabSegMovInv \n";
                strSQL += " WHERE co_empRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)+" \n";
                strSQL += " AND co_locRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC)+" \n";
                strSQL += " AND co_tipDocRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC)+" \n";
                strSQL += " AND co_docRelCabSolTraInv="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC)+" " ;
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) {

                }else{
                    stmLoc01 = conn.createStatement();
                    strSQL = "";
                    strSQL+= "  SELECT (CASE WHEN MAX(a1.co_seg) IS NULL THEN 0 ELSE MAX(a1.co_seg) END +1) as co_seg   \n";
                    strSQL+= " FROM tbm_cabSegMovInv as a1  \n";
                    rstLoc01 = stmLoc01.executeQuery(strSQL);
                    if (rstLoc01.next()) {
                        intCodSeg=rstLoc01.getInt("co_seg");
                    }
                    strSQL = "";
                    strSQL+= " INSERT INTO tbm_cabSegMovInv (co_seg, co_reg, co_empRelCabSolTraInv, co_locRelCabSolTraInv, co_tipDocRelCabSolTraInv, co_docRelCabSolTraInv) \n";
                    strSQL+= " VALUES ("+intCodSeg+","+intCodRegSolTra+","+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP);
                    strSQL+= ","+objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC)+","+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC) +","+objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC)+"); \n";
                    stmLoc01.executeUpdate(strSQL);
                    rstLoc01.close();
                    rstLoc01=null;
                    stmLoc01.close();
                    stmLoc01=null;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e) { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception  Evt){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }  
        return blnRes;
    }
    
    // <editor-fold defaultstate="collapsed" desc=" /* José Marín: */ ">
//    /**
//     * GENERA PRESTAMOS
//     * Este metodo se encarga de realizar los prestamos y transferencias para obtener los items 
//     * necesarios para la solicitud...
//     * 
//     * En el paso 1: se obtiene un contenedor con los items que se van a solicitar ingresen en una empresa... la empresa que se envia es la que
//     * esta solicitando la mercaderia 
//     * 
//     * En el paso 2: se envia dos veces la informacion al metodo ZafReaMovInv, 1a primera instancia compra/vende la mercaderia en la misma bodega 
//     * es decir si se transfiere de Inmaconsa a California, el primer paso seria pasar la mercaderia a tuval(inmaconsa) haciendo prestamos, y 
//     * la segunda instancia seria la transferencia de inmaconsa a california
//     * 
//     * @param conLoc
//     * @return 
//     */
//
//    private boolean generaPrestamos(java.sql.Connection conLoc)
//    {
//        int intNumFil, i,intCodEmp, intCodLoc, intCodTipDoc, intCodDoc;
//        java.sql.Statement stmLoc,stmLoc01;
//        java.sql.ResultSet rstLoc, rstLoc01;
//        boolean blnRes=true,isEnviaSolicitud=true;
//        try
//        {
//            if (conLoc!=null)
//            {
//                stmLoc=conLoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
//                stmLoc01=conLoc.createStatement();
//                if (datFecAux==null)
//                    return false;
//                intNumFil=objTblMod.getRowCountTrue();
//                for (i=0; i<intNumFil;i++){
//                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
//                        if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_AUT)){
//                            /*
//                                PRIMERO NECESITO SABER SI EN LA EMPRESA DUEÑA DE LA BODEGA EXISTE LA MERCADERIA
//                                DESPUES SI TIENE LO SUFICIENTE SOLO SE HACE UNA TRANSFERENCIA
//                                CASO CONTRARIO PRESTAMOS ENTRE EMPRESAS PARA LO DEMAS LA QUE NO TIENE LO QUE LE FALTE.
//                            */
//                            /* Paso 1 */
//                            arlDat = new ArrayList();
//                            isEnviaSolicitud=true;
//                            dblCanFal=0.0;
//                            intCodEmp=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP).toString());
//                            intCodLoc=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC).toString());
//                            intCodTipDoc=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC).toString());
//                            intCodDoc=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC).toString());
//                            strSQL="";
//                            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a1.co_bodOrg, a1.co_bodDes,a2.co_reg,a2.co_itm,a2.nd_can \n";
//                            strSQL+=" FROM tbm_cabSolTraInv as a1 \n";
//                            strSQL+=" INNER JOIN tbm_detSolTraInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc  \n";
//                            strSQL+="                                        AND a1.co_doc=a2.co_doc)\n";
//                            strSQL+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_tipDoc="+intCodTipDoc+" AND a1.co_doc="+intCodDoc+"\n";
//                            rstLoc=stmLoc.executeQuery(strSQL);
//                            while(rstLoc.next()){
//                                if(obtenerEmpresaItemBodega(rstLoc.getInt("co_bodDes"),rstLoc.getInt("co_itm"),rstLoc.getInt("co_bodOrg"))){  // BODEGA DESTINO TIENE DUEÑO
//                                    if(obtenerCantidadNecesaria(conLoc,rstLoc.getDouble("nd_can"), intCodItmSolTra, rstLoc.getInt("co_bodOrg"))){
//                                        arlReg = new ArrayList();
//                                        arlReg.add(INT_ARL_COD_EMP, intCodEmpSolTra);
//                                        arlReg.add(INT_ARL_COD_LOC, intCodLocSolTra);
//                                        arlReg.add(INT_ARL_COD_TIP_DOC, intCodTipDoc);
//                                        arlReg.add(INT_ARL_COD_BOD_GRP, rstLoc.getInt("co_bodOrg"));// BODEGA DE ORIGEN
//                                        arlReg.add(INT_ARL_COD_ITM, intCodItmSolTra);  
//                                        arlReg.add(INT_ARL_COD_ITM_MAE,intCodItmMaeSolTra);   
//                                        arlReg.add(INT_ARL_COD_BOD, intCodBodOrgSolTra);
//                                        arlReg.add(INT_ARL_NOM_BOD, strNomBodSolTra);
//                                        arlReg.add(INT_ARL_CAN_COM, dblCanFal );  // CANTIDAD NECESARIA!!!
//                                        arlReg.add(INT_ARL_CHK_CLI_RET, false);  // Cliente Retira
//                                        arlReg.add(INT_ARL_EST_BOD, 'A');  // A U C   Para saber si necesita pedir autorizaciones o debe ser cliente retira
//                                        arlReg.add(INT_ARL_ING_EGR_FIS_BOD, "S");
//                                        arlReg.add(INT_ARL_COD_BOD_GRP_ING, rstLoc.getInt("co_bodOrg")); // BODEGA DE DESTINO
//                                        arlDat.add(arlReg);
//                                    }
//                                }
//                                else{  //BODEGA SIN DUEÑO 
//                                    double dblCanPorTraDir=rstLoc.getDouble("nd_can");// CANTIDAD NECASARIA
//                                    /*NO TERMINADO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
//                                    /* SOLO POR TRANSFERENCIA,DIRECTAMENTE SEGUN DONDE HAYA MERCADERIA JM*/
//                                    strSQL="";
//                                    strSQL+=" SELECT a.co_emp , a.co_bod, a.co_itm ,  \n";
//                                    strSQL+="        CASE WHEN var.tx_tipunimed = 'E' THEN  TRUNC(a.nd_canDis) ELSE a.nd_canDis END AS nd_canDis \n";
//                                    strSQL+=" FROM tbm_invbod as a   \n";
//                                    strSQL+=" INNER JOIN tbr_bodEmpBodGrp as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod) \n";
//                                    strSQL+=" INNER JOIN tbm_inv AS inv ON(inv.co_emp=a.co_emp AND inv.co_itm=a.co_itm ) \n";
//                                    strSQL+=" INNER JOIN tbm_equInv AS invGru ON (inv.co_emp=invGru.co_emp AND inv.co_itm=invGru.co_itm) \n";
//                                    strSQL+=" LEFT JOIN tbm_var AS var ON(var.co_reg=inv.co_uni ) \n";
//                                    strSQL+=" WHERE invGru.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+rstLoc.getInt("co_emp")+" AND co_itm="+rstLoc.getInt("co_itm")+")   \n";
//                                    strSQL+="        AND (a1.co_empGrp="+rstLoc.getInt("co_emp")+" AND a1.co_bodGrp="+rstLoc.getInt("co_bodOrg")+")  \n";
//                                    strSQL+=" ORDER BY nd_canDis DESC  \n";
//                                    rstLoc01= stmLoc01.executeQuery(strSQL);
//                                    while(rstLoc01.next()){
//                                        /* Cantidad disponible por empresa, puede tomar de 1 empresa misma bodega o puede tomar de todas las empresas misma bodega */
//                                        if(rstLoc01.getDouble("nd_canDis")>=dblCanPorTraDir){
//                                            dblCanFal=dblCanPorTraDir;
//                                            dblCanPorTraDir=0;
//                                        }else{
//                                            if(dblCanPorTraDir>0){
//                                                dblCanFal=rstLoc01.getDouble("nd_canDis");
//                                                dblCanPorTraDir=dblCanPorTraDir-dblCanFal;
//                                            }
//                                        }
//                                        if(blnIsDueBod){
//                                            if(dblCanFal > 0 ){////PENDIENTES!!!!
//                                                if(obtenerItemBodega(rstLoc01.getInt("co_emp"),rstLoc.getInt("co_bodDes"),rstLoc01.getInt("co_itm"),rstLoc.getInt("co_bodOrg"))){
//                                                    arlReg = new ArrayList();
//                                                    arlReg.add(INT_ARL_COD_EMP, rstLoc01.getInt("co_emp"));
//                                                    arlReg.add(INT_ARL_COD_LOC, intCodLocSolTra);
//                                                    arlReg.add(INT_ARL_COD_TIP_DOC, intCodTipDoc);
//                                                    arlReg.add(INT_ARL_COD_BOD_GRP, rstLoc.getInt("co_bodOrg"));// BODEGA DE ORIGEN
//                                                    arlReg.add(INT_ARL_COD_ITM, intCodItmSolTra);  
//                                                    arlReg.add(INT_ARL_COD_ITM_MAE,intCodItmMaeSolTra);   
//                                                    arlReg.add(INT_ARL_COD_BOD, intCodBodOrgSolTra);
//                                                    arlReg.add(INT_ARL_NOM_BOD, strNomBodSolTra);
//                                                    arlReg.add(INT_ARL_CAN_COM, dblCanFal );  // CANTIDAD NECESARIA!!!
//                                                    arlReg.add(INT_ARL_CHK_CLI_RET, false);  // Cliente Retira
//                                                    arlReg.add(INT_ARL_EST_BOD, 'A');  // A U C   Para saber si necesita pedir autorizaciones o debe ser cliente retira
//                                                    arlReg.add(INT_ARL_ING_EGR_FIS_BOD, "S");
//                                                    arlReg.add(INT_ARL_COD_BOD_GRP_ING,/* intCodBodOrgSolTra*/ rstLoc.getInt("co_bodOrg")); // BODEGA DE DESTINO
//                                                    arlDat.add(arlReg);
//                                                }
//                                                dblCanFal=0.00;
//                                            }
//                                        }
//                                        else{
//                                            if(dblCanFal > 0 ){////PENDIENTES!!!!
//                                                if(obtenerItemBodega(rstLoc01.getInt("co_emp"),rstLoc.getInt("co_bodDes"),rstLoc01.getInt("co_itm"),rstLoc.getInt("co_bodOrg"))){
//                                                    arlReg = new ArrayList();
//                                                    arlReg.add(INT_ARL_COD_EMP, rstLoc01.getInt("co_emp"));
//                                                    arlReg.add(INT_ARL_COD_LOC, intCodLocSolTra);
//                                                    arlReg.add(INT_ARL_COD_TIP_DOC, intCodTipDoc);
//                                                    arlReg.add(INT_ARL_COD_BOD_GRP, rstLoc.getInt("co_bodOrg"));// BODEGA DE ORIGEN
//                                                    arlReg.add(INT_ARL_COD_ITM, intCodItmSolTra);  
//                                                    arlReg.add(INT_ARL_COD_ITM_MAE,intCodItmMaeSolTra);   
//                                                    arlReg.add(INT_ARL_COD_BOD, intCodBodOrgSolTra);
//                                                    arlReg.add(INT_ARL_NOM_BOD, strNomBodSolTra);
//                                                    arlReg.add(INT_ARL_CAN_COM, dblCanFal );  // CANTIDAD NECESARIA!!!
//                                                    arlReg.add(INT_ARL_CHK_CLI_RET, false);  // Cliente Retira
//                                                    arlReg.add(INT_ARL_EST_BOD, 'A');  // A U C   Para saber si necesita pedir autorizaciones o debe ser cliente retira
//                                                    arlReg.add(INT_ARL_ING_EGR_FIS_BOD, "S");
//                                                    arlReg.add(INT_ARL_COD_BOD_GRP_ING,/* intCodBodOrgSolTra*/ rstLoc.getInt("co_bodDes")); // BODEGA DE DESTINO
//                                                    arlDat.add(arlReg);
//                                                }
//                                                dblCanFal=0.00;
//                                            }
//                                        }
//                                        
//                                    }
//                                    rstLoc01.close();
//                                    rstLoc01=null;
//                                }
//                                if(isEnviaSolicitud){
//                                    /* CONTENEDOR PARA ENVIAR A MOVIENTO DE INVENTARIO */
//                                    arlDatSolTra= new ArrayList();
//                                    arlRegSolTra = new ArrayList();
//                                    arlRegSolTra.add(INT_ARL_COT_VEN_COD_EMP,Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP).toString()));
//                                    arlRegSolTra.add(INT_ARL_COT_VEN_COD_LOC,Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC).toString()));
//                                    arlRegSolTra.add(INT_ARL_COT_VEN_COD_TIP_DOC,Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC).toString()));
//                                    arlRegSolTra.add(INT_ARL_COT_VEN_COD_DOC,Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC).toString()));
//                                    arlRegSolTra.add(INT_ARL_COT_VEN_COD_BOD_EGR,rstLoc.getInt("co_bodOrg")); // ORIGEN = EGRESO
//                                    arlDatSolTra.add(arlRegSolTra);
//                                    isEnviaSolicitud=false;
//                                    /* ENVIAR EL CONTENEDOR */
//                                }
//                            }  /* FIN SOLICITUK!!! xD  */ 
//                            
//                            
//                            /*================================== Paso 2 ===============================================*/
//                            if(blnIsDueBod){
//                                
//                                if(!objReaMovInv.inicia(conLoc, arlDat,datFecAux ,arlDatSolTra)){
//                                    blnRes=false;  
//                                }
//                                else{
//                                    /*
//                                        Si se logra generar los prestamos entre empresas o transferencias para poner la mercaderia 
//                                        en una sola bodega. El siguiente paso seria hacer la transferencia  
//                                    */
//                                    System.out.println("PARTE DOS ZAFCOM92 ");
//                                    arlDat.clear();
//                                    rstLoc.beforeFirst();
//                                    while(rstLoc.next()){
//                                        if(obtenerEmpresaItemBodega(rstLoc.getInt("co_bodDes"),rstLoc.getInt("co_itm"),rstLoc.getInt("co_bodOrg"))){
//                                            arlReg = new ArrayList();
//                                            arlReg.add(INT_ARL_COD_EMP, intCodEmpSolTra);
//                                            arlReg.add(INT_ARL_COD_LOC, intCodLocSolTra);
//                                            arlReg.add(INT_ARL_COD_TIP_DOC, intCodTipDoc);
//                                            arlReg.add(INT_ARL_COD_BOD_GRP, rstLoc.getInt("co_bodOrg"));// BODEGA DE ORIGEN
//                                            arlReg.add(INT_ARL_COD_ITM, intCodItmSolTra);  // 1 
//                                            arlReg.add(INT_ARL_COD_ITM_MAE,intCodItmMaeSolTra);  //14 
//                                            arlReg.add(INT_ARL_COD_BOD, intCodBodOrgSolTra);
//                                            arlReg.add(INT_ARL_NOM_BOD, strNomBodSolTra);
//                                            arlReg.add(INT_ARL_CAN_COM, rstLoc.getDouble("nd_can") );  // CAntidad para la necesidad
//                                            arlReg.add(INT_ARL_CHK_CLI_RET, false);  // Cliente Retira
//                                            arlReg.add(INT_ARL_EST_BOD, 'A');  // A U C   Para saber si necesita pedir autorizaciones o debe ser cliente retira
//                                            arlReg.add(INT_ARL_ING_EGR_FIS_BOD, "S");
//                                            arlReg.add(INT_ARL_COD_BOD_GRP_ING, /*intCodBodDesSolTra*/rstLoc.getInt("co_bodDes")); // BODEGA DE DESTINO
//                                            arlDat.add(arlReg);
//                                        }
//                                    }
//                                    if(!objReaMovInv.iniciaTransferencia(conLoc, arlDat,datFecAux ,arlDatSolTra)){
//                                        blnRes=false;  
//                                    }
//                                }
//                            }
//                            else{
//                                // SI NO TIENE DUEÑO SERIAN SOLO TRANSFERENCIAS PERO SE DEBE ENVIAR POR EMPRESA 
//                                if(ordenarLasTransferencias()){
//                                    if(blnTraTuv){
//                                        if(!objReaMovInv.iniciaTransferencia(conLoc, arlDatTraTuv,datFecAux,arlDatSolTra )){
//                                            blnRes=false;  
//                                        }
//                                    }
//                                    if(blnTraCas){
//                                        if(!objReaMovInv.iniciaTransferencia(conLoc, arlDatTraCas,datFecAux,arlDatSolTra )){
//                                            blnRes=false;  
//                                        }
//                                    }
//                                    if(blnTraDim){
//                                        if(!objReaMovInv.iniciaTransferencia(conLoc, arlDatTraDim,datFecAux,arlDatSolTra )){
//                                            blnRes=false;  
//                                        }
//                                    }
//                                }
//                            }
//                            /* ========================= FIN DE PASO DOS ========================= */
//                            rstLoc.close();
//                            rstLoc=null;
//                        }    
//                    }
//                }
//                stmLoc.close();
//                stmLoc=null;
//                //Inicializo el estado de las filas.
////                objTblMod.initRowsState();
////                objTblMod.setDataModelChanged(false);
//            }
//        }
//        catch (java.sql.SQLException e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
//    
   //</editor-fold>
    
    private boolean obtenerEmpresaItemBodega(int intCodBodDes,int intCodItm,int intCodBodOrg){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());        
            stmLoc= conLoc.createStatement();
            strSQL="";//tbm_bod 
            strSQL+=" SELECT co_empDueBod, co_bod, tx_nom ";        
            strSQL+=" FROM tbm_bod as a1";        
            strSQL+=" WHERE co_emp="+objParSis.getCodigoEmpresaGrupo()+" AND co_bod="+intCodBodDes;   
            rstLoc=stmLoc.executeQuery(strSQL);
            if(rstLoc.next()){
                if(rstLoc.getString("co_empDueBod")!=null){
                    intCodEmpSolTra=rstLoc.getInt("co_empDueBod");
                    intCodLocSolTra=intCodEmpSolTra==1?5:intCodEmpSolTra==2?5:intCodEmpSolTra==4?2:0;
                    blnIsDueBod=true;
                }
                else{
                    blnRes=false;
                    blnIsDueBod=false;
                }
            }
            
            if(intCodEmpSolTra!=0){
                strSQL=" ";
                strSQL+=" SELECT a1.co_emp, a1.co_itm, a2.co_itmMae,a1.tx_codAlt2 \n";
                strSQL+=" FROM tbm_inv as a1 \n";
                strSQL+=" INNER JOIN tbm_equInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)\n";
                strSQL+=" WHERE a2.co_itmMae = (\n";
                strSQL+="                       SELECT a2.co_itmMae ";
                strSQL+="                       FROM tbm_inv as a1  ";
                strSQL+="                       INNER JOIN tbm_equInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSQL+="                       WHERE a1.co_emp="+objParSis.getCodigoEmpresaGrupo()+" and a1.co_itm ="+intCodItm+" \n";
                strSQL+=" ) and a1.co_emp="+intCodEmpSolTra+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodItmSolTra=rstLoc.getInt("co_itm");
                    intCodItmMaeSolTra=rstLoc.getInt("co_itmMae");
                }
                else{
                    blnRes=false;
                }
                strSQL="";
                strSQL+=" select co_bod from tbr_bodEmpBodGrp where co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" \n";
                strSQL+=" and co_bodGrp="+intCodBodOrg+" and co_emp="+intCodEmpSolTra+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodBodOrgSolTra=rstLoc.getInt("co_bod");
                }
                
                strSQL="";
                strSQL+=" select a1.co_bod, a2.tx_nom  from tbr_bodEmpBodGrp  as a1 "
                        + "INNER JOIN tbm_bod as a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod) where a1.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" \n";
                strSQL+=" and a1.co_bodGrp="+intCodBodDes+" and a1.co_emp="+intCodEmpSolTra+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodBodDesSolTra=rstLoc.getInt("co_bod");
                    strNomBodSolTra=rstLoc.getString("tx_nom");
                }
            }
            rstLoc.close();
            rstLoc=null;
            conLoc.close();
            stmLoc.close();
            conLoc=null;
            stmLoc=null;
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
    
    /***
     * 
     * OBTIENE LOS DATOS SEGUN LOS PARAMETROS POR EMPRESA QUE RECIVE
     * 
     * intCodEmp: Codigo por Empresa
     * intCodBodDes: Codigo por Bodega de grupo
     * intCodItm: Item por empresa
     * intCodBodOrg: Codigo por Bodega de grupo
     */
    
    private boolean obtenerItemBodega(int intCodEmp, int intCodBodDes,int intCodItm,int intCodBodOrg){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());        
            stmLoc= conLoc.createStatement();
            
            intCodEmpSolTra=intCodEmp;  //X_x
            intCodLocSolTra=intCodEmpSolTra==1?5:intCodEmpSolTra==2?5:intCodEmpSolTra==4?2:0;//NUNCA SE GUARDA SOLO ES PARA ENVIAR 
                    
            strSQL=" ";
            strSQL+=" SELECT a1.co_emp, a1.co_itm, a2.co_itmMae,a1.tx_codAlt2 \n";
            strSQL+=" FROM tbm_inv as a1 \n";
            strSQL+=" INNER JOIN tbm_equInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)\n";
            strSQL+=" WHERE a2.co_itmMae = (\n";
            strSQL+="                       SELECT a2.co_itmMae ";
            strSQL+="                       FROM tbm_inv as a1  ";
            strSQL+="                       INNER JOIN tbm_equInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
            strSQL+="                       WHERE a1.co_emp="+intCodEmp+" and a1.co_itm ="+intCodItm+" \n";
            strSQL+=" ) and a1.co_emp="+intCodEmpSolTra+" \n";
            rstLoc=stmLoc.executeQuery(strSQL);
            if(rstLoc.next()){
                intCodItmSolTra=rstLoc.getInt("co_itm");
                intCodItmMaeSolTra=rstLoc.getInt("co_itmMae");
            }
            else{
                blnRes=false;
            }
            strSQL="";
            strSQL+=" select co_bod from tbr_bodEmpBodGrp where co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" \n";
            strSQL+=" and co_bodGrp="+intCodBodOrg+" and co_emp="+intCodEmp+" \n";
            rstLoc=stmLoc.executeQuery(strSQL);
            if(rstLoc.next()){
                intCodBodOrgSolTra=rstLoc.getInt("co_bod");
            }

            strSQL="";
            strSQL+=" select a1.co_bod, a2.tx_nom  from tbr_bodEmpBodGrp  as a1 "
                    + "INNER JOIN tbm_bod as a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod) where a1.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" \n";
            strSQL+=" and a1.co_bodGrp="+intCodBodDes+" and a1.co_emp="+intCodEmpSolTra+" \n";
            rstLoc=stmLoc.executeQuery(strSQL);
            if(rstLoc.next()){
                intCodBodDesSolTra=rstLoc.getInt("co_bod");
                strNomBodSolTra=rstLoc.getString("tx_nom");
            }
            rstLoc.close();
            rstLoc=null;
            conLoc.close();
            stmLoc.close();
            conLoc=null;
            stmLoc=null;
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
     *  dblCanNec: la cantidad que se pide en la ventana de pedidos
     *  intCodItm: el item que se esta trabajando
     *  intCodBod: la bodega donde se esta pidiendo
     *  Funcion se penso necesaria para solo enviar lo que se necesita en el contenedor, es decir lo que no posee la empresa que vende
     *  dentro de su bodega predeterminado, antes de hacer calculos se debe confirmar que se este vendiendo de la bodega predeterminada
     * 
     *  Jota
     */
    private boolean obtenerCantidadNecesaria(java.sql.Connection conn, Double dblCanNec, int intCodItm, int intCodBodGrp){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strCadena;
        int intBodPre=0, intBodOrg=0;
        try{
            if(conn!=null){
                stmLoc = conn.createStatement();
                strCadena="";
                strCadena+=" SELECT * FROM tbr_bodEmpBodGrp WHERE co_emp="+intCodEmpSolTra+" AND co_bodGrp=" + intCodBodGrp + " AND co_empGrp="+objParSis.getCodigoEmpresaGrupo();
                rstLoc = stmLoc.executeQuery(strCadena);
                if (rstLoc.next()) {
                    intBodOrg=rstLoc.getInt("co_bod");//BODEGA ORIGEN DE LA MERCADERIA POR EMPRESA QUE SOLICITA
                }
                strCadena="";
                strCadena+=" SELECT co_emp, co_bod, co_itm, nd_canDis FROM tbm_invBod WHERE co_emp="+intCodEmpSolTra;
                strCadena+=" and co_bod="+intBodOrg+" and co_itm="+intCodItm;
                strCadena+="";
                rstLoc = stmLoc.executeQuery(strCadena);// PARA TRANSFERIR???? 
                if (rstLoc.next()) {
                    if(dblCanNec>=rstLoc.getDouble("nd_canDis"))   {
                        dblCanFal=dblCanNec-rstLoc.getDouble("nd_canDis");  // SI NECESITO PEDIR ALGO QUE NO TENGA EN EL STOCK EN LA MISMA EMPRESA....  
                        blnRes=true;
                    }else{
                        dblCanFal=0.0;
                        blnRes=true;
                    } 
                }

                stmLoc.close();
                rstLoc.close();
                stmLoc=null;
                rstLoc=null;
            }
        }
        catch(java.sql.SQLException Evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
        catch(Exception Evt){ 
            objUti.mostrarMsgErr_F1(this, Evt);     
            blnRes=false;
        }
        return blnRes;
    }
    

    
    private boolean ordenarLasTransferencias(){
        boolean blnRes=false;
        try{
            arlDatTraTuv= new ArrayList();
            arlDatTraCas= new ArrayList();
            arlDatTraDim= new ArrayList();
            for(int i=0;i<arlDat.size();i++){
                if(1==objUti.getIntValueAt(arlDat, i, INT_ARL_COD_EMP)){
                    arlRegTraTuv = new ArrayList();
                    arlRegTraTuv.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlDat, i, INT_ARL_COD_EMP));
                    arlRegTraTuv.add(INT_ARL_COD_LOC, objUti.getIntValueAt(arlDat, i, INT_ARL_COD_LOC));
                    arlRegTraTuv.add(INT_ARL_COD_TIP_DOC, objUti.getIntValueAt(arlDat, i, INT_ARL_COD_TIP_DOC));
                    arlRegTraTuv.add(INT_ARL_COD_BOD_GRP, objUti.getIntValueAt(arlDat, i, INT_ARL_COD_BOD_GRP)); 
                    arlRegTraTuv.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlDat, i, INT_ARL_COD_ITM));  
                    arlRegTraTuv.add(INT_ARL_COD_ITM_MAE,objUti.getIntValueAt(arlDat, i, INT_ARL_COD_ITM_MAE));   
                    arlRegTraTuv.add(INT_ARL_COD_BOD, objUti.getIntValueAt(arlDat, i, INT_ARL_COD_BOD));
                    arlRegTraTuv.add(INT_ARL_NOM_BOD, objUti.getStringValueAt(arlDat, i, INT_ARL_NOM_BOD));
                    arlRegTraTuv.add(INT_ARL_CAN_COM, objUti.getStringValueAt(arlDat, i, INT_ARL_CAN_COM) );   
                    arlRegTraTuv.add(INT_ARL_CHK_CLI_RET, objUti.getStringValueAt(arlDat, i, INT_ARL_CHK_CLI_RET));   
                    arlRegTraTuv.add(INT_ARL_EST_BOD, objUti.getStringValueAt(arlDat, i, INT_ARL_EST_BOD));   
                    arlRegTraTuv.add(INT_ARL_ING_EGR_FIS_BOD, objUti.getStringValueAt(arlDat, i, INT_ARL_ING_EGR_FIS_BOD));
                    arlRegTraTuv.add(INT_ARL_COD_BOD_GRP_ING,objUti.getIntValueAt(arlDat, i, INT_ARL_COD_BOD_GRP_ING));  
                    arlDatTraTuv.add(arlRegTraTuv);
                    blnTraTuv=true;
                }
                else if(2==objUti.getIntValueAt(arlDat, i, INT_ARL_COD_EMP)){
                    arlRegTraCas = new ArrayList();
                    arlRegTraCas.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlDat, i, INT_ARL_COD_EMP));
                    arlRegTraCas.add(INT_ARL_COD_LOC, objUti.getIntValueAt(arlDat, i, INT_ARL_COD_LOC));
                    arlRegTraCas.add(INT_ARL_COD_TIP_DOC, objUti.getIntValueAt(arlDat, i, INT_ARL_COD_TIP_DOC));
                    arlRegTraCas.add(INT_ARL_COD_BOD_GRP, objUti.getIntValueAt(arlDat, i, INT_ARL_COD_BOD_GRP)); 
                    arlRegTraCas.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlDat, i, INT_ARL_COD_ITM));  
                    arlRegTraCas.add(INT_ARL_COD_ITM_MAE,objUti.getIntValueAt(arlDat, i, INT_ARL_COD_ITM_MAE));   
                    arlRegTraCas.add(INT_ARL_COD_BOD, objUti.getIntValueAt(arlDat, i, INT_ARL_COD_BOD));
                    arlRegTraCas.add(INT_ARL_NOM_BOD, objUti.getStringValueAt(arlDat, i, INT_ARL_NOM_BOD));
                    arlRegTraCas.add(INT_ARL_CAN_COM, objUti.getStringValueAt(arlDat, i, INT_ARL_CAN_COM) );   
                    arlRegTraCas.add(INT_ARL_CHK_CLI_RET, objUti.getStringValueAt(arlDat, i, INT_ARL_CHK_CLI_RET));   
                    arlRegTraCas.add(INT_ARL_EST_BOD, objUti.getStringValueAt(arlDat, i, INT_ARL_EST_BOD));   
                    arlRegTraCas.add(INT_ARL_ING_EGR_FIS_BOD, objUti.getStringValueAt(arlDat, i, INT_ARL_ING_EGR_FIS_BOD));
                    arlRegTraCas.add(INT_ARL_COD_BOD_GRP_ING,objUti.getIntValueAt(arlDat, i, INT_ARL_COD_BOD_GRP_ING));  
                    arlDatTraCas.add(arlRegTraCas);
                    blnTraCas=true;
                }
                else{
                    arlRegTraDim = new ArrayList();
                    arlRegTraDim.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlDat, i, INT_ARL_COD_EMP));
                    arlRegTraDim.add(INT_ARL_COD_LOC, objUti.getIntValueAt(arlDat, i, INT_ARL_COD_LOC));
                    arlRegTraDim.add(INT_ARL_COD_TIP_DOC, objUti.getIntValueAt(arlDat, i, INT_ARL_COD_TIP_DOC));
                    arlRegTraDim.add(INT_ARL_COD_BOD_GRP, objUti.getIntValueAt(arlDat, i, INT_ARL_COD_BOD_GRP)); 
                    arlRegTraDim.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlDat, i, INT_ARL_COD_ITM));  
                    arlRegTraDim.add(INT_ARL_COD_ITM_MAE,objUti.getIntValueAt(arlDat, i, INT_ARL_COD_ITM_MAE));   
                    arlRegTraDim.add(INT_ARL_COD_BOD, objUti.getIntValueAt(arlDat, i, INT_ARL_COD_BOD));
                    arlRegTraDim.add(INT_ARL_NOM_BOD, objUti.getStringValueAt(arlDat, i, INT_ARL_NOM_BOD));
                    arlRegTraDim.add(INT_ARL_CAN_COM, objUti.getStringValueAt(arlDat, i, INT_ARL_CAN_COM) );   
                    arlRegTraDim.add(INT_ARL_CHK_CLI_RET, objUti.getStringValueAt(arlDat, i, INT_ARL_CHK_CLI_RET));   
                    arlRegTraDim.add(INT_ARL_EST_BOD, objUti.getStringValueAt(arlDat, i, INT_ARL_EST_BOD));   
                    arlRegTraDim.add(INT_ARL_ING_EGR_FIS_BOD, objUti.getStringValueAt(arlDat, i, INT_ARL_ING_EGR_FIS_BOD));
                    arlRegTraDim.add(INT_ARL_COD_BOD_GRP_ING,objUti.getIntValueAt(arlDat, i, INT_ARL_COD_BOD_GRP_ING));  
                    arlDatTraDim.add(arlRegTraDim);
                    blnTraDim=true;
                }
            }
            blnRes=true;
        }
        catch(Exception Evt){ 
            objUti.mostrarMsgErr_F1(this, Evt);     
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    /*REMOTO!!!  Reposiciones */

    
    public boolean generaTransferenciaPreAutorizada(java.sql.Connection conn, int intCodEmp, int intCodLoc,int intCodTipDoc, int intCodDoc){
       boolean blnRes=false;
       try{
            if(conn!=null){
                if(llamaObjetoCfgBod(conn,intCodEmp,intCodLoc,intCodTipDoc,intCodDoc)){
                    if(generaPrestamos(conn,intCodEmp,intCodLoc,intCodTipDoc,intCodDoc)){
                         blnRes=true;
                    }
                }
            }
       }
       catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
       }  
       return blnRes;
    }
    
    
    
    private boolean insertaSeguimiento(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
         int intCodSeg=0,intCodRegSolTra=1;
        try{
            if(conn!=null){
                stmLoc= conn.createStatement();
                strSQL = "";
                strSQL+= "  SELECT (CASE WHEN MAX(a1.co_seg) IS NULL THEN 0 ELSE MAX(a1.co_seg) END +1) as co_seg   \n";
                strSQL+= " FROM tbm_cabSegMovInv as a1  \n";
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) {
                    intCodSeg=rstLoc.getInt("co_seg");
                }
                strSQL = "";
                strSQL+= " INSERT INTO tbm_cabSegMovInv (co_seg, co_reg, co_empRelCabSolTraInv, co_locRelCabSolTraInv, co_tipDocRelCabSolTraInv, co_docRelCabSolTraInv) \n";
                strSQL+= " VALUES ("+intCodSeg+","+intCodRegSolTra+","+ intCodEmp;
                strSQL+= ","+intCodLoc+","+ intCodTipDoc +","+intCodDoc+"); \n";
                stmLoc.executeUpdate(strSQL);
                rstLoc.close();
                rstLoc=null;
            }
        }
        catch (java.sql.SQLException e) { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception  Evt){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }  
        return blnRes;
        
        
    }
    
      /**
     * GENERA PRESTAMOS
     * Este metodo se encarga de realizar los prestamos y transferencias para obtener los items 
     * necesarios para la solicitud...
     * 
     * En el paso 1: se obtiene un contenedor con los items que se van a solicitar ingresen en una empresa... la empresa que se envia es la que
     * esta solicitando la mercaderia 
     * 
     * En el paso 2: se envia dos veces la informacion al metodo ZafReaMovInv, 1a primera instancia compra/vende la mercaderia en la misma bodega 
     * es decir si se transfiere de Inmaconsa a California, el primer paso seria pasar la mercaderia a tuval(inmaconsa) haciendo prestamos, y 
     * la segunda instancia seria la transferencia de inmaconsa a california
     * 
     * @param conLoc
     * @return 
     */
    
    private boolean generaPrestamos(java.sql.Connection conLoc, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc)
    {
        java.sql.Statement stmLoc,stmLoc01;
        java.sql.ResultSet rstLoc, rstLoc01;
        boolean blnRes=true,isEnviaSolicitud=true;
        try{
            if (conLoc!=null){
                stmLoc=conLoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //stmLoc01=conLoc.createStatement();
                if (datFecAux==null)
                    return false;
                /*
                    PRIMERO NECESITO SABER SI EN LA EMPRESA DUEÑA DE LA BODEGA EXISTE LA MERCADERIA
                    DESPUES SI TIENE LO SUFICIENTE SOLO SE HACE UNA TRANSFERENCIA
                    CASO CONTRARIO PRESTAMOS ENTRE EMPRESAS PARA LO DEMAS LA QUE NO TIENE LO QUE LE FALTE.
                */
                /* Paso 1 */
                arlDat = new ArrayList();
                isEnviaSolicitud=true;
                dblCanFal=0.0;
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a1.co_bodOrg, a1.co_bodDes,a2.co_reg,a2.co_itm,a2.nd_can \n";
                strSQL+=" FROM tbm_cabSolTraInv as a1 \n";
                strSQL+=" INNER JOIN tbm_detSolTraInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc  \n";
                strSQL+="                                        AND a1.co_doc=a2.co_doc)\n";
                strSQL+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_tipDoc="+intCodTipDoc+" AND a1.co_doc="+intCodDoc+"\n";
                rstLoc=stmLoc.executeQuery(strSQL);
                while(rstLoc.next()){
                    if(obtenerEmpresaItemBodega(rstLoc.getInt("co_bodDes"),rstLoc.getInt("co_itm"),rstLoc.getInt("co_bodOrg"))){
                        if(obtenerCantidadNecesaria(conLoc,rstLoc.getDouble("nd_can"), intCodItmSolTra, rstLoc.getInt("co_bodOrg"))){
                            arlReg = new ArrayList();
                            arlReg.add(INT_ARL_COD_EMP, intCodEmpSolTra);
                            arlReg.add(INT_ARL_COD_LOC, intCodLocSolTra);
                            arlReg.add(INT_ARL_COD_TIP_DOC, intCodTipDoc);
                            arlReg.add(INT_ARL_COD_BOD_GRP, rstLoc.getInt("co_bodOrg"));// BODEGA DE ORIGEN
                            arlReg.add(INT_ARL_COD_ITM, intCodItmSolTra);  
                            arlReg.add(INT_ARL_COD_ITM_MAE,intCodItmMaeSolTra);   
                            arlReg.add(INT_ARL_COD_BOD, intCodBodOrgSolTra);
                            arlReg.add(INT_ARL_NOM_BOD, strNomBodSolTra);
                            arlReg.add(INT_ARL_CAN_COM, dblCanFal );  // CANTIDAD NECESARIA!!!
                            arlReg.add(INT_ARL_CHK_CLI_RET, false);  // Cliente Retira
                            arlReg.add(INT_ARL_EST_BOD, 'A');  // A U C   Para saber si necesita pedir autorizaciones o debe ser cliente retira
                            arlReg.add(INT_ARL_ING_EGR_FIS_BOD, "S");
                            arlReg.add(INT_ARL_COD_BOD_GRP_ING, /*intCodBodOrgSolTra*/rstLoc.getInt("co_bodOrg")); // BODEGA DE DESTINO
                            arlDat.add(arlReg);
                        }
                    }
                    else{  //BODEGA SIN DUEÑO 
                        double dblCanPorTraDir=rstLoc.getDouble("nd_can");// CANTIDAD NECASARIA
                        /* SOLO POR TRANSFERENCIA,DIRECTAMENTE SEGUN DONDE HAYA MERCADERIA JM*/
                        stmLoc01=conLoc.createStatement();
                        if(blnIsImportacion){
                            strSQL="";
                            strSQL+=" SELECT a.co_emp , a.co_bod, a.co_itm ,   \n";
                            strSQL+=" CASE WHEN var.tx_tipunimed = 'E' THEN  TRUNC(CASE WHEN a.nd_canDis IS NULL THEN 0 ELSE a.nd_canDis END) ELSE CASE WHEN a.nd_canDis IS NULL THEN 0 ELSE a.nd_canDis END END AS nd_canDis \n";
                            strSQL+=" FROM tbm_invbod as a   \n";
                            strSQL+=" INNER JOIN tbr_bodEmpBodGrp as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod) \n";
                            strSQL+=" INNER JOIN tbm_inv AS inv ON(inv.co_emp=a.co_emp AND inv.co_itm=a.co_itm ) \n";
                            strSQL+=" INNER JOIN tbm_equInv AS invGru ON (inv.co_emp=invGru.co_emp AND inv.co_itm=invGru.co_itm)  \n";
                            strSQL+=" LEFT JOIN tbm_var AS var ON(var.co_reg=inv.co_uni )  \n";
                            strSQL+=" WHERE invGru.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+rstLoc.getInt("co_emp")+" AND co_itm="+rstLoc.getInt("co_itm")+")   \n";
                            strSQL+="        AND (a1.co_empGrp="+rstLoc.getInt("co_emp")+" AND a1.co_bodGrp="+rstLoc.getInt("co_bodOrg")+")  \n";
                            strSQL+="        AND a.co_emp = ( \n";
                            strSQL+="                       SELECT a1.co_emp  \n";
                            strSQL+="                       FROM tbm_cabMovInv as a1  \n";
                            strSQL+="                       INNER JOIN tbr_cabSolTraInvCabMovInv AS a2 ON (a1.co_emp=a2.co_empRelCabMovInv and a1.co_loc=a2.co_locRelCabMovInv AND  \n";
                            strSQL+="                                   a1.co_tipDoc=a2.co_tipDocRelCabMovInv AND a1.co_doc=a2.co_docRelCabMovInv) \n";
                            strSQL+="                       WHERE a2.co_EmpRelCabSolTraInv="+intCodEmp+" AND a2.co_locRelCabSolTraInv="+intCodLoc+" AND a2.co_tipDocRelCabSolTraInv="+intCodTipDoc+" AND  a2.co_docRelCabSolTraInv="+intCodDoc+" \n";
                            strSQL+="           ) \n";
                            strSQL+=" ORDER BY nd_canDis DESC \n";
                            strSQL+=" \n";
                        }
                        else{
                            strSQL="";
                            strSQL+=" SELECT a.co_emp , a.co_bod, a.co_itm ,  \n";
                            strSQL+="        CASE WHEN var.tx_tipunimed = 'E' THEN  TRUNC(CASE WHEN a.nd_canDis IS NULL THEN 0 ELSE a.nd_canDis END) ELSE CASE WHEN a.nd_canDis IS NULL THEN 0 ELSE a.nd_canDis END END AS nd_canDis \n";
                            strSQL+=" FROM tbm_invbod as a   \n";
                            strSQL+=" INNER JOIN tbr_bodEmpBodGrp as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod) \n";
                            strSQL+=" INNER JOIN tbm_inv AS inv ON(inv.co_emp=a.co_emp AND inv.co_itm=a.co_itm ) \n";
                            strSQL+=" INNER JOIN tbm_equInv AS invGru ON (inv.co_emp=invGru.co_emp AND inv.co_itm=invGru.co_itm) \n";
                            strSQL+=" LEFT JOIN tbm_var AS var ON(var.co_reg=inv.co_uni ) \n";
                            strSQL+=" WHERE invGru.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+rstLoc.getInt("co_emp")+" AND co_itm="+rstLoc.getInt("co_itm")+")   \n";
                            strSQL+="        AND (a1.co_empGrp="+rstLoc.getInt("co_emp")+" AND a1.co_bodGrp="+rstLoc.getInt("co_bodOrg")+")  \n";
                            strSQL+=" ORDER BY nd_canDis DESC  \n";
                        }
                        System.out.println("Transferencias Nuevo: " + strSQL);
                        rstLoc01= stmLoc01.executeQuery(strSQL);
                        while(rstLoc01.next()){
                            /* Cantidad disponible por empresa, puede tomar de 1 empresa misma bodega o puede tomar de todas las empresas misma bodega */
                            if(rstLoc01.getDouble("nd_canDis")>=dblCanPorTraDir){
                                dblCanFal=dblCanPorTraDir;
                                dblCanPorTraDir=0;
                            }else{
                                if(dblCanPorTraDir>0){
                                    dblCanFal=rstLoc01.getDouble("nd_canDis");
                                    dblCanPorTraDir=dblCanPorTraDir-dblCanFal;
                                }
                            }
                            if(blnIsDueBod){
                                if(dblCanFal > 0 ){////PENDIENTES!!!!
                                    if(obtenerItemBodega(rstLoc01.getInt("co_emp"),rstLoc.getInt("co_bodDes"),rstLoc01.getInt("co_itm"),rstLoc.getInt("co_bodOrg"))){
                                        arlReg = new ArrayList();
                                        arlReg.add(INT_ARL_COD_EMP, rstLoc01.getInt("co_emp"));
                                        arlReg.add(INT_ARL_COD_LOC, intCodLocSolTra);
                                        arlReg.add(INT_ARL_COD_TIP_DOC, intCodTipDoc);
                                        arlReg.add(INT_ARL_COD_BOD_GRP, rstLoc.getInt("co_bodOrg"));// BODEGA DE ORIGEN
                                        arlReg.add(INT_ARL_COD_ITM, intCodItmSolTra);  
                                        arlReg.add(INT_ARL_COD_ITM_MAE,intCodItmMaeSolTra);   
                                        arlReg.add(INT_ARL_COD_BOD, intCodBodOrgSolTra);
                                        arlReg.add(INT_ARL_NOM_BOD, strNomBodSolTra);
                                        arlReg.add(INT_ARL_CAN_COM, dblCanFal );  // CANTIDAD NECESARIA!!!
                                        arlReg.add(INT_ARL_CHK_CLI_RET, false);  // Cliente Retira
                                        arlReg.add(INT_ARL_EST_BOD, 'A');  // A U C   Para saber si necesita pedir autorizaciones o debe ser cliente retira
                                        arlReg.add(INT_ARL_ING_EGR_FIS_BOD, "S");
                                        arlReg.add(INT_ARL_COD_BOD_GRP_ING,/* intCodBodOrgSolTra*/ rstLoc.getInt("co_bodOrg")); // BODEGA DE DESTINO
                                        arlDat.add(arlReg);
                                    }
                                    dblCanFal=0.00;
                                }
                            }
                            else{
                                if(dblCanFal > 0 ){////PENDIENTES!!!!
                                    if(obtenerItemBodega(rstLoc01.getInt("co_emp"),rstLoc.getInt("co_bodDes"),rstLoc01.getInt("co_itm"),rstLoc.getInt("co_bodOrg"))){
                                        arlReg = new ArrayList();
                                        arlReg.add(INT_ARL_COD_EMP, rstLoc01.getInt("co_emp"));
                                        arlReg.add(INT_ARL_COD_LOC, intCodLocSolTra);
                                        arlReg.add(INT_ARL_COD_TIP_DOC, intCodTipDoc);
                                        arlReg.add(INT_ARL_COD_BOD_GRP, rstLoc.getInt("co_bodOrg"));// BODEGA DE ORIGEN
                                        arlReg.add(INT_ARL_COD_ITM, intCodItmSolTra);  
                                        arlReg.add(INT_ARL_COD_ITM_MAE,intCodItmMaeSolTra);   
                                        arlReg.add(INT_ARL_COD_BOD, intCodBodOrgSolTra);
                                        arlReg.add(INT_ARL_NOM_BOD, strNomBodSolTra);
                                        arlReg.add(INT_ARL_CAN_COM, dblCanFal );  // CANTIDAD NECESARIA!!!
                                        arlReg.add(INT_ARL_CHK_CLI_RET, false);  // Cliente Retira
                                        arlReg.add(INT_ARL_EST_BOD, 'A');  // A U C   Para saber si necesita pedir autorizaciones o debe ser cliente retira
                                        arlReg.add(INT_ARL_ING_EGR_FIS_BOD, "S");
                                        arlReg.add(INT_ARL_COD_BOD_GRP_ING,/* intCodBodOrgSolTra*/ rstLoc.getInt("co_bodDes")); // BODEGA DE DESTINO
                                        arlDat.add(arlReg);
                                    }
                                    dblCanFal=0.00;
                                }
                            }
                        }
                        stmLoc01.close();
                        stmLoc01=null;
                        rstLoc01.close();
                        rstLoc01=null;
                    }
                    if(isEnviaSolicitud){
                        /* CONTENEDOR PARA ENVIAR A MOVIENTO DE INVENTARIO */
                        arlDatSolTra= new ArrayList();
                        arlRegSolTra = new ArrayList();
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_EMP,intCodEmp);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_LOC,intCodLoc);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_TIP_DOC,intCodTipDoc);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_DOC,intCodDoc);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_BOD_EGR,rstLoc.getInt("co_bodOrg")); // ORIGEN = EGRESO
                        arlDatSolTra.add(arlRegSolTra);
                        isEnviaSolicitud=false;
                        /* ENVIAR EL CONTENEDOR */
                    }
                }  /* FIN SOLICITUK!!! xD  */ 


                /*================================== Paso 2 ===============================================*/
                if(blnIsDueBod){
                    //INT_ARL_COD_BOD_GRP// BODEGA DE ORIGEN // EGRESO
                    //INT_ARL_COD_BOD_GRP_ING//BODEGA DE DESTINO // INGRESO
                    if(llamaObjetoCfgBod(conLoc,objUti.getIntValueAt(arlDat, 0, INT_ARL_COD_BOD_GRP),objUti.getIntValueAt(arlDat, 0, INT_ARL_COD_BOD_GRP_ING))){
                        if(!objReaMovInv.inicia(conLoc, arlDat,datFecAux ,arlDatSolTra,objCfgBod)){
                            blnRes=false;  
                        }
                        else{
                            /*  Si se logra generar los prestamos entre empresas o transferencias para poner la mercaderia 
                                en una sola bodega. El siguiente paso seria hacer la transferencia  */
                            System.out.println("PARTE DOS ZAFCOM92 ");
                            arlDat.clear();
                            rstLoc.beforeFirst();
                            while(rstLoc.next()){
                                if(obtenerEmpresaItemBodega(rstLoc.getInt("co_bodDes"),rstLoc.getInt("co_itm"),rstLoc.getInt("co_bodOrg"))){
                                    arlReg = new ArrayList();
                                    arlReg.add(INT_ARL_COD_EMP, intCodEmpSolTra);
                                    arlReg.add(INT_ARL_COD_LOC, intCodLocSolTra);
                                    arlReg.add(INT_ARL_COD_TIP_DOC, intCodTipDoc);
                                    arlReg.add(INT_ARL_COD_BOD_GRP, rstLoc.getInt("co_bodOrg"));// BODEGA DE ORIGEN GRUPO SIEMPRE
                                    arlReg.add(INT_ARL_COD_ITM, intCodItmSolTra);  // 1 
                                    arlReg.add(INT_ARL_COD_ITM_MAE,intCodItmMaeSolTra);  //14 
                                    arlReg.add(INT_ARL_COD_BOD, intCodBodOrgSolTra);
                                    arlReg.add(INT_ARL_NOM_BOD, strNomBodSolTra);
                                    arlReg.add(INT_ARL_CAN_COM, rstLoc.getDouble("nd_can") );  // CAntidad para la necesidad
                                    arlReg.add(INT_ARL_CHK_CLI_RET, false);  // Cliente Retira
                                    arlReg.add(INT_ARL_EST_BOD, 'A');  // A U C   Para saber si necesita pedir autorizaciones o debe ser cliente retira
                                    arlReg.add(INT_ARL_ING_EGR_FIS_BOD, "S");
                                    arlReg.add(INT_ARL_COD_BOD_GRP_ING, rstLoc.getInt("co_bodDes")); // BODEGA DE DESTINO
                                    arlDat.add(arlReg);
                                }
                            }
                            if(llamaObjetoCfgBod(conLoc,objUti.getIntValueAt(arlDat, 0, INT_ARL_COD_BOD_GRP),objUti.getIntValueAt(arlDat, 0, INT_ARL_COD_BOD_GRP_ING))){
                                if(!objReaMovInv.iniciaTransferencia(conLoc, arlDat,datFecAux ,arlDatSolTra,objCfgBod)){
                                    blnRes=false;  
                                }
                            }else{blnRes=false;}                         
                        }
                    }else{blnRes=false;}
                }
                else{
                    // SI NO TIENE DUEÑO SERIAN SOLO TRANSFERENCIAS PERO SE DEBE ENVIAR POR EMPRESA 
                    if(ordenarLasTransferencias()){
                        if(blnTraTuv){
                            if(llamaObjetoCfgBod(conLoc,objUti.getIntValueAt(arlDat, 0, INT_ARL_COD_BOD_GRP),objUti.getIntValueAt(arlDat, 0, INT_ARL_COD_BOD_GRP_ING))){
                                if(!objReaMovInv.iniciaTransferencia(conLoc, arlDatTraTuv,datFecAux,arlDatSolTra,objCfgBod )){
                                    blnRes=false;  
                                }
                            }else{blnRes=false;}    
                        }
                        if(blnTraCas){
                            if(llamaObjetoCfgBod(conLoc,objUti.getIntValueAt(arlDat, 0, INT_ARL_COD_BOD_GRP),objUti.getIntValueAt(arlDat, 0, INT_ARL_COD_BOD_GRP_ING))){
                                if(!objReaMovInv.iniciaTransferencia(conLoc, arlDatTraCas,datFecAux,arlDatSolTra,objCfgBod )){
                                    blnRes=false;  
                                }
                            }else{blnRes=false;}    
                        }
                        if(blnTraDim){
                            if(llamaObjetoCfgBod(conLoc,objUti.getIntValueAt(arlDat, 0, INT_ARL_COD_BOD_GRP),objUti.getIntValueAt(arlDat, 0, INT_ARL_COD_BOD_GRP_ING))){
                                if(!objReaMovInv.iniciaTransferencia(conLoc, arlDatTraDim,datFecAux,arlDatSolTra,objCfgBod )){
                                    blnRes=false;  
                                }
                            }else{blnRes=false;}    
                        }
                    }
                }
                /* ========================= FIN DE PASO DOS ========================= */
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
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
}
