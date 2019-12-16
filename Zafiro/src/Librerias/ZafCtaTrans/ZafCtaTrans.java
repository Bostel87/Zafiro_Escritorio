/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafCtaTrans;

/**
 * 
 * @author jayapata
 * Ver 0.1 
 */
public class ZafCtaTrans {

   Librerias.ZafParSis.ZafParSis objZafParSis;
   Librerias.ZafUtil.ZafUtil objUti;
   javax.swing.JInternalFrame jIntfra=null;
   javax.swing.JDialog jDialo=null;


      /** Creates a new instance of ZafInv */
    public ZafCtaTrans(javax.swing.JInternalFrame jfrthis, Librerias.ZafParSis.ZafParSis obj){
      try{
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jIntfra = jfrthis;
            objUti = new Librerias.ZafUtil.ZafUtil();
        }catch (CloneNotSupportedException e){ objUti.mostrarMsgErr_F1(jfrthis, e);  }
    }

    public ZafCtaTrans(javax.swing.JDialog jDial, Librerias.ZafParSis.ZafParSis obj){
       try{
           this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jDialo = jDial;
            objUti = new Librerias.ZafUtil.ZafUtil();
        }catch (CloneNotSupportedException e){ objUti.mostrarMsgErr_F1(jDial, e);  }
    }



private void mostarErrorException(Exception evt){
    if(jDialo==null)
        objUti.mostrarMsgErr_F1(jIntfra, evt);
     else objUti.mostrarMsgErr_F1(jDialo, evt);
}

private void mostarErrorSqlException(java.sql.SQLException evt){
    if(jDialo==null)
        objUti.mostrarMsgErr_F1(jIntfra, evt);
     else objUti.mostrarMsgErr_F1(jDialo, evt);
}


public boolean quitarCtaConProOC(java.sql.Connection conn, int intCodEmpOC, int intCodLocOC, int intCodTipDocOC, int intCodDocOC, double dblValAsi  ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  double dblValCtaFinCom =0;
  try{
    if(conn!=null){
     stmLoc=conn.createStatement();
   /*********** CASO PROVEEDOR VARIOS ********************/
      strSql="select a2.co_ctahab, a1.co_ctatra, a1.co_ctafin,  a.co_reg, a.nd_monhab, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta  " +
      " from tbm_detdia as a " +
      " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctafin=a.co_cta ) " +
      " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp  and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc    and  a2.co_ctahab=a1.co_ctatra ) " +
      " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin ) " +
      " where a.co_emp="+intCodEmpOC+" and a.co_loc="+intCodLocOC+" and a.co_tipdoc="+intCodTipDocOC+" and a.co_dia= "+intCodDocOC+"  "
      + "   and  a.nd_monhab > 0   ";
      //System.out.println("quitarCtaConProOC-> "+strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
         dblValCtaFinCom = dblValAsi;
         if(rstLoc.getString("exitCta").equals("S")){
           if(!actualizarRegDiaDevCom(conn, intCodEmpOC, intCodLocOC, intCodTipDocOC, intCodDocOC, rstLoc.getInt("co_ctafin"), rstLoc.getInt("co_ctatra"), dblValCtaFinCom, "nd_monhab" )){
               blnRes=false;  //  Error
            }
         }
     }
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;
 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorSqlException(e);   }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
 return blnRes;
}


public boolean quitarCtaConIvaFinOC(java.sql.Connection conn, int intCodEmpOC, int intCodLocOC, int intCodTipDocOC, int intCodDocOC, double dblValAsi  ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  double dblValIvaFinCom =0, dblIva;
  try{
    if(conn!=null){
     stmLoc=conn.createStatement();
 /*********** CASO IVA ********************/
     strSql="select a4.nd_poriva, a2.co_ctaivacom, a1.*, a.co_reg, a.nd_mondeb, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta " +
     " from tbm_detdia as a " +
     " inner join tbm_cabmovinv as a4 on (a4.co_emp = a.co_emp and a4.co_loc = a.co_loc and a4.co_tipdoc = a.co_tipdoc and a4.co_doc = a.co_dia) " +
     " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctafin=a.co_cta ) " +
     " inner join tbm_emp as a2 on (a2.co_emp=a.co_emp     and  a2.co_ctaivacom=a1.co_ctatra )  " +
     " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin )  " +
     " where a.co_emp="+intCodEmpOC+" and a.co_loc="+intCodLocOC+" and a.co_tipdoc="+intCodTipDocOC+" and a.co_dia= "+intCodDocOC+" "
     + " and  a.nd_mondeb > 0 ";
     //System.out.println("quitarCtaConIvaFinOC-> "+strSql);
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
        dblIva = (rstLoc.getDouble("nd_poriva") / 100) + 1;
        dblIva = objUti.redondear(dblIva, 2);
        //dblValIvaFinCom = objUti.redondear( (dblValAsi - (dblValAsi / 1.12)), 2);
        dblValIvaFinCom = objUti.redondear( (dblValAsi - (dblValAsi / dblIva)), 2);
        if(rstLoc.getString("exitCta").equals("S")){
           if(!actualizarRegDiaDevCom(conn, intCodEmpOC, intCodLocOC, intCodTipDocOC, intCodDocOC, rstLoc.getInt("co_ctafin"), rstLoc.getInt("co_ctatra"), dblValIvaFinCom, "nd_mondeb" )){
               blnRes=false;  //  Error
            }
        }
     }
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;
 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorSqlException(e);   }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
 return blnRes;
}





