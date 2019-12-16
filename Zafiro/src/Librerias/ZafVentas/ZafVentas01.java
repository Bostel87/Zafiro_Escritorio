/* ESTA CLASE  PERMITE ANULAR LA FACTURA Y LA GUIA DE REMISION
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafVentas;
  
/**
 *
 * @author javier
 * Version 0.1.1 : Se adiciona bodega 28 para nueva sucursal de Cuenca(No estoy segura si esta clase debió actualizarse).
 */
public class ZafVentas01 {
   Librerias.ZafParSis.ZafParSis objZafParSis;
   Librerias.ZafUtil.ZafUtil objUti;
   javax.swing.JInternalFrame jIntfra=null;
   javax.swing.JDialog jDialo=null;
   private Librerias.ZafUtil.ZafCtaCtb_dat  objZafCtaCtb_dat;
   Librerias.ZafInventario.ZafInvItm objInvItm;
   private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblModPag;
   private Librerias.ZafDate.ZafDatePicker txtFecDoc;
   Librerias.ZafUtil.UltDocPrint objUltDocPrint;

   int intCodMotDoc=0;
   int intCodTipPerEmp=0;

   public int intCodDocNueFac=0;

  double dblSubToTalFac=0;
  double dblValIvaFac=0;
  double dblToTalFac=0;
  double dblTotalDescuento=0;

  double dblTotalTrans=0;
  double dblTotalSinTrans=0;

  private java.util.Date datFecAux;

   private java.util.ArrayList arlRegAniMes, arlDatAniMes;

   final int INT_ARL_ANI_CIE=0;
   final int INT_ARL_MES_CIE=1;
   final int INT_ARL_TIP_CIE=2;

  public String StrItmFal="";

  final int intarreglonum[] = new int[10];
  final int intarreglodia[] = new int[10];
  final int intCanArr[]= new int[1];

   String strCodTipPerCli="";
   String strarreglosop[] = new String[10];

    final int INT_TBL_PAGLIN=0; //Linea de pago
    final int INT_TBL_PAGCRE=1; // Dias de credito
    final int INT_TBL_PAGFEC=2; // Fecha de vencimiento
    final int INT_TBL_PAGRET=3; // porcentaje de retencion
    final int INT_TBL_PAGMON=4; // monto de pago
    final int INT_TBL_PAGGRA=5; // dias de gracias
    final int INT_TBL_PAGCOD=6; // codigo de retencion
    final int INT_TBL_PAGSOP=7; // soporte de cheque

    javax.swing.JTable tblPag = new javax.swing.JTable();

    StringBuffer stbDatDocRel=null;
    int intDatDocRel=0;


/** Creates a new instance of ZafInv */
public ZafVentas01(javax.swing.JInternalFrame jfrthis, Librerias.ZafParSis.ZafParSis obj){
  try{
        this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
        objInvItm = new Librerias.ZafInventario.ZafInvItm(jfrthis, objZafParSis );
        jIntfra = jfrthis;
        objUti = new Librerias.ZafUtil.ZafUtil();
        arlDatAniMes = new java.util.ArrayList();
        objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);

          configurarTablaPago();

    }catch (CloneNotSupportedException e){ objUti.mostrarMsgErr_F1(jfrthis, e);  }
}

public ZafVentas01(javax.swing.JDialog jDial, Librerias.ZafParSis.ZafParSis obj){
   try{
       this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
       objInvItm = new Librerias.ZafInventario.ZafInvItm(jDial, objZafParSis );
        jDialo = jDial;
        objUti = new Librerias.ZafUtil.ZafUtil();
        arlDatAniMes = new java.util.ArrayList();
        objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);

        configurarTablaPago();
        
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


public void MensajeInf(String strMsg){
   javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
   String strTit="Mensaje del sistema Zafiro";
   obj.showMessageDialog(jIntfra,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
   obj=null;
}



private boolean configurarTablaPago(){
 boolean blnRes=false;
 try{
      java.util.Vector vecCabPAg=new java.util.Vector();    //Almacena cabeceras
      vecCabPAg.clear();
      vecCabPAg.add(INT_TBL_PAGLIN,"");
      vecCabPAg.add(INT_TBL_PAGCRE,"Días.Crédito");
      vecCabPAg.add(INT_TBL_PAGFEC,"Fec.Vencimiento");
      vecCabPAg.add(INT_TBL_PAGRET,"%Retención");
      vecCabPAg.add(INT_TBL_PAGMON,"Monto");
      vecCabPAg.add(INT_TBL_PAGGRA,"Días.Gracia");
      vecCabPAg.add(INT_TBL_PAGCOD,"Cod.Ret");
      vecCabPAg.add(INT_TBL_PAGSOP,"Tx_Sop");
      objTblModPag=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
      objTblModPag.setHeader(vecCabPAg);

      tblPag.setModel(objTblModPag);
      tblPag.setRowSelectionAllowed(true);
      tblPag.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

         tblPag.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

      tblPag.getTableHeader().setReorderingAllowed(false);

      blnRes=true;

  }catch (Exception e){ blnRes=false;  }
  return blnRes;
}



public boolean _getExisCanNunRec(java.sql.Connection conn, int intCodEmpFac, int intCodLocFac, int intCodTipDocFac, int intCodDocFac ){
 boolean blnRes=false;;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();


      strSql=" select * from ( "
      + " select  co_itm, tx_codalt,  sum( cannunrec ) as cannunrec from (  "
      + " select a1.co_itm, a1.tx_codalt,  (case when a1.nd_cannunrec is null then 0 else a1.nd_cannunrec end ) as cannunrec  from ( "
      + " select co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel  from tbr_detmovinv "
      + " where  co_emp="+intCodEmpFac+" AND co_loc="+intCodLocFac+" AND co_tipDoc="+intCodTipDocFac+" and co_doc="+intCodDocFac+" "
      + " group by co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel "
      + " ) as a"
      + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel )  "
      + " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc )   "
      + " left join tbm_cli as a3 on (a3.co_emp=a2.co_emp and a3.co_cli=a2.co_cli  and a3.co_empgrp =0    )   "
      + " where   a2.st_coninv not in ('F') "
      + "  ) as x  group by co_itm, tx_codalt "
      + "  ) as x  where  cannunrec > 0 ";
      
     // System.out.println(" _getExisCanNunRec  "+strSql );

      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){

          StrItmFal +="Item "+rstLoc.getString("tx_codalt")+" faltante  "+objUti.redondear(rstLoc.getDouble("cannunrec"),2)+" \n ";
          blnRes=true;
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;


 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}



/**
 * Funcion que permite saber si hay docuementos de ingreso pendiente de confirmar
 * @param conn
 * @param intCodEmpFac
 * @param intCodLocFac
 * @param intCodTipDocFac
 * @param intCodDocFac
 * @return
 */
public boolean _getVerificaDocPendConf(java.sql.Connection conn, int intCodEmpFac, int intCodLocFac, int intCodTipDocFac, int intCodDocFac ){
 boolean blnRes=false;;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();


      strSql="select * from ( "
      + " select  can, cantotconf ,  case when cantotconf = can then 'C' else  case when cantotconf > 0 then  'E' else 'P' end end  as estconf from (  "
      + " select can, (cancon+cannunrec+canmalest) as cantotconf from ( "
      + " SELECT   "
      + " sum(case when a1.nd_can is null then 0 else a1.nd_can end ) as can,   "
      + " sum(case when a1.nd_cancon is null then 0 else a1.nd_cancon end ) as cancon, "
      + " sum(case when a1.nd_cannunrec is null then 0 else a1.nd_cannunrec end ) as cannunrec, "
      + " sum(case when a1.nd_cantotmalest is null then 0 else a1.nd_cantotmalest end ) as canmalest "
      + " from (  "
      + "  select co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel  from tbr_detmovinv  "
      + "  where co_emp="+intCodEmpFac+" AND co_loc="+intCodLocFac+" AND co_tipDoc="+intCodTipDocFac+" and co_doc="+intCodDocFac+"   "
      + "  group by co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel "
      + " ) as a  "
      + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel )   "
      + " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc )   "
      + " left join tbm_cli as a3 on (a3.co_emp=a2.co_emp and a3.co_cli=a2.co_cli and a3.co_empgrp =0  )  "
      + " WHERE  a2.st_coninv not in ('F') and a1.st_meringegrfisbod='S'   "
      + "  ) as x ) as x ) as x where  estconf in ('E','P') ";
     
     // System.out.println(" _getVerificaDocPendConf "+strSql  );
      
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
         blnRes=true;
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;


 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}



/**
 * Esta funcion verifica si existe documento de ingreso relacionados a la factura.
 * @param conn
 * @param intCodEmpFac
 * @param intCodLocFac
 * @param intCodTipDocFac
 * @param intCodDocFac
 * @return true  si existe   false no existe
 */
public boolean _getVerificaExisDocIng(java.sql.Connection conn, int intCodEmpFac, int intCodLocFac, int intCodTipDocFac, int intCodDocFac ){
 boolean blnRes=false;;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();

         
      strSql="SELECT a.co_doc from tbr_detmovinv as a "
      + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
      + " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) "
      + " left join tbm_cli as a3 on (a3.co_emp=a2.co_emp and a3.co_cli=a2.co_cli and a3.co_empgrp =0 )  "
      + " WHERE a.co_emp="+intCodEmpFac+" AND a.co_loc="+intCodLocFac+" AND a.co_tipDoc="+intCodTipDocFac+" and a.co_doc="+intCodDocFac+" "
      + " and  a2.st_coninv not in ('F')    "
      + " GROUP BY a.co_doc ";
      
      //System.out.println(" _getVerificaExisDocIng "+strSql  );
      
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
           blnRes=true;
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;

}}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}




public boolean cargarTipEmp(java.sql.Connection conn, int intCodEmp, int intCodLoc ){
 boolean blnRes=false;
 java.sql.Statement stmTipEmp;
 java.sql.ResultSet rstEmp;
 String sSql;
 try{
    if( conn !=null){
        stmTipEmp=conn.createStatement();

        sSql="select b.co_tipper , b.tx_descor , round(a.nd_ivaVen,2) as porIva , bod.co_bod, a1.tx_dir, a1.tx_nom as nombod  FROM  tbm_emp as a " +
        " left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper)" +
        " left join tbr_bodloc as bod on(bod.co_emp=a.co_emp and bod.co_loc="+intCodLoc+" and bod.st_reg='P')  " +
        " inner join tbm_bod as a1 on (a1.co_emp=bod.co_emp and a1.co_bod=bod.co_bod ) " +
        " where a.co_emp="+intCodEmp;
        rstEmp = stmTipEmp.executeQuery(sSql);
        if(rstEmp.next()){
            intCodTipPerEmp = rstEmp.getInt("co_tipper");
        }
        rstEmp.close();
        stmTipEmp.close();
        stmTipEmp = null;
        rstEmp = null;
        blnRes=true;

 }}catch(java.sql.SQLException Evt){  System.out.println(""+Evt ); }
  catch(Exception Evt){  System.out.println(""+Evt ); }
 return blnRes;
}

public boolean FormaRetencion(java.sql.Connection conTmp, int intCodEmp ){
boolean blnRes=false;
java.sql.Statement stmTmp;
java.sql.ResultSet rst;
try {
    if (conTmp!=null) {
        stmTmp = conTmp.createStatement();
        String sql = "SELECT co_mot FROM tbm_motdoc WHERE co_emp="+intCodEmp+" AND tx_tipmot='B'";
        rst=stmTmp.executeQuery(sql);
        while(rst.next()){
            intCodMotDoc = rst.getInt(1);
            blnRes=true;
        }

        rst.close();
        stmTmp.close();
        rst=null;
        stmTmp=null;
    }
}catch(java.sql.SQLException Evt){  System.out.println(""+Evt );    }
catch(Exception Evt)  {  System.out.println(""+Evt );    }
return blnRes;
}


 
public boolean _AnularFacGuiCreNueFacGui(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intNumDoc, boolean blnCreNueFac , double dblPorIva ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 int intCodDoc=0;
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();

       stbDatDocRel=new StringBuffer();
       intDatDocRel=0;
       
      strSql="SELECT a.co_forpag, a3.co_tipper, a.co_doc as codocfac, a.st_reg, a.co_mnu, a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.ne_numdoc "
      + " FROM tbm_cabmovinv as a "
      + " INNER join tbm_detguirem as a1 on (a1.co_emprel=a.co_emp and a1.co_locrel=a.co_loc and a1.co_tipdocrel=a.co_tipdoc and a1.co_docrel=a.co_doc )  "
      + " INNER join tbm_cabguirem as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc )  "
      + " INNER join tbm_cli as a3 on (a3.co_emp=a.co_emp and a3.co_cli=a.co_cli )"
      + " WHERE a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+""
      + " AND a.co_tipdoc="+intCodTipDoc+" and a.ne_numdoc=0 and a.st_reg not in ('E','I') and a.ne_numcot="+intNumDoc+" "
      + " GROUP BY a.co_forpag, a3.co_tipper, a.co_doc, a.st_reg, a.co_mnu, a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.ne_numdoc ";
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){

         if(rstLoc.getInt("ne_numdoc") > 0 ){
             MensajeInf("LA GUIA DE REMISION TIENE ASIGNADO NUMERO NO ES POSIBLE SEGUIR CON EL PROCESO..");
         }else{
            intCodDoc=rstLoc.getInt("codocfac");

            if(_AnularFacGui(conn, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc,intCodDoc ) ){
             if(_AnularFacGui(conn, "tbm_cabguirem", rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc") ) ){
              if(_AnularDiario(conn, intCodEmp, intCodLoc, intCodTipDoc,intCodDoc ) ){
               if(_invertirInvFacDet(conn, intCodEmp, intCodLoc, intCodTipDoc,intCodDoc ) ){
                  
                  if(blnCreNueFac){
                      
                   if(FormaRetencion(conn, intCodEmp )){
                    if(cargarTipEmp(conn, intCodEmp, intCodLoc )){

                      if(_crearNuevaFactura(conn, intCodEmp, intCodLoc, intCodTipDoc,intCodDoc, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), dblPorIva, rstLoc.getString("co_tipper"), intCodMotDoc, intCodTipPerEmp, rstLoc.getInt("co_forpag")  ) ){
                       // if( generaComVenComp(conn, intCodEmp,  intCodLoc,  intCodTipDoc,  intCodDoc) ){

                          if(! stbDatDocRel.toString().equals("") )  asignaSecEmpGrp(conn, stbDatDocRel );
                          
                          blnRes=true;
                       // }else blnRes=false;
                      }else blnRes=false;
                    }else blnRes=false;
                   }else blnRes=false;

                      
                  }else{
                    // if( generaComVenComp(conn, intCodEmp,  intCodLoc,  intCodTipDoc,  intCodDoc) ){
                        blnRes=true;

                        if(! stbDatDocRel.toString().equals("") )  asignaSecEmpGrp(conn, stbDatDocRel );


                   //  }else blnRes=false;
                  }

                  
               }
              }
             }
            }


         }
      }
      rstLoc.close();
      rstLoc=null;

      stbDatDocRel=null;

      stmLoc.close();
      stmLoc=null;

 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}



