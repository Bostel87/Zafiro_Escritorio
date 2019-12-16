/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas;

import Librerias.ZafPulFacEle.ZafPulFacEle;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author postgres
 */
public class ZafCliRet {
    
    private String strStrConnDb;
    private String strUsrConnDb;
    private String strClaConnDb;
    
    int intCodBodGrp;
    private int intCodEmpLoc;
    private int intCodLocLoc;
    private ZafClassImpGuia_01 objEnvMail;
    private ZafUtil objUti;
    private ZafPulFacEle objPulFacEle;
    private String strImpRptBod2="";
    //private String strRutaRpt2="";
    private String strNomImpBod2="";
    private String[] strRutaRpt2;

    public ZafCliRet() {
    }
    
    
    
    
  public boolean generaOrdenDespachoClienteRetira(Connection con,int emp,int codbod){
  boolean blnRes=true;
  Connection connCen;
  Statement stmCen;
  ResultSet rstCen;
  String strSql="";
  try{
    //connCen =  DriverManager.getConnection(strStrConnDb, strUsrConnDb, strClaConnDb );
   //if(connCen != null ){
     if(con != null ){
     stmCen=con.createStatement();
     
        ZafGuiRemDAO objGuiRem=new ZafGuiRemDAO();
        intCodBodGrp=objGuiRem.obtenerBodGru(con, emp, codbod);
        

      strSql=" select x.co_emp, x.co_loc, x.co_tipdoc, x.co_doc, x.co_bod,  x.dirbor, a2.co_bodgrp from ( select * from ( "+
      " SELECT a.co_emp,  a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc  , a1.co_bod , a4.tx_dir as dirbor ,a.co_ptodes, "+
      " ( select count(x.co_doc) from tbm_detmovinv as x where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc and x.st_cliretemprel='S' ) as exiCliret " +
      " FROM tbm_cabmovinv AS a " +
      " INNER JOIN tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) " +
      " INNER JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=a.co_emp AND a2.co_bod=a1.co_bod) " +
      " INNER JOIN tbm_cabtipdoc AS a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc) " +
      " INNER JOIN tbm_bod AS a4 on (a4.co_emp=a1.co_emp and a4.co_bod=a1.co_bod) " +
      " WHERE a.co_tipdoc IN ( 124,125,126,127,136,137,138,139,166,167, 224,225,226,227,228) " +
      " AND a.st_creguirem='N' "+
      " AND a.st_reg not in('E','I')  and a1.st_meringegrfisbod='S'   "+
      " AND (a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrp+" )   "+
      " GROUP BY  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a1.co_bod, a3.tx_descor, a.ne_numdoc, a4.tx_dir, a.tx_numped, a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes "+
      " ) as x where exiCliret > 0 ) as x "+
      " inner JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=x.co_emp AND a2.co_bod=x.co_ptodes ) "+
      " GROUP BY x.co_emp, x.co_loc, x.co_tipdoc, x.co_doc, x.co_bod, x.dirbor, a2.co_bodgrp ";    
      
      
      
      
      
     rstCen=stmCen.executeQuery(strSql);
     while(rstCen.next()){
           insertarGuiaRem_cliret(  rstCen.getInt("co_emp"), rstCen.getInt("co_loc"), rstCen.getInt("co_tipdoc"), rstCen.getInt("co_doc"), rstCen.getInt("co_bod"), rstCen.getString("dirbor"), rstCen.getInt("co_bodgrp")  ); //rstCen.getInt("co_tipdoc"), rstCen.getInt("co_doc"), rstCen.getInt("co_bod"), rstCen.getString("tx_dir"), rstCen.getString("docrel"), rstCen.getString("tx_numped") );
     }
     rstCen.close();
     rstCen=null;
     stmCen.close();
     stmCen=null;
//     connCen.close();
//     connCen=null;
     
     con.close();
     con=null;
     
  }}catch(SQLException Evt){ 
      blnRes=false; System.out.println("generaOrdenDespachoClienteRetira "+Evt.toString() );  
      objEnvMail.enviarCorreo(" "+Evt.toString() );  }
    catch(Exception Evt){ 
        blnRes=false; System.out.println("generaOrdenDespachoClienteRetira "+Evt.toString() );   
        objEnvMail.enviarCorreo(" "+Evt.toString() );  }
  return  blnRes;
}//fin de generaOD
    
    
  private boolean insertarGuiaRem_cliret( int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, String strDirBod, int intCodBodGrpIng ){ // int intCodTipDoc, int intCodDoc,  int intCodBod, String strDirBod, String strDocRel, String strNumPen ){
  boolean blnRes=false;
  java.sql.Connection connCen;
  java.sql.Statement stmCen, stmLoc;
  java.sql.ResultSet rstCen;
  String strSql="";
  int intTipDocGuia=101;
  int intCodDocGuia=0;
  int intNumGuiaDes=0;
  try{
    connCen =  java.sql.DriverManager.getConnection( strStrConnDb, strUsrConnDb, strClaConnDb );

    if(connCen!=null){
      connCen.setAutoCommit(false);

      stmCen=connCen.createStatement();
      stmLoc=connCen.createStatement();

      strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabguirem WHERE " +
      " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intTipDocGuia;
      rstCen = stmCen.executeQuery(strSql);
      if(rstCen.next())
           intCodDocGuia = rstCen.getInt("co_doc");
      rstCen.close();
      rstCen=null;

      intNumGuiaDes=_getNumGuiaDes(connCen, intCodEmpLoc, intCodLocLoc );

       strSql=" UPDATE tbm_loc SET ne_ultnumorddes="+intNumGuiaDes+" " +
       " WHERE co_emp="+intCodEmpLoc+" AND co_loc="+intCodLocLoc;
       stmLoc.executeUpdate(strSql);

         if( insertarCabGuiaRem_cliret( connCen, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBodGrpIng, intTipDocGuia, intCodDocGuia, intNumGuiaDes, intCodBod, strDirBod  ) ){
          if( insertarDetGuiaRem_cliret( connCen, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBodGrpIng, intTipDocGuia, intCodDocGuia , intNumGuiaDes ) ){
               connCen.commit();

               //almacenarArchHis("Ord.Des.--> "+ intNumGuiaDes );
               //enviarNumODBod(intNumGuiaDes);
               
               enviarPulsoFacturacionElectronica();
               
               

               if(strImpRptBod2.equals("S"))
               impresionGuiaRemAutBod2(connCen, intCodEmp, intCodLoc, intTipDocGuia, intCodDocGuia,intCodBod);

          }else {  connCen.rollback(); }
         }else {  connCen.rollback(); }

    stmLoc.close();
    stmLoc=null;
    stmCen.close();
    stmCen=null;
    connCen.close();
    connCen=null;

    blnRes=true;
  }}catch(java.sql.SQLException Evt){ blnRes=false;  System.out.println(""+Evt );  objEnvMail.enviarCorreo(" "+Evt.toString() );  }
    catch(Exception Evt){ blnRes=false;  System.out.println(""+Evt ); objEnvMail.enviarCorreo(" "+Evt.toString() );  }
  return  blnRes;
}

  
  
  /**
 * Optiene el numero de guia remision de despacho en base empresa y local
 * @param conn  coneccion de base
 * @param intCodEmpGuia   codigo de empresa
 * @param intCodLocGuia   codigo de local
 * @return  numero de guia
 */
