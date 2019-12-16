/*
 * pantalladialogo.java   st_autIngEgrInvCon
 *
 * Created on 13 de agosto de 2008, 10:45
 */
           
package Ventas.ZafVen01;
import java.util.Vector;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
          
               
/**
 *
 * @author  jayapata
 */
public class ZafVen01_20 extends javax.swing.JDialog {
    java.sql.Connection CONN_GLO=null;
    java.sql.Connection CONN_GLO_REM=null;
    
    Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLbl2, objTblCelRenLbl3;
    private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt; 
    Librerias.ZafParSis.ZafParSis objZafParSis;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    Librerias.ZafInventario.ZafInvItm objInvItm;  // Para trabajar con la informacion de tipo de documento
    Librerias.ZafUtil.UltDocPrint objUltDocPrint;
    ZafUtil objUti;
  
    private java.util.Date datFecAux;  
    
    
    final int INT_TBL_LINEA =0; 
    final int INT_TBL_CODITM=1;
    final int INT_TBL_CODEMP=2;
    final int INT_TBL_CODBOD=3;
    final int INT_TBL_NOMEMP=4;
    final int INT_TBL_NOMBOD=5;
    final int INT_TBL_STKACT=6;
    final int INT_TBL_ESTBOD=7;
    final int INT_TBL_CANCOM=8;
    final int INT_TBL_CODALT=9;
    final int INT_TBL_NOMITM=10;
    final int INT_TBL_UNIMED=11;
    final int INT_TBL_COSUNI=12;

    final int INT_TBL_DATCANCOM=13;
    
    
      
    
    int intNumCotBus=0;
    int intConStbBod=0;
    int[][] intColBodEmp = new int[30][2];
     
    Vector vecCab=new Vector(); 
    ZafVenCon objVenConCLi;
    ZafVenCon objVenConVen; 
    String strCodCli="";
    String strDesCli="";
    String strCodSol="";
    String strDesSol="";
    String strTipPer_emp="";
    StringBuffer stbDevTemp, stbDevInvTemp, stbLisBodItmTemp, stbDocRelEmpTemp;
    public StringBuffer stbLisBodItm;
    public StringBuffer stbDocRelEmp;
    double bldivaEmp=0;
    double Glo_dblCanFalCom=0.00;
    double Glo_dblCanFalDevCom=0.00;
    double Glo_dblCanFalDevVen=0.00;
  
    double Glo_dblTotDevCom=0.00;
    double Glo_dblTotDevVen=0.00;

    boolean blnEstDevVen=false;
    boolean blnEstDevCom=false;

    double Glo_dblCanTotDevCom=0.00;
    int intConStbBodTemp=0;
    int intCodBodPre=0;
    int intCodTipPerEmp=0;
    int intEstReaOcFac=0;
    int INTCODREGCEN=0;
    int intDocRelEmp=0, intDocRelEmpTemp=0;
    int intBodPreDevCom=0;
    double dblCanDevComPar=0;
    private String Str_RegSel[]; 
    public boolean blnAcepta = false; 
    java.util.ArrayList arlAuxColEdi=new java.util.ArrayList();

    StringBuffer stbDatVen=null;
    StringBuffer stbDatInsVen=null;
    int intDatVen=0;

    String StrEstConfDevVen="P";

    StringBuffer stbDatDevCom=null;
    StringBuffer stbDatDevVen=null;
    int intDatDevCom=0;
    int intDatDevVen=0;


    /** Creates new form pantalladialogo */
    public ZafVen01_20(java.awt.Frame parent, boolean modal ,Librerias.ZafParSis.ZafParSis ZafParSis, int intNumCot, java.sql.Connection conn, java.sql.Connection connRem, int  INTCODREGCENBD ) {
       super(parent, modal);
       try{ 
        this.objZafParSis = ZafParSis;
        objUti = new ZafUtil();
        objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);
        objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);
        objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl2 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl3 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        
        stbLisBodItm= new StringBuffer();
        stbDocRelEmp= new StringBuffer();

        initComponents();
       // CONN_GLO=conn;
        CONN_GLO_REM=connRem;
        CONN_GLO=conn;
        INTCODREGCEN=INTCODREGCENBD;
          
        intNumCotBus=intNumCot;
        cargarTipEmp();
        this.setTitle( objZafParSis.getNombreMenu() );
        lblTit.setText( objZafParSis.getNombreMenu());
        System.out.println("=====================>>>>>  AKI!!!! ZafVen01_20  <<<<<======== ");
        }catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);  } 
    }
    
    
  

public void cargarTipEmp(){
 java.sql.Statement stmTipEmp; 
 java.sql.ResultSet rstEmp;
 String sSql;
 try{  
    if(CONN_GLO!=null){
        stmTipEmp=CONN_GLO.createStatement();

        sSql="select b.co_tipper , b.tx_descor , round(a.nd_ivaVen,2) as porIva , bod.co_bod FROM  tbm_emp as a " +
        " left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper)" +
        " left join tbr_bodloc as bod on(bod.co_emp=a.co_emp and bod.co_loc="+objZafParSis.getCodigoLocal()+" and bod.st_reg='P')  " +
        "where a.co_emp="+objZafParSis.getCodigoEmpresa();

        rstEmp = stmTipEmp.executeQuery(sSql);
        if(rstEmp.next()){
            strTipPer_emp = rstEmp.getString("tx_descor");
            bldivaEmp   =  rstEmp.getDouble("porIva");
            intCodBodPre = rstEmp.getInt("co_bod");
            intCodTipPerEmp = rstEmp.getInt("co_tipper");
        }

        rstEmp.close();
        stmTipEmp.close();
        stmTipEmp = null;
        rstEmp = null;
    }
}catch(java.sql.SQLException Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
 catch(Exception Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
}



        
private void configurarFrm(){
      configuraTbl();
      
      configurarVenConClientes();
      configurarVenConVendedor();

     

      if(CONN_GLO_REM!=null) cargarDat(CONN_GLO_REM);
      else cargarDat(CONN_GLO);

    
     
      if(intEstReaOcFac==0){
        blnAcepta = true;
        dispose();
      }
      
} 
  
    
  

 
private boolean cargarDat(java.sql.Connection conn){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01, stmLoc02, stmLoc03, stmLocDat;
 java.sql.ResultSet rstLoc, rstLoc01, rstLoc02, rstLocDat;
 String strSql="", strAuxDesc="";
 Vector vecData;
 StringBuffer stbDev;
 StringBuffer stbDevInv;
 StringBuffer stbDatCot;
 int intEstDev=0;
 int intEstDevError=0;
 int intEstDatCot=0;
 try{
   if(conn!=null){
       
    
      stbDev=new StringBuffer();
      stbDevInv=new StringBuffer();
      stbDatCot=new StringBuffer();

      stmLoc01=conn.createStatement();
      stmLoc02=conn.createStatement();
      stmLoc03=conn.createStatement();

      stmLoc=conn.createStatement();  //  CONN_GLO

      stmLocDat=CONN_GLO.createStatement();

      vecData = new Vector();

       if(objZafParSis.getCodigoEmpresa() != 1)
          strAuxDesc=" desc ";





      strSql=" SELECT  a.co_Emp, a.co_itm, a1.tx_codalt , a1.tx_nomitm, var.tx_descor, a.co_bod, a1.nd_prevta1, a1.nd_cosuni, sum(a.nd_can) as can  " +
      " ,CASE WHEN ( (trim(SUBSTR (UPPER(a1.tx_codalt), length(a1.tx_codalt) ,1))  IN ( SELECT UPPER(trim(tx_cad))  " +
      " FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+"  AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='I'  ))) " +
      " THEN 'S' ELSE 'N' END  as isterL  " +
      " FROM tbm_detcotven as a  " +
      " INNER JOIN tbm_inv AS a1 ON(a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm)  " +
      " LEFT JOIN tbm_var AS var ON(var.co_reg=a1.co_uni)  " +
      " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" AND a.co_cot="+intNumCotBus+" AND a1.st_ser='N'  " +
      " GROUP BY a.co_Emp, a.co_itm, a1.tx_nomitm, var.tx_descor, a.co_bod, a1.nd_prevta1, a1.nd_cosuni, a1.tx_codalt ";
      rstLocDat=stmLocDat.executeQuery(strSql);
      while(rstLocDat.next()){

          if(intEstDatCot==1) stbDatCot.append(" UNION ALL ");
          stbDatCot.append(" SELECT "+rstLocDat.getInt("co_emp")+" as co_emp ,"+rstLocDat.getInt("co_itm")+" as co_itm,text('"+rstLocDat.getString("tx_codalt")+"') as tx_codalt, " +
                           " text('"+rstLocDat.getString("tx_nomitm")+"') as tx_nomitm, text('"+rstLocDat.getString("tx_descor")+"') as tx_descor, "+rstLocDat.getInt("co_bod")+" as co_bod, " +
                           " "+rstLocDat.getDouble("nd_prevta1")+" as nd_prevta1,  "+rstLocDat.getDouble("nd_cosuni")+" as nd_cosuni, "+rstLocDat.getDouble("can")+" as can,  text('"+rstLocDat.getString("isterl")+"') as isterl ");
          intEstDatCot=1;
          
      }
      rstLocDat.close();
      rstLocDat=null;
      stmLocDat.close();
      stmLocDat=null;
      



 


      strSql=" SELECT *, case when  (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1)) in ('S')) then 'S' else 'N' end as Term   FROM ( " +
      " select a2.co_itmmae, x.co_emp, x.co_itm, x.co_bod, x.tx_codalt, x.tx_descor, x.tx_nomitm, a1.nd_stkact , x.can, abs(a1.nd_stkact - x.can) AS canfal, " +
      " x.nd_prevta1, x.nd_cosuni     from ( " +
      " SELECT * FROM ( " +
         stbDatCot.toString()
      +" ) as x WHERE x.isterl='S'  " +
      " ) as x " +
      " INNER JOIN tbm_invbod AS a1 ON(a1.co_emp=x.co_emp AND a1.co_bod=x.co_bod AND a1.co_itm=x.co_itm )  " +
      " INNER JOIN tbm_equinv AS a2 ON(a2.co_emp=x.co_emp and a2.co_itm=x.co_itm ) " +
      " ) AS x   WHERE  (nd_stkact - can)  < 0 ";


      //System.out.println("strSql: "+strSql );
           
      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){

       
       Glo_dblCanFalCom=rstLoc.getDouble("canfal");
             
      strSql="select distinct(co_empper) as empper from tbr_bodemp where co_emp="+objZafParSis.getCodigoEmpresa()+" " +
      " and co_empper not in ("+objZafParSis.getCodigoEmpresa()+")  ORDER BY  empper "+strAuxDesc;
      rstLoc02=stmLoc02.executeQuery(strSql);
      while(rstLoc02.next()){
       stbDevTemp=new StringBuffer();
       stbDevInvTemp=new StringBuffer();
       stbLisBodItmTemp=new StringBuffer();
       stbDocRelEmpTemp=new StringBuffer();
     
       intConStbBodTemp=0;
       intDocRelEmpTemp=0;
       
       if(Glo_dblCanFalCom > 0){
         Glo_dblCanFalDevCom=Glo_dblCanFalCom;
         Glo_dblCanFalDevVen=Glo_dblCanFalCom;

         Glo_dblTotDevCom=0.00;
         Glo_dblTotDevVen=0.00;
      
         if(verificaDev(conn, rstLoc.getString("co_itm"), Glo_dblCanFalCom , rstLoc02.getInt("empper") ) ){
           if(Glo_dblCanFalDevCom==Glo_dblCanFalDevVen){
            if( Glo_dblTotDevCom == Glo_dblTotDevVen ){
                     
                Glo_dblCanFalCom=Glo_dblCanFalDevCom;
                stbDev.append( stbDevTemp.toString() );
                stbDevInv.append( stbDevInvTemp.toString()  );

               if(intConStbBodTemp==1){
                if(intConStbBod==1)  stbLisBodItm.append(" UNION ALL ");
                 stbLisBodItm.append( stbLisBodItmTemp.toString() );
                 intConStbBod=1;
               }

                if(intDocRelEmp==1) stbDocRelEmp.append(" UNION ALL ");
                  stbDocRelEmp.append( stbDocRelEmpTemp.toString() );
                intDocRelEmp=1;

                intEstDev=1;

                System.out.println(" Devlucion Oki " );

              } else{ conn.rollback(); if(CONN_GLO_REM!=null) CONN_GLO_REM.rollback();  intEstDevError=1;  System.out.println(" ..............ROLLBACK....1............"); }
           } else{ conn.rollback(); if(CONN_GLO_REM!=null) CONN_GLO_REM.rollback();  intEstDevError=1;  System.out.println(" ..............ROLLBACK....2............"); }
         } else{ conn.rollback(); if(CONN_GLO_REM!=null) CONN_GLO_REM.rollback();  intEstDevError=1;  System.out.println(" ..............ROLLBACK....3............"); }

          if(intEstDevError==1){
           if(intEstDev==1){
            stmLoc03.executeUpdate( stbDev.toString() );
            stmLoc03.executeUpdate( stbDevInv.toString() );
           }
           intEstDevError=0;
          }
       }
       stbDevTemp=null;
       stbDevInvTemp=null;
       stbLisBodItmTemp=null;
       stbDocRelEmpTemp=null;
    
     }
     rstLoc02.close();
     rstLoc02=null;

      
/********************************************/

   if(Glo_dblCanFalCom > 0){

     int intBodGrp=0; 
     if(objZafParSis.getCodigoEmpresa()==1){
         intBodGrp=1; // BODEGA CALIFORMIA GRUPO
     }
     else if(objZafParSis.getCodigoEmpresa()==4){
         intBodGrp=2; // BODEGA VIA DAULE GRUPO
     }
     else if(objZafParSis.getCodigoEmpresa()==2){
         if(objZafParSis.getCodigoLocal()==1)  intBodGrp=3; // BODEGA QUITO GRUPO
         if(objZafParSis.getCodigoLocal()==4)  intBodGrp=5; // BODEGA MANTA GRUPO
         if(objZafParSis.getCodigoLocal()==6)  intBodGrp=11; //BODEGA SANTO DOMINGO GRUPO
         if(objZafParSis.getCodigoLocal()==10) intBodGrp=28; //BODEGA CUENCA GRUPO
     }


     String strSqlAux=" ,( SELECT  a.co_itm FROM tbm_equinv as a  inner join tbm_inv as a1 on (a1.co_emp=a.co_emp and a1.co_itm=a.co_itm) " +
     " WHERE a.co_emp=x1.co_empper AND a.co_itmmae = "+rstLoc.getString("co_itmmae")+"  ) as  coitmemp " +
     " "+
     " ,( SELECT  a1.nd_prevta1 FROM tbm_equinv as a  inner join tbm_inv as a1 on (a1.co_emp=a.co_emp and a1.co_itm=a.co_itm)  " +
     "   WHERE a.co_emp=x1.co_empper AND a.co_itmmae = "+rstLoc.getString("co_itmmae")+"  ) as  nd_prevta " +
     " " +
     " ,( SELECT  a1.tx_codalt FROM tbm_equinv as a   inner join tbm_inv as a1 on (a1.co_emp=a.co_emp and a1.co_itm=a.co_itm) " +
     "  WHERE a.co_emp=x1.co_empper AND a.co_itmmae = "+rstLoc.getString("co_itmmae")+"  ) as  tx_codalt ";


      strSql="SELECT * FROM (  SELECT x1.* "+strSqlAux+" ,text('"+rstLoc.getString("tx_nomitm")+"') as tx_nomitm " +
      " ,text('"+rstLoc.getString("tx_descor")+"') as tx_descor, "+rstLoc.getString("nd_cosuni")+" as nd_cosuni " +
      " ,"+rstLoc.getString("co_itm")+" as coitmCom FROM ( " +
      " select * from tbr_bodEmpBodGrp where co_empgrp="+objZafParSis.getCodigoEmpresaGrupo()+" and co_bodgrp="+intBodGrp+" " +
      " and co_emp not in (3, "+objZafParSis.getCodigoEmpresa()+" ) " +
      " ) as x " +
      " inner join ( "+

       " SELECT a.co_emp, a.co_loc, a.co_empper, a2.tx_nom, a.co_bodper, a3.tx_nom as nombod, a.st_reg " +
       ",( " +
       " SELECT nd_stkact FROM tbm_invbod AS x " +
       " INNER JOIN tbm_equinv AS x1 ON(x1.co_emp=x.co_emp and x1.co_itm=x.co_itm ) " +
       " INNER JOIN tbm_equinv AS x2 ON(x2.co_emp=x1.co_emp and x2.co_itm=x1.co_itm) " +
       " WHERE x.co_emp=a.co_empper " +
       " AND  x.co_bod=a.co_bodper  AND  x2.co_itmmae = "+rstLoc.getString("co_itmmae")+" " +
       " ) as stk " +
       " FROM tbr_bodemp as a " +
       " inner join tbm_emp as a2 ON (a2.co_emp=a.co_empper) " +
       " inner join tbm_bod as a3 ON (a3.co_emp=a.co_empper and a3.co_bod=a.co_bodper) " +
       " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" order by a.co_empper, a.co_bodper  "+

       " ) as x1 on ( x1.co_empper=x.co_emp and x1.co_bodper=x.co_bod )  ) AS x  WHERE stk > 0   ";

      System.out.println("\n\n\n\n\n\nPARA COMPRA VENTAS-->" +strSql);

      dblCabFalComAut=Glo_dblCanFalCom;

      intEstDevError=0;
       stbDevTemp=new StringBuffer();
       stbDevInvTemp=new StringBuffer();
       stbDocRelEmpTemp=new StringBuffer();

      if(realizaComVenAutomatico(conn, strSql ) ){
           Glo_dblCanFalCom=dblCabFalComAut;

           stbDev.append( stbDevTemp.toString() );
           stbDevInv.append( stbDevInvTemp.toString()  );

            if(intDocRelEmp==1) stbDocRelEmp.append(" UNION ALL ");
              stbDocRelEmp.append( stbDocRelEmpTemp.toString() );
            intDocRelEmp=1;

           intEstDev=1;

           System.out.println(" ComVenAut Oki " );

      }
      else{ conn.rollback(); if(CONN_GLO_REM!=null) CONN_GLO_REM.rollback();  intEstDevError=1;  System.out.println(" ..............ROLLBACK....Com Automatica............"); }


          if(intEstDevError==1){
           if(intEstDev==1){
            stmLoc03.executeUpdate( stbDev.toString() );
            stmLoc03.executeUpdate( stbDevInv.toString() );
           }
           intEstDevError=0;
          }

       
       stbDevTemp=null;
       stbDevInvTemp=null;
       stbDocRelEmpTemp=null;

   }
/**************************************************/



       if(Glo_dblCanFalCom > 0){   
        // if( rstLoc.getString("Term").equals("N")){


           //System.out.println("--> "+Glo_dblCanFalCom );

         java.util.Vector vecReg2 = new java.util.Vector();
          vecReg2.add(INT_TBL_LINEA,"");
          vecReg2.add( INT_TBL_CODITM, rstLoc.getString("co_itm") );
          vecReg2.add( INT_TBL_CODEMP, "" );
          vecReg2.add( INT_TBL_CODBOD, "" );
          vecReg2.add( INT_TBL_NOMEMP, rstLoc.getString("tx_codalt") );
          vecReg2.add( INT_TBL_NOMBOD, rstLoc.getString("tx_nomitm") );
          vecReg2.add( INT_TBL_STKACT,"" );
          vecReg2.add( INT_TBL_ESTBOD,"" );
          vecReg2.add( INT_TBL_CANCOM,  ""+Glo_dblCanFalCom );
          vecReg2.add( INT_TBL_CODALT, rstLoc.getString("tx_codalt") );
          vecReg2.add( INT_TBL_NOMITM, rstLoc.getString("tx_nomitm") );
          vecReg2.add( INT_TBL_UNIMED, rstLoc.getString("tx_descor") );
          vecReg2.add( INT_TBL_COSUNI, rstLoc.getString("nd_cosuni") );

          vecReg2.add( INT_TBL_DATCANCOM ,""+Glo_dblCanFalCom );
       
         vecData.add(vecReg2);    
          
       strSql="SELECT a.co_emp, a.co_loc, a.co_empper, a2.tx_nom, a.co_bodper, a3.tx_nom as nombod, a.st_reg " +
       ",( " +
       " SELECT nd_stkact FROM tbm_invbod AS x " +
       " INNER JOIN tbm_equinv AS x1 ON(x1.co_emp=x.co_emp and x1.co_itm=x.co_itm ) " +
       " INNER JOIN tbm_equinv AS x2 ON(x2.co_emp=x1.co_emp and x2.co_itm=x1.co_itm) " +
       " WHERE x.co_emp=a.co_empper " +
       " AND  x.co_bod=a.co_bodper  AND  x2.co_itmmae = "+rstLoc.getString("co_itmmae")+" " +
       " ) as stk " +
       " FROM tbr_bodemp as a " +
       " inner join tbm_emp as a2 ON (a2.co_emp=a.co_empper) " +
       " inner join tbm_bod as a3 ON (a3.co_emp=a.co_empper and a3.co_bod=a.co_bodper) " +
       " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" order by a.co_empper, a.co_bodper  ";
       rstLoc01=stmLoc01.executeQuery(strSql);
       while(rstLoc01.next()){ 
           
          java.util.Vector vecReg = new java.util.Vector();
          vecReg.add(INT_TBL_LINEA,"");
          vecReg.add( INT_TBL_CODITM, rstLoc.getString("co_itm") );
          vecReg.add( INT_TBL_CODEMP, rstLoc01.getString("co_empper") );
          vecReg.add( INT_TBL_CODBOD, rstLoc01.getString("co_bodper") );
          vecReg.add( INT_TBL_NOMEMP, rstLoc01.getString("tx_nom") );
          vecReg.add( INT_TBL_NOMBOD, rstLoc01.getString("nombod") );
          vecReg.add( INT_TBL_STKACT, rstLoc01.getString("stk") );
          vecReg.add( INT_TBL_ESTBOD, rstLoc01.getString("st_reg") );
          vecReg.add( INT_TBL_CANCOM,"" );
          vecReg.add( INT_TBL_CODALT, rstLoc.getString("tx_codalt") );
          vecReg.add( INT_TBL_NOMITM, rstLoc.getString("tx_nomitm") );
          vecReg.add( INT_TBL_UNIMED, rstLoc.getString("tx_descor") );
          vecReg.add( INT_TBL_COSUNI, rstLoc.getString("nd_cosuni") );

          vecReg.add( INT_TBL_DATCANCOM ,"");
          
         vecData.add(vecReg); 
       }
       rstLoc01.close();
       rstLoc01=null;
       
       
         vecReg2 = new java.util.Vector();
          vecReg2.add(INT_TBL_LINEA,"");
          vecReg2.add( INT_TBL_CODITM, "" );
          vecReg2.add( INT_TBL_CODEMP, "0" );
          vecReg2.add( INT_TBL_CODBOD, "" );
          vecReg2.add( INT_TBL_NOMEMP, "" );
          vecReg2.add( INT_TBL_NOMBOD, "" );
          vecReg2.add( INT_TBL_STKACT,"" );
          vecReg2.add( INT_TBL_ESTBOD,"" );
          vecReg2.add( INT_TBL_CANCOM,"" );
          vecReg2.add( INT_TBL_CODALT, "" );
          vecReg2.add( INT_TBL_NOMITM, "" );
          vecReg2.add( INT_TBL_UNIMED, "" );
          vecReg2.add( INT_TBL_COSUNI, "" );

          vecReg2.add( INT_TBL_DATCANCOM ,"");

         vecData.add(vecReg2);    
       
         intEstReaOcFac=1;
        } //}

      
           
      }  
      rstLoc.close();
      rstLoc=null;

       
       stbDev=null;
       stbDevInv=null;
       stbDatCot=null;
       
      objTblMod.setData(vecData);
      tblDat .setModel(objTblMod);         
        

        
      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;
      stmLoc02.close();
      stmLoc02=null;
      stmLoc03.close();
      stmLoc03=null;
   
     lblMsgSis.setText("Listo");
     pgrSis.setValue(0);
     pgrSis.setIndeterminate(false);    
     
      objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
     
    blnRes=true;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;          
}   
    