public boolean _invertirInvFacDet(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSQL="";
 int intTipCli=3;
 int intCodItm =0;
 int intCodBod=0;
 int intTipStk=0;
 double dlbCanMov=0.00;
 String strEstFisBod="", strTipIngEgr="E";
 try{
  if(conn!=null){
    stmLoc=conn.createStatement();
   
    objInvItm.limpiarObjeto();
    objInvItm.inicializaObjeto();

    String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(b.tx_codalt), length(b.tx_codalt) ,1)) IN ( " +
    " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
    " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
    " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
    " THEN 'S' ELSE 'N' END AS proconf  ";

    strSQL="SELECT a.co_itm, a.co_bod, abs(a.nd_can) as nd_can, a.st_meringegrfisbod  " +
    strAux2+" FROM tbm_detMovInv as a " +
    " INNER JOIN tbm_inv AS b on(a.co_emp=b.co_emp and a.co_itm=b.co_itm ) "+
    " WHERE a.co_emp="+intCodEmp+" AND a.co_loc ="+intCodLoc+" " +
    " AND a.co_tipDoc = "+intCodTipDoc+" AND a.co_doc ="+intCodDoc+" AND b.st_ser='N'";
    rstLoc = stmLoc.executeQuery(strSQL);
    while(rstLoc.next()){
       
       intCodItm=rstLoc.getInt("co_itm");
       intCodBod=rstLoc.getInt("co_bod");
       dlbCanMov=rstLoc.getDouble("nd_can");

       strEstFisBod=rstLoc.getString("proconf");
       objInvItm.generaQueryStock(intCodEmp, intCodItm, intCodBod, dlbCanMov, 1, strEstFisBod, rstLoc.getString("st_meringegrfisbod"), strTipIngEgr, -1);
       intTipStk=1;
    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;

        if(intTipStk==1){
           if(!objInvItm.ejecutaActStock(conn, intTipCli))
             return false;

           if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli ))
            return false;
        }
         objInvItm.limpiarObjeto();
         blnRes=true;
     }}catch(java.sql.SQLException e){ blnRes=false;   mostarErrorException(e); }
       catch(Exception e){ blnRes=false;   mostarErrorException(e); }
   return blnRes;
 }




private boolean asignaSecEmpGrp(java.sql.Connection connLoc, StringBuffer stbDocRelSec ){
  boolean blnRes=false;
  java.sql.ResultSet rstLoc;
  java.sql.Statement stmLoc, stmLoc01;
  String strSql="";
  int intSecEmp=0,intSecGrp=0;
  try{
   if(connLoc!=null){
     stmLoc=connLoc.createStatement();
     stmLoc01=connLoc.createStatement();
     strSql="SELECT * FROM( "+stbDocRelSec.toString()+" ) AS x";

     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next()){

        intSecEmp=objUltDocPrint.getNumSecDoc(connLoc, rstLoc.getInt("coemp") );
        intSecGrp=objUltDocPrint.getNumSecDoc(connLoc, objZafParSis.getCodigoEmpresaGrupo() );

         strSql="UPDATE tbm_cabmovinv SET ne_SecEmp="+intSecEmp+", ne_SecGrp="+intSecGrp+" WHERE co_emp="+rstLoc.getInt("coemp")+" AND co_loc="+rstLoc.getInt("coloc")+" " +
         " AND co_tipdoc="+rstLoc.getInt("cotipdoc")+" AND  co_doc="+rstLoc.getInt("codoc")+"";
         stmLoc01.executeUpdate(strSql);
     }
     rstLoc.close();
     rstLoc=null;

    stmLoc.close();
    stmLoc=null;
    stmLoc01.close();
    stmLoc01=null;

   }}catch(java.sql.SQLException e){ blnRes=false;  mostarErrorException(e);  }
    catch(Exception e) {  blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}



//
//select a2.* from tbr_cabmovinv as a
//inner join tbm_cabmovinv  as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel )
//inner join tbm_detmovinv  as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc )
//where a.co_emp=4 and a.co_loc=3 and a.co_tipdoc=1 and a.co_doc=34034  and nd_cannunrec > 0





public boolean _crearNuevaFactura(java.sql.Connection conn, int intCodEmpFac, int intCodLocFac, int intCodTipDocFac, int intCodDocFac, int intCodEmpGui, int intCodLocGui, int intCodTipDocGui, int intCodDocGui, double dblPorIva
 ,String strTipPerCli, int intCodMotDocEnv, int intCodTipPerEmpRec, int intCodForPag  ){

 boolean blnRes=false;;
 java.sql.Statement stmLoc;
 String strSql="";
 int intCodDocNueGui=0;
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      objZafCtaCtb_dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objZafParSis, intCodTipDocFac);

       strCodTipPerCli = strTipPerCli;
       intCodMotDoc = intCodMotDocEnv;
       intCodTipPerEmp=intCodTipPerEmpRec;

      strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
      " co_emp="+intCodEmpFac+" AND co_loc="+intCodLocFac+" AND co_tipDoc="+intCodTipDocFac;
      intCodDocNueFac = getCodigoMaxDoc(conn, strSql );

      strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabguirem WHERE " +
      " co_emp="+intCodEmpGui+" AND co_loc="+intCodLocGui+" AND co_tipDoc="+intCodTipDocGui;
      intCodDocNueGui =  getCodigoMaxDoc(conn, strSql );

    if(_calculaTotal(conn, intCodEmpFac,  intCodLocFac,  intCodTipDocFac,  intCodDocFac, dblPorIva, intCodDocNueFac, intCodEmpGui,  intCodLocGui,  intCodTipDocGui, intCodDocNueGui )){
      if(insertarCabFac(conn, intCodEmpFac,  intCodLocFac,  intCodTipDocFac,  intCodDocFac, intCodDocNueFac, intCodEmpGui,  intCodLocGui,  intCodTipDocGui, intCodDocGui, intCodDocNueGui  )){
       if(insertarDetFac(conn)){
        if(insertarDiario(conn, intCodEmpFac,  intCodLocFac,  intCodTipDocFac,  intCodDocNueFac )){

         if( CalculoPago(conn, intCodEmpFac, intCodForPag ) ){
           if( insertarPagFac(conn, intCodEmpFac, intCodLocFac, intCodTipDocFac, intCodDocNueFac ) ){
             
               blnRes=true;
            
         }}

     }}}}

      objZafCtaCtb_dat=null;

      stmLoc.close();
      stmLoc=null;

 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}

private boolean CalculoPago(java.sql.Connection conn, int intCodEmp, int intCodForPag){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 try{
   if(conn!=null){
        stmLoc=conn.createStatement();

        String sSQL2 = "SELECT A1.ne_numPag, A2.ne_diaCre, A2.st_sop " +
        " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
        " Where A1.co_forPag = "  +intCodForPag+// Clausulas Where para las tablas maestras
        "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
        "       and A1.co_emp = " + intCodEmp + // Consultando en la empresa en la ke se esta trabajando
        "       and A2.co_emp = A1.co_emp " + // Consultando en la empresa en la ke se esta trabajando
        "       order by A2.co_reg ";// Consultando en el local en el ke se esta trabajando

        String sSQL3 = "SELECT count(A2.ne_diaCre) as c " +
        " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
        " Where A1.co_forPag = "  +intCodForPag+// Clausulas Where para las tablas maestras
        "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
        "       and A1.co_emp = " + intCodEmp + // Consultando en la empresa en la ke se esta trabajando
        "       and A2.co_emp = A1.co_emp ";  // Consultando en la empresa en la ke se esta trabajando


        rstLoc= stmLoc.executeQuery(sSQL3);
        rstLoc.next();
        intCanArr[0] = rstLoc.getInt(1);
        rstLoc.close();
        rstLoc=null;


        rstLoc= stmLoc.executeQuery(sSQL2);
        int x=0;
        while(rstLoc.next()){
            intarreglodia[x]=rstLoc.getInt(2);
            intarreglonum[x]=rstLoc.getInt(1);
            strarreglosop[x]=rstLoc.getString("st_sop");
            x++;
        }
        rstLoc.close();
        rstLoc=null;

        stmLoc.close();
        stmLoc=null;
        blnRes=true;

    }}catch(Exception e) {  blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}


private boolean insertarPagFac(java.sql.Connection conn, int intCodEmp, int intCodLocFac, int intCodTipDocFac, int intCodDocNueFac  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  java.util.Calendar objFec;
  int intVal =  intCanArr[0];
  int intsizearre = intarreglodia.length;
  int i=0;
  double dblBaseDePagos = 0 , dblRetIva = 0 , dblRetFue = 0, dblRetFueFle = 0;
  double dblPago=0.00;
  double dblPagos=0.00;
  double dblRete=0;
  String strSql="";
  String strFec="";
  String strFecSem="";
  String strFecSis="";
  String strFecSisBase="";
  try{
     if(conn!=null){
      stmLoc= conn.createStatement();
      intVal= intsizearre - (intsizearre-intVal);

      strFecSis=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaBaseDatos());
      strFecSisBase = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaBaseDatos());

      txtFecDoc = new Librerias.ZafDate.ZafDatePicker( new javax.swing.JFrame(), "d/m/y");

      strFec=objUti.formatearFecha(strFecSis ,"yyyy-MM-dd","dd/MM/yyyy");
      objFec = java.util.Calendar.getInstance();
      int fecDoc [] = txtFecDoc.getFecha(strFec);

      if(fecDoc!=null){
         objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
         objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
         objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
       }

     dblBaseDePagos = dblToTalFac;

     if(dblBaseDePagos>0){
        java.util.Vector vecData = new java.util.Vector();


         Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
         /*************************************************************************************///
         dblRetFueFle = dblTotalTrans; //getSubtotalTrans();
         if(!(strCodTipPerCli==null)){
          if(!(strCodTipPerCli.trim().equals("1"))){
           if(dblRetFueFle>0){
              java.util.Vector vecReg = new java.util.Vector();
              dblRetFueFle = objUti.redondeo( (    dblRetFueFle  * 0.01 ),2 );
              vecReg.add(INT_TBL_PAGLIN, "");
              vecReg.add(INT_TBL_PAGCRE, "");
              vecReg.add(INT_TBL_PAGFEC, txtFecDoc.getText() );
              vecReg.add(INT_TBL_PAGRET, "1.00");
              vecReg.add(INT_TBL_PAGMON, ""+dblRetFueFle);
              vecReg.add(INT_TBL_PAGGRA, "");
              vecReg.add(INT_TBL_PAGCOD, "1");
              vecReg.add(INT_TBL_PAGSOP, "N");
             vecData.add(vecReg);
            }} else { dblRetFueFle=0; }
         }else { dblRetFueFle=0; }

          strSql="SELECT tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta "+
          " FROM tbm_polret as polret left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)"+
          " WHERE polret.co_emp = "+ objZafParSis.getCodigoEmpresa()+" and co_mot = "+intCodMotDoc+" and co_sujret = " + intCodTipPerEmp +" and co_ageret  = "+strCodTipPerCli+" "+
          " AND polret.st_reg='A'  AND  '"+strFecSisBase+"'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
          rstLoc = stmLoc.executeQuery(strSql);
          while(rstLoc.next()){
             java.util.Vector vecReg = new java.util.Vector();
             vecReg.add(INT_TBL_PAGLIN, "");
             vecReg.add(INT_TBL_PAGCRE, "");
             vecReg.add(INT_TBL_PAGFEC, strFec );

             if(rstLoc.getString("tx_aplret").equals("S")){
                dblRetFue = dblTotalSinTrans; //getSubtotalSinTrans();
                if(!(dblRetFueFle>0))
                  dblRetFue = objUti.redondeo( (  dblSubToTalFac * ( rstLoc.getDouble("nd_porret")/100)),2 );
                 else
                  dblRetFue = objUti.redondeo( (    dblRetFue  * ( rstLoc.getDouble("nd_porret")/100)),2 );

               vecReg.add(INT_TBL_PAGRET, rstLoc.getString("nd_porret") );
               vecReg.add(INT_TBL_PAGMON, ""+dblRetFue);
               vecReg.add(INT_TBL_PAGGRA, "");
            }
             if(rstLoc.getString("tx_aplret").equals("I")){
               dblRetIva = objUti.redondeo(  ( dblValIvaFac  * ( rstLoc.getDouble("nd_porret")/100)),2 );

                vecReg.add(INT_TBL_PAGRET, rstLoc.getString("nd_porret") );
                vecReg.add(INT_TBL_PAGMON, ""+dblRetIva);
                vecReg.add(INT_TBL_PAGGRA, "");
             }
              vecReg.add(INT_TBL_PAGCOD, rstLoc.getString("co_tipret") );
              vecReg.add(INT_TBL_PAGSOP, "N");
              vecData.add(vecReg);
          }
          rstLoc.close();
          rstLoc=null;
       /*************************************************************************************///
         dblRete = dblRetFueFle+dblRetFue+dblRetIva;
         dblBaseDePagos = objUti.redondear( ( dblToTalFac - dblRete ), 2);
         for(i=0; i < intVal; i++){
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());

            int diaCre = intarreglodia[i];
            int numPag = intarreglonum[i];
            String strSop = ((strarreglosop[i]==null)?"N":strarreglosop[i]);

            if (diaCre!=0)
                objFecPagActual.add(java.util.Calendar.DATE, diaCre);

            dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));

            java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL_PAGLIN, "");
            vecReg.add(INT_TBL_PAGCRE, ""+diaCre);
            vecReg.add(INT_TBL_PAGFEC, dtePckPag.getText() );

            dblPagos = objUti.redondear( (numPag==0)?0:(dblBaseDePagos/numPag) ,2);
            dblPago += dblPagos;
            dblPagos = objUti.redondear(dblPagos ,2);

            vecReg.add(INT_TBL_PAGRET, "");
            if(i==(intVal-1))
              dblPagos= objUti.redondear( dblPagos + (dblToTalFac  - (dblPago + dblRete )), 2);

            vecReg.add(INT_TBL_PAGMON, ""+dblPagos);
            vecReg.add(INT_TBL_PAGGRA, "0");
            vecReg.add(INT_TBL_PAGCOD, "");
            vecReg.add(INT_TBL_PAGSOP, strSop);
            vecData.add(vecReg);
        }
       objTblModPag.setData(vecData);
       tblPag.setModel(objTblModPag);
       vecData=null;



