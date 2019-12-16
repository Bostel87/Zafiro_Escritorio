/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.   
 */
  
package Librerias.ZafReaVenComAut;
  
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.UltDocPrint;
import Librerias.ZafUtil.ZafUtil;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;

/**
 * Clase que se encarga de realizar la venta y compra de manera automatica
 * <UL>
 * <PRE>
 *  objZafReaVenComAut = new Librerias.ZafReaVenComAut.ZafReaVenComAut(this, objZafParSis);
 *
 *  if(objZafReaVenComAut.realizaVenComAut(conn, 1, 4, 46, 1756 )){
 *     conn.commit();
 *  }else conn.rollback();
 *
 * objZafReaVenComAut=null;
 * </PRE>
 * </UL>
 * @author jayapata  Email: jaya_gar@hotmail.com
 * @version v. 1.3 Cambioes efectuados para trabajar con local Cuenca
 */
public class ZafReaVenComAut {
  ZafParSis objZafParSis;
  ZafUtil objUti;
  JInternalFrame jIntfra=null;
  JDialog jDialo=null;
  private java.util.Date datFecAux;
  ZafInvItm objInvItm;
  UltDocPrint objUltDocPrint;
  StringBuffer stbDatDevCom=null;
  StringBuffer stbDatDevVen=null;
  int intDatDevCom=0;
  int intDatDevVen=0;
  double Glo_dblCanFalCom=0.00;
  double Glo_dblCanFalDevCom=0.00;
  double Glo_dblCanFalDevVen=0.00;
  double Glo_dblTotDevCom=0.00;
  double Glo_dblTotDevVen=0.00;
  double bldivaEmp=0;

  java.util.ArrayList arlItmRec;
  
//  private final boolean blnReaDevComVen=true;
  private final boolean blnReaDevComVen=false;
  
    /**
     * Constructor de la clase venta y compra automatica
     * @param jfrthis   recibe objeto JInternalFrame
     * @param obj   recibe el objeto ZafParsis
     */
    public ZafReaVenComAut(javax.swing.JInternalFrame jfrthis, Librerias.ZafParSis.ZafParSis obj ){
        try{
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jIntfra = jfrthis;
            objUti = new Librerias.ZafUtil.ZafUtil();
            objInvItm = new Librerias.ZafInventario.ZafInvItm(jIntfra, objZafParSis);
            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);

        }
        catch (CloneNotSupportedException e){ 
            objUti.mostrarMsgErr_F1(jfrthis, e);  
        }
    }

/**
 * Permite realizar el proceso de venta y compra automatica
 * @param conn   Coneccion de la base
 * @param intCodEmpRec  Código de empresa de la transferencia
 * @param intCodLocRec  Código de local de la transferencia
 * @param intCodTipDocRec Código de tipo de documento de la transferencia
 * @param intCodDocRec  Código de documento de la transferencia
 * @return true si se realizo con exito  false algun error
 */
public boolean realizaVenComAut(java.sql.Connection conn, int intCodEmpRec, int intCodLocRec, int intCodTipDocRec, int intCodDocRec ){
   boolean blnRes=true;
   try{

       System.out.println("AAA");
     arlItmRec = new java.util.ArrayList();
     arlItmRec.clear();

      if(realizaVenComAutTuval(conn,  intCodEmpRec,  intCodLocRec, intCodTipDocRec,  intCodDocRec) ){
          System.out.println("BBB");
       if(realizaVenComAutDimulti(conn,  intCodEmpRec,  intCodLocRec, intCodTipDocRec,  intCodDocRec) ){
           System.out.println("CCC");
        if(realizaVenComAutCastek(conn,  intCodEmpRec,  intCodLocRec, intCodTipDocRec,  intCodDocRec, 3) ){
            System.out.println("DDD");
         if(realizaVenComAutCastek(conn,  intCodEmpRec,  intCodLocRec, intCodTipDocRec,  intCodDocRec, 5) ){
             System.out.println("EEE");
          if(realizaVenComAutCastek(conn,  intCodEmpRec,  intCodLocRec, intCodTipDocRec,  intCodDocRec, 11 ) ){
              System.out.println("FFF");
              if(realizaVenComAutCastek(conn,  intCodEmpRec,  intCodLocRec, intCodTipDocRec,  intCodDocRec, 28 ) ){
                  System.out.println("GGG");
                recosterarItm(conn);
                blnRes=true;
          
            }else blnRes=false;
          }else blnRes=false;
         }else blnRes=false;
        }else blnRes=false;
       }else blnRes=false;
      }else blnRes=false;

     arlItmRec=null;

   }catch(Exception evt){  blnRes=false; mostarErrorException(evt); }
  return  blnRes;
}



private void recosterarItm(java.sql.Connection conn){
 try{
  if(conn != null){

      java.util.Date datFecHoy =objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
      String strFecHoy = objUti.formatearFecha(datFecHoy, "dd/MM/yyyy");

       for(int i=0;i<arlItmRec.size();i++) {

         objUti.recostearItm2009DesdeFecha(jIntfra, objZafParSis, conn,  Integer.parseInt( this.getStringValueAt(arlItmRec, i, 0)), this.getStringValueAt(arlItmRec, i, 1), strFecHoy, "dd/MM/yyyy");

       }

  }}catch(Exception e) { mostarErrorException(e);  }
}




public String getStringValueAt(java.util.ArrayList objeto, int row, int col)
{
java.util.ArrayList arlAux;
try
{
    if (objeto==null)
        return "";
    else
    {
        arlAux=(java.util.ArrayList)objeto.get(row);
        if (arlAux==null)
            return "";
        else
            if (arlAux.get(col)==null)
                return "";
            else
                return arlAux.get(col).toString();
    }
}
catch (ArrayIndexOutOfBoundsException e)
{
    return "";
}
}





String strTipPer_emp="";
String strNomBodIng="";
int intCodTipPerEmp=0;
int intCodBodPre=0;


public void cargarTipEmp(java.sql.Connection conn, int intCodEmp, int intCodLoc ){
 java.sql.Statement stmTipEmp;
 java.sql.ResultSet rstEmp;
 String sSql;
 try{
    if(conn!=null){
        stmTipEmp=conn.createStatement();

        sSql="select b.co_tipper , b.tx_descor , round(a.nd_ivaVen,2) as porIva , bod.co_bod, bod1.tx_nom FROM  tbm_emp as a " +
        " left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper)" +
        " left join tbr_bodloc as bod on(bod.co_emp=a.co_emp and bod.co_loc="+intCodLoc+" and bod.st_reg='P')  " +
        " left join tbm_bod as bod1 on(bod1.co_emp=bod.co_emp and bod1.co_bod=bod.co_bod )   " +
        " where a.co_emp="+intCodEmp;

        rstEmp = stmTipEmp.executeQuery(sSql);
        if(rstEmp.next()){
            strTipPer_emp = rstEmp.getString("tx_descor");
            bldivaEmp   =  rstEmp.getDouble("porIva");
            intCodBodPre = rstEmp.getInt("co_bod");
            intCodTipPerEmp = rstEmp.getInt("co_tipper");
            strNomBodIng= rstEmp.getString("tx_nom");
        }

        rstEmp.close();
        stmTipEmp.close();
        stmTipEmp = null;
        rstEmp = null;
    }
}catch(java.sql.SQLException Evt){   mostarErrorException(Evt); }
 catch(Exception Evt){   mostarErrorException(Evt); }
}








//
//
//               strSql="select distinct(co_empper) as empper from tbr_bodemp where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_empper not in ( "+objZafParSis.getCodigoEmpresa()+" ) ";
//               rstLoc=stmLoc.executeQuery(strSql);
//               while(rstLoc.next()){
//
//                   if(Glo_dblCanFalDevCom > 0 ){
//
//                     strSql="SELECT a.co_emp, a3.co_empper, a.co_itm, a.co_itmrel, a3.co_loc, a3.co_locdevcom, a3.co_tipdocdevcom, a3.co_locdevven, a3.co_tipdocdevven, a2.nd_stkact, a.nd_salven "+
//                     " ,a2.co_bod, a5.co_itmmae FROM tbm_invmovempgrp as a "+
//                     " inner join tbm_inv as a1 on (a1.co_emp=a.co_emprel and a1.co_itm=a.co_itmrel) "+
//                     " inner join tbr_bodemp as a3 on (a3.co_emp=a.co_emp and a3.co_loc="+objZafParSis.getCodigoLocal()+"  and a3.co_empper=a.co_emprel  and a3.st_reg='A') "+
//                     " inner join tbm_invbod as a2 on (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm and a2.co_bod=a3.co_bodper) "+
//                     " inner join tbm_equinv as a5 on (a5.co_emp=a2.co_emp and a5.co_itm=a2.co_itm )  " +
//                     "     inner join tbr_bodEmpBodGrp as a4 on (a4.co_emp=a2.co_emp and a4.co_bod=a2.co_bod) "+
//                     " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_itm="+strCodItm+"  AND a.nd_salven < 0   AND  a2.nd_stkact > 0 "+
//                     " AND ( a4.co_empGrp=0 AND a4.co_bodGrp= "+strCodBodGrp+"  ) "+
//                     " AND  a3.co_empper="+rstLoc.getInt("empper")+" ";
//                      System.out.println("Paso 2 --> "+strSql );
//                     rstLoc01=stmLoc01.executeQuery(strSql);
//                     while(rstLoc01.next()){
//
//                          dlbStkBobDevCom= rstLoc01.getDouble("nd_stkact");
//
//                           stbDatDevCom=new StringBuffer();
//                           stbDatDevVen=new StringBuffer();
//                           intDatDevCom=0;
//                           intDatDevVen=0;
//
//                          _getMovDevCompra(conn, rstLoc01.getInt("co_emp"),  rstLoc01.getInt("co_empper"), rstLoc01.getInt("co_locdevcom"), rstLoc01.getInt("co_tipdocdevcom"), rstLoc01.getInt("co_itmmae"), Glo_dblCanFalDevCom,  rstLoc01.getInt("co_bod"), dlbStkBobDevCom, rstLoc01.getInt("co_tipdocdevven"), strCodItm );
//                          _getMovDevVenta(conn, rstLoc01.getInt("co_emp"),  rstLoc01.getInt("co_locdevven"), rstLoc01.getInt("co_tipdocdevven"),  rstLoc01.getInt("co_itmmae"), rstLoc01.getInt("co_empper"),   Glo_dblCanFalDevCom, dlbStkBobDevCom , rstLoc01.getInt("co_bod")   );
//
//                           if( (!stbDatDevCom.toString().equals(""))  && (!stbDatDevVen.toString().equals("")) ){
//
//                                if(_RealizarDevoluciones(conn, strMerIngEgrFisBod , rstLoc01.getInt("co_locdevcom"), rstLoc01.getInt("co_locdevven"), rstLoc01.getInt("co_tipdocdevcom"), rstLoc01.getInt("co_tipdocdevven"), rstLoc01.getInt("co_bod"), dlbStkBobDevCom, strCodItm, strNomBodSal ) ){
//                                   blnRes=true;
//                                   System.out.println(" GENERE DEVOLUCIONES  ");
//                                 }else{
//                                           System.out.println(" ROTURA EN BREAK EN DEVOLUCIONES .... ");
//                                           break;
//                                    }
//                           }
//                          stbDatDevCom=null;
//                          stbDatDevVen=null;
//                     }
//                     rstLoc01.close();
//                     rstLoc01=null;
//                  }
//               }
//               rstLoc.close();
//               rstLoc=null;
//
//    /**************************************************************************************************************/
//
//
//
//
















/**
 * Permite realizar el proceso de venta y compra automatica de la Bodega de califormia lo que ingresa a tuval
 * @param conn   Coneccion de la base
 * @param intCodEmpRec  Código de empresa de la transferencia
 * @param intCodLocRec  Código de local de la transferencia
 * @param intCodTipDocRec Código de tipo de documento de la transferencia
 * @param intCodDocRec  Código de documento de la transferencia
 * @return true si se realizo con exito  false algun error
 */
/**
 * MODIFICADO EFLORESA: NO GENERAR DEVOLUCIONES DE COMPRA/VENTA. 
 * 
 */
