/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.   
 */
  
/*
 * ZafCxC11.java
 *
 * Created on May 5, 2010, 11:21:57 AM
 */
      
package CxC.ZafCxC11;
      
import GenOD.ZafGenOdPryTra;
import Librerias.ZafCnfDoc.ZafCnfDoc;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil; /**/
import java.util.Vector; /**/
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafRptSis.ZafRptSis;
import ZafReglas.ZafGenGuiRem;
import ZafReglas.ZafImp;
import ZafReglas.ZafMetImp;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;


/**
 *
 * @author jayapata
 */
public class ZafCxC11 extends javax.swing.JInternalFrame {
  Librerias.ZafParSis.ZafParSis objZafParSis;
  ZafUtil objUti; /**/
  private Librerias.ZafDate.ZafDatePicker txtFecDoc;
  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod; /**/
  private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxtChq, objTblCelEdiTxtValChq, objTblCelEdiTxtObs;
  private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;
  private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCli, objTblCelEdiTxtVcoBan;
  private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
  private java.util.Date datFecAux; 
  private Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu objTblPopMnu;

  java.sql.Connection CONN_GLO=null;
  java.sql.Statement  STM_GLO=null;
  java.sql.ResultSet  rstCab=null;

  ZafVenCon objVenConCli;
  ZafVenCon objVenConBan;
  ZafVenCon objVenConTipdoc;
  ZafVenCon objVenConRec;

   mitoolbar objTooBar;

   private ZafThreadGUI objThrGUI;
   private ZafRptSis objRptSis;


   final int INT_TBL_LINEA=0;  // NUMERO DE LINEAS
   final int INT_TBL_CODCLI=1; // CODIGO DEL CLIENTE
   final int INT_TBL_BUTCLI=2;  // BOTON PARA BUSCAR CLIENTES
   final int INT_TBL_NOMCLI=3;  // NOMBRE DEL CLIENTE
   final int INT_TBL_CODBAN=4;  // CODIGO DEL BANCO
   final int INT_TBL_DSCBAN=5;  // DESCRIPCION CORTA DEL BANCO
   final int INT_TBL_BUTBAN=6;  // BOTON PARA BUSCAR BANCOS
   final int INT_TBL_NOMBAN=7;  // NOMBRE DEL BANCO
   final int INT_TBL_NUMCTA=8;  // NUMERO DE CUENTA DEL BANCO
   final int INT_TBL_NUMCHQ=9;  // NUMERO DEL CHEQUE
   final int INT_TBL_VALCHQ=10;  // VALOR DEL CHEQUE
   final int INT_TBL_FECVEN=11;  // FECHA DE VENCIMIENTO
   final int INT_TBL_BUTFAC=12;  // BOTON DE BUSQUEDA DE FACTURAS
   final int INT_TBL_OBSERV=13;  // OBSERVACION
   final int INT_TBL_DATFAC=14;  // DATOS DE LAS FACTURAS SELECCIONADAS
   final int INT_TBL_VAPLFAC=15;  // VALOR DE FACTURAS SELECCIONADAS
   final int INT_TBL_VALCHQORI=16;  // VALOR ORIGEN DEL CHEQUE
   final int INT_TBL_CODREG=17;  // CODIGO DEL REGISTRO
   final int INT_TBL_ESTAPL=18;  // ESTADO SI ESTA APLICADO EL CHEQUE
   final int INT_TBL_CODCLIORI=19;  // CODIGO ORIGEN DEL CLIENTE
   final int INT_TBL_ASIGBAN=20;    // SI YA TIENE ASIGNADO EL BANCO
   final int INT_TBL_ESTCAMFAC=21;  // estado si ha cambiado o agregado facturas.

    
    //Constantes del ArrayList Elementos Eliminados
   final int INT_ARR_CODREG   = 0;

   int intPuertoImpGuia=0;
   int INT_ENV_REC_IMP_GUIA = 0;

   String strDesCodTipDoc="";
   String strDesLarTipDoc="";
   String strDesSol="";
   String strCodSol="";
   String strFormatoFecha="d/m/y";
   String strCodTipDoc="";
//   String DIRECCION_REPORTE="C://zafiro//reportes_impresos//ZafCxC11/ZafCxC11.jrxml";
   String strIpImpGuia="";
   String strVersion=" Ver 0.9.6 ";

   Vector vecCab=new Vector();    //Almacena las cabeceras  /**/
   javax.swing.JTextField txtCodTipDoc= new javax.swing.JTextField();

   boolean blnHayCam=false;

   StringBuffer stbFacSel;

    String strCodEmp="";
    String strCodLoc="";
    String strCodTip="";
    String strCodDoc="";
    String StrLisFacRetPorLoc="";
    boolean blnEstCar=false;
    String strCodRegGlo="";
    String strSqlTipDocAux="";

    double dblMinAjuCenAut=0;
    double dblMaxAjuCenAut=0;
    
    private String str_RegSel;
    private String strTipDocs;
    private String strCodDocs;
    
    Vector vecOD=new Vector();

    /** Creates new form ZafMae28 */
    public ZafCxC11(Librerias.ZafParSis.ZafParSis obj) {
        try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();

            this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
            lblTit.setText(objZafParSis.getNombreMenu());  /**/

	     objUti = new ZafUtil(); /**/
	     objTooBar = new mitoolbar(this);
	     this.getContentPane().add(objTooBar,"South");

              objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);


             if(objZafParSis.getCodigoMenu()==1824){
                 objTooBar.setVisibleInsertar(false);
                 objTooBar.setVisibleEliminar(false);
                 objTooBar.setVisibleAnular(false);
             }
            
//           /*
//           * Aqui se verifica si estoy en linux
//           */
//           if(System.getProperty("os.name").equals("Linux")){
//               DIRECCION_REPORTE="//zafiro//reportes_impresos//ZafCxC11/ZafCxC11.jrxml";
//           }

             cargarIpPuertoGuiaEmp();

             txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
             txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
             txtFecDoc.setText("");
             panCabRecChq.add(txtFecDoc);
             txtFecDoc.setBounds(580, 8, 92, 20);



	 }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }

    public ZafCxC11(Librerias.ZafParSis.ZafParSis obj , Integer intCodEmp, Integer intCodLoc, Integer intCodTipDoc, Integer intCodDoc, String strCodReg, int intCodMnu ) {
        try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();

            objUti = new ZafUtil(); /**/

             strCodEmp= String.valueOf( intCodEmp.intValue() );
             strCodLoc= String.valueOf( intCodLoc.intValue() );
             strCodTip= String.valueOf( intCodTipDoc.intValue() );
             strCodDoc= String.valueOf( intCodDoc.intValue() );
             strCodRegGlo=strCodReg;
             blnEstCar=true;

             objZafParSis.setCodigoMenu(intCodMnu);

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);


            this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
            lblTit.setText(objZafParSis.getNombreMenu());  /**/
 
	     objTooBar = new mitoolbar(this);
	     //this.getContentPane().add(objTooBar,"South");

             if(objZafParSis.getCodigoMenu()==1824){
                 objTooBar.setVisibleInsertar(false);
                 objTooBar.setVisibleEliminar(false);
                 objTooBar.setVisibleAnular(false);
             }

//           /*
//           * Aqui se verifica si estoy en linux
//           */
//           if(System.getProperty("os.name").equals("Linux")){
//               DIRECCION_REPORTE="//Zafiro//Reportes_impresos//ZafCxC11/ZafCxC11.jrxml";
//           }

             cargarIpPuertoGuiaEmp();

             txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
             txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
             txtFecDoc.setText("");
             panCabRecChq.add(txtFecDoc);
             txtFecDoc.setBounds(580, 8, 92, 20);



	 }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }




/**
* Permite obtener la Ip de impresion de guia.
*/
public void cargarIpPuertoGuiaEmp(){
  java.sql.Connection  conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
        stmLoc=conn.createStatement();

        strSql="SELECT a1.co_emp, a1.co_loc, a1.tx_dirser, a1.ne_pueser FROM tbm_serCliSer AS a " +
        " INNER JOIN tbm_serCliSerLoc AS a1 ON( a1.co_ser=a.co_ser ) " +
        " WHERE a.co_ser=1  AND a1.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a1.co_loc="+objZafParSis.getCodigoLocal()+" ";
        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()){
            strIpImpGuia=rstLoc.getString("tx_dirser");
            intPuertoImpGuia=rstLoc.getInt("ne_pueser");
        }
        rstLoc.close();
        stmLoc.close();
        stmLoc=null;
        rstLoc=null;
        conn.close();
        conn=null;
  }}catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }
}




/**
 * Esta funcion permite extraer los rangos de ajuste de centavos automativos
 * @return true si no hay problema false por algun error.
 */
private boolean cargarRangoAjuCenAut(){
  boolean blnRes=true;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       stmLoc=conn.createStatement();

      strSql="SELECT nd_valminajucenaut, nd_valmaxajucenaut  FROM tbm_emp WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" ";
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
         dblMinAjuCenAut=objUti.redondear(rstLoc.getDouble("nd_valminajucenaut"), 2);
         dblMaxAjuCenAut=objUti.redondear(rstLoc.getDouble("nd_valmaxajucenaut"), 2);
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      conn.close();
      conn=null;
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}



/**
 * Carga ventanas de consulta y configuracion de la tabla
 */
public void Configura_ventana_consulta(){
    configurarVenConClientes();
    configurarVenConBanco();
    configurarVenConTipDoc();
    configurarVenConRecaudador();

   if( objZafParSis.getCodigoMenu()==1739 )
    ConfigurartablaRecChq();

   if( objZafParSis.getCodigoMenu()==1749 )
    ConfigurartablaRecRet();

   if( objZafParSis.getCodigoMenu()==1824 )
    ConfigurartablaAsigFac();


    cargarRangoAjuCenAut();

    if(blnEstCar){
         cargarDatos( strCodEmp, strCodLoc, strCodTip, strCodDoc  );
     }

}


private boolean cargarDatos(String intCodEmp, String intCodLoc, String intCodTipDoc, String intCodDoc ){
  boolean blnRes=true;
  java.sql.Connection conn;
  try{
     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){

       cargarCabReg(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );
       cargarDetReg(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );

       seleccionReg(strCodRegGlo);
      
      conn.close();
      conn=null;
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

public void seleccionReg(String strCodReg){
  String strCodRegBus="";
  try{


    for(int i=0; i<tblDat.getRowCount(); i++){

      strCodRegBus=  (tblDat.getValueAt(i, INT_TBL_CODREG)==null?"":(tblDat.getValueAt(i, INT_TBL_CODREG).equals("")?"":tblDat.getValueAt(i, INT_TBL_CODREG).toString()));
      if(strCodRegBus.equals(strCodReg)){
           tblDat.changeSelection( i, 2, false, false);
      }

   }

 }catch(Exception e) {   objUti.mostrarMsgErr_F1(this,e);  }
}




private boolean cargarCabReg(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){
    boolean blnRes=false;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc02;
    String strSql="",strAux="";
    try{
      if(conn!=null){
        stmLoc=conn.createStatement();

       strSql="SELECT  a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a.fe_doc, a.ne_numdoc1, a.nd_mondoc, a.co_usrrec,  a2.tx_nom " +
       " ,a1.tx_descor, a1.tx_deslar, a.st_reg  " +
       " FROM tbm_cabrecdoc AS a " +
       " INNER JOIN tbm_cabtipdoc AS a1 on ( a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc ) " +
       " LEFT JOIN tbm_usr AS a2 on (a2.co_usr=a.co_usrrec ) " +
       " WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" " +
       " and a.co_tipdoc="+strCodTipDoc+"  and a.co_Doc="+strCodDoc+" ";
       rstLoc02=stmLoc.executeQuery(strSql);
       if(rstLoc02.next()){

        txtCodTipDoc.setText( rstLoc02.getString("co_tipdoc"));
        txtDesCodTitpDoc.setText( rstLoc02.getString("tx_descor"));
        txtDesLarTipDoc.setText( rstLoc02.getString("tx_deslar"));
        txtCodRec.setText( rstLoc02.getString("co_usrrec"));
        txtDesRec.setText( rstLoc02.getString("tx_nom"));
        txtCodDoc.setText( rstLoc02.getString("co_doc"));
        txtNumDoc.setText( rstLoc02.getString("ne_numdoc1"));
        valDoc.setText(""+ objUti.redondear( rstLoc02.getString("nd_mondoc"), 2) );

        strAux=rstLoc02.getString("st_reg");

        java.util.Date dateObj = rstLoc02.getDate("fe_doc");
        if(dateObj==null){
          txtFecDoc.setText("");
        }else{
            java.util.Calendar calObj = java.util.Calendar.getInstance();
            calObj.setTime(dateObj);
            txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                              calObj.get(java.util.Calendar.MONTH)+1     ,
                              calObj.get(java.util.Calendar.YEAR)        );
        }
      }
      rstLoc02.close();
      rstLoc02=null;


    if (strAux.equals("A"))
        strAux="Activo";
     else if (strAux.equals("I"))
         strAux="Anulado";
      else if (strAux.equals("E"))
         strAux="Eliminado";
      else
          strAux="Otro";
     objTooBar.setEstadoRegistro(strAux);

     stmLoc.close();
     stmLoc=null;

}}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
 catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

return blnRes;
}

private boolean cargarDetReg(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){
    boolean blnRes=false;
    java.sql.Statement stmLoc, stmLoc01;
    java.sql.ResultSet rstLoc, rstLoc01;
    String strSql="";
    Vector vecData;
    String strDatFac="";
    int  intCon=0;
    double  dblCanAplFac=0;
    try{
      if(conn!=null){
        stmLoc=conn.createStatement();
        stmLoc01=conn.createStatement();

       vecData = new Vector();

        strSql="SELECT CASE WHEN co_tipdoccon IS NULL THEN 'N' ELSE 'S' END AS AsigBan,  CASE WHEN nd_valapl > 0 THEN 'S' ELSE  'N' END AS estApl, " +
        " a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, a.co_cli, a1.tx_nom,  a.co_banchq,  a.tx_numctachq, a.tx_numchq, a.fe_recchq,  " +
        " a.fe_venchq, a.nd_monchq, a.tx_obs1, a.nd_valapl, a.fe_asitipdoccon, a.st_asidocrel, a.st_regrep, a.st_reg,  a2.tx_descor, a2.tx_deslar  " +
        " FROM tbm_detrecdoc as a " +
        " INNER JOIN tbm_cli as a1 ON (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli) " +
        " LEFT  JOIN tbm_var as a2 ON (a2.co_reg=a.co_banchq ) "+
        " WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" " +
        " and a.co_tipdoc="+strCodTipDoc+"  and a.co_Doc="+strCodDoc+" AND a.st_reg NOT IN ('E') " +
        " ORDER BY a.co_reg   ";
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

           java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL_LINEA,"");
            vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
            vecReg.add(INT_TBL_BUTCLI, "" );
            vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nom") );
            vecReg.add(INT_TBL_CODBAN, rstLoc.getString("co_banchq") );
            vecReg.add(INT_TBL_DSCBAN, rstLoc.getString("tx_descor") );
            vecReg.add(INT_TBL_BUTBAN, "" );
            vecReg.add(INT_TBL_NOMBAN, rstLoc.getString("tx_deslar") );
            vecReg.add(INT_TBL_NUMCTA, rstLoc.getString("tx_numctachq") );
            vecReg.add(INT_TBL_NUMCHQ, rstLoc.getString("tx_numchq") );
            vecReg.add(INT_TBL_VALCHQ, rstLoc.getString("nd_monchq") );
            vecReg.add(INT_TBL_FECVEN, objUti.formatearFecha(rstLoc.getDate("fe_venchq"), "dd/MM/yyyy") ); // rstLoc.getString("fe_venchq") );
            vecReg.add(INT_TBL_BUTFAC, "" );
            vecReg.add(INT_TBL_OBSERV, rstLoc.getString("tx_obs1") );


            strDatFac="";
            intCon=0;
            dblCanAplFac=0;
            strSql="SELECT  a.co_emprel,  a.co_locrel, a.co_tipdocrel, a.co_docrel, a.co_regrel, a1.nd_monchq as valapl FROM " +
            " tbr_detrecdocpagmovinv AS a " +
            " INNER JOIN tbm_pagmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel " +
            " and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) " +
            " WHERE a.co_emp="+rstLoc.getInt("co_emp")+" and a.co_loc="+rstLoc.getInt("co_loc")+" and a.co_tipdoc="+rstLoc.getInt("co_tipdoc")+" " +
            " and a.co_doc= "+rstLoc.getInt("co_doc")+" and a.co_reg="+rstLoc.getInt("co_reg")+" AND a.st_reg NOT IN ('E') ";
            rstLoc01=stmLoc01.executeQuery(strSql);
            while(rstLoc01.next()){
                if(intCon==1) strDatFac+=" UNION ALL ";
                strDatFac +=" SELECT "+rstLoc01.getInt("co_emprel")+" as coemp, "+rstLoc01.getInt("co_locrel")+" as coloc, " +
                " "+rstLoc01.getInt("co_tipdocrel")+" as cotipdoc, "+rstLoc01.getInt("co_docrel")+" as codoc," +
                " "+rstLoc01.getInt("co_regrel")+" as coreg, "+rstLoc01.getDouble("valapl")+" as valapl  ";
                intCon=1;
                dblCanAplFac+=rstLoc01.getDouble("valapl");
            }
            rstLoc01.close();
            rstLoc01=null;

            vecReg.add(INT_TBL_DATFAC, strDatFac );
            vecReg.add(INT_TBL_VAPLFAC, ""+dblCanAplFac );
            vecReg.add(INT_TBL_VALCHQORI, rstLoc.getString("nd_monchq") );
            vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg") );
            vecReg.add(INT_TBL_ESTAPL, rstLoc.getString("estApl") );
            vecReg.add(INT_TBL_CODCLIORI, rstLoc.getString("co_cli") );
            vecReg.add(INT_TBL_ASIGBAN, rstLoc.getString("AsigBan") );
            vecReg.add(INT_TBL_ESTCAMFAC, "N" );


            vecData.add(vecReg);
       }
        rstLoc.close();
        rstLoc=null;

        objTblMod.setData(vecData);
        tblDat .setModel(objTblMod);

   stmLoc.close();
   stmLoc=null;
   stmLoc01.close();
   stmLoc01=null;

}}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
 catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

return blnRes;
}




private boolean configurarVenConRecaudador() {
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
        " where b.co_emp="+objZafParSis.getCodigoEmpresa()+"  and a.st_reg not in ('I')  order by a.tx_nom";  //and b.st_ven='N'

        objVenConRec=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }catch (Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
   return blnRes;
 }

  private boolean configurarVenConTipDoc() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");

            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");

            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("110");
            arlAncCol.add("350");
            
            //Armar la sentencia SQL.   a7.nd_stkTot,
            String Str_Sql="";

             if(objZafParSis.getCodigoUsuario()==1){
              Str_Sql="Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocprg as b " +
              " left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
              " where   b.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
              " b.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
              " b.co_mnu = " + objZafParSis.getCodigoMenu();
             }else {
                    Str_Sql ="SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar  "+
                    " FROM tbr_tipDocUsr AS a1 inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"+
                    " WHERE "+
                    "  a1.co_emp=" + objZafParSis.getCodigoEmpresa()+""+
                    " AND a1.co_loc=" + objZafParSis.getCodigoLocal()+""+
                    " AND a1.co_mnu=" + objZafParSis.getCodigoMenu()+""+
                    " AND a1.co_usr=" + objZafParSis.getCodigoUsuario();
             }
            
            strSqlTipDocAux=" SELECT co_tipdoc FROM ("+Str_Sql+" ) AS x ";

            objVenConTipdoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;

            objVenConTipdoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public void abrirCon(){
        try{
            CONN_GLO=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        }
        catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
    }

    public void CerrarCon(){
        try{
            CONN_GLO.close();
            CONN_GLO=null;
        }
        catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
    }



private boolean ConfigurartablaRecChq() {
 boolean blnRes=false;
 try{
     //Configurar JTable: Establecer el modelo.
        vecCab.clear();
        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_CODCLI,"Cód.Cli.");
        vecCab.add(INT_TBL_BUTCLI,"");
        vecCab.add(INT_TBL_NOMCLI,"Cliente.");
        vecCab.add(INT_TBL_CODBAN,"Cód.Ban.");
        vecCab.add(INT_TBL_DSCBAN,"Banco.");
        vecCab.add(INT_TBL_BUTBAN,"");
        vecCab.add(INT_TBL_NOMBAN,"Nom.Ban");
        vecCab.add(INT_TBL_NUMCTA,"Núm.Cta.");
        vecCab.add(INT_TBL_NUMCHQ,"Núm.Chq.");
        vecCab.add(INT_TBL_VALCHQ,"Val.Chq.");
        vecCab.add(INT_TBL_FECVEN,"Fec.Ven.");
        vecCab.add(INT_TBL_BUTFAC,"");
        vecCab.add(INT_TBL_OBSERV,"Observación");
        vecCab.add(INT_TBL_DATFAC,"");
        vecCab.add(INT_TBL_VAPLFAC,"");
        vecCab.add(INT_TBL_VALCHQORI,"");
        vecCab.add(INT_TBL_CODREG,"");
        vecCab.add(INT_TBL_ESTAPL,"");
        vecCab.add(INT_TBL_CODCLIORI,"");
        vecCab.add(INT_TBL_ASIGBAN,"");
        vecCab.add(INT_TBL_ESTCAMFAC,"");

	objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        java.awt.Color colFonCol;
        colFonCol=new java.awt.Color(228,228,203);

        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);

        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
       //* objMouMotAda=new ZafMouMotAda();
        //*tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


        //Configurar ZafTblMod: Establecer las columnas obligatorias.
        java.util.ArrayList arlAux=new java.util.ArrayList();
        arlAux.add("" + INT_TBL_NUMCHQ);
        arlAux.add("" + INT_TBL_VALCHQ);
        arlAux.add("" + INT_TBL_FECVEN);
        objTblMod.setColumnasObligatorias(arlAux);
        arlAux=null;


          //Configurar ZafTblMod: Establecer las columnas ELIMINADAS
          arlAux=new java.util.ArrayList();
          arlAux.add("" + INT_TBL_CODREG);
          objTblMod.setColsSaveBeforeRemoveRow(arlAux);
          arlAux=null;


        //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
        objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());


        tblDat.getTableHeader().setReorderingAllowed(false);

	//Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();  /**/

        objTblMod.setColumnDataType(INT_TBL_VALCHQ, objTblMod.INT_COL_DBL, new Integer(0), null);


       

        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_CODCLI);
        vecAux.add("" + INT_TBL_BUTCLI);
        vecAux.add("" + INT_TBL_NOMCLI);
        vecAux.add("" + INT_TBL_DSCBAN);
        vecAux.add("" + INT_TBL_BUTBAN);
        vecAux.add("" + INT_TBL_NOMBAN);
        vecAux.add("" + INT_TBL_NUMCTA);
        vecAux.add("" + INT_TBL_NUMCHQ);
        vecAux.add("" + INT_TBL_VALCHQ);
        vecAux.add("" + INT_TBL_FECVEN);
        vecAux.add("" + INT_TBL_BUTFAC);
        vecAux.add("" + INT_TBL_OBSERV);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;

        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

        objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_VALCHQ).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;


         //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_BUTCLI).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_DSCBAN).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_BUTBAN).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_NOMBAN).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_NUMCTA).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_NUMCHQ).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_VALCHQ).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_FECVEN).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_BUTFAC).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_OBSERV).setPreferredWidth(80);
        
        tcmAux.getColumn(INT_TBL_FECVEN).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));

    
        /* Aqui se agrega las columnas que van
             ha hacer ocultas
         * */
        ArrayList arlColHid=new ArrayList();
        arlColHid.add(""+INT_TBL_CODBAN);
        arlColHid.add(""+INT_TBL_DATFAC);
        arlColHid.add(""+INT_TBL_VAPLFAC);
        arlColHid.add(""+INT_TBL_VALCHQORI);
        arlColHid.add(""+INT_TBL_CODREG);
        arlColHid.add(""+INT_TBL_ESTAPL);
        arlColHid.add(""+INT_TBL_CODCLIORI);
        arlColHid.add(""+INT_TBL_ASIGBAN);
        arlColHid.add(""+INT_TBL_ESTCAMFAC);
        objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;

       objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
       objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
       objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
       tcmAux.getColumn(INT_TBL_NUMCTA).setCellRenderer(objTblCelRenLbl);
       tcmAux.getColumn(INT_TBL_NUMCHQ).setCellRenderer(objTblCelRenLbl);
       tcmAux.getColumn(INT_TBL_FECVEN).setCellRenderer(objTblCelRenLbl);
       objTblCelRenLbl=null;