double dblCabFalComAut=0.00;

private boolean realizaComVenAutomatico(java.sql.Connection conn, String strSqlDat  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc, stmLocDes;
  java.sql.ResultSet rstLocAut, rstLocDes;
  String strCodAlt="", strNomItm="", strUniMed="";
  int intCodItm=0;
  String strSql="";
  double dblPreVta=0;
  double dblCosUni=0;
  double dblDesVta=35;
  String strCodEmp="";
  int intCodBod=0;
  int intNumFila=1;
  int intCodItmCom=0;
  double dlbCanComAut=0, dlbCosItm=0;
  try{
     if(conn!=null){
      stmLoc=conn.createStatement();
      stmLocDes=conn.createStatement();


      rstLocAut=stmLoc.executeQuery(strSqlDat);
      while(rstLocAut.next()){

         if( dblCabFalComAut > 0 ){

             strCodEmp= rstLocAut.getString("co_empper");
             intCodBod=rstLocAut.getInt("co_bodper");
             intCodItm=rstLocAut.getInt("coitmemp");
             dblPreVta=rstLocAut.getDouble("nd_prevta");
             strCodAlt=rstLocAut.getString("tx_codalt");

             strNomItm=rstLocAut.getString("tx_nomitm");
             strUniMed=rstLocAut.getString("tx_descor");
             dlbCosItm=rstLocAut.getDouble("nd_cosuni");

             intCodItmCom=rstLocAut.getInt("coitmCom");

             

             if(rstLocAut.getDouble("stk") >= dblCabFalComAut ){

                  dlbCanComAut=dblCabFalComAut;
                  dblCabFalComAut=dblCabFalComAut-dlbCanComAut;
               
             }else{

                 dlbCanComAut= rstLocAut.getDouble("stk");
                 dblCabFalComAut=dblCabFalComAut-dlbCanComAut;

             }

             
              dblCabFalComAut=objUti.redondeo( dblCabFalComAut ,4 );

              ///System.out.println("dblCabFalComAut--> "+dblCabFalComAut );


                  if(objZafParSis.getCodigoEmpresa()==1){
                   if(strCodEmp.equals("2")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=2854";
                   if(strCodEmp.equals("4")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=3117";
                  }
                  if(objZafParSis.getCodigoEmpresa()==2){
                   if(strCodEmp.equals("1")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=603";
                   if(strCodEmp.equals("4")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=498";
                  }
                  if(objZafParSis.getCodigoEmpresa()==4){
                   if(strCodEmp.equals("2")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=789";
                   if(strCodEmp.equals("1")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=1039";
                  }
                  rstLocDes = stmLocDes.executeQuery(strSql);
                  if(rstLocDes.next())
                     dblDesVta=rstLocDes.getDouble("nd_maxDes");
                  rstLocDes.close();
                  rstLocDes=null;


                  if(dblPreVta > 0 ){
                   dblCosUni  =  dblPreVta - ( dblPreVta * ( dblDesVta / 100 ) );
                   System.out.println("\n\n\n\n\n\n Gen Ven Aut");
                   if(generaVenAut(conn, Integer.parseInt(strCodEmp), intCodBod, intNumFila, dlbCanComAut, dblDesVta, intCodItm, dblPreVta, strCodAlt, strNomItm, strUniMed, dlbCosItm )){
                    System.out.println("Gen Com Aut");
                     if(generaComAut(conn, Integer.parseInt(strCodEmp), objZafParSis.getCodigoEmpresa(), intCodBodPre, intNumFila, dlbCanComAut, intCodBod, dblCosUni, intCodItmCom, strCodAlt, strNomItm, strUniMed )){
                         System.out.println("OKIII--Aut");
                         blnRes=true;
                     }else { blnRes=false;  break; }
                    }else { blnRes=false;  break; }

                  }else{   blnRes=false;  break; }



         }
      }
      rstLocAut.close();
      rstLocAut=null;

     stmLocDes.close();
     stmLocDes=null;

    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}



private boolean generaVenAut(java.sql.Connection conn, int intCodEmp, int intCodBod, int intNumFil, double dblCan, double intDesVta, int intCodItm, double dblPreVta, String strCodAlt,String strNomItm, String strUniMed, double dlbCosItm ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intCodLoc=0;
  int intCodTipDoc=0;
  int intCodDoc=0;
  String strEstConf="C";
   try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      strSql="SELECT co_locven as co_loc, co_tipdocven FROM tbr_bodemp WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND" +
      " co_loc="+objZafParSis.getCodigoLocal()+" AND co_empper="+intCodEmp+" AND co_bodper="+intCodBod;
      
      //System.out.println("-->" + strSql  );

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


      String strMerIngEgr="E";
      String strIngEgrFisBod="N";
      if(verificaIngEgrFisBod(intCodEmp, intCodBod)){
        strMerIngEgr="S";
        strIngEgrFisBod="S";
      }


 /*************************************************************************/


      if(generaVenCabAut(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmp, 1, dlbSub, dlbValIva, dlbTot, strEstConf   )){
       if(generaVenDetAut(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBod ,intNumFil,  dblCan, intCodItm, -1 , intDesVta, dblPreVta, strIngEgrFisBod, strCodAlt, strNomItm, strUniMed, dlbCosItm )){
        if(actualizaStockConVenAut(CONN_GLO, CONN_GLO_REM, intCodEmp, intCodBod , dblCan, -1, intCodItm, strMerIngEgr, "E" )){
           blnRes=true;
      }}}
      objInvItm.limpiarObjeto();
    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}

private boolean generaVenCabAut(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpCom,  int TipMov, double dlbSub, double dlbValIva, double dlbTot, String strEstConf  ){
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


      datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
      strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());

     if(TipMov==1){
       if(objZafParSis.getCodigoEmpresa()==1){
          if(intCodEmp==2) { strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=2854";  }
          if(intCodEmp==4) { strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=3117";  }
       }
       if(objZafParSis.getCodigoEmpresa()==2){
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
       if(objZafParSis.getCodigoEmpresa()==4){
          if(intCodEmp==2){ strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=789";  }
          if(intCodEmp==1){ strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=1039";  }
        }
    }
    if(TipMov==2){
       if(objZafParSis.getCodigoEmpresa()==1){
          if(intCodEmpCom==2){
              if(intCodTipDoc==130){
                  strCui="MANTA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
                  " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_cli=603";

              }else if(intCodTipDoc==165){
                  strCui="SANTO DOMINGO";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
                  " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_cli=603";
              }
              else if(intCodTipDoc==224){
                  strCui="CUENCA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=4 ) " +
                  " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_cli=603";
              }
              else{
                  strCui="QUITO ";
                  strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=603";
              }

          }
          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=1039";
       }
       if(objZafParSis.getCodigoEmpresa()==2){
          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=2854";
          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=789";
       }
       if(objZafParSis.getCodigoEmpresa()==4){
          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=3117";
          if(intCodEmpCom==2){

              if(intCodTipDoc==130){
                  strCui="MANTA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
                  " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_cli=498";

              }else if(intCodTipDoc==165){
                  strCui="SANTO DOMINGO";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
                  " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_cli=498";
              }
              else if(intCodTipDoc==224){
                  strCui="CUENCA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=4 ) " +
                  " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_cli=498";
              }
              else{
                  strCui="QUITO ";
                  strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=498";
              }


          }

       }
    }
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){

          intNumDoc=getUltNumDoc(conn, intCodEmp, intCodLoc, intCodTipDoc);

     if(intDocRelEmpTemp==1) stbDocRelEmpTemp.append(" UNION ALL ");
         stbDocRelEmpTemp.append( " SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC" );
      intDocRelEmpTemp=1;

          strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
          " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, " +
          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
          " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv ) " +
          " VALUES("+intCodEmp+", "+intCodLoc+", "+
          intCodTipDoc+", "+intCodDoc+", '"+datFecAux+"', "+rstLoc.getString("co_cli")+" ,null,'','"+
          rstLoc.getString("tx_nom")+"','"+rstLoc.getString("tx_dir")+"','"+rstLoc.getString("tx_ide")+ "','"+rstLoc.getString("tx_tel")+"'," +
          "'"+strCui+"','', "+intNumDoc+" , 0,"+
          " '' ,'REALIZADO AUTOMATICAMENTE' , "+dlbSub+" ,0 , "+dlbTot+", "+dlbValIva+" , 1 ,'Contado', '"+strFecSistema+"', "+
          objZafParSis.getCodigoUsuario()+", '"+strFecSistema+"', "+objZafParSis.getCodigoUsuario()+" , null, null," +
          " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', null "+
          " ,'S', 'S', '"+strEstConf+"' )";

          strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDoc+" WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc;

          //System.out.println("-->"+strSql );
          
          stmLoc.executeUpdate(strSql);
          stbDevTemp.append( strSql +" ; " );

      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}

private boolean generaVenDetAut(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intNumFil, double dblCan, int intCodItm, int intTipMov , double intDesVta, double dblPreVta, String strIngEgrFisBod, String strCodAlt, String strNomItm, String strUniMed, double dlbCosItm ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  //String strPreCos="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

       //strPreCos = ((tblDat.getValueAt(intNumFil, INT_TBL_PREVTA)==null)?"0":(tblDat.getValueAt(intNumFil, INT_TBL_PREVTA).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_PREVTA).toString()));
       //double dblPreVta = objUti.redondear(strPreCos, 2);
       //**********strPreCos = ((tblDat.getValueAt(intNumFil, INT_TBL_COSUNI)==null)?"0":(tblDat.getValueAt(intNumFil, INT_TBL_COSUNI).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_COSUNI).toString()));
       double dblCosUni = objUti.redondear(dlbCosItm, 2);

       double dblValDes = ((dblCan * dblPreVta)==0)?0:((dblCan * dblPreVta) * (intDesVta / 100));
       double dblTotal = (dblCan * dblPreVta)- dblValDes;
              dblTotal =  objUti.redondear(dblTotal,2);

        strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+(intNumFil+1)+", " +
        " '"+strCodAlt+"' "+
        " ,'"+strCodAlt+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        ", '"+strNomItm+"' "+
        ", '"+strUniMed+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+(dblCan*dblCosUni)+","+dblPreVta+", "+intDesVta+", 'S' "+
        " ,"+(dblCan*dblCosUni)+", 'I' , '"+strIngEgrFisBod+"', 0 ,"+dblCosUni+" ) ";
        stmLoc.executeUpdate(strSql);

        stbDevTemp.append( strSql +" ; " );

        strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_sal=nd_sal-"+dblCan+" WHERE co_emp="+intCodEmp+" AND " +
        " co_itm="+intCodItm+" AND co_emprel="+objZafParSis.getCodigoEmpresa();
        stmLoc.executeUpdate(strSql);

        stbDevTemp.append( strSql +" ; " );

//         if(intDatVen==1) stbDatVen.append(" UNION ALL ");
//          stbDatVen.append("SELECT "+intCodEmp+" as coemp, "+intCodLoc+" as coloc, "+intCodTipDoc+"  as cotipdoc, "+intCodDoc+" AS codoc, "+intCodBod+" as cobod  ");
//         intDatVen=1;

      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}




private boolean generaComAut(java.sql.Connection conn, int intCodEmpCom, int intCodEmp, int intCodBod, int intNumFil, double dblCan, int intCodBodVen, double dblCosUni, int intCodItmCom , String strCodAlt, String strNomItm, String strUniMed ){
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

      intCodLoc=objZafParSis.getCodigoLocal();


      strSql="SELECT co_loccom as co_loc, co_tipdoccom FROM tbr_bodemp WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND" +
      " co_loc="+objZafParSis.getCodigoLocal()+" AND co_empper="+intCodEmpCom+" AND co_bodper="+intCodBodVen;
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

      intCodItm=intCodItmCom; //Integer.parseInt( objInvItm.getIntDatoValidado(tblDat.getValueAt(intNumFil, INT_TBL_CODITM)) );
      objInvItm.limpiarObjeto();


      double dblPorDes=0;
      double dblValDes = ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
      double dblTotal = (dblCan * dblCosUni)- dblValDes;
      double dlbSub =  objUti.redondear(dblTotal,2);
      double dlbValIva=  dlbSub * (bldivaEmp/100);
      double dlbTot=  dlbSub + dlbValIva;

      String strMerIngEgr="E";
      String strIngEgrFisBod="N";
      if(verificaIngEgrFisBod(intCodEmpCom, intCodBodVen)){
        strMerIngEgr="S";
        strIngEgrFisBod="S";
      }


      if(generaVenCabAut(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmpCom , 2, dlbSub, dlbValIva, dlbTot, strEstConf )){
       if(generaComDetAut(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBod ,intNumFil,  dblCan, intCodItm, 1, intCodEmpCom, dblCosUni, strIngEgrFisBod , strCodAlt, strNomItm, strUniMed )){
        if(actualizaStockConVenAut(CONN_GLO, CONN_GLO_REM, intCodEmp, intCodBod , dblCan, 1, intCodItm , strMerIngEgr, "I" )){
           blnRes=true;
      }}}
      objInvItm.limpiarObjeto();
    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}

private boolean generaComDetAut(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intNumFil, double dblCan, int intCodItm, int intTipMov, int intCodEmpCom, double dblCosUni, String strIngEgrFisBod, String strCodAlt, String strNomItm, String strUniMed ){
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
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+(intNumFil+1)+", " +
        " '"+strCodAlt+"' "+
        " ,'"+strCodAlt+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        ", '"+strNomItm+"' "+
        ", '"+strUniMed+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+dblTotal+","+dblCosUni+", "+dblPorDes+", 'S' "+
        " ,"+dblTotal+", 'I' , '"+strIngEgrFisBod+"', 0 ,"+dblCosUni+" ) ";
        stmLoc.executeUpdate(strSql);

        stbDevTemp.append( strSql +" ; " );

//        if(intDatVen==1) stbDatVen.append(" UNION ALL ");
//          stbDatVen.append("SELECT "+intCodEmp+" as coemp, "+intCodLoc+" as coloc, "+intCodTipDoc+"  as cotipdoc, "+intCodDoc+" AS codoc, "+intCodBod+" as cobod  ");
//        intDatVen=1;

        strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_sal=nd_sal+"+dblCan+" WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND " +
        " co_itm="+intCodItm+" AND co_emprel="+intCodEmpCom;
        stmLoc.executeUpdate(strSql);

        stbDevTemp.append( strSql +" ; " );
        
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}
















     
                 
    
private boolean verificaDev(java.sql.Connection conn, String strCodItm, double dblCanFal, int intCodEmp  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 double dlbStkBobDevCom=0;
 try{
  if(conn!=null){
    stmLoc=conn.createStatement();

               
    strSql="SELECT a.co_emp, a3.co_empper, a.co_itm, a.co_itmrel, a3.co_loc, a3.co_locdevcom, a3.co_tipdocdevcom, a3.co_locdevven, a3.co_tipdocdevven, a2.nd_stkact, a.nd_sal "+
    " ,a2.co_bod FROM tbm_invmovempgrp as a " +
    " inner join tbm_inv as a1 on (a1.co_emp=a.co_emprel and a1.co_itm=a.co_itmrel) " +
    " inner join tbr_bodemp as a3 on (a3.co_emp=a.co_emp and a3.co_loc="+objZafParSis.getCodigoLocal()+" and a3.co_empper=a.co_emprel  and a3.st_reg='A') " +
    " inner join tbm_invbod as a2 on (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm and a2.co_bod=a3.co_bodper) " +
    " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_itm="+strCodItm+" AND a.nd_sal < 0   AND  a2.nd_stkact > 0 "+
    " AND  a3.co_empper="+intCodEmp+"  ";
    //System.out.println("Paso 1 Dev : "+ strSql );
    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){

        Glo_dblCanTotDevCom=0;
        dlbStkBobDevCom=rstLoc.getDouble("nd_stkact");
        
       if( Glo_dblCanFalDevCom > 0 ){

   
        stbDatDevCom=new StringBuffer();
        stbDatDevVen=new StringBuffer();
        intDatDevCom=0;
        intDatDevVen=0;

           _getMovDevCom(conn, rstLoc.getInt("co_emp"),  rstLoc.getInt("co_empper"), rstLoc.getInt("co_locdevcom"), rstLoc.getInt("co_tipdocdevcom"), rstLoc.getInt("co_itmrel"), dblCanFal,  rstLoc.getInt("co_bod"), dlbStkBobDevCom, rstLoc.getInt("co_tipdocdevven"), strCodItm );
           _getMovDevVen(conn, rstLoc.getInt("co_emp"),  rstLoc.getInt("co_locdevven"), rstLoc.getInt("co_tipdocdevven"),  rstLoc.getInt("co_itm"), rstLoc.getInt("co_empper"),   dblCanFal, dlbStkBobDevCom , rstLoc.getInt("co_bod")   );

     
          if( (!stbDatDevCom.toString().equals(""))  && (!stbDatDevVen.toString().equals("")) ){
           // System.out.println(""+stbDatDevCom.toString() );
           // System.out.println(""+stbDatDevVen.toString() );
            
              if(_RealizarDevol(conn, rstLoc.getInt("co_locdevcom"), rstLoc.getInt("co_locdevven"), rstLoc.getInt("co_tipdocdevcom"), rstLoc.getInt("co_tipdocdevven"), rstLoc.getInt("co_bod"), dlbStkBobDevCom, strCodItm ) ){
                blnRes=true;
              }
        

          }

        stbDatDevCom=null;
        stbDatDevVen=null;


//          if(verificaMovDevCom(conn, rstLoc.getInt("co_emp"),  rstLoc.getInt("co_empper"), rstLoc.getInt("co_locdevcom"), rstLoc.getInt("co_tipdocdevcom"), rstLoc.getInt("co_itmrel"), dblCanFal,  rstLoc.getInt("co_bod"), dlbStkBobDevCom, rstLoc.getInt("co_tipdocdevven"), strCodItm )){
//          if(verificaMovDevVen(conn, rstLoc.getInt("co_emp"),  rstLoc.getInt("co_locdevven"), rstLoc.getInt("co_tipdocdevven"),  rstLoc.getInt("co_itm"), rstLoc.getInt("co_empper"),   dblCanFal, dlbStkBobDevCom , rstLoc.getInt("co_bod")   )){
//
//             blnRes=true;
//
//       }}




     }
    }
    rstLoc.close();
    rstLoc=null;

    stmLoc.close();
    stmLoc=null;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }  
 return blnRes;       
}
        



private boolean _RealizarDevol(java.sql.Connection conn,  int intCodLocDevCom , int intCodLocDevVen, int intCodTipDocDevCom, int intCodTipDocDevVen, int intCodBodDevCom, double dlbStkBobDevCom,  String strCodItm  ){
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
    //System.out.println("Paso 2  : "+ strSql );
    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
       if(Glo_dblCanFalDevCom > 0 ){
        if( dlbStkBobDevCom > 0 ){

           // System.out.println("Realizando DevCom -->  ");

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

       //---  Glo_dblCanTotDevCom=Glo_dblCanTotDevCom+dlbCanDev;

         StrEstConfDevVen="P";

        int intCodBodDes =  _getCodigoBodDes(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_bod1"), rstLoc.getInt("co_emp1") );
        if(!(intCodBodDes== -1)){


         if(generaDevCom(conn, rstLoc.getInt("co_emp"),  intCodLocDevCom, rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"),  rstLoc.getInt("co_reg")
            , rstLoc.getInt("ne_numdoc"), intCodTipDocDevCom,  rstLoc.getInt("co_bod"), rstLoc.getString("tx_codalt"), rstLoc.getString("tx_codalt2"), rstLoc.getString("tx_nomitm"), rstLoc.getString("tx_unimed"),     dlbCanDev , rstLoc.getDouble("nd_pordes"),  rstLoc.getInt("co_itm"),  rstLoc.getDouble("nd_cosuni"),  rstLoc.getDouble("nd_can") , strCodItm, rstLoc.getInt("co_loc")  )){

             
          if(generaDevVen(conn, rstLoc.getInt("co_emp"),  rstLoc.getInt("co_emp1"),  intCodLocDevVen, rstLoc.getInt("co_tipdoc1"), rstLoc.getInt("co_doc1"),  rstLoc.getInt("co_reg1")
            , rstLoc.getInt("ne_numdoc1"), intCodTipDocDevVen,  rstLoc.getInt("co_bod1"), rstLoc.getString("tx_codalt1"), rstLoc.getString("tx_codalt21"), rstLoc.getString("tx_nomitm1"), rstLoc.getString("tx_unimed1"),   dlbCanDev  , rstLoc.getDouble("nd_pordes1"),  rstLoc.getInt("co_itm1"),  rstLoc.getDouble("nd_preuni1"), rstLoc.getDouble("nd_can"), intCodBodDevCom , rstLoc.getInt("co_loc1") )){
              
             blnRes=true;
             Glo_dblCanFalDevVen = Glo_dblCanFalDevCom;

         }}

       }else{  MensajeInf("PROBLEMA AL OBTENER BODEGA DESTINO.. ");  blnRes=false;  break; }


       }}
    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

private boolean _getMovDevCom(java.sql.Connection conn,  int intCodEmpDev, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodItm,  double dblCanFal, int intCodBodDevCom, double dlbStkBobDevCom, int intTipDocDevVen, String strCodItm  ){
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

  String strAuxDirCli="";
  if(objZafParSis.getCodigoEmpresa()==2){
   if(objZafParSis.getCodigoLocal()==1){
      strAuxDirCli=" AND  upper(a.tx_dircli) like '%MAR%' ";
  }if(objZafParSis.getCodigoLocal()==4){
     strAuxDirCli=" AND  upper(a.tx_dircli) like '%BUE%' ";
  }}


  String strAuxBod="";
  if(intCodEmp==2){
   if(intTipDocDevVen==134) 
       strAuxBod=" AND a1.co_bod IN ( 4 ) ";
   else 
       strAuxBod=" AND a1.co_bod NOT IN ( 4 ) ";
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
    " WHERE a.co_emp="+intCodEmp+"   "+strAuxLoc+" "+
    " AND a.co_tipdoc="+intTipDocCom+" AND a.co_cli="+intCodCli+" " +
    " AND a.fe_doc>='2008-01-01' and a.st_reg not in ('E','I') and a1.co_itm="+intCodItm+"   " +
    " and a1.co_bod= "+intCodBodDevCom+"   "+strAuxDirCli+"  "+strAuxBod+"  ORDER BY a.fe_Doc, a.co_doc " +
    " ) as x  where saldo <> 0 ";
    ///System.out.println("Paso 2 verificaMovDevCom : "+ strSql );
    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
     intRegSec++;
     if(intDatDevCom==1) stbDatDevCom.append(" UNION ALL ");
     stbDatDevCom.append(" SELECT "+intRegSec+" AS regsec,  "+rstLoc.getInt("co_emp")+" AS co_emp, "+rstLoc.getInt("co_loc")+" AS co_loc, " +
     " "+rstLoc.getInt("co_tipdoc")+" AS co_tipdoc, "+rstLoc.getInt("co_doc")+" AS co_doc, "+rstLoc.getInt("co_reg")+" AS co_reg," +
     " "+rstLoc.getInt("co_bod")+" AS co_bod, "+rstLoc.getDouble("saldo")+" AS saldo, " +
     " "+rstLoc.getInt("ne_numdoc")+" AS ne_numdoc, '"+rstLoc.getString("tx_codalt")+"' AS tx_codalt, '"+rstLoc.getString("tx_codalt2")+"' AS tx_codalt2," +
     " '"+rstLoc.getString("tx_nomitm")+"' AS tx_nomitm, '"+rstLoc.getString("tx_unimed")+"' AS tx_unimed, "+rstLoc.getString("nd_pordes")+" AS nd_pordes, " +
     " "+rstLoc.getString("co_itm")+" AS co_itm, "+rstLoc.getString("nd_cosuni")+" AS nd_cosuni, "+rstLoc.getString("nd_can")+" AS nd_can,  "+rstLoc.getString("nd_preuni")+" AS nd_preuni  ");

     intDatDevCom=1;
     
    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

private boolean _getMovDevVen(java.sql.Connection conn, int intCodEmp,  int intCodLoc, int  intCodTipDoc, int intCodItm, int intCodEmpRel, double dblCanFal, double dlbStkBobDevCom, int intCodBodDevCom ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 int intTipDocVen=1;
 int intCodCli=0;
 int intRegSec=0;
 try{
  if(conn!=null){
    stmLoc=conn.createStatement();

      if(objZafParSis.getCodigoEmpresa()==1){
          if(intCodEmpRel==2) intCodCli=603;
          if(intCodEmpRel==4) intCodCli=1039;
       }
       if(objZafParSis.getCodigoEmpresa()==2){
          if(intCodEmpRel==1) intCodCli=2854;
          if(intCodEmpRel==4) intCodCli=789;
       }
       if(objZafParSis.getCodigoEmpresa()==4){
          if(intCodEmpRel==2) intCodCli=498;
          if(intCodEmpRel==1) intCodCli=3117;
       }


  String strAuxBod="";
  if(objZafParSis.getCodigoEmpresa()==2){
   if(objZafParSis.getCodigoLocal()==1){
      strAuxBod=" AND a1.co_bod NOT IN ( 4 ) ";
  }if(objZafParSis.getCodigoLocal()==4){
     strAuxBod="  AND a1.co_bod IN ( 4 ) ";
  }}


  String strAuxDirCli="";
  if(intCodEmpRel==2){
   if(intCodTipDoc==134)
      strAuxDirCli=" AND  upper(a.tx_dircli) like '%BUE%' ";
   else
     strAuxDirCli=" AND  upper(a.tx_dircli) like '%MAR%' ";
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
    " WHERE a.co_emp="+intCodEmp+"    "+strAuxLoc+" "+  
    " AND a.co_tipdoc="+intTipDocVen+" AND a.co_cli="+intCodCli+" " +
    " AND a.fe_doc>='2008-01-01' and a.st_reg not in ('E','I') and a1.co_itm="+intCodItm+"  "+strAuxBod+"  "+strAuxDirCli+"  ORDER BY a.fe_Doc, a.co_doc " +
    " ) as x  where saldo <> 0 ";
    //System.out.println("Paso 2 verificaMovDevVen : "+ strSql );
    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
     intRegSec++;
     if(intDatDevVen==1) stbDatDevVen.append(" UNION ALL ");
     stbDatDevVen.append(" SELECT "+intRegSec+" AS regsec1,  "+rstLoc.getInt("co_emp")+" AS co_emp1, "+rstLoc.getInt("co_loc")+" AS co_loc1, " +
     " "+rstLoc.getInt("co_tipdoc")+" AS co_tipdoc1, "+rstLoc.getInt("co_doc")+" AS co_doc1, "+rstLoc.getInt("co_reg")+" AS co_reg1," +
     " "+rstLoc.getInt("co_bod")+" AS co_bod1, "+rstLoc.getDouble("saldo")+" AS saldo1, " +
     " "+rstLoc.getInt("ne_numdoc")+" AS ne_numdoc1, '"+rstLoc.getString("tx_codalt")+"' AS tx_codalt1, '"+rstLoc.getString("tx_codalt2")+"' AS tx_codalt21," +
     " '"+rstLoc.getString("tx_nomitm")+"' AS tx_nomitm1, '"+rstLoc.getString("tx_unimed")+"' AS tx_unimed1, "+rstLoc.getString("nd_pordes")+" AS nd_pordes1, " +
     " "+rstLoc.getString("co_itm")+" AS co_itm1, "+rstLoc.getString("nd_cosuni")+" AS nd_cosuni1, "+rstLoc.getString("nd_can")+" AS nd_can1,  "+rstLoc.getString("nd_preuni")+" AS nd_preuni1   ");
        
     intDatDevVen=1;

    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}




  
  

private boolean verificaMovDevVen(java.sql.Connection conn, int intCodEmp,  int intCodLoc, int  intCodTipDoc, int intCodItm, int intCodEmpRel, double dblCanFal, double dlbStkBobDevCom, int intCodBodDevCom ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 int intTipDocVen=1;
 int intCodCli=0; 
 double dlbCanDev=0;
 try{
  if(conn!=null){
    stmLoc=conn.createStatement();
    
      if(objZafParSis.getCodigoEmpresa()==1){
          if(intCodEmpRel==2) intCodCli=603;  
          if(intCodEmpRel==4) intCodCli=1039;  
       }
       if(objZafParSis.getCodigoEmpresa()==2){
          if(intCodEmpRel==1) intCodCli=2854; 
          if(intCodEmpRel==4) intCodCli=789;
       }
       if(objZafParSis.getCodigoEmpresa()==4){
          if(intCodEmpRel==2) intCodCli=498;
          if(intCodEmpRel==1) intCodCli=3117;  
       }
  
    
  String strAuxBod="";
  if(objZafParSis.getCodigoEmpresa()==2){
   if(objZafParSis.getCodigoLocal()==1){
      strAuxBod=" AND a1.co_bod NOT IN ( 4 ) ";
  }if(objZafParSis.getCodigoLocal()==4){
     strAuxBod="  AND a1.co_bod IN ( 4 ) ";
  }}
        
    
  String strAuxDirCli="";
  if(intCodEmpRel==2){
   if(intCodTipDoc==134)
      strAuxDirCli=" AND  upper(a.tx_dircli) like '%BUE%' ";
   else
     strAuxDirCli=" AND  upper(a.tx_dircli) like '%MAR%' ";
  }
  
   String strAuxLoc="";
//   if(intCodEmp==4)
//       strAuxLoc="";
//   else
//     strAuxLoc=" AND a.co_loc="+intCodLoc+" ";
        
    strSql="select * FROM ( " +
    " SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.co_reg,  a1.nd_pordes, a1.tx_codalt, a1.tx_codalt2, a1.tx_nomitm, a1.tx_unimed, " +
    " a.ne_numdoc, a.co_cli, a1.co_bod, a1.nd_cosuni, a1.nd_preuni, a1.co_itm, a1.tx_codalt, abs(a1.nd_can) as nd_can, a1.nd_candev " +
    " ,( abs(a1.nd_can) - abs( case when a1.nd_candev is null then 0 else a1.nd_candev end ) ) as saldo " +
    " FROM tbm_cabmovinv  AS a " +
    " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) " +
    " WHERE a.co_emp="+intCodEmp+"    "+strAuxLoc+" "+  //    AND a.co_loc="+intCodLoc+" "+
    " AND a.co_tipdoc="+intTipDocVen+" AND a.co_cli="+intCodCli+" " +
    " AND a.fe_doc>='2008-01-01' and a.st_reg not in ('E','I') and a1.co_itm="+intCodItm+"  "+strAuxBod+"  "+strAuxDirCli+"  ORDER BY a.fe_Doc, a.co_doc " +
    " ) as x  where saldo <> 0 ";
    //System.out.println("Paso 2 verificaMovDevVen : "+ strSql );
    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
      // if( rstLoc.getDouble("saldo") >= dblCanFal ){


     if( Glo_dblCanTotDevCom > 0 ){


      if(Glo_dblCanFalDevVen > 0 ){
       if( dlbStkBobDevCom > 0 ){
        if(rstLoc.getDouble("saldo") >= Glo_dblCanFalDevVen ) {

            if( dlbStkBobDevCom <= Glo_dblCanFalDevVen )
                dlbCanDev=dlbStkBobDevCom;
            else
               dlbCanDev = Glo_dblCanFalDevVen;
          
            Glo_dblCanFalDevVen=Glo_dblCanFalDevVen-dlbCanDev;
            dlbStkBobDevCom=dlbStkBobDevCom-dlbCanDev;
        } 
        else{

              if( dlbStkBobDevCom <= rstLoc.getDouble("saldo") )
                 dlbCanDev=dlbStkBobDevCom;
              else
                  dlbCanDev = rstLoc.getDouble("saldo");

              Glo_dblCanFalDevVen=Glo_dblCanFalDevVen-dlbCanDev;
              dlbStkBobDevCom=dlbStkBobDevCom-dlbCanDev;
        } 

        Glo_dblCanTotDevCom=Glo_dblCanTotDevCom-dlbCanDev;


         if(generaDevVen(conn, intCodEmpRel,  rstLoc.getInt("co_emp"),  intCodLoc, rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"),  rstLoc.getInt("co_reg")
            , rstLoc.getInt("ne_numdoc"), intCodTipDoc,  rstLoc.getInt("co_bod"), rstLoc.getString("tx_codalt"), rstLoc.getString("tx_codalt2"), rstLoc.getString("tx_nomitm"), rstLoc.getString("tx_unimed"),   dlbCanDev  , rstLoc.getDouble("nd_pordes"),  rstLoc.getInt("co_itm"),  rstLoc.getDouble("nd_preuni"), rstLoc.getDouble("nd_can"), intCodBodDevCom , rstLoc.getInt("co_loc") )){
              
              blnRes=true;   
       }
       
       
       }

      } }


    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }  
 return blnRes;       
}
  

     

  

private boolean generaDevVen(java.sql.Connection conn, int intCodEmpRel, int intCodEmp, int intCodLoc, int intCodTipDocOc, int intCodDocOc, int intCodRegOc
        , int intNumDoc, int intCodTipDoc, int intCodBod,  String strCodAlt, String strCodAlt2, String strNomItm, String strUniMed,  double dblCan, double dblPorDes, int intCodItm, double dblCosUni, double dblCanFac, int intCodBodDevCom, int intCodLocDev ){
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

         
      String strMerIngEgr="E";
      //if(verificaIngEgrFisBod(intCodEmpRel, intCodBodDevCom))
      if(getVerIngFisBodDevVen(conn, intCodEmpRel, intCodBodDevCom, intCodEmp, intCodBod ) ){
        strMerIngEgr="S";
        strConfDevVen="P";
      }

      if(StrEstConfDevVen.equals("F")) {
          strMerIngEgr="E";
          strConfDevVen="F";
      }



      if(generaDevVenCab(conn, intNumDoc, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmpRel, 2, dlbSub, dlbValIva, dlbTot, intCodTipDocOc, intCodDocOc, intCodLocDev , strConfDevVen )){
       if(generaDevVenDet(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBod, intCodTipDocOc, intCodDocOc, intCodRegOc, strCodAlt, strCodAlt2, strNomItm, strUniMed, dblCan, intCodItm, 1 ,  intCodEmpRel, dblCosUni, dblPorDes, dblCanFac, intCodLocDev )){
        if(actualizaStockDev(CONN_GLO, CONN_GLO_REM, intCodEmp, intCodBod , dblCan, 1, intCodItm, strMerIngEgr  ,"I" )){
         if(generaTransBodaBod(conn, intCodEmp, intCodItm, intCodBod, dblCan, "S", StrEstConfDevVen )){
            blnRes=true;
      }}}}
    objInvItm.limpiarObjeto();  
    stmLoc.close();
    stmLoc=null;
      
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}           
   


private boolean getVerIngFisBodDevVen(java.sql.Connection conn, int intCodEmpDevCom, int intCodBodDevCom, int intCodEmpDevVen, int intCodBodDevVen ){
   boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      strSql="SELECT grp1, grp2 FROM ( " +
      " SELECT * FROM ( " +
      " select 1 as reg, co_bodgrp as grp1 from tbr_bodempbodgrp where co_emp="+intCodEmpDevCom+" and co_bod="+intCodBodDevCom+" " +
      " ) as x left join ( " +
      "  select 1 as reg, co_bodgrp as grp2 from tbr_bodempbodgrp where co_emp="+intCodEmpDevVen+" and co_bod="+intCodBodDevVen+" " +
      " ) as x1 on (x1.reg=x.reg) ) as x  where grp1=grp2 ";
      //System.out.println("getVerIngFisBodDevVen -->"+ strSql );
      rstLoc = stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
        blnRes=false;
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}



/*****************************************************************************************/

private boolean generaTransBodaBod(java.sql.Connection conn, int intCodEmp, int intCodItm,  int intCodBod, double dblCanBod, String strEstGuia, String strEstConInv ){
  boolean blnRes=true;
  java.sql.ResultSet rstLoc;
  java.sql.Statement stmLoc;
  int intCodLocTra=0;
  int intCodTipDocTra=0;
  String strSql="";
  try{
    if(conn!=null){
       stmLoc=conn.createStatement();

     if(intCodBod != intCodBodPre){
      if(objZafParSis.getCodigoEmpresa()==intCodEmp ){

         // System.out.println("Transferencia...Para Compañias..");
                    
                   strSql="SELECT co_loctra, co_tipdoctra, st_reg FROM tbr_bodemp " +
                   " where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_empper="+objZafParSis.getCodigoEmpresa()+" and st_reg='A' ";
                   rstLoc = stmLoc.executeQuery(strSql);
                   if(rstLoc.next()){
                     intCodLocTra=rstLoc.getInt("co_loctra");
                     intCodTipDocTra=rstLoc.getInt("co_tipdoctra");
                   }
                   rstLoc.close();
                   rstLoc=null;

                    if(!generaTransDev(conn, CONN_GLO, CONN_GLO_REM, objZafParSis.getCodigoEmpresa(), intCodLocTra, intCodTipDocTra, intCodBod, intCodBodPre, String.valueOf(intCodItm), dblCanBod, strEstGuia, strEstConInv  ) ){
                       blnRes=false;  
                    }
      }}
    stmLoc.close();
    stmLoc=null;
}}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}

private boolean generaTransDev(java.sql.Connection conn, java.sql.Connection connLoc, java.sql.Connection connRemota, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodBodOri, int intCodBodDes, String strCodItm, double dblanmov, String strEstGuia, String strEstConInv ){
boolean blnRes=false;
try{
   if(conn != null ){

     if(consultarTansAutDev(conn, connLoc, connRemota, intCodEmp,  intCodLoc, intCodTipDoc, intCodBodOri, intCodBodDes, strCodItm, dblanmov, strEstGuia, strEstConInv )){
         blnRes=true;
     }

}}catch(Exception Evt)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
return blnRes;
}

private boolean consultarTansAutDev(java.sql.Connection conn, java.sql.Connection connLoc, java.sql.Connection connRemota,  int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodBodOri, int intCodBodDes, String strCodItm, double dblanmov, String strEstGuia, String strEstConInv ){
  boolean blnRes=false;
  java.sql.Statement stmLoc, stmLoc01;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String str_MerIEFisBod="N";
  String strMerIngEgr="N";
  String strMerIngEgrGuia="N";
  int intCodDoc=0;
  int intNumDoc=0;
  int intSecEmp=0;
  int intSecGrp=0;
  try{
  if(conn!=null){

     stmLoc=conn.createStatement();
     stmLoc01=conn.createStatement();

     StringBuffer stbTbl1;

     double dblTot=0;
     int intReg=1;
     int intNumFil=1;
     int intControl=0;


     ///System.out.println(" REALIZA TRANSFERENCIA...................... ");

     if(strEstConInv.equals("P")) strMerIngEgr="S";

     if(strEstGuia.equals("N")){
         strMerIngEgrGuia="S";
     }



     dblTot=0;
     intReg=1;
     intNumFil=1;
     stbTbl1=new StringBuffer();
     intControl=0;
     intCodDoc=0;


     objInvItm.limpiarObjeto();
     objInvItm.inicializaObjeto();

     strSql="select a.co_itm, a.tx_codalt, a.tx_nomitm, a1.tx_descor as tx_unimed,  "+intCodBodOri+" as co_bodori, " +
     " "+intCodBodDes+" as co_boddes,  "+dblanmov+" as nd_can, a.nd_cosuni, ( "+dblanmov+" * a.nd_cosuni) as costot " +
     " from tbm_inv as a " +
     " left join  tbm_var as a1 on (a1.co_reg=a.co_uni) " +
     " where  a.co_emp="+intCodEmp+"  and a.co_itm="+strCodItm;
     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next()){

        if(intControl==0)
           intCodDoc=getCodigoDoc(conn, intCodEmp, intCodLoc, intCodTipDoc );

         dblTot += rstLoc.getDouble("costot");

         strSql="INSERT INTO tbm_detmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, " +
         " tx_codalt, tx_nomitm, tx_unimed, co_bod, nd_can, nd_cosuni, nd_preuni, nd_pordes, st_ivacom, " +
         " nd_cancon,  ne_numfil, nd_tot, tx_codalt2, nd_costot, nd_cospro, " +
         " nd_cosunigrp, nd_costotgrp, nd_candev, st_regrep, co_itmact, st_meringegrfisbod, nd_cannunrec ) " +
         " VALUES( "+intCodEmp+","+intCodLoc+","+intCodTipDoc+", "+intCodDoc+", "+intReg+", "+rstLoc.getInt("co_itm")+"," +
         " '"+rstLoc.getString("tx_codalt")+"', '"+rstLoc.getString("tx_nomitm")+"', '"+rstLoc.getString("tx_unimed")+"',"+
         " "+rstLoc.getInt("co_bodori")+", "+rstLoc.getDouble("nd_can")*-1+", "+rstLoc.getString("nd_cosuni")+", "+rstLoc.getString("nd_cosuni")+"," +
         " 0, 'N', 0, "+intNumFil+", "+rstLoc.getDouble("costot")*-1+", '"+rstLoc.getString("tx_codalt")+"', "+rstLoc.getDouble("costot")*-1+", "+rstLoc.getString("nd_cosuni")+"," +
         " "+rstLoc.getString("nd_cosuni")+", "+rstLoc.getDouble("costot")*-1+",  0, 'I', "+rstLoc.getInt("co_itm")+", " +
         " '"+strMerIngEgrGuia+"', 0  ) ; ";
         stbTbl1.append( strSql );
         intReg++;
         str_MerIEFisBod="E";
         objInvItm.generaQueryStock(intCodEmp, Integer.parseInt(strCodItm), rstLoc.getInt("co_bodori"), rstLoc.getDouble("nd_can"),  -1 , "N", strMerIngEgrGuia, str_MerIEFisBod, 1);


         strSql="INSERT INTO tbm_detmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, " +
         " tx_codalt, tx_nomitm, tx_unimed, co_bod, nd_can, nd_cosuni, nd_preuni, nd_pordes, st_ivacom, " +
         " nd_cancon,  ne_numfil, nd_tot, tx_codalt2, nd_costot, nd_cospro, " +
         " nd_cosunigrp, nd_costotgrp, nd_candev, st_regrep, co_itmact, st_meringegrfisbod, nd_cannunrec ) " +
         " VALUES( "+intCodEmp+","+intCodLoc+","+intCodTipDoc+", "+intCodDoc+", "+intReg+", "+rstLoc.getInt("co_itm")+"," +
         " '"+rstLoc.getString("tx_codalt")+"', '"+rstLoc.getString("tx_nomitm")+"', '"+rstLoc.getString("tx_unimed")+"',"+
         " "+rstLoc.getInt("co_boddes")+", "+rstLoc.getDouble("nd_can")+", "+rstLoc.getString("nd_cosuni")+", "+rstLoc.getString("nd_cosuni")+"," +
         " 0, 'N',  0, "+intNumFil+", "+rstLoc.getDouble("costot")+", '"+rstLoc.getString("tx_codalt")+"', "+rstLoc.getDouble("costot")+", "+rstLoc.getString("nd_cosuni")+"," +
         " "+rstLoc.getString("nd_cosuni")+", "+rstLoc.getDouble("costot")+",  0, 'I', "+rstLoc.getInt("co_itm")+", " +
         " '"+strMerIngEgr+"', 0  ) ; ";
         stbTbl1.append( strSql );
         intReg++;
         str_MerIEFisBod="I";
         objInvItm.generaQueryStock(intCodEmp, Integer.parseInt(strCodItm), rstLoc.getInt("co_boddes"), rstLoc.getDouble("nd_can"),  1 , "N", strMerIngEgr, str_MerIEFisBod, 1);



        intNumFil++;
        intControl=1;
     }
     rstLoc.close();
     rstLoc=null;

  


     if(intControl==1){
       intNumDoc=getUltNumDoc(conn, intCodEmp, intCodLoc, intCodTipDoc );
//       intSecEmp=getNumeroOrdenEmpresa(conn, intCodEmp );
//       intSecGrp=getNumeroOrdenGrupo(conn );

     
      if( insertarCabTransDev(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intNumDoc, dblTot ,  intSecEmp, intSecGrp, strEstGuia , strEstConInv ) ){
       if(insertarCabDiaTransDev( conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intNumDoc ) ){
         if(insertarDetDiaTransDev( conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, dblTot, intCodBodOri , intCodBodDes ) ){
            stmLoc.executeUpdate(stbTbl1.toString());
            stbDevTemp.append( stbTbl1.toString() +" ; " );
       }}}
     }
     stbTbl1=null;


      stbDevInvTemp.append( objInvItm.getQueryEjecutaActStock() +" ; " );


      if(!objInvItm.ejecutaActStock(connLoc, 3))
          return false;

       if(connRemota!=null){
           // System.out.println("remoto.."+connRemota );

           if(!objInvItm.ejecutaActStock(connRemota, 3 ))
             return false;
           if(!objInvItm.ejecutaVerificacionStock(connRemota, 3))
             return false;
       }else{
         if(!objInvItm.ejecutaVerificacionStock(connLoc, 3 ))
            return false;
       }
      objInvItm.limpiarObjeto();





     blnRes=true;
     stmLoc.close();
     stmLoc=null;
     stmLoc01.close();
     stmLoc01=null;
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
  return  blnRes;
}
  
private boolean insertarCabTransDev(java.sql.Connection  conIns, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc, double dlbTot , int intSecEmp, int intSecGrp, String strEstGuia, String strEstConInv  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 try{
    if(conIns!=null){
      stmLoc=conIns.createStatement();

    java.util.Date datFecAux =objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());

    if(intDocRelEmpTemp==1) stbDocRelEmpTemp.append(" UNION ALL ");
       stbDocRelEmpTemp.append( " SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC" );
    intDocRelEmpTemp=1;

    strSql="INSERT INTO tbm_cabmovinv(co_emp, co_loc, co_tipdoc, co_doc, ne_numdoc, fe_doc, " +
    " nd_sub, nd_tot, nd_poriva, st_reg, fe_ing , co_usring , " +
    " ne_secemp, ne_secgrp, nd_valiva, co_mnu, st_tipdev, st_coninv, " +
    " st_imp, st_creguirem, st_coninvtraaut,  st_docconmersaldemdebfac , st_regrep   ) " +
    " VALUES ("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intNumDoc+" ,'"+datFecAux+"', " +
    " "+(dlbTot*-1)+", "+(dlbTot*-1)+", 0, 'A', "+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getCodigoUsuario()+", " +
    " "+intSecEmp+", "+intSecGrp+", 0, 779, 'C', '"+strEstConInv+"',  'S', '"+strEstGuia+"', 'N', 'N', 'I' ) ";

    strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDoc+" WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+" ";
    stmLoc.executeUpdate(strSql);
    stbDevTemp.append( strSql +" ; " );

   stmLoc.close();
   stmLoc=null;
   blnRes=true;
}}
catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }

return blnRes;
}

private boolean insertarCabDiaTransDev(java.sql.Connection  conIns, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc ){
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
    stbDevTemp.append( strSql +" ; " );
    
   stmLoc.close();
   stmLoc=null;
   blnRes=true;
}}
catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }

return blnRes;
}

private boolean insertarDetDiaTransDev(java.sql.Connection  conIns, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, double dlbTot, int intBodOri, int intBodDes ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 int intCodReg=1;
 int intCtaOri=0;
 int intCtaDes=0;

 try{
    if(conIns!=null){
      stmLoc=conIns.createStatement();

      if(intCodEmp==1){
        if(intBodOri==1)  intCtaOri=407;
        if(intBodOri==3)  intCtaOri=2434;
        if(intBodOri==4)  intCtaOri=2435;
        if(intBodOri==5)  intCtaOri=2499;
        if(intBodOri==6)  intCtaOri=2500;
        if(intBodOri==7)  intCtaOri=2501;
        if(intBodOri==8)  intCtaOri=2502;
        if(intBodOri==9)  intCtaOri=2503;
        if(intBodOri==10)  intCtaOri=2504;
        if(intBodOri==12)  intCtaOri=2639;

        if(intBodDes==1)  intCtaDes=407;
        if(intBodDes==3)  intCtaDes=2434;
        if(intBodDes==4)  intCtaDes=2435;
        if(intBodDes==5)  intCtaDes=2499;
        if(intBodDes==6)  intCtaDes=2500;
        if(intBodDes==7)  intCtaDes=2501;
        if(intBodDes==8)  intCtaDes=2502;
        if(intBodDes==9)  intCtaDes=2503;
        if(intBodDes==10)  intCtaDes=2504;
        if(intBodDes==12)  intCtaDes=2639;

      }


      if(intCodEmp==2){
        if(intBodOri==1)  intCtaOri=74;
        if(intBodOri==3)  intCtaOri=75;
        if(intBodOri==4)  intCtaOri=1025;
        if(intBodOri==5)  intCtaOri=1092;
        if(intBodOri==6)  intCtaOri=1093;
        if(intBodOri==7)  intCtaOri=1121;
        if(intBodOri==8)  intCtaOri=1122;
        if(intBodOri==9)  intCtaOri=1123;
        if(intBodOri==10)  intCtaOri=1124;
        if(intBodOri==12)  intCtaOri=1160;

        if(intBodDes==1)  intCtaDes=74;
        if(intBodDes==3)  intCtaDes=75;
        if(intBodDes==4)  intCtaDes=1025;
        if(intBodDes==5)  intCtaDes=1092;
        if(intBodDes==6)  intCtaDes=1093;
        if(intBodDes==7)  intCtaDes=1121;
        if(intBodDes==8)  intCtaDes=1122;
        if(intBodDes==9)  intCtaDes=1123;
        if(intBodDes==10)  intCtaDes=1124;
        if(intBodDes==12)  intCtaDes=1160;
      }


      if(intCodEmp==4){
        if(intBodOri==1)  intCtaOri=383;
        if(intBodOri==2)  intCtaOri=381;
        if(intBodOri==3)  intCtaOri=1992;
        if(intBodOri==4)  intCtaOri=1993;
        if(intBodOri==5)  intCtaOri=2031;
        if(intBodOri==6)  intCtaOri=2032;
        if(intBodOri==7)  intCtaOri=2034;
        if(intBodOri==8)  intCtaOri=2035;
        if(intBodOri==9)  intCtaOri=2036;
        if(intBodOri==11)  intCtaOri=2101;

        if(intBodDes==1)  intCtaDes=383;
        if(intBodDes==2)  intCtaDes=381;
        if(intBodDes==3)  intCtaDes=1992;
        if(intBodDes==4)  intCtaDes=1993;
        if(intBodDes==5)  intCtaDes=2031;
        if(intBodDes==6)  intCtaDes=2032;
        if(intBodDes==7)  intCtaDes=2034;
        if(intBodDes==8)  intCtaDes=2035;
        if(intBodDes==9)  intCtaDes=2036;
        if(intBodDes==11)  intCtaDes=2101;

      }

    strSql="INSERT INTO tbm_detdia( co_emp, co_loc, co_tipdoc, co_dia, co_reg,  co_cta, nd_mondeb, nd_monhab, st_regrep ) " +
    " VALUES ("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intCodReg+" ,"+intCtaOri+", " +
    " 0, "+dlbTot+", 'I' ) ; ";

    intCodReg++;
     strSql +=" INSERT INTO tbm_detdia( co_emp, co_loc, co_tipdoc, co_dia, co_reg,  co_cta, nd_mondeb, nd_monhab, st_regrep ) " +
    " VALUES ("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intCodReg+" ,"+intCtaDes+", " +
    " "+dlbTot+", 0,  'I' ) ; ";
    stmLoc.executeUpdate(strSql);
    stbDevTemp.append( strSql +" ; " );

   stmLoc.close();
   stmLoc=null;
   blnRes=true;
}}
catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }

return blnRes;
}



/*****************************************************************************************/










private boolean generaDevVenDet(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intCodTipDocOc, int intCodDocOc, int intCodRegOc,  String strCodAlt, String strCodAlt2, String strNomItm, String strUniMed, double dblCan, int intCodItm, int intTipMov, int intCodEmpCom, double dblCosUni, double dblPorDes, double dblCanFac, int intCodLocDev ){
  boolean blnRes=false;  
  java.sql.Statement stmLoc; 
  String strSql="";
  String strmeringegrfisbod="S";
  try{
    if(conn!=null){ 
      stmLoc=conn.createStatement();
       
       double dblValDes= ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
       double dblTotal = (dblCan * dblCosUni)- dblValDes;  
              dblTotal =  objUti.redondear(dblTotal, objZafParSis.getDecimalesBaseDatos() );  


        if(StrEstConfDevVen.equals("F")) strmeringegrfisbod="N";

        strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, nd_candev, nd_canorg ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", 1, " +
        " '"+strCodAlt+"' "+
        " ,'"+strCodAlt2+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        ", '"+strNomItm+"' "+
        ", '"+strUniMed+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+dblTotal+","+dblCosUni+", "+dblPorDes+", 'S' "+
        " ,"+dblTotal+", 'I' , '"+strmeringegrfisbod+"', 0 ,"+dblCosUni+", "+(dblCan*intTipMov)+", "+dblCanFac+" ) ";
        stmLoc.executeUpdate(strSql);
        stbDevTemp.append( strSql +" ; " );

        strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_sal=nd_sal+"+dblCan+" WHERE co_emp="+intCodEmp+" AND " +
        " co_itm="+intCodItm+" AND co_emprel="+intCodEmpCom; 
        stmLoc.executeUpdate(strSql);
        stbDevTemp.append( strSql +" ; " );

        strSql ="  UPDATE tbm_detMovInv SET nd_candev=(case when nd_candev is null then 0 else nd_candev end) + "+dblCan+" " +
        " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLocDev+" AND co_tipdoc="+intCodTipDocOc+" AND co_doc="+intCodDocOc+" "+
        " AND co_reg="+intCodRegOc; 
        stmLoc.executeUpdate(strSql);
        stbDevTemp.append( strSql +" ; " );

        
      stmLoc.close();
      stmLoc=null;
      blnRes=true;
      
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}           



private boolean getObtenerStkBodPre( java.sql.Connection conn, int intCodEmp, int intCodItm, int intCodBod, double dblStk ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="", strSqlAux="";
 double dlbStkBod=0;
 try{
  if(conn!=null){
    stmLoc=conn.createStatement();

    strSqlAux="SELECT a2.co_bodgrp FROM tbr_bodloc AS a " +
    " INNER JOIN tbr_bodEmpBodGrp AS a2 ON(a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) " +
    " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" AND a.co_bod="+intCodBodPre+" ";

    strSql="SELECT a2.co_emp, a2.co_itm , a2.co_bod, a2.nd_stkact FROM( " +
    " select * from tbm_equinv as a where a.co_emp="+intCodEmp+" and a.co_itmmae =  " +
    " ( select co_itmmae from tbm_equinv as a where a.co_emp="+intCodEmp+" and a.co_itm="+intCodItm+" ) " +
    " ) as  a1 " +
    " inner join tbm_invbod as a2 on (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm) " +
    " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a2.co_emp AND a6.co_bod=a2.co_bod) " +
    " WHERE ( a6.co_empGrp="+objZafParSis.getCodigoEmpresaGrupo()+" AND a6.co_bodGrp=( "+strSqlAux+" ) ) " +
    "   and  a2.co_bod not in ( "+intCodBod+" )  and a2.nd_stkact >= "+dblStk+"   ";
    //System.out.println(" getObtenerStkBodPre--> "+strSql );
    rstLoc=stmLoc.executeQuery(strSql);
    if(rstLoc.next()){
       dlbStkBod=rstLoc.getDouble("nd_stkact");
       intBodPreDevCom=rstLoc.getInt("co_bod");
       blnRes=true;
    }
    rstLoc.close();
    rstLoc=null;
    
   stmLoc.close();
   stmLoc=null;

 }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
  catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}           



private boolean getObtenerStkBodPrePar( java.sql.Connection conn, int intCodEmp, int intCodItm, int intCodBod, double dblStk ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="", strSqlAux="";
 try{
  if(conn!=null){
    stmLoc=conn.createStatement();

    strSqlAux="SELECT a2.co_bodgrp FROM tbr_bodloc AS a " +
    " INNER JOIN tbr_bodEmpBodGrp AS a2 ON(a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) " +
    " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" AND a.co_bod="+intCodBodPre+" ";

    strSql="SELECT a2.co_emp, a2.co_itm , a2.co_bod, a2.nd_stkact, ("+dblStk+" -a2.nd_stkact) AS canmov  FROM( " +
    " select * from tbm_equinv as a where a.co_emp="+intCodEmp+" and a.co_itmmae =  " +
    " ( select co_itmmae from tbm_equinv as a where a.co_emp="+intCodEmp+" and a.co_itm="+intCodItm+" ) " +
    " ) as  a1 " +
    " inner join tbm_invbod as a2 on (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm) " +
    " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a2.co_emp AND a6.co_bod=a2.co_bod) " +
    " WHERE ( a6.co_empGrp="+objZafParSis.getCodigoEmpresaGrupo()+" AND a6.co_bodGrp=( "+strSqlAux+" ) ) " +
    "   and  a2.co_bod not in ( "+intCodBod+" )  and a2.nd_stkact > 0   ";
    //System.out.println(" getObtenerStkBodPrePar--> "+strSql );
    rstLoc=stmLoc.executeQuery(strSql);
    if(rstLoc.next()){
       dblCanDevComPar=rstLoc.getDouble("canmov");
       intBodPreDevCom=rstLoc.getInt("co_bod");
       blnRes=true;
    }
    rstLoc.close();
    rstLoc=null;

   stmLoc.close();
   stmLoc=null;

 }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
  catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}

   


private boolean verificaMovDevCom(java.sql.Connection conn,  int intCodEmpDev, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodItm,  double dblCanFal, int intCodBodDevCom, double dlbStkBobDevCom, int intTipDocDevVen, String strCodItm  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 int intTipDocCom=2;
 int intCodCli=0; // 
 double dlbCanDev=0;
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

  String strAuxDirCli="";
  if(objZafParSis.getCodigoEmpresa()==2){
   if(objZafParSis.getCodigoLocal()==1){
      strAuxDirCli=" AND  upper(a.tx_dircli) like '%MAR%' ";
  }if(objZafParSis.getCodigoLocal()==4){
     strAuxDirCli=" AND  upper(a.tx_dircli) like '%BUE%' ";
  }}
    

  String strAuxBod="";
  if(intCodEmp==2){
   if(intTipDocDevVen==134) strAuxBod=" AND a1.co_bod IN ( 4 ) ";
   else strAuxBod=" AND a1.co_bod NOT IN ( 4 ) ";
  }

   String strAuxLoc="";
//   if(intCodEmp==4)
//       strAuxLoc="";
//   else
//     strAuxLoc=" AND a.co_loc="+intCodLoc+" ";


    strSql="select * FROM ( " +
    " SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.co_reg,  a1.nd_pordes, a1.tx_codalt, a1.tx_codalt2, a1.tx_nomitm, a1.tx_unimed, " +
    " a.ne_numdoc, a.co_cli, a1.co_bod, a1.nd_cosuni, a1.co_itm, a1.tx_codalt, a1.nd_can, a1.nd_candev " +
    " ,( abs(a1.nd_can) - abs(case when a1.nd_candev is null then 0 else a1.nd_candev end )) as saldo " +
    " FROM tbm_cabmovinv  AS a " +
    " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) " +
    " WHERE a.co_emp="+intCodEmp+"   "+strAuxLoc+" "+ //AND a.co_loc="+intCodLoc+" " +
    " AND a.co_tipdoc="+intTipDocCom+" AND a.co_cli="+intCodCli+" " +
    " AND a.fe_doc>='2008-01-01' and a.st_reg not in ('E','I') and a1.co_itm="+intCodItm+"   " +
    " and a1.co_bod= "+intCodBodDevCom+"   "+strAuxDirCli+"  "+strAuxBod+"  ORDER BY a.fe_Doc, a.co_doc " +
    " ) as x  where saldo <> 0 ";
    //System.out.println("Paso 2 verificaMovDevCom : "+ strSql );
    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
      // if( rstLoc.getDouble("saldo") >= dblCanFal ){
       if(Glo_dblCanFalDevCom > 0 ){
        if( dlbStkBobDevCom > 0 ){
        if(rstLoc.getDouble("saldo") >= Glo_dblCanFalDevCom ) {  
            
            if( dlbStkBobDevCom <= Glo_dblCanFalDevCom )
                dlbCanDev=dlbStkBobDevCom;
            else
               dlbCanDev = Glo_dblCanFalDevCom;

            Glo_dblCanFalDevCom=Glo_dblCanFalDevCom-dlbCanDev;
            dlbStkBobDevCom=dlbStkBobDevCom-dlbCanDev;
        } 
        else{
               
            if( dlbStkBobDevCom <= rstLoc.getDouble("saldo") )
              dlbCanDev=dlbStkBobDevCom;
            else
              dlbCanDev = rstLoc.getDouble("saldo");
            
            Glo_dblCanFalDevCom=Glo_dblCanFalDevCom-dlbCanDev;
            dlbStkBobDevCom=dlbStkBobDevCom-dlbCanDev;
        } 
          
           
         Glo_dblCanTotDevCom=Glo_dblCanTotDevCom+dlbCanDev;
           
         if(generaDevCom(conn, rstLoc.getInt("co_emp"),  intCodLoc, rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"),  rstLoc.getInt("co_reg")
            , rstLoc.getInt("ne_numdoc"), intCodTipDoc,  rstLoc.getInt("co_bod"), rstLoc.getString("tx_codalt"), rstLoc.getString("tx_codalt2"), rstLoc.getString("tx_nomitm"), rstLoc.getString("tx_unimed"),     dlbCanDev , rstLoc.getDouble("nd_pordes"),  rstLoc.getInt("co_itm"),  rstLoc.getDouble("nd_cosuni"),  rstLoc.getDouble("nd_can") , strCodItm, rstLoc.getInt("co_loc")  )){
           blnRes=true;   
       } } }
    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }  
 return blnRes;       
}
        

   

      


private boolean generaDevCom(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDocOc, int intCodDocOc, int intCodRegOc
        , int intNumDoc, int intCodTipDoc, int intCodBod,  String strCodAlt, String strCodAlt2, String strNomItm, String strUniMed,  double dblCan, double dblPorDes, int intCodItm, double dblCosUni, double dblCanOc, String strCodItm, int intCodLocDevCom ){
  boolean blnRes=false;
  boolean blnTransAut=false;
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

  


      String strMerIngEgr="E";
      if(verificaIngEgrFisBod(intCodEmp, intCodBod))
        strMerIngEgr="S";

   

      if(getObtenerStkBodPre( conn, intCodEmp, intCodItm,  intCodBod,  dblCan ) ){
        if(generaTransBodaBodDevCom(conn, intCodEmp, intCodItm, intBodPreDevCom, intCodBod, dblCan, "S", "F" )){
            blnTransAut=true;
            strEstGuiRem="S";
            strMerIngEgr="E";
            StrEstConfDevVen="F";
        }
      }else{
//          if(getObtenerStkBodPrePar( conn, intCodEmp, intCodItm,  intCodBod,  dblCan ) ){
//           if(generaTransBodaBodDevCom(conn, intCodEmp, intCodItm, intCodBod, intBodPreDevCom, dblCanDevComPar, "N", "P" )){
//            if(generaTransBodaBodDevCom(conn, intCodEmp, intCodItm, intBodPreDevCom, intCodBod, dblCan, "S", "F" )){
//              blnTransAut=true;
//              strEstGuiRem="S";
//              strMerIngEgr="E";
//              StrEstConfDevVen="F";
//           }}
//          }else{
           /************/
//              if(intConStbBodTemp==1)  stbLisBodItmTemp.append(" UNION ALL ");
//               stbLisBodItmTemp.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodBod+" AS COBOD, "+
//               strCodItm+" AS COITM, "+dblCan+" AS NDCAN ");
//              intConStbBodTemp=1;
          /************/
          blnTransAut=false;  //true
     } //}


   if(blnTransAut){
      if(generaDevComCab(conn, intNumDoc, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmp, 1,  dlbSub, dlbValIva, dlbTot, strEstGuiRem  )){
       if(generaDevComDet(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBod, intCodTipDocOc, intCodDocOc, intCodRegOc, strCodAlt, strCodAlt2, strNomItm, strUniMed, dblCan, intCodItm, -1 ,  objZafParSis.getCodigoEmpresa(), dblCosUni, dblPorDes, dblCanOc, intCodLocDevCom , strEstGuiRem  )){
        if(actualizaStockDev(CONN_GLO, CONN_GLO_REM, intCodEmp, intCodBod , dblCan, -1, intCodItm, strMerIngEgr, "E" )){
           blnRes=true;   
      }}}
   }
      
     objInvItm.limpiarObjeto(); 
    stmLoc.close();
    stmLoc=null;
      
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}           

private boolean generaTransBodaBodDevCom(java.sql.Connection conn, int intCodEmp, int intCodItm,  int intCodBodOri, int intCodBodDes,  double dblCanBod, String strEstGuia, String strEstConInv ){
  boolean blnRes=true;
  java.sql.ResultSet rstLoc;
  java.sql.Statement stmLoc;
  int intCodLocTra=0;
  int intCodTipDocTra=0;
  String strSql="";
  try{
    if(conn!=null){
       stmLoc=conn.createStatement();

          //System.out.println("Transferencia...Para Compañias..Dev.Com");

                   strSql="SELECT co_loctra, co_tipdoctra, st_reg FROM tbr_bodemp " +
                   " where co_emp="+intCodEmp+" and co_empper="+intCodEmp+" and st_reg='A' ";
                   rstLoc = stmLoc.executeQuery(strSql);
                   if(rstLoc.next()){
                     intCodLocTra=rstLoc.getInt("co_loctra");
                     intCodTipDocTra=rstLoc.getInt("co_tipdoctra");
                   }
                   rstLoc.close();
                   rstLoc=null;

                    if(!generaTransDev(conn, CONN_GLO, CONN_GLO_REM, intCodEmp, intCodLocTra, intCodTipDocTra, intCodBodOri, intCodBodDes, String.valueOf(intCodItm), dblCanBod, strEstGuia, strEstConInv  ) ){
                       blnRes=false;
                    }
      
    stmLoc.close();
    stmLoc=null;
}}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}

 
      
// nd_candev
private boolean generaDevComDet(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intCodTipDocOc, int intCodDocOc, int intCodRegOc,  String strCodAlt, String strCodAlt2, String strNomItm, String strUniMed, double dblCan, int intCodItm, int intTipMov, int intCodEmpCom, double dblCosUni, double dblPorDes, double dblCanOc , int intCodLocDevCom, String strEstGuiRem  ){
  boolean blnRes=false;  
  java.sql.Statement stmLoc; 
  String strSql="";
  String  strmeringegrfisbod="S";
  try{
    if(conn!=null){ 
      stmLoc=conn.createStatement();
       
       double dblValDes= ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
       double dblTotal = (dblCan * dblCosUni)- dblValDes;  
              dblTotal =  objUti.redondear(dblTotal, objZafParSis.getDecimalesBaseDatos() );  

        if(strEstGuiRem.equals("S")) strmeringegrfisbod="N";

        strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, nd_canorg, nd_candev ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", 1, " +
        " '"+strCodAlt+"' "+
        " ,'"+strCodAlt2+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        ", '"+strNomItm+"' "+
        ", '"+strUniMed+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+(dblTotal*intTipMov)+","+dblCosUni+", "+dblPorDes+", 'S' "+
        " ,"+(dblTotal*intTipMov)+", 'I' , '"+strmeringegrfisbod+"', 0 ,"+dblCosUni+", "+dblCanOc+", "+(dblCan*intTipMov)+" ) ";
        stmLoc.executeUpdate(strSql);
        stbDevTemp.append( strSql +" ; " );
          
        strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_sal=nd_sal-"+dblCan+" WHERE co_emp="+intCodEmp+" AND " +
        " co_itm="+intCodItm+" AND co_emprel="+intCodEmpCom; 
        stmLoc.executeUpdate(strSql);
        stbDevTemp.append( strSql +" ; " );

        strSql ="  UPDATE tbm_detMovInv SET nd_candev=(case when nd_candev is null then 0 else nd_candev end) + "+dblCan+" " +
        " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLocDevCom+" AND co_tipdoc="+intCodTipDocOc+" AND co_doc="+intCodDocOc+" "+
        " AND co_reg="+intCodRegOc; 
        stmLoc.executeUpdate(strSql);
        stbDevTemp.append( strSql +" ; " );

      stmLoc.close();
      stmLoc=null;
      blnRes=true;
      
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}           


  

   
private boolean generaDevComCab(java.sql.Connection conn, int intNumDoc, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpCom,  int TipMov, double dlbSub, double dlbValIva, double dlbTot , String strEstGuiRem ){
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
      
      datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
      strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());      
      
     if(TipMov==1){
         
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
          
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){

         intNumDocDev=getUltNumDoc(conn, intCodEmp, intCodLoc, intCodTipDoc);

         if(intDocRelEmpTemp==1) stbDocRelEmpTemp.append(" UNION ALL ");
             stbDocRelEmpTemp.append( " SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC" );
          intDocRelEmpTemp=1;

          strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
          " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot,  " +
          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
          " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv, st_creguirem ) " +
          " VALUES("+intCodEmp+", "+intCodLoc+", "+
          intCodTipDoc+", "+intCodDoc+", '"+datFecAux+"', "+rstLoc.getString("co_cli")+" ,null,'','"+
          rstLoc.getString("tx_nom")+"','"+rstLoc.getString("tx_dir")+"','"+rstLoc.getString("tx_ide")+ "','"+rstLoc.getString("tx_tel")+"','"+
          " ','',"+intNumDocDev+" , "+intNumDoc+","+
          "  '' ,'' , "+dlbSub+" ,0 , "+dlbTot+", "+dlbValIva+" , 1 ,'Contado', '"+strFecSistema+"', "+
          objZafParSis.getCodigoUsuario()+", '"+strFecSistema+"', "+objZafParSis.getCodigoUsuario()+" , null, null," +
          " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', null "+
          " ,'S', 'S', 'P', '"+strEstGuiRem+"' )";
          strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDocDev+" WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc;
          stmLoc.executeUpdate(strSql);
          stbDevTemp.append( strSql +" ; " );
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      blnRes=true;
      
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}           




private boolean generaDevVenCab(java.sql.Connection conn, int intNumDoc, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpCom,  int TipMov, double dlbSub, double dlbValIva, double dlbTot ,int intCodTipDocOc, int intCodDocOc, int intCodLocDev, String strConfDevVen  ){
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


      datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
      strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());

     if(TipMov==1){

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
         
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){

         intNumDocDev=getUltNumDoc(conn, intCodEmp, intCodLoc, intCodTipDoc);

         if(intDocRelEmpTemp==1) stbDocRelEmpTemp.append(" UNION ALL ");
           stbDocRelEmpTemp.append( " SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC" );
        intDocRelEmpTemp=1;

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
          stbDevTemp.append( strSql +" ; " );
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}




         
//validaditm




private boolean verificaCanTotItm(String strCodItm){
  boolean blnRes=false; 
  double dblCanTot=0;
  double dlbCanTotSal=3; 
  String strCanSal ="";
  String strCodItm2="";
   for(int i=0; i < tblDat.getRowCount(); i++){ 
    if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null) {
     strCodItm2 = tblDat.getValueAt(i, INT_TBL_CODITM).toString();
     if(strCodItm2.equals(strCodItm)){  
      if( ( (tblDat.getValueAt(i, INT_TBL_CODEMP)==null) || (tblDat.getValueAt(i, INT_TBL_CODEMP).toString().equals("")) )   ) {
        strCanSal = ((tblDat.getValueAt(i, INT_TBL_CANCOM)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANCOM).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANCOM).toString()));
        dlbCanTotSal = objUti.redondear(strCanSal, 6); 
      }else {
          strCanSal = ((tblDat.getValueAt(i, INT_TBL_CANCOM)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANCOM).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANCOM).toString()));
          dblCanTot += objUti.redondear(strCanSal, 6);
      }
    }}}

  dblCanTot = objUti.redondear(dblCanTot, 6);
  //System.out.println("dblCanTot-> "+dblCanTot +" dlbCanTotSal-> "+dlbCanTotSal );


  if( dblCanTot > dlbCanTotSal ) blnRes=true;
 return blnRes;   
}




private double getTotComItm(String strCodItm){
  double dblCanTot=0;
  String strCanSal ="";
  String strCodItm2="";
   for(int i=0; i < tblDat.getRowCount(); i++){ 
    if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null) {
     strCodItm2 = tblDat.getValueAt(i, INT_TBL_CODITM).toString();
     if(strCodItm2.equals(strCodItm)){
      if(!( (tblDat.getValueAt(i, INT_TBL_CODEMP)==null) || (tblDat.getValueAt(i, INT_TBL_CODEMP).toString().equals("")) )   ) {
          strCanSal = ((tblDat.getValueAt(i, INT_TBL_CANCOM)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANCOM).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANCOM).toString()));
          dblCanTot += objUti.redondear(strCanSal, 6);
      }
    }}}

  dblCanTot = objUti.redondear(dblCanTot, 6);
 return dblCanTot;   
}



private double getCanFalItm(String strCodItm){
  double dblCanTot=0;
  String strCanSal ="";
  String strCodItm2="";
   for(int i=0; i < tblDat.getRowCount(); i++){ 
    if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null) {
     strCodItm2 = tblDat.getValueAt(i, INT_TBL_CODITM).toString();
     if(strCodItm2.equals(strCodItm)){
      if(( (tblDat.getValueAt(i, INT_TBL_CODEMP)==null) || (tblDat.getValueAt(i, INT_TBL_CODEMP).toString().equals("")) )   ) {
          strCanSal = ((tblDat.getValueAt(i, INT_TBL_CANCOM)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANCOM).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANCOM).toString()));
          dblCanTot = objUti.redondear(strCanSal, 6); 
      }
    }}}
 return dblCanTot;   
}

 
private void MensajeInf(String strMensaje){
 javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
 String strTit;
 strTit="Mensaje del sistema Zafiro";
 obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
}

    
    
  

  


    
private boolean configurarVenConClientes() {
    boolean blnRes=true;
    try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("a.co_cli");
        arlCam.add("a.tx_nom");
        arlCam.add("a.tx_dir");
        arlCam.add("a.tx_tel");
        arlCam.add("a.tx_ide");

        ArrayList arlAli=new ArrayList();
        arlAli.add("Código");
        arlAli.add("Nom.Cli.");
        arlAli.add("Dirección");
        arlAli.add("Telefono");
        arlAli.add("RUC/CI");

        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("50");
        arlAncCol.add("180");
        arlAncCol.add("120");
        arlAncCol.add("80");
        arlAncCol.add("100");           

        //Armar la sentencia SQL.
        String  strSQL;
        strSQL="SELECT a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide FROM tbr_cliloc as a1 " +
        " INNER JOIN tbm_cli as a ON(a.co_emp=a1.co_emp AND a.co_cli=a1.co_cli) " +
        " WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a1.co_loc="+objZafParSis.getCodigoLocal()+" " +
        " AND a.st_cli='S' AND a.st_reg NOT IN('I','T')  order by a.tx_nom ";

        objVenConCLi=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
   return blnRes;
}
        
   

private boolean configurarVenConVendedor() {
    boolean blnRes=true;
    try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("a.co_usr");
        arlCam.add("a.tx_nom");
        ArrayList arlAli=new ArrayList();
        arlAli.add("Código");
        arlAli.add("Nombre.");
        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("70");
        arlAncCol.add("470");
        //Armar la sentencia SQL.
        String  strSQL="";
        strSQL="select a.co_usr, a.tx_nom  from tbr_usremp as b" +
        " inner join tbm_usr as a on (a.co_usr=b.co_usr) " +
        " where b.co_emp="+objZafParSis.getCodigoEmpresa()+" and b.st_ven='S' and a.st_reg not in ('I')  order by a.tx_nom";

        objVenConVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }catch (Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
   return blnRes;
 }
    

              
  
private boolean configuraTbl(){
       boolean blnRes=false;
       try{
            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"..");
            vecCab.add(INT_TBL_CODITM,"Cod.Itm");
            vecCab.add(INT_TBL_CODEMP,"Cod.Emp");
            vecCab.add(INT_TBL_CODBOD,"Cod.Bod");
            vecCab.add(INT_TBL_NOMEMP,"Nom.Emp");
            vecCab.add(INT_TBL_NOMBOD,"Nom.Bod");
            vecCab.add(INT_TBL_STKACT,"Atk.Act");
            vecCab.add( INT_TBL_ESTBOD,"" );
            vecCab.add( INT_TBL_CANCOM,"" );
            vecCab.add( INT_TBL_CODALT,"Cod.Itm" );
            vecCab.add( INT_TBL_NOMITM,"Nom.Itm" );
            vecCab.add( INT_TBL_UNIMED,"Uni.Med" );
            vecCab.add( INT_TBL_COSUNI,"Cos.Uni" );

            vecCab.add( INT_TBL_DATCANCOM ,"");
            
	    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);
    	    
	    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
	    
	    //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOMEMP).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(300);
            tcmAux.getColumn(INT_TBL_STKACT).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_CANCOM).setPreferredWidth(90);
            
            
          objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
          tcmAux.getColumn(INT_TBL_NOMBOD).setCellRenderer(objTblCelRenLbl);
          tcmAux.getColumn(INT_TBL_NOMEMP).setCellRenderer(objTblCelRenLbl);
          tcmAux.getColumn(INT_TBL_CODEMP).setCellRenderer(objTblCelRenLbl);
          tcmAux.getColumn(INT_TBL_CODBOD).setCellRenderer(objTblCelRenLbl);
          tcmAux.getColumn(INT_TBL_CODITM).setCellRenderer(objTblCelRenLbl);
          tcmAux.getColumn(INT_TBL_ESTBOD).setCellRenderer(objTblCelRenLbl);
          
           objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    //Mostrar de color gris las columnas impares.
                    
                    int intCell=objTblCelRenLbl.getRowRender();
                     
                    String str=tblDat.getValueAt(intCell, INT_TBL_CODEMP).toString();
                    if(str.equals("")){
                        objTblCelRenLbl.setBackground(java.awt.Color.BLUE);
                        objTblCelRenLbl.setFont(new java.awt.Font(objTblCelRenLbl.getFont().getFontName(), java.awt.Font.BOLD,   objTblCelRenLbl.getFont().getSize()));
                        objTblCelRenLbl.setForeground(java.awt.Color.WHITE);
                    } 
                    if(str.equals("1")){
                        objTblCelRenLbl.setBackground(java.awt.Color.ORANGE);
                        objTblCelRenLbl.setForeground(java.awt.Color.BLACK);
                    } 
                    if(str.equals("2")){
                        objTblCelRenLbl.setBackground(java.awt.Color.WHITE);
                        objTblCelRenLbl.setForeground(java.awt.Color.BLACK);
                    } 
                    if(str.equals("4")){
                        objTblCelRenLbl.setBackground(java.awt.Color.YELLOW);
                        objTblCelRenLbl.setForeground(java.awt.Color.BLACK);
                    }
                    
                    if(str.equals("0")){
                        objTblCelRenLbl.setBackground(java.awt.Color.BLACK);
                        objTblCelRenLbl.setForeground(java.awt.Color.BLACK);
                    } 
                    
                }
            });

          
             
             
          objTblCelRenLbl2.setBackground(objZafParSis.getColorCamposObligatorios());
          objTblCelRenLbl2.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
          objTblCelRenLbl2.setTipoFormato(objTblCelRenLbl2.INT_FOR_NUM);
          objTblCelRenLbl2.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
          objTblMod.setColumnDataType(INT_TBL_STKACT, objTblMod.INT_COL_DBL, new Integer(0), null);
          tcmAux.getColumn(INT_TBL_STKACT).setCellRenderer(objTblCelRenLbl2);
          objTblMod.setColumnDataType(INT_TBL_CANCOM, objTblMod.INT_COL_DBL, new Integer(0), null);
          tcmAux.getColumn(INT_TBL_CANCOM).setCellRenderer(objTblCelRenLbl2);
          objTblCelRenLbl2.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    //Mostrar de color gris las columnas impares.
                    
                    int intCell=objTblCelRenLbl2.getRowRender();
                     
                    String str=tblDat.getValueAt(intCell, INT_TBL_CODEMP).toString();
                    if(str.equals("")){
                        objTblCelRenLbl2.setBackground(java.awt.Color.BLUE);
                        objTblCelRenLbl2.setFont(new java.awt.Font(objTblCelRenLbl2.getFont().getFontName(), java.awt.Font.BOLD,   objTblCelRenLbl2.getFont().getSize()));
                        objTblCelRenLbl2.setForeground(java.awt.Color.WHITE);
                    } 
                    if(str.equals("1")){
                        objTblCelRenLbl2.setBackground(java.awt.Color.ORANGE);
                        objTblCelRenLbl2.setForeground(java.awt.Color.BLACK);
                    } 
                    if(str.equals("2")){
                        objTblCelRenLbl2.setBackground(java.awt.Color.WHITE);
                        objTblCelRenLbl2.setForeground(java.awt.Color.BLACK);
                    } 
                    if(str.equals("4")){
                        objTblCelRenLbl2.setBackground(java.awt.Color.YELLOW);
                        objTblCelRenLbl2.setForeground(java.awt.Color.BLACK);
                    }
                    
                    if(str.equals("0")){
                        objTblCelRenLbl2.setBackground(java.awt.Color.BLACK);
                        objTblCelRenLbl2.setForeground(java.awt.Color.BLACK);
                    } 
                   //objTblCelRenLbl2.setToolTipText(objTblCelRenLbl.getText());
                }
            });

             
            
            
            
            
          objTblCelRenLbl3.setBackground(objZafParSis.getColorCamposObligatorios());
          objTblCelRenLbl3.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
          objTblCelRenLbl3.setTipoFormato(objTblCelRenLbl3.INT_FOR_NUM);
          
          objTblCelRenLbl3.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
          objTblMod.setColumnDataType(INT_TBL_STKACT, objTblMod.INT_COL_DBL, new Integer(0), null);
          tcmAux.getColumn(INT_TBL_STKACT).setCellRenderer(objTblCelRenLbl3);
           
          objTblCelRenLbl3.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
          objTblMod.setColumnDataType(INT_TBL_CANCOM, objTblMod.INT_COL_DBL, new Integer(0), null);
          tcmAux.getColumn(INT_TBL_CANCOM).setCellRenderer(objTblCelRenLbl3);
          objTblCelRenLbl3.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    //Mostrar de color gris las columnas impares.
                    
                    int intCell=objTblCelRenLbl3.getRowRender();
                    
                    String strEstBod = (( (tblDat.getValueAt(intCell, INT_TBL_ESTBOD)==null) || (tblDat.getValueAt(intCell, INT_TBL_ESTBOD).toString().equals("")))?"V":tblDat.getValueAt(intCell, INT_TBL_ESTBOD).toString());
                    String str=tblDat.getValueAt(intCell, INT_TBL_CODEMP).toString();


                    String strCanCom = (( (tblDat.getValueAt(intCell, INT_TBL_DATCANCOM)==null) || (tblDat.getValueAt(intCell, INT_TBL_DATCANCOM).toString().equals("")))?"":tblDat.getValueAt(intCell, INT_TBL_DATCANCOM).toString());


                    if(str.equals("")){

                        objTblCelRenLbl3.setToolTipText(" "+strCanCom  );
                       
                        objTblCelRenLbl3.setBackground(java.awt.Color.BLUE);
                        objTblCelRenLbl3.setFont(new java.awt.Font(objTblCelRenLbl2.getFont().getFontName(), java.awt.Font.BOLD,   objTblCelRenLbl2.getFont().getSize()));
                        objTblCelRenLbl3.setForeground(java.awt.Color.WHITE);
                    } 
                    if(str.equals("1")){
                     if(!(strEstBod.trim().equals("V"))) {
                        objTblCelRenLbl3.setToolTipText(" "+objTblCelRenLbl3.getText());
                        objTblCelRenLbl3.setBackground(java.awt.Color.LIGHT_GRAY);
                        objTblCelRenLbl3.setForeground(java.awt.Color.BLACK);
                     }else{
                        objTblCelRenLbl3.setBackground(java.awt.Color.ORANGE);
                        objTblCelRenLbl3.setForeground(java.awt.Color.BLACK);
                     }  
                    } 
                    if(str.equals("2")){
                     if(!(strEstBod.trim().equals("V"))) {
                        objTblCelRenLbl3.setToolTipText(" "+objTblCelRenLbl3.getText());
                        objTblCelRenLbl3.setBackground(java.awt.Color.LIGHT_GRAY);
                        objTblCelRenLbl3.setForeground(java.awt.Color.BLACK);
                     }else{
                        objTblCelRenLbl3.setBackground(java.awt.Color.WHITE);
                        objTblCelRenLbl3.setForeground(java.awt.Color.BLACK);
                     }
                    
                    }
                    if(str.equals("4")){
                     if(!(strEstBod.trim().equals("V"))) {  
                        objTblCelRenLbl3.setToolTipText(" "+objTblCelRenLbl3.getText());
                        objTblCelRenLbl3.setBackground(java.awt.Color.LIGHT_GRAY);
                        objTblCelRenLbl3.setForeground(java.awt.Color.BLACK);
                     }else{
                        objTblCelRenLbl3.setBackground(java.awt.Color.YELLOW);
                        objTblCelRenLbl3.setForeground(java.awt.Color.BLACK);
                    }}
                    
                    if(str.equals("0")){
                        objTblCelRenLbl3.setBackground(java.awt.Color.BLACK);
                        objTblCelRenLbl3.setForeground(java.awt.Color.BLACK);
                    } 
                  
                }
            });

            
            
            
                
            /* Aqui se agrega las columnas que van 
                ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODITM);
            arlColHid.add(""+INT_TBL_CODEMP);
            arlColHid.add(""+INT_TBL_ESTBOD);
            arlColHid.add(""+INT_TBL_CODBOD);
            arlColHid.add(""+INT_TBL_UNIMED);
            arlColHid.add(""+INT_TBL_NOMITM);
            arlColHid.add(""+INT_TBL_CODALT);
            arlColHid.add(""+INT_TBL_COSUNI);
            arlColHid.add(""+INT_TBL_DATCANCOM);
            
            
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
             vecAux.add("" + INT_TBL_CANCOM);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            
            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANCOM).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                  
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        String strEstBod = (( (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString().equals("")))?"V":tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString());
                        
                        if((strEstBod.trim().equals("V"))) {
                            objTblCelEdiTxt.setCancelarEdicion(true);
                        } 
                          
                     
                    }
                     
                }
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                   
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        String strEstBod = (( (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString().equals("")))?"V":tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString());
                        
                        if((strEstBod.trim().equals("V"))) {
                            objTblCelEdiTxt.setCancelarEdicion(true);
                         }else{
                        
                            String strCodItm = ((tblDat.getValueAt(intNumFil, INT_TBL_CODITM)==null)?"0":tblDat.getValueAt(intNumFil, INT_TBL_CODITM).toString());
                            
                            double dlbCan =  Double.parseDouble((((tblDat.getValueAt(intNumFil, INT_TBL_STKACT)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_STKACT).toString().equals("")))?"0":tblDat.getValueAt(intNumFil, INT_TBL_STKACT).toString()));
                            double dlbCanCom = Double.parseDouble((((tblDat.getValueAt(intNumFil, INT_TBL_CANCOM)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_CANCOM).toString().equals("")))?"0":tblDat.getValueAt(intNumFil, INT_TBL_CANCOM).toString()));
                            if(dlbCanCom > dlbCan){
                               MensajeInf("La cantidad es mayor a la existente.1.");
                               tblDat.setValueAt("0", intNumFil, INT_TBL_CANCOM);
                            }
                            if(verificaCanTotItm(strCodItm)){
                              MensajeInf("La cantidad es mayor a la existente.2.");
                              tblDat.setValueAt("0", intNumFil, INT_TBL_CANCOM);
                            }
                            
                            
                          }
                    
                        
                        
                        
                    }
                    
                }
            });


    


            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);
          
            new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);

           blnRes=true;
	 }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
        return blnRes;
      }
    
    







           
             
           
          






    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        butradgrp = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        panTbl = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBut = new javax.swing.JPanel();
        panSubBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panTit.setPreferredSize(new java.awt.Dimension(100, 24));

        lblTit.setText("titulo");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panFrm.setLayout(new java.awt.BorderLayout());

        panTabGen.setLayout(new java.awt.BorderLayout());

        panTbl.setLayout(new java.awt.BorderLayout());

        tblDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrTbl.setViewportView(tblDat);

        panTbl.add(scrTbl, java.awt.BorderLayout.CENTER);

        panTabGen.add(panTbl, java.awt.BorderLayout.CENTER);

        tabGen.addTab("General", panTabGen);

        panFrm.add(tabGen, java.awt.BorderLayout.CENTER);
        tabGen.getAccessibleContext().setAccessibleName("General");

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBut.setLayout(new java.awt.BorderLayout());

        butAce.setFont(new java.awt.Font("SansSerif", 0, 11));
        butAce.setText("Aceptar");
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panSubBot.add(butAce);

        butCan.setFont(new java.awt.Font("SansSerif", 0, 11));
        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panSubBot.add(butCan);

        panBut.add(panSubBot, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBut.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBut, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-650)/2, (screenSize.height-470)/2, 650, 470);
    }// </editor-fold>//GEN-END:initComponents



    







private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
    // TODO add your handling code here:
     cerrarVen();
}//GEN-LAST:event_butCanActionPerformed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    // TODO add your handling code here:
    cerrarVen();
}//GEN-LAST:event_formWindowClosing

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    // TODO add your handling code here:
    configurarFrm();
            
}//GEN-LAST:event_formWindowOpened

private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
    // TODO add your handling code here:
  java.sql.Connection conn;
  stbDatVen=new StringBuffer();
  stbDatInsVen=new StringBuffer();
  try{  
    
      if(CONN_GLO_REM!=null)conn = CONN_GLO_REM; 
       else conn = CONN_GLO;
      
   if(validaditm()){
    if(generaVenCom(conn)){
     if(unirDocPaso1(conn)){
          blnAcepta = true;

   }}}

      
  stbDatVen=null;
  stbDatInsVen=null;
  dispose();
    
  }catch(Exception Evt) { objUti.mostrarMsgErr_F1(this, Evt);     }
  
  
             
}//GEN-LAST:event_butAceActionPerformed



private boolean unirDocPaso1(java.sql.Connection conn){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
   if(conn!=null){
    stmLoc=conn.createStatement();

   if(!stbDatVen.toString().equals("")){
    strSql="SELECT * FROM (" +
    " SELECT coemp, coloc, cotipdoc, cobod FROM ( "+stbDatVen.toString()+" ) AS x " +
    " ) AS x GROUP BY coemp, coloc, cotipdoc, cobod ORDER BY cotipdoc ";
    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
       if(unirDocPaso2( conn, rstLoc.getInt("coemp"), rstLoc.getInt("coloc"), rstLoc.getInt("cotipdoc"), rstLoc.getInt("cobod") )){
         blnRes=true;
       }else{
          blnRes=false;
          break;
       }
    }
    rstLoc.close();
    rstLoc=null;
   }
   stmLoc.close();
   stmLoc=null;
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
  return blnRes;
}

private boolean unirDocPaso2(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodBod ){
 boolean blnRes=false;
 java.sql.Statement stmLoc,stmLoc01;
 java.sql.ResultSet rstLoc,rstLoc01;
 String strSql="", strSqlDel="";
 int intCod_Doc=0;
 int intConTrol=0;
 int intCod_Reg=0;
 try{
   if(conn!=null){
    stmLoc=conn.createStatement();
    stmLoc01=conn.createStatement();

    strSql="SELECT * FROM ( SELECT a1.* FROM ("+stbDatVen.toString()+" ) AS a " +
    " inner join tbm_detmovinv as a1 on (a1.co_emp=a.coemp and a1.co_loc=a.coloc and a1.co_tipdoc=a.cotipdoc and a1.co_doc=a.codoc) "+
    " ) as x where co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_bod="+intCodBod+" ";
    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){

      if(intConTrol==0){
        strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
        " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc;
        rstLoc01 = stmLoc01.executeQuery(strSql);
        if(rstLoc01.next()) intCod_Doc=rstLoc01.getInt("co_doc");
        rstLoc01.close();
        rstLoc01=null;
        if(!unirDocPaso3(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"),  intCod_Doc, intCodBod ) )
        {
         blnRes=false;
         break;
        }
      }
      intConTrol=1;

      intCod_Reg++;

      if(!unirDocPaso4(conn,  rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), rstLoc.getInt("co_reg"), intCod_Doc,  intCod_Reg  ) )
      {
       blnRes=false;
       break;
      }

        strSqlDel+=" DELETE FROM TBM_DETMOVINV WHERE CO_EMP="+rstLoc.getInt("co_emp")+" AND CO_LOC="+rstLoc.getInt("co_loc")+" " +
        "  AND CO_TIPDOC="+rstLoc.getInt("co_tipdoc")+" AND CO_DOC="+rstLoc.getInt("co_doc")+" ; ";
        strSqlDel+=" DELETE FROM TBM_CABMOVINV WHERE CO_EMP="+rstLoc.getInt("co_emp")+" AND CO_LOC="+rstLoc.getInt("co_loc")+" " +
        "  AND CO_TIPDOC="+rstLoc.getInt("co_tipdoc")+" AND CO_DOC="+rstLoc.getInt("co_doc")+" ; ";

 
     blnRes=true;
    }
    rstLoc.close();
    rstLoc=null;

    if(blnRes){
       stmLoc.executeUpdate( stbDatInsVen.toString() );
       stmLoc.executeUpdate( strSqlDel );
    }
    
    stmLoc.close();
    stmLoc=null;
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
  return blnRes;
}
   
private boolean unirDocPaso3(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc , int intCo_Doc, int intCodBod ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 int intNumDoc=0;
 double dblTot=0;
 double dblSub=0;
 double dblIva=0;
 String strEstConf="F";
 try{
   if(conn!=null){
    stmLoc=conn.createStatement();
 
      intNumDoc=getUltNumDoc(conn, intCodEmp, intCodLoc, intCodTipDoc);

      if(intDocRelEmp==1) stbDocRelEmp.append(" UNION ALL ");
        stbDocRelEmp.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCo_Doc+" AS CODOC" );
      intDocRelEmp=1;

        strSql="SELECT * FROM ( " +
        " SELECT a.*,  a1.nd_tot, a1.nd_sub, a1.nd_valiva, a1.st_coninv  FROM ("+stbDatVen.toString()+" ) AS a " +
        " inner join tbm_cabmovinv as a1 on (a1.co_emp=a.coemp and a1.co_loc=a.coloc and a1.co_tipdoc=a.cotipdoc and a1.co_doc=a.codoc) "+
        " ) as x where coemp="+intCodEmp+" and coloc="+intCodLoc+" and cotipdoc="+intCodTipDoc+" and cobod="+intCodBod+" ";
        //System.out.println("-->"+ strSql );
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

            dblTot+=rstLoc.getDouble("nd_tot");
            dblSub+=rstLoc.getDouble("nd_sub");
            dblIva+=rstLoc.getDouble("nd_valiva");
            if( rstLoc.getString("st_coninv").equals("P") ) strEstConf="P";
            if( rstLoc.getString("st_coninv").equals("C") ) strEstConf="C";
            if( rstLoc.getString("st_coninv").equals("F") ){
               if( strEstConf.equals("P") || strEstConf.equals("C") ) strEstConf="P";
               else strEstConf="F";
            }

        }
        rstLoc.close();
        rstLoc=null;


      strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
      " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, " +
      " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
      " co_usrMod,co_forret,tx_vehret,tx_choret, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
      " ,st_coninvtraaut, st_excDocConVenCon, st_coninv, co_ptodes ) " +
      " SELECT  co_emp, co_loc, co_tipDoc, "+intCo_Doc+", fe_doc, co_cli, co_com, tx_ate, "+
      " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, "+intNumDoc+", ne_numCot, " +
      " tx_obs1, tx_obs2, "+dblSub+", nd_porIva, "+dblTot+", "+dblIva+", co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
      " co_usrMod,co_forret,tx_vehret,tx_choret, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
      " ,st_coninvtraaut, st_excDocConVenCon, '"+strEstConf+"', co_ptodes  "+
      " FROM tbm_cabMovInv WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc;
      stbDatInsVen.append(strSql+" ; ");

      strSql ="UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDoc+" WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc;
      stmLoc.executeUpdate(strSql);

    stmLoc.close();
    stmLoc=null;
    blnRes=true;
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
  return blnRes;
}

private boolean unirDocPaso4(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc , int intCodReg, int intCo_Doc, int intCod_Reg  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 try{
   if(conn!=null){
    stmLoc=conn.createStatement();

      strSql="INSERT INTO tbm_detMovInv( co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
      " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
      " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
      " ,nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, tx_obs1  ) " +
      " SELECT  co_emp, co_loc, co_tipdoc , "+intCo_Doc+", "+intCod_Reg+", "+
      " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +
      " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +
      " ,nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni ,tx_obs1  "+
      " FROM tbm_detMovInv WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" "+
      " AND co_reg="+intCodReg;
      stbDatInsVen.append(strSql+" ; ");

    stmLoc.close();
    stmLoc=null;
    blnRes=true;
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
  return blnRes;
}







private void generaCompVent_paso0(){
     
//      final int INT_TBL_LINEA =0; 
//    final int INT_TBL_CODITM=1;
//    final int INT_TBL_CODEMP=2;
//    final int INT_TBL_CODBOD=3;
//    final int INT_TBL_NOMEMP=4;
//    final int INT_TBL_NOMBOD=5;
//    final int INT_TBL_STKACT=6;
//    final int INT_TBL_ESTBOD=7;
//    final int INT_TBL_CANCOM=8;
//    final int INT_TBL_CODALT=9;
//    final int INT_TBL_NOMITM=10;
//    final int INT_TBL_UNIMED=11;
//    final int INT_TBL_COSUNI=12;
//    
    String srtSql="";
    String strCanSal ="";
    double dblCanBod=0; 
    StringBuffer stbins;
    stbins=new StringBuffer(); 
    int intConTrol=0;
    
     for(int i=0; i < tblDat.getRowCount(); i++){ 
      if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null) {
       if(tblDat.getValueAt(i, INT_TBL_ESTBOD)!=null) {
        if(tblDat.getValueAt(i, INT_TBL_ESTBOD).toString().equals("A")){
    
          strCanSal = ((tblDat.getValueAt(i, INT_TBL_CANCOM)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANCOM).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANCOM).toString()));
          dblCanBod = objUti.redondear(strCanSal, 6);
          if(dblCanBod>0){
   
             if(intConTrol==1)   stbins.append(" UNION ALL ");  
              srtSql ="SELECT "+tblDat.getValueAt(i, INT_TBL_CODEMP)+" as coemp,"+tblDat.getValueAt(i, INT_TBL_CODITM)+" as coitm, "+
              tblDat.getValueAt(i, INT_TBL_CODBOD)+" as cobod,"+tblDat.getValueAt(i, INT_TBL_STKACT)+" as stkact, '"+
              tblDat.getValueAt(i, INT_TBL_ESTBOD)+"' as estbod,"+  objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANCOM))+" as cancon  ";
              stbins.append(srtSql);
              intConTrol=1;
                      
              
          }  
          
        }
     }}}
     
        
    generaCompVent_paso1(stbins);
            
    srtSql=" select *  from ( "+stbins.toString()+" ) as x ";
    
    ///System.out.println(" "+ srtSql );
    
    stbins=null;
}

private void generaCompVent_paso1(StringBuffer stbins){
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String srtSql="";
 
  try{
    conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn!=null){
  
      stmLoc=conn.createStatement(); 
       
      srtSql=" SELECT coemp, cobod  FROM(  "+stbins.toString()+"  ) AS x GROUP BY coemp, cobod ORDER BY coemp  ";
      rstLoc=stmLoc.executeQuery(srtSql);
      while(rstLoc.next()){
         generaCompVent_paso2(conn, stbins, rstLoc.getString("coemp"), rstLoc.getString("cobod") );
      }
      
   }}catch(java.sql.SQLException ex) { blnRes=false; objUti.mostrarMsgErr_F1(this, ex);   }
     catch(Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
}

private void generaCompVent_paso2(java.sql.Connection conn, StringBuffer stbins, String strCodEmp, String strCodBod){
  boolean blnRes=false;
  java.sql.Statement stmLoc,stmLoc01;
  java.sql.ResultSet rstLoc,rstLoc01;
  String strSql="";
  String strCodAlt="";
  int intCodItm=0;
  double dblDesVta=0;
  double dblPreVta=0;
  double dblCosUni=0;
  int intNumFila=0;
  try{
   if(conn!=null){
  
      stmLoc=conn.createStatement(); 
      stmLoc01=conn.createStatement(); 
              
         strSql=" SELECT * FROM(  "+stbins.toString()+" ) AS x WHERE coemp="+strCodEmp+" AND cobod="+strCodBod+" ";
         rstLoc=stmLoc.executeQuery(strSql);
         while(rstLoc.next()){
             
              intNumFila++; 
              /*********************************************************************/
                  if(objZafParSis.getCodigoEmpresa()==1){
                   if(strCodEmp.equals("2")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=2854";  
                   if(strCodEmp.equals("4")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=3117";  
                  }
                  if(objZafParSis.getCodigoEmpresa()==2){
                   if(strCodEmp.equals("1")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=603";  
                   if(strCodEmp.equals("4")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=498";  
                  }
                  if(objZafParSis.getCodigoEmpresa()==4){
                   if(strCodEmp.equals("2")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=789";  
                   if(strCodEmp.equals("1")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=1039";  
                  }
                  rstLoc01 = stmLoc01.executeQuery(strSql);
                  if(rstLoc01.next()) 
                     dblDesVta=rstLoc01.getDouble("nd_maxDes");
                  rstLoc01.close();
                  rstLoc01=null;
                /*********************************************************************/
                  
                  
                  strSql="SELECT a.co_emp, a.co_itm, a1.nd_prevta1, nd_stkact, a1.tx_codalt FROM tbm_equinv as a " +
                  " inner join tbm_inv as a1 on (a1.co_emp=a.co_emp and a1.co_itm=a.co_itm) "+
                  " WHERE a.co_emp="+strCodEmp+" AND a.co_itmmae = ( " +
                  " select co_itmmae from tbm_equinv where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_itm="+rstLoc.getString("coitm")+" ) ";
                  rstLoc01 = stmLoc01.executeQuery(strSql);
                  if(rstLoc01.next()){ 
                     intCodItm=rstLoc01.getInt("co_itm");
                     dblPreVta=rstLoc01.getDouble("nd_prevta1");
                     strCodAlt=rstLoc01.getString("tx_codalt");
                  }
                  rstLoc01.close();
                  rstLoc01=null;
                  if(dblPreVta > 0 ){
                     dblCosUni  =  dblPreVta - ( dblPreVta * ( dblDesVta / 100 ) );

                   int intCodBodDes =  _getCodigoBodDes(conn, objZafParSis.getCodigoEmpresa(), intCodBodPre, Integer.parseInt(strCodEmp) );
                   if(!(intCodBodDes== -1)){
                     
                    if(generaVen(conn, Integer.parseInt(strCodEmp), Integer.parseInt(strCodBod), intNumFila, rstLoc.getDouble("cancon"), dblDesVta, intCodItm, dblPreVta, intCodBodDes )){
                     if(generaCom(conn, Integer.parseInt(strCodEmp), objZafParSis.getCodigoEmpresa(), intCodBodPre, intNumFila, rstLoc.getDouble("cancon"), Integer.parseInt(strCodBod), dblCosUni )){  
                         //System.out.println("OKIII");
                         blnRes=true; 
                     }else { blnRes=false;  break; }
                    }else { blnRes=false;  break; } 

                    }else{  MensajeInf("PROBLEMA AL OBTENER BODEGA DESTINO.. ");  blnRes=false;  break; }
                      
                  }else{  MensajeInf("El item "+strCodAlt+" no tiene precio de lista.");  blnRes=false;  break; } 
                  
                  
         } 
          
           
   }}catch(java.sql.SQLException ex) { blnRes=false; objUti.mostrarMsgErr_F1(this, ex);   }
     catch(Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
}


private boolean generaVenCom(java.sql.Connection conn){
   boolean blnRes=true;  
   // for(int i=0; i<tblDat.getRowCount(); i++){
      if(!verificaBodEmp(conn)){
          blnRes=false;  
      //    break;
   }//}
  return blnRes;
}         
 

/*******************************************************************************************/


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

 }}catch(java.sql.SQLException ex) {  objUti.mostrarMsgErr_F1(this, ex);   }
   catch(Exception e) {  objUti.mostrarMsgErr_F1(this, e);  }
 return intCodDoc;
}
    

