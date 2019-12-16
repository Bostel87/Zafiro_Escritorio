/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafGenFacAut;

import Librerias.ZafGetDat.ZafDatBod;
import Librerias.ZafGetDat.ZafDatItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafUtil.ZafUtil;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *  
 * @author Sistemas6
 */
public class ZafModDatGenFac {
    public String strVersion="ZafModDatGenFac v0.01";        
    private  java.awt.Component jifCnt;
    private String  strSql, strIns;
    private ZafUtil objUti;
    private ZafParSis objParSis;
    private ArrayList arlRegIng, arlDatIng;
    private ArrayList arlRegCot, arlDatCot;
    private ZafDatBod objDatBod;
    private ZafDatItm objDatItm;
    
    private final int INT_TBL_DAT_COT_LIN=0;
    private final int INT_TBL_DAT_COT_COD_EMP=1;  
    private final int INT_TBL_DAT_COT_COD_LOC=2;  
    private final int INT_TBL_DAT_COT_COD_COT=3;  	
    private final int INT_TBL_DAT_COT_COD_REG=3;  	
    private final int INT_TBL_DAT_COT_COD_ITM=4;  	
    private final int INT_TBL_DAT_COT_CAN=5;  	
    private final int INT_TBL_DAT_COT_CAN_UTI=6;  	
    private final int INT_TBL_DAT_COT_IS_RES=7;
    
    private final int INT_TBL_DAT_ING_LIN=0;
    private final int INT_TBL_DAT_ING_COD_ITM=1;
    private final int INT_TBL_DAT_ING_CAN=2;
    private final int INT_TBL_DAT_ING_CAN_UTI=3;
    private final int INT_TBL_DAT_ING_COD_BOD_ING=4;
    private final int INT_TBL_DAT_ING_COD_BOD_EGR=5;
    
    
    
    public ZafModDatGenFac(ZafParSis obj, java.awt.Component parent){
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            jifCnt=parent;
            objDatBod = new Librerias.ZafGetDat.ZafDatBod(objParSis, jifCnt);
            objDatItm = new Librerias.ZafGetDat.ZafDatItm(objParSis, jifCnt);
            System.out.println(strVersion);
        }
        catch (CloneNotSupportedException e){
            System.out.println("ZafModDatGenFac: " + e);
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
    }
    