//        objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
//        objTblCelEdiTxt.setBackground(colFonCol);
//        tcmAux.getColumn(INT_TBL_CODCLI).setCellEditor(objTblCelEdiTxt);
//        tcmAux.getColumn(INT_TBL_NOMCLI).setCellEditor(objTblCelEdiTxt);
//
//        objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
//
//            }
//            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//
//
//             }
//        });

        objTblCelEdiTxtObs=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
        tcmAux.getColumn(INT_TBL_OBSERV).setCellEditor(objTblCelEdiTxtObs);
        objTblCelEdiTxtObs.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                 int intNumFil = tblDat.getSelectedRow();
                 String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
                 String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));

                 if((strEstApl.equals("S")))
                    objTblCelEdiTxtObs.setCancelarEdicion(true);

                  if((strAsigBan.equals("S")))
                    objTblCelEdiTxtObs.setCancelarEdicion(true);

            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            }
        });

        
        objTblCelEdiTxtChq=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
        objTblCelEdiTxtChq.setBackground(colFonCol);
        tcmAux.getColumn(INT_TBL_NUMCTA).setCellEditor(objTblCelEdiTxtChq);
        tcmAux.getColumn(INT_TBL_NUMCHQ).setCellEditor(objTblCelEdiTxtChq);
        objTblCelEdiTxtChq.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                int intNumFil = tblDat.getSelectedRow();
                if(intNumFil >= 0 ) {
                    String strCodBan = (tblDat.getValueAt(intNumFil, INT_TBL_CODBAN)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString()));
                    String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
                    String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));


                   if((strEstApl.equals("S")))
                     objTblCelEdiTxtChq.setCancelarEdicion(true);

                   if((strAsigBan.equals("S")))
                     objTblCelEdiTxtChq.setCancelarEdicion(true);


                   if((strCodBan.trim().equals(""))) {
                      objTblCelEdiTxtChq.setCancelarEdicion(true);
                   }
                   /*********************************/

                    
                  /*********************************/
                }

            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow();
                if(tblDat.getSelectedColumn()==INT_TBL_NUMCHQ){

                    String strNumChq = (tblDat.getValueAt(intNumFil, INT_TBL_NUMCHQ)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_NUMCHQ).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_NUMCHQ).toString()));
                    String strCodBan = (tblDat.getValueAt(intNumFil, INT_TBL_CODBAN)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString()));
                    String strNumCta = (tblDat.getValueAt(intNumFil, INT_TBL_NUMCTA)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_NUMCTA).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_NUMCTA).toString()));
                    String strCodCli = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));


                    if( !_getExitNumChqRep( strCodCli, strNumChq, strCodBan, strNumCta ) ){
                        MensajeInf("NUMERO DE CHEQUE YA EXISTE..1");
                        tblDat.setValueAt("", intNumFil, INT_TBL_NUMCHQ);
                    }else{
                        if(!_getVerficicaNumRepChqTbl( intNumFil,  strCodBan,  strNumCta,  strNumChq)){
                            MensajeInf("NUMERO DE CHEQUE YA EXISTE..2");
                            tblDat.setValueAt("", intNumFil, INT_TBL_NUMCHQ);
                        }
                    }
                }
                if(tblDat.getSelectedColumn()==INT_TBL_NUMCTA){
                    String strNumCta = (tblDat.getValueAt(intNumFil, INT_TBL_NUMCTA)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_NUMCTA).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_NUMCTA).toString()));
                    if(strNumCta.equals(""))
                        tblDat.setValueAt("",intNumFil, INT_TBL_NUMCHQ);
                }


             }
        });
   
    
        objTblCelEdiTxtValChq=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
        objTblCelEdiTxtValChq.setBackground(colFonCol);
        tcmAux.getColumn(INT_TBL_VALCHQ).setCellEditor(objTblCelEdiTxtValChq);
        objTblCelEdiTxtValChq.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                int intNumFil = tblDat.getSelectedRow();
                if(intNumFil >= 0 ) {

                   String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
                   String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));


                   if((strEstApl.equals("S")))
                     objTblCelEdiTxtValChq.setCancelarEdicion(true);

                   if((strAsigBan.equals("S")))
                    objTblCelEdiTxtValChq.setCancelarEdicion(true);


                   String strCodBan = (tblDat.getValueAt(intNumFil, INT_TBL_CODBAN)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString()));

                   if((strCodBan.trim().equals(""))) {
                      objTblCelEdiTxtValChq.setCancelarEdicion(true);
                   }
      
                }
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               int intNumFil=tblDat.getSelectedRow();


               double dblValFac = Double.parseDouble( (tblDat.getValueAt(intNumFil, INT_TBL_VAPLFAC)==null?"0":(tblDat.getValueAt(intNumFil, INT_TBL_VAPLFAC).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_VAPLFAC).toString()))  );
               double dblValChq = Double.parseDouble( (tblDat.getValueAt(intNumFil, INT_TBL_VALCHQ)==null?"0":(tblDat.getValueAt(intNumFil, INT_TBL_VALCHQ).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_VALCHQ).toString()))  );
               double dblValChqOri = Double.parseDouble( (tblDat.getValueAt(intNumFil, INT_TBL_VALCHQORI)==null?"0":(tblDat.getValueAt(intNumFil, INT_TBL_VALCHQORI).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_VALCHQORI).toString()))  );

               if(dblValFac > 0 ){

                   if( dblValChq  < dblValFac ){
                       MensajeInf("NO PUEDE SER MENOR AL VALOR SELECCIONADO DE LA FACTURAS. ");
                       tblDat.setValueAt(""+dblValChqOri,  intNumFil, INT_TBL_VALCHQ );
                   }else{
                       tblDat.setValueAt(""+dblValChq,  intNumFil, INT_TBL_VALCHQORI );
                       calculaTotMonChq();
                   }
               }else{
                  tblDat.setValueAt(""+dblValChq,  intNumFil, INT_TBL_VALCHQORI );
                  calculaTotMonChq();
               }

             }
        });


      

        int intColCli[]=new int[2];
        intColCli[0]=1;
        intColCli[1]=3;

        int intColTblCli[]=new int[2];
        intColTblCli[0]=INT_TBL_CODCLI;
        intColTblCli[1]=INT_TBL_NOMCLI;

        objTblCelEdiTxtVcoCli=new ZafTblCelEdiTxtVco(tblDat, objVenConCli, intColCli, intColTblCli );
        tcmAux.getColumn(INT_TBL_CODCLI).setCellEditor(objTblCelEdiTxtVcoCli);
        tcmAux.getColumn(INT_TBL_NOMCLI).setCellEditor(objTblCelEdiTxtVcoCli);
        objTblCelEdiTxtVcoCli.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                int intNumFil = tblDat.getSelectedRow();
                String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
                String strExis=  (tblDat.getValueAt(intNumFil, INT_TBL_CODREG)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODREG).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODREG).toString()));
                String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));

                if((strEstApl.equals("S")))
                    objTblCelEdiTxtVcoCli.setCancelarEdicion(true);
                
                if(!(strExis.equals("")))
                    objTblCelEdiTxtVcoCli.setCancelarEdicion(true);

                if((strAsigBan.equals("S")))
                    objTblCelEdiTxtVcoCli.setCancelarEdicion(true);
            }
            public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               
               if(tblDat.getSelectedColumn()==INT_TBL_NOMCLI) objVenConCli.setCampoBusqueda(2);
               else objVenConCli.setCampoBusqueda(0);

                objVenConCli.setCriterio1(11);
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                 int intNumFil = tblDat.getSelectedRow();
                 if (objTblCelEdiTxtVcoCli.isConsultaAceptada()) {
                      eventoVenConCli(intNumFil);
                 }
                

            }
        });



        int intColBan[]=new int[3];
        intColBan[0]=1;
        intColBan[1]=2;
        intColBan[2]=3;

        int intColTblBan[]=new int[3];
        intColTblBan[0]=INT_TBL_CODBAN;
        intColTblBan[1]=INT_TBL_DSCBAN;
        intColTblBan[2]=INT_TBL_NOMBAN;

        objTblCelEdiTxtVcoBan=new ZafTblCelEdiTxtVco(tblDat, objVenConBan, intColBan, intColTblBan );
        tcmAux.getColumn(INT_TBL_DSCBAN).setCellEditor(objTblCelEdiTxtVcoBan);
        tcmAux.getColumn(INT_TBL_NOMBAN).setCellEditor(objTblCelEdiTxtVcoBan);
        objTblCelEdiTxtVcoBan.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow();
                if(intNumFil >= 0 ) {

                   String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
                   String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));

                   if((strEstApl.equals("S")))
                    objTblCelEdiTxtVcoBan.setCancelarEdicion(true);

                   if((strAsigBan.equals("S")))
                    objTblCelEdiTxtVcoBan.setCancelarEdicion(true);


                    String strCodCLi = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
              
                   if((strCodCLi.trim().equals(""))) {
                          objTblCelEdiTxtVcoBan.setCancelarEdicion(true);
                   }else{

                       if(tblDat.getSelectedColumn()==INT_TBL_DSCBAN) objVenConBan.setCampoBusqueda(1);
                       else objVenConBan.setCampoBusqueda(2);
                       objVenConBan.setCriterio1(11);
                   }

                }

               
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                  int intNumFil = tblDat.getSelectedRow();
                  String strCodBan = (tblDat.getValueAt(intNumFil, INT_TBL_CODBAN)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString()));
                  if((strCodBan.trim().equals(""))) {
                     tblDat.setValueAt("", intNumFil, INT_TBL_NUMCTA);
                     tblDat.setValueAt("", intNumFil, INT_TBL_NUMCHQ);
                  }

                 
            }
        });

        
        objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBL_BUTCLI).setCellRenderer(objTblCelRenBut);
        tcmAux.getColumn(INT_TBL_BUTBAN).setCellRenderer(objTblCelRenBut);
        tcmAux.getColumn(INT_TBL_BUTFAC).setCellRenderer(objTblCelRenBut);
        objTblCelRenBut=null;

        new ButCli(tblDat, INT_TBL_BUTCLI);
        new ButBan(tblDat, INT_TBL_BUTBAN);
        new ButFacRecChq(tblDat, INT_TBL_BUTFAC);

         objTblPopMnu = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
         objTblPopMnu.setInsertarFilasVisible(false);
         objTblPopMnu.setInsertarFilaVisible(false);

         objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if(objTblPopMnu.isClickEliminarFila()){
                      if(!verificaSelEli()){
                         MensajeInf("NO SE PUEDE ELIMINAR. HAY DATOS QUE YA ESTAN PROCESADOS ");
                         objTblPopMnu.cancelarClick();
                       }
                    }
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnu.isClickInsertarFila())
                    {
                        //Escriba aquí el código que se debe realizar luego de insertar la fila.
                        System.out.println("afterClick: isClickInsertarFila");
                    }
                    else if (objTblPopMnu.isClickEliminarFila())
                    {
                        System.out.println("afterClick: isClickEliminarFila");
                        javax.swing.JOptionPane.showMessageDialog(null, "Las filas se eliminaron con éxito.");
                        calculaTotMonChq();
                    }
                }
            });


     tcmAux=null;
     setEditable(false);

     



      blnRes=true;
   }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
    return blnRes;
}

private boolean ConfigurartablaRecRet() {
 boolean blnRes=false;
 try{
     //Configurar JTable: Establecer el modelo.
        vecCab.clear();
        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_CODCLI,"Cód.Cli.");
        vecCab.add(INT_TBL_BUTCLI,"");
        vecCab.add(INT_TBL_NOMCLI,"Cliente.");
        vecCab.add(INT_TBL_CODBAN,"");
        vecCab.add(INT_TBL_DSCBAN,"");
        vecCab.add(INT_TBL_BUTBAN,"");
        vecCab.add(INT_TBL_NOMBAN,"");
        vecCab.add(INT_TBL_NUMCTA,"");
        vecCab.add(INT_TBL_NUMCHQ,"Núm.Ret.");
        vecCab.add(INT_TBL_VALCHQ,"Val.Ret.");
        vecCab.add(INT_TBL_FECVEN,"");
        vecCab.add(INT_TBL_BUTFAC,"");
        vecCab.add(INT_TBL_OBSERV,"Observación");
        vecCab.add(INT_TBL_DATFAC,"");
        vecCab.add(INT_TBL_VAPLFAC,"");
        vecCab.add(INT_TBL_VALCHQORI,"");
        vecCab.add(INT_TBL_CODREG,"");
        vecCab.add(INT_TBL_ESTAPL,"");
        vecCab.add(INT_TBL_CODCLIORI,"");
        vecCab.add(INT_TBL_ASIGBAN,"");
        vecCab.add(INT_TBL_ESTCAMFAC,"");

	objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        java.awt.Color colFonCol;
        colFonCol=new java.awt.Color(228,228,203);

        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);

        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
       //* objMouMotAda=new ZafMouMotAda();
        //*tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


        //Configurar ZafTblMod: Establecer las columnas obligatorias.
        java.util.ArrayList arlAux=new java.util.ArrayList();
        arlAux.add("" + INT_TBL_NUMCHQ);
        arlAux.add("" + INT_TBL_VALCHQ);
        arlAux.add("" + INT_TBL_FECVEN);
        objTblMod.setColumnasObligatorias(arlAux);
        arlAux=null;


          //Configurar ZafTblMod: Establecer las columnas ELIMINADAS
          arlAux=new java.util.ArrayList();
          arlAux.add("" + INT_TBL_CODREG);
          objTblMod.setColsSaveBeforeRemoveRow(arlAux);
          arlAux=null;


        //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
        objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());


        tblDat.getTableHeader().setReorderingAllowed(false);

	//Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();  /**/

        objTblMod.setColumnDataType(INT_TBL_VALCHQ, objTblMod.INT_COL_DBL, new Integer(0), null);


        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_CODCLI);
        vecAux.add("" + INT_TBL_BUTCLI);
        vecAux.add("" + INT_TBL_NOMCLI);
        vecAux.add("" + INT_TBL_NUMCHQ);
        vecAux.add("" + INT_TBL_VALCHQ);
        vecAux.add("" + INT_TBL_BUTFAC);
        vecAux.add("" + INT_TBL_OBSERV);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;

        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

        objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_VALCHQ).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;


         //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_BUTCLI).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(250);
        tcmAux.getColumn(INT_TBL_DSCBAN).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_BUTBAN).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_NOMBAN).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_NUMCTA).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_NUMCHQ).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_VALCHQ).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_FECVEN).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_BUTFAC).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_OBSERV).setPreferredWidth(130);

        tcmAux.getColumn(INT_TBL_FECVEN).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));


        /* Aqui se agrega las columnas que van
             ha hacer ocultas
         * */
        ArrayList arlColHid=new ArrayList();
        arlColHid.add(""+INT_TBL_CODBAN);
        arlColHid.add(""+INT_TBL_DSCBAN);
        arlColHid.add(""+INT_TBL_BUTBAN);
        arlColHid.add(""+INT_TBL_NOMBAN);
        arlColHid.add(""+INT_TBL_NUMCTA);
        arlColHid.add(""+INT_TBL_FECVEN);
        arlColHid.add(""+INT_TBL_VAPLFAC);
        arlColHid.add(""+INT_TBL_VALCHQORI);
        arlColHid.add(""+INT_TBL_CODREG);
        arlColHid.add(""+INT_TBL_ESTAPL);
        arlColHid.add(""+INT_TBL_CODCLIORI);
        arlColHid.add(""+INT_TBL_ASIGBAN);
        arlColHid.add(""+INT_TBL_ESTCAMFAC);
        arlColHid.add(""+INT_TBL_DATFAC);
        objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;

    
       objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
       objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
       objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
       tcmAux.getColumn(INT_TBL_NUMCTA).setCellRenderer(objTblCelRenLbl);
       tcmAux.getColumn(INT_TBL_NUMCHQ).setCellRenderer(objTblCelRenLbl);
       tcmAux.getColumn(INT_TBL_FECVEN).setCellRenderer(objTblCelRenLbl);
       objTblCelRenLbl=null;


//        objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
//        objTblCelEdiTxt.setBackground(colFonCol);
//        tcmAux.getColumn(INT_TBL_CODCLI).setCellEditor(objTblCelEdiTxt);
//        tcmAux.getColumn(INT_TBL_NOMCLI).setCellEditor(objTblCelEdiTxt);
//
//        objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
//
//            }
//            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//
//
//             }
//        });

        objTblCelEdiTxtObs=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
        tcmAux.getColumn(INT_TBL_OBSERV).setCellEditor(objTblCelEdiTxtObs);
        objTblCelEdiTxtObs.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                 int intNumFil = tblDat.getSelectedRow();
                 String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
                 String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));

                 if((strEstApl.equals("S")))
                    objTblCelEdiTxtObs.setCancelarEdicion(true);

                  if((strAsigBan.equals("S")))
                    objTblCelEdiTxtObs.setCancelarEdicion(true);

            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            }
        });


        objTblCelEdiTxtChq=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
        objTblCelEdiTxtChq.setBackground(colFonCol);
        tcmAux.getColumn(INT_TBL_NUMCHQ).setCellEditor(objTblCelEdiTxtChq);
        objTblCelEdiTxtChq.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                int intNumFil = tblDat.getSelectedRow();
                if(intNumFil >= 0 ) {
                    String strCodCli = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
                    String strExis=  (tblDat.getValueAt(intNumFil, INT_TBL_CODREG)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODREG).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODREG).toString()));

//                    String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
//                    String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));
//
//
//                   if((strEstApl.equals("S")))
//                     objTblCelEdiTxtChq.setCancelarEdicion(true);
//
//                   if((strAsigBan.equals("S")))
//                     objTblCelEdiTxtChq.setCancelarEdicion(true);


                   if(!(strExis.equals("")))
                        objTblCelEdiTxtChq.setCancelarEdicion(true);

                   if((strCodCli.trim().equals(""))) 
                      objTblCelEdiTxtChq.setCancelarEdicion(true);
                   
                   /*********************************/


                  /*********************************/
                }

            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow();
                if(tblDat.getSelectedColumn()==INT_TBL_NUMCHQ){

                    String strNumRet = (tblDat.getValueAt(intNumFil, INT_TBL_NUMCHQ)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_NUMCHQ).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_NUMCHQ).toString()));
                    String strCodCli = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));

                    if( !_getExitNumRet( strCodCli ,  strNumRet ) ) {  //(strNumChq, strCodBan, strNumCta ) ){
                        MensajeInf("NUMERO DE RETENCIÓN YA EXISTE..1 ");
                        tblDat.setValueAt("", intNumFil, INT_TBL_NUMCHQ);
                    }else{
                       if(!_getVerficicaNumRepRetTbl( intNumFil,  strCodCli,  strNumRet)){
                          MensajeInf("NUMERO DE RETENCIÓN YA EXISTE..2 ");
                          tblDat.setValueAt("", intNumFil, INT_TBL_NUMCHQ);
                       }
                    }
                 }

             }
        });


        objTblCelEdiTxtValChq=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
        objTblCelEdiTxtValChq.setBackground(colFonCol);
        tcmAux.getColumn(INT_TBL_VALCHQ).setCellEditor(objTblCelEdiTxtValChq);
        objTblCelEdiTxtValChq.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                int intNumFil = tblDat.getSelectedRow();
                if(intNumFil >= 0 ) {

                     String strExis=  (tblDat.getValueAt(intNumFil, INT_TBL_CODREG)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODREG).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODREG).toString()));

//                   String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
//                   String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));
//
//
//                   if((strEstApl.equals("S")))
//                     objTblCelEdiTxtValChq.setCancelarEdicion(true);
//
//                   if((strAsigBan.equals("S")))
//                    objTblCelEdiTxtValChq.setCancelarEdicion(true);

                   if(!(strExis.equals("")))
                      objTblCelEdiTxtValChq.setCancelarEdicion(true);

                     
                   String strCodCli = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));

                   if((strCodCli.trim().equals(""))) {
                      objTblCelEdiTxtValChq.setCancelarEdicion(true);
                   }

                }
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               int intNumFil=tblDat.getSelectedRow();


               double dblValFac = Double.parseDouble( (tblDat.getValueAt(intNumFil, INT_TBL_VAPLFAC)==null?"0":(tblDat.getValueAt(intNumFil, INT_TBL_VAPLFAC).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_VAPLFAC).toString()))  );
               double dblValChq = Double.parseDouble( (tblDat.getValueAt(intNumFil, INT_TBL_VALCHQ)==null?"0":(tblDat.getValueAt(intNumFil, INT_TBL_VALCHQ).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_VALCHQ).toString()))  );
               double dblValChqOri = Double.parseDouble( (tblDat.getValueAt(intNumFil, INT_TBL_VALCHQORI)==null?"0":(tblDat.getValueAt(intNumFil, INT_TBL_VALCHQORI).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_VALCHQORI).toString()))  );

               if(dblValFac > 0 ){

                   if( dblValChq  < dblValFac ){
                       MensajeInf("NO PUEDE SER MENOR AL VALOR SELECCIONADO DE LA FACTURAS. ");
                       tblDat.setValueAt(""+dblValChqOri,  intNumFil, INT_TBL_VALCHQ );
                   }else{
                       tblDat.setValueAt(""+dblValChq,  intNumFil, INT_TBL_VALCHQORI );
                       calculaTotMonChq();
                   }
               }else{
                  tblDat.setValueAt(""+dblValChq,  intNumFil, INT_TBL_VALCHQORI );
                  calculaTotMonChq();
               }

             }
        });




        int intColCli[]=new int[2];
        intColCli[0]=1;
        intColCli[1]=3;

        int intColTblCli[]=new int[2];
        intColTblCli[0]=INT_TBL_CODCLI;
        intColTblCli[1]=INT_TBL_NOMCLI;

        objTblCelEdiTxtVcoCli=new ZafTblCelEdiTxtVco(tblDat, objVenConCli, intColCli, intColTblCli );
        tcmAux.getColumn(INT_TBL_CODCLI).setCellEditor(objTblCelEdiTxtVcoCli);
        tcmAux.getColumn(INT_TBL_NOMCLI).setCellEditor(objTblCelEdiTxtVcoCli);
        objTblCelEdiTxtVcoCli.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                int intNumFil = tblDat.getSelectedRow();
                String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
                String strExis=  (tblDat.getValueAt(intNumFil, INT_TBL_CODREG)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODREG).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODREG).toString()));
                String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));

                if((strEstApl.equals("S")))
                    objTblCelEdiTxtVcoCli.setCancelarEdicion(true);

                if(!(strExis.equals("")))
                    objTblCelEdiTxtVcoCli.setCancelarEdicion(true);

                if((strAsigBan.equals("S")))
                    objTblCelEdiTxtVcoCli.setCancelarEdicion(true);
            }
            public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

               if(tblDat.getSelectedColumn()==INT_TBL_NOMCLI) objVenConCli.setCampoBusqueda(2);
               else objVenConCli.setCampoBusqueda(0);

                objVenConCli.setCriterio1(11);
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                 int intNumFil = tblDat.getSelectedRow();
                 if (objTblCelEdiTxtVcoCli.isConsultaAceptada()) {
                      eventoVenConCli(intNumFil);
                 }


            }
        });



        int intColBan[]=new int[3];
        intColBan[0]=1;
        intColBan[1]=2;
        intColBan[2]=3;

        int intColTblBan[]=new int[3];
        intColTblBan[0]=INT_TBL_CODBAN;
        intColTblBan[1]=INT_TBL_DSCBAN;
        intColTblBan[2]=INT_TBL_NOMBAN;

        objTblCelEdiTxtVcoBan=new ZafTblCelEdiTxtVco(tblDat, objVenConBan, intColBan, intColTblBan );
        tcmAux.getColumn(INT_TBL_DSCBAN).setCellEditor(objTblCelEdiTxtVcoBan);
        tcmAux.getColumn(INT_TBL_NOMBAN).setCellEditor(objTblCelEdiTxtVcoBan);
        objTblCelEdiTxtVcoBan.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow();
                if(intNumFil >= 0 ) {

                   String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
                   String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));

                   if((strEstApl.equals("S")))
                    objTblCelEdiTxtVcoBan.setCancelarEdicion(true);

                   if((strAsigBan.equals("S")))
                    objTblCelEdiTxtVcoBan.setCancelarEdicion(true);


                    String strCodCLi = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));

                   if((strCodCLi.trim().equals(""))) {
                          objTblCelEdiTxtVcoBan.setCancelarEdicion(true);
                   }else{

                       if(tblDat.getSelectedColumn()==INT_TBL_DSCBAN) objVenConBan.setCampoBusqueda(1);
                       else objVenConBan.setCampoBusqueda(2);
                       objVenConBan.setCriterio1(11);
                   }

                }


            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                  int intNumFil = tblDat.getSelectedRow();
                  String strCodBan = (tblDat.getValueAt(intNumFil, INT_TBL_CODBAN)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString()));
                  if((strCodBan.trim().equals(""))) {
                     tblDat.setValueAt("", intNumFil, INT_TBL_NUMCTA);
                     tblDat.setValueAt("", intNumFil, INT_TBL_NUMCHQ);
                  }


            }
        });


        objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBL_BUTCLI).setCellRenderer(objTblCelRenBut);
        tcmAux.getColumn(INT_TBL_BUTBAN).setCellRenderer(objTblCelRenBut);
        tcmAux.getColumn(INT_TBL_BUTFAC).setCellRenderer(objTblCelRenBut);
        objTblCelRenBut=null;

        new ButCli(tblDat, INT_TBL_BUTCLI);
        new ButBan(tblDat, INT_TBL_BUTBAN);
        new ButFacRecRet(tblDat, INT_TBL_BUTFAC);

         objTblPopMnu = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
         objTblPopMnu.setInsertarFilasVisible(false);
         objTblPopMnu.setInsertarFilaVisible(false);

         objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if(objTblPopMnu.isClickEliminarFila()){
                      if(!verificaSelEli()){
                         MensajeInf("NO SE PUEDE ELIMINAR. HAY DATOS QUE YA ESTAN PROCESADOS ");
                         objTblPopMnu.cancelarClick();
                       }
                    }
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnu.isClickInsertarFila())
                    {
                        //Escriba aquí el código que se debe realizar luego de insertar la fila.
                        System.out.println("afterClick: isClickInsertarFila");
                    }
                    else if (objTblPopMnu.isClickEliminarFila())
                    {
                        System.out.println("afterClick: isClickEliminarFila");
                        javax.swing.JOptionPane.showMessageDialog(null, "Las filas se eliminaron con éxito.");
                    }
                }
            });


     tcmAux=null;
     setEditable(false);





      blnRes=true;
   }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
    return blnRes;
}