public int getNumeroOrdenGrupo(java.sql.Connection con){ 
int intgrp = 1;
try{
try{
    if (con!=null){

        java.sql.Statement stm =con.createStatement();
        String strSQL= "SELECT Max(ne_secgrp)+1 as grupo  FROM tbm_cabMovInv";
        java.sql.ResultSet rst = stm.executeQuery(strSQL);
        if(rst.next()){
            intgrp = rst.getInt(1);
        }
        rst.close();
        stm.close();
    }
}catch(java.sql.SQLException e){
    con.close();
    objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);                    
}
}catch(Exception e){
objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
}
return intgrp;
}

private boolean generaTrans(java.sql.Connection conn, java.sql.Connection connLoc, java.sql.Connection connRemota, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodBodOri, int intCodBodDes, String strCodItm, double dblanmov ){
boolean blnRes=false;
try{
   if(conn != null ){
     
     if(consultarTansAut(conn, connLoc, connRemota, intCodEmp,  intCodLoc, intCodTipDoc, intCodBodOri, intCodBodDes, strCodItm, dblanmov )){
         blnRes=true;
     }else conn.rollback();

}}catch(java.sql.SQLException e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
   catch(Exception Evt)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
return blnRes; 
}

private boolean consultarTansAut(java.sql.Connection conn, java.sql.Connection connLoc, java.sql.Connection connRemota,  int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodBodOri, int intCodBodDes, String strCodItm, double dblanmov ){
  boolean blnRes=false;
  java.sql.Statement stmLoc, stmLoc01;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String str_MerIEFisBod="N";
  int intCodDoc=0;
  int intNumDoc=0;
  int intSecEmp=0;
  int intSecGrp=0;
  try{
  if(conn!=null){
  
     stmLoc=conn.createStatement();
     stmLoc01=conn.createStatement();
     
     StringBuffer stbTbl1;
      
     double dblTot=0; 
     int intReg=1;
     int intNumFil=1;
     int intControl=0;


     //System.out.println(" REALIZA TRANSFERENCIA...................... ");
     
   
     dblTot=0;
     intReg=1;
     intNumFil=1;
     stbTbl1=new StringBuffer(); 
     intControl=0;
     intCodDoc=0;
     
     
     objInvItm.limpiarObjeto();
     objInvItm.inicializaObjeto();

     if(intConStbBod==1)  stbLisBodItm.append(" UNION ALL ");
     stbLisBodItm.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodBodOri+" AS COBOD, "+strCodItm+" AS COITM, "+dblanmov+" AS NDCAN ");
     intConStbBod=1;

     strSql="select a.co_itm, a.tx_codalt, a.tx_nomitm, a1.tx_descor as tx_unimed,  "+intCodBodOri+" as co_bodori, " +
     " "+intCodBodDes+" as co_boddes,  "+dblanmov+" as nd_can, a.nd_cosuni, ( "+dblanmov+" * a.nd_cosuni) as costot " +
     " from tbm_inv as a " +
     " left join  tbm_var as a1 on (a1.co_reg=a.co_uni) " +
     " where  a.co_emp="+intCodEmp+"  and a.co_itm="+strCodItm;
     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next()){
         
        if(intControl==0) 
           intCodDoc=getCodigoDoc(conn, intCodEmp, intCodLoc, intCodTipDoc );   
     
         dblTot += rstLoc.getDouble("costot");



         strSql="INSERT INTO tbm_detmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, " +
         " tx_codalt, tx_nomitm, tx_unimed, co_bod, nd_can, nd_cosuni, nd_preuni, nd_pordes, st_ivacom, " +
         " nd_cancon,  ne_numfil, nd_tot, tx_codalt2, nd_costot, nd_cospro, " +
         " nd_cosunigrp, nd_costotgrp, nd_candev, st_regrep, co_itmact, st_meringegrfisbod, nd_cannunrec ) " +
         " VALUES( "+intCodEmp+","+intCodLoc+","+intCodTipDoc+", "+intCodDoc+", "+intReg+", "+rstLoc.getInt("co_itm")+"," +
         " '"+rstLoc.getString("tx_codalt")+"', '"+rstLoc.getString("tx_nomitm")+"', '"+rstLoc.getString("tx_unimed")+"',"+
         " "+rstLoc.getInt("co_bodori")+", "+rstLoc.getDouble("nd_can")*-1+", "+rstLoc.getString("nd_cosuni")+", "+rstLoc.getString("nd_cosuni")+"," +
         " 0, 'N', 0, "+intNumFil+", "+rstLoc.getDouble("costot")*-1+", '"+rstLoc.getString("tx_codalt")+"', "+rstLoc.getDouble("costot")*-1+", "+rstLoc.getString("nd_cosuni")+"," +
         " "+rstLoc.getString("nd_cosuni")+", "+rstLoc.getDouble("costot")*-1+",  0, 'I', "+rstLoc.getInt("co_itm")+", " +
         " 'S', 0  ) ; ";
         stbTbl1.append( strSql );
         intReg++;
         str_MerIEFisBod="E";
         objInvItm.generaQueryStock(intCodEmp, Integer.parseInt(strCodItm), rstLoc.getInt("co_bodori"), rstLoc.getDouble("nd_can"),  -1 , "N", "S", str_MerIEFisBod, 1);
         
         
         strSql="INSERT INTO tbm_detmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, " +
         " tx_codalt, tx_nomitm, tx_unimed, co_bod, nd_can, nd_cosuni, nd_preuni, nd_pordes, st_ivacom, " +
         " nd_cancon,  ne_numfil, nd_tot, tx_codalt2, nd_costot, nd_cospro, " +
         " nd_cosunigrp, nd_costotgrp, nd_candev, st_regrep, co_itmact, st_meringegrfisbod, nd_cannunrec ) " +
         " VALUES( "+intCodEmp+","+intCodLoc+","+intCodTipDoc+", "+intCodDoc+", "+intReg+", "+rstLoc.getInt("co_itm")+"," +
         " '"+rstLoc.getString("tx_codalt")+"', '"+rstLoc.getString("tx_nomitm")+"', '"+rstLoc.getString("tx_unimed")+"',"+
         " "+rstLoc.getInt("co_boddes")+", "+rstLoc.getDouble("nd_can")+", "+rstLoc.getString("nd_cosuni")+", "+rstLoc.getString("nd_cosuni")+"," +
         " 0, 'N', 0, "+intNumFil+", "+rstLoc.getDouble("costot")+", '"+rstLoc.getString("tx_codalt")+"', "+rstLoc.getDouble("costot")+", "+rstLoc.getString("nd_cosuni")+"," +
         " "+rstLoc.getString("nd_cosuni")+", "+rstLoc.getDouble("costot")+",  0, 'I', "+rstLoc.getInt("co_itm")+", " +
         " 'S', 0  ) ; ";
         stbTbl1.append( strSql );        
         intReg++;
         str_MerIEFisBod="I";
         objInvItm.generaQueryStock(intCodEmp, Integer.parseInt(strCodItm), rstLoc.getInt("co_boddes"), rstLoc.getDouble("nd_can"),  1 , "N", "S", str_MerIEFisBod, 1);
         
         
         
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

       if(intDocRelEmp==1) stbDocRelEmp.append(" UNION ALL ");
        stbDocRelEmp.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC" );
       intDocRelEmp=1;
          
   
      if( insertarCabTrans(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intNumDoc, dblTot ,  intSecEmp, intSecGrp ) ){
       if(insertarCabDiaTrans( conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intNumDoc ) ){ 
         if(insertarDetDiaTrans( conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, dblTot, intCodBodOri , intCodBodDes ) ){ 
            stmLoc.executeUpdate(stbTbl1.toString());
       }}}
     }
     stbTbl1=null; 
     
     
     
     
      if(!objInvItm.ejecutaActStock(connLoc, 3))
          return false;
         
       if(connRemota!=null){
            //System.out.println("remoto.."+connRemota );
            
           if(!objInvItm.ejecutaActStock(connRemota, 3 ))
             return false;
           if(!objInvItm.ejecutaVerificacionStock(connRemota, 3))
             return false;
       }else{
         if(!objInvItm.ejecutaVerificacionStock(connLoc, 3 ))
            return false;
       }
      objInvItm.limpiarObjeto();
     
     
      
      
      
     blnRes=true;
     stmLoc.close();
     stmLoc=null;
     stmLoc01.close();
     stmLoc01=null;
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }     
  return  blnRes;                                    
}