private boolean realizaVenComAutTuval(java.sql.Connection conn, int intCodEmpRec, int intCodLocRec, int intCodTipDocRec, int intCodDocRec ){
   boolean blnRes=true;
   java.sql.Statement stmLoc, stmLoc01, stmLoc02, stmLoc03;
   java.sql.ResultSet rstLoc, rstLoc01, rstLoc02, rstLoc03;
   String strSql="";
   double dlbStkBobDevCom=0;
   String strMerIngEgrFisBod="N";
   String strCodItm="", strNomBodSal="";
   String strCodEmp="";
   double dblCanVenCom=0, dblDesVta=0;
   int intCodBod=0;
   int intCodItm=0;
   double dblPreVta=0, dblCosUni=0;
   double dblCosUniVen=0;
   double dblValMarRel=0;
   String strCodAlt="";
   java.util.ArrayList arlReg;
   try{
     stmLoc=conn.createStatement();
     stmLoc01=conn.createStatement();
     stmLoc02=conn.createStatement();
     stmLoc03=conn.createStatement();

   //*******************************************************************************************************************************
     // CASO BODEGA CALIFORMIA  WTF!!!
     strSql="SELECT CASE WHEN x.empven in (4) THEN 1039 else CASE WHEN x.empven in (2) THEN 603 END  END AS coclicom, " +
     " CASE WHEN x.empven in (4) THEN 1 else CASE WHEN x.empven in (2) THEN 1  END  END AS coregclicom, " +
     " CASE WHEN x.empven in (4) THEN 3117 else CASE WHEN x.empven in (2) THEN  2854 END  END AS cocliven,  " +
     " CASE WHEN x.co_bodGrp in (1) THEN 1  END AS coregcliven,    " +
     " x.empven, x1.co_locven,  x1.co_tipdocven, x.bodven, x.empcom, x1.co_loccom , x1.co_tipdoccom, x.bodcom , co_bodGrp " +
     " ,x1.co_tipdocdevven ,x1.co_tipdocdevcom  " +
     " FROM ( "+
     " SELECT x.co_emp as empven, x.co_bod as bodven, x1.co_emp as empcom, x.coloccom,  x1.co_bod as bodcom, x.co_bodGrp  FROM ( " +
     " select a.co_emp, a.co_bod, a1.co_bodGrp, 1 as coempenv, 4 as coloccom from tbm_detmovinv as a " +
     " INNER JOIN tbr_bodEmpBodGrp AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) " +
     " where a.co_emp="+intCodEmpRec+" and a.co_loc="+intCodLocRec+" and a.co_tipdoc="+intCodTipDocRec+" and a.co_doc="+intCodDocRec+" " +
     " AND  a.nd_can > 0  AND ( a1.co_empGrp=0 AND a1.co_bodGrp=1 )  and a.co_emp not in ( 1 )  " +
     " group by a.co_emp, a.co_bod, a1.co_bodGrp " +
     " ) AS x " +
     " INNER JOIN tbr_bodEmpBodGrp AS x1 ON (x1.co_emp=coempenv and x1.co_bodgrp=x.co_bodGrp) " +
     " INNER JOIN tbm_bod AS a1 ON (a1.co_emp=x1.co_emp AND a1.co_bod=x1.co_bod) " +
     " WHERE a1.st_reg='A'  "+
     "  ) AS x " +
     " INNER JOIN tbr_bodemp AS x1 ON(x1.co_emp=x.empcom and x1.co_loc=coloccom and x1.co_empper=x.empven and x1.co_bodper=x.bodven ) ";
     //System.out.println("Paso 0 --> "+strSql );
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){

       cargarTipEmp(conn, rstLoc.getInt("empcom"), rstLoc.getInt("co_loccom") );


       strSql="select a.co_emp, a.co_loc, a.co_itm, a.co_reg,  a.tx_codalt, nd_can, a6.co_itm as coitm2 , a7.nd_cosuni, a.tx_nomitm" +
       " ,a.tx_unimed from tbm_detmovinv as a " +
       " INNER JOIN tbr_bodEmpBodGrp AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) " +
       " inner join tbm_equinv as a5 on (a5.co_emp=a.co_emp and a5.co_itm=a.co_itm ) " +
       " inner join tbm_equinv as a6 on (a6.co_emp="+rstLoc.getInt("empcom")+" and a6.co_itmmae=a5.co_itmmae ) " +
       " inner join tbm_inv as a7 on (a7.co_emp=a6.co_emp and a7.co_itm=a6.co_itm )  " +
       " where a.co_emp="+intCodEmpRec+" and a.co_loc="+intCodLocRec+" and a.co_tipdoc="+intCodTipDocRec+" and a.co_doc="+intCodDocRec+" " +
       " AND  a.nd_can > 0  AND ( a1.co_empGrp=0 AND a1.co_bodGrp=1 )    ";
       //System.out.println("Paso 1 --> "+strSql );
       rstLoc01=stmLoc01.executeQuery(strSql);
       while(rstLoc01.next()){

             Glo_dblCanFalCom= rstLoc01.getDouble("nd_can");  // viene de la transferencia cargado en la conexion 
             Glo_dblCanFalDevCom=Glo_dblCanFalCom;
             Glo_dblCanFalDevVen=Glo_dblCanFalCom;


             strCodItm=rstLoc01.getString("coitm2");


  /**************************************************************************************************************/

             
             strSql="SELECT a.co_emp, a3.co_empper, a.co_itm, a.co_itmrel, a3.co_loc, a3.co_locdevcom, a3.co_tipdocdevcom, a3.co_locdevven, a3.co_tipdocdevven, a2.nd_stkact, a.nd_salven "+
             " ,a2.co_bod, a5.co_itmmae, a6.tx_nom as nombod FROM tbm_invmovempgrp as a "+
             " inner join tbm_inv as a1 on (a1.co_emp=a.co_emprel and a1.co_itm=a.co_itmrel) "+
             " inner join tbr_bodemp as a3 on (a3.co_emp=a.co_emp and a3.co_loc="+rstLoc.getInt("co_loccom")+"  and a3.co_empper=a.co_emprel  and a3.st_reg='A') "+
             " inner join tbm_invbod as a2 on (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm and a2.co_bod=a3.co_bodper) "+
             " inner join tbm_equinv as a5 on (a5.co_emp=a2.co_emp and a5.co_itm=a2.co_itm )  " +
             " inner join tbm_bod as a6 on (a6.co_emp=a2.co_emp and a6.co_bod=a2.co_bod )       " +
             " inner join tbr_bodEmpBodGrp as a4 on (a4.co_emp=a2.co_emp and a4.co_bod=a2.co_bod) "+
             " where a.co_emp="+rstLoc.getInt("empcom")+" and a.co_itm="+rstLoc01.getInt("coitm2")+"  AND a.nd_salven < 0   AND  a2.nd_stkact > 0 "+
             " AND ( a4.co_empGrp=0 AND a4.co_bodGrp= 1  ) "+
             " AND  a3.co_empper="+rstLoc01.getInt("co_emp")+" ";
             System.out.println("Paso 2 NECESIDAD DE MOVIMIENTOS EN LA TABLA--> "+strSql );
             rstLoc02=stmLoc02.executeQuery(strSql);
             if(rstLoc02.next()){

                  dlbStkBobDevCom= rstLoc02.getDouble("nd_stkact");
                  strNomBodSal=rstLoc02.getString("nombod");
                   stbDatDevCom=new StringBuffer();
                   stbDatDevVen=new StringBuffer();
                   intDatDevCom=0;
                   intDatDevVen=0;
                   
                   if (blnReaDevComVen){
                       _getMovDevCompra(conn, rstLoc02.getInt("co_emp"),  rstLoc02.getInt("co_empper"), rstLoc02.getInt("co_locdevcom"), rstLoc02.getInt("co_tipdocdevcom"), rstLoc02.getInt("co_itmmae"), Glo_dblCanFalDevCom,  rstLoc02.getInt("co_bod"), dlbStkBobDevCom, rstLoc02.getInt("co_tipdocdevven"), strCodItm );
                       _getMovDevVenta(conn, rstLoc02.getInt("co_emp"),  rstLoc02.getInt("co_locdevven"), rstLoc02.getInt("co_tipdocdevven"),  rstLoc02.getInt("co_itmmae"), rstLoc02.getInt("co_empper"),   Glo_dblCanFalDevCom, dlbStkBobDevCom , rstLoc02.getInt("co_bod")   );
                   }

                   if( (!stbDatDevCom.toString().equals(""))  && (!stbDatDevVen.toString().equals("")) ){

                        if(_RealizarDevoluciones(conn, strMerIngEgrFisBod , rstLoc02.getInt("co_locdevcom"), rstLoc02.getInt("co_locdevven"), rstLoc02.getInt("co_tipdocdevcom"), rstLoc02.getInt("co_tipdocdevven"), rstLoc02.getInt("co_bod"), dlbStkBobDevCom, strCodItm, strNomBodSal,  intCodEmpRec,  intCodLocRec,  intCodTipDocRec,  intCodDocRec, rstLoc01.getInt("co_reg") ) ){
                           blnRes=true;
                           System.out.println(" GENERA DEVOLUCIONES  ");


                         }else{
                                   System.out.println(" ROTURA EN BREAK EN DEVOLUCIONES .... ");
                                   break;
                            }
                   }
                  stbDatDevCom=null;
                  stbDatDevVen=null;
             }
             rstLoc02.close();
             rstLoc02=null;


             



  /**************************************************************************************************************/



       if(Glo_dblCanFalDevCom > 0 ){


          strCodItm=rstLoc01.getString("co_itm");

          strSql="select a.co_emp , a.co_bod, a.nd_stkact, a.co_itm " +
          " from tbm_invbod as a " +
          " inner join tbr_bodEmpBodGrp as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod) " +
          " where   ( a1.co_empGrp=0 AND a1.co_bodGrp= 1  ) AND a.co_itm = "+rstLoc01.getString("co_itm")+" AND a.nd_stkact > 0  " +
          " and a.co_emp = "+rstLoc01.getString("co_emp")+"  ";

          //System.out.println("Paso Compra venta 1 --> "+strSql );
          rstLoc02=stmLoc02.executeQuery(strSql);
          while(rstLoc02.next()){

                 strCodEmp=rstLoc.getString("empven");

                 if( rstLoc02.getDouble("nd_stkact") >= Glo_dblCanFalDevCom )
                     dblCanVenCom=Glo_dblCanFalDevCom;
                 else
                    dblCanVenCom=rstLoc02.getDouble("nd_stkact");


          if(dblCanVenCom >  0 ){

                 intCodBod=rstLoc02.getInt("co_bod");
                 /*********************************************************************/
                 int intNumFila=1;
                  if(rstLoc.getInt("empcom")==1){
                   if(strCodEmp.equals("2")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=2854";
                   if(strCodEmp.equals("4")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=3117";
                  }
                  if(rstLoc.getInt("empcom")==2){
                   if(strCodEmp.equals("1")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=603";
                   if(strCodEmp.equals("4")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=498";
                  }
                  if(rstLoc.getInt("empcom")==4){
                   if(strCodEmp.equals("2")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=789";
                   if(strCodEmp.equals("1")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=1039";
                  }

                 //System.out.println("strSql->   "+ strSql );

                  rstLoc03 = stmLoc03.executeQuery(strSql);
                  if(rstLoc03.next())
                     dblDesVta=rstLoc03.getDouble("nd_maxDes");
                  rstLoc03.close();
                  rstLoc03=null;
                /*********************************************************************/
                  strSql="SELECT a.co_emp, a.co_itm, a.nd_prevta1, nd_stkact, a.tx_codalt "
                  + " ,case when a.nd_cosuni is null then 0 else a.nd_cosuni end as cosuni "
                  + " FROM tbm_inv as a " +
                  " WHERE a.co_emp="+strCodEmp+" AND a.co_itm = "+strCodItm+" ";
                  rstLoc03 = stmLoc03.executeQuery(strSql);
                  if(rstLoc03.next()){
                     intCodItm=rstLoc03.getInt("co_itm");
                     dblPreVta=rstLoc03.getDouble("nd_prevta1");
                     strCodAlt=rstLoc03.getString("tx_codalt");
                     dblCosUniVen=rstLoc03.getDouble("cosuni");
                  }
                  rstLoc03.close();
                  rstLoc03=null;


                  /*********************************************************/

                   if(dblCosUniVen > 0 ){  // TRABAJA CON COSTO

                      dblValMarRel = objInvItm._getMargenComVenRel(Integer.parseInt(strCodEmp), rstLoc.getInt("empcom") );


                      dblPreVta= dblCosUniVen*dblValMarRel;
                      dblCosUni= dblCosUniVen*dblValMarRel;
                      dblDesVta= 0;

                   }else{ //  TRABAJA CON PRECIO DE VENTA

                    if(dblPreVta > 0 )
                      dblCosUni  =  dblPreVta - ( dblPreVta * ( dblDesVta / 100 ) );

                   }

                  /*********************************************************/



                if(dblPreVta > 0 ){

                  
                   if(generaVen(conn, strMerIngEgrFisBod,  Integer.parseInt(strCodEmp), intCodBod, intNumFila, dblCanVenCom, dblDesVta, intCodItm, dblPreVta, rstLoc.getInt("bodcom"), rstLoc01.getDouble("nd_cosuni"), rstLoc01.getString("tx_codalt"), rstLoc01.getString("tx_nomitm") , rstLoc01.getString("tx_unimed") , rstLoc.getInt("empcom"), rstLoc.getInt("co_loccom") ,  intCodEmpRec,  intCodLocRec,  intCodTipDocRec,  intCodDocRec,  rstLoc01.getInt("co_reg") )){
                   
                     if(generaCom(conn, strMerIngEgrFisBod, Integer.parseInt(strCodEmp), rstLoc.getInt("empcom"), intCodBodPre, intNumFila, dblCanVenCom, intCodBod, dblCosUni, strNomBodSal , rstLoc01.getString("tx_codalt"), rstLoc01.getString("tx_nomitm") , rstLoc01.getString("tx_unimed") ,rstLoc01.getInt("coitm2") , rstLoc.getInt("empcom"), rstLoc.getInt("co_loccom") , intCodEmpRec,  intCodLocRec,  intCodTipDocRec,  intCodDocRec,  rstLoc01.getInt("co_reg") )){
                        
                         blnRes=true;
                         Glo_dblCanFalDevCom=Glo_dblCanFalDevCom-dblCanVenCom;
                         System.out.println(" GENERE COMPRA VENTA  ");

                     }else { blnRes=false;  break; }
                    }else { blnRes=false;  break; }

                  
                }else{  mostrarMsg("El item "+strCodAlt+" no tiene precio de lista.");
                      blnRes=false;  break; }

          }

          }
          rstLoc02.close();
          rstLoc02=null;

       }

  /*************************************************************************************************************/





/*


        String strSqlCli="SELECT a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1, a1.tx_obs1 as cuidad FROM tbm_cli as a " +
        " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg="+rstLoc.getInt("coregcliven")+" )  " +
        " WHERE a.co_emp="+rstLoc.getInt("empven")+" AND a.co_cli= "+rstLoc.getInt("cocliven")+"  ";

        if(generaVen(conn, intCodEmpRec, intCodLocRec, intCodTipDocRec,  intCodDocRec, rstLoc.getInt("empven"), rstLoc.getInt("co_locven"), rstLoc.getInt("co_tipdocven"), rstLoc.getInt("bodven"), rstLoc.getInt("empcom"), rstLoc.getInt("co_bodGrp"), strSqlCli )){

            String strSqlPrv="SELECT a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1, a1.tx_obs1 as cuidad FROM tbm_cli as a " +
            " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg="+rstLoc.getInt("coregclicom")+" )  " +
            " WHERE a.co_emp="+rstLoc.getInt("empcom")+" AND a.co_cli="+rstLoc.getInt("coclicom")+" ";

            if(generaCom(conn, intCodEmpRec, intCodLocRec, intCodTipDocRec,  intCodDocRec, rstLoc.getInt("empcom"), rstLoc.getInt("co_loccom"), rstLoc.getInt("co_tipdoccom"), rstLoc.getInt("bodcom"), rstLoc.getInt("empcom"), rstLoc.getInt("co_bodGrp"), strSqlPrv ) ){

                blnRes=true;

           }else{  blnRes=false; }
        }else{  blnRes=false;  }





        */







     }
     rstLoc01.close();
     rstLoc01=null;






     }
     rstLoc.close();
     rstLoc=null;
//*******************************************************************************************************************************

     stmLoc.close();
     stmLoc=null;
     stmLoc01.close();
     stmLoc01=null;
     stmLoc02.close();
     stmLoc02=null;
     stmLoc03.close();
     stmLoc03=null;

   }catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorSqlException(Evt); }
    catch(Exception evt){  blnRes=false; mostarErrorException(evt); }
  return  blnRes;
}



private boolean generaCom(java.sql.Connection conn, String strMerIngEgrFisBod,  int intCodEmpCom, int intCodEmp, int intCodBod, int intNumFil, double dblCan, int intCodBodVen, double dblCosUni, String strNomBodSal ,String strCodAltItm, String strNomItm, String strUniMed, int intCodItmCom, int intCodEmpVen, int intCodLocCom, int intCodEmpRec, int intCodLocRec, int intCodTipDocRec, int intCodDocRec, int intCodRegRec ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intCodLoc=0;
  int intCodTipDoc=0;
  int intCodDoc=0;
  int intCodItm=0;
  String strEstConf="F";
   try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      intCodLoc=intCodLocCom;

      String strNomBodVen="";

        if(intCodEmpCom==1){
          if( intCodBodVen==1 )  strNomBodVen="BC";
          if( intCodBodVen==7 )  strNomBodVen="BV";
          if( intCodBodVen==8 )  strNomBodVen="BQ";
          if( intCodBodVen==10 )  strNomBodVen="BM";
          if( intCodBodVen==12 )  strNomBodVen="BSD";
        }
        if(intCodEmpCom==2){
          if( intCodBodVen==1 )  strNomBodVen="BQ";
          if( intCodBodVen==3 )  strNomBodVen="BC";
          if( intCodBodVen==4 )  strNomBodVen="BM";
          if( intCodBodVen==9 )  strNomBodVen="BV";
          if( intCodBodVen==12 )  strNomBodVen="BSD";
        }
        if(intCodEmpCom==4){
          if( intCodBodVen==1 )  strNomBodVen="BV";
          if( intCodBodVen==2 )  strNomBodVen="BC";
          if( intCodBodVen==7 )  strNomBodVen="BQ";
          if( intCodBodVen==9 )  strNomBodVen="BM";
          if( intCodBodVen==11 )  strNomBodVen="BSD";
        }





      strSql="SELECT co_loccom as co_loc, co_tipdoccom FROM tbr_bodemp WHERE co_emp="+intCodEmpVen+" AND" +
      " co_loc="+intCodLocCom+" AND co_empper="+intCodEmpCom+" AND co_bodper="+intCodBodVen;
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
         intCodTipDoc=rstLoc.getInt("co_tipdoccom");
      }
      rstLoc.close();
      rstLoc=null;

      strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
      " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc;
      rstLoc = stmLoc.executeQuery(strSql);
      if(rstLoc.next()) intCodDoc=rstLoc.getInt("co_doc");
      rstLoc.close();
      rstLoc=null;

      intCodItm=intCodItmCom;
      objInvItm.limpiarObjeto();


      double dblPorDes=0;
      double dblValDes = ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
      double dblTotal = (dblCan * dblCosUni)- dblValDes;
      double dlbSub =  objUti.redondear(dblTotal,2);
      double dlbValIva=  dlbSub * (bldivaEmp/100);
      double dlbTot=  dlbSub + dlbValIva;


      if(generaVenCab(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmpCom , 2, dlbSub, dlbValIva, dlbTot, strEstConf, 0, intCodEmpVen, intCodEmpRec,  intCodLocRec,  intCodTipDocRec,  intCodDocRec )){
       if(generaComDet(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBod ,intNumFil,  dblCan, intCodItm, 1, intCodEmpCom, dblCosUni, strMerIngEgrFisBod, strNomBodVen, strNomBodSal, strCodAltItm,  strNomItm,  strUniMed, intCodEmpRec,  intCodLocRec,  intCodTipDocRec,  intCodDocRec, intCodRegRec )){
        if(actualizaStock(conn, null, intCodEmp, intCodBod , dblCan, 1, intCodItm , strMerIngEgrFisBod, "I" )){
           blnRes=true;
      }}}
      objInvItm.limpiarObjeto();
    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false;  mostarErrorException(Evt);     }
    catch(Exception Evt)   { blnRes=false; mostarErrorException(Evt);     }
 return blnRes;
}

private boolean generaComDet(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intNumFil, double dblCan, int intCodItm, int intTipMov, int intCodEmpCom, double dblCosUni, String strIngEgrFisBod, String strNomBodVen, String strNomBodSal
    , String strCodAltItm, String strNomItm, String strUniMed, int intCodEmpRec, int intCodLocRec, int intCodTipDocRec, int  intCodDocRec, int intCodRegRec     ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  java.util.ArrayList arlReg;
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

       double dblPorDes=0;
       double dblValDes = ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
       double dblTotal = (dblCan * dblCosUni)- dblValDes;
              dblTotal =  objUti.redondear(dblTotal,2);

        strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom, " +//<==Campos que aparecen en la parte inferior del 1er Tab
        " nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, tx_obs1, tx_nombodorgdes  ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+(intNumFil+1)+", " +
        " '"+strCodAltItm+"' "+
        " ,'"+strCodAltItm+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        ", "+objUti.codificar(strNomItm)+" "+
        //", '"+strNomItm+"' "+
        ", '"+strUniMed+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+dblTotal+","+dblCosUni+", "+dblPorDes+", 'S' "+
        " ,"+dblTotal+", 'I' , '"+strIngEgrFisBod+"', 0 ,"+dblCosUni+", '"+strNomBodVen+"', '"+strNomBodSal+"' ) ";
        stmLoc.executeUpdate(strSql);

        
           arlReg=new java.util.ArrayList();
           arlReg.add(0, ""+intCodEmp );
           arlReg.add(1, ""+intCodItm );
           arlItmRec.add(arlReg);


//        if(intDatVen==1) stbDatVen.append(" UNION ALL ");
//          stbDatVen.append("SELECT "+intCodEmp+" as coemp, "+intCodLoc+" as coloc, "+intCodTipDoc+"  as cotipdoc, "+intCodDoc+" AS codoc, "+intCodBod+" as cobod  ");
//        intDatVen=1;

        strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salcom=nd_salcom+"+dblCan+" WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND " +
        " co_itm="+intCodItm+" AND co_emprel="+intCodEmpCom;
        stmLoc.executeUpdate(strSql);




          strSql="INSERT INTO tbr_detMovInv(co_emp, co_loc, co_tipDoc, co_doc, co_reg, st_reg, st_regrep , co_emprel, co_locrel, co_tipDocrel, co_docrel, co_regrel ) " +
          "  VALUES( "+intCodEmpRec+", "+intCodLocRec+", "+intCodTipDocRec+", "+intCodDocRec+", "+intCodRegRec+", 'A', 'P' " +
          " ,"+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+(intNumFil+1)+"  ) ";
          stmLoc.executeUpdate(strSql);



      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorException(Evt);     }
    catch(Exception Evt)   { blnRes=false; mostarErrorException(Evt);     }
 return blnRes;
}




private boolean generaVen(java.sql.Connection conn, String strMerIngEgrFisBod, int intCodEmp, int intCodBod, int intNumFil, double dblCan, double intDesVta, int intCodItm, double dblPreVta, int intCodBodDes, double cosuni, String strCodAltItm, String strNomItm, String strUniMed, int intCodEmpVen, int intCodLocCom, int intCodEmpRec, int intCodLocRec, int intCodTipDocRec,  int intCodDocRec, int intCodRegRec ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intCodLoc=0;
  int intCodTipDoc=0;
  int intCodDoc=0;
  String strEstConf="F";
   try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      strSql="SELECT co_locven as co_loc, co_tipdocven FROM tbr_bodemp WHERE co_emp="+intCodEmpVen+" AND" +
      " co_loc="+intCodLocCom+" AND co_empper="+intCodEmp+" AND co_bodper="+intCodBod;
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
         intCodLoc=rstLoc.getInt("co_loc");
         intCodTipDoc=rstLoc.getInt("co_tipdocven");
      }
      rstLoc.close();
      rstLoc=null;

      strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
      " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc;
      rstLoc = stmLoc.executeQuery(strSql);
      if(rstLoc.next()) intCodDoc=rstLoc.getInt("co_doc");
      rstLoc.close();
      rstLoc=null;

      objInvItm.limpiarObjeto();

       double dblValDes = ((dblCan * dblPreVta)==0)?0:((dblCan * dblPreVta) * (intDesVta / 100));
       double dblTotal = (dblCan * dblPreVta)- dblValDes;
       double  dlbSub =  objUti.redondear(dblTotal,2);
       double dlbValIva=  dlbSub * (bldivaEmp/100);
       double dlbTot=  dlbSub + dlbValIva;
       dlbSub=dlbSub*-1;
       dlbValIva=dlbValIva*-1;
       dlbTot=dlbTot*-1;

//       if(intConStbBod==1)  stbLisBodItm.append(" UNION ALL ");
//       stbLisBodItm.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodBod+" AS COBOD, "+
//       objInvItm.getIntDatoValidado(tblDat.getValueAt(intNumFil, INT_TBL_CODITM))+" AS COITM, "+dblCan+" AS NDCAN ");
//       intConStbBod=1;


 /*************************************************************************/


      if(generaVenCab(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmp, 1, dlbSub, dlbValIva, dlbTot, strEstConf , intCodBodDes , intCodEmpVen,  intCodEmpRec,  intCodLocRec,  intCodTipDocRec,   intCodDocRec )){
       if(generaVenDet(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBod ,intNumFil,  dblCan, intCodItm, -1 , intDesVta, dblPreVta, strMerIngEgrFisBod, cosuni, strCodAltItm, strNomItm, strUniMed, intCodEmpRec,  intCodLocRec,  intCodTipDocRec,   intCodDocRec, intCodRegRec )){
        if(actualizaStock(conn, null, intCodEmp, intCodBod , dblCan, -1, intCodItm, strMerIngEgrFisBod, "E" )){
           blnRes=true;
      }}}
      objInvItm.limpiarObjeto();
    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorException(Evt);     }
    catch(Exception Evt)   { blnRes=false; mostarErrorException(Evt);     }
 return blnRes;
}
   
