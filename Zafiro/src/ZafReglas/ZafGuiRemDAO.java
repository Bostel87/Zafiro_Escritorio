/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas;

import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase que obtiene los datos relacionados con las OD y las GUIREM
 * existen metodos de acceso para generar OD Y GUIAS REMISION.
 * @author cmateo
 */
public class ZafGuiRemDAO {
    
    public String strOD="";
    private ZafUtil objUti=new ZafUtil();
    
	private String strVersion="v0.3";
    
    /**Metodo que obtiene las ordenes de despacho que se encuentran confirmadas.
     * utilizadas para terminales L.
     * @param conn Conexion de acceso a datos.
     * @param intCodEmp Codigo de la empresa.
     * @param intCodLoc Codigo del local.
     * @param intCodTipDoc codigo del tipo de documento.
     * @param intCodDoc codigo del documento.
     * @param intCodBodGrp codigo de la bodega del grupo.
     * @return 
     */
    public ResultSet obtenerDatosOrdenDespacho(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBodGrp){
        
        ResultSet rsRet=null;
        Statement stDatDes=null;
        try{
            
            stDatDes=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
            String strSql=" SELECT "
                + " a1.co_emp as coempgui, a1.co_loc as colocgui, a1.co_tipdoc as cotipdocgui, a1.co_doc as codocgui "
                + " ,a1.ne_numorddes, a.tx_tipcon, a.co_emp,a.co_loc, a1.tx_ptopar"
                + " ,a1.co_clides, a1.tx_rucclides, a1.tx_nomclides"
                + " , a1.tx_dirclides, a1.tx_telclides, a1.tx_ciuclides"
                + " , a1.nd_pestotkgr, a1.tx_datdocoriguirem,a1.co_ptopar "
                + " , a1.co_forRet,a1.tx_vehRet,a1.tx_choRet"
                + " , a1.co_ven, a1.tx_nomven, a1.tx_numped, a1.co_ptodes"
                + " , a.co_tipdoc,a.co_doc, a.co_veh, a.co_cho"
                + " , a.tx_idetra, a.tx_nomtra, a.tx_plavehtra, a7.co_bod, a7.tx_nom "
                + " FROM tbr_bodEmpBodGrp as a6  "
                + " inner join tbm_bod as a7 on (a7.co_emp=a6.co_emp and a7.co_bod=a6.co_bod  ) "
                + " inner join tbm_cabguirem as a1 on ( a1.co_emp=a7.co_emp and a1.co_ptopar=a7.co_bod ) "
                + " inner join tbm_cabingegrmerbod as a on ( a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelguirem and a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem ) "
                + " WHERE    "
                + " ( a6.co_empGrp=0 AND a6.co_bodGrp =  "+intCodBodGrp+"  )  AND  "
                + " a1.ne_numdoc=0 and a1.st_reg = 'A' /*AND a1.st_imporddes='S'*/ and a.st_reg='A' and a.tx_tipCon in ('P','T') "
                + " and  a.co_mnu = 2205 "
                + " and a1.co_emp="+intCodEmp+" and a1.co_loc="+intCodLoc+" and a1.co_tipdoc="+intCodTipDoc+" and a1.co_doc="+intCodDoc;
            rsRet=stDatDes.executeQuery(strSql);
            return rsRet;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }finally{
            try{
                //stDatDes.close();
                //stDatDes=null;
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Metodo que permite obtener los Datos de los Detalles de las guias de Remision cliente No Retira.
     * @param intCodEmpConf codigo de la empresa de la confirmacion egreso.
     * @param intCodLocConf codigo del local de la confirmacion egreso.
     * @param intCodTipDocConf codigo del tipo de documento de confirmacion egreso.
     * @param intCodDocConf codigo del documento de confirmacion egreso.
     * @return ResultSet con los detalles de items de la confirmacion de egreso.
     */
    public ResultSet obtenerDatDetGuiRem(Connection conn, int intCodEmpConf, int intCodLocConf, int intCodTipDocConf, int intCodDocConf){
        
        //ZafCon objZafCon=new ZafCon();
        Statement stDetGuiRem;
        ResultSet rsDetGuiRem=null;
        String strSql="SELECT * from ( "
        + " SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a.co_reg as coregconf, "
        + " a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a1.nd_pestotkgr, abs(a.nd_can) as canegr, "
        + " a1.st_meregrfisbod, abs(COALESCE(a.nd_cannunrec,0)) as cannunrec, COALESCE((abs(a.nd_can)+abs(a.nd_cannunrec)),0) as cantotconf "
        + " ,case when a1.st_meregrfisbod = 'S' then  abs(a.nd_can)  else   (abs(a1.nd_cantotguisec)-abs(a1.nd_cannunrec))  end as canconf  "
        + " ,a1.tx_obs1, a.co_emp as co_empconf, a.co_loc as co_locconf, a.co_tipdoc as co_tipdocconf, a.co_doc as co_docconf"
        + " from tbm_detingegrmerbod as a "
        + " INNER JOIN tbm_detguirem as a1 on "
        + "(a1.co_emp=a.co_emp "
        + " and a1.co_loc=a.co_locrelguirem "
        + " and a1.co_tipdoc=a.co_tipdocrelguirem "
        + " and a1.co_doc=a.co_docrelguirem "
        + " and a1.co_reg=a.co_regrelguirem  ) "
        + " WHERE a.co_emp="+intCodEmpConf
        + " and a.co_loc="+intCodLocConf
        + " and a.co_tipdoc="+intCodTipDocConf
        + " and a.co_doc= "+intCodDocConf+" "
        + " ) as x WHERE  canconf > 0  ORDER BY co_reg ";
        try{
            stDetGuiRem=conn.createStatement();
            rsDetGuiRem= stDetGuiRem.executeQuery(strSql);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return rsDetGuiRem;
    }
    
    /**
     * Metodo para obtener el sigte documento en CABGUIREM
     * @param conn conexion acceso a datos.
     * @param intCodEmpGui codigo de empresa guia u orden despacho.
     * @param intCodLocGui codigo de local guia u orden despacho.
     * @param intCodTipDocGuiNue codigo de tipo de documento
     * @return int con el nuevo codigo.
     */
    public int obtenerSec(Connection conn,int intCodEmpGui, int intCodLocGui, int intCodTipDocGuiNue){
        int intCodDocGuiNue=0;
        ResultSet rstCen=null;
        Statement stmCen=null;
        try{
            String strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabguirem WHERE " +
                            " co_emp="+intCodEmpGui+" AND co_loc="+intCodLocGui+" AND co_tipDoc="+intCodTipDocGuiNue;
             stmCen=conn.createStatement();
            rstCen = stmCen.executeQuery(strSql);
            if(rstCen.next()){
               intCodDocGuiNue = rstCen.getInt("co_doc");    
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            try{
                rstCen.close();
                rstCen=null;
                stmCen.close();
                stmCen=null;
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return intCodDocGuiNue;
    }
    
    public int obtenerNumGuiaFin (Connection conn,int intCodEmpGuia,int intCodLocGuia,int intCodTipDoc){
    
        ResultSet rstNumGui=null;
        Statement stNumGui=null;
        int intNumDocGuia=0;
        String strSql="SELECT CASE WHEN ne_ultdoc is null THEN 1 else ne_ultdoc+1 END as numguia FROM tbm_cabtipdoc WHERE co_emp="+intCodEmpGuia+"   "+
                      " AND co_loc="+intCodLocGuia+" AND co_tipdoc= "+intCodTipDoc+"  ";
        try{
            stNumGui= conn.createStatement();
            rstNumGui= stNumGui.executeQuery(strSql);
            
            if(rstNumGui.next())
                intNumDocGuia = rstNumGui.getInt("numguia");
            rstNumGui.close();
            rstNumGui=null;
            stNumGui.close();
            stNumGui=null;            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return intNumDocGuia;
    }
    
    
    /**
     * Metodo para actualizar el tipo de documento.
     * @param conn conexion Acceso a Datos.
     * @param intNumGuiFin numero guia fin.
     * @param intCodEmpLoc codigo de empresa.
     * @param intCodLocLoc codigo de local.
     * @param intCodTipDocGuiNue
     * @return 
     */
    public boolean actualizarTipDoc(Connection conn, int intNumGuiFin, int intCodEmpLoc, int intCodLocLoc, int intCodTipDocGuiNue){
        boolean booRet=false;
        String strSql=" UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumGuiFin+
                      " WHERE co_emp="+intCodEmpLoc+" AND co_loc="+intCodLocLoc+
                      " and co_tipdoc="+intCodTipDocGuiNue+" ";    
        Statement st=null;
        try{
            st=conn.createStatement();
            st.execute(strSql);
            booRet=true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return booRet;
    }
    

    public boolean actualizarTipDocLoc(Connection conn, int intNumOrdDes, int intCodEmpLoc, int intCodLocLoc){
        boolean booRet=false;
        String strSql=" UPDATE tbm_loc SET ne_ultnumorddes="+intNumOrdDes+
                      " WHERE co_emp="+intCodEmpLoc+" AND co_loc="+intCodLocLoc;    
        Statement st=null;
        try{
            st=conn.createStatement();
            st.execute(strSql);
            booRet=true;
        }catch(Exception ex){
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(null, ex);
        }
        return booRet;
    }    
    
    
    
    /**
     * Obtener la Serie del local.
     * @param intCodEmp codigo de la empresa.
     * @param intCodLoc codigo del local.
     * @return String retorna la serie del local ej: 005-001.
     */
    public String obtenerSerLocDoc(Connection conn,int intCodEmp, int intCodLoc){
    
        ResultSet rsSerLocDoc=null;
        Statement stSerLocDoc=null;
        String strSerDoc="";
        String strSql="";
        strSql+=" SELECT co_emp, co_loc, tx_nom, tx_secdoc";
        strSql+=" FROM tbm_loc";
        strSql+=" WHERE co_emp=" + intCodEmp + " AND co_loc=" + intCodLoc + "";
        strSql+=" AND st_reg IN('A','P')";
        try{
            stSerLocDoc=conn.createStatement();
            rsSerLocDoc=stSerLocDoc.executeQuery(strSql);
            if(rsSerLocDoc.next()){
                strSerDoc=rsSerLocDoc.getString("tx_secdoc");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                stSerLocDoc.close();
                rsSerLocDoc.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return strSerDoc;
    }
    
    
    /**
     * Metodo obtiene el ultimo Numero de Orden Despacho.
     * @param conn Conexion acceso a Datos.
     * @param intCodEmpOD Codigo de Empresa OD.
     * @param intCodLocOD Codigo de Local OD.
     * @return int con el ultimo numero de OD.
     */
    public int obtenerNumOrdDes(java.sql.Connection conn, int intCodEmpOD, int intCodLocOD ){
     int intNumDocGuiaDes=0;
     java.sql.Statement stmLoc;
     java.sql.ResultSet rstLoc;
     String strSql="";
      try{
       if(conn != null ){
           stmLoc=conn.createStatement();
           strSql=" SELECT CASE WHEN ne_ultnumorddes is null THEN 1 else ne_ultnumorddes+1 END as numOrdDes "+
                  " FROM tbm_loc WHERE co_emp="+intCodEmpOD+"   "+
                  " AND co_loc="+intCodLocOD+"  ";
           rstLoc=stmLoc.executeQuery(strSql);
           if(rstLoc.next())
             intNumDocGuiaDes = rstLoc.getInt("numOrdDes");
           rstLoc.close();
           rstLoc=null;
           stmLoc.close();
           stmLoc=null;

     }}catch(java.sql.SQLException Evt){  
         System.out.println(""+Evt );   
      }catch(Exception Evt){  
          System.out.println(""+Evt );  
      }
      return  intNumDocGuiaDes;
    }    

    /**
     * Metodo que inserta en la Cabecera la Guia de Remision.
     * @param conn conexion de acceso a Datos.
     * @param rsDatConDes ResultSet con los datos de la cabecera.
     * @param intCodTipNewGui tipo documento nueva guia remision.
     * @param intGuiRemNew codigo de nueva guia remision.
     * @param intNumGuiFin numero de guia de remision.
     * @param strSecDocOri numero de serie.
     * @return boolean retorna true si es correcta la insercion, o false tiene errores.
     */
    public boolean insertarCabGuiRem(Connection conn,ResultSet rsDatConDes,int intCodTipNewGui, int intGuiRemNew, int intNumGuiFin,String strSecDocOri){
        String strSql="";
        boolean booRet=false;
        
        try{
            //if(rsDatConDes.next()){

               strSql=" INSERT INTO tbm_cabguirem (co_emp, co_loc, co_tipdoc, co_doc, fe_doc, fe_initra, fe_tertra, " +
               " ne_numdoc, tx_ptopar, co_clides, tx_rucclides, tx_nomclides, tx_dirclides, tx_telclides,  " +
               " tx_ciuclides, nd_pestotkgr, st_imp, tx_obs1, tx_obs2, st_reg, fe_ing, " +
               " co_usring, tx_coming, tx_comultmod, co_ptopar, st_tipGuiRem, tx_datdocoriguirem " +
               " ,co_forRet,tx_vehRet,tx_choRet, co_ven, tx_nomven, tx_numped, co_ptodes, st_coninv"+
               " , ne_numorddes, co_veh, co_cho, tx_idetra, tx_nomtra, tx_plavehtra ,tx_numserdocori ) " +                       
               " VALUES ( "+rsDatConDes.getInt("coempgui")+", "+rsDatConDes.getInt("colocgui")+", "+intCodTipNewGui+", "+intGuiRemNew+",  current_date, current_date, current_date + 3, " +
               " "+intNumGuiFin+", '"+rsDatConDes.getString("tx_ptopar")+"', "+rsDatConDes.getString("co_clides")+", '"+rsDatConDes.getString("tx_rucclides")+"', '"+rsDatConDes.getString("tx_nomclides")+"', " +
               " '"+rsDatConDes.getString("tx_dirclides")+"', '"+rsDatConDes.getString("tx_telclides")+"', '"+rsDatConDes.getString("tx_ciuclides")+"', " +
               " "+rsDatConDes.getString("nd_pestotkgr")+", 'N', '', '"+rsDatConDes.getString("tx_datdocoriguirem")+"','A', current_timestamp, 1, " +
               " '', '', "+rsDatConDes.getString("co_ptopar")+", 'S', 'O.D-"+rsDatConDes.getString("ne_numorddes")+"' " +
               " ,"+rsDatConDes.getString("co_forRet")+", '"+rsDatConDes.getString("tx_vehRet")+"', '"+rsDatConDes.getString("tx_choRet")+"', "
               + " "+rsDatConDes.getString("co_ven")+" ,'"+rsDatConDes.getString("tx_nomven")+"', '"+rsDatConDes.getString("tx_numped")+"' "
               + " ,"+rsDatConDes.getString("co_ptodes")+", 'C', 0,"+(rsDatConDes.getInt("co_veh") ==0?null:rsDatConDes.getInt("co_veh"))+", "+(rsDatConDes.getInt("co_cho")==0?null:rsDatConDes.getInt("co_cho"))+", '"+rsDatConDes.getString("tx_idetra")+"', '"+ rsDatConDes.getString("tx_nomtra") +"', '"+rsDatConDes.getString("tx_plavehtra")+"' "
               + ", '" + strSecDocOri + "')";   
               
              strSql+="; INSERT INTO tbr_cabguirem (co_emp, co_loc, co_tipdoc, co_doc, co_locrel, co_tipdocrel, co_docrel, st_regrep)" +
                " VALUES( "+rsDatConDes.getInt("coempgui")+", "+rsDatConDes.getInt("colocgui")+", "+intCodTipNewGui+", "+intGuiRemNew+", "+rsDatConDes.getInt("colocgui")+"" +
                " ,"+rsDatConDes.getInt("cotipdocgui") +", "+rsDatConDes.getInt("codocgui") +", 'I' ) ";
              
                strSql+="; UPDATE tbm_cabguirem SET st_tieGuiSec='S' WHERE co_emp="+rsDatConDes.getInt("coempgui")+" AND co_loc="+rsDatConDes.getInt("colocgui")+" " +
                " AND co_tipdoc="+rsDatConDes.getInt("cotipdocgui")+" AND  co_doc="+rsDatConDes.getInt("codocgui")+" ";
              
                strSql+="; UPDATE tbm_cabingegrmerbod SET co_locrelguirem="+rsDatConDes.getInt("colocgui")+", co_tipdocrelguirem="+intCodTipNewGui+", co_docrelguirem="+intGuiRemNew+" "
                + " WHERE co_emp="+rsDatConDes.getInt("co_emp")+" AND co_loc="+rsDatConDes.getInt("co_loc")+" " +
                " AND co_tipdoc="+ rsDatConDes.getInt("co_tipdoc")+" AND  co_doc="+rsDatConDes.getInt("co_doc")+" ";
               Statement stInsCab=conn.createStatement();
               stInsCab.execute(strSql);
               booRet=true;
              
          //  }
        }catch(Exception ex){
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(null, ex);
        }
         return booRet;
    }
    
    
    private String codificar(Object cadena)
    {
        if (cadena==null)
            return "Null";
        if (cadena.toString().equals(""))
            return "Null";
        return "'" + cadena.toString().replaceAll("'", "''") + "'";
    }
    
    /**
     * Metodo que inserta los detalles de la Guia Remision.
     * @param objZafCon conexion acceso a datos
     * @param rsDet Resultset que trae los detalles dela OD confirmada.
     * @param intCodNewGuiRem codigo de la nueva guia de remisión.
     * @param intTipDocNewGui codigo del tipo de documento de la guía de remision.
     * @return 
     */
    public boolean insertarDetGuiRem(Connection objZafCon, ResultSet rsDet, int intCodNewGuiRem, int intTipDocNewGui){
        boolean booRet=false;
        String strSql="";
        Statement stDetGuiRem=null;
        try{ 
            if(rsDet!=null){
                while(rsDet.next())
                {

                    strSql+=" INSERT INTO tbm_detguirem(co_emp, co_loc, co_tipdoc, co_doc, co_reg,  " +
                    "  co_itm, tx_codalt, tx_nomitm, tx_unimed, nd_can, st_regrep, nd_pestotkgr, tx_obs1, st_meregrfisbod, nd_cannunrec, nd_cancon ) " +
                    " VALUES ("+rsDet.getInt("co_emp")+", "+rsDet.getInt("co_loc")+", "+intTipDocNewGui+", "+intCodNewGuiRem+", "+rsDet.getInt("coregconf")+", "+
                    " "+rsDet.getString("co_itm")+", '"+rsDet.getString("tx_codalt")+"', " +
                    " "+codificar(rsDet.getString("tx_nomitm"))+" ,'"+rsDet.getString("tx_unimed")+"', " +
                    " "+rsDet.getString("canconf")+" ,'I' , "+rsDet.getString("nd_pestotkgr")+", '"+rsDet.getString("tx_obs1")+"' " +
                    " ,'"+rsDet.getString("st_meregrfisbod")+"', "+rsDet.getString("cannunrec")+", "+rsDet.getString("canegr")+" )";

                    strSql+="; INSERT INTO tbr_detguirem (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel, co_docrel, co_regrel, st_regrep)" +
                    " VALUES( "+rsDet.getInt("co_emp")+", "+rsDet.getInt("co_loc")+", "+intTipDocNewGui+", "+intCodNewGuiRem+", "+rsDet.getInt("coregconf")+", "+rsDet.getInt("co_loc")+"" +
                    " ,"+rsDet.getInt("co_tipdoc")+", "+rsDet.getInt("co_doc")+", "+rsDet.getInt("co_reg")+", 'I' ) ";
                    
                    
                    if(rsDet.getString("st_meregrfisbod").equals("S")){
                        strSql+="; UPDATE tbm_detguirem SET " +
                        " nd_cantotguisec=nd_cantotguisec+"+rsDet.getString("cantotconf")+" " +
                        " WHERE co_emp="+rsDet.getInt("co_emp")+" AND co_loc="+rsDet.getInt("co_loc")+"  AND  co_tipdoc="+rsDet.getInt("co_tipdoc")+"" +
                        "  AND  co_doc="+rsDet.getInt("co_doc")+"  AND  co_reg="+rsDet.getInt("co_reg")+" ";
                     }

                    strSql+="; UPDATE tbm_detingegrmerbod SET co_locrelguirem="+rsDet.getInt("co_loc")+", co_tipdocrelguirem="+intTipDocNewGui+", co_docrelguirem="+intCodNewGuiRem+" "
                    + " ,co_regrelguirem="+rsDet.getInt("coregconf")+"  WHERE co_emp="+rsDet.getInt("co_empconf") +" AND co_loc="+rsDet.getInt("co_locconf") +" " +
                    " AND co_tipdoc="+rsDet.getInt("co_tipdocconf") +" AND  co_doc="+rsDet.getInt("co_docconf") +" and co_reg="+rsDet.getInt("coregconf")+"; ";
                }
                stDetGuiRem=objZafCon.createStatement();
                stDetGuiRem.executeUpdate(strSql);
                booRet=true;
            }
        }
       catch(Exception ex){
           ex.printStackTrace();
           booRet=false;
           try{
                stDetGuiRem.close();
           }catch(Exception ex2){
               ex2.printStackTrace();
           }
           objUti.mostrarMsgErr_F1(null, ex);
       }
        return booRet;
    }
    

    /**
     * Metodo para obtener documentos que se registran cuando se hacen por ejemplo ventas entre empresas relacionadas.
     * @param conn Conexion acceso a datos.
     * @param intCodEmp codigo de la empresa.
     * @param intCodLoc codigo del local.
     * @param intCodTipDoc codigo del tipo del documento.
     * @param intCodDoc codigo del documento.
     * @param intCodGrpBod codigo del grupo de bodega.
     * @return 
     */
    public ResultSet obtenerFactTransEntEmp(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodGrpBod){
        ResultSet rsReg=null;
        Statement st=null;        
        String strQry="";
        try{
            st=conn.createStatement();
            strQry="SELECT  x.co_emp, x.co_loc, x.co_bod,  x.dirbor, a2.co_bodgrp,"+
                         "  x.co_empRelFacCli, x.co_locRelFacCli, x.co_tipDocRelFacCli,"+
                         "  x.co_docRelFacCli, x.co_empFacEmpRel, x.co_locFacEmpRel, "+
                         "  x.co_tipDocFacEmpRel, x.co_docFacEmpRel "+
                 " FROM ( select * FROM (  "+
                                            " SELECT  a.co_emp,  a.co_loc, a.co_tipdoc, "+
                                                    " a.co_doc, a.fe_doc  , a1.co_bod , "+
                                                    " a4.tx_dir as dirbor ,a.co_ptodes "+
                                                    " ,( select count(x.co_doc) "+
                                                    " from tbm_detmovinv as x "+
                                                    " where x.co_emp=a.co_emp "+
                                                    " and x.co_loc=a.co_loc "+
                                                    " and x.co_tipdoc=a.co_tipdoc "+
                                                    " and x.co_doc=a.co_doc "+
                                                    " and x.st_cliretemprel='S' ) as exiCliret"+
                                                    " , b1.co_empRel AS co_empRelFacCli,"+
                                                    " b1.co_locRel AS co_locRelFacCli, b1.co_tipDocRel AS co_tipDocRelFacCli, "+
                                                    " b1.co_docRel AS co_docRelFacCli, b1.co_regRel AS co_regRelFacCli"+
                                                    " , b1.co_emp AS co_empFacEmpRel, b1.co_loc AS co_locFacEmpRel, b1.co_tipDoc AS co_tipDocFacEmpRel,"+
                                                    " b1.co_doc AS co_docFacEmpRel, b1.co_reg AS co_regFacEmpRel "+
                                            " FROM tbm_cabmovinv AS a"+
                                            " INNER JOIN "+
                                            " (tbm_detmovinv as a1 "+
                                            "  INNER JOIN tbr_detMovInv AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_doc=b1.co_doc AND a1.co_reg=b1.co_reg)"+
                                                " ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc)"+
                                            " INNER JOIN tbr_bodEmpBodGrp AS a2 "+
                                                " ON (a2.co_emp=a.co_emp AND a2.co_bod=a1.co_bod)"+
                                            " INNER JOIN tbm_cabtipdoc AS a3 "+
                                                " ON (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc)"+
                                            " INNER JOIN tbm_bod AS a4 "+
                                                " ON (a4.co_emp=a1.co_emp and a4.co_bod=a1.co_bod) "+
                                            " WHERE   a.co_tipdoc IN ( 124,125,126,127,136,137,138,139,166,167, 224,225,226,227 )"+
                                            " AND  a.st_creguirem='N'"+
                                            " AND  a.st_reg not in('E','I')  and a1.st_meringegrfisbod='S'"+
                                            " AND ( a2.co_empGrp=0 AND a2.co_bodGrp=" + intCodGrpBod + ") "+  
                                            " GROUP BY  a.co_emp,  a.co_loc, a.co_tipdoc, "+
                                                    " a.co_doc, a.fe_doc, a1.co_bod, "+
                                                    " a3.tx_descor, a.ne_numdoc, a4.tx_dir, "+
                                                    " a.tx_numped,a.co_cli , a.tx_nomcli, "+
                                                    " a.tx_dircli, a.co_ptodes, b1.co_empRel,"+
                                                    " b1.co_locRel, b1.co_tipDocRel, b1.co_docRel,"+
                                                    " b1.co_regRel, b1.co_emp, b1.co_loc, "+
                                                    " b1.co_tipDoc, b1.co_doc, b1.co_reg"+
                                    ") as x"+
                    " where exiCliret=0  ) as x"+
                    " inner JOIN tbr_bodEmpBodGrp AS a2 "+
                    " ON (a2.co_emp=x.co_emp AND a2.co_bod=x.co_ptodes )"+
                    
                    //FILTRA SOLO EL DOCUMENTO QUE ACABAMOS DE GENERAR.(OBVIAMENTE PUEDEN HABER VARIOS DOCUMENTOS DE COMPRA VENTA)
                    " where x.co_emp="+intCodEmp+
                    " and x.co_loc="+intCodLoc+
                    " and x.co_tipdoc="+intCodTipDoc+
                    " and x.co_doc="+intCodDoc+
                    
                    " GROUP BY  x.co_emp, x.co_loc, x.co_bod,  "+
                    " x.dirbor, a2.co_bodgrp, x.co_empRelFacCli, "+
                    " x.co_locRelFacCli, x.co_tipDocRelFacCli, x.co_docRelFacCli, x.co_empFacEmpRel, x.co_locFacEmpRel, x.co_tipDocFacEmpRel, x.co_docFacEmpRel;";
            rsReg=st.executeQuery(strQry);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return rsReg;    
    }
    
    
    /**
     * Metodo que obtiene los datos para insertar en la Cabecera de la orden de despacho en el caso (CLIENTE NO RETIRA).
     * @param conn Conexion Acceso a Datos.
     * @param intCodEmp Codigo de la Empresa.
     * @param intCodLoc Codigo del Local.
     * @param intCodBodGrpo Codigo de Bodega de Grupo.
     * @param intCodBodGrpIng Codigo de bodega Grupo Ing.
     * @return ResultSet.
     */
    public ResultSet obtenerDatFacCabOrdDes(Connection conn, int intCodEmp, int intCodLoc, int intCodBodGrpo, int intCodBodGrpIng, int intCodTipDoc, int intCodDoc){
        String strQry="";
        Statement stmQuery=null;
        ResultSet rsReg=null;
        try{

          strQry="select a.* "+ 
                    " from (  "+
                            "select x.* "+
                            " from ( select * "+
                                   " from ( "+
                                            " SELECT  a.co_emp,  a.co_loc, a.co_tipdoc, "+
                                                    " a.co_doc, a.fe_doc  , a1.co_bod, "+
                                                    " ( a3.tx_descor || '-' || a.ne_numdoc ) as docrel, a4.tx_dir,"+
                                                    " a.tx_numped  ,a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes"+  
                                                    ",( select count(x.co_doc) "+
                                                    "   from tbm_detmovinv as x "+
                                                    "   where x.co_emp=a.co_emp "+
                                                    "   and x.co_loc=a.co_loc "+
                                                    "   and x.co_tipdoc=a.co_tipdoc"+ 
                                                    "   and x.co_doc=a.co_doc "+
                                                    "   and x.st_cliretemprel='S' ) as exiClireti"+
                                            " FROM tbm_cabmovinv AS a    "+
                                            " INNER JOIN tbm_detmovinv as a1 "+
                                            " on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc)"+   
                                            " INNER JOIN tbr_bodEmpBodGrp AS a2 "+
                                            " ON (a2.co_emp=a.co_emp AND a2.co_bod=a1.co_bod) "+
                                            " INNER JOIN tbm_cabtipdoc AS a3 "+
                                            " on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc)"+   
                                            " INNER JOIN tbm_bod AS a4"+
                                            " on (a4.co_emp=a1.co_emp and a4.co_bod=a1.co_bod)"+
                                            " WHERE a.co_tipdoc IN ( 124,125,126,127,136,137,138,139,166,167, 224,225,226,227 )"+    
                                            " AND  a.st_creguirem='N'"+    
                                            " AND  a.st_reg not in('E','I')"+  
                                            " AND a1.st_meringegrfisbod='S'"+   
                                            " AND ( a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrpo+")"+  
                                            " GROUP BY  a.co_emp,  a.co_loc, a.co_tipdoc,"+ 
                                                       " a.co_doc, a.fe_doc, a1.co_bod, "+
                                                       " a3.tx_descor, a.ne_numdoc, a4.tx_dir,"+
                                                       " a.tx_numped ,a.co_cli , a.tx_nomcli,"+ 
                                                       " a.tx_dircli, a.co_ptodes "+ 
                                    ") as x"+   
                                  " where exiClireti=0  ) as x "+
                            " inner JOIN tbr_bodEmpBodGrp AS a2"+
                            " ON (a2.co_emp=x.co_emp AND a2.co_bod=x.co_ptodes)"+   //Bodega donde va a llegar la mercaderia 
                            " where  ( a2.co_empGrp=0 AND a2.co_bodGrp="+intCodBodGrpIng+")"+//bodega donde ingresa la mercaderia    
                            " and x.co_emp= "+intCodEmp+
                            " and x.co_loc="+intCodLoc+
                            " and x.co_tipdoc= "+intCodTipDoc+
                            " and x.co_doc="+intCodDoc+
                            " limit 1"+  
                      " ) as x"+   
                      " INNER JOIN tbm_cabmovinv as a"+
                      " on (a.co_emp=x.co_emp and a.co_loc=x.co_loc and a.co_tipdoc=x.co_tipdoc and a.co_doc=x.co_doc)";
           stmQuery=conn.createStatement();
           rsReg=stmQuery.executeQuery(strQry);
        }catch(Exception ex){
            ex.printStackTrace();
        } 
        return rsReg;
    }
    
    
    
/**
     * Metodo que obtiene la informacion para insertar en la cabecera de la orden de despacho (CLIENTE NO RETIRA) CUENCA.
     * @param conn Conexion Acceso a Datos.
     * @param intCodEmp Codigo de la Empresa.
     * @param intCodLoc Codigo del Local.
     * @param intCodBodGrpo Codigo de Bodega de Grupo.
     * @param intCodBodGrpIng Codigo de bodega Grupo Ing.
     * @return ResultSet.
     */
    public ResultSet obtenerDatFacCabOrdDesCuenca(Connection conn, int intCodEmp, int intCodLoc, int intCodBodGrpo, int intCodBodGrpIng, int intCodEmp1, int intCodLoc1, int intCodTipDoc,int intCodDoc){
        String strQry="";
        Statement stmQuery=null;
        ResultSet rsReg=null;
        try{

          strQry="select a.* "+ 
                    " from (  "+
                            "select x.* "+
                            " from ( select * "+
                                   " from ( "+
                                            " SELECT  a.co_emp,  a.co_loc, a.co_tipdoc, "+
                                                    " a.co_doc, a.fe_doc  , a1.co_bod, "+
                                                    " ( a3.tx_descor || '-' || a.ne_numdoc ) as docrel, a4.tx_dir,"+
                                                    " a.tx_numped  ,a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes"+  
                                                    ",( select count(x.co_doc) "+
                                                    "   from tbm_detmovinv as x "+
                                                    "   where x.co_emp=a.co_emp "+
                                                    "   and x.co_loc=a.co_loc "+
                                                    "   and x.co_tipdoc=a.co_tipdoc"+ 
                                                    "   and x.co_doc=a.co_doc "+
                                                    "   and x.st_cliretemprel='S' ) as exiClireti"+
                                            " FROM tbm_cabmovinv AS a    "+
                                            " INNER JOIN tbm_detmovinv as a1 "+
                                            " on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc)"+   
                                            " INNER JOIN tbr_bodEmpBodGrp AS a2 "+
                                            " ON (a2.co_emp=a.co_emp AND a2.co_bod=a1.co_bod) "+
                                            " INNER JOIN tbm_cabtipdoc AS a3 "+
                                            " on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc)"+   
                                            " INNER JOIN tbm_bod AS a4"+
                                            " on (a4.co_emp=a1.co_emp and a4.co_bod=a1.co_bod)"+
                                            " WHERE a.co_tipdoc IN ( 124,125,126,127,136,137,138,139,166,167, 224,225,226,227 )"+    
                                            " AND  a.st_creguirem='N'"+    
                                            " AND  a.st_reg not in('E','I')"+  
                                            " AND a1.st_meringegrfisbod='S'"+   
                                            " AND ( a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrpo+")"+  
                                            " GROUP BY  a.co_emp,  a.co_loc, a.co_tipdoc,"+ 
                                                       " a.co_doc, a.fe_doc, a1.co_bod, "+
                                                       " a3.tx_descor, a.ne_numdoc, a4.tx_dir,"+
                                                       " a.tx_numped ,a.co_cli , a.tx_nomcli,"+ 
                                                       " a.tx_dircli, a.co_ptodes "+ 
                                    ") as x"+   
                                  " where exiClireti=0  ) as x "+
                            " inner JOIN tbr_bodEmpBodGrp AS a2"+
                            " ON (a2.co_emp=x.co_emp AND a2.co_bod=x.co_ptodes)"+   //Bodega donde va a llegar la mercaderia 
                            " where  ( a2.co_empGrp=0 AND a2.co_bodGrp="+intCodBodGrpIng+")"+//bodega donde ingresa la mercaderia    
                            " and x.co_emp= "+intCodEmp+
                            " and x.co_loc="+intCodLoc+
                            " and x.co_tipdoc="+intCodTipDoc+
                            " and x.co_doc="+intCodDoc+

                  
                      " ) as x"+   
                      " INNER JOIN tbm_cabmovinv as a"+
                      " on (a.co_emp=x.co_emp and a.co_loc=x.co_loc and a.co_tipdoc=x.co_tipdoc and a.co_doc=x.co_doc)";
           stmQuery=conn.createStatement();
           rsReg=stmQuery.executeQuery(strQry);
        }catch(Exception ex){
            ex.printStackTrace();
        } 
        return rsReg;
    }  
    
    
    /**
     * Metodo que obtiene los datos de la cabecera de la Orden de Despacho en el caso (CLIENTE NO RETIRA) INMACONSA.
     * @param conn Conexion de acceso.\
     * @param intCodBodGrpo codigo de bodega grupo.
     * @param intCodBodGrpIng codigo de bodega de ingreso.
     * @param intCodEmp codigo de empresa.
     * @param intCodLoc codigo de local.
     * @param intCodTipDoc codigo de tipo de documento.
     * @param intCodDoc codigo del documento.
     * @return 
     */
    public ResultSet obtenerDatFacCabOrdDesInmaconsa(Connection conn,int intCodBodGrpo, int intCodBodGrpIng,  int intCodEmp, int intCodLoc, int intCodTipDoc,int intCodDoc ){
        String strQry="";
        Statement stmQuery=null;
        ResultSet rsReg=null;
        try{

          strQry="select a.* "+ 
                    " from (  "+
                            "select x.* "+
                            " from ( select * "+
                                   " from ( "+
                                            " SELECT  a.co_emp,  a.co_loc, a.co_tipdoc, "+
                                                    " a.co_doc, a.fe_doc  , a1.co_bod, "+
                                                    " ( a3.tx_descor || '-' || a.ne_numdoc ) as docrel, a4.tx_dir,"+
                                                    " a.tx_numped  ,a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes"+  
                                                    ",( select count(x.co_doc) "+
                                                    "   from tbm_detmovinv as x "+
                                                    "   where x.co_emp=a.co_emp "+
                                                    "   and x.co_loc=a.co_loc "+
                                                    "   and x.co_tipdoc=a.co_tipdoc"+ 
                                                    "   and x.co_doc=a.co_doc "+
                                                    "   and x.st_cliretemprel='S' ) as exiClireti"+
                                            " FROM tbm_cabmovinv AS a    "+
                                            " INNER JOIN tbm_detmovinv as a1 "+
                                            " on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc)"+   
                                            " INNER JOIN tbr_bodEmpBodGrp AS a2 "+
                                            " ON (a2.co_emp=a.co_emp AND a2.co_bod=a1.co_bod) "+
                                            " INNER JOIN tbm_cabtipdoc AS a3 "+
                                            " on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc)"+   
                                            " INNER JOIN tbm_bod AS a4"+
                                            " on (a4.co_emp=a1.co_emp and a4.co_bod=a1.co_bod)"+
                                            " WHERE a.co_tipdoc IN ( 124,125,126,127,136,137,138,139,166,167, 224,225,226,227 )"+    
                                            " AND  a.st_creguirem='N'"+    
                                            " AND  a.st_reg not in('E','I')"+  
                                            " AND a1.st_meringegrfisbod='S'"+   
                                            " AND ( a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrpo+")"+  
                                            " GROUP BY  a.co_emp,  a.co_loc, a.co_tipdoc,"+ 
                                                       " a.co_doc, a.fe_doc, a1.co_bod, "+
                                                       " a3.tx_descor, a.ne_numdoc, a4.tx_dir,"+
                                                       " a.tx_numped ,a.co_cli , a.tx_nomcli,"+ 
                                                       " a.tx_dircli, a.co_ptodes "+ 
                                    ") as x"+   
                                  " where exiClireti=0  ) as x "+
                            " inner JOIN tbr_bodEmpBodGrp AS a2"+
                            " ON (a2.co_emp=x.co_emp AND a2.co_bod=x.co_ptodes)"+   //Bodega donde va a llegar la mercaderia 
                            " where  ( a2.co_empGrp=0 AND a2.co_bodGrp="+intCodBodGrpIng+")"+//bodega donde ingresa la mercaderia    
                            " and x.co_emp= "+intCodEmp+
                            " and x.co_loc="+intCodLoc+
                            " and x.co_tipdoc="+intCodTipDoc+
                            " and x.co_doc="+intCodDoc+

                  
                      " ) as x"+   
                      " INNER JOIN tbm_cabmovinv as a"+
                      " on (a.co_emp=x.co_emp and a.co_loc=x.co_loc and a.co_tipdoc=x.co_tipdoc and a.co_doc=x.co_doc)";
           stmQuery=conn.createStatement();
           rsReg=stmQuery.executeQuery(strQry);
        }catch(Exception ex){
            ex.printStackTrace();
        } 
        return rsReg;
    }    
    
    
    /**
     * Obtiene los datos de las Facturas realizadas por ventas entre companias (Detalles de Ventas).
     * @param conn conexion de acceso a datos.
     * @param intCodEmp codigo de empresa.
     * @param intCodLoc codigo de documento.
     * @param intCodBodGrp codigo de la bodega del grupo (donde se realizo la venta por falta de stock)
     * @param intCodBodGrpIng codigo de la bodega donde ingreso stock por la compra realizada
     * @return ResultSet con los datos obtenidos.
     */
    public ResultSet obtenerDatFacDetOrdDes(Connection conn, int intCodEmp, int intCodLoc, int intCodBodGrp, int intCodBodGrpIng, int intTipDoc, int intCodDoc){
        String strQry="";
        Statement stmQuery=null;
        ResultSet rsReg=null;
        try{

          strQry="select x.*  "+
                " , a.co_reg, a.co_itm, a.tx_codalt, "+
                " a.tx_nomitm, a.tx_unimed, a.nd_can, a.st_meringegrfisbod  "+
                " from (  "+
                        " select x.*   "+
                        " from ( select * "+
                               " from ( SELECT  a.co_emp,  a.co_loc, a.co_tipdoc,  "+
                                            "   a.co_doc, a.fe_doc  , a1.co_bod, "+
                                            "   ( a3.tx_descor || '-' || a.ne_numdoc ) as docrel, a4.tx_dir, a.tx_numped,    "+
                                            "   a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes,  "+
                                            "  ( select count(x.co_doc) "+
                                               " from tbm_detmovinv as x "+
                                               " where x.co_emp=a.co_emp "+
                                               " and x.co_loc=a.co_loc "+
                                               " and x.co_tipdoc=a.co_tipdoc "+
                                               " and x.co_doc=a.co_doc "+
                                               " and x.st_cliretemprel='S' ) as exiCliret "+
                                      " FROM tbm_cabmovinv AS a    "+
                                      " INNER JOIN tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc)    "+
                                      " INNER JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=a.co_emp AND a2.co_bod=a1.co_bod)    "+
                                      " INNER JOIN tbm_cabtipdoc AS a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc)    "+
                                      " INNER JOIN tbm_bod AS a4 on (a4.co_emp=a1.co_emp and a4.co_bod=a1.co_bod)    "+
                                      "  WHERE   a.co_tipdoc IN ( 124,125,126,127,136,137,138,139,166,167, 224,225,226,227 )    "+
                                      "  AND  a.st_creguirem='N'    "+
                                      "  AND  a.st_reg not in('E','I')  and a1.st_meringegrfisbod='S'    "+
                                      "  AND ( a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrp+" )    "+
                                      "  GROUP BY  a.co_emp,  a.co_loc, a.co_tipdoc, "+
                                      "  a.co_doc, a.fe_doc, a1.co_bod, "+
                                      "  a3.tx_descor, a.ne_numdoc, a4.tx_dir, a.tx_numped,"+
                                      "  a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes  "+
                                    "   ) as x   "+
                              " where exiCliret=0  ) as x  "+
                    " inner JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=x.co_emp AND a2.co_bod=x.co_ptodes )    "+
                    " where  ( a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrpIng+" )    and x.co_emp="+intCodEmp+" and x.co_loc="+intCodLoc+" AND x.co_tipdoc="+ intTipDoc +" AND x.co_doc="+ intCodDoc + " limit 7  "+
                  //and x.co_emp="+intCodEmp+" and x.co_loc="+intCodLoc+" AND x.co_tipDoc=" + intCodTipDoc + " AND x.co_doc=" + intCodDoc +
                    " ) as x   "+
            "  INNER JOIN tbm_detmovinv as a on (a.co_emp=x.co_emp and a.co_loc=x.co_loc and a.co_tipdoc=x.co_tipdoc and a.co_doc=x.co_doc)    "+
            "  where  a.nd_can < 0  ";
           stmQuery=conn.createStatement();
           rsReg=stmQuery.executeQuery(strQry);
        }catch(Exception ex){
            ex.printStackTrace();
        } 
        return rsReg;
    } 
    
    
    /**
     * Obtiene los datos de las Facturas realizadas por ventas entre companias (Detalles de Ventas).
     * @param conn conexion de acceso a datos.
     * @param intCodEmp codigo de empresa.
     * @param intCodLoc codigo de documento.
     * @param intCodBodGrp codigo de la bodega del grupo (donde se realizo la venta por falta de stock)
     * @param intCodBodGrpIng codigo de la bodega donde ingreso stock por la compra realizada
     * @return ResultSet con los datos obtenidos.
     */
    public ResultSet obtenerDatFacDetOrdDesCuenca(Connection conn, int intCodEmp, int intCodLoc, int intCodBodGrp, int intCodBodGrpIng, int intCodTipDoc,int intCodDoc ){
        String strQry="";
        Statement stmQuery=null;
        ResultSet rsReg=null;
        try{

          strQry="select x.*  "+
                " , a.co_reg, a.co_itm, a.tx_codalt, "+
                " a.tx_nomitm, a.tx_unimed, a.nd_can, a.st_meringegrfisbod  "+
                " from (  "+
                        " select x.*   "+
                        " from ( select * "+
                               " from ( SELECT  a.co_emp,  a.co_loc, a.co_tipdoc,  "+
                                            "   a.co_doc, a.fe_doc  , a1.co_bod, "+
                                            "   ( a3.tx_descor || '-' || a.ne_numdoc ) as docrel, a4.tx_dir, a.tx_numped,    "+
                                            "   a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes,  "+
                                            "  ( select count(x.co_doc) "+
                                               " from tbm_detmovinv as x "+
                                               " where x.co_emp=a.co_emp "+
                                               " and x.co_loc=a.co_loc "+
                                               " and x.co_tipdoc=a.co_tipdoc "+
                                               " and x.co_doc=a.co_doc "+
                                               " and x.st_cliretemprel='S' ) as exiCliret "+
                                      " FROM tbm_cabmovinv AS a    "+
                                      " INNER JOIN tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc)    "+
                                      " INNER JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=a.co_emp AND a2.co_bod=a1.co_bod)    "+
                                      " INNER JOIN tbm_cabtipdoc AS a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc)    "+
                                      " INNER JOIN tbm_bod AS a4 on (a4.co_emp=a1.co_emp and a4.co_bod=a1.co_bod)    "+
                                      "  WHERE   a.co_tipdoc IN ( 124,125,126,127,136,137,138,139,166,167, 224,225,226,227 )    "+
                                      "  AND  a.st_creguirem='N'    "+
                                      "  AND  a.st_reg not in('E','I')  and a1.st_meringegrfisbod='S'    "+
                                      "  AND ( a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrp+" )    "+
                                      "  GROUP BY  a.co_emp,  a.co_loc, a.co_tipdoc, "+
                                      "  a.co_doc, a.fe_doc, a1.co_bod, "+
                                      "  a3.tx_descor, a.ne_numdoc, a4.tx_dir, a.tx_numped,"+
                                      "  a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes  "+
                                    "   ) as x   "+
                              " where exiCliret=0  ) as x  "+
                    " inner JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=x.co_emp AND a2.co_bod=x.co_ptodes )    "+
                    " where  ( a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrpIng+" )    and x.co_emp="+intCodEmp+" and x.co_loc="+intCodLoc+" AND x.co_tipDoc=" + intCodTipDoc + " AND x.co_doc=" + intCodDoc +
                    " ) as x   "+
            "  INNER JOIN tbm_detmovinv as a on (a.co_emp=x.co_emp and a.co_loc=x.co_loc and a.co_tipdoc=x.co_tipdoc and a.co_doc=x.co_doc)    "+
            "  where  a.nd_can < 0  ";
           stmQuery=conn.createStatement();
           rsReg=stmQuery.executeQuery(strQry);
        }catch(Exception ex){
            ex.printStackTrace();
        } 
        return rsReg;
    }    
    
    
    
    /**
     * Metodo que obtiene los datos para los detalles de la orden de Despacho (CLIENTE NO RETIRA ) INMACONSA.
     * @param conn Conexion de acceso a Datos.
     * @param intCodEmp codigo de la empresa.
     * @param intCodLoc codigo del local.
     * @param intCodBodGrp codigo del bodega del grupo.
     * @param intCodBodGrpIng codigo de la bodega de ingreso.
     * @param intCodTipDoc codigo del tipo de documento
     * @param intCodDoc codigo del documento.
     * @return ResultSet con datos de detalles.
     */
    public ResultSet obtenerDatFacDetOrdDesInmaconsa(Connection conn, int intCodEmp, int intCodLoc, int intCodBodGrp, int intCodBodGrpIng, int intCodTipDoc,int intCodDoc ){
        String strQry="";
        Statement stmQuery=null;
        ResultSet rsReg=null;
        try{

          strQry="select x.*  "+
                " , a.co_reg, a.co_itm, a.tx_codalt, "+
                " a.tx_nomitm, a.tx_unimed, a.nd_can, a.st_meringegrfisbod  "+
                " from (  "+
                        " select x.*   "+
                        " from ( select * "+
                               " from ( SELECT  a.co_emp,  a.co_loc, a.co_tipdoc,  "+
                                            "   a.co_doc, a.fe_doc  , a1.co_bod, "+
                                            "   ( a3.tx_descor || '-' || a.ne_numdoc ) as tx_docrel, a4.tx_dir, a.tx_numped,    "+
                                            "   a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes,  "+
                                            "  ( select count(x.co_doc) "+
                                               " from tbm_detmovinv as x "+
                                               " where x.co_emp=a.co_emp "+
                                               " and x.co_loc=a.co_loc "+
                                               " and x.co_tipdoc=a.co_tipdoc "+
                                               " and x.co_doc=a.co_doc "+
                                               " and x.st_cliretemprel='S' ) as exiCliret "+
                                      " FROM tbm_cabmovinv AS a    "+
                                      " INNER JOIN tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc)    "+
                                      " INNER JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=a.co_emp AND a2.co_bod=a1.co_bod)    "+
                                      " INNER JOIN tbm_cabtipdoc AS a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc)    "+
                                      " INNER JOIN tbm_bod AS a4 on (a4.co_emp=a1.co_emp and a4.co_bod=a1.co_bod)    "+
                                      "  WHERE   a.co_tipdoc IN ( 124,125,126,127,136,137,138,139,166,167, 224,225,226,227 )    "+
                                      "  AND  a.st_creguirem='N'    "+
                                      "  AND  a.st_reg not in('E','I')  and a1.st_meringegrfisbod='S'    "+
                                      "  AND ( a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrp+" )    "+
                                      "  GROUP BY  a.co_emp,  a.co_loc, a.co_tipdoc, "+
                                      "  a.co_doc, a.fe_doc, a1.co_bod, "+
                                      "  a3.tx_descor, a.ne_numdoc, a4.tx_dir, a.tx_numped,"+
                                      "  a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes  "+
                                    "   ) as x   "+
                              " where exiCliret=0  ) as x  "+
                    " inner JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=x.co_emp AND a2.co_bod=x.co_ptodes )    "+
                    " where  ( a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrpIng+" )    and x.co_emp="+intCodEmp+" and x.co_loc="+intCodLoc+" AND x.co_tipDoc=" + intCodTipDoc + " AND x.co_doc=" + intCodDoc +
                    " ) as x   "+
            "  INNER JOIN tbm_detmovinv as a on (a.co_emp=x.co_emp and a.co_loc=x.co_loc and a.co_tipdoc=x.co_tipdoc and a.co_doc=x.co_doc)    "+
            "  where  a.nd_can < 0  ";
           stmQuery=conn.createStatement();
           rsReg=stmQuery.executeQuery(strQry);
        }catch(Exception ex){
            ex.printStackTrace();
        } 
        return rsReg;
    }    
    
    
    /**
     * Metodo para insertar la Cabecera de la orden de Despacho en el caso Cliente NO RETIRA.
     * @param conn conexion acceso a datos.
     * @param rsDatConDes ResultSet con los registros que se deben insertar en l
     * @param intCodTipOD codigo del tipo documento Orden Despacho
     * @param intODNew Codigo de la numero OD.
     * @param intNumOD Codigo del ultimo numero de OD.
     * @param intCodBod Codigo del codigo de la Bodega.
     * @param strDirBod Direccion d ela bodega
     * @return boolean indica si el metodo se ejecuto correctamente o no.
     */
    public boolean insertarCabOrdDesp(Connection conn,ResultSet rsDatConDes,int intCodTipOD, int intCodOD, int intNumOD,int intCodBod,String strDirBod){
        String strSql="";
        boolean booRet=false;
        strOD="";
        try{
            if(rsDatConDes.next()){
               strSql=" INSERT INTO tbm_cabguirem (co_emp, co_loc, co_tipdoc, co_doc, fe_doc, fe_orddes "+
                      " ,ne_numorddes, ne_numdoc, co_clides, tx_rucclides, tx_nomclides,  tx_dirclides, tx_telclides, tx_ciuclides, st_imp " +
                      " ,st_reg, fe_ing, co_usring ,st_conInv, fe_initra, fe_tertra, tx_coming, st_regrep, co_ptopar, tx_ptopar " +
                      " ,tx_datdocoriguirem, tx_numped, co_ptodes ,st_imporddes  ) " +                       
                      " VALUES ( "+rsDatConDes.getString("co_emp")+", "+rsDatConDes.getString("co_loc")+", "+intCodTipOD+", "+intCodOD+",  current_date, current_date "+   // '"+rstLoc.getString("fe_doc")+"' " +
                      " ,"+intNumOD+", 0, "+rsDatConDes.getString("co_cli")+", '"+rsDatConDes.getString("tx_ruc")+ "', '"+rsDatConDes.getString("tx_nomcli")+"', '"+rsDatConDes.getString("tx_dircli")+"' " +
                      " ,'"+rsDatConDes.getString("tx_telcli")+"', '"+rsDatConDes.getString("tx_ciucli")+ "', 'N' ,'A', current_timestamp , 1 " +
                      " ,'P', current_date, current_date, '', 'I' , "+intCodBod+", '"+strDirBod+"'  " +
                      //" , '' ,'',  "+rsDatConDes.getString("co_ptodes")+",'S' )";
                      " , '' ,'',  "+rsDatConDes.getString("co_ptodes")+",'N' )";

               Statement stInsCab=conn.createStatement();
               stInsCab.execute(strSql);
               strOD=rsDatConDes.getString("co_emp")+"-"+rsDatConDes.getString("co_loc")+"-"+intCodTipOD+"-"+intCodOD;
               booRet=true;
            }            
        }catch(Exception ex){
            ex.printStackTrace();
            booRet=false;
            objUti.mostrarMsgErr_F1(null, ex); 
        }
        return booRet;
    }
    

/**
     * Metodo para insertar la Cabecera de la orden de Despacho en el caso Cliente NO RETIRA.
     * @param conn conexion acceso a datos.
     * @param rsDatConDes ResultSet con los registros que se deben insertar en l
     * @param intCodTipOD codigo del tipo documento Orden Despacho
     * @param intODNew Codigo de la numero OD.
     * @param intNumOD Codigo del ultimo numero de OD.
     * @param intCodBod Codigo del codigo de la Bodega.
     * @param strDirBod Direccion d ela bodega.
     * @param intCodEmp1 codigo de empresa.
     * @param intCodLoc1 codigo de local.
     * @return boolean indica si el metodo se ejecuto correctamente o no.
     */
    public boolean insertarCabOrdDespCuenca(Connection conn,ResultSet rsDatConDes,int intCodTipOD, int intCodOD, int intNumOD,int intCodBod,String strDirBod, int intCodEmp1, int intCodLoc1){
        String strSql="";
        boolean booRet=false;
        strOD="";
        try{
            if(rsDatConDes.next()){
               strSql=" INSERT INTO tbm_cabguirem (co_emp, co_loc, co_tipdoc, co_doc, fe_doc, fe_orddes "+
                      " ,ne_numorddes, ne_numdoc, co_clides, tx_rucclides, tx_nomclides,  tx_dirclides, tx_telclides, tx_ciuclides, st_imp " +
                      " ,st_reg, fe_ing, co_usring ,st_conInv, fe_initra, fe_tertra, tx_coming, st_regrep, co_ptopar, tx_ptopar " +
                      " ,tx_datdocoriguirem, tx_numped, co_ptodes ,st_imporddes  ) " +                       
                      " VALUES ( "+intCodEmp1+", "+intCodLoc1+", "+intCodTipOD+", "+intCodOD+",  current_date, current_date "+   // '"+rstLoc.getString("fe_doc")+"' " +
                      " ,"+intNumOD+", 0, "+rsDatConDes.getString("co_cli")+", '"+rsDatConDes.getString("tx_ruc")+ "', '"+rsDatConDes.getString("tx_nomcli")+"', '"+rsDatConDes.getString("tx_dircli")+"' " +
                      " ,'"+rsDatConDes.getString("tx_telcli")+"', '"+rsDatConDes.getString("tx_ciucli")+ "', 'N' ,'A', current_timestamp , 1 " +
                      " ,'P', current_date, current_date, '', 'I' , "+intCodBod+", '"+strDirBod+"'  " +
                      //" , '' ,'',  "+rsDatConDes.getString("co_ptodes")+",'S' )";
                       " , '' ,'',  "+rsDatConDes.getString("co_ptodes")+",'N' )";

               Statement stInsCab=conn.createStatement();
               stInsCab.execute(strSql);
               strOD=rsDatConDes.getString("co_emp")+"-"+rsDatConDes.getString("co_loc")+"-"+intCodTipOD+"-"+intCodOD;
               booRet=true;
            }            
        }catch(Exception ex){
            ex.printStackTrace();
            booRet=false;
            objUti.mostrarMsgErr_F1(null, ex);
        }
        return booRet;
    }  
    
    
    /**
     * 
     * @param conn Conexion de Acceso a Datos
     * @param rsDatConDes resultset con los datos de la cabecera de la orden de despacho.
     * @param intCodTipOD tipo del documento.
     * @param intCodOD codigo del documento.
     * @param intNumOD numero de orden de despacho.
     * @param intCodBod codigo de la bodega.
     * @param strDirBod direccion de la bodega.
     * @param intCodEmp1 empresa donde se inserta la orden.
     * @param intCodLoc1 local donde se inserta la orden.
     * @return boolean.
     */
    public boolean insertarCabOrdDespInmaconsa(Connection conn,ResultSet rsDatConDes,int intCodTipOD, int intCodOD, int intNumOD,int intCodBod,String strDirBod, int intCodEmp1, int intCodLoc1){
        String strSql="";
        boolean booRet=false;
        strOD="";
        try{
            if(rsDatConDes.next()){
               strSql=" INSERT INTO tbm_cabguirem (co_emp, co_loc, co_tipdoc, co_doc, fe_doc, fe_orddes "+
                      " ,ne_numorddes, ne_numdoc, co_clides, tx_rucclides, tx_nomclides,  tx_dirclides, tx_telclides, tx_ciuclides, st_imp " +
                      " ,st_reg, fe_ing, co_usring ,st_conInv, fe_initra, fe_tertra, tx_coming, st_regrep, co_ptopar, tx_ptopar " +
                      " ,tx_datdocoriguirem, tx_numped, co_ptodes ,st_imporddes  ) " +                       
                      " VALUES ( "+intCodEmp1+", "+intCodLoc1+", "+intCodTipOD+", "+intCodOD+",  current_date, current_date "+   // '"+rstLoc.getString("fe_doc")+"' " +
                      " ,"+intNumOD+", 0, "+rsDatConDes.getString("co_cli")+", '"+rsDatConDes.getString("tx_ruc")+ "', '"+rsDatConDes.getString("tx_nomcli")+"', '"+rsDatConDes.getString("tx_dircli")+"' " +
                      " ,'"+rsDatConDes.getString("tx_telcli")+"', '"+rsDatConDes.getString("tx_ciucli")+ "', 'N' ,'A', current_timestamp , 1 " +
                      " ,'P', current_date, current_date, '', 'I' , "+intCodBod+", '"+strDirBod+"'  " +
                      //" , '' ,'',  "+rsDatConDes.getString("co_ptodes")+",'S' )";
                      " , '' ,'',  "+rsDatConDes.getString("co_ptodes")+",'N' )";

               Statement stInsCab=conn.createStatement();
               stInsCab.execute(strSql);
               strOD=rsDatConDes.getString("co_emp")+"-"+rsDatConDes.getString("co_loc")+"-"+intCodTipOD+"-"+intCodOD;
               booRet=true;
            }            
        }catch(Exception ex){
            ex.printStackTrace();            
            booRet=false;
            objUti.mostrarMsgErr_F1(null, ex); 
        }
        return booRet;
    }    
    
    
    /**
     * Metodo que inserta los detalles de la Orden de Despacho.
     * @param con conexion Acceso a Datos.
     * @param rsDatDetDes Resultset con los Datos a Insertar(basado en los detalles de facturas de ventas entre empresas relacionadas).
     * @param intCodEmp codigo de la empresa.
     * @param intCodLoc codigo del local.
     * @param intCodTipOd codigo del tipo de documento de Orden de Despacho.
     * @param intODNew codigo de la nueva Orden de Despacho.
     * @param intNumOD
     * @return 
     */
    public boolean insertarDetOrdDesp(Connection con, ResultSet rsDatDetDes, int intCodEmp, int intCodLoc,int intCodTipOd, int intCodOD, int intNumOD){
        String strIns="";
        String strUpd="";
        StringBuffer strInsDet=new StringBuffer("");
        Statement stmInsDet=null;
        boolean booRetorno=false;
        ZafUtil objZafUtil=new ZafUtil();
        int intDetOD=0;
        try{
            while(rsDatDetDes.next()){
                intDetOD++;
                String strObsDocRel = rsDatDetDes.getString("docrel") +"  "+rsDatDetDes.getString("tx_numped");
                if(intDetOD>1){
                    strInsDet.append(" union all ");
                }
                strInsDet.append("select "+intCodEmp+","+intCodLoc+","+intCodTipOd+","+intCodOD+",");
                strInsDet.append(intDetOD+","+intCodEmp+","+intCodLoc+","+rsDatDetDes.getInt("co_tipdoc"));
                strInsDet.append(","+rsDatDetDes.getInt("co_doc")+","+rsDatDetDes.getInt("co_reg")+", " );                
                
                strInsDet.append(rsDatDetDes.getString("co_itm")+", '"+rsDatDetDes.getString("tx_codalt")+"'," );
                strInsDet.append(objZafUtil.codificar(rsDatDetDes.getString("tx_nomitm"))+",'"+rsDatDetDes.getString("tx_unimed")+"', abs("+rsDatDetDes.getString("nd_can")+")  ");

                strInsDet.append(" ,'I', '"+strObsDocRel+"',  '"+rsDatDetDes.getString("st_meringegrfisbod")+"' ");
                
                strUpd+="UPDATE tbm_cabmovinv SET st_creguirem='S', ne_numorddes="+intNumOD+"  WHERE co_emp="+intCodEmp+" " +
                " and co_loc="+intCodLoc+" and co_tipdoc="+rsDatDetDes.getInt("co_tipdoc")+" and co_doc="+rsDatDetDes.getInt("co_doc")+";";

                strUpd+="UPDATE tbm_detmovinv SET tx_obs1='"+intNumOD+"'  WHERE co_emp="+intCodEmp+" " +
                " and co_loc="+intCodLoc+" and co_tipdoc="+rsDatDetDes.getInt("co_tipdoc")+" and co_doc="+rsDatDetDes.getInt("co_doc")+";";
                

            }
            rsDatDetDes.close();
            rsDatDetDes=null;
            if(intDetOD>0){
                strIns="INSERT INTO  tbm_detguirem (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel," +
                                                  " co_tipdocrel, co_docrel, co_regrel, co_itm, tx_codalt,  tx_nomitm"+
                                                  " , tx_unimed, nd_can, st_regrep, tx_obs1,  st_meregrfisbod ) "+strInsDet.toString();
                stmInsDet=con.createStatement();
                stmInsDet.execute(strIns);
                stmInsDet.executeUpdate(strUpd);
            }
            booRetorno=true;
        }catch(Exception ex){
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(null, ex); 
        }
        return booRetorno;
    }
    
    
    /**
     * Metodo que inserta los detalles de la ORDEN DE DESPACHO generado por CLIENTE NO RETIRA (CUENCA).
     * @param con Conexion de acceso. 
     * @param rsDatDetDes Resultset con los registros de detalle .
     * @param intCodEmp Codigo de la empresa relacionada.
     * @param intCodLoc Codigo del local relacionado.
     * @param intCodTipODRef Codigo del tipo de documento relacionado.
     * @param intCodODRef Codigo del documento relacionado.
     * @param intNumOD numero de la orden de despacho.
     * @param intCodEmpOrdDesRef Codigo de la empresa de la OD.
     * @param intCodLocOrdDesRef Codigo del local de la OD.
     * @param intCodTipDocOrdDesRef Codigo del tipo de documento de la OD.
     * @param intCodDocOrdDesRef Codigo del documento de la OD.
     * @return boolean.
     */
    public boolean insertarDetOrdDespCuenca(Connection con, ResultSet rsDatDetDes, int intCodEmp, int intCodLoc,int intCodTipODRef, int intCodODRef, int intNumOD, int intCodEmpOrdDesRef, int intCodLocOrdDesRef, int intCodTipDocOrdDesRef, int intCodDocOrdDesRef){
        String strIns="";
        String strUpd="";
        StringBuffer strInsDet=new StringBuffer("");
        Statement stmInsDet=null;
        boolean booRetorno=false;
        ZafUtil objUtil=new ZafUtil();
        int intDetOD=0;
        try{
            while(rsDatDetDes.next()){
                intDetOD++;
                String strObsDocRel = rsDatDetDes.getString("docrel") +"  "+rsDatDetDes.getString("tx_numped");
                if(intDetOD>1){
                    strInsDet.append(" union all ");
                }
                strInsDet.append("select "+intCodEmpOrdDesRef+","+intCodLocOrdDesRef+","+intCodTipDocOrdDesRef+","+intCodDocOrdDesRef+",");
                strInsDet.append(intDetOD+","+intCodEmp+","+intCodLoc+","+intCodTipODRef);
                strInsDet.append(","+intCodODRef+","+rsDatDetDes.getInt("co_reg")+", " );                
                
                strInsDet.append(rsDatDetDes.getString("co_itm")+", '"+rsDatDetDes.getString("tx_codalt")+"'," );
                //strInsDet.append("'"+rsDatDetDes.getString("tx_nomitm")+"','"+rsDatDetDes.getString("tx_unimed")+"', abs("+rsDatDetDes.getString("nd_can")+")  ");
                strInsDet.append(objUtil.codificar(rsDatDetDes.getString("tx_nomitm"))+",'"+rsDatDetDes.getString("tx_unimed")+"', abs("+rsDatDetDes.getString("nd_can")+")  ");
                strInsDet.append(" ,'I', '"+strObsDocRel+"',  '"+rsDatDetDes.getString("st_meringegrfisbod")+"' ");
                
                strUpd+="UPDATE tbm_cabmovinv SET st_creguirem='S', ne_numorddes="+intNumOD+"  WHERE co_emp="+intCodEmp+" " +
                " and co_loc="+intCodLoc+" and co_tipdoc="+rsDatDetDes.getInt("co_tipdoc")+" and co_doc="+rsDatDetDes.getInt("co_doc")+";";

                strUpd+="UPDATE tbm_detmovinv SET tx_obs1='"+intNumOD+"'  WHERE co_emp="+intCodEmp+" " +
                " and co_loc="+intCodLoc+" and co_tipdoc="+rsDatDetDes.getInt("co_tipdoc")+" and co_doc="+rsDatDetDes.getInt("co_doc")+";";
                

            }
            rsDatDetDes.close();
            rsDatDetDes=null;
            if(intDetOD>0){
                strIns="INSERT INTO  tbm_detguirem (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel," +
                                                  " co_tipdocrel, co_docrel, co_regrel, co_itm, tx_codalt,  tx_nomitm"+
                                                  " , tx_unimed, nd_can, st_regrep, tx_obs1,  st_meregrfisbod ) "+strInsDet.toString();
                stmInsDet=con.createStatement();
                stmInsDet.execute(strIns);
                stmInsDet.executeUpdate(strUpd);
            }
            booRetorno=true;
        }catch(Exception ex){
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(null, ex);
        }
        return booRetorno;
    }  
    
    /**
     * Metodo que inserta los detalles de la ORDEN DE DESPACHO generado por CLIENTE NO RETIRA (INMACONSA).
     * @param con Conexion de acceso. 
     * @param rsDatDetDes Resultset con los registros de detalle .
     * @param intCodEmp Codigo de la empresa relacionada.
     * @param intCodLoc Codigo del local relacionado.
     * @param intCodTipODRef Codigo del tipo de documento relacionado.
     * @param intCodODRef Codigo del documento relacionado.
     * @param intNumOD numero de la orden de despacho.
     * @param intCodEmpOrdDesRef Codigo de la empresa de la OD.
     * @param intCodLocOrdDesRef Codigo del local de la OD.
     * @param intCodTipDocOrdDesRef Codigo del tipo de documento de la OD.
     * @param intCodDocOrdDesRef Codigo del documento de la OD.
     * @return boolean.
     */
    public boolean insertarDetOrdDespInmaconsa(Connection con, ResultSet rsDatDetDes, int intCodEmp, int intCodLoc,int intCodTipODRef, int intCodODRef, int intNumOD, int intCodEmpOrdDesRef, int intCodLocOrdDesRef, int intCodTipDocOrdDesRef, int intCodDocOrdDesRef){
        String strIns="";
        String strUpd="";
        StringBuffer strInsDet=new StringBuffer("");
        Statement stmInsDet=null;
        boolean booRetorno=false;
        ZafUtil objZafUtil=new ZafUtil();
        int intDetOD=0;
        try{
            while(rsDatDetDes.next()){
                intDetOD++;
                String strObsDocRel = rsDatDetDes.getString("tx_docrel") +"  "+rsDatDetDes.getString("tx_numped");
                if(intDetOD>1){
                    strInsDet.append(" union all ");
                }
                strInsDet.append("select "+intCodEmpOrdDesRef+","+intCodLocOrdDesRef+","+intCodTipDocOrdDesRef+","+intCodDocOrdDesRef+",");
                strInsDet.append(intDetOD+","+intCodEmp+","+intCodLoc+","+intCodTipODRef);
                strInsDet.append(","+intCodODRef+","+rsDatDetDes.getInt("co_reg")+", " );                
                
                strInsDet.append(rsDatDetDes.getString("co_itm")+", '"+rsDatDetDes.getString("tx_codalt")+"'," );
                strInsDet.append(objZafUtil.codificar(rsDatDetDes.getString("tx_nomitm"))+",'"+rsDatDetDes.getString("tx_unimed")+"', abs("+rsDatDetDes.getString("nd_can")+")  ");
                strInsDet.append(" ,'I', '"+strObsDocRel+"',  '"+rsDatDetDes.getString("st_meringegrfisbod")+"' ");
                
                strUpd+="UPDATE tbm_cabmovinv SET st_creguirem='S', ne_numorddes="+intNumOD+"  WHERE co_emp="+intCodEmp+" " +
                " and co_loc="+intCodLoc+" and co_tipdoc="+rsDatDetDes.getInt("co_tipdoc")+" and co_doc="+rsDatDetDes.getInt("co_doc")+";";

                strUpd+="UPDATE tbm_detmovinv SET tx_obs1='"+intNumOD+"'  WHERE co_emp="+intCodEmp+" " +
                " and co_loc="+intCodLoc+" and co_tipdoc="+rsDatDetDes.getInt("co_tipdoc")+" and co_doc="+rsDatDetDes.getInt("co_doc")+";";
                

            }
            rsDatDetDes.close();
            rsDatDetDes=null;
            if(intDetOD>0){
                strIns="INSERT INTO  tbm_detguirem (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel," +
                                                  " co_tipdocrel, co_docrel, co_regrel, co_itm, tx_codalt,  tx_nomitm"+
                                                  " , tx_unimed, nd_can, st_regrep, tx_obs1,  st_meregrfisbod ) "+strInsDet.toString();
                stmInsDet=con.createStatement();
                stmInsDet.execute(strIns);
                stmInsDet.executeUpdate(strUpd);
            }
            booRetorno=true;
        }catch(Exception ex){
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(null, ex);
        }
        return booRetorno;
    }     
    
    
    
    /**
     * Método para obtener la bodega del grupo.
     * @param con conexión de acceso a datos.
     * @param intEmp codigo de empresa.
     * @param intBod codigo de la bodega.
     * @return int retorno del codigo de la bodega del grupo.
     */
    public int obtenerBodGru(Connection con,int intEmp, int intBod){
        int intBodGrp=0;
        Statement stmQry=null;
        ResultSet rsDat=null;        
        String strSql=" select co_bodgrp "+
                      " from tbr_bodempbodgrp where co_emp="+intEmp+ " and co_bod="+intBod;
        try{
            stmQry=con.createStatement();
            rsDat=stmQry.executeQuery(strSql);
            if(rsDat.next()){
                intBodGrp=rsDat.getInt("co_bodgrp");
            }            
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                rsDat.close();
                rsDat=null;
                stmQry.close();
                stmQry=null;
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return intBodGrp;
    }
    
    
    /**
     * Metodo que obtiene los documentos que son de transferencia en tabla CABMOVINV
     * @param con conexion de acceso a datos
     * @param strTipDoc String con los tipos de documentos de transferencia.
     * @param intCodBodGrp codigo de la bodega del Grupo.
     * @return ResultSet con  los registros de los documentos.
     */
    public ResultSet obtenerDatosODxTran(Connection con, String strTipDoc, int intCodBodGrp, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
        
        Statement stODTran=null;
        ResultSet rsODTran=null;
        
        try{
            StringBuffer strQry=new StringBuffer(" select a.co_emp,  a.co_loc, a.co_tipdoc, ");
            strQry.append(" a.co_doc, a.fe_doc, a1.co_bod, ");
            strQry.append("( a3.tx_descor || '-' || a.ne_numdoc ) as docrel, a4.tx_dir ");
            strQry.append(" FROM tbm_cabmovinv AS a " );
            strQry.append(" INNER JOIN tbm_detmovinv as a1 ");
            strQry.append(" on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) ");
            strQry.append(" INNER JOIN tbr_bodEmpBodGrp AS a2 ");
            strQry.append(" ON (a2.co_emp=a.co_emp AND a2.co_bod=a1.co_bod)");
            strQry.append(" INNER JOIN tbm_cabtipdoc AS a3 ");
            strQry.append(" on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc) ");
            strQry.append(" INNER JOIN tbm_bod AS a4 ");
            strQry.append(" on (a4.co_emp=a1.co_emp and a4.co_bod=a1.co_bod)");
            strQry.append(" WHERE   a.co_tipdoc IN ( "+strTipDoc+ ") ");
            strQry.append(" AND  a.st_creguirem='N' " ); 
            strQry.append(" AND a1.nd_can < 0 "); 
            strQry.append(" AND  a.st_reg not in('E','I')"); 
            strQry.append(" AND ( a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrp+ ") ");
            strQry.append(" AND a.co_emp="+intCodEmp);
            strQry.append(" AND a.co_loc="+intCodLoc);
            strQry.append(" AND a.co_tipdoc="+intCodTipDoc);
            strQry.append(" AND a.co_doc="+intCodDoc); 
            strQry.append(" AND (a.ne_numorddes is null OR a.ne_numorddes=0)"); 
            strQry.append(" GROUP BY a.co_emp,  a.co_loc, a.co_tipdoc,");
            strQry.append(" a.co_doc, a.fe_doc, a1.co_bod,");
            strQry.append(" a3.tx_descor, a.ne_numdoc, a4.tx_dir ");
            stODTran= con.createStatement();
            rsODTran= stODTran.executeQuery(strQry.toString());
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        return rsODTran;        
    }
    

    
    /**
     * VERIFICA BODEGAS PARA GENERAR ORDEN DE DESPACHO POR TRANSFERENCIAS.
     * @param conx conexion acceso a datos.
     * @param intCodEmp codigo de la empresa.
     * @param intCodLoc codigo del local.
     * @param intCodTipDoc codigo del tipo del documento.
     * @param intCodDoc codigo del documento.
     * @return boolean.
     */
    public boolean verificaBodOD(Connection conx, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
        
        boolean booRet=false;
        Statement stmRe=null;
        ResultSet rsBodOD=null;
        String strSql= " SELECT * FROM ( "
            + " SELECT distinct(co_bod), co_emp FROM tbm_detmovinv "
            + " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and nd_can > 0 "
            + " ) as x "
            + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=x.co_emp AND a6.co_bod=x.co_bod) "
            + " AND ( a6.co_empGrp=0 AND a6.co_bodGrp in (1,2,3,4,5,9,11,15,20,21,22,23, 28,6,29) ) ";//se agrego el 4
        try{
            stmRe=conx.createStatement();
            rsBodOD=stmRe.executeQuery(strSql);
            if(rsBodOD.next()){
                booRet=true;
            }
        
        }catch(Exception ex){
            ex.printStackTrace();            
        }
        return booRet;
    }
    
    
    /**
     * Actualiza la tabla cabmovinv
     * @param conn conexion de acceso a datos
     * @param intCodEmp codigo de la empresa.
     * @param intCodLoc codigo del local.
     * @param intCodTipDoc codigo del tipo de documento.
     * @param intCodDoc codigo del documento.
     * @return  boolean.
     */
    public boolean actualizarDocTran (Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
        boolean booRet=false;
        Statement stmUpd=null;
        try{
            String strSql="UPDATE tbm_cabmovinv SET st_creguirem='S'  WHERE co_emp="+intCodEmp+" " +
              " and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc;
            stmUpd=conn.createStatement();
            stmUpd.execute(strSql);
            booRet=true;
        }catch(Exception ex){
            ex.printStackTrace();
        }        
        return booRet;    
    }
    
    
    /**
     * Metodo para obtener Datos de los detalles de Ordenes Despacho.
     * @param conx conexion de acceso.
     * @param intCodEmp codigo de empresa.
     * @param intCodLoc codigo de local.
     * @param intCodTipDoc codigo del tipo de documento.
     * @param intCodDoc codigo del documento.
     * @param intCodBodOri codigo de la bodega de origen.
     * @param intCodBodDes codigo de la bodega de despacho.
     * @return ResultSet.
     */
    public ResultSet obtenerDatDetODxTran(Connection conx, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBodOri, int intCodBodDes){
        ResultSet rsDetTra=null;
        Statement stmDetTra=null;
        String strQry="";
        try{
            stmDetTra=conx.createStatement();
            strQry= " SELECT x.co_emp, x.co_loc, x.co_tipdoc, x.co_doc, "+
                          " x.co_itm, x.co_reg, x.tx_codalt, x.tx_nomitm, "+
                          " x.tx_unimed, abs(x.nd_can) as nd_can, x.st_meringegrfisbod "+
                    " FROM (  " +
                        "   SELECT co_emp, co_loc, co_tipdoc, "+
                                 " co_doc, co_reg, co_itm, "+
                                 " tx_codalt, tx_nomitm, tx_unimed, "+
                                 " nd_can, st_meringegrfisbod , co_bod , ne_numfil " +
                          " FROM tbm_detmovinv " +
                          " WHERE co_emp="+intCodEmp+
                          " and co_loc="+intCodLoc+
                          " and co_tipdoc="+intCodTipDoc+
                          " and co_doc="+intCodDoc+
                          " and co_bod="+intCodBodOri+" and nd_can < 0  "+
                        " ) AS x "+
                   " inner join ( " +
                        " SELECT co_emp, co_loc, co_tipdoc, "+
                        "        co_doc, co_reg, co_itm, "+
                        "        tx_codalt, tx_nomitm, tx_unimed, "+
                        "        nd_can, st_meringegrfisbod , co_bod , ne_numfil " +
                        " FROM tbm_detmovinv " +
                        " WHERE co_emp="+intCodEmp+
                        " and co_loc="+intCodLoc+
                        " and co_tipdoc="+intCodTipDoc+
                        " and co_doc="+intCodDoc+
                        " and co_bod="+intCodBodDes+" and nd_can > 0  "+
                  " ) AS x1 "+
                  " on ( x1.co_emp=x.co_emp and x1.co_loc=x.co_loc "+
                  " and x1.co_tipdoc=x.co_tipdoc and x1.co_doc=x.co_doc "+
                  " and x1.ne_numfil=x.ne_numfil )";

            rsDetTra=stmDetTra.executeQuery(strQry);
            
        }catch(Exception ex){
            ex.printStackTrace();        
        }        
        return rsDetTra;
    }
    
 
    
   /**
    * 
    * @param cnx conexion de acceso a datos.
    * @param rsDetODxTra resultset que trae los datos de los detalles de los documentos de transferencia.
    * @param intCodEmp codigo de la empresa.
    * @param intCodLoc codigo del local.
    * @param intCodTipDoc codigo del tipo de documento.
    * @param intCodDoc codigo del documento.
    * @param intTipDocGuia codigo del tipo documento.
    * @param intCodDocGuia codigo del documento de guia remision.
    * @return boolean que indica si se realizo con exito o error la insercion de los datos.
    */
    public boolean insertarDetODxTran(Connection cnx, ResultSet rsDetODxTra, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intTipDocGuia, int intCodDocGuia){
        boolean booRet=false;
        StringBuffer stbinsGuia=new StringBuffer("");
        int intReg=0;
        String strSql="";
        Statement stmLoc=null;
        ZafUtil objUtil=new ZafUtil();
        
        int intEstIns=0;
        try{
            while(rsDetODxTra.next()){
                intReg++;
                if(intEstIns > 0)
                    stbinsGuia.append(" UNION ALL ");
                
                stbinsGuia.append("SELECT "+intCodEmp+","+intCodLoc+","+intTipDocGuia+", "+intCodDocGuia+" " +
                " ,"+intReg+", "+intCodEmp+","+intCodLoc+","+intCodTipDoc+","+intCodDoc+","+rsDetODxTra.getInt("co_reg")+" " +
                " , "+rsDetODxTra.getString("co_itm")+", '"+rsDetODxTra.getString("tx_codalt")+"' " +
                //" ,'"+rsDetODxTra.getString("tx_nomitm")+"','"+rsDetODxTra.getString("tx_unimed")+"', abs("+rsDetODxTra.getString("nd_can")+") " +
                " ,"+objUtil.codificar(rsDetODxTra.getString("tx_nomitm"))+",'"+rsDetODxTra.getString("tx_unimed")+"', abs("+rsDetODxTra.getString("nd_can")+") " +                        
                " ,'I', '', '"+rsDetODxTra.getString("st_meringegrfisbod")+"' ");                
                intEstIns=1;
            }
            rsDetODxTra.close();
            rsDetODxTra=null;
            
            if(intEstIns==1){
                strSql="INSERT INTO tbm_detguirem (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel," +
                " co_tipdocrel, co_docrel, co_regrel, co_itm, tx_codalt,  tx_nomitm, tx_unimed, nd_can, st_regrep, tx_obs1, st_meregrfisbod ) "+stbinsGuia.toString();
                stmLoc=cnx.createStatement();
                stmLoc.execute(strSql);
                booRet=true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(null, ex);
        }
        return booRet;    
    }
    
    
    public boolean insertarDetODxTranImp(Connection cnx, ResultSet rsDetODxTra, int intCodEmpGuia, int intCodLocGuia,int intTipDocGuia, int intCodDocGuia, int intCodEmp,int intCodLoc, int intCodTipDoc, int intCodDoc){
        boolean booRet=false;
        StringBuffer stbinsGuia=new StringBuffer("");
        int intReg=0;
        String strSql="";
        Statement stmLoc=null;
        ZafUtil objUtil=new ZafUtil();
        
        int intEstIns=0;
        try{
            while(rsDetODxTra.next()){
                intReg++;
                if(intEstIns > 0)
                    stbinsGuia.append(" UNION ALL ");
                
                stbinsGuia.append("SELECT "+intCodEmpGuia+","+intCodLocGuia+","+intTipDocGuia+", "+intCodDocGuia+" " +
                " ,"+intReg+", "+intCodEmp+","+intCodLoc+","+intCodTipDoc+","+intCodDoc+","+rsDetODxTra.getInt("co_reg")+" " +
                " , "+rsDetODxTra.getString("co_itm")+", '"+rsDetODxTra.getString("tx_codalt")+"' " +
                //" ,'"+rsDetODxTra.getString("tx_nomitm")+"','"+rsDetODxTra.getString("tx_unimed")+"', abs("+rsDetODxTra.getString("nd_can")+") " +
                " ,"+objUtil.codificar(rsDetODxTra.getString("tx_nomitm"))+",'"+rsDetODxTra.getString("tx_unimed")+"', abs("+rsDetODxTra.getString("nd_can")+") " +                        
                " ,'I', '', '"+rsDetODxTra.getString("st_meringegrfisbod")+"' ");                
                intEstIns=1;
            }
            rsDetODxTra.close();
            rsDetODxTra=null;
            
            if(intEstIns==1){
                strSql="INSERT INTO tbm_detguirem (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel," +
                " co_tipdocrel, co_docrel, co_regrel, co_itm, tx_codalt,  tx_nomitm, tx_unimed, nd_can, st_regrep, tx_obs1, st_meregrfisbod ) "+stbinsGuia.toString();
                stmLoc=cnx.createStatement();
                stmLoc.execute(strSql);
                booRet=true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(null, ex);
        }
        return booRet;    
    }
    
       
    
    /**
     * Metodo que inserta la OD (cabecera) por documentos que sean de transferencia.
     * @param cnx conexion de acceso a Datos.
     * @param intCodEmp codigo de empresa.
     * @param intCodLoc codigo del local.
     * @param intCodTipDocGuia codigo del tipo de documento de OD.
     * @param intCodDocGuia codigo del documento de OD.
     * @param strRucCli Ruc cliente.
     * @param strNomCli Nombre cliente.
     * @param strDirCli Direccion del cliente.
     * @param strTelCli Telefono del cliente.
     * @param intNumGuiaRem numero de guia de remision.
     * @param intCodBod bodega de partida.(de donde sale la mercaderia)
     * @param strDirBod direccion bodega.
     * @param strDocRel documento relacionado.
     * @param strcoclides codigo del cliente 
     * @param intCodBodDes codigo de bodega destino.(donde llega la mercaderia )
     * @param intCodTipDoc codigo del tipo de documento para actualizar.
     * @param intCodDoc codigo del documento para actualizar.
     * @return boolean indica si la ejecucion del metodo es correcta o incorrecta.
     */
    public boolean insertCabODxTran(Connection cnx, int intCodEmp, int intCodLoc, int intCodTipDocGuia, 
            int intCodDocGuia, String strRucCli, String strNomCli, String strDirCli,  String strTelCli,
            int intNumGuiaRem, int intCodBod, String strDirBod, String strDocRel,
            String strcoclides, int intCodBodDes, int intCodTipDoc, int intCodDoc){
        
        boolean booRet=false;
        Statement stmInsCabOD=null;
        
        try{
          String strSql="INSERT INTO tbm_cabguirem( co_emp, co_loc, co_tipdoc, co_doc, fe_doc ,fe_orddes "+
          " , ne_numorddes, ne_numdoc,  tx_rucclides, tx_nomclides,  tx_dirclides, tx_telclides, tx_ciuclides, st_imp " +
          " ,st_reg, fe_ing, co_usring ,st_conInv, fe_initra, fe_tertra, tx_coming, st_regrep, co_ptopar, tx_ptopar " +
          " , tx_vehret, tx_choret ,tx_datdocoriguirem , co_clides, co_ptodes, st_imporddes  ) "+
          " VALUES( "+intCodEmp+", "+intCodLoc+", "+intCodTipDocGuia+", "+intCodDocGuia+", current_date, current_date  "+  // '"+strFecDoc+"' " +
          " ,"+intNumGuiaRem+", 0,   '"+strRucCli+ "', '"+strNomCli+"', '"+strDirCli+"' " +
          " ,'"+strTelCli+"', '', 'N' ,'A', current_timestamp , 1 " +
          " ,'P', current_date, current_date, '', 'I' , "+intCodBod+", '"+strDirBod+"'  " +
          //" , '', '', '"+strDocRel+"', "+strcoclides+", "+intCodBodDes+", 'S'  )";
          " , '', '', '"+strDocRel+"', "+strcoclides+", "+intCodBodDes+", 'N'  )";

          strSql+=" ; UPDATE tbm_cabmovinv SET st_creguirem='S', ne_numorddes="+intNumGuiaRem+"  WHERE co_emp="+intCodEmp+" " +
          " and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc; 
          
          stmInsCabOD=cnx.createStatement();
          stmInsCabOD.execute(strSql);
          stmInsCabOD.close();
          stmInsCabOD=null;
          booRet=true;
        }catch(Exception ex){
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(null, ex);
        }
        return booRet;   
    }
    
    
private int getBodegaOrigenDestino_Inmaconsa(Connection con1, int intCodEmp_Imp, int intCodBodEmpTrs){
        boolean blnRes=true;
        Statement stm11;
        ResultSet rst11;
        String strAux;
        int intCodBod_OriDes_LocInm=-1;
        try{
            if(con1!=null){
                stm11=con1.createStatement();
                strAux="";
                strAux+="SELECT b1.* FROM(";
                strAux+=" 	SELECT a2.co_empGrp, a2.co_bodGrp, a1.*";
                strAux+="	FROM tbm_bod AS a1 INNER JOIN tbr_bodEmpBodGrp AS a2";
                strAux+="	ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strAux+="	WHERE a1.co_emp=" + intCodEmp_Imp + "";
                strAux+=") AS b1";
                strAux+=" INNER JOIN(";
                strAux+=" 	SELECT a2.co_empGrp, a2.co_bodGrp";
                strAux+=" 	FROM tbm_bod AS a1 INNER JOIN tbr_bodEmpBodGrp AS a2";
                strAux+="	ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strAux+="	WHERE a1.co_emp=" + intCodEmp_Imp + " AND a1.co_bod=" + intCodBodEmpTrs + "";
                strAux+=" ) AS b2";
                strAux+=" ON b1.co_empGrp=b2.co_empGrp AND b1.co_bodGrp=b2.co_bodGrp";
                rst11=stm11.executeQuery(strAux);
                if(rst11.next()){
                    intCodBod_OriDes_LocInm=rst11.getInt("co_bod");
                }
                stm11.close();
                stm11=null;
                rst11.close();
                rst11=null;
            }

        }
        catch(java.sql.SQLException e){
            blnRes=false;
            System.out.println("ERROR SQL getBodegaLocalInmaconsa: "+e );
            //objEnvMail.enviarCorreo("ERROR SQL getBodegaLocalInmaconsa: "+e.toString() );
        }
        catch(Exception e){
            blnRes=false;
            System.out.println("ERROR getBodegaLocalInmaconsa: "+e );
            //objEnvMail.enviarCorreo("ERROR getBodegaLocalInmaconsa: "+e.toString() );
        }
        return intCodBod_OriDes_LocInm;
    }    
    
    
    public boolean insertCabODxTranImp(Connection cnx, int intCodEmp, int intCodLoc, int intCodTipDocGuia, 
            int intCodDocGuia, String strRucCli, String strNomCli, String strDirCli,  String strTelCli,
            int intNumGuiaRem, int intCodBod, String strDirBod, String strDocRel,
            String strcoclides, int intCodBodDes, int intCodTipDoc, int intCodDoc, String strNomImp, int intCodEmp2, int intCodLoc2){
        
        boolean booRet=false;
        Statement stmInsCabOD=null;
        
        try{
            
          String strSql="INSERT INTO tbm_cabguirem( co_emp, co_loc, co_tipdoc, co_doc, fe_doc ,fe_orddes "+
          " , ne_numorddes, ne_numdoc,  tx_rucclides, tx_nomclides,  tx_dirclides, tx_telclides, tx_ciuclides, st_imp " +
          " ,st_reg, fe_ing, co_usring ,st_conInv, fe_initra, fe_tertra, tx_coming, st_regrep, co_ptopar, tx_ptopar " +
          " , tx_vehret, tx_choret ,tx_datdocoriguirem , co_clides, co_ptodes, st_imporddes,tx_obs2  ) "+
          " VALUES( "+intCodEmp+", "+intCodLoc+", "+intCodTipDocGuia+", "+intCodDocGuia+", current_date, current_date  "+  // '"+strFecDoc+"' " +
          " ,"+intNumGuiaRem+", 0,   '"+strRucCli+ "', '"+strNomCli+"', '"+strDirCli+"' " +
          " ,'"+strTelCli+"', '', 'N' ,'A', current_timestamp , 1 " +
          " ,'P', current_date, current_date, '', 'I' , "+getBodegaOrigenDestino_Inmaconsa(cnx, intCodEmp, intCodBod)+", '"+strDirBod+"'  " +
          //" , '', '', '"+strDocRel+"', "+strcoclides+", "+getBodegaOrigenDestino_Inmaconsa(cnx, intCodEmp, intCodBodDes)+", 'S','"+strNomImp+"'  )";
		  " , '', '', '"+strDocRel+"', "+strcoclides+", "+getBodegaOrigenDestino_Inmaconsa(cnx, intCodEmp, intCodBodDes)+", 'N','"+strNomImp+"'  )";

          strSql+=" ; UPDATE tbm_cabmovinv SET st_creguirem='S', ne_numorddes="+intNumGuiaRem+"  WHERE co_emp="+intCodEmp2+" " +
          " and co_loc="+intCodLoc2+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc; 
          
          stmInsCabOD=cnx.createStatement();
          stmInsCabOD.execute(strSql);
          stmInsCabOD.close();
          stmInsCabOD=null;
          booRet=true;
        }catch(Exception ex){
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(null, ex);
        }
        return booRet;   
    }    

    
    
    public ResultSet obtenerDocTransGenODTran(Connection conn, int codEmp, int codLoc, int codTipDoc, int codDoc){
        String strQry="";
        Statement stmDocTra=null;
        ResultSet rsODTra=null;        
        try{
            /*strQry=" SELECT a.co_emp, x.co_bod, a.tx_nom, a.tx_dir, a.tx_tel, " +
                    
                            //CODIGO DEL DOCUMENTO
                            " CASE WHEN a.co_emp=1 THEN "+
                            " CASE WHEN x.co_bod IN (6, 7) THEN 1039 ELSE " +                  //DIMULTI" 
                            " CASE WHEN x.co_bod IN (8, 10, 12, 21, 22, 23) THEN 603 ELSE "+   //CASTEK" 
                            " CASE WHEN x.co_bod IN (1, 15, 20, 9) THEN 3516 ELSE NULL "+      //TUVAL 9" +
                            " END END END " +
                            " ELSE " +
                            " CASE WHEN a.co_emp=2 THEN " +
                            " CASE WHEN x.co_bod IN (1, 4, 12, 21, 22, 23) then 446 ELSE "+    //CASTEK
                            " CASE WHEN x.co_bod IN (3, 15, 20, 10) THEN 2854 ELSE "+          //TUVAL 10
                            " CASE WHEN x.co_bod IN (8, 9) THEN 789 ELSE NULL "+               //DIMULTI
                            " END END END " +
                            " ELSE " +
                            " CASE WHEN a.co_emp=4 THEN " +
                            "  CASE WHEN x.co_bod IN (7, 9, 11, 21, 22, 23) THEN 498 ELSE "+    //CASTEK
                            "  CASE WHEN x.co_bod IN (2, 15, 20, 8) THEN 3117  ELSE "+          //TUVAL 8
                            "  CASE WHEN x.co_bod IN (1, 6) THEN 886  ELSE NULL "+              //DIMULTI
                            "  END END END END END " +
                            "  END  AS coclides, " +
                    
                            // NOMBRE DE LA EMPRESA.
                            "  CASE WHEN a.co_emp=1 THEN " +
                            "  CASE WHEN x.co_bod=7 THEN 'DIMULTI' ELSE " +
                            "  CASE WHEN x.co_bod=8 THEN 'CASTEK - QUITO' ELSE " +
                            "  CASE WHEN x.co_bod=10 THEN 'CASTEK - MANTA' ELSE " +
                            "  CASE WHEN x.co_bod=12 THEN 'CASTEK - SANTO DOMINGO' ELSE " +
                            "  CASE WHEN x.co_bod IN (1, 15) THEN 'TUVAL' ELSE " +
                            "  CASE WHEN x.co_bod=9 then 'Navisur' ELSE " +
                            "  CASE WHEN x.co_bod IN (6, 20, 21, 22, 23) then a.tx_nom "+       //EXPOSICION
                            "  END END END END END END END" +
                            "  else " +
                            "  CASE WHEN a.co_emp=2 THEN " +
                            "  CASE WHEN x.co_bod = 1 THEN 'CASTEK - QUITO ' ELSE " +
                            "  CASE WHEN x.co_bod IN (8, 20, 21, 22, 23) THEN a.tx_nom ELSE "+  //EXPOSICION
                            "  CASE WHEN x.co_bod IN (3, 15) THEN 'TUVAL ' ELSE " +
                            "  CASE WHEN x.co_bod = 10 THEN 'NAVISUR' ELSE " +
                            "  CASE WHEN x.co_bod=4 THEN 'CASTEK - MANTA ' ELSE " +
                            "  CASE WHEN x.co_bod=12 THEN 'CASTEK - SANTO DOMINGO ' ELSE " +
                            "  CASE WHEN x.co_bod=9 THEN 'DIMULTI' " +
                            "  END END END END END END END" +
                            "  ELSE " +
                            "  CASE WHEN a.co_emp=4 THEN " +
                            "  CASE WHEN x.co_bod=7 THEN 'CASTEK - QUITO ' ELSE " +
                            "  CASE WHEN x.co_bod IN (2, 15) then 'TUVAL ' else " +
                            "  CASE WHEN x.co_bod=9 THEN 'CASTEK - MANTA ' ELSE " +
                            "  CASE WHEN x.co_bod=11 THEN 'CASTEK - SANTO DOMINGO' ELSE " +
                            "  CASE WHEN x.co_bod = 1 THEN 'DIMULTI' ELSE " +
                            "  CASE WHEN x.co_bod= 8 THEN 'NAVISUR' ELSE " +
                            "  CASE WHEN x.co_bod in (6, 20, 21, 22, 23) THEN a.tx_nom"+       //EXPOSICION
                            "  END END END END END END END END END" +
                            "  END  AS EMPRESA, " +
                    
                            
                            // RUC DE LA EMPRESA.
                            "  CASE WHEN a.co_emp=1 THEN " +
                            "  CASE WHEN x.co_bod in (6, 7) THEN '0992372427001' ELSE "+                   //DIMULTI
                            "  CASE WHEN x.co_bod IN (8, 10, 12, 21, 22, 23) THEN '0992329432001' ELSE "+  //CASTEK
                            "  CASE WHEN x.co_bod IN (1, 15, 20, 9) THEN '0990281866001' ELSE NULL "+      //TUVAL
                            "  END END END " +
                            "  ELSE " +
                            "  CASE WHEN a.co_emp=2 then " +
                            "  CASE WHEN x.co_bod IN (1, 4, 12, 21, 22, 23) THEN '0992329432001' ELSE "+   //CASTEK
                            "  CASE WHEN x.co_bod IN (3, 15, 20, 10) THEN '0990281866001 ' ELSE "+         //TUVAL 10
                            "  CASE WHEN x.co_bod IN (8, 9) THEN '0992372427001' "+     //DIMULTI
                            "  END END END " +
                            "  ELSE " +
                            "  CASE WHEN a.co_emp=4 THEN " +
                            "  CASE WHEN x.co_bod IN (7, 9, 11, 21, 22, 23) THEN '0992329432001' ELSE "+   //CASTEK  
                            "  CASE WHEN x.co_bod IN (2, 15, 20, 8) THEN '0990281866001' else "+           //TUVAL
                            "  CASE WHEN x.co_bod in (1, 6) THEN '0992372427001' "+     //DIMULTI
                            "  END END END END END " +
                            "  END  AS RUCEMP " +
                    
                    
                            "  FROM (  SELECT distinct(co_bod) , co_emp " +
                            "  FROM tbm_detmovinv " +
                            "  WHERE co_emp=" + codEmp +
                            "  AND co_loc=" + codLoc +
                            "  AND co_tipdoc=" + codTipDoc +
                            "  AND co_doc=" + codDoc +
                            "  AND nd_can>0) AS x " +
                    
                            "  INNER JOIN tbm_bod AS a ON (a.co_emp=x.co_emp AND a.co_bod=x.co_bod)";*/
            
            
      strQry =" SELECT a.co_emp, x.co_bod, a.tx_nom, a.tx_dir, a.tx_tel, "
              + " case when a.co_emp=1 then "
              + " case when x.co_bod in (6, 7) then 1039 ELSE "                 // DIMULTI
              + " case when x.co_bod in (8, 10, 12, 21, 22, 23,28) then 603 ELSE " // CASTEK
              + " case when x.co_bod in (1, 15, 20) then 3516 ELSE null "       // TUVAL
              + " END END END "
              + " else "
              + " case when a.co_emp=2 then "
              + " case when x.co_bod IN (1, 4, 12, 21, 22, 23,28) then 446 ELSE "  // CASTEK
              + " case when x.co_bod IN (3, 15, 20) then 2854 else "            // TUVAL
              + " case when x.co_bod in (8, 9) then 789 else null "             // DIMULTI
              + " END END end "
              + " else "
              + " case when a.co_emp=4 then "
              + " case when x.co_bod IN (7, 9, 11, 21, 22, 23,28) then 498 ELSE "  // CASTEK
              + " case when x.co_bod IN (2, 15, 20) then 3117  else "           // TUVAL
              + " case when x.co_bod in (1, 6) then 886  else null "            // DIMULTI
              + " END END END END END "
              + " END  AS coclides, "
              + " case when a.co_emp=1 then "
              + " case when x.co_bod=7 then 'DIMULTI' ELSE " 
              + " case when x.co_bod=8 then 'CASTEK - QUITO' ELSE "
              + " case when x.co_bod=10 then 'CASTEK - MANTA' ELSE "
              + " case when x.co_bod=12 then 'CASTEK - SANTO DOMINGO' ELSE "
              + " case when x.co_bod=28 then 'CASTEK - CUENCA' ELSE "
              + " case when x.co_bod in (1, 15) then 'TUVAL' ELSE "
              + " case when x.co_bod in (6, 20, 21, 22, 23,28) then a.tx_nom "     // EXPOSICION
              + " END END END END END END END "
              + " else "
              + " case when a.co_emp=2 then "
              + " case when x.co_bod = 1 then 'CASTEK - QUITO ' ELSE "
              + " case when x.co_bod in (8, 20, 21, 22, 23) then a.tx_nom ELSE " // EXPOSICION
              + " case when x.co_bod in (3, 15) then 'TUVAL ' else "
              + " case when x.co_bod=4 then 'CASTEK - MANTA ' ELSE "
              + " case when x.co_bod=12 then 'CASTEK - SANTO DOMINGO ' ELSE "
              + " case when x.co_bod=28 then 'CASTEK - CUENCA ' ELSE "              
              + " case when x.co_bod=9 then 'DIMULTI' "
              + " END END END END END END END"
              + " else "
              + " case when a.co_emp=4 then "
              + " case when x.co_bod=7 then 'CASTEK - QUITO ' ELSE "
              + " case when x.co_bod IN (2, 15) then 'TUVAL ' else "
              + " case when x.co_bod=9 then 'CASTEK - MANTA ' ELSE "
              + " case when x.co_bod=11 then 'CASTEK - SANTO DOMINGO' ELSE "
              + " case when x.co_bod=28 then 'CASTEK - CUENCA' ELSE "
              + " case when x.co_bod = 1 then 'DIMULTI' ELSE "
              + " case when x.co_bod in (6, 20, 21, 22, 23) then a.tx_nom "     // EXPOSICION    
              + " END END END END END END END END "
              + " END END AS EMPRESA, "
              + " case when a.co_emp=1 then "
              + " case when x.co_bod in (6, 7) then '0992372427001' ELSE "                   // DIMULTI
              + " case when x.co_bod IN (8, 10, 12, 21, 22, 23,28) then '0992329432001' ELSE "  // CASTEK
              + " case when x.co_bod IN (1, 15, 20) then '0990281866001' ELSE NULL "         // TUVAL
              + " END END END "
              + " else "
              + " case when a.co_emp=2 then "
              + " case when x.co_bod IN (1, 4, 12, 21, 22, 23) then '0992329432001' ELSE "   // CASTEK
              + " case when x.co_bod in (3, 15, 20) then '0990281866001 ' else "             // TUVAL
              + " case when x.co_bod in (8, 9) then '0992372427001' "                        // DIMULTI
              + " END END END "
              + " else "
              + " case when a.co_emp=4 then "
              + " case when x.co_bod IN (7, 9, 11, 21, 22, 23,28) then '0992329432001' ELSE "   // CASTEK  
              + " case when x.co_bod IN (2, 15, 20) then '0990281866001' else "              // TUVAL
              + " case when x.co_bod in (1, 6) then '0992372427001' "                        // DIMULTI
              + " END END END END END "
              + " END  AS RUCEMP "
              + " FROM (  SELECT distinct(co_bod) , co_emp "
              + " FROM tbm_detmovinv "
              + " WHERE co_emp="+codEmp
              + " and co_loc="+codLoc
              + " and co_tipdoc="+codTipDoc
              + " and co_doc="+codDoc
              + " and nd_can > 0 ) as x "
              + " INNER JOIN tbm_bod as a on (a.co_emp=x.co_emp and a.co_bod=x.co_bod) ";            
            
           stmDocTra= conn.createStatement();
           rsODTra=stmDocTra.executeQuery(strQry);                    
            
        }catch(Exception ex){
            ex.printStackTrace();
            rsODTra=null;
        }
        return rsODTra;
    }    

    
    
    /**
     * Metodo que devuelve los documentos e informacion de los documentos de transferencia, para proceder a generar la OD automatica.
     * @param conn conexion de acceso a Datos.
     * @param codEmp codigo de la empresa.
     * @param codLoc codigo del local.
     * @param codTipDoc codigo del tipo de documento
     * @param codDoc codigo del documento.
     * @return ResultSet .
     */
    public ResultSet obtenerDocTransGenODTranCuenca(Connection conn, int codEmp, int codLoc, int codTipDoc, int codDoc){
        String strQry="";
        Statement stmDocTra=null;
        ResultSet rsODTra=null;        
        try{
            strQry=" SELECT a.co_emp, x.co_bod, a.tx_nom, a.tx_dir, a.tx_tel, " +
                    
                            //CODIGO DEL DOCUMENTO
                            " CASE WHEN a.co_emp=1 THEN "+
                            " CASE WHEN x.co_bod IN (6, 7) THEN 1039 ELSE " +                  //DIMULTI" 
                            " CASE WHEN x.co_bod IN (8, 10, 12, 21, 22, 23) THEN 603 ELSE "+   //CASTEK" 
                            " CASE WHEN x.co_bod IN (1, 15, 20, 9) THEN 3516 ELSE NULL "+      //TUVAL 9" +
                            " END END END " +
                            " ELSE " +
                            " CASE WHEN a.co_emp=2 THEN " +
                            " CASE WHEN x.co_bod IN (1, 4, 12, 21, 22, 23) then 446 ELSE "+    //CASTEK
                            " CASE WHEN x.co_bod IN (3, 15, 20, 10) THEN 2854 ELSE "+          //TUVAL 10
                            " CASE WHEN x.co_bod IN (8, 9) THEN 789 ELSE NULL "+               //DIMULTI
                            " END END END " +
                            " ELSE " +
                            " CASE WHEN a.co_emp=4 THEN " +
                            "  CASE WHEN x.co_bod IN (7, 9, 11, 21, 22, 23) THEN 498 ELSE "+    //CASTEK
                            "  CASE WHEN x.co_bod IN (2, 15, 20, 8) THEN 3117  ELSE "+          //TUVAL 8
                            "  CASE WHEN x.co_bod IN (1, 6) THEN 886  ELSE NULL "+              //DIMULTI
                            "  END END END END END " +
                            "  END  AS coclides, " +
                    
                            // NOMBRE DE LA EMPRESA.
                            "  CASE WHEN a.co_emp=1 THEN " +
                            "  CASE WHEN x.co_bod=7 THEN 'DIMULTI' ELSE " +
                            "  CASE WHEN x.co_bod=8 THEN 'CASTEK - QUITO' ELSE " +
                            "  CASE WHEN x.co_bod=10 THEN 'CASTEK - MANTA' ELSE " +
                            "  CASE WHEN x.co_bod=12 THEN 'CASTEK - SANTO DOMINGO' ELSE " +
                            "  CASE WHEN x.co_bod IN (1, 15) THEN 'TUVAL' ELSE " +
                            "  CASE WHEN x.co_bod=9 then 'Navisur' ELSE " +
                            "  CASE WHEN x.co_bod IN (6, 20, 21, 22, 23) then a.tx_nom "+       //EXPOSICION
                            "  END END END END END END END" +
                            "  else " +
                            "  CASE WHEN a.co_emp=2 THEN " +
                            "  CASE WHEN x.co_bod = 1 THEN 'CASTEK - QUITO ' ELSE " +
                            "  CASE WHEN x.co_bod IN (8, 20, 21, 22, 23) THEN a.tx_nom ELSE "+  //EXPOSICION
                            "  CASE WHEN x.co_bod IN (3, 15) THEN 'TUVAL ' ELSE " +
                            "  CASE WHEN x.co_bod = 10 THEN 'NAVISUR' ELSE " +
                            "  CASE WHEN x.co_bod=4 THEN 'CASTEK - MANTA ' ELSE " +
                            "  CASE WHEN x.co_bod=12 THEN 'CASTEK - SANTO DOMINGO ' ELSE " +
                            "  CASE WHEN x.co_bod=9 THEN 'DIMULTI' " +
                            "  END END END END END END END" +
                            "  ELSE " +
                            "  CASE WHEN a.co_emp=4 THEN " +
                            "  CASE WHEN x.co_bod=7 THEN 'CASTEK - QUITO ' ELSE " +
                            "  CASE WHEN x.co_bod IN (2, 15) then 'TUVAL ' else " +
                            "  CASE WHEN x.co_bod=9 THEN 'CASTEK - MANTA ' ELSE " +
                            "  CASE WHEN x.co_bod=11 THEN 'CASTEK - SANTO DOMINGO' ELSE " +
                            "  CASE WHEN x.co_bod = 1 THEN 'DIMULTI' ELSE " +
                            "  CASE WHEN x.co_bod= 8 THEN 'NAVISUR' ELSE " +
                            "  CASE WHEN x.co_bod in (6, 20, 21, 22, 23) THEN a.tx_nom"+       //EXPOSICION
                            "  END END END END END END END END END" +
                            "  END  AS EMPRESA, " +
                    
                            
                            // RUC DE LA EMPRESA.
                            "  CASE WHEN a.co_emp=1 THEN " +
                            "  CASE WHEN x.co_bod in (6, 7) THEN '0992372427001' ELSE "+                   //DIMULTI
                            "  CASE WHEN x.co_bod IN (8, 10, 12, 21, 22, 23) THEN '0992329432001' ELSE "+  //CASTEK
                            "  CASE WHEN x.co_bod IN (1, 15, 20, 9) THEN '0990281866001' ELSE NULL "+      //TUVAL
                            "  END END END " +
                            "  ELSE " +
                            "  CASE WHEN a.co_emp=2 then " +
                            "  CASE WHEN x.co_bod IN (1, 4, 12, 21, 22, 23) THEN '0992329432001' ELSE "+   //CASTEK
                            "  CASE WHEN x.co_bod IN (3, 15, 20, 10) THEN '0990281866001 ' ELSE "+         //TUVAL 10
                            "  CASE WHEN x.co_bod IN (8, 9) THEN '0992372427001' "+     //DIMULTI
                            "  END END END " +
                            "  ELSE " +
                            "  CASE WHEN a.co_emp=4 THEN " +
                            "  CASE WHEN x.co_bod IN (7, 9, 11, 21, 22, 23) THEN '0992329432001' ELSE "+   //CASTEK  
                            "  CASE WHEN x.co_bod IN (2, 15, 20, 8) THEN '0990281866001' else "+           //TUVAL
                            "  CASE WHEN x.co_bod in (1, 6) THEN '0992372427001' "+     //DIMULTI
                            "  END END END END END " +
                            "  END  AS RUCEMP " +
                    
                    
                            "  FROM (  SELECT distinct(co_bod) , co_emp " +
                            "  FROM tbm_detmovinv " +
                            "  WHERE co_emp=" + codEmp +
                            "  AND co_loc=" + codLoc +
                            "  AND co_tipdoc=" + codTipDoc +
                            "  AND co_doc=" + codDoc +
                            "  AND nd_can>0) AS x " +
                    
                            "  INNER JOIN tbm_bod AS a ON (a.co_emp=x.co_emp AND a.co_bod=x.co_bod)";
            
           stmDocTra= conn.createStatement();
           rsODTra=stmDocTra.executeQuery(strQry);                    
            
        }catch(Exception ex){
            ex.printStackTrace();
            rsODTra=null;
        }
        return rsODTra;
    }
    
    
    /**
     * Metodo que devuelve los documentos e informacion de los documentos de transferencia, para proceder a generar la OD automatica(IMPORTACIONES).
     * @param conn conexion de acceso a la base de Datos.
     * @param codEmp codigo de la empresa.
     * @param codLoc codigo del local.
     * @param codTipDoc codigo del tipo de documento.
     * @param codDoc codigo del documento.
     * @return ResultSet
     */
    public ResultSet obtenerDocTransGenODTranImportaciones(Connection conn, int codEmp, int codLoc, int codTipDoc, int codDoc){

        String strQry="";
        Statement stmDocTra=null;
        ResultSet rsODTra=null;        
        try{
            strQry="";
            strQry+="SELECT a.co_emp, x.co_bod, a.tx_nom, a.tx_dir, a.tx_tel, ";
            strQry+=" CASE WHEN a.co_emp=1 THEN";
            strQry+="	   CASE WHEN x.co_bod in (6, 7) THEN 1039";// DIMULTI
            strQry+="		WHEN x.co_bod in (8, 10, 12, 21, 22, 23) THEN 603";// CASTEK
            strQry+="		WHEN x.co_bod in (1, 15, 20, 3, 4) THEN 3516";//// TUVAL
            strQry+="		ELSE NULL";
            strQry+="	   END";
            strQry+=" WHEN a.co_emp=2 THEN";
            strQry+="	   CASE WHEN x.co_bod IN (1, 4, 12, 21, 22, 23, 5, 6) THEN 446";// CASTEK
            strQry+="		WHEN x.co_bod IN (3, 15, 20) THEN 2854";// TUVAL
            strQry+="		WHEN x.co_bod in (8, 9) THEN 789";// DIMULTI
            strQry+="		ELSE NULL";
            strQry+="	   END";
            strQry+=" WHEN a.co_emp=4 THEN";
            strQry+="	   CASE WHEN x.co_bod IN (7, 9, 11, 21, 22, 23) THEN 498";// CASTEK
            strQry+="		WHEN x.co_bod IN (2, 15, 20) THEN 3117";// TUVAL
            strQry+="		WHEN x.co_bod in (1, 6, 3, 4) THEN 886";// DIMULTI
            strQry+="		ELSE NULL";
            strQry+="	   END";
            strQry+=" END  AS coclides,";
            strQry+=" CASE WHEN a.co_emp=1 THEN";
            strQry+=" 	  CASE WHEN x.co_bod=7 THEN 'DIMULTI'";
            strQry+=" 	       WHEN x.co_bod=8 THEN 'CASTEK - QUITO'";
            strQry+="	       WHEN x.co_bod=10 THEN 'CASTEK - MANTA'";
            strQry+="	       WHEN x.co_bod=12 THEN 'CASTEK - SANTO DOMINGO'";
            strQry+="	       WHEN x.co_bod in (1, 15, 3, 4) THEN 'TUVAL'";
            strQry+="	       WHEN x.co_bod in (6, 20, 21, 22, 23) THEN a.tx_nom";// EXPOSICION
            strQry+="	  END";
            strQry+=" WHEN a.co_emp=2 THEN";
            strQry+="	  CASE WHEN x.co_bod in (1, 5, 6) THEN 'CASTEK - QUITO '";
            strQry+="	       WHEN x.co_bod in (8, 20, 21, 22, 23) THEN a.tx_nom";// EXPOSICION
            strQry+="	       WHEN x.co_bod in (3, 15) THEN 'TUVAL '";
            strQry+="	       WHEN x.co_bod=4 THEN 'CASTEK - MANTA '";
            strQry+="	       WHEN x.co_bod=12 THEN 'CASTEK - SANTO DOMINGO '";
            strQry+=" 	       WHEN x.co_bod=9 THEN 'DIMULTI'";
            strQry+="	  END";
            strQry+=" WHEN a.co_emp=4 THEN";
            strQry+="	  CASE WHEN x.co_bod=7 THEN 'CASTEK - QUITO '";
            strQry+="	       WHEN x.co_bod IN (2, 15) THEN 'TUVAL '";
            strQry+="	       WHEN x.co_bod=9 THEN 'CASTEK - MANTA '";
            strQry+="	       WHEN x.co_bod=11 THEN 'CASTEK - SANTO DOMINGO'";
            strQry+=" 	       WHEN x.co_bod in (1, 3, 4) THEN 'DIMULTI'";
            strQry+="	       WHEN x.co_bod in (6, 20, 21, 22, 23) THEN a.tx_nom";// EXPOSICION
            strQry+="	  END";
            strQry+=" END  AS EMPRESA,";
            strQry+=" CASE WHEN a.co_emp=1 THEN";
            strQry+="	  CASE WHEN x.co_bod in (6, 7) THEN '0992372427001'";//// DIMULTI
            strQry+="	       WHEN x.co_bod IN (8, 10, 12, 21, 22, 23) THEN '0992329432001'";// CASTEK
            strQry+="	       WHEN x.co_bod IN (1, 15, 20, 3, 4) THEN '0990281866001'";
            strQry+=" 	       ELSE NULL";// TUVAL
            strQry+="	  END";
            strQry+=" WHEN a.co_emp=2 THEN";
            strQry+="	  CASE WHEN x.co_bod IN (1, 4, 12, 21, 22, 23, 5, 6) THEN '0992329432001'";// CASTEK
            strQry+="	       WHEN x.co_bod in (3, 15, 20) THEN '0990281866001 '";// TUVAL
            strQry+="	       WHEN x.co_bod in (8, 9) THEN '0992372427001'";// DIMULTI
            strQry+="	  END";
            strQry+=" WHEN a.co_emp=4 THEN";
            strQry+="	 CASE WHEN x.co_bod IN (7, 9, 11, 21, 22, 23) THEN '0992329432001'";// CASTEK
            strQry+="	      WHEN x.co_bod IN (2, 15, 20) THEN '0990281866001'";// TUVAL
            strQry+="	      WHEN x.co_bod in (1, 6, 3, 4) THEN '0992372427001'";// DIMULTI
            strQry+="	 END";
            strQry+=" END  AS RUCEMP";
            strQry+=" FROM (";
            strQry+="       SELECT distinct(co_bod) , co_emp FROM tbm_detmovinv";
            strQry+="       WHERE co_emp="+codEmp + "  AND co_loc="+codLoc + "";
            strQry+="       AND co_tipdoc="+codTipDoc + " AND co_doc="+codDoc + " and nd_can > 0";
            strQry+="      ) as x";
            strQry+=" INNER JOIN tbm_bod as a on (a.co_emp=x.co_emp and a.co_bod=x.co_bod) ;";
            stmDocTra= conn.createStatement();
            rsODTra=stmDocTra.executeQuery(strQry);
                    
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return rsODTra;        
    }
    
        
    /**
     * Metodo que devuelve los documentos e informacion de los documentos de transferencia, para proceder a generar la OD automatica(INMACONSA).
     * @param conn conexion a base de datos.
     * @param codEmp codigo de la empresa.
     * @param codLoc codigo del local.
     * @param codTipDoc codigo del tipo de documento.
     * @param codDoc codigo del documento.
     * @return ResultSet.
     */
    public ResultSet obtenerDocTransGenODTranInmaconsa(Connection conn, int codEmp, int codLoc, int codTipDoc, int codDoc){
        String strQry="";
        Statement stmDocTra=null;
        ResultSet rsODTra=null;        
        try{
            strQry=" SELECT a.co_emp, x.co_bod, a.tx_nom, a.tx_dir, a.tx_tel, "
                    + " case when a.co_emp=1 then "
                    + " case when x.co_bod in (6, 7) then 1039 ELSE "                   //DIMULTI
                    + " case when x.co_bod in (8, 10, 12, 21, 22, 23,28 ) then 603 ELSE "   //CASTEK
                    + " case when x.co_bod in (1, 15, 20, 9,31) then 3516 ELSE null "      //TUVAL //9
                    + " END END END "
                    + " else "
                    + " case when a.co_emp=2 then "
                    + " case when x.co_bod IN (1, 4, 12, 21, 22, 23,28) then 446 ELSE "    //CASTEK
                    + " case when x.co_bod IN (3, 15, 20, 10,31) then 2854 else "          //TUVAL//10
                    + " case when x.co_bod in (8, 9) then 789 else null "               //DIMULTI
                    + " END END end "
                    + " else "
                    + " case when a.co_emp=4 then "
                    + " case when x.co_bod IN (7, 9, 11, 21, 22, 23,28) then 498 ELSE "    //CASTEK
                    + " case when x.co_bod IN (2, 15, 20, 8,31) then 3117  else "          //TUVAL//8
                    + " case when x.co_bod in (1, 6) then 886  else null "              //DIMULTI
                    + " END END END END END "
                    + " END  AS coclides, "
                    + " case when a.co_emp=1 then "
                    + " case when x.co_bod=7 then 'DIMULTI' ELSE " 
                    + " case when x.co_bod=8 then 'CASTEK - QUITO' ELSE "
                    + " case when x.co_bod=10 then 'CASTEK - MANTA' ELSE "
                    + " case when x.co_bod=12 then 'CASTEK - SANTO DOMINGO' ELSE "
                    + " case when x.co_bod=28 then 'CASTEK - CUENCA' ELSE "
                    + " case when x.co_bod in (1, 15,31) then 'TUVAL' ELSE "
                    + " case when x.co_bod=9 then 'Navisur' ELSE "
                    + " case when x.co_bod in (6, 20, 21, 22, 23) then a.tx_nom "       //EXPOSICION
                    + " END END END END END END END END"
                    + " else "
                    + " case when a.co_emp=2 then "
                    + " case when x.co_bod = 1 then 'CASTEK - QUITO ' ELSE "
                    + " case when x.co_bod in (8, 20, 21, 22, 23,28) then a.tx_nom ELSE "  //EXPOSICION
                    + " case when x.co_bod in (3, 15,31) then 'TUVAL ' else "
                    + " case when x.co_bod = 10 then 'NAVISUR' ELSE "//--
                    + " case when x.co_bod=4 then 'CASTEK - MANTA ' ELSE "
                    + " case when x.co_bod=12 then 'CASTEK - SANTO DOMINGO ' ELSE "
                    + " case when x.co_bod=28 then 'CASTEK - CUENCA ' ELSE "
                    + " case when x.co_bod=9 then 'DIMULTI' "
                    + " END END END END END END END END"
                    + " else "
                    + " case when a.co_emp=4 then "
                    + " case when x.co_bod=7 then 'CASTEK - QUITO ' ELSE "
                    + " case when x.co_bod IN (2, 15,31) then 'TUVAL ' else "
                    + " case when x.co_bod=9 then 'CASTEK - MANTA ' ELSE "
                    + " case when x.co_bod=11 then 'CASTEK - SANTO DOMINGO' ELSE "
                    + " case when x.co_bod=28 then 'CASTEK - CUENCA' ELSE "
                    + " case when x.co_bod = 1 then 'DIMULTI' ELSE "
                    + " case when x.co_bod= 8 then 'NAVISUR' ELSE "//--
                    + " case when x.co_bod in (6, 20, 21, 22, 23) then a.tx_nom "       //EXPOSICION    
                    + " END END END END END END END END END END"
                    + " END  AS EMPRESA, "
                    + " case when a.co_emp=1 then "
                    + " case when x.co_bod in (6, 7) then '0992372427001' ELSE "                   //DIMULTI
                    + " case when x.co_bod IN (8, 10, 12, 21, 22, 23,28) then '0992329432001' ELSE "  //CASTEK
                    + " case when x.co_bod IN (1, 15, 20, 9,31) then '0990281866001' ELSE NULL "      //TUVAL//9
                    + " END END END "
                    + " else "
                    + " case when a.co_emp=2 then "
                    + " case when x.co_bod IN (1, 4, 12, 21, 22, 23,28) then '0992329432001' ELSE "   //CASTEK
                    + " case when x.co_bod in (3, 15, 20, 10,31) then '0990281866001 ' else "         //TUVAL//10
                    + " case when x.co_bod in (8, 9) then '0992372427001' "                        //DIMULTI
                    + " END END END "
                    + " else "
                    + " case when a.co_emp=4 then "
                    + " case when x.co_bod IN (7, 9, 11, 21, 22, 23,28) then '0992329432001' ELSE "   //CASTEK  
                    + " case when x.co_bod IN (2, 15, 20, 8,31) then '0990281866001' else "           //TUVAL//8
                    + " case when x.co_bod in (1, 6) then '0992372427001' "                        //DIMULTI
                    + " END END END END END "
                    + " END  AS RUCEMP "
                    + " FROM (  SELECT distinct(co_bod) , co_emp "
                    + " FROM tbm_detmovinv "
                    + " WHERE co_emp=" + codEmp
                    + " and co_loc=" + codLoc
                    + " and co_tipdoc=" + codTipDoc
                    + " and co_doc=" + codDoc
                    + " and nd_can>0) as x "
                    + " INNER JOIN tbm_bod as a on (a.co_emp=x.co_emp and a.co_bod=x.co_bod) ";
            
           stmDocTra= conn.createStatement();
           rsODTra=stmDocTra.executeQuery(strQry);                    
            
        }catch(Exception ex){
            ex.printStackTrace();
            rsODTra=null;
        }
        return rsODTra;
    }     
    
    
    /**
     * Metodo que verifica bodegas para realizar TRANSFERENCIAS IMPORTACIONES.
     * @param conx conexion acceso a datos.
     * @param intCodEmp codigo de la empresa.
     * @param intCodLoc codigo del local.
     * @param intCodTipDoc codigo del tipo de documento.
     * @param intCodDoc codigo del documento.
     * @param strCodBods codigo de las bodegas relacionadas.
     * @return 
     */
    public boolean verificaBodODImportaciones(Connection conx, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strCodBods){
        
        boolean booRet=false;
        Statement stmRe=null;
        ResultSet rsBodOD=null;
        String strSqlUpd="";
        String strSql= " SELECT * FROM ( "
            + " SELECT distinct(co_bod), co_emp FROM tbm_detmovinv "
            + " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and nd_can > 0 "
            + " ) as x "
            + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=x.co_emp AND a6.co_bod=x.co_bod) "
            + " AND ( a6.co_empGrp=0 AND a6.co_bodGrp in ("+strCodBods+") ) ";//se agrego el 4
        try{
            stmRe=conx.createStatement();
            rsBodOD=stmRe.executeQuery(strSql);
            if(rsBodOD.next()){
                booRet=true;
            }else{
                strSqlUpd="UPDATE tbm_cabmovinv SET st_creguirem='S'  WHERE co_emp="+intCodEmp+" " +
                " and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc;
                stmRe.executeUpdate(strSqlUpd);
            }
        
        }catch(Exception ex){
            ex.printStackTrace();            
        }finally{
            try{
                rsBodOD.close();
                rsBodOD=null;
                stmRe.close();
                stmRe=null;            
            }catch(Exception ex){
                ex.printStackTrace();
            }        
        }
        return booRet;
    }
    
    
    /**
     * Metodo que verifica bodegas para realizar TRANSFERENCIAS INMACONSA.
     * @param conx Conexion acceso a datos.
     * @param intCodEmp Codigo de Empresa.
     * @param intCodLoc Codigo de Local.
     * @param intCodTipDoc Codigo del Tipo de Documento.
     * @param intCodDoc Codigo del Documento.
     * @param strCodBods String de codigos de bodegas relacionadas separadas por comas.
     * @return 
     */
    public boolean verificaBodODInmaconsa(Connection conx, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strCodBods){
        
        boolean booRet=false;
        Statement stmRe=null;
        ResultSet rsBodOD=null;
        String strSqlUpd="";
        String strSql= " SELECT * FROM ( "
            + " SELECT distinct(co_bod), co_emp FROM tbm_detmovinv "
            + " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and nd_can > 0 "
            + " ) as x "
            + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=x.co_emp AND a6.co_bod=x.co_bod) "
            + " AND ( a6.co_empGrp=0 AND a6.co_bodGrp in ("+strCodBods+") ) ";//se agrego el 4
        try{
            stmRe=conx.createStatement();
            rsBodOD=stmRe.executeQuery(strSql);
            if(rsBodOD.next()){
                booRet=true;
            }else{
                strSqlUpd="UPDATE tbm_cabmovinv SET st_creguirem='S'  WHERE co_emp="+intCodEmp+" " +
                " and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc;
                stmRe.executeUpdate(strSqlUpd);
            }
        
        }catch(Exception ex){
            ex.printStackTrace();            
        }finally{
            try{
                rsBodOD.close();
                rsBodOD=null;
                stmRe.close();
                stmRe=null;            
            }catch(Exception ex){
                ex.printStackTrace();
            }        
        }
        return booRet;
    }
    
    
    /**
     * Metodo que verifica las bodegas para realizar TRANSFERENCIAS CASTEK CUENCA.
     * @param conx Conexion de acceso a datos.
     * @param intCodEmp Codigo de la empresa.
     * @param intCodLoc Codigo del local.
     * @param intCodTipDoc Codigo del tipo de documento.
     * @param intCodDoc Codigo del Documento.
     * @param strCodBods codigo de las bodegas relacionadas.
     * @return 
     */
    public boolean verificaBodODCuenca(Connection conx, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strCodBods){
        
        boolean booRet=false;
        Statement stmRe=null;
        ResultSet rsBodOD=null;
        String strSqlUpd="";
        String strSql= " SELECT * FROM ( "
            + " SELECT distinct(co_bod), co_emp FROM tbm_detmovinv "
            + " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and nd_can > 0 "
            + " ) as x "
            + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=x.co_emp AND a6.co_bod=x.co_bod) "
            + " AND ( a6.co_empGrp=0 AND a6.co_bodGrp in ("+strCodBods+") ) ";//se agrego el 4
        try{
            stmRe=conx.createStatement();
            rsBodOD=stmRe.executeQuery(strSql);
            if(rsBodOD.next()){
                booRet=true;
            }else{
                strSqlUpd="UPDATE tbm_cabmovinv SET st_creguirem='S'  WHERE co_emp="+intCodEmp+" " +
                " and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc;
                stmRe.executeUpdate(strSqlUpd);
            }
        
        }catch(Exception ex){
            ex.printStackTrace();            
        }finally{
            try{
                rsBodOD.close();
                rsBodOD=null;
                stmRe.close();
                stmRe=null;            
            }catch(Exception ex){
                ex.printStackTrace();
            }        
        }
        return booRet;
    }    
    
    
/**
     * Metodo que isnerta la cabecera de OD por documentos que sean de transferencia (INMACONSA).
     * @param cnx Conexion de acceso a datos.
     * @param intCodEmp codigo de la empresa GUIA especifico para INMACONSA.
     * @param intCodLoc codigo del local GUIA especifico para INMACONSA.
     * @param intCodTipDocGuia Tipo del documento.
     * @param intCodDocGuia Codigo del documento Guia.
     * @param strRucCli Ruc del cliente.
     * @param strNomCli Nombre del Cliente.
     * @param strDirCli Direccion del cliente.
     * @param strTelCli Telefono del cliente.
     * @param intNumGuiaRem Numero de la Guia Remision.
     * @param intCodBod Codigo de la Bodega.
     * @param strDirBod Direccion de la Bodega.
     * @param strDocRel documento relacionado.
     * @param strcoclides codigo cliente.
     * @param intCodBodDes codigo de la bodega destino (donde llega la mercaderia).
     * @param intCodTipDoc codigo del tipo de documento para actualizar.
     * @param intCodDoc codigo del documento para actualizar.
     * @param intCodEmpInm codigo de la empresa Inmaconsa parte del PK.
     * @param intCodLocInm codigo del local Inmaconsa parte del PK.
     * @return 
     */
    public boolean insertCabODxTranInm(Connection cnx, int intCodEmp, int intCodLoc, int intCodTipDocGuia, 
            int intCodDocGuia, String strRucCli, String strNomCli, String strDirCli,  String strTelCli,
            int intNumGuiaRem, int intCodBod, String strDirBod, String strDocRel,
            String strcoclides, int intCodBodDes, int intCodTipDoc, int intCodDoc,int intCodEmpInm,int intCodLocInm){
        
        boolean booRet=false;
        Statement stmInsCabOD=null;
        
        try{
          String strSql="INSERT INTO tbm_cabguirem( co_emp, co_loc, co_tipdoc, co_doc, fe_doc ,fe_orddes "+
          " , ne_numorddes, ne_numdoc,  tx_rucclides, tx_nomclides,  tx_dirclides, tx_telclides, tx_ciuclides, st_imp " +
          " ,st_reg, fe_ing, co_usring ,st_conInv, fe_initra, fe_tertra, tx_coming, st_regrep, co_ptopar, tx_ptopar " +
          " , tx_vehret, tx_choret ,tx_datdocoriguirem , co_clides, co_ptodes, st_imporddes  ) "+
          " VALUES( "+intCodEmpInm+", "+intCodLocInm+", "+intCodTipDocGuia+", "+intCodDocGuia+", current_date, current_date  "+  // '"+strFecDoc+"' " +
          " ,"+intNumGuiaRem+", 0,   '"+strRucCli+ "', '"+strNomCli+"', '"+strDirCli+"' " +
          " ,'"+strTelCli+"', '', 'N' ,'A', current_timestamp , 1 " +
          " ,'P', current_date, current_date, '', 'I' , "+intCodBod+", '"+strDirBod+"'  " +
          //" , '', '', '"+strDocRel+"', "+strcoclides+", "+intCodBodDes+", 'S'  )";
		  " , '', '', '"+strDocRel+"', "+strcoclides+", "+intCodBodDes+", 'N'  )";

          strSql+=" ; UPDATE tbm_cabmovinv SET st_creguirem='S', ne_numorddes="+intNumGuiaRem+"  WHERE co_emp="+intCodEmp+" " +
          " and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc; 
          
          stmInsCabOD=cnx.createStatement();
          stmInsCabOD.execute(strSql);
          stmInsCabOD.close();
          stmInsCabOD=null;
          booRet=true;
        }catch(Exception ex){
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(null, ex);
        }
        return booRet;   
    }
    
    
    
    /**
     * Metodo que inserta las OD por transferencias entre companias (INMACONSA).
     * @param cnx conexion acceso a datos.
     * @param rsDetODxTra resultset de Ordenes de Despacho.
     * @param intCodEmpInm codigo de la empresa .
     * @param intCodLocInm codigo del local .
     * @param intCodEmp codigo de la empresa movimiento.
     * @param intCodLoc codigo del local movimiento.
     * @param intCodTipDoc codigo del tipo de documento.
     * @param intCodDoc codigo del documento.
     * @param intTipDocGuia codigo del tipo de documento guia.
     * @param intCodDocGuia codigo del documento guia remision.
     * @return 
     */
    public boolean insertarDetODxTranInm(Connection cnx, ResultSet rsDetODxTra, int intCodEmpInm, int intCodLocInm,int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intTipDocGuia, int intCodDocGuia){
        boolean booRet=false;
        StringBuffer stbinsGuia=new StringBuffer("");
        int intReg=0;
        String strSql="";
        Statement stmLoc=null;
        ZafUtil objUtil=new ZafUtil();
        
        int intEstIns=0;
        try{
            while(rsDetODxTra.next()){
                intReg++;
                if(intEstIns > 0)
                    stbinsGuia.append(" UNION ALL ");
                
                stbinsGuia.append("SELECT "+intCodEmpInm+","+intCodLocInm+","+intTipDocGuia+", "+intCodDocGuia+" " +
                " ,"+intReg+", "+intCodEmp+","+intCodLoc+","+intCodTipDoc+","+intCodDoc+","+rsDetODxTra.getInt("co_reg")+" " +
                " , "+rsDetODxTra.getString("co_itm")+", '"+rsDetODxTra.getString("tx_codalt")+"' " +
                //" ,'"+rsDetODxTra.getString("tx_nomitm")+"','"+rsDetODxTra.getString("tx_unimed")+"', abs("+rsDetODxTra.getString("nd_can")+") " +
                " ,"+objUtil.codificar(rsDetODxTra.getString("tx_nomitm"))+",'"+rsDetODxTra.getString("tx_unimed")+"', abs("+rsDetODxTra.getString("nd_can")+") " +                        
                " ,'I', '', '"+rsDetODxTra.getString("st_meringegrfisbod")+"' ");                
                intEstIns=1;
            }
            rsDetODxTra.close();
            rsDetODxTra=null;
            
            if(intEstIns==1){
                strSql="INSERT INTO tbm_detguirem (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel," +
                " co_tipdocrel, co_docrel, co_regrel, co_itm, tx_codalt,  tx_nomitm, tx_unimed, nd_can, st_regrep, tx_obs1, st_meregrfisbod ) "+stbinsGuia.toString();
                stmLoc=cnx.createStatement();
                stmLoc.execute(strSql);
                booRet=true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(null, ex);
        }
        return booRet;    
    }
    
    public ResultSet obtenerRegRelPreTra(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
       ResultSet rsReg=null;
       Statement stm=null;
       String strQry="";       
       
      /* strQry=" select i.co_emprel, i.co_locrel, i.co_tipdocrel, "+
       " i.co_docrel, m.co_bod "+
       " from( select co_emprel, co_locrel, co_tipdocrel, "+
             " co_docrel, co_regrel, co_emp, co_loc, co_tipdoc, co_doc, co_reg"+
             " from tbr_detmovinv "+
             " where co_emp=" +intCodEmp+
             " and co_loc=" +intCodLoc+
             " and co_tipdoc=" +intCodTipDoc+
             " and co_doc=" +intCodDoc+
             " )i,  tbm_detmovinv m "+
             " where i.co_emprel=m.co_emp "+
             " and i.co_locrel=m.co_loc"+
             " and i.co_tipdocrel=m.co_tipdoc"+
             " and i.co_docrel=m.co_doc "+
             " and i.co_regrel=m.co_reg ";*/
       
      /* strQry="select u.co_emp, u.co_loc, u.co_tipdoc, u.co_doc, m.co_bod from( " +
              " select co_emprel, co_locrel, co_tipdocrel, co_docrel " +
              " from tbr_detmovinv " +
                " where co_emp= " + intCodEmp+
                " and co_loc= " + intCodLoc+
                " and co_tipdoc= " + intCodTipDoc+
                " and co_doc= " +intCodDoc+
                " )i, tbr_detmovinv u , tbm_detmovinv m" +
                " where u.co_emprel=i.co_emprel" +
                " and u.co_locrel=i.co_locrel" +
                " and u.co_tipdocrel=i.co_tipdocrel" +
                " and u.co_docrel=i.co_docrel" +
                " and u.co_tipdoc<>228" +
                " and u.co_emp=m.co_emp" +
                " and u.co_loc=m.co_loc" +
                " and u.co_tipdoc=m.co_tipdoc" +
                " and u.co_doc=m.co_doc";*/
       
        strQry= "select  u.co_emp, u.co_loc, u.co_tipdoc, u.co_doc, m.co_bod from("+
                " select co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel" +
                " from tbr_detmovinv "+
                " where co_emp="+intCodEmp+
                " and co_loc= "+intCodLoc+
                " and co_tipdoc="+intCodTipDoc+
                " and co_doc="+intCodDoc+
                " )i, tbr_detmovinv u , tbm_detmovinv m "+
                " where u.co_emprel=i.co_emprel"+
                " and u.co_locrel=i.co_locrel"+
                " and u.co_tipdocrel=i.co_tipdocrel"+
                " and u.co_docrel=i.co_docrel"+
                " and u.co_regrel=i.co_regrel"+
                " and u.co_tipdoc<>228"+
                " and u.co_emp=m.co_emp"+
                " and u.co_loc=m.co_loc"+
                " and u.co_tipdoc=m.co_tipdoc"+
                " and u.co_doc=m.co_doc"+
                " and u.co_reg=m.co_reg";       
       
       
       try{
            stm=con.createStatement();
            rsReg=stm.executeQuery(strQry);
       }catch(Exception ex){
            ex.printStackTrace();
       }       
       return rsReg;
    }
    
    
    /**
     * Metodo que obtiene los registros relacionados con una transferencia realizada manualmente sin usar la reposicion y sin usar
     * las ventas.
     * @param con conexion de acceso a datos.
     * @param intCodEmp codigo de la empresa.
     * @param intCodLoc codigo del local.
     * @param intCodTipDoc codigo del tipo de documento.
     * @param intCodDoc codigo del documento.
     * @return ResultSet.
     */
    public ResultSet obtenerRegRelTraMan(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
       ResultSet rsReg=null;
       Statement stm=null;
       String strQry="";       
       
        strQry= "select  u.co_emp, u.co_loc, u.co_tipdoc, u.co_doc, m.co_bod from("+
                " select co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel" +
                " from tbr_detmovinv "+
                " where co_emp="+intCodEmp+
                " and co_loc= "+intCodLoc+
                " and co_tipdoc="+intCodTipDoc+
                " and co_doc="+intCodDoc+
                " )i, tbr_detmovinv u , tbm_detmovinv m "+
                " where u.co_emprel=i.co_emprel"+
                " and u.co_locrel=i.co_locrel"+
                " and u.co_tipdocrel=i.co_tipdocrel"+
                " and u.co_docrel=i.co_docrel"+
                " and u.co_regrel=i.co_regrel"+
                " and u.co_tipdocrel in (46,234)"+
                " and u.co_emp=m.co_emp"+
                " and u.co_loc=m.co_loc"+
                " and u.co_tipdoc=m.co_tipdoc"+
                " and u.co_doc=m.co_doc"+
                " and u.co_reg=m.co_reg";       
       try{
            stm=con.createStatement();
            rsReg=stm.executeQuery(strQry);
       }catch(Exception ex){
            ex.printStackTrace();
       }       
       return rsReg;
    }    
    
    /**
     * Metodo que obtiene los registros relacionados con una reposicion por transferencia.
     * @param con conexion acceso a base de datos.
     * @param intCodEmp codigo de la empresa.
     * @param intCodLoc codigo del local.
     * @param intCodTipDoc codigo del tipo de documento.
     * @param intCodDoc codigo del documento.
     * @return ResultSet.
     */
    public ResultSet obtenerRegRelRepTran(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
       ResultSet rsReg=null;
       Statement stm=null;
       String strQry="";       
       
        strQry= "select  u.co_emp, u.co_loc, u.co_tipdoc, u.co_doc, m.co_bod from("+
                " select co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel" +
                " from tbr_detmovinv "+
                " where co_emp="+intCodEmp+
                " and co_loc= "+intCodLoc+
                " and co_tipdoc="+intCodTipDoc+
                " and co_doc="+intCodDoc+
                " )i, tbr_detmovinv u , tbm_detmovinv m "+
                " where u.co_emprel=i.co_emprel"+
                " and u.co_locrel=i.co_locrel"+
                " and u.co_tipdocrel=i.co_tipdocrel"+
                " and u.co_docrel=i.co_docrel"+
                " and u.co_regrel=i.co_regrel"+
                " and m.nd_can<0 "+
                " and u.co_emp=m.co_emp"+
                " and u.co_loc=m.co_loc"+
                " and u.co_tipdoc=m.co_tipdoc"+
                " and u.co_doc=m.co_doc"+
                " and u.co_reg=m.co_reg"+
                " group by u.co_emp,u.co_loc, u.co_tipdoc," +
                " u.co_doc, m.co_bod ";
       try{
            stm=con.createStatement();
            rsReg=stm.executeQuery(strQry);
       }catch(Exception ex){
            ex.printStackTrace();
       }       
       return rsReg;
    }    
    
    /**
     * Metodo que verifica la forma de Pago.
     * @param oc conexion de acceso a datos.
     * @param intCodEmp codigo de la empresa.
     * @param intCodForPag codigo de forma de pago.
     * @return int 
     */
    public int verificarTipCred(Connection oc, int intCodEmp, int intCodForPag){
        int res=0;
        Statement stmSql=null;
        String strQuery="";
        ResultSet rs;
        try {
            stmSql=oc.createStatement();
            strQuery="select * from tbm_cabforpag where  st_reg='A' and co_emp="+intCodEmp+" and ( tx_des like 'Crédito%' or tx_des like 'Credito%') and co_forpag="+intCodForPag;
            rs=stmSql.executeQuery(strQuery);
            if (rs.next()) {
                res=1;
            }
            stmSql.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }//fin de verifica 
    
    
    
    
    public int obtenerCodDocFac(Connection con, int intCodEmp, int intCodLoc, int intNumDoc, int intCodTipDoc){
    
        Statement stmSql=null;
        String strQuery="";
        int intCodDoc=0;
        ResultSet rs;
        try {
            stmSql=con.createStatement();
            strQuery="select * from tbm_cabmovinv where  co_emp="+intCodEmp+" and co_tipdoc="+intCodTipDoc+" and co_loc="+intCodLoc+" and ne_numdoc="+intNumDoc;
            rs=stmSql.executeQuery(strQuery);
            if(rs.next()){
               intCodDoc= rs.getInt("co_doc");
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return intCodDoc;
    }    
    
    
    
    public int verificarRelODFac(Connection con,int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
        
        String strSqlFacRel=" select a1.co_tipdoc "+
        " from tbm_detguirem as a "+
        " inner join tbm_cabmovinv as a1 "+
        " on (a1.co_emp=a.co_emprel "+
        " and a1.co_loc=a.co_locrel "+
        " and a1.co_tipdoc=a.co_tipdocrel "+
        " and a1.co_doc=a.co_docrel ) "+
        " where a.co_emp= " +intCodEmp+
        " and a.co_loc= "+intCodLoc+
        " and a.co_tipdoc= "+intCodTipDoc+
        " and a.co_doc=  "+intCodDoc;
        
        Statement stmSql=null;
        
        int intTipDoc=0;
        ResultSet rs;
        try 
        {
            stmSql=con.createStatement();
            
            rs=stmSql.executeQuery(strSqlFacRel);
            if(rs.next()){
               intTipDoc= rs.getInt("co_tipdoc");
               
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }        
        
        return intTipDoc;
    
    }
	

	public int obtenerTipDocCnfAutEgr(java.sql.Connection conn, int intCodEmp){
         int intTipDoc=0;
         java.sql.Statement stmTipDoc;
         java.sql.ResultSet rstTipDoc;
         String strSql="";
         int intCoCfg=0;
          try{
                if(conn != null ){
                    stmTipDoc=conn.createStatement();

                    if(intCodEmp==1){
                        intCoCfg=13;
                    }else if(intCodEmp==2){
                        intCoCfg=13;
                    }else if(intCodEmp==4){
                        intCoCfg=13;
                    }
                    /*SE TRAE EL TIPO DE DOCUMENTO DE CONFIRMACION AUTOMATICA PARA LA EMPRESA REQUERIDA*/
                    strSql= "select co_tipdoc from tbm_cfgTipDocUtiProAut where co_emp="+intCodEmp+" and co_cfg="+intCoCfg;
                    /*SE TRAE EL TIPO DE DOCUMENTO DE CONFIRMACION AUTOMATICA PARA LA EMPRESA REQUERIDA*/
                    System.out.println("SQL TIPO DE DOCUMENTO"+ strSql);
                    rstTipDoc=stmTipDoc.executeQuery(strSql);
                    if(rstTipDoc.next())
                      intTipDoc = rstTipDoc.getInt("co_tipdoc");
                    rstTipDoc.close();
                    rstTipDoc=null;
                    stmTipDoc.close();
                    stmTipDoc=null;

                }
         }catch(java.sql.SQLException Evt){  
             System.out.println(""+Evt );   
          }catch(Exception Evt){  
              System.out.println(""+Evt );  
          }
          return  intTipDoc;
    }     


    /**
     * Metodo que obtiene la IP del servidor de impresion
     * @param conn Conexion de acceso a datos.
     * @return String IP del servidor.
     */
    public String obtenerIpSerImp(Connection conn){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strCadena,strIp=null;
        
        try{            
            stmLoc= conn.createStatement();
            strCadena="";
            strCadena+=" select * from tbm_sercliserloc where co_ser=16 and co_emp=0 and co_loc=1 ";
            rstLoc=stmLoc.executeQuery(strCadena);
            if(rstLoc.next()){
                strIp=rstLoc.getString("tx_dirSer");
                blnRes=true;
            }
            rstLoc.close();
            rstLoc=null;
            stmLoc.close();
            stmLoc=null;
        }
        catch (Exception e){
            blnRes=false;
        }
        return strIp;
    }
    
    public int obtenerTipDocOD(java.sql.Connection conn, int intCodEmp){
         int intTipDoc=0;
         java.sql.Statement stmTipDoc;
         java.sql.ResultSet rstTipDoc;
         String strSql="";
         int intCoCfg=0;
          try{
                if(conn != null ){
                    stmTipDoc=conn.createStatement();

                    if(intCodEmp==1){
                        intCoCfg=9;
                    }else if(intCodEmp==2){
                        intCoCfg=9;
                    }else if(intCodEmp==4){
                        intCoCfg=9;
                    }
                    /*SE TRAE EL TIPO DE DOCUMENTO DE TRANSFERENCIA PARA LA EMPRESA DONDE SE GENERA LA TRANSFERENCIA*/
                    strSql= "select co_tipdoc from tbm_cfgTipDocUtiProAut where co_emp="+intCodEmp+" and co_cfg="+intCoCfg;
                    /*SE TRAE EL TIPO DE DOCUMENTO DE TRANSFERENCIA PARA LA EMPRESA DONDE SE GENERA LA TRANSFERENCIA*/
                    System.out.println("SQL TIPO DE DOCUMENTO"+ strSql);
                    rstTipDoc=stmTipDoc.executeQuery(strSql);
                    if(rstTipDoc.next())
                      intTipDoc = rstTipDoc.getInt("co_tipdoc");
                    rstTipDoc.close();
                    rstTipDoc=null;
                    stmTipDoc.close();
                    stmTipDoc=null;

                }
         }catch(java.sql.SQLException Evt){  
             System.out.println(""+Evt );   
          }catch(Exception Evt){  
              System.out.println(""+Evt );  
          }
          return  intTipDoc;
    } 	

    
}
