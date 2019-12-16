/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafResInv;

import Librerias.ZafGetDat.ZafDatBod;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Sistemas6
 */
public class ZafResInv {
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private java.awt.Component cmpPad;
    private String strVersion="v0.1", strSql;
    private ZafDatBod objDatBod;  
    
    
     public ZafResInv(ZafParSis obj, java.awt.Component componente){
         try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            cmpPad=componente;
            objDatBod = new Librerias.ZafGetDat.ZafDatBod(objParSis, cmpPad);
            System.err.println(strVersion);
         }
         catch (CloneNotSupportedException e){
            System.out.println("ZafMovInv: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
         }
     }
     
      
     
       
     
     /**
      * Inserta la tabla relacional 
      * @param conExt
      * @param CodEmp
      * @param CodLoc
      * @param CodCot
      * @param CodEmpRel
      * @param CodLocRel
      * @param CodCotRel
      * @return 
      */
     
     public boolean insertarTbr_cabCotVen(java.sql.Connection conExt,int CodEmp,int CodLoc,int CodCot,int CodEmpRel,int CodLocRel,int CodCotRel){
        boolean blnRes=true;
        java.sql.Statement stmLoc, stmLoc01;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement(); stmLoc01 = conExt.createStatement();
                strSql="";
                strSql+=" SELECT *  \n";
                strSql+=" FROM tbr_cabCotVen \n";
                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+" AND \n";
                strSql+="       co_empRel="+CodEmpRel+" AND co_locRel="+CodLocRel+" AND co_cotRel="+CodCotRel+" ";
                rstLoc = stmLoc01.executeQuery(strSql);
                if(!rstLoc.next()){
                    strSql="";
                    strSql+=" INSERT INTO tbr_cabCotVen (co_emp,co_loc,co_cot,co_empRel,co_locRel,co_cotRel,tx_tipRel) \n ";
                    strSql+=" VALUES("+CodEmp+","+CodLoc+","+CodCot+","+CodEmpRel+","+CodLocRel+","+CodCotRel+", 'R' ); \n";
                    System.out.println("insertarTbr_cabCotVen: " + strSql);
                    stmLoc.executeUpdate(strSql);
                }
                stmLoc01.close();
                stmLoc01 = null;
                stmLoc.close();
                stmLoc=null;
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
     
     /**
      * Insertar tabla relacional de detalle de cotizaciones de venta
      * @param conExt
      * @param CodEmp
      * @param CodLoc
      * @param CodCot
      * @param CodReg
      * @param CodEmpRel
      * @param CodLocRel
      * @param CodCotRel
      * @param CodRegRel
      * @return 
      */
     
      public boolean insertarTbr_detCotVen(java.sql.Connection conExt,int CodEmp,int CodLoc,int CodCot, int CodReg,
                                                                      int CodEmpRel,int CodLocRel,int CodCotRel, int CodRegRel){
        boolean blnRes=true;
        java.sql.Statement stmLoc, stmLoc01;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement(); stmLoc01 = conExt.createStatement();
                strSql="";
                strSql+=" SELECT *  \n";
                strSql+=" FROM tbr_detCotVen \n";
                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+" AND co_reg="+CodReg+" AND \n";
                strSql+="       co_empRel="+CodEmpRel+" AND co_locRel="+CodLocRel+" AND co_cotRel="+CodCotRel+" AND co_regRel="+CodRegRel+"  ";
                rstLoc = stmLoc01.executeQuery(strSql);
                if(!rstLoc.next()){
                    strSql="";
                    strSql+=" INSERT INTO tbr_detCotVen (co_emp,co_loc,co_cot,co_reg,co_empRel,co_locRel,co_cotRel,co_regRel,tx_tipRel) \n ";
                    strSql+=" VALUES("+CodEmp+","+CodLoc+","+CodCot+","+CodReg+","+CodEmpRel+","+CodLocRel+","+CodCotRel+","+CodRegRel+",'R'); \n";
                    System.out.println("insertarTbr_cabCotVen: " + strSql);
                    stmLoc.executeUpdate(strSql);
                }
                stmLoc01.close();
                stmLoc01=null;
                stmLoc.close();
                stmLoc=null;
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
    
    /**
     * GENERANDO LA FACTURA
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @param CodReg
     * @return 
     */
      
     public boolean modificaCantidadesReservadasFacturadas(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot, int CodReg, 
                                                                             int CodEmpRel, int CodLocRel, int CodCotRel, int CodRegRel){
         boolean blnRes=true;
         java.sql.Statement stmLoc, stmLoc01;
         java.sql.ResultSet rstLoc;
         double dblCanPen=0.00;
         try{
             if(conExt!=null){
                stmLoc = conExt.createStatement(); 
                stmLoc01 = conExt.createStatement();
                
                
                
                
                /* <<  MODIFICA PEDIDOS COTIZACION PADRE  >>  */
                strSql="";
                strSql+=" SELECT a2.co_emp,a2.co_loc,a2.co_cot,a2.co_reg, a2.co_itm, a2.nd_can   \n";
                strSql+=" FROM tbm_cabCotVen as a1   \n";
                strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+=" INNER JOIN tbr_detCotVen as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_cot=a3.co_cot AND  \n";
                strSql+="                                     a2.co_reg=a3.co_reg)   \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" AND a1.co_reg="+CodReg+" AND a3.tx_tipRel='R' \n";
                rstLoc=stmLoc01.executeQuery(strSql);
                if(rstLoc.next()){
                      dblCanPen=rstLoc.getDouble("nd_can");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc01.close();
                stmLoc01=null;
                
                strSql="";
                strSql+=" SELECT a.*, a.nd_canAut-(a.nd_canFac+a.nd_canCan) as Disponible \n";
                strSql+=" FROM ( \n";
                strSql+="       SELECT a1.co_emp,a1.co_loc,a1.co_cot,a1.co_reg,a1.co_empRel,a1.co_bodRel,a1.co_itmRel,a1.nd_can, \n";
                strSql+="              a1.nd_canAut, CASE WHEN a1.nd_canFac IS NULL THEN 0 ELSE a1.nd_canFac END as nd_canFac, \n";
                strSql+="              CASE WHEN a1.nd_canCan IS NULL THEN 0 ELSE a1.nd_canCan END as nd_canCan  \n";
                strSql+="       FROM tbm_pedOtrBodCotVen  as a1  \n";
                strSql+="       WHERE a1.co_emp="+CodEmpRel+" AND a1.co_loc="+CodLocRel+" AND a1.co_cot="+CodCotRel+" AND a1.co_reg="+CodRegRel+"  \n";
                strSql+=" ) as a \n";
                rstLoc = stmLoc01.executeQuery(strSql);
                while(rstLoc.next()){
                    if(dblCanPen>0){
                        if(rstLoc.getDouble("disponible")>=0){
                            dblCanPen=dblCanPen-rstLoc.getDouble("disponible");
                            strSql="";
                            strSql+="UPDATE tbm_pedOtrBodCotVen SET nd_canFac=CASE WHEN nd_canFac IS NULL THEN 0 ELSE nd_canFac END + "+rstLoc.getDouble("disponible");
                            strSql+="WHERE co_emp="+rstLoc.getInt("co_emp")+" AND co_loc="+rstLoc.getInt("co_loc")+" AND co_cot="+rstLoc.getInt("co_cot")+" AND \n";
                            strSql+="      co_reg="+rstLoc.getInt("co_reg")+" AND co_empRel="+rstLoc.getInt("co_empRel") +" AND \n";
                            strSql+="      co_bodRel="+rstLoc.getInt("co_bodRel")+" AND co_itmRel="+rstLoc.getInt("co_itmRel")+"; \n";                              
                            System.out.println("MODIFICA PEDIDOS COTIZACION PADRE: " + strSql);
                            stmLoc.executeUpdate(strSql);
                        }
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc01.close();
                stmLoc01=null;
                
                /* <<  MODIFICA PEDIDOS COTIZACION PADRE  >>  */
                
                strSql="";
                strSql+=" UPDATE tbm_detCotVen SET nd_canFac=CASE WHEN nd_canFac IS NULL THEN 0 ELSE nd_canFac END + a.nd_can, \n";
                strSql+="                          nd_valFac=CASE WHEN nd_valFac IS NULL THEN 0 ELSE nd_valFac END + a.totalCalculado,  \n";
                strSql+="                          nd_canAutRes=CASE WHEN nd_canAutRes IS NULL THEN 0 ELSE nd_canAutRes END - a.nd_can, \n";
                strSql+="                          nd_canPenFac=CASE WHEN nd_canPenFac IS NULL THEN 0 ELSE nd_canPenFac END - a.nd_can  \n";
                strSql+=" FROM ( \n";
                strSql+="       SELECT co_emp, co_loc, co_cot, co_reg, co_itm,tx_codAlt, nd_can, nd_preUni, nd_porDes,  \n";
                strSql+="               (ROUND( nd_preUni*nd_can- (nd_preUni* nd_can)*nd_porDes/100  ,2)  ) as totalCalculado \n";
                strSql+="       FROM tbm_detCotVen  \n";
                strSql+="       WHERE co_emp="+CodEmp+" and co_loc="+CodLoc+" and co_cot="+CodCot+" and co_reg="+CodReg+" \n";
                strSql+=" )as a  \n";
                strSql+=" WHERE tbm_detCotVen.co_emp="+CodEmpRel+" AND \n";
                strSql+="       tbm_detCotVen.co_loc="+CodLocRel+" AND \n";
                strSql+="       tbm_detCotVen.co_cot="+CodCotRel+" AND \n";
                strSql+="       tbm_detCotVen.co_reg="+CodRegRel+" ; \n";
                System.out.println("actualiza tbm_detCotVen(1)(2 COTIZACION ANTERIOR MODIFICAMOS DATOS FACTURADOS): " + strSql);
                stmLoc.executeUpdate(strSql);
                
                stmLoc.close();
                
                stmLoc=null;
                
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
     
     
     
     
    /**
     * GENERANDO LA FACTURA
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @param CodReg
     * @return 
     */
      
     public boolean modificaCantidadesReservadasFacturadas(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
         boolean blnRes=true;
         java.sql.Statement stmLoc, stmLoc01, stmLoc02;
         java.sql.ResultSet rstLoc,rstLoc01;
         double dblCanPen=0.00;
         int CodEmpRel=-1,CodLocRel=-1, CodCotRel=-1,CodRegRel=-1,CodReg=-1;
         try{
             if(conExt!=null){
                stmLoc = conExt.createStatement(); stmLoc01 = conExt.createStatement(); stmLoc02 = conExt.createStatement();
                /* <<  MODIFICA PEDIDOS COTIZACION PADRE  >>  */
                strSql="";
                strSql+=" SELECT a2.co_emp,a2.co_loc,a2.co_cot,a2.co_reg, a2.co_itm, a2.nd_can,   \n";
                strSql+="        a3.co_empRel,a3.co_locRel,a3.co_cotRel,a3.co_regRel \n";
                strSql+=" FROM tbm_cabCotVen as a1   \n";
                strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+=" INNER JOIN tbr_detCotVen as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_cot=a3.co_cot AND  \n";
                strSql+="                                     a2.co_reg=a3.co_reg)   \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" AND a3.tx_tipRel='R' \n";
                System.out.println("modificaCantidadesReservadasFacturadas: \n" + strSql);
                rstLoc=stmLoc01.executeQuery(strSql);
                while(rstLoc.next()){
                    dblCanPen=rstLoc.getDouble("nd_can");CodReg=rstLoc.getInt("co_reg");
                    CodEmpRel=rstLoc.getInt("co_empRel");CodLocRel=rstLoc.getInt("co_locRel");
                    CodCotRel=rstLoc.getInt("co_cotRel");CodRegRel=rstLoc.getInt("co_regRel");
                    strSql="";
                    strSql+=" SELECT a.*, a.nd_canAut-(a.nd_canFac+a.nd_canCan) as Disponible \n";
                    strSql+=" FROM ( \n";
                    strSql+="       SELECT a1.co_emp,a1.co_loc,a1.co_cot,a1.co_reg,a1.co_empRel,a1.co_bodRel,a1.co_itmRel,a1.nd_can, \n";
                    strSql+="              a1.nd_canAut, CASE WHEN a1.nd_canFac IS NULL THEN 0 ELSE a1.nd_canFac END as nd_canFac, \n";
                    strSql+="              CASE WHEN a1.nd_canCan IS NULL THEN 0 ELSE a1.nd_canCan END as nd_canCan  \n";
                    strSql+="       FROM tbm_pedOtrBodCotVen  as a1  \n";
                    strSql+="       WHERE a1.co_emp="+CodEmpRel+" AND a1.co_loc="+CodLocRel+" AND a1.co_cot="+CodCotRel+" AND a1.co_reg="+CodRegRel+"  \n";
                    strSql+=" ) as a \n";
                    System.out.println("modificaCantidadesReservadasFacturadas 2: \n" + strSql);
                    rstLoc01 = stmLoc02.executeQuery(strSql);
                    while(rstLoc01.next()){
                        if(dblCanPen>0){
                            if(rstLoc01.getDouble("disponible")>=0){
                                if(dblCanPen<=rstLoc01.getDouble("disponible")){
                                    strSql="";
                                    strSql+="UPDATE tbm_pedOtrBodCotVen SET nd_canFac=CASE WHEN nd_canFac IS NULL THEN 0 ELSE nd_canFac END + "+dblCanPen;
                                    strSql+="WHERE co_emp="+rstLoc01.getInt("co_emp")+" AND co_loc="+rstLoc01.getInt("co_loc")+" AND co_cot="+rstLoc01.getInt("co_cot")+" AND \n";
                                    strSql+="      co_reg="+rstLoc01.getInt("co_reg")+" AND co_empRel="+rstLoc01.getInt("co_empRel") +" AND \n";
                                    strSql+="      co_bodRel="+rstLoc01.getInt("co_bodRel")+" AND co_itmRel="+rstLoc01.getInt("co_itmRel")+"; \n";    
                                    System.out.println("MODIFICA PEDIDOS COTIZACION PADRE: \n" + strSql);
                                    stmLoc.executeUpdate(strSql);
                                }
                                else{
                                    strSql="";
                                    strSql+="UPDATE tbm_pedOtrBodCotVen SET nd_canFac=CASE WHEN nd_canFac IS NULL THEN 0 ELSE nd_canFac END + "+rstLoc01.getDouble("disponible");
                                    strSql+="WHERE co_emp="+rstLoc01.getInt("co_emp")+" AND co_loc="+rstLoc01.getInt("co_loc")+" AND co_cot="+rstLoc01.getInt("co_cot")+" AND \n";
                                    strSql+="      co_reg="+rstLoc01.getInt("co_reg")+" AND co_empRel="+rstLoc01.getInt("co_empRel") +" AND \n";
                                    strSql+="      co_bodRel="+rstLoc01.getInt("co_bodRel")+" AND co_itmRel="+rstLoc01.getInt("co_itmRel")+"; \n";    
                                    System.out.println("MODIFICA PEDIDOS COTIZACION PADRE: \n" + strSql);
                                    stmLoc.executeUpdate(strSql);
                                }
                                dblCanPen=dblCanPen-rstLoc01.getDouble("disponible");
                            }
                        }
                    }
                    rstLoc01.close();
                    rstLoc01=null;
                    
                    
                    strSql="";
                    strSql+=" UPDATE tbm_detCotVen SET nd_canFac=CASE WHEN nd_canFac IS NULL THEN 0 ELSE nd_canFac END + a.nd_can, \n";
                    strSql+="                          nd_valFac=CASE WHEN nd_valFac IS NULL THEN 0 ELSE nd_valFac END + a.totalCalculado,  \n";
                    strSql+="                          nd_canAutRes=CASE WHEN nd_canAutRes IS NULL THEN 0 ELSE nd_canAutRes END - a.nd_can, \n";
                    strSql+="                          nd_canPenFac=CASE WHEN nd_canPenFac IS NULL THEN 0 ELSE nd_canPenFac END - a.nd_can  \n";
                    strSql+=" FROM ( \n";
                    strSql+="       SELECT co_emp, co_loc, co_cot, co_reg, co_itm,tx_codAlt, nd_can, nd_preUni, nd_porDes,  \n";
                    strSql+="               (ROUND(  ROUND( nd_preUni* nd_can,2)  - ROUND ( (nd_preUni* nd_can)*nd_porDes/100,2),2) ) as totalCalculado \n";
                    strSql+="       FROM tbm_detCotVen  \n";
                    strSql+="       WHERE co_emp="+CodEmp+" and co_loc="+CodLoc+" and co_cot="+CodCot+" and co_reg="+CodReg+" \n";
                    strSql+=" )as a  \n";
                    strSql+=" WHERE tbm_detCotVen.co_emp="+CodEmpRel+" AND \n";
                    strSql+="       tbm_detCotVen.co_loc="+CodLocRel+" AND \n";
                    strSql+="       tbm_detCotVen.co_cot="+CodCotRel+" AND \n";
                    strSql+="       tbm_detCotVen.co_reg="+CodRegRel+" ; \n";
                    System.out.println("actualiza tbm_detCotVen(1)(2 COTIZACION ANTERIOR MODIFICAMOS DATOS FACTURADOS): \n" + strSql);
                    stmLoc.executeUpdate(strSql);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc01.close();
                stmLoc01=null;
                stmLoc.close();
                stmLoc=null;
                stmLoc02.close();
                stmLoc02=null;
                
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
     
     
     
    /**
     *  ANTES DE FACTURAR
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @param CodReg
     * @return 
     */
      
     public boolean modificaCantidadesReservadas(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot, int CodReg, 
                                                                             int CodEmpRel, int CodLocRel, int CodCotRel, int CodRegRel){
         boolean blnRes=true;
         java.sql.Statement stmLoc;
         
         try{
             if(conExt!=null){
                stmLoc = conExt.createStatement();           
                strSql="DELETE FROM tbm_pedOtrBodCotVen WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+" AND co_reg="+CodReg;
                System.out.println("DELETE pedidos "+strSql);
                stmLoc.executeUpdate(strSql);
                strSql="";
                strSql+=" INSERT INTO tbm_pedOtrBodCotVen (co_emp,co_loc,co_cot,co_reg,co_empRel,co_bodRel,co_itmRel,nd_can,nd_canAut,nd_canFac) \n";
                strSql+=" SELECT a.co_emp, a.co_loc,a.co_cot,a.co_reg,a.co_emp as co_empRel, a.co_bod as co_bodRel, a.co_itm as co_itmRel, \n";
                strSql+="         a.nd_can, a.nd_can as nd_canAut, a.nd_can as nd_canFac \n";
                strSql+=" FROM ( \n";
                strSql+="       SELECT a2.co_emp,a2.co_loc,a2.co_cot,a2.co_bod,a2.co_reg, a2.co_itm, a2.nd_can  \n";
                strSql+="       FROM tbm_cabCotVen as a1  \n";
                strSql+="       INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+="       INNER JOIN tbr_detCotVen as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_cot=a3.co_cot AND   \n";
                strSql+="                                          a2.co_reg=a3.co_reg)   \n";
                strSql+="       WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+" and a2.co_reg="+CodReg+" \n"; 
                strSql+=" ) as a ; \n";
                System.out.println("INSERTAR tbm_pedOtrBodCotVen: la cotizacion para que cargue en la tabla de pedidos \n " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
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
     
     /**
      * Para saber si una cotizacion viene de una reserva
      * 
      * @param conExt
      * @param CodEmp
      * @param CodLoc
      * @param CodCot
      * @return 
      */
     
     
     public boolean cotizacionVieneDeReservas(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
         boolean blnRes=false;
         java.sql.Statement stmLoc;
         java.sql.ResultSet rstLoc;
         try{
             if(conExt!=null){
                 stmLoc = conExt.createStatement();
                 strSql="";
                 strSql+=" SELECT * \n";
                 strSql+=" FROM tbr_cabCotVen \n";
                 strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+" AND tx_tipRel='R' \n";
                 System.out.println("cotizacionVieneDeReservas: " + strSql);
                 rstLoc = stmLoc.executeQuery(strSql);
                 if(rstLoc.next()){
                     blnRes=true;
                 }
                 rstLoc.close();
                 rstLoc=null;
                 stmLoc.close();
                 stmLoc=null;
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
       
     
     /**
      * Para saber si una cotizacion viene de una reserva
      * 
      * @param conExt
      * @param CodEmp
      * @param CodLoc
      * @param CodCot
      * @return 
      */
     
     
     public boolean SeguimientoVieneDeReservas(java.sql.Connection conExt, int CodSegInv){
         boolean blnRes=false;
         java.sql.Statement stmLoc;
         java.sql.ResultSet rstLoc;
         try{
             if(conExt!=null){
                 stmLoc = conExt.createStatement();
                 strSql="";
                 strSql+=" SELECT a2.* \n";
                 strSql+=" FROM tbm_cabSegMovInv as a1 \n";
                 strSql+=" INNER JOIN tbm_cabCotVen as a2 ON (a1.co_empRelCabCotVen=a2.co_emp AND a1.co_locRelCabCotVen=a2.co_loc AND \n";
                 strSql+="                                    a1.co_cotRelCabCotVen=a2.co_cot) \n";
                 strSql+=" WHERE a1.co_seg="+CodSegInv+" AND a2.st_solResInv IS NOT NULL AND a2.st_autSolResInv = 'A' \n";
                 System.out.println("cotizacionVieneDeReservas: " + strSql);
                 rstLoc = stmLoc.executeQuery(strSql);
                 if(rstLoc.next()){
                     blnRes=true;
                 }
                 rstLoc.close();
                 rstLoc=null;
                 stmLoc.close();
                 stmLoc=null;
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
     
     
     public boolean eliminar_TbrCabCotVen_TbrDetCotVen(java.sql.Connection conExt,int CodEmp, int CodLoc, int CodCot ){
         boolean blnRes=true;
         java.sql.Statement stmLoc;
         try{
             if(conExt!=null){
                 stmLoc = conExt.createStatement();
                 strSql="";
                 //strSql+=" DELETE FROM tbr_cabCotVen WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+"; \n";
                 strSql+=" INSERT INTO tbt_detCotVen (co_emp, co_loc, co_cot, co_reg, co_emprel, co_locrel, co_cotRel,  \n";
                 strSql+="                                    co_regRel, tx_tiprel) \n";
                 strSql+="  SELECT a.co_emp,a.co_loc,a.co_cot,a.co_reg,a.co_empRel,a.co_locRel,a.co_cotRel,a.co_regRel,a.tx_tipRel \n";
                 strSql+="  FROM ( \n";
                 strSql+="      SELECT co_emp, co_loc, co_cot, co_reg, co_emprel, co_locrel, co_cotrel, \n";
                 strSql+="             co_regrel, tx_tiprel  \n";
                 strSql+="      FROM tbr_detCotVen  \n";
                 strSql+="      WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+"  \n";
                 strSql+="      EXCEPT \n";
                 strSql+="      SELECT co_emp, co_loc, co_cot, co_reg, co_emprel, co_locrel, co_cotrel, \n";
                 strSql+="             co_regrel, tx_tiprel  \n";
                 strSql+="      FROM tbt_detCotVen  \n";
                 strSql+="      WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+"  \n";
                 strSql+=" ) as a  ;  \n";
                 System.out.println("eliminar_TbrCabCotVen_TbrDetCotVen(INSERTAR EL TEMPORAL): " + strSql);
                 stmLoc.executeUpdate(strSql);
                 strSql="";
                 strSql+=" DELETE FROM tbr_detCotVen WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+"; \n";
                 strSql+=" DELETE FROM tbm_pedOtrBodCotVen  WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+"; \n";
                 System.out.println("eliminar_TbrCabCotVen_TbrDetCotVen: " + strSql);
                 stmLoc.executeUpdate(strSql);
                 stmLoc.close();
                 stmLoc=null;
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
    
}