/***********************************************************************************************/

    double dblValRet=0;
    String strFecCor="";
    for(int x=0; x<tblPag.getRowCount();x++){
      dblValRet =  Double.parseDouble( (tblPag.getValueAt(x, INT_TBL_PAGRET)==null?"0":tblPag.getValueAt(x, INT_TBL_PAGRET).equals("")?"0":tblPag.getValueAt(x, INT_TBL_PAGRET).toString() ) );
      if(dblValRet==0.00){
          strFecCor= tblPag.getValueAt(x, INT_TBL_PAGFEC).toString();
          break;
      }
    }


     String strF1 = objUti.formatearFecha(strFecSisBase, "yyyy-MM-dd", "yyyy/MM/dd");
     java.util.Date  fac1 = objUti.parseDate(strF1, "yyyy/MM/dd");
     int intAnioAct= (fac1.getYear()+1900);

     //  por alfredo.  año nuevo 31 dic año anterior
     String strF = objUti.formatearFecha(strFecCor, "dd/MM/yyyy", "yyyy/MM/dd");
     java.util.Date  fac = objUti.parseDate(strF, "yyyy/MM/dd");
     int intAnioCre= (fac.getYear()+1900);


     if(intAnioCre > intAnioAct ){
         strFecCor="31/12/"+intAnioAct;
     }



    for(int x=0; x<tblPag.getRowCount();x++){
      dblValRet =  Double.parseDouble( (tblPag.getValueAt(x, INT_TBL_PAGRET)==null?"0":tblPag.getValueAt(x, INT_TBL_PAGRET).equals("")?"0":tblPag.getValueAt(x, INT_TBL_PAGRET).toString() )  );
      if(dblValRet > 0.00 )
        tblPag.setValueAt(strFecCor,  x, INT_TBL_PAGFEC);

    }

       /*************************************************************************************///

       for(int x=0; x<tblPag.getRowCount();x++){
         i=x;
         int FecPagDoc[] =  txtFecDoc.getFecha(tblPag.getValueAt(i, INT_TBL_PAGFEC).toString());
         String strFechaPag = "#" + FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
         String strSop = ((tblPag.getValueAt(i, INT_TBL_PAGSOP)==null)?"":tblPag.getValueAt(i, INT_TBL_PAGSOP).toString() );
         String strCodTipRet= getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGCOD));
         strSql="INSERT INTO  tbm_pagMovInv(co_emp, co_loc, co_tipDoc, co_doc, co_reg, " + //CAMPOS PrimayKey
         " ne_diaCre, fe_ven, mo_pag, ne_diaGra, nd_porRet ,st_regrep , st_sop" +//<==
         " ,co_tipret ) VALUES (" +
         intCodEmp+", "+intCodLocFac+", "+intCodTipDocFac+", "+intCodDocNueFac+", "+(x+1)+", "+
         getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGCRE))+", '"+strFechaPag+"',"+
         (objUti.redondear( getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGMON)), 2 ) * -1)+", "+
         getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGGRA))+", "+
         getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGRET))+", 'I', '"+strSop+"', "+
         (strCodTipRet.equals("0")?null:strCodTipRet)+" ) ";
         stmLoc.executeUpdate(strSql);
      }
      blnRes=true;
     /*************************************************************************************///
    }
    stmLoc.close();
    stmLoc=null;
   }}catch(java.sql.SQLException e) { blnRes=false; mostarErrorException(e); }
     catch(Exception e){ blnRes=false; mostarErrorException(e);  }
   return blnRes;
 }



/**
 * Función que validad si es nulo asignas "0" caso contrario devuelve el valor que tiene.
 * @param objTbl Objeto de la celda
 * @return "0" si el nulo ó  vacion "" el valor que tiene
 */
public String getIntDatoValidado( Object objTbl ){
 String strVar="0";
 try{
    if(objTbl==null) strVar="0";
    else if(objTbl.toString().equals("")) strVar="0";
    else strVar=objTbl.toString();
  }catch(Exception e){   }
 return strVar;
}



int intCodBodGrpDan=14;


/*

SELECT a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg, a2.co_itm, a2.co_bod, a2.nd_cannunrec
,a6.tx_codalt, a6.tx_nomitm, a2.tx_unimed, a6.nd_cosuni
, a5.co_bodgrp, a4.co_itm as coitmegr
FROM tbr_cabmovinv as a
inner join tbm_cabmovinv  as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel )
inner join tbm_detmovinv  as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc )
inner join tbr_detmovinv  as a3 on (a3.co_emprel=a2.co_emp and a3.co_locrel=a2.co_loc and a3.co_tipdocrel=a2.co_tipdoc and a3.co_docrel=a2.co_doc and a3.co_regrel=a2.co_reg )
inner join tbm_detmovinv as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and a4.co_reg=a3.co_reg )
inner join tbr_bodEmpBodGrp as a5 on (a5.co_emp=a4.co_emp and a5.co_bod=a4.co_bod)
inner join tbm_inv as a6 on (a6.co_emp=a2.co_emp and a6.co_itm=a2.co_itm )
where a.co_emp=4 and a.co_loc=3 and a.co_tipdoc=1 and a.co_doc=34725   and a2.nd_cannunrec > 0


select co_bod  from tbr_bodEmpBodGrp  where co_emp=1 and co_bodgrp=1

select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a2.co_bodgrp, a1.co_itm  from tbr_detmovinv as a
inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )
inner join tbr_bodEmpBodGrp as a2 on (a2.co_emp=a1.co_emp and a2.co_bod=a1.co_bod)
where a.co_emprel=4 and a.co_locrel=3 and a.co_tipdocrel=128 and a.co_docrel= 7405 and a.co_regrel=1 and a1.nd_can < 0




select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a2.co_bodgrp, a1.co_itm  from tbr_detmovinv as a
inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )
inner join tbr_bodEmpBodGrp as a2 on (a2.co_emp=a1.co_emp and a2.co_bod=a1.co_bod)
where a.co_emprel=4 and a.co_locrel=3 and a.co_tipdocrel=153 and a.co_docrel= 4965 and a.co_regrel=2  and a1.nd_can < 0



        
        */

 int intCodEmpCompra=0;
 int intCodLocCom=0;
 double bldivaEmp=0;
 String strNomBodIngVen="";