private boolean insertarCabTrans(java.sql.Connection  conIns, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc, double dlbTot , int intSecEmp, int intSecGrp  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 try{
    if(conIns!=null){ 
      stmLoc=conIns.createStatement();
       
    java.util.Date datFecAux =objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
     
    strSql="INSERT INTO tbm_cabmovinv(co_emp, co_loc, co_tipdoc, co_doc, ne_numdoc, fe_doc, " +
    " nd_sub, nd_tot, nd_poriva, st_reg, fe_ing , co_usring , " +
    " ne_secemp, ne_secgrp, nd_valiva,  co_mnu, st_tipdev, st_coninv, " +
    " st_imp, st_creguirem, st_coninvtraaut,  st_docconmersaldemdebfac , st_regrep   ) " +
    " VALUES ("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intNumDoc+" ,'"+datFecAux+"', " +
    " "+(dlbTot*-1)+", "+(dlbTot*-1)+", 0, 'A', "+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getCodigoUsuario()+", " +
    " "+intSecEmp+", "+intSecGrp+", 0,  779, 'C', 'P',  'S', 'N', 'N', 'N', 'I' ) ";
   
    strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDoc+" WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+" ";
    
    //System.out.println(" "+ strSql );
        
    stmLoc.executeUpdate(strSql);
   
   stmLoc.close();
   stmLoc=null;
   blnRes=true;
}}
catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }

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
catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }

