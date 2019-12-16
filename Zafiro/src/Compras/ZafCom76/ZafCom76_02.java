/*
 * ZafCom76_02.java
 *
 */
package Compras.ZafCom76;
import Librerias.ZafEvt.ZafAsiDiaEvent;
import Librerias.ZafEvt.ZafAsiDiaListener;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.swing.event.EventListenerList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
/**
 * @author  Ingrid Lino
 */
public class ZafCom76_02 extends javax.swing.JPanel
{
    //Constantes:
    private static final int INT_BEF_INS=0;                   /**Antes de insertar: Indica "Before insertar".*/
    private static final int INT_AFT_INS=1;                   /**Después de insertar: Indica "After insertar".*/
    private static final int INT_BEF_CON=2;                   /**Antes de consultar: Indica "Before consultar".*/
    private static final int INT_AFT_CON=3;                   /**Después de consultar: Indica "After consultar".*/
    private static final int INT_BEF_MOD=4;                   /**Antes de modificar: Indica "Before modificar".*/
    private static final int INT_AFT_MOD=5;                   /**Después de modificar: Indica "After modificar".*/
    private static final int INT_BEF_ELI=6;                   /**Antes de eliminar: Indica "Before eliminar".*/
    private static final int INT_AFT_ELI=7;                   /**Después de eliminar: Indica "After eliminar".*/
    private static final int INT_BEF_ANU=8;                   /**Antes de anular: Indica "Before anular".*/
    private static final int INT_AFT_ANU=9;                   /**Después de anular: Indica "After anular".*/
    private static final int INT_BEF_EDI_CEL=10;              /**Antes de editar celda: Indica "Before editar celda".*/
    private static final int INT_AFT_EDI_CEL=11;              /**Después de editar celda: Indica "After editar celda".*/
    private static final int INT_BEF_CON_CTA=12;              /**Antes de consultar las cuentas: Indica "Before consultar cuentas".*/
    private static final int INT_AFT_CON_CTA=13;              /**Después de consultar las cuentas: Indica "After consultar cuentas".*/

    //Constantes: Columnas del JTable.
    public static final int INT_TBL_DAT_LIN=0;                //Línea
    public static final int INT_TBL_DAT_COD_ITM_MAE=1;        //Codigo de item maestro
    public static final int INT_TBL_DAT_COD_ITM=2;            //Codigo de item
    public static final int INT_TBL_DAT_COD_ALT_ITM=3;        //Código alterno del item
    public static final int INT_TBL_DAT_COD_LET_ITM=4;        //Código alterno del item 2
    public static final int INT_TBL_DAT_BUT_ITM=5;            //Botón para consulta del item
    public static final int INT_TBL_DAT_NOM_ITM=6;            //Nombre del item
    public static final int INT_TBL_DAT_UNI_MED=7;            //Unidad de medida
    public static final int INT_TBL_DAT_PES=8;                //Peso del item    
    public static final int INT_TBL_DAT_COD_ARA=9;            //Codigo Arancel
    public static final int INT_TBL_DAT_NOM_ARA=10;           //Nombre Arancel
    public static final int INT_TBL_DAT_DES_ARA=11;           //Descripcion Arancel       
    public static final int INT_TBL_DAT_POR_ARA=12;           //Porcentaje Arancel
    public static final int INT_TBL_DAT_CAN_TON_MET=13;       //Cantidad de Tonelada Metrica
    public static final int INT_TBL_DAT_CAN_NOT_PED=14;       //Cantidad Nota de Pedido
    public static final int INT_TBL_DAT_CAN_PZA_PED_EMB=15;   //Cantidad Pedido Embarcado
    public static final int INT_TBL_DAT_CAN_PEN_NOT_PED=16;   //Cantidad utilizada de la nota de pedido.
    public static final int INT_TBL_DAT_CAN_UTI_PED_EMB=17;   //Cantidad utilizada del pedido embarcado
    public static final int INT_TBL_DAT_PRE_UNI=18;           //Precio Unitario
    public static final int INT_TBL_DAT_TOT_FOB_CFR=19;       //Total de FOB / CFR
    public static final int INT_TBL_DAT_VAL_FLE=20;           //Valor de flete
    public static final int INT_TBL_DAT_VAL_CFR=21;           //Valor CFR
    public static final int INT_TBL_DAT_VAL_CFR_ARA=22;       //Valor CFR * Porcentaje de arancel
    public static final int INT_TBL_DAT_TOT_ARA=23;           //Total de arancel
    public static final int INT_TBL_DAT_TOT_GAS=24;           //Total de gasto
    public static final int INT_TBL_DAT_TOT_COS=25;           //Total de costo
    public static final int INT_TBL_DAT_COS_UNI=26;           //Costo unitario
    public static final int INT_TBL_DAT_COD_EMP_REL=27;       //Codigo de empresa de la nota de pedido no del pedido de embarque
    public static final int INT_TBL_DAT_COD_LOC_REL=28;       //Codigo de local de la nota de pedido no del pedido de embarque
    public static final int INT_TBL_DAT_COD_TIP_DOC_REL=29;   //Codigo de tipo de documento de la nota de pedido no del pedido de embarque
    public static final int INT_TBL_DAT_COD_DOC_REL=30;       //Codigo de documento de la nota de pedido no del pedido de embarque
    public static final int INT_TBL_DAT_COD_REG_REL=31;       //Codigo de registro de la nota de pedido no del pedido de embarque
    public static final int INT_TBL_DAT_CAN_PZA_PED_EMB_AUX=32;   //Cantidad pedido embarcado auxiliar

    //ArrayList: Filas 
    private ArrayList arlRegFilEli, arlDatFilEli;
    public static final int INT_CFE_COD_EMP=0;               //Columnas de las filas eliminadas: Código de la empresa
    public static final int INT_CFE_COD_LOC=1;               //Columnas de las filas eliminadas: Código del local
    public static final int INT_CFE_COD_TIP_DOC=2;           //Columnas de las filas eliminadas: Código del tipo de documento
    public static final int INT_CFE_COD_DOC=3;               //Columnas de las filas eliminadas: Código del documento.
    public static final int INT_CFE_COD_REG=4;               //Columnas de las filas eliminadas: Código del registro.
    public static final int INT_CFE_CAN_PED_EMB_AUX=5;       //Columnas de las filas eliminadas: Cantidad que esta guardada en la db, esta es la que se debe reversar
    public static final int INT_CFE_CAN_UTI_PED_EMB=6;       //Columnas de las filas eliminadas: Cantidad que esta guardada en la db, esta es la que se debe reversar
    public static final int INT_CFE_CAN_NOT_PED=7;           //Columnas de las filas eliminadas: Cantidad que esta guardada en la db, esta es la que se debe reversar
    public static final int INT_CFE_CAN_PED_EMB=8;           //Columnas de las filas eliminadas: Cantidad que esta guardada en la db, esta es la que se debe reversar
    public static final int INT_CFE_CAN_PEN_NOT_PED=9;       //Columnas de las filas eliminadas: Cantidad que esta guardada en la db, esta es la que se debe reversar
        
    //Variables generales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;   
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblTot objTblTot;
    private ZafTblCelRenLbl objTblCelRenLbl;                //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxtCan, objTblCelEdiTxtPre, objTblCelEdiTxtPie, objTblCelEdiTxtAra;//Editor: JTextField en celda.
    private ZafMouMotAda objMouMotAda;                      //ToolTipText en TableHeader.
    private ZafTblModLis objTblModLis;                      //Detectar cambios de valores en las celdas.
    private ZafTblPopMnu objTblPopMnu;                      //PopupMenu: Establecer PeopuMené en JTable.
    private ZafTblEdi objTblEdi;
    private ZafPerUsr objPerUsr;
    private ZafTblOrd objTblOrd;                            //JTable de ordenamiento.    
    private ZafTblCelRenBut objTblCelRenButItm;
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm;
    private ZafVenCon vcoItm;
    private ZafCom76_01 objCom76_01;
    
