package Librerias.ZafImp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author sistemas3
 */
public class ZafImp
{
    //Constantes
    ArrayList arlRegCodVisBue, arlDatCodVisBue;
    final int INT_ARL_COD_VIS_BUE_COD_EMP=0;
    final int INT_ARL_COD_VIS_BUE_COD_LOC=1;
    final int INT_ARL_COD_VIS_BUE_COD_TIP_DOC=2;
    final int INT_ARL_COD_VIS_BUE_COD_VIS_BUE=3;
    final int INT_ARL_COD_VIS_BUE_COD_USR=4;
    final int INT_ARL_COD_VIS_BUE_NEC_AUT_PRE=5;
    final int INT_ARL_COD_VIS_BUE_VAL_VIS_BUE=6;
    
    ArrayList arlRegCabMovInv, arlDatCabMovInv;
    final int INT_ARL_CAB_MOV_INV_COD_EMP=0;
    final int INT_ARL_CAB_MOV_INV_COD_LOC=1;
    final int INT_ARL_CAB_MOV_INV_COD_TIP_DOC=2;
    final int INT_ARL_CAB_MOV_INV_DES_COR_TIP_DOC=3;
    final int INT_ARL_CAB_MOV_INV_NUM_VIS_BUE=4;
    final int INT_ARL_CAB_MOV_INV_COD_VIS_BUE=5;
    final int INT_ARL_CAB_MOV_INV_NEC_VIS_BUE_PRE=6;
    final int INT_ARL_CAB_MOV_INV_VAL_VIS_BUE=7;
    final int INT_ARL_CAB_MOV_INV_COD_EMP_CAB_MOV_INV=8;
    final int INT_ARL_CAB_MOV_INV_COD_LOC_CAB_MOV_INV=9;
    final int INT_ARL_CAB_MOV_INV_COD_TIP_DOC_CAB_MOV_INV=10;
    final int INT_ARL_CAB_MOV_INV_COD_DOC_CAB_MOV_INV=11;
    final int INT_ARL_CAB_MOV_INV_EST_AUT_CAB_MOV_INV=12;
    final int INT_ARL_CAB_MOV_INV_VAL_AUT_CAB_MOV_INV=13;
    final int INT_ARL_CAB_MOV_INV_COD_VIS_BUE_CAB_MOV_INV=14;
    
    ArrayList arlRegCodVisBueObl, arlDatCodVisBueObl;
    final int INT_ARL_COD_VIS_BUE_OBL_COD_EMP=0;
    final int INT_ARL_COD_VIS_BUE_OBL_COD_LOC=1;
    final int INT_ARL_COD_VIS_BUE_OBL_COD_TIP_DOC=2;
    final int INT_ARL_COD_VIS_BUE_OBL_COD_VIS_BUE=3;        
    
    //Otras Constantes
    public static final int INT_DAT_VAL_AUT_DOC_AJU=500;
    public static final int INT_COD_TIP_DOC_AJU_ING_IMP=295;
    public static final int INT_COD_TIP_DOC_AJU_COM_LOC=296;
    public static final int INT_COD_TIP_DOC_ING_IMP=14;
    public static final int INT_COD_TIP_DOC_COM_LOC=245;
    public static final int INT_COD_TIP_DOC_ORD_DIS_INM=203;
    public static final int INT_COD_TIP_DOC_DOC_POR_PAG=106;
    public static final int INT_COD_TIP_DOC_DOC_OPCOLO=246;
    
    public static final int INT_COD_TIP_DOC_PRO=299;                //Código de tipo de documento de Provisión
    public static final int INT_COD_TIP_DOC_AJU_PRO=302;            //Código de tipo de documento de Ajuste de Provisión
    public static final int INT_COD_TIP_DOC_PAG_IMP=303;            //Código de tipo de documento de Pago de Impuesto
    public static final int INT_COD_TIP_DOC_PAG_EXT=304;            //Código de tipo de documento de Pago a proveedores del exterior.
    public static final int INT_COD_TIP_DOC_OTR_MOV_BAN=175;        //Código de tipo de documento de Otros Movimientos Bancarios
    public static final int INT_COD_TIP_DOC_TRA_BAN_EXT=87;         //Código de tipo de documento de Transferencias Bancarias al Exterior
    public static final int INT_COD_TIP_DOC_TRA_BAN_ARA=88;         //Código de tipo de documento de Transferencias Bancarias Arancelarias
    public static final int INT_COD_TIP_DOC_AJU_NOT_PED_PED_EMB=306;//Código de tipo de documento de por Ajuste de Nota de Pedido por Pedido Embarcado
    
    //Código de menú
    public static final int INT_COD_MNU_PRG_AJU_INV=3460;
    public static final int INT_COD_MNU_PRG_ING_IMP=2889;
    public static final int INT_COD_MNU_PRG_COM_LOC=4355;
    public static final int INT_COD_MNU_PRG_SOTRINI=3456;
    public static final int INT_COD_MNU_PRG_SOTRINC=4372;     
    public static final int INT_COD_MNU_PRG_DXP_COM_LOC=4386;   
    public static final int INT_COD_MNU_PRG_DXP_ING_IMP=3932;
    public static final int INT_COD_MNU_PRG_CFG_ING_IMP=2733;
    public static final int INT_COD_MNU_PRG_CFG_COM_LOC=4320;
    
    //Configuraciones
    public static final int INT_COD_CFG_IMP_ISD=5; /* Código de Configuración de Impuesto: ISD. */
    public static final int INT_COD_CAR_PAG_ISD_CRE_TRI=23;
    public static final int INT_COD_CAR_PAG_ISD_GTO=26;
    
    //Variables
    private ZafUtil objUti;
    private ZafParSis objParSis;
    private java.awt.Component cmpPad;

    private int intNumVisBueObl;
    public int intCodDocPro;
    
    private BigDecimal bgdPorISD;
    
    //Tablas y campos de tabla de Documentos Relacionados a Movimientos por Importaciones
    public String strNomTblDocMovImp;
    public String strCodEmpMovImp;
    public String strCodLocMovImp;
    public String strCodTipDocMovImp;
    public String strCodDocMovImp;
    public String strCodRegMovImp;
    public String strCodCarPagMovImp;
    //Relacional
    public String strNomTblDocRelMovImp;
    public String strCodEmpRelMovImp;
    public String strCodLocRelMovImp;
    public String strCodTipDocRelMovImp;
    public String strCodDocRelMovImp;
    public String strCodRegRelMovImp;
    public String strCodCarPagRelMovImp;
    
    private String strEstDoc=""; 
    private String strSQL="";
    public String strVer="v0.14.2";
    
    ArrayList arlRegInsPed, arlDatInsPed;
    public final int INT_ARL_INS_PED_COD_EMP=0;
    public final int INT_ARL_INS_PED_COD_LOC=1;
    public final int INT_ARL_INS_PED_COD_TIP_DOC=2;
    public final int INT_ARL_INS_PED_COD_DOC=3;
    public final int INT_ARL_INS_PED_NUM_PED=4;    
    public final int INT_ARL_INS_PED_INS_IMP=5;
    public final int INT_ARL_INS_PED_COD_IMP=6;
    