return blnRes; 
}
             
private boolean insertarDetDiaTrans(java.sql.Connection  conIns, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, double dlbTot, int intBodOri, int intBodDes ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 int intCodReg=1;
 int intCtaOri=0;
 int intCtaDes=0;
 
 try{
    if(conIns!=null){ 
      stmLoc=conIns.createStatement();
    
      if(intCodEmp==1){
        if(intBodOri==1)  intCtaOri=407;
        if(intBodOri==3)  intCtaOri=2434;
        if(intBodOri==4)  intCtaOri=2435;
        if(intBodOri==5)  intCtaOri=2499;
        if(intBodOri==6)  intCtaOri=2500;
        if(intBodOri==7)  intCtaOri=2501;
        if(intBodOri==8)  intCtaOri=2502;
        if(intBodOri==9)  intCtaOri=2503;
        if(intBodOri==10)  intCtaOri=2504;
        if(intBodOri==12)  intCtaOri=2639;
        
        if(intBodDes==1)  intCtaDes=407;
        if(intBodDes==3)  intCtaDes=2434;
        if(intBodDes==4)  intCtaDes=2435;
        if(intBodDes==5)  intCtaDes=2499;
        if(intBodDes==6)  intCtaDes=2500;
        if(intBodDes==7)  intCtaDes=2501;
        if(intBodDes==8)  intCtaDes=2502;
        if(intBodDes==9)  intCtaDes=2503;
        if(intBodDes==10)  intCtaDes=2504;
        if(intBodDes==12)  intCtaDes=2639;
        
      }
      
   
      if(intCodEmp==2){
        if(intBodOri==1)  intCtaOri=74;
        if(intBodOri==3)  intCtaOri=75;
        if(intBodOri==4)  intCtaOri=1025;
        if(intBodOri==5)  intCtaOri=1092;
        if(intBodOri==6)  intCtaOri=1093;
        if(intBodOri==7)  intCtaOri=1121;
        if(intBodOri==8)  intCtaOri=1122;
        if(intBodOri==9)  intCtaOri=1123;
        if(intBodOri==10)  intCtaOri=1124;
        if(intBodOri==12)  intCtaOri=1160;

          
        if(intBodDes==1)  intCtaDes=74;
        if(intBodDes==3)  intCtaDes=75;
        if(intBodDes==4)  intCtaDes=1025;
        if(intBodDes==5)  intCtaDes=1092;
        if(intBodDes==6)  intCtaDes=1093;
        if(intBodDes==7)  intCtaDes=1121;
        if(intBodDes==8)  intCtaDes=1122;
        if(intBodDes==9)  intCtaDes=1123;
        if(intBodDes==10)  intCtaDes=1124;
        if(intBodDes==12)  intCtaDes=1160;
      }
      
  
      if(intCodEmp==4){
        if(intBodOri==1)  intCtaOri=383;
        if(intBodOri==2)  intCtaOri=381;
        if(intBodOri==3)  intCtaOri=1992;
        if(intBodOri==4)  intCtaOri=1993;
        if(intBodOri==5)  intCtaOri=2031;
        if(intBodOri==6)  intCtaOri=2032;
        if(intBodOri==7)  intCtaOri=2034;
        if(intBodOri==8)  intCtaOri=2035;
        if(intBodOri==9)  intCtaOri=2036;
        if(intBodOri==11)  intCtaOri=2101;
        

        if(intBodDes==1)  intCtaDes=383;
        if(intBodDes==2)  intCtaDes=381;
        if(intBodDes==3)  intCtaDes=1992;
        if(intBodDes==4)  intCtaDes=1993;
        if(intBodDes==5)  intCtaDes=2031;
        if(intBodDes==6)  intCtaDes=2032;
        if(intBodDes==7)  intCtaDes=2034;
        if(intBodDes==8)  intCtaDes=2035;
        if(intBodDes==9)  intCtaDes=2036;
        if(intBodDes==11)  intCtaDes=2101;


      }
      
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
catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }

return blnRes; 
}
    
        
           


/*******************************************************************************************/   


private boolean verificaBodEmp(java.sql.Connection conn){
  boolean blnRes=true; 
  java.sql.Statement stmLoc; 
  java.sql.ResultSet rstLoc; 
  String strCanSal ="";
  String strSql="";
  String strCodAlt="";
  int intCodItm=0;
  int intNumCol=0;
  double dblPreVta=0;
  double dblCosUni=0;
  double dblDesVta=35;
  double dblCanBod=0; 
  String strCodEmp="";
  int intCodBod=0;
  int intNumFila=0;
  int intCodLocTra=0;
  int intCodTipDocTra=0;

  try{
     if(conn!=null){ 
      stmLoc=conn.createStatement();
      
       for(int i=0; i < tblDat.getRowCount(); i++){ 
        if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null) {
         if(tblDat.getValueAt(i, INT_TBL_ESTBOD)!=null) {
          if(tblDat.getValueAt(i, INT_TBL_ESTBOD).toString().equals("A")){
             
             strCanSal = ((tblDat.getValueAt(i, INT_TBL_CANCOM)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANCOM).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANCOM).toString()));
             dblCanBod = objUti.redondear(strCanSal, 6);
             if(dblCanBod>0){
                 
                intCodBod=Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODBOD).toString());  
                strCodEmp=tblDat.getValueAt(i, INT_TBL_CODEMP).toString(); 
                
                if(objZafParSis.getCodigoEmpresa()==Integer.parseInt(strCodEmp) ){
                    
                    
                  //System.out.println("Transferencia...");
                    
                   strSql="SELECT co_loctra, co_tipdoctra, st_reg FROM tbr_bodemp " +
                   " where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_empper="+objZafParSis.getCodigoEmpresa()+" and co_bodper="+intCodBod;
                   rstLoc = stmLoc.executeQuery(strSql);
                   if(rstLoc.next()){ 
                     intCodLocTra=rstLoc.getInt("co_loctra");
                     intCodTipDocTra=rstLoc.getInt("co_tipdoctra");
                   }
                   rstLoc.close();
                   rstLoc=null;
                  
                    if(!generaTrans(conn, CONN_GLO, CONN_GLO_REM, objZafParSis.getCodigoEmpresa(), intCodLocTra, intCodTipDocTra, intCodBod, intCodBodPre,  objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CODITM)), dblCanBod  ) ){
                       blnRes=false;  break; 
                    }
                    
                    
                    
                }else{
                
                 
                 
                 
                 
                 
                 
                 
                 /*********************************************************************/
                  intNumFila=i;
                  if(objZafParSis.getCodigoEmpresa()==1){
                   if(strCodEmp.equals("2")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=2854";  
                   if(strCodEmp.equals("4")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=3117";  
                  }
                  if(objZafParSis.getCodigoEmpresa()==2){
                   if(strCodEmp.equals("1")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=603";  
                   if(strCodEmp.equals("4")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=498";  
                  }
                  if(objZafParSis.getCodigoEmpresa()==4){
                   if(strCodEmp.equals("2")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=789";  
                   if(strCodEmp.equals("1")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=1039";  
                  }
                  rstLoc = stmLoc.executeQuery(strSql);
                  if(rstLoc.next()) 
                     dblDesVta=rstLoc.getDouble("nd_maxDes");
                  rstLoc.close();
                  rstLoc=null;
                /*********************************************************************/
                  strSql="SELECT a.co_emp, a.co_itm, a1.nd_prevta1, nd_stkact, a1.tx_codalt FROM tbm_equinv as a " +
                  " inner join tbm_inv as a1 on (a1.co_emp=a.co_emp and a1.co_itm=a.co_itm) "+
                  " WHERE a.co_emp="+strCodEmp+" AND a.co_itmmae = ( " +
                  " select co_itmmae from tbm_equinv where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_itm="+objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CODITM))+" ) ";
                  rstLoc = stmLoc.executeQuery(strSql);
                  if(rstLoc.next()){ 
                     intCodItm=rstLoc.getInt("co_itm");
                     dblPreVta=rstLoc.getDouble("nd_prevta1");
                     strCodAlt=rstLoc.getString("tx_codalt");
                  }
                  rstLoc.close();
                  rstLoc=null;
              
                  if(dblPreVta > 0 ){

                   int intCodBodDes =  _getCodigoBodDes(conn, objZafParSis.getCodigoEmpresa(), intCodBodPre, Integer.parseInt(strCodEmp) );
                   if(!(intCodBodDes== -1)){

                   dblCosUni  =  dblPreVta - ( dblPreVta * ( dblDesVta / 100 ) );
                   //System.out.println("Gen Ven");
                   if(generaVen(conn, Integer.parseInt(strCodEmp), intCodBod, intNumFila, dblCanBod, dblDesVta, intCodItm, dblPreVta, intCodBodDes )){
                    //System.out.println("Gen Com");
                     if(generaCom(conn, Integer.parseInt(strCodEmp), objZafParSis.getCodigoEmpresa(), intCodBodPre, intNumFila, dblCanBod, intCodBod, dblCosUni )){  
                         //System.out.println("OKIII");
                         blnRes=true; 
                     }else { blnRes=false;  break; }
                    }else { blnRes=false;  break; } 

                   }else{  MensajeInf("PROBLEMA AL OBTENER BODEGA DESTINO.. ");  blnRes=false;  break; }

                }else{  MensajeInf("El item "+strCodAlt+" no tiene precio de lista.");  blnRes=false;  break; } 



                   
                  
                  
                }    
                  
                  
                  
                  
                  
                  
                  
             } 
     }}}}
      
    stmLoc.close();
    stmLoc=null;
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
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

  }}catch(java.sql.SQLException Evt){ intCodBodDes=-1;    }
    catch(Exception Evt)   { intCodBodDes=-1;   }
 return intCodBodDes;
}

   