private boolean generaVenCab(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpCom,  int TipMov, double dlbSub, double dlbValIva, double dlbTot, String strEstConf, int intCodBodDes, int intCodEmpVen, int intCodEmpRec, int intCodLocRec, int intCodTipDocRec,  int intCodDocRec  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strFecSistema="";
  int intSecEmp=0;
  int intSecGrp=0;
  int intNumDoc=0;
  String strCui="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

//      if(CONN_GLO_REM!=null) {
//            intSecEmp = objUltDocPrint.getNumeroOrdenEmpresa(CONN_GLO_REM, intCodEmp);
//            intSecGrp = objUltDocPrint.getNumeroOrdenGrupo(CONN_GLO_REM);
//      }else{
//            intSecEmp = objUltDocPrint.getNumeroOrdenEmpresa(CONN_GLO, intCodEmp);
//            intSecGrp = objUltDocPrint.getNumeroOrdenGrupo(CONN_GLO);
//      }

       intSecEmp = objUltDocPrint.getNumSecDoc(conn, intCodEmp );
       intSecGrp = objUltDocPrint.getNumSecDoc(conn, 0 );
      
      datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
      strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());

     if(TipMov==1 || TipMov==228){  // TipMov 1 FACVEN   228 FACVENE

         System.out.println(" 122  --> "+ intCodEmpVen +"    --   "+ intCodEmp );

       if(intCodEmpVen==1){
          if(intCodEmp==2) { strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=2854";  }
          if(intCodEmp==4) { strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=3117";  }
       }
       if(intCodEmpVen==2){
          if(intCodEmp==1){
              if(intCodTipDoc==126){  // 126;"FAVECM";"Factura de ventas (CASTEK MANTA)"
                  strCui="MANTA ";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=603";
              }else if(intCodTipDoc==166){ // 166;"FAVECS";"Factura de ventas (CASTEK SANTO DOMINGO)"
                  strCui="SANTO DOMINGO";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=603";
              }
              else if(intCodTipDoc==225){  // 225;"FAVECC";"Factura de ventas (CASTEK CUENCA)"
                  strCui="CUENCA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=4 ) " +
                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=603";
              }
              else{ strCui="QUITO ";
                 strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=603"; }
          }
          if(intCodEmp==4){
              if(intCodTipDoc==126){
                  strCui="MANTA ";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=498";
              } else if(intCodTipDoc==166){
                  strCui="SANTO DOMINGO";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=498";
              }
              else if(intCodTipDoc==225){
                  strCui="CUENCA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=4 ) " +
                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=498";
              }
              else{ strCui="QUITO ";
              strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=498";  }
       }}
       if(intCodEmpVen==4){
          if(intCodEmp==2){ strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=789";  }
          if(intCodEmp==1){ strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=1039";  }
        }
    }
    if(TipMov==2){

         System.out.println(" 133  --> "+ intCodEmpVen +"    --   "+ intCodEmpCom );
         
       if(intCodEmpVen==1){
          if(intCodEmpCom==2){
              if(intCodTipDoc==130){
                  strCui="MANTA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
                  " WHERE a.co_emp="+intCodEmpVen+" AND a.co_cli=603";

              }else if(intCodTipDoc==165){
                  strCui="SANTO DOMINGO";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
                  " WHERE a.co_emp="+intCodEmpVen+" AND a.co_cli=603";
              }
              else if(intCodTipDoc==224){
                  strCui="CUENCA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=4 ) " +
                  " WHERE a.co_emp="+intCodEmpVen+" AND a.co_cli=603";
              }
              else{
                  strCui="QUITO ";
                  strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmpVen+" AND co_cli=603";
              }

          }
          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmpVen+" AND co_cli=1039";
       }
       if(intCodEmpVen==2){
          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmpVen+" AND co_cli=2854";
          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmpVen+" AND co_cli=789";
       }
       if(intCodEmpVen==4){
          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmpVen+" AND co_cli=3117";
          if(intCodEmpCom==2){

              if(intCodTipDoc==130){
                  strCui="MANTA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
                  " WHERE a.co_emp="+intCodEmpVen+" AND a.co_cli=498";

              }else if(intCodTipDoc==165){
                  strCui="SANTO DOMINGO";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
                  " WHERE a.co_emp="+intCodEmpVen+" AND a.co_cli=498";
              }
                else if(intCodTipDoc==224){
                  strCui="CUENCA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=4 ) " +
                  " WHERE a.co_emp="+intCodEmpVen+" AND a.co_cli=498";
              }
              else{
                  strCui="QUITO ";
                  strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmpVen+" AND co_cli=498";
              }


          }

       }
    }

     //System.out.println(" 22 --> "+ strSql );

     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){

          intNumDoc=getUltNumDoc(conn, intCodEmp, intCodLoc, intCodTipDoc);


//          if(intDocRelEmp==1) stbDocRelEmp.append(" UNION ALL ");
//          stbDocRelEmp.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC" );
//          intDocRelEmp=1;

          strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
          " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, " +
          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
          " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv, co_ptodes ) " +
          " VALUES("+intCodEmp+", "+intCodLoc+", "+
          intCodTipDoc+", "+intCodDoc+", '"+datFecAux+"', "+rstLoc.getString("co_cli")+" ,null,'','"+
          rstLoc.getString("tx_nom")+"','"+rstLoc.getString("tx_dir")+"','"+rstLoc.getString("tx_ide")+ "','"+rstLoc.getString("tx_tel")+"'," +
          "'"+strCui+"','', "+intNumDoc+" , 0,"+
//          " '' ,'RELIZADO MANUALMENTE' , "+dlbSub+" ,0 , "+dlbTot+", "+dlbValIva+" , 1 ,'Contado', '"+strFecSistema+"', "+
          " '' ,'RELIZADO MANUALMENTE' , "+dlbSub+" ,0 , "+dlbTot+", 0 , 1 ,'Contado', '"+strFecSistema+"', "+
          objZafParSis.getCodigoUsuario()+", '"+strFecSistema+"', "+objZafParSis.getCodigoUsuario()+" , null, null," +
          " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', 45 "+
          " ,'S', 'S', '"+strEstConf+"', "+(intCodBodDes==0?null:String.valueOf(intCodBodDes))+" )";
         //*** strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDoc+" WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc;
          stmLoc.executeUpdate(strSql);

//          strSql="INSERT INTO tbr_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, st_reg, st_regrep, co_emprel, co_locrel, co_tipDocrel, co_docrel ) " +
//          "  VALUES("+intCodEmpRec+", "+intCodLocRec+", "+intCodTipDocRec+", "+intCodDocRec+", 'A','P', "+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+" ) ";
//          stmLoc.executeUpdate(strSql);
          

      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorException(Evt);     }
    catch(Exception Evt)   { blnRes=false; mostarErrorException(Evt);     }
 return blnRes;
}

private boolean generaVenDet(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intNumFil, double dblCan, int intCodItm, int intTipMov , double intDesVta, double dblPreVta, String strMerIngEgrFisBod, double cosuni
  , String strCodAltItm, String strNomItm, String strUniMed , int intCodEmpRec , int intCodLocRec, int intCodTipDocRec, int intCodDocRec, int intCodRegRec  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
 // String strPreCos="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

       //strPreCos = ((tblDat.getValueAt(intNumFil, INT_TBL_PREVTA)==null)?"0":(tblDat.getValueAt(intNumFil, INT_TBL_PREVTA).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_PREVTA).toString()));
       //double dblPreVta = objUti.redondear(strPreCos, 2);
       //strPreCos = ((tblDat.getValueAt(intNumFil, INT_TBL_COSUNI)==null)?"0":(tblDat.getValueAt(intNumFil, INT_TBL_COSUNI).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_COSUNI).toString()));
       double dblCosUni = objUti.redondear(cosuni, 2);

       double dblValDes = ((dblCan * dblPreVta)==0)?0:((dblCan * dblPreVta) * (intDesVta / 100));
       double dblTotal = (dblCan * dblPreVta)- dblValDes;
              dblTotal =  objUti.redondear(dblTotal,2);

        strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, tx_nombodorgdes ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+(intNumFil+1)+", " +
        " '"+strCodAltItm+"' "+
        " ,'"+strCodAltItm+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        //", '"+strNomItm+"' "+
        ", "+objUti.codificar(strNomItm)+" "+
        ", '"+strUniMed+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+(dblCan*dblCosUni)+","+dblPreVta+", "+intDesVta+", 'S' "+
        " ,"+(dblCan*dblCosUni)+", 'I' , '"+strMerIngEgrFisBod+"', 0 ,"+dblCosUni+" ,'"+strNomBodIng+"' ) ";
        stmLoc.executeUpdate(strSql);
        strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salven=nd_salven-"+dblCan+" WHERE co_emp="+intCodEmp+" AND " +
        " co_itm="+intCodItm+" AND co_emprel="+objZafParSis.getCodigoEmpresa();
        stmLoc.executeUpdate(strSql);



          strSql="INSERT INTO tbr_detMovInv(co_emp, co_loc, co_tipDoc, co_doc, co_reg, st_reg, st_regrep , co_emprel, co_locrel, co_tipDocrel, co_docrel, co_regrel ) " +
          "  VALUES( "+intCodEmpRec+", "+intCodLocRec+", "+intCodTipDocRec+", "+intCodDocRec+", "+intCodRegRec+", 'A', 'P' " +
          " ,"+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+(intNumFil+1)+"  ) ";
          stmLoc.executeUpdate(strSql);
        

//         if(intDatVen==1) stbDatVen.append(" UNION ALL ");
//          stbDatVen.append("SELECT "+intCodEmp+" as coemp, "+intCodLoc+" as coloc, "+intCodTipDoc+"  as cotipdoc, "+intCodDoc+" AS codoc, "+intCodBod+" as cobod  ");
//         intDatVen=1;


      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorException(Evt);     }
    catch(Exception Evt)   { blnRes=false; mostarErrorException(Evt);     }
 return blnRes;
}





private boolean actualizaStock(java.sql.Connection conn, java.sql.Connection connRemota, int intCodEmp, int intCodBod,  double dblCan, int intTipMov, int intCodItm, String strMerIngEgr, String str_MerIEFisBod ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  int intTipCli=3;
  double dlbCanMov=0.00;
  try{
      stmLoc=conn.createStatement();

      objInvItm.limpiarObjeto();
      objInvItm.inicializaObjeto();

      dlbCanMov=dblCan;

      objInvItm.generaQueryStock(intCodEmp, intCodItm, intCodBod, dlbCanMov, intTipMov, "N", strMerIngEgr, str_MerIEFisBod, 1);

      if(!objInvItm.ejecutaActStock(conn, intTipCli))
          return false;

       if(connRemota!=null){
            //System.out.println("remoto.."+connRemota );

           if(!objInvItm.ejecutaActStock(connRemota, intTipCli ))
             return false;
           if(!objInvItm.ejecutaVerificacionStock(connRemota, intTipCli))
             return false;
       }else{
         if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli ))
            return false;
       }
      objInvItm.limpiarObjeto();
      stmLoc.close();
      stmLoc=null;
      blnRes=true;
   }catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorException(Evt);     }
    catch(Exception Evt)   { blnRes=false; mostarErrorException(Evt);     }
 return blnRes;
}