private boolean generaComVenComp(java.sql.Connection conn, int intCodEmpFac, int intCodLocFac, int intCodTipDocFac, int intCodDocFac ){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc, rstLoc01;
 String strSql="";
 String strCodEmp="";
 String strCodAlt="";
 String strMerIngEgrFisBod="S";
 String strNomBodSal="";
 String strNomBodSalTrans="";
 String strNomBodIng="";
 int intCodBodDanEmp=0;
 int intNumFila=0;
 int intCodEmpVen=0;
 int intCodItm=0;
 int intCodBod=0;
 int intCodBodPre=0;
 int intCodTipDocTra=153;
 int intCodItmIng=0;
 double dblDesVta=0;
 double dblPreVta=0;
 double dblCosUni=0;
 double dblCanVenCom=0;
  try{
     if(conn!=null){
      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();
    

         cargarTipEmp(conn);
    
          strSql="SELECT a8.tx_nom as nombod, a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg, a2.co_itm, a2.co_bod, a2.nd_cannunrec "
          + " ,a6.tx_codalt, a6.tx_nomitm, a2.tx_unimed, a6.nd_cosuni "
          + " , a5.co_bodgrp, a4.co_itm as coitmegr ,a7.co_itmmae, a9.tx_nom as nombodEgrTrans "
          + " FROM tbr_cabmovinv as a "
          + " inner join tbm_cabmovinv  as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel )  "
          + " inner join tbm_detmovinv  as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc )  "
          + " inner join tbr_detmovinv  as a3 on (a3.co_emprel=a2.co_emp and a3.co_locrel=a2.co_loc and a3.co_tipdocrel=a2.co_tipdoc and a3.co_docrel=a2.co_doc and a3.co_regrel=a2.co_reg ) "
          + " inner join tbm_detmovinv as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and a4.co_reg=a3.co_reg )  "
          + " inner join tbr_bodEmpBodGrp as a5 on (a5.co_emp=a4.co_emp and a5.co_bod=a4.co_bod) "
          + " inner join tbm_inv as a6 on (a6.co_emp=a2.co_emp and a6.co_itm=a2.co_itm ) "
          + " inner join tbm_equinv as a7 on (a7.co_emp=a4.co_emp and a7.co_itm=a4.co_itm )  "
          + " inner join tbm_bod as a8 on (a8.co_emp=a2.co_emp and a8.co_bod=a2.co_bod ) "
          + " inner join tbm_bod as a9 on (a9.co_emp=a5.co_empgrp and a9.co_bod=a5.co_bodgrp ) "
          + " where a.co_emp="+intCodEmpFac+" and a.co_loc="+intCodLocFac+" and a.co_tipdoc="+intCodTipDocFac+" and a.co_doc="+intCodDocFac+"   and a2.nd_cannunrec > 0   ";

          System.out.println(" == --> "+strSql );

          rstLoc=stmLoc.executeQuery(strSql);
          while(rstLoc.next()){

                 strCodEmp=rstLoc.getString("co_emp");

                 strNomBodSal=rstLoc.getString("nombod");

                 strNomBodIngVen=rstLoc.getString("nombodEgrTrans");

                 strNomBodIng=rstLoc.getString("nombodEgrTrans");



                 if(rstLoc.getInt("co_bodgrp")==1) { intCodEmpCompra=1; intCodLocCom=4; }
                 if(rstLoc.getInt("co_bodgrp")==2) { intCodEmpCompra=4; intCodLocCom=3; }
                 if(rstLoc.getInt("co_bodgrp")==3) { intCodEmpCompra=2; intCodLocCom=1; }
                 if(rstLoc.getInt("co_bodgrp")==5) { intCodEmpCompra=2; intCodLocCom=4; }
                 if(rstLoc.getInt("co_bodgrp")==11) { intCodEmpCompra=2; intCodLocCom=6; }
                 if(rstLoc.getInt("co_bodgrp")==28) { intCodEmpCompra=2; intCodLocCom=10; }


                  strSql="select co_itm from tbm_equinv where co_emp="+intCodEmpCompra+" and co_itmmae= "+rstLoc.getInt("co_itmmae")+" ";
                  rstLoc01 = stmLoc01.executeQuery(strSql);
                  if(rstLoc01.next()){
                     intCodItmIng=rstLoc01.getInt("co_itm");
                  }
                  rstLoc01.close();
                  rstLoc01=null;



                 dblCanVenCom=rstLoc.getDouble("nd_cannunrec");

                 intCodBodPre= _getBodegaIng(conn, intCodEmpCompra,  rstLoc.getInt("co_bodgrp") );

                 intCodBod=rstLoc.getInt("co_bod");
                 /*********************************************************************/
                  intNumFila=1;

                  if(intCodEmpCompra==1){
                   if(strCodEmp.equals("2")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=2854";
                   if(strCodEmp.equals("4")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=3117";
                  }
                  if(intCodEmpCompra==2){
                   if(strCodEmp.equals("1")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=603";
                   if(strCodEmp.equals("4")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=498";
                  }
                  if(intCodEmpCompra==4){
                   if(strCodEmp.equals("2")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=789";
                   if(strCodEmp.equals("1")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=1039";
                  }


                  rstLoc01 = stmLoc01.executeQuery(strSql);
                  if(rstLoc01.next())
                     dblDesVta=rstLoc01.getDouble("nd_maxDes");
                  rstLoc01.close();
                  rstLoc01=null;
                /*********************************************************************/
                  strSql="SELECT a.co_emp, a.co_itm, a.nd_prevta1, a.nd_stkact, a.tx_codalt FROM tbm_inv as a " +
                  " WHERE a.co_emp="+strCodEmp+" AND a.co_itm = "+rstLoc.getInt("co_itm")+" ";
                  rstLoc01 = stmLoc01.executeQuery(strSql);
                  if(rstLoc01.next()){
                     intCodItm=rstLoc01.getInt("co_itm");
                     dblPreVta=rstLoc01.getDouble("nd_prevta1");
                     strCodAlt=rstLoc01.getString("tx_codalt");
                  }
                  rstLoc01.close();
                  rstLoc01=null;


                  if(dblPreVta > 0 ){

                   int intCodBodDes =  _getCodigoBodDes(conn, intCodEmpCompra, intCodBodPre, Integer.parseInt(strCodEmp) );
                   if(!(intCodBodDes== -1)){

//                   intCodDocFacCom=0;
//                   intCodLocFacCom=0;
//                   intCodTipDocFacCom=0;
//
//                   intCodDocFacVen=0;
//                   intCodLocFacVen=0;
//                   intCodTipDocFacVen=0;


                  strSql="select a.co_emp, a.co_bod, a1.tx_nom  from tbr_bodEmpBodGrp as a "
                   + " inner join tbm_bod as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod )  "
                   + " where a.co_empgrp="+objZafParSis.getCodigoEmpresaGrupo()+""
                   + " and a.co_bodgrp="+intCodBodGrpDan+" and a.co_emp= "+intCodEmpCompra+"  ";

                    System.out.println("Paso Trans 1 --> "+strSql );

                   rstLoc01 = stmLoc01.executeQuery(strSql);
                   if(rstLoc01.next()){
                     intCodBodDanEmp=rstLoc01.getInt("co_bod");
                     strNomBodSalTrans=rstLoc01.getString("tx_nom");
                   }
                   rstLoc01.close();
                   rstLoc01=null;




                   dblCosUni  =  dblPreVta - ( dblPreVta * ( dblDesVta / 100 ) );
                   //System.out.println("Gen Ven");
                   if(generaVen(conn, strMerIngEgrFisBod,  Integer.parseInt(strCodEmp), intCodBod, intNumFila, dblCanVenCom, dblDesVta, intCodItm, dblPreVta, intCodBodDes , rstLoc.getString("tx_codalt"),  rstLoc.getString("tx_nomitm"), rstLoc.getString("tx_unimed"), rstLoc.getString("nd_cosuni")  )){
                    if(generaCom(conn, strMerIngEgrFisBod, Integer.parseInt(strCodEmp), intCodEmpCompra, intCodBodPre, intNumFila, dblCanVenCom, intCodBod, dblCosUni, strNomBodSal, intCodItmIng, rstLoc.getString("tx_codalt"),  rstLoc.getString("tx_nomitm"), rstLoc.getString("tx_unimed")   )){


                        if(generaTrans(conn,  intCodEmpCompra, intCodLocCom, intCodTipDocTra, intCodBodPre, intCodBodDanEmp,  intCodItmIng, dblCanVenCom, strNomBodSalTrans, strNomBodIng  ) ){

                         
                        
//                        if(ingresarEnTblRelacional(conn, Integer.parseInt(strCodEmp), intCodLocFacVen, intCodTipDocFacVen, intCodDocFacVen, (intNumFila+1)
//                           , objZafParSis.getCodigoEmpresa(), intCodLocFacCom, intCodTipDocFacCom, intCodDocFacCom, (intNumFila+1) ) ){

                         blnRes=true;
                         System.out.println(" GENERE COMPRA VENTA  ");



                      // }else { blnRes=false;  break; }
                     }else { blnRes=false;  break; }
                    }else { blnRes=false;  break; }
                    }else { blnRes=false;  break; }

                  }else{  MensajeInf("PROBLEMA AL OBTENER BODEGA DESTINO.. ");  blnRes=false;  break; }

                }else{  MensajeInf("El item "+strCodAlt+" no tiene precio de lista.");  blnRes=false;  break; }

          

          }
          rstLoc.close();
          rstLoc=null;

       


    stmLoc.close();
    stmLoc=null;
   }}catch(java.sql.SQLException e) { blnRes=false; mostarErrorException(e);   }
     catch(Exception e){ blnRes=false; mostarErrorException(e);  }
   return blnRes;
 }


private boolean generaTrans(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodBodOri, int intCodBodDes, int intCodItm, double dblanmov,String strNomBodIngTra, String strNomBodSalTra ){
boolean blnRes=false;
try{
   if(conn != null ){

     if(consultarTansAut(conn, intCodEmp,  intCodLoc, intCodTipDoc, intCodBodOri, intCodBodDes, intCodItm, dblanmov, strNomBodIngTra, strNomBodSalTra )){
         blnRes=true;
     }

}}catch(Exception e)  { blnRes=false;   mostarErrorException(e);  }
return blnRes;
}


private boolean consultarTansAut(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodBodOri, int intCodBodDes, int intCodItm, double dblanmov, String strNomBodIngTra, String strNomBodSalTra ){
  boolean blnRes=false;
  java.sql.Statement stmLoc, stmLoc01;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String str_MerIEFisBod="N";
  int intCodDoc=0;
  int intNumDoc=0;
  int intSecEmp=0;
  int intSecGrp=0;
  int intCodRegEgr=0;
  int intCodRegIng=0;


  try{
  if(conn!=null){

     stmLoc=conn.createStatement();
     stmLoc01=conn.createStatement();

     StringBuffer stbTbl1;

     double dblTot=0;
     int intReg=1;
     int intNumFil=1;
     int intControl=0;



     dblTot=0;
     intReg=1;
     intNumFil=1;
     stbTbl1=new StringBuffer();
     intControl=0;
     intCodDoc=0;


     objInvItm.limpiarObjeto();
     objInvItm.inicializaObjeto();

//     if(intConStbBod==1)  stbLisBodItm.append(" UNION ALL ");
//     stbLisBodItm.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodBodOri+" AS COBOD, "+strCodItm+" AS COITM, "+dblanmov+" AS NDCAN ");
//     intConStbBod=1;



     strSql="select a.co_itm, a.tx_codalt, a.tx_nomitm, a1.tx_descor as tx_unimed,  "+intCodBodOri+" as co_bodori, " +
     " "+intCodBodDes+" as co_boddes,  "+dblanmov+" as nd_can, a.nd_cosuni, ( "+dblanmov+" * a.nd_cosuni) as costot " +
     " from tbm_inv as a " +
     " left join  tbm_var as a1 on (a1.co_reg=a.co_uni) " +
     " where  a.co_emp="+intCodEmp+"  and a.co_itm="+intCodItm;

      System.out.println("---> "+strSql);

      
     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next()){

        if(intControl==0)
           intCodDoc=getCodigoDoc(conn, intCodEmp, intCodLoc, intCodTipDoc );

         dblTot += rstLoc.getDouble("costot");



         intCodRegEgr= intReg;
         strSql="INSERT INTO tbm_detmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, " +
         " tx_codalt, tx_nomitm, tx_unimed, co_bod, nd_can, nd_cosuni, nd_preuni, nd_pordes, st_ivacom, " +
         " nd_cancon,  ne_numfil, nd_tot, tx_codalt2, nd_costot, nd_cospro, " +
         " nd_cosunigrp, nd_costotgrp, nd_candev, st_regrep, co_itmact, st_meringegrfisbod, nd_cannunrec, tx_nombodorgdes ) " +
         " VALUES( "+intCodEmp+","+intCodLoc+","+intCodTipDoc+", "+intCodDoc+", "+intReg+", "+rstLoc.getInt("co_itm")+"," +
         " '"+rstLoc.getString("tx_codalt")+"', '"+rstLoc.getString("tx_nomitm")+"', '"+rstLoc.getString("tx_unimed")+"',"+
         " "+rstLoc.getInt("co_bodori")+", "+rstLoc.getDouble("nd_can")*-1+", "+rstLoc.getString("nd_cosuni")+", "+rstLoc.getString("nd_cosuni")+"," +
         " 0, 'N', 0, "+intNumFil+", "+rstLoc.getDouble("costot")*-1+", '"+rstLoc.getString("tx_codalt")+"', "+rstLoc.getDouble("costot")*-1+", "+rstLoc.getString("nd_cosuni")+"," +
         " "+rstLoc.getString("nd_cosuni")+", "+rstLoc.getDouble("costot")*-1+",  0, 'I', "+rstLoc.getInt("co_itm")+", " +
         " 'S', 0, '"+strNomBodIngTra+"'  ) ; ";
         stbTbl1.append( strSql );
         intReg++;
         str_MerIEFisBod="E";


        //  System.out.println("---> "+strSql);

         //objInvItm.generaQueryStock(intCodEmp, intCodItm, rstLoc.getInt("co_bodori"), rstLoc.getDouble("nd_can"),  -1 , "N", "S", str_MerIEFisBod, 1);


         intCodRegIng= intReg;
         strSql="INSERT INTO tbm_detmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, " +
         " tx_codalt, tx_nomitm, tx_unimed, co_bod, nd_can, nd_cosuni, nd_preuni, nd_pordes, st_ivacom, " +
         " nd_cancon,  ne_numfil, nd_tot, tx_codalt2, nd_costot, nd_cospro, " +
         " nd_cosunigrp, nd_costotgrp, nd_candev, st_regrep, co_itmact, st_meringegrfisbod, nd_cannunrec, tx_nombodorgdes ) " +
         " VALUES( "+intCodEmp+","+intCodLoc+","+intCodTipDoc+", "+intCodDoc+", "+intReg+", "+rstLoc.getInt("co_itm")+"," +
         " '"+rstLoc.getString("tx_codalt")+"', '"+rstLoc.getString("tx_nomitm")+"', '"+rstLoc.getString("tx_unimed")+"',"+
         " "+rstLoc.getInt("co_boddes")+", "+rstLoc.getDouble("nd_can")+", "+rstLoc.getString("nd_cosuni")+", "+rstLoc.getString("nd_cosuni")+"," +
         " 0, 'N', 0, "+intNumFil+", "+rstLoc.getDouble("costot")+", '"+rstLoc.getString("tx_codalt")+"', "+rstLoc.getDouble("costot")+", "+rstLoc.getString("nd_cosuni")+"," +
         " "+rstLoc.getString("nd_cosuni")+", "+rstLoc.getDouble("costot")+",  0, 'I', "+rstLoc.getInt("co_itm")+", " +
         " 'S', "+rstLoc.getDouble("nd_can")+" , '"+strNomBodSalTra+"' ) ; ";
         stbTbl1.append( strSql );
         intReg++;
         str_MerIEFisBod="I";



         strSql = " INSERT INTO tbr_detmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, st_reg, co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel, st_regrep  ) " +
          " VALUES( "+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intCodRegEgr+", 'A' " +
          " ,"+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intCodRegIng+", 'P' ) ; ";
          stbTbl1.append( strSql );



         objInvItm.generaQueryStock(intCodEmp, intCodItm, rstLoc.getInt("co_boddes"), rstLoc.getDouble("nd_can"),  1 , "N", "S", str_MerIEFisBod, 1);



        intNumFil++;
        intControl=1;
     }
     rstLoc.close();
     rstLoc=null;




     if(intControl==1){
       intNumDoc=getUltNumDoc(conn, intCodEmp, intCodLoc, intCodTipDoc );

//       intSecEmp=getNumeroOrdenEmpresa(conn, intCodEmp ); *
//       intSecGrp=getNumeroOrdenGrupo(conn );

//        intSecEmp=objUltDocPrint.getNumSecDoc(conn, intCodEmp );
//        intSecGrp=objUltDocPrint.getNumSecDoc(conn, objZafParSis.getCodigoEmpresaGrupo() );

//       if(intDocRelEmp==1) stbDocRelEmp.append(" UNION ALL ");
//        stbDocRelEmp.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC" );
//       intDocRelEmp=1;


      if( insertarCabTrans(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intNumDoc, dblTot ,  intSecEmp, intSecGrp ) ){
       if(insertarCabDiaTrans( conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intNumDoc ) ){
         if(insertarDetDiaTrans( conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, dblTot, intCodBodOri , intCodBodDes ) ){
            stmLoc.executeUpdate(stbTbl1.toString());
            blnRes=true;
       }}}
     }
     stbTbl1=null;




      if(!objInvItm.ejecutaActStock(conn, 3))
          return false;

     
       if(!objInvItm.ejecutaVerificacionStock(conn, 3 ))
            return false;

     
      objInvItm.limpiarObjeto();

     
     stmLoc.close();
     stmLoc=null;
     stmLoc01.close();
     stmLoc01=null;
  }}catch(java.sql.SQLException e){ blnRes=false;  mostarErrorException(e);  }
    catch(Exception e){ blnRes=false;  mostarErrorException(e); }
  return  blnRes;
}


private int getUltNumDoc(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc ){
  int intUltNumDoc=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

     strSql="SELECT CASE WHEN (ne_ultdoc+1) IS NULL THEN 1 ELSE (ne_ultdoc+1) END AS numdoc FROM tbm_cabtipdoc WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc;
     rstLoc = stmLoc.executeQuery(strSql);
     if(rstLoc.next()) intUltNumDoc = rstLoc.getInt("numdoc");

    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;

 }}catch(java.sql.SQLException e) {  mostarErrorException(e);   }
   catch(Exception e) {  mostarErrorException(e); }
 return intUltNumDoc;
}


private int getCodigoDoc(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc ){
  int intCodDoc=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

     strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
     " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc;
     rstLoc = stmLoc.executeQuery(strSql);
     if(rstLoc.next()) intCodDoc = rstLoc.getInt("co_doc");

    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;

 }}catch(java.sql.SQLException e) {  mostarErrorException(e);  }
   catch(Exception e) {  mostarErrorException(e);   }
 return intCodDoc;
}


private boolean insertarCabTrans(java.sql.Connection  conIns, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc, double dlbTot , int intSecEmp, int intSecGrp  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 try{
    if(conIns!=null){
      stmLoc=conIns.createStatement();

    java.util.Date datFecAux =objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());


     if(intDatDocRel==1)  stbDatDocRel.append(" UNION ALL ");
     stbDatDocRel.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC, "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC ");
     intDatDocRel=1;


    strSql="INSERT INTO tbm_cabmovinv(co_emp, co_loc, co_tipdoc, co_doc, ne_numdoc, fe_doc, " +
    " nd_sub, nd_tot, nd_poriva, st_reg, fe_ing , co_usring , " +
    " ne_secemp, ne_secgrp, nd_valiva,  co_mnu, st_tipdev, st_coninv, " +
    " st_imp, st_creguirem, st_coninvtraaut,  st_docconmersaldemdebfac , st_regrep ,st_docGenDevMerMalEst  ) " +
    " VALUES ("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intNumDoc+" ,'"+datFecAux+"', " +
    " "+(dlbTot*-1)+", "+(dlbTot*-1)+", 0, 'A', "+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getCodigoUsuario()+", " +
    " "+intSecEmp+", "+intSecGrp+", 0,  779, 'C', 'C',  'S', 'S', 'N', 'N', 'I','S' ) ";

    strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDoc+" WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+" ";

    //System.out.println(" "+ strSql );

    stmLoc.executeUpdate(strSql);

   stmLoc.close();
   stmLoc=null;
   blnRes=true;
}}
catch(java.sql.SQLException e)  {   mostarErrorException(e);   }
catch(Exception e)  {  mostarErrorException(e);  }

