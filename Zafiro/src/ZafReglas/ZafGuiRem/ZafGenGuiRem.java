/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas.ZafGuiRem;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPulFacEle.ZafPulFacEle;
import Librerias.ZafUtil.ZafUtil;
import ZafReglas.ZafImp;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author sistemas4
 */
public class ZafGenGuiRem {
    
    ZafImp objZafImp=new ZafImp();
    ZafImp oi=new ZafImp();
    private ResultSet rs;
    private Statement stmSql;
    private String strQuery="";
    //private ZafCon oc=new ZafCon();
    private int intCodBodGrp;
    private int numOrdDes;
    private int intCodTipDocEmpLocGuia;
    private String strSecDocOri;
    private ZafUtil objUti;
    private ZafPulFacEle objPulFacEle;
    private ZafParSis objZafParSis;
    
    private final String strVersion = " v0.5";

    public ZafGenGuiRem(int coBodGrp,int numOrddes) {
        intCodBodGrp=coBodGrp;
        numOrdDes=numOrddes;
        
    }

    public ZafGenGuiRem() {
    }
    
    
    
    
    /**
     * <h2>GENERACION DE LA GUIA DE REMISION</h2>
     * <P>Metodo público para la generación de la guía de remisión, de acuerdo a la orden de despacho
     * emitida con anterioridad, esta guía se genera después de la confirmación del egreso de la mercadería</P>
     */
    //public void generarGuiRem(int coloc,Connection con){
	    //public void generarGuiRem(int coloc,Connection con){
//    public boolean generarGuiRem(int coloc,Connection con){
//        boolean booRetorno=true;
//    
//        try {
//            /**
//             * agregue numorddes para que esta consulta solo me traiga un registro por cada OD, que formará el cuerpo de la guía 
//             * de remisión
//             */
//
//            stmSql=con.createStatement();
//            strQuery=" SELECT "
//            + " a1.co_emp as coempgui, a1.co_loc as colocgui, a1.co_tipdoc as cotipdocgui, a1.co_doc as codocgui "
//            + " ,a1.ne_numorddes, a.tx_tipcon, a.co_emp,a.co_loc,a.co_tipdoc,a.co_doc, a.co_veh, a.co_cho, a.tx_idetra, a.tx_nomtra, a.tx_plavehtra, a7.co_bod, a7.tx_nom "
//            + " FROM tbr_bodEmpBodGrp as a6  "
//            + " inner join tbm_bod as a7 on (a7.co_emp=a6.co_emp and a7.co_bod=a6.co_bod  ) "
//            + " inner join tbm_cabguirem as a1 on ( a1.co_emp=a7.co_emp and a1.co_ptopar=a7.co_bod ) "
//            + " inner join tbm_cabingegrmerbod as a on ( a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelguirem and a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem ) "
//            + " WHERE    "
//            + " ( a6.co_empGrp=0 AND a6.co_bodGrp =  "+intCodBodGrp+"  )  AND  "
//            + " a1.ne_numdoc=0 and a1.st_reg = 'A' /*AND a1.st_imporddes='S' */ and a.st_reg='A' and a.tx_tipCon in ('P','T') "
//            + " and  a.co_mnu = 2205 and a1.ne_numorddes="+numOrdDes;
//            
//            rs=stmSql.executeQuery(strQuery);
//            if (rs.next()) {
//                if(rs.getInt("co_tipdoc")==114)
//                    intCodTipDocEmpLocGuia=102;
//                else if(rs.getInt("co_tipdoc")==233)
//                    intCodTipDocEmpLocGuia=231;
//          
//                oi.setEmp(rs.getInt("coempgui"));
//                oi.setLoc(rs.getInt("colocgui"));
//                oi.setTipdoc(intCodTipDocEmpLocGuia);
//                oi.setCoDoc(rs.getInt("codocgui"));
//                
////                crearGuiaRemFin(
////                                rs.getInt("coempgui"),rs.getInt("colocgui"), rs.getInt("cotipdocgui"), intCodTipDocEmpLocGuia
////                              , rs.getInt("codocgui"), rs.getInt("co_emp"), rs.getInt("co_loc"), rs.getInt("co_tipdoc"), rs.getInt("co_doc")
////                              , rs.getInt("co_veh"), rs.getInt("co_cho"), rs.getString("tx_idetra"), rs.getString("tx_nomtra")
////                              , rs.getString("tx_plavehtra"), true ,coloc,con,intCodBodGrp);
//                
//                if(crearGuiaRemFin(
//                                rs.getInt("coempgui"),rs.getInt("colocgui"), rs.getInt("cotipdocgui"), intCodTipDocEmpLocGuia
//                              , rs.getInt("codocgui"), rs.getInt("co_emp"), rs.getInt("co_loc"), rs.getInt("co_tipdoc"), rs.getInt("co_doc")
//                              , rs.getInt("co_veh"), rs.getInt("co_cho"), rs.getString("tx_idetra"), rs.getString("tx_nomtra")
//                              , rs.getString("tx_plavehtra"), true ,coloc,con,intCodBodGrp)){
//                    booRetorno=true;
//                }else{
//                    booRetorno=false;
//                }                
//                
//            }else{
//                booRetorno=false;
//            }
//            
//
//            
//            stmSql.close();
//            rs.close();
//            
////            asignarCosDet();
////            asignarCostCab();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return booRetorno;
//    
//    }//fin de generar guia de remision


public void generarGuiRem(int coloc,Connection con){
    
        try {
            /**
             * agregue numorddes para que esta consulta solo me traiga un registro por cada OD, que formará el cuerpo de la guía 
             * de remisión
             */

            stmSql=con.createStatement();
            strQuery=" SELECT "
            + " a1.co_emp as coempgui, a1.co_loc as colocgui, a1.co_tipdoc as cotipdocgui, a1.co_doc as codocgui "
            + " ,a1.ne_numorddes, a.tx_tipcon, a.co_emp,a.co_loc,a.co_tipdoc,a.co_doc, a.co_veh, a.co_cho, a.tx_idetra, a.tx_nomtra, a.tx_plavehtra, a7.co_bod, a7.tx_nom "
            /* SE AGREGA PARA SABER QUIEN HIZO LA CONFIRMACION*/
            + ", a.co_usring"
            /* SE AGREGA PARA SABER QUIEN HIZO LA CONFIRMACION*/
            + " FROM tbr_bodEmpBodGrp as a6  "
            + " inner join tbm_bod as a7 on (a7.co_emp=a6.co_emp and a7.co_bod=a6.co_bod  ) "
            + " inner join tbm_cabguirem as a1 on ( a1.co_emp=a7.co_emp and a1.co_ptopar=a7.co_bod ) "
            + " inner join tbm_cabingegrmerbod as a on ( a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelguirem and a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem ) "
            + " WHERE    "
            + " ( a6.co_empGrp=0 AND a6.co_bodGrp =  "+intCodBodGrp+"  )  AND  "
            + " a1.ne_numdoc=0 and a1.st_reg = 'A' /*AND a1.st_imporddes='S' */ and a.st_reg='A' and a.tx_tipCon in ('P','T') "
            + " and  a.co_mnu = 2205 and a1.ne_numorddes="+numOrdDes;
            
            rs=stmSql.executeQuery(strQuery);
            if (rs.next()) {
                if(rs.getInt("co_tipdoc")==114)
                    intCodTipDocEmpLocGuia=102;
                else if(rs.getInt("co_tipdoc")==233)
                    intCodTipDocEmpLocGuia=231;
                
          
            oi.setEmp(rs.getInt("coempgui"));
            oi.setLoc(rs.getInt("colocgui"));
            oi.setTipdoc(intCodTipDocEmpLocGuia);
            oi.setCoDoc(rs.getInt("codocgui"));
                
                crearGuiaRemFin(
                                rs.getInt("coempgui"),rs.getInt("colocgui"), rs.getInt("cotipdocgui"), intCodTipDocEmpLocGuia
                              , rs.getInt("codocgui"), rs.getInt("co_emp"), rs.getInt("co_loc"), rs.getInt("co_tipdoc"), rs.getInt("co_doc")
                              , rs.getInt("co_veh"), rs.getInt("co_cho"), rs.getString("tx_idetra"), rs.getString("tx_nomtra")
                              , rs.getString("tx_plavehtra"), true ,coloc,con,intCodBodGrp,rs.getInt("co_usring"));
                
             
                
            }
            

            
            stmSql.close();
            rs.close();
            
//            asignarCosDet();
//            asignarCostCab();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    
    }//fin de generar guia de remision

   
    
    /**
     * <h1>CREACION DE LA GUIA DE REMISIÓN</h1>
     * <p>Este es un metodo privado para la generación de la guía de remisión, aquí se ingresan tanto la cabecerade la guía como su detalle </p>
     */

     //private void crearGuiaRemFin( int intCodEmpGui, int intCodLocGui, int intCodTipDocGui, int intCodTipDocGuiNue
//    private boolean crearGuiaRemFin( int intCodEmpGui, int intCodLocGui, int intCodTipDocGui, int intCodTipDocGuiNue    
//        , int intCodDocGui, int intCodEmpConf, int intCodLocConf, int intCodTipDocConf, int intCodDocConf
//        , int intCodVeh, int intCodCho, String strIdeTra, String strNomTra, String strPlaVehTra, boolean blnObtDatGu,int coloc,Connection con, int intCodBodGrp){
//       
//        //int intNumGuiFin=_getNumGuiaFin(intCodEmpGui, intCodLocGui, intCodTipDocGui);
//        //int intNumGuiFin=_getNumGuiaFin(intCodEmpGui, intCodLocGui, intCodTipDocGuiNue);
//        //verificar local para inmaconsa
//        
//        //objZafParSis=new ZafParSis();
//        
//        //numeracion de guia
//        ZafReglas.ZafGenGuiRem objZafGenGuiRem=new ZafReglas.ZafGenGuiRem();
//        String[] strRet=new String[4];
//        boolean booRetorno=false;
//
//        
//        if(intCodBodGrp!=15){
//            strRet=objZafGenGuiRem.obtenerRptImpGuiRem(intCodBodGrp);
//        }else{
//            strRet[0]="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
//            strRet[1]="guias_inmaconsa";   
//            strRet[2]=String.valueOf(intCodEmpGui);
//            strRet[3]=String.valueOf(intCodLocGui);
//
//        }
//        //int intNumGuiFin=_getNumGuiaFin(Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]), intCodTipDocGuiNue,con);
//        //int intNumGuiFin=_getNumGuiaFin2(Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]), intCodTipDocGuiNue,con);
//        //int intNumGuiFin=_getNumGuiaFin2(intCodEmpGui, intCodLocGui, intCodTipDocGuiNue,con);
//        /*intCodEmpGui=Integer.parseInt(strRet[2]);
//        intCodLocGui=Integer.parseInt(strRet[3]);*/
//        
//        System.out.println("revision actual");
//        int intCodDocGuiNue=traerGuiaNueva(intCodEmpGui, intCodLocGui, intCodTipDocGuiNue,con);
//        
//        int intNumGuiFin=_getNumGuiaFin2(Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]), intCodTipDocGuiNue,con);
//        
//        try {
//            
//           //strSecDocOri=traerDocOri(intCodEmpConf, intCodLocConf,con);
//            //strSecDocOri=traerDocOri(Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]),con);
//            strSecDocOri=traerDocOri(Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]),con);
//            /*if (coloc==10) {
//                actualizarCabTipDoc(intNumGuiFin, intCodEmpConf, coloc, intCodTipDocGuiNue,con);
//            }else{
//                actualizarCabTipDoc(intNumGuiFin, intCodEmpConf, intCodLocConf, intCodTipDocGuiNue,con);
//            }*/
//           
//           //actualizarCabTipDoc(intNumGuiFin, Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]), intCodTipDocGuiNue,con);
//            actualizarCabTipDoc(intNumGuiFin, Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]), intCodTipDocGuiNue,con);
//            
//            
//            //con.setAutoCommit(false);
//            
//           if (insertarCabGuiaRemFin(intCodEmpGui, intCodLocGui, intCodTipDocGui, intCodTipDocGuiNue, intCodDocGui, intCodDocGuiNue, intNumGuiFin, intCodEmpConf, intCodLocConf, intCodTipDocConf, intCodDocConf, intCodVeh, intCodCho, strIdeTra, strNomTra, strPlaVehTra, blnObtDatGu,con ) ) {
//                if (insertarDetGuiaRemFin(intCodEmpConf, intCodLocConf, intCodTipDocConf, intCodDocConf, intCodTipDocGuiNue, intCodDocGuiNue,con ) ) {
//                    //con.commit();
//                    //enviarPulsoFacturacionElectronica();
//                     booRetorno=true;
//		     //enviarPulsoGuiaElectronica();
//                }else{
//                    //con.rollback();
//                    booRetorno=false;
//                }
//            }else{
//               //con.rollback();
//                booRetorno=false;               
//           }
//            ZafImpConGuiRem objImpGuiRem=new ZafImpConGuiRem();            
//            //objImpGuiRem.imprimirGuiRemCliNotRet_autSRI(con, oi.getEmp(), oi.getLoc(), oi.getTipdoc(), intCodDocGuiNue, intCodBodGrp);
//            //enviarPulsoImpresionGui("imprimeGuiaRemisionConfirmacion"+"-"+oi.getEmp()+"-"+oi.getLoc()+"-"+oi.getTipdoc()+"-"+intCodDocGuiNue+"-"+intCodBodGrp,6000);
//            
//       } catch (Exception e) {
//           e.printStackTrace();
//           objUti.mostrarMsgErr_F1(null, e);
//       }
//       return  booRetorno;
//   }//fin de crearGuiaRemFin
   
   
   
private void crearGuiaRemFin( int intCodEmpGui, int intCodLocGui, int intCodTipDocGui, int intCodTipDocGuiNue
        , int intCodDocGui, int intCodEmpConf, int intCodLocConf, int intCodTipDocConf, int intCodDocConf
        , int intCodVeh, int intCodCho, String strIdeTra, String strNomTra, String strPlaVehTra, boolean blnObtDatGu,int coloc,Connection con, int intCodBodGrp, int intCoUsrIng){
       
        //int intNumGuiFin=_getNumGuiaFin(intCodEmpGui, intCodLocGui, intCodTipDocGui);
        //int intNumGuiFin=_getNumGuiaFin(intCodEmpGui, intCodLocGui, intCodTipDocGuiNue);
        //verificar local para inmaconsa
        
        //objZafParSis=new ZafParSis();
        
        //numeracion de guia
        ZafReglas.ZafGenGuiRem objZafGenGuiRem=new ZafReglas.ZafGenGuiRem();
        String[] strRet=new String[4];

        
        if(intCodBodGrp!=15){
            strRet=objZafGenGuiRem.obtenerRptImpGuiRem(intCodBodGrp);
        }else{
            strRet[0]="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strRet[1]="guias_inmaconsa";   
            strRet[2]=String.valueOf(intCodEmpGui);
            strRet[3]=String.valueOf(intCodLocGui);

        }
        //int intNumGuiFin=_getNumGuiaFin(Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]), intCodTipDocGuiNue,con);
        //int intNumGuiFin=_getNumGuiaFin2(Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]), intCodTipDocGuiNue,con);
        //int intNumGuiFin=_getNumGuiaFin2(intCodEmpGui, intCodLocGui, intCodTipDocGuiNue,con);
        /*intCodEmpGui=Integer.parseInt(strRet[2]);
        intCodLocGui=Integer.parseInt(strRet[3]);*/
        
        System.out.println("revision actual");
        int intCodDocGuiNue=traerGuiaNueva(intCodEmpGui, intCodLocGui, intCodTipDocGuiNue,con);
        
        int intNumGuiFin=_getNumGuiaFin2(Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]), intCodTipDocGuiNue,con);
        