private boolean _getMovDevCompra( java.sql.Connection conn,  int intCodEmpDev, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodItmmae,  double dblCanFal, int intCodBodDevCom, double dlbStkBobDevCom, int intTipDocDevVen, String strCodItm  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 int intTipDocCom=2;
 int intCodCli=0;
 int intRegSec=0;
 try{
  if(conn!=null){
    stmLoc=conn.createStatement();

      if(intCodEmp==1){
          if(intCodEmpDev==2) intCodCli=603;
          if(intCodEmpDev==4) intCodCli=1039;
       }
       if(intCodEmp==2){
          if(intCodEmpDev==1) intCodCli=2854;
          if(intCodEmpDev==4) intCodCli=789;
       }
       if(intCodEmp==4){
          if(intCodEmpDev==2) intCodCli=498;
          if(intCodEmpDev==1) intCodCli=3117;
       }



   String strAuxLoc="";
//   if(intCodEmp==4)
//       strAuxLoc="";
//   else
//     strAuxLoc=" AND a.co_loc="+intCodLoc+" ";


    strSql="select * FROM ( " +
    " SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg,  " +
    " a.ne_numdoc,  a1.tx_codalt, a1.tx_codalt2, a1.tx_nomitm, a1.tx_unimed, a1.nd_pordes, a1.co_itm, a1.nd_cosuni, a1.nd_can, a1.nd_preuni,  "+
    " a1.co_bod, ( abs(a1.nd_can) - abs(case when a1.nd_candev is null then 0 else a1.nd_candev end )) as saldo " +
    " FROM tbm_cabmovinv  AS a " +
    " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) " +
    " inner join tbm_equinv as a4 on (a4.co_emp= a1.co_emp and a4.co_itm=a1.co_itm ) "+
    " WHERE a.co_emp="+intCodEmp+"   "+strAuxLoc+" "+
    " AND a.co_tipdoc="+intTipDocCom+" AND a.co_cli="+intCodCli+" " +
    " AND a.fe_doc>='2014-01-01' and a.st_reg not in ('E','I') and a4.co_itmmae="+intCodItmmae+"   " +  /* JoséMarín 23/Dic/2015 */
    " ORDER BY a.fe_Doc, a.co_doc " +
    " ) as x  where saldo <> 0 ";

    System.out.println("  devcom1 strSql-->   "+ strSql );

    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
        intRegSec++;
        if(intDatDevCom==1) stbDatDevCom.append(" UNION ALL ");
        /*stbDatDevCom.append(" SELECT "+intRegSec+" AS regsec,  "+rstLoc.getInt("co_emp")+" AS co_emp, "+rstLoc.getInt("co_loc")+" AS co_loc, " +
        " "+rstLoc.getInt("co_tipdoc")+" AS co_tipdoc, "+rstLoc.getInt("co_doc")+" AS co_doc, "+rstLoc.getInt("co_reg")+" AS co_reg," +
        " "+rstLoc.getInt("co_bod")+" AS co_bod, "+rstLoc.getDouble("saldo")+" AS saldo, " +
        " "+rstLoc.getInt("ne_numdoc")+" AS ne_numdoc, '"+rstLoc.getString("tx_codalt")+"' AS tx_codalt, '"+rstLoc.getString("tx_codalt2")+"' AS tx_codalt2," +
        " '"+rstLoc.getString("tx_nomitm")+"' AS tx_nomitm, '"+rstLoc.getString("tx_unimed")+"' AS tx_unimed, "+rstLoc.getString("nd_pordes")+" AS nd_pordes, " +
        " "+rstLoc.getString("co_itm")+" AS co_itm, "+rstLoc.getString("nd_cosuni")+" AS nd_cosuni, "+rstLoc.getString("nd_can")+" AS nd_can,  "+rstLoc.getString("nd_preuni")+" AS nd_preuni  ");*/

        stbDatDevCom.append(" SELECT "+intRegSec+" AS regsec,  "+rstLoc.getInt("co_emp")+" AS co_emp, "+rstLoc.getInt("co_loc")+" AS co_loc, " +
        " "+rstLoc.getInt("co_tipdoc")+" AS co_tipdoc, "+rstLoc.getInt("co_doc")+" AS co_doc, "+rstLoc.getInt("co_reg")+" AS co_reg," +
        " "+rstLoc.getInt("co_bod")+" AS co_bod, "+rstLoc.getDouble("saldo")+" AS saldo, " +
        " "+rstLoc.getInt("ne_numdoc")+" AS ne_numdoc, '"+rstLoc.getString("tx_codalt")+"' AS tx_codalt, '"+rstLoc.getString("tx_codalt2")+"' AS tx_codalt2," +
        " "+objUti.codificar(rstLoc.getString("tx_nomitm"))+" AS tx_nomitm, '"+rstLoc.getString("tx_unimed")+"' AS tx_unimed, "+rstLoc.getString("nd_pordes")+" AS nd_pordes, " +
        " "+rstLoc.getString("co_itm")+" AS co_itm, "+rstLoc.getString("nd_cosuni")+" AS nd_cosuni, "+rstLoc.getString("nd_can")+" AS nd_can,  "+rstLoc.getString("nd_preuni")+" AS nd_preuni ");

        intDatDevCom=1;

    }
    rstLoc.close();
    rstLoc=null;



    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException Evt) {  blnRes=false; mostarErrorException(Evt);  }
    catch(Exception Evt) { blnRes=false; mostarErrorException(Evt); }
 return blnRes;
}


private boolean _getMovDevVenta(java.sql.Connection conn, int intCodEmp,  int intCodLoc, int  intCodTipDoc, int intCodItmmae, int intCodEmpRel, double dblCanFal, double dlbStkBobDevCom, int intCodBodDevCom ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 int intTipDocVen=228;
 int intCodCli=0;
 int intRegSec=0;
 try{
  if(conn!=null){
    stmLoc=conn.createStatement();

      if(intCodEmp==1){
          if(intCodEmpRel==2) intCodCli=603;
          if(intCodEmpRel==4) intCodCli=1039;
       }
       if(intCodEmp==2){
          if(intCodEmpRel==1) intCodCli=2854;
          if(intCodEmpRel==4) intCodCli=789;
       }
       if(intCodEmp==4){
          if(intCodEmpRel==2) intCodCli=498;
          if(intCodEmpRel==1) intCodCli=3117;
       }
       


    String strAuxLoc="";
    //   if(intCodEmp==4)
    //       strAuxLoc="";
    //   else
    //     strAuxLoc=" AND a.co_loc="+intCodLoc+" ";

    strSql="select * FROM ( " +
    " SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, " +
    " a.ne_numdoc,  a1.tx_codalt, a1.tx_codalt2, a1.tx_nomitm, a1.tx_unimed, a1.nd_pordes, a1.co_itm, a1.nd_cosuni, abs(a1.nd_can) as nd_can, a1.nd_preuni,   "+
    " a1.co_bod, ( abs(a1.nd_can) - abs( case when a1.nd_candev is null then 0 else a1.nd_candev end ) ) as saldo " +
    " FROM tbm_cabmovinv  AS a " +
    " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) " +
    " inner join tbm_equinv as a4 on (a4.co_emp= a1.co_emp and a4.co_itm=a1.co_itm ) " +
    " WHERE a.co_emp="+intCodEmp+"    "+strAuxLoc+" "+
    " AND a.co_tipdoc IN ("+intTipDocVen+",1) AND a.co_cli="+intCodCli+" " +
    " AND a.fe_doc>='2014-01-01' and a.st_reg not in ('E','I') and a4.co_itmmae="+intCodItmmae+"  ORDER BY a.fe_Doc, a.co_doc " +
    " ) as x  where saldo <> 0 ";

    System.out.println("Paso 2 _getMovDevVenta : "+ strSql );



    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
     intRegSec++;
     if(intDatDevVen==1) stbDatDevVen.append(" UNION ALL ");
     /*stbDatDevVen.append(" SELECT "+intRegSec+" AS regsec1,  "+rstLoc.getInt("co_emp")+" AS co_emp1, "+rstLoc.getInt("co_loc")+" AS co_loc1, " +
     " "+rstLoc.getInt("co_tipdoc")+" AS co_tipdoc1, "+rstLoc.getInt("co_doc")+" AS co_doc1, "+rstLoc.getInt("co_reg")+" AS co_reg1," +
     " "+rstLoc.getInt("co_bod")+" AS co_bod1, "+rstLoc.getDouble("saldo")+" AS saldo1, " +
     " "+rstLoc.getInt("ne_numdoc")+" AS ne_numdoc1, '"+rstLoc.getString("tx_codalt")+"' AS tx_codalt1, '"+rstLoc.getString("tx_codalt2")+"' AS tx_codalt21," +
     " '"+rstLoc.getString("tx_nomitm")+"' AS tx_nomitm1, '"+rstLoc.getString("tx_unimed")+"' AS tx_unimed1, "+rstLoc.getString("nd_pordes")+" AS nd_pordes1, " +
     " "+rstLoc.getString("co_itm")+" AS co_itm1, "+rstLoc.getString("nd_cosuni")+" AS nd_cosuni1, "+rstLoc.getString("nd_can")+" AS nd_can1,  "+rstLoc.getString("nd_preuni")+" AS nd_preuni1   ");*/

     stbDatDevVen.append(" SELECT "+intRegSec+" AS regsec1,  "+rstLoc.getInt("co_emp")+" AS co_emp1, "+rstLoc.getInt("co_loc")+" AS co_loc1, " +
     " "+rstLoc.getInt("co_tipdoc")+" AS co_tipdoc1, "+rstLoc.getInt("co_doc")+" AS co_doc1, "+rstLoc.getInt("co_reg")+" AS co_reg1," +
     " "+rstLoc.getInt("co_bod")+" AS co_bod1, "+rstLoc.getDouble("saldo")+" AS saldo1, " +
     " "+rstLoc.getInt("ne_numdoc")+" AS ne_numdoc1, '"+rstLoc.getString("tx_codalt")+"' AS tx_codalt1, '"+rstLoc.getString("tx_codalt2")+"' AS tx_codalt21," +
     " "+objUti.codificar(rstLoc.getString("tx_nomitm"))+" AS tx_nomitm1, '"+rstLoc.getString("tx_unimed")+"' AS tx_unimed1, "+rstLoc.getString("nd_pordes")+" AS nd_pordes1, " +
     " "+rstLoc.getString("co_itm")+" AS co_itm1, "+rstLoc.getString("nd_cosuni")+" AS nd_cosuni1, "+rstLoc.getString("nd_can")+" AS nd_can1,  "+rstLoc.getString("nd_preuni")+" AS nd_preuni1 ");

     intDatDevVen=1;

    }
    rstLoc.close();
    rstLoc=null;

    stmLoc.close();
    stmLoc=null;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; mostarErrorException(Evt);  }
    catch(Exception Evt) { blnRes=false; mostarErrorException(Evt); }
 return blnRes;
}



private boolean _RealizarDevoluciones(java.sql.Connection conn, String strMerIngEgrFisBod, int intCodLocDevCom , int intCodLocDevVen, int intCodTipDocDevCom, int intCodTipDocDevVen, int intCodBodDevCom, double dlbStkBobDevCom,  String strCodItm, String strNomBodSal, int intCodEmpRec, int intCodLocRec, int intCodTipDocRec, int intCodDocRec, int intCodRegRec  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 double dlbCanDev=0;
 try{
  if(conn!=null){
    stmLoc=conn.createStatement();

    strSql="SELECT * FROM ( " +
    " SELECT * FROM ( "+stbDatDevCom.toString()+" ) AS x "+
    " LEFT JOIN ( "+stbDatDevVen.toString()+" ) AS x1 ON(x1.regsec1=x.regsec) " +
    " ) AS x  WHERE  saldo1=saldo ";

    System.out.println(" _RealizarDevoluciones ->  "+ strSql );
    
    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
       if(Glo_dblCanFalDevCom > 0 ){
            if( dlbStkBobDevCom > 0 ){

             if(rstLoc.getDouble("saldo") >= Glo_dblCanFalDevCom ) {

                if( dlbStkBobDevCom <= Glo_dblCanFalDevCom ) dlbCanDev=dlbStkBobDevCom;
                 else dlbCanDev = Glo_dblCanFalDevCom;

                Glo_dblCanFalDevCom=Glo_dblCanFalDevCom-dlbCanDev;
                dlbStkBobDevCom=dlbStkBobDevCom-dlbCanDev;
             }else{

                 if( dlbStkBobDevCom <= rstLoc.getDouble("saldo") )  dlbCanDev=dlbStkBobDevCom;
                 else dlbCanDev = rstLoc.getDouble("saldo");

                Glo_dblCanFalDevCom=Glo_dblCanFalDevCom-dlbCanDev;
                dlbStkBobDevCom=dlbStkBobDevCom-dlbCanDev;
             }


            if( dlbCanDev > 0 ){

    //       stbDevTemp=new StringBuffer();
    //       stbDevInvTemp=new StringBuffer();

             if(generaDevCompra(conn, strMerIngEgrFisBod,  rstLoc.getInt("co_emp"),  intCodLocDevCom, rstLoc.getInt("co_tipdoc"), 
                rstLoc.getInt("co_doc"),  rstLoc.getInt("co_reg")
                , rstLoc.getInt("ne_numdoc"), intCodTipDocDevCom,  rstLoc.getInt("co_bod"), rstLoc.getString("tx_codalt"), 
                rstLoc.getString("tx_codalt2"), rstLoc.getString("tx_nomitm"), rstLoc.getString("tx_unimed"),     dlbCanDev , 
                rstLoc.getDouble("nd_pordes"),  rstLoc.getInt("co_itm"),  rstLoc.getDouble("nd_cosuni"),  rstLoc.getDouble("nd_can") , 
                strCodItm, rstLoc.getInt("co_loc") , intCodBodDevCom, rstLoc.getInt("co_emp1"),  intCodEmpRec,  intCodLocRec,  
                intCodTipDocRec,  intCodDocRec, intCodRegRec  )){
                    if(generaDevVenta(conn, strMerIngEgrFisBod,  rstLoc.getInt("co_emp"),  rstLoc.getInt("co_emp1"),  intCodLocDevVen, 
                       rstLoc.getInt("co_tipdoc1"), rstLoc.getInt("co_doc1"),  rstLoc.getInt("co_reg1")
                      ,rstLoc.getInt("ne_numdoc1"), intCodTipDocDevVen,  rstLoc.getInt("co_bod1"), rstLoc.getString("tx_codalt1"), 
                       rstLoc.getString("tx_codalt21"), rstLoc.getString("tx_nomitm1"), rstLoc.getString("tx_unimed1"),   dlbCanDev  , 
                       rstLoc.getDouble("nd_pordes1"),  rstLoc.getInt("co_itm1"),  rstLoc.getDouble("nd_preuni1"), rstLoc.getDouble("nd_can"), 
                       intCodBodDevCom , rstLoc.getInt("co_loc1"), intCodBodPre, strNomBodSal, intCodEmpRec,  intCodLocRec,  intCodTipDocRec,  
                       intCodDocRec , intCodRegRec)){

                       blnRes=true;
                       Glo_dblCanFalDevVen = Glo_dblCanFalDevCom;
                    } 
             }

    //        stbDevTemp=null;
    //        stbDevInvTemp=null;

            }
         }
       }
    }
    rstLoc.close();
    rstLoc=null;


    stmLoc.close();
    stmLoc=null;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; mostarErrorException( Evt);  }
    catch(Exception Evt) { blnRes=false; mostarErrorException( Evt); }
 return blnRes;
}



private boolean generaDevVenta(java.sql.Connection conn, String strMerIngEgrFisBod, int intCodEmpRel, int intCodEmp, int intCodLoc, int intCodTipDocOc, int intCodDocOc, int intCodRegOc
        , int intNumDoc, int intCodTipDoc, int intCodBod,  String strCodAlt, String strCodAlt2, String strNomItm, String strUniMed,  double dblCan, double dblPorDes, int intCodItm, double dblCosUni, double dblCanFac, int intCodBodDevCom, int intCodLocDev, int intCodBodPredeterminada, String strNomBodSal, int intCodEmpRec, int intCodLocRec, int intCodTipDocRec, int intCodDocRec, int intCodRegRec  ){
   boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strConfDevVen="F";
  int intCodDoc=0;
   try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
      " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc;
      rstLoc = stmLoc.executeQuery(strSql);
      if(rstLoc.next()) intCodDoc=rstLoc.getInt("co_doc");
      rstLoc.close();
      rstLoc=null;
      objInvItm.limpiarObjeto();

       double dblValDes= ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
       double dblTotal = (dblCan * dblCosUni)- dblValDes;
       double  dlbSub =  objUti.redondear(dblTotal, objZafParSis.getDecimalesBaseDatos() );
       double dlbValIva=  dlbSub * (bldivaEmp/100);
       double dlbTot=  dlbSub + dlbValIva;

       Glo_dblTotDevVen += dlbSub;


       if(strMerIngEgrFisBod.equals("S")) strConfDevVen="P";

      if(generaDevVenCab(conn, intNumDoc, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmpRel, 2, dlbSub, dlbValIva, dlbTot, intCodTipDocOc, intCodDocOc, intCodLocDev , strConfDevVen, intCodEmpRec,  intCodLocRec,  intCodTipDocRec,  intCodDocRec  )){
       if(generaDevVenDet(conn, strMerIngEgrFisBod, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBodPredeterminada, intCodTipDocOc, intCodDocOc, intCodRegOc, strCodAlt, strCodAlt2, strNomItm, strUniMed, dblCan, intCodItm, 1 ,  intCodEmpRel, dblCosUni, dblPorDes, dblCanFac, intCodLocDev, strNomBodSal, intCodEmpRec,  intCodLocRec,  intCodTipDocRec,  intCodDocRec, intCodRegRec )){
        if(actualizaStockDev(conn, null, intCodEmp, intCodBodPredeterminada , dblCan, 1, intCodItm, strMerIngEgrFisBod  ,"I" )){

            blnRes=true;
      }}}
    objInvItm.limpiarObjeto();
    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorException( Evt);     }
    catch(Exception Evt)   { blnRes=false; mostarErrorException(Evt);     }
 return blnRes;
}