 double dblValIvaOCGlo=0;
 double dblValTotOCGlo=0;
public boolean quitarCtaConDevCom(java.sql.Connection conn, int intCodEmpOC, int intCodLocOC, int intCodTipDocOC, int intCodDocOC, double dblValAsi ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  double dblIva;
 
  try{
    if(conn!=null){
     stmLoc=conn.createStatement();
     dblIva = 0;
     strSql =  "select nd_poriva ";
     strSql += "from   tbm_cabmovinv ";
     strSql += "where co_emp = " + intCodEmpOC;
     strSql += "      and co_loc = " + intCodLocOC;
     strSql += "      and co_tipdoc = " + intCodTipDocOC;
     strSql += "      and co_doc = " + intCodDocOC;
     rstLoc = stmLoc.executeQuery(strSql);
     
     if (rstLoc.next())
     {  dblIva = (rstLoc.getDouble("nd_poriva") / 100) + 1;
        dblIva = objUti.redondear(dblIva, 2);
     }
     
     //dblValIvaOCGlo = objUti.redondear( (dblValAsi - (dblValAsi / 1.12)), 2);
     dblValIvaOCGlo = objUti.redondear( (dblValAsi - (dblValAsi / dblIva)), 2);
     dblValTotOCGlo = dblValAsi;

     strSql="select * from tbr_cabmovinv "
     + " where co_emprel="+intCodEmpOC+" and co_locrel="+intCodLocOC+" and co_tipdocrel="+intCodTipDocOC+" and co_docrel= "+intCodDocOC+" "
     + " and co_tipdoc=4 and st_reg='A' ";
     //System.out.println("quitarCtaConDevCom-> "+strSql);
     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next()){

         if(quitarCtaConIvaFinDevCom( conn,  rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc") )){
           if(quitarCtaConProFinDevCom( conn,  rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc") )){
             blnRes=true;
           } else { blnRes=false;  break; }
         } else { blnRes=false;  break; }


     }
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;
 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorSqlException(e);   }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
 return blnRes;
}

public boolean quitarCtaConIvaFinDevCom(java.sql.Connection conn, int intCodEmpDev, int intCodLocDev, int intCodTipDocDev, int intCodDocDev  ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  double dblValIvaFinCom =0;
  try{
    if(conn!=null){
     stmLoc=conn.createStatement();
 /*********** CASO IVA ********************/
     strSql="select a2.co_ctaivacom,  a1.*, a.co_reg, a.nd_monhab, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta " +
     " from tbm_detdia as a " +
     " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctafin=a.co_cta ) " +
     " inner join tbm_emp as a2 on (a2.co_emp=a.co_emp     and  a2.co_ctaivacom=a1.co_ctatra )  " +
     " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin )  " +
     " where a.co_emp="+intCodEmpDev+" and a.co_loc="+intCodLocDev+" and a.co_tipdoc="+intCodTipDocDev+" and a.co_dia= "+intCodDocDev+" "
     + " and  a.nd_monhab > 0 ";
     //System.out.println("quitarCtaConIvaFinOC-> "+strSql);
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
         
        if(rstLoc.getDouble("nd_monhab") >= dblValIvaOCGlo )  dblValIvaFinCom=dblValIvaOCGlo;
        else  dblValIvaFinCom=rstLoc.getDouble("nd_monhab");
     
        if(rstLoc.getString("exitCta").equals("S")){
          if(actualizarRegDiaDevCom(conn, intCodEmpDev, intCodLocDev, intCodTipDocDev, intCodDocDev, rstLoc.getInt("co_ctafin"), rstLoc.getInt("co_ctatra"), dblValIvaFinCom, "nd_monhab" )){
               
               dblValIvaOCGlo = dblValIvaOCGlo - dblValIvaFinCom;
               blnRes=true;

            }else blnRes=false;  //  Error
        }

     }
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;
 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorSqlException(e);   }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
 return blnRes;
}

public boolean quitarCtaConProFinDevCom(java.sql.Connection conn, int intCodEmpDev, int intCodLocDev, int intCodTipDocDev, int intCodDocDev  ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  double dblValCtaFinCom =0;
  try{
    if(conn!=null){
     stmLoc=conn.createStatement();
   /*********** CASO PROVEEDOR VARIOS ********************/
      strSql="select a2.co_ctahab, a1.co_ctatra, a1.co_ctafin,  a.co_reg, a.nd_mondeb, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta  " +
      " from tbm_detdia as a " +
      " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctafin=a.co_cta ) " +
      " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp  and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc    and  a2.co_ctadeb=a1.co_ctatra ) " +
      " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin ) " +
      " where a.co_emp="+intCodEmpDev+" and a.co_loc="+intCodLocDev+" and a.co_tipdoc="+intCodTipDocDev+" and a.co_dia= "+intCodDocDev+"  "
      + "   and  a.nd_mondeb > 0   ";
      //System.out.println("quitarCtaConProFinDevCom-> "+strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){

        if(rstLoc.getDouble("nd_mondeb") >= dblValTotOCGlo )  dblValCtaFinCom=dblValTotOCGlo;
        else  dblValCtaFinCom=rstLoc.getDouble("nd_mondeb");

        if(rstLoc.getString("exitCta").equals("S")){
          if(actualizarRegDiaDevCom(conn, intCodEmpDev, intCodLocDev, intCodTipDocDev, intCodDocDev, rstLoc.getInt("co_ctafin"), rstLoc.getInt("co_ctatra"), dblValCtaFinCom, "nd_mondeb" )){

               dblValTotOCGlo = dblValTotOCGlo - dblValCtaFinCom;
               blnRes=true;

            }else blnRes=false;  //  Error
        }

     }
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;
 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorSqlException(e);   }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
 return blnRes;
}



public boolean agrecambioCtaConDevCom(java.sql.Connection conn, int intCodEmpOC, int intCodLocOC, int intCodTipDocOC, int intCodDocOC  ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
    if(conn!=null){
     stmLoc=conn.createStatement();
     strSql="select * from tbr_cabmovinv "
     + " where co_emprel="+intCodEmpOC+" and co_locrel="+intCodLocOC+" and co_tipdocrel="+intCodTipDocOC+" and co_docrel= "+intCodDocOC+" "
     + " and co_tipdoc=4  and st_reg='A' ";
     //System.out.println("agrecambioCtaConIvaDevCom-> "+strSql);
     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next()){

        if(cambioCtaConIvaDevCom(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), rstLoc.getInt("co_emprel"), rstLoc.getInt("co_locrel"), rstLoc.getInt("co_tipdocrel"), rstLoc.getInt("co_docrel") ) ){
         if(cambioCtaConProDevCom(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), rstLoc.getInt("co_emprel"), rstLoc.getInt("co_locrel"), rstLoc.getInt("co_tipdocrel"), rstLoc.getInt("co_docrel") ) ){
            blnRes=true;
         } else { blnRes=false;  break; }
        }else  { blnRes=false;  break; }
     }
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;
 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorSqlException(e);   }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
 return blnRes;
}




