/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafMovIngEgrInv;

import GenOD.ZafGenOdPryTra;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafCfgBod.ZafCfgBod;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import Librerias.ZafCnfDoc.ZafCnfDoc; 
import java.sql.DriverManager;
import java.util.Vector;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
/**
 *
 * @author sistemas3
 */
public class ZafMovIngEgrInv {
    
    //ingreso y egreso
    public int intCodEmpEgrAux, intCodLocEgrAux, intCodTipDocEgrAux, intCodDocEgrAux;  // JM: Para poder costear los dos documentos 2017/12/27
    public int intCodEmpIngAux, intCodLocIngAux, intCodTipDocIngAux, intCodDocIngAux;  // JM: Para poder costear los dos documentos 2017/12/27
    public int intCodEmpTraAux, intCodLocTraAux, intCodTipDocTraAux, intCodDocTraAux;
    
    private int intCodEmp;//Código de empresa
    private int intCodLoc;//Código de local
    private int intCodTipDoc;//Código de tipo de documento
    private int intCodDoc;//Código de documento    
    private int intNumDoc;
    private int intCodCliPrv;
    private String strSQL;    
    private BigDecimal bgdValDocSub;//subtotal
    private BigDecimal bgdValDocIva;//iva
    private BigDecimal bgdValDocTot;//total
    private BigDecimal bgdValPorIva;//porcentaje de iva
    private Date dtpFecDoc;
    private char chrGenIva;
    
    private ArrayList arlDatItm;
    private static final int INT_ARL_COD_EMP=0;
    private static final int INT_ARL_COD_LOC=1;
    private static final int INT_ARL_COD_TIP_DOC=2;
    private static final int INT_ARL_COD_CLI_PRV=3;
    private static final int INT_ARL_COD_BOD_ORI_DES=4;
    private static final int INT_ARL_COD_ITM_GRP=5;
    private static final int INT_ARL_COD_ITM_EMP=6;
    private static final int INT_ARL_COD_ITM_MAE=7;    
    private static final int INT_ARL_COD_ALT_ITM=8;
    private static final int INT_ARL_NOM_ITM=9;
    private static final int INT_ARL_UNI_MED_ITM=10;
    private static final int INT_ARL_COD_LET_ITM=11;    
    private static final int INT_ARL_CAN_ITM=12;
    private static final int INT_ARL_COS_UNI=13;//recibe el costo unitario del item tbm_inv.nd_cosUni
    private static final int INT_ARL_PRE_UNI=14;
    private static final int INT_ARL_PES_UNI=15;
    private static final int INT_ARL_IVA_COM_ITM=16;
    private static final int INT_ARL_EST_ING_EGR_MER_BOD=17;
    
    private Statement stm;
    private ResultSet rst;
    
    private ZafUtil objUti;
    private ZafParSis objParSis;
    private ZafAsiDia objAsiDia;
    
    private BigDecimal bdeSig;
    private int intNumSecEmp;
    private int intNumSecGrp;
    
    private int intNumDec;//variable global que tendrá el número de decimales a usar en todo el programa.    
    
    private int intCodForPag;
    private int intNumPag;
    
    //para datos de pago            CFP = CABECERA DE FORMA DE PAGO
    private ArrayList arlRegCabForPag, arlDatCabForPag;
    private int INT_ARL_CFP_COD_SRI=0;
    private int INT_ARL_CFP_COD_TIP_RET=1;
    private int INT_ARL_CFP_DES_COR_TIP_RET=2;
    private int INT_ARL_CFP_DES_LAR_TIP_RET=3;
    private int INT_ARL_CFP_POR_RET=4;
    private int INT_ARL_CFP_APL_RET=5;
    private int INT_ARL_CFP_COD_CTA=6;
    
    //Detalle de forma de pago      DFP = DETALLE DE FORMA DE PAGO
    
    //a1.co_emp, a1.co_forPag, a1.co_reg, a1.ne_diaCre, a1.st_sop
    private ArrayList arlRegDetForPag, arlDatDetForPag;
    private int INT_ARL_DFP_COD_EMP=0;
    private int INT_ARL_DFP_COD_PAG=1;
    private int INT_ARL_DFP_COD_REG=2;
    private int INT_ARL_DFP_DIA_CRE=3;
    private int INT_ARL_DFP_EST_SOP=4;
    
    //private int intTipMov;// 0= un solo movimiento. Ejm: Egreso  ......  1= un documento pero en el detalle cantidad por negativo y por positivo. Ejm: Transferencias
    
    
    private ArrayList arlRegTbrDetMovInvEgr, arlDatTbrDetMovInvEgr;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_EMP=0;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_LOC=1;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_TIP_DOC=2;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_DOC=3;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_REG=4;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_EMP_REL=5;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_LOC_REL=6;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_TIP_DOC_REL=7;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_DOC_REL=8;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_REG_REL=9;
    
    //solicitud
    private int intCodEmpSol;//Código de empresa de solicitud
    private int intCodLocSol;//Código de local de solicitud
    private int intCodTipDocSol;//Código de tipo de documento de solicitud
    private int intCodDocSol;//Código de documento de solicitud
    
    private ZafSegMovInv objSegMovInv;
    private String strTipMovSeg;
    private Object objEstDocGenOrdDes;
    
    private ArrayList arlDatCnfNumRecEnt, arlRegCnfNumRecEnt;//Arreglo recibido como parametro
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
    
    private java.awt.Component cmpPad;
    private ZafCnfDoc objCnfDoc;
    
    private Vector vecRegDia, vecDatDia;
    final int INT_VEC_DIA_LIN=0;
    final int INT_VEC_DIA_COD_CTA=1;
    final int INT_VEC_DIA_NUM_CTA=2;
    final int INT_VEC_DIA_BUT_CTA=3;
    final int INT_VEC_DIA_NOM_CTA=4;
    final int INT_VEC_DIA_DEB=5;
    final int INT_VEC_DIA_HAB=6;
    final int INT_VEC_DIA_REF=7;
    final int INT_VEC_DIA_EST_CON=8;
    
    private int intCodEmpEgrGenAsiDia, intCodEmpIngGenAsiDia;
    
    private int intCodBodEgr, intCodBodIng_EmpGenEgr;
    private int intCodBodIng, intCodBodEgr_EmpGenIng;
    
    private BigDecimal bdeMarUtiComVenRel;
    
    private ZafCfgBod objCfgBod;
    private boolean blnConIng;
    private boolean blnConEgr;
    private String strEgrGen;
    private String strIngGen;
    
    private javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet;
    private JasperPrint jprRepGuiRem;
    private javax.print.attribute.standard.PrinterName jprNamPriNam;
    private javax.print.attribute.PrintServiceAttributeSet jprSerAttSet;
    private net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExpEgr, objJRPSerExpIng;
    private java.util.Date datFecAux;
    private java.util.Map mapPar;
    private String strTipRep;
    
    private String strFecHorSer, strRutRpt, strTitRpt, strNomRpt, strSQLRep, strSQLSubRep;
    private int INT_COD_RPT_ORD_ALM=460; //Orden de Almacenamiento (Nuevo)
    