private boolean generaVen(java.sql.Connection conn, int intCodEmp, int intCodBod, int intNumFil, double dblCan, double intDesVta, int intCodItm, double dblPreVta, int intCodBodDes ){
  boolean blnRes=false;  
  java.sql.Statement stmLoc; 
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intCodLoc=0;
  int intCodTipDoc=0;
  int intCodDoc=0;
  String strEstConf="C";
   try{
    if(conn!=null){ 
      stmLoc=conn.createStatement();
    
      strSql="SELECT co_locven as co_loc, co_tipdocven FROM tbr_bodemp WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND" +
      " co_loc="+objZafParSis.getCodigoLocal()+" AND co_empper="+intCodEmp+" AND co_bodper="+intCodBod;
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

       if(intConStbBod==1)  stbLisBodItm.append(" UNION ALL ");
       stbLisBodItm.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodBod+" AS COBOD, "+
       objInvItm.getIntDatoValidado(tblDat.getValueAt(intNumFil, INT_TBL_CODITM))+" AS COITM, "+dblCan+" AS NDCAN ");
       intConStbBod=1;


 /*************************************************************************/

       
      String strMerIngEgr="E";
      String strIngEgrFisBod="N";
      if(verificaIngEgrFisBod(intCodEmp, intCodBod)){
        strMerIngEgr="S";
        strIngEgrFisBod="S";
      }


 /*************************************************************************/


      if(generaVenCab(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmp, 1, dlbSub, dlbValIva, dlbTot, strEstConf , intCodBodDes  )){
       if(generaVenDet(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBod ,intNumFil,  dblCan, intCodItm, -1 , intDesVta, dblPreVta, strIngEgrFisBod )){
        if(actualizaStock(CONN_GLO, CONN_GLO_REM, intCodEmp, intCodBod , dblCan, -1, intCodItm, strMerIngEgr, "E" )){
           blnRes=true;   
      }}}
      objInvItm.limpiarObjeto();
    stmLoc.close();
    stmLoc=null;
      
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}           
   




private boolean verificaIngEgrFisBod(int intCodEmpCom, int intCodBodVen ){
 boolean blnRes=true;
 try{
 if(objZafParSis.getCodigoEmpresa()==1){
    if(intCodEmpCom==1){ if( intCodBodVen==1 ) blnRes=false;  }
    if(intCodEmpCom==2){ if( intCodBodVen==3 ) blnRes=false; }
    if(intCodEmpCom==4){ if( intCodBodVen==2 ) blnRes=false; }
 }

 if(objZafParSis.getCodigoEmpresa()==2){
   if(objZafParSis.getCodigoLocal()==1){
    if(intCodEmpCom==1){ if( intCodBodVen==8 ) blnRes=false; }
    if(intCodEmpCom==2){ if( intCodBodVen==1 ) blnRes=false; }
    if(intCodEmpCom==4){ if( intCodBodVen==7 ) blnRes=false; }
   }
   if(objZafParSis.getCodigoLocal()==4){
    if(intCodEmpCom==1){ if( intCodBodVen==10 ) blnRes=false; }
    if(intCodEmpCom==2){ if( intCodBodVen==4 ) blnRes=false; }
    if(intCodEmpCom==4){ if( intCodBodVen==9 ) blnRes=false; }
   }
   if(objZafParSis.getCodigoLocal()==6){
    if(intCodEmpCom==1){ if( intCodBodVen==12 ) blnRes=false; }
    if(intCodEmpCom==2){ if( intCodBodVen==12 ) blnRes=false; }
    if(intCodEmpCom==4){ if( intCodBodVen==11 ) blnRes=false; }
   }
   if(objZafParSis.getCodigoLocal()==10){
    if(intCodEmpCom==1){ if( intCodBodVen==28 ) blnRes=false; }
    if(intCodEmpCom==2){ if( intCodBodVen==28 ) blnRes=false; }
    if(intCodEmpCom==4){ if( intCodBodVen==28 ) blnRes=false; }
   }
 }

 if(objZafParSis.getCodigoEmpresa()==4){
    if(intCodEmpCom==1){ if( intCodBodVen==7 )  blnRes=false; }
    if(intCodEmpCom==2){ if( intCodBodVen==9 )  blnRes=false; }
    if(intCodEmpCom==4){ if( intCodBodVen==1 )  blnRes=false; }
 }
 
 }catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
  return blnRes;
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

 }}catch(java.sql.SQLException ex) {  objUti.mostrarMsgErr_F1(this, ex);   }
   catch(Exception e) {  objUti.mostrarMsgErr_F1(this, e);  }
 return intUltNumDoc;
}

             

  
   