private boolean ConfigurartablaAsigFac() {
 boolean blnRes=false;
 try{
     //Configurar JTable: Establecer el modelo.
        vecCab.clear();
        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_CODCLI,"Cód.Cli.");
        vecCab.add(INT_TBL_BUTCLI,"");
        vecCab.add(INT_TBL_NOMCLI,"Cliente.");
        vecCab.add(INT_TBL_CODBAN,"Cód.Ban.");
        vecCab.add(INT_TBL_DSCBAN,"Banco.");
        vecCab.add(INT_TBL_BUTBAN,"");
        vecCab.add(INT_TBL_NOMBAN,"Nom.Ban");
        vecCab.add(INT_TBL_NUMCTA,"Núm.Cta.");
        vecCab.add(INT_TBL_NUMCHQ,"Núm.Chq.");
        vecCab.add(INT_TBL_VALCHQ,"Val.Chq.");
        vecCab.add(INT_TBL_FECVEN,"Fec.Ven.");
        vecCab.add(INT_TBL_BUTFAC,"");
        vecCab.add(INT_TBL_OBSERV,"Observación");
        vecCab.add(INT_TBL_DATFAC,"");
        vecCab.add(INT_TBL_VAPLFAC,"");
        vecCab.add(INT_TBL_VALCHQORI,"");
        vecCab.add(INT_TBL_CODREG,"");
        vecCab.add(INT_TBL_ESTAPL,"");
        vecCab.add(INT_TBL_CODCLIORI,"");
        vecCab.add(INT_TBL_ASIGBAN,"");
        vecCab.add(INT_TBL_ESTCAMFAC,"");

	objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        java.awt.Color colFonCol;
        colFonCol=new java.awt.Color(228,228,203);

        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);

        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
       //* objMouMotAda=new ZafMouMotAda();
        //*tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


        //Configurar ZafTblMod: Establecer las columnas obligatorias.
        java.util.ArrayList arlAux=new java.util.ArrayList();
        arlAux.add("" + INT_TBL_NUMCHQ);
        arlAux.add("" + INT_TBL_VALCHQ);
        arlAux.add("" + INT_TBL_FECVEN);
        objTblMod.setColumnasObligatorias(arlAux);
        arlAux=null;


          //Configurar ZafTblMod: Establecer las columnas ELIMINADAS
          arlAux=new java.util.ArrayList();
          arlAux.add("" + INT_TBL_CODREG);
          objTblMod.setColsSaveBeforeRemoveRow(arlAux);
          arlAux=null;


        //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
        objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());


        tblDat.getTableHeader().setReorderingAllowed(false);

	//Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();  /**/

        objTblMod.setColumnDataType(INT_TBL_VALCHQ, objTblMod.INT_COL_DBL, new Integer(0), null);


        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_BUTFAC);
        vecAux.add("" + INT_TBL_OBSERV);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;

        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

        objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_VALCHQ).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;


         //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_BUTCLI).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_DSCBAN).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_BUTBAN).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_NOMBAN).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_NUMCTA).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_NUMCHQ).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_VALCHQ).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_FECVEN).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_BUTFAC).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_OBSERV).setPreferredWidth(80);

        tcmAux.getColumn(INT_TBL_FECVEN).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));


        /* Aqui se agrega las columnas que van
             ha hacer ocultas
         * */
        ArrayList arlColHid=new ArrayList();
        arlColHid.add(""+INT_TBL_CODBAN);
        arlColHid.add(""+INT_TBL_DATFAC);
        arlColHid.add(""+INT_TBL_VAPLFAC);
        arlColHid.add(""+INT_TBL_VALCHQORI);
        arlColHid.add(""+INT_TBL_CODREG);
        arlColHid.add(""+INT_TBL_ESTAPL);
        arlColHid.add(""+INT_TBL_CODCLIORI);
        arlColHid.add(""+INT_TBL_ASIGBAN);
        arlColHid.add(""+INT_TBL_ESTCAMFAC);
        objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;

       objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
       objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
       objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
       tcmAux.getColumn(INT_TBL_NUMCTA).setCellRenderer(objTblCelRenLbl);
       tcmAux.getColumn(INT_TBL_NUMCHQ).setCellRenderer(objTblCelRenLbl);
       tcmAux.getColumn(INT_TBL_FECVEN).setCellRenderer(objTblCelRenLbl);
       objTblCelRenLbl=null;


//        objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
//        objTblCelEdiTxt.setBackground(colFonCol);
//        tcmAux.getColumn(INT_TBL_CODCLI).setCellEditor(objTblCelEdiTxt);
//        tcmAux.getColumn(INT_TBL_NOMCLI).setCellEditor(objTblCelEdiTxt);
//
//        objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
//
//            }
//            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//
//
//             }
//        });

        objTblCelEdiTxtObs=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
        tcmAux.getColumn(INT_TBL_OBSERV).setCellEditor(objTblCelEdiTxtObs);
        objTblCelEdiTxtObs.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                 int intNumFil = tblDat.getSelectedRow();
                 String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
                 String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));

                 if((strEstApl.equals("S")))
                    objTblCelEdiTxtObs.setCancelarEdicion(true);

                  if((strAsigBan.equals("S")))
                    objTblCelEdiTxtObs.setCancelarEdicion(true);

            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            }
        });


        objTblCelEdiTxtChq=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
        objTblCelEdiTxtChq.setBackground(colFonCol);
        tcmAux.getColumn(INT_TBL_NUMCTA).setCellEditor(objTblCelEdiTxtChq);
        tcmAux.getColumn(INT_TBL_NUMCHQ).setCellEditor(objTblCelEdiTxtChq);
        objTblCelEdiTxtChq.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                int intNumFil = tblDat.getSelectedRow();
                if(intNumFil >= 0 ) {
                    String strCodBan = (tblDat.getValueAt(intNumFil, INT_TBL_CODBAN)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString()));
                    String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
                    String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));


                   if((strEstApl.equals("S")))
                     objTblCelEdiTxtChq.setCancelarEdicion(true);

                   if((strAsigBan.equals("S")))
                     objTblCelEdiTxtChq.setCancelarEdicion(true);


                   if((strCodBan.trim().equals(""))) {
                      objTblCelEdiTxtChq.setCancelarEdicion(true);
                   }
                   /*********************************/


                  /*********************************/
                }

            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow();
                if(tblDat.getSelectedColumn()==INT_TBL_NUMCHQ){

                    String strNumChq = (tblDat.getValueAt(intNumFil, INT_TBL_NUMCHQ)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_NUMCHQ).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_NUMCHQ).toString()));
                    String strCodBan = (tblDat.getValueAt(intNumFil, INT_TBL_CODBAN)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString()));
                    String strNumCta = (tblDat.getValueAt(intNumFil, INT_TBL_NUMCTA)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_NUMCTA).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_NUMCTA).toString()));
                    String strCodCli = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));

                    if( !_getExitNumChqRep(strCodCli, strNumChq, strCodBan, strNumCta ) ){
                        MensajeInf("NUMERO DE CHEQUE YA EXISTE..");
                        tblDat.setValueAt("", intNumFil, INT_TBL_NUMCHQ);

                    }
                }
                if(tblDat.getSelectedColumn()==INT_TBL_NUMCTA){
                    String strNumCta = (tblDat.getValueAt(intNumFil, INT_TBL_NUMCTA)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_NUMCTA).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_NUMCTA).toString()));
                    if(strNumCta.equals(""))
                        tblDat.setValueAt("",intNumFil, INT_TBL_NUMCHQ);
                }


             }
        });


        objTblCelEdiTxtValChq=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
        objTblCelEdiTxtValChq.setBackground(colFonCol);
        tcmAux.getColumn(INT_TBL_VALCHQ).setCellEditor(objTblCelEdiTxtValChq);
        objTblCelEdiTxtValChq.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                int intNumFil = tblDat.getSelectedRow();
                if(intNumFil >= 0 ) {

                   String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
                   String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));


                   if((strEstApl.equals("S")))
                     objTblCelEdiTxtValChq.setCancelarEdicion(true);

                   if((strAsigBan.equals("S")))
                    objTblCelEdiTxtValChq.setCancelarEdicion(true);


                   String strCodBan = (tblDat.getValueAt(intNumFil, INT_TBL_CODBAN)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString()));

                   if((strCodBan.trim().equals(""))) {
                      objTblCelEdiTxtValChq.setCancelarEdicion(true);
                   }

                }
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               int intNumFil=tblDat.getSelectedRow();


               double dblValFac = Double.parseDouble( (tblDat.getValueAt(intNumFil, INT_TBL_VAPLFAC)==null?"0":(tblDat.getValueAt(intNumFil, INT_TBL_VAPLFAC).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_VAPLFAC).toString()))  );
               double dblValChq = Double.parseDouble( (tblDat.getValueAt(intNumFil, INT_TBL_VALCHQ)==null?"0":(tblDat.getValueAt(intNumFil, INT_TBL_VALCHQ).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_VALCHQ).toString()))  );
               double dblValChqOri = Double.parseDouble( (tblDat.getValueAt(intNumFil, INT_TBL_VALCHQORI)==null?"0":(tblDat.getValueAt(intNumFil, INT_TBL_VALCHQORI).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_VALCHQORI).toString()))  );

               if(dblValFac > 0 ){

                   if( dblValChq  < dblValFac ){
                       MensajeInf("NO PUEDE SER MENOR AL VALOR SELECCIONADO DE LA FACTURAS. ");
                       tblDat.setValueAt(""+dblValChqOri,  intNumFil, INT_TBL_VALCHQ );
                   }else{
                       tblDat.setValueAt(""+dblValChq,  intNumFil, INT_TBL_VALCHQORI );
                       calculaTotMonChq();
                   }
               }else{
                  tblDat.setValueAt(""+dblValChq,  intNumFil, INT_TBL_VALCHQORI );
                  calculaTotMonChq();
               }

             }
        });




        int intColCli[]=new int[2];
        intColCli[0]=1;
        intColCli[1]=3;

        int intColTblCli[]=new int[2];
        intColTblCli[0]=INT_TBL_CODCLI;
        intColTblCli[1]=INT_TBL_NOMCLI;

        objTblCelEdiTxtVcoCli=new ZafTblCelEdiTxtVco(tblDat, objVenConCli, intColCli, intColTblCli );
        tcmAux.getColumn(INT_TBL_CODCLI).setCellEditor(objTblCelEdiTxtVcoCli);
        tcmAux.getColumn(INT_TBL_NOMCLI).setCellEditor(objTblCelEdiTxtVcoCli);
        objTblCelEdiTxtVcoCli.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                int intNumFil = tblDat.getSelectedRow();
                String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
                String strExis=  (tblDat.getValueAt(intNumFil, INT_TBL_CODREG)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODREG).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODREG).toString()));
                String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));

                if((strEstApl.equals("S")))
                    objTblCelEdiTxtVcoCli.setCancelarEdicion(true);

                if(!(strExis.equals("")))
                    objTblCelEdiTxtVcoCli.setCancelarEdicion(true);

                if((strAsigBan.equals("S")))
                    objTblCelEdiTxtVcoCli.setCancelarEdicion(true);
            }
            public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

               if(tblDat.getSelectedColumn()==INT_TBL_NOMCLI) objVenConCli.setCampoBusqueda(2);
               else objVenConCli.setCampoBusqueda(0);

                objVenConCli.setCriterio1(11);
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                 int intNumFil = tblDat.getSelectedRow();
                 if (objTblCelEdiTxtVcoCli.isConsultaAceptada()) {
                      eventoVenConCli(intNumFil);
                 }


            }
        });



        int intColBan[]=new int[3];
        intColBan[0]=1;
        intColBan[1]=2;
        intColBan[2]=3;

        int intColTblBan[]=new int[3];
        intColTblBan[0]=INT_TBL_CODBAN;
        intColTblBan[1]=INT_TBL_DSCBAN;
        intColTblBan[2]=INT_TBL_NOMBAN;

        objTblCelEdiTxtVcoBan=new ZafTblCelEdiTxtVco(tblDat, objVenConBan, intColBan, intColTblBan );
        tcmAux.getColumn(INT_TBL_DSCBAN).setCellEditor(objTblCelEdiTxtVcoBan);
        tcmAux.getColumn(INT_TBL_NOMBAN).setCellEditor(objTblCelEdiTxtVcoBan);
        objTblCelEdiTxtVcoBan.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow();
                if(intNumFil >= 0 ) {

                   String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
                   String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));

                   if((strEstApl.equals("S")))
                    objTblCelEdiTxtVcoBan.setCancelarEdicion(true);

                   if((strAsigBan.equals("S")))
                    objTblCelEdiTxtVcoBan.setCancelarEdicion(true);


                    String strCodCLi = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));

                   if((strCodCLi.trim().equals(""))) {
                          objTblCelEdiTxtVcoBan.setCancelarEdicion(true);
                   }else{

                       if(tblDat.getSelectedColumn()==INT_TBL_DSCBAN) objVenConBan.setCampoBusqueda(1);
                       else objVenConBan.setCampoBusqueda(2);
                       objVenConBan.setCriterio1(11);
                   }

                }


            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                  int intNumFil = tblDat.getSelectedRow();
                  String strCodBan = (tblDat.getValueAt(intNumFil, INT_TBL_CODBAN)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString()));
                  if((strCodBan.trim().equals(""))) {
                     tblDat.setValueAt("", intNumFil, INT_TBL_NUMCTA);
                     tblDat.setValueAt("", intNumFil, INT_TBL_NUMCHQ);
                  }


            }
        });


        objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBL_BUTCLI).setCellRenderer(objTblCelRenBut);
        tcmAux.getColumn(INT_TBL_BUTBAN).setCellRenderer(objTblCelRenBut);
        tcmAux.getColumn(INT_TBL_BUTFAC).setCellRenderer(objTblCelRenBut);
        objTblCelRenBut=null;

        new ButCli(tblDat, INT_TBL_BUTCLI);
        new ButBan(tblDat, INT_TBL_BUTBAN);
        new ButFacRecChq(tblDat, INT_TBL_BUTFAC);

         objTblPopMnu = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
         objTblPopMnu.setInsertarFilasVisible(false);
         objTblPopMnu.setInsertarFilaVisible(false);

         objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if(objTblPopMnu.isClickEliminarFila()){
                      if(!verificaSelEli()){
                         MensajeInf("NO SE PUEDE ELIMINAR. HAY DATOS QUE YA ESTAN PROCESADOS ");
                         objTblPopMnu.cancelarClick();
                       }
                    }
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnu.isClickInsertarFila())
                    {
                        //Escriba aquí el código que se debe realizar luego de insertar la fila.
                        System.out.println("afterClick: isClickInsertarFila");
                    }
                    else if (objTblPopMnu.isClickEliminarFila())
                    {
                        System.out.println("afterClick: isClickEliminarFila");
                        javax.swing.JOptionPane.showMessageDialog(null, "Las filas se eliminaron con éxito.");
                    }
                }
            });


     tcmAux=null;
     setEditable(false);





      blnRes=true;
   }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
    return blnRes;
}

/**
 * verifica si ya tiene aplicado algun valor al registro antes de eliminar de la fila
 * @return  true si se puede eliminar  false no se puede eliminar
 */
private boolean verificaSelEli(){
  boolean blnRes=true;
  String strEstApl="";
  try{
    int intFilSel[];
    int intFil=0;
    intFilSel=tblDat.getSelectedRows();
   
    for(int i=0; i<intFilSel.length; i++) {
         intFil=intFilSel[i]-i;
         strEstApl = (tblDat.getValueAt(intFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intFil, INT_TBL_ESTAPL).toString()));
         if((strEstApl.equals("S"))){
             blnRes=false;
             break;
         }
   }
   intFilSel=null;
   
   }catch(Exception e) { blnRes=false;  objUti.mostrarMsgErr_F1(this,e);  }
  return blnRes;
}

private void eventoVenConCli(int intNumFil){
  try{
       String strCodCli = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
       String strCodCliOri = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLIORI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLIORI).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLIORI).toString()));

       if(!strCodCli.equals(strCodCliOri)){
        tblDat.setValueAt("", intNumFil, INT_TBL_OBSERV);
        tblDat.setValueAt("", intNumFil, INT_TBL_DATFAC);
        tblDat.setValueAt("", intNumFil, INT_TBL_VAPLFAC);
        tblDat.setValueAt("", intNumFil, INT_TBL_NUMCHQ);
       }
       tblDat.setValueAt(strCodCli, intNumFil, INT_TBL_CODCLIORI);

  }catch(Exception e) {   objUti.mostrarMsgErr_F1(this,e);  }
}


/**
 * Calcula el monto total de los cheques ingresados
 */
public void calculaTotMonChq(){
  double dblMonChq=0;
  try{
    for(int i=0; i<tblDat.getRowCount(); i++){
      if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){
          
          dblMonChq += Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_VALCHQ)==null)?"0":(tblDat.getValueAt(i, INT_TBL_VALCHQ).toString())));
          
    }}
   dblMonChq=objUti.redondear(dblMonChq, 2);
   valDoc.setText(""+dblMonChq);

 }catch(Exception e) {   objUti.mostrarMsgErr_F1(this,e);  }
}


/**
 * Verifica si existe numero de cheque.
 * @param strCodCli codigo del cliente
 * @param strNumChq numero del cheque
 * @param strCodBan  codigo del banco
 * @param strNumCta   numero de la cuenta
 * @return  true si no existe   false si existe 
 */
private boolean _getExitNumChqRep(String strCodCli, String strNumChq, String strCodBan, String strNumCta  ){
  boolean blnRes=true;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strSqlAux="";
  try{
     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);
       stmLoc=conn.createStatement();

        //if(!strNumCta.equals(""))
          //  strSqlAux=" AND trim(tx_numctachq)='"+strNumCta+"' ";
        

        strSql="SELECT tx_numchq FROM tbm_detrecdoc WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
        " AND co_cli="+strCodCli+" AND co_banchq="+strCodBan+" AND  trim(tx_numchq) = '"+strNumChq+"' "+strSqlAux+" ";
        rstLoc = stmLoc.executeQuery(strSql);
        if(rstLoc.next())
            blnRes=false;
        rstLoc.close();
        rstLoc=null;

       stmLoc.close();
       stmLoc=null;
       conn.close();
       conn=null;
  
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


/**
 * Verifica si existe repetido el numero del cheque en la tabla
 * @param intNumFil fila del regsitro de la tabla
 * @param strCodBan  codigo del banco
 * @param strNumCta  numero de cuenta
 * @param strNumChq  numero del cheque
 * @return  true si no existe   false si existe 
 */
public boolean _getVerficicaNumRepChqTbl(int intNumFil, String strCodBan, String strNumCta, String strNumChq){
  boolean blnRes=true;
  String strNumChqBus="";
  String strCodBanBus="";
  String strCtaBanBus="";
  try{
    for(int i=0; i<tblDat.getRowCount(); i++){
      if( !((tblDat.getValueAt(i, INT_TBL_CODBAN)==null?"":(tblDat.getValueAt(i, INT_TBL_CODBAN).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODBAN).toString())).equals("")) ){

       if(intNumFil != i){

          strCodBanBus = (tblDat.getValueAt(i, INT_TBL_CODBAN)==null?"":(tblDat.getValueAt(i, INT_TBL_CODBAN).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODBAN).toString()));
          strCtaBanBus = (tblDat.getValueAt(i, INT_TBL_NUMCTA)==null?"":(tblDat.getValueAt(i, INT_TBL_NUMCTA).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_NUMCTA).toString()));
          strNumChqBus = (tblDat.getValueAt(i, INT_TBL_NUMCHQ)==null?"0":tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString());

          if( strCodBanBus.equals(strCodBan) && strCtaBanBus.equals(strNumCta) && strNumChqBus.equals(strNumChq) ){
              blnRes=false;
              break;
          }

       }
    }}

 }catch(Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this,e);  }
 return blnRes;
}


/**
 * Verifica si existe numero de retencion.
 * @param strNumChq numero del cheque
 * @param strCodCli  codigo del cliente
 * @param strNumRet   numero de la retencion
 * @return  true si no existe   false si existe
 */
private boolean _getExitNumRet(String strCodCli , String strNumRet  ){
  boolean blnRes=true;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);
       stmLoc=conn.createStatement();

        strSql="SELECT tx_numchq FROM tbm_detrecdoc WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
        " AND co_tipdoc="+txtCodTipDoc.getText()+" and  co_cli="+strCodCli+" AND  trim(tx_numchq) = '"+strNumRet+"' ";
        rstLoc = stmLoc.executeQuery(strSql);
        if(rstLoc.next())
            blnRes=false;
        rstLoc.close();
        rstLoc=null;

       stmLoc.close();
       stmLoc=null;
       conn.close();
       conn=null;

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

/**
 * Verifica si existe repetido el numero de retencion pero en la tabla
 * @param intNumFil  numero de fila que se esta insertando
 * @param strCodCLi  codigo del cliente correspondiente a la fila
 * @param strNumRet  numero de retencion correspondiente a la fila
 * @return  true si no existe   false si existe
 */
public boolean _getVerficicaNumRepRetTbl(int intNumFil, String strCodCLi, String strNumRet){
  boolean blnRes=true;
  String strNumRetBus="";
  String strCodCLiBus="";
  try{
    for(int i=0; i<tblDat.getRowCount(); i++){
      if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){

       if(intNumFil != i){

          strCodCLiBus = (tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString()));
          strNumRetBus = (tblDat.getValueAt(i, INT_TBL_NUMCHQ)==null?"0":tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString());

          if( strCodCLiBus.equals(strCodCLi) && strNumRetBus.equals(strNumRet) ){
              blnRes=false;
              break;
          }

       }
    }}
  
 }catch(Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this,e);  }
 return blnRes;
}



 private class ButCli extends Librerias.ZafTableColBut.ZafTableColBut{
    public ButCli(javax.swing.JTable tbl, int intIdx){
         super(tbl,intIdx);
    }
    public void butCLick() {

     int intNumFil = tblDat.getSelectedRow();
      if(intNumFil >= 0 ) {
      String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
      String strExis=  (tblDat.getValueAt(intNumFil, INT_TBL_CODREG)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODREG).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODREG).toString()));
      String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));


      if(strEstApl.equals("N")){
       if(strAsigBan.equals("N")){
       if(strExis.equals("")){
        listaClientes(intNumFil);
      }}}
    }
 }}

     private void listaClientes(int intNumFil){

        objVenConCli.setTitle("Listado de Clientes");
        objVenConCli.show();
        if (objVenConCli.getSelectedButton()==objVenConCli.INT_BUT_ACE) {
            tblDat.setValueAt(objVenConCli.getValueAt(1),intNumFil,INT_TBL_CODCLI);
            tblDat.setValueAt(objVenConCli.getValueAt(3),intNumFil,INT_TBL_NOMCLI);
            eventoVenConCli(intNumFil);
        }

    }


 private class ButBan extends Librerias.ZafTableColBut.ZafTableColBut{
    public ButBan(javax.swing.JTable tbl, int intIdx){
         super(tbl,intIdx);
    }
    public void butCLick() {

     int intNumFil = tblDat.getSelectedRow();
      if(intNumFil >= 0 ) {
      String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
      String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));
      String strCodCLi = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
      if(!(strCodCLi.trim().equals(""))){
         if(strEstApl.equals("N")){
          if(strAsigBan.equals("N")){
                listaBancos();
          }
         }
      }

    }
 }}

private void listaBancos(){
      objVenConBan.setTitle("Listado de Clientes");
      objVenConBan.show();
      if (objVenConBan.getSelectedButton()==objVenConBan.INT_BUT_ACE) {
          tblDat.setValueAt(objVenConBan.getValueAt(1),tblDat.getSelectedRow(),INT_TBL_CODBAN);
          tblDat.setValueAt(objVenConBan.getValueAt(2),tblDat.getSelectedRow(),INT_TBL_DSCBAN);
          tblDat.setValueAt(objVenConBan.getValueAt(3),tblDat.getSelectedRow(),INT_TBL_NOMBAN);
}}
  
 private class ButFacRecChq extends Librerias.ZafTableColBut.ZafTableColBut{
    public ButFacRecChq(javax.swing.JTable tbl, int intIdx){
         super(tbl,intIdx);
    }
    public void butCLick() {

     int intNumFil = tblDat.getSelectedRow();
     String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
     String strCodCli=  (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
     String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));

     if(strCodCli.equals("")){
        MensajeInf("TIENE QUE ESCOJER UN CLIENTE..");
     }else{
        if(strEstApl.equals("N")){
          if(strAsigBan.equals("N")){
           listaFacturasRecChq(intNumFil, strCodCli);
          }else MensajeInf("YA TIENE ASIGNADO BANCO, NO ES POSIBLE AGREGAR Ó QUITAR FACTURAS..");
        }else MensajeInf("YA TIENE VALOR APLICADO, NO ES POSIBLE AGREGAR Ó QUITAR FACTURAS..");
     }
 }}

private void listaFacturasRecChq(int intNumFil , String strCodCli){
 String strSql="";
 String strAux=" text('N') AS est , 0 AS valapl,  ";
 String strSqlAux=" null AS estrel, ";
 //String strSqlAux2=" ,0 as valapl ";
 String strCodAux=" WHERE CASE WHEN estrel IS NULL THEN tx_numchq is null  ELSE  tx_numchq like '%%' END ";

 String strCodReg = (tblDat.getValueAt(intNumFil, INT_TBL_CODREG)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODREG).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODREG).toString()));
 if(!strCodReg.equals("")){
     strSqlAux="( SELECT x.co_emprel FROM  tbr_detrecdocpagmovinv AS x " +
     " WHERE x.co_emp="+objZafParSis.getCodigoEmpresa()+" and x.co_loc="+objZafParSis.getCodigoLocal()+" and x.co_tipdoc="+txtCodTipDoc.getText()+" " +
     " and x.co_doc="+txtCodDoc.getText()+"  and x.co_reg="+strCodReg+" " +
     " AND x.co_emprel=a1.co_emp  AND  x.co_locrel=a1.co_loc AND x.co_tipdocrel=a1.co_tipdoc  AND x.co_docrel=a1.co_doc " +
     " AND x.co_regrel=a1.co_reg and x.st_reg not in ('E') ) AS estrel, ";
 }



 int intEst=0;
 stbFacSel=new StringBuffer();
 for(int i=0; i<tblDat.getRowCount(); i++){
   if( i!=intNumFil ){
      String strDatFac = (tblDat.getValueAt(i, INT_TBL_DATFAC)==null?"":(tblDat.getValueAt(i, INT_TBL_DATFAC).equals("")?"":tblDat.getValueAt(i, INT_TBL_DATFAC).toString()));
      if(!strDatFac.equals("")){
          if(intEst==1) stbFacSel.append(" UNION ALL ");
          stbFacSel.append( strDatFac );
          intEst=1;
      }
  }}