return blnRes;
}

private boolean insertarCabDiaTrans(java.sql.Connection  conIns, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 try{
    if(conIns!=null){
      stmLoc=conIns.createStatement();

    java.util.Date datFecAux =objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());

    strSql="INSERT INTO tbm_cabdia( co_emp, co_loc, co_tipdoc, co_dia, tx_numdia, fe_dia, " +
    " st_reg, fe_ing , co_usring, st_regrep, st_imp, st_usrmodasidiapre, st_asidiareg ) "+
    " VALUES ("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", '"+intNumDoc+"' ,'"+datFecAux+"', " +
    " 'A', "+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getCodigoUsuario()+", 'I','N','N','S' )";
    stmLoc.executeUpdate(strSql);

   stmLoc.close();
   stmLoc=null;
   blnRes=true;
}}
catch(java.sql.SQLException e)  {   mostarErrorException(e);  }
catch(Exception e)  {  mostarErrorException(e);  }

return blnRes;
}



private boolean insertarDetDiaTrans(java.sql.Connection  conIns, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, double dlbTot, int intBodOri, int intBodDes ){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc;
 String strSql="", strSql2="";
 int intCodReg=1;
 int intCtaOri=0;
 int intCtaDes=0;

 try{
    if(conIns!=null){
      stmLoc=conIns.createStatement();
      stmLoc01=conIns.createStatement();


        strSql2="select co_ctaexi from tbm_bod where co_emp="+intCodEmp+" and co_bod="+intBodOri+" ";
        rstLoc=stmLoc01.executeQuery(strSql2);
        if(rstLoc.next()){
            intCtaOri= rstLoc.getInt("co_ctaexi");
        }
        rstLoc.close();
        rstLoc=null;

        strSql2="select co_ctaexi from tbm_bod where co_emp="+intCodEmp+" and co_bod="+intBodDes+" ";
        rstLoc=stmLoc01.executeQuery(strSql2);
        if(rstLoc.next()){
            intCtaDes= rstLoc.getInt("co_ctaexi");
        }
        rstLoc.close();
        rstLoc=null;


    strSql="INSERT INTO tbm_detdia( co_emp, co_loc, co_tipdoc, co_dia, co_reg,  co_cta, nd_mondeb, nd_monhab, st_regrep ) " +
    " VALUES ("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intCodReg+" ,"+intCtaOri+", " +
    " 0, "+dlbTot+", 'I' ) ; ";

    intCodReg++;
     strSql +=" INSERT INTO tbm_detdia( co_emp, co_loc, co_tipdoc, co_dia, co_reg,  co_cta, nd_mondeb, nd_monhab, st_regrep ) " +
    " VALUES ("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intCodReg+" ,"+intCtaDes+", " +
    " "+dlbTot+", 0,  'I' ) ; ";
    stmLoc.executeUpdate(strSql);

   stmLoc.close();
   stmLoc=null;
   blnRes=true;
}}
catch(java.sql.SQLException e)  {   mostarErrorException(e);  }
catch(Exception e )  {  mostarErrorException(e);  }

return blnRes;
}


private boolean generaCom(java.sql.Connection conn, String strMerIngEgrFisBod,  int intCodEmpCom, int intCodEmp, int intCodBod, int intNumFil, double dblCan, int intCodBodVen, double dblCosUni, String strNomBodSal, int intCodItmEgr, String strCodAlt,  String strNomItm, String strUniMed  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intCodLoc=0;
  int intCodTipDoc=0;
  int intCodDoc=0;
  int intCodItm=0;
  String strEstConf="C";
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
          if( intCodBodVen==28 )  strNomBodVen="BCU";
        }
        if(intCodEmpCom==2){
          if( intCodBodVen==1 )  strNomBodVen="BQ";
          if( intCodBodVen==3 )  strNomBodVen="BC";
          if( intCodBodVen==4 )  strNomBodVen="BM";
          if( intCodBodVen==9 )  strNomBodVen="BV";
          if( intCodBodVen==12 )  strNomBodVen="BSD";
          if( intCodBodVen==28 )  strNomBodVen="BCU";
        }
        if(intCodEmpCom==4){
          if( intCodBodVen==1 )  strNomBodVen="BV";
          if( intCodBodVen==2 )  strNomBodVen="BC";
          if( intCodBodVen==7 )  strNomBodVen="BQ";
          if( intCodBodVen==9 )  strNomBodVen="BM";
          if( intCodBodVen==11 )  strNomBodVen="BSD";
          if( intCodBodVen==28 )  strNomBodVen="BCU";
        }







      strSql="SELECT co_loccom as co_loc, co_tipdoccom FROM tbr_bodemp WHERE co_emp="+intCodEmpCompra+" AND" +
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

//      intCodDocFacCom=intCodDoc;
//      intCodLocFacCom=intCodLoc;
//      intCodTipDocFacCom=intCodTipDoc;


      intCodItm=intCodItmEgr;   //Integer.parseInt( objInvItm.getIntDatoValidado(tblDat.getValueAt(intNumFil, INT_TBL_CODITM)) );
//      objInvItm.limpiarObjeto();


      double dblPorDes=0;
      double dblValDes = ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
      double dblTotal = (dblCan * dblCosUni)- dblValDes;
      double dlbSub =  objUti.redondear(dblTotal,2);
      double dlbValIva=  dlbSub * (bldivaEmp/100);
      double dlbTot=  dlbSub + dlbValIva;

      
      if(generaVenCab(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmpCom , 2, dlbSub, dlbValIva, dlbTot, strEstConf, 0 )){
       if(generaComDet(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBod ,intNumFil,  dblCan, intCodItm, 1, intCodEmpCom, dblCosUni, strMerIngEgrFisBod, strNomBodVen, strNomBodSal,  strCodAlt,   strNomItm,  strUniMed  )){
//        if(actualizaStock(CONN_GLO, CONN_GLO_REM, intCodEmp, intCodBod , dblCan, 1, intCodItm , strMerIngEgrFisBod, "I" )){
           blnRes=true;
      }}//}
//      objInvItm.limpiarObjeto();
    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException e){ blnRes=false;   mostarErrorException(e);      }
    catch(Exception e)   { blnRes=false;   mostarErrorException(e);       }
 return blnRes;
}



private boolean generaComDet(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intNumFil, double dblCan, int intCodItm, int intTipMov, int intCodEmpCom, double dblCosUni, String strIngEgrFisBod, String strNomBodVen, String strNomBodSal
        , String strCodAlt,  String strNomItm, String strUniMed 
        ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

       double dblPorDes=0;
       double dblValDes = ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
       double dblTotal = (dblCan * dblCosUni)- dblValDes;
              dblTotal =  objUti.redondear(dblTotal,2);

        strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, tx_obs1, tx_nombodorgdes, nd_cannunrec  ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+(intNumFil+1)+", " +
        " '"+strCodAlt+"' "+
        " ,'"+strCodAlt+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        ", '"+strNomItm+"' "+
        ", '"+strUniMed+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+dblTotal+","+dblCosUni+", "+dblPorDes+", 'S' "+
        " ,"+dblTotal+", 'I' , '"+strIngEgrFisBod+"', 0 ,"+dblCosUni+", '"+strNomBodVen+"', '"+strNomBodSal+"', "+dblCan+" ) ";

        System.out.println("--> "+ strSql );

        stmLoc.executeUpdate(strSql);

//        if(intDatVen==1) stbDatVen.append(" UNION ALL ");
//          stbDatVen.append("SELECT "+intCodEmp+" as coemp, "+intCodLoc+" as coloc, "+intCodTipDoc+"  as cotipdoc, "+intCodDoc+" AS codoc, "+intCodBod+" as cobod  ");
//        intDatVen=1;

        strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salcom=nd_salcom+"+dblCan+" WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND " +
        " co_itm="+intCodItm+" AND co_emprel="+intCodEmpCom;
        stmLoc.executeUpdate(strSql);
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException e){ blnRes=false; mostarErrorException(e);       }
    catch(Exception e)   { blnRes=false; mostarErrorException(e);       }
 return blnRes;
}





public void cargarTipEmp(java.sql.Connection conn){
 java.sql.Statement stmTipEmp;
 java.sql.ResultSet rstEmp;
 String sSql;
 try{
    if(conn!=null){
        stmTipEmp=conn.createStatement();

        sSql="select b.co_tipper , b.tx_descor , round(a.nd_ivaVen,2) as porIva , bod.co_bod, bod1.tx_nom FROM  tbm_emp as a " +
        " left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper)" +
        " left join tbr_bodloc as bod on(bod.co_emp=a.co_emp and bod.co_loc="+objZafParSis.getCodigoLocal()+" and bod.st_reg='P')  " +
        " left join tbm_bod as bod1 on(bod1.co_emp=bod.co_emp and bod1.co_bod=bod.co_bod )   " +
        " where a.co_emp="+objZafParSis.getCodigoEmpresa();

        rstEmp = stmTipEmp.executeQuery(sSql);
        if(rstEmp.next()){
            bldivaEmp   =  rstEmp.getDouble("porIva");
         }
        rstEmp.close();
        stmTipEmp.close();
        stmTipEmp = null;
        rstEmp = null;
    }
}catch(java.sql.SQLException e){   }
 catch(Exception e){   }
}


private boolean generaVen(java.sql.Connection conn, String strMerIngEgrFisBod, int intCodEmp, int intCodBod, int intNumFil, double dblCan, double intDesVta, int intCodItm, double dblPreVta, int intCodBodDes,  String strCodAlt,  String strNomItm, String strUniMed, String strPreCos ){
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

      strSql="SELECT co_locven as co_loc, co_tipdocven FROM tbr_bodemp WHERE co_emp="+intCodEmpCompra+" AND" +
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

//      intCodLocFacVen=intCodLoc;
//      intCodTipDocFacVen=intCodTipDoc;
//      intCodDocFacVen=intCodDoc;

    //  objInvItm.limpiarObjeto();

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


      if(generaVenCab(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmp, 1, dlbSub, dlbValIva, dlbTot, strEstConf , intCodBodDes  )){
       if(generaVenDet(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBod ,intNumFil,  dblCan, intCodItm, -1 , intDesVta, dblPreVta, strMerIngEgrFisBod,   strCodAlt,   strNomItm,  strUniMed,  strPreCos )){
       // if(actualizaStock(CONN_GLO, CONN_GLO_REM, intCodEmp, intCodBod , dblCan, -1, intCodItm, strMerIngEgrFisBod, "E" )){
           blnRes=true;
      }}//}
//      objInvItm.limpiarObjeto();
    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException e){ blnRes=false;  mostarErrorException(e);  }
    catch(Exception e)   { blnRes=false; mostarErrorException(e);     }
 return blnRes;
}



