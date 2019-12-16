/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Ventas.ZafVen02;

import Librerias.ZafUtil.ZafUtil;

/**
 *
 * @author jayapata
 */
public class ZafVen02_03 {
   Librerias.ZafParSis.ZafParSis objZafParSis;
   Librerias.ZafInventario.ZafInvItm objInvItm;
   ZafUtil objUti;
   javax.swing.JInternalFrame jIntfra=null;

   private int intCodEmpFac=0;
   private int intCodLocFac=0;
   private int intCodTipDocFac=0;
   private int intCodDocFac=0;
   StringBuffer strBufDatCorEle;
   int intEstBufCorEle=0;
   

  public ZafVen02_03(Librerias.ZafParSis.ZafParSis obj, javax.swing.JInternalFrame jIntfraObj ) {
    try{
        this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
        jIntfra=jIntfraObj;
        objUti = new ZafUtil();
        objInvItm = new Librerias.ZafInventario.ZafInvItm(jIntfra, objZafParSis );
        strBufDatCorEle = new  StringBuffer();

       }catch (Exception e){  objUti.mostrarMsgErr_F1(null, e);  }
   }


  

/**
 * ESTE PROCESO REALIZA LA ANULACION DE LA COMPRAS VENTAS RELACIONADAS QUE CORRESPONDAN A LA VENTA REALIZADA AL CLIENTE
 * @param conn
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @return FALSE  SI  NO REALIZO   TRUE  SI SE REALIZO
 */
  public boolean realizarProcesoaAnulacion(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
       boolean blnRes=false;

       intCodEmpFac=intCodEmp;
       intCodLocFac=intCodLoc;
       intCodTipDocFac= intCodTipDoc;
       intCodDocFac= intCodDoc;


       if(generaAnulacion(conn)){
              blnRes=true;
       }

       return blnRes;
  }


String strDocAnuIng="";
String strDocAnuEgr="";
private boolean generaAnulacion(java.sql.Connection conn){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
    if(conn!=null){
       stmLoc=conn.createStatement();

       /* QUERY QUE PERMITE OPTENER LOS DOCUMENTO QUE GENERARON UN INGRESO RELACIONADO ENTRE COMPAÑIA  */
        strSql="SELECT a1.st_coninv, a.* ,  a2.tx_descor ,  a1.ne_numdoc FROM tbr_cabmovinv AS a " +
        " inner join tbm_cabmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel )  " +
        "  inner join tbm_cabtipdoc as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc )    "
        + " WHERE a.co_emp="+intCodEmpFac+" and a.co_loc="+intCodLocFac+" and a.co_tipdoc="+intCodTipDocFac+" and a.co_doc="+intCodDocFac+" " +
        " and  a1.st_coninv not in ('F')  ";
        System.out.println("--> "+strSql );
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

            
          if(rstLoc.getInt("co_tipdocrel")==1){
               blnRes=false;
               break;
          }else if(rstLoc.getInt("co_tipdocrel")==2){
               blnRes=false;
               break;
          }else if(rstLoc.getInt("co_tipdocrel")==3){
               blnRes=false;
               break;
          }else if(rstLoc.getInt("co_tipdocrel")==4){
               blnRes=false;
               break;
          }else{

              
               if(rstLoc.getString("st_coninv").equals("P")) {
                    if(verificaAnulacion( conn, rstLoc.getInt("co_emprel") , rstLoc.getInt("co_locrel") , rstLoc.getInt("co_tipdocrel") , rstLoc.getInt("co_docrel")   ) ){
                        if(anulaDocEgrIng( conn, rstLoc.getInt("co_emprel") , rstLoc.getInt("co_locrel") , rstLoc.getInt("co_tipdocrel") , rstLoc.getInt("co_docrel"), " and    nd_can > 0  "   ) ){

                            strDocAnuIng +="\n DOCUMENTO ANULADO "+rstLoc.getString("tx_descor")+"-"+rstLoc.getString("ne_numdoc");
                            blnRes=true;

                         }else{ System.out.println("FALLO 1 ");  blnRes=false;   break; }
                     }else{ System.out.println("FALLO 2 ");  blnRes=false;   break; }
                }else {  System.out.println("FALLO 3 ");  blnRes=false;   break;   }


          }

        }
        rstLoc.close();
        rstLoc=null;


       /**
        *   pregunta  1  se anula el documento intermedio    , cuando se genera el documento final desaparece
        *
        * o   pregunta 2    se poneen cantidad   0    cuando se genera docuemento final queda en facturas cantidad  0 
        *
        */

        System.out.println("--->  "+strDocAnuIng );
        System.out.println("--->  "+strBufDatCorEle.toString() );

        
    stmLoc.close();
    stmLoc=null;

 }}catch(java.sql.SQLException ex) { blnRes=false; objUti.mostrarMsgErr_F1(null, ex);   }
   catch(Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(null, e);  }
 return blnRes;
}