        try {
            
           //strSecDocOri=traerDocOri(intCodEmpConf, intCodLocConf,con);
            //strSecDocOri=traerDocOri(Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]),con);
            strSecDocOri=traerDocOri(Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]),con);
            /*if (coloc==10) {
                actualizarCabTipDoc(intNumGuiFin, intCodEmpConf, coloc, intCodTipDocGuiNue,con);
            }else{
                actualizarCabTipDoc(intNumGuiFin, intCodEmpConf, intCodLocConf, intCodTipDocGuiNue,con);
            }*/
           
           //actualizarCabTipDoc(intNumGuiFin, Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]), intCodTipDocGuiNue,con);
            actualizarCabTipDoc(intNumGuiFin, Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]), intCodTipDocGuiNue,con);
            
            
            con.setAutoCommit(false);
            
           if (insertarCabGuiaRemFin(intCodEmpGui, intCodLocGui, intCodTipDocGui, intCodTipDocGuiNue, intCodDocGui, intCodDocGuiNue, intNumGuiFin, intCodEmpConf, intCodLocConf, intCodTipDocConf, intCodDocConf, intCodVeh, intCodCho, strIdeTra, strNomTra, strPlaVehTra, blnObtDatGu,intCoUsrIng,con ) ) {
                if (insertarDetGuiaRemFin(intCodEmpConf, intCodLocConf, intCodTipDocConf, intCodDocConf, intCodTipDocGuiNue, intCodDocGuiNue,con ) ) {
                    con.commit();
                    //enviarPulsoFacturacionElectronica();
                    enviarPulsoGuiaElectronica();
                }else{con.rollback();}
            }else{con.rollback();}
            ZafImpConGuiRem objImpGuiRem=new ZafImpConGuiRem();            
            //objImpGuiRem.imprimirGuiRemCliNotRet_autSRI(con, oi.getEmp(), oi.getLoc(), oi.getTipdoc(), intCodDocGuiNue, intCodBodGrp);
            enviarPulsoImpresionGui("imprimeGuiaRemisionConfirmacion"+"-"+oi.getEmp()+"-"+oi.getLoc()+"-"+oi.getTipdoc()+"-"+intCodDocGuiNue+"-"+intCodBodGrp,6000);
            
       } catch (Exception e) {
           e.printStackTrace();
           objUti.mostrarMsgErr_F1(null, e);
       }
       
   }//fin de crearGuiaRemFin       


   

    /**
     * <h1>NUEVO NUMERO DE SECUENCIA PARA LA NUEVA GUIA DE REMISION</h1>
     * <p>Esta función consulta la secuencia numerica existente en la cabecera de guía de remisión para el campo "co_doc",
     * se le aumenta una unidad y devuelve el nuevo número de la guía </p>
     */
    private int traerGuiaNueva(int intCodEmpGui,int intCodLocGui,int intCodTipDocGuiNue,Connection con){
        int resp=0;
        Statement stmSql=null;
        ResultSet rs=null;
        try {
            //stmSql=oc.ocon.createStatement();
            stmSql=con.createStatement();
            strQuery="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabguirem WHERE " +
            " co_emp="+intCodEmpGui+" AND co_loc="+intCodLocGui+" AND co_tipDoc="+intCodTipDocGuiNue;
            rs=stmSql.executeQuery(strQuery);
            if (rs.next()) {
                resp=rs.getInt("co_doc");
            }
            stmSql.close();
            rs.close();
            stmSql=null;
            rs=null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return resp;
    }//
    
    
    
    /**
     * <h1>NUMERO DE DOCUMENTO EN LA CABECERA DE LOS TIPOS DE DOCUMENTOS</h1>
     * <p>Esta función devuelve el ultimo documento que se hizo de acuerdo al tipo de documento, se usa al genrearse la nueva guia de remisión,
     * al insertar la cabecera de dicha guía</p>
     */
    private int _getNumGuiaFin(int intCodEmpGuia, int intCodLocGuia, int intCodTipDoc, Connection con){
        int resp=0;
        
        
        try {
            
            
            
            if ((intCodEmpGuia==1 && intCodLocGuia==10)|| (intCodEmpGuia==2 && intCodLocGuia==10) || (intCodEmpGuia==4 && intCodLocGuia==10)){
                intCodEmpGuia=1; intCodLocGuia=10;
            }
            
            //stmSql=oc.ocon.createStatement();
            stmSql=con.createStatement();
            strQuery="SELECT CASE WHEN ne_ultdoc is null THEN 1 else ne_ultdoc+1 END as numguia FROM tbm_cabtipdoc WHERE co_emp="+intCodEmpGuia+"   "+
            " AND co_loc="+intCodLocGuia+" AND co_tipdoc= "+intCodTipDoc+"  ";
            rs=stmSql.executeQuery(strQuery);
            if (rs.next()) {
                resp=rs.getInt("numguia");
            }
            stmSql.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return resp;
    }//fin de _getNumGuiaFin
    
    
    
    private int _getNumGuiaFin2(int intCodEmpGuia, int intCodLocGuia, int intCodTipDoc, Connection con){
        int resp=0;
        Statement stmSql=null;
        ResultSet rs=null;
        String strQuery="";
        try {
            stmSql=con.createStatement();
            strQuery="SELECT CASE WHEN ne_ultdoc is null THEN 1 else ne_ultdoc+1 END as numguia FROM tbm_cabtipdoc WHERE co_emp="+intCodEmpGuia+"   "+
            " AND co_loc="+intCodLocGuia+" AND co_tipdoc= "+intCodTipDoc+"  ";
            rs=stmSql.executeQuery(strQuery);
            if (rs.next()) {
                resp=rs.getInt("numguia");
            }
            stmSql.close();
            rs.close();
            stmSql=null;
            rs=null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return resp;
    }//fin de _getNumGuiaFin    
    
    
    
    /**
     * <h1>SECUENCIA DE DOCUMENTOS EN TBM_LOC</h1>
     */
    private String traerDocOri(int intCodEmpLoc,int intCodLocLoc,Connection con){
        String rsp="";
        Statement stmSql=null;
        ResultSet rs=null;
        
        try {
            stmSql=con.createStatement();
            strQuery="";
            strQuery+=" SELECT co_emp, co_loc, tx_nom, tx_secdoc";
            strQuery+=" FROM tbm_loc";
            strQuery+=" WHERE co_emp=" + intCodEmpLoc + " AND co_loc=" + intCodLocLoc + "";
            strQuery+=" AND st_reg IN('A','P')";
            rs=stmSql.executeQuery(strQuery);
            if (rs.next()) {
                rsp=rs.getString("tx_secdoc");
            }
            stmSql.close();
            rs.close();
            stmSql=null;
            rs=null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsp;
    }// fin traertraerDocOri
    
    
    
    /**
     * <h1>ACTUALIZACION DEL ULTIMO DOCUMENTO EN LA TABLA TBM_CABTIPDOC</h1>
     * <P>actualización de ne_ultdoc de la tabla tbm_cabtipdoc de acuerdo a la empresa, local y tipo de documento</P>
     */
    private void actualizarCabTipDoc(int intNumGuiFin,int intCodEmpLoc,int intCodLocLoc,int intCodTipDocGuiNue,Connection con){
        Statement stmSql=null;
        String strQuery="";
        try {
            stmSql=con.createStatement();
            strQuery=" UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumGuiFin+" WHERE co_emp="+intCodEmpLoc+" AND co_loc="+intCodLocLoc+" and co_tipdoc="+intCodTipDocGuiNue+" ";
            stmSql.executeUpdate(strQuery);
            stmSql.close();
            stmSql=null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }

    
    
    /**
     * <h1>INGRESO DE LA CABECERA DE LA GUIA DE REMISION</h1>
     * <P>Función para el registro de la cabecera de la guía de remisión</P>
     */
        private boolean insertarCabGuiaRemFin(int intCodEmpGui, int intCodLocGui, int intCodTipDocGui, int intCodTipDocGuiNue, int intCodDocGui, int intCodDocGuiNue, int intNumGuiFin, int intCodEmpConf, int intCodLocConf, int intCodTipDocConf, int intCodDocConf, int intCodVeh, int intCodCho, String strIdeTra, String strNomTra, String strPlaVehTra, boolean blnObtDatGu,int intCoUsrIng,Connection con) {
        boolean rsp=false;
        ResultSet rs1;
        ResultSet rs=null;
        String strQuery="";
        Statement stmSql=null;
        try {
            
            if (blnObtDatGu) {
                strQuery="SELECT * FROM tbm_cabguirem WHERE co_emp="+intCodEmpGui+" and co_loc="+intCodLocGui+" and co_tipdoc="+intCodTipDocGui+" and co_doc="+intCodDocGui+"  ";
                System.out.println("consulta para peso de guia antes del else: "+strQuery);
            }else{
                strQuery=" select a6.tx_ptopar, a6.nd_pestotkgr, a6.tx_datdocoriguirem, a6.co_ptopar, a6.ne_numorddes, a6.co_forRet, a6.tx_vehRet, a6.tx_choRet "
                + " ,a6.co_ven, a6.tx_nomven, a6.tx_numped, a6.co_ptodes "
                + " ,null as co_clides, a5.tx_nomcli as tx_nomclides, a5.tx_dircli as tx_dirclides, a5.tx_telcli as tx_telclides, a5.tx_ruc as tx_rucclides, a5.tx_ciucli as tx_ciuclides "
                + " from tbm_cabguirem as a6  "
                + " inner join tbm_detguirem as a on (a.co_emp=a6.co_emp and a.co_loc=a6.co_loc and a.co_tipdoc=a6.co_tipdoc and a.co_doc=a6.co_doc )  "
                + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel )  "
                + " inner join tbr_detmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc and a2.co_reg=a1.co_reg )  "
                + " inner join tbm_detmovinv as a3 on (a3.co_emp=a2.co_emprel and a3.co_loc=a2.co_locrel and a3.co_tipdoc=a2.co_tipdocrel and a3.co_doc=a2.co_docrel and a3.co_reg=a2.co_regrel )  "
                + " inner join tbr_detmovinv as a4 on (a4.co_emprel=a3.co_emp and a4.co_locrel=a3.co_loc and a4.co_tipdocrel=a3.co_tipdoc and a4.co_docrel=a3.co_doc and a4.co_regrel=a3.co_reg and a4.co_emp=a3.co_emp )  "
                + " inner join tbm_cabmovinv as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_loc and a5.co_tipdoc=a4.co_tipdoc and a5.co_doc=a4.co_doc  )  "
                + " where a6.co_emp="+intCodEmpGui+" and a6.co_loc="+intCodLocGui+" and a6.co_tipdoc="+intCodTipDocGui+" and a6.co_doc="+intCodDocGui+" ORDER BY tx_nomCliDes ASC ";
            System.out.println("consulta para peso de guia despues del else: "+strQuery);    
            }
            stmSql=con.createStatement();
            rs=stmSql.executeQuery(strQuery);
            System.out.println("consulta para peso de guia despues del resultset: "+strQuery);
            if (rs.next()) {
                strQuery=" INSERT INTO tbm_cabguirem (co_emp, co_loc, co_tipdoc, co_doc, fe_doc, fe_initra, fe_tertra, " +
                " ne_numdoc, tx_ptopar, co_clides, tx_rucclides, tx_nomclides, tx_dirclides, tx_telclides,  " +
                " tx_ciuclides, nd_pestotkgr, st_imp, tx_obs1, tx_obs2, st_reg, fe_ing, " +
                " co_usring, tx_coming, tx_comultmod, co_ptopar, st_tipGuiRem, tx_datdocoriguirem " +
                " ,co_forRet,tx_vehRet,tx_choRet, co_ven, tx_nomven, tx_numped, co_ptodes, st_coninv, ne_numorddes, co_veh, co_cho, tx_idetra, tx_nomtra, tx_plavehtra, tx_numserdocori  ) " +
                " VALUES ( "+intCodEmpGui+", "+intCodLocGui+", "+intCodTipDocGuiNue+", "+intCodDocGuiNue+",  current_date, current_date, current_date + 3, " +
                " "+intNumGuiFin+", '"+rs.getString("tx_ptopar")+"', "+rs.getString("co_clides")+", '"+rs.getString("tx_rucclides")+"', '"+rs.getString("tx_nomclides")+"', " +
                " '"+rs.getString("tx_dirclides")+"', '"+rs.getString("tx_telclides")+"', '"+rs.getString("tx_ciuclides")+"', " +
                " "+rs.getString("nd_pestotkgr")+", 'N', '', '"+rs.getString("tx_datdocoriguirem")+"','A', current_timestamp,"+intCoUsrIng+", " +
                " '', '', "+rs.getString("co_ptopar")+", 'S', 'O.D-"+rs.getString("ne_numorddes")+"' " +
                " ,"+rs.getString("co_forRet")+", '"+rs.getString("tx_vehRet")+"', '"+rs.getString("tx_choRet")+"', "
                + " "+rs.getString("co_ven")+" ,'"+rs.getString("tx_nomven")+"', '"+rs.getString("tx_numped")+"' "
                + " ,"+rs.getString("co_ptodes")+", 'C', 0,"+(intCodVeh==0?null:intCodVeh)+", "+(intCodCho==0?null:intCodCho)+", '"+strIdeTra+"', '"+strNomTra+"', '"+strPlaVehTra+"' "
                + ", '" + strSecDocOri + "')";           

           
                strQuery+="; INSERT INTO tbr_cabguirem (co_emp, co_loc, co_tipdoc, co_doc, co_locrel, co_tipdocrel, co_docrel, st_regrep)" +
                " VALUES( "+intCodEmpGui+", "+intCodLocGui+", "+intCodTipDocGuiNue+", "+intCodDocGuiNue+", "+intCodLocGui+"" +
                " ,"+intCodTipDocGui+", "+intCodDocGui+", 'I' ) ";

                strQuery+="; UPDATE tbm_cabguirem SET st_tieGuiSec='S' WHERE co_emp="+intCodEmpGui+" AND co_loc="+intCodLocGui+" " +
                " AND co_tipdoc="+intCodTipDocGui+" AND  co_doc="+intCodDocGui+" ";

                strQuery+="; UPDATE tbm_cabingegrmerbod SET co_locrelguirem="+intCodLocGui+", co_tipdocrelguirem="+intCodTipDocGuiNue+", co_docrelguirem="+intCodDocGuiNue+" "
                + " WHERE co_emp="+intCodEmpConf+" AND co_loc="+intCodLocConf+" " +
                " AND co_tipdoc="+intCodTipDocConf+" AND  co_doc="+intCodDocConf+" ";
            }
            stmSql.executeUpdate(strQuery);
            stmSql.close();
            rs.close();
            stmSql=null;
            rs=null;
            //rs1.close();
            rsp=true;
        } catch (Exception e) {
            e.printStackTrace();
			rsp=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return rsp;
    }//fin de insertarCabGuiaRemFin
    
    
    
    
    /**
     * <h1>INGRESO DEL DETALLE DE LA GUIA DE REMISION</h1>
     * <p>Función para el registro del detalle de la guía de remisión</p>
     */

    private boolean insertarDetGuiaRemFin(int intCodEmpConf, int intCodLocConf, int intCodTipDocConf, int intCodDocConf, int intCodTipDocGuiNue, int intCodDocGuiNue,Connection con) {
        boolean rsp=false;
        Statement stmSql=null;
        String strQuery="";
        ResultSet rs=null;
            try {
                objUti=new ZafUtil();
                stmSql=con.createStatement();
                strQuery="";
                strQuery="SELECT * from ( "
                + " SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a.co_reg as coregconf, "
                + " a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a1.nd_pestotkgr, abs(a.nd_can) as canegr, "
                + " a1.st_meregrfisbod, abs(COALESCE(a.nd_cannunrec,0)) as cannunrec, COALESCE((abs(a.nd_can)+abs(a.nd_cannunrec)),0) as cantotconf "
                + " ,case when a1.st_meregrfisbod = 'S' then  abs(a.nd_can)  else   (abs(a1.nd_cantotguisec)-abs(a1.nd_cannunrec))  end as canconf  "
                + " ,a1.tx_obs1  "
                + " from tbm_detingegrmerbod as a "
                + " INNER JOIN tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelguirem and a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem and a1.co_reg=a.co_regrelguirem  ) "
                + " WHERE a.co_emp="+intCodEmpConf+" and a.co_loc="+intCodLocConf+" and a.co_tipdoc="+intCodTipDocConf+" and a.co_doc= "+intCodDocConf+" "
                + " ) as x WHERE  canconf > 0  ORDER BY co_reg ";
                
                rs=stmSql.executeQuery(strQuery);
                strQuery="";
                while (rs.next()) {                    
                       strQuery+=" INSERT INTO tbm_detguirem(co_emp, co_loc, co_tipdoc, co_doc, co_reg,  " +
                        "  co_itm, tx_codalt, tx_nomitm, tx_unimed, nd_can, st_regrep, nd_pestotkgr, tx_obs1, st_meregrfisbod, nd_cannunrec, nd_cancon ) " +
                        " VALUES ("+rs.getInt("co_emp")+", "+rs.getInt("co_loc")+", "+intCodTipDocGuiNue+", "+intCodDocGuiNue+", "+rs.getInt("coregconf")+", "+
                        " "+rs.getString("co_itm")+", '"+rs.getString("tx_codalt")+"', " +
                        " "+objUti.codificar(rs.getString("tx_nomitm"))+" ,'"+rs.getString("tx_unimed")+"', " +
                        " "+rs.getString("canconf")+" ,'I' , "+rs.getString("nd_pestotkgr")+", '"+rs.getString("tx_obs1")+"' " +
                        " ,'"+rs.getString("st_meregrfisbod")+"', "+rs.getString("cannunrec")+", "+rs.getString("canegr")+" )";

                        strQuery+="; INSERT INTO tbr_detguirem (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel, co_docrel, co_regrel, st_regrep)" +
                        " VALUES( "+rs.getInt("co_emp")+", "+rs.getInt("co_loc")+", "+intCodTipDocGuiNue+", "+intCodDocGuiNue+", "+rs.getInt("coregconf")+", "+rs.getInt("co_loc")+"" +
                        " ,"+rs.getInt("co_tipdoc")+", "+rs.getInt("co_doc")+", "+rs.getInt("co_reg")+", 'I' ) ";

                        if(rs.getString("st_meregrfisbod").equals("S")){
                         strQuery+="; UPDATE tbm_detguirem SET " +
                         " nd_cantotguisec=nd_cantotguisec+"+rs.getString("cantotconf")+" " +
                         " WHERE co_emp="+rs.getInt("co_emp")+" AND co_loc="+rs.getInt("co_loc")+"  AND  co_tipdoc="+rs.getInt("co_tipdoc")+"" +
                         "  AND  co_doc="+rs.getInt("co_doc")+"  AND  co_reg="+rs.getInt("co_reg")+" ";
                        }

                        strQuery+="; UPDATE tbm_detingegrmerbod SET co_locrelguirem="+rs.getInt("co_loc")+", co_tipdocrelguirem="+intCodTipDocGuiNue+", co_docrelguirem="+intCodDocGuiNue+" "
                        + " ,co_regrelguirem="+rs.getInt("coregconf")+"  WHERE co_emp="+intCodEmpConf+" AND co_loc="+intCodLocConf+" " +
                        " AND co_tipdoc="+intCodTipDocConf+" AND  co_doc="+intCodDocConf+" and co_reg="+rs.getInt("coregconf")+" ; ";
                }
                stmSql.executeUpdate(strQuery);
                stmSql.close();
                rs.close();
                rsp=true;
            } catch (Exception e) {
                e.printStackTrace();
				rsp=false;
                objUti.mostrarMsgErr_F1(null, e);
            }
        
        
        return rsp;
    }//FIN DE DETALLE
    
    /**
     * <h1>ENVIO DE PULSO PARA ACTIVAR EL SERVICIO 11</h1>
     * <p>Esta función envía por streamind un entero para activar el servicio 11 y enviar al SRI la guía 
     * generada y pueda ser autorizada por contingencia</p>
     */
    private void enviarPulsoFacturacionElectronica() {
        objPulFacEle = new ZafPulFacEle();
        objPulFacEle.iniciar();
        //objPulFacEle.iniciar();
        
        System.out.println(" PULSO::::::  enviarPulsoFacturacionElectronica  ");
    }
    
	public void enviarPulsoGuiaElectronica() {
			objPulFacEle = new ZafPulFacEle();
			objPulFacEle.iniciarConGui(6012);
			//objPulFacEle.iniciar();
			
			System.out.println(" PULSO::::::  enviarPulsoGuiaElectronica  ");
		}	

    /**
     * ASIGNAR EL COSTO TOTAL A LAS GUÍAS DE REMISIÓN QUE NO TIENEN PESO (DETALLE)
     */
    public void asignarCosDet(Connection con) throws SQLException{
        
        List<ZafPesDetGuiRem>detalle;
        detalle=CargaDatPesDetGuiRem(con);
        
        try {
            //oc.ocon.setAutoCommit(false);
            con.setAutoCommit(false);
            stmSql=con.createStatement();
            for (int i = 0; i < detalle.size(); i++) {
                strQuery="update tbm_detguirem set nd_pesTotKgr="+detalle.get(i).getNd_peso()+" where co_emp="+detalle.get(i).getCo_emp()+" "
                        + " and co_loc="+detalle.get(i).getCo_loc()+" and co_tipdoc="+detalle.get(i).getCo_tipdoc()+" and co_doc="+detalle.get(i).getCo_doc()+" "
                        + " and co_reg="+detalle.get(i).getCo_reg();
                stmSql.executeUpdate(strQuery);
                con.commit();
            }
            
            stmSql.close();
        } catch (Exception e) {
            e.printStackTrace();
            con.rollback();
        }
        
        
//        try {
//            stmSql=oc.ocon.createStatement();
//            strQuery="UPDATE tbm_detGuiRem\n" +
//            "SET nd_pesTotKgr=b1.nd_pesTotKgr\n" +
//            "FROM\n" +
//            "(\n" +
//            "SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg\n" +
//            ", (CASE WHEN a2.nd_can*a3.nd_pesItmKgr IS NULL THEN 0.00 ELSE a2.nd_can*a3.nd_pesItmKgr END) AS nd_pesTotKgr\n" +
//            "FROM tbm_cabGuiRem AS a1\n" +
//            "INNER JOIN tbm_detGuiRem AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)\n" +
//            "INNER JOIN tbm_inv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)\n" +
//            "WHERE a1.fe_doc>='2015/01/01' AND a1.ne_numOrdDes=0 AND a2.nd_pesTotKgr=0 AND a3.nd_pesItmKgr>0\n" +
//            "ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg\n" +
//            ") AS b1\n" +
//            "WHERE tbm_detGuiRem.co_emp=b1.co_emp AND tbm_detGuiRem.co_loc=b1.co_loc AND tbm_detGuiRem.co_tipDoc=b1.co_tipDoc AND tbm_detGuiRem.co_doc=b1.co_doc AND tbm_detGuiRem.co_reg=b1.co_reg;";
//            stmSql.executeUpdate(strQuery);
//            System.out.println("consulta de peso de detalle"+strQuery);
//            stmSql.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }//fin de asignar
    
    /**
     * ASIGNAR EL COSTO TOTAL A LAS GUÍAS DE REMISIÓN QUE NO TIENEN PESO (CABECERA).
     */
    public void asignarCostCab(Connection con) throws SQLException{
        List<ZafPesCabGuiRem>cab;
        cab=CargaDatPesCabtGuiRem(con);
        try {
            con.setAutoCommit(false);
            stmSql=con.createStatement();
            for (int i = 0; i < cab.size(); i++) {
                strQuery="update tbm_cabguirem set nd_pesTotKgr="+cab.get(i).getNd_peso()+" where co_emp="+cab.get(i).getCo_emp()+""
                        + " and co_loc="+cab.get(i).getCo_loc()+" and co_tipdoc="+cab.get(i).getCo_tipdoc()+" and co_doc="+cab.get(i).getCo_doc();
                stmSql.executeUpdate(strQuery);
                con.commit();
            }
            
            stmSql.close();
        } catch (Exception e) {
            e.printStackTrace();
            con.rollback();
        }
        
        
//        try {
//            stmSql=oc.ocon.createStatement();
//            strQuery="UPDATE tbm_cabGuiRem\n" +
//                "SET nd_pesTotKgr=b1.nd_pesTotKgr\n" +
//                "FROM\n" +
//                "(\n" +
//                "SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, SUM(a2.nd_pesTotKgr) AS nd_pesTotKgr\n" +
//                "FROM tbm_cabGuiRem AS a1\n" +
//                "INNER JOIN tbm_detGuiRem AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)\n" +
//                "WHERE a1.fe_doc>='2015/01/01' AND a1.ne_numOrdDes=0 AND a1.nd_pesTotKgr=0\n" +
//                "GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc\n" +
//                "HAVING SUM(a2.nd_pesTotKgr)>0\n" +
//                "ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc\n" +
//                ") AS b1\n" +
//                "WHERE tbm_cabGuiRem.co_emp=b1.co_emp AND tbm_cabGuiRem.co_loc=b1.co_loc AND tbm_cabGuiRem.co_tipDoc=b1.co_tipDoc AND tbm_cabGuiRem.co_doc=b1.co_doc;";
//            stmSql.executeUpdate(strQuery);
//            System.out.println("consulta de peso de cabecera\n"+strQuery);
//            stmSql.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
    }//fin de asignar
    
    
    private List<ZafPesDetGuiRem> CargaDatPesDetGuiRem(Connection con){
        List<ZafPesDetGuiRem>detalle=new ArrayList<ZafPesDetGuiRem>();
        ZafPesDetGuiRem op;
        
        try {
            stmSql=con.createStatement();
            strQuery="SELECT\n" +
"			a1.co_emp,\n" +
"			a1.co_loc,\n" +
"			a1.co_tipDoc,\n" +
"			a1.co_doc,\n" +
"			a2.co_reg,\n" +
"			(\n" +
"				CASE\n" +
"				WHEN a2.nd_can * a3.nd_pesItmKgr IS NULL THEN\n" +
"					0.00\n" +
"				ELSE\n" +
"					a2.nd_can * a3.nd_pesItmKgr\n" +
"				END\n" +
"			) AS nd_pesTotKgr\n" +
"		FROM\n" +
"			tbm_cabGuiRem AS a1\n" +
"		INNER JOIN tbm_detGuiRem AS a2 ON (\n" +
"			a1.co_emp = a2.co_emp\n" +
"			AND a1.co_loc = a2.co_loc\n" +
"			AND a1.co_tipDoc = a2.co_tipDoc\n" +
"			AND a1.co_doc = a2.co_doc\n" +
"		)\n" +
"		INNER JOIN tbm_inv AS a3 ON (\n" +
"			a2.co_emp = a3.co_emp\n" +
"			AND a2.co_itm = a3.co_itm\n" +
"		)\n" +
"		WHERE\n" +
"			a1.fe_doc >= '2015/01/01'\n" +
"		AND a1.ne_numOrdDes = 0\n" +
"		AND a2.nd_pesTotKgr = 0\n" +
"		AND a3.nd_pesItmKgr > 0\n" +
"		ORDER BY\n" +
"			a1.co_emp,\n" +
"			a1.co_loc,\n" +
"			a1.co_tipDoc,\n" +
"			a1.co_doc,\n" +
"			a2.co_reg";
            rs=stmSql.executeQuery(strQuery);
            while (rs.next()) {
                op=new ZafPesDetGuiRem();
                op.setCo_emp(rs.getInt("co_emp"));
                op.setCo_loc(rs.getInt("co_loc"));
                op.setCo_tipdoc(rs.getInt("co_tipdoc"));
                op.setCo_doc(rs.getInt("co_doc"));
                op.setCo_reg(rs.getInt("co_reg"));
                op.setNd_peso(rs.getDouble("nd_pesTotKgr"));
                detalle.add(op);
            }
            stmSql.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return detalle;
    }//fin de carga datos
    
    private List<ZafPesCabGuiRem>CargaDatPesCabtGuiRem(Connection con){
        List<ZafPesCabGuiRem>cabecera=new ArrayList<ZafPesCabGuiRem>();
        ZafPesCabGuiRem op;
        
        try {
            stmSql=con.createStatement();
            strQuery="SELECT\n" +
"			a1.co_emp,\n" +
"			a1.co_loc,\n" +
"			a1.co_tipDoc,\n" +
"			a1.co_doc,\n" +
"			SUM (a2.nd_pesTotKgr) AS nd_pesTotKgr\n" +
"		FROM\n" +
"			tbm_cabGuiRem AS a1\n" +
"		INNER JOIN tbm_detGuiRem AS a2 ON (\n" +
"			a1.co_emp = a2.co_emp\n" +
"			AND a1.co_loc = a2.co_loc\n" +
"			AND a1.co_tipDoc = a2.co_tipDoc\n" +
"			AND a1.co_doc = a2.co_doc\n" +
"		)\n" +
"		WHERE\n" +
"			a1.fe_doc >= '2015/01/01'\n" +
"		AND a1.ne_numOrdDes = 0\n" +
"		AND a1.nd_pesTotKgr = 0\n" +
"		GROUP BY\n" +
"			a1.co_emp,\n" +
"			a1.co_loc,\n" +
"			a1.co_tipDoc,\n" +
"			a1.co_doc\n" +
"		HAVING\n" +
"			SUM (a2.nd_pesTotKgr) > 0\n" +
"		ORDER BY\n" +
"			a1.co_emp,\n" +
"			a1.co_loc,\n" +
"			a1.co_tipDoc,\n" +
"			a1.co_doc";
            rs=stmSql.executeQuery(strQuery);
            while (rs.next()) {
                op=new ZafPesCabGuiRem();
                op.setCo_emp(rs.getInt("co_emp"));
                op.setCo_loc(rs.getInt("co_loc"));
                op.setCo_tipdoc(rs.getInt("co_tipdoc"));
                op.setCo_doc(rs.getInt("co_doc"));
                op.setNd_peso(rs.getDouble("nd_pesTotKgr"));
                cabecera.add(op);
            }
            stmSql.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return cabecera;
    }
    
    
    public int getIntCodBodGrp() {
        return intCodBodGrp;
    }

    public void setIntCodBodGrp(int intCodBodGrp) {
        this.intCodBodGrp = intCodBodGrp;
    }

    public int getNumOrdDes() {
        return numOrdDes;
    }

    public void setNumOrdDes(int numOrdDes) {
        this.numOrdDes = numOrdDes;
    }

    
    public void enviarPulsoImpresionGui(String strDat, int intPuerto) {
        objPulFacEle = new ZafPulFacEle();
        objPulFacEle.iniciarImpresion(strDat, intPuerto);        //objPulFacEle.iniciar();        
        System.out.println(" PULSO::::::  enviarPulsoImpresionGui  ");
    }    
	
    public ZafImp getOi() {
        return oi;
    }

    public void setOi(ZafImp oi) {
        this.oi = oi;
    }
	
    
}
