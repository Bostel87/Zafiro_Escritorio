/*
 * ZafMae20.java
 *
 * Created on 19 Agosto , 2013, 8:44 PM
 */

package Ventas.ZafVen42;
import Librerias.ZafCfgSer.ZafCfgSer;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafGenDocIngEgrInvRes.ZafGenDocIngEgrInvRes;
import Librerias.ZafGenFacAut.ZafModDatGenFac;
import Librerias.ZafGetDat.ZafDatBod;
import Librerias.ZafGetDat.ZafDatItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafResInv.ZafCanResInv;
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
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Ventas.ZafVen01.ZafVen01;

import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.awt.Frame;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author  José Marín 
 */
public class ZafVen42 extends javax.swing.JInternalFrame {
    
    private ZafParSis objParSis;
    private ZafTblCelEdiTxt objTblCelEdiTxt;//Editor: JTextField en celda.
    private ZafDtePckEdi objTblDtaPckEdi;
    private ZafUtil objUti;//Objeto del tipo de la clase ZafUtil, el cual me va a permitir 
    private ZafTblMod objTblMod;
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelRenBut objTblCelRenBut;//Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButDG1;
    private ZafTblOrd objTblOrd;
    private ZafTblBus objTblBus;
    private ZafTblCelEdiButVco objTblCelEdiButVcoTipSolRes;//Editor: JButton en celda (Bodega origen).
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg2;//Editor: JButton en celda (Bodega origen).
    
    private ZafTblCelEdiButDlg objTblCelEdiButDlg;              //Editor: JButton en celda.

    
    private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGenDG1;
    private ZafTblFilCab objTblFilCab;
    private boolean blnHayCam;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private boolean blnCon;//true: Continua la ejecución del hilo. // Continuidad del hilo
    private ZafThreadGUI objThrGUI;
    private ZafThrGua objThrGua;
   
   
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenLbl objTblCelRenLblCod;                 //Render: Presentar JLabel en JTable (Números).
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
    private String strMarCod;
    private String strMarNom;
    private ZafTblCelRenChk objTblCelRenChk, objTblCelRenChk2;
    private ZafTblCelEdiChk objTblCelEdiChkPre; 
    
    public String strVer="v 0.20";
    private ZafSelFec objSelFec;
    
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    
    private String strSQL, strSql;
    private Vector vecDat, vecReg, vecCab, vecAux;
   
    private Connection con;
    private Statement stm;
    private ResultSet rst;
   
    private ZafTblMod objTblModLoc;
    private ZafTblCelRenBut  objTblCelRenButConOcp;  
    
    //Constantes: Columnas del JTable:
    static final int INT_TBL_LOC_LIN=0;                         //Línea.
    static final int INT_TBL_LOC_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_LOC_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_LOC_NOM_EMP=3;                     //Nombre de la empresa.
    static final int INT_TBL_LOC_COD_LOC=4;                     //Código del local.
    static final int INT_TBL_LOC_NOM_LOC=5;                     //Nombre del local.
    
    				

    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_COD_EMP=1; // Código de la Empresa
    private final int INT_TBL_DAT_NOM_EMP=2; // Nombre de la empresa
    private final int INT_TBL_DAT_COD_LOC=3; // Código del local	
    private final int INT_TBL_DAT_NOM_LOC=4; // Nombre del Local 
    private final int INT_TBL_DAT_COD_COT=5; // Código del documento / Cotizacion
    private final int INT_TBL_DAT_FEC_COT=6; // Fecha del documento
    private final int INT_TBL_DAT_COD_CLI=7; //Código del cliente 
    private final int INT_TBL_DAT_NOM_CLI=8; // Nombre del cliente	
    private final int INT_TBL_DAT_TOT_COT=9;  //Valor del documento 
    private final int INT_TBL_DAT_BTN_COT_VEN=10;  // BOTON COTIZACIONES DE VENTA Muestra la "Cotizaciòn de ventas"   
    private final int INT_TBL_DAT_COD_TIP_SOL=11;  // Codigo del TipoSolicitud de Reserva
    private final int INT_TBL_DAT_BTN_TIP_SOL=12;  // BOTON TipoSolicitud DE Reserva  
    private final int INT_TBL_DAT_TIP_SOL=13;  // Tipo de solicitud de reserva
    private final int INT_TBL_DAT_FEC_SOL=14;  // Fecha de solicitud de reserva
    private final int INT_TBL_DAT_CHK_SOL_RES=15; // JM SE LLENA SOLO PARA SABER CUANDO ESTAN RESERVANDO 
    private final int INT_TBL_DAT_OBS_SOL_RES=16;        // OBSERVACION DE RESERVA 
    private final int INT_TBL_DAT_BTN_OBS_SOL_RES=17;    // BOTON OBS RESERVA Observación de la reserva
    private final int INT_TBL_DAT_CHK_PED_OTR_BOD=18;    // CheckBox: posee pedidos a otras bodegas
    private final int INT_TBL_DAT_BTN_PED_OTR_BOD=19;
    private final int INT_TBL_DAT_CHK_SOL_ENV_PED=20;    // CheckBox: solicitar enviar pedidos 
    private final int INT_TBL_DAT_CHK_PENDIENTE=21;    // CheckBox: Pendiente
    private final int INT_TBL_DAT_CHK_AUTORIZAR=22;    // CheckBox: Autorizar
    private final int INT_TBL_DAT_CHK_DENEGAR=23;    // CheckBox: Denegar
    private final int INT_TBL_DAT_CHK_AUT_ENV_PED=24;    // CheckBox: Autorizar enviar pedido
    private final int INT_TBL_DAT_FEC_SOL_FAC_AUT=25;    // FECHA SOLICITADA PARA AUTO FACTURA Fecha solicitada
    private final int INT_TBL_DAT_OBS_AUT_RES=26;   // OBSERVACION de la autorizacion
    private final int INT_TBL_DAT_BTN_OBS_AUT_RES=27;   // BOTON DE OBSERVACION DE LA AUTORIZACION Observación de la autorización	Muestra la "Observación de la autorización" 
    private final int INT_TBL_DAT_VAL_FAC=28;   // Valor Facturado de la reserva 
    private final int INT_TBL_DAT_BTN_LIS_FAC_VEN=29;   // BOTON Listado de Facturas 
    private final int INT_TBL_DAT_VAL_CAN=30;   // Valor Cancelado
    private final int INT_TBL_DAT_CHK_CANCELAR=31;   //  ????? NO me dicen todavia  ??? 21/Sep/2017 se usara como un check para saber que se cancelara, por ahora solo total cancelacion  JM 
    private final int INT_TBL_DAT_BUT_LIS_CAN=32;   // BOTON Listado de cancelacion
    private final int INT_TBL_DAT_CHK_NOT_PRO_RES=33;    // NO TIENE PROCESO DE RESERVA
    private final int INT_TBL_DAT_CHK_PEN_PRO_RES=34;    // PROCESO PENDIENTE
    private final int INT_TBL_DAT_CHK_COM_PRO_RES=35;    // PROCESO COMPLETO
    private final int INT_TBL_DAT_EST_SOL_RES=36;    // EstadoSolRes
    private final int INT_TBL_DAT_MOM_DES_SOL_RES=37; // Para guardar el momento de despacho de la solicitud de reserva
    private final int INT_TBL_DAT_EST_FAC_PRI_DIA_LAB=38; // Para guardar el momento de despacho de la solicitud de reserva
    private final int INT_TBL_DAT_STR_TIP_RES_INV=39; // Para guardar el momento de despacho de la solicitud de reserva
    
    private ZafPerUsr objPerUsr;                               //Objeto que almacena el perfil del usuario.
    private ZafDocLis objDocLis;
    
    private String strCodCli, strDesLarCli;                     //Contenido del campo al obtener el foco.
    private String strCodVen, strNomVen;                         //Contenido del campo al obtener el foco.
    
    private ZafVenCon vcoCli;                                   //Ventana de consulta "Clientes".
    private ZafVenCon vcoVen;                                   //Ventana de consulta.
    
    private ZafVenCon vcoTipSol;                                   //Ventana de consulta. 
    
    private Librerias.ZafAut.ZafAut objAutPrg;
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    private ZafCorEle objCorEle;
    
     private ArrayList arlDat,arlReg ;
    
    private int intCodTipDocFacEle_ZafVen01_06 = 228;
    private int intCodMnuCotVen = 3965; 
    private int intCodMnuResVenAut = 4146; 
    
    private String strTitCorEle = "Zafiro - Sistema de Reservas", strCorEleErr="sistemas6@tuvalsa.com;jota.marin@live.com";
    
   /* CONSTANTES PARA CONTENEDOR A ENVIAR JoséMario 21/Sep/2015  */
   final int INT_ARL_CON_COD_EMP=0;
   final int INT_ARL_CON_COD_LOC=1;
   final int INT_ARL_CON_COD_TIP_DOC=2;
   final int INT_ARL_CON_COD_BOD_GRP=3;
   final int INT_ARL_CON_COD_ITM=4;
   final int INT_ARL_CON_COD_ITM_MAE=5;
   final int INT_ARL_CON_COD_BOD=6;
   final int INT_ARL_CON_NOM_BOD=7;
   final int INT_ARL_CON_CAN_COM=8;
   final int INT_ARL_CON_CHK_CLI_RET=9;
   final int INT_ARL_CON_EST_BOD=10;
   final int INT_ARL_CON_ING_EGR_FIS_BOD=11;
   final int INT_ARL_CON_COD_BOD_ING_MER=12;
   
    private int intNumDocOC, Glo_intCodSec=0,intCodMotBien=0;
    private String strFecSis, strFecSisBase;
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    public StringBuffer stbDocRelEmp= new StringBuffer();
    private javax.swing.JTextArea txtsql;
    private Librerias.ZafAsiDia.ZafAsiDia objDiario;  
    
    private int intNumDec;
    
    /**
     * Función que permite obtener el nombre del campo que se desea actualizar
     * @param indiceNombreCampo 
     *          <HTML>
     *              <BR>  0: Actualiza en campo "nd_stkAct"
     *              <BR>  1: Actualiza en campo "nd_canPerIng"
     *              <BR>  2: Actualiza en campo "nd_canPerEgr"
     *              <BR>  3: Actualiza en campo "nd_canBodIng"
     *              <BR>  4: Actualiza en campo "nd_canBodEgr"
     *              <BR>  5: Actualiza en campo "nd_canDesIng"
     *              <BR>  6: Actualiza en campo "nd_canDesEgr"
     *              <BR>  7: Actualiza en campo "nd_canTra"
     *              <BR>  8: Actualiza en campo "nd_canRev"
     *              <BR>  9: Actualiza en campo "nd_canRes"
     *              <BR> 10: Actualiza en campo "nd_canDis"
     *          </HTML>
     * @return true: Si se pudo obtener el nombre del campo
     * <BR> false: Caso contrario
     */
    
    final int INT_ARL_STK_INV_STK_ACT=0;  // nd_stkAct
    final int INT_ARL_STK_INV_NOM_CAM_ACT=1;
    final int INT_ARL_STK_INV_NOM_CAM_ACT_2=2;
    final int INT_ARL_STK_INV_CAN_ING_BOD=3;  // nd_canBodIng --> transferencia afectar ingreso 
    final int INT_ARL_STK_INV_CAN_EGR_BOD=4;  // nd_canBodEgr --> transferencia afectar egreso
    final int INT_ARL_STK_INV_CAN_DES_ENT_BOD=5;
    final int INT_ARL_STK_INV_CAN_DES_ENT_CLI=6;
    final int INT_ARL_STK_INV_CAN_TRA=7;
    final int INT_ARL_STK_INV_CAN_REV=8;
    final int INT_ARL_STK_INV_CAN_RES=9;
    final int INT_ARL_STK_INV_CAN_DIS=10;  // nd_canDis
    final int INT_ARL_STK_INV_CAN_RES_VEN=11; // Cantidad en reserva de venta 
      