public boolean cambioCtaConIvaOC(java.sql.Connection conn, int intCodEmpOC, int intCodLocOC, int intCodTipDocOC, int intCodDocOC, double dblValAsi  ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  double dblValIvaFinCom =0, dblIva;
  
  try{
    if(conn!=null){
     stmLoc=conn.createStatement();
 /*********** CASO IVA ********************/
     strSql="select a4.nd_poriva, a2.co_ctaivacom, a1.*, a.co_reg, a.nd_mondeb, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta " +
     " from tbm_detdia as a " +
     " inner join tbm_cabmovinv as a4 on (a4.co_emp = a.co_emp and a4.co_loc = a.co_loc and a4.co_tipdoc = a.co_tipdoc and a4.co_doc = a.co_dia) " +
     " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctatra=a.co_cta ) " +
     " inner join tbm_emp as a2 on (a2.co_emp=a.co_emp     and  a2.co_ctaivacom=a.co_cta )  " +
     " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin )  " +
     " where a.co_emp="+intCodEmpOC+" and a.co_loc="+intCodLocOC+" and a.co_tipdoc="+intCodTipDocOC+" and a.co_dia= "+intCodDocOC+" "
     + " and  a.nd_mondeb > 0 ";
     //System.out.println("cambioCtaConIvaOC-> "+strSql);
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
        dblIva = (rstLoc.getDouble("nd_poriva") / 100) + 1;
        dblIva = objUti.redondear(dblIva, 2);
        //dblValIvaFinCom =  objUti.redondear( (dblValAsi - (dblValAsi/1.12)), 2);
        dblValIvaFinCom =  objUti.redondear( (dblValAsi - (dblValAsi / dblIva)), 2);
        if(rstLoc.getString("exitCta").equals("N")){
            if(!agregarRegDiaDevCom(conn, intCodEmpOC, intCodLocOC, intCodTipDocOC, intCodDocOC, rstLoc.getInt("co_ctatra"), rstLoc.getInt("co_ctafin"), dblValIvaFinCom, 0, dblValIvaFinCom, "nd_mondeb" )){
               blnRes=false; //  Error
             }
        }else{
            if(!actualizarRegDiaDevCom(conn, intCodEmpOC, intCodLocOC, intCodTipDocOC, intCodDocOC, rstLoc.getInt("co_ctatra"), rstLoc.getInt("co_ctafin"), dblValIvaFinCom, "nd_mondeb" )){
               blnRes=false; //  Error
             }
        }
     }
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;
 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorSqlException(e);   }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
 return blnRes;
}

