/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafGenFacAut;

import Librerias.ZafCfgSer.ZafCfgSer;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.UltDocPrint;
import Librerias.ZafUtil.ZafCtaCtb_dat;
import Librerias.ZafUtil.ZafUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Sistemas6
 */


public class ZafGenFacPar {
    private String strSql, strVersion="FacPar v 0.02";
    private String strCorEleTo="sistemas6@tuvalsa.com"; 
    private String strTitCorEle="Sistemas: Generación de Facturas Automaticas"; 
    private String strTit="Mensaje del sistema Zafiro";
    private java.awt.Component jifCnt;
     
    private ZafParSis objParSis;
    private ZafCorEle objCorEle;
    private ZafUtil objUti;
    
    private ZafInvItm objInvItm;                                                // Para trabajar con la informacion de tipo de documento
    
    private javax.swing.JTable tblPag;
    private javax.swing.JTable tblDat;
    private UltDocPrint objUltDocPrint;                                         // Para trabajar con la informacion de tipo de documento
    private ZafCfgSer objCfgSer;
    private int intCodSer=16;// Impresiones mateo
    private ArrayList arlDatAniMes;
    
    
    private final int intarreglonum[] = new int[10];
    private final int intarreglodia[] = new int[10];
    private final int intCanArr[] = new int[1]; 
    private String strarreglosop[] = new String[10];
    private double dblRetFueGlo = 0, dblRetIvaGlo = 0;
    private Vector vecDataTblPag;
    
    private ZafTblMod objTblModPag;
    private int intNumDec = 0;                                                  //Numero de decimales a presentar
    
    //Tabla Forma de Pago
    private static final int INT_TBL_PAGLIN = 0;                                  //Linea de pago
    private static final int INT_TBL_PAGCRE = 1;                                  //Dias de credito
    private static final int INT_TBL_PAGFEC = 2;                                  //Fecha de vencimiento
    private static final int INT_TBL_PAGRET = 3;                                  //Porcentaje de retencion`
    private static final int INT_TBL_PAGMON = 4;                                  //Monto de pago
    private static final int INT_TBL_PAGGRA = 5;                                  //Dias de gracias    
    private static final int INT_TBL_PAGCOD = 6;                                  //Codigo de retencion
    private static final int INT_TBL_PAGSOP = 7;                                  //Soporte de cheque
    private static final int INT_TBL_COMSOL = 8;                                  //Compensacion Solidaria
    
    private int intNumCotVen=-1;
    private int intCodForPag=-1;
    private int int_Num_Dia_Val = 0;
    
    private double dblSubSerNoSer = 0, dblIvaSerNoSer = 0;
    
