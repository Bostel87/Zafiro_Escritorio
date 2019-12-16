/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafGenDocIngEgrInvRes;


import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafCfgBod.ZafCfgBod;
import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafGetDat.ZafDatBod;
import Librerias.ZafGetDat.ZafDatCli;
import Librerias.ZafGetDat.ZafDatItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
/**
 *
 * @author Sistemas6
 */
public class ZafGenDocIngEgrInvRes {
    
    private ZafParSis objParSis;
    private ZafCorEle objCorEle;
    private ZafUtil objUti;
    private ZafAsiDia objAsiDia;
    
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
    private Date dtpFecDoc;
    
    private ArrayList arlDatItm, arlRegItm;
    
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
    private static final int INT_ARL_COD_SEG=18;    /* Seguimiento */
     
    
    private java.awt.Component cmpPad;
    public String strVer=" v0.05", strSql;
    private int intNumDoc,intCodDoc,intCodReg;
    private BigDecimal bdeMon;
    
    private static final int INT_CON_ING=0;     // Parametro de Ingreso 
    private static final int INT_CON_EGR=1;     // Parametro de Egreso 
    
    private BigDecimal bdeSig;
    private int intNumSecEmp;
    private int intNumSecGrp;
    private String strTipMovSeg = "";
    private int intCodTipDocIns=0;
    private String strCorEleTo="sistemas6@tuvalsa.com"; 
    private String strTitCorEle="Sistemas: Reservas"; 
    
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
     *              <BR> 11: Actualiza en campo "nd_canResVen"
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
    
    
    private ZafDatItm objDatItm;    // Nueva libreria Jota 4/Julio/2017
    private ZafDatCli objDatCli;    // Nueva libreria Jota 5/Julio/2017
    private ZafDatBod objDatBod;    // Nueva libreria Jota 5/Julio/2017
    