// System.out.println("--> "+ stbFacSel.toString() );

  double dblMonChq = objUti.redondear( (tblDat.getValueAt(intNumFil, INT_TBL_VALCHQ)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_VALCHQ).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_VALCHQ).toString())), 2);
  

  String strDatFac = (tblDat.getValueAt(intNumFil, INT_TBL_DATFAC)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_DATFAC).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_DATFAC).toString()));
  if(!strDatFac.equals("")){
       strAux=" CASE WHEN x1.coemp IS NULL THEN 'N' ELSE 'S' END AS est ,  x1.valapl,  ";
       //strSqlAux2="";
  }

 strSql=" SELECT  * FROM ( " +


 " SELECT "+strAux+" x.* FROM ( " +
 " SELECT x.* FROM ( " +
 " SELECT  * FROM ( " +
 " select "+strSqlAux+" a1.co_banchq, a1.tx_numchq, a1.nd_monchq, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a2.tx_descor,  " +
 " a.ne_numdoc, a1.ne_diacre, a.fe_doc, a1.fe_ven, a1.nd_porret, (a1.mo_pag *-1 ) as mo_pag, a1.nd_abo, (a1.mo_pag+a1.nd_abo)*-1 as valpen " +
 " from tbm_cabmovinv as a  " +
 " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  " +
 " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc  ) " +
 " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+"  "+StrLisFacRetPorLoc+ " "
 + " and a1.co_tipdoc " +
    " IN ( select co_tipdoc  from tbr_tipdocdetprg where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and co_mnu= "+objZafParSis.getCodigoMenu()+" ) " +
 " and a.co_cli="+strCodCli+" and  a1.st_reg in ('A','C') " +
 " AND a.st_reg not in ('I','E') " +
 // "and nd_porret = 0 " -- Se comenta esta linea porque Cobranzas solicito el 29/dic/2014 que tambien deben aparecer las retenciones
 "and (a1.mo_pag+a1.nd_abo) < 0   order by a.ne_numdoc " +
 " ) AS x  "+strCodAux+" "+
 " ) AS x  ";

 if(intEst==1){
    strSql=strSql+" "+
    " LEFT JOIN ( "+stbFacSel.toString()+" ) AS x1 "+
    " ON (x1.coemp=x.co_emp and x1.coloc=x.co_loc and x1.cotipdoc=x.co_tipdoc and x1.codoc=x.co_doc and x1.coreg=x.co_reg ) " +
    " WHERE x1.coemp IS NULL ";
 }
 stbFacSel=null;


 strSql=strSql+" ) AS x  ";
  if(!strDatFac.equals("")){
       strSql=strSql+" "+
      " LEFT JOIN ( "+strDatFac+" ) AS x1 "+
      " ON (x1.coemp=x.co_emp and x1.coloc=x.co_loc and x1.cotipdoc=x.co_tipdoc and x1.codoc=x.co_doc and x1.coreg=x.co_reg ) ";
  }


  strSql=strSql+" ) AS x ORDER BY ne_numdoc  ";

  //System.out.println("--> "+strSql);

     ZafCxC11_01 obj = new  ZafCxC11_01(javax.swing.JOptionPane.getFrameForComponent(this), true,  objZafParSis, strSql, objUti.redondearBigDecimal(""+dblMonChq, objZafParSis.getDecimalesBaseDatos()) , objUti.redondearBigDecimal(""+dblMinAjuCenAut, objZafParSis.getDecimalesBaseDatos()), objUti.redondearBigDecimal(""+dblMaxAjuCenAut, objZafParSis.getDecimalesBaseDatos()) );
     obj.show();

    if(obj.acepta()){
        tblDat.setValueAt( obj.GetCamSel(2), intNumFil, INT_TBL_OBSERV);
        tblDat.setValueAt( obj.GetCamSel(1), intNumFil, INT_TBL_DATFAC);
        tblDat.setValueAt( obj.GetCamSel(3), intNumFil, INT_TBL_VAPLFAC);
        tblDat.setValueAt( "S", intNumFil, INT_TBL_ESTCAMFAC);
    }
    obj.dispose();
    obj=null;
}

 private class ButFacRecRet extends Librerias.ZafTableColBut.ZafTableColBut{
    public ButFacRecRet(javax.swing.JTable tbl, int intIdx){
         super(tbl,intIdx);
    }
    public void butCLick() {

     int intNumFil = tblDat.getSelectedRow();
     String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
     String strCodCli=  (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
     String strAsigBan=  (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));

     if(strCodCli.equals("")){
        MensajeInf("TIENE QUE ESCOJER UN CLIENTE..");
     }else{
        if(strEstApl.equals("N")){
          if(strAsigBan.equals("N"))
           listaFacturasRecRet(intNumFil, strCodCli);
     }}
 }}




private void listaFacturasRecRet(int intNumFil , String strCodCli){
 String strSql="";
 String strAux=" text('N') AS est , 0 AS valapl,  ";
 String strSqlAux=" null AS estrel, ";
 //String strSqlAux2=" ,0 as valapl ";
 String strCodAux=" WHERE CASE WHEN estrel IS NULL THEN tx_numchq is null  ELSE  tx_numchq like '%%' END ";

 String strCodReg = (tblDat.getValueAt(intNumFil, INT_TBL_CODREG)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODREG).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODREG).toString()));
 if(!strCodReg.equals("")){
     strSqlAux="( SELECT x.co_emprel FROM  tbr_detrecdocpagmovinv AS x " +
     " WHERE x.co_emp="+objZafParSis.getCodigoEmpresa()+" and x.co_loc="+objZafParSis.getCodigoLocal()+" and x.co_tipdoc="+txtCodTipDoc.getText()+" " +
     " and x.co_doc="+txtCodDoc.getText()+"  and x.co_reg="+strCodReg+" " +
     " AND x.co_emprel=a1.co_emp  AND  x.co_locrel=a1.co_loc AND x.co_tipdocrel=a1.co_tipdoc  AND x.co_docrel=a1.co_doc " +
     " AND x.co_regrel=a1.co_reg ) AS estrel, ";
 }

 

 int intEst=0;
 stbFacSel=new StringBuffer();
 for(int i=0; i<tblDat.getRowCount(); i++){
   if( i!=intNumFil ){
      String strDatFac = (tblDat.getValueAt(i, INT_TBL_DATFAC)==null?"":(tblDat.getValueAt(i, INT_TBL_DATFAC).equals("")?"":tblDat.getValueAt(i, INT_TBL_DATFAC).toString()));
      if(!strDatFac.equals("")){
          if(intEst==1) stbFacSel.append(" UNION ALL ");
          stbFacSel.append( strDatFac );
          intEst=1;
      }
  }}

// System.out.println("--> "+ stbFacSel.toString() );

  double dblMonChq = objUti.redondear( (tblDat.getValueAt(intNumFil, INT_TBL_VALCHQ)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_VALCHQ).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_VALCHQ).toString())), 2);
  

  String strDatFac = (tblDat.getValueAt(intNumFil, INT_TBL_DATFAC)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_DATFAC).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_DATFAC).toString()));
  if(!strDatFac.equals("")){
       strAux=" CASE WHEN x1.coemp IS NULL THEN 'N' ELSE 'S' END AS est ,  x1.valapl,  ";
       //strSqlAux2="";
  }

 strSql=" SELECT  * FROM ( " +


 " SELECT "+strAux+" x.* FROM ( " +
 " SELECT x.* FROM ( " +
 " SELECT  * FROM ( " +
 " select "+strSqlAux+" a1.co_banchq, a1.tx_numchq, a1.nd_monchq, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a2.tx_descor,  " +
 " a.ne_numdoc, a1.nd_porret as ne_diacre, a.fe_doc, a1.fe_ven, a1.nd_porret, (a1.mo_pag *-1 ) as mo_pag, a1.nd_abo, (a1.mo_pag+a1.nd_abo)*-1 as valpen " +
 " from tbm_cabmovinv as a  " +
 " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  " +
 " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc  ) " +
 " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+"  "+StrLisFacRetPorLoc+ " "
 + " and a1.co_tipdoc " +
    " IN ( select co_tipdoc  from tbr_tipdocdetprg where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and co_mnu= "+objZafParSis.getCodigoMenu()+" ) " +
 "  and a.co_cli="+strCodCli+" and  a1.st_reg in ('A','C') " +
 " AND a.st_reg not in ('I','E') and nd_porret > 0  and (a1.mo_pag+a1.nd_abo) < 0   order by a.ne_numdoc " +
 " ) AS x  "+strCodAux+" "+
 " ) AS x  ";
 
 if(intEst==1){
    strSql=strSql+" "+
    " LEFT JOIN ( "+stbFacSel.toString()+" ) AS x1 "+
    " ON (x1.coemp=x.co_emp and x1.coloc=x.co_loc and x1.cotipdoc=x.co_tipdoc and x1.codoc=x.co_doc and x1.coreg=x.co_reg ) " +
    " WHERE x1.coemp IS NULL ";
 }
 stbFacSel=null;


 strSql=strSql+" ) AS x  ";
  if(!strDatFac.equals("")){
       strSql=strSql+" "+
      " LEFT JOIN ( "+strDatFac+" ) AS x1 "+
      " ON (x1.coemp=x.co_emp and x1.coloc=x.co_loc and x1.cotipdoc=x.co_tipdoc and x1.codoc=x.co_doc and x1.coreg=x.co_reg ) ";
  }


  strSql=strSql+" ) AS x ORDER BY ne_numdoc  ";

     ZafCxC11_01 obj = new  ZafCxC11_01(javax.swing.JOptionPane.getFrameForComponent(this), true,  objZafParSis, strSql, objUti.redondearBigDecimal(""+dblMonChq, objZafParSis.getDecimalesBaseDatos()), objUti.redondearBigDecimal(""+dblMinAjuCenAut, objZafParSis.getDecimalesBaseDatos()), objUti.redondearBigDecimal(""+dblMaxAjuCenAut, objZafParSis.getDecimalesBaseDatos())  );
     obj.show();

    if(obj.acepta()){
        tblDat.setValueAt( obj.GetCamSel(2), intNumFil, INT_TBL_OBSERV);
        tblDat.setValueAt( obj.GetCamSel(1), intNumFil, INT_TBL_DATFAC);
        tblDat.setValueAt( obj.GetCamSel(3), intNumFil, INT_TBL_VAPLFAC);
        tblDat.setValueAt( "S", intNumFil, INT_TBL_ESTCAMFAC);
    }
    obj.dispose();
    obj=null;
}

 
    private boolean configurarVenConClientes() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_ide");
            arlCam.add("a.tx_nom");
          
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("RUC/CI");
            arlAli.add("Nom.Cli.");
          
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("130");
            arlAncCol.add("350");
            
            //Armar la sentencia SQL.
            String  strSQL;

             strSQL="SELECT a.co_cli, a.tx_ide, a.tx_nom  FROM tbm_cli as a " +
             " WHERE a.co_emp ="+objZafParSis.getCodigoEmpresa()+"  and a.st_reg IN('A')  and a.st_cli='S' ORDER BY a.tx_nom  ";
             StrLisFacRetPorLoc="";
               int cod = objZafParSis.getCodigoUsuario();
            if(!(objZafParSis.getCodigoUsuario()==1) ){
                
               if(!objUti.utilizarClientesEmpresa(objZafParSis, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), objZafParSis.getCodigoUsuario())){
                  strSQL = " SELECT a.co_cli, a.tx_ide, a.tx_nom FROM tbr_cliloc as b " +
                  " inner join tbm_cli as a on (a.co_emp=b.co_emp and a.co_cli=b.co_cli) " +
                  " WHERE  b.co_emp ="+objZafParSis.getCodigoEmpresa()+" and b.co_loc="+objZafParSis.getCodigoLocal()+" and  a.st_reg  in ('A') ORDER BY a.tx_nom ";
                  StrLisFacRetPorLoc = "  AND a.co_loc="+objZafParSis.getCodigoLocal()+"   ";
               }
            }
             
            objVenConCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;

        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


   private boolean configurarVenConBanco() {
      boolean blnRes=true;
       try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_reg");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");

            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor");
            arlAli.add("Descripción");

            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("130");
            arlAncCol.add("350");

            //Armar la sentencia SQL.
            String  strSQL;

            strSQL="select a.co_reg, a.tx_descor, a.tx_deslar from tbm_var as a where a.co_grp=8  order by a.tx_deslar ";

            objVenConBan=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;

        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }




public void setEditable(boolean editable) {
  if (editable==true){
    objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);

 }else{  objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI); }
}





    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panGenTabGen = new javax.swing.JPanel();
        panCabRecChq = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        txtDesLarTipDoc = new javax.swing.JTextField();
        txtDesCodTitpDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        txtCodDoc = new javax.swing.JTextField();
        txtDesRec = new javax.swing.JTextField();
        butRec = new javax.swing.JButton();
        txtCodRec = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblNumDoc = new javax.swing.JLabel();
        lblCodDoc1 = new javax.swing.JLabel();
        valDoc = new javax.swing.JTextField();
        panDetRecChq = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        panNor.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("titulo"); // NOI18N
        panNor.add(lblTit, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        panGenTabGen.setLayout(new java.awt.BorderLayout());

        panCabRecChq.setPreferredSize(new java.awt.Dimension(100, 80));
        panCabRecChq.setLayout(null);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel2.setText("Recaudador:"); // NOI18N
        panCabRecChq.add(jLabel2);
        jLabel2.setBounds(10, 30, 90, 20);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel3.setText("Tipo de documento:"); // NOI18N
        panCabRecChq.add(jLabel3);
        jLabel3.setBounds(10, 10, 110, 20);

        lblCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodDoc.setText("Código del documento:"); // NOI18N
        panCabRecChq.add(lblCodDoc);
        lblCodDoc.setBounds(10, 50, 120, 20);

        txtDesLarTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        panCabRecChq.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(190, 10, 230, 20);

        txtDesCodTitpDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesCodTitpDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCodTitpDocActionPerformed(evt);
            }
        });
        txtDesCodTitpDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCodTitpDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCodTitpDocFocusLost(evt);
            }
        });
        panCabRecChq.add(txtDesCodTitpDoc);
        txtDesCodTitpDoc.setBounds(120, 10, 70, 20);

        butTipDoc.setText(".."); // NOI18N
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabRecChq.add(butTipDoc);
        butTipDoc.setBounds(420, 10, 20, 20);

        txtCodDoc.setBackground(objZafParSis.getColorCamposSistema());
        panCabRecChq.add(txtCodDoc);
        txtCodDoc.setBounds(120, 50, 90, 20);

        txtDesRec.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesRecActionPerformed(evt);
            }
        });
        txtDesRec.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesRecFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesRecFocusLost(evt);
            }
        });
        panCabRecChq.add(txtDesRec);
        txtDesRec.setBounds(190, 30, 230, 20);

        butRec.setText(".."); // NOI18N
        butRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRecActionPerformed(evt);
            }
        });
        panCabRecChq.add(butRec);
        butRec.setBounds(420, 30, 20, 20);

        txtCodRec.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodRecActionPerformed(evt);
            }
        });
        txtCodRec.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodRecFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodRecFocusLost(evt);
            }
        });
        panCabRecChq.add(txtCodRec);
        txtCodRec.setBounds(120, 30, 70, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel6.setText("Fecha del documento:"); // NOI18N
        panCabRecChq.add(jLabel6);
        jLabel6.setBounds(460, 10, 120, 20);

        txtNumDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        panCabRecChq.add(txtNumDoc);
        txtNumDoc.setBounds(580, 30, 90, 20);

        lblNumDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNumDoc.setText("Número de documento:"); // NOI18N
        panCabRecChq.add(lblNumDoc);
        lblNumDoc.setBounds(460, 30, 120, 20);

        lblCodDoc1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodDoc1.setText("Valor del Documento:"); // NOI18N
        panCabRecChq.add(lblCodDoc1);
        lblCodDoc1.setBounds(460, 50, 120, 20);

        valDoc.setBackground(objZafParSis.getColorCamposSistema());
        valDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        valDoc.setText("0.00");
        panCabRecChq.add(valDoc);
        valDoc.setBounds(580, 50, 90, 20);

        panGenTabGen.add(panCabRecChq, java.awt.BorderLayout.NORTH);

        panDetRecChq.setLayout(new java.awt.BorderLayout());

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
        jScrollPane1.setViewportView(tblDat);

        panDetRecChq.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panGenTabGen.add(panDetRecChq, java.awt.BorderLayout.CENTER);

        tabGen.addTab("General", panGenTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        // TODO add your handling code here:
        txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        // TODO add your handling code here:
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        // TODO add your handling code here:
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCodTitpDoc.setText("");
                txtDesLarTipDoc.setText("");
            }else
                BuscarTipDoc("a.tx_deslar",txtDesLarTipDoc.getText(),2);
        }else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
}//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCodTitpDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocActionPerformed
        // TODO add your handling code here:
        txtDesCodTitpDoc.transferFocus();
}//GEN-LAST:event_txtDesCodTitpDocActionPerformed

    private void txtDesCodTitpDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocFocusGained
        // TODO add your handling code here:
        strDesCodTipDoc=txtDesCodTitpDoc.getText();
        txtDesCodTitpDoc.selectAll();
}//GEN-LAST:event_txtDesCodTitpDocFocusGained

    private void txtDesCodTitpDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocFocusLost
        // TODO add your handling code here:

        if (!txtDesCodTitpDoc.getText().equalsIgnoreCase(strDesCodTipDoc)) {
            if (txtDesCodTitpDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCodTitpDoc.setText("");
                txtDesLarTipDoc.setText("");
            }else
                BuscarTipDoc("a.tx_descor",txtDesCodTitpDoc.getText(),1);
        }else
            txtDesCodTitpDoc.setText(strDesCodTipDoc);

    }//GEN-LAST:event_txtDesCodTitpDocFocusLost

    

public void BuscarTipDoc(String campo,String strBusqueda,int tipo){
  objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
  if(objVenConTipdoc.buscar(campo, strBusqueda )) {
      txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
      txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
      txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
      strCodTipDoc=objVenConTipdoc.getValueAt(1);
        
  }else{
        objVenConTipdoc.setCampoBusqueda(tipo);
        objVenConTipdoc.cargarDatos();
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE) {
           txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
           txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
           txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
           strCodTipDoc=objVenConTipdoc.getValueAt(1);
         }else{
           txtCodTipDoc.setText(strCodTipDoc); 
           txtDesCodTitpDoc.setText(strDesCodTipDoc);
           txtDesLarTipDoc.setText(strDesLarTipDoc);
  }}}
    
 


    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        // TODO add your handling code here:
        objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
        objVenConTipdoc.setCampoBusqueda(1);
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE) {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
            strCodTipDoc=objVenConTipdoc.getValueAt(1);
        }
}//GEN-LAST:event_butTipDocActionPerformed

    private void txtDesRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesRecActionPerformed
        // TODO add your handling code here:
        txtDesRec.transferFocus();
}//GEN-LAST:event_txtDesRecActionPerformed

    private void txtDesRecFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesRecFocusGained
        // TODO add your handling code here:
        strDesSol=txtDesRec.getText();
        txtDesRec.selectAll();
}//GEN-LAST:event_txtDesRecFocusGained

    private void txtDesRecFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesRecFocusLost
        // TODO add your handling code here:
        if (!txtDesRec.getText().equalsIgnoreCase(strDesSol)) {
            if(txtDesRec.getText().equals("")) {
                txtCodRec.setText("");
                txtDesRec.setText("");
            }else
                BuscarRecaudador("a.tx_nom",txtDesRec.getText(),1);
        }else
            txtDesRec.setText(strDesSol);
}//GEN-LAST:event_txtDesRecFocusLost

    private void butRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRecActionPerformed
        // TODO add your handling code here:
        objVenConRec.setTitle("Listado de Recaudador");
        objVenConRec.setCampoBusqueda(1);
        objVenConRec.show();
        if (objVenConRec.getSelectedButton()==objVenConRec.INT_BUT_ACE) {
            txtCodRec.setText(objVenConRec.getValueAt(1));
            txtDesRec.setText(objVenConRec.getValueAt(2));
        }
}//GEN-LAST:event_butRecActionPerformed

    private void txtCodRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodRecActionPerformed
        // TODO add your handling code here:
        txtCodRec.transferFocus();
}//GEN-LAST:event_txtCodRecActionPerformed

    private void txtCodRecFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodRecFocusGained
        // TODO add your handling code here:
        strCodSol=txtCodRec.getText();
        txtCodRec.selectAll();
}//GEN-LAST:event_txtCodRecFocusGained

    private void txtCodRecFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodRecFocusLost
        // TODO add your handling code here:
        if (!txtCodRec.getText().equalsIgnoreCase(strCodSol)) {
            if(txtCodRec.getText().equals("")) {
                txtCodRec.setText("");
                txtDesRec.setText("");
            }else
                BuscarRecaudador("a.co_usr",txtCodRec.getText(),0);
        }else
            txtCodRec.setText(strCodSol);
}//GEN-LAST:event_txtCodRecFocusLost

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();

    }//GEN-LAST:event_formInternalFrameOpened

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
           exitForm();
           
    }//GEN-LAST:event_formInternalFrameClosing


    /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de salidad.
     */
    private void exitForm(){

        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            cerrarObj();
            System.gc();
            dispose();
        }

    }


    public void cerrarObj(){
        try{
            objVenConCli.dispose(); //***
            objVenConCli=null;  //****
            objVenConBan.dispose();
            objVenConBan=null;
            objVenConTipdoc.dispose();
            objVenConTipdoc=null;
            objVenConRec.dispose();
            objVenConRec=null;

            objUti=null;
            objTooBar=null;
          
            objZafParSis=null;
            txtFecDoc=null;
           

        }
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }


    
    
 public void BuscarRecaudador(String campo,String strBusqueda,int tipo){
  objVenConRec.setTitle("Listado de Recaudador");
  if(objVenConRec.buscar(campo, strBusqueda )) {
      txtCodRec.setText(objVenConRec.getValueAt(1));
      txtDesRec.setText(objVenConRec.getValueAt(2));
  }else{
        objVenConRec.setCampoBusqueda(tipo);
        objVenConRec.cargarDatos();
        objVenConRec.show();
        if (objVenConRec.getSelectedButton()==objVenConRec.INT_BUT_ACE) {
            txtCodRec.setText(objVenConRec.getValueAt(1));
            txtDesRec.setText(objVenConRec.getValueAt(2));
        }else{
            txtCodRec.setText(strCodSol);
            txtDesRec.setText(strDesSol);
  }}}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butRec;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblCodDoc1;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panCabRecChq;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panDetRecChq;
    private javax.swing.JPanel panGenTabGen;
    private javax.swing.JPanel panNor;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodRec;
    private javax.swing.JTextField txtDesCodTitpDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDesRec;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField valDoc;
    // End of variables declaration//GEN-END:variables



      private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }





      public class mitoolbar extends ZafToolBar{
        public mitoolbar(javax.swing.JInternalFrame jfrThis){
            super(jfrThis, objZafParSis);
        }


public boolean anular() {
  boolean blnRes=false;
  java.sql.Connection  conn;
  String strAux="";
  int intCodDoc=0;
  int intCodTipDoc=0;
  try{
     conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);

        strAux=objTooBar.getEstadoRegistro();
        if (strAux.equals("Eliminado")) {
            MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
            blnRes=true;
        }
        if (strAux.equals("Anulado")) {
            MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
            blnRes=true;
        }

      intCodDoc=Integer.parseInt(txtCodDoc.getText());
      intCodTipDoc=Integer.parseInt(txtCodTipDoc.getText());


     if(obtenerEstAnu(conn)){
      if(!blnRes){
        if(anularReg(conn, intCodTipDoc, intCodDoc )){
            conn.commit();
            blnRes=true;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
        }else conn.rollback();
      }else blnRes=false;
     }

      conn.close();
      conn=null;
   }}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}



/**
 * Anula el registro
 * @param conn  coneccion de la base
 * @param intCodTipDoc  codigo del tipo de documento
 * @param intCodDoc   codigo del documento
 * @return  true si se pudo anular false no se puedo anular
 */
private boolean anularReg(java.sql.Connection conn, int intCodTipDoc, int intCodDoc ){
  boolean blnRes=false;
  java.sql.Statement stmLoc, stmLoc01, stmLoc02;
  java.sql.ResultSet rstLoc01, rstLoc02;
  String strSql="";
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();
        stmLoc01=conn.createStatement();
        stmLoc02=conn.createStatement();

        strSql="SELECT co_reg FROM tbm_detrecdoc WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" " +
        " and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and ( nd_valapl > 0 or co_tipdoccon IS NOT NULL ) ";
        rstLoc01=stmLoc01.executeQuery(strSql);
        if(rstLoc01.next()){
            MensajeInf("NO SE PUEDE ANULAR PORQUE YA TIENE UN BANCO ASIGNADO O UN DEPO REALIZADO ..");
        }else{

           strSql="SELECT st_imp FROM tbm_cabrecdoc WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" " +
           " and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and st_imp='N' ";
           rstLoc02=stmLoc02.executeQuery(strSql);
           if(rstLoc02.next()){

               strSql="UPDATE tbm_cabrecdoc SET st_reg='I', st_regrep='M' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" " +
               " and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+"  ";

               strSql+=" ; UPDATE tbm_detrecdoc SET st_reg='I', st_regrep='M' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" " +
               " and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+"  ";

               strSql+=" ; UPDATE tbr_detrecdocpagmovinv SET st_reg='I', st_regrep='M' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND" +
               " co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+"  ";

               strSql+=" ; UPDATE tbm_pagmovinv SET nd_monchq=0, fe_venchq=null, fe_recchq=null, tx_numchq=null, co_banchq=null, " +
               " tx_numctachq=null, st_pos='N', st_entsop='N', st_regrep='M' FROM ( "+
               "  SELECT  co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel FROM tbr_detrecdocpagmovinv  WHERE " +
               "  co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
               "  AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" "+
               " ) AS x WHERE tbm_pagmovinv.co_emp=x.co_emprel AND tbm_pagmovinv.co_loc=x.co_locrel AND tbm_pagmovinv.co_tipdoc=x.co_tipdocrel " +
               " AND tbm_pagmovinv.co_doc=x.co_docrel AND tbm_pagmovinv.co_reg=x.co_regrel ; ";

               stmLoc.executeUpdate(strSql);
               blnRes=true;

           }else{
               MensajeInf("NO SE PUEDE ANULAR PORQUE YA ESTA IMPRESO EL DOCUMENTO..");
           }
           rstLoc02.close();
           rstLoc02=null;

        }
        rstLoc01.close();
        rstLoc01=null;

       
       stmLoc.close();
       stmLoc=null;
       stmLoc01.close();
       stmLoc01=null;
       stmLoc02.close();
       stmLoc02=null;
    
  }}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}







        public void clickAceptar() {
            setEstadoBotonMakeFac();
        }