     public ZafGenFacPar(ZafParSis obj, java.awt.Component componente){
        try{
            jifCnt=componente;
            this.objParSis = (ZafParSis) obj.clone();
            objCorEle = new ZafCorEle(objParSis);
            objInvItm = new ZafInvItm(objParSis);
            objUti = new ZafUtil();
            tblPag = new javax.swing.JTable();
            tblDat = new javax.swing.JTable();
            objUltDocPrint = new UltDocPrint(objParSis);
            objCfgSer = new ZafCfgSer(objParSis);
            arlDatAniMes = new ArrayList();
             
            bgdValPorIva=objParSis.getPorcentajeIvaVentas().divide(new BigDecimal("100"),objParSis.getDecimalesMostrar(),BigDecimal.ROUND_HALF_UP)  ;
            strFecSisBase = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaBaseDatos());
            intNumDec = objParSis.getDecimalesMostrar();
            System.out.println(strVersion);
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        catch(Exception  e){
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
    }      
    
    /**
     * 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @param arlDatExt
     * @return 
     */ 
     
    public boolean insertarFacturaParcial(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot, ArrayList arlDatExt){
        boolean blnRes=false;
        try{
            if(conExt!=null){
                if(iniciarProcesoFacturaParcial(conExt, CodEmp, CodLoc, CodCot, arlDatExt)){
                    blnRes=true;
                }
            }
        }
        catch(Exception  e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }
    
    /**
     * 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @param arlDatExt
     * @return 
     */
     Librerias.ZafGenFacAut.ZafGenFacAut objGenFacAut;
    
    private boolean iniciarProcesoFacturaParcial(java.sql.Connection conExt, int CodEmp, int CodLoc,int CodCot, ArrayList arlDatExt){
        boolean blnRes=false;
        try{
            if(conExt!=null){
                intNumCotVen = getUltCoDoc(conExt, CodEmp, CodLoc);
                getObtenerDiaValCot(conExt);
                if(insertarCab(conExt, CodEmp, CodLoc, CodCot, arlDatExt)){
                    if(insertarDet(conExt, CodEmp, CodLoc, CodCot, arlDatExt)){
                        if(insertarPagosCotizacion(conExt, CodEmp, CodLoc, intNumCotVen, arlDatExt)){
                            if(insertarRelacionCotizacionPadreHija(conExt, CodEmp, CodLoc, CodCot, intNumCotVen)){
                                if(insertarCotizacionHijaSeguimiento(conExt, CodEmp, CodLoc, CodCot, intNumCotVen)){
//                                    if(colocarCantidadesDisponiblesParaFacturar(conExt, CodEmp, CodLoc, CodCot, intNumCotVen)){
                                        if(UPDATE_cotizacionPadreCantidadesFacturadas(conExt, CodEmp, CodLoc, CodCot, intNumCotVen)){
                                            objGenFacAut = new Librerias.ZafGenFacAut.ZafGenFacAut(objParSis, jifCnt);
                                            if(objGenFacAut.InciaProcesoGeneraFactura(conExt, CodEmp, CodLoc, intNumCotVen)){
                                                blnRes=true;
                                            }
                                        }
//                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(Exception  e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }
    
    
    
    private boolean colocarCantidadesDisponiblesParaFacturar(java.sql.Connection conExt,int CodEmp,int CodLoc,int CodCot,int intNumCotVen){
        boolean blnRes=true;
        java.sql.Statement stmLoc ;
        java.sql.ResultSet rstLoc;
        String strUpdate="", strAux="";
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot, a2.co_reg, a2.co_itm,a2.tx_codAlt, a2.co_bod, a2.nd_can, a3.nd_stkAct, a3.nd_canDesEntCli,    \n";
                strSql+="        CASE WHEN a3.nd_canIngBod IS NULL THEN 0 ELSE a3.nd_canIngBod END as nd_canIngBod,    \n";
                strSql+="        CASE WHEN a3.nd_canDis IS NULL THEN 0 ELSE a3.nd_canDis END as nd_canDis  \n";
                strSql+=" FROM tbm_cabCotVen as a1 \n";
                strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+=" INNER JOIN tbm_invBod as a3 ON (a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod AND a2.co_itm=a3.co_itm) ";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+intNumCotVen+"     \n";
                strSql+="       \n";
                System.out.println("cargarDetReg: "+strSql);   
                /* Se debe agregar una validacion que revise el stock en tbm_invBod y que revise que hayan cantidades nd_canResVen */
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    System.out.println("tx_codAlt " + rstLoc.getString("tx_codAlt"));
                    System.out.println("STOCK " + rstLoc.getDouble("nd_stkAct"));
                    System.out.println("nd_canDis " + rstLoc.getDouble("nd_canDis"));
                    System.out.println("nd_canIngBod " + rstLoc.getDouble("nd_canIngBod"));
                    System.out.println("nd_can " + rstLoc.getDouble("nd_can"));
                    if(rstLoc.getDouble("nd_stkAct")>=rstLoc.getDouble("nd_canDis")+rstLoc.getDouble("nd_canIngBod")+rstLoc.getDouble("nd_can")){
                        strUpdate+=" UPDATE tbm_invBod SET nd_canDis=CASE WHEN nd_canDis IS NULL THEN 0 ELSE nd_canDis END + "+rstLoc.getString("nd_can")+"  " ;
                        strUpdate+=" WHERE co_emp="+CodEmp+" AND co_bod="+rstLoc.getString("co_bod")+" AND co_itm="+rstLoc.getString("co_itm")+"; ";
                    }
                    else{
                        strAux+=" "+rstLoc.getString("tx_codAlt");
                        System.out.println("Error.. ");
                        blnRes=false;
                    }
                }
                
                if(blnRes){
                    System.out.println("strUpdate " + strUpdate);
                    stmLoc.executeUpdate(strUpdate);
                }
                else{
                    JOptionPane.showMessageDialog(jifCnt,"Los siguientes items presentan problemas de inventario: " + strAux, strTit, JOptionPane.ERROR_MESSAGE);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(jifCnt, e);
            blnRes=false;            
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }
    
    
    
    private boolean insertarCotizacionHijaSeguimiento(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCotPadre, int CodCotHija){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        int intNumDoc=-1, intCodSeg=-1;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql = " SELECT MAX(co_reg)+1 as co_reg, a1.co_seg  \n";
                strSql+= " FROM tbm_cabSegMovInv as a1 \n";
                strSql+= " WHERE a1.co_seg = ( \n";
                strSql+= "     SELECT co_seg \n";
                strSql+= "     FROM tbm_cabSegMovInv \n";
                strSql+= "     WHERE co_empRelCabCotVen="+CodEmp+" AND  \n";
                strSql+= "     co_locRelCabCotVen="+CodLoc+" AND co_cotRelCabCotVen="+CodCotPadre+" \n  ";
                strSql+= " ) \n GROUP BY a1.co_seg";                        

                System.out.println("ObtenerNumeroSeguimiento: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    intNumDoc = rstLoc.getInt("co_reg");
                    intCodSeg = rstLoc.getInt("co_seg");
                }
                rstLoc.close();
                rstLoc=null;
                strSql="";
                strSql+=" INSERT INTO tbm_cabSegMovInv (co_seg,co_reg,co_empRelCabCotVen, co_locRelCabCotVen, co_cotRelCabCotVen ) ";
                strSql+=" VALUES ("+intCodSeg+", "+intNumDoc+", "+CodEmp+", "+CodLoc+","+CodCotHija+" );";
                System.out.println("InsertarEnTablaSeguimiento: "+strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(jifCnt, e);
            blnRes=false;            
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }
    
    
    private boolean UPDATE_cotizacionPadreCantidadesFacturadas(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCotPadre, int CodCotHija){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strMsg="", strItem="", strUpdate="";
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot, a1.co_reg as co_regHija, a1.co_itm, a1.tx_codAlt, a1.nd_can as nd_canHija,  \n";
                strSql+="        a2.co_emp, a2.co_loc, a2.co_cot, a2.co_reg as co_regPadre, a2.co_itm, a2.nd_can as nd_canPadre, \n";
                strSql+="        (CASE WHEN a2.nd_canFac IS NULL THEN 0 ELSE a2.nd_canFac END  +   \n";
                strSql+="         CASE WHEN a2.nd_canCan IS NULL THEN 0 ELSE a2.nd_canCan END ) as nd_canUsada   \n";
                strSql+=" FROM tbm_detCotVen as a1  \n";
                strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_itm=a2.co_itm ) \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCotHija+" AND  \n";
                strSql+="       a2.co_emp="+CodEmp+" and a2.co_loc="+CodLoc+" and a2.co_cot="+CodCotPadre+"  ";
                strSql+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_cot, a1.co_reg \n";
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    System.out.println("nd_canUsada + nd_canHija: "+ (rstLoc.getDouble("nd_canUsada") + rstLoc.getDouble("nd_canHija")) );
                    System.out.println("nd_canPadre: " + rstLoc.getDouble("nd_canPadre") );
                    if((rstLoc.getDouble("nd_canUsada") + rstLoc.getDouble("nd_canHija")) <= rstLoc.getDouble("nd_canPadre") ){
                        strUpdate+=" UPDATE tbm_detCotVen SET nd_canFac=("+objUti.redondear(rstLoc.getDouble("nd_canHija"), objParSis.getDecimalesMostrar())+" +  \n";
                        strUpdate+=" CASE WHEN nd_canFac IS NULL THEN 0 ELSE nd_canFac END), "; 
                        strUpdate+=" nd_canPenFac= CASE WHEN nd_canPenFac IS NULL THEN 0 ELSE nd_canPenFac END - ( "+objUti.redondear(rstLoc.getDouble("nd_canHija"), objParSis.getDecimalesMostrar())+" +    ";
                        strUpdate+="  CASE WHEN nd_canCan IS NULL THEN 0 ELSE nd_canCan END   )   ";
                        strUpdate+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCotPadre+" AND co_reg="+rstLoc.getInt("co_regPadre")+" ; \n";
                    }
                    else{
                        strItem+="<tr><td>" +  rstLoc.getString("tx_codAlt") + " </td>";
                        strItem+=" <td>" + objUti.redondear(rstLoc.getString("nd_canUsada"), objParSis.getDecimalesMostrar())  + " </td>";
                        strItem+=" <td>" + objUti.redondear(rstLoc.getString("nd_canHija"), objParSis.getDecimalesMostrar()) + " </td> ";
                        strItem+=" </tr> ";
                        blnRes=false;
                    }
                }
                rstLoc.close();
                rstLoc=null;
                if(!blnRes){
                    strMsg="<html> La solicitud posee Items con cantidades insuficientes. <BR><BR>" ;// CAMBIA
                    strMsg+=" <table BORDER=1><tr><td> ITEM </td> <td> Utilizada/Cancelada </td><td> Can.Sol. </td>";
                    strMsg+=""+ strItem + "    ";
                    strMsg+=" </table><BR>";
                    strMsg+="No se puede realizar esta operación. <html>";
                    JOptionPane.showMessageDialog(jifCnt, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
                }
                else{
                    strUpdate+=" UPDATE tbm_cabCotVen SET st_solFacPar='S', tx_tipCot='P' ";
                    strUpdate+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCotPadre+" ; ";
                    System.out.println("UPDATE_cotizacionPadreCantidadesFacturadas: " + strUpdate);
                    stmLoc.executeUpdate(strUpdate);
                }
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(jifCnt, e);
            blnRes=false;            
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
        
    }
    
    
    
    
    /**
     * Inserta la relacion entre las cotizaciones padres con las hijas 
     * 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCotPadre
     * @param CodCotHija
     * @return 
     */
    
    private boolean insertarRelacionCotizacionPadreHija(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCotPadre, int CodCotHija){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" INSERT INTO tbr_cabCotVen (co_emp, co_loc, co_cot, co_empRel, co_locRel, co_cotRel, tx_tipRel) \n";
                strSql+=" VALUES ( "+CodEmp+", "+CodLoc+", "+CodCotHija+", "+CodEmp+", "+CodLoc+", "+CodCotPadre+", 'P' ); \n";
                System.out.println("insertarRelacionCotizacionPadreHija: 1: " + strSql);
                stmLoc.executeUpdate(strSql);
                
                strSql="";
                strSql+=" INSERT INTO tbr_detCotVen (co_emp, co_loc, co_cot, co_reg, co_empRel, co_locRel, co_cotRel, co_regRel, tx_tipRel) \n";
                strSql+=" SELECT a.co_emp, a.co_loc, a.co_cot, a.co_reg, a.co_empRel, a.co_locRel, a.co_cotRel, a.co_regRel, 'P' as tx_tipRel \n";
                strSql+=" FROM ( \n";
                strSql+="       SELECT a1.co_emp, a1.co_loc, a1.co_cot, a1.co_reg, a2.co_emp as co_empRel, a2.co_loc as co_locRel, a2.co_cot as co_cotRel, a2.co_reg as co_regRel \n";
                strSql+="       FROM tbm_detCotVen as a1   \n";
                strSql+="       INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_itm=a2.co_itm ) \n";
                strSql+="       WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCotHija+" and    \n";
                strSql+="             a2.co_emp="+CodEmp+" and a2.co_loc="+CodLoc+" and a2.co_cot="+CodCotPadre+"   \n";
                strSql+=" ) as a  ;  \n";
                System.out.println("insertarRelacionCotizacionPadreHija: 2: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(jifCnt, e);
            blnRes=false;            
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }
    
    
    
    /**
     * 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @param arlDatExt
     * @return 
     */
    
    private boolean insertarCab(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot, ArrayList arlDatExt) {
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String sqlCab;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT co_emp, co_loc, co_cot, fe_cot, fe_procon, co_cli, co_ven, tx_ate, tx_obs1, tx_obs2, nd_sub, nd_valiva,  \n";
                strSql+="        nd_valComSol,nd_subIvaCer,nd_subIvaGra,nd_porComSol, nd_tot, nd_porIva, co_forPag, fe_ing, fe_ultmod, co_usring ,co_usrmod, st_reg,\n";
                strSql+="        ne_val,tx_numped , tx_nomcli, co_locrelsoldevven,  co_tipdocrelsoldevven, co_docrelsoldevven, st_docconmersaldemdebfac, fe_val ,  \n";
                strSql+="        tx_dirCliFac, tx_dirCliGuiRem ,co_tipCre, co_forRet, tx_vehRet, tx_choRet  \n";
                strSql+=" FROM tbm_cabCotVen   ";
                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+" \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    sqlCab = "INSERT INTO  tbm_cabCotVen ";
                    sqlCab+="(co_emp, co_loc, co_cot, "; //CAMPOS PrimayKey
                    sqlCab+=" fe_cot, fe_procon,";//Fecha de la cotizacion
                    sqlCab+=" co_cli, co_ven, tx_ate, ";//<==Campos que aparecen en la parte superior del 1er Tab
                    sqlCab+=" tx_obs1, tx_obs2, nd_sub, nd_valiva,  ";//<==Campos que aparecen en la parte inferior del 1er Tab
                    sqlCab+=" nd_valComSol,nd_subIvaCer,nd_subIvaGra,nd_porComSol,";
                    sqlCab+=" nd_tot, nd_porIva,";
                    sqlCab+=" co_forPag, fe_ing, fe_ultmod, co_usring ,co_usrmod, st_reg, ne_val,tx_numped ";
                    sqlCab+=" , tx_nomcli, co_locrelsoldevven,  co_tipdocrelsoldevven, co_docrelsoldevven, st_docconmersaldemdebfac, fe_val, tx_dirCliFac, tx_dirCliGuiRem ";
                    if(rstLoc.getString("co_tipCre")!=null){
                        sqlCab += " ,co_tipCre ";
                    }
                    
                    sqlCab += " , co_forRet, tx_vehRet, tx_choRet, tx_tipCot  ) ";
                    sqlCab+="VALUES ( ";
                    sqlCab+=rstLoc.getString("co_emp")+", ";
                    sqlCab+=rstLoc.getString("co_loc")+", ";
                    sqlCab+=intNumCotVen+", '";
                    sqlCab+=rstLoc.getString("fe_cot")+"',"+rstLoc.getString("fe_proCon")+", ";
                    sqlCab+=rstLoc.getString("co_cli")+", ";
                    sqlCab+=rstLoc.getString("co_ven")+",'";
                    sqlCab+=rstLoc.getString("tx_ate")+"',";
                    sqlCab+=objUti.codificar(rstLoc.getString("tx_obs1"))+",";
                    sqlCab+="'"+rstLoc.getString("tx_obs2")+" - Cot.Org.: "+ CodCot + "',";
                    sqlCab+="-1,";
                    sqlCab+="-1, ";

                    sqlCab+="0,0,0,null,";

                    sqlCab+="-1, ";
                    sqlCab+=rstLoc.getString("nd_porIva") + " , ";
                    sqlCab+=rstLoc.getString("co_forPag")+ ",CURRENT_TIMESTAMP,null, ";
                    sqlCab+=objParSis.getCodigoUsuario() + " , ";
                    sqlCab+=" null , ";
                    sqlCab+="'E'," + rstLoc.getString("ne_val") + ",'" + rstLoc.getString("tx_numPed")+ "', ";
                    sqlCab+="'" + rstLoc.getString("tx_nomcli") + "', " + rstLoc.getString("co_locrelsoldevven")  + ", " + rstLoc.getString("co_tipdocrelsoldevven") + ", " ;
                    sqlCab+=rstLoc.getString("co_docrelsoldevven") + ",'" + rstLoc.getString("st_docconmersaldemdebfac") + "', (current_date + " + int_Num_Dia_Val + ") ," ; 

                    sqlCab+=objUti.codificar(rstLoc.getString("tx_dirCliFac")) + ", " + objUti.codificar(rstLoc.getString("tx_dirCliGuiRem"));
                    if(rstLoc.getString("co_tipCre")!=null){
                        sqlCab += " ," + objUti.codificar(rstLoc.getString("co_tipCre")) + " ";
                    }
                    sqlCab+=", "+rstLoc.getString("co_forRet")+", "+objUti.codificar(rstLoc.getString("tx_vehRet"))+", "+objUti.codificar(rstLoc.getString("tx_choRet"))+", 'H' ";
                    sqlCab += "  ) ; ";
                    System.out.println("INSERTAR: \n" + sqlCab);
                    intCodForPag= rstLoc.getInt("co_forPag");
                    stmLoc.executeUpdate(sqlCab);

                }
                stmLoc.close();
                stmLoc=null;
                rstLoc.close();
                rstLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(jifCnt, e);
            blnRes=false;            
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }
    
    
    
    private static final int INT_TBL_DAT_CON_LIN = 0;                   //Linea.
    private static final int INT_TBL_DAT_CON_COD_EMP = 1;              //Código de Empresa de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_CON_COD_LOC = 2;              //Código de Local de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_CON_COD_DOC = 3;              //Código de la cotización.
    private static final int INT_TBL_DAT_CON_COD_REG = 4;              //Registro de la cotización.
    private static final int INT_TBL_DAT_CON_CAN_SOL_FAC_ITM = 5;      //Cantidad que se solicita facturar
        
    private BigDecimal bgdValDocSub;//subtotal
    private BigDecimal bgdValDocIva;//iva
    private BigDecimal bgdValDocTot;//total
    private BigDecimal bgdValPorIva;//porcentaje de iva
    
    /**
     * 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @param arlDatExt
     * @return 
     */
        
    private boolean insertarDet(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot, ArrayList arlDatExt) {
        boolean blnRes = false;
        java.sql.Statement stmLoc;
        int intest = 0;
        java.sql.ResultSet rstLoc;
        String strAux="", strIns="";
        double dblCan=0,dblPre=0,dblDes=0,dblValDes=0,dblTotal=0;
        BigDecimal bgdCanItm;
        BigDecimal bgdPreItm;
        BigDecimal bgdValDesItm;
        BigDecimal bgdPorDesItm;
        BigDecimal bgdTotItm=BigDecimal.ZERO;
        bgdValDocSub=new BigDecimal("0");
        bgdValDocIva=new BigDecimal("0");
        bgdValDocTot=new BigDecimal("0");
        try {
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                String strQry = "INSERT INTO  tbm_detCotVen (co_emp, co_loc, co_cot, co_reg, co_itm, tx_codalt,tx_codalt2, tx_nomItm, " + 
                    " co_bod, nd_can, nd_preUni, nd_porDes, st_ivaVen, co_prv, nd_precom ,  nd_pordesprecom, st_traauttot, "
                    +" co_locrelsolsaltemmer, co_tipdocrelsolsaltemmer, co_docrelsolsaltemmer, co_regrelsolsaltemmer, "
                    +" co_locrelsoldevven, co_tipdocrelsoldevven, co_docrelsoldevven, co_regrelsoldevven, "
                    +" nd_preunivenlis, nd_pordesvenmax, co_bodcom,nd_porIva,nd_basImpIvaCer, nd_basImpIvaGra, nd_canPenFac  ) ";
                
                strSql="";
                strSql+=" SELECT co_emp, co_loc, co_cot, co_reg, co_itm, tx_codalt,tx_codalt2, tx_nomItm,  \n";
                strSql+="        co_bod, nd_can, nd_preUni, nd_porDes, st_ivaVen, co_prv,nd_precom ,  nd_pordesprecom,  \n";
                strSql+="        st_traauttot, co_locrelsolsaltemmer, co_tipdocrelsolsaltemmer, co_docrelsolsaltemmer, co_regrelsolsaltemmer, \n";
                strSql+="        co_locrelsoldevven, co_tipdocrelsoldevven, co_docrelsoldevven, co_regrelsoldevven, \n";
                strSql+="        nd_preunivenlis, nd_pordesvenmax, co_bodcom,nd_porIva,nd_basImpIvaCer, nd_basImpIvaGra  \n";
                strSql+=" FROM tbm_detCotVen  \n";
                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+" \n";
                strSql+=" GROUP BY co_emp, co_loc, co_cot, co_reg \n";
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    for(int i=0; i<arlDatExt.size(); i++){
                        if(rstLoc.getInt("co_reg")==objUti.getIntValueAt(arlDatExt, i, INT_TBL_DAT_CON_COD_REG)){
                            dblCan = objUti.getDoubleValueAt(arlDatExt, i, INT_TBL_DAT_CON_CAN_SOL_FAC_ITM); // JM: Cantidad que se solicita facturar 
                            dblPre = rstLoc.getDouble("nd_preUni");
                            dblDes = rstLoc.getDouble("nd_porDes");

                            bgdCanItm=BigDecimal.valueOf(dblCan) ;
                            bgdPreItm=BigDecimal.valueOf(dblPre) ;
                            bgdPorDesItm=BigDecimal.valueOf(dblDes) ;

                            //DESCUENTO
                            bgdValDesItm = bgdPorDesItm.multiply((bgdCanItm.multiply(bgdPreItm))); 
                             bgdValDesItm = bgdValDesItm.divide(new BigDecimal("100"),objParSis.getDecimalesBaseDatos(),BigDecimal.ROUND_HALF_UP);
                            ///TOTAL
                            bgdTotItm=objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)).subtract(bgdValDesItm), objParSis.getDecimalesMostrar());    
                            dblTotal = bgdTotItm.doubleValue();
                        
                            if(rstLoc.getString("st_ivaVen").equals("S")){
                                bgdValDocIva=bgdValDocIva.add((bgdTotItm.multiply(bgdValPorIva)));// no se debe redondear hasta el final 
                            }
                          
                            strAux=" " + strQry + " SELECT " + rstLoc.getString("co_emp") + "," + rstLoc.getString("co_loc") + "," + intNumCotVen + "," + (i + 1) + ", ";
                            strAux+=rstLoc.getString("co_itm") + ",'" + rstLoc.getString("tx_codAlt") + "','" + rstLoc.getString("tx_codAlt2");
                            strAux+="'," + objUti.codificar(rstLoc.getString("tx_nomItm")) + ",";
                            strAux+=rstLoc.getString("co_bod") + "," + dblCan + ",";
                            strAux+=dblPre + ",";
                            strAux+=dblDes + ",'";
                            strAux+=rstLoc.getString("st_ivaVen") + "',";
                            strAux+=rstLoc.getString("co_prv") + ",";
                            strAux+=rstLoc.getString("nd_precom") + " , ";
                            strAux+=rstLoc.getString("nd_pordesprecom")+ " , '";
                            strAux+=rstLoc.getString("st_traauttot") + "',";
                            strAux+="" + rstLoc.getString("co_locrelsolsaltemmer") + ",";
                            strAux+=" " + rstLoc.getString("co_tipdocrelsolsaltemmer")  + "  ";
                            strAux+=" ," + rstLoc.getString("co_docrelsolsaltemmer") + "," + rstLoc.getString("co_regrelsolsaltemmer") + "," + rstLoc.getString("co_locrelsoldevven")+ ",";
                            strAux+=" " + rstLoc.getString("co_tipdocrelsoldevven")+", ";
                            strAux+=" " + rstLoc.getString("co_docrelsoldevven") + ", "+rstLoc.getString("co_regrelsoldevven") +", "; 
                            
                            strAux+=rstLoc.getString("nd_preunivenlis") + ", " + rstLoc.getString("nd_porDesVenMax") + ",  ";
                            strAux+=" " + rstLoc.getString("co_bodcom") + ", ";
                            strAux+= rstLoc.getString("nd_porIva")  + ",";
                            strAux+= (rstLoc.getString("st_ivaVen").equals("S") ? 0 : (dblTotal*-1) ) + ",";
                            strAux+= (rstLoc.getString("st_ivaVen").equals("S") ? (dblTotal*-1) : 0 ) + "";
                            strAux+="," + dblCan;
                            strAux+=" ; ";

                            strIns+=strAux;
                            intest = 1;
                            //al final tendrá el valor del subtotal del documento
                            bgdValDocSub=objUti.redondearBigDecimal((bgdValDocSub.add(bgdTotItm)), objParSis.getDecimalesMostrar())    ;
                            
                        }
                    }
                }
                rstLoc.close();
                rstLoc=null;
                
                
                if (intest == 1) {
                    
                    //al final tendrá el valor del total del documento
                    bgdValDocIva=objUti.redondearBigDecimal(bgdValDocIva, objParSis.getDecimalesMostrar());
                    bgdValDocTot=objUti.redondearBigDecimal(((bgdValDocSub.add(bgdValDocIva))), objParSis.getDecimalesMostrar());

                    System.out.println("bgdValDocSub:" + bgdValDocSub);
                    System.out.println("bgdValDocTot:" + bgdValDocTot);
                    System.out.println("bgdValDocIva:" + bgdValDocIva);
                    
                    strAux="";
                    strAux+=" UPDATE tbm_cabCotVen SET nd_sub="+bgdValDocSub+", nd_tot="+bgdValDocTot+",  nd_valIva="+bgdValDocIva+" ";
                    strAux+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+intNumCotVen+" ;  " ;        
                    strIns+=strAux;
                    
                    System.out.println("ZafVen01.insertarDet: \n" +strIns);
                    stmLoc.executeUpdate(strIns);
                    blnRes = true;
                }
            } 
        } 
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(jifCnt, e);
            blnRes=false;            
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }
    
    
    
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    
    /**
     * 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @return 
     */
    
    private boolean insertarPagosCotizacion(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot,  ArrayList arlDatExt){
        boolean blnRes=false;
        try{
            txtFecDoc = new ZafDatePicker("d/m/y");
            if(conExt!=null){
                if(cargarTipEmp(CodEmp, CodLoc)){
                    CalculoPago2(conExt, CodEmp );
                    if(cargarTipoCliente(CodEmp, CodLoc,CodCot)){
                        if(configurarTablaPago()){
                            if(calculaPag(CodEmp, CodLoc, arlDatExt)){
                                if(FormaRetencion(conExt, CodEmp)){
                                    if(insertDetPag(conExt, CodEmp, CodLoc)){
                                        blnRes=true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(Exception  Ext){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, Ext);
        }
        return blnRes;
    }
    
    
    
    private boolean insertDetPag(java.sql.Connection conExt , int CodEmp, int CodLoc ) {
        boolean blnRes = false;
        java.sql.Statement stmLoc;
        String strSql = "";
        try {
            stmLoc = conExt.createStatement();
            for (int i = 0; i < tblPag.getRowCount(); i++) {
                int FecPagDoc[] = txtFecDoc.getFecha(tblPag.getValueAt(i, INT_TBL_PAGFEC).toString());
                String strFechaPag = "#" + FecPagDoc[2] + "/" + FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
                String strCodTipRet = objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGCOD));
                strSql+= "INSERT INTO  tbm_pagCotVen(co_emp, co_loc, co_cot, co_reg, ne_diaCre, fe_ven, ";
                strSql+=" mo_pag, ne_diaGra, nd_porRet, co_tipret ) ";
                strSql+=" VALUES(" + CodEmp + ", " + CodLoc + ", " + intNumCotVen + ", " + (i + 1) + ", ";
                strSql+=objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGCRE)) + ", '#" + strFechaPag + "#', ";
                strSql += (new BigDecimal( objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGMON))).multiply(new BigDecimal(-1)))+", ";
                strSql+=objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGGRA)) + ", ";
                strSql+=objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGRET)) + ", ";
                strSql+=(strCodTipRet.equals("0") ? null : strCodTipRet) + " ";
                strSql += "  ) ; ";
            
            }
            stmLoc.executeUpdate(strSql);
            stmLoc.close();
            stmLoc = null;
            blnRes = true;
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
    
    
    
    private void CalculoPago2(java.sql.Connection conPag, int CodEmp ) {
        try {          
            if (conPag != null) {
                String sSQL2 = "SELECT A1.ne_numPag, A2.ne_diaCre, A2.st_sop "
                        + " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                        " Where A1.co_forPag = " + intCodForPag +// Clausulas Where para las tablas maestras
                        "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                        "       and A1.co_emp = " + CodEmp + // Consultando en la empresa en la ke se esta trabajando
                        "       and A2.co_emp = A1.co_emp " + // Consultando en la empresa en la ke se esta trabajando
                        "       order by A2.co_reg ";// Consultando en el local en el ke se esta trabajando

                String sSQL3 = "SELECT count(A2.ne_diaCre) as c "
                        + " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                        " Where A1.co_forPag = " + intCodForPag +// Clausulas Where para las tablas maestras
                        "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                        "       and A1.co_emp = " + CodEmp + // Consultando en la empresa en la ke se esta trabajando
                        "       and A2.co_emp = A1.co_emp ";  // Consultando en la empresa en la ke se esta trabajando

                Statement stmDoc2 = conPag.createStatement();
                ResultSet rstDocCab2 = stmDoc2.executeQuery(sSQL3);
                rstDocCab2.next();
                intCanArr[0] = rstDocCab2.getInt(1);

                stmDoc2 = conPag.createStatement();
                rstDocCab2 = stmDoc2.executeQuery(sSQL2);
                int x = 0;
                while (rstDocCab2.next()) {
                    intarreglodia[x] = rstDocCab2.getInt(2);
                    intarreglonum[x] = rstDocCab2.getInt(1);
                    strarreglosop[x] = rstDocCab2.getString("st_sop");
                    x++;
                }
                rstDocCab2.close();
                rstDocCab2 = null;
 
            }
        }
        catch (SQLException Evt) {
            System.err.println("ERROR " + Evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        } 
        catch (Exception Evt) {
            System.err.println("ERROR " + Evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
    }
    
    
    private String strFecSisBase;
    private Date datFecDoc;
   
    public boolean calculaPag(int CodEmp, int CodLoc, ArrayList arrDatExt) {
        boolean blnRes=true;
        int intVal = intCanArr[0];
        int intsizearre = intarreglodia.length;
        intVal = intsizearre - (intsizearre - intVal);
        double dblBaseDePagos = 0, dblRetIva = 0, dblRetFue = 0, dblRetFueFle = 0;
        double dblPago = 0.00;
        double dblPagos = 0.00;
        double dblRete = 0;
        int i = 0;
        try {
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            datFecDoc = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            strFecSisBase = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaBaseDatos());
            java.util.Date dateObj = datFecDoc;
            Calendar calObj = Calendar.getInstance();
            calObj.setTime(dateObj);

            objFec.set(java.util.Calendar.DAY_OF_MONTH, calObj.get(Calendar.DAY_OF_MONTH));
            objFec.set(java.util.Calendar.MONTH, calObj.get(Calendar.MONTH) + 1 );
            objFec.set(java.util.Calendar.YEAR, calObj.get(Calendar.YEAR));
            
            dblBaseDePagos = bgdValDocTot.doubleValue();
            

            dblRetFueGlo = 0.0;
            dblRetIvaGlo = 0.00;

            if (dblBaseDePagos > 0) {
                vecDataTblPag = new Vector();
                
                Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
                //*************************************************************************************///
                java.sql.Connection conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conLoc != null) {
                    /**
                     * ***************************************************************************************************************************
                     */
                    calculaSubtotalServiNoServi(conLoc, "T", CodEmp, CodLoc, intNumCotVen);

                    if (dblSubSerNoSer > 0.00) {
                        cargaForPag(conLoc, intCodMotTran, CodEmp);
                    }

                    /**
                     * ***************************************************************************************************************************
                     */
                    calculaSubtotalServiNoServi(conLoc, "N", CodEmp, CodLoc, intNumCotVen);

                    if (dblSubSerNoSer > 0.00) {
                        cargaForPag(conLoc, intCodMotBien, CodEmp);
                    }

                    /**
                     * ***************************************************************************************************************************
                     */
                    calculaSubtotalServiNoServi(conLoc, "S", CodEmp, CodLoc, intNumCotVen);

                    if (dblSubSerNoSer > 0.00) {
                        cargaForPag(conLoc, intCodMotServ, CodEmp);
                    }

                    if(blnIsComSol && dblPorIva==14.00){
                        cargaForPagComSol();
                    }

                    /**
                     * ***************************************************************************************************************************
                     */
                    //*************************************************************************************///

                    conLoc.close();
                    conLoc = null;
                }
                //*************************************************************************************///
                //dblRete = dblRetFueFle+dblRetFue+dblRetIva;
                dblRete = dblRetFueGlo + dblRetIvaGlo;

                dblBaseDePagos = objUti.redondear((bgdValDocTot.doubleValue() - dblRete), intNumDec);




                for (i = 0; i < intVal; i++) {
                    java.util.Calendar objFecPagActual = Calendar.getInstance();
                    objFecPagActual.setTime(objFec.getTime());

                    int diaCre = intarreglodia[i];
                    int numPag = intarreglonum[i];
                    String strSop = ((strarreglosop[i] == null) ? "N" : strarreglosop[i]);

                    if (diaCre != 0) {
                        objFecPagActual.add(java.util.Calendar.DATE, diaCre);
                    }

                    dtePckPag.setAnio(objFecPagActual.get(java.util.Calendar.YEAR));
                    dtePckPag.setMes(objFecPagActual.get(java.util.Calendar.MONTH) + 1);
                    dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));

                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_PAGLIN, "");
                    vecReg.add(INT_TBL_PAGCRE, "" + diaCre);
                    vecReg.add(INT_TBL_PAGFEC, dtePckPag.getText());

                    dblPagos = objUti.redondear((numPag == 0) ? 0 : (dblBaseDePagos / numPag), intNumDec);
                    dblPago += dblPagos;
                    dblPagos = objUti.redondear(dblPagos, intNumDec);

                    vecReg.add(INT_TBL_PAGRET, "");

                    if (i == (intVal - 1)) {
                            dblPagos = objUti.redondear(dblPagos + (bgdValDocTot.doubleValue() - (dblPago + dblRete)), intNumDec);
                    }

                    vecReg.add(INT_TBL_PAGMON, "" + dblPagos);
                    vecReg.add(INT_TBL_PAGGRA, "0");
                    vecReg.add(INT_TBL_PAGCOD, "");
                    vecReg.add(INT_TBL_PAGSOP, strSop);
                    vecReg.add(INT_TBL_COMSOL, "N");

                    vecDataTblPag.add(vecReg);
                }
                objTblModPag.setData(vecDataTblPag);
                tblPag.setModel(objTblModPag);

                vecDataTblPag = null;



                /**
                 * ********************************************************************************************
                 */
                double dblValRet = 0;
                String strFecCor = "";
                for (int x = 0; x < tblPag.getRowCount(); x++) {
                    dblValRet = Double.parseDouble(objInvItm.getIntDatoValidado(tblPag.getValueAt(x, INT_TBL_PAGRET)));
                    if (dblValRet == 0.00) {
                        strFecCor = tblPag.getValueAt(x, INT_TBL_PAGFEC).toString();
                        break;
                    }
                }

                String strF1 = objUti.formatearFecha(strFecSisBase, "yyyy-MM-dd", "yyyy/MM/dd");
                java.util.Date fac1 = objUti.parseDate(strF1, "yyyy/MM/dd");
                int intAnioAct = (fac1.getYear() + 1900);

                //  por alfredo.  año nuevo 31 dic año anterior
                String strF = objUti.formatearFecha(strFecCor, "dd/MM/yyyy", "yyyy/MM/dd");
                java.util.Date fac = objUti.parseDate(strF, "yyyy/MM/dd");
                int intAnioCre = (fac.getYear() + 1900);


                if (intAnioCre > intAnioAct) {
                    strFecCor = "31/12/" + intAnioAct;
                }

                for (int x = 0; x < tblPag.getRowCount(); x++) {
                    dblValRet = Double.parseDouble(objInvItm.getIntDatoValidado(tblPag.getValueAt(x, INT_TBL_PAGRET)));
                    if (dblValRet > 0.00) {
                        tblPag.setValueAt(strFecCor, x, INT_TBL_PAGFEC);
                    }

                }

                /**
                 * ********************************************************************************************
                 */
                 
            }
             
        } 
        catch (SQLException Evt) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        } 
        catch (Exception Evt) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        return blnRes;
    }

    
    
    private boolean cargaForPagComSol() {
        boolean blnRes=true;
        Date datFecDocComSol;
        double dblRetFue = 0;
        try {
            datFecDocComSol = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            System.out.println("datFecDocComSol " + datFecDocComSol.toString());
            System.out.println("---->>> "+ objUti.formatearFecha(datFecDocComSol, "dd/MM/yyyy"));
            java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL_PAGLIN, "");
            vecReg.add(INT_TBL_PAGCRE, "");
            vecReg.add(INT_TBL_PAGFEC, objUti.formatearFecha(datFecDocComSol, "dd/MM/yyyy"));
            dblRetFue = objUti.redondear((dblBaseIva * (dblPorComSol) / 100), 2);
            vecReg.add(INT_TBL_PAGRET, "");
            vecReg.add(INT_TBL_PAGMON, "" + dblRetFue);
            vecReg.add(INT_TBL_PAGGRA, "");
            vecReg.add(INT_TBL_PAGCOD, "");
            vecReg.add(INT_TBL_PAGSOP, "N");
            vecReg.add(INT_TBL_COMSOL, "S");  // <<<< ===== COMPENSACION SOLIDARIA
            vecDataTblPag.add(vecReg);
        }  
        catch (Exception Evt) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        return blnRes;
    }
        
    
    