private boolean generaDevCompra(java.sql.Connection conn, String strMerIngEgrFisBod,  int intCodEmp, int intCodLoc, int intCodTipDocOc, int intCodDocOc, int intCodRegOc
        , int intNumDoc, int intCodTipDoc, int intCodBod,  String strCodAlt, String strCodAlt2, String strNomItm, String strUniMed,  double dblCan, double dblPorDes, int intCodItm, double dblCosUni, double dblCanOc, String strCodItm, int intCodLocDevCom, int intCodBodDevCom, int intCodEmpCom, int intCodEmpRec, int intCodLocRec, int intCodTipDocRec, int intCodDocRec, int intCodRegRec  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intCodDoc=0;
  String strEstGuiRem="N";
   try{
    if(conn!=null){
      stmLoc=conn.createStatement();


      strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
      " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc;

      rstLoc = stmLoc.executeQuery(strSql);
      if(rstLoc.next()) intCodDoc=rstLoc.getInt("co_doc");
      rstLoc.close();
      rstLoc=null;

      objInvItm.limpiarObjeto();

      double dblValDes= ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
      double dblTotal = (dblCan * dblCosUni)- dblValDes;
      double dlbSub =  objUti.redondear(dblTotal, objZafParSis.getDecimalesBaseDatos() );
      double dlbValIva=  dlbSub * (bldivaEmp/100);
      double dlbTot=  dlbSub + dlbValIva;

      Glo_dblTotDevCom += dlbSub;

      dlbSub=dlbSub*-1;
      dlbValIva=dlbValIva*-1;
      dlbTot=dlbTot*-1;


//       if(intConStbBod==1)  stbLisBodItm.append(" UNION ALL ");
//       stbLisBodItm.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodBodDevCom+" AS COBOD, "+
//       strCodItm+" AS COITM, "+dblCan+" AS NDCAN ");
//       intConStbBod=1;


      if(strMerIngEgrFisBod.equals("N")) strEstGuiRem="S";

      if(generaDevComCab(conn, intNumDoc, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmpCom, 1,  dlbSub, dlbValIva, dlbTot, strEstGuiRem,  intCodEmpRec,  intCodLocRec,  intCodTipDocRec,  intCodDocRec   )){
       if(generaDevComDet(conn, strMerIngEgrFisBod, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBodDevCom, intCodTipDocOc, intCodDocOc, intCodRegOc, strCodAlt, strCodAlt2, strNomItm, strUniMed, dblCan, intCodItm, -1 ,  objZafParSis.getCodigoEmpresa(), dblCosUni, dblPorDes, dblCanOc, intCodLocDevCom , strEstGuiRem,  intCodEmpRec,  intCodLocRec,  intCodTipDocRec,  intCodDocRec, intCodRegRec  )){
        if(actualizaStockDev(conn, null, intCodEmp, intCodBodDevCom , dblCan, -1, intCodItm, strMerIngEgrFisBod, "E" )){

            blnRes=true;
      }}}



     objInvItm.limpiarObjeto();
    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorException(Evt);     }
    catch(Exception Evt)   { blnRes=false; mostarErrorException(Evt);     }
 return blnRes;
}




private boolean generaDevVenCab(java.sql.Connection conn, int intNumDoc, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpCom,  int TipMov, double dlbSub, double dlbValIva, double dlbTot ,int intCodTipDocOc, int intCodDocOc, int intCodLocDev, String strConfDevVen, int intCodEmpRec, int intCodLocRec, int intCodTipDocRec, int intCodDocRec   ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strFecSistema="";
  int intSecEmp=0;
  int intSecGrp=0;
  int intNumDocDev=0;
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      intSecEmp = objUltDocPrint.getNumSecDoc(conn, intCodEmp );
      intSecGrp = objUltDocPrint.getNumSecDoc(conn, 0 );      

      datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
      strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());

     if(TipMov==1 || TipMov==228){
 System.out.println("*********************************************\n JOTA \n" + TipMov + "\n +=====================================================================");
       if(objZafParSis.getCodigoEmpresa()==1){
          if(intCodEmp==2) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=2854";
          if(intCodEmp==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=3117";
       }
       if(objZafParSis.getCodigoEmpresa()==2){
          if(intCodEmp==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=603";
          if(intCodEmp==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=498";
       }
       if(objZafParSis.getCodigoEmpresa()==4){
          if(intCodEmp==2) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=789";
          if(intCodEmp==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=1039";
        }
    }
    if(TipMov==2){
       if(intCodEmp==1){
          if(intCodEmpCom==2) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=603";
          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=1039";
       }
       if(intCodEmp==2){
          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=2854";
          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=789";
       }
       if(intCodEmp==4){
          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=3117";
          if(intCodEmpCom==2) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=498";
       }
    }

     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){

         intNumDocDev=getUltNumDoc(conn, intCodEmp, intCodLoc, intCodTipDoc);

//         if(intDocRelEmpTemp==1) stbDocRelEmpTemp.append(" UNION ALL ");
//           stbDocRelEmpTemp.append( " SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC" );
//        intDocRelEmpTemp=1;
//
//
//          if(intDocRelEmp==1) stbDocRelEmp.append(" UNION ALL ");
//                stbDocRelEmp.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC" );
//           intDocRelEmp=1;


          strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
          " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot,  " +
          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
          " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv ) " +
          " VALUES("+intCodEmp+", "+intCodLoc+", "+
          intCodTipDoc+", "+intCodDoc+", '"+datFecAux+"', "+rstLoc.getString("co_cli")+" ,null,'','"+
          rstLoc.getString("tx_nom")+"','"+rstLoc.getString("tx_dir")+"','"+rstLoc.getString("tx_ide")+ "','"+rstLoc.getString("tx_tel")+"','"+
          " ','',"+intNumDocDev+" , "+intNumDoc+","+
          "  '' ,'' , "+dlbSub+" ,0 , "+dlbTot+", "+dlbValIva+" , 1 ,'Contado', '"+strFecSistema+"', "+
          objZafParSis.getCodigoUsuario()+", '"+strFecSistema+"', "+objZafParSis.getCodigoUsuario()+" , null, null," +
          " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', null "+
          " ,'S', 'S', '"+strConfDevVen+"' )";

          strSql +=" ; INSERT INTO tbr_cabmovinv( co_emp, co_loc, co_tipdoc, co_doc, st_reg, co_locrel, co_tipdocrel, co_docrel, st_regrep, co_emprel )" +
          " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", 'A', "+intCodLocDev+", "+intCodTipDocOc+", "+intCodDocOc+", 'I', "+intCodEmp+" ) ";

          strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDocDev+" WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc;
          stmLoc.executeUpdate(strSql);
         // stbDevTemp.append( strSql +" ; " );


//          strSql="INSERT INTO tbr_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, st_reg, st_regrep , co_emprel, co_locrel, co_tipDocrel, co_docrel ) " +
//          "  VALUES( "+intCodEmpRec+", "+intCodLocRec+", "+intCodTipDocRec+", "+intCodDocRec+", 'A', 'P' " +
//          " ,"+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+" ) ";
//          stmLoc.executeUpdate(strSql);

      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorException(Evt);     }
    catch(Exception Evt)   { blnRes=false; mostarErrorException(Evt);     }
 return blnRes;
}


private boolean generaDevVenDet(java.sql.Connection conn, String strMerIngEgrFisBod, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intCodTipDocOc, int intCodDocOc, int intCodRegOc,  String strCodAlt, String strCodAlt2, String strNomItm, String strUniMed, double dblCan, int intCodItm, int intTipMov, int intCodEmpCom, double dblCosUni, double dblPorDes, double dblCanFac, int intCodLocDev, String strNomBodSal, int  intCodEmpRec, int intCodLocRec, int intCodTipDocRec, int intCodDocRec, int intCodRegRec ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  java.util.ArrayList arlReg;
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

       double dblValDes= ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
       double dblTotal = (dblCan * dblCosUni)- dblValDes;
              dblTotal =  objUti.redondear(dblTotal, objZafParSis.getDecimalesBaseDatos() );


        /*strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, nd_candev, nd_canorg, tx_nombodorgdes ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", 1, " +
        " '"+strCodAlt+"' "+
        " ,'"+strCodAlt2+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        ", '"+strNomItm+"' "+
        ", '"+strUniMed+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+dblTotal+","+dblCosUni+", "+dblPorDes+", 'S' "+
        " ,"+dblTotal+", 'I' , '"+strMerIngEgrFisBod+"', 0 ,"+dblCosUni+", "+(dblCan*intTipMov)+", "+dblCanFac+", '"+strNomBodSal+"' ) ";
        stmLoc.executeUpdate(strSql);*/

        strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, nd_candev, nd_canorg, tx_nombodorgdes ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", 1, " +
        " '"+strCodAlt+"' "+
        " ,'"+strCodAlt2+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        ", "+objUti.codificar(strNomItm)+" "+
        ", '"+strUniMed+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+dblTotal+","+dblCosUni+", "+dblPorDes+", 'S' "+
        " ,"+dblTotal+", 'I' , '"+strMerIngEgrFisBod+"', 0 ,"+dblCosUni+", "+(dblCan*intTipMov)+", "+dblCanFac+", '"+strNomBodSal+"' ) ";
        stmLoc.executeUpdate(strSql);
       // stbDevTemp.append( strSql +" ; " );


           arlReg=new java.util.ArrayList();
           arlReg.add(0, ""+intCodEmp );
           arlReg.add(1, ""+intCodItm );
           arlItmRec.add(arlReg);


          strSql="INSERT INTO tbr_detMovInv(co_emp, co_loc, co_tipDoc, co_doc, co_reg, st_reg, st_regrep , co_emprel, co_locrel, co_tipDocrel, co_docrel, co_regrel ) " +
          "  VALUES( "+intCodEmpRec+", "+intCodLocRec+", "+intCodTipDocRec+", "+intCodDocRec+", "+intCodRegRec+", 'A', 'P' " +
          " ,"+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", 1  ) ";
          stmLoc.executeUpdate(strSql);


        strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salven=nd_salven+"+dblCan+" WHERE co_emp="+intCodEmp+" AND " +
        " co_itm="+intCodItm+" AND co_emprel="+intCodEmpCom;
        stmLoc.executeUpdate(strSql);
        //stbDevTemp.append( strSql +" ; " );

        strSql ="  UPDATE tbm_detMovInv SET nd_candev=(case when nd_candev is null then 0 else nd_candev end) + "+dblCan+" " +
        " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLocDev+" AND co_tipdoc="+intCodTipDocOc+" AND co_doc="+intCodDocOc+" "+
        " AND co_reg="+intCodRegOc;
        stmLoc.executeUpdate(strSql);
       // stbDevTemp.append( strSql +" ; " );




      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorException(Evt);     }
    catch(Exception Evt)   { blnRes=false; mostarErrorException(Evt);     }
 return blnRes;
}


private boolean actualizaStockDev(java.sql.Connection conn, java.sql.Connection connRemota, int intCodEmp, int intCodBod,  double dblCan, int intTipMov, int intCodItm, String strMerIngEgr, String str_MerIEFisBod ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  int intTipCli=3;
  double dlbCanMov=0.00;
  try{
      stmLoc=conn.createStatement();

      objInvItm.limpiarObjeto();
      objInvItm.inicializaObjeto();

      dlbCanMov=dblCan;

      objInvItm.generaQueryStock(intCodEmp, intCodItm, intCodBod, dlbCanMov, intTipMov, "N", strMerIngEgr, str_MerIEFisBod, 1);

      //stbDevInvTemp.append( objInvItm.getQueryEjecutaActStock() +" ; " );


      if(!objInvItm.ejecutaActStock(conn, intTipCli))
          return false;

       if(connRemota!=null){
            //System.out.println("remoto.."+connRemota );

           if(!objInvItm.ejecutaActStock(connRemota, intTipCli ))
             return false;
           if(!objInvItm.ejecutaVerificacionStock(connRemota, intTipCli))
             return false;
       }else{
         if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli ))
            return false;
       }
      objInvItm.limpiarObjeto();
      stmLoc.close();
      stmLoc=null;
      blnRes=true;
   }catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorException(Evt);     }
    catch(Exception Evt)   { blnRes=false; mostarErrorException(Evt);     }
 return blnRes;
}


private boolean generaDevComCab(java.sql.Connection conn, int intNumDoc, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpCom,  int TipMov, double dlbSub, double dlbValIva, double dlbTot , String strEstGuiRem , int intCodEmpRec, int intCodLocRec, int intCodTipDocRec, int intCodDocRec  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strFecSistema="";
  int intSecEmp=0;
  int intSecGrp=0;
  int intNumDocDev=0;
  String strCui="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      intSecEmp = objUltDocPrint.getNumSecDoc(conn, intCodEmp );
      intSecGrp = objUltDocPrint.getNumSecDoc(conn, 0 );
      
      datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
      strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());

