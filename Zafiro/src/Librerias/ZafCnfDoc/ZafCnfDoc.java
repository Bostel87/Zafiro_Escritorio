package Librerias.ZafCnfDoc;
import GenOD.ZafGenOdPryTra;
import Librerias.ZafCfgSer.ZafCfgSer;
import Librerias.ZafGenDocIngEgrInvRes.ZafGenDocIngEgrInvRes;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.ResultSet;
import java.sql.Statement;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import Librerias.ZafGenFacAut.ZafGenFacAut;
import Librerias.ZafResInv.ZafResInv;

/**
 *
 * @author sistemas3
 */
public class ZafCnfDoc 
{
    //Constantes
    private ArrayList arlDatCnf;//Arreglo recibido como parametro
    private static final int INT_ARL_DAT_COD_EMP_CNF=0;
    private static final int INT_ARL_DAT_COD_LOC_CNF=1;
    private static final int INT_ARL_DAT_COD_TIP_DOC_CNF=2;
    private static final int INT_ARL_DAT_COD_DOC_CNF=3;
    private static final int INT_ARL_DAT_COD_REG_CNF=4;
    private static final int INT_ARL_DAT_COD_ITM_GRP_CNF=5;
    private static final int INT_ARL_DAT_COD_ITM_EMP_CNF=6;
    private static final int INT_ARL_DAT_COD_ITM_MAE_CNF=7;
    private static final int INT_ARL_DAT_CAN_ITM_CNF=8;
    private static final int INT_ARL_DAT_COD_EMP_ORI=9;
    private static final int INT_ARL_DAT_COD_LOC_ORI=10;
    private static final int INT_ARL_DAT_COD_TIP_DOC_ORI=11;
    private static final int INT_ARL_DAT_COD_DOC_ORI=12;
    private static final int INT_ARL_DAT_COD_REG_ORI=13;
    private static final int INT_ARL_DAT_CAN_ITM_NOT_CNF=14;
    private static final int INT_ARL_DAT_COD_BOD_EMP=15;
    private static final int INT_ARL_DAT_EST_ING_EGR_MER_BOD=16;
    
    private ArrayList arlRegTrsEgrIng, arlDatTrsEgrIng;
    private static final int INT_ARL_DAT_TRS_COD_EMP=0;
    private static final int INT_ARL_DAT_TRS_COD_LOC=1;
    private static final int INT_ARL_DAT_TRS_COD_TIP_DOC=2;
    private static final int INT_ARL_DAT_TRS_COD_DOC=3;
    private static final int INT_ARL_DAT_TRS_COD_REG=4;
    private static final int INT_ARL_DAT_TRS_COD_EMP_REL=5;
    private static final int INT_ARL_DAT_TRS_COD_LOC_REL=6;
    private static final int INT_ARL_DAT_TRS_COD_TIP_DOC_REL=7;
    private static final int INT_ARL_DAT_TRS_COD_DOC_REL=8;    
    private static final int INT_ARL_DAT_TRS_COD_REG_REL=9;
    private static final int INT_ARL_DAT_TRS_COD_ITM_EMP=10;
    private static final int INT_ARL_DAT_TRS_CAN=11;
    
    //ArrayList: Reservas - Para solo pedir estos datos los demas la clase los obtiene.
    private static final int INT_ARL_DAT_RES_COD_EMP=0;
    private static final int INT_ARL_DAT_RES_COD_LOC=1;
    private static final int INT_ARL_DAT_RES_COD_BOD_EMP=2;
    private static final int INT_ARL_DAT_RES_COD_ITM_EMP=3;
    private static final int INT_ARL_DAT_RES_CAN=4;
    private static final int INT_ARL_DAT_RES_COS_UNI=5;
    private static final int INT_ARL_DAT_RES_COD_SEG=6 ;
    private static final int INT_ARL_DAT_COD_REG=7 ;
    private static final int INT_ARL_DAT_NOM_ITM=8 ;
    
    //ArrayList: JM Cantidades Remotas
    private ArrayList arlDatIng,  arlRegIng;
    private static final int INT_ARL_COD_EMP=0;
    private static final int INT_ARL_COD_LOC=1; 
    private static final int INT_ARL_COD_TIP_DOC=2;
    private static final int INT_ARL_COD_DOC=3; 
    private static final int INT_ARL_COD_REG=4;
    private static final int INT_ARL_COD_REG_REL=5; 
    private static final int INT_ARL_COD_ITM=6;
    private static final int INT_ARL_CAN_ITM=7;    
  
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafGenOdPryTra objGenOdPryTra;
    private ZafGenFacAut objGenFacAut;
    private ZafCfgSer objCfgSer;
    private ZafResInv objResInv;                                /* JM 7/Agosto/2017 */
    private java.awt.Component cmpPad;
    private Statement stm;
    private ResultSet rst, rstCab;
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.

    private Object objCamActIngDetMovInvCnfIng;//campo que permite saber el campo que debo validar si tiene dato al momento de hacer confirmación de ingreso - tbm_detMovInv
    private Object objCamActIngInvBodCnfIng;//campo que permite saber el campo que debo validar si tiene dato al momento de hacer confirmación de ingreso - tbm_invBod
    private int intCodBodGrpCnf;
    private int intCodEmp, intCodLoc,intCodTipDoc, intCodDoc;
    private String strSQL="";
    private String strVer="ZafCnfDoc v 0.02";  // JM 24/Oct/2018

    
    public ZafCnfDoc(ZafParSis obj, int bodegaConfirmaGrupo, java.awt.Component parent){
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            cmpPad=parent;
            arlDatCnf=new ArrayList();
            arlDatTrsEgrIng=new ArrayList();
            objGenOdPryTra=new ZafGenOdPryTra();
            objGenFacAut=new ZafGenFacAut(objParSis, cmpPad);            
            objResInv = new Librerias.ZafResInv.ZafResInv(objParSis, cmpPad);
            intCodBodGrpCnf=bodegaConfirmaGrupo;
            objCamActIngDetMovInvCnfIng=null;
            objCamActIngInvBodCnfIng=null;
            System.out.println(strVer);
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
    }
    
    public ZafCnfDoc(ZafParSis obj, java.awt.Component parent){
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            cmpPad=parent;
            arlDatCnf=new ArrayList();
            arlDatTrsEgrIng=new ArrayList();
            objGenOdPryTra=new ZafGenOdPryTra();
            objGenFacAut=new ZafGenFacAut(objParSis, cmpPad);
            objResInv = new Librerias.ZafResInv.ZafResInv(objParSis, cmpPad);
            objCamActIngDetMovInvCnfIng=null;
            objCamActIngInvBodCnfIng=null;
            System.out.println(strVer);
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
    }
    
    /**
     * Función que permite setear la confirmación del ingreso/egreso
     * @param codigoEmpresa          Código de empresa donde se guardará el documento
     * @param codigoLocal            Código de local donde se guardará el documento
     * @param codigoTipoDocumento    Código de tipo de documento donde se guardará el documento
     * @param codigoDocumento        Código de documento donde se guardará el documento
     * @param codigoRegistro         Código de registro donde se guardará el documento
     * @param signoCampo             Indica el signo que se debe aplicar para realizar la operación
     * @param codigoItem             Código de registro donde se guardará el documento
     * @param cantidad               Cantidad nunca recibida
     * @param codigoBodega           Código bodega donde se va a confirmar la mercadería
     * @return true: Si se pudo efectuar la operación
     * <BR> false: caso contrario
     */
    public boolean setCamNotIngEgrMerBodega(Connection con, ArrayList arregloDatos){
        boolean blnRes=true;
        arlDatCnf.clear();
        arlDatCnf=arregloDatos;
        String strSQLUpd="";
        String strEstIngEgrMerBod="";
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int i=0; i<arlDatCnf.size(); i++){
                    strEstIngEgrMerBod=objUti.getObjectValueAt(arlDatCnf, i, INT_ARL_DAT_EST_ING_EGR_MER_BOD)==null?"":objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_EST_ING_EGR_MER_BOD);
                    if( (strEstIngEgrMerBod.equals("")) || (strEstIngEgrMerBod.equals("N"))){
                        strSQL="";                
                        strSQL+=" UPDATE tbm_detMovInv";
                        strSQL+=" SET nd_canNunRec=nd_canNunRec+" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_CAN_ITM_NOT_CNF) + "";
                        strSQL+=" , nd_canPen=0";
                        strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_EMP_ORI) + "";
                        strSQL+=" AND co_loc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_LOC_ORI) + "";
                        strSQL+=" AND co_tipDoc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_TIP_DOC_ORI) + "";
                        strSQL+=" AND co_doc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_DOC_ORI) + "";
                        strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_REG_ORI) + "";
                        strSQL+=";";