private boolean generaVenCab(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpCom,  int TipMov, double dlbSub, double dlbValIva, double dlbTot, String strEstConf, int intCodBodDes  ){
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

      
      datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
      strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());      
      
     if(TipMov==1){
       if(objZafParSis.getCodigoEmpresa()==1){
          if(intCodEmp==2) { strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=2854";  }
          if(intCodEmp==4) { strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=3117";  }
       }
       if(objZafParSis.getCodigoEmpresa()==2){
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
       if(objZafParSis.getCodigoEmpresa()==4){
          if(intCodEmp==2){ strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=789";  }
          if(intCodEmp==1){ strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=1039";  }
        }
    }
    if(TipMov==2){
       if(objZafParSis.getCodigoEmpresa()==1){
          if(intCodEmpCom==2){
              if(intCodTipDoc==130){
                  strCui="MANTA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
                  " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_cli=603";

              }else if(intCodTipDoc==165){
                  strCui="SANTO DOMINGO";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
                  " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_cli=603";
              }
              else if(intCodTipDoc==224){
                  strCui="CUENCA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=4 ) " +
                  " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_cli=603";
              }
              else{
                  strCui="QUITO ";
                  strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=603";
              }

          }
          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=1039";  
       }
       if(objZafParSis.getCodigoEmpresa()==2){
          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=2854";  
          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=789";  
       }
       if(objZafParSis.getCodigoEmpresa()==4){
          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=3117";  
          if(intCodEmpCom==2){
             
              if(intCodTipDoc==130){
                  strCui="MANTA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
                  " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_cli=498";

              }else if(intCodTipDoc==165){
                  strCui="SANTO DOMINGO";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
                  " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_cli=498";
              }
              else if(intCodTipDoc==224){
                  strCui="CUENCA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=4 ) " +
                  " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_cli=498";
              }
              else{
                  strCui="QUITO ";
                  strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=498";
              } 

          
          }

       }
    }   
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
         
         //** intNumDoc=getUltNumDoc(conn, intCodEmp, intCodLoc, intCodTipDoc);

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
          " '' ,'RELIZADO MANUALMENTE' , "+dlbSub+" ,0 , "+dlbTot+", "+dlbValIva+" , 1 ,'Contado', '"+strFecSistema+"', "+
          objZafParSis.getCodigoUsuario()+", '"+strFecSistema+"', "+objZafParSis.getCodigoUsuario()+" , null, null," +
          " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', 45 "+
          " ,'S', 'S', '"+strEstConf+"', "+(intCodBodDes==0?null:String.valueOf(intCodBodDes))+" )";
         //*** strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDoc+" WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc;
          stmLoc.executeUpdate(strSql);

      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      blnRes=true;
      
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
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
   }catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
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

      stbDevInvTemp.append( objInvItm.getQueryEjecutaActStock() +" ; " );


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
   }catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}



private boolean actualizaStockConVenAut(java.sql.Connection conn, java.sql.Connection connRemota, int intCodEmp, int intCodBod,  double dblCan, int intTipMov, int intCodItm, String strMerIngEgr, String str_MerIEFisBod ){
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

       stbDevInvTemp.append( objInvItm.getQueryEjecutaActStock() +" ; " );


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
   }catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}



   
     
private boolean generaVenDet(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intNumFil, double dblCan, int intCodItm, int intTipMov , double intDesVta, double dblPreVta, String strIngEgrFisBod ){
  boolean blnRes=false;  
  java.sql.Statement stmLoc; 
  String strSql="";
  String strPreCos="";
  try{
    if(conn!=null){ 
      stmLoc=conn.createStatement();
       
       //strPreCos = ((tblDat.getValueAt(intNumFil, INT_TBL_PREVTA)==null)?"0":(tblDat.getValueAt(intNumFil, INT_TBL_PREVTA).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_PREVTA).toString()));
       //double dblPreVta = objUti.redondear(strPreCos, 2);
       strPreCos = ((tblDat.getValueAt(intNumFil, INT_TBL_COSUNI)==null)?"0":(tblDat.getValueAt(intNumFil, INT_TBL_COSUNI).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_COSUNI).toString()));
       double dblCosUni = objUti.redondear(strPreCos, 2);
        
       double dblValDes = ((dblCan * dblPreVta)==0)?0:((dblCan * dblPreVta) * (intDesVta / 100));
       double dblTotal = (dblCan * dblPreVta)- dblValDes;  
              dblTotal =  objUti.redondear(dblTotal,2);
          
        strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+(intNumFil+1)+", " +
        " '"+((tblDat.getValueAt(intNumFil, INT_TBL_CODALT)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODALT).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODALT).toString()))+"' "+
        " ,'"+((tblDat.getValueAt(intNumFil, INT_TBL_CODALT)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODALT).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODALT).toString()))+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        ", '"+((tblDat.getValueAt(intNumFil, INT_TBL_NOMITM)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_NOMITM).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_NOMITM).toString()))+"' "+
        ", '"+((tblDat.getValueAt(intNumFil, INT_TBL_UNIMED)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_UNIMED).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_UNIMED).toString()))+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+(dblCan*dblCosUni)+","+dblPreVta+", "+intDesVta+", 'S' "+
        " ,"+(dblCan*dblCosUni)+", 'I' , '"+strIngEgrFisBod+"', 0 ,"+dblCosUni+" ) ";
        stmLoc.executeUpdate(strSql);
        strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_sal=nd_sal-"+dblCan+" WHERE co_emp="+intCodEmp+" AND " +
        " co_itm="+intCodItm+" AND co_emprel="+objZafParSis.getCodigoEmpresa(); 
        stmLoc.executeUpdate(strSql);

         if(intDatVen==1) stbDatVen.append(" UNION ALL ");
          stbDatVen.append("SELECT "+intCodEmp+" as coemp, "+intCodLoc+" as coloc, "+intCodTipDoc+"  as cotipdoc, "+intCodDoc+" AS codoc, "+intCodBod+" as cobod  ");
         intDatVen=1;

      stmLoc.close();
      stmLoc=null;
      blnRes=true;
      
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}           




 
private boolean generaComDet(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intNumFil, double dblCan, int intCodItm, int intTipMov, int intCodEmpCom, double dblCosUni, String strIngEgrFisBod, String strNomBodVen  ){
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
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, tx_obs1  ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+(intNumFil+1)+", " +
        " '"+((tblDat.getValueAt(intNumFil, INT_TBL_CODALT)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODALT).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODALT).toString()))+"' "+
        " ,'"+((tblDat.getValueAt(intNumFil, INT_TBL_CODALT)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODALT).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODALT).toString()))+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        ", '"+((tblDat.getValueAt(intNumFil, INT_TBL_NOMITM)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_NOMITM).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_NOMITM).toString()))+"' "+
        ", '"+((tblDat.getValueAt(intNumFil, INT_TBL_UNIMED)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_UNIMED).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_UNIMED).toString()))+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+dblTotal+","+dblCosUni+", "+dblPorDes+", 'S' "+
        " ,"+dblTotal+", 'I' , '"+strIngEgrFisBod+"', 0 ,"+dblCosUni+", '"+strNomBodVen+"' ) ";
        stmLoc.executeUpdate(strSql);

        if(intDatVen==1) stbDatVen.append(" UNION ALL ");
          stbDatVen.append("SELECT "+intCodEmp+" as coemp, "+intCodLoc+" as coloc, "+intCodTipDoc+"  as cotipdoc, "+intCodDoc+" AS codoc, "+intCodBod+" as cobod  ");
        intDatVen=1;

        strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_sal=nd_sal+"+dblCan+" WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND " +
        " co_itm="+intCodItm+" AND co_emprel="+intCodEmpCom; 
        stmLoc.executeUpdate(strSql);
      stmLoc.close();
      stmLoc=null;
      blnRes=true;
      
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}           