//     if(TipMov==1){
//
//       if(objZafParSis.getCodigoEmpresa()==1){
//          if(intCodEmp==2) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=2854";
//          if(intCodEmp==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=3117";
//       }
//       if(objZafParSis.getCodigoEmpresa()==2){
//          if(intCodEmp==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=603";
//          if(intCodEmp==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=498";
//       }
//       if(objZafParSis.getCodigoEmpresa()==4){
//          if(intCodEmp==2) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=789";
//          if(intCodEmp==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=1039";
//        }
//    }

   if(TipMov==1 || TipMov==228){
       System.out.println("*********************************************\n JOTA \n" + TipMov + "\n +=====================================================================");
       if(intCodEmpCom==1){
          if(intCodEmp==2) { strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=2854";  }
          if(intCodEmp==4) { strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=3117";  }
       }
       if(intCodEmpCom==2){
          if(intCodEmp==1){
              if(intCodTipDoc==138){
                  strCui="MANTA ";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=603";
              }else if(intCodTipDoc==167){
                  strCui="SANTO DOMINGO";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=603";
              }
              else if(intCodTipDoc==226){
                  strCui="CUENCA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=4 ) " +
                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=603";
              }
              else{ strCui="QUITO ";
                 strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=603"; }
          }
          if(intCodEmp==4){
              if(intCodTipDoc==138){
                  strCui="MANTA ";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=498";
              } else if(intCodTipDoc==167){
                  strCui="SANTO DOMINGO";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=498";
              }
              else if(intCodTipDoc==226){
                  strCui="CUENCA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=4 ) " +
                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=498";
              }
              else{ strCui="QUITO ";
              strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=498";  }
       }}
       if(intCodEmpCom==4){
          if(intCodEmp==2){ strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=789";  }
          if(intCodEmp==1){ strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=1039";  }
        }
    }

    if(TipMov==2){
       if(objZafParSis.getCodigoEmpresa()==1){
          if(intCodEmpCom==2) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=603";
          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=1039";
       }
       if(objZafParSis.getCodigoEmpresa()==2){
          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=2854";
          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=789";
       }
       if(objZafParSis.getCodigoEmpresa()==4){
          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=3117";
          if(intCodEmpCom==2) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=498";
       }
    }

      System.out.println(" generaDevComCab --> "+ strSql );

     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){

         intNumDocDev=getUltNumDoc(conn, intCodEmp, intCodLoc, intCodTipDoc);

//         if(intDocRelEmpTemp==1) stbDocRelEmpTemp.append(" UNION ALL ");
//             stbDocRelEmpTemp.append( " SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC" );
//          intDocRelEmpTemp=1;
//
//          if(intDocRelEmp==1) stbDocRelEmp.append(" UNION ALL ");
//                stbDocRelEmp.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC" );
//           intDocRelEmp=1;


          strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
          " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot,  " +
          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
          " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv, st_creguirem ) " +
          " VALUES("+intCodEmp+", "+intCodLoc+", "+
          intCodTipDoc+", "+intCodDoc+", '"+datFecAux+"', "+rstLoc.getString("co_cli")+" ,null,'','"+
          rstLoc.getString("tx_nom")+"','"+rstLoc.getString("tx_dir")+"','"+rstLoc.getString("tx_ide")+ "','"+rstLoc.getString("tx_tel")+"', "+
          " '"+strCui+"','',"+intNumDocDev+" , "+intNumDoc+","+
          "  '' ,'' , "+dlbSub+" ,0 , "+dlbTot+", "+dlbValIva+" , 1 ,'Contado', '"+strFecSistema+"', "+
          objZafParSis.getCodigoUsuario()+", '"+strFecSistema+"', "+objZafParSis.getCodigoUsuario()+" , null, null," +
          " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', null "+
          " ,'S', 'S', 'F', '"+strEstGuiRem+"' )";
          strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDocDev+" WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc;
          stmLoc.executeUpdate(strSql);
         // stbDevTemp.append( strSql +" ; " );

//
//          strSql="INSERT INTO tbr_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, st_reg, st_regrep , co_emprel, co_locrel, co_tipDocrel, co_docrel ) " +
//          "  VALUES( "+intCodEmpRec+", "+intCodLocRec+", "+intCodTipDocRec+", "+intCodDocRec+", 'A', 'P' " +
//          " ,"+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+" ) ";
//          stmLoc.executeUpdate(strSql);




      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorException(Evt);     }
    catch(Exception Evt)   { blnRes=false; mostarErrorException(Evt);     }
 return blnRes;
}


private boolean generaDevComDet(java.sql.Connection conn, String strMerIngEgrFisBod, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intCodTipDocOc, int intCodDocOc, int intCodRegOc,  String strCodAlt, String strCodAlt2, String strNomItm, String strUniMed, double dblCan, int intCodItm, int intTipMov, int intCodEmpCom, double dblCosUni, double dblPorDes, double dblCanOc , int intCodLocDevCom, String strEstGuiRem , int intCodEmpRec, int intCodLocRec, int intCodTipDocRec , int intCodDocRec, int intCodRegRec  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

       double dblValDes= ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
       double dblTotal = (dblCan * dblCosUni)- dblValDes;
              dblTotal =  objUti.redondear(dblTotal, objZafParSis.getDecimalesBaseDatos() );

        /*strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, nd_canorg, nd_candev, tx_nombodorgdes ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", 1, " +
        " '"+strCodAlt+"' "+
        " ,'"+strCodAlt2+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        ", '"+strNomItm+"' "+
        ", '"+strUniMed+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+(dblTotal*intTipMov)+","+dblCosUni+", "+dblPorDes+", 'S' "+
        " ,"+(dblTotal*intTipMov)+", 'I' , '"+strMerIngEgrFisBod+"', 0 ,"+dblCosUni+", "+dblCanOc+", "+(dblCan*intTipMov)+", '"+strNomBodIng+"' ) ";
        stmLoc.executeUpdate(strSql);*/

        strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, nd_canorg, nd_candev, tx_nombodorgdes ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", 1, " +
        " '"+strCodAlt+"' "+
        " ,'"+strCodAlt2+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        ", "+objUti.codificar(strNomItm)+" "+
        ", '"+strUniMed+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+(dblTotal*intTipMov)+","+dblCosUni+", "+dblPorDes+", 'S' "+
        " ,"+(dblTotal*intTipMov)+", 'I' , '"+strMerIngEgrFisBod+"', 0 ,"+dblCosUni+", "+dblCanOc+", "+(dblCan*intTipMov)+", '"+strNomBodIng+"' ) ";
        stmLoc.executeUpdate(strSql);
//        stbDevTemp.append( strSql +" ; " );



          strSql="INSERT INTO tbr_detMovInv(co_emp, co_loc, co_tipDoc, co_doc, co_reg, st_reg, st_regrep , co_emprel, co_locrel, co_tipDocrel, co_docrel, co_regrel ) " +
          "  VALUES( "+intCodEmpRec+", "+intCodLocRec+", "+intCodTipDocRec+", "+intCodDocRec+", "+intCodRegRec+", 'A', 'P' " +
          " ,"+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", 1  ) ";
          stmLoc.executeUpdate(strSql);





        strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salcom=nd_salcom-"+dblCan+" WHERE co_emp="+intCodEmp+" AND " +
        " co_itm="+intCodItm+" AND co_emprel="+intCodEmpCom;
        stmLoc.executeUpdate(strSql);
//        stbDevTemp.append( strSql +" ; " );

        strSql ="  UPDATE tbm_detMovInv SET nd_candev=(case when nd_candev is null then 0 else nd_candev end) + "+dblCan+" " +
        " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLocDevCom+" AND co_tipdoc="+intCodTipDocOc+" AND co_doc="+intCodDocOc+" "+
        " AND co_reg="+intCodRegOc;
        stmLoc.executeUpdate(strSql);
//        stbDevTemp.append( strSql +" ; " );

      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorException(Evt);     }
    catch(Exception Evt)   { blnRes=false; mostarErrorException(Evt);     }
 return blnRes;
}







/**
 * Permite realizar el proceso de venta y compra automatica de la Bodega de via daule lo que ingresa a dimulti
 * @param conn   Coneccion de la base
 * @param intCodEmpRec  Código de empresa de la transferencia
 * @param intCodLocRec  Código de local de la transferencia
 * @param intCodTipDocRec Código de tipo de documento de la transferencia
 * @param intCodDocRec  Código de documento de la transferencia
 * @return true si se realizo con exito  false algun error
 */
/**
 * MODIFICADO EFLORESA: NO GENERAR DEVOLUCIONES DE COMPRA/VENTA. 
 */
private boolean realizaVenComAutDimulti(java.sql.Connection conn, int intCodEmpRec, int intCodLocRec, int intCodTipDocRec, int intCodDocRec ){
   boolean blnRes=true;
   java.sql.Statement stmLoc, stmLoc01, stmLoc02, stmLoc03;
   java.sql.ResultSet rstLoc, rstLoc01, rstLoc02, rstLoc03;
   String strSql="";
   double dlbStkBobDevCom=0;
   String strMerIngEgrFisBod="N";
   String strCodItm="", strNomBodSal="";
   String strCodEmp="";
   double dblCanVenCom=0, dblDesVta=0;
   int intCodBod=0;
   int intCodItm=0;
   double dblPreVta=0, dblCosUni=0;
   double dblCosUniVen=0;
   double dblValMarRel=0;
   String strCodAlt="";
   try{
     stmLoc=conn.createStatement();
     stmLoc01=conn.createStatement();
     stmLoc02=conn.createStatement();
     stmLoc03=conn.createStatement();




//*******************************************************************************************************************************
     // CASO BODEGA VIA DAULE
     strSql="SELECT CASE WHEN x.empven in (1) THEN 3117 else CASE WHEN x.empven in (2) THEN 498 END  END AS coclicom, " +
     " CASE WHEN x.empven in (1) THEN 1 else CASE WHEN x.empven in (2) THEN 1  END  END AS coregclicom, " +
     " CASE WHEN x.empven in (1) THEN 1039 else CASE WHEN x.empven in (2) THEN  789 END  END AS cocliven, " +
     " CASE WHEN x.co_bodGrp in (2) THEN 1  END AS coregcliven,   " +
     " x.empven, x1.co_locven,  x1.co_tipdocven, x.bodven, x.empcom, x1.co_loccom , x1.co_tipdoccom, x.bodcom , co_bodGrp FROM ( "+
     " SELECT x.co_emp as empven, x.co_bod as bodven, x1.co_emp as empcom, x.coloccom,  x1.co_bod as bodcom, x.co_bodGrp  FROM ( " +
     " select a.co_emp, a.co_bod, a1.co_bodGrp, 4 as coempenv, 3 as coloccom from tbm_detmovinv as a " +
     " INNER JOIN tbr_bodEmpBodGrp AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) " +
     " where a.co_emp="+intCodEmpRec+" and a.co_loc="+intCodLocRec+" and a.co_tipdoc="+intCodTipDocRec+" and a.co_doc="+intCodDocRec+" " +
     " AND  a.nd_can > 0  AND ( a1.co_empGrp=0 AND a1.co_bodGrp=2 )  and a.co_emp not in ( 4 )  " +
     " group by a.co_emp, a.co_bod, a1.co_bodGrp " +
     " ) AS x " +
     " INNER JOIN tbr_bodEmpBodGrp AS x1 ON (x1.co_emp=coempenv and x1.co_bodgrp=x.co_bodGrp) " +
     " INNER JOIN tbm_bod AS a1 ON (a1.co_emp=x1.co_emp AND a1.co_bod=x1.co_bod) " +
     " WHERE a1.st_reg='A'  "+
     "  ) AS x " +
     " INNER JOIN tbr_bodemp AS x1 ON(x1.co_emp=x.empcom and x1.co_loc=coloccom and x1.co_empper=x.empven and x1.co_bodper=x.bodven ) ";
     System.out.println("Paso 0 --> "+strSql );
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){


        cargarTipEmp(conn, rstLoc.getInt("empcom"), rstLoc.getInt("co_loccom") );


       strSql="select a.co_emp, a.co_loc, a.co_itm, a.co_reg,  a.tx_codalt, nd_can, a6.co_itm as coitm2 , a7.nd_cosuni, a.tx_nomitm" +
       " ,a.tx_unimed from tbm_detmovinv as a " +
       " INNER JOIN tbr_bodEmpBodGrp AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) " +
       " inner join tbm_equinv as a5 on (a5.co_emp=a.co_emp and a5.co_itm=a.co_itm ) " +
       " inner join tbm_equinv as a6 on (a6.co_emp="+rstLoc.getInt("empcom")+" and a6.co_itmmae=a5.co_itmmae ) " +
       " inner join tbm_inv as a7 on (a7.co_emp=a6.co_emp and a7.co_itm=a6.co_itm )  " +
       " where a.co_emp="+intCodEmpRec+" and a.co_loc="+intCodLocRec+" and a.co_tipdoc="+intCodTipDocRec+" and a.co_doc="+intCodDocRec+" " +
       " AND  a.nd_can > 0  AND ( a1.co_empGrp=0 AND a1.co_bodGrp=2 )    ";
       System.out.println("Paso 1 --> "+strSql );
       rstLoc01=stmLoc01.executeQuery(strSql);
       while(rstLoc01.next()){

             Glo_dblCanFalCom= rstLoc01.getDouble("nd_can");
             Glo_dblCanFalDevCom=Glo_dblCanFalCom;
             Glo_dblCanFalDevVen=Glo_dblCanFalCom;


             strCodItm=rstLoc01.getString("coitm2");



   /**************************************************************************************************************/

             strSql="SELECT a.co_emp, a3.co_empper, a.co_itm, a.co_itmrel, a3.co_loc, a3.co_locdevcom, a3.co_tipdocdevcom, a3.co_locdevven, a3.co_tipdocdevven, a2.nd_stkact, a.nd_salven "+
             " ,a2.co_bod, a5.co_itmmae, a6.tx_nom as nombod FROM tbm_invmovempgrp as a "+
             " inner join tbm_inv as a1 on (a1.co_emp=a.co_emprel and a1.co_itm=a.co_itmrel) "+
             " inner join tbr_bodemp as a3 on (a3.co_emp=a.co_emp and a3.co_loc="+rstLoc.getInt("co_loccom")+"  and a3.co_empper=a.co_emprel  and a3.st_reg='A') "+
             " inner join tbm_invbod as a2 on (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm and a2.co_bod=a3.co_bodper) "+
             " inner join tbm_equinv as a5 on (a5.co_emp=a2.co_emp and a5.co_itm=a2.co_itm )  " +
             " inner join tbm_bod as a6 on (a6.co_emp=a2.co_emp and a6.co_bod=a2.co_bod )       " +
             " inner join tbr_bodEmpBodGrp as a4 on (a4.co_emp=a2.co_emp and a4.co_bod=a2.co_bod) "+
             " where a.co_emp="+rstLoc.getInt("empcom")+" and a.co_itm="+rstLoc01.getInt("coitm2")+"  AND a.nd_salven < 0   AND  a2.nd_stkact > 0 "+
             " AND ( a4.co_empGrp=0 AND a4.co_bodGrp= 2  ) "+
             " AND  a3.co_empper="+rstLoc01.getInt("co_emp")+" ";
             System.out.println("Paso 2 --> "+strSql );
             rstLoc02=stmLoc02.executeQuery(strSql);
             if(rstLoc02.next()){

                  dlbStkBobDevCom= rstLoc02.getDouble("nd_stkact");
                  strNomBodSal=rstLoc02.getString("nombod");
                   stbDatDevCom=new StringBuffer();
                   stbDatDevVen=new StringBuffer();
                   intDatDevCom=0;
                   intDatDevVen=0;

                    if (blnReaDevComVen){
                        _getMovDevCompra(conn, rstLoc02.getInt("co_emp"),  rstLoc02.getInt("co_empper"), rstLoc02.getInt("co_locdevcom"), rstLoc02.getInt("co_tipdocdevcom"), rstLoc02.getInt("co_itmmae"), Glo_dblCanFalDevCom,  rstLoc02.getInt("co_bod"), dlbStkBobDevCom, rstLoc02.getInt("co_tipdocdevven"), strCodItm );
                        _getMovDevVenta(conn, rstLoc02.getInt("co_emp"),  rstLoc02.getInt("co_locdevven"), rstLoc02.getInt("co_tipdocdevven"),  rstLoc02.getInt("co_itmmae"), rstLoc02.getInt("co_empper"),   Glo_dblCanFalDevCom, dlbStkBobDevCom , rstLoc02.getInt("co_bod")   );
                    }
                   
                   if( (!stbDatDevCom.toString().equals(""))  && (!stbDatDevVen.toString().equals("")) ){

                        if(_RealizarDevoluciones(conn, strMerIngEgrFisBod , rstLoc02.getInt("co_locdevcom"), rstLoc02.getInt("co_locdevven"), rstLoc02.getInt("co_tipdocdevcom"), rstLoc02.getInt("co_tipdocdevven"), rstLoc02.getInt("co_bod"), dlbStkBobDevCom, strCodItm, strNomBodSal,  intCodEmpRec,  intCodLocRec,  intCodTipDocRec,  intCodDocRec , rstLoc01.getInt("co_reg") ) ){
                           blnRes=true;
                           System.out.println(" GENERA DEVOLUCIONES  ");
                         }else{
                                   System.out.println(" ROTURA EN BREAK EN DEVOLUCIONES .... ");
                                   break;
                            }
                   }
                  stbDatDevCom=null;
                  stbDatDevVen=null;
             }
             rstLoc02.close();
             rstLoc02=null;

    /**************************************************************************************************************/





       if(Glo_dblCanFalDevCom > 0 ){



          strCodItm=rstLoc01.getString("co_itm");

          strSql="select a.co_emp , a.co_bod, a.nd_stkact, a.co_itm " +
          " from tbm_invbod as a " +
          " inner join tbr_bodEmpBodGrp as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod) " +
          " where   ( a1.co_empGrp=0 AND a1.co_bodGrp= 2  ) AND a.co_itm = "+rstLoc01.getString("co_itm")+" AND a.nd_stkact > 0  " +
          " and a.co_emp = "+rstLoc01.getString("co_emp")+"  ";

          System.out.println("Paso Compra venta 1 --> "+strSql );
          rstLoc02=stmLoc02.executeQuery(strSql);
          while(rstLoc02.next()){

                 strCodEmp=rstLoc.getString("empven");

                 if( rstLoc02.getDouble("nd_stkact") >= Glo_dblCanFalDevCom )
                     dblCanVenCom=Glo_dblCanFalDevCom;
                 else
                    dblCanVenCom=rstLoc02.getDouble("nd_stkact");



            if(dblCanVenCom >  0 ){

                 intCodBod=rstLoc02.getInt("co_bod");
                 /*********************************************************************/
                 int intNumFila=1;
                  if(rstLoc.getInt("empcom")==1){
                   if(strCodEmp.equals("2")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=2854";
                   if(strCodEmp.equals("4")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=3117";
                  }
                  if(rstLoc.getInt("empcom")==2){
                   if(strCodEmp.equals("1")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=603";
                   if(strCodEmp.equals("4")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=498";
                  }
                  if(rstLoc.getInt("empcom")==4){
                   if(strCodEmp.equals("2")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=789";
                   if(strCodEmp.equals("1")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=1039";
                  }

                // System.out.println("strSql->   "+ strSql );

                  rstLoc03 = stmLoc03.executeQuery(strSql);
                  if(rstLoc03.next())
                     dblDesVta=rstLoc03.getDouble("nd_maxDes");
                  rstLoc03.close();
                  rstLoc03=null;
                /*********************************************************************/
                  strSql="SELECT a.co_emp, a.co_itm, a.nd_prevta1, nd_stkact, a.tx_codalt "
                  + " ,case when a.nd_cosuni is null then 0 else a.nd_cosuni end as cosuni "
                  + " FROM tbm_inv as a " +
                  " WHERE a.co_emp="+strCodEmp+" AND a.co_itm = "+strCodItm+" ";
                  rstLoc03 = stmLoc03.executeQuery(strSql);
                  if(rstLoc03.next()){
                     intCodItm=rstLoc03.getInt("co_itm");
                     dblPreVta=rstLoc03.getDouble("nd_prevta1");
                     strCodAlt=rstLoc03.getString("tx_codalt");
                     dblCosUniVen=rstLoc03.getDouble("cosuni");
                  }
                  rstLoc03.close();
                  rstLoc03=null;



                  /*********************************************************/

                   if(dblCosUniVen > 0 ){  // TRABAJA CON COSTO

                      dblValMarRel = objInvItm._getMargenComVenRel(Integer.parseInt(strCodEmp), rstLoc.getInt("empcom") );


                      dblPreVta= dblCosUniVen*dblValMarRel;
                      dblCosUni= dblCosUniVen*dblValMarRel;
                      dblDesVta= 0;

                   }else{ //  TRABAJA CON PRECIO DE VENTA

                    if(dblPreVta > 0 )
                      dblCosUni  =  dblPreVta - ( dblPreVta * ( dblDesVta / 100 ) );

                   }

                  /*********************************************************/




                  if(dblPreVta > 0 ){

                   if(generaVen(conn, strMerIngEgrFisBod,  Integer.parseInt(strCodEmp), intCodBod, intNumFila, dblCanVenCom, dblDesVta, intCodItm, dblPreVta, rstLoc.getInt("bodcom"), rstLoc01.getDouble("nd_cosuni"), rstLoc01.getString("tx_codalt"), rstLoc01.getString("tx_nomitm") , rstLoc01.getString("tx_unimed") , rstLoc.getInt("empcom"), rstLoc.getInt("co_loccom") ,   intCodEmpRec,  intCodLocRec,  intCodTipDocRec,  intCodDocRec , rstLoc01.getInt("co_reg") )){
                     if(generaCom(conn, strMerIngEgrFisBod, Integer.parseInt(strCodEmp), rstLoc.getInt("empcom"), intCodBodPre, intNumFila, dblCanVenCom, intCodBod, dblCosUni, strNomBodSal , rstLoc01.getString("tx_codalt"), rstLoc01.getString("tx_nomitm") , rstLoc01.getString("tx_unimed") ,rstLoc01.getInt("coitm2") , rstLoc.getInt("empcom"), rstLoc.getInt("co_loccom"), intCodEmpRec,  intCodLocRec,  intCodTipDocRec,  intCodDocRec, rstLoc01.getInt("co_reg")  )){
                         blnRes=true;
                         Glo_dblCanFalDevCom=Glo_dblCanFalDevCom-dblCanVenCom;
                         System.out.println(" GENERE COMPRA VENTA  ");

                     }else { blnRes=false;  break; }
                    }else { blnRes=false;  break; }

                }else{  mostrarMsg("El item "+strCodAlt+" no tiene precio de lista.");
                      blnRes=false;  break; }

          }

          }
          rstLoc02.close();
          rstLoc02=null;

       }

  /*************************************************************************************************************/


     }
     rstLoc01.close();
     rstLoc01=null;

     }
     rstLoc.close();
     rstLoc=null;
//*******************************************************************************************************************************
 
     stmLoc.close();
     stmLoc=null;
     stmLoc01.close();
     stmLoc01=null;
     stmLoc02.close();
     stmLoc02=null;
     stmLoc03.close();
     stmLoc03=null;


   }catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorSqlException(Evt); }
    catch(Exception evt){  blnRes=false; mostarErrorException(evt); }
  return  blnRes;
}

/**
 * Permite realizar el proceso de venta y compra automatica de la Bodega de quito, manta, santo domingo lo que ingresa a castek corespondiente  a cada local
 * @param conn   Coneccion de la base
 * @param intCodEmpRec  Código de empresa de la transferencia
 * @param intCodLocRec  Código de local de la transferencia
 * @param intCodTipDocRec Código de tipo de documento de la transferencia
 * @param intCodDocRec  Código de documento de la transferencia
 * @return true si se realizo con exito  false algun error
 */
/**
 * MODIFICADO EFLORESA: NO GENERAR DEVOLUCIONES DE COMPRA/VENTA. 
 */
private boolean realizaVenComAutCastek(java.sql.Connection conn, int intCodEmpRec, int intCodLocRec, int intCodTipDocRec, int intCodDocRec, int intCodBodGrp ){
   boolean blnRes=true;
   java.sql.Statement stmLoc, stmLoc01, stmLoc02, stmLoc03;
   java.sql.ResultSet rstLoc, rstLoc01, rstLoc02, rstLoc03;
   String strSql="";
   double dlbStkBobDevCom=0;
   String strMerIngEgrFisBod="N";
   String strCodItm="", strNomBodSal="";
   String strCodEmp="";
   double dblCanVenCom=0, dblDesVta=0;
   int intCodBod=0;
   int intCodItm=0;
   double dblPreVta=0, dblCosUni=0;
   double dblCosUniVen=0;
   double dblValMarRel=0;
   String strCodAlt="";
   try{
     stmLoc=conn.createStatement();
     stmLoc01=conn.createStatement();
     stmLoc02=conn.createStatement();
     stmLoc03=conn.createStatement();

//*******************************************************************************************************************************
     // CASO BODEGA QUITO MANTA SANTO DOMINGO
     strSql="SELECT CASE WHEN x.empven in (1) THEN 2854 else CASE WHEN x.empven in (4) THEN 789 END  END AS coclicom, " +
     " CASE WHEN x.empven in (1) THEN 1 else CASE WHEN x.empven in (4) THEN 1  END  END AS coregclicom, " +
     " CASE WHEN x.empven in (1) THEN 603 else CASE WHEN x.empven in (4) THEN  498 END  END AS cocliven,  " +
     " CASE WHEN x.co_bodGrp in (3) THEN 1 else " +
             " CASE WHEN x.co_bodGrp in (5) THEN 2 else " + 
                " CASE WHEN x.co_bodGrp in (11) THEN 3 else" +
                    " CASE WHEN x.co_bodGrp in (28) THEN 4 " +
                        " END END END END AS coregcliven, " +
     " x.empven, x1.co_locven,  x1.co_tipdocven, x.bodven, x.empcom, x1.co_loccom , x1.co_tipdoccom, x.bodcom, co_bodGrp  FROM ( "+
     " SELECT x.co_emp as empven, x.co_bod as bodven, x1.co_emp as empcom, x.coloccom,  x1.co_bod as bodcom, x.co_bodGrp  FROM ( " +
     " select a.co_emp, a.co_bod, a1.co_bodGrp, 2 as coempenv, " +
     "  CASE WHEN a1.co_bodGrp IN (3) THEN 1 ELSE " +
     "   CASE WHEN a1.co_bodGrp IN (5) THEN 4 ELSE " +
     "    CASE WHEN a1.co_bodGrp IN (11) THEN 6 ELSE " +
             "    CASE WHEN a1.co_bodGrp IN (28) THEN 10 ELSE " +
             " NULL END END END END  AS coloccom  " +
     " FROM tbm_detmovinv as a " +
     " INNER JOIN tbr_bodEmpBodGrp AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) " +
     " where a.co_emp="+intCodEmpRec+" and a.co_loc="+intCodLocRec+" and a.co_tipdoc="+intCodTipDocRec+" and a.co_doc="+intCodDocRec+"  " +
     " AND  a.nd_can > 0  AND ( a1.co_empGrp=0 AND a1.co_bodGrp = "+intCodBodGrp+"  )  and a.co_emp not in ( 2 )  " +
     " group by a.co_emp, a.co_bod, a1.co_bodGrp " +
     " ) AS x " +
     " INNER JOIN tbr_bodEmpBodGrp AS x1 ON (x1.co_emp=coempenv and x1.co_bodgrp=x.co_bodGrp) " +
     " INNER JOIN tbm_bod AS a1 ON (a1.co_emp=x1.co_emp AND a1.co_bod=x1.co_bod) " +
     " WHERE a1.st_reg='A' " +
     "  ) AS x " +
     " INNER JOIN tbr_bodemp AS x1 ON(x1.co_emp=x.empcom and x1.co_loc=coloccom and x1.co_empper=x.empven and x1.co_bodper=x.bodven ) ";
     System.out.println("ERROR: " + strSql);
     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next()){




        cargarTipEmp(conn, rstLoc.getInt("empcom"), rstLoc.getInt("co_loccom") );


       strSql="select a.co_emp, a.co_loc, a.co_itm, a.co_reg, a.tx_codalt, nd_can, a6.co_itm as coitm2 , a7.nd_cosuni, a.tx_nomitm" +
       " ,a.tx_unimed from tbm_detmovinv as a " +
       " INNER JOIN tbr_bodEmpBodGrp AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) " +
       " inner join tbm_equinv as a5 on (a5.co_emp=a.co_emp and a5.co_itm=a.co_itm ) " +
       " inner join tbm_equinv as a6 on (a6.co_emp="+rstLoc.getInt("empcom")+" and a6.co_itmmae=a5.co_itmmae ) " +
       " inner join tbm_inv as a7 on (a7.co_emp=a6.co_emp and a7.co_itm=a6.co_itm )  " +
       " where a.co_emp="+intCodEmpRec+" and a.co_loc="+intCodLocRec+" and a.co_tipdoc="+intCodTipDocRec+" and a.co_doc="+intCodDocRec+" " +
       " AND  a.nd_can > 0  AND ( a1.co_empGrp=0 AND a1.co_bodGrp  = "+intCodBodGrp+"  )     ";  // in (3,5,11,28) )
       System.out.println("Paso 1 --> "+strSql );
       rstLoc01=stmLoc01.executeQuery(strSql);
       while(rstLoc01.next()){

             Glo_dblCanFalCom= rstLoc01.getDouble("nd_can");
             Glo_dblCanFalDevCom=Glo_dblCanFalCom;
             Glo_dblCanFalDevVen=Glo_dblCanFalCom;


             strCodItm=rstLoc01.getString("coitm2");



   /**************************************************************************************************************/

             strSql="SELECT a.co_emp, a3.co_empper, a.co_itm, a.co_itmrel, a3.co_loc, a3.co_locdevcom, a3.co_tipdocdevcom, a3.co_locdevven, a3.co_tipdocdevven, a2.nd_stkact, a.nd_salven "+
             " ,a2.co_bod, a5.co_itmmae, a6.tx_nom as nombod FROM tbm_invmovempgrp as a "+
             " inner join tbm_inv as a1 on (a1.co_emp=a.co_emprel and a1.co_itm=a.co_itmrel) "+
             " inner join tbr_bodemp as a3 on (a3.co_emp=a.co_emp and a3.co_loc="+rstLoc.getInt("co_loccom")+"  and a3.co_empper=a.co_emprel  and a3.st_reg='A') "+
             " inner join tbm_invbod as a2 on (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm and a2.co_bod=a3.co_bodper) "+
             " inner join tbm_equinv as a5 on (a5.co_emp=a2.co_emp and a5.co_itm=a2.co_itm )  " +
             " inner join tbm_bod as a6 on (a6.co_emp=a2.co_emp and a6.co_bod=a2.co_bod )       " +
             " inner join tbr_bodEmpBodGrp as a4 on (a4.co_emp=a2.co_emp and a4.co_bod=a2.co_bod) "+
             " where a.co_emp="+rstLoc.getInt("empcom")+" and a.co_itm="+rstLoc01.getInt("coitm2")+"  AND a.nd_salven < 0   AND  a2.nd_stkact > 0 "+
             " AND ( a4.co_empGrp=0 AND a4.co_bodGrp  = "+intCodBodGrp+"   ) "+
             " AND  a3.co_empper="+rstLoc01.getInt("co_emp")+" ";
             System.out.println("Paso 2 --> "+strSql );
             rstLoc02=stmLoc02.executeQuery(strSql);
             if(rstLoc02.next()){

                  dlbStkBobDevCom= rstLoc02.getDouble("nd_stkact");
                  strNomBodSal=rstLoc02.getString("nombod");
                   stbDatDevCom=new StringBuffer();
                   stbDatDevVen=new StringBuffer();
                   intDatDevCom=0;
                   intDatDevVen=0; 
                   
                   if (blnReaDevComVen){ 
                       _getMovDevCompra(conn, rstLoc02.getInt("co_emp"),  rstLoc02.getInt("co_empper"), rstLoc02.getInt("co_locdevcom"), rstLoc02.getInt("co_tipdocdevcom"), rstLoc02.getInt("co_itmmae"), Glo_dblCanFalDevCom,  rstLoc02.getInt("co_bod"), dlbStkBobDevCom, rstLoc02.getInt("co_tipdocdevven"), strCodItm );
                       _getMovDevVenta(conn, rstLoc02.getInt("co_emp"),  rstLoc02.getInt("co_locdevven"), rstLoc02.getInt("co_tipdocdevven"),  rstLoc02.getInt("co_itmmae"), rstLoc02.getInt("co_empper"),   Glo_dblCanFalDevCom, dlbStkBobDevCom , rstLoc02.getInt("co_bod")   );
                   }

                   if( (!stbDatDevCom.toString().equals(""))  && (!stbDatDevVen.toString().equals("")) ){

                        if(_RealizarDevoluciones(conn, strMerIngEgrFisBod , rstLoc02.getInt("co_locdevcom"), rstLoc02.getInt("co_locdevven"), rstLoc02.getInt("co_tipdocdevcom"), rstLoc02.getInt("co_tipdocdevven"), rstLoc02.getInt("co_bod"), dlbStkBobDevCom, strCodItm, strNomBodSal,  intCodEmpRec,  intCodLocRec,  intCodTipDocRec,  intCodDocRec , rstLoc01.getInt("co_reg")  ) ){
                           blnRes=true;
                           System.out.println(" GENERA DEVOLUCIONES  ");
                         }else{
                                   System.out.println(" ROTURA EN BREAK EN DEVOLUCIONES .... ");
                                   break;
                            }
                   }
                  stbDatDevCom=null;
                  stbDatDevVen=null;
             }
             rstLoc02.close();
             rstLoc02=null;

    /**************************************************************************************************************/





       if(Glo_dblCanFalDevCom > 0 ){



          strCodItm=rstLoc01.getString("co_itm");

          strSql="select a.co_emp , a.co_bod, a.nd_stkact, a.co_itm " +
          " from tbm_invbod as a " +
          " inner join tbr_bodEmpBodGrp as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod) " +
          " where   ( a1.co_empGrp=0 AND a1.co_bodGrp  = "+intCodBodGrp+"  ) AND a.co_itm = "+rstLoc01.getString("co_itm")+" AND a.nd_stkact > 0  " +
          " and a.co_emp = "+rstLoc01.getString("co_emp")+"  ";

          System.out.println("Paso Compra venta 1 --> "+strSql );
          rstLoc02=stmLoc02.executeQuery(strSql);
          while(rstLoc02.next()){

                 strCodEmp=rstLoc.getString("empven");

                 if( rstLoc02.getDouble("nd_stkact") >= Glo_dblCanFalDevCom )
                     dblCanVenCom=Glo_dblCanFalDevCom;
                 else
                    dblCanVenCom=rstLoc02.getDouble("nd_stkact");



            if(dblCanVenCom >  0 ){

                 intCodBod=rstLoc02.getInt("co_bod");
                 /*********************************************************************/
                 int intNumFila=1;
                  if(rstLoc.getInt("empcom")==1){
                   if(strCodEmp.equals("2")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=2854";
                   if(strCodEmp.equals("4")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=3117";
                  }
                  if(rstLoc.getInt("empcom")==2){
                   if(strCodEmp.equals("1")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=603";
                   if(strCodEmp.equals("4")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=498";
                  }
                  if(rstLoc.getInt("empcom")==4){
                   if(strCodEmp.equals("2")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=789";
                   if(strCodEmp.equals("1")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=1039";
                  }

                // System.out.println("strSql->   "+ strSql );

                  rstLoc03 = stmLoc03.executeQuery(strSql);
                  if(rstLoc03.next())
                     dblDesVta=rstLoc03.getDouble("nd_maxDes");
                  rstLoc03.close();
                  rstLoc03=null;
                /*********************************************************************/
                  strSql="SELECT a.co_emp, a.co_itm, a.nd_prevta1, nd_stkact, a.tx_codalt "
                  + "  ,case when a.nd_cosuni is null then 0 else a.nd_cosuni end as cosuni "
                  + " FROM tbm_inv as a " +
                  " WHERE a.co_emp="+strCodEmp+" AND a.co_itm = "+strCodItm+" ";
                  rstLoc03 = stmLoc03.executeQuery(strSql);
                  if(rstLoc03.next()){
                     intCodItm=rstLoc03.getInt("co_itm");
                     dblPreVta=rstLoc03.getDouble("nd_prevta1");
                     strCodAlt=rstLoc03.getString("tx_codalt");
                     dblCosUniVen=rstLoc03.getDouble("cosuni");
                  }
                  rstLoc03.close();
                  rstLoc03=null;

   
                  /*********************************************************/

                   if(dblCosUniVen > 0 ){  // TRABAJA CON COSTO

                      dblValMarRel = objInvItm._getMargenComVenRel(Integer.parseInt(strCodEmp), rstLoc.getInt("empcom") );

                      dblPreVta= dblCosUniVen*dblValMarRel;  // dblCosUniVen*1.05;
                      dblCosUni= dblCosUniVen*dblValMarRel;
                      dblDesVta= 0;

                   }else{ //  TRABAJA CON PRECIO DE VENTA

                    if(dblPreVta > 0 )
                      dblCosUni  =  dblPreVta - ( dblPreVta * ( dblDesVta / 100 ) );

                   }

                  /*********************************************************/
  

                  if(dblPreVta > 0 ){

                   if(generaVen(conn, strMerIngEgrFisBod,  Integer.parseInt(strCodEmp), intCodBod, intNumFila, dblCanVenCom, dblDesVta, intCodItm, dblPreVta, rstLoc.getInt("bodcom"), rstLoc01.getDouble("nd_cosuni"), rstLoc01.getString("tx_codalt"), rstLoc01.getString("tx_nomitm") , rstLoc01.getString("tx_unimed") , rstLoc.getInt("empcom"), rstLoc.getInt("co_loccom"),  intCodEmpRec,  intCodLocRec,  intCodTipDocRec,  intCodDocRec , rstLoc01.getInt("co_reg") )){
                     if(generaCom(conn, strMerIngEgrFisBod, Integer.parseInt(strCodEmp), rstLoc.getInt("empcom"), intCodBodPre, intNumFila, dblCanVenCom, intCodBod, dblCosUni, strNomBodSal , rstLoc01.getString("tx_codalt"), rstLoc01.getString("tx_nomitm") , rstLoc01.getString("tx_unimed") ,rstLoc01.getInt("coitm2") , rstLoc.getInt("empcom"), rstLoc.getInt("co_loccom"), intCodEmpRec,  intCodLocRec,  intCodTipDocRec,  intCodDocRec , rstLoc01.getInt("co_reg") )){
                         blnRes=true;
                         Glo_dblCanFalDevCom=Glo_dblCanFalDevCom-dblCanVenCom;
                         System.out.println(" GENERE COMPRA VENTA  ");

                     }else { blnRes=false;  break; }
                    }else { blnRes=false;  break; }

                }else{  mostrarMsg("El item "+strCodAlt+" no tiene precio de lista.");
                      blnRes=false;  break; }

          }

          }
          rstLoc02.close();
          rstLoc02=null;

       }

  /*************************************************************************************************************/












     }
     rstLoc01.close();
     rstLoc01=null;



     }
     rstLoc.close();
     rstLoc=null;
//*******************************************************************************************************************************

     stmLoc.close();
     stmLoc=null;
     stmLoc01.close();
     stmLoc01=null;
     stmLoc02.close();
     stmLoc02=null;
     stmLoc03.close();
     stmLoc03=null;

   }catch(java.sql.SQLException Evt){ blnRes=false; mostarErrorSqlException(Evt); }
    catch(Exception evt){  blnRes=false; mostarErrorException(evt); }
  return  blnRes;
}









private boolean generaVen(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpRel, int intCodLocRel, int intCodTipDocRel, int intCodBod, int intCodEmpCom, int intCodBodGrp, String strSqlCLi ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  StringBuffer stbins;
  String strSql="";
  int intCodDocRel=0;
  
  int intCodRegDetRel=0;
  double dlbDesVta=35;
  int intEst=0;
  int intEstStk=0;
  int intTipCli=3;
   try{
    if(conn!=null){
      stmLoc=conn.createStatement();
        
      strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
      " co_emp="+intCodEmpRel+" AND co_loc="+intCodLocRel+" AND co_tipDoc="+intCodTipDocRel;
      rstLoc = stmLoc.executeQuery(strSql);
      if(rstLoc.next()) intCodDocRel=rstLoc.getInt("co_doc");
      rstLoc.close();
      rstLoc=null;


      String strConfDevVen="F";
      String strMerIngEgr="E";
      String strIngEgrFisBod="N";
      String strEstGuiRem="S";


      stbins=new StringBuffer();

      objInvItm.limpiarObjeto();
      objInvItm.inicializaObjeto();

       strSql="SELECT *, (nd_can*dblCosUni) as ndtot FROM ( " +
       " SELECT  a.co_reg, eqinv.co_itm as coitmven , eqinv2.co_itm as coitmcom, inv.nd_prevta1, inv.tx_codalt, inv.tx_nomitm, a.tx_unimed, a.nd_can " +
       " , (inv.nd_prevta1-(inv.nd_prevta1* 0.35)) as dblCosUni FROM tbm_detmovinv as a "+
       " INNER JOIN tbm_inv AS inv ON (inv.co_emp=a.co_emp AND inv.co_itm=a.co_itm) "+
       " INNER JOIN tbm_equinv AS eqinv ON (eqinv.co_emp=a.co_emp and eqinv.co_itm=a.co_itm) "+
       " INNER JOIN tbm_equinv AS eqinv2 ON (eqinv2.co_itmmae=eqinv.co_itmmae and eqinv2.co_emp= "+intCodEmpCom+" ) "+
       " INNER JOIN tbr_bodEmpBodGrp AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) "+
       " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and  a.nd_can > 0 "+
       " and ( a1.co_empGrp=0 AND a1.co_bodGrp= "+intCodBodGrp+" )  and a.co_emp not in ("+intCodEmpCom+") ) AS x  ";
       rstLoc = stmLoc.executeQuery(strSql);
       while(rstLoc.next()){

            intCodRegDetRel++;
            strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+
            " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +
            " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +
            ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni ) " +
            " VALUES("+intCodEmpRel+", "+intCodLocRel+", "+intCodTipDocRel+", "+intCodDocRel+", "+intCodRegDetRel+", " +
            " '"+rstLoc.getString("tx_codalt")+"', '"+rstLoc.getString("tx_codalt")+"', "+rstLoc.getInt("coitmven")+", "+
            " "+rstLoc.getInt("coitmven")+", '"+rstLoc.getString("tx_nomitm")+"', '"+rstLoc.getString("tx_unimed")+"', "+
            " "+intCodBod+", "+rstLoc.getDouble("nd_can")*-1+", "+rstLoc.getDouble("ndtot")+","+rstLoc.getDouble("dblCosUni")+", " +
            " "+rstLoc.getDouble("ndtot")+","+rstLoc.getDouble("nd_prevta1")+", "+dlbDesVta+", 'S', "+
            " "+rstLoc.getDouble("ndtot")+", 'I' , '"+strIngEgrFisBod+"', 0 ,"+rstLoc.getDouble("dblCosUni")+" ) ; ";
            stbins.append(strSql);
            intEst=1;


            strSql="INSERT INTO tbr_detMovInv( co_emp, co_loc, co_tipdoc , co_doc, co_reg, st_reg, " +
            " co_emprel, co_locrel, co_tipdocrel , co_docrel, co_regrel, st_regrep ) "+
            " VALUES("+intCodEmp+","+intCodLoc+","+intCodTipDoc+", "+intCodDoc+", "+rstLoc.getInt("co_reg")+", 'A' " +
            " "+intCodEmpRel+", "+intCodLocRel+", "+intCodTipDocRel+", "+intCodDocRel+", "+intCodRegDetRel+", 'P' ) ";
            stbins.append(strSql);


            strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salven=nd_salven-"+rstLoc.getDouble("nd_can")+" WHERE co_emp="+intCodEmpRel+" AND " +
            " co_itm="+rstLoc.getInt("coitmven")+" AND co_emprel="+intCodEmpCom+" ; ";
            stbins.append(strSql);

            // stmLoc.executeUpdate(strSql);

            objInvItm.generaQueryStock(intCodEmpRel, rstLoc.getInt("coitmven"), intCodBod, rstLoc.getDouble("nd_can"), -1, "N", strMerIngEgr, "E", 1 );
            //intEstStk=1;
       }
       rstLoc.close();
       rstLoc=null;


       if(intCodRegDetRel > 0 ){
             if(generaVenCab(conn, intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel,  strEstGuiRem , "F", strSqlCLi )){
              if(objInvItm.ejecutaActStock(conn, intTipCli )){
               if(objInvItm.ejecutaVerificacionStock(conn, intTipCli)){
                   stmLoc.executeUpdate(stbins.toString());
                   blnRes=true;
             }}}
      }
    objInvItm.limpiarObjeto();
          
    stbins=null;
    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);     }
 return blnRes;
}


private boolean generaVenCab(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strEstGuiRem, String strConfDevVen, String strSqlCLi ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strFecSistema="";
  int intSecEmp=0;
  int intSecGrp=0;
  int intNumDoc=0;
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

       intSecEmp = objUltDocPrint.getNumSecDoc(conn, intCodEmp );
       intSecGrp = objUltDocPrint.getNumSecDoc(conn, 0 );

      datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
      strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());


//     strSql="SELECT a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1, a1.tx_obs1 as cuidad FROM tbm_cli as a " +
//     " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 )  " +
//     " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=2854 ";
     rstLoc=stmLoc.executeQuery(strSqlCLi);
     if(rstLoc.next()){

          intNumDoc=getUltNumDoc(conn, intCodEmp, intCodLoc, intCodTipDoc);

          strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
          " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, ne_numgui, " +
          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
          " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv , st_creguirem ) " +
          " VALUES("+intCodEmp+", "+intCodLoc+", "+
          intCodTipDoc+", "+intCodDoc+", '"+datFecAux+"', "+rstLoc.getString("co_cli")+" ,null,'','"+
          rstLoc.getString("tx_nom")+"','"+rstLoc.getString("tx_dir")+"','"+rstLoc.getString("tx_ide")+ "','"+rstLoc.getString("tx_tel1")+"'," +
          "'"+rstLoc.getString("cuidad")+"','', "+intNumDoc+" , 0,"+
          " "+intNumDoc+", '' ,'' , 0 ,12 ,0, 0 , 2 ,'Credito 30', '"+strFecSistema+"', "+
          objZafParSis.getCodigoUsuario()+", '"+strFecSistema+"', "+objZafParSis.getCodigoUsuario()+" , null, null," +
          " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', null "+
          " ,'S', 'S', '"+strConfDevVen+"', '"+strEstGuiRem+"' )";


//           if(intDocRelEmpLoc==1) stbDocRelEmpLoc.append(" UNION ALL ");
//               stbDocRelEmpLoc.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC ");
//           intDocRelEmpLoc=1;

          strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDoc+" WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc;
          
          //System.out.println("-->" + strSql );
          
          stmLoc.executeUpdate(strSql);

          blnRes=true;
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
     

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);     }
 return blnRes;
}



private boolean generaCom(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpRel, int intCodLocRel, int intCodTipDocRel, int intCodBod, int intCodEmpCom, int intCodBodGrp, String strSqlCLi ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  StringBuffer stbins;
  String strSql="";
  int intCodDocRel=0;

  int intCodRegDetRel=0;
  double dlbDesVta=35;
  int intEst=0;
  int intEstStk=0;
  int intTipCli=3;
   try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
      " co_emp="+intCodEmpRel+" AND co_loc="+intCodLocRel+" AND co_tipDoc="+intCodTipDocRel;
      rstLoc = stmLoc.executeQuery(strSql);
      if(rstLoc.next()) intCodDocRel=rstLoc.getInt("co_doc");
      rstLoc.close();
      rstLoc=null;


      String strConfDevVen="F";
      String strMerIngEgr="E";
      String strIngEgrFisBod="N";
      String strEstGuiRem="S";


      stbins=new StringBuffer();

      objInvItm.limpiarObjeto();
      objInvItm.inicializaObjeto();

       strSql="SELECT *, (nd_can*dblCosUni) as ndtot FROM ( " +
       " SELECT  a.co_reg,  eqinv.co_itm as coitmven , eqinv2.co_itm as coitmcom, inv.nd_prevta1, inv.tx_codalt, inv.tx_nomitm, a.tx_unimed, a.nd_can " +
       " , (inv.nd_prevta1-(inv.nd_prevta1* 0.35)) as dblCosUni FROM tbm_detmovinv as a "+
       " INNER JOIN tbm_inv AS inv ON (inv.co_emp=a.co_emp AND inv.co_itm=a.co_itm) "+
       " INNER JOIN tbm_equinv AS eqinv ON (eqinv.co_emp=a.co_emp and eqinv.co_itm=a.co_itm) "+
       " INNER JOIN tbm_equinv AS eqinv2 ON (eqinv2.co_itmmae=eqinv.co_itmmae and eqinv2.co_emp= "+intCodEmpCom+" ) "+
       " INNER JOIN tbr_bodEmpBodGrp AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) "+
       " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and  a.nd_can > 0 "+
       " and ( a1.co_empGrp=0 AND a1.co_bodGrp= "+intCodBodGrp+" )  and a.co_emp not in ("+intCodEmpCom+") ) AS x  ";
       rstLoc = stmLoc.executeQuery(strSql);
       while(rstLoc.next()){

            intCodRegDetRel++;
            strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+
            " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +
            " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +
            ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni ) " +
            " VALUES("+intCodEmpRel+", "+intCodLocRel+", "+intCodTipDocRel+", "+intCodDocRel+", "+intCodRegDetRel+", " +
            " '"+rstLoc.getString("tx_codalt")+"', '"+rstLoc.getString("tx_codalt")+"', "+rstLoc.getInt("coitmcom")+", "+
            " "+rstLoc.getInt("coitmcom")+", '"+rstLoc.getString("tx_nomitm")+"', '"+rstLoc.getString("tx_unimed")+"', "+
            " "+intCodBod+", "+rstLoc.getDouble("nd_can")+", "+rstLoc.getDouble("ndtot")+","+rstLoc.getDouble("dblCosUni")+", " +
            " "+rstLoc.getDouble("ndtot")+","+rstLoc.getDouble("dblCosUni")+", 0, 'S', "+
            " "+rstLoc.getDouble("ndtot")+", 'I' , '"+strIngEgrFisBod+"', 0 ,"+rstLoc.getDouble("dblCosUni")+" ) ; ";
            stbins.append(strSql);


            strSql="INSERT INTO tbr_detMovInv( co_emp, co_loc, co_tipdoc , co_doc, co_reg, st_reg, " +
            " co_emprel, co_locrel, co_tipdocrel , co_docrel, co_regrel, st_regrep ) "+
            " VALUES("+intCodEmp+","+intCodLoc+","+intCodTipDoc+", "+intCodDoc+", "+rstLoc.getInt("co_reg")+", 'A' " +
            " "+intCodEmpRel+", "+intCodLocRel+", "+intCodTipDocRel+", "+intCodDocRel+", "+intCodRegDetRel+", 'P' ) ";
            stbins.append(strSql);
            



            strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salcom=nd_salcom-"+rstLoc.getDouble("nd_can")+" WHERE co_emp="+intCodEmpCom+" AND " +
            " co_itm="+rstLoc.getInt("coitmcom")+" AND co_emprel="+intCodEmpRel+" ; ";
            stbins.append(strSql);

            // stmLoc.executeUpdate(strSql);

            objInvItm.generaQueryStock(intCodEmpRel, rstLoc.getInt("coitmcom"), intCodBod, rstLoc.getDouble("nd_can"), 1, "N", strMerIngEgr, "E", 1);
            //intEstStk=1;
       }
       rstLoc.close();
       rstLoc=null;




        if(intCodRegDetRel > 0 ){
             if(generaVenCab(conn, intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel,  strEstGuiRem , "F", strSqlCLi )){
              if(objInvItm.ejecutaActStock(conn, intTipCli )){
               if(objInvItm.ejecutaVerificacionStock(conn, intTipCli)){
                   stmLoc.executeUpdate(stbins.toString());
                   blnRes=true;
              }}}
        }

     objInvItm.limpiarObjeto();
          
    stbins=null;
    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(jIntfra, Evt);     }
 return blnRes;
}



/**
 * Permite obtener el ultimo numero de documento en la tabla tbm_cabtipdoc
 * @param conn  Coneccion de la base
 * @param intCodEmp  Código de empresa
 * @param intCodLoc   Código de local
 * @param intCodTipDoc  Código de tipo de documento
 * @return el numero de documento
 */
private int getUltNumDoc(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc ){
  int intUltNumDoc=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

     strSql="SELECT CASE WHEN (ne_ultdoc+1) IS NULL THEN 1 ELSE (ne_ultdoc+1) END AS numdoc FROM tbm_cabtipdoc " +
     "WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc;
     rstLoc = stmLoc.executeQuery(strSql);
     if(rstLoc.next()) intUltNumDoc = rstLoc.getInt("numdoc");

    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;

 }}catch(java.sql.SQLException ex) {  objUti.mostrarMsgErr_F1(jIntfra, ex);   }
   catch(Exception e) {  objUti.mostrarMsgErr_F1(jIntfra, e);  }
 return intUltNumDoc;
}


public void mostrarMsg(String strMsg) {
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    if(jDialo==null)
    oppMsg.showMessageDialog(jIntfra,strMsg,strTit,javax.swing.JOptionPane.OK_OPTION);
    else oppMsg.showMessageDialog(jDialo,strMsg,strTit,javax.swing.JOptionPane.OK_OPTION);
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

}
  