public boolean cambioCtaConProOC(java.sql.Connection conn, int intCodEmpOC, int intCodLocOC, int intCodTipDocOC, int intCodDocOC, double dblValAsi  ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  double dblValCtaFinCom =0;
  try{
    if(conn!=null){
     stmLoc=conn.createStatement();
 /*********** CASO PROVEEDOR VARIOS ********************/
      strSql="select a2.co_ctahab, a1.co_ctatra, a1.co_ctafin,  a.co_reg, a.nd_monhab, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta  " +
      " from tbm_detdia as a " +
      " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctatra=a.co_cta ) " +
      " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp  and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc    and  a2.co_ctahab=a.co_cta ) " +
      " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin ) " +
      " where a.co_emp="+intCodEmpOC+" and a.co_loc="+intCodLocOC+" and a.co_tipdoc="+intCodTipDocOC+" and a.co_dia= "+intCodDocOC+"  "
      + "   and  a.nd_monhab > 0   ";
      //System.out.println("cambioCtaConProOC-> "+strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
         dblValCtaFinCom = dblValAsi;
          if(rstLoc.getString("exitCta").equals("N")){
                if(!agregarRegDiaDevCom(conn, intCodEmpOC, intCodLocOC, intCodTipDocOC, intCodDocOC, rstLoc.getInt("co_ctatra"), rstLoc.getInt("co_ctafin"), 0, dblValCtaFinCom, dblValCtaFinCom, "nd_monhab" )){
                   blnRes=false;   //  Error
                }
            }else{
                if(!actualizarRegDiaDevCom(conn, intCodEmpOC, intCodLocOC, intCodTipDocOC, intCodDocOC, rstLoc.getInt("co_ctatra"), rstLoc.getInt("co_ctafin"), dblValCtaFinCom ,"nd_monhab" )){
                   blnRes=false;  //  Error
                }
            }
     }
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;
 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorSqlException(e);   }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
 return blnRes;
}