public void clickAnterior() {
 try{
  if(rstCab != null ) {
     abrirCon();
     if(!rstCab.isFirst()) {
         if(blnHayCam) {
              if(isRegPro()) {
                 rstCab.previous();
                 refrescaDatos(CONN_GLO, rstCab);
           }}else {
               rstCab.previous();
               refrescaDatos(CONN_GLO, rstCab);
       }}
      CerrarCon();

  }}catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
    catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
}




        public void clickAnular() {

        }


        public void clickConsultar() {
//            clnTextos();
            noEditable(false);

            cargarTipoDoc (2);

	}

        public void clickEliminar() {
//          noEditable(false);
	}





public void clickFin(){
 try{
    if(rstCab != null ){
     abrirCon();
      if(!rstCab.isLast()){
       if (blnHayCam){
        if(isRegPro()){
           rstCab.last();
           refrescaDatos(CONN_GLO, rstCab);
       }}else{
            rstCab.last();
            refrescaDatos(CONN_GLO, rstCab);
      }}
     CerrarCon();
   }}catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }
     catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
}






public void clickInicio(){
 try{
   if(rstCab != null ){
       abrirCon();
     if(!rstCab.isFirst()) {
      if(blnHayCam){
       if(isRegPro()){
          rstCab.first();
          refrescaDatos(CONN_GLO, rstCab);
        }}else{
            rstCab.first();
            refrescaDatos(CONN_GLO, rstCab);
        }}
      CerrarCon();
  }}catch (java.sql.SQLException e) {  objUti.mostrarMsgErr_F1(this, e); }
    catch (Exception e) { objUti.mostrarMsgErr_F1(this, e);  }
}




public void clickInsertar() {
 try{
    clnTextos();
 //   noEditable(false);
//
    java.awt.Color colBack;
    colBack = txtCodDoc.getBackground();
    txtCodDoc.setEditable(false);
    txtCodDoc.setBackground(colBack);

    valDoc.setEditable(false);
    valDoc.setBackground(colBack);

    
    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
    java.util.Date dateObj =datFecAux;
    java.util.Calendar calObj = java.util.Calendar.getInstance();
    calObj.setTime(dateObj);
    txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
    calObj.get(java.util.Calendar.MONTH)+1     ,
    calObj.get(java.util.Calendar.YEAR)        );

    setEditable(true);
    objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
    objTblMod.setDataModelChanged(false);

    cargarTipoDoc(1);

      if(rstCab!=null) {
          rstCab.close();
          rstCab=null;
      }

   }catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e); }
}




        public void setEstadoBotonMakeFac(){
            switch(getEstado()) {
                case 'l'://Estado 0 => Listo
                    break;
                case 'x'://Estado click modificar
                    break;
                case 'c'://Estado Consultar
                    break;
                case 'y':
                    break;
                case 'z':
                    break;
                default:
                    break;
            }
        }



public void clickSiguiente(){
 try{
   if(rstCab != null ){
       abrirCon();
      if(!rstCab.isLast()) {
       if(blnHayCam || objTblMod.isDataModelChanged()) {
          if(isRegPro()) {
              rstCab.next();
              refrescaDatos(CONN_GLO, rstCab);
          }}else{
               rstCab.next();
               refrescaDatos(CONN_GLO, rstCab);
       }}
    CerrarCon();
  }}catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
    catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
}








public boolean eliminar() {
  boolean blnRes=false;
//  java.sql.Connection  conn;
//  try{
//     conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//     if(conn!=null){
//       conn.setAutoCommit(false);
//
//        strAux=objTooBar.getEstadoRegistro();
//        if (strAux.equals("Eliminado")) {
//            MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
//            blnRes=true;
//        }
//
//        if(!strEstAut.equals("P")){
//         String strEst="";
//         if(strEstAut.equals("A"))strEst="AUTORIZADO";
//         if(strEstAut.equals("D"))strEst="DENEGADO";
//         if(strEstAut.equals("C"))strEst="CANCELADO";
//         MensajeInf("El documento ya está "+strEst+".\nNo es posible modifcar un documento "+strEst+".");
//         blnRes=true;
//        }
//
//
//      if(!blnRes){
//        if(eliminarReg(conn)){
//            conn.commit();
//            blnRes=true;
//            objTooBar.setEstadoRegistro("Eliminado");
//            blnHayCam=false;
//        }else conn.rollback();
//       }else blnRes=false;
//
//      conn.close();
//      conn=null;
//   }}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
//    catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}




private boolean eliminarReg(java.sql.Connection conn){
  boolean blnRes=false;
//  java.sql.Statement stmLoc;
//  String strSql="";
//  try{
//     if(conn!=null){
//        stmLoc=conn.createStatement();
//        strSql="UPDATE tbm_cabsoldevven SET st_reg='E' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
//         "AND  co_tipdoc="+txtCodTipDoc.getText()+" AND co_doc="+txtCodDoc.getText();
//        stmLoc.executeUpdate(strSql);
//
//       stmLoc.close();
//       stmLoc=null;
//     blnRes=true;
//  }}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
//    catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}





/**
 * validad campos requeridos antes de insertar o modificar
 * @return  true si esta todo bien   false   falta algun dato 
 */
 private boolean validaCampos(){

   int intExiDatTbl=0;
   String strMens="RETENCIONES";
   
   if(!(objZafParSis.getCodigoMenu()==1749)){
       strMens="CHEQUE";
   }

  if(txtDesCodTitpDoc.getText().equals("") ){
    tabGen.setSelectedIndex(0);
    MensajeInf("El campo << Tipo Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtDesCodTitpDoc.requestFocus();
    return false;
    }

    if(!txtFecDoc.isFecha()){
      tabGen.setSelectedIndex(0);
      MensajeInf("El campo << Fecha Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
      txtFecDoc.requestFocus();
      return false;
    }


   if(txtNumDoc.getText().equals("") ){
    tabGen.setSelectedIndex(0);
    MensajeInf("El campo << Número de Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtNumDoc.requestFocus();
    return false;
    }


     for(int i=0; i<tblDat.getRowCount(); i++){
      if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){

          intExiDatTbl=1;

         if(!(objZafParSis.getCodigoMenu()==1749)){
          if( ((tblDat.getValueAt(i, INT_TBL_CODBAN)==null?"":(tblDat.getValueAt(i, INT_TBL_CODBAN).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODBAN).toString())).equals("")) ){
                MensajeInf("SELECCIONE EL BANCO. ");
                tblDat.repaint();
                tblDat.requestFocus();
                return false;
          }
        }
           if( ((tblDat.getValueAt(i, INT_TBL_NUMCHQ)==null?"":(tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString())).equals("")) ){
                MensajeInf("DIGITE EL NUMERO DEL "+strMens+". ");
                tblDat.repaint();
                tblDat.requestFocus();
                tblDat.editCellAt(i, INT_TBL_NUMCHQ);
                return false;
          }

          if( Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_VALCHQ)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALCHQ).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALCHQ).toString())) ) <= 0 ){
                MensajeInf("DIGITE EL VALOR DEL "+strMens+". ");
                tblDat.repaint();
                tblDat.requestFocus();
                tblDat.editCellAt(i, INT_TBL_VALCHQ);
                return false;
          }
         if(!(objZafParSis.getCodigoMenu()==1749)){
          if( ((tblDat.getValueAt(i, INT_TBL_FECVEN)==null?"":(tblDat.getValueAt(i, INT_TBL_FECVEN).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_FECVEN).toString())).equals("")) ){
                MensajeInf("DIGITE LA FECHA DE VENCIMIENTO DEL CHEQUE. ");
                tblDat.repaint();
                tblDat.requestFocus();
                tblDat.editCellAt(i, INT_TBL_FECVEN);
                return false;
          }
        }
      }
     }


    if(intExiDatTbl==0){
        MensajeInf("NO HAY DATOS EN DETALLE INGRESE DATOS.... ");
        return false;
    }

   return true;
 }







public boolean insertar(){
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intCodDoc=0;
  ZafGenOdPryTra objGenOD=new ZafGenOdPryTra();
  ZafCnfDoc objValCnf=new ZafCnfDoc(objZafParSis,this);
  try{
      

    if(validaCampos()){

     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);
       stmLoc=conn.createStatement();

        INT_ENV_REC_IMP_GUIA = 0;

        strSql="SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 end as co_doc  FROM tbm_cabrecdoc WHERE " +
        " co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" "+
        " and co_tipDoc = "+txtCodTipDoc.getText();
        rstLoc = stmLoc.executeQuery(strSql);
        if(rstLoc.next())
            intCodDoc = rstLoc.getInt("co_doc");
         rstLoc.close();
         rstLoc=null;

          if(objZafParSis.getCodigoMenu()==1749){
              if(insertarCab(conn, intCodDoc)){
               if(reestructurarFacturas(conn)){
               if(insertarDetRecRet(conn, intCodDoc)){
                 conn.commit();
                 txtCodDoc.setText(""+intCodDoc);
                 blnRes=true;
                 cargarDetIns(conn, intCodDoc);
              }else conn.rollback();
             }else conn.rollback();
            }else conn.rollback();
          }else{
              if(insertarCab(conn, intCodDoc)){
               if(reestructurarFacturas(conn)){
               if(insertarDetRecChq(conn, intCodDoc)){
                if(_getObtenerTodFacVen(conn)){
                 conn.commit();
                 txtCodDoc.setText(""+intCodDoc);
                 blnRes=true;
                 cargarDetIns(conn, intCodDoc);

                    if(INT_ENV_REC_IMP_GUIA==1){
                     ZafGenGuiRem objZafGuiRem=new ZafGenGuiRem();
                     int numDoc=0;
                     Iterator itVecOd=vecOD.iterator();
                     while (itVecOd.hasNext()){
                        String strDat=(String)itVecOd.next();
                        String[] strArrDat=strDat.split("-");                        
                        int intCodEmp=Integer.parseInt((String)strArrDat[0]);
                        int intCodLoc=Integer.parseInt((String)strArrDat[1]);
                        int intCodTipDoc2=Integer.parseInt((String)strArrDat[2]);
                        int intCodDocu=Integer.parseInt((String)strArrDat[3]);                        
                        
                        if(!objValCnf.isDocIngPenCnfxFac(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, "I")){
                            if(!(objGenOD.validarODExs(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu))){
                                if(objGenOD.generarNumOD(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, false)){
                                    conn.commit();
                                    String strIp=objGenOD.obtenerIpSerImp(conn);
                                    objGenOD.imprimirOdLocal(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, strIp);
                                    //objGenOD.generarTermL(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu);
                                    //boolean booRetTer=objZafGuiRem.generarProTermL(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, 0);
                                    objGenOD.generarTermL(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu);
                                }else{
                                    conn.rollback();
                                }
                            }
                        }
                     }
                     vecOD.clear();                        

                        
                    } //enviarRequisitoImp(strIpImpGuia, intPuertoImpGuia);


                }else conn.rollback();
               }else conn.rollback();
              }else conn.rollback();
             }else conn.rollback();
          }
       stmLoc.close();
       stmLoc=null;
       conn.close();
       conn=null;
    }

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


private void enviarRequisitoImp(String strIp, int intPuerto){
    try{
       //System.out.println(" OKI----->  ");
       java.net.Socket s1 = new java.net.Socket(strIp, intPuerto);
       java.io.DataOutputStream dos = new java.io.DataOutputStream(s1.getOutputStream());
       dos.writeInt(1);

       dos.close();
       s1.close();

     }catch (java.net.ConnectException connExc){   System.err.println("OCURRIO UN ERROR 1 "+connExc ); }
      catch (IOException e){  System.err.println("OCURRIO UN ERROR 2 "+ e );  }
}




/**
 * Permite recargar los datos de la tabla despues de insertar o modificar con objetivo de tener ejem:  codigo del registro
 * que eso da cuando se insertar
 * @param conn   coneccion de la base
 * @param intCodDoc   codigo del documento
 * @return  true si se consulto bien   false si hay algun error. 
 */
private boolean cargarDetIns(java.sql.Connection conn, int intCodDoc ){
  boolean blnRes=false;
  java.sql.Statement stmLoc, stmLoc01;
  java.sql.ResultSet rstLoc, rstLoc01;
  String strSql="", strDatFac="";
  Vector vecData;
  int intCon=0;
  double dblCanAplFac=0;
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();

       vecData = new Vector();

        strSql="SELECT CASE WHEN co_tipdoccon IS NULL THEN 'N' ELSE 'S' END AS AsigBan,  CASE WHEN nd_valapl > 0 THEN 'S' ELSE  'N' END AS estApl, " +
        " a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, a.co_cli, a1.tx_nom,  a.co_banchq,  a.tx_numctachq, a.tx_numchq, a.fe_recchq,  " +
        " a.fe_venchq, a.nd_monchq, a.tx_obs1, a.nd_valapl, a.fe_asitipdoccon, a.st_asidocrel, a.st_regrep, a.st_reg,  a2.tx_descor, a2.tx_deslar  " +
        " FROM tbm_detrecdoc as a " +
        " INNER JOIN tbm_cli as a1 ON (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli) " +
        " LEFT  JOIN tbm_var as a2 ON (a2.co_reg=a.co_banchq ) "+
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" " +
        " and a.co_tipdoc="+txtCodTipDoc.getText()+"  and a.co_Doc="+intCodDoc+" AND a.st_reg NOT IN ('E') ";
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

           java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL_LINEA,"");
            vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
            vecReg.add(INT_TBL_BUTCLI, "" );
            vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nom") );
            vecReg.add(INT_TBL_CODBAN, rstLoc.getString("co_banchq") );
            vecReg.add(INT_TBL_DSCBAN, rstLoc.getString("tx_descor") );
            vecReg.add(INT_TBL_BUTBAN, "" );
            vecReg.add(INT_TBL_NOMBAN, rstLoc.getString("tx_deslar") );
            vecReg.add(INT_TBL_NUMCTA, rstLoc.getString("tx_numctachq") );
            vecReg.add(INT_TBL_NUMCHQ, rstLoc.getString("tx_numchq") );
            vecReg.add(INT_TBL_VALCHQ, rstLoc.getString("nd_monchq") );
            vecReg.add(INT_TBL_FECVEN, objUti.formatearFecha(rstLoc.getDate("fe_venchq"), "dd/MM/yyyy") ); // rstLoc.getString("fe_venchq") );
            vecReg.add(INT_TBL_BUTFAC, "" );
            vecReg.add(INT_TBL_OBSERV, rstLoc.getString("tx_obs1") );


            strDatFac="";
            intCon=0;
            dblCanAplFac=0;
            strSql="SELECT  a.co_emprel,  a.co_locrel, a.co_tipdocrel, a.co_docrel, a.co_regrel, a1.nd_monchq as valapl FROM " +
            " tbr_detrecdocpagmovinv AS a " +
            " INNER JOIN tbm_pagmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel " +
            " and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) " +
            " WHERE a.co_emp="+rstLoc.getInt("co_emp")+" and a.co_loc="+rstLoc.getInt("co_loc")+" and a.co_tipdoc="+rstLoc.getInt("co_tipdoc")+" " +
            " and a.co_doc= "+rstLoc.getInt("co_doc")+" and a.co_reg="+rstLoc.getInt("co_reg")+" AND a.st_reg NOT IN ('E') ";
            rstLoc01=stmLoc01.executeQuery(strSql);
            while(rstLoc01.next()){
                if(intCon==1) strDatFac+=" UNION ALL ";
                strDatFac +=" SELECT "+rstLoc01.getInt("co_emprel")+" as coemp, "+rstLoc01.getInt("co_locrel")+" as coloc, " +
                " "+rstLoc01.getInt("co_tipdocrel")+" as cotipdoc, "+rstLoc01.getInt("co_docrel")+" as codoc," +
                " "+rstLoc01.getInt("co_regrel")+" as coreg, "+rstLoc01.getDouble("valapl")+" as valapl  ";
                intCon=1;
                dblCanAplFac+=rstLoc01.getDouble("valapl");
            }
            rstLoc01.close();
            rstLoc01=null;

            vecReg.add(INT_TBL_DATFAC, strDatFac );
            vecReg.add(INT_TBL_VAPLFAC, ""+dblCanAplFac );
            vecReg.add(INT_TBL_VALCHQORI, rstLoc.getString("nd_monchq") );
            vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg") );
            vecReg.add(INT_TBL_ESTAPL, rstLoc.getString("estApl") );
            vecReg.add(INT_TBL_CODCLIORI, rstLoc.getString("co_cli") );
            vecReg.add(INT_TBL_ASIGBAN, rstLoc.getString("AsigBan") );
            vecReg.add(INT_TBL_ESTCAMFAC, "N" );
            
            vecData.add(vecReg);
       }
        objTblMod.setData(vecData);
        tblDat .setModel(objTblMod);
        
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;
       stmLoc01.close();
       stmLoc01=null;
    blnRes=true;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


private boolean insertarCab(java.sql.Connection conn, int intCodDoc){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 String strFecDoc="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        //************  PERMITE SABER SI EL NUMERO DE recepción ESTA DUPLICADO  *****************/
             strSql ="select count(ne_numdoc1) as num from tbm_cabrecdoc WHERE " +
                     " co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" " +
                     "and co_tipdoc="+txtCodTipDoc.getText()+" and ne_numdoc1="+txtNumDoc.getText();
              rstLoc = stmLoc.executeQuery(strSql);
              if(rstLoc.next()){
                  if(rstLoc.getInt("num") >= 1 ){
                        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
                        String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";
                        strMsg=" No. de Recepcion ya existe... ?";
                        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE,null);
                        blnRes=true;
              }}
              rstLoc.close();
              rstLoc=null;
              if(blnRes) return false;
              blnRes=false;
        //***********************************************************************************************/

        strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";
        
        strSql="INSERT INTO tbm_cabrecdoc(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc1, co_usrrec, st_imp, tx_obs1, tx_obs2 " +
        " ,nd_mondoc, st_reg, fe_ing, co_usring, tx_coming, st_regrep ) "+
        " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" " +
        " ,'"+strFecDoc+"', "+txtNumDoc.getText()+", "+(txtCodRec.getText().equals("")?null:txtCodRec.getText())+", 'N', '','' " +
        " ,"+(valDoc.getText().equals("")?"0":valDoc.getText())+", 'A', "+objZafParSis.getFuncionFechaHoraBaseDatos()+","+objZafParSis.getCodigoUsuario()+" " +
        " ,'"+objZafParSis.getNombreComputadoraConDirIP()+"', 'I' ) ; ";

        strSql+=" UPDATE tbm_cabtipdoc SET ne_ultdoc="+txtNumDoc.getText()+" WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
        " AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+txtCodTipDoc.getText();
       
        stmLoc.executeUpdate(strSql);

      stmLoc.close();
      stmLoc=null;
    blnRes=true;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


private boolean _getObtenerTodFacVen(java.sql.Connection conn){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc;
 String strSql="";
 
 // variable creada para el nuevo servicio uno 
 ZafGenGuiRem objZafGenGuiRem=new ZafGenGuiRem();
 /*Permite inicializar el vector donde se encuentran las facturas totalmente saldadas*/
 vecOD.clear();
 /*Permite inicializar el vector donde se encuentran las facturas totalmente saldadas*/
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();
        stmLoc01=conn.createStatement();

     for(int i=0; i<tblDat.getRowCount(); i++){
      if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){
        if( !((tblDat.getValueAt(i, INT_TBL_DATFAC)==null?"":(tblDat.getValueAt(i, INT_TBL_DATFAC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_DATFAC).toString())).equals("")) ){
             if(!strSql.equals("")) strSql += " UNION ALL ";
             strSql += " select coemp, coloc, cotipdoc, codoc from ( "+tblDat.getValueAt(i, INT_TBL_DATFAC).toString()+" ) as x ";
        }      
     }}

    if(!strSql.equals("")){

      strSql = " select coemp, coloc, cotipdoc, codoc from ( "+strSql+" " +
      "   ) as x group by coemp, coloc, cotipdoc, codoc  ";
      //System.out.println("--> "+strSql );
      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){

          if(_getVerificaPagTotFac(conn, rstLoc.getString("coemp"), rstLoc.getString("coloc"), rstLoc.getString("cotipdoc"), rstLoc.getString("codoc")  )){


               //strSqlInsDet.append(
               strSql =  " UPDATE tbm_cabguirem SET " +
               "  st_aut='A' "+
               " ,tx_obsAut=' AUTOMATICO '  "+
               " ,fe_aut="+objZafParSis.getFuncionFechaHoraBaseDatos()+" "+
               " ,tx_comAut='"+objZafParSis.getNombreComputadoraConDirIP()+"' "+
               " ,co_usrAut="+objZafParSis.getCodigoUsuario()+" "+
               " FROM  ( " +
               "  select co_emp, co_loc, co_tipdoc, co_doc from tbm_detguirem where " +
               "  co_emprel="+rstLoc.getString("coemp")+" and co_locrel="+rstLoc.getString("coloc")+"  and  co_tipdocrel="+rstLoc.getString("cotipdoc")+" and co_docrel="+rstLoc.getString("codoc")+" " +
               "  group by co_emp, co_loc, co_tipdoc, co_doc " +
               " ) AS x " +
               " WHERE x.co_emp= tbm_cabguirem.co_emp and x.co_loc=tbm_cabguirem.co_loc and x.co_tipdoc=tbm_cabguirem.co_tipdoc " +
               " and x.co_doc=tbm_cabguirem.co_doc  and  tbm_cabguirem.ne_numdoc=0 and tbm_cabguirem.st_aut='P' ;  ";  // );
               stmLoc01.executeUpdate(strSql);


               INT_ENV_REC_IMP_GUIA=1;
               
               vecOD.add(rstLoc.getString("coemp")+"-"+rstLoc.getString("coloc")+"-"+rstLoc.getString("cotipdoc")+"-"+rstLoc.getString("codoc"));
                
               /*
               int numdoc=0;
               ZafImp objCreOrd=new ZafImp();
               objCreOrd.setEmp(Integer.parseInt(rstLoc.getString("coemp")));
               objCreOrd.setLoc(Integer.parseInt(rstLoc.getString("coloc")));
               objCreOrd.setTipdoc(Integer.parseInt(rstLoc.getString("cotipdoc")));
               objCreOrd.setCoDoc(Integer.parseInt(rstLoc.getString("codoc")));
               ZafMetImp objImpOd=new ZafMetImp(objCreOrd);
               numdoc=objImpOd.traerNumDocFacElec(conn);
               objCreOrd.setNumdoc(numdoc);
               boolean r=objImpOd.verificarOrd(conn);
               if (r==false) {
                  objImpOd.impresionNormal(conn);
               }
               
               objZafGenGuiRem.generarODxTraRecChq(conn, Integer.parseInt(rstLoc.getString("coemp")), Integer.parseInt(rstLoc.getString("coloc")), Integer.parseInt(rstLoc.getString("cotipdoc")), Integer.parseInt(rstLoc.getString("codoc")));
               
               objZafGenGuiRem.generarODxPreComVenRecChq(conn, Integer.parseInt(rstLoc.getString("coemp")), Integer.parseInt(rstLoc.getString("coloc")), Integer.parseInt(rstLoc.getString("cotipdoc")), Integer.parseInt(rstLoc.getString("codoc")));
               
               objZafGenGuiRem.generarProTermL(conn, Integer.parseInt(rstLoc.getString("coemp")), Integer.parseInt(rstLoc.getString("coloc")), Integer.parseInt(rstLoc.getString("cotipdoc")), Integer.parseInt(rstLoc.getString("codoc")),numdoc);
               */
               
          }    


      }
      rstLoc.close();
      rstLoc=null;
    }
      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;
    blnRes=true;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