    private ZafDatItm objDatItm;
    private ZafDatBod objDatBod;
    private GenOD.ZafGenOdPryTra objGenOD;
    private ZafCfgSer objCfgSer;
    private int intCodSer=16;// Impresiones mateo
    
    
    public ZafVen42(ZafParSis obj) {
        try{
            objUti = new ZafUtil();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objCorEle = new ZafCorEle(objParSis); 
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            objTblCelEdiButGenDG1=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            objTblCelRenButDG1=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            objAutPrg = new Librerias.ZafAut.ZafAut(this, objParSis);
            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);  
            objDiario = new Librerias.ZafAsiDia.ZafAsiDia(objParSis);
            intNumDec = objParSis.getDecimalesMostrar();
            objDatItm = new Librerias.ZafGetDat.ZafDatItm(objParSis, this);
            objDatBod = new Librerias.ZafGetDat.ZafDatBod(objParSis, this);
            objGenOD = new GenOD.ZafGenOdPryTra();
            objCfgSer = new ZafCfgSer(objParSis);
             
            initComponents();
            configurarFrm();
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }

    }
        
    /**
     * 
     * @param parent
     * @param obj 
     */
    
    public ZafVen42( Frame parent, ZafParSis obj) {
        try{
            objUti = new ZafUtil();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objCorEle = new ZafCorEle(objParSis);
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            objTblCelEdiButGenDG1=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            objTblCelRenButDG1=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
             
            objAutPrg = new Librerias.ZafAut.ZafAut(this, objParSis);
            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);  
            objDiario = new Librerias.ZafAsiDia.ZafAsiDia(objParSis);
            intNumDec = objParSis.getDecimalesMostrar();
            objCfgSer = new ZafCfgSer(objParSis);
            objGenOD = new GenOD.ZafGenOdPryTra();
            initComponents();
            configurarFrm();
            
            blnIsCotVen=true;
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
        spnFil = new javax.swing.JScrollPane();
        panFil = new javax.swing.JPanel();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblVen1 = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        panLoc = new javax.swing.JPanel();
        spnLoc = new javax.swing.JScrollPane();
        tblLoc = new javax.swing.JTable();
        chkMosLocIna = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        txtCodTipSol = new javax.swing.JTextField();
        txtNomTipSol = new javax.swing.JTextField();
        btnTipSol = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtNumCot = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        chkEstSolNoTiene = new javax.swing.JCheckBox();
        chkAutSolPendiente = new javax.swing.JCheckBox();
        chkFacSolPendiente = new javax.swing.JCheckBox();
        chkEstSolPendiente = new javax.swing.JCheckBox();
        chkEstSolCompleta = new javax.swing.JCheckBox();
        chkAutSolAutorizada = new javax.swing.JCheckBox();
        chkFacSolParcial = new javax.swing.JCheckBox();
        chkAutSolDenegada = new javax.swing.JCheckBox();
        chkFacSolTotal = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        chkCanSolNoTiene = new javax.swing.JCheckBox();
        chkCanSolParcial = new javax.swing.JCheckBox();
        chkCanSolTotal = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnRpt = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable()  {    protected javax.swing.table.JTableHeader createDefaultTableHeader()    {       return new ZafTblHeaGrp(columnModel);    } };
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
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

        panFil.setPreferredSize(new java.awt.Dimension(0, 480));
        panFil.setLayout(null);

        lblCli.setText("Cliente:");
        lblCli.setToolTipText("Cliente");
        panFil.add(lblCli);
        lblCli.setBounds(10, 270, 120, 20);

        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        panFil.add(txtCodCli);
        txtCodCli.setBounds(153, 270, 56, 20);

        txtDesLarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCliActionPerformed(evt);
            }
        });
        txtDesLarCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusLost(evt);
            }
        });
        panFil.add(txtDesLarCli);
        txtDesLarCli.setBounds(210, 270, 430, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(640, 270, 20, 20);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todas las cotizaciones");
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
        optTod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                optTodFocusGained(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 200, 170, 23);

        bgrFil.add(optFil);
        optFil.setText("Solo las cotizaciones que cumplen el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(10, 220, 430, 23);

        lblVen1.setText("Vendedor/Comprador:");
        lblVen1.setToolTipText("Vendedor/Comprador");
        panFil.add(lblVen1);
        lblVen1.setBounds(10, 250, 150, 20);

        txtCodVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVenActionPerformed(evt);
            }
        });
        txtCodVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVenFocusLost(evt);
            }
        });
        panFil.add(txtCodVen);
        txtCodVen.setBounds(153, 250, 56, 20);

        txtNomVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVenActionPerformed(evt);
            }
        });
        txtNomVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVenFocusLost(evt);
            }
        });
        panFil.add(txtNomVen);
        txtNomVen.setBounds(210, 250, 430, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFil.add(butVen);
        butVen.setBounds(640, 250, 20, 20);

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de locales"));
        panLoc.setAutoscrolls(true);
        panLoc.setLayout(new java.awt.BorderLayout());

        tblLoc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnLoc.setViewportView(tblLoc);

        panLoc.add(spnLoc, java.awt.BorderLayout.CENTER);

        chkMosLocIna.setText("Mostrar locales inactivos");
        chkMosLocIna.setPreferredSize(new java.awt.Dimension(145, 14));
        chkMosLocIna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosLocInaActionPerformed(evt);
            }
        });
        panLoc.add(chkMosLocIna, java.awt.BorderLayout.SOUTH);

        panFil.add(panLoc);
        panLoc.setBounds(10, 80, 640, 120);

        jLabel1.setText("Tipo de solicitud:");
        panFil.add(jLabel1);
        jLabel1.setBounds(10, 295, 110, 14);

        txtCodTipSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTipSolActionPerformed(evt);
            }
        });
        txtCodTipSol.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTipSolFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTipSolFocusLost(evt);
            }
        });
        panFil.add(txtCodTipSol);
        txtCodTipSol.setBounds(153, 290, 56, 20);

        txtNomTipSol.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNomTipSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTipSolActionPerformed(evt);
            }
        });
        txtNomTipSol.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTipSolFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTipSolFocusLost(evt);
            }
        });
        panFil.add(txtNomTipSol);
        txtNomTipSol.setBounds(210, 290, 430, 20);

        btnTipSol.setText("jButton1");
        btnTipSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTipSolActionPerformed(evt);
            }
        });
        panFil.add(btnTipSol);
        btnTipSol.setBounds(640, 290, 20, 20);

        jLabel2.setText("Num. Cotizacion:");
        panFil.add(jLabel2);
        jLabel2.setBounds(10, 315, 100, 14);

        txtNumCot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumCotFocusGained(evt);
            }
        });
        panFil.add(txtNumCot);
        txtNumCot.setBounds(153, 310, 80, 20);

        jLabel3.setText("Estado de la solicitud:");
        panFil.add(jLabel3);
        jLabel3.setBounds(10, 340, 190, 14);

        jLabel4.setText("Autorización de la solicitud:");
        panFil.add(jLabel4);
        jLabel4.setBounds(10, 360, 190, 14);

        jLabel5.setText("Facturación de la solicitud:");
        panFil.add(jLabel5);
        jLabel5.setBounds(10, 380, 190, 14);

        chkEstSolNoTiene.setText("No tiene");
        chkEstSolNoTiene.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEstSolNoTieneActionPerformed(evt);
            }
        });
        panFil.add(chkEstSolNoTiene);
        chkEstSolNoTiene.setBounds(210, 340, 100, 23);

        chkAutSolPendiente.setText("Pendiente");
        chkAutSolPendiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAutSolPendienteActionPerformed(evt);
            }
        });
        panFil.add(chkAutSolPendiente);
        chkAutSolPendiente.setBounds(210, 360, 100, 23);

        chkFacSolPendiente.setText("Pendiente");
        chkFacSolPendiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFacSolPendienteActionPerformed(evt);
            }
        });
        panFil.add(chkFacSolPendiente);
        chkFacSolPendiente.setBounds(210, 380, 100, 23);

        chkEstSolPendiente.setText("Pendiente");
        chkEstSolPendiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEstSolPendienteActionPerformed(evt);
            }
        });
        panFil.add(chkEstSolPendiente);
        chkEstSolPendiente.setBounds(330, 340, 100, 23);

        chkEstSolCompleta.setText("Completa");
        chkEstSolCompleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEstSolCompletaActionPerformed(evt);
            }
        });
        panFil.add(chkEstSolCompleta);
        chkEstSolCompleta.setBounds(450, 340, 140, 23);

        chkAutSolAutorizada.setText("Autorizada");
        chkAutSolAutorizada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAutSolAutorizadaActionPerformed(evt);
            }
        });
        panFil.add(chkAutSolAutorizada);
        chkAutSolAutorizada.setBounds(330, 360, 100, 23);

        chkFacSolParcial.setText("Parcial");
        chkFacSolParcial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFacSolParcialActionPerformed(evt);
            }
        });
        panFil.add(chkFacSolParcial);
        chkFacSolParcial.setBounds(330, 380, 100, 23);

        chkAutSolDenegada.setText("Denegada");
        chkAutSolDenegada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAutSolDenegadaActionPerformed(evt);
            }
        });
        panFil.add(chkAutSolDenegada);
        chkAutSolDenegada.setBounds(450, 360, 130, 23);

        chkFacSolTotal.setText("Total");
        chkFacSolTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFacSolTotalActionPerformed(evt);
            }
        });
        panFil.add(chkFacSolTotal);
        chkFacSolTotal.setBounds(450, 380, 110, 23);

        jLabel6.setText("Cancelación de la solicitud:");
        panFil.add(jLabel6);
        jLabel6.setBounds(10, 400, 190, 14);

        chkCanSolNoTiene.setText("No tiene");
        chkCanSolNoTiene.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCanSolNoTieneActionPerformed(evt);
            }
        });
        panFil.add(chkCanSolNoTiene);
        chkCanSolNoTiene.setBounds(210, 400, 100, 23);

        chkCanSolParcial.setText("Parcial");
        chkCanSolParcial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCanSolParcialActionPerformed(evt);
            }
        });
        panFil.add(chkCanSolParcial);
        chkCanSolParcial.setBounds(330, 400, 100, 23);

        chkCanSolTotal.setText("Total");
        chkCanSolTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCanSolTotalActionPerformed(evt);
            }
        });
        panFil.add(chkCanSolTotal);
        chkCanSolTotal.setBounds(450, 400, 100, 23);

        spnFil.setViewportView(panFil);

        tabFrm.addTab("Filtro", spnFil);

        panRpt.setPreferredSize(new java.awt.Dimension(452, 402));
        panRpt.setLayout(new java.awt.BorderLayout());

        spnRpt.setPreferredSize(new java.awt.Dimension(452, 402));
        spnRpt.setRequestFocusEnabled(false);

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
        spnRpt.setViewportView(tblDat);

        panRpt.add(spnRpt, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(452, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTot.setViewportView(tblTot);

        panRpt.add(spnTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);
        tabFrm.getAccessibleContext().setAccessibleName("Reporte");

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

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleName("Reporte");

        setBounds(0, 0, 700, 450);
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
                objThrGUI=new ZafVen42.ZafThreadGUI();
                objThrGUI.start();    
            }            
        }
        else{
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    
    
    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if(isCamVal()){
            if (objTblMod.isDataModelChanged()){
                if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0){
                    if(objThrGua==null){
                        objThrGua=new ZafVen42.ZafThrGua();
                        objThrGua.start();
                    }
                }
            }
            else {
                mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
            }
        }
        

    }//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
        optFil.setSelected(true); 
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)){
            if (txtCodCli.getText().equals("")){
                txtCodCli.setText("");
                txtDesLarCli.setText("");
            }
            else{
                mostrarVenConCli(1);
            }
        }
        else
        txtCodCli.setText(strCodCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
 
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtDesLarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCliActionPerformed
        txtDesLarCli.transferFocus();
    }//GEN-LAST:event_txtDesLarCliActionPerformed

    private void txtDesLarCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusGained
        strDesLarCli=txtDesLarCli.getText();
        txtDesLarCli.selectAll();
        optFil.setSelected(true); 
    }//GEN-LAST:event_txtDesLarCliFocusGained

    private void txtDesLarCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli))
        {
            if (txtDesLarCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtDesLarCli.setText("");
            }
            else
            {
                mostrarVenConCli(2);
            }
        }
        else
        txtDesLarCli.setText(strDesLarCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        
    }//GEN-LAST:event_txtDesLarCliFocusLost

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        optFil.setSelected(true); 
    }//GEN-LAST:event_butCliActionPerformed

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        txtCodVen.transferFocus();
    }//GEN-LAST:event_txtCodVenActionPerformed

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        strCodVen=txtCodVen.getText();
        txtCodVen.selectAll();
        optFil.setSelected(true);
    }//GEN-LAST:event_txtCodVenFocusGained

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodVen.getText().equalsIgnoreCase(strCodVen))
        {
            if (txtCodVen.getText().equals(""))
            {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }
            else
            {
                mostrarVenConVen(1);
            }
        }
        else
        txtCodVen.setText(strCodVen);
        //Seleccionar el JRadioButton de filtro si es necesario.
        
    }//GEN-LAST:event_txtCodVenFocusLost

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        txtNomVen.transferFocus();
    }//GEN-LAST:event_txtNomVenActionPerformed

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        strNomVen=txtNomVen.getText();
        txtNomVen.selectAll();
        optFil.setSelected(true);
    }//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomVen.getText().equalsIgnoreCase(strNomVen))
        {
            if (txtNomVen.getText().equals(""))
            {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }
            else
            {
                mostrarVenConVen(2);
            }
        }
        else
        txtNomVen.setText(strNomVen);
        //Seleccionar el JRadioButton de filtro si es necesario.
 
    }//GEN-LAST:event_txtNomVenFocusLost

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        mostrarVenConVen(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        optFil.setSelected(true); 
    }//GEN-LAST:event_butVenActionPerformed

    private void chkMosLocInaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosLocInaActionPerformed
        cargarLoc();
    }//GEN-LAST:event_chkMosLocInaActionPerformed

    private void btnTipSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTipSolActionPerformed
        // TODO add your handling code here:
         mostrarVenConTipSol(0);
         optFil.setSelected(true); 
    }//GEN-LAST:event_btnTipSolActionPerformed

    private void txtCodTipSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTipSolActionPerformed
        // TODO add your handling code here:
        txtCodTipSol.transferFocus();
    }//GEN-LAST:event_txtCodTipSolActionPerformed

    private void txtCodTipSolFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTipSolFocusGained
        // TODO add your handling code here:
        strCodTipSol=txtCodTipSol.getText();
        txtCodTipSol.selectAll();
        optFil.setSelected(true); 
    }//GEN-LAST:event_txtCodTipSolFocusGained

    private void txtCodTipSolFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTipSolFocusLost
        // TODO add your handling code here:
         if (!txtCodTipSol.getText().equalsIgnoreCase(strCodTipSol))
        {
            if (txtCodTipSol.getText().equals(""))
            {
                txtCodTipSol.setText("");
                txtNomTipSol.setText("");
            }
            else
            {
                mostrarVenConTipSol(1);
            }
        }
        else
        txtCodTipSol.setText(strCodTipSol);
    }//GEN-LAST:event_txtCodTipSolFocusLost

    private void txtNomTipSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTipSolActionPerformed
        // TODO add your handling code here:
        txtNomTipSol.transferFocus();
    }//GEN-LAST:event_txtNomTipSolActionPerformed

    private void txtNomTipSolFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTipSolFocusGained
        // TODO add your handling code here:
        strNomTipSol=txtNomTipSol.getText();
        txtNomTipSol.selectAll();
        optFil.setSelected(true); 
    }//GEN-LAST:event_txtNomTipSolFocusGained

    private void txtNomTipSolFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTipSolFocusLost
        // TODO add your handling code here:
        if (!txtNomTipSol.getText().equalsIgnoreCase(strNomTipSol))
        {
            if (txtNomTipSol.getText().equals(""))
            {
                txtCodVen.setText("");
                txtNomTipSol.setText("");
            }
            else
            {
                mostrarVenConVen(2);
            }
        }
        else
        txtNomTipSol.setText(strNomTipSol);
    }//GEN-LAST:event_txtNomTipSolFocusLost

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:
        if (optTod.isSelected()){
            limpiarFiltros();
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void limpiarFiltros(){
        txtCodVen.setText("");
        txtNomVen.setText("");  
        txtCodCli.setText("");
        txtDesLarCli.setText("");
        txtCodTipSol.setText("");
        txtNomTipSol.setText("");
        txtNumCot.setText("");

        chkEstSolNoTiene.setSelected(false);
        chkEstSolPendiente.setSelected(false);
        chkEstSolCompleta.setSelected(false);

        chkAutSolPendiente.setSelected(false);
        chkAutSolAutorizada.setSelected(false);
        chkAutSolDenegada.setSelected(false);

        chkFacSolPendiente.setSelected(false);
        chkFacSolParcial.setSelected(false);
        chkFacSolTotal.setSelected(false);

        chkCanSolNoTiene.setSelected(false);
        chkCanSolParcial.setSelected(false);
        chkCanSolTotal.setSelected(false);
    }
    
    private void optTodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_optTodFocusGained
        // TODO add your handling code here:
        if (optTod.isSelected()){
            limpiarFiltros();
        }
    }//GEN-LAST:event_optTodFocusGained

    private void txtNumCotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumCotFocusGained
        // TODO add your handling code here:
        optFil.setSelected(true); 
    }//GEN-LAST:event_txtNumCotFocusGained

    private void chkEstSolNoTieneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEstSolNoTieneActionPerformed
        // TODO add your handling code here:
        optFil.setSelected(true); 
    }//GEN-LAST:event_chkEstSolNoTieneActionPerformed

    private void chkEstSolPendienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEstSolPendienteActionPerformed
        // TODO add your handling code here:
        optFil.setSelected(true); 
    }//GEN-LAST:event_chkEstSolPendienteActionPerformed

    private void chkEstSolCompletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEstSolCompletaActionPerformed
        // TODO add your handling code here:
        optFil.setSelected(true); 
    }//GEN-LAST:event_chkEstSolCompletaActionPerformed

    private void chkAutSolPendienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAutSolPendienteActionPerformed
        // TODO add your handling code here:
        optFil.setSelected(true); 
    }//GEN-LAST:event_chkAutSolPendienteActionPerformed

    private void chkAutSolAutorizadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAutSolAutorizadaActionPerformed
        // TODO add your handling code here:
        optFil.setSelected(true); 
    }//GEN-LAST:event_chkAutSolAutorizadaActionPerformed

    private void chkAutSolDenegadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAutSolDenegadaActionPerformed
        // TODO add your handling code here:
        optFil.setSelected(true); 
    }//GEN-LAST:event_chkAutSolDenegadaActionPerformed

    private void chkFacSolPendienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFacSolPendienteActionPerformed
        // TODO add your handling code here:
        optFil.setSelected(true); 
    }//GEN-LAST:event_chkFacSolPendienteActionPerformed

    private void chkFacSolParcialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFacSolParcialActionPerformed
        // TODO add your handling code here:
        optFil.setSelected(true); 
    }//GEN-LAST:event_chkFacSolParcialActionPerformed

    private void chkFacSolTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFacSolTotalActionPerformed
        // TODO add your handling code here:
        optFil.setSelected(true); 
    }//GEN-LAST:event_chkFacSolTotalActionPerformed

    private void chkCanSolNoTieneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCanSolNoTieneActionPerformed
        // TODO add your handling code here:
        optFil.setSelected(true); 
    }//GEN-LAST:event_chkCanSolNoTieneActionPerformed

    private void chkCanSolParcialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCanSolParcialActionPerformed
        // TODO add your handling code here:
        optFil.setSelected(true); 
    }//GEN-LAST:event_chkCanSolParcialActionPerformed

    private void chkCanSolTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCanSolTotalActionPerformed
        // TODO add your handling code here:
        optFil.setSelected(true); 
    }//GEN-LAST:event_chkCanSolTotalActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        // TODO add your handling code here:
        if (optTod.isSelected()){
            limpiarFiltros();
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_optFilActionPerformed

 
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton btnTipSol;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butVen;
    private javax.swing.JCheckBox chkAutSolAutorizada;
    private javax.swing.JCheckBox chkAutSolDenegada;
    private javax.swing.JCheckBox chkAutSolPendiente;
    private javax.swing.JCheckBox chkCanSolNoTiene;
    private javax.swing.JCheckBox chkCanSolParcial;
    private javax.swing.JCheckBox chkCanSolTotal;
    private javax.swing.JCheckBox chkEstSolCompleta;
    private javax.swing.JCheckBox chkEstSolNoTiene;
    private javax.swing.JCheckBox chkEstSolPendiente;
    private javax.swing.JCheckBox chkFacSolParcial;
    private javax.swing.JCheckBox chkFacSolPendiente;
    private javax.swing.JCheckBox chkFacSolTotal;
    private javax.swing.JCheckBox chkMosLocIna;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen1;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JScrollPane spnRpt;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblLoc;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodTipSol;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtNomTipSol;
    private javax.swing.JTextField txtNomVen;
    private javax.swing.JTextField txtNumCot;
    // End of variables declaration//GEN-END:variables

//    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }   
    

    
       String strFormatoFecha = "d/m/y";

//    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            objPerUsr=new ZafPerUsr(objParSis);
            
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            panFil.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            
            this.setTitle(objParSis.getNombreMenu() + strVer);
            lblTit.setText(objParSis.getNombreMenu());
            //Botón Genera Factura Automática
            
            if(objParSis.getCodigoMenu()==4142){  // Solicitud de reserva
                chkEstSolNoTiene.setSelected(true);
            }
            if(objParSis.getCodigoMenu()==4146){  /* AUTORIZACION DE RESERVA */
                chkEstSolPendiente.setSelected(true);
            }
            if(objParSis.getCodigoMenu()==4150){ /* CANCELACION DE RESERVA  */
                butGua.setVisible(false);
                chkAutSolAutorizada.setSelected(true);
                chkEstSolPendiente.setSelected(true); 
                
                 
            }
            if(objParSis.getCodigoMenu()==4154){    //    4154;1170;"C";"Seguimiento de solicitudes de reservas de inventario..."
                butGua.setVisible(false);
                //Estado de la solicitud
                chkEstSolPendiente.setSelected(true); 
                chkEstSolCompleta.setSelected(true);
                //Estado de la Autorizacion
                chkAutSolPendiente.setSelected(true); 
                chkAutSolAutorizada.setSelected(true);
                chkAutSolDenegada.setSelected(true);
                
            }
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_NOM_LOC,"Local");
            vecCab.add(INT_TBL_DAT_COD_COT,"Cód.Cot.");
            vecCab.add(INT_TBL_DAT_FEC_COT,"Fec.Cot.");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cod.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Nom.Cli.");
            vecCab.add(INT_TBL_DAT_TOT_COT,"Tot.Cot.");
            vecCab.add(INT_TBL_DAT_BTN_COT_VEN,"...");
            vecCab.add(INT_TBL_DAT_COD_TIP_SOL,"Cód.Tip.Sol.");
            vecCab.add(INT_TBL_DAT_BTN_TIP_SOL,"...");
            vecCab.add(INT_TBL_DAT_TIP_SOL,"Tip.Sol.");
            vecCab.add(INT_TBL_DAT_FEC_SOL,"Fec.Sol.");
            vecCab.add(INT_TBL_DAT_CHK_SOL_RES,"Sol.Res."); // JM 
            vecCab.add(INT_TBL_DAT_OBS_SOL_RES,"Obs.Res.");
            vecCab.add(INT_TBL_DAT_BTN_OBS_SOL_RES,"...");
            vecCab.add(INT_TBL_DAT_CHK_PED_OTR_BOD,"Ped.Otr.Bod.");
            vecCab.add(INT_TBL_DAT_BTN_PED_OTR_BOD,"...");
            vecCab.add(INT_TBL_DAT_CHK_SOL_ENV_PED,"Sol.Env.Ped.");
            vecCab.add(INT_TBL_DAT_CHK_PENDIENTE,"Pendientes");
            vecCab.add(INT_TBL_DAT_CHK_AUTORIZAR,"Autorizar");
            vecCab.add(INT_TBL_DAT_CHK_DENEGAR,"Denegar");
            vecCab.add(INT_TBL_DAT_CHK_AUT_ENV_PED,"Aut.Env.Ped.");
            vecCab.add(INT_TBL_DAT_FEC_SOL_FAC_AUT,"Fec.Aut.");
            vecCab.add(INT_TBL_DAT_OBS_AUT_RES,"Obs.Aut.");
            vecCab.add(INT_TBL_DAT_BTN_OBS_AUT_RES,"...");
            vecCab.add(INT_TBL_DAT_VAL_FAC,"Val.Fac.");
            vecCab.add(INT_TBL_DAT_BTN_LIS_FAC_VEN,"...");
            vecCab.add(INT_TBL_DAT_VAL_CAN,"Val.Can.");
            vecCab.add(INT_TBL_DAT_CHK_CANCELAR,"Cancelar");
            vecCab.add(INT_TBL_DAT_BUT_LIS_CAN,"...");
            vecCab.add(INT_TBL_DAT_CHK_NOT_PRO_RES,"No tiene");
            vecCab.add(INT_TBL_DAT_CHK_PEN_PRO_RES,"Pendiente");
            vecCab.add(INT_TBL_DAT_CHK_COM_PRO_RES,"Completo");
            vecCab.add(INT_TBL_DAT_EST_SOL_RES,"J:Sol.Res.");
            vecCab.add(INT_TBL_DAT_MOM_DES_SOL_RES,"J:Mom.Des.Sol.Res.");
            vecCab.add(INT_TBL_DAT_EST_FAC_PRI_DIA_LAB,"J:st_genFacPriDiaLabMes");
            
            vecCab.add(INT_TBL_DAT_STR_TIP_RES_INV,"J:tx_tipResInv");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //objTblMod.setFilasEditables(vecDat);n
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
            objTblPopMnu.setBorrarContenidoEnabled(true);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();

            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_LOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_COT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_COT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_TOT_COT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BTN_COT_VEN).setPreferredWidth(30);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_SOL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BTN_TIP_SOL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_TIP_SOL).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_FEC_SOL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CHK_SOL_RES).setPreferredWidth(30);//JM
            tcmAux.getColumn(INT_TBL_DAT_OBS_SOL_RES).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BTN_OBS_SOL_RES).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_CHK_PED_OTR_BOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BTN_PED_OTR_BOD).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK_SOL_ENV_PED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHK_PENDIENTE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUTORIZAR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DENEGAR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT_ENV_PED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_SOL_FAC_AUT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_OBS_AUT_RES).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BTN_OBS_AUT_RES).setPreferredWidth(30);
            
            tcmAux.getColumn(INT_TBL_DAT_VAL_FAC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BTN_LIS_FAC_VEN).setPreferredWidth(30);
            
            tcmAux.getColumn(INT_TBL_DAT_VAL_CAN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CANCELAR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_BUT_LIS_CAN).setPreferredWidth(30);

            tcmAux.getColumn(INT_TBL_DAT_CHK_NOT_PRO_RES).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHK_PEN_PRO_RES).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHK_COM_PRO_RES).setPreferredWidth(60);
            
            tcmAux.getColumn(INT_TBL_DAT_EST_SOL_RES).setPreferredWidth(10);
             tcmAux.getColumn(INT_TBL_DAT_MOM_DES_SOL_RES).setPreferredWidth(10);
             tcmAux.getColumn(INT_TBL_DAT_EST_FAC_PRI_DIA_LAB).setPreferredWidth(10);
             tcmAux.getColumn(INT_TBL_DAT_STR_TIP_RES_INV).setPreferredWidth(10);
             
             
            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_SOL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_SOL_RES, tblDat);    
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_SOL_RES, tblDat);    // Estado
            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_MOM_DES_SOL_RES, tblDat);    // J
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_FAC_PRI_DIA_LAB, tblDat);    // J
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_STR_TIP_RES_INV, tblDat);    // J
            
            if(objParSis.getCodigoMenu()==4150){
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BTN_TIP_SOL, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TIP_SOL, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_SOL_RES, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_SOL_ENV_PED, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_PENDIENTE, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_AUTORIZAR, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_DENEGAR, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_AUT_ENV_PED, tblDat);
                
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_SOL, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_VAL_FAC, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BTN_LIS_FAC_VEN, tblDat);
                
            }
            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setResizable(false);
            
            objTblCelRenLblCod=new ZafTblCelRenLbl();
            objTblCelRenLblCod.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_COD_COT).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setCellRenderer(objTblCelRenLblCod);
             
            
            
            tcmAux.getColumn(INT_TBL_DAT_FEC_COT).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));
            tcmAux.getColumn(INT_TBL_DAT_FEC_SOL).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));
            tcmAux.getColumn(INT_TBL_DAT_FEC_SOL_FAC_AUT).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));
            
             
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BTN_COT_VEN);
            /* SOLICITUD DE RESERVA */
            if(objParSis.getCodigoMenu()==4142){
                vecAux.add("" + INT_TBL_DAT_BTN_TIP_SOL);
                vecAux.add("" + INT_TBL_DAT_FEC_SOL);
            }
            vecAux.add("" + INT_TBL_DAT_BTN_OBS_SOL_RES);
            vecAux.add("" + INT_TBL_DAT_OBS_SOL_RES);
            vecAux.add(""+INT_TBL_DAT_BTN_PED_OTR_BOD); // JM 27/Abril/2018
            
                /* AUTORIZACION DE RESERVA */
            if(objParSis.getCodigoMenu()==4146){
                vecAux.add("" + INT_TBL_DAT_CHK_AUTORIZAR);
                vecAux.add("" + INT_TBL_DAT_CHK_DENEGAR);
                vecAux.add("" + INT_TBL_DAT_FEC_SOL_FAC_AUT);
            }
            
            /* NO ES SOLICITUD DE RESERVA PUEDE VER LA OBSERVACION DE LA AUTORIZACION */
            if(objParSis.getCodigoMenu()!=4142){
                vecAux.add("" + INT_TBL_DAT_OBS_AUT_RES);
                vecAux.add("" + INT_TBL_DAT_BTN_OBS_AUT_RES);
            }
            
            
            /* CANCELACION DE RESERVA */
            if(objParSis.getCodigoMenu()==4150){
                vecAux.add("" + INT_TBL_DAT_BUT_LIS_CAN);
            }
            vecAux.add("" + INT_TBL_DAT_BTN_LIS_FAC_VEN);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
       
            
            //AUTORIZACION
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*2);
            
            ZafTblHeaColGrp objTblHeaColGrpCot=new ZafTblHeaColGrp("Paso 1: Cotizaciones de Venta");
            objTblHeaColGrpCot.setHeight(16);
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DAT_COD_EMP)); 
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DAT_NOM_EMP)); 
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DAT_COD_LOC)); 
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DAT_NOM_LOC)); 
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DAT_COD_COT));
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DAT_FEC_COT));
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DAT_COD_CLI));
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DAT_NOM_CLI));
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DAT_TOT_COT));
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DAT_BTN_COT_VEN));
            
            ZafTblHeaColGrp objTblHeaColGrpSol=new ZafTblHeaColGrp("Paso 2: Solicitud de Reserva");
            objTblHeaColGrpSol.setHeight(16);
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_COD_TIP_SOL));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_BTN_TIP_SOL));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_TIP_SOL));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_FEC_SOL));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_CHK_SOL_RES));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_OBS_SOL_RES));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_BTN_OBS_SOL_RES));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_CHK_PED_OTR_BOD));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_BTN_PED_OTR_BOD));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_CHK_SOL_ENV_PED));
            
            ZafTblHeaColGrp objTblHeaColGrp=new ZafTblHeaColGrp("Paso 3: Autorizacion");
            objTblHeaColGrp.setHeight(16);
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_CHK_PENDIENTE)); 
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_CHK_AUTORIZAR)); 
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_CHK_DENEGAR)); 
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_CHK_AUT_ENV_PED)); 
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_FEC_SOL_FAC_AUT)); 
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_OBS_AUT_RES)); 
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BTN_OBS_AUT_RES)); 
      
            ZafTblHeaColGrp objTblHeaColFac=new ZafTblHeaColGrp("Paso 4: Factura de Ventas");
            objTblHeaColFac.setHeight(16);
            objTblHeaColFac.add(tcmAux.getColumn(INT_TBL_DAT_VAL_FAC)); 
            objTblHeaColFac.add(tcmAux.getColumn(INT_TBL_DAT_BTN_LIS_FAC_VEN)); 
            
            ZafTblHeaColGrp objTblHeaColCan=new ZafTblHeaColGrp("Paso 5: Cancelacion");
            objTblHeaColCan.setHeight(16);
            objTblHeaColCan.add(tcmAux.getColumn(INT_TBL_DAT_VAL_CAN)); 
            objTblHeaColCan.add(tcmAux.getColumn(INT_TBL_DAT_CHK_CANCELAR)); 
            objTblHeaColCan.add(tcmAux.getColumn(INT_TBL_DAT_BUT_LIS_CAN)); 
            
            ZafTblHeaColGrp objTblHeaColResPro=new ZafTblHeaColGrp("Resumen del Proceso");
            objTblHeaColResPro.setHeight(16);
            objTblHeaColResPro.add(tcmAux.getColumn(INT_TBL_DAT_CHK_NOT_PRO_RES)); 
            objTblHeaColResPro.add(tcmAux.getColumn(INT_TBL_DAT_CHK_PEN_PRO_RES)); 
            objTblHeaColResPro.add(tcmAux.getColumn(INT_TBL_DAT_CHK_COM_PRO_RES)); 
            
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpCot);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpSol);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            objTblHeaGrp.addColumnGroup(objTblHeaColFac);
            objTblHeaGrp.addColumnGroup(objTblHeaColCan);
            objTblHeaGrp.addColumnGroup(objTblHeaColResPro);
            
            
            objTblHeaColGrpCot=null;
            objTblHeaColGrp=null;
            objTblHeaColGrpSol=null;
            objTblHeaColFac=null;
            objTblHeaColCan = null;
            
             
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            objTblFilCab=null;
            
            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BTN_COT_VEN).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BTN_TIP_SOL).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BTN_OBS_SOL_RES).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BTN_OBS_AUT_RES).setCellRenderer(objTblCelRenBut);            
            tcmAux.getColumn(INT_TBL_DAT_BUT_LIS_CAN).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BTN_LIS_FAC_VEN).setCellRenderer(objTblCelRenBut);
            
            tcmAux.getColumn(INT_TBL_DAT_BTN_PED_OTR_BOD).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;

            //Cheks 
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_PED_OTR_BOD).setCellRenderer(objTblCelRenChk);  // SOLICITAR RESERVA
            tcmAux.getColumn(INT_TBL_DAT_CHK_SOL_ENV_PED).setCellRenderer(objTblCelRenChk);   // FACTURA AUTOMATICA
            tcmAux.getColumn(INT_TBL_DAT_CHK_PENDIENTE).setCellRenderer(objTblCelRenChk);   // AUTORIZAR    
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUTORIZAR).setCellRenderer(objTblCelRenChk);   // DENEGAR           
            tcmAux.getColumn(INT_TBL_DAT_CHK_DENEGAR).setCellRenderer(objTblCelRenChk);    
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT_ENV_PED).setCellRenderer(objTblCelRenChk);    
            tcmAux.getColumn(INT_TBL_DAT_CHK_NOT_PRO_RES).setCellRenderer(objTblCelRenChk);    
            tcmAux.getColumn(INT_TBL_DAT_CHK_PEN_PRO_RES).setCellRenderer(objTblCelRenChk);    
            tcmAux.getColumn(INT_TBL_DAT_CHK_COM_PRO_RES).setCellRenderer(objTblCelRenChk);    
            tcmAux.getColumn(INT_TBL_DAT_CHK_SOL_RES).setCellRenderer(objTblCelRenChk); // JoseMario  
            
            
            
            objTblCelRenChk = null;
            
            objTblCelRenChk2=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_PED_OTR_BOD).setCellRenderer(objTblCelRenChk2);  // SOLICITAR RESERVA
            tcmAux.getColumn(INT_TBL_DAT_CHK_SOL_ENV_PED).setCellRenderer(objTblCelRenChk2);   // FACTURA AUTOMATICA
            
            
            objTblCelRenChk2.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt){
                    if (objTblMod.getValueAt(objTblCelRenChk2.getRowRender(), INT_TBL_DAT_CHK_PED_OTR_BOD).equals(true)){
                        objTblCelRenChk2.setBackground(Color.RED);
                    }
                    else{
                        objTblCelRenChk2.setBackground(Color.WHITE);
                    }
                }
            });
            /*-----------------------------------------------------*/
            
            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), true, true);
            tcmAux.getColumn(INT_TBL_DAT_TOT_COT).setCellRenderer(objTblCelRenLbl);          
            tcmAux.getColumn(INT_TBL_DAT_VAL_FAC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CAN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;
            
 
            
            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_DAT_TIP_SOL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_SOL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_SOL_FAC_AUT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;
            
            
            ButCotVen butCotVen = new ButCotVen(tblDat, INT_TBL_DAT_BTN_COT_VEN); //*****
            ButObsSolRes butObsSolRes = new ButObsSolRes(tblDat, INT_TBL_DAT_BTN_OBS_SOL_RES); //*****
            ButObsAutResVen butObsAutResVen = new ButObsAutResVen(tblDat, INT_TBL_DAT_BTN_OBS_AUT_RES); //*****
            ButLisCanResVen butLisCanResVen = new ButLisCanResVen(tblDat, INT_TBL_DAT_BUT_LIS_CAN); //*****
            ButLisFacVen butLisFacVen = new ButLisFacVen(tblDat, INT_TBL_DAT_BTN_LIS_FAC_VEN); //*****
            
            ButLisPedOtrBod butPedOtrBod = new ButLisPedOtrBod(tblDat, INT_TBL_DAT_BTN_PED_OTR_BOD);
            
           
             
            
          
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblOrd=new ZafTblOrd(tblDat);
            objTblBus=new ZafTblBus(tblDat);
            objDocLis=new ZafDocLis();
            
            configurarVenConCli();
            configurarVenConVen();
            configurarTblLoc();
            cargarLoc();
            configurarVenConTipSol();
            
            if(objParSis.getCodigoMenu()==4150){
                txtCodTipSol.setText("5");
                if (!txtCodTipSol.getText().equalsIgnoreCase(strCodTipSol)){
                    if (txtCodTipSol.getText().equals("")){
                        txtCodTipSol.setText("");
                        txtNomTipSol.setText("");
                    }
                    else{
                        mostrarVenConTipSol(1);
                    }
                }
                else
                txtCodTipSol.setText(strCodTipSol);
            }
            
            
            
            int intColVen[]=new int[5];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=5;//tx_momDes
            intColVen[3]=4;//st_facPriDiaLab
            intColVen[4]=3;
            int intColTbl[]=new int[5];
            intColTbl[0]=INT_TBL_DAT_COD_TIP_SOL;
            intColTbl[1]=INT_TBL_DAT_TIP_SOL;
            intColTbl[2]=INT_TBL_DAT_MOM_DES_SOL_RES;
            intColTbl[3]=INT_TBL_DAT_EST_FAC_PRI_DIA_LAB;
            intColTbl[4]=INT_TBL_DAT_STR_TIP_RES_INV;
            objTblCelEdiButVcoTipSolRes=new ZafTblCelEdiButVco(tblDat, vcoTipSol, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BTN_TIP_SOL).setCellEditor(objTblCelEdiButVcoTipSolRes);
            objTblCelEdiButVcoTipSolRes.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strFacPriDiaMes="";
                int intCodEmp,intCodLoc,intRow;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_EST_SOL_RES)!=null){
                        MensajeInf("NO ES POSIBLE MODIFICAR UNA SOLICITUD DE RESERVA");
                        objTblCelEdiButVcoTipSolRes.setEnabled(false);
                    }
                    else{
                        objTblCelEdiButVcoTipSolRes.setEnabled(true);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    intCodEmp = Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP).toString());
                    intCodLoc = Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_LOC).toString());
                    intRow = tblDat.getSelectedRow();
                    if( vcoTipSol.getValueAt(4)!=null){
                        strFacPriDiaMes = vcoTipSol.getValueAt(4).toString();
                        if(strFacPriDiaMes.equals("S")){
                            objTblMod.setValueAt( getFechaLab(intCodEmp, intCodLoc), intRow, INT_TBL_DAT_FEC_SOL);
                        }
                    }else{
                        tblDat.editCellAt(tblDat.getSelectedRow(), INT_TBL_DAT_FEC_SOL);
                    } 
                    /* solicitando enviar mercaderia antes de que se genere la factura */
                    if(vcoTipSol.getValueAt(5)!=null){
                        if(vcoTipSol.getValueAt(5).toString().equals("A")){
                            objTblMod.setValueAt(true, intRow, INT_TBL_DAT_CHK_SOL_ENV_PED); 
                        }
                        else{
                            objTblMod.setValueAt(false, intRow, INT_TBL_DAT_CHK_SOL_ENV_PED);
                        }
                    }
                    else{
                        objTblMod.setValueAt(false, intRow, INT_TBL_DAT_CHK_SOL_ENV_PED);
                    }

                    if(objTblMod.getValueAt(intRow, INT_TBL_DAT_COD_TIP_SOL)!=null){
                        objTblMod.setValueAt(true, intRow, INT_TBL_DAT_CHK_SOL_RES);
                    }
                    
                }
            });
            intColVen=null;
            intColTbl=null;
           
            
            /* VALIDACIONES DE FECHAS DOCUMENTOS */
            
            
            
            /* VALIDACIONES DE FECHAS DOCUMENTOS */
            
            
            
            /* Renderizadores - Autorizacion  */
            
            objTblCelEdiChkPre = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUTORIZAR).setCellEditor(objTblCelEdiChkPre);
            objTblCelEdiChkPre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strFecSolRes="";
                String strMensaje="<html>La Solicitud presenta envio de material antes de la generacion de la factura. <BR> ¿Está seguro que desea realizar esta operación?<html>";
                String strMenEmp="<html>La Solicitud de reserva en la misma empresa posee <FONT COLOR=\"red\">Transferencias de Inventario. </FONT> <BR> ¿Está seguro que desea realizar esta operación?<html>";
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    strFecSolRes = objTblMod.getValueAt(intNumFil, INT_TBL_DAT_FEC_SOL).toString();
                    System.out.println("strFecSolRes: " + strFecSolRes);
                    objTblMod.setValueAt(strFecSolRes, intNumFil, INT_TBL_DAT_FEC_SOL_FAC_AUT); 
                    
                    /* Reserva con facturación automatica */ 
                    if(objTblMod.getValueAt(intNumFil, INT_TBL_DAT_MOM_DES_SOL_RES)!=null){
                        if(objTblMod.getValueAt(intNumFil, INT_TBL_DAT_MOM_DES_SOL_RES).toString().equals("A")){
                            quitarChksAutorizacion(intNumFil,INT_TBL_DAT_CHK_AUTORIZAR);
                            if (mostrarMsgCon(strMensaje)==0){
                                 objTblMod.setValueAt(true, intNumFil, INT_TBL_DAT_CHK_AUT_ENV_PED); 
                            }
                            else{
                                System.out.println("NOoo....");
                                quitarChksAutorizacion(intNumFil,INT_TBL_DAT_CHK_DENEGAR);
                            }
                        }
                    }
                    
                    
                   
                    /* Reserva en la propia empresa */ 
                    if(objTblMod.getValueAt(intNumFil, INT_TBL_DAT_CHK_PED_OTR_BOD).equals(true)){
                        if(objTblMod.getValueAt(intNumFil,INT_TBL_DAT_STR_TIP_RES_INV).toString().equals("R")){
                            if (mostrarMsgCon(strMenEmp)==0){
                                 objTblMod.setValueAt(true, intNumFil, INT_TBL_DAT_CHK_AUT_ENV_PED); 
                            }
                            else{
                                System.out.println("NOoo....");
                                quitarChksAutorizacion(intNumFil,INT_TBL_DAT_CHK_DENEGAR);
                            }
                        }
                    }
                    
                    
                }
            });
            
            objTblCelEdiChkPre = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DENEGAR).setCellEditor(objTblCelEdiChkPre);
            objTblCelEdiChkPre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    quitarChksAutorizacion(intNumFil,INT_TBL_DAT_CHK_DENEGAR);
                }
            });
            
            
            /* Renderizadores - Autorizacion  */
             
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                txtCodCli.setEditable(false);
                txtDesLarCli.setEditable(false);
                butCli.setEnabled(false);
            }
            
            int intCol[]={INT_TBL_DAT_TOT_COT, INT_TBL_DAT_VAL_FAC, INT_TBL_DAT_VAL_CAN};
            objTblTot=new ZafTblTot(spnRpt, spnTot, tblDat, tblTot, intCol);
             
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);           
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private ZafTblTot objTblTot;                                //JTable de totales.
    /**
     * private final int INT_TBL_DAT_CHK_PENDIENTE=20;    // CheckBox: Pendiente
     * private final int INT_TBL_DAT_CHK_AUTORIZAR=21;    // CheckBox: Autorizar
     * private final int INT_TBL_DAT_CHK_DENEGAR=22;    // CheckBox: Denegar
     * @param Row
     * @param Column 
     */
    private void quitarChksAutorizacion(int Row, int Column){
        try{
             if(Column==INT_TBL_DAT_CHK_AUTORIZAR){
                 objTblMod.setValueAt(true, Row, INT_TBL_DAT_CHK_AUTORIZAR); 
                 
                 objTblMod.setValueAt(false, Row, INT_TBL_DAT_CHK_PENDIENTE); 
                 objTblMod.setValueAt(false, Row, INT_TBL_DAT_CHK_DENEGAR); 
             }
             if(Column==INT_TBL_DAT_CHK_DENEGAR){
                 objTblMod.setValueAt(true, Row, INT_TBL_DAT_CHK_DENEGAR); 
                 
                 objTblMod.setValueAt(false, Row, INT_TBL_DAT_CHK_AUTORIZAR); 
                 objTblMod.setValueAt(false, Row, INT_TBL_DAT_CHK_PENDIENTE); 
                 objTblMod.setValueAt(false, Row, INT_TBL_DAT_CHK_AUT_ENV_PED); 
             }
        }
        catch(Exception Evt){ 
          objUti.mostrarMsgErr_F1(null, Evt); 
        }
         
    }
    
    private void MensajeInf(String strMensaje) {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this, strMensaje, strTit, JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    /**
         * Esta función determina si los campos son válidos.
         *
         * @return true: Si los campos son válidos. <BR>false: En el caso
         * contrario.
         */
        private boolean isCamVal() {
            int intExiDatTbl = 0;
             /* SOLICITUD DE RESERVA */
            if(objParSis.getCodigoMenu()==4142){
                for (int i = 0; i < tblDat.getRowCount(); i++) {
                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
                        if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_SOL_RES)){
                            intExiDatTbl++;
                            if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_SOL_RES)){
                                if (((tblDat.getValueAt(i, INT_TBL_DAT_FEC_SOL) == null ? "" : (tblDat.getValueAt(i, INT_TBL_DAT_FEC_SOL).toString().equals("") ? "" : tblDat.getValueAt(i, INT_TBL_DAT_FEC_SOL).toString())).equals("")))  {
                                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Factura </FONT> es obligatorio.<BR>Verifique esto y vuelva a intentarlo.</HTML>");
                                    tblDat.repaint();
                                    tblDat.requestFocus();
                                    tblDat.editCellAt(i, INT_TBL_DAT_FEC_SOL);
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            else if(objParSis.getCodigoMenu()==4146){
                for (int i = 0; i < tblDat.getRowCount(); i++) {
                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
                        intExiDatTbl++;
                        if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_SOL_RES)){
                            if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_AUTORIZAR)){
                                if (((tblDat.getValueAt(i, INT_TBL_DAT_FEC_SOL_FAC_AUT) == null ? "" : (tblDat.getValueAt(i, INT_TBL_DAT_FEC_SOL_FAC_AUT).toString().equals("") ? "" : tblDat.getValueAt(i, INT_TBL_DAT_FEC_SOL_FAC_AUT).toString())).equals("")))  {
                                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Factura Automatica Autorizada</FONT> es obligatorio.<BR>Verifique esto y vuelva a intentarlo.</HTML>");
                                    tblDat.repaint();
                                    tblDat.requestFocus();
                                    tblDat.editCellAt(i, INT_TBL_DAT_FEC_SOL_FAC_AUT);
                                    return false;
                                }
                            }
                        }
                    }
                }
                
            }
            
            
            if (intExiDatTbl == 0) {
                mostrarMsgInf("NO HAY DATOS EN DETALLE INGRESE DATOS.... ");
                return false;
            }
            return true;
        
        }
    
    
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    
    private boolean cargarDetReg(java.sql.Connection conn){
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
                String strConSQL="";
                switch (objSelFec.getTipoSeleccion()){
                    case 0: //Búsqueda por rangos
                        strConSQL+=" AND a1.fe_cot BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strConSQL+=" AND a1.fe_cot<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strConSQL+=" AND a1.fe_cot>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }
                /* Filtro */
                if (txtCodCli.getText().length()>0){
                    strConSQL+=" AND a1.co_cli=" + txtCodCli.getText();
                }
                if (txtCodVen.getText().length()>0){
                    strConSQL+=" AND a1.co_ven=" + txtCodVen.getText();
                }
                if(txtCodTipSol.getText().length()>0){
                    strConSQL+=" AND a1.co_tipSolResInv= "+txtCodTipSol.getText()+" ";
                }
                if(txtNumCot.getText().length()>0){
                    strConSQL+=" AND a1.co_cot= "+txtNumCot.getText()+" ";
                }
                /* Filtro CHKs */
                strAux = filtroChecks();
                if(strAux.length()>0){
                    strConSQL+=strAux;
                }
                
                
                //Obtener los locales a consultar.
                int intNumFilTblBod=objTblModLoc.getRowCountTrue();
                int i=0;
                strAux="";
                for (int j=0; j<intNumFilTblBod; j++){
                    if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHK)){
                        if (i==0)
                            strAux+=" (a1.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP) + " AND a1.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_LOC) + ")";
                        else
                            strAux+=" OR (a1.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP) + " AND a1.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_LOC) + ")";
                        i++;
                    }
                }
                if (!strAux.equals("")){
                    strConSQL+=" AND (" + strAux + ")";
                }
                
                //Filtro: Ventas a clientes, Ventas a empresas relacionadas, Préstamos.
                //A=Activo;I=Anulado;P=Pendiente por autorizar;U=Autorizado;D=Denegado;E=En proceso de facturar;L=Listo para facturar;F=Facturada
                strAux="";
                if(objParSis.getCodigoMenu()==4142){  //4142; ;"C";"Solicitudes de reservas de inventario..."
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        strSQL=" ";
                        strSQL+=" SELECT a1.co_emp, a2.tx_nom as tx_nomEmp,a1.co_loc,a3.tx_nom as tx_nomLoc,a1.co_cot,a1.fe_cot,a1.co_cli,  \n";
                        strSQL+="        a4.tx_nom,ROUND(a1.nd_tot,2) as nd_tot, a1.st_reg,a1.st_solResInv,a1.co_tipSolResInv, a1.fe_solResInv, \n";
                        strSQL+="        a1.tx_obsSolResInv, a1.st_pedOtrBod, a1.st_solEnvPed, a1.st_autSolResInv, a1.st_autEnvPed,  \n";
                        strSQL+="        ROUND( a1.nd_valSolResInvFac,2) as nd_valSolResInvFac,   \n";
                        strSQL+="        a1.nd_valSolResInvCan, a5.tx_desLar, a5.tx_tipResInv,CASE WHEN a5.st_facPriDiaLabSigMes IS NULL THEN 'N' ELSE a5.st_facPriDiaLabSigMes END AS st_facPriDiaLabSigMes,a5.tx_momDes  , \n";
                        strSQL+="        a1.fe_autSolResInv,a1.tx_obsAutSolResInv, ";
                        strSQL+="        CASE WHEN a1.st_solResInv IS NOT NULL AND a1.st_reg <> 'F' THEN 'P' ELSE 'N' END as pendiente , \n";
                        strSQL+="        CASE WHEN a1.st_solResInv IS NULL THEN 'P' ELSE 'N' END as noAplica   \n";
                        strSQL+=" FROM tbm_cabCotVen AS a1  \n";
                        strSQL+=" INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp)  \n";
                        strSQL+=" INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)  \n";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)  \n";
                        strSQL+=" LEFT OUTER JOIN tbm_tipSolResInv as a5 ON (a1.co_tipSolResInv=a5.co_tipSolResInv AND a5.st_reg='A') \n";
                        strSQL+=" WHERE a1.st_reg IN ('A','R','P','U')    \n";
                        strSQL+="       "+strConSQL+" \n";
                        strSQL+="  AND a1.st_reg IN ('A','R','P','U') \n";
                        strSQL+=" ORDER BY a1.co_emp,a1.co_loc,a1.co_cot  ";
                        strSQL+=" \n";
                    }
                    else{
                        strSQL=" ";
                        strSQL+=" SELECT a1.co_emp, a2.tx_nom as tx_nomEmp,a1.co_loc,a3.tx_nom as tx_nomLoc,a1.co_cot,a1.fe_cot,a1.co_cli,  \n";
                        strSQL+="        a4.tx_nom,ROUND(a1.nd_tot,2) as nd_tot, a1.st_reg,a1.st_solResInv,a1.co_tipSolResInv, a1.fe_solResInv, \n";
                        strSQL+="        a1.tx_obsSolResInv, a1.st_pedOtrBod, a1.st_solEnvPed, a1.st_autSolResInv, a1.st_autEnvPed,  \n";
                        strSQL+="        ROUND( a1.nd_valSolResInvFac,2) as nd_valSolResInvFac,   \n";
                        strSQL+="        a1.nd_valSolResInvCan, a5.tx_desLar, a5.tx_tipResInv,CASE WHEN a5.st_facPriDiaLabSigMes IS NULL THEN 'N' ELSE a5.st_facPriDiaLabSigMes END AS st_facPriDiaLabSigMes,a5.tx_momDes  , \n";
                        strSQL+="        a1.fe_autSolResInv,a1.tx_obsAutSolResInv, ";
                        strSQL+="        CASE WHEN a1.st_solResInv IS NOT NULL AND a1.st_reg <> 'F' THEN 'P' ELSE 'N' END as pendiente , \n";
                        strSQL+="        CASE WHEN a1.st_solResInv IS NULL THEN 'P' ELSE 'N' END as noAplica   \n";
                        strSQL+=" FROM tbm_cabCotVen AS a1  \n";
                        strSQL+=" INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp)  \n";
                        strSQL+=" INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)  \n";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)  \n";
                        strSQL+=" LEFT OUTER JOIN tbm_tipSolResInv as a5 ON (a1.co_tipSolResInv=a5.co_tipSolResInv AND a5.st_reg='A') \n";
                        strSQL+=" WHERE  a1.co_emp="+objParSis.getCodigoEmpresa()+"    \n";
                        strSQL+="       "+strConSQL+" \n";
                        strSQL+=" \n";
                        strSQL+=" AND a1.st_reg IN ('A','R','P','U') \n";
                        strSQL+=" ORDER BY a1.co_emp,a1.co_loc,a1.co_cot  ";
                    }
                }
                else if(objParSis.getCodigoMenu()==4146){ // 4146; ;"C";"Autorizaciones de reserva de mercaderia..."
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        strSQL=" ";
                        strSQL+=" SELECT a1.co_emp, a2.tx_nom as tx_nomEmp,a1.co_loc,a3.tx_nom as tx_nomLoc,a1.co_cot,a1.fe_cot,a1.co_cli,  \n";
                        strSQL+="        a4.tx_nom,ROUND(a1.nd_tot,2) as nd_tot, a1.st_reg,a1.st_solResInv,a1.co_tipSolResInv, a1.fe_solResInv, \n";
                        strSQL+="        a1.tx_obsSolResInv, a1.st_pedOtrBod, a1.st_solEnvPed, a1.st_autSolResInv, a1.st_autEnvPed, \n";
                        strSQL+="        ROUND( a1.nd_valSolResInvFac,2) as nd_valSolResInvFac,   \n";
                        strSQL+="        a1.nd_valSolResInvCan, a5.tx_desLar, a5.tx_tipResInv,CASE WHEN a5.st_facPriDiaLabSigMes IS NULL THEN 'N' ELSE a5.st_facPriDiaLabSigMes END  AS st_facPriDiaLabSigMes,a5.tx_momDes  , \n";
                        strSQL+="        a1.fe_autSolResInv,a1.tx_obsAutSolResInv, ";
                        strSQL+="        CASE WHEN a1.st_solResInv IS NOT NULL AND a1.st_reg <> 'F' THEN 'P' ELSE 'N' END as pendiente , \n";
                        strSQL+="        CASE WHEN a1.st_solResInv IS NULL THEN 'P' ELSE 'N' END as noAplica   \n";
                        strSQL+=" FROM tbm_cabCotVen AS a1  \n";
                        strSQL+=" INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp)  \n";
                        strSQL+=" INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)  \n";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)  \n";
                        strSQL+=" INNER JOIN tbm_tipSolResInv as a5 ON (a1.co_tipSolResInv=a5.co_tipSolResInv AND a5.st_reg='A') \n";
                        strSQL+=" WHERE a1.st_reg NOT IN ('I')   \n";
                        strSQL+="       "+strConSQL+" \n";
                        strSQL+=" \n";
                         
                        strSQL+=" ORDER BY a1.co_emp,a1.co_loc,a1.co_cot  ";
                    } 
                    else{
                        strSQL=" ";
                        strSQL+=" SELECT a1.co_emp, a2.tx_nom as tx_nomEmp,a1.co_loc,a3.tx_nom as tx_nomLoc,a1.co_cot,a1.fe_cot,a1.co_cli,  \n";
                        strSQL+="        a4.tx_nom,ROUND(a1.nd_tot,2) as nd_tot, a1.st_reg,a1.st_solResInv,a1.co_tipSolResInv, a1.fe_solResInv, \n";
                        strSQL+="        a1.tx_obsSolResInv, a1.st_pedOtrBod, a1.st_solEnvPed, a1.st_autSolResInv, a1.st_autEnvPed,  \n";
                        strSQL+="        ROUND( a1.nd_valSolResInvFac,2) as nd_valSolResInvFac,   \n";
                        strSQL+="        a1.nd_valSolResInvCan, a5.tx_desLar, a5.tx_tipResInv,a5.tx_momDes , \n";
                        strSQL+="        a1.fe_autSolResInv,a1.tx_obsAutSolResInv,CASE WHEN a5.st_facPriDiaLabSigMes IS NULL THEN 'N' ELSE a5.st_facPriDiaLabSigMes END AS st_facPriDiaLabSigMes, \n";
                        strSQL+="        CASE WHEN a1.st_solResInv IS NOT NULL AND a1.st_reg <> 'F' THEN 'P' ELSE 'N' END as pendiente , \n";
                        strSQL+="        CASE WHEN a1.st_solResInv IS NULL THEN 'P' ELSE 'N' END as noAplica   \n";
                        strSQL+=" FROM tbm_cabCotVen AS a1  \n";
                        strSQL+=" INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp)  \n";
                        strSQL+=" INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)  \n";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)  \n";
                        strSQL+=" INNER JOIN tbm_tipSolResInv as a5 ON (a1.co_tipSolResInv=a5.co_tipSolResInv AND a5.st_reg='A') \n";
                        strSQL+=" WHERE  a1.co_emp="+objParSis.getCodigoEmpresa()+"    \n";
                        strSQL+="       "+strConSQL+" AND a1.st_reg NOT IN ('I')     \n";
                        strSQL+=" ORDER BY a1.co_emp,a1.co_loc,a1.co_cot  ";
                    }
                }
                else if(objParSis.getCodigoMenu()==4154){    //    4154;1170;"C";"Seguimiento de solicitudes de reservas de inventario..."
                     if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        strSQL=" ";
                        strSQL+=" SELECT a1.co_emp, a2.tx_nom as tx_nomEmp,a1.co_loc,a3.tx_nom as tx_nomLoc,a1.co_cot,a1.fe_cot,a1.co_cli,  \n";
                        strSQL+="        a4.tx_nom,ROUND(a1.nd_tot,2) as nd_tot, a1.st_reg,a1.st_solResInv,a1.co_tipSolResInv, a1.fe_solResInv, \n";
                        strSQL+="        a1.tx_obsSolResInv, a1.st_pedOtrBod, a1.st_solEnvPed, a1.st_autSolResInv, a1.st_autEnvPed,  \n";
                        strSQL+="        ROUND( a1.nd_valSolResInvFac,2) as nd_valSolResInvFac,   \n";
                        strSQL+="        a1.nd_valSolResInvCan, a5.tx_desLar, a5.tx_tipResInv,CASE WHEN a5.st_facPriDiaLabSigMes IS NULL THEN 'N' ELSE a5.st_facPriDiaLabSigMes END AS st_facPriDiaLabSigMes,a5.tx_momDes  , \n";
                        strSQL+="        a1.fe_autSolResInv,a1.tx_obsAutSolResInv,  ";
                        strSQL+="        CASE WHEN a1.st_solResInv IS NOT NULL AND a1.st_reg <> 'F' THEN 'P' ELSE 'N' END as pendiente , \n";
                        strSQL+="        CASE WHEN a1.st_solResInv IS NULL THEN 'P' ELSE 'N' END as noAplica   \n";
                        strSQL+=" FROM tbm_cabCotVen AS a1  \n";
                        strSQL+=" INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp)  \n";
                        strSQL+=" INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)  \n";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)  \n";
                        strSQL+=" INNER JOIN tbm_tipSolResInv as a5 ON (a1.co_tipSolResInv=a5.co_tipSolResInv AND a5.st_reg='A') \n";
                        strSQL+=" WHERE  a1.st_reg NOT IN ('I')  \n";
                        strSQL+="       "+strConSQL+" \n";
                        strSQL+=" \n";
                        strSQL+=" ORDER BY a1.co_emp,a1.co_loc,a1.co_cot  ";
                    } 
                    else{
                        strSQL=" ";
                        strSQL+=" SELECT a1.co_emp, a2.tx_nom as tx_nomEmp,a1.co_loc,a3.tx_nom as tx_nomLoc,a1.co_cot,a1.fe_cot,a1.co_cli,  \n";
                        strSQL+="        a4.tx_nom,ROUND(a1.nd_tot,2) as nd_tot, a1.st_reg,a1.st_solResInv,a1.co_tipSolResInv, a1.fe_solResInv, \n";
                        strSQL+="        a1.tx_obsSolResInv, a1.st_pedOtrBod, a1.st_solEnvPed, a1.st_autSolResInv, a1.st_autEnvPed, \n";
                        strSQL+="        ROUND( a1.nd_valSolResInvFac,2) as nd_valSolResInvFac,   \n";
                        strSQL+="        a1.nd_valSolResInvCan, a5.tx_desLar, a5.tx_tipResInv,CASE WHEN a5.st_facPriDiaLabSigMes IS NULL THEN 'N' ELSE a5.st_facPriDiaLabSigMes END AS st_facPriDiaLabSigMes,a5.tx_momDes  , \n";
                        strSQL+="        a1.fe_autSolResInv,a1.tx_obsAutSolResInv,  ";
                        strSQL+="        CASE WHEN a1.st_solResInv IS NOT NULL AND a1.st_reg <> 'F' THEN 'P' ELSE 'N' END as pendiente , \n";
                        strSQL+="        CASE WHEN a1.st_solResInv IS NULL THEN 'P' ELSE 'N' END as noAplica   \n";
                        strSQL+=" FROM tbm_cabCotVen AS a1  \n";
                        strSQL+=" INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp)  \n";
                        strSQL+=" INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)  \n";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)  \n";
                        strSQL+=" INNER JOIN tbm_tipSolResInv as a5 ON (a1.co_tipSolResInv=a5.co_tipSolResInv AND a5.st_reg='A') \n";
                        strSQL+=" WHERE  a1.co_emp="+objParSis.getCodigoEmpresa()+"    \n";
                        strSQL+="       "+strConSQL+" \n";
                        strSQL+=" \n";
                        strSQL+=" AND a1.st_reg NOT IN ('I')  \n";
                        strSQL+=" ORDER BY a1.co_emp,a1.co_loc,a1.co_cot  ";
                    }
                }
                else if(objParSis.getCodigoMenu()==4150){ /* CANCELACION DE RESERVA  */
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        strSQL=" ";
                        strSQL+=" SELECT a1.co_emp, a2.tx_nom as tx_nomEmp,a1.co_loc,a3.tx_nom as tx_nomLoc,a1.co_cot,a1.fe_cot,a1.co_cli,  \n";
                        strSQL+="        a4.tx_nom,ROUND(a1.nd_tot,2) as nd_tot, a1.st_reg,a1.st_solResInv,a1.co_tipSolResInv, a1.fe_solResInv, \n";
                        strSQL+="        a1.tx_obsSolResInv, a1.st_pedOtrBod, a1.st_solEnvPed, a1.st_autSolResInv, a1.st_autEnvPed,  \n";
                        strSQL+="        ROUND( a1.nd_valSolResInvFac,2) as nd_valSolResInvFac,   \n";
                        strSQL+="        a1.nd_valSolResInvCan, a5.tx_desLar, a5.tx_tipResInv,CASE WHEN a5.st_facPriDiaLabSigMes IS NULL THEN 'N' ELSE a5.st_facPriDiaLabSigMes END AS st_facPriDiaLabSigMes,a5.tx_momDes  , \n";
                        strSQL+="        a1.fe_autSolResInv,a1.tx_obsAutSolResInv,  ";
                        strSQL+="        CASE WHEN a1.st_solResInv IS NOT NULL AND a1.st_reg <> 'F' THEN 'P' ELSE 'N' END as pendiente , \n";
                        strSQL+="        CASE WHEN a1.st_solResInv IS NULL THEN 'P' ELSE 'N' END as noAplica   \n";
                        strSQL+=" FROM tbm_cabCotVen AS a1  \n";
                        strSQL+=" INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp)  \n";
                        strSQL+=" INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)  \n";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)  \n";
                        strSQL+=" INNER JOIN tbm_tipSolResInv as a5 ON (a1.co_tipSolResInv=a5.co_tipSolResInv AND a5.st_reg='A') \n";
                        strSQL+=" WHERE  a1.st_reg NOT IN ('I')  \n";
                        strSQL+="       "+strConSQL+" \n";
                        strSQL+=" \n";
                        strSQL+=" ORDER BY a1.co_emp,a1.co_loc,a1.co_cot  ";
                    } 
                    else{
                        strSQL=" ";
                        strSQL+=" SELECT a1.co_emp, a2.tx_nom as tx_nomEmp,a1.co_loc,a3.tx_nom as tx_nomLoc,a1.co_cot,a1.fe_cot,a1.co_cli,  \n";
                        strSQL+="        a4.tx_nom,ROUND(a1.nd_tot,2) as nd_tot, a1.st_reg,a1.st_solResInv,a1.co_tipSolResInv, a1.fe_solResInv, \n";
                        strSQL+="        a1.tx_obsSolResInv, a1.st_pedOtrBod, a1.st_solEnvPed, a1.st_autSolResInv, a1.st_autEnvPed, \n";
                        strSQL+="        ROUND( a1.nd_valSolResInvFac,2) as nd_valSolResInvFac,   \n";
                        strSQL+="        a1.nd_valSolResInvCan, a5.tx_desLar, a5.tx_tipResInv,CASE WHEN a5.st_facPriDiaLabSigMes IS NULL THEN 'N' ELSE a5.st_facPriDiaLabSigMes END AS st_facPriDiaLabSigMes,a5.tx_momDes  , \n";
                        strSQL+="        a1.fe_autSolResInv,a1.tx_obsAutSolResInv,  ";
                        strSQL+="        CASE WHEN a1.st_solResInv IS NOT NULL AND a1.st_reg <> 'F' THEN 'P' ELSE 'N' END as pendiente , \n";
                        strSQL+="        CASE WHEN a1.st_solResInv IS NULL THEN 'P' ELSE 'N' END as noAplica   \n";
                        strSQL+=" FROM tbm_cabCotVen AS a1  \n";
                        strSQL+=" INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp)  \n";
                        strSQL+=" INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)  \n";
                        strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)  \n";
                        strSQL+=" INNER JOIN tbm_tipSolResInv as a5 ON (a1.co_tipSolResInv=a5.co_tipSolResInv AND a5.st_reg='A') \n";
                        strSQL+=" WHERE  a1.co_emp="+objParSis.getCodigoEmpresa()+"    \n";
                        strSQL+="       "+strConSQL+" \n";
                        strSQL+=" \n";
                        strSQL+=" AND a1.st_reg NOT IN ('I')  \n";
                        strSQL+=" ORDER BY a1.co_emp,a1.co_loc,a1.co_cot  ";
                    }
                }
                System.out.println("cargarDetalle \n" + strSQL);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                while(rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_NOM_EMP,rst.getString("tx_nomEmp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_NOM_LOC,rst.getString("tx_nomLoc"));
                        vecReg.add(INT_TBL_DAT_COD_COT,rst.getString("co_cot")==null?"":rst.getString("co_cot"));
                        vecReg.add(INT_TBL_DAT_FEC_COT,objUti.formatearFecha(rst.getDate("fe_cot"), "dd/MM/yyyy"));
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli")==null?"":rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nom")==null?"":rst.getString("tx_nom"));
                        vecReg.add(INT_TBL_DAT_TOT_COT,rst.getString("nd_tot")==null?"":rst.getString("nd_tot"));
                        vecReg.add(INT_TBL_DAT_BTN_COT_VEN,"..");
                        vecReg.add(INT_TBL_DAT_COD_TIP_SOL,rst.getString("co_tipSolResInv"));
                        vecReg.add(INT_TBL_DAT_BTN_TIP_SOL,"..");
                        vecReg.add(INT_TBL_DAT_TIP_SOL,rst.getString("tx_desLar") );