private int _getNumGuiaDes(java.sql.Connection conn, int intCodEmpGuia, int intCodLocGuia ){
 int intNumDocGuiaDes=0;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
  try{
   if(conn != null ){
       stmLoc=conn.createStatement();

       strSql="SELECT CASE WHEN ne_ultnumorddes is null THEN 1 else ne_ultnumorddes+1 END as numOrdDes FROM tbm_loc WHERE co_emp="+intCodEmpGuia+"   "+
       " AND co_loc="+intCodLocGuia+"  ";
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next())
         intNumDocGuiaDes = rstLoc.getInt("numOrdDes");
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;

 }}catch(java.sql.SQLException Evt){   System.out.println(""+Evt ); objEnvMail.enviarCorreo(" "+Evt.toString() );  }
    catch(Exception Evt){  System.out.println(""+Evt ); objEnvMail.enviarCorreo(" "+Evt.toString() ); }
  return  intNumDocGuiaDes;
}
    
    private boolean insertarCabGuiaRem_cliret(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBodGrpIng, int intCodTipDocGuia, int intCodDocGuia, int intNumGuiaRem, int intCodBod, String strDirBod ){  //, String strDocRel, String strNumPen ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="", strSql01="";
  try{
   if(conn!=null){
      stmLoc=conn.createStatement();

     strSql=" select a.*  " +
     " from (  " +
     "  select x.* from (  " +
     "  SELECT  a.co_emp,  a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc  , a1.co_bod, ( a3.tx_descor || '-' || a.ne_numdoc ) as docrel, a4.tx_dir, a.tx_numped    " +
     "  ,a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes  " +
     "   FROM tbm_cabmovinv AS a    " +
     "   INNER JOIN tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc)   " +
     "   INNER JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=a.co_emp AND a2.co_bod=a1.co_bod)    " +
     "   INNER JOIN tbm_cabtipdoc AS a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc)    " +
     "   INNER JOIN tbm_bod AS a4 on (a4.co_emp=a1.co_emp and a4.co_bod=a1.co_bod)    " +
     "   WHERE   a.co_tipdoc IN ( 124,125,126,127,136,137,138,139,166,167, 224,225,226,227 )    " +
     "   AND  a.st_creguirem='N'    " +
     "   AND  a.st_reg not in('E','I')  and a1.st_meringegrfisbod='S'    " +
     "   AND ( a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrp+" )    " +
     "  GROUP BY  a.co_emp,  a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a1.co_bod, a3.tx_descor, a.ne_numdoc, a4.tx_dir, a.tx_numped  " +
     " ,a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes  " +
     "  ) as x   " +
     "  inner JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=x.co_emp AND a2.co_bod=x.co_ptodes )    " +
     "  where  ( a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrpIng+" )    and x.co_emp="+intCodEmp+" and x.co_loc="+intCodLoc+"  and  x.co_tipdoc="+intCodTipDoc+"  and x.co_doc="+intCodDoc+" " +
     "  ) as x   " +
     " INNER JOIN tbm_cabmovinv as a on (a.co_emp=x.co_emp and a.co_loc=x.co_loc and a.co_tipdoc=x.co_tipdoc and a.co_doc=x.co_doc) ";
     
    rstLoc=stmLoc.executeQuery(strSql);
    if(rstLoc.next()){
          strSql01="INSERT INTO tbm_cabguirem( co_emp, co_loc, co_tipdoc, co_doc, fe_doc, fe_orddes "+
          " ,ne_numorddes, ne_numdoc, co_clides, tx_rucclides, tx_nomclides,  tx_dirclides, tx_telclides, tx_ciuclides, st_imp " +
          " ,st_reg, fe_ing, co_usring ,st_conInv, fe_initra, fe_tertra, tx_coming, st_regrep, co_ptopar, tx_ptopar " +
          " ,tx_datdocoriguirem, tx_numped, co_ptodes ,st_imporddes  ) "+
          " VALUES( "+rstLoc.getString("co_emp")+", "+rstLoc.getString("co_loc")+", "+intCodTipDocGuia+", "+intCodDocGuia+",  current_date, current_date "+   // '"+rstLoc.getString("fe_doc")+"' " +
          " ,"+intNumGuiaRem+", 0, "+rstLoc.getString("co_cli")+", '"+rstLoc.getString("tx_ruc")+ "', '"+rstLoc.getString("tx_nomcli")+"', '"+rstLoc.getString("tx_dircli")+"' " +
          " ,'"+rstLoc.getString("tx_telcli")+"', '"+rstLoc.getString("tx_ciucli")+ "', 'N' ,'A', current_timestamp , 1 " +
          " ,'P', current_date, current_date, '', 'I' , "+intCodBod+", '"+strDirBod+"'  " +
          " , '' ,'',  "+rstLoc.getString("co_ptodes")+",'S' )";
       //   " , '"+strDocRel+"' ,'"+strNumPen+"',  "+rstLoc.getString("co_ptodes")+" )";

   }
   rstLoc.close();
   rstLoc=null;

   stmLoc.executeUpdate(strSql01);

   stmLoc.close();
   stmLoc=null;
   blnRes=true;
}}catch(java.sql.SQLException Evt){ blnRes=false;  System.out.println(""+Evt.toString() ); objEnvMail.enviarCorreo(" "+Evt.toString() );   }
  catch(Exception Evt){ blnRes=false;  System.out.println(""+Evt.toString() );  objEnvMail.enviarCorreo(" "+Evt.toString() ); }
 return blnRes;
}//fin
    
    
    private boolean insertarDetGuiaRem_cliret(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBodGrpIng, int intCodTipDocGuia, int intCodDocGuia, int intNumGuiaRem ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="", strSql01="";
  int intEstIns=0;
  int intReg=0;
  try{
   if(conn!=null){
      stmLoc=conn.createStatement();

      StringBuffer stbinsGuia=new StringBuffer(); //VARIABLE TIPO BUFFER

     strSql=" select x.*  "+
     " , a.co_reg, a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed, a.nd_can, a.st_meringegrfisbod  "+
     " from (  "+
     " select x.* from (  "+
     "  SELECT  a.co_emp,  a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc  , a1.co_bod, ( a3.tx_descor || '-' || a.ne_numdoc ) as docrel, a4.tx_dir, a.tx_numped    "+
     " ,a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes  "+
     "  FROM tbm_cabmovinv AS a    "+
     "   INNER JOIN tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc)    "+
     "   INNER JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=a.co_emp AND a2.co_bod=a1.co_bod)    "+
     "   INNER JOIN tbm_cabtipdoc AS a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc)    "+
     "   INNER JOIN tbm_bod AS a4 on (a4.co_emp=a1.co_emp and a4.co_bod=a1.co_bod)    "+
     "  WHERE   a.co_tipdoc IN ( 124,125,126,127,136,137,138,139,166,167, 224,225,226,227 )    "+
     "  AND  a.st_creguirem='N'    "+
     "  AND  a.st_reg not in('E','I')  and a1.st_meringegrfisbod='S'    "+
     "  AND ( a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrp+" )    "+
     "  GROUP BY  a.co_emp,  a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a1.co_bod, a3.tx_descor, a.ne_numdoc, a4.tx_dir, a.tx_numped  "+
     "   ,a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes  "+
     "    ) as x   "+
     "    inner JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=x.co_emp AND a2.co_bod=x.co_ptodes )    "+
     "    where  ( a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrpIng+" )    and x.co_emp="+intCodEmp+" and x.co_loc="+intCodLoc+"   and  x.co_tipdoc="+intCodTipDoc+"  and x.co_doc="+intCodDoc+"  "+
     "    ) as x   "+
     "  INNER JOIN tbm_detmovinv as a on (a.co_emp=x.co_emp and a.co_loc=x.co_loc and a.co_tipdoc=x.co_tipdoc and a.co_doc=x.co_doc)    "+
     "  where  a.nd_can < 0  ";

    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
       intReg++;
       if(intEstIns > 0)
         stbinsGuia.append(" UNION ALL ");


       String strObsDocRel = rstLoc.getString("docrel") +"  "+rstLoc.getString("tx_numped");

      /*stbinsGuia.append("SELECT "+intCodEmp+","+intCodLoc+","+intCodTipDocGuia+", "+intCodDocGuia+" " +
      " ,"+intReg+", "+intCodEmp+","+intCodLoc+","+rstLoc.getInt("co_tipdoc")+","+rstLoc.getInt("co_doc")+","+rstLoc.getInt("co_reg")+" " +
      " , "+rstLoc.getString("co_itm")+", '"+rstLoc.getString("tx_codalt")+"' " +
      " ,'"+rstLoc.getString("tx_nomitm")+"','"+rstLoc.getString("tx_unimed")+"', abs("+rstLoc.getString("nd_can")+")  " +
      " ,'I', '"+strObsDocRel+"',  '"+rstLoc.getString("st_meringegrfisbod")+"' ");
      intEstIns=1;*/

      stbinsGuia.append("SELECT "+intCodEmp+","+intCodLoc+","+intCodTipDocGuia+", "+intCodDocGuia+" " +
      " ,"+intReg+", "+intCodEmp+","+intCodLoc+","+rstLoc.getInt("co_tipdoc")+","+rstLoc.getInt("co_doc")+","+rstLoc.getInt("co_reg")+" " +
      " , "+rstLoc.getString("co_itm")+", '"+rstLoc.getString("tx_codalt")+"' " +
      " ,"+objUti.codificar(rstLoc.getString("tx_nomitm"))+",'"+rstLoc.getString("tx_unimed")+"', abs("+rstLoc.getString("nd_can")+")  " +
      " ,'I', '"+strObsDocRel+"',  '"+rstLoc.getString("st_meringegrfisbod")+"' ");
      intEstIns=1;

       strSql01+=" ; UPDATE tbm_cabmovinv SET st_creguirem='S', ne_numorddes="+intNumGuiaRem+"  WHERE co_emp="+intCodEmp+" " +
       " and co_loc="+intCodLoc+" and co_tipdoc="+rstLoc.getInt("co_tipdoc")+" and co_doc="+rstLoc.getInt("co_doc");

       strSql01+=" ; UPDATE tbm_detmovinv SET tx_obs1='"+intNumGuiaRem+"'  WHERE co_emp="+intCodEmp+" " +
       " and co_loc="+intCodLoc+" and co_tipdoc="+rstLoc.getInt("co_tipdoc")+" and co_doc="+rstLoc.getInt("co_doc");


   }
   rstLoc.close();
   rstLoc=null;

     if(intEstIns==1){
         strSql="INSERT INTO  tbm_detguirem (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel," +
         " co_tipdocrel, co_docrel, co_regrel, co_itm, tx_codalt,  tx_nomitm, tx_unimed, nd_can, st_regrep, tx_obs1,  st_meregrfisbod ) "+stbinsGuia.toString();
         stmLoc.executeUpdate(strSql);
         stmLoc.executeUpdate(strSql01);
     }

   stbinsGuia=null;
   stmLoc.close();
   stmLoc=null;
   blnRes=true;
}}catch(java.sql.SQLException Evt){ blnRes=false;  System.out.println(""+Evt ); objEnvMail.enviarCorreo(" "+Evt.toString() );  }
  catch(Exception Evt){ blnRes=false;  System.out.println(""+Evt );  objEnvMail.enviarCorreo(" "+Evt.toString() ); }
 return blnRes;
}//fin
    
    
    private void enviarPulsoFacturacionElectronica(){
    objPulFacEle = new ZafPulFacEle();
    objPulFacEle.iniciar();
    System.out.println(" PULSO::::::  enviarPulsoFacturacionElectronica  ");
}//fin
    
    
    private boolean impresionGuiaRemAutBod2(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc,int codbod){
  String DIRECCION_REPORTE_GUIA="";
  String strDirSis="";
  String strRutSubRpt="";
  int cobodgru;
  try{
    if(conn!=null){
        ZafGenGuiRem og=new ZafGenGuiRem();
       strDirSis= og.getDirectorioSistema();
       
       ZafGuiRemDAO objGuiRem=new ZafGuiRemDAO();
        
        cobodgru=objGuiRem.obtenerBodGru(conn, intCodEmp, codbod);
        ZafGenGuiRem objGenGuiRem=new ZafGenGuiRem();
        strRutaRpt2=objGenGuiRem.obtenerRptImpOD(cobodgru);
      
       DIRECCION_REPORTE_GUIA=strDirSis+strRutaRpt2;
      
        strRutSubRpt=DIRECCION_REPORTE_GUIA.substring(0, DIRECCION_REPORTE_GUIA.lastIndexOf("ZafRptCom23_01.jasper"));

        //System.out.println("Ruta Reporte OD ->  "+strRutSubRpt );
        System.out.println("Normal 2 Ruta Reporte OD ->  "+DIRECCION_REPORTE_GUIA );

        Map parameters = new HashMap();
        parameters.put("co_emp", new Integer(intCodEmp) );
        parameters.put("co_loc", new Integer(intCodLoc) );
        parameters.put("co_tipdoc", new Integer(intCodTipDoc) );
        parameters.put("co_doc",  new Integer(intCodDoc) );

        parameters.put("SUBREPORT_DIR", strRutSubRpt );

        javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
        objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);

        JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  conn);
        javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strNomImpBod2 , null);
        javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(printerName);
        net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
        objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
        objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
        objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
        objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        objJRPSerExp.exportReport();
        objPriReqAttSet=null;

 }
  }catch (JRException e) {  
      objEnvMail.enviarCorreo(" Error el imprimir en bodega 2.."+e.toString() ); 
  }catch (Exception e) {
      objEnvMail.enviarCorreo(" Error el imprimir en bodega 2.."+e.toString() ); 
  }
 return true;
}
    //*********************************************************************************************************************//

    public String getStrStrConnDb() {
        return strStrConnDb;
    }

    public void setStrStrConnDb(String strStrConnDb) {
        this.strStrConnDb = strStrConnDb;
    }

    public String getStrUsrConnDb() {
        return strUsrConnDb;
    }

    public void setStrUsrConnDb(String strUsrConnDb) {
        this.strUsrConnDb = strUsrConnDb;
    }

    public String getStrClaConnDb() {
        return strClaConnDb;
    }

    public void setStrClaConnDb(String strClaConnDb) {
        this.strClaConnDb = strClaConnDb;
    }

    public int getIntCodEmpLoc() {
        return intCodEmpLoc;
    }

    public void setIntCodEmpLoc(int intCodEmpLoc) {
        this.intCodEmpLoc = intCodEmpLoc;
    }

    public int getIntCodLocLoc() {
        return intCodLocLoc;
    }

    public void setIntCodLocLoc(int intCodLocLoc) {
        this.intCodLocLoc = intCodLocLoc;
    }
    
    
    
    
    
    
}
