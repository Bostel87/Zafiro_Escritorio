/*
 * ZafCom96_01
 * 
 * Created on 1 de octubre de 2016, 15:44
 */
package Compras.ZafCom96;

import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafCfgBod.ZafCfgBod;
import Librerias.ZafCnfDoc.ZafCnfDoc;
import Librerias.ZafMovIngEgrInv.ZafMovIngEgrInv;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import java.util.ArrayList;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import java.sql.DriverManager;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author tsanginez
 */
public class ZafCom96_01 extends javax.swing.JDialog {

    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    //PARA CARGAR LA PANTALLA PRINCIPAL
    private final int INT_TBL_DAT_LIN = 0;   //SECUENCIAL
    private final int INT_TBL_DAT_COD_ITM = 1;//CODIGO DEL ITEM
    private final int INT_TBL_DAT_COD_ALT = 2;//CODIGO ALTERNO DEL ITEM
    private final int INT_TBL_DAT_COD_LET = 3;//CODIGO LET DEL ITEM
    private final int INT_TBL_DAT_NOM_ITM = 4;//NOMBRE DEL ITEM
    private final int INT_TBL_DAT_UNI_MED = 5;//UNIDAD DE MEDIDA
    private final int INT_TBL_DAT_NUM_CAN = 6;//CANTIDAD
    private final int INT_TBL_DAT_NUM_CAN_TOT_CON = 7;//CANTIDAD TOTAL CONFIRMADA
    private final int INT_TBL_DAT_NUM_CAN_TOT_CAN = 8;//CANTIDAD TOTAL CANCELADA 
    private final int INT_TBL_DAT_NUM_CAN_PEN = 9;//CANTIDAD PENDIENTE
    private final int INT_TBL_DAT_NUM_CAN_CAN = 10;//CANTIDAD A CANCELAR

    private Vector vecReg, vecDat, vecCab;
    private Vector vecRegCon, vecDatCon;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblFilCab objTblFilCab;
    private String strSql, strAux;
    private Connection con;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private ZafMouMotAda objMouMotAda;
    private int intCodEmp1, intCodLoc1, intCodTipDoc1, intCodDoc1;
    private String strEstTra;
    private ZafThreadGUI objThrGUI;
    private boolean blnCon;                     //true: Continua la ejecucián del hilo.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafTblCelEdiTxt objTblCelEdiTxt;            //Editor: JTextField en celda.
    private int intCodEmpSol1, intCodLocSol1, intCodTipDocSol1, intCodDocSol1;
    private boolean blnAgrPenChkTblSel = true;                                 //Marcar todas las casillas de verificación del JTable de bodegas.
    private int intTraInvCoEmp,intTraInvCoLoc, intTraInvCoTipDoc, intTraInvCoDoc;
    private int intCodSeg, intCodReg;
    private ArrayList arlRegMovInv,arlDatMovInv;
     /* CONSTANTES PARA CONTENEDOR MOVIMIENTOS DE INVENTARIO  */
    private static final int INT_ARL_COD_EMP_MOV = 0;
    private static final int INT_ARL_COD_LOC_MOV = 1;
    private static final int INT_ARL_COD_TIP_DOC_MOV = 2;
    private static final int INT_ARL_COD_DOC_MOV = 3;
    //
    private ArrayList arlDatItemMovInv, arlRegItemMovInv;
    private ArrayList arlItm;
    private int intCodBodegaMovimientoOrg, intCodBodegaMovimientoDes;
    private String strNombreBodegaOrg,strNombreBodegaDes;
    /* CONSTANTES PARA CONTENEDOR A ENVIAR Tony  */
    final int INT_ARL_COD_EMP = 0;
    final int INT_ARL_COD_LOC = 1;
    final int INT_ARL_COD_TIP_DOC = 2;
    final int INT_ARL_COD_BOD_GRP = 3;
    final int INT_ARL_COD_ITM = 4;
    final int INT_ARL_COD_ITM_MAE = 5;
    final int INT_ARL_COD_BOD = 6;
    final int INT_ARL_NOM_BOD = 7;
    final int INT_ARL_CAN_COM = 8;
    final int INT_ARL_CHK_CLI_RET = 9;
    final int INT_ARL_EST_BOD = 10;
    final int INT_ARL_ING_EGR_FIS_BOD = 11;
    final int INT_ARL_COD_BOD_GRP_ING = 12;
    //
    private int intCodEmpSolTra, intCodLocSolTra, intCodTipDocSolTra, intCodItmSolTra, intCodItmMaeSolTra;
    private String strCodAltItm;
    private String strNomItm;
    private double dblCosUni;
    private double dblPreUni;
    private String strEstIva;
    private String strCodAltItm2;
    private String strUniMedItm;
    private int intCodItmGru;
    private double dblPesItm;
    private String strIvaCom;
    private String strIvaVen;
    private String strMerIngEgrFisBod;
    private double dblCan,dblCanCon,dblCanCan;
    //todo de cancelacion
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
    private java.awt.Component componente;
    private ArrayList arlDatHistoricoMovInvEgr;
    private ArrayList arlConDatPreEmpEgr;
    private ArrayList arlConDatPreEmpIng;
    private ArrayList arlConDatPreInvIngAux;
    private ArrayList arlConDatPreInvEgrAux;
    private int intCodCliGenVen;
    private int intCodCliGenCom;
    private int intCodEmpGenVen;
    private int intCodLocGenVen;
    private int intCodTipDocGenVen;
    private int intCodEmpGenCom;
    private int intCodLocGenCom;
    private int intCodTipDocGenCom;
    private int intCodLocTra;
    private int intCodTipDocGenTra;
    private int intCodEmpGenDevVen;
    private int intCodLocGenDevVen;
    private int intCodTipDocGenDevVen;
    private int intCodEmpGenDevCom;
    private int intCodLocGenDevCom;
    private int intCodTipDocGenDevCom;
    private int intCodItmGenVen;
    private int intCodItmGenCom;
    private ArrayList arlConRegPreEmpEgr;
    private ArrayList arlDatosItemEgreso;
    private ArrayList arlConRegPreEmpIng;
    private ArrayList arlDatosItemIngreso;
    //** EGRING **//
    private static final int INT_ARL_CODEMP = 0;
    private static final int INT_ARL_CODLOC = 1;
    private static final int INT_ARL_CODTIPDOC = 2;
    private static final int INT_ARL_CODCLIPRV = 3;
    private static final int INT_ARL_CODBODORIDES = 4;
    private static final int INT_ARL_CODITMGRP = 5;
    private static final int INT_ARL_CODITMEMP = 6;
    private static final int INT_ARL_CODITMMAE = 7;
    private static final int INT_ARL_CODALTITM = 8;
    private static final int INT_ARL_NOMITM = 9;
    private static final int INT_ARL_UNIMEDITM = 10;
    private static final int INT_ARL_CODLETITM = 11;
    private static final int INT_ARL_CANITM = 12;
    private static final int INT_ARL_COSUNI = 13;//recibe el costo unitario del item tbm_inv.nd_cosUni
    private static final int INT_ARL_PREUNI = 14;
    private static final int INT_ARL_PESUNI = 15;
    private static final int INT_ARL_IVACOMITM = 16;
    private static final int INT_ARL_ESTINGEGRMERBOD = 17;
    
    private ZafCfgBod objCfgBod;
    private ZafStkInv objStkInv;
    private int intCodEmpMovStk;
    
    /**
     * Función que permite obtener el nombre del campo que se desea actualizar
     *
     * @param indiceNombreCampo
     * <HTML>
     * <BR> 0: Actualiza en campo "nd_stkAct"
     * <BR> 1: Actualiza en campo "nd_canPerIng"
     * <BR> 2: Actualiza en campo "nd_canPerEgr"
     * <BR> 3: Actualiza en campo "nd_canBodIng"
     * <BR> 4: Actualiza en campo "nd_canBodEgr"
     * <BR> 5: Actualiza en campo "nd_canDesIng"
     * <BR> 6: Actualiza en campo "nd_canDesEgr"
     * <BR> 7: Actualiza en campo "nd_canTra"
     * <BR> 8: Actualiza en campo "nd_canRev"
     * <BR> 9: Actualiza en campo "nd_canRes"
     * <BR> 10: Actualiza en campo "nd_canDis"
     * </HTML>
     * @return true: Si se pudo obtener el nombre del campo
     * <BR> false: Caso contrario
     */
    final int INT_ARL_STK_INV_STK_ACT = 0;  // nd_stkAct
    final int INT_ARL_STK_INV_NOM_CAM_ACT = 1;
    final int INT_ARL_STK_INV_NOM_CAM_ACT_2 = 2;
    final int INT_ARL_STK_INV_CAN_ING_BOD = 3;  // nd_canBodIng --> transferencia afectar ingreso 
    final int INT_ARL_STK_INV_CAN_EGR_BOD = 4;  // nd_canBodEgr --> transferencia afectar egreso
    final int INT_ARL_STK_INV_CAN_DES_ENT_BOD = 5;
    final int INT_ARL_STK_INV_CAN_DES_ENT_CLI = 6;
    final int INT_ARL_STK_INV_CAN_TRA = 7;
    final int INT_ARL_STK_INV_CAN_REV = 8;
    final int INT_ARL_STK_INV_CAN_RES = 9;
    final int INT_ARL_STK_INV_CAN_DIS = 10;  // nd_canDis
    
    private ArrayList arlRegStkInvItm, arlDatStkInvItm, arlConDatTraInvStkEgr, arlConDatTraInvStkIng;
    
     /* NUEVO CONTENEDOR PARA ITEMS */
    private static final int INT_STK_INV_COD_ITM_GRP = 0;
    private static final int INT_STK_INV_COD_ITM_EMP = 1;
    private static final int INT_STK_INV_COD_ITM_MAE = 2;
    private static final int INT_STK_INV_COD_LET_ITM = 3;
    private static final int INT_STK_INV_CAN_ITM = 4;
    private static final int INT_STK_INV_COD_BOD_EMP = 5;
    
    /* CONSTANTES PARA CONTENEDOR A ENVIAR   */
    private static final int INT_ARL_COD_EMP_ = 0;            // CODIGO DE LA EMPRESA
    private static final int INT_ARL_COD_LOC_ = 1;            // CODIGO DEL LOCAL
    private static final int INT_ARL_COD_TIP_DOC_ = 2;        // CODIGO DEL TIPO DE DOCUMENTO
    private static final int INT_ARL_COD_CLI_ = 3;            // CODIGO DEL CLIENTE/PROVEEDOR
    private static final int INT_ARL_COD_BOD_ING_EGR_ = 4;    // CODGIO DE LA BODEGA DE INGRESO/EGRESO 
    private static final int INT_ARL_COD_ITM_GRP_ = 5;        // CODIGO DE ITEM DE GRUPO
    private static final int INT_ARL_COD_ITM_ = 6;            // CODIGO DEL ITEM POR EMPRESA
    private static final int INT_ARL_COD_ITM_MAE_ = 7;        // CODIGO DEL ITEM MAESTRO
    private static final int INT_ARL_COD_ITM_ALT_ = 8;        // CODIGO DEL ITEM ALTERNO 
    private static final int INT_ARL_NOM_ITM_ = 9;            // NOMBRE DEL ITEM
    private static final int INT_ARL_UNI_MED_ITM_ = 10;       // UNIDAD DE MEDIDA
    private static final int INT_ARL_COD_LET_ITM_ = 11;       // CODIGO ALTERNO DEL ITEM 2 (CODIGO DE TRES LETRAS)
    private static final int INT_ARL_CAN_MOV = 12;           // CANTIDAD 
    private static final int INT_ARL_COS_UNI = 13;           // COSTO UNITARIO DEL ITEM
    private static final int INT_ARL_PRE_UNI = 14;           // PRECIO UNITARIO DEL ITEM
    private static final int INT_ARL_PES_ITM = 15;           // PESO DEL ITEM
    private static final int INT_ARL_IVA_COM_ITM = 16;       // ESTADO DEL ITEM IVA
    private static final int INT_ARL_EST_MER_ING_EGR_FIS_BOD = 17;// SI LA MERCADERIA EGRESA FISICAMENTE / SI SE CONFIRMA
    private static final int INT_ARL_IND_ORG = 18;           // PARA ORGANIZAR AL FINAL LOS PRESTAMOS 
    
    private ArrayList arlConDatTraInv;
    private ArrayList arlConRegTraInv;
    private String strTipMovCnfTot;
    private int intCodBodEmp;
    private int intCodEmpDocCnf;
    private int intCodLocDocCnf;
    private int intCodTipDocCnf;
    private int intCodDocCnf;
    
    
    private ZafCnfDoc objCnfDoc;
    private java.awt.Component cmpPad;
    private Date dtpFecDoc;
    private ArrayList arlDatItm;
    private char chrGenIva;
    private String strTipMovSeg;
    private int intCodEmp;
    private int intCodLoc;
    private int intCodDoc;
    private int intNumDoc;
    private int intCodTipDoc;
//    private int intCodEmpSol;
//    private int intCodLocSol;
//    private int intCodTipDocSol;
//    private int intCodDocSol;
    private BigDecimal bdeSig;
    private int intNumSecEmp;
    private int intNumSecGrp;
    private ZafAsiDia objAsiDia;
    private Vector vecRegDia, vecDatDia;
    private String strSQL;
    private int intCodCliPrv;
    private int intCodBodIng;
    private int intCodEmpEgrGenAsiDia, intCodEmpIngGenAsiDia;
    private Object objEstDocGenOrdDes;
    private int intCodBodEgr;
    private ZafSegMovInv objSegMovInv;
    private ArrayList arlDatCnfNumRecEnt, arlRegCnfNumRecEnt;//Arreglo recibido como parametro
    private BigDecimal bgdValDocSub;
    private BigDecimal bgdValDocIva;
    private int intNumDec;
    private BigDecimal bgdValPorIva;
    private BigDecimal bgdValDocTot;
    String strVersion;
    
    private static final int INT_ARL_COD_EMP_A=0;
    private static final int INT_ARL_COD_LOC_A=1;
    private static final int INT_ARL_COD_TIP_DOC_A=2;
    private static final int INT_ARL_COD_CLI_PRV_A=3;
    private static final int INT_ARL_COD_BOD_ORI_DES_A=4;
    private static final int INT_ARL_COD_ITM_GRP_A=5;
    private static final int INT_ARL_COD_ITM_EMP_A=6;
    private static final int INT_ARL_COD_ITM_MAE_A=7;    
    private static final int INT_ARL_COD_ALT_ITM_A=8;
    private static final int INT_ARL_NOM_ITM_A=9;
    private static final int INT_ARL_UNI_MED_ITM_A=10;
    private static final int INT_ARL_COD_LET_ITM_A=11;    
    private static final int INT_ARL_CAN_ITM_A=12;
    private static final int INT_ARL_COS_UNI_A=13;//recibe el costo unitario del item tbm_inv.nd_cosUni
    private static final int INT_ARL_PRE_UNI_A=14;
    private static final int INT_ARL_PES_UNI_A=15;
    private static final int INT_ARL_IVA_COM_ITM_A=16;
    private static final int INT_ARL_EST_ING_EGR_MER_BOD_A=17;
    
    private static final int INT_ARL_DAT_COD_EMP_CNF_=0;
    private static final int INT_ARL_DAT_COD_LOC_CNF_=1;
    private static final int INT_ARL_DAT_COD_TIP_DOC_CNF_=2;
    private static final int INT_ARL_DAT_COD_DOC_CNF_=3;
    private static final int INT_ARL_DAT_COD_REG_CNF_=4;
    private static final int INT_ARL_DAT_COD_ITM_GRP_CNF_=5;
    private static final int INT_ARL_DAT_COD_ITM_EMP_CNF_=6;
    private static final int INT_ARL_DAT_COD_ITM_MAE_CNF_=7;
    private static final int INT_ARL_DAT_CAN_ITM_CNF_=8;
    private static final int INT_ARL_DAT_COD_EMP_ORI_=9;
    private static final int INT_ARL_DAT_COD_LOC_ORI_=10;
    private static final int INT_ARL_DAT_COD_TIP_DOC_ORI_=11;
    private static final int INT_ARL_DAT_COD_DOC_ORI_=12;
    private static final int INT_ARL_DAT_COD_REG_ORI_=13;
    private static final int INT_ARL_DAT_CAN_ITM_NOT_CNF_=14;
    private static final int INT_ARL_DAT_COD_BOD_EMP_=15;
    private static final int INT_ARL_DAT_EST_ING_EGR_MER_BOD_=16;
    
    private final int INT_VEC_DIA_LIN=0;
    private final int INT_VEC_DIA_COD_CTA=1;
    private final int INT_VEC_DIA_NUM_CTA=2;
    private final int INT_VEC_DIA_BUT_CTA=3;
    private final int INT_VEC_DIA_NOM_CTA=4;
    private final int INT_VEC_DIA_DEB=5;
    private final int INT_VEC_DIA_HAB=6;
    private final int INT_VEC_DIA_REF=7;
    private final int INT_VEC_DIA_EST_CON=8;
    
