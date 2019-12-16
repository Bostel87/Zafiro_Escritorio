/*
 * pantalladialogo.java
 *
 * Created on 13 de agosto de 2008, 10:45
 */
  
package Ventas.ZafVen01;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
  
/**
 * @author  jayapata
 */
public class ZafVen01_07 extends javax.swing.JDialog {
    Connection CONN_GLO=null, conRemGlo=null;
    ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLbl2, objTblCelRenLbl3, objTblCelRenLbl4;
   // private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt; 
    ZafParSis objZafParSis;
    private ZafTblMod objTblMod;
    ZafInvItm objInvItm;  // Para trabajar con la informacion de tipo de documento
    ZafUtil objUti;    
    
    final int INT_TBL_LINEA =0; 
    final int INT_TBL_CODITM=1;
    final int INT_TBL_CODEMP=2;
    final int INT_TBL_NOMEMP=3;
    final int INT_TBL_NOMBOD=4;
    final int INT_TBL_STKACT=5;
    final int INT_TBL_ESTBOD=6;
    final int INT_TBL_CANCOM=7;
    final int INT_TBL_CANING=8;       
    
    String strCodItmBus="";
    int[][] intColBodEmp = new int[30][2];
     
    Vector vecCab=new Vector(); 
    ZafVenCon objVenConCLi;
    ZafVenCon objVenConVen; 
    String strCodCli="";
    String strDesCli="";
    String strCodSol="";
    String strDesSol="";
    String strTipPer_emp="";
    double bldivaEmp=0;
    int intCodBodPre=0;
    int intCodTipPerEmp=0;
    int INTCODREGCEN=0;

    private String Str_RegSel[]; 
    public boolean blnAcepta = false; 
    ArrayList arlAuxColEdi=new ArrayList();
     
    /** Creates new form pantalladialogo */
    public ZafVen01_07(java.awt.Frame parent, boolean modal ,Librerias.ZafParSis.ZafParSis ZafParSis, String  strCodItm, java.sql.Connection conn, int intConCenn ) {
       super(parent, modal);
       try{ 
        this.objZafParSis = ZafParSis;
        objUti = new ZafUtil();
        objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);
            
        objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl2 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl3 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl4 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        
        initComponents();
        CONN_GLO=conn;
        strCodItmBus=strCodItm;
        INTCODREGCEN=intConCenn;
        cargarTipEmp();
        this.setTitle( objZafParSis.getNombreMenu() );
        lblTit.setText( objZafParSis.getNombreMenu());
        
        }catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);  } 
    }
    
private void cargarTipEmp(){
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
      cargarConn();
      
  } 
  
    public boolean abrirConRem(){
        boolean blnres=false;
        try{
            int intIndEmp=INTCODREGCEN;
            if(intIndEmp != 0){
                conRemGlo=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(intIndEmp), objZafParSis.getUsuarioBaseDatos(intIndEmp), objZafParSis.getClaveBaseDatos(intIndEmp));
                conRemGlo.setAutoCommit(false);
            }
            blnres=true;
        }
        catch (java.sql.SQLException e) {
            mostrarMsg("NO SE PUEDE ESTABLECER LA CONEXION REMOTA CON LA BASE CENTRAL..");
            return false;
        }
        return blnres;
    }


      private void mostrarMsg(String strMsg) {
        //javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.OK_OPTION);
    }