    /**
     * Función que permite obtener el objeto ZafParSis
     * @param obj El objeto ZafParSis
     */
    public ZafGenDocIngEgrInvRes(ZafParSis obj, java.awt.Component parent){
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            cmpPad=parent;
            objAsiDia = new ZafAsiDia(objParSis);
            vecDatDia=new Vector();
            objCorEle = new ZafCorEle(objParSis);
            System.out.println(strVer);
            objDatItm = new ZafDatItm(objParSis,cmpPad);
            objDatCli = new ZafDatCli(objParSis,cmpPad);
            objDatBod = new ZafDatBod(objParSis,cmpPad);
            objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);
            objGenOD = new GenOD.ZafGenOdPryTra();
        }
        catch (CloneNotSupportedException e){
            System.out.println("ZafMovInv: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
    }   
     
    /**
     * obtenerDatosItems
     * Metodo para obtener todos los datos a insertar
     * @param arlItm
     * @return 
     */
    
    private boolean obtenerDatosItems(ArrayList arlItm, int configuracioEgresoIngreso){
        boolean blnRes=false;
        try{
            
            for(int i=0;i<arlItm.size();i++){
                arlRegItm = new ArrayList();
                arlRegItm.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlItm, i, INT_ARL_DAT_COD_EMP));
                arlRegItm.add(INT_ARL_COD_LOC, objUti.getIntValueAt(arlItm, i, INT_ARL_DAT_COD_LOC));
                arlRegItm.add(INT_ARL_COD_TIP_DOC, "");
                arlRegItm.add(INT_ARL_COD_CLI_PRV, "");
                arlRegItm.add(INT_ARL_COD_BOD_ORI_DES, objUti.getIntValueAt(arlItm, i, INT_ARL_DAT_COD_BOD_EMP));
                
                arlRegItm.add(INT_ARL_COD_ITM_GRP, objDatItm.getCodigoItemGrupo(objUti.getIntValueAt(arlItm, i, INT_ARL_DAT_COD_EMP), objUti.getIntValueAt(arlItm, i, INT_ARL_DAT_COD_ITM_EMP) ) );
                arlRegItm.add(INT_ARL_COD_ITM_EMP, objUti.getIntValueAt(arlItm, i, INT_ARL_DAT_COD_ITM_EMP));
                arlRegItm.add(INT_ARL_COD_ITM_MAE, objDatItm.getCodigoMaestroItem(objUti.getIntValueAt(arlItm, i, INT_ARL_DAT_COD_EMP), objUti.getIntValueAt(arlItm, i, INT_ARL_DAT_COD_ITM_EMP) )  );
                arlRegItm.add(INT_ARL_COD_ALT_ITM, objDatItm.getCodigoAlternoItem(objUti.getIntValueAt(arlItm, i, INT_ARL_DAT_COD_EMP), objUti.getIntValueAt(arlItm, i, INT_ARL_DAT_COD_ITM_EMP) )  );
                arlRegItm.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlItm, i, INT_ARL_DAT_NOM_ITM)   );
                arlRegItm.add(INT_ARL_UNI_MED_ITM, objDatItm.getUnidadMedidaItem(objUti.getIntValueAt(arlItm, i, INT_ARL_DAT_COD_EMP), objUti.getIntValueAt(arlItm, i, INT_ARL_DAT_COD_ITM_EMP) )  );
                arlRegItm.add(INT_ARL_COD_LET_ITM, objDatItm.getCodigoLetraItem(objUti.getIntValueAt(arlItm, i, INT_ARL_DAT_COD_EMP), objUti.getIntValueAt(arlItm, i, INT_ARL_DAT_COD_ITM_EMP)) );
                
                arlRegItm.add(INT_ARL_CAN_ITM,objUti.getDoubleValueAt(arlItm, i, INT_ARL_DAT_CAN));
                 
                arlRegItm.add(INT_ARL_COS_UNI,objUti.getDoubleValueAt(arlItm, i, INT_ARL_DAT_COS_UNI));
                arlRegItm.add(INT_ARL_PRE_UNI,objUti.getDoubleValueAt(arlItm, i, INT_ARL_DAT_COS_UNI));
                
                arlRegItm.add(INT_ARL_PES_UNI,objDatItm.getPesoItem(objUti.getIntValueAt(arlItm, i, INT_ARL_DAT_COD_EMP), objUti.getIntValueAt(arlItm, i, INT_ARL_DAT_COD_ITM_EMP) )  );
                arlRegItm.add(INT_ARL_IVA_COM_ITM,"N");
                if(configuracioEgresoIngreso==0){
                    arlRegItm.add(INT_ARL_EST_ING_EGR_MER_BOD,"N");
                }
                else{
                    arlRegItm.add(INT_ARL_EST_ING_EGR_MER_BOD,"S");
                }
                arlRegItm.add(INT_ARL_COD_SEG,objUti.getIntValueAt(arlItm, i, INT_ARL_DAT_COD_SEG));
                
                arlDatItm.add(arlRegItm);
            }
            blnRes=true;
        }
        catch(Exception  e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
 
    /**
     * Metodo a ser llamado por las confirmaciones para generar los docuemntos de movimiento de material en reservas
     * @param conExt
     * @param dtpFechaDocumento
     * @param arlDat (INT_ARL_DAT_COD_EMP,INT_ARL_DAT_COD_LOC,INT_ARL_DAT_COD_BOD_EMP,INT_ARL_DAT_COD_ITM_EMP,INT_ARL_DAT_CAN,INT_ARL_DAT_COS_UNI,INT_ARL_DAT_COD_SEG)
     * @param intCodCfgIngEgr 0 Ingresos, 1 Egresos (No Hay MAS)
     * @return 
     */
    public boolean GenerarDocumentoIngresoEgresoReservas(java.sql.Connection conExt,  Date dtpFechaDocumento, ArrayList arlDat, int intCodCfgIngEgr){
        boolean blnRes=false;
        try{
            System.out.println("GenerarDocumentoIngresoEgresoReservas:: "  + arlDat.toString());
            arlDatItm = new ArrayList();
            if(conExt!=null){
                if(dtpFechaDocumento!=null){
                    if(arlDat!=null){
                        if(intCodCfgIngEgr==0 || intCodCfgIngEgr==1){
                            if(obtenerDatosItems(arlDat,intCodCfgIngEgr)){
                                if(generaDocumentoIngresoEgreso(conExt, dtpFechaDocumento, arlDatItm, intCodCfgIngEgr)){
                                    blnRes=true;
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(Exception  e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    
    

    /**
     * Clase creada para generar Documentos de Egreso por reserva
     * 
     * @param intCodCfgIngEgr: 0 Ingresos 1 Egresos 
     * @param conn
     * @param intCodSeg
     * @return 
     */
    private GenOD.ZafGenOdPryTra objGenOD;

    private boolean generaDocumentoIngresoEgreso(java.sql.Connection conExt,  Date dtpFechaDocumento, ArrayList arlItm, int intCodCfgIngEgr){
        boolean blnRes=false;
        try{
            if(conExt!=null){
                System.out.println("generaDocumentoIngresoEgreso: "+arlItm.toString());
                System.out.println("intCodCfgIngEgr: "+ intCodCfgIngEgr +" +++ 0 Ingreso 1 Egreso");
                if(obtenerTipoDocumentoAGenerar(conExt,arlItm,intCodCfgIngEgr)){
                    if(iniDia()){
                        if(insertarCabDoc(conExt,dtpFechaDocumento,intCodCfgIngEgr ,arlItm)){
                            if(insertarDetDoc(conExt,intCodCfgIngEgr,arlItm)){
                                if(insertarDocumentoSeguimiento(conExt,arlItm)){
                                    if(insertarTablaRelacionadas(conExt,intCodCfgIngEgr,objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_EMP), objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_LOC),intCodTipDocIns,intCodDoc,objUti.getIntValueAt(arlItm, 0,INT_ARL_COD_SEG))){
                                        if(modificaInvBod(conExt,intCodCfgIngEgr, arlItm)){
                                            if(genDiaTrs(objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_EMP), intCodCfgIngEgr)){
                                                if(objAsiDia.insertarDiario(conExt,  objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_EMP), objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_LOC), intCodTipDocIns, String.valueOf(intCodDoc), String.valueOf(intNumDoc), dtpFechaDocumento)){
                                                    if(revisarInvBodNegativos(conExt,intCodCfgIngEgr,arlItm)){
                                                        if(intCodCfgIngEgr==1){
                                                            if(prepararEgreso(conExt, objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_EMP), objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_LOC), intCodTipDocIns, intCodDoc )){
                                                                if(objUti.costearDocumento(cmpPad, objParSis, conExt, objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_EMP), objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_LOC), intCodTipDocIns, intCodDoc)){
                                                                    System.out.println("antes od");
                                                                    if(objGenOD.generarODxEgrResv(conExt,  objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_EMP), objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_LOC), intCodTipDocIns, intCodDoc, true)){
                                                                        System.err.println("generaDocumentoEgreso Reservas::: xD ");
                                                                        blnRes=true;
                                                                    }
                                                                }
                                                                
                                                            }

                                                        }else{
                                                            System.err.println("generaDocumentoIngreso Reservas::: xD ");
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
        }
        catch(Exception  e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
     
    
    
    
        private boolean prepararEgreso(java.sql.Connection conn,  int CodEmp, int CodLoc, int CodTipDoc, int CodDoc){
            java.sql.Statement stmLoc,stmLoc01;
            java.sql.ResultSet rstLoc,rstLoc01;
            boolean blnRes=true, blnCanNecSerCon=false, blnIsServicio=false, blnIsSer=false, blnIsMismaBodega=false,blnIsRes=false;
            double dblCanRem=0.00 , dblCanFac=0.00, dblCanEgrBod=0;
            int intCodBodIng=0, intCodBodEgr=0, intCodBodGrp;
            String strCodAlt="",strItmSer="",strCadena;
            try{
                if(conn!=null){
                     
                    stmLoc = conn.createStatement();
                    stmLoc01 = conn.createStatement();
 
                    arlDatIng = obtenerCantidadRemota(conn, CodEmp, CodLoc, CodTipDoc, CodDoc);
 
                    
                    strSql="";
                    strSql+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg,a2.co_bod, a2.co_itm,a2.tx_codAlt, a2.nd_can  \n";
                    strSql+=" FROM tbm_cabMovInv AS a1  \n";
                    strSql+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND \n";
                    strSql+="                                    a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)    \n";
                    strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_tipDoc="+CodTipDoc+" AND a1.co_doc="+CodDoc+" ORDER BY a2.co_reg \n";
                    System.out.println("J: " + strSql);
                    rstLoc=stmLoc.executeQuery(strSql);     
                    while(rstLoc.next()){
                        strCodAlt = rstLoc.getString("tx_codAlt");
                        dblCanFac=(rstLoc.getDouble("nd_can")*-1);
                        
                        String strTerminal=strCodAlt.substring(strCodAlt.length()-1);
                        int intBodGrp = intBodGrp(CodEmp, rstLoc.getInt("co_bod"));
                        
                        String strCfgCon = objUti.getCfgConfirma(cmpPad, objParSis,  objParSis.getCodigoEmpresaGrupo(), intBodGrp, strTerminal);                    
                        if(strCfgCon.equals("S") ){
                            blnCanNecSerCon=true;
                        }else{
                            blnCanNecSerCon=false; /* NO NECESITA CONFIRMACION */ 
                        }
                          
                        intCodBodGrp=bodegaPorEmpresaGrupo(rstLoc.getInt("co_emp"), rstLoc.getInt("co_bod"));  
                        dblCanRem=0.00;
                        for(int i=0;i<arlDatIng.size();i++){
                            if(rstLoc.getInt("co_reg")==objUti.getIntValueAt(arlDatIng, i,INT_ARL_COD_REG_REL_ING)){//PRIMERO ENCONTRAMOS EL REGISTRO DE LA RELACION
                                intCodBodEgr = objUti.getIntValueAt(arlDatIng, i,INT_ARL_BOD_EGR_ING);
                                intCodBodIng = objUti.getIntValueAt(arlDatIng, i,INT_ARL_BOD_ING_ING);
                                if(intCodBodEgr==intCodBodIng){
                                    blnIsMismaBodega=true;
                                }
                                else{
                                    dblCanRem+=objUti.getDoubleValueAt(arlDatIng, i, INT_ARL_CAN_ITM_ING); // LA CANTIDAD DEL INGRESO
                                }
                            }
                        }
                        dblCanEgrBod = dblCanFac - dblCanRem;
                        System.out.println("ITEMS: "+strCodAlt+"-"+"CANFAC: "+dblCanFac+"-CAN.REM.:"+dblCanRem+"-CAN.EGRE.BOD.:"+dblCanEgrBod );
                       
                        if(intCodBodGrp==15 || intCodBodGrp==3){   /* DOS AMBIENTES ===> INMACONSA  */  
                            if(blnCanNecSerCon){
                                strSql= " UPDATE tbm_detMovInv SET nd_canEgrBod="+((dblCanEgrBod)*-1)+"  ";
                                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_tipDoc="+CodTipDoc+" AND co_doc="+CodDoc+" AND co_reg="+rstLoc.getInt("co_reg")+"; ";
                                strSql+=" UPDATE tbm_detMovInv SET nd_canDesEntCli="+((dblCanRem)*-1)+", nd_canPen="+(dblCanFac*-1)+"  ";
                                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_tipDoc="+CodTipDoc+" AND co_doc="+CodDoc+" AND co_reg="+rstLoc.getInt("co_reg")+"; ";
                                System.out.println("prepararEgreso..DOS AMBIENTES " + strSql);
                                stmLoc01.executeUpdate(strSql);
                            }
                            dblCanRem=0;  
                            dblCanEgrBod=0;
                            blnIsMismaBodega=false;
                        }
                        else{  /*  BODEGAS QUE SOLO POSEEN UN AMBIENTE  -- QUITO - MANTA - SANTA DOMINGO --> CALIFORNIA - VIA DAULE--- */
                            if(blnCanNecSerCon){
                                strSql= " UPDATE tbm_detMovInv SET nd_canEgrBod="+(dblCanFac*-1)+", nd_canPen="+(dblCanFac*-1)+"  ";
                                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_tipDoc="+CodTipDoc+" AND co_doc="+CodDoc+" AND co_reg="+rstLoc.getInt("co_reg")+" ; ";
                                System.out.println("prepararEgreso. 2 . (UN AMBIENTE) " + strSql);
                                stmLoc01.executeUpdate(strSql);
                            }
                            dblCanRem=0;  
                        }
                    }
                    stmLoc.close();
                    stmLoc01.close();
                    rstLoc.close();
                    stmLoc=null;
                    stmLoc01=null;
                    rstLoc=null;
                }
            }
            catch (SQLException Evt) {
                blnRes = false;
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(cmpPad, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(cmpPad, Evt);
            }
            return blnRes;
        }
           
        
        private ArrayList arlRegIng;
        private static final int INT_ARL_COD_EMP_ING=0;
        private static final int INT_ARL_COD_LOC_ING=1; 
        private static final int INT_ARL_COD_TIP_DOC_ING=2;
        private static final int INT_ARL_COD_DOC_ING=3; 
        private static final int INT_ARL_COD_REG_ING=4;
        private static final int INT_ARL_COD_REG_REL_ING=5; 
        private static final int INT_ARL_COD_ITM_ING=6;
        private static final int INT_ARL_CAN_ITM_ING=7;
        private static final int INT_ARL_BOD_ING_ING=8;  /* Bodega de Ingreso */
        private static final int INT_ARL_BOD_EGR_ING=9;  /* Bodega de Egreso */
        private static final int INT_ARL_CAN_UTI_ING=10;  /* Cantidad Utilizada */ 
    
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
        java.sql.Statement stmLoc; 
        java.sql.ResultSet rstLoc;
        try{
            if(con!=null){
                arlDatIng = new ArrayList();
                stmLoc=con.createStatement();
                strSql="";/* LOS INGRESOS AGRUPADOS POR ITEM */
                strSql+=" SELECT a2.co_emp , a2.co_loc, a2.co_tipDoc, a2.co_doc,a8.co_bodGrp as co_bodIng, a7.co_bodEgr,  \n";
                strSql+="        a3.co_reg,a6.co_reg as coRegRel,a3.co_itm, a3.nd_can \n";
                strSql+=" FROM tbm_cabMovInv as a2  /*EL INGRESO */  \n";
                strSql+=" INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND   \n";
                strSql+="                                    a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
                strSql+=" INNER JOIN tbr_detMovInv as a5 ON (a3.co_emp=a5.co_empRel AND a3.co_loc=a5.co_locRel AND a3.co_tipDoc=a5.co_tipDocRel \n";
                strSql+="                                    AND a3.co_doc=a5.co_docRel AND a3.co_reg=a5.co_regRel)  \n";
                strSql+=" INNER JOIN tbm_detMovInv as a6 ON (a5.co_emp=a6.co_emp AND a5.co_loc=a6.co_loc AND a5.co_tipDoc=a6.co_tipDoc AND  \n";
                strSql+="                                    a5.co_doc=a6.co_doc AND a5.co_reg=a6.co_reg)/*LA FACTURA*/  \n";
                strSql+=" LEFT OUTER JOIN ( \n";
                strSql+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a4.co_bodGrp as co_bodEgr, a1.co_reg,  \n";
                strSql+="                  a3.co_emp  as co_empIng, a3.co_loc as co_locIng, a3.co_tipDoc as co_tipDocIng,  \n";
                strSql+="                  a3.co_doc as co_docIng,a3.co_reg as co_regIng\n";
                strSql+="           FROM tbm_detMovInv AS a1 \n";
                strSql+="           INNER JOIN tbr_detMovInv AS a2 ON (a1.co_emp=a2.co_Emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND \n";
                strSql+="                                               a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg) \n";
                strSql+="           INNER JOIN (	 \n";
                strSql+="                       SELECT a3.co_emp , a3.co_loc, a3.co_tipDoc, a3.co_doc ,a3.co_reg   \n";
                strSql+="                       FROM tbm_detMovInv as a3   \n";
                strSql+="                       INNER JOIN tbr_detMovInv as a5 ON (a3.co_emp=a5.co_empRel AND a3.co_loc=a5.co_locRel AND  \n";
                strSql+="                                                          a3.co_tipDoc=a5.co_tipDocRel AND a3.co_doc=a5.co_docRel   \n";
                strSql+="                                                           AND a3.co_reg=a5.co_regRel) \n";
                strSql+="                       WHERE  a5.co_emp="+CodEmp+" AND a5.co_loc="+CodLoc+" AND a5.co_tipDoc="+CodTipDoc+" AND a5.co_doc="+CodDoc+"  \n";
                strSql+="           ) as a3 ON (a2.co_empRel=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_tipDocRel=a3.co_tipDoc AND \n";
                strSql+="                        a2.co_docRel=a3.co_doc AND a2.co_regRel=a3.co_reg) \n";
                strSql+="           INNER JOIN tbr_bodEmpBodGrp AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_bod=a4.co_bod AND a4.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+") \n";
                strSql+="           WHERE NOT (a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+"  AND a1.co_tipDoc="+CodTipDoc+" AND a1.co_doc="+CodDoc+" ) \n";
                strSql+=" ) as a7 ON (a3.co_emp=a7.co_empIng AND a3.co_loc=a7.co_locIng AND a3.co_tipDoc=a7.co_tipDocIng AND \n";
                strSql+="             a3.co_doc=a7.co_docIng AND a3.co_reg=a7.co_regIng)  \n";
                strSql+=" INNER JOIN tbr_bodEmpBodGrp AS a8 ON (a3.co_emp=a8.co_emp AND a3.co_bod=a8.co_bod AND a8.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" )\n";
                strSql+=" WHERE a6.co_emp="+CodEmp+" AND a6.co_loc="+CodLoc+" AND a6.co_tipDoc="+CodTipDoc+" AND a6.co_doc="+CodDoc+"  \n";
                strSql+="       AND a3.nd_can > 0  \n";
                strSql+=" GROUP BY a6.co_reg, a2.co_emp , a2.co_loc, a2.co_tipDoc,  a2.co_doc,a8.co_bodGrp,a7.co_bodEgr,a3.co_reg,a3.co_reg, a3.co_itm, a3.nd_can \n";
                strSql+=" ORDER BY a6.co_reg \n"; // IMPORTANTE ORDENADO POR LOS REGISTROS DE LA FACTURA                 
                System.out.println("obtenerCantidadRemota:... \n" + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    arlRegIng=new ArrayList();
                    arlRegIng.add(INT_ARL_COD_EMP_ING,rstLoc.getInt("co_emp")); // (DATOS DEL INGRESO)
                    arlRegIng.add(INT_ARL_COD_LOC_ING,rstLoc.getInt("co_loc"));
                    arlRegIng.add(INT_ARL_COD_TIP_DOC_ING,rstLoc.getInt("co_tipDoc"));
                    arlRegIng.add(INT_ARL_COD_DOC_ING,rstLoc.getInt("co_doc"));
                    arlRegIng.add(INT_ARL_COD_REG_ING,rstLoc.getInt("co_reg"));
                    arlRegIng.add(INT_ARL_COD_REG_REL_ING,rstLoc.getInt("coRegRel")); // RELACIONAL (LA FACTURA)
                    arlRegIng.add(INT_ARL_COD_ITM_ING,rstLoc.getInt("co_itm"));
                    arlRegIng.add(INT_ARL_CAN_ITM_ING,rstLoc.getDouble("nd_can"));
                    arlRegIng.add(INT_ARL_BOD_ING_ING,rstLoc.getInt("co_bodIng"));  /* JoséMario 12/Sep/2016 */
                    arlRegIng.add(INT_ARL_BOD_EGR_ING,rstLoc.getInt("co_bodEgr"));  /* JoséMario 12/Sep/2016 */
                    arlRegIng.add(INT_ARL_CAN_UTI_ING,0.00);
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
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch (Exception e){         
            System.err.println("ERROR: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return arlDatIng;
    }
        
    
    private int bodegaPorEmpresaGrupo(int intCodEmp, int intCodBodEmp){
        int intCodBodGrp=0;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        String strCadena; 
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strCadena="";
                strCadena+=" SELECT co_emp, co_bod, co_empGrp, co_bodGrp  ";
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
        catch(Exception Evt) { 
            objUti.mostrarMsgErr_F1(cmpPad, Evt);  
            intCodBodGrp=0;
        }  
        return intCodBodGrp;
    }
        
        
    private int bodegaPredeterminada(int intCodEmp, int intCodLoc){
        int intBodPre=0;
        String strSql;
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
                    intBodPre = rstLoc.getInt("co_bod");
                }
                strSql="";
                strSql="";
                conLoc.close();
                conLoc=null;
                rstLoc.close();
                stmLoc.close();
                stmLoc = null;
                rstLoc = null;
            }
        }
        catch(java.sql.SQLException Evt){ 
            objUti.mostrarMsgErr_F1(cmpPad, Evt); 
            intBodPre=0;
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(cmpPad, Evt);  
            intBodPre=0;
        }
        return intBodPre;
    }
    
    
    
    /**
     * Metodo para modifical el Stock de Inventario, disponible, de la mercaderia
     * @param conExt
     * @param ConfiguracioIngresoEgreso 0 Ingreso 1 Egreso 
     * @param arlItm
     * @return 
     */
    
    private boolean modificaInvBod(java.sql.Connection conExt, int ConfiguracioIngresoEgreso, ArrayList arlItm){
        boolean blnRes=false;
        try{
            if(conExt!=null){
                if(ConfiguracioIngresoEgreso==0){
                    if(generaMovimientoInventarioIngreso(conExt,arlItm )){
                        blnRes=true;
                    }
                }
                else{
                     if(generaMovimientoInventarioEgreso(conExt,arlItm)){
                         blnRes=true;
                     }
                }
            }
        }
        catch(Exception  e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    private ZafStkInv objStkInv;
    
    
    /**
     * Metodo para revisar que los items del egreso no generen un negativo
     * @param conExt
     * @param CodigoConfiguracionIngresoEgreso
     * @param arlItm
     * @return 
     */
    
     private boolean revisarInvBodNegativos(java.sql.Connection conExt,int CodigoConfiguracionIngresoEgreso, ArrayList arlItm){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                if(CodigoConfiguracionIngresoEgreso==1){
                    stmLoc = conExt.createStatement();
                    for(int i=0;i<arlItm.size();i++){
                        strSql="";
                        strSql+=" SELECT a1.nd_stkAct,a1.nd_canDis,a1.nd_canRes \n ";
                        strSql+=" FROM tbm_invBod as a1 \n";
                        strSql+=" INNER JOIN tbm_inv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                        strSql+=" WHERE a1.co_emp="+objUti.getStringValueAt(arlItm, i,INT_ARL_COD_EMP)+" AND \n";
                        strSql+="       a1.co_bod="+objUti.getStringValueAt(arlItm, i,INT_ARL_COD_BOD_ORI_DES)+" AND \n";
                        strSql+="       a1.co_itm="+objUti.getStringValueAt(arlItm, i,INT_ARL_COD_ITM_EMP)+" AND \n";
                        strSql+="       a2.st_ser='N' AND (a2.tx_codAlt like '%I' OR a2.tx_codAlt like '%S')    \n";
                        strSql+=" ";
                        System.out.println("revisarInvBodNegativos ::: \n" + strSql);
                        rstLoc = stmLoc.executeQuery(strSql);
                        if(rstLoc.next()){
                            if(rstLoc.getDouble("nd_stkAct")<0){
                                objCorEle.enviarCorreoMasivo(strCorEleTo,strTitCorEle+" SEGUIMIENTO: " + objUti.getStringValueAt(arlItm, i,INT_ARL_COD_SEG)+"NO SE GENERO... GENERARIA NEGATIVOS PILAS!!!  ",strSql );
                                blnRes=false;
                            }
                        }
                        rstLoc.close();
                        rstLoc=null;
                    }
                    stmLoc.close();
                    stmLoc=null;
                }
                else{
                    blnRes=true;
                }
            }
        }
        catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(cmpPad, Evt);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(cmpPad, Evt);
        }
         return blnRes;
    }
    
    
    private boolean generaMovimientoInventarioIngreso(java.sql.Connection con, ArrayList arlItm){
        boolean blnRes=false;
        try{
            System.out.println("generaMovimientoInventarioIngreso.......... ");
            if(generaNuevoContenedorItemsIngreso(arlItm)){
                if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_STK_ACT, "+", 0, arlDatStkInvItm)){
                    if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_CAN_RES, "+", 1, arlDatStkInvItm)){
                        blnRes=true;
                    }else{blnRes=false;}
                }else{blnRes=false;}
            }else{blnRes=false;}
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(cmpPad, e);
                System.err.println("ERROR: " + e.toString());
                blnRes=false;
        }
        return blnRes;
    }
    
    private boolean generaMovimientoInventarioEgreso(java.sql.Connection con, ArrayList arlItm){
        boolean blnRes=false;
        try{
            System.out.println("generaMovimientoInventarioEgreso.......... ");  
            if(generaNuevoContenedorItemsEgreso(arlItm)){
                if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_STK_ACT, "-", 0, arlDatStkInvItm)){
                    if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_CAN_RES, "-",1, arlDatStkInvItm)){
                        if(obtenerDatosEgreso(con, arlItm)){
                            blnRes=true;
                        }else{blnRes=false;}
                    }else{blnRes=false;}
                }else{blnRes=false;}
            }else{blnRes=false;}
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(cmpPad, e);
                System.err.println("ERROR: " + e.toString());
                blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    /**
     *      Genera Nuevo Contenedor:
     *          nuevo contenedor de Items
     * 
     * @return 
     */
    private ArrayList arlRegStkInvItm, arlDatStkInvItm;
    /* NUEVO CONTENEDOR PARA ITEMS */
    private int intCodEmpMovStk;
    
    private static final int INT_STK_INV_COD_ITM_GRP=0;
    private static final int INT_STK_INV_COD_ITM_EMP=1;
    private static final int INT_STK_INV_COD_ITM_MAE=2;    
    private static final int INT_STK_INV_COD_LET_ITM=3;     
    private static final int INT_STK_INV_CAN_ITM=4;
    private static final int INT_STK_INV_COD_BOD_EMP=5; 
    
    private boolean generaNuevoContenedorItemsIngreso(ArrayList arlItm ){
        boolean blnRes=true;
        double dblAux;
        try{
           
            arlDatStkInvItm = new ArrayList();
            for(int i=0;i<arlItm.size();i++){
                arlRegStkInvItm = new ArrayList();
                intCodEmpMovStk=objUti.getIntValueAt(arlItm, i, INT_ARL_COD_EMP);
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_GRP,objUti.getStringValueAt(arlItm, i,INT_ARL_COD_ITM_GRP) );
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_EMP,objUti.getStringValueAt(arlItm, i,INT_ARL_COD_ITM_EMP) );
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_MAE,objUti.getStringValueAt(arlItm, i,INT_ARL_COD_ITM_MAE) );
                arlRegStkInvItm.add(INT_STK_INV_COD_LET_ITM,objUti.getStringValueAt(arlItm, i,INT_ARL_COD_LET_ITM) );
                dblAux=Double.parseDouble(objUti.getStringValueAt(arlItm, i,INT_ARL_CAN_ITM));
                if(dblAux<0){
                    dblAux=dblAux*-1;
                }
                arlRegStkInvItm.add(INT_STK_INV_CAN_ITM,dblAux );
                arlRegStkInvItm.add(INT_STK_INV_COD_BOD_EMP,objUti.getStringValueAt(arlItm, i,INT_ARL_COD_BOD_ORI_DES) );
                arlDatStkInvItm.add(arlRegStkInvItm);
            }
            
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(cmpPad, e);
                blnRes=false;
        }
        return blnRes;
    } 
    
    
    
       private boolean generaNuevoContenedorItemsEgreso(ArrayList arlItm){
        boolean blnRes=true;
        double dblAux;
        try{
            arlDatStkInvItm = new ArrayList();
            for(int i=0;i<arlItm.size();i++){
                arlRegStkInvItm = new ArrayList();
                
                intCodEmpMovStk=objUti.getIntValueAt(arlItm, i, INT_ARL_COD_EMP);
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_GRP,objUti.getStringValueAt(arlItm, i,INT_ARL_COD_ITM_GRP) );
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_EMP,objUti.getStringValueAt(arlItm, i,INT_ARL_COD_ITM_EMP) );
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_MAE,objUti.getStringValueAt(arlItm, i,INT_ARL_COD_ITM_MAE) );
                arlRegStkInvItm.add(INT_STK_INV_COD_LET_ITM,objUti.getStringValueAt(arlItm, i,INT_ARL_COD_LET_ITM) );
                dblAux=Double.parseDouble(objUti.getStringValueAt(arlItm, i,INT_ARL_CAN_ITM));
                if(dblAux<0){
                    dblAux=dblAux*-1;
                }
                arlRegStkInvItm.add(INT_STK_INV_CAN_ITM,dblAux );
                arlRegStkInvItm.add(INT_STK_INV_COD_BOD_EMP,objUti.getStringValueAt(arlItm, i,INT_ARL_COD_BOD_ORI_DES) );
                arlDatStkInvItm.add(arlRegStkInvItm);
            }
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(cmpPad, e);
                blnRes=false;
        }
        return blnRes;
    } 
    
      
       
    
    /**
     * Segun el tipo de movimiento sea ingreso o Egreso sabremos si se confirma o no la mercaderia
     * 
     * @param arlItm
     * @param ingEgr 0 Ingreso 1 Egreso
     * @return 
     */
    private int intCodEmpCot,intCodLocCot,intCodDocCot;
    private boolean obtenerTipoDocumentoAGenerar(java.sql.Connection conExt, ArrayList arlItm, int ingEgr){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                
//1;4;293;"INBOPF";"Ingresos a Bodega (Prefacturas)"
//1;4;294;"EGBOPF";"Egresos de Bodega (Prefacturas)"

                if(ingEgr==0){  /* Configuracion para Ingresos */
                    intCodTipDocIns=293; 
                    strMerIngEgrFisBod="N";
                    objIsGenOD=null;
                    strTipMovSeg="I";
                }else{  /* Configuracion para EGRESOS */
                    intCodTipDocIns=294; 
                    strMerIngEgrFisBod="S";
                    objIsGenOD="S";
                    strTipMovSeg="E";
                }
                strSql="";
                strSql+=" SELECT co_empRelCabCotVen, co_locRelCabCotVen, co_cotRelCabCotVen  \n";
                strSql+=" FROM tbm_cabSegMovInv  \n";
                strSql+=" WHERE co_seg="+objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_SEG)+" and co_empRelCabCotVen IS NOT NULL \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intCodEmpCot = rstLoc.getInt("co_empRelCabCotVen");
                    intCodLocCot = rstLoc.getInt("co_locRelCabCotVen");
                    intCodDocCot = rstLoc.getInt("co_cotRelCabCotVen");
                }
                else{
                    blnRes=false;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
             System.out.println("Error - insertarDetDoc: " + e);
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            System.out.println("Error - iniDia: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
  
    
    
    private boolean insertarDetDoc(java.sql.Connection conExt, int CodigoConfiguracionIngresoEgreso, ArrayList arlItm){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strNomBodDet="",strEstIngEgrMerBod, strSQLIns="", strEstIva="";
        int intNumFil=0,j=0;
        BigDecimal bgdCanItm;
        BigDecimal bgdPreItm;
        BigDecimal bgdTotItm=BigDecimal.ZERO;
        BigDecimal bgdIntSig;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                BigDecimal bgdValDocSub=new BigDecimal("0");
                BigDecimal bgdValDocIva=new BigDecimal("0");
                BigDecimal bgdValDocTot=new BigDecimal("0");
                System.out.println("arlItm: " + arlItm.toString());
                for(int i=0; i<arlItm.size(); i++){
                   
                    
                    //nombre de bodega origen
                    strSql="";
                    strSql+="SELECT a1.tx_nom AS tx_nomBod FROM tbm_bod AS a1";
                    strSql+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_EMP) + "";
                    strSql+=" AND a1.co_bod=" + objUti.codificar(objUti.getStringValueAt(arlItm, i, INT_ARL_COD_BOD_ORI_DES)) + "";
                    System.out.println("insertarDetDoc (1): "+strSql);
                    rstLoc=stmLoc.executeQuery(strSql);
                    if(rstLoc.next())
                        strNomBodDet=rstLoc.getString("tx_nomBod");
                    else
                        return false;

                    //Egreso, Ingreso, Factura, Orden de Compra
                    //--aqui se inserta en positivo porque la cantidad se la recibe en postivo
                    
                    strSql="";
                    strSql+="INSERT INTO tbm_detmovinv(";
                    strSql+="   co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, tx_codalt,";
                    strSql+="   tx_nomitm, tx_unimed, co_bod, nd_can, nd_canorg, nd_cosuni, nd_preuni,";
                    strSql+="   nd_pordes, st_ivacom, nd_exi, nd_valexi, st_reg, nd_cancon, tx_obs1,";
                    strSql+="   co_usrcon, ne_numfil, nd_tot, tx_codalt2, nd_costot, nd_cospro,";
                    strSql+="   nd_cosunigrp, nd_costotgrp, nd_exigrp, nd_valexigrp, nd_cosprogrp,";
                    strSql+="   nd_candev, co_itmact, co_locrelsoldevven, co_tipdocrelsoldevven,";
                    strSql+="   co_docrelsoldevven, co_regrelsoldevven, nd_cannunrec,";
                    strSql+="   co_locrelsolsaltemmer, co_tipdocrelsolsaltemmer, co_docrelsolsaltemmer,";
                    strSql+="   co_regrelsolsaltemmer, nd_preunivenlis, nd_pordesvenmax, tx_nombodorgdes,";
                    strSql+="   nd_cantotmalest, nd_cantotmalestpro, nd_cantotnunrecpro, st_cliretemprel,";
                    strSql+="   fe_retemprel, tx_perretemprel, tx_vehretemprel, tx_plavehretemprel,";
                    strSql+="   tx_obscliretemprel, nd_ara, nd_preuniimp, nd_valtotfobcfr, nd_cantonmet,";
                    strSql+="   nd_valfle, nd_valcfr, nd_valtotara, nd_valtotgas, nd_canutiorddis";
                    
                    strEstIngEgrMerBod=objUti.getObjectValueAt(arlItm, i, INT_ARL_EST_ING_EGR_MER_BOD)==null?"":objUti.getStringValueAt(arlItm, i, INT_ARL_EST_ING_EGR_MER_BOD);
                    
                    strSql+=", nd_canEgrBod";
                    strSql+=", nd_canIngBod";
                    strSql+=", nd_canPen";
                    strSql+=", st_meringegrfisbod";
                    j++;
                    strSql+=")";
                    strSql+="(";
                    strSql+="   SELECT ";
                    strSql+="" + objUti.codificar(objUti.getStringValueAt(arlItm, i, INT_ARL_COD_EMP)) + ",";//co_emp
                    strSql+="" + objUti.codificar(objUti.getStringValueAt(arlItm, i, INT_ARL_COD_LOC)) + ",";//co_loc
                    strSql+="" + intCodTipDocIns + ",";//co_tipdoc
                    strSql+="" + intCodDoc + ",";//co_doc
                    strSql+="" + j + ",";//co_reg
                    strSql+="" + objUti.getStringValueAt(arlItm, i, INT_ARL_COD_ITM_EMP) + ",";//co_itm
                    strSql+="" + objUti.codificar(objUti.getStringValueAt(arlItm, i, INT_ARL_COD_ALT_ITM)) + ",";//tx_codalt
                    strSql+="" + objUti.codificar(objUti.getStringValueAt(arlItm, i, INT_ARL_NOM_ITM)) + ",";//tx_nomitm
                    strSql+="" + objUti.codificar(objUti.getStringValueAt(arlItm, i, INT_ARL_UNI_MED_ITM)) + ",";//tx_unimed
                    //strSql+="" + intCodBodRegUno + ",";//co_bod
                    strSql+="" + objUti.codificar(objUti.getStringValueAt(arlItm, i, INT_ARL_COD_BOD_ORI_DES)) + ",";//co_bod
                    bgdCanItm=new BigDecimal(objUti.getObjectValueAt(arlItm, i, INT_ARL_CAN_ITM)==null?"0":(objUti.getStringValueAt(arlItm, i, INT_ARL_CAN_ITM).equals("")?"0":objUti.getStringValueAt(arlItm, i, INT_ARL_CAN_ITM)));
                    
                    if(CodigoConfiguracionIngresoEgreso==0){
                         strSql+="" + bgdCanItm + ",";//nd_can
                    }else{
                        bgdCanItm = bgdCanItm.multiply(new BigDecimal(-1));
                        strSql+="" + bgdCanItm + ",";//nd_can
                    }                   
                    strSql+="Null,";//nd_canorg
                    
                    System.out.println("COSTO UNITARIO LLEGA: " + objUti.redondearBigDecimal(objUti.getStringValueAt(arlItm, i, INT_ARL_COS_UNI), objParSis.getDecimalesBaseDatos()));
                    
                    bgdPreItm=objUti.redondearBigDecimal(objUti.getStringValueAt(arlItm, i, INT_ARL_COS_UNI), objParSis.getDecimalesBaseDatos());
                    System.out.println("bgdPreItm-ANTES: " + bgdPreItm);
                    
                    strEstIva=objUti.getStringValueAt(arlItm, i, INT_ARL_IVA_COM_ITM);
                    bgdTotItm=objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)), objParSis.getDecimalesMostrar());                 

                     
                    
                    strSql+="" + bgdPreItm + ",";//nd_cosuni
                    strSql+="" + bgdPreItm + ",";//nd_preuni
                    strSql+="0,";//nd_pordes     por disposición de Eddye para FACVENE, FACCOM, INGBOPR, EGBOPR, TRANSFERENCIAS EN GENERAL, no se debe realizar descuento  

                    strSql+="" + objUti.codificar(strEstIva) + ",";//st_ivacom                
                    strSql+="Null, ";//nd_exi
                    strSql+="Null,";//nd_valexi
                    strSql+="Null,";//st_reg
                    strSql+="0,";//nd_cancon
                    strSql+="Null,";//tx_obs1
                    strSql+="Null,";//co_usrcon
                    strSql+="" + intNumFil + ",";//ne_numfil
                    strSql+="" + objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)), objParSis.getDecimalesMostrar()) + ", ";//nd_tot
                    strSql+="" + objUti.codificar(objUti.getStringValueAt(arlItm, i, INT_ARL_COD_LET_ITM)) + ",";//tx_codalt2
                    strSql+="" + objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)), objParSis.getDecimalesBaseDatos()) + ",";//nd_costot
                    strSql+="Null,";//nd_cospro
                    strSql+="" + bgdPreItm + ",";//nd_cosunigrp
                    strSql+="" + objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)), objParSis.getDecimalesBaseDatos()) + ",";//nd_costotgrp
                    strSql+="Null,";//nd_exigrp
                    strSql+="Null,";//nd_valexigrp
                    strSql+="Null,";//nd_cosprogrp
                    strSql+="0, ";//nd_candev
                    strSql+="" + objUti.getStringValueAt(arlItm, i, INT_ARL_COD_ITM_EMP) + ",";//co_itmact
                    strSql+="Null,";//co_locrelsoldevven
                    strSql+="Null,";//co_tipdocrelsoldevven
                    strSql+="Null,";//co_docrelsoldevven
                    strSql+="Null,";//co_regrelsoldevven
                    
                    strSql+="0,";//nd_cannunrec
                    strSql+="Null,";//co_locrelsolsaltemmer
                    strSql+="Null,";//co_tipdocrelsolsaltemmer
                    strSql+="Null,";//co_docrelsolsaltemmer
                    strSql+="Null,";//co_regrelsolsaltemmer
                    strSql+="Null,";//nd_preunivenlis
                    strSql+="Null,";//nd_pordesvenmax
    //                strSql+="" + objUti.codificar(strNomBodRegUno) + ",";//tx_nombodorgdes
                    strSql+="" + objUti.codificar(strNomBodDet) + ",";//tx_nombodorgdes
                    strSql+="Null,";//nd_cantotmalest
                    strSql+="Null,";//nd_cantotmalestpro
                    strSql+="Null,";//nd_cantotnunrecpro
                    strSql+="Null,";//st_cliretemprel
                    strSql+="Null,";//fe_retemprel
                    strSql+="Null,";//tx_perretemprel
                    strSql+="Null,";//tx_vehretemprel
                    strSql+="Null,";//tx_plavehretemprel
                    strSql+="Null,";//tx_obscliretemprel
                    strSql+="Null,";//nd_ara
                    strSql+="Null,";//nd_preuniimp
                    strSql+="Null,";//nd_valtotfobcfr
                    strSql+="Null,";//nd_cantonmet
                    strSql+="Null,";//nd_valfle
                    strSql+="Null,";//nd_valcfr
                    strSql+="Null,";//nd_valtotara
                    strSql+="Null,";//nd_valtotgas
                    strSql+="Null";//nd_canutiorddis
                    
                    
                    //3.- Validación por terminal del item
                    

                    System.out.println("********************************************************************");
                    System.out.println("strEstIngEgrMerBod: " + strEstIngEgrMerBod);
                    
                    
                    if(CodigoConfiguracionIngresoEgreso==0){
                         strSql+=", Null";//nd_canEgrBod
                        strSql+=", Null";//nd_canIngBod
                        strSql+=", Null";//nd_canPen
                        strEstIngEgrMerBod="N";
                        System.out.println("OO: ");
                    }
                    else{
                        /* EGRESO */
                        if(strEstIngEgrMerBod.equals("S")){
                            strSql+=", " + bgdCanItm.multiply(new BigDecimal(-1)) + "";//nd_canEgrBod
                            strSql+=", Null";//nd_canIngBod
                            strSql+=", " + bgdCanItm.multiply(new BigDecimal(-1)) + "";//nd_canPen
                            System.out.println("JJ: ");
                        }
                        else{
                            strSql+=", Null";//nd_canEgrBod
                            strSql+=", Null";//nd_canIngBod
                            strSql+=", Null";//nd_canPen
                            strEstIngEgrMerBod="N";
                            System.out.println("KK: ");
                        }
                    }
                 
                     
                    
                    strSql+=", '" + strEstIngEgrMerBod + "'";//st_meringegrfisbod  
                    ////////////////////////////////////////////////////////////////
                    strSql+=");";
                    System.out.println("**--++ :)  insertar_tbmDetMovInv: " + strSql);
                    strSQLIns+=strSql;
                    
                    if(CodigoConfiguracionIngresoEgreso==1){//egreso
                        //ESTO NO ESTARIA BIEN PORQUE ENVIAN LOS ITEMS DE INGRESO Y EGRESO PARA TX
                        bgdValDocSub=objUti.redondearBigDecimal((bgdValDocSub.add(bgdTotItm)), objParSis.getDecimalesMostrar())    ;//al final tendrá el valor del subtotal del documento
                        bgdValDocTot=objUti.redondearBigDecimal(((bgdValDocSub.add(bgdValDocIva))), objParSis.getDecimalesMostrar());//al final tendrá el valor del total del documento
                    }
                    else {//ingreso
                        //ESTO NO ESTARIA BIEN PORQUE ENVIAN LOS ITEMS DE INGRESO Y EGRESO PARA TX
                        bgdValDocSub=objUti.redondearBigDecimal((bgdValDocSub.add(bgdTotItm)), objParSis.getDecimalesMostrar());//al final tendrá el valor del subtotal del documento
                        bgdValDocTot=objUti.redondearBigDecimal(((bgdValDocSub.add(bgdValDocIva))), objParSis.getDecimalesMostrar());//al final tendrá el valor del total del documento
                    }
                    
                     
                }
                bdeMon = bgdValDocTot;
                strSql="";
                strSql+=" UPDATE tbm_cabMovInv SET ";
                if(CodigoConfiguracionIngresoEgreso==1){//egreso
                    if( Double.parseDouble(bgdValDocSub.toString())>0 ){
                        strSql+=" nd_sub="+bgdValDocSub.multiply(new BigDecimal(-1))+", nd_tot="+bgdValDocTot.multiply(new BigDecimal(-1))+", nd_valIva="+bgdValDocIva.multiply(new BigDecimal(-1))+" ";
                        strSql+=" , st_genOrdDes='S'";
                    }
                    else{
                        strSql+=" nd_sub="+bgdValDocSub  +", nd_tot="+bgdValDocTot +", nd_valIva="+bgdValDocIva +" ";
                        strSql+=" , st_genOrdDes='S'";  
                    }
                }
                else{
                    strSql+=" nd_sub="+bgdValDocSub+", nd_tot="+bgdValDocTot+", nd_valIva="+bgdValDocIva+" ";
                    strSql+=" , st_genOrdDes=Null";
                }
                strSql+=" WHERE co_emp="+objUti.codificar(objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_EMP)) + " AND co_loc="+objUti.codificar(objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_LOC))+" AND co_tipDoc="+intCodTipDocIns+" AND co_doc="+intCodDoc+";";
                strSQLIns+=strSql;
                System.out.println("UPDATE tbm_cabMovInv: " + strSQLIns);
                stmLoc.executeUpdate(strSQLIns);
                blnRes=true;
            }
        }
        catch(java.sql.SQLException e){
             System.out.println("Error - insertarDetDoc: " + e);
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            System.out.println("Error - insertarDetDoc: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
        
    }
    
    private ZafCfgBod objCfgBod;
    private String strMerIngEgrFisBod;
    private Object objIsGenOD; 
    
    
    /**
     * Insertar el Documento creado en el seguimiento 
     * 
     * 
     * @param conExt
     * @param arlItm
     * @return 
     */
    
    private boolean insertarDocumentoSeguimiento(java.sql.Connection conExt, ArrayList arlItm){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
         int intCodRegSolTra=-1;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql = "";
                strSql+= " SELECT a1.co_seg, MAX(a1.co_reg+1) as co_reg\n";
                strSql+= " FROM tbm_cabSegMovInv as a1  \n";
                strSql+= " WHERE a1.co_seg="+objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_SEG)+" \n";
                strSql+= " GROUP BY a1.co_seg  \n";
                System.out.println("insertarDocumentoSeguimiento 1 " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    intCodRegSolTra=rstLoc.getInt("co_reg");
                }
                rstLoc.close();
                rstLoc=null;
                strSql = "";
                strSql+= " INSERT INTO tbm_cabSegMovInv (co_seg, co_reg, co_empRelCabMovInv, co_locRelCabMovInv, co_tipDocRelCabMovInv, co_docRelCabMovInv) \n";
                strSql+= " VALUES ("+objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_SEG)+","+intCodRegSolTra+","+ objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_EMP);
                strSql+= ","+objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_LOC)+","+ intCodTipDocIns +","+intCodDoc+"); \n";
                System.out.println("insertarDocumentoSeguimiento 2 " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }
        catch(java.sql.SQLException e){
             System.out.println("Error - insertarDocumentoSeguimiento: " + e);
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            System.out.println("Error - insertarDocumentoSeguimiento: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
  
    
    private boolean insertarCabDoc(java.sql.Connection conExt, Date dtpFechaDocumento , int ConfiguracionIngresoEgreso, ArrayList arlItm){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
       
        try{
            if(conExt!=null){
                 
                stmLoc = conExt.createStatement();

                strSql="";
                strSql+="SELECT MAX(a1.co_doc) AS co_doc FROM tbm_cabMovInv AS a1";
                strSql+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlItm, 0, INT_ARL_COD_EMP) + "";
                strSql+=" AND a1.co_loc=" + objUti.getStringValueAt(arlItm, 0, INT_ARL_COD_LOC) + "";
                strSql+=" AND a1.co_tipDoc=" + intCodTipDocIns + "";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next())//JOTA
                    intCodDoc=(Integer.parseInt(rstLoc.getObject("co_doc")==null?"0":(rstLoc.getString("co_doc").equals("")?"0":rstLoc.getString("co_doc")))) + 1;
                else
                    return false;

                //número
                strSql="";
                strSql+="SELECT a1.ne_ultDoc FROM tbm_cabTipDoc AS a1";
                strSql+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlItm, 0, INT_ARL_COD_EMP) + "";
                strSql+=" AND a1.co_loc=" + objUti.getStringValueAt(arlItm, 0, INT_ARL_COD_LOC) + "";
                strSql+=" AND a1.co_tipDoc=" + intCodTipDocIns + "";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next())//JOTA
                    intNumDoc=(Integer.parseInt(rstLoc.getObject("ne_ultDoc")==null?"0":(rstLoc.getString("ne_ultDoc").equals("")?"0":rstLoc.getString("ne_ultDoc")))) + 1;
                else
                    return false;

                //signo para generar el documento
                strSql="";
                strSql+="SELECT a1.tx_natdoc FROM tbm_cabTipDoc AS a1";
                strSql+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlItm, 0, INT_ARL_COD_EMP) + "";
                strSql+=" AND a1.co_loc=" + objUti.getStringValueAt(arlItm, 0, INT_ARL_COD_LOC) + "";
                strSql+=" AND a1.co_tipDoc=" + intCodTipDocIns + "";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                    bdeSig=new BigDecimal(rstLoc.getString("tx_natDoc").equals("I")?"1":"-1");
                else
                    return false;



                //para obtener secuencia de empresa
                strSql="";
                strSql+="SELECT CASE WHEN ne_secUltDocTbmCabMovInv IS NULL THEN 0 ELSE (ne_secUltDocTbmCabMovInv+1) END AS ne_numSecEmp";
                strSql+=" FROM tbm_emp WHERE co_emp=" + objUti.getStringValueAt(arlItm, 0, INT_ARL_COD_EMP) + "";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                    intNumSecEmp=rstLoc.getInt("ne_numSecEmp");

                //para obtener secuencia de grupo
                strSql="";
                strSql+="SELECT CASE WHEN ne_secUltDocTbmCabMovInv IS NULL THEN 0 ELSE (ne_secUltDocTbmCabMovInv+1) END AS ne_numSecGrp";
                strSql+=" FROM tbm_emp WHERE co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                    intNumSecGrp=rstLoc.getInt("ne_numSecGrp");


                strSql="";
                strSql+="INSERT INTO tbm_cabMovInv(";
                strSql+="   co_emp, co_loc, co_tipdoc, co_doc, ne_numcot, ne_numdoc, ne_numgui, fe_doc, co_cli, tx_ruc, tx_nomcli, tx_dircli, tx_telcli,";
                strSql+="   co_forpag, tx_desforpag, nd_poriva,nd_sub, nd_tot, tx_obs1, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, tx_numped,";
                strSql+="   ne_secgrp, ne_secemp, nd_valiva, co_mnu, st_tipdev, st_imp, tx_coming, tx_commod, co_ptodes,";
                strSql+="   co_emprelcabsoltrainv, co_locrelcabsoltrainv, co_tipdocrelcabsoltrainv, co_docrelcabsoltrainv, tx_tipmov, st_genOrdDes";
                strSql+=")";
                strSql+="   (";
                strSql+="    SELECT ";
                strSql+="" + objUti.getStringValueAt(arlItm, 0, INT_ARL_COD_EMP) + ",";//co_emp
                strSql+="" + objUti.getStringValueAt(arlItm, 0, INT_ARL_COD_LOC) + ",";//co_loc
                strSql+="" + intCodTipDocIns + ",";//co_tipdoc
                strSql+="" + intCodDoc + ",";//co_doc
                strSql+="0, ";//ne_numcot
                strSql+="" + intNumDoc + ",";//ne_numdoc

                strSql+="0, ";//ne_numgui
                strSql+="'"+ objUti.formatearFecha(dtpFechaDocumento, objParSis.getFormatoFechaBaseDatos()) +"', "; //fe_doc
                strSql+=" "+objUti.codificar(objDatCli.getCodigoClienteCotizacion(intCodEmpCot, intCodLocCot, intCodDocCot),2) +" ,";//co_cli
                strSql+=" "+objUti.codificar(objDatCli.getIDClienteCotizacion(intCodEmpCot, intCodLocCot, intCodDocCot))+" ,";//tx_ruc
                strSql+=" "+objUti.codificar(objDatCli.getNombreClienteCotizacion(intCodEmpCot, intCodLocCot, intCodDocCot))+" ,";//tx_nomcli
                strSql+=" "+objUti.codificar(objDatCli.getDireccionClienteCotizacionGeneraGuia(intCodEmpCot, intCodLocCot, intCodDocCot))+" ,";//tx_dircli
                strSql+=" "+objUti.codificar(objDatCli.getTelefonoClienteCotizacion(intCodEmpCot, intCodLocCot, intCodDocCot))+" ,";//tx_telcli
                strSql+="Null,";//co_forpag
                strSql+="Null,";//tx_desforpag
                strSql+="0, ";//nd_poriva


                strSql+="0,";//nd_sub
                strSql+="0,";//nd_tot
                strSql+="'Generado por Zafiro "+strVer+": '||" + " CURRENT_TIMESTAMP" + ",";//tx_obs1
                strSql+="'A'" + ",";//st_reg
                strSql+=" CURRENT_TIMESTAMP" + ",";//fe_ing
                strSql+=" null" + ",";//fe_ultmod
                strSql+="" + objParSis.getCodigoUsuario() + ",";//co_usring
                strSql+="" + objParSis.getCodigoUsuario() + ",";//co_usrmod
                strSql+="Null,";//tx_numped   No almacena nada en este momento sino cuando se obtengan los dos tipos de documentos
                strSql+="" + intNumSecGrp + ",";//ne_secgrp
                strSql+="" + intNumSecEmp + ",";//ne_secemp
                strSql+="0,";//nd_valiva
                strSql+="" + objParSis.getCodigoMenu() + ",";//co_mnu
                strSql+="'C',";//st_tipdev
                strSql+="'N',";//st_imp
                strSql+="" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + ",";//tx_coming
                strSql+="null,";//tx_commod

                strSql+=" Null,";//co_ptodes


                strSql+="null,";//co_emprelcabsoltrainv
                strSql+="null,";//co_locrelcabsoltrainv
                strSql+="null,";//co_tipdocrelcabsoltrainv
                strSql+="null,";//co_docrelcabsoltrainv
                strSql+="'" + strTipMovSeg + "'";//tx_tipmov

                System.out.println("1-insertar_tbmCabMovInv");
                /*  DDDDDDDDDDDDDDDDDDDDDDDDDD  */



                if(objIsGenOD==null)
                    strSql+=", " + objIsGenOD + "";//st_genOrdDes
                else
                    strSql+=", '" + objIsGenOD + "'";//st_genOrdDes
                /*  DDDDDDDDDDDDDDDDDDDDDDDDDD  */
                strSql+=");";
                System.out.println("insertar_tbmCabMovInv: " + strSql);

                //para obtener secuencia de empresa
                strSql+=" UPDATE tbm_emp";
                strSql+=" SET ne_secUltDocTbmCabMovInv=(ne_secUltDocTbmCabMovInv+1)";
                strSql+=" WHERE co_emp=" + objUti.getStringValueAt(arlItm, 0, INT_ARL_COD_EMP) + "";
                strSql+=";";
                System.out.println("aumentar secuencia empresa: " + strSql);
                //para obtener secuencia de grupo
                strSql+=" UPDATE tbm_emp";
                strSql+=" SET ne_secUltDocTbmCabMovInv=(ne_secUltDocTbmCabMovInv+1)";
                strSql+=" WHERE co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                strSql+=";";
                System.out.println("aumentar secuencia grupo: " + strSql);
                stmLoc.executeUpdate(strSql);

                stmLoc.close();
                stmLoc=null;
                rstLoc.close();
                rstLoc=null;
                blnRes=true;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            System.out.println("Error - insertarCabDoc: " + e);
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
            System.out.println("Error - iniDia: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
     
    
    /**
     * Función que permite generar el diario de transferencia
     * @param codigoEmpresaDocumento Código de empresa del documento
     * @param codigoEmpresaCliente Código de empresa del cliente
     * 4;3284;"PRE FACTURAS CIAS";"1.01.06.01.16"
        2;2409;"PRE FACTURAS CIAS";"1.01.06.01.16"
        1;4819;"PRE FACTURAS CIAS";"1.01.06.01.16"
        * 2;1277;"BODEGA     GENERAL";"1.01.06.01.15"
        1;3000;"BODEGA GENERAL";"1.01.06.01.15"
        4;2253;"BODEGA  GENERAL";"1.01.06.01.15"
     */
    private boolean genDiaTrs(int CodEmp, int codigoConfiguracion){        
        boolean blnRes=true;
        String strCodCtaPreFac="", strNumCtaPreFac="", strNomCtaPreFac="";
        String strCodCtaBodGen="", strNumCtaBodGen="", strNomCtaBodGen="";
        boolean blnIsCosenco=false,blnIsEcuatosa=false,blnIsDetopacio=false;
        try{
            blnIsCosenco = (objParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO") > -1)?true:false;
            blnIsEcuatosa = (objParSis.getNombreEmpresa().toUpperCase().indexOf("ECUATOSA") > -1)?true:false;
            blnIsDetopacio = (objParSis.getNombreEmpresa().toUpperCase().indexOf("DETOPACIO") > -1)?true:false;
            switch(CodEmp){
                case 1:
                    if(blnIsCosenco || blnIsEcuatosa ||blnIsDetopacio){
                        strCodCtaPreFac="2327"; strNumCtaPreFac="1.01.06.01.16"; strNomCtaPreFac="PRE FACTURAS CIAS"; 
                        strCodCtaBodGen="407"; strNumCtaBodGen="1.01.06.01.01"; strNomCtaBodGen="BODEGA GUAYAQUIL"; break;
                    }
                    else{
                        strCodCtaPreFac="4819"; strNumCtaPreFac="1.01.06.01.16"; strNomCtaPreFac="PRE FACTURAS CIAS"; 
                        strCodCtaBodGen="3000"; strNumCtaBodGen="1.01.06.01.15"; strNomCtaBodGen="BODEGA GENERAL"; break;
                    }
                    
                case 2:strCodCtaPreFac="2409"; strNumCtaPreFac="1.01.06.01.16"; strNomCtaPreFac="PRE FACTURAS CIAS"; 
                        strCodCtaBodGen="1277"; strNumCtaBodGen="1.01.06.01.15"; strNomCtaBodGen="BODEGA     GENERAL";  break;
                case 4:strCodCtaPreFac="3284"; strNumCtaPreFac="1.01.06.01.16"; strNomCtaPreFac="PRE FACTURAS CIAS"; 
                        strCodCtaBodGen="2253"; strNumCtaBodGen="1.01.06.01.15"; strNomCtaBodGen="BODEGA  GENERAL";  break; 
            }
            System.out.println("****codigoConfiguracion (0 Ingreso 1 EGRESO): " + codigoConfiguracion);
            if(codigoConfiguracion==INT_CON_ING){ /* INGRESO */
                System.out.println("DIARIO INGRESO");
                //Cuenta de debe
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, strCodCtaBodGen);//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, strNumCtaBodGen);
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, strNomCtaBodGen);
                vecRegDia.add(INT_VEC_DIA_DEB, "" + bdeMon.abs());
                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
                //Cuenta de haber
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, strCodCtaPreFac);//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, strNumCtaPreFac);
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, strNomCtaPreFac);
                vecRegDia.add(INT_VEC_DIA_DEB, "0");
                vecRegDia.add(INT_VEC_DIA_HAB, "" + bdeMon.abs());
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
            }else if(codigoConfiguracion==INT_CON_EGR){  /* EGRESO */
                System.out.println("DIARIO EGRESO");
                //Cuenta de debe
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, strCodCtaPreFac);//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, strNumCtaPreFac);
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, strNomCtaPreFac);
                vecRegDia.add(INT_VEC_DIA_DEB, "" + bdeMon.abs());
                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
                //Cuenta de haber
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, strCodCtaBodGen);//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, strNumCtaBodGen);
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, strNomCtaBodGen);
                vecRegDia.add(INT_VEC_DIA_DEB, "0");
                vecRegDia.add(INT_VEC_DIA_HAB, "" + bdeMon.abs());
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
            }
            System.out.println("vecDatDia " + vecDatDia.toString());
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
     * Esta función muestra un mensaje informativo al usuario. Se podróa utilizar
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
    
    
 
    private ArrayList arlDatIng;
     
    private static final int INT_ARL_ING_COD_ITM=0;
    private static final int INT_ARL_ING_CAN_ITM=1;
    private static final int INT_ARL_ING_COD_BOD_EGR=2;
    private static final int INT_ARL_ING_CAN_EGR_BOD=3;  /* Bodega de Ingreso */
    private static final int INT_ARL_ING_CAN_EGR_DES=4;  /* Cantidad Utilizada */
    
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
    private boolean obtenerCantidadRemota(java.sql.Connection con, int CodEmp, int CodLoc,int CodCot){
        java.sql.Statement stmLoc; 
        java.sql.ResultSet rstLoc;
        boolean blnRes=true;
        try{
            if(con!=null){
                arlDatIng = new ArrayList();
                stmLoc=con.createStatement();
                strSql="";/* LOS INGRESOS AGRUPADOS POR ITEM */
                strSql+="  SELECT a2.co_emp , a2.co_loc,a2.co_cot,a8.co_bodGrp, a2.co_itm,a2.tx_codAlt,a2.nd_can, \n";
                strSql+="         CASE WHEN a4.nd_canIng IS NULL THEN 0 ELSE a4.nd_canIng END as nd_canEgrDes, \n";
                strSql+="         (a2.nd_can- CASE WHEN a4.nd_canIng IS NULL THEN 0 ELSE a4.nd_canIng END) as nd_canEgrBod  ";
                strSql+="  FROM tbm_cabCotVen as a1  \n";
                strSql+="  INNER JOIN tbm_detCotVen AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+="  LEFT OUTER JOIN ( \n";
                strSql+="       SELECT x.co_itm, SUM(x.nd_can) as nd_canIng \n";
                strSql+="       FROM(  \n";
                strSql+="           SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc,a3.co_reg,    \n";
                strSql+="                    a3.co_itm, a3.nd_can  \n";
                strSql+="           FROM tbm_cabMovInv as a2  /*EL INGRESO */ \n";
                strSql+="           INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND   \n";
                strSql+="                                              a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
                strSql+="           INNER JOIN (  \n";
                strSql+="                       SELECT a1.co_emp AS co_empIng, a1.co_loc as co_locIng, a1.co_tipDoc as co_tipDocIng, a1.co_doc as co_docIng \n";
                strSql+="                       FROM tbm_cabMovInv as a1 \n";
                strSql+="                       INNER JOIN tbm_cabSegMovInv as a2 ON (a1.co_emp=a2.co_empRelCabMovInv AND a1.co_loc=a2.co_locRelCabMovInv AND  \n";
                strSql+="                                                           a1.co_tipDoc=a2.co_tipDocRelCabMovInv AND a1.co_doc=a2.co_docRelCabMovInv) \n";
                strSql+="                       INNER JOIN ( \n";
                strSql+="                                   SELECT a1.co_seg \n";
                strSql+="                                   FROM tbm_cabSegMovInv as a1  \n";
                strSql+="                                   INNER JOIN tbm_cabCotVen as a2 ON (a1.co_empRelCabCotVen=a2.co_emp AND a1.co_locRelCabCotVen=a2.co_loc AND \n";
                strSql+="                                                                      a1.co_cotRelCabCotVen=a2.co_cot)  \n";
                strSql+="                                   WHERE a2.co_emp="+CodEmp+" AND a2.co_loc="+CodLoc+" AND a2.co_cot="+CodCot+"  \n";
                strSql+="                       ) AS a3 ON (a2.co_seg=a3.co_seg) \n";
                strSql+="                       INNER JOIN tbm_cabTipDoc as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc) \n";
                strSql+="                       WHERE a4.tx_natDoc='I' 	 \n";
                strSql+="           ) as a7 ON (a2.co_emp=a7.co_empIng AND a2.co_loc=a7.co_locIng AND a2.co_tipDoc=a7.co_tipDocIng AND \n";
		strSql+="                   a2.co_doc=a7.co_docIng)  \n";
                strSql+="       ) as x \n";
                strSql+="       GROUP BY x.co_itm	 \n";
                strSql+="  ) AS a4 ON (a2.co_itm=a4.co_itm)  \n";
                strSql+="       INNER JOIN tbr_bodEmpBodGrp AS a8 ON (a2.co_emp=a8.co_emp AND a2.co_bod=a8.co_bod AND a8.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" ) \n";
		strSql+="  WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+"   \n"; 	
                System.out.println("obtenerCantidadRemota:... \n" + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    arlRegIng=new ArrayList();
                    arlRegIng.add(INT_ARL_ING_COD_ITM,rstLoc.getInt("co_itm"));
                    arlRegIng.add(INT_ARL_ING_CAN_ITM,rstLoc.getDouble("nd_can"));
                    arlRegIng.add(INT_ARL_ING_COD_BOD_EGR,rstLoc.getInt("co_bodGrp"));  /* Bodega de Grupo */
                    arlRegIng.add(INT_ARL_ING_CAN_EGR_BOD,rstLoc.getDouble("nd_canEgrBod"));  
                    arlRegIng.add(INT_ARL_ING_CAN_EGR_DES,rstLoc.getDouble("nd_canEgrDes"));
                    arlDatIng.add(arlRegIng);
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){         
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch (Exception e){         
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }

    
    
    private boolean obtenerDatosEgreso(java.sql.Connection conExt, ArrayList arlItm){
        boolean blnRes=true, blnPoseeIngreso=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        double dblCanEgrBod=0.00, dblCanEgrDes=0.00;
        int intCodBodGrpEgr=-1;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                intCodBodGrpEgr = objDatBod.getCodigoBodegaGrupo(objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_EMP), objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_BOD_ORI_DES));
                strSql="";
                strSql+=" SELECT co_empRelCabCotVen, co_locRelCabCotVen, co_cotRelCabCotVen ";
                strSql+=" FROM tbm_cabSegMovInv ";
                strSql+=" WHERE co_seg="+objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_SEG)+" AND co_empRelCabCotVen IS NOT NULL";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                      if(obtenerCantidadRemota(conExt, rstLoc.getInt("co_empRelCabCotVen"),rstLoc.getInt("co_locRelCabCotVen"),rstLoc.getInt("co_cotRelCabCotVen"))){
                          System.out.println("obtenerCantidadRemota " + arlDatIng.toString()); 
                          for(int i=0;i<arlItm.size();i++){
                               for(int ingreso=0;ingreso<arlDatIng.size();ingreso++){
                                   if(objUti.getStringValueAt(arlItm, i,INT_ARL_COD_ITM_EMP).equals(objUti.getStringValueAt(arlDatIng, ingreso,INT_ARL_ING_COD_ITM))){
                                        dblCanEgrBod = objUti.getDoubleValueAt(arlDatIng, ingreso, INT_ARL_ING_CAN_EGR_BOD);
                                        dblCanEgrDes = objUti.getDoubleValueAt(arlDatIng, ingreso, INT_ARL_ING_CAN_EGR_DES);
                                   }
                               }// FOR 2 ingreso     
                               System.out.println("EMP: " + objUti.getIntValueAt(arlItm, i, INT_ARL_COD_EMP) + " ITEM: "+ objUti.getStringValueAt(arlItm, i, INT_ARL_COD_ALT_ITM));
                               System.out.println("EGR.BOD: " + dblCanEgrBod + " EGR.DES: "+ dblCanEgrDes); 
                               if(intCodBodGrpEgr==15 || intCodBodGrpEgr==3){
                                    /* EGRESO DE DOS AMBIENTES ===> INMACONSA y QUITO  */
                                    System.out.println("EGRESO RESERVAS DOS AMBIENTES ");
                                    if(generaNuevoContenedorItemsMovimientoStock(objUti.getIntValueAt(arlItm, i, INT_ARL_COD_EMP),
                                                                                 objUti.getIntValueAt(arlItm, i, INT_ARL_COD_ITM_EMP),
                                                                                 dblCanEgrBod,
                                                                                 objUti.getIntValueAt(arlItm, i, INT_ARL_COD_BOD_ORI_DES))){
                                         if(objStkInv.actualizaInventario(conExt, objUti.getIntValueAt(arlItm, i, INT_ARL_COD_EMP),INT_ARL_STK_INV_CAN_EGR_BOD, "-", 0, arlDatStkInvItm)){
                                             arlDatStkInvItm.clear();
                                         }else{blnRes=false;}
                                     }else{blnRes=false;}

                                     if(generaNuevoContenedorItemsMovimientoStock(objUti.getIntValueAt(arlItm, i, INT_ARL_COD_EMP),
                                                                                  objUti.getIntValueAt(arlItm, i, INT_ARL_COD_ITM_EMP),
                                                                                  dblCanEgrDes,
                                                                                  objUti.getIntValueAt(arlItm, i, INT_ARL_COD_BOD_ORI_DES))){
                                         if(objStkInv.actualizaInventario(conExt, objUti.getIntValueAt(arlItm, i, INT_ARL_COD_EMP),INT_ARL_STK_INV_CAN_DES_ENT_CLI, "-", 0, arlDatStkInvItm)){
                                             arlDatStkInvItm.clear();
                                         }else{blnRes=false;}
                                     }else{blnRes=false;}
                                }
                                else{
                                    System.out.println("EGRESO RESERVAS UN AMBIENTE TODO SALE DE BODEGA");
                                    if(generaNuevoContenedorItemsMovimientoStock(objUti.getIntValueAt(arlItm, i, INT_ARL_COD_EMP),objUti.getIntValueAt(arlItm, i, INT_ARL_COD_ITM_EMP), dblCanEgrBod , objUti.getIntValueAt(arlItm, i, INT_ARL_COD_BOD_ORI_DES))){
                                         if(objStkInv.actualizaInventario(conExt, objUti.getIntValueAt(arlItm, i, INT_ARL_COD_EMP),INT_ARL_STK_INV_CAN_EGR_BOD, "-", 0, arlDatStkInvItm)){
                                             arlDatStkInvItm.clear();
                                         }else{blnRes=false;}
                                     }else{blnRes=false;}
                                }
                                   
                                blnPoseeIngreso=false; 
                           }  // FOR 1 arlItm
                      }else{blnRes=false;}
                }else{blnRes=false;}
            }else{blnRes=false;}
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(cmpPad, e);
                blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