    /**
     * Modifica las cantidades Locales contra las remotas en una cotizacion
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
    
    public boolean modificaCantidadesLocalesRemotasCotizacion(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
        String strIns="";
        int intCodItm=-1,intCodReg=-1,intCodBodEgr=-1,intCodBodIng=-1;
        int intCodItmIng=-1;
        double dblCanCot=0.00, dblCanCotUti=0,dblCanIng=0,dblCanIngUti=0,dblCanAux=0;
        try{
            if(conExt!=null){
                if(cargarDatosIngresosPorCotizacion(conExt,CodEmp,CodLoc,CodCot)){
                    if(cargarDatosCotizacion(conExt,CodEmp,CodLoc,CodCot)){
                        for(int i=0;i<arlDatCot.size();i++){
                            intCodReg = objUti.getIntValueAt(arlDatCot,i,INT_TBL_DAT_COT_COD_REG);
                            dblCanCot = objUti.getDoubleValueAt(arlDatCot,i,INT_TBL_DAT_COT_CAN);
                            dblCanCotUti = objUti.getDoubleValueAt(arlDatCot,i,INT_TBL_DAT_COT_CAN_UTI);
                            intCodItm = objUti.getIntValueAt(arlDatCot,i,INT_TBL_DAT_COT_COD_ITM);
                            System.out.println("Cot: " + intCodReg+" Itm: "+intCodItm+ " Can: " + dblCanCot+" Uti:" + dblCanCotUti);
                            for(int j=0;j<arlDatIng.size();j++){
                                intCodItmIng = objUti.getIntValueAt(arlDatIng,j,INT_TBL_DAT_ING_COD_ITM);
                                dblCanIng = objUti.getDoubleValueAt(arlDatIng,j,INT_TBL_DAT_ING_CAN);
                                dblCanIngUti = objUti.getDoubleValueAt(arlDatIng, j, INT_TBL_DAT_ING_CAN_UTI);                               
                                intCodBodEgr=objUti.getIntValueAt(arlDatIng,j,INT_TBL_DAT_ING_COD_BOD_EGR);
                                intCodBodIng=objUti.getIntValueAt(arlDatIng,j,INT_TBL_DAT_ING_COD_BOD_ING);
                                System.out.println("ItmIng: "+intCodItmIng+" CanIng: " + dblCanIng+" CanUtiIng:" + dblCanIngUti+" BodEgr: " + intCodBodEgr + " BodIng: " + intCodBodIng);
                                if(intCodItm==intCodItmIng){
                                    if(objUti.getStringValueAt(arlDatCot, i, INT_TBL_DAT_COT_IS_RES).equals("N")){
                                        if(dblCanCotUti<dblCanCot && dblCanIngUti<dblCanIng){
                                            if(intCodBodEgr!=intCodBodIng){
                                                if(dblCanCot>=dblCanIng){
                                                    if(dblCanCotUti<=dblCanIng){
                                                        objUti.setDoubleValueAt(arlDatCot, i, INT_TBL_DAT_COT_CAN_UTI, dblCanCotUti + dblCanIng);
                                                        objUti.setDoubleValueAt(arlDatIng, j, INT_TBL_DAT_ING_CAN_UTI, dblCanIngUti +dblCanIng);
                                                        dblCanCotUti+=dblCanIng;
                                                        if(!modificarDetalleCotizacion(conExt,true, CodEmp,CodLoc,CodCot,intCodReg,0.00,dblCanIng)){
                                                            blnRes=false;
                                                        }
                                                    }
                                                    else{
                                                        dblCanAux=dblCanIng-dblCanCotUti; //  COT 50  
                                                        objUti.setDoubleValueAt(arlDatCot, i, INT_TBL_DAT_COT_CAN_UTI, dblCanCotUti + dblCanAux);
                                                        objUti.setDoubleValueAt(arlDatIng, j, INT_TBL_DAT_ING_CAN_UTI, dblCanIngUti +dblCanCotUti);
                                                        dblCanCotUti+=dblCanAux;
                                                        if(!modificarDetalleCotizacion(conExt,true, CodEmp,CodLoc,CodCot,intCodReg,0.00,dblCanAux)){
                                                            blnRes=false;
                                                        }
                                                    }
                                                }
                                                else{
                                                    objUti.setDoubleValueAt(arlDatCot, i, INT_TBL_DAT_COT_CAN_UTI, dblCanCot);
                                                    objUti.setDoubleValueAt(arlDatIng, j, INT_TBL_DAT_ING_CAN_UTI, dblCanIngUti + dblCanCot);
                                                    dblCanCotUti+=dblCanCot;
                                                    if(!modificarDetalleCotizacion(conExt,true, CodEmp,CodLoc,CodCot,intCodReg,0.00,dblCanCot)){
                                                        blnRes=false;
                                                    }
                                                }
                                            }
                                            else{
                                                if(dblCanCotUti<dblCanCot && dblCanIngUti<dblCanIngUti){
                                                    System.out.println("item de prestamos misma bodega... sale de bodega");
                                                    if(dblCanIng<dblCanCot){
                                                        objUti.setDoubleValueAt(arlDatCot, i, INT_TBL_DAT_COT_CAN_UTI, dblCanCotUti + dblCanIng);
                                                        objUti.setDoubleValueAt(arlDatIng, j, INT_TBL_DAT_ING_CAN_UTI, dblCanIngUti +dblCanIng);
                                                        dblCanCotUti+=dblCanIng;
                                                        if(!modificarDetalleCotizacion(conExt,true, CodEmp,CodLoc,CodCot,intCodReg,dblCanIng,0.0)){
                                                            blnRes=false;
                                                        }
                                                    }
                                                    else{
                                                        dblCanAux = dblCanCot - dblCanCotUti;
                                                        objUti.setDoubleValueAt(arlDatCot, i, INT_TBL_DAT_COT_CAN_UTI, dblCanCotUti + dblCanAux);
                                                        objUti.setDoubleValueAt(arlDatIng, j, INT_TBL_DAT_ING_CAN_UTI, dblCanIngUti +dblCanAux);
                                                        dblCanCotUti+=dblCanIng;
                                                        if(!modificarDetalleCotizacion(conExt,true, CodEmp,CodLoc,CodCot,intCodReg,dblCanAux,0.0)){
                                                            blnRes=false;
                                                        }
                                                    }
                                                    
                                                }
                                            }
                                        }
                                    }
                                    else{
                                        System.out.println("ITEM DE RESERVA TODO LOCAL");
                                        objUti.setDoubleValueAt(arlDatCot, i, INT_TBL_DAT_COT_CAN_UTI, dblCanCot);
                                        dblCanCotUti+=dblCanCot;
                                        if(!modificarDetalleCotizacion(conExt,false, CodEmp,CodLoc,CodCot,intCodReg,dblCanCot,0.00)){
                                            blnRes=false;
                                        }
                                        break;
                                    }
                                }
                            }   /* <FIN FOR INGRESOS> */ 
                        } /* <FIN FOR COTIZACION> */
                        if(!modificarDetalleCotizacionTodoResLoc(conExt,CodEmp,CodLoc,CodCot )){
                            blnRes=false;
                        }
                    }
                }
            }
        }
         
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }
    
    
    
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
    
    private ZafStkInv objStkInv;
    
    /**
     * En el caso de los items que son facturados y vienen de una reserva, esos items ya no esta disponibles por lo tanto no se sacan del disponible 
     *      ni se suman a la cantidad reservada en ventas
     * @param conExt
     * @param CfgPro  0 Transferencias de venta 1 Reservas, 
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
    public boolean modificaCantidadesLocalesPonerReservadas(java.sql.Connection conExt,int CfgPro, int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);
                strSql="";
                strSql+=" SELECT a2.co_emp,a2.co_loc,a2.co_cot,a2.co_reg,a2.co_itm, a2.tx_codAlt,a2.co_bod,a2.nd_can,a2.nd_canRem, \n";
                strSql+="        CASE WHEN a2.nd_canLoc IS NULL THEN 0 ELSE a2.nd_canLoc END as nd_canLoc,     \n";
                strSql+="         CASE WHEN a3.co_empRel IS NULL THEN 'N' ELSE 'S' END AS IsRes  \n";
                strSql+=" FROM tbm_detCotVen as a2 \n";
                strSql+=" INNER JOIN tbm_inv as a4 ON (a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm) ";
                strSql+=" LEFT OUTER JOIN tbr_detCotVen as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_cot=a3.co_cot AND a2.co_reg=a3.co_reg) \n";
                strSql+=" WHERE a2.co_emp="+CodEmp+" AND a2.co_loc="+CodLoc+" AND a2.co_cot="+CodCot+" AND a4.st_ser='N' \n";
                strSql+=" ORDER BY a2.co_reg  \n";
                System.out.println("modificaCantidadesLocalesPonerReservadas: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    String strTerminal=rstLoc.getString("tx_codAlt").substring(rstLoc.getString("tx_codAlt").length()-1);
                    if(generaNuevoContenedorItemsMovimientoStock(CodEmp,rstLoc.getInt("co_itm"),rstLoc.getDouble("nd_canLoc"), objDatBod.getbodegaPredeterminadaPorLocal(CodEmp, CodLoc) )){
                        if(strTerminal.equals("I") || strTerminal.equals("S")){
                            if(rstLoc.getString("IsRes").equals("S")){
                                System.out.println("item de reserva: "+rstLoc.getString("tx_codAlt")+"  ");
                            }
                            else{
                                if(objStkInv.actualizaInventario(conExt, CodEmp,INT_ARL_STK_INV_CAN_DIS, "-", 0, arlDatStkInvItm)){
                                    if(CfgPro==0){
                                        if(objStkInv.actualizaInventario(conExt, CodEmp,INT_ARL_STK_INV_CAN_RES_VEN, "+", 0, arlDatStkInvItm)){ 
                                            System.out.println("ZafModDatGenFac.Mover Inventario ZafStkInv....  ");
                                        }else{blnRes=false;}
                                    }
                                }else{blnRes=false;}
                            }
                        }
                        else if(strTerminal.equals("L")){ /*TERMINALES L EN INMACONSA SI NECESITAN CONFIRMACIONES*/  
                            if(rstLoc.getString("IsRes").equals("S")){
                                System.out.println("item de reserva: "+rstLoc.getString("tx_codAlt")+"  ");
                            }
                            else{
                                if(15==objDatBod.getCodigoBodegaGrupo(CodEmp, objDatBod.getbodegaPredeterminadaPorLocal(CodEmp, CodLoc))){
                                    if(CfgPro==0){
                                        if(objStkInv.actualizaInventario(conExt, CodEmp,INT_ARL_STK_INV_CAN_DIS, "-", 0, arlDatStkInvItm)){
                                            System.out.println("ZafModDatGenFac.Mover Inventario ZafStkInv....  ");
                                        }else{blnRes=false;}
                                        if(objStkInv.actualizaInventario(conExt, CodEmp,INT_ARL_STK_INV_CAN_RES_VEN, "+", 0, arlDatStkInvItm)){ 
                                            System.out.println("ZafModDatGenFac.Mover Inventario ZafStkInv....  ");
                                        }else{blnRes=false;}
                                    }
                                }
                            }
                            
                        }
                    }
                }
                rstLoc.close();
                rstLoc=null;
                objStkInv = null;
                stmLoc.close();
                stmLoc=null;
            }
        }
         catch (SQLException Evt){
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
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
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
        }
        return blnRes;
    } 
    
    
    private boolean modificarDetalleCotizacionTodoResLoc(java.sql.Connection conExt,int CodEmp,int CodLoc,int CodCot ){
        boolean blnRes=true;
        java.sql.Statement stmLoc, stmIns;
        java.sql.ResultSet rstLoc;
        double dblCanFal=0.00;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                stmIns = conExt.createStatement();
                strSql="";
                strSql+=" SELECT a2.co_emp,a2.co_loc,a2.co_cot,a2.co_reg, \n";
                strSql+="       CASE WHEN a2.nd_can IS NULL THEN 0 ELSE a2.nd_can END AS nd_can,\n";
                strSql+="       CASE WHEN a2.nd_canLoc IS NULL THEN 0 ELSE a2.nd_canLoc END AS nd_canLoc, \n";
                strSql+="       CASE WHEN a2.nd_canRem IS NULL THEN 0 ELSE a2.nd_canRem END AS nd_canRem \n";
                strSql+=" FROM tbm_cabCotVen AS a1 \n";
                strSql+=" INNER JOIN tbm_detCotVen AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot)  \n";
                strSql+=" INNER JOIN tbm_inv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)  \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" AND a3.st_ser='N' \n";
                strSql+=" ORDER BY a2.co_emp,a2.co_loc,a2.co_cot,a2.co_reg ";
                System.out.println("modificarDetalleCotizacionTodoResLoc: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    dblCanFal = rstLoc.getDouble("nd_can")- ( rstLoc.getDouble("nd_canLoc") + rstLoc.getDouble("nd_canRem") );
                    if(dblCanFal>0){
                        strSql="";
                        strSql+=" UPDATE tbm_detCotVen SET nd_canLoc = CASE WHEN nd_canLoc IS NULL THEN 0 ELSE nd_canLoc END +  "+dblCanFal+" \n";                       
                        strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+" AND co_reg="+rstLoc.getInt("co_reg")+" ; \n";
                        System.out.println("modificarDetalleCotizacionTodoResLoc: \n" + strSql);
                        stmIns.executeUpdate(strSql);
                    }
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc=null;
                stmIns.close();
                stmIns=null;
                
            }
        }
        catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }
    
    private boolean IsDosAreas(int CodEmp, int CodLoc){
        boolean blnRes=false;
        try{
            int intCodBodGrp=objDatBod.getCodigoBodegaGrupo(CodEmp, objDatBod.getbodegaPredeterminadaPorLocal(CodEmp, CodLoc));
            if(intCodBodGrp==15 || intCodBodGrp==3){
                blnRes=true;
            }
            else{
                blnRes=false;
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        
        return blnRes;
    }
    
    /**
     * Modifica los detalles de la cotizacion segun la necesidad, si se suman los valores o se asignan directamente
     * @param conExt
     * @param cfg TRUE: Suma los valores FALSE: Asigna los valores
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @param CodReg
     * @param CanLoc
     * @param CanRem
     * @return 
     */
    
    private boolean modificarDetalleCotizacion(java.sql.Connection conExt, boolean cfg, int CodEmp, int CodLoc, int CodCot, int CodReg, Double CanLoc, Double CanRem){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                if(cfg){
                    strIns="";
                    strIns+=" UPDATE tbm_detCotVen SET nd_canLoc=(CASE WHEN nd_canLoc IS NULL THEN 0 ELSE nd_canLoc END) + "+CanLoc+" , \n";
                    strIns+="                          nd_canRem=(CASE WHEN nd_canRem IS NULL THEN 0 ELSE nd_canRem END) + "+CanRem+" \n";
                    strIns+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+" AND co_reg="+CodReg+" ; \n";   
                }
                else{
                    strIns="";
                    strIns+=" UPDATE tbm_detCotVen SET nd_canLoc="+CanLoc+" , \n";
                    strIns+="                          nd_canRem="+CanRem+" \n";
                    strIns+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+" AND co_reg="+CodReg+" ; \n";   
                }
                System.out.println("modificarDetalleCotizacion: " + strIns);
                stmLoc.executeUpdate(strIns);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }
    
    /**
     * Cargar los datos del ingreso 
     * 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
    private boolean cargarDatosIngresosPorCotizacion(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                arlDatIng = new ArrayList();
                strSql="";
                strSql+=" SELECT x.co_itm,x.co_bodGrpIng, x.co_bodGrpEgr, SUM(x.nd_can) as nd_canIng   \n";
                strSql+=" FROM(  \n";
                strSql+="       SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc,a3.co_reg, a3.co_itm , \n";
                strSql+="              a8.co_bodGrp ,a8.co_bodGrp as co_bodGrpIng ,a11.co_bodGrp as co_bodGrpEgr, a3.nd_can \n";
                strSql+="       FROM tbm_cabMovInv as a2  /*EL INGRESO */  \n";
                strSql+="       INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND  \n";
                strSql+="                                          a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc)  \n";
                strSql+="       INNER JOIN (    \n";
                strSql+="             SELECT a1.co_emp AS co_empIng, a1.co_loc as co_locIng, a1.co_tipDoc as co_tipDocIng, a1.co_doc as co_docIng   \n";
                strSql+="             FROM tbm_cabMovInv as a1  \n";
                strSql+="             INNER JOIN tbm_cabSegMovInv as a2 ON (a1.co_emp=a2.co_empRelCabMovInv AND a1.co_loc=a2.co_locRelCabMovInv AND  \n";
                strSql+="                                                   a1.co_tipDoc=a2.co_tipDocRelCabMovInv AND a1.co_doc=a2.co_docRelCabMovInv)\n";
                strSql+="             INNER JOIN (   \n";
                strSql+="                   SELECT a1.co_seg  \n";
                strSql+="                   FROM tbm_cabSegMovInv as a1  \n";
                strSql+="                   INNER JOIN tbm_cabCotVen as a2 ON (a1.co_empRelCabCotVen=a2.co_emp AND a1.co_locRelCabCotVen=a2.co_loc AND  \n";
                strSql+="                                                      a1.co_cotRelCabCotVen=a2.co_cot)   \n";
		strSql+="                   WHERE a2.co_emp="+CodEmp+" AND a2.co_loc="+CodLoc+" AND a2.co_cot="+CodCot+"    \n";		         
                strSql+="              ) AS a3 ON (a2.co_seg=a3.co_seg)  \n";
                strSql+="              INNER JOIN tbm_cabTipDoc as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)  \n";
                strSql+="              WHERE a1.nd_tot>0  \n";
                strSql+="       ) as a7 ON (a2.co_emp=a7.co_empIng AND a2.co_loc=a7.co_locIng AND a2.co_tipDoc=a7.co_tipDocIng AND  \n";
                strSql+="                   a2.co_doc=a7.co_docIng)  \n";		 
                strSql+="       INNER JOIN tbr_bodEmpBodGrp AS a8 ON (a3.co_emp=a8.co_emp AND a3.co_bod=a8.co_bod AND a8.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" )  \n";
		strSql+="       INNER JOIN tbr_detMovInv AS a9 ON (a3.co_emp=a9.co_empRel AND a3.co_loc=a9.co_locRel AND a3.co_tipDoc=a9.co_tipDocRel AND  \n";
                strSql+="                                           a3.co_doc=a9.co_docRel AND a3.co_reg=a9.co_RegRel)   \n";
                strSql+="       INNER JOIN tbm_detMovInv AS a10 ON (a9.co_emp=a10.co_emp AND a9.co_loc=a10.co_loc AND a9.co_tipDoc=a10.co_tipDoc AND a9.co_doc=a10.co_doc) \n";
                strSql+="       INNER JOIN tbr_bodEmpBodGrp AS a11 ON (a10.co_emp=a11.co_emp AND a10.co_bod=a11.co_bod AND a11.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" )  \n";
                strSql+="       WHERE a3.nd_can>0 AND a10.nd_can < 0  \n"; 
                strSql+="       GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc,a3.co_reg, a3.co_itm,a8.co_bodGrp   ,a11.co_bodGrp, a3.nd_can  \n";
		strSql+=" ) as x  \n";
		strSql+=" GROUP BY x.co_itm,x.co_bodGrpIng, x.co_bodGrpEgr  \n";  
                strSql+=" ORDER BY x.co_itm \n";
                System.out.println("cargarDatosIngresosPorCotizacion: \n" + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    arlRegIng = new ArrayList();
                    arlRegIng.add(INT_TBL_DAT_ING_LIN, "");
                    arlRegIng.add(INT_TBL_DAT_ING_COD_ITM,rstLoc.getInt("co_itm"));
                    arlRegIng.add(INT_TBL_DAT_ING_CAN,rstLoc.getDouble("nd_canIng"));
                    arlRegIng.add(INT_TBL_DAT_ING_CAN_UTI,0.00);
                    arlRegIng.add(INT_TBL_DAT_ING_COD_BOD_ING, rstLoc.getInt("co_bodGrpIng"));
                    arlRegIng.add(INT_TBL_DAT_ING_COD_BOD_EGR, rstLoc.getInt("co_bodGrpEgr"));
                    arlDatIng.add(arlRegIng);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }
    
    /**
     * Carga todos los items de la cotizacion excepto los servicios
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
    private boolean cargarDatosCotizacion(java.sql.Connection conExt,int CodEmp,int CodLoc,int CodCot){
        boolean blnRes=true;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                arlDatCot = new ArrayList();
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_loc,a2.co_cot,a2.co_reg, a2.co_itm, a2.nd_can,  \n";
                strSql+="        CASE WHEN a3.co_empRel IS NULL THEN 'N' ELSE 'S' END AS IsRes \n";
                strSql+=" FROM tbm_cabCotVen as a1 \n";
                strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+=" INNER JOIN tbm_inv as a4 ON (a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm) \n";
                strSql+=" LEFT OUTER JOIN tbr_detCotVen as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_cot=a3.co_cot AND a2.co_reg=a3.co_reg) \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" AND a4.st_ser='N' \n";
                strSql+=" ORDER BY a2.co_reg \n";
                System.out.println("cargarDatosCotizacion:" + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    arlRegCot = new ArrayList(); 
                    arlRegCot.add(INT_TBL_DAT_COT_LIN, "");
                    arlRegCot.add(INT_TBL_DAT_COT_COD_EMP,rstLoc.getInt("co_emp"));
                    arlRegCot.add(INT_TBL_DAT_COT_COD_LOC,rstLoc.getInt("co_loc"));
                    arlRegCot.add(INT_TBL_DAT_COT_COD_COT,rstLoc.getInt("co_cot"));
                    arlRegCot.add(INT_TBL_DAT_COT_COD_REG,rstLoc.getInt("co_reg"));
                    arlRegCot.add(INT_TBL_DAT_COT_COD_ITM,rstLoc.getInt("co_itm"));
                    arlRegCot.add(INT_TBL_DAT_COT_CAN,rstLoc.getInt("nd_can"));
                    arlRegCot.add(INT_TBL_DAT_COT_CAN_UTI,0.00);
                    arlRegCot.add(INT_TBL_DAT_COT_IS_RES,rstLoc.getString("IsRes"));// S - N
                    arlDatCot.add(arlRegCot);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }
    
    
    private ArrayList arlDatFacVen, arlDatIngGen;
    private ArrayList arlRegFacVen, arlRegIngGen;
    private static final int INT_ARL_COD_EMP_REL=0;
    private static final int INT_ARL_COD_LOC_REL=1;
    private static final int INT_ARL_COD_TIP_DOC_REL=2;
    private static final int INT_ARL_COD_DOC_REL=3;
    private static final int INT_ARL_COD_REG_REL_AUX=4;
    private static final int INT_ARL_COD_ITM_REL=5;
    private static final int INT_ARL_CAN_REL=6;
    private static final int INT_ARL_COD_CAN_UTI=7;
    
    /**
         * Nueva forma de insert
         * 
         * 
         * @param conn
         * @param rstExt
         * @param intCodTipDoc
         * @param intCodDoc
         * @return 
         */
        
        public boolean insertarTablaRelacionadas(java.sql.Connection conn,int intCodEmp, int intCodLoc,int intCodTipDoc,int intCodDoc,int intCodCot){
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLocFacVen,rstLocIng;    
            boolean blnRes = true;
            try{
                if(conn!=null){
                    stmLoc = conn.createStatement();
                    arlDatFacVen = new ArrayList();
                    arlDatIngGen = new ArrayList();
                    
                    strSql="";
                    strSql+="       SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc,a3.co_reg, a3.co_itm, ABS(a3.nd_can) as nd_can \n";
                    strSql+="       FROM( \n";
                    strSql+="            SELECT co_empRelCabMovInv, co_locRelCabMovInv, co_tipDocRelCabMovInv, co_docRelCabMovInv \n";
                    strSql+="            FROM tbm_cabSegMovInv \n";
                    strSql+="            WHERE co_empRelCabMovInv="+intCodEmp+" AND co_locRelCabMovInv="+intCodLoc+" AND \n";
                    strSql+="                  co_tipDocRelCabMovInv="+intCodTipDoc+" AND co_docRelCabMovInv="+intCodDoc+" \n";
                    strSql+="       ) as a1  \n";
                    strSql+="       INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND  \n";
                    strSql+="                                          a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc ) \n";
                    strSql+="       INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
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
                    strSql+="   WHERE a1.co_seg=(select co_seg from tbm_cabSegMovInv \n";
                    strSql+="                    where co_empRelCabCotVen="+intCodEmp+" and co_locRelCabCotVen="+intCodLoc+" \n";
                    strSql+="                          and co_cotRelCabCotVen="+intCodCot+" ) and a3.nd_can > 0 \n ";
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
                    stmLoc.executeUpdate(strIng);
                    
                    stmLoc.close();
                    stmLoc=null;
                }
            }
            catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            } 
            catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            }
            return blnRes;
        }
    
    public boolean cuadraStockSegunMovimientos(java.sql.Connection conExt, int CodEmp, int CodLoc,int CodTipDoc, int CodDoc){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp,a1.co_loc,a1.co_tipDoc,a1.co_doc, a2.co_reg,a2.co_itm,a2.co_bod  \n ";
                strSql+=" FROM tbm_cabMovInv as a1 \n";
                strSql+=" INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND \n";
                strSql+="                                    a1.co_doc=a2.co_doc) \n";
                strSql+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_tipDoc="+CodTipDoc+" AND a1.co_doc="+CodDoc+" AND \n";
                strSql+="        a3.st_ser='N' AND (a2.tx_codAlt like '%I' OR a2.tx_codAlt like '%S')   \n";
                System.out.println("cuadraStockSegunMovimientos: "+ strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    if(querysIngrid(conExt,rstLoc.getInt("co_emp"),rstLoc.getInt("co_bod"),rstLoc.getInt("co_itm"))){
                        if(disponible(conExt,rstLoc.getInt("co_emp"),rstLoc.getInt("co_bod"),rstLoc.getInt("co_itm")) ){
                            if(stocktbm_inv(conExt,rstLoc.getInt("co_emp"))){
                                
                            }else{blnRes=false; }
                        }else{blnRes=false; }
                    }else{blnRes=false; }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        
        return blnRes;
    }    
    
    
    
    private boolean stocktbm_inv(java.sql.Connection conExt, int CodEmp){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                strSql="";
                strSql+=" update tbm_inv set nd_stkact=a.sum FROM ( select * from(  select * from(   select co_itm,nd_stkact   \n";
                strSql+=" from tbm_inv where co_emp="+CodEmp+"  \n";
                strSql+="  ) as a , (   select co_itm as coitm, sum(nd_stkact) from tbm_invbod where co_emp="+CodEmp+" group by co_itm  \n";
                strSql+=" ) as b where b.coitm=a.co_itm ) as x where x.nd_stkact <> sum   \n";
                strSql+=" ) AS a where tbm_inv.co_emp="+CodEmp+" and tbm_inv.co_itm=a.coitm ;   \n";
                System.out.println("Stock_tbm_inv: "+strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }
        catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        return blnRes;
    }
    
    
    
    private boolean disponible(java.sql.Connection conExt, int CodEmp, int CodBod, int CodItm){
        boolean blnRes=false ;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                strSql="";
                strSql+=" UPDATE tbm_invBod SET nd_canDis=a.nd_canDisCal \n";
                strSql+=" FROM ( \n";
                strSql+="   SELECT a2.tx_codAlt,a1.co_emp,a1.co_bod,a1.co_itm,a1.nd_stkAct, a1.nd_canDis, a1.nd_canIngBod, a1.nd_canRes,a1.nd_canResVen, \n";
                strSql+="          a1.nd_stkAct - CASE WHEN a1.nd_canIngBod IS NULL THEN 0 ELSE CASE WHEN a1.nd_canIngBod <= 0 THEN 0 ELSE a1.nd_canIngBod END END \n";
                strSql+="                       - CASE WHEN a1.nd_canRes IS NULL THEN 0 ELSE CASE WHEN a1.nd_canRes <= 0 THEN 0 ELSE a1.nd_canRes END END \n";
                strSql+="                       - CASE WHEN a1.nd_canResVen IS NULL THEN 0 ELSE CASE WHEN a1.nd_canResVen <= 0 THEN 0 ELSE a1.nd_canResVen END END \n";
                strSql+="           AS nd_canDisCal  \n";
                strSql+="   FROM tbm_invBod as a1 \n";
                strSql+="   INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)  \n";
                strSql+="   WHERE CASE WHEN a1.nd_canDis IS NULL THEN 0 ELSE a1.nd_canDis END > a1.nd_stkAct and a1.co_emp>0  \n";
                strSql+="         AND a1.co_emp="+CodEmp+" and a1.co_bod="+CodBod+" and a1.co_itm="+CodItm+" ";
                strSql+=" )AS a WHERE  \n";
                strSql+=" tbm_invBod.co_emp=a.co_emp AND tbm_invBod.co_itm=a.co_itm AND tbm_invBod.co_bod=a.co_bod \n";
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(jifCnt, e);
            blnRes=false;
        } 
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(jifCnt, e);
            blnRes=false;
        }  
        return blnRes;
    }
 
    
    private boolean querysIngrid(java.sql.Connection conExt, int CodEmp, int CodBod, int CodItm){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                strSql="";
                strSql+=" update tbm_invbod set nd_stkact=a.mov FROM ( ";
                strSql+=" select * from ( ";
                strSql+="       select w.co_itm ";
                strSql+="              , CASE WHEN x.mov IS NULL THEN 0 ELSE x.mov END AS mov ";
                strSql+="            , CASE WHEN y.sum IS NULL THEN 0 ELSE y.sum END AS sum";
                strSql+="       from ( ";
                strSql+="           select c.co_itm from tbm_inv as c ";
                strSql+="                   where  c.st_reg not in ('I','E','X') ";
                strSql+="               and c.co_emp="+CodEmp+" and c.st_ser='N' ";
                strSql+="            group by c.co_itm ";
                strSql+="       ) as  w";
                strSql+="   LEFT OUTER JOIN( ";
                strSql+="       select sum(b.nd_can) as mov,b.co_itm from tbm_cabmovinv as a ";
                strSql+="       inner join tbm_detmovinv as b on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc)   ";
                strSql+="       inner join tbm_inv as c on(b.co_emp=c.co_emp and b.co_itm=c.co_itm) ";
                strSql+="       where  a.st_reg not in ('I','E','X','O')    and  ( a.st_tipdev='C' or  a.st_tipdev is null ) ";
                strSql+="       and a.co_emp="+CodEmp+" and c.st_ser='N'   and  b.co_bod="+CodBod+" AND b.co_itm="+CodItm+"  ";
                strSql+="       AND (CASE WHEN b.st_Reg IS NULL THEN 'A' ELSE b.st_Reg END ) NOT IN ('I')  \n";
                strSql+="       group by b.co_itm ";
                strSql+="   ) as  x ";
                strSql+="   ON w.co_itm=x.co_itm ";
                strSql+="   LEFT OUTER JOIN( ";
                strSql+="       select  sum(nd_stkact) AS sum,co_itm as coitm from tbm_invbod where co_emp="+CodEmp+" and co_bod="+CodBod+" AND ";
                strSql+="                                                                           co_itm="+CodItm+" ";
                strSql+="       group by co_itm ";
                strSql+="   ) as y  ";
                strSql+="   ON w.co_itm=y.coitm  ";
                strSql+=" ) as x ";
                strSql+=" WHERE x.mov<>x.sum  ";
                strSql+=" ) AS a  ";
                strSql+=" WHERE tbm_invbod.co_emp="+CodEmp+" and tbm_invbod.co_bod="+CodBod+" and tbm_invbod.co_itm=a.co_itm; ";
                System.out.println("querysIngrid: \n" + strSql);
                stmLoc.executeUpdate(strSql);
                System.out.println("querysIngrid OK ");
                stmLoc.close();
                stmLoc=null;
                blnRes=true; // OK OK OK OK 
            }
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(jifCnt, e);
            blnRes=false;
            
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(jifCnt, e);
            blnRes=false;
        }  
        return blnRes;
    }
        
    /**
     * JM
     * Solo se puede utilizar para cotizaciones que sean de reserva por factura automatica,
     * utiliza los datos de la tabla de pedidos segun los pedidos organiza los datos los codigos L siempre seran remotos 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    public boolean modificaDetalleCotizacionLocalRemoto(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
        java.sql.Statement stmLoc ;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();  
                strSql="";
                strSql+=" UPDATE tbm_detCotVen SET nd_canLoc=0, nd_canRem=0 \n";
                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+"; ";
                stmLoc.executeUpdate(strSql);
                
                strSql="";
                strSql+=" SELECT a.*, CASE WHEN a.nd_canAut IS NULL THEN a.nd_can  \n";
                strSql+="        ELSE CASE WHEN a.co_bodPreGrp=a.co_bodPreGrpIng THEN a.nd_canAut ELSE 0 END END AS nd_canLoc,   \n";
                strSql+="        CASE WHEN a.nd_canAut IS NULL THEN 0  \n";
                strSql+="        ELSE CASE WHEN a.co_bodPreGrp <> a.co_bodPreGrpIng THEN a.nd_canAut ELSE 0 END END AS nd_canRem  \n";
                strSql+=" FROM ( \n";
                strSql+="       SELECT a2.co_emp, a2.co_loc, a2.co_cot, a2.co_reg, a2.co_itm, a2.tx_codAlt, a2.nd_can, a5.co_bodGrp as co_bodPreGrp, \n";
                strSql+="              '--->'as x, a3.co_empRel, a3.co_bodRel, a3.co_itmRel, a3.nd_canAut, a6.co_bodGrp as co_bodPreGrpIng  \n";
                strSql+="       FROM tbm_cabCotVen AS a1   \n";
                strSql+="       INNER JOIN tbm_detCotVen AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+="       INNER JOIN tbr_bodloc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a4.st_reg='P')  \n";
                strSql+="       INNER JOIN tbr_bodEmpBodGrp AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_bod=a5.co_bod)  \n";
                strSql+="       INNER JOIN tbm_inv AS a7 ON (a2.co_emp=a7.co_emp AND a2.co_itm=a7.co_itm)  \n";
                strSql+="       LEFT OUTER JOIN tbm_pedOtrBodCotVen AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND \n";
                strSql+="                                                     a2.co_cot=a3.co_cot AND a2.co_reg=a3.co_reg)  \n";
                strSql+="       LEFT OUTER JOIN tbr_bodEmpBodGrp AS a6 ON (a3.co_emp=a6.co_emp AND a3.co_bodRel=a6.co_bod)  \n";
                strSql+="       WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" AND a7.st_ser = 'N' AND  \n";
                strSql+="             (a2.tx_codAlt like '%I' OR a2.tx_codAlt like '%S')  \n";
                strSql+=" ) as a \n";
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    if(!modificarDetalleCotizacion(conExt,true, CodEmp,CodLoc,CodCot,rstLoc.getInt("co_reg"),rstLoc.getDouble("nd_canLoc"),rstLoc.getDouble("nd_canRem"))){
                        blnRes=false;
                    }
                }
                /* JM: Codigos L siempre remotos */
                strSql="";
                strSql+=" SELECT a2.co_emp, a2.co_loc,a2.co_cot,a2.co_reg,a2.co_itm,a2.tx_codAlt,a2.nd_can \n";
                strSql+=" FROM tbm_cabCotVen as a1   \n";
                strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+"  AND  \n";
                strSql+="      (a2.tx_codAlt like '%L' )  \n";
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    if(!modificarDetalleCotizacion(conExt,true, CodEmp,CodLoc,CodCot,rstLoc.getInt("co_reg"),0.00,rstLoc.getDouble("nd_can"))){
                        blnRes=false;
                    }
                } 
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
	    }
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(jifCnt, e);
            blnRes=false;
            
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(jifCnt, e);
            blnRes=false;
        } 
        return blnRes;
    }
    
}
