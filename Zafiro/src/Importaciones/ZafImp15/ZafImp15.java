 /*
 * ZafImp15.java
 *
 * Created on 15 de Octubre del 2018
 */
package Importaciones.ZafImp15;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafParSis.ZafParSis; 
import Librerias.ZafPerUsr.ZafPerUsr; 
import Librerias.ZafUtil.ZafUtil; 
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;  
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;  
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;  
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;   
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus; 
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd; 
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen; 
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.Period;
import java.util.Vector;
import java.util.ArrayList;
import javax.swing.JScrollBar;
import javax.swing.ListSelectionModel;

/**
 *
 * @author  Ingrid Lino
 */
public class ZafImp15 extends javax.swing.JInternalFrame
{
    /*  ================================================================================================================  */    
    /*  =================================================  Constantes  =================================================  */    
    /*  ================================================================================================================  */    
    
    /* ===============================  Reporte: Pedidos en Tránsito =============================== */
    //Constantes: Columnas del JTable: TblFix - Pedidos en Tránsito
    static final int INT_TBL_DAT_FIX_LIN=0;
    static final int INT_TBL_DAT_FIX_INS_PED=1;
    static final int INT_TBL_DAT_FIX_COD_EMP=2;
    static final int INT_TBL_DAT_FIX_COD_LOC=3;
    static final int INT_TBL_DAT_FIX_COD_TIP_DOC=4;
    static final int INT_TBL_DAT_FIX_DES_COR_TIP_DOC=5;
    static final int INT_TBL_DAT_FIX_DES_LAR_TIP_DOC=6;
    static final int INT_TBL_DAT_FIX_COD_DOC=7; 
    static final int INT_TBL_DAT_FIX_NUM_DOC=8;
    static final int INT_TBL_DAT_FIX_NUM_PED=9;
    static final int INT_TBL_DAT_FIX_EST_COL_FIL=10;         //Estado que establece color de las filas. N=Ninguno; A=Amarillo; R=Rojo.
    static final int INT_TBL_DAT_FIX_ORD_FIL=11;             //Orden de fila
    
    //Constantes: Columnas del JTable: TblDat - Pedidos en Tránsito 
    static final int INT_TBL_DAT_LIN=0;
    static final int INT_TBL_DAT_INS_PED=1;                  //Para saber que instancia del pedido es: NOTPED, PEDEMB, INIMPO.
    static final int INT_TBL_DAT_COD_EMP=2;
    static final int INT_TBL_DAT_COD_LOC=3;
    static final int INT_TBL_DAT_COD_TIP_DOC=4;
    static final int INT_TBL_DAT_DES_COR_TIP_DOC=5;
    static final int INT_TBL_DAT_DES_LAR_TIP_DOC=6;
    static final int INT_TBL_DAT_COD_DOC=7; 
    static final int INT_TBL_DAT_NUM_DOC=8;
    static final int INT_TBL_DAT_NUM_PED=9;
    static final int INT_TBL_DAT_TIP_NOT_PED=10;
    static final int INT_TBL_DAT_COD_IMP=11;
    static final int INT_TBL_DAT_NOM_IMP=12;
    static final int INT_TBL_DAT_COD_EXP=13;
    static final int INT_TBL_DAT_NOM_EXP=14;
    static final int INT_TBL_DAT_FEC_PRE_IMP=15;
    static final int INT_TBL_DAT_FEC_COL_PED=16;
    static final int INT_TBL_DAT_FEC_EMB_EST=17;             //Fecha de Embarque Estimada
    static final int INT_TBL_DAT_FEC_EMB_REA=18;             //Fecha de Embarque Real.
    static final int INT_TBL_DAT_FEC_PUE=19;
    static final int INT_TBL_DAT_FEC_ARR_BOD=20;
    static final int INT_TBL_DAT_VAL_TOT_FAC=21;
    static final int INT_TBL_DAT_VAL_TOT_ABO=22;
    static final int INT_TBL_DAT_VAL_TOT_PEN=23;
    static final int INT_TBL_DAT_BUT=24;
    static final int INT_TBL_DAT_EST_PAG_ARA=25;
    static final int INT_TBL_DAT_EST_COL_FIL=26;              //Estado que establece color de las filas. N=Ninguno; A=Amarillo; R=Rojo.
    static final int INT_TBL_DAT_ORD_FIL=27;                  //Orden de la fila
    
    
    /* =============================== Reporte: Panel de Control de Procesos =============================== */
    //Constantes: Columnas del JTable: TblFix - Control Procesos
    static final int INT_TBL_RPT_FIX_LIN=0;
    static final int INT_TBL_RPT_FIX_COD_EMP=1;
    static final int INT_TBL_RPT_FIX_COD_LOC=2;
    static final int INT_TBL_RPT_FIX_COD_TIP_DOC=3;
    static final int INT_TBL_RPT_FIX_COD_DOC=4;
    static final int INT_TBL_RPT_FIX_NUM_DOC=5;
    static final int INT_TBL_RPT_FIX_NUM_PED=6;    
    
    //Constantes: Columnas del JTable: TblDat - Control Procesos
    //Pedido
    static final int INT_TBL_RPT_LIN=0; 
    static final int INT_TBL_RPT_COD_EMP=1; 
    static final int INT_TBL_RPT_COD_LOC=2; 
    static final int INT_TBL_RPT_COD_TIP_DOC=3; 
    static final int INT_TBL_RPT_COD_DOC=4; 
    static final int INT_TBL_RPT_NUM_DOC=5; 
    static final int INT_TBL_RPT_NUM_PED=6;                   //Alfabetico: Número de Orden
    static final int INT_TBL_RPT_PED_ASO=7;                   //Alfabetico: Número de Pedido Previo Asociado a la Orden
    //PK - Pedidos Asociados
    static final int INT_TBL_RPT_COD_EMP_NOTPEDPRE=8;
    static final int INT_TBL_RPT_COD_LOC_NOTPEDPRE=9;
    static final int INT_TBL_RPT_COD_TIP_DOC_NOTPEDPRE=10;
    static final int INT_TBL_RPT_COD_DOC_NOTPEDPRE=11;    
    static final int INT_TBL_RPT_COD_EMP_NOTPED=12;
    static final int INT_TBL_RPT_COD_LOC_NOTPED=13;
    static final int INT_TBL_RPT_COD_TIP_DOC_NOTPED=14;
    static final int INT_TBL_RPT_COD_DOC_NOTPED=15;        
    static final int INT_TBL_RPT_COD_EMP_PEDEMB=16;
    static final int INT_TBL_RPT_COD_LOC_PEDEMB=17;
    static final int INT_TBL_RPT_COD_TIP_DOC_PEDEMB=18;
    static final int INT_TBL_RPT_COD_DOC_PEDEMB=19;    
    static final int INT_TBL_RPT_COD_EMP_INIMPO=20;
    static final int INT_TBL_RPT_COD_LOC_INIMPO=21;
    static final int INT_TBL_RPT_COD_TIP_DOC_INIMPO=22;
    static final int INT_TBL_RPT_COD_DOC_INIMPO=23;    
    //Verificación: La verificación sólo se realizará cuando exista fecha de recepción de factura.
    static final int INT_TBL_RPT_VAL_PED=24;                 //Numerico: Valor del Pedido
    static final int INT_TBL_RPT_VAL_PAG_TOT=25;             //Numerico: Valor Total Pagado al Exterior
    static final int INT_TBL_RPT_CHK_VER_PAG=26;             //Check: Verificación de Valor de Pago. Coincida Valor Pagado Total coincida el valor del pedido.
    //Datos de Orden
    static final int INT_TBL_RPT_NOM_RUB_MAT=27;              //Alfanumerico: Descripción del Material
    static final int INT_TBL_RPT_COD_IMP=28;
    static final int INT_TBL_RPT_NOM_IMP=29;
    static final int INT_TBL_RPT_COD_DIR_IND=30;              //Auxiliar: Directo/Indirecto
    static final int INT_TBL_RPT_DES_DIR_IND=31;              //Alfabetico: Directo/Indirecto
    static final int INT_TBL_RPT_DES_TIP_PED=32;              //Alfabetico: Incoterms
    static final int INT_TBL_RPT_DES_PTO_EMB=33;              //Alfabetico: Puerto de Embarque
    static final int INT_TBL_RPT_COD_PRV=34;                  //Auxiliar: Código Proveedor
    static final int INT_TBL_RPT_NOM_PRV=35;                  //Alfabetico: Proveedor
    static final int INT_TBL_RPT_COD_FOR_PAG=36;              //Auxiliar: Forma de Pago
    static final int INT_TBL_RPT_DES_FOR_PAG=37;              //Alfabetico: Forma de Pago     
    //Seguimiento de Orden
    static final int INT_TBL_RPT_FEC_PRO_EST=38;              //Fecha: Producción Estimada
    static final int INT_TBL_RPT_FEC_SEG_ORD=39;              //Fecha: Seguimiento Orden
    static final int INT_TBL_RPT_DES_REQ_ORD=40;              //Alfanumerico: Descripción del requerimiento
    static final int INT_TBL_RPT_FEC_PRE_IMP=41;              //Fecha: Presupuesto de Importación
    static final int INT_TBL_RPT_FEC_COL_PED=42;              //Fecha: Orden Colocada - Fecha Colocación del Pedido Previo
    static final int INT_TBL_RPT_FEC_REC_PRO=43;              //Fecha: Recibida PI - Recepción Proforma
    static final int INT_TBL_RPT_FEC_CON_PRO=44;              //Fecha: Enviada - Confiirmación Proforma
    static final int INT_TBL_RPT_FEC_DOC_NPP=45;
    static final int INT_TBL_RPT_FEC_NOT_PED_IND=46;          //Fecha: Notificación de Pedidos Indirectos
    static final int INT_TBL_RPT_FEC_DOC_NOT_PED=47;          //Fecha: Ingreso Costos a Zafiro - Fecha Documento NP
    //Anticipo 1 - Proveedor
    static final int INT_TBL_RPT_FEC_SOL_ANT_PRV_1=48;        //Fecha: Solicitud de anticipo a proveedor 1
    static final int INT_TBL_RPT_VAL_SOL_ANT_PRV_1=49;        //Númerico: Valor solicitado de anticipo a proveedor 1
    static final int INT_TBL_RPT_FEC_PAG_ANT_PRV_1=50;        //Fecha: Pago de anticipo a proveedor 1
    static final int INT_TBL_RPT_TXT_OBS_ANT_PRV_1=51;        //Alfanumerico: Observación de anticipo a proveedor 1
    //Anticipo 2 - Proveedor
    static final int INT_TBL_RPT_FEC_SOL_ANT_PRV_2=52;        //Fecha: Solicitud de anticipo a proveedor 2
    static final int INT_TBL_RPT_VAL_SOL_ANT_PRV_2=53;        //Numerico: Valor solicitado de anticipo a proveedor 2
    static final int INT_TBL_RPT_FEC_PAG_ANT_PRV_2=54;        //Fecha: Pago de anticipo a proveedor 2
    static final int INT_TBL_RPT_TXT_OBS_ANT_PRV_2=55;        //Alfanumerico: Observación de anticipo a proveedor 2
    //Proceso de Embarque
    static final int INT_TBL_RPT_FEC_REC_FAC_PRV=56;          //Fecha: Recibida C.I.+ P/L - Recepción Factura
    static final int INT_TBL_RPT_FEC_SOL_AGE=57;              //Fecha: Solicitud Agente
    static final int INT_TBL_RPT_FEC_ASI_AGE=58;              //Fecha: Asignación Agente
    static final int INT_TBL_RPT_DES_PED_CON=59;              //Alfanumerico: Consolidación
    static final int INT_TBL_RPT_FEC_EMB_EST=60;              //Fecha: Embarque - Estimada 
    static final int INT_TBL_RPT_FEC_EMB_BOK=61;              //Fecha: Embarque - Booking
    static final int INT_TBL_RPT_FEC_EMB_BL=62;               //Fecha: Embarque - BL
    static final int INT_TBL_RPT_FEC_PUE=63;                  //Fecha: Puerto - (E.T.A.)
    static final int INT_TBL_RPT_FEC_ARR_BOD=64;              //Fecha: Arribo a Bodega
    //Fechas de Pago Estimadas
    static final int INT_TBL_RPT_FEC_BAS_PAG_1=65;
    static final int INT_TBL_RPT_FEC_BAS_PAG_2=66;
    static final int INT_TBL_RPT_FEC_BAS_PAG_3=67;
    static final int INT_TBL_RPT_FEC_BAS_PAG_4=68;
    //Fechas de Pago BL
    static final int INT_TBL_RPT_FEC_PAG_REA_1=69;
    static final int INT_TBL_RPT_FEC_PAG_REA_2=70;
    static final int INT_TBL_RPT_FEC_PAG_REA_3=71;
    static final int INT_TBL_RPT_FEC_PAG_REA_4=72;    
    //Fechas de Saldos a Proveedor
    static final int INT_TBL_RPT_FEC_SOL_SAL_PRV=73;          //Fecha: Solicitud de saldo a proveedor
    static final int INT_TBL_RPT_VAL_SOL_SAL_PRV=74;          //Numerico: Valor solicitado de saldo a proveedor
    static final int INT_TBL_RPT_FEC_PAG_SAL_PRV=75;          //Fecha: Pago de saldo a proveedor
    static final int INT_TBL_RPT_TXT_OBS_SAL_PRV=76;          //Alfanumerico: Observación de saldo a proveedor
    //Fechas Vencimiento 1 - Pedidos Llegados
    static final int INT_TBL_RPT_FEC_EST_VEN_1=77;            //Fecha: Estimada de vencimiento 1
    static final int INT_TBL_RPT_VAL_EST_VEN_1=78;            //Numerico: Valor estimado de vencimiento 1
    static final int INT_TBL_RPT_FEC_PAG_VEN_1=79;            //Fecha: Pago de vencimiento 1
    static final int INT_TBL_RPT_VAL_PAG_VEN_1=80;            //Numerico: Valor pagado de vencimiento 1
    //Fechas Vencimiento 2 - Pedidos Llegados
    static final int INT_TBL_RPT_FEC_EST_VEN_2=81;            //Fecha: Estimada de vencimiento 2
    static final int INT_TBL_RPT_VAL_EST_VEN_2=82;            //Numerico: Valor estimado de vencimiento 2
    static final int INT_TBL_RPT_FEC_PAG_VEN_2=83;            //Fecha: Pago de vencimiento 2
    static final int INT_TBL_RPT_VAL_PAG_VEN_2=84;            //Numerico: Valor pagado de vencimiento 2
    //Fechas Vencimiento 3 - Pedidos Llegados
    static final int INT_TBL_RPT_FEC_EST_VEN_3=85;            //Fecha: Estimada de vencimiento 3
    static final int INT_TBL_RPT_VAL_EST_VEN_3=86;            //Numerico: Valor estimado de vencimiento 3
    static final int INT_TBL_RPT_FEC_PAG_VEN_3=87;            //Fecha: Pago de vencimiento 3
    static final int INT_TBL_RPT_VAL_PAG_VEN_3=88;            //Numerico: Valor pagado de vencimiento 3
    //Comprobación y Notas de Crédito
    static final int INT_TBL_RPT_VAL_NOT_CRE=89;              //Numerico: Notas de Crédito
    static final int INT_TBL_RPT_TXT_OBS_COM=90;                  //Alfanumerico: Observación - Comprobación de Valores
    
    //Constantes: ArrayList
    private ArrayList arlRegCarPag, arlDatCarPag; 
    final int INT_ARL_CAR_PAG_COD_CAR_PAG=0;
    final int INT_ARL_CAR_PAG_NOM_CAR_PAG=1;
    final int INT_ARL_CAR_PAG_TIP_CAR_PAG=2;
    final int INT_ARL_CAR_PAG_COL_CAR_PAG=3;
    
    private ArrayList arlRegTotCarPag, arlDatTotCarPag;
    final int INT_ARL_TOT_CAR_PAG_TIP=0;
    final int INT_ARL_TOT_CAR_PAG_NOM=1;
    final int INT_ARL_TOT_CAR_PAG_COL=2;
    final int INT_ARL_TOT_CAR_PAG_SUM=3;  
    
    final int INT_COD_TIP_DOC_PED_IMP=310;   //Tipo de Documento de ORDEN DEL PEDIDO DE IMPORTACIÓN.

    /*  ================================================================================================================  */    
    /*  =================================================  Variables  ==================================================  */    
    /*  ================================================================================================================  */        
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab, objTblFilCabFix;
    private ZafTblMod objTblModDat, objTblModFix;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblNumEst, objTblCelRenLblEst;             //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblNumRpt, objTblCelRenLblValPed; 
    private ZafTblCelRenLbl objTblCelRenLblFix;                                 //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblFec, objTblCelRenLblNumDin;          //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblCarPag, objTblCelRenLblTotCarPag;    //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxtDiaCre, objTblCelEdiTxtPorCre, objTblCelEdiTxtValPag;  //Render: Presentar JTextField en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxtNumPed, objTblCelEdiTxtNumPedFix, objTblCelEdiTxtValPed, objTblCelEdiTxtValNum;       //Editor: JTextField en celda.
    private ZafTblEdi objTblEdiNumPed, objTblEdiValPed;                         //Editor: Editor del JTable.
    private ZafTblCelRenChk objTblCelRenChkFecEmbRea;                           //Render: Presentar JCheckBox en JTable.
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafMouMotAda objMouMotAda, objMouMotAdaFix;                         //ToolTipText en TableHeader.
    private ZafMouMotAdaRpt objMouMotAdaRpt; 
    private ZafTblPopMnu objTblPopMnu, objTblPopMnuFix;                         //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus, objTblBusFix;                                  //Editor de búsqueda.
    private ZafTblOrd objTblOrd, objTblOrd2, objTblOrdFix;                      //JTable de ordenamiento.
    private ZafDtePckEdi objDtePckEdiFecEmb, objDtePckEdiFecPue, objDtePckEdiFecVenRea;
    private ZafDtePckEdi objDtePckEdiFecRpt;                                    //Fechas: Control de Procesos
    private ZafHisTblBasDat objHisTblBasDat;                                    //Histórico    
    private ZafDocLis objDocLis; 
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    private ZafVenCon vcoEmp, vcoExp;
    private ZafImp objZafImp;
    private ZafSelFec objSelFecDoc;
    private ZafTblCelRenChk objTblCelRenChk;
    
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoPedAso, objTblCelEdiTxtVcoEmpImp, objTblCelEdiTxtVcoTipPed, objTblCelEdiTxtVcoPrvExt;
    private ZafVenCon vcoPedAso, vcoEmpImp, vcoTipPed, vcoPrvExt;      
    
    private ZafPerUsr objPerUsr;    
    private Vector vecDat, vecCab, vecReg, vecAux;
    private Vector vecDatFix, vecCabFix, vecRegFix, vecAuxFix; 
    private boolean blnCon;                                                     //true: Continua la ejecución del hilo.
    private boolean blnHayCam;
    private java.util.Date datFecAux;                                           //Auxiliar: Para almacenar fechas.
    private JScrollBar barDatHor, barFixHor, barDatVer, barFixVer;

    private int intNumColEst; //Numero de columnas fijas
    private int intNumColAddCarPag, intNumColIniCarPag, intNumColFinCarPag;
    private int intNumColAddTotCarPag, intNumColIniTotCarPag, intNumColFinTotCarPag;
    private int intNumColAddEstReg, intNumColIniEstReg, intNumColFinEstReg;     //Estado del item a mostrarse
    private int intNumGrpAddFecVenEst, intNumColFecVenEst, intNumColIniFecVenEst, intNumColFinFecVenEst;
    private int intNumGrpAddFecVenRea, intNumColFecVenRea, intNumColIniFecVenRea, intNumColFinFecVenRea; 

    private static int intCalEditAfter=0, intCalEditBefore=1;
    
    private String strCodEmp, strNomEmp, strCodExp,  strNomExp;
    private String strFormatoDatePicker = "y-m-d";    
    private String strTipCarPag="'A','F','G','I'";
    private String strCodExpHavelock="15";
    private String strSQL, strAux; 
    
    private String strVer=" v0.4.6"; 
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafImp15(ZafParSis obj) 
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
                if (!configurarFrm())
                    exitForm();
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }
            
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }  
      
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Título de la ventana
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objHisTblBasDat=new ZafHisTblBasDat();
            objDocLis=new ZafDocLis();
            
            //Inicializar ArrayList
            arlDatCarPag=new ArrayList();
            arlDatTotCarPag=new ArrayList();
            
            //Configurar ZafSelFec:
            objSelFecDoc=new ZafSelFec();
            panFecDoc.add(objSelFecDoc);
            objSelFecDoc.setBounds(4, 0, 472, 68);
            objSelFecDoc.setCheckBoxVisible(true);
            objSelFecDoc.setCheckBoxChecked(false);
            objSelFecDoc.setTitulo("Fecha del documento");       

            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                configurarVenConEmp();
                txtCodImp.setEditable(true);
                txtNomImp.setEditable(true);
                butImp.setEnabled(true);
            }
            else{
                lblEmpImp.setVisible(false);
                txtCodImp.setVisible(false);
                txtNomImp.setVisible(false);
                butImp.setVisible(false);
            } 
            
            //chkRptConPro.setVisible(false); 
            
            //Ocultar búqueda de estado de documento.
            //lblEstDoc.setVisible(false); cboEstDoc.setVisible(false); cboEstDoc.setSelectedIndex(1);
            
//            chkMosISD.setVisible(false);
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            objPerUsr=new ZafPerUsr(objParSis);  
            if (!objPerUsr.isOpcionEnabled(3242))  //Consultar 
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(4495))  //Guardar
            {
                butGua.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(3243))  //Cerrar
            {
                butCer.setVisible(false);
            }     
            
            
            //Ocultar Panel FIX
            panRptFix.setVisible(false);

            //Configurar los JTables.
            configurarTblFix(); 
            configurarTblDat();
            
            //Configurar Ventanas de consulta
            configurarVenConExp();
            configurarVenConPedAso();
            configurarVenConEmpImp();
            configurarVenConTipPed();
            configurarVenConPrvExt();
        }
        catch(Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura el JTable "tblFix".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblFix()
    {
        boolean blnRes=true;
        try
        {
            //Configurar tamaño panel de datos.
            //sppRpt.setDividerLocation(185);
            sppRpt.setDividerLocation(0);
            
            //Configurar JTable: Establecer el modelo.
            vecDatFix=new Vector();    //Almacena los datos
            vecCabFix=new Vector();    //Almacena las cabeceras
            vecCabFix.clear();
            vecCabFix.add(INT_TBL_DAT_FIX_LIN,"");
            vecCabFix.add(INT_TBL_DAT_FIX_INS_PED,"Ins.Ped.");
            vecCabFix.add(INT_TBL_DAT_FIX_COD_EMP,"Cód.Emp.");
            vecCabFix.add(INT_TBL_DAT_FIX_COD_LOC,"Cód.Loc.");
            vecCabFix.add(INT_TBL_DAT_FIX_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCabFix.add(INT_TBL_DAT_FIX_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCabFix.add(INT_TBL_DAT_FIX_DES_LAR_TIP_DOC,"Tip.Doc.");
            vecCabFix.add(INT_TBL_DAT_FIX_COD_DOC,"Cód.Doc.");
            vecCabFix.add(INT_TBL_DAT_FIX_NUM_DOC,"Núm.Doc.");
            vecCabFix.add(INT_TBL_DAT_FIX_NUM_PED,"Núm.Ped.");
            vecCabFix.add(INT_TBL_DAT_FIX_EST_COL_FIL,"");
            vecCabFix.add(INT_TBL_DAT_FIX_ORD_FIL,"Orden");
            
            objTblModFix=new ZafTblMod();
            objTblModFix.setHeader(vecCabFix);
            tblFix.setModel(objTblModFix);

            //Configurar JTable: Establecer tipo de selección.
            tblFix.setRowSelectionAllowed(true);
            tblFix.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuFix=new ZafTblPopMnu(tblFix);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblFix.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblFix.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_FIX_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_FIX_INS_PED).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FIX_DES_COR_TIP_DOC).setPreferredWidth(54);
            tcmAux.getColumn(INT_TBL_DAT_FIX_DES_LAR_TIP_DOC).setPreferredWidth(15);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FIX_NUM_DOC).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_DAT_FIX_NUM_PED).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_DAT_FIX_EST_COL_FIL).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_FIX_ORD_FIL).setPreferredWidth(20);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblFix.getTableHeader().setReorderingAllowed(false); 
                  
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_LIN, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_INS_PED, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COD_EMP, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COD_LOC, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COD_TIP_DOC, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COD_DOC, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_EST_COL_FIL, tblFix);
            //objTblModFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_ORD_FIL, tblFix);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            //Configurar JTable: Establecer los listener para el TableHeader.
//            tblFix.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
//                public void mouseClicked(java.awt.event.MouseEvent evt) {
//                    tblFixOrdMouseClicked(evt);
//                }
//            });            
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            //objTblOrdFix=new ZafTblOrd(tblFix);       

            //Configurar JTable: Agrupamiento de columnas
            ZafTblHeaGrp objTblHeaGrpDatPed=(ZafTblHeaGrp)tblFix.getTableHeader();
            objTblHeaGrpDatPed.setHeight(16*3);
            ZafTblHeaColGrp objTblHeaColGrpDatPed=null;
            objTblHeaColGrpDatPed=new ZafTblHeaColGrp("Datos del Pedido");
            objTblHeaColGrpDatPed.setHeight(16); 
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_DAT_FIX_LIN));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_DAT_FIX_INS_PED));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COD_EMP));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COD_LOC));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COD_TIP_DOC));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_DAT_FIX_DES_COR_TIP_DOC));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_DAT_FIX_DES_LAR_TIP_DOC));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COD_DOC));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_DAT_FIX_NUM_DOC));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_DAT_FIX_NUM_PED));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_DAT_FIX_EST_COL_FIL));  
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_DAT_FIX_ORD_FIL)); 
            objTblHeaGrpDatPed.addColumnGroup(objTblHeaColGrpDatPed);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblFix=new ZafTblCelRenLbl();
            //objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_FIX_INS_PED).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_EMP).setCellRenderer(objTblCelRenLblFix); 
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_LOC).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_TIP_DOC).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_DAT_FIX_DES_COR_TIP_DOC).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_DAT_FIX_DES_LAR_TIP_DOC).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_DOC).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_DAT_FIX_NUM_DOC).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_DAT_FIX_NUM_PED).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_DAT_FIX_EST_COL_FIL).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_DAT_FIX_ORD_FIL).setCellRenderer(objTblCelRenLblFix);
            objTblCelRenLblFix.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColRoj, colFonColYel;
                int intFil = -1;
                String strColFil="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLblFix.getRowRender();
                    strColFil=objTblModFix.getValueAt(intFil, INT_TBL_DAT_FIX_EST_COL_FIL)== null ? "N": objTblModFix.getValueAt(intFil, INT_TBL_DAT_FIX_EST_COL_FIL).toString();
                    colFonColRoj=new java.awt.Color(255, 0, 0);
                    colFonColYel=new java.awt.Color(255, 255, 0);
                    if(strColFil.equals("A")){ 
                        objTblCelRenLblFix.setBackground(colFonColYel);
                    }
                    else if(strColFil.equals("R")){
                        objTblCelRenLblFix.setBackground(colFonColRoj);
                    }
                    else {
                        objTblCelRenLblFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });       
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaFix=new ZafMouMotAda();
            tblFix.getTableHeader().addMouseMotionListener(objMouMotAdaFix);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBusFix=new ZafTblBus(tblFix);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabFix=new ZafTblFilCab(tblFix);
            tcmAux.getColumn(INT_TBL_DAT_FIX_LIN).setCellRenderer(objTblFilCabFix);            
            
            //Adicionar el listener que controla el redimensionamiento de las columnas.
            ZafTblColModLisTotFix objTblColModLisFix=new ZafTblColModLisTotFix();
            tblFix.getColumnModel().addColumnModelListener(objTblColModLisFix);
            //ZafTblColModLisTotFix objTblColModLisTotFix=new ZafTblColModLisTotFix(); 
            //tblTotFix.getColumnModel().addColumnModelListener(objTblColModLisTotFix);    
            
            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            //spnFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            spnFix.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            
            //Controla que la fila seleccionada en una tabla(tblFix) tambien se seleccione en la otra tabla(tblDat)
            ListSelectionModel modDat = tblFix.getSelectionModel();
            tblDat.setSelectionModel(modDat); 
            //Controla que la fila seleccionada en una tabla(tblDat) tambien se seleccione en la otra tabla(tblFix)
            ListSelectionModel modFix = tblDat.getSelectionModel(); 
            tblFix.setSelectionModel(modFix);            

            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
            barFixHor=spnFix.getHorizontalScrollBar();
            barDatHor=spnDat.getHorizontalScrollBar();
            
            barFixVer=spnFix.getVerticalScrollBar();
            barDatVer=spnDat.getVerticalScrollBar();            
            
            //PARA DESPLAZAMIENTOS DE CELDAS
            //HORIZONTAL
            barDatHor.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barFixHor.setValue(evt.getValue());
                }
            });      

            //VERTICAL
            barDatVer.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barFixVer.setValue(evt.getValue());
                }
            });           
                        
            //objTblModFix.setModoOperacion(objTblModFix.INT_TBL_EDI);
            
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
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_INS_PED,"Ins.Ped.");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_PED,"Núm.Ped.");
            vecCab.add(INT_TBL_DAT_TIP_NOT_PED,"Tip.Not.Ped.");
            vecCab.add(INT_TBL_DAT_COD_IMP,"Cód.Imp.");
            vecCab.add(INT_TBL_DAT_NOM_IMP,"Importador");
            vecCab.add(INT_TBL_DAT_COD_EXP,"Cód.Exp.");
            vecCab.add(INT_TBL_DAT_NOM_EXP,"Exportador");
            vecCab.add(INT_TBL_DAT_FEC_PRE_IMP,"Fec.Pre.");
            vecCab.add(INT_TBL_DAT_FEC_COL_PED,"Fec.Col.");
            vecCab.add(INT_TBL_DAT_FEC_EMB_EST,"Fec.Emb.Est."); 
            vecCab.add(INT_TBL_DAT_FEC_EMB_REA,"Fec.BL");
            vecCab.add(INT_TBL_DAT_FEC_PUE,"Fec.Pue.");
            vecCab.add(INT_TBL_DAT_FEC_ARR_BOD,"Fec.Bod.");
            vecCab.add(INT_TBL_DAT_VAL_TOT_FAC,"Val.Tot.Fac.");
            vecCab.add(INT_TBL_DAT_VAL_TOT_ABO,"Val.Abo.");
            vecCab.add(INT_TBL_DAT_VAL_TOT_PEN,"Val.Pen.");
            vecCab.add(INT_TBL_DAT_BUT,"");
            vecCab.add(INT_TBL_DAT_EST_PAG_ARA,"Est.Pag.Ara."); 
            vecCab.add(INT_TBL_DAT_EST_COL_FIL,"Est.Col.Fil."); 
            vecCab.add(INT_TBL_DAT_ORD_FIL,"Orden");             
            
            objTblModDat=new ZafTblMod();
            objTblModDat.setHeader(vecCab);
            tblDat.setModel(objTblModDat);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_INS_PED).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(54);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(15);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_DAT_TIP_NOT_PED).setPreferredWidth(53);
            tcmAux.getColumn(INT_TBL_DAT_COD_IMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_IMP).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_DAT_COD_EXP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EXP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_FEC_PRE_IMP).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_FEC_COL_PED).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMB_EST).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMB_REA).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_FEC_PUE).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_FEC_ARR_BOD).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_FAC).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_ABO).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_PEN).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_DAT_BUT).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_EST_PAG_ARA).setPreferredWidth(25);   
            tcmAux.getColumn(INT_TBL_DAT_EST_COL_FIL).setPreferredWidth(25);  
            tcmAux.getColumn(INT_TBL_DAT_ORD_FIL).setPreferredWidth(25);  
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            //tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Agrupamiento de columnas            
            //Datos del Pedido
            ZafTblHeaGrp objTblHeaGrpDatPedDat=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpDatPedDat.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpDatPedDat=new ZafTblHeaColGrp("Datos del Pedido");
            objTblHeaColGrpDatPedDat.setHeight(16); 
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_LIN));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_INS_PED));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_COD_EMP));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_COD_LOC));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_COD_DOC));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_NUM_DOC));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_NUM_PED));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_TIP_NOT_PED));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_COD_IMP));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_NOM_IMP));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_COD_EXP));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_NOM_EXP));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_FEC_PRE_IMP));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_FEC_COL_PED));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_FEC_EMB_EST));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_FEC_EMB_REA));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_FEC_PUE));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_FEC_ARR_BOD));            
            objTblHeaGrpDatPedDat.addColumnGroup(objTblHeaColGrpDatPedDat);            
            
            //Valor Factura
            ZafTblHeaGrp objTblHeaGrpValFac=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpValFac.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColValFac=new ZafTblHeaColGrp("Pago al Exterior");
            objTblHeaColValFac.setHeight(16);
            objTblHeaColValFac.add(tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_FAC));
            objTblHeaColValFac.add(tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_ABO));
            objTblHeaColValFac.add(tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_PEN));
            objTblHeaGrpValFac.addColumnGroup(objTblHeaColValFac);

            //Configurar JTable: Ocultar columnas del sistema.
            //objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_LIN, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_INS_PED, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            //objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_DES_COR_TIP_DOC, tblDat);
            //objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_DES_LAR_TIP_DOC, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            //objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_NUM_DOC, tblDat);
            //objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_NUM_PED, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_IMP, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_EXP, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_EST_PAG_ARA, tblDat);   
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_EST_COL_FIL, tblDat); 
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_ORD_FIL, tblDat); 
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            //Configurar JTable: Establecer los listener para el TableHeader.
//            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
//                public void mouseClicked(java.awt.event.MouseEvent evt) {
//                    tblDatOrdMouseClicked(evt);
//                }
//            });
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);      
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            //objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_INS_PED).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setCellRenderer(objTblCelRenLbl); 
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TIP_NOT_PED).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_EXP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EXP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_PRE_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_COL_PED).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMB_EST).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMB_REA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_PUE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_ARR_BOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColRoj, colFonColYel;
                int intFil = -1;
                String strColFil="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLbl.getRowRender();
                    strColFil=objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL)== null ? "N": objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL).toString();
                    colFonColRoj=new java.awt.Color(255, 0, 0);
                    colFonColYel=new java.awt.Color(255, 255, 0);
                    if(strColFil.equals("A")){ 
                        objTblCelRenLbl.setBackground(colFonColYel);
                    }
                    else if(strColFil.equals("R")){
                        objTblCelRenLbl.setBackground(colFonColRoj);
                    }
                    else {
                        objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });                 
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblNumEst=new ZafTblCelRenLbl();
            objTblCelRenLblNumEst.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNumEst.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNumEst.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_FAC).setCellRenderer(objTblCelRenLblNumEst);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_ABO).setCellRenderer(objTblCelRenLblNumEst);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_PEN).setCellRenderer(objTblCelRenLblNumEst);
            objTblCelRenLblNumEst.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//                java.awt.Color colFonColRoj, colFonColYel;
//                int intFil = -1;
//                String strColFil="";
//                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
//                    intFil = objTblCelRenLblNumEst.getRowRender();
//                    strColFil=objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL)== null ? "N": objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL).toString();
//                    colFonColRoj=new java.awt.Color(255, 0, 0);
//                    colFonColYel=new java.awt.Color(255, 255, 0);
//                    if(strColFil.equals("A")){ 
//                        objTblCelRenLblNumEst.setBackground(colFonColYel);
//                    }
//                    else if(strColFil.equals("R")){
//                        objTblCelRenLblNumEst.setBackground(colFonColRoj);
//                    }
//                    else {
//                        objTblCelRenLblNumEst.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                    }
//                }
            });            
            
            //Establecer JTable: Establecer columnas DataPicker
            objDtePckEdiFecPue = new ZafDtePckEdi(strFormatoDatePicker);
            tcmAux.getColumn(INT_TBL_DAT_FEC_PUE).setCellEditor(objDtePckEdiFecPue); 
            tcmAux.getColumn(INT_TBL_DAT_FEC_ARR_BOD).setCellEditor(objDtePckEdiFecPue); 
            objDtePckEdiFecPue.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {   
                int intFil=-1;
                String strEstCiePedEmb=""; 
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil = tblDat.getSelectedRow();
                    /**
                     * Validar actualización de fecha de embarque
                     * <BR>Pedido Embarcado: Se podrá actualizar la fecha de embarque, mientras no este cerrado el pedido embarcado.
                     * <BR>LSC - 15/Abr/2019: El pedido embarcado consta como cerrado, cuando se paga el arancel del pedido (tbm_cabPedEmbImp.st_ciePagAra)
                     * <BR>Nota de Pedido: Siempre se podrá actualizar el mes de embarque, debido a que no ha sido realizado el embarque.
                     */
                    strEstCiePedEmb=objUti.parseString(objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_PAG_ARA));
                    if(strEstCiePedEmb.equals("S")){
                        objDtePckEdiFecPue.setCancelarEdicion(true); 
                        mostrarMsgInf("<HTML>No puede editarse las fechas de un pedido cerrado.</HTML>");
                    }
                    else{
                        objDtePckEdiFecPue.setCancelarEdicion(false);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    /* Se vuelve a calcular las fechas de vencimiento, de acuerdo a la nueva fecha de embarque.*/
                    calcularGrupoColumnsFecVenEstPorFila(intFil);
                }
            });                

            //Establecer JTable: Establecer columnas DataPicker
            objDtePckEdiFecEmb = new ZafDtePckEdi(strFormatoDatePicker);  
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMB_EST).setCellEditor(objDtePckEdiFecEmb);    
            objDtePckEdiFecEmb.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {   
                int intFil=-1;
                String strEstCiePedEmb=""; 
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil = tblDat.getSelectedRow();
                    /**
                     * Validar actualización de fecha de embarque
                     * <BR>Pedido Embarcado: Se podrá actualizar la fecha de embarque, mientras no este cerrado el pedido embarcado.
                     * <BR>LSC - 15/Abr/2019: El pedido embarcado consta como cerrado, cuando se paga el arancel del pedido (tbm_cabPedEmbImp.st_ciePagAra)
                     * <BR>Nota de Pedido: Siempre se podrá actualizar el mes de embarque, debido a que no ha sido realizado el embarque.
                     */
                    strEstCiePedEmb=objUti.parseString(objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_PAG_ARA));
                    if(strEstCiePedEmb.equals("S")){
                        objDtePckEdiFecEmb.setCancelarEdicion(true); 
                        mostrarMsgInf("<HTML>No puede cambiarse la fecha de embarque de un pedido cerrado.</HTML>");
                    }
                    else{
                        objDtePckEdiFecEmb.setCancelarEdicion(false);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    /* Se vuelve a calcular las fechas de vencimiento, de acuerdo a la nueva fecha de embarque.*/
                    calcularGrupoColumnsFecVenEstPorFila(intFil);
                }
            });    
            
            //PARA EL BOTON DE ANEXO UNO, QUE LLAMA A LA VENTANA DE NOTA DE PEDIDO
            objTblCelRenBut=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT).setCellRenderer(objTblCelRenBut);
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    mostrarFormularioPedido(intFilSel); 
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            objTblModDat.setModoOperacion(objTblModDat.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            
            intNumColEst=objTblModDat.getColumnCount();
        }
        catch(Exception e)
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
                case INT_TBL_DAT_LIN:
                    //strMsg="";
                    strMsg ="<html><h3 style='text-align:center;'>Colores utilizados en la tabla</h3><table border='1'>";
                    strMsg+="<tr><td><b>Fondo</b></td><td><b>Fuente</b></td><td><b>Observación</b></td></tr>";
                    strMsg+="<tr><td style='background-color: #FF0000'>&nbsp;</td><td>&nbsp;</td><td>Indica que \"Mes de Embarque es menor a la Fecha Actual\".</td></tr>"; //Color Rojo
                    strMsg+="<tr><td style='background-color: #FFFF00'>&nbsp;</td><td>&nbsp;</td><td>Indica \"Mes de Embarque estimado dentro de los 30 días\".</td></tr>"; //Color Amarillo
                    //strMsg+="<tr><td>&nbsp;</td><td style='background-color: #FF0000'>&nbsp;</td><td>Indica que son \"Items de ajustes tipo egreso\".</td></tr>"; //Color Rojo
                    strMsg+="</table><br></html>";
                    break;                
                case INT_TBL_DAT_INS_PED:
                    strMsg="Instancia del pedido";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número del documento";
                    break;
                case INT_TBL_DAT_NUM_PED:
                    strMsg="Número del pedido";
                    break;
                case INT_TBL_DAT_TIP_NOT_PED:
                    strMsg="Tipo de Nota de Pedido";
                    break;
                case INT_TBL_DAT_COD_IMP:
                    strMsg="Código del Importador";
                    break;
                case INT_TBL_DAT_NOM_IMP:
                    strMsg="Nombre del Importador";
                    break;
                case INT_TBL_DAT_COD_EXP:
                    strMsg="Código del Exportador";
                    break;
                case INT_TBL_DAT_NOM_EXP:
                    strMsg="Nombre del Exportador";
                    break;
                case INT_TBL_DAT_FEC_PRE_IMP:
                    strMsg="Fecha de presupuesto";
                    break;                     
                case INT_TBL_DAT_FEC_COL_PED:
                    strMsg="Fecha de colocación del pedido";
                    break;   
                case INT_TBL_DAT_FEC_EMB_EST:
                    strMsg="Fecha de Embarque Estimada";
                    break;                    
                case INT_TBL_DAT_FEC_EMB_REA:
                    strMsg="Fecha de Embarque BL";
                    break;
                case INT_TBL_DAT_FEC_PUE:
                    strMsg="Fecha del puerto";
                    break;                    
                case INT_TBL_DAT_FEC_ARR_BOD:
                    strMsg="Fecha de arribo de bodega";
                    break;
                case INT_TBL_DAT_VAL_TOT_FAC:
                    //strMsg="Total FOB / CFR";
                    strMsg="Valor Total Factura";
                    break;
                case INT_TBL_DAT_VAL_TOT_ABO:
                    strMsg="Valor Abonado";
                    break;
                case INT_TBL_DAT_VAL_TOT_PEN:
                    strMsg="Valor Pendiente"; 
                    break;                    
                case INT_TBL_DAT_BUT:
                    strMsg="Cargar formulario de Nota de Pedido/Pedido Embarcado";
                    break;                    
                case INT_TBL_DAT_EST_PAG_ARA:
                    strMsg="Estado del cierre del pago de arancel";
                    break;
                case INT_TBL_DAT_EST_COL_FIL:
                    strMsg="Estado del color para la fila";
                    break;  
                default:
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }    
    
    
    /**
     * Esta función configura el JTable "tblFix". -- Reporte de Peter
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblFixPanCon()
    {
        boolean blnRes=true;
        try
        {
            //sppRpt.setDividerLocation(90);
            sppRpt.setDividerLocation(0);
            
//            //Ocultar Panel FIX
//            if(chkRptConPro.isSelected()){
//                panRptFix.setVisible(true);
//            }
//            else{
//                panRptFix.setVisible(false);
//            }
            
            //Configurar JTable: Establecer el modelo.
            vecDatFix=new Vector();    //Almacena los datos
            vecCabFix=new Vector();    //Almacena las cabeceras
            vecCabFix.clear();
            vecCabFix.add(INT_TBL_RPT_FIX_LIN,"");
            vecCabFix.add(INT_TBL_RPT_FIX_COD_EMP,"Cód.Emp.");
            vecCabFix.add(INT_TBL_RPT_FIX_COD_LOC,"Cód.Loc.");
            vecCabFix.add(INT_TBL_RPT_FIX_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCabFix.add(INT_TBL_RPT_FIX_COD_DOC,"Cód.Doc.");
            vecCabFix.add(INT_TBL_RPT_FIX_NUM_DOC,"Núm.Doc.");
            vecCabFix.add(INT_TBL_RPT_FIX_NUM_PED,"No.Orden");
            
            objTblModFix=new ZafTblMod();
            objTblModFix.setHeader(vecCabFix);
            tblFix.setModel(objTblModFix);

            //Configurar JTable: Establecer tipo de selección.
            tblFix.setRowSelectionAllowed(true);
            tblFix.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblFix);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblFix.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblFix.getColumnModel();
            tcmAux.getColumn(INT_TBL_RPT_FIX_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_RPT_FIX_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_RPT_FIX_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_RPT_FIX_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_RPT_FIX_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_RPT_FIX_NUM_DOC).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_RPT_FIX_NUM_PED).setPreferredWidth(70);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblFix.getTableHeader().setReorderingAllowed(false);            
          
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModFix.addSystemHiddenColumn(INT_TBL_RPT_FIX_COD_EMP, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_RPT_FIX_COD_LOC, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_RPT_FIX_COD_TIP_DOC, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_RPT_FIX_COD_DOC, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_RPT_FIX_NUM_DOC, tblFix);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            //Configurar JTable: Establecer los listener para el TableHeader.
//            tblFix.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
//                public void mouseClicked(java.awt.event.MouseEvent evt) {
//                    tblFixOrdMouseClicked(evt);
//                }
//            }); 

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            //objTblOrdFix=new ZafTblOrd(tblFix);    
            
            //Configurar JTable: Agrupamiento de columnas
            ZafTblHeaGrp objTblHeaGrpDatPed=(ZafTblHeaGrp)tblFix.getTableHeader();
            objTblHeaGrpDatPed.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpDatPed=null;
            objTblHeaColGrpDatPed=new ZafTblHeaColGrp("Pedido");
            objTblHeaColGrpDatPed.setHeight(16);
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_FIX_LIN));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_FIX_COD_EMP));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_FIX_COD_LOC));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_FIX_COD_TIP_DOC));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_FIX_COD_DOC));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_FIX_NUM_DOC));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_FIX_NUM_PED));
            objTblHeaGrpDatPed.addColumnGroup(objTblHeaColGrpDatPed);            
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaFix=new ZafMouMotAda();
            tblFix.getTableHeader().addMouseMotionListener(objMouMotAdaFix);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBusFix=new ZafTblBus(tblFix);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabFix=new ZafTblFilCab(tblFix);
            tcmAux.getColumn(INT_TBL_RPT_FIX_LIN).setCellRenderer(objTblFilCabFix);
            
            //Adicionar el listener que controla el redimensionamiento de las columnas.
            ZafTblColModLisTotFix objTblColModLisFix=new ZafTblColModLisTotFix();
            tblFix.getColumnModel().addColumnModelListener(objTblColModLisFix);
            //ZafTblColModLisTotFix objTblColModLisTotFix=new ZafTblColModLisTotFix(); 
            //tblTotFix.getColumnModel().addColumnModelListener(objTblColModLisTotFix);    
            
            
            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            //spnFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            spnFix.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            
            
            //Controla que la fila seleccionada en una tabla(tblFix) tambien se seleccione en la otra tabla(tblDat)
            ListSelectionModel modDat = tblFix.getSelectionModel();
            tblDat.setSelectionModel(modDat); //se descomento
            //Controla que la fila seleccionada en una tabla(tblDat) tambien se seleccione en la otra tabla(tblFix)
            ListSelectionModel modFix = tblDat.getSelectionModel();
            tblFix.setSelectionModel(modFix);            

            
            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
            barFixHor=spnFix.getHorizontalScrollBar();
            barDatHor=spnDat.getHorizontalScrollBar();
            
            barFixVer=spnFix.getVerticalScrollBar();
            barDatVer=spnDat.getVerticalScrollBar();            
            
            //PARA DESPLAZAMIENTOS DE CELDAS
            //HORIZONTAL
            barDatHor.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barFixHor.setValue(evt.getValue());
                }
            });      

            //VERTICAL
            barDatVer.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barFixVer.setValue(evt.getValue());
                }
            });   
            
            //Configurar JTable: Establecer el modo de operación.
            objTblModFix.setModoOperacion(objTblModDat.INT_TBL_INS);     
            //objTblModFix.setModoOperacion(objTblModFix.INT_TBL_EDI); 
            
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
     * Esta función configura el JTable "tblDat". -- Reporte de Peter
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatPanCon()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_RPT_LIN,"");
            vecCab.add(INT_TBL_RPT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_RPT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_RPT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_RPT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_RPT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_RPT_NUM_PED,"No.Orden");
            
            vecCab.add(INT_TBL_RPT_PED_ASO,"Pedido Asociado");
            
            vecCab.add(INT_TBL_RPT_COD_EMP_NOTPEDPRE,"Cód.Emp.Not.Ped.Pre.");
            vecCab.add(INT_TBL_RPT_COD_LOC_NOTPEDPRE,"Cód.Loc.Not.Ped.Pre.");
            vecCab.add(INT_TBL_RPT_COD_TIP_DOC_NOTPEDPRE,"Cód.Tip.Doc.Not.Ped.Pre.");
            vecCab.add(INT_TBL_RPT_COD_DOC_NOTPEDPRE,"Cód.Doc.Not.Ped.Pre.");      
            
            vecCab.add(INT_TBL_RPT_COD_EMP_NOTPED,"Cód.Emp.Not.Ped.");
            vecCab.add(INT_TBL_RPT_COD_LOC_NOTPED,"Cód.Loc.Not.Ped.");
            vecCab.add(INT_TBL_RPT_COD_TIP_DOC_NOTPED,"Cód.Tip.Doc.Not.Ped.");
            vecCab.add(INT_TBL_RPT_COD_DOC_NOTPED,"Cód.Doc.Not.Ped.");                  

            vecCab.add(INT_TBL_RPT_COD_EMP_PEDEMB,"Cód.Emp.Ped.Emb.");
            vecCab.add(INT_TBL_RPT_COD_LOC_PEDEMB,"Cód.Loc.Ped.Emb.");
            vecCab.add(INT_TBL_RPT_COD_TIP_DOC_PEDEMB,"Cód.Tip.Doc.Ped.Emb.");
            vecCab.add(INT_TBL_RPT_COD_DOC_PEDEMB,"Cód.Doc.Ped.Emb.");                    
            
            vecCab.add(INT_TBL_RPT_COD_EMP_INIMPO,"Cód.Emp.Ing.Imp.");
            vecCab.add(INT_TBL_RPT_COD_LOC_INIMPO,"Cód.Loc.Ing.Imp.");
            vecCab.add(INT_TBL_RPT_COD_TIP_DOC_INIMPO,"Cód.Tip.Doc.Ing.Imp.");
            vecCab.add(INT_TBL_RPT_COD_DOC_INIMPO,"Cód.Doc.Ing.Imp.");      
            
            vecCab.add(INT_TBL_RPT_VAL_PED,"Valor P.I/C.I.");  
            vecCab.add(INT_TBL_RPT_VAL_PAG_TOT,"Val.Tot.Pag.");   
            vecCab.add(INT_TBL_RPT_CHK_VER_PAG,"");               
            
            vecCab.add(INT_TBL_RPT_NOM_RUB_MAT,"Material");  
            vecCab.add(INT_TBL_RPT_COD_IMP,"Cód.Imp.");                 
            vecCab.add(INT_TBL_RPT_NOM_IMP,"Importador");  
            vecCab.add(INT_TBL_RPT_COD_DIR_IND,"Cód.Tip.Imp.");  
            vecCab.add(INT_TBL_RPT_DES_DIR_IND,"Directo/Indirecto");  

            vecCab.add(INT_TBL_RPT_DES_TIP_PED,"Incoterms");  
            vecCab.add(INT_TBL_RPT_DES_PTO_EMB,"Puerto/Emb.");              
            vecCab.add(INT_TBL_RPT_COD_PRV,"Cód.Prv.Ext.");              
            vecCab.add(INT_TBL_RPT_NOM_PRV,"Provedor");              
            vecCab.add(INT_TBL_RPT_COD_FOR_PAG,"Cód.For.Pag.");              
            vecCab.add(INT_TBL_RPT_DES_FOR_PAG,"Forma de Pago");               
            
            vecCab.add(INT_TBL_RPT_FEC_PRO_EST,"Fec.Pro.Est.");  
            vecCab.add(INT_TBL_RPT_FEC_SEG_ORD,"Fec.Seg.Ord.");
            
            vecCab.add(INT_TBL_RPT_DES_REQ_ORD,"Requerimiento");  
            
            vecCab.add(INT_TBL_RPT_FEC_PRE_IMP,"Presupuesto");  
            vecCab.add(INT_TBL_RPT_FEC_COL_PED,"Orden Colocada");  
            
            vecCab.add(INT_TBL_RPT_FEC_REC_PRO,"Recepción P.I.");  
            vecCab.add(INT_TBL_RPT_FEC_CON_PRO,"Enviada");              
            
            vecCab.add(INT_TBL_RPT_FEC_DOC_NPP,"Ingreso NPP");
            
            vecCab.add(INT_TBL_RPT_FEC_NOT_PED_IND,"Notificación Ped.Ind.");
            vecCab.add(INT_TBL_RPT_FEC_DOC_NOT_PED,"Ingreso Costos a Zafiro");
            
            vecCab.add(INT_TBL_RPT_FEC_SOL_ANT_PRV_1,"Fec.Sol.Ant.1");
            vecCab.add(INT_TBL_RPT_VAL_SOL_ANT_PRV_1,"Val.Sol.Ant.1");
            vecCab.add(INT_TBL_RPT_FEC_PAG_ANT_PRV_1,"Fec.Pag.Ant.1");
            vecCab.add(INT_TBL_RPT_TXT_OBS_ANT_PRV_1,"Obs.Ant.1");
            
            vecCab.add(INT_TBL_RPT_FEC_SOL_ANT_PRV_2,"Fec.Sol.Ant.2");
            vecCab.add(INT_TBL_RPT_VAL_SOL_ANT_PRV_2,"Val.Sol.Ant.2");
            vecCab.add(INT_TBL_RPT_FEC_PAG_ANT_PRV_2,"Fec.Pag.Ant.2");
            vecCab.add(INT_TBL_RPT_TXT_OBS_ANT_PRV_2,"Obs.Ant.2");
            
            vecCab.add(INT_TBL_RPT_FEC_REC_FAC_PRV,"Fec.Rec.Fac.");
            vecCab.add(INT_TBL_RPT_FEC_SOL_AGE,"Fec.Sol.Age.");
            vecCab.add(INT_TBL_RPT_FEC_ASI_AGE,"Fec.Asi.Age.");              
            vecCab.add(INT_TBL_RPT_DES_PED_CON,"Consolidación");   
            vecCab.add(INT_TBL_RPT_FEC_EMB_EST,"Fec.Emb.Est.");  
            vecCab.add(INT_TBL_RPT_FEC_EMB_BOK,"Fec.Booking");  
            vecCab.add(INT_TBL_RPT_FEC_EMB_BL,"Fec.Emb.BL");  
            vecCab.add(INT_TBL_RPT_FEC_PUE,"Fec.Pue.");  
            vecCab.add(INT_TBL_RPT_FEC_ARR_BOD,"Fec.Bod.");  
            
            vecCab.add(INT_TBL_RPT_FEC_BAS_PAG_1,"Fec.Bas.Pag.1");  
            vecCab.add(INT_TBL_RPT_FEC_BAS_PAG_2,"Fec.Bas.Pag.2");  
            vecCab.add(INT_TBL_RPT_FEC_BAS_PAG_3,"Fec.Bas.Pag.3");  
            vecCab.add(INT_TBL_RPT_FEC_BAS_PAG_4,"Fec.Bas.Pag.4");  
            
            vecCab.add(INT_TBL_RPT_FEC_PAG_REA_1,"Fec.Pag.BL.1");  
            vecCab.add(INT_TBL_RPT_FEC_PAG_REA_2,"Fec.Pag.BL.2");  
            vecCab.add(INT_TBL_RPT_FEC_PAG_REA_3,"Fec.Pag.BL.3");  
            vecCab.add(INT_TBL_RPT_FEC_PAG_REA_4,"Fec.Pag.BL.4");    
            
            
            vecCab.add(INT_TBL_RPT_FEC_SOL_SAL_PRV,"Fec.Sol.Sal.Prv.");   
            vecCab.add(INT_TBL_RPT_VAL_SOL_SAL_PRV,"Val.Sol.Sal.Prv.");   
            vecCab.add(INT_TBL_RPT_FEC_PAG_SAL_PRV,"Fec.Pag.Sal.Prv.");   
            vecCab.add(INT_TBL_RPT_TXT_OBS_SAL_PRV,"Obs.Sal.Prv.");
            
            vecCab.add(INT_TBL_RPT_FEC_EST_VEN_1,"Fec.Est.Ven.1");   
            vecCab.add(INT_TBL_RPT_VAL_EST_VEN_1,"Val.Est.Ven.1");   
            vecCab.add(INT_TBL_RPT_FEC_PAG_VEN_1,"Fec.Pag.Ven.1");   
            vecCab.add(INT_TBL_RPT_VAL_PAG_VEN_1,"Val.Pag.Ven.1");  
            
            vecCab.add(INT_TBL_RPT_FEC_EST_VEN_2,"Fec.Est.Ven.2");   
            vecCab.add(INT_TBL_RPT_VAL_EST_VEN_2,"Val.Est.Ven.2");   
            vecCab.add(INT_TBL_RPT_FEC_PAG_VEN_2,"Fec.Pag.Ven.2");   
            vecCab.add(INT_TBL_RPT_VAL_PAG_VEN_2,"Val.Pag.Ven.2");    
            
            vecCab.add(INT_TBL_RPT_FEC_EST_VEN_3,"Fec.Est.Ven.3");   
            vecCab.add(INT_TBL_RPT_VAL_EST_VEN_3,"Val.Est.Ven.3");   
            vecCab.add(INT_TBL_RPT_FEC_PAG_VEN_3,"Fec.Pag.Ven.3");   
            vecCab.add(INT_TBL_RPT_VAL_PAG_VEN_3,"Val.Pag.Ven.3");     
            
            vecCab.add(INT_TBL_RPT_VAL_NOT_CRE,"Val.Not.Cre.");     
            vecCab.add(INT_TBL_RPT_TXT_OBS_COM,"Observación: Comprobación");     
                        
            
            objTblModDat=new ZafTblMod();
            objTblModDat.setHeader(vecCab);
            tblDat.setModel(objTblModDat);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_RPT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_RPT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_RPT_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_RPT_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_RPT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_RPT_NUM_DOC).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_RPT_NUM_PED).setPreferredWidth(93);
            
            tcmAux.getColumn(INT_TBL_RPT_PED_ASO).setPreferredWidth(95);
            
            tcmAux.getColumn(INT_TBL_RPT_COD_EMP_NOTPEDPRE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_RPT_COD_LOC_NOTPEDPRE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_RPT_COD_TIP_DOC_NOTPEDPRE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_RPT_COD_DOC_NOTPEDPRE).setPreferredWidth(30); 

            tcmAux.getColumn(INT_TBL_RPT_COD_EMP_NOTPED).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_RPT_COD_LOC_NOTPED).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_RPT_COD_TIP_DOC_NOTPED).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_RPT_COD_DOC_NOTPED).setPreferredWidth(30); 
            
            tcmAux.getColumn(INT_TBL_RPT_COD_EMP_PEDEMB).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_RPT_COD_LOC_PEDEMB).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_RPT_COD_TIP_DOC_PEDEMB).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_RPT_COD_DOC_PEDEMB).setPreferredWidth(30);    
            
            tcmAux.getColumn(INT_TBL_RPT_COD_EMP_INIMPO).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_RPT_COD_LOC_INIMPO).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_RPT_COD_TIP_DOC_INIMPO).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_RPT_COD_DOC_INIMPO).setPreferredWidth(30);             
            
            tcmAux.getColumn(INT_TBL_RPT_VAL_PED).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_RPT_VAL_PAG_TOT).setPreferredWidth(75);  
            tcmAux.getColumn(INT_TBL_RPT_CHK_VER_PAG).setPreferredWidth(30);  
            
            tcmAux.getColumn(INT_TBL_RPT_NOM_RUB_MAT).setPreferredWidth(120);
            
            tcmAux.getColumn(INT_TBL_RPT_COD_IMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_RPT_NOM_IMP).setPreferredWidth(75);
            
            tcmAux.getColumn(INT_TBL_RPT_COD_DIR_IND).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_RPT_DES_DIR_IND).setPreferredWidth(95);

            tcmAux.getColumn(INT_TBL_RPT_DES_TIP_PED).setPreferredWidth(65);
            
            tcmAux.getColumn(INT_TBL_RPT_DES_PTO_EMB).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_RPT_COD_PRV).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_RPT_NOM_PRV).setPreferredWidth(100);
            
            tcmAux.getColumn(INT_TBL_RPT_COD_FOR_PAG).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_RPT_DES_FOR_PAG).setPreferredWidth(120);
               
            tcmAux.getColumn(INT_TBL_RPT_FEC_PRO_EST).setPreferredWidth(73);
            tcmAux.getColumn(INT_TBL_RPT_FEC_SEG_ORD).setPreferredWidth(73);
            tcmAux.getColumn(INT_TBL_RPT_DES_REQ_ORD).setPreferredWidth(150);
            
            tcmAux.getColumn(INT_TBL_RPT_FEC_PRE_IMP).setPreferredWidth(76);
            
            tcmAux.getColumn(INT_TBL_RPT_FEC_COL_PED).setPreferredWidth(95);
            
            tcmAux.getColumn(INT_TBL_RPT_FEC_REC_PRO).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_RPT_FEC_CON_PRO).setPreferredWidth(85);
            
            tcmAux.getColumn(INT_TBL_RPT_FEC_DOC_NPP).setPreferredWidth(77);
            
            tcmAux.getColumn(INT_TBL_RPT_FEC_NOT_PED_IND).setPreferredWidth(120);  
            tcmAux.getColumn(INT_TBL_RPT_FEC_DOC_NOT_PED).setPreferredWidth(90);   
            
            tcmAux.getColumn(INT_TBL_RPT_FEC_SOL_ANT_PRV_1).setPreferredWidth(80); 
            tcmAux.getColumn(INT_TBL_RPT_VAL_SOL_ANT_PRV_1).setPreferredWidth(80); 
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_ANT_PRV_1).setPreferredWidth(80); 
            tcmAux.getColumn(INT_TBL_RPT_TXT_OBS_ANT_PRV_1).setPreferredWidth(100); 
            
            tcmAux.getColumn(INT_TBL_RPT_FEC_SOL_ANT_PRV_2).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_RPT_VAL_SOL_ANT_PRV_2).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_ANT_PRV_2).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_RPT_TXT_OBS_ANT_PRV_2).setPreferredWidth(100); 
            
            tcmAux.getColumn(INT_TBL_RPT_FEC_REC_FAC_PRV).setPreferredWidth(75); 
            tcmAux.getColumn(INT_TBL_RPT_FEC_SOL_AGE).setPreferredWidth(73); 
            tcmAux.getColumn(INT_TBL_RPT_FEC_ASI_AGE).setPreferredWidth(73);   
            
            tcmAux.getColumn(INT_TBL_RPT_DES_PED_CON).setPreferredWidth(88);   
            
            
            tcmAux.getColumn(INT_TBL_RPT_FEC_EMB_EST).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_RPT_FEC_EMB_BOK).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_RPT_FEC_EMB_BL).setPreferredWidth(75);
            
            tcmAux.getColumn(INT_TBL_RPT_FEC_PUE).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_RPT_FEC_ARR_BOD).setPreferredWidth(70);
            
            tcmAux.getColumn(INT_TBL_RPT_FEC_BAS_PAG_1).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_RPT_FEC_BAS_PAG_2).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_RPT_FEC_BAS_PAG_3).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_RPT_FEC_BAS_PAG_4).setPreferredWidth(85);
            
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_REA_1).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_REA_2).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_REA_3).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_REA_4).setPreferredWidth(85);            
            
            tcmAux.getColumn(INT_TBL_RPT_FEC_SOL_SAL_PRV).setPreferredWidth(92);            
            tcmAux.getColumn(INT_TBL_RPT_VAL_SOL_SAL_PRV).setPreferredWidth(92);    
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_SAL_PRV).setPreferredWidth(92);   
            tcmAux.getColumn(INT_TBL_RPT_TXT_OBS_SAL_PRV).setPreferredWidth(100); 
            
            tcmAux.getColumn(INT_TBL_RPT_FEC_EST_VEN_1).setPreferredWidth(80);        
            tcmAux.getColumn(INT_TBL_RPT_VAL_EST_VEN_1).setPreferredWidth(80);        
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_VEN_1).setPreferredWidth(80);        
            tcmAux.getColumn(INT_TBL_RPT_VAL_PAG_VEN_1).setPreferredWidth(80);        
            
            tcmAux.getColumn(INT_TBL_RPT_FEC_EST_VEN_2).setPreferredWidth(80);        
            tcmAux.getColumn(INT_TBL_RPT_VAL_EST_VEN_2).setPreferredWidth(80);        
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_VEN_2).setPreferredWidth(80);        
            tcmAux.getColumn(INT_TBL_RPT_VAL_PAG_VEN_2).setPreferredWidth(80);                    

            tcmAux.getColumn(INT_TBL_RPT_FEC_EST_VEN_3).setPreferredWidth(80);        
            tcmAux.getColumn(INT_TBL_RPT_VAL_EST_VEN_3).setPreferredWidth(80);        
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_VEN_3).setPreferredWidth(80);        
            tcmAux.getColumn(INT_TBL_RPT_VAL_PAG_VEN_3).setPreferredWidth(80);   
            
            tcmAux.getColumn(INT_TBL_RPT_VAL_NOT_CRE).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_RPT_TXT_OBS_COM).setPreferredWidth(170);            

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false); 
          
            //Configurar JTable: Ocultar columnas del sistema.
            //objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_LIN, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_EMP, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_LOC, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_TIP_DOC, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_DOC, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_NUM_DOC, tblDat);
            
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_EMP_NOTPEDPRE, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_LOC_NOTPEDPRE, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_TIP_DOC_NOTPEDPRE, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_DOC_NOTPEDPRE, tblDat);        
                        
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_EMP_NOTPED, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_LOC_NOTPED, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_TIP_DOC_NOTPED, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_DOC_NOTPED, tblDat);        
            
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_EMP_PEDEMB, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_LOC_PEDEMB, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_TIP_DOC_PEDEMB, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_DOC_PEDEMB, tblDat);  
            
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_EMP_INIMPO, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_LOC_INIMPO, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_TIP_DOC_INIMPO, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_DOC_INIMPO, tblDat);               
            
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_IMP, tblDat);
            
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_DIR_IND, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_PRV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_COD_FOR_PAG, tblDat);
            
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_FEC_PAG_REA_1, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_FEC_PAG_REA_2, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_FEC_PAG_REA_3, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_RPT_FEC_PAG_REA_4, tblDat);
            

            ////////////////////////////////////////////////////////////////////////////
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            //Configurar JTable: Establecer los listener para el TableHeader.
//            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
//                public void mouseClicked(java.awt.event.MouseEvent evt) {
//                    tblDatOrdMouseClicked(evt);
//                }
//            });       
            ////////////////////////////////////////////////////////////////////////////
            
            //Configurar JTable: Editor de la tabla.
            objTblEdiNumPed=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Editor de la tabla.
            objTblEdiValPed=new ZafTblEdi(tblDat);            
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            //objTblOrd=new ZafTblOrd(tblDat);               
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaRpt=new ZafMouMotAdaRpt(); 
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAdaRpt); 
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_RPT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl(); 
            //objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            //tcmAux.getColumn(INT_TBL_RPT_COD_EMP).setCellRenderer(objTblCelRenLbl);
            
            //Configurar JTable: Agrupamiento de columnas
            //Datos del Pedido
            ZafTblHeaGrp objTblHeaGrpDatPed=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpDatPed.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpDatPed=null;
            objTblHeaColGrpDatPed=new ZafTblHeaColGrp("Pedido");
            objTblHeaColGrpDatPed.setHeight(16); 
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_EMP));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_LOC));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_TIP_DOC));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_DOC));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_NUM_DOC));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_NUM_PED));            
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_PED_ASO));
            
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_EMP_NOTPEDPRE));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_LOC_NOTPEDPRE));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_TIP_DOC_NOTPEDPRE));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_DOC_NOTPEDPRE));
            
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_EMP_NOTPED));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_LOC_NOTPED));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_TIP_DOC_NOTPED));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_DOC_NOTPED));    
            
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_EMP_PEDEMB));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_LOC_PEDEMB));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_TIP_DOC_PEDEMB));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_DOC_PEDEMB));    
            
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_EMP_INIMPO));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_LOC_INIMPO));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_TIP_DOC_INIMPO));
            objTblHeaColGrpDatPed.add(tcmAux.getColumn(INT_TBL_RPT_COD_DOC_INIMPO));  
            
            objTblHeaGrpDatPed.addColumnGroup(objTblHeaColGrpDatPed);     
            
            //Verificación
            ZafTblHeaGrp objTblHeaGrpVer=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpVer.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpVer=null;
            objTblHeaColGrpVer=new ZafTblHeaColGrp("Verificación");
            objTblHeaColGrpVer.setHeight(16); 
            objTblHeaColGrpVer.add(tcmAux.getColumn(INT_TBL_RPT_VAL_PED));
            objTblHeaColGrpVer.add(tcmAux.getColumn(INT_TBL_RPT_VAL_PAG_TOT));
            objTblHeaColGrpVer.add(tcmAux.getColumn(INT_TBL_RPT_CHK_VER_PAG));
            objTblHeaGrpVer.addColumnGroup(objTblHeaColGrpVer);      
            
            //Datos de la Orden de Importación
            ZafTblHeaGrp objTblHeaGrpDatOrd=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpDatOrd.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpDatOrd=null;
            objTblHeaColGrpDatOrd=new ZafTblHeaColGrp("Datos de Orden");
            objTblHeaColGrpDatOrd.setHeight(16);             
            
            objTblHeaColGrpDatOrd.add(tcmAux.getColumn(INT_TBL_RPT_NOM_RUB_MAT));
            objTblHeaColGrpDatOrd.add(tcmAux.getColumn(INT_TBL_RPT_COD_IMP));
            objTblHeaColGrpDatOrd.add(tcmAux.getColumn(INT_TBL_RPT_NOM_IMP));
            objTblHeaColGrpDatOrd.add(tcmAux.getColumn(INT_TBL_RPT_COD_DIR_IND));
            objTblHeaColGrpDatOrd.add(tcmAux.getColumn(INT_TBL_RPT_DES_DIR_IND));
            
            objTblHeaColGrpDatOrd.add(tcmAux.getColumn(INT_TBL_RPT_DES_TIP_PED));
            objTblHeaColGrpDatOrd.add(tcmAux.getColumn(INT_TBL_RPT_DES_PTO_EMB));
            objTblHeaColGrpDatOrd.add(tcmAux.getColumn(INT_TBL_RPT_COD_PRV));
            objTblHeaColGrpDatOrd.add(tcmAux.getColumn(INT_TBL_RPT_NOM_PRV));
            objTblHeaColGrpDatOrd.add(tcmAux.getColumn(INT_TBL_RPT_COD_FOR_PAG));
            objTblHeaColGrpDatOrd.add(tcmAux.getColumn(INT_TBL_RPT_DES_FOR_PAG));            
            
            objTblHeaGrpDatOrd.addColumnGroup(objTblHeaColGrpDatOrd); 
            
            //Seguimiento de la Orden
            ZafTblHeaGrp objTblHeaGrpSeg=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpSeg.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpSeg=null;
            objTblHeaColGrpSeg=new ZafTblHeaColGrp("Seguimiento de Orden");
            objTblHeaColGrpSeg.setHeight(16); 
            objTblHeaColGrpSeg.add(tcmAux.getColumn(INT_TBL_RPT_FEC_PRO_EST));
            objTblHeaColGrpSeg.add(tcmAux.getColumn(INT_TBL_RPT_FEC_SEG_ORD));
            objTblHeaColGrpSeg.add(tcmAux.getColumn(INT_TBL_RPT_DES_REQ_ORD));
            objTblHeaColGrpSeg.add(tcmAux.getColumn(INT_TBL_RPT_FEC_PRE_IMP));
            objTblHeaColGrpSeg.add(tcmAux.getColumn(INT_TBL_RPT_FEC_COL_PED));
            objTblHeaColGrpSeg.add(tcmAux.getColumn(INT_TBL_RPT_FEC_REC_PRO));            
            objTblHeaColGrpSeg.add(tcmAux.getColumn(INT_TBL_RPT_FEC_CON_PRO));            
            objTblHeaColGrpSeg.add(tcmAux.getColumn(INT_TBL_RPT_FEC_DOC_NPP));            
            objTblHeaColGrpSeg.add(tcmAux.getColumn(INT_TBL_RPT_FEC_NOT_PED_IND));  
            objTblHeaColGrpSeg.add(tcmAux.getColumn(INT_TBL_RPT_FEC_DOC_NOT_PED));  
            objTblHeaGrpSeg.addColumnGroup(objTblHeaColGrpSeg);             
            
            //Anticipo 1 - Proveedor
            ZafTblHeaGrp objTblHeaGrpAntPrv1=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpAntPrv1.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpAntPrv1=null;
            objTblHeaColGrpAntPrv1=new ZafTblHeaColGrp("Anticipo 1 - Proveedor");
            objTblHeaColGrpAntPrv1.setHeight(16); 
            objTblHeaColGrpAntPrv1.add(tcmAux.getColumn(INT_TBL_RPT_FEC_SOL_ANT_PRV_1));
            objTblHeaColGrpAntPrv1.add(tcmAux.getColumn(INT_TBL_RPT_VAL_SOL_ANT_PRV_1));
            objTblHeaColGrpAntPrv1.add(tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_ANT_PRV_1));
            objTblHeaColGrpAntPrv1.add(tcmAux.getColumn(INT_TBL_RPT_TXT_OBS_ANT_PRV_1));
            objTblHeaGrpAntPrv1.addColumnGroup(objTblHeaColGrpAntPrv1); 
            
            //Anticipo 2 - Proveedor
            ZafTblHeaGrp objTblHeaGrpAntPrv2=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpAntPrv2.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpAntPrv2=null;
            objTblHeaColGrpAntPrv2=new ZafTblHeaColGrp("Anticipo 2 - Proveedor");
            objTblHeaColGrpAntPrv2.setHeight(16); 
            objTblHeaColGrpAntPrv2.add(tcmAux.getColumn(INT_TBL_RPT_FEC_SOL_ANT_PRV_2));
            objTblHeaColGrpAntPrv2.add(tcmAux.getColumn(INT_TBL_RPT_VAL_SOL_ANT_PRV_2));
            objTblHeaColGrpAntPrv2.add(tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_ANT_PRV_2));
            objTblHeaColGrpAntPrv2.add(tcmAux.getColumn(INT_TBL_RPT_TXT_OBS_ANT_PRV_2));
            objTblHeaGrpAntPrv2.addColumnGroup(objTblHeaColGrpAntPrv2);       
            
            //Proceso Embarque
            ZafTblHeaGrp objTblHeaGrpProEmb=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpProEmb.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpProEmb=null;
            objTblHeaColGrpProEmb=new ZafTblHeaColGrp("Proceso de Embarque");
            objTblHeaColGrpProEmb.setHeight(16); 
            objTblHeaColGrpProEmb.add(tcmAux.getColumn(INT_TBL_RPT_FEC_REC_FAC_PRV));  
            objTblHeaColGrpProEmb.add(tcmAux.getColumn(INT_TBL_RPT_FEC_SOL_AGE));  
            objTblHeaColGrpProEmb.add(tcmAux.getColumn(INT_TBL_RPT_FEC_ASI_AGE));
            objTblHeaColGrpProEmb.add(tcmAux.getColumn(INT_TBL_RPT_DES_PED_CON));
            objTblHeaColGrpProEmb.add(tcmAux.getColumn(INT_TBL_RPT_FEC_EMB_EST));
            objTblHeaColGrpProEmb.add(tcmAux.getColumn(INT_TBL_RPT_FEC_EMB_BOK));
            objTblHeaColGrpProEmb.add(tcmAux.getColumn(INT_TBL_RPT_FEC_EMB_BL));
            objTblHeaColGrpProEmb.add(tcmAux.getColumn(INT_TBL_RPT_FEC_PUE));
            objTblHeaColGrpProEmb.add(tcmAux.getColumn(INT_TBL_RPT_FEC_ARR_BOD));
            objTblHeaGrpProEmb.addColumnGroup(objTblHeaColGrpProEmb);               
            
            //Fechas de vencimiento estimadas
            ZafTblHeaGrp objTblHeaGrpFecVenEst=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpFecVenEst.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpFecVenEst=null;
            objTblHeaColGrpFecVenEst=new ZafTblHeaColGrp("Fechas Base de Pago");
            objTblHeaColGrpFecVenEst.setHeight(16); 
            objTblHeaColGrpFecVenEst.add(tcmAux.getColumn(INT_TBL_RPT_FEC_BAS_PAG_1));
            objTblHeaColGrpFecVenEst.add(tcmAux.getColumn(INT_TBL_RPT_FEC_BAS_PAG_2));
            objTblHeaColGrpFecVenEst.add(tcmAux.getColumn(INT_TBL_RPT_FEC_BAS_PAG_3));
            objTblHeaColGrpFecVenEst.add(tcmAux.getColumn(INT_TBL_RPT_FEC_BAS_PAG_4));
            objTblHeaGrpFecVenEst.addColumnGroup(objTblHeaColGrpFecVenEst); 
            
            //Fechas de vencimiento reales
            ZafTblHeaGrp objTblHeaGrpFecVenRea=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpFecVenRea.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpFecVenRea=null;
            objTblHeaColGrpFecVenRea=new ZafTblHeaColGrp("Fechas de Pago BL");
            objTblHeaColGrpFecVenRea.setHeight(16); 
            objTblHeaColGrpFecVenRea.add(tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_REA_1));
            objTblHeaColGrpFecVenRea.add(tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_REA_2));
            objTblHeaColGrpFecVenRea.add(tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_REA_3));
            objTblHeaColGrpFecVenRea.add(tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_REA_4));
            objTblHeaGrpFecVenRea.addColumnGroup(objTblHeaColGrpFecVenRea);   
            
            //Fechas de Saldos a Proveedor
            ZafTblHeaGrp objTblHeaGrpFecSalPrv=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpFecSalPrv.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpFecSalPrv=null;
            objTblHeaColGrpFecSalPrv=new ZafTblHeaColGrp("Fechas de Saldos a Proveedor");
            objTblHeaColGrpFecSalPrv.setHeight(16); 
            objTblHeaColGrpFecSalPrv.add(tcmAux.getColumn(INT_TBL_RPT_FEC_SOL_SAL_PRV));
            objTblHeaColGrpFecSalPrv.add(tcmAux.getColumn(INT_TBL_RPT_VAL_SOL_SAL_PRV));
            objTblHeaColGrpFecSalPrv.add(tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_SAL_PRV));
            objTblHeaColGrpFecSalPrv.add(tcmAux.getColumn(INT_TBL_RPT_TXT_OBS_SAL_PRV));
            objTblHeaGrpFecSalPrv.addColumnGroup(objTblHeaColGrpFecSalPrv);   
            
           
            
            //Fechas de vencimiento 1: Pedidos Llegados
            ZafTblHeaGrp objTblHeaGrpFecVenPedLle1=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpFecVenPedLle1.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpFecVenPedLle1=null;
            objTblHeaColGrpFecVenPedLle1=new ZafTblHeaColGrp("Fechas Vencimiento 1 - Pedidos Llegados");
            objTblHeaColGrpFecVenPedLle1.setHeight(16); 
            objTblHeaColGrpFecVenPedLle1.add(tcmAux.getColumn(INT_TBL_RPT_FEC_EST_VEN_1));
            objTblHeaColGrpFecVenPedLle1.add(tcmAux.getColumn(INT_TBL_RPT_VAL_EST_VEN_1));
            objTblHeaColGrpFecVenPedLle1.add(tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_VEN_1));
            objTblHeaColGrpFecVenPedLle1.add(tcmAux.getColumn(INT_TBL_RPT_VAL_PAG_VEN_1));
            objTblHeaGrpFecVenPedLle1.addColumnGroup(objTblHeaColGrpFecVenPedLle1);      
            
            //Fechas de vencimiento 2: Pedidos Llegados
            ZafTblHeaGrp objTblHeaGrpFecVenPedLle2=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpFecVenPedLle2.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpFecVenPedLle2=null;
            objTblHeaColGrpFecVenPedLle2=new ZafTblHeaColGrp("Fechas Vencimiento 2 - Pedidos Llegados");
            objTblHeaColGrpFecVenPedLle2.setHeight(16); 
            objTblHeaColGrpFecVenPedLle2.add(tcmAux.getColumn(INT_TBL_RPT_FEC_EST_VEN_2));
            objTblHeaColGrpFecVenPedLle2.add(tcmAux.getColumn(INT_TBL_RPT_VAL_EST_VEN_2));
            objTblHeaColGrpFecVenPedLle2.add(tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_VEN_2));
            objTblHeaColGrpFecVenPedLle2.add(tcmAux.getColumn(INT_TBL_RPT_VAL_PAG_VEN_2));
            objTblHeaGrpFecVenPedLle2.addColumnGroup(objTblHeaColGrpFecVenPedLle2);  
            
            //Fechas de vencimiento 3: Pedidos Llegados
            ZafTblHeaGrp objTblHeaGrpFecVenPedLle3=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpFecVenPedLle3.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpFecVenPedLle3=null;
            objTblHeaColGrpFecVenPedLle3=new ZafTblHeaColGrp("Fechas Vencimiento 3 - Pedidos Llegados");
            objTblHeaColGrpFecVenPedLle3.setHeight(16); 
            objTblHeaColGrpFecVenPedLle3.add(tcmAux.getColumn(INT_TBL_RPT_FEC_EST_VEN_3));
            objTblHeaColGrpFecVenPedLle3.add(tcmAux.getColumn(INT_TBL_RPT_VAL_EST_VEN_3));
            objTblHeaColGrpFecVenPedLle3.add(tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_VEN_3));
            objTblHeaColGrpFecVenPedLle3.add(tcmAux.getColumn(INT_TBL_RPT_VAL_PAG_VEN_3));
            objTblHeaGrpFecVenPedLle3.addColumnGroup(objTblHeaColGrpFecVenPedLle3);       
            
            //Comprobación
            ZafTblHeaGrp objTblHeaGrpCom=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpCom.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpCom=null;
            objTblHeaColGrpCom=new ZafTblHeaColGrp("Comprobación");
            objTblHeaColGrpCom.setHeight(16); 
            objTblHeaColGrpCom.add(tcmAux.getColumn(INT_TBL_RPT_VAL_NOT_CRE));
            objTblHeaColGrpCom.add(tcmAux.getColumn(INT_TBL_RPT_TXT_OBS_COM));
            objTblHeaGrpCom.addColumnGroup(objTblHeaColGrpCom);              
            
            //Configurar JTable: Establecer Formato de Fecha
            objDtePckEdiFecRpt = new ZafDtePckEdi(strFormatoDatePicker);
            tcmAux.getColumn(INT_TBL_RPT_FEC_PRO_EST).setCellEditor(objDtePckEdiFecRpt); 
            tcmAux.getColumn(INT_TBL_RPT_FEC_SEG_ORD).setCellEditor(objDtePckEdiFecRpt); 
            tcmAux.getColumn(INT_TBL_RPT_FEC_REC_PRO).setCellEditor(objDtePckEdiFecRpt); 
            tcmAux.getColumn(INT_TBL_RPT_FEC_CON_PRO).setCellEditor(objDtePckEdiFecRpt); 
            tcmAux.getColumn(INT_TBL_RPT_FEC_NOT_PED_IND).setCellEditor(objDtePckEdiFecRpt); 
            tcmAux.getColumn(INT_TBL_RPT_FEC_SOL_ANT_PRV_1).setCellEditor(objDtePckEdiFecRpt); 
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_ANT_PRV_1).setCellEditor(objDtePckEdiFecRpt); 
            tcmAux.getColumn(INT_TBL_RPT_FEC_SOL_ANT_PRV_2).setCellEditor(objDtePckEdiFecRpt); 
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_ANT_PRV_2).setCellEditor(objDtePckEdiFecRpt);      
            tcmAux.getColumn(INT_TBL_RPT_FEC_REC_FAC_PRV).setCellEditor(objDtePckEdiFecRpt); 
            tcmAux.getColumn(INT_TBL_RPT_FEC_SOL_AGE).setCellEditor(objDtePckEdiFecRpt); 
            tcmAux.getColumn(INT_TBL_RPT_FEC_ASI_AGE).setCellEditor(objDtePckEdiFecRpt); 
            tcmAux.getColumn(INT_TBL_RPT_FEC_EMB_EST).setCellEditor(objDtePckEdiFecRpt); 
            tcmAux.getColumn(INT_TBL_RPT_FEC_EMB_BOK).setCellEditor(objDtePckEdiFecRpt); 
            tcmAux.getColumn(INT_TBL_RPT_FEC_EMB_BL).setCellEditor(objDtePckEdiFecRpt); 
            tcmAux.getColumn(INT_TBL_RPT_FEC_PUE).setCellEditor(objDtePckEdiFecRpt);             
            tcmAux.getColumn(INT_TBL_RPT_FEC_ARR_BOD).setCellEditor(objDtePckEdiFecRpt);
            tcmAux.getColumn(INT_TBL_RPT_FEC_BAS_PAG_1).setCellEditor(objDtePckEdiFecRpt);
            tcmAux.getColumn(INT_TBL_RPT_FEC_BAS_PAG_2).setCellEditor(objDtePckEdiFecRpt);
            tcmAux.getColumn(INT_TBL_RPT_FEC_BAS_PAG_3).setCellEditor(objDtePckEdiFecRpt);
            tcmAux.getColumn(INT_TBL_RPT_FEC_BAS_PAG_4).setCellEditor(objDtePckEdiFecRpt);
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_REA_1).setCellEditor(objDtePckEdiFecRpt);
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_REA_2).setCellEditor(objDtePckEdiFecRpt);
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_REA_3).setCellEditor(objDtePckEdiFecRpt);
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_REA_4).setCellEditor(objDtePckEdiFecRpt);
            tcmAux.getColumn(INT_TBL_RPT_FEC_SOL_SAL_PRV).setCellEditor(objDtePckEdiFecRpt);
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_SAL_PRV).setCellEditor(objDtePckEdiFecRpt);
            tcmAux.getColumn(INT_TBL_RPT_FEC_EST_VEN_1).setCellEditor(objDtePckEdiFecRpt);
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_VEN_1).setCellEditor(objDtePckEdiFecRpt);
            tcmAux.getColumn(INT_TBL_RPT_FEC_EST_VEN_2).setCellEditor(objDtePckEdiFecRpt);
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_VEN_2).setCellEditor(objDtePckEdiFecRpt);
            tcmAux.getColumn(INT_TBL_RPT_FEC_EST_VEN_3).setCellEditor(objDtePckEdiFecRpt);
            tcmAux.getColumn(INT_TBL_RPT_FEC_PAG_VEN_3).setCellEditor(objDtePckEdiFecRpt);            
            
            //Establecer columnas editables
            vecAux=new Vector();
            
            vecAux.add("" + INT_TBL_RPT_NUM_PED); 
            vecAux.add("" + INT_TBL_RPT_PED_ASO); 
            vecAux.add("" + INT_TBL_RPT_VAL_PED); 
            vecAux.add("" + INT_TBL_RPT_NOM_RUB_MAT); 
            vecAux.add("" + INT_TBL_RPT_NOM_IMP); 
            vecAux.add("" + INT_TBL_RPT_DES_DIR_IND); 
            vecAux.add("" + INT_TBL_RPT_DES_TIP_PED); 
            vecAux.add("" + INT_TBL_RPT_DES_PTO_EMB); 
            vecAux.add("" + INT_TBL_RPT_NOM_PRV); 
            vecAux.add("" + INT_TBL_RPT_COD_FOR_PAG); 
            vecAux.add("" + INT_TBL_RPT_DES_FOR_PAG); 
            
            vecAux.add("" + INT_TBL_RPT_FEC_PRO_EST); 
            vecAux.add("" + INT_TBL_RPT_FEC_SEG_ORD); 
            vecAux.add("" + INT_TBL_RPT_DES_REQ_ORD);             
            
            vecAux.add("" + INT_TBL_RPT_FEC_REC_PRO); 
            vecAux.add("" + INT_TBL_RPT_FEC_CON_PRO); 
            vecAux.add("" + INT_TBL_RPT_FEC_NOT_PED_IND); 
            
            vecAux.add("" + INT_TBL_RPT_FEC_SOL_ANT_PRV_1); 
            vecAux.add("" + INT_TBL_RPT_VAL_SOL_ANT_PRV_1); 
            vecAux.add("" + INT_TBL_RPT_FEC_PAG_ANT_PRV_1); 
            vecAux.add("" + INT_TBL_RPT_TXT_OBS_ANT_PRV_1); 
            
            vecAux.add("" + INT_TBL_RPT_FEC_SOL_ANT_PRV_2); 
            vecAux.add("" + INT_TBL_RPT_VAL_SOL_ANT_PRV_2); 
            vecAux.add("" + INT_TBL_RPT_FEC_PAG_ANT_PRV_2); 
            vecAux.add("" + INT_TBL_RPT_TXT_OBS_ANT_PRV_2); 
            
            vecAux.add("" + INT_TBL_RPT_FEC_REC_FAC_PRV); 
            vecAux.add("" + INT_TBL_RPT_FEC_SOL_AGE); 
            vecAux.add("" + INT_TBL_RPT_FEC_ASI_AGE); 
            
            vecAux.add("" + INT_TBL_RPT_DES_PED_CON); 
            
            vecAux.add("" + INT_TBL_RPT_FEC_EMB_EST); 
            vecAux.add("" + INT_TBL_RPT_FEC_EMB_BOK); 
            vecAux.add("" + INT_TBL_RPT_FEC_PUE); 
            vecAux.add("" + INT_TBL_RPT_FEC_ARR_BOD); 
            
            vecAux.add("" + INT_TBL_RPT_FEC_BAS_PAG_1); 
            vecAux.add("" + INT_TBL_RPT_FEC_BAS_PAG_2); 
            vecAux.add("" + INT_TBL_RPT_FEC_BAS_PAG_3); 
            vecAux.add("" + INT_TBL_RPT_FEC_BAS_PAG_4);
            
            vecAux.add("" + INT_TBL_RPT_FEC_PAG_REA_1); 
            vecAux.add("" + INT_TBL_RPT_FEC_PAG_REA_2); 
            vecAux.add("" + INT_TBL_RPT_FEC_PAG_REA_3); 
            vecAux.add("" + INT_TBL_RPT_FEC_PAG_REA_4);             
            
            
            vecAux.add("" + INT_TBL_RPT_FEC_SOL_SAL_PRV);      
            vecAux.add("" + INT_TBL_RPT_VAL_SOL_SAL_PRV);      
            vecAux.add("" + INT_TBL_RPT_FEC_PAG_SAL_PRV);  
            vecAux.add("" + INT_TBL_RPT_TXT_OBS_SAL_PRV); 
            
            vecAux.add("" + INT_TBL_RPT_FEC_EST_VEN_1);      
            vecAux.add("" + INT_TBL_RPT_VAL_EST_VEN_1);      
            vecAux.add("" + INT_TBL_RPT_FEC_PAG_VEN_1);      
            vecAux.add("" + INT_TBL_RPT_VAL_PAG_VEN_1);  
            
            vecAux.add("" + INT_TBL_RPT_FEC_EST_VEN_2);      
            vecAux.add("" + INT_TBL_RPT_VAL_EST_VEN_2);      
            vecAux.add("" + INT_TBL_RPT_FEC_PAG_VEN_2);      
            vecAux.add("" + INT_TBL_RPT_VAL_PAG_VEN_2);    
            
            vecAux.add("" + INT_TBL_RPT_FEC_EST_VEN_3);      
            vecAux.add("" + INT_TBL_RPT_VAL_EST_VEN_3);      
            vecAux.add("" + INT_TBL_RPT_FEC_PAG_VEN_3);      
            vecAux.add("" + INT_TBL_RPT_VAL_PAG_VEN_3);                   
            
            vecAux.add("" + INT_TBL_RPT_VAL_NOT_CRE);  
            vecAux.add("" + INT_TBL_RPT_TXT_OBS_COM);  
            
            objTblModDat.setColumnasEditables(vecAux);
            vecAux=null;             
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiTxtNumPed=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_RPT_NUM_PED).setCellEditor(objTblCelEdiTxtNumPed);   
            objTblCelEdiTxtNumPed.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            { 
                int intFilSel;
                String strNumPedAftEdi="", strNumPedBefEdi="";
                String strCodEmpPed="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)    {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1) 
                    {   
                        strCodEmpPed=objTblModDat.getValueAt(intFilSel, INT_TBL_RPT_COD_EMP)==null?"":objTblModDat.getValueAt(intFilSel, INT_TBL_RPT_COD_EMP).toString();
                        strNumPedBefEdi=objTblModDat.getValueAt(intFilSel, INT_TBL_RPT_NUM_PED)==null?"":objTblModDat.getValueAt(intFilSel, INT_TBL_RPT_NUM_PED).toString();
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)     {
                    strNumPedAftEdi=(objTblModDat.getValueAt(intFilSel, INT_TBL_RPT_NUM_PED)==null?"":objTblModDat.getValueAt(intFilSel, INT_TBL_RPT_NUM_PED).toString()).trim();
                    if(validaExiNumPed(null, strNumPedAftEdi, intFilSel)) {
                        objTblModDat.setValueAt(""+strNumPedBefEdi, intFilSel, INT_TBL_RPT_NUM_PED);
                        mostrarMsgInf("<HTML>El número de Pedido "+strNumPedAftEdi+" ya existe.<BR>Indique otro número de pedido y vuelva a intentarlo.</HTML>");
                    }
                    else{
                        objTblModDat.setValueAt(""+strNumPedAftEdi, intFilSel, INT_TBL_RPT_NUM_PED); //Se setea el número de pedido sin espacios.
                        objTblEdiNumPed.seleccionarCelda((intFilSel+1), INT_TBL_RPT_NUM_PED); 
                        //objTblModFix.insertRow();
                    }
                }
            });   
            
            //Configurar JTable: Editor de celdas - Valor del Pedido
            objTblCelEdiTxtValPed=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_RPT_VAL_PED).setCellEditor(objTblCelEdiTxtValPed);   
            objTblCelEdiTxtValPed.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            { 
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)  {
                    intFilSel = tblDat.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)   {
                    calcularVerificacionPagExt(intFilSel);
                }
            });      
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_RPT_CHK_VER_PAG).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                java.awt.Color colFonColCre;
                int intFil = -1;
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    intFil = objTblCelRenChk.getRowRender();
                    colFonColCre=new java.awt.Color(255,221,187);
                    calcularVerificacionPagExt();
                    if(objTblModDat.isChecked(intFil, INT_TBL_RPT_CHK_VER_PAG)){
                        objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        objTblCelRenChk.setBackground(colFonColCre);
                    }
                }
            });            
            
            //Configurar JTable: Editor de celdas. - Valor Númerico
            objTblCelEdiTxtValNum=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_RPT_VAL_SOL_ANT_PRV_1).setCellEditor(objTblCelEdiTxtValNum);   
            tcmAux.getColumn(INT_TBL_RPT_VAL_SOL_ANT_PRV_2).setCellEditor(objTblCelEdiTxtValNum);   
            tcmAux.getColumn(INT_TBL_RPT_VAL_SOL_SAL_PRV).setCellEditor(objTblCelEdiTxtValNum);   
            tcmAux.getColumn(INT_TBL_RPT_VAL_EST_VEN_1).setCellEditor(objTblCelEdiTxtValNum);   
            tcmAux.getColumn(INT_TBL_RPT_VAL_PAG_VEN_1).setCellEditor(objTblCelEdiTxtValNum);               
            tcmAux.getColumn(INT_TBL_RPT_VAL_EST_VEN_2).setCellEditor(objTblCelEdiTxtValNum);   
            tcmAux.getColumn(INT_TBL_RPT_VAL_PAG_VEN_2).setCellEditor(objTblCelEdiTxtValNum);  
            tcmAux.getColumn(INT_TBL_RPT_VAL_EST_VEN_3).setCellEditor(objTblCelEdiTxtValNum);   
            tcmAux.getColumn(INT_TBL_RPT_VAL_PAG_VEN_3).setCellEditor(objTblCelEdiTxtValNum);   
            tcmAux.getColumn(INT_TBL_RPT_VAL_NOT_CRE).setCellEditor(objTblCelEdiTxtValNum);   
            objTblCelEdiTxtValNum.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            { 
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)  
                {
                    calcularPagTotExt(); //Se calcula el valor total pagado, en base a los pagos registrados.
                }
            });               

            
            //<editor-fold defaultstate="collapsed" desc="/* Ventana de consulta: Pedidos Asociados */">
            int intColVen[]=new int[5];            
            intColVen[0]=1; //co_emp
            intColVen[1]=2; //co_loc
            intColVen[2]=3; //co_tipDoc
            intColVen[3]=4; //co_doc
            intColVen[4]=5; //tx_numPed
            int intColTbl[]=new int[5];            
            intColTbl[0]=INT_TBL_RPT_COD_EMP_NOTPEDPRE;
            intColTbl[1]=INT_TBL_RPT_COD_LOC_NOTPEDPRE;
            intColTbl[2]=INT_TBL_RPT_COD_TIP_DOC_NOTPEDPRE;
            intColTbl[3]=INT_TBL_RPT_COD_DOC_NOTPEDPRE;
            intColTbl[4]=INT_TBL_RPT_PED_ASO;

            //Búsqueda por Decripción.
            objTblCelEdiTxtVcoPedAso=new ZafTblCelEdiTxtVco(tblDat, vcoPedAso, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_RPT_PED_ASO).setCellEditor(objTblCelEdiTxtVcoPedAso);
            objTblCelEdiTxtVcoPedAso.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoPedAso.limpiar();
                    vcoPedAso.setCampoBusqueda(4);
                    vcoPedAso.setCriterio1(11);                      
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoPedAso.isConsultaAceptada()) { 
                        objTblModDat.setValueAt(vcoPedAso.getValueAt(1), intFilSel, INT_TBL_RPT_COD_EMP_NOTPEDPRE);
                        objTblModDat.setValueAt(vcoPedAso.getValueAt(2), intFilSel, INT_TBL_RPT_COD_LOC_NOTPEDPRE);
                        objTblModDat.setValueAt(vcoPedAso.getValueAt(3), intFilSel, INT_TBL_RPT_COD_TIP_DOC_NOTPEDPRE);
                        objTblModDat.setValueAt(vcoPedAso.getValueAt(4), intFilSel, INT_TBL_RPT_COD_DOC_NOTPEDPRE);
                        objTblModDat.setValueAt(vcoPedAso.getValueAt(5), intFilSel, INT_TBL_RPT_PED_ASO);
                    }
                }
            });     
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="/* Ventana de consulta: Empresas Importadoras */">
            intColVen=new int[2];            
            intColVen[0]=1; //co_emp
            intColVen[1]=2; //tx_nomEmpImp
            intColTbl=new int[2];            
            intColTbl[0]=INT_TBL_RPT_COD_IMP;
            intColTbl[1]=INT_TBL_RPT_NOM_IMP;

            //Búsqueda por Decripción.
            objTblCelEdiTxtVcoEmpImp=new ZafTblCelEdiTxtVco(tblDat, vcoEmpImp, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_RPT_NOM_IMP).setCellEditor(objTblCelEdiTxtVcoEmpImp);
            objTblCelEdiTxtVcoEmpImp.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoEmpImp.limpiar();
                    vcoEmpImp.setCampoBusqueda(1);
                    vcoEmpImp.setCriterio1(11);                      
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoEmpImp.isConsultaAceptada()) { 
                        objTblModDat.setValueAt(vcoEmpImp.getValueAt(1), intFilSel, INT_TBL_RPT_COD_IMP);
                        objTblModDat.setValueAt(vcoEmpImp.getValueAt(2), intFilSel, INT_TBL_RPT_NOM_IMP);
                    }
                }
            });     
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="/* Ventana de consulta: Tipos de Pedidos: Indirectos/Directos */">
            intColVen=new int[2];            
            intColVen[0]=1; //co_emp
            intColVen[1]=2; //tx_desTipPed
            intColTbl=new int[2];            
            intColTbl[0]=INT_TBL_RPT_COD_DIR_IND;
            intColTbl[1]=INT_TBL_RPT_DES_DIR_IND;

            //Búsqueda por Decripción.
            objTblCelEdiTxtVcoTipPed=new ZafTblCelEdiTxtVco(tblDat, vcoTipPed, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_RPT_DES_DIR_IND).setCellEditor(objTblCelEdiTxtVcoTipPed);
            objTblCelEdiTxtVcoTipPed.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoTipPed.limpiar();
                    vcoTipPed.setCampoBusqueda(1);
                    vcoTipPed.setCriterio1(11);                      
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoTipPed.isConsultaAceptada()) { 
                        objTblModDat.setValueAt(vcoTipPed.getValueAt(1), intFilSel, INT_TBL_RPT_COD_DIR_IND);
                        objTblModDat.setValueAt(vcoTipPed.getValueAt(2), intFilSel, INT_TBL_RPT_DES_DIR_IND);
                    }
                }
            });     
            //</editor-fold>       
            
            //<editor-fold defaultstate="collapsed" desc="/* Ventana de consulta: Proveedores del Exterior */">
            intColVen=new int[2];            
            intColVen[0]=1; //co_prvExp
            intColVen[1]=2; //tx_nomPrvExp
            intColTbl=new int[2];            
            intColTbl[0]=INT_TBL_RPT_COD_PRV;
            intColTbl[1]=INT_TBL_RPT_NOM_PRV;

            //Búsqueda por Decripción.
            objTblCelEdiTxtVcoPrvExt=new ZafTblCelEdiTxtVco(tblDat, vcoPrvExt, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_RPT_NOM_PRV).setCellEditor(objTblCelEdiTxtVcoPrvExt);
            objTblCelEdiTxtVcoPrvExt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoPrvExt.limpiar();
                    vcoPrvExt.setCampoBusqueda(1);
                    vcoPrvExt.setCriterio1(11);                      
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoPrvExt.isConsultaAceptada()) { 
                        objTblModDat.setValueAt(vcoPrvExt.getValueAt(1), intFilSel, INT_TBL_RPT_COD_PRV);
                        objTblModDat.setValueAt(vcoPrvExt.getValueAt(2), intFilSel, INT_TBL_RPT_NOM_PRV);
                    }
                }
            });     
            //</editor-fold>  
            
            
            //Configurar JTable: Establecer formato
            objTblCelRenLblValPed=new ZafTblCelRenLbl();
            objTblCelRenLblValPed.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblValPed.setTipoFormato(objTblCelRenLblValPed.INT_FOR_NUM);
            objTblCelRenLblValPed.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_RPT_VAL_PED).setCellRenderer(objTblCelRenLblValPed);    
            objTblCelRenLblValPed.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                java.awt.Color colFonColRoj, colFonColNeg;
                int intFil = -1;
                String strFecRecFac="";
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    intFil = objTblCelRenLblValPed.getRowRender();
                    colFonColRoj=new java.awt.Color(255, 0, 0);
                    colFonColNeg=new java.awt.Color(0, 0, 0); 
                    strFecRecFac=objTblModDat.getValueAt(intFil, INT_TBL_RPT_FEC_REC_FAC_PRV)==null?"":objTblModDat.getValueAt(intFil, INT_TBL_RPT_FEC_REC_FAC_PRV).toString();
                    if(!strFecRecFac.equals("")){ //Si existe fecha de recepción de factura 
                        objTblCelRenLblValPed.setForeground(colFonColNeg);
                    }    
                    else{
                        objTblCelRenLblValPed.setForeground(colFonColRoj);
                    }
                }
            });            
            
            //Configurar JTable: Establecer formato
            objTblCelRenLblNumRpt=new ZafTblCelRenLbl();
            objTblCelRenLblNumRpt.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNumRpt.setTipoFormato(objTblCelRenLblNumRpt.INT_FOR_NUM);
            objTblCelRenLblNumRpt.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_RPT_VAL_PAG_TOT).setCellRenderer(objTblCelRenLblNumRpt);
            tcmAux.getColumn(INT_TBL_RPT_VAL_SOL_ANT_PRV_1).setCellRenderer(objTblCelRenLblNumRpt);
            tcmAux.getColumn(INT_TBL_RPT_VAL_SOL_ANT_PRV_2).setCellRenderer(objTblCelRenLblNumRpt);
            tcmAux.getColumn(INT_TBL_RPT_VAL_SOL_SAL_PRV).setCellRenderer(objTblCelRenLblNumRpt);            
            tcmAux.getColumn(INT_TBL_RPT_VAL_EST_VEN_1).setCellRenderer(objTblCelRenLblNumRpt);
            tcmAux.getColumn(INT_TBL_RPT_VAL_PAG_VEN_1).setCellRenderer(objTblCelRenLblNumRpt);            
            tcmAux.getColumn(INT_TBL_RPT_VAL_EST_VEN_2).setCellRenderer(objTblCelRenLblNumRpt);
            tcmAux.getColumn(INT_TBL_RPT_VAL_PAG_VEN_2).setCellRenderer(objTblCelRenLblNumRpt);            
            tcmAux.getColumn(INT_TBL_RPT_VAL_EST_VEN_3).setCellRenderer(objTblCelRenLblNumRpt);
            tcmAux.getColumn(INT_TBL_RPT_VAL_PAG_VEN_3).setCellRenderer(objTblCelRenLblNumRpt);             
            tcmAux.getColumn(INT_TBL_RPT_VAL_NOT_CRE).setCellRenderer(objTblCelRenLblNumRpt);            
            objTblCelRenLblNumRpt=null;                
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModDat.setColumnDataType(INT_TBL_RPT_VAL_PED, objTblModDat.INT_COL_DBL, new Integer(0), null); 
            objTblModDat.setColumnDataType(INT_TBL_RPT_VAL_PAG_TOT, objTblModDat.INT_COL_DBL, new Integer(0), null);       
            objTblModDat.setColumnDataType(INT_TBL_RPT_VAL_SOL_ANT_PRV_1, objTblModDat.INT_COL_DBL, new Integer(0), null);       
            objTblModDat.setColumnDataType(INT_TBL_RPT_VAL_SOL_ANT_PRV_2, objTblModDat.INT_COL_DBL, new Integer(0), null);       
            objTblModDat.setColumnDataType(INT_TBL_RPT_VAL_SOL_SAL_PRV, objTblModDat.INT_COL_DBL, new Integer(0), null);       
            objTblModDat.setColumnDataType(INT_TBL_RPT_VAL_EST_VEN_1, objTblModDat.INT_COL_DBL, new Integer(0), null);       
            objTblModDat.setColumnDataType(INT_TBL_RPT_VAL_PAG_VEN_1, objTblModDat.INT_COL_DBL, new Integer(0), null);       
            objTblModDat.setColumnDataType(INT_TBL_RPT_VAL_EST_VEN_2, objTblModDat.INT_COL_DBL, new Integer(0), null);       
            objTblModDat.setColumnDataType(INT_TBL_RPT_VAL_PAG_VEN_2, objTblModDat.INT_COL_DBL, new Integer(0), null);       
            objTblModDat.setColumnDataType(INT_TBL_RPT_VAL_EST_VEN_3, objTblModDat.INT_COL_DBL, new Integer(0), null);       
            objTblModDat.setColumnDataType(INT_TBL_RPT_VAL_PAG_VEN_3, objTblModDat.INT_COL_DBL, new Integer(0), null);  
            objTblModDat.setColumnDataType(INT_TBL_RPT_VAL_NOT_CRE, objTblModDat.INT_COL_DBL, new Integer(0), null);  
            
            //Configurar JTable: Establecer el modo de operación.
            objTblModDat.setModoOperacion(objTblModDat.INT_TBL_INS);     
            
            // Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblModDat.addTableModelListener(objTblModLis);              
            
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
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaRpt extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_RPT_LIN:
                    strMsg="";
                    break;       
                case INT_TBL_RPT_NUM_PED:
                    strMsg="Número de Orden (Número de Pedido)";
                    break;      
                case INT_TBL_RPT_PED_ASO:
                    strMsg="Pedido Asociado (Nota Pedido Previa)";
                    break;    
                case INT_TBL_RPT_VAL_PED:
                    strMsg ="<html><h3 style='text-align:center;'>Colores utilizados en la tabla</h3><table border='1'>";
                    strMsg+="<tr><td><b>Fondo</b></td><td><b>Fuente</b></td><td><b>Observación</b></td></tr>";
                    strMsg+="<tr><td>&nbsp;</td><td style='background-color: #FF0000'>&nbsp;</td><td>Indica que es el \"Valor Proforma\".</td></tr>"; //Color Rojo
                    strMsg+="<tr><td>&nbsp;</td><td style='background-color: #FFFFFF'>&nbsp;</td><td>Indica que es el \"Valor Factura\".</td></tr>"; //Color Negro
                    strMsg+="</table><br></html>";                    
                    break;      
                case INT_TBL_RPT_VAL_PAG_TOT:
                    strMsg="Valor Total Pagado (ValSalPrv+ValAnt1+ValAnt2+ValVen1+ValVen2+ValVen3-NotCre)";
                    break;      
                case INT_TBL_RPT_CHK_VER_PAG:
                    strMsg ="<html><h3 style='text-align:center;'>Colores utilizados en la tabla</h3><table border='1'>";
                    strMsg+="<tr><td><b>Fondo</b></td><td><b>Fuente</b></td><td><b>Observación</b></td></tr>"; 
                    strMsg+="<tr><td style='background-color: #FFDDBB'>&nbsp;</td><td>&nbsp;</td><td>Indica que el \"Valor de Factura es distinto a Valor Pagado\".</td></tr>"; //Color Naranja
                    strMsg+="</table><br></html>";                    
                    break;    
                case INT_TBL_RPT_NOM_RUB_MAT:
                    strMsg="[100] Material";
                    break;     
                case INT_TBL_RPT_NOM_IMP:
                    strMsg="[80] Importador";
                    break;     
                case INT_TBL_RPT_DES_DIR_IND:
                    strMsg="[20] Directo/Indirecto";
                    break;     
                case INT_TBL_RPT_DES_TIP_PED:
                    strMsg="[20] Incoterms";
                    break;     
                case INT_TBL_RPT_DES_PTO_EMB:
                    strMsg="[20]Puerto de Embarque";
                    break;         
                case INT_TBL_RPT_NOM_PRV:
                    strMsg="[120] Proveedor del Exterior";
                    break;   
                case INT_TBL_RPT_DES_FOR_PAG:
                    strMsg="[100] Forma de Pago";
                    break;                        
                case INT_TBL_RPT_FEC_PRO_EST:
                    strMsg="Fecha de Producción Estimada";
                    break;                        
                case INT_TBL_RPT_FEC_SEG_ORD:
                    strMsg="Fecha de Seguimiento de Orden";
                    break;    
                case INT_TBL_RPT_DES_REQ_ORD:
                    strMsg="[100] Descripción del Requerimiento";
                    break;        
                case INT_TBL_RPT_FEC_PRE_IMP:
                    strMsg="Fecha de Presupuesto de Importación";
                    break;    
                case INT_TBL_RPT_FEC_COL_PED:
                    strMsg="Fecha de Colocación de Pedido";
                    break;                        
                case INT_TBL_RPT_FEC_REC_PRO:
                    strMsg="Fecha de Recepción de Proforma";
                    break;    
                case INT_TBL_RPT_FEC_CON_PRO:
                    strMsg="Fecha de Confirmación de Orden";
                    break;        
                case INT_TBL_RPT_FEC_DOC_NPP:
                    strMsg="Fecha de documento de Nota de Pedido Previa";
                    break;    
                case INT_TBL_RPT_FEC_NOT_PED_IND:
                    strMsg="Fecha de Notificación de Orden Indirectas (Havelock)";
                    break;                        
                case INT_TBL_RPT_FEC_DOC_NOT_PED:
                    strMsg="Fecha de documento de Nota de Pedido";
                    break;    
                case INT_TBL_RPT_FEC_SOL_ANT_PRV_1:
                    strMsg="Fecha de solicitud de anticipo a proveedor 1";
                    break;        
                case INT_TBL_RPT_VAL_SOL_ANT_PRV_1:
                    strMsg="Valor solicitado de anticipo a proveedor 1";
                    break;    
                case INT_TBL_RPT_FEC_PAG_ANT_PRV_1:
                    strMsg="Fecha de pago de anticipo a proveedor 1";
                    break;               
                case INT_TBL_RPT_TXT_OBS_ANT_PRV_1:
                    strMsg="[20]Observación de anticipo a proveedor 1";
                    break;                               
                case INT_TBL_RPT_FEC_SOL_ANT_PRV_2:
                    strMsg="Fecha de solicitud de anticipo a proveedor 2";
                    break;        
                case INT_TBL_RPT_VAL_SOL_ANT_PRV_2:
                    strMsg="Valor solicitado de anticipo a proveedor 2";
                    break;    
                case INT_TBL_RPT_FEC_PAG_ANT_PRV_2:
                    strMsg="Fecha de pago de anticipo a proveedor 2";
                    break;    
                case INT_TBL_RPT_TXT_OBS_ANT_PRV_2:
                    strMsg="[20]Observación de anticipo a proveedor 2";
                    break;                        
                case INT_TBL_RPT_FEC_REC_FAC_PRV:
                    strMsg="Fecha Recepción C.I.+P/L - Fecha Recepción de Factura";
                    break;   
                case INT_TBL_RPT_FEC_SOL_AGE:
                    strMsg="Fecha Solicitud Agente";
                    break;    
                case INT_TBL_RPT_FEC_ASI_AGE:
                    strMsg="Fecha Asignación Agente";
                    break;   
                case INT_TBL_RPT_DES_PED_CON:
                    strMsg="[20]Consolidación";
                    break;                      
                case INT_TBL_RPT_FEC_EMB_EST:
                    strMsg="Fecha de Embarque Estimada";
                    break;    
                case INT_TBL_RPT_FEC_EMB_BOK:
                    strMsg="Fecha de Booking";
                    break;        
                case INT_TBL_RPT_FEC_EMB_BL:
                    strMsg="Fecha de Embarque BL";
                    break;                        
                case INT_TBL_RPT_FEC_PUE:
                    strMsg="Fecha de Puerto (E.T.A.)";
                    break;    
                case INT_TBL_RPT_FEC_ARR_BOD:
                    strMsg="Fecha de Arribo";
                    break;                        
                case INT_TBL_RPT_FEC_BAS_PAG_1:
                    strMsg="Fecha de Pago Estimada 1";
                    break;    
                case INT_TBL_RPT_FEC_BAS_PAG_2:
                    strMsg="Fecha de Pago Estimada 2";
                    break;        
                case INT_TBL_RPT_FEC_BAS_PAG_3:
                    strMsg="Fecha de Pago Estimada 3";
                    break;
                case INT_TBL_RPT_FEC_BAS_PAG_4:
                    strMsg="Fecha de Pago Estimada 4";
                    break;    
                case INT_TBL_RPT_FEC_PAG_REA_1:
                    strMsg="Fecha de Pago Real 1";
                    break;    
                case INT_TBL_RPT_FEC_PAG_REA_2:
                    strMsg="Fecha de Pago Real 2";
                    break;        
                case INT_TBL_RPT_FEC_PAG_REA_3:
                    strMsg="Fecha de Pago Real 3";
                    break;
                case INT_TBL_RPT_FEC_PAG_REA_4:
                    strMsg="Fecha de Pago Real 4";
                    break;                      
                case INT_TBL_RPT_FEC_SOL_SAL_PRV:
                    strMsg="Fecha de Solicitud Saldo para Proveedor";
                    break;                        
                case INT_TBL_RPT_VAL_SOL_SAL_PRV:
                    strMsg="Valor Saldo a la Vista a Proveedor";
                    break;    
                case INT_TBL_RPT_FEC_PAG_SAL_PRV:
                    strMsg="Fecha de Pago Saldo a la Vista a Proveedor";
                    break;    
                case INT_TBL_RPT_TXT_OBS_SAL_PRV:
                    strMsg="[20]Observación de saldo a proveedor";
                    break;                        
                case INT_TBL_RPT_FEC_EST_VEN_1:
                    strMsg="Fecha Vencimiento 1";
                    break;     
                case INT_TBL_RPT_VAL_EST_VEN_1:
                    strMsg="Valor Vencimiento 1";
                    break;                        
                case INT_TBL_RPT_FEC_PAG_VEN_1:
                    strMsg="Fecha Pago de Saldo a Proveedor - Vencimiento 1";
                    break;    
                case INT_TBL_RPT_VAL_PAG_VEN_1:
                    strMsg="Valor Pagado a Vencimiento 1";
                    break;  
                case INT_TBL_RPT_FEC_EST_VEN_2:
                    strMsg="Fecha Vencimiento 2";
                    break;     
                case INT_TBL_RPT_VAL_EST_VEN_2:
                    strMsg="Valor Vencimiento 2";
                    break;                        
                case INT_TBL_RPT_FEC_PAG_VEN_2:
                    strMsg="Fecha Pago de Saldo a Proveedor - Vencimiento 2";
                    break;    
                case INT_TBL_RPT_VAL_PAG_VEN_2:
                    strMsg="Valor Pagado a Vencimiento 2";
                    break;     
                case INT_TBL_RPT_FEC_EST_VEN_3:
                    strMsg="Fecha Vencimiento 3";
                    break;     
                case INT_TBL_RPT_VAL_EST_VEN_3:
                    strMsg="Valor Vencimiento 3";
                    break;                        
                case INT_TBL_RPT_FEC_PAG_VEN_3:
                    strMsg="Fecha Pago de Saldo a Proveedor - Vencimiento 3";
                    break; 
                case INT_TBL_RPT_VAL_PAG_VEN_3:
                    strMsg="Valor Pagado a Vencimiento 3";
                    break;  
                case INT_TBL_RPT_VAL_NOT_CRE:
                    strMsg="Valor Nota de Credito";
                    break;                      
                case INT_TBL_RPT_TXT_OBS_COM:
                    strMsg="[100] Observación: Comprobación";
                    break;                    
                default:
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }    
    
    private void mostrarFormularioPedido(int fila){
        try{
            String strCodEmpPed=objTblModDat.getValueAt(fila, INT_TBL_DAT_COD_EMP).toString();
            String strCodLocPed=objTblModDat.getValueAt(fila, INT_TBL_DAT_COD_LOC).toString();
            String strCodTipDocPed=objTblModDat.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC).toString();
            String strCodDocPed=objTblModDat.getValueAt(fila, INT_TBL_DAT_COD_DOC).toString();
            String strInsPed=objTblModDat.getValueAt(fila, INT_TBL_DAT_INS_PED).toString();
            
            if(strInsPed.equals("NOTPED")){   //Nota de Pedido
                Importaciones.ZafImp32.ZafImp32 objVcoPed=new Importaciones.ZafImp32.ZafImp32(objParSis, Integer.parseInt(strCodLocPed), Integer.parseInt(strCodTipDocPed), Integer.parseInt(strCodDocPed));
                this.getParent().add(objVcoPed,javax.swing.JLayeredPane.DEFAULT_LAYER);
                objVcoPed.setVisible(true);
                objVcoPed=null;
            }
            else if(strInsPed.equals("PEDEMB")){  //Pedido Embarcado
                Importaciones.ZafImp33.ZafImp33 objVcoPed=new Importaciones.ZafImp33.ZafImp33(objParSis, Integer.parseInt(strCodTipDocPed), Integer.parseInt(strCodDocPed));
                this.getParent().add(objVcoPed,javax.swing.JLayeredPane.DEFAULT_LAYER);
                objVcoPed.setVisible(true);
                objVcoPed=null;
            }
            else if(strInsPed.equals("INIMPO")){  //Ingreso por Importación
                Importaciones.ZafImp34.ZafImp34 objVcoPed=new Importaciones.ZafImp34.ZafImp34(objParSis, Integer.parseInt(strCodEmp), Integer.parseInt(strCodEmpPed), Integer.parseInt(strCodTipDocPed), Integer.parseInt(strCodDocPed));
                this.getParent().add(objVcoPed,javax.swing.JLayeredPane.DEFAULT_LAYER);
                objVcoPed.setVisible(true);
                objVcoPed=null;
            }            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
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
                        txtCodImp.setText(vcoEmp.getValueAt(1));
                        txtNomImp.setText(vcoEmp.getValueAt(2));
                        txtCodExp.setEditable(true);
                        txtNomExp.setEditable(true);
                        butExp.setEnabled(true);
                        txtCodExp.setText("");
                        txtNomExp.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodImp.getText())){
                        txtCodImp.setText(vcoEmp.getValueAt(1));
                        txtNomImp.setText(vcoEmp.getValueAt(2));
                        txtCodExp.setEditable(true);
                        txtNomExp.setEditable(true);
                        butExp.setEnabled(true);
                        txtCodExp.setText("");
                        txtNomExp.setText("");
                    }
                    else{
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodImp.setText(vcoEmp.getValueAt(1));
                            txtNomImp.setText(vcoEmp.getValueAt(2));
                            txtCodExp.setEditable(true);
                            txtNomExp.setEditable(true);
                            butExp.setEnabled(true);
                            txtCodExp.setText("");
                            txtNomExp.setText("");
                        }
                        else{
                            txtCodImp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomImp.getText())){
                        txtCodImp.setText(vcoEmp.getValueAt(1));
                        txtNomImp.setText(vcoEmp.getValueAt(2));
                        txtCodExp.setEditable(true);
                        txtNomExp.setEditable(true);
                        butExp.setEnabled(true);
                        txtCodExp.setText("");
                        txtNomExp.setText("");
                    }
                    else{
                        vcoEmp.setCampoBusqueda(2);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodImp.setText(vcoEmp.getValueAt(1));
                            txtNomImp.setText(vcoEmp.getValueAt(2));
                            txtCodExp.setEditable(true);
                            txtNomExp.setEditable(true);
                            butExp.setEnabled(true);
                            txtCodExp.setText("");
                            txtNomExp.setText("");
                        }
                        else{
                            txtNomImp.setText(strNomEmp);
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
            arlAncCol.add("414");
            //Armar la sentencia SQL.

            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom";
                strSQL+=" FROM tbm_emp AS a1";
                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" AND a1.st_reg NOT IN('I','E')";
                strSQL+=" ORDER BY a1.co_emp";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom";
                strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" AND a1.st_reg NOT IN('I','E')";
                strSQL+=" ORDER BY a1.co_emp";
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
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConExp(int intTipBus)
    {
        boolean blnRes=true;
        String strSQLTmp="";
        try{               
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoExp.setCampoBusqueda(1);
                    vcoExp.show();
                    if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                    {
                        txtCodExp.setText(vcoExp.getValueAt(1));
                        txtNomExp.setText(vcoExp.getValueAt(2));
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoExp.buscar("a1.co_exp", txtCodExp.getText()))
                    {
                        txtCodExp.setText(vcoExp.getValueAt(1));
                        txtNomExp.setText(vcoExp.getValueAt(2));
                    }
                    else
                    {
                        vcoExp.setCampoBusqueda(0);
                        vcoExp.setCriterio1(11);
                        vcoExp.cargarDatos();
                        vcoExp.show();
                        if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp.setText(vcoExp.getValueAt(2));
                        }
                        else
                        {
                            txtCodExp.setText(strCodExp);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoExp.buscar("a1.tx_nom", txtNomExp.getText()))
                    {
                        txtCodExp.setText(vcoExp.getValueAt(1));
                        txtNomExp.setText(vcoExp.getValueAt(2));
                    }
                    else
                    {
                        vcoExp.setCampoBusqueda(2);
                        vcoExp.setCriterio1(11);
                        vcoExp.cargarDatos();
                        vcoExp.show();
                        if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp.setText(vcoExp.getValueAt(2));
                        }
                        else
                        {
                            txtNomExp.setText(strNomExp);
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
     * mostrar los "Exportadores".
     */
    private boolean configurarVenConExp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_exp");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_nom2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            arlAli.add("Nombre alterno");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("225");
            arlAncCol.add("225");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_exp, a1.tx_nom, a1.tx_nom2";
            strSQL+=" FROM tbm_expImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_nom";
            vcoExp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de exportadores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoExp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT); 
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
     * mostrar los "Pedidos Asociados".
     */
    private boolean configurarVenConPedAso()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.co_loc");
            arlCam.add("a1.co_tipDoc");
            arlCam.add("a1.co_doc");
            arlCam.add("a1.tx_numPed");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Emp.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Cód.Tip.Doc");
            arlAli.add("Cód.Doc.");
            arlAli.add("Núm.Ped.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("300"); 
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_Doc, a1.tx_numDoc2 AS tx_numPed";
            strSQL+=" FROM tbm_cabNotPedPreImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'"; 
            strSQL+=" AND NOT ( (a1.tx_numDoc2 LIKE '%PS%') OR (a1.tx_numDoc2 LIKE '%PL%') )"; /* No mostrar compras locales. */
            strSQL+=" AND a1.fe_doc>'2018-01-01'";
            strSQL+=" ORDER BY a1.tx_numDoc2";            
            vcoPedAso=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado pedidos asociados", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoPedAso.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoPedAso.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
            vcoPedAso.setConfiguracionColumna(3, javax.swing.JLabel.RIGHT);
            vcoPedAso.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Empresas Importadoras".
     */
    private boolean configurarVenConEmpImp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nomEmpImp");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Imp.");
            arlAli.add("Importador");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("300"); 
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_emp, a1.tx_nom AS tx_nomEmpImp";
            strSQL+=" FROM tbm_emp AS a1";
            strSQL+=" WHERE a1.st_reg='A' "; 
            strSQL+=" ORDER BY a1.co_emp";            
            vcoEmpImp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado Importadores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmpImp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }        
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de Pedidos".
     */
    private boolean configurarVenConTipPed()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipPed");
            arlCam.add("a1.tx_DesTipPed");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Tip.Ped.");
            arlAli.add("Tipo de Pedido");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("300"); 
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT * FROM (";
            strSQL+=" 	SELECT 0 AS co_tipPed, 'Directo' AS tx_DesTipPed";
            strSQL+=" 	UNION ALL";
            strSQL+=" 	SELECT 1 AS co_tipPed, 'Indirecto' AS tx_DesTipPed";
            strSQL+=" ) AS x";      
            strSQL+=" ORDER BY x.co_tipPed";   
            vcoTipPed=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tipos de Pedidos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoTipPed.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }      
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores del Exterior".
     */
    private boolean configurarVenConPrvExt()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_prvExt");
            arlCam.add("a1.tx_nomPrvExt");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Prv.Ext.");
            arlAli.add("Proveedor Exportador");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("300"); 
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_exp AS co_prvExt, a1.tx_nom AS tx_nomPrvExt";
            strSQL+=" FROM tbm_expImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_nom";
            vcoPrvExt=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Proveedores del Exterior", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoPrvExt.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
        panCon = new javax.swing.JPanel();
        panFecDoc = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblEmpImp = new javax.swing.JLabel();
        txtCodImp = new javax.swing.JTextField();
        txtNomImp = new javax.swing.JTextField();
        butImp = new javax.swing.JButton();
        lblExp = new javax.swing.JLabel();
        txtCodExp = new javax.swing.JTextField();
        txtNomExp = new javax.swing.JTextField();
        butExp = new javax.swing.JButton();
        chkRptConPro = new javax.swing.JCheckBox();
        panPedTra = new javax.swing.JPanel();
        chkMosNotPed = new javax.swing.JCheckBox();
        chkMosPedEmb = new javax.swing.JCheckBox();
        chkMosPedLleDir = new javax.swing.JCheckBox();
        chkMosPedLleInd = new javax.swing.JCheckBox();
        chkMosCarPag = new javax.swing.JCheckBox();
        chkMosTotCarPag = new javax.swing.JCheckBox();
        chkMosFecVenEst = new javax.swing.JCheckBox();
        chkMosFecVenRea = new javax.swing.JCheckBox();
        chkRptPedTra = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        sppRpt = new javax.swing.JSplitPane();
        panRptFix = new javax.swing.JPanel();
        spnFix = new javax.swing.JScrollPane();
        tblFix = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panRptDat = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panTotDat = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
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

        panFrm.setPreferredSize(new java.awt.Dimension(475, 311));
        panFrm.setLayout(new java.awt.BorderLayout());

        tabFrm.setPreferredSize(new java.awt.Dimension(475, 311));

        panCon.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panCon.setLayout(new java.awt.BorderLayout());

        panFecDoc.setPreferredSize(new java.awt.Dimension(0, 70));
        panFecDoc.setLayout(new java.awt.BorderLayout());
        panCon.add(panFecDoc, java.awt.BorderLayout.NORTH);

        panFil.setPreferredSize(new java.awt.Dimension(0, 250));
        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 0, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(10, 20, 400, 20);

        lblEmpImp.setText("Empresa(Importador):");
        lblEmpImp.setToolTipText("Vendedor/Comprador");
        panFil.add(lblEmpImp);
        lblEmpImp.setBounds(40, 45, 140, 20);

        txtCodImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodImpFocusLost(evt);
            }
        });
        txtCodImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodImpActionPerformed(evt);
            }
        });
        panFil.add(txtCodImp);
        txtCodImp.setBounds(180, 45, 70, 20);

        txtNomImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomImpFocusLost(evt);
            }
        });
        txtNomImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomImpActionPerformed(evt);
            }
        });
        panFil.add(txtNomImp);
        txtNomImp.setBounds(250, 45, 360, 20);

        butImp.setText("...");
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panFil.add(butImp);
        butImp.setBounds(610, 45, 20, 20);

        lblExp.setText("Exportador:");
        lblExp.setToolTipText("Vendedor/Comprador");
        panFil.add(lblExp);
        lblExp.setBounds(40, 65, 140, 20);

        txtCodExp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodExpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodExpFocusLost(evt);
            }
        });
        txtCodExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodExpActionPerformed(evt);
            }
        });
        panFil.add(txtCodExp);
        txtCodExp.setBounds(180, 65, 70, 20);

        txtNomExp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomExpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomExpFocusLost(evt);
            }
        });
        txtNomExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomExpActionPerformed(evt);
            }
        });
        panFil.add(txtNomExp);
        txtNomExp.setBounds(250, 65, 360, 20);

        butExp.setText("...");
        butExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpActionPerformed(evt);
            }
        });
        panFil.add(butExp);
        butExp.setBounds(610, 65, 20, 20);

        chkRptConPro.setText("Control de Procesos");
        chkRptConPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRptConProActionPerformed(evt);
            }
        });
        panFil.add(chkRptConPro);
        chkRptConPro.setBounds(400, 95, 260, 20);

        panPedTra.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panPedTra.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panPedTra.setLayout(null);

        chkMosNotPed.setSelected(true);
        chkMosNotPed.setText("Mostrar Notas de Pedido");
        chkMosNotPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosNotPedActionPerformed(evt);
            }
        });
        panPedTra.add(chkMosNotPed);
        chkMosNotPed.setBounds(25, 30, 200, 16);

        chkMosPedEmb.setSelected(true);
        chkMosPedEmb.setText("Mostrar Pedidos Embarcados");
        chkMosPedEmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosPedEmbActionPerformed(evt);
            }
        });
        panPedTra.add(chkMosPedEmb);
        chkMosPedEmb.setBounds(25, 46, 200, 16);

        chkMosPedLleDir.setSelected(true);
        chkMosPedLleDir.setText("Mostrar Pedidos Llegados (Directos)");
        chkMosPedLleDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosPedLleDirActionPerformed(evt);
            }
        });
        panPedTra.add(chkMosPedLleDir);
        chkMosPedLleDir.setBounds(25, 62, 335, 16);

        chkMosPedLleInd.setText("Mostrar Pedidos Llegados (Indirectos - Havelock)");
        chkMosPedLleInd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosPedLleIndActionPerformed(evt);
            }
        });
        panPedTra.add(chkMosPedLleInd);
        chkMosPedLleInd.setBounds(25, 78, 335, 16);

        chkMosCarPag.setSelected(true);
        chkMosCarPag.setText("Mostrar Cargos a Pagar");
        chkMosCarPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosCarPagActionPerformed(evt);
            }
        });
        panPedTra.add(chkMosCarPag);
        chkMosCarPag.setBounds(25, 94, 200, 16);

        chkMosTotCarPag.setSelected(true);
        chkMosTotCarPag.setText("Mostrar Total Cargos a Pagar");
        chkMosTotCarPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosTotCarPagActionPerformed(evt);
            }
        });
        panPedTra.add(chkMosTotCarPag);
        chkMosTotCarPag.setBounds(25, 110, 200, 16);

        chkMosFecVenEst.setSelected(true);
        chkMosFecVenEst.setText("Mostrar Fechas de Vencimiento Estimadas");
        chkMosFecVenEst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosFecVenEstActionPerformed(evt);
            }
        });
        panPedTra.add(chkMosFecVenEst);
        chkMosFecVenEst.setBounds(25, 125, 290, 16);

        chkMosFecVenRea.setSelected(true);
        chkMosFecVenRea.setText("Mostrar Fechas de Vencimiento Reales");
        chkMosFecVenRea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosFecVenReaActionPerformed(evt);
            }
        });
        panPedTra.add(chkMosFecVenRea);
        chkMosFecVenRea.setBounds(25, 143, 290, 16);

        chkRptPedTra.setSelected(true);
        chkRptPedTra.setText("Listado de Pedidos en Tránsito");
        chkRptPedTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRptPedTraActionPerformed(evt);
            }
        });
        panPedTra.add(chkRptPedTra);
        chkRptPedTra.setBounds(5, 5, 300, 20);

        panFil.add(panPedTra);
        panPedTra.setBounds(20, 90, 365, 165);

        panCon.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panCon);

        panRpt.setLayout(new java.awt.BorderLayout());

        sppRpt.setDividerLocation(180);
        sppRpt.setDividerSize(2);

        panRptFix.setLayout(new java.awt.BorderLayout());

        tblFix.setModel(new javax.swing.table.DefaultTableModel(
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
        tblFix.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblFix.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblFixFocusGained(evt);
            }
        });
        spnFix.setViewportView(tblFix);

        panRptFix.add(spnFix, java.awt.BorderLayout.CENTER);

        sppRpt.setLeftComponent(panRptFix);

        panRptDat.setLayout(new java.awt.BorderLayout());

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
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblDat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblDatFocusGained(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        panRptDat.add(spnDat, java.awt.BorderLayout.CENTER);

        panTotDat.setLayout(new java.awt.BorderLayout());
        panRptDat.add(panTotDat, java.awt.BorderLayout.SOUTH);

        sppRpt.setRightComponent(panRptDat);

        panRpt.add(sppRpt, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(350, 42));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 40));
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

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 17));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout());

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodImp.setText("");
            txtNomImp.setText("");
            txtCodExp.setText("");
            txtNomExp.setText("");
            //cboEstDoc.setSelectedIndex(0);
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

    private void txtCodImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodImpActionPerformed
        txtCodImp.transferFocus();
    }//GEN-LAST:event_txtCodImpActionPerformed

    private void txtCodImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusGained
        strCodEmp=txtCodImp.getText();
        txtCodImp.selectAll();
    }//GEN-LAST:event_txtCodImpFocusGained

    private void txtCodImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusLost
        if (!txtCodImp.getText().equalsIgnoreCase(strCodEmp)){
            if (txtCodImp.getText().equals("")){
                txtCodImp.setText("");
                txtNomImp.setText("");
                objTblModDat.removeAllRows();
                txtCodExp.setText("");
                txtNomExp.setText("");
            }
            else
                mostrarVenConEmp(1);
        }
        else
            txtCodImp.setText(strCodEmp);
    }//GEN-LAST:event_txtCodImpFocusLost

    private void txtNomImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomImpActionPerformed
        txtNomImp.transferFocus();
    }//GEN-LAST:event_txtNomImpActionPerformed

    private void txtNomImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusGained
        strNomEmp=txtNomImp.getText();
        txtNomImp.selectAll();
    }//GEN-LAST:event_txtNomImpFocusGained

    private void txtNomImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusLost
        if (!txtNomImp.getText().equalsIgnoreCase(strNomEmp))
        {
            if (txtNomImp.getText().equals(""))
            {
                txtCodImp.setText("");
                txtNomImp.setText("");
                objTblModDat.removeAllRows();
            }
            else
            {
                mostrarVenConEmp(2);
            }
        }
        else
            txtNomImp.setText(strNomEmp);
    }//GEN-LAST:event_txtNomImpFocusLost

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        strCodEmp=txtCodImp.getText();
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butImpActionPerformed

    private void txtCodExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodExpActionPerformed
        txtCodExp.transferFocus();
    }//GEN-LAST:event_txtCodExpActionPerformed

    private void txtCodExpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusGained
        strCodExp=txtCodExp.getText();
        txtCodExp.selectAll();
    }//GEN-LAST:event_txtCodExpFocusGained

    private void txtCodExpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodExp.getText().equalsIgnoreCase(strCodExp)){
            if (txtCodExp.getText().equals("")){
                txtCodExp.setText("");
                txtNomExp.setText("");
                objTblModDat.removeAllRows();
            }
            else
                mostrarVenConExp(1);
        }
        else
            txtCodExp.setText(strCodExp);
    }//GEN-LAST:event_txtCodExpFocusLost

    private void txtNomExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomExpActionPerformed
        txtNomExp.transferFocus();
    }//GEN-LAST:event_txtNomExpActionPerformed

    private void txtNomExpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomExpFocusGained
        strNomExp=txtNomExp.getText();
        txtNomExp.selectAll();
    }//GEN-LAST:event_txtNomExpFocusGained

    private void txtNomExpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomExpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomExp.getText().equalsIgnoreCase(strNomExp))
        {
            if (txtNomExp.getText().equals(""))
            {
                txtCodExp.setText("");
                txtNomExp.setText("");
                objTblModDat.removeAllRows();
            }
            else
            {
                mostrarVenConExp(2);
            }
        }
        else
            txtNomExp.setText(strNomExp);
    }//GEN-LAST:event_txtNomExpFocusLost

    private void butExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpActionPerformed
        strCodExp=txtCodExp.getText();
        mostrarVenConExp(0);
    }//GEN-LAST:event_butExpActionPerformed

    private void chkMosNotPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosNotPedActionPerformed
//        if(chkMosNotPedAbi.isSelected()){
//            objTblModDat.removeSystemHiddenColumn(INT_TBL_DAT_BUT_NOT_PED, tblDat);
//        }
//        else{
//            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_BUT_NOT_PED, tblDat);
//        }
    }//GEN-LAST:event_chkMosNotPedActionPerformed

    private void chkMosPedEmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosPedEmbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkMosPedEmbActionPerformed

    private void chkMosCarPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosCarPagActionPerformed
        //ocultarMostrarColDin();
    }//GEN-LAST:event_chkMosCarPagActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblModDat.isDataModelChanged())
        {      
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                if(isCamVal())
                {
                    if (guardar())
                    {
                        mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                        if(chkRptConPro.isSelected()){
                            cargarReg();
                        }
                    }
                    else
                        mostrarMsgErr("<HTML>Ocurrió un error al realizar la operación GUARDAR.<BR>Intente realizar la operación nuevamente.<BR>Si el problema persiste notifiquelo a su administrador del sistema.</HTML>");
                }
                else
                    mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
            }
        }
        else
            mostrarMsgInf("No se han realizado cambios que guardar.");


    }//GEN-LAST:event_butGuaActionPerformed

    private void chkMosFecVenEstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosFecVenEstActionPerformed
        //ocultarMostrarColDin();
    }//GEN-LAST:event_chkMosFecVenEstActionPerformed

    private void chkMosFecVenReaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosFecVenReaActionPerformed
        //ocultarMostrarColDin();
    }//GEN-LAST:event_chkMosFecVenReaActionPerformed

    private void chkMosTotCarPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosTotCarPagActionPerformed
        //ocultarMostrarColDin();
    }//GEN-LAST:event_chkMosTotCarPagActionPerformed

    private void chkMosPedLleDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosPedLleDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkMosPedLleDirActionPerformed

    private void chkMosPedLleIndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosPedLleIndActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkMosPedLleIndActionPerformed

    private void chkRptConProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRptConProActionPerformed
        if(chkRptConPro.isSelected()){
            chkRptPedTra.setSelected(false);
            //objSelFecDoc.setCheckBoxChecked(true);
        }
        else if(chkRptPedTra.isSelected()) {
            chkRptConPro.setSelected(false);
            objSelFecDoc.setCheckBoxChecked(false);            
            chkMosNotPed.setSelected(true);
            chkMosPedEmb.setSelected(true);
            chkMosPedLleDir.setSelected(true);
            //chkMosPedLleInd.setSelected(true);
            chkMosCarPag.setSelected(true);
            chkMosTotCarPag.setSelected(true);
            chkMosFecVenEst.setSelected(true);
            chkMosFecVenRea.setSelected(true);            
        }
    }//GEN-LAST:event_chkRptConProActionPerformed

    private void tblFixFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblFixFocusGained

    }//GEN-LAST:event_tblFixFocusGained

    private void tblDatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblDatFocusGained

    }//GEN-LAST:event_tblDatFocusGained

    private void chkRptPedTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRptPedTraActionPerformed
        if(chkRptPedTra.isSelected()){
            chkRptConPro.setSelected(false);
            objSelFecDoc.setCheckBoxChecked(false);
            chkMosNotPed.setSelected(true);
            chkMosPedEmb.setSelected(true);
            chkMosPedLleDir.setSelected(true);
            //chkMosPedLleInd.setSelected(true);
            chkMosCarPag.setSelected(true);
            chkMosTotCarPag.setSelected(true);
            chkMosFecVenEst.setSelected(true);
            chkMosFecVenRea.setSelected(true);
        }
        else if(chkRptConPro.isSelected()) {
            chkRptPedTra.setSelected(false);
            //objSelFecDoc.setCheckBoxChecked(true);
        }
    }//GEN-LAST:event_chkRptPedTraActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butExp;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butImp;
    private javax.swing.JCheckBox chkMosCarPag;
    private javax.swing.JCheckBox chkMosFecVenEst;
    private javax.swing.JCheckBox chkMosFecVenRea;
    private javax.swing.JCheckBox chkMosNotPed;
    private javax.swing.JCheckBox chkMosPedEmb;
    private javax.swing.JCheckBox chkMosPedLleDir;
    private javax.swing.JCheckBox chkMosPedLleInd;
    private javax.swing.JCheckBox chkMosTotCarPag;
    private javax.swing.JCheckBox chkRptConPro;
    private javax.swing.JCheckBox chkRptPedTra;
    private javax.swing.JLabel lblEmpImp;
    private javax.swing.JLabel lblExp;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panFecDoc;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPedTra;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panRptDat;
    private javax.swing.JPanel panRptFix;
    private javax.swing.JPanel panTotDat;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFix;
    private javax.swing.JSplitPane sppRpt;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblFix;
    private javax.swing.JTextField txtCodExp;
    private javax.swing.JTextField txtCodImp;
    private javax.swing.JTextField txtNomExp;
    private javax.swing.JTextField txtNomImp;
    // End of variables declaration//GEN-END:variables

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
            if (!cargarReg())
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
    
    private boolean cargarReg(){
        boolean blnRes=false;
        try{           
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());  
            if(con!=null){
                if(chkRptConPro.isSelected())
                {
                    if(configurarTblFixPanCon()){
                        if(configurarTblDatPanCon()){
                            if(cargarDetRegPanCon()){
                                blnRes=true;
                            }
                        }
                    }
                }
                else if(chkRptPedTra.isSelected())  { 
                    if(configurarTblFix()){
                        if(configurarTblDat()){
                            if(getColumnsAdd()){
                                if(cargarDetReg()){
                                    if(cargarValFacAboPen()){
                                        if(cargarValCarPagPed()){ 
                                            if(cargarValTotCarPagPed()){  
                                                if(cargarFecVenPagEst()){ 
                                                    if(cargarFecVenPagRea()){
                                                        if(setColumnasEditables()){ 
                                                            if(deleteColumnas()){ 
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
                        }
                    }
                } 
                con.close();
                con=null;                
                
                if(blnRes){
                    objTblModDat.initRowsState(); 
                    if (blnCon)
                        lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                    else
                        lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                    butCon.setText("Consultar");
                    pgrSis.setIndeterminate(false);
                }
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }      

    
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetRegPanCon()
    {
        boolean blnRes=true;
        int intCodTipDocTraBan=0;
        try
        {               
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            String strAuxFil="";
            if (con!=null){
                intCodTipDocTraBan=objZafImp.INT_COD_TIP_DOC_TRA_BAN_EXT;
                stm=con.createStatement();
                lblTit.setText("Control de Procesos...");

                //Fecha de documento
                if(objSelFecDoc.isCheckBoxChecked()){
                    switch (objSelFecDoc.getTipoSeleccion()){   
                        case 0: //Búsqueda por rangos
                            strAuxFil+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFecDoc.getFechaDesde(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFecDoc.getFechaHasta(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAuxFil+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFecDoc.getFechaHasta(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAuxFil+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFecDoc.getFechaDesde(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
                
                //Armar sentencia SQL
                strSQL="";
                strSQL+=" SELECT  x.*";
                strSQL+=" FROM( ";
                strSQL+="   SELECT a1.*";
                strSQL+="        , a2.co_empNotPedPre, a2.co_locNotPedPre, a2.co_tipDocNotPedPre, a2.co_docNotPedPre";                
                strSQL+="        , a3.co_empNotPed, a3.co_locNotPed, a3.co_tipDocNotPed, a3.co_docNotPed";                
                strSQL+="        , a4.co_empPedEmb, a4.co_locPedEmb, a4.co_tipDocPedEmb, a4.co_docPedEmb";                
                strSQL+="        , a5.co_empMovInv, a5.co_locMovInv, a5.co_tipDocMovInv, a5.co_docMovInv";
                  
                strSQL+="        , a2.tx_numPedAso, a2.fe_docNPP, a3.fe_docNotPed, a2.fe_colPed, a2.fe_preImp";
                
                strSQL+="        , (CASE WHEN a4.fe_embEstPedEmb IS NOT NULL THEN a4.fe_embEstPedEmb ELSE a3.fe_embEstNotPed END) AS fe_embEst, a4.fe_embBL, a3.fe_pueNotPed, a3.fe_arrNotPed";
                
                strSQL+="        , (CASE WHEN a4.fe_venEst1 IS NOT NULL THEN a4.fe_venEst1 ELSE a3.fe_venEst1 END) AS fe_venEst1";
                strSQL+="        , (CASE WHEN a4.fe_venEst2 IS NOT NULL THEN a4.fe_venEst2 ELSE a3.fe_venEst2 END) AS fe_venEst2";
                strSQL+="        , (CASE WHEN a4.fe_venEst3 IS NOT NULL THEN a4.fe_venEst3 ELSE a3.fe_venEst3 END) AS fe_venEst3";
                strSQL+="        , (CASE WHEN a4.fe_venEst4 IS NOT NULL THEN a4.fe_venEst4 ELSE a3.fe_venEst4 END) AS fe_venEst4";
                
                strSQL+="        , (CASE WHEN a4.fe_venRea1 IS NOT NULL THEN a4.fe_venRea1 ELSE a3.fe_venRea1 END) AS fe_venRea1";
                strSQL+="        , (CASE WHEN a4.fe_venRea2 IS NOT NULL THEN a4.fe_venRea2 ELSE a3.fe_venRea2 END) AS fe_venRea2";
                strSQL+="        , (CASE WHEN a4.fe_venRea3 IS NOT NULL THEN a4.fe_venRea3 ELSE a3.fe_venRea3 END) AS fe_venRea3";
                strSQL+="        , (CASE WHEN a4.fe_venRea4 IS NOT NULL THEN a4.fe_venRea4 ELSE a3.fe_venRea4 END) AS fe_venRea4";  
                
                strSQL+="        , (CASE WHEN a4.nd_valPagVenEst1 IS NOT NULL THEN a4.nd_valPagVenEst1 ELSE a3.nd_valPagVenEst1 END) AS nd_valPagVenEst1";
                strSQL+="        , (CASE WHEN a4.nd_valPagVenEst2 IS NOT NULL THEN a4.nd_valPagVenEst2 ELSE a3.nd_valPagVenEst2 END) AS nd_valPagVenEst2";
                strSQL+="        , (CASE WHEN a4.nd_valPagVenEst3 IS NOT NULL THEN a4.nd_valPagVenEst3 ELSE a3.nd_valPagVenEst3 END) AS nd_valPagVenEst3";
                strSQL+="        , (CASE WHEN a4.nd_valPagVenEst4 IS NOT NULL THEN a4.nd_valPagVenEst4 ELSE a3.nd_valPagVenEst4 END) AS nd_valPagVenEst4";
                
                strSQL+="        , (CASE WHEN a4.nd_valPagVen1 IS NOT NULL THEN a4.nd_valPagVen1 ELSE a3.nd_valPagVen1 END) AS nd_valPagVenRea1";
                strSQL+="        , (CASE WHEN a4.nd_valPagVen2 IS NOT NULL THEN a4.nd_valPagVen2 ELSE a3.nd_valPagVen2 END) AS nd_valPagVenRea2";
                strSQL+="        , (CASE WHEN a4.nd_valPagVen3 IS NOT NULL THEN a4.nd_valPagVen3 ELSE a3.nd_valPagVen3 END) AS nd_valPagVenRea3";
                strSQL+="        , (CASE WHEN a4.nd_valPagVen4 IS NOT NULL THEN a4.nd_valPagVen4 ELSE a3.nd_valPagVen4 END) AS nd_valPagVenRea4";       
                
                strSQL+="        , a5.fe_venReaPedLle1, a5.fe_venReaPedLle2, a5.fe_venReaPedLle3, a5.fe_venReaPedLle4";   //Fechas INIMPO
                strSQL+="        , a5.nd_valPagVenReaPedLle1, a5.nd_valPagVenReaPedLle2, a5.nd_valPagVenReaPedLle3, a5.nd_valPagVenReaPedLle4";   //Valores INIMPO
                
                strSQL+="   FROM ( "; /*PEDIMP*/
                strSQL+="        SELECT a.*, a.co_empRelCabNotPedPreImp AS co_empNotPedPre, a.co_locRelCabNotPedPreImp AS co_locNotPedPre, a.co_tipDocRelCabNotPedPreImp AS co_tipDocNotPedPre, a.co_docRelCabNotPedPreImp AS co_docNotPedPre";
                strSQL+="        FROM tbm_cabPedImp AS a  WHERE a.st_Reg IN ('A')";
                strSQL+="   ) AS a1";
                strSQL+="   LEFT OUTER JOIN ( "; /*NOTPEDPRE*/ 
                strSQL+="        SELECT a.co_emp AS co_empNotPedPre, a.co_loc AS co_locNotPedPre, a.co_tipDoc AS co_tipDocNotPedPre, a.co_Doc AS co_docNotPedPre";
                strSQL+="             , a.co_emp AS co_empNotPed, a.co_locRel AS co_locNotPed, a.co_tipDocRel AS co_tipDocNotPed, a.co_docRel AS co_docNotPed";
                strSQL+="             , a.fe_doc AS fe_docNPP, a.fe_colPed, a1.fe_preImp, a.tx_numDoc2 AS tx_numPedAso";
                strSQL+="        FROM tbm_cabNotPedPreImp AS a";
                strSQL+="        LEFT OUTER JOIN ( ";  /* Presupuesto*/
                strSQL+="           SELECT a.co_emp AS co_empPreImp, a.co_loc as co_locPreImp, a.co_TipDoc AS co_tipDocPreImp, a.co_Doc AS co_docPreImp";
                strSQL+="                , a2.co_EmpRelCabNotPedPreImp AS co_empNotPedPre, a2.co_locRelCabNotPedPreImp  AS co_locNotPedPre, a2.co_tipDocRelCabNotPedPreImp AS co_tipDocNotPedPre, a2.co_docRelCabNotPedPreImp AS co_docNotPedPre";
                strSQL+="                , ( (''||a.ne_ani) ||'-'|| (  (CASE WHEN a.ne_mes<=9 THEN '0' ELSE ''END)||'' ||a.ne_mes) ||'-'|| '01')  AS fe_preImp";
                strSQL+="           FROM tbm_cabPreImp AS a INNER JOIN tbm_detPreImp AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc";
                strSQL+="           INNER JOIN tbr_detPreImp AS a2 ON a1.co_Emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_Reg=a2.co_Reg";
                strSQL+="           WHERE a.st_Reg IN ('A') AND a1.st_Reg IN ('A')"; 
                strSQL+="        ) AS a1 ON a.co_emp=a1.co_empNotPedPre AND a.co_loc=a1.co_locNotPedPre AND a.co_tipDoc=a1.co_tipDocNotPedPre AND a.co_doc=a1.co_docNotPedPre"; 
                strSQL+="        WHERE a.st_Reg IN ('A')  AND NOT ( (a.tx_numDoc2 LIKE '%PS%') OR (a.tx_numDoc2 LIKE '%PL%') )"; /* No mostrar compras locales. */
                strSQL+="   ) AS a2";  
                strSQL+="   ON a1.co_empNotPedPre=a2.co_empNotPedPre AND a1.co_locNotPedPre=a2.co_locNotPedPre AND a1.co_tipDocNotPedPre=a2.co_tipDocNotPedPre AND a1.co_docNotPedPre=a2.co_docNotPedPre";
                strSQL+="   LEFT OUTER JOIN ( "; /* NOTPED */
                strSQL+="      SELECT a.co_imp, a.co_exp, a.co_ctaPas AS co_ctaPed, a.co_emp AS co_empNotPed, a.co_loc AS co_locNotPed, a.co_tipDoc AS co_tipDocNotPed, a.co_Doc AS co_docNotPed";
                strSQL+="           , a.fe_emb AS fe_embEstNotPed, a.fe_pue AS fe_pueNotPed, a.fe_arr AS fe_arrNotPed, a.fe_doc AS fe_docNotPed";
                strSQL+="           , a.fe_venEst1, a.fe_venEst2, a.fe_venEst3, a.fe_venEst4, a.fe_venRea1, a.fe_venRea2, a.fe_venRea3, a.fe_venRea4";
                strSQL+="           , a.nd_valPagVenEst1, a.nd_valPagVenEst2, a.nd_valPagVenEst3, a.nd_valPagVenEst4, a.nd_valPagVen1, a.nd_valPagVen2, a.nd_valPagVen3, a.nd_valPagVen4";
                strSQL+="      FROM tbm_CabNotPedImp AS a WHERE a.st_reg IN ('A') AND (a.st_doc IN ('A') OR a.st_doc IS NULL )";
                strSQL+="   ) AS a3";
                strSQL+="   ON a2.co_empNotPed=a3.co_empNotPed AND a2.co_locNotPed=a3.co_locNotPed AND a2.co_tipDocNotPed=a3.co_tipDocNotPed AND a2.co_docNotPed=a3.co_docNotPed";
                strSQL+="   LEFT OUTER JOIN ( "; /*PEDEMB*/
                strSQL+="        SELECT a.co_imp, a.co_exp, a.co_ctaPas AS co_ctaPed, a.co_emp AS co_empPedEmb, a.co_loc AS co_locPedEmb, a.co_tipDoc AS co_tipDocPedEmb, a.co_Doc AS co_docPedEmb";
                strSQL+="             , a3.co_emp AS co_empNotPed, a3.co_locRel AS co_locNotPed, a3.co_tipDocRel AS co_tipDocNotPed, a3.co_DocRel AS co_docNotPed";
                strSQL+="             , (CASE WHEN a.tx_numDoc4 IS NOT NULL THEN a.tx_numDoc4 ELSE a.tx_numDoc2 END) AS tx_numPed";
                strSQL+="             , a.fe_venEst1, a.fe_venEst2, a.fe_venEst3, a.fe_venEst4, a.fe_venRea1, a.fe_venRea2, a.fe_venRea3, a.fe_venRea4";
                strSQL+="             , a.nd_valPagVenEst1, a.nd_valPagVenEst2, a.nd_valPagVenEst3, a.nd_valPagVenEst4, a.nd_valPagVen1, a.nd_valPagVen2, a.nd_valPagVen3, a.nd_valPagVen4";
                strSQL+="             , (CASE WHEN a.st_emb IN ('N') THEN a.fe_emb END) AS fe_embEstPedEmb, (CASE WHEN ( a.st_emb IN ('S') OR a.st_emb IS NULL) THEN a.fe_emb END) AS fe_embBL";
                strSQL+="        FROM tbm_CabPedEmbImp AS a INNER JOIN tbm_detPedEmbImp AS a3 ON a.co_emp=a3.co_emp AND a.co_loc=a3.co_loc AND a.co_tipDoc=a3.co_TipDoc AND a.co_doc=a3.co_doc";
                strSQL+="        WHERE a.st_reg IN ('A') AND (a.st_doc IN ('A') OR a.st_doc IS NULL)";
                strSQL+="        GROUP BY a.co_imp, a.co_exp, a.co_ctaPas, a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a3.co_emp, a3.co_locRel, a3.co_tipDocRel, a3.co_docRel, a.st_emb, a.fe_emb";
                strSQL+="   ) AS a4";
                strSQL+="   ON a3.co_empNotPed=a4.co_empNotPed AND a3.co_locNotPed=a4.co_locNotPed AND a3.co_tipDocNotPed=a4.co_tipDocNotPed AND a3.co_docNotPed=a4.co_docNotPed AND a1.tx_numPed=a4.tx_numPed";
                strSQL+="   LEFT OUTER JOIN ( "; /* INIMPO */
                strSQL+="        SELECT a.co_emp AS co_empMovInv, a.co_loc AS co_locMovInv, a.co_tipDoc AS co_tipDocMovInv, a.co_Doc AS co_docMovInv";
                strSQL+="             , a.co_empRelPedEmbImp AS co_empPedEmb, a.co_locRelPedEmbImp AS co_locPedEmb, a.co_tipDocRelPedEmbImp AS co_tipDocPedEmb, a.co_docRelPedEmbImp AS co_docPedEmb";
                strSQL+="             , a.fe_venRea1 AS fe_venReaPedLle1, a.fe_venRea2 AS fe_venReaPedLle2, a.fe_venRea3 AS fe_venReaPedLle3, a.fe_venRea4 AS fe_venReaPedLle4";
                strSQL+="             , a.nd_valPagVen1 AS nd_valPagVenReaPedLle1, a.nd_valPagVen2 AS nd_valPagVenReaPedLle2, a.nd_valPagVen3 AS nd_valPagVenReaPedLle3, a.nd_valPagVen4 AS nd_valPagVenReaPedLle4";
                strSQL+="        FROM (tbm_CabMovInv AS a INNER JOIN tbm_expImp AS a2 ON a.co_exp=a2.co_exp) WHERE a.st_reg IN ('A') AND a.tx_numDoc2 IS NOT NULL";
                strSQL+="   ) AS a5";
                strSQL+="   ON a4.co_empPedEmb=a5.co_empPedEmb AND a4.co_locPedEmb=a5.co_locPedEmb AND a4.co_tipDocPedEmb=a5.co_tipDocPedEmb AND a4.co_docPedEmb=a5.co_docPedEmb";  
                strSQL+="   LEFT OUTER JOIN( "; 
                strSQL+="        SELECT x.co_cta, x.co_emp, SUM(CASE WHEN x.nd_monDeb IS NULL THEN 0 ELSE x.nd_monDeb END ) AS nd_valAbo";  
                strSQL+="        FROM tbm_detDia AS x"; 
                strSQL+="        INNER JOIN tbm_cabDia AS x1 ON x.co_emp=x1.co_emp AND x.co_loc=x1.co_loc AND x.co_tipDoc=x1.co_tipDoc AND x.co_dia=x1.co_dia"; 
                strSQL+="        AND x1.st_reg IN ('A') AND x1.co_tipDoc="+intCodTipDocTraBan;  //TRABAEX
                strSQL+="        GROUP BY x.co_cta, x.co_emp";
                strSQL+="   ) AS a6";
                strSQL+="   ON a3.co_imp=a6.co_emp AND a3.co_ctaPed=a6.co_Cta";
                strSQL+="   LEFT OUTER JOIN( "; 
                strSQL+="        SELECT x.co_cta, x.co_emp, SUM(CASE WHEN x.nd_monDeb IS NULL THEN 0 ELSE x.nd_monDeb END ) AS nd_valAbo";  
                strSQL+="        FROM tbm_detDia AS x"; 
                strSQL+="        INNER JOIN tbm_cabDia AS x1 ON x.co_emp=x1.co_emp AND x.co_loc=x1.co_loc AND x.co_tipDoc=x1.co_tipDoc AND x.co_dia=x1.co_dia"; 
                strSQL+="        AND x1.st_reg IN ('A') AND x1.co_tipDoc="+intCodTipDocTraBan;  //TRABAEX
                strSQL+="        GROUP BY x.co_cta, x.co_emp";
                strSQL+="   ) AS a7";
                strSQL+="   ON a4.co_imp=a7.co_emp AND a4.co_ctaPed=a7.co_Cta";                
                strSQL+="   WHERE a1.st_reg IN ('A')";
                strSQL+="   "+strAuxFil+""; 
                strSQL+=" ) AS x ";
                //strSQL+=" ORDER BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.fe_doc";
                strSQL+=" ORDER BY x.tx_numPed, x.fe_doc";
                System.out.println("cargarDetRegPanCon Ver: "+strSQL); 
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()){
                    if (blnCon){                        
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_RPT_LIN,"");
                        vecReg.add(INT_TBL_RPT_COD_EMP,             (rst.getObject("co_emp")==null?"":rst.getString("co_emp")) );        
                        vecReg.add(INT_TBL_RPT_COD_LOC,             (rst.getObject("co_loc")==null?"":rst.getString("co_loc")) );   
                        vecReg.add(INT_TBL_RPT_COD_TIP_DOC,         (rst.getObject("co_tipDoc")==null?"":rst.getString("co_tipDoc")) );   
                        vecReg.add(INT_TBL_RPT_COD_DOC,             (rst.getObject("co_doc")==null?"":rst.getString("co_doc")) );   
                        vecReg.add(INT_TBL_RPT_NUM_DOC,             (rst.getObject("ne_numDoc")==null?"":rst.getString("ne_numDoc")) );   
                        vecReg.add(INT_TBL_RPT_NUM_PED,             (rst.getObject("tx_numPed")==null?"":rst.getString("tx_numPed")) );   
                        vecReg.add(INT_TBL_RPT_PED_ASO,             (rst.getObject("tx_numPedAso")==null?"":rst.getString("tx_numPedAso")) );     /////?????

                        vecReg.add(INT_TBL_RPT_COD_EMP_NOTPEDPRE,      (rst.getObject("co_empRelCabNotPedPreImp")==null?"":rst.getString("co_empRelCabNotPedPreImp")) );   
                        vecReg.add(INT_TBL_RPT_COD_LOC_NOTPEDPRE,      (rst.getObject("co_locRelCabNotPedPreImp")==null?"":rst.getString("co_locRelCabNotPedPreImp")) );   
                        vecReg.add(INT_TBL_RPT_COD_TIP_DOC_NOTPEDPRE,  (rst.getObject("co_tipDocRelCabNotPedPreImp")==null?"":rst.getString("co_tipDocRelCabNotPedPreImp")) );   
                        vecReg.add(INT_TBL_RPT_COD_DOC_NOTPEDPRE,      (rst.getObject("co_docRelCabNotPedPreImp")==null?"":rst.getString("co_docRelCabNotPedPreImp")) );  

                        vecReg.add(INT_TBL_RPT_COD_EMP_NOTPED,      (rst.getObject("co_empNotPed")==null?"":rst.getString("co_empNotPed")) );   
                        vecReg.add(INT_TBL_RPT_COD_LOC_NOTPED,      (rst.getObject("co_locNotPed")==null?"":rst.getString("co_locNotPed")) );   
                        vecReg.add(INT_TBL_RPT_COD_TIP_DOC_NOTPED,  (rst.getObject("co_tipDocNotPed")==null?"":rst.getString("co_tipDocNotPed")) );   
                        vecReg.add(INT_TBL_RPT_COD_DOC_NOTPED,      (rst.getObject("co_docNotPed")==null?"":rst.getString("co_docNotPed")) );  

                        vecReg.add(INT_TBL_RPT_COD_EMP_PEDEMB,      (rst.getObject("co_empPedEmb")==null?"":rst.getString("co_empPedEmb")) );   
                        vecReg.add(INT_TBL_RPT_COD_LOC_PEDEMB,      (rst.getObject("co_locPedEmb")==null?"":rst.getString("co_locPedEmb")) );   
                        vecReg.add(INT_TBL_RPT_COD_TIP_DOC_PEDEMB,  (rst.getObject("co_tipDocPedEmb")==null?"":rst.getString("co_tipDocPedEmb")) );   
                        vecReg.add(INT_TBL_RPT_COD_DOC_PEDEMB,      (rst.getObject("co_docPedEmb")==null?"":rst.getString("co_docPedEmb")) );  

                        vecReg.add(INT_TBL_RPT_COD_EMP_INIMPO,      (rst.getObject("co_empMovInv")==null?"":rst.getString("co_empMovInv")) );   
                        vecReg.add(INT_TBL_RPT_COD_LOC_INIMPO,      (rst.getObject("co_locMovInv")==null?"":rst.getString("co_locMovInv")) );   
                        vecReg.add(INT_TBL_RPT_COD_TIP_DOC_INIMPO,  (rst.getObject("co_tipDocMovInv")==null?"":rst.getString("co_tipDocMovInv")) );   
                        vecReg.add(INT_TBL_RPT_COD_DOC_INIMPO,      (rst.getObject("co_docMovInv")==null?"":rst.getString("co_docMovInv")) );  

                        vecReg.add(INT_TBL_RPT_VAL_PED,             (rst.getObject("nd_valPed")==null?"":rst.getString("nd_valPed")) );   
                        
                        vecReg.add(INT_TBL_RPT_VAL_PAG_TOT,         (rst.getObject("nd_valTotPagExt")==null?"":rst.getString("nd_valTotPagExt")) );   
                        vecReg.add(INT_TBL_RPT_CHK_VER_PAG,         null );                           
                        
                        vecReg.add(INT_TBL_RPT_NOM_RUB_MAT,         (rst.getObject("tx_desMat")==null?"":rst.getString("tx_desMat")) );      
                        
                        vecReg.add(INT_TBL_RPT_COD_IMP,             (rst.getObject("co_imp")==null?"":rst.getString("co_imp")) );   
                        vecReg.add(INT_TBL_RPT_NOM_IMP,             (rst.getObject("tx_nomImp")==null?"":rst.getString("tx_nomImp")) );   
                        
                        vecReg.add(INT_TBL_RPT_COD_DIR_IND,          (rst.getObject("ne_tipImp")==null?"":rst.getString("ne_tipImp")) );   
                        vecReg.add(INT_TBL_RPT_DES_DIR_IND,          (rst.getObject("tx_desTipImp")==null?"":rst.getString("tx_desTipImp")) );   

                        vecReg.add(INT_TBL_RPT_DES_TIP_PED,          (rst.getObject("tx_desIncTer")==null?"":rst.getString("tx_desIncTer")) );   
                        vecReg.add(INT_TBL_RPT_DES_PTO_EMB,          (rst.getObject("tx_nomPtoEmb")==null?"":rst.getString("tx_nomPtoEmb")) );   
                        vecReg.add(INT_TBL_RPT_COD_PRV,              (rst.getObject("co_prv")==null?"":rst.getString("co_prv")));   
                        vecReg.add(INT_TBL_RPT_NOM_PRV,              (rst.getObject("tx_nomPrv")==null?"":rst.getString("tx_nomPrv")) );     
                        vecReg.add(INT_TBL_RPT_COD_FOR_PAG,          (rst.getObject("co_forPag")==null?"":rst.getString("co_forPag")) );   
                        vecReg.add(INT_TBL_RPT_DES_FOR_PAG,          (rst.getObject("tx_forPag")==null?"":rst.getString("tx_forPag")) );     
                        
                        vecReg.add(INT_TBL_RPT_FEC_PRO_EST,          (rst.getString("fe_proEst")) );  
                        vecReg.add(INT_TBL_RPT_FEC_SEG_ORD,          (rst.getString("fe_segOrd")) );  
                        vecReg.add(INT_TBL_RPT_DES_REQ_ORD,          (rst.getObject("tx_desReq")==null?"":rst.getString("tx_desReq")));  
                        
                        vecReg.add(INT_TBL_RPT_FEC_PRE_IMP,          (rst.getString("fe_preImp")) );   
                        
                        vecReg.add(INT_TBL_RPT_FEC_COL_PED,          (rst.getString("fe_colPed")) );   
                        vecReg.add(INT_TBL_RPT_FEC_REC_PRO,          (rst.getString("fe_recPro")) );   
                        vecReg.add(INT_TBL_RPT_FEC_CON_PRO,          (rst.getString("fe_conPro")) );   
                        vecReg.add(INT_TBL_RPT_FEC_DOC_NPP,          (rst.getString("fe_docNPP")) );   
                        
                        vecReg.add(INT_TBL_RPT_FEC_NOT_PED_IND,      (rst.getString("fe_notPedInd")) );   
                        vecReg.add(INT_TBL_RPT_FEC_DOC_NOT_PED,      (rst.getString("fe_docNotPed")) );   
                        
                        vecReg.add(INT_TBL_RPT_FEC_SOL_ANT_PRV_1,    (rst.getString("fe_solAntPrv1")) );   
                        vecReg.add(INT_TBL_RPT_VAL_SOL_ANT_PRV_1,    (rst.getObject("nd_valSolAntPrv1")==null?"":rst.getString("nd_valSolAntPrv1")) );                           
                        vecReg.add(INT_TBL_RPT_FEC_PAG_ANT_PRV_1,    (rst.getString("fe_pagAntPrv1")) );   
                        vecReg.add(INT_TBL_RPT_TXT_OBS_ANT_PRV_1,    (rst.getObject("tx_obsSolAntPrv1")==null?"":rst.getString("tx_obsSolAntPrv1")) );   
                        
                        vecReg.add(INT_TBL_RPT_FEC_SOL_ANT_PRV_2,    (rst.getString("fe_solAntPrv2")) );   
                        vecReg.add(INT_TBL_RPT_VAL_SOL_ANT_PRV_2,    (rst.getObject("nd_valSolAntPrv2")==null?"":rst.getString("nd_valSolAntPrv2")) );                           
                        vecReg.add(INT_TBL_RPT_FEC_PAG_ANT_PRV_2,    (rst.getString("fe_pagAntPrv2")) );   
                        vecReg.add(INT_TBL_RPT_TXT_OBS_ANT_PRV_2,    (rst.getObject("tx_obsSolAntPrv2")==null?"":rst.getString("tx_obsSolAntPrv2")) );   
                        
                        vecReg.add(INT_TBL_RPT_FEC_REC_FAC_PRV,      (rst.getString("fe_recFac")) );      
                        vecReg.add(INT_TBL_RPT_FEC_SOL_AGE,          (rst.getString("fe_solAge")) );      
                        vecReg.add(INT_TBL_RPT_FEC_ASI_AGE,          (rst.getString("fe_asiAge")) );      
                        
                        vecReg.add(INT_TBL_RPT_DES_PED_CON,          (rst.getObject("tx_desPedCon")==null?"":rst.getString("tx_desPedCon")) );      
                        
                        vecReg.add(INT_TBL_RPT_FEC_EMB_EST,          (rst.getString("fe_embEst")) );   //Actualizar en última instancia.
                        vecReg.add(INT_TBL_RPT_FEC_EMB_BOK,          (rst.getString("fe_embBok")) );   
                        vecReg.add(INT_TBL_RPT_FEC_EMB_BL,           (rst.getString("fe_embBL")) );   
                        
                        vecReg.add(INT_TBL_RPT_FEC_PUE,              (rst.getString("fe_pueNotPed")) );   
                        vecReg.add(INT_TBL_RPT_FEC_ARR_BOD,          (rst.getString("fe_arrNotPed")) );   
                        
                        vecReg.add(INT_TBL_RPT_FEC_BAS_PAG_1,       (rst.getString("fe_venEst1")) );   
                        vecReg.add(INT_TBL_RPT_FEC_BAS_PAG_2,       (rst.getString("fe_venEst2")) );   
                        vecReg.add(INT_TBL_RPT_FEC_BAS_PAG_3,       (rst.getString("fe_venEst3")) );   
                        vecReg.add(INT_TBL_RPT_FEC_BAS_PAG_4,       (rst.getString("fe_venEst4")) );   
                       
                        vecReg.add(INT_TBL_RPT_FEC_PAG_REA_1,       (rst.getString("fe_venRea1")) );   
                        vecReg.add(INT_TBL_RPT_FEC_PAG_REA_2,       (rst.getString("fe_venRea2")) );   
                        vecReg.add(INT_TBL_RPT_FEC_PAG_REA_3,       (rst.getString("fe_venRea3")) );   
                        vecReg.add(INT_TBL_RPT_FEC_PAG_REA_4,       (rst.getString("fe_venRea4")) );   
                        
                        vecReg.add(INT_TBL_RPT_FEC_SOL_SAL_PRV,     (rst.getString("fe_SolSalPrv")) );  
                        vecReg.add(INT_TBL_RPT_VAL_SOL_SAL_PRV,     (rst.getObject("nd_valSolSalPrv")==null?"":rst.getString("nd_valSolSalPrv")) );  
                        vecReg.add(INT_TBL_RPT_FEC_PAG_SAL_PRV,     (rst.getString("fe_pagSalPrv")) );  
                        vecReg.add(INT_TBL_RPT_TXT_OBS_SAL_PRV,     (rst.getObject("tx_obsSolSalPrv")==null?"":rst.getString("tx_obsSolSalPrv")) );   

                        vecReg.add(INT_TBL_RPT_FEC_EST_VEN_1,       (rst.getString("fe_venReaPedLle1")) );
                        vecReg.add(INT_TBL_RPT_VAL_EST_VEN_1,       (rst.getObject("nd_valPagVenReaPedLle1")==null?"":rst.getString("nd_valPagVenReaPedLle1")) );   
                        vecReg.add(INT_TBL_RPT_FEC_PAG_VEN_1,       (rst.getString("fe_pagPrvVen1")) );   
                        vecReg.add(INT_TBL_RPT_VAL_PAG_VEN_1,       (rst.getObject("nd_valPagPrvVen1")==null?"":rst.getString("nd_valPagPrvVen1")) ); 
                        
                        vecReg.add(INT_TBL_RPT_FEC_EST_VEN_2,       (rst.getString("fe_venReaPedLle2")) );   
                        vecReg.add(INT_TBL_RPT_VAL_EST_VEN_2,       (rst.getObject("nd_valPagVenReaPedLle2")==null?"":rst.getString("nd_valPagVenReaPedLle2")) );   
                        vecReg.add(INT_TBL_RPT_FEC_PAG_VEN_2,       (rst.getString("fe_pagPrvVen2")) );   
                        vecReg.add(INT_TBL_RPT_VAL_PAG_VEN_2,       (rst.getObject("nd_valPagPrvVen2")==null?"":rst.getString("nd_valPagPrvVen2")) );        
                        
                        vecReg.add(INT_TBL_RPT_FEC_EST_VEN_3,       (rst.getString("fe_venReaPedLle3")) );   
                        vecReg.add(INT_TBL_RPT_VAL_EST_VEN_3,       (rst.getObject("nd_valPagVenReaPedLle3")==null?"":rst.getString("nd_valPagVenReaPedLle3")) );   
                        vecReg.add(INT_TBL_RPT_FEC_PAG_VEN_3,       (rst.getString("fe_pagPrvVen3")) );   
                        vecReg.add(INT_TBL_RPT_VAL_PAG_VEN_3,       (rst.getObject("nd_valPagPrvVen3")==null?"":rst.getString("nd_valPagPrvVen3")) );      
                        
                        vecReg.add(INT_TBL_RPT_VAL_NOT_CRE,         (rst.getObject("nd_valPagPrvVen3")==null?"":rst.getString("nd_valPagPrvVen3")) );       //OJOJOJO
                        vecReg.add(INT_TBL_RPT_TXT_OBS_COM,         (rst.getObject("tx_obs1")==null?"":rst.getString("tx_obs1")) );    

                        vecDat.add(vecReg);   
                        
                        //Cargar los datos en la Tabla Fija
                        vecRegFix=new Vector();
                        vecRegFix.add(INT_TBL_DAT_FIX_LIN,"");
                        vecRegFix.add(INT_TBL_RPT_FIX_COD_EMP,             (rst.getObject("co_emp")==null?"":rst.getString("co_emp")) );        
                        vecRegFix.add(INT_TBL_RPT_FIX_COD_LOC,             (rst.getObject("co_loc")==null?"":rst.getString("co_loc")) );   
                        vecRegFix.add(INT_TBL_RPT_FIX_COD_TIP_DOC,         (rst.getObject("co_tipDoc")==null?"":rst.getString("co_tipDoc")) );   
                        vecRegFix.add(INT_TBL_RPT_FIX_COD_DOC,             (rst.getObject("co_doc")==null?"":rst.getString("co_doc")) );   
                        vecRegFix.add(INT_TBL_RPT_FIX_NUM_DOC,             (rst.getObject("ne_numDoc")==null?"":rst.getString("ne_numDoc")) );   
                        vecRegFix.add(INT_TBL_RPT_FIX_NUM_PED,             (rst.getObject("tx_numPed")==null?"":rst.getString("tx_numPed")) );                           
                        vecDatFix.add(vecRegFix);                             
                    }
                    else
                    {
                        break;
                    }
                }
                rst.close(); 
                stm.close();
                rst=null;
                stm=null;
                        
                //Asignar vectores al modelo.
                //Tabla de Datos
                objTblModDat.setData(vecDat);
                tblDat.setModel(objTblModDat);
                vecDat.clear();
                objTblModDat.initRowsState();        
                objTblModDat.setModoOperacion(ZafTblMod.INT_TBL_INS);
                
                calcularVerificacionPagExt();
                
                //Tabla Fija
                objTblModFix.setData(vecDatFix);
                tblFix.setModel(objTblModFix);
                vecDatFix.clear();     
                
            }
        }
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }        
    
    
    
    
    /* ============================================================================================================================== */
    
    
    /**
     * Función que permite configurar el número de columnas que se deben agregar al modelo de datos
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */ 
    private boolean getColumnsAdd(){
        boolean blnRes=false;
        try{
            if(con!=null){
                if(eliminaColumnsAddCarPag()){
                    if(addColumnsAddCarPag()){
                        if(addColumnsAddTotCarPag()){
                            if(addColumnAddFecVenEst()){
                                if(addColumnAddFecVenRea()){
                                    if(addColumnAddEstReg()){
                                        blnRes=true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean eliminaColumnsAddCarPag(){
        boolean blnRes=true;
        try{
            objTblModDat.removeAllRows();

            for (int i=(objTblModDat.getColumnCount()-1); i>=intNumColEst; i--){               
               objTblModDat.removeColumn(tblDat, i);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean addColumnsAddCarPag(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;
        ZafTblHeaColGrp objTblHeaColGrpFecEstEmb=null;
        intNumColIniCarPag=intNumColEst;//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomCarPag="";
        try{
            intNumColAddCarPag=numeroColumnasAddCarPag();//el numero de columnas a adicionar dependera de los meses/anios seleccionados en el historico del PRIMER PANEL
            
            objTblCelRenLblCarPag=new ZafTblCelRenLbl();
            objTblCelRenLblCarPag.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCarPag.setTipoFormato(objTblCelRenLblCarPag.INT_FOR_NUM);
            objTblCelRenLblCarPag.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblCarPag.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                }
            });

            for (int i=0; i<intNumColAddCarPag; i++){
                strNomCarPag="" + objUti.getStringValueAt(arlDatCarPag, i, INT_ARL_CAR_PAG_NOM_CAR_PAG);

                objUti.setStringValueAt(arlDatCarPag, i, INT_ARL_CAR_PAG_COL_CAR_PAG, "" + (intNumColIniCarPag+i));                
                tbc=new javax.swing.table.TableColumn(intNumColIniCarPag+i);
                //tbc.setHeaderValue("<HTML><DIV ALIGN=center>" + strNomPed + "<BR>" + strFecEstPedEmb + "</DIV></HTML>");
                //tbc.setHeaderValue(strNomPed);
                tbc.setHeaderValue(strNomCarPag);

                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                
                if(!chkMosCarPag.isSelected()) {
                    /* Ocultar columnas de cargo, solicitado por LSC. */
                    tbc.setWidth(0);
                    tbc.setResizable(false);
                    tbc.setMaxWidth(0);
                    tbc.setMinWidth(0);
                    tbc.setPreferredWidth(0);                
                }
                
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblCarPag);

                //Configurar JTable: Agregar la columna al JTable.
                objTblModDat.addColumn(tblDat, tbc);

                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Cargos a Pagar");
                    objTblHeaColGrpTit.setHeight(16);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
                }
                objTblHeaColGrpTit.add(tbc);
            }

            intNumColFinCarPag=objTblModDat.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;
            objTblHeaColGrpFecEstEmb=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
   /**
    * Función que permite obtener los cargos a pagar de
    * "O"  "FLETE APROXIMADO"
    * "V"  "VALOR DE LA FACTURA"
    * @return 
    */
    private int numeroColumnasAddCarPag(){
        int intNumColAdd=0;
        arlDatCarPag.clear();
        try{
            if(con!=null){
                //Armar la sentencia SQL.
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_carpag, a1.tx_nom, a1.tx_tipcarpag, a1.tx_obs1, a1.st_reg";
                strSQL+=" FROM tbm_carpagimp AS a1";
                strSQL+=" WHERE a1.st_reg NOT IN('E','I') AND tx_tipcarpag NOT IN('O', 'V')"; /* No mostrar cargo de valor factura, porque este valor se refleja en el total del pedido.*/
                //strSQL+=" WHERE a1.st_reg NOT IN('E','I') AND tx_tipcarpag NOT IN('O')";
                strSQL+=" ORDER BY a1.ne_ubi";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegCarPag=new ArrayList();
                    arlRegCarPag.add(INT_ARL_CAR_PAG_COD_CAR_PAG, rst.getInt("co_carpag"));
                    arlRegCarPag.add(INT_ARL_CAR_PAG_NOM_CAR_PAG, rst.getString("tx_nom"));
                    arlRegCarPag.add(INT_ARL_CAR_PAG_TIP_CAR_PAG, rst.getString("tx_tipcarpag"));
                    arlRegCarPag.add(INT_ARL_CAR_PAG_COL_CAR_PAG, "");
                    arlDatCarPag.add(arlRegCarPag);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                    
                intNumColAdd=arlDatCarPag.size();
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intNumColAdd;
   }

    private boolean addColumnsAddTotCarPag(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;
        intNumColIniTotCarPag=intNumColFinCarPag;//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomCarPag="";
        try{
            intNumColAddTotCarPag=numeroColumnasAddTotCarPag();
            
            objTblCelRenLblTotCarPag=new ZafTblCelRenLbl();
            objTblCelRenLblTotCarPag.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblTotCarPag.setTipoFormato(objTblCelRenLblTotCarPag.INT_FOR_NUM);
            objTblCelRenLblTotCarPag.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                   
            objTblCelRenLblTotCarPag.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                }
            });
            
            for (int i=0; i<intNumColAddTotCarPag; i++){
                strNomCarPag="" + objUti.getStringValueAt(arlDatTotCarPag, i, INT_ARL_TOT_CAR_PAG_NOM);
                /* Se asigna el número de la columna de los totales de los cargos a pagar. */
                objUti.setStringValueAt(arlDatTotCarPag, i, INT_ARL_TOT_CAR_PAG_COL, "" + (intNumColIniTotCarPag+i));                
                tbc=new javax.swing.table.TableColumn(intNumColIniTotCarPag+i);
                //tbc.setHeaderValue("<HTML><DIV ALIGN=center>" + strNomPed + "<BR>" + strFecEstPedEmb + "</DIV></HTML>");
                //tbc.setHeaderValue(strNomPed);
                tbc.setHeaderValue(strNomCarPag);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                
                if(!chkMosTotCarPag.isSelected()) {
                    tbc.setWidth(0);
                    tbc.setResizable(false);
                    tbc.setMaxWidth(0);
                    tbc.setMinWidth(0);
                    tbc.setPreferredWidth(0);                
                }                
                
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblTotCarPag);

                //Configurar JTable: Agregar la columna al JTable.
                objTblModDat.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Totales");
                    objTblHeaColGrpTit.setHeight(16);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
                }
                objTblHeaColGrpTit.add(tbc);
            }
            intNumColFinTotCarPag=objTblModDat.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
   /**
    * Función que permite conocer los tipos de cargos agrupados, excepto
    * "O"  "FLETE APROXIMADO"
    * "V"  "VALOR DE LA FACTURA"
    * @return true Si se pudo realizar la operación
    * <BR> false Caso contrario
    */
    private int numeroColumnasAddTotCarPag(){
        int intNumColAdd=0;
        arlDatTotCarPag.clear();
        try{
            if(con!=null){
                //Armar la sentencia SQL.
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT b1.tx_tipCarPag";
                strSQL+="       , CASE WHEN b1.tx_tipCarPag='A' THEN 'Ara.'";
                strSQL+="              WHEN b1.tx_tipCarPag='F' THEN 'Flet.'";
                strSQL+=" 	       WHEN b1.tx_tipCarPag='G' THEN 'Gas.'";
                strSQL+=" 	       WHEN b1.tx_tipCarPag='I' THEN 'Iva.'";
                strSQL+=" 	       ELSE '' END AS tx_nomTipCarPag";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.tx_tipCarPag";
                strSQL+=" 	FROM tbm_carPagImp AS a1";
                strSQL+=" 	WHERE a1.st_reg='A' AND a1.tx_tipCarPag IN("+strTipCarPag+")";
                strSQL+=" 	GROUP BY a1.tx_tipCarPag";
                strSQL+=" ) AS b1";
                strSQL+=" ORDER BY b1.tx_tipCarPag";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegTotCarPag=new ArrayList();
                    arlRegTotCarPag.add(INT_ARL_TOT_CAR_PAG_TIP, rst.getString("tx_tipCarPag"));
                    arlRegTotCarPag.add(INT_ARL_TOT_CAR_PAG_NOM, rst.getString("tx_nomTipCarPag"));
                    arlRegTotCarPag.add(INT_ARL_TOT_CAR_PAG_COL, "");
                    arlDatTotCarPag.add(arlRegTotCarPag);
                } 
                stm.close();
                stm=null;
                rst.close(); 
                rst=null;

                intNumColAdd=arlDatTotCarPag.size();
            }
        }
        catch(Exception e){ 
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intNumColAdd;
    }    

    /**
     * Función que permite adicionar la columna de Fechas de vencimientos Estimadas de los pedidos
     * @return 
     */
    private boolean addColumnAddFecVenEst(){
        boolean blnRes=true;
        String strSubTit="";
        ZafTblHeaGrp objTblHeaGrpTblFecPry = (ZafTblHeaGrp) tblDat.getTableHeader();
        objTblHeaGrpTblFecPry.setHeight(16*3);    
        ZafTblHeaColGrp objTblHeaColGrpFec = null;
        ZafTblHeaColGrp objTblHeaColSubGrp= null;
        javax.swing.table.TableColumn tbc;
        intNumGrpAddFecVenEst=4;          //AP: Se estableció 4 columnas para grupo de las fechas de vencimiento Proyectadas.
        intNumColFecVenEst=4;             //Son las columnas que estan dentro de cada grupo de fecha de vencimiento.
        intNumColIniFecVenEst=intNumColFinTotCarPag; //numero de columnas que tiene el modelo antes de adicionar las columnas
        java.awt.Color colFonColPar, colFonColImp;
        colFonColPar = new java.awt.Color(255, 221, 187); //Color Naranja.
        colFonColImp = new java.awt.Color(228, 228, 203); //Color Verde Agua.        
        try{
            for (int i=0; i<intNumGrpAddFecVenEst; i++)
            {
                /*Creando Grupo "Fechas de Vencimiento Estimadas" */
                if(i==0){ 
                    objTblHeaColGrpFec=new ZafTblHeaColGrp("Fechas de Vencimiento Estimadas");
                    objTblHeaColGrpFec.setHeight(16);
                }                    
                for(int j=0; j<intNumColFecVenEst; j++)
                {
                    int intIndColGrp =(intNumColIniFecVenEst+j+(i*intNumColFecVenEst)); //Obtengo Indice de la Columna.
                    strSubTit = (j==0? "Días":(j==1?"Fec.Ven.":(j==2?"% Pag.":(j==3?"Val.Pag":""))));
                    tbc=new javax.swing.table.TableColumn( (intIndColGrp) );
                    tbc.setHeaderValue("" + strSubTit);
                    
                    if( (j==0) ) { //Día.Cré.
                        //Configurar JTable: Establecer el ancho de la columna.
                        tbc.setPreferredWidth(42);
                        //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
                        objTblModDat.setColumnDataType(intIndColGrp, objTblModDat.INT_COL_INT, new Integer(0), 100);    
                        //Configurar JTable: Renderizar celdas.
                        objTblCelRenLblNumDin = new ZafTblCelRenLbl();
                        //objTblCelRenLblNumDin.setBackground(objParSis.getColorCamposObligatorios());
                        objTblCelRenLblNumDin.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                        objTblCelRenLblNumDin.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);            
                        objTblCelRenLblNumDin.setFormatoNumerico(objParSis.getFormatoNumero(), true, true); //Muestra 0
                        tbc.setCellRenderer(objTblCelRenLblNumDin); 
                        
                        //Configurar JTable: Establecer color en celdas
                        if((i % 2)==0) 
                            objTblCelRenLblNumDin.setBackground(colFonColPar);                       
                        else
                            objTblCelRenLblNumDin.setBackground(colFonColImp);   
                        objTblCelRenLblNumDin=null;
                        
                        //Configurar JTable: Editor celdas.
                        objTblCelEdiTxtDiaCre=new ZafTblCelEdiTxt();
                        //tbc.setCellRenderer(new RenderDecimales(objParSis.getDecimalesMostrar()) );
                        tbc.setCellEditor(objTblCelEdiTxtDiaCre);
                        objTblCelEdiTxtDiaCre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() { 
                            int intFil=-1;
                            int intCol=-1;
                            String strDat="";
                            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                intFil=tblDat.getSelectedRow();
                                intCol=tblDat.getSelectedColumn();
                            }
                            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                strDat=objTblModDat.getValueAt(intFil, intCol)==null?"0":(objTblModDat.getValueAt(intFil, intCol).toString().equals("")?"0":objTblModDat.getValueAt(intFil, intCol).toString());
                                if(objUti.isNumero(strDat))
                                    calcularFecPagFecVenEstSel(intFil, intCol, intCalEditAfter);
                                else
                                    objTblModDat.setValueAt("", intFil, intCol); 
                            } 
                        });
                    }
                    else if (j==1) { //Fec.Ven.
                        //Configurar JTable: Establecer el ancho de la columna.
                        tbc.setPreferredWidth(70);
                        //Configurar JTable: Renderizar celdas.
                        objTblCelRenLblFec=new ZafTblCelRenLbl();
                        objTblCelRenLblFec.setHorizontalAlignment(javax.swing.JLabel.CENTER);
                        objTblCelRenLblFec.setTipoFormato(objTblCelRenLblFec.INT_FOR_FEC);    
                        tbc.setCellRenderer(objTblCelRenLblFec);
                        //Configurar JTable: Establecer color en celdas
                        if((i % 2)==0) 
                            objTblCelRenLblFec.setBackground(colFonColPar);                       
                        else
                            objTblCelRenLblFec.setBackground(colFonColImp);  
                        objTblCelRenLblFec=null;
                    }
                    else if( (j==2) ) { // % Pag
                        //Configurar JTable: Establecer el ancho de la columna.
                        tbc.setPreferredWidth(42);
                        //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
                        objTblModDat.setColumnDataType(intIndColGrp, objTblModDat.INT_COL_INT, new Integer(0), 100);    
                        //Configurar JTable: Renderizar celdas.
                        objTblCelRenLblNumDin = new ZafTblCelRenLbl();
                        objTblCelRenLblNumDin.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                        objTblCelRenLblNumDin.setTipoFormato(objTblCelRenLblNumDin.INT_FOR_NUM);                           
                        objTblCelRenLblNumDin.setFormatoNumerico(objParSis.getFormatoNumero(), false, false); //No Muestra 0
                        tbc.setCellRenderer(objTblCelRenLblNumDin);      
                        //Configurar JTable: Establecer color en celdas
                        if((i % 2)==0) 
                            objTblCelRenLblNumDin.setBackground(colFonColPar);                       
                        else
                            objTblCelRenLblNumDin.setBackground(colFonColImp);  
                        objTblCelRenLblNumDin=null;

                        //Configurar JTable: Editor celdas.
                        objTblCelEdiTxtPorCre=new ZafTblCelEdiTxt();
                        tbc.setCellEditor(objTblCelEdiTxtPorCre);
                        objTblCelEdiTxtPorCre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() { 
                            int intFil=-1;
                            int intCol=-1;
                            String strDat="";  
                            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                intFil=tblDat.getSelectedRow();
                                intCol=tblDat.getSelectedColumn();
                            }
                            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                strDat=objTblModDat.getValueAt(intFil, intCol)==null?"0":(objTblModDat.getValueAt(intFil, intCol).toString().equals("")?"0":objTblModDat.getValueAt(intFil, intCol).toString()); 
                                if(objUti.isNumero(strDat)) {
                                    if( new BigDecimal (strDat).compareTo(BigDecimal.ZERO)>=0) /* Valida que solo se pueda ingresar valores positivos. */
                                        calcularValPagFecVenEstSel(intFil, intCol, intCalEditAfter);
                                    else 
                                        objTblModDat.setValueAt("", intFil, intCol);  
                                } 
                                else
                                    objTblModDat.setValueAt("", intFil, intCol);                                   
                            }
                        });                        
                    }
                    else if( (j==3) ) { //Val.Pag.
                        //Configurar JTable: Establecer el ancho de la columna.
                        tbc.setPreferredWidth(70);         
                        //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
                        objTblModDat.setColumnDataType(intIndColGrp, objTblModDat.INT_COL_DBL, new Integer(0), null);    
                        //Configurar JTable: Renderizar celdas.
                        objTblCelRenLblNumDin = new ZafTblCelRenLbl();
                        objTblCelRenLblNumDin.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                        objTblCelRenLblNumDin.setTipoFormato(objTblCelRenLblNumDin.INT_FOR_NUM);                           
                        objTblCelRenLblNumDin.setFormatoNumerico(objParSis.getFormatoNumero(), true, false); //Muestra 0
                        tbc.setCellRenderer(objTblCelRenLblNumDin); 
                        //Configurar JTable: Establecer color en celdas
                        if((i % 2)==0) 
                            objTblCelRenLblNumDin.setBackground(colFonColPar);                       
                        else
                            objTblCelRenLblNumDin.setBackground(colFonColImp);  
                        objTblCelRenLblNumDin=null;
                    }
                    
                    //Configurar JTable: Establecer el ancho de la columna.
                    if(!chkMosFecVenEst.isSelected()) {
                        /* Ocultar columnas de fechas de vencimiento, de acuerdo al filtro. */
                        tbc.setWidth(0); 
                        tbc.setResizable(false);
                        tbc.setMaxWidth(0);
                        tbc.setMinWidth(0);
                        tbc.setPreferredWidth(0);                
                    }                    

                    //Configurar JTable: Agregar la columna al JTable.
                    objTblModDat.addColumn(tblDat, tbc);
                    
                    //Configurar JTable: Agrupamiento de Columnas
                    if(j==0) //Primera Columna del Grupo.
                    {
                        objTblHeaColSubGrp=new ZafTblHeaColGrp("Fecha Vencimiento #"+ (i+1) );
                        objTblHeaColSubGrp.setHeight(16);
                        objTblHeaColGrpFec.add(objTblHeaColSubGrp); 
                    } 
                    objTblHeaGrpTblFecPry.addColumnGroup(objTblHeaColGrpFec);  
                    objTblHeaColSubGrp.add(tblDat.getColumnModel().getColumn(intIndColGrp));
                }
            }
            intNumColFinFecVenEst=objTblModDat.getColumnCount();
            objTblHeaGrpTblFecPry=null;
            objTblHeaColGrpFec=null;
            objTblHeaColSubGrp=null;
        }
        catch(Exception e){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e); 
        } 
        return blnRes;
    }     
    
    /**
     * Función que permite adicionar la columna de Fechas de vencimientos Reales de los pedidos
     * @return 
     */
    private boolean addColumnAddFecVenRea(){
        boolean blnRes=true;
        String strSubTit="";
        ZafTblHeaGrp objTblHeaGrpTblFecRea = (ZafTblHeaGrp) tblDat.getTableHeader();
        objTblHeaGrpTblFecRea.setHeight(16*3);    
        ZafTblHeaColGrp objTblHeaColGrpFec = null;
        ZafTblHeaColGrp objTblHeaColSubGrp= null;
        javax.swing.table.TableColumn tbc;
        intNumGrpAddFecVenRea=4;          //AP: Se estableció 4 columnas para grupo de las fechas de vencimiento Reales.
        intNumColFecVenRea=2;             //Son las columnas que estan dentro de cada grupo de fecha de vencimiento.
        intNumColIniFecVenRea=intNumColFinFecVenEst; //numero de columnas que tiene el modelo antes de adicionar las columnas
        java.awt.Color colFonColPar, colFonColImp;
        colFonColPar = new java.awt.Color(255, 221, 187); //Color Naranja.
        colFonColImp = new java.awt.Color(228, 228, 203); //Color Verde Agua.        
        try{
            for (int i=0; i<intNumGrpAddFecVenRea; i++)
            {
                /*Creando Grupo "Fechas de Vencimiento Estimadas" */
                if(i==0){ 
                    objTblHeaColGrpFec=new ZafTblHeaColGrp("Fechas de Vencimiento Reales");
                    objTblHeaColGrpFec.setHeight(16);
                }                    
                for(int j=0; j<intNumColFecVenRea; j++)
                {
                    int intIndColGrp =(intNumColIniFecVenRea+j+(i*intNumColFecVenRea)); //Obtengo Indice de la Columna.
                    strSubTit = (j==0? "Fec.Ven.":(j==1?"Val.Pag":""));
                    tbc=new javax.swing.table.TableColumn( (intIndColGrp) );
                    tbc.setHeaderValue("" + strSubTit);
                    
                    if (j==0) { //Fec.Ven.
                        //Configurar JTable: Establecer el ancho de la columna.
                        tbc.setPreferredWidth(70);
                        
                        //Establecer JTable: Establecer columnas DataPicker
                        objDtePckEdiFecVenRea = new ZafDtePckEdi(strFormatoDatePicker);
                        tbc.setCellEditor(objDtePckEdiFecVenRea);   
                        
                        //Configurar JTable: Renderizar celdas.
                        objTblCelRenLblFec=new ZafTblCelRenLbl();
                        objTblCelRenLblFec.setHorizontalAlignment(javax.swing.JLabel.CENTER);
                        objTblCelRenLblFec.setTipoFormato(objTblCelRenLblFec.INT_FOR_FEC);    
                        tbc.setCellRenderer(objTblCelRenLblFec);
                        
                        //Configurar JTable: Establecer color en celdas
                        if((i % 2)==0) 
                            objTblCelRenLblFec.setBackground(colFonColPar);                       
                        else
                            objTblCelRenLblFec.setBackground(colFonColImp);  
                        objTblCelRenLblFec=null;
                    }
                    else if( (j==1) ) { //Val.Pag.
                        //Configurar JTable: Establecer el ancho de la columna.
                        tbc.setPreferredWidth(70);         
                        //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
                        objTblModDat.setColumnDataType(intIndColGrp, objTblModDat.INT_COL_DBL, new Integer(0), null);    
                        //Configurar JTable: Renderizar celdas.
                        objTblCelRenLblNumDin = new ZafTblCelRenLbl();
                        objTblCelRenLblNumDin.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                        objTblCelRenLblNumDin.setTipoFormato(objTblCelRenLblNumDin.INT_FOR_NUM);                           
                        objTblCelRenLblNumDin.setFormatoNumerico(objParSis.getFormatoNumero(), true, false); //Muestra 0
                        tbc.setCellRenderer(objTblCelRenLblNumDin); 
                        //Configurar JTable: Establecer color en celdas
                        if((i % 2)==0) 
                            objTblCelRenLblNumDin.setBackground(colFonColPar);                       
                        else
                            objTblCelRenLblNumDin.setBackground(colFonColImp);  
                        objTblCelRenLblNumDin=null;
                        
                        //Configurar JTable: Editor celdas.
                        objTblCelEdiTxtValPag=new ZafTblCelEdiTxt();
                        tbc.setCellEditor(objTblCelEdiTxtValPag);
                        objTblCelEdiTxtValPag.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() { 
                            int intFil=-1;
                            int intCol=-1;
                            String strDat="";  
                            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                intFil=tblDat.getSelectedRow();
                                intCol=tblDat.getSelectedColumn();
                            }
                            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                strDat=objTblModDat.getValueAt(intFil, intCol)==null?"0":(objTblModDat.getValueAt(intFil, intCol).toString().equals("")?"0":objTblModDat.getValueAt(intFil, intCol).toString()); 
                                if(objUti.isNumero(strDat)) {
                                    if( new BigDecimal (strDat).compareTo(BigDecimal.ZERO)<0) /* Valida que solo se pueda ingresar valores positivos. */
                                        objTblModDat.setValueAt("", intFil, intCol);  
                                } 
                            }
                        });                            
                    } 
                    
                    //Configurar JTable: Establecer el ancho de la columna.
                    if(!chkMosFecVenRea.isSelected()) {
                        /* Ocultar columnas de fechas de vencimiento, de acuerdo al filtro. */
                        tbc.setWidth(0); 
                        tbc.setResizable(false);
                        tbc.setMaxWidth(0);
                        tbc.setMinWidth(0);
                        tbc.setPreferredWidth(0);                
                    }                    

                    //Configurar JTable: Agregar la columna al JTable.
                    objTblModDat.addColumn(tblDat, tbc);
                    
                    //Configurar JTable: Agrupamiento de Columnas
                    if(j==0) //Primera Columna del Grupo.
                    {
                        objTblHeaColSubGrp=new ZafTblHeaColGrp("Fecha Vencimiento #"+ (i+1) );
                        objTblHeaColSubGrp.setHeight(16);
                        objTblHeaColGrpFec.add(objTblHeaColSubGrp); 
                    } 
                    objTblHeaGrpTblFecRea.addColumnGroup(objTblHeaColGrpFec);  
                    objTblHeaColSubGrp.add(tblDat.getColumnModel().getColumn(intIndColGrp));
                }
            }
            intNumColFinFecVenRea=objTblModDat.getColumnCount();
            objTblHeaGrpTblFecRea=null;
            objTblHeaColGrpFec=null;
            objTblHeaColSubGrp=null;
        }
        catch(Exception e){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e); 
        } 
        return blnRes;
    }         
    
    /**
     * Función que permite adicionar la columna de Estado de los pedidos
     * @return 
     */
    private boolean addColumnAddEstReg(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        intNumColAddEstReg=1;//es una columna solo para cargar el estado del item, si esta eliminado o no (solo es visual la eliminacion)
        intNumColIniEstReg=intNumColFinFecVenRea;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblEst=new ZafTblCelRenLbl();
            objTblCelRenLblEst.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            objTblCelRenLblEst.setTipoFormato(objTblCelRenLblEst.INT_FOR_GEN);
            objTblCelRenLblEst.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblModDat.getValueAt(objTblCelRenLblEst.getRowRender(), intNumColIniEstReg)==null?"":objTblModDat.getValueAt(objTblCelRenLblEst.getRowRender(), intNumColIniEstReg).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblEst.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblEst.setBackground(Color.WHITE);
                    }
                }
            });

            for (int i=0; i<intNumColAddEstReg; i++){
                String strSubTit=("Est.");
                tbc=new javax.swing.table.TableColumn(intNumColIniEstReg+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(30);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblEst);

                //Configurar JTable: Agregar la columna al JTable.
                objTblModDat.addColumn(tblDat, tbc);
            }
            intNumColFinEstReg=objTblModDat.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado. Pedidos embarcados
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        int intOrdFil=0;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            String strAuxFil="";
            if (con!=null){
                stm=con.createStatement();
                
                //objZafImp.getUltimaInstanciaPedidos(con);    //Cargar listado de pedidos: Última Instancia.
                objZafImp.getOpenNotaPedido(con);              //Cargar listado de Notas de Pedido Abiertas.
                objZafImp.getOpenPedidoEmbarcado(con);         //Cargar listado de Pedido Embarcado Abiertos.
                objZafImp.getOpenIngresoImportacion(con);      //Cargar listado de Ingreso por Importación Abiertos.
                
                //Filtro
                //Fecha de documento
                if(objSelFecDoc.isCheckBoxChecked()){
                    switch (objSelFecDoc.getTipoSeleccion()){
                        case 0: //Búsqueda por rangos
                            strAuxFil+=" AND y1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFecDoc.getFechaDesde(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFecDoc.getFechaHasta(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAuxFil+=" AND y1.fe_doc<='" + objUti.formatearFecha(objSelFecDoc.getFechaHasta(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAuxFil+=" AND y1.fe_doc>='" + objUti.formatearFecha(objSelFecDoc.getFechaDesde(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
                
                /* Otros Filtros */
                strAuxFil+=txtCodImp.getText().equals("")?"":" AND y1.co_imp=" + txtCodImp.getText() + "";
                strAuxFil+=txtCodExp.getText().equals("")?"":" AND y1.co_exp=" + txtCodExp.getText() + "";
                
                //Armar sentencia SQL
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCorTipDoc, a1.tx_desLarTipDoc, a1.co_doc, a1.ne_numDoc";
                strSQL+="      , CASE WHEN a1.ne_tipNotPed=1 THEN 'TM FOB' WHEN a1.ne_tipNotPed=2 THEN 'TM CFR'";
                strSQL+="             WHEN a1.ne_tipNotPed=3 THEN 'PZA FOB' WHEN a1.ne_tipNotPed=4 THEN 'PZA CFR' ELSE '' END AS tx_tipNotPed";
                strSQL+="      , a1.ne_tipNotPed, a1.tx_numPed, a1.fe_doc, a1.fe_preImp"; 
                strSQL+="      , a1.fe_embEst, a1.fe_embRea, a1.fe_emb, a1.fe_arr, a1.fe_pue";
                strSQL+="      , a1.co_imp, a1.tx_nomEmpImp, a1.co_exp, a1.tx_nomExp, a1.st_reg, a1.st_regPed";
                strSQL+="      , a1.fe_colPed, a1.tx_insPed, a1.st_ciePagAra, a1.st_Color";
                strSQL+=" FROM(\n";
                
                //<editor-fold defaultstate="collapsed" desc="/*NOTA DE PEDIDO*/">
                if(chkMosNotPed.isSelected()){
                    strSQL+=" SELECT z.co_emp, z.co_loc, z.co_tipDoc, z.co_doc, z.fe_doc, z.fe_preImp, z.ne_numDoc, z.tx_numPed";
                    strSQL+="      , z.co_imp, z.tx_nomEmpImp, z.co_exp, z.tx_nomExp, z.ne_tipNotPed";
                    strSQL+=" 	   , z.tx_desCorTipDoc, z.tx_desLarTipDoc, z.st_reg, z.fe_colPed";     
                    strSQL+="      , (''||z.fe_emb) AS fe_embEst, CAST('' AS CHARACTER VARYING) AS fe_embRea, (''||z.fe_emb) AS fe_emb\n";
                    strSQL+="      , (''||z.fe_pue) AS fe_pue, (''||z.fe_arr) AS fe_arr";
                    strSQL+=" 	   , z.tx_insPed, z.st_ciePagAra, z.st_regPed, z.st_color\n";
                    strSQL+=" FROM (\n";
                    strSQL+="       SELECT y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.fe_doc, y3.fe_preImp, y.ne_numDoc, y.tx_numPed";
                    strSQL+="            , y.co_imp, y.tx_nomEmpImp, y.co_exp, y.tx_nomExp, y1.ne_tipNotPed";
                    strSQL+=" 	         , y2.tx_desCor AS tx_desCorTipDoc, y2.tx_desLar AS tx_desLarTipDoc, y1.st_reg, y1.fe_colPed";
                    strSQL+="		 , y1.fe_emb, y1.fe_pue, y1.fe_arr";  
                    strSQL+="            , CAST( 'NOTPED' AS CHARACTER VARYING) AS tx_insPed, CAST('N' AS CHARACTER VARYING ) AS st_ciePagAra";
                    strSQL+="            , CAST('N' AS CHARACTER VARYING) AS st_regPed";
                     strSQL+="           , (CASE WHEN ( CAST (y1.fe_emb AS DATE) < CURRENT_DATE)  THEN 'R' ELSE  (CASE WHEN ( (CAST (y1.fe_emb AS DATE) - CURRENT_DATE) BETWEEN 0 AND 30 )  THEN 'A' ELSE 'N' END) END) AS st_color";
                    strSQL+="       FROM( "+objZafImp.getSQLNotaPedidoInstanciaOpen()+"  ) AS y"; 
                    strSQL+="       INNER JOIN tbm_cabNotPedImp AS y1 ON y.co_emp=y1.co_emp AND y.co_loc=y1.co_loc AND y.co_tipDoc=y1.co_tipDoc AND y.co_doc=y1.co_doc"; 
                    strSQL+="       INNER JOIN tbm_cabTipDoc AS y2 ON y1.co_emp=y2.co_emp AND y1.co_loc=y2.co_loc AND y1.co_tipDoc=y2.co_tipDoc"; 
                    strSQL+="       INNER JOIN (";
                    strSQL+="            SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc";
                    strSQL+="                 , ( (''||a1.ne_ani) ||'-'|| (  (CASE WHEN a1.ne_mes<9 THEN '0' ELSE ''END)||'' ||a1.ne_mes) ||'-'|| '01')  AS fe_preImp\n";
                    strSQL+="            FROM tbm_cabNotPedImp AS a "; 
                    strSQL+="            INNER JOIN tbm_cabNotPedPreImp AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_locRel AND a.co_tipDoc=a1.co_tipDocRel AND a.co_doc=a1.co_docRel";
                    strSQL+="            WHERE a1.st_reg IN ('A') AND a.st_reg IN ('A') AND (a.st_doc IN ('A') OR a.st_doc IS NULL) ";
                    strSQL+="       ) AS y3 ON y.co_emp=y3.co_emp AND y.co_loc=y3.co_loc AND y.co_tipDoc=y3.co_tipDoc AND y.co_doc=y3.co_doc ";
                    strSQL+="       WHERE y1.st_Reg IN ('A') ";  
                    //strSQL+="       AND y.st_notPedLis='N'";  //Se comenta validación porque AP necesita que se muestren las notas de pedido listas, aun cuando no tengan pedidos embarcados
                    strSQL+="       " + strAuxFil; 
                    strSQL+="       GROUP BY y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.fe_doc, y3.fe_preImp, y.ne_numDoc, y.tx_numPed";
                    strSQL+="              , y.co_imp, y.tx_nomEmpImp, y.co_exp, y.tx_nomExp, y1.ne_tipNotPed";
                    strSQL+="       	   , y2.tx_desCor, y2.tx_desLar, y1.st_reg, y1.fe_colPed";
                    strSQL+="		   , y1.fe_emb, y1.fe_pue, y1.fe_arr";  
                    strSQL+=" ) AS z\n";   
                }
                //</editor-fold>
                
                if( (chkMosNotPed.isSelected()) && (chkMosPedEmb.isSelected()) ) {
                    strSQL+="        UNION\n";
                }

                //<editor-fold defaultstate="collapsed" desc="/*PEDIDO EMBARCADO*/">
                if((chkMosPedEmb.isSelected())){
                    strSQL+=" SELECT z.co_emp, z.co_loc, z.co_tipDoc, z.co_doc, z.fe_doc, z.fe_PreImp, z.ne_numDoc, z.tx_numPed";
                    strSQL+="      , z.co_imp, z.tx_nomEmpImp, z.co_exp, z.tx_nomExp, z.ne_tipNotPed";
                    strSQL+=" 	   , z.tx_desCorTipDoc, z.tx_desLarTipDoc, z.st_reg, z.fe_colPed";     
                    strSQL+="      , z.fe_embEst, z.fe_embRea, z.fe_emb, z.fe_pue, z.fe_arr, z.tx_insPed, z.st_ciePagAra, z.st_regPed, z.st_color"; 
                    strSQL+=" FROM (";
                    strSQL+="       SELECT y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.fe_doc, y3.fe_preImp, y.ne_numDoc, y.tx_numPed";
                    strSQL+="            , y.co_imp, y.tx_nomEmpImp, y.co_exp, y.tx_nomExp, y1.ne_tipNotPed";
                    strSQL+=" 	         , y2.tx_desCor AS tx_desCorTipDoc, y2.tx_desLar AS tx_desLarTipDoc, y1.st_reg, y1.fe_colPed";
                    strSQL+="            , (CASE WHEN y1.st_emb IN ('N') THEN  (''||y1.fe_emb) ELSE CAST('' AS CHARACTER VARYING) END) AS fe_embEst";
                    strSQL+="            , (CASE WHEN y1.st_emb IN ('N') THEN  CAST('' AS CHARACTER VARYING) ELSE (''||y1.fe_emb) END) AS fe_embRea, (''||y1.fe_emb) AS fe_emb";
                    strSQL+=" 	         , ''||y1.fe_pue AS fe_pue, ''||y1.fe_arr AS fe_arr";
                    strSQL+="            , CAST( 'PEDEMB' AS CHARACTER VARYING) AS tx_insPed, ( CASE WHEN y1.st_ciePagImp IS NULL THEN 'S' ELSE y1.st_ciePagImp END ) AS st_ciePagAra";
                    strSQL+="            , CAST('N' AS CHARACTER VARYING) AS st_color";
                    strSQL+="            , CAST('P' AS CHARACTER VARYING) AS st_regPed";
                    strSQL+="       FROM( "+objZafImp.getSQLPedidoEmbarcadoInstanciaOpen()+"  ) AS y"; 
                    strSQL+="       INNER JOIN tbm_cabPedEmbImp AS y1 ON y.co_emp=y1.co_emp AND y.co_loc=y1.co_loc AND y.co_tipDoc=y1.co_tipDoc AND y.co_doc=y1.co_doc"; 
                    strSQL+="       INNER JOIN tbm_cabTipDoc AS y2 ON y1.co_emp=y2.co_emp AND y1.co_loc=y2.co_loc AND y1.co_tipDoc=y2.co_tipDoc"; 
                    strSQL+="       LEFT OUTER JOIN (";
                    strSQL+="            SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc";
                    strSQL+="                 , ( (''||a4.ne_ani) ||'-'|| (  (CASE WHEN a4.ne_mes<9 THEN '0' ELSE ''END)||'' ||a4.ne_mes) ||'-'|| '01')  AS fe_preImp";
                    strSQL+="            FROM tbm_cabPedEmbImp AS a";
                    strSQL+="            INNER JOIN tbm_detPedEmbImp AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc";
                    strSQL+="            INNER JOIN tbm_detNotPedImp AS a2 ON a1.co_emp=a2.co_emp AND a1.co_locRel=a2.co_loc AND a1.co_tipDocRel=a2.co_tipDoc AND a1.co_docRel=a2.co_doc AND a1.co_regRel=a2.co_reg";
                    strSQL+="            INNER JOIN tbm_cabNotPedImp AS a3 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                    strSQL+="            INNER JOIN tbm_cabNotPedPreImp AS a4 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_locRel AND a3.co_tipDoc=a4.co_tipDocRel AND a3.co_doc=a4.co_docRel";
                    strSQL+="            WHERE a.st_reg IN ('A') AND (a.st_doc IN ('A') OR a.st_doc IS NULL) ";
                    strSQL+="            AND a3.st_reg IN ('A') AND (a3.st_doc IN ('A') OR a3.st_doc IS NULL) AND a4.st_reg IN ('A')"; 
                    strSQL+="            GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a4.ne_ani, a4.ne_mes ";                   
                    strSQL+="       ) AS y3 ON y.co_emp=y3.co_emp AND y.co_loc=y3.co_loc AND y.co_tipDoc=y3.co_tipDoc AND y.co_doc=y3.co_doc ";                    
                    strSQL+="       WHERE y1.st_Reg IN ('A') ";  
                    strSQL+="       " + strAuxFil;
                    strSQL+="       GROUP BY y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.fe_doc, y3.fe_preImp, y.ne_numDoc, y.tx_numPed";
                    strSQL+="              , y.co_imp, y.tx_nomEmpImp, y.co_exp, y.tx_nomExp, y1.ne_tipNotPed";
                    strSQL+=" 	           , y2.tx_desCor, y2.tx_desLar, y1.st_reg, y1.fe_colPed";
                    strSQL+="              , y1.st_emb, y1.fe_emb, y1.fe_pue, y1.fe_arr, y1.st_ciePagImp ";
                    strSQL+=" ) AS z\n";
                }
                //</editor-fold>
                
                if( ( (chkMosNotPed.isSelected()) || (chkMosPedEmb.isSelected()) )  &&  ( (chkMosPedLleDir.isSelected()) || (chkMosPedLleInd.isSelected()) )  )
                {
                    strSQL+="        UNION\n";
                }
                
                //<editor-fold defaultstate="collapsed" desc="/*INGRESOS POR IMPORTACION */">
                if( (chkMosPedLleDir.isSelected())  || (chkMosPedLleInd.isSelected()) ){
                    strSQL+=" SELECT z.co_emp, z.co_loc, z.co_tipDoc, z.co_doc, z.fe_doc, z.fe_PreImp, z.ne_numDoc, z.tx_numPed";
                    strSQL+="      , z.co_imp, z.tx_nomEmpImp, z.co_exp, z.tx_nomExp, z.ne_tipNotPed";
                    strSQL+=" 	   , z.tx_desCorTipDoc, z.tx_desLarTipDoc, z.st_reg, z.fe_colPed";     
                    strSQL+="      , z.fe_embEst, z.fe_embRea, z.fe_emb, z.fe_arr, z.fe_pue, z.tx_insPed, z.st_ciePagAra, z.st_regPed, z.st_color"; 
                    strSQL+=" FROM (";
                    strSQL+="       SELECT y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.fe_doc, y4.fe_preImp, y.ne_numDoc, y.tx_numPed";
                    strSQL+="            , y.co_imp, y.tx_nomEmpImp, y.co_exp, y.tx_nomExp, y1.ne_tipNotPed";
                    strSQL+=" 	         , y2.tx_desCor AS tx_desCorTipDoc, y2.tx_desLar AS tx_desLarTipDoc, y1.st_reg, y1.fe_colPed";
                    strSQL+="            , CAST('' AS CHARACTER VARYING) AS fe_embEst, ''||y3.fe_Emb AS fe_embRea, ''||y3.fe_Emb AS fe_emb";
                    strSQL+=" 	         , ''||y.fe_doc AS fe_arr, ''||y3.fe_pue AS fe_pue";
                    strSQL+="            , CAST( 'INIMPO' AS CHARACTER VARYING) AS tx_insPed, CAST( 'S' AS CHARACTER VARYING) AS st_ciePagAra";
                    strSQL+="            , CAST('N' AS CHARACTER VARYING) AS st_color";
                    strSQL+="            , CAST('P' AS CHARACTER VARYING) AS st_regPed"; 
                    strSQL+="       FROM( "+objZafImp.getSQLIngresoImportacionInstanciaOpen()+"  ) AS y"; 
                    strSQL+="       INNER JOIN tbm_cabMovInv AS y1 ON y.co_emp=y1.co_emp AND y.co_loc=y1.co_loc AND y.co_tipDoc=y1.co_tipDoc AND y.co_doc=y1.co_doc"; 
                    strSQL+="       INNER JOIN tbm_cabTipDoc AS y2 ON y1.co_emp=y2.co_emp AND y1.co_loc=y2.co_loc AND y1.co_tipDoc=y2.co_tipDoc"; 
                    strSQL+="       INNER JOIN tbm_cabPedEmbImp AS y3 ON y1.co_empRelPedEmbImp=y3.co_emp AND y1.co_locRelPedEmbImp=y3.co_loc AND y1.co_tipDocRelPedEmbImp=y3.co_tipDoc AND y1.co_docRelPedEmbImp=y3.co_doc"; 
                    strSQL+="       LEFT OUTER JOIN (";
                    strSQL+="            SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, ( (''||a5.ne_ani) ||'-'|| (  (CASE WHEN a5.ne_mes<9 THEN '0' ELSE ''END)||'' ||a5.ne_mes) ||'-'|| '01')  AS fe_preImp"; 
                    strSQL+="            FROM tbm_cabMovInv AS a"; 
                    strSQL+="            INNER JOIN tbm_cabPedEmbImp AS a1 ON a.co_empRelPedEmbImp=a1.co_emp AND a.co_locRelPedEmbImp=a1.co_loc AND a.co_tipDocRelPedEmbImp=a1.co_tipDoc AND a.co_docRelPedEmbImp=a1.co_doc"; 
                    strSQL+="            INNER JOIN tbm_detPedEmbImp AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc"; 
                    strSQL+="            INNER JOIN tbm_detNotPedImp AS a3 ON a2.co_emp=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_tipDocRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc AND a2.co_regRel=a3.co_reg"; 
                    strSQL+="            INNER JOIN tbm_cabNotPedImp AS a4 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc"; 
                    strSQL+="            INNER JOIN tbm_cabNotPedPreImp AS a5 ON a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_locRel AND a4.co_tipDoc=a5.co_tipDocRel AND a4.co_doc=a5.co_docRel"; 
                    strSQL+="            WHERE a.st_reg IN ('A') AND a1.st_reg IN ('A')  AND (a1.st_doc IN ('A') OR a1.st_doc IS NULL) ";  
                    strSQL+="            AND a4.st_reg IN ('A')  AND (a4.st_doc IN ('A') OR a4.st_doc IS NULL) AND a5.st_reg IN ('A')"; 
                    strSQL+="            GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a5.ne_ani, a5.ne_mes";                   
                    strSQL+="       ) AS y4 ON y.co_emp=y4.co_emp AND y.co_loc=y4.co_loc AND y.co_tipDoc=y4.co_tipDoc AND y.co_doc=y4.co_doc ";                              
                    strSQL+="       WHERE y1.st_Reg IN ('A') ";  
                    strSQL+="       " + strAuxFil;
                    
                    /* Pedidos Llegados */
                    if( (chkMosPedLleDir.isSelected()) && (!chkMosPedLleInd.isSelected()) ) { /*Mostrar sólo pedidos directos.*/
                        strSQL+=" AND y.co_exp NOT IN ("+strCodExpHavelock+")";    
                    }
                    else if( (chkMosPedLleInd.isSelected()) && (!chkMosPedLleDir.isSelected()) ) { /*Mostrar sólo pedidos indirectos.*/
                        strSQL+=" AND y.co_exp IN ("+strCodExpHavelock+")"; 
                    }
                    
                    strSQL+="       GROUP BY y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.fe_doc, y4.fe_preImp, y.ne_numDoc, y.tx_numPed";
                    strSQL+="              , y.co_imp, y.tx_nomEmpImp, y.co_exp, y.tx_nomExp, y1.ne_tipNotPed";
                    strSQL+=" 	           , y2.tx_desCor, y2.tx_desLar, y1.st_reg, y1.fe_colPed";
                    strSQL+=" 	           , y3.fe_Emb, y3.fe_pue/*, y3.fe_arr*/";
                    strSQL+=" ) AS z\n";
                }
                //</editor-fold>                
                
                strSQL+=" ) AS a1";
                strSQL+=" ORDER BY a1.fe_emb, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.fe_preImp, a1.ne_numDoc, a1.ne_tipNotPed, a1.tx_numPed";
                System.out.println("cargarDetReg: "+strSQL); 
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()){
                    if (blnCon){     
                        intOrdFil++;
                        //Cargar los datos en la Tabla Datos
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_INS_PED,         rst.getString("tx_insPed"));       //tx_insPed
                        vecReg.add(INT_TBL_DAT_COD_EMP,         rst.getString("co_emp"));          //co_emp
                        vecReg.add(INT_TBL_DAT_COD_LOC,         rst.getString("co_loc"));          //co_loc
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     rst.getString("co_tipDoc"));       //co_tipDoc
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, rst.getString("tx_desCorTipDoc")); //tx_desCorTipDoc
                        vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, rst.getString("tx_desLarTipDoc")); //tx_desLarTipDoc
                        vecReg.add(INT_TBL_DAT_COD_DOC,         rst.getString("co_doc"));          //co_doc
                        vecReg.add(INT_TBL_DAT_NUM_DOC,         rst.getString("ne_numDoc"));       //ne_numDoc
                        vecReg.add(INT_TBL_DAT_NUM_PED,         rst.getString("tx_numPed"));       //tx_numPed
                        vecReg.add(INT_TBL_DAT_TIP_NOT_PED,     rst.getString("tx_tipNotPed"));    //tx_tipNotPed
                        vecReg.add(INT_TBL_DAT_COD_IMP,         rst.getString("co_imp"));          //co_imp
                        vecReg.add(INT_TBL_DAT_NOM_IMP,         rst.getString("tx_nomEmpImp"));    //tx_nomEmpImp
                        vecReg.add(INT_TBL_DAT_COD_EXP,         rst.getString("co_exp"));          //co_exp
                        vecReg.add(INT_TBL_DAT_NOM_EXP,         rst.getString("tx_nomExp"));       //tx_nomExp
                        vecReg.add(INT_TBL_DAT_FEC_PRE_IMP,     (rst.getString("fe_preImp")==null?"":rst.getString("fe_preImp").equals("")?"":rst.getString("fe_preImp")));       //fe_preImp
                        vecReg.add(INT_TBL_DAT_FEC_COL_PED,     rst.getString("fe_colPed"));       //fe_colPed
                        vecReg.add(INT_TBL_DAT_FEC_EMB_EST,     (rst.getString("fe_embEst").equals("")?"":rst.getString("fe_embEst"))); //fe_embEst: Fecha Embarque Estimada.
                        vecReg.add(INT_TBL_DAT_FEC_EMB_REA,     (rst.getString("fe_embRea").equals("")?"":rst.getString("fe_embRea"))); //fe_embRea: Fecha Embarque Real.
                        vecReg.add(INT_TBL_DAT_FEC_PUE,         rst.getString("fe_pue"));          //fe_pue
                        vecReg.add(INT_TBL_DAT_FEC_ARR_BOD,     rst.getString("fe_arr"));          //fe_arr
                        vecReg.add(INT_TBL_DAT_VAL_TOT_FAC,     null); 
                        vecReg.add(INT_TBL_DAT_VAL_TOT_ABO,     null);
                        vecReg.add(INT_TBL_DAT_VAL_TOT_PEN,     null);
                        vecReg.add(INT_TBL_DAT_BUT,             null);
                        vecReg.add(INT_TBL_DAT_EST_PAG_ARA,     rst.getString("st_ciePagAra"));   //st_ciePagAra  
                        vecReg.add(INT_TBL_DAT_EST_COL_FIL,     null);
                        vecReg.add(INT_TBL_DAT_ORD_FIL,         ""+intOrdFil);
                        
                        //Columnas dinámicas de Cargos a Pagar
                        for(int j=intNumColIniCarPag; j<intNumColFinCarPag;j++){
                            vecReg.add(j,     "0");
                        }
                        //Columnas dinámicas de Totales de Cargos a Pagar
                        for(int j=intNumColIniTotCarPag; j<intNumColFinTotCarPag;j++){
                            vecReg.add(j,     "0");
                        }
                        //Columnas dinámicas de Fechas de vencimiento Estimadas
                        for(int j=intNumColIniFecVenEst; j<intNumColFinFecVenEst;j++){
                            vecReg.add(j,     "");
                        }
                        //Columnas dinámicas de Fechas de vencimiento Reales
                        for(int j=intNumColIniFecVenRea; j<intNumColFinFecVenRea;j++){
                            vecReg.add(j,     "");
                        }        
                        //Columnas dinámica de Estado de registro
                        for(int j=intNumColIniEstReg; j<intNumColFinEstReg;j++){
                            vecReg.add(j,     rst.getString("st_reg"));
                        }
                        
                        //Se establece el color de la fila.
                        strAux= rst.getString("st_color")==null?"N":rst.getString("st_color");
                        if (! strAux.equals("") )  {
                            vecReg.setElementAt(strAux, INT_TBL_DAT_EST_COL_FIL);
                        }                        
                        vecDat.add(vecReg);    
                        
                        //Cargar los datos en la Tabla Fija
                        vecRegFix=new Vector();
                        vecRegFix.add(INT_TBL_DAT_FIX_LIN,"");
                        vecRegFix.add(INT_TBL_DAT_FIX_INS_PED,         rst.getString("tx_insPed"));       //tx_insPed
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_EMP,         rst.getString("co_emp"));          //co_emp
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_LOC,         rst.getString("co_loc"));          //co_loc
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_TIP_DOC,     rst.getString("co_tipDoc"));       //co_tipDoc
                        vecRegFix.add(INT_TBL_DAT_FIX_DES_COR_TIP_DOC, rst.getString("tx_desCorTipDoc")); //tx_desCorTipDoc
                        vecRegFix.add(INT_TBL_DAT_FIX_DES_LAR_TIP_DOC, rst.getString("tx_desLarTipDoc")); //tx_desLarTipDoc
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_DOC,         rst.getString("co_doc"));          //co_doc
                        vecRegFix.add(INT_TBL_DAT_FIX_NUM_DOC,         rst.getString("ne_numDoc"));       //ne_numDoc
                        vecRegFix.add(INT_TBL_DAT_FIX_NUM_PED,         rst.getString("tx_numPed"));       //tx_numPed
                        vecRegFix.add(INT_TBL_DAT_FIX_EST_COL_FIL,     null);
                        vecRegFix.add(INT_TBL_DAT_FIX_ORD_FIL,         ""+intOrdFil);
                        //Se establece el color de la fila.
                        strAux= rst.getString("st_color")==null?"N":rst.getString("st_color");
                        if (! strAux.equals("") )  {
                            vecRegFix.setElementAt(strAux, INT_TBL_DAT_FIX_EST_COL_FIL);
                        }                            
                        vecDatFix.add(vecRegFix);                        
                    }
                    else
                    {
                        break;
                    }
                }
                rst.close(); 
                stm.close();
                rst=null;
                stm=null;
                        
                //Asignar vectores al modelo.
                //Tabla de Datos
                objTblModDat.setData(vecDat);
                tblDat.setModel(objTblModDat);
                vecDat.clear();
                objTblModDat.initRowsState();        
                
                //Tabla Fija
                objTblModFix.setData(vecDatFix);
                tblFix.setModel(objTblModFix);
                vecDatFix.clear();
            }
        }
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Función que permite cargar los valores abonados y pendientes de los Pedidos seleccionados
     * @return true Si se realizó la operación
     * <BR> false Caso contrario
     */
    private boolean cargarValFacAboPen(){
        boolean blnRes=true;
        String strCodEmp="";
        String strCodLoc="";
        String strCodTipDoc="";
        String strCodDoc="";
        BigDecimal bgdValAux=BigDecimal.ZERO;
        int intCodTipDocTraBan=0;
        try{
            if(con!=null){
                intCodTipDocTraBan=objZafImp.INT_COD_TIP_DOC_TRA_BAN_EXT;
                for(int i=0; i<objTblModDat.getRowCountTrue(); i++){
                    stm=con.createStatement();
                    
                    strCodEmp=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_EMP)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_EMP).toString());
                    strCodLoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_LOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_LOC).toString());
                    strCodTipDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString());
                    strCodDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_DOC).toString());
                    
                    String strInsPed=objUti.parseString(objTblModDat.getValueAt(i,INT_TBL_DAT_INS_PED)); 
                    String strNomTbl=(strInsPed.equals("NOTPED")?"NotPedImp": (strInsPed.equals("PEDEMB")?"PedEmbImp":(strInsPed.equals("INIMPO")?"MovInv":""))); 
                    
                    strSQL ="";
                    strSQL+=" SELECT x.*, (x.nd_ValFac - x.nd_valAbo) AS nd_ValPen";
                    strSQL+=" FROM (";  
                    strSQL+="        SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.co_cta, a2.tx_numDoc2";
                    strSQL+="             , (CASE WHEN a3.nd_valAbo IS NULL THEN 0 ELSE a3.nd_valAbo END) AS nd_ValAbo";    
                    if(strInsPed.equals("NOTPED")){
                        strSQL+="         , SUM(CASE WHEN a1.nd_valCarPag IS NULL THEN 0 ELSE (a1.nd_valCarPag - ( CASE WHEN a4.nd_ValPedEmb IS NULL THEN 0 ELSE a4.nd_ValPedEmb END ) ) END ) AS nd_ValFac"; //Se presenta valor parcial
                    }
                    else{
                        strSQL+="         , SUM(CASE WHEN a1.nd_valCarPag IS NULL THEN 0 ELSE a1.nd_valCarPag END ) AS nd_ValFac";
                    }
                    strSQL+="        FROM tbm_carPagImp AS a";  
                    strSQL+="        INNER JOIN tbm_carPag"+strNomTbl+" AS a1 ON a.co_CarPag=a1.co_CarPag AND a.tx_tipCarPag IN ('V')"; 
                    strSQL+="        INNER JOIN tbm_cab"+strNomTbl+" AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc"; 
                    if(strInsPed.equals("INIMPO")){
                        strSQL+="    INNER JOIN tbm_cabPedEmbImp AS a4 ON a2.co_empRelPedEmbImp=a4.co_emp AND a2.co_locRelPedEmbImp=a4.co_loc AND a2.co_tipDocRelPedEmbImp=a4.co_tipDoc AND a2.co_docRelPedEmbImp=a4.co_doc"; 
                    }     
                    
                    if(strInsPed.equals("NOTPED")){
                        strSQL+="    LEFT OUTER JOIN("; 
                        strSQL+="        SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.tx_numPed, SUM(x.nd_ValCarPag) AS nd_ValPedEmb";
                        strSQL+="        FROM (";
                        strSQL+="            SELECT a.tx_tipCarPag, a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a1.nd_ValCarPag";
                        strSQL+="                , (CASE WHEN a4.tx_numDoc3 IS NOT NULL THEN a4.tx_numDoc3 ELSE a4.tx_numDoc2 END) AS tx_numPed";
                        strSQL+="            FROM tbm_CarPagImp AS a";
                        strSQL+="            INNER JOIN tbm_carPagPedEmbImp AS a1 ON a.co_carPag=a1.co_carPag";
                        strSQL+="            INNER JOIN tbm_cabPedEmbImp AS a5 ON a1.co_Emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_TipDOc=a5.co_tipDoc AND a1.co_Doc=a5.co_doc";
                        strSQL+="            INNER JOIN tbm_detPedEmbImp AS a2 ON a5.co_Emp=a2.co_emp AND a5.co_loc=a2.co_loc AND a5.co_TipDOc=a2.co_tipDoc AND a5.co_Doc=a2.co_doc";
                        strSQL+="            INNER JOIN tbm_detNotPedImp AS a3 ON a2.co_Emp=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_TipDocRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc";
                        strSQL+="            INNER JOIN tbm_cabNotPedImp AS a4 ON a3.co_Emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_TipDOc=a4.co_tipDoc AND a3.co_Doc=a4.co_doc";
                        strSQL+="            WHERE a.tx_tipCarPag IN ('V') AND a4.st_cie IN ('N') ";
                        strSQL+="            AND a4.st_reg IN ('A') AND (a4.st_doc IN ('A') OR a4.st_doc IS NULL)";
                        strSQL+="            AND a5.st_reg IN ('A') AND (a5.st_doc IN ('A') OR a5.st_doc IS NULL)";
                        strSQL+="            GROUP BY a.tx_tipCarPag, a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a1.nd_ValCarPag, a4.tx_numDoc2, a4.tx_numDoc3";
                        strSQL+="        ) AS x";
                        strSQL+="        WHERE NOT ( (x.tx_numPed LIKE '%PS%') OR (x.tx_numPed LIKE '%PL%') ) /* No mostrar compras locales. */";
                        strSQL+="        GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.tx_numPed";
                        strSQL+="    ) AS a4";    
                        strSQL+="    ON a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc AND a2.co_doc=a4.co_doc"; 
                    }
                    strSQL+="        LEFT OUTER JOIN( "; 
                    strSQL+="             SELECT x.co_cta, x.co_emp, SUM(CASE WHEN x.nd_monDeb IS NULL THEN 0 ELSE x.nd_monDeb END ) AS nd_valAbo";  
                    strSQL+="             FROM tbm_detDia AS x"; 
                    strSQL+=" 	          INNER JOIN tbm_cabDia AS x1 ON x.co_emp=x1.co_emp AND x.co_loc=x1.co_loc AND x.co_tipDoc=x1.co_tipDoc AND x.co_dia=x1.co_dia"; 
                    strSQL+=" 	          AND x1.st_reg IN ('A') AND x1.co_tipDoc="+intCodTipDocTraBan;  //TRABAEX
                    strSQL+="             GROUP BY x.co_cta, x.co_emp";
                    strSQL+="        ) AS a3";
                    if(strInsPed.equals("INIMPO"))
                        strSQL+="   ON a2.co_emp=a3.co_emp AND a4.co_CtaPas=a3.co_Cta";
                    else /* NotPed.; Ped.Emb.*/
                        strSQL+="   ON a2.co_imp=a3.co_emp AND a2.co_CtaPas=a3.co_Cta";
                    strSQL+="   WHERE a1.co_emp=" + strCodEmp;
                    strSQL+="   AND a1.co_loc=" + strCodLoc;
                    strSQL+="   AND a1.co_tipDoc=" + strCodTipDoc;
                    strSQL+="   AND a1.co_doc=" + strCodDoc;
                    strSQL+="   GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.co_cta, a2.tx_numDoc2, a3.nd_valAbo";  
                    strSQL+=" ) AS x";    
                    //System.out.println("cargarValFacAboPen: "+strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        //<editor-fold defaultstate="collapsed" desc="/* LSC: Presenta el valor de la factura (Pago a proveedores del exterior). Actualizar en la columna Tot.Fob.Cfr, con el valor del cargo a pagar "Valor Factura".*/">
                        bgdValAux=new BigDecimal(rst.getObject("nd_valFac")==null?"0":(rst.getString("nd_valFac").equals("")?"0":rst.getString("nd_valFac")));
                        objTblModDat.setValueAt(bgdValAux, i, INT_TBL_DAT_VAL_TOT_FAC);
                        //</editor-fold>
                        
                        bgdValAux=new BigDecimal(rst.getObject("nd_valAbo")==null?"0":(rst.getString("nd_valAbo").equals("")?"0":rst.getString("nd_valAbo")));
                        objTblModDat.setValueAt(bgdValAux, i, INT_TBL_DAT_VAL_TOT_ABO);
                        
                        bgdValAux=new BigDecimal(rst.getObject("nd_valPen")==null?"0":(rst.getString("nd_valPen").equals("")?"0":rst.getString("nd_valPen")));
                        objTblModDat.setValueAt(bgdValAux, i, INT_TBL_DAT_VAL_TOT_PEN);
                    }
                    rst.close();
                    rst=null;
                    stm.close();
                    stm=null;
                }
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
     * Función que permite cargar los valores de Cargos a Pagar de los Pedidos seleccionados
     * @return true Si se realizó la operación
     * <BR> false Caso contrario
     */
    private boolean cargarValCarPagPed(){
        boolean blnRes=true;
        String strCodEmp="";
        String strCodLoc="";
        String strCodTipDoc="";
        String strCodDoc="";
        int intColCarPag=-1;
        BigDecimal bgdValCarPag=BigDecimal.ZERO;
        try{
            if(con!=null){
                if(chkMosCarPag.isSelected()) {
                    for(int i=0; i<objTblModDat.getRowCountTrue(); i++){
                        stm=con.createStatement();

                        strCodEmp=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_EMP)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_EMP).toString());
                        strCodLoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_LOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_LOC).toString());
                        strCodTipDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString());
                        strCodDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_DOC).toString());
                        
                        String strInsPed=objUti.parseString(objTblModDat.getValueAt(i,INT_TBL_DAT_INS_PED)); 
                        String strNomTbl=(strInsPed.equals("NOTPED")?"NotPedImp": (strInsPed.equals("PEDEMB")?"PedEmbImp":(strInsPed.equals("INIMPO")?"MovInv":""))); 

                        for(int j=0; j<arlDatCarPag.size(); j++){
                            intColCarPag=objUti.getIntValueAt(arlDatCarPag, j, INT_ARL_CAR_PAG_COL_CAR_PAG);
                            strSQL ="";
                            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.co_carPag, a2.tx_tipCarPag";
                            strSQL+="     , (CASE WHEN a1.nd_valCarPag IS NULL THEN 0 ELSE a1.nd_valCarPag END) AS nd_valCarPag";
//                          strSQL+="     , ( (CASE WHEN a1.nd_valCarPag IS NULL THEN 0 ELSE a1.nd_valCarPag END) - (CASE WHEN a3.nd_valCarPag IS NULL THEN 0 ELSE a3.nd_valCarPag END)  ) AS nd_valCarPag";
                            strSQL+=" FROM tbm_carPag"+strNomTbl+" AS a1 INNER JOIN tbm_carPagImp AS a2 ON a1.co_carPag=a2.co_carPag";
//                          if(strInsPed.equals("NOTPED")) {
//                             strSQL+=" LEFT OUTER JOIN ("; /* Muestre el valor del cargo parcial, cuando tiene Pedido Embarcado. */
//                             strSQL+="    SELECT a1.co_emp, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a.co_carPag, a.nd_ValCarPag";
//                             strSQL+="    FROM tbm_carPagPedEmbImp AS a INNER JOIN tbm_detPedEmbImp AS a1";
//                             strSQL+="    ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc";
//                             strSQL+="    INNER JOIN tbm_cabPedEmbImp AS a2 ON a1.co_Emp=a2.co_Emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_Doc AND a2.st_Reg IN ('A')";
//                             strSQL+="    GROUP BY a1.co_emp, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a.co_carPag, a.nd_ValCarPag";
//                             strSQL+=" ) AS a3";
//                             strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_locRel AND a1.co_tipDoc=a3.co_tipDocRel AND a1.co_doc=a3.co_docRel AND a1.co_carPag=a3.co_carPag";
//                          }
                            strSQL+=" WHERE a1.co_emp=" + strCodEmp + "";
                            strSQL+=" AND a1.co_loc=" + strCodLoc + "";
                            strSQL+=" AND a1.co_tipDoc=" + strCodTipDoc + "";
                            strSQL+=" AND a1.co_doc=" + strCodDoc + "";
                            strSQL+=" AND a1.co_carPag=" + objUti.getIntValueAt(arlDatCarPag, j, INT_ARL_CAR_PAG_COD_CAR_PAG) + "";
                            strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.co_carPag, a2.tx_tipCarPag, a1.nd_valCarPag";
                            rst=stm.executeQuery(strSQL);
                            if(rst.next()){
                                bgdValCarPag=new BigDecimal(rst.getObject("nd_valCarPag")==null?"0":(rst.getString("nd_valCarPag").equals("")?"0":rst.getString("nd_valCarPag")));
                                objTblModDat.setValueAt(bgdValCarPag, i, intColCarPag);
                                //<editor-fold defaultstate="collapsed" desc="/* LSC: Presenta el valor de la factura (Pago a proveedores del exterior). Actualizar en la columna Tot.Fob.Cfr, con el valor del cargo a pagar "Valor Factura".*/">
                                // if(rst.getString("tx_tipCarPag").equals("V")){
                                //     objTblModDat.setValueAt(bgdValCarPag, i, INT_TBL_DAT_VAL_TOT_FAC);
                                // }
                                //</editor-fold>
                            }
                            rst.close();
                            rst=null;
                        }
                        stm.close();
                        stm=null;
                    }
                }
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
     * Función que permite cargar los totales de los Cargos a Pagar de los Pedidos seleccionados
     * <BR> Segmentando por tipo de cargo, tales como: Gastos, Aranceles, etc.
     * @return true Si se realizó la operación
     * <BR> false Caso contrario
     */
    private boolean cargarValTotCarPagPed(){
        boolean blnRes=true;
        String strCodEmp="";
        String strCodLoc="";
        String strCodTipDoc="";
        String strCodDoc="";
        String strRstTotTipCarPag="";
        String strArlTotTipCarPag="";
        int intNumColTotCarPag=-1;
        BigDecimal bgdTotValCarPag=BigDecimal.ZERO;
        try{
            if(con!=null){
                if(chkMosTotCarPag.isSelected()) {
                    for(int i=0; i<objTblModDat.getRowCountTrue(); i++){
                        stm=con.createStatement();

                        strCodEmp=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_EMP)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_EMP).toString());
                        strCodLoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_LOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_LOC).toString());
                        strCodTipDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString());
                        strCodDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_DOC).toString());

                        String strInsPed=objUti.parseString(objTblModDat.getValueAt(i,INT_TBL_DAT_INS_PED)); 
                        String strNomTbl=(strInsPed.equals("NOTPED")?"NotPedImp": (strInsPed.equals("PEDEMB")?"PedEmbImp":(strInsPed.equals("INIMPO")?"MovInv":""))); 

                        strSQL ="";
                        strSQL+=" SELECT * FROM (";
                        strSQL+="   SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.tx_tipCarPag";
                        strSQL+="        , SUM (CASE WHEN a1.nd_valCarPag IS NULL THEN 0 ELSE a1.nd_valCarPag END) AS nd_valTotCarPag";
//                      strSQL+="        , SUM( (CASE WHEN a1.nd_valCarPag IS NULL THEN 0 ELSE a1.nd_valCarPag END) - (CASE WHEN a3.nd_valCarPag IS NULL THEN 0 ELSE a3.nd_valCarPag END)  ) AS nd_valTotCarPag";
                        strSQL+="   FROM tbm_carPag"+strNomTbl+" AS a1 ";
                        strSQL+="   INNER JOIN tbm_carPagImp AS a2 ON a1.co_carPag=a2.co_carPag ";
//                      if(strInsPed.equals("NOTPED")) {                        
//                         strSQL+="   LEFT OUTER JOIN ( /* Muestre el valor del cargo parcial, cuando tiene Pedido Embarcado. */";
//                         strSQL+="       SELECT a1.co_emp, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a.co_carPag, a.nd_ValCarPag";
//                         strSQL+="       FROM tbm_carPagPedEmbImp AS a INNER JOIN tbm_detPedEmbImp AS a1";
//                         strSQL+="       ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc";
//                         strSQL+="       INNER JOIN tbm_cabPedEmbImp AS a2 ON a1.co_Emp=a2.co_Emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_Doc AND a2.st_Reg IN ('A')";
//                         strSQL+="       GROUP BY a1.co_emp, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a.co_carPag, a.nd_ValCarPag";
//                         strSQL+="   ) AS a3";
//                         strSQL+="   ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_locRel AND a1.co_tipDoc=a3.co_tipDocRel AND a1.co_doc=a3.co_docRel AND a1.co_carPag=a3.co_carPag";
//                      }
                        strSQL+="   WHERE a1.co_emp=" + strCodEmp + "";
                        strSQL+="   AND a1.co_loc=" + strCodLoc + "";
                        strSQL+="   AND a1.co_tipDoc=" + strCodTipDoc + "";
                        strSQL+="   AND a1.co_doc=" + strCodDoc + "";
                        strSQL+="   AND a2.tx_tipCarPag IN("+strTipCarPag+")";
                        strSQL+="   GROUP BY  a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.tx_tipCarPag";
                        strSQL+=" ) AS x";
                        strSQL+=" ORDER BY x.tx_tipCarPag";
                        rst=stm.executeQuery(strSQL);
                        while(rst.next()){
                            strRstTotTipCarPag=rst.getString("tx_tipCarPag");   
                            /* Calcular los valores de las columnas de Totales. */
                            for(int k=0; k<arlDatTotCarPag.size(); k++){
                                strArlTotTipCarPag=objUti.getStringValueAt(arlDatTotCarPag, k, INT_ARL_TOT_CAR_PAG_TIP);
                                intNumColTotCarPag=objUti.getIntValueAt(arlDatTotCarPag, k, INT_ARL_TOT_CAR_PAG_COL);
                                if(strRstTotTipCarPag.equals(strArlTotTipCarPag)){
                                    bgdTotValCarPag=new BigDecimal(rst.getObject("nd_valTotCarPag")==null?"0":(rst.getString("nd_valTotCarPag").equals("")?"0":rst.getString("nd_valTotCarPag")));                                
                                    objTblModDat.setValueAt(bgdTotValCarPag, i, intNumColTotCarPag);
                                    break;
                                }
                            }
                        }
                        rst.close();
                        rst=null;
                        stm.close();
                        stm=null;
                    }
                }
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
     * Función que permite cargar los valores y fechas de vencimiento estimados de pagos de los Pedidos seleccionados
     * @return true Si se realizó la operación
     * <BR> false Caso contrario
     */
    private boolean cargarFecVenPagEst(){
        boolean blnRes=true;
        String strCodEmp="";
        String strCodLoc="";
        String strCodTipDoc="";
        String strCodDoc="";
        int intAux=0;
        try{
            if(con!=null){
                if(chkMosFecVenEst.isSelected()){ 
                    for(int i=0; i<objTblModDat.getRowCountTrue(); i++){
                        stm=con.createStatement(); 

                        strCodEmp=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_EMP)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_EMP).toString());
                        strCodLoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_LOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_LOC).toString());
                        strCodTipDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString());
                        strCodDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_DOC).toString());

                        String strInsPed=objUti.parseString(objTblModDat.getValueAt(i,INT_TBL_DAT_INS_PED)); 
                        String strNomTbl=(strInsPed.equals("NOTPED")?"NotPedImp": (strInsPed.equals("PEDEMB")?"PedEmbImp":(strInsPed.equals("INIMPO")?"MovInv":""))); 
                        //String strNomTbl=(strInsPed.equals("NOTPED")?"NotPedImp": (strInsPed.equals("PEDEMB")?"PedEmbImp":"")); 

                        if(!strInsPed.equals("INIMPO")){
                            strSQL ="";
                            strSQL+="   SELECT a1.ne_numDiaCre1, a1.ne_numDiaCre2, a1.ne_numDiaCre3, a1.ne_numDiaCre4";     
                            strSQL+="        , a1.nd_porCrePag1, a1.nd_porCrePag2, a1.nd_porCrePag3, a1.nd_porCrePag4";     
                            strSQL+="        , a1.fe_venEst1,  a1.fe_venEst2, a1.fe_venEst3, a1.fe_venEst4" ;  
                            strSQL+="        , a1.nd_valPagVenEst1,  a1.nd_valPagVenEst2, a1.nd_valPagVenEst3, a1.nd_valPagVenEst4" ;  
                            strSQL+="   FROM tbm_cab"+strNomTbl+" AS a1"; 
                            strSQL+="   WHERE a1.co_emp=" + strCodEmp;
                            strSQL+="   AND a1.co_loc=" + strCodLoc;
                            strSQL+="   AND a1.co_tipDoc=" + strCodTipDoc;
                            strSQL+="   AND a1.co_doc=" + strCodDoc;
                            rst=stm.executeQuery(strSQL);
                            if(rst.next()){
                                intAux=0;
                                //Actualizar datos de Fechas Vencimiento: Estimadas
                                for(int j=intNumColIniFecVenEst; j<intNumColFinFecVenEst;j=(j+intNumColFecVenEst)) {
                                    objTblModDat.setValueAt(rst.getString("ne_numDiaCre"+(intAux+1)+""), i, (j+0)); 
                                    objTblModDat.setValueAt(rst.getString("fe_venEst"+(intAux+1)+""), i, (j+1));          //calcularFecPagFecVenEstSel(i, (j+0), intCalEditBefore);
                                    objTblModDat.setValueAt(rst.getString("nd_porCrePag"+(intAux+1)+""), i, (j+2));
                                    objTblModDat.setValueAt(rst.getString("nd_valPagVenEst"+(intAux+1)+""), i, (j+3));    //calcularValPagFecVenEstSel(i, (j+2), intCalEditBefore);
                                    intAux++;
                                }                                
                            } 
                            rst.close();
                            rst=null;
                            stm.close();
                            stm=null;
                        }
                        else{
                            strSQL ="";
                            strSQL+="   SELECT a2.ne_numDiaCre1, a2.ne_numDiaCre2, a2.ne_numDiaCre3, a2.ne_numDiaCre4";     
                            strSQL+="        , a2.nd_porCrePag1, a2.nd_porCrePag2, a2.nd_porCrePag3, a2.nd_porCrePag4";     
                            strSQL+="        , a2.fe_venEst1,  a2.fe_venEst2, a2.fe_venEst3, a2.fe_venEst4" ;  
                            strSQL+="        , a2.nd_valPagVenEst1,  a2.nd_valPagVenEst2, a2.nd_valPagVenEst3, a2.nd_valPagVenEst4" ;  
                            strSQL+="   FROM tbm_cabMovInv AS a1"; 
                            strSQL+="   INNER JOIN tbm_cabPedEmbImp AS a2 ON a1.co_empRelPedEmbImp=a2.co_emp AND a1.co_locRelPedEmbImp=a2.co_loc AND a1.co_tipDocRelPedEmbImp=a2.co_TipDoc AND a1.co_docRelPedEmbImp=a2.co_doc"; 
                            strSQL+="   WHERE a1.co_emp=" + strCodEmp;
                            strSQL+="   AND a1.co_loc=" + strCodLoc;
                            strSQL+="   AND a1.co_tipDoc=" + strCodTipDoc;
                            strSQL+="   AND a1.co_doc=" + strCodDoc;
                            strSQL+="   AND a2.st_Reg IN ('A') AND (a2.st_doc IN ('A') OR a2.st_doc IS NULL)";
                            rst=stm.executeQuery(strSQL);
                            if(rst.next()){
                                intAux=0;
                                //Actualizar datos de Fechas Vencimiento: Estimadas
                                for(int j=intNumColIniFecVenEst; j<intNumColFinFecVenEst;j=(j+intNumColFecVenEst)) {
                                    objTblModDat.setValueAt(rst.getString("ne_numDiaCre"+(intAux+1)+""), i, (j+0)); 
                                    objTblModDat.setValueAt(rst.getString("fe_venEst"+(intAux+1)+""), i, (j+1));          //calcularFecPagFecVenEstSel(i, (j+0), intCalEditBefore);
                                    objTblModDat.setValueAt(rst.getString("nd_porCrePag"+(intAux+1)+""), i, (j+2));
                                    objTblModDat.setValueAt(rst.getString("nd_valPagVenEst"+(intAux+1)+""), i, (j+3));    //calcularValPagFecVenEstSel(i, (j+2), intCalEditBefore);
                                    intAux++;
                                }                                
                            } 
                            rst.close();
                            rst=null;
                            stm.close();
                            stm=null;                            
                        }
                    }
                }
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
     * Función que permite cargar los valores y fechas de vencimiento estimados de pagos de los Pedidos seleccionados
     * @return true Si se realizó la operación
     * <BR> false Caso contrario
     */
    private boolean cargarFecVenPagRea(){
        boolean blnRes=true;
        String strCodEmp="";
        String strCodLoc="";
        String strCodTipDoc="";
        String strCodDoc="";
        int intAux=0;
        try{
            if(con!=null){
                if(chkMosFecVenRea.isSelected()){ 
                    for(int i=0; i<objTblModDat.getRowCountTrue(); i++){
                        stm=con.createStatement(); 

                        strCodEmp=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_EMP)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_EMP).toString());
                        strCodLoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_LOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_LOC).toString());
                        strCodTipDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString());
                        strCodDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_DOC).toString());

                        String strInsPed=objUti.parseString(objTblModDat.getValueAt(i,INT_TBL_DAT_INS_PED)); 
                        String strNomTbl=(strInsPed.equals("NOTPED")?"NotPedImp": (strInsPed.equals("PEDEMB")?"PedEmbImp":(strInsPed.equals("INIMPO")?"MovInv":""))); 

                        strSQL ="";
                        strSQL+="   SELECT a1.fe_venRea1, a1.nd_valPagVen1, a1.fe_venRea2, a1.nd_valPagVen2, a1.fe_venRea3, a1.nd_valPagVen3, a1.fe_venRea4, a1.nd_valPagVen4"; 
                        strSQL+="   FROM tbm_cab"+strNomTbl+" AS a1"; 
                        strSQL+="   WHERE a1.co_emp=" + strCodEmp;
                        strSQL+="   AND a1.co_loc=" + strCodLoc;
                        strSQL+="   AND a1.co_tipDoc=" + strCodTipDoc;
                        strSQL+="   AND a1.co_doc=" + strCodDoc;
                        //System.out.println("cargarFecVenPagRea: "+strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            intAux=0;
                            //Actualizar datos de Fechas Vencimiento: Reales
                            for(int j=intNumColIniFecVenRea; j<intNumColFinFecVenRea;j=(j+intNumColFecVenRea)){
                                objTblModDat.setValueAt(  rst.getString("fe_venRea"+(intAux+1)+""), i, (j+0)); 
                                objTblModDat.setValueAt( (rst.getObject("nd_valPagVen"+(intAux+1)+"")==null?"":rst.getString("nd_valPagVen"+(intAux+1)+"") ) , i, (j+1)); 
                                intAux++;
                            }                        
                        } 
                        rst.close();
                        rst=null;
                        stm.close();
                        stm=null;
                    }
                }
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
    
    private boolean setColumnasEditables(){
        Boolean blnRes=true;
        try{
            //Establecer columnas editables
            vecAux=new Vector();
            //vecAux.add("" + INT_TBL_DAT_FEC_COL_PED);
            vecAux.add("" + INT_TBL_DAT_FEC_EMB_EST);
            vecAux.add("" + INT_TBL_DAT_FEC_PUE);
            vecAux.add("" + INT_TBL_DAT_FEC_ARR_BOD);
            
            vecAux.add("" + INT_TBL_DAT_BUT);
            /* Fechas de vencimiento: Estimadas */
            for(int i=0; i<(intNumGrpAddFecVenEst); i++) {
                for(int j=0; j<(intNumColFecVenEst); j++) {
                    int intIndColGrp =(intNumColIniFecVenEst+j+(i*intNumColFecVenEst)); //Obtengo Indice de la Columna. 
                    if(j%2==0) { //Permitir edición de Columnas: Días y % Pago
                        vecAux.add(""+ intIndColGrp);
                    }
                }
            }
            /* Fechas de vencimiento: Reales */
            for(int i=0; i<(intNumGrpAddFecVenRea); i++) {
                for(int j=0; j<(intNumColFecVenRea); j++)  {
                    int intIndColGrp =(intNumColIniFecVenRea+j+(i*intNumColFecVenRea)); //Obtengo Indice de la Columna. 
                    vecAux.add(""+ intIndColGrp);
                }
            }                
            objTblModDat.setColumnasEditables(vecAux);
            vecAux=null;                
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    
    }
    
    private boolean deleteColumnas()
    {
        boolean blnRes=true;
        String strInsPed="";
        BigDecimal bgdValPen=new BigDecimal("0");
        try 
        {
            if(chkMosPedLleDir.isSelected() || (chkMosPedLleInd.isSelected()) ) {
                objTblModDat.setModoOperacion(objTblModDat.INT_TBL_INS);
                for(int i=(objTblModDat.getRowCountTrue()-1); i>=0; i--)
                {
                    strInsPed=objTblModDat.getValueAt(i, INT_TBL_DAT_INS_PED)==null?"":(objTblModDat.getValueAt(i, INT_TBL_DAT_INS_PED).toString().equals("")?"":objTblModDat.getValueAt(i, INT_TBL_DAT_INS_PED).toString());
                    if(strInsPed.equals("INIMPO")) { //Se eliminan las filas de los INIMPO que no tienen valores pendientes.
                        bgdValPen=new BigDecimal(objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_TOT_PEN)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_TOT_PEN).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_TOT_PEN).toString()));
                        if(bgdValPen.compareTo(new BigDecimal("0"))==0)  {
                            objTblModDat.removeRow(i);
                        }
                    }
                }
                objTblModDat.setModoOperacion(objTblModDat.INT_TBL_EDI);  
                objTblModDat.removeEmptyRows(); 
            }
        }
        catch (Exception e) 
        {
            blnRes=false;
        }
        return blnRes;
    }    
    
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        return true;
    }
    
    /**
     * Esta función permite actualizar los registros del detalle.
     * Indica que el pedido ha sido arribado.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean guardar()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if(chkRptPedTra.isSelected())
                {
                    if(actualizarDatosPedTra()) {   
                        blnRes=true; 
                        con.commit();
                    }
                    else
                        con.rollback(); 
                }
                else{
                    if(actualizarDatosPanCon()) {   
                        blnRes=true; 
                        con.commit();
                    }
                    else
                        con.rollback(); 
                }

                con.close();
                con=null; 
            }
            //Inicializo el estado de las filas.
            objTblModDat.initRowsState();
            objTblModDat.setDataModelChanged(false);
        }
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
      
    /**
     * Esta función permite actualizar las fechas de la nota de pedido
     * @return true: Si se pudo actualizar las fechas 
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDatosPedTra() {
        boolean blnRes=true;
        String strInsPed="", strNomTbl="";
        String strSQLUpdate="", strFecUltMod=""; 
        try{
            if (con!=null)
            {
                stm=con.createStatement(); 
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()); 
                
                //Armar la sentencia SQL.
                strSQLUpdate="";
                for (int intFil=0; intFil<objTblModDat.getRowCountTrue(); intFil++)
                {
                    if (objUti.parseString(objTblModDat.getValueAt(intFil,INT_TBL_DAT_LIN)).equals("M")) 
                    {
                        /* Obtener instancia del pedido a actualizar */
                        strInsPed=objUti.parseString(objTblModDat.getValueAt(intFil,INT_TBL_DAT_INS_PED));
                        strNomTbl=(strInsPed.equals("NOTPED")?"tbm_cabNotPedImp": (strInsPed.equals("PEDEMB")?"tbm_cabPedEmbImp":(strInsPed.equals("INIMPO")?"tbm_CabMovInv":"")));
                        
                        //Armar sentencia SQL
                        strSQL="";
                        strSQL+=" UPDATE "+strNomTbl+"";
                        strSQL+=" SET fe_ultmod='"+strFecUltMod+"'";
                        strSQL+="   , co_usrmod=" + objParSis.getCodigoUsuario() + "";
                        //strSQL+="   , fe_colPed=" + objUti.codificar( objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_COL_PED)==null?"":objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_COL_PED).toString(), 0);
                        
                        if(!strInsPed.equals("INIMPO"))
                        {
                            String strAuxFec=objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_EMB_EST)==null?"":objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_EMB_EST).toString();
                            if(!strAuxFec.equals("")){ /* Fecha Embarque del pedido: Solo podrá modificarse la fecha de embarque estimada, la real no puede ser editada. */
                               strSQL+="   , fe_emb=" + objUti.codificar(strAuxFec, 0);
                            }
                            strSQL+="   , fe_pue=" + objUti.codificar( objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_PUE)==null?"":objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_PUE).toString(), 0);
                            strSQL+="   , fe_arr=" + objUti.codificar( objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_ARR_BOD)==null?"":objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_ARR_BOD).toString(), 0);
                            
                            //Actualizar datos Grupo: Fechas Vencimiento Estimadas
                            for(int i=0; i<(intNumGrpAddFecVenEst); i++) { //Cantidad de grupos de fechas de vencimiento
                                for(int j=0; j<(intNumColFecVenEst); j++) { //Número de columnas dinámicas por grupo de fecha.        
                                    int intIndColGrp = (intNumColIniFecVenEst+j+(i*intNumColFecVenEst)); //Obtengo Indice de la Columna. 
                                    //Valores
                                    Object objDat =(objTblModDat.getValueAt(intFil,intIndColGrp)==null)?null:objTblModDat.getValueAt(intFil, intIndColGrp).toString().equals("")?null:objTblModDat.getValueAt(intFil, intIndColGrp).toString();   
                                    if(j==0) {
                                        strSQL+=" , ne_numDiaCre"+(i+1)+"=" + objDat;
                                    }
                                    else if(j==1) { //Fec.Ven.Est.
                                        strSQL+=" , fe_venEst"+(i+1)+"="+ objUti.codificar( objTblModDat.getValueAt(intFil, intIndColGrp)==null?"":objTblModDat.getValueAt(intFil, intIndColGrp).toString(), 0);  /* Fecha Vencimiento Estimada */
                                    }                             
                                    else if(j==2) {
                                        strSQL+=" , nd_porCrePag"+(i+1)+"=" + objDat;
                                    }
                                    else if(j==3) { //Val.Ven.Est.
                                        strSQL+=" , nd_valPagVenEst"+(i+1)+"=" + objDat;
                                    }                                       
                                }
                            }    
                        }  
                        
                        //Actualizar datos Grupo: Fechas Vencimiento Real
                        for(int i=0; i<(intNumGrpAddFecVenRea); i++) { //Cantidad de grupos de fechas de vencimiento
                            for(int j=0; j<(intNumColFecVenRea); j++) { //Número de columnas dinámicas por grupo de fecha.        
                                int intIndColGrp = (intNumColIniFecVenRea+j+(i*intNumColFecVenRea)); //Obtengo Indice de la Columna. 
                                Object objDat =(objTblModDat.getValueAt(intFil,intIndColGrp)==null)?null:objTblModDat.getValueAt(intFil, intIndColGrp).toString().equals("")?null:objTblModDat.getValueAt(intFil, intIndColGrp).toString();   
                                if(j==0){
                                    strSQL+=" , fe_venRea"+(i+1)+"="+ objUti.codificar( objTblModDat.getValueAt(intFil, intIndColGrp)==null?"":objTblModDat.getValueAt(intFil, intIndColGrp).toString(), 0); /* Fecha Vencimiento real */
                                }
                                else if(j==1) 
                                    strSQL+=" , nd_valPagVen"+(i+1)+"=" + objDat;
                            }
                        }                              
                        
                        strSQL+=" WHERE co_emp=" + objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_EMP);
                        strSQL+=" AND co_loc=" + objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_LOC);
                        strSQL+=" AND co_tipDoc=" + objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC);
                        strSQL+=" AND co_doc=" + objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_DOC);
                        strSQL+=";";
                        strSQLUpdate+=strSQL;
                    }
                }
                System.out.println("strSQLUpdate: "+strSQLUpdate);
                stm.executeUpdate(strSQLUpdate);
                stm.close();
                stm=null;   
                
                //Inserta Histórico
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_cabNotPedImp", "tbh_cabNotPedImp", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());                
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_cabPedEmbImp", "tbh_cabPedEmbImp", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());                
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
    
    
    /**
     * Esta función permite actualizar datos del Panel Control de Procesos.
     * @return true: Si se pudo actualizar.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDatosPanCon() 
    {
        boolean blnRes=false;
        try 
        {
            if (con!=null)
            {
                if(actualiza_ordenPedido()){   
                    if(actualiza_notaPedido()){
                        if(actualiza_pedidoEmbarcado()){
                            if(actualiza_ingresoImportacion()){
                                blnRes=true;
                            }
                        }
                    }
                }
            }

        }
        catch (Exception e)   { 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }       
    
    private boolean actualiza_ordenPedido(){
        boolean blnRes=false;
        String strSQLUpdate="", strFecUltMod="";
        try{
            if(con!=null){
                //Obtener fecha y hora del servidor
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());

                //Armar sentencia SQL: Inserción
                strSQLUpdate="";
                strSQL="";
                for (int i=0; i<objTblModDat.getRowCountTrue(); i++)
                {
                    String strLin=objUti.parseString(objTblModDat.getValueAt(i,INT_TBL_RPT_LIN));
                    if ( strLin.equals("I") || strLin.equals("M") ) 
                    {
                        //<editor-fold defaultstate="collapsed" desc="/* Registra Datos Orden de Importación.*/">
                        String strCodEmpPedImp=objUti.parseString(objTblModDat.getValueAt(i,INT_TBL_RPT_COD_EMP));
                        if(strCodEmpPedImp.equals(""))   //Inserción
                        {
                            String strNumPedImp=objUti.parseString(objTblModDat.getValueAt(i,INT_TBL_RPT_NUM_PED));
                            if(!validaExiNumPed(con, strNumPedImp, i)){  //Valida que no exista el número de pedido
                                stm=con.createStatement();
                                //Armar sentencia SQL: Inserción
                                strSQL ="";
                                strSQL+=" INSERT INTO tbm_cabPedImp (";
                                strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, ne_numdoc, tx_numped, fe_doc, tx_desmat, tx_desreq";
                                strSQL+=" , co_imp, tx_nomimp, co_prv, tx_nomprv, co_forpag, tx_forpag";
                                strSQL+=" , ne_tipimp, tx_destipimp, tx_desincter, tx_nomptoemb, nd_valped, tx_obs1, st_reg";
                                strSQL+=" , co_emprelcabnotpedpreimp, co_locrelcabnotpedpreimp, co_tipdocrelcabnotpedpreimp, co_docrelcabnotpedpreimp";
                                strSQL+=" , fe_proest, fe_segord, fe_recpro, fe_conpro, fe_notpedind";
                                strSQL+=" , fe_solantprv1, nd_valsolantprv1, fe_pagantprv1, tx_obsSolAntPrv1";
                                strSQL+=" , fe_solantprv2, nd_valsolantprv2, fe_pagantprv2, tx_obsSolAntPrv2";                            
                                strSQL+=" , fe_recfac, fe_solage, fe_asiage, tx_desPedCon, fe_embbok";  
                                strSQL+=" , fe_solsalprv, nd_valsolsalprv, fe_pagsalprv, tx_obsSolSalPrv";
                                strSQL+=" , fe_pagprvven1, nd_valpagprvven1, fe_pagprvven2, nd_valpagprvven2";
                                strSQL+=" , fe_pagprvven3, nd_valpagprvven3, nd_valtotpagext";
                                strSQL+=" , fe_ing, co_usring, tx_coming, fe_ultmod, co_usrmod, tx_commod";                     
                                strSQL+=" )";    

                                strSQL+=" SELECT ";
                                strSQL+="   "+objParSis.getCodigoEmpresa()+ " AS co_emp";              //co_emp
                                strSQL+=" , "+objParSis.getCodigoLocal()+ " AS co_loc";                //co_loc
                                strSQL+=" , "+INT_COD_TIP_DOC_PED_IMP+ " AS co_tipdoc";                //co_tipdoc

                                strSQL+=" , (SELECT (CASE WHEN MAX(co_doc)+1 IS NULL THEN 1 ELSE MAX(co_doc)+1 END) AS co_doc";
                                strSQL+="    FROM tbm_cabPedImp WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+"";
                                strSQL+="    AND co_tipDoc="+INT_COD_TIP_DOC_PED_IMP+" ) AS co_doc";   //co_doc

                                strSQL+=" , (SELECT (CASE WHEN MAX(co_doc)+1 IS NULL THEN 1 ELSE MAX(co_doc)+1 END) AS ne_numdoc";
                                strSQL+="    FROM tbm_cabPedImp WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+"";
                                strSQL+="    AND co_tipDoc="+INT_COD_TIP_DOC_PED_IMP+" ) AS ne_numdoc";  //ne_numdoc                            

                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_NUM_PED)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_NUM_PED).toString())+" AS tx_numPed";    //tx_numPed

                                strSQL+=" , CAST('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaBaseDatos()) + "' AS DATE) AS fe_doc"; //fe_doc                            

                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_NOM_RUB_MAT)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_NOM_RUB_MAT).toString())+" AS tx_desmat";                               
                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_DES_REQ_ORD)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_DES_REQ_ORD).toString())+" AS tx_desreq";

                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_COD_IMP)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_COD_IMP).toString(), 2)+" AS co_imp";   
                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_NOM_IMP)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_NOM_IMP).toString())+" AS tx_nomimp";   

                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_COD_PRV)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_COD_PRV).toString(), 2)+" AS co_prv";   
                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_NOM_PRV)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_NOM_PRV).toString())+" AS tx_nomprv";      

                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_COD_FOR_PAG)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_COD_FOR_PAG).toString(), 2)+" AS co_forpag";   
                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_DES_FOR_PAG)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_DES_FOR_PAG).toString())+" AS tx_forpag";                               

                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_COD_DIR_IND)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_COD_DIR_IND).toString(), 2)+" AS ne_tipimp";   
                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_DES_DIR_IND)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_DES_DIR_IND).toString())+" AS tx_destipimp";                               

                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_DES_TIP_PED)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_DES_TIP_PED).toString())+" AS tx_desincter";
                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_DES_PTO_EMB)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_DES_PTO_EMB).toString())+" AS tx_nomptoemb";

                                strSQL+=" , "+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PED)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PED).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PED).toString()), objParSis.getDecimalesBaseDatos())+" AS nd_valped"; 

                                strSQL+="  ,"+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_TXT_OBS_COM)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_TXT_OBS_COM).toString())+" AS tx_obs1";                                 

                                strSQL+=" , 'A' AS st_Reg";

                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_COD_EMP_NOTPEDPRE)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_COD_EMP_NOTPEDPRE).toString(),2)+" AS co_emprelcabnotpedpreimp";
                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_COD_LOC_NOTPEDPRE)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_COD_LOC_NOTPEDPRE).toString(),2)+" AS co_locrelcabnotpedpreimp";
                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_COD_TIP_DOC_NOTPEDPRE)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_COD_TIP_DOC_NOTPEDPRE).toString(),2)+" AS co_tipdocrelcabnotpedpreimp";
                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_COD_DOC_NOTPEDPRE)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_COD_DOC_NOTPEDPRE).toString(),2)+" AS co_docrelcabnotpedpreimp";                            

                                strSQL+=" , "+ objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PRO_EST)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PRO_EST).toString() )+ " AS fe_proest";                            
                                strSQL+=" , "+ objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SEG_ORD)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SEG_ORD).toString() )+ " AS fe_segord";

                                strSQL+=" , "+ objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_REC_PRO)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_REC_PRO).toString() )+ " AS fe_recpro";
                                strSQL+=" , "+ objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_CON_PRO)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_CON_PRO).toString() )+ " AS fe_conpro";
                                strSQL+=" , "+ objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_NOT_PED_IND)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_NOT_PED_IND).toString() )+ " AS fe_notpedind";

                                strSQL+=" , "+ objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SOL_ANT_PRV_1)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SOL_ANT_PRV_1).toString() )+ " AS fe_solantprv1";
                                strSQL+=" , "+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_SOL_ANT_PRV_1)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_SOL_ANT_PRV_1).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_SOL_ANT_PRV_1).toString()), objParSis.getDecimalesBaseDatos())+" AS nd_valsolantprv1"; 
                                strSQL+=" , "+ objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_ANT_PRV_1)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_ANT_PRV_1).toString() )+ " AS fe_pagantprv1";
                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_TXT_OBS_ANT_PRV_1)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_TXT_OBS_ANT_PRV_1).toString())+" AS tx_obsSolAntPrv1";

                                strSQL+=" , "+ objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SOL_ANT_PRV_2)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SOL_ANT_PRV_2).toString() )+ " AS fe_solantprv2";
                                strSQL+=" , "+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_SOL_ANT_PRV_2)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_SOL_ANT_PRV_2).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_SOL_ANT_PRV_2).toString()), objParSis.getDecimalesBaseDatos())+" AS nd_valsolantprv2"; 
                                strSQL+=" , "+ objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_ANT_PRV_2)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_ANT_PRV_2).toString() )+ " AS fe_pagantprv2";
                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_TXT_OBS_ANT_PRV_2)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_TXT_OBS_ANT_PRV_2).toString())+" AS tx_obsSolAntPrv2";

                                strSQL+=" , "+ objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_REC_FAC_PRV)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_REC_FAC_PRV).toString() )+ " AS fe_recfac";
                                strSQL+=" , "+ objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SOL_AGE)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SOL_AGE).toString() )+ " AS fe_solage";
                                strSQL+=" , "+ objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_ASI_AGE)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_ASI_AGE).toString() )+ " AS fe_asiage";                            
                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_DES_PED_CON)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_DES_PED_CON).toString())+" AS tx_desPedCon";
                                strSQL+=" , "+ objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_EMB_BOK)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_EMB_BOK).toString() )+ " AS fe_embbok";

                                strSQL+=" , "+ objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SOL_SAL_PRV)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SOL_SAL_PRV).toString() )+ " AS fe_solsalprv";
                                strSQL+=" , "+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_SOL_SAL_PRV)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_SOL_SAL_PRV).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_SOL_SAL_PRV).toString()), objParSis.getDecimalesBaseDatos())+" AS nd_valsolsalprv";                                                         
                                strSQL+=" , "+ objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_SAL_PRV)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_SAL_PRV).toString() )+ " AS fe_pagsalprv";
                                strSQL+=" , "+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_TXT_OBS_SAL_PRV)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_TXT_OBS_SAL_PRV).toString())+" AS tx_obsSolSalPrv";

                                strSQL+=" , "+ objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_VEN_1)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_VEN_1).toString() )+ " AS fe_pagprvven1";
                                strSQL+=" , "+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_VEN_1)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_VEN_1).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_VEN_1).toString()), objParSis.getDecimalesBaseDatos())+" AS nd_valpagprvven1";                                                                                                                 

                                strSQL+=" , "+ objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_VEN_2)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_VEN_2).toString() )+ " AS fe_pagprvven2";
                                strSQL+=" , "+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_VEN_2)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_VEN_2).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_VEN_2).toString()), objParSis.getDecimalesBaseDatos())+" AS nd_valpagprvven2";                                                                                                                 

                                strSQL+=" , "+ objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_VEN_3)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_VEN_3).toString() )+ " AS fe_pagprvven3";
                                strSQL+=" , "+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_VEN_3)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_VEN_3).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_VEN_3).toString()), objParSis.getDecimalesBaseDatos())+" AS nd_valpagprvven3";                                                                                                                 

                                strSQL+=" , "+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_TOT)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_TOT).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_TOT).toString()), objParSis.getDecimalesBaseDatos())+" AS nd_valtotpagext";                                                                                                                 

                                strSQL+=" , CAST('"+ strFecUltMod + "' AS TIMESTAMP) AS fe_ing";//fe_ing
                                strSQL+=" , "+ objParSis.getCodigoUsuario() + " AS co_usring"; //co_usring
                                strSQL+=" , "+ objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + " AS tx_coming"; //tx_coming
                                strSQL+=" , CAST('"+ strFecUltMod + "' AS TIMESTAMP) AS fe_ultMod";//fe_ultMod      
                                strSQL+=" , "+ objParSis.getCodigoUsuario() + " AS co_usrmod"; //co_usrmod   
                                strSQL+=" , "+ objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + " AS tx_commod"; //tx_commod
                                strSQL+=" ;";
                                System.out.println("inserta_Pedido: "+strSQL); 
                                stm.executeUpdate(strSQL);
                                stm.close();
                                stm=null;
                                blnRes=true;
                            }
                            else{
                                mostrarMsgInf("<HTML>El número de Pedido "+strNumPedImp+" ya existe.<BR>Indique otro número de pedido y vuelva a intentarlo.</HTML>");
                            }
                        }
                        else{   //Modificación
                            stm=con.createStatement();
                            //Armar sentencia SQL: Actualización
                            strSQL ="";
                            strSQL+=" UPDATE tbm_cabPedImp";
                            strSQL+=" SET fe_ultmod='"+strFecUltMod+"'";
                            strSQL+="   , co_usrmod=" + objParSis.getCodigoUsuario() + "";  
                            strSQL+="   , tx_commod="+ objUti.codificar(objParSis.getNombreComputadoraConDirIP());
                            
                            strSQL+="   , tx_numPed=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_NUM_PED)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_NUM_PED).toString());  //tx_numPed                        
                            strSQL+="   , fe_doc=" + objUti.codificar(objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaBaseDatos())) + ""; //fe_doc                            
                            
                            strSQL+="   , tx_desmat="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_NOM_RUB_MAT)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_NOM_RUB_MAT).toString());
                            strSQL+="   , tx_desreq="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_DES_REQ_ORD)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_DES_REQ_ORD).toString());                             
                           
                            strSQL+="   , co_imp="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_COD_IMP)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_COD_IMP).toString(),2); 
                            strSQL+="   , tx_nomimp="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_NOM_IMP)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_NOM_IMP).toString());
                            
                            strSQL+="   , co_prv="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_COD_PRV)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_COD_PRV).toString(),2);
                            strSQL+="   , tx_nomprv="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_NOM_PRV)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_NOM_PRV).toString());
                            
                            strSQL+="   , co_forpag="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_COD_FOR_PAG)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_COD_FOR_PAG).toString(),2);
                            strSQL+="   , tx_forpag="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_DES_FOR_PAG)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_DES_FOR_PAG).toString());                               
                            
                            strSQL+="   , ne_tipimp="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_COD_DIR_IND)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_COD_DIR_IND).toString(),2);
                            strSQL+="   , tx_destipimp="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_DES_DIR_IND)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_DES_DIR_IND).toString());
                            
                            strSQL+="   , tx_desincter="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_DES_TIP_PED)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_DES_TIP_PED).toString());
                            strSQL+="   , tx_nomptoemb="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_DES_PTO_EMB)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_DES_PTO_EMB).toString());
                            
                            strSQL+="   , nd_valped="+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PED)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PED).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PED).toString()), objParSis.getDecimalesBaseDatos());
                            
                            strSQL+="   , co_emprelcabnotpedpreimp="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_COD_EMP_NOTPEDPRE)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_COD_EMP_NOTPEDPRE).toString(),2);
                            strSQL+="   , co_locrelcabnotpedpreimp="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_COD_LOC_NOTPEDPRE)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_COD_LOC_NOTPEDPRE).toString(),2);
                            strSQL+="   , co_tipdocrelcabnotpedpreimp="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_COD_TIP_DOC_NOTPEDPRE)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_COD_TIP_DOC_NOTPEDPRE).toString(),2);
                            strSQL+="   , co_docrelcabnotpedpreimp="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_COD_DOC_NOTPEDPRE)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_COD_DOC_NOTPEDPRE).toString(),2);
                            
                            strSQL+="   , fe_proest=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PRO_EST)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PRO_EST).toString() );  
                            strSQL+="   , fe_segord=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SEG_ORD)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SEG_ORD).toString() );
                            strSQL+="   , fe_recpro=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_REC_PRO)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_REC_PRO).toString() );
                            strSQL+="   , fe_conpro=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_CON_PRO)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_CON_PRO).toString() );
                            strSQL+="   , fe_notpedind=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_NOT_PED_IND)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_NOT_PED_IND).toString() );
                            
                            strSQL+="   , fe_solantprv1=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SOL_ANT_PRV_1)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SOL_ANT_PRV_1).toString() );
                            strSQL+="   , nd_valsolantprv1="+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_SOL_ANT_PRV_1)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_SOL_ANT_PRV_1).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_SOL_ANT_PRV_1).toString()), objParSis.getDecimalesBaseDatos()); 
                            strSQL+="   , fe_pagantprv1=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_ANT_PRV_1)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_ANT_PRV_1).toString() );
                            strSQL+="   , tx_obsSolAntPrv1="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_TXT_OBS_ANT_PRV_1)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_TXT_OBS_ANT_PRV_1).toString());
                            
                            strSQL+="   , fe_solantprv2=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SOL_ANT_PRV_2)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SOL_ANT_PRV_2).toString() );
                            strSQL+="   , nd_valsolantprv2="+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_SOL_ANT_PRV_2)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_SOL_ANT_PRV_2).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_SOL_ANT_PRV_2).toString()), objParSis.getDecimalesBaseDatos()); 
                            strSQL+="   , fe_pagantprv2=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_ANT_PRV_2)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_ANT_PRV_2).toString() );
                            strSQL+="   , tx_obsSolAntPrv2="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_TXT_OBS_ANT_PRV_2)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_TXT_OBS_ANT_PRV_2).toString());
                            
                            strSQL+="   , fe_recfac=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_REC_FAC_PRV)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_REC_FAC_PRV).toString() );
                            strSQL+="   , fe_solage=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SOL_AGE)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SOL_AGE).toString() );
                            strSQL+="   , fe_asiage=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_ASI_AGE)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_ASI_AGE).toString() );                            
                            strSQL+="   , tx_desPedCon="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_DES_PED_CON)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_DES_PED_CON).toString());                            
                            strSQL+="   , fe_embbok=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_EMB_BOK)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_EMB_BOK).toString() );
                            
                            strSQL+="   , fe_solsalprv=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SOL_SAL_PRV)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_SOL_SAL_PRV).toString() );
                            strSQL+="   , nd_valsolsalprv="+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_SOL_SAL_PRV)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_SOL_SAL_PRV).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_SOL_SAL_PRV).toString()), objParSis.getDecimalesBaseDatos()); 
                            strSQL+="   , fe_pagsalprv=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_SAL_PRV)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_SAL_PRV).toString() );
                            strSQL+="   , tx_obsSolSalPrv="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_TXT_OBS_SAL_PRV)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_TXT_OBS_SAL_PRV).toString());
                            
                            strSQL+="   , fe_pagprvven1=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_VEN_1)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_VEN_1).toString() );
                            strSQL+="   , nd_valpagprvven1="+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_VEN_1)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_VEN_1).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_VEN_1).toString()), objParSis.getDecimalesBaseDatos()); 
                            
                            strSQL+="   , fe_pagprvven2=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_VEN_2)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_VEN_2).toString() );
                            strSQL+="   , nd_valpagprvven2="+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_VEN_2)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_VEN_2).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_VEN_2).toString()), objParSis.getDecimalesBaseDatos()); 
                            
                            strSQL+="   , fe_pagprvven3=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_VEN_3)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_VEN_3).toString() );
                            strSQL+="   , nd_valpagprvven3="+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_VEN_3)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_VEN_3).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_VEN_3).toString()), objParSis.getDecimalesBaseDatos()); 
                            
                            strSQL+="   , nd_valtotpagext="+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_TOT)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_TOT).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_PAG_TOT).toString()), objParSis.getDecimalesBaseDatos());  
                            
                            strSQL+="   , tx_obs1="+ objUti.codificar(objTblModDat.getValueAt(i, INT_TBL_RPT_TXT_OBS_COM)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_TXT_OBS_COM).toString());
                            
                            
                            strSQL+=" WHERE co_emp=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_EMP); 
                            strSQL+=" AND co_loc=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_LOC);
                            strSQL+=" AND co_tipDoc=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_TIP_DOC);
                            strSQL+=" AND co_doc=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_DOC);
                            strSQL+=";";  
                            System.out.println("actualiza_Pedido: "+strSQL);
                            stm.executeUpdate(strSQL);
                            stm.close();
                            stm=null;  
                            blnRes=true;
                        }              
                    }
                }                
               
                //Insertar Histórico
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_cabPedImp", "tbh_cabPedImp", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());                
                //blnRes=true;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    /**
     * Esta función permite actualiza los datos de la nota de pedido
     * @return true: Si se pudo actualizar los datos
     * <BR>false: En el caso contrario.
     */
    private boolean actualiza_notaPedido() {
        boolean blnRes=true;
        String strSQLUpdate="", strFecUltMod; 
        try{
            if (con!=null)
            {
                stm=con.createStatement(); 
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()); 
                
                //Armar la sentencia SQL.
                strSQLUpdate="";
                strSQL="";
                for (int i=0; i<objTblModDat.getRowCountTrue(); i++)
                {
                    String strLin=objUti.parseString(objTblModDat.getValueAt(i,INT_TBL_RPT_LIN));
                    if ( strLin.equals("I") || strLin.equals("M") ) 
                    {
                        //<editor-fold defaultstate="collapsed" desc="/* Actualiza Datos Nota Pedido.*/">
                        String strCodEmpNotPed=objUti.parseString(objTblModDat.getValueAt(i,INT_TBL_RPT_COD_EMP_NOTPED));
                        if(!strCodEmpNotPed.equals(""))   //Si existe Nota de Pedido.
                        {                    
                            //Armar sentencia SQL
                            strSQL ="";
                            strSQL+=" UPDATE tbm_cabNotPedImp";
                            strSQL+=" SET fe_ultmod='"+strFecUltMod+"'";
                            strSQL+="   , co_usrmod=" + objParSis.getCodigoUsuario() + "";  
                            strSQL+="   , fe_emb=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_EMB_EST)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_EMB_EST).toString() );  
                            strSQL+="   , fe_pue=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PUE)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PUE).toString() );  
                            strSQL+="   , fe_arr=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_ARR_BOD)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_ARR_BOD).toString() );  
                            strSQL+=" WHERE co_emp=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_EMP_NOTPED);
                            strSQL+=" AND co_loc=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_LOC_NOTPED);
                            strSQL+=" AND co_tipDoc=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_TIP_DOC_NOTPED);
                            strSQL+=" AND co_doc=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_DOC_NOTPED);
                            strSQL+=";";    
                            strSQLUpdate+=strSQL;
                        }
                        //</editor-fold>
                    }
                }
                System.out.println("actualiza_notaPedido: "+strSQLUpdate);
                stm.executeUpdate(strSQLUpdate);
                stm.close();
                stm=null;   
                
                //Inserta Histórico
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_cabNotPedImp", "tbh_cabNotPedImp", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());                
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
    
    /**
     * Esta función permite actualiza los datos del pedido embarcado.
     * @return true: Si se pudo actualizar los datos 
     * <BR>false: En el caso contrario.
     */
    private boolean actualiza_pedidoEmbarcado() {
        boolean blnRes=true;
        String strSQLUpdate="", strFecUltMod; 
        try{
            if (con!=null)
            {
                stm=con.createStatement(); 
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()); 
                
                //Armar la sentencia SQL.
                strSQLUpdate="";
                strSQL="";
                for (int i=0; i<objTblModDat.getRowCountTrue(); i++)
                {
                    String strLin=objUti.parseString(objTblModDat.getValueAt(i,INT_TBL_RPT_LIN));
                    if ( strLin.equals("I") || strLin.equals("M") ) 
                    {
                        //<editor-fold defaultstate="collapsed" desc="/* Actualiza Datos Pedido Embarcado.*/">
                        String strCodEmpPedEmb=objUti.parseString(objTblModDat.getValueAt(i,INT_TBL_RPT_COD_EMP_PEDEMB));
                        if(!strCodEmpPedEmb.equals(""))   //Si existe Pedido Embarcado
                        {                    
                            //Armar sentencia SQL: Actualiza fecha de embarque solo cuando el pedido no ha sido embarcado.
                            strSQL ="";
                            strSQL+=" UPDATE tbm_cabPedEmbImp";
                            strSQL+=" SET fe_ultmod='"+strFecUltMod+"'";
                            strSQL+="   , co_usrmod=" + objParSis.getCodigoUsuario() + "";  
                            strSQL+="   , fe_emb=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_EMB_EST)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_EMB_EST).toString() );  
                            strSQL+=" WHERE co_emp=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_EMP_PEDEMB);
                            strSQL+=" AND co_loc=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_LOC_PEDEMB);
                            strSQL+=" AND co_tipDoc=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_TIP_DOC_PEDEMB);
                            strSQL+=" AND co_doc=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_DOC_PEDEMB);
                            strSQL+=" AND st_emb IN ('N')";
                            strSQL+=";";                                
                            strSQLUpdate+=strSQL;
                            
                            //Armar sentencia SQL: Actualiza datos de embarque
                            strSQL ="";
                            strSQL+=" UPDATE tbm_cabPedEmbImp";
                            strSQL+=" SET fe_ultmod='"+strFecUltMod+"'";
                            strSQL+="   , co_usrmod=" + objParSis.getCodigoUsuario() + "";  
                            //strSQL+="   , fe_pue=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PUE)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PUE).toString() );  
                            //strSQL+="   , fe_arr=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_ARR_BOD)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_ARR_BOD).toString() );                              
                            strSQL+="   , fe_venEst1=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_BAS_PAG_1)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_BAS_PAG_1).toString() );                             
                            strSQL+="   , fe_venEst2=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_BAS_PAG_2)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_BAS_PAG_2).toString() );              
                            strSQL+="   , fe_venEst3=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_BAS_PAG_3)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_BAS_PAG_3).toString() );                               
                            strSQL+="   , fe_venEst4=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_BAS_PAG_4)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_BAS_PAG_4).toString() );                               
                            strSQL+="   , fe_venRea1=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_REA_1)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_REA_1).toString() );                               
                            strSQL+="   , fe_venRea2=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_REA_2)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_REA_2).toString() );                              
                            strSQL+="   , fe_venRea3=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_REA_3)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_REA_3).toString() );                               
                            strSQL+="   , fe_venRea4=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_REA_4)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_PAG_REA_4).toString() );                              
                            strSQL+=" WHERE co_emp=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_EMP_PEDEMB);
                            strSQL+=" AND co_loc=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_LOC_PEDEMB);
                            strSQL+=" AND co_tipDoc=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_TIP_DOC_PEDEMB);
                            strSQL+=" AND co_doc=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_DOC_PEDEMB);
                            strSQL+=";";    
                            strSQLUpdate+=strSQL;
                        }
                        //</editor-fold>
                    }
                }
                System.out.println("actualiza_pedidoEmbarcado: "+strSQLUpdate);
                stm.executeUpdate(strSQLUpdate);
                stm.close();
                stm=null;   
                
                //Inserta Histórico
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_cabPedEmbImp", "tbh_cabPedEmbImp", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());                
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
    
    /**
     * Esta función permite actualiza los datos del ingreso por importación
     * @return true: Si se pudo actualizar los datos 
     * <BR>false: En el caso contrario.
     */
    private boolean actualiza_ingresoImportacion() {
        boolean blnRes=true;
        String strSQLUpdate="", strFecUltMod; 
        try{
            if (con!=null)
            {
                stm=con.createStatement(); 
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()); 
                
                //Armar la sentencia SQL.
                strSQLUpdate="";
                strSQL="";
                for (int i=0; i<objTblModDat.getRowCountTrue(); i++)
                {
                    String strLin=objUti.parseString(objTblModDat.getValueAt(i,INT_TBL_RPT_LIN));
                    if ( strLin.equals("I") || strLin.equals("M") ) 
                    {
                        //<editor-fold defaultstate="collapsed" desc="/* Actualiza Datos Ingreso por Importación.*/">
                        String strCodEmpIngImp=objUti.parseString(objTblModDat.getValueAt(i,INT_TBL_RPT_COD_EMP_INIMPO));
                        if(!strCodEmpIngImp.equals(""))   //Si existe Ingreso por Importación
                        {   
                            //Armar sentencia SQL: Actualiza datos del ingreso por importación
                            strSQL ="";
                            strSQL+=" UPDATE tbm_cabMovInv";
                            strSQL+=" SET fe_ultmod='"+strFecUltMod+"'";
                            strSQL+="   , co_usrmod=" + objParSis.getCodigoUsuario() + "";                             
                            strSQL+="   , fe_venRea1=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_EST_VEN_1)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_EST_VEN_1).toString() );                              
                            strSQL+="   , nd_ValPagVen1="+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_EST_VEN_1)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_EST_VEN_1).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_EST_VEN_1).toString()), objParSis.getDecimalesBaseDatos()); 
                            strSQL+="   , fe_venRea2=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_EST_VEN_2)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_EST_VEN_2).toString() );                              
                            strSQL+="   , nd_ValPagVen2="+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_EST_VEN_2)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_EST_VEN_2).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_EST_VEN_2).toString()), objParSis.getDecimalesBaseDatos()); 
                            strSQL+="   , fe_venRea3=" + objUti.codificar( objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_EST_VEN_3)==null?"":objTblModDat.getValueAt(i, INT_TBL_RPT_FEC_EST_VEN_3).toString() );                              
                            strSQL+="   , nd_ValPagVen3="+ objUti.redondearBigDecimal(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_EST_VEN_3)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_EST_VEN_3).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_RPT_VAL_EST_VEN_3).toString()), objParSis.getDecimalesBaseDatos()); 
                            strSQL+=" WHERE co_emp=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_EMP_INIMPO);
                            strSQL+=" AND co_loc=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_LOC_INIMPO);
                            strSQL+=" AND co_tipDoc=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_TIP_DOC_INIMPO);
                            strSQL+=" AND co_doc=" + objTblModDat.getValueAt(i, INT_TBL_RPT_COD_DOC_INIMPO);
                            strSQL+=";";    
                            strSQLUpdate+=strSQL;
                        }
                        //</editor-fold>
                    }
                }
                System.out.println("actualiza_ingresoImportacion: "+strSQLUpdate);
                stm.executeUpdate(strSQLUpdate);
                stm.close();
                stm=null;   
                
                //Inserta Histórico
                //objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_cabMovInv", "tbh_cabMovInv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());                
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
    
    private int getNumFecha(String strFecha, int intTipDat){
        int intNumFec=-1;
        Connection conLoc;
        ResultSet rstLoc;
        Statement stmLoc;
        String strSqlAux;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSqlAux ="";
                strSqlAux+=" SELECT (CASE WHEN (EXTRACT(YEAR from CAST('"+strFecha+"'AS DATE))) IS NULL THEN -1 ELSE ( EXTRACT(YEAR from CAST('"+strFecha+"'AS DATE))) END)  AS ne_numAnio";
                strSqlAux+="      , (CASE WHEN (EXTRACT(MONTH from CAST('"+strFecha+"'AS DATE))) IS NULL THEN -1 ELSE ( EXTRACT(MONTH from CAST('"+strFecha+"'AS DATE))) END) AS ne_numMes";  
                strSqlAux+="      , (CASE WHEN (EXTRACT(DAY from CAST('"+strFecha+"'AS DATE))) IS NULL THEN -1 ELSE ( EXTRACT(DAY from CAST('"+strFecha+"'AS DATE))) END) AS ne_numDia";
                rstLoc=stmLoc.executeQuery(strSqlAux);
                if(rstLoc.next()){
                    switch (intTipDat){
                        case 1: intNumFec=rstLoc.getInt("ne_numAnio"); break;
                        case 2: intNumFec=rstLoc.getInt("ne_numMes");  break;
                        case 3: intNumFec=rstLoc.getInt("ne_numDia");  break;
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
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intNumFec;
    }
  
    /***
     * Calcula fechas y valores dentro del grupo "Fechas de Vencimiento Proyectadas"
     */
    private void calcularGrupoColumnsFecVenEst(){ 
        try {
            for(int intFil=0; intFil<objTblModDat.getRowCountTrue();intFil++){
                for(int i=0; i<(intNumGrpAddFecVenEst); i++) { //Cantidad de grupos de fechas de vencimiento
                    for(int j=0; j<(intNumColFecVenEst); j++) { //Número de columnas dinámicas por grupo de fecha.    
                        int intIndColGrp = (intNumColIniFecVenEst+j+(i*intNumColFecVenEst)); //Obtengo Indice de la Columna. 
                        if(j==0) /* Calcula Fecha Vencimiento*/
                            calcularFecPagFecVenEstSel(intFil, intIndColGrp, intCalEditBefore);
                        else if(j==2) /* Calcula Valor Vencimiento*/
                            calcularValPagFecVenEstSel(intFil, intIndColGrp, intCalEditBefore);
                    }
                }
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }  
    
    /***
     * Calcula fechas y valores dentro del grupo "Fechas de Vencimiento Proyectadas"
     * Este calculo solo se realiza por fila, en todas las columnas.
     * <BR>Ejemplo: Se utiliza para recalcular fechas, despues de editar la fecha de embarque en el JTable.
     */
    private void calcularGrupoColumnsFecVenEstPorFila(int intFilSel){ 
        try {
            for(int i=0; i<(intNumGrpAddFecVenEst); i++) { //Cantidad de grupos de fechas de vencimiento
                for(int j=0; j<(intNumColFecVenEst); j++) { //Número de columnas dinámicas por grupo de fecha.    
                    int intIndColGrp = (intNumColIniFecVenEst+j+(i*intNumColFecVenEst)); //Obtengo Indice de la Columna. 
                    if(j==0) /* Calcula Fecha Vencimiento*/
                        calcularFecPagFecVenEstSel(intFilSel, intIndColGrp, intCalEditAfter);
                    else if(j==2) /* Calcula Valor Vencimiento*/
                        calcularValPagFecVenEstSel(intFilSel, intIndColGrp, intCalEditAfter);
                }
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }      
    
    /**
     * Función que calcula las fechas de vencimiento Estimadas: Por Fila y Columna Seleccionada
     * @param intFil
     * @param intCol
     * @param intTipEdi 0=Después de editar; 1=Antes de Editar
     */
    private void calcularFecPagFecVenEstSel(int intFil, int intCol, int intTipEdi) { 
        int intNumDiaCre=-999999;
        String strFecEmb="", strFecEmbEst="", strFecEmbRea="";
        LocalDate ldtFecVenEst;        
        try {
            intNumDiaCre=Integer.parseInt(objTblModDat.getValueAt(intFil, intCol)==null?"-999999":(objTblModDat.getValueAt(intFil, intCol).toString().equals("")?"-999999":objTblModDat.getValueAt(intFil, intCol).toString()));
            
            /* Fecha Embarque del pedido: Solo podrá modificarse la fecha de embarque estimada, la real no puede ser editada. */
            strFecEmb="";
            strFecEmbRea=(objTblModDat.getValueAt(intFil,INT_TBL_DAT_FEC_EMB_REA)==null)?"":objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_EMB_REA).toString();
            objDtePckEdiFecEmb.setText(strFecEmbRea); //Se asigna la fecha de embarque al datePicker
            if(objDtePckEdiFecEmb.isFecha()) { //Si existe fecha de Embarque real, se asigna la fecha de embarque real.
                strFecEmb=objUti.formatearFecha(objDtePckEdiFecEmb.getText(),"yyyy-MM-dd",objParSis.getFormatoFechaBaseDatos());
            }
            else{ //Si no existe fecha de Embarque real, se asigna la fecha de embarque estimada.
                strFecEmbEst=(objTblModDat.getValueAt(intFil,INT_TBL_DAT_FEC_EMB_EST)==null)?"":objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_EMB_EST).toString();
                objDtePckEdiFecEmb.setText(strFecEmbEst); //Se asigna la fecha de embarque al datePicker
                if(objDtePckEdiFecEmb.isFecha()) { 
                    strFecEmb=objUti.formatearFecha(objDtePckEdiFecEmb.getText(),"yyyy-MM-dd",objParSis.getFormatoFechaBaseDatos());
                }
            }
            
            if(intNumDiaCre==-999999){
                objTblModDat.setValueAt("", intFil, (intCol+1)); 
            }
            else{
                LocalDate datFecEmb = LocalDate.of(getNumFecha(strFecEmb, 1), getNumFecha(strFecEmb, 2), getNumFecha(strFecEmb, 3)); 
                ldtFecVenEst = datFecEmb.plus(Period.ofDays(intNumDiaCre));
                objTblModDat.setValueAt(""+ldtFecVenEst, intFil, (intCol+1)); 
                if(intTipEdi==intCalEditBefore){
                    if(intNumDiaCre==0){
                        objTblModDat.setValueAt("", intFil, (intCol+1)); 
                    }
                }            
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /**
     * Función que calcula los valores de vencimiento Estimadas: Por Fila y Columna Seleccionada
     * @param intFil
     * @param intCol
     * @param intTipEdi 0=Después de editar; 1=Antes de Editar
     */    
    private void calcularValPagFecVenEstSel(int intFil, int intCol, int intTipEdi){ 
        BigDecimal bgdAuxPor = new BigDecimal ("-1");
        BigDecimal bgdPorCre = BigDecimal.ZERO;
        BigDecimal bgdValFac = BigDecimal.ZERO;
        BigDecimal bgdValPag = BigDecimal.ZERO;
        try {
            bgdAuxPor = new BigDecimal ( objTblModDat.getValueAt(intFil, intCol)==null?"-1":(objTblModDat.getValueAt(intFil, intCol).toString().equals("")?"-1":objTblModDat.getValueAt(intFil, intCol).toString()));
            bgdPorCre=objUti.redondearBigDecimal( bgdAuxPor.divide(BigDecimal.valueOf(100)), objParSis.getDecimalesMostrar() ); 
            bgdValFac=objUti.redondearBigDecimal(new BigDecimal( (objTblModDat.getValueAt(intFil, INT_TBL_DAT_VAL_TOT_FAC)==null)?"0":objTblModDat.getValueAt(intFil, INT_TBL_DAT_VAL_TOT_FAC).toString()),objParSis.getDecimalesBaseDatos());                     
            
            if( bgdAuxPor.compareTo(new BigDecimal("-1"))==0 ){
                objTblModDat.setValueAt("", intFil, (intCol+1)); 
            }
            else{
                bgdValPag = bgdValFac.multiply(bgdPorCre);  
                objTblModDat.setValueAt(""+bgdValPag, intFil, (intCol+1)); 
                if(intTipEdi==intCalEditBefore){ 
                    if(bgdPorCre.compareTo(BigDecimal.ZERO)==0){
                        objTblModDat.setValueAt("", intFil, (intCol+1)); 
                    }
                }            
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }   
    
    /**
     * Función que calcula la comprobación de pagos del exterior.
     * @param intFil
     */    
    private void calcularVerificacionPagExt() { 
        try {
            for (int i=0; i<objTblModDat.getRowCountTrue(); i++)
            {
                calcularVerificacionPagExt(i);
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }       
    
    /**
     * Función que calcula la diferencia de valores en pagos al exterior.
     * La casilla de verificación se marcará cuando el valor sea correcto.
     * Y solo entrará en la verificación los pedidos que tengan una fecha de recepción de factura.
     * <BR> 
     * @param intFil
     */    
    private void calcularVerificacionPagExt(int intFil){ 
        BigDecimal bgdValFac = BigDecimal.ZERO;
        BigDecimal bgdValPag = BigDecimal.ZERO;
        BigDecimal bgdValPagDif = BigDecimal.ZERO;
        try {
            String strFecRecFac=objTblModDat.getValueAt(intFil, INT_TBL_RPT_FEC_REC_FAC_PRV)==null?"":objTblModDat.getValueAt(intFil, INT_TBL_RPT_FEC_REC_FAC_PRV).toString();
            if(!strFecRecFac.equals("")){ //Si existe fecha de recepción de factura
                //Calcular si existen diferencias
                bgdValFac=objUti.redondearBigDecimal(new BigDecimal( (objTblModDat.getValueAt(intFil, INT_TBL_RPT_VAL_PED)==null)?"0":objTblModDat.getValueAt(intFil, INT_TBL_RPT_VAL_PED).toString()),objParSis.getDecimalesBaseDatos());                     
                bgdValPag=objUti.redondearBigDecimal(new BigDecimal( (objTblModDat.getValueAt(intFil, INT_TBL_RPT_VAL_PAG_TOT)==null)?"0":objTblModDat.getValueAt(intFil, INT_TBL_RPT_VAL_PAG_TOT).toString()),objParSis.getDecimalesBaseDatos());                     
                bgdValPagDif = bgdValFac.subtract(bgdValPag);
                if(bgdValPagDif.compareTo(BigDecimal.ZERO)==0){
                    objTblModDat.setValueAt(new Boolean(true), intFil, INT_TBL_RPT_CHK_VER_PAG); 
                }
                else{
                    objTblModDat.setValueAt(new Boolean(false), intFil, INT_TBL_RPT_CHK_VER_PAG); 
                }            
            }
            else{
                objTblModDat.setValueAt(new Boolean(false), intFil, INT_TBL_RPT_CHK_VER_PAG); 
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }   
    
    private void calcularPagTotExt() { 
        try {
            for (int i=0; i<objTblModDat.getRowCountTrue(); i++)
            {
                calcularPagTotExt(i);
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }       
    
    private void calcularPagTotExt(int intFil){ 
        BigDecimal bgdValPag = BigDecimal.ZERO;
        try {
            String strFecRecFac=objTblModDat.getValueAt(intFil, INT_TBL_RPT_FEC_REC_FAC_PRV)==null?"":objTblModDat.getValueAt(intFil, INT_TBL_RPT_FEC_REC_FAC_PRV).toString();
            if(!strFecRecFac.equals("")){ //Si existe fecha de recepción de factura
                //Calculo del valor a pagar:  (ValAnt1+ValAnt2+ValSalPrv+ValVen1+ValVen2+ValVen3-NotCre)
                bgdValPag=bgdValPag.add(objUti.redondearBigDecimal(new BigDecimal( (objTblModDat.getValueAt(intFil, INT_TBL_RPT_VAL_SOL_ANT_PRV_1)==null)?"0":objTblModDat.getValueAt(intFil, INT_TBL_RPT_VAL_SOL_ANT_PRV_1).toString()),objParSis.getDecimalesBaseDatos()));
                bgdValPag=bgdValPag.add(objUti.redondearBigDecimal(new BigDecimal( (objTblModDat.getValueAt(intFil, INT_TBL_RPT_VAL_SOL_ANT_PRV_2)==null)?"0":objTblModDat.getValueAt(intFil, INT_TBL_RPT_VAL_SOL_ANT_PRV_2).toString()),objParSis.getDecimalesBaseDatos()));
                bgdValPag=bgdValPag.add(objUti.redondearBigDecimal(new BigDecimal( (objTblModDat.getValueAt(intFil, INT_TBL_RPT_VAL_SOL_SAL_PRV)==null)?"0":objTblModDat.getValueAt(intFil, INT_TBL_RPT_VAL_SOL_SAL_PRV).toString()),objParSis.getDecimalesBaseDatos()));
                bgdValPag=bgdValPag.add(objUti.redondearBigDecimal(new BigDecimal( (objTblModDat.getValueAt(intFil, INT_TBL_RPT_VAL_PAG_VEN_1)==null)?"0":objTblModDat.getValueAt(intFil, INT_TBL_RPT_VAL_PAG_VEN_1).toString()),objParSis.getDecimalesBaseDatos()));
                bgdValPag=bgdValPag.add(objUti.redondearBigDecimal(new BigDecimal( (objTblModDat.getValueAt(intFil, INT_TBL_RPT_VAL_PAG_VEN_2)==null)?"0":objTblModDat.getValueAt(intFil, INT_TBL_RPT_VAL_PAG_VEN_2).toString()),objParSis.getDecimalesBaseDatos()));
                bgdValPag=bgdValPag.add(objUti.redondearBigDecimal(new BigDecimal( (objTblModDat.getValueAt(intFil, INT_TBL_RPT_VAL_PAG_VEN_3)==null)?"0":objTblModDat.getValueAt(intFil, INT_TBL_RPT_VAL_PAG_VEN_3).toString()),objParSis.getDecimalesBaseDatos()));
                bgdValPag=bgdValPag.subtract(objUti.redondearBigDecimal(new BigDecimal( (objTblModDat.getValueAt(intFil, INT_TBL_RPT_VAL_NOT_CRE)==null)?"0":objTblModDat.getValueAt(intFil, INT_TBL_RPT_VAL_NOT_CRE).toString()),objParSis.getDecimalesBaseDatos()));
                objTblModDat.setValueAt(bgdValPag, intFil, INT_TBL_RPT_VAL_PAG_TOT); 
            }
            else{
                objTblModDat.setValueAt("0", intFil, INT_TBL_RPT_VAL_PAG_TOT); 
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }      
 
    private class ZafTblColModLisTotFix implements javax.swing.event.TableColumnModelListener{
        public void columnAdded(javax.swing.event.TableColumnModelEvent e){
        }

        public void columnMarginChanged(javax.swing.event.ChangeEvent e){
            int intColSel, intAncCol;
            if (tblFix.getTableHeader().getResizingColumn()!=null){
                intColSel=tblFix.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0){
                    intAncCol=tblFix.getColumnModel().getColumn(intColSel).getPreferredWidth();
//                    tblTotFix.getColumnModel().getColumn(intColSel).setPreferredWidth(intAncCol);
                }
            }
        }

        public void columnMoved(javax.swing.event.TableColumnModelEvent e){
        }

        public void columnRemoved(javax.swing.event.TableColumnModelEvent e){
        }

        public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e){
        }
    }        
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblDatOrdMouseClicked(java.awt.event.MouseEvent evt)
    {
        try
        {
            int  intColSel=tblDat.getSelectedColumn();
            //Realizar acción sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1)
            //if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1)
            {
                ordenarDatPorCol(tblDat, tblFix, INT_TBL_DAT_ORD_FIL, INT_TBL_DAT_FIX_ORD_FIL, intColSel);
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }    
    
    private boolean validaExiNumPed(Connection conExt, String numeroPedido, int fila) 
    {
        boolean blnRes=false;
        Connection conCie;
        Statement stmCie;
        ResultSet rstCie;
        String strSQLCie="";
        String codigoPedido="";
        try
        {
            if (conExt==null)
                conCie=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            else
                conCie=conExt;
            
            if (conCie!=null)
            {
                //Armar la sentencia SQL.
                stmCie=conCie.createStatement();
                strSQLCie ="";
                strSQLCie+=" SELECT * FROM tbm_cabPedImp AS a1";
                strSQLCie+=" WHERE a1.st_reg NOT IN ('E', 'I') ";
                strSQLCie+=" AND a1.tx_numPed ='"+numeroPedido+"'";
                codigoPedido=objTblModDat.getValueAt(fila, INT_TBL_RPT_COD_DOC)==null?"":objTblModDat.getValueAt(fila, INT_TBL_RPT_COD_DOC).toString();
                if(!codigoPedido.equals("")){
                    strSQLCie+=" AND a1.co_doc <> "+codigoPedido+"";
                }
                rstCie=stmCie.executeQuery(strSQLCie);
                if(rstCie.next())   {
                    blnRes=true;
                } 
                rstCie.close();
                rstCie=null;
                stmCie.close();
                stmCie=null;      
                if (conExt==null){  //Si trabaja con conexión local, cierra conexión.
                    conCie.close();
                    conCie=null;
                }                
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }        
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblFixOrdMouseClicked(java.awt.event.MouseEvent evt)
    {
        try
        {
            int  intColSel=tblFix.getSelectedColumn();
            //Realizar acción sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1)
            //if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1)
            {
                ordenarDatPorCol(tblFix, tblDat, INT_TBL_DAT_FIX_ORD_FIL, INT_TBL_DAT_ORD_FIL, intColSel);
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }        
    
    /***
     * Función que permite ordenar los datos de los 2 paneles, de acuerdo a la columna de ordenamiento solicitada.
     */ 
    private void ordenarDatPorCol(javax.swing.JTable tabla1, javax.swing.JTable tabla2, int columnaOrdTabla1, int columnaOrdTabla2, int columnaCriterioOrden) 
    {
        try{
            ArrayList arlCabDat, arlRegDat;
            int INT_FIL_BEF=0;
            int INT_FIL_NEW=1;
            int INT_NUM_PED=2;
            
            /* Realiza ordenamiento de la tabla seleccionada, según criterio especificado. */
            objTblOrd=new ZafTblOrd(tabla1);   
            objTblOrd.ordenar(columnaCriterioOrden);  

            /*Obtiene el orden de las filas, previo al ordenamiento.*/
            arlCabDat=new ArrayList();
            for(int i=0; i<tabla1.getRowCount();i++) {
                arlRegDat=new ArrayList();
                arlRegDat.add(INT_FIL_BEF, (tabla1.getValueAt(i, columnaOrdTabla1).toString())); //Obtiene el orden de la fila antes de actualizar.
                arlRegDat.add(INT_FIL_NEW, (i+1)); 
                arlRegDat.add(INT_NUM_PED, (tabla1.getValueAt(i, INT_TBL_DAT_NUM_PED).toString())); 
                arlCabDat.add(arlRegDat);
            }  

            /* Actualiza nuevo orden de las filas. */
            for(int k=0; k<arlCabDat.size();k++) {
                tabla1.setValueAt((objUti.getStringValueAt(arlCabDat, k, INT_FIL_NEW)), k, columnaOrdTabla1); /*Se actualiza la fila con el nuevo orden*/
            }

            /*Obtiene nuevo orden asignado de tabla1 y Actualiza el orden de la tabla2*/
            for(int k=0; k<arlCabDat.size();k++) {
                tabla2.setValueAt((objUti.getStringValueAt(arlCabDat, k, INT_FIL_NEW)), k, columnaOrdTabla2); //Se actualiza la fila con el nuevo orden 
            }     

            /* Realiza ordenamiento de la tabla 2 seleccionada, según criterio especificado. */
            objTblOrd2=new ZafTblOrd(tabla2);   
            objTblOrd2.ordenar(columnaOrdTabla2);
        }   
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    

}