    public String strVer="v0.06";
    /**
     * Función que permite obtener el objeto ZafParSis
     * @param obj El objeto ZafParSis
     */
    public ZafMovIngEgrInv(ZafParSis obj, java.awt.Component parent){
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            cmpPad=parent;
            objAsiDia = new ZafAsiDia(objParSis);
            intNumDec=objParSis.getDecimalesBaseDatos();
            arlDatCabForPag=new ArrayList();
            arlDatDetForPag=new ArrayList();
            arlDatTbrDetMovInvEgr=new ArrayList();
            bgdValPorIva=objParSis.getPorcentajeIvaVentas().divide(new BigDecimal("100"),objParSis.getDecimalesMostrar(),BigDecimal.ROUND_HALF_UP)  ;
//            intTipMov=0;// 0= un solo movimiento. Ejm: Egreso  ......  1= un documento pero en el detalle cantidad por negativo y por positivo. Ejm: Transferencias
            objSegMovInv=new ZafSegMovInv(objParSis, cmpPad);
            arlDatCnfNumRecEnt=new ArrayList();
            objCnfDoc=new ZafCnfDoc(objParSis, cmpPad);
            vecDatDia=new Vector();
            
            blnConIng=false;
            blnConEgr=false;
            strEgrGen="";
            strIngGen="";
            
            System.out.println(strVer);
        }
        catch (CloneNotSupportedException e){
            System.out.println("ZafMovInv: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
    }   

    
    /**
     * Función que permite obtener datos necesarios para generar el documento
     * @param con                       Conexión enviada
     * @param dtpFechaDocumento         <HTML>Fecha de documento con la que se guardará el documento. <BR>Formato: dd/MM/yyyy</HTML>
     * @param chrGenerarIva             Estado que permitirá saber si hay que calcular valor de iva. Según tipo de documento. Por Préstamos ='N', por Facturación/Compra ='S'
     * @param arlDatosItemEgreso        <HTML>Información del item:
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
     *                                  </HTML>
     * @param arlDatosItemIngreso       <HTML>Información del item:
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
     *                                      <BR>Cantidad del item en positivo
     *                                      <BR>Costo unitario del item
     *                                      <BR>Precio unitario del item
     *                                      <BR>Peso del item
     *                                      <BR>Estado del item para generar o no el IVA
     *                                  </HTML>
     * @param tipMov                    <HTML>Tipo de movimiento del documento
     *                                      <BR>I : 
     *                                      <BR>E : 
     *                                      <BR>R : 
     *                                      <BR>V : 
     *                                  </HTML>
     * @return 
     */
    public boolean getDatoEgresoIngreso(Connection con, Date dtpFechaDocumento, char chrGenerarIva
            , ArrayList arlDatosItemEgreso, ArrayList arlDatosItemIngreso
            , int codEmpSol, int codLocSol, int codTipDocSol, int codDocSol
            , String tipMovSeg, Object estGenOrdDes, ZafCfgBod objetoConfigBodega)
    
    {
        int intCodEmpEgr=0, intCodLocEgr=0, intCodTipDocEgr=0, intCodDocEgr=0;
        boolean blnRes=false;
        objEstDocGenOrdDes=estGenOrdDes;
        ZafGenOdPryTra objGenOrdDes=new ZafGenOdPryTra();
        
        intCodBodEgr=-1;//nombre de bodega de egreso
        intCodBodIng=-1;//nombre de bodega de ingreso
        
        
        intCodBodIng_EmpGenEgr=-1;
        intCodBodEgr_EmpGenIng=-1;
        
        try{
            if(con!=null){
                arlDatTbrDetMovInvEgr.clear();
//                intTipMov=0;
                dtpFecDoc=dtpFechaDocumento;
                chrGenIva=chrGenerarIva;
                System.out.println("chrGenIva: " + chrGenIva);
                
                //solicitud
                intCodEmpSol=codEmpSol;
                intCodLocSol=codLocSol;
                intCodTipDocSol=codTipDocSol;
                intCodDocSol=codDocSol;
                strTipMovSeg=tipMovSeg;
                
                stm=con.createStatement();
                
                if(arlDatosItemEgreso.size()>0){//Egreso
                    intCodEmpEgrGenAsiDia=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_EMP);
                    intCodBodEgr=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_BOD_ORI_DES);
                    
                }
                
                if(arlDatosItemIngreso.size()>0){//Ingreso
                    intCodEmpIngGenAsiDia=objUti.getIntValueAt(arlDatosItemIngreso, 0, INT_ARL_COD_EMP);
                    intCodBodIng=objUti.getIntValueAt(arlDatosItemIngreso, 0, INT_ARL_COD_BOD_ORI_DES);
                }
                                
                intCodBodIng_EmpGenEgr=getCodBodDes_EmpPtoPar(intCodEmpIngGenAsiDia, intCodBodIng, intCodEmpEgrGenAsiDia);
                intCodBodEgr_EmpGenIng=getCodBodDes_EmpPtoPar(intCodEmpEgrGenAsiDia, intCodBodEgr, intCodEmpIngGenAsiDia);
                
                
                objCfgBod=objetoConfigBodega;
                System.out.println("getDatoEgresoIngreso - objCfgBod:" + objCfgBod);
                
                
                if(arlDatosItemEgreso.size()>0){
                    intCodEmp=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_EMP);
                    intCodLoc=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_LOC);
                    intCodTipDoc=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_TIP_DOC);
                    intCodCliPrv=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_CLI_PRV);
                    //intCodBodRegUno=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_BOD);
                    //System.out.println("ZafMovInv.getDatoEgresoIngreso EGRESO: " + intCodBodRegUno);
                    arlDatItm=arlDatosItemEgreso;

                    intCodEmpEgr=intCodEmp;
                    
                    //código
                    strSQL="";
                    strSQL+="SELECT CASE WHEN MAX(a1.co_doc) IS NULL THEN 0 ELSE  MAX(a1.co_doc) END AS co_doc FROM tbm_cabMovInv AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intCodDoc=(Integer.parseInt(rst.getObject("co_doc")==null?"":(rst.getString("co_doc").equals("")?"":rst.getString("co_doc")))) + 1;
                    else
                        return false;
                    intCodEmpEgrAux=intCodEmp; intCodLocEgrAux=intCodLoc; intCodTipDocEgrAux=intCodTipDoc; intCodDocEgrAux=intCodDoc;
                    System.out.println("EGRESO: "+intCodEmpEgrAux+","+intCodLocEgrAux+","+intCodTipDocEgrAux+","+intCodDocEgrAux);
                    //Egreso para armar la relacion con el INBOPR
                    for(int h=0; h<arlDatosItemEgreso.size(); h++){
                        arlRegTbrDetMovInvEgr=new ArrayList();
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_EMP, intCodEmp);
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_LOC, intCodLoc);
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_TIP_DOC, intCodTipDoc);
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_DOC, intCodDoc);
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_REG, (h+1));
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_EMP_REL, "");
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_LOC_REL, "");
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_TIP_DOC_REL, "");
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_DOC_REL, "");
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_REG_REL, "");
                        arlDatTbrDetMovInvEgr.add(arlRegTbrDetMovInvEgr);
                    }                    
                    
                    //número
                    strSQL="";
                    strSQL+="SELECT a1.ne_ultDoc FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intNumDoc=(Integer.parseInt(rst.getObject("ne_ultDoc")==null?"":(rst.getString("ne_ultDoc").equals("")?"":rst.getString("ne_ultDoc")))) + 1;
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
                                        
                    //--Forma de pago
                    strSQL="";
                    strSQL+="SELECT a8.co_forPag, a8.tx_des AS tx_nomForPag, a8.ne_numPag";
                    strSQL+=" FROM tbm_cabForPag AS a8 LEFT OUTER JOIN tbm_forPagCli AS a7";
                    strSQL+=" ON a7.co_emp=a8.co_emp AND a7.co_forPag=a8.co_forPag AND a7.st_reg='S'";
                    strSQL+=" LEFT OUTER JOIN tbm_cli AS a6";
                    strSQL+=" ON a6.co_emp=a7.co_emp AND a6.co_cli=a7.co_cli AND a7.st_reg='S'";
                    strSQL+=" WHERE a8.co_emp=" + intCodEmp + " AND a6.st_reg NOT IN('E','I') AND a6.co_cli=" + intCodCliPrv + "";
                    strSQL+=" GROUP BY a8.co_forPag, a8.tx_des, a8.ne_numPag";
                    strSQL+=" ORDER BY a8.co_forPag, a8.tx_des, a8.ne_numPag";
                    System.out.println("Forma de pago: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        intNumPag=rst.getInt("ne_numPag");
                        intCodForPag=rst.getInt("co_forPag");
                    }
                    else
                        return false;
                    
                    //--Retenciones
                    if(chrGenIva=='S'){//no debe generar retenciones por iva, por tanto: solo aplica a SUBTOTAL
                        //strSQL+=" AND tx_aplRet='S'";
                        strSQL="";
                        strSQL+="SELECT a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet";
                        strSQL+=" , a9.tx_desCor AS tx_desCorRet, a9.tx_desLar AS tx_desLarRet, a9.nd_porRet, a9.tx_aplRet, a9.co_cta";
                        strSQL+=" FROM tbm_motDoc AS a1 LEFT OUTER JOIN (  (tbm_polRet AS a2 LEFT OUTER JOIN tbm_tipPer AS a4";
                        strSQL+=" ON a2.co_emp=a4.co_emp AND a2.co_ageRet=a4.co_tipper";
                        strSQL+=" LEFT OUTER JOIN tbm_emp AS a5";
                        strSQL+=" ON a4.co_emp=a5.co_emp AND a4.co_tipPer=a5.co_tipPer)";
                        strSQL+=" LEFT OUTER JOIN tbm_tipPer AS a3";
                        strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_sujret=a3.co_tipper";
                        strSQL+=" LEFT OUTER JOIN tbm_cli AS a6";
                        strSQL+=" ON a3.co_emp=a6.co_emp AND a3.co_tipPer=a6.co_tipPer";
                        strSQL+=" ) ON a1.co_emp=a2.co_emp AND a1.co_mot=a2.co_motTra";
                        strSQL+=" LEFT OUTER JOIN tbm_cabTipRet AS a9 ON a2.co_emp=a9.co_emp AND a2.co_tipRet=a9.co_tipRet";
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp + " AND CURRENT_DATE BETWEEN a2.fe_vigDes AND (CASE WHEN a2.fe_vigHas IS NULL THEN CURRENT_DATE ELSE a2.fe_vigHas END)";
                        strSQL+=" AND a1.st_reg NOT IN('E','I') AND a2.st_reg NOT IN('E','I')";
                        strSQL+=" AND a6.st_reg NOT IN('E','I') AND a5.st_reg NOT IN('E','I')";
                        strSQL+=" AND a1.co_mot=1";//quemado: para bienes
                        strSQL+=" AND a6.co_cli=" + intCodCliPrv + "";
                        strSQL+=" GROUP BY a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet , a9.tx_desCor, a9.tx_desLar";
                        strSQL+=" , a9.nd_porRet, a9.tx_aplRet, a9.co_cta";
                        strSQL+=" ORDER BY a2.co_tipRet, a1.co_mot, a1.tx_desCor, a2.tx_codSri";
                        System.out.println("Retenciones-egreso: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        arlDatCabForPag.clear();
                        while(rst.next()){
                            arlRegCabForPag=new ArrayList();
//                            arlRegCabForPag.add(INT_ARL_CFP_COD_CAB_FOR_PAG, rst.getInt("co_forPag"));
//                            arlRegCabForPag.add(INT_ARL_CFP_NOM_CAB_FOR_PAG, rst.getString("tx_nomForPag"));
//                            arlRegCabForPag.add(INT_ARL_CFP_NUM_PAG, rst.getInt("ne_numPag"));
                            arlRegCabForPag.add(INT_ARL_CFP_COD_SRI, rst.getString("tx_codSri"));
                            arlRegCabForPag.add(INT_ARL_CFP_COD_TIP_RET, rst.getInt("co_tipRet"));
                            arlRegCabForPag.add(INT_ARL_CFP_DES_COR_TIP_RET, rst.getString("tx_desCorRet"));
                            arlRegCabForPag.add(INT_ARL_CFP_DES_LAR_TIP_RET, rst.getString("tx_desLarRet"));
                            arlRegCabForPag.add(INT_ARL_CFP_POR_RET, (rst.getBigDecimal("nd_porRet").divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP))  );
                            arlRegCabForPag.add(INT_ARL_CFP_APL_RET, rst.getString("tx_aplRet"));
                            arlRegCabForPag.add(INT_ARL_CFP_COD_CTA, rst.getInt("co_cta"));
                            arlDatCabForPag.add(arlRegCabForPag);
                        }
                    }


                    //datos de forma de pago de detalle
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_forPag, a1.co_reg, a1.ne_diaCre, a1.st_sop";
                    strSQL+=" FROM tbm_detForPag AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_forPag=" + intCodForPag + "";
                    strSQL+=" ORDER BY a1.co_emp, a1.co_forPag, a1.co_reg";
                    rst=stm.executeQuery(strSQL);
                    arlDatDetForPag.clear();
                    while(rst.next()){
                        arlRegDetForPag=new ArrayList();
                        arlRegDetForPag.add(INT_ARL_DFP_COD_EMP, rst.getString("co_emp"));
                        arlRegDetForPag.add(INT_ARL_DFP_COD_PAG, rst.getString("co_forPag"));
                        arlRegDetForPag.add(INT_ARL_DFP_COD_REG, rst.getString("co_reg"));
                        arlRegDetForPag.add(INT_ARL_DFP_DIA_CRE, rst.getString("ne_diaCre"));
                        arlRegDetForPag.add(INT_ARL_DFP_EST_SOP, rst.getString("st_sop"));
                        arlDatDetForPag.add(arlRegDetForPag);
                    }
                    
                    
                                        
                    if(generarSQLDocumentoEgreso(con)){
                        intCodEmpEgr=intCodEmp; intCodLocEgr=intCodLoc; intCodTipDocEgr=intCodTipDoc; intCodDocEgr=intCodDoc;
                        //INGRESO
                        arlDatItm=arlDatosItemIngreso;
                        if(arlDatItm.size()>0){
                            intCodEmp=objUti.getIntValueAt(arlDatItm, 0, INT_ARL_COD_EMP);
                            intCodLoc=objUti.getIntValueAt(arlDatItm, 0, INT_ARL_COD_LOC);
                            intCodTipDoc=objUti.getIntValueAt(arlDatItm, 0, INT_ARL_COD_TIP_DOC);
                            intCodCliPrv=objUti.getIntValueAt(arlDatItm, 0, INT_ARL_COD_CLI_PRV);
//                            intCodBodRegUno=objUti.getIntValueAt(arlDatItm, 0, INT_ARL_COD_BOD);
//                            System.out.println("ZafMovInv.getDatoEgresoIngreso BODEGA INGRESO " + intCodBodRegUno);
                            
                            
                            
                            //código
                            strSQL="";
                            strSQL+="SELECT CASE WHEN MAX(a1.co_doc) IS NULL THEN 0 ELSE MAX(a1.co_doc) END AS co_doc FROM tbm_cabMovInv AS a1";
                            strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                            strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                            strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                            System.out.println("codigo " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next())
                                intCodDoc=(Integer.parseInt(rst.getObject("co_doc")==null?"":(rst.getString("co_doc").equals("")?"":rst.getString("co_doc")))) + 1;
                            else
                                return false;
                            intCodEmpIngAux=intCodEmp; intCodLocIngAux=intCodLoc; intCodTipDocIngAux=intCodTipDoc; intCodDocIngAux=intCodDoc;
                            System.out.println("INGRESO: "+intCodEmpIngAux+","+intCodLocIngAux+","+intCodTipDocIngAux+","+intCodDocIngAux);
                            //Ingreso para armar la relacion con el EGBOPR
                            for(int h=0; h<arlDatosItemIngreso.size(); h++){
                                objUti.setStringValueAt(arlDatTbrDetMovInvEgr, h, INT_ARL_TBR_DET_MOV_INV_EGR_COD_EMP_REL,""+intCodEmp);
                                objUti.setStringValueAt(arlDatTbrDetMovInvEgr, h, INT_ARL_TBR_DET_MOV_INV_EGR_COD_LOC_REL,""+ intCodLoc);
                                objUti.setStringValueAt(arlDatTbrDetMovInvEgr, h, INT_ARL_TBR_DET_MOV_INV_EGR_COD_TIP_DOC_REL,""+ intCodTipDoc);
                                objUti.setStringValueAt(arlDatTbrDetMovInvEgr, h, INT_ARL_TBR_DET_MOV_INV_EGR_COD_DOC_REL,""+ intCodDoc);
                                objUti.setStringValueAt(arlDatTbrDetMovInvEgr, h, INT_ARL_TBR_DET_MOV_INV_EGR_COD_REG_REL,""+ (h+1));
                            }
                                                                                    
                            //número
                            strSQL="";
                            strSQL+="SELECT a1.ne_ultDoc FROM tbm_cabTipDoc AS a1";
                            strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                            strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                            strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                            System.out.println("número + " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next())
                                intNumDoc=(Integer.parseInt(rst.getObject("ne_ultDoc")==null?"":(rst.getString("ne_ultDoc").equals("")?"":rst.getString("ne_ultDoc")))) + 1;
                            else
                                return false;

                            //signo para generar el documento
                            strSQL="";
                            strSQL+="SELECT a1.tx_natdoc FROM tbm_cabTipDoc AS a1";
                            strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                            strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                            strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                            System.out.println("número + " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next())
                                bdeSig=new BigDecimal(rst.getString("tx_natDoc").equals("I")?"1":"-1");
                            else
                                return false;

                            //para obtener secuencia de empresa
                            strSQL="";
                            strSQL+="SELECT CASE WHEN ne_secUltDocTbmCabMovInv IS NULL THEN 0 ELSE (ne_secUltDocTbmCabMovInv+1) END AS ne_numSecEmp";
                            strSQL+=" FROM tbm_emp WHERE co_emp=" + intCodEmp + "";
                            System.out.println("para obtener secuencia de empresa " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next())
                                intNumSecEmp=rst.getInt("ne_numSecEmp");

                            //para obtener secuencia de grupo
                            strSQL="";
                            strSQL+="SELECT CASE WHEN ne_secUltDocTbmCabMovInv IS NULL THEN 0 ELSE (ne_secUltDocTbmCabMovInv+1) END AS ne_numSecGrp";
                            strSQL+=" FROM tbm_emp WHERE co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                            System.out.println("para obtener secuencia de grupo " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next())
                                intNumSecGrp=rst.getInt("ne_numSecGrp");
                            
                            //--Forma de pago
                            strSQL="";
                            strSQL+="SELECT a8.co_forPag, a8.tx_des AS tx_nomForPag, a8.ne_numPag";
                            strSQL+=" FROM tbm_cabForPag AS a8 LEFT OUTER JOIN tbm_forPagCli AS a7";
                            strSQL+=" ON a7.co_emp=a8.co_emp AND a7.co_forPag=a8.co_forPag AND a7.st_reg='S'";
                            strSQL+=" LEFT OUTER JOIN tbm_cli AS a6";
                            strSQL+=" ON a6.co_emp=a7.co_emp AND a6.co_cli=a7.co_cli AND a7.st_reg='S'";
                            strSQL+=" WHERE a8.co_emp=" + intCodEmp + " AND a6.st_reg NOT IN('E','I') AND a6.co_cli=" + intCodCliPrv + "";
                            strSQL+=" GROUP BY a8.co_forPag, a8.tx_des, a8.ne_numPag";
                            strSQL+=" ORDER BY a8.co_forPag, a8.tx_des, a8.ne_numPag";
                            System.out.println("Forma de pago: " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next()){
                                intNumPag=rst.getInt("ne_numPag");
                                intCodForPag=rst.getInt("co_forPag");
                            }
                            else
                                return false;

                            //--Retenciones
                            if(chrGenIva=='S'){//no debe generar retenciones por iva, por tanto: solo aplica a SUBTOTAL
                                //strSQL+=" AND tx_aplRet='S'";
                                strSQL="";
                                strSQL+="SELECT a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet";
                                strSQL+=" , a9.tx_desCor AS tx_desCorRet, a9.tx_desLar AS tx_desLarRet, a9.nd_porRet, a9.tx_aplRet, a9.co_cta";
                                strSQL+=" FROM tbm_motDoc AS a1 LEFT OUTER JOIN (  (tbm_polRet AS a2 LEFT OUTER JOIN tbm_tipPer AS a4";
                                strSQL+=" ON a2.co_emp=a4.co_emp AND a2.co_ageRet=a4.co_tipper";
                                strSQL+=" LEFT OUTER JOIN tbm_emp AS a5";
                                strSQL+=" ON a4.co_emp=a5.co_emp AND a4.co_tipPer=a5.co_tipPer)";
                                strSQL+=" LEFT OUTER JOIN tbm_tipPer AS a3";
                                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_sujret=a3.co_tipper";
                                strSQL+=" LEFT OUTER JOIN tbm_cli AS a6";
                                strSQL+=" ON a3.co_emp=a6.co_emp AND a3.co_tipPer=a6.co_tipPer";
                                strSQL+=" ) ON a1.co_emp=a2.co_emp AND a1.co_mot=a2.co_motTra";
                                strSQL+=" LEFT OUTER JOIN tbm_cabTipRet AS a9 ON a2.co_emp=a9.co_emp AND a2.co_tipRet=a9.co_tipRet";
                                strSQL+=" WHERE a1.co_emp=" + intCodEmp + " AND CURRENT_DATE BETWEEN a2.fe_vigDes AND (CASE WHEN a2.fe_vigHas IS NULL THEN CURRENT_DATE ELSE a2.fe_vigHas END)";
                                strSQL+=" AND a1.st_reg NOT IN('E','I') AND a2.st_reg NOT IN('E','I')";
                                strSQL+=" AND a6.st_reg NOT IN('E','I') AND a5.st_reg NOT IN('E','I')";
                                strSQL+=" AND a1.co_mot=1";//quemado: para bienes
                                strSQL+=" AND a6.co_cli=" + intCodCliPrv + "";
                                strSQL+=" GROUP BY a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet , a9.tx_desCor, a9.tx_desLar";
                                strSQL+=" , a9.nd_porRet, a9.tx_aplRet, a9.co_cta";
                                strSQL+=" ORDER BY a2.co_tipRet, a1.co_mot, a1.tx_desCor, a2.tx_codSri";
                                System.out.println("Retenciones-ingreso: " + strSQL);
                                rst=stm.executeQuery(strSQL);
                                arlDatCabForPag.clear();
                                while(rst.next()){
                                    arlRegCabForPag=new ArrayList();
//                                    arlRegCabForPag.add(INT_ARL_CFP_COD_CAB_FOR_PAG, rst.getInt("co_forPag"));
//                                    arlRegCabForPag.add(INT_ARL_CFP_NOM_CAB_FOR_PAG, rst.getString("tx_nomForPag"));
//                                    arlRegCabForPag.add(INT_ARL_CFP_NUM_PAG, rst.getInt("ne_numPag"));
                                    arlRegCabForPag.add(INT_ARL_CFP_COD_SRI, rst.getString("tx_codSri"));
                                    arlRegCabForPag.add(INT_ARL_CFP_COD_TIP_RET, rst.getInt("co_tipRet"));
                                    arlRegCabForPag.add(INT_ARL_CFP_DES_COR_TIP_RET, rst.getString("tx_desCorRet"));
                                    arlRegCabForPag.add(INT_ARL_CFP_DES_LAR_TIP_RET, rst.getString("tx_desLarRet"));
                                    arlRegCabForPag.add(INT_ARL_CFP_POR_RET, (rst.getBigDecimal("nd_porRet").divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP))  );
                                    arlRegCabForPag.add(INT_ARL_CFP_APL_RET, rst.getString("tx_aplRet"));
                                    arlRegCabForPag.add(INT_ARL_CFP_COD_CTA, rst.getInt("co_cta"));
                                    arlDatCabForPag.add(arlRegCabForPag);
                                }
                            }


                            //datos de forma de pago de detalle
                            strSQL="";
                            strSQL+="SELECT a1.co_emp, a1.co_forPag, a1.co_reg, a1.ne_diaCre, a1.st_sop";
                            strSQL+=" FROM tbm_detForPag AS a1";
                            strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                            strSQL+=" AND a1.co_forPag=" + intCodForPag + "";
                            strSQL+=" ORDER BY a1.co_emp, a1.co_forPag, a1.co_reg";
                            System.out.println("datos de forma de pago de detalle " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            arlDatDetForPag.clear();
                            while(rst.next()){
                                arlRegDetForPag=new ArrayList();
                                arlRegDetForPag.add(INT_ARL_DFP_COD_EMP, rst.getString("co_emp"));
                                arlRegDetForPag.add(INT_ARL_DFP_COD_PAG, rst.getString("co_forPag"));
                                arlRegDetForPag.add(INT_ARL_DFP_COD_REG, rst.getString("co_reg"));
                                arlRegDetForPag.add(INT_ARL_DFP_DIA_CRE, rst.getString("ne_diaCre"));
                                arlRegDetForPag.add(INT_ARL_DFP_EST_SOP, rst.getString("st_sop"));
                                arlDatDetForPag.add(arlRegDetForPag);
                            }
     
                            //Limpiar objetos de conexion a db
                            stm.close();
                            stm=null;
                            rst.close();
                            rst=null;
                            
                            if(generarSQLDocumentoIngreso(con)){
                                if(objGenOrdDes.generarOrdDesxPrexProyTra(con, intCodEmpEgr, intCodLocEgr, intCodTipDocEgr, intCodDocEgr)){
                                    blnRes=true;
                                }
                            }   
                        }
                    }
                }
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            System.err.println("ERROR SQL ingreso/egreso: " + e.toString());
        }
        catch(Exception e){
            blnRes=false;
            System.err.println("ERROR ingreso/egreso: " + e.toString());
        }
        return blnRes;
    }
    
    
    
    /**
     * Función que permite generar el documento de ingreso/egreso dependiendo de la naturaleza del tipo de documento 
     * @return true: Si se pudo realizar la operación
     * <BR> false: Caso contrario
     */
    private boolean generarSQLDocumentoEgreso(Connection con){
        boolean blnRes=false;
        iniDia();
        try{
            if(con!=null){
                if(insertar_tbmCabMovInv(con, 0)){
                    if(objSegMovInv.setSegMovInvCompra(con, intCodEmpSol, intCodLocSol, intCodTipDocSol, intCodDocSol, 5, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
                        if(insertar_tbmDetMovInv(con, 0)){
                            if(insertar_tbmPagMovInv(con)){
                                if(objCnfDoc.setCamNotIngEgrMerBodega(con, arlDatCnfNumRecEnt)){
                                    if(genDiaEgr(intCodEmpEgrGenAsiDia, intCodEmpIngGenAsiDia)){
                                        if (objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, intCodTipDoc, String.valueOf(intCodDoc), String.valueOf(intNumDoc), dtpFecDoc)){
                                            System.out.println("generarSQLDocumentoEgreso");
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
            System.out.println("Error - generarSQLDocumentoEgreso: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite generar el documento de ingreso/egreso dependiendo de la naturaleza del tipo de documento
     * @return true: Si se pudo realizar la operación
     * <BR> false: Caso contrario
     */
    private boolean generarSQLDocumentoIngreso(Connection con){
        boolean blnRes=false;
        iniDia();
        try{
            if(con!=null){
                if(insertar_tbmCabMovInv(con, 2)){
                    if(objSegMovInv.setSegMovInvCompra(con, intCodEmpSol, intCodLocSol, intCodTipDocSol, intCodDocSol, 5, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
                        if(insertar_tbmDetMovInv(con, 1)){
                            if(insertar_tbmPagMovInv(con)){
                                if(inserta_TbrDetMovInv(con)){
                                    if(objCnfDoc.setCamNotIngEgrMerBodega(con, arlDatCnfNumRecEnt)){
                                        if(genDiaIng(intCodEmpIngGenAsiDia, intCodEmpEgrGenAsiDia)){
                                            if(objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, intCodTipDoc, String.valueOf(intCodDoc), String.valueOf(intNumDoc), dtpFecDoc)){
                                                System.out.println("generarSQLDocumentoIngreso");
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
        catch(Exception e){
            System.out.println("Error - generarSQLDocumentoIngreso: " + e);
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
    private boolean insertar_tbmCabMovInv(Connection con, int tipoTransaccion)
    {
        boolean blnRes=true;
        String strRucCliPrv="";
        String strNomCliPrv="";
        String strDirCliPrv="";
        String strTelCliPrv="";
        int intCodForPagCliPrv=-1;
        String strDesForPagCliPrv="";
        int intCodBodGrpOri=-1, intCodBodGrpDes=-1;
        String strRutRptOrdAlm="", strNomRptOrdAlm="";
        try{
            if(con!=null)
            {
                //Obtiene Rutas de Reporte de Orden de Almacenamiento.
                strRutRptOrdAlm=getRutaRptSis(INT_COD_RPT_ORD_ALM, 0);
                strNomRptOrdAlm=getRutaRptSis(INT_COD_RPT_ORD_ALM, 3);
                
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
                strSQL+="   co_forpag, tx_desforpag, nd_poriva,nd_sub, nd_tot, tx_obs1, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, tx_numped,";
                strSQL+="   ne_secgrp, ne_secemp, nd_valiva, co_mnu, st_tipdev, st_imp, tx_coming, tx_commod, co_ptodes,";
                strSQL+="   co_emprelcabsoltrainv, co_locrelcabsoltrainv, co_tipdocrelcabsoltrainv, co_docrelcabsoltrainv, tx_tipmov, st_genOrdDes";
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
                    if((tipoTransaccion==0))//egreso
                        strSQL+=" " + objParSis.getPorcentajeIvaVentas(intCodEmp, intCodLoc, dtpFecDoc)+ ",";//nd_poriva
                    else//ingreso
                        strSQL+=" " + objParSis.getPorcentajeIvaCompras(intCodEmp, intCodLoc, dtpFecDoc) + ",";//nd_poriva
                }
                else{
                    strSQL+="Null,";//co_cli
                    strSQL+="Null,";//tx_ruc
                    strSQL+="Null,";//tx_nomcli
                    strSQL+="Null,";//tx_dircli
                    strSQL+="Null,";//tx_telcli
                    strSQL+="Null,";//co_forpag
                    strSQL+="Null,";//tx_desforpag
                    strSQL+="0, ";//nd_poriva
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
                    strSQL+="" + intCodBodIng_EmpGenEgr + ",";//co_ptodes                                //strSQL+="" + intCodBodIng + ",";//co_ptodes  
                else if(tipoTransaccion==2)//Ingreso
                    strSQL+="" + intCodBodEgr_EmpGenIng + ",";//co_ptodes                                //strSQL+="" + intCodBodEgr + ",";//co_ptodes
                else if(tipoTransaccion==1)//Transferencia
                    strSQL+=" Null,";//co_ptodes
                else
                    strSQL+=" Null, ";//co_ptodes
                
                strSQL+="" + intCodEmpSol + ",";//co_emprelcabsoltrainv
                strSQL+="" + intCodLocSol + ",";//co_locrelcabsoltrainv
                strSQL+="" + intCodTipDocSol + ",";//co_tipdocrelcabsoltrainv
                strSQL+="" + intCodDocSol + ",";//co_docrelcabsoltrainv
                strSQL+="'" + strTipMovSeg + "'";//tx_tipmov
                System.out.println("1-insertar_tbmCabMovInv");
                
                if(objCfgBod==null)
                    System.out.println("objCfgBod - ES NULO");
                else
                    System.out.println("objCfgBod - NO ES NULO");
                
                if(tipoTransaccion==0){//Egreso
                    if(objEstDocGenOrdDes==null)
                        strSQL+=", " + objEstDocGenOrdDes + "";//st_genOrdDes
                    else
                        strSQL+=", '" + objEstDocGenOrdDes + "'";//st_genOrdDes
                    
                    blnConEgr=objCfgBod.isConEgr();
                    strEgrGen=objCfgBod.getEgrGen();
                }
                else if(tipoTransaccion==1){//Transferencia
                    if(objEstDocGenOrdDes==null)
                        strSQL+=", " + objEstDocGenOrdDes + "";//st_genOrdDes
                    else
                        strSQL+=", '" + objEstDocGenOrdDes + "'";//st_genOrdDes
                    
                    //AHORA - NUEVO
                    intCodBodGrpOri=getCodBodGrp(intCodEmp, intCodBodEgr);
                    intCodBodGrpDes=getCodBodGrp(intCodEmp, intCodBodIng);

                    blnConIng=objCfgBod.isConIng();
                    blnConEgr=objCfgBod.isConEgr();
                    strEgrGen=objCfgBod.getEgrGen();
                    strIngGen=objCfgBod.getIngGen();  
                }
                else if(tipoTransaccion==2){//Ingreso
                    if(objEstDocGenOrdDes==null)
                        strSQL+=", Null";//st_genOrdDes
                    else
                        strSQL+=", Null";//st_genOrdDes
                    
                    blnConIng=objCfgBod.isConIng();
                    strIngGen=objCfgBod.getIngGen();
                }
                else{
                   strSQL+=", Null";//st_genOrdDes
                    blnConIng=false;
                    blnConEgr=false;
                    strEgrGen="";
                    strIngGen="";
                }
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
                
                System.out.println("strEgrGen: " + strEgrGen);
                System.out.println("strIngGen: " + strIngGen);
                

                if(!strEgrGen.equals("")){
                    getImpRepConIngEgr(objParSis, cmpPad, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strEgrGen, strRutRptOrdAlm, strNomRptOrdAlm, (strEgrGen.equals("E")?"Orden de Egreso":(strEgrGen.equals("D")?"Orden de Despacho":"")));
                }
                    
                if(!strIngGen.equals("")){
                    getImpRepConIngEgr(objParSis, cmpPad, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strIngGen, strRutRptOrdAlm, strNomRptOrdAlm, (strIngGen.equals("A")?"Orden de Almacenamiento":""));
                }
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
        BigDecimal bgdTotItm=BigDecimal.ZERO;
        BigDecimal bgdIntSig;
        String strNomBodDet="";
        int intNumFil=0;
        String strEstIngEgrMerBod="";
        arlDatCnfNumRecEnt.clear();
        int intCodBodGrpOri=-1, intCodBodGrpDes=-1;
        String strCodAltItmSinCos="";
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
                    
//                    blnValConEgr=false;
//                    blnValConIng=false;
                    
                    
                    if((i%2)==0)//es impar
                        intNumFil++;
                    
                    //nombre de bodega origen
                    strSQL="";
                    strSQL+="SELECT a1.tx_nom AS tx_nomBod FROM tbm_bod AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_bod=" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_BOD_ORI_DES)) + "";
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
                    strSQL+="   co_docrelsoldevven, co_regrelsoldevven, nd_cannunrec,";
                    strSQL+="   co_locrelsolsaltemmer, co_tipdocrelsolsaltemmer, co_docrelsolsaltemmer,";
                    strSQL+="   co_regrelsolsaltemmer, nd_preunivenlis, nd_pordesvenmax, tx_nombodorgdes,";
                    strSQL+="   nd_cantotmalest, nd_cantotmalestpro, nd_cantotnunrecpro, st_cliretemprel,";
                    strSQL+="   fe_retemprel, tx_perretemprel, tx_vehretemprel, tx_plavehretemprel,";
                    strSQL+="   tx_obscliretemprel, nd_ara, nd_preuniimp, nd_valtotfobcfr, nd_cantonmet,";
                    strSQL+="   nd_valfle, nd_valcfr, nd_valtotara, nd_valtotgas, nd_canutiorddis";
                    
                    strEstIngEgrMerBod=objUti.getObjectValueAt(arlDatItm, i, INT_ARL_EST_ING_EGR_MER_BOD)==null?"":objUti.getStringValueAt(arlDatItm, i, INT_ARL_EST_ING_EGR_MER_BOD);
                    
                    strSQL+=", nd_canEgrBod";
                    strSQL+=", nd_canIngBod";
                    strSQL+=", nd_canPen";
                    strSQL+=", st_meringegrfisbod";
                    
                    strSQL+=")";
                    strSQL+="(";
                    strSQL+="   SELECT ";
                    strSQL+="" + intCodEmp + ",";//co_emp
                    strSQL+="" + intCodLoc + ",";//co_loc
                    strSQL+="" + intCodTipDoc + ",";//co_tipdoc
                    strSQL+="" + intCodDoc + ",";//co_doc
                    strSQL+="" + j + ",";//co_reg
                    strSQL+="" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_EMP) + ",";//co_itm
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ALT_ITM)) + ",";//tx_codalt
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_NOM_ITM)) + ",";//tx_nomitm
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_UNI_MED_ITM)) + ",";//tx_unimed
                    //strSQL+="" + intCodBodRegUno + ",";//co_bod
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_BOD_ORI_DES)) + ",";//co_bod
                    bgdCanItm=new BigDecimal(objUti.getObjectValueAt(arlDatItm, i, INT_ARL_CAN_ITM)==null?"0":(objUti.getStringValueAt(arlDatItm, i, INT_ARL_CAN_ITM).equals("")?"0":objUti.getStringValueAt(arlDatItm, i, INT_ARL_CAN_ITM)));
                    strSQL+="" + bgdCanItm + ",";//nd_can
                    strSQL+="Null,";//nd_canorg
                    
                    System.out.println("COSTO UNITARIO LLEGA: " + objUti.redondearBigDecimal(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COS_UNI), intNumDec));
                    //recalcular costos
//                    if(  (intCodTipDoc==205) || /* (intCodTipDoc==206) ||*/ (intCodTipDoc==2) /* ||  (intCodTipDoc==228)*/ || (intCodTipDoc==4) || (intCodTipDoc==229)  ){
//                        if(!procesarDocumento(con, objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_EMP)
//                                , objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ALT_ITM))
//                                , ""+intNumSecGrp, ""+intNumSecEmp, bgdCanItm, ""+intCodEmp, ""+intCodLoc, ""+intCodTipDoc, ""+intCodDoc, ""+j, i))
//                            blnRes=false;
//                    }
//                    System.out.println("COSTO UNITARIO MODIFICADO: " + objUti.redondearBigDecimal(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COS_UNI), intNumDec));
                    
                    bgdPreItm=objUti.redondearBigDecimal(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COS_UNI), intNumDec);
                    System.out.println("bgdPreItm-ANTES: " + bgdPreItm);
                    
                    //if( (intCodTipDoc==206) || (intCodTipDoc==228) || (intCodTipDoc==4)  ){
                    if( (intCodTipDoc==228) || (intCodTipDoc==4)  ){
                        if(bgdPreItm.compareTo(new BigDecimal("0"))<=0){
                            strCodAltItmSinCos+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ALT_ITM)) + ",  ";
                            mostrarMsgInf("<HTML>Costos negativos.<BR>Solicitar verificación al Administrador del Sistema.</HTML>");
                            blnRes=false;
                        }
                    }                    
                    strEstIva=objUti.getStringValueAt(arlDatItm, i, INT_ARL_IVA_COM_ITM);
                    bgdTotItm=objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)), intNumDec);                 
                    if(chrGenIva=='S'){
                        if(getMarUtiMovRel(tipoOperacion)){
                            bgdPreItm=objUti.redondearBigDecimal((bgdPreItm.multiply(bdeMarUtiComVenRel)),objParSis.getDecimalesBaseDatos());
                            System.out.println("bgdPreItm-DESPUES: " + bgdPreItm);
                        }
                        else
                            blnRes=false;
                        
                        bgdTotItm=objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)), intNumDec);
                        if(strEstIva.equals("S")){
                            bgdValDocIva=bgdValDocIva.add(objUti.redondearBigDecimal((bgdTotItm.multiply(bgdValPorIva)), objParSis.getDecimalesMostrar()));//al final tendrá el valor del iva del documento
                        }
                        
                    }
                    
                    strSQL+="" + objUti.redondearBigDecimal(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COS_UNI), intNumDec) + ",";//nd_cosuni
                    strSQL+="" + bgdPreItm + ",";//nd_preuni
                    strSQL+="0,";//nd_pordes     por disposición de Eddye para FACVENE, FACCOM, INGBOPR, EGBOPR, TRANSFERENCIAS EN GENERAL, no se debe realizar descuento  

                    strSQL+="" + objUti.codificar(strEstIva) + ",";//st_ivacom                
                    strSQL+="Null, ";//nd_exi
                    strSQL+="Null,";//nd_valexi
                    strSQL+="Null,";//st_reg
                    strSQL+="0,";//nd_cancon
                    strSQL+="Null,";//tx_obs1
                    strSQL+="Null,";//co_usrcon
                    strSQL+="" + intNumFil + ",";//ne_numfil
                    strSQL+="" + objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)), objParSis.getDecimalesMostrar()) + ", ";//nd_tot
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_LET_ITM)) + ",";//tx_codalt2
                    strSQL+="" + objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)), objParSis.getDecimalesMostrar()) + ",";//nd_costot
                    strSQL+="Null,";//nd_cospro
                    strSQL+="" + bgdPreItm + ",";//nd_cosunigrp
                    strSQL+="" + objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)), objParSis.getDecimalesMostrar()) + ",";//nd_costotgrp
                    strSQL+="Null,";//nd_exigrp
                    strSQL+="Null,";//nd_valexigrp
                    strSQL+="Null,";//nd_cosprogrp
                    strSQL+="0, ";//nd_candev
                    strSQL+="" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_EMP) + ",";//co_itmact
                    strSQL+="Null,";//co_locrelsoldevven
                    strSQL+="Null,";//co_tipdocrelsoldevven
                    strSQL+="Null,";//co_docrelsoldevven
                    strSQL+="Null,";//co_regrelsoldevven
                    
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
                    
                    
                    //3.- Validación por terminal del item
                    
                    
                        System.out.println("********************************************************************");
                        System.out.println("strEstIngEgrMerBod: " + strEstIngEgrMerBod);
                    
                        if(tipoOperacion==0){//egreso
                            if(blnConEgr){//bodega se confirma
                                intCodBodGrpOri=getCodBodGrp(intCodEmpEgrGenAsiDia, intCodBodEgr);
                                intCodBodGrpDes=getCodBodGrp(intCodEmpIngGenAsiDia, intCodBodIng);
                                if(intCodBodGrpOri==intCodBodGrpDes){
                                    strSQL+=", Null";//nd_canEgrBod
                                    strSQL+=", Null";//nd_canIngBod
                                    strSQL+=", Null";//nd_canPen
                                    strEstIngEgrMerBod="N";
                                    System.out.println("AA: ");
                                }
                                else{
                                    if(strEstIngEgrMerBod.equals("S")){
                                        strSQL+=", " + bgdCanItm + "";//nd_canEgrBod
                                        strSQL+=", Null";//nd_canIngBod
                                        strSQL+=", " + bgdCanItm + "";//nd_canPen
                                        System.out.println("BB: ");
                                    }
                                    else{
                                        strSQL+=", Null";//nd_canEgrBod
                                        strSQL+=", Null";//nd_canIngBod
                                        strSQL+=", Null";//nd_canPen
                                        strEstIngEgrMerBod="N";
                                        System.out.println("CC: ");
                                    }
                                }
                            }
                            else{
                                strSQL+=", Null";//nd_canEgrBod
                                strSQL+=", Null";//nd_canIngBod
                                strSQL+=", Null";//nd_canPen
                                strEstIngEgrMerBod="N";
                                System.out.println("DD: ");
                            }                                 
                        }
                        else if(tipoOperacion==1){//ingreso                            
                            if(blnConIng){//bodega se confirma
                                intCodBodGrpOri=getCodBodGrp(intCodEmpEgrGenAsiDia, intCodBodEgr);
                                intCodBodGrpDes=getCodBodGrp(intCodEmpIngGenAsiDia, intCodBodIng);

                                if(intCodBodGrpOri==intCodBodGrpDes){
                                    strSQL+=", Null";//nd_canEgrBod
                                    strSQL+=", Null";//nd_canIngBod
                                    strSQL+=", Null";//nd_canPen
                                    strEstIngEgrMerBod="N";
                                    System.out.println("EE: ");
                                }
                                else{
                                    if(strEstIngEgrMerBod.equals("S")){
                                        strSQL+=", Null";//nd_canEgrBod
                                        strSQL+=", " + bgdCanItm + "";//nd_canIngBod
                                        strSQL+=", " + bgdCanItm + "";//nd_canPen
                                        System.out.println("FF: ");
                                    }
                                    else{
                                        strSQL+=", Null";//nd_canEgrBod
                                        strSQL+=", Null";//nd_canIngBod
                                        strSQL+=", Null";//nd_canPen
                                        strEstIngEgrMerBod="N";
                                        System.out.println("GG: ");
                                    }
                                }
                            }
                            else{
                                strSQL+=", Null";//nd_canEgrBod
                                strSQL+=", Null";//nd_canIngBod
                                strSQL+=", Null";//nd_canPen
                                strEstIngEgrMerBod="N";
                                System.out.println("HH: ");
                            }
                        }
                        else if(tipoOperacion==2){//tx
                            
                            System.out.println("intCodEmp: " + intCodEmp);
                            
                            getBodegasTransfer();
                            

                            System.out.println("*constructor - intCodBodEgr:" + intCodBodEgr);
                            System.out.println("*constructor - intCodBodIng:" + intCodBodIng);
                            
                            intCodBodGrpOri=getCodBodGrp(intCodEmp, intCodBodEgr);
                            intCodBodGrpDes=getCodBodGrp(intCodEmp, intCodBodIng);
                            System.out.println("intCodBodGrpOri: " + intCodBodGrpOri);
                            System.out.println("intCodBodGrpDes: " + intCodBodGrpDes);
                            
                            if((i%2)==0){//es impar - egreso                                
                                if(blnConEgr){//bodega se confirma
                                    if(intCodBodGrpOri==intCodBodGrpDes){
                                        strSQL+=", Null";//nd_canEgrBod
                                        strSQL+=", Null";//nd_canIngBod
                                        strSQL+=", Null";//nd_canPen
                                        strEstIngEgrMerBod="N";
                                        System.out.println("II: ");
                                    }
                                    else{
                                        if(strEstIngEgrMerBod.equals("S")){
                                            strSQL+=", " + bgdCanItm + "";//nd_canEgrBod
                                            strSQL+=", Null";//nd_canIngBod
                                            strSQL+=", " + bgdCanItm + "";//nd_canPen
                                            System.out.println("JJ: ");
                                        }
                                        else{
                                            strSQL+=", Null";//nd_canEgrBod
                                            strSQL+=", Null";//nd_canIngBod
                                            strSQL+=", Null";//nd_canPen
                                            strEstIngEgrMerBod="N";
                                            System.out.println("KK: ");
                                        }
                                    }
                                }
                                else{
                                    strSQL+=", Null";//nd_canEgrBod
                                    strSQL+=", Null";//nd_canIngBod
                                    strSQL+=", Null";//nd_canPen
                                    strEstIngEgrMerBod="N";
                                    System.out.println("LL: ");
                                }
                            }
                            if((i%2)!=0){//es par  - ingreso
                                if(blnConIng){//bodega se confirma
                                    if(intCodBodGrpOri==intCodBodGrpDes){
                                        strSQL+=", Null";//nd_canEgrBod
                                        strSQL+=", Null";//nd_canIngBod
                                        strSQL+=", Null";//nd_canPen
                                        strEstIngEgrMerBod="N";
                                        System.out.println("MM: ");
                                    }
                                    else{
                                        if(strEstIngEgrMerBod.equals("S")){
                                            strSQL+=", Null";//nd_canEgrBod
                                            strSQL+=", " + bgdCanItm + "";//nd_canIngBod
                                            strSQL+=", " + bgdCanItm + "";//nd_canPen
                                            System.out.println("NN: ");
                                        }
                                        else{
                                            strSQL+=", Null";//nd_canEgrBod
                                            strSQL+=", Null";//nd_canIngBod
                                            strSQL+=", Null";//nd_canPen
                                            strEstIngEgrMerBod="N";
                                            System.out.println("OO: ");
                                        }
                                    }
                                }
                                else{
                                    strSQL+=", Null";//nd_canEgrBod
                                    strSQL+=", Null";//nd_canIngBod
                                    strSQL+=", Null";//nd_canPen
                                    strEstIngEgrMerBod="N";
                                    System.out.println("PP: ");
                                }
                            }
                        }

                    
                    strSQL+=", '" + strEstIngEgrMerBod + "'";//st_meringegrfisbod  
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
                    //recosteo
                    //objUti.recostearItm2009DesdeFecha(cmpPad, objParSis, con, intCodEmp, objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_EMP), objUti.formatearFecha(dtpFecDoc, objParSis.getFormatoFechaBaseDatos()), "yyyy-MM-dd");
                }
                
                System.out.println("****************************************************************");
                System.out.println("bgdValDocSub:" + bgdValDocSub);
                System.out.println("bgdValDocTot:" + bgdValDocTot);
                System.out.println("bgdValDocIva:" + bgdValDocIva);
                System.out.println("****************************************************************");
                
                strSQL="";
                strSQL+=" UPDATE tbm_cabMovInv ";
                strSQL+=" SET nd_sub="+bgdValDocSub+", nd_tot="+bgdValDocTot+", nd_valIva="+bgdValDocIva+" ";
                
                if(blnConEgr){
                    if(strEgrGen.equals("D"))
                        strSQL+=" , st_genOrdDes='S'";
                    else
                        strSQL+=" , st_genOrdDes=Null";
                }
                else
                    strSQL+=" , st_genOrdDes=Null";
                strSQL+=" WHERE co_emp="+intCodEmp + " AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc+" AND co_doc="+intCodDoc+";";
                System.out.println("UPDATE tbm_cabMovInv: " + strSQL);
                strSQLIns+=strSQL;
                
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                
                if(strCodAltItmSinCos.length()>0)
                    mostrarMsgInf("Los items que cuentan con problemas son: " + strCodAltItmSinCos);
                
                strCodAltItmSinCos="";
                
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
     * Función que permite realizar la inserción de la relación entre el detalle del egreso y del ingreso
     * @param con: Especifíca la conexión que se desea utilizar
     * @return true: Si se pudo realizar la operación
     * <BR> false: caso contrario
    */
    private boolean inserta_TbrDetMovInv(Connection con){
        boolean blnRes=true;
        int j=0;
        String strSQLIns="";
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int i=0; i<arlDatTbrDetMovInvEgr.size(); i++){
                    j++;
                    strSQL="";
                    strSQL+=" INSERT INTO tbr_detmovinv(";
                    strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, co_reg,";
                    strSQL+="   co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel";
                    strSQL+=" , st_reg) (SELECT ";
                    strSQL+=" " + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_EMP) + "";//co_emp
                    strSQL+="," + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_LOC) + "";//co_loc
                    strSQL+="," + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_TIP_DOC) + "";//co_tipdoc
                    strSQL+="," + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_DOC) + "";//co_doc
                    strSQL+="," + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_REG) + "";//co_reg
                    strSQL+="," + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_EMP_REL) + "";//co_emprel
                    strSQL+="," + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_LOC_REL) + "";//co_locrel
                    strSQL+="," + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_TIP_DOC_REL) + "";//co_tipdocrel
                    strSQL+="," + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_DOC_REL) + "";//co_docrel
                    strSQL+="," + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_REG_REL) + "";//co_regrel
                    strSQL+=",'A');";//st_reg
                    strSQLIns+=strSQL;
                }
                System.out.println("inserta_TbrDetMovInv: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
            }

        }
        catch(java.sql.SQLException e){
            System.out.println("Error - inserta_TbrDetMovInv SQL: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - inserta_TbrDetMovInv: " + e);
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
     * Función que permite realizar la inserción en la tabla de pagos
     * @return true: Si se pudo realizar la operación
     * <BR> false: caso contrario
     */
    private boolean insertar_tbmPagMovInv(Connection con){
        boolean blnRes=true;
        String strTipRetApl="";
        int j=0;
        BigDecimal bgdMonPag=new BigDecimal("0");
        BigDecimal bgdMonTotAplRet=new BigDecimal("0");
        String strSQLIns="";
        BigDecimal bgdValPenDocTot=new BigDecimal("0");
        BigDecimal bgdValAplDocTot=new BigDecimal("0");//Valor que se ha aplicado del total pendiente(Total - retenciones)
        BigDecimal bgdValCuoPag=new BigDecimal("0");//valor de la cuota que se debe cancelar
        BigDecimal bgdValCuoPagAcu=new BigDecimal("0");//valor de la cuota que ya se ha "pagado"
        try{
            stm=con.createStatement();
            //registros de retenciones en tbm_pagMovInv
            System.out.println("arlDatCabForPag:" + arlDatCabForPag.toString());
            System.out.println("arlDatDetForPag:" + arlDatDetForPag.toString());
            
            for(int i=0; i<arlDatCabForPag.size(); i++){
                j++;
                strSQL="";
                strSQL+="INSERT INTO tbm_pagmovinv(";
                strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, co_reg, ne_diacre, fe_ven,";
                strSQL+="   co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop,";
                strSQL+="   st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq,";
                strSQL+="   fe_venchq, nd_monchq, co_prochq, st_reg, fe_ree, co_usrree,";
                strSQL+="   tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp,";
                strSQL+="   tx_numser, tx_numautsri, tx_feccad, fe_venchqautpag, nd_valiva,";
                strSQL+="   tx_tipreg";
                strSQL+=" ) (";
                strSQL+=" SELECT " + intCodEmp + ",";//co_emp
                strSQL+="" + intCodLoc + ",";//co_loc
                strSQL+="" + intCodTipDoc + ",";//co_tipdoc
                strSQL+="" + intCodDoc + ",";//co_doc
                strSQL+="" + j + ",";//co_reg
                strSQL+="0,";//ne_diacre
                strSQL+="" + objUti.codificar(objUti.formatearFecha(dtpFecDoc, "yyyy-MM-dd")) + ",";//fe_ven
                strSQL+="" + objUti.getStringValueAt(arlDatCabForPag, i, INT_ARL_CFP_COD_TIP_RET) + ",";//co_tipret
                strSQL+="" + objUti.getBigDecimalValueAt(arlDatCabForPag, i, INT_ARL_CFP_POR_RET).multiply(new BigDecimal("100")) + ",";//nd_porret
                strTipRetApl=objUti.getStringValueAt(arlDatCabForPag, i, INT_ARL_CFP_APL_RET);
                strSQL+="" + objUti.codificar(strTipRetApl) + ",";//tx_aplret
                if(strTipRetApl.equals("S"))
                    bgdMonPag=bgdValDocSub.multiply(objUti.getBigDecimalValueAt(arlDatCabForPag, i, INT_ARL_CFP_POR_RET));
                else if(strTipRetApl.equals("I"))
                    bgdMonPag=bgdValDocIva.multiply(objUti.getBigDecimalValueAt(arlDatCabForPag, i, INT_ARL_CFP_POR_RET));
                else
                    return false;
                
                bgdMonTotAplRet=objUti.redondearBigDecimal((bgdMonTotAplRet.add(bgdMonPag)), objParSis.getDecimalesMostrar());                                                            //Valor aplicado por retenciones
                strSQL+="" + objUti.redondearBigDecimal(bgdMonPag, objParSis.getDecimalesMostrar()) + ",";//mo_pag
                strSQL+="0,";//ne_diagra
                strSQL+="0,";//nd_abo
                strSQL+="'N',";//st_sop
                strSQL+="'N',";//st_entsop
                strSQL+="'N',";//st_pos
                strSQL+="Null,";//co_banchq
                strSQL+="Null,";//tx_numctachq
                strSQL+="Null,";//tx_numchq
                strSQL+="Null,";//fe_recchq
                strSQL+="Null,";//fe_venchq
                strSQL+="Null,";//nd_monchq
                strSQL+="Null,";//co_prochq
                strSQL+="'A',";//st_reg
                strSQL+="Null,";//fe_ree
                strSQL+="Null,";//co_usrree
                strSQL+="Null,";//tx_comree
                strSQL+="'N',";//st_autpag
                strSQL+="Null,";//co_ctaautpag
                strSQL+="Null,";//tx_obs1
                strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatCabForPag, i, INT_ARL_CFP_COD_SRI)) + ",";//tx_codsri
                strSQL+="" + objUti.redondearBigDecimal(bgdValDocSub, objParSis.getDecimalesMostrar()) + ",";//nd_basimp
                strSQL+="Null,";//tx_numser
                strSQL+="Null,";//tx_numautsri
                strSQL+="Null,";//tx_feccad
                strSQL+="Null,";//fe_venchqautpag
                strSQL+="" + bgdValDocIva + ",";//nd_valiva
                strSQL+="Null";//tx_tipreg
                strSQL+=");";
                System.out.println("insertar_tbmPagMovInv: " + strSQL);
                strSQLIns+=strSQL;
            }
            
            bgdValPenDocTot=bgdValDocTot.subtract(bgdMonTotAplRet);//Valor del documento - Valor aplicado por retenciones
            
            for(int i=0; i<intNumPag; i++){
                bgdValCuoPag=bgdValPenDocTot.divide(new BigDecimal(""+intNumPag), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
                if(i==(intNumPag-1)){
                    bgdValCuoPag=bgdValPenDocTot.subtract(bgdValCuoPagAcu);
                }                        
                j++;
                bgdValCuoPagAcu=objUti.redondearBigDecimal((bgdValCuoPagAcu.add(bgdValCuoPag)), objParSis.getDecimalesMostrar());
                
                strSQL="";
                strSQL+="INSERT INTO tbm_pagmovinv(";
                strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, co_reg, ne_diacre, fe_ven,";
                strSQL+="   co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop,";
                strSQL+="   st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq,";
                strSQL+="   fe_venchq, nd_monchq, co_prochq, st_reg, fe_ree, co_usrree,";
                strSQL+="   tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp,";
                strSQL+="   tx_numser, tx_numautsri, tx_feccad, fe_venchqautpag, nd_valiva,";
                strSQL+="   tx_tipreg";
                strSQL+=" ) (";
                strSQL+=" SELECT " + intCodEmp + ",";//co_emp
                strSQL+="" + intCodLoc + ",";//co_loc
                strSQL+="" + intCodTipDoc + ",";//co_tipdoc
                strSQL+="" + intCodDoc + ",";//co_doc
                strSQL+="" + j + ",";//co_reg
                strSQL+="" + objUti.getStringValueAt(arlDatDetForPag, i, INT_ARL_DFP_DIA_CRE) + ", ";//ne_diacre
//              strSQL+=" ( SELECT (" + objUti.codificar(objUti.formatearFecha(dtpFecDoc, "yyyy-MM-dd")) + ") + " + objUti.getStringValueAt(arlDatDetForPag, i, INT_ARL_DFP_DIA_CRE) + "),";//fe_ven
                strSQL+=" ( SELECT CAST(" + objUti.codificar(objUti.formatearFecha(dtpFecDoc, objParSis.getFormatoFechaBaseDatos())) + " AS DATE) + " + objUti.getStringValueAt(arlDatDetForPag, i, INT_ARL_DFP_DIA_CRE) + "),";//fe_ven
                //strSQL+=" ( SELECT CAST('" + objUti.formatearFecha(dtpFecDoc, objParSis.getFormatoFechaBaseDatos()) + "' AS DATE) + CAST('" + objUti.getStringValueAt(arlDatDetForPag, i, INT_ARL_DFP_DIA_CRE) + " days' AS INTERVAL)),";//fe_ven
                
                strSQL+="0,";//co_tipret
                strSQL+="0,";//nd_porret
                strSQL+="Null,";//tx_aplret

                //bgdMonTotAplRet=bgdMonTotAplRet.add(bgdMonPag);
                strSQL+="" + objUti.redondearBigDecimal(bgdValCuoPag, objParSis.getDecimalesMostrar()) + ",";//mo_pag
                strSQL+="0,";//ne_diagra
                strSQL+="0,";//nd_abo
                strSQL+="'N',";//st_sop
                strSQL+="'N',";//st_entsop
                strSQL+="'N',";//st_pos
                strSQL+="Null,";//co_banchq
                strSQL+="Null,";//tx_numctachq
                strSQL+="Null,";//tx_numchq
                strSQL+="Null,";//fe_recchq
                strSQL+="Null,";//fe_venchq
                strSQL+="Null,";//nd_monchq
                strSQL+="Null,";//co_prochq
                strSQL+="'A',";//st_reg
                strSQL+="Null,";//fe_ree
                strSQL+="Null,";//co_usrree
                strSQL+="Null,";//tx_comree
                strSQL+="'N',";//st_autpag
                strSQL+="Null,";//co_ctaautpag
                strSQL+="Null,";//tx_obs1
                strSQL+="Null,";//tx_codsri
                strSQL+="" + objUti.redondearBigDecimal(bgdMonPag, objParSis.getDecimalesMostrar()) + ",";//nd_basimp
                strSQL+="Null,";//tx_numser
                strSQL+="Null,";//tx_numautsri
                strSQL+="Null,";//tx_feccad
                strSQL+="Null,";//fe_venchqautpag
                strSQL+="" + bgdValDocIva + ",";//nd_valiva
                strSQL+="Null";//tx_tipreg
                strSQL+=");";
                System.out.println("insertar_tbmPagMovInv: " + strSQL);
                strSQLIns+=strSQL;
            }
            stm.executeUpdate(strSQLIns);
            
            System.out.println("---------------------------");
            System.out.println("bgdValDocTot: " + bgdValDocTot);
            System.out.println("bgdValCuoPagAcu: " + bgdValCuoPagAcu);
            System.out.println("bgdMonTotAplRet: " + bgdMonTotAplRet);
            
            

            if(bgdValDocTot.compareTo(bgdValCuoPagAcu.add(bgdMonTotAplRet))!=0){
                System.out.println("Se generaron mal los cálculos de los pagos");
                return false;
            }            
            
            // UPDATE .... tbm_cabMovInv nd_sub, nd_to, nd_valIva 
            
            
        }
        catch(java.sql.SQLException e){
            System.out.println("Error SQL - insertar_tbmPagMovInv SQL: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - insertar_tbmPagMovInv SQL: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
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
    public boolean getDatoTransferencia(Connection con, int intCodigoEmpresa, int intCodigoLocal, int intCodigoTipoDocumento
        , Date dtpFechaDocumento, int intCodigoBodegaEmpresaEgreso, int intCodigoBodegaEmpresaIngreso, char chrGenerarIva, ArrayList arlDatosItem
            , int codEmpSol, int codLocSol, int codTipDocSol, int codDocSol
            , String tipMovSeg, Object estGenOrdDes, ZafCfgBod objetoConfigBodega){
        boolean blnRes=false;
        objEstDocGenOrdDes=estGenOrdDes;
        ZafGenOdPryTra objGenTra = new GenOD.ZafGenOdPryTra();
        try{
            if(con!=null){

                intCodEmp=intCodigoEmpresa;
                intCodLoc=intCodigoLocal;
                intCodTipDoc=intCodigoTipoDocumento;
                dtpFecDoc=dtpFechaDocumento;
                arlDatItm=arlDatosItem;
                chrGenIva=chrGenerarIva;//jose mario debe enviarme correctamente el parametro
                System.out.println("chrGenIva-tx: " + chrGenIva);
                strTipMovSeg=tipMovSeg;
                
                //solicitud
                intCodEmpSol=codEmpSol;
                intCodLocSol=codLocSol;
                intCodTipDocSol=codTipDocSol;
                intCodDocSol=codDocSol;
                strTipMovSeg=tipMovSeg;
                

                
                objCfgBod=objetoConfigBodega;
                System.out.println("getDatoTransferencia - objCfgBod:" + objCfgBod);
                
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
                intCodEmpTraAux=intCodEmp; intCodLocTraAux=intCodLoc; intCodTipDocTraAux=intCodTipDoc; intCodDocTraAux=intCodDoc;
                System.out.println("TRANSFERENCIA: "+intCodEmpTraAux+","+intCodLocTraAux+","+intCodTipDocTraAux+","+intCodDocTraAux);
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
                    if (objGenTra.generarODTraPryTra(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
                        blnRes=true;
                    }else{
                        blnRes=false;
                    }
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
        iniDia();
        try{
            if(con!=null){
                if(insertar_tbmCabMovInv(con, 1)){
                    if(objSegMovInv.setSegMovInvCompra(con, intCodEmpSol, intCodLocSol, intCodTipDocSol, intCodDocSol, 5, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
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
     * Función que permite generar el diario del egreso
     * @param codigoEmpresaDocumento Código de empresa del documento
     * @param codigoEmpresaCliente Código de empresa del cliente
     */
    private boolean genDiaEgr(int codigoEmpresaDocumento, int codigoEmpresaCliente){
        boolean blnRes=true;
        try{
            System.out.println("EGRESO");
            System.out.println("codigoEmpresaDocumento:" + codigoEmpresaDocumento);
            System.out.println("codigoEmpresaCliente:" + codigoEmpresaCliente);
            //EMPRESA TUVAL - CLIENTE CASTEK
            if(codigoEmpresaDocumento==1){//Tuval
                if(codigoEmpresaCliente==2){//Castek                    
                    if(chrGenIva=='S'){//FACTURACION
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "4385");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.90");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "Compañias Relacionadas  Castek");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocTot.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber - iva
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "4545");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "2.01.06.02.20");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "14 % I.V.A. COBRADO EN VENTAS");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocIva.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1434");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "4.01.01.01");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "VENTAS MERCADERIAS GQUIL.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        
                    }
                    else{//PRESTAMO - no hay iva
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3548");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.88");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "CASTEK S.A.");
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
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }

                }
        
            }
            
            //EMPRESA TUVAL - CLIENTE DIMULTI
            if(codigoEmpresaDocumento==1){//Tuval
                if(codigoEmpresaCliente==4){//Dimulti                   
                    if(chrGenIva=='S'){
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "4386");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.91");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "Compañias Relacionadas  Dimulti");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocTot.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber - iva
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "4545");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "2.01.06.02.20");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "14 % I.V.A. COBRADO EN VENTAS");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocIva.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1434");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "4.01.01.01");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "VENTAS MERCADERIAS GQUIL.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                    else{//PRESTAMO - no hay iva
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3549");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.89");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "DIMULTI S.A.");
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
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                }
            }
            
        
            //EMPRESA CASTEK - CLIENTE TUVAL
            if(codigoEmpresaDocumento==2){//Castek
                if(codigoEmpresaCliente==1){//Tuval
                    if(chrGenIva=='S'){
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2110");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.90");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "Compañias Relacionadas  Tuval");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocTot.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    
                        //Cuenta de haber - iva
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2218");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "2.01.06.02.20");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "14 % I.V.A. COBRADO EN VENTAS");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocIva.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "545");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "4.01.01.01");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "VENTAS MERCADERIAS GQUIL.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                    else{//PRESTAMO - no hay iva
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1543");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.88");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "TUVAL S.A.");
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
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                    
                    
                    

                }
            }
    
            //EMPRESA CASTEK - CLIENTE DIMULTI
            if(codigoEmpresaDocumento==2){//Castek
                if(codigoEmpresaCliente==4){//Dimulti
                    
                    if(chrGenIva=='S'){
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2111");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.91");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "Compañias Relacionadas  Dimulti");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocTot.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    
                        //Cuenta de haber - iva
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2218");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "2.01.06.02.20");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "14 % I.V.A. COBRADO EN VENTAS");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocIva.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "545");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "4.01.01.01");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "VENTAS MERCADERIAS GQUIL.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                    else{//PRESTAMO - no hay iva
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1544");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.98");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "DIMULTI S.A.");
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
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                    
                    

                }
            }

            //EMPRESA DIMULTI - CLIENTE TUVAL
            if(codigoEmpresaDocumento==4){//Dimulti
                if(codigoEmpresaCliente==1){//Tuval
                    
                    if(chrGenIva=='S'){
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3062");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.91");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "Compañias Relacionadas  Tuval");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocTot.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    
                        //Cuenta de haber - iva
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3135");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "2.01.06.02.20");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "14 % I.V.A. COBRADO EN VENTAS");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocIva.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1373");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "4.01.01.01");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "VENTAS MERCADERIAS GQUIL.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                    else{//PRESTAMO - no hay iva
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2553");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.89");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "TUVAL S.A.");
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
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                    

                }
            }
            
            //EMPRESA DIMULTI - CLIENTE CASTEK
            if(codigoEmpresaDocumento==4){//Dimulti
                if(codigoEmpresaCliente==2){//Castek
                    
                    if(chrGenIva=='S'){
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3061");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.90");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "Compañias Relacionadas  Castek");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocTot.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    
                        //Cuenta de haber - iva
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3135");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "2.01.06.02.20");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "14 % I.V.A. COBRADO EN VENTAS");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocIva.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1373");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "4.01.01.01");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "VENTAS MERCADERIAS GQUIL.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                    else{//PRESTAMO - no hay iva
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2552");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.98");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "CASTEK S.A.");
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
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                    

                }
            }
            
            objAsiDia.setDetalleDiario(vecDatDia);
            System.out.println("*detalle diario:  " + objAsiDia.getDetalleDiario());
            
            objAsiDia.setGeneracionDiario((byte)2);
            System.out.println("***detalle diario:  " + objAsiDia.getDetalleDiario());
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Función que permite generar el diario cuando es un ingreso
     * @param codigoEmpresaDocumento Código de empresa del documento
     * @param codigoEmpresaCliente Código de empresa del cliente
     */
    private boolean genDiaIng(int codigoEmpresaDocumento, int codigoEmpresaCliente){
        boolean blnRes=true;
        try{
            //EMPRESA TUVAL - CLIENTE CASTEK
            System.out.println("INGRESO");
            System.out.println("codigoEmpresaDocumento:" + codigoEmpresaDocumento);
            System.out.println("codigoEmpresaCliente:" + codigoEmpresaCliente);
            if(codigoEmpresaDocumento==1){//Tuval
                if(codigoEmpresaCliente==2){//Castek                    
                    if(chrGenIva=='S'){//FACTURACION
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3000");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de debe - iva
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "584");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.07.03.01");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "12% IVA COMPRAS");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocIva.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                        
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "4385");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.90");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "Compañias Relacionadas  Castek");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocTot.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                    else{//PRESTAMO - no hay iva
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3000");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3548");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.88");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "CASTEK S.A.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocTot.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);

                    }

                }
        
            }
            
            //EMPRESA TUVAL - CLIENTE DIMULTI
            if(codigoEmpresaDocumento==1){//Tuval
                if(codigoEmpresaCliente==4){//Dimulti                   
                    if(chrGenIva=='S'){
                        //Cuenta de debe - iva
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "584");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.07.03.01");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "12% IVA COMPRAS");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocIva.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3000");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "4386");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.91");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "Compañias Relacionadas  Dimulti");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocTot.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                    else{//PRESTAMO - no hay iva
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3000");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3549");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.89");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "DIMULTI S.A.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocTot.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);

                    }
                }
            }
            
        
            //EMPRESA CASTEK - CLIENTE TUVAL
            if(codigoEmpresaDocumento==2){//Castek
                if(codigoEmpresaCliente==1){//Tuval
                    if(chrGenIva=='S'){
                        //Cuenta de debe - iva
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "94");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.07.03.01");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "12% IVA COMPRAS");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocIva.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1277");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA     GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2110");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.90");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "Compañias Relacionadas  Tuval");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocTot.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    

                    }
                    else{//PRESTAMO - no hay iva
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1277");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1543");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.88");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "TUVAL S.A.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocTot.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);

                    }
                    
                    
                    

                }
            }
    
            //EMPRESA CASTEK - CLIENTE DIMULTI
            if(codigoEmpresaDocumento==2){//Castek
                if(codigoEmpresaCliente==4){//Dimulti
                    
                    if(chrGenIva=='S'){
                        //Cuenta de debe - iva
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "94");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.07.03.01");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "12% IVA COMPRAS");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocIva.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1277");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA     GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2111");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.91");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "Compañias Relacionadas  Dimulti");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocTot.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    

                    }
                    else{//PRESTAMO - no hay iva
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1277");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1544");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.98");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "DIMULTI S.A.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocTot.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    

                    }
                    
                    

                }
            }

            //EMPRESA DIMULTI - CLIENTE TUVAL
            if(codigoEmpresaDocumento==4){//Dimulti
                if(codigoEmpresaCliente==1){//Tuval
                    
                    if(chrGenIva=='S'){
                        //Cuenta de debe - iva
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "545");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.07.03.01");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "12% IVA COMPRAS");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocIva.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2253");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA  GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3062");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.91");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "Compañias Relacionadas  Tuval");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocTot.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    

                    }
                    else{//PRESTAMO - no hay iva
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2253");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2553");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.89");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "TUVAL S.A.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocTot.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);

                    }
                    

                }
            }
            
            //EMPRESA DIMULTI - CLIENTE CASTEK
            if(codigoEmpresaDocumento==4){//Dimulti
                if(codigoEmpresaCliente==2){//Castek
                    
                    if(chrGenIva=='S'){
                        //Cuenta de debe - iva
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "545");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.07.03.01");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "12% IVA COMPRAS");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocIva.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2253");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA  GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3061");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.90");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "Compañias Relacionadas  Castek");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocTot.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    

                    }
                    else{//PRESTAMO - no hay iva
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2253");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDocSub.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2552");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.98");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "CASTEK S.A.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDocTot.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    
                    }

                }
            }
            
            objAsiDia.setDetalleDiario(vecDatDia);
            System.out.println("*detalle diario:  " + objAsiDia.getDetalleDiario());
            objAsiDia.setGeneracionDiario((byte)2);
            System.out.println("***detalle diario:  " + objAsiDia.getDetalleDiario());
        }
        catch(Exception e){
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
            
            System.out.println("****codigoEmpresaDocumento: " + codigoEmpresaDocumento);
            
            
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
     * 
     * @param codigoEmpresa
     * @param codigoBodegaEmpresa
     * @return 
     */
    private int getCodBodGrp(int codigoEmpresa, int codigoBodegaEmpresa){
        Connection conCodBodGrp;
        Statement stmCodBodGrp;
        ResultSet rstCodBodGrp;
        String strCodBodGrp="";
        int intCodBodGrp=-1;
        try{
            conCodBodGrp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conCodBodGrp!=null){
                stmCodBodGrp=conCodBodGrp.createStatement();
                strCodBodGrp="";
                strCodBodGrp+=" SELECT a2.co_bodGrp";
                strCodBodGrp+=" FROM tbm_bod AS a1 INNER JOIN tbr_bodEmpBodGrp AS a2";
                strCodBodGrp+=" ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strCodBodGrp+=" WHERE a1.co_emp=" + codigoEmpresa + " AND a1.co_bod=" + codigoBodegaEmpresa + "";
                rstCodBodGrp=stmCodBodGrp.executeQuery(strCodBodGrp);
                if(rstCodBodGrp.next())
                    intCodBodGrp=rstCodBodGrp.getInt("co_bodGrp");
                
                conCodBodGrp.close();
                conCodBodGrp=null;
                stmCodBodGrp.close();
                stmCodBodGrp=null;
                rstCodBodGrp.close();
                rstCodBodGrp=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return intCodBodGrp;
    }
    
    
    
    
    private boolean getMarUtiMovRel(int tipoOperacion){
        boolean blnRes=false;
        bdeMarUtiComVenRel=BigDecimal.ZERO;
        try{
            System.out.println("****getMarUtiMovRel ");
            System.out.println("intCodEmpEgrGenAsiDia: " + intCodEmpEgrGenAsiDia);
            System.out.println("intCodEmpIngGenAsiDia: " + intCodEmpIngGenAsiDia);
            
            if((tipoOperacion==0) ||  (tipoOperacion==1)){//egreso ||  ingreso
                //VENTA ENTRE RELACIONADAS:
                if( (intCodEmpEgrGenAsiDia==1) && (intCodEmpIngGenAsiDia==2) ){//TUVAL VENDE A CASTEK:
                    bdeMarUtiComVenRel=new BigDecimal("1.02");
                    blnRes=true;
                }
                else if( (intCodEmpEgrGenAsiDia==1) && (intCodEmpIngGenAsiDia==4) ){//TUVAL VENDE A DIMULTI:
                    bdeMarUtiComVenRel=new BigDecimal("1.02");
                    blnRes=true;
                }
                else if( (intCodEmpEgrGenAsiDia==2) && (intCodEmpIngGenAsiDia==1) ){//CASTEK VENDE A TUVAL:
                    bdeMarUtiComVenRel=new BigDecimal("1.05");
                    blnRes=true;
                }
                else if( (intCodEmpEgrGenAsiDia==2) && (intCodEmpIngGenAsiDia==4) ){//CASTEK VENDE A DIMULTI:
                    bdeMarUtiComVenRel=new BigDecimal("1.05");
                    blnRes=true;
                }
                else if( (intCodEmpEgrGenAsiDia==4) && (intCodEmpIngGenAsiDia==1) ){//DIMULTI VENDE A TUVAL:
                    bdeMarUtiComVenRel=new BigDecimal("1.03");
                    blnRes=true;
                }
                else if( (intCodEmpEgrGenAsiDia==4) && (intCodEmpIngGenAsiDia==2) ){//DIMULTI VENDE A CASTEK:
                    bdeMarUtiComVenRel=new BigDecimal("1.02");
                    blnRes=true;
                }
            }
            else if(tipoOperacion==2)//transferencia
                blnRes=true;

            System.out.println("bdeMarUtiComVenRel: " + bdeMarUtiComVenRel);
            
            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    private String getPrintServer(int bodega){
        String strNomPrnSer="";
        try{
            switch (bodega){
                case 1://California
                    strNomPrnSer = "od_califormia";
                    break;
                case 2://Vía Daule
                    strNomPrnSer = "od_dimulti";
                    break;
                case 3://Quito Norte
                    strNomPrnSer = "od_quito";
                    break;
                case 4://Compras locales
                    strNomPrnSer = "od_inmaconsa";
                    break;
                case 5://Manta
                    strNomPrnSer = "od_manta";
                    break;
                case 6://Importaciones
                    strNomPrnSer = "od_inmaconsa";
                    break;
                case 11://Santo Domingo
                    strNomPrnSer = "od_stodgo";
                    break;
                case 15://Inmaconsa
                    strNomPrnSer = "od_inmaconsa";
                    break;
                case 28://Cuenca
                    strNomPrnSer = "od_cuenca";
                    break;
                default:
                    break;
            }
            System.out.println("getPrintServer: " + strNomPrnSer);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return strNomPrnSer;
    }
    
    
    
    

    /**
     * 
     * @param obj                   Objeto ZafParSis
     * @param parent                Objeto Component
     * @param codigoEmpresa         Código de Empresa del documento de la transferencia
     * @param codigoLocal           Código de Local del documento de la transferencia 
     * @param codigoTipoDocumento   Código de Tipo del documento de la transferencia
     * @param codigoDocumento       Código de documento de la transferencia
     * @param codigoBodegaGrupo     Código de Bodega de Grupo
     * @param tipoReporte           <HTML>Tipo de reporte 
     *                                      <BR>"E" para Orden de Egresos
     *                                      <BR>"A" para Orden de Almacenamiento
     *                              </HTML>
     * @param rutaReporte           Ruta del reporte. Ejemplo:   /Reportes/Compras/ZafCom94
     * @param nombreReporte         Nombre del reporte. Ejemplo: /ZafRptCom94_01.jasper
     * @param tituloReporte
     * @param nombrePrintServer 
     */
    public boolean getImpRepConIngEgr(ZafParSis obj, java.awt.Component parent, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento
        , String tipoReporte, String rutaReporte, String nombreReporte, String tituloReporte){
        boolean blnRes=true;
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            cmpPad=parent;
            //Obtener la fecha y hora del servidor.
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MM/yyyy   HH:mm:ss");
            datFecAux=null;            
            
            //Creacion del mapa de parametros
            mapPar=new java.util.HashMap();
            strTipRep=tipoReporte;
            System.out.println("***strTipRep: " + strTipRep);
            
            //strRutRpt=objUti.getDirectorioSistema() + rutaReporte;//
            strRutRpt=rutaReporte;//
            strNomRpt=nombreReporte;
            strTitRpt=tituloReporte;
                        
            intCodEmp=codigoEmpresa;
            intCodLoc=codigoLocal;
            intCodTipDoc=codigoTipoDocumento;
            intCodDoc=codigoDocumento;
            
            generarRpt();
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }    
    
    
    /**
     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(){
        boolean blnRes=true;
        try{
            //Orden de Egreso
            //Orden de Almacenamiento
            System.out.println("***-***strTipRep: " + strTipRep);
            
            strSQLRep="";
            strSQLRep+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc AS co_docTra, a1.ne_numDoc AS ne_numDocTra, a1.fe_doc AS fe_docTra, a4.tx_desCor AS tx_desCorTra";
            strSQLRep+=" , a3.co_emp AS co_empSolTraInv, a3.co_loc AS co_locSolTraInv, a3.co_tipDoc AS co_tipDocSolTraInv, a3.ne_numDoc AS ne_numDocSolTraInv, a5.tx_desCor AS tx_desCorSolTraInv, a3.fe_doc AS fe_docSolTraInv";
            strSQLRep+=" , a3.fe_aut, a3.co_usrAut, a6.tx_usr, a6.tx_nom AS tx_nomUsr";
            strSQLRep+=" ,'" + strTitRpt + "' AS tx_nomRpt, a7.co_bod AS co_bodOrg, a8.tx_nom AS tx_nomBodOrg";
            strSQLRep+=" FROM ((tbm_cabMovInv AS a1 INNER JOIN tbm_loc AS a2";
            strSQLRep+=" 		ON a1.co_emp=a2.co_emp";
            strSQLRep+=" 		INNER JOIN tbm_cabTipDoc AS a4 ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)";
            strSQLRep+=" 	INNER JOIN (tbm_detMovInv AS a7 INNER JOIN tbm_bod AS a8 ON a7.co_emp=a8.co_emp AND a7.co_bod=a8.co_bod)";
            strSQLRep+=" 	ON a1.co_emp=a7.co_emp AND a1.co_loc=a7.co_loc AND a1.co_tipDoc=a7.co_tipDoc AND a1.co_doc=a7.co_doc)";
            strSQLRep+=" LEFT OUTER JOIN ((tbm_cabSolTraInv AS a3 INNER JOIN tbm_cabTipDoc AS a5 ON a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipDoc=a5.co_tipDoc)";
            strSQLRep+=" 		LEFT OUTER JOIN tbm_usr AS a6 ON a3.co_usrAut=a6.co_usr)";
            strSQLRep+=" ON a1.co_empRelCabSolTraInv=a3.co_emp AND a1.co_locRelCabSolTraInv=a3.co_loc AND a1.co_tipDocRelCabSolTraInv=a3.co_tipDoc AND a1.co_docRelCabSolTraInv=a3.co_doc";
            strSQLRep+=" WHERE a1.co_emp=" + intCodEmp + "";
            strSQLRep+=" AND a1.co_loc=" + intCodLoc + "";
            strSQLRep+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
            strSQLRep+=" AND a1.co_doc=" + intCodDoc + "";
            if(strTipRep.equals("E"))
                strSQLRep+=" AND a7.nd_can<0";
            else if(strTipRep.equals("A"))
                strSQLRep+=" AND a7.nd_can>0";
            strSQLRep+=" AND a1.st_reg='A'";
            strSQLRep+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a4.tx_desCor";
            strSQLRep+=" , a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.ne_numDoc , a5.tx_desCor, a3.fe_doc";
            strSQLRep+=" , a3.fe_aut, a3.co_usrAut, a6.tx_usr, a6.tx_nom, a7.co_bod, a8.tx_nom";
            strSQLRep+=";";

            strSQLSubRep="";
            strSQLSubRep+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg, a1.tx_codAlt2, ABS(a1.nd_can) AS nd_can, a1.tx_uniMed, a2.tx_ubi";
            strSQLSubRep+=" FROM tbm_detMovInv AS a1 INNER JOIN tbm_invBod AS a2";
            strSQLSubRep+=" ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod AND a1.co_itm=a2.co_itm";
            strSQLSubRep+=" WHERE a1.co_emp=" + intCodEmp + "";
            strSQLSubRep+=" AND a1.co_loc=" + intCodLoc + "";
            strSQLSubRep+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
            strSQLSubRep+=" AND a1.co_doc=" + intCodDoc + "";
            if(strTipRep.equals("E"))
                strSQLSubRep+=" AND a1.nd_can<0";
            else if(strTipRep.equals("A"))
                strSQLSubRep+=" AND a1.nd_can>0";
            strSQLSubRep+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg, a1.tx_codAlt2, a1.nd_can, a1.tx_uniMed, a2.tx_ubi";
            strSQLSubRep+=" ORDER BY a1.co_reg";
            strSQLSubRep+=";";

            System.out.println("generarRpt-strSQLRep:  " + strSQLRep);
            System.out.println("generarRpt-strSQLSubRep:  " + strSQLSubRep);

            //Inicializar los parametros que se van a pasar al reporte.                                    
            mapPar.put("strSQLRep", strSQLRep);
            mapPar.put("strSQLSubRep", strSQLSubRep);
            mapPar.put("SUBREPORT_DIR", strRutRpt);
            mapPar.put("strFecImp",  strFecHorSer );
            mapPar.put("strCamAudRpt", objParSis.getNombreUsuario() + " - " + strNomRpt + "  ");
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    
    public boolean isImprimirRptEgr(Connection conexion){
        boolean blnRes=true;
        int intCodBodGrpOri=-1;
        try{
            if(conexion!=null){
                intCodBodGrpOri=getCodBodGrp(intCodEmp, intCodBodEgr);
                
                objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
                objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
                jprRepGuiRem =JasperFillManager.fillReport((strRutRpt+strNomRpt), mapPar,  conexion);
                jprNamPriNam=new javax.print.attribute.standard.PrinterName(getPrintServer(intCodBodGrpOri) , null);
                jprSerAttSet=new javax.print.attribute.HashPrintServiceAttributeSet();
                jprSerAttSet.add(jprNamPriNam);
                objJRPSerExpEgr=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                objJRPSerExpEgr.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, jprRepGuiRem);
                objJRPSerExpEgr.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                objJRPSerExpEgr.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, jprSerAttSet);
                objJRPSerExpEgr.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                objJRPSerExpEgr.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                objJRPSerExpEgr.exportReport();
                blnRes=true;
            }
            
            
            
                
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }


    
    public boolean isImprimirRptIng(Connection conexion){
        boolean blnRes=true;
        int intCodBodGrpDes=-1;
        try{
            if(conexion!=null){
                
                intCodBodGrpDes=getCodBodGrp(intCodEmp, intCodBodIng);
                
                objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
                objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
                jprRepGuiRem =JasperFillManager.fillReport((strRutRpt+strNomRpt), mapPar,  conexion);
                jprNamPriNam=new javax.print.attribute.standard.PrinterName(getPrintServer(intCodBodGrpDes) , null);
                jprSerAttSet=new javax.print.attribute.HashPrintServiceAttributeSet();
                jprSerAttSet.add(jprNamPriNam);
                objJRPSerExpIng=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                objJRPSerExpIng.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, jprRepGuiRem);
                objJRPSerExpIng.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                objJRPSerExpIng.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, jprSerAttSet);
                objJRPSerExpIng.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                objJRPSerExpIng.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                
                
                System.out.println("**objJRPSerExpIng: " +objJRPSerExpIng);
                System.out.println("**strRutRpt: " + strRutRpt);
                System.out.println("**strNomRpt: " + strNomRpt);
                System.out.println("**mapPar:" + mapPar);
                System.out.println("**jprNamPriNam: " + jprNamPriNam);
                System.out.println("**jprRepGuiRem:" + jprRepGuiRem);
                System.out.println("**objPriReqAttSet: " + objPriReqAttSet);
                System.out.println("**jprSerAttSet: " + jprSerAttSet);

                objJRPSerExpIng.exportReport();
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }

    public String getDocEgrGen() {
        return strEgrGen;
    }

    public String getDocIngGen() {
        return strIngGen;
    }


    private boolean getBodegasTransfer(){
        boolean blnRes=true;
        intCodBodEgr=-1;
        intCodBodIng=-1;
        try{
            if(arlDatItm.size()>0){
                for(int i=0; i<arlDatItm.size();i++){
                    if((i%2)==0){//es impar - egreso
                        if(intCodBodEgr==-1)
                            intCodBodEgr=Integer.parseInt(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_BOD_ORI_DES));
                    }
                    if((i%2)!=0){//es par  - ingreso
                        if(intCodBodIng==-1)
                            intCodBodIng=Integer.parseInt(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_BOD_ORI_DES));
                    }
                    
                    
                }
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
   
    
    
    /**
     * Función que permite obtener el costo unitario de 
     * @param conexion
     * @param codigoItem
     * @param codigoAlternoItem
     * @param secuenciaGrupo
     * @param secuenciaEmpresa
     * @param cantidad
     * @param codigoEmpresa
     * @param codigoLocal
     * @param codigoTipoDocumento
     * @param codigoDocumento
     * @param codigoRegistro
     * @return 
     */
    private boolean procesarDocumento(Connection conexion, String codigoItem, String codigoAlternoItem, String secuenciaGrupo, String secuenciaEmpresa, BigDecimal cantidad
                                        , String codigoEmpresa, String codigoLocal, String codigoTipoDocumento, String codigoDocumento, String codigoRegistro, int indiceArreglo){
        boolean blnRes=true;
        
        String strCodItm=codigoItem;
        String strCodAltItm=codigoAlternoItem;
        String strSecGrp=secuenciaGrupo;
        String strSecEmp=secuenciaEmpresa;        
        String strCodEmp=codigoEmpresa;
        String strCodLoc=codigoLocal;
        String strCodTipDoc=codigoTipoDocumento;
        String strCodDoc=codigoDocumento;
        String strCodReg=codigoRegistro;
        BigDecimal bdeCan=cantidad;
        
        BigDecimal bdeCosUniDocRel=BigDecimal.ZERO;
        BigDecimal bdeCosProDocRel=BigDecimal.ZERO;
        BigDecimal bdeCosTotDocRel=BigDecimal.ZERO;
        BigDecimal bdeCosUniGrpDocRel=BigDecimal.ZERO;
        BigDecimal bdeCosTotGrpDocRel=BigDecimal.ZERO;
        
        String strSQLProDoc="";
        Statement stmProDoc;
        ResultSet rstProDoc;
        
        try{
            if(conexion!=null){
                conexion.setAutoCommit(false);
                stmProDoc=conexion.createStatement();
                
                
                if(  (strCodTipDoc.equals("228")) || (strCodTipDoc.equals("206"))  || (strCodTipDoc.equals("4"))  ){
//EGRESOS
                    strSQLProDoc="";
                    strSQLProDoc+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc";
                    strSQLProDoc+=" , a1.fe_doc, a1.fe_ing, a2.co_reg, a1.ne_secGrp, a1.ne_secEmp";
                    strSQLProDoc+=" , a2.co_itm, a3.tx_codAlt, a2.nd_can, a2.nd_cosUni, a2.nd_preUni, a2.nd_cosPro";
                    strSQLProDoc+=" , a5.co_itmMae, a1.ne_secGrp, a1.ne_secEmp";
                    strSQLProDoc+=" FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2";
                    strSQLProDoc+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQLProDoc+=" INNER JOIN tbm_inv AS a3";
                    strSQLProDoc+=" ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                    strSQLProDoc+=" INNER JOIN tbm_equInv AS a5";
                    strSQLProDoc+=" ON a3.co_emp=a5.co_emp AND a3.co_itm=a5.co_itm";
                    strSQLProDoc+=" WHERE a1.st_reg='A' /*AND a1.st_conInv NOT IN('F')*/";
                    //strSQLProDoc+=" AND (CASE WHEN a1.st_conInv IS NULL THEN '' ELSE a1.st_conInv END) NOT IN('F')";
                    strSQLProDoc+=" AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                    strSQLProDoc+=" AND a3.tx_codAlt=" + strCodAltItm + "";
                    strSQLProDoc+=" AND a1.ne_secGrp<" + strSecGrp + "";
                    strSQLProDoc+=" AND a1.ne_secEmp<" + strSecEmp + "";
                    strSQLProDoc+=" AND a1.co_emp=" + strCodEmp + "";
                    strSQLProDoc+=" ORDER BY a3.tx_codAlt, a1.fe_doc DESC, a2.nd_can DESC, a1.ne_secGrp DESC, a1.ne_secEmp DESC, a1.fe_doc, a1.fe_ing";
                    strSQLProDoc+=" LIMIT 1";
                }
                else if(   (strCodTipDoc.equals("2"))  ||  (strCodTipDoc.equals("205")) ||  (strCodTipDoc.equals("229"))  ){//INGRESOS
                    strSQLProDoc="";
                    strSQLProDoc+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.ne_numDoc";
                    strSQLProDoc+=" , a2.fe_doc, a2.fe_ing, a2.co_reg, a2.ne_secGrp, a2.ne_secEmp";
                    strSQLProDoc+=" , a2.co_itm, a2.tx_codAlt, a2.nd_can, a2.nd_cosUni, a2.nd_preUni, a2.nd_cosPro";
                    strSQLProDoc+=" , a2.co_itmMae, a2.ne_secGrp, a2.ne_secEmp";
                    strSQLProDoc+=" FROM(";
                    strSQLProDoc+=" 	SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a3.co_reg";
                    strSQLProDoc+=" 	FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2";
                    strSQLProDoc+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQLProDoc+=" 	INNER JOIN tbr_detMovInv AS a3";
                    strSQLProDoc+=" 	ON a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel AND a2.co_tipDoc=a3.co_tipDocRel AND a2.co_doc=a3.co_docRel AND a2.co_reg=a3.co_regRel";
                    strSQLProDoc+=" 	WHERE a1.co_emp=" + strCodEmp + " AND a1.co_loc=" + strCodLoc + "";
                    strSQLProDoc+="   AND a1.co_tipDoc=" + strCodTipDoc + " AND a1.co_doc=" + strCodDoc + "";
                    strSQLProDoc+="   AND a2.tx_codAlt=" + strCodAltItm + "";
                    //strSQLProDoc+="   AND a3.co_tipDoc=206 ";
                    
                    strSQLProDoc+=" AND a3.co_tipDoc=(";
                    strSQLProDoc+="                   CASE WHEN " + strCodTipDoc + "=2 THEN 228";
                    strSQLProDoc+="                        WHEN " + strCodTipDoc + "=205 THEN 206";
                    strSQLProDoc+="                        WHEN " + strCodTipDoc + "=229 THEN 4";
                    strSQLProDoc+="                   END)";
                    
                    strSQLProDoc+=" ) AS a1";
                    strSQLProDoc+=" INNER JOIN(";
                    strSQLProDoc+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc";
                    strSQLProDoc+=" 	, a1.fe_doc, a1.fe_ing, a2.co_reg, a1.ne_secGrp, a1.ne_secEmp";
                    strSQLProDoc+=" 	, a2.co_itm, a3.tx_codAlt, a2.nd_can, a2.nd_cosUni, a2.nd_preUni, a2.nd_cosPro";
                    strSQLProDoc+=" 	, a5.co_itmMae";
                    strSQLProDoc+=" 	FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2";
                    strSQLProDoc+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQLProDoc+=" 	INNER JOIN tbm_inv AS a3";
                    strSQLProDoc+=" 	ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                    strSQLProDoc+=" 	INNER JOIN tbm_equInv AS a5";
                    strSQLProDoc+=" 	ON a3.co_emp=a5.co_emp AND a3.co_itm=a5.co_itm";
                    strSQLProDoc+=" 	WHERE a1.st_reg='A'";
                    strSQLProDoc+=" 	ORDER BY a3.tx_codAlt, a1.ne_secGrp DESC, a1.ne_secEmp DESC, a1.fe_doc, a1.fe_ing";
                    strSQLProDoc+=" ) AS a2";
                    strSQLProDoc+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg";
                    strSQLProDoc+=";";
                }

                System.out.println("procesarDocumento-SQL: " + strSQLProDoc);
                rstProDoc=stmProDoc.executeQuery(strSQLProDoc);
                if(rstProDoc.next()){                    

                    if(   (strCodTipDoc.equals("228"))  || (strCodTipDoc.equals("206"))  ||  (strCodTipDoc.equals("4"))  ){//negativo
                        bdeCosUniDocRel=(new BigDecimal(rstProDoc.getObject("nd_cosUni")==null?"0":(rstProDoc.getString("nd_cosUni").equals("")?"0":rstProDoc.getString("nd_cosUni"))).abs());
                        bdeCosProDocRel=(new BigDecimal(rstProDoc.getObject("nd_cosPro")==null?"0":(rstProDoc.getString("nd_cosPro").equals("")?"0":rstProDoc.getString("nd_cosPro"))).abs());
                        bdeCosTotDocRel=(objUti.redondearBigDecimal((bdeCan.multiply(bdeCosProDocRel)), objParSis.getDecimalesMostrar()).abs()).multiply(new BigDecimal("-1"));
                        bdeCosUniGrpDocRel=(new BigDecimal(rstProDoc.getObject("nd_cosUni")==null?"0":(rstProDoc.getString("nd_cosUni").equals("")?"0":rstProDoc.getString("nd_cosUni"))).abs());
                        bdeCosTotGrpDocRel=(objUti.redondearBigDecimal((bdeCan.multiply(bdeCosUniGrpDocRel)), objParSis.getDecimalesMostrar()).abs()).multiply(new BigDecimal("-1"));
                    }
                    else if(   (strCodTipDoc.equals("2")) || (strCodTipDoc.equals("205")) || (strCodTipDoc.equals("229"))  ){//positivo
                        bdeCosUniDocRel=new BigDecimal(rstProDoc.getObject("nd_cosUni")==null?"0":(rstProDoc.getString("nd_cosUni").equals("")?"0":rstProDoc.getString("nd_cosUni"))).abs();
                        bdeCosProDocRel=new BigDecimal(rstProDoc.getObject("nd_cosPro")==null?"0":(rstProDoc.getString("nd_cosPro").equals("")?"0":rstProDoc.getString("nd_cosPro"))).abs();
                        bdeCosProDocRel=bdeCosUniDocRel;
                        bdeCosTotDocRel=objUti.redondearBigDecimal((bdeCan.multiply(bdeCosProDocRel)), objParSis.getDecimalesMostrar()).abs();
                        bdeCosUniGrpDocRel=new BigDecimal(rstProDoc.getObject("nd_cosUni")==null?"0":(rstProDoc.getString("nd_cosUni").equals("")?"0":rstProDoc.getString("nd_cosUni"))).abs();
                        bdeCosTotGrpDocRel=objUti.redondearBigDecimal((bdeCan.multiply(bdeCosUniGrpDocRel)), objParSis.getDecimalesMostrar()).abs();
                    }
                    

                    System.out.println("COSTO : " + codigoAlternoItem + "/" + bdeCosProDocRel.toString());
                    objUti.setStringValueAt(arlDatItm, indiceArreglo, INT_ARL_COS_UNI, bdeCosProDocRel.toString());
                    
//                    strSQLProDoc="";
//                    strSQLProDoc+=" UPDATE tbm_detMovInv";
//                    strSQLProDoc+=" SET nd_cosUni=" + bdeCosProDocRel + "";
//                    strSQLProDoc+=" , nd_preUni=" + bdeCosProDocRel + "";
//                    strSQLProDoc+=" , nd_cosUniGrp=" + bdeCosProDocRel + "";
//                    strSQLProDoc+=" , nd_cosTot=" + bdeCosTotDocRel + "";
//                    strSQLProDoc+=" , nd_tot=" + bdeCosTotDocRel + "";
//                    strSQLProDoc+=" , nd_cosTotGrp=" + bdeCosTotDocRel + "";
//                    strSQLProDoc+=" WHERE co_emp=" + strCodEmp + "";
//                    strSQLProDoc+=" AND co_loc=" + strCodLoc + "";
//                    strSQLProDoc+=" AND co_tipDoc=" + strCodTipDoc + "";
//                    strSQLProDoc+=" AND co_doc=" + strCodDoc + "";
//                    strSQLProDoc+=" AND co_reg=" + strCodReg + "";
//                    strSQLProDoc+=";";
//                    System.out.println("procesarDocumento-UPDATE: " + strSQLProDoc);
//                    stm.executeUpdate(strSQLProDoc);
                    
                    
                    //objUti.recostearItm2009DesdeFecha(cmpPad, objParSis, conexion, Integer.parseInt(strCodEmp), strCodItm, objUti.formatearFecha(dtpFecDoc, objParSis.getFormatoFechaBaseDatos()), "yyyy-MM-dd");

                }
                
                stmProDoc.close();
                stmProDoc=null;
                rstProDoc.close();
                rstProDoc=null;
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
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(cmpPad,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    private int getCodBodDes_EmpPtoPar(int codigoEmpresaPtoPartida, int codigoBodegaPtoPartida, int codigoEmpresaPtoDes){
        boolean blnRes=true;
        int intCodBod=-1;
        Connection conCodBodBodDes_EmpPtoPar;
        Statement stmBodDes_EmpPtoPar;
        ResultSet rstBodDes_EmpPtoPar;
        String strSQLCodBodBodDes_EmpPtoPar="";
        try{
            conCodBodBodDes_EmpPtoPar=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conCodBodBodDes_EmpPtoPar!=null){
                stmBodDes_EmpPtoPar=conCodBodBodDes_EmpPtoPar.createStatement();
                strSQLCodBodBodDes_EmpPtoPar="";
                strSQLCodBodBodDes_EmpPtoPar+=" SELECT a2.co_bod AS co_bodIng_EmpGenEgr FROM(";//egreso
                strSQLCodBodBodDes_EmpPtoPar+=" 	SELECT *FROM tbr_bodEmpBodGrp AS a1";
                strSQLCodBodBodDes_EmpPtoPar+=" 	WHERE a1.co_emp=" + codigoEmpresaPtoPartida + " AND a1.co_bod=" + codigoBodegaPtoPartida + "";
                strSQLCodBodBodDes_EmpPtoPar+=" ) AS a1";
                strSQLCodBodBodDes_EmpPtoPar+=" INNER JOIN(";//ingreso
                strSQLCodBodBodDes_EmpPtoPar+=" 	SELECT *FROM tbr_bodEmpBodGrp AS a1";
                strSQLCodBodBodDes_EmpPtoPar+=" 	WHERE a1.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " AND a1.co_emp=" + codigoEmpresaPtoDes + "";
                strSQLCodBodBodDes_EmpPtoPar+=" ) AS a2";
                strSQLCodBodBodDes_EmpPtoPar+=" ON a1.co_empGrp=a2.co_empGrp AND a1.co_bodGrp=a2.co_bodGrp";
                rstBodDes_EmpPtoPar=stmBodDes_EmpPtoPar.executeQuery(strSQLCodBodBodDes_EmpPtoPar);
                System.out.println("getCodBodDes_EmpPtoPar: " + strSQLCodBodBodDes_EmpPtoPar);
                if(rstBodDes_EmpPtoPar.next())
                    intCodBod=rstBodDes_EmpPtoPar.getInt("co_bodIng_EmpGenEgr");
                
                conCodBodBodDes_EmpPtoPar.close();
                conCodBodBodDes_EmpPtoPar=null;
                stmBodDes_EmpPtoPar.close();
                stmBodDes_EmpPtoPar=null;
                rstBodDes_EmpPtoPar.close();
                rstBodDes_EmpPtoPar=null;
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
        return intCodBod;
    }

    /***
     * Esta función permite consultar la ruta relativa del sistema del reporte del sistema solicitado.
     * @param intCodRpt Codigo del reporte del sistema que sera consultado los datos
     * @param intTipRut 0=Ruta Completa del Reporte; 1=Ruta Absoluta; 2=Ruta Relativa; 3=Nombre del Reporte.
     * @return strRutRpt retorna 
     * <BR>false: En el caso contrario
     */
    private String getRutaRptSis(int intCodRpt, int intTipRut)
    {
        String strRutRpt="";
        Connection conLoc;
        ResultSet rstLoc;
        Statement stmLoc;
        try
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null)
            {
                stmLoc=conLoc.createStatement(); 
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT a1.co_rpt, a1.tx_desCor, a1.tx_desLar, a1.tx_rutAbsRpt, a1.tx_rutRelRpt, a1.tx_nomRpt, 'A' AS st_reg";
                strSQL+=" FROM tbm_rptSis AS a1";
                strSQL+=" WHERE a1.co_rpt=" + intCodRpt; //460: Orden de Almacenamiento (Nuevo)
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.co_rpt";
                rstLoc=stmLoc.executeQuery(strSQL);
                if (rstLoc.next())
                {
                    switch(intTipRut){
                        case 0:  strRutRpt=rstLoc.getString("tx_rutAbsRpt")+""+rstLoc.getString("tx_rutRelRpt"); break;
                        case 1:  strRutRpt=rstLoc.getString("tx_rutAbsRpt"); break;
                        case 2:  strRutRpt=rstLoc.getString("tx_rutRelRpt"); break;
                        case 3:  strRutRpt=rstLoc.getString("tx_nomRpt");    break;
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
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch (Exception e)  {
            objUti.mostrarMsgErr_F1(cmpPad, e); 
        }
        return strRutRpt;
    }    
    
    
    
    
    
    
    
    
}