private boolean verificaAnulacion(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc  ){
 boolean blnRes=true;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
    if(conn!=null){
       stmLoc=conn.createStatement();
       stmLoc01=conn.createStatement();
 
       strSql="SELECT a9.tx_corele, a4.st_tieguisec, a4.st_cretodguisec, a4.co_emp as coempgui, a4.co_loc as colocgui, a4.co_tipdoc as cotipdocgui, a4.co_doc as codocgui, a4.st_coninv as estconfgui " +
       " ,a1.co_emp as coempegr, a1.co_loc as colocegr, a1.co_tipdoc as cotipdocegr, a1.co_doc as codocegr  " +
       "   ,a5.tx_descor , a4.ne_numdoc , a4.tx_datdocoriguirem , a7.tx_descor as destipdocegr, a6.ne_numdoc as numdocegr "
       + " FROM tbr_cabmovinv AS a " +
       " inner join tbr_detmovinv as a1 on (a1.co_emprel=a.co_emprel and a1.co_locrel=a.co_locrel and a1.co_tipdocrel=a.co_tipdocrel and a1.co_docrel=a.co_docrel ) " +
       "    inner join tbm_cabmovinv as a6 on (a6.co_emp=a1.co_emp and a6.co_loc=a1.co_loc and a6.co_tipdoc=a1.co_tipdoc and a6.co_doc=a1.co_doc )   "
       + "  inner join tbm_cabtipdoc as a7 on (a7.co_emp=a6.co_emp and a7.co_loc=a6.co_loc and a7.co_tipdoc=a6.co_tipdoc )    "
       + "   left join tbm_detguirem as a3 on (a3.co_emprel=a1.co_emp and a3.co_locrel=a1.co_loc and a3.co_tipdocrel=a1.co_tipdoc and a3.co_docrel=a1.co_doc  ) " +
       "    left join tbm_cabguirem as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc  )  " +
       "    left join tbm_cabtipdoc as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_loc and a5.co_tipdoc=a4.co_tipdoc )    "
       + "   left join tbr_bodempbodgrp as a8 on (a8.co_emp=a4.co_emp and a8.co_bod=a4.co_ptopar ) "
       + "   left join tbm_bod as a9 on (a9.co_emp=a8.co_empgrp and a9.co_bod=a8.co_bodgrp  )  "
       + "WHERE a.co_emprel="+intCodEmp+" and a.co_locrel="+intCodLoc+" and a.co_tipdocrel="+intCodTipDoc+" and a.co_docrel="+intCodDoc+" " +
       " GROUP BY  a4.st_tieguisec, a4.st_cretodguisec, a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc,  a4.st_coninv " +
       " ,a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc   ,a5.tx_descor , a4.ne_numdoc , a4.tx_datdocoriguirem , a7.tx_descor, a6.ne_numdoc , a9.tx_corele ";
       System.out.println("--> "+strSql );
       rstLoc=stmLoc.executeQuery(strSql);
       while(rstLoc.next()){


          if(rstLoc.getInt("cotipdocegr")==1){
               blnRes=false;
               break;
          }else if(rstLoc.getInt("cotipdocegr")==2){
               blnRes=false;
               break;
          }else if(rstLoc.getInt("cotipdocegr")==3){
               blnRes=false;
               break;
          }else if(rstLoc.getInt("cotipdocegr")==4){
               blnRes=false;
               break;
          }else{
              

              if(rstLoc.getString("codocgui")==null){
                if(anulaDocEgrIng( conn, rstLoc.getInt("coempegr") , rstLoc.getInt("colocegr") , rstLoc.getInt("cotipdocegr") , rstLoc.getInt("codocegr") , " and    nd_can < 0  "  ) ){


                    strDocAnuEgr =" SELECT  text'"+rstLoc.getString("tx_corele")+"' as CORELE, text'("+rstLoc.getString("destipdocegr")+"-"+rstLoc.getString("numdocegr")+")' AS DOCANU ";

                    if(intEstBufCorEle==1) strBufDatCorEle.append(" UNION ALL ");
                    strBufDatCorEle.append(strDocAnuEgr);
                    intEstBufCorEle=1;

                }else{  System.out.println("FALLO 4 1 ");
                    blnRes=false;
                   break;
                }
              }else{
                   if(rstLoc.getString("estconfgui").equals("P")) {


                    if(rstLoc.getString("st_tieguisec").equals("N")) {

                     if(anulaDocEgrIng( conn, rstLoc.getInt("coempegr") , rstLoc.getInt("colocegr") , rstLoc.getInt("cotipdocegr") , rstLoc.getInt("codocegr") , " and    nd_can < 0  "  ) ){

                        strSql="UPDATE tbm_cabguirem SET st_reg='I' WHERE co_emp="+rstLoc.getString("coempgui")+" and " +
                        " co_loc="+rstLoc.getString("colocgui")+" and co_tipdoc="+rstLoc.getString("cotipdocgui")+" and co_doc="+rstLoc.getString("codocgui")+" ";
                        //System.out.println("Anula Guia--> "+strSql );
                        stmLoc01.executeUpdate(strSql);


                        strDocAnuEgr =" SELECT  text'"+rstLoc.getString("tx_corele")+"' as CORELE, text'("+rstLoc.getString("tx_descor")+"-"+rstLoc.getString("ne_numdoc")+"  "+rstLoc.getString("destipdocegr")+"-"+rstLoc.getString("numdocegr")+" )' AS DOCANU ";

                        if(intEstBufCorEle==1) strBufDatCorEle.append(" UNION ALL ");
                        strBufDatCorEle.append(strDocAnuEgr);
                        intEstBufCorEle=1;


                     }else{ System.out.println("FALLO 4 ");
                          blnRes=false;
                          break;
                     }

                   }else if(rstLoc.getString("st_tieguisec").equals("S")) {
                        if(rstLoc.getString("st_cretodguisec").equals("N")) {
                             System.out.println("FALLO 5 ");
                              blnRes=false;
                              break;

                        }else { System.out.println("FALLO 6 ");
                              blnRes=false;
                              break;
                        }
                       
                   }


                  }else{
                       System.out.println("FALLO 7 ");
                       blnRes=false;
                       break;
                       
                  }

              }

              

           }
           
           
       }
       rstLoc.close();
       rstLoc=null;

    stmLoc01.close();
    stmLoc01=null;
    stmLoc.close();
    stmLoc=null;

 }}catch(java.sql.SQLException ex) { blnRes=false; objUti.mostrarMsgErr_F1(null, ex);   }
   catch(Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(null, e);  }
 return blnRes;
}