    /**
     * Configura la tabla de pago
     *
     * @return true si OK 
     * @return false si hay algun error
     */
    private boolean configurarTablaPago() 
    {
        boolean blnRes = false;
        try 
        {
            //Almacena cabeceras
            Vector vecCabPAg = new Vector();                           
            vecCabPAg.clear();
            vecCabPAg.add(INT_TBL_PAGLIN, "");
            vecCabPAg.add(INT_TBL_PAGCRE, "Días.Crédito");
            vecCabPAg.add(INT_TBL_PAGFEC, "Fec.Vencimiento");
            vecCabPAg.add(INT_TBL_PAGRET, "%Retención");
            vecCabPAg.add(INT_TBL_PAGMON, "Monto");
            vecCabPAg.add(INT_TBL_PAGGRA, "Días.Gracia");
            vecCabPAg.add(INT_TBL_PAGCOD, "Cod.Ret");
            vecCabPAg.add(INT_TBL_PAGSOP, "Tx_Sop");
            vecCabPAg.add(INT_TBL_COMSOL, "Compensacion"); // JM 7/Feb/2017
            objTblModPag = new ZafTblMod();
            objTblModPag.setHeader(vecCabPAg);

            tblPag.setModel(objTblModPag);
            tblPag.setRowSelectionAllowed(true);
            tblPag.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblPag, INT_TBL_PAGLIN);

            objTblModPag.setColumnDataType(INT_TBL_PAGCRE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModPag.setColumnDataType(INT_TBL_PAGRET, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModPag.setColumnDataType(INT_TBL_PAGMON, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModPag.setColumnDataType(INT_TBL_PAGGRA, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            tblPag.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux = tblPag.getColumnModel();
            //Configurar JTable: Establecer el ancho de las columnas.
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_PAGLIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_PAGCRE).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_PAGFEC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_PAGRET).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_PAGMON).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_PAGGRA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_PAGCOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PAGSOP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_COMSOL).setPreferredWidth(60);
            

            tblPag.getTableHeader().setReorderingAllowed(false);
            tcmAux = null;
            blnRes=true;

        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }

    
        
        
        /**
    * Permite obtener dia de validez de la cotizacion
    */
    private void getObtenerDiaValCot(java.sql.Connection conExt) {
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try {
            if (conExt != null) {
                stmLoc = conExt.createStatement();

                strSql = "SELECT ne_diavalcotven FROM tbm_loc WHERE co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " ";
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    int_Num_Dia_Val = rstLoc.getInt("ne_diavalcotven");
                }
                rstLoc.close();
                stmLoc.close();
                rstLoc = null;
                stmLoc = null;
            }
        } 
        catch (SQLException Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        } 
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
    }
        
        
         private int getUltCoDoc(java.sql.Connection conExt, int CodEmp, int CodLoc) {
            int valReturn = 0;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            try {
                if(conExt!=null){
                    stmLoc = conExt.createStatement();
                    strSql = "SELECT Max(co_cot) as co_cot FROM tbm_cabCotVen where "
                        + " co_emp = " + CodEmp + " and co_loc = " + CodLoc;
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        valReturn = rstLoc.getInt("co_cot");
                    }
                    rstLoc.close();
                    rstLoc=null;
                    valReturn++;
                    stmLoc.close();
                    stmLoc = null;
                }
            } 
            catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(jifCnt, e);
                valReturn = -1;
            } 
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(jifCnt, e);
                valReturn = -1;
            }
            return valReturn;
        }
         
         
         
    public void calculaSubtotalServiNoServi(java.sql.Connection conn, String strServi, int CodEmp, int CodLoc, int CodCot) {
        double dblCan, dblDes, dblTotal = 0.00, dblPre = 0.00, dblValDes = 0.00;
        double dblSub = 0, dblIva = 0, dblTmp = 0;
        int intFilSel = 0;
        dblSubSerNoSer = 0;
        dblIvaSerNoSer = 0;
        BigDecimal bgdCanItm;
        BigDecimal bgdPreItm;
        BigDecimal bgdValDesItm;
        BigDecimal bgdPorDesItm;
        BigDecimal bgdTotItm=BigDecimal.ZERO;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot, a2.co_reg, a2.co_itm, a2.nd_can, a2.nd_preUni, a2.nd_porDes,a2.st_ivaVen  \n";
                strSql+=" FROM tbm_cabCotVen as a1 \n";
                strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" \n";
                strSql+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_cot, a2.co_reg \n";
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    if (objInvItm.getEstItm(conn, CodEmp, rstLoc.getInt("co_itm"), strServi)) {

                        dblCan = rstLoc.getDouble("nd_can");
                        dblPre = rstLoc.getDouble("nd_preUni");
                        dblDes = rstLoc.getDouble("nd_porDes");

                        bgdCanItm=BigDecimal.valueOf(dblCan) ;
                        bgdPreItm=BigDecimal.valueOf(dblPre) ;
                        bgdPorDesItm=BigDecimal.valueOf(dblDes) ;

                        //DESCUENTO
                        bgdValDesItm = bgdPorDesItm.multiply((bgdCanItm.multiply(bgdPreItm))); 
                        bgdValDesItm = bgdValDesItm.divide(new BigDecimal("100"),objParSis.getDecimalesBaseDatos(),BigDecimal.ROUND_HALF_UP);
                        ///TOTAL
                        bgdTotItm=objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)).subtract(bgdValDesItm), objParSis.getDecimalesMostrar());    
                        dblTotal = bgdTotItm.doubleValue();

                        dblSub += dblTotal;


                        if (rstLoc.getString("st_ivaVen").equals("S")) {
                            dblTmp = dblTotal;
                            dblIva = dblIva + (((dblTmp * dblPorIva) == 0) ? 0 : (dblTmp * dblPorIva) / 100);
                        } else {
                            dblIva = dblIva + 0;
                        }

                    }
                    
                }
               
                        
                     

                if (stIvaVen.equals("N")) {
                    dblIvaSerNoSer = 0.00;
                } else {
                    dblIvaSerNoSer = objUti.redondear(dblIva, intNumDec);
                }


                dblSubSerNoSer = objUti.redondear(dblSub, intNumDec);

            }
        } 
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
    }

    
    //Códigos de Motivos del documento para las retenciones
    private int intCodMotBien = 0;                                                //Bien
    private int intCodMotServ = 0;                                                //Servicio
    private int intCodMotTran = 0;                                                //Transporte
    
    private boolean FormaRetencion(java.sql.Connection conTmp, int CodEmp) {
        boolean blnRes = false;
        java.sql.Statement stmTmp;
        java.sql.ResultSet rst;
        try {
            if (conTmp != null) {
                stmTmp = conTmp.createStatement();
                String sql = "SELECT tx_tipmot, co_mot FROM tbm_motdoc WHERE co_emp=" + CodEmp + " AND tx_tipmot in ('B','S','T') ";
                rst = stmTmp.executeQuery(sql);
                while (rst.next()) {
                    //intCodMotDoc = rst.getInt(1);
                    if (rst.getString("tx_tipmot").equals("B")) {
                        intCodMotBien = rst.getInt("co_mot");
                    } else if (rst.getString("tx_tipmot").equals("S")) {
                        intCodMotServ = rst.getInt("co_mot");
                    } else if (rst.getString("tx_tipmot").equals("T")) {
                        intCodMotTran = rst.getInt("co_mot");
                    }
                    blnRes = true;
                }
                rst.close();
                stmTmp.close();
                rst = null;
                stmTmp = null;
            }
        } catch (SQLException Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        return blnRes;
    }
    
    private int intCodTipDocFacEle=228;
    private String strTipPer_emp = "";
    private int intCodBodPre = 0;                                                 //Almacena codigo de bodega predeterminada.
    private String strNomBodPrv = "";
    private String strNomBodPtaPar = "";
    private ZafCtaCtb_dat objZafCtaCtb_dat;
    private double bldivaEmp = 0;
    /*--------------------------------------------------------------------------------*/
    private boolean blnIsComSol=false;// JoséMario 31/May/2016 Compensacion Solidaria 
    private double dblPorComSol=0.00, dblComSol; // JoséMario 31/May/2016 Compensacion Solidaria
    /*--------------------------------------------------------------------------------*/
    private double dblPorIva;                                                  //Porcentaje de Iva para la empresa enviado en ZafParSis
    private double dblTotalCot, dblIvaCot;
    private double dblSubtotalCot;
    private double dblBaseCero, dblBaseIva, dblValorPagar=0.00;
    private double dblIvaVen;
    /*--------------------------------------------------------------------------------*/
    
    private boolean cargarTipEmp(int CodEmp, int CodLoc) {
        boolean blnRes=true;
        Statement stmTipEmp;
        ResultSet rstEmp;
        String sSql;
        java.sql.Connection conLoc;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmTipEmp = conLoc.createStatement();
                sSql = "select b.co_tipper , b.tx_descor , round(a.nd_ivaVen,2) as porIva , bod.co_bod, a1.tx_dir, a1.tx_nom as nombod  FROM  tbm_emp as a "
                        + " left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper)"
                        + " left join tbr_bodloc as bod on(bod.co_emp=a.co_emp and bod.co_loc=" + CodLoc + " and bod.st_reg='P')  "
                        + " inner join tbm_bod as a1 on (a1.co_emp=bod.co_emp and a1.co_bod=bod.co_bod ) "
                        + " where a.co_emp=" +CodEmp;
                rstEmp = stmTipEmp.executeQuery(sSql);
                if (rstEmp.next()) {
                    strTipPer_emp = rstEmp.getString("tx_descor");
                    intCodBodPre = rstEmp.getInt("co_bod");
                    intCodTipPerEmp = rstEmp.getInt("co_tipper");
                    strNomBodPtaPar = rstEmp.getString("tx_dir");
                    strNomBodPrv = rstEmp.getString("nombod");
                }                
                objZafCtaCtb_dat = new ZafCtaCtb_dat(objParSis,CodEmp, CodLoc, intCodTipDocFacEle);
                bldivaEmp=objZafCtaCtb_dat.getPorIvaVen();
                dblPorComSol=objZafCtaCtb_dat.getPorComSol();
                objZafCtaCtb_dat = null;
                dblPorIva=bldivaEmp;
                dblIvaVen=dblPorIva;
                
                rstEmp.close();
                stmTipEmp.close();
                stmTipEmp = null;
                rstEmp = null;
                conLoc.close();
                conLoc=null;
                
            }
        } 
        catch (SQLException Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            blnRes=false;
        } 
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            blnRes=false;
        }
        return blnRes;
    }
    
    private String stIvaVen = "S";
    private String strCodTipPerCli = "";
    private int intTipForPag2 = 0;
    
    private boolean cargarTipoCliente(int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT Cli.st_ivaVen, Cli.co_cli, Cli.co_tipper , forpag.ne_tipforpag ";
                strSql+=" FROM tbm_cabCotven as CotCab \n";
                strSql+=" left outer join  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_cli = cli.co_cli) \n";
                strSql+=" left outer join tbm_cabForPag as forpag on (CotCab.co_emp=forpag.co_emp and CotCab.co_forPag = forpag.co_forpag ) \n";
                strSql+=" WHERE CotCab.co_emp="+CodEmp+" AND CotCab.co_loc="+CodLoc+" AND CotCab.co_cot="+CodCot+" ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    strCodTipPerCli = rstLoc.getString("co_tipper");
                    stIvaVen = rstLoc.getString("st_ivaven");
                    intTipForPag2 = Integer.parseInt(rstLoc.getString("ne_tipforpag"));
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            
        }
        catch (SQLException Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            blnRes=false;
        } 
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            blnRes=false;
        }
        return blnRes;
    }
    

    
    private int intCodTipPerEmp;
    
    private void cargaForPag(java.sql.Connection conn, int intCodMot, int CodEmp) {
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSql = "";
        double dblRetFue = 0, dblRetIva = 0;
        try {
            if (intTipForPag2 == 4) {
                return;
            }
            if (conn != null) {
               stm = conn.createStatement();
                strSql = " select tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta";
                strSql += " from tbm_polret as polret left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)";
                strSql += " where polret.co_emp = " + CodEmp + " and co_mot = " + intCodMot + " and co_sujret = " + intCodTipPerEmp + " and co_ageret  = " + strCodTipPerCli + " "
                        + " AND polret.st_reg='A' AND  '" + strFecSisBase + "'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
           
                rst = stm.executeQuery(strSql);
                while (rst.next()) {
                    if (rst.getString("tx_aplret").equals("S")) {
                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_PAGLIN, "");
                        vecReg.add(INT_TBL_PAGCRE, "");
                        vecReg.add(INT_TBL_PAGFEC, strFecSisBase);
                        dblRetFue = objUti.redondeo((dblSubSerNoSer * (rst.getDouble("nd_porret") / 100)), 2);
                        dblRetFueGlo += dblRetFue;
                        vecReg.add(INT_TBL_PAGRET, rst.getString("nd_porret"));
                        vecReg.add(INT_TBL_PAGMON, "" + dblRetFue);
                        vecReg.add(INT_TBL_PAGGRA, "");
                        vecReg.add(INT_TBL_PAGCOD, "" + rst.getString("co_tipret"));
                        vecReg.add(INT_TBL_PAGSOP, "N");
                        vecReg.add(INT_TBL_COMSOL, "N");
                        vecDataTblPag.add(vecReg);
                    }
                    if (rst.getString("tx_aplret").equals("I")) {
                        if (dblIvaSerNoSer > 0) {
                            java.util.Vector vecReg = new java.util.Vector();
                            vecReg.add(INT_TBL_PAGLIN, "");
                            vecReg.add(INT_TBL_PAGCRE, "");
                            vecReg.add(INT_TBL_PAGFEC, strFecSisBase);
                            if(blnIsComSol){
                                dblRetIva = objUti.redondeo(((dblIvaSerNoSer - dblComSol) * (rst.getDouble("nd_porret") / 100)), 2); 
                            }else{
                                dblRetIva = objUti.redondeo((dblIvaSerNoSer * (rst.getDouble("nd_porret") / 100)), 2);
                            }
                            dblRetIvaGlo += dblRetIva;
                            vecReg.add(INT_TBL_PAGRET, rst.getString("nd_porret"));
                            vecReg.add(INT_TBL_PAGMON, "" + dblRetIva);
                            vecReg.add(INT_TBL_PAGGRA, "");
                            vecReg.add(INT_TBL_PAGCOD, "" + rst.getString("co_tipret"));
                            vecReg.add(INT_TBL_PAGSOP, "N");
                            vecReg.add(INT_TBL_COMSOL, "N");
                            vecDataTblPag.add(vecReg);
                        }
                    }
                }
                rst.close();   
                rst = null;

            }
        } catch (SQLException Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
    }
    
}