//                        if(rst.getString("fe_solResInv")!=null){
                        vecReg.add(INT_TBL_DAT_FEC_SOL, objUti.formatearFecha(rst.getDate("fe_solResInv"), "dd/MM/yyyy").equals("[ERROR]")?"":objUti.formatearFecha(rst.getDate("fe_solResInv"), "dd/MM/yyyy") );
//                        }
//                        else{
//                            vecReg.add(INT_TBL_DAT_FEC_SOL,"");
//                        }
                        vecReg.add(INT_TBL_DAT_CHK_SOL_RES,rst.getString("st_solResInv")==null?false:true   );
                        vecReg.add(INT_TBL_DAT_OBS_SOL_RES,rst.getString("tx_obsSolResInv"));
                        vecReg.add(INT_TBL_DAT_BTN_OBS_SOL_RES,"");
                        vecReg.add(INT_TBL_DAT_CHK_PED_OTR_BOD,rst.getString("st_pedOtrBod")==null?false:true );
                        vecReg.add(INT_TBL_DAT_BTN_PED_OTR_BOD,"");
                        vecReg.add(INT_TBL_DAT_CHK_SOL_ENV_PED,rst.getString("st_solEnvPed")==null?false:true);
                        if(rst.getString("st_autSolResInv")!=null){
                            if(rst.getString("st_autSolResInv").equals("P")){
                                vecReg.add(INT_TBL_DAT_CHK_PENDIENTE,true);
                                vecReg.add(INT_TBL_DAT_CHK_AUTORIZAR,false);
                                vecReg.add(INT_TBL_DAT_CHK_DENEGAR,false);
                            }else if(rst.getString("st_autSolResInv").equals("A")){
                                vecReg.add(INT_TBL_DAT_CHK_PENDIENTE,false);
                                vecReg.add(INT_TBL_DAT_CHK_AUTORIZAR,true);
                                vecReg.add(INT_TBL_DAT_CHK_DENEGAR,false);
                            }else /*if(rst.getString("st_autSolResInv").equals("D"))*/{
                                vecReg.add(INT_TBL_DAT_CHK_PENDIENTE,false);
                                vecReg.add(INT_TBL_DAT_CHK_AUTORIZAR,false);
                                vecReg.add(INT_TBL_DAT_CHK_DENEGAR,true);
                            }
                        }
                        else{
                            vecReg.add(INT_TBL_DAT_CHK_PENDIENTE,false);
                            vecReg.add(INT_TBL_DAT_CHK_AUTORIZAR,false);
                            vecReg.add(INT_TBL_DAT_CHK_DENEGAR,false);
                        }
                        vecReg.add(INT_TBL_DAT_CHK_AUT_ENV_PED,rst.getString("st_autEnvPed")==null?false:true );
                        vecReg.add(INT_TBL_DAT_FEC_SOL_FAC_AUT,objUti.formatearFecha(rst.getDate("fe_autSolResInv"), "dd/MM/yyyy").equals("[ERROR]")?"":objUti.formatearFecha(rst.getDate("fe_autSolResInv"), "dd/MM/yyyy")  );
                        vecReg.add(INT_TBL_DAT_OBS_AUT_RES,rst.getString("tx_obsAutSolResInv"));
                        vecReg.add(INT_TBL_DAT_BTN_OBS_AUT_RES,"");
                        vecReg.add(INT_TBL_DAT_VAL_FAC,rst.getString("nd_valSolResInvFac"));
                        vecReg.add(INT_TBL_DAT_BTN_LIS_FAC_VEN,"");
                        vecReg.add(INT_TBL_DAT_VAL_CAN,rst.getString("nd_valSolResInvCan"));
                        vecReg.add(INT_TBL_DAT_CHK_CANCELAR,"");
                        vecReg.add(INT_TBL_DAT_BUT_LIS_CAN,"");
//                        vecReg.add(INT_TBL_DAT_CHK_NOT_PRO_RES,rst.getString("noAplica").equals("P")?true:false);
//                        vecReg.add(INT_TBL_DAT_CHK_PEN_PRO_RES,rst.getString("pendiente").equals("P")?true:false);
//                        vecReg.add(INT_TBL_DAT_CHK_COM_PRO_RES,rst.getString("st_reg").equals("F")?true:false);
                        vecReg.add(INT_TBL_DAT_CHK_NOT_PRO_RES,getEstadoProceso(1 ,rst.getInt("co_emp"), rst.getInt("co_loc"),rst.getInt("co_cot")) );
                        vecReg.add(INT_TBL_DAT_CHK_PEN_PRO_RES,getEstadoProceso(2 ,rst.getInt("co_emp"), rst.getInt("co_loc"),rst.getInt("co_cot")));
                        vecReg.add(INT_TBL_DAT_CHK_COM_PRO_RES,getEstadoProceso(3 ,rst.getInt("co_emp"), rst.getInt("co_loc"),rst.getInt("co_cot")));
                        vecReg.add(INT_TBL_DAT_EST_SOL_RES,rst.getString("st_solResInv"));
                        vecReg.add(INT_TBL_DAT_MOM_DES_SOL_RES,rst.getString("tx_momDes"));
                        vecReg.add(INT_TBL_DAT_EST_FAC_PRI_DIA_LAB,rst.getString("st_facPriDiaLabSigMes"));  // JM 17/Jul/2017
                        vecReg.add(INT_TBL_DAT_STR_TIP_RES_INV,rst.getString("tx_tipResInv"));  // JM 17/Jul/2017
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
        objTblTot.calcularTotales();   
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
     * Metodo para obtener el estado del proceso
     * 
     * @param indiceProceso 1 NO TIENE PROCESO 2 EN PROCESO 3 COMPLETO
     * @return 
     */
    
    private boolean getEstadoProceso(int indiceProceso ,int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        try{ //null=No tiene;P=Pendiente;C=Completa
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp,a1.co_loc,a1.co_cot, \n";
                strSql+="        CASE WHEN a1.st_solResInv IS NULL THEN 'N' ELSE a1.st_solResInv END as st_solResInv  \n";
                strSql+=" FROM tbm_cabCotVen as a1 \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+"  \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    switch(indiceProceso){
                        case 1: 
                            if(rstLoc.getString("st_solResInv").equals("N")) 
                                blnRes=true; 
                            break;
                        case 2: 
                            if(rstLoc.getString("st_solResInv").equals("P"))
                                blnRes=true;
                            break;
                        case 3: 
                            if(rstLoc.getString("st_solResInv").equals("C"))
                                blnRes=true;
                            break;
                    }  
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            
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
    
    
    public boolean cargarRegCot(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=false;
        if(conExt!=null){
            blnCon=true;
            if(cargarDetReg(conExt,CodEmp,CodLoc,CodCot)){
                if (tblDat.getRowCount()>0){
                     tabFrm.setSelectedIndex(1);
                     blnRes=true;
                }
            }
        }
        return blnRes;
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    
    private boolean cargarDetReg(java.sql.Connection conn, int CodEmp, int CodLoc, int CodCot){
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
                strSQL=" ";
                strSQL+=" SELECT a1.co_emp, a2.tx_nom as tx_nomEmp,a1.co_loc,a3.tx_nom as tx_nomLoc,a1.co_cot,a1.fe_cot,a1.co_cli,  \n";
                strSQL+="        a4.tx_nom,ROUND(a1.nd_tot,2) as nd_tot, a1.st_reg,a1.st_solResInv,a1.co_tipSolResInv, a1.fe_solResInv, \n";
                strSQL+="        a1.tx_obsSolResInv, a1.st_pedOtrBod, a1.st_solEnvPed, a1.st_autSolResInv, a1.st_autEnvPed, a1.nd_valSolResInvFac, \n";
                strSQL+="        a1.nd_valSolResInvCan, a5.tx_desLar, a5.tx_tipResInv,a5.tx_momDes  , \n";
                strSQL+="        a1.fe_autSolResInv,a1.tx_obsAutSolResInv ,CASE WHEN a5.st_facPriDiaLabSigMes IS NULL THEN 'N' ELSE a5.st_facPriDiaLabSigMes END as st_facPriDiaLabSigMes \n";
                strSQL+=" FROM tbm_cabCotVen AS a1  \n";
                strSQL+=" INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp)  \n";
                strSQL+=" INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)  \n";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)  \n";
                strSQL+=" LEFT OUTER JOIN tbm_tipSolResInv as a5 ON (a1.co_tipSolResInv=a5.co_tipSolResInv AND a5.st_reg='A') \n";
                strSQL+=" WHERE  a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+"    \n";
                strSQL+=" ORDER BY a1.co_emp,a1.co_loc,a1.co_cot  ";
                strSQL+=" \n";
                System.out.println("cargarDetalle.Cotizaciones \n" + strSQL);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                while(rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_NOM_EMP,rst.getString("tx_nomEmp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_NOM_LOC,rst.getString("tx_nomLoc"));
                        vecReg.add(INT_TBL_DAT_COD_COT,rst.getString("co_cot")==null?"":rst.getString("co_cot"));
                        vecReg.add(INT_TBL_DAT_FEC_COT,objUti.formatearFecha(rst.getDate("fe_cot"), "dd/MM/yyyy"));
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli")==null?"":rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nom")==null?"":rst.getString("tx_nom"));
                        vecReg.add(INT_TBL_DAT_TOT_COT,rst.getString("nd_tot")==null?"":rst.getString("nd_tot"));
                        vecReg.add(INT_TBL_DAT_BTN_COT_VEN,"..");
                        vecReg.add(INT_TBL_DAT_COD_TIP_SOL,rst.getString("co_tipSolResInv"));
                        vecReg.add(INT_TBL_DAT_BTN_TIP_SOL,"..");
                        vecReg.add(INT_TBL_DAT_TIP_SOL,rst.getString("tx_desLar") );
                        vecReg.add(INT_TBL_DAT_FEC_SOL, objUti.formatearFecha(rst.getDate("fe_solResInv"), "dd/MM/yyyy").equals("[ERROR]")?"":objUti.formatearFecha(rst.getDate("fe_solResInv"), "dd/MM/yyyy") );
                        vecReg.add(INT_TBL_DAT_CHK_SOL_RES,rst.getString("st_solResInv")==null?false:true   );
                        vecReg.add(INT_TBL_DAT_OBS_SOL_RES,rst.getString("tx_obsSolResInv"));
                        vecReg.add(INT_TBL_DAT_BTN_OBS_SOL_RES,"");
                        vecReg.add(INT_TBL_DAT_CHK_PED_OTR_BOD,rst.getString("st_pedOtrBod")==null?false:true );
                        vecReg.add(INT_TBL_DAT_BTN_PED_OTR_BOD,"");
                        vecReg.add(INT_TBL_DAT_CHK_SOL_ENV_PED,rst.getString("st_solEnvPed")==null?false:true);
                        if(rst.getString("st_autSolResInv")!=null){
                            if(rst.getString("st_autSolResInv").equals("P")){
                                vecReg.add(INT_TBL_DAT_CHK_PENDIENTE,true);
                                vecReg.add(INT_TBL_DAT_CHK_AUTORIZAR,false);
                                vecReg.add(INT_TBL_DAT_CHK_DENEGAR,false);
                            }else if(rst.getString("st_autSolResInv").equals("A")){
                                vecReg.add(INT_TBL_DAT_CHK_PENDIENTE,false);
                                vecReg.add(INT_TBL_DAT_CHK_AUTORIZAR,true);
                                vecReg.add(INT_TBL_DAT_CHK_DENEGAR,false);
                            }else /*if(rst.getString("st_autSolResInv").equals("D"))*/{
                                vecReg.add(INT_TBL_DAT_CHK_PENDIENTE,false);
                                vecReg.add(INT_TBL_DAT_CHK_AUTORIZAR,false);
                                vecReg.add(INT_TBL_DAT_CHK_DENEGAR,true);
                            }
                        }else{
                            vecReg.add(INT_TBL_DAT_CHK_PENDIENTE,false);
                            vecReg.add(INT_TBL_DAT_CHK_AUTORIZAR,false);
                            vecReg.add(INT_TBL_DAT_CHK_DENEGAR,false);
                        }
                        vecReg.add(INT_TBL_DAT_CHK_AUT_ENV_PED,rst.getString("st_autEnvPed")==null?false:true);
                        vecReg.add(INT_TBL_DAT_FEC_SOL_FAC_AUT, objUti.formatearFecha(rst.getDate("fe_autSolResInv"), "dd/MM/yyyy").equals("[ERROR]")?"":objUti.formatearFecha(rst.getDate("fe_autSolResInv"), "dd/MM/yyyy")  );
                        vecReg.add(INT_TBL_DAT_OBS_AUT_RES,rst.getString("tx_obsAutSolResInv"));
                        vecReg.add(INT_TBL_DAT_BTN_OBS_AUT_RES,"");
                        vecReg.add(INT_TBL_DAT_VAL_FAC,rst.getString("nd_valSolResInvFac"));
                        vecReg.add(INT_TBL_DAT_BTN_LIS_FAC_VEN,"");
                        vecReg.add(INT_TBL_DAT_VAL_CAN,rst.getString("nd_valSolResInvCan"));
                        vecReg.add(INT_TBL_DAT_CHK_CANCELAR,"");
                        vecReg.add(INT_TBL_DAT_BUT_LIS_CAN,"");
                        vecReg.add(INT_TBL_DAT_CHK_NOT_PRO_RES,false);
                        vecReg.add(INT_TBL_DAT_CHK_PEN_PRO_RES,false);
                        vecReg.add(INT_TBL_DAT_CHK_COM_PRO_RES,false);
                        vecReg.add(INT_TBL_DAT_EST_SOL_RES,rst.getString("st_solResInv"));
                        vecReg.add(INT_TBL_DAT_MOM_DES_SOL_RES,rst.getString("tx_momDes"));
                        vecReg.add(INT_TBL_DAT_EST_FAC_PRI_DIA_LAB,rst.getString("st_facPriDiaLabSigMes"));
                        vecReg.add(INT_TBL_DAT_STR_TIP_RES_INV,rst.getString("tx_tipResInv"));
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
     * filtroChecks
     * 
     * @return Un string con los filtros adecuados
     */
    
    private String filtroChecks(){
        String strConSQL="";
        try{
            /* CHECKS */
            /* Estado de la Solicitud null=No tiene;P=Pendiente;C=Completa */
            if(chkEstSolNoTiene.isSelected() || chkEstSolPendiente.isSelected() || chkEstSolCompleta.isSelected()){
                strConSQL+=" AND ( ";
                if(chkEstSolNoTiene.isSelected()){
                    strConSQL+=" a1.st_solResInv  IS NULL ";
                    if(chkEstSolPendiente.isSelected() || chkEstSolCompleta.isSelected()){
                        strConSQL+=" OR ";
                    }
                }
                if(chkEstSolPendiente.isSelected()){
                    strConSQL+=" a1.st_solResInv like 'P' ";
                    if(chkEstSolCompleta.isSelected()){
                        strConSQL+=" OR ";
                    }
                }
                if(chkEstSolCompleta.isSelected()){
                    strConSQL+=" a1.st_solResInv like 'C' ";  
                }
                strConSQL+=" )";
            }
            /* Autorizacion de la Solicitud  null=No aplica;P=Pendiente;A=Autorizada;D=Denegada */
            if(chkAutSolPendiente.isSelected() || chkAutSolAutorizada.isSelected() || chkAutSolDenegada.isSelected()){
                strConSQL+=" AND ( ";
                if(chkAutSolPendiente.isSelected()){
                    strConSQL+=" a1.st_autSolResInv like 'P' ";
                    if(chkAutSolAutorizada.isSelected() || chkAutSolDenegada.isSelected()){
                        strConSQL+=" OR ";
                    }
                }
                if(chkAutSolAutorizada.isSelected()){
                    strConSQL+=" a1.st_autSolResInv like 'A' ";
                    if(chkAutSolDenegada.isSelected()){
                        strConSQL+=" OR ";
                    }
                }
                if(chkAutSolDenegada.isSelected()){
                    strConSQL+=" a1.st_autSolResInv like 'D' ";  
                }
                strConSQL+=" )";
            }
            /* Factura de la soicitud */
            if(chkFacSolPendiente.isSelected() || chkFacSolParcial.isSelected() || chkFacSolTotal.isSelected()){
                strConSQL+=" AND ( ";
                if(chkFacSolPendiente.isSelected()){
                    strConSQL+=" a1.nd_valSolResInvFac IS NULL ";
                    if(chkFacSolParcial.isSelected() || chkFacSolTotal.isSelected()){
                        strConSQL+=" OR ";
                    }
                }
                if(chkFacSolParcial.isSelected()){
                    strConSQL+=" a1.nd_valSolResInvFac < a1.nd_tot ";
                    if(chkFacSolTotal.isSelected()){
                        strConSQL+=" OR ";
                    }
                }
                if(chkFacSolTotal.isSelected()){
                    strConSQL+=" a1.nd_valSolResInvFac = a1.nd_tot ";  
                }
                strConSQL+=" )";
            }
            /* Cancelacion de la soicitud */
            if(chkCanSolNoTiene.isSelected() || chkCanSolParcial.isSelected() || chkCanSolTotal.isSelected()){
                strConSQL+=" AND ( ";
                if(chkCanSolNoTiene.isSelected()){
                    strConSQL+=" a1.nd_valSolResInvCan IS NULL ";
                    if(chkCanSolParcial.isSelected() || chkCanSolTotal.isSelected()){
                        strConSQL+=" OR ";
                    }
                }
                if(chkCanSolParcial.isSelected()){
                    strConSQL+=" a1.nd_valSolResInvCan < a1.nd_tot ";
                    if(chkCanSolTotal.isSelected()){
                        strConSQL+=" OR ";
                    }
                }
                if(chkCanSolTotal.isSelected()){
                    strConSQL+=" a1.nd_valSolResInvCan = a1.nd_tot ";  
                }
                strConSQL+=" )";
            }
        }
        catch (Exception e){
            strConSQL="ERROR!! ";
            System.err.println("FILTROS CHECKS");
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strConSQL;
        
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
                case INT_TBL_DAT_NOM_EMP: strMsg="Nombre de la Empresa"; break;
                case INT_TBL_DAT_COD_LOC: strMsg="Código del Local"; break;
                case INT_TBL_DAT_NOM_LOC: strMsg="Nombre del Local"; break;
                case INT_TBL_DAT_COD_COT: strMsg="Código de la Cotización"; break;
                case INT_TBL_DAT_FEC_COT: strMsg="Fecha de la Cotización"; break;
                case INT_TBL_DAT_COD_CLI: strMsg="Código del Cliente"; break;
                case INT_TBL_DAT_NOM_CLI: strMsg="Nombre del Cliente"; break;
                case INT_TBL_DAT_TOT_COT: strMsg="Valor del documento"; break;
                case INT_TBL_DAT_BTN_COT_VEN: strMsg="Muestra la Cotización de ventas"; break;
                case INT_TBL_DAT_BTN_TIP_SOL: strMsg="Muestra el Listado de tipos de solicitudes de reservas de inventario"; break;
                case INT_TBL_DAT_TIP_SOL: strMsg="Tipo de solicitud de reserva de inventario"; break;
                case INT_TBL_DAT_FEC_SOL: strMsg="Fecha solicitada"; break;
                case INT_TBL_DAT_CHK_SOL_RES: strMsg="JM: Esta reservando!!"; break;
                case INT_TBL_DAT_OBS_SOL_RES: strMsg="Observación de la reserva"; break;
                case INT_TBL_DAT_BTN_OBS_SOL_RES: strMsg="Muestra la Observación de la reserva"; break;
                case INT_TBL_DAT_CHK_PED_OTR_BOD: strMsg="La solicitud tiene pedido a otras bodegas"; break;
                case INT_TBL_DAT_BTN_PED_OTR_BOD: strMsg="Muestra los pedidos que tenga una reserva"; break;
                case INT_TBL_DAT_CHK_SOL_ENV_PED: strMsg="Solicitar envíar pedido a otras bodegas al autorizar la reserva de inventario"; break;
                case INT_TBL_DAT_CHK_PENDIENTE: strMsg="Pendiente"; break;
                case INT_TBL_DAT_CHK_AUTORIZAR: strMsg="Autorizar"; break;
                case INT_TBL_DAT_CHK_DENEGAR: strMsg="Denegar"; break;    
                case INT_TBL_DAT_CHK_AUT_ENV_PED: strMsg="Autorizar enviar pedido a otras bodegas al autorizar la reserva de inventario"; break;
                case INT_TBL_DAT_FEC_SOL_FAC_AUT: strMsg="Fecha autorizada"; break;
                case INT_TBL_DAT_OBS_AUT_RES: strMsg="Observación de la autorización"; break;
                case INT_TBL_DAT_BTN_OBS_AUT_RES: strMsg="Muestra la Observación de la autorización"; break;
                case INT_TBL_DAT_VAL_FAC: strMsg="Valor facturado"; break;
                case INT_TBL_DAT_BTN_LIS_FAC_VEN: strMsg="Muestra el listado de Facturas de ventas"; break;
                case INT_TBL_DAT_VAL_CAN: strMsg="Valor cancelado"; break;
                case INT_TBL_DAT_CHK_CANCELAR: strMsg="Cancelar"; break;
                case INT_TBL_DAT_BUT_LIS_CAN: strMsg="Muestra el listado de cancelaciones"; break;
                case INT_TBL_DAT_CHK_NOT_PRO_RES: strMsg="No tiene solicitud de reserva"; break;
                case INT_TBL_DAT_CHK_PEN_PRO_RES: strMsg="El proceso está pendiente"; break;
                case INT_TBL_DAT_CHK_COM_PRO_RES: strMsg="El proceso está completo"; break;
                default: strMsg=""; break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
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
                if (!cargarDetReg(con)){
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);         
                    butCon.setText("Consultar");

                }
                //Establecer el foco en el JTable sólo cuando haya datos.
                if (tblDat.getRowCount()>0){
                     tabFrm.setSelectedIndex(1);
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
    
    
    private boolean blnCotVen=false, blnIsCotVen = false;
    
    private class ZafThrGua extends Thread{
        public void run(){
            try{
                if(objParSis.getCodigoMenu()==4142){
                    if (!guardarSolicitudReserva()){
                        lblMsgSis.setText("Listo");
                        pgrSis.setValue(0);         
                        
                    }
                }
                else if(objParSis.getCodigoMenu()==4146 || blnIsCotVen){
                    if (!guardarAutorizacionReserva()){
                        lblMsgSis.setText("Listo");
                        pgrSis.setValue(0);         
                    }
                }
                butCon.doClick();
                objThrGua=null;
                
            }
            catch (Exception e){
                System.err.println(e);
            }
        }
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

 
 
    private class ButCotVen extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButCotVen(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Cotizaciones de Venta.");
           
        }
        @Override
        public void butCLick() {
           int intCol = tblDat.getSelectedRow();
           String strCodEmp = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_EMP  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_EMP  ).toString());
           String strCodLoc = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_LOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_LOC  ).toString());
           String strCodCot = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_COT  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_COT  ).toString());
          llamarVen(strCodEmp, strCodLoc, strCodCot);
        }
    }
    
     
    private void llamarVen(String strCodEmp, String strCodLoc, String strCodCot){
            ZafVen01 obj1 = new  Ventas.ZafVen01.ZafVen01(objParSis, strCodEmp, strCodLoc, strCodCot);
            this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj1.show();
    }
 
 
    private class ButObsSolRes extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButObsSolRes(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Observacion.");
           
        }
        @Override
        public void butCLick() {
            int intCol = tblDat.getSelectedRow();
            String strObs = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_OBS_SOL_RES  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_DAT_OBS_SOL_RES  ).toString());
            llamarVenObs(strObs, intCol);
        }
    }
 
 
    private void llamarVenObs(String strObs, int intCol){
            ZafVen42_01 obj1 = new  ZafVen42_01(javax.swing.JOptionPane.getFrameForComponent(this), true , strObs );
            obj1.show();
            if(obj1.getAceptar())
              tblDat.setValueAt( obj1.getObser(), intCol, INT_TBL_DAT_OBS_SOL_RES );  // << JOTA JOTA JOTA 
           obj1=null; 
    }
    
    
    
            
    private class ButObsAutResVen extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButObsAutResVen(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Observacion.");
           
        }
        @Override
        public void butCLick() {
            int intCol = tblDat.getSelectedRow();
            String strObs = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_OBS_AUT_RES  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_DAT_OBS_AUT_RES  ).toString());
            llamarVenObsAut(strObs, intCol);
        }
    }
 
 
    private void llamarVenObsAut(String strObs, int intCol){
            ZafVen42_02 obj1 = new  ZafVen42_02(javax.swing.JOptionPane.getFrameForComponent(this), true , strObs );
            obj1.show();
            if(obj1.getAceptar())
              tblDat.setValueAt( obj1.getObser(), intCol, INT_TBL_DAT_OBS_AUT_RES );
           obj1=null; 
    }
    
     
 
 
    private void llamarFacVen(String strCodEmp, String strCodLoc, String strCodCot){
            ZafVen42_09 obj1 = new  ZafVen42_09(objParSis,this, Integer.parseInt(strCodEmp),Integer.parseInt(strCodLoc) ,Integer.parseInt(strCodCot) );
            this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj1.show();
    }
    
    
     /**
      * Listado de facturas de Venta
      * 
      */
     
      private class ButLisFacVen extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButLisFacVen(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Factura de Venta.");
           
        }
        @Override
        public void butCLick() {
            int intCol = tblDat.getSelectedRow();
            String strCodEmp = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_EMP  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_EMP  ).toString());
            String strCodLoc = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_LOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_LOC  ).toString());
            String strCodCot = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_COT  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_COT  ).toString());
          // OBTENER FACTURAS SEGUN LA COTIZACION PUEDE TENER MUCHAS FACTURAS
           llamarFacVen(strCodEmp, strCodLoc, strCodCot);
        }
    }
    
    private ArrayList arlDatRes, arlDatCodL;
   
   /**
    * 
    * @return 
    **/
    
    private boolean guardarSolicitudReserva(){
        java.sql.Connection conLoc;
        boolean blnRes=true;
        try{
            pgrSis.setIndeterminate(true);
            int intNumFil=objTblMod.getRowCountTrue(), intCodEmp, intCodLoc, intCodCot;
            for (int i=0; i<intNumFil;i++){
                intCodEmp=Integer.parseInt(tblDat.getValueAt(i,INT_TBL_DAT_COD_EMP).toString() );
                intCodLoc=Integer.parseInt(tblDat.getValueAt(i,INT_TBL_DAT_COD_LOC).toString() );
                intCodCot=Integer.parseInt(tblDat.getValueAt(i,INT_TBL_DAT_COD_COT).toString() );
                if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_SOL_RES) && tblDat.getValueAt(i,INT_TBL_DAT_EST_SOL_RES)==null){
                    if(validacionesSolicitarReserva(i,intCodEmp, intCodLoc, intCodCot, true)){
                        arlDatRes = new ArrayList();
                        conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                        if(conLoc!=null){
                            conLoc.setAutoCommit(false);
                            if(tblDat.getValueAt(i,INT_TBL_DAT_STR_TIP_RES_INV).toString().equals("F")){
                                if(guardarSolicitudReservaFacturaAutomatica(conLoc,i ,intCodEmp,intCodLoc, intCodCot)){
                                    System.out.println("Reserva por Factura Automatica");
                                }else{blnRes=false;}
                            }else if(tblDat.getValueAt(i,INT_TBL_DAT_STR_TIP_RES_INV).toString().equals("R")){
                                if(guardarSolicitudReservaEmpresa(conLoc,i ,intCodEmp,intCodLoc, intCodCot)){
                                    System.out.println("Reserva normal");
                                }else{blnRes=false;}
                            }
                            if(blnRes==true){
                                System.out.println("ok commit");
                                conLoc.commit();
                            }else{
                                System.out.println("NOOOOO!!! no guarda rollback");
                                conLoc.rollback();
                            }
                            blnRes=true;
                            conLoc.close();
                            conLoc=null;
                        }
                        blnRes=false;
                    }
                }
            }
            
            blnRes=true;
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
     * 
     * @param conExt
     * @param indice
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
      @return 
     */
    private boolean guardarSolicitudReservaEmpresa(java.sql.Connection conExt,int indice ,int CodEmp,int CodLoc, int CodCot){
        boolean blnRes=true;
        try{
            if(conExt!=null){
                /* PASO 1: Ventana de Pedidos a otras bodegas */
                if(existenTerminalesPedidos(CodEmp, CodLoc, CodCot)){
                    ZafVen42_03 objVen42_03 = new ZafVen42_03(javax.swing.JOptionPane.getFrameForComponent(this), objParSis,CodEmp,CodLoc,CodCot);
                    objVen42_03.show();
                    if(objVen42_03.acepta()){
                        arlDatRes = objVen42_03.getArlDatRes();
                        if(!arlDatRes.isEmpty()){
                            if(modificaChecksPedOtrBod(CodEmp,CodLoc)){
                                objTblMod.setValueAt(true, indice, INT_TBL_DAT_CHK_PED_OTR_BOD); 
                                objTblMod.setValueAt(true, indice, INT_TBL_DAT_CHK_SOL_ENV_PED);  /*JM: La mercaderia se envia siempre al punto de facturacion */
                            }
                        }else{blnRes=false;}
                    }else{blnRes=false;}
                    objVen42_03 = null;
                }
                /* PASO 4: Grabar configuracion y guarda en la tabla de pedidos a otras bodegas */
                if(blnRes){
                    if(modificarDatosDetallesCotizacionBodegasReserva(conExt,CodEmp,CodLoc,CodCot,indice)){  // DETALLE DE LA COTIZACION
                        if(pedidosBodegaDestino(conExt,CodEmp,CodLoc,CodCot)){
//                            if(terminalesStockLocal(conExt,CodEmp,CodLoc,CodCot)){
                                System.out.println("ok prestamos ");
//                            }else{blnRes=false;MensajeInf("Error al Guardar: Insertando pedidos Otras bodegas 3"); }
                        }else{blnRes=false;MensajeInf("Error al Guardar: Insertando pedidos Otras bodegas 2"); }
                    }else{blnRes=false;MensajeInf("Error al Guardar: Insertando pedidos Otras bodegas"); }
                }
                 /* PASO 5: Tabla grande nueva, items segun la forma de despacho */
                if(blnRes){
                    if(existenTerminalesPedidos(CodEmp, CodLoc, CodCot)){
                        ZafVen42_08 objVen42_08 = new ZafVen42_08(javax.swing.JOptionPane.getFrameForComponent(this), objParSis,conExt,CodEmp,CodLoc,CodCot);
                        objVen42_08.show();
                        if (objVen42_08.acepta()){
                            strSql=objVen42_08.getCadenaInsertBodegasDespacho();
                            if(strSql.length()>0){
                                if(modificarDatosBodegaDespacho(conExt,strSql)){
                                    System.out.println("ok Configuracion/Modificacion Despachos ");
                                }else{blnRes=false;MensajeInf("Error al Guardar: Modificacion en los datos del despacho");}
                            }else{blnRes=false;}
                        }
                        else{
                            blnRes=false;
                            MensajeInf("Error al Guardar: Modificacion en los datos del despacho");
                        }
                        objVen42_08 = null;
                    }
                }
                
                /* PASO 6: Modificacion en el estado de la cotizaicon*/
                if(blnRes){
                    if(modificarEstadoCotizacion(conExt,CodEmp,CodLoc,CodCot,indice)){
                       System.out.println("ok modificarEstadoCotizacion");
                    }
                    else{
                        MensajeInf("Error al Guardar: Modificacion de la Cotizacion de venta");
                        blnRes=false;
                    }
                }
            }
        }
        catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
           blnRes=false;
        } 
        return blnRes;
    }
    
    /**
     * Metodo para procesar todas las reservas que generen una factura automatica
     * @param conExt
     * @param indice
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
    private boolean guardarSolicitudReservaFacturaAutomatica(java.sql.Connection conExt,int indice ,int CodEmp,int CodLoc, int CodCot ){
        boolean blnRes=true;
        try{
            if(conExt!=null){
                if(solicitarAutorizacionCxC(CodEmp, CodLoc, CodCot)){
                     /* PASO 1: Ventana de Pedidos a otras bodegas */
                    if(existenTerminalesPedidos(CodEmp, CodLoc, CodCot)){
                        ZafVen42_03 objVen42_03 = new ZafVen42_03(javax.swing.JOptionPane.getFrameForComponent(this), objParSis,CodEmp,CodLoc,CodCot);
                        objVen42_03.show();
                        if(objVen42_03.acepta()){
                            arlDatRes = objVen42_03.getArlDatRes();
                            if(!arlDatRes.isEmpty()){
                                if(modificaChecksPedOtrBod(CodEmp,CodLoc)){
                                    objTblMod.setValueAt(true, indice, INT_TBL_DAT_CHK_PED_OTR_BOD); 
                                }
                            }else{blnRes=false;}
                        }else{blnRes=false;}
                        objVen42_03 = null;
                    }

                    if(blnRes){
                        /* PASO 2: Codigos L */
                        if(existenCodigosL(CodEmp, CodLoc, CodCot)){
                            arlDatCodL = new ArrayList();
                            ZafVen42_04 objVen42_04 = new ZafVen42_04(javax.swing.JOptionPane.getFrameForComponent(this), objParSis,CodEmp,CodLoc,CodCot);
                            objVen42_04.show();
                            if (objVen42_04.acepta()) {
                                arlDatCodL = objVen42_04.getArlDatResTerL();
                                if(!arlDatCodL.isEmpty()){
                                    if(modificarCotizacionCompras(conExt)){
                                        System.out.println("ok L ");
                                    }else{ blnRes = false;MensajeInf("Error al Guardar: Modificando los codigo L");}
                                }else{blnRes = false;}
                            }else{blnRes = false;}
                            objVen42_04 = null;
                        }
                    }
                    /* PASO 3: Configuracion de Despachos por cliente fue inactivada */
                    // PENDIENTE DE SALIR -- JM 13/Jul/2017