    private String strSQLDatPedUltIns;   //Para obtener todos los pedidos en su última instancia.
    private String strSQLPedSelUltIns;   //Para obtener la última instancia de un pedido seleccionado.
    private String strSQLNotPedOpen;     //Para obtener Notas de pedidos abiertas
    private String strSQLPedEmbOpen;     //Para obtener Notas de pedidos abiertas
    private String strSQLIngImpOpen;     //Para obtener Notas de pedidos abiertas
    
    
    /**
     * Constructor
     * @param obj
     * @param parent 
     */
    public ZafImp(ZafParSis obj, java.awt.Component parent)
    {
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            cmpPad=parent;
            //arlDatCodVisBue=new ArrayList();
            arlDatCodVisBue=new ArrayList();
            arlDatCabMovInv=new ArrayList();
            arlDatCodVisBueObl=new ArrayList();
            arlDatInsPed=new ArrayList();
            //Tabla
            strNomTblDocMovImp="";
            strNomTblDocRelMovImp="";
            //Campos
            strCodEmpRelMovImp="";
            strCodLocMovImp="";
            strCodTipDocMovImp="";
            strCodDocMovImp="";
            strCodEmpRelMovImp="";//Relacion
            strCodLocRelMovImp="";//Relacion
            strCodTipDocRelMovImp="";//Relacion
            strCodDocRelMovImp="";//Relacion
            strCodRegRelMovImp="";//Relacion
            strCodCarPagRelMovImp="";//Relacion
            //System.out.println("ZafImp:   v0.1.7);
        }
        catch (CloneNotSupportedException e){
            System.out.println("ZafImp: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
    }
    
    /**
     * Función que permite conocer si existen solicitudes de transferencia sin transferencias asociadas
     * @return true: Si existen solicitudes de transferencias sin transferencias a bodegas realizadas
     * <BR> false  : Caso contrario
     */
    public boolean isSolicitudTransferencia(Connection conRef, int intCodigoEmpresa, int intCodigoLocal, int intCodigoTipoDocumento, int intCodigoDocumento, int intCodigoItemMaestro){
        boolean blnRes=false;
        Statement stmSolTrs;
        ResultSet rstSolTrs;
        try{
            if(conRef!=null){
                //--SOLICITUD/TRANSFERENCIA
                stmSolTrs=conRef.createStatement();
                strSQL="";
                strSQL+=" SELECT b2.co_emp AS co_empSolTrs, b2.co_loc AS co_locSolTrs, b2.co_tipDoc AS co_tipDocSolTrs";
                strSQL+=" , CASE WHEN b2.nd_can IS NULL THEN 0 ELSE b2.nd_can END AS nd_canSolTrs";
                strSQL+=" , b3.co_empTrs, b3.co_locTrs, b3.co_tipDocTrs, b3.co_docTrs";
                strSQL+=" , CASE WHEN ABS(b3.nd_canTrs) IS NULL THEN 0 ELSE ABS(b3.nd_canTrs) END AS nd_canTrs";
                strSQL+=" , b3.co_itmMae, b3.co_itm AS co_itmEmp";
                strSQL+=" FROM(";
                //SOLICITUD
                strSQL+=" 	SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a4.co_itm, a4.nd_can, a6.co_itmMae";
                strSQL+=" 	, a3.co_empRelCabMovInv, a3.co_locRelCabMovInv, a3.co_tipDocRelCabMovInv, a3.co_docRelCabMovInv, a4.co_reg";
                strSQL+=" 	FROM tbm_cabMovInv AS a1 INNER JOIN tbr_cabSolTraInvCabMovInv AS a3";
                strSQL+=" 			ON a1.co_emp=a3.co_empRelCabMovInv AND a1.co_loc=a3.co_locRelCabMovInv";
                strSQL+=" 			AND a1.co_tipDoc=a3.co_tipDocRelCabMovInv AND a1.co_doc=a3.co_docRelCabMovInv";
                strSQL+=" 	INNER JOIN tbm_cabSolTraInv AS a2";
                strSQL+=" 	ON a2.co_emp=a3.co_empRelCabSolTraInv AND a2.co_loc=a3.co_locRelCabSolTraInv AND a2.co_tipDoc=a3.co_tipDocRelCabSolTraInv AND a2.co_doc=a3.co_docRelCabSolTraInv";
                strSQL+=" 	INNER JOIN tbm_detSolTraInv AS a4";
                strSQL+=" 	ON a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc AND a2.co_doc=a4.co_doc";
                strSQL+=" 	INNER JOIN tbm_inv AS a5 ON a4.co_emp=a5.co_emp AND a4.co_itm=a5.co_itm";
                strSQL+=" 	INNER JOIN tbm_equInv AS a6 ON a5.co_emp=a6.co_emp AND a5.co_itm=a6.co_itm";
                strSQL+=" 	WHERE a3.co_empRelCabMovInv=" + intCodigoEmpresa + " AND a3.co_locRelCabMovInv=" + intCodigoLocal + "";
                strSQL+=" 	AND a3.co_tipDocRelCabMovInv=" + intCodigoTipoDocumento + " AND a3.co_docRelCabMovInv=" + intCodigoDocumento + "";
                strSQL+=" 	AND a1.st_reg='A' AND a2.st_reg='A'";
                strSQL+=" 	AND a2.st_aut='A'";//AUTORIZADO
                strSQL+=" 	ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a6.co_itmMae";
                strSQL+=" ) AS b2";
                strSQL+=" LEFT OUTER JOIN(";
                //TRANSFERENCIA
                strSQL+=" 	SELECT a1.co_empRelCabSolTraInv, a1.co_locRelCabSolTraInv, a1.co_tipDocRelCabSolTraInv, a1.co_docRelCabSolTraInv";
                strSQL+=" 	, SUM(a1.nd_can) AS nd_canTrs, a1.tx_codAlt, a1.co_itmMae, a1.tx_codAlt2, a1.co_reg, a1.co_itm";
                strSQL+=" 	, a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv";
                strSQL+=" 	, a1.co_emp AS co_empTrs, a1.co_loc AS co_locTrs, a1.co_tipDoc AS co_tipDocTrs, a1.co_doc AS co_docTrs";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a2.tx_codAlt, a4.co_itmMae, a2.nd_can, a2.co_itm";
                strSQL+=" 		, a3.tx_codAlt2, a3.tx_nomItm, a2.tx_unimed, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc";
                strSQL+=" 		, a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel";
                strSQL+=" 		, a5.co_empRel AS co_empRelCabMovInv, a5.co_locRel AS co_locRelCabMovInv, a5.co_tipDocRel AS co_tipDocRelCabMovInv, a5.co_docRel AS co_docRelCabMovInv";
                strSQL+=" 		, a1.co_empRelCabSolTraInv, a1.co_locRelCabSolTraInv, a1.co_tipDocRelCabSolTraInv, a1.co_docRelCabSolTraInv";
                strSQL+=" 		FROM (tbr_cabMovInv AS a5 INNER JOIN tbm_cabMovInv AS a1";
                strSQL+=" 			ON a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc AND a5.co_tipDoc=a1.co_tipDoc AND a5.co_doc=a1.co_doc)";
                strSQL+=" 		INNER JOIN tbm_detMovInv AS a2";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 		INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                strSQL+=" 		INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                strSQL+=" 		WHERE a2.nd_can<0";
                strSQL+=" 		AND a5.co_empRel=" + intCodigoEmpresa + " AND a5.co_locRel=" + intCodigoLocal + "";
                strSQL+=" 	        AND a5.co_tipDocRel=" + intCodigoTipoDocumento + " AND a5.co_docRel=" + intCodigoDocumento + "";
                strSQL+=" 	) AS a1";
                strSQL+=" 	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 	, a1.co_empRelCabSolTraInv, a1.co_locRelCabSolTraInv, a1.co_tipDocRelCabSolTraInv, a1.co_docRelCabSolTraInv";
                strSQL+=" 	, a1.co_reg, a1.tx_codAlt, a1.co_itmMae, a1.tx_codAlt2, a1.co_itm";
                strSQL+=" 	, a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv";
                strSQL+=" 	ORDER BY a1.co_itmMae";
                strSQL+=" ) AS b3";
                strSQL+=" ON b2.co_emp=b3.co_empRelCabSolTraInv AND b2.co_loc=b3.co_locRelCabSolTraInv";
                strSQL+=" AND b2.co_tipDoc=b3.co_tipDocRelCabSolTraInv AND b2.co_doc=b3.co_docRelCabSolTraInv AND b2.co_itmMae=b3.co_itmMae";
                strSQL+=" WHERE (CASE WHEN b2.nd_can IS NULL THEN 0 ELSE b2.nd_can END) > (CASE WHEN ABS(b3.nd_canTrs) IS NULL THEN 0 ELSE ABS(b3.nd_canTrs) END)";
                strSQL+=" ORDER BY b3.co_itmMae";
                rstSolTrs=stmSolTrs.executeQuery(strSQL);
                if(rstSolTrs.next()){
                    blnRes=true;
                }
                stmSolTrs.close();
                stmSolTrs=null;
                rstSolTrs.close();
                rstSolTrs=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite conocer si el Pedido tiene items con cantidades sobrantes
     * @param conRef
     * @param intCodigoEmpresa       : Código de empresa de Pedido
     * @param intCodigoLocal         : Código de local de Pedido
     * @param intCodigoTipoDocumento : Código de tipo de documento de Pedido
     * @param intCodigoDocumento     : Código de documento de Pedido
     * @return true                  : Existen items con cantidades sobrantes
     * <BR> false                    : Caso contrario
     */
    public boolean isConteoPendienteTransferir(Connection conRef, int intCodigoEmpresa, int intCodigoLocal, int intCodigoTipoDocumento, int intCodigoDocumento){
        boolean blnRes=false;
        Statement stmConPenTrs;
        ResultSet rstConPenTrs;
        try{
            if(conRef!=null){
                //--SOBRANTE
                stmConPenTrs=conRef.createStatement();
                strSQL="";
                strSQL+=" SELECT b1.co_empCon, b1.co_locCon, b1.co_tipdocCon, b1.co_docCon, b1.co_regCon, b1.nd_canCon, b1.co_itmMae, b1.co_itmGrp";
                strSQL+=" , b1.co_empSolTrs, b1.co_locSolTrs, b1.co_tipDocSolTrs";
                strSQL+=" , b1.nd_canSolTrs, b1.co_empTrs, b1.co_locTrs, b1.co_tipDocTrs";
                strSQL+=" , b1.co_docTrs, b1.nd_canTrs";
                strSQL+=" , b1.co_empIngImp, b1.co_locIngImp, b1.co_tipDocIngImp, b1.co_docIngImp, b1.co_regIngImp";
                strSQL+=" , b1.ne_numDocIngImp, b1.fe_docIngImp, b1.tx_numDoc2IngImp, b1.nd_canIngImp, b1.nd_cosUni";
                strSQL+=" , b1.tx_nomItm, b1.tx_uniMed, b1.nd_preUniImp, b1.nd_pesItmKgr, b1.tx_codAlt, b1.tx_codAlt2";
                strSQL+=" FROM(";
                strSQL+=" 	 SELECT b1.co_emp AS co_empCon, b1.co_loc AS co_locCon, b1.co_tipdoc AS co_tipdocCon, b1.co_doc AS co_docCon";
                strSQL+=" 	 , b1.co_reg AS co_regCon, CASE WHEN b1.nd_stkCon IS NULL THEN 0 ELSE b1.nd_stkCon END AS nd_canCon, b1.co_itmMae, b1.co_itm AS co_itmGrp";
                strSQL+=" 	 , b2.co_emp AS co_empSolTrs, b2.co_loc AS co_locSolTrs, b2.co_tipDoc AS co_tipDocSolTrs";
                strSQL+=" 	 , CASE WHEN b2.nd_can IS NULL THEN 0 ELSE b2.nd_can END AS nd_canSolTrs";
                strSQL+=" 	 , b3.co_empRel AS co_empTrs, b3.co_locRel AS co_locTrs, b3.co_tipdocRel AS co_tipDocTrs";
                strSQL+=" 	 , b3.co_docRel AS co_docTrs, CASE WHEN b3.nd_canTrs IS NULL THEN 0 ELSE b3.nd_canTrs END AS nd_canTrs";
                strSQL+=" 	 , b4.co_emp AS co_empIngImp, b4.co_loc AS co_locIngImp, b4.co_tipDoc AS co_tipDocIngImp, b4.co_doc AS co_docIngImp, b4.co_reg AS co_regIngImp";
                strSQL+=" 	 , b4.ne_numDoc AS ne_numDocIngImp, b4.fe_doc AS fe_docIngImp, b4.tx_numDoc2 AS tx_numDoc2IngImp";
                strSQL+=" 	 , CASE WHEN b4.nd_can IS NULL THEN 0 ELSE b4.nd_can END AS nd_canIngImp, b4.nd_cosUni";
                strSQL+=" 	 , b4.tx_nomItm, b4.tx_uniMed, b4.nd_preUniImp, b4.nd_pesItmKgr, b4.tx_codAlt, b4.tx_codAlt2";
                strSQL+=" 	 FROM(";
                //CONTEO
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a8.nd_stkCon,a6.co_itmMae, a2.co_itm";
                strSQL+=" 		, a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel";
                strSQL+=" 		FROM tbm_cabOrdConInv AS a1";
                strSQL+=" 		INNER JOIN (tbm_detOrdConInv AS a2 INNER JOIN tbm_conInv AS a8";
                strSQL+=" 			   ON a2.co_emp=a8.co_emp AND a2.co_loc=a8.co_locrel AND a2.co_tipdoc=a8.co_tipdocrel AND a2.co_doc=a8.co_docrel AND a2.co_reg=a8.co_regrel    )";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 		INNER JOIN (tbm_invBod AS a3 INNER JOIN";
                strSQL+=" 				 (tbm_inv AS a4 INNER JOIN tbm_var AS a5 ON a4.co_uni=a5.co_reg)";
                strSQL+=" 						     INNER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm";
                strSQL+=" 						ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm)";
                strSQL+=" 		ON a2.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod AND a2.co_itm=a3.co_itm";
                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" 		AND a1.co_tipDocRel IN(14,245) AND a1.st_reg='A'";
                strSQL+=" 		AND a1.co_empRel=" + intCodigoEmpresa + " AND a1.co_locRel=" + intCodigoLocal + "";
                strSQL+="               AND a1.co_tipDocRel=" + intCodigoTipoDocumento + " AND a1.co_docRel=" + intCodigoDocumento + "";
                strSQL+=" 	 ) AS b1";
                strSQL+="	 LEFT OUTER JOIN(";
                //SOLICITUD
                strSQL+="           SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, SUM(b1.nd_can) AS nd_can, b1.co_itmMae";
                strSQL+="           , b1.co_empRelCabMovInv, b1.co_locRelCabMovInv, b1.co_tipDocRelCabMovInv, b1.co_docRelCabMovInv";
                strSQL+="           FROM(";
                strSQL+=" 		SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.ne_numdoc, a2.fe_doc, a2.tx_obs1";
                strSQL+=" 		, a4.co_reg, a4.co_itm, a4.nd_can, a6.co_itmMae";
                strSQL+=" 		, a3.co_empRelCabMovInv, a3.co_locRelCabMovInv, a3.co_tipDocRelCabMovInv, a3.co_docRelCabMovInv";
                strSQL+=" 		FROM tbm_cabMovInv AS a1 INNER JOIN tbr_cabSolTraInvCabMovInv AS a3";
                strSQL+=" 				ON a1.co_emp=a3.co_empRelCabMovInv AND a1.co_loc=a3.co_locRelCabMovInv";
                strSQL+=" 				AND a1.co_tipDoc=a3.co_tipDocRelCabMovInv AND a1.co_doc=a3.co_docRelCabMovInv";
                strSQL+=" 		INNER JOIN tbm_cabSolTraInv AS a2";
                strSQL+=" 		ON a2.co_emp=a3.co_empRelCabSolTraInv AND a2.co_loc=a3.co_locRelCabSolTraInv AND a2.co_tipDoc=a3.co_tipDocRelCabSolTraInv AND a2.co_doc=a3.co_docRelCabSolTraInv";
                strSQL+=" 		INNER JOIN tbm_detSolTraInv AS a4";
                strSQL+="		ON a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc AND a2.co_doc=a4.co_doc";
                strSQL+=" 		INNER JOIN tbm_inv AS a5 ON a4.co_emp=a5.co_emp AND a4.co_itm=a5.co_itm";
                strSQL+=" 		INNER JOIN tbm_equInv AS a6 ON a5.co_emp=a6.co_emp AND a5.co_itm=a6.co_itm";
                strSQL+=" 		WHERE a3.co_empRelCabMovInv=" + intCodigoEmpresa + " AND a3.co_locRelCabMovInv=" + intCodigoLocal + "";
                strSQL+="               AND a3.co_tipDocRelCabMovInv=" + intCodigoTipoDocumento + " AND a3.co_docRelCabMovInv=" + intCodigoDocumento + "";
                strSQL+=" 		AND a1.st_reg='A' AND a2.st_reg='A'";
                strSQL+="		AND a2.st_aut='A'";//AUTORIZADO
                strSQL+="           ) AS b1";
                strSQL+="           GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_itmMae";
                strSQL+="           , b1.co_empRelCabMovInv, b1.co_locRelCabMovInv, b1.co_tipDocRelCabMovInv, b1.co_docRelCabMovInv";
                strSQL+="	 ) AS b2";
                strSQL+="	 ON b1.co_empRel=b2.co_empRelCabMovInv AND b1.co_locRel=b2.co_locRelCabMovInv AND b1.co_tipDocRel=b2.co_tipDocRelCabMovInv AND b1.co_docRel=b2.co_docRelCabMovInv AND b1.co_itmMae=b2.co_itmMae";
                strSQL+="	 LEFT OUTER JOIN(";
                //TRANSFERENCIA
                strSQL+=" 		SELECT /*a1.co_emp, a1.co_loc, a1.co_tipDoc";
                strSQL+="               ,*/ a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, SUM(a1.nd_can) AS nd_canTrs";
                strSQL+=" 		, a1.co_itmMae, a1.tx_codAlt2";
                strSQL+=" 		, a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv";
                strSQL+=" 		FROM(";
                strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a2.tx_codAlt, a4.co_itmMae, ABS(a2.nd_can) AS nd_can, a2.co_itm";
                strSQL+=" 			, a3.tx_codAlt2, a3.tx_nomItm, a2.tx_unimed, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc";
                strSQL+=" 			, a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel";
                strSQL+=" 			, a5.co_empRel AS co_empRelCabMovInv, a5.co_locRel AS co_locRelCabMovInv, a5.co_tipDocRel AS co_tipDocRelCabMovInv, a5.co_docRel AS co_docRelCabMovInv";
                strSQL+=" 			FROM (tbr_cabMovInv AS a5 INNER JOIN tbm_cabMovInv AS a1";
                strSQL+=" 				ON a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc AND a5.co_tipDoc=a1.co_tipDoc AND a5.co_doc=a1.co_doc)";
                strSQL+=" 			INNER JOIN tbm_detMovInv AS a2";
                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 			INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                strSQL+=" 			INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                strSQL+=" 			WHERE a2.nd_can<0";
                strSQL+="			AND a5.co_empRel=" + intCodigoEmpresa + " AND a5.co_locRel=" + intCodigoLocal + "";
                strSQL+="                       AND a5.co_tipDocRel=" + intCodigoTipoDocumento + " AND a5.co_docRel=" + intCodigoDocumento + "";
                strSQL+=" 		) AS a1";
                strSQL+=" 		GROUP BY /*a1.co_emp, a1.co_loc, a1.co_tipDoc";
                strSQL+="               ,*/ a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, a1.co_itmMae, a1.tx_codAlt2";
                strSQL+=" 		, a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv";
                strSQL+=" 	 ) AS b3";
                strSQL+=" 	 ON b1.co_empRel=b3.co_empRel AND b1.co_locRel=b3.co_locRel AND b1.co_tipDocRel=b3.co_tipDocRel AND b1.co_docRel=b3.co_docRel AND b1.co_itmMae=b3.co_itmMae";
                strSQL+=" 	 LEFT OUTER JOIN(";
                //PEDIDO
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a1.tx_numDoc2";
                strSQL+=" 		, a2.nd_can, a4.co_itmMae, a2.nd_cosUni, a2.tx_codAlt, a2.tx_codAlt2";
                strSQL+=" 		, a2.tx_nomItm, a2.tx_uniMed, a2.nd_preUniImp, a3.nd_pesItmKgr";
                strSQL+=" 		FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 		INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                strSQL+=" 		INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                strSQL+=" 		WHERE a1.co_emp=" + intCodigoEmpresa + " AND a1.co_loc=" + intCodigoLocal + "";
                strSQL+="               AND a1.co_tipDoc=" + intCodigoTipoDocumento + " AND a1.co_doc=" + intCodigoDocumento + "";
                strSQL+=" 		AND a1.st_reg='A'";
                strSQL+=" 	 ) AS b4";
                strSQL+=" 	 ON b1.co_empRel=b4.co_emp AND b1.co_locRel=b4.co_loc AND b1.co_tipDocRel=b4.co_tipDoc AND b1.co_docRel=b4.co_doc AND b1.co_itmMae=b4.co_itmMae";
                strSQL+=" 	 WHERE  b1.nd_stkCon>0";
                strSQL+=" 	 ORDER BY b1.co_reg";
                strSQL+=" ) AS b1";
                strSQL+=" WHERE (CASE WHEN (b1.nd_canIngImp>=b1.nd_canCon) THEN b1.nd_canCon ELSE b1.nd_canIngImp END)>b1.nd_canTrs";
                strSQL+=" ORDER BY b1.co_regCon";
                System.out.println("isConteoPendienteTransferir: " + strSQL);
                rstConPenTrs=stmConPenTrs.executeQuery(strSQL);
                if(rstConPenTrs.next()){
                    blnRes=true;
                    mostrarMsgInf("Existen conteos pendientes de generar solicitud/transferencia");
                }
                stmConPenTrs.close();
                stmConPenTrs=null;
                rstConPenTrs.close();
                rstConPenTrs=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    
    
    
    /**
     * Función que permite conocer si el Pedido tiene items con cantidades contadas
     * @param conRef
     * @param intCodigoEmpresa       : Código de empresa de Pedido
     * @param intCodigoLocal         : Código de local de Pedido
     * @param intCodigoTipoDocumento : Código de tipo de documento de Pedido
     * @param intCodigoDocumento     : Código de documento de Pedido
     * @param intCodigoItemMaestro   : Código de item maestro
     * @return true                  : Existen items con cantidades sobrantes
     * <BR> false                    : Caso contrario
     */
    public boolean isConteo(Connection conRef, int intCodigoEmpresa, int intCodigoLocal, int intCodigoTipoDocumento, int intCodigoDocumento, int intCodigoItemMaestro){
        boolean blnRes=false;
        Statement stmCon;
        ResultSet rstCon;
        try{
            if(conRef!=null){
                stmCon=conRef.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a8.nd_stkCon,a6.co_itmMae, a2.co_itm";
                strSQL+=" , a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel";
                strSQL+=" FROM tbm_cabOrdConInv AS a1";
                strSQL+=" INNER JOIN (tbm_detOrdConInv AS a2 INNER JOIN tbm_conInv AS a8";
                strSQL+="            ON a2.co_emp=a8.co_emp AND a2.co_loc=a8.co_locrel AND a2.co_tipdoc=a8.co_tipdocrel AND a2.co_doc=a8.co_docrel AND a2.co_reg=a8.co_regrel    )";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN (tbm_invBod AS a3 INNER JOIN";
                strSQL+="                  (tbm_inv AS a4 INNER JOIN tbm_var AS a5 ON a4.co_uni=a5.co_reg)";
                strSQL+="                                      INNER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm";
                strSQL+="                                 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm)";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod AND a2.co_itm=a3.co_itm";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDocRel IN(14,245) AND a1.st_reg='A'";
                strSQL+=" AND a1.co_empRel=" + intCodigoEmpresa + " AND a1.co_locRel=" + intCodigoLocal + "";
                strSQL+=" AND a1.co_tipDocRel=" + intCodigoTipoDocumento + " AND a1.co_docRel=" + intCodigoDocumento + "";
                strSQL+=" AND a6.co_itmMae=" + intCodigoItemMaestro + "";
                strSQL+=" AND a8.nd_stkCon>0";
                rstCon=stmCon.executeQuery(strSQL);
                if(rstCon.next()){
                    blnRes=true;
                }
                stmCon.close();
                stmCon=null;
                rstCon.close();
                rstCon=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    

    
    /**
     * Función que permite conocer si el Pedido tiene items con cantidades sobrantes
     * @param conRef
     * @param intCodigoEmpresa       : Código de empresa de Pedido
     * @param intCodigoLocal         : Código de local de Pedido
     * @param intCodigoTipoDocumento : Código de tipo de documento de Pedido
     * @param intCodigoDocumento     : Código de documento de Pedido
     * @param intCodigoItemMaestro   : Código de item maestro
     * @return true                  : Existen items con cantidades sobrantes
     * <BR> false                    : Caso contrario
     */
    public boolean isPedidoSobrante(Connection conRef, int intCodigoEmpresa, int intCodigoLocal, int intCodigoTipoDocumento, int intCodigoDocumento, int intCodigoItemMaestro){
        boolean blnRes=false;
        Statement stmPedSob;
        ResultSet rstPedSob;
        try{
            if(conRef!=null){
                //--SOBRANTE
                stmPedSob=conRef.createStatement();
                strSQL="";
                strSQL+=" SELECT b2.co_emp AS co_empIngImp, b2.co_loc AS co_locIngImp, b2.co_tipDoc AS co_tipDocIngImp, b2.co_doc AS co_docIngImp, b2.co_reg AS co_regIngImp";
                strSQL+=" , b2.ne_numDoc AS ne_numDocIngImp, b2.fe_doc AS fe_docIngImp, b2.tx_numDoc2 AS tx_numDoc2IngImp";
                strSQL+=" , CASE WHEN b2.nd_can IS NULL THEN 0 ELSE b2.nd_can END AS nd_canIngImp, b2.nd_cosUni";
                strSQL+=" , b2.tx_nomItm, b2.tx_uniMed, b2.nd_preUniImp, b2.nd_pesItmKgr, b2.tx_codAlt, b2.tx_codAlt2";
                strSQL+=" , b1.co_emp AS co_empCon, b1.co_loc AS co_locCon, b1.co_tipdoc AS co_tipdocCon, b1.co_doc AS co_docCon";
                strSQL+=" , b1.co_reg AS co_regCon, CASE WHEN b1.nd_stkCon IS NULL THEN 0 ELSE b1.nd_stkCon END AS nd_canCon, b1.co_itmMae, b1.co_itm AS co_itmGrp";
                strSQL+=" FROM(";
                //PEDIDO
                strSQL+="        SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a1.tx_numDoc2";
                strSQL+="        , a2.nd_can, a4.co_itmMae, a2.nd_cosUni, a2.tx_codAlt, a2.tx_codAlt2";
                strSQL+="        , a2.tx_nomItm, a2.tx_uniMed, a2.nd_preUniImp, a3.nd_pesItmKgr";
                strSQL+="        FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2";
                strSQL+="        ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="        INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                strSQL+="        INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                strSQL+="        WHERE a1.co_emp=" + intCodigoEmpresa + " AND a1.co_loc=" + intCodigoLocal + "";
                strSQL+="        AND a1.co_tipDoc=" + intCodigoTipoDocumento + " AND a1.co_doc=" + intCodigoDocumento + "";
                strSQL+="        AND a4.co_itmMae=" + intCodigoItemMaestro + "";
                strSQL+="        AND a1.st_reg='A'";
                strSQL+=" ) AS b2";
                strSQL+=" LEFT OUTER JOIN(";
                //CONTEO
                strSQL+="        SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a8.nd_stkCon,a6.co_itmMae, a2.co_itm";
                strSQL+="        , a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel";
                strSQL+="        FROM tbm_cabOrdConInv AS a1";
                strSQL+="        INNER JOIN (tbm_detOrdConInv AS a2 INNER JOIN tbm_conInv AS a8";
                strSQL+="                   ON a2.co_emp=a8.co_emp AND a2.co_loc=a8.co_locrel AND a2.co_tipdoc=a8.co_tipdocrel AND a2.co_doc=a8.co_docrel AND a2.co_reg=a8.co_regrel    )";
                strSQL+="        ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="        INNER JOIN (tbm_invBod AS a3 INNER JOIN";
                strSQL+="                         (tbm_inv AS a4 INNER JOIN tbm_var AS a5 ON a4.co_uni=a5.co_reg)";
                strSQL+="                                             INNER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm";
                strSQL+="                                        ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm)";
                strSQL+="        ON a2.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod AND a2.co_itm=a3.co_itm";
                strSQL+="        WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="        AND a1.co_tipDocRel IN(14,245) AND a1.st_reg='A'";
                strSQL+="        AND a1.co_empRel=" + intCodigoEmpresa + " AND a1.co_locRel=" + intCodigoLocal + "";
                strSQL+="        AND a1.co_tipDocRel=" + intCodigoTipoDocumento + " AND a1.co_docRel=" + intCodigoDocumento + "";
                strSQL+="        AND a4.co_itmMae=" + intCodigoItemMaestro + "";
                strSQL+=" ) AS b1";
                strSQL+=" ON b1.co_empRel=b2.co_emp AND b1.co_locRel=b2.co_loc AND b1.co_tipDocRel=b2.co_tipDoc AND b1.co_docRel=b2.co_doc AND b1.co_itmMae=b2.co_itmMae";
                strSQL+=" WHERE b2.nd_can<b1.nd_stkCon";
                strSQL+=" ORDER BY b1.co_reg";
                rstPedSob=stmPedSob.executeQuery(strSQL);
                if(rstPedSob.next()){
                    blnRes=true;
                }
                stmPedSob.close();
                stmPedSob=null;
                rstPedSob.close();
                rstPedSob=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite conocer si el Pedido tiene items con cantidades faltantes
     * @param conRef
     * @param intCodigoEmpresa       : Código de empresa de Pedido
     * @param intCodigoLocal         : Código de local de Pedido
     * @param intCodigoTipoDocumento : Código de tipo de documento de Pedido
     * @param intCodigoDocumento     : Código de documento de Pedido
     * @param intCodigoItemMaestro   : Código de item maestro
     * @return true                  : Existen items con cantidades sobrantes
     * <BR> false                    : Caso contrario
     */
    public boolean isPedidoFaltante(Connection conRef, int intCodigoEmpresa, int intCodigoLocal, int intCodigoTipoDocumento, int intCodigoDocumento, int intCodigoItemMaestro){
        boolean blnRes=true;
        Statement stmPedFal;
        ResultSet rstPedFal;
        try{
            if(conRef!=null){
                //--FALTANTE
                stmPedFal=conRef.createStatement();
                strSQL="";
                strSQL+=" SELECT b2.co_emp AS co_empIngImp, b2.co_loc AS co_locIngImp, b2.co_tipDoc AS co_tipDocIngImp, b2.co_doc AS co_docIngImp, b2.co_reg AS co_regIngImp";
                strSQL+=" , b2.ne_numDoc AS ne_numDocIngImp, b2.fe_doc AS fe_docIngImp, b2.tx_numDoc2 AS tx_numDoc2IngImp";
                strSQL+=" , CASE WHEN b2.nd_can IS NULL THEN 0 ELSE b2.nd_can END AS nd_canIngImp, b2.nd_cosUni";
                strSQL+=" , b2.tx_nomItm, b2.tx_uniMed, b2.nd_preUniImp, b2.nd_pesItmKgr, b2.tx_codAlt, b2.tx_codAlt2";
                strSQL+=" , b1.co_emp AS co_empCon, b1.co_loc AS co_locCon, b1.co_tipdoc AS co_tipdocCon, b1.co_doc AS co_docCon";
                strSQL+=" , b1.co_reg AS co_regCon, CASE WHEN b1.nd_stkCon IS NULL THEN 0 ELSE b1.nd_stkCon END AS nd_canCon, b1.co_itmMae, b1.co_itm AS co_itmGrp";
                strSQL+=" FROM(";
                //PEDIDO
                strSQL+="        SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a1.tx_numDoc2";
                strSQL+="        , a2.nd_can, a4.co_itmMae, a2.nd_cosUni, a2.tx_codAlt, a2.tx_codAlt2";
                strSQL+="        , a2.tx_nomItm, a2.tx_uniMed, a2.nd_preUniImp, a3.nd_pesItmKgr";
                strSQL+="        FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2";
                strSQL+="        ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="        INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                strSQL+="        INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                strSQL+="        WHERE a1.co_emp=" + intCodigoEmpresa + " AND a1.co_loc=" + intCodigoLocal + "";
                strSQL+="        AND a1.co_tipDoc=" + intCodigoTipoDocumento + " AND a1.co_doc=" + intCodigoDocumento + "";
                strSQL+="        AND a4.co_itmMae=" + intCodigoItemMaestro + "";
                strSQL+="        AND a1.st_reg='A'";
                strSQL+=" ) AS b2";
                strSQL+=" LEFT OUTER JOIN(";
                //CONTEO
                strSQL+="        SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a8.nd_stkCon,a6.co_itmMae, a2.co_itm";
                strSQL+="        , a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel";
                strSQL+="        FROM tbm_cabOrdConInv AS a1";
                strSQL+="        INNER JOIN (tbm_detOrdConInv AS a2 INNER JOIN tbm_conInv AS a8";
                strSQL+="                   ON a2.co_emp=a8.co_emp AND a2.co_loc=a8.co_locrel AND a2.co_tipdoc=a8.co_tipdocrel AND a2.co_doc=a8.co_docrel AND a2.co_reg=a8.co_regrel    )";
                strSQL+="        ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="        INNER JOIN (tbm_invBod AS a3 INNER JOIN";
                strSQL+="                         (tbm_inv AS a4 INNER JOIN tbm_var AS a5 ON a4.co_uni=a5.co_reg)";
                strSQL+="                                             INNER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm";
                strSQL+="                                        ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm)";
                strSQL+="        ON a2.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod AND a2.co_itm=a3.co_itm";
                strSQL+="        WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="        AND a1.co_tipDocRel IN(14,245) AND a1.st_reg='A'";
                strSQL+="        AND a1.co_empRel=" + intCodigoEmpresa + " AND a1.co_locRel=" + intCodigoLocal + "";
                strSQL+="        AND a1.co_tipDocRel=" + intCodigoTipoDocumento + " AND a1.co_docRel=" + intCodigoDocumento + "";
                strSQL+="        AND a6.co_itmMae=" + intCodigoItemMaestro + "";
                strSQL+=" ) AS b1";
                strSQL+=" ON b1.co_empRel=b2.co_emp AND b1.co_locRel=b2.co_loc AND b1.co_tipDocRel=b2.co_tipDoc AND b1.co_docRel=b2.co_doc AND b1.co_itmMae=b2.co_itmMae";
                strSQL+=" WHERE b2.nd_can>b1.nd_stkCon";
                strSQL+=" ORDER BY b1.co_reg";
                rstPedFal=stmPedFal.executeQuery(strSQL);
                if(rstPedFal.next()){
                    blnRes=true;
                }
                stmPedFal.close();
                stmPedFal=null;
                rstPedFal.close();
                rstPedFal=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    

    public boolean setCampoTabla(Connection conexion, int intCodigoEmpresa, int intCodigoLocal, int intCodigoTipoDocumento, int intCodigoDocumento
                                        , String strNombreTabla, String strNombreCampo1, Object objValorCampo1){
        boolean blnRes=true;
        Statement stmEstDoc;
        try{
            if(conexion!=null){
                stmEstDoc=conexion.createStatement();
                strSQL="";
                strSQL+=" UPDATE " + strNombreTabla + "";
                strSQL+=" SET";
                strSQL+=" " + strNombreCampo1 + "=" + objValorCampo1 + "";
                strSQL+=" WHERE co_emp=" + intCodigoEmpresa + "";
                strSQL+=" AND co_loc=" + intCodigoLocal + "";
                strSQL+=" AND co_tipDoc=" + intCodigoTipoDocumento + "";
                strSQL+=" AND co_doc=" + intCodigoDocumento + "";
                strSQL+=";";
                System.out.println("setCampoTabla: " + strSQL);
                stmEstDoc.executeUpdate(strSQL);
                stmEstDoc.close();
                stmEstDoc=null;
            }
            
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    
    
    public boolean setCampoTabla(Connection conexion, String nameEmpresa, String nameLocal, String nameTipoDocumento, String nameDocumento
                                                    , int intCodigoEmpresa, int intCodigoLocal, int intCodigoTipoDocumento, int intCodigoDocumento
                                                    , String strNombreTabla, String strNombreCampo1, Object objValorCampo1){
        boolean blnRes=true;
        Statement stmEstDoc;
        try{
            if(conexion!=null){
                stmEstDoc=conexion.createStatement();
                strSQL="";
                strSQL+=" UPDATE " + strNombreTabla + "";
                strSQL+=" SET";
                strSQL+=" " + strNombreCampo1 + "=" + objValorCampo1 + "";
                strSQL+=" WHERE " + nameEmpresa + "=" + intCodigoEmpresa + "";
                strSQL+=" AND " + nameLocal + "=" + intCodigoLocal + "";
                strSQL+=" AND " + nameTipoDocumento + "=" + intCodigoTipoDocumento + "";
                strSQL+=" AND " + nameDocumento + "=" + intCodigoDocumento + "";
                strSQL+=";";
                System.out.println("setCampoTabla-dia: " + strSQL);
                stmEstDoc.executeUpdate(strSQL);
                stmEstDoc.close();
                stmEstDoc=null;
            }
            
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    
    
    
    /**
     * Función que permite conocer si existen solicitudes de transferencia pendientes de autorizar/desautorizar
     * @return true: Si existen solicitudes de transferencias pendientes
     * <BR> false  : Caso contrario
     */
    public boolean isSolicitudPendiente(Connection conRef, int intCodigoEmpresa, int intCodigoLocal, int intCodigoTipoDocumento, int intCodigoDocumento){
        boolean blnRes=false;
        Statement stmSolPen;
        ResultSet rstSolPen;
        try{
            if(conRef!=null){
                //--SOLICITUD/TRANSFERENCIA
                stmSolPen=conRef.createStatement();
                //CONTEO_TRANSFERENCIA_INGRESOIMPORTACION
                strSQL="";
                strSQL+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.ne_numdoc, a2.fe_doc, a2.tx_obs1";
                strSQL+=" , a4.co_reg, a4.co_itm, a4.nd_can, a6.co_itmMae";
                strSQL+=" , a3.co_empRelCabMovInv, a3.co_locRelCabMovInv, a3.co_tipDocRelCabMovInv, a3.co_docRelCabMovInv";
                strSQL+=" FROM tbm_cabMovInv AS a1 INNER JOIN tbr_cabSolTraInvCabMovInv AS a3";
                strSQL+=" 		ON a1.co_emp=a3.co_empRelCabMovInv AND a1.co_loc=a3.co_locRelCabMovInv";
                strSQL+=" 		AND a1.co_tipDoc=a3.co_tipDocRelCabMovInv AND a1.co_doc=a3.co_docRelCabMovInv";
                strSQL+=" INNER JOIN tbm_cabSolTraInv AS a2";
                strSQL+=" ON a2.co_emp=a3.co_empRelCabSolTraInv AND a2.co_loc=a3.co_locRelCabSolTraInv";
                strSQL+=" AND a2.co_tipDoc=a3.co_tipDocRelCabSolTraInv AND a2.co_doc=a3.co_docRelCabSolTraInv";
                strSQL+=" INNER JOIN tbm_detSolTraInv AS a4";
                strSQL+=" ON a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc AND a2.co_doc=a4.co_doc";
                strSQL+=" INNER JOIN tbm_inv AS a5 ON a4.co_emp=a5.co_emp AND a4.co_itm=a5.co_itm";
                strSQL+=" INNER JOIN tbm_equInv AS a6 ON a5.co_emp=a6.co_emp AND a5.co_itm=a6.co_itm";
                strSQL+=" WHERE a3.co_empRelCabMovInv=" + intCodigoEmpresa + " AND a3.co_locRelCabMovInv=" + intCodigoLocal + "";
                strSQL+=" AND a3.co_tipDocRelCabMovInv=" + intCodigoTipoDocumento + " AND a3.co_docRelCabMovInv=" + intCodigoDocumento + "";
                //strSQL+=" a6.co_itmMae=" + intCodigoItemMaestro + "";
                strSQL+=" AND a1.st_reg='A' AND a2.st_reg='A'";
                strSQL+=" AND a2.st_aut IS NULL";
                rstSolPen=stmSolPen.executeQuery(strSQL);
                if(rstSolPen.next()){
                    mostrarMsgInf("<HTML>Existen solicitudes pendientes de ser aprobadas o denegadas.<BR>Verifique las solicitudes pendientes y vuelva a intentarlo.</HTML>");
                    blnRes=true;
                }
                stmSolPen.close();
                stmSolPen=null;
                rstSolPen.close();
                rstSolPen=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    

    
    /**
     * Función que permite obtener un dato de un campo de una determinada tabla
     * @param conexion Conexión a la base de datos
     * @param intCodigoEmpresa Código de la empresa
     * @param intCodigoLocal   Código del local
     * @param intCodigoTipoDocumento Código del Tipo de documento
     * @param intCodigoDocumento Código del documento
     * @param strNombreTabla     Nombre de la tabla donde se encuentra el campo
     * @param strNombreCampo     Nombre del campo del que se desea obtener el dato
     * @return 
     */
    public boolean getEstadoDocumento(Connection conexion, int intCodigoEmpresa, int intCodigoLocal, int intCodigoTipoDocumento
                                        , int intCodigoDocumento, String strNombreTabla, String strNombreCampo){
        boolean blnRes=true;
        Statement stmEstDoc;
        ResultSet rstEstDoc;
        try{
            if(conexion!=null){
                strEstDoc="";
                stmEstDoc=conexion.createStatement();
                strSQL="";
                strSQL+=" SELECT " + strNombreCampo + " AS st_regRef FROM " + strNombreTabla + " AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodigoEmpresa + "";
                strSQL+=" AND a1.co_loc=" + intCodigoLocal + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodigoTipoDocumento + "";
                strSQL+=" AND a1.co_doc=" + intCodigoDocumento + "";
                strSQL+=";";
                System.out.println("getEstadoDocumento: " + strSQL);
                rstEstDoc=stmEstDoc.executeQuery(strSQL);
                if(rstEstDoc.next()){
                    strEstDoc=rstEstDoc.getString("st_regRef");
                }
                stmEstDoc.close();
                stmEstDoc=null;
                rstEstDoc.close();
                rstEstDoc=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }

    public String getEstDoc() {
        return strEstDoc;
    }

    public void setEstDoc(String strEstDoc) {
        this.strEstDoc = strEstDoc;
    }
     
    
    /**
     * Función que permite conocer si el Pedido tiene items con cantidades sobrantes o faltantes, es decir debe generar ajuste
     * @param conRef
     * @param intCodigoEmpresa       : Código de empresa de Pedido
     * @param intCodigoLocal         : Código de local de Pedido
     * @param intCodigoTipoDocumento : Código de tipo de documento de Pedido
     * @param intCodigoDocumento     : Código de documento de Pedido
     * @return true                  : Existen items con cantidades sobrantes/faltantes
     * <BR> false                    : Caso contrario
     */
    public boolean isPedidoCompleto(Connection conRef, int intCodigoEmpresa, int intCodigoLocal, int intCodigoTipoDocumento, int intCodigoDocumento){
        boolean blnRes=false;
        Statement stmConPenTrs;
        ResultSet rstConPenTrs;
        try{
            if(conRef!=null){
                stmConPenTrs=conRef.createStatement();
                //<editor-fold defaultstate="collapsed" desc="/* comentado */">
//                strSQL="";
//                strSQL+=" SELECT b1.co_empCon, b1.co_locCon, b1.co_tipdocCon, b1.co_docCon, b1.co_regCon, b1.nd_canCon, b1.co_itmMae, b1.co_itmGrp";
//                strSQL+=" , b1.co_empSolTrs, b1.co_locSolTrs, b1.co_tipDocSolTrs, b1.co_docSolTrs, b1.ne_numDocSolTrs";
//                strSQL+=" , b1.fe_docSolTrs, b1.tx_obs1, b1.co_regSolTrs, b1.nd_canSolTrs, b1.co_empTrs, b1.co_locTrs, b1.co_tipDocTrs";
//                strSQL+=" , b1.co_docTrs, b1.nd_canTrs, b1.co_itmEmp, b1.co_regTrs";
//                strSQL+=" , b1.co_empIngImp, b1.co_locIngImp, b1.co_tipDocIngImp, b1.co_docIngImp, b1.co_regIngImp";
//                strSQL+=" , b1.ne_numDocIngImp, b1.fe_docIngImp, b1.tx_numDoc2IngImp, b1.nd_canIngImp, b1.nd_cosUni";
//                strSQL+=" , b1.tx_nomItm, b1.tx_uniMed, b1.nd_preUniImp, b1.nd_pesItmKgr, b1.tx_codAlt, b1.tx_codAlt2";
//                strSQL+=" FROM(";
//                strSQL+=" 	 SELECT b1.co_emp AS co_empCon, b1.co_loc AS co_locCon, b1.co_tipdoc AS co_tipdocCon, b1.co_doc AS co_docCon";
//                strSQL+=" 	 , b1.co_reg AS co_regCon, CASE WHEN b1.nd_stkCon IS NULL THEN 0 ELSE b1.nd_stkCon END AS nd_canCon, b1.co_itmMae, b1.co_itm AS co_itmGrp";
//                strSQL+=" 	 , b2.co_emp AS co_empSolTrs, b2.co_loc AS co_locSolTrs, b2.co_tipDoc AS co_tipDocSolTrs, b2.co_doc AS co_docSolTrs, b2.ne_numDoc AS ne_numDocSolTrs";
//                strSQL+=" 	 , b2.fe_doc AS fe_docSolTrs, b2.tx_obs1, b2.co_reg AS co_regSolTrs, CASE WHEN b2.nd_can IS NULL THEN 0 ELSE b2.nd_can END AS nd_canSolTrs";
//                strSQL+=" 	 , b3.co_empRel AS co_empTrs, b3.co_locRel AS co_locTrs, b3.co_tipdocRel AS co_tipDocTrs";
//                strSQL+=" 	 , b3.co_docRel AS co_docTrs, CASE WHEN b3.nd_canTrs IS NULL THEN 0 ELSE b3.nd_canTrs END AS nd_canTrs";
//                strSQL+=" 	 , b3.co_itm AS co_itmEmp, b3.co_reg AS co_regTrs";
//                strSQL+=" 	 , b4.co_emp AS co_empIngImp, b4.co_loc AS co_locIngImp, b4.co_tipDoc AS co_tipDocIngImp, b4.co_doc AS co_docIngImp, b4.co_reg AS co_regIngImp";
//                strSQL+=" 	 , b4.ne_numDoc AS ne_numDocIngImp, b4.fe_doc AS fe_docIngImp, b4.tx_numDoc2 AS tx_numDoc2IngImp";
//                strSQL+=" 	 , CASE WHEN b4.nd_can IS NULL THEN 0 ELSE b4.nd_can END AS nd_canIngImp, b4.nd_cosUni";
//                strSQL+=" 	 , b4.tx_nomItm, b4.tx_uniMed, b4.nd_preUniImp, b4.nd_pesItmKgr, b4.tx_codAlt, b4.tx_codAlt2";
//                strSQL+=" 	 FROM(";
//                //CONTEO
//                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a8.nd_stkCon,a6.co_itmMae, a2.co_itm";
//                strSQL+=" 		, a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel";
//                strSQL+=" 		FROM tbm_cabOrdConInv AS a1";
//                strSQL+=" 		INNER JOIN (tbm_detOrdConInv AS a2 INNER JOIN tbm_conInv AS a8";
//                strSQL+=" 			   ON a2.co_emp=a8.co_emp AND a2.co_loc=a8.co_locrel AND a2.co_tipdoc=a8.co_tipdocrel AND a2.co_doc=a8.co_docrel AND a2.co_reg=a8.co_regrel    )";
//                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
//                strSQL+=" 		INNER JOIN (tbm_invBod AS a3 INNER JOIN";
//                strSQL+=" 				 (tbm_inv AS a4 INNER JOIN tbm_var AS a5 ON a4.co_uni=a5.co_reg)";
//                strSQL+=" 						     INNER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm";
//                strSQL+=" 						ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm)";
//                strSQL+=" 		ON a2.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod AND a2.co_itm=a3.co_itm";
//                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
//                strSQL+=" 		AND a1.co_tipDocRel IN(14,245) AND a1.st_reg='A'";
//                strSQL+=" 		AND a1.co_empRel=" + intCodigoEmpresa + " AND a1.co_locRel=" + intCodigoLocal + "";
//                strSQL+="               AND a1.co_tipDocRel=" + intCodigoTipoDocumento + " AND a1.co_docRel=" + intCodigoDocumento + "";
//                strSQL+=" 	 ) AS b1";
//                strSQL+="	 LEFT OUTER JOIN(";
//                //SOLICITUD
//                strSQL+=" 		SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.ne_numdoc, a2.fe_doc, a2.tx_obs1";
//                strSQL+=" 		, a4.co_reg, a4.co_itm, a4.nd_can, a6.co_itmMae";
//                strSQL+=" 		, a3.co_empRelCabMovInv, a3.co_locRelCabMovInv, a3.co_tipDocRelCabMovInv, a3.co_docRelCabMovInv";
//                strSQL+=" 		FROM tbm_cabMovInv AS a1 INNER JOIN tbr_cabSolTraInvCabMovInv AS a3";
//                strSQL+=" 				ON a1.co_emp=a3.co_empRelCabMovInv AND a1.co_loc=a3.co_locRelCabMovInv";
//                strSQL+=" 				AND a1.co_tipDoc=a3.co_tipDocRelCabMovInv AND a1.co_doc=a3.co_docRelCabMovInv";
//                strSQL+=" 		INNER JOIN tbm_cabSolTraInv AS a2";
//                strSQL+=" 		ON a2.co_emp=a3.co_empRelCabSolTraInv AND a2.co_loc=a3.co_locRelCabSolTraInv AND a2.co_tipDoc=a3.co_tipDocRelCabSolTraInv AND a2.co_doc=a3.co_docRelCabSolTraInv";
//                strSQL+=" 		INNER JOIN tbm_detSolTraInv AS a4";
//                strSQL+="		ON a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc AND a2.co_doc=a4.co_doc";
//                strSQL+=" 		INNER JOIN tbm_inv AS a5 ON a4.co_emp=a5.co_emp AND a4.co_itm=a5.co_itm";
//                strSQL+=" 		INNER JOIN tbm_equInv AS a6 ON a5.co_emp=a6.co_emp AND a5.co_itm=a6.co_itm";
//                strSQL+=" 		WHERE a3.co_empRelCabMovInv=" + intCodigoEmpresa + " AND a3.co_locRelCabMovInv=" + intCodigoLocal + "";
//                strSQL+="               AND a3.co_tipDocRelCabMovInv=" + intCodigoTipoDocumento + " AND a3.co_docRelCabMovInv=" + intCodigoDocumento + "";
//                strSQL+=" 		AND a1.st_reg='A' AND a2.st_reg='A'";
//                strSQL+="		AND a2.st_aut='A'";//AUTORIZADO
//                strSQL+="	 ) AS b2";
//                strSQL+="	 ON b1.co_empRel=b2.co_empRelCabMovInv AND b1.co_locRel=b2.co_locRelCabMovInv AND b1.co_tipDocRel=b2.co_tipDocRelCabMovInv AND b1.co_docRel=b2.co_docRelCabMovInv AND b1.co_itmMae=b2.co_itmMae";
//                strSQL+="	 LEFT OUTER JOIN(";
//                //TRANSFERENCIA
//                strSQL+=" 		SELECT a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, SUM(a1.nd_can) AS nd_canTrs, a1.tx_codAlt";
//                strSQL+=" 		, a1.co_itmMae, a1.tx_codAlt2, a1.co_reg, a1.co_itm";
//                strSQL+=" 		, a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv";
//                strSQL+=" 		FROM(";
//                strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a2.tx_codAlt, a4.co_itmMae, a2.nd_can, a2.co_itm";
//                strSQL+=" 			, a3.tx_codAlt2, a3.tx_nomItm, a2.tx_unimed, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc";
//                strSQL+=" 			, a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel";
//                strSQL+=" 			, a5.co_empRel AS co_empRelCabMovInv, a5.co_locRel AS co_locRelCabMovInv, a5.co_tipDocRel AS co_tipDocRelCabMovInv, a5.co_docRel AS co_docRelCabMovInv";
//                strSQL+=" 			FROM (tbr_cabMovInv AS a5 INNER JOIN tbm_cabMovInv AS a1";
//                strSQL+=" 				ON a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc AND a5.co_tipDoc=a1.co_tipDoc AND a5.co_doc=a1.co_doc)";
//                strSQL+=" 			INNER JOIN tbm_detMovInv AS a2";
//                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
//                strSQL+=" 			INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
//                strSQL+=" 			INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
//                strSQL+=" 			WHERE a2.nd_can<0";
//                strSQL+="			AND a5.co_empRel=" + intCodigoEmpresa + " AND a5.co_locRel=" + intCodigoLocal + "";
//                strSQL+="                       AND a5.co_tipDocRel=" + intCodigoTipoDocumento + " AND a5.co_docRel=" + intCodigoDocumento + "";
//                strSQL+=" 		) AS a1";
//                strSQL+=" 		GROUP BY a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, a1.co_reg, a1.tx_codAlt, a1.co_itmMae, a1.tx_codAlt2, a1.co_itm";
//                strSQL+=" 		, a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv";
//                strSQL+=" 	 ) AS b3";
//                strSQL+=" 	 ON b1.co_empRel=b3.co_empRel AND b1.co_locRel=b3.co_locRel AND b1.co_tipDocRel=b3.co_tipDocRel AND b1.co_docRel=b3.co_docRel AND b1.co_itmMae=b3.co_itmMae";
//                strSQL+=" 	 LEFT OUTER JOIN(";
//                //PEDIDO
//                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a1.tx_numDoc2";
//                strSQL+=" 		, a2.nd_can, a4.co_itmMae, a2.nd_cosUni, a2.tx_codAlt, a2.tx_codAlt2";
//                strSQL+=" 		, a2.tx_nomItm, a2.tx_uniMed, a2.nd_preUniImp, a3.nd_pesItmKgr";
//                strSQL+=" 		FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2";
//                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
//                strSQL+=" 		INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
//                strSQL+=" 		INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
//                strSQL+=" 		WHERE a1.co_emp=" + intCodigoEmpresa + " AND a1.co_loc=" + intCodigoLocal + "";
//                strSQL+="               AND a1.co_tipDoc=" + intCodigoTipoDocumento + " AND a1.co_doc=" + intCodigoDocumento + "";
//                strSQL+=" 		AND a1.st_reg='A'";
//                strSQL+=" 	 ) AS b4";
//                strSQL+=" 	 ON b1.co_empRel=b4.co_emp AND b1.co_locRel=b4.co_loc AND b1.co_tipDocRel=b4.co_tipDoc AND b1.co_docRel=b4.co_doc AND b1.co_itmMae=b4.co_itmMae";
//                strSQL+=" 	 WHERE  b1.nd_stkCon>0";
//                strSQL+=" 	 ORDER BY b1.co_reg";
//                strSQL+=" ) AS b1";                
//                strSQL+=" WHERE b1.nd_canIngImp=b1.nd_canCon AND b1.nd_canCon=b1.nd_canSolTrs AND b1.nd_canSolTrs=b1.nd_canTrs";
//                strSQL+=" ORDER BY b1.co_regCon";
                //</editor-fold>
                
                strSQL="";
                //PEDIDO COMPLETO
                strSQL+=" SELECT b1.co_empCon, b1.co_locCon, b1.co_tipdocCon, b1.co_docCon, b1.co_regCon, b1.nd_canCon, b1.co_itmMae, b1.co_itmGrp";
                strSQL+="      , b1.nd_canSolTrs, b1.co_empTrs, b1.co_locTrs, b1.co_tipDocTrs, b1.co_docTrs, b1.nd_canTrs, b1.co_itmEmp";
                strSQL+="      , b1.co_empIngImp, b1.co_locIngImp, b1.co_tipDocIngImp, b1.co_docIngImp, b1.co_regIngImp";
                strSQL+="      , b1.ne_numDocIngImp, b1.fe_docIngImp, b1.tx_numDoc2IngImp, b1.nd_canIngImp, b1.nd_cosUni";
                strSQL+="      , b1.tx_nomItm, b1.tx_uniMed, b1.nd_preUniImp, b1.nd_pesItmKgr, b1.tx_codAlt, b1.tx_codAlt2";
                strSQL+=" FROM(";
                strSQL+=" 	 SELECT b1.co_emp AS co_empCon, b1.co_loc AS co_locCon, b1.co_tipdoc AS co_tipdocCon, b1.co_doc AS co_docCon";
                strSQL+=" 	      , b1.co_reg AS co_regCon, CASE WHEN b1.nd_stkCon IS NULL THEN 0 ELSE b1.nd_stkCon END AS nd_canCon, b1.co_itmMae, b1.co_itm AS co_itmGrp";
                strSQL+=" 	      , CASE WHEN b2.nd_can IS NULL THEN 0 ELSE b2.nd_can END AS nd_canSolTrs";
                strSQL+=" 	      , b3.co_empRel AS co_empTrs, b3.co_locRel AS co_locTrs, b3.co_tipdocRel AS co_tipDocTrs";
                strSQL+=" 	      , b3.co_docRel AS co_docTrs, CASE WHEN ABS(b3.nd_canTrs) IS NULL THEN 0 ELSE ABS(b3.nd_canTrs) END AS nd_canTrs";
                strSQL+=" 	      , b3.co_itm AS co_itmEmp";
                strSQL+=" 	      , b4.co_emp AS co_empIngImp, b4.co_loc AS co_locIngImp, b4.co_tipDoc AS co_tipDocIngImp, b4.co_doc AS co_docIngImp, b4.co_reg AS co_regIngImp";
                strSQL+=" 	      , b4.ne_numDoc AS ne_numDocIngImp, b4.fe_doc AS fe_docIngImp, b4.tx_numDoc2 AS tx_numDoc2IngImp";
                strSQL+=" 	      , CASE WHEN b4.nd_can IS NULL THEN 0 ELSE b4.nd_can END AS nd_canIngImp, b4.nd_cosUni";
                strSQL+=" 	      , b4.tx_nomItm, b4.tx_uniMed, b4.nd_preUniImp, b4.nd_pesItmKgr, b4.tx_codAlt, b4.tx_codAlt2";
                strSQL+=" 	 FROM(";
                //CONTEO
                strSQL+=" 		SELECT b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc, b1.co_reg, b1.nd_stkCon, b1.co_itmMae, b1.co_itm";
                strSQL+=" 		     , b1.co_empRel, b1.co_locRel, b1.co_tipDocRel, b1.co_docRel";
                strSQL+=" 		FROM(";
                strSQL+=" 			SELECT a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg, a2.nd_stkCon, a2.co_itmMae, a2.co_itm";
                strSQL+=" 			     , a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel";
                strSQL+=" 			FROM(";
                strSQL+=" 				SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 				FROM tbm_cabOrdConInv AS a1";
                strSQL+=" 				WHERE a1.co_empRel=" + intCodigoEmpresa + " AND a1.co_locRel=" + intCodigoLocal + "";
                strSQL+="                               AND a1.co_tipDocRel=" + intCodigoTipoDocumento + " AND a1.co_docRel=" + intCodigoDocumento + "";
                strSQL+=" 			) AS a1";
                strSQL+=" 			LEFT OUTER JOIN(";
                strSQL+=" 				SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a8.nd_stkCon,a6.co_itmMae, a2.co_itm";
                strSQL+=" 				     , a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel";
                strSQL+=" 				FROM tbm_cabOrdConInv AS a1 ";
                strSQL+=" 				RIGHT OUTER JOIN";
                strSQL+=" 				(    tbm_detOrdConInv AS a2 RIGHT OUTER JOIN tbm_conInv AS a8";
                strSQL+=" 			             ON a2.co_emp=a8.co_emp AND a2.co_loc=a8.co_locrel AND a2.co_tipdoc=a8.co_tipdocrel";
                strSQL+=" 				     AND a2.co_doc=a8.co_docrel AND a2.co_reg=a8.co_regrel";
                strSQL+=" 				     RIGHT OUTER JOIN tbm_inv AS a4 ON a8.co_emp=a4.co_emp AND a8.co_itm=a4.co_itm";
                strSQL+=" 				     RIGHT OUTER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm";
                strSQL+=" 				)";
                strSQL+=" 				ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 				WHERE a1.st_reg='A'";
                strSQL+=" 			) AS a2";
                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 			UNION ALL";
                strSQL+=" 			SELECT a3.co_emp, a3.co_locRel, a3.co_tipDocRel, a3.co_docRel, a3.co_regRel, a3.nd_stkCon, a3.co_itmMae, a3.co_itm";
                strSQL+=" 			     , a3.co_empIngImp, a3.co_locIngImp, a3.co_tipDocIngImp, a3.co_docIngImp";
                strSQL+=" 			FROM(";
                strSQL+=" 				SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 				FROM tbm_cabOrdConInv AS a1";
                strSQL+=" 				WHERE a1.co_empRel=" + intCodigoEmpresa + " AND a1.co_locRel=" + intCodigoLocal + "";
                strSQL+="                               AND a1.co_tipDocRel=" + intCodigoTipoDocumento + " AND a1.co_docRel=" + intCodigoDocumento + "";
                strSQL+=" 			) AS a1";
                strSQL+=" 			INNER JOIN(";
                strSQL+=" 				SELECT a1.co_emp, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a1.co_regRel, a1.nd_stkCon, -1 AS co_itmMae, a1.co_itm";
                strSQL+=" 				     , a2.co_empRel AS co_empIngImp, a2.co_locRel AS co_locIngImp, a2.co_tipDocRel AS co_tipDocIngImp, a2.co_docRel AS co_docIngImp";
                strSQL+=" 				FROM tbm_conInv AS a1 INNER JOIN tbm_cabOrdConInv AS a2";
                strSQL+=" 				ON a1.co_emp=a2.co_emp AND a1.co_locRel=a2.co_loc AND a1.co_tipDocRel=a2.co_tipDoc AND a1.co_docRel=a2.co_doc";
                strSQL+=" 				WHERE a1.co_itm IS NULL";
                strSQL+=" 			) AS a3";
                strSQL+=" 			ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_locRel AND a1.co_tipDoc=a3.co_tipDocRel AND a1.co_doc=a3.co_docRel";
                strSQL+=" 		) AS b1";
                strSQL+=" 	 ) AS b1";
                //SOLICITUD
                strSQL+=" 	 LEFT OUTER JOIN(";
                strSQL+=" 		SELECT a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, SUM(a1.nd_can) AS nd_can, a1.tx_codAlt";
                strSQL+=" 		     , a1.co_itmMae, a1.tx_codAlt2";
                strSQL+=" 		     , a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv";
                strSQL+=" 		FROM(";
                strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a2.tx_codAlt, a4.co_itmMae, ABS(a2.nd_can) AS nd_can, a2.co_itm";
                strSQL+=" 			     , a3.tx_codAlt2, a3.tx_nomItm, a2.tx_unimed, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc";
                strSQL+=" 			     , a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel";
                strSQL+=" 			     , a5.co_empRel AS co_empRelCabMovInv, a5.co_locRel AS co_locRelCabMovInv, a5.co_tipDocRel AS co_tipDocRelCabMovInv, a5.co_docRel AS co_docRelCabMovInv";
                strSQL+=" 			FROM (tbr_cabMovInv AS a5 INNER JOIN tbm_cabMovInv AS a1";
                strSQL+=" 				ON a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc AND a5.co_tipDoc=a1.co_tipDoc AND a5.co_doc=a1.co_doc)";
                strSQL+=" 			INNER JOIN tbm_detMovInv AS a2";
                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 			INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                strSQL+=" 			INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                strSQL+=" 			WHERE a2.nd_can<0";
                strSQL+=" 			AND a5.co_empRel=" + intCodigoEmpresa + " AND a5.co_locRel=" + intCodigoLocal + "";
                strSQL+=" 			AND a5.co_tipDocRel=" + intCodigoTipoDocumento + " AND a5.co_docRel=" + intCodigoDocumento + "";
                strSQL+=" 		) AS a1";
                strSQL+=" 		GROUP BY a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, a1.tx_codAlt, a1.co_itmMae, a1.tx_codAlt2";
                strSQL+=" 		       , a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv";
                strSQL+=" 		ORDER BY a1.co_itmMae";
                strSQL+=" 	 ) AS b2";
                strSQL+=" 	 ON b1.co_empRel=b2.co_empRelCabMovInv AND b1.co_locRel=b2.co_locRelCabMovInv AND b1.co_tipDocRel=b2.co_tipDocRelCabMovInv AND b1.co_docRel=b2.co_docRelCabMovInv AND b1.co_itmMae=b2.co_itmMae";
                //TRANSFERENCIA
                strSQL+=" 	 LEFT OUTER JOIN(";
                strSQL+=" 		SELECT a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, SUM(a1.nd_can) AS nd_canTrs, a1.tx_codAlt";
                strSQL+=" 		     , a1.co_itmMae, a1.tx_codAlt2, a1.co_itm, a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv";
                strSQL+=" 		FROM(";
                strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a2.tx_codAlt, a4.co_itmMae, a2.nd_can, a2.co_itm";
                strSQL+=" 			     , a3.tx_codAlt2, a3.tx_nomItm, a2.tx_unimed, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc";
                strSQL+=" 			     , a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel";
                strSQL+=" 			     , a5.co_empRel AS co_empRelCabMovInv, a5.co_locRel AS co_locRelCabMovInv, a5.co_tipDocRel AS co_tipDocRelCabMovInv, a5.co_docRel AS co_docRelCabMovInv";
                strSQL+=" 			FROM (tbr_cabMovInv AS a5 INNER JOIN tbm_cabMovInv AS a1";
                strSQL+=" 				ON a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc AND a5.co_tipDoc=a1.co_tipDoc AND a5.co_doc=a1.co_doc)";
                strSQL+=" 			INNER JOIN tbm_detMovInv AS a2";
                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 			INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                strSQL+=" 			INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                strSQL+=" 			WHERE a2.nd_can<0";
                strSQL+=" 			AND a5.co_empRel=" + intCodigoEmpresa + " AND a5.co_locRel=" + intCodigoLocal + "";
                strSQL+=" 			AND a5.co_tipDocRel=" + intCodigoTipoDocumento + " AND a5.co_docRel=" + intCodigoDocumento + "";
                strSQL+=" 		) AS a1";
                strSQL+=" 		GROUP BY a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, a1.tx_codAlt, a1.co_itmMae, a1.tx_codAlt2, a1.co_itm";
                strSQL+=" 		       , a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv";
                strSQL+=" 		ORDER BY a1.co_itmMae";
                strSQL+=" 	 ) AS b3";
                strSQL+=" 	 ON b1.co_empRel=b3.co_empRel AND b1.co_locRel=b3.co_locRel AND b1.co_tipDocRel=b3.co_tipDocRel AND b1.co_docRel=b3.co_docRel AND b1.co_itmMae=b3.co_itmMae";
                //PEDIDO
                strSQL+=" 	 LEFT OUTER JOIN(";
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a1.tx_numDoc2";
                strSQL+=" 		     , a2.nd_can, a4.co_itmMae, a2.nd_cosUni, a2.tx_codAlt, a2.tx_codAlt2";
                strSQL+=" 		     , a2.tx_nomItm, a2.tx_uniMed, a2.nd_preUniImp, a3.nd_pesItmKgr";
                strSQL+=" 		FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 		INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                strSQL+=" 		INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                strSQL+=" 		WHERE a1.st_reg='A'";
                strSQL+=" 		AND a1.co_emp=" + intCodigoEmpresa + " AND a1.co_loc=" + intCodigoLocal + "";
                strSQL+=" 		AND a1.co_tipDoc=" + intCodigoTipoDocumento + " AND a1.co_doc=" + intCodigoDocumento + "";
                strSQL+=" 	 ) AS b4";
                strSQL+=" 	 ON b1.co_empRel=b4.co_emp AND b1.co_locRel=b4.co_loc AND b1.co_tipDocRel=b4.co_tipDoc AND b1.co_docRel=b4.co_doc AND b1.co_itmMae=b4.co_itmMae";
                strSQL+=" 	 ORDER BY b1.co_reg";
                strSQL+=" ) AS b1";
                //strSQL+=" WHERE (b1.nd_canIngImp=b1.nd_canCon) AND (b1.nd_canCon=b1.nd_canSolTrs) AND (b1.nd_canSolTrs=b1.nd_canTrs)";
                strSQL+=" WHERE (b1.nd_canIngImp-b1.nd_canCon)!=0 OR (b1.nd_canCon-b1.nd_canSolTrs)!=0 OR (b1.nd_canSolTrs-b1.nd_canTrs)!=0";
                strSQL+=" ORDER BY b1.co_regCon";
                System.out.println("isPedidoCompleto: " + strSQL);
                rstConPenTrs=stmConPenTrs.executeQuery(strSQL);
                if(rstConPenTrs.next()){
                    blnRes=true;
                }
                stmConPenTrs.close();
                stmConPenTrs=null;
                rstConPenTrs.close();
                rstConPenTrs=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite conocer el Numero de Vistos Buenos de un tipo de documento
     * @param conRef
     * @param intCodigoEmpresa
     * @param intCodigoLocal
     * @param intCodigoTipoDocumento
     * @return isNumVisBue El número de vistos buenos que necesita el tipo de documento
     */
    public int getNumVisBueTipDoc(Connection conRef, int intCodigoEmpresa, int intCodigoLocal, int intCodigoTipoDocumento){
        int intNumVisBue=-1;
        Statement stmNumVisBue;
        ResultSet rstNumVisBue;
        try{
            if(conRef!=null){
                stmNumVisBue=conRef.createStatement();
                //Numero de Vistos Buenos
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.ne_numVisBue";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodigoEmpresa + "";
                strSQL+=" AND a1.co_loc=" + intCodigoLocal + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodigoTipoDocumento + "";
                strSQL+=" AND a1.st_reg='A'";
                System.out.println("getNumVisBueTipDoc: "+strSQL);
                rstNumVisBue=stmNumVisBue.executeQuery(strSQL);
                if(rstNumVisBue.next()){
                    intNumVisBue=rstNumVisBue.getInt("ne_numVisBue");
                }
                stmNumVisBue.close();
                stmNumVisBue=null;
                rstNumVisBue.close();
                rstNumVisBue=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return intNumVisBue;
    }
    
    /**
     * Función que permite conocer el Numero de Vistos Buenos obligatorios de un tipo de documento
     * @param conRef
     * @param intCodigoEmpresa
     * @param intCodigoLocal
     * @param intCodigoTipoDocumento
     * @return isNumVisBue El número de vistos buenos que necesita el tipo de documento
     */
    public int getNumVisBueObl(Connection conRef, int intCodigoEmpresa, int intCodigoLocal, int intCodigoTipoDocumento){
        int intNumVisBueObl=-1;
        Statement stmNumVisBue;
        ResultSet rstNumVisBue;
        try{
            if(conRef!=null){
                stmNumVisBue=conRef.createStatement();
                //Numero de Vistos Buenos
                strSQL="";
                strSQL+=" SELECT COUNT(b1.nd_valAut) AS ne_numVisBue ";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_visBue";
                strSQL+=" 	     , (CASE WHEN a1.nd_valAut IS NULL THEN 0 ELSE a1.nd_ValAut END) as nd_ValAut";
                strSQL+=" 	FROM tbr_visBueUsrTipDoc AS a1";
                strSQL+=" 	WHERE a1.st_reg='A' ";
                strSQL+=" 	AND a1.co_emp=" + intCodigoEmpresa + "";
                strSQL+=" 	AND a1.co_loc=" + intCodigoLocal + "";
                strSQL+=" 	AND a1.co_tipDoc=" + intCodigoTipoDocumento + "";
                strSQL+=" 	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_visBue, a1.nd_valAut";
                strSQL+=" ) AS b1";
                strSQL+=" WHERE (CASE WHEN b1.nd_ValAut >0 THEN TRUE ELSE FALSE END )";
                System.out.println("getNumVisBueObl: " + strSQL);
                rstNumVisBue=stmNumVisBue.executeQuery(strSQL);
                if(rstNumVisBue.next()){
                    intNumVisBueObl=rstNumVisBue.getInt("ne_numVisBue");
                }
                stmNumVisBue.close();
                stmNumVisBue=null;
                rstNumVisBue.close();
                rstNumVisBue=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return intNumVisBueObl;
    }
    
    
    /**
     * Función que permite conocer el Numero de Vistos Buenos autorizados del documento por usuario.
     * @param conRef
     * @param intCodigoEmpresa
     * @param intCodigoLocal
     * @param intCodigoTipoDocumento
     * @return isNumVisBue El número de vistos buenos que necesita el tipo de documento
     */
    public int getNumVisBueAutUsr(Connection conRef, int intCodigoEmpresa, int intCodigoLocal, int intCodigoTipoDocumento, int intCodigoDocumento){
        int intNumVisBueAutUsr=-1;
        Statement stmNumVisBue;
        ResultSet rstNumVisBue;
        try{
            if(conRef!=null){
                stmNumVisBue=conRef.createStatement();
                //Numero de Vistos Buenos
                strSQL ="";
                strSQL+=" SELECT COUNT(b1.co_visbue) AS ne_numVisBueAutUsr FROM(";
                strSQL+=" 	SELECT c1.co_emp, c1.co_loc, c1.co_tipdoc, c1.co_doc, c1.co_visbue";
                strSQL+=" 	FROM(";
                strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_visbue";
                strSQL+="           FROM tbm_visBueMovInv AS a1";
                strSQL+="           WHERE a1.st_reg='A'";
                strSQL+="           AND a1.co_emp=" + intCodigoEmpresa + "";
                strSQL+="           AND a1.co_loc=" + intCodigoLocal + "";
                strSQL+="           AND a1.co_tipDoc=" + intCodigoTipoDocumento + "";
                strSQL+="           AND a1.co_doc=" + intCodigoDocumento + "";
                strSQL+="           GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_visbue";
                strSQL+="           UNION ALL";
                strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, " + intCodigoDocumento + " AS co_doc, a1.co_visBue";
                strSQL+="           FROM tbr_visBueUsrTipDoc AS a1";
                strSQL+="           WHERE a1.st_reg='A' ";  
                strSQL+="           AND a1.co_emp=" + objParSis.getCodigoEmpresa() + ""; 
                strSQL+="           AND a1.co_loc=" + objParSis.getCodigoLocal() + ""; 
                strSQL+="           AND a1.co_tipDoc=" + intCodigoTipoDocumento + "";
                strSQL+="           AND a1.co_usr=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" 	) AS c1";
                strSQL+=" 	GROUP BY c1.co_emp, c1.co_loc, c1.co_tipdoc, c1.co_doc, c1.co_visbue";
                strSQL+=" ) AS b1";
                System.out.println("getNumVisBueAutUsr: "+strSQL);
                rstNumVisBue=stmNumVisBue.executeQuery(strSQL);
                if(rstNumVisBue.next()){
                    intNumVisBueAutUsr=rstNumVisBue.getInt("ne_numVisBueAutUsr");
                }
                stmNumVisBue.close();
                stmNumVisBue=null;
                rstNumVisBue.close();
                rstNumVisBue=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return intNumVisBueAutUsr;
    }

    
    /**
     * Función que permite obtener un arreglo con los códigos de vistos buenos asociados al tipo de documento y usuario
     * @param conRef
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodTipDoc
     * @return 
     */
    public boolean getCodVisBue(Connection conRef, int intCodEmp, int intCodLoc, int intCodTipDoc, Object objCodUsr){
        boolean blnRes=true;
        Statement stmCodVisBue;
        ResultSet rstCodVisBue;
        try{
            if(conRef!=null){
                stmCodVisBue=conRef.createStatement();
                arlDatCodVisBue.clear();
                //Código de Vistos Buenos por usuario y si necesitan autorización previa
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_visBue, a1.co_usr, a1.st_necVisBuePre, a1.nd_valAut";
                strSQL+=" FROM tbr_visBueUsrTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                if(objCodUsr!=null)
                    strSQL+=" AND a1.co_usr=" + objCodUsr + "";
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_visBue";
                rstCodVisBue=stmCodVisBue.executeQuery(strSQL);
                while(rstCodVisBue.next()){
                    arlRegCodVisBue=new ArrayList();
                    arlRegCodVisBue.add(INT_ARL_COD_VIS_BUE_COD_EMP, rstCodVisBue.getInt("co_emp"));
                    arlRegCodVisBue.add(INT_ARL_COD_VIS_BUE_COD_LOC, rstCodVisBue.getInt("co_loc"));
                    arlRegCodVisBue.add(INT_ARL_COD_VIS_BUE_COD_TIP_DOC, rstCodVisBue.getInt("co_tipDoc"));
                    arlRegCodVisBue.add(INT_ARL_COD_VIS_BUE_COD_VIS_BUE, rstCodVisBue.getInt("co_visBue"));
                    arlRegCodVisBue.add(INT_ARL_COD_VIS_BUE_COD_USR, rstCodVisBue.getInt("co_usr"));
                    arlRegCodVisBue.add(INT_ARL_COD_VIS_BUE_NEC_AUT_PRE, rstCodVisBue.getInt("st_necVisBuePre"));
                    arlRegCodVisBue.add(INT_ARL_COD_VIS_BUE_VAL_VIS_BUE, rstCodVisBue.getInt("nd_valAut"));
                    arlDatCodVisBue.add(arlRegCodVisBue);
                }
                stmCodVisBue.close();
                rstCodVisBue.close();
                stmCodVisBue=null;
                rstCodVisBue=null;
            }
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }

    public ArrayList getDatCodVisBue() {
        return arlDatCodVisBue;
    }
    
  

    public boolean getVisBueAutAsoDoc(Connection conRef, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
        boolean blnRes=true;
        Statement stmCodVisBue;
        ResultSet rstCodVisBue;
        try{
            if(conRef!=null){
                stmCodVisBue=conRef.createStatement();
                arlDatCabMovInv.clear();
                //Vistos buenos autorizados asociados al documento
                strSQL="";
                strSQL+=" SELECT a1.co_emp AS co_empVisBue, a1.co_loc AS co_locVisBue, a1.co_tipDoc AS co_tipDocVisBue, a1.tx_desCor AS tx_desCorVisBue";
                strSQL+=" , a1.ne_numVisBue AS ne_numVisBue, a1.co_visBue, a1.st_necVisBuePre, a1.nd_valAut";
                strSQL+=" , a2.co_emp AS co_empCabMovInv, a2.co_loc AS co_locCabMovInv, a2.co_tipDoc AS co_tipDocCabMovInv, a2.co_doc AS co_docCabMovInv";
                strSQL+=" , a2.st_aut AS st_autCabMovInv, a2.nd_valAut AS nd_valAutCabMovInv, a2.co_visBue AS co_visBueCabMovInv";
                strSQL+=" FROM(";
                //codigos de vistos buenos
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.ne_numVisBue, a2.co_visBue, a2.st_necVisBuePre, a2.nd_valAut";
                strSQL+=" 	FROM tbm_cabTipDoc AS a1 INNER JOIN tbr_visBueUsrTipDoc AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+="       WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+="       AND a1.co_loc=" + intCodLoc + "";
                strSQL+="       AND a1.co_tipDoc=" + intCodTipDoc + "";
                strSQL+=" 	AND a1.st_reg='A'";
                strSQL+=" 	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.ne_numVisBue, a2.co_visBue, a2.st_necVisBuePre, a2.nd_valAut";
                strSQL+=" ) AS a1";
                strSQL+=" LEFT OUTER JOIN(";
                //Si devuelve un registro es porque aun Alfredo no autoriza
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.st_aut, a1.nd_valAut, a2.co_visBue";
                strSQL+=" 	FROM tbm_cabMovInv AS a1 INNER JOIN tbm_visBueMovInv AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+="       WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+="       AND a1.co_loc=" + intCodLoc + "";
                strSQL+="       AND a1.co_tipDoc=" + intCodTipDoc + "";
                strSQL+="       AND a1.co_doc=" + intCodDoc + "";
                strSQL+=" 	AND (a1.st_aut IS NULL OR a1.st_aut='P')";
                strSQL+=" ) AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_visBue=a2.co_visBue";
                strSQL+=" a1.co_emp, a1.co_loc, a1.co_tipDoc";
                rstCodVisBue=stmCodVisBue.executeQuery(strSQL);
                while(rstCodVisBue.next()){
                    arlRegCabMovInv=new ArrayList();
                    arlRegCabMovInv.add(INT_ARL_CAB_MOV_INV_COD_EMP,                 rstCodVisBue.getInt("co_empVisBue"));
                    arlRegCabMovInv.add(INT_ARL_CAB_MOV_INV_COD_LOC,                 rstCodVisBue.getInt("co_locVisBue"));
                    arlRegCabMovInv.add(INT_ARL_CAB_MOV_INV_COD_TIP_DOC,             rstCodVisBue.getInt("co_tipDocVisBue"));
                    arlRegCabMovInv.add(INT_ARL_CAB_MOV_INV_DES_COR_TIP_DOC,         rstCodVisBue.getInt("tx_desCorVisBue"));
                    arlRegCabMovInv.add(INT_ARL_CAB_MOV_INV_NUM_VIS_BUE,             rstCodVisBue.getInt("ne_numVisBue"));
                    arlRegCabMovInv.add(INT_ARL_CAB_MOV_INV_COD_VIS_BUE,             rstCodVisBue.getInt("co_visBue"));
                    arlRegCabMovInv.add(INT_ARL_CAB_MOV_INV_NEC_VIS_BUE_PRE,         rstCodVisBue.getInt("st_necVisBuePre"));
                    arlRegCabMovInv.add(INT_ARL_CAB_MOV_INV_VAL_VIS_BUE,             rstCodVisBue.getInt("nd_valAut"));
                    arlRegCabMovInv.add(INT_ARL_CAB_MOV_INV_COD_EMP_CAB_MOV_INV,     rstCodVisBue.getInt("co_empCabMovInv"));
                    arlRegCabMovInv.add(INT_ARL_CAB_MOV_INV_COD_LOC_CAB_MOV_INV,     rstCodVisBue.getInt("co_locCabMovInv"));
                    arlRegCabMovInv.add(INT_ARL_CAB_MOV_INV_COD_TIP_DOC_CAB_MOV_INV, rstCodVisBue.getInt("co_tipDocCabMovInv"));
                    arlRegCabMovInv.add(INT_ARL_CAB_MOV_INV_COD_DOC_CAB_MOV_INV,     rstCodVisBue.getInt("co_docCabMovInv"));
                    arlRegCabMovInv.add(INT_ARL_CAB_MOV_INV_EST_AUT_CAB_MOV_INV,     rstCodVisBue.getInt("st_autCabMovInv"));
                    arlRegCabMovInv.add(INT_ARL_CAB_MOV_INV_VAL_AUT_CAB_MOV_INV,     rstCodVisBue.getInt("nd_valAutCabMovInv"));
                    arlRegCabMovInv.add(INT_ARL_CAB_MOV_INV_COD_VIS_BUE_CAB_MOV_INV, rstCodVisBue.getInt("co_visBueCabMovInv"));         
                    arlDatCabMovInv.add(arlRegCabMovInv);
                }
                stmCodVisBue.close();
                rstCodVisBue.close();
                stmCodVisBue=null;
                rstCodVisBue=null;
            }
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }

    public ArrayList getDatCabMovInv() {
        return arlDatCabMovInv;
    }

    
    /**
     * Función que permite conocer los vistos buenos obligatorios asociados a un tipo de documento
     * @param conRef
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodTipDoc
     * @return 
     */
    public boolean getCodVisBueObl(Connection conRef, int intCodEmp, int intCodLoc, int intCodTipDoc){
        boolean blnRes=true;
        Statement stmCodVisBue;
        ResultSet rstCodVisBue;
        try{
            if(conRef!=null){
                stmCodVisBue=conRef.createStatement();
                arlDatCodVisBueObl.clear();
                //Código de Vistos Buenos
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_visBue, a1.nd_valAut";
                strSQL+=" FROM tbr_visBueUsrTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" AND a1.nd_valAut IS NOT NULL";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_visBue";
                rstCodVisBue=stmCodVisBue.executeQuery(strSQL);
                while(rstCodVisBue.next()){
                    arlRegCodVisBueObl=new ArrayList();
                    arlRegCodVisBueObl.add(INT_ARL_COD_VIS_BUE_OBL_COD_EMP,     rstCodVisBue.getInt("co_emp"));
                    arlRegCodVisBueObl.add(INT_ARL_COD_VIS_BUE_OBL_COD_LOC,     rstCodVisBue.getInt("co_loc"));
                    arlRegCodVisBueObl.add(INT_ARL_COD_VIS_BUE_OBL_COD_TIP_DOC, rstCodVisBue.getInt("co_tipDoc"));
                    arlRegCodVisBueObl.add(INT_ARL_COD_VIS_BUE_OBL_COD_VIS_BUE, rstCodVisBue.getInt("co_visBue"));
                    arlDatCodVisBueObl.add(arlRegCodVisBueObl);
                }
                intNumVisBueObl=arlDatCodVisBueObl.size();
                stmCodVisBue.close();
                rstCodVisBue.close();
                stmCodVisBue=null;
                rstCodVisBue=null;
            }
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }

    public ArrayList getDatCodVisBueObl() {
        return arlDatCodVisBueObl;
    }

    public int getIntNumVisBueObl() {
        return intNumVisBueObl;
    }

    
    /***
     * Función que permite conocer el valor permitido autorizar por usuario.
     * @param conRef
     * @param codigoEmpresa
     * @param codigoLocal
     * @param codigoTipoDocumento
     * @param codigoDocumento
     * @param codigoUsuario
     * @return objValAutUsr
     * <BR> NULL: Cuando no tiene configuración de vistos buenos.
     * <BR> Valor autorizado: El usuario si tiene configuración en vistos buenos, minimo 0.
     */
    public Object getValAutPorUsr(Connection conRef, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento, int codigoUsuario)
    {
        Statement stmNumVisBue;
        ResultSet rstNumVisBue;
        Object objValAutUsr=null; 
        try{
            if(conRef!=null){
                stmNumVisBue=conRef.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_visBue, a1.co_usr";
                strSQL+="     , (CASE WHEN a1.nd_valAut IS NULL THEN 0 ELSE a1.nd_ValAut END) as nd_valAut";
                strSQL+=" FROM tbr_visBueUsrTipDoc AS a1";
                strSQL+=" WHERE a1.st_reg='A' ";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento + "";
                strSQL+=" AND a1.co_usr=" + codigoUsuario + "";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_visBue";
                System.out.println("getValDocAutUsr: " + strSQL);
                rstNumVisBue=stmNumVisBue.executeQuery(strSQL);
                if(rstNumVisBue.next()){
                    objValAutUsr=rstNumVisBue.getObject("nd_valAut");
                }
                stmNumVisBue.close();
                stmNumVisBue=null;
                rstNumVisBue.close();
                rstNumVisBue=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        System.out.println("objValAutUsr: "+objValAutUsr);
        return objValAutUsr;
    }
    
    
    /**
     * Función que permite conocer si el Pedido genera sólo ajuste de negativos (sólo items faltantes, ningún sobrante)
     * @param conRef
     * @param intCodigoEmpresa       : Código de empresa de Pedido
     * @param intCodigoLocal         : Código de local de Pedido
     * @param intCodigoTipoDocumento : Código de tipo de documento de Pedido
     * @param intCodigoDocumento     : Código de documento de Pedido
     * @return true                  : Existen items con SOLO cantidades faltantes
     * <BR> false                    : Caso contrario
     */
    public boolean isAjusteFaltanteCompleto(Connection conRef, int intCodigoEmpresa, int intCodigoLocal, int intCodigoTipoDocumento, int intCodigoDocumento){
        boolean blnRes=false;
        Statement stmConPenTrs;
        ResultSet rstConPenTrs;
        try{
            if(conRef!=null){
                stmConPenTrs=conRef.createStatement();
                strSQL="";
                strSQL+=" SELECT b1.co_empCon, b1.co_locCon, b1.co_tipdocCon, b1.co_docCon, b1.co_regCon, b1.nd_canCon, b1.co_itmMae, b1.co_itmGrp";
                strSQL+="      , b1.nd_canSolTrs, b1.co_empTrs, b1.co_locTrs, b1.co_tipDocTrs, b1.co_docTrs, b1.nd_canTrs, b1.co_itmEmp";
                strSQL+="      , b1.co_empIngImp, b1.co_locIngImp, b1.co_tipDocIngImp, b1.co_docIngImp, b1.co_regIngImp";
                strSQL+="      , b1.ne_numDocIngImp, b1.fe_docIngImp, b1.tx_numDoc2IngImp, b1.nd_canIngImp, b1.nd_cosUni";
                strSQL+="      , b1.tx_nomItm, b1.tx_uniMed, b1.nd_preUniImp, b1.nd_pesItmKgr, b1.tx_codAlt, b1.tx_codAlt2";
                strSQL+=" FROM(";
                strSQL+="         SELECT b1.co_emp AS co_empCon, b1.co_loc AS co_locCon, b1.co_tipdoc AS co_tipdocCon, b1.co_doc AS co_docCon";
                strSQL+="              , b1.co_reg AS co_regCon, CASE WHEN b1.nd_stkCon IS NULL THEN 0 ELSE b1.nd_stkCon END AS nd_canCon, b1.co_itmMae, b1.co_itm AS co_itmGrp";
                strSQL+="              , CASE WHEN b2.nd_can IS NULL THEN 0 ELSE b2.nd_can END AS nd_canSolTrs";
                strSQL+="              , b3.co_empRel AS co_empTrs, b3.co_locRel AS co_locTrs, b3.co_tipdocRel AS co_tipDocTrs";
                strSQL+="              , b3.co_docRel AS co_docTrs, CASE WHEN ABS(b3.nd_canTrs) IS NULL THEN 0 ELSE ABS(b3.nd_canTrs) END AS nd_canTrs";
                strSQL+="              , b3.co_itm AS co_itmEmp";
                strSQL+="              , b4.co_emp AS co_empIngImp, b4.co_loc AS co_locIngImp, b4.co_tipDoc AS co_tipDocIngImp, b4.co_doc AS co_docIngImp, b4.co_reg AS co_regIngImp";
                strSQL+="              , b4.ne_numDoc AS ne_numDocIngImp, b4.fe_doc AS fe_docIngImp, b4.tx_numDoc2 AS tx_numDoc2IngImp";
                strSQL+="              , CASE WHEN b4.nd_can IS NULL THEN 0 ELSE b4.nd_can END AS nd_canIngImp, b4.nd_cosUni";
                strSQL+="              , b4.tx_nomItm, b4.tx_uniMed, b4.nd_preUniImp, b4.nd_pesItmKgr, b4.tx_codAlt, b4.tx_codAlt2";
                strSQL+="         FROM(";
                //CONTEO
                strSQL+="                SELECT b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc, b1.co_reg, b1.nd_stkCon, b1.co_itmMae, b1.co_itm";
                strSQL+="                     , b1.co_empRel, b1.co_locRel, b1.co_tipDocRel, b1.co_docRel";
                strSQL+="                FROM(";
                strSQL+="                        SELECT a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg, a2.nd_stkCon, a2.co_itmMae, a2.co_itm";
                strSQL+="                             , a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel";
                strSQL+="                        FROM(";
                strSQL+="                                SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+="                                FROM tbm_cabOrdConInv AS a1";
                strSQL+="                                WHERE a1.co_empRel=" + intCodigoEmpresa + " AND a1.co_locRel=" + intCodigoLocal + "";
                strSQL+="                                AND a1.co_tipDocRel=" + intCodigoTipoDocumento + " AND a1.co_docRel=" + intCodigoDocumento + "";
                strSQL+="                        ) AS a1";
                strSQL+="                        LEFT OUTER JOIN(";
                strSQL+="                                SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a8.nd_stkCon,a6.co_itmMae, a2.co_itm";
                strSQL+="                                     , a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel";
                strSQL+="                                FROM tbm_cabOrdConInv AS a1 ";
                strSQL+="                                RIGHT OUTER JOIN ";
                strSQL+="                                (   tbm_detOrdConInv AS a2 RIGHT OUTER JOIN tbm_conInv AS a8";
                strSQL+="                                    ON a2.co_emp=a8.co_emp AND a2.co_loc=a8.co_locrel AND a2.co_tipdoc=a8.co_tipdocrel";
                strSQL+="                                    AND a2.co_doc=a8.co_docrel AND a2.co_reg=a8.co_regrel";
                strSQL+="                                    RIGHT OUTER JOIN tbm_inv AS a4 ON a8.co_emp=a4.co_emp AND a8.co_itm=a4.co_itm";
                strSQL+="                                    RIGHT OUTER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm";
                strSQL+="                                )";
                strSQL+="                                ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="                                WHERE a1.st_reg='A'";
                strSQL+="                        ) AS a2";
                strSQL+="                        ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="                        UNION ALL";
                strSQL+="                        SELECT a3.co_emp, a3.co_locRel, a3.co_tipDocRel, a3.co_docRel, a3.co_regRel, a3.nd_stkCon, a3.co_itmMae, a3.co_itm";
                strSQL+="                             , a3.co_empIngImp, a3.co_locIngImp, a3.co_tipDocIngImp, a3.co_docIngImp";
                strSQL+="                        FROM(";
                strSQL+="                                SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+="                                FROM tbm_cabOrdConInv AS a1";
                strSQL+="                                WHERE a1.co_empRel=" + intCodigoEmpresa + " AND a1.co_locRel=" + intCodigoLocal + "";
                strSQL+="                                AND a1.co_tipDocRel=" + intCodigoTipoDocumento + " AND a1.co_docRel=" + intCodigoDocumento + "";
                strSQL+="                        ) AS a1";
                strSQL+="                        INNER JOIN(";
                strSQL+="                                SELECT a1.co_emp, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a1.co_regRel, a1.nd_stkCon, -1 AS co_itmMae, a1.co_itm";
                strSQL+="                                     , a2.co_empRel AS co_empIngImp, a2.co_locRel AS co_locIngImp, a2.co_tipDocRel AS co_tipDocIngImp, a2.co_docRel AS co_docIngImp";
                strSQL+="                                FROM tbm_conInv AS a1 INNER JOIN tbm_cabOrdConInv AS a2";
                strSQL+="                                ON a1.co_emp=a2.co_emp AND a1.co_locRel=a2.co_loc AND a1.co_tipDocRel=a2.co_tipDoc AND a1.co_docRel=a2.co_doc";
                strSQL+="                                WHERE a1.co_itm IS NULL";
                strSQL+="                        ) AS a3";
                strSQL+="                        ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_locRel AND a1.co_tipDoc=a3.co_tipDocRel AND a1.co_doc=a3.co_docRel";
                strSQL+="                ) AS b1";
                strSQL+="         ) AS b1";
                strSQL+="         LEFT OUTER JOIN(";
                //SOLICITUD
                strSQL+="                SELECT a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, SUM(a1.nd_can) AS nd_can, a1.tx_codAlt";
                strSQL+="                     , a1.co_itmMae, a1.tx_codAlt2";
                strSQL+="                     , a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv";
                strSQL+="                FROM(";
                strSQL+="                        SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a2.tx_codAlt, a4.co_itmMae, ABS(a2.nd_can) AS nd_can, a2.co_itm";
                strSQL+="                             , a3.tx_codAlt2, a3.tx_nomItm, a2.tx_unimed, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc";
                strSQL+="                             , a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel";
                strSQL+="                             , a5.co_empRel AS co_empRelCabMovInv, a5.co_locRel AS co_locRelCabMovInv, a5.co_tipDocRel AS co_tipDocRelCabMovInv, a5.co_docRel AS co_docRelCabMovInv";
                strSQL+="                        FROM (tbr_cabMovInv AS a5 INNER JOIN tbm_cabMovInv AS a1";
                strSQL+="                              ON a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc AND a5.co_tipDoc=a1.co_tipDoc AND a5.co_doc=a1.co_doc)";
                strSQL+="                        INNER JOIN tbm_detMovInv AS a2";
                strSQL+="                        ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="                        INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                strSQL+="                        INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                strSQL+="                        WHERE a2.nd_can<0";
                strSQL+="                        AND a5.co_empRel=" + intCodigoEmpresa + " AND a5.co_locRel=" + intCodigoLocal + "";
                strSQL+="                        AND a5.co_tipDocRel=" + intCodigoTipoDocumento + " AND a5.co_docRel=" + intCodigoDocumento + "";
                strSQL+="                ) AS a1";
                strSQL+="                GROUP BY a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, a1.tx_codAlt, a1.co_itmMae, a1.tx_codAlt2";
                strSQL+="                       , a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv";
                strSQL+="                ORDER BY a1.co_itmMae";
                strSQL+="         ) AS b2";
                strSQL+="         ON b1.co_empRel=b2.co_empRelCabMovInv AND b1.co_locRel=b2.co_locRelCabMovInv AND b1.co_tipDocRel=b2.co_tipDocRelCabMovInv AND b1.co_docRel=b2.co_docRelCabMovInv AND b1.co_itmMae=b2.co_itmMae";
                strSQL+="         LEFT OUTER JOIN(";
                //TRANSFERENCIA
                strSQL+="                SELECT a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, SUM(a1.nd_can) AS nd_canTrs, a1.tx_codAlt";
                strSQL+="                     , a1.co_itmMae, a1.tx_codAlt2, a1.co_itm, a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv";
                strSQL+="                FROM(";
                strSQL+="                        SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a2.tx_codAlt, a4.co_itmMae, a2.nd_can, a2.co_itm";
                strSQL+="                             , a3.tx_codAlt2, a3.tx_nomItm, a2.tx_unimed, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc";
                strSQL+="                             , a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel";
                strSQL+="                             , a5.co_empRel AS co_empRelCabMovInv, a5.co_locRel AS co_locRelCabMovInv, a5.co_tipDocRel AS co_tipDocRelCabMovInv, a5.co_docRel AS co_docRelCabMovInv";
                strSQL+="                        FROM (tbr_cabMovInv AS a5 INNER JOIN tbm_cabMovInv AS a1";
                strSQL+="                              ON a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc AND a5.co_tipDoc=a1.co_tipDoc AND a5.co_doc=a1.co_doc)";
                strSQL+="                        INNER JOIN tbm_detMovInv AS a2";
                strSQL+="                        ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="                        INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                strSQL+="                        INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                strSQL+="                        WHERE a2.nd_can<0";
                strSQL+="                        AND a5.co_empRel=" + intCodigoEmpresa + " AND a5.co_locRel=" + intCodigoLocal + "";
                strSQL+="                        AND a5.co_tipDocRel=" + intCodigoTipoDocumento + " AND a5.co_docRel=" + intCodigoDocumento + "";
                strSQL+="                ) AS a1";
                strSQL+="                GROUP BY a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, a1.tx_codAlt, a1.co_itmMae, a1.tx_codAlt2, a1.co_itm";
                strSQL+="                       , a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv";
                strSQL+="                ORDER BY a1.co_itmMae";
                strSQL+="         ) AS b3";
                strSQL+="         ON b1.co_empRel=b3.co_empRel AND b1.co_locRel=b3.co_locRel AND b1.co_tipDocRel=b3.co_tipDocRel AND b1.co_docRel=b3.co_docRel AND b1.co_itmMae=b3.co_itmMae";
                strSQL+="         LEFT OUTER JOIN(";
                //PEDIDO
                strSQL+="                SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a1.tx_numDoc2";
                strSQL+="                     , a2.nd_can, a4.co_itmMae, a2.nd_cosUni, a2.tx_codAlt, a2.tx_codAlt2";
                strSQL+="                     , a2.tx_nomItm, a2.tx_uniMed, a2.nd_preUniImp, a3.nd_pesItmKgr";
                strSQL+="                FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2";
                strSQL+="                ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="                INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                strSQL+="                INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                strSQL+="                WHERE a1.co_emp=" + intCodigoEmpresa + " AND a1.co_loc=" + intCodigoLocal + "";
                strSQL+="                AND a1.co_tipDoc=" + intCodigoTipoDocumento + " AND a1.co_doc=" + intCodigoDocumento + "";
                strSQL+="                AND a1.st_reg='A'";
                strSQL+="         ) AS b4";
                strSQL+="         ON b1.co_empRel=b4.co_emp AND b1.co_locRel=b4.co_loc AND b1.co_tipDocRel=b4.co_tipDoc AND b1.co_docRel=b4.co_doc AND b1.co_itmMae=b4.co_itmMae";
                strSQL+="         ORDER BY b1.co_reg";
                strSQL+=" ) AS b1";
                strSQL+=" WHERE (b1.nd_canIngImp<b1.nd_canCon)";
                strSQL+=" ORDER BY b1.co_regCon";
                System.out.println("isAjusteFaltanteCompleto: " + strSQL);
                //Si retorna algo el ResultSet    -> No es sólo faltante el ajuste que se va a generar debe retornar FALSE
                //Si NO retorna nada el ResultSet -> Es sólo faltantes el ajuste que se va a generar y debe retornar TRUE
                rstConPenTrs=stmConPenTrs.executeQuery(strSQL);
                if(!rstConPenTrs.next()){
                    blnRes=true;
                }
                rstConPenTrs.close();
                rstConPenTrs=null;
                stmConPenTrs.close();
                stmConPenTrs=null;                
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite obtener el nombre del campo que se desea actualizar
     * @param indiceNombreCampo 
     *          <HTML>
     *              <BR>  0: Tabla - Nota de Pedido Previa
     *              <BR>  1: Tabla - Nota de Pedido
     *              <BR>  2: Tabla - Pedido Embarcado
     *              <BR>  3: Tabla - Ingreso por Importación
     *              <BR>  4: Tabla - Ajuste por Importación
     *              <BR>  5: Tabla - Cargo a Pagar (Pedido Embarcado)
     *              <BR>  6: Tabla - Documentos por Pagar
     *              <BR>  7: Tabla - Aranceles
     *              <BR>  8: Tabla - Provisiones
     *          </HTML>
     * @return true: Si se pudo obtener el nombre del campo
     * <BR> false: Caso contrario
     */
    public boolean getNomTblDocRelMovImp(int indiceNombreCampo){
        boolean blnRes=true;
        try{
            switch(indiceNombreCampo){
                case 0:
                    //Tabla - Nota de Pedido Previa
                    strNomTblDocMovImp="tbm_cabNotPedPreImp";
                    strNomTblDocRelMovImp="";
                    //Campos
                    strCodEmpMovImp="co_empRelCabNotPedPreImp";
                    strCodLocMovImp="co_locRelCabNotPedPreImp";
                    strCodTipDocMovImp="co_tipDocRelCabNotPedPreImp";
                    strCodDocMovImp="co_docRelCabNotPedPreImp";
                    strCodRegMovImp="";
                    strCodCarPagMovImp="";
                    //Campos - Relacionados
                    strCodEmpRelMovImp="";
                    strCodLocRelMovImp="";
                    strCodTipDocRelMovImp="";
                    strCodDocRelMovImp="";
                    strCodRegRelMovImp="";
                    strCodCarPagRelMovImp="";
                    break;
                case 1:
                    //Tabla - Nota de Pedido
                    strNomTblDocMovImp="tbm_cabNotPedPreImp";
                    strNomTblDocRelMovImp="tbm_cabNotPedImp";
                    //Campos
                    strCodEmpMovImp="co_empRelCabNotPedPreImp";//Nota de Pedido Previa
                    strCodLocMovImp="co_locRelCabNotPedPreImp";//Nota de Pedido Previa
                    strCodTipDocMovImp="co_tipDocRelCabNotPedPreImp";//Nota de Pedido Previa
                    strCodDocMovImp="co_docRelCabNotPedPreImp";//Nota de Pedido Previa
                    strCodRegMovImp="";//Nota de Pedido Previa
                    strCodCarPagMovImp="";//Nota de Pedido Previa
                    strCodEmpRelMovImp="co_empRelCabNotPedImp";//Nota de Pedido
                    strCodLocRelMovImp="co_locRelCabNotPedImp";//Nota de Pedido
                    strCodTipDocRelMovImp="co_tipDocRelCabNotPedImp";//Nota de Pedido
                    strCodDocRelMovImp="co_docRelCabNotPedImp";//Nota de Pedido
                    strCodRegRelMovImp="";//Nota de Pedido
                    strCodCarPagRelMovImp="";//Nota de Pedido
                    break;
                case 2:
                    //Tabla - Pedido Embarcado
                    strNomTblDocMovImp="tbm_cabNotPedImp";
                    strNomTblDocRelMovImp="tbm_cabPedEmbImp";
                    //Campos
                    strCodEmpMovImp="co_empRelCabNotPedImp";//Nota de Pedido
                    strCodLocMovImp="co_locRelCabNotPedImp";//Nota de Pedido
                    strCodTipDocMovImp="co_tipDocRelCabNotPedImp";//Nota de Pedido
                    strCodDocMovImp="co_docRelCabNotPedImp";//Nota de Pedido
                    strCodRegMovImp="";//Nota de Pedido
                    strCodCarPagMovImp="";//Nota de Pedido
                    strCodEmpRelMovImp="co_empRelCabPedEmbImp";//Pedido Embarcado
                    strCodLocRelMovImp="co_locRelCabPedEmbImp";//Pedido Embarcado
                    strCodTipDocRelMovImp="co_tipDocRelCabPedEmbImp";//Pedido Embarcado
                    strCodDocRelMovImp="co_docRelCabPedEmbImp";//Pedido Embarcado
                    strCodRegRelMovImp="";//Pedido Embarcado
                    strCodCarPagRelMovImp="";//Pedido Embarcado
                    break;
                case 3:
                    //Tabla - Ingreso por Importación
                    strNomTblDocMovImp="tbm_cabPedEmbImp";
                    strNomTblDocRelMovImp="tbm_cabMovInv";
                    //Campos
                    strCodEmpRelMovImp="co_empRelCabPedEmbImp";//Pedido Embarcado
                    strCodLocRelMovImp="co_locRelCabPedEmbImp";//Pedido Embarcado
                    strCodTipDocRelMovImp="co_tipDocRelCabPedEmbImp";//Pedido Embarcado
                    strCodDocRelMovImp="co_docRelCabPedEmbImp";//Pedido Embarcado
                    strCodRegRelMovImp="";//Pedido Embarcado
                    strCodCarPagRelMovImp="";//Pedido Embarcado
                    strCodEmpRelMovImp="co_empRelCabIngImp";//Ingreso por Importación
                    strCodLocRelMovImp="co_locRelCabIngImp";//Ingreso por Importación
                    strCodTipDocRelMovImp="co_tipDocRelCabIngImp";//Ingreso por Importación
                    strCodDocRelMovImp="co_docRelCabIngImp";//Ingreso por Importación
                    strCodRegRelMovImp="";//Ingreso por Importación
                    strCodCarPagRelMovImp="";//Ingreso por Importación
                    break;
                case 4:
                    //Tabla - Ajuste por Importación
                    strNomTblDocMovImp="tbm_cabMovInv";
                    strNomTblDocRelMovImp="tbm_cabMovInv";
                    //Campos
                    strCodEmpMovImp="co_empRelCabIngImp";//Ingreso por Importación
                    strCodLocMovImp="co_locRelCabIngImp";//Ingreso por Importación
                    strCodTipDocMovImp="co_tipDocRelCabIngImp";//Ingreso por Importación
                    strCodDocMovImp="co_docRelCabIngImp";//Ingreso por Importación
                    strCodRegMovImp="";//Ingreso por Importación
                    strCodCarPagMovImp="";//Ingreso por Importación
                    strCodEmpRelMovImp="co_empRelCabAjuImp";//Ajuste por Importación
                    strCodLocRelMovImp="co_locRelCabAjuImp";//Ajuste por Importación
                    strCodTipDocRelMovImp="co_tipDocRelCabAjuImp";//Ajuste por Importación
                    strCodDocRelMovImp="co_docRelCabAjuImp";//Ajuste por Importación
                    strCodRegRelMovImp="";//Ajuste por Importación
                    strCodCarPagRelMovImp="";//Ajuste por Importación
                    break;
                case 5:
                    //Tabla - Cargo a Pagar (Pedido Embarcado)
                    strNomTblDocMovImp="tbm_cabPedEmbImp";
                    strNomTblDocRelMovImp="tbm_carPagPedEmbImp";
                    //Campos
                    strCodEmpMovImp="co_empRelCabCarPag";//Pedido Embarcado
                    strCodLocMovImp="co_locRelCabCarPag";//Pedido Embarcado
                    strCodTipDocMovImp="co_tipDocRelCabCarPag";//Pedido Embarcado
                    strCodDocMovImp="co_docRelCabCarPag";//Pedido Embarcado
                    strCodRegMovImp="";//Pedido Embarcado
                    strCodCarPagMovImp="";//Pedido Embarcado
                    strCodEmpRelMovImp="co_empRelCabCarPag";//Cargo a Pagar (Pedido Embarcado)
                    strCodLocRelMovImp="co_locRelCabCarPag";//Cargo a Pagar (Pedido Embarcado)
                    strCodTipDocRelMovImp="co_tipDocRelCabCarPag";//Cargo a Pagar (Pedido Embarcado)
                    strCodDocRelMovImp="co_docRelCabCarPag";//Cargo a Pagar (Pedido Embarcado)
                    strCodRegRelMovImp="co_regRelCabCarPag";//Cargo a Pagar (Pedido Embarcado)
                    strCodCarPagRelMovImp="co_carPagRelCabCarPag";//Cargo a Pagar (Pedido Embarcado)
                    break;
                case 6:
                    //Tabla - Documentos por Pagar
                    strNomTblDocMovImp="tbm_carPagPedEmbImp";
                    strNomTblDocRelMovImp="tbm_cabMovInv";
                    //Campos
                    strCodEmpMovImp="co_empRelCabCarPag";//Cargo a Pagar (Pedido Embarcado)
                    strCodLocMovImp="co_locRelCabCarPag";//Cargo a Pagar (Pedido Embarcado)
                    strCodTipDocMovImp="co_tipDocRelCabCarPag";//Cargo a Pagar (Pedido Embarcado)
                    strCodDocMovImp="co_docRelCabCarPag";//Cargo a Pagar (Pedido Embarcado)
                    strCodRegMovImp="co_regRelCabCarPag";//Cargo a Pagar (Pedido Embarcado)
                    strCodCarPagMovImp="co_carPagRelCabCarPag";//Cargo a Pagar (Pedido Embarcado)
                    strCodEmpRelMovImp="co_empRelCabPagPro";//Documentos por Pagar
                    strCodLocRelMovImp="co_locRelCabPagPro";//Documentos por Pagar
                    strCodTipDocRelMovImp="co_tipdocRelCabPagPro";//Documentos por Pagar
                    strCodDocRelMovImp="co_docRelCabPagPro";//Documentos por Pagar
                    strCodRegRelMovImp="co_regRelCabPagPro";//Documentos por Pagar
                    strCodCarPagRelMovImp="co_carPagRelCabPagPro";//Documentos por Pagar
                    break;
                case 7:
                    //Tabla - Aranceles
                    strNomTblDocMovImp="tbm_cabNotPedImp";
                    strNomTblDocRelMovImp="tbm_cabDia";
                    //Campos
                    strCodEmpMovImp="co_empRelCabNotPedImp";//Nota de Pedido
                    strCodLocMovImp="co_locRelCabNotPedImp";//Nota de Pedido
                    strCodTipDocMovImp="co_tipDocRelCabNotPedImp";//Nota de Pedido
                    strCodDocMovImp="co_docRelCabNotPedImp";//Nota de Pedido
                    strCodRegMovImp="";//Nota de Pedido
                    strCodCarPagMovImp="";//Nota de Pedido
                    strCodEmpRelMovImp="co_empRelCabDia";//Aranceles
                    strCodLocRelMovImp="co_locRelCabDia";//Aranceles
                    strCodTipDocRelMovImp="co_tipdocRelCabDia";//Aranceles
                    strCodDocRelMovImp="co_diaRelCabDia";//Aranceles
                    strCodRegRelMovImp="";//Aranceles
                    strCodCarPagRelMovImp="";//Aranceles
                    break;                    
                case 8:
                    //Tabla - Provisiones
                    strNomTblDocMovImp="tbm_cabPedEmbImp";
                    strNomTblDocRelMovImp="tbm_cabDia";
                    //Campos
                    strCodEmpMovImp="co_empRelCabPedEmbImp";//Nota de Pedido
                    strCodLocMovImp="co_locRelCabPedEmbImp";//Nota de Pedido
                    strCodTipDocMovImp="co_tipDocRelCabPedEmbImp";//Nota de Pedido
                    strCodDocMovImp="co_docRelCabPedEmbImp";//Nota de Pedido
                    strCodRegMovImp="";//Nota de Pedido
                    strCodCarPagMovImp="";//Nota de Pedido
                    strCodEmpRelMovImp="co_empRelCabDia";//Provisiones
                    strCodLocRelMovImp="co_locRelCabDia";//Provisiones
                    strCodTipDocRelMovImp="co_tipdocRelCabDia";//Provisiones
                    strCodDocRelMovImp="co_diaRelCabDia";//Provisiones
                    strCodRegRelMovImp="";//Provisiones
                    strCodCarPagRelMovImp="";//Provisiones
                    break;
                default:
                    //Tabla - Ninguna
                    strNomTblDocMovImp="";
                    strNomTblDocRelMovImp="";
                    //Campos
                    strCodEmpMovImp="";
                    strCodLocMovImp="";
                    strCodTipDocMovImp="";
                    strCodDocMovImp="";
                    strCodRegMovImp="";
                    strCodCarPagMovImp="";
                    strCodEmpRelMovImp="";
                    strCodLocRelMovImp="";
                    strCodTipDocRelMovImp="";
                    strCodDocRelMovImp="";
                    strCodRegRelMovImp="";
                    strCodCarPagRelMovImp="";
                    break;
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(cmpPad, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }    

    public boolean getCodDocPro(Connection conexion, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento){
        boolean blnRes=true;
        Statement stmCodDoc;
        ResultSet rstCodDoc;
        intCodDocPro=-1;
        try{
            if(conexion!=null){
                stmCodDoc=conexion.createStatement();
                strSQL="";
                strSQL+=" SELECT MAX(a1.co_dia) AS ne_ultCodDia";
                strSQL+=" FROM tbm_cabDia AS a1";
                strSQL+=" WHERE a1.co_emp=" + codigoEmpresa + "";
                strSQL+=" AND a1.co_loc=" + codigoLocal + "";
                strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento + "";
                System.out.println("getCodDocPro: " + strSQL);
                rstCodDoc=stmCodDoc.executeQuery(strSQL);
                if(rstCodDoc.next()){
                    intCodDocPro=rstCodDoc.getInt("ne_ultCodDia");
                }
                if(intCodDocPro==-1)
                    blnRes=false;
                else
                    intCodDocPro++;
                System.out.println("intCodDocPro: " + intCodDocPro);
                stmCodDoc.close();
                stmCodDoc=null;
                rstCodDoc.close();
                rstCodDoc=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
   
    public int getIntCodDocPro() {
        return intCodDocPro;
    }
    
    /**
     * Función que obtiene el porcentaje de ISD, de acuerdo al registro vigente.
     * @return El porcentaje de ISD Ej.: 0.05
     */
    public BigDecimal getPorcentajeISD(int codigoEmpresa, int codigoLocal)
    {
        bgdPorISD=BigDecimal.ZERO;
        try{
            Date datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            bgdPorISD=(objParSis.getPorImp(codigoEmpresa, codigoLocal, INT_COD_CFG_IMP_ISD, datFecAux)).divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP); 
        }
        catch(Exception e){
           objUti.mostrarMsgErr_F1(cmpPad, e );
        }
        return bgdPorISD;    
    }
    
    /**
     * Función que valida si ya existe un ingreso de importación del pedido Embarcado.
     * @param codigoEmpresaPedidoEmbarcado
     * @param codigoLocalPedidoEmbarcado
     * @param codigoTipoDocumentoPedidoEmbarcado
     * @param codigoDocumentoPedidoEmbarcado
     * @return true: Si existe Ingreso por Importación
     * <BR> false: Caso contrario.
     */
    public boolean existeIngresoImportacion(int codigoEmpresaPedidoEmbarcado, int codigoLocalPedidoEmbarcado
                                          , int codigoTipoDocumentoPedidoEmbarcado, int codigoDocumentoPedidoEmbarcado) {
        boolean blnRes=false;
        Connection conIngImp;
        Statement stmIngImp;
        ResultSet rstIngImp;
        try{
            conIngImp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conIngImp!=null){
                stmIngImp=conIngImp.createStatement();
                strSQL ="";
                strSQL+=" SELECT *FROM tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_emprelpedembimp=" + codigoEmpresaPedidoEmbarcado;
                strSQL+=" AND a1.co_locrelpedembimp=" + codigoLocalPedidoEmbarcado;
                strSQL+=" AND a1.co_tipdocrelpedembimp=" + codigoTipoDocumentoPedidoEmbarcado;
                strSQL+=" AND a1.co_docrelpedembimp=" + codigoDocumentoPedidoEmbarcado;
                strSQL+=" AND a1.st_reg NOT IN('I', 'E')";
                System.out.println("existeIngresoImportacion: " + strSQL);
                rstIngImp=stmIngImp.executeQuery(strSQL);
                if(rstIngImp.next())
                    blnRes=true;

                conIngImp.close();
                conIngImp=null;
                stmIngImp.close();
                stmIngImp=null;
                rstIngImp.close();
                rstIngImp=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=true;
        }
        return blnRes;
    }
    
    /**
     * Función que permite obtener las Notas de Pedido Previa pendientes de aplicar Nota de Pedido
     * @param conexion Conexión a la base de datos
     * @return true Si se pudo realizar la operación
     * <BR> fslse Caso contrario
     */
    public boolean getOpenNotaPedidoPrevia(Connection conexion){
        boolean blnRes=true;
        Connection conPed=conexion;
        Statement stmPed;
        ResultSet rstPed;
        try{
            arlDatInsPed.clear();
           if(conPed!=null){
               stmPed=conPed.createStatement();
               //NOTA DE PEDIDO PREVIA SIN NOTA DE PEDIDO
               strSQL="";
               strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.ne_numDoc, a1.tx_numDoc2";
               strSQL+=" FROM tbm_cabNotPedPreImp AS a1";
               strSQL+=" WHERE a1.st_reg='A'";
               strSQL+=" AND a1.st_cie='N'";
               strSQL+=" ORDER BY a1.fe_doc, a1.tx_numDoc2";
               rstPed=stmPed.executeQuery(strSQL);
               while(rstPed.next()){
                   arlRegInsPed=new ArrayList();
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_EMP, rstPed.getInt("co_emp"));
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_LOC, rstPed.getInt("co_loc"));
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_TIP_DOC, rstPed.getInt("co_tipDoc"));
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_DOC, rstPed.getInt("co_doc"));
                   arlRegInsPed.add(INT_ARL_INS_PED_NUM_PED, rstPed.getString("tx_numDoc2"));
                   arlDatInsPed.add(arlRegInsPed);
               }
               stmPed.close();
               stmPed=null;
               rstPed.close();
               rstPed=null;
           }            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Función que permite conocer si el Proveedor del Exterior tiene cuenta bancaria asociada
     * @param conexion Conexión a la base de datos
     * @param codigoPrvExt Código del Proveedor del Exterior
     * @return 
     */
    public boolean isCuentaBancoPrvExt(Connection conexion, int codigoEmpresa, int codigoPrvExt){
        boolean blnRes=false;
        Statement stmCtaBcoPrvExt;
        ResultSet rstCtaBcoPrvExt;
        try{
            if(conexion!=null){
                stmCtaBcoPrvExt=conexion.createStatement();
                strSQL="";
                strSQL+=" SELECT COUNT(b1.co_ctaBan) AS co_ctaBan FROM(";
                strSQL+=" 	SELECT a1.co_exp, a2.co_ctaBan";
                strSQL+=" 	FROM tbm_expImp AS a1 LEFT OUTER JOIN tbm_ctaBan AS a2";
                strSQL+=" 	ON a1.co_exp=a2.co_ctaExp";
                strSQL+=" 	WHERE a2.co_emp=" + codigoEmpresa + "";
                strSQL+=" 	AND a1.co_exp=" + codigoPrvExt + "";
                strSQL+=" 	ORDER BY a1.co_exp";
                strSQL+=" ) AS b1";
                rstCtaBcoPrvExt=stmCtaBcoPrvExt.executeQuery(strSQL);
                if(rstCtaBcoPrvExt.next()){
                    if(rstCtaBcoPrvExt.getInt("")==0)
                        blnRes=true;
                }
                
                stmCtaBcoPrvExt.close();
                stmCtaBcoPrvExt=null;
                rstCtaBcoPrvExt.close();
                rstCtaBcoPrvExt=null;
            }            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    /**
     * Función que permite obtener las Notas de Pedido pendientes de aplicar Pedidos Embarcados
     * @param conexion Conexión a la base de datos
     * @return true Si se pudo realizar la operación
     * <BR> fslse Caso contrario
     */
    public boolean getOpenNotaPedido(Connection conexion){
        boolean blnRes=true;
        Connection conPed=conexion;
        Statement stmPed;
        ResultSet rstPed;
        try{
            arlDatInsPed.clear();
           if(conPed!=null){
               stmPed=conPed.createStatement();
               strSQLNotPedOpen="";
               //NOTA DE PEDIDO SIN PEDIDO EMBARCADO
               strSQL="";
               strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.co_Exp, a3.tx_nom AS tx_nomExp, a1.co_imp, a4.tx_nom AS tx_nomEmpImp";
               strSQL+=" 	     , a1.ne_numDoc, a1.tx_numDoc2, a1.tx_numDoc3, CAST('' AS CHARACTER VARYING) AS tx_numDoc4";
               strSQL+="             , (CASE WHEN a1.tx_numDoc3 IS NOT NULL THEN a1.tx_numDoc3 ELSE a1.tx_numDoc2 END) AS tx_numPed, a1.co_ctaPas AS co_cta"; 
               strSQL+="             , '0' AS st_ins, CAST('Nota de Pedido' AS CHARACTER VARYING) AS tx_insPedImp, a1.st_notPedLis";
               strSQL+=" 	FROM (tbm_cabNotPedImp AS a1 INNER JOIN tbm_expImp AS a3 ON a1.co_exp=a3.co_exp)";
               strSQL+=" 	INNER JOIN tbm_cabNotPedPreImp AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_locRel AND a1.co_tipDoc=a2.co_tipDocRel AND a1.co_doc=a2.co_docRel";
               strSQL+="        INNER JOIN tbm_emp AS a4 ON a1.co_imp=a4.co_emp";
               strSQL+=" 	WHERE a1.st_reg='A' AND a2.st_reg='A'";
               strSQL+="        AND (a1.st_doc='A' OR a1.st_doc IS NULL)";  //Solo pedidos activos, no reversión, ni inactivos
               strSQL+=" 	AND a2.st_cie='S' AND a1.st_cie='N' ";
               //strSQL+=" 	AND a1.st_notPedLis='N' ";                  //Se comenta aquí y se valida en el programa de pagos al exterior.
               //strSQL+=" 	AND a1.co_ctaPas IS NOT NULL";              //Se comenta aquí y se valida en el programa de pagos al exterior.
               strSQL+=" 	AND a1.co_exp NOT IN (32)";                 //Solo Importaciones, NO Compras Locales.               
               strSQL+="        ORDER BY a1.fe_doc, a1.tx_numDoc2";
               strSQLNotPedOpen=strSQL;
               rstPed=stmPed.executeQuery(strSQL);
               while(rstPed.next()){
                   arlRegInsPed=new ArrayList();
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_EMP, rstPed.getInt("co_emp"));
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_LOC, rstPed.getInt("co_loc"));
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_TIP_DOC, rstPed.getInt("co_tipDoc"));
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_DOC, rstPed.getInt("co_doc"));
                   arlRegInsPed.add(INT_ARL_INS_PED_NUM_PED, rstPed.getString("tx_numDoc2"));
                   arlDatInsPed.add(arlRegInsPed);
               }
               stmPed.close();
               stmPed=null;
               rstPed.close();
               rstPed=null;
           }            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    /**
     * Función que permite obtener los Pedidos Embarcados pendientes de aplicar Ingresos por Importación
     * @param conexion Conexión a la base de datos
     * @return true Si se pudo realizar la operación
     * <BR> fslse Caso contrario
     */
    public boolean getOpenPedidoEmbarcado(Connection conexion){
        boolean blnRes=true;
        Connection conPed=conexion;
        Statement stmPed;
        ResultSet rstPed;
        try{
            arlDatInsPed.clear();
           if(conPed!=null){
               stmPed=conPed.createStatement();
               strSQLPedEmbOpen="";
               //PEDIDO EMBARCADO SIN INGRESO POR IMPORTACION
               strSQL="";
               strSQL+="	SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.co_Exp, b1.tx_nomExp, b1.co_imp, b1.tx_nomEmpImp";
               strSQL+="	     , b1.ne_numDoc, b1.tx_numDoc2, b1.tx_numDoc3, b1.tx_numDoc4, b1.tx_numPed, b1.co_cta";
               strSQL+=" 	     , '1' AS st_ins, CAST('Pedido Embarcado' AS CHARACTER VARYING) AS tx_insPedImp, CAST('N' AS CHARACTER VARYING) AS st_notPedLis";
               strSQL+="        FROM(";
               strSQL+="            SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.co_Exp, a2.tx_nom AS tx_nomExp, a1.co_imp, a3.tx_nom AS tx_nomEmpImp";
               strSQL+="                 , a1.ne_numDoc, a1.tx_numDoc2, CAST('' AS CHARACTER VARYING) AS tx_numDoc3, a1.tx_numDoc4, a1.co_ctaPas AS co_cta, a1.st_doc";
               strSQL+="                 , (CASE WHEN a1.tx_numDoc4 IS NOT NULL THEN a1.tx_numDoc4 ELSE a1.tx_numDoc2 END) AS tx_numPed ";
               strSQL+=" 	    FROM tbm_cabPedEmbImp AS a1 INNER JOIN tbm_expImp AS a2 ON(a1.co_exp=a2.co_exp)";
               strSQL+="            INNER JOIN tbm_emp AS a3 ON a1.co_imp=a3.co_emp";
               strSQL+="	    WHERE a1.st_reg='A'";
               strSQL+="            AND (a1.st_doc='A' OR a1.st_doc IS NULL)";  //Solo pedidos activos, no reversión, ni inactivos
               strSQL+=" 	    AND a1.co_exp NOT IN (32)";                 //Solo Importaciones, NO Compras Locales.               
               strSQL+=" 	) AS b1";
               strSQL+=" 	LEFT OUTER JOIN(";
               strSQL+=" 		 SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_empRelPedEmbImp, a1.co_locRelPedEmbImp, a1.co_tipDocRelPedEmbImp, a1.co_docRelPedEmbImp";
               strSQL+=" 		 FROM tbm_cabMovInv AS a1";
               strSQL+=" 		 WHERE a1.co_empRelPedEmbImp IS NOT NULL";
               strSQL+=" 		 AND a1.co_locRelPedEmbImp IS NOT NULL";
               strSQL+=" 		 AND a1.co_tipDocRelPedEmbImp IS NOT NULL";
               strSQL+=" 		 AND a1.co_docRelPedEmbImp IS NOT NULL";
               strSQL+=" 	) AS b2";
               strSQL+=" 	ON b1.co_emp=b2.co_empRelPedEmbImp AND b1.co_loc=b2.co_locRelPedEmbImp AND b1.co_tipDoc=b2.co_tipDocRelPedEmbImp AND b1.co_doc=b2.co_docRelPedEmbImp";
               strSQL+=" 	WHERE b2.co_empRelPedEmbImp IS NULL";               
               strSQL+="        ORDER BY b1.fe_doc, b1.tx_numDoc2";
               strSQLPedEmbOpen=strSQL;
               rstPed=stmPed.executeQuery(strSQL);
               while(rstPed.next()){
                   arlRegInsPed=new ArrayList();
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_EMP, rstPed.getInt("co_emp"));
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_LOC, rstPed.getInt("co_loc"));
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_TIP_DOC, rstPed.getInt("co_tipDoc"));
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_DOC, rstPed.getInt("co_doc"));
                   arlRegInsPed.add(INT_ARL_INS_PED_NUM_PED, rstPed.getString("tx_numDoc2"));
                   arlDatInsPed.add(arlRegInsPed);
               }
               stmPed.close();
               stmPed=null;
               rstPed.close();
               rstPed=null;
           }            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    /**
     * Función que permite obtener los Ingresos por Importación
     * @param conexion Conexión a la base de datos
     * @return true Si se pudo realizar la operación
     * <BR> fslse Caso contrario
     */
    public boolean getOpenIngresoImportacion(Connection conexion){
        boolean blnRes=true;
        Connection conPed=conexion;
        Statement stmPed;
        ResultSet rstPed;
        try{
            arlDatInsPed.clear();
           if(conPed!=null){
               stmPed=conPed.createStatement();
               strSQLIngImpOpen="";
               //INGRESO POR IMPORTACION
               strSQL="";
               strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.co_Exp, a2.tx_nom AS tx_nomExp, a1.co_emp AS co_imp, a3.tx_nom AS tx_nomEmpImp";
               strSQL+="	     , a1.ne_numDoc, a1.tx_numDoc2, CAST('' AS CHARACTER VARYING) AS tx_numDoc3, CAST('' AS CHARACTER VARYING) AS tx_numDoc4";
               strSQL+="             , a1.tx_numDoc2 AS tx_numPed, CAST( -1 AS INTEGER) AS co_cta "; //El ingreso por Importador siempre tendrá Cuenta.
               strSQL+=" 	     , '2' AS st_ins, CAST('Ingreso por Importación' AS CHARACTER VARYING) AS tx_insPedImp, CAST('N' AS CHARACTER VARYING) AS st_notPedLis";
               strSQL+=" 	FROM tbm_cabMovInv AS a1 INNER JOIN tbm_expImp AS a2 ON(a1.co_exp=a2.co_exp)";
               strSQL+="        INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
               strSQL+=" 	WHERE a1.co_empRelPedEmbImp IS NOT NULL";
               strSQL+=" 	AND a1.co_locRelPedEmbImp IS NOT NULL";
               strSQL+=" 	AND a1.co_tipDocRelPedEmbImp IS NOT NULL";
               strSQL+=" 	AND a1.co_docRelPedEmbImp IS NOT NULL";
               strSQL+=" 	AND a1.st_ciePagExt IN('P', 'E')";
               strSQL+=" 	AND a1.st_reg='A'";
               strSQL+=" 	AND a1.co_exp NOT IN (32)";                 //Solo Importaciones, NO Compras Locales.
               strSQL+="        ORDER BY a1.fe_doc, a1.tx_numDoc2";
               strSQLIngImpOpen=strSQL;
               rstPed=stmPed.executeQuery(strSQL);
               while(rstPed.next()){
                   arlRegInsPed=new ArrayList();
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_EMP, rstPed.getInt("co_emp"));
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_LOC, rstPed.getInt("co_loc"));
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_TIP_DOC, rstPed.getInt("co_tipDoc"));
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_DOC, rstPed.getInt("co_doc"));
                   arlRegInsPed.add(INT_ARL_INS_PED_NUM_PED, rstPed.getString("tx_numDoc2"));
                   arlDatInsPed.add(arlRegInsPed);
               }
               stmPed.close();
               stmPed=null;
               rstPed.close();
               rstPed=null;
           }            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
      
    /**
     * Función que permite obtener datos de la instancia del pedido. Puede ser Nota de Pedido, Pedido Embarcado o Ingreso por Importación.
     * El campo st_ins devuelve la instancia del pedido requerido.
     * @param conexion Conexión a la base de datos
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    public boolean getUltimaInstanciaPedidos(Connection conexion){
        boolean blnRes=true;
        Connection conPed=conexion;
        Statement stmPed;
        ResultSet rstPed;
        try{
            arlDatInsPed.clear();
            if(conPed!=null){
               stmPed=conPed.createStatement();
               strSQLDatPedUltIns=""; 
               //Armar sentencia SQL
               strSQL="";
               strSQL+=" SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.fe_doc, c1.co_exp, c1.tx_nomExp, c1.co_imp, c1.tx_nomEmpImp";
               strSQL+="      , c1.ne_numDoc, c1.tx_numDoc2, c1.tx_numDoc3, c1.tx_numDoc4, c1.tx_numPed, c1.co_cta, c1.st_ins, c1.tx_insPedImp, c1.st_notPedLis";
               strSQL+=" FROM(";
               /*Nota de Pedido*/
               strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.co_Exp, a3.tx_nom AS tx_nomExp, a1.co_imp, a4.tx_nom AS tx_nomEmpImp";
               strSQL+=" 	     , a1.ne_numDoc, a1.tx_numDoc2, a1.tx_numDoc3, CAST('' AS CHARACTER VARYING) AS tx_numDoc4";
               strSQL+="             , (CASE WHEN a1.tx_numDoc3 IS NOT NULL THEN a1.tx_numDoc3 ELSE a1.tx_numDoc2 END) AS tx_numPed, a1.co_ctaPas AS co_cta"; 
               strSQL+="             , '0' AS st_ins, CAST('Nota de Pedido' AS CHARACTER VARYING) AS tx_insPedImp, a1.st_notPedLis";
               strSQL+=" 	FROM (tbm_cabNotPedImp AS a1 INNER JOIN tbm_expImp AS a3 ON a1.co_exp=a3.co_exp)";
               strSQL+=" 	INNER JOIN tbm_cabNotPedPreImp AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_locRel AND a1.co_tipDoc=a2.co_tipDocRel AND a1.co_doc=a2.co_docRel";
               strSQL+="        INNER JOIN tbm_emp AS a4 ON a1.co_imp=a4.co_emp";
               strSQL+=" 	WHERE a1.st_reg='A' AND a2.st_reg='A'";
               strSQL+="        AND (a1.st_doc='A' OR a1.st_doc IS NULL)";  //Solo pedidos activos, no reversión, ni inactivos
               strSQL+=" 	AND a2.st_cie='S' AND a1.st_cie='N' ";
               //strSQL+=" 	AND a1.st_notPedLis='N' AND a1.co_ctaPas IS NOT NULL"; //Se comenta aquí y se valida en el programa de pagos al exterior.
               strSQL+=" 	AND a1.co_exp NOT IN (32)";                 //Solo Importaciones, NO Compras Locales.
               /*Pedido Embarcado*/
               strSQL+="        UNION";
               strSQL+="	SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.co_Exp, b1.tx_nomExp, b1.co_imp, b1.tx_nomEmpImp";
               strSQL+="	     , b1.ne_numDoc, b1.tx_numDoc2, b1.tx_numDoc3, b1.tx_numDoc4, b1.tx_numPed, b1.co_cta";
               strSQL+=" 	     , '1' AS st_ins, CAST('Pedido Embarcado' AS CHARACTER VARYING) AS tx_insPedImp, CAST('N' AS CHARACTER VARYING) AS st_notPedLis";
               strSQL+="        FROM(";
               strSQL+="            SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.co_Exp, a2.tx_nom AS tx_nomExp, a1.co_imp, a3.tx_nom AS tx_nomEmpImp";
               strSQL+="                 , a1.ne_numDoc, a1.tx_numDoc2, CAST('' AS CHARACTER VARYING) AS tx_numDoc3, a1.tx_numDoc4, a1.co_ctaPas AS co_cta, a1.st_doc";
               strSQL+="                 , (CASE WHEN a1.tx_numDoc4 IS NOT NULL THEN a1.tx_numDoc4 ELSE a1.tx_numDoc2 END) AS tx_numPed ";
               strSQL+=" 	    FROM tbm_cabPedEmbImp AS a1 INNER JOIN tbm_expImp AS a2 ON(a1.co_exp=a2.co_exp)";
               strSQL+="            INNER JOIN tbm_emp AS a3 ON a1.co_imp=a3.co_emp";
               strSQL+="	    WHERE a1.st_reg='A'";
               strSQL+="            AND (a1.st_doc='A' OR a1.st_doc IS NULL)";  //Solo pedidos activos, no reversión, ni inactivos
               strSQL+=" 	    AND a1.co_exp NOT IN (32)";                 //Solo Importaciones, NO Compras Locales.               
               strSQL+=" 	) AS b1";
               strSQL+=" 	LEFT OUTER JOIN(";
               strSQL+=" 		 SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_empRelPedEmbImp, a1.co_locRelPedEmbImp, a1.co_tipDocRelPedEmbImp, a1.co_docRelPedEmbImp";
               strSQL+=" 		 FROM tbm_cabMovInv AS a1";
               strSQL+=" 		 WHERE a1.co_empRelPedEmbImp IS NOT NULL";
               strSQL+=" 		 AND a1.co_locRelPedEmbImp IS NOT NULL";
               strSQL+=" 		 AND a1.co_tipDocRelPedEmbImp IS NOT NULL";
               strSQL+=" 		 AND a1.co_docRelPedEmbImp IS NOT NULL";
               strSQL+=" 	) AS b2";
               strSQL+=" 	ON b1.co_emp=b2.co_empRelPedEmbImp AND b1.co_loc=b2.co_locRelPedEmbImp AND b1.co_tipDoc=b2.co_tipDocRelPedEmbImp AND b1.co_doc=b2.co_docRelPedEmbImp";
               strSQL+=" 	WHERE b2.co_empRelPedEmbImp IS NULL";
               /*Ingreso por Importación*/
               strSQL+=" 	UNION";
               strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.co_Exp, a2.tx_nom AS tx_nomExp, a1.co_emp AS co_imp, a3.tx_nom AS tx_nomEmpImp";
               strSQL+="	     , a1.ne_numDoc, a1.tx_numDoc2, CAST('' AS CHARACTER VARYING) AS tx_numDoc3, CAST('' AS CHARACTER VARYING) AS tx_numDoc4";
               strSQL+="             , a1.tx_numDoc2 AS tx_numPed, CAST( -1 AS INTEGER) AS co_cta "; //El ingreso por Importador siempre tendrá Cuenta.
               strSQL+=" 	     , '2' AS st_ins, CAST('Ingreso por Importación' AS CHARACTER VARYING) AS tx_insPedImp, CAST('N' AS CHARACTER VARYING) AS st_notPedLis";
               strSQL+=" 	FROM tbm_cabMovInv AS a1 INNER JOIN tbm_expImp AS a2 ON(a1.co_exp=a2.co_exp)";
               strSQL+="        INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
               strSQL+=" 	WHERE a1.co_empRelPedEmbImp IS NOT NULL";
               strSQL+=" 	AND a1.co_locRelPedEmbImp IS NOT NULL";
               strSQL+=" 	AND a1.co_tipDocRelPedEmbImp IS NOT NULL";
               strSQL+=" 	AND a1.co_docRelPedEmbImp IS NOT NULL";
               strSQL+=" 	AND a1.st_ciePagExt IN('P', 'E')";
               strSQL+=" 	AND a1.st_reg='A'";
               strSQL+=" 	AND a1.co_exp NOT IN (32)";                 //Solo Importaciones, NO Compras Locales.
               strSQL+=" ) AS c1";
               strSQLDatPedUltIns=strSQL; 
               System.out.println("getOpenInstanciaPedido: "+strSQL);
               rstPed=stmPed.executeQuery(strSQL);
               while(rstPed.next()){
                   arlRegInsPed=new ArrayList();
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_EMP,     rstPed.getInt("co_emp"));
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_LOC,     rstPed.getInt("co_loc"));
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_TIP_DOC, rstPed.getInt("co_tipDoc"));
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_DOC,     rstPed.getInt("co_doc"));
                   arlRegInsPed.add(INT_ARL_INS_PED_NUM_PED,     rstPed.getString("tx_numPed"));
                   arlRegInsPed.add(INT_ARL_INS_PED_INS_IMP,     rstPed.getString("st_ins"));
                   arlRegInsPed.add(INT_ARL_INS_PED_COD_IMP,     rstPed.getString("co_imp"));
                   arlDatInsPed.add(arlRegInsPed);
               }
               stmPed.close();
               stmPed=null;
               rstPed.close();
               rstPed=null;
           }            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Función que permite obtener datos de la última instancia del pedido seleccionado.
     * Puede ser Nota de Pedido, Pedido Embarcado o Ingreso por Importación.
     * @param conexion Conexión a la base de datos
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    public boolean getInstanciaPedidoSeleccionado(Connection conexion, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento){
        boolean blnRes=true;
        Connection conPed=conexion;
        String strConUltIns="";
        try{ 
           if(conPed!=null){
               strSQLPedSelUltIns="";
               getUltimaInstanciaPedidos(conPed);  
               //Armar sentencia SQL
               strConUltIns ="";
               strConUltIns+=""+getSQLUltimaInstanciaPedido();
               strConUltIns+=" WHERE c1.co_emp="+codigoEmpresa;
               strConUltIns+=" AND c1.co_loc="+codigoLocal;
               strConUltIns+=" AND c1.co_tipDoc="+codigoTipoDocumento;
               strConUltIns+=" AND c1.co_doc="+codigoDocumento;
               strSQLPedSelUltIns=strConUltIns;
           }
        }
        catch(Exception e){ 
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /***
     * Función retorna arraylist con instancias de pedidos.
     * @return 
     */
    public ArrayList getArregloInstanciaPedido() {
        return arlDatInsPed;
    }
    
    public String getSQLUltimaInstanciaPedido(){
        return strSQLDatPedUltIns; 
    }
    
    public String getSQLInstanciaPedidoSeleccionado(){
        return strSQLPedSelUltIns;
    } 
    
    public String getSQLNotaPedidoInstanciaOpen(){
        return strSQLNotPedOpen; 
    }
    
    public String getSQLPedidoEmbarcadoInstanciaOpen(){
        return strSQLPedEmbOpen; 
    }
        
    public String getSQLIngresoImportacionInstanciaOpen(){
        return strSQLIngImpOpen; 
    }
    
    


    
    
    
    
    
                                       
}