    private Vector vecDat, vecCab,vecAux;
    private boolean blnHayCam;
    private boolean blnCon;                         //true: Continua la ejecucién del hilo.
    private boolean blnAsiDiaEdi;                   //true: El asiento de diario es editable.
    private boolean blnCanOpe;                      //Determina si se debe cancelar la operación.
    private byte bytDiaGen;                         //0=Diario no generado; 1=Diario generado por el Sistema; 2=Diario generado por el usuario.
    private char chrOpcManDatTooBar;
    
    private BigDecimal bgdValTotFobCfr;//para obtener el valor total de la sumatoria del detalle de la columna Tot.Fob/Cfr
    private BigDecimal bgdV2_FleItm;
    private BigDecimal bgdValV4ItmPagAra;
    private BigDecimal bgdValV15FacGas;
    private BigDecimal bgdValV5FacAra;
    private BigDecimal bgdValTotCos;                //para campos de la la cabecera del formulario  txtValDoc
    private BigDecimal bgdPesTot;                   //para campos de la la cabecera del formulario  txtPesTotKgs       
    private BigDecimal bgdTotSumCan;
    private BigDecimal bgdValTotAra;
    
    private int intTipNotPed;//TipoNotaPedido , viene de ZafCom76
    private String strSQL;
    private String strFechaDocumento, strFechaEmbarque;

    protected EventListenerList objEveLisLis=new EventListenerList();     

    /** Crea una nueva instancia de la clase ZafAsiDia_v01. */
    public ZafCom76_02(ZafParSis obj, ZafCom76_01 objCom76_01Ref)
    {
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
        blnCanOpe=false;
        arlDatFilEli=new ArrayList();
        objCom76_01=objCom76_01Ref;
        intTipNotPed=0;
        bgdValTotCos=new BigDecimal(BigInteger.ZERO);
        bgdValTotFobCfr=new BigDecimal(BigInteger.ZERO);
        blnHayCam=false;
        bgdValTotAra=new BigDecimal(BigInteger.ZERO);

        configurarFrm();
    }

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Inicializar variables.
            bytDiaGen=0;
            //Configurar los JTables.
            configurarVenConItm();
            configurarTblDat();
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
    private boolean configurarTblDat(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(33);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE,"Cód.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM,"Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_COD_LET_ITM,"Cód.Let.Itm.");
            vecCab.add(INT_TBL_DAT_BUT_ITM,"");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre del item");
            vecCab.add(INT_TBL_DAT_UNI_MED,"Uni.Med.");
            vecCab.add(INT_TBL_DAT_PES,"Peso(kg)");
            vecCab.add(INT_TBL_DAT_COD_ARA,"Cód.Par.");
            vecCab.add(INT_TBL_DAT_NOM_ARA,"Partida");
            vecCab.add(INT_TBL_DAT_DES_ARA,"Descripción");
            vecCab.add(INT_TBL_DAT_POR_ARA,"Arancel");
            vecCab.add(INT_TBL_DAT_CAN_TON_MET,"Can.Ton.Met.");
            vecCab.add(INT_TBL_DAT_CAN_NOT_PED,    "Can.Not.Ped");
            vecCab.add(INT_TBL_DAT_CAN_PZA_PED_EMB,"Cantidad");
            vecCab.add(INT_TBL_DAT_CAN_PEN_NOT_PED,"Can.Pen.Not.Ped.");
            vecCab.add(INT_TBL_DAT_CAN_UTI_PED_EMB,"Can.Uti.Ped.Emb.");
            vecCab.add(INT_TBL_DAT_PRE_UNI,"Pre.Uni");
            vecCab.add(INT_TBL_DAT_TOT_FOB_CFR,"Tot.FOB/CFR");            
            vecCab.add(INT_TBL_DAT_VAL_FLE,"Val.Fle.");
            vecCab.add(INT_TBL_DAT_VAL_CFR,"Val.CFR.");
            vecCab.add(INT_TBL_DAT_VAL_CFR_ARA,"Val.CFR.Ara");
            vecCab.add(INT_TBL_DAT_TOT_ARA,"Tot.Ara.");
            vecCab.add(INT_TBL_DAT_TOT_GAS,"Tot.Gas.");
            vecCab.add(INT_TBL_DAT_TOT_COS,"Tot.Cos.");
            vecCab.add(INT_TBL_DAT_COS_UNI,"Cos.Uni.");
            vecCab.add(INT_TBL_DAT_COD_EMP_REL,"Cód.Emp.Rel.");
            vecCab.add(INT_TBL_DAT_COD_LOC_REL,"Cód.Loc.Rel.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC_REL,"Cód.Tip.Doc.Rel.");
            vecCab.add(INT_TBL_DAT_COD_DOC_REL,"Cód.Doc.Rel.");
            vecCab.add(INT_TBL_DAT_COD_REG_REL,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_CAN_PZA_PED_EMB_AUX,"Auxiliar");

            objPerUsr=new ZafPerUsr(objParSis);
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_TON_MET, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_NOT_PED, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_PZA_PED_EMB, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_PZA_PED_EMB_AUX, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_UTI_PED_EMB, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_PRE_UNI, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_TOT_FOB_CFR, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_FLE, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_CFR, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_CFR_ARA, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_TOT_ARA, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_TOT_GAS, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_TOT_COS, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COS_UNI, objTblMod.INT_COL_DBL, new Integer(0), null);
            
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_COD_ITM_MAE);
            arlAux.add("" + INT_TBL_DAT_CAN_PZA_PED_EMB);
            arlAux.add("" + INT_TBL_DAT_PRE_UNI);
            arlAux.add("" + INT_TBL_DAT_CAN_TON_MET);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccién.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mené de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET_ITM).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_PES).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_COD_ARA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ARA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DES_ARA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_POR_ARA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TON_MET).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_NOT_PED).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PZA_PED_EMB).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PEN_NOT_PED).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAN_UTI_PED_EMB).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PRE_UNI).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_TOT_FOB_CFR).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_FLE).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CFR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CFR_ARA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TOT_ARA).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_TOT_GAS).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_TOT_COS).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_REL).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_REL).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_REL).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_REL).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_REL).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PZA_PED_EMB_AUX).setPreferredWidth(80);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setResizable(false);