//                    if(blnRes){
//                        if(!revisarDespachosCliente(CodEmp, CodLoc, CodCot)){
//                            ZafVen42_06 objVen42_06 = new ZafVen42_06(javax.swing.JOptionPane.getFrameForComponent(this), objParSis,intCodEmp,intCodLoc,intCodCot,arlDatRes);
//                            objVen42_06.show();
//                            if(objVen42_06.acepta()){
//                                System.out.println("ok Configuracion despachos ");
//                            }
//                            else{
//                                MensajeInf("Error al Guardar: Insertando los datos del despacho");
//                                blnRes=false;
//                            }
//                            objVen42_06 = null;
//                        }
//                    }
                    // PENDIENTE DE SALIR -- JM 13/Jul/2017 
                    /* PASO 4: Grabar configuracion y guarda en la tabla de pedidos a otras bodegas */
                    if(blnRes){
                        if(modificarDatosDetallesCotizacionBodegasReserva(conExt,CodEmp,CodLoc,CodCot,indice)){  // DETALLE DE LA COTIZACION
                            if(pedidosBodegaDestino(conExt,CodEmp,CodLoc,CodCot)){
//                                if(terminalesStockLocal(conExt,CodEmp,CodLoc,CodCot)){  /* NO EXISTE ESTO es un terminal que debe estar configurado */
                                    System.out.println("ok prestamos ");
//                                }else{blnRes=false;MensajeInf("Error al Guardar: Insertando pedidos Otras bodegas 3"); }
                            }else{blnRes=false;MensajeInf("Error al Guardar: Insertando pedidos Otras bodegas 2"); }
                        }else{blnRes=false;MensajeInf("Error al Guardar: Insertando pedidos Otras bodegas"); }
                    }
                     /* PASO 5: Tabla grande nueva, items segun la forma de despacho */
                    if(blnRes){
                        if(existenTerminalesPedidos(CodEmp, CodLoc, CodCot)){
                            ZafVen42_07 objVen42_07 = new ZafVen42_07(javax.swing.JOptionPane.getFrameForComponent(this), objParSis,conExt,CodEmp,CodLoc,CodCot);
                            objVen42_07.show();
                            if (objVen42_07.acepta()){
                                strSql=objVen42_07.getCadenaInsertBodegasDespacho();
                                if(strSql.length()>0){
                                    if(modificarDatosBodegaDespacho(conExt,strSql)){
                                        System.out.println("ok Configuracion/Modificacion Despachos ");
                                    }else{blnRes=false;MensajeInf("Error al Guardar: Modificacion en los datos del despacho");}
                                }else{blnRes=false;}
                            }
                            else{
                                blnRes=false;
                                MensajeInf("Error al Guardar: Modificacion en los datos del despacho");
                            }
                            objVen42_07 = null;
                        }
                    }

                    /* PASO 6: Modificacion en el estado de la cotizaicon*/
                    if(blnRes){
                        if(modificarEstadoCotizacion(conExt,CodEmp,CodLoc,CodCot,indice)){
                           System.out.println("ok modificarEstadoCotizacion");
                        }
                        else{
                            MensajeInf("Error al Guardar: Modificacion de la Cotizacion de venta");
                            blnRes=false;
                        }
                    }
                }
                else{
                    MensajeInf("Error al Guardar: Controles de CxC");
                    blnRes=false;
                }
            }
        }
        catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
           blnRes=false;
        } 
        return blnRes;
    }
    
    /**
     * 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
    private boolean pedidosBodegaDestino(java.sql.Connection conExt,int CodEmp,int CodLoc,int CodCot){
        boolean blnRes=false;
        java.sql.Statement stmLoc,stmLoc01,stmLoc02;
        java.sql.ResultSet rstLoc, rstLoc01;
        int intCodBodPre=-1;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();stmLoc01 = conExt.createStatement();stmLoc02 = conExt.createStatement();
                intCodBodPre = bodegaPredeterminadaPorEmpresa(CodEmp,CodLoc);
                strSql="";
                strSql+=" SELECT a2.co_emp,a2.co_loc,a2.co_cot,a2.co_reg,a2.co_itm,a2.nd_can,a4.co_empRel, a4.co_bodRel/*,a7.tx_forDes*/ \n";
                strSql+=" FROM tbm_cabCotVen as a1  \n";
                strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                strSql+=" INNER JOIN tbm_pedOtrBodCotVen as a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND \n";
                strSql+="                                          a2.co_cot=a4.co_cot AND a2.co_reg=a4.co_reg ) \n";
                strSql+=" INNER JOIN tbr_bodloc as a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a5.st_reg='P') \n";
                strSql+=" INNER JOIN tbr_bodEmpBodGrp as a6 ON (a5.co_emp=a6.co_emp AND a5.co_bod=a6.co_bod)  \n";
//                strSql+=" INNER JOIN tbm_forDesCliBod as a7 ON (a1.co_emp=a7.co_emp AND a1.co_loc=a7.co_loc AND a1.co_cli=a7.co_cli AND \n";
//                strSql+="                                       a6.co_empGrp=a7.co_empRel AND a6.co_bodGrp=a7.co_bodRel) \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" AND a3.st_ser='N' \n";
                strSql+=" ORDER BY a2.co_emp,a2.co_loc,a2.co_cot,a2.co_reg  \n";
                System.out.println("pedidosBodegaDestino 1:" + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    strSql="";
                    strSql+=" SELECT a2.co_emp,a2.co_loc,a2.co_cot,a2.co_reg,a2.co_itm,a2.nd_can ,a3.co_empRel, a3.co_bodRel, a3.co_itmRel \n";
                    strSql+=" FROM tbm_cabCotVen as a1  \n";
                    strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot)   \n";
                    strSql+=" INNER JOIN tbm_pedOtrBodCotVen as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_cot=a3.co_cot AND    \n";
                    strSql+="                                          a2.co_reg=a3.co_reg /*AND a2.co_emp=a3.co_empRel AND a2.co_itm=a3.co_itmRel*/ ) \n";
                    strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" AND a2.co_reg="+rstLoc.getInt("co_reg") +"   \n";
//JM 12/Jun/2017            //  strSql+="      AND  ((a3.co_empRel="+CodEmp+" AND  a3.co_bodRel<>"+intCodBodPre+" ) OR (a3.co_empRel<>"+CodEmp+") )  \n";
                    System.out.println("pedidosBodegaDestino 2:" + strSql);
                    rstLoc01=stmLoc01.executeQuery(strSql);
                    if(rstLoc01.next()){
                        strSql="";
                        strSql+=" UPDATE tbm_pedOtrBodCotVen SET  ";
//                        if(rstLoc.getString("tx_forDes").equals("E")){
                            strSql+=" nd_bodDesCanEnvCli= nd_can ,nd_bodDesCanCliRet=null \n";
//                        }
//                        else{
//                            strSql+=" nd_bodDesCanEnvCli=null ,nd_bodDesCanCliRet=nd_can \n";
//                        }
                        strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+" AND co_reg="+rstLoc.getInt("co_reg")+" AND  ";
                        strSql+="       co_empRel="+rstLoc01.getInt("co_empRel") +" AND co_bodRel="+rstLoc01.getInt("co_bodRel")+" AND co_itmRel="+rstLoc01.getInt("co_itmRel")+" ;  ";
                        System.out.println("pedidosBodegaDestino 3:" + strSql);
                        stmLoc02.executeUpdate(strSql);
                    }