private boolean generaCom(java.sql.Connection conn, int intCodEmpCom, int intCodEmp, int intCodBod, int intNumFil, double dblCan, int intCodBodVen, double dblCosUni ){
  boolean blnRes=false;  
  java.sql.Statement stmLoc; 
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intCodLoc=0;
  int intCodTipDoc=0;
  int intCodDoc=0;
  int intCodItm=0;
  String strEstConf="P";
   try{
    if(conn!=null){ 
      stmLoc=conn.createStatement();
    
      intCodLoc=objZafParSis.getCodigoLocal();

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
      

 




 if(objZafParSis.getCodigoEmpresa()==1){
    if(intCodEmpCom==1){
     if( intCodBodVen==1 ) strEstConf="F";
    }
    if(intCodEmpCom==2){
     if( intCodBodVen==3 ) strEstConf="F";
    }
    if(intCodEmpCom==4){
     if( intCodBodVen==2 ) strEstConf="F";
    }
 }

  if(objZafParSis.getCodigoEmpresa()==2){
        if(objZafParSis.getCodigoLocal()==1){
         if(intCodEmpCom==1){
          if( intCodBodVen==8 ) strEstConf="F";
         }
         if(intCodEmpCom==2){
          if( intCodBodVen==1 ) strEstConf="F";
         }
         if(intCodEmpCom==4){
          if( intCodBodVen==7 ) strEstConf="F";
         }
        }
        if(objZafParSis.getCodigoLocal()==4){
         if(intCodEmpCom==1){
          if( intCodBodVen==10 ) strEstConf="F";
         }
         if(intCodEmpCom==2){
          if( intCodBodVen==4 ) strEstConf="F";
         }
         if(intCodEmpCom==4){
          if( intCodBodVen==9 ) strEstConf="F";
         }
        }
        if(objZafParSis.getCodigoLocal()==6){
         if(intCodEmpCom==1){
          if( intCodBodVen==12 ) strEstConf="F";
         }
         if(intCodEmpCom==2){
          if( intCodBodVen==12 ) strEstConf="F";
         }
         if(intCodEmpCom==4){
          if( intCodBodVen==11 ) strEstConf="F";
         }
        }
        if(objZafParSis.getCodigoLocal()==10){
         if(intCodEmpCom==1){
          if( intCodBodVen==28 ) strEstConf="F";
         }
         if(intCodEmpCom==2){
          if( intCodBodVen==28 ) strEstConf="F";
         }
         if(intCodEmpCom==4){
          if( intCodBodVen==28 ) strEstConf="F";
         }
        }
 }
        
 if(objZafParSis.getCodigoEmpresa()==4){
    if(intCodEmpCom==1){
     if( intCodBodVen==7 ) strEstConf="F";
    }
    if(intCodEmpCom==2){
     if( intCodBodVen==9 ) strEstConf="F";
    }
    if(intCodEmpCom==4){
     if( intCodBodVen==1 ) strEstConf="F";
    }
 }

      strSql="SELECT co_loccom as co_loc, co_tipdoccom FROM tbr_bodemp WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND" +
      " co_loc="+objZafParSis.getCodigoLocal()+" AND co_empper="+intCodEmpCom+" AND co_bodper="+intCodBodVen;
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
     
      intCodItm=Integer.parseInt( objInvItm.getIntDatoValidado(tblDat.getValueAt(intNumFil, INT_TBL_CODITM)) );
      objInvItm.limpiarObjeto();


      double dblPorDes=0;
      double dblValDes = ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
      double dblTotal = (dblCan * dblCosUni)- dblValDes;
      double dlbSub =  objUti.redondear(dblTotal,2);
      double dlbValIva=  dlbSub * (bldivaEmp/100);
      double dlbTot=  dlbSub + dlbValIva;

      String strMerIngEgr="E";
      String strIngEgrFisBod="N";
      if(verificaIngEgrFisBod(intCodEmpCom, intCodBodVen)){
        strMerIngEgr="S";
        strIngEgrFisBod="S";
      }


      if(generaVenCab(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmpCom , 2, dlbSub, dlbValIva, dlbTot, strEstConf, 0 )){
       if(generaComDet(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBod ,intNumFil,  dblCan, intCodItm, 1, intCodEmpCom, dblCosUni, strIngEgrFisBod, strNomBodVen )){
        if(actualizaStock(CONN_GLO, CONN_GLO_REM, intCodEmp, intCodBod , dblCan, 1, intCodItm , strMerIngEgr, "I" )){
           blnRes=true;   
      }}}
      objInvItm.limpiarObjeto();
    stmLoc.close();
    stmLoc=null;
      
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}           

   
   
  












private boolean validaditm(){
   boolean blnRes=true;  
   String strCodItm="",strCodAlt="";
    for(int i=0; i<tblDat.getRowCount(); i++){
     if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null) { 
      if( ( (tblDat.getValueAt(i, INT_TBL_CODEMP)==null) || (tblDat.getValueAt(i, INT_TBL_CODEMP).toString().equals("")) )   ) {
         strCodItm = tblDat.getValueAt(i, INT_TBL_CODITM).toString();
         strCodAlt = tblDat.getValueAt(i, INT_TBL_NOMEMP).toString();
         if(verificaCanTotItmAce(strCodItm)){
            MensajeInf("El item "+strCodAlt+" no cumple con la  cantidad es insuficiente para la venta."); 
            blnRes=false;  
            break;
        }   
     }}}
  return blnRes;
}         




private boolean verificaCanTotItmAce(String strCodItm){
  boolean blnRes=false; 
  double dblCanTot=0;
  double dlbCanTotSal=3; 
   
   dlbCanTotSal = getCanFalItm(strCodItm);  
   dblCanTot = getTotComItm(strCodItm);
      
  if( dblCanTot > dlbCanTotSal ) return true;
  if( dblCanTot < dlbCanTotSal ) return true;
 return blnRes;   
}




   



       
private void cerrarVen(){
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
        System.gc();
        blnAcepta=false;
        dispose();
    }
}
    

   
 public boolean acepta(){
   return blnAcepta;
 }
 
    
 
 public String GetCamSel(int Idx){
        if(!(Str_RegSel==null)){
            if(Idx <= 0 || Idx > Str_RegSel.length)
                return "El parametro debe ser entre 1 y " + Integer.toString(Str_RegSel.length) ;
            else
                return Str_RegSel[Idx-1];
        }else{
            return "";
        }
   } 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.ButtonGroup butradgrp;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBut;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panSubBot;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTbl;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
    
     protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }
    
     
}