private boolean _getVerificaPagTotFac(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc){
 boolean blnRes=false;
 java.sql.Statement stm;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
  if (conn!=null){
        stm=conn.createStatement();

         strSql="select *, (pagfac+monchq) as dif from (  " +
         " select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc,  sum(a1.mo_pag+a1.nd_Abo) as pagfac , sum(a1.nd_monchq) as monchq  " +
         " from tbm_pagmovinv as a1 " +
         " left join tbr_detrecdocpagmovinv as a2 " +
         " on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg  )  " +
         " where a1.co_emp="+strCodEmp+" and a1.co_loc="+strCodLoc+" and a1.co_tipdoc="+strCodTipDoc+" and a1.co_doc="+strCodDoc+"  and  a1.nd_porret=0  and a1.st_reg in ('A','C')  " +
          /* AGREGADO POR COMPENSACION SOLIDARIA */                  
          " and (CASE WHEN a1.tx_tipreg IS NOT NULL THEN a1.tx_tipReg <> 'S' ELSE TRUE END) "+
          /* AGREGADO POR COMPENSACION SOLIDARIA */                                    
         " group by a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc  ) as x  ";
         //System.out.println("--->  "+strSql);
         rstLoc=stm.executeQuery(strSql);
         if(rstLoc.next()){
            if( rstLoc.getDouble("monchq") > 0 ){
              if( rstLoc.getDouble("dif") >= 0 ){
                if( _getVerificaFecChqFac(conn, rstLoc.getString("co_emp"), rstLoc.getString("co_loc"), rstLoc.getString("co_tipdoc"), rstLoc.getString("co_doc") ) )
                 blnRes=true;
              }else{
                  if( rstLoc.getDouble("dif") >= -0.01 ){
                    if( _getVerificaFecChqFac(conn, rstLoc.getString("co_emp"), rstLoc.getString("co_loc"), rstLoc.getString("co_tipdoc"), rstLoc.getString("co_doc") ) )
                      blnRes=true;
                  }
              }
            }
         }
         rstLoc.close();
         rstLoc=null;

        if(!blnRes){

          strSql="select *, (pagfac+abofac) as dif from (  " +
          " select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc,  sum(a1.mo_pag) as pagfac , sum(a1.nd_abo) as abofac  " +
          " from tbm_pagmovinv as a1 " +
          " where a1.co_emp="+strCodEmp+" and a1.co_loc="+strCodLoc+" and a1.co_tipdoc="+strCodTipDoc+" and a1.co_doc="+strCodDoc+"  and  a1.nd_porret=0  and a1.st_reg in ('A','C')  " +
          /* AGREGADO POR COMPENSACION SOLIDARIA */                  
          " and (CASE WHEN tx_tipreg IS NOT NULL THEN tx_tipReg <> 'S' ELSE TRUE END) "+
          /* AGREGADO POR COMPENSACION SOLIDARIA */                                    
          " group by a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc ) as x   ";
          //System.out.println("--->  "+strSql);
          rstLoc=stm.executeQuery(strSql);
          if(rstLoc.next()){
            if( rstLoc.getDouble("abofac") > 0 ){
              if( rstLoc.getDouble("dif") >= -0.01 ){
                  blnRes=true;
              }
            }
          }
          rstLoc.close();
          rstLoc=null;

        }


   stm.close();
   stm=null;

}}catch (java.sql.SQLException e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
  catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}


private boolean _getVerificaFecChqFac(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc){
 boolean blnRes=false;
 java.sql.Statement stm;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
  if (conn!=null){
       stm=conn.createStatement();


       strSql="select * " +
       " , case when fe_venchq <= fe_ven then 'S' else  case when  fe_venchq  <=  current_date  then 'S'  else  'N' end end as FecCont " +
       " , case when fe_venchq <= fecvenchq then 'S' else 'N' end as FecnoCont " +
       " from ( "+
       "select ne_diacre, fe_ven, (fe_ven+1) as fecvenchq , fe_venchq  from tbm_pagmovinv " +
       " where co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" and co_doc="+strCodDoc+" and nd_porret=0 and st_reg in ('A','C') " +
       /* AGREGADO POR COMPENSACION SOLIDARIA */
       " and (CASE WHEN tx_tipreg IS NOT NULL THEN tx_tipReg <> 'S' ELSE TRUE END) "+                      
        /* AGREGADO POR COMPENSACION SOLIDARIA */               
       " ) as x  ";
       //System.out.println("-> "+strSql );
       rstLoc=stm.executeQuery(strSql);
       while(rstLoc.next()){

           if(rstLoc.getInt("ne_diacre")==0){
                if(rstLoc.getString("FecCont").equals("S")){
                    blnRes=true;
                }else{
                    blnRes=false;
                    break;
                }
           }else{
               if(rstLoc.getString("FecnoCont").equals("S")){
                    blnRes=true;
                }else{
                    blnRes=false;
                    break;
                }
           }

       } 
       rstLoc.close();
       rstLoc=null;
       stm.close();
       stm=null;

}}catch (java.sql.SQLException e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
  catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}



private boolean insertarDetRecChq(java.sql.Connection conn, int intCodDoc){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 String strFecDoc="";
 String strEstasidocrel="N";
 int intNumReg=0;
 double dblValChq=0;
 double dblValFac=0;
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";

     for(int i=0; i<tblDat.getRowCount(); i++){
      if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){

          strEstasidocrel="N";
          
          dblValChq=objUti.redondear( (tblDat.getValueAt(i, INT_TBL_VALCHQ)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALCHQ).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALCHQ).toString()) ), 2 );
          dblValFac=objUti.redondear( (tblDat.getValueAt(i, INT_TBL_VAPLFAC)==null?"0":(tblDat.getValueAt(i, INT_TBL_VAPLFAC).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VAPLFAC).toString()) ),2 );
          if(dblValChq==dblValFac)
              strEstasidocrel="S";
          
         intNumReg++;
         strSql="INSERT INTO tbm_detrecdoc(co_emp, co_loc, co_tipdoc, co_doc,  co_reg,  co_cli, co_banchq,  tx_numctachq, tx_numchq, fe_recchq, " +
         " fe_venchq,  nd_monchq, tx_obs1, nd_valapl, st_asidocrel,  st_regrep , st_reg  ) " +
         " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" " +
         " ,"+intNumReg+", "+tblDat.getValueAt(i, INT_TBL_CODCLI).toString()+", "+tblDat.getValueAt(i, INT_TBL_CODBAN).toString()+"  " +
         " ,'"+(tblDat.getValueAt(i, INT_TBL_NUMCTA)==null?"":tblDat.getValueAt(i, INT_TBL_NUMCTA).toString())+"', '"+tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString()+"' "+
         ", '"+strFecDoc+"', '"+objUti.formatearFecha(tblDat.getValueAt(i,INT_TBL_FECVEN).toString(),"dd/MM/yyyy","yyyy/MM/dd")+"' " +
         ", "+dblValChq+", '"+(tblDat.getValueAt(i, INT_TBL_OBSERV)==null?"":tblDat.getValueAt(i, INT_TBL_OBSERV).toString())+"' " +
         ", 0  "+
         " , '"+strEstasidocrel+"' ,'I', 'A' )  ;  ";


        if( !((tblDat.getValueAt(i, INT_TBL_DATFAC)==null?"":(tblDat.getValueAt(i, INT_TBL_DATFAC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_DATFAC).toString())).equals("")) ){

              strSql +="INSERT INTO tbr_detrecdocpagmovinv( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel," +
              " co_tipdocrel, co_docrel, co_regrel, st_regrep) " +
              " select "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" ,"+intNumReg+",  coemp, coloc, cotipdoc, codoc, coreg, 'I' from( " +
              tblDat.getValueAt(i, INT_TBL_DATFAC).toString()+" ) as x  ; ";
    
              strSql +=" UPDATE tbm_pagmovinv SET " +
              " st_entsop='S', st_pos='S', co_banchq=x.cobanchq, tx_numctachq=x.txnumctachq, tx_numchq=x.txnumchq, fe_recchq=x.ferecchq, fe_venchq=x.fevenchq, nd_monchq=x.valapl" +
              " FROM ( " +
              "  select *, "+tblDat.getValueAt(i, INT_TBL_CODBAN).toString()+" as cobanchq " +
              " ,"+(tblDat.getValueAt(i, INT_TBL_VALCHQ)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALCHQ).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALCHQ).toString()))+" as monchq "+
              ", text('"+(tblDat.getValueAt(i, INT_TBL_NUMCTA)==null?"":tblDat.getValueAt(i, INT_TBL_NUMCTA).toString())+"') as  txnumctachq " +
              ", text('"+tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString()+"') as txnumchq " +
              ", date('"+strFecDoc+"') as ferecchq, date('"+objUti.formatearFecha(tblDat.getValueAt(i,INT_TBL_FECVEN).toString(),"dd/MM/yyyy","yyyy/MM/dd")+"') as fevenchq from ( " +
              " "+tblDat.getValueAt(i, INT_TBL_DATFAC).toString()+" "+
              " ) as x " +
              " ) as x WHERE tbm_pagmovinv.co_emp=x.coemp AND tbm_pagmovinv.co_loc=x.coloc AND tbm_pagmovinv.co_tipdoc=x.cotipdoc " +
              "  AND tbm_pagmovinv.co_doc=x.codoc AND tbm_pagmovinv.co_reg=x.coreg ";
  
        }
       //System.out.println("---> "+strSql);
         stmLoc.executeUpdate(strSql);

    }}


      stmLoc.close();
      stmLoc=null;
    blnRes=true;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


private boolean insertarDetRecRet(java.sql.Connection conn, int intCodDoc){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 String strFecDoc="";
 String strEstasidocrel="N";
 int intNumReg=0;
 double dblValChq=0;
 double dblValFac=0;
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";

     for(int i=0; i<tblDat.getRowCount(); i++){
      if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){

          strEstasidocrel="N";

          dblValChq=objUti.redondear( (tblDat.getValueAt(i, INT_TBL_VALCHQ)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALCHQ).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALCHQ).toString()) ), 2 );
          dblValFac=objUti.redondear( (tblDat.getValueAt(i, INT_TBL_VAPLFAC)==null?"0":(tblDat.getValueAt(i, INT_TBL_VAPLFAC).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VAPLFAC).toString()) ),2 );
          if(dblValChq==dblValFac)
              strEstasidocrel="S";

         intNumReg++;
         strSql="INSERT INTO tbm_detrecdoc(co_emp, co_loc, co_tipdoc, co_doc,  co_reg,  co_cli, co_banchq,  tx_numctachq, tx_numchq, fe_recchq, " +
         " fe_venchq,  nd_monchq, tx_obs1, nd_valapl, st_asidocrel,  st_regrep , st_reg  ) " +
         " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" " +
         " ,"+intNumReg+", "+tblDat.getValueAt(i, INT_TBL_CODCLI).toString()+", null  " +
         " ,null, '"+tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString()+"' "+
         ", '"+strFecDoc+"', null " +
         ", "+dblValChq+", '"+(tblDat.getValueAt(i, INT_TBL_OBSERV)==null?"":tblDat.getValueAt(i, INT_TBL_OBSERV).toString())+"' " +
         ", 0  "+
         " , '"+strEstasidocrel+"' ,'I', 'A' )  ;  ";


        if( !((tblDat.getValueAt(i, INT_TBL_DATFAC)==null?"":(tblDat.getValueAt(i, INT_TBL_DATFAC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_DATFAC).toString())).equals("")) ){

              strSql +="INSERT INTO tbr_detrecdocpagmovinv( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel," +
              " co_tipdocrel, co_docrel, co_regrel, st_regrep) " +
              " select "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" ,"+intNumReg+",  coemp, coloc, cotipdoc, codoc, coreg, 'I' from( " +
              tblDat.getValueAt(i, INT_TBL_DATFAC).toString()+" ) as x  ; ";

              strSql +=" UPDATE tbm_pagmovinv SET " +
              " st_entsop='S', st_pos='S', tx_numchq=x.txnumchq, fe_recchq=x.ferecchq, fe_venchq=x.ferecchq, nd_monchq=x.valapl" +
              " FROM ( " +
              "  select * " +
              ", text('"+tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString()+"') as txnumchq " +
              ", date('"+strFecDoc+"') as ferecchq from ( " +
              " "+tblDat.getValueAt(i, INT_TBL_DATFAC).toString()+" "+
              " ) as x " +
              " ) as x WHERE tbm_pagmovinv.co_emp=x.coemp AND tbm_pagmovinv.co_loc=x.coloc AND tbm_pagmovinv.co_tipdoc=x.cotipdoc " +
              "  AND tbm_pagmovinv.co_doc=x.codoc AND tbm_pagmovinv.co_reg=x.coreg ";

        }
       //System.out.println("---> "+strSql);
         stmLoc.executeUpdate(strSql);

    }}


      stmLoc.close();
      stmLoc=null;
    blnRes=true;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}









       private boolean validarDat(){
          boolean blnRes=true;
//          if(txtCodTipDoc.getText().trim().equals("")) { MensajeInf("Ingrese tipo el documento");  return false; }


          return blnRes;
       }



/**
 * Permite saber si el documento esta anulado
 * @param conn  coneccion de la base
 * @return  true no esta anulado   false esta anulado el registro 
 */
private boolean obtenerEstAnu(java.sql.Connection conn){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="", strEst="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        strSql="SELECT st_reg FROM tbm_cabrecdoc " +
        " where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" " +
        " and co_tipdoc="+txtCodTipDoc.getText()+" and co_doc="+txtCodDoc.getText()+" and st_reg='I' ";

        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()){
           if(rstLoc.getString("st_reg").equals("I"))strEst="ANULADO";
           MensajeInf("El documento ya está "+strEst+".\nNo es posible modifcar un documento "+strEst+".");
           blnRes=false;
        }
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}



public boolean modificar(){
  boolean blnRes=false;
  java.sql.Connection conn;
  int intCodDoc=0;
  int intCodTipDoc=0;
  String strAux="";
  
  ZafGenOdPryTra objGenOD=new ZafGenOdPryTra();
  ZafCnfDoc objValCnf=new ZafCnfDoc(objZafParSis,this);
  
          
  try{

      strAux=objTooBar.getEstadoRegistro();
      if(strAux.equals("Eliminado")) {
          MensajeInf("El documento está ELIMINADO.\nNo es posible modifcar un documento eliminado.");
          return false;
      }
      if(strAux.equals("Anulado")) {
          MensajeInf("El documento ya está ANULADO.\nNo es posible modifcar un documento anulado.");
          return false;
      }

    if(validaCampos()){

     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);

       INT_ENV_REC_IMP_GUIA = 0;



      if(obtenerEstAnu(conn)){

        intCodDoc=Integer.parseInt(txtCodDoc.getText());
        intCodTipDoc=Integer.parseInt(txtCodTipDoc.getText());

        if( _getEstImp( conn, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(),  intCodTipDoc,  intCodDoc ) ){


            if(objZafParSis.getCodigoMenu()==1749){
                if(modificarCab(conn, intCodTipDoc, intCodDoc)){
                  if(EliminadosRegRec(conn, intCodTipDoc, intCodDoc )){
                   if(reestructurarFacturas(conn)){
                    if(insertarDetNuevosRecRet(conn, intCodTipDoc, intCodDoc )){
                    if(modificarDetRecRet(conn, intCodTipDoc, intCodDoc )){
                      conn.commit();
                      blnRes=true;
                      cargarDetIns(conn, intCodDoc);
                   }else conn.rollback();
                  }else conn.rollback();
                 }else conn.rollback();
                }else conn.rollback();
               }else conn.rollback();
            }else{
                  if(modificarCab(conn, intCodTipDoc, intCodDoc)){
                  if(EliminadosRegRec(conn, intCodTipDoc, intCodDoc )){
                   if(reestructurarFacturas(conn)){
                    if(insertarDetNuevosRecChq(conn, intCodTipDoc, intCodDoc )){
                    if(modificarDetRecChq(conn, intCodTipDoc, intCodDoc )){
                     if(_getObtenerTodFacVen(conn)){
                       conn.commit();
                       blnRes=true;
                       cargarDetIns(conn, intCodDoc);
                        if(INT_ENV_REC_IMP_GUIA==1) {

                            Iterator itVecOD=vecOD.iterator();
                            while(itVecOD.hasNext()){
                               String strDat=(String)itVecOD.next();
                               String[] strArrDat=strDat.split("-");

                               int intCodEmp=Integer.parseInt((String)strArrDat[0]);
                               int intCodLoc=Integer.parseInt((String)strArrDat[1]);
                               int intCodTipDoc2=Integer.parseInt((String)strArrDat[2]);
                               int intCodDocu=Integer.parseInt((String)strArrDat[3]);

                               
                                /*FORMA DE GENERAR EL TERMINAL L PROYECTO DE TRANSFERENCIA*/
                               if(!objValCnf.isDocIngPenCnfxFac(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, "I")){
                                    if(!(objGenOD.validarODExs(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu))){
                                         if(objGenOD.generarNumOD(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, false)){
                                             conn.commit();
                                             String strIp=objGenOD.obtenerIpSerImp(conn);
                                             objGenOD.imprimirOdLocal(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, strIp);
                                             objGenOD.generarTermL(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu);
                                         }else{
                                             conn.rollback();
                                         }
                                    }
                               }
                                /*FORMA DE GENERAR EL TERMINAL L PROYECTO DE TRANSFERENCIA*/
                               
                               
                               
                            }
                            vecOD.clear();                                
                            
                            
                            
                        }
                        //enviarRequisitoImp(strIpImpGuia, intPuertoImpGuia);

                     }else conn.rollback();
                    }else conn.rollback();
                   }else conn.rollback();
                  }else conn.rollback();
                 }else conn.rollback();
                }else conn.rollback();
           }
        
        

         

       }
     }

       conn.close();
       conn=null;
   }

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


/**
 * Se encarga de hacer la reestructuracion de un pago de la factura
 * @param conn  coneccion de la base
 * @return  true si se reestructuro bien  false si no puedo realizar dicho proceso
 */
private boolean reestructurarFacturas(java.sql.Connection conn ){
 boolean blnRes=true;
 java.sql.Statement stmLoc, stmLoc01, stmLoc02;
 java.sql.ResultSet rstLoc, rstLoc01;
 String strSql="";
 int intCodReg=0;
 int intEst=0;
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();
        stmLoc01=conn.createStatement();
        stmLoc02=conn.createStatement();


     stbFacSel=new StringBuffer();
     for(int i=0; i<tblDat.getRowCount(); i++){
          String strDatFac = (tblDat.getValueAt(i, INT_TBL_DATFAC)==null?"":(tblDat.getValueAt(i, INT_TBL_DATFAC).equals("")?"":tblDat.getValueAt(i, INT_TBL_DATFAC).toString()));
          String strEstFac = (tblDat.getValueAt(i, INT_TBL_ESTCAMFAC)==null?"N":(tblDat.getValueAt(i, INT_TBL_ESTCAMFAC).equals("")?"N":tblDat.getValueAt(i, INT_TBL_ESTCAMFAC).toString()));

         if(strEstFac.equals("S")){
          if(!strDatFac.equals("")){
              if(intEst==1) stbFacSel.append(" UNION ALL ");
              stbFacSel.append( strDatFac );
              intEst=1;
         }}
      }

     if(intEst==1){

         strSql="SELECT x.* FROM ( " +
         " SELECT *, ( valpen - valapl ) as val2 FROM ( "+stbFacSel.toString()+" ) AS x WHERE   ( valpen - valapl ) > 0 " +
         " ) AS x  INNER JOIN tbm_emp AS a1 ON ( a1.co_emp = x.coemp )   " +
         " WHERE ( abs(val2)  between  a1.nd_valminajucenaut and  a1.nd_valmaxajucenaut ) = false ";
         //System.out.println("--> "+strSql );
         rstLoc=stmLoc.executeQuery(strSql);
         while(rstLoc.next()){

           intCodReg= _getObtenerMaxCodRegPag(conn, rstLoc.getInt("coemp"), rstLoc.getInt("coloc"), rstLoc.getInt("cotipdoc"), rstLoc.getInt("codoc") );

           strSql="INSERT INTO tbm_pagmovinv( co_emp, co_loc, co_tipdoc, co_doc, co_reg , ne_diacre, fe_ven, "+
           " co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, " +
           " tx_numchq, fe_recchq, fe_venchq, nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree,  "+
           " tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp ) " +
           " SELECT co_emp, co_loc, co_tipdoc, co_doc, "+intCodReg+" , ne_diacre, fe_ven, "+
           " co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop,  "+
           " 'N', 'N', null, null, null, null,  "+
           " null, 0, co_prochq, CASE WHEN st_reg IN ('A') THEN 'F' ELSE CASE WHEN st_reg IN ('C') THEN 'I'  END END AS estreg , " +
           " 'I', fe_ree, co_usrree,  "+
           " tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp "+
           " FROM tbm_pagmovinv where co_emp="+rstLoc.getInt("coemp")+" and co_loc="+rstLoc.getInt("coloc")+" and " +
           " co_tipdoc="+rstLoc.getInt("cotipdoc")+"  and co_doc="+rstLoc.getInt("codoc")+"  and co_reg="+rstLoc.getInt("coreg")+"   ";
           //System.out.println("1 --> "+strSql );
           stmLoc01.executeUpdate(strSql);

           intCodReg= _getObtenerMaxCodRegPag(conn, rstLoc.getInt("coemp"), rstLoc.getInt("coloc"), rstLoc.getInt("cotipdoc"), rstLoc.getInt("codoc") );

           strSql="INSERT INTO tbm_pagmovinv( co_emp, co_loc, co_tipdoc, co_doc, co_reg , ne_diacre, fe_ven, "+
           " co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, " +
           " tx_numchq, fe_recchq, fe_venchq, nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree,  "+
           " tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp ) " +
           " SELECT co_emp, co_loc, co_tipdoc, co_doc, "+intCodReg+" , ne_diacre, fe_ven, "+
           " 0, 0, tx_aplret, abs("+rstLoc.getDouble("val2")+")*-1, ne_diagra, 0 as nd_abo, st_sop,  "+
           " 'N', 'N', null, null, null, null,  "+
           " null, 0, co_prochq, 'C', 'M', fe_ree, co_usrree,  "+
           " tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp "+
           " FROM tbm_pagmovinv where co_emp="+rstLoc.getInt("coemp")+" and co_loc="+rstLoc.getInt("coloc")+" and " +
           " co_tipdoc="+rstLoc.getInt("cotipdoc")+"  and co_doc="+rstLoc.getInt("codoc")+"  and co_reg="+rstLoc.getInt("coreg")+"   ";
           //System.out.println("2 --> "+strSql );
           stmLoc01.executeUpdate(strSql);


           /*************** SI HAY PAGO REALIZADO GENERE REGISTRO DE ESE PAGO Y CAMBIA DETPAG AL REGISTRO NUEVO **********************/

           strSql="SELECT nd_abo FROM tbm_pagmovinv WHERE co_emp="+rstLoc.getInt("coemp")+" and co_loc="+rstLoc.getInt("coloc")+" and " +
           " co_tipdoc="+rstLoc.getInt("cotipdoc")+"  and co_doc="+rstLoc.getInt("codoc")+"  and co_reg="+rstLoc.getInt("coreg")+"  " +
           " AND nd_abo > 0 ";
           rstLoc01=stmLoc02.executeQuery(strSql);
           if(rstLoc01.next()){

               intCodReg= _getObtenerMaxCodRegPag(conn, rstLoc.getInt("coemp"), rstLoc.getInt("coloc"), rstLoc.getInt("cotipdoc"), rstLoc.getInt("codoc") );

               strSql="INSERT INTO tbm_pagmovinv( co_emp, co_loc, co_tipdoc, co_doc, co_reg , ne_diacre, fe_ven, "+
               " co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, " +
               " tx_numchq, fe_recchq, fe_venchq, nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree,  "+
               " tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp ) " +
               " SELECT co_emp, co_loc, co_tipdoc, co_doc, "+intCodReg+" , ne_diacre, fe_ven, "+
               " co_tipret, nd_porret, tx_aplret, abs(nd_abo)*-1, ne_diagra, nd_abo, st_sop,  "+
               " st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq,  "+
               " fe_venchq, nd_monchq, co_prochq, 'C', 'M', fe_ree, co_usrree,  "+
               " tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp "+
               " FROM tbm_pagmovinv where co_emp="+rstLoc.getInt("coemp")+" and co_loc="+rstLoc.getInt("coloc")+" and " +
               " co_tipdoc="+rstLoc.getInt("cotipdoc")+"  and co_doc="+rstLoc.getInt("codoc")+"  and co_reg="+rstLoc.getInt("coreg")+"   ";
               //System.out.println("2 --> "+strSql );
               stmLoc01.executeUpdate(strSql);

               strSql="UPDATE tbm_detpag SET co_regpag="+intCodReg+", st_regrep='M' WHERE co_emp="+rstLoc.getInt("coemp")+" and co_locpag="+rstLoc.getInt("coloc")+" and " +
               " co_tipdocpag="+rstLoc.getInt("cotipdoc")+"  and co_docpag="+rstLoc.getInt("codoc")+"  and co_regpag="+rstLoc.getInt("coreg"); 
               stmLoc01.executeUpdate(strSql);

           }
           rstLoc01.close();
           rstLoc01=null;

           /***************************************************************************************************************************/

           strSql="UPDATE tbm_pagmovinv SET nd_abo=0,  mo_pag=abs("+rstLoc.getDouble("valapl")+")*-1, st_reg='C', st_regrep='M' WHERE " +
           " co_emp="+rstLoc.getInt("coemp")+" and co_loc="+rstLoc.getInt("coloc")+" and " +
           " co_tipdoc="+rstLoc.getInt("cotipdoc")+"  and co_doc="+rstLoc.getInt("codoc")+"  and co_reg="+rstLoc.getInt("coreg")+"   ";
           //System.out.println("3 --> "+strSql );
           stmLoc01.executeUpdate(strSql);
           
         }
         rstLoc.close();
         rstLoc=null;

     }
     stmLoc.close();
     stmLoc=null;
     stmLoc01.close();
     stmLoc01=null;
     stmLoc02.close();
     stmLoc02=null;
     stbFacSel=null;

}}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

/**
 * Obtiene el maximo registro de la tabla tbm_pagmovinv  + 1
 * @param conn    coneccion de la base
 * @param intCodEmp   codigo de la empresa
 * @param intCodLoc   codigo del local
 * @param intCodTipDoc codigo del tipo documento
 * @param intCodDoc    codigo del documento
 * @return  -1  si no se hay algun error   caso contrario retorna el valor correcto
 */