public boolean cambioCtaConProDevCom(java.sql.Connection conn, int intCodEmpdev, int intCodLocdev, int intCodTipDocdev, int intCodDocdev, int intCodEmpOC, int intCodLocOC, int intCodTipDocOC, int intCodDocOC  ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  double dblValCtaDevCom =0;
  double dblValCtaFinCom =0;
  double dblValor=0;
  try{
    if(conn!=null){
     stmLoc=conn.createStatement();
 /*********** CASO PROVEEDOR VARIOS ********************/
      strSql="select a2.co_ctahab, a1.co_ctatra, a1.co_ctafin,  a.co_reg, a.nd_mondeb, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta  " +
      " from tbm_detdia as a " +
      " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctatra=a.co_cta ) " +
      " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp  and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc    and  a2.co_ctadeb=a.co_cta ) " +
      " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin ) " +
      " where a.co_emp="+intCodEmpdev+" and a.co_loc="+intCodLocdev+" and a.co_tipdoc="+intCodTipDocdev+" and a.co_dia= "+intCodDocdev+"   ";
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
         dblValCtaDevCom = rstLoc.getDouble("nd_mondeb");

       strSql="SELECT case when valDevCom is null then 0 else valDevCom end as valDevCom FROM ( "
       + " select  sum(case when a1.nd_mondeb is null then 0 else a1.nd_mondeb end ) as valDevCom from tbr_cabmovinv as a "
       + " inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_dia=a.co_doc ) "
       + " WHERE a.co_emprel="+intCodEmpOC+" AND a.co_locrel="+intCodLocOC+" AND a.co_tipdocrel="+intCodTipDocOC+" AND a.co_docrel="+intCodDocOC+"  and a1.co_cta="+rstLoc.getInt("co_ctafin")+" "
       + " and a.co_tipdoc="+intCodTipDocdev+" and a.st_reg='A'  "
       + " ) AS x ";
       dblValor = __getValor(conn, strSql );

         strSql="SELECT (a.nd_monhab - "+dblValor+") as valcta  FROM tbm_detdia AS a   "
         + " WHERE a.co_emp="+intCodEmpOC+" AND a.co_loc="+intCodLocOC+" AND a.co_tipdoc="+intCodTipDocOC+" AND a.co_dia="+intCodDocOC+"  and co_cta="+rstLoc.getInt("co_ctafin")+" and nd_monhab > 0 ";
         if(_existeCtaIvaOCFin(conn, strSql )){
          dblValCtaFinCom = __getValCtaIvaOCFin(conn, strSql, dblValCtaDevCom );
          if(dblValCtaFinCom > 0 ){
            if(rstLoc.getString("exitCta").equals("N")){
                if(!agregarRegDiaDevCom(conn, intCodEmpdev, intCodLocdev, intCodTipDocdev, intCodDocdev, rstLoc.getInt("co_ctatra"), rstLoc.getInt("co_ctafin"), dblValCtaFinCom, 0, dblValCtaFinCom, "nd_mondeb" )){
                   blnRes=false;   //  Error
                }
            }else{
                if(!actualizarRegDiaDevCom(conn, intCodEmpdev, intCodLocdev, intCodTipDocdev, intCodDocdev, rstLoc.getInt("co_ctatra"), rstLoc.getInt("co_ctafin"), dblValCtaFinCom ,"nd_mondeb" )){
                   blnRes=false;  //  Error
                }
            }
     }}}
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;
 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorSqlException(e);   }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
 return blnRes;
}