private boolean cargarConn(){
  boolean blnRes=false;
  java.sql.Connection conn;
  try{
  if(INTCODREGCEN==0){
       conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
       if(conn!=null){

            cargarDat(conn);

         conn.close();
         conn=null;
       }
  }else{

      if(!abrirConRem())
        return false;

           cargarDat(conRemGlo);
      
       if(conRemGlo!=null) {
          conRemGlo.close();
          conRemGlo=null;
        }
  }

 }catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
   catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

 
private boolean cargarDat(java.sql.Connection conn){
 boolean blnRes=false;
 
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc, rstLoc01;
 String strSql="";
 Vector vecData;
 try{

   //conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn!=null){

      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();
      
      vecData = new Vector();
      
      
      strSql="SELECT a5.co_itmmae, a.co_emp, a5.co_itm, a.tx_codalt, a.tx_nomitm  "+
      " FROM tbm_inv AS a "+
      " LEFT JOIN tbm_var AS var ON(var.co_reg=a.co_uni) "+
      " INNER JOIN tbm_equinv AS a5 ON(a5.co_emp=a.co_emp and a5.co_itm=a.co_itm ) "+
      " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_itm in ( "+strCodItmBus+" ) ";
      System.out.println("ZafVen01_07.cargarDat: "+ strSql );
      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){
         
         java.util.Vector vecReg2 = new java.util.Vector();
          vecReg2.add(INT_TBL_LINEA,"");
          vecReg2.add( INT_TBL_CODITM, rstLoc.getString("co_itm") );
          vecReg2.add( INT_TBL_CODEMP, "" );
          vecReg2.add( INT_TBL_NOMEMP, rstLoc.getString("tx_codalt") );
          vecReg2.add( INT_TBL_NOMBOD, rstLoc.getString("tx_nomitm") );
          vecReg2.add( INT_TBL_STKACT,"" );
          vecReg2.add( INT_TBL_ESTBOD,"" );
          vecReg2.add( INT_TBL_CANCOM,"" );
          vecReg2.add( INT_TBL_CANING, "" );
         vecData.add(vecReg2);    
          
//       strSql="SELECT a.co_emp, a.co_loc, a.co_empper, a2.tx_nom, a.co_bodper, a3.tx_nom as nombod, a.st_reg " +
//       ",( " +
//       " SELECT nd_stkact FROM tbm_invbod AS x " +
//       " INNER JOIN tbm_equinv AS x1 ON(x1.co_emp=x.co_emp and x1.co_itm=x.co_itm ) " +
//       " INNER JOIN tbm_equinv AS x2 ON(x2.co_emp=x1.co_emp and x2.co_itm=x1.co_itm) " +
//       " WHERE x.co_emp=a.co_empper " +
//       " AND  x.co_bod=a.co_bodper  AND  x2.co_itmmae = "+rstLoc.getString("co_itmmae")+" " +
//       " ) as stk " +
//       ",( " +
//       " SELECT nd_caningbod FROM tbm_invbod AS x " +
//       " INNER JOIN tbm_equinv AS x1 ON(x1.co_emp=x.co_emp and x1.co_itm=x.co_itm ) " +
//       " INNER JOIN tbm_equinv AS x2 ON(x2.co_emp=x1.co_emp and x2.co_itm=x1.co_itm) " +
//       " WHERE x.co_emp=a.co_empper " +
//       " AND  x.co_bod=a.co_bodper  AND  x2.co_itmmae = "+rstLoc.getString("co_itmmae")+" " +
//       " ) as caning " +
//       " FROM tbr_bodemp as a " +
//       " inner join tbm_emp as a2 ON (a2.co_emp=a.co_empper) " +
//       " inner join tbm_bod as a3 ON (a3.co_emp=a.co_empper and a3.co_bod=a.co_bodper) " +
//       " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" order by a.co_empper, a.co_bodper  ";
                    
/* strSql=" select  sum(x.stk) as stk , sum(x.caning) as caning,  x.st_reg,   x2.tx_nom,  x1.co_bodgrp  from ( "+
 " SELECT a.co_emp, a.co_loc, a.co_empper, a2.tx_nom, a.co_bodper, a3.tx_nom as nombod, a.st_reg  "+
 " ,(  SELECT nd_stkact FROM tbm_invbod AS x   "+
 " INNER JOIN tbm_equinv AS x1 ON(x1.co_emp=x.co_emp and x1.co_itm=x.co_itm )   "+
 " INNER JOIN tbm_equinv AS x2 ON(x2.co_emp=x1.co_emp and x2.co_itm=x1.co_itm)   "+
 " WHERE x.co_emp=a.co_empper  AND  x.co_bod=a.co_bodper  AND  x2.co_itmmae = "+rstLoc.getString("co_itmmae")+"  ) as stk  "+
 " ,(  SELECT nd_caningbod FROM tbm_invbod AS x  INNER JOIN tbm_equinv AS x1 ON(x1.co_emp=x.co_emp and x1.co_itm=x.co_itm )  "+
 "  INNER JOIN tbm_equinv AS x2 ON(x2.co_emp=x1.co_emp and x2.co_itm=x1.co_itm)   "+
 "  WHERE x.co_emp=a.co_empper  AND  x.co_bod=a.co_bodper  AND  x2.co_itmmae = "+rstLoc.getString("co_itmmae")+"  ) as caning   "+
 "  FROM tbr_bodemp as a   "+
 "  inner join tbm_emp as a2 ON (a2.co_emp=a.co_empper)   "+
 "  inner join tbm_bod as a3 ON (a3.co_emp=a.co_empper and a3.co_bod=a.co_bodper)   "+
 "  where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" order by a.co_empper, a.co_bodper   "+
 " ) as x  "+
 " inner join  tbr_bodEmpBodGrp as x1 on (x1.co_emp=x.co_empper and x1.co_bod=x.co_bodper ) "+
 " inner join tbm_bod as x2 ON (x2.co_emp=x1.co_empgrp and x2.co_bod=x1.co_bodgrp )   "+
 " group by  x.st_reg, x2.tx_nom, x1.co_bodgrp  order by co_bodgrp ";*/

        strSql=" select sum(stk) as stk, sum(caning) as caning, st_reg, tx_nom, co_bodgrp from ( " +
        " select  sum(x.stk) as stk , sum(x.caning) as caning,  x.st_reg,   x2.tx_nom,  x1.co_bodgrp  from ( "+
        " SELECT a.co_emp, a.co_loc, a.co_empper, a2.tx_nom, a.co_bodper, a3.tx_nom as nombod, a.st_reg, "+
        " ( SELECT /*nd_stkact PARA VENTA nd_canDis JoseMario 3/Jun/2016*/ nd_canDis FROM tbm_invbod AS x "+
        " INNER JOIN tbm_equinv AS x1 ON(x1.co_emp=x.co_emp and x1.co_itm=x.co_itm ) "+
        " INNER JOIN tbm_equinv AS x2 ON(x2.co_emp=x1.co_emp and x2.co_itm=x1.co_itm)   "+
        " WHERE x.co_emp=a.co_empper  AND  x.co_bod=a.co_bodper  AND  x2.co_itmmae = "+rstLoc.getString("co_itmmae")+"  ) as stk  "+
        " ,(  SELECT nd_caningbod FROM tbm_invbod AS x  INNER JOIN tbm_equinv AS x1 ON(x1.co_emp=x.co_emp and x1.co_itm=x.co_itm )  "+
        "  INNER JOIN tbm_equinv AS x2 ON(x2.co_emp=x1.co_emp and x2.co_itm=x1.co_itm)   "+
        "  WHERE x.co_emp=a.co_empper  AND  x.co_bod=a.co_bodper  AND  x2.co_itmmae = "+rstLoc.getString("co_itmmae")+"  ) as caning   "+
        "  FROM tbr_bodemp as a   "+
        "  inner join tbm_emp as a2 ON (a2.co_emp=a.co_empper)   "+
        "  inner join tbm_bod as a3 ON (a3.co_emp=a.co_empper and a3.co_bod=a.co_bodper)   "+
        "  where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" AND a.st_reg IN ('A','U','C','V') order by a.co_empper, a.co_bodper   "+
        " ) as x  "+
        " inner join  tbr_bodEmpBodGrp as x1 on (x1.co_emp=x.co_empper and x1.co_bod=x.co_bodper ) "+
        " inner join tbm_bod as x2 ON (x2.co_emp=x1.co_empgrp and x2.co_bod=x1.co_bodgrp )   "+
        " group by  x.st_reg, x2.tx_nom, x1.co_bodgrp "
                //+ " order by co_bodgrp "
//        + " union all "
//        + " select sum((a4.nd_stkact)) as stk, sum((a4.nd_caningbod)) as caning, 'A' :: text as st_reg, a3.tx_nom, a6.co_bodgrp "
//        + " from tbr_bodloc as a "
//        + " inner join tbm_bod as a3 on (a3.co_emp=a.co_emp and a3.co_bod=a.co_bod ) "
//        + " left outer join tbm_invbod as a4 on (a4.co_emp=a3.co_emp and a4.co_bod=a3.co_bod ) "
//        + " inner join tbm_equinv as a5 on (a5.co_emp=a4.co_emp and a5.co_itm=a4.co_itm ) "
//        + " inner join tbr_bodEmpBodGrp as a6 on (a6.co_emp=a.co_emp and a6.co_bod=a.co_bod ) "
//        + " where a.co_emp="+objZafParSis.getCodigoEmpresa() + " "
//        + " and a.co_loc="+objZafParSis.getCodigoLocal()+" "
//        + " and a.st_reg='P' "
//        + " and a5.co_itmmae="+rstLoc.getString("co_itmmae")+" "
//        + " group by a3.tx_nom, a6.co_bodgrp"
        + " ) as y "
        + " group by st_reg, tx_nom, co_bodgrp "
        + " order by 5 ";

      System.out.println("ZafVen01_07.cargarDat: "+ strSql );

       rstLoc01=stmLoc01.executeQuery(strSql);
       while(rstLoc01.next()){ 
          
          java.util.Vector vecReg = new java.util.Vector();
          vecReg.add(INT_TBL_LINEA,"");
          vecReg.add( INT_TBL_CODITM, rstLoc.getString("co_itm") );
          vecReg.add( INT_TBL_CODEMP, "2"); //rstLoc01.getString("co_empper") );
          vecReg.add( INT_TBL_NOMEMP, rstLoc01.getString("co_bodgrp") );  // rstLoc01.getString("tx_nom") );
          vecReg.add( INT_TBL_NOMBOD, rstLoc01.getString("tx_nom") ); //rstLoc01.getString("nombod") );
          vecReg.add( INT_TBL_STKACT, rstLoc01.getString("stk") );
          vecReg.add( INT_TBL_ESTBOD, rstLoc01.getString("st_reg") );
          vecReg.add( INT_TBL_CANCOM,"" );
         // if(_getVerificarBodImpEmp(rstLoc01.getInt("co_empper"), rstLoc01.getInt("co_bodper") ))
         //     vecReg.add( INT_TBL_CANING, "" );
         // else
            vecReg.add( INT_TBL_CANING, /*rstLoc01.getString("caning")*/ "" );

         vecData.add(vecReg); 
       }
       rstLoc01.close();
       rstLoc01=null;
       
         
         vecReg2 = new java.util.Vector();
          vecReg2.add(INT_TBL_LINEA,"");
          vecReg2.add( INT_TBL_CODITM, "" );
          vecReg2.add( INT_TBL_CODEMP, "0" );
          vecReg2.add( INT_TBL_NOMEMP, "" );
          vecReg2.add( INT_TBL_NOMBOD, "" );
          vecReg2.add( INT_TBL_STKACT,"" );
          vecReg2.add( INT_TBL_ESTBOD,"" );
          vecReg2.add( INT_TBL_CANCOM,"" );
          vecReg2.add( INT_TBL_CANING, "" );
         vecData.add(vecReg2);    
         
      }  
      rstLoc.close();
      rstLoc=null;
               
      objTblMod.setData(vecData);
      tblDat .setModel(objTblMod);         
      
      //Configurar JTable: Establecer columnas editables.
      //OCULTAR CANTIDAD POR INGRESAR
      objTblMod.addSystemHiddenColumn(INT_TBL_CANING, tblDat);
      
      
      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;
      
   // conn.close();
   // conn=null;
     lblMsgSis.setText("Listo");
     pgrSis.setValue(0);
     pgrSis.setIndeterminate(false);    
     
      objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
     
    blnRes=true;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;          
}   
    


private boolean _getVerificarBodImpEmp(int intCodEmp, int intCodBod){
  boolean blnRes=false;  
  if(intCodEmp==1){
      if(intCodBod==3) blnRes=true;
  }else if(intCodEmp==2){
      if(intCodBod==5) blnRes=true;
  }else if(intCodEmp==4){
      if(intCodBod==3) blnRes=true;
  }
    
  return blnRes;
}


 
private void MensajeInf(String strMensaje){
 //javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
 String strTit;
 strTit="Mensaje del sistema Zafiro";
 JOptionPane.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
            /*vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"..");
            vecCab.add(INT_TBL_CODITM,"Cod.Itm");
            vecCab.add(INT_TBL_CODEMP,"Cod.Emp");
            vecCab.add(INT_TBL_NOMEMP,"Nom.Emp");
            vecCab.add(INT_TBL_NOMBOD,"Nom.Bod");
            vecCab.add(INT_TBL_STKACT,"Stock.Act");
            vecCab.add(INT_TBL_ESTBOD,"" );
            vecCab.add(INT_TBL_CANCOM,"" );
            vecCab.add(INT_TBL_CANING,"Disponible");*/
            
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"..");
            vecCab.add(INT_TBL_CODITM,"Cod.Itm");
            vecCab.add(INT_TBL_CODEMP,"Cod.Emp");
            vecCab.add(INT_TBL_NOMEMP,"Nom.Emp");
            vecCab.add(INT_TBL_NOMBOD,"Nom.Bod");
            vecCab.add(INT_TBL_STKACT,"Disponible");
            vecCab.add(INT_TBL_ESTBOD,"" );
            vecCab.add(INT_TBL_CANCOM,"" );
            vecCab.add(INT_TBL_CANING,"Por Ingresar");
            
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 
            ZafColNumerada zafColNumerada = new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            
             //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
             ZafMouMotAda objMouMotAda=new ZafMouMotAda();
             tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
             
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOMEMP).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(320);
            tcmAux.getColumn(INT_TBL_STKACT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CANCOM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CANING).setPreferredWidth(80);
            
            
          objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
          tcmAux.getColumn(INT_TBL_NOMBOD).setCellRenderer(objTblCelRenLbl);
          tcmAux.getColumn(INT_TBL_NOMEMP).setCellRenderer(objTblCelRenLbl);
          tcmAux.getColumn(INT_TBL_CODEMP).setCellRenderer(objTblCelRenLbl);
          tcmAux.getColumn(INT_TBL_CODITM).setCellRenderer(objTblCelRenLbl);
          tcmAux.getColumn(INT_TBL_CANING).setCellRenderer(objTblCelRenLbl);
          
           objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
               @Override
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
          objTblCelRenLbl2.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
          //objTblCelRenLbl2.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
          //objTblMod.setColumnDataType(INT_TBL_STKACT, objTblMod.INT_COL_DBL, new Integer(0), null);
          //tcmAux.getColumn(INT_TBL_STKACT).setCellRenderer(objTblCelRenLbl2);
          objTblMod.setColumnDataType(INT_TBL_CANCOM, ZafTblMod.INT_COL_DBL, new Integer(0), null);
          tcmAux.getColumn(INT_TBL_CANCOM).setCellRenderer(objTblCelRenLbl2);
          objTblCelRenLbl2.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
              @Override
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
                  
                }
            });          

          objTblCelRenLbl4.setBackground(objZafParSis.getColorCamposObligatorios());
          objTblCelRenLbl4.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
          objTblCelRenLbl4.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
          objTblMod.setColumnDataType(INT_TBL_CANING, ZafTblMod.INT_COL_DBL, new Integer(0), null);
          tcmAux.getColumn(INT_TBL_CANING).setCellRenderer(objTblCelRenLbl4);
          objTblCelRenLbl4.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
              @Override
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    //Mostrar de color gris las columnas impares.

                    int intCell=objTblCelRenLbl3.getRowRender();

                    String str=tblDat.getValueAt(intCell, INT_TBL_CODEMP).toString();
                    if(str.equals("")){
                        objTblCelRenLbl4.setBackground(java.awt.Color.BLUE);
                        objTblCelRenLbl4.setFont(new java.awt.Font(objTblCelRenLbl4.getFont().getFontName(), java.awt.Font.BOLD,   objTblCelRenLbl4.getFont().getSize()));
                        objTblCelRenLbl4.setForeground(java.awt.Color.WHITE);
                    }
                    if(str.equals("1")){
                        objTblCelRenLbl4.setBackground(java.awt.Color.ORANGE);
                        objTblCelRenLbl4.setForeground(java.awt.Color.BLACK);
                    }
                    if(str.equals("2")){
                        objTblCelRenLbl4.setBackground(java.awt.Color.WHITE);
                        objTblCelRenLbl4.setForeground(java.awt.Color.BLACK);
                    }
                    if(str.equals("4")){
                        objTblCelRenLbl4.setBackground(java.awt.Color.YELLOW);
                        objTblCelRenLbl4.setForeground(java.awt.Color.BLACK);
                    }

                    if(str.equals("0")){
                        objTblCelRenLbl4.setBackground(java.awt.Color.BLACK);
                        objTblCelRenLbl4.setForeground(java.awt.Color.BLACK);
                    }

                }
            });

          objTblCelRenLbl3.setBackground(objZafParSis.getColorCamposObligatorios());
          objTblCelRenLbl3.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
          objTblCelRenLbl3.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
          
          objTblCelRenLbl3.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
          objTblMod.setColumnDataType(INT_TBL_STKACT, ZafTblMod.INT_COL_DBL, new Integer(0), null);
          tcmAux.getColumn(INT_TBL_STKACT).setCellRenderer(objTblCelRenLbl3);
          tcmAux.getColumn(INT_TBL_CANING).setCellRenderer(objTblCelRenLbl3);
          
          objTblCelRenLbl3.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
          objTblMod.setColumnDataType(INT_TBL_CANCOM, ZafTblMod.INT_COL_DBL, new Integer(0), null);
          tcmAux.getColumn(INT_TBL_CANCOM).setCellRenderer(objTblCelRenLbl3);
          objTblCelRenLbl3.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
              @Override
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    //Mostrar de color gris las columnas impares.
                    
                    int intCell=objTblCelRenLbl3.getRowRender();
                    
                    String strEstBod = (( (tblDat.getValueAt(intCell, INT_TBL_ESTBOD)==null) || (tblDat.getValueAt(intCell, INT_TBL_ESTBOD).toString().equals("")))?"V":tblDat.getValueAt(intCell, INT_TBL_ESTBOD).toString());
                       
                    
                    String str=tblDat.getValueAt(intCell, INT_TBL_CODEMP).toString();
                    if(str.equals("")){
                        objTblCelRenLbl3.setBackground(java.awt.Color.BLUE);
                        objTblCelRenLbl3.setFont(new java.awt.Font(objTblCelRenLbl2.getFont().getFontName(), java.awt.Font.BOLD,   objTblCelRenLbl2.getFont().getSize()));
                        objTblCelRenLbl3.setForeground(java.awt.Color.WHITE);
                    } 
                    if(str.equals("1")){
                     if(!(strEstBod.trim().equals("V"))) {  
                        objTblCelRenLbl3.setBackground(java.awt.Color.LIGHT_GRAY);
                        objTblCelRenLbl3.setForeground(java.awt.Color.BLACK);
                     }else{
                        objTblCelRenLbl3.setBackground(java.awt.Color.ORANGE);
                        objTblCelRenLbl3.setForeground(java.awt.Color.BLACK);
                     }  
                    } 
                    if(str.equals("2")){
                     if(!(strEstBod.trim().equals("V"))) {  
                        objTblCelRenLbl3.setBackground(java.awt.Color.LIGHT_GRAY);
                        objTblCelRenLbl3.setForeground(java.awt.Color.BLACK);
                     }else{
                        objTblCelRenLbl3.setBackground(java.awt.Color.WHITE);
                        objTblCelRenLbl3.setForeground(java.awt.Color.BLACK);
                     }
                    
                    }
                    if(str.equals("4")){
                     if(!(strEstBod.trim().equals("V"))) {  
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

          objTblCelRenLbl4.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
          objTblMod.setColumnDataType(INT_TBL_CANING, ZafTblMod.INT_COL_DBL, new Integer(0), null);
          tcmAux.getColumn(INT_TBL_CANING).setCellRenderer(objTblCelRenLbl4);
          objTblCelRenLbl4.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
              @Override
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    //Mostrar de color gris las columnas impares.

                    int intCell=objTblCelRenLbl4.getRowRender();

                    String strEstBod = (( (tblDat.getValueAt(intCell, INT_TBL_ESTBOD)==null) || (tblDat.getValueAt(intCell, INT_TBL_ESTBOD).toString().equals("")))?"V":tblDat.getValueAt(intCell, INT_TBL_ESTBOD).toString());


                    String str=tblDat.getValueAt(intCell, INT_TBL_CODEMP).toString();
                    if(str.equals("")){
                        objTblCelRenLbl4.setBackground(java.awt.Color.BLUE);
                        objTblCelRenLbl4.setFont(new java.awt.Font(objTblCelRenLbl2.getFont().getFontName(), java.awt.Font.BOLD,   objTblCelRenLbl2.getFont().getSize()));
                        objTblCelRenLbl4.setForeground(java.awt.Color.WHITE);
                    }
                    if(str.equals("1")){
                     if(!(strEstBod.trim().equals("V"))) {
                        objTblCelRenLbl4.setBackground(java.awt.Color.CYAN);
                        objTblCelRenLbl4.setForeground(java.awt.Color.BLACK);
                     }else{
                        objTblCelRenLbl4.setBackground(java.awt.Color.ORANGE);
                        objTblCelRenLbl4.setForeground(java.awt.Color.BLACK);
                     }
                    }
                    if(str.equals("2")){
                     if(!(strEstBod.trim().equals("V"))) {
                        objTblCelRenLbl4.setBackground(java.awt.Color.CYAN);
                        objTblCelRenLbl4.setForeground(java.awt.Color.BLACK);
                     }else{
                        objTblCelRenLbl4.setBackground(java.awt.Color.WHITE);
                        objTblCelRenLbl4.setForeground(java.awt.Color.BLACK);
                     }

                    }
                    if(str.equals("4")){
                     if(!(strEstBod.trim().equals("V"))) {
                        objTblCelRenLbl4.setBackground(java.awt.Color.CYAN);
                        objTblCelRenLbl4.setForeground(java.awt.Color.BLACK);
                     }else{
                        objTblCelRenLbl4.setBackground(java.awt.Color.YELLOW);
                        objTblCelRenLbl4.setForeground(java.awt.Color.BLACK);
                    }}

                    if(str.equals("0")){
                        objTblCelRenLbl4.setBackground(java.awt.Color.BLACK);
                        objTblCelRenLbl4.setForeground(java.awt.Color.BLACK);
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
            arlColHid.add(""+INT_TBL_CANCOM);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            //*****vecAux.add("" + INT_TBL_CANCOM);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            /* 
            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANCOM).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                  
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        String strEstBod = (( (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString().equals("")))?"V":tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString());
                        
                        if((strEstBod.trim().equals("V"))) {
                            objTblCelEdiTxt.setCancelarEdicion(true);
                        }else{
                            
                            double dlbCan =  Double.parseDouble((((tblDat.getValueAt(intNumFil, INT_TBL_STKACT)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_STKACT).toString().equals("")))?"0":tblDat.getValueAt(intNumFil, INT_TBL_STKACT).toString()));
                            double dlbCanCom = Double.parseDouble((((tblDat.getValueAt(intNumFil, INT_TBL_CANCOM)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_CANCOM).toString().equals("")))?"0":tblDat.getValueAt(intNumFil, INT_TBL_CANCOM).toString()));
                            if(dlbCanCom > dlbCan){
                                
                            }
                            
                        }
                    }
                     
                }
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                   
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        String strEstBod = (( (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString().equals("")))?"V":tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString());
                        
                        if((strEstBod.trim().equals("V"))) {
                            objTblCelEdiTxt.setCancelarEdicion(true);
                    }}
                 
                }
            });
             */ 
            
            ZafTblEdi zafTblEdi = new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);
          
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

        butCan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        setBounds((screenSize.width-650)/2, (screenSize.height-371)/2, 650, 371);
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

       
private void cerrarVen(){
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
    //javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    //if(JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
        System.gc();
        blnAcepta=false;
        dispose();
    //}
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


private class ZafMouMotAda extends MouseMotionAdapter{
    @Override
 public void mouseMoved(MouseEvent evt){
    int intCol=tblDat.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LINEA:
            strMsg="";
            break;
            case INT_TBL_CODITM:
            strMsg="Código del item.";
            break;

            case INT_TBL_NOMEMP:
            strMsg="Nombre de la Empresa.";
            break;

            case INT_TBL_NOMBOD:
            strMsg="Nombre de la Bodega.";
            break;

            case INT_TBL_STKACT:
            strMsg="Stock Actual.";
            break;

            case INT_TBL_CANING:
            strMsg="Cantidad que esta por ingresar.";
            break;

        default:
            strMsg="";
            break;
    }
    tblDat.getTableHeader().setToolTipText(strMsg);
}
}

    @Override 
    protected void finalize() throws Throwable { 
        System.gc();
        super.finalize();
    }
    
     
}