public int _getObtenerMaxCodRegPag(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
  int intCodReg=-1;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     if(conn!=null){
       stmLoc=conn.createStatement();

       strSql="SELECT CASE WHEN max(co_reg) IS NULL THEN 1 ELSE max(co_reg)+1 END AS coreg FROM tbm_pagmovinv " +
       " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+" AND co_doc= "+intCodDoc+" ";
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next()){
             intCodReg=rstLoc.getInt("coreg");
       }
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;
  }}catch(java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }
    catch(Exception  Evt){ objUti.mostrarMsgErr_F1(this, Evt); }
 return intCodReg;
}


private boolean modificarDetRecChq(java.sql.Connection conn, int intCodTipDoc, int intCodDoc){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 String strSql="";
 String strFecDoc="";
 String strEstasidocrel="N";
 double dblValChq=0;
 double dblValFac=0;
 String strCodReg="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";

     for(int i=0; i<tblDat.getRowCount(); i++){

      if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){

        strCodReg=((tblDat.getValueAt(i, INT_TBL_CODREG)==null?"":(tblDat.getValueAt(i, INT_TBL_CODREG).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODREG).toString())));

        if( !(strCodReg.equals("")) ){

          strEstasidocrel="N";

          dblValChq=objUti.redondear( (tblDat.getValueAt(i, INT_TBL_VALCHQ)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALCHQ).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALCHQ).toString()) ), 2 );
          dblValFac=objUti.redondear( (tblDat.getValueAt(i, INT_TBL_VAPLFAC)==null?"0":(tblDat.getValueAt(i, INT_TBL_VAPLFAC).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VAPLFAC).toString()) ),2 );
          if(dblValChq==dblValFac)
              strEstasidocrel="S";

       
         strSql="UPDATE tbm_detrecdoc SET   co_banchq="+tblDat.getValueAt(i, INT_TBL_CODBAN).toString()+", " +
         " tx_numctachq='"+(tblDat.getValueAt(i, INT_TBL_NUMCTA)==null?"":tblDat.getValueAt(i, INT_TBL_NUMCTA).toString())+"', " +
         " tx_numchq='"+tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString()+"', fe_recchq='"+strFecDoc+"', " +
         " fe_venchq='"+objUti.formatearFecha(tblDat.getValueAt(i,INT_TBL_FECVEN).toString(),"dd/MM/yyyy","yyyy/MM/dd")+"', " +
         " nd_monchq="+dblValChq+", tx_obs1='"+(tblDat.getValueAt(i, INT_TBL_OBSERV)==null?"":tblDat.getValueAt(i, INT_TBL_OBSERV).toString())+"', " +
         " st_asidocrel='"+strEstasidocrel+"', st_regrep='M' WHERE " +
         " co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND " +
         " co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" AND  co_reg="+strCodReg;

         strSql+=" ; UPDATE tbr_detrecdocpagmovinv SET st_reg='E', st_regrep='M' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND" +
         " co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" and co_reg="+strCodReg;

         strSql+=" ; UPDATE tbm_pagmovinv SET nd_monchq=0, fe_venchq=null, fe_recchq=null, tx_numchq=null, co_banchq=null, " +
         " tx_numctachq=null, st_pos='N', st_entsop='N', st_regrep='M' FROM ( "+
         "  SELECT  co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel FROM tbr_detrecdocpagmovinv  WHERE " +
         "  co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
         "  AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" and co_reg="+strCodReg+" "+
         " ) AS x WHERE tbm_pagmovinv.co_emp=x.co_emprel AND tbm_pagmovinv.co_loc=x.co_locrel AND tbm_pagmovinv.co_tipdoc=x.co_tipdocrel " +
         " AND tbm_pagmovinv.co_doc=x.co_docrel AND tbm_pagmovinv.co_reg=x.co_regrel ; ";


        stmLoc.executeUpdate(strSql);

        if( !((tblDat.getValueAt(i, INT_TBL_DATFAC)==null?"":(tblDat.getValueAt(i, INT_TBL_DATFAC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_DATFAC).toString())).equals("")) ){


              if(!actInsRetRelPag( conn, intCodTipDoc, intCodDoc, strCodReg, tblDat.getValueAt(i, INT_TBL_DATFAC).toString() )){
                  blnRes=false;
                  break;
              }

              strSql =" UPDATE tbm_pagmovinv SET  st_regrep='M', " +
              " st_entsop='S', st_pos='S', co_banchq=x.cobanchq, tx_numctachq=x.txnumctachq, tx_numchq=x.txnumchq, fe_recchq=x.ferecchq, fe_venchq=x.fevenchq, nd_monchq=x.valapl" +
              " FROM ( " +
              "  select *, "+tblDat.getValueAt(i, INT_TBL_CODBAN).toString()+" as cobanchq " +
              " ,"+(tblDat.getValueAt(i, INT_TBL_VALCHQ)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALCHQ).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALCHQ).toString()))+" as monchq "+
              ", text('"+(tblDat.getValueAt(i, INT_TBL_NUMCTA)==null?"":tblDat.getValueAt(i, INT_TBL_NUMCTA).toString())+"') as  txnumctachq " +
              ", text('"+tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString()+"') as txnumchq " +
              ", date('"+strFecDoc+"') as ferecchq, date('"+objUti.formatearFecha(tblDat.getValueAt(i,INT_TBL_FECVEN).toString(),"dd/MM/yyyy","yyyy/MM/dd")+"') as fevenchq from ( " +
              " "+tblDat.getValueAt(i, INT_TBL_DATFAC).toString()+" "+
              " ) as x " +
              " ) as x WHERE tbm_pagmovinv.co_emp=x.coemp AND tbm_pagmovinv.co_loc=x.coloc AND tbm_pagmovinv.co_tipdoc=x.cotipdoc " +
              "  AND tbm_pagmovinv.co_doc=x.codoc AND tbm_pagmovinv.co_reg=x.coreg ";

             stmLoc.executeUpdate(strSql);
        }
        
    }}}


      stmLoc.close();
      stmLoc=null;
   
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


private boolean modificarDetRecRet(java.sql.Connection conn, int intCodTipDoc, int intCodDoc){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 String strSql="";
 String strFecDoc="";
 String strEstasidocrel="N";
 double dblValChq=0;
 double dblValFac=0;
 String strCodReg="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";

     for(int i=0; i<tblDat.getRowCount(); i++){

      if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){

        strCodReg=((tblDat.getValueAt(i, INT_TBL_CODREG)==null?"":(tblDat.getValueAt(i, INT_TBL_CODREG).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODREG).toString())));

        if( !(strCodReg.equals("")) ){

          strEstasidocrel="N";

          dblValChq=objUti.redondear( (tblDat.getValueAt(i, INT_TBL_VALCHQ)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALCHQ).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALCHQ).toString()) ), 2 );
          dblValFac=objUti.redondear( (tblDat.getValueAt(i, INT_TBL_VAPLFAC)==null?"0":(tblDat.getValueAt(i, INT_TBL_VAPLFAC).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VAPLFAC).toString()) ),2 );
          if(dblValChq==dblValFac)
              strEstasidocrel="S";


         strSql="UPDATE tbm_detrecdoc SET  " +
         " tx_numchq='"+tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString()+"', fe_recchq='"+strFecDoc+"', " +
         " nd_monchq="+dblValChq+", tx_obs1='"+(tblDat.getValueAt(i, INT_TBL_OBSERV)==null?"":tblDat.getValueAt(i, INT_TBL_OBSERV).toString())+"', " +
         " st_asidocrel='"+strEstasidocrel+"', st_regrep='M' WHERE " +
         " co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND " +
         " co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" AND  co_reg="+strCodReg;

         strSql+=" ; UPDATE tbr_detrecdocpagmovinv SET st_reg='E', st_regrep='M' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND" +
         " co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" and co_reg="+strCodReg;

         strSql+=" ; UPDATE tbm_pagmovinv SET nd_monchq=0, fe_venchq=null, fe_recchq=null, tx_numchq=null,  " +
         "  st_pos='N', st_entsop='N', st_regrep='M' FROM ( "+
         "  SELECT  co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel FROM tbr_detrecdocpagmovinv  WHERE " +
         "  co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
         "  AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" and co_reg="+strCodReg+" "+
         " ) AS x WHERE tbm_pagmovinv.co_emp=x.co_emprel AND tbm_pagmovinv.co_loc=x.co_locrel AND tbm_pagmovinv.co_tipdoc=x.co_tipdocrel " +
         " AND tbm_pagmovinv.co_doc=x.co_docrel AND tbm_pagmovinv.co_reg=x.co_regrel ; ";


        stmLoc.executeUpdate(strSql);

        if( !((tblDat.getValueAt(i, INT_TBL_DATFAC)==null?"":(tblDat.getValueAt(i, INT_TBL_DATFAC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_DATFAC).toString())).equals("")) ){


              if(!actInsRetRelPag( conn, intCodTipDoc, intCodDoc, strCodReg, tblDat.getValueAt(i, INT_TBL_DATFAC).toString() )){
                  blnRes=false;
                  break;
              }

              strSql =" UPDATE tbm_pagmovinv SET  st_regrep='M', " +
              " st_entsop='S', st_pos='S', tx_numchq=x.txnumchq, fe_recchq=x.ferecchq, fe_venchq=x.ferecchq, nd_monchq=x.valapl" +
              " FROM ( " +
              "  select * "+
              ", text('"+tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString()+"') as txnumchq " +
              ", date('"+strFecDoc+"') as ferecchq  from ( " +
              " "+tblDat.getValueAt(i, INT_TBL_DATFAC).toString()+" "+
              " ) as x " +
              " ) as x WHERE tbm_pagmovinv.co_emp=x.coemp AND tbm_pagmovinv.co_loc=x.coloc AND tbm_pagmovinv.co_tipdoc=x.cotipdoc " +
              "  AND tbm_pagmovinv.co_doc=x.codoc AND tbm_pagmovinv.co_reg=x.coreg ";

             stmLoc.executeUpdate(strSql);
        }

    }}}


      stmLoc.close();
      stmLoc=null;

 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}



/**
 * Actualiza informacion en la tabla tbr_detrecdocpagmovinv o caso comtrario inserta los nuevos registros.
 * @param conn  coneccion de la base
 * @param intCodTipDoc  codigo tipo docuemnto
 * @param intCodDoc  codigo documento
 * @param strCodReg  codigo del registro
 * @param strSQL    Query del que actualiza o inserta.. 
 * @return  true si se realizo con exito   false si hay algun error 
 */
private boolean actInsRetRelPag(java.sql.Connection conn, int intCodTipDoc, int intCodDoc, String strCodReg, String strSQL ){
 boolean blnRes=true;
 java.sql.Statement stmLoc, stmLoc01, stmLoc02;
 java.sql.ResultSet rstLoc, rstLoc01;
 String strSql="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();
        stmLoc01=conn.createStatement();
        stmLoc02=conn.createStatement();

        rstLoc=stmLoc.executeQuery(strSQL);
        while(rstLoc.next()){

          strSql="SELECT * FROM tbr_detrecdocpagmovinv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" " +
          " and co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" and co_reg="+strCodReg+" and co_emprel="+rstLoc.getInt("coemp")+" " +
          " and co_locrel="+rstLoc.getInt("coloc")+" and co_tipdocrel="+rstLoc.getInt("cotipdoc")+"  and co_docrel="+rstLoc.getInt("codoc")+" " +
          " and co_regrel="+rstLoc.getInt("coreg")+" ";
          rstLoc01=stmLoc01.executeQuery(strSql);
          if(rstLoc01.next()){
              strSql="UPDATE tbr_detrecdocpagmovinv SET st_reg='A', st_regrep='M' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" " +
              " and co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" and co_reg="+strCodReg+" and co_emprel="+rstLoc.getInt("coemp")+" " +
              " and co_locrel="+rstLoc.getInt("coloc")+" and co_tipdocrel="+rstLoc.getInt("cotipdoc")+"  and co_docrel="+rstLoc.getInt("codoc")+" " +
              " and co_regrel="+rstLoc.getInt("coreg")+" ";
              //System.out.println("--> "+strSql );
              stmLoc02.executeUpdate(strSql);
          }else{

              strSql ="INSERT INTO tbr_detrecdocpagmovinv( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel," +
              " co_tipdocrel, co_docrel, co_regrel, st_reg, st_regrep) " +
              " select "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+intCodTipDoc+", "+intCodDoc+" ,"+strCodReg+" " +
              ", "+rstLoc.getInt("coemp")+", "+rstLoc.getInt("coloc")+", "+rstLoc.getInt("cotipdoc")+", "+rstLoc.getInt("codoc")+",  " +
              " "+rstLoc.getInt("coreg")+",'A', 'I' ";
              //System.out.println("--> "+strSql );
              stmLoc02.executeUpdate(strSql);

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
      stmLoc02.close();
      stmLoc02=null;

    blnRes=true;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


private boolean insertarDetNuevosRecChq(java.sql.Connection conn, int intCodTipDoc, int intCodDoc){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 String strFecDoc="";
 String strEstasidocrel="N";
 int intNumReg=0;
 double dblValChq=0;
 double dblValFac=0;
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";

     for(int i=0; i<tblDat.getRowCount(); i++){
      if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){
       if( ((tblDat.getValueAt(i, INT_TBL_CODREG)==null?"":(tblDat.getValueAt(i, INT_TBL_CODREG).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODREG).toString())).equals("")) ){

          strEstasidocrel="N";

          dblValChq=objUti.redondear( (tblDat.getValueAt(i, INT_TBL_VALCHQ)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALCHQ).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALCHQ).toString()) ), 2 );
          dblValFac=objUti.redondear( (tblDat.getValueAt(i, INT_TBL_VAPLFAC)==null?"0":(tblDat.getValueAt(i, INT_TBL_VAPLFAC).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VAPLFAC).toString()) ),2 );
          if(dblValChq==dblValFac)
              strEstasidocrel="S";

         intNumReg = _getObtenerMaxCodRegDet(conn, intCodTipDoc, intCodDoc );
         strSql="INSERT INTO tbm_detrecdoc(co_emp, co_loc, co_tipdoc, co_doc,  co_reg,  co_cli, co_banchq,  tx_numctachq, tx_numchq, fe_recchq, " +
         " fe_venchq,  nd_monchq, tx_obs1, nd_valapl, st_asidocrel,  st_regrep , st_reg  ) " +
         " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" " +
         " ,"+intNumReg+", "+tblDat.getValueAt(i, INT_TBL_CODCLI).toString()+", "+tblDat.getValueAt(i, INT_TBL_CODBAN).toString()+"  " +
         " ,'"+(tblDat.getValueAt(i, INT_TBL_NUMCTA)==null?"":tblDat.getValueAt(i, INT_TBL_NUMCTA).toString())+"', '"+tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString()+"' "+
         ", '"+strFecDoc+"', '"+objUti.formatearFecha(tblDat.getValueAt(i,INT_TBL_FECVEN).toString(),"dd/MM/yyyy","yyyy/MM/dd")+"' " +
         ", "+dblValChq+", '"+(tblDat.getValueAt(i, INT_TBL_OBSERV)==null?"":tblDat.getValueAt(i, INT_TBL_OBSERV).toString())+"' " +
         ", 0  "+
         " , '"+strEstasidocrel+"' ,'I', 'A' )  ;  ";


        if( !((tblDat.getValueAt(i, INT_TBL_DATFAC)==null?"":(tblDat.getValueAt(i, INT_TBL_DATFAC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_DATFAC).toString())).equals("")) ){

              strSql +="INSERT INTO tbr_detrecdocpagmovinv( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel," +
              " co_tipdocrel, co_docrel, co_regrel, st_regrep) " +
              " select "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" ,"+intNumReg+",  coemp, coloc, cotipdoc, codoc, coreg, 'I' from( " +
              tblDat.getValueAt(i, INT_TBL_DATFAC).toString()+" ) as x  ; ";

              strSql +=" UPDATE tbm_pagmovinv SET " +
              " st_entsop='S', st_pos='S', co_banchq=x.cobanchq, tx_numctachq=x.txnumctachq, tx_numchq=x.txnumchq, fe_recchq=x.ferecchq, fe_venchq=x.fevenchq, nd_monchq=x.valapl" +
              " FROM ( " +
              "  select *, "+tblDat.getValueAt(i, INT_TBL_CODBAN).toString()+" as cobanchq " +
              " ,"+(tblDat.getValueAt(i, INT_TBL_VALCHQ)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALCHQ).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALCHQ).toString()))+" as monchq "+
              ", text('"+(tblDat.getValueAt(i, INT_TBL_NUMCTA)==null?"":tblDat.getValueAt(i, INT_TBL_NUMCTA).toString())+"') as  txnumctachq " +
              ", text('"+tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString()+"') as txnumchq " +
              ", date('"+strFecDoc+"') as ferecchq, date('"+objUti.formatearFecha(tblDat.getValueAt(i,INT_TBL_FECVEN).toString(),"dd/MM/yyyy","yyyy/MM/dd")+"') as fevenchq from ( " +
              " "+tblDat.getValueAt(i, INT_TBL_DATFAC).toString()+" "+
              " ) as x " +
              " ) as x WHERE tbm_pagmovinv.co_emp=x.coemp AND tbm_pagmovinv.co_loc=x.coloc AND tbm_pagmovinv.co_tipdoc=x.cotipdoc " +
              "  AND tbm_pagmovinv.co_doc=x.codoc AND tbm_pagmovinv.co_reg=x.coreg ";

        }
       //System.out.println("---> "+strSql);
         stmLoc.executeUpdate(strSql);

    }}}


      stmLoc.close();
      stmLoc=null;
    blnRes=true;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

private boolean insertarDetNuevosRecRet(java.sql.Connection conn, int intCodTipDoc, int intCodDoc){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 String strFecDoc="";
 String strEstasidocrel="N";
 int intNumReg=0;
 double dblValChq=0;
 double dblValFac=0;
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";

     for(int i=0; i<tblDat.getRowCount(); i++){
      if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){
       if( ((tblDat.getValueAt(i, INT_TBL_CODREG)==null?"":(tblDat.getValueAt(i, INT_TBL_CODREG).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODREG).toString())).equals("")) ){

          strEstasidocrel="N";

          dblValChq=objUti.redondear( (tblDat.getValueAt(i, INT_TBL_VALCHQ)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALCHQ).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALCHQ).toString()) ), 2 );
          dblValFac=objUti.redondear( (tblDat.getValueAt(i, INT_TBL_VAPLFAC)==null?"0":(tblDat.getValueAt(i, INT_TBL_VAPLFAC).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VAPLFAC).toString()) ),2 );
          if(dblValChq==dblValFac)
              strEstasidocrel="S";

         intNumReg = _getObtenerMaxCodRegDet(conn, intCodTipDoc, intCodDoc );
         strSql="INSERT INTO tbm_detrecdoc(co_emp, co_loc, co_tipdoc, co_doc,  co_reg,  co_cli, tx_numchq, fe_recchq, " +
         "  nd_monchq, tx_obs1, nd_valapl, st_asidocrel,  st_regrep , st_reg  ) " +
         " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" " +
         " ,"+intNumReg+", "+tblDat.getValueAt(i, INT_TBL_CODCLI).toString()+"  " +
         " , '"+tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString()+"' "+
         ", '"+strFecDoc+"'  " +
         ", "+dblValChq+", '"+(tblDat.getValueAt(i, INT_TBL_OBSERV)==null?"":tblDat.getValueAt(i, INT_TBL_OBSERV).toString())+"' " +
         ", 0  "+
         " , '"+strEstasidocrel+"' ,'I', 'A' )  ;  ";


        if( !((tblDat.getValueAt(i, INT_TBL_DATFAC)==null?"":(tblDat.getValueAt(i, INT_TBL_DATFAC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_DATFAC).toString())).equals("")) ){

              strSql +="INSERT INTO tbr_detrecdocpagmovinv( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel," +
              " co_tipdocrel, co_docrel, co_regrel, st_regrep) " +
              " select "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" ,"+intNumReg+",  coemp, coloc, cotipdoc, codoc, coreg, 'I' from( " +
              tblDat.getValueAt(i, INT_TBL_DATFAC).toString()+" ) as x  ; ";

              strSql +=" UPDATE tbm_pagmovinv SET " +
              " st_entsop='S', st_pos='S',  tx_numchq=x.txnumchq, fe_recchq=x.ferecchq, fe_venchq=x.ferecchq, nd_monchq=x.valapl" +
              " FROM ( " +
              "  select * " +
              ", text('"+tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString()+"') as txnumchq " +
              ", date('"+strFecDoc+"') as ferecchq  from ( " +
              " "+tblDat.getValueAt(i, INT_TBL_DATFAC).toString()+" "+
              " ) as x " +
              " ) as x WHERE tbm_pagmovinv.co_emp=x.coemp AND tbm_pagmovinv.co_loc=x.coloc AND tbm_pagmovinv.co_tipdoc=x.cotipdoc " +
              "  AND tbm_pagmovinv.co_doc=x.codoc AND tbm_pagmovinv.co_reg=x.coreg ";

        }
       //System.out.println("---> "+strSql);
         stmLoc.executeUpdate(strSql);

    }}}


      stmLoc.close();
      stmLoc=null;
    blnRes=true;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

   
public int _getObtenerMaxCodRegDet(java.sql.Connection conn, int intCodTipDoc, int intCodDoc){
  int intCodReg=-1;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     if(conn!=null){
       stmLoc=conn.createStatement();

       strSql="SELECT CASE WHEN max(co_reg) IS NULL THEN 1 ELSE max(co_reg)+1 END AS coreg FROM tbm_detrecdoc " +
       " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
       " AND co_tipdoc="+intCodTipDoc+" AND co_doc= "+intCodDoc+" ";
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next()){
             intCodReg=rstLoc.getInt("coreg");
       }
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;
  }}catch(java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }
    catch(Exception  Evt){ objUti.mostrarMsgErr_F1(this, Evt); }
 return intCodReg;
}

public boolean EliminadosRegRec(java.sql.Connection conn, int intCodTipDoc, int intCodDoc){
  boolean blnRes=true;
  java.sql.Statement stmLoc, stmLoc01;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     if(conn!=null){
       stmLoc=conn.createStatement();
       stmLoc01=conn.createStatement();

         java.util.ArrayList arlAux=objTblMod.getDataSavedBeforeRemoveRow();
         if(arlAux!=null){
           for(int i=0;i<arlAux.size();i++){
               int intCodReg = objUti.getIntValueAt(arlAux, i, INT_ARR_CODREG);

               strSql="SELECT st_reg FROM tbm_detrecdoc WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
               " AND co_tipdoc="+intCodTipDoc+" AND co_doc= "+intCodDoc+" AND co_reg="+intCodReg+" and nd_valapl <= 0 ";
               rstLoc=stmLoc.executeQuery(strSql);
               if(rstLoc.next()){

                   strSql="UPDATE tbm_detrecdoc SET st_reg='E' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND" +
                   " co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" and co_reg="+intCodReg;
                   strSql+=" ; UPDATE tbr_detrecdocpagmovinv SET st_reg='E' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND" +
                   " co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" and co_reg="+intCodReg;

                   strSql+=" ; UPDATE tbm_pagmovinv SET nd_monchq=0, fe_venchq=null, fe_recchq=null, tx_numchq=null, co_banchq=null, " +
                   " tx_numctachq=null, st_pos='N', st_entsop='N' FROM ( "+
                   "  SELECT  co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel FROM tbr_detrecdocpagmovinv  WHERE " +
                   "  co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
                   "  AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" and co_reg="+intCodReg+" "+
                   " ) AS x WHERE tbm_pagmovinv.co_emp=x.co_emprel AND tbm_pagmovinv.co_loc=x.co_locrel AND tbm_pagmovinv.co_tipdoc=x.co_tipdocrel " +
                   " AND tbm_pagmovinv.co_doc=x.co_docrel AND tbm_pagmovinv.co_reg=x.co_regrel ";

                   stmLoc.executeUpdate(strSql);

               }else{
                   MensajeInf(" NO ELIMINADO... > ");
               }
               rstLoc.close();
               rstLoc=null;

            }
          }
      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;
  }}catch(java.sql.SQLException e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
    catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}






/**
 * Se encarga de modificar la cabezera del registro
 * @param conn  coneccion de la base
 * @param intCodTipDoc   codigo del tipo de documento
 * @param intCodDoc      codigo de documento
 * @return
 */
private boolean modificarCab(java.sql.Connection conn, int intCodTipDoc, int intCodDoc){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 String strFecDoc="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();
//
//        //************  PERMITE SABER SI EL NUMERO DE Devolucion ESTA DUPLICADO  *****************/
//           if(!txtNumDocSolOcu.getText().equals(txtNumDoc.getText())){
//             strSql ="select count(ne_numdoc) as num from tbm_cabsoldevven WHERE " +
//                     " co_emp="+intCodEmpSol+" and co_loc="+intCodLocSol+" " +
//                     "and co_tipdoc="+txtCodTipDoc.getText()+" and ne_numdoc="+txtNumDoc.getText();
//              rstLoc = stmLoc.executeQuery(strSql);
//              if(rstLoc.next()){
//                  if(rstLoc.getInt("num") >= 1 ){
//                        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
//                        String strTit, strMsg;
//                        strTit="Mensaje del sistema Zafiro";
//                        strMsg=" No. de Solicitud ya existe... ?";
//                        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE,null);
//                        blnRes=true;
//              }}
//              rstLoc.close();
//              rstLoc=null;
//              if(blnRes) return false;
//              blnRes=false;
//           }
//        //***********************************************************************************************/
//
        strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";

         strSql="UPDATE tbm_cabrecdoc SET  fe_doc='"+strFecDoc+"', ne_numdoc1="+txtNumDoc.getText()+", co_usrrec="+(txtCodRec.getText().equals("")?null:txtCodRec.getText())+",  " +
         " nd_mondoc="+(valDoc.getText().equals("")?"0":valDoc.getText())+", "+
         " tx_obs1='', tx_obs2='', fe_ultmod="+objZafParSis.getFuncionFechaHoraBaseDatos()+", " +
         " co_usrmod="+objZafParSis.getCodigoUsuario()+", "+
         " tx_comultmod='"+objZafParSis.getNombreComputadoraConDirIP()+"' "+
         " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+intCodTipDoc+" " +
         " AND co_doc="+intCodDoc;
         stmLoc.executeUpdate(strSql);

      stmLoc.close();
      stmLoc=null;
    
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}





     private void bloquea(javax.swing.JTextField txtFiel,  java.awt.Color colBack, boolean blnEst){
        colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }

     private void noEditable(boolean blnEst){
//        java.awt.Color colBack = txtDesCodTitpDoc.getBackground();
//         bloquea(txtCodLoc, colBack, blnEst);
//         bloquea(txtDesLoc, colBack, blnEst);
//         bloquea(txtDesCodTitpDoc, colBack, blnEst);
//         bloquea(txtDesLarTipDoc, colBack, blnEst);
//         bloquea(txtCodDoc, colBack, blnEst);
//         bloquea(txtNomCli, colBack, blnEst);
//         bloquea(txtCodDocFac, colBack, blnEst);
//         bloquea(txtSub, colBack, blnEst);
//         bloquea(txtIva, colBack, blnEst);
//         bloquea(valDoc, colBack, blnEst);
//         bloquea(txtFecDocFac, colBack, blnEst);
//        txaObs1.setEditable(blnEst);
//        txaObs2.setEditable(blnEst);
      }