private boolean generaVenDet(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intNumFil, double dblCan, int intCodItm, int intTipMov , double intDesVta, double dblPreVta, String strMerIngEgrFisBod
    , String strCodAlt,  String strNomItm, String strUniMed, String strPreCos  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

     
      String strNomBodIng=strNomBodIngVen;
      

       //strPreCos = ((tblDat.getValueAt(intNumFil, INT_TBL_PREVTA)==null)?"0":(tblDat.getValueAt(intNumFil, INT_TBL_PREVTA).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_PREVTA).toString()));
       //double dblPreVta = objUti.redondear(strPreCos, 2);

       //strPreCos = ((tblDat.getValueAt(intNumFil, INT_TBL_COSUNI)==null)?"0":(tblDat.getValueAt(intNumFil, INT_TBL_COSUNI).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_COSUNI).toString()));
       double dblCosUni = objUti.redondear(strPreCos, 2);

       double dblValDes = ((dblCan * dblPreVta)==0)?0:((dblCan * dblPreVta) * (intDesVta / 100));
       double dblTotal = (dblCan * dblPreVta)- dblValDes;
              dblTotal =  objUti.redondear(dblTotal,2);

        strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, tx_nombodorgdes ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+(intNumFil+1)+", " +
        " '"+strCodAlt+"', '"+strCodAlt+"' "+
        ", "+intCodItm+", "+intCodItm+" "+
        ", '"+strNomItm+"', '"+strUniMed+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+(dblCan*dblCosUni)+","+dblPreVta+", "+intDesVta+", 'S' "+
        " ,"+(dblCan*dblCosUni)+", 'I' , '"+strMerIngEgrFisBod+"', 0 ,"+dblCosUni+" ,'"+strNomBodIng+"' ) ";
        stmLoc.executeUpdate(strSql);
        strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salven=nd_salven-"+dblCan+" WHERE co_emp="+intCodEmp+" AND " +
        " co_itm="+intCodItm+" AND co_emprel="+intCodEmpCompra;
        stmLoc.executeUpdate(strSql);

//         if(intDatVen==1) stbDatVen.append(" UNION ALL ");
//          stbDatVen.append("SELECT "+intCodEmp+" as coemp, "+intCodLoc+" as coloc, "+intCodTipDoc+"  as cotipdoc, "+intCodDoc+" AS codoc, "+intCodBod+" as cobod  ");
//         intDatVen=1;

      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException e){ blnRes=false;  mostarErrorException(e);     }
    catch(Exception e)   { blnRes=false;  mostarErrorException(e);    }
 return blnRes;
}


   
private boolean generaVenCab(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpCom,  int TipMov, double dlbSub, double dlbValIva, double dlbTot, String strEstConf, int intCodBodDes  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
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



      datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
     
     if(TipMov==1){
       if(intCodEmpCompra==1){
          if(intCodEmp==2) { strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=2854";  }
          if(intCodEmp==4) { strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=3117";  }
       }
       if(intCodEmpCompra==2){
          if(intCodEmp==1){
              if(intCodTipDoc==126){
                  strCui="MANTA ";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=603";
              }else if(intCodTipDoc==166){
                  strCui="SANTO DOMINGO";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=603";
              }
              else if(intCodTipDoc==225){
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
       if(intCodEmpCompra==4){
          if(intCodEmp==2){ strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=789";  }
          if(intCodEmp==1){ strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=1039";  }
        }
    }
    if(TipMov==2){
       if(intCodEmpCompra==1){
          if(intCodEmpCom==2){
              if(intCodTipDoc==130){
                  strCui="MANTA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
                  " WHERE a.co_emp="+intCodEmpCompra+" AND a.co_cli=603";

              }else if(intCodTipDoc==165){
                  strCui="SANTO DOMINGO";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
                  " WHERE a.co_emp="+intCodEmpCompra+" AND a.co_cli=603";
              }
              else if(intCodTipDoc==224){
                  strCui="CUENCA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=4 ) " +
                  " WHERE a.co_emp="+intCodEmpCompra+" AND a.co_cli=603";
              }
              else{
                  strCui="QUITO ";
                  strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmpCompra+" AND co_cli=603";
              }

          }
          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmpCompra+" AND co_cli=1039";
       }
       if(intCodEmpCompra==2){
          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmpCompra+" AND co_cli=2854";
          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmpCompra+" AND co_cli=789";
       }
       if(intCodEmpCompra==4){
          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmpCompra+" AND co_cli=3117";
          if(intCodEmpCom==2){

              if(intCodTipDoc==130){
                  strCui="MANTA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
                  " WHERE a.co_emp="+intCodEmpCompra+" AND a.co_cli=498";

              }else if(intCodTipDoc==165){
                  strCui="SANTO DOMINGO";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
                  " WHERE a.co_emp="+intCodEmpCompra+" AND a.co_cli=498";
              }
              else if(intCodTipDoc==224){
                  strCui="CUENCA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=4 ) " +
                  " WHERE a.co_emp="+intCodEmpCompra+" AND a.co_cli=498";
              }
              else{
                  strCui="QUITO ";
                  strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmpCompra+" AND co_cli=498";
              }


          }

       }
    }

    System.out.println("---> "+strSql);

     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){

          intNumDoc=getUltNumDoc(conn, intCodEmp, intCodLoc, intCodTipDoc);

      if(intDatDocRel==1)  stbDatDocRel.append(" UNION ALL ");
        stbDatDocRel.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC, "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC ");
       intDatDocRel=1;

          strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
          " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, " +
          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng,  " +
          " co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv, co_ptodes, st_creguirem ) " +
          " VALUES("+intCodEmp+", "+intCodLoc+", "+
          intCodTipDoc+", "+intCodDoc+", '"+datFecAux+"', "+rstLoc.getString("co_cli")+" ,null,'','"+
          rstLoc.getString("tx_nom")+"','"+rstLoc.getString("tx_dir")+"','"+rstLoc.getString("tx_ide")+ "','"+rstLoc.getString("tx_tel")+"'," +
          "'"+strCui+"','', "+intNumDoc+" , 0,"+
          " '' ,'RELIZADO MANUALMENTE' , "+dlbSub+" ,0 , "+dlbTot+", "+dlbValIva+" , 1 ,'Contado', "+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+
          objZafParSis.getCodigoUsuario()+",  null, null," +
          " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', 45 "+
          " ,'S', 'S', '"+strEstConf+"', "+(intCodBodDes==0?null:String.valueOf(intCodBodDes))+", 'S' )";
          strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDoc+" WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc;
          
         // System.out.println("--> "+ strSql );

          stmLoc.executeUpdate(strSql);

          blnRes=true;
          
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      

  }}catch(java.sql.SQLException e){ blnRes=false;  mostarErrorException(e);    }
    catch(Exception e)   { blnRes=false;  mostarErrorException(e);     }
 return blnRes;
}





private int _getCodigoBodDes(java.sql.Connection conn, int CodEmpCom, int intCodBodCom, int intCodEmpVen){
 int intCodBodDes=-1;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

     strSql="select  a1.co_bod  from tbr_bodEmpBodGrp as a " +
     " inner join tbr_bodEmpBodGrp as a1 on (a1.co_empgrp=a.co_empgrp and a1.co_bodgrp=a.co_bodgrp ) " +
     " where a.co_emp="+CodEmpCom+" and a.co_bod="+intCodBodCom+"   and a1.co_emp="+intCodEmpVen+" ";
     System.out.println(" Bodega Destino "+strSql );
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
         intCodBodDes=rstLoc.getInt("co_bod");
     }
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;

  }}catch(java.sql.SQLException e){ intCodBodDes=-1;  mostarErrorException(e);   }
    catch(Exception e)   { intCodBodDes=-1;   mostarErrorException(e); }
 return intCodBodDes;
}




private int _getBodegaIng(java.sql.Connection conn, int intCodEmp, int intCodBodGrp ){
 int intCodBod=-1;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
     if(conn!=null){
      stmLoc=conn.createStatement();

      strSql="select co_bod  from tbr_bodEmpBodGrp  where co_emp="+intCodEmp+" and co_bodgrp="+intCodBodGrp+" ";
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
          intCodBod=rstLoc.getInt("co_bod");
      }
      rstLoc.close();
      rstLoc=null;

    stmLoc.close();
    stmLoc=null;
   }}catch(java.sql.SQLException e) { intCodBod=-1; mostarErrorException(e);   }
     catch(Exception e){ intCodBod=-1; mostarErrorException(e);  }
   return intCodBod;
 }













private boolean insertarDiario(java.sql.Connection conn, int intCodEmpFac, int intCodLocFac, int intCodTipDocFac, int intCodDocFac ){
  boolean blnRes=false;
  java.sql.Statement stmLoc, stmLocIns;
  String strSql="", strSqlIns="";
  String strPer=null, strMes="";
  String strFecSistema="";
  java.util.Date datFecAux;
  int intCodPer=0;
  int intMes =0;
  try{
     if(conn!=null){
      stmLoc=conn.createStatement();

       datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
        if(datFecAux==null)
         return false;

   /********************************************************************************************/

    int intArlAni=0;
    int intArlMes=0;
    String strArlTipCie="";
    int intRefAniNew=0;
    int intRefMesNew=0;
    int intTipPro=0;
    intRefAniNew=(datFecAux.getYear() + 1900);
    intRefMesNew=(datFecAux.getMonth() + 1 );

    //SI EL Aï¿½O NO HA SIDO CREADO EN EL SISTEMA NO SE DEBE PERMITIR INGRESAR(NO EXISTE EL ANIO EN tbm_anicresis)
    if( ! (objZafParSis.isAnioDocumentoCreadoSistema(intRefAniNew))  ){
        MensajeInf("<HTML>El documento no puede ser grabado en el año<FONT COLOR=\"blue\"> " + intRefAniNew + " </FONT> debido a que dicho año todavía no ha sido creado en el sistema<BR>Notifique este problema a su Administrador del Sistema</HTML>");
        return false;
    }
    //ESTE CODIGO ES NUEVO, Y PERMITE VALIDAR Q NO SE INGRESEN DIARIOS CON CIERRES MENSUALES O ANUALES
    if( ! (retAniMesCie(conn, intCodEmpFac, intRefAniNew)))
        return false;
    for (int k=0; k<arlDatAniMes.size(); k++){
        intArlAni=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_ANI_CIE);
        intArlMes=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_MES_CIE);
        strArlTipCie=(objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE)==null?"":objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE));
        if( (strArlTipCie.toString().equals("M"))  ){
            if(intRefAniNew==intArlAni){
                if(intRefMesNew==intArlMes){
                    MensajeInf("<HTML>El mes que desea ingresar está cerrado. <BR>Está tratando de INSERTAR un documento en un periodo cerrado. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
        }
        else{
            MensajeInf("<HTML>La fecha del documento es incorrecta. <BR>Está tratando de INSERTAR un documento en un periodo que tiene un cierre anual. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
            return false;
        }
    }

  /********************************************************************************************/



      strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());

      strSqlIns="INSERT INTO  tbm_cabdia(co_emp, co_loc, co_tipDoc, co_dia, fe_dia, tx_glo, "+
      " fe_ing, co_usrIng, fe_ultMod, co_usrMod ) "+
      " VALUES("+intCodEmpFac+", "+intCodLocFac+", "+intCodTipDocFac+", "+
      intCodDocFac+", '"+datFecAux +"','','"+strFecSistema+"', "+objZafParSis.getCodigoUsuario()+", "+
      " '"+strFecSistema+"', "+objZafParSis.getCodigoUsuario()+")";
      //stmLoc.executeUpdate(strSql);

      intMes = datFecAux.getMonth()+1;
      if(intMes<10)  strMes="0"+String.valueOf(intMes);
        else  strMes=String.valueOf(intMes);

      strPer =  String.valueOf((datFecAux.getYear()+1900))+strMes;
      intCodPer=Integer.parseInt(strPer);


      
           stmLocIns=conn.createStatement();
           stmLocIns.executeUpdate(strSqlIns);
           stmLocIns.close();
           stmLocIns=null;


      if(insertarDetDia(conn, intCodEmpFac,  intCodLocFac, intCodTipDocFac, intCodDocFac, intCodPer)){
          blnRes=true;
     }else blnRes=false;



    stmLoc.close();
    stmLoc=null;
   }}catch(java.sql.SQLException e) { blnRes=false; mostarErrorException(e);   }
     catch(Exception e){ blnRes=false; mostarErrorException(e);  }
   return blnRes;
 }

