/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Ventas.ZafVen01;

import Librerias.ZafUtil.ZafUtil;
    
/**
 *
 * @author jayapata
 */
public class ZafVen01_08 {
    Librerias.ZafParSis.ZafParSis objZafParSis;
    Librerias.ZafUtil.ZafUtil objUti;
    
    public ZafVen01_08(Librerias.ZafParSis.ZafParSis obj ){
       try{
           this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
           objUti = new ZafUtil();
      
       }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e ); }
    }

   
   public boolean getActualizarSolDevVen(java.sql.Connection conn, int intCodEmpSel, int intCodLocSel, int intCodDocSel){
     boolean blnRes=true;
     java.sql.Statement stmLoc, stmLoc01;
     java.sql.ResultSet rstLoc, rstLoc01;
     String strSql="";
     try{
      if(conn!=null){
         stmLoc=conn.createStatement();
         stmLoc01=conn.createStatement();

         strSql="update tbm_detsoldevven set nd_canvuefaccli=nd_canvuefaccli+x.canfac from ( "+
         " select *,  "+
         "  case when  canpenfac > nd_can then nd_can else canpenfac end as canfac "+
         " from (  "+
         " select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg,  a.nd_can,  ( a1.nd_canvolfac - a1.nd_canvuefaccli) as canpenfac  from tbm_detcotven as a  "+
         " inner join tbm_detsoldevven as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelsoldevven and a1.co_tipdoc=a.co_tipdocrelsoldevven and  "+
         " a1.co_doc=a.co_docrelsoldevven and a1.co_reg=a.co_regrelsoldevven ) "+
         " where a.co_emp="+intCodEmpSel+" and a.co_loc="+intCodLocSel+" and a.co_cot= "+intCodDocSel+" "+
         " ) as x ) as x  where tbm_detsoldevven.co_emp=x.co_emp and tbm_detsoldevven.co_loc=x.co_loc and tbm_detsoldevven.co_tipdoc=x.co_tipdoc" +
         " and tbm_detsoldevven.co_doc=x.co_doc and tbm_detsoldevven.co_reg=x.co_reg  ";
         stmLoc.executeUpdate(strSql);
         
         strSql="select *  from ( " +
         " select  a.co_emp, a.co_locrelsoldevven, a.co_tipdocrelsoldevven, a.co_docrelsoldevven from tbm_cabcotven as a " +
         " where a.co_emp="+intCodEmpSel+" and a.co_loc="+intCodLocSel+" and a.co_cot= "+intCodDocSel+" " +
         " group by a.co_emp, a.co_locrelsoldevven, a.co_tipdocrelsoldevven, a.co_docrelsoldevven " +
         " ) as a ";
         rstLoc=stmLoc.executeQuery(strSql);
         if(rstLoc.next()){

             strSql="select * from ( " +
             " select  sum((abs(nd_canvolfac) - abs( nd_canvuefaccli + nd_cannunvuefaccli ))) as difvuefac from tbm_detsoldevven " +
             " where co_emp="+rstLoc.getInt("co_emp")+" and co_loc="+rstLoc.getInt("co_locrelsoldevven")+" and co_tipdoc="+rstLoc.getInt("co_tipdocrelsoldevven")+" and co_doc="+rstLoc.getInt("co_docrelsoldevven")+" " +
             " ) as x  where difvuefac=0 ";
             rstLoc01=stmLoc01.executeQuery(strSql);
             if(rstLoc01.next()){
               strSql="UPDATE tbm_cabsoldevven SET st_mersindevfac='S', fe_mersindevfac="+objZafParSis.getFuncionFechaHoraBaseDatos()+" " +
               " WHERE co_emp="+rstLoc.getInt("co_emp")+" AND co_loc="+rstLoc.getString("co_locrelsoldevven")+" AND " +
               " co_tipdoc="+rstLoc.getString("co_tipdocrelsoldevven")+" AND co_doc="+rstLoc.getString("co_docrelsoldevven");
               stmLoc01.executeUpdate(strSql);
             }
             rstLoc01.close();
             rstLoc01=null;
         }
         rstLoc.close();
         rstLoc=null;

         stmLoc.close();
         stmLoc=null;
         stmLoc01.close();
         stmLoc01=null;

      }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);     }
        catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);     }

     return blnRes;
   }



}