public boolean cambioCtaConIvaDevCom(java.sql.Connection conn, int intCodEmpdev, int intCodLocdev, int intCodTipDocdev, int intCodDocdev, int intCodEmpOC, int intCodLocOC, int intCodTipDocOC, int intCodDocOC  ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  double dblValIvaDevCom =0;
  double dblValIvaFinCom =0;
  double dblValor=0;
  try{
    if(conn!=null){
     stmLoc=conn.createStatement();
 /*********** CASO IVA ********************/
     strSql="select a2.co_ctaivacom,  a1.*, a.co_reg, a.nd_monhab, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta " +
     " from tbm_detdia as a " +
     " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctatra=a.co_cta ) " +
     " inner join tbm_emp as a2 on (a2.co_emp=a.co_emp     and  a2.co_ctaivacom=a.co_cta )  " +
     " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin )  " +
     " where a.co_emp="+intCodEmpdev+" and a.co_loc="+intCodLocdev+" and a.co_tipdoc="+intCodTipDocdev+" and a.co_dia= "+intCodDocdev+"   ";
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
       dblValIvaDevCom = rstLoc.getDouble("nd_monhab");       

       strSql="SELECT case when valDevCom is null then 0 else valDevCom end as valDevCom FROM ( "
       + " select  sum(case when a1.nd_monhab is null then 0 else a1.nd_monhab end ) as valDevCom from tbr_cabmovinv as a "
       + " inner join tbm_detdia as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_dia=a.co_doc ) "
       + " WHERE a.co_emprel="+intCodEmpOC+" AND a.co_locrel="+intCodLocOC+" AND a.co_tipdocrel="+intCodTipDocOC+" AND a.co_docrel="+intCodDocOC+"  and a1.co_cta="+rstLoc.getInt("co_ctafin")+" "
       + " and a.co_tipdoc="+intCodTipDocdev+" and a.st_reg='A'  "
       + " ) AS x ";
       dblValor = __getValor(conn, strSql );

       strSql="SELECT (a.nd_mondeb - "+dblValor+") as valcta  FROM tbm_detdia AS a   "
       + " WHERE a.co_emp="+intCodEmpOC+" AND a.co_loc="+intCodLocOC+" AND a.co_tipdoc="+intCodTipDocOC+" AND a.co_dia="+intCodDocOC+"  and co_cta="+rstLoc.getInt("co_ctafin")+" and nd_mondeb > 0 ";
       if(_existeCtaIvaOCFin(conn, strSql )){
          dblValIvaFinCom = __getValCtaIvaOCFin(conn, strSql, dblValIvaDevCom );
          if(dblValIvaFinCom > 0 ){

            if(rstLoc.getString("exitCta").equals("N")){
                if(!agregarRegDiaDevCom(conn, intCodEmpdev, intCodLocdev, intCodTipDocdev, intCodDocdev, rstLoc.getInt("co_ctatra"), rstLoc.getInt("co_ctafin"), 0, dblValIvaFinCom, dblValIvaFinCom, "nd_monhab" )){
                   blnRes=false;   //  Error
                }
            }else{
                if(!actualizarRegDiaDevCom(conn, intCodEmpdev, intCodLocdev, intCodTipDocdev, intCodDocdev, rstLoc.getInt("co_ctatra"), rstLoc.getInt("co_ctafin"), dblValIvaFinCom, "nd_monhab" )){
                   blnRes=false;  //  Error
                }
            }
     }}}
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;
 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorSqlException(e);   }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
 return blnRes;
}



private boolean _existeCtaIvaOCFin(java.sql.Connection conn, String strSql ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  try{
    if(conn!=null){
     stmLoc=conn.createStatement();

   /*********** CASO IVA ********************/
     System.out.println("_existeCtaIvaOC--> "+strSql );
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
        blnRes=true;
     }
     rstLoc.close();
     rstLoc=null;
  /*********************************************/
     stmLoc.close();
     stmLoc=null;

 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorSqlException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e); }
 return blnRes;
}

private double __getValor(java.sql.Connection conn, String strSql ){
  double dblVal=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  try{
   if(conn!=null){
     stmLoc=conn.createStatement();
   /*********** CASO IVA ********************/
     //System.out.println("__getValCtaIvaOCFin--> "+strSql );
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
       dblVal=rstLoc.getDouble("valDevCom");
     }
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;

 }}catch(java.sql.SQLException e) {   mostarErrorSqlException(e);  }
  catch(Exception  e){   mostarErrorException(e); }
 return dblVal;
}