private boolean insertarDetDia(java.sql.Connection conn, int intCodEmpFac, int intCodLocFac, int intCodTipDocFac, int intCodDocFac, int intCodPer){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String srtSql="", strSqlIns="";
  String srtSqlSal="";
  String strSQL="";
  double dblValTotDes=0;
  try{
    if(conn!=null){
    stmLoc=conn.createStatement();
    
        
    srtSql="INSERT INTO tbm_detdia(co_emp, co_loc, co_tipDoc, co_dia, co_reg, co_cta, nd_mondeb, nd_monhab )"+
    " VALUES("+intCodEmpFac+", "+intCodLocFac+", "+intCodTipDocFac+", "+intCodDocFac+", ";

     dblValTotDes = dblTotalDescuento;
     if(dblToTalFac>0){
        //java.sql.PreparedStatement pstDetDia = conn.prepareStatement(srtSql+" 1,"+objZafCtaCtb_dat.getCtaDeb()+","+
        //dblTotalCot+", 0 ) " );
        //pstDetDia.executeUpdate();

        strSqlIns+=srtSql+" 1,"+objZafCtaCtb_dat.getCtaDeb()+","+dblToTalFac+", 0 )  ; ";

        srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+dblToTalFac+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objZafCtaCtb_dat.getCtaDeb()+" AND co_per="+intCodPer;
        strSqlIns+=srtSqlSal+"  ; ";
    

     }
    if( dblValTotDes > 0){
//       java.sql.PreparedStatement pstDetDia = conn.prepareStatement(srtSql+" 2, "+objZafCtaCtb_dat.getCtaDescVentas()+", "+
//       dblValTotDes+", 0 )");
//       pstDetDia.executeUpdate();

       strSqlIns+=srtSql+" 2, "+objZafCtaCtb_dat.getCtaDescVentas()+", "+dblValTotDes+", 0 ) ; ";

       srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+dblValTotDes+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objZafCtaCtb_dat.getCtaDescVentas()+" AND co_per="+intCodPer;
       strSqlIns+=srtSqlSal+"  ; ";

       //stmLoc.executeUpdate(srtSqlSal);
     }
     /** Agregando la cuenta de Ventas  */
     if( (dblValTotDes+dblSubToTalFac) > 0){
//         java.sql.PreparedStatement pstDetDia = conn.prepareStatement(srtSql+" 3, "+objZafCtaCtb_dat.getCtaHab()+", 0, " +
//         objUti.redondear(dblValTotDes+ dblSubtotalCot,2)+" )");
//         pstDetDia.executeUpdate();

         strSqlIns+=srtSql+" 3, "+objZafCtaCtb_dat.getCtaHab()+", 0, "+objUti.redondear(dblValTotDes+ dblSubToTalFac,2)+" ) ; ";

         srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+(objUti.redondear(dblValTotDes+ dblSubToTalFac,2)*-1)+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objZafCtaCtb_dat.getCtaHab()+" AND co_per="+intCodPer;
         strSqlIns+=srtSqlSal+"  ; ";

         //stmLoc.executeUpdate(srtSqlSal);
     }
     /** Agregando la cuenta Iva en Ventas  */
     if( dblValIvaFac > 0){
//         java.sql.PreparedStatement pstDetDia = conn.prepareStatement(srtSql+" 4 , "+objZafCtaCtb_dat.getCtaIvaVentas()+", 0, "+
//         dblIvaCot+" )");
//         pstDetDia.executeUpdate();

         strSqlIns+=srtSql+" 4 , "+objZafCtaCtb_dat.getCtaIvaVentas()+", 0, "+dblValIvaFac+" ) ; ";

         srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+(dblValIvaFac*-1)+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objZafCtaCtb_dat.getCtaIvaVentas()+" AND co_per="+intCodPer;
         strSqlIns+=srtSqlSal+"  ; ";

         //stmLoc.executeUpdate(srtSqlSal);
     }

    //PARA ACTUALIZAR SALDOS DE LOS NODOS PADRES
    for(int j=6; j>1; j--){
        strSQL="";
        strSQL+="UPDATE tbm_salCta";
        strSQL+=" SET nd_salCta=b1.nd_salCta";
        strSQL+=" FROM (";
        strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intCodPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
        strSQL+=" FROM tbm_plaCta AS a1";
        strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
        strSQL+=" WHERE a1.co_emp=" + intCodEmpFac;
        strSQL+=" AND a1.ne_niv=" + j;
        strSQL+=" AND a2.co_per=" + intCodPer + "";
        strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
        strSQL+=" ) AS b1";
        strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
        strSqlIns+=strSQL+" ;  ";
        //stmLoc.executeUpdate(strSQL);
   }
        //PARA ACTUALIZAR EL SALDO DE LA CUENTA DE ESTADO DE RESULTADOS
        strSQL="UPDATE tbm_salCta";
        strSQL+=" SET nd_salCta=b1.nd_salCta";
        strSQL+=" FROM (";
        strSQL+=" SELECT a1.co_emp, a3.co_ctaRes AS co_cta, " + intCodPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
        strSQL+=" FROM tbm_plaCta AS a1";
        strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
        strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
        strSQL+=" WHERE a1.co_emp=" + intCodEmpFac;
        strSQL+=" and a1.ne_niv='1' and a1.tx_niv1 in ('4','5','6','7','8')";
        strSQL+=" AND a2.co_per=" + intCodPer + "";
        strSQL+=" GROUP BY a1.co_emp, a3.co_ctaRes";
        strSQL+=" ) AS b1";
        strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
        strSqlIns+=strSQL+" ;  ";
        //stmLoc.executeUpdate(strSQL);

         //PARA ACTUALIZAR SALDOS DE LOS NODOS PADRES
    for(int j=6; j>1; j--){
        strSQL="";
        strSQL+="UPDATE tbm_salCta";
        strSQL+=" SET nd_salCta=b1.nd_salCta";
        strSQL+=" FROM (";
        strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intCodPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
        strSQL+=" FROM tbm_plaCta AS a1";
        strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
        strSQL+=" WHERE a1.co_emp=" + intCodEmpFac;
        strSQL+=" AND a1.ne_niv=" + j;
        strSQL+=" AND a2.co_per=" + intCodPer + "";
        strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
        strSQL+=" ) AS b1";
        strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
        strSqlIns+=strSQL+" ;  ";
        //stmLoc.executeUpdate(strSQL);
   }


      stmLoc.executeUpdate(strSqlIns);
     
      stmLoc.close();
      stmLoc=null;
     blnRes=true;
  }}catch(java.sql.SQLException e){ blnRes=false;   mostarErrorException(e);  }
    catch(Exception e) {  blnRes=false;   mostarErrorException(e);  }
 return blnRes;
}

private boolean retAniMesCie(java.sql.Connection conn, int intCodEemp, int anio){
    boolean blnRes=true;
    arlDatAniMes.clear();
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSQL="";
    try{
        if(conn!=null){
            stmLoc=conn.createStatement();
            strSQL="";
            strSQL+="select a1.ne_ani, a2.ne_mes, a1.tx_tipCie";
            strSQL+=" from tbm_cabciesis as a1 left outer join tbm_detciesis as a2";
            strSQL+=" on a1.co_emp=a2.co_emp and a1.ne_ani=a2.ne_ani";
            strSQL+=" where a1.co_emp=" + intCodEemp+ "";
            strSQL+=" and a1.ne_ani=" + anio + "";
            rstLoc=stmLoc.executeQuery(strSQL);
            while(rstLoc.next()){
                arlRegAniMes=new java.util.ArrayList();
                arlRegAniMes.add(INT_ARL_ANI_CIE, "" + rstLoc.getInt("ne_ani"));
                arlRegAniMes.add(INT_ARL_MES_CIE, "" + rstLoc.getInt("ne_mes"));
                arlRegAniMes.add(INT_ARL_TIP_CIE, "" + rstLoc.getString("tx_tipCie"));
                arlDatAniMes.add(arlRegAniMes);
            }
            rstLoc.close();
            rstLoc=null;
            stmLoc.close();
            stmLoc=null;

     }}catch(java.sql.SQLException e){ blnRes=false;   mostarErrorException(e); }
     catch(Exception e){   blnRes=false;  mostarErrorException(e);  }
   return blnRes;
 }

private boolean insertarCabFac(java.sql.Connection conn, int intCodEmpFac, int intCodLocFac, int intCodTipDocFac, int intCodDocFac, int intCodDocFacNue, int intCodEmpGui, int intCodLocGui, int intCodTipDocGui, int intCodDocGui, int intCodDocGuiNue ){
  boolean blnRes=false;
  java.sql.Statement stmLoc, stmLoc01;
  String strSql="";
  java.util.Date datFecAux;
  try{
      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();



//      strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
//
      datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
        if(datFecAux==null)
         return false;




       strSql="INSERT INTO tbm_cabmovinv( co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "
       + "  tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, "
       + "  tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng,   "
       + "  co_forret,tx_vehret,tx_choret, st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu "
       + "  ,st_coninvtraaut, st_excDocConVenCon, st_coninv , st_creguirem )  "
       + "  SELECT co_emp, co_loc, co_tipDoc, "+intCodDocFacNue+" AS co_doc, '"+datFecAux+"' as fe_doc, co_cli, co_com, tx_ate, "
       + "  tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, "
       + "  tx_obs1, tx_obs2, "+dblSubToTalFac*-1+" as nd_sub, nd_porIva, "+dblToTalFac*-1+" as  nd_tot, "+dblValIvaFac*-1+" as nd_valiva, co_forPag, tx_desforpag, "+objZafParSis.getFuncionFechaHoraBaseDatos()+", co_usrIng,   "
       + "  co_forret,tx_vehret,tx_choret, 'A' as st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu "
       + "  ,st_coninvtraaut, st_excDocConVenCon, st_coninv , st_creguirem  "
       + "  FROM tbm_cabmovinv where co_emp="+intCodEmpFac+"  and  co_loc="+intCodLocFac+"  and  co_tipdoc ="+intCodTipDocFac+"  and  co_doc= "+intCodDocFac+" "
       + " ";


       strSql+=" ; INSERT INTO tbm_cabguirem( co_emp, co_loc, co_tipdoc, co_doc, fe_doc ,ne_numdoc, co_clides, tx_rucclides, tx_nomclides, "
       + " tx_dirclides, tx_telclides, tx_ciuclides, st_imp, st_reg, fe_ing, co_usring ,st_conInv, fe_initra, fe_tertra, tx_coming, "
       + " st_regrep, co_ptopar, tx_ptopar, co_forret, tx_vehret, tx_choret ,tx_datdocoriguirem , co_ven, tx_nomven ) "
       + " SELECT co_emp, co_loc, co_tipdoc, "+intCodDocGuiNue+" AS co_doc,  '"+datFecAux+"' as  fe_doc ,ne_numdoc, co_clides, tx_rucclides, tx_nomclides, "
       + " tx_dirclides, tx_telclides, tx_ciuclides, st_imp, 'A' as  st_reg, "+objZafParSis.getFuncionFechaHoraBaseDatos()+", co_usring ,st_conInv, fe_initra, fe_tertra, '"+objZafParSis.getNombreComputadoraConDirIP()+"', "
       + " st_regrep, co_ptopar, tx_ptopar, co_forret, tx_vehret, tx_choret ,tx_datdocoriguirem , co_ven, tx_nomven "
       + "  FROM tbm_cabguirem  "
       + " where co_emp="+intCodEmpGui+"  and  co_loc="+intCodLocGui+"  and  co_tipdoc ="+intCodTipDocGui+"  and  co_doc= "+intCodDocGui+" ";

       //System.out.println(""+strSql );

       stmLoc.executeUpdate(strSql);

      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;
      blnRes=true;


   }catch(java.sql.SQLException e){ blnRes=false;  mostarErrorException(e);  }
    catch(Exception e)   { blnRes=false;   mostarErrorException(e);   }
 return blnRes;
}

private boolean insertarDetFac(java.sql.Connection conn ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  try{
      stmLoc=conn.createStatement();

        strSql=" ; INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can,nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_preunivenlis, nd_pordesvenmax , ne_numfil )" + stbins.toString();

        strSql+=" ; INSERT INTO  tbm_detguirem (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel," +
        " co_tipdocrel, co_docrel, co_regrel, co_itm, tx_codalt,  tx_nomitm, tx_unimed, nd_can, st_regrep, tx_obs1, st_meregrfisbod ) "+stbinsGuia.toString();

        //System.out.println(""+strSql );

       stmLoc.executeUpdate(strSql);
    
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

   }catch(java.sql.SQLException e){ blnRes=false;  mostarErrorException(e);  }
    catch(Exception e)   { blnRes=false;   mostarErrorException(e);   }
 return blnRes;
}



 StringBuffer stbins=new StringBuffer(); //VARIABLE TIPO BUFFER
 StringBuffer stbinsGuia=new StringBuffer();