//                    else{
//                        strSql="";
//                        strSql+=" INSERT INTO tbm_pedOtrBodCotVen (co_emp,co_loc,co_cot,co_reg,co_empRel,co_bodRel,co_itmRel,nd_can,";
//                        strSql+="                                  st_necAut,st_pedOtrBod, nd_bodDesCanEnvCli, nd_bodDesCanCliRet ) ";
//                        strSql+=" VALUES ( "+CodEmp+","+CodLoc+","+CodCot+","+rstLoc.getInt("co_reg")+",";
//                        strSql+=" "+CodEmp+",";
//                        strSql+=" "+intCodBodPre+",";
//                        strSql+=" "+rstLoc.getInt("co_itm")+",";
//                        strSql+=" "+rstLoc.getInt("nd_can")+",'N',";// st_necAut
//                        strSql+=" NULL,";  // st_pedOtrBod
//                        if(rstLoc.getString("tx_forDes").equals("E")){
//                            strSql+=" "+rstLoc.getInt("nd_can")+", null ";
//                        }
//                        else{
//                            strSql+="  null,"+rstLoc.getInt("nd_can")+" ";
//                        }
//                        strSql+="); \n";
//                    }
                    rstLoc01.close();
                    rstLoc01=null;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();stmLoc01.close();stmLoc02.close();
                stmLoc=null;stmLoc01=null;stmLoc02=null;
                blnRes=true;
            }
        }
        catch(java.sql.SQLException Evt){ 
            objUti.mostrarMsgErr_F1(null, Evt); 
            blnRes=false;
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(null, Evt);  
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * 
     * 
     * @param conExt
     * @param strIns
     * @return 
     */
    
    private boolean modificarDatosBodegaDespacho(java.sql.Connection conExt, String strIns){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                stmLoc.executeUpdate(strIns);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }
        catch(java.sql.SQLException Evt){ 
            objUti.mostrarMsgErr_F1(null, Evt); 
            blnRes=false;
        }
        catch(Exception  Evt){ 
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }
    
    /**
     * 
     * 
     * @param intCodEmpCot
     * @param intCodLocCot
     * @return 
     */
    
    private boolean modificaChecksPedOtrBod(int intCodEmpCot, int intCodLocCot){
        boolean blnRes=false;
        int intCodBodPreGrp=-1;
        try{
            intCodBodPreGrp = bodegaPredeterminadaGrupo(intCodEmpCot,intCodLocCot);
            for(int i=0;i<arlDatRes.size();i++){
                if(intCodBodPreGrp!=objUti.getIntValueAt(arlDatRes, i, INT_ARL_COD_BOD_EGR_GRP)){
                    blnRes=true;
                }
            }
        }
        catch(Exception  Evt){ 
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }
    
    /**
     * 
     * @param intCodEmp
     * @param intCodLoc
     * @return 
     */
    
    private int bodegaPredeterminadaGrupo(int intCodEmp, int intCodLoc){
        int intBodPre=-1;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc; 
        java.sql.ResultSet rstLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql=" SELECT a2.co_emp, a2.co_bod, a2.co_empGrp, a2.co_bodGrp ";
                strSql+=" FROM tbr_bodloc as a1 ";
                strSql+=" INNER JOIN tbr_bodEmpBodGrp as a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod) ";
                strSql+=" WHERE a1.co_loc="+intCodLoc+" and a1.st_reg='P' AND ";
                strSql+=" a1.co_emp="+intCodEmp+" ";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intBodPre = rstLoc.getInt("co_bodGrp");
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch(java.sql.SQLException Evt){ 
            objUti.mostrarMsgErr_F1(null, Evt); 
            intBodPre=-1;
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(null, Evt);  
            intBodPre=-1;
        }
        return intBodPre;
    }
    
    /**
     * 
     * @param intCodEmp
     * @param intCodLoc
     * @return 
     */
    
    private int bodegaPredeterminadaPorEmpresa(int intCodEmp, int intCodLoc){
        int intBodPre=-1;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc; 
        java.sql.ResultSet rstLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql=" SELECT a1.co_emp, a1.co_bod ";
                strSql+=" FROM tbr_bodloc as a1 ";
                strSql+=" WHERE a1.co_loc="+intCodLoc+" and a1.st_reg='P' AND ";
                strSql+=" a1.co_emp="+intCodEmp+" ";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intBodPre = rstLoc.getInt("co_bod");
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch(java.sql.SQLException Evt){ 
            objUti.mostrarMsgErr_F1(null, Evt); 
            intBodPre=-1;
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(null, Evt);  
            intBodPre=-1;
        }
        return intBodPre;
    }
    
    /**
     * 
     * 
     * 
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
    private boolean revisarDespachosCliente(int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;       
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                 for(int i=0;i<arlDatRes.size();i++){
                    strSql="";
                    strSql+=" SELECT a2.co_emp, a2.co_loc, a2.co_cli, a2.co_empRel, a2.co_bodRel, a2.co_bodRel, a2.tx_forDes  \n";
                    strSql+=" FROM tbm_cabCotVen as a1 \n";
                    strSql+=" INNER JOIN tbr_bodloc as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a4.st_reg='P')  \n";
                    strSql+=" INNER JOIN tbr_bodEmpBodGrp as a5 ON (a4.co_emp=a5.co_emp AND a4.co_bod=a5.co_bod) \n";
                    strSql+=" INNER JOIN tbm_forDesCliBod as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cli=a2.co_cli AND \n";
                    strSql+="                                       "+objParSis.getCodigoEmpresaGrupo()+"=a2.co_empRel AND a5.co_bodGrp=a2.co_bodRel) ";
                    strSql+=" WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+"  \n";
                    //System.out.println("revisarDespachosCliente: " + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    if(!rstLoc.next()){
                         blnRes=false;
                    }
                 }
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
    
     
    
    private class ButLisPedOtrBod extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButLisPedOtrBod(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Listado de Items con Pedidos a otras bodegas");
           
        }
        @Override
        public void butCLick() {
            int intRow = tblDat.getSelectedRow();
            int CodEmp = Integer.parseInt( tblDat.getValueAt(intRow,  INT_TBL_DAT_COD_EMP  )==null?"":tblDat.getValueAt(intRow,  INT_TBL_DAT_COD_EMP  ).toString());
            int CodLoc = Integer.parseInt( tblDat.getValueAt(intRow,  INT_TBL_DAT_COD_LOC  )==null?"":tblDat.getValueAt(intRow,  INT_TBL_DAT_COD_LOC  ).toString());
            int CodCot = Integer.parseInt( tblDat.getValueAt(intRow,  INT_TBL_DAT_COD_COT  )==null?"":tblDat.getValueAt(intRow,  INT_TBL_DAT_COD_COT  ).toString());
            
            if(validaTipodeReservaPedidosOtrasBodegas(CodEmp,CodLoc,CodCot)){
                 llamarVenPedOtrBod(CodEmp,CodLoc,CodCot);
            }
            else{
                MensajeInf("Este tipo de solicitud genera la factura de forma automáticamente.");
            }
        }
    }
    
    
    
     /**
      * 
      * @param intRow
      * @param CodEmp
      * @param CodLoc
      * @param CodCot 
      */
    private void llamarVenPedOtrBod(int CodEmp, int CodLoc, int CodCot){
            ZafVen42_11 obj1 = new  ZafVen42_11(javax.swing.JOptionPane.getFrameForComponent(this),objParSis, CodEmp , CodLoc , CodCot);
            //this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj1.show();
           if(obj1.blnAcepta){
               obj1=null;
           }
    }
    
    
    
    /**
     * 
     * 
     */
    
    
     
     private class ButLisCanResVen extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButLisCanResVen(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Cancelacion de Reservas.");
           
        }
        @Override
        public void butCLick() {
            int intRow = tblDat.getSelectedRow();
            int CodEmp = Integer.parseInt( tblDat.getValueAt(intRow,  INT_TBL_DAT_COD_EMP  )==null?"":tblDat.getValueAt(intRow,  INT_TBL_DAT_COD_EMP  ).toString());
            int CodLoc = Integer.parseInt( tblDat.getValueAt(intRow,  INT_TBL_DAT_COD_LOC  )==null?"":tblDat.getValueAt(intRow,  INT_TBL_DAT_COD_LOC  ).toString());
            int CodCot = Integer.parseInt( tblDat.getValueAt(intRow,  INT_TBL_DAT_COD_COT  )==null?"":tblDat.getValueAt(intRow,  INT_TBL_DAT_COD_COT  ).toString());
            
            if(validaTipodeReserva(CodEmp,CodLoc,CodCot)){
                if(validacionCotizacioAutorizada(CodEmp,CodLoc,CodCot)){
                    if(validacionCotizacionNoEsteCompleta(CodEmp,CodLoc,CodCot)){
                        if(validaCotizacionIngresosCompletados(CodEmp,CodLoc,CodCot)){
                            llamarVenCanRes(intRow,CodEmp,CodLoc,CodCot);
                        }
                        else{
                            MensajeInf("La cotizacion posee items pendientes de confirmacion de Ingresos en la bodega destino, no se puede cancelar.");
                        }
                    }
                    else{
                        MensajeInf("La cotizacion esta completa, no se puede cancelar.");
                    }
                    
                }
                else{
                    MensajeInf("La cotizacion no ha sido autorizada, no se puede cancelar.");
                }
            }
            else{
                MensajeInf("Este tipo de solicitud no se puede cancelar, debe esperar a que se genere la factura de forma automática y generar una Nota de credito.");
            }
        }
    }
     /**
      * 
      * @param intRow
      * @param CodEmp
      * @param CodLoc
      * @param CodCot 
      */
     private void llamarVenCanRes(int intRow,int CodEmp, int CodLoc, int CodCot){
         
            ZafVen42_10 obj1 = new  ZafVen42_10(javax.swing.JOptionPane.getFrameForComponent(this),objParSis, CodEmp , CodLoc , CodCot);
            //this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj1.show();
            if(obj1.acepta()){
                if(cancelarDocumento(CodEmp,CodLoc,CodCot)){
                    //objTblMod.setValueAt(true, intRow, INT_TBL_DAT_CHK_CANCELAR ); 
                    MensajeInf("La Reserva ha sido cancelada con exito.");
                }
                else{
                    MensajeInf("Error al guardar la cancelacion.");
                }
            }
    }
     
     /**
      * 
      * @param CodEmp
      * @param CodLoc
      * @param CodCot
      * @return 
      */
     private boolean cancelarDocumento(int CodEmp, int CodLoc, int CodCot){
         boolean blnRes=false;
         java.sql.Connection conLoc;
         try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                conLoc.setAutoCommit(false);
                ZafCanResInv objCanResInv = new ZafCanResInv(objParSis,javax.swing.JOptionPane.getFrameForComponent(this) );
                if(objCanResInv.iniciaCancelacionReservas(conLoc, CodEmp, CodLoc, CodCot)){
                    blnRes=true;
                    conLoc.commit();
                }else{
                    blnRes=false;
                    conLoc.rollback();
                }
                conLoc.close();
                conLoc=null;
            }
            
         }
         catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
         
     }
     
     
     private boolean validacionCotizacioAutorizada(int CodEmp, int CodLoc, int CodCot){
         boolean blnRes=false;
         java.sql.Connection conLoc;
         java.sql.Statement stmLoc;
         java.sql.ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT CASE WHEN a1.st_autSolResInv IS NULL THEN 'N' ELSE a1.st_autSolResInv END as st_autSolResInv  \n";
                strSql+=" FROM tbm_cabCotVen as a1 \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+"  ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    if(rstLoc.getString("st_autSolResInv").equals("A")){
                        blnRes=true;
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            System.err.println("ERROR: " + e);
            blnRes=false;            
        }
        catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
     }
     
     
     private boolean validacionCotizacionNoEsteCompleta(int CodEmp, int CodLoc, int CodCot){
         boolean blnRes=false;
         java.sql.Connection conLoc;
         java.sql.Statement stmLoc;
         java.sql.ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT CASE WHEN a1.st_solResInv IS NULL THEN 'N' ELSE a1.st_solResInv END as st_solResInv  \n";
                strSql+=" FROM tbm_cabCotVen as a1 \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+"  ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    if(rstLoc.getString("st_solResInv").equals("P")){
                        blnRes=true;
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            System.err.println("ERROR: " + e);
            blnRes=false;            
        }
        catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
     }
     
      private boolean validaTipodeReservaPedidosOtrasBodegas(int CodEmp, int CodLoc, int CodCot){
         boolean blnRes=false;
         java.sql.Connection conLoc;
         java.sql.Statement stmLoc;
         java.sql.ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a2.tx_tipResInv  \n";
                strSql+=" FROM tbm_cabCotVen as a1 \n";
                strSql+=" INNER JOIN tbm_tipSolResInv as a2 ON (a1.co_tipSolResInv=a2.co_tipSolResInv)  ";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+"  ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    if(rstLoc.getString("tx_tipResInv").equals("R")){
                        blnRes=true;
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            System.err.println("ERROR: " + e);
            blnRes=false;            
        }
        catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
     }
     
     
    
     private boolean validaTipodeReserva(int CodEmp, int CodLoc, int CodCot){
         boolean blnRes=false;
         java.sql.Connection conLoc;
         java.sql.Statement stmLoc;
         java.sql.ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a2.tx_tipResInv  \n";
                strSql+=" FROM tbm_cabCotVen as a1 \n";
                strSql+=" INNER JOIN tbm_tipSolResInv as a2 ON (a1.co_tipSolResInv=a2.co_tipSolResInv)  ";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+"  ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    if(rstLoc.getString("tx_tipResInv").equals("R")){
                        blnRes=true;
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            System.err.println("ERROR: " + e);
            blnRes=false;            
        }
        catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
     }
     
    private boolean validaCotizacionIngresosCompletados( int CodEmp,int CodLoc, int CodCot){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a2.co_seg, a1.co_emp, a1.co_loc, a1.co_cot \n";
                strSql+=" FROM tbm_cabCotVen as a1 \n";
                strSql+=" INNER JOIN tbm_cabSegMovInv as a2 ON (a1.co_emp=a2.co_empRelCabCotVen AND a1.co_loc=a2.co_locRelCabCotVen AND  \n";
                strSql+="                                       a1.co_cot=a2.co_cotRelCabCotVen)  \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+"  AND a1.co_cot="+CodCot+" \n";
                strSql+=" \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    if(isDocIngPenCnf(conLoc,rstLoc.getInt("co_seg"), "I")){
                       blnRes=false; 
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            System.err.println("ERROR: " + e);
            blnRes=false;            
        }
        catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
    }
        
       
        
    /**
     * Función que permite obtener los documentos de ingresos que están pendientes de confirmar
     * @return true Si obtiene algún registro es porque aún no se debe confirmar
     * <BR> false Caso contrario
     */
    public boolean isDocIngPenCnf(Connection conExt
                                    , int codigoSeguimiento
                                    , String tipoOperacion){
        boolean blnRes=true;
        int intCodSeg=codigoSeguimiento;
        BigDecimal bdeCanIngBod=BigDecimal.ZERO;
        BigDecimal bdeCan=BigDecimal.ZERO;
        BigDecimal bdeCanPndCnf=BigDecimal.ZERO;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                if(tipoOperacion.equals("I")){
                    stmLoc=conExt.createStatement();
                    if(intCodSeg!=-1){                        
                        strSQL="";
                        strSQL+=" SELECT SUM(b1.nd_canPenCnf) AS nd_canPenCnf, SUM(b1.nd_canIngBod) AS nd_canIngBod, SUM(b1.nd_can) AS nd_can";
                        strSQL+=" , SUM((CASE WHEN b1.nd_canDesEntCli IS NULL THEN 0 ELSE b1.nd_canDesEntCli END)) AS nd_canDesEntCli";
                        strSQL+=" , SUM((CASE WHEN b1.nd_canEgrBod IS NULL THEN 0 ELSE b1.nd_canEgrBod END)) AS nd_canEgrBod";
                        strSQL+=" FROM(";
                        strSQL+="    SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                        strSQL+="    , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                        strSQL+="    , a2.nd_canIngBod, a2.nd_canDesEntCli, a2.nd_canEgrBod";
                        strSQL+="    FROM(";
                        strSQL+="            SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                        strSQL+="            FROM tbm_cabsegmovinv AS a3 INNER JOIN tbm_cabMovInv AS a1";
                        strSQL+="            ON a3.co_empRelCabMovInv=a1.co_emp AND a3.co_locRelCabMovInv=a1.co_loc";
                        strSQL+="            AND a3.co_tipDocRelCabMovInv=a1.co_tipDoc AND a3.co_docRelCabMovInv=a1.co_doc";
                        strSQL+="            INNER JOIN tbm_detMovInv AS a2";
                        strSQL+="            ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+="            WHERE a3.co_seg=" + intCodSeg + "";
                        strSQL+="    ) AS a1";
                        strSQL+="    INNER JOIN(";
                        strSQL+="            SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon";
                        //strSQL+="            , (a2.nd_can - a2.nd_canCon) AS nd_canPenCnf, a2.nd_canIngBod, a2.nd_canDesEntCli, a2.nd_canEgrBod";
                        strSQL+="            , (a2.nd_can - (CASE WHEN a2.nd_canDesEntCli IS NULL THEN 0 ELSE a2.nd_canDesEntCli END)) AS nd_canPenCnf, a2.nd_canIngBod, a2.nd_canEgrBod, a2.nd_canDesEntCli";
                        strSQL+="            FROM tbm_cabMovInv AS a1";
                        //strSQL+="            INNER JOIN tbm_detMovInv AS a2";
                        strSQL+="            INNER JOIN (tbm_detMovInv AS a2";
                        strSQL+="                       LEFT OUTER JOIN tbr_detMovInv AS a3";
                        strSQL+="                       ON a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel AND a2.co_tipDoc=a3.co_tipDocRel AND a2.co_doc=a3.co_docRel AND a2.co_reg=a3.co_regRel";
                        strSQL+="                       )";
                        strSQL+="            ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+="            WHERE a2.st_meringegrfisbod='S'";
                        strSQL+="            AND (ABS(a2.nd_can) - ABS(a2.nd_canCon)) <> 0";
                        strSQL+="            AND (a2.nd_canPen<>0 OR a2.nd_canPen IS NOT NULL)";
                        strSQL+="            AND a2.nd_can>0";
                        strSQL+=" ) AS a2";
                        strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                        strSQL+=" , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                        strSQL+=" , a2.nd_canIngBod, a2.nd_canDesEntCli, a2.nd_canEgrBod";
                        strSQL+=" ) AS b1";
                        System.out.println("isDocIngPenCnf: " + strSQL);
                        rstLoc=stmLoc.executeQuery(strSQL);
                        if(rstLoc.next()){
                            bdeCanIngBod=new BigDecimal(rstLoc.getObject("nd_canIngBod")==null?"0":(rstLoc.getString("nd_canIngBod").equals("")?"0":rstLoc.getString("nd_canIngBod")));
                            bdeCan=new BigDecimal(rstLoc.getObject("nd_can")==null?"0":(rstLoc.getString("nd_can").equals("")?"0":rstLoc.getString("nd_can")));//rst.getBigDecimal("nd_can");
                            bdeCanPndCnf=new BigDecimal(rstLoc.getObject("nd_canPenCnf")==null?"0":(rstLoc.getString("nd_canPenCnf").equals("")?"0":rstLoc.getString("nd_canPenCnf")));//rst.getBigDecimal("nd_can");
                            //bdeCanDesEntCli=new BigDecimal(rst.getObject("nd_canDesEntCli")==null?"0":(rst.getString("nd_canDesEntCli").equals("")?"0":rst.getString("nd_canDesEntCli")));//rst.getBigDecimal("nd_canDesEntCli");
                            //bdeCanEgrBod=new BigDecimal(rst.getObject("nd_canEgrBod")==null?"0":(rst.getString("nd_canEgrBod").equals("")?"0":rst.getString("nd_canEgrBod")));//rst.getBigDecimal("nd_canDesEntCli");
                            
                            System.out.println("**++ bdeCanIngBod: " + bdeCanIngBod);
                            System.out.println("**++ bdeCanPndCnf: " + bdeCanPndCnf);
                            
                            //se comento
                            if(bdeCanIngBod.compareTo(BigDecimal.ZERO)==0 ){
                                if(bdeCanPndCnf.compareTo(BigDecimal.ZERO)==0 ){
                                  
                                        blnRes=false;
                                }
                            }
                            //fin 
                        }
                        rstLoc.close();
                        rstLoc=null;
                    }
                    stmLoc.close();
                    stmLoc=null;
                }                
            }
        }
        catch(java.sql.SQLException e){
            System.err.println("ERROR: " + e);
            blnRes=false;            
        }
        catch(Exception e){
            System.err.println("ERROR: " + e);
            blnRes=false;            
        }
        return blnRes;
    }
    
     
    
      /**
       * Guarda la autorizacion de la reserva
       * @return 
       */
      
    private boolean guardarAutorizacionReserva(){
        java.sql.Connection conLoc;
        boolean blnRes=true;
        try{
            lblMsgSis.setText("Guardando datos...");
            objCfgSer.cargaDatosIpHostServicios(0, intCodSer);
            pgrSis.setIndeterminate(true);
            int intNumFil=objTblMod.getRowCountTrue(), intCodEmp, intCodLoc, intCodCot;
            for (int i=0; i<intNumFil;i++){
                if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){                    
                    intCodEmp=Integer.parseInt(tblDat.getValueAt(i,INT_TBL_DAT_COD_EMP).toString() );
                    intCodLoc=Integer.parseInt(tblDat.getValueAt(i,INT_TBL_DAT_COD_LOC).toString() );
                    intCodCot=Integer.parseInt(tblDat.getValueAt(i,INT_TBL_DAT_COD_COT).toString() );
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_AUTORIZAR)){
                        if(validaStock(intCodEmp, intCodLoc, intCodCot)){
                            if(validaControlesCxC(intCodEmp, intCodLoc, intCodCot)){
                                if(validaDatosAutorizacion(i,intCodEmp, intCodLoc,intCodCot)){
                                    // AUTORIZACION
                                    if(tblDat.getValueAt(i, INT_TBL_DAT_STR_TIP_RES_INV).toString().equals("F")){
                                        conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                                        if(conLoc!=null){
                                            conLoc.setAutoCommit(false);
                                            if(objDatItm.modificaItemTerminalOcupado(intCodEmp, intCodLoc, intCodCot)){
                                                if(guardarAutorizacionFacturacionAutomatica(conLoc,i,intCodEmp,intCodLoc,intCodCot)){
                                                    conLoc.commit();                
                                                    objGenOD.imprimirOdxRes(conLoc, getCodigoSeguimiento(conLoc,intCodEmp,intCodLoc,intCodCot), objCfgSer.getIpHost());
                                                    blnCotVen=true;
                                                }else{
                                                    blnRes=false;
                                                    conLoc.rollback();
                                                    blnCotVen=false;
                                                }
                                            }else{
                                                blnRes=false;
                                                conLoc.rollback();
                                                blnCotVen=false;
                                            }
                                            conLoc.close();
                                            conLoc=null;   
                                        }
                                    }
                                    else if(tblDat.getValueAt(i,INT_TBL_DAT_STR_TIP_RES_INV).toString().equals("R")){
                                        conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                                        if(conLoc!=null){
                                            conLoc.setAutoCommit(false);
                                            if(guardarAutorizacionReservaEmpresa(conLoc,i ,intCodEmp,intCodLoc, intCodCot)){
                                                conLoc.commit();
                                                objGenOD.imprimirOdxRes(conLoc, getCodigoSeguimiento(conLoc,intCodEmp,intCodLoc,intCodCot), objCfgSer.getIpHost());
                                            }
                                            else{
                                                blnRes=false;
                                                conLoc.rollback();
                                            }
                                            conLoc.close();
                                            conLoc=null;   
                                        }
                                    }
                                }
                            }
                            else{
                                blnRes=false;
                            }
                        }
                        else{
                            blnRes=false;
                        }
                    }else if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_DENEGAR)){
                        /*  DENEGAR   */
                        if(revisarCotizacionPendiente(intCodEmp,intCodLoc,intCodCot)){
                            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                            if(conLoc!=null){
                                conLoc.setAutoCommit(false);
                                if(cambiarEstadoCotizacionDenegada(conLoc,intCodEmp,intCodLoc,intCodCot)){
                                    conLoc.commit();
                                    enviarCorreoSolicitudDenegada(intCodEmp,intCodLoc,intCodCot);
                                }
                                else{
                                    conLoc.rollback();
                                }
                            }
                            conLoc.close();
                            conLoc=null;
                        }
                        else{
                            MensajeInf("La cotizacion "+intCodCot+" no se puede denegar, ya ha sido autorizada. Debe solicitar cancelacion. ");
                        }
                    }
                    blnRes=false;
                    blnGenSolTra=false;
                }
            }
            pgrSis.setIndeterminate(false);
            
            if(!blnIsCotVen){
                conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                cargarDetReg(conLoc);
                conLoc.close();
                conLoc=null;
            }
            blnRes=true;
       }
       catch (java.sql.SQLException e) { 
           objUti.mostrarMsgErr_F1(this, e);  
       }
       catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
       }  
       return blnRes;
    }
    
    
    
    private boolean revisarCotizacionPendiente(int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT co_emp,co_loc,co_cot,st_reg,st_autSolResInv \n";
                strSql+=" FROM tbm_cabCotVen  \n";
                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+" ";
                System.out.println("revisarCotizacionPendiente " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    if(rstLoc.getString("st_autSolResInv").equals("P")){
                         blnRes=true;
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
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
    
    
    private boolean revisarCotizacionSinFactura(int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT co_emp,co_loc,co_cot,st_reg,st_autSolResInv \n";
                strSql+=" FROM tbm_cabCotVen  \n";
                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+" ";
                System.out.println("revisarCotizacionPendiente " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    if(rstLoc.getString("st_reg").equals("F")){
                         blnRes=false;
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
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
     * Guarda la autorizacion de una Solicitud de reserva en la Misma empresa
     * @param conExt
     * @param indice
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    private boolean guardarAutorizacionReservaEmpresa(java.sql.Connection conExt, int indice, int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
        try{
            if(conExt!=null){
                if(moverItemsCotizacionReservaEmpresa(conExt,CodEmp, CodLoc, CodCot)){
                    if(cambiarEstadoCotizacionAutorizada(conExt, CodEmp, CodLoc, CodCot,indice)){
                        if(realizaMovimientoEntreEmpresas(conExt, CodEmp, CodLoc, CodCot)){
                            //if(moverInvBodReservado(conLoc,intCodEmp,intCodLoc,intCodCot)){
                                if(updateCotizacionProcesoSolicitaTransferenciaInventario(conExt, CodEmp, CodLoc, CodCot)){
                                    if(insertarCotizacionSeguimiento(conExt, CodEmp, CodLoc, CodCot)){
                                        if(blnGenSolTra ){
                                            Librerias.ZafMovIngEgrInv.ZafReaMovInv objReaMovInv = new Librerias.ZafMovIngEgrInv.ZafReaMovInv(objParSis,this );   
                                            Compras.ZafCom91.ZafCom91 objCom91 = new Compras.ZafCom91.ZafCom91(objParSis,1);
                                            arlDatSolTra = new ArrayList();
                                            arlDatSolTra=(objCom91.insertarSolicitudTransferenciaReservas(conExt,arlSolTraDat,CodEmp,CodLoc,CodCot));
                                            System.out.println("GeneraSolicitud" + arlDatSolTra.toString());
                                            if(isSolTraInv(conExt,CodEmp,CodLoc,CodCot)){
                                                if(objReaMovInv.inicia(conExt, arlDat,datFecAux,arlDatSolTra,null)){
                                                    if(modificaDetalleCotizacionCantidadesLocalesRemotasDos(conExt, CodEmp,CodLoc, CodCot)){
                                                        System.out.println("OK PRESTAMOS");
                                                    }
                                                    else{
                                                        MensajeInf("Error al Guardar: Reservar mercaderia local");
                                                        System.out.println("GeneraSolicitud NO reservo local.....");
                                                        blnRes=false;
                                                    }
                                                }else{
                                                    MensajeInf("Error al Guardar: Prestamos entre empresas");
                                                    System.out.println("GeneraSolicitud No Genero la solicitud.....");
                                                    blnRes=false;
                                                }
                                            }
                                            else{
                                                MensajeInf("Error al Guardar: La solicitud de transferencia");
                                                System.out.println("No Genero la solicitud.....");
                                                blnRes=false;
                                            }
                                            objParSis.setCodigoMenu(intCodMnuResVenAut); // AUTORIZACIONES 
                                            objReaMovInv = null;
                                            objCom91 = null;

                                        }
                                        else{
                                            /* Todo es local */
//                                            if(modificaDetalleCotizacionCantidadesLocalesRemotas(conExt, CodEmp, CodLoc, CodCot)){
//                                                System.out.println("OK PRESTAMOS");
//                                            }
//                                            else{
//                                                MensajeInf("Error al Guardar: Reservar mercaderia local");
//                                                System.out.println("GeneraSolicitud NO reservo local.....");
//                                                blnRes=false;
//                                            }
                                            if(cotizacionSinSolicitudTransferencia(conExt,CodEmp,CodLoc,CodCot)){
                                                if(ponerItemsComoReservaLocal(conExt,CodEmp,CodLoc,CodCot)){
                                                    System.out.println("generarEgresoMercaderiaReservada OK");
                                                }
                                                else{
                                                    MensajeInf("Error al Guardar: Reservar mercaderia local");
                                                    System.out.println("GeneraSolicitud NO reservo local.....");
                                                    blnRes=false;
                                                }
                                            }
                                        }
                                        /* Genera Egreso de reserva */
                                    }else{blnRes=false;MensajeInf("Error al Guardar: Insertando Cotizacion en el seguimiento");}
                                }
                            //}
                        }else{blnRes=false;MensajeInf("Error al Guardar: Realizando Movimiento Entre Empresas ");}
                    }
                }
            }
        }
        catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
           blnRes=false;
        }  
        return blnRes;
    } 
    
    private int intCodSegGlo=-1;
    /**
     * Guarda la autorizacion de una solicitud de reserva con Facturacion Automatica.
     * @param conExt
     * @param indice
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    private ZafModDatGenFac objModDatGenFac;
    
    private boolean guardarAutorizacionFacturacionAutomatica(java.sql.Connection conExt, int indice, int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
        try{
            if(conExt!=null){
                if(moverItemsCotizacion(conExt,CodEmp, CodLoc, CodCot)){
                    if(cambiarEstadoCotizacionAutorizada(conExt, CodEmp, CodLoc, CodCot,indice)){
                        if(realizaMovimientoEntreEmpresas(conExt, CodEmp, CodLoc, CodCot)){
                            //if(moverInvBodReservado(conLoc,intCodEmp,intCodLoc,intCodCot)){
                                if(updateCotizacionProcesoSolicitaTransferenciaInventario(conExt, CodEmp, CodLoc, CodCot)){
                                    if(insertarCotizacionSeguimiento(conExt, CodEmp, CodLoc, CodCot)){
                                        if(existenCodigosL( CodEmp,  CodLoc,  CodCot)){
                                            blnGenSolTraCodL=true;
                                        }
                                        else{
                                            blnGenSolTraCodL=false;
                                        }

                                        if(blnGenSolTra ){
                                            Librerias.ZafMovIngEgrInv.ZafReaMovInv objReaMovInv = new Librerias.ZafMovIngEgrInv.ZafReaMovInv(objParSis,this );   
                                            Compras.ZafCom91.ZafCom91 objCom91 = new Compras.ZafCom91.ZafCom91(objParSis,1);
                                            arlDatSolTra = new ArrayList();
                                            arlDatSolTra=(objCom91.insertarSolicitudTransferenciaReservas(conExt,arlSolTraDat,CodEmp,CodLoc,CodCot));
                                            System.out.println("GeneraSolicitud" + arlDatSolTra.toString());
                                            if(isSolTraInv(conExt,CodEmp,CodLoc,CodCot)){
                                                System.out.println("arlDat: " + arlDat.toString());
                                                if(objReaMovInv.inicia(conExt, arlDat,datFecAux,arlDatSolTra,null)){
//                                                    if(modificaDetalleCotizacionCantidadesLocalesRemotas(conExt, CodEmp,CodLoc, CodCot)){
                                                        if(itemsSinIngresoReservaLocal(conExt,CodEmp,CodLoc,CodCot)){
                                                            System.out.println("OK PRESTAMOS");
                                                        }else{
                                                            MensajeInf("Error al Guardar: Reservar mercaderia local sin ingresos ");
                                                            System.out.println("GeneraSolicitud NO reservo local.....");
                                                            blnRes=false;
                                                        }
//                                                    }
//                                                    else{
//                                                        MensajeInf("Error al Guardar: Reservar mercaderia local");
//                                                        System.out.println("GeneraSolicitud NO reservo local.....");
//                                                        blnRes=false;
//                                                    }
                                                }else{
                                                    MensajeInf("Error al Guardar: Prestamos entre empresas");
                                                    System.out.println("GeneraSolicitud No Genero la solicitud.....");
                                                    blnRes=false;
                                                }
                                            }
                                            else{
                                                MensajeInf("Error al Guardar: La solicitud de transferencia");
                                                System.out.println("No Genero la solicitud.....");
                                                blnRes=false;
                                            }
                                            objParSis.setCodigoMenu(intCodMnuResVenAut); // AUTORIZACIONES 
                                            objReaMovInv = null;
                                            objCom91 = null;

                                        }
                                        else{
                                            /* Todo es local */
                                            if(modificaDetalleCotizacionCantidadesLocalesRemotasDos(conExt, CodEmp, CodLoc, CodCot)){
                                                System.out.println("OK PRESTAMOS");
                                            }
                                            else{
                                                MensajeInf("Error al Guardar: Reservar mercaderia local");
                                                System.out.println("GeneraSolicitud NO reservo local.....");
                                                blnRes=false;
                                            }
                                        }


                                        if(blnRes){
                                            if(blnGenSolTraCodL){
                                                txtsql = new javax.swing.JTextArea();
                                                if(generaOC(conExt,CodEmp,CodLoc,CodCot)){
                                                    System.out.println("OC OK");
                                                }
                                                else{
                                                    MensajeInf("Error al Guardar: La orden de Compra");
                                                    blnRes=false;
                                                }
                                            }
                                        }
                                        /* Genera Egreso de reserva */
                                        if(blnRes){
                                            if(cotizacionReservaDespachoAntesFactura(conExt,CodEmp,CodLoc,CodCot )){
                                                if(validaCotizacionIngresosCompletados(conExt,CodEmp,CodLoc,CodCot)){
                                                    if(generarEgresoMercaderiaReservada(conExt,CodEmp,CodLoc,CodCot)){
                                                        System.out.println("generarEgresoMercaderiaReservada OK");
                                                    }
                                                    else{
                                                        blnRes=false;
                                                        MensajeInf("Error al Guardar: Generando Egreso de la mercaderia reservada");
                                                    }
                                                }
                                                else if(cotizacionSinSolicitudTransferencia(conExt,CodEmp,CodLoc,CodCot)){
                                                    if(validaCotizacionIngresosCompletados(conExt,CodEmp,CodLoc,CodCot)){
                                                        if(ponerItemsComoReservaLocal(conExt,CodEmp,CodLoc,CodCot)){
                                                            if(generarEgresoMercaderiaReservada(conExt,CodEmp,CodLoc,CodCot)){
                                                                System.out.println("generarEgresoMercaderiaReservada OK");
                                                            }
                                                            else{
                                                                blnRes=false;
                                                                MensajeInf("Error al Guardar: Generando Egreso de la mercaderia reservada");
                                                            }
                                                        }
                                                    } 
                                                }
                                            }
                                        }
                                        if(blnRes){
                                            objModDatGenFac = new Librerias.ZafGenFacAut.ZafModDatGenFac(objParSis, this);
                                            if(objModDatGenFac.modificaDetalleCotizacionLocalRemoto(conExt,CodEmp,CodLoc,CodCot)){
                                                System.out.println("objModDatGenFac.modificaDetalleCotizacionLocalRemoto OK");
                                            }
                                            else{
                                                blnRes=false;
                                                MensajeInf("Error al Guardar: Grabando los datos Locales y remotos de la cotizacion");
                                            }
                                            objModDatGenFac = null;
                                        }
                                        
                                         
                                        /* Genera Egreso de reserva */
                                    }else{blnRes=false;MensajeInf("Error al Guardar: Insertando Cotizacion en el seguimiento");}
                                }
                            //}
                        }else{blnRes=false;MensajeInf("Error al Guardar: Realizando Movimiento Entre Empresas ");}
                    }
                }
            }
        }
       catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
           blnRes=false;
       } 
        return blnRes;
    }
    
    private boolean validaCotizacionIngresosCompletados(java.sql.Connection conExt,int CodEmp,int CodLoc, int CodCot){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT a2.co_seg, a1.co_emp, a1.co_loc, a1.co_cot \n";
                strSql+=" FROM tbm_cabCotVen as a1 \n";
                strSql+=" INNER JOIN tbm_cabSegMovInv as a2 ON (a1.co_emp=a2.co_empRelCabCotVen AND a1.co_loc=a2.co_locRelCabCotVen AND  \n";
                strSql+="                                       a1.co_cot=a2.co_cotRelCabCotVen)  \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+"  AND a1.co_cot="+CodCot+" \n";
                strSql+=" \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    if(isDocIngPenCnf(conExt,rstLoc.getInt("co_seg"), "I")){
                       blnRes=false; 
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;            
        }
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
     
    
    
    /**
     * 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
    private int getCodigoSeguimiento(java.sql.Connection conExt,int CodEmp,int CodLoc,int CodCot){
        int codigoSeguimiento=-1;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                strSql="";
                strSql+=" SELECT co_seg \n";
                strSql+=" FROM tbm_cabSegMovInv \n";
                strSql+=" WHERE co_empRelCabCotVen="+CodEmp+" AND co_locRelCabCotVen="+CodLoc+" AND co_cotRelCabCotVen="+CodCot+" \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    codigoSeguimiento = rstLoc.getInt("co_seg");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e) { 
           codigoSeguimiento=-1;
           objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception  Evt){ 
           codigoSeguimiento=-1;
           objUti.mostrarMsgErr_F1(this, Evt);
        }
        return codigoSeguimiento;
    }
    
    private boolean ponerItemsComoReservaLocal(java.sql.Connection conExt,int CodEmp,int CodLoc,int CodCot){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
         
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                strSql="";
                strSql+=" UPDATE tbm_invBod SET nd_canDis = ( CASE WHEN tbm_invBod.nd_canDis IS NULL THEN 0 ELSE tbm_invBod.nd_canDis END ) - a.nd_can,   \n";
                strSql+="                       nd_canRes = ( CASE WHEN tbm_invBod.nd_canRes IS NULL THEN 0 ELSE tbm_invBod.nd_canRes END ) + a.nd_can  \n";
                strSql+=" FROM (  \n";
                strSql+="       SELECT a2.tx_codAlt, a3.co_emp, a3.co_itm, a3.co_bod , a3.nd_can, a1.nd_canDis, a1.nd_canRes   \n";
                strSql+="       FROM tbm_invBod as a1   \n";
                strSql+="       INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSql+="       INNER JOIN tbm_detCotVen as a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod AND a1.co_itm=a3.co_itm)  \n";
                strSql+="       WHERE a3.co_emp="+CodEmp+" AND a3.co_loc="+CodLoc+" AND a3.co_cot="+CodCot+" AND a2.st_ser='N' AND  \n";
                strSql+="             ( a3.tx_codAlt LIKE ('%S') OR a3.tx_codAlt like ('%I'))  \n";
                strSql+="  )as a  \n";
                strSql+=" WHERE tbm_invBod.co_emp=a.co_emp AND tbm_invBod.co_itm=a.co_itm AND tbm_invBod.co_bod=a.co_bod ; \n";
                System.out.println("ReservaLocal: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e) { 
           blnRes = false;
           objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
           blnRes=false;
        }
        return blnRes;
    }
    
    
    private boolean cotizacionSinSolicitudTransferencia(java.sql.Connection conExt,int CodEmp,int CodLoc,int CodCot){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_seg, a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_bodOrg, a2.co_bodDes \n";
                strSql+=" FROM tbm_cabSegMovInv as a1 \n";
                strSql+=" INNER JOIN tbm_cabSolTraInv as a2 ON (a1.co_empRelCabSolTraInv=a2.co_emp AND a1.co_locRelCabSolTraInv=a2.co_loc AND  \n";
                strSql+="                                       a1.co_tipDocRelCabSolTraInv=a2.co_tipDoc AND a1.co_docRelCabSolTraInv=a2.co_doc) \n";
                strSql+=" INNER JOIN ( \n";
                strSql+="               SELECT a2.co_seg \n";
                strSql+="               FROM tbm_cabCotVen as a1  \n";
                strSql+="               INNER JOIN tbm_cabSegMovInv as a2 ON (a1.co_emp=a2.co_empRelCabCotVen AND a1.co_loc=a2.co_locRelCabCotVen AND \n";
                strSql+="                                                     a1.co_cot=a2.co_cotRelCabCotVen) \n";
                strSql+="               WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+" \n";
                strSql+=" ) as a3 ON (a1.co_seg=a3.co_seg)\n";
                strSql+=" \n";
                System.out.println("cotizacionSinSolicitudTransferencia:  " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    blnRes=false;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            
        }
        catch (java.sql.SQLException e) { 
           blnRes = false;
           objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
           blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Envia correos al ser denegada una solicitud
     * @param CodEmp
     * @param CodLoc
     * @param CodCot 
     */
    
    private void enviarCorreoSolicitudDenegada(int CodEmp, int CodLoc, int CodCot){
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
               stmLoc=conLoc.createStatement();
               strSql= " SELECT a2.tx_corEle  \n";
               strSql+=" FROM tbm_cabCotVen as a1  \n";
               strSql+=" INNER JOIN tbm_usr as a2 ON (a1.co_usrIng=a2.co_usr) \n";
               strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" ";
               rstLoc = stmLoc.executeQuery(strSql);
               if(rstLoc.next()){
                   objCorEle.enviarCorreoMasivo(rstLoc.getString("tx_corEle"), "Sistema de Reservas: ", "La solicitud de reserva, que corresponde a la cotizacion "+CodCot+" ha sido denegada.");
               }
               rstLoc.close();
               rstLoc=null;
               stmLoc.close();
               stmLoc=null;
               conLoc.close();
               conLoc=null;
            }
        }
        catch (java.sql.SQLException e) { 
          
           objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
        }  
    }
    
    
    public boolean Aceptar(){
        return blnCotVen;
    }
    
    /* Para solo pedir estos datos los demas la clase los obtiene */
    private static final int INT_ARL_DAT_COD_EMP=0;
    private static final int INT_ARL_DAT_COD_LOC=1;
    private static final int INT_ARL_DAT_COD_BOD_EMP=2;
    private static final int INT_ARL_DAT_COD_ITM_EMP=3;
    private static final int INT_ARL_DAT_CAN=4;
    private static final int INT_ARL_DAT_COS_UNI=5;
    private static final int INT_ARL_DAT_COD_SEG=6 ;
    private static final int INT_ARL_DAT_COD_REG=7 ;
    private static final int INT_ARL_DAT_NOM_ITM=8 ;
    
    /**
     * Metodo para generar el egreso de la mercaderia reservada
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
    private boolean generarEgresoMercaderiaReservada(java.sql.Connection conExt, int CodEmp,int CodLoc,int CodCot){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        ArrayList arlResDat, arlResReg;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                arlResDat = new ArrayList();
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot, a2.co_reg, a2.co_itm, a2.co_bod, a2.nd_can,  \n";
                strSql+="        CASE WHEN a3.nd_cosUni IS NULL THEN 0 ELSE a3.nd_cosUni END as nd_cosUni,a4.co_seg ";
                strSql+="        ,a2.tx_nomItm \n";
                strSql+=" FROM tbm_cabCotVen as a1  \n";
                strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                strSql+=" INNER JOIN tbm_cabSegMovInv as a4 ON (a1.co_emp=a4.co_empRelCabCotVen AND a1.co_loc=a4.co_locRelCabCotVen AND \n";
                strSql+="                                       a1.co_cot=a4.co_cotRelCabCotVen) \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+" and a3.st_ser='N' \n";
                strSql+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_cot, a2.co_reg \n";
                System.out.println("generarEgresoMercaderiaReservada " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    arlResReg = new ArrayList();
                    arlResReg.add(INT_ARL_DAT_COD_EMP, rstLoc.getInt("co_emp"));
                    arlResReg.add(INT_ARL_DAT_COD_LOC, rstLoc.getInt("co_loc"));
                    arlResReg.add(INT_ARL_DAT_COD_BOD_EMP, rstLoc.getInt("co_bod"));
                    arlResReg.add(INT_ARL_DAT_COD_ITM_EMP, rstLoc.getInt("co_itm"));
                    arlResReg.add(INT_ARL_DAT_CAN, rstLoc.getDouble("nd_can"));
                    arlResReg.add(INT_ARL_DAT_COS_UNI, rstLoc.getDouble("nd_cosUni"));
                    arlResReg.add(INT_ARL_DAT_COD_SEG, rstLoc.getInt("co_seg"));
                    arlResReg.add(INT_ARL_DAT_COD_REG, rstLoc.getInt("co_reg"));
                    arlResReg.add(INT_ARL_DAT_NOM_ITM, rstLoc.getString("tx_nomItm"));
                    arlResDat.add(arlResReg);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                if(!arlResDat.isEmpty()){
                    ZafGenDocIngEgrInvRes objGenDocIngEgrInvRes = new ZafGenDocIngEgrInvRes(objParSis,this);
                    if(objGenDocIngEgrInvRes.GenerarDocumentoIngresoEgresoReservas(conExt, datFecAux, arlResDat, 1)){
                        blnRes=true;
                    }
                }
            }
        }
        catch (java.sql.SQLException e) { 
           blnRes = false;
           objUti.mostrarMsgErr_F1(this, e);  
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }
    
     
    
    
    /**
     * Metodo para saber si una cotizacion esta reservada con Despacho antes de la emitir la factura
     * @param conExt Conexion
     * @param CodEmp Empresa
     * @param CodLoc Local 
     * @param CodCot Codigo de la Cotizacion
     * @return 
     */
    
    private boolean cotizacionReservaDespachoAntesFactura(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_loc,a1.co_cot,a1.fe_cot, ROUND(a1.nd_tot,2) as nd_tot, a1.st_reg,a1.st_solResInv,a1.co_tipSolResInv, a1.fe_solResInv, \n";
                strSql+="        a1.st_pedOtrBod, a1.st_solEnvPed, a1.st_autSolResInv, a1.st_autEnvPed, a5.tx_desLar, a5.tx_tipResInv, a5.st_facPriDiaLabSigMes,a5.tx_momDes  \n";
                strSql+=" FROM tbm_cabCotVen as a1 ";
                strSql+=" INNER JOIN tbm_tipSolResInv as a5 ON (a1.co_tipSolResInv=a5.co_tipSolResInv AND a5.st_reg='A') ";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" AND (a1.st_solResInv IS NOT NULL) AND ";
                strSql+="       a1.st_autSolResInv LIKE ('A') AND a5.tx_momDes='A'  ";
                strSql+=" ORDER BY a1.co_emp,a1.co_loc,a1.co_cot ";
                System.out.println("cotizacionReservaDespachoAntesFactura " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e) { 
           blnRes = false;
           objUti.mostrarMsgErr_F1(this, e);  
       }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }
    
 
    private ZafStkInv objStkInv;
    
    
    