//            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ARA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_ARA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DES_ARA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_POR_ARA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_TON_MET, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_NOT_PED, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_PEN_NOT_PED, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_UTI_PED_EMB, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_VAL_FLE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_VAL_CFR_ARA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TOT_ARA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TOT_GAS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_PZA_PED_EMB_AUX, tblDat);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_PES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_POR_ARA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TON_MET).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_NOT_PED).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PZA_PED_EMB).setCellRenderer(objTblCelRenLbl);            
            tcmAux.getColumn(INT_TBL_DAT_PRE_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TOT_FOB_CFR).setCellRenderer(objTblCelRenLbl);
            
            tcmAux.getColumn(INT_TBL_DAT_VAL_FLE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CFR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CFR_ARA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TOT_ARA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TOT_GAS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TOT_COS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setCellRenderer(objTblCelRenLbl);

            
            objTblCelRenLbl=null;


            objTblCelEdiTxtCan=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PZA_PED_EMB).setCellEditor(objTblCelEdiTxtCan);
            objTblCelEdiTxtCan.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                BigDecimal bgdValCanPndNotPed=new BigDecimal(BigInteger.ZERO);
                BigDecimal bgdValCanPedEmb=new BigDecimal(BigInteger.ZERO);
                BigDecimal bgdValCanPedEmbAnt=new BigDecimal(BigInteger.ZERO);
                BigDecimal bgdValCanPedEmbAux=new BigDecimal(BigInteger.ZERO);

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();

                    bgdValCanPndNotPed=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PEN_NOT_PED)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PEN_NOT_PED).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PEN_NOT_PED).toString()));
                    bgdValCanPedEmbAnt=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PZA_PED_EMB)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PZA_PED_EMB).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PZA_PED_EMB).toString()));
                    bgdValCanPedEmbAux=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PZA_PED_EMB_AUX)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PZA_PED_EMB_AUX).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PZA_PED_EMB_AUX).toString()));
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    bgdValCanPedEmb=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PZA_PED_EMB)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PZA_PED_EMB).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PZA_PED_EMB).toString()));
                    if( (chrOpcManDatTooBar=='x')  ||  (chrOpcManDatTooBar=='m')  ){//por modificacion
                        if(bgdValCanPedEmbAux.add(bgdValCanPndNotPed).compareTo(bgdValCanPedEmb)<0){
                            if(msgPermitirValorExcedidoEmbarque()){
                                objTblMod.setValueAt(bgdValCanPedEmb, intFil, INT_TBL_DAT_CAN_PZA_PED_EMB);//se coloca el valor ingresado
                            }
                            else{
                                objTblMod.setValueAt(bgdValCanPedEmbAnt, intFil, INT_TBL_DAT_CAN_PZA_PED_EMB);//se deja el valor que estaba antes de intentar cambiar
                            }
                        }
                    }
                    else{
                        if(bgdValCanPedEmb.compareTo(bgdValCanPndNotPed)>0){
                            if(msgPermitirValorExcedidoEmbarque()){
                                objTblMod.setValueAt(bgdValCanPedEmb, intFil, INT_TBL_DAT_CAN_PZA_PED_EMB);
                            }
                            else{
                                objTblMod.setValueAt(bgdValCanPedEmbAnt, intFil, INT_TBL_DAT_CAN_PZA_PED_EMB);
                            }
                        }
                    }
                    
                    regenerarCalculos();

                    //Generar evento "afterEditarCelda()".
                    fireCom76_02Listener(new ZafAsiDiaEvent(this), INT_AFT_EDI_CEL);


                }
            });


            objTblCelEdiTxtPre=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_PRE_UNI).setCellEditor(objTblCelEdiTxtPre);
            objTblCelEdiTxtPre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    regenerarCalculos();

                    //Generar evento "afterEditarCelda()".
                    fireCom76_02Listener(new ZafAsiDiaEvent(this), INT_AFT_EDI_CEL);

                }
            });


            objTblCelEdiTxtPie=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TON_MET).setCellEditor(objTblCelEdiTxtPie);
            objTblCelEdiTxtPie.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    if((intTipNotPed==0) || (intTipNotPed==3) || (intTipNotPed==4) )
                        objTblCelEdiTxtPie.setCancelarEdicion(true);
                    else
                        objTblCelEdiTxtPie.setCancelarEdicion(false);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    regenerarCalculos();

                    //Generar evento "afterEditarCelda()".
                    fireCom76_02Listener(new ZafAsiDiaEvent(this), INT_AFT_EDI_CEL);

                }
            });

            objTblCelEdiTxtAra=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_POR_ARA).setCellEditor(objTblCelEdiTxtAra);
            objTblCelEdiTxtAra.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    regenerarCalculos();

                    //Generar evento "beforeModificar()".
                    fireCom76_02Listener(new ZafAsiDiaEvent(this), INT_AFT_EDI_CEL);

                }
            });


            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CAN_PZA_PED_EMB);
            vecAux.add("" + INT_TBL_DAT_CAN_TON_MET);
            vecAux.add("" + INT_TBL_DAT_PRE_UNI);

            if(objParSis.getCodigoUsuario()==1){
                vecAux.add("" + INT_TBL_DAT_BUT_ITM);
                vecAux.add("" + INT_TBL_DAT_COD_ALT_ITM);
                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_ITM, tblDat);
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            }
            else{
                //SETEAR TODO A FALSO Y A EDITABLE
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                objTblPopMnu.setInsertarFilaEnabled(false);
                objTblPopMnu.setInsertarFilasEnabled(false);

                //INSERCION
                if(objPerUsr.isOpcionEnabled(4351)){ //Ficha "General": Tabla de items->Insertar items
                    objTblPopMnu.setInsertarFilaEnabled(true);
                    objTblPopMnu.setInsertarFilasEnabled(true);
                     vecAux.add("" + INT_TBL_DAT_BUT_ITM);
                     vecAux.add("" + INT_TBL_DAT_COD_ALT_ITM);
                     objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_ITM, tblDat);
                     objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                }
            }
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);


            //Configurar JTable: Renderizar celdas.
            objTblCelRenButItm=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellRenderer(objTblCelRenButItm);
            objTblCelRenButItm=null;
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[11];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            intColVen[3]=4;
            intColVen[4]=5;
            intColVen[5]=6;
            intColVen[6]=7;
            intColVen[7]=8;
            intColVen[8]=9;
            intColVen[9]=10;
            intColVen[10]=11;
            int intColTbl[]=new int[11];
            intColTbl[0]=INT_TBL_DAT_COD_ITM_MAE;
            intColTbl[1]=INT_TBL_DAT_COD_ITM;
            intColTbl[2]=INT_TBL_DAT_COD_ALT_ITM;
            intColTbl[3]=INT_TBL_DAT_COD_LET_ITM;
            intColTbl[4]=INT_TBL_DAT_NOM_ITM;
            intColTbl[5]=INT_TBL_DAT_UNI_MED;
            intColTbl[6]=INT_TBL_DAT_PES;
            intColTbl[7]=INT_TBL_DAT_COD_ARA;
            intColTbl[8]=INT_TBL_DAT_NOM_ARA;
            intColTbl[9]=INT_TBL_DAT_DES_ARA;
            intColTbl[10]=INT_TBL_DAT_POR_ARA;

            objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellEditor(objTblCelEdiTxtVcoItm);
            objTblCelEdiTxtVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strSQLAdi="";
                String strLin="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    strLin=objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN).toString();
                    //INSERCION
                    if( (objPerUsr.isOpcionEnabled(4351)) && (strLin.equals("I")) ){
                        objTblCelEdiTxtVcoItm.setCancelarEdicion(false);
                    }
                    else if(  (objPerUsr.isOpcionEnabled(4351)) && (strLin.equals("M")) ){//MODIFICACION
                        //objTblCelEdiTxtVcoItm.setCancelarEdicion(true);//antes true, pero Alfredo solicito que en modificar tb se le permita modificar no solo la cantidad y el precio sino el item.
                        objTblCelEdiTxtVcoItm.setCancelarEdicion(false);
                    }
                    else{
                        objTblCelEdiTxtVcoItm.setCancelarEdicion(true);
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoItm.setCampoBusqueda(2);
                    vcoItm.setCriterio1(11);
                    strSQLAdi=" 		AND CAST('" + objUti.formatearFecha(getFechaDocumento(), "dd/MM/yyyy", "yyyy-MM-dd") + "' AS date) BETWEEN a6.fe_vigDes";
                    strSQLAdi+=" 	AND (CASE WHEN a6.fe_vigHas IS NULL THEN CURRENT_DATE ELSE a6.fe_vigHas END))";
                    strSQLAdi+=" ON a1.co_grpImp=a4.co_grp";
                    strSQLAdi+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQLAdi+=" AND (UPPER(a1.tx_codAlt) LIKE '%I' OR UPPER(a1.tx_codAlt) LIKE '%S') AND a1.st_reg NOT IN('T','U') AND a1.st_ser NOT IN('S','T')";
                    //strSQLAdi+=" AND a6.nd_ara IS NOT NULL";  ///Esto es para validar solo en Importaciones, no compras locales.
                    strSQLAdi+=" GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr, a5.co_parara, a5.tx_par, a5.tx_des, a2.tx_desCor,a6.nd_ara";
                    strSQLAdi+=" ORDER BY a1.tx_codAlt";
                    vcoItm.setCondicionesSQL(strSQLAdi);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoItm.isConsultaAceptada()){
                        objTblMod.setValueAt(vcoItm.getValueAt(1),  intFil, INT_TBL_DAT_COD_ITM_MAE);
                        objTblMod.setValueAt(vcoItm.getValueAt(2),  intFil, INT_TBL_DAT_COD_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(3),  intFil, INT_TBL_DAT_COD_ALT_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(4),  intFil, INT_TBL_DAT_COD_LET_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(5),  intFil, INT_TBL_DAT_NOM_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(6),  intFil, INT_TBL_DAT_UNI_MED);
                        objTblMod.setValueAt(vcoItm.getValueAt(7),  intFil, INT_TBL_DAT_PES);
                        objTblMod.setValueAt(vcoItm.getValueAt(8),  intFil, INT_TBL_DAT_COD_ARA);
                        objTblMod.setValueAt(vcoItm.getValueAt(9),  intFil, INT_TBL_DAT_NOM_ARA);
                        objTblMod.setValueAt(vcoItm.getValueAt(10), intFil, INT_TBL_DAT_DES_ARA);
                        objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_POR_ARA);
                        regenerarCalculos();
                    }
                }
            });

            objTblCelEdiButVcoItm=new ZafTblCelEdiButVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellEditor(objTblCelEdiButVcoItm);
            objTblCelEdiButVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strSQLAdi="";
                String strLin="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    strLin=objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN).toString();
                    //INSERCION
                    if( (objPerUsr.isOpcionEnabled(4351)) && (strLin.equals("I")) ){
                        objTblCelEdiButVcoItm.setCancelarEdicion(false);
                    }
                    else if(  (objPerUsr.isOpcionEnabled(4351)) && (strLin.equals("M")) ){//MODIFICACION
                        objTblCelEdiButVcoItm.setCancelarEdicion(true);//antes true, pero Alfredo solicito que en modificar tb se le permita modificar no solo la cantidad y el precio sino el item.
                        objTblCelEdiButVcoItm.setCancelarEdicion(false);
                    }
                    else{
                        objTblCelEdiButVcoItm.setCancelarEdicion(true);
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoItm.setCampoBusqueda(3);
                    vcoItm.setCriterio1(11);
                    strSQLAdi=" 		AND CAST('" + objUti.formatearFecha(getFechaDocumento(), "dd/MM/yyyy", "yyyy-MM-dd") + "' AS date) BETWEEN a6.fe_vigDes";
                    strSQLAdi+=" 	AND (CASE WHEN a6.fe_vigHas IS NULL THEN CURRENT_DATE ELSE a6.fe_vigHas END))";
                    strSQLAdi+=" ON a1.co_grpImp=a4.co_grp";
                    strSQLAdi+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQLAdi+=" AND (UPPER(a1.tx_codAlt) LIKE '%I' OR UPPER(a1.tx_codAlt) LIKE '%S') AND a1.st_reg NOT IN('T','U') AND a1.st_ser NOT IN('S','T')";
                    //strSQLAdi+=" AND a6.nd_ara IS NOT NULL";  ///Esto es para validar solo en Importaciones, no compras locales.
                    strSQLAdi+=" GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr, a5.co_parara, a5.tx_par, a5.tx_des, a2.tx_desCor, a6.nd_ara";
                    strSQLAdi+=" ORDER BY a1.tx_codAlt";
                    vcoItm.setCondicionesSQL(strSQLAdi);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoItm.isConsultaAceptada()){
                        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                        objTblMod.removeEmptyRows();
                        objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                        objTblMod.setValueAt(vcoItm.getValueAt(1),  intFil, INT_TBL_DAT_COD_ITM_MAE);
                        objTblMod.setValueAt(vcoItm.getValueAt(2),  intFil, INT_TBL_DAT_COD_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(3),  intFil, INT_TBL_DAT_COD_ALT_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(4),  intFil, INT_TBL_DAT_COD_LET_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(5),  intFil, INT_TBL_DAT_NOM_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(6),  intFil, INT_TBL_DAT_UNI_MED);
                        objTblMod.setValueAt(vcoItm.getValueAt(7),  intFil, INT_TBL_DAT_PES);
                        objTblMod.setValueAt(vcoItm.getValueAt(8),  intFil, INT_TBL_DAT_COD_ARA);
                        objTblMod.setValueAt(vcoItm.getValueAt(9),  intFil, INT_TBL_DAT_NOM_ARA);
                        objTblMod.setValueAt(vcoItm.getValueAt(10), intFil, INT_TBL_DAT_DES_ARA);
                        objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_POR_ARA);
                        regenerarCalculos();
                    }
                }
            });
            intColVen=null;
            intColTbl=null;

            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);
            //Libero los objetos auxiliares.
            tcmAux=null;

            //Configurar JTable: Establecer relacián entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_CAN_TON_MET, INT_TBL_DAT_CAN_PZA_PED_EMB, INT_TBL_DAT_TOT_FOB_CFR, INT_TBL_DAT_VAL_FLE, INT_TBL_DAT_VAL_CFR, INT_TBL_DAT_VAL_CFR_ARA, INT_TBL_DAT_TOT_ARA, INT_TBL_DAT_TOT_GAS, INT_TBL_DAT_TOT_COS};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);


            objTblPopMnu.setPegarVisible(true);
            objTblPopMnu.setPegarEnabled(true);

             objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                 int intFilEli[];

                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnu.isClickEliminarFila()){
                        intFilEli=tblDat.getSelectedRows();
                        objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                        for(int p=(intFilEli.length-1);p>=0; p--){
                            //se guardan los datos necesarios en un arraylist para guardar en frio esos items eliminados.
                            arlRegFilEli=new ArrayList();
                            arlRegFilEli.add(INT_CFE_COD_EMP,           "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_COD_EMP_REL));
                            arlRegFilEli.add(INT_CFE_COD_LOC,           "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_COD_LOC_REL));
                            arlRegFilEli.add(INT_CFE_COD_TIP_DOC,       "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_COD_TIP_DOC_REL));
                            arlRegFilEli.add(INT_CFE_COD_DOC,           "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_COD_DOC_REL));
                            arlRegFilEli.add(INT_CFE_COD_REG,           "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_COD_REG_REL));
                            arlRegFilEli.add(INT_CFE_CAN_PED_EMB_AUX,   "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_CAN_PZA_PED_EMB_AUX));
                            arlRegFilEli.add(INT_CFE_CAN_UTI_PED_EMB,   "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_CAN_UTI_PED_EMB));
                            arlRegFilEli.add(INT_CFE_CAN_NOT_PED,       "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_CAN_NOT_PED));
                            arlRegFilEli.add(INT_CFE_CAN_PED_EMB,       "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_CAN_PZA_PED_EMB));
                            arlRegFilEli.add(INT_CFE_CAN_PEN_NOT_PED,   "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_CAN_PEN_NOT_PED));
                            arlDatFilEli.add(arlRegFilEli);
                        }
                    }
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {

                    if (objTblPopMnu.isClickEliminarFila()){
                        regenerarCalculos();
                    }
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
            });

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
     * resulta muy corto para mostrar leyendas que requieren més espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_LET_ITM:
                    strMsg="";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="";
                    break;
                case INT_TBL_DAT_UNI_MED:
                    strMsg="";
                    break;                    
                case INT_TBL_DAT_PES:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_ARA:
                    strMsg="";
                    break;
                case INT_TBL_DAT_NOM_ARA:
                    strMsg="";
                    break;
                case INT_TBL_DAT_DES_ARA:
                    strMsg="";
                    break;
                case INT_TBL_DAT_POR_ARA:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CAN_TON_MET:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CAN_NOT_PED:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CAN_PZA_PED_EMB:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CAN_PEN_NOT_PED:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CAN_UTI_PED_EMB:
                    strMsg="";
                    break;
                case INT_TBL_DAT_PRE_UNI:
                    strMsg="";
                    break;
                case INT_TBL_DAT_TOT_FOB_CFR:
                    strMsg="";
                    break;
                case INT_TBL_DAT_VAL_FLE:
                    strMsg="";
                    break;
                case INT_TBL_DAT_VAL_CFR:
                    strMsg="";
                    break;
                case INT_TBL_DAT_VAL_CFR_ARA:
                    strMsg="";
                    break;
                case INT_TBL_DAT_TOT_ARA:
                    strMsg="";
                    break;
                case INT_TBL_DAT_TOT_GAS:
                    strMsg="";
                    break;
                case INT_TBL_DAT_TOT_COS:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COS_UNI:
                    strMsg="";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)   {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    blnHayCam=true;
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    blnHayCam=true;
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    blnHayCam=true;
                    if ( (tblDat.getEditingColumn()==INT_TBL_DAT_COD_ALT_ITM) )
                    break;
                    if ( (tblDat.getEditingColumn()==INT_TBL_DAT_BUT_ITM) )
                    break;
                    if ( (tblDat.getEditingColumn()==INT_TBL_DAT_CAN_PZA_PED_EMB) )
                    break;
                    if ( (tblDat.getEditingColumn()==INT_TBL_DAT_PRE_UNI) )
                    break;
                    if ( (tblDat.getEditingColumn()==INT_TBL_DAT_CAN_TON_MET) )
                    break;
            }
        }
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
            arlCam.add("a3.co_itmMae");
            arlCam.add("a3.co_itm");
            arlCam.add("a1.tx_codAlt");
            arlCam.add("a1.tx_codAlt2");
            arlCam.add("a1.tx_nomItm");
            arlCam.add("a1.tx_uniMed");
            arlCam.add("a1.nd_pesitmkgr");
            arlCam.add("a5.co_parara");
            arlCam.add("a5.tx_par");
            arlCam.add("a5.tx_des");
            arlCam.add("a6.nd_ara");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.Mae.");
            arlAli.add("Cód.Itm.");
            arlAli.add("Alterno");
            arlAli.add("Letra");
            arlAli.add("Nombre");
            arlAli.add("Uni.Med.");
            arlAli.add("Peso(kg)");            
            arlAli.add("Cód.Par.Ara.");
            arlAli.add("Nom.Par.Ara.");
            arlAli.add("Des.Par.Ara.");
            arlAli.add("% arancel");

            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("50");
            arlAncCol.add("250");
            arlAncCol.add("50");
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, CASE WHEN a1.nd_pesitmkgr IS NULL THEN 0 ELSE a1.nd_pesitmkgr END AS nd_pesitmkgr";
            strSQL+=" , a5.co_parara, a5.tx_par, a5.tx_des, a2.tx_DesCor as tx_uniMed, a6.nd_ara";
            //strSQL+=", CASE WHEN a6.nd_ara IS NULL THEN 0 ELSE a6.nd_ara END AS nd_ara";
            strSQL+=" FROM ((tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=")";
            strSQL+=" LEFT OUTER JOIN(tbm_grpInvImp AS a4 INNER JOIN tbm_parAraImp AS a5 ON a4.co_parAra=a5.co_parAra";
            strSQL+=" 		INNER JOIN tbm_vigParAraImp AS a6 ON a5.co_parAra=a6.co_parAra";
            //Ocultar columnas.
            int intColOcu[]=new int[5];
            intColOcu[0]=1;
            intColOcu[1]=2;
            intColOcu[2]=8;
            intColOcu[3]=9;
            intColOcu[4]=10;

            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoItm.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
            vcoItm.setConfiguracionColumna(7, javax.swing.JLabel.RIGHT, vcoItm.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(8, javax.swing.JLabel.RIGHT, vcoItm.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setCampoBusqueda(3);
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panNor = new javax.swing.JPanel();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

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

        add(spnDat, java.awt.BorderLayout.CENTER);

        panNor.setPreferredSize(new java.awt.Dimension(0, 30));
        panNor.setLayout(new java.awt.BorderLayout());

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

        panNor.add(spnTot, java.awt.BorderLayout.CENTER);

        add(panNor, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panNor;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podréa utilizar
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
     * Esta función inicializa el asiento de diario.
     * @return true: Si se pudo inicializar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean inicializar()
    {
        boolean blnRes=true;
        objTblMod.removeAllRows();
        objTblMod.initRowsState();
        blnHayCam=false;
        //bdeMonHab=new BigDecimal("0");
        bytDiaGen=0;
        return blnRes;
    }

    /**
     * Esta función determina si el diario es editable o no.
     * @return true: Si el diario es editable.
     * <BR>false: En el caso contrario.
     */
    public boolean isEditable()
    {
        return blnAsiDiaEdi;
    }
    
    public BigDecimal getTotalSumaCantidad(){
        return bgdTotSumCan;
    }

    public void setTotalSumaCantidad(BigDecimal bgdTotSumCan){
        this.bgdTotSumCan=bgdTotSumCan;
    }

    /**
     * Función que permite calcular el valor de la columna Total FOB/CFR para todos los datos cargados en la tabla
     * @return true: Si se pudo realizar correctamente el cálculo
     * <BR>false: En el caso contrario.
     */
    private boolean calcularC10_TotalFobCfr(){//          C10=C8*C9
        boolean blnRes=true;
        BigDecimal bgdCan=new BigDecimal(BigInteger.ZERO);//V1
        BigDecimal bgdPre=new BigDecimal(BigInteger.ZERO);//S8
        BigDecimal bgdTotFobCfr=new BigDecimal(BigInteger.ZERO);//V2
        try{
            for(int i=0; i<objTblMod.getRowCountTrue();i++){
                if((intTipNotPed==1) || (intTipNotPed==2) )
                    bgdCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_TON_MET)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_TON_MET).toString()));
                else
                    bgdCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_PZA_PED_EMB)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_PZA_PED_EMB).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_PZA_PED_EMB).toString()));

                bgdPre=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PRE_UNI)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PRE_UNI).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PRE_UNI).toString()));
                bgdTotFobCfr=bgdCan.multiply(bgdPre);
                objTblMod.setValueAt(bgdTotFobCfr, i, INT_TBL_DAT_TOT_FOB_CFR);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    //factor del flete
    private boolean calcularV2_FleteItem(){//          V2=V1/S8
        boolean blnRes=true;
        BigDecimal bgdTot_SumCan=new BigDecimal(BigInteger.ZERO);//S8
        bgdV2_FleItm=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValFle=new BigDecimal(BigInteger.ZERO);
        try{
            bgdValFle=new BigDecimal(objCom76_01.getFleteItem()==null?"0":(objCom76_01.getFleteItem().toString().equals("")?"0":objCom76_01.getFleteItem().toString()));
            if(bgdValFle.compareTo(new BigDecimal("0"))>0){
                if( (intTipNotPed==1) || (intTipNotPed==2)   ){
                    bgdTot_SumCan=new BigDecimal(objTblTot.getValueAt(0, INT_TBL_DAT_CAN_TON_MET)==null?"0":(objTblTot.getValueAt(0, INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objTblTot.getValueAt(0, INT_TBL_DAT_CAN_TON_MET).toString()));
                }
                else if( (intTipNotPed==3) || (intTipNotPed==4)   ){
                    bgdTot_SumCan=new BigDecimal(objTblTot.getValueAt(0, INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblTot.getValueAt(0, INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblTot.getValueAt(0, INT_TBL_DAT_TOT_FOB_CFR).toString()));
                }
                if(bgdTot_SumCan.compareTo(new BigDecimal("0"))>0)
                    bgdV2_FleItm=bgdValFle.divide(bgdTot_SumCan, 20, BigDecimal.ROUND_HALF_UP);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


    public BigDecimal getV2_FleItm() {
        return bgdV2_FleItm;
    }

    public void setV2_FleItm(BigDecimal bgdV2_FleItm) {
        this.bgdV2_FleItm = bgdV2_FleItm;
    }

    public boolean calcularC12_ValorFlete(){//          C12=C8*V2
        boolean blnRes=true;
        BigDecimal bgdCan=new BigDecimal(BigInteger.ZERO);//V1
        BigDecimal bgdValFle=new BigDecimal(BigInteger.ZERO);//V2
        try{
            for(int i=0; i<objTblMod.getRowCountTrue();i++){
                if( (intTipNotPed==1) || (intTipNotPed==2)   )
                    bgdCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_TON_MET)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_TON_MET).toString()));
                else if( (intTipNotPed==3) || (intTipNotPed==4)   )
                    bgdCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_TOT_FOB_CFR).toString()));

                bgdValFle=bgdCan.multiply(bgdV2_FleItm);
                objTblMod.setValueAt(bgdValFle, i, INT_TBL_DAT_VAL_FLE);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public boolean calcularC13_ValorCFR(){//          C13=C10+C12
        boolean blnRes=true;
        BigDecimal bgdTotFobCfr=new BigDecimal(BigInteger.ZERO);//V1
        BigDecimal bgdValFle=new BigDecimal(BigInteger.ZERO);//V2
        try{
            for(int i=0; i<objTblMod.getRowCountTrue();i++){
                bgdTotFobCfr=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_TOT_FOB_CFR).toString()));
                bgdValFle=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_FLE)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_FLE).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_FLE).toString()));
                objTblMod.setValueAt((bgdTotFobCfr.add(bgdValFle)), i, INT_TBL_DAT_VAL_CFR);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public boolean calcularV4_ItemPaganArancel(){//          V4=   (Σ arancel)>0  ->   Σ val.CFR
        boolean blnRes=true;
        BigDecimal bgdValAraFil=new BigDecimal(BigInteger.ZERO);//V1
        BigDecimal bgdValCfr=new BigDecimal(BigInteger.ZERO);//V2
        bgdValV4ItmPagAra=new BigDecimal(BigInteger.ZERO);//V2
        try{
            for(int i=0; i<objTblMod.getRowCountTrue();i++){
                bgdValAraFil=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_POR_ARA)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_POR_ARA).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_POR_ARA).toString()));
                if(bgdValAraFil.compareTo(new BigDecimal("0"))>0){
                    bgdValCfr=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CFR_ARA)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CFR_ARA).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CFR_ARA).toString()));
                    bgdValV4ItmPagAra=bgdValV4ItmPagAra.add(bgdValCfr);
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public boolean calcularC14_ValCfrAra(){//          Valor arancel>0 [(1+ columnaArancel/100) * Val.CFR]   sino   0    todo columna por columna
        boolean blnRes=true;
        BigDecimal bgdPorAra=new BigDecimal(BigInteger.ZERO);//columna Piezas
        BigDecimal bgdValCfr=new BigDecimal(BigInteger.ZERO);//columna Tot.Cos.
        BigDecimal bgdValCfrAra=new BigDecimal(BigInteger.ZERO);//columna Cantidad
        try{
            for(int i=0; i<objTblMod.getRowCountTrue();i++){
                bgdPorAra=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_POR_ARA)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_POR_ARA).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_POR_ARA).toString()));
                bgdValCfr=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CFR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CFR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CFR).toString()));

                if(bgdPorAra.compareTo(new BigDecimal("0"))>0){
                    //bgdValCfrAra=new BigDecimal("1").add((bgdPorAra.divide(new BigDecimal("100"), objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP))).multiply(bgdValCfr);
                    bgdValCfrAra=((bgdPorAra.divide(new BigDecimal("100"), objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP))).multiply(bgdValCfr);
                }
                else{
                    bgdValCfrAra=new BigDecimal(BigInteger.ZERO);
                }
                objTblMod.setValueAt(bgdValCfrAra, i, INT_TBL_DAT_VAL_CFR_ARA);

            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public boolean calcularV5_FactorArancel(){//          V5=   V4>0  ->   V3/V4
        boolean blnRes=true;
        bgdValV5FacAra=new BigDecimal(BigInteger.ZERO);
        try{
            if(bgdValV4ItmPagAra.compareTo(new BigDecimal("0"))>0){
                bgdValV5FacAra=objCom76_01.getDerechosArancelarios().divide(bgdValV4ItmPagAra, 20, BigDecimal.ROUND_HALF_UP);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public boolean calcularC15_TotalArancel(){//          Valor arancel>0 [(1+ columnaArancel/100) * Val.CFR]   sino   0    todo columna por columna
        boolean blnRes=true;
        BigDecimal bgdPorAra=new BigDecimal(BigInteger.ZERO);//columna Piezas
        BigDecimal bgdValCfrAra=new BigDecimal(BigInteger.ZERO);//columna Tot.Cos.
        BigDecimal bgdTotAra=new BigDecimal(BigInteger.ZERO);//columna Tot.Cos.
        try{
            for(int i=0; i<objTblMod.getRowCountTrue();i++){
                bgdPorAra=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_POR_ARA)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_POR_ARA).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_POR_ARA).toString()));
                bgdValCfrAra=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CFR_ARA)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CFR_ARA).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CFR_ARA).toString()));
                if(bgdValV5FacAra.compareTo(new BigDecimal("0"))>0){
                    if(bgdPorAra.compareTo(new BigDecimal("0"))>0){
                        bgdTotAra=bgdValCfrAra.multiply(bgdValV5FacAra);
                    }
                    else{
                         bgdTotAra=new BigDecimal("0");
                    }
                }
                else{
                    if(objCom76_01.getDerechosArancelarios().compareTo(new BigDecimal("0"))>0)
                         bgdTotAra=bgdValCfrAra.multiply((bgdPorAra.divide(new BigDecimal("100"), objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP)));
                    else
                         bgdTotAra=objUti.redondearBigDecimal(bgdValCfrAra, objParSis.getDecimalesBaseDatos());
                }
                objTblMod.setValueAt(bgdTotAra, i, INT_TBL_DAT_TOT_ARA);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public boolean calcularV15_FactorGastos(){
        boolean blnRes=true;
        BigDecimal bgdValTotGas=objCom76_01.getTotalGastos();
        BigDecimal bgdValTotCfr=new BigDecimal(BigInteger.ZERO);
        bgdValV15FacGas=new BigDecimal(BigInteger.ZERO);
        try{
            bgdValTotCfr=new BigDecimal(objTblTot.getValueAt(0, INT_TBL_DAT_VAL_CFR)==null?"0":(objTblTot.getValueAt(0, INT_TBL_DAT_VAL_CFR).toString().equals("")?"0":objTblTot.getValueAt(0, INT_TBL_DAT_VAL_CFR).toString()));
            if(bgdValTotCfr.compareTo(new BigDecimal("0"))>0){
                bgdValV15FacGas=bgdValTotGas.divide(bgdValTotCfr, 20, BigDecimal.ROUND_HALF_UP);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public boolean calcularC16_TotalGastos(){//          C15 = C13 * V15
        boolean blnRes=true;
        BigDecimal bgdValCfr=new BigDecimal(BigInteger.ZERO);//V2
        BigDecimal bgdValC15TotGas=new BigDecimal(BigInteger.ZERO);//V2
        try{
            for(int i=0; i<objTblMod.getRowCountTrue();i++){
                bgdValCfr=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CFR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CFR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CFR).toString()));
                bgdValC15TotGas=bgdValCfr.multiply(bgdValV15FacGas);
                objTblMod.setValueAt(bgdValC15TotGas, i, INT_TBL_DAT_TOT_GAS);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public boolean calcularC17_TotalCostos(){//          C16 = C13 + C14 + C15
        boolean blnRes=true;
        BigDecimal bgdValCfr=new BigDecimal(BigInteger.ZERO);//columna Val.CFR.
        BigDecimal bgdTotAra=new BigDecimal(BigInteger.ZERO);//columna Tot.Ara.
        BigDecimal bgdTotGas=new BigDecimal(BigInteger.ZERO);//columna Tot.Gas.
        BigDecimal bgdValC16TotCos=new BigDecimal(BigInteger.ZERO);//columna Tot.Cos.
        try{
            for(int i=0; i<objTblMod.getRowCountTrue();i++){
                bgdValCfr=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CFR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CFR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CFR).toString()));
                bgdTotAra=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_TOT_ARA)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_TOT_ARA).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_TOT_ARA).toString()));
                bgdTotGas=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_TOT_GAS)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_TOT_GAS).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_TOT_GAS).toString()));
                bgdValC16TotCos=bgdValCfr.add(bgdTotAra).add(bgdTotGas);
                objTblMod.setValueAt(bgdValC16TotCos, i, INT_TBL_DAT_TOT_COS);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public boolean calcularC18_CostoUnitario(){//          C11>0 (C17=C16/C11)   sino   (C17=C16/C8)   todo columna por columna
        boolean blnRes=true;
        BigDecimal bgdValPza=new BigDecimal(BigInteger.ZERO);//columna Piezas
        BigDecimal bgdTotCos=new BigDecimal(BigInteger.ZERO);//columna Tot.Cos.
        BigDecimal bgdTotCan=new BigDecimal(BigInteger.ZERO);//columna Cantidad
        BigDecimal bgdValC17CosUni=new BigDecimal(BigInteger.ZERO);//columna Cos.Uni.
        try{
            for(int i=0; i<objTblMod.getRowCountTrue();i++){
                bgdValPza=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_PZA_PED_EMB)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_PZA_PED_EMB).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_PZA_PED_EMB).toString()));
                bgdTotCos=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_TOT_COS)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_TOT_COS).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_TOT_COS).toString()));
                if(bgdValPza.compareTo(new BigDecimal("0"))>0){
                    bgdValC17CosUni=bgdTotCos.divide(bgdValPza, objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP);
                }
                else{
                    bgdTotCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_TON_MET)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_TON_MET).toString()));
                    if(bgdTotCan.compareTo(new BigDecimal("0"))>0){
                        bgdValC17CosUni=bgdTotCos.divide(bgdTotCan, objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP);
                    }
                }
                objTblMod.setValueAt(bgdValC17CosUni, i, INT_TBL_DAT_COS_UNI);

            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public void regenerarCalculos(){
        objTblTot.calcularTotales();
        bgdTotSumCan=new BigDecimal(objTblTot.getValueAt(0, INT_TBL_DAT_CAN_PZA_PED_EMB)==null?"0":(objTblTot.getValueAt(0, INT_TBL_DAT_CAN_PZA_PED_EMB).toString().equals("")?"0":objTblTot.getValueAt(0, INT_TBL_DAT_CAN_PZA_PED_EMB).toString()));
       
        calcularC10_TotalFobCfr();
        objTblTot.calcularTotales();
        calcularV2_FleteItem();
        calcularC12_ValorFlete();
        objTblTot.calcularTotales();
        calcularC13_ValorCFR();
        objTblTot.calcularTotales();
        
        calcularC14_ValCfrAra();
        objTblTot.calcularTotales();
        calcularV4_ItemPaganArancel();
        objTblTot.calcularTotales();
        calcularV5_FactorArancel();
        calcularC15_TotalArancel();
        objTblTot.calcularTotales();
        calcularV15_FactorGastos();
        calcularC16_TotalGastos();
        objTblTot.calcularTotales();
        calcularC17_TotalCostos();
        objTblTot.calcularTotales();
        calcularC18_CostoUnitario();
        objTblTot.calcularTotales();
        calcularV20PesoTotal();
        fireCom76_02Listener(new ZafAsiDiaEvent(this), INT_AFT_EDI_CEL);
    }

    public BigDecimal calcularV20PesoTotal(){//calcula el campo de peso total de la cabecera
        BigDecimal bgdCan=new BigDecimal(BigInteger.ZERO);//cantidad
        BigDecimal bgdPes=new BigDecimal(BigInteger.ZERO);//peso
        BigDecimal bgdPesTot=new BigDecimal(BigInteger.ZERO);//peso
        try{
            for(int i=0; i<objTblMod.getRowCountTrue();i++){
                bgdCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_PZA_PED_EMB)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_PZA_PED_EMB).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_PZA_PED_EMB).toString()));
                bgdPes=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES).toString()));
                bgdPesTot=bgdPesTot.add(bgdCan.multiply(bgdPes));
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            bgdPesTot=new BigDecimal(BigInteger.ZERO);
        }
        return bgdPesTot;
    }

    public ZafTblMod getTblModCom76_02() {
        return objTblMod;
    }

    public void setTblModCom76_02(ZafTblMod objTblMod) {
        this.objTblMod = objTblMod;
    }

    public String getFechaDocumento() {
        return strFechaDocumento;
    }

    public void setFechaDocumento(String strFechaDocumento) {
        this.strFechaDocumento = strFechaDocumento;
    }

    public String getFechaEmbarque() {
        return strFechaEmbarque;
    }

    public void setFechaEmbarque(String strFechaEmbarque) {
        this.strFechaEmbarque = strFechaEmbarque;
    }

    public void regenerarCalculoCfrAra(){
        calcularC14_ValCfrAra();
        objTblTot.calcularTotales();
    }

    public boolean asignarVectorModeloDatos(Vector vectorDatos){
        boolean blnRes=true;
        try{
            //Asignar vectores al modelo.
            objTblMod.setData(vectorDatos);
            tblDat.setModel(objTblMod);
            vecDat.clear();
            objTblTot.calcularTotales();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public boolean limpiarTablaCom76_02(){
        boolean blnRes=true;
        try{
            objTblMod.removeAllRows();
            objTblTot.calcularTotales();

            bgdV2_FleItm=new BigDecimal(BigInteger.ZERO);
            bgdValV4ItmPagAra=new BigDecimal(BigInteger.ZERO);
            bgdValV15FacGas=new BigDecimal(BigInteger.ZERO);
            bgdValV5FacAra=new BigDecimal(BigInteger.ZERO);
            intTipNotPed=0;
            arlDatFilEli.clear();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public void setTipoNotaPedido(int tipoNotaPedido){
        intTipNotPed=tipoNotaPedido;
    }


    /**
     * Esta función adiciona el listener que controlará los eventos que se generen sobre la clase "ZafCom76_02".
     * @param listener El objeto que implementa los métodos de la interface "ZafAsiDiaListener".
     */
    public void addCom76_02Listener(ZafAsiDiaListener listener)
    {
        objEveLisLis.add(ZafAsiDiaListener.class, listener);
    }


    /**
     * Esta función remueve el listener que controlaré los eventos que se generen sobre la clase "ZafAsiDia_v01".
     * @param listener El objeto que implementa los métodos de la interface "ZafAsiDiaListener".
     */
    public void removeCom76_02Listener(ZafAsiDiaListener listener)
    {
        objEveLisLis.remove(ZafAsiDiaListener.class, listener);
    }

    
    /**
     * Esta función dispara el listener adecuado de acuerdo a los argumentos recibidos.
     * @param evt El objeto "ZafAsiDiaEvent".
     * @param metodo El método que seré invocado.
     * Puede tomar uno de los siguientes valores:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Tipo de campo</I></TD><TD><I>Método</I></TD></TR>
     *     <TR><TD>INT_BEF_INS</TD><TD>Invoca al método "beforeInsertar" de la interface.</TD></TR>
     *     <TR><TD>INT_AFT_INS</TD><TD>Invoca al método "afterInsertar" de la interface.</TD></TR>
     *     <TR><TD>INT_BEF_CON</TD><TD>Invoca al método "beforeConsultar" de la interface.</TD></TR>
     *     <TR><TD>INT_AFT_CON</TD><TD>Invoca al método "afterConsultar" de la interface.</TD></TR>
     *     <TR><TD>INT_BEF_MOD</TD><TD>Invoca al método "beforeModificar" de la interface.</TD></TR>
     *     <TR><TD>INT_AFT_MOD</TD><TD>Invoca al método "afterModificar" de la interface.</TD></TR>
     *     <TR><TD>INT_BEF_ELI</TD><TD>Invoca al método "beforeEliminar" de la interface.</TD></TR>
     *     <TR><TD>INT_AFT_ELI</TD><TD>Invoca al método "afterEliminar" de la interface.</TD></TR>
     *     <TR><TD>INT_BEF_ANU</TD><TD>Invoca al método "beforeAnular" de la interface.</TD></TR>
     *     <TR><TD>INT_AFT_ANU</TD><TD>Invoca al método "afterAnular" de la interface.</TD></TR>
     * </TABLE>
     * </CENTER>
     */
    private void fireCom76_02Listener(ZafAsiDiaEvent evt, int metodo)
    {
        int i;
        Object[] obj=objEveLisLis.getListenerList();
        //Cada listener ocupa 2 elementos:
        //1)Es el listener class.
        //2)Es la instancia del listener.
        for (i=0;i<obj.length;i+=2)
        {
            if (obj[i]==ZafAsiDiaListener.class)
            {
                switch (metodo)
                {
                    case INT_BEF_INS:
                        ((ZafAsiDiaListener)obj[i+1]).beforeInsertar(evt);
                        break;
                    case INT_AFT_INS:
                        ((ZafAsiDiaListener)obj[i+1]).afterInsertar(evt);
                        break;
                    case INT_BEF_CON:
                        ((ZafAsiDiaListener)obj[i+1]).beforeConsultar(evt);
                        break;
                    case INT_AFT_CON:
                        ((ZafAsiDiaListener)obj[i+1]).afterConsultar(evt);
                        break;
                    case INT_BEF_MOD:
                        ((ZafAsiDiaListener)obj[i+1]).beforeModificar(evt);
                        break;
                    case INT_AFT_MOD:
                        ((ZafAsiDiaListener)obj[i+1]).afterModificar(evt);
                        break;
                    case INT_BEF_ELI:
                        ((ZafAsiDiaListener)obj[i+1]).beforeEliminar(evt);
                        break;
                    case INT_AFT_ELI:
                        ((ZafAsiDiaListener)obj[i+1]).afterEliminar(evt);
                        break;
                    case INT_BEF_ANU:
                        ((ZafAsiDiaListener)obj[i+1]).beforeAnular(evt);
                        break;
                    case INT_AFT_ANU:
                        ((ZafAsiDiaListener)obj[i+1]).afterAnular(evt);
                        break;
                    case INT_BEF_EDI_CEL:
                        ((ZafAsiDiaListener)obj[i+1]).beforeEditarCelda(evt);
                        break;
                    case INT_AFT_EDI_CEL:
                        ((ZafAsiDiaListener)obj[i+1]).afterEditarCelda(evt);
                        break;
                    case INT_BEF_CON_CTA:
                        ((ZafAsiDiaListener)obj[i+1]).beforeConsultarCuentas(evt);
                        break;
                    case INT_AFT_CON_CTA:
                        ((ZafAsiDiaListener)obj[i+1]).afterConsultarCuentas(evt);
                        break;
                }
            }
        }
    }

    public BigDecimal getValorTotalCosto() {
        bgdValTotCos=new BigDecimal(BigInteger.ZERO);
        bgdValTotCos=objUti.redondearBigDecimal(new BigDecimal(objTblTot.getValueAt(0, INT_TBL_DAT_TOT_COS)==null?"0":(objTblTot.getValueAt(0, INT_TBL_DAT_TOT_COS).toString().equals("")?"0":objTblTot.getValueAt(0, INT_TBL_DAT_TOT_COS).toString())), objParSis.getDecimalesBaseDatos());
        return bgdValTotCos;
    }
    
    public BigDecimal getValorTotalFacturaConIva() {
        BigDecimal bgdPorIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
        BigDecimal bgdValTotFacConIva = objUti.redondearBigDecimal(getValorTotalCosto().multiply(bgdPorIva), objParSis.getDecimalesMostrar() );
        return bgdValTotFacConIva;
    }

    public BigDecimal getCalcularPesoTotal(){
       BigDecimal bgdCan=new BigDecimal(BigInteger.ZERO);//V1
       BigDecimal bgdPes=new BigDecimal(BigInteger.ZERO);//S8
       bgdPesTot=new BigDecimal(BigInteger.ZERO);
       try{
           for(int i=0; i<objTblMod.getRowCountTrue();i++){
               bgdCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_PZA_PED_EMB)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_PZA_PED_EMB).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_PZA_PED_EMB).toString()));
               bgdPes=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES).toString()));
               bgdPesTot=objUti.redondearBigDecimal(bgdPesTot.add(bgdCan.multiply(bgdPes)), objParSis.getDecimalesBaseDatos());
           }
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       return bgdPesTot;
    }


    public boolean isBlnHayCamCom76_02() {
        return blnHayCam;
    }

    public void setBlnHayCamCamCom76_02(boolean blnHayCam) {
        this.blnHayCam = blnHayCam;
    }

    public ArrayList getArregloFilasEliminadas() {
        return arlDatFilEli;
    }

    public void setArregloFilasEliminadas(ArrayList arlDatFilEli) {
        this.arlDatFilEli = arlDatFilEli;
    }

    public void addArregloFilasEliminadas(ArrayList arlRegFilEliCom76_03) {
        this.arlDatFilEli.add(arlRegFilEliCom76_03);
    }


    /**Función usada para la nota de embarque, cuando se seleccionan items, se debe colocar el valor de la suma del flete pero
     * de los items seleccionados, se lee este valor y se lo setea en en ZafCom76_01.
     * @return valor del flete de los items seleccionados
     */
    public BigDecimal getValorTotalFlete() {
        BigDecimal bgdValFleItmSel=new BigDecimal(objTblTot.getValueAt(0, INT_TBL_DAT_VAL_FLE)==null?"0":(objTblTot.getValueAt(0, INT_TBL_DAT_VAL_FLE).toString().equals("")?"0":objTblTot.getValueAt(0, INT_TBL_DAT_VAL_FLE).toString()));
        return bgdValFleItmSel;
    }


    public ZafTblTot getTblTot() {
        return objTblTot;
    }


    public void setModoOperacionTooBar(char opcionTooBar){
        chrOpcManDatTooBar=opcionTooBar;
    }

    private boolean msgPermitirValorExcedidoEmbarque(){
        boolean blnRes=false;
        String strTit="", strMsg="";
        String strLin="";
        try{
            if( (chrOpcManDatTooBar=='x')  ||  (chrOpcManDatTooBar=='m')  ){//por modificacion
                strLin=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN).toString();
                if(!strLin.equals("I")){
                    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="<HTML>El valor ingresado es diferente al valor de la Nota de Pedido.<BR>¿Está seguro que desea continuar?</HTML>";
                    if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
                    {
                        blnRes=true;
                    }
                }
                else
                    blnRes=true;
            }
            else{//por insercion
                javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
                strTit="Mensaje del sistema Zafiro";
                strMsg="<HTML>El valor ingresado es mayor al rango permitido(10% del valor de la nota de pedido).<BR>¿Está seguro que desea continuar?</HTML>";
                if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
                {
                    blnRes=true;
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Función que permite obtener el valor del Total Fob/Cfr
     * @return BigDecimal El valor del Total Fob/Cfr
     */
    public BigDecimal getValorTotalFobCfr() {
        bgdValTotFobCfr=new BigDecimal(BigInteger.ZERO);
        bgdValTotFobCfr=objUti.redondearBigDecimal(new BigDecimal(objTblTot.getValueAt(0, INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblTot.getValueAt(0, INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblTot.getValueAt(0, INT_TBL_DAT_TOT_FOB_CFR).toString())), objParSis.getDecimalesBaseDatos());
        return bgdValTotFobCfr;
    }


    public BigDecimal getValorTotalArancel() {
        bgdValTotAra=new BigDecimal(BigInteger.ZERO);
        bgdValTotAra=objUti.redondearBigDecimal(new BigDecimal(objTblTot.getValueAt(0, INT_TBL_DAT_TOT_ARA)==null?"0":(objTblTot.getValueAt(0, INT_TBL_DAT_TOT_ARA).toString().equals("")?"0":objTblTot.getValueAt(0, INT_TBL_DAT_TOT_ARA).toString())), objParSis.getDecimalesBaseDatos());
        return bgdValTotAra;
    }

    /***
     * Función que permite ordenar los datos de la tabla por Código Alterno
     */
    public void ordenarDatPorCodAlt()
    {
        //Configurar JTable: Establecer la clase que controla el ordenamiento.
        objTblOrd=new ZafTblOrd(tblDat);
        objTblOrd.ordenar(INT_TBL_DAT_COD_ALT_ITM);
    }    

//    public boolean seteaColumnaPiezas(){
//        boolean blnRes=true;
//        try{
//           for(int i=0; i<objTblMod.getRowCountTrue();i++){
//               objTblMod.setValueAt("0", i, INT_TBL_DAT_PIE);
//            }
//        }
//        catch(Exception e){
//            objUti.mostrarMsgErr_F1(this, e);
//            blnRes=false;
//        }
//        return blnRes;
//    }
    
    
    

}