public boolean _calculaTotal(java.sql.Connection conn, int intCodEmpFac, int intCodLocFac, int intCodTipDocFac, int intCodDocFac, double dblPorIva, int intCodDocFacNue, int intCodEmpGui, int intCodLocGui, int intCodTipDocGui, int intCodDocGuiNue  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc,stmLoc01;
 java.sql.ResultSet rstLoc,rstLoc01;
 String strSql="";
 String strItmCanNunRec="";
 double dblCanNunRec=0;
 double dblCanFac=0, dlbcostouni=0, bldcostot=0;
 double dblCanVolFac=0, dblPreuni=0, dblPorDes=0, dblValDes=0, dblTotal=0, dblSubTot=0, dblIva=0, dblTotalDoc=0;
 int intNumFil=0;
 int intEstIns=0;
 int intEstInsGuia=0;
 int intReaActStk=0;
 int intTipCli=3;
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();

      objInvItm.limpiarObjeto();
      objInvItm.inicializaObjeto();

      strSql="SELECT a2.co_itm, sum(a2.nd_cannunrec) as cannunrec  FROM ( "
      + "  select co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel  from tbr_detmovinv "
      + "  where co_emp="+intCodEmpFac+" and co_loc="+intCodLocFac+" and co_tipdoc="+intCodTipDocFac+" and co_doc="+intCodDocFac+"   "
      + "  group by co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel "
      + " ) as a "
      + " inner join tbm_detmovinv  as a2 on (a2.co_emp=a.co_emprel and a2.co_loc=a.co_locrel and a2.co_tipdoc=a.co_tipdocrel and a2.co_doc=a.co_docrel and a2.co_reg=a.co_regrel ) "
      + " inner join tbm_cabmovinv  as a1 on (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc ) "
      + " left  join tbm_cli as a3 on (a3.co_emp=a1.co_emp and a3.co_cli=a1.co_cli and a3.co_empgrp =0  )  "
      + " where nd_cannunrec > 0  "
      + " and  a1.st_coninv not in ('F')  GROUP BY  a2.co_itm ";

      //System.out.println(" _calculaTotal  "+ strSql );

      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){

        dblCanNunRec=rstLoc.getDouble("cannunrec");

        if(!strItmCanNunRec.equals("")) strItmCanNunRec+=",";
         strItmCanNunRec += rstLoc.getString("co_itm");

        strSql="SELECT a2.st_ser, a.st_meringegrfisbod, a.nd_preunivenlis, a.nd_pordesvenmax, a.ne_numfil, a.st_ivacom, a.nd_cosuni, a.tx_unimed, a.tx_nomitm, a.tx_codalt, a.co_itm, a.co_bod,  abs(a.nd_can) as ndcan, a.nd_preuni, a.nd_pordes "
        + " FROM tbm_detmovinv as a "
        + " INNER JOIN tbm_inv AS a2 ON (a2.co_emp=a.co_emp and a2.co_itm=a.co_itm ) "
        + "where a.co_emp="+intCodEmpFac+" and a.co_loc="+intCodLocFac+" and a.co_tipdoc="+intCodTipDocFac+" and a.co_doc="+intCodDocFac+"  "
        + " and a.co_itm= "+rstLoc.getInt("co_itm")+"  ";
        rstLoc01=stmLoc01.executeQuery(strSql);
        while(rstLoc01.next()){

          dblCanFac=rstLoc01.getDouble("ndcan");

          if(dblCanNunRec>0){

            if( dblCanNunRec >= dblCanFac ){

                dblCanNunRec=dblCanNunRec-dblCanFac;
                dblCanFac=dblCanFac-dblCanFac;
                // no se genera para la nueva factura

            }else{

                 dblCanFac=dblCanFac-dblCanNunRec;
                 dblCanNunRec=dblCanNunRec-dblCanNunRec;
                 // se factura lo que quedad

            }

          }

            if(dblCanFac > 0 ){
                intNumFil++;
                dblCanVolFac = dblCanFac;
                dblPreuni = rstLoc01.getDouble("nd_preuni");
                dblPorDes = rstLoc01.getDouble("nd_pordes");
                dblValDes =   ((dblCanVolFac * dblPreuni)==0)?0:((dblCanVolFac * dblPreuni) * (dblPorDes / 100));
                dblTotal  = (dblCanVolFac * dblPreuni)- dblValDes;

                dblTotalDescuento +=  dblValDes;

                dblTotal =  objUti.redondear(dblTotal,3);
                dblTotal =  objUti.redondear(dblTotal,2);

                if(rstLoc01.getString("st_ser").equals("T")) dblTotalTrans +=  dblTotal;
                else  dblTotalSinTrans +=  dblTotal;

                dblSubTot += dblTotal;

                if(rstLoc01.getString("st_ivacom").equals("S"))
                    dblIva = dblIva + (((dblTotal * dblPorIva)==0)?0:(dblTotal * dblPorIva)/100) ;
                 else  dblIva = dblIva + 0;




                dlbcostouni=rstLoc01.getDouble("nd_cosuni");
                bldcostot=(dlbcostouni * dblCanVolFac);
                bldcostot=(bldcostot*-1);

                

               if(rstLoc01.getString("st_ser").equals("N")){
                 objInvItm.generaQueryStock(intCodEmpFac, rstLoc01.getInt("co_itm"), rstLoc01.getInt("co_bod"), dblCanVolFac, -1, "N", rstLoc01.getString("st_meringegrfisbod"), "E", 1);
                 intReaActStk=1;
               }


                if (intEstIns == 1) stbins.append(" UNION ALL ");

                 stbins.append("SELECT "+intCodEmpFac+","+intCodLocFac+","+intCodTipDocFac+","+intCodDocFacNue+","+intNumFil+",'" +
                 rstLoc01.getString("tx_codalt")+"','"+rstLoc01.getString("tx_codalt")+"',"+rstLoc01.getString("co_itm")+", "+rstLoc01.getString("co_itm")+", '"+
                 rstLoc01.getString("tx_nomitm")+"','"+rstLoc01.getString("tx_unimed")+"',"+rstLoc01.getString("co_bod")+","+
                 dblCanVolFac*-1+","+dblTotal*-1+", "+rstLoc01.getString("nd_cosuni") + ", 0 , "+objUti.redondear( dblPreuni,4) + ", " +
                 objUti.redondear( dblPorDes ,2) + ", '"+rstLoc01.getString("st_ivacom")+ "' " +
                 ","+bldcostot+",'I', '"+rstLoc01.getString("st_meringegrfisbod")+"', 0, "+
                 rstLoc01.getString("nd_preunivenlis")+", "+rstLoc01.getString("nd_pordesvenmax")+", " +
                 " "+rstLoc01.getString("ne_numfil")+" ");

                intEstIns=1;

                if(intEstInsGuia == 1) stbinsGuia.append(" UNION ALL ");

                 stbinsGuia.append("SELECT "+intCodEmpGui+","+intCodLocGui+","+intCodTipDocGui+","+intCodDocGuiNue+","+intNumFil+" " +
                 " ,"+intCodEmpFac+","+intCodLocFac+","+intCodTipDocFac+","+intCodDocFacNue+","+intNumFil+" " +
                 " , "+rstLoc01.getString("co_itm")+", '"+rstLoc01.getString("tx_codalt")+"' " +
                 " ,'"+rstLoc01.getString("tx_nomitm")+"','"+rstLoc01.getString("tx_unimed")+"', "+dblCanVolFac+" " +
                 " ,'I', '', '"+rstLoc01.getString("st_meringegrfisbod")+"' ");

                 intEstInsGuia=1;



               blnRes=true;
            }

        }
        rstLoc01.close();
        rstLoc01=null;

      }
      rstLoc.close();
      rstLoc=null;




        strSql="SELECT   a2.st_ser, a.st_meringegrfisbod, a.nd_preunivenlis, a.nd_pordesvenmax, a.ne_numfil, a.st_ivacom, a.nd_cosuni, a.tx_unimed, a.tx_nomitm, a.tx_codalt, a.co_itm, a.co_bod,  abs(a.nd_can) as ndcan, a.nd_preuni, a.nd_pordes "
        + " FROM tbm_detmovinv as a "
        + " INNER JOIN tbm_inv AS a2 ON (a2.co_emp=a.co_emp and a2.co_itm=a.co_itm ) "
        + " where a.co_emp="+intCodEmpFac+" and a.co_loc="+intCodLocFac+" and a.co_tipdoc="+intCodTipDocFac+" and a.co_doc="+intCodDocFac+"  ";

        if(!strItmCanNunRec.equals(""))
         strSql += " and a.co_itm NOT IN ("+strItmCanNunRec+")  ";

        rstLoc01=stmLoc01.executeQuery(strSql);
        while(rstLoc01.next()){

                intNumFil++;
                dblCanVolFac =  rstLoc01.getDouble("ndcan");
                dblPreuni = rstLoc01.getDouble("nd_preuni");
                dblPorDes = rstLoc01.getDouble("nd_pordes");
                dblValDes =   ((dblCanVolFac * dblPreuni)==0)?0:((dblCanVolFac * dblPreuni) * (dblPorDes / 100));
                dblTotal  = (dblCanVolFac * dblPreuni)- dblValDes;

                dblTotalDescuento +=  dblValDes;

                dblTotal =  objUti.redondear(dblTotal,3);
                dblTotal =  objUti.redondear(dblTotal,2);

                if(rstLoc01.getString("st_ser").equals("T")) dblTotalTrans +=  dblTotal;
                else  dblTotalSinTrans +=  dblTotal;


                dblSubTot += dblTotal;

                if(rstLoc01.getString("st_ivacom").equals("S"))
                    dblIva = dblIva + (((dblTotal * dblPorIva)==0)?0:(dblTotal * dblPorIva)/100) ;
                 else  dblIva = dblIva + 0;

                dlbcostouni=rstLoc01.getDouble("nd_cosuni");
                bldcostot=(dlbcostouni * dblCanVolFac);
                bldcostot=(bldcostot*-1);


               if(rstLoc01.getString("st_ser").equals("N")){
                 objInvItm.generaQueryStock(intCodEmpFac, rstLoc01.getInt("co_itm"), rstLoc01.getInt("co_bod"), dblCanVolFac, -1, "N", rstLoc01.getString("st_meringegrfisbod"), "E", 1);
                 intReaActStk=1;
               }


                if (intEstIns == 1) stbins.append(" UNION ALL ");

                 stbins.append("SELECT "+intCodEmpFac+","+intCodLocFac+","+intCodTipDocFac+","+intCodDocFacNue+","+intNumFil+",'" +
                 rstLoc01.getString("tx_codalt")+"','"+rstLoc01.getString("tx_codalt")+"',"+rstLoc01.getString("co_itm")+", "+rstLoc01.getString("co_itm")+", '"+
                 rstLoc01.getString("tx_nomitm")+"','"+rstLoc01.getString("tx_unimed")+"',"+rstLoc01.getString("co_bod")+","+
                 dblCanVolFac+","+dblTotal+", "+rstLoc01.getString("nd_cosuni") + ", 0 , "+objUti.redondear( dblPreuni,4) + ", " +
                 objUti.redondear( dblPorDes ,2) + ", '"+rstLoc01.getString("st_ivacom")+ "' " +
                 ","+bldcostot+",'I', '"+rstLoc01.getString("st_meringegrfisbod")+"', 0, "+
                 rstLoc01.getString("nd_preunivenlis")+", "+rstLoc01.getString("nd_pordesvenmax")+", " +
                 " "+rstLoc01.getString("ne_numfil")+" ");

                 intEstIns=1;

                 if(intEstInsGuia == 1) stbinsGuia.append(" UNION ALL ");

                 stbinsGuia.append("SELECT "+intCodEmpGui+","+intCodLocGui+","+intCodTipDocGui+","+intCodDocGuiNue+","+intNumFil+" " +
                 " ,"+intCodEmpFac+","+intCodLocFac+","+intCodTipDocFac+","+intCodDocFacNue+","+intNumFil+" " +
                 " , "+rstLoc01.getString("co_itm")+", '"+rstLoc01.getString("tx_codalt")+"' " +
                 " ,'"+rstLoc01.getString("tx_nomitm")+"','"+rstLoc01.getString("tx_unimed")+"', "+dblCanVolFac+" " +
                 " ,'I', '', '"+rstLoc01.getString("st_meringegrfisbod")+"' ");

                 intEstInsGuia=1;
                 
                blnRes=true;
            
        }
        rstLoc01.close();
        rstLoc01=null;


        dblIva = objUti.redondear(dblIva,2);
        dblSubTot = objUti.redondear(dblSubTot ,2);
        dblTotalDoc = dblSubTot + dblIva;
        dblTotalDoc = objUti.redondear(dblTotalDoc ,2);

        dblTotalDescuento =  objUti.redondear(dblTotalDescuento ,2);


        dblSubToTalFac=dblSubTot;
        dblValIvaFac=dblIva;
        dblToTalFac=dblTotalDoc;
        


        
      if(intReaActStk==1){
        if(!objInvItm.ejecutaActStock(conn, intTipCli))
          return false;

        if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli))
          return false;
      }
      objInvItm.limpiarObjeto();


      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;

 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}









/**
 * Obtiene el codigo maximo del documento a generara
 * @param conn
 * @return
 */
public int getCodigoMaxDoc(java.sql.Connection conn, String strSql ){
  int intCodDoc=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  try{
    if(conn!=null){

       stmLoc=conn.createStatement();
       rstLoc = stmLoc.executeQuery(strSql);
       if(rstLoc.next()) intCodDoc = rstLoc.getInt(1);
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;

 }}catch(java.sql.SQLException e) {  mostarErrorException(e);   }
   catch(Exception e) {  mostarErrorException(e);  }
 return intCodDoc;
}









/**
 * funcion permite anular documento como son la factura la guia de remision.
 * @param conn
 * @param strNomTbl
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @return
 */
public boolean _AnularFacGui(java.sql.Connection conn, String strNomTbl, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc  ){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 String strSql="";
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      strSql="UPDATE "+strNomTbl+" SET st_reg='I' "
      + "WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" ";
      stmLoc.executeUpdate(strSql);

      stmLoc.close();
      stmLoc=null;

 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}



/**
 * funcion permite anular documento de asiento de diario.
 * @param conn
 * @param strNomTbl
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @return
 */
public boolean _AnularDiario(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc  ){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 String strSql="";
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      strSql="UPDATE tbm_cabdia SET st_reg='I' "
      + "WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" ";
      stmLoc.executeUpdate(strSql);

      stmLoc.close();
      stmLoc=null;

 }}catch(java.sql.SQLException e) { blnRes=false;  mostarErrorException(e);  }
  catch(Exception  e){ blnRes=false;  mostarErrorException(e);  }
  return blnRes;
}








}