// <editor-fold defaultstate="collapsed" desc=" /* José Marín: Comentado el 21/Nov/2018 no hacia nada  */ ">
//    private boolean modificaDetalleCotizacionCantidadesLocalesRemotasAntes(java.sql.Connection con, int intCodEmp, int intCodLoc, int intCodCot){
//        boolean blnRes=true, blnIsInSolTra=false;
//        java.sql.Statement stmLoc,stmLoc01;
//        java.sql.ResultSet rstLoc,rstCot;
//        String strCadena="",strUpdate="";
//        int intCodItm;
//        double dblCan=0.00,dblCanCot=0.00;
//         arlDatStkInvItm = new ArrayList(); 
//        try{
//            if(con!=null){
//                objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);
//                stmLoc=con.createStatement();
//                stmLoc01=con.createStatement();
//                strCadena="";
//                strCadena+=" SELECT b.co_itm, CASE WHEN a.nd_can IS NULL THEN 0 else a.nd_can END as nd_can \n";
//                strCadena+=" FROM ( \n";
//                strCadena+="    SELECT a3.co_itm, SUM(a3.nd_can) as nd_can \n";
//                strCadena+="    FROM tbm_cabSegMovInv as a1 ";
//                strCadena+="    INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND   \n";
//                strCadena+="                                        a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc)  /*EL INGRESO */ \n";
//                strCadena+="    INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
//                strCadena+="    LEFT OUTER JOIN ( \n";
//                strCadena+="                    SELECT a1.st_genOrdDes, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.co_empRel,a3.co_locRel, \n";
//                strCadena+="                            a3.co_tipDocRel, a3.co_docRel,a3.co_regRel \n";
//                strCadena+="                    FROM tbm_cabMovInv as a1  /* EL EGRESO */ \n";
//                strCadena+="                    INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND  \n";
//                strCadena+="                                                        a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
//                strCadena+="                    INNER JOIN tbr_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND  \n";
//                strCadena+="                                                    a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg) \n";
//                strCadena+="                    INNER JOIN ( \n";
//                strCadena+="                          SELECT a1.co_seg, a1.co_empRelCabSolTraInv, a1.co_locRelCabSolTraInv, a1.co_tipDocRelCabSolTraInv, a1.co_docRelCabSolTraInv \n";
//                strCadena+="                          FROM tbm_cabSegMovInv as a1 \n";
//                strCadena+="                          WHERE a1.co_seg = ( \n";
//                strCadena+="                                            SELECT a1.co_seg  \n";
//                strCadena+="                                            FROM tbm_cabSegMovInv as a1  \n";
//                strCadena+="                                            WHERE a1.co_empRelCabCotVen="+intCodEmp+" and a1.co_locRelCabCotVen="+intCodLoc+" AND  a1.co_cotRelCabCotVen="+intCodCot+" GROUP BY co_seg \n";
//                strCadena+="                        ) AND a1.co_empRelCabSolTraInv IS NOT NULL  \n";
//                strCadena+="                         /* PARA OBTENER LAS SOLICITUDES GUARDADAS EN CAMPOS RELACIONALES de tbm_cabMovInv */  \n";
//                strCadena+="                    ) AS a4 ON (a1.co_empRelCabSolTrainv=a4.co_empRelCabSolTraInv AND a1.co_locRelCabSolTraInv=a4.co_locRelCabSolTraInv AND \n";
//                strCadena+="                            a1.co_tipDocRelCabSolTraInv=a4.co_tipDocRelCabSolTraInv AND a1.co_docRelCabSolTraInv=a4.co_docRelCabSolTraInv)  \n";
//                strCadena+="    ) as a4 ON(a3.co_emp=a4.co_empRel AND a3.co_loc=a4.co_locRel AND a3.co_tipDoc=a4.co_tipDocRel AND  \n";
//                strCadena+="                a3.co_doc=a4.co_docRel AND a3.co_reg=a4.co_regRel)   \n";
//                strCadena+="    WHERE a1.co_seg=( \n";
//                strCadena+="                    SELECT co_seg  \n";
//                strCadena+="                    FROM tbm_cabSegMovINv as a1 \n";
//                strCadena+="                    INNER JOIN tbm_cabCotVen as a4 ON (a1.co_empRelCabCotVen=a4.co_emp AND a1.co_locRelCabCotVen=a4.co_loc \n";
//                strCadena+="                                                        AND a1.co_cotRelCabCotVen=a4.co_cot) \n";
//                strCadena+="                    WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_cot="+intCodCot+" GROUP BY co_seg \n";
//                strCadena+="                   ) and a3.nd_can > 0 AND ( a2.st_genOrdDes = 'S' OR a4.st_genOrdDes = 'S')   \n";
//                strCadena+="    GROUP BY  a3.co_itm   \n";
//                strCadena+=" ) as a  \n";
//                strCadena+=" RIGHT JOIN ( \n";
//                strCadena+="    SELECT a2.co_itm, a2.nd_can \n";
//                strCadena+="    FROM tbm_cabCotVen as a1 ";
//                strCadena+="    INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
//                strCadena+="    WHERE a1.co_emp="+intCodEmp+" and a1.co_loc="+intCodLoc+" and a1.co_cot="+intCodCot+" AND a2.tx_codAlt NOT LIKE '%L'  \n";
//                strCadena+=" ) as b ON (a.co_itm=b.co_itm)   \n";
//                System.out.println("RESERVAS 0.2: " + strCadena);
//                rstLoc=stmLoc.executeQuery(strCadena);
//                while(rstLoc.next()){
//                    if(rstLoc.getDouble("nd_can")>0){  /// TENEMOS ALGUN PEDIDO JM 4/Agosto/2017
//                        blnIsInSolTra=true;
//                    }
//                    intCodItm=rstLoc.getInt("co_itm");
//                    dblCan=rstLoc.getDouble("nd_can");
//                    strCadena="";
//                    strCadena+=" SELECT a1.co_emp, a1.co_loc,a2.co_reg, a2.co_itm, a2.nd_can    ";
//                    strCadena+=" FROM tbm_cabCotVen as a1 ";
//                    strCadena+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot)";
//                    strCadena+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" ";
//                    strCadena+="        AND a2.co_itm="+intCodItm+" ";
//                    strCadena+=" ORDER BY a1.co_emp, a1.co_loc,a2.co_reg";
//                    System.out.println("Items:");
//                    rstCot=stmLoc01.executeQuery(strCadena);
//                    while(rstCot.next()){
//                        dblCanCot=rstCot.getDouble("nd_can");
//                        if(dblCanCot >= dblCan){
//                            strUpdate+=" UPDATE tbm_detCotVen SET nd_canLoc="+(dblCanCot-dblCan)+", nd_canRem="+dblCan+" WHERE ";
//                            strUpdate+=" co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_cot="+intCodCot+" AND co_reg="+rstCot.getInt("co_reg")+"; \n";
//                            if(!generaNuevoContenedorItemsMovimientoStock(intCodEmp,intCodItm,(dblCanCot-dblCan),bodegaPredeterminadaPorEmpresa(intCodEmp,intCodLoc))){
//                                blnRes=false;
//                            }
//                            dblCan=0.00;
//                        }
//                        else{/*por si el item esta varias veces en la cotizacion*/
//                            strUpdate+=" UPDATE tbm_detCotVen SET nd_canLoc=0.00, nd_canRem="+(dblCanCot)+" WHERE ";
//                            strUpdate+=" co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_cot="+intCodCot+" AND co_reg="+rstCot.getInt("co_reg")+";  \n";
//                            dblCan=dblCan-dblCanCot;
//                        }
//                    }
//                    rstCot.close();
//                    rstCot=null;
//                }
//                if(blnIsInSolTra){
//                    /*Si la cantidad se reserva se saca solo de la cantidad disponible, pero se deja en el stockActual hasta que se genera la factura...*/
//                    if(objStkInv.actualizaInventario(con, objParSis.getCodigoEmpresa(),INT_ARL_STK_INV_CAN_DIS, "-", 0, arlDatStkInvItm)){
//                        if(objStkInv.actualizaInventario(con, objParSis.getCodigoEmpresa(),INT_ARL_STK_INV_CAN_RES, "+", 0, arlDatStkInvItm)){ 
//                            System.out.println("ZafVen42.Mover Inventario ZafStkInv....  ");
//                        }else{blnRes=false;}
//                    }else{blnRes=false;}
//                }
//                 
//                System.out.println("strUpdate" + strUpdate);
//                stmLoc.executeUpdate(strUpdate);
//                rstLoc.close();
//                rstLoc=null;
//                stmLoc.close();
//                stmLoc=null;
//                stmLoc01.close();
//                stmLoc01=null;
//            }
//        }
//        catch (Exception Evt) {
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, Evt);
//        }
//        return blnRes;
//
//    }
    //</editor-fold>
    
    
    
    private boolean itemsSinIngresoReservaLocal(java.sql.Connection con, int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        double dblCan=0.00;
         arlDatStkInvItm = new ArrayList(); 
        try{
            if(con!=null){
                objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);
                stmLoc=con.createStatement();
                 
                strSql="";
                strSql+=" SELECT X.* \n";
                strSql+=" FROM ( \n";
                strSql+="       SELECT a2.co_emp , a2.co_loc,a2.co_cot,a8.co_bodGrp, a2.co_itm,a2.tx_codAlt,a2.nd_can,  \n";
                strSql+="              CASE WHEN a4.nd_canIng IS NULL THEN 0 ELSE a4.nd_canIng END as nd_canEgrDes,  \n";
                strSql+="              (a2.nd_can- CASE WHEN a4.nd_canIng IS NULL THEN 0 ELSE a4.nd_canIng END) as nd_canEgrBod   \n";
                strSql+="       FROM tbm_cabCotVen as a1   \n";
                strSql+="       INNER JOIN tbm_detCotVen AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot)  \n";
                strSql+="       LEFT OUTER JOIN (  \n";
                strSql+="                   SELECT x.co_emp, x.co_itm, SUM(x.nd_can) as nd_canIng  \n";
                strSql+="                   FROM(  \n";
                strSql+="                           SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc,a3.co_reg,   \n";
                strSql+="                                   a3.co_itm, a3.nd_can   \n";
                strSql+="                           FROM tbm_cabMovInv as a2  /*EL INGRESO */  \n";
                strSql+="                           INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND    \n";
                strSql+="                                                               a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc)  \n";
                strSql+="                           INNER JOIN (   \n";
                strSql+="                                   SELECT a1.co_emp AS co_empIng, a1.co_loc as co_locIng, a1.co_tipDoc as co_tipDocIng, a1.co_doc as co_docIng  \n";
                strSql+="                                   FROM tbm_cabMovInv as a1 \n";
                strSql+="                                   INNER JOIN tbm_cabSegMovInv as a2 ON (a1.co_emp=a2.co_empRelCabMovInv AND a1.co_loc=a2.co_locRelCabMovInv AND   \n";
                strSql+="                                                                         a1.co_tipDoc=a2.co_tipDocRelCabMovInv AND a1.co_doc=a2.co_docRelCabMovInv) \n";
                strSql+="                                   INNER JOIN (  \n";
                strSql+="                                               SELECT a1.co_seg  \n";
                strSql+="                                               FROM tbm_cabSegMovInv as a1 \n";
                strSql+="                                               INNER JOIN tbm_cabCotVen as a2 ON (a1.co_empRelCabCotVen=a2.co_emp AND a1.co_locRelCabCotVen=a2.co_loc AND  \n";
                strSql+="                                                                            a1.co_cotRelCabCotVen=a2.co_cot)   \n";
                strSql+="                                               WHERE a2.co_emp="+CodEmp+" AND a2.co_loc="+CodLoc+" AND a2.co_cot="+CodCot+"  \n";
                strSql+="                                   ) AS a3 ON (a2.co_seg=a3.co_seg) \n";
                strSql+="                                   INNER JOIN tbm_cabTipDoc as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc) \n";
	     	strSql+="                                   WHERE a1.nd_tot>0 		  \n";
                strSql+="                           ) as a7 ON (a2.co_emp=a7.co_empIng AND a2.co_loc=a7.co_locIng AND a2.co_tipDoc=a7.co_tipDocIng AND  \n";
                strSql+="                                       a2.co_doc=a7.co_docIng)   \n";
                strSql+="                           WHERE a3.nd_can>0  \n";
                strSql+="                   ) as x  \n";
                strSql+="                   GROUP BY x.co_emp, x.co_itm	  \n";
                strSql+="        ) AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm)  \n";
                strSql+="       INNER JOIN tbr_bodEmpBodGrp AS a8 ON (a2.co_emp=a8.co_emp AND a2.co_bod=a8.co_bod AND a8.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" )  \n";
		strSql+="       INNER JOIN tbm_inv as a9 ON (a2.co_emp=a9.co_emp AND a2.co_itm=a9.co_itm )  \n";
                strSql+="       WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" AND (a2.tx_codALt like '%I' OR a2.tx_codAlt like '%S') AND a9.st_ser='N' \n";			 
		strSql+="  ) as X     \n";
                strSql+="  /*       WHERE X.nd_canEgrDes=0 */           \n";
		System.out.println("itemsSinIngresoReservaLocal 0.2 (6/Sep/2017): \n" + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    dblCan=rstLoc.getDouble("nd_canEgrBod");
                    arlDatStkInvItm = new ArrayList(); 
                    if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),(dblCan),bodegaPredeterminadaPorEmpresa(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                        if(objStkInv.actualizaInventario(con, CodEmp,INT_ARL_STK_INV_CAN_DIS, "-", 0, arlDatStkInvItm)){
                            if(objStkInv.actualizaInventario(con, CodEmp,INT_ARL_STK_INV_CAN_RES, "+", 0, arlDatStkInvItm)){ 
                                System.out.println("ZafVen42.Mover itemsSinIngresoReservaLocal ZafStkInv....  ");
                            }else{blnRes=false;}
                        }else{blnRes=false;}
                    }
                    
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException Evt){ 
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;

    }
    
    private boolean modificaDetalleCotizacionCantidadesLocalesRemotasDos(java.sql.Connection con, int intCodEmp, int intCodLoc, int intCodCot){
        boolean blnRes=true, blnIsInSolTra=false;
        java.sql.Statement stmLoc,stmLoc01;
        java.sql.ResultSet rstLoc,rstCot;
        String strCadena="",strUpdate="";
        int intCodItm;
        double dblCan=0.00,dblCanCot=0.00;
         arlDatStkInvItm = new ArrayList(); 
        try{
            if(con!=null){
                objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);
                stmLoc=con.createStatement();
                stmLoc01=con.createStatement();
                strCadena ="";
                strCadena+=" SELECT b.co_itm, CASE WHEN a.nd_can IS NULL THEN 0 else a.nd_can END as nd_can \n";
                strCadena+=" FROM ( \n";
                strCadena+="    SELECT a3.co_itm, SUM(a3.nd_can) as nd_can \n";
                strCadena+="    FROM tbm_cabSegMovInv as a1 ";
                strCadena+="    INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND   \n";
                strCadena+="                                        a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc)  /*EL INGRESO */ \n";
                strCadena+="    INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
                strCadena+="    LEFT OUTER JOIN ( \n";
                strCadena+="                    SELECT a1.st_genOrdDes, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.co_empRel,a3.co_locRel, \n";
                strCadena+="                            a3.co_tipDocRel, a3.co_docRel,a3.co_regRel \n";
                strCadena+="                    FROM tbm_cabMovInv as a1  /* EL EGRESO */ \n";
                strCadena+="                    INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND  \n";
                strCadena+="                                                        a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
                strCadena+="                    INNER JOIN tbr_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND  \n";
                strCadena+="                                                    a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg) \n";
                strCadena+="                    INNER JOIN ( \n";
                strCadena+="                          SELECT a1.co_seg, a1.co_empRelCabSolTraInv, a1.co_locRelCabSolTraInv, a1.co_tipDocRelCabSolTraInv, a1.co_docRelCabSolTraInv \n";
                strCadena+="                          FROM tbm_cabSegMovInv as a1 \n";
                strCadena+="                          WHERE a1.co_seg = ( \n";
                strCadena+="                                            SELECT a1.co_seg  \n";
                strCadena+="                                            FROM tbm_cabSegMovInv as a1  \n";
                strCadena+="                                            WHERE a1.co_empRelCabCotVen="+intCodEmp+" and a1.co_locRelCabCotVen="+intCodLoc+" AND  a1.co_cotRelCabCotVen="+intCodCot+" GROUP BY co_seg \n";
                strCadena+="                        ) AND a1.co_empRelCabSolTraInv IS NOT NULL  \n";
                strCadena+="                         /* PARA OBTENER LAS SOLICITUDES GUARDADAS EN CAMPOS RELACIONALES de tbm_cabMovInv */  \n";
                strCadena+="                    ) AS a4 ON (a1.co_empRelCabSolTrainv=a4.co_empRelCabSolTraInv AND a1.co_locRelCabSolTraInv=a4.co_locRelCabSolTraInv AND \n";
                strCadena+="                            a1.co_tipDocRelCabSolTraInv=a4.co_tipDocRelCabSolTraInv AND a1.co_docRelCabSolTraInv=a4.co_docRelCabSolTraInv)  \n";
                strCadena+="    ) as a4 ON(a3.co_emp=a4.co_empRel AND a3.co_loc=a4.co_locRel AND a3.co_tipDoc=a4.co_tipDocRel AND  \n";
                strCadena+="                a3.co_doc=a4.co_docRel AND a3.co_reg=a4.co_regRel)   \n";
                strCadena+="    WHERE a1.co_seg=( \n";
                strCadena+="                    SELECT co_seg  \n";
                strCadena+="                    FROM tbm_cabSegMovINv as a1 \n";
                strCadena+="                    INNER JOIN tbm_cabCotVen as a4 ON (a1.co_empRelCabCotVen=a4.co_emp AND a1.co_locRelCabCotVen=a4.co_loc \n";
                strCadena+="                                                        AND a1.co_cotRelCabCotVen=a4.co_cot) \n";
                strCadena+="                    WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_cot="+intCodCot+" GROUP BY co_seg \n";
                strCadena+="                   ) and a3.nd_can > 0 /*AND ( a2.st_genOrdDes = 'S' OR a4.st_genOrdDes = 'S')  */ \n";
                strCadena+="    GROUP BY  a3.co_itm   \n";
                strCadena+=" ) as a  \n";
                strCadena+=" RIGHT JOIN ( \n";
                strCadena+="    SELECT a2.co_itm, a2.nd_can \n";
                strCadena+="    FROM tbm_cabCotVen as a1 ";
                strCadena+="    INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strCadena+="    WHERE a1.co_emp="+intCodEmp+" and a1.co_loc="+intCodLoc+" and a1.co_cot="+intCodCot+" AND a2.tx_codAlt NOT LIKE '%L'  \n";
                strCadena+=" ) as b ON (a.co_itm=b.co_itm)   \n";
                System.out.println("\n\n METODO DIFERENTE RESERVAS 0.3: " + strCadena);
                rstLoc=stmLoc.executeQuery(strCadena);
                while(rstLoc.next()){
                    blnIsInSolTra=true;
                    intCodItm=rstLoc.getInt("co_itm");
                    dblCan=rstLoc.getDouble("nd_can");
                    strCadena="";
                    strCadena+=" SELECT a1.co_emp, a1.co_loc,a2.co_reg, a2.co_itm, a2.nd_can    ";
                    strCadena+=" FROM tbm_cabCotVen as a1 ";
                    strCadena+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot)";
                    strCadena+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" ";
                    strCadena+="        AND a2.co_itm="+intCodItm+" ";
                    strCadena+=" ORDER BY a1.co_emp, a1.co_loc,a2.co_reg";
                    System.out.println("Items:");
                    rstCot=stmLoc01.executeQuery(strCadena);
                    while(rstCot.next()){
                        dblCanCot=rstCot.getDouble("nd_can");
                        if(dblCanCot >= dblCan){
                            strUpdate+=" UPDATE tbm_detCotVen SET nd_canLoc="+(dblCanCot-dblCan)+", nd_canRem="+dblCan+" WHERE ";
                            strUpdate+=" co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_cot="+intCodCot+" AND co_reg="+rstCot.getInt("co_reg")+"; \n";
                            if(!generaNuevoContenedorItemsMovimientoStock(intCodEmp,intCodItm,(dblCanCot-dblCan),bodegaPredeterminadaPorEmpresa(intCodEmp,intCodLoc))){
                                blnRes=false;
                            }
                            dblCan=0.00;
                        }
                        else{/*por si el item esta varias veces en la cotizacion*/
                            strUpdate+=" UPDATE tbm_detCotVen SET nd_canLoc=0.00, nd_canRem="+(dblCanCot)+" WHERE ";
                            strUpdate+=" co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_cot="+intCodCot+" AND co_reg="+rstCot.getInt("co_reg")+";  \n";
                            dblCan=dblCan-dblCanCot;
                        }
                    }
                    rstCot.close();
                    rstCot=null;
                }
                if(blnIsInSolTra){
                    /*Si la cantidad se reserva se saca solo de la cantidad disponible, pero se deja en el stockActual hasta que se genera la factura...*/
                    if(objStkInv.actualizaInventario(con, intCodEmp,INT_ARL_STK_INV_CAN_DIS, "-", 0, arlDatStkInvItm)){
                        if(objStkInv.actualizaInventario(con, intCodEmp,INT_ARL_STK_INV_CAN_RES, "+", 0, arlDatStkInvItm)){ 
                            System.out.println("ZafVen42.Mover Inventario ZafStkInv....  ");
                        }else{blnRes=false;}
                    }else{blnRes=false;}
                }
                 
                System.out.println("strUpdate" + strUpdate);
                stmLoc.executeUpdate(strUpdate);
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                stmLoc01.close();
                stmLoc01=null;
            }
        }
        catch(java.sql.SQLException Evt){ 
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;

    }

    /* NUEVO CONTENEDOR PARA ITEMS ZafStkInv MovimientoStock */

        private static final int INT_STK_INV_COD_ITM_GRP=0;
        private static final int INT_STK_INV_COD_ITM_EMP=1;
        private static final int INT_STK_INV_COD_ITM_MAE=2;    
        private static final int INT_STK_INV_COD_LET_ITM=3;     
        private static final int INT_STK_INV_CAN_ITM=4;
        private static final int INT_STK_INV_COD_BOD_EMP=5; 
        private ArrayList arlRegStkInvItm, arlDatStkInvItm;
    
        /**
         * 
         * @param intCodEmp
         * @param intCodItm
         * @param dlbCanMov
         * @param intCodBod
         * @return 
         */
        
        private boolean generaNuevoContenedorItemsMovimientoStock(int intCodEmp, int intCodItm, double dlbCanMov,int intCodBod){
        boolean blnRes=true;
        double dblAux;
        int intCodigoItemGrupo=0, intCodigoItemMaestro=0;
        String strCodTresLetras="";
        try{
            //arlDatStkInvItm = new ArrayList();
            intCodigoItemGrupo=getCodigoItemGrupo(intCodEmp,intCodItm);
            intCodigoItemMaestro=getCodigoMaestroItemGrupo(intCodEmp,intCodItm);
            strCodTresLetras=getCodigoLetraItem(intCodEmp,intCodItm);
            if(intCodigoItemGrupo==0 || intCodigoItemMaestro==0 || strCodTresLetras.equals("")){
                blnRes=false;
            }
            
            arlRegStkInvItm = new ArrayList();
            arlRegStkInvItm.add(INT_STK_INV_COD_ITM_GRP,intCodigoItemGrupo);
            arlRegStkInvItm.add(INT_STK_INV_COD_ITM_EMP,intCodItm);
            arlRegStkInvItm.add(INT_STK_INV_COD_ITM_MAE,intCodigoItemMaestro);
            arlRegStkInvItm.add(INT_STK_INV_COD_LET_ITM, strCodTresLetras);
            dblAux=dlbCanMov;
            if(dblAux<0){
                dblAux=dblAux*-1;
            }
            arlRegStkInvItm.add(INT_STK_INV_CAN_ITM,dblAux );
            arlRegStkInvItm.add(INT_STK_INV_COD_BOD_EMP,intCodBod );
            arlDatStkInvItm.add(arlRegStkInvItm);
            
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
        }
        return blnRes;
    } 
        
        /**
         * 
         * @param intCodEmp
         * @param intCodItm
         * @return 
         */
        
         private String getCodigoLetraItem(int intCodEmp, int intCodItm){
            String strCodLetItm="";
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            String strCadena;
            try{
                conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conLoc != null) {
                    stmLoc=conLoc.createStatement();
                    strCadena="";
                    strCadena+=" SELECT CASE WHEN tx_codAlt2 IS NULL THEN tx_codAlt ELSE tx_codAlt2 END AS tx_codAlt2 \n";
                    strCadena+=" FROM tbm_inv as x1 \n";
                    strCadena+=" WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+intCodItm+" \n";
                    rstLoc=stmLoc.executeQuery(strCadena);
                    if(rstLoc.next()){
                        strCodLetItm=rstLoc.getString("tx_codAlt2");
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
            catch(java.sql.SQLException Evt){ 
                strCodLetItm="";
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                strCodLetItm="";
            }
            return strCodLetItm;
        }
        
         /**
          * 
          * @param intCodEmp
          * @param intCodItm
          * @return 
          */
         
        private int getCodigoItemGrupo(int intCodEmp, int intCodItm){
            int intCodItmGru=0;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            String strCadena;
            try{
                conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conLoc != null) {
                    stmLoc=conLoc.createStatement();
                    strCadena="";
                    strCadena+=" SELECT co_itm \n";
                    strCadena+=" FROM tbm_equInv as x1 \n";
                    strCadena+=" WHERE x1.co_itmMae = ( \n";
                    strCadena+="                        select co_itmMae  \n";
                    strCadena+="                        from tbm_Equinv as a1 \n";
                    strCadena+="                        where co_emp="+intCodEmp+" and co_itm="+intCodItm+")  \n";
                    strCadena+=" and x1.co_emp="+objParSis.getCodigoEmpresaGrupo()+" \n";
                    rstLoc=stmLoc.executeQuery(strCadena);
                    if(rstLoc.next()){
                        intCodItmGru=rstLoc.getInt("co_itm");
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
            catch(java.sql.SQLException Evt){ 
                intCodItmGru=-1;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                intCodItmGru=-1;
            }
            return intCodItmGru;
        }
        
        /**
         * 
         * @param intCodEmp
         * @param intCodItm
         * @return 
         */
        private int getCodigoMaestroItemGrupo(int intCodEmp, int intCodItm){
            int intCodItmMae=0;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            String strCadena;
            try{
                conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conLoc != null) {
                    stmLoc=conLoc.createStatement();
                    strCadena="";
                    strCadena+=" SELECT x1.co_itmMae \n";
                    strCadena+=" FROM tbm_equInv as x1 \n";
                    strCadena+=" WHERE x1.co_emp="+intCodEmp+" and x1.co_itm="+intCodItm+" \n";
                    rstLoc=stmLoc.executeQuery(strCadena);
                    if(rstLoc.next()){
                        intCodItmMae=rstLoc.getInt("co_itmMae");
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
            catch(java.sql.SQLException Evt){ 
                intCodItmMae=-1;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                intCodItmMae=-1;
            }
            return intCodItmMae;
        }
        
    
    final int INT_DAT_COD_EMP =0; 
    final int INT_DAT_COD_LOC =1;       
    final int INT_DAT_COD_COT =2;       
    final int INT_DAT_COD_FOR_PAG=3;       
    final int INT_DAT_OBS=4; 
    final int INT_DAT_COD_PRV=5;  
    final int INT_DAT_COD_FOR_DES=6; 
    
    /**
     * 
     * @param conLoc
     * @return 
     */
    
    private boolean modificarCotizacionCompras(java.sql.Connection conLoc){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        try{          
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                for(int i=0;i<arlDatCodL.size();i++){
                   strSql+=" UPDATE tbm_detCotVen SET co_forPagCom="+ objUti.getIntValueAt(arlDatCodL, i, INT_DAT_COD_FOR_PAG)+" , ";
                   strSql+="                          tx_obs1Com="+ objUti.codificar(objUti.getStringValueAt(arlDatCodL, i, INT_DAT_OBS),0)  +", ";
                   strSql+="                          co_forRetCom="+objUti.getIntValueAt(arlDatCodL, i, INT_DAT_COD_FOR_DES)+"  \n";
                   strSql+=" WHERE co_emp="+objUti.getIntValueAt(arlDatCodL, i, INT_DAT_COD_EMP)+" AND ";
                   strSql+=" co_loc="+ objUti.getIntValueAt(arlDatCodL, i, INT_DAT_COD_LOC) +" AND ";
                   strSql+=" co_cot="+ objUti.getIntValueAt(arlDatCodL, i, INT_DAT_COD_COT) +" AND ";
                   strSql+=" co_prv="+ objUti.getIntValueAt(arlDatCodL, i, INT_DAT_COD_PRV) +" ; \n";
                }
                System.out.println("modificarCotizacionCompras::: \n " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }
        catch(java.sql.SQLException Evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch(Exception Evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
        return blnRes;
    }
    
    /**
     * 
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodCot
     * @return 
     */
    private boolean existenTerminalesPedidos(int intCodEmp,int intCodLoc,int intCodCot){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot, a1.co_reg, a1.co_itm, a1.tx_codALt \n";
                strSql+=" FROM tbm_detCotVen as a1 \n";
                strSql+=" INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSql+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" AND  \n";
                strSql+="       (a1.tx_codAlt like '%I' OR a1.tx_codAlt like '%S') AND a2.st_ser='N' \n";
                System.out.println("existenTerminalesPedidos: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
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
    
    /**
     * 
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodCot
     * @return 
     */
    private boolean existenCodigosL(int intCodEmp,int intCodLoc,int intCodCot){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot, a1.co_reg, a1.co_itm, a1.tx_codALt \n";
                strSql+=" FROM tbm_detCotVen as a1 \n";
                strSql+=" INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) ";
                strSql+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" AND a1.tx_codAlt like '%L' AND \n";
                strSql+="       a2.st_ser='N'  \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
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
    
     /**
      * 
      * @param conExt
      * @param intCodEmp
      * @param intCodLoc
      * @param intCodCot
      * @return 
      */
    private boolean insertarCotizacionSeguimiento(java.sql.Connection conExt,int intCodEmp,int intCodLoc,int intCodCot){
        boolean blnRes=true;
        java.sql.Statement stmLoc, stmLocIns;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        int intCodSeg=0;
        String strCadena;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conExt!=null && conLoc!=null){
                stmLoc = conExt.createStatement();
                stmLocIns = conLoc.createStatement();
                strCadena = "";
                strCadena+= " SELECT CASE WHEN MAX(co_seg) IS NULL THEN 1 ELSE (MAX(co_seg))+1 END AS co_seg from tbm_cabSegMovInv ";
                rstLoc = stmLocIns.executeQuery(strCadena);
                if (rstLoc.next()) {
                    intCodSeg=rstLoc.getInt("co_seg");
                }

            /* INSERCION DE SEGUIMIENTO DIRECTA A LA BASE  */
                
                strCadena="";
                strCadena+=" INSERT INTO tbm_cabSegMovInv (co_seg, co_reg) \n";
                strCadena+=" VALUES ( "+intCodSeg+",1);";
                System.out.println("Directo.... " + strCadena);
                stmLocIns.executeUpdate(strCadena);
                stmLocIns.close();
            /* INSERCION DE SEGUIMIENTO DIRECTA A LA BASE  */

                strCadena="";
                strCadena+=" UPDATE tbm_cabSegMovInv SET co_empRelCabCotVen="+intCodEmp+",co_locRelCabCotVen="+intCodLoc;
                strCadena+="                            ,co_cotRelCabCotVen="+intCodCot;
                strCadena+=" WHERE co_seg="+intCodSeg+" AND co_reg=1";
                System.out.println("Transaccion.... " + strCadena);
                stmLoc.executeUpdate(strCadena);

                stmLoc.close();
                rstLoc.close();
                stmLocIns=null;
                stmLoc=null;
                rstLoc=null;
            }
            conLoc.close();
            conLoc=null;

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
    
    /**
     * 
     * @param con
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodCot
     * @return 
     */
     private boolean isSolTraInv(java.sql.Connection con, int intCodEmp, int intCodLoc, int intCodCot){
        java.sql.Statement stmLoc,stmLoc01;
        java.sql.ResultSet rstLoc,rstLoc01;
        String strCadena;
        boolean blnRes=false;
        try{
            if(con!=null){
                stmLoc = con.createStatement();
                stmLoc01= con.createStatement();
                strCadena="";
                strCadena+=" SELECT * FROM tbm_cabSegMovInv where co_empRelCabCotVen="+intCodEmp;
                strCadena+=" AND co_locRelCabCotVen="+intCodLoc+" AND co_cotRelCabCotVen="+intCodCot;
                System.out.println("isSolTraInv: " + strCadena);
                rstLoc=stmLoc.executeQuery(strCadena);
                if(rstLoc.next()){
                    strCadena=" SELECT co_empRelCabSolTraInv, co_locRelCabSolTraInv, co_tipDocRelCabSolTraInv, co_docRelCabSolTraInv  \n";
                    strCadena+=" FROM tbm_cabSegMovInv  \n";
                    strCadena+=" WHERE co_seg="+rstLoc.getInt("co_seg")+" AND co_empRelCabSolTraInv IS NOT NULL \n";
                    System.out.println("isSolTraInv 2: " + strCadena);
                    rstLoc01=stmLoc01.executeQuery(strCadena);
                    if(rstLoc01.next()){ // TIENE UNA SOLICITUD
                        blnRes=true;
                    }
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
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
/* CONSTANTES PARA CONTENEDOR A ENVIAR SOLICITUD DE TRANSFERENCIA JoséMario 26/Abril/2015  */
   final int INT_ARL_SOL_TRA_COD_BOD_ING=0;
   final int INT_ARL_SOL_TRA_COD_BOD_EGR=1;
   final int INT_ARL_SOL_TRA_ITM_GRP=2;
   final int INT_ARL_SOL_TRA_CAN=3;
   final int INT_ARL_SOL_PES_UNI=4;
   final int INT_ARL_SOL_PES_TOT=5;
   
   private boolean blnGenSolTra=false, blnSolTraInv=false, blnGenSolTraCodL=false;
   private ArrayList arlSolTraDat, arlSolTraReg;
   private ArrayList arlDatSolTra;
   /**
    * 
    * @param conExt
    * @param intCodEmp
    * @param intCodLoc
    * @param intCodCot
    * @return 
    */
    private boolean realizaMovimientoEntreEmpresas(java.sql.Connection conExt, int intCodEmp, int intCodLoc, int intCodCot){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        double dblDisponibleEmpresa=0,dblCanSol=0, dblDisponibleGRUPO=0;
        try{
            if(conExt!=null){
                arlDat = new ArrayList();
                arlSolTraDat = new ArrayList();
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT a7.co_bodGrp as co_bodGrp_EmpEgr, a7.co_bod as co_bod_X_EmpEgr,  a6.co_bodGrp as co_bodGrp_EmpCot,  \n";
                strSql+="        a10.co_itm as co_itmGrp, a8.co_itmMae, a3.co_emp,a3.co_loc,a3.co_cot,    \n";
                strSql+="        a2.co_itm,a9.co_bod as co_bodIngEmp,a9.tx_nom as tx_nomBod ,  \n";
                strSql+="        SUM(a3.nd_canAut) as nd_canAut \n";
                strSql+=" FROM tbm_cabCotVen AS a1 \n";
                strSql+=" INNER JOIN tbm_detCotVen AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+=" INNER JOIN tbm_pedOtrBodCotVen AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND  \n";
                strSql+="                                          a2.co_cot=a3.co_cot AND a2.co_reg=a3.co_reg) \n";
                strSql+=" INNER JOIN tbm_inv AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm) \n";
                strSql+=" INNER JOIN tbr_bodloc as a5 ON (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc ) \n";
                strSql+=" INNER JOIN tbr_bodEmpBodGrp as a6 ON (a5.co_emp=a6.co_emp AND a5.co_bod=a6.co_bod) \n";
                strSql+=" INNER JOIN tbr_bodEmpBodGrp as a7 ON (a3.co_empRel=a7.co_emp AND a3.co_bodRel=a7.co_bod) \n";
                strSql+=" INNER JOIN tbm_equInv as a8 ON (a2.co_emp=a8.co_emp AND a2.co_itm=a8.co_itm) \n";
                strSql+=" INNER JOIN tbm_bod as a9 ON (a2.co_emp=a9.co_emp AND a2.co_bod=a9.co_bod) \n";
                strSql+=" INNER JOIN tbm_equInv as a10 ON (a8.co_itmMae=a10.co_itmMae AND a10.co_emp="+objParSis.getCodigoEmpresaGrupo()+" ) \n";
                strSql+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" AND \n";
                strSql+="       a1.st_autSolResInv='A' AND a4.st_ser='N' /* No es un servicio */ AND \n";
                strSql+="       a5.st_reg='P' AND a6.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" AND \n";
                strSql+="       a7.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" AND (a2.tx_codAlt like '%S' OR a2.tx_codAlt like '%I') \n";
                strSql+=" GROUP BY a7.co_bodGrp, a7.co_bod ,  a6.co_bodGrp  ,a10.co_itm  , a8.co_itmMae, a3.co_emp,a3.co_loc,a3.co_cot,a2.co_itm,a9.co_bod, a9.tx_nom \n";
                strSql+=" ORDER BY a7.co_bodGrp,a10.co_itm  \n";
                strSql+=" /* AGRUPADO PEDIDO POR BODEGA MIRAR SI DISPONIBLE EN LA MISMA EMPRESA, DIFERENTE BODEGA PEDIR */\n";
                System.out.println("realizaMovimientoEntreEmpresas \n" + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    dblDisponibleEmpresa=objDatItm.getDisponibleItemEmpresa(conExt, intCodEmp, rstLoc.getInt("co_itmMae"), rstLoc.getInt("co_bodGrp_EmpEgr") );
                    dblDisponibleGRUPO=objDatItm.getDisponibleItemGrupo(conExt, rstLoc.getInt("co_itmMae"), rstLoc.getInt("co_bodGrp_EmpEgr") );
                    System.out.println("EMPRESA: "+intCodEmp+" ITEM: "+rstLoc.getInt("co_itmMae")+" BODEGA: "+rstLoc.getInt("co_bodGrp_EmpEgr")+ " DISPONIBLE "+dblDisponibleEmpresa);
                    System.out.println("GRUPO: ITEM: "+rstLoc.getInt("co_itmMae")+" BODEGA: "+rstLoc.getInt("co_bodGrp_EmpEgr")+ " DISPONIBLE "+dblDisponibleGRUPO);
                    System.out.println("solicitado: " + rstLoc.getDouble("nd_canAut"));    
                    if(objDatBod.getCodigoBodegaGrupo(intCodEmp, objDatBod.getbodegaPredeterminadaPorLocal(intCodEmp, intCodLoc))== rstLoc.getInt("co_bodGrp_EmpEgr")   ){
                        if(dblDisponibleEmpresa>=rstLoc.getDouble("nd_canAut")){
                            dblCanSol=0;
                        }
                        else{
                            if(dblDisponibleGRUPO>=rstLoc.getDouble("nd_canAut")){
                                dblCanSol=rstLoc.getDouble("nd_canAut")-dblDisponibleEmpresa;
                            }
                            else{
                                System.out.println("No hay disponible");
                                return false;
                            }
                        }
                    }
                    else{
                        if(dblDisponibleGRUPO>=rstLoc.getDouble("nd_canAut")){
                            dblCanSol=rstLoc.getDouble("nd_canAut")-0.00;
                        }
                        else{
                            System.out.println("No hay disponible");
                            return false;
                        }
                    }
                    
                    if(dblCanSol<0){
                        dblCanSol=dblCanSol*-1;
                    }
                    if(dblCanSol>0){
                        System.out.println("CANTIDAD SOLICITADA: " + rstLoc.getDouble("nd_canAut")+ " CANTIDAD DISPONIBLE EN BODEGA: "+dblDisponibleEmpresa+" PEDIDO: "+dblCanSol);
                        arlReg = new ArrayList();
                        arlReg.add(INT_ARL_CON_COD_EMP, rstLoc.getInt("co_emp"));
                        arlReg.add(INT_ARL_CON_COD_LOC, rstLoc.getInt("co_loc"));
                        arlReg.add(INT_ARL_CON_COD_TIP_DOC, intCodTipDocFacEle_ZafVen01_06);
                        arlReg.add(INT_ARL_CON_COD_BOD_GRP, rstLoc.getInt("co_bodGrp_EmpEgr")); // BODEGA DE GRUPO DE DONDE SALE LA MERCADERIA
                        arlReg.add(INT_ARL_CON_COD_ITM, rstLoc.getInt("co_itm"));  // 1 
                        arlReg.add(INT_ARL_CON_COD_ITM_MAE, rstLoc.getInt("co_itmMae"));  //14 
                        arlReg.add(INT_ARL_CON_COD_BOD, rstLoc.getInt("co_bod_X_EmpEgr")); // BODEGA POR EMPRESA DE DONDE SALE LA MERCADERIA
                        arlReg.add(INT_ARL_CON_NOM_BOD, rstLoc.getString("tx_nomBod"));
                        arlReg.add(INT_ARL_CON_CAN_COM, /*rstLoc.getDouble("nd_canAut") JM 18/08/2017 */dblCanSol);  // CAntidad para la necesidad
                        arlReg.add(INT_ARL_CON_CHK_CLI_RET, "N");  // Cliente Retira
                        arlReg.add(INT_ARL_CON_EST_BOD, "A");  // A U C   Para saber si necesita pedir autorizaciones o debe ser cliente retira
                        arlReg.add(INT_ARL_CON_ING_EGR_FIS_BOD, "IE");
                        arlReg.add(INT_ARL_CON_COD_BOD_ING_MER,rstLoc.getInt("co_bodGrp_EmpCot")); // BODEGA DE GRUPO DONDE INGRESA LA MERCADERIA
                        arlDat.add(arlReg);

                        arlSolTraReg = new ArrayList();
                        arlSolTraReg.add(INT_ARL_SOL_TRA_COD_BOD_ING, rstLoc.getInt("co_bodGrp_EmpCot")); // BODEGA DE GRUPO
                        arlSolTraReg.add(INT_ARL_SOL_TRA_COD_BOD_EGR, rstLoc.getInt("co_bodGrp_EmpEgr"));  //BODEGA DE GRUPO DE DONDE SALE LA MERCA                                 
                        arlSolTraReg.add(INT_ARL_SOL_TRA_ITM_GRP, rstLoc.getInt("co_itmGrp") );  //14 
                        arlSolTraReg.add(INT_ARL_SOL_TRA_CAN, /*rstLoc.getDouble("nd_canAut") JM 18/08/2017 */dblCanSol); // 
                        arlSolTraReg.add(INT_ARL_SOL_PES_UNI, obtenerPesoUnidad(rstLoc.getInt("co_itm") ) ); // PESO POR ITEM
                        arlSolTraReg.add(INT_ARL_SOL_PES_TOT, rstLoc.getDouble("nd_canAut") *  
                                obtenerPesoUnidad(rstLoc.getInt("co_itm") )); // PESO TOTAL
                        arlSolTraDat.add(arlSolTraReg);
                        blnGenSolTra=true;
                        blnRes=false; // Si se intenta generar el contenedor debe generar la solicitud para poder pasar      
                    }
                                   
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
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
     * 
     * @param intCodItm
     * @return 
     */
    
    private double obtenerPesoUnidad(int intCodItm){
        double dblPesItm=0;
        String strSql;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc; 
        java.sql.ResultSet rstLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql=" select CASE WHEN a1.nd_pesItmKgr IS NULL THEN 0 ELSE a1.nd_pesItmKgr END AS nd_pesItmKgr ";
                strSql+=" from tbm_inv as a1 ";
                strSql+=" where a1.co_emp="+objParSis.getCodigoEmpresaGrupo()+" AND ";
                strSql+=" a1.co_itm="+intCodItm;
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    dblPesItm = rstLoc.getDouble("nd_pesItmKgr");
                }
                conLoc.close();
                conLoc=null;
                rstLoc.close();
                stmLoc.close();
                stmLoc = null;
                rstLoc = null;
            }
        }
        catch(java.sql.SQLException Evt){ 
            objUti.mostrarMsgErr_F1(null, Evt); 
            dblPesItm=0;
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(null, Evt);  
            dblPesItm=0;
        }
        return dblPesItm;
    }
    
    /**
     * 
     * @param conn
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodCot
     * @return 
     */
    
    private boolean updateCotizacionProcesoSolicitaTransferenciaInventario(java.sql.Connection conn,int intCodEmp, int intCodLoc, int intCodCot){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
         
        String strCadena;
        try{
            if(conn!=null){
                stmLoc = conn.createStatement();
                strCadena ="";
                strCadena+=" UPDATE tbm_cabCotVen set st_reg='E' WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc;
                strCadena+=" AND co_cot="+intCodCot+"; ";
                System.out.println("UPDATE: " + strCadena);
                stmLoc.executeUpdate(strCadena);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
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
    
    /**
     * 
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodCot
     * @return 
     */
    
    private boolean validaStock(int intCodEmp,int intCodLoc,int intCodCot){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        String strItem="",strMsg;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a5.co_itmMae, a6.co_bodGrp, a7.tx_codAlt, a7.tx_CodAlt2, SUM(a1.nd_can) as nd_can   \n";
                strSql+=" FROM  tbm_pedOtrBodCotVen as a1   \n";
                strSql+=" INNER JOIN tbm_cabCotVen as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_cot=a3.co_cot) \n";
                strSql+=" INNER JOIN tbm_detCotVen as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_cot=a4.co_cot AND a1.co_reg=a4.co_reg)  \n";
                strSql+=" INNER JOIN tbm_equInv AS a5 ON (a1.co_empRel=a5.co_emp AND a1.co_itmRel=a5.co_itm)  \n";
                strSql+=" INNER JOIN tbm_inv as a7 ON (a5.co_Emp=a7.co_emp AND a5.co_itm=a7.co_itm)\n";
                strSql+=" INNER JOIN tbr_bodEmpBodGrp as a6 ON (a1.co_empRel=a6.co_emp AND a1.co_bodRel=a6.co_bod) \n";
                strSql+=" WHERE a1.co_emp="+intCodEmp+" and a1.co_loc="+intCodLoc+" and a1.co_cot="+intCodCot+"  \n";
                strSql+=" GROUP BY a5.co_itmMae, a6.co_bodGrp, a7.tx_codAlt, a7.tx_CodAlt2  \n";
                strSql+=" \n";
                System.out.println("validaStock: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    if(rstLoc.getDouble("nd_can")>objDatItm.getDisponibleItemGrupo(conLoc, rstLoc.getInt("co_itmMae"), rstLoc.getInt("co_bodGrp"))){
                        /*      GENERA NEGATIVOS        */
                       blnRes=false;
                       strItem+="<tr><td>" +  rstLoc.getString("tx_codAlt") + " </td>";
                       strItem+=" <td>" + objUti.redondear(objDatItm.getDisponibleItemGrupo(conLoc, rstLoc.getInt("co_itmMae"), rstLoc.getInt("co_bodGrp")), objParSis.getDecimalesMostrar())+ " </td>";
                       strItem+=" <td>" + objUti.redondear(rstLoc.getString("nd_can"), objParSis.getDecimalesMostrar()) + " </td> ";
                       strItem+=" </tr> ";
                    }
                }
                
                if(!blnRes){
                    strMsg="<html> La Cotización posee Items con cantidades insuficientes. <BR><BR>" ;// CAMBIA
                    strMsg+=" <table BORDER=1><tr><td> ITEM </td> <td> Disponible </td><td> Can.Sol. </td>";
                    strMsg+=""+ strItem + "    ";
                    strMsg+=" </table><BR>";
                    strMsg+="No se puede realizar esta operación. <html>";
                    System.out.println(strMsg);
                    String strTit="Mensaje del sistema Zafiro";
                    JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
                }
                
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
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
    
      /**
     * 
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodCot
     * @return 
     */
    
    private boolean validaControlesCxC(int CodEmp,int CodLoc,int CodCot){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_loc, Null AS co_tipDoc, Null AS tx_desCor, Null AS tx_desLar, a1.co_cot AS co_doc, \n";
                strSql+="       a1.co_cot AS ne_numDoc, a1.fe_cot AS fe_doc, a1.co_cli, a2.tx_ide, a2.tx_nom AS tx_nomCli, a1.nd_tot, a2.tx_obsCxC, \n";
                strSql+="       a1.co_tipSolResInv, a3.tx_tipResInv \n";
                strSql+=" FROM tbm_cabCotVen AS a1  \n";
                strSql+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli) \n";
                strSql+=" INNER JOIN tbm_tipSolResInv as a3 ON (a1.co_tipSolResInv=a3.co_tipSolResInv AND a3.st_reg='A') \n ";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" AND a1.st_reg IN ('P','D') AND a3.tx_tipResInv='F' \n";
                strSql+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_cot \n";
                System.out.println("validaControlesCxC: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    blnRes=false;
//                    Herramientas.ZafHer03.ZafHer03 objHer03 = new Herramientas.ZafHer03.ZafHer03(objParSis);
//                    this.getParent().add(objHer03);
//                    objHer03.butConsultar();
//                    objHer03.show();
                    /* JM Modificacion solicitada por FRuiz al 19/Sep/2017 */
                    Herramientas.ZafHer03.ZafHer03_01 objHer03_01 = new Herramientas.ZafHer03.ZafHer03_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis,CodEmp,CodLoc,CodCot);
                    objHer03_01.show();
                    if(objHer03_01.GuardoOk()){
                        blnRes=true;
                    }
                    else{
                        MensajeInf("La operacion GUARDAR no se ha podido realizar con exito. (CxC)");
                    }
                    objHer03_01 = null;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
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
    
    
    /**
     * st_autSolResInv null=No aplica;P=Pendiente;A=Autorizada;D=Denegada 
     * st_solResInv null=No tiene;P=Pendiente;C=Completa 
     * 
     * @param conExt
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodCot
     * @return 
     */
    private boolean cambiarEstadoCotizacionDenegada(java.sql.Connection conExt,int intCodEmp,int intCodLoc,int intCodCot){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" UPDATE tbm_cabCotVen SET st_autSolResInv='D',st_solResInv='C',co_usrIngAutSolResInv="+objParSis.getCodigoUsuario()+",fe_autSolResInv=CURRENT_TIMESTAMP,  \n";
                strSql+="                          tx_comIngAutSolResInv="+objUti.codificar(objParSis.getNombreComputadoraConDirIP(), 0)+" \n";
                strSql+=" WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_cot="+intCodCot+"; ";
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
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
     * @param conExt
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodCot
     * @param indice
     * @return 
     */
    private boolean cambiarEstadoCotizacionAutorizada(java.sql.Connection conExt,int intCodEmp,int intCodLoc,int intCodCot, int indice){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){ 
                stmLoc = conExt.createStatement();
                strSql=""; 
                strSql+=" UPDATE tbm_cabCotVen SET st_autSolResInv='A',co_usrIngAutSolResInv="+objParSis.getCodigoUsuario()+", \n";
                strSql+=" st_autEnvPed=st_solEnvPed,fe_ingAutSolResInv=CURRENT_TIMESTAMP, ";
                strSql+=" tx_comIngAutSolResInv="+objUti.codificar(objParSis.getNombreComputadoraConDirIP(), 0)+",  ";
                strSql+=" fe_autSolResInv="+ objUti.codificar(objUti.formatearFecha(objTblMod.getValueAt(indice,INT_TBL_DAT_FEC_SOL_FAC_AUT).toString(), "dd/MM/yyyy", "yyyy/MM/dd"),0)+", \n";
                strSql+=" tx_obsAutSolResInv="+objUti.codificar(objTblMod.getValueAt(indice,INT_TBL_DAT_OBS_AUT_RES),0);
                strSql+=" WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_cot="+intCodCot+"; ";
                System.out.println("cambiarEstadoCotizacionAutorizada " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
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
     *  solo lo que esta en la bodega predeterminada lo demas se pondra en reserva con la confirmacion del ingreso
     * 
     * @param conExt
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodCot
     * @return 
     */
    private boolean moverInvBodReservado(java.sql.Connection conExt,int intCodEmp,int intCodLoc,int intCodCot){
        boolean blnRes=false, blnIns=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        double dblCanDis=0.00, dblCanSolRes=0.00;
        String strIns="";
        try{
             if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp,a1.co_loc,a1.co_cot,a1.co_reg,a1.nd_canAut, a2.co_emp as co_empInvBod,a2.co_bod as co_bodInvBod,a2.co_itm,a2.nd_stkAct,  \n";
                strSql+="        CASE WHEN a2.nd_canDis IS NULL THEN 0 ELSE a2.nd_canDis END as nd_canDis  \n";
                strSql+=" FROM  tbm_pedOtrBodCotVen as a1  \n";
                strSql+=" INNER JOIN tbm_invBod as a2 ON (a1.co_empRel=a2.co_emp AND a1.co_bodRel=a2.co_bod AND a1.co_itmRel=a2.co_itm) \n";
                strSql+=" WHERE a1.co_emp="+intCodEmp+"  AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" \n";
                System.out.println("moverInvBodReservado: \n" + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    dblCanDis = rstLoc.getDouble("nd_canDis");
                    dblCanSolRes = rstLoc.getDouble("nd_canAut");
                    if(dblCanSolRes<=dblCanDis){
                        strSql="";
                        strSql+=" UPDATE tbm_invBod SET nd_canDis=(nd_canDis-"+dblCanSolRes+"), nd_canRes=((CASE WHEN nd_canRes IS NULL THEN 0 ELSE nd_canRes END) + " + dblCanSolRes+") ";
                        strSql+=" WHERE co_emp="+rstLoc.getInt("co_empInvBod")+" AND co_bod="+rstLoc.getInt("co_bodInvBod");
                        strSql+=" AND co_itm="+rstLoc.getInt("co_itm")+";\n";
                        strIns+=strSql;
                    }else{
                        blnIns=false;
                        System.out.println("NO DISPONIBLE " + rstLoc.getInt("co_itm"));
                        //return false; CONTENEDOR PARA MOSTRAR ITEMS DE PROBLEMAS
                    }
                }
                rstLoc.close();
                rstLoc=null;
                
                if(blnIns){
                    System.out.println("moverInvBodReservado: " + strIns);
                    stmLoc.executeUpdate(strIns);
                    blnRes=true;
                }
                else{
                    blnRes=false;
                }
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
       return blnRes;
    }
    
    
    /**
     * 
     * @param conExt
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodCot
     * @return 
     */
    private boolean moverItemsCotizacionReservaEmpresa(java.sql.Connection conExt,int intCodEmp,int intCodLoc,int intCodCot){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" UPDATE tbm_detCotVen SET nd_canAutRes=nd_can, nd_canPenFac=nd_can  \n";
                strSql+=" WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_cot="+intCodCot+"; \n";
                strSql+=" UPDATE tbm_pedOtrBodCotVen SET nd_canAut=nd_can, st_autEnvPed=st_solEnvPed \n";
                strSql+=" WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_cot="+intCodCot+"; \n";
                System.out.println("moverItemsCotizacion... \n" + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
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
     * 
     * @param conExt
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodCot
     * @return 
     */
    private boolean moverItemsCotizacion(java.sql.Connection conExt,int intCodEmp,int intCodLoc,int intCodCot){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                //strSql+=" UPDATE tbm_detCotVen SET nd_canAutRes=nd_canSolRes WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_cot="+intCodCot+";";
                strSql+=" UPDATE tbm_pedOtrBodCotVen SET nd_canAut=nd_can, st_autEnvPed=st_solEnvPed  WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_cot="+intCodCot+";";
                System.out.println("moverItemsCotizacion... \n" + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }
        catch (java.sql.SQLException e) { 
            objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception  Evt){ 
            objUti.mostrarMsgErr_F1(this, Evt);
        }  
        return blnRes;
    }
    
    
     /* CONSTANTES PARA CONTENEDOR A ENVIAR JoséMario 21/Sep/2015  */
    private final int INT_ARL_COD_EMP=0;
    private final int INT_ARL_COD_LOC=1;
    private final int INT_ARL_COD_COT=2;
    private final int INT_ARL_COD_ITM=3;
    private final int INT_ARL_CAN_COM=4;
    private final int INT_ARL_COD_EMP_EGR=5;
    private final int INT_ARL_COD_BOD_EMP_EGR=6;
    private final int INT_ARL_COD_ITM_EGR=7;
    private final int INT_ARL_CAN_UTI=8;
    private final int INT_ARL_COD_BOD_EGR_GRP=9;
   
   /**
    * 
    * 
    * @param conExt
    * @param intCodEmp
    * @param intCodLoc
    * @param intCodCot
    * @return 
    */
   
   /* Jose mario crea una columna mas en el arraylist, en esa columna colocar la cantidad que has ocupado con eso sabras si un ingreso ya fue utilizado */
    private boolean modificarDatosDetallesCotizacionBodegasReserva(java.sql.Connection conExt,int intCodEmp, int intCodLoc, int intCodCot, int intRow){
        boolean blnRes=true;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        double dblCanPen=0.00;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT co_emp, co_loc, co_cot, co_reg, co_itm, nd_can FROM tbm_detCotVen  ";
                strSql+=" WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_cot="+intCodCot+" AND (tx_codAlt like '%I' OR tx_codAlt like '%S') ";
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    for(int i=0;i<arlDatRes.size();i++){    
                        if(rstLoc.getInt("co_itm")==objUti.getIntValueAt(arlDatRes, i, INT_ARL_COD_ITM)){
                            if(rstLoc.getDouble("nd_can")>=+objUti.getDoubleValueAt(arlDatRes, i, INT_ARL_CAN_COM)){
                                // SI es menor o IGUAL es un solo item
                                if(objUti.getDoubleValueAt(arlDatRes, i, INT_ARL_CAN_COM)>objUti.getDoubleValueAt(arlDatRes, i, INT_ARL_CAN_UTI)){ 
                                    // Si todavia no h utilizado todo lo de ese item
                                    if(modificarPedidosSolicitadosReservas(conExt, intCodEmp,intCodLoc,intCodCot,rstLoc.getInt("co_reg"),
                                            objUti.getIntValueAt(arlDatRes, i,INT_ARL_COD_EMP_EGR),
                                            objUti.getIntValueAt(arlDatRes, i, INT_ARL_COD_BOD_EMP_EGR),
                                            objUti.getIntValueAt(arlDatRes, i, INT_ARL_COD_ITM_EGR),
                                            objUti.getDoubleValueAt(arlDatRes, i, INT_ARL_CAN_COM),
                                            objUti.getIntValueAt(arlDatRes, i, INT_ARL_COD_BOD_EGR_GRP), 
                                            intRow)){
                                        objUti.setDoubleValueAt(arlDatRes, i, INT_ARL_CAN_UTI, objUti.getDoubleValueAt(arlDatRes, i, INT_ARL_CAN_COM));
                                        // Asigno un valor de lo que se esta utilizando 
                                    }else{blnRes=false;}   
                                }
                            }
                            else{
                                // El Item puede estar dos veces
                                if(objUti.getDoubleValueAt(arlDatRes, i, INT_ARL_CAN_COM)>objUti.getDoubleValueAt(arlDatRes, i, INT_ARL_CAN_UTI)){ 
                                    // Si todavia no ha utilizado todo lo de ese item
                                    dblCanPen = rstLoc.getDouble("nd_can");
                                    if(modificarPedidosSolicitadosReservas(conExt, intCodEmp,intCodLoc,intCodCot,rstLoc.getInt("co_reg"),
                                            objUti.getIntValueAt(arlDatRes, i,INT_ARL_COD_EMP_EGR),
                                            objUti.getIntValueAt(arlDatRes, i, INT_ARL_COD_BOD_EMP_EGR),
                                            objUti.getIntValueAt(arlDatRes, i, INT_ARL_COD_ITM_EGR) ,
                                            dblCanPen,
                                            objUti.getIntValueAt(arlDatRes, i, INT_ARL_COD_BOD_EGR_GRP), 
                                            intRow)){
                                        // Asigno un valor de lo que se esta utilizando 
                                        objUti.setDoubleValueAt(arlDatRes, i, INT_ARL_CAN_UTI, (dblCanPen+objUti.getDoubleValueAt(arlDatRes, i, INT_ARL_CAN_UTI)));
                                    }else{blnRes=false;}   
                                }
                            }
                        }
                    }
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
    
    
     private boolean terminalesStockLocal(java.sql.Connection conExt,int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc,stmLoc01;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement(); stmLoc01 = conExt.createStatement();
                int intCodBodPre = bodegaPredeterminadaPorEmpresa(CodEmp,CodLoc);
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot, a2.co_reg, a2.co_itm, a2.nd_can,  ";
                strSql+="    a2.tx_codALt, trim(SUBSTR (UPPER(a2.tx_codalt), length(a2.tx_codalt) ,1)) as terminal,\n";
                strSql+="         CASE WHEN ( (trim(SUBSTR (UPPER(a3.tx_codalt), length(a3.tx_codalt) ,1))  IN (  \n";
                strSql+="               SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+CodEmp+"  AND co_loc="+CodLoc+" \n";
                strSql+="                AND co_tipdoc=228  AND co_usr=(SELECT co_usrIng from tbm_cabCotVen WHERE co_emp="+CodEmp+"  AND co_loc="+CodLoc+"  AND co_cot="+CodCot+" )  AND st_reg='A'   \n";
                strSql+="        ))) THEN 'S' ELSE 'N' END  as isterL  \n";
                strSql+=" FROM tbm_cabCotVen as a1 \n";
                strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_Loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" AND a2.tx_codAlt like '%S' \n";
                System.out.println("terminalesStockLocal " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    strSql="";
                    strSql+=" INSERT INTO tbm_pedOtrBodCotVen (co_emp,co_loc,co_cot,co_reg,co_empRel,co_bodRel,co_itmRel,nd_can,";
                    strSql+="                                  st_necAut,st_pedOtrBod,st_solEnvPed,st_autEnvPed,nd_bodOrgCanEnvBod) ";
                    strSql+=" VALUES ( "+CodEmp+","+CodLoc+","+CodCot+","+rstLoc.getString("co_reg") +",";
                    strSql+=" "+CodEmp+",";
                    strSql+=" "+intCodBodPre+",";
                    strSql+=" "+rstLoc.getString("co_itm")+",";
                    strSql+=" "+rstLoc.getString("nd_can")+",'S',";
                    strSql+=" NULL,";  // st_pedOtrBod
                    strSql+=" null,"; // st_solEnvPed
                    strSql+=" null, \n"; // st_autEnvPed
                    strSql+=" NULL";
                    strSql+=" ); ";
                    System.out.println("terminalesStockLocal " + strSql);
                    stmLoc01.executeUpdate(strSql);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                stmLoc01.close();
                stmLoc01=null;
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
     * 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @param CodReg
     * @param CodEmpEgr
     * @param CodBodEgr
     * @param CodItmEgr
     * @param CanItm
     * @param CodBodGrp
     * @param Row
     * @return 
     */
    
    
    private boolean modificarPedidosSolicitadosReservas(java.sql.Connection conExt, int CodEmp, int CodLoc ,int CodCot ,int CodReg, 
                                                        int CodEmpEgr, int CodBodEgr,int CodItmEgr ,double CanItm, int CodBodGrp, int Row ){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        int intCodBodPreGrp=-1;
        try{
            if(conExt!=null){
                intCodBodPreGrp = bodegaPredeterminadaGrupo(CodEmp,CodLoc);
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT * FROM tbm_pedOtrBodCotVen ";
                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+" AND co_reg="+CodReg+" AND";
                strSql+="       co_empRel="+CodEmpEgr+" AND co_bodRel="+CodBodEgr+" AND co_itmRel="+CodItmEgr;
                strSql+=" ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    strSql="";
                    strSql+=" UPDATE tbm_pedOtrBodCotVen SET nd_can ="+CanItm+", ";
                    if(intCodBodPreGrp!=CodBodGrp){
                        strSql+=" st_pedOtrBod='N',";
                    }
                    else{
                        strSql+=" st_pedOtrBod=null,";
                    }
                    if(objTblMod.isChecked(Row, INT_TBL_DAT_CHK_SOL_ENV_PED)){
                        strSql+=" st_solEnvPed='S',";
                    }
                    else{
                        strSql+=" st_solEnvPed=null,";
                    }
                    strSql+=" st_autEnvPed=null,";
                    if(intCodBodPreGrp!=CodBodGrp){
                        strSql+=" nd_bodOrgCanEnvBod=" +CanItm;
                    }
                    else{
                        strSql+=" nd_bodOrgCanEnvBod=null";
                    }
                    
                    strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+" AND co_reg="+CodReg+" AND";
                    strSql+="       co_empRel="+CodEmpEgr+" AND co_bodRel="+CodBodEgr+" AND co_itmRel="+CodItmEgr+" ;";
                }
                else{
                    strSql="";
                    strSql+=" INSERT INTO tbm_pedOtrBodCotVen (co_emp,co_loc,co_cot,co_reg,co_empRel,co_bodRel,co_itmRel,nd_can,";
                    strSql+="                                  st_necAut,st_pedOtrBod,st_solEnvPed,st_autEnvPed,nd_bodOrgCanEnvBod) ";
                    strSql+=" VALUES ( "+CodEmp+","+CodLoc+","+CodCot+","+CodReg+",";
                    strSql+=" "+CodEmpEgr+",";
                    strSql+=" "+CodBodEgr+",";
                    strSql+=" "+CodItmEgr+",";
                    strSql+=" "+CanItm+",'S',";
                    if(intCodBodPreGrp!=CodBodGrp){
                        strSql+=" 'S',";  // st_pedOtrBod
                    }
                    else{
                        strSql+=" NULL,";  // st_pedOtrBod
                    }
                    if(objTblMod.isChecked(Row, INT_TBL_DAT_CHK_SOL_ENV_PED)){  // st_solEnvPed
                        strSql+=" 'S',"; // st_solEnvPed 
                    }
                    else{
                        strSql+=" null,"; // st_solEnvPed
                    }
                    strSql+=" null, \n"; // st_autEnvPed
                    
                    if(intCodBodPreGrp!=CodBodGrp){
                        strSql+=" "+CanItm+" ";
                    }
                    else{
                        strSql+=" NULL";
                    }
                    strSql+=" ); ";
                }
                System.out.println("modificarPedidosSolicitadosReservas... \n" + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
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
     * 
     * 
     * @param conExt
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodCot
     * @return 
     */
    
    
    private boolean modificarEstadoCotizacion(java.sql.Connection conExt,int intCodEmp, int intCodLoc, int intCodCot, int indice){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" UPDATE tbm_cabCotVen SET co_forRet=44,";
                strSql+="   st_solResInv='P',st_autSolResInv='P', \n";  // Pendiente  de confirmar y estado de autorizacion pendiente
                strSql+="   co_tipSolResInv="+objUti.codificar(objTblMod.getValueAt(indice,INT_TBL_DAT_COD_TIP_SOL),2)+", ";
                strSql+="   fe_solResInv="+objUti.codificar(objUti.formatearFecha(objTblMod.getValueAt(indice,INT_TBL_DAT_FEC_SOL).toString(), "dd/MM/yyyy", "yyyy/MM/dd"),0)+", ";
                strSql+="   tx_obsSolResInv="+objUti.codificar(objTblMod.getValueAt(indice,INT_TBL_DAT_OBS_SOL_RES),0)+", ";
                if(objTblMod.isChecked(indice, INT_TBL_DAT_CHK_PED_OTR_BOD)){
                    strSql+="   st_pedOtrBod='S', ";
                }else{
                    strSql+="   st_pedOtrBod=null, ";
                }
                if(objTblMod.isChecked(indice, INT_TBL_DAT_CHK_SOL_ENV_PED)){
                    strSql+="   st_solEnvPed='S', ";
                }else{
                    strSql+="   st_solEnvPed=null, ";
                }
                strSql+="   co_usrIngSolResInv="+objParSis.getCodigoUsuario()+", ";
                strSql+="   tx_comIngSolResInv="+objUti.codificar(objParSis.getNombreComputadoraConDirIP(), 0)+", ";
                strSql+="   fe_ingSolResInv=CURRENT_TIMESTAMP, ";
                strSql+="   tx_obs2= tx_obs2 || ' Sis.Res.:"+strVer+"'     \n";
                strSql+=" WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_cot="+intCodCot+";";
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
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
     * Metodo para llamar a las solicitudes de autorizacion de Cotizaciones de Venta en el caso de Que sea necesario, 
     * cuando se emita una factura
     * 
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodCot
     * @return 
     */
    
    
    private boolean solicitarAutorizacionCxC(int intCodEmp, int intCodLoc, int intCodCot){
        boolean blnRes=false;
        try{
            java.sql.Connection conLoc;
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                 int EstAut = 1;
                 EstAut = objAutPrg.checkCtlsCot("tbm_cabautcotven", "tbm_detautcotven",intCodEmp,intCodLoc, intCodCot, conLoc,true);
                switch (EstAut) {
                    case 1: // Todo Correcto
                        //System.out.println("No necesita autorizaciones....");
                        blnRes=true;
                    break;
                    case 2:
                        //System.out.println("Autorizacion....");
                        conLoc.setAutoCommit(false);
                        
                        if (objAutPrg.insertarCabDetAut(conLoc, intCodEmp, intCodLoc, intCodCot, 3)) {
                            if(colocarCotizacionComoNecesitaAutorizacion(conLoc, intCodEmp, intCodLoc, intCodCot)){
                                conLoc.commit();
                                blnRes=true;
                            }else{conLoc.rollback();}
                        } else { conLoc.rollback();}
                        
                    break;    
                };
            }
            
        }
        catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
       }  
       return blnRes;
    }
    
    /**
     * 
     * @param conExt
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodCot
     * @return 
     */
    private boolean colocarCotizacionComoNecesitaAutorizacion(java.sql.Connection conExt, int intCodEmp, int intCodLoc, int intCodCot){
        java.sql.Statement stmLoc;
        boolean blnRes=false;
        try {
            if (conExt != null){
                stmLoc = conExt.createStatement();
                String strCadena="";
                strCadena+=" UPDATE tbm_cabCotVen SET st_reg='P' WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_cot="+intCodCot+"; ";
                //System.out.println("ACTUALIZAR " + strCadena);
                stmLoc.executeUpdate(strCadena);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }
        catch (java.sql.SQLException e) {   
            blnRes = false;  
            objUti.mostrarMsgErr_F1(this, e);   
        } 
        catch (Exception e) {  
            blnRes = false;   
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
    private boolean mostrarVenConCli(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.setVisible(true);
                    if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarCli.setText(strDesLarCli);
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConVen(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoVen.setCampoBusqueda(2);
                    vcoVen.setVisible(true);
                    if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoVen.buscar("a1.co_usr", txtCodVen.getText()))
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    else
                    {
                        vcoVen.setCampoBusqueda(0);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.setVisible(true);
                        if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtNomVen.setText(vcoVen.getValueAt(3));
                        }
                        else
                        {
                            txtCodVen.setText(strCodVen);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoVen.buscar("a1.tx_nom", txtNomVen.getText()))
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    else
                    {
                        vcoVen.setCampoBusqueda(2);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.setVisible(true);
                        if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtNomVen.setText(vcoVen.getValueAt(3));
                        }
                        else
                        {
                            txtNomVen.setText(strNomVen);
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
    
    
    private String strCodTipSol, strNomTipSol;
    
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
    private boolean mostrarVenConTipSol(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTipSol.setCampoBusqueda(2);
                    vcoTipSol.setVisible(true);
                    if (vcoTipSol.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                        txtCodTipSol.setText(vcoTipSol.getValueAt(1));
                        txtNomTipSol.setText(vcoTipSol.getValueAt(2));
                    }
                break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoTipSol.buscar("a1.co_tipSol", txtCodTipSol.getText())){
                        txtCodTipSol.setText(vcoTipSol.getValueAt(1));
                        txtNomTipSol.setText(vcoTipSol.getValueAt(2));
                    }
                    else{
                        vcoTipSol.setCampoBusqueda(0);
                        vcoTipSol.setCriterio1(11);
                        vcoTipSol.cargarDatos();
                        vcoTipSol.setVisible(true);
                        if (vcoTipSol.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                            txtCodTipSol.setText(vcoTipSol.getValueAt(1));
                            txtNomTipSol.setText(vcoTipSol.getValueAt(2));
                        }
                        else{
                            txtCodTipSol.setText(strCodTipSol);
                        }
                    }
                break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoTipSol.buscar("a1.tx_nom", txtNomTipSol.getText())){
                        txtCodTipSol.setText(vcoTipSol.getValueAt(1));
                        txtNomTipSol.setText(vcoTipSol.getValueAt(2));
                    }
                    else{
                        vcoTipSol.setCampoBusqueda(2);
                        vcoTipSol.setCriterio1(11);
                        vcoTipSol.cargarDatos();
                        vcoTipSol.setVisible(true);
                        if (vcoTipSol.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                            txtCodTipSol.setText(vcoTipSol.getValueAt(1));
                            txtNomTipSol.setText(vcoTipSol.getValueAt(2));
                        }
                        else{
                            txtNomTipSol.setText(strNomTipSol);
                        }
                    }
                break;
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "solicitudes de reserva de inventario".
     */
    private boolean configurarVenConTipSol()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipSol");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_tipResInv");
            arlCam.add("a1.st_facPriDiaLabSigMes");
            arlCam.add("a1.tx_momDes");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            arlAli.add("tx_tipResInv");
            arlAli.add("st_facPriDiaLabSigMes");
            arlAli.add("tx_momDes");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("40");
            arlAncCol.add("600");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_tipSolResInv as co_tipSol,a1.tx_desLar as tx_nom ,a1.tx_tipResInv,a1.tx_momDes ,   ";
            strSQL+="        CASE WHEN a1.st_facPriDiaLabSigMes IS NULL THEN 'N' ELSE a1.st_facPriDiaLabSigMes END as st_facPriDiaLabSigMes";
            strSQL+=" FROM tbm_tipSolResInv AS a1";
            strSQL+=" WHERE  a1.st_reg = 'A' ";
            strSQL+=" ORDER BY a1.tx_desLar";
            //System.out.println("configurarVenConTipSol: " + strSQL);
            
            //Ocultar columnas.
            int intColOcu[]=new int[3];
            intColOcu[0]=3;  // tx_tipResInv
            intColOcu[1]=4;  // st_facPriDiaLabSigMes
            intColOcu[2]=5;  // tx_momDes
            
            vcoTipSol=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Tipos de solicitudes de reserva de inventario ", strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoTipSol.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    
    /**
     * Esta función configura el JTable "tblLoc".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblLoc()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LOC_LIN,"");
            vecCab.add(INT_TBL_LOC_CHK,"");
            vecCab.add(INT_TBL_LOC_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_LOC_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_LOC_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_LOC_NOM_LOC,"Local");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModLoc=new ZafTblMod();
            objTblModLoc.setHeader(vecCab);
            tblLoc.setModel(objTblModLoc);
            //Configurar JTable: Establecer tipo de selección.
            tblLoc.setRowSelectionAllowed(true);
            tblLoc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblLoc);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblLoc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblLoc.getColumnModel();
            tcmAux.getColumn(INT_TBL_LOC_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_LOC_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_LOC_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOM_EMP).setPreferredWidth(221);
            tcmAux.getColumn(INT_TBL_LOC_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOM_LOC).setPreferredWidth(221);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_LOC_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblLoc.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblModLoc.addSystemHiddenColumn(INT_TBL_LOC_COD_EMP, tblLoc);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblLoc.getTableHeader().addMouseMotionListener(new ZafMouMotAdaLoc());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblLoc.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblLocMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_LOC_CHK);
            objTblModLoc.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblLoc);
            tcmAux.getColumn(INT_TBL_LOC_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_LOC_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_LOC_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_LOC_COD_LOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblLoc);
            tcmAux.getColumn(INT_TBL_LOC_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiChk.isChecked())
                    {
//                        objTblMod.setValueAt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_PEN), tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                    }
                    else
                    {
//                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                    }
                }
            });

            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblLoc.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModLoc.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
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
    private void tblLocMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModLoc.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblLoc.columnAtPoint(evt.getPoint())==INT_TBL_LOC_CHK)
            {
                if (blnMarTodChkTblEmp){
                    for (i=0; i<intNumFil; i++){
                        objTblModLoc.setChecked(true, i, INT_TBL_LOC_CHK);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(false, i, INT_TBL_LOC_CHK);
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
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaLoc extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblLoc.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_LOC_LIN:
                    strMsg="";
                    break;
                case INT_TBL_LOC_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_LOC_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_LOC_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_LOC_NOM_LOC:
                    strMsg="Nombre del local";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblLoc.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta función permite consultar los locales de acuerdo al siguiente criterio:
     * <UL>
     * <LI>Si se ingresa a la empresa "Grupo" se muestran todos los locales.
     * <LI>Si se ingresa a cualquier otra empresa se muestran sólo los locales pertenecientes a la empresa seleccionada.
     * </UL>
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarLoc()
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
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los locales.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp)";
                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                    {
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        if (!chkMosLocIna.isSelected())
                            strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    }
                    if (!chkMosLocIna.isSelected())
                        strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_loc";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL+=" INNER JOIN tbr_locUsr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a3.co_usr=" + objParSis.getCodigoUsuario() + ")";
                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                    {
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        if (!chkMosLocIna.isSelected())
                            strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    }
                    if (!chkMosLocIna.isSelected())
                        strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_loc";
                    rst=stm.executeQuery(strSQL);
                }
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LOC_LIN,"");
                    vecReg.add(INT_TBL_LOC_CHK,Boolean.TRUE);
                    vecReg.add(INT_TBL_LOC_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_LOC_NOM_EMP,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_LOC_COD_LOC,rst.getString("co_loc"));
                    vecReg.add(INT_TBL_LOC_NOM_LOC,rst.getString("a2_tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModLoc.setData(vecDat);
                tblLoc.setModel(objTblModLoc);
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
     * mostrar los "Clientes".
     */
    private boolean configurarVenConCli()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("394");
            //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
            if (objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg='A' AND a1.st_cli='S'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.st_reg='A' AND a1.st_cli='S'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCli.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
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
     * mostrar los "Vendedores".
     */
    private boolean configurarVenConVen()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_usr");
            arlCam.add("a1.tx_usr");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Usuario");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("120");
            arlAncCol.add("374");
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
                strSQL+=" FROM tbm_usr AS a1";
                strSQL+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)";
                strSQL+=" WHERE a1.st_reg='A' AND a2.st_ven='S'";
                strSQL+=" GROUP BY a1.co_usr, a1.tx_usr, a1.tx_nom";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            else
            {
                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
                    strSQL+=" FROM tbm_usr AS a1";
                    strSQL+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)";
                    strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.st_reg='A' AND a2.st_ven='S' ";
                    strSQL+=" ORDER BY a1.tx_nom";
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
                    strSQL+=" FROM tbm_usr AS a1";
                    strSQL+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)";
                    strSQL+=" INNER JOIN tbr_locusr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_usr=a3.co_usr)";
                    strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa()+" AND a3.co_loc="+objParSis.getCodigoUsuario();
                    strSQL+=" AND a1.st_reg='A' AND a2.st_ven='S' AND a3.st_reg IN ('A','P')";
                    strSQL+=" ORDER BY a1.tx_nom";
                }
                //Armar la sentencia SQL.
               
            }
            vcoVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de vendedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoVen.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    String strMsjCorEle="";
    String strDesAut="sistemas6@tuvalsa.com",strDesVen="sistemas6@tuvalsa.com",strDesSis="sistemas6@tuvalsa.com";
    
     private void enviaCorreosElectronicos(int intCodTipCor){
            try{
                objCorEle.enviarCorreoMasivo(strDesSis,"RESERVAS TUVAL ",strMsjCorEle );  
            }
            catch (Exception Evt) {
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(this, Evt);
            } 
            
        }
    
     /**
      * Obtiene la siguiente fecha laboral en formato dd/MM/yyyy
      * 
      * 
      * @param intCodEmp
      * @param intCodLoc
      * @return 
      */
    
    private String getFechaLab(int intCodEmp, int intCodLoc){
        String strFecha=""; 
        java.sql.Connection conLoc;
         try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                java.sql.Statement stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a1.*  ";
                strSql+=" FROM tbm_calCiu as a1  ";
                strSql+=" INNER JOIN tbm_loc as a2 ON (a1.co_ciu=a2.co_ciu) ";
                strSql+=" WHERE a2.co_emp="+intCodEmp+" and co_loc="+intCodLoc+" AND CASE WHEN  EXTRACT(MONTH from CURRENT_DATE)=12 THEN ";
                strSql+="       (EXTRACT(YEAR from CURRENT_DATE)+1)=EXTRACT(YEAR from a1.fe_Dia) AND /*MES ENERO JM*/1=EXTRACT(MONTH from a1.fe_Dia)  ELSE ";
                strSql+="       EXTRACT(YEAR from CURRENT_DATE)=EXTRACT(YEAR from a1.fe_Dia) AND (EXTRACT(MONTH from CURRENT_DATE)+1)=EXTRACT(MONTH from a1.fe_Dia) END AND a1.tx_tipDia='L' ";
                strSql+=" ORDER BY a1.fe_dia ASC	";
                strSql+=" LIMIT 1";
            	java.sql.ResultSet rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    //System.out.println("fecha " + rstLoc.getString("fe_dia"));
                    strFecha=objUti.formatearFecha(rstLoc.getString("fe_dia"), "yyyy-MM-dd","dd/MM/yyyy" );
                    //System.out.println("strFecha " + strFecha);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strFecha;
    } 

    private int intCodTipDocOC=2;
    Librerias.ZafUtil.UltDocPrint           objUltDocPrint;  // Para trabajar con la informacion de tipo de documento    
    
    /**
     * GENERA LA ORDEN DE COMPRA
     * @param conExt
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodCot
     * @return 
     */
    
    private boolean generaOC(java.sql.Connection conExt,int intCodEmp,int intCodLoc,int intCodCot){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        int intCodDoc,intCodBod=-1,intCodPrv=-1,intCodEmpTemp=-1,intCodLocTemp=-1;
        String strConfInv="F",strCorEle="";
        try{          
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot, a2.co_bod, a2.co_prv, a1.co_usrIng,a6.tx_nom as tx_nomCom,a3.tx_nom, a3.tx_ide, \n";
                strSql+="        a3.tx_dir, a3.tx_tel,a3.tx_nom as tx_nomCli ,a4.tx_desLar as tx_nomCiu, a2.co_forPagCom,a5.tx_des as tx_nomForPagCom, \n";
                strSql+="        a6.tx_usr,a6.tx_nom as tx_nomUsr, a6.tx_corEle, a2.tx_obs1Com, a2.co_forRetCom \n";
                strSql+=" FROM tbm_cabCotVen as a1 \n";
                strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+=" INNER JOIN tbm_cli as a3 ON (a2.co_emp=a3.co_emp AND a2.co_prv=a3.co_cli) \n";
                strSql+=" LEFT OUTER JOIN tbm_ciu as a4 ON (a3.co_ciu=a4.co_ciu) \n";
                strSql+=" INNER JOIN tbm_cabForPag AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_forPagCom=a5.co_forPag)\n";
                strSql+=" INNER JOIN tbm_usr AS a6 ON (a1.co_usrIng=a6.co_usr) ";
                strSql+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" AND a2.tx_codAlt like '%L' \n";
                strSql+=" GROUP by a2.co_prv,a1.co_emp, a1.co_loc, a1.co_cot, a2.co_bod  , a1.co_usrIng  ,a3.tx_nom, a3.tx_ide, a3.tx_dir,   \n";
                strSql+="          a3.tx_tel ,a4.tx_desLar , a2.co_forPagCom, a5.tx_des, a6.tx_usr, a6.tx_nom ,   \n";
                strSql+="          a6.tx_corEle, a2.tx_obs1Com, a2.co_forRetCom \n";
                System.out.println("Datos para la OC: generaOC: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    intCodBod=rstLoc.getInt("co_bod");
                    intCodPrv = rstLoc.getInt("co_prv");
                    strCorEle = rstLoc.getString("tx_corEle");
                    intCodDoc=getCodigoDocumento(conExt,intCodEmp,intCodLoc, intCodTipDocOC);
                    txtsql.setText("");
                    if(FormaRetencion(conExt,intCodEmp )){
                        if(insertarCabOC(conExt,rstLoc,intCodDoc, strConfInv )){    
                            if(insertarDetOC(intCodDoc,rstLoc,conExt )){    
                                intCodEmpTemp = objParSis.getCodigoEmpresa(); intCodLocTemp=objParSis.getCodigoLocal();
                                objParSis.setCodigoEmpresa(intCodEmp); objParSis.setCodigoLocal(intCodLoc);
                                Ventas.ZafVen01.ZafVen01_OC obj1 = new Ventas.ZafVen01.ZafVen01_OC(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, intCodCot, intCodPrv, 45, rstLoc.getString("co_usrIng"), rstLoc.getString("tx_nomCom"), conExt, intCodDoc,false);
                                if(obj1.cargarOCReservas(conExt, intCodEmp, intCodLoc, intCodCot, intCodTipDocOC, intCodDoc)){
                                    if(obj1.insertarRegPagos(conExt, intCodDoc)){
                                        String strMensaje=("<HTML>Imprima la orden de compra numero <FONT COLOR=\"blue\"> " + intNumDocOC + " </FONT>.</HTML>");
                                        objCorEle.enviarCorreoMasivo(rstLoc.getString("tx_corEle"),strTitCorEle,strMensaje);
                                    }else{MensajeInf("Error al Guardar: Insertando pagos");blnRes=false;}
                                }else{MensajeInf("Error al Guardar: Al cargar la OC ");blnRes=false;}

                                objParSis.setCodigoEmpresa(intCodEmpTemp);objParSis.setCodigoLocal(intCodLocTemp);
                                obj1 = null;
                            }else{blnRes=false;}
                        }else{MensajeInf("Error al Guardar: Insertando OC");blnRes=false;}
                    }else{MensajeInf("Error al Guardar: Forma Retencion pagos OC");blnRes=false;}
                }
            }else{blnRes=false;}
            
            if(blnRes==false){
                objCorEle.enviarCorreoMasivo(strCorEleErr, strTitCorEle, "ERROR PAGOS OC co_emp="+intCodEmp+"AND co_loc="+intCodLoc+" AND co_cot="+intCodCot );
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception Evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
        return blnRes;
    }    
    
    
    /**
        * Esta Funcion permite obtener el codigo maximo sumado +1 de la tabla tbm_cabmovinv filtrado por empresa local y tipoDocumento.
        * @param  con : recive la coneccion de la base
        * @param  intCodTipDoc : recive el tipo de documento 
        *
        * @return   : retorna el codigo de documento
        */ 
         public int getCodigoDocumento(java.sql.Connection con,int CodEmp, int CodLoc, int intCodTipDoc){ 
            int intDoc = -1;
            java.sql.Statement stm; 
            java.sql.ResultSet rst;
            try{
                 if (con!=null){
                        stm =con.createStatement();
                        String strSQL= "SELECT case when Max(co_doc) is null then  1 else Max(co_doc)+1  end  as codoc  FROM tbm_cabMovInv WHERE " +
                          " co_emp ="+CodEmp+" AND co_loc ="+CodLoc+" AND co_tipdoc ="+intCodTipDoc;
                        
                        rst = stm.executeQuery(strSQL);
                        if(rst.next()){
                            intDoc = rst.getInt("codoc");
                        }
                        rst.close();
                        stm.close();
                    }
                }catch(java.sql.SQLException e){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
                 catch(Exception e){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  }
            return intDoc;
        }  
          
         
    
    
    private String GLO_strArreItm="";
    private BigDecimal bgdCanItm=BigDecimal.ZERO;
    private BigDecimal bgdPreItm=BigDecimal.ZERO;
    private BigDecimal bgdValDesItm=BigDecimal.ZERO;
    private BigDecimal bgdPorDesItm=BigDecimal.ZERO;
    private BigDecimal bgdTotItm=BigDecimal.ZERO;
    private BigDecimal bgdIntSig=BigDecimal.ZERO;
    private BigDecimal bgdValPorIva=BigDecimal.ZERO;
    private BigDecimal bgdValDocTot=BigDecimal.ZERO;
    private BigDecimal bgdValDocSub=BigDecimal.ZERO;
    private BigDecimal bgdValDocIva=BigDecimal.ZERO;
    private BigDecimal bgdIva=BigDecimal.ZERO;

    /**
     * 
     * @param intCodDoc
     * @param rstExt
     * @param conExt
     * @return 
     */
    private boolean insertarDetOC(int intCodDoc, java.sql.ResultSet rstExt, java.sql.Connection conExt){
        boolean blnRes=false, blnIsInmaconsaOD=false;
        String strEstFisBod="";
        String str_MerIEFisBod="S",strIns="";
        int intControl=0, i=0;
        boolean blnIsSer=false;
        java.sql.Statement stmLoc, stmLoc01,stmIns;
        java.sql.ResultSet rstLoc, rstLoc01;
        BigDecimal bgdCanPen=BigDecimal.ZERO;
        try{
            if(conExt!=null){
                bgdCanItm=BigDecimal.ZERO; bgdPreItm=BigDecimal.ZERO;
                bgdValDesItm=BigDecimal.ZERO; bgdPorDesItm=BigDecimal.ZERO;
                bgdTotItm=BigDecimal.ZERO; bgdIntSig=BigDecimal.ZERO;
                bgdValPorIva=BigDecimal.ZERO; bgdValDocTot=BigDecimal.ZERO;
                bgdValDocSub=BigDecimal.ZERO; bgdValDocIva=BigDecimal.ZERO;
                bgdIva=BigDecimal.ZERO;
                stmLoc=conExt.createStatement();stmLoc01 = conExt.createStatement();stmIns = conExt.createStatement();
                GLO_strArreItm="";
                strSql="";
                strSql+=" SELECT a1.co_itm,a2.tx_codALt, a1.nd_can, a1.nd_porDesPreCom, a1.nd_preCom,a1.co_bod, tx_desCor as tx_nomUni, \n";
                strSql+="        a1.tx_nomItm,a2.st_ivaCom ";
                strSql+=" FROM tbm_detCotVen as a1 \n";
                strSql+=" INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSql+=" LEFT OUTER JOIN tbm_var as a3 ON (a2.co_uni=a3.co_reg) \n";
                strSql+=" WHERE a1.co_emp="+rstExt.getInt("co_emp") +" AND a1.co_loc="+rstExt.getInt("co_loc")+" AND a1.co_cot="+rstExt.getInt("co_cot")+" AND \n";
                strSql+="       a1.co_prv="+rstExt.getInt("co_prv")+" AND a1.tx_codAlt like '%L' AND a2.st_ser='N' \n";
                strSql+=" \n";
                System.out.println("insertarDetOC: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    
                    if(intControl!=0)    
                         GLO_strArreItm=GLO_strArreItm+",";

                    GLO_strArreItm=GLO_strArreItm+rstLoc.getString("co_itm");
                    intControl++;
                    str_MerIEFisBod="N";
                    String strCadena="";
                    strCadena+=" SELECT st_ser ";
                    strCadena+=" FROM tbm_inv ";
                    strCadena+=" WHERE co_emp="+objParSis.getCodigoEmpresa() + " AND co_itm="+rstLoc.getInt("co_itm");
                    rstLoc01=stmLoc01.executeQuery(strCadena);
                    if(rstLoc01.next()){
                        blnIsSer=rstLoc01.getString("st_ser").equals("S")?true:false;
                    }
                    rstLoc01.close();
                    rstLoc01=null;

                     if(rstLoc.getInt("co_bod") ==15){
                          String strTerminal=rstLoc.getString("tx_codALt").substring(rstLoc.getString("tx_codALt").length()-1);
                          int intBodGrp = intBodGrp(rstExt.getInt("co_emp"),rstLoc.getInt("co_bod") );
                          String strCfgCon = objUti.getCfgConfirma(this, objParSis,  objParSis.getCodigoEmpresaGrupo(), intBodGrp, strTerminal);
                          if(strCfgCon.equals("S") && blnIsSer==false){
                              str_MerIEFisBod="S";
                              bgdCanItm= rstLoc.getBigDecimal("nd_can");
                              bgdCanPen=rstLoc.getBigDecimal("nd_can");
                              blnIsInmaconsaOD=true;
                          }
                          else{
                              bgdCanPen=BigDecimal.ZERO;
                          }
                     }
                      
                     Boolean blnIva = new Boolean(rstLoc.getString("st_ivacom").equals("S")); 
                     bgdCanItm= rstLoc.getBigDecimal("nd_can");
                     bgdPreItm= rstLoc.getBigDecimal("nd_preCom"); 
                     bgdPorDesItm  = rstLoc.getBigDecimal("nd_porDesPreCom"); 
                     /////
                     bgdValPorIva=objParSis.getPorcentajeIvaVentas().divide(new BigDecimal("100"),objParSis.getDecimalesMostrar(),BigDecimal.ROUND_HALF_UP)  ;

                     //DESCUENTO
                     bgdValDesItm = objUti.redondearBigDecimal( bgdPorDesItm.multiply((bgdCanItm.multiply(bgdPreItm))) , objParSis.getDecimalesMostrar()); 
                     bgdValDesItm = bgdValDesItm.divide(new BigDecimal("100"),objParSis.getDecimalesMostrar(),BigDecimal.ROUND_HALF_UP);

                     ///TOTAL
                     bgdTotItm=objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)).subtract(bgdValDesItm), objParSis.getDecimalesMostrar());    

                     //TOTAL DOCUMENTO
                     bgdValDocSub=objUti.redondearBigDecimal((bgdValDocSub.add(bgdTotItm)), objParSis.getDecimalesMostrar())    ;//al final tendrá el valor del subtotal del documento
                     //TOTAL SIN IVA
                     bgdValDocTot=objUti.redondearBigDecimal(((bgdValDocTot.add(bgdValDocSub))), objParSis.getDecimalesMostrar());//al final tendrá el valor del total del documento

                     //dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;
                     //dblTotal  = objUti.redondear((dblCan * dblPre)- dblValDes,objParSis.getDecimalesMostrar()) ;8
                     if (blnIva.booleanValue()){    
                        bgdIva = objUti.redondearBigDecimal(bgdIva.add(bgdTotItm.multiply(bgdValPorIva) ), objParSis.getDecimalesMostrar());
//                        dblIva = objUti.redondear(dblIva+(((dblTotal * dblPorIva)==0)?0:dblTotal * (dblPorIva/100)),objZafParSis.getDecimalesMostrar()) ;
                     }
                     /************************************************************************/
                     
                   strSql=" INSERT INTO tbm_detMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, tx_unimed, co_itm, co_itmact, " +
                   " tx_codAlt,tx_codAlt2, tx_nomItm, co_bod, nd_can, nd_cosUni, nd_cosUniGrp , nd_preuni, nd_porDes, st_ivaCom  "+
                   ",nd_costot, nd_costotgrp, nd_tot , st_regrep, nd_cancon, st_meringegrfisbod, nd_preunivenlis, nd_pordesvenmax"
                 + ",nd_canIngBod,nd_canPen )" +
                   " VALUES("+rstExt.getInt("co_emp") +", "+rstExt.getInt("co_loc")+", "+intCodTipDocOC+"," +
                   intCodDoc+", "+(i+1)+", '"+rstLoc.getString("tx_nomUni") +"', "+rstLoc.getInt("co_itm")+", " +
                   rstLoc.getInt("co_itm")+", '"+rstLoc.getString("tx_codALt")+"', '"+rstLoc.getString("tx_codALt")+"', " +
                   " "+objUti.codificar(rstLoc.getString("tx_nomItm"),0)+", "+rstLoc.getInt("co_bod")+", "+bgdCanItm+", " +
                   objUti.redondeo(rstLoc.getDouble("nd_preCom"),objParSis.getDecimalesBaseDatos())+"," +
                   objUti.redondeo(rstLoc.getDouble("nd_preCom"),objParSis.getDecimalesBaseDatos())+", " +
                   objUti.redondeo(rstLoc.getDouble("nd_preCom"),objParSis.getDecimalesBaseDatos())+"," +
                   objUti.redondeo(rstLoc.getDouble("nd_porDesPreCom") ,objParSis.getDecimalesMostrar())+",'" +
                   ((blnIva==null)?"N":(blnIva.toString().equals("true"))?"S":"N")+"' , " +
                   bgdTotItm+", " +
                   bgdTotItm+"," +
                   bgdTotItm+",'I',"+
                   " 0, '"+str_MerIEFisBod+"', "+
                   objUti.redondeo(rstLoc.getDouble("nd_preCom"),objParSis.getDecimalesBaseDatos())+" , " +
                   objUti.redondeo(rstLoc.getDouble("nd_porDesPreCom") ,objParSis.getDecimalesMostrar())+" " +
                   " , "+bgdCanPen+", " +bgdCanPen+"); ";
 
                   strIns+=strSql;
                   System.out.println("ZafVen42_OC: insertarDetOC: " + strSql);
                   i++;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                stmLoc01.close();
                stmLoc01=null;
                
                
                stmIns.executeUpdate(strIns);
                
                /*TOTALES!!! */
                bgdValDocTot = objUti.redondearBigDecimal(bgdValDocSub.add(bgdIva), objParSis.getDecimalesMostrar());
                
                strSql=" UPDATE tbm_cabMovInv SET nd_sub="+bgdValDocSub+", nd_tot="+bgdValDocTot+", " ;
                strSql+="                         nd_valIva="+bgdIva+" \n";
                strSql+=" WHERE co_emp="+ rstExt.getInt("co_emp")+" AND ";
                strSql+=" co_loc="+rstExt.getInt("co_loc")+" AND co_tipDoc="+intCodTipDocOC+" " ;
                strSql+=" AND co_doc="+intCodDoc+"; "; 
                System.out.println("ZafVen42_OC: TOTALES : " + strSql);
                stmIns.executeUpdate(strSql);

                
                if(blnIsInmaconsaOD){
                    strSql=" UPDATE tbm_cabMovInv SET st_conInv='P' WHERE co_emp="+ rstExt.getInt("co_emp")+" AND ";
                    strSql+=" co_loc="+rstExt.getInt("co_loc")+" AND co_tipDoc="+intCodTipDocOC+" " ;
                    strSql+=" AND co_doc="+intCodDoc+"; "; 
                    System.out.println("ZafVen42_OC: UPDATE CABECERA ES INMACONSA : " + strSql);
                    stmIns.executeUpdate(strSql);
                }
                blnRes=true;        
            }
            else{
                blnRes=false;
            }
 
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false; 
        }
        catch(Exception evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, evt); 
        }
        return blnRes;
    }      



    
    private int intCodMotServ=0;
    private int intCodMotTran=0;
    /**
     * 
     * @param conTmp
     * @param CodEmp
     * @return 
     */
      private boolean FormaRetencion(java.sql.Connection conTmp, int CodEmp){
        boolean blnRes=false; 
        java.sql.Statement stmTmp;
        java.sql.ResultSet rst;
        try{
           if (conTmp!=null)
           {
              stmTmp = conTmp.createStatement(); 
              String sql = "SELECT tx_tipmot, co_mot FROM tbm_motdoc WHERE co_emp="+CodEmp+" AND tx_tipmot in ('B','S','T')";
               System.out.println("ZafVen42.FormaRetencion " + sql);
              rst=stmTmp.executeQuery(sql);
              while(rst.next()){
                  if(rst.getString("tx_tipmot").equals("B")) intCodMotBien= rst.getInt("co_mot");
                  else if(rst.getString("tx_tipmot").equals("S")) intCodMotServ= rst.getInt("co_mot");
                  else if(rst.getString("tx_tipmot").equals("T")) intCodMotTran= rst.getInt("co_mot");
              }
              rst.close();
              stmTmp.close();
              rst=null;
              stmTmp=null;
              blnRes=true;
          }
        }
        catch(SQLException Evt){  objUti.mostrarMsgErr_F1(this, Evt);     }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);     }
       return blnRes;
    }
    
    /**
     * 
     * @param conn
     * @param rstExt
     * @param intCodDoc
     * @param strConfInv
     * @return 
     */
 
    private boolean insertarCabOC(java.sql.Connection conn,java.sql.ResultSet rstExt,int intCodDoc, String strConfInv ){
      boolean blnRes=false;
      java.sql.Statement stmLoc, stmIns;
      java.sql.ResultSet rstLoc;
      String strSql="";
      String strFecha=""; 
      String strNomBen="";
      int intCodBen=0;
      int intSecGrp=0;
      int intSecEmp=0;
      try{
            if(conn!=null){
                stmLoc=conn.createStatement(); 
                stmIns=conn.createStatement(); 
                strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)javax.swing.JOptionPane.getFrameForComponent(this) ) ,"d/m/y");
                txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
                txtFecDoc.setText(strFecSis);
                intSecEmp = objUltDocPrint.getNumSecDoc(conn, rstExt.getInt("co_emp"));
                intSecGrp = objUltDocPrint.getNumSecDoc(conn, objParSis.getCodigoEmpresaGrupo());

                Glo_intCodSec=intSecEmp;

                int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
                strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";


                strSql="SELECT co_reg, tx_benchq FROM tbm_benchq  WHERE co_emp="+rstExt.getInt("co_emp")+" " +
                " AND co_cli="+rstExt.getInt("co_prv")+" and st_reg='P'";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                   intCodBen=rstLoc.getInt("co_reg");
                   strNomBen=rstLoc.getString("tx_benchq");
                }

                strSql = "SELECT CASE WHEN (ne_ultDoc+1) IS NULL THEN 1 ELSE (ne_ultDoc+1) END AS ultnum, st_predoc FROM tbm_cabTipDoc WHERE co_emp=" + rstExt.getInt("co_emp") + " "
                        + " AND co_loc=" + rstExt.getInt("co_loc") + " AND co_tipDoc=" + intCodTipDocOC;
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next())
                {
                    intNumDocOC = rstLoc.getInt("ultnum");
                }

              rstLoc.close();
              rstLoc=null;
              stmLoc.close();
              stmLoc=null;

              String strEmiChq="";  // JM: Preguntar

              stbDocRelEmp.append(" SELECT "+rstExt.getInt("co_emp")+" AS COEMP, "+rstExt.getInt("co_loc")+" AS COLOC , "+intCodTipDocOC+" AS COTIPDOC, "+intCodDoc+" AS CODOC " );

              strSql="INSERT INTO  tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
              " tx_nomCli, tx_dirCli, tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, tx_numPed, "+
              " tx_obs1, tx_obs2, nd_sub , nd_tot, nd_valiva, nd_porIva, co_forPag, fe_ing, co_usrIng,fe_ultMod, " +
              " co_usrMod , tx_desforpag ,st_reg, co_forret,tx_vehret,tx_choret,ne_secgrp,ne_secemp , st_regrep , st_tipdev, st_coninv, co_ben, tx_benchq "
              + " , st_emiChqAntRecFacPrv , st_imp,tx_tipMov  ) "+
              " VALUES("+rstExt.getInt("co_emp")+", "+rstExt.getInt("co_loc")+", "+intCodTipDocOC+", "+
              intCodDoc+",'"+strFecha+"', "+rstExt.getInt("co_prv")+", "+rstExt.getInt("co_usrIng")+", '','"+
              rstExt.getString("tx_nomCli") +"','"+rstExt.getString("tx_dir")+"', '"+rstExt.getString("tx_ide")+"','"+rstExt.getString("tx_tel")+"','"+ 
              rstExt.getString("tx_nomCiu")+"','"+rstExt.getString("tx_nomUsr")+ "',"+intNumDocOC+",0,"+
              "null,"+objUti.codificar(rstExt.getString("tx_obs1Com"), 0)+",'Generada Automaticamente por Zafiro v 0.1',"+ 
              "0 /*SubTotal*/,0.00 /*Total*/ ,"+
              "0 /* IVA */, "+objParSis.getPorcentajeIvaCompras()+","+rstExt.getInt("co_forPagCom")+","+ 
              " '"+strFecSis+"', "+rstExt.getInt("co_usrIng")+", '"+ strFecSis+ "',"+rstExt.getInt("co_usrIng")+",'"+
              rstExt.getString("tx_nomForPagCom")+"','A', "+rstExt.getInt("co_forRetCom") +",'','', " +
              intSecGrp+","+intSecEmp+" ,'I','C','"+strConfInv+"', "+intCodBen+", '"+strNomBen+"'"
              + "  ,"+(strEmiChq.equals("")?null:"'"+strEmiChq+"'")+" ,'S','I'  ) ;";  // ", "+(intSecGrp-1)+", "+(intSecEmp-1)+" " +
              System.out.println("INSERTAR OC : " + strSql);
              stmIns.executeUpdate(strSql); 

              strSql="INSERT INTO tbm_retmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_motdoc, st_regrep) " + 
              " VALUES("+rstExt.getInt("co_emp")+", "+rstExt.getInt("co_loc")+", "+intCodTipDocOC+", "+intCodDoc+", " +
              " 1, "+intCodMotBien+", 'I' );";
                
              System.out.println("INSERTAR OC2 : " + strSql);
              stmIns.executeUpdate(strSql); 
              stmIns.close();
              stmIns=null;

              blnRes=true;
            }
      }
      catch(Exception evt){ 
          blnRes=false; 
          objUti.mostrarMsgErr_F1(this, evt); 
      }
      return blnRes;
    }      
    
   
    
    /**
     * 
     * @param intCodEmp
     * @param intCodBodEmp
     * @return 
     */
    
        private int intBodGrp(int intCodEmp,int intCodBodEmp){
            int intCodBodGrp=0;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            String strCadena;
            try{
                conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conLoc != null) {
                    stmLoc=conLoc.createStatement();
                    strCadena="";
                    strCadena+=" SELECT co_empGrp, co_bodGrp  ";
                    strCadena+="  FROM tbr_bodEmpBodGrp  ";
                    strCadena+=" WHERE co_bod="+ intCodBodEmp ;
                    strCadena+=" AND co_emp="+intCodEmp +" AND co_empGrp="+objParSis.getCodigoEmpresaGrupo();
                    rstLoc=stmLoc.executeQuery(strCadena);
                    if(rstLoc.next()){
                            intCodBodGrp=rstLoc.getInt("co_bodGrp");
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                intCodBodGrp=0;
            }
            return intCodBodGrp;
        }
        /**
         * Validaciones de Autorizaciones
         * @param indice
         * @param CodEmp
         * @param CodLoc
         * @param CodCot
         * @return 
         */
        private boolean validaDatosAutorizacion(int indice,int CodEmp, int CodLoc,int CodCot){
            boolean blnRes=true;
            try{
                if(!validaFechasMAX(indice,tblDat.getValueAt(indice,INT_TBL_DAT_STR_TIP_RES_INV).toString(),false)){
                     blnRes=false;
                     MensajeInf("Datos incorrectos: La Fecha solicitada es mayor a la permitida, revise la Fecha y vuelva a intentarlo. ");
                 }
                if(!validaFechasPrimerDiaLaboral(indice,false,CodEmp,CodLoc)){
                     blnRes=false;
                     MensajeInf("Datos incorrectos: La Fecha solicitada, no corresponde al primer dia laboral registrado. ");
                } 
                if(!revisarCotizacionPendiente(CodEmp,CodLoc,CodCot)){
                    blnRes=false;
                    MensajeInf("La cotizacion "+CodCot+" ya no esta pendiente, no se puede autorizar. ");
                }
                
                if(!revisarCotizacionSinFactura(CodEmp,CodLoc,CodCot)){
                    blnRes=false;
                    MensajeInf("La cotizacion "+CodCot+" esta Facturada, no se puede autorizar. ");
                }
                
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
            }
            return blnRes;
        }
   
        /**
         * Validaciones solicitud de Reservas
         * 
         * @param indice
         * @param CodEmp
         * @param CodLoc
         * @param CodCot
         * @param isSol
         * @return 
         */
        private boolean validacionesSolicitarReserva(int indice, int CodEmp, int CodLoc, int CodCot, boolean isSol){
            boolean blnRes=true;
            try{
                if(!validaItemsServicio(CodEmp, CodLoc, CodCot)){
                    blnRes=false;
                    MensajeInf("La cotizacion contiene Items de Servicio o Transporte, los cuales no deben ser reservados.");
                }

                if(!validaFechasMAX(indice, tblDat.getValueAt(indice,INT_TBL_DAT_STR_TIP_RES_INV).toString(),isSol)){
                    blnRes=false;
                    MensajeInf("La Fecha solicitada es mayor a la permitida, revise la Fecha y vuelva a intentarlo");
                }
                 
                if(!validaTerminalesLSSegunEmpresaLocal(CodEmp,CodLoc,CodCot)){
                    blnRes=false;
                    MensajeInf("No puede solicitar reserva de codigos L/S en este local, debe reservarlo por el local que puede ser facturado");
                }
                 
                if(!validaFechasPrimerDiaLaboral(indice,isSol,CodEmp,CodLoc)){
                     blnRes=false;
                     MensajeInf("La Fecha solicitada, no corresponde al primer dia laboral registrado");
                }   
                 
                if(tblDat.getValueAt(indice,INT_TBL_DAT_STR_TIP_RES_INV).toString().equals("R")){
                    if(!validaTerminalesLReservaLocal(CodEmp,CodLoc,CodCot)){
                        blnRes=false;
                        MensajeInf("No puede solicitar Reserva en Empresa de Codigos L ");
                    }
                }
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
            }
            return blnRes;
        } 
        
        private int intDiasMaximoReserva=30;
        private int intDiasMaximoFacturaAutomatica=20;   /*  JM: Solicitado por LSC y WCampoverde  2019-Jul-16 */
        /**
         * Validacion creada para que no puedan reervar nada por mas de 30 dias, desde la fecha actual.
         * @param indice
         * @param isSol TRUE si es una solicitud FALSE si es una autorizacion
         * @return 
         */
        private boolean validaFechasMAX(int indice, String strTipSol,boolean isSol){
            boolean blnRes=false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            String strFechaSol="";
            try{
                 conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conLoc!=null){
                    stmLoc=conLoc.createStatement();
                    if(isSol){
                        strFechaSol=objUti.formatearFecha(objTblMod.getValueAt(indice, INT_TBL_DAT_FEC_SOL).toString(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                    }
                    else{
                        strFechaSol=objUti.formatearFecha(objTblMod.getValueAt(indice, INT_TBL_DAT_FEC_SOL_FAC_AUT).toString(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                    }
                    strSql="SELECT '"+strFechaSol+"' - CURRENT_DATE  as dias ";
                    System.out.println("dias... "+ strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    if(rstLoc.next()){
                        rstLoc.getInt("dias");
                        if(strTipSol.equals("R")){
                            if(rstLoc.getInt("dias")<=intDiasMaximoReserva){
                                blnRes=true;
                            }
                        }
                        else{
                            if(rstLoc.getInt("dias")<=intDiasMaximoFacturaAutomatica){  /*  JM: Solicitado por LSC y WCampoverde  2019-Jul-16 */
                                blnRes=true;
                            }
                        }
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                    conLoc.close();
                    conLoc=null; 
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
                blnRes=false; 
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
            }
            return blnRes;
        }
        
        /**
         * Si la reserva es de generar la factura automaticamente en el primer dia laboral revisa que las fechas colocadas sean las adecuadas
         * @param indice
         * @param isSol
         * @param CodEmp
         * @param CodLoc
         * @return 
         */
        
        private boolean validaFechasPrimerDiaLaboral(int indice,boolean isSol, int CodEmp, int CodLoc){
            boolean blnRes=false;
            try{
                if(objTblMod.getValueAt(indice, INT_TBL_DAT_EST_FAC_PRI_DIA_LAB).toString().equals("S")){
                    if(isSol){
                        if(getFechaLab(CodEmp,CodLoc).equals(objTblMod.getValueAt(indice, INT_TBL_DAT_FEC_SOL).toString())){
                            blnRes=true;
                        }
                        //strFechaSol=objUti.formatearFecha(objTblMod.getValueAt(indice, INT_TBL_DAT_FEC_SOL).toString(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                    }
                    else{
                        if(getFechaLab(CodEmp,CodLoc).equals(objTblMod.getValueAt(indice, INT_TBL_DAT_FEC_SOL_FAC_AUT).toString())){
                            blnRes=true;
                        }
                        //strFechaSol=objUti.formatearFecha(objTblMod.getValueAt(indice, INT_TBL_DAT_FEC_SOL_FAC_AUT).toString(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                    }
                }
                else{
                    blnRes=true;
                }
                
            }           
            catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
            }
            return blnRes;
        }
        
        
        /**
         * Revisa las configuraciones en reglas de inventario del usuario que ingreso la cotizacion y segun eso 
         * determina si puede o no reservar un codigo L  S
         * @param indice
         * @param isSol
         * @return 
         */
        
        private boolean validaTerminalesLSSegunEmpresaLocal(int CodEmp,int CodLoc, int CodCot){
            boolean blnRes=true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            try{
                 conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conLoc!=null){
                    stmLoc=conLoc.createStatement();
                    strSql=""; 
                    strSql+="  SELECT a1.co_emp, a1.co_loc, a1.co_cot, a2.tx_codALt, trim(SUBSTR (UPPER(a2.tx_codalt), length(a2.tx_codalt) ,1)) as terminal,\n";
                    strSql+="         CASE WHEN ( (trim(SUBSTR (UPPER(a3.tx_codalt), length(a3.tx_codalt) ,1))  IN (  \n";
                    strSql+="               SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+CodEmp+"  AND co_loc="+CodLoc+" \n";
                    strSql+="                AND co_tipdoc=228  AND co_usr=(SELECT co_usrIng from tbm_cabCotVen WHERE co_emp="+CodEmp+"  AND co_loc="+CodLoc+"  AND co_cot="+CodCot+" )  AND st_reg='A' /* AND st_tipmov='C' */  \n";
                    strSql+="        ))) THEN 'S' ELSE 'N' END  as isterL  \n";
                    strSql+=" FROM tbm_cabCotVen as a1  \n";
                    strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND  a1.co_cot=a2.co_cot) \n";
                    strSql+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                    strSql+=" WHERE  a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+" \n";
                    System.out.println("validaTerminalesLSSegunEmpresaLocal... "+ strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    while(rstLoc.next()){
                        if(rstLoc.getString("terminal").equals("L")  ){
                            if(rstLoc.getString("isterL").equals("N")){
                                blnRes=false;
                            } 
                        }
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                    conLoc.close();
                    conLoc=null;
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
                blnRes=false; 
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
            }
            return blnRes;
        }
        
        
         /**
         * Revisa las configuraciones en reglas de inventario del usuario que ingreso la cotizacion y segun eso 
         * determina si puede o no reservar un codigo L  S
         * @param indice
         * @param isSol
         * @return 
         */
        
        private boolean validaTerminalesLReservaLocal(int CodEmp,int CodLoc, int CodCot){
            boolean blnRes=true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            try{
                 conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conLoc!=null){
                    stmLoc=conLoc.createStatement();
                    strSql=""; 
                    strSql+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot, a2.tx_codALt, trim(SUBSTR (UPPER(a2.tx_codalt), length(a2.tx_codalt) ,1)) as terminal \n";
                    strSql+=" FROM tbm_cabCotVen as a1  \n";
                    strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND  a1.co_cot=a2.co_cot) \n";
                    strSql+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                    strSql+=" WHERE  a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+" \n";
                    System.out.println("validaTerminalesLReservaLocal... "+ strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    while(rstLoc.next()){
                        if(rstLoc.getString("terminal").equals("L")){
                            blnRes=false;
                        }
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                    conLoc.close();
                    conLoc=null;
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
                blnRes=false; 
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
            }
            return blnRes;
        }
        
        /**
         * Valida si se esta tratando de reservar un item de servicio o transporte o otros, esos items no pueden ser reservados
         * @param CodEmp
         * @param CodLoc
         * @param CodCot
         * @return 
         */
        
        private boolean validaItemsServicio(int CodEmp, int CodLoc, int CodCot){
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            boolean blnRes=true;
            try{
                conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conLoc != null) {
                    stmLoc=conLoc.createStatement();
                    strSql="";
                    strSql+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot \n";
                    strSql+=" FROM tbm_cabCotVen as a1 \n";
                    strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                    strSql+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)  \n";
                    strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" AND (a3.st_ser='S' OR a3.st_ser='T' OR a3.st_ser='O')  \n";
                    rstLoc=stmLoc.executeQuery(strSql);
                    if(rstLoc.next()){
                        blnRes=false;
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
                blnRes=false; 
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(this, e);
                blnRes=false;
            }
            return blnRes;
        }
    
        
    private boolean validacionCodigosL(int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=false;
        String strMsg;
        try{
            if(objDatItm.verificarCodigosL(CodEmp, CodLoc, CodCot)){
                if(objDatItm.marcarItemsOcupados(CodEmp, CodLoc, CodCot)){
                    blnRes=true;
                }
            }
            else{
                if(objDatItm.modificaItemTerminalOcupado(CodEmp, CodLoc, CodCot)){
                    blnRes=true;
                }
                else{
                    blnRes = false;
                    strMsg = "<html> Ocurrio un error no se pudo modificar los Items. <BR>";
                    strMsg += "Si el problema persiste comunicarse con su Administrador de sistema. JM <html>";
                    JOptionPane.showMessageDialog(this, strMsg, strTitCorEle, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        catch(Exception  Evt){ 
            objUti.mostrarMsgErr_F1(this, Evt);
            blnRes=false;
        }
        return blnRes;
    }


}





