private boolean anulaDocEgrIng(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strFilSql  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
    if(conn!=null){
       stmLoc=conn.createStatement();

       objInvItm.limpiarObjeto();
       objInvItm.inicializaObjeto();


       strSql="UPDATE tbm_cabmovinv SET st_reg='I' " +
       " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc= "+intCodDoc+" ";

     //  System.out.println("---> "+ strSql );

       stmLoc.executeUpdate(strSql);



       strSql="SELECT co_bod,  co_itm, abs(nd_can) as ndcan, nd_cancon " +
       " , case when nd_can < 0 then 1 else -1 end as tipmov  " +
       " , case when nd_can < 0 then 'I' else 'E' end as MerIEFisBod  FROM  tbm_detmovinv " +
       " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc= "+intCodDoc+" " +
       " "+strFilSql+" ";
       rstLoc=stmLoc.executeQuery(strSql);
       while(rstLoc.next()){


              objInvItm.generaQueryStock(intCodEmp, rstLoc.getInt("co_itm"), rstLoc.getInt("co_bod"), rstLoc.getDouble("ndcan"),  rstLoc.getInt("tipmov") , "N", "S", rstLoc.getString("MerIEFisBod"), 1);


       }
       rstLoc.close();
       rstLoc=null;

       
         if(!objInvItm.ejecutaActStock(conn, 3 ))
             return false;
         if(!objInvItm.ejecutaVerificacionStock(conn, 3))
             return false;


    stmLoc.close();
    stmLoc=null;
    blnRes=true;

 }}catch(java.sql.SQLException ex) { blnRes=false; objUti.mostrarMsgErr_F1(null, ex);   }
   catch(Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(null, e);  }
 return blnRes;
}









}