//    
//    private boolean obtenerDatosEgreso(java.sql.Connection conExt, ArrayList arlItm){
//        boolean blnRes=true, blnPoseeIngreso=false;
//        java.sql.Statement stmLoc;
//        java.sql.ResultSet rstLoc;
//        double dblCanEgrBod=0.00, dblCanEgrDes=0.00;
//        int intCodBodGrpEgr=-1;
//        try{
//            if(conExt!=null){
//                stmLoc = conExt.createStatement();
//                intCodBodGrpEgr = objDatBod.getCodigoBodegaGrupo(objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_EMP), objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_BOD_ORI_DES));
//                strSql="";
//                strSql+=" SELECT co_empRelCabCotVen, co_locRelCabCotVen, co_cotRelCabCotVen ";
//                strSql+=" FROM tbm_cabSegMovInv ";
//                strSql+=" WHERE co_seg="+objUti.getIntValueAt(arlItm, 0, INT_ARL_COD_SEG)+" AND co_empRelCabCotVen IS NOT NULL";
//                rstLoc = stmLoc.executeQuery(strSql);
//                if(rstLoc.next()){
//                      if(obtenerCantidadRemota(conExt, rstLoc.getInt("co_empRelCabCotVen"),rstLoc.getInt("co_locRelCabCotVen"),rstLoc.getInt("co_cotRelCabCotVen"))){
//                          System.out.println("obtenerCantidadRemota " + arlDatIng.toString()); 
//                          for(int i=0;i<arlItm.size();i++){
//                               for(int ingreso=0;ingreso<arlDatIng.size();ingreso++){
//                                   if(objUti.getStringValueAt(arlItm, i,INT_ARL_COD_ITM_EMP).equals(objUti.getStringValueAt(arlDatIng, ingreso,INT_ARL_ING_COD_ITM))){
//                                        blnPoseeIngreso=true;
//                                        dblCanEgrBod = objUti.getDoubleValueAt(arlDatIng, ingreso, INT_ARL_ING_CAN_EGR_BOD);
//                                        dblCanEgrDes = objUti.getDoubleValueAt(arlDatIng, ingreso, INT_ARL_ING_CAN_EGR_DES);
//                                   }
//                               }// FOR 2 ingreso     
//                               if(blnPoseeIngreso==false){
//                                   dblCanEgrBod=Double.parseDouble(objUti.getStringValueAt(arlItm, i,INT_ARL_CAN_ITM));
//                                   dblCanEgrDes=0.00;
//                               }    
//                               System.out.println("EGR.BOD: " + dblCanEgrBod + " EGR.DES: "+ dblCanEgrDes);
//                                       
//                                if(intCodBodGrpEgr==15 || intCodBodGrpEgr==3){
//                                    /* EGRESO DE DOS AMBIENTES ===> INMACONSA y QUITO  */
//                                    System.out.println("EGRESO RESERVAS DOS AMBIENTES ");
//                                    if(generaNuevoContenedorItemsMovimientoStock(objUti.getIntValueAt(arlItm, i, INT_ARL_COD_EMP),
//                                                                                 objUti.getIntValueAt(arlItm, i, INT_ARL_COD_ITM_EMP),
//                                                                                 dblCanEgrBod,
//                                                                                 objUti.getIntValueAt(arlItm, i, INT_ARL_COD_BOD_ORI_DES))){
//                                         if(objStkInv.actualizaInventario(conExt, objUti.getIntValueAt(arlItm, i, INT_ARL_COD_EMP),INT_ARL_STK_INV_CAN_EGR_BOD, "-", 0, arlDatStkInvItm)){
//                                             arlDatStkInvItm.clear();
//                                         }else{blnRes=false;}
//                                     }else{blnRes=false;}
//
//                                     if(generaNuevoContenedorItemsMovimientoStock(objUti.getIntValueAt(arlItm, i, INT_ARL_COD_EMP),
//                                                                                  objUti.getIntValueAt(arlItm, i, INT_ARL_COD_ITM_EMP),
//                                                                                  dblCanEgrDes,
//                                                                                  objUti.getIntValueAt(arlItm, i, INT_ARL_COD_BOD_ORI_DES))){
//                                         if(objStkInv.actualizaInventario(conExt, objUti.getIntValueAt(arlItm, i, INT_ARL_COD_EMP),INT_ARL_STK_INV_CAN_DES_ENT_CLI, "-", 0, arlDatStkInvItm)){
//                                             arlDatStkInvItm.clear();
//                                         }else{blnRes=false;}
//                                     }else{blnRes=false;}
//                                }
//                                else{
//                                    System.out.println("EGRESO RESERVAS UN AMBIENTE TODO SALE DE BODEGA");
//                                    if(generaNuevoContenedorItemsMovimientoStock(objUti.getIntValueAt(arlItm, i, INT_ARL_COD_EMP),objUti.getIntValueAt(arlItm, i, INT_ARL_COD_ITM_EMP), dblCanEgrBod , objUti.getIntValueAt(arlItm, i, INT_ARL_COD_BOD_ORI_DES))){
//                                         if(objStkInv.actualizaInventario(conExt, objUti.getIntValueAt(arlItm, i, INT_ARL_COD_EMP),INT_ARL_STK_INV_CAN_EGR_BOD, "-", 0, arlDatStkInvItm)){
//                                             arlDatStkInvItm.clear();
//                                         }else{blnRes=false;}
//                                     }else{blnRes=false;}
//                                }
//                                   
//                                blnPoseeIngreso=false; 
//                           }  // FOR 1 arlItm
//                      }else{blnRes=false;}
//                }else{blnRes=false;}
//            }else{blnRes=false;}
//        }
//        catch(Exception e){
//                objUti.mostrarMsgErr_F1(cmpPad, e);
//                blnRes=false;
//        }
//        return blnRes;
//    }
//    
    
    
    
     private boolean generaNuevoContenedorItemsMovimientoStock(int intCodEmp, int intCodItm, double dlbCanMov,int intCodBod){
        boolean blnRes=true;
        double dblAux;
        int intCodigoItemGrupo=0, intCodigoItemMaestro=0;
        String strCodTresLetras="";
        try{
            arlDatStkInvItm = new ArrayList(); 
            intCodigoItemGrupo=objDatItm.getCodigoItemGrupo(intCodEmp,intCodItm);
            intCodigoItemMaestro=objDatItm.getCodigoMaestroItem(intCodEmp, intCodItm);
            strCodTresLetras=objDatItm.getCodigoLetraItem(intCodEmp,intCodItm);
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
                objUti.mostrarMsgErr_F1(cmpPad, e);
                blnRes=false;
        }
        return blnRes;
    } 
     
     
    
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
                objUti.mostrarMsgErr_F1(cmpPad, e);
                intCodBodGrp=0;
            }
            return intCodBodGrp; 
        }

        
         /*PARA LOS DOCUMENTOS GENERADOS*/
        private ArrayList arlRegFacVen, arlDatFacVen;
        private ArrayList arlRegIngGen, arlDatIngGen;
        private static final int INT_ARL_COD_EMP_REL=0;
        private static final int INT_ARL_COD_LOC_REL=1;
        private static final int INT_ARL_COD_TIP_DOC_REL=2;
        private static final int INT_ARL_COD_DOC_REL=3;
        private static final int INT_ARL_COD_REG_REL_AUX=4;
        private static final int INT_ARL_COD_ITM_REL=5;
        private static final int INT_ARL_CAN_REL=6;
        private static final int INT_ARL_COD_CAN_UTI=7;

        
        private boolean insertarTablaRelacionadas(java.sql.Connection conn, int intCodCfgIngEgr,int intCodEmp, int intCodLoc,int intCodTipDoc,int intCodDoc,int intCodSeg){
            java.sql.Statement stmLoc, stmIns;
            java.sql.ResultSet rstLocFacVen,rstLocIng;    
            boolean blnRes = true;
            try{
                if(intCodCfgIngEgr==1){ /* JM:  SOLO SI ES UN EGRESO 28/Sep/2017 */
                    if(conn!=null){
                        stmLoc = conn.createStatement();
                        stmIns = conn.createStatement();
                        arlDatFacVen = new ArrayList();
                        arlDatIngGen = new ArrayList();

                        strSql="";
                        strSql+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc,a3.co_reg, a3.co_itm, ABS(a3.nd_can) as nd_can \n";
                        strSql+=" FROM tbm_cabMovInv as a2   \n";
                        strSql+=" INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
                        strSql+=" WHERE a2.co_emp="+intCodEmp+" AND a2.co_loc="+intCodLoc+" AND \n";
                        strSql+="       a2.co_tipDoc="+intCodTipDoc+" AND a2.co_doc="+intCodDoc+" \n";
                        strSql+=" ORDER BY a3.nd_can ";
                        System.out.println("insertarTablaRelacionadas... "+strSql);
                        rstLocFacVen = stmLoc.executeQuery(strSql);
                        while(rstLocFacVen.next()){
                            arlRegFacVen = new ArrayList();
                            arlRegFacVen.add(INT_ARL_COD_EMP_REL, rstLocFacVen.getInt("co_emp"));
                            arlRegFacVen.add(INT_ARL_COD_LOC_REL, rstLocFacVen.getInt("co_loc"));
                            arlRegFacVen.add(INT_ARL_COD_TIP_DOC_REL, rstLocFacVen.getInt("co_tipDoc"));
                            arlRegFacVen.add(INT_ARL_COD_DOC_REL, rstLocFacVen.getInt("co_doc"));
                            arlRegFacVen.add(INT_ARL_COD_REG_REL_AUX, rstLocFacVen.getInt("co_reg"));
                            arlRegFacVen.add(INT_ARL_COD_ITM_REL, rstLocFacVen.getInt("co_itm"));
                            arlRegFacVen.add(INT_ARL_CAN_REL, rstLocFacVen.getDouble("nd_can"));
                            arlRegFacVen.add(INT_ARL_COD_CAN_UTI, 0.00);
                            arlDatFacVen.add(arlRegFacVen);     
                        }
                        rstLocFacVen.close();
                        rstLocFacVen=null;

                        strSql="";
                        strSql+="   SELECT a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, \n";
                        strSql+="          a1.co_docRelCabMovInv,a3.co_reg as co_regRel, a3.co_itm, a3.nd_can \n";
                        strSql+="   FROM tbm_cabSegMovInv as a1 \n";
                        strSql+="   INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND  \n";
                        strSql+="                                      a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc) \n";
                        strSql+="   INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
                        strSql+="   RIGHT JOIN tbr_detMovInv as a5 ON (a2.co_emp=a5.co_empRel AND a2.co_loc=a5.co_locRel AND a2.co_tipDoc=a5.co_tipDocRel AND a2.co_doc=a5.co_docRel) \n";
                        strSql+="   WHERE a1.co_seg=( "+intCodSeg+" ) and a3.nd_can > 0 \n ";
                        strSql+="    GROUP BY a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv,  ";
                        strSql+="               a1.co_docRelCabMovInv,a3.co_reg , a2.co_cli, a3.co_itm, a3.nd_can  ";
                        strSql+="    ORDER BY a3.nd_can DESC ";
                        System.out.println("insertarTablaRelacionadas 2 ... "+strSql);
                        rstLocIng = stmLoc.executeQuery(strSql);
                        while(rstLocIng.next()){
                            arlRegIngGen = new ArrayList();
                            arlRegIngGen.add(INT_ARL_COD_EMP_REL, rstLocIng.getInt("co_empRelCabMovInv"));
                            arlRegIngGen.add(INT_ARL_COD_LOC_REL, rstLocIng.getInt("co_locRelCabMovInv"));
                            arlRegIngGen.add(INT_ARL_COD_TIP_DOC_REL, rstLocIng.getInt("co_tipDocRelCabMovInv"));
                            arlRegIngGen.add(INT_ARL_COD_DOC_REL, rstLocIng.getInt("co_docRelCabMovInv"));
                            arlRegIngGen.add(INT_ARL_COD_REG_REL_AUX, rstLocIng.getInt("co_regRel"));
                            arlRegIngGen.add(INT_ARL_COD_ITM_REL, rstLocIng.getInt("co_itm"));
                            arlRegIngGen.add(INT_ARL_CAN_REL, rstLocIng.getDouble("nd_can"));
                            arlRegIngGen.add(INT_ARL_COD_CAN_UTI, 0.00);
                            arlDatIngGen.add(arlRegIngGen);     
                        }
                        rstLocIng.close();
                        rstLocIng=null;

                        System.out.println("arlDatFacVen: " + arlDatFacVen.toString());
                        System.out.println("arlDatIngGen: " + arlDatIngGen.toString());

                        String strIng="";
                        for(int i=0;i<arlDatFacVen.size();i++){
                            for(int b=0;b<arlDatIngGen.size();b++){
                                if(objUti.getIntValueAt(arlDatFacVen, i, INT_ARL_COD_ITM_REL)==objUti.getIntValueAt(arlDatIngGen, b, INT_ARL_COD_ITM_REL)){
                                    if(objUti.getDoubleValueAt(arlDatIngGen, b, INT_ARL_CAN_REL)>objUti.getDoubleValueAt(arlDatIngGen, b, INT_ARL_COD_CAN_UTI)){
                                        strSql="";
                                        strSql+=" INSERT INTO tbr_detMovInv(co_emp,co_loc,co_tipdoc,co_doc,co_reg,st_reg, \n";
                                        strSql+="                           co_emprel,co_locrel,co_tipdocrel,co_docrel,co_regrel) \n";
                                        strSql+=" VALUES ("+objUti.getIntValueAt(arlDatFacVen, i, INT_ARL_COD_EMP_REL)+",";
                                        strSql+=" "+objUti.getIntValueAt(arlDatFacVen, i, INT_ARL_COD_LOC_REL)+"," ;
                                        strSql+=" "+objUti.getIntValueAt(arlDatFacVen, i, INT_ARL_COD_TIP_DOC_REL)+",";
                                        strSql+=" "+objUti.getIntValueAt(arlDatFacVen, i, INT_ARL_COD_DOC_REL)+",";
                                        strSql+=" "+objUti.getIntValueAt(arlDatFacVen, i, INT_ARL_COD_REG_REL_AUX)+",'A', \n";
                                        strSql+=" "+objUti.getIntValueAt(arlDatIngGen, b, INT_ARL_COD_EMP_REL)+",";
                                        strSql+=" "+objUti.getIntValueAt(arlDatIngGen, b, INT_ARL_COD_LOC_REL)+",";
                                        strSql+=" "+objUti.getIntValueAt(arlDatIngGen, b, INT_ARL_COD_TIP_DOC_REL)+","; 
                                        strSql+=" "+objUti.getIntValueAt(arlDatIngGen, b, INT_ARL_COD_DOC_REL)+",";
                                        strSql+=" "+objUti.getIntValueAt(arlDatIngGen, b, INT_ARL_COD_REG_REL_AUX)+");";
                                        strIng+=strSql;
                                        if(objUti.getDoubleValueAt(arlDatFacVen, i, INT_ARL_CAN_REL)<=objUti.getDoubleValueAt(arlDatIngGen, b, INT_ARL_CAN_REL)){
                                            // FACTURA 5    INGRESO 10 // le sumo lo de la factura
                                            objUti.setDoubleValueAt(arlDatIngGen, b, INT_ARL_COD_CAN_UTI, (objUti.getDoubleValueAt(arlDatIngGen, b, INT_ARL_COD_CAN_UTI)+objUti.getDoubleValueAt(arlDatFacVen, i, INT_ARL_CAN_REL)));
                                        }
                                        else{
                                            //factura 10 ing 4// le sumo el ingreso
                                            objUti.setDoubleValueAt(arlDatIngGen, b, INT_ARL_COD_CAN_UTI, (objUti.getDoubleValueAt(arlDatIngGen, b, INT_ARL_COD_CAN_UTI)+objUti.getDoubleValueAt(arlDatIngGen, b, INT_ARL_CAN_REL)));
                                        }
                                    }
                                }
                            }
                        }
                        System.out.println("insertarTablaRelacionadas: \n" + strIng);
                        stmIns.executeUpdate(strIng);

                        if( isExisteOC(conn, intCodEmp,intCodLoc,intCodTipDoc,intCodDoc)  ){
                                strSql="";
                                strSql+=" INSERT INTO tbr_cabMovInv ( co_emp, co_loc, co_tipdoc,  co_doc , st_reg, co_emprel, co_locrel, co_tipdocrel, co_docrel, st_regrep  ) \n";
                                strSql+=" SELECT DISTINCT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, 'A' as st_reg, b.co_empRelCabMovInv,b.co_locRelCabMovInv, b.co_tipDocRelCabMovInv, b.co_docRelCabMovInv,'I'   \n";
                                strSql+=" FROM ( \n";
                                strSql+="       SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc,a3.co_reg, a3.co_itm, ABS(a3.nd_can) as nd_can \n";
                                strSql+="       FROM ( \n";
                                strSql+="               SELECT co_empRelCabMovInv, co_locRelCabMovInv, co_tipDocRelCabMovInv, co_docRelCabMovInv \n";
                                strSql+="               FROM tbm_cabSegMovInv  \n";
                                strSql+="               WHERE co_empRelCabMovInv="+intCodEmp+" AND co_locRelCabMovInv="+intCodLoc+" AND \n";
                                strSql+="                  co_tipDocRelCabMovInv="+intCodTipDoc+" AND co_docRelCabMovInv="+intCodDoc+" \n";
                                strSql+="       ) as a1 \n";
                                strSql+="       INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND \n";
                                strSql+="                                          a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc ) \n";
                                strSql+="       INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
                                strSql+="       INNER JOIN tbm_inv as a4 ON (a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm) \n";
                                strSql+="       WHERE a3.tx_codAlt like '%L'  \n";
                                strSql+=" ) as a \n";
                                strSql+=" INNER JOIN ( \n";
                                strSql+="   SELECT a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv,a3.co_reg as co_regRel, a2.co_cli, a3.co_itm, a3.nd_can \n";
                                strSql+="   FROM tbm_cabSegMovInv as a1 \n";
                                strSql+="   INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND  \n";
                                strSql+="                                       a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc) \n";
                                strSql+="   INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
                                strSql+="   WHERE a1.co_seg=("+intCodSeg+" ) and a3.nd_can > 0 \n ";
                                strSql+="    GROUP BY a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv,  ";
                                strSql+="               a1.co_docRelCabMovInv,a3.co_reg , a2.co_cli, a3.co_itm, a3.nd_can          ";
                                strSql+=" ) as b ON (a.co_itm=b.co_itm ); \n";

                                strSql+=" INSERT INTo tbr_detMovInv ( co_emp, co_loc, co_tipdoc,  co_doc, co_reg , st_reg, co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel, st_regrep  ) \n";
                                strSql+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc,a.co_reg, 'A' as st_reg, b.co_empRelCabMovInv,b.co_locRelCabMovInv, b.co_tipDocRelCabMovInv, b.co_docRelCabMovInv,b.co_regRel,'I'   \n";
                                strSql+=" FROM ( \n";
                                strSql+="       SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc,a3.co_reg, a3.co_itm, ABS(a3.nd_can) as nd_can \n";
                                strSql+="       FROM ( \n";
                                strSql+="               SELECT co_empRelCabMovInv, co_locRelCabMovInv, co_tipDocRelCabMovInv, co_docRelCabMovInv \n";
                                strSql+="               FROM tbm_cabSegMovInv  \n";
                                strSql+="               WHERE co_empRelCabMovInv="+intCodEmp+" AND co_locRelCabMovInv="+intCodLoc+" AND \n";
                                strSql+="                  co_tipDocRelCabMovInv="+intCodTipDoc+" AND co_docRelCabMovInv="+intCodDoc+" \n";
                                strSql+="       ) as a1 \n";
                                strSql+="       INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND \n";
                                strSql+="                                          a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc ) \n";
                                strSql+="       INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
                                strSql+="       INNER JOIN tbm_inv as a4 ON (a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm) \n";
                                strSql+="       WHERE a3.tx_codAlt like '%L'  \n";
                                strSql+=" ) as a \n";
                                strSql+=" INNER JOIN ( \n";
                                strSql+="   SELECT a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv,a3.co_reg as co_regRel, a2.co_cli, a3.co_itm, a3.nd_can \n";
                                strSql+="   FROM tbm_cabSegMovInv as a1 \n";
                                strSql+="   INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND  \n";
                                strSql+="                                       a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc) \n";
                                strSql+="   INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
                                strSql+="   WHERE a1.co_seg=("+intCodSeg+" ) and a3.nd_can > 0 \n ";
                                strSql+="    GROUP BY a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv,  ";
                                strSql+="               a1.co_docRelCabMovInv,a3.co_reg , a2.co_cli, a3.co_itm, a3.nd_can ";                    
                                strSql+=" ) as b ON (a.co_itm=b.co_itm ); \n";
                                System.out.println("isExisteOC: " + strSql);
                                stmIns.executeUpdate(strSql);
                        }
                        stmIns.close();
                        stmIns=null;        
                        stmLoc.close();
                        stmLoc=null;
                    }
                }
                
            }
            catch (SQLException Evt) {
                blnRes = false;
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(cmpPad, Evt);
            } 
            catch (Exception Evt) {
                blnRes = false;
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(cmpPad, Evt);
            }
            return blnRes;
        }
        
        
        
        private boolean isExisteOC(java.sql.Connection conn, int intCodEmp, int intCodLoc,int intCodTipDoc,int intCodDoc){
            boolean blnRes=false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strCadena;
            try{
                stmLoc=conn.createStatement();
                strCadena="";
                strCadena+=" SELECT a1.co_itm ";
                strCadena+=" FROM tbm_detMovInv as a1  ";
                strCadena+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND ";
                strCadena+=" a1.co_tipDoc="+intCodTipDoc+" AND a1.co_doc="+intCodDoc+" AND a1.tx_codAlt like '%L' ";
                System.out.println("IsExisteOC" + strCadena);
                rstLoc = stmLoc.executeQuery(strCadena);
                if (rstLoc.next()) {
                   blnRes=true;
                }
                stmLoc.close();
                stmLoc=null;
                rstLoc.close();
                rstLoc=null;
            }
            catch (SQLException Evt) {
                blnRes = false;
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(cmpPad, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(cmpPad, Evt);
            }
            return blnRes;
        }





}