private double __getValCtaIvaOCFin(java.sql.Connection conn, String strSql, double dblValIvaDevCom ){
  double dblValCta=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  try{
   if(conn!=null){
     stmLoc=conn.createStatement();
   /*********** CASO IVA ********************/
    // System.out.println("__getValCtaIvaOCFin--> "+strSql );
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
        if(rstLoc.getDouble("valcta") >= dblValIvaDevCom )  dblValCta=dblValIvaDevCom;
        else  dblValCta=rstLoc.getDouble("valcta");
     }
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;

 }}catch(java.sql.SQLException e) {   mostarErrorSqlException(e);  }
  catch(Exception  e){   mostarErrorException(e); }
 return dblValCta;
}




private boolean agregarRegDiaDevCom(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCdoCtaTrans, int intCodCtaFin, double dblValDeb, double dblvalHab, double dblValAsi, String strCampUti ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  int intCodReg=0;
  try{
    if(conn!=null){
     stmLoc=conn.createStatement();
   /*********** CASO IVA ********************/
        intCodReg= _getObtenerMaxCodRegDetDia(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );
         strSql = "INSERT INTO TBM_DETDIA( co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cta, nd_mondeb, nd_monhab, " +
         " tx_ref, st_regrep, st_conban, fe_conban, co_usrconban ) " +
         " SELECT co_emp, co_loc, co_tipdoc, co_dia, "+intCodReg+", "+intCodCtaFin+", "+dblValDeb+", "+dblvalHab+",  tx_ref, st_regrep,  " +
         " st_conban, fe_conban, co_usrconban FROM tbm_detdia  " +
         " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_cta="+intCdoCtaTrans+" "+
         " ; "
         + " UPDATE TBM_DETDIA SET "+strCampUti+"= "+strCampUti+"-"+dblValAsi+"  " +
         " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_cta="+intCdoCtaTrans+"  ";

         ///System.out.println("agregarRegDiaDevCom-> "+strSql);
         stmLoc.executeUpdate(strSql);
  /*********************************************/
     stmLoc.close();
     stmLoc=null;
     blnRes=true;

 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorSqlException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e); }
 return blnRes;
}


private boolean actualizarRegDiaDevCom(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCdoCtaTrans, int intCodCtaFin, double dblValAsi, String strCampUti ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  try{
    if(conn!=null){
     stmLoc=conn.createStatement();
   /*********** CASO IVA ********************/
         strSql = " UPDATE TBM_DETDIA SET "+strCampUti+"= "+strCampUti+" + "+dblValAsi+"  " +
         " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_cta="+intCodCtaFin+"  "+
         " ; UPDATE TBM_DETDIA SET "+strCampUti+"= "+strCampUti+"-"+dblValAsi+"  " +
         " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_cta="+intCdoCtaTrans+"  ";
        // System.out.println("actualizarRegDiaDevCom-> "+strSql);
         stmLoc.executeUpdate(strSql);
  /*********************************************/
     stmLoc.close();
     stmLoc=null;
     blnRes=true;
 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorSqlException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e); }
 return blnRes;
}





/**
 * Obtiene el maximo registro de la tabla tbm_detdia  + 1
 * @param conn    coneccion de la base
 * @param intCodEmp   codigo de la empresa
 * @param intCodLoc   codigo del local
 * @param intCodTipDoc codigo del tipo documento
 * @param intCodDoc    codigo del documento
 * @return  -1  si no se hay algun error   caso contrario retorna el valor correcto
 */
private int _getObtenerMaxCodRegDetDia(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
  int intCodReg=-1;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     if(conn!=null){
       stmLoc=conn.createStatement();

       strSql="SELECT CASE WHEN max(co_reg) IS NULL THEN 1 ELSE max(co_reg)+1 END AS coreg FROM tbm_detdia " +
       " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+" AND co_dia= "+intCodDoc+" ";
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next()){
             intCodReg=rstLoc.getInt("coreg");
       }
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;
  }}catch(java.sql.SQLException e) { mostarErrorSqlException(e);  }
    catch(Exception  e){ mostarErrorException(e); }
 return intCodReg;
}




    

}