    String strCodAltItmConf,strNomItmConf,strCodAltItm2Conf;
     int intCodItmGruConf;
    /* CONTENEDOR PARA SOLICITUDES DE TRANSFERENCIAS CANCELACION */
    private static final int INT_ARL_CAN_COD_EMP = 0;
    /**
     * Creates new form
     */
    public ZafCom96_01(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis ZafParSis, HashMap map) {
        super(parent, modal);
        try {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) ZafParSis.clone();
            initComponents();
            this.setTitle(objParSis.getNombreMenu());
            lblTit.setText(objParSis.getNombreMenu());
            componente = this;
            cmpPad=componente;
            objUti = new ZafUtil();
            intCodEmp1 = (Integer) map.get("intCodEmp");
            intCodLoc1 = (Integer) map.get("intCodLoc");
            intCodTipDoc1 = (Integer) map.get("intCodTipDoc");
            intCodDoc1 = (Integer) map.get("intCodDoc");
            strEstTra = (String)map.get("strEstTra");
            strVersion = (String)map.get("strVersion");
            objCnfDoc=new ZafCnfDoc(objParSis, cmpPad);
            datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            configurarFrm(); 
            if (!cargarItmCan()) {
                mostrarMsgInf("Fallo la carga de información");
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            //Inicializar objetos.
            objUti = new ZafUtil();
            vecDat = new Vector();
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            configurarTblDat();
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Función que permite configurar la tabla
     *
     * @return
     */
    private boolean configurarTblDat() {
        boolean blnRes = false;
        try {
            //Configurar JTable: Establecer el modelo.
            vecCab = new Vector(10);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_ITM, "Cod.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT, "Cod.Alt.");
            vecCab.add(INT_TBL_DAT_COD_LET, "Cod.Let.");
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Nom.Itm.");
            vecCab.add(INT_TBL_DAT_UNI_MED, "Uni.Med.");
            vecCab.add(INT_TBL_DAT_NUM_CAN, "Can.");
            vecCab.add(INT_TBL_DAT_NUM_CAN_TOT_CON, "Can.Tot.Con.");
            vecCab.add(INT_TBL_DAT_NUM_CAN_TOT_CAN, "Can.Tot.Can.");
            vecCab.add(INT_TBL_DAT_NUM_CAN_PEN, "Can.Pen.");
            vecCab.add(INT_TBL_DAT_NUM_CAN_CAN, "Can.Can.");
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_NUM_CAN, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_NUM_CAN_TOT_CON, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_NUM_CAN_TOT_CAN, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_NUM_CAN_PEN, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_NUM_CAN_CAN, objTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(140);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CAN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CAN_TOT_CON).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CAN_TOT_CAN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CAN_PEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CAN_CAN).setPreferredWidth(70);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_DAT_NUM_CAN_CAN);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            //Configurar JTable: Editor de la tabla.
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CAN_TOT_CON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CAN_TOT_CAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CAN_PEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CAN_CAN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CAN_CAN).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bdeCanPenCan=BigDecimal.ZERO;
                BigDecimal bdeCanCnfCan=BigDecimal.ZERO;
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    bdeCanPenCan=new BigDecimal(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NUM_CAN_PEN).toString());
                }
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        bdeCanCnfCan=new BigDecimal(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NUM_CAN_CAN).toString());
                        if(bdeCanPenCan.compareTo(bdeCanCnfCan)>=0){
                            //objTblMod.setChecked(true, intFilSel, INT_TBL_DAT_CHK); PENDIENTE PARA VERIFICAR
                        }
                        else{
                            //objTblMod.setChecked(false, intFilSel, INT_TBL_DAT_CHK);
                            objTblMod.setValueAt(null, intFilSel, INT_TBL_DAT_NUM_CAN_CAN);
                            mostrarMsgInf("<HTML>La cantidad ingresada es superior a la permitida<BR>Verifique la cantida y vuelva a ingresarla</HTML>");
                        }
                    
                }
            });
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (blnAgrPenChkTblSel) {
                       if (validaIngCanPen(blnAgrPenChkTblSel)) {
                        tblAgrCanPenMouseClicked(evt);    
                    } 
                    }else{
                       if (validaIngCanPen(blnAgrPenChkTblSel)) {
                        tblAgrCanPenMouseClicked(evt);    
                    } 
                    }
                }
            });
            

            //Configurar JTable: Editor de búsqueda.
            objTblBus = new ZafTblBus(tblDat);
            setEditable(true);
            //Libero los objetos auxiliares.
            tcmAux = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    private void setEditable(boolean editable) {
        if (editable == true) {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        } else {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panRpt = new javax.swing.JPanel();
        tabFil = new javax.swing.JTabbedPane();
        panFilGen = new javax.swing.JPanel();
        spnGen = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panObs = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtObs1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtObs2 = new javax.swing.JTextField();
        panBot = new javax.swing.JPanel();
        panSubBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblTit.setText("Título de la ventana1");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panRpt.setLayout(new java.awt.BorderLayout());

        panFilGen.setLayout(new java.awt.BorderLayout());

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
        tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatMouseClicked(evt);
            }
        });
        tblDat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDatKeyPressed(evt);
            }
        });
        spnGen.setViewportView(tblDat);

        panFilGen.add(spnGen, java.awt.BorderLayout.CENTER);

        jLabel1.setText("Observación 1:");
        jLabel1.setPreferredSize(new java.awt.Dimension(50, 14));

        txtObs1.setPreferredSize(new java.awt.Dimension(15, 30));

        jLabel2.setText("Observación 2:");

        javax.swing.GroupLayout panObsLayout = new javax.swing.GroupLayout(panObs);
        panObs.setLayout(panObsLayout);
        panObsLayout.setHorizontalGroup(
            panObsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panObsLayout.createSequentialGroup()
                .addGroup(panObsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panObsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtObs2)
                    .addComponent(txtObs1, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE))
                .addContainerGap())
        );
        panObsLayout.setVerticalGroup(
            panObsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panObsLayout.createSequentialGroup()
                .addGroup(panObsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtObs1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panObsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtObs2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panFilGen.add(panObs, java.awt.BorderLayout.PAGE_END);

        tabFil.addTab("General", panFilGen);

        panRpt.add(tabFil, java.awt.BorderLayout.CENTER);

        getContentPane().add(panRpt, java.awt.BorderLayout.CENTER);

        panBot.setLayout(new java.awt.BorderLayout());

        butAce.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butAce.setText("Guardar");
        butAce.setPreferredSize(new java.awt.Dimension(79, 23));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panSubBot.add(butAce);

        butCan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCan.setText("Cerrar");
        butCan.setPreferredSize(new java.awt.Dimension(79, 23));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panSubBot.add(butCan);

        panBot.add(panSubBot, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBot.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBot, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(744, 450));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        try {
            if (objTblMod.isDataModelChanged()) {
                if (validacionCompraVenta()) {
                    if (isSolicitudCambia()) {
                    
                    if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?") == 0) {
                        generaCancelacion();
                        pgrSis.setValue(0);
                    }
                    } else {
                        if (mostrarMsgCon("No es posible cancelar los items solicitados porque ya no están pendiendes. ¿Desea consultar los datos actualizados?") == 0) {
                            cargarItmCan();
                        }
                    }
                }else{
                    mostrarMsgInf("La cancelación no se encuentra disponible en este periodo de fecha. No es posible cancelar los items solicitados, porfavor comunicarse con sistemas");
                    cargarItmCan();
                }
                
            } else {
                mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        cerrarFrm();
    }//GEN-LAST:event_butCanActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_formWindowClosing

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        
    }//GEN-LAST:event_tblDatKeyPressed

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked
        
    }//GEN-LAST:event_tblDatMouseClicked

    /**
     * Función que permite cerrar el formulario
     */
    private void cerrarFrm() {
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == 0) {
            System.gc();
            dispose();
        }
    }

    

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_COD_ITM:
                    strMsg = "Código del item";
                    break;
                case INT_TBL_DAT_COD_ALT:
                    strMsg = "Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_LET:
                    strMsg ="Código alterno 2 del item";
                case INT_TBL_DAT_NOM_ITM:
                    strMsg = "Nombre del item";
                    break;
                case INT_TBL_DAT_UNI_MED:
                    strMsg = "Unidad de medida";
                    break;
                case INT_TBL_DAT_NUM_CAN:
                    strMsg = "Cantidad";
                    break;
                case INT_TBL_DAT_NUM_CAN_TOT_CON:
                    strMsg = "Cantidad total confirmada";
                    break;
                case INT_TBL_DAT_NUM_CAN_TOT_CAN:
                    strMsg = "Cantidad total cancelada";
                    break;
                case INT_TBL_DAT_NUM_CAN_PEN:
                    strMsg = "Cantidad pendiente";
                    break;
                case INT_TBL_DAT_NUM_CAN_CAN:
                    strMsg = "Cantidad a cancelar";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFilGen;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panSubBot;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnGen;
    private javax.swing.JTabbedPane tabFil;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtObs1;
    private javax.swing.JTextField txtObs2;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private class ZafThreadGUI extends Thread {

        public void run() {
            if (!cargarItmCan()) {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
            }
            //Establecer el foco en el JTable sálo cuando haya datos.
            if (tblDat.getRowCount() > 0) {
                tabFil.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI = null;
        }
    }

    private boolean cargarItmCan() {
        intCodEmpSol1 = intCodEmp1;
        intCodLocSol1 = intCodLoc1;
        intCodTipDocSol1 = intCodTipDoc1;
        intCodDocSol1 = intCodDoc1;
        Boolean blnRes = false;
        if (obtenerCodSegReg(intCodEmpSol1, intCodLocSol1, intCodTipDocSol1, intCodDocSol1)) {
            if (obtenerMovimientosInventario()) {
                for (int j = 0; j < arlDatMovInv.size(); j++) {
                    int intCodEmpMov = Integer.parseInt(objUti.getStringValueAt(arlDatMovInv, j, INT_ARL_COD_EMP_MOV));
                    int intCodLocMov = Integer.parseInt(objUti.getStringValueAt(arlDatMovInv, j, INT_ARL_COD_LOC_MOV));
                    int intCodTipDocMov = Integer.parseInt(objUti.getStringValueAt(arlDatMovInv, j, INT_ARL_COD_TIP_DOC_MOV));
                    int intCodDocMov = Integer.parseInt(objUti.getStringValueAt(arlDatMovInv, j, INT_ARL_COD_DOC_MOV));
                    int intCodCfg = obtenerCodigoCfg(intCodEmpMov, intCodLocMov, intCodTipDocMov);
                    if (validaCfgTra(intCodCfg)) {
                        if (obtenerItemsMovInv(intCodEmpMov, intCodLocMov, intCodTipDocMov, intCodDocMov)) {
                            for (int x = 0; x < arlDatItemMovInv.size(); x++) {
                                if (obtenerDatosItem(objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM_MAE), objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_EMP))) {//Porque es el egreso de la tranferencia
                                    if (consultaItemsMovInv(x, intCodEmpMov, intCodLocMov, intCodTipDocMov, intCodDocMov)) {
                                        vecReg = new Vector();
                                        vecReg.add(INT_TBL_DAT_LIN, "");
                                        vecReg.add(INT_TBL_DAT_COD_ITM, objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM));
                                        vecReg.add(INT_TBL_DAT_COD_ALT, strCodAltItm);
                                        vecReg.add(INT_TBL_DAT_COD_LET, strCodAltItm2);
                                        vecReg.add(INT_TBL_DAT_NOM_ITM, strNomItm);
                                        vecReg.add(INT_TBL_DAT_UNI_MED, strUniMedItm);
                                        vecReg.add(INT_TBL_DAT_NUM_CAN, dblCan);
                                        vecReg.add(INT_TBL_DAT_NUM_CAN_TOT_CON, dblCanCon);
                                        vecReg.add(INT_TBL_DAT_NUM_CAN_TOT_CAN, dblCanCan);
                                        double dblCanPen = dblCan - dblCanCon - dblCanCan;
                                        vecReg.add(INT_TBL_DAT_NUM_CAN_PEN, dblCanPen);
                                        vecReg.add(INT_TBL_DAT_NUM_CAN_CAN, 0);
                                        vecDat.add(vecReg);
                                        blnRes=true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
        return blnRes;
    }
    private boolean obtenerCodSegReg(int intCodEmpSolTraInv, int intCodLocSolTraInv, int intCodTipDocSolTraInv, int intCodDocSolTraInv) {
        boolean blnRes = true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();

            strSql  = "";
            strSql += " select (CASE WHEN MAX(co_seg) IS NULL THEN 0 ELSE MAX(co_seg) END ) as co_seg\n";
            strSql += " from tbm_cabsegmovinv \n";
            strSql += " where co_emprelcabsoltrainv=" + intCodEmpSolTraInv;
            strSql += "  and co_locrelcabsoltrainv=" + intCodLocSolTraInv;
            strSql += "  and co_tipdocrelcabsoltrainv=" + intCodTipDocSolTraInv;
            strSql += "  and co_docrelcabsoltrainv=" + intCodDocSolTraInv;

            rstLoc = stmLoc.executeQuery(strSql);
            if (rstLoc.next()) {
                intCodSeg = rstLoc.getInt("co_seg");
            } else {
                blnRes = false;
            }
            strSql = "";
            strSql += " select (CASE WHEN MAX(co_reg) IS NULL THEN 0 ELSE MAX(co_reg) END ) as co_reg    ";
            strSql += " from tbm_cabsegmovinv  \n";
            strSql += " where co_seg=" + intCodSeg;
            rstLoc = stmLoc.executeQuery(strSql);
            if (rstLoc.next()) {
                intCodReg = rstLoc.getInt("co_reg");
            }else{
                blnRes = false;
            }
            rstLoc.close();
            conLoc.close();
            stmLoc.close();
            rstLoc = null;
            conLoc = null;
            stmLoc = null;
        } catch (java.sql.SQLException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }//</editor-fold> 
    private boolean obtenerMovimientosInventario() {
        boolean blnRes = true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            strSql = " ";
            strSql += "  select co_emprelcabmovinv, co_locrelcabmovinv, co_tipdocrelcabmovinv, co_docrelcabmovinv\n";
            strSql += "  from tbm_cabsegmovinv\n";
            strSql += "  where co_seg =" + intCodSeg;
            strSql += "  and co_emprelcabmovinv is not null ";
            strSql += "  and CASE WHEN (select coalesce((select   co_reg      ";
            strSql += "  from tbm_cabsegmovinv as b1  ";
            strSql += "  where b1.co_seg=  "+ intCodSeg;
            strSql += "  and b1.co_tipdocrelcabingegrmerbod=277 order by co_reg asc limit 1), 0))=0 THEN  1=1 "; //Validacion para cuando se haga una cancelacion no tomar en cuenta esos registros.
            strSql += "  else co_reg<(select co_reg  ";
            strSql += "  from tbm_cabsegmovinv as b1  ";
            strSql += "  where b1.co_seg=  "+ intCodSeg;
            strSql += "  and b1.co_tipdocrelcabingegrmerbod=277 order by co_reg asc limit 1) end "; //Validacion para cuando se haga una cancelacion no tomar en cuenta esos registros.
            strSql += "  order by co_reg desc";
            rstLoc = stmLoc.executeQuery(strSql);
            arlDatMovInv = new ArrayList();
            while (rstLoc.next()) {
                arlRegMovInv = new ArrayList();
                arlRegMovInv.add(INT_ARL_COD_EMP_MOV, rstLoc.getInt("co_emprelcabmovinv"));
                arlRegMovInv.add(INT_ARL_COD_LOC_MOV, rstLoc.getInt("co_locrelcabmovinv"));
                arlRegMovInv.add(INT_ARL_COD_TIP_DOC_MOV, rstLoc.getInt("co_tipdocrelcabmovinv"));
                arlRegMovInv.add(INT_ARL_COD_DOC_MOV, rstLoc.getInt("co_docrelcabmovinv"));
                arlDatMovInv.add(arlRegMovInv);
                int intCodCfg = obtenerCodigoCfg(rstLoc.getInt("co_emprelcabmovinv"), rstLoc.getInt("co_locrelcabmovinv"), rstLoc.getInt("co_tipdocrelcabmovinv"));
                if (validaCfgTra(intCodCfg)) {
                    intTraInvCoEmp=rstLoc.getInt("co_emprelcabmovinv");
                    intTraInvCoLoc=rstLoc.getInt("co_locrelcabmovinv");
                    intTraInvCoTipDoc=rstLoc.getInt("co_tipdocrelcabmovinv");
                    intTraInvCoDoc=rstLoc.getInt("co_docrelcabmovinv");
                }
            }
            rstLoc.close();
            rstLoc = null;
            conLoc.close();
            stmLoc.close();
            conLoc = null;
            stmLoc = null;
        } catch (java.sql.SQLException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    private boolean obtenerItemsMovInv(int intCodEmpMov, int intCodLocMov, int intCodTipDocMov,int intCodDocMov) {
        boolean blnRes = true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            strSql = " ";
            strSql += " select distinct co_emp, co_loc, co_tipdoc, co_doc , co_itm,@nd_canpen as nd_can ";
            strSql += " from tbm_detmovinv ";
            strSql += " where co_emp="+intCodEmpMov;
            strSql += " and   co_loc="+intCodLocMov ;
            strSql += " and co_tipdoc="+ intCodTipDocMov;
            strSql += " and co_doc="+intCodDocMov;
            rstLoc = stmLoc.executeQuery(strSql);
            arlDatItemMovInv = new ArrayList();
            arlItm = new ArrayList();
            while (rstLoc.next()) {
                obtenerBodegasXEmpresas(intCodEmpMov, intCodLocMov, intCodTipDocMov, intCodDocMov);
                if (obtenerEmpresaItemBodega(intCodBodegaMovimientoOrg, rstLoc.getInt("co_itm"), intCodBodegaMovimientoDes,intCodEmpMov,intCodLocMov,intCodTipDocMov)) {
                arlRegItemMovInv = new ArrayList();
                arlRegItemMovInv.add(INT_ARL_COD_EMP, intCodEmpSolTra);
                arlRegItemMovInv.add(INT_ARL_COD_LOC, intCodLocSolTra);
                arlRegItemMovInv.add(INT_ARL_COD_TIP_DOC, intCodTipDocSolTra);
                arlRegItemMovInv.add(INT_ARL_COD_BOD_GRP, intCodBodegaMovimientoOrg);// BODEGA DE ORIGEN
                arlRegItemMovInv.add(INT_ARL_COD_ITM, intCodItmSolTra);
                arlRegItemMovInv.add(INT_ARL_COD_ITM_MAE, intCodItmMaeSolTra);
                arlRegItemMovInv.add(INT_ARL_COD_BOD, intCodBodegaMovimientoOrg);
                arlRegItemMovInv.add(INT_ARL_NOM_BOD, strNombreBodegaOrg);
                arlRegItemMovInv.add(INT_ARL_CAN_COM, rstLoc.getDouble("nd_can"));  // CANTIDAD NECESARIA!!!
                arlRegItemMovInv.add(INT_ARL_CHK_CLI_RET, false);  // Cliente Retira
                arlRegItemMovInv.add(INT_ARL_EST_BOD, 'A');  
                arlRegItemMovInv.add(INT_ARL_ING_EGR_FIS_BOD, "IE");
                arlRegItemMovInv.add(INT_ARL_COD_BOD_GRP_ING, intCodBodegaMovimientoDes); // BODEGA DE DESTINO //Verificar tony 
                arlDatItemMovInv.add(arlRegItemMovInv);
                }
            }

            rstLoc.close();
            conLoc.close();
            stmLoc.close();
            rstLoc = null;
            conLoc = null;
            stmLoc = null;
        } catch (java.sql.SQLException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    private boolean obtenerBodegasXEmpresas(int intCodEmpMov, int intCodLocMov, int intCodTipDocMov, int intCodDocMov) {
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        String strSqlBod="";
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
            strSqlBod = " ";
            strSqlBod += " select distinct co_reg, co_bod,tx_nombodorgdes ";
            strSqlBod += " from tbm_detmovinv ";
            strSqlBod += " where co_emp="+intCodEmpMov;
            strSqlBod += " and   co_loc="+intCodLocMov ;
            strSqlBod += " and co_tipdoc="+ intCodTipDocMov;
            strSqlBod += " and co_doc="+intCodDocMov;
            strSqlBod += " order by co_reg limit 2 ";
            rstLoc = stmLoc.executeQuery(strSqlBod);
            int intContador=0;
            while (rstLoc.next()) {
                if (rstLoc.getInt("co_reg")==1) {
                    intCodBodegaMovimientoDes=rstLoc.getInt("co_bod");
                    strNombreBodegaDes=rstLoc.getString("tx_nombodorgdes");
                    intContador++;
                }else{
                    intCodBodegaMovimientoOrg=rstLoc.getInt("co_bod");
                    strNombreBodegaOrg=rstLoc.getString("tx_nombodorgdes");
                    intContador++;
                }
            }
                if (intContador==1) {
                     intCodBodegaMovimientoOrg=intCodBodegaMovimientoDes;
                     strNombreBodegaOrg=strNombreBodegaDes;      
                }
            rstLoc.close();
            conLoc.close();
            stmLoc.close();
            rstLoc = null;
            conLoc = null;
            stmLoc = null;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(null, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(null, Evt);
        }
        return blnRes;
    }
    private boolean obtenerEmpresaItemBodega(int intCodBodDes, int intCodItm, int intCodBodOrg,int intCodEmpMov, int intCodLocMov, int intCodTipDocMov) {
        boolean blnRes = true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc=null;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            intCodEmpSolTra = intCodEmpMov;
            intCodLocSolTra = intCodLocMov;
            intCodTipDocSolTra = intCodTipDocMov;
            if (intCodEmpSolTra != 0) {
                strSql = " ";
                strSql += " SELECT a1.co_emp, a1.co_itm, a2.co_itmMae,a1.tx_codAlt2,a1.tx_codalt \n";
                strSql += " FROM tbm_inv as a1 \n";
                strSql += " INNER JOIN tbm_equInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)\n";
                strSql += " WHERE a2.co_itmMae = (\n";
                strSql += "                       SELECT a2.co_itmMae ";
                strSql += "                       FROM tbm_inv as a1  ";
                strSql += "                       INNER JOIN tbm_equInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                if (intCodEmpSolTra==4 || intCodEmpSolTra==2) {
                    strSql += "                       WHERE a1.co_emp=" + intCodEmpSolTra + " and a1.co_itm =" + intCodItm + " \n";
                }else{
                strSql += "                       WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + " and a1.co_itm =" + intCodItm + " \n";
                }
                strSql += " ) and a1.co_emp=" + intCodEmpSolTra + " \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    intCodItmSolTra = rstLoc.getInt("co_itm");
                    intCodItmMaeSolTra = rstLoc.getInt("co_itmMae");
                    arlItm.add(rstLoc.getString("tx_codalt"));
                } else {
                    blnRes = false;
                }
                strSql = "";
                strSql += " select co_bod from tbr_bodEmpBodGrp where co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " \n";
                strSql += " and co_bodGrp=" + intCodBodOrg + " and co_emp=" + intCodEmpSolTra + " \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    //intCodBodOrgSolTra = rstLoc.getInt("co_bod");
                    //intCodBodegaMovimientoOrg = rstLoc.getInt("co_bod");
                }
                
                strSql = "";
                strSql += " select a1.co_bod, a2.tx_nom  from tbr_bodEmpBodGrp  as a1 ";
                strSql += " INNER JOIN tbm_bod as a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod) where a1.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " \n";
                strSql += " and a1.co_bodGrp=" + intCodBodDes + " and a1.co_emp=" + intCodEmpSolTra + " \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    //intCodBodDesSolTra = rstLoc.getInt("co_bod");
                    //intCodBodegaMovimientoDes = rstLoc.getInt("co_bod");
                    //strNomBodSolTra = rstLoc.getString("tx_nom");
                    blnRes=true;
                }
            }
            rstLoc.close();
            rstLoc = null;
            conLoc.close();
            stmLoc.close();
            conLoc = null;
            stmLoc = null;
        } catch (java.sql.SQLException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    private boolean obtenerDatosItem(int codItmMae, int intCodEmpOrg) {
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        boolean blnRes = true;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                strSql = " SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, CASE WHEN a1.nd_cosUni IS NULL THEN 0 ELSE a1.nd_cosUni END as nd_cosUni, \n";
                strSql += "       a1.nd_preVta1, a1.st_ivaVen, CASE WHEN a1.tx_codAlt2 IS NULL THEN '' ELSE a1.tx_codAlt2 END as tx_codAlt2, a2.co_itmMae, \n";
                strSql += "       CASE WHEN a1.co_uni IS NULL THEN 0 ELSE a1.co_uni END as co_uni, ";
                strSql += "       CASE WHEN a1.nd_pesItmKgr IS NULL THEN 0 ELSE a1.nd_pesItmKgr END as nd_pesItmKgr , GRU.co_itm as co_itmGru, \n";
                strSql += "       a1.st_ivaCom, a1.st_ivaVen, a3.tx_desCor";
                strSql += " FROM tbm_inv as a1 \n";
                strSql += " INNER JOIN tbm_equInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSql += "  INNER JOIN tbm_equInv as GRU ON (a2.co_ItmMae=GRU.co_itmMae AND GRU.co_emp=" + objParSis.getCodigoEmpresaGrupo() + ") \n";
                strSql += " LEFT OUTER JOIN tbm_var as a3 ON (a1.co_uni=a3.co_reg) \n";
                strSql += " WHERE a1.co_emp=" + intCodEmpOrg + " and a1.st_reg='A' AND GRU.co_itmMae=" + codItmMae;
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    strCodAltItm = rstLoc.getString("tx_codALt");
                    strNomItm = rstLoc.getString("tx_nomItm");
                    dblCosUni = rstLoc.getDouble("nd_cosUni");
                    dblPreUni = rstLoc.getDouble("nd_preVta1");
                    strEstIva = rstLoc.getString("st_ivaVen");
                    strCodAltItm2 = rstLoc.getString("tx_codAlt2");
                    strUniMedItm = rstLoc.getString("tx_desCor");
                    intCodItmGru = rstLoc.getInt("co_itmGru");
                    dblPesItm = rstLoc.getDouble("nd_pesItmKgr");
                    strIvaCom = rstLoc.getString("st_ivaCom");
                    strIvaVen = rstLoc.getString("st_ivaVen");
                }

                /* Si un item se confirma */
                
                strSql = "";
                strSql += " SELECT * FROM tbm_cfgTerInvCon ";
                strSql += " WHERE tx_ter=" + objUti.codificar(String.valueOf(strCodAltItm.charAt(strCodAltItm.length() - 1))) + " AND co_emp=" + intCodEmpOrg;
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    // SI SE CONFIRMA
                    strMerIngEgrFisBod = "S";
                } else {
                    // NO SE CONFIRMA
                    strMerIngEgrFisBod = "N";
                }
                conLoc.close();
                conLoc = null;
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(null, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(null, Evt);
        }
        return blnRes;
    }
    private boolean consultaItemsMovInv(int x, int intCodEmpMovInvItm, int intCodLocMovInvItm, int intCodTipDocMovInvItm, int intCodDocMovInvItm ) {
        boolean blnRes = false;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            if (strEstTra.equals("E") || strEstTra.equals("P")) {
            strSql  = " ";
            strSql += " select distinct canTot.*,sum(canCon.nd_canCon) as nd_canCon,sum(canCan.nd_canCan) as nd_canCan\n" +
                      " from (select distinct a3.co_itm, a3.tx_codalt, a3.tx_codalt2, @a3.nd_can as nd_canTot \n" +
                      " from tbm_cabsegmovinv as a1\n" +
                      " inner join tbm_cabmovinv as a2 on (a1.co_emprelcabmovinv=a2.co_emp and a1.co_locrelcabmovinv=a2.co_loc and a1.co_tipdocrelcabmovinv=a2.co_tipdoc and a1.co_docrelcabmovinv=a2.co_doc)\n" +
                      " inner join tbm_detmovinv as a3 on (a2.co_emp = a3.co_emp and a2.co_loc=a3.co_loc and a2.co_tipdoc=a3.co_tipdoc and a2.co_doc=a3.co_doc)\n" +
                      " where a2.co_emp= "+intCodEmpMovInvItm+" and a2.co_loc= "+intCodLocMovInvItm+" and   a2.co_tipdoc= "+intCodTipDocMovInvItm+" and a2.co_doc= "+intCodDocMovInvItm+" and a1.co_seg= "+intCodSeg+") canTot\n" +
                      "	left join (select distinct a3.co_itm, a3.tx_codalt, a3.tx_codalt2, @a3.nd_can as nd_canTot , \n" +
                      " @a2.nd_can as nd_canCon, a2.co_doc \n" +
                      " from tbm_cabsegmovinv as a1\n" +
                      "	left join  tbm_detIngEgrMerBod as a2 on (a1.co_emprelcabingegrmerbod= a2.co_emp and a1.co_locrelcabingegrmerbod=a2.co_loc \n" +
                      "	and a1.co_tipdocrelcabingegrmerbod=a2.co_tipdoc and a1.co_docrelcabingegrmerbod=a2.co_doc \n" +
                      " and a2.co_tipdoc=274 ) " +
                      //" and CASE WHEN a2.co_tipdoc=275 THEN A2.co_tipdoc=275  \n" +
                      //"	when a2.co_tipdoc=274 THEN A2.co_tipdoc=274\n" +
                      //" else A2.co_tipdoc=275 end )\n" +
                      " inner join tbm_detmovinv as a3 on (a2.co_itm = a3.co_itm)\n" +
                      " inner join tbm_cabmovinv as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc)\n" +
                      " where a4.co_emp= "+intCodEmpMovInvItm+" and a4.co_loc= "+intCodLocMovInvItm+" and   a4.co_tipdoc= "+intCodTipDocMovInvItm+" and a4.co_doc= "+intCodDocMovInvItm+" and a1.co_seg= "+intCodSeg+" order by a3.co_itm) canCon on (canTot.co_itm=canCon.co_itm) \n" +
                      "	left join (select distinct a3.co_itm, a3.tx_codalt, a3.tx_codalt2, @a3.nd_can as nd_canTot , \n" +
                      "	@a2.nd_can as nd_canCan, a2.co_doc\n" +
                      " from tbm_cabsegmovinv as a1\n" +
                      " left join  tbm_detIngEgrMerBod as a2 on (a1.co_emprelcabingegrmerbod= a2.co_emp and a1.co_locrelcabingegrmerbod=a2.co_loc \n" +
                      " and a1.co_tipdocrelcabingegrmerbod=a2.co_tipdoc and a1.co_docrelcabingegrmerbod=a2.co_doc \n" +
                      " and a2.co_tipdoc=277 )" +
                      //" and CASE WHEN a2.co_tipdoc=277 THEN A2.co_tipdoc=277  \n" +
                      //" when a2.co_tipdoc=276 THEN A2.co_tipdoc=276\n" +
                      //" else A2.co_tipdoc=277 end )\n" +
                      " left join tbm_cabIngEgrMerBod as a10 on (a10.co_emp= a2.co_emp and a10.co_loc=a2.co_loc \n" +
                      " and a10.co_tipdoc=a2.co_tipdoc and a10.co_doc=a2.co_doc )"+
                      " inner join tbm_detmovinv as a3 on (a2.co_itm = a3.co_itm)\n" +
                      " inner join tbm_cabmovinv as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc)\n" +
                      " where a4.co_emp= "+intCodEmpMovInvItm+" and a4.co_loc= "+intCodLocMovInvItm+" and   a4.co_tipdoc= "+intCodTipDocMovInvItm+" and a4.co_doc= "+intCodDocMovInvItm+" and a1.co_seg= "+intCodSeg+" order by a3.co_itm) canCan on (canTot.co_itm=canCan.co_itm) "+
                      " group by  canTot.co_itm, canTot.tx_codalt, canTot.tx_codalt2, canTot.nd_cantot";
            }else{ // Solo para mostrar los items que esten completos.
            strSql = " ";
            strSql += "select distinct a2.co_itm,a2.tx_codalt,a2.tx_codalt2,@a2.nd_can as nd_canTot, @a2.nd_can as nd_canCon, 0 nd_canCan \n";
            strSql += "		from tbm_cabmovinv as a1\n";
            strSql += "		inner join tbm_detmovinv as a2 on (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc)\n";
            strSql += "		where a1.co_emp= " + intCodEmpMovInvItm; 
            strSql += "		and   a1.co_loc= " +  intCodLocMovInvItm;
            strSql += "		and   a1.co_tipdoc= " + intCodTipDocMovInvItm;
            strSql += "		and   a1.co_doc= "+ intCodDocMovInvItm;
            butAce.setVisible(false);
            }
                      
            rstLoc = stmLoc.executeQuery(strSql);
            while (rstLoc.next()) {
                if (rstLoc.getString("tx_codalt").equals(arlItm.get(x).toString())) {
                    dblCan = rstLoc.getDouble("nd_canTot");
                    dblCanCon = rstLoc.getDouble("nd_canCon");
                    dblCanCan = rstLoc.getDouble("nd_canCan");
                    blnRes=true;
                }
            }
            rstLoc.close();
            conLoc.close();
            stmLoc.close();
            rstLoc = null;
            conLoc = null;
            stmLoc = null;
        } catch (java.sql.SQLException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    private int obtenerCodigoCfg(int intCodEmp, int intCodLoc, int intCodTipDoc) {
        int intCodCfg = 0;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSQL="";
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            strSQL = " ";
            strSQL += "  select distinct co_cfg\n";
            strSQL += "  from tbm_cfgtipdocutiproaut \n";
            strSQL += "  where co_emp=" + intCodEmp;
            //strSQL += "  and co_loc=" + intCodLoc;    Esto cambie porque se cambio la configuracion por local y no me permitia obtener el codigo de cfg. tony
            strSQL += "  and co_tipdoc=" + intCodTipDoc;
            strSQL += "  and st_reg='A'";
            rstLoc = stmLoc.executeQuery(strSQL);
            if (rstLoc.next()) {
                intCodCfg = rstLoc.getInt("co_cfg");
            } else {
                intCodCfg = 0;
            }
            rstLoc.close();
            rstLoc = null;
            conLoc.close();
            stmLoc.close();
            conLoc = null;
            stmLoc = null;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intCodCfg;
    }
    private boolean validaCfgTra(int intCfg){
        if (intCfg == 7) { //TRAINV : Tranferencia de inventario.
            return true;
        }else if (intCfg == 16) { //TRINRB : Transferencias de inventario (Reposiciones)
            return true;
        }else if (intCfg == 15) { //TRINAU : Transferencias de inventario (Ventas)
            return true;
        }
        return false;
    }
    private void tblAgrCanPenMouseClicked(java.awt.event.MouseEvent evt) {
        int i, intNumFil;
        try {
            intNumFil = objTblMod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1 && tblDat.columnAtPoint(evt.getPoint()) == INT_TBL_DAT_NUM_CAN_CAN) {
                if (blnAgrPenChkTblSel) {
                    //Mostrar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        objTblMod.setValueAt(Double.valueOf(objTblMod.getValueAt(i, INT_TBL_DAT_NUM_CAN_PEN).toString()), i, INT_TBL_DAT_NUM_CAN_CAN);
                        //objTblMod.setChecked(true, i, INT_TBL_DAT_CHK_SEL);
                    }
                    blnAgrPenChkTblSel = false;
                } else {
                    //Ocultar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        objTblMod.setValueAt(0, i, INT_TBL_DAT_NUM_CAN_CAN);
                        //objTblMod.setChecked(false, i, INT_TBL_DAT_CHK_SEL);
                    }
                    blnAgrPenChkTblSel = true;
                }
            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    private boolean validaIngCanPen(Boolean booValida){
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        if (booValida) {
            strMsg = "¿Está seguro que desea asignar la cantidad pendiente a todos los items? Se sobreescribiran las cantidades ya ingresadas.";
        }else{
            strMsg = "¿Está seguro que desea borrar la cantidad pendiente a todos los items? Se sobreescribiran las cantidades ya ingresadas.";
        }
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }
    //TODO PARA GENERAR LA CANCELACION
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
    
    private void generaCancelacion() {
        try {
                if (guardarCancelacion()) {
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    cargarItmCan();
                }else{
                    lblMsgSis.setText("Hubo un error...");
                    pgrSis.setValue(0);
                }
                pgrSis.setValue(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
    private boolean guardarCancelacion() throws SQLException {
        boolean blnRes = false;
        try {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            lblMsgSis.setText("Guardando datos...");
            pgrSis.setIndeterminate(true);
            if (cancelacion()) {
                con.commit();
                blnRes = true;
                mostrarMsgInf("SE HA GUARDADO CON EXITO.");
                pgrSis.setIndeterminate(false);
            } else {
                mostrarMsgInf("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema. TS");
                con.rollback();
                pgrSis.setIndeterminate(false);
            }
            if (con!=null) {
                con.close();    
                con=null;
            }
            pgrSis.setValue(0);
        } catch (java.sql.SQLException e) {
            if (con!=null) {
            con.rollback();
            con.close();
            con = null;
            }
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception Evt) {
            if (con!=null) {
            con.rollback();
            con.close(); 
            con = null;
            }
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }
    private boolean cancelacion() throws SQLException {
        int intNumFil;
        boolean blnRes = false;
        int intTraCodEmp=0,intTraCodLoc=0,intTraCodTipDoc=0, intTraCodDoc=0;
        try {
            if (con != null) {
                if (datFecAux == null) {
                    return false;
                }
                intNumFil = objTblMod.getRowCountTrue();
                ZafMovIngEgrInv objMovInvEgrInv = new ZafMovIngEgrInv(objParSis, componente);
                //if (obtenerCodSegReg(intCodEmpSol1, intCodLocSol1, intCodTipDocSol1, intCodDocSol1)) {
                if (obtenerMovimientosInventario()) {
                    pgrSis.setValue(10);
                    //arlConDatPreEmpEgr = new ArrayList();
                    //arlConDatPreEmpIng = new ArrayList();
                    arlDatHistoricoMovInvEgr = new ArrayList();
                    for (int j = 0; j < arlDatMovInv.size(); j++) {
                        char chrGeneraIva;
                        String strTipConIngEgr = "R";
                        String strTipProReaEmpRel = ""; // CONFIGURACION DE LA EMPRESA PARA SI SE HACE PRESTAMOS, COMPRA/VENTA, DEVOLUCIONES
                        Object estGenOrdDes = null;// PENDIENTE SI LA CANCCELACION GENERA ORDEN DE DESPACHO
                        int intCodEmpMov = Integer.parseInt(objUti.getStringValueAt(arlDatMovInv, j, INT_ARL_COD_EMP_MOV));
                        int intCodLocMov = Integer.parseInt(objUti.getStringValueAt(arlDatMovInv, j, INT_ARL_COD_LOC_MOV));
                        int intCodTipDocMov = Integer.parseInt(objUti.getStringValueAt(arlDatMovInv, j, INT_ARL_COD_TIP_DOC_MOV));
                        int intCodDocMov = Integer.parseInt(objUti.getStringValueAt(arlDatMovInv, j, INT_ARL_COD_DOC_MOV));
                        int intCodCfg = obtenerCodigoCfg(intCodEmpMov, intCodLocMov, intCodTipDocMov);
                        int intCodTipDocInverso = obtenerTipoDocumentoInverso(intCodEmpMov, intCodLocMov, intCodCfg);
                        if (obtenerItemsMovInv(intCodEmpMov, intCodLocMov, intCodTipDocMov, intCodDocMov)) {
                            if (!validaCfgTra(intCodCfg)) { //Si es diferente de una transferencia de inventario
                                if (intCodCfg == 3 || intCodCfg == 4) {
                                    chrGeneraIva = 'S';
                                    strTipProReaEmpRel = "C";
                                    strTipConIngEgr = "V";
                                } else if (intCodCfg == 1 || intCodCfg == 2) {
                                    chrGeneraIva = 'N';
                                    strTipProReaEmpRel = "P";
                                } else {
                                    chrGeneraIva = 'S';
                                    strTipProReaEmpRel = "C";
                                    strTipConIngEgr = "R";
                                }
                                if (intCodCfg == 5 || intCodCfg == 6) {
                                    strTipProReaEmpRel = "D";
                                    strTipConIngEgr = "V";
                                }

                                int intCodEmpMovSig = Integer.parseInt(objUti.getStringValueAt(arlDatMovInv, j + 1, INT_ARL_COD_EMP_MOV));
                                int intCodLocMovSig = Integer.parseInt(objUti.getStringValueAt(arlDatMovInv, j + 1, INT_ARL_COD_LOC_MOV));
                                int intCodTipDocMovSig = Integer.parseInt(objUti.getStringValueAt(arlDatMovInv, j + 1, INT_ARL_COD_TIP_DOC_MOV));
                                int intCodDocMovSig = Integer.parseInt(objUti.getStringValueAt(arlDatMovInv, j + 1, INT_ARL_COD_DOC_MOV));
                                int intCodCfgSig = obtenerCodigoCfg(intCodEmpMovSig, intCodLocMovSig, intCodTipDocMovSig);
                                //int intCodTipDocInversoSig = obtenerTipoDocumentoInverso(intCodEmpMov, intCodLocMov, intCodCfg);
                                obtenerItemsMovInv(intCodEmpMovSig, intCodLocMovSig, intCodTipDocMovSig, intCodDocMovSig);

                                int intEmpOrg = 0;
                                int intEmpDes = 0;

                                if (intCodCfg == 1 || intCodCfg == 3 || intCodCfg == 6) {//Egresos
                                    intEmpDes = intCodEmpMov;
                                    intEmpOrg = intCodEmpMovSig;
                                } else if (intCodCfg == 2 || intCodCfg == 4 || intCodCfg == 5) {//Ingresos
                                    intEmpOrg = intCodEmpMov;
                                    intEmpDes = intCodEmpMovSig;
                                }

//                                if (intCodCfgSig == 1 || intCodCfgSig == 3 || intCodCfgSig == 6) {//Egresos Documento Siguiente
//                                    intEmpOrg = intCodEmpMovSig;
//                                } else if (intCodCfgSig == 2 || intCodCfgSig == 4 || intCodCfgSig == 5) {//Ingresos Documento Siguiente
//                                    intEmpDes = intCodEmpMovSig;
//                                }

                                Boolean blnVerificaItems = false;
                                arlConDatPreEmpEgr = new ArrayList();
                                arlConDatPreEmpIng = new ArrayList();
                                arlConDatPreInvEgrAux = new ArrayList();
                                arlConDatPreInvIngAux = new ArrayList();
                                for (int x = 0; x < arlDatItemMovInv.size(); x++) {
                                    for (int z = 0; z < intNumFil; z++) {
                                        obtenerDatosItemConf(objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM_MAE), objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_EMP));
                                        if (strCodAltItmConf.equals(objTblMod.getValueAt(z, INT_TBL_DAT_COD_ALT).toString())) {
                                            if (Double.parseDouble(objTblMod.getValueAt(z, INT_TBL_DAT_NUM_CAN_CAN).toString()) > 0) {
                                                if (obtenerTipoDocumentos(2, intEmpOrg, intEmpDes, strTipProReaEmpRel, objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_TIP_DOC))) { //Recibe el tipo de movimiento 2 para ingreso o egreso y 1 para tranferencia. //codigo de empresa origen y codigo de empresa destino
                                                    if (obtenerDatosVenta(objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM_MAE))) {
                                                        obtenerDatosCompra(objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM_MAE));
                                                        if (obtenerDatosItem(objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM_MAE), intEmpDes)) {
                                                            dblCanCan = Double.parseDouble(objTblMod.getValueAt(z, INT_TBL_DAT_NUM_CAN_CAN).toString());
                                                            if (consultaItemsMovInvEgrIngConf(x, intCodEmpMov, intCodLocMov, intCodTipDocMov, intCodDocMov)) {
                                                                if (dblCanCan > 0.00) {
                                                                    if (obtenerEgresoIngresoConf(x)) {
                                                                        blnVerificaItems = true;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                break;// Si ya encontro el item pero no ha seleccionado ninguna cantidad se va por el otro item
                                            }
                                        }
                                    }
                                }
                                if (blnVerificaItems) {
                                    llamaObjetoCfgBod(con, objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_CODBODORIDES), objUti.getIntValueAt(arlDatosItemIngreso, 0, INT_ARL_CODBODORIDES));
                                    objMovInvEgrInv.getDatoEgresoIngreso(con,
                                            datFecAux,
                                            chrGeneraIva,
                                            arlDatosItemEgreso,
                                            arlDatosItemIngreso,
                                            intCodEmpSol1,
                                            intCodLocSol1,
                                            intCodTipDocSol1,
                                            intCodDocSol1,
                                            strTipConIngEgr,
                                            estGenOrdDes,
                                            objCfgBod);
                                    generaMovimientoInventarioEgrIng(con);
                                    blnRes = true;
                                }
                                j++;
                            } else if (validaCfgTra(intCodCfg)) {
                                pgrSis.setValue(40);
                                arlConDatTraInv = new ArrayList();
                                arlConDatTraInvStkIng = new ArrayList();
                                arlConDatTraInvStkEgr = new ArrayList();
                                Boolean blnVer = false;
                                for (int x = 0; x < arlDatItemMovInv.size(); x++) {
                                    for (int z = 0; z < intNumFil; z++) {
                                        if (objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM) == Integer.valueOf(objTblMod.getValueAt(z, INT_TBL_DAT_COD_ITM).toString())) {
                                            if (Double.parseDouble(objTblMod.getValueAt(z, INT_TBL_DAT_NUM_CAN_CAN).toString()) > 0) {
                                                obtenerTipoDocumentos(1, intCodEmpMov, intCodEmpMov, strTipProReaEmpRel, objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_TIP_DOC)); //Recibe el tipo de movimiento 2 para ingreso o egreso y 1 para tranferencia. //codigo de empresa origen y codigo de empresa destino
                                                if (obtenerDatosVenta(objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM_MAE))) {
                                                    if (obtenerDatosItem(objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM_MAE), objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_CAN_COD_EMP))) {
                                                        //if (consultaItemsMovInvConf(x, intCodEmpMov, intCodLocMov, intCodTipDocMov, intCodDocMov)) {
                                                        dblCanCan = dblCanCan = Double.parseDouble(objTblMod.getValueAt(z, INT_TBL_DAT_NUM_CAN_CAN).toString());
                                                        if (dblCanCan > 0.00) {
                                                            obtenerCosPreUniTra(objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM), intCodEmpMov, intCodLocMov, intCodTipDocMov, intCodDocMov);
                                                            if (obtenerTransferenciaConf(x, intCodEmpMov, intCodEmpMov, strTipProReaEmpRel)) {
                                                                blnVer = true;
                                                                blnRes = true;
                                                            } else {
                                                                blnVer = false;
                                                                blnRes = false;
                                                                break;
                                                            }
                                                        }
                                                    } else {
                                                        blnVer = false;
                                                        blnRes = false;
                                                        break;
                                                    }
                                                }
                                            } else {
                                                break;// Si ya encontro el item pero no ha seleccionado ninguna cantidad se va por el otro item
                                            }
                                        }
                                    }
                                }
                                chrGeneraIva = 'N';   /* TRANSFERENCIA NO GENERA IVA */

                                if (blnVer) {
                                    insertarRegDocConCanConf(intCodDocMov, intCodEmpMov, intCodLocMov, intCodTipDocMov, intCodDocMov, con);
                                    getDatoTransferencia(con,
                                            intCodEmpMov,
                                            intCodLocMov,
                                            intCodTipDocInverso,
                                            datFecAux,
                                            intCodBodegaMovimientoOrg,
                                            intCodBodegaMovimientoDes,
                                            chrGeneraIva,//
                                            arlConDatTraInv, //Item
                                            intCodEmpSol1,
                                            intCodLocSol1,
                                            intCodTipDocSol1,
                                            intCodDocSol1,
                                            strTipConIngEgr, //Tipo MovSeg
                                            null);//Object estGenOrdeDes
                                    generaMovimientoInventarioTransferencia(con, intCodEmpMov);
                                    actualizarEgrIngMovInvConf(intCodEmpMov, intCodLocMov, intCodTipDocMov, intCodDocMov);
                                    actualizarDesEntCliMovInvConf(intCodEmpMov, intCodLocMov, intCodTipDocMov, intCodDocMov);
                                    actualizarSolicitud();
                                    //actualizar tbm_detmovinv de la orddes actualizarEgrIngMovInvConf() de la orrden
                                    //Verificar si se cancelaron todos los items para poner la orden de despacho como parcial o pendiente.
                                    
                                    blnRes = true;
                                } else {
                                    mostrarMsgInf("Se ha generado un error");
                                    blnRes = false;
                                    break;
                                }
                                pgrSis.setValue(60);
                            }
                        }
                    }
                }
                //}
                pgrSis.setValue(80);
                if (!blnRes) {
                    con.rollback();
                }else{
                    con.commit();
                }
                
                if (costearDocumentos()) {
                    blnRes=true;
                }else{
                    blnRes=false;
                }
                pgrSis.setValue(100);
                //Inicializo el estado de las filas.
                objTblMod.initRowsState();
                objTblMod.setDataModelChanged(false);
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            con.rollback();
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (NumberFormatException e) {
            blnRes = false;
            con.rollback();
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    private boolean costearDocumentos(){
        boolean blnRes = true;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            strSql = " ";
            strSql += "  select co_emprelcabmovinv, co_locrelcabmovinv, co_tipdocrelcabmovinv, co_docrelcabmovinv\n";
            strSql += "  from tbm_cabsegmovinv\n";
            strSql += "  where co_seg =" + intCodSeg;
            strSql += "  and co_emprelcabmovinv is not null ";
            rstLoc = stmLoc.executeQuery(strSql);
            while (rstLoc.next()) {
                if (objUti.costearDocumento(this, objParSis, con,rstLoc.getInt("co_emprelcabmovinv"), rstLoc.getInt("co_locrelcabmovinv"), rstLoc.getInt("co_tipdocrelcabmovinv"), rstLoc.getInt("co_docrelcabmovinv"))) {
                   blnRes=true;
                }else{
                    blnRes=false;
                    break;
                }
            }
            if (blnRes) {
                con.commit();
            }else{
                con.rollback();
            }
            rstLoc.close();
            rstLoc = null;
            conLoc.close();
            stmLoc.close();
            conLoc = null;
            stmLoc = null;
        } catch (java.sql.SQLException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    /**
     * Verificar si la solicitud se ha realizado modificaciones en los items.
     *
     * @return
     * @throws SQLException
     */
    private boolean isSolicitudCambia() throws SQLException {
        Boolean blnRes = false;
        vecDatCon= new Vector();
                for (int j = 0; j < arlDatMovInv.size(); j++) {
                    int intCodEmpMov = Integer.parseInt(objUti.getStringValueAt(arlDatMovInv, j, INT_ARL_COD_EMP_MOV));
                    int intCodLocMov = Integer.parseInt(objUti.getStringValueAt(arlDatMovInv, j, INT_ARL_COD_LOC_MOV));
                    int intCodTipDocMov = Integer.parseInt(objUti.getStringValueAt(arlDatMovInv, j, INT_ARL_COD_TIP_DOC_MOV));
                    int intCodDocMov = Integer.parseInt(objUti.getStringValueAt(arlDatMovInv, j, INT_ARL_COD_DOC_MOV));
                    int intCodCfg = obtenerCodigoCfg(intCodEmpMov, intCodLocMov, intCodTipDocMov);
                    if (validaCfgTra(intCodCfg)) {
                        if (obtenerItemsMovInv(intCodEmpMov, intCodLocMov, intCodTipDocMov, intCodDocMov)) {
                            for (int x = 0; x < arlDatItemMovInv.size(); x++) {
                                if (obtenerDatosItem(objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM_MAE), objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_EMP))) {//Porque es el egreso de la tranferencia
                                    if (consultaItemsMovInv(x, intCodEmpMov, intCodLocMov, intCodTipDocMov, intCodDocMov)) {
                                        vecRegCon = new Vector();
                                        vecRegCon.add(INT_TBL_DAT_LIN, "");
                                        vecRegCon.add(INT_TBL_DAT_COD_ITM, objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM));
                                        vecRegCon.add(INT_TBL_DAT_COD_ALT, strCodAltItm);
                                        vecRegCon.add(INT_TBL_DAT_COD_LET, strCodAltItm2);
                                        vecRegCon.add(INT_TBL_DAT_NOM_ITM, strNomItm);
                                        vecRegCon.add(INT_TBL_DAT_UNI_MED, strUniMedItm);
                                        vecRegCon.add(INT_TBL_DAT_NUM_CAN, dblCan);
                                        vecRegCon.add(INT_TBL_DAT_NUM_CAN_TOT_CON, dblCanCon);
                                        vecRegCon.add(INT_TBL_DAT_NUM_CAN_TOT_CAN, dblCanCan);
                                        double dblCanPen = dblCan - dblCanCon - dblCanCan;
                                        vecRegCon.add(INT_TBL_DAT_NUM_CAN_PEN, dblCanPen);
                                        vecRegCon.add(INT_TBL_DAT_NUM_CAN_CAN, 0);
                                        vecDatCon.add(vecRegCon);
                                    }
                                }
                            }
                        }
                    }
                }
                blnRes= verificaItms();
        return blnRes;
    }
     private int obtenerTipoDocumentoInverso(int intCodEmp, int intCodLoc, int intCodCfg) {
        int intCodTipDocInv = 0;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            switch (intCodCfg) {
                case 1: //Si el documento es un Egreso de Bodega 1=EGBOPR
                    intCodCfg = 2; // Asignar el inverso que es un ingreso 2=INBOPR
                    //intCodCfgConCan = 11;//Se asigna el codigo de confirmacion 11 que es la confirmacion de cancelacion de egreso 11=COEGCA
                    break;
                case 2: //Si el documento es un Ingreso de Bodega 2=INBOPR
                    intCodCfg = 1; // Asignar el inverso que es un ingreso 1=EGBOPR
                    //intCodCfgConCan = 10;//Se asigna el codigo de confirmacion 10 que es la confirmacion de cancelacion de ingreso 10=COINCA
                    break;
                case 3://Si el documento es una Factura de ventas 3=FACVENE
                    intCodCfg = 4; // Asignar el inverso que es una Orden De Compra 4=FACCOM
                    break;
                case 4: //Si el documento es una Orden De Compra 4=FACCOM
                    intCodCfg = 3;//Asignar el inverso que es una Factura de ventas 3=FACVENE
                    break;
                case 5: //Si el documento es una Devolucion de ventas 5=DEVVENE
                    intCodCfg = 6; //Asignar el inverso que es una Devolucion de Compras 6=DEVCOM
                    break;
                case 6: //Si el documento es una Devolucion de Compras 6=DEVCOM
                    intCodCfg = 5; //Asignar el inverso que es una Devolucion de ventas 5=DEVVENE
                    break;
                case 7: //Si el documento es una Tranferencia de Inventario 7=TRAINV
                    intCodCfg = 7; //Asignar el inverso que es una Tranferencia de Inventario 7=TRAINV
                    break;
                case 16: //Si el documento es una Tranferencia de Inventario 16=TRINBR
                    intCodCfg = 16; //Asignar el inverso que es una Tranferencia de Inventario 16=TRINBR
                    break;
                default:
                    intCodCfg = 0;
                    break;
            }

            if (intCodCfg != 0) {
                strSql  = " ";
                strSql += "  select distinct co_tipdoc\n";
                strSql += "  from tbm_cfgtipdocutiproaut \n";
                strSql += "  where co_emp=" + intCodEmp;
                //strSQL += "  and co_loc=" + intCodLoc;    Esto cambie porque se cambio la configuracion por local y no me permitia obtener el codigo de cfg. tony
                strSql += "  and co_cfg=" + intCodCfg;
                strSql += "  and st_reg='A'";
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    intCodTipDocInv = rstLoc.getInt("co_tipdoc");
                } else {
                    intCodTipDocInv = 0;
                }
                rstLoc.close();
                rstLoc = null;
                conLoc.close();
                stmLoc.close();
                conLoc = null;
                stmLoc = null;
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intCodTipDocInv;
    }
     /**
     * <h1>obtenerTipoDocumentos:</h1>
     * Obtenemos la configuración que se encuentra en
     * tbm_emp.tx_tipProReaEmpRel, y con eso obtenemos la configuracion de la
     * empresa segun eso sabemos que tipo de proceso realiza la empresa despues
     * en la tabla de documentos usamos los del proceso correspondiente
     * (tbm_cfgTipDocUtiProAut) null=No aplica
     * <br> P=Préstamos </br>
     * C=Compras/Ventas D=Devoluciones de compras/ventas
     *
     * @param rstExt
     * @return
     */
    private boolean obtenerTipoDocumentos(int intTipMov, int intCodEmpOrg, int intCodEmpDes, String strTipProReaEmpRel, int intTipDoc) {
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        int intCodCfgIng = 0, intCodCfgEgr = 0, intCodCfgDevVen = 0, intCodCfgDevCom = 0, intCodCfgTra = 0;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                if (intTipMov == 2) {  // CUANDO NO ES TRANSFERENCIA 
                    strSql = "";
                    strSql += " SELECT co_empOrg, co_cliEmpOrg \n";
                    strSql += " FROM tbm_cfgEmpRel \n";
                    strSql += " WHERE co_empOrg=" + intCodEmpOrg + " AND co_empDes=" + intCodEmpDes + " \n";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        intCodCliGenVen = rstLoc.getInt("co_cliEmpOrg");
                    }
                    strSql = "";
                    strSql += " SELECT co_empOrg, co_cliEmpOrg \n";
                    strSql += " FROM tbm_cfgEmpRel \n";
                    strSql += " WHERE co_empOrg=" + intCodEmpDes + " AND co_empDes=" + intCodEmpOrg + " \n";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        intCodCliGenCom = rstLoc.getInt("co_cliEmpOrg");
                    }
                    rstLoc.close();
                    rstLoc = null;
                }
                /*
                 objParSis EMPRESA INGRESO rstExt EMPRESA EGRESO
                 */
                //VENTA = EGRESO = DEVOLUCION EN COMPRA ;
                switch (intCodEmpOrg) {
                    case 1: // TUVAL 
                        if (strTipProReaEmpRel.equals("P")) {
                            intCodCfgEgr = 1; // 1;Préstamos (Egreso);5;206
                        } else if (strTipProReaEmpRel.equals("C")) {

                            intCodCfgEgr = 3; // 3;Facturas;5;228
                        } else if (strTipProReaEmpRel.equals("D")) {
                            intCodCfgEgr = 3; // 3;Facturas;5;228
                            intCodCfgDevCom = 6; //6;Devoluciones de compras;5;4
                        }
//                        intCodCfgTra=7;//7;Transferencias de inventario;1;153
                        break;
                    case 2: // CASTEK
                        if (strTipProReaEmpRel.equals("P")) {
                            intCodCfgEgr = 1; // 1;Préstamos (Egreso);5;206
                        } else if (strTipProReaEmpRel.equals("C")) {
                            intCodCfgEgr = 3; // 3;Facturas;5;228
                        } else if (strTipProReaEmpRel.equals("D")) {
                            intCodCfgEgr = 3; // 3;Facturas;5;228
                            intCodCfgDevCom = 6; //6;Devoluciones de compras;5;4
                        }
//                        intCodCfgTra=7; // 7;Transferencias de inventario;1;153
                        break;
                    case 4: // DIMULTI
                        if (strTipProReaEmpRel.equals("P")) {
                            intCodCfgEgr = 1; // 1;Préstamos (Egreso);2;206
                        } else if (strTipProReaEmpRel.equals("C")) {
                            intCodCfgEgr = 3; // 3;Facturas;5;228
                        } else if (strTipProReaEmpRel.equals("D")) {
                            intCodCfgEgr = 3; // 3;Facturas;5;228
                            intCodCfgDevCom = 6; //6;Devoluciones de compras;5;4
                        }
//                        intCodCfgTra=7; // 7;Transferencias de inventario;3;153
                        break;
                }
                // INGRESO = ORDEN DE COMPRA = DEVOLUCION EN VENTA 
                switch (intCodEmpDes) {
                    case 1: // TUVAL 
                        if (strTipProReaEmpRel.equals("P")) {
                            intCodCfgIng = 2; // 2;Préstamos (Ingreso);5;205
                        } else if (strTipProReaEmpRel.equals("C")) {
                            intCodCfgIng = 4; // 4;Ordenes de compra;5;2
                        } else if (strTipProReaEmpRel.equals("D")) {
                            intCodCfgIng = 4; // 4;Ordenes de compra;5;2
                            intCodCfgDevVen = 5; //5;Devoluciones de ventas;5;229
                        }
                        intCodCfgTra = obtenerCodigoCfg(intCodEmpDes, 5, intTipDoc);//7;Transferencias de inventario;1;153
                        break;
                    case 2: // CASTEK
                        if (strTipProReaEmpRel.equals("P")) {
                            intCodCfgIng = 2; // 2;Préstamos (Ingreso);5;205
                        } else if (strTipProReaEmpRel.equals("C")) {
                            intCodCfgIng = 4; // 4;Ordenes de compra;5;2
                        } else if (strTipProReaEmpRel.equals("D")) {
                            intCodCfgIng = 4; // 4;Ordenes de compra;5;2
                            intCodCfgDevVen = 5; //5;Devoluciones de ventas;5;229
                        }
                       intCodCfgTra = obtenerCodigoCfg(intCodEmpDes, 5, intTipDoc); // 7;Transferencias de inventario;1;153
                        break;
                    case 4: // DIMULTI
                        if (strTipProReaEmpRel.equals("P")) {
                            intCodCfgIng = 2; // 2;Préstamos (Ingreso);5;205
                        } else if (strTipProReaEmpRel.equals("C")) {
                            intCodCfgIng = 4; // 4;Ordenes de compra;5;2
                        } else if (strTipProReaEmpRel.equals("D")) {
                            intCodCfgIng = 4; // 4;Ordenes de compra;5;2
                            intCodCfgDevVen = 5; //5;Devoluciones de ventas;5;229
                        }
                        intCodCfgTra = obtenerCodigoCfg(intCodEmpDes, 2, intTipDoc);// 7;Transferencias de inventario;3;153
                        break;
                }

                if (strTipProReaEmpRel.equals("P") || strTipProReaEmpRel.equals("C")) {
                    strSql = "";
                    strSql += " SELECT co_emp,co_cfg, tx_nom, co_loc, co_tipDoc, tx_cfgLocUti, tx_obs1, st_reg ";
                    strSql += " FROM tbm_cfgTipDocUtiProAut ";
                    strSql += " WHERE co_emp=" + intCodEmpOrg + " AND co_cfg=" + intCodCfgEgr + " AND st_reg='A'";
                    System.out.println("Local Venta: " + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) { /* LA VENTA ES EL EGRESO DE LA MERCADERIA */

                        intCodEmpGenVen = rstLoc.getInt("co_emp");
                        intCodLocGenVen = rstLoc.getInt("co_loc");
                        intCodTipDocGenVen = rstLoc.getInt("co_tipDoc");
                    }
                    strSql = "";
                    strSql += " SELECT co_emp,co_cfg, tx_nom, co_loc, co_tipDoc, tx_cfgLocUti, tx_obs1, st_reg ";
                    strSql += " FROM tbm_cfgTipDocUtiProAut ";
                    strSql += " WHERE co_emp=" + intCodEmpDes + " AND co_cfg=" + intCodCfgIng + " AND st_reg='A'";
                    System.out.println("Local Compra: " + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) { /* LA COMPRA ES EL INGRESO DE LA MERCADERIA */

                        intCodEmpGenCom = rstLoc.getInt("co_emp");
                        intCodLocGenCom = rstLoc.getInt("co_loc");
                        intCodTipDocGenCom = rstLoc.getInt("co_tipDoc");
                    }
                    strSql = "";
                    strSql += " SELECT co_emp,co_cfg, tx_nom, co_loc, co_tipDoc, tx_cfgLocUti, tx_obs1, st_reg ";
                    strSql += " FROM tbm_cfgTipDocUtiProAut ";
                    strSql += " WHERE co_emp=" + intCodEmpDes + " AND co_cfg=" + intCodCfgTra + " AND st_reg='A'";
                    System.out.println("Local transferencias: " + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        intCodLocTra = rstLoc.getInt("co_loc");/* TRANSFERENCIA  */

                        intCodTipDocGenTra = rstLoc.getInt("co_tipDoc");
                    }
                    rstLoc.close();
                    rstLoc = null;
                } else if (strTipProReaEmpRel.equals("D")) {
                    strSql = "";
                    strSql += " SELECT co_emp,co_cfg, tx_nom, co_loc, co_tipDoc, tx_cfgLocUti, tx_obs1, st_reg ";
                    strSql += " FROM tbm_cfgTipDocUtiProAut ";
                    strSql += " WHERE co_emp=" + intCodEmpDes + " AND co_cfg=" + intCodCfgDevVen + " AND st_reg='A'";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) { /* LA VENTA ES EL EGRESO DE LA MERCADERIA */

                        intCodEmpGenDevVen = rstLoc.getInt("co_emp");
                        intCodLocGenDevVen = rstLoc.getInt("co_loc");
                        intCodTipDocGenDevVen = rstLoc.getInt("co_tipDoc");
                    }
                    strSql = "";
                    strSql += " SELECT co_emp,co_cfg, tx_nom, co_loc, co_tipDoc, tx_cfgLocUti, tx_obs1, st_reg ";
                    strSql += " FROM tbm_cfgTipDocUtiProAut ";
                    strSql += " WHERE co_emp=" + intCodEmpOrg + " AND co_cfg=" + intCodCfgDevCom + " AND st_reg='A'";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) { /* LA COMPRA ES EL INGRESO DE LA MERCADERIA */

                        intCodEmpGenDevCom = rstLoc.getInt("co_emp");
                        intCodLocGenDevCom = rstLoc.getInt("co_loc");
                        intCodTipDocGenDevCom = rstLoc.getInt("co_tipDoc");
                    }
                    strSql = "";
                    strSql += " SELECT co_emp,co_cfg, tx_nom, co_loc, co_tipDoc, tx_cfgLocUti, tx_obs1, st_reg ";
                    strSql += " FROM tbm_cfgTipDocUtiProAut ";
                    strSql += " WHERE co_emp=" + intCodEmpDes + " AND co_cfg=" + intCodCfgTra + " AND st_reg='A'";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        intCodLocTra = rstLoc.getInt("co_loc");
                        intCodTipDocGenTra = rstLoc.getInt("co_tipDoc");
                    }
                    rstLoc.close();
                    rstLoc = null;
                }else {
                    strSql = "";
                    strSql += " SELECT co_emp,co_cfg, tx_nom, co_loc, co_tipDoc, tx_cfgLocUti, tx_obs1, st_reg ";
                    strSql += " FROM tbm_cfgTipDocUtiProAut ";
                    strSql += " WHERE co_emp=" + intCodEmpDes + " AND co_cfg=" + intCodCfgDevVen + " AND st_reg='A'";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) { /* LA VENTA ES EL EGRESO DE LA MERCADERIA */

                        intCodEmpGenDevVen = rstLoc.getInt("co_emp");
                        intCodLocGenDevVen = rstLoc.getInt("co_loc");
                        intCodTipDocGenDevVen = rstLoc.getInt("co_tipDoc");
                    }
                    strSql = "";
                    strSql += " SELECT co_emp,co_cfg, tx_nom, co_loc, co_tipDoc, tx_cfgLocUti, tx_obs1, st_reg ";
                    strSql += " FROM tbm_cfgTipDocUtiProAut ";
                    strSql += " WHERE co_emp=" + intCodEmpOrg + " AND co_cfg=" + intCodCfgDevCom + " AND st_reg='A'";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) { /* LA COMPRA ES EL INGRESO DE LA MERCADERIA */

                        intCodEmpGenDevCom = rstLoc.getInt("co_emp");
                        intCodLocGenDevCom = rstLoc.getInt("co_loc");
                        intCodTipDocGenDevCom = rstLoc.getInt("co_tipDoc");
                    }
                    strSql = "";
                    strSql += " SELECT co_emp,co_cfg, tx_nom, co_loc, co_tipDoc, tx_cfgLocUti, tx_obs1, st_reg ";
                    strSql += " FROM tbm_cfgTipDocUtiProAut ";
                    strSql += " WHERE co_emp=" + intCodEmpDes + " AND co_cfg=" + intCodCfgTra + " AND st_reg='A'";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        intCodLocTra = rstLoc.getInt("co_loc");
                        intCodTipDocGenTra = rstLoc.getInt("co_tipDoc");
                    }
                    rstLoc.close();
                    rstLoc = null;
                }

                stmLoc.close();
                stmLoc = null;
            }
            conLoc.close();
            conLoc = null;
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(null, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(null, Evt);
        }
        return blnRes;
    }
    /**
     * Datos necesarios para los prestamos entre empresas
     *
     * @param rstLoc
     * @return
     */
    private boolean obtenerDatosVenta(int intCodItmMae) {
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try {

            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                strSql = "";
                strSql += " SELECT co_itmMae, co_emp, co_itm \n";
                strSql += " FROM tbm_equInv \n";
                strSql += " WHERE co_emp=" + intCodEmpGenVen + " AND co_itmMae=" + intCodItmMae + " \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    intCodItmGenVen = rstLoc.getInt("co_itm");
                }
                conLoc.close();
                conLoc = null;
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(null, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(null, Evt);
        }
        return blnRes;
    }
     private boolean obtenerDatosCompra(int intCodItmMae) {
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try {

            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                strSql = "";
                strSql += " SELECT co_itmMae, co_emp, co_itm \n";
                strSql += " FROM tbm_equInv \n";
                strSql += " WHERE co_emp=" + intCodEmpGenCom + " AND co_itmMae=" + intCodItmMae + " \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    intCodItmGenCom = rstLoc.getInt("co_itm");
                }
                conLoc.close();
                conLoc = null;
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(null, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(null, Evt);
        }
        return blnRes;
    }
   
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
     
     /**
     * *
     * EGRESO E INGRESO: Para la tabla de tbm_invBod campo a
     * trabajar: EGRESOS: nd_stkAct(0), nd_canDis(10) INGRESOS:nd_stkAct(0)
     *
     * @param con
     * @return
     */
    private boolean generaMovimientoInventarioEgrIng(java.sql.Connection con) {
        boolean blnRes = true;
        objStkInv = new ZafStkInv(objParSis);
        try {
            if (generaMovimientoInventarioPrestamosIngreso(con)) {
                if (generaMovimientoInventarioPrestamosEgreso(con)) {
                    System.out.println("ENVIA....  ");
                }
            }
        } catch (Exception e) {
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    } 
    private boolean generaMovimientoInventarioPrestamosIngreso(java.sql.Connection con){
        boolean blnRes=false;
        try{
            System.out.println("Genera Ingreso .......... ");
                if(generaNuevoContenedorItemsPrestamosIngreso()){
                    if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_STK_ACT, "+", 0, arlDatStkInvItm)){
                        if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_CAN_DIS, "+", 1, arlDatStkInvItm)){
                            blnRes=true;
                        }else{blnRes=false;}
                    }else{blnRes=false;}
                }else{blnRes=false;}
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                System.err.println("ERROR: " + e.toString());
                blnRes=false;
        }
        return blnRes;
    }
     private boolean generaMovimientoInventarioPrestamosEgreso(java.sql.Connection con){
        boolean blnRes=false;
        try{
            System.out.println("Genera Egreso .......... ");  
                if(generaNuevoContenedorItemsPrestamosEgreso()){
                    if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_STK_ACT, "-", 0, arlDatStkInvItm)){
                        if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_CAN_DIS, "-", 1, arlDatStkInvItm)){
                            blnRes=true;
                        }else{blnRes=false;}
                    }else{blnRes=false;}
                }else{blnRes=false;}
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                System.err.println("ERROR: " + e.toString());
                blnRes=false;
        }
        return blnRes;
    }
     private boolean generaNuevoContenedorItemsPrestamosIngreso(){
        boolean blnRes=true;
        double dblAux;
        try{
            arlDatStkInvItm = new ArrayList();
            for(int i=0;i<arlConDatPreInvIngAux.size();i++){
                arlRegStkInvItm = new ArrayList();
                intCodEmpMovStk=objUti.getIntValueAt(arlConDatPreInvIngAux, i, INT_ARL_COD_EMP);
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_GRP,objUti.getStringValueAt(arlConDatPreInvIngAux, i,INT_ARL_CODITMGRP) );
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_EMP,objUti.getStringValueAt(arlConDatPreInvIngAux, i,INT_ARL_CODITMEMP) );
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_MAE,objUti.getStringValueAt(arlConDatPreInvIngAux, i,INT_ARL_CODITMMAE) );
                arlRegStkInvItm.add(INT_STK_INV_COD_LET_ITM,objUti.getStringValueAt(arlConDatPreInvIngAux, i,INT_ARL_COD_LET_ITM_) );
                dblAux=Double.parseDouble(objUti.getStringValueAt(arlConDatPreInvIngAux, i,INT_ARL_CAN_MOV));
                if(dblAux<0){
                    dblAux=dblAux*-1;
                }
                arlRegStkInvItm.add(INT_STK_INV_CAN_ITM,dblAux );
                arlRegStkInvItm.add(INT_STK_INV_COD_BOD_EMP,objUti.getStringValueAt(arlConDatPreInvIngAux, i,INT_ARL_COD_BOD_ING_EGR_) );
                arlDatStkInvItm.add(arlRegStkInvItm);
            }
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
        }
        return blnRes;
    } 
     private boolean generaNuevoContenedorItemsPrestamosEgreso(){
        boolean blnRes=true;
        double dblAux;
        try{
            arlDatStkInvItm = new ArrayList();
            for(int i=0;i<arlConDatPreInvEgrAux.size();i++){
                arlRegStkInvItm = new ArrayList();
                
                intCodEmpMovStk=objUti.getIntValueAt(arlConDatPreInvEgrAux, i, INT_ARL_COD_EMP);
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_GRP,objUti.getStringValueAt(arlConDatPreInvEgrAux, i,INT_ARL_CODITMGRP) );
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_EMP,objUti.getStringValueAt(arlConDatPreInvEgrAux, i,INT_ARL_CODITMEMP ) );
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_MAE,objUti.getStringValueAt(arlConDatPreInvEgrAux, i,INT_ARL_CODITMMAE ) );
                arlRegStkInvItm.add(INT_STK_INV_COD_LET_ITM,objUti.getStringValueAt(arlConDatPreInvEgrAux, i,INT_ARL_COD_LET_ITM_) );
                dblAux=Double.parseDouble(objUti.getStringValueAt(arlConDatPreInvEgrAux, i,INT_ARL_CAN_MOV));
                if(dblAux<0){
                    dblAux=dblAux*-1;
                }
                arlRegStkInvItm.add(INT_STK_INV_CAN_ITM,dblAux );
                arlRegStkInvItm.add(INT_STK_INV_COD_BOD_EMP,objUti.getStringValueAt(arlConDatPreInvEgrAux, i,INT_ARL_COD_BOD_ING_EGR_) );
                arlDatStkInvItm.add(arlRegStkInvItm);
            }
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
        }
        return blnRes;
    } 
      private void obtenerCosPreUniTra(int intCodItm, int intCodEmpTra, int intCodLocTra, int intCodTipDoc, int intCodDoc){
       java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                strSql = " ";
                strSql += " select distinct co_emp, co_loc, co_tipdoc, co_doc , co_itm,@nd_canpen as nd_can, nd_cosuni, nd_preuni ";
                strSql += " from tbm_detmovinv ";
                strSql += " where co_emp="+intCodEmpTra;
                strSql += " and   co_loc="+intCodLocTra ;
                strSql += " and co_tipdoc="+ intCodTipDoc;
                strSql += " and co_doc="+intCodDoc;
                rstLoc = stmLoc.executeQuery(strSql);
                while (rstLoc.next()) {
                    if (rstLoc.getInt("co_itm")==intCodItm) {
                         dblCosUni = rstLoc.getDouble("nd_cosuni");
                         dblPreUni = rstLoc.getDouble("nd_preuni");
                         break;
                    }
                }
                conLoc.close();
                conLoc = null;
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
            }
        } catch (java.sql.SQLException Evt) {
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(null, Evt);
        } catch (Exception Evt) {
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(null, Evt);
        }
     
     }
    
    /**
     * Función que permite obtener datos necesarios para generar el documento
     * @param con                       Conexión enviada
     * @param intCodigoEmpresa          Código de empresa donde se guardará el documento
     * @param intCodigoLocal            Código de local donde se guardará el documento
     * @param intCodigoTipoDocumento    Código de tipo de documento donde se guardará el documento
     * @param dtpFechaDocumento         <HTML>Fecha de documento con la que se guardará el documento. <BR>Formato: dd/MM/yyyy</HTML>
     * @param intCodigoBodegaEgreso     Código de bodega donde se egresará la mercadería
     * @param intCodigoBodegaIngreso    Código de bodega donde se ingresará la mercadería
     * @param chrGenerarIva             Estado que permitirá saber si hay que calcular valor de subtotal. Según tipo de documento. Por Préstamos ='N', por Facturación/Compra ='S'
     * @param arlDatosItem              <HTML>Información del item:
     *                                      <BR>Código del item de grupo
     *                                      <BR>Código del item de empresa
     *                                      <BR>Código del item maestro
     *                                      <BR>Código del item alterno
     *                                      <BR>Nombre del item
     *                                      <BR>Unidad de medida del item
     *                                      <BR>Código del item en letras
     *                                      <BR>Cantidad del item.     La cantidad se envía en negativo. 
     *                                                             <BR>La clase se encarga de generar registro para cantidad positiva y registro para cantidad negativa.
     *                                      <BR>Costo unitario del item
     *                                      <BR>Precio unitario del item
     *                                      <BR>Peso del item
     *                                      <BR>Estado del item para generar o no el IVA
     *                                  </HTML>
     * @return 
     */
    private boolean getDatoTransferencia(Connection con, int intCodigoEmpresa, int intCodigoLocal, int intCodigoTipoDocumento
        , Date dtpFechaDocumento, int intCodigoBodegaEgreso, int intCodigoBodegaIngreso, char chrGenerarIva, ArrayList arlDatosItem
            , int codEmpSol, int codLocSol, int codTipDocSol, int codDocSol
            , String tipMovSeg, Object estGenOrdDes){
        boolean blnRes=false;
        String strSQL="";
        try{
            if(con!=null){
//                intTipMov=1;
                intCodEmp=intCodigoEmpresa;
                intCodLoc=intCodigoLocal;
                intCodTipDoc=intCodigoTipoDocumento;
                dtpFecDoc=dtpFechaDocumento;
//                intCodBodRegUno=intCodigoBodegaEgreso;
//                intCodBodRegDos=intCodigoBodegaIngreso;
                arlDatItm=arlDatosItem;
                chrGenIva=chrGenerarIva;
                strTipMovSeg=tipMovSeg;
                
                //solicitud
                intCodEmpSol1=codEmpSol;
                intCodLocSol1=codLocSol;
                intCodTipDocSol1=codTipDocSol;
                intCodDocSol1=codDocSol;
                strTipMovSeg=tipMovSeg;
                
                stm=con.createStatement();
                //código
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc) AS co_doc FROM tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next())//JOTA
                    intCodDoc=(Integer.parseInt(rst.getObject("co_doc")==null?"0":(rst.getString("co_doc").equals("")?"0":rst.getString("co_doc")))) + 1;
                else
                    return false;
                
                //número
                strSQL="";
                strSQL+="SELECT a1.ne_ultDoc FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next())//JOTA
                    intNumDoc=(Integer.parseInt(rst.getObject("ne_ultDoc")==null?"0":(rst.getString("ne_ultDoc").equals("")?"0":rst.getString("ne_ultDoc")))) + 1;
                else
                    return false;
                
                //signo para generar el documento
                strSQL="";
                strSQL+="SELECT a1.tx_natdoc FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    bdeSig=new BigDecimal(rst.getString("tx_natDoc").equals("I")?"1":"-1");
                else
                    return false;
                
                //para obtener secuencia de empresa
                strSQL="";
                strSQL+="SELECT CASE WHEN ne_secUltDocTbmCabMovInv IS NULL THEN 0 ELSE (ne_secUltDocTbmCabMovInv+1) END AS ne_numSecEmp";
                strSQL+=" FROM tbm_emp WHERE co_emp=" + intCodEmp + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intNumSecEmp=rst.getInt("ne_numSecEmp");
                    
                //para obtener secuencia de grupo
                strSQL="";
                strSQL+="SELECT CASE WHEN ne_secUltDocTbmCabMovInv IS NULL THEN 0 ELSE (ne_secUltDocTbmCabMovInv+1) END AS ne_numSecGrp";
                strSQL+=" FROM tbm_emp WHERE co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intNumSecGrp=rst.getInt("ne_numSecGrp");

                if(generarSQLDocumentoTransferencia(con)){
                   }
                else
                    blnRes=false;
                if(stm!=null){
                    stm.close();
                    stm=null;
                }
                if(rst!=null){
                    rst.close();
                    rst=null;
                }
                
            }
        }
        catch(java.sql.SQLException e){
            System.err.println("ERROR ZafMovInv " + e.toString());
            blnRes=false;
        }
        catch(Exception e){
            System.err.println("ERROR ZafMovInv " + e.toString());
            blnRes=false;
        }
        return blnRes;
    }
    /**
     * Función que permite generar el documento de ingreso/egreso dependiendo de la naturaleza del tipo de documento 
     * @return true: Si se pudo realizar la operación
     * <BR> false: Caso contrario
     */
    private boolean generarSQLDocumentoTransferencia(java.sql.Connection con){
        boolean blnRes=false;
        inicializaObjTra();
        iniDia();
        try{
            if(con!=null){
                if(insertar_tbmCabMovInv(con, 1)){
                    if(objSegMovInv.setSegMovInvCompra(con, intCodEmpSol1, intCodLocSol1, intCodTipDocSol1, intCodDocSol1, 5, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
                        if(insertar_tbmDetMovInv(con, 2)){
                            if(insertar_tbrDetMovInv_Tra(con)){
                                if(objCnfDoc.setCamNotIngEgrMerBodega(con, arlDatCnfNumRecEnt)){
                                    if(genDiaTrs(intCodEmp)){
                                        if (objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, intCodTipDoc, ""+intCodDoc, ""+intNumDoc, dtpFecDoc)){
                                            blnRes=true; 
                                        }
                                    }

                                }

                            }
                        }
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("Error - generarSQLDocumentoTransferencia: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }   
    //<editor-fold defaultstate="collapsed" desc="Inicializa algunos objetos para realizar la transferencia.">
    private void inicializaObjTra() {
            objUti=new ZafUtil();
            cmpPad=componente;
            objAsiDia = new ZafAsiDia(objParSis);
            intNumDec=objParSis.getDecimalesBaseDatos();
            bgdValPorIva=objParSis.getPorcentajeIvaVentas().divide(new BigDecimal("100"),objParSis.getDecimalesMostrar(),BigDecimal.ROUND_HALF_UP)  ;
            objSegMovInv=new ZafSegMovInv(objParSis, cmpPad);
            arlDatCnfNumRecEnt=new ArrayList();
            objCnfDoc=new ZafCnfDoc(objParSis, cmpPad);
            vecDatDia=new Vector();
    }//</editor-fold>
    private boolean iniDia(){
        boolean blnRes=true;
        try{
            objAsiDia.inicializar();
            vecDatDia.clear();
        }
        catch(Exception e){
            System.out.println("Error - generarSQLDocumentoTransferencia: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
     /**
     * Función que permite realizar la inserción de la cabecera
     * @return true: Si se pudo realizar la operación
     * <BR> false: caso contrario
     */
    private boolean insertar_tbmCabMovInv(Connection con, int tipoTransaccion){
        boolean blnRes=true;
        String strRucCliPrv="";
        String strNomCliPrv="";
        String strDirCliPrv="";
        String strTelCliPrv="";
        int intCodForPagCliPrv=-1;
        String strDesForPagCliPrv="";
        try{
            if(con!=null){
                stm=con.createStatement();
                
                if( (tipoTransaccion==0) || (tipoTransaccion==2) ){//egreso o ingreso
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel, a1.co_forPag, a2.tx_des";
                    strSQL+=" FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_cabForPag AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_forPag=a2.co_forPag";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_cli=" + intCodCliPrv + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        strRucCliPrv=rst.getString("tx_ide");
                        strNomCliPrv=rst.getString("tx_nom");
                        strDirCliPrv=rst.getString("tx_dir");
                        strTelCliPrv=rst.getString("tx_tel");
                        intCodForPagCliPrv=rst.getInt("co_forPag");
                        strDesForPagCliPrv=rst.getString("tx_des");
                        rst.close();
                        rst=null;
                    }
                    

                    
                    if( (tipoTransaccion==0) ){//egreso
                        
                        System.out.println("******---------------------------------------*******");
                        System.out.println("tipoTransaccion: " + tipoTransaccion);
                        System.out.println("intCodEmp: " + intCodEmp);
                        System.out.println("intCodBodIng: " + intCodBodIng);
                        System.out.println("******---------------------------------------*******");
                        
                        if(intCodEmpIngGenAsiDia==2){
                            strRucCliPrv="";
                            strNomCliPrv="";
                            strDirCliPrv="";
                            strTelCliPrv="";
                            
                            if( (intCodEmpIngGenAsiDia==2) && (intCodBodIng==1) ){
                                //UIO
                                strSQL="";
                                strSQL+=" SELECT a1.co_cli, a1.tx_nom, a1.tx_ide, a2.tx_dir, a2.tx_tel1 AS tx_tel, a1.co_tipper  ";
                                strSQL+=" FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_dircli AS a2";
                                strSQL+=" ON(a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli AND a2.co_reg=1)";
                                strSQL+=" WHERE a1.co_emp=" + intCodEmpEgrGenAsiDia + " AND a1.co_cli=" + intCodCliPrv + "";
                                strSQL+=";";
                            }

                            if( (intCodEmpIngGenAsiDia==2) && (intCodBodIng==4) ){
                                //MANTA
                                strSQL="";
                                strSQL+=" SELECT a1.co_cli, a1.tx_nom, a1.tx_ide, a2.tx_dir, a2.tx_tel1 AS tx_tel, a1.co_tipper  ";
                                strSQL+=" FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_dircli AS a2";
                                strSQL+=" ON(a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli AND a2.co_reg=2)";
                                strSQL+=" WHERE a1.co_emp=" + intCodEmpEgrGenAsiDia + " AND a1.co_cli=" + intCodCliPrv + "";
                                strSQL+=";";
                            }

                            if( (intCodEmpIngGenAsiDia==2) && (intCodBodIng==12) ){
                                //STO DOMINGO
                                strSQL="";
                                strSQL+=" SELECT a1.co_cli, a1.tx_nom, a1.tx_ide, a2.tx_dir, a2.tx_tel1 AS tx_tel, a1.co_tipper  ";
                                strSQL+=" FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_dircli AS a2";
                                strSQL+=" ON(a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli AND a2.co_reg=3)";
                                strSQL+=" WHERE a1.co_emp=" + intCodEmpEgrGenAsiDia + " AND a1.co_cli=" + intCodCliPrv + "";
                                strSQL+=";";
                            }

                            if( (intCodEmpIngGenAsiDia==2) && (intCodBodIng==28) ){
                                //CUENCA
                                strSQL="";
                                strSQL+=" SELECT a1.co_cli, a1.tx_nom, a1.tx_ide, a2.tx_dir, a2.tx_tel1 AS tx_tel, a1.co_tipper  ";
                                strSQL+=" FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_dircli AS a2";
                                strSQL+=" ON(a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli AND a2.co_reg=4)";
                                strSQL+=" WHERE a1.co_emp=" + intCodEmpEgrGenAsiDia + " AND a1.co_cli=" + intCodCliPrv + "";
                                strSQL+=";";
                            }
                            
                            System.out.println("Datos de direccion: " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next()){
                                strRucCliPrv=rst.getString("tx_ide");
                                strNomCliPrv=rst.getString("tx_nom");
                                strDirCliPrv=rst.getString("tx_dir");
                                strTelCliPrv=rst.getString("tx_tel");
                                rst.close();
                                rst=null;
                            }


                        }
                                                                        
                    }                                   
                    
                }
                
                
                strSQL="";
                strSQL+="INSERT INTO tbm_cabMovInv(";
                strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, ne_numcot, ne_numdoc, ne_numgui, fe_doc, co_cli, tx_ruc, tx_nomcli, tx_dircli, tx_telcli,";
                strSQL+="   co_forpag, tx_desforpag, nd_sub, nd_tot, tx_obs1, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, tx_numped,";
                strSQL+="   ne_secgrp, ne_secemp, nd_valiva, co_mnu, st_tipdev, st_imp, tx_coming, tx_commod, co_ptodes,";
                strSQL+="   co_emprelcabsoltrainv, co_locrelcabsoltrainv, co_tipdocrelcabsoltrainv, co_docrelcabsoltrainv, tx_tipmov, st_genOrdDes,st_coninv";
                strSQL+=")";
                strSQL+="   (";
                strSQL+="    SELECT ";
                strSQL+="" + intCodEmp + ",";//co_emp
                strSQL+="" + intCodLoc + ",";//co_loc
                strSQL+="" + intCodTipDoc + ",";//co_tipdoc
                strSQL+="" + intCodDoc + ",";//co_doc
                strSQL+="0, ";//ne_numcot
                strSQL+="" + intNumDoc + ",";//ne_numdoc
                strSQL+="0, ";//ne_numgui
                //strSQL+=" '" + objUti.formatearFecha(dtpFecDoc.toString(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "',";//fe_doc
                strSQL+="'"+ objUti.formatearFecha(dtpFecDoc, objParSis.getFormatoFechaBaseDatos()) +"', "; //fe_doc
                if( (tipoTransaccion==0) || (tipoTransaccion==2) ){//egreso o ingreso
                    strSQL+="" + intCodCliPrv + ",";//co_cli
                    strSQL+="" + objUti.codificar(strRucCliPrv) + ",";//tx_ruc
                    strSQL+="" + objUti.codificar(strNomCliPrv) + ",";//tx_nomcli
                    strSQL+="" + objUti.codificar(strDirCliPrv) + ",";//tx_dircli
                    strSQL+="" + objUti.codificar(strTelCliPrv) + ",";//tx_telcli
                    strSQL+="" + objUti.codificar(intCodForPagCliPrv) + ",";//co_forpag
                    strSQL+="" + objUti.codificar(strDesForPagCliPrv) + ",";//tx_desforpag
                }
                else{
                    strSQL+="Null,";//co_cli
                    strSQL+="Null,";//tx_ruc
                    strSQL+="Null,";//tx_nomcli
                    strSQL+="Null,";//tx_dircli
                    strSQL+="Null,";//tx_telcli
                    strSQL+="Null,";//co_forpag
                    strSQL+="Null,";//tx_desforpag
                }

                strSQL+="0,";//nd_sub
                strSQL+="0,";//nd_tot
                strSQL+="'Generado por Zafiro: '||" + " CURRENT_TIMESTAMP" + ",";//tx_obs1
                strSQL+="'A'" + ",";//st_reg
                strSQL+=" CURRENT_TIMESTAMP" + ",";//fe_ing
                strSQL+=" CURRENT_TIMESTAMP" + ",";//fe_ultmod
                strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usring
                strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usrmod
                strSQL+="Null,";//tx_numped   No almacena nada en este momento sino cuando se obtengan los dos tipos de documentos
                strSQL+="" + intNumSecGrp + ",";//ne_secgrp
                strSQL+="" + intNumSecEmp + ",";//ne_secemp
                strSQL+="0,";//nd_valiva
                strSQL+="" + objParSis.getCodigoMenu() + ",";//co_mnu
                strSQL+="'C',";//st_tipdev
                strSQL+="'N',";//st_imp
                strSQL+="" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + ",";//tx_coming
                strSQL+="" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + ",";//tx_commod
                if(tipoTransaccion==0)//Egreso
                    strSQL+="" + intCodBodIng + ",";//co_ptodes
                else if(tipoTransaccion==2)//Ingreso
                    strSQL+="" + intCodBodEgr + ",";//co_ptodes
                else if(tipoTransaccion==1)//Transferencia
                    strSQL+=" Null,";//co_ptodes
                else
                    strSQL+=" Null, ";//co_ptodes
                
                strSQL+="" + intCodEmpSol1 + ",";//co_emprelcabsoltrainv
                strSQL+="" + intCodLocSol1 + ",";//co_locrelcabsoltrainv
                strSQL+="" + intCodTipDocSol1 + ",";//co_tipdocrelcabsoltrainv
                strSQL+="" + intCodDocSol1 + ",";//co_docrelcabsoltrainv
                strSQL+="'" + strTipMovSeg + "'";//tx_tipmov
                
                if(tipoTransaccion==0){//Egreso
                    if(objEstDocGenOrdDes==null)
                        strSQL+=", " + null + "";//st_genOrdDes
                    else
                        strSQL+=", '" + null + "'";//st_genOrdDes
                }
                else if(tipoTransaccion==1){//Transferencia
                        strSQL+=", " + null + "";//st_genOrdDes
                }
                else if(tipoTransaccion==2){//Ingreso
                    if(objEstDocGenOrdDes==null)
                        strSQL+=", Null";//st_genOrdDes
                    else
                        strSQL+=", Null";//st_genOrdDes
                }
                else{
                   strSQL+=", Null";//st_genOrdDes 
                }
                strSQL+=", 'F'";//st_coninv
                
                
                
                strSQL+=");";
                System.out.println("insertar_tbmCabMovInv: " + strSQL);
                
                //para obtener secuencia de empresa
                strSQL+=" UPDATE tbm_emp";
                strSQL+=" SET ne_secUltDocTbmCabMovInv=(ne_secUltDocTbmCabMovInv+1)";
                strSQL+=" WHERE co_emp=" + intCodEmp + "";
                strSQL+=";";
                System.out.println("aumentar secuencia empresa: " + strSQL);
                //para obtener secuencia de grupo
                strSQL+=" UPDATE tbm_emp";
                strSQL+=" SET ne_secUltDocTbmCabMovInv=(ne_secUltDocTbmCabMovInv+1)";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=";";
                System.out.println("aumentar secuencia grupo: " + strSQL);
                stm.executeUpdate(strSQL);
             
                stm.close();
                stm=null;

            }
        }
        catch(java.sql.SQLException e){
            System.out.println("Error - insertar_tbmCabMovInv SQL: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - insertar_tbmCabMovInv: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    /**
     * Función que permite realizar la inserción de la detalle
     * @param con: Especifíca la conexión que se desea utilizar
     * @return true: Si se pudo realizar la operación
     * <BR> false: caso contrario
    */
    private boolean insertar_tbmDetMovInv(Connection con, int tipoOperacion){
        boolean blnRes=true;
        int j=0;
        BigDecimal bgdCanItm;
        BigDecimal bgdPreItm;
        String strSQLIns="";
        String strEstIva="";
        BigDecimal bgdTotItm;
        BigDecimal bgdIntSig;
        String strNomBodDet="";
        int intNumFil=0;
        String strEstIngEgrMerBod="";
        arlDatCnfNumRecEnt.clear();
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQLIns="";
                bgdValDocSub=new BigDecimal("0");
                bgdValDocIva=new BigDecimal("0");
                bgdValDocTot=new BigDecimal("0");

                System.out.println("arlDatItm: " + arlDatItm.toString());
                System.out.println("tipoOperacion: " + tipoOperacion);
                for(int i=0; i<arlDatItm.size(); i++){
                    if((i%2)==0)//es impar
                        intNumFil++;
                    
                    //nombre de bodega origen
                    strSQL="";
                    strSQL+="SELECT a1.tx_nom AS tx_nomBod FROM tbm_bod AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_bod=" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_BOD_ORI_DES_A)) + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        strNomBodDet=rst.getString("tx_nomBod");
                    else
                        return false;

                    //Egreso, Ingreso, Factura, Orden de Compra
                    //--aqui se inserta en positivo porque la cantidad se la recibe en postivo
                    j++;
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detmovinv(";
                    strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, tx_codalt,";
                    strSQL+="   tx_nomitm, tx_unimed, co_bod, nd_can, nd_canorg, nd_cosuni, nd_preuni,";
                    strSQL+="   nd_pordes, st_ivacom, nd_exi, nd_valexi, st_reg, nd_cancon, tx_obs1,";
                    strSQL+="   co_usrcon, ne_numfil, nd_tot, tx_codalt2, nd_costot, nd_cospro,";
                    strSQL+="   nd_cosunigrp, nd_costotgrp, nd_exigrp, nd_valexigrp, nd_cosprogrp,";
                    strSQL+="   nd_candev, co_itmact, co_locrelsoldevven, co_tipdocrelsoldevven,";
                    strSQL+="   co_docrelsoldevven, co_regrelsoldevven, st_meringegrfisbod, nd_cannunrec,";
                    strSQL+="   co_locrelsolsaltemmer, co_tipdocrelsolsaltemmer, co_docrelsolsaltemmer,";
                    strSQL+="   co_regrelsolsaltemmer, nd_preunivenlis, nd_pordesvenmax, tx_nombodorgdes,";
                    strSQL+="   nd_cantotmalest, nd_cantotmalestpro, nd_cantotnunrecpro, st_cliretemprel,";
                    strSQL+="   fe_retemprel, tx_perretemprel, tx_vehretemprel, tx_plavehretemprel,";
                    strSQL+="   tx_obscliretemprel, nd_ara, nd_preuniimp, nd_valtotfobcfr, nd_cantonmet,";
                    strSQL+="   nd_valfle, nd_valcfr, nd_valtotara, nd_valtotgas, nd_canutiorddis";
                    
                    strEstIngEgrMerBod=objUti.getObjectValueAt(arlDatItm, i, INT_ARL_EST_ING_EGR_MER_BOD_A)==null?"":objUti.getStringValueAt(arlDatItm, i, INT_ARL_EST_ING_EGR_MER_BOD_A);
                    
                    strSQL+=", nd_canEgrBod";
                    strSQL+=", nd_canIngBod";
                    strSQL+=", nd_canPen";
                    
                    
                    strSQL+=")";
                    strSQL+="(";
                    strSQL+="   SELECT ";
                    strSQL+="" + intCodEmp + ",";//co_emp
                    strSQL+="" + intCodLoc + ",";//co_loc
                    strSQL+="" + intCodTipDoc + ",";//co_tipdoc
                    strSQL+="" + intCodDoc + ",";//co_doc
                    strSQL+="" + j + ",";//co_reg
                    strSQL+="" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_EMP_A) + ",";//co_itm
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ALT_ITM_A)) + ",";//tx_codalt
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_NOM_ITM_A)) + ",";//tx_nomitm
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_UNI_MED_ITM_A)) + ",";//tx_unimed
                    //strSQL+="" + intCodBodRegUno + ",";//co_bod
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_BOD_ORI_DES_A)) + ",";//co_bod
                    bgdCanItm=new BigDecimal(objUti.getObjectValueAt(arlDatItm, i, INT_ARL_CAN_ITM_A)==null?"0":(objUti.getStringValueAt(arlDatItm, i, INT_ARL_CAN_ITM_A).equals("")?"0":objUti.getStringValueAt(arlDatItm, i, INT_ARL_CAN_ITM_A)));
                    strSQL+="" + bgdCanItm + ",";//nd_can
                    strSQL+="Null,";//nd_canorg
                    bgdPreItm=objUti.redondearBigDecimal(objUti.getStringValueAt(arlDatItm, i, INT_ARL_PRE_UNI_A), intNumDec);
                    strSQL+="" + bgdPreItm + ",";//nd_cosuni
                    strSQL+="" + bgdPreItm + ",";//nd_preuni
                    strSQL+="0,";//nd_pordes     por disposición de Eddye para FACVENE, FACCOM, INGBOPR, EGBOPR, TRANSFERENCIAS EN GENERAL, no se debe realizar descuento           
                    strEstIva=objUti.getStringValueAt(arlDatItm, i, INT_ARL_IVA_COM_ITM);
                    bgdTotItm=objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)), intNumDec);
                    if(chrGenIva=='S'){
                        if(strEstIva.equals("S")){                        
                            bgdValDocIva=bgdValDocIva.add(objUti.redondearBigDecimal((bgdTotItm.multiply(bgdValPorIva)), objParSis.getDecimalesMostrar()));//al final tendrá el valor del iva del documento
                        }
                    }                
                    strSQL+="" + objUti.codificar(strEstIva) + ",";//st_ivacom                
                    strSQL+="Null, ";//nd_exi
                    strSQL+="Null,";//nd_valexi
                    strSQL+="Null,";//st_reg
                    strSQL+="0,";//nd_cancon
                    strSQL+="Null,";//tx_obs1
                    strSQL+="Null,";//co_usrcon
                    strSQL+="" + intNumFil + ",";//ne_numfil
                    strSQL+="" + objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)), intNumDec) + ", ";//nd_tot
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_LET_ITM_A)) + ",";//tx_codalt2
                    strSQL+="" + objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)), intNumDec) + ",";//nd_costot
                    strSQL+="Null,";//nd_cospro
                    strSQL+="" + objUti.redondearBigDecimal(objUti.getStringValueAt(arlDatItm, i, INT_ARL_PRE_UNI_A), intNumDec) + ",";//nd_cosunigrp
                    strSQL+="" + objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)), intNumDec) + ",";//nd_costotgrp
                    strSQL+="Null,";//nd_exigrp
                    strSQL+="Null,";//nd_valexigrp
                    strSQL+="Null,";//nd_cosprogrp
                    strSQL+="0, ";//nd_candev
                    strSQL+="" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_EMP_A) + ",";//co_itmact
                    strSQL+="Null,";//co_locrelsoldevven
                    strSQL+="Null,";//co_tipdocrelsoldevven
                    strSQL+="Null,";//co_docrelsoldevven
                    strSQL+="Null,";//co_regrelsoldevven
                    strSQL+="'" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_EST_ING_EGR_MER_BOD_A) + "',";//st_meringegrfisbod  
                    strSQL+="0,";//nd_cannunrec
                    strSQL+="Null,";//co_locrelsolsaltemmer
                    strSQL+="Null,";//co_tipdocrelsolsaltemmer
                    strSQL+="Null,";//co_docrelsolsaltemmer
                    strSQL+="Null,";//co_regrelsolsaltemmer
                    strSQL+="Null,";//nd_preunivenlis
                    strSQL+="Null,";//nd_pordesvenmax
    //                strSQL+="" + objUti.codificar(strNomBodRegUno) + ",";//tx_nombodorgdes
                    strSQL+="" + objUti.codificar(strNomBodDet) + ",";//tx_nombodorgdes
                    strSQL+="Null,";//nd_cantotmalest
                    strSQL+="Null,";//nd_cantotmalestpro
                    strSQL+="Null,";//nd_cantotnunrecpro
                    strSQL+="Null,";//st_cliretemprel
                    strSQL+="Null,";//fe_retemprel
                    strSQL+="Null,";//tx_perretemprel
                    strSQL+="Null,";//tx_vehretemprel
                    strSQL+="Null,";//tx_plavehretemprel
                    strSQL+="Null,";//tx_obscliretemprel
                    strSQL+="Null,";//nd_ara
                    strSQL+="Null,";//nd_preuniimp
                    strSQL+="Null,";//nd_valtotfobcfr
                    strSQL+="Null,";//nd_cantonmet
                    strSQL+="Null,";//nd_valfle
                    strSQL+="Null,";//nd_valcfr
                    strSQL+="Null,";//nd_valtotara
                    strSQL+="Null,";//nd_valtotgas
                    strSQL+="Null";//nd_canutiorddis

                    ////////////////////////////////////////////////
                    if(strEstIngEgrMerBod.equals("S")){
                        if(tipoOperacion==0){//egreso
                            strSQL+=", " + bgdCanItm + "";//nd_canEgrBod
                            strSQL+=", Null";//nd_canIngBod
                            strSQL+=", " + bgdCanItm + "";//nd_canPen
                        }
                        else if(tipoOperacion==1){//ingreso
                            strSQL+=", Null";//nd_canEgrBod
                            strSQL+=", " + bgdCanItm + "";//nd_canIngBod
                            strSQL+=", " + bgdCanItm + "";//nd_canPen
                        }
                        else if(tipoOperacion==2){//tx
                            if((i%2)==0){//es impar
                                strSQL+=", " + bgdCanItm + "";//nd_canEgrBod
                                strSQL+=", Null";//nd_canIngBod
                                strSQL+=", " + bgdCanItm + "";//nd_canPen
                            }
                            if((i%2)!=0){//es par
                                strSQL+=", Null";//nd_canEgrBod
                                strSQL+=", " + bgdCanItm + "";//nd_canIngBod
                                strSQL+=", " + bgdCanItm + "";//nd_canPen
                            }
                        }
                    }
                    else{
                        strSQL+=", Null";//nd_canEgrBod
                        strSQL+=", Null";//nd_canIngBod
                        strSQL+=", Null";//nd_canPen
                        
                        
                        arlRegCnfNumRecEnt=new ArrayList();
                        arlRegCnfNumRecEnt.add(INT_ARL_DAT_COD_EMP_CNF_,     "");
                        arlRegCnfNumRecEnt.add(INT_ARL_DAT_COD_LOC_CNF_,     "");
                        arlRegCnfNumRecEnt.add(INT_ARL_DAT_COD_TIP_DOC_CNF_, "");
                        arlRegCnfNumRecEnt.add(INT_ARL_DAT_COD_DOC_CNF_,     "");
                        arlRegCnfNumRecEnt.add(INT_ARL_DAT_COD_REG_CNF_,     "");
                        arlRegCnfNumRecEnt.add(INT_ARL_DAT_COD_ITM_GRP_CNF_, objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_GRP_A));
                        arlRegCnfNumRecEnt.add(INT_ARL_DAT_COD_ITM_EMP_CNF_, objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_EMP_A));
                        arlRegCnfNumRecEnt.add(INT_ARL_DAT_COD_ITM_MAE_CNF_, objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_MAE_A));
                        arlRegCnfNumRecEnt.add(INT_ARL_DAT_CAN_ITM_CNF_,     "");
                        arlRegCnfNumRecEnt.add(INT_ARL_DAT_COD_EMP_ORI_,     intCodEmp);
                        arlRegCnfNumRecEnt.add(INT_ARL_DAT_COD_LOC_ORI_,     intCodLoc);
                        arlRegCnfNumRecEnt.add(INT_ARL_DAT_COD_TIP_DOC_ORI_, intCodTipDoc);
                        arlRegCnfNumRecEnt.add(INT_ARL_DAT_COD_DOC_ORI_,     intCodDoc);
                        arlRegCnfNumRecEnt.add(INT_ARL_DAT_COD_REG_ORI_,     j);
                        arlRegCnfNumRecEnt.add(INT_ARL_DAT_CAN_ITM_NOT_CNF_, bgdCanItm);
                        arlRegCnfNumRecEnt.add(INT_ARL_DAT_COD_BOD_EMP_, objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_BOD_ORI_DES_A));
                        arlRegCnfNumRecEnt.add(INT_ARL_DAT_EST_ING_EGR_MER_BOD_, objUti.getStringValueAt(arlDatItm, i, INT_ARL_EST_ING_EGR_MER_BOD_A));
                        arlDatCnfNumRecEnt.add(arlRegCnfNumRecEnt);
                    }
                    ////////////////////////////////////////////////////////////////
                    strSQL+=");";
                    System.out.println("**--++ :)  insertar_tbmDetMovInv: " + strSQL);
                    strSQLIns+=strSQL;
                    
                    if(tipoOperacion==0){//egreso
                        //ESTO NO ESTARIA BIEN PORQUE ENVIAN LOS ITEMS DE INGRESO Y EGRESO PARA TX
                        bgdValDocSub=objUti.redondearBigDecimal((bgdValDocSub.add(bgdTotItm)), objParSis.getDecimalesMostrar())    ;//al final tendrá el valor del subtotal del documento
                        bgdValDocTot=objUti.redondearBigDecimal(((bgdValDocSub.add(bgdValDocIva))), objParSis.getDecimalesMostrar());//al final tendrá el valor del total del documento
                    }
                    else if(tipoOperacion==1){//ingreso
                        //ESTO NO ESTARIA BIEN PORQUE ENVIAN LOS ITEMS DE INGRESO Y EGRESO PARA TX
                        bgdValDocSub=objUti.redondearBigDecimal((bgdValDocSub.add(bgdTotItm)), objParSis.getDecimalesMostrar());//al final tendrá el valor del subtotal del documento
                        bgdValDocTot=objUti.redondearBigDecimal(((bgdValDocSub.add(bgdValDocIva))), objParSis.getDecimalesMostrar());//al final tendrá el valor del total del documento
                    }
                    else if(tipoOperacion==2){//tx
                        if((i%2)!=0){//impar
                            //ESTO NO ESTARIA BIEN PORQUE ENVIAN LOS ITEMS DE INGRESO Y EGRESO PARA TX
                            bgdValDocSub=objUti.redondearBigDecimal((bgdValDocSub.add(bgdTotItm)), objParSis.getDecimalesMostrar());//al final tendrá el valor del subtotal del documento
                            bgdValDocTot=objUti.redondearBigDecimal(((bgdValDocSub.add(bgdValDocIva))), objParSis.getDecimalesMostrar());//al final tendrá el valor del total del documento
                        }
                    }                    
                }
                
                System.out.println("****************************************************************");
                System.out.println("bgdValDocSub:" + bgdValDocSub);
                System.out.println("bgdValDocTot:" + bgdValDocTot);
                System.out.println("bgdValDocIva:" + bgdValDocIva);
                System.out.println("****************************************************************");
                
                strSQL="";
                strSQL+=" UPDATE tbm_cabMovInv ";
                strSQL+=" SET nd_sub="+bgdValDocSub+", nd_tot="+bgdValDocTot+", nd_valIva="+bgdValDocIva+" ";
                strSQL+=" WHERE co_emp="+intCodEmp + " AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc+" AND co_doc="+intCodDoc+";";
                System.out.println("UPDATE tbm_cabMovInv: " + strSQL);
                strSQLIns+=strSQL;
                
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }

        }
        catch(java.sql.SQLException e){
            System.out.println("Error - insertar_tbmDetMovInv SQL: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - insertar_tbmDetMovInv: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Función que permite realizar la inserción de la relación entre los registros de egreso e ingreso de la transferencia
     * @param con: Especifíca la conexión que se desea utilizar
     * @return true: Si se pudo realizar la operación
     * <BR> false: caso contrario
    */
    private boolean insertar_tbrDetMovInv_Tra(Connection con){
        boolean blnRes=true;
        int j=0;
        String strSQLIns="";
        
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQLIns="";
                for(int i=0; i<arlDatItm.size(); i++){
                    if((i%2)!=0){//es impar
                        strSQL=" INSERT INTO tbr_detmovinv(";
                        strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, co_reg,";
                        strSQL+="   co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel";
                        strSQL+=" , st_reg) (SELECT ";
                        strSQL+="" + intCodEmp + "";//co_emp
                        strSQL+="," + intCodLoc + "";//co_loc
                        strSQL+="," + intCodTipDoc + "";//co_tipdoc
                        strSQL+="," + intCodDoc + "";//co_doc
                        j++;
                        strSQL+="," + j + "";//co_reg
                        strSQL+="," + intCodEmp + "";//co_emprel
                        strSQL+="," + intCodLoc + "";//co_locrel
                        strSQL+="," + intCodTipDoc + "";//co_tipdocrel
                        strSQL+="," + intCodDoc + "";//co_docrel
                        j++;
                        strSQL+="," + j + "";//co_regrel
                        strSQL+=",'A');";//st_reg
                        strSQLIns+=strSQL;
                    }
                }                
                System.out.println("insertar_tbrDetMovInv_Tra: "+strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
            }

        }
        catch(java.sql.SQLException e){
            System.out.println("Error - insertar_tbrDetMovInv_Tra SQL: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - insertar_tbrDetMovInv_Tra: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    /**
     * Función que permite generar el diario de transferencia
     * @param codigoEmpresaDocumento Código de empresa del documento
     * @param codigoEmpresaCliente Código de empresa del cliente
     */
    private boolean genDiaTrs(int codigoEmpresaDocumento){        
        boolean blnRes=true;
        try{
            if(codigoEmpresaDocumento==1){
                //Cuenta de debe
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "3000");//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocTot.abs());
                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
                //Cuenta de haber
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "3000");//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                vecRegDia.add(INT_VEC_DIA_DEB, "0");
                vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocTot.abs());
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
            }
            else if(codigoEmpresaDocumento==2){
                //Cuenta de debe
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "1277");//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocTot.abs());
                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
                //Cuenta de haber
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "1277");//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA     GENERAL");
                vecRegDia.add(INT_VEC_DIA_DEB, "0");
                vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocTot.abs());
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
            }
            else if(codigoEmpresaDocumento==4){
                //Cuenta de debe
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "2253");//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocTot.abs());
                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
                //Cuenta de haber
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "2253");//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                vecRegDia.add(INT_VEC_DIA_DEB, "0");
                vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocTot.abs());
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
            }
            System.out.println("*detalle diario:  " + objAsiDia.getDetalleDiario());
            objAsiDia.setDetalleDiario(vecDatDia);
            System.out.println("***detalle diario:  " + objAsiDia.getDetalleDiario());
            objAsiDia.setGeneracionDiario((byte)2);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    /**
     * *
     * TRANSFERENCIAS DE INVENTARIO: Para la tabla de tbm_invBod campo a
     * trabajar: EGRESOS: nd_stkAct(0), nd_canDis(10) INGRESOS:nd_stkAct(0)
     *
     * @param con
     * @return
     */

    private boolean generaMovimientoInventarioTransferencia(java.sql.Connection con, int intCodigoEmpresa) {
        boolean blnRes = true;
        objStkInv = new ZafStkInv(objParSis);
        try {
            if (generaNuevoContenedorItemsTransferenciaIngreso()) {
                // System.out.println("GeneraMovimientoInventarioTransferencia......INGRESO.... " + arlDatStkInvItm);
                if (objStkInv.actualizaInventario(con, intCodigoEmpresa, INT_ARL_STK_INV_STK_ACT, "-", 1, arlDatStkInvItm)) {
                    if (objStkInv.actualizaInventario(con, intCodigoEmpresa, INT_ARL_STK_INV_CAN_ING_BOD, "-", 0, arlDatStkInvItm)) {
                        //if (objStkInv.actualizaInventario(con, intCodigoEmpresa, INT_ARL_STK_INV_CAN_DIS, "+", 1, arlDatStkInvItm)) {
                        if (generaNuevoContenedorItemsTransferenciaEgreso()) {
                            //System.out.println("GeneraMovimientoInventarioTransferencia....EGRESO...... " + arlDatStkInvItm);
                            if (objStkInv.actualizaInventario(con, intCodigoEmpresa, INT_ARL_STK_INV_STK_ACT, "+", 0, arlDatStkInvItm)) {
                                if (objStkInv.actualizaInventario(con, intCodigoEmpresa, INT_ARL_STK_INV_CAN_DIS, "+", 0, arlDatStkInvItm)) {
                                    if (objStkInv.actualizaInventario(con, intCodigoEmpresa, INT_ARL_STK_INV_CAN_EGR_BOD, "+", 1, arlDatStkInvItm)) {
                                        System.out.println("Mover Inventario ZafStkInv....  ");
                                        blnRes = true;
                                    }
                                }
                            } else {
                                System.out.println("FALLA EN ZafStkInv....  ");
                                blnRes = false;
                            }
                        } else {
                            System.out.println("FALLA EN GENERAR NUEVO CONTENEDOR DE ITEMS....  ");
                            blnRes = false;
                        }
//                    }else{
//                        System.out.println("FALLA EN GENERAR NUEVO CONTENEDOR DE ITEMS....  ");
//                            blnRes = false;
//                        }
                    } else {
                        System.out.println("FALLA EN GENERAR NUEVO CONTENEDOR DE ITEMS....  ");
                        blnRes = false;
                    }
                } else {
                    System.out.println("FALLA EN ZafStkInv....  ");
                    blnRes = false;
                }
            } else {
                System.out.println("FALLA EN GENERAR NUEVO CONTENEDOR DE ITEMS....  ");
                blnRes = false;
            }
        } catch (Exception e) {
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
     private boolean generaNuevoContenedorItemsTransferenciaIngreso() {
        boolean blnRes = true;
        double dblAux;
        try {
            arlDatStkInvItm = new ArrayList();
            for (int i = 0; i < arlConDatTraInvStkIng.size(); i++) {
                arlRegStkInvItm = new ArrayList();
                //double dblCanReversada=0.00;
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_GRP, objUti.getStringValueAt(arlConDatTraInvStkIng, i, INT_ARL_CODITMGRP));
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_EMP, objUti.getStringValueAt(arlConDatTraInvStkIng, i, INT_ARL_CODITMEMP));
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_MAE, objUti.getStringValueAt(arlConDatTraInvStkIng, i, INT_ARL_CODITMMAE));
                arlRegStkInvItm.add(INT_STK_INV_COD_LET_ITM, objUti.getStringValueAt(arlConDatTraInvStkIng, i, INT_ARL_CODLETITM));
                
//                if (arlDatHistoricoMovInvEgr != null) {
//                        for (int y = 0; y < arlDatHistoricoMovInvEgr.size(); y++) {
//                            if (objUti.getStringValueAt(arlDatHistoricoMovInvEgr, y, INT_ARL_CODLETITM).equals(objUti.getStringValueAt(arlConDatTraInvStkIng, i, INT_ARL_CODLETITM))) {
//                                dblCanReversada = dblCanReversada + objUti.getDoubleValueAt(arlDatHistoricoMovInvEgr, y, INT_ARL_CANITM);
//                            }
//                        }
//                    }
                
                dblAux = Double.parseDouble(objUti.getStringValueAt(arlConDatTraInvStkIng, i, INT_ARL_CAN_MOV));
                if (dblAux < 0) {
                    dblAux = dblAux * -1;
                }
               // dblAux=dblAux-dblCanReversada;
                arlRegStkInvItm.add(INT_STK_INV_CAN_ITM, dblAux);
                arlRegStkInvItm.add(INT_STK_INV_COD_BOD_EMP, objUti.getStringValueAt(arlConDatTraInvStkIng, i, INT_ARL_COD_BOD_ING_EGR_));  // BODEGA
                arlDatStkInvItm.add(arlRegStkInvItm);
            }
        } catch (Exception e) {
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
      private boolean generaNuevoContenedorItemsTransferenciaEgreso() {
        boolean blnRes = true;
        double dblAux;
        try {
            arlDatStkInvItm = new ArrayList();
            for (int i = 0; i < arlConDatTraInvStkEgr.size(); i++) {
                arlRegStkInvItm = new ArrayList();
                //double dblCanReversada=0.00;
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_GRP, objUti.getStringValueAt(arlConDatTraInvStkEgr, i, INT_ARL_CODITMGRP));
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_EMP, objUti.getStringValueAt(arlConDatTraInvStkEgr, i, INT_ARL_CODITMEMP));
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_MAE, objUti.getStringValueAt(arlConDatTraInvStkEgr, i, INT_ARL_CODITMMAE));
                arlRegStkInvItm.add(INT_STK_INV_COD_LET_ITM, objUti.getStringValueAt(arlConDatTraInvStkEgr, i, INT_ARL_COD_LET_ITM_));
//                if (arlDatHistoricoMovInvEgr != null) {
//                        for (int y = 0; y < arlDatHistoricoMovInvEgr.size(); y++) {
//                            if (objUti.getStringValueAt(arlDatHistoricoMovInvEgr, y, INT_ARL_CODLETITM).equals(objUti.getStringValueAt(arlConDatTraInvStkEgr, i, INT_ARL_CODLETITM))) {
//                                dblCanReversada = dblCanReversada + objUti.getDoubleValueAt(arlDatHistoricoMovInvEgr, y, INT_ARL_CANITM);
//                            }
//                        }
//                    }
                
                dblAux = Double.parseDouble(objUti.getStringValueAt(arlConDatTraInvStkEgr, i, INT_ARL_CAN_MOV));
                if (dblAux < 0) {
                    dblAux = dblAux * -1;
                }
                //dblAux=dblAux-dblCanReversada;
                arlRegStkInvItm.add(INT_STK_INV_CAN_ITM, dblAux);
                arlRegStkInvItm.add(INT_STK_INV_COD_BOD_EMP, objUti.getStringValueAt(arlConDatTraInvStkEgr, i, INT_ARL_COD_BOD_ING_EGR_));  // BODEGA
                arlDatStkInvItm.add(arlRegStkInvItm);
            }
        } catch (Exception e) {
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
      
      //<editor-fold defaultstate="collapsed" desc="Actualiza la solicitud como estado M que significa cancelacion manual.">
    private boolean actualizarSolicitud() {
        boolean blnRes = true;
        java.sql.Statement stmLoc = null;
        String strSql;
        try {
            if (con != null) {
                stmLoc = con.createStatement();
                strSql = "";
//                strSql += " UPDATE tbm_cabsoltrainv  SET st_can='M', ";
//                strSql += " fe_can = current_timestamp , ";
//                strSql += " co_usrcan = " + objParSis.getCodigoUsuario() + " , ";
//                strSql += " tx_comcan = " + objUti.codificar(objParSis.getDireccionIP()) + ", ";
                strSql += " UPDATE tbm_cabsoltrainv  SET  ";
                strSql += " st_coninv = 'E' ";
                strSql += " WHERE co_emp = " + intCodEmpSol1;
                strSql += " AND co_loc = " + intCodLocSol1;
                strSql += " AND co_tipdoc = " + intCodTipDocSol1;
                strSql += " AND co_doc = " + intCodDocSol1;
                stmLoc.executeUpdate(strSql);
                blnRes = true;
                stmLoc.close();
                stmLoc=null;
            }
        } catch (java.sql.SQLException Evt) {
            try {
                blnRes = false;
                con.rollback();
                Evt.printStackTrace();
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (SQLException ex) {
                ex.printStackTrace();
                objUti.mostrarMsgErr_F1(this, Evt);
            }
        } catch (Exception e) {
            try {
                blnRes = false;
                con.rollback();
                e.printStackTrace();
                objUti.mostrarMsgErr_F1(this, e);
            } catch (SQLException ex) {
                ex.printStackTrace();
                objUti.mostrarMsgErr_F1(this, ex);
            }
        }
        return blnRes;
    }//</editor-fold> 
     /**
     * Verifica para agregar los items dependiendo del movimiento
     *
     * @return
     */
    private boolean consultaItemsMovInvEgrIngConf(int x, int intCodEmpMovInvItm, int intCodLocMovInvItm, int intCodTipDocMovInvItm, int intCodDocMovInvItm ) {
        boolean blnRes = false;
        boolean blnEntra=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSqlConf;
        int intNumFil;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            intNumFil = objTblMod.getRowCountTrue();
            strSqlConf = " ";
            strSqlConf += " select a2.co_itm,a2.tx_codalt,a2.tx_codalt2,a2.nd_canpen,a2.co_bod,a3.nd_canegrbod, CASE WHEN a3.nd_canegrbod>=a2.nd_can then a3.nd_canegrbod else a2.nd_can end as cantidadReversa\n";
            strSqlConf += "		from tbm_cabmovinv as a1\n";
            strSqlConf += "		inner join tbm_detmovinv as a2 on (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc)\n";
            strSqlConf += "		inner join tbm_detmovinv as a3 on (a2.tx_codalt=a3.tx_codalt )\n";
            strSqlConf += "		where a1.co_emp= " + intCodEmpMovInvItm; 
            strSqlConf += "		and   a1.co_loc= " +  intCodLocMovInvItm;
            strSqlConf += "		and   a1.co_tipdoc= " + intCodTipDocMovInvItm;
            strSqlConf += "		and   a1.co_doc= "+ intCodDocMovInvItm;
            strSqlConf += "		and   a3.co_emp= " + intTraInvCoEmp; 
            strSqlConf += "		and   a3.co_loc= " +  intTraInvCoLoc;
            strSqlConf += "		and   a3.co_tipdoc= " + intTraInvCoTipDoc;
            strSqlConf += "		and   a3.co_doc= "+ intTraInvCoDoc;
            strSqlConf += "		and a3.nd_canegrbod is not null ";
            rstLoc = stmLoc.executeQuery(strSqlConf);
            //dblCanCan=0.00;
            while (rstLoc.next()) {
                if (rstLoc.getString("tx_codalt").equals(arlItm.get(x).toString())) {
                    blnRes=true;
                    double dblCanPen = rstLoc.getDouble("cantidadReversa");// CANTIDAD A REVERSAR LIMITE DE EGR-ING
                    double dblCanReversada=0.00;
                    double dblCanPendienteTotal=0.00;
                    double dblCantidadCanceladaHistorico=0.00;
                    //double dblCanPendienteTotal=rstLoc.getDouble("nd_canegrbod");
                    for (int i = 0; i < arlConDatTraInv.size(); i++) {
                        obtenerDatosItemConf(objUti.getIntValueAt(arlConDatTraInv, i, INT_ARL_CODITMMAE), objUti.getIntValueAt(arlConDatTraInv, i, INT_ARL_CODEMP));
                        if (rstLoc.getString("tx_codalt").equals(strCodAltItmConf)) {
                              dblCanPendienteTotal=objUti.getDoubleValueAt(arlConDatTraInv, i, INT_ARL_CANITM);//CANTIDAD TOTAL DE LA TRANSFERENCIA
                              break;
                        }
                    }
                    
                    for (int i = 0; i < intNumFil; i++) {
                        if (strCodAltItmConf.equals(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT).toString())) {
                              dblCantidadCanceladaHistorico=Double.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_NUM_CAN_TOT_CAN).toString());
                              break;
                        }
                    }
                    
                    //**Validacion para no tener negativos 
                    if (dblCanPen<0.00) {
                        dblCanPen=dblCanPen*-1;
                    }
                    if (dblCanPendienteTotal<0.00) {
                        dblCanPendienteTotal=dblCanPendienteTotal*-1;
                    }
                    //**
                    
                    if (arlDatHistoricoMovInvEgr != null) {
                        for (int i = 0; i < arlDatHistoricoMovInvEgr.size(); i++) {
                        obtenerDatosItemConf(objUti.getIntValueAt(arlDatHistoricoMovInvEgr, i, INT_ARL_CODITMMAE), objUti.getIntValueAt(arlDatHistoricoMovInvEgr, i, INT_ARL_CODEMP));
                            if (rstLoc.getString("tx_codalt").equals(strCodAltItmConf)) {
                                dblCanReversada = objUti.getDoubleValueAt(arlDatHistoricoMovInvEgr, i, INT_ARL_CANITM);
                            }
                        }
                    }
                    
                    //**Validacion para no tener negativos 
                    if (dblCanReversada<0.00) {
                        dblCanReversada=dblCanReversada*-1;
                    }
                    
                    //**
                    
                        if (dblCanPen!=0.00) {
                            if (dblCanReversada>0.00) {
                                dblCanPendienteTotal=dblCanPendienteTotal-dblCanReversada;
                                if (dblCanPendienteTotal>=(dblCanPen)) {
                                         blnEntra=true;
                                        dblCanCan=dblCanPen;
                                    
                                }else{
                                    blnEntra=true;
                                    dblCanCan=dblCanPendienteTotal;
                                }
                            }
                            if (dblCanPendienteTotal>=dblCanPen) {
                                 blnEntra=true;
                                dblCanCan=dblCanPen;
                            }
                            else{
                                blnEntra=true;
                                dblCanCan=dblCanPendienteTotal;
                            }
                            }
                      //2  --- 13 
                    if (arlDatHistoricoMovInvEgr != null && arlDatHistoricoMovInvEgr.size() > 0) {
                        if (dblCanPen - dblCanCan <= dblCantidadCanceladaHistorico) {
                            dblCanCan = 0.00;
                        }
                    }
                    
                    }
            }
            if (!blnEntra) {
                   dblCanCan=0.00;
            }
            if (dblCanCan<0.00) {
                dblCanCan=dblCanCan*-1;
            }
            rstLoc.close();
            rstLoc = null;
            conLoc.close();
            stmLoc.close();
            conLoc = null;
            stmLoc = null;
        } catch (java.sql.SQLException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    private boolean obtenerDatosItemConf(int codItmMae, int intCodEmpOrg) {
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        boolean blnRes = true;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                strSql = " SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, CASE WHEN a1.nd_cosUni IS NULL THEN 0 ELSE a1.nd_cosUni END as nd_cosUni, \n";
                strSql += "       a1.nd_preVta1, a1.st_ivaVen, CASE WHEN a1.tx_codAlt2 IS NULL THEN '' ELSE a1.tx_codAlt2 END as tx_codAlt2, a2.co_itmMae, \n";
                strSql += "       CASE WHEN a1.co_uni IS NULL THEN 0 ELSE a1.co_uni END as co_uni, ";
                strSql += "       CASE WHEN a1.nd_pesItmKgr IS NULL THEN 0 ELSE a1.nd_pesItmKgr END as nd_pesItmKgr , GRU.co_itm as co_itmGru, \n";
                strSql += "       a1.st_ivaCom, a1.st_ivaVen, a3.tx_desCor";
                strSql += " FROM tbm_inv as a1 \n";
                strSql += " INNER JOIN tbm_equInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSql += "  INNER JOIN tbm_equInv as GRU ON (a2.co_ItmMae=GRU.co_itmMae AND GRU.co_emp=" + objParSis.getCodigoEmpresaGrupo() + ") \n";
                strSql += " LEFT OUTER JOIN tbm_var as a3 ON (a1.co_uni=a3.co_reg) \n";
                strSql += " WHERE a1.co_emp=" + intCodEmpOrg + " and a1.st_reg='A' AND GRU.co_itmMae=" + codItmMae;
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    strCodAltItmConf = rstLoc.getString("tx_codALt");
                    strNomItmConf = rstLoc.getString("tx_nomItm");
                    strCodAltItm2Conf = rstLoc.getString("tx_codAlt2");
                    intCodItmGruConf = rstLoc.getInt("co_itmGru");
                }
                conLoc.close();
                conLoc = null;
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(null, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(null, Evt);
        }
        return blnRes;
    }
    
    private boolean obtenerEgresoIngresoConf(int x) {
        boolean blnRes = true;
        arlConRegPreEmpEgr = new ArrayList();
        arlConRegPreEmpEgr.add(INT_ARL_CODEMP, intCodEmpGenVen);//EGRESO 206 DESDE DONDE SALE LA MERCADERIA
        arlConRegPreEmpEgr.add(INT_ARL_CODLOC, intCodLocGenVen);
        arlConRegPreEmpEgr.add(INT_ARL_CODTIPDOC, intCodTipDocGenVen);  // EL EGRESO intCodTipDocGenVen (206,228 FACVENE)
        arlConRegPreEmpEgr.add(INT_ARL_CODCLIPRV, intCodCliGenVen); // EMPRESA RELACIONADA 
        arlConRegPreEmpEgr.add(INT_ARL_CODBODORIDES, objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_BOD_GRP));  // BODEGA DE DONDE SE SACA LA MERCADERIA
        arlConRegPreEmpEgr.add(INT_ARL_CODITMGRP, intCodItmGru); // CODIGO DEL ITEM DE GRUPO
        arlConRegPreEmpEgr.add(INT_ARL_CODITMEMP, intCodItmGenVen); //ITEM CAMBIAR A OTRA EMPRESA
        arlConRegPreEmpEgr.add(INT_ARL_CODITMMAE, objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM_MAE));
        arlConRegPreEmpEgr.add(INT_ARL_CODALTITM, strCodAltItm); /* CODIGO DE LA BODEGA DONDE SE TOMA LA MERCADERIA */
        arlConRegPreEmpEgr.add(INT_ARL_NOMITM, strNomItm);
        arlConRegPreEmpEgr.add(INT_ARL_UNIMEDITM, strUniMedItm);
        arlConRegPreEmpEgr.add(INT_ARL_CODLETITM, strCodAltItm2);  // CODIGO ALTERNO 2 DEL ITEM
        Double dblCan = dblCanCan;
        arlConRegPreEmpEgr.add(INT_ARL_CANITM, (dblCan*-1));  // CANTIDAD POR TRANSFERENCIA
        arlConRegPreEmpEgr.add(INT_ARL_COSUNI, dblCosUni);  // COSTO UNITARIO
        arlConRegPreEmpEgr.add(INT_ARL_PREUNI, dblPreUni);  // PRECIO UNITARIO
        arlConRegPreEmpEgr.add(INT_ARL_PESUNI, dblPesItm);  // PESO DEL ITEM
        arlConRegPreEmpEgr.add(INT_ARL_IVACOMITM, "N");  // Iva
        arlConRegPreEmpEgr.add(INT_ARL_ESTINGEGRMERBOD, strMerIngEgrFisBod);  // ITEM EGRESA FISICAMENTE 
        arlConDatPreEmpEgr.add(arlConRegPreEmpEgr);
        arlDatHistoricoMovInvEgr.add(arlConRegPreEmpEgr);
        arlDatosItemEgreso = arlConDatPreEmpEgr;
        arlConDatPreInvEgrAux =arlConDatPreEmpEgr;
        //  INGRESO INGRESO GENERA COMPRA
        arlConRegPreEmpIng = new ArrayList();
        arlConRegPreEmpIng.add(INT_ARL_CODEMP, intCodEmpGenCom);
        arlConRegPreEmpIng.add(INT_ARL_CODLOC, intCodLocGenCom);
        arlConRegPreEmpIng.add(INT_ARL_CODTIPDOC, intCodTipDocGenCom);  // EL INGRESO intCodTipDocGenCom (205,2 FACCOM)
        arlConRegPreEmpIng.add(INT_ARL_CODCLIPRV, intCodCliGenCom); // EMPRESA RELACIONADA QUE ESTA GENERANDO EL MOVIMIENTO
        arlConRegPreEmpIng.add(INT_ARL_CODBODORIDES, objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_BOD_GRP_ING));  // BODEGA DE DONDE SE SACA LA MERCADERIA    
        arlConRegPreEmpIng.add(INT_ARL_CODITMGRP, intCodItmGru); // CODIGO DEL ITEM DE GRUPO
        arlConRegPreEmpIng.add(INT_ARL_CODITMEMP, intCodItmGenCom);
        arlConRegPreEmpIng.add(INT_ARL_CODITMMAE, objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM_MAE));
        arlConRegPreEmpIng.add(INT_ARL_CODALTITM, strCodAltItm); /* CODIGO DE LA BODEGA DONDE SE TOMA LA MERCADERIA */
        arlConRegPreEmpIng.add(INT_ARL_NOMITM, strNomItm);
        arlConRegPreEmpIng.add(INT_ARL_UNIMEDITM, strUniMedItm);
        arlConRegPreEmpIng.add(INT_ARL_CODLETITM, strCodAltItm2);  // CODIGO ALTERNO 2 DEL ITEM
        arlConRegPreEmpIng.add(INT_ARL_CANITM, dblCan);  // CANTIDAD POR TRANSFERENCIA
        arlConRegPreEmpIng.add(INT_ARL_COSUNI, dblCosUni);  // COSTO UNITARIO
        arlConRegPreEmpIng.add(INT_ARL_PREUNI, dblPreUni);  // PRECIO UNITARIO
        arlConRegPreEmpIng.add(INT_ARL_PESUNI, dblPesItm);  // PESO DEL ITEM
        arlConRegPreEmpIng.add(INT_ARL_IVACOMITM, "N");  // IVA
        arlConRegPreEmpIng.add(INT_ARL_ESTINGEGRMERBOD, strMerIngEgrFisBod);  // ITEM EGRESA FISICAMENTE 
        arlConDatPreEmpIng.add(arlConRegPreEmpIng);
        arlDatosItemIngreso = arlConDatPreEmpIng;
        arlConDatPreInvIngAux =arlConDatPreEmpIng;
        return blnRes;
    }
    /**
     * Esta función inserta el registro de confirmacion de cancelacion
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarRegDocConCanConf(int intCodDocMov, int intCoEmpMv, int intCoLocMv, int intCoTipDcMv, int intCoDocMv,Connection con) throws SQLException{
        boolean blnRes=false;
        ZafSegMovInv objSegMovInv = new ZafSegMovInv(objParSis, componente);
         
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        try {
            //con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            if(con!=null){
                stm = con.createStatement();
                String strTipDocCan;
                for (int i = 0; i < 2; i++) {
                    ///////////////////////////CONFIRMACION
                    if (i==0) {
                       strTipDocCan="I";
                       strTipMovCnfTot="I";
                       intCodTipDocCnf=276;//codigo quemado confirmacion de cancelacion 
                       //intCodBodEmp=objUti.getIntValueAt(arlConDatTraInv, x, INT_ARL_CODBODORIDES);
                       //intCodBodEmp=intCodBodDesSolTra;
                       intCodBodEmp=intCodBodegaMovimientoDes;
                    }else if (i==1) {
                       strTipDocCan="E";
                       strTipMovCnfTot="E";
                       intCodTipDocCnf=277;//codigo quemado confirmacion de cancelacion 
                       //intCodBodEmp=objUti.getIntValueAt(arlConDatTraInv, x, INT_ARL_CODBODORIDES);
                       //intCodBodEmp=intCodBodOrgSolTra;
                       intCodBodEmp=intCodBodegaMovimientoOrg;
                    }
                        intCodEmpDocCnf=objUti.getIntValueAt(arlDatItemMovInv, 0, INT_ARL_COD_EMP);
                        intCodLocDocCnf=intCodLocSolTra;
                        //intCodRegCnf=x;
                        intCodDocCnf=intCodDocMov;
                        
                        ///////////////////////////
                if(insertar_tbmCabIngEgrMerBodConf(con)){
                    if(insertar_tbmDetIngEgrMerBodConf(intCoEmpMv, intCoLocMv, intCoTipDcMv, intCoDocMv,con)){
                        if(actualizar_tbmCabTipDocConf(intCodEmpDocCnf, intCodLocDocCnf, intCodTipDocCnf,con)){
                            //if(actualizaCampos_tbmDetMovInvConf(intCoEmpMv, intCoLocMv, intCoTipDcMv, intCoDocMv)){
                                if(objSegMovInv.setSegMovInvCompra(con, intCodEmpSol1, intCodLocSol1, intCodTipDocSol1, intCodDocSol1, 7, intCodEmpDocCnf, intCodLocDocCnf, intCodTipDocCnf, intCodDocCnf)){
                                        //con.commit();
                                        blnRes=true;
                                }
                                else{blnRes=false;
                                    con.rollback();}
                            //}
                            //else{blnRes=false;
                              //  con.rollback();}
                        }
                        else{blnRes=false;
                            con.rollback();}
                    }
                    else{blnRes=false;
                        con.rollback();}
                }
                else{
                    blnRes=false;
                    con.rollback();
                }
            }
        }
            //con.close();
            //con=null;
        }
        catch (java.sql.SQLException e){
            
            try {
                blnRes=false;
                con.rollback();
                con.close();
                con=null;
            } catch (SQLException ex) {
                e.printStackTrace();
                objUti.mostrarMsgErr_F1(this, ex);
            }
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            con.rollback();
            con.close();
            con=null;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCabIngEgrMerBodConf(Connection con){
        int intUltReg, intUltRegNum;
        boolean blnRes=true;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        try {
            //con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                 
                 String strFecHorSer = objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
               // if(arlDatDocCnfSel.size()>0){
                    //Obtener el código del último registro.
                    strSQL="";
                    strSQL+="SELECT MAX(a1.co_doc)";
                    strSQL+=" FROM tbm_cabIngEgrMerBod AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmpDocCnf + "";
                    strSQL+="   AND a1.co_loc=" + intCodLocDocCnf + "";
                    strSQL+="   AND a1.co_tipdoc=" + intCodTipDocCnf + "";
                    System.out.println("insertar_tbmCabIngEgrMerBod: " + strSQL);
                    intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intUltReg==-1)
                        return false;
                    intUltReg++;
                    intCodDocCnf=intUltReg;
                    strSQL="";
                    strSQL+="SELECT MAX(a1.ne_numdoc)";
                    strSQL+=" FROM tbm_cabIngEgrMerBod AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmpDocCnf + "";
                    strSQL+="   AND a1.co_loc=" + intCodLocDocCnf + "";
                    strSQL+="   AND a1.co_tipdoc=" + intCodTipDocCnf + "";
                   // strSQL+="   AND a1.co_doc=" + intUltReg + "";
                    System.out.println("insertar_tbmCabIngEgrMerBod: " + strSQL);
                    intUltRegNum=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intUltReg==-1)
                        return false;
                    intUltRegNum++;
                    
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_cabIngEgrMerBod(";
                    strSQL+=" co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod,";
                    strSQL+=" co_locrel, co_tipdocrel, co_docrel, co_locrelguirem, co_tipdocrelguirem, co_docrelguirem,";
                    strSQL+=" co_mnu, st_imp, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, tx_tipcon,";
                    strSQL+=" tx_fordes, tx_fordescliret, co_veh, co_cho, tx_idetra, tx_nomtra, tx_plavehtra";
                    strSQL+=" )";
                    strSQL+=" ( SELECT";
                    strSQL+=" " + intCodEmpDocCnf + "";//co_emp
                    strSQL+=", " + intCodLocDocCnf + "";//co_loc
                    strSQL+=", " + intCodTipDocCnf + "";//co_tipdoc
                    strSQL+=", " + intUltReg + "";//co_doc
                    strSQL+=", '" + strFecHorSer + "'";//fe_doc
                    strSQL+=", " + intUltRegNum + "";//ne_numdoc
                    strSQL+=", " + intCodBodEmp + "";//co_bod
//                    if( strTipMovCnfTot.equals("I") ){//Ingreso
//                        strSQL+=", " + intCodLocDocCnf + "";//co_locrel
//                        strSQL+=", " + intCodTipDocCnf + "";//co_tipdocrel
//                        strSQL+=", " + intCodDocCnf + "";//co_docrel
//                        strSQL+=", Null";//co_locrelguirem
//                        strSQL+=", Null";//co_tipdocrelguirem
//                        strSQL+=", Null";//co_docrelguirem
//                    }
//                    else if( strTipMovCnfTot.equals("E") ){//Egreso
//                        strSQL+=", Null";//co_locrel
//                        strSQL+=", Null";//co_tipdocrel
//                        strSQL+=", Null";//co_docrel
//                        strSQL+=", " + intCodLocDocCnf + "";//co_locrelguirem   
//                        strSQL+=", " + intCodTipDocCnf + "";//co_tipdocrelguirem
//                        strSQL+=", " + intCodDocCnf + "";//co_docrelguirem
//                    }
                        strSQL+=", Null";//co_locrel
                        strSQL+=", Null";//co_tipdocrel
                        strSQL+=", Null";//co_docrel
                        strSQL+=", Null";//co_locrelguirem   
                        strSQL+=", Null";//co_tipdocrelguirem
                        strSQL+=", Null";//co_docrelguirem
                    
                    strSQL+=", " + objParSis.getCodigoMenu() + "";//co_mnu
                    strSQL+=", 'N'";//st_imp
                    strSQL+=", "+objUti.codificar(txtObs1.getText());//tx_obs1
                    strSQL+=", "+objUti.codificar(txtObs2.getText());//tx_obs2
                    strSQL+=", 'A'";//st_reg
                    strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                    strSQL+=", '" + strAux + "'";//fe_ing
                    strSQL+=", Null";//fe_ultmod
                    strSQL+=", " + objParSis.getCodigoUsuario() + "";//co_usring
                    strSQL+=", Null";//co_usrmod
                    strSQL+=", Null";//tx_tipcon
                    strSQL+=", Null";//tx_fordes
                    strSQL+=", Null";//tx_fordescliret
                        strSQL+=", Null";//co_veh
                        strSQL+=", Null";//co_cho
                        strSQL+=", Null";//tx_idetra
                        strSQL+=", Null";//tx_nomtra
                        strSQL+=", Null";//tx_plavehtra
                    strSQL+=" );";
                    System.out.println("tbm_cabIngEgrMerBod: " + strSQL);
                    stm.executeUpdate(strSQL);
                    //con.commit();
                    stm.close();
                    stm=null;
                //}
            }
        }
        catch (java.sql.SQLException e){
            try {
                blnRes=false;
                con.rollback();
                con.close();
                con=null;
                e.printStackTrace();
                objUti.mostrarMsgErr_F1(this, e);
            } catch (SQLException ex) {
                ex.printStackTrace();
                objUti.mostrarMsgErr_F1(this, ex);
            }
        }
        catch (Exception e){
            try {
                blnRes=false;
                con.rollback();
                con.close();
                con=null;
                e.printStackTrace();
                objUti.mostrarMsgErr_F1(this, e);
            } catch (SQLException ex) {
                ex.printStackTrace();
                objUti.mostrarMsgErr_F1(this, ex);
            }
        }
        return blnRes;
    }
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @param tipoOperacion  <HTML> Tipo de confirmación que se debe realizar
     *              <BR>  0: Actualiza en campo "nd_can"
     *              <BR>  1: Actualiza en campo "nd_cannunrec"
     *              <BR>  2: Actualiza en campo "nd_canmalest"
     *          </HTML>
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmDetIngEgrMerBodConf(int intCodEmpMv, int intCodLocMv, int intCodTipDocMv, int intCodDocMv,Connection con){
        boolean blnRes=true;
        String strSQLIns="";
        int j=0;
        BigDecimal bdeCanUsr=BigDecimal.ZERO;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        int intNumFil;
        try {
            //con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            //stm = con.createStatement();
            intNumFil = objTblMod.getRowCountTrue();
             if (con!=null){
                 stm=con.createStatement();
                for (int x = 0; x < arlDatItemMovInv.size(); x++) {
                    for (int z = 0; z < intNumFil; z++) {
                     if (objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM)==Integer.valueOf(objTblMod.getValueAt(z, INT_TBL_DAT_COD_ITM).toString())) {
                       if (Double.parseDouble(objTblMod.getValueAt(z, INT_TBL_DAT_NUM_CAN_CAN).toString()) > 0) {
                           dblCanCan=Double.parseDouble(objTblMod.getValueAt(z, INT_TBL_DAT_NUM_CAN_CAN).toString());
                           j++;
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_detingegrmerbod(";
                        strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel, co_docrel,co_regrel,";
                        strSQL+="   co_locrelguirem, co_tipdocrelguirem, co_docrelguirem, co_regrelguirem,";
                        strSQL+="   co_itm, co_bod, nd_can, tx_obs1,";
                        strSQL+="   nd_canmalest, st_solproreamermalest, st_tipproreamermalest, nd_canmalestaut,";
                        strSQL+="   nd_canmalestden, tx_obsautproreamermalest, fe_ingautproreamermalest,";
                        strSQL+="   co_usringautproreamermalest, tx_comingautproreamermalest, nd_cannunrec,";
                        strSQL+="   st_solproreamernunrec, nd_cannunrecaut, nd_cannunrecden, tx_obsautproreamernunrec,";
                        strSQL+="   fe_ingautproreamernunrec, co_usringautproreamernunrec, tx_comingautproreamernunrec";
                        strSQL+=")";
                        strSQL+="( SELECT";
                        strSQL+=" " + intCodEmpDocCnf + "";//co_emp
                        strSQL+="," + intCodLocDocCnf + "";//co_loc
                        strSQL+="," + intCodTipDocCnf + "";//co_tipdoc
                        strSQL+="," + intCodDocCnf                            + "";//co_doc
                        strSQL+="," + j + "";//co_reg

                        if( strTipMovCnfTot.equals("I") ){//Ingreso
//                            strSQL+=", " + intCodLocDocCnf + "";//co_locrel
//                            strSQL+=", " + intCodTipDocCnf + "";//co_tipdocrel
//                            strSQL+=", " + intCodDocCnf + "";//co_docrel
//                            strSQL+=", " + intCodRegCnf +"";//co_regrel
                             strSQL+=", Null";//co_locrel
                            strSQL+=", Null";//co_tipdocrel
                            strSQL+=", Null";//co_docrel
                            strSQL+=", Null";//co_regrel
                            strSQL+=", Null";//co_locrelguirem
                            strSQL+=", Null";//co_tipdocrelguirem
                            strSQL+=", Null";//co_docrelguirem
                            strSQL+=", Null";//co_regrelguirem
                        }
                        else if( strTipMovCnfTot.equals("E") ){//Egreso
                            strSQL+=", Null";//co_locrel
                            strSQL+=", null";//co_tipdocrel
                            strSQL+=", Null";//co_docrel
                            strSQL+=", Null";//co_regrel                        
//                            strSQL+=", " + intCodLocDocCnf + "";//co_locrelguirem
//                            strSQL+=", " + intCodTipDocCnf + "";//co_tipdocrelguirem
//                            strSQL+=", " + intCodDocCnf + "";//co_docrelguirem
//                            strSQL+=", " + intCodRegCnf + "";//co_regrelguirem
                            strSQL+=", Null";//co_locrelguirem
                            strSQL+=", Null";//co_tipdocrelguirem
                            strSQL+=", Null";//co_docrelguirem
                            strSQL+=", Null";//co_regrelguirem
                        }
                        strSQL+="," + objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM) + "";//co_itm
                        strSQL+="," + intCodBodEmp + "";//co_bod

                        //bdeCanUsr=objUti.redondearBigDecimal((objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_CAN_COM)==null?"0":(objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_CAN_COM).toString().equals("")?"0":objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_CAN_COM).toString())), objParSis.getDecimalesMostrar());
                        bdeCanUsr=BigDecimal.valueOf(dblCanCan);
                        if( strTipMovCnfTot.equals("I") ){//Ingreso
                            strSQL+="," + bdeCanUsr  + "";//nd_can
                        }
                        else if( strTipMovCnfTot.equals("E") ){//Egreso
                            strSQL+=", (-" + bdeCanUsr + ")";//nd_can
                        }

                        strSQL+=", "+objUti.codificar(txtObs1.getText());//tx_obs1
                        strSQL+=", Null";//nd_canmalest
                        strSQL+=", Null";//st_solproreamermalest
                        strSQL+=", Null";//st_tipproreamermalest
                        strSQL+=", Null";//nd_canmalestaut
                        strSQL+=", Null";//nd_canmalestden
                        strSQL+=", Null";//tx_obsautproreamermalest
                        strSQL+=", Null";//fe_ingautproreamermalest
                        strSQL+=", Null";//co_usringautproreamermalest
                        strSQL+=", Null";//tx_comingautproreamermalest

                        if( strTipMovCnfTot.equals("I") ){//Ingreso
                            strSQL+=", "+bdeCanUsr;//nd_cannunrec
                        }
                        else if( strTipMovCnfTot.equals("E") ){//Egreso
                            strSQL+=", (-"+bdeCanUsr+")";//nd_cannunrec
                        }

                        strSQL+=", Null";//st_solproreamernunrec
                        strSQL+=", Null";//nd_cannunrecaut
                        strSQL+=", Null";//nd_cannunrecden
                        strSQL+=", Null";//tx_obsautproreamernunrec
                        strSQL+=", Null";//fe_ingautproreamernunrec
                        strSQL+=", Null";//co_usringautproreamernunrec
                        strSQL+=", Null";//tx_comingautproreamernunrec
                        strSQL+=")";
                        strSQL+=";";
                        strSQLIns+=strSQL;                
                       }else{
                           break;
                       }
                     }                                               
                    }
                                                                   
//                    if (consultaItemsMovInvConf(x, intCodEmpMv, intCodLocMv, intCodTipDocMv, intCodDocMv)) {
//                        if (dblCanCan>0.00) {
                                    
//                        }
//                    }
            }
                        System.out.println("insertar_tbmDetIngEgrMerBodmp: " + strSQLIns);
                        stm.executeUpdate(strSQLIns);
                        stm.close();
                        stm=null;  
        }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     /**
     * Esta función actualiza el campo "ne_ultDoc" de la tabla "tbm_cabTipDoc".
     * @return true: Si se pudo actualizar el campo.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabTipDocConf(int intCodEmp, int intCodLoc,int intCodTipDoc,Connection con){
        boolean blnRes=true;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        try {
            //con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stm = con.createStatement();
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_tipDoc=" + intCodTipDoc;
                System.out.println("actualizarCabTipDoc: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean obtenerTransferenciaConf(int x, int intCodEmpOrg, int intCodEmpDes, String strTipProReaEmpRel) {
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        boolean blnRes = true;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            strSql = " SELECT *   \n";
            strSql += " FROM ( \n";
            strSql += "     SELECT a.co_emp , a.co_bod, a.co_itm  \n";
            strSql += "            , CASE WHEN var.tx_tipunimed = 'E' THEN  TRUNC(a.nd_canDis) ELSE a.nd_canDis END AS nd_canDis \n";
            strSql += "     FROM tbm_invbod as a \n";
            strSql += "     INNER JOIN tbr_bodEmpBodGrp as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod)  \n";
            strSql += "     INNER JOIN tbm_inv AS inv ON(inv.co_emp=a.co_emp AND inv.co_itm=a.co_itm )   \n";
            strSql += "     LEFT JOIN tbm_var AS var ON(var.co_reg=inv.co_uni ) \n";
            strSql += "     WHERE a.co_emp=" + objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_COD_EMP) + " AND a.co_itm= " + objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM);
            strSql += "           AND a.co_bod != " + objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_COD_BOD_GRP_ING) + " ";
            strSql += "           AND ( a1.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " AND a1.co_bodGrp= " + objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_COD_BOD_GRP) + "   ) \n";
            strSql += " ) AS x ";
            //strSql += "  \n WHERE nd_canDis > 0  ";
//                        System.out.println("TRANSFERENCIA \n" + strSql); 
            rstLoc = stmLoc.executeQuery(strSql);
            
            while (rstLoc.next()) {
                //if (obtenerDatosItem(Integer.parseInt(objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM_MAE)), intCodEmpOrg)) {
                    if (obtenerTipoDocumentos(1, intCodEmpOrg, intCodEmpDes, strTipProReaEmpRel,objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_TIP_DOC))) {
//                                    if(obtenerDatosTransferencia(rstLoc)){
                        //INGRSEAR AL CONTENEDOR PARA TRANSFERENCIAS
                                        /* PRIMERO EL EGRESO SOLICITO INGRIK */
                        arlConRegTraInv = new ArrayList();
                        int intCodigoBodegaEgresoTrans = intCodBodegaMovimientoOrg;//Integer.parseInt(objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_COD_BOD_GRP_ING));  // BODEGA DE EGRESO
                        //***********************************88 REVISAR
                        int intCodigoBodegaIngresoTrans = intCodBodegaMovimientoDes;//Integer.parseInt(objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_COD_BOD_GRP)); // BODEGA DE INGRESO //INT_ARL_COD_BOD_GRP_ING
                        //************************************888888
                        double dblCan = dblCanCan;
                        arlConRegTraInv.add(INT_ARL_CODEMP, objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_COD_EMP));
                        arlConRegTraInv.add(INT_ARL_CODLOC, intCodLocTra);     // CODIGO DEL LOCAL  
                        arlConRegTraInv.add(INT_ARL_CODTIPDOC, intCodTipDocGenTra); // CODIGO DE TIPO DE DOCUMENTO
                        arlConRegTraInv.add(INT_ARL_CODCLIPRV, null); // DEBERIA SER NULL
                        arlConRegTraInv.add(INT_ARL_CODBODORIDES, intCodigoBodegaEgresoTrans);  // BODEGA EN DONDE SE EGRESA LA MERCADERIA
                        arlConRegTraInv.add(INT_ARL_CODITMGRP, intCodItmGru);  // CODIGO DEL ITEM DE GRUPO
                        arlConRegTraInv.add(INT_ARL_CODITMEMP, objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM));
                        arlConRegTraInv.add(INT_ARL_CODITMMAE, objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM_MAE));
                        arlConRegTraInv.add(INT_ARL_CODALTITM, strCodAltItm); /* CODIGO DE LA BODEGA DONDE SE TOMA LA MERCADERIA */

                        arlConRegTraInv.add(INT_ARL_NOMITM, strNomItm);
                        arlConRegTraInv.add(INT_ARL_UNIMEDITM, strUniMedItm);
                        arlConRegTraInv.add(INT_ARL_CODLETITM, strCodAltItm2);  // CODIGO ALTERNO 2 DEL ITEM
                        //arlConRegTraInv.add(INT_ARL_CANITM, (-1 * Double.parseDouble(objUti.getStringValueAt(arlDat, x, INT_ARL_CAN_COM))));  // CANTIDAD POR TRANSFERENCIA EGRESA
                        arlConRegTraInv.add(INT_ARL_CANITM, (-1 * dblCan));  // CANTIDAD POR TRANSFERENCIA EGRESA
                        arlConRegTraInv.add(INT_ARL_COSUNI, dblCosUni);  // COSTO UNITARIO
                        arlConRegTraInv.add(INT_ARL_PREUNI, dblPreUni);  // PRECIO UNITARIO                           
                        arlConRegTraInv.add(INT_ARL_PESUNI, dblPesItm);  // PESO DEL ITEM
                        arlConRegTraInv.add(INT_ARL_IVACOMITM, "N");  //TRANSFERENCIAS NO GENERA IVA
                        arlConRegTraInv.add(INT_ARL_ESTINGEGRMERBOD, strMerIngEgrFisBod);  // ITEM EGRESA FISICAMENTE 
                        arlConDatTraInvStkIng.add(arlConRegTraInv);
                        arlConDatTraInv.add(arlConRegTraInv);

                        // ==============  INGRESO!!!! 19/MaYO/2016 (ASI DEBE SER PIDIO INGRIK )
                        arlConRegTraInv = new ArrayList();
                        arlConRegTraInv.add(INT_ARL_CODEMP, objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_COD_EMP));
                        arlConRegTraInv.add(INT_ARL_CODLOC, intCodLocTra);     // CODIGO DEL LOCAL  
                        arlConRegTraInv.add(INT_ARL_CODTIPDOC, intCodTipDocGenTra); // CODIGO DE TIPO DE DOCUMENTO
                        arlConRegTraInv.add(INT_ARL_CODCLIPRV, null); // DEBERIA SER NULL
                        arlConRegTraInv.add(INT_ARL_CODBODORIDES, intCodigoBodegaIngresoTrans);  // BODEGA EN DONDE SE INGRESA LA MERCADERIA
                        arlConRegTraInv.add(INT_ARL_CODITMGRP, intCodItmGru);  // CODIGO DEL ITEM DE GRUPO
                        arlConRegTraInv.add(INT_ARL_CODITMEMP, objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM));
                        arlConRegTraInv.add(INT_ARL_CODITMMAE, objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM_MAE));
                        arlConRegTraInv.add(INT_ARL_CODALTITM, strCodAltItm); /* CODIGO DE LA BODEGA DONDE SE TOMA LA MERCADERIA */

                        arlConRegTraInv.add(INT_ARL_NOMITM, strNomItm);
                        arlConRegTraInv.add(INT_ARL_UNIMEDITM, strUniMedItm);
                        arlConRegTraInv.add(INT_ARL_CODLETITM, strCodAltItm2);  // CODIGO ALTERNO 2 DEL ITEM
                        //arlConRegTraInv.add(INT_ARL_CANITM, (Double.parseDouble(objUti.getStringValueAt(arlDat, x, INT_ARL_CAN_COM))));  // CANTIDAD POR TRANSFERENCIA EGRESO
                        arlConRegTraInv.add(INT_ARL_CANITM, (dblCan));  // CANTIDAD POR TRANSFERENCIA EGRESO
                        arlConRegTraInv.add(INT_ARL_COSUNI, dblCosUni);  // COSTO UNITARIO
                        arlConRegTraInv.add(INT_ARL_PREUNI, dblPreUni);  // PRECIO UNITARIO                           
                        arlConRegTraInv.add(INT_ARL_PESUNI, dblPesItm);  // PESO DEL ITEM
                        arlConRegTraInv.add(INT_ARL_IVACOMITM, "N");  //TRANSFERENCIAS NO GENERA IVA
                        arlConRegTraInv.add(INT_ARL_ESTINGEGRMERBOD, strMerIngEgrFisBod);  // ITEM EGRESA FISICAMENTE 
                        arlConDatTraInvStkEgr.add(arlConRegTraInv);
                        arlConDatTraInv.add(arlConRegTraInv);
                        blnRes = true;
                    } else {
                        blnRes = false;
                    }