/**
 * carga el tipo de documento cuando se da en click insertar y consultar
 * @param intVal  valor si tiene que cargar numero de documento o no 1 = si cargar  
 */
public void cargarTipoDoc(int intVal){
  java.sql.Connection  conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
        stmLoc=conn.createStatement();

      if(objZafParSis.getCodigoUsuario()==1){
        strSql= "SELECT  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor, " +
        " case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc "+
        " ,doc.tx_natdoc, doc.st_meringegrfisbod " +
        " FROM tbr_tipdocprg as menu " +
        " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "+
        " WHERE   menu.co_emp = "+objZafParSis.getCodigoEmpresa() + " and " +
        " menu.co_loc = " + objZafParSis.getCodigoLocal()+" AND  " +
        " menu.co_mnu = "+objZafParSis.getCodigoMenu()+" AND  menu.st_reg = 'S' ";
    }else{

        strSql= "SELECT  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor, " +
        " case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc "+
        " ,doc.tx_natdoc, doc.st_meringegrfisbod " +
        " FROM tbr_tipDocUsr as menu " +
        " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "+
        " WHERE   menu.co_emp = "+objZafParSis.getCodigoEmpresa() + " and " +
        " menu.co_loc = " + objZafParSis.getCodigoLocal()+" AND  " +
        " menu.co_mnu = "+objZafParSis.getCodigoMenu()+" AND  " +
        " menu.co_usr="+objZafParSis.getCodigoUsuario()+" AND menu.st_reg = 'S' ";

    }          
        
        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()){
            txtCodTipDoc.setText(((rstLoc.getString("co_tipdoc")==null)?"":rstLoc.getString("co_tipdoc")));
            txtDesCodTitpDoc.setText(((rstLoc.getString("tx_descor")==null)?"":rstLoc.getString("tx_descor")));
            txtDesLarTipDoc.setText(((rstLoc.getString("tx_deslar")==null)?"":rstLoc.getString("tx_deslar")));
            strCodTipDoc=txtCodTipDoc.getText();
            if(intVal==1)
               txtNumDoc.setText(((rstLoc.getString("numDoc")==null)?"":rstLoc.getString("numDoc")));
        }
        rstLoc.close();
        stmLoc.close();
        stmLoc=null;
        rstLoc=null;
        conn.close();
        conn=null;
  }}catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }
}





 public void  clnTextos(){
    strCodTipDoc=""; strDesCodTipDoc=""; strDesLarTipDoc="";
    strCodSol=""; strDesSol="";

    txtFecDoc.setText("");

    txtDesCodTitpDoc.setText("");
    txtDesLarTipDoc.setText("");
    valDoc.setText("0.00");
    txtFecDoc.setText("");
    txtCodRec.setText("");
    txtDesRec.setText("");
    txtNumDoc.setText("");
    txtCodDoc.setText("");
   // txtObs1.setText("");
   // txtObs2.setText("");

    objTblMod.removeAllRows();
 }




        public boolean cancelar() {
            boolean blnRes=true;

            try {
                if (blnHayCam || objTblMod.isDataModelChanged()) {
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m') {
                        if (!isRegPro())
                            return false;
                    }
                }
                if (rstCab!=null) {
                    rstCab.close();
                    if (STM_GLO!=null){
                        STM_GLO.close();
                        STM_GLO=null;
                    }
                    rstCab=null;

                }
            }
            catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            clnTextos();
            blnHayCam=false;

            return blnRes;
        }




        public boolean aceptar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterAnular() {
            return true;
        }

        public boolean afterCancelar() {

            return true;
        }

        public boolean afterConsultar() {

            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterInsertar() {
            this.setEstado('w');

            return true;
        }

        public boolean afterModificar() {

           this.setEstado('w');

            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }







          /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }






  /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro() {
        boolean blnRes=true;
//        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
//        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
//        switch (mostrarMsgCon(strAux)) {
//            case 0: //YES_OPTION
//                switch (objTooBar.getEstado()) {
//                    case 'n': //Insertar
//                        blnRes=objTooBar.insertar();
//                        break;
//                    case 'm': //Modificar
////                        blnRes=objTooBar.modificar();
//                        break;
//                }
//                break;
//            case 1: //NO_OPTION
//                objTblMod.setDataModelChanged(false);
//                blnHayCam=false;
//                blnRes=true;
//                break;
//            case 2: //CANCEL_OPTION
//                blnRes=false;
//                break;
//        }
        return blnRes;
    }





        public boolean consultar() {
                /*
                 * Esto Hace en caso de que el modo de operacion sea Consulta
                 */
           return _consultar(FilSql());
        }







private boolean _consultar(String strFil){
  boolean blnRes=false;
  String strSql="";
   try{
      if(!validarDat()) return false;

        abrirCon();
        if(CONN_GLO!=null) {
            STM_GLO=CONN_GLO.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY );

           strSql="SELECT  a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc , a.st_reg,  a.fe_doc "+
           " FROM tbm_cabrecdoc AS a " +
           " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" AND a.st_reg NOT IN('E') " +
           " "+strFil+" ORDER BY a.fe_doc, a.co_doc ";
           rstCab=STM_GLO.executeQuery(strSql);
           if(rstCab.next()){
              rstCab.last();
              setMenSis("Se encontraron " + rstCab.getRow() + " registros");
              refrescaDatos(CONN_GLO, rstCab);
              blnRes=true;
           }else{
                setMenSis("0 Registros encontrados");
                clnTextos();
           }

      CerrarCon();
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

    System.gc();
    return blnRes;
}


/**
 * Carga los datos  de la reccion previo a la consulta
 * @param conn  coneccion de la base
 * @param rstDatRec  resulset de los datos consultados
 * @return  true si se cargo con exito  false si no cargo 
 */
private boolean refrescaDatos(java.sql.Connection conn, java.sql.ResultSet rstDatRec ){
    boolean blnRes=false;
    java.sql.Statement stmLoc, stmLoc01;
    java.sql.ResultSet rstLoc, rstLoc01, rstLoc02;
    String strSql="";
    String strAux="";
    String strDatFac="";
    Vector vecData;
    int intCon=0;
    double dblCanAplFac=0;
    try{
      if(conn!=null){
        stmLoc=conn.createStatement();
        stmLoc01=conn.createStatement();

        /**********CARGAR DATOS DE CABEZERA ***************/
  
       strSql="SELECT  a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a.fe_doc, a.ne_numdoc1, a.nd_mondoc, a.co_usrrec,  a2.tx_nom " +
       " ,a1.tx_descor, a1.tx_deslar, a.st_reg  " +
       " FROM tbm_cabrecdoc AS a " +
       " INNER JOIN tbm_cabtipdoc AS a1 on ( a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc ) " +
       " LEFT JOIN tbm_usr AS a2 on (a2.co_usr=a.co_usrrec ) " +
       " WHERE a.co_emp="+rstDatRec.getInt("co_emp")+" and a.co_loc="+rstDatRec.getInt("co_loc")+" " +
       " and a.co_tipdoc="+rstDatRec.getInt("co_tipdoc")+"  and a.co_Doc="+rstDatRec.getInt("co_doc")+" ";
       rstLoc02=stmLoc.executeQuery(strSql);
       if(rstLoc02.next()){

        txtCodTipDoc.setText( rstLoc02.getString("co_tipdoc"));
        txtDesCodTitpDoc.setText( rstLoc02.getString("tx_descor"));
        txtDesLarTipDoc.setText( rstLoc02.getString("tx_deslar"));
        txtCodRec.setText( rstLoc02.getString("co_usrrec"));
        txtDesRec.setText( rstLoc02.getString("tx_nom"));
        txtCodDoc.setText( rstLoc02.getString("co_doc"));
        txtNumDoc.setText( rstLoc02.getString("ne_numdoc1"));
        valDoc.setText(""+ objUti.redondear( rstLoc02.getString("nd_mondoc"), 2) );

        strAux=rstLoc02.getString("st_reg");

        java.util.Date dateObj = rstLoc02.getDate("fe_doc");
        if(dateObj==null){
          txtFecDoc.setText("");
        }else{
            java.util.Calendar calObj = java.util.Calendar.getInstance();
            calObj.setTime(dateObj);
            txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                              calObj.get(java.util.Calendar.MONTH)+1     ,
                              calObj.get(java.util.Calendar.YEAR)        );
        }
      }
      rstLoc02.close();
      rstLoc02=null;

       /***************************************************/


        /**********CARGAR DATOS DE DETALLE ***************/
        vecData = new Vector();

        strSql="SELECT CASE WHEN co_tipdoccon IS NULL THEN 'N' ELSE 'S' END AS AsigBan,  CASE WHEN nd_valapl > 0 THEN 'S' ELSE  'N' END AS estApl, " +
        " a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, a.co_cli, a1.tx_nom,  a.co_banchq,  a.tx_numctachq, a.tx_numchq, a.fe_recchq,  " +
        " a.fe_venchq, a.nd_monchq, a.tx_obs1, a.nd_valapl, a.fe_asitipdoccon, a.st_asidocrel, a.st_regrep, a.st_reg,  a2.tx_descor, a2.tx_deslar  " +
        " FROM tbm_detrecdoc as a " +
        " INNER JOIN tbm_cli as a1 ON (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli) " +
        " LEFT  JOIN tbm_var as a2 ON (a2.co_reg=a.co_banchq ) "+
        " WHERE a.co_emp="+rstDatRec.getInt("co_emp")+" and a.co_loc="+rstDatRec.getInt("co_loc")+" " +
        " and a.co_tipdoc="+rstDatRec.getInt("co_tipdoc")+"  and a.co_Doc="+rstDatRec.getInt("co_doc")+" AND a.st_reg NOT IN ('E') " +
        " ORDER BY a.co_reg ";
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

           java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL_LINEA,"");
            vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
            vecReg.add(INT_TBL_BUTCLI, "" );
            vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nom") );
            vecReg.add(INT_TBL_CODBAN, rstLoc.getString("co_banchq") );
            vecReg.add(INT_TBL_DSCBAN, rstLoc.getString("tx_descor") );
            vecReg.add(INT_TBL_BUTBAN, "" );
            vecReg.add(INT_TBL_NOMBAN, rstLoc.getString("tx_deslar") );
            vecReg.add(INT_TBL_NUMCTA, rstLoc.getString("tx_numctachq") );
            vecReg.add(INT_TBL_NUMCHQ, rstLoc.getString("tx_numchq") );
            vecReg.add(INT_TBL_VALCHQ, rstLoc.getString("nd_monchq") );
            vecReg.add(INT_TBL_FECVEN, objUti.formatearFecha(rstLoc.getDate("fe_venchq"), "dd/MM/yyyy") ); // rstLoc.getString("fe_venchq") );
            vecReg.add(INT_TBL_BUTFAC, "" );
            vecReg.add(INT_TBL_OBSERV, rstLoc.getString("tx_obs1") );
  
        	
            strDatFac="";
            intCon=0;
            dblCanAplFac=0;
            strSql="SELECT  a.co_emprel,  a.co_locrel, a.co_tipdocrel, a.co_docrel, a.co_regrel, a1.nd_monchq as valapl FROM " +
            " tbr_detrecdocpagmovinv AS a " +
            " INNER JOIN tbm_pagmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel " +
            " and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) " +
            " WHERE a.co_emp="+rstLoc.getInt("co_emp")+" and a.co_loc="+rstLoc.getInt("co_loc")+" and a.co_tipdoc="+rstLoc.getInt("co_tipdoc")+" " +
            " and a.co_doc= "+rstLoc.getInt("co_doc")+" and a.co_reg="+rstLoc.getInt("co_reg")+" AND a.st_reg NOT IN ('E') ";
            rstLoc01=stmLoc01.executeQuery(strSql);
            while(rstLoc01.next()){
                if(intCon==1) strDatFac+=" UNION ALL ";
                strDatFac +=" SELECT "+rstLoc01.getInt("co_emprel")+" as coemp, "+rstLoc01.getInt("co_locrel")+" as coloc, " +
                " "+rstLoc01.getInt("co_tipdocrel")+" as cotipdoc, "+rstLoc01.getInt("co_docrel")+" as codoc," +
                " "+rstLoc01.getInt("co_regrel")+" as coreg, "+rstLoc01.getDouble("valapl")+" as valapl  ";
                intCon=1;
                dblCanAplFac+=rstLoc01.getDouble("valapl");
            }
            rstLoc01.close();
            rstLoc01=null;

            vecReg.add(INT_TBL_DATFAC, strDatFac );
            vecReg.add(INT_TBL_VAPLFAC, ""+dblCanAplFac );
            vecReg.add(INT_TBL_VALCHQORI, rstLoc.getString("nd_monchq") );
            vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg") );
            vecReg.add(INT_TBL_ESTAPL, rstLoc.getString("estApl") );
            vecReg.add(INT_TBL_CODCLIORI, rstLoc.getString("co_cli") );
            vecReg.add(INT_TBL_ASIGBAN, rstLoc.getString("AsigBan") );
            vecReg.add(INT_TBL_ESTCAMFAC, "N" );


            vecData.add(vecReg);
       }
        rstLoc.close();
        rstLoc=null;

        objTblMod.setData(vecData);
        tblDat .setModel(objTblMod);
   
       /***************************************************/
        stmLoc.close();
        stmLoc=null;

        
        
        if (strAux.equals("A"))
            strAux="Activo";
         else if (strAux.equals("I"))
             strAux="Anulado";
          else if (strAux.equals("E"))
             strAux="Eliminado";
          else
              strAux="Otro";
         objTooBar.setEstadoRegistro(strAux);

            int intPosRel=rstDatRec.getRow();
            rstDatRec.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstDatRec.getRow());
            rstDatRec.absolute(intPosRel);
        
        blnRes=true;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
   catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}







private String FilSql() {
    String sqlFiltro = "";
    //Agregando filtro por Numero de Cotizacion
    if(!txtCodTipDoc.getText().equals(""))
        sqlFiltro = sqlFiltro + " and a.co_tipdoc="+txtCodTipDoc.getText();
    else
        sqlFiltro = sqlFiltro + " and a.co_tipdoc in ("+strSqlTipDocAux+") ";

    if(!txtCodRec.getText().equals(""))
        sqlFiltro = sqlFiltro + " and a.co_usrrec="+txtCodRec.getText();

    if(!txtCodDoc.getText().equals(""))
        sqlFiltro = sqlFiltro + " and a.co_doc="+txtCodDoc.getText();
    
    
     if(!txtNumDoc.getText().equals(""))
        sqlFiltro = sqlFiltro + " and a.ne_numdoc1="+txtNumDoc.getText();


     if(txtFecDoc.isFecha()){
         int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
         String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
         sqlFiltro = sqlFiltro + " and a.fe_doc = '" +  strFecSql + "'";
      }

    return sqlFiltro ;
}





public void clickModificar(){
 try{
  setEditable(true);
 // noEditable(false);

  java.awt.Color colBack;
  colBack = txtCodDoc.getBackground();

  valDoc.setEditable(false);
  valDoc.setBackground(colBack);


  bloquea(txtCodDoc, colBack, false);
  bloquea(txtDesCodTitpDoc, colBack, false);
  bloquea(txtDesLarTipDoc, colBack, false);
  bloquea(valDoc, colBack, false);
  bloquea(txtNumDoc, colBack, false);

  if(objZafParSis.getCodigoMenu()==1824){
      bloquea(txtCodRec, colBack, false);
      bloquea(txtDesRec, colBack, false);
      butRec.setEnabled(false);
      txtFecDoc.setEnabled(false);
  }

//  bloquea(txtNumDocFac, colBack, false);
//
//  if(intEstCon==1){
//
//      bloquea(txtCodSol, colBack, false);
//      bloquea(txtDesSol, colBack, false);
//      bloquea(txtNumDoc, colBack, false);
//       txtObs1.setEditable(false);
//       txtObs2.setEditable(false);
//
//      radCan.setEnabled(false);
//      radDes.setEnabled(false);
//      radPre.setEnabled(false);
//
//      butSol.setEnabled(false);
//  }
//
  butTipDoc.setEnabled(false);
//  butBusFac.setEnabled(false);
//
// this.setEnabledConsultar(false);
//
 objTblMod.setDataModelChanged(false);
 blnHayCam=false;

 if(!(objZafParSis.getCodigoMenu()==1824)){
  if(!_estadoImpDoc())
    this.setEstado('w');
 }


 }catch(Exception evt){ objUti.mostrarMsgErr_F1(this, evt); }
}

/**
 * verificar el estado de impresion  realizando la coneccion a la base 
 * @return
 */
private boolean _estadoImpDoc(){
  boolean blnRes=true;
  java.sql.Connection conn;
  int intCodTipDoc=0;
  int intCodDoc=0;
  try{
     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){

        intCodDoc=Integer.parseInt(txtCodDoc.getText());
        intCodTipDoc=Integer.parseInt(txtCodTipDoc.getText());

        if( !_getEstImp( conn, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(),  intCodTipDoc,  intCodDoc ) ){
            blnRes=false;
        }
        conn.close();
        conn=null;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
   catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}


/**
 * Funcion que permite saber si el documento esta Impreso
 * @param conn Coneccion de la base
 * @param intCodEmp Codigo de la empresa
 * @param intCodLoc  Codigo del local
 * @param intCodTipDoc Codigo del tipo del documento
 * @param intCodDoc  Codigo documento
 * @return   true no esta impreso  false si esta impreso
 */
private boolean _getEstImp(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     if(conn!=null){
       stmLoc=conn.createStatement();

      if(!(objZafParSis.getCodigoMenu()==1824)){

       strSql="SELECT st_imp FROM tbm_cabrecdoc WHERE  co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+"" +
       " AND co_doc="+intCodDoc+"  and st_imp='S'";
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next()){
           blnRes=false;
           MensajeInf("EL DOCUMENTO YA ESTA IMPRESO Y NO SE PUEDE MODIFICAR ..");
       }
       rstLoc.close();
       rstLoc=null;

     }

       stmLoc.close();
       stmLoc=null;

  }}catch(java.sql.SQLException ex) { blnRes=false; objUti.mostrarMsgErr_F1(this, ex);   }
    catch(Exception Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}



private void cargarRepote(int intTipo){
   if (objThrGUI==null)
    {
        objThrGUI=new ZafThreadGUI();
        objThrGUI.setIndFunEje(intTipo);
        objThrGUI.start();
    }
}




        //******************************************************************************************************

        public boolean vistaPreliminar(){
              cargarRepote(1);

//            java.sql.Connection conIns;
//            String strNomUsr="";
//            String strFecHorSer="";
//            String strTitRep="";
//            try {
//                conIns =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//                try {
//                    if(conIns!=null){
//
//                        JasperDesign jasperDesign = JasperManager.loadXmlDesign(DIRECCION_REPORTE);
//                        JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
//
//                         //Obtener la fecha y hora del servidor.
//                        datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
//                        if (datFecAux==null)
//                            return false;
//                        strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
//                        datFecAux=null;
//
//                        strNomUsr=objZafParSis.getNombreUsuario();
//
//                         strTitRep="Recepción de Documentos Cheques";
//                        if(objZafParSis.getCodigoMenu()==1749)
//                         strTitRep="Recepción de Documentos Retenciones";
//
//                        Map parameters = new HashMap();
//                        parameters.put("coemp",  new Integer(objZafParSis.getCodigoEmpresa()) );
//                        parameters.put("coloc",  new Integer(objZafParSis.getCodigoLocal()));
//                        parameters.put("cotipdoc", new Integer( Integer.parseInt(txtCodTipDoc.getText())) );
//                        parameters.put("codoc", new Integer( Integer.parseInt(txtCodDoc.getText())) );
//                        parameters.put("nomusr", strNomUsr );
//                        parameters.put("fecimp", strFecHorSer );
//                        parameters.put("strTit", strTitRep );
//
//                        /// JasperPrint report=JasperFillManager.fillReport(DIRECCION_REPORTE, parameters,  conIns);
//                        JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
//                        JasperViewer.viewReport(report, false);
//
//                        cambiarEstImp( conIns, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()) );
//
//                        conIns.close();
//                        conIns=null;
//                    }
//                }
//                catch (JRException e) {  objUti.mostrarMsgErr_F1(this, e);    }
//            } catch(java.sql.SQLException ex) {  objUti.mostrarMsgErr_F1(this, ex);   }
            return true;
        }





        public boolean imprimir(){

              cargarRepote(1);

              
//            java.sql.Connection conIns;
//            String strNomUsr="";
//            String strFecHorSer="";
//            String strTitRep="";
//            try {
//                conIns =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//                try {
//                    if(conIns!=null){
//
//                        JasperDesign jasperDesign = JasperManager.loadXmlDesign(DIRECCION_REPORTE);
//                        JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
//
//                         //Obtener la fecha y hora del servidor.
//                        datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
//                        if (datFecAux==null)
//                            return false;
//                        strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
//                        datFecAux=null;
//
//                        strNomUsr=objZafParSis.getNombreUsuario();
//
//                       strTitRep="Recepción de Documentos Cheques";
//                        if(objZafParSis.getCodigoMenu()==1749)
//                         strTitRep="Recepción de Documentos Retenciones";
//
//                        Map parameters = new HashMap();
//                        parameters.put("coemp",  new Integer(objZafParSis.getCodigoEmpresa()) );
//                        parameters.put("coloc",  new Integer(objZafParSis.getCodigoLocal()));
//                        parameters.put("cotipdoc", new Integer( Integer.parseInt(txtCodTipDoc.getText())) );
//                        parameters.put("codoc", new Integer( Integer.parseInt(txtCodDoc.getText())) );
//                        parameters.put("nomusr", strNomUsr );
//                        parameters.put("fecimp", strFecHorSer );
//                        parameters.put("strTit", strTitRep );
//
//                        /// JasperPrint report=JasperFillManager.fillReport(DIRECCION_REPORTE, parameters,  conIns);
//                        JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
//                        JasperManager.printReport(report,false);
//
//                        cambiarEstImp( conIns, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()) );
//
//                        conIns.close();
//                        conIns=null;
//                    }
//                }
//                catch (JRException e) {  objUti.mostrarMsgErr_F1(this, e);    }
//            } catch(java.sql.SQLException ex) {  objUti.mostrarMsgErr_F1(this, ex);   }
            return true;
        }


        //******************************************************************************************************


        public void clickImprimir(){
        }
        public void clickVisPreliminar(){
        }

        public void clickCancelar(){

        }

        public void cierraConnections(){

        }


        public boolean beforeAceptar() {
            return true;
        }
        public boolean beforeAnular() {
            return true;
        }
        public boolean beforeCancelar() {
            return true;
        }
        public boolean beforeConsultar() {
            return true;
        }
        public boolean beforeEliminar() {
            return true;
        }
        public boolean beforeImprimir() {

            return true;
        }
        public boolean beforeInsertar() {
            return true;
        }
        public boolean beforeModificar() {
            return true;
        }
        public boolean beforeVistaPreliminar() {

            return true;
        }




    }

    

   /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        private int intIndFun;

        public ZafThreadGUI()
        {
            intIndFun=0;
        }

        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                    objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                    objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUI=null;
        }

        /**
         * Esta función establece el indice de la función a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta función sirve para determinar
         * la función que debe ejecutar el Thread.
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }




    /**
     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {
        String strRutRpt, strNomRpt, strTitRep, strNomUsr="";
        int i, intNumTotRpt;
        boolean blnRes=true;
        try
        {
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
            {

                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                strNomUsr=objZafParSis.getNombreUsuario();
                strTitRep=objZafParSis.getNombreMenu();

                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            case 19:
                            default:
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.

                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("codEmp", new Integer(objZafParSis.getCodigoEmpresa()) );
                                mapPar.put("codLoc", new Integer(objZafParSis.getCodigoLocal()) );
                                mapPar.put("codTipDoc", new Integer( Integer.parseInt(txtCodTipDoc.getText())) );
                                mapPar.put("codDoc", new Integer( Integer.parseInt(txtCodDoc.getText())) );
                                mapPar.put("nomUsr", strNomUsr );
                                mapPar.put("titRpt", strTitRep );

//                                mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);

                                cambiarEstImp( objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()) );

                                break;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }




private void cambiarEstImp( int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  String strSql="";
  try{
      conn =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       stmLoc=conn.createStatement();
       strSql="UPDATE tbm_cabrecdoc SET st_imp='S' WHERE  co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+"" +
       " AND co_doc="+intCodDoc+" ";
       System.out.println("--> "+ strSql );
       stmLoc.executeUpdate(strSql);

       stmLoc.close();
       stmLoc=null;
       conn.close();
       conn=null;
  }}catch(java.sql.SQLException ex) {  objUti.mostrarMsgErr_F1(this, ex);   }
    catch(Exception Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  }
}





    protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }

     


}