//                        strSQL+=" UPDATE tbm_invBod";
//                        strSQL+=" SET nd_canDis=nd_canDis+" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_CAN_ITM_NOT_CNF) + "";
//                        strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_EMP_ORI) + "";
//                        strSQL+=" AND co_itm=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_ITM_EMP_CNF) + "";
//                        strSQL+=" AND co_bod=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_BOD_EMP) + "";
//                        strSQL+=";";
                        strSQLUpd+=strSQL;
                    }
                }
                stm.executeUpdate(strSQLUpd);
                stm.close();
                stm=null;
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
     * Función que permite confirmar documentos de ingresos/egresos
     * @param con                   Conexión a la base de datos
     * @param arregloDatoItem        <HTML>Información del item:
     *                                      <BR>Código del item de grupo
     *                                      <BR>Código del item de empresa
     *                                      <BR>Código del item maestro
     *                                      <BR>Código del registro
     *                                  </HTML>
     * @param       tipoMovimiento      <HTML> Información del item
     *                                      <BR>E: Egreso
     *                                      <BR>I: Ingreso
     *                                  </HTML>
     * @param codigoBodegaEmpresa
     * @return 
     */
    public boolean setCnfIngresoEgreso(Connection con, ArrayList arregloDatos, String tipoMovimiento, int codigoBodegaEmpresa){
        boolean blnRes=true;
        String strTipMov=tipoMovimiento;
        arlDatCnf.clear();
        arlDatCnf=arregloDatos;
        
        Object objCanAntEgrDetMovInv="";
        Object objCanActEgrDetMovInv="";
        Object objCanAntEgrInvBod="";
        Object objCanActEgrInvBod="";
        Object objCanAntIngDetMovInv="";
        Object objCanActIngDetMovInv="";
        Object objCanAntIngInvBod="";
        Object objCanActIngInvBod="";        
        String strSigCamAntEgr="", strSigCamActEgr="";
        String strSigCamAntIng="", strSigCamActIng="";
        
        String strSQLUpd="";
        BigDecimal bdeCanCnfDBEgr=BigDecimal.ZERO, bdeCanNunRecDBEgr=BigDecimal.ZERO;
        
        try{
            if(con!=null){
                stm=con.createStatement();
                
                if(strTipMov.equals("I")){//COINDE & COINBO   (Mercadería local)
                    strSigCamAntIng="-";//anterior -
                    strSigCamActIng="+";//actual +
                }
                
                if(strTipMov.equals("E")){//COEGDE & COEGBO   (Mercadería local)
                    strSigCamAntEgr="-";//anterior +
                    strSigCamActEgr="+";//actual -
                }

                if(arlDatCnf.size()>0){
                    for(int i=0; i<arlDatCnf.size(); i++){
                        strSQL="";
                        strSQL="";
                        strSQL+=" SELECT c1.co_emp, c1.co_loc, c1.co_bod, c1.tx_camantegrdetmovinv, c1.tx_camactegrdetmovinv";
                        strSQL+=" , c1.tx_camantegrinvbod, c1.tx_camactegrinvbod, c1.tx_camantingdetmovinv";
                        strSQL+=" , c1.tx_camactingdetmovinv, c1.tx_camantinginvbod, c1.tx_camactinginvbod";
                        strSQL+=" FROM(";
                        strSQL+=" 		SELECT b1.co_emp, b1.co_loc, b1.co_bod, b1.tx_camantegrdetmovinv, b1.tx_camactegrdetmovinv";
                        strSQL+=" 		 , b1.tx_camantegrinvbod, b1.tx_camactegrinvbod, b1.tx_camantingdetmovinv";
                        strSQL+=" 		 , b1.tx_camactingdetmovinv, b1.tx_camantinginvbod, b1.tx_camactinginvbod";
                        strSQL+=" 		 FROM(";
                        strSQL+=" 			 SELECT a2.co_emp, a2.co_loc, a2.co_bod, a1.tx_camantegrdetmovinv, a1.tx_camactegrdetmovinv";
                        strSQL+=" 			 , a1.tx_camantegrinvbod, a1.tx_camactegrinvbod, a1.tx_camantingdetmovinv";
                        strSQL+=" 			 , a1.tx_camactingdetmovinv, a1.tx_camantinginvbod, a1.tx_camactinginvbod";
                        strSQL+=" 			 FROM tbm_cfgConInv AS a1 INNER JOIN tbm_cfgConInvTipDocBod AS a2";
                        strSQL+=" 			 ON a1.co_cfg=a2.co_cfg";
                        strSQL+=" 			 WHERE a2.co_tipdoc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_TIP_DOC_CNF) + "";
                        strSQL+=" 			 AND a1.tx_tipmov='" + tipoMovimiento + "'";
                        strSQL+=" 			 AND a1.st_reg='A' AND a2.st_reg='A'";
                        strSQL+=" 		) AS b1";
                        strSQL+=" 		INNER JOIN(";
                        strSQL+="                     	SELECT b1.co_empGrp, b1.co_bodGrp, b2.co_emp, b2.co_bod, b1.tx_nom AS tx_nomBod";
                        strSQL+="                      	FROM(";
                        strSQL+="                      		SELECT a2.co_empGrp, a2.co_bodGrp, a1.tx_nom";
                        strSQL+="                      		FROM tbm_bod AS a1 INNER JOIN tbr_bodEmpBodGrp AS a2";
                        strSQL+="                      		ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                        strSQL+="                      		WHERE a1.st_reg='A'";
                        strSQL+="                                   AND a2.co_emp=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_EMP_CNF) + "";
                        strSQL+="                                   AND a2.co_bod=" + codigoBodegaEmpresa + "";
                        strSQL+="                      	) AS b1";
                        strSQL+="                      	INNER JOIN(";
                        strSQL+="                      		SELECT a2.co_empGrp, a2.co_bodGrp, a2.co_emp, a2.co_bod";
                        strSQL+="                      		FROM tbr_bodEmpBodGrp AS a2";
                        strSQL+="                      	) AS b2";
                        strSQL+="                      	ON b1.co_empGrp=b2.co_empGrp AND b1.co_bodGrp=b2.co_bodGrp";
                        strSQL+=" 			GROUP BY b1.co_empGrp, b1.co_bodGrp, b2.co_emp, b2.co_bod, b1.tx_nom";
                        strSQL+=" 		) AS b2";
                        strSQL+=" 		ON b1.co_emp=b2.co_emp AND b1.co_bod=b2.co_bod";
                        strSQL+=" ) AS c1";
                        strSQL+=" INNER JOIN(";
                        strSQL+="	SELECT b1.co_empGrp, b1.co_bodGrp, b2.co_emp, b2.co_bod, b1.tx_nom AS tx_nomBod";
                        strSQL+=" 	FROM(";
                        strSQL+=" 		SELECT a2.co_empGrp, a2.co_bodGrp, a1.tx_nom";
                        strSQL+=" 		FROM tbm_bod AS a1 INNER JOIN tbr_bodEmpBodGrp AS a2";
                        strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                        strSQL+=" 		WHERE a1.st_reg='A'";
                        strSQL+="           AND a1.co_emp=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_EMP_CNF) + "";
                        strSQL+="           AND a1.co_bod=" + codigoBodegaEmpresa + "";
                        strSQL+=" 	) AS b1";
                        strSQL+=" 	INNER JOIN(";
                        strSQL+=" 		SELECT a2.co_empGrp, a2.co_bodGrp, a2.co_emp, a2.co_bod";
                        strSQL+=" 		FROM tbr_bodEmpBodGrp AS a2";
                        strSQL+=" 	) AS b2";
                        strSQL+=" 	ON b1.co_empGrp=b2.co_empGrp AND b1.co_bodGrp=b2.co_bodGrp";
                        strSQL+=" ) AS c2";
                        strSQL+=" ON c1.co_emp=c2.co_emp AND c1.co_bod=c2.co_bod";
                        strSQL+=" GROUP BY c1.co_emp, c1.co_loc, c1.co_bod, c1.tx_camantegrdetmovinv, c1.tx_camactegrdetmovinv";
                        strSQL+=" , c1.tx_camantegrinvbod, c1.tx_camactegrinvbod, c1.tx_camantingdetmovinv";
                        strSQL+=" , c1.tx_camactingdetmovinv, c1.tx_camantinginvbod, c1.tx_camactinginvbod";
                        System.out.println("Tabla config: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            //Egresos
                            objCanAntEgrDetMovInv=rst.getString("tx_camantegrdetmovinv");
                            objCanActEgrDetMovInv=rst.getString("tx_camactegrdetmovinv");
                            objCanAntEgrInvBod=rst.getString("tx_camantegrinvbod");
                            objCanActEgrInvBod=rst.getString("tx_camactegrinvbod");
                            //Ingresos
                            objCanAntIngDetMovInv=rst.getString("tx_camantingdetmovinv");
                            objCanActIngDetMovInv=rst.getString("tx_camactingdetmovinv");
                            objCanAntIngInvBod=rst.getString("tx_camantinginvbod");
                            objCanActIngInvBod=rst.getString("tx_camactinginvbod");
                        }
                        
                        //tbm_detMovInv
                        strSQL="";
                        strSQL+=" UPDATE tbm_detMovInv";
                        strSQL+=" SET ";
                        
                        //I : Ingresos de cualquier tipo
                        if(objCanAntIngDetMovInv!=null)
                            strSQL+="" + objCanAntIngDetMovInv + "=(CASE WHEN " + objCanAntIngDetMovInv + " IS NULL THEN 0 ELSE " + objCanAntIngDetMovInv + " END)" + strSigCamAntIng + "(" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_CAN_ITM_CNF) + ")";
                        if(objCanActIngDetMovInv!=null){
                            if(objCanAntIngDetMovInv!=null)
                                strSQL+=",";
                            strSQL+="" + objCanActIngDetMovInv + "=(CASE WHEN " + objCanActIngDetMovInv + " IS NULL THEN 0 ELSE " + objCanActIngDetMovInv + " END)" + strSigCamActIng + "(" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_CAN_ITM_CNF) + ")";
                        }
                        //E : Egresos de cualquier tipo
                        if(objCanAntEgrDetMovInv!=null)
                            strSQL+="" + objCanAntEgrDetMovInv + "=(CASE WHEN " + objCanAntEgrDetMovInv + " IS NULL THEN 0 ELSE " + objCanAntEgrDetMovInv + " END)" + strSigCamAntEgr + "(" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_CAN_ITM_CNF) + ")";
                        if(objCanActEgrDetMovInv!=null){
                            if(objCanAntEgrDetMovInv!=null)
                                strSQL+=",";
                            strSQL+="" + objCanActEgrDetMovInv + "=(CASE WHEN " + objCanActEgrDetMovInv + " IS NULL THEN 0 ELSE " + objCanActEgrDetMovInv + " END)" + strSigCamActEgr + "(" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_CAN_ITM_CNF) + ")";
                        }                        
                        
                        strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_EMP_ORI) + " AND co_loc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_LOC_ORI) + "";
                        strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_TIP_DOC_ORI) + " AND co_doc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_DOC_ORI) + "";
                        strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_REG_ORI) + "";
                        strSQL+=";";
                        //tbm_invBod
                        strSQL+=" UPDATE tbm_invBod";
                        strSQL+=" SET ";
                        //I : Ingresos de cualquier tipo
                        if(objCanAntIngInvBod!=null)
                            strSQL+="" + objCanAntIngInvBod + "=(CASE WHEN " + objCanAntIngInvBod + " IS NULL THEN 0 ELSE " + objCanAntIngInvBod + " END)" + strSigCamAntIng + "(" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_CAN_ITM_CNF) + ")";
                        if(objCanActIngInvBod!=null){
                            if(objCanAntIngInvBod!=null)
                                strSQL+=",";
                            strSQL+="" + objCanActIngInvBod + "=(CASE WHEN " + objCanActIngInvBod + " IS NULL THEN 0 ELSE " + objCanActIngInvBod + " END)" + strSigCamActIng + "(" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_CAN_ITM_CNF) + ")";
                        }
                        //E : Egresos de cualquier tipo
                        if(objCanAntEgrInvBod!=null)
                            strSQL+="" + objCanAntEgrInvBod + "=(CASE WHEN " + objCanAntEgrInvBod + " IS NULL THEN 0 ELSE " + objCanAntEgrInvBod + " END)" + strSigCamAntEgr + "(" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_CAN_ITM_CNF) + ")";
                        if(objCanActEgrInvBod!=null){
                            if(objCanAntEgrInvBod!=null)
                                strSQL+=",";
                            strSQL+="" + objCanActEgrInvBod + "=(CASE WHEN " + objCanActEgrInvBod + " IS NULL THEN 0 ELSE " + objCanActEgrInvBod + " END)" + strSigCamActEgr + "(" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_CAN_ITM_CNF) + ")";
                        }
                        strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_EMP_ORI) + "";
                        strSQL+=" AND co_itm=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_ITM_EMP_CNF) + "";
                        strSQL+=" AND co_bod=" + codigoBodegaEmpresa + "";                        
                        strSQL+=";";
                        strSQLUpd+=strSQL;
                        System.out.println("setCnfIngresoEgreso -1 : " + strSQLUpd);
                        stm.executeUpdate(strSQLUpd);
                        strSQLUpd="";
                        
                        //cantidad pendiente
                        strSQL="";
                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                        strSQL+=" , CASE WHEN a1.nd_can IS NULL THEN 0 ELSE a1.nd_can END AS nd_can";
                        strSQL+=" , CASE WHEN a1.nd_canCon IS NULL THEN 0 ELSE a1.nd_canCon END AS nd_canCon";
                        strSQL+=" , CASE WHEN a1.nd_canNunRec IS NULL THEN 0 ELSE a1.nd_canNunRec END AS nd_canNunRec";
                        strSQL+=" FROM tbm_detMovInv AS a1";
                        strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_EMP_ORI) + "";
                        strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_LOC_ORI) + "";
                        strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_TIP_DOC_ORI) + "";
                        strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_DOC_ORI) + "";
                        strSQL+=" AND a1.co_reg=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_REG_ORI) + "";
                        strSQL+=";";
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            bdeCanCnfDBEgr=rst.getBigDecimal("nd_canCon");
                            bdeCanNunRecDBEgr=rst.getBigDecimal("nd_canNunRec");
                        }

                        strSQL="";
                        strSQL+=" UPDATE tbm_detMovInv";
                        strSQL+=" SET ";
                        strSQL+=" nd_canPen=nd_can+" + "(" + strSigCamAntIng + (bdeCanCnfDBEgr).abs() + ")" + "+" + "(" + strSigCamAntIng + (bdeCanNunRecDBEgr).abs() + ")";
                        strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_EMP_ORI) + "";
                        strSQL+=" AND co_loc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_LOC_ORI) + "";
                        strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_TIP_DOC_ORI) + "";
                        strSQL+=" AND co_doc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_DOC_ORI) + "";
                        strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_REG_ORI) + "";
                        strSQL+=";";
                        strSQLUpd+=strSQL;
                        System.out.println("setCnfIngresoEgreso - 2: " + strSQLUpd);
                        stm.executeUpdate(strSQLUpd);
                        strSQLUpd="";
                        
                    }//fin del for
                    
                }

                stm.close();
                stm=null;
                rst.close();
                rst=null;                
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
     * Función que permite confirmar documentos de transferencias, egresos, ingresos entre relacionadas - REPOSICIÓN
     *        Egreso/Ingreso por Reposición (TRAINV, EGBOPR/INBOPR, FACVEN/FACCOM, DEVCOM/DEVVEN)
     * @param con                   Conexión a la base de datos
     * @param codigoEmpresa         Código de empresa
     * @param codigoLocal           Código de local
     * @param codigoTipoDocumento   Código de tipo de documento
     * @param codigoDocumento       Código de documento
     * @param arregloDatoItem        <HTML>Información del item:
     *                                      <BR>Código del item de grupo
     *                                      <BR>Código del item de empresa
     *                                      <BR>Código del item maestro
     *                                      <BR>Código del registro
     *                                  </HTML>
     *                  //el arreglo del item
    //          codigo de item empresa, codigo de item grupo, codigo de registro
     * @param tipoMovimiento        <HTML> Tipo confirmación
     *                                  <BR> R: Relacionadas
     *                              </HTML>
     * @param codigoBodegaEmpresa
     * @param cantidad
     * @return 
     */
    public boolean setCnfRelacionada(Connection con, ArrayList arregloDatos
            , String tipoMovimiento, String tipoConfirmacion/*Esta bodega es la bodega donde se va a confirmar*/
            ){
        boolean blnRes=true;
        String strTipMov=tipoMovimiento;
        String strTipCnf=tipoConfirmacion;
        arlDatCnf.clear();
        arlDatCnf=arregloDatos;
//        System.out.println("strTipCnf: " + strTipCnf);
        Object objCanAntEgrDetMovInv="";
        Object objCanActEgrDetMovInv="";
        Object objCanAntEgrInvBod="";
        Object objCanActEgrInvBod="";
        Object objCanAntIngDetMovInv="";
        Object objCanActIngDetMovInv="";
        Object objCanAntIngInvBod="";
        Object objCanActIngInvBod="";        
        String strSigCamAntEgr="", strSigCamActEgr="";
        String strSigCamAntIng="", strSigCamActIng="";
        
        String strSQLUpd="";
        int intCodEmpDocEgr=-1, intCodLocDocEgr=-1, intCodTipDocEgr=-1, intCodDocEgr=-1, intCodBodEgr=-1;
        int intCodEmpDocIng=-1, intCodLocDocIng=-1, intCodTipDocIng=-1, intCodDocIng=-1, intCodBodIng=-1;

        BigDecimal bdeCanCnfUsr=BigDecimal.ZERO, bdeCanNotCnfUsr=BigDecimal.ZERO;
        BigDecimal bdeCanCnfDBIng=BigDecimal.ZERO, bdeCanCnfDBCanTraIng=BigDecimal.ZERO;
        BigDecimal bdeCanCnfDBEgr=BigDecimal.ZERO, bdeCanCnfDBCanTraEgr=BigDecimal.ZERO;
        BigDecimal bdeCanTraEgr=BigDecimal.ZERO, bdeCanTraIng=BigDecimal.ZERO;
        BigDecimal bdeCanNunRecDBEgr=BigDecimal.ZERO, bdeCanNunRecDBIng=BigDecimal.ZERO;
        
        int intCodBodDocCnf=-1;
        try{
            if(con!=null){
                stm=con.createStatement();
                                
                if( (strTipMov.equals("R")) ){//COEGBO & COEGDE & COINDE  &  COINBO   (Mercadería remota - tx)
                    strSigCamAntEgr="-";//registro de egreso - No importa si es Comfirmación de Ingreso/Egreso, lo que importa es el registro
                    strSigCamActEgr="+";//registro de egreso - No importa si es Comfirmación de Ingreso/Egreso, lo que importa es el registro
                    strSigCamAntIng="-";//registro de ingreso - No importa si es Comfirmación de Ingreso/Egreso, lo que importa es el registro
                    strSigCamActIng="+";//registro de ingreso - No importa si es Comfirmación de Ingreso/Egreso, lo que importa es el registro
                }
                System.out.println("arlDatCnf: " + arlDatCnf.toString());
                if(arlDatCnf.size()>0){
                    for(int k=0; k<arlDatCnf.size();k++){
                        arlDatTrsEgrIng.clear();
                        strSQL="";
                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.st_reg";
                        strSQL+=" , a1.co_emprel, a1.co_locrel, a1.co_tipdocrel, a1.co_docrel, a1.co_regrel";
                        strSQL+=" , " + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_CAN_ITM_CNF) + " AS nd_can";
                        strSQL+=" , " + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_ITM_EMP_CNF) + " AS co_itmEmp";
                        strSQL+=" FROM tbr_detmovinv AS a1";
                        //Por egresos
                        if(strTipCnf.equals("E")){
                            strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_EMP_ORI) + "";
                            strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_LOC_ORI) + "";
                            strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_TIP_DOC_ORI) + "";
                            strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_DOC_ORI) + "";
                            strSQL+=" AND a1.co_reg IN(";
                        }
                        else if(strTipCnf.equals("I")){
                            strSQL+=" WHERE a1.co_empRel=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_EMP_ORI) + "";
                            strSQL+=" AND a1.co_locRel=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_LOC_ORI) + "";
                            strSQL+=" AND a1.co_tipDocRel=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_TIP_DOC_ORI) + "";
                            strSQL+=" AND a1.co_docRel=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_DOC_ORI) + "";
                            strSQL+=" AND a1.co_regRel IN(";
                        }                   
                        strSQL+="" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_REG_ORI) + "";
                        strSQL+=")";
                        strSQL+=";";
                        rst=stm.executeQuery(strSQL);
                        while(rst.next()){
                            arlRegTrsEgrIng=new ArrayList();
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_EMP, rst.getString("co_emp"));//egreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_LOC, rst.getString("co_loc"));//egreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_TIP_DOC, rst.getString("co_tipdoc"));//egreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_DOC, rst.getString("co_doc"));//egreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_REG, rst.getString("co_reg"));//egreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_EMP_REL, rst.getString("co_empRel"));//ingreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_LOC_REL, rst.getString("co_locRel"));//ingreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_TIP_DOC_REL, rst.getString("co_tipDocRel"));//ingreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_DOC_REL, rst.getString("co_docRel"));//ingreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_REG_REL, rst.getString("co_regRel"));//ingreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_ITM_EMP, rst.getString("co_itmEmp"));                        
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_CAN, rst.getString("nd_can"));
                            arlDatTrsEgrIng.add(arlRegTrsEgrIng);
                            //egreso
                            intCodEmpDocEgr=rst.getInt("co_emp");
                            intCodLocDocEgr=rst.getInt("co_loc");
                            intCodTipDocEgr=rst.getInt("co_tipDoc");
                            intCodDocEgr=rst.getInt("co_doc");
                            //Ingreso
                            intCodEmpDocIng=rst.getInt("co_empRel");
                            intCodLocDocIng=rst.getInt("co_locRel");
                            intCodTipDocIng=rst.getInt("co_tipDocRel");
                            intCodDocIng=rst.getInt("co_docRel");
                        }

                        //obtener la bodega del registro de ingreso
                        strSQL="";
                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_bod ";
                        strSQL+=" FROM tbm_detMovInv AS a1";
                        strSQL+=" WHERE a1.co_emp=" + intCodEmpDocIng + "";
                        strSQL+=" AND a1.co_loc=" + intCodLocDocIng + "";
                        strSQL+=" AND a1.co_tipDoc=" + intCodTipDocIng + "";
                        strSQL+=" AND a1.co_doc=" + intCodDocIng + "";
                        strSQL+=" AND a1.nd_can>0";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_bod";
                        System.out.println("intCodBodEmpDocRelCnf: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next())
                            intCodBodIng=rst.getInt("co_bod");//bodega del documento origen  -- intCodBodEmpDocRelCnf

                        //obtener la bodega del registro de egreso
                        strSQL="";
                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_bod ";
                        strSQL+=" FROM tbm_detMovInv AS a1";
                        strSQL+=" WHERE a1.co_emp=" + intCodEmpDocEgr + "";
                        strSQL+=" AND a1.co_loc=" + intCodLocDocEgr + "";
                        strSQL+=" AND a1.co_tipDoc=" + intCodTipDocEgr + "";
                        strSQL+=" AND a1.co_doc=" + intCodDocEgr + "";
                        strSQL+=" AND a1.nd_can<0";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_bod";
                        System.out.println("intCodBodEmpDocRelCnf: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next())
                            intCodBodEgr=rst.getInt("co_bod");//bodega del documento origen  -- intCodBodEmpDocRelCnf

                        if(strTipCnf.equals("E")){
                            intCodBodDocCnf=intCodBodEgr;
                        }
                        else if(strTipCnf.equals("I")){
                            intCodBodDocCnf=intCodBodIng;
                        }

                        strSQL="";
                        strSQL+=" SELECT c1.co_emp, c1.co_loc, c1.co_bod, c1.tx_camantegrdetmovinv, c1.tx_camactegrdetmovinv";
                        strSQL+=" , c1.tx_camantegrinvbod, c1.tx_camactegrinvbod, c1.tx_camantingdetmovinv";
                        strSQL+=" , c1.tx_camactingdetmovinv, c1.tx_camantinginvbod, c1.tx_camactinginvbod";
                        strSQL+=" FROM(";
                        strSQL+=" 		SELECT b1.co_emp, b1.co_loc, b1.co_bod, b1.tx_camantegrdetmovinv, b1.tx_camactegrdetmovinv";
                        strSQL+=" 		 , b1.tx_camantegrinvbod, b1.tx_camactegrinvbod, b1.tx_camantingdetmovinv";
                        strSQL+=" 		 , b1.tx_camactingdetmovinv, b1.tx_camantinginvbod, b1.tx_camactinginvbod";
                        strSQL+=" 		 FROM(";
                        strSQL+=" 			 SELECT a2.co_emp, a2.co_loc, a2.co_bod, a1.tx_camantegrdetmovinv, a1.tx_camactegrdetmovinv";
                        strSQL+=" 			 , a1.tx_camantegrinvbod, a1.tx_camactegrinvbod, a1.tx_camantingdetmovinv";
                        strSQL+=" 			 , a1.tx_camactingdetmovinv, a1.tx_camantinginvbod, a1.tx_camactinginvbod";
                        strSQL+=" 			 FROM tbm_cfgConInv AS a1 INNER JOIN tbm_cfgConInvTipDocBod AS a2";
                        strSQL+=" 			 ON a1.co_cfg=a2.co_cfg";
                        strSQL+=" 			 WHERE a2.co_tipdoc=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_TIP_DOC_CNF) + "";
                        strSQL+=" 			 AND a1.tx_tipmov='" + tipoMovimiento + "'";
                        strSQL+=" 			 AND a1.st_reg='A' AND a2.st_reg='A'";
                        strSQL+=" 		) AS b1";
                        strSQL+=" 		INNER JOIN(";
                        strSQL+="                     	SELECT b1.co_empGrp, b1.co_bodGrp, b2.co_emp, b2.co_bod, b1.tx_nom AS tx_nomBod";
                        strSQL+="                      	FROM(";
                        strSQL+="                      		SELECT a2.co_empGrp, a2.co_bodGrp, a1.tx_nom";
                        strSQL+="                      		FROM tbm_bod AS a1 INNER JOIN tbr_bodEmpBodGrp AS a2";
                        strSQL+="                      		ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                        strSQL+="                      		WHERE a1.st_reg='A'";
                        strSQL+="                                   AND a2.co_emp=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_EMP_CNF) + "";
                        strSQL+="                                   AND a2.co_bod=" + intCodBodDocCnf + "";
                        strSQL+="                      	) AS b1";
                        strSQL+="                      	INNER JOIN(";
                        strSQL+="                      		SELECT a2.co_empGrp, a2.co_bodGrp, a2.co_emp, a2.co_bod";
                        strSQL+="                      		FROM tbr_bodEmpBodGrp AS a2";
                        strSQL+="                      	) AS b2";
                        strSQL+="                      	ON b1.co_empGrp=b2.co_empGrp AND b1.co_bodGrp=b2.co_bodGrp";
                        strSQL+=" 			GROUP BY b1.co_empGrp, b1.co_bodGrp, b2.co_emp, b2.co_bod, b1.tx_nom";
                        strSQL+=" 		) AS b2";
                        strSQL+=" 		ON b1.co_emp=b2.co_emp AND b1.co_bod=b2.co_bod";
                        strSQL+=" ) AS c1";
                        strSQL+=" INNER JOIN(";
                        strSQL+="	SELECT b1.co_empGrp, b1.co_bodGrp, b2.co_emp, b2.co_bod, b1.tx_nom AS tx_nomBod";
                        strSQL+=" 	FROM(";
                        strSQL+=" 		SELECT a2.co_empGrp, a2.co_bodGrp, a1.tx_nom";
                        strSQL+=" 		FROM tbm_bod AS a1 INNER JOIN tbr_bodEmpBodGrp AS a2";
                        strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                        strSQL+=" 		WHERE a1.st_reg='A'";
                        strSQL+="           AND a1.co_emp=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_EMP_CNF) + "";
                        strSQL+="           AND a1.co_bod=" + intCodBodDocCnf + "";
                        strSQL+=" 	) AS b1";
                        strSQL+=" 	INNER JOIN(";
                        strSQL+=" 		SELECT a2.co_empGrp, a2.co_bodGrp, a2.co_emp, a2.co_bod";
                        strSQL+=" 		FROM tbr_bodEmpBodGrp AS a2";
                        strSQL+=" 	) AS b2";
                        strSQL+=" 	ON b1.co_empGrp=b2.co_empGrp AND b1.co_bodGrp=b2.co_bodGrp";
                        strSQL+=" ) AS c2";
                        strSQL+=" ON c1.co_emp=c2.co_emp AND c1.co_bod=c2.co_bod";
                        strSQL+=" GROUP BY c1.co_emp, c1.co_loc, c1.co_bod, c1.tx_camantegrdetmovinv, c1.tx_camactegrdetmovinv";
                        strSQL+=" , c1.tx_camantegrinvbod, c1.tx_camactegrinvbod, c1.tx_camantingdetmovinv";
                        strSQL+=" , c1.tx_camactingdetmovinv, c1.tx_camantinginvbod, c1.tx_camactinginvbod";
                        System.out.println("tbm_cfgConInv: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            //Egresos
                            objCanAntEgrDetMovInv=rst.getString("tx_camantegrdetmovinv");
                            objCanActEgrDetMovInv=rst.getString("tx_camactegrdetmovinv");
                            objCanAntEgrInvBod=rst.getString("tx_camantegrinvbod");
                            objCanActEgrInvBod=rst.getString("tx_camactegrinvbod");
                            //Ingresos
                            objCanAntIngDetMovInv=rst.getString("tx_camantingdetmovinv");
                            objCanActIngDetMovInv=rst.getString("tx_camactingdetmovinv");
                            objCanAntIngInvBod=rst.getString("tx_camantinginvbod");
                            objCanActIngInvBod=rst.getString("tx_camactinginvbod");
                        }

                        if(arlDatTrsEgrIng.size()>0){
                            for(int i=0; i<arlDatTrsEgrIng.size(); i++){
                                //egreso
                                bdeCanCnfUsr=BigDecimal.ZERO;
                                bdeCanNotCnfUsr=BigDecimal.ZERO;                            

                                if(strTipCnf.equals("E")){//SOLO REGISTRO DE EGRESO
                                    bdeCanCnfUsr=new BigDecimal(objUti.getObjectValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN)==null?"0":(objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN).equals("")?"0":objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN)));
                                    bdeCanNotCnfUsr=new BigDecimal(objUti.getObjectValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF)==null?"0":(objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF).equals("")?"0":objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF)));

                                    //tbm_detMovInv
                                    strSQL="";
                                    strSQL+="UPDATE tbm_detMovInv";
                                    strSQL+=" SET ";
                                    //E : Egresos de cualquier tipo
                                    if(objCanAntEgrDetMovInv!=null)
                                        strSQL+="" + objCanAntEgrDetMovInv + "=(CASE WHEN " + objCanAntEgrDetMovInv + " IS NULL THEN 0 ELSE " + objCanAntEgrDetMovInv + " END)" + strSigCamAntEgr + "(" + bdeCanCnfUsr + ")";
                                    if(objCanActEgrDetMovInv!=null){
                                        if(objCanAntEgrDetMovInv!=null)
                                             strSQL+=",";
                                        strSQL+="" + objCanActEgrDetMovInv + "=(CASE WHEN " + objCanActEgrDetMovInv + " IS NULL THEN 0 ELSE " + objCanActEgrDetMovInv + " END)" + strSigCamActEgr + "(" + bdeCanCnfUsr + ")";
                                    }
                                    strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + " AND co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                    strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + " AND co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                    strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                    strSQL+=";";                                
                                    //tbm_invBod
                                    strSQL+=" UPDATE tbm_invBod";
                                    strSQL+=" SET ";
                                    //E : Egresos de cualquier tipo
                                    if(objCanAntEgrInvBod!=null)
                                        strSQL+="" + objCanAntEgrInvBod + "=(CASE WHEN " + objCanAntEgrInvBod + " IS NULL THEN 0 ELSE " + objCanAntEgrInvBod + " END)" + strSigCamAntEgr + "(" + bdeCanCnfUsr + ")";
                                    if(objCanActEgrInvBod!=null){
                                        if(objCanAntEgrInvBod!=null)
                                            strSQL+=",";
                                        strSQL+="" + objCanActEgrInvBod + "=(CASE WHEN " + objCanActEgrInvBod + " IS NULL THEN 0 ELSE " + objCanActEgrInvBod + " END)" + strSigCamActEgr + "(" + bdeCanCnfUsr + ")";
                                    }
                                    strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + "";
                                    strSQL+=" AND co_itm=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_ITM_EMP) + "";
                                    strSQL+=" AND co_bod=" + intCodBodEgr + "";
                                    strSQL+=";";
                                    strSQLUpd+=strSQL;
                                    System.out.println("strSQLUpd-egreso1: " + strSQLUpd);
                                    stm.executeUpdate(strSQLUpd);
                                    strSQLUpd="";                                

                                    //cantidad pendiente
                                    strSQL="";
                                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                                    strSQL+=" , CASE WHEN a1.nd_can IS NULL THEN 0 ELSE a1.nd_can END AS nd_can";
                                    strSQL+=" , CASE WHEN a1.nd_canCon IS NULL THEN 0 ELSE a1.nd_canCon END AS nd_canCon";
                                    strSQL+=" , CASE WHEN a1.nd_canNunRec IS NULL THEN 0 ELSE a1.nd_canNunRec END AS nd_canNunRec";
                                    strSQL+=" FROM tbm_detMovInv AS a1";
                                    strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + "";
                                    strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                    strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + "";
                                    strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                    strSQL+=" AND a1.co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                    strSQL+=";";
                                    rst=stm.executeQuery(strSQL);
                                    if(rst.next()){
                                        bdeCanCnfDBEgr=rst.getBigDecimal("nd_canCon");
                                        bdeCanNunRecDBEgr=rst.getBigDecimal("nd_canNunRec");
                                    }

                                    strSQL="";
                                    strSQL+="UPDATE tbm_detMovInv";
                                    strSQL+=" SET ";
                                    strSQL+=" nd_canPen=nd_can+" + (bdeCanCnfDBEgr).abs() + "+" + (bdeCanNunRecDBEgr).abs() + "";
                                    strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + " AND co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                    strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + " AND co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                    strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                    strSQL+=";";
                                    strSQLUpd+=strSQL;
                                    System.out.println("strSQLUpd-egreso2: " + strSQLUpd);
                                    stm.executeUpdate(strSQLUpd);
                                    strSQLUpd="";
                                }//fin de egreso

                                if(strTipCnf.equals("I")){//AMBOS REGISTROS
                                    //Ingreso
                                    strSQL="";
                                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                                    strSQL+=" , CASE WHEN a1.nd_can IS NULL THEN 0 ELSE a1.nd_can END AS nd_can";
                                    strSQL+=" , CASE WHEN a1.nd_canCon IS NULL THEN 0 ELSE a1.nd_canCon END AS nd_canCon";
                                    strSQL+=" , CASE WHEN a1.nd_canNunRec IS NULL THEN 0 ELSE a1.nd_canNunRec END AS nd_canNunRec";
                                    strSQL+=" , " + objCanAntIngDetMovInv + " AS nd_canIngBod";
                                    //strSQL+=" , nd_canIngBod AS nd_canIngBod";
                                    strSQL+=" FROM tbm_detMovInv AS a1";
                                    strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP_REL) + "";
                                    strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC_REL) + "";
                                    strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC_REL) + "";
                                    strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC_REL) + "";
                                    strSQL+=" AND a1.co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG_REL) + "";
                                    System.out.println("SQL 1: " + strSQL);
                                    strSQL+=";";
                                    rst=stm.executeQuery(strSQL);
                                    if(rst.next()){
                                        bdeCanTraIng=rst.getBigDecimal("nd_canIngBod");
                                    }

                                    if(objCanAntEgrDetMovInv!=null){//nuevo
                                        //Egreso
                                        strSQL="";
                                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                                        strSQL+=" , CASE WHEN a1.nd_can IS NULL THEN 0 ELSE a1.nd_can END AS nd_can";
                                        strSQL+=" , CASE WHEN a1.nd_canCon IS NULL THEN 0 ELSE a1.nd_canCon END AS nd_canCon";
                                        strSQL+=" , CASE WHEN a1.nd_canNunRec IS NULL THEN 0 ELSE a1.nd_canNunRec END AS nd_canNunRec";
                                        strSQL+=" , " + objCanAntEgrDetMovInv + " AS nd_canTra";
                                        //strSQL+=" , nd_canTra AS nd_canTra";
                                        strSQL+=" FROM tbm_detMovInv AS a1";
                                        strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + "";
                                        strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                        strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + "";
                                        strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                        strSQL+=" AND a1.co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                        //strSQL+=" AND a1.co_tipDoc!=228";
                                        strSQL+=";";
                                        System.out.println("SQL 2: " + strSQL);
                                        rst=stm.executeQuery(strSQL);
                                        if(rst.next()){
                                            bdeCanTraEgr=rst.getBigDecimal("nd_canTra");
                                        }
                                    }

                                    if(objParSis.getCodigoMenu()==4010)
                                        bdeCanTraEgr=new BigDecimal("-1");

                                    System.out.println("bdeCanTraIng: " + bdeCanTraIng);
                                    System.out.println("bdeCanTraEgr: " + bdeCanTraEgr);

                                    if(bdeCanTraIng.compareTo(new BigDecimal("0"))!=0){//si se puede confirmar algo en el ingreso
                                        if(bdeCanTraEgr.compareTo(new BigDecimal("0"))!=0){//si hay algo en el campo en transito(implica que se ha hecho la confirmacion del egreso en el otro punto remoto)

                                            //*****registro de ingreso
                                            bdeCanCnfUsr=new BigDecimal(objUti.getObjectValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN)==null?"0":(objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN).equals("")?"0":objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN)));
                                            bdeCanNotCnfUsr=new BigDecimal(objUti.getObjectValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF)==null?"0":(objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF).equals("")?"0":objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF)));
                                            strSQL="";
                                            strSQL+="UPDATE tbm_detMovInv";
                                            strSQL+=" SET ";
                                            if(objCanAntIngDetMovInv!=null)
                                                strSQL+="" + objCanAntIngDetMovInv + "=(CASE WHEN " + objCanAntIngDetMovInv + " IS NULL THEN 0 ELSE " + objCanAntIngDetMovInv + " END)" + strSigCamAntIng + "(" + bdeCanCnfUsr + ")";
                                            if(objCanActIngDetMovInv!=null){
                                                if(objCanAntIngDetMovInv!=null)
                                                    strSQL+=",";
                                                strSQL+="" + objCanActIngDetMovInv + "=(CASE WHEN " + objCanActIngDetMovInv + " IS NULL THEN 0 ELSE " + objCanActIngDetMovInv + " END)" + strSigCamActIng + "(" + bdeCanCnfUsr + ")";
                                            }
                                            strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP_REL) + " AND co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC_REL) + "";
                                            strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC_REL) + " AND co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC_REL) + "";
                                            strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG_REL) + "";
                                            strSQL+=";";                                        
                                            //tbm_invBod
                                            strSQL+=" UPDATE tbm_invBod";
                                            strSQL+=" SET ";
                                            if(objCanAntIngInvBod!=null)
                                                strSQL+="" + objCanAntIngInvBod + "=(CASE WHEN " + objCanAntIngInvBod + " IS NULL THEN 0 ELSE " + objCanAntIngInvBod + " END)" + strSigCamAntIng + "(" + bdeCanCnfUsr + ")";
                                            if(objCanActIngInvBod!=null){
                                                if(objCanAntIngInvBod!=null)
                                                    strSQL+=",";
                                                strSQL+="" + objCanActIngInvBod + "=(CASE WHEN " + objCanActIngInvBod + " IS NULL THEN 0 ELSE " + objCanActIngInvBod + " END)" + strSigCamActIng + "(" + bdeCanCnfUsr + ")";
                                            }
                                            strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP_REL) + "";
                                            strSQL+=" AND co_itm=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_ITM_EMP) + "";
                                            strSQL+=" AND co_bod=" + intCodBodIng + "";
                                            strSQL+=";";
                                            strSQLUpd+=strSQL;                                        
                                            System.out.println("strSQLUpd-A: " + strSQLUpd);
                                            stm.executeUpdate(strSQLUpd);
                                            strSQLUpd="";


                                            strSQL="";
                                            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                                            strSQL+=" , CASE WHEN a1.nd_can IS NULL THEN 0 ELSE a1.nd_can END AS nd_can";
                                            strSQL+=" , CASE WHEN a1.nd_canCon IS NULL THEN 0 ELSE a1.nd_canCon END AS nd_canCon";
                                            strSQL+=" , CASE WHEN a1.nd_canNunRec IS NULL THEN 0 ELSE a1.nd_canNunRec END AS nd_canNunRec";
                                            strSQL+=" , " + objCanAntIngDetMovInv + " AS nd_canIngBod";
                                            strSQL+=" FROM tbm_detMovInv AS a1";
                                            strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP_REL) + "";
                                            strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC_REL) + "";
                                            strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC_REL) + "";
                                            strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC_REL) + "";
                                            strSQL+=" AND a1.co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG_REL) + "";
                                            System.out.println("SQL 1: " + strSQL);
                                            strSQL+=";";
                                            rst=stm.executeQuery(strSQL);
                                            if(rst.next()){
                                                bdeCanCnfDBIng=rst.getBigDecimal("nd_canCon");
                                                bdeCanNunRecDBIng=rst.getBigDecimal("nd_canNunRec");
                                            }

                                            strSQL="";
                                            strSQL+="UPDATE tbm_detMovInv";
                                            strSQL+=" SET ";
                                            strSQL+=" nd_canPen=nd_can-" + (bdeCanCnfDBIng).abs() + "-" + (bdeCanNunRecDBIng).abs() + "";
                                            strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP_REL) + " AND co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC_REL) + "";
                                            strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC_REL) + " AND co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC_REL) + "";
                                            strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG_REL) + "";
                                            strSQL+=";";
                                            strSQLUpd+=strSQL;                                        
                                            System.out.println("strSQLUpd-B: " + strSQLUpd);
                                            stm.executeUpdate(strSQLUpd);
                                            strSQLUpd="";

                                            //*****registro de egreso
                                            //inicio de registro de egreso
                                            if(objParSis.getCodigoMenu()==4000){//Confirmación de ingresos - despacho.... por bodega no debe actualizar en el registro de egreso asociado
                                                bdeCanCnfUsr=new BigDecimal(objUti.getObjectValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN)==null?"0":(objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN).equals("")?"0":objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN)));
                                                bdeCanNotCnfUsr=new BigDecimal(objUti.getObjectValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF)==null?"0":(objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF).equals("")?"0":objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF)));
                                                strSQL="";
                                                strSQL+="UPDATE tbm_detMovInv";
                                                strSQL+=" SET ";
                                                strSQL+=" nd_canTra=(CASE WHEN nd_canTra IS NULL THEN 0 ELSE nd_canTra END)+(" + bdeCanCnfUsr.abs() + ")";
                                                strSQL+=", nd_canCon=((CASE WHEN nd_canCon IS NULL THEN 0 ELSE nd_canCon END)-" + bdeCanCnfUsr + ")";
                                                strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + " AND co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                                strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + " AND co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                                strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                                strSQL+=";";
                                                //tbm_invBod
                                                strSQL+=" UPDATE tbm_invBod";
                                                strSQL+=" SET ";
                                                strSQL+=" nd_canTra=((CASE WHEN nd_canTra IS NULL THEN 0 ELSE nd_canTra END)+" + bdeCanCnfUsr.abs() + ")";
                                                strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + "";
                                                //strSQL+=" AND co_itm=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_ITM_EMP) + "";
                                                strSQL+=" AND co_itm=(";
                                                strSQL+="        SELECT a1.co_itm";
                                                strSQL+="        FROM tbm_detMovInv AS a1 INNER JOIN tbr_detMovInv AS a2";
                                                strSQL+="        ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg";
                                                strSQL+="        WHERE a2.co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + "";
                                                strSQL+="        AND a2.co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                                strSQL+="        AND a2.co_tipDoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + "";
                                                strSQL+="        AND a2.co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                                strSQL+="        AND a2.co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                                strSQL+=")";
                                                strSQL+=" AND co_bod=" + intCodBodEgr + "";
                                                strSQL+=";";
                                                strSQLUpd+=strSQL;                                        
                                                System.out.println("strSQLUpd-C: " + strSQLUpd);
                                                stm.executeUpdate(strSQLUpd);
                                                strSQLUpd="";                                        

                                                if(objCanAntEgrDetMovInv!=null){//nuevo
                                                    strSQL="";
                                                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                                                    strSQL+=" , CASE WHEN a1.nd_can IS NULL THEN 0 ELSE a1.nd_can END AS nd_can";
                                                    strSQL+=" , CASE WHEN a1.nd_canCon IS NULL THEN 0 ELSE a1.nd_canCon END AS nd_canCon";
                                                    strSQL+=" , CASE WHEN a1.nd_canNunRec IS NULL THEN 0 ELSE a1.nd_canNunRec END AS nd_canNunRec";
                                                    strSQL+=" , " + objCanAntEgrDetMovInv + " AS nd_canTra";
                                                    strSQL+=" FROM tbm_detMovInv AS a1";
                                                    strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + "";
                                                    strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                                    strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + "";
                                                    strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                                    strSQL+=" AND a1.co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                                    strSQL+=";";
                                                    System.out.println("SQL 2: " + strSQL);
                                                    rst=stm.executeQuery(strSQL);
                                                    if(rst.next()){
                                                        bdeCanCnfDBEgr=rst.getBigDecimal("nd_canCon");
                                                        bdeCanNunRecDBEgr=rst.getBigDecimal("nd_canNunRec");
                                                    }

                                                    strSQL="";
                                                    strSQL+="UPDATE tbm_detMovInv";
                                                    strSQL+=" SET ";
                                                    strSQL+=" nd_canPen=nd_can+" + (bdeCanCnfDBEgr).abs() + "+" + (bdeCanNunRecDBEgr).abs() + "";
                                                    strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + " AND co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                                    strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + " AND co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                                    strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                                    strSQL+=";";
                                                    strSQLUpd+=strSQL;                                        
                                                    System.out.println("strSQLUpd-D: " + strSQLUpd);
                                                    stm.executeUpdate(strSQLUpd);
                                                    strSQLUpd="";
                                                }
                                            }
                                            
                                            //Inmaconsa, California, Vía Daule
                                            if(objParSis.getCodigoMenu()==4010){//Confirmación de ingresos - bodega....
                                                bdeCanCnfUsr=new BigDecimal(objUti.getObjectValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN)==null?"0":(objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN).equals("")?"0":objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN)));
                                                bdeCanNotCnfUsr=new BigDecimal(objUti.getObjectValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF)==null?"0":(objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF).equals("")?"0":objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF)));
                                                strSQL="";
                                                strSQL+="UPDATE tbm_detMovInv";
                                                strSQL+=" SET ";
                                                strSQL+=" nd_canTra=(CASE WHEN nd_canTra IS NULL THEN 0 ELSE nd_canTra END)+(" + bdeCanCnfUsr.abs() + ")";
                                                strSQL+=", nd_canCon=((CASE WHEN nd_canCon IS NULL THEN 0 ELSE nd_canCon END)-" + bdeCanCnfUsr + ")";
                                                strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + " AND co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                                strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + " AND co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                                strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                                strSQL+=";";
                                                //tbm_invBod
                                                strSQL+=" UPDATE tbm_invBod";
                                                strSQL+=" SET ";
                                                strSQL+=" nd_canTra=((CASE WHEN nd_canTra IS NULL THEN 0 ELSE nd_canTra END)+" + bdeCanCnfUsr.abs() + ")";
                                                strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + "";
                                                //strSQL+=" AND co_itm=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_ITM_EMP) + "";
                                                strSQL+=" AND co_itm=(";
                                                strSQL+="        SELECT a1.co_itm";
                                                strSQL+="        FROM tbm_detMovInv AS a1 INNER JOIN tbr_detMovInv AS a2";
                                                strSQL+="        ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg";
                                                strSQL+="        WHERE a2.co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + "";
                                                strSQL+="        AND a2.co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                                strSQL+="        AND a2.co_tipDoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + "";
                                                strSQL+="        AND a2.co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                                strSQL+="        AND a2.co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                                strSQL+=")";
                                                strSQL+=" AND co_bod=" + intCodBodEgr + "";
                                                strSQL+=";";
                                                strSQLUpd+=strSQL;                                        
                                                System.out.println("strSQLUpd-C: " + strSQLUpd);
                                                stm.executeUpdate(strSQLUpd);
                                                strSQLUpd="";                                        

                                                if(objCanAntEgrDetMovInv!=null){//nuevo
                                                    strSQL="";
                                                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                                                    strSQL+=" , CASE WHEN a1.nd_can IS NULL THEN 0 ELSE a1.nd_can END AS nd_can";
                                                    strSQL+=" , CASE WHEN a1.nd_canCon IS NULL THEN 0 ELSE a1.nd_canCon END AS nd_canCon";
                                                    strSQL+=" , CASE WHEN a1.nd_canNunRec IS NULL THEN 0 ELSE a1.nd_canNunRec END AS nd_canNunRec";
                                                    strSQL+=" , " + objCanAntEgrDetMovInv + " AS nd_canTra";
                                                    strSQL+=" FROM tbm_detMovInv AS a1";
                                                    strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + "";
                                                    strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                                    strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + "";
                                                    strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                                    strSQL+=" AND a1.co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                                    strSQL+=";";
                                                    System.out.println("SQL 2: " + strSQL);
                                                    rst=stm.executeQuery(strSQL);
                                                    if(rst.next()){
                                                        bdeCanCnfDBEgr=rst.getBigDecimal("nd_canCon");
                                                        bdeCanNunRecDBEgr=rst.getBigDecimal("nd_canNunRec");
                                                    }

                                                    strSQL="";
                                                    strSQL+="UPDATE tbm_detMovInv";
                                                    strSQL+=" SET ";
                                                    strSQL+=" nd_canPen=nd_can+" + (bdeCanCnfDBEgr).abs() + "+" + (bdeCanNunRecDBEgr).abs() + "";
                                                    strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + " AND co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                                    strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + " AND co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                                    strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                                    strSQL+=";";
                                                    strSQLUpd+=strSQL;                                        
                                                    System.out.println("strSQLUpd-D: " + strSQLUpd);
                                                    stm.executeUpdate(strSQLUpd);
                                                    strSQLUpd="";
                                                }
                                            }
                                            
                                            //fin de registro de egreso
                                            
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;                
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
     * Función que permite confirmar documentos de transferencias, egresos, ingresos entre relacionadas - REPOSICIÓN
     *        Egreso/Ingreso por Reposición (TRAINV, EGBOPR/INBOPR, FACVEN/FACCOM, DEVCOM/DEVVEN)
     * @param con                   Conexión a la base de datos
     * @param codigoEmpresa         Código de empresa
     * @param codigoLocal           Código de local
     * @param codigoTipoDocumento   Código de tipo de documento
     * @param codigoDocumento       Código de documento
     * @param arregloDatoItem        <HTML>Información del item:
     *                                      <BR>Código del item de grupo
     *                                      <BR>Código del item de empresa
     *                                      <BR>Código del item maestro
     *                                      <BR>Código del registro
     *                                  </HTML>
     *                  //el arreglo del item
    //          codigo de item empresa, codigo de item grupo, codigo de registro
     * @param tipoMovimiento        <HTML> Tipo confirmación
     *                                  <BR> V: Ventas con mercadería de relacionadas
     *                              </HTML>
     * @param codigoBodegaEmpresa
     * @param cantidad
     * @return 
     */
    public boolean setCnfVtaRelacionada(Connection con, ArrayList arregloDatos
            , String tipoMovimiento, String tipoConfirmacion/*Esta bodega es la bodega donde se va a confirmar*/
            ){
        boolean blnRes=true;
        String strTipMov=tipoMovimiento;
        String strTipCnf=tipoConfirmacion;
        arlDatCnf.clear();
        arlDatCnf=arregloDatos;
        
        Object objCanAntEgrDetMovInv="";
        Object objCanActEgrDetMovInv="";
        Object objCanAntEgrInvBod="";
        Object objCanActEgrInvBod="";
        Object objCanAntIngDetMovInv="";
        Object objCanActIngDetMovInv="";
        Object objCanAntIngInvBod="";
        Object objCanActIngInvBod="";
        String strSigCamAntEgr="", strSigCamActEgr="";
        String strSigCamAntIng="", strSigCamActIng="";
        
        String strSQLUpd="";
        
        int intCodEmpDocEgr=-1, intCodLocDocEgr=-1, intCodTipDocEgr=-1, intCodDocEgr=-1, intCodBodEgr=-1;
        int intCodEmpDocIng=-1, intCodLocDocIng=-1, intCodTipDocIng=-1, intCodDocIng=-1, intCodBodIng=-1;
        int intCodBodDocCnf=-1;
                
        BigDecimal bdeCanCnfUsr=BigDecimal.ZERO, bdeCanNotCnfUsr=BigDecimal.ZERO;
        BigDecimal bdeCanCnfDBIng=BigDecimal.ZERO, bdeCanCnfDBCanTraIng=BigDecimal.ZERO;
        BigDecimal bdeCanCnfDBEgr=BigDecimal.ZERO, bdeCanCnfDBCanTraEgr=BigDecimal.ZERO;
        BigDecimal bdeCanTraEgr=BigDecimal.ZERO, bdeCanTraIng=BigDecimal.ZERO;
        BigDecimal bdeCanNunRecDBEgr=BigDecimal.ZERO, bdeCanNunRecDBIng=BigDecimal.ZERO;
        try{
            if(con!=null){
                stm=con.createStatement();
                                
                if( (strTipMov.equals("V")) ){//COEGBO & COEGDE & COINDE  &  COINBO   (Mercadería remota - tx)
                    strSigCamAntEgr="-";//registro de egreso - No importa si es Comfirmación de Ingreso/Egreso, lo que importa es el registro
                    strSigCamActEgr="+";//registro de egreso - No importa si es Comfirmación de Ingreso/Egreso, lo que importa es el registro
                    strSigCamAntIng="-";//registro de ingreso - No importa si es Comfirmación de Ingreso/Egreso, lo que importa es el registro
                    strSigCamActIng="+";//registro de ingreso - No importa si es Comfirmación de Ingreso/Egreso, lo que importa es el registro
                }
                System.out.println("arlDatCnf: " + arlDatCnf.toString());
                if(arlDatCnf.size()>0){
                    for(int k=0; k<arlDatCnf.size();k++){
                        arlDatTrsEgrIng.clear();
                        System.out.println("***************************************************************************************");

                        strSQL="";
                        strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc, b1.co_reg, b1.st_reg \n";
                        strSQL+=" , b1.co_emprel, b1.co_locrel, b1.co_tipdocrel, b1.co_docrel, b1.co_regrel \n";
                        strSQL+=" , b1.nd_can, b1.co_itmEmp /*, b2.co_cot, b2.st_reg, b2.tx_momGenFac */ \n";
                        strSQL+=" FROM( \n";
                        strSQL+="       SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.st_reg \n";
                        strSQL+="       , a1.co_emprel, a1.co_locrel, a1.co_tipdocrel, a1.co_docrel, a1.co_regrel \n";
                        strSQL+="       , " + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_CAN_ITM_CNF) + " AS nd_can \n";
                        strSQL+="       , " + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_ITM_EMP_CNF) + " AS co_itmEmp \n";
                        strSQL+="       FROM tbr_detmovinv AS a1 \n";
                        strSQL+="       INNER JOIN tbm_cabMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  \n";
                        //Por egresos
                        if(strTipCnf.equals("E")){
                            strSQL+="       WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_EMP_ORI) + "";
                            strSQL+="       AND a1.co_loc=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_LOC_ORI) + "";
                            strSQL+="       AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_TIP_DOC_ORI) + "";
                            strSQL+="       AND a1.co_doc=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_DOC_ORI) + "";
                            strSQL+="       AND a1.co_reg IN(";
                        }
                        else if(strTipCnf.equals("I")){
                            strSQL+="       WHERE a1.co_empRel=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_EMP_ORI) + "";
                            strSQL+="       AND a1.co_locRel=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_LOC_ORI) + "";
                            strSQL+="       AND a1.co_tipDocRel=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_TIP_DOC_ORI) + "";
                            strSQL+="       AND a1.co_docRel=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_DOC_ORI) + "";
                            strSQL+="       AND a1.co_regRel IN(";
                        }                   
                        strSQL+="" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_REG_ORI) + "";
                        strSQL+="                               )  AND (a2.tx_tipMov='V' OR a2.tx_tipMov='I' OR a2.tx_tipMov='R') ";
                        strSQL+=" ) AS b1";
//                        strSQL+=" LEFT OUTER JOIN(";
//                        strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a3.co_cot, a3.st_reg, a3.tx_momGenFac";
//                        strSQL+=" 	FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabCotVen AS a3";
//                        strSQL+=" 			ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.ne_numCot=a3.co_cot)";
//                        strSQL+=" 	INNER JOIN tbm_detMovInv AS a2";
//                        strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
//                        strSQL+=" 	AND a1.st_reg='A' AND a3.st_reg IN('F') AND a3.tx_momGenFac IN('P')";
//                        strSQL+=" ) AS b2";
//                        strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc AND b1.co_reg=b2.co_reg";
//                        strSQL+=" WHERE b2.co_cot IS NULL";
                        System.out.println("tbr_detmovinv: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        while(rst.next()){
                            arlRegTrsEgrIng=new ArrayList();
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_EMP, rst.getString("co_emp"));//egreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_LOC, rst.getString("co_loc"));//egreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_TIP_DOC, rst.getString("co_tipdoc"));//egreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_DOC, rst.getString("co_doc"));//egreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_REG, rst.getString("co_reg"));//egreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_EMP_REL, rst.getString("co_empRel"));//ingreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_LOC_REL, rst.getString("co_locRel"));//ingreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_TIP_DOC_REL, rst.getString("co_tipDocRel"));//ingreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_DOC_REL, rst.getString("co_docRel"));//ingreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_REG_REL, rst.getString("co_regRel"));//ingreso
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_COD_ITM_EMP, rst.getString("co_itmEmp"));                        
                            arlRegTrsEgrIng.add(INT_ARL_DAT_TRS_CAN, rst.getString("nd_can"));
                            arlDatTrsEgrIng.add(arlRegTrsEgrIng);

                            intCodEmpDocEgr=rst.getInt("co_emp");
                            intCodLocDocEgr=rst.getInt("co_loc");
                            intCodTipDocEgr=rst.getInt("co_tipDoc");
                            intCodDocEgr=rst.getInt("co_doc");
                            intCodEmpDocIng=rst.getInt("co_empRel");
                            intCodLocDocIng=rst.getInt("co_locRel");
                            intCodTipDocIng=rst.getInt("co_tipdocRel");
                            intCodDocIng=rst.getInt("co_docRel");
                        }

                        //obtener la bodega del registro de egreso
                        strSQL="";
                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_bod ";
                        strSQL+=" FROM tbm_detMovInv AS a1";
                        strSQL+=" WHERE a1.co_emp=" + intCodEmpDocEgr + "";
                        strSQL+=" AND a1.co_loc=" + intCodLocDocEgr + "";
                        strSQL+=" AND a1.co_tipDoc=" + intCodTipDocEgr + "";
                        strSQL+=" AND a1.co_doc=" + intCodDocEgr + "";
                        strSQL+=" AND a1.nd_can<0";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_bod";
                        System.out.println("intCodBodEmpDocRelCnf jota habilita: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next())
                            intCodBodEgr=rst.getInt("co_bod");//bodega del documento origen  -- intCodBodEmpDocRelCnf

                        //obtener la bodega del registro de ingreso
                        strSQL="";
                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_bod ";
                        strSQL+=" FROM tbm_detMovInv AS a1";
                        strSQL+=" WHERE a1.co_emp=" + intCodEmpDocIng + "";
                        strSQL+=" AND a1.co_loc=" + intCodLocDocIng + "";
                        strSQL+=" AND a1.co_tipDoc=" + intCodTipDocIng + "";
                        strSQL+=" AND a1.co_doc=" + intCodDocIng + "";
                        strSQL+=" AND a1.nd_can>0";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_bod";
                        System.out.println("intCodBodEmpDocRelCnf: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next())
                            intCodBodIng=rst.getInt("co_bod");//bodega del documento origen  -- intCodBodEmpDocRelCnf

                        if(strTipCnf.equals("E")){
                                intCodBodDocCnf=intCodBodEgr;                        
                        }
                        else if(strTipCnf.equals("I")){
                            intCodBodDocCnf=intCodBodIng;                        
                        }

                        strSQL="";
                        strSQL+=" SELECT c1.co_emp, c1.co_loc, c1.co_bod, c1.tx_camantegrdetmovinv, c1.tx_camactegrdetmovinv";
                        strSQL+=" , c1.tx_camantegrinvbod, c1.tx_camactegrinvbod, c1.tx_camantingdetmovinv";
                        strSQL+=" , c1.tx_camactingdetmovinv, c1.tx_camantinginvbod, c1.tx_camactinginvbod";
                        strSQL+=" FROM(";
                        strSQL+=" 		SELECT b1.co_emp, b1.co_loc, b1.co_bod, b1.tx_camantegrdetmovinv, b1.tx_camactegrdetmovinv";
                        strSQL+=" 		 , b1.tx_camantegrinvbod, b1.tx_camactegrinvbod, b1.tx_camantingdetmovinv";
                        strSQL+=" 		 , b1.tx_camactingdetmovinv, b1.tx_camantinginvbod, b1.tx_camactinginvbod";
                        strSQL+=" 		 FROM(";
                        strSQL+=" 			 SELECT a2.co_emp, a2.co_loc, a2.co_bod, a1.tx_camantegrdetmovinv, a1.tx_camactegrdetmovinv";
                        strSQL+=" 			 , a1.tx_camantegrinvbod, a1.tx_camactegrinvbod, a1.tx_camantingdetmovinv";
                        strSQL+=" 			 , a1.tx_camactingdetmovinv, a1.tx_camantinginvbod, a1.tx_camactinginvbod";
                        strSQL+=" 			 FROM tbm_cfgConInv AS a1 INNER JOIN tbm_cfgConInvTipDocBod AS a2";
                        strSQL+=" 			 ON a1.co_cfg=a2.co_cfg";
                        strSQL+=" 			 WHERE a2.co_tipdoc=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_TIP_DOC_CNF) + "";
                        strSQL+=" 			 AND a1.tx_tipmov='" + tipoMovimiento + "'";
                        strSQL+=" 			 AND a1.st_reg='A' AND a2.st_reg='A'";
                        strSQL+=" 		) AS b1";
                        strSQL+=" 		INNER JOIN(";
                        strSQL+="                     	SELECT b1.co_empGrp, b1.co_bodGrp, b2.co_emp, b2.co_bod, b1.tx_nom AS tx_nomBod";
                        strSQL+="                      	FROM(";
                        strSQL+="                      		SELECT a2.co_empGrp, a2.co_bodGrp, a1.tx_nom";
                        strSQL+="                      		FROM tbm_bod AS a1 INNER JOIN tbr_bodEmpBodGrp AS a2";
                        strSQL+="                      		ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                        strSQL+="                      		WHERE a1.st_reg='A'";
                        strSQL+="                                   AND a2.co_emp=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_EMP_CNF) + "";
                        strSQL+="                                   AND a2.co_bod=" + intCodBodDocCnf + "";
                        strSQL+="                      	) AS b1";
                        strSQL+="                      	INNER JOIN(";
                        strSQL+="                      		SELECT a2.co_empGrp, a2.co_bodGrp, a2.co_emp, a2.co_bod";
                        strSQL+="                      		FROM tbr_bodEmpBodGrp AS a2";
                        strSQL+="                      	) AS b2";
                        strSQL+="                      	ON b1.co_empGrp=b2.co_empGrp AND b1.co_bodGrp=b2.co_bodGrp";
                        strSQL+=" 			GROUP BY b1.co_empGrp, b1.co_bodGrp, b2.co_emp, b2.co_bod, b1.tx_nom";
                        strSQL+=" 		) AS b2";
                        strSQL+=" 		ON b1.co_emp=b2.co_emp AND b1.co_bod=b2.co_bod";
                        strSQL+=" ) AS c1";
                        strSQL+=" INNER JOIN(";
                        strSQL+="	SELECT b1.co_empGrp, b1.co_bodGrp, b2.co_emp, b2.co_bod, b1.tx_nom AS tx_nomBod";
                        strSQL+=" 	FROM(";
                        strSQL+=" 		SELECT a2.co_empGrp, a2.co_bodGrp, a1.tx_nom";
                        strSQL+=" 		FROM tbm_bod AS a1 INNER JOIN tbr_bodEmpBodGrp AS a2";
                        strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                        strSQL+=" 		WHERE a1.st_reg='A'";
                        strSQL+="           AND a1.co_emp=" + objUti.getStringValueAt(arlDatCnf, k, INT_ARL_DAT_COD_EMP_CNF) + "";
                        strSQL+="           AND a1.co_bod=" + intCodBodDocCnf + "";
                        strSQL+=" 	) AS b1";
                        strSQL+=" 	INNER JOIN(";
                        strSQL+=" 		SELECT a2.co_empGrp, a2.co_bodGrp, a2.co_emp, a2.co_bod";
                        strSQL+=" 		FROM tbr_bodEmpBodGrp AS a2";
                        strSQL+=" 	) AS b2";
                        strSQL+=" 	ON b1.co_empGrp=b2.co_empGrp AND b1.co_bodGrp=b2.co_bodGrp";
                        strSQL+=" ) AS c2";
                        strSQL+=" ON c1.co_emp=c2.co_emp AND c1.co_bod=c2.co_bod";
                        strSQL+=" GROUP BY c1.co_emp, c1.co_loc, c1.co_bod, c1.tx_camantegrdetmovinv, c1.tx_camactegrdetmovinv";
                        strSQL+=" , c1.tx_camantegrinvbod, c1.tx_camactegrinvbod, c1.tx_camantingdetmovinv";
                        strSQL+=" , c1.tx_camactingdetmovinv, c1.tx_camantinginvbod, c1.tx_camactinginvbod";
                        System.out.println("tbm_cfgConInv: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            //Egresosl;
                            objCanAntEgrDetMovInv=rst.getString("tx_camantegrdetmovinv");
                            objCanActEgrDetMovInv=rst.getString("tx_camactegrdetmovinv");
                            objCanAntEgrInvBod=rst.getString("tx_camantegrinvbod");
                            objCanActEgrInvBod=rst.getString("tx_camactegrinvbod");
                            //Ingresos
                            objCanAntIngDetMovInv=rst.getString("tx_camantingdetmovinv");
                            objCanActIngDetMovInv=rst.getString("tx_camactingdetmovinv");
                            objCanAntIngInvBod=rst.getString("tx_camantinginvbod");
                            objCanActIngInvBod=rst.getString("tx_camactinginvbod");
                        }

                        if(arlDatTrsEgrIng.size()>0){
                            for(int i=0; i<arlDatTrsEgrIng.size(); i++){
                                //egreso
                                bdeCanCnfUsr=BigDecimal.ZERO;
                                bdeCanNotCnfUsr=BigDecimal.ZERO;                            

                                if(strTipCnf.equals("E")){//SOLO REGISTRO DE EGRESO
                                    bdeCanCnfUsr=new BigDecimal(objUti.getObjectValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN)==null?"0":(objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN).equals("")?"0":objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN)));
                                    bdeCanNotCnfUsr=new BigDecimal(objUti.getObjectValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF)==null?"0":(objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF).equals("")?"0":objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF)));

                                    //tbm_detMovInv
                                    strSQL="";
                                    strSQL+="UPDATE tbm_detMovInv";
                                    strSQL+=" SET ";
                                    //E : Egresos de cualquier tipo
                                    if(objCanAntEgrDetMovInv!=null)
                                        strSQL+="" + objCanAntEgrDetMovInv + "=(CASE WHEN " + objCanAntEgrDetMovInv + " IS NULL THEN 0 ELSE " + objCanAntEgrDetMovInv + " END)" + strSigCamAntEgr + "(" + bdeCanCnfUsr + ")";
                                    if(objCanActEgrDetMovInv!=null){
                                        if(objCanAntEgrDetMovInv!=null)
                                             strSQL+=",";
                                        strSQL+="" + objCanActEgrDetMovInv + "=(CASE WHEN " + objCanActEgrDetMovInv + " IS NULL THEN 0 ELSE " + objCanActEgrDetMovInv + " END)" + strSigCamActEgr + "(" + bdeCanCnfUsr + ")";
                                    }
                                    strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + " AND co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                    strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + " AND co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                    strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                    strSQL+=";";
                                    //tbm_invBod
                                    strSQL+=" UPDATE tbm_invBod";
                                    strSQL+=" SET ";
                                    //E : Egresos de cualquier tipo
                                    if(objCanAntEgrInvBod!=null)
                                        strSQL+="" + objCanAntEgrInvBod + "=(CASE WHEN " + objCanAntEgrInvBod + " IS NULL THEN 0 ELSE " + objCanAntEgrInvBod + " END)" + strSigCamAntEgr + "(" + bdeCanCnfUsr + ")";
                                    if(objCanActEgrInvBod!=null){
                                        if(objCanAntEgrInvBod!=null)
                                            strSQL+=",";
                                        strSQL+="" + objCanActEgrInvBod + "=(CASE WHEN " + objCanActEgrInvBod + " IS NULL THEN 0 ELSE " + objCanActEgrInvBod + " END)" + strSigCamActEgr + "(" + bdeCanCnfUsr + ")";
                                    }
                                    strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + "";
                                    strSQL+=" AND co_itm=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_ITM_EMP) + "";
                                    strSQL+=" AND co_bod=" + intCodBodEgr + "";
                                    strSQL+=";";
                                    strSQLUpd+=strSQL;    
                                    System.out.println("strSQLUpd: " + strSQLUpd);
                                    stm.executeUpdate(strSQLUpd);
                                    strSQLUpd="";

                                    //cantidad pendiente
                                    strSQL="";
                                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                                    strSQL+=" , CASE WHEN a1.nd_can IS NULL THEN 0 ELSE a1.nd_can END AS nd_can";
                                    strSQL+=" , CASE WHEN a1.nd_canCon IS NULL THEN 0 ELSE a1.nd_canCon END AS nd_canCon";
                                    strSQL+=" , CASE WHEN a1.nd_canNunRec IS NULL THEN 0 ELSE a1.nd_canNunRec END AS nd_canNunRec";
                                    strSQL+=" FROM tbm_detMovInv AS a1";
                                    strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + "";
                                    strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                    strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + "";
                                    strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                    strSQL+=" AND a1.co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                    strSQL+=";";
                                    rst=stm.executeQuery(strSQL);
                                    if(rst.next()){
                                        bdeCanCnfDBEgr=rst.getBigDecimal("nd_canCon");
                                        bdeCanNunRecDBEgr=rst.getBigDecimal("nd_canNunRec");
                                    }

                                    strSQL="";
                                    strSQL+="UPDATE tbm_detMovInv";
                                    strSQL+=" SET ";
                                    strSQL+=" nd_canPen=nd_can+" + (bdeCanCnfDBEgr).abs() + "+" + (bdeCanNunRecDBEgr).abs() + "";
                                    strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + " AND co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                    strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + " AND co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                    strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                    strSQL+=";";
                                    strSQLUpd+=strSQL;
                                    System.out.println("strSQLUpd: " + strSQLUpd);
                                    stm.executeUpdate(strSQLUpd);
                                    strSQLUpd="";
                                }//fin de egreso

                                if(strTipCnf.equals("I")){//AMBOS REGISTROS
                                    //Ingreso
                                    strSQL="";
                                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                                    strSQL+=" , CASE WHEN a1.nd_can IS NULL THEN 0 ELSE a1.nd_can END AS nd_can";
                                    strSQL+=" , CASE WHEN a1.nd_canCon IS NULL THEN 0 ELSE a1.nd_canCon END AS nd_canCon";
                                    strSQL+=" , CASE WHEN a1.nd_canNunRec IS NULL THEN 0 ELSE a1.nd_canNunRec END AS nd_canNunRec";
                                    strSQL+=" , " + objCanAntIngDetMovInv + " AS nd_canIngBod";
                                    strSQL+=" FROM tbm_detMovInv AS a1";
                                    strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP_REL) + "";
                                    strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC_REL) + "";
                                    strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC_REL) + "";
                                    strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC_REL) + "";
                                    strSQL+=" AND a1.co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG_REL) + "";
                                    System.out.println("SQL 1: " + strSQL);
                                    strSQL+=";";
                                    rst=stm.executeQuery(strSQL);
                                    if(rst.next()){
                                        bdeCanTraIng=rst.getBigDecimal("nd_canIngBod");
                                    }

                                    if(objCanAntEgrDetMovInv!=null){//nuevo
                                        //Egreso
                                        strSQL="";
                                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                                        strSQL+=" , CASE WHEN a1.nd_can IS NULL THEN 0 ELSE a1.nd_can END AS nd_can";
                                        strSQL+=" , CASE WHEN a1.nd_canCon IS NULL THEN 0 ELSE a1.nd_canCon END AS nd_canCon";
                                        strSQL+=" , CASE WHEN a1.nd_canNunRec IS NULL THEN 0 ELSE a1.nd_canNunRec END AS nd_canNunRec";
                                        strSQL+=" , " + objCanAntEgrDetMovInv + " AS nd_canTra";
                                        strSQL+=" FROM tbm_detMovInv AS a1";
                                        strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + "";
                                        strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                        strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + "";
                                        strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                        strSQL+=" AND a1.co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                        strSQL+=";";
                                        System.out.println("SQL 2: " + strSQL);
                                        rst=stm.executeQuery(strSQL);
                                        if(rst.next()){
                                            bdeCanTraEgr=rst.getBigDecimal("nd_canTra");
                                        }
                                    }

                                    if(objParSis.getCodigoMenu()==4010)
                                        bdeCanTraEgr=new BigDecimal("-1");

                                    System.out.println("bdeCanTraIng: " + bdeCanTraIng);
                                    System.out.println("bdeCanTraEgr: " + bdeCanTraEgr);

                                    if(bdeCanTraIng.compareTo(new BigDecimal("0"))!=0){//si se puede confirmar algo en el ingreso
                                        if(bdeCanTraEgr.compareTo(new BigDecimal("0"))!=0){//si hay algo en el campo en transito(implica que se ha hecho la confirmacion del egreso en el otro punto remoto)

                                            //*****registro de ingreso
                                            bdeCanCnfUsr=new BigDecimal(objUti.getObjectValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN)==null?"0":(objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN).equals("")?"0":objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN)));
                                            bdeCanNotCnfUsr=new BigDecimal(objUti.getObjectValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF)==null?"0":(objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF).equals("")?"0":objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF)));
                                            strSQL="";
                                            strSQL+="UPDATE tbm_detMovInv";
                                            strSQL+=" SET ";
                                            if(objCanAntIngDetMovInv!=null)
                                                strSQL+="" + objCanAntIngDetMovInv + "=(CASE WHEN " + objCanAntIngDetMovInv + " IS NULL THEN 0 ELSE " + objCanAntIngDetMovInv + " END)" + strSigCamAntIng + "(" + bdeCanCnfUsr + ")";
                                            if(objCanActIngDetMovInv!=null){
                                                if(objCanAntIngDetMovInv!=null)
                                                    strSQL+=",";
                                                strSQL+="" + objCanActIngDetMovInv + "=(CASE WHEN " + objCanActIngDetMovInv + " IS NULL THEN 0 ELSE " + objCanActIngDetMovInv + " END)" + strSigCamActIng + "(" + bdeCanCnfUsr + ")";
                                            }
                                            strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP_REL) + " AND co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC_REL) + "";
                                            strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC_REL) + " AND co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC_REL) + "";
                                            strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG_REL) + "";
                                            strSQL+=";";
                                            //tbm_invBod
                                            strSQL+=" UPDATE tbm_invBod";
                                            strSQL+=" SET ";
                                            if(objCanAntIngInvBod!=null)
                                                strSQL+="" + objCanAntIngInvBod + "=(CASE WHEN " + objCanAntIngInvBod + " IS NULL THEN 0 ELSE " + objCanAntIngInvBod + " END)" + strSigCamAntIng + "(" + bdeCanCnfUsr + ")";
                                            if(objCanActIngInvBod!=null){
                                                if(objCanAntIngInvBod!=null)
                                                    strSQL+=",";
                                                strSQL+="" + objCanActIngInvBod + "=(CASE WHEN " + objCanActIngInvBod + " IS NULL THEN 0 ELSE " + objCanActIngInvBod + " END)" + strSigCamActIng + "(" + bdeCanCnfUsr + ")";
                                            }
                                            strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP_REL) + "";
                                            strSQL+=" AND co_itm=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_ITM_EMP) + "";
                                            strSQL+=" AND co_bod=" + intCodBodIng + "";
                                            strSQL+=";";
                                            strSQLUpd+=strSQL;                                        
                                            System.out.println("strSQLUpd: " + strSQLUpd);
                                            stm.executeUpdate(strSQLUpd);
                                            strSQLUpd="";

                                            strSQL="";
                                            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                                            strSQL+=" , CASE WHEN a1.nd_can IS NULL THEN 0 ELSE a1.nd_can END AS nd_can";
                                            strSQL+=" , CASE WHEN a1.nd_canCon IS NULL THEN 0 ELSE a1.nd_canCon END AS nd_canCon";
                                            strSQL+=" , CASE WHEN a1.nd_canNunRec IS NULL THEN 0 ELSE a1.nd_canNunRec END AS nd_canNunRec";
                                            strSQL+=" , " + objCanAntIngDetMovInv + " AS nd_canIngBod";
                                            strSQL+=" FROM tbm_detMovInv AS a1";
                                            strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP_REL) + "";
                                            strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC_REL) + "";
                                            strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC_REL) + "";
                                            strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC_REL) + "";
                                            strSQL+=" AND a1.co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG_REL) + "";
                                            System.out.println("SQL 1: " + strSQL);
                                            strSQL+=";";
                                            rst=stm.executeQuery(strSQL);
                                            if(rst.next()){
                                                bdeCanCnfDBIng=rst.getBigDecimal("nd_canCon");
                                                bdeCanNunRecDBIng=rst.getBigDecimal("nd_canNunRec");
                                            }

                                            strSQL="";
                                            strSQL+=" UPDATE tbm_detMovInv";
                                            strSQL+=" SET ";
                                            strSQL+=" nd_canPen=nd_can-" + (bdeCanCnfDBIng).abs() + "-" + (bdeCanNunRecDBIng).abs() + "";
                                            strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP_REL) + " AND co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC_REL) + "";
                                            strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC_REL) + " AND co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC_REL) + "";
                                            strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG_REL) + "";
                                            strSQL+=";";
                                            strSQLUpd+=strSQL;                                        
                                            System.out.println("strSQLUpd: " + strSQLUpd);
                                            stm.executeUpdate(strSQLUpd);
                                            strSQLUpd="";

                                            //*****registro de egreso
                                            if(objParSis.getCodigoMenu()==4000){//Confirmación de ingresos - despacho.... por bodega no debe actualizar en el registro de egreso asociado
                                                bdeCanCnfUsr=new BigDecimal(objUti.getObjectValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN)==null?"0":(objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN).equals("")?"0":objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_CAN)));
                                                bdeCanNotCnfUsr=new BigDecimal(objUti.getObjectValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF)==null?"0":(objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF).equals("")?"0":objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_CAN_ITM_NOT_CNF)));
                                                strSQL="";
                                                strSQL+="UPDATE tbm_detMovInv";
                                                strSQL+=" SET ";
                                                strSQL+=" nd_canTra=(CASE WHEN nd_canTra IS NULL THEN 0 ELSE nd_canTra END)+(" + bdeCanCnfUsr.abs() + ")";
                                                strSQL+=", nd_canCon=((CASE WHEN nd_canCon IS NULL THEN 0 ELSE nd_canCon END)-" + bdeCanCnfUsr + ")";
                                                strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + " AND co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                                strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + " AND co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                                strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                                strSQL+=";";                                       
                                                //tbm_invBod
                                                strSQL+=" UPDATE tbm_invBod";
                                                strSQL+=" SET ";
                                                strSQL+=" nd_canTra=((CASE WHEN nd_canTra IS NULL THEN 0 ELSE nd_canTra END)+" + bdeCanCnfUsr.abs() + ")";
                                                strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + "";
                                                strSQL+=" AND co_itm=(";
                                                strSQL+="        SELECT a1.co_itm";
                                                strSQL+="        FROM tbm_detMovInv AS a1 INNER JOIN tbr_detMovInv AS a2";
                                                strSQL+="        ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg";
                                                strSQL+="        WHERE a2.co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + "";
                                                strSQL+="        AND a2.co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                                strSQL+="        AND a2.co_tipDoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + "";
                                                strSQL+="        AND a2.co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                                strSQL+="        AND a2.co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                                strSQL+=")";
                                                strSQL+=" AND co_bod=" + intCodBodEgr + "";
                                                strSQL+=";";
                                                strSQLUpd+=strSQL;                                        
                                                System.out.println("strSQLUpd: " + strSQLUpd);
                                                stm.executeUpdate(strSQLUpd);
                                                strSQLUpd="";

                                                strSQL="";
                                                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                                                strSQL+=" , CASE WHEN a1.nd_can IS NULL THEN 0 ELSE a1.nd_can END AS nd_can";
                                                strSQL+=" , CASE WHEN a1.nd_canCon IS NULL THEN 0 ELSE a1.nd_canCon END AS nd_canCon";
                                                strSQL+=" , CASE WHEN a1.nd_canNunRec IS NULL THEN 0 ELSE a1.nd_canNunRec END AS nd_canNunRec";
                                                strSQL+=" , " + objCanAntEgrDetMovInv + " AS nd_canTra";
                                                strSQL+=" FROM tbm_detMovInv AS a1";
                                                strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + "";
                                                strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                                strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + "";
                                                strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                                strSQL+=" AND a1.co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                                strSQL+=";";
                                                System.out.println("SQL 2: " + strSQL);
                                                rst=stm.executeQuery(strSQL);
                                                if(rst.next()){
                                                    bdeCanCnfDBEgr=rst.getBigDecimal("nd_canCon");
                                                    bdeCanNunRecDBEgr=rst.getBigDecimal("nd_canNunRec");
                                                }

                                                System.out.println("***bdeCanCnfDBEgr: " + bdeCanCnfDBEgr);
                                                System.out.println("***bdeCanNunRecDBEgr: " + bdeCanNunRecDBEgr);

                                                strSQL="";
                                                strSQL+="UPDATE tbm_detMovInv";
                                                strSQL+=" SET ";
                                                strSQL+=" nd_canPen=nd_can+" + (bdeCanCnfDBEgr).abs() + "+" + (bdeCanNunRecDBEgr).abs() + "";
                                                strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_EMP) + " AND co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_LOC) + "";
                                                strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_TIP_DOC) + " AND co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_DOC) + "";
                                                strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatTrsEgrIng, i, INT_ARL_DAT_TRS_COD_REG) + "";
                                                strSQL+=";";
                                                strSQLUpd+=strSQL;                                        
                                                System.out.println("strSQLUpd: " + strSQLUpd);
                                                stm.executeUpdate(strSQLUpd);
                                                strSQLUpd="";
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;                
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
    
    //
    /**
     * Función que permite actualizar campos al momento de generarse las FACVEN
     * Se llamará a través del Servicio que generará la Factura cuando se confirme el ingreso de la mercadería INBOPR
     * @param con Conexión a la base de datos
     * @param arregloDatos
     * @param tipoMovimiento Solo strTipMov="V"
     * @param codigoBodegaEmpresa
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    public boolean setCnfIngGenFac(Connection con, ArrayList arregloDatos, String tipoMovimiento, int codigoBodegaEmpresa){
        boolean blnRes=true;
        String strTipMov=tipoMovimiento;
        arlDatCnf.clear();
        arlDatCnf=arregloDatos;
        
        Object objCanAntIngDetMovInv="";
        Object objCanActIngDetMovInv="";
        Object objCanAntIngInvBod="";
        Object objCanActIngInvBod="";
        
        String strSigCamAntIng="", strSigCamActIng="";
        String strSQLUpd="";
        try{
            if(con!=null){
                stm=con.createStatement();
                //Borre el query para obtener los campos que se deben actualizar
                if(strTipMov.equals("I")){//COINDE & COINBO   (Mercadería local)
                    strSigCamAntIng="-";//anterior -
                    strSigCamActIng="+";//actual +
                }
                
                if(arlDatCnf.size()>0){
                    objCanAntIngDetMovInv="nd_canDesEntCli";
                    objCanActIngDetMovInv="nd_canCon";

                    for(int i=0; i<arlDatCnf.size(); i++){
                        //tbm_detMovInv
                        strSQL="";
                        strSQL+="UPDATE tbm_detMovInv";
                        strSQL+=" SET";

                        //I : Ingresos de cualquier tipo
                        if(objCanAntIngDetMovInv!=null)
                            strSQL+="" + objCanAntIngDetMovInv + "=" + objCanAntIngDetMovInv + "" + strSigCamAntIng + "" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_CAN_ITM_CNF) + "";
                        if(objCanActIngDetMovInv!=null)
                            strSQL+="" + objCanActIngDetMovInv + "=" + objCanActIngDetMovInv + "" + strSigCamActIng + "" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_CAN_ITM_CNF) + "";
                        strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_EMP_CNF) + " AND co_loc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_LOC_CNF) + "";
                        strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_TIP_DOC_CNF) + " AND co_doc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_DOC_CNF) + "";
                        strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_REG_CNF) + "";
                        strSQL+=";";
                        //tbm_invBod
                        strSQL+=" UPDATE tbm_invBod";
                        strSQL+=" SET ";
                        //I : Ingresos de cualquier tipo
                        if(objCanAntIngInvBod!=null)
                            strSQL+="" + objCanAntIngInvBod + "=" + objCanAntIngInvBod + "" + strSigCamAntIng + "" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_CAN_ITM_CNF) + "";
                        if(objCanActIngInvBod!=null)
                            strSQL+="" + objCanActIngInvBod + "=" + objCanActIngInvBod + "" + strSigCamActIng + "" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_CAN_ITM_CNF) + "";
                        strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_EMP_CNF) + "";
                        strSQL+=" AND co_itm=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_ITM_EMP_CNF) + "";
                        strSQL+=" AND co_bod=" + codigoBodegaEmpresa + "";
                        strSQL+=";";
                        strSQLUpd+=strSQL;
                    }
                    stm.executeUpdate(strSQLUpd);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;                
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
    
    // <editor-fold defaultstate="collapsed" desc=" /* José Marín: ya estaba comentado 15/Jun/2017  */ ">
    //cuando el estado es E: todos los ingresos confirmados
    /**
     * Función que permite conocer si el proceso de confirmaciones de ingreso realcionado al seguimiento ha sido completado
     * @param con Conexión a la base de datos
     * @param codigoSeguimiento Código de seguimiento
     * @return true Si esta completo el proceso de confirmaciones
     * <BR> false Caso contrario
     */
    /*
    public boolean isProCnfTotActEstCot(Connection con
                                     , int codigoEmpresaSolicitud, int codigoLocalSolicitud, int codigoTipoDocumentoSolicitud, int codigoDocumentoSolicitud){
        boolean blnRes=true;
        int intCodSeg=-1;
        BigDecimal bdeValPenCnf=BigDecimal.ZERO;
        try{
            if(con!=null){
                System.out.println("********  intCodBodGrpCnf: " + intCodBodGrpCnf);
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_seg FROM tbm_cabSegMovInv AS a1 ";
                strSQL+=" WHERE a1.co_emprelcabsoltrainv=" + codigoEmpresaSolicitud + "";
                strSQL+=" AND a1.co_locrelcabsoltrainv=" + codigoLocalSolicitud + "";
                strSQL+=" AND a1.co_tipdocrelcabsoltrainv=" + codigoTipoDocumentoSolicitud + "";
                strSQL+=" AND a1.co_docrelcabsoltrainv=" + codigoDocumentoSolicitud + "";
                strSQL+=" GROUP BY a1.co_seg";
                System.out.println("ERROR 2: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodSeg=rst.getInt("co_seg");
                
                strSQL="";
                strSQL+="SELECT a1.co_emprelcabcotven, a1.co_locrelcabcotven, a1.co_cotrelcabcotven";
                strSQL+=" FROM tbm_cabSegMovInv AS a1";
                strSQL+=" WHERE a1.co_seg=" + intCodSeg + "";
                strSQL+=" AND a1.co_emprelcabcotven IS NOT NULL AND a1.co_locrelcabcotven IS NOT NULL AND a1.co_cotrelcabcotven IS NOT NULL";
                strSQL+=" GROUP BY co_emprelcabcotven, co_locrelcabcotven, co_cotrelcabcotven";
                System.out.println("ERROR 1: " + strSQL);
                rstCab=stm.executeQuery(strSQL);
                if(rstCab.next()){
                        strSQL="";
                        strSQL+=" SELECT (SUM(b1.nd_canPenCnf)) AS nd_canPenCnf FROM(";
                        strSQL+="   SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                        strSQL+="   , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                        strSQL+="   FROM(";
                        strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                        strSQL+="           FROM tbm_cabsegmovinv AS a3 INNER JOIN tbm_cabMovInv AS a1";
                        strSQL+="           ON a3.co_empRelCabMovInv=a1.co_emp AND a3.co_locRelCabMovInv=a1.co_loc";
                        strSQL+="           AND a3.co_tipDocRelCabMovInv=a1.co_tipDoc AND a3.co_docRelCabMovInv=a1.co_doc";
                        strSQL+="           INNER JOIN tbm_detMovInv AS a2";
                        strSQL+="           ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+="           WHERE a3.co_seg=" + intCodSeg + "";
                        strSQL+="   ) AS a1";
                        strSQL+="   INNER JOIN(";
                        strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon";
                        strSQL+="           , (a2.nd_can - a2.nd_canCon) AS nd_canPenCnf";
                        strSQL+="           FROM tbm_cabMovInv AS a1";
                        strSQL+="           INNER JOIN tbm_detMovInv AS a2";
                        strSQL+="           ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+="           INNER JOIN tbr_detMovInv AS a3";
                        strSQL+="           ON a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel AND a2.co_tipDoc=a3.co_tipDocRel AND a2.co_doc=a3.co_docRel AND a2.co_reg=a3.co_regRel";
                        strSQL+="           WHERE a2.st_meringegrfisbod='S' AND (a2.nd_can - a2.nd_canCon)<>0";                        
                        strSQL+="           AND (a2.nd_canPen<>0 OR a2.nd_canPen IS NOT NULL)";
                        strSQL+="   ) AS a2";
                        strSQL+="   ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+="   GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                        strSQL+="   , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                        strSQL+=" ) AS b1";
                        strSQL+=" HAVING (SUM(b1.nd_canPenCnf)) <>0";
                        System.out.println("isProCnfTotActEstCot: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        
                        if(rst.next()){
                            bdeValPenCnf=new BigDecimal(rst.getObject("nd_canPenCnf")==null?"0":(rst.getString("nd_canPenCnf").equals("")?"0":rst.getString("nd_canPenCnf")));
                            if(bdeValPenCnf.compareTo(BigDecimal.ZERO)==0){
                                if(setEstCotizacion(con, intCodSeg)){
                                    if(getCodSegGenFacAut(con, codigoEmpresaSolicitud, codigoLocalSolicitud, codigoTipoDocumentoSolicitud, codigoDocumentoSolicitud)){
                                        if(objGenFacAut.iniciarProcesoGeneraFactura(con, intCodSegGenFacAut)){
                                            System.out.println("***GENERA FACTURA ");
                                        }
                                    }
                                    blnRes=true;
                                }
                                else
                                    blnRes=false;
                            }
                        }
                        else{
                            if(setEstCotizacion(con, intCodSeg)){
                                if(getCodSegGenFacAut(con, codigoEmpresaSolicitud, codigoLocalSolicitud, codigoTipoDocumentoSolicitud, codigoDocumentoSolicitud)){
                                    if(objGenFacAut.iniciarProcesoGeneraFactura(con, intCodSegGenFacAut)){
                                        System.out.println("***GENERA FACTURA ");
                                    }
                                }
                                blnRes=true;
                            }
                            else
                                blnRes=false;
                        }
                        rst.close();
                        rst=null;
                    }                
                    stm.close();
                    stm=null;
                    rstCab.close();
                    rstCab=null;
                }
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
    */
    
    //</editor-fold>
    
    /**
     * Función que permite conocer si el proceso de confirmaciones de ingreso realcionado al seguimiento ha sido completado
     * @param con Conexión a la base de datos
     * @param codigoSeguimiento Código de seguimiento
     * @return true Si esta completo el proceso de confirmaciones
     * <BR> false Caso contrario
     */
    public boolean isProCnfTotActEstCot(Connection con, int codigoSeguimiento, int codigoMenu){
        boolean blnRes=true;
        int intCodSeg=codigoSeguimiento;
        BigDecimal bdeValPenCnf=BigDecimal.ZERO;
        try{
            if(con!=null){
                if(intCodSeg!=-1){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="SELECT a1.co_emprelcabcotven, a1.co_locrelcabcotven, a1.co_cotrelcabcotven";
                    strSQL+=" FROM tbm_cabSegMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_cabCotVen as a2 ON (a1.co_empRelCabCotVen=a2.co_emp AND a1.co_locRelCabCotVen=a2.co_loc AND   ";
                    strSQL+="                                     a1.co_cotRelCabCotVen=a2.co_cot )";
                    strSQL+=" WHERE a1.co_seg=" + intCodSeg + " AND a2.st_autSolResInv IS NULL  AND a2.st_solResInv IS NULL  ";
                    strSQL+=" AND a1.co_emprelcabcotven IS NOT NULL AND a1.co_locrelcabcotven IS NOT NULL AND a1.co_cotrelcabcotven IS NOT NULL";
                    strSQL+=" ";
                    strSQL+=" GROUP BY co_emprelcabcotven, co_locrelcabcotven, co_cotrelcabcotven";
                    System.out.println("ERROR 1: " + strSQL);
                    rstCab=stm.executeQuery(strSQL);
                    if(rstCab.next()){
                        strSQL="";
                        strSQL+=" SELECT (SUM(b1.nd_canPenCnf)) AS nd_canPenCnf FROM(";
                        strSQL+="   SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                        strSQL+="   , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                        strSQL+="   FROM(";
                        strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                        strSQL+="           FROM tbm_cabsegmovinv AS a3 INNER JOIN tbm_cabMovInv AS a1";
                        strSQL+="           ON a3.co_empRelCabMovInv=a1.co_emp AND a3.co_locRelCabMovInv=a1.co_loc";
                        strSQL+="           AND a3.co_tipDocRelCabMovInv=a1.co_tipDoc AND a3.co_docRelCabMovInv=a1.co_doc";
                        strSQL+="           INNER JOIN tbm_detMovInv AS a2";
                        strSQL+="           ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+="           WHERE a3.co_seg=" + intCodSeg + " AND (a1.tx_tipMov='V' OR a1.tx_tipMov='I' OR a1.tx_tipMov='R') "; /* JM: Facturacion parcial 2018/Oct/24 */
                        strSQL+="   ) AS a1";
                        strSQL+="   INNER JOIN(";
                        strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon";
                        strSQL+="           , (a2.nd_can - a2.nd_canCon) AS nd_canPenCnf";
                        strSQL+="           FROM tbm_cabMovInv AS a1";
                        strSQL+="           INNER JOIN (tbm_detMovInv AS a2";
                        strSQL+="                       LEFT OUTER JOIN tbr_detMovInv AS a3";
                        strSQL+="                       ON a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel AND a2.co_tipDoc=a3.co_tipDocRel AND a2.co_doc=a3.co_docRel AND a2.co_reg=a3.co_regRel";
                        strSQL+="           )";
                        strSQL+="           ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+="           WHERE a2.st_meringegrfisbod='S' AND (a2.nd_can - a2.nd_canCon)<>0";                        
                        strSQL+="           AND (a2.nd_canPen<>0 OR a2.nd_canPen IS NOT NULL) AND a2.nd_can>0";
                        strSQL+="           AND (a1.tx_tipMov='V' OR a1.tx_tipMov='I' OR a1.tx_tipMov='R') "; /* JM: Facturacion parcial 2018/Oct/24 */
                        strSQL+="   ) AS a2";
                        strSQL+="   ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+="   GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                        strSQL+="   , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                        strSQL+=" ) AS b1";
                        strSQL+=" HAVING (SUM(b1.nd_canPenCnf)) <>0";
                        System.out.println("isProCnfTotActEstCot: " + strSQL);
                        rst=stm.executeQuery(strSQL);

                        if(rst.next()){
                            bdeValPenCnf=new BigDecimal(rst.getObject("nd_canPenCnf")==null?"0":(rst.getString("nd_canPenCnf").equals("")?"0":rst.getString("nd_canPenCnf")));
                            if(bdeValPenCnf.compareTo(BigDecimal.ZERO)==0){
                                if(codigoMenu==4000 || codigoMenu==4010){
                                    if(setEstCotizacion(con, intCodSeg)){
                                        if(objGenFacAut.iniciarProcesoGeneraFactura(con, intCodSeg)){
                                            System.out.println("***GENERA FACTURA ");
                                            blnRes=true;
                                        }
                                        else
                                            blnRes=false;
                                    }
                                    else 
                                        blnRes=false;
                                }
                                else
                                    blnRes=true;
                            }
                        }
                        else{
                            if(codigoMenu==4000 || codigoMenu==4010){
                                if(setEstCotizacion(con, intCodSeg)){
                                    if(objGenFacAut.iniciarProcesoGeneraFactura(con, intCodSeg)){
                                        System.out.println("***GENERA FACTURA ");
                                        blnRes=true;
                                    }
                                    else
                                        blnRes=false;
                                }
                                else 
                                    blnRes=false;
                            }
                            else
                                blnRes=true;
                        }
                        rst.close();
                        rst=null;
                    }                
                    stm.close();
                    stm=null;
                    rstCab.close();
                    rstCab=null;
                }
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
     * Función que permite cambiar el estado de confirmación de la cotización
     * @param con
     * @param codigoSeguimiento
     * @return 
     */
    private boolean setEstCotizacion(Connection con, int codigoSeguimiento){
        boolean blnRes=true;
        Statement stmEstCot;
        try{
            if(con!=null){
                stmEstCot=con.createStatement();
                strSQL="";
                strSQL+=" UPDATE tbm_cabCotVen";
                strSQL+=" SET st_reg='L' FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_cot";
                strSQL+=" 	FROM tbm_cabsegmovinv AS a3 INNER JOIN tbm_cabCotVen AS a1";
                strSQL+=" 	ON a3.co_empRelCabCotVen=a1.co_emp AND a3.co_locRelCabCotVen=a1.co_loc AND a3.co_cotRelCabCotVen=a1.co_cot";
                strSQL+=" 	WHERE a3.co_seg=" + codigoSeguimiento + " AND a1.st_reg='E'";
                strSQL+=" ) AS b1";
                strSQL+=" WHERE tbm_cabCotVen.co_emp=b1.co_emp AND tbm_cabCotVen.co_loc=b1.co_loc AND tbm_cabCotVen.co_cot=b1.co_cot";
                strSQL+=";";
                System.out.println("setEstCotizacion: " + strSQL);
                stmEstCot.executeUpdate(strSQL);
                stmEstCot.close();
                stmEstCot=null;
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
     * E: por lo menos una confirmacion sea de egreso o ingreso(siempre sera un egreso primero porque debe salir para entrar
     * C: cuando se han confirmado todos los egresos y todos los ingresos
     * @param con
     * @param codigoSeguimiento
     * @return 
     */
    public boolean isProCnfTotActEstSol(Connection con, int codigoSeguimiento){
        boolean blnRes=true;
        int intCodSeg=codigoSeguimiento;
        BigDecimal bdeValPenCnf=BigDecimal.ZERO;
        try{
            if(con!=null){
                stm=con.createStatement();
                if(intCodSeg!=-1){
                    strSQL="";
                    //--INGRESO
                    strSQL+=" SELECT SUM(b1.nd_canPenCnf) AS nd_canPenCnf FROM(";
                    strSQL+=" 	(";
                    strSQL+=" 		SELECT ABS(SUM(b1.nd_canPenCnf)) AS nd_canPenCnf FROM(";
                    strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                    strSQL+=" 			, a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.nd_canPenCnf";
                    strSQL+=" 			FROM(";
                    strSQL+=" 				SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a2.co_reg";
                    strSQL+=" 				FROM tbm_cabsegmovinv AS a3 INNER JOIN tbm_cabMovInv AS a1";
                    strSQL+=" 				ON a3.co_empRelCabMovInv=a1.co_emp AND a3.co_locRelCabMovInv=a1.co_loc";
                    strSQL+=" 				AND a3.co_tipDocRelCabMovInv=a1.co_tipDoc AND a3.co_docRelCabMovInv=a1.co_doc";
                    strSQL+=" 				INNER JOIN tbm_detMovInv AS a2";
                    strSQL+=" 				ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 				WHERE a3.co_seg=" + intCodSeg + "";
                    strSQL+=" 				AND a1.co_tipDoc NOT IN(14,245)";
                    strSQL+=" 				GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a2.co_reg";
                    strSQL+=" 			) AS a1";
                    strSQL+=" 			INNER JOIN(";
                    strSQL+=" 				SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon";
                    strSQL+=" 				, (a2.nd_can - a2.nd_canCon) AS nd_canPenCnf";
                    strSQL+=" 				FROM tbm_cabMovInv AS a1";
                    strSQL+=" 				INNER JOIN (tbm_detMovInv AS a2";
                    strSQL+="                                       LEFT OUTER JOIN tbr_detMovInv AS a3";
                    strSQL+="                                       ON a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel AND a2.co_tipDoc=a3.co_tipDocRel AND a2.co_doc=a3.co_docRel AND a2.co_reg=a3.co_regRel";
                    strSQL+=" 				)";
                    strSQL+=" 				ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 				WHERE a2.st_meringegrfisbod='S' AND (a2.nd_can - a2.nd_canCon)<>0";
                    strSQL+="                           AND (a2.nd_canPen<>0 OR a2.nd_canPen IS NOT NULL) AND a2.nd_can>0";
                    strSQL+=" 			) AS a2";
                    strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg";
                    strSQL+=" 			GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                    strSQL+=" 			, a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.nd_canPenCnf";
                    strSQL+=" 		) AS b1";
                    strSQL+=" 		HAVING (SUM(b1.nd_canPenCnf)) <>0";
                    strSQL+=" 	)";
                    strSQL+=" 	UNION ALL";
                    //--EGRESO
                    strSQL+=" 	(";
                    strSQL+=" 		SELECT ABS(SUM(b1.nd_canPenCnf)) AS nd_canPenCnf FROM(";
                    strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                    strSQL+=" 			, a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                    strSQL+=" 			FROM(";
                    strSQL+=" 				SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                    strSQL+=" 				FROM tbm_cabsegmovinv AS a3 INNER JOIN tbm_cabMovInv AS a1";
                    strSQL+=" 				ON a3.co_empRelCabMovInv=a1.co_emp AND a3.co_locRelCabMovInv=a1.co_loc";
                    strSQL+=" 				AND a3.co_tipDocRelCabMovInv=a1.co_tipDoc AND a3.co_docRelCabMovInv=a1.co_doc";
                    strSQL+=" 				INNER JOIN tbm_detMovInv AS a2";
                    strSQL+=" 				ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 				WHERE a3.co_seg=" + intCodSeg + "";
                    strSQL+=" 			) AS a1";
                    strSQL+=" 			INNER JOIN(";
                    strSQL+=" 				SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon";
                    strSQL+=" 				, (a2.nd_can - a2.nd_canCon) AS nd_canPenCnf";
                    strSQL+=" 				FROM tbm_cabMovInv AS a1";
                    strSQL+=" 				INNER JOIN (tbm_detMovInv AS a2";
                    strSQL+="                                       LEFT OUTER JOIN tbr_detMovInv AS a3";
                    strSQL+="                                       ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
                    strSQL+="                                   )";
                    strSQL+=" 				ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";                    
                    strSQL+=" 				WHERE a2.st_meringegrfisbod='S' AND (a2.nd_can - a2.nd_canCon)<>0";
                    strSQL+=" 				AND (a2.nd_canPen<>0 OR a2.nd_canPen IS NOT NULL) AND a2.nd_can<0";
                    strSQL+=" 			) AS a2";
                    strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 			GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                    strSQL+=" 			, a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                    strSQL+=" 		) AS b1";
                    strSQL+=" 		HAVING (SUM(b1.nd_canPenCnf)) <>0";
                    strSQL+=" 	)";
                    strSQL+=" ) AS b1";
                    strSQL+=" HAVING SUM(b1.nd_canPenCnf)<>0";
                    System.out.println("isProCnfTotActEstSol: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        System.out.println("* ENTRO!!!!");
                        bdeValPenCnf=new BigDecimal(rst.getObject("nd_canPenCnf")==null?"0":(rst.getString("nd_canPenCnf").equals("")?"0":rst.getString("nd_canPenCnf")));
                        System.out.println("bdeValPenCnf: " + bdeValPenCnf);
                        System.out.println("** ENTRO!!!!");
                        if(bdeValPenCnf.compareTo(BigDecimal.ZERO)==0){
                            System.out.println("*** ENTRO!!!!");
                            if(!setEstSolicitud(con, intCodSeg)){
                                System.out.println("**** ENTRO!!!!");
                                blnRes=false;
                            }
                        }
                    }
                    else{
                        System.out.println("* NO ENTRO!!!!");
                        if(!setEstSolicitud(con, intCodSeg)){
                            System.out.println("* NO ENTRO!!!!");
                            blnRes=false;
                        }
                    }
                    rst.close();
                    rst=null;
                }    
                stm.close();
                stm=null;

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
     * Función que permite cambiar el estado de confirmación de la solicitud
     * @param con
     * @param codigoSeguimiento
     * @return 
     */
    private boolean setEstSolicitud(Connection con, int codigoSeguimiento){
        boolean blnRes=true;
        Statement stmEstSol;
        try{
            if(con!=null){
                stmEstSol=con.createStatement();
                strSQL="";
                strSQL+=" UPDATE tbm_cabSolTraInv";
                strSQL+=" SET st_conInv='C' FROM(";
                strSQL+=" 	SELECT a3.co_seg, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 	FROM tbm_cabSegMovInv AS a3 INNER JOIN tbm_cabSolTraInv AS a1";
                strSQL+=" 	ON a3.co_empRelCabSolTraInv=a1.co_emp AND a3.co_locRelCabSolTraInv=a1.co_loc";
                strSQL+=" 	AND a3.co_tipDocRelcabSolTraInv=a1.co_tipDoc AND a3.co_docRelCabSolTraInv=a1.co_doc";
                strSQL+=" 	WHERE a3.co_seg=" + codigoSeguimiento + "";
                strSQL+=" 	AND a1.st_conInv IN('P', 'E')";
                strSQL+=" ) AS b1";
                strSQL+=" WHERE tbm_cabSolTraInv.co_emp=b1.co_emp AND tbm_cabSolTraInv.co_loc=b1.co_loc";
                strSQL+=" AND tbm_cabSolTraInv.co_tipDoc=b1.co_tipDoc AND tbm_cabSolTraInv.co_doc=b1.co_doc";
                strSQL+=";";
                System.out.println("setEstSolicitud: " + strSQL);
                stmEstSol.executeUpdate(strSQL);
                stmEstSol.close();
                stmEstSol=null;
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
     * Función que permite saber si el documento origen EGBOPR, FACVEN, TX se ha confirmado completamente
     * @param con
     * @param codigoEmpresaSolicitud
     * @param codigoLocalSolicitud
     * @param codigoTipoDocumentoSolicitud
     * @param codigoDocumentoSolicitud
     * @param tipoOperacion I: confirmación de ingreso   E: confirmación de egreso
     * @return 
     */
    public boolean isCnfTotDocOri(Connection con, int codigoSeguimiento, String tipoOperacion
                                  , int codigoEmpresaOrigen, int codigoLocalOrigen, int codigoTipoDocumentoOrigen, int codigoDocumentoOrigen){
        boolean blnRes=true;
        int intCodSeg=codigoSeguimiento;
        BigDecimal bdeValPenCnf=BigDecimal.ZERO;
        //int intCodEmpDocIngEgr=-1, intCodLocDocIngEgr=-1, intCodTipDocDocIngEgr=-1, intCodDocDocIngEgr=-1;
        try{
            if(con!=null){
                System.out.println("tipoOperacion: " + tipoOperacion);
                stm=con.createStatement();
                if(intCodSeg!=-1){
//                    if(arlDatTrsEgrIng.size()>0){
                        //--INGRESO
                        if(tipoOperacion.equals("I")){
                            strSQL="";
                            strSQL+=" SELECT SUM(b1.nd_canPenCnf) AS nd_canPenCnf FROM(";
                            strSQL+=" 	(";
                            strSQL+=" 		SELECT ABS(SUM(b1.nd_canPenCnf)) AS nd_canPenCnf FROM(";
                            strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                            strSQL+=" 			, a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                            strSQL+=" 			FROM(";
                            strSQL+=" 				SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                            strSQL+=" 				FROM tbm_cabsegmovinv AS a3 INNER JOIN tbm_cabMovInv AS a1";
                            strSQL+=" 				ON a3.co_empRelCabMovInv=a1.co_emp AND a3.co_locRelCabMovInv=a1.co_loc";
                            strSQL+=" 				AND a3.co_tipDocRelCabMovInv=a1.co_tipDoc AND a3.co_docRelCabMovInv=a1.co_doc";
                            strSQL+=" 				INNER JOIN tbm_detMovInv AS a2";
                            strSQL+=" 				ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+=" 				WHERE a3.co_seg=" + intCodSeg + "";
                            strSQL+=" 			) AS a1";
                            strSQL+=" 			INNER JOIN(";
//                            strSQL+=" 				SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon";
//                            strSQL+=" 				, (a2.nd_can - a2.nd_canDesEntCli) AS nd_canPenCnf";
//                            strSQL+=" 				FROM tbm_cabMovInv AS a1";
//                            strSQL+=" 				INNER JOIN tbm_detMovInv AS a2";
//                            strSQL+=" 				ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
//                            strSQL+=" 				INNER JOIN tbr_detMovInv AS a3";
//                            strSQL+=" 				ON a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel AND a2.co_tipDoc=a3.co_tipDocRel AND a2.co_doc=a3.co_docRel AND a2.co_reg=a3.co_regRel";
//                            strSQL+=" 				WHERE a2.st_meringegrfisbod='S' AND (a2.nd_can - a2.nd_canCon)<>0";
//                            strSQL+=" 				AND (a2.nd_canPen<>0 OR a2.nd_canPen IS NOT NULL)";
////                            strSQL+="                               AND a1.co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, 0, INT_ARL_DAT_TRS_COD_EMP_REL) + "";
////                            strSQL+="                               AND a1.co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, 0, INT_ARL_DAT_TRS_COD_LOC_REL) + "";
////                            strSQL+="                               AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, 0, INT_ARL_DAT_TRS_COD_TIP_DOC_REL) + "";
////                            strSQL+="                               AND a1.co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, 0, INT_ARL_DAT_TRS_COD_DOC_REL) + "";                        
//                            strSQL+="                               AND a1.co_emp=" + codigoEmpresaOrigen + "";
//                            strSQL+="                               AND a1.co_loc=" + codigoLocalOrigen + "";
//                            strSQL+="                               AND a1.co_tipDoc=" + codigoTipoDocumentoOrigen + "";
//                            strSQL+="                               AND a1.co_doc=" + codigoDocumentoOrigen + "";                        
                            
                            
                            strSQL+=" 				SELECT v1.co_emp, v1.co_loc, v1.co_tipDoc, v1.co_doc, v1.co_reg, v1.st_meringegrfisbod, v1.nd_can, v1.nd_canCon";
                            strSQL+=" 				, (CASE WHEN v1.co_empRel IS NULL THEN v1.nd_canIngBod ELSE (v1.nd_can - v1.nd_canDesEntCli) END  ) AS nd_canPenCnf";
                            strSQL+=" 				, v1.co_emp AS co_empRel FROM(";
                            strSQL+=" 					SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canIngBod";
                            strSQL+=" 					, CASE WHEN a2.nd_canDesEntCli IS NULL THEN 0 ELSE a2.nd_canDesEntCli END AS nd_canDesEntCli";
                            strSQL+=" 					, a3.co_emp AS co_empRel";
                            strSQL+=" 					FROM tbm_cabMovInv AS a1";
                            strSQL+=" 					INNER JOIN (tbm_detMovInv AS a2";
                            strSQL+=" 							LEFT OUTER JOIN tbr_detMovInv AS a3";
                            strSQL+=" 							ON a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel AND a2.co_tipDoc=a3.co_tipDocRel AND a2.co_doc=a3.co_docRel AND a2.co_reg=a3.co_regRel";
                            strSQL+=" 						   )";
                            strSQL+=" 					ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+=" 					WHERE a2.st_meringegrfisbod='S' AND (a2.nd_can - a2.nd_canCon)<>0";
                            strSQL+=" 					AND (a2.nd_canPen<>0 OR a2.nd_canPen IS NOT NULL) AND a2.nd_can>0";
                            
                            strSQL+="                               AND a1.co_emp=" + codigoEmpresaOrigen + "";
                            strSQL+="                               AND a1.co_loc=" + codigoLocalOrigen + "";
                            strSQL+="                               AND a1.co_tipDoc=" + codigoTipoDocumentoOrigen + "";
                            strSQL+="                               AND a1.co_doc=" + codigoDocumentoOrigen + "";
                            strSQL+=" 				) AS v1";
                            strSQL+=" 			) AS a2";
                            strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+=" 			GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                            strSQL+=" 			, a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                            strSQL+=" 		) AS b1";
                            strSQL+=" 		HAVING (SUM(b1.nd_canPenCnf)) <>0";
                            strSQL+=" 	)";
                            strSQL+=" ) AS b1";
                        }
                        else if(tipoOperacion.equals("E")){//--EGRESO
                            strSQL="";
                            strSQL+=" SELECT SUM(b1.nd_canPenCnf) AS nd_canPenCnf FROM(";
                            strSQL+=" 	(";
                            strSQL+=" 		SELECT ABS(SUM(b1.nd_canPenCnf)) AS nd_canPenCnf FROM(";
                            strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                            strSQL+=" 			, a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canTra, a2.nd_canPenCnf";
                            strSQL+=" 			FROM(";
                            strSQL+=" 				SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                            strSQL+=" 				FROM tbm_cabsegmovinv AS a3 INNER JOIN tbm_cabMovInv AS a1";
                            strSQL+=" 				ON a3.co_empRelCabMovInv=a1.co_emp AND a3.co_locRelCabMovInv=a1.co_loc";
                            strSQL+=" 				AND a3.co_tipDocRelCabMovInv=a1.co_tipDoc AND a3.co_docRelCabMovInv=a1.co_doc";
                            strSQL+=" 				INNER JOIN tbm_detMovInv AS a2";
                            strSQL+=" 				ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+=" 				WHERE a3.co_seg=" + intCodSeg + "";
                            strSQL+=" 			) AS a1";
                            strSQL+=" 			INNER JOIN(";
                            strSQL+=" 				SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can";
                            strSQL+="                           , a2.nd_canEgrBod, a2.nd_canTra, a2.nd_canDesEntCli, a2.nd_canNunRec";
                            //strSQL+=" 				, (a2.nd_can - (CASE WHEN a2.nd_canTra IS NULL THEN 0 ELSE a2.nd_canTra END)) AS nd_canPenCnf";
                            
                            strSQL+="                           , (a2.nd_can";
                            strSQL+="                           	- a2.nd_canCon";
                            strSQL+="                                   - a2.nd_canNunRec";
                            strSQL+="                                   - (CASE WHEN a2.nd_canTra IS NULL THEN 0 ELSE a2.nd_canTra END)";                            
                            strSQL+="                     ) AS nd_canPenCnf ";
      
                            strSQL+=" 				FROM tbm_cabMovInv AS a1";
                            strSQL+=" 				INNER JOIN (tbm_detMovInv AS a2";
                            strSQL+="                                       LEFT OUTER JOIN tbr_detMovInv AS a3";
                            strSQL+="                                       ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
                            strSQL+=" 				)";
                            strSQL+=" 				ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+=" 				WHERE a2.st_meringegrfisbod='S' AND (a2.nd_can - (CASE WHEN a2.nd_canTra IS NULL THEN 0 ELSE a2.nd_canTra END))<>0";
                            strSQL+=" 				AND (a2.nd_canPen<>0 OR a2.nd_canPen IS NOT NULL) AND a2.nd_can<0";
//                            strSQL+="                               AND a1.co_emp=" + objUti.getStringValueAt(arlDatTrsEgrIng, 0, INT_ARL_DAT_TRS_COD_EMP) + "";
//                            strSQL+="                               AND a1.co_loc=" + objUti.getStringValueAt(arlDatTrsEgrIng, 0, INT_ARL_DAT_TRS_COD_LOC) + "";
//                            strSQL+="                               AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatTrsEgrIng, 0, INT_ARL_DAT_TRS_COD_TIP_DOC) + "";
//                            strSQL+="                               AND a1.co_doc=" + objUti.getStringValueAt(arlDatTrsEgrIng, 0, INT_ARL_DAT_TRS_COD_DOC) + "";
                            strSQL+="                               AND a1.co_emp=" + codigoEmpresaOrigen + "";
                            strSQL+="                               AND a1.co_loc=" + codigoLocalOrigen + "";
                            strSQL+="                               AND a1.co_tipDoc=" + codigoTipoDocumentoOrigen + "";
                            strSQL+="                               AND a1.co_doc=" + codigoDocumentoOrigen + "";
                            strSQL+=" 			) AS a2";
                            strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+=" 			GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                            strSQL+=" 			, a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canTra, a2.nd_canPenCnf";
                            strSQL+=" 		) AS b1";
                            //strSQL+=" 		HAVING (SUM(b1.nd_canPenCnf)) <>0";
                            strSQL+=" 	)";
                            strSQL+=" ) AS b1";
                        }
                        System.out.println("isCnfTotDocOri: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            System.out.println("**si");
                            bdeValPenCnf=new BigDecimal(rst.getObject("nd_canPenCnf")==null?"0":(rst.getString("nd_canPenCnf").equals("")?"0":rst.getString("nd_canPenCnf")));
                            System.out.println("**bdeValPenCnf: " + bdeValPenCnf);

//                            if(tipoOperacion.equals("I")){
//                                intCodEmpDocIngEgr=objUti.getIntValueAt(arlDatTrsEgrIng, 0, INT_ARL_DAT_TRS_COD_EMP_REL);
//                                intCodLocDocIngEgr=objUti.getIntValueAt(arlDatTrsEgrIng, 0, INT_ARL_DAT_TRS_COD_LOC_REL);
//                                intCodTipDocDocIngEgr=objUti.getIntValueAt(arlDatTrsEgrIng, 0, INT_ARL_DAT_TRS_COD_TIP_DOC_REL);
//                                intCodDocDocIngEgr=objUti.getIntValueAt(arlDatTrsEgrIng, 0, INT_ARL_DAT_TRS_COD_DOC_REL);
//                            }
//                            else if(tipoOperacion.equals("E")){
//                                intCodEmpDocIngEgr=objUti.getIntValueAt(arlDatTrsEgrIng, 0, INT_ARL_DAT_TRS_COD_EMP);
//                                intCodLocDocIngEgr=objUti.getIntValueAt(arlDatTrsEgrIng, 0, INT_ARL_DAT_TRS_COD_LOC);
//                                intCodTipDocDocIngEgr=objUti.getIntValueAt(arlDatTrsEgrIng, 0, INT_ARL_DAT_TRS_COD_TIP_DOC);
//                                intCodDocDocIngEgr=objUti.getIntValueAt(arlDatTrsEgrIng, 0, INT_ARL_DAT_TRS_COD_DOC);
//                            }

                            if(bdeValPenCnf.compareTo(BigDecimal.ZERO)==0){
                                if(!setEstDocOrigen(con, codigoEmpresaOrigen, codigoLocalOrigen, codigoTipoDocumentoOrigen, codigoDocumentoOrigen))
                                    blnRes=false;
                            }else{
                                if (tipoOperacion.equals("E")) {
                                    if (!setEstParTraInv(con, codigoEmpresaOrigen, codigoLocalOrigen, codigoTipoDocumentoOrigen, codigoDocumentoOrigen)) {   
                                    }
                                }
                            }
                        }
                        else{
                            System.out.println("**no");
                            if(!setEstDocOrigen(con, codigoEmpresaOrigen, codigoLocalOrigen, codigoTipoDocumentoOrigen, codigoDocumentoOrigen))
                                blnRes=false;
                        }
                        rst.close();
                        rst=null;
//                    }

                }
                stm.close();
                stm=null;
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
     * Función que permite cambiar el estado de confirmación del documento origen
     * @param con
     * @param codigoSeguimiento
     * @return 
     */
    private boolean setEstDocOrigen(Connection con, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento){
        boolean blnRes=true;
        Statement stmEstSol;
        try{
            if(con!=null){                
//                if(arlDatTrsEgrIng.size()>0){
                    stmEstSol=con.createStatement();
                    strSQL="";
                    strSQL+=" UPDATE tbm_cabMovInv";
                    strSQL+=" SET st_conInv='C'";
                    strSQL+=" WHERE co_emp=" + codigoEmpresa + "";
                    strSQL+=" AND co_loc=" + codigoLocal + "";
                    strSQL+=" AND co_tipDoc=" + codigoTipoDocumento + "";
                    strSQL+=" AND co_doc=" + codigoDocumento + "";
                    strSQL+=" AND st_conInv IN('P', 'E')";
                    strSQL+=";";
                    System.out.println("setEstDocOrigen: " + strSQL);
                    stmEstSol.executeUpdate(strSQL);
                    stmEstSol.close();
                    stmEstSol=null;
//                }
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
     * Función que permite obtener los documentos de ingresos que están pendientes de confirmar
     * @return true Si obtiene algún registro es porque aún no se debe confirmar
     * <BR> false Caso contrario
     */
    public boolean isDocIngPenCnf(Connection con, int codigoSeguimiento, String tipoOperacion){
        boolean blnRes=true;
        int intCodSeg=codigoSeguimiento;
        BigDecimal bdeCanIngBod=BigDecimal.ZERO;
        BigDecimal bdeCan=BigDecimal.ZERO;
        BigDecimal bdeCanPndCnf=BigDecimal.ZERO;
        try{
            if(con!=null){
                if(tipoOperacion.equals("I")){
                    stm=con.createStatement();
                    if(intCodSeg!=-1){                        
                        strSQL="";
                        //strSQL+=" SELECT SUM(b1.nd_canPenCnf) AS nd_canPenCnf, SUM(b1.nd_canIngBod) AS nd_canIngBod, SUM(b1.nd_can) AS nd_can, SUM(b1.nd_canDesEntCli) AS nd_canDesEntCli, SUM(b1.nd_canEgrBod) AS nd_canEgrBod";
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
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            bdeCanIngBod=new BigDecimal(rst.getObject("nd_canIngBod")==null?"0":(rst.getString("nd_canIngBod").equals("")?"0":rst.getString("nd_canIngBod")));
                            bdeCan=new BigDecimal(rst.getObject("nd_can")==null?"0":(rst.getString("nd_can").equals("")?"0":rst.getString("nd_can")));//rst.getBigDecimal("nd_can");
                            bdeCanPndCnf=new BigDecimal(rst.getObject("nd_canPenCnf")==null?"0":(rst.getString("nd_canPenCnf").equals("")?"0":rst.getString("nd_canPenCnf")));//rst.getBigDecimal("nd_can");
                            //bdeCanDesEntCli=new BigDecimal(rst.getObject("nd_canDesEntCli")==null?"0":(rst.getString("nd_canDesEntCli").equals("")?"0":rst.getString("nd_canDesEntCli")));//rst.getBigDecimal("nd_canDesEntCli");
                            //bdeCanEgrBod=new BigDecimal(rst.getObject("nd_canEgrBod")==null?"0":(rst.getString("nd_canEgrBod").equals("")?"0":rst.getString("nd_canEgrBod")));//rst.getBigDecimal("nd_canDesEntCli");
                            
                            System.out.println("**++ bdeCanIngBod: " + bdeCanIngBod);
                            System.out.println("**++ bdeCanPndCnf: " + bdeCanPndCnf);
                            
                            //se comento
                            if(bdeCanIngBod.compareTo(BigDecimal.ZERO)==0 ){
                                if(bdeCanPndCnf.compareTo(BigDecimal.ZERO)==0 ){
                                    if(!setCanCnfIngGenFac(con, intCodSeg))
                                        blnRes=false;
                                }
                            }
                            //fin 
                        }
                        rst.close();
                        rst=null;
                    }
                    stm.close();
                    stm=null;
                }                
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
     * Función que permite setear los documentos de ingresos que están pendientes de confirmar
     * cambiando la cantidad que esta pendiente de entregar al cliente en despacho a la cantidad confirmada,
     * esto ocurre porque la factura se genera
     * @return true Si obtiene algún registro es porque aún no se debe confirmar
     * <BR> false Caso contrario
     */
    private boolean setCanCnfIngGenFac(Connection con, int codigoSeguimiento){
        boolean blnRes=true;
        int intCodSeg=codigoSeguimiento;
        Statement stmEstSol;
        ResultSet rstEstSol;
        BigDecimal bdeCanCnfDBEgr=BigDecimal.ZERO;
        BigDecimal bdeCanNunRecDBEgr=BigDecimal.ZERO;
        //MANEJA DISPONIBLES JM 
        try{
            if(con!=null){
                stmEstSol=con.createStatement();
                if(intCodSeg!=-1){
                    if(IsCotResVen(con, codigoSeguimiento)){
                            strSQL="";
                            strSQL+=" UPDATE tbm_invBod";
                            strSQL+=" SET nd_canDesEntCli=(tbm_invBod.nd_canDesEntCli - x.nd_canDesEntCliDetMovInv)";
                            //if(IsCotResVenGenFacAutDesFin(con, codigoSeguimiento) ){  /* Solo si es reserva con Despacho al final se deja la mercaderia en cantidad reservada */
                            strSQL+=" , nd_canRes= CASE WHEN nd_canRes IS NULL THEN 0 ELSE nd_canRes END + (x.nd_canDesEntCliDetMovInv)";  // Se lo reserva xD 
                            //} 
                            /* JM: Con una sola confirmacion, no aplicaria hacer esto deberia ponerse reservado hasta que se genere el egreso al cliente 20/Jul/2017 */
                            strSQL+=" FROM(";
                            strSQL+=" 	SELECT c1.co_emp, c1.co_bod, c1.co_itm, SUM(c1.nd_canDesEntCliDetMovInv) AS nd_canDesEntCliDetMovInv";
                            strSQL+=" 	FROM(";
                            strSQL+=" 		SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc";
                            strSQL+=" 		, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                            strSQL+=" 		, a2.nd_canIngBod, a2.nd_canDesEntCliDetMovInv, a2.co_bod, a2.co_itm, a2.nd_canDis, a2.nd_canDesEntCliInvBod";
                            strSQL+=" 		FROM(";
                            strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                            strSQL+=" 			FROM tbm_cabsegmovinv AS a3 INNER JOIN tbm_cabMovInv AS a1";
                            strSQL+=" 			ON a3.co_empRelCabMovInv=a1.co_emp AND a3.co_locRelCabMovInv=a1.co_loc";
                            strSQL+=" 			AND a3.co_tipDocRelCabMovInv=a1.co_tipDoc AND a3.co_docRelCabMovInv=a1.co_doc";
                            strSQL+=" 			INNER JOIN tbm_detMovInv AS a2";
                            strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+=" 			WHERE a3.co_seg=" + intCodSeg + "";
                            strSQL+=" 		) AS a1";
                            strSQL+=" 		INNER JOIN(";
                            strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon";
                            strSQL+=" 			, (a2.nd_can - a2.nd_canCon) AS nd_canPenCnf, a2.nd_canIngBod";
                            strSQL+=" 			, (CASE WHEN a2.nd_canDesEntCli IS NULL THEN 0 ELSE a2.nd_canDesEntCli END) AS nd_canDesEntCliDetMovInv";
                            strSQL+=" 			, a4.co_bod, a2.co_itm, a4.nd_canDis, (CASE WHEN a4.nd_canDesEntCli IS NULL THEN 0 ELSE a4.nd_canDesEntCli END) AS nd_canDesEntCliInvBod";
                            strSQL+=" 			FROM tbm_cabMovInv AS a1";
                            strSQL+=" 			INNER JOIN ((tbm_detMovInv AS a2 INNER JOIN tbm_invBod AS a4 ON a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod AND a2.co_itm=a4.co_itm)";
                            strSQL+="                                   LEFT OUTER JOIN tbr_detMovInv AS a3";
                            strSQL+="                                   ON a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel AND a2.co_tipDoc=a3.co_tipDocRel AND a2.co_doc=a3.co_docRel AND a2.co_reg=a3.co_regRel";
                            strSQL+="                                  )";
                            strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+=" 			WHERE a2.st_meringegrfisbod='S' AND a2.nd_canIngBod=0 AND (a2.nd_can - (CASE WHEN a2.nd_canDesEntCli IS NULL THEN 0 ELSE a2.nd_canDesEntCli END))=0";
                            strSQL+="                       AND (a2.nd_canPen<>0 OR a2.nd_canPen IS NOT NULL) AND a2.nd_can>0";
                            strSQL+=" 			GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canIngBod";
                            strSQL+=" 			, a2.nd_canDesEntCli, a4.co_bod, a2.co_itm, a4.nd_canDis, a4.nd_canDesEntCli";
                            strSQL+=" 		) AS a2";
                            strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+=" 		GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc";
                            strSQL+=" 		, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                            strSQL+=" 		, a2.nd_canIngBod, a2.nd_canDesEntCliDetMovInv, a2.co_bod, a2.co_itm, a2.nd_canDis, a2.nd_canDesEntCliInvBod";
                            strSQL+=" 	) AS c1";
                            strSQL+=" 	GROUP BY c1.co_emp, c1.co_bod, c1.co_itm";
                            strSQL+=" ) AS x";
                            strSQL+=" WHERE tbm_invBod.co_emp=x.co_emp AND tbm_invBod.co_bod=x.co_bod AND tbm_invBod.co_itm=x.co_itm";
                            strSQL+=";";
                            System.out.println("setCanCnfIngGenFac(1) RESERVADOOOOOO!!!!!!  - tbm_invBod: " + strSQL);

                            strSQL+=";";
                            strSQL+=" UPDATE tbm_detMovInv";
                            strSQL+=" SET nd_canDesEntCli=(x.nd_canDesEntCli - x.nd_canDesEntCli)";
                            strSQL+=" , nd_canCon=(x.nd_canCon + x.nd_canDesEntCli)";
                            strSQL+=" FROM(";
                            strSQL+=" 	SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc";
                            strSQL+=" 	, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                            strSQL+=" 	, a2.nd_canIngBod, a2.nd_canDesEntCli";
                            strSQL+=" 	FROM(";
                            strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                            strSQL+=" 		FROM tbm_cabsegmovinv AS a3 INNER JOIN tbm_cabMovInv AS a1";
                            strSQL+=" 		ON a3.co_empRelCabMovInv=a1.co_emp AND a3.co_locRelCabMovInv=a1.co_loc";
                            strSQL+=" 		AND a3.co_tipDocRelCabMovInv=a1.co_tipDoc AND a3.co_docRelCabMovInv=a1.co_doc";
                            strSQL+=" 		INNER JOIN tbm_detMovInv AS a2";
                            strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+=" 		WHERE a3.co_seg=" + intCodSeg + "";
                            strSQL+=" 	) AS a1";
                            strSQL+=" 	INNER JOIN(";
                            strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon";
                            strSQL+=" 		, (a2.nd_can - a2.nd_canCon) AS nd_canPenCnf, a2.nd_canIngBod, (CASE WHEN a2.nd_canDesEntCli IS NULL THEN 0 ELSE a2.nd_canDesEntCli END) AS nd_canDesEntCli";
                            strSQL+=" 		FROM tbm_cabMovInv AS a1";
                            strSQL+=" 		INNER JOIN (tbm_detMovInv AS a2";
                            strSQL+="                           LEFT OUTER JOIN tbr_detMovInv AS a3";
                            strSQL+="                           ON a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel AND a2.co_tipDoc=a3.co_tipDocRel AND a2.co_doc=a3.co_docRel AND a2.co_reg=a3.co_regRel";
                            strSQL+="                           )";
                            strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+=" 		WHERE a2.st_meringegrfisbod='S' AND a2.nd_canIngBod=0 AND (a2.nd_can - (CASE WHEN a2.nd_canDesEntCli IS NULL THEN 0 ELSE a2.nd_canDesEntCli END))=0";
                            strSQL+="               AND (a2.nd_canPen<>0 OR a2.nd_canPen IS NOT NULL) AND a2.nd_can>0";
                            strSQL+=" 	) AS a2";
                            strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+=" 	GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc";
                            strSQL+=" 	, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                            strSQL+=" 	, a2.nd_canIngBod, a2.nd_canDesEntCli";
                            strSQL+=" ) AS x";
                            strSQL+=" WHERE tbm_detMovInv.co_emp=x.co_emp AND tbm_detMovInv.co_loc=x.co_loc AND tbm_detMovInv.co_tipDoc=x.co_tipDoc";
                            strSQL+=" AND tbm_detMovInv.co_doc=x.co_doc AND tbm_detMovInv.co_reg=x.co_reg";
                            System.out.println("setCanCnfIngGenFac(2) strSQL - tbm_detMovInv: " + strSQL);
                            stmEstSol.executeUpdate(strSQL);

                            for(int i=0; i<arlDatCnf.size(); i++){
                                //cantidad pendiente
                                strSQL="";
                                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                                strSQL+=" , CASE WHEN a1.nd_can IS NULL THEN 0 ELSE a1.nd_can END AS nd_can";
                                strSQL+=" , CASE WHEN a1.nd_canCon IS NULL THEN 0 ELSE a1.nd_canCon END AS nd_canCon";
                                strSQL+=" , CASE WHEN a1.nd_canNunRec IS NULL THEN 0 ELSE a1.nd_canNunRec END AS nd_canNunRec";
                                strSQL+=" FROM tbm_detMovInv AS a1";
                                strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_EMP_ORI) + "";
                                strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_LOC_ORI) + "";
                                strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_TIP_DOC_ORI) + "";
                                strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_DOC_ORI) + "";
                                strSQL+=" AND a1.co_reg=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_REG_ORI) + "";
                                strSQL+=";";
                                System.out.println("setCanCnfIngGenFac(3) " + strSQL);
                                rst=stm.executeQuery(strSQL);
                                if(rst.next()){
                                    bdeCanCnfDBEgr=rst.getBigDecimal("nd_canCon");
                                    bdeCanNunRecDBEgr=rst.getBigDecimal("nd_canNunRec");
                                }

                                strSQL="";
                                strSQL+=" UPDATE tbm_detMovInv";
                                strSQL+=" SET ";
                                strSQL+=" nd_canPen=nd_can+" + (bdeCanCnfDBEgr).abs() + "+" + (bdeCanNunRecDBEgr).abs() + "";
                                strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_EMP_ORI) + "";
                                strSQL+=" AND co_loc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_LOC_ORI) + "";
                                strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_TIP_DOC_ORI) + "";
                                strSQL+=" AND co_doc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_DOC_ORI) + "";
                                strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_REG_ORI) + "";
                                strSQL+=";";
                                System.out.println("setCanCnfIngGenFac(4) setCanCnfIngGenFac - nd_canPen: " + strSQL);
                                stm.executeUpdate(strSQL);
                                strSQL="";
                            }
                            if(IsCotResVenGenFacAutDesAnt(con, codigoSeguimiento) ){
                                System.out.println("Reserva con despacho antes de la factura....");
                                System.out.println("SEGUIMIENTO: " + codigoSeguimiento);
                                if(!isDocResDesAntFacDetMovInv(con,codigoSeguimiento)){
                                    return false;
                                }
                            }
                    }
                    else{  /* JM 15/Jun/2017 Cotizacion sin reserva xD   */
                        strSQL="";
                        strSQL+=" UPDATE tbm_invBod";
                        strSQL+=" SET nd_canDesEntCli=(tbm_invBod.nd_canDesEntCli - x.nd_canDesEntCliDetMovInv)";
                        strSQL+=" , nd_canDis=(tbm_invBod.nd_canDis + x.nd_canDesEntCliDetMovInv)";
                        strSQL+=" FROM(";
                        strSQL+=" 	SELECT c1.co_emp, c1.co_bod, c1.co_itm, SUM(c1.nd_canDesEntCliDetMovInv) AS nd_canDesEntCliDetMovInv";
                        strSQL+=" 	FROM(";
                        strSQL+=" 		SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc";
                        strSQL+=" 		, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                        strSQL+=" 		, a2.nd_canIngBod, a2.nd_canDesEntCliDetMovInv, a2.co_bod, a2.co_itm, a2.nd_canDis, a2.nd_canDesEntCliInvBod";
                        strSQL+=" 		FROM(";
                        strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                        strSQL+=" 			FROM tbm_cabsegmovinv AS a3 INNER JOIN tbm_cabMovInv AS a1";
                        strSQL+=" 			ON a3.co_empRelCabMovInv=a1.co_emp AND a3.co_locRelCabMovInv=a1.co_loc";
                        strSQL+=" 			AND a3.co_tipDocRelCabMovInv=a1.co_tipDoc AND a3.co_docRelCabMovInv=a1.co_doc";
                        strSQL+=" 			INNER JOIN tbm_detMovInv AS a2";
                        strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+=" 			WHERE a3.co_seg=" + intCodSeg + "";
                        strSQL+=" 		) AS a1";
                        strSQL+=" 		INNER JOIN(";
                        strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon";
                        strSQL+=" 			, (a2.nd_can - a2.nd_canCon) AS nd_canPenCnf, a2.nd_canIngBod";
                        strSQL+=" 			, (CASE WHEN a2.nd_canDesEntCli IS NULL THEN 0 ELSE a2.nd_canDesEntCli END) AS nd_canDesEntCliDetMovInv";
                        strSQL+=" 			, a4.co_bod, a2.co_itm, a4.nd_canDis, (CASE WHEN a4.nd_canDesEntCli IS NULL THEN 0 ELSE a4.nd_canDesEntCli END) AS nd_canDesEntCliInvBod";
                        strSQL+=" 			FROM tbm_cabMovInv AS a1";
                        strSQL+=" 			INNER JOIN ((tbm_detMovInv AS a2 INNER JOIN tbm_invBod AS a4 ON a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod AND a2.co_itm=a4.co_itm)";
                        strSQL+="                                   LEFT OUTER JOIN tbr_detMovInv AS a3";
                        strSQL+="                                   ON a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel AND a2.co_tipDoc=a3.co_tipDocRel AND a2.co_doc=a3.co_docRel AND a2.co_reg=a3.co_regRel";
                        strSQL+="                                  )";
                        strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+=" 			WHERE a2.st_meringegrfisbod='S' AND a2.nd_canIngBod=0 AND (a2.nd_can - (CASE WHEN a2.nd_canDesEntCli IS NULL THEN 0 ELSE a2.nd_canDesEntCli END))=0";
                        strSQL+="                       AND (a2.nd_canPen<>0 OR a2.nd_canPen IS NOT NULL) AND a2.nd_can>0";
                        strSQL+=" 			GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canIngBod";
                        strSQL+=" 			, a2.nd_canDesEntCli, a4.co_bod, a2.co_itm, a4.nd_canDis, a4.nd_canDesEntCli";
                        strSQL+=" 		) AS a2";
                        strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+=" 		GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc";
                        strSQL+=" 		, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                        strSQL+=" 		, a2.nd_canIngBod, a2.nd_canDesEntCliDetMovInv, a2.co_bod, a2.co_itm, a2.nd_canDis, a2.nd_canDesEntCliInvBod";
                        strSQL+=" 	) AS c1";
                        strSQL+=" 	GROUP BY c1.co_emp, c1.co_bod, c1.co_itm";
                        strSQL+=" ) AS x";
                        strSQL+=" WHERE tbm_invBod.co_emp=x.co_emp AND tbm_invBod.co_bod=x.co_bod AND tbm_invBod.co_itm=x.co_itm";
                        strSQL+=";";
                        System.out.println("setCanCnfIngGenFac(1) strSQL - tbm_invBod: " + strSQL);

                        strSQL+=";";
                        strSQL+=" UPDATE tbm_detMovInv";
                        strSQL+=" SET nd_canDesEntCli=(x.nd_canDesEntCli - x.nd_canDesEntCli)";
                        strSQL+=" , nd_canCon=(x.nd_canCon + x.nd_canDesEntCli)";
                        strSQL+=" FROM(";
                        strSQL+=" 	SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc";
                        strSQL+=" 	, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                        strSQL+=" 	, a2.nd_canIngBod, a2.nd_canDesEntCli";
                        strSQL+=" 	FROM(";
                        strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                        strSQL+=" 		FROM tbm_cabsegmovinv AS a3 INNER JOIN tbm_cabMovInv AS a1";
                        strSQL+=" 		ON a3.co_empRelCabMovInv=a1.co_emp AND a3.co_locRelCabMovInv=a1.co_loc";
                        strSQL+=" 		AND a3.co_tipDocRelCabMovInv=a1.co_tipDoc AND a3.co_docRelCabMovInv=a1.co_doc";
                        strSQL+=" 		INNER JOIN tbm_detMovInv AS a2";
                        strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+=" 		WHERE a3.co_seg=" + intCodSeg + "";
                        strSQL+=" 	) AS a1";
                        strSQL+=" 	INNER JOIN(";
                        strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon";
                        strSQL+=" 		, (a2.nd_can - a2.nd_canCon) AS nd_canPenCnf, a2.nd_canIngBod, (CASE WHEN a2.nd_canDesEntCli IS NULL THEN 0 ELSE a2.nd_canDesEntCli END) AS nd_canDesEntCli";
                        strSQL+=" 		FROM tbm_cabMovInv AS a1";
                        strSQL+=" 		INNER JOIN (tbm_detMovInv AS a2";
                        strSQL+="                           LEFT OUTER JOIN tbr_detMovInv AS a3";
                        strSQL+="                           ON a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel AND a2.co_tipDoc=a3.co_tipDocRel AND a2.co_doc=a3.co_docRel AND a2.co_reg=a3.co_regRel";
                        strSQL+="                           )";
                        strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+=" 		WHERE a2.st_meringegrfisbod='S' AND a2.nd_canIngBod=0 AND (a2.nd_can - (CASE WHEN a2.nd_canDesEntCli IS NULL THEN 0 ELSE a2.nd_canDesEntCli END))=0";
                        strSQL+="               AND (a2.nd_canPen<>0 OR a2.nd_canPen IS NOT NULL) AND a2.nd_can>0";
                        strSQL+=" 	) AS a2";
                        strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+=" 	GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc";
                        strSQL+=" 	, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                        strSQL+=" 	, a2.nd_canIngBod, a2.nd_canDesEntCli";
                        strSQL+=" ) AS x";
                        strSQL+=" WHERE tbm_detMovInv.co_emp=x.co_emp AND tbm_detMovInv.co_loc=x.co_loc AND tbm_detMovInv.co_tipDoc=x.co_tipDoc";
                        strSQL+=" AND tbm_detMovInv.co_doc=x.co_doc AND tbm_detMovInv.co_reg=x.co_reg";
                        System.out.println("setCanCnfIngGenFac(2) strSQL - tbm_detMovInv: " + strSQL);
                        stmEstSol.executeUpdate(strSQL);

                        for(int i=0; i<arlDatCnf.size(); i++){
                            //cantidad pendiente
                            strSQL="";
                            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                            strSQL+=" , CASE WHEN a1.nd_can IS NULL THEN 0 ELSE a1.nd_can END AS nd_can";
                            strSQL+=" , CASE WHEN a1.nd_canCon IS NULL THEN 0 ELSE a1.nd_canCon END AS nd_canCon";
                            strSQL+=" , CASE WHEN a1.nd_canNunRec IS NULL THEN 0 ELSE a1.nd_canNunRec END AS nd_canNunRec";
                            strSQL+=" FROM tbm_detMovInv AS a1";
                            strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_EMP_ORI) + "";
                            strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_LOC_ORI) + "";
                            strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_TIP_DOC_ORI) + "";
                            strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_DOC_ORI) + "";
                            strSQL+=" AND a1.co_reg=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_REG_ORI) + "";
                            strSQL+=";";
                            System.out.println("setCanCnfIngGenFac(3) " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next()){
                                bdeCanCnfDBEgr=rst.getBigDecimal("nd_canCon");
                                bdeCanNunRecDBEgr=rst.getBigDecimal("nd_canNunRec");
                            }

                            strSQL="";
                            strSQL+=" UPDATE tbm_detMovInv";
                            strSQL+=" SET ";
                            strSQL+=" nd_canPen=nd_can+" + (bdeCanCnfDBEgr).abs() + "+" + (bdeCanNunRecDBEgr).abs() + "";
                            strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_EMP_ORI) + "";
                            strSQL+=" AND co_loc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_LOC_ORI) + "";
                            strSQL+=" AND co_tipdoc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_TIP_DOC_ORI) + "";
                            strSQL+=" AND co_doc=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_DOC_ORI) + "";
                            strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatCnf, i, INT_ARL_DAT_COD_REG_ORI) + "";
                            strSQL+=";";
                            System.out.println("setCanCnfIngGenFac(4) setCanCnfIngGenFac - nd_canPen: " + strSQL);
                            stm.executeUpdate(strSQL);
                            strSQL="";
                        }
                    }
                }
                stmEstSol.close();
                stmEstSol=null;
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
    
    private boolean IsCotResVen(java.sql.Connection con, int codigoSeguimiento){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(con!=null){
                stmLoc = con.createStatement();
                strSQL="";
                strSQL+=" SELECT a2.co_seg, a1.co_emp, a1.co_loc, a1.co_cot, a1.st_autSolResInv, a1.st_solResInv \n";
                strSQL+=" FROM tbm_cabCotVen as a1 \n";
                strSQL+=" INNER JOIN tbm_cabSegMovInv as a2 ON (a1.co_emp=a2.co_empRelCabCotVen AND a1.co_loc=a2.co_locRelCabCotVen AND  \n";
                strSQL+="                                       a1.co_cot=a2.co_cotRelCabCotVen)  \n";
                strSQL+=" WHERE a2.co_seg="+codigoSeguimiento+" AND a1.st_autSolResInv='A'  AND a1.st_solResInv IS NOT NULL \n";
                System.out.println("IsCotResVen: \n" + strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
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
    
    private boolean IsCotResVenGenFacAutDesAnt(java.sql.Connection con, int codigoSeguimiento){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(con!=null){
                stmLoc = con.createStatement();
                strSQL="";
                strSQL+=" SELECT a2.co_seg, a1.co_emp, a1.co_loc, a1.co_cot, a1.st_autSolResInv, a1.st_solResInv \n";
                strSQL+=" FROM tbm_cabCotVen as a1 \n";
                strSQL+=" INNER JOIN tbm_cabSegMovInv as a2 ON (a1.co_emp=a2.co_empRelCabCotVen AND a1.co_loc=a2.co_locRelCabCotVen AND  \n";
                strSQL+="                                       a1.co_cot=a2.co_cotRelCabCotVen)  \n";
                strSQL+=" INNER JOIN tbm_tipSolResInv as a3 ON (a1.co_tipSolResInv=a3.co_tipSolResInv) ";
                strSQL+=" WHERE a2.co_seg="+codigoSeguimiento+" AND a1.st_autSolResInv='A'  AND a1.st_solResInv IS NOT NULL AND \n";
                strSQL+="       a3.tx_momDes='A'  ";
                System.out.println("Jota.IsCotResVenGenFacAut: \n" + strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
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
     * Metodo para saber si la confirmacion probiene de una cotizacion, y si la cotizacion esta ligada a una reserva de Mercaderia con
     * despacho previo a la factura, si el caso se da debe generar un documento de egreso de la mercaderia
     * @param conExt
     * @param CodSeg
     * @param CodEmpDocCnf
     * @param CodLocDocCnf
     * @param CodTipDocCnf
     * @param CodDocCnf
     * @return 
     */
    public boolean isDocResDesAntFac(java.sql.Connection conExt, int CodSeg, int CodEmpDocCnf, int CodLocDocCnf, int CodTipDocCnf, int CodDocCnf ){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        ArrayList arlResDat, arlResReg;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_seg, a2.co_emp, a2.co_loc, a2.co_cot \n";
                strSQL+=" FROM tbm_cabSegmovInv as a1  \n";
                strSQL+=" INNER JOIN tbm_cabCotVen as a2 ON (a1.co_empRelCabCotVen=a2.co_emp AND a1.co_locRelCabCotVen=a2.co_loc AND  \n";
                strSQL+="                                    a1.co_cotRelCabCotVen=a2.co_cot)  \n";
                strSQL+=" INNER JOIN tbm_tipSolResInv as a3 ON (a2.co_tipSolResInv=a3.co_tipSolResinv)  \n";
                strSQL+=" WHERE a1.co_seg="+CodSeg+" AND a3.tx_momDes='A' AND  \n";
                strSQL+="       a2.st_solResInv IS NOT NULL AND a2.st_autSolResInv IS NOT NULL \n";
                System.out.println("Jota.isDocResDesAntFac Viene de una cotizacion? (1): \n" + strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    arlResDat = new ArrayList();
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp,a1.co_loc,a1.co_tipDoc,a1.co_doc,a2.co_reg,a2.co_itm,a2.co_bod, a2.nd_can, a3.nd_cosUni, \n";
                    strSQL+="        a5.co_emp as co_empCot, a5.co_loc as co_locCot, a5.co_cot,a3.co_reg as co_regCot,a3.tx_nomItm   \n";
                    strSQL+=" FROM tbm_cabIngEgrMerBod as a1 \n";
                    strSQL+=" INNER JOIN tbm_detIngEgrMerBod as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND  \n";
                    strSQL+="                                          a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
                    strSQL+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                    strSQL+=" INNER JOIN tbm_cabSegMovInv as a4 ON (a1.co_emp=a4.co_empRelCabIngEgrMerBod AND a1.co_loc=a4.co_locRelCabIngEgrMerBod AND  \n";
                    strSQL+="                                       a1.co_tipDoc=a4.co_tipDocRelCabIngEgrMerBod AND a1.co_doc=a4.co_docRelCabIngEgrMerBod) \n";
                    strSQL+=" INNER JOIN ( \n";
                    strSQL+="               SELECT a2.co_seg,a1.co_emp,a1.co_loc,a1.co_cot,a3.co_reg,a3.tx_nomItm \n";
                    strSQL+="               FROM tbm_cabCotVen as a1  \n";
                    strSQL+="               INNER JOIN tbm_cabSegMovInv as a2 ON (a1.co_emp=a2.co_empRelCabCotVen AND a1.co_loc=a2.co_locRelCabCotVen AND  \n";
                    strSQL+="                                                       a1.co_cot=a2.co_cotRelCabCotVen ) \n";
                    strSQL+="               INNER JOIN tbm_detCotVen as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_cot=a3.co_cot) ";
                    strSQL+="               WHERE a2.co_empRelCabCotVen IS NOT NULL AND a2.co_seg="+CodSeg+" \n";
                    strSQL+=" ) as a5 ON (a4.co_seg=a5.co_seg) \n";
                    strSQL+=" WHERE a1.co_emp="+CodEmpDocCnf+" and a1.co_loc="+CodLocDocCnf+" and a1.co_tipDoc="+CodTipDocCnf+" and a1.co_doc="+CodDocCnf+" \n";
                    strSQL+="       AND a3.st_ser='N' \n";
                    strSQL+=" ORDER BY a1.co_emp,a1.co_loc,a1.co_tipDoc,a1.co_doc,a2.co_reg \n";
                    System.out.println("ZafCnfDoc.isDocResDesAntFac Obtiene datos ingreso y local para generar documento (2): \n" + strSQL);
                    rstLoc = stmLoc.executeQuery(strSQL);
                    while(rstLoc.next()){
                        arlResReg = new ArrayList();
                        arlResReg.add(INT_ARL_DAT_RES_COD_EMP, rstLoc.getInt("co_empCot"));
                        arlResReg.add(INT_ARL_DAT_RES_COD_LOC, rstLoc.getInt("co_locCot"));
                        arlResReg.add(INT_ARL_DAT_RES_COD_BOD_EMP, rstLoc.getInt("co_bod"));
                        arlResReg.add(INT_ARL_DAT_RES_COD_ITM_EMP, rstLoc.getInt("co_itm"));
                        arlResReg.add(INT_ARL_DAT_RES_CAN, rstLoc.getDouble("nd_can"));
                        arlResReg.add(INT_ARL_DAT_RES_COS_UNI, rstLoc.getDouble("nd_cosUni"));
                        arlResReg.add(INT_ARL_DAT_RES_COD_SEG, CodSeg);
                        arlResReg.add(INT_ARL_DAT_COD_REG, rstLoc.getInt("co_regCot"));
                        arlResReg.add(INT_ARL_DAT_NOM_ITM, rstLoc.getString("tx_nomItm"));
                        arlResDat.add(arlResReg);
                    }
                    if(!arlResDat.isEmpty()){
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        ZafGenDocIngEgrInvRes objGenDocIngEgrInvRes = new ZafGenDocIngEgrInvRes(objParSis,cmpPad);
                        if(objGenDocIngEgrInvRes.GenerarDocumentoIngresoEgresoReservas(conExt, datFecAux, arlResDat, 1)){
                            blnRes=true;
                        }
                    }
                }else{
                    /* No es el caso */
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
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
     * Genera egreso desde un movimiento de detalle de inventario
     * @param conExt
     * @param CodSeg
     * @param CodEmpDoc
     * @param CodLocDoc
     * @param CodTipDoc
     * @param CodDoc
     * @return 
     */
    private boolean isDocResDesAntFacDetMovInv(java.sql.Connection conExt, int CodSeg ){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        ArrayList arlResDat, arlResReg;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_seg, a2.co_emp, a2.co_loc, a2.co_cot \n";
                strSQL+=" FROM tbm_cabSegmovInv as a1  \n";
                strSQL+=" INNER JOIN tbm_cabCotVen as a2 ON (a1.co_empRelCabCotVen=a2.co_emp AND a1.co_locRelCabCotVen=a2.co_loc AND  \n";
                strSQL+="                                    a1.co_cotRelCabCotVen=a2.co_cot)  \n";
                strSQL+=" INNER JOIN tbm_tipSolResInv as a3 ON (a2.co_tipSolResInv=a3.co_tipSolResinv)  \n";
                strSQL+=" WHERE a1.co_seg="+CodSeg+" AND a3.tx_momDes='A' AND  \n";
                strSQL+="       a2.st_solResInv IS NOT NULL AND a2.st_autSolResInv IS NOT NULL \n";
                System.out.println("Jota.isDocResDesAntFac Viene de una cotizacion? (1): \n" + strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    arlResDat = new ArrayList();
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp,a1.co_loc, a1.co_cot,a2.co_reg,a2.co_itm,a2.co_bod, a2.nd_can, a3.nd_cosUni, \n";
                    strSQL+="        a5.co_emp as co_empCot, a5.co_loc as co_locCot, a5.co_cot ,a2.tx_nomItm  \n";
                    strSQL+=" FROM tbm_cabCotVen as a1 \n";
                    strSQL+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND  \n";
                    strSQL+="                                            a1.co_cot=a2.co_cot) \n";
                    strSQL+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                    strSQL+=" INNER JOIN tbm_cabSegMovInv as a4 ON (a1.co_emp=a4.co_empRelCabCotVen AND a1.co_loc=a4.co_locRelCabCotVen AND  \n";
                    strSQL+="                                        a1.co_cot=a4.co_cotRelCabCotVen) \n";
                    strSQL+=" INNER JOIN ( \n";
                    strSQL+="               SELECT a2.co_seg,a1.co_emp,a1.co_loc,a1.co_cot \n";
                    strSQL+="               FROM tbm_cabCotVen as a1  \n";
                    strSQL+="               INNER JOIN tbm_cabSegMovInv as a2 ON (a1.co_emp=a2.co_empRelCabCotVen AND a1.co_loc=a2.co_locRelCabCotVen AND  \n";
                    strSQL+="                                                       a1.co_cot=a2.co_cotRelCabCotVen ) \n";
                    strSQL+="               WHERE a2.co_empRelCabCotVen IS NOT NULL AND a2.co_seg="+CodSeg+" \n";
                    strSQL+=" ) as a5 ON (a4.co_seg=a5.co_seg) \n";
                    strSQL+=" WHERE  a3.st_ser='N' \n";
                    strSQL+="         \n";
                    strSQL+=" ORDER BY a1.co_emp,a1.co_loc, a1.co_cot,a2.co_reg \n";
                    System.out.println("Jota.isDocResDesAntFac 0.2 Obtiene datos ingreso y local para generar documento (2): \n" + strSQL);
                    rstLoc = stmLoc.executeQuery(strSQL);
                    while(rstLoc.next()){
                        arlResReg = new ArrayList();
                        arlResReg.add(INT_ARL_DAT_RES_COD_EMP, rstLoc.getInt("co_empCot"));
                        arlResReg.add(INT_ARL_DAT_RES_COD_LOC, rstLoc.getInt("co_locCot"));
                        arlResReg.add(INT_ARL_DAT_RES_COD_BOD_EMP, rstLoc.getInt("co_bod"));
                        arlResReg.add(INT_ARL_DAT_RES_COD_ITM_EMP, rstLoc.getInt("co_itm"));
                        arlResReg.add(INT_ARL_DAT_RES_CAN, rstLoc.getDouble("nd_can"));
                        arlResReg.add(INT_ARL_DAT_RES_COS_UNI, rstLoc.getDouble("nd_cosUni"));
                        arlResReg.add(INT_ARL_DAT_RES_COD_SEG, CodSeg);
                        arlResReg.add(INT_ARL_DAT_COD_REG, rstLoc.getInt("co_reg"));
                        arlResReg.add(INT_ARL_DAT_NOM_ITM, rstLoc.getString("tx_nomItm"));
                        arlResDat.add(arlResReg);
                    }
                    if(!arlResDat.isEmpty()){
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        ZafGenDocIngEgrInvRes objGenDocIngEgrInvRes = new ZafGenDocIngEgrInvRes(objParSis,cmpPad);
                        if(objGenDocIngEgrInvRes.GenerarDocumentoIngresoEgresoReservas(conExt, datFecAux, arlResDat, 1)){
                            blnRes=true;
                        }
                    }
                }else{
                    /* No es el caso */
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
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
     * Segun la solicitud 
     * obtiene todos los ingresos de cada item, se lo hizo para la cotizacion
     * 
     * @param con
     * @param CodEmp Datos de la solicitud de transferencia
     * @param CodLoc
     * @param CodTipDoc
     * @param CodDoc
     * @return 
     */
    public ArrayList<String> obtenerCantidadRemota(java.sql.Connection con, int CodEmp, int CodLoc,int CodTipDoc, int CodDoc){
        intCodEmp=CodEmp;
        intCodLoc=CodLoc;
        intCodTipDoc=CodTipDoc;
        intCodDoc=CodDoc;
        java.sql.Statement stmLoc; 
        java.sql.ResultSet rstLoc;
        try{
            if(con!=null){
                arlDatIng = new ArrayList();
                stmLoc=con.createStatement();
                strSQL="";/* LOS INGRESOS AGRUPADOS POR ITEM */
                strSQL+=" SELECT  a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv,   \n";
                strSQL+="        a1.co_docRelCabMovInv,a3.co_reg,a5.co_reg as coRegRel,a3.co_itm, a3.nd_can \n";
                strSQL+=" FROM tbm_cabSegMovInv as a1 \n";
                strSQL+=" INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND   \n";
                strSQL+="                                    a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc)  /*EL INGRESO */ \n";
                strSQL+=" INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
                strSQL+=" RIGHT JOIN tbr_detMovInv as a5 ON (a2.co_emp=a5.co_empRel AND a2.co_loc=a5.co_locRel AND a2.co_tipDoc=a5.co_tipDocRel AND a2.co_doc=a5.co_docRel AND a3.co_reg=a5.co_regRel) \n";
                strSQL+=" INNER JOIN tbm_detMovInv as a6 ON (a5.co_emp=a6.co_emp AND a5.co_loc=a6.co_loc AND a5.co_tipDoc=a6.co_tipDoc AND \n";
                strSQL+="                                    a5.co_doc=a6.co_doc AND a5.co_reg=a6.co_reg)/*LA FACTURA*/ \n";
                strSQL+=" WHERE a1.co_seg=(select co_seg from tbm_cabSegMovInv  \n";
                strSQL+="                    where co_empRelCabSolTraInv="+intCodEmp+" and co_locRelCabSolTraInv="+intCodLoc+" AND \n";
                strSQL+="                          co_tipDocRelCabSolTraInv="+intCodTipDoc+" and co_docRelCabSolTraInv="+intCodDoc+" ) and a3.nd_can > 0\n";
                strSQL+=" GROUP BY a1.co_empRelCabMovInv, a1.co_locRelCabMovInv,  a1.co_tipDocRelCabMovInv,a1.co_docRelCabMovInv,a3.co_reg,a5.co_reg, a3.co_itm, a3.nd_can \n";
                strSQL+=" ORDER BY a5.co_reg \n"; // IMPORTANTE ORDENADO POR LOS REGISTROS DE LA FACTURA 
                rstLoc=stmLoc.executeQuery(strSQL);
                while(rstLoc.next()){
                    arlRegIng=new ArrayList();
                    arlRegIng.add(INT_ARL_COD_EMP,rstLoc.getInt("co_empRelCabMovInv")); // (DATOS DEL INGRESO)
                    arlRegIng.add(INT_ARL_COD_LOC,rstLoc.getInt("co_locRelCabMovInv"));
                    arlRegIng.add(INT_ARL_COD_TIP_DOC,rstLoc.getInt("co_tipDocRelCabMovInv"));
                    arlRegIng.add(INT_ARL_COD_DOC,rstLoc.getInt("co_docRelCabMovInv"));
                    arlRegIng.add(INT_ARL_COD_REG,rstLoc.getInt("co_reg"));
                    arlRegIng.add(INT_ARL_COD_REG_REL,rstLoc.getInt("coRegRel")); // RELACIONAL (LA FACTURA)
                    arlRegIng.add(INT_ARL_COD_ITM,rstLoc.getInt("co_itm"));
                    arlRegIng.add(INT_ARL_CAN_ITM,rstLoc.getDouble("nd_can"));
                    arlDatIng.add(arlRegIng);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){         
            System.err.println("ERROR: " + e);
        }
        catch (Exception e){         
            System.err.println("ERROR: " + e);
        }
        return arlDatIng;
    }
    
    /* PASO 2: Obtener la cotizacion  */
    
    
    
    /**
     * Función que permite obtener el código del seguimiento asociado a la solicitud
     * @return true Si existe seguimiento asociado a la solicitud
     * <BR> false Caso contrario
     */
    /*
    private boolean getCodSegGenFacAut(Connection con
                                        , int codigoEmpresaSolicitud, int codigoLocalSolicitud, int codigoTipoDocumentoSolicitud, int codigoDocumentoSolicitud){
        boolean blnRes=false;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_seg FROM tbm_cabSegMovInv AS a1 ";
                strSQL+=" WHERE a1.co_emprelcabsoltrainv=" + codigoEmpresaSolicitud + "";
                strSQL+=" AND a1.co_locrelcabsoltrainv=" + codigoLocalSolicitud + "";
                strSQL+=" AND a1.co_tipdocrelcabsoltrainv=" + codigoTipoDocumentoSolicitud + "";
                strSQL+=" AND a1.co_docrelcabsoltrainv=" + codigoDocumentoSolicitud + "";
                strSQL+=" GROUP BY a1.co_seg";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodSegGenFacAut=rst.getInt("co_seg");
                    blnRes=true;
                }
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e) {
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
         
    }
    */
	
  /**Verificacion para generacion de Ordenes de Despacho
     * Función que permite obtener los documentos de ingresos que están pendientes de confirmar
     * @return true Si obtiene algún registro es porque aún se debe confirmar
     * <BR> false Caso contrario
     */
    public boolean isDocIngPenCnfxFac(Connection con
                                    , int codigoEmpresaFactura, int codigoLocalFactura, int codigoTipoDocumentoFactura, int codigoDocumentoFactura
                                    , String tipoOperacion){
        boolean blnRes=true;
        boolean blnIgBodDesOri=false;
        int intCodSeg=-1;
        BigDecimal bdeCanIngBod=BigDecimal.ZERO;
        BigDecimal bdeCan=BigDecimal.ZERO;
        BigDecimal bdeCanDesEntCli=BigDecimal.ZERO;
        String strSQL="";
        ResultSet rst2=null;
        ResultSet rst=null;
        try{
            if(con!=null){
                if(tipoOperacion.equals("I")){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+=" SELECT a1.co_seg FROM tbm_cabSegMovInv AS a1 ";
                    strSQL+=" WHERE a1.co_emprelcabmovinv=" + codigoEmpresaFactura + "";
                    strSQL+=" AND a1.co_locrelcabmovinv=" + codigoLocalFactura + "";
                    strSQL+=" AND a1.co_tipdocrelcabmovinv=" + codigoTipoDocumentoFactura + "";
                    strSQL+=" AND a1.co_docrelcabmovinv=" + codigoDocumentoFactura + "";
                    strSQL+=" GROUP BY a1.co_seg";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        intCodSeg=rst.getInt("co_seg");
                        /*VERIFICACION PARA CASOS DE INMACONSA A INMACONSA */                        
                        strSQL="  select sol.co_bodorg, sol.co_boddes";
                        strSQL+=" from tbm_Cabsegmovinv as seg";
                        strSQL+=" inner join tbm_cabsoltrainv as sol";
                        strSQL+=" on (seg.co_emprelcabsoltrainv=sol.co_emp";
                        strSQL+=" and seg.co_locrelcabsoltrainv=sol.co_loc";
                        strSQL+=" and seg.co_tipdocrelcabsoltrainv=sol.co_tipdoc";
                        strSQL+=" and seg.co_docrelcabsoltrainv=sol.co_doc)";
                        strSQL+=" where co_seg="+intCodSeg;
                        strSQL+=" and seg.co_emprelcabsoltrainv is not null ";
                        strSQL+=" and seg.co_locrelcabsoltrainv is not null ";
                        strSQL+=" and seg.co_tipdocrelcabsoltrainv is not null ";
                        strSQL+=" and seg.co_docrelcabsoltrainv is not null ";   
                        rst2=stm.executeQuery(strSQL);
                        while(rst2.next() && !blnIgBodDesOri){
                            if(rst2.getInt("co_bodorg")!=rst2.getInt("co_boddes")){
                                blnIgBodDesOri=true;
                                break;
                            }
                        }
                        rst2.close();
                        /*VERIFICACION PARA CASOS DE INMACONSA A INMACONSA */
                        
                        if(blnIgBodDesOri){
                            strSQL="";
                            strSQL+=" SELECT SUM(b1.nd_canIngBod) AS nd_canIngBod, SUM(b1.nd_can) AS nd_can, SUM(b1.nd_canDesEntCli) AS nd_canDesEntCli";
                            strSQL+=" FROM(";
                            strSQL+="    SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                            strSQL+="    , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                            strSQL+="    , a2.nd_canIngBod, a2.nd_canDesEntCli";
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
                            strSQL+="            , (a2.nd_can - a2.nd_canCon) AS nd_canPenCnf, a2.nd_canIngBod, CASE WHEN a2.nd_canDesEntCli IS NULL THEN 0 ELSE a2.nd_canDesEntCli END AS nd_canDesEntCli";
                            strSQL+="            FROM tbm_cabMovInv AS a1";
                            strSQL+="            INNER JOIN (tbm_detMovInv AS a2";
                            strSQL+="                       LEFT OUTER JOIN tbr_detMovInv AS a3";
                            strSQL+="                       ON a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel AND a2.co_tipDoc=a3.co_tipDocRel AND a2.co_doc=a3.co_docRel AND a2.co_reg=a3.co_regRel";
                            strSQL+="                       )";
                            strSQL+="            ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+="            WHERE a2.st_meringegrfisbod='S' and (a2.nd_can - a2.nd_canCon)> 0";
                            strSQL+="            AND (a2.nd_canPen<>0 OR a2.nd_canPen IS NOT NULL) AND a2.nd_can>0";
                            strSQL+=" ) AS a2";
                            strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                            strSQL+=" , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                            strSQL+=" , a2.nd_canIngBod, a2.nd_canDesEntCli";
                            strSQL+=" ) AS b1 having  SUM(b1.nd_canIngBod) > 0 ";
                            System.out.println("isDocIngPenCnf: " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next()){
                                blnRes=true;
                            }else{
                                blnRes=false;
                            }
                            rst.close();
                            rst=null;
                        }else{
                            blnRes=false;
                        }
                    }else{
                        blnRes=false;
                    }
                    stm.close();
                    stm=null;
                }                
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
     * Función que permite calcular la cantidad pendiente
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    public boolean setCanPendiente(Connection con, int codigoSeguimiento){
        boolean blnRes=true;
        String strSQLUpd="";
        try{
            if(con!=null){
                stm=con.createStatement();
                
                strSQL="";
                strSQL+=" SELECT a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv";
                strSQL+=" FROM tbm_cabSegMovInv AS a1";
                strSQL+=" WHERE a1.co_seg=" + codigoSeguimiento + "";
                strSQL+=" AND a1.co_empRelCabMovInv IS NOT NULL";
                strSQL+=" ORDER BY a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    strSQL="";
                    strSQL+=" UPDATE tbm_detMovInv";
                    strSQL+=" SET nd_canPen=x.nd_canPenCal FROM(";
                    strSQL+="     SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg";
                    strSQL+="     , b1.nd_can, b1.nd_canCon,  b1.nd_canNunRec, b1.nd_canPen";
                    strSQL+="     , (b1.nd_can - b1.nd_canCon - b1.nd_canNunRec) AS nd_canPenCal";
                    strSQL+="     FROM(";
                    strSQL+="             SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                    strSQL+="             , CASE WHEN a1.nd_can IS NULL THEN 0 ELSE a1.nd_can END AS nd_can";
                    strSQL+="             , CASE WHEN a1.nd_canCon IS NULL THEN 0 ELSE a1.nd_canCon END AS nd_canCon";
                    strSQL+="             , CASE WHEN a1.nd_canNunRec IS NULL THEN 0 ELSE a1.nd_canNunRec END AS nd_canNunRec";
                    strSQL+="             , CASE WHEN a1.nd_canPen IS NULL THEN 0 ELSE a1.nd_canPen END AS nd_canPen";
                    strSQL+="             FROM tbm_detMovInv AS a1";
                    strSQL+="             WHERE a1.co_emp=" + rst.getString("co_empRelCabMovInv") + "";
                    strSQL+="             AND a1.co_loc=" + rst.getString("co_locRelCabMovInv") + "";
                    strSQL+="             AND a1.co_tipDoc=" + rst.getString("co_tipDocRelCabMovInv") + "";
                    strSQL+="             AND a1.co_doc=" + rst.getString("co_docRelCabMovInv") + "";
                    strSQL+="             AND a1.nd_canPen IS NOT NULL";
                    strSQL+="     ) AS b1";
                    strSQL+=" ) AS x";
                    strSQL+=" WHERE tbm_detMovInv.co_emp=x.co_emp AND tbm_detMovInv.co_loc=x.co_loc AND tbm_detMovInv.co_tipDoc=x.co_tipDoc";
                    strSQL+=" AND tbm_detMovInv.co_doc=x.co_doc AND tbm_detMovInv.co_reg=x.co_reg";
                    strSQL+=";";
                    strSQLUpd+=strSQL;
                }
                System.out.println("setCanPendiente - UPDATE: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
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
    
    private boolean setEstParTraInv(Connection con, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento){
        boolean blnRes=true;
        Statement stmEstSol;
        try{
            if(con!=null){
//                if(arlDatTrsEgrIng.size()>0){
                    stmEstSol=con.createStatement();
                    strSQL="";
                    strSQL+=" UPDATE tbm_cabsoltrainv";
                    strSQL+=" SET st_conInv='E'";
                    strSQL+=" FROM (SELECT co_emprelcabsoltrainv,co_locrelcabsoltrainv,co_tipdocrelcabsoltrainv,co_docrelcabsoltrainv ";
                    strSQL+=" FROM tbm_cabmovinv ";
                    strSQL+=" WHERE co_emp=" + codigoEmpresa + "";
                    strSQL+=" AND co_loc=" + codigoLocal + "";
                    strSQL+=" AND co_tipDoc=" + codigoTipoDocumento + "";
                    strSQL+=" AND co_doc=" + codigoDocumento + ") mov";
                    strSQL+=" WHERE mov.co_emprelcabsoltrainv=co_emp" ;
                    strSQL+="       and mov.co_locrelcabsoltrainv=co_loc ";
                    strSQL+="     and mov.co_tipdocrelcabsoltrainv=co_tipdoc ";
                    strSQL+="     and mov.co_docrelcabsoltrainv=co_doc ";
                    strSQL+=" AND (st_conInv IS NULL OR st_conInv IN('P'))";
                    strSQL+=";";
                    System.out.println("setEstParTraInv: " + strSQL);
                    stmEstSol.executeUpdate(strSQL);
                    stmEstSol.close();
                    stmEstSol=null;
//                }
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
     * Función que permite saber si el documento origen está confirmado parcial o total
     * @param con
     * @param codigoEmpresaSolicitud
     * @param codigoLocalSolicitud
     * @param codigoTipoDocumentoSolicitud
     * @param codigoDocumentoSolicitud
     * @param tipoOperacion I: confirmación de ingreso   E: confirmación de egreso
     * @return registros si tiene confirmaciones aplicadas
     */
    public boolean isCnfParTotDocEgr(Connection con
                                    , int codigoEmpresaOrigen, int codigoLocalOrigen, int codigoTipoDocumentoOrigen, int codigoDocumentoOrigen
                                    , String tipoOperacion){
        boolean blnRes=true;
        try{
            if(con!=null){
                System.out.println("tipoOperacion: " + tipoOperacion);
                stm=con.createStatement();
                    
                //--EGRESO
//                if((tipoOperacion.equals("E")){
                    //--
                
                //confirmacion parcial de documentos que tienen la bodega la mercadería
                strSQL="";
                strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.nd_can, b1.nd_canCon FROM(";
                strSQL+=" 	SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, SUM(ABS(b1.nd_can)) AS nd_can, SUM(ABS(b1.nd_canCon)) AS nd_canCon";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon";
                strSQL+=" 		FROM tbm_cabMovInv AS a1";
                strSQL+=" 		INNER JOIN tbm_detMovInv AS a2";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                //strSQL+=" 		INNER JOIN tbr_detMovInv AS a3";
                //strSQL+=" 		ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
                strSQL+=" 		WHERE a2.st_meringegrfisbod='S'";
                strSQL+=" 		AND (a1.st_conInv IS NULL OR a1.st_conInv='P')";
                strSQL+="           AND a1.co_emp=" + codigoEmpresaOrigen + "";
                strSQL+="           AND a1.co_loc=" + codigoLocalOrigen + "";
                strSQL+="           AND a1.co_tipDoc=" + codigoTipoDocumentoOrigen + "";
                strSQL+="           AND a1.co_doc=" + codigoDocumentoOrigen + "";
                strSQL+=" 	) AS b1";
                strSQL+=" 	GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                strSQL+=" ) AS b1";
                strSQL+=" WHERE (  (b1.nd_canCon>0)  AND (b1.nd_can>b1.nd_canCon) AND (b1.nd_can<>b1.nd_canCon)  ) ";
                strSQL+=";";
//                }
                System.out.println("isCnfParDocEgr-MercEmpresa: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    strSQL="";
                    strSQL+=" UPDATE tbm_cabMovInv";
                    strSQL+=" SET st_conInv='E'";
                    strSQL+=" WHERE co_emp=" + codigoEmpresaOrigen + "";
                    strSQL+=" AND co_loc=" + codigoLocalOrigen + "";
                    strSQL+=" AND co_tipDoc=" + codigoTipoDocumentoOrigen + "";
                    strSQL+=" AND co_doc=" + codigoDocumentoOrigen + "";
                    strSQL+=";";
                    stm.executeUpdate(strSQL);
                    System.out.println("setEstConfirmEgresoParcial-MercEmpresa: " + strSQL);
                }
                //Estado de confirmacion total de documentos que tienen la bodega la mercadería
                strSQL="";
                strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.nd_can, b1.nd_canCon FROM(";
                strSQL+=" 	SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, SUM(ABS(b1.nd_can)) AS nd_can, SUM(ABS(b1.nd_canCon)) AS nd_canCon";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon";
                strSQL+=" 		FROM tbm_cabMovInv AS a1";
                strSQL+=" 		INNER JOIN tbm_detMovInv AS a2";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                //strSQL+=" 		INNER JOIN tbr_detMovInv AS a3";
                //strSQL+=" 		ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
                strSQL+=" 		WHERE a2.st_meringegrfisbod='S'";
                strSQL+=" 		AND (a1.st_conInv IS NULL OR a1.st_conInv IN('P','E'))";
                strSQL+="           AND a1.co_emp=" + codigoEmpresaOrigen + "";
                strSQL+="           AND a1.co_loc=" + codigoLocalOrigen + "";
                strSQL+="           AND a1.co_tipDoc=" + codigoTipoDocumentoOrigen + "";
                strSQL+="           AND a1.co_doc=" + codigoDocumentoOrigen + "";
                strSQL+=" 	) AS b1";
                strSQL+=" 	GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                strSQL+=" ) AS b1";
                strSQL+=" WHERE (  (b1.nd_canCon>0) AND (b1.nd_can=b1.nd_canCon)  ) ";
                strSQL+=";";
                System.out.println("isCnfTotDocEgr-MercEmpresa: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    strSQL="";
                    strSQL+=" UPDATE tbm_cabMovInv";
                    strSQL+=" SET st_conInv='C'";
                    strSQL+=" WHERE co_emp=" + codigoEmpresaOrigen + "";
                    strSQL+=" AND co_loc=" + codigoLocalOrigen + "";
                    strSQL+=" AND co_tipDoc=" + codigoTipoDocumentoOrigen + "";
                    strSQL+=" AND co_doc=" + codigoDocumentoOrigen + "";
                    strSQL+=";";
                    stm.executeUpdate(strSQL);
                    System.out.println("setEstConfirmEgresoTotal-MercEmpresa: " + strSQL);
                }
                
                //confirmacion parcial de documentos que no poseen la bodega la mercadería
                strSQL="";
                strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.nd_can, b1.nd_canCon FROM(";
                strSQL+=" 	SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, SUM(ABS(b1.nd_can)) AS nd_can, SUM(ABS(b1.nd_canTra)) AS nd_canCon";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can";
                strSQL+=" 		, CASE WHEN a2.nd_canTra IS NULL THEN 0 ELSE a2.nd_canTra END AS nd_canTra";
                strSQL+=" 		FROM tbm_cabMovInv AS a1";
                strSQL+=" 		INNER JOIN tbm_detMovInv AS a2";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                //strSQL+=" 		INNER JOIN tbr_detMovInv AS a3";
                //strSQL+=" 		ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
                strSQL+=" 		WHERE a2.st_meringegrfisbod='S' ";
                strSQL+=" 		AND (a1.st_conInv IS NULL OR a1.st_conInv='P')";
                strSQL+="               AND a1.co_emp=" + codigoEmpresaOrigen + "";
                strSQL+="               AND a1.co_loc=" + codigoLocalOrigen + "";
                strSQL+="               AND a1.co_tipDoc=" + codigoTipoDocumentoOrigen + "";
                strSQL+="               AND a1.co_doc=" + codigoDocumentoOrigen + "";
                strSQL+=" 	) AS b1";
                strSQL+=" 	GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                strSQL+=" ) AS b1";
                strSQL+=" WHERE (  (b1.nd_canCon>0)  AND (b1.nd_can>b1.nd_canCon) AND (b1.nd_can<>b1.nd_canCon)  )";
                strSQL+=";";
                System.out.println("isCnfParDocEgr-MercEmpresa: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    strSQL="";
                    strSQL+=" UPDATE tbm_cabMovInv";
                    strSQL+=" SET st_conInv='E'";
                    strSQL+=" WHERE co_emp=" + codigoEmpresaOrigen + "";
                    strSQL+=" AND co_loc=" + codigoLocalOrigen + "";
                    strSQL+=" AND co_tipDoc=" + codigoTipoDocumentoOrigen + "";
                    strSQL+=" AND co_doc=" + codigoDocumentoOrigen + "";
                    strSQL+=";";
                    stm.executeUpdate(strSQL);
                    System.out.println("setEstConfirmEgresoParcial-MercOtraEmpresa: " + strSQL);
                }

                //confirmacion total de documentos que no poseen la bodega la mercadería
                strSQL="";
                strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.nd_can, b1.nd_canCon FROM(";
                strSQL+=" 	SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, SUM(ABS(b1.nd_can)) AS nd_can, SUM(ABS(b1.nd_canTra)) AS nd_canCon";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can";
                strSQL+=" 		, CASE WHEN a2.nd_canTra IS NULL THEN 0 ELSE a2.nd_canTra END AS nd_canTra";
                strSQL+=" 		FROM tbm_cabMovInv AS a1";
                strSQL+=" 		INNER JOIN tbm_detMovInv AS a2";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                //strSQL+=" 		INNER JOIN tbr_detMovInv AS a3";
                //strSQL+=" 		ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
                strSQL+=" 		WHERE a2.st_meringegrfisbod='S' ";
                strSQL+=" 		AND (a1.st_conInv IS NULL OR a1.st_conInv IN('P','E'))";
                strSQL+="               AND a1.co_emp=" + codigoEmpresaOrigen + "";
                strSQL+="               AND a1.co_loc=" + codigoLocalOrigen + "";
                strSQL+="               AND a1.co_tipDoc=" + codigoTipoDocumentoOrigen + "";
                strSQL+="               AND a1.co_doc=" + codigoDocumentoOrigen + "";
                strSQL+=" 	) AS b1";
                strSQL+=" 	GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                strSQL+=" ) AS b1";
                strSQL+=" WHERE (  (b1.nd_canCon>0) AND (b1.nd_can=b1.nd_canCon)  )";
                strSQL+=";";
                System.out.println("isCnfTotDocEgr-MercEmpresa: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    strSQL="";
                    strSQL+=" UPDATE tbm_cabMovInv";
                    strSQL+=" SET st_conInv='C'";
                    strSQL+=" WHERE co_emp=" + codigoEmpresaOrigen + "";
                    strSQL+=" AND co_loc=" + codigoLocalOrigen + "";
                    strSQL+=" AND co_tipDoc=" + codigoTipoDocumentoOrigen + "";
                    strSQL+=" AND co_doc=" + codigoDocumentoOrigen + "";
                    strSQL+=";";
                    stm.executeUpdate(strSQL);
                    System.out.println("setEstConfirmEgresoTotal-MercOtraEmpresa: " + strSQL);
                }
                rst.close();
                rst=null;                
                stm.close();
                stm=null;
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
     * Función que permite obtener la dirección IP del servicio de impresión de OD y guías de remisión.
     * @param conexion
     * @return 
     */
    public String getIpImpOrdGui(Connection conexion){
        String strDirIp="";
        try{
            if(conexion!=null){
                 //Configuración del servidor.
                objCfgSer = new ZafCfgSer(objParSis);
                objCfgSer.cargaDatosIpHostServicios(objParSis.getCodigoEmpresaGrupo(), objCfgSer.INT_TBL_COD_SER_IMP_OD_GR);
                strDirIp=objCfgSer.getIpHost();  
            }
        }
        catch(Exception e) {
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return strDirIp;
        
    }
    
    /**
     * Función que permite obtener la dirección IP del servicio de contigencia de guías de remisión.
     * @param conexion
     * @return 
     */
    public String getIpConGuiRem(Connection conexion){
        String strDirIp="";
        try{
            if(conexion!=null){
                 //Configuración del servidor.
                objCfgSer = new ZafCfgSer(objParSis);
                objCfgSer.cargaDatosIpHostServicios(objParSis.getCodigoEmpresaGrupo(), objCfgSer.INT_TBL_COD_SER_CON_GR);
                strDirIp=objCfgSer.getIpHost(); 
            }
        }
        catch(Exception e) {
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return strDirIp;
    }
       
    /**
     * Función que permite obtener la dirección IP del servicio de modo Offline de guías de remisión.
     * @param conexion
     * @return 
     */
    public String getIpModOffLinGuiRem(Connection conexion){
        String strDirIp="";
        try{
            if(conexion!=null){
                 //Configuración del servidor.
                objCfgSer = new ZafCfgSer(objParSis);
                objCfgSer.cargaDatosIpHostServicios(objParSis.getCodigoEmpresaGrupo(), objCfgSer.INT_TBL_COD_SER_OFF_LIN_GR);
                strDirIp=objCfgSer.getIpHost();  
            }
        }
        catch(Exception e) {
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return strDirIp;
    }
    
    /**
     * Función que permite obtener el puerto del servicio de contigencia de guías de remisión.
     * @param conexion
     * @return 
     */
    public int getPueSerConGuiRem(Connection conexion){
        int intPueSer=0;
        try{
            if(conexion!=null){
                 //Configuración del servidor.
                objCfgSer = new ZafCfgSer(objParSis);
                objCfgSer.cargaDatosIpHostServicios(objParSis.getCodigoEmpresaGrupo(), objCfgSer.INT_TBL_COD_SER_CON_GR);
                intPueSer=objCfgSer.getPueSer();  
            }
        }
        catch(Exception e) {
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return intPueSer;
    }
       
    /**
     * Función que permite obtener el puerto del servicio de modo Offline de guías de remisión.
     * @param conexion
     * @return 
     */
    public int getPueSerModOffLinGuiRem(Connection conexion){
        int intPueSer=0;
        try{
            if(conexion!=null){
                 //Configuración del servidor.
                objCfgSer = new ZafCfgSer(objParSis);
                objCfgSer.cargaDatosIpHostServicios(objParSis.getCodigoEmpresaGrupo(), objCfgSer.INT_TBL_COD_SER_OFF_LIN_GR);
                intPueSer=objCfgSer.getPueSer(); 
            }
        }
        catch(Exception e) {
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return intPueSer;
    }
    
    
    /**
     * Función que permite imprimir la Orden de Despacho al momento de realizar confirmación de ingreso
     * @param conexion Conexion a la base
     * @param codigoSeguimiento Código de seguimiento
     * @return true 
     */
    public boolean imprimirOrdDes(Connection conexion, int codigoSeguimiento){
        boolean blnRes=true;
        int intCodSegRef=codigoSeguimiento;
        String strPryKeyFac="";
        String [] strArrPryKeyFac=new String[4];
        try{
            if(conexion!=null){
                if(intCodSegRef!=-1){
                    strPryKeyFac=objGenFacAut.obtenerFacturaSeguimientoInventario(conexion, intCodSegRef);
                    if(strPryKeyFac.trim().length()>0){
                        strArrPryKeyFac=strPryKeyFac.split("-");
                        objGenOdPryTra.imprimirOdLocal(conexion, Integer.parseInt(strArrPryKeyFac[0]), Integer.parseInt(strArrPryKeyFac[1]), Integer.parseInt(strArrPryKeyFac[2]), Integer.parseInt(strArrPryKeyFac[3]), getIpImpOrdGui(conexion));
                        System.out.println("imprimirOrdDes"); 
                    }
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;        
    }
    
    /**
     * Función que permite imprimir la Orden de Despacho por egresos de reservas.
     * @param conexion Conexion a la base
     * @param codigoSeguimiento Código de seguimiento
     * @return true 
     */
    public boolean imprimirEgresoReservas(java.sql.Connection conexion, int codigoSeguimiento){
        boolean blnRes=true;
        try{
            if(conexion!=null){
                if(objResInv.SeguimientoVieneDeReservas(conexion, codigoSeguimiento)){
                    objGenOdPryTra.imprimirOdxRes(conexion, codigoSeguimiento, getIpImpOrdGui(conexion));
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
}