//                } else {
//                    strErr = "Problemas con el Item:" + objUti.getStringValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM);
//                    System.out.println(strErr);
//                    blnRes = false;
//                }
            }

        } catch (SQLException ex) {
            blnRes=false;
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        }
        return blnRes;
    }
    private boolean actualizarEgrIngMovInvConf(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc) {
        boolean blnRes = false;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSql="";
        Connection conn;
        try {
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) {
                stmLoc=conn.createStatement();    
                strSQL="";
                strSQL+=" SELECT * FROM TBM_DETMOVINV ";
                strSQL+=" WHERE co_emp= "+intCodEmp;
                strSQL+=" AND co_loc= "+ intCodLoc;
                strSQL+=" AND co_tipdoc= "+intCodTipDoc;
                strSQL+=" AND co_doc= "+ intCodDoc;
                int intNumFil = objTblMod.getRowCountTrue();    
                String strCodAltItmRv="";
                for (int x = 0; x < arlDatItemMovInv.size(); x++) {
                    for (int z = 0; z < intNumFil; z++) {
                        if (objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM) == Integer.valueOf(objTblMod.getValueAt(z, INT_TBL_DAT_COD_ITM).toString())) {
                            if (Double.parseDouble(objTblMod.getValueAt(z, INT_TBL_DAT_NUM_CAN_CAN).toString()) > 0) {
                                if (strCodAltItmRv.equals("")) {
                                    strCodAltItmRv = "'" + objTblMod.getValueAt(z, INT_TBL_DAT_COD_ALT).toString() + "'";
                                } else {
                                    strCodAltItmRv += ",'" + objTblMod.getValueAt(z, INT_TBL_DAT_COD_ALT).toString() + "'";
                                }
                            }
                        }
                    }
                }
                strSQL += " AND tx_codalt in (" + strCodAltItmRv + ")";
                
                rstLoc=stmLoc.executeQuery(strSQL);
                double douEgrTemp=0.00;
                while(rstLoc.next()){
                    double douEgr=rstLoc.getDouble("nd_canegrbod");
                    double douIng=rstLoc.getDouble("nd_caningbod");
                    if (douEgr<0) {
                        for (int z = 0; z < intNumFil; z++) {
                                if (rstLoc.getString("tx_codalt").equals(objTblMod.getValueAt(z, INT_TBL_DAT_COD_ALT).toString())) {
                                    
                                    if (Double.parseDouble(objTblMod.getValueAt(z, INT_TBL_DAT_NUM_CAN_CAN).toString()) > 0) {
                                        douEgr=Double.valueOf(objTblMod.getValueAt(z, INT_TBL_DAT_NUM_CAN_CAN).toString())*-1;
                                    }
                                }
                            }
                        strSql+=" UPDATE tbm_detmovinv SET nd_canegrbod=nd_canegrbod+"+douEgr*(-1)+", nd_cannunrec=case when nd_cannunrec is null then 0 else nd_cannunrec end + (nd_cancon-("+douEgr+"))*-1"//+rstLoc.getDouble("nd_cancon")*(-1)
                          + "  WHERE  co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and co_reg= "+rstLoc.getInt("co_reg");                
                        douEgrTemp=douEgr;
                    
                    }
                    
                    if (douIng>0) {
                        strSql+=" UPDATE tbm_detmovinv SET nd_caningbod=nd_caningbod+"+douEgrTemp+", nd_cannunrec=case when nd_cannunrec is null then 0 else nd_cannunrec end + "+ douEgrTemp*-1 //+rstLoc.getDouble("nd_cancon")*(-1)
                          + "  WHERE  co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and co_reg= "+rstLoc.getInt("co_reg");                
                        douEgrTemp=0.00;
                    }
                    
                    
                    strSql+=" ;UPDATE tbm_detmovinv SET nd_canpen=nd_can-nd_cancon-nd_cannunrec WHERE  co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and co_reg= "+rstLoc.getInt("co_reg") + ";";
                }
                stmLoc.executeUpdate(strSql);
                blnRes = true;
            }
            if (blnRes) {
                conn.commit();
            }
            stmLoc.close();
            stmLoc=null;
            rstLoc.close();
            rstLoc=null;
            conn.close();
            conn=null;
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    private boolean verificaItms(){
        int intNumFil;
        intNumFil = objTblMod.getRowCountTrue();
        for (int i = 0; i < vecDatCon.size(); i++) {
            for (int j = 0; j < intNumFil; j++) {
                 if (objUti.getStringValueAt(vecDatCon, i, INT_TBL_DAT_COD_ALT).equals(objTblMod.getValueAt(j, INT_TBL_DAT_COD_ALT).toString())) {
                     if (objUti.getDoubleValueAt(vecDatCon, i, INT_TBL_DAT_NUM_CAN_TOT_CAN)!=Double.valueOf(objTblMod.getValueAt(j, INT_TBL_DAT_NUM_CAN_TOT_CAN).toString())) {
                         return false;
                     }
                     if (objUti.getDoubleValueAt(vecDatCon, i, INT_TBL_DAT_NUM_CAN_TOT_CON)!=Double.valueOf(objTblMod.getValueAt(j, INT_TBL_DAT_NUM_CAN_TOT_CON).toString())) {
                         return false;
                     }
                     if (objUti.getDoubleValueAt(vecDatCon, i, INT_TBL_DAT_NUM_CAN_PEN)!=Double.valueOf(objTblMod.getValueAt(j, INT_TBL_DAT_NUM_CAN_PEN).toString())) {
                         return false;
                     }
                }
            }
           
        }
        return true;
    }
    private boolean validacionCompraVenta() {
        String strTipRoReaEmpRel = "";
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSQL="";
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            strSQL = " ";
            strSQL += "  select tx_tipproreaemprel\n";
            strSQL += "  from tbm_emp \n";
            strSQL += "  where co_emp=" + objParSis.getCodigoEmpresa();
            rstLoc = stmLoc.executeQuery(strSQL);
            if (rstLoc.next()) {
                strTipRoReaEmpRel = rstLoc.getString("tx_tipproreaemprel");
            } 
            if (strTipRoReaEmpRel.equals("C")) {
               blnRes= false;
            }else{
                blnRes=true;
            }
            rstLoc.close();
            rstLoc = null;
            conLoc.close();
            stmLoc.close();
            conLoc = null;
            stmLoc = null;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    //Este metodo se lo usa cuando la mercaderia pasa por dos procesos el de bodega y luego a despacho cuando aun se encuentra en bodega se puede cancelar.
    private boolean actualizarDesEntCliMovInvConf(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc) {
        boolean blnRes = false;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSql="";
        Connection conn;
        try {
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) {
                stmLoc=conn.createStatement();    
                strSQL="";
                strSQL+=" SELECT * FROM TBM_DETMOVINV ";
                strSQL+=" WHERE co_emp= "+intCodEmp;
                strSQL+=" AND co_loc= "+ intCodLoc;
                strSQL+=" AND co_tipdoc= "+intCodTipDoc;
                strSQL+=" AND co_doc= "+ intCodDoc;
                int intNumFil = objTblMod.getRowCountTrue();    
                String strCodAltItmRv="";
                for (int x = 0; x < arlDatItemMovInv.size(); x++) {
                    for (int z = 0; z < intNumFil; z++) {
                        if (objUti.getIntValueAt(arlDatItemMovInv, x, INT_ARL_COD_ITM) == Integer.valueOf(objTblMod.getValueAt(z, INT_TBL_DAT_COD_ITM).toString())) {
                            if (Double.parseDouble(objTblMod.getValueAt(z, INT_TBL_DAT_NUM_CAN_CAN).toString()) > 0) {
                                if (strCodAltItmRv.equals("")) {
                                    strCodAltItmRv = "'" + objTblMod.getValueAt(z, INT_TBL_DAT_COD_ALT).toString() + "'";
                                } else {
                                    strCodAltItmRv += ",'" + objTblMod.getValueAt(z, INT_TBL_DAT_COD_ALT).toString() + "'";
                                }
                            }
                        }
                    }
                }
                strSQL += " AND tx_codalt in (" + strCodAltItmRv + ")";
                
                rstLoc=stmLoc.executeQuery(strSQL);
                double douEgrTemp=0.00;
                while(rstLoc.next()){
                    double douEgr=rstLoc.getDouble("nd_candesentcli");
                    double douIng=rstLoc.getDouble("nd_caningbod");
                    if (douEgr<0) {
                        for (int z = 0; z < intNumFil; z++) {
                                if (rstLoc.getString("tx_codalt").equals(objTblMod.getValueAt(z, INT_TBL_DAT_COD_ALT).toString())) {
                                    
                                    if (Double.parseDouble(objTblMod.getValueAt(z, INT_TBL_DAT_NUM_CAN_CAN).toString()) > 0) {
                                        douEgr=Double.valueOf(objTblMod.getValueAt(z, INT_TBL_DAT_NUM_CAN_CAN).toString())*-1;
                                    }
                                }
                            }
                        strSql+=" UPDATE tbm_detmovinv SET nd_candesentcli=nd_candesentcli+"+douEgr*(-1)+", nd_cannunrec=case when nd_cannunrec is null then 0 else nd_cannunrec end + (nd_cancon-("+douEgr+"))*-1"//+rstLoc.getDouble("nd_cancon")*(-1)
                          + "  WHERE  co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and co_reg= "+rstLoc.getInt("co_reg");                
                        douEgrTemp=douEgr;
                    
                    }
                    
                    if (douIng>0) {
                        strSql+=" UPDATE tbm_detmovinv SET nd_caningbod=nd_caningbod+"+douEgrTemp+", nd_cannunrec=case when nd_cannunrec is null then 0 else nd_cannunrec end + "+ douEgrTemp*-1 //+rstLoc.getDouble("nd_cancon")*(-1)
                          + "  WHERE  co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and co_reg= "+rstLoc.getInt("co_reg");                
                        douEgrTemp=0.00;
                    }
                    
                    
                    strSql+=" ;UPDATE tbm_detmovinv SET nd_canpen=nd_can-nd_cancon-nd_cannunrec WHERE  co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and co_reg= "+rstLoc.getInt("co_reg") + ";";
                }
                stmLoc.executeUpdate(strSql);
                blnRes = true;
            }
            if (blnRes) {
                conn.commit();
            }
            stmLoc.close();
            stmLoc=null;
            rstLoc.close();
            rstLoc=null;
            conn.close();
            conn=null;
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
}
