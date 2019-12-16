/*
 * ZafCxC46.java
 *
 * Created on Jul 30, 2010, 17:15:18 PM
 */
          
package CxC.ZafCxC46;
       
import GenOD.ZafGenOdPryTra;
import Librerias.ZafCnfDoc.ZafCnfDoc;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil; /**/
import java.util.Vector; /**/
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafVenCon.ZafVenConCxC01;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import java.util.ArrayList;
import Librerias.ZafRptSis.ZafRptSis;
import ZafReglas.ZafGenGuiRem;
import ZafReglas.ZafImp;
import ZafReglas.ZafMetImp;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
//import net.sf.jasperreports.view.JasperViewer;
//import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.JasperManager;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JRException;
//import java.util.HashMap;
//import java.util.Map;


/**
 *
 * @author jayapata
 */
public class ZafCxC46 extends javax.swing.JInternalFrame {
  Librerias.ZafParSis.ZafParSis objZafParSis;
  ZafUtil objUti; /**/
  private Librerias.ZafDate.ZafDatePicker txtFecDoc;
  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod; /**/
  private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
  private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;
  private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
  private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
  private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt;
  private ZafTblCelEdiTxtVco  objTblCelEdiTxtVcoFacRet;
  private ZafTblCelEdiButGen objTblCelEdiButGen;      //Editor: JButton en celda.
  private Librerias.ZafAsiDia.ZafAsiDia objAsiDia; // Asiento de Diario
  private Librerias.ZafAjuCenAut.ZafAjuCenAut objAjuCenAut;
  Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu objTblPopMnu;
  private java.util.Date datFecAux;
  mitoolbar objTooBar;
  private ZafThreadGUI objThrGUI;
  private ZafRptSis objRptSis;
  private ZafCxC46_02 objLiberaGuia;


  ZafVenCon objVenConTipdoc;
  ZafVenConCxC01 objVenConCxC01;
  ZafVenCon objVenConFac;
  
  java.sql.Connection CONN_GLO=null;
  java.sql.Statement  STM_GLO=null;
  java.sql.ResultSet  rstCab=null;

  String strVersion=" v 0.12";
  String strCodTipDoc="";
  String strDesCodTipDoc="";
  String strDesLarTipDoc="";
  String strCodTipDocCon="";
  String strDesCorTipDocCon="";
  String strDesLarTipDocCon="";
  String strSqlTipDocAux="";

  String strCodCtaDeb="";
  String strTxtCodCtaDeb="";
  String strNomCtaDeb="";
  String strCodCtaHab="";
  String strTxtCodCtaHab="";
  String strNomCtaHab="";
  StringBuffer strSqlInsDet;
  String strFormatoFecha="d/m/y";
  StringBuffer stbFacSel;
//  String DIRECCION_REPORTE="C://zafiro//reportes_impresos//ZafCxC46/ZafCxC46.jrxml";

  String strCodCtaCli="";
  String strTxtCodCtaCli="";
  String strNomCtaCli="";
  String strCodCtaEfe="";
  String strTxtCodCtaEfe="";
  String strNomCtaEfe="";
  String strIpImpGuia="";


  final int INT_TBL_LINEA=0;  // NUMERO DE LINEAS
  final int INT_TBL_BUTCLI=1; // BUTON PARA BUSCAR DOCUMENTO
  final int INT_TBL_CHKSEL=2; // SELECCION  DE FILA
  final int INT_TBL_CODCLI=3; // CODIGO CLIENTE
  final int INT_TBL_NOMCLI=4; // NOMBRE CLIENTE
  final int INT_TBL_CODEMP=5; // CODIGO EMPRESA
  final int INT_TBL_CODLOC=6; // CODIGO DEL LOCAL
  final int INT_TBL_CODTID=7; // CODIGO TIPO DE DOCUMENTO
  final int INT_TBL_CODDOC=8; // CODIGO DOCUMENTAL
  final int INT_TBL_CODREG=9; // CODIGO REGISTRO
  final int INT_TBL_DCTIPDOC=10; // DESCRIPCION CORTA TIPO DOCUMENTO
  final int INT_TBL_DLTIPDOC=11; // DESCRIPCION LARGA TIPO DOCUMENTO
  final int INT_TBL_NUMDOC=12; // NUMERO DOCUMENTO
  final int INT_TBL_FECDOC=13; // FECHA DOCUMENTO
  final int INT_TBL_DIACRE=14; // DIA DE CREDITO
  final int INT_TBL_FECVEN=15; // FECHE VENCIMIENTO DOCUMENTO
  final int INT_TBL_PORRET=16; // PORCENTAJE DE RETENCION
  final int INT_TBL_VALDOC=17; // VALOR DOCUMENTO
  final int INT_TBL_VALPEN=18; // VALOR PENDIENTE
  final int INT_TBL_ABONO=19; //  ABONO
  final int INT_TBL_CODREGEFE=20; // CODIGO REGISTRO DE PAGO
  final int INT_TBL_ABONOORI=21; //  ABONO ORIGEN
  final int INT_TBL_BUTFAC=22;  // MUESTRA EL DOCUMENTO FACTURA.

  //Constantes del ArrayList Elementos Eliminados
  final int INT_ARR_CODREG   = 0;

   int intPuertoImpGuia=0;
   int INT_ENV_REC_IMP_GUIA = 0;

  int intCodMnuDocIng=0;
  int intTipModDoc=0;

  double dblMinAjuCenAut=0;
  double dblMaxAjuCenAut=0;

  javax.swing.JTextField txtCodTipDoc= new javax.swing.JTextField();

  Vector vecCab=new Vector();    //Almacena las cabeceras  /**/
  java.util.Vector vecDetDiario;
  
  boolean blnHayCam=false;
  
  
    String strCodEmp="";
    String strCodLoc="";
    String strCodTip="";
    String strCodDoc="";
    boolean blnEstCar=false;
    
  Vector vecOD = new Vector();
  
  double dblBefModTot=0.0;
  double dblAftModTot=0.0;

    /** Creates new form ZafCxC01 */
    public ZafCxC46(Librerias.ZafParSis.ZafParSis obj) {
        try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();

            this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
            lblTit.setText(objZafParSis.getNombreMenu());  /**/
   
	     objUti = new ZafUtil(); /**/
	     objTooBar = new mitoolbar(this);
	     this.getContentPane().add(objTooBar,"South");


             objLiberaGuia = new ZafCxC46_02(this, objZafParSis);

             objTooBar.agregarSeparador();
             objTooBar.agregarBoton(butCalCam);

              objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

             objAjuCenAut = new  Librerias.ZafAjuCenAut.ZafAjuCenAut(this, objZafParSis);
             
             objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia(this.objZafParSis);
             objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
             public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                 if (txtCodTipDoc.getText().equals(""))
                     objAsiDia.setCodigoTipoDocumento(-1);
                 else
                    objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
              }
            });

            cargarIpPuertoGuiaEmp();

             /*
           * Aqui se verifica si estoy en linux
           */
//           if(System.getProperty("os.name").equals("Linux")){
//               DIRECCION_REPORTE="//zafiro//reportes_impresos//ZafCxC46/ZafCxC46.jrxml";
//           }

            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);


           /*
           * Aqui se verifica si estoy en linux
           */
            

             txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
             txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
             txtFecDoc.setText("");
             panCabCob.add(txtFecDoc);
             txtFecDoc.setBounds(580, 4, 92, 20);

	 }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }
                      
                              
    public ZafCxC46(Librerias.ZafParSis.ZafParSis obj, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodMnu) {
        try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();

            this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
            lblTit.setText(objZafParSis.getNombreMenu());  /**/

            objZafParSis.setCodigoMenu(intCodMnu);

	     objUti = new ZafUtil(); /**/
	     objTooBar = new mitoolbar(this);
	     //this.getContentPane().add(objTooBar,"South");

              objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

             objAjuCenAut = new  Librerias.ZafAjuCenAut.ZafAjuCenAut(this, objZafParSis);

             strCodEmp= String.valueOf( intCodEmp );
             strCodLoc= String.valueOf( intCodLoc );
             strCodTip= String.valueOf( intCodTipDoc );
             strCodDoc= String.valueOf( intCodDoc );
             blnEstCar=true;

             objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia(this.objZafParSis);
             objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
             public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                 if (txtCodTipDoc.getText().equals(""))
                     objAsiDia.setCodigoTipoDocumento(-1);
                 else
                    objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
              }
            });
  
             /*
           * Aqui se verifica si estoy en linux
           */
//           if(System.getProperty("os.name").equals("Linux")){
//               DIRECCION_REPORTE="//zafiro//reportes_impresos//ZafCxC46/ZafCxC46.jrxml";
//           }

            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
   
           cargarIpPuertoGuiaEmp();
           /*
           * Aqui se verifica si estoy en linux
           */


             txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
             txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
             txtFecDoc.setText("");
             panCabCob.add(txtFecDoc);
             txtFecDoc.setBounds(580, 4, 92, 20);

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



public void Configura_ventana_consulta(){

    configurarVenConTipDoc();
    
    configurarVenConDocAbi();
   
    cargarRangoAjuCenAut();

    configurarVenConFacturas();

    ConfigurarTabla();
 

     if(blnEstCar)
        cargarDatos( strCodEmp, strCodLoc, strCodTip, strCodDoc );


}

private boolean cargarDatos(String intCodEmp, String intCodLoc, String intCodTipDoc, String strCodDoc ){
  boolean blnRes=true;
  java.sql.Connection conn;
  try{
     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){

       cargarCabReg(conn, intCodEmp, intCodLoc, intCodTipDoc, strCodDoc );
       cargarDetReg(conn, intCodEmp, intCodLoc, intCodTipDoc, strCodDoc );

      conn.close();
      conn=null;
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

private boolean cargarCabReg(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc){
    boolean blnRes=false;
    java.sql.Statement stmLoc, stmLoc01;
    java.sql.ResultSet rstLoc01, rstLoc02;
    String strSql="",strAux="";
    try{
      if(conn!=null){
        stmLoc=conn.createStatement();
        stmLoc01=conn.createStatement();

       strSql="SELECT a.st_reg, a.co_tipdoc, a1.tx_descor, a1.tx_deslar, a.co_doc, a.fe_doc, a.ne_numdoc1, a.ne_numdoc2, a.nd_mondoc, " +
       " a.tx_obs1, a.tx_obs2  from tbm_cabpag as a " +
       " INNER JOIN tbm_cabtipdoc as a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc )  " +
       " WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" " +
       " and a.co_tipdoc="+strCodTipDoc+"  and a.co_Doc="+strCodDoc+" ";
       rstLoc02=stmLoc.executeQuery(strSql);
       if(rstLoc02.next()){

        txtCodTipDoc.setText( rstLoc02.getString("co_tipdoc"));
        txtDesCodTitpDoc.setText( rstLoc02.getString("tx_descor"));
        txtDesLarTipDoc.setText( rstLoc02.getString("tx_deslar"));
        txtCodDoc.setText( rstLoc02.getString("co_doc"));
        txtAlt1.setText( rstLoc02.getString("ne_numdoc1"));
       
        valDoc.setText(""+ objUti.redondear( rstLoc02.getString("nd_mondoc"), 2) );
        txaObs1.setText(rstLoc02.getString("tx_obs1"));
        txaObs2.setText(rstLoc02.getString("tx_obs2"));

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

       objAsiDia.consultarDiario(Integer.parseInt(strCodEmp), Integer.parseInt(strCodLoc), Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc) );


//       cargarTiPDocCon( conn, Integer.parseInt(strCodEmp), Integer.parseInt(strCodLoc), Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc) );


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
     stmLoc01.close();
     stmLoc01=null;

}}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
 catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

return blnRes;
}

private boolean cargarDetReg(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc   ){
    boolean blnRes=false;
    java.sql.Statement stmLoc, stmLoc01;
    java.sql.ResultSet rstLoc;
    String strSql="";
    Vector vecData;
    try{
      if(conn!=null){
        stmLoc=conn.createStatement();
        stmLoc01=conn.createStatement();

       vecData = new Vector();

         String sqlAuxDif="";
         if(objZafParSis.getCodigoMenu()==1648) sqlAuxDif=" , ( a1.mo_pag + a1.nd_abo  ) as dif "; //retencion
         if(objZafParSis.getCodigoMenu()==256) sqlAuxDif=" , ( a1.mo_pag + a1.nd_abo  ) as dif ";  // (abs(a1.nd_abo)-abs(a1.mo_pag) ) as dif ";
         if(objZafParSis.getCodigoMenu()==488) sqlAuxDif=" , ( a1.mo_pag + a1.nd_abo  ) as dif ";

        strSql="SELECT a2.tx_numautsri, a2.tx_secdoc, a2.tx_feccad, a2.tx_codsri, " +
        " a1.tx_numctachq, a1.nd_monchq, a1.co_banchq, ban.tx_deslar as dlbanco,  a1.fe_venchq, a1.tx_numchq, a.co_reg as coregpag, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg "+sqlAuxDif+"  " +
        " ,a2.co_cli, a2.tx_nomcli, a3.tx_descor, a3.tx_deslar, a2.ne_numdoc, a2.fe_doc, a1.ne_diacre, a1.nd_porret, a1.fe_ven, a1.mo_pag, a.nd_abo " +
        " ,a.co_tipdoccon, a4.tx_descor as txdctipdoc, a4.tx_deslar as txdltipdoc " +
        " ,a4.co_ctadeb, a5.tx_codcta AS txctadeb, a5.tx_deslar as nomctadeb,  a4.co_ctahab, a6.tx_codcta as txctahab, a6.tx_deslar as nomctahab       " +
        " "+
        " FROM tbm_detpag as a " +
        " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locpag and a1.co_tipdoc=a.co_tipdocpag and a1.co_doc=a.co_docpag and a1.co_reg=a.co_regpag ) " +
        " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) " +
        " inner join tbm_cabtipdoc as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc  ) " +
        " " +
        "  inner join tbm_cabtipdoc as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoccon  ) " +
        "  inner join tbm_placta as a5 on (a5.co_emp=a4.co_emp and a5.co_cta=a4.co_ctadeb ) " +
        "  inner join tbm_placta as a6 on (a6.co_emp=a4.co_emp and a6.co_cta=a4.co_ctahab ) " +
        "  LEFT  JOIN tbm_var as ban ON (ban.co_reg=a.co_banchq ) " +
        " WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" " +
        " and a.co_tipdoc="+strCodTipDoc+"  and a.co_Doc="+strCodDoc+" and a.st_reg='A' "+
        " ORDER BY a.co_reg ";
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

           java.util.Vector vecReg = new java.util.Vector();
             vecReg.add(INT_TBL_LINEA,"");
             vecReg.add(INT_TBL_BUTCLI,"..");
             vecReg.add(INT_TBL_CHKSEL, new Boolean(true) );
             vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
             vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nomcli") );
             vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp") );
             vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
             vecReg.add(INT_TBL_CODTID, rstLoc.getString("co_tipdoc") );
             vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
             vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg") );
             vecReg.add(INT_TBL_DCTIPDOC, rstLoc.getString("tx_descor") );
             vecReg.add(INT_TBL_DLTIPDOC, rstLoc.getString("tx_deslar") );
             vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc") );
             vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc") );
             vecReg.add(INT_TBL_DIACRE, rstLoc.getString("ne_diacre") );
             vecReg.add(INT_TBL_FECVEN, rstLoc.getString("fe_ven") );
             vecReg.add(INT_TBL_PORRET, rstLoc.getString("nd_porret") );
             vecReg.add(INT_TBL_VALDOC, rstLoc.getString("mo_pag") );
             vecReg.add(INT_TBL_VALPEN, rstLoc.getString("dif") );
             vecReg.add(INT_TBL_ABONO,  rstLoc.getString("nd_abo") );
             vecReg.add(INT_TBL_CODREGEFE,  rstLoc.getString("coregpag") );
             vecReg.add(INT_TBL_ABONOORI,  rstLoc.getString("nd_abo") );
             vecReg.add( INT_TBL_BUTFAC, "..");
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

//
//private void cargarTiPDocCon(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
//   java.sql.Statement stmLoc;
//   java.sql.ResultSet rstLoc;
//   String strSql="";
//   try{
//      if(conn!=null){
//        stmLoc=conn.createStatement();
//
//       strSql="SELECT a.co_tipdoccon, a1.tx_descor, a1.tx_deslar " +
//       " ,a1.co_ctadeb, a3.tx_codcta AS txctadeb, a3.tx_deslar as nomctadeb,  a1.co_ctahab, a4.tx_codcta as txctahab, a4.tx_deslar as nomctahab  " +
//       " ,( select count(distinct(x.co_tipdoccon)) from tbm_detpag as x where x.co_emp = "+intCodEmp+"  and x.co_loc= "+intCodLoc+" and x.co_tipdoc="+intCodTipDoc+" and x.co_doc="+intCodDoc+" and x.st_reg='A'  ) as cantipdoccon " +
//       " FROM tbm_detpag as a " +
//       " inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoccon ) " +
//       " LEFT JOIN tbm_placta AS a3 on (a3.co_emp = a1.co_emp and a3.co_cta = a1.co_ctadeb )  " +
//       " LEFT JOIN tbm_placta AS a4 on (a4.co_emp = a1.co_emp and a4.co_cta = a1.co_ctahab ) " +
//       " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.st_reg='A' " +
//       " GROUP BY a.co_tipdoccon, a1.tx_descor, a1.tx_deslar " +
//       "  ,a1.co_ctadeb, a3.tx_codcta , a3.tx_deslar ,  a1.co_ctahab, a4.tx_codcta , a4.tx_deslar   ";
//       rstLoc=stmLoc.executeQuery(strSql);
//       if(rstLoc.next()){
//
//          if( rstLoc.getInt("cantipdoccon") == 1 ){
//
//              txtCodTipDocCon.setText(rstLoc.getString("co_tipdoccon"));
//
//
//              strCodCtaDeb=rstLoc.getString("co_ctadeb");
//              strTxtCodCtaDeb=rstLoc.getString("txctadeb");
//              strNomCtaDeb=rstLoc.getString("nomctadeb");
//              strCodCtaHab=rstLoc.getString("co_ctahab");
//              strTxtCodCtaHab=rstLoc.getString("txctahab");
//              strNomCtaHab=rstLoc.getString("nomctahab");
//
//
//
//          }else{
//
//
//
//
//             txtCodTipDocCon.setText("");
//
//             strCodCtaDeb="";
//             strTxtCodCtaDeb="";
//             strNomCtaDeb="";
//             strCodCtaHab="";
//             strTxtCodCtaHab="";
//             strNomCtaHab="";
//
//          }
//
//       }
//       rstLoc.close();
//       rstLoc=null;
//       stmLoc.close();
//       stmLoc=null;
//
// }}catch(java.sql.SQLException Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  }
//   catch(Exception Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  }
//}





private boolean configurarVenConFacturas() {
  boolean blnRes=true;
   try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("a.co_cli");
        arlCam.add("a.tx_nomcli");
        arlCam.add("a.co_emp");
        arlCam.add("a.co_loc");
        arlCam.add("a.co_tipdoc");
        arlCam.add("a.tx_desCor");
        arlCam.add("a.tx_desLar");
        arlCam.add("a.co_doc");
        arlCam.add("a.co_reg");
        arlCam.add("a.ne_numDoc");
        arlCam.add("a.fe_doc");
        arlCam.add("a.ne_diaCre");
        arlCam.add("a.fe_ven");
        arlCam.add("a.nd_porRet");
        arlCam.add("a.mo_pag");
        arlCam.add("a.nd_abo");
        arlCam.add("a.nd_pen");
        arlCam.add("a.st_sop");
        arlCam.add("a.st_entSop");
        arlCam.add("a.st_pos");
        arlCam.add("a.co_banChq");
        arlCam.add("a.a4_tx_desLar");
        arlCam.add("a.tx_numCtaChq");
        arlCam.add("a.tx_numChq");
        arlCam.add("a.fe_recChq");
        arlCam.add("a.fe_venChq");
        arlCam.add("a.nd_monChq");
  
        ArrayList arlAli=new ArrayList();
        arlAli.add("Cód.Cli");
        arlAli.add("Nom.Cli");
        arlAli.add("Cód.Emp");
        arlAli.add("Cód.Loc");
        arlAli.add("Cód.TipDoc");
        arlAli.add("DesCor");
        arlAli.add("tx_desLar");
        arlAli.add("co_doc");
        arlAli.add("co_reg");
        arlAli.add("ne_numDoc");
        arlAli.add("fe_doc");
        arlAli.add("ne_diaCre");
        arlAli.add("fe_ven");
        arlAli.add("nd_porRet");
        arlAli.add("mo_pag");
        arlAli.add("nd_abo");
        arlAli.add("nd_pen");
        arlAli.add("st_sop");
        arlAli.add("st_entSop");
        arlAli.add("st_pos");
        arlAli.add("co_banChq");
        arlAli.add("a4_tx_desLar");
        arlAli.add("tx_numCtaChq");
        arlAli.add("tx_numChq");
        arlAli.add("fe_recChq");
        arlAli.add("fe_venChq");
        arlAli.add("nd_monChq");


        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("55");
        arlAncCol.add("150");
        arlAncCol.add("60");
        arlAncCol.add("50");
        arlAncCol.add("10");
        arlAncCol.add("70");
        arlAncCol.add("20");
        arlAncCol.add("20");
        arlAncCol.add("20");
        arlAncCol.add("75");
        arlAncCol.add("80");
        arlAncCol.add("20");
        arlAncCol.add("20");
        arlAncCol.add("60");
        arlAncCol.add("20");
        arlAncCol.add("20");
        arlAncCol.add("20");
        arlAncCol.add("20");
        arlAncCol.add("20");
        arlAncCol.add("20");
        arlAncCol.add("20");
        arlAncCol.add("20");
        arlAncCol.add("20");
        arlAncCol.add("20");
        arlAncCol.add("20");
        arlAncCol.add("20");
        arlAncCol.add("20");



        int intColOcu[] =new int[20];
        intColOcu[0]=3;
        intColOcu[1]=5;
        intColOcu[2]=7;
        intColOcu[3]=8;
        intColOcu[4]=9;
        intColOcu[5]=13;
        intColOcu[6]=15;
        intColOcu[7]=16;
        intColOcu[8]=17;
        intColOcu[9]=18;
        intColOcu[10]=19;
        intColOcu[11]=20;
        intColOcu[12]=21;
        intColOcu[13]=22;
        intColOcu[14]=23;
        intColOcu[15]=24;
        intColOcu[16]=25;
        intColOcu[17]=26;
        intColOcu[18]=27;
        intColOcu[19]=12;


        //Armar la sentencia SQL.
        String  strSQL;

        String strAux="";
        if(!(objZafParSis.getCodigoUsuario()==1)){

          if(objZafParSis.getCodigoUsuario()==89){
              if(objZafParSis.getCodigoEmpresa()==1) strAux=" and a1.co_loc in ("+objZafParSis.getCodigoLocal()+",5) ";
              if(objZafParSis.getCodigoEmpresa()==2) strAux=" and a1.co_loc in ("+objZafParSis.getCodigoLocal()+",5) ";
              if(objZafParSis.getCodigoEmpresa()==4) strAux=" and a1.co_loc in ("+objZafParSis.getCodigoLocal()+",2) ";
          }
          else strAux=" and a1.co_loc="+objZafParSis.getCodigoLocal()+" ";
          
          
        }

        String strAuxFil="";
        strAuxFil=" AND a3.ne_mod IN (1, 3)  AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0) ";
      

        strSQL="SELECT * FROM ( SELECT  a1.co_cli, a1.tx_nomcli, a1.co_emp, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, a2.mo_pag, a2.nd_abo " +
        " , (a2.mo_pag+a2.nd_abo) AS nd_pen, a2.st_sop, a2.st_entSop, a2.st_pos, a2.co_banChq, a4.tx_desLar AS a4_tx_desLar, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq " +
        " , a2.nd_monChq " +
        " FROM  tbm_cabMovInv AS a1  " +
        " INNER JOIN tbm_cli AS b1 ON (a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli) " +
        " INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) " +
        " INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)  " +
        " LEFT OUTER JOIN tbm_var AS a4 ON (a2.co_banChq=a4.co_reg) " +
        " WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+"   "+strAux+" "+
        " AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C')  "+strAuxFil+" " +
        " AND ( a2.tx_numchq IS NULL OR a2.tx_numchq='')   AND (a2.mo_pag+a2.nd_abo)<>0 " +
        " ) AS a ORDER BY co_emp, co_loc, co_tipDoc, co_doc, co_reg ";

        objVenConFac=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol, intColOcu );
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

        objVenConFac.setConfiguracionColumna(14, javax.swing.JLabel.RIGHT, objVenConFac.INT_FOR_NUM, objZafParSis.getFormatoNumero(),false,true);
                
 
    }
    catch (Exception e) {
        blnRes=false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    return blnRes;
}

      
    
private boolean configurarVenConDocAbi(){
 boolean blnRes=true;
 try{
    objVenConCxC01=new ZafVenConCxC01(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de documentos abiertos");

    objVenConCxC01.setTipoConsulta(1);
    objVenConCxC01.setCheckedMostrarSoloDocumentosContado(true);
    objVenConCxC01.setCheckedMostrarRetenciones(false);
      
  }catch (Exception e){   blnRes=false;   objUti.mostrarMsgErr_F1(this, e); }
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
         strEstApl = (tblDat.getValueAt(intFil, INT_TBL_CODREGEFE)==null?"N":(tblDat.getValueAt(intFil, INT_TBL_CODREGEFE).equals("")?"N":tblDat.getValueAt(intFil, INT_TBL_CODREGEFE).toString()));
         if(!(strEstApl.equals("N"))){
             blnRes=false;
             break;
         }
   }
   intFilSel=null;

   }catch(Exception e) { blnRes=false;  objUti.mostrarMsgErr_F1(this,e);  }
  return blnRes;
}





private boolean ConfigurarTabla() {
 boolean blnRes=false;
 try{
     //Configurar JTable: Establecer el modelo.
        vecCab.clear();
        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_BUTCLI,"");
        vecCab.add(INT_TBL_CHKSEL,"");
        vecCab.add(INT_TBL_CODCLI,"Cód.Cli");
        vecCab.add(INT_TBL_NOMCLI,"Nom.CLi");
        vecCab.add(INT_TBL_CODEMP,"Cod.Emp");
        vecCab.add(INT_TBL_CODLOC,"Cód.Loc");
        vecCab.add(INT_TBL_CODTID,"Cód.Tip.Doc");
        vecCab.add(INT_TBL_CODDOC,"Cod.Doc");
        vecCab.add(INT_TBL_CODREG,"Cod.Reg");
        vecCab.add(INT_TBL_DCTIPDOC,"Des.Cor");
        vecCab.add(INT_TBL_DLTIPDOC,"Des.Lar");
        vecCab.add(INT_TBL_NUMDOC,"Num.Doc");
        vecCab.add(INT_TBL_FECDOC,"Fec.Doc");
        vecCab.add(INT_TBL_DIACRE,"Dia.Cre");
        vecCab.add(INT_TBL_FECVEN,"Fec.ven");
        vecCab.add(INT_TBL_PORRET,"Por.ret");
        vecCab.add(INT_TBL_VALDOC,"Val.Doc");
        vecCab.add(INT_TBL_VALPEN,"Val.Pen");
        vecCab.add(INT_TBL_ABONO,"Abono");
        vecCab.add(INT_TBL_CODREGEFE,"");
        vecCab.add(INT_TBL_ABONOORI,"");
        vecCab.add( INT_TBL_BUTFAC, "..");

	objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);

        //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
        objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());
        tblDat.getTableHeader().setReorderingAllowed(false);

	//Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();  /**/

        objTblMod.setColumnDataType(INT_TBL_DIACRE, objTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_PORRET, objTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_VALDOC, objTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_VALPEN, objTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_ABONO, objTblMod.INT_COL_DBL, new Integer(0), null);


        objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_DIACRE).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_PORRET).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_VALDOC).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_VALPEN).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;

        objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_ABONO).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;

         //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        ZafMouMotAda objMouMotAda=new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

         //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_BUTCLI).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_CHKSEL).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(40);
        tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_DCTIPDOC).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DLTIPDOC).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DIACRE).setPreferredWidth(35);
        tcmAux.getColumn(INT_TBL_FECVEN).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_PORRET).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_VALDOC).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_VALPEN).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_ABONO).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_BUTFAC).setPreferredWidth(20);
       

        /* Aqui se agrega las columnas que van
             ha hacer ocultas
         * */
        ArrayList arlColHid=new ArrayList();
        arlColHid.add(""+INT_TBL_CODEMP);
        arlColHid.add(""+INT_TBL_CODTID);
        arlColHid.add(""+INT_TBL_CODDOC);
        arlColHid.add(""+INT_TBL_CODREG);
        arlColHid.add(""+INT_TBL_CODREGEFE);
        arlColHid.add(""+INT_TBL_ABONOORI);
//        arlColHid.add(""+INT_TBL_PORRET);
        objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;

        
          //Configurar ZafTblMod: Establecer las columnas ELIMINADAS
          java.util.ArrayList  arlAux=new java.util.ArrayList();
          arlAux.add("" + INT_TBL_CODREGEFE);
          objTblMod.setColsSaveBeforeRemoveRow(arlAux);
          arlAux=null;



        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_CHKSEL);
        vecAux.add("" + INT_TBL_BUTCLI);
        vecAux.add("" + INT_TBL_ABONO);
        vecAux.add("" + INT_TBL_NUMDOC);
        vecAux.add("" + INT_TBL_BUTFAC);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;

        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);



        objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_CHKSEL).setCellRenderer(objTblCelRenChk);
        objTblCelRenChk=null;


        objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_CHKSEL).setCellEditor(objTblCelEdiChk);
        objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                int intNumFil = tblDat.getSelectedRow();

                String strCodCli=  (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
               if((strCodCli.equals("")))
                   objTblCelEdiChk.setCancelarEdicion(true);

               if( !(tblDat.getValueAt(intNumFil, INT_TBL_CODREGEFE)==null))
                   objTblCelEdiChk.setCancelarEdicion(true);

            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
              int intNunFil = tblDat.getSelectedRow();
              if(tblDat.getValueAt(intNunFil, INT_TBL_CHKSEL).toString().equals("true")){

                if( tblDat.getValueAt(intNunFil, INT_TBL_CODREGEFE)==null){
                  double dblValPen = objUti.redondear( (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString())), 2);
                  dblValPen = Math.abs(dblValPen);

                  tblDat.setValueAt(""+dblValPen, intNunFil, INT_TBL_ABONO);
                 
                }else{
                    tblDat.setValueAt( tblDat.getValueAt(intNunFil, INT_TBL_ABONOORI), intNunFil, INT_TBL_ABONO);
                }
               }else{
                    tblDat.setValueAt("0", intNunFil, INT_TBL_ABONO);
                    
               }

              calculaTotMonAbo();
            }
        });


    objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
    tcmAux.getColumn(INT_TBL_ABONO).setCellEditor(objTblCelEdiTxt);
    objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
           int intNumFil = tblDat.getSelectedRow();

           String strCodCli=  (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
           if((strCodCli.equals("")))
               objTblCelEdiTxt.setCancelarEdicion(true);

           if( !(tblDat.getValueAt(intNumFil, INT_TBL_CODREGEFE)==null))
               objTblCelEdiTxt.setCancelarEdicion(true);

        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
          int intNunFil = tblDat.getSelectedRow();
          
           Pattern pattern=Pattern.compile("^\\d+(?:\\.\\d{1,2})?$");
           Matcher matcher=pattern.matcher(objTblCelEdiTxt.getText());
           if(!matcher.find()){
               JOptionPane.showMessageDialog(null, "La cantidad solo puede ser de 2 decimales ");
               tblDat.setValueAt( new Boolean(false), intNunFil, INT_TBL_CHKSEL );
               tblDat.setValueAt( 0, intNunFil, INT_TBL_ABONO );
           }else{


            double dblValApl = objUti.redondear( (tblDat.getValueAt(intNunFil, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_ABONO).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_ABONO).toString())), 4);
            double dblValPen = objUti.redondear( (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString())), 4);

            dblValPen = Math.abs(dblValPen);

//            if(dblValApl > dblValPen){
//                MensajeInf("EL VALOR QUE ESTA ABONANDO ES MAYOR AL VALOR PENDIENTE..");
//                tblDat.setValueAt("0", intNunFil, INT_TBL_ABONO );
//            }

                if(dblValApl != 0 ) tblDat.setValueAt( new Boolean(true), intNunFil, INT_TBL_CHKSEL );
                else tblDat.setValueAt( new Boolean(false), intNunFil, INT_TBL_CHKSEL );

            

//               double dblDif=objUti.redondear((dblValPen-dblValApl), 4);
//               if( !(( dblDif >= dblMinAjuCenAut ) && ( dblDif <= dblMaxAjuCenAut ))   ){
//                   MensajeInf("EL VALOR APLICADO ESTA FUERA DE RANGO DE AJUSTE DE CENTAVOS.");
//                   tblDat.setValueAt("", intNunFil, INT_TBL_ABONO );
//                   tblDat.setValueAt( new Boolean(false), intNunFil, INT_TBL_CHKSEL );
//               }else{
//                  if(dblValApl != 0 ) tblDat.setValueAt( new Boolean(true), intNunFil, INT_TBL_CHKSEL );
//                  else tblDat.setValueAt( new Boolean(false), intNunFil, INT_TBL_CHKSEL );
//               }




           calculaTotMonAbo();
           }
         }
    });





        int intColFac[]=new int[16];
        intColFac[0]=1;
        intColFac[1]=2;
        intColFac[2]=3;
        intColFac[3]=4;
        intColFac[4]=5;
        intColFac[5]=8;
        intColFac[6]=9;
        intColFac[7]=11;
        intColFac[8]=10;
        intColFac[9]=6;
        intColFac[10]=7;
        intColFac[11]=12;
        intColFac[12]=14;
        intColFac[13]=13;
        intColFac[14]=15;
        intColFac[15]=17;
       // intColFac[16]=16;
       

        int intColTblFac[]=new int[16];
        intColTblFac[0]=INT_TBL_CODCLI;
        intColTblFac[1]=INT_TBL_NOMCLI;
        intColTblFac[2]=INT_TBL_CODEMP;
        intColTblFac[3]=INT_TBL_CODLOC;
        intColTblFac[4]=INT_TBL_CODTID;
        intColTblFac[5]=INT_TBL_CODDOC;
        intColTblFac[6]=INT_TBL_CODREG;
        intColTblFac[7]=INT_TBL_FECDOC;
        intColTblFac[8]=INT_TBL_NUMDOC;
        intColTblFac[9]=INT_TBL_DCTIPDOC;
        intColTblFac[10]=INT_TBL_DLTIPDOC;
        intColTblFac[11]=INT_TBL_DIACRE;
        intColTblFac[12]=INT_TBL_PORRET;
        intColTblFac[13]=INT_TBL_FECVEN;
        intColTblFac[14]=INT_TBL_VALDOC;
        intColTblFac[15]=INT_TBL_VALPEN;
       // intColTblFac[16]=INT_TBL_ABONO;
        

        objTblCelEdiTxtVcoFacRet=new ZafTblCelEdiTxtVco(tblDat, objVenConFac, intColFac, intColTblFac );
        tcmAux.getColumn(INT_TBL_NUMDOC).setCellEditor(objTblCelEdiTxtVcoFacRet);
        objTblCelEdiTxtVcoFacRet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
               int intNumFil = tblDat.getSelectedRow();

               System.out.println("--> 1 ");
               
               if( !(tblDat.getValueAt(intNumFil, INT_TBL_CODREGEFE)==null))
                 objTblCelEdiTxtVcoFacRet.setCancelarEdicion(true);
            }
            public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                 System.out.println("--> 2 ");

                objVenConFac.setCampoBusqueda(9);
                objVenConFac.setCriterio1(11);
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow();

                 System.out.println("--> 3 ");

                 if( tblDat.getValueAt( intNumFil, INT_TBL_CODLOC)==null){
                     objTblCelEdiTxtVcoFacRet.setCancelarEdicion(true);
                 }else{

                  if(!_getVerificaExistenciaVenCon( intNumFil,  tblDat.getValueAt( intNumFil, INT_TBL_CODLOC).toString(),
                        tblDat.getValueAt( intNumFil, INT_TBL_CODTID).toString(),
                        tblDat.getValueAt( intNumFil, INT_TBL_CODDOC).toString(),
                        tblDat.getValueAt( intNumFil, INT_TBL_CODREG).toString() ) ){
                            //System.out.println("Existe.......");
                            objTblMod.removeRow(intNumFil);
                        }
                 }

//                double dblValAbo = objUti.redondear( (tblDat.getValueAt(intNumFil, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(intNumFil, INT_TBL_ABONO).equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_ABONO).toString())), 4);
//                if(dblValAbo != 0){
//
//                     calculaTotMonAbo();
//                }

            }
        });



        objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBL_BUTCLI).setCellRenderer(objTblCelRenBut);
        objTblCelRenBut=null;

     
          //------------------------------------------------------------------
    //Eddye_ventana de documentos pendientes//
    objTblCelEdiButGen=new ZafTblCelEdiButGen();
    tblDat.getColumnModel().getColumn(INT_TBL_BUTCLI).setCellEditor(objTblCelEdiButGen);
    objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            
         if( (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODREGEFE)==null)){

            int intFilSel, intFilSelVenCon[], i, j;
            String strCodCli, strNomCli, strValDoc;
            double dblValDoc=0;
            if (tblDat.getSelectedColumn()==INT_TBL_BUTCLI)
            {
                objVenConCxC01.setVisible(true);
            }
            if (objVenConCxC01.getSelectedButton()==objVenConCxC01.INT_BUT_ACE)
            {
                intFilSel=tblDat.getSelectedRow();
                intFilSelVenCon=objVenConCxC01.getFilasSeleccionadas();
                strCodCli=objVenConCxC01.getCodigoCliente();
                strNomCli=objVenConCxC01.getNombreCliente();
                j=intFilSel;
                for (i=0; i<intFilSelVenCon.length; i++)
                {
                    if (objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_LIN)!="P")
                    {
                        
                        
                        strValDoc=objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_VAL_CHQ);
                        strValDoc=(strValDoc==null?"0":(strValDoc.equals("")?"0":strValDoc));
                        dblValDoc=objUti.redondear(strValDoc, 4);


                        
                        

                        if(!_getVerificaExistencia(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_COD_LOC).toString(),
                        objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_COD_TIP_DOC).toString(),
                        objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_COD_DOC).toString(),
                        objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_COD_REG).toString() ) ){

                            System.out.println("Existe.......");

                        }else{
                      
                        objTblMod.insertRow(j);
                        objTblMod.setValueAt(strCodCli, j, INT_TBL_CODCLI);
                        objTblMod.setValueAt(strNomCli, j, INT_TBL_NOMCLI);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_COD_LOC), j, INT_TBL_CODLOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_COD_TIP_DOC), j, INT_TBL_CODTID);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_DEC_TIP_DOC), j, INT_TBL_DCTIPDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_DEL_TIP_DOC), j, INT_TBL_DLTIPDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_COD_DOC), j, INT_TBL_CODDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_COD_REG), j, INT_TBL_CODREG);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_NUM_DOC), j, INT_TBL_NUMDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_FEC_DOC), j, INT_TBL_FECDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_DIA_CRE), j, INT_TBL_DIACRE);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_FEC_VEN), j, INT_TBL_FECVEN);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_POR_RET), j, INT_TBL_PORRET);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_VAL_DOC), j, INT_TBL_VALDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_VAL_PEN), j, INT_TBL_VALPEN);
                        objTblMod.setValueAt( ""+objZafParSis.getCodigoEmpresa()  , j, INT_TBL_CODEMP );
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_VAL_CHQ), j, INT_TBL_ABONO);
                        objVenConCxC01.setFilaProcesada(intFilSelVenCon[i]);
                       
                        }

                        j++;
                    }
                }
                tblDat.requestFocus();
//                calculaTotMonAbo();
                
            }
        }}
    });
  //------------------------------------------------------------------


    Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButCotEmi=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
    tcmAux.getColumn(INT_TBL_BUTFAC).setCellRenderer(objTblCelRenButCotEmi);
    objTblCelRenButCotEmi=null;
    new ButFac(tblDat, INT_TBL_BUTFAC);   //*****



    setEditable(false);

//     new ZafTblOrd(tblDat);

   objTblPopMnu = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
   // objTblPopMnu.setEliminarFilaVisible(false);
    objTblPopMnu.setInsertarFilasVisible(false);
    objTblPopMnu.setInsertarFilaVisible(false);

    objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
        public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
           if(objTblPopMnu.isClickEliminarFila()){
            if(!(objZafParSis.getCodigoUsuario()==1)){
              if(!verificaSelEli()){
                 MensajeInf("NO SE PUEDE ELIMINAR. SOLO SE PUEDE ELIMINAR DATOS NUEVOS.  ");
                 objTblPopMnu.cancelarClick();
               }
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
                //javax.swing.JOptionPane.showMessageDialog(null, "Las filas se eliminaron con éxito.");
                calculaTotMonAbo();
            }
        }
    });
    tcmAux=null;
   

      blnRes=true;
   }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
    return blnRes;
}

public void setEditable(boolean editable) {
  if (editable==true){
    objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);

 }else{  objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI); }
}



private class ButFac extends Librerias.ZafTableColBut.ZafTableColBut_uni{
    public ButFac(javax.swing.JTable tbl, int intIdx){
        super(tbl,intIdx, "Factura Comercial.");
    }
    public void butCLick() {
       int intCol = tblDat.getSelectedRow();
       if( tblDat.getValueAt(intCol, INT_TBL_CODCLI)!= null ){
       String strCodEmp = tblDat.getValueAt(intCol, INT_TBL_CODEMP).toString();
       String strCodLoc = tblDat.getValueAt(intCol, INT_TBL_CODLOC).toString();
       String strCodTipDoc = tblDat.getValueAt(intCol, INT_TBL_CODTID).toString();
       String strCodDoc = tblDat.getValueAt(intCol, INT_TBL_CODDOC).toString();

       llamarVenFac(strCodEmp, strCodLoc, strCodTipDoc, strCodDoc  );
    }}
}

private void llamarVenFac(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){

   Ventas.ZafVen02.ZafVen02 obj1 = new Ventas.ZafVen02.ZafVen02( objZafParSis , strCodEmp , strCodLoc, strCodTipDoc, strCodDoc, 14 );
   this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER );
   obj1.show();

}



private boolean _getVerificaExistencia(String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodReg ){
 boolean blnRes=true;
  try{

    for(int i=0; i < tblDat.getRowCount(); i++){
     if( tblDat.getValueAt(i, INT_TBL_CODCLI)!= null ){
      if( tblDat.getValueAt(i, INT_TBL_CODLOC).toString().equals(strCodLoc) ){
       if( tblDat.getValueAt(i, INT_TBL_CODTID).toString().equals(strCodTipDoc) ){
        if( tblDat.getValueAt(i, INT_TBL_CODDOC).toString().equals(strCodDoc) ){
         if( tblDat.getValueAt(i, INT_TBL_CODREG).toString().equals(strCodReg) ){
               blnRes=false;
         }}}}
    }}

  }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
 return blnRes;
}



private boolean _getVerificaExistenciaVenCon(int intFilSel, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodReg ){
 boolean blnRes=true;
  try{

    for(int i=0; i < tblDat.getRowCount(); i++){
     if(intFilSel != i ){
     if( tblDat.getValueAt(i, INT_TBL_CODCLI)!= null ){
      if( tblDat.getValueAt(i, INT_TBL_CODLOC).toString().equals(strCodLoc) ){
       if( tblDat.getValueAt(i, INT_TBL_CODTID).toString().equals(strCodTipDoc) ){
        if( tblDat.getValueAt(i, INT_TBL_CODDOC).toString().equals(strCodDoc) ){
         if( tblDat.getValueAt(i, INT_TBL_CODREG).toString().equals(strCodReg) ){
               blnRes=false;
         }}}}
    }}}

  }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
 return blnRes;
}



/**
 * Calcula el monto total de los cheques ingresados
 */
public void calculaTotMonAbo(){
  double dblMonChq=0, dblValDoc=0;
  String strValDoc="";
  int intTipMov=0;
  try{
    for(int i=0; i<tblDat.getRowCount(); i++){
     if( tblDat.getValueAt(i, INT_TBL_CODCLI) != null ){
     
         System.out.println("-> "+tblDat.getValueAt(i, INT_TBL_CODCLI) );

          strValDoc=(tblDat.getValueAt(i, INT_TBL_VALDOC)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALDOC).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALDOC).toString()));

          dblValDoc=objUti.redondear(strValDoc, 4);
          if(dblValDoc > 0 ){
            intTipMov=-1;
          }else{
              intTipMov=1;
          }

          dblMonChq += Math.abs( Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_ABONO)==null)?"0":(tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_ABONO).toString() )  )) ) * intTipMov;

    }
    }
   dblMonChq=objUti.redondear(dblMonChq, 2);
   valDoc.setText(""+dblMonChq);

   if(!(strCodCtaCli.equals("")))
     generaAsiento(dblMonChq);
   else generaAsiento();

 }catch(Exception e) {   objUti.mostrarMsgErr_F1(this,e);  }
}




  


  
private boolean configurarVenConTipDoc() {
boolean blnRes=true;
try {
    ArrayList arlCam=new ArrayList();
    arlCam.add("a.co_tipdoc");
    arlCam.add("a.tx_descor");
    arlCam.add("a.tx_deslar");
    arlCam.add("a.ne_tipresmoddoc");

    ArrayList arlAli=new ArrayList();
    arlAli.add("Código");
    arlAli.add("Des.Cor.");
    arlAli.add("Des.Lar.");
    arlAli.add("Tip.ModDoc.");

    ArrayList arlAncCol=new ArrayList();
    arlAncCol.add("80");
    arlAncCol.add("110");
    arlAncCol.add("350");
    arlAncCol.add("20");

    //Armar la sentencia SQL.   a7.nd_stkTot,
    String Str_Sql="";

     if(objZafParSis.getCodigoUsuario()==1){
      Str_Sql="SELECT * FROM ( Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar, 3 as ne_tipresmoddoc  from tbr_tipdocprg as b " +
      " left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
      " where   b.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
      " b.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
      " b.co_mnu = " + objZafParSis.getCodigoMenu()+" ) AS a ";
     }else {
            Str_Sql ="SELECT * FROM ( SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar, a1.ne_tipresmoddoc  "+
            " FROM tbr_tipDocUsr AS a1 inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"+
            " WHERE "+
            "  a1.co_emp=" + objZafParSis.getCodigoEmpresa()+""+
            " AND a1.co_loc=" + objZafParSis.getCodigoLocal()+""+
            " AND a1.co_mnu=" + objZafParSis.getCodigoMenu()+""+
            " AND a1.co_usr=" + objZafParSis.getCodigoUsuario()+" ) AS a  ";
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



//private void generaAsiento(double dblValDoc){
//  try{
//     objAsiDia.inicializar();
//     int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null.
//     int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema).
//     int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso).
//     int INT_VEC_BOTON  = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null.
//     int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta.
//     int INT_VEC_DEBE   = 5; //5) Debe.
//     int INT_VEC_HABER  = 6; //6) Haber.
//     int INT_VEC_REF    = 7; //7) Referencia: Se debe asignar una cadena vacía o null
//     int INT_VEC_NUEVO    = 8; //7) Referencia: Se debe asignar una cadena vacía o null
//
//
//
//       if (vecDetDiario==null) vecDetDiario = new java.util.Vector();
//         else vecDetDiario.removeAllElements();
//
//
//      java.util.Vector vecReg;
//
//       vecReg = new java.util.Vector();
//       vecReg.add(INT_LINEA, null);
//       vecReg.add(INT_VEC_CODCTA, new Integer( strCodCtaDeb ) );
//       vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaDeb );
//       vecReg.add(INT_VEC_BOTON, null);
//       vecReg.add(INT_VEC_NOMCTA, strNomCtaDeb );
//      if(objZafParSis.getCodigoMenu()==488){
//       if(dblValDoc > 0 ){
//          vecReg.add(INT_VEC_DEBE,  new Double(0));
//          vecReg.add(INT_VEC_HABER, new Double(Math.abs( dblValDoc )));
//       }else{
//         vecReg.add(INT_VEC_DEBE,  new Double(Math.abs( dblValDoc )));
//         vecReg.add(INT_VEC_HABER, new Double(0));
//       }
//      }else{
//       if(dblValDoc > 0 ){
//          vecReg.add(INT_VEC_DEBE,  new Double(Math.abs( dblValDoc )));
//          vecReg.add(INT_VEC_HABER, new Double(0));
//       }else{
//         vecReg.add(INT_VEC_DEBE,  new Double(0));
//         vecReg.add(INT_VEC_HABER, new Double(Math.abs( dblValDoc )));
//       }
//      }
//       vecReg.add(INT_VEC_REF, null);
//       vecReg.add(INT_VEC_NUEVO, null);
//
//        vecDetDiario.add(vecReg);
//        vecReg = new java.util.Vector();
//        vecReg.add(INT_LINEA, null);
//        vecReg.add(INT_VEC_CODCTA,new Integer( strCodCtaHab ));
//        vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaHab );
//        vecReg.add(INT_VEC_BOTON, null);
//        vecReg.add(INT_VEC_NOMCTA, strNomCtaHab );
//       if(objZafParSis.getCodigoMenu()==488){
//        if(dblValDoc > 0 ){
//          vecReg.add(INT_VEC_DEBE,  new Double( Math.abs( dblValDoc ) ));
//          vecReg.add(INT_VEC_HABER, new Double(0));
//        }else{
//          vecReg.add(INT_VEC_DEBE,  new Double(0));
//          vecReg.add(INT_VEC_HABER, new Double( Math.abs( dblValDoc ) ));
//        }
//     }else{
//        if(dblValDoc > 0 ){
//          vecReg.add(INT_VEC_DEBE,  new Double( 0 ));
//          vecReg.add(INT_VEC_HABER, new Double(Math.abs( dblValDoc )));
//        }else{
//          vecReg.add(INT_VEC_DEBE,  new Double(Math.abs( dblValDoc )));
//          vecReg.add(INT_VEC_HABER, new Double( 0 ));
//        }
//       }
//        vecReg.add(INT_VEC_REF, null);
//        vecReg.add(INT_VEC_NUEVO, null);
//        vecDetDiario.add(vecReg);
//
//     objAsiDia.setDetalleDiario(vecDetDiario);
// }catch(Exception e) {   objUti.mostrarMsgErr_F1(this,e);  }
//}

//private void generaAsiento(){
//  try{
//     objAsiDia.inicializar();
//     int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null.
//     int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema).
//     int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso).
//     int INT_VEC_BOTON  = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null.
//     int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta.
//     int INT_VEC_DEBE   = 5; //5) Debe.
//     int INT_VEC_HABER  = 6; //6) Haber.
//     int INT_VEC_REF    = 7; //7) Referencia: Se debe asignar una cadena vacía o null
//     int INT_VEC_NUEVO  = 8; //7) Referencia: Se debe asignar una cadena vacía o null
//
//     double dblAbono=0, dblValDoc=0;
//     String strValDoc="";
//     int intTipMov=0;
//
//
//       if (vecDetDiario==null) vecDetDiario = new java.util.Vector();
//         else vecDetDiario.removeAllElements();
//
//
//    for(int i=0; i < tblDat.getRowCount(); i++){
//
//
//       dblAbono= objUti.redondear( (tblDat.getValueAt(i, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_ABONO).toString())), 2 );
//
//
//      strValDoc=(tblDat.getValueAt(i, INT_TBL_VALDOC)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALDOC).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALDOC).toString()));
//      dblValDoc=objUti.redondear(strValDoc, 4);
//      if(dblValDoc > 0 ){
//        intTipMov=-1;
//      }else{
//          intTipMov=1;
//      }
//
//      java.util.Vector vecReg;
//
//       vecReg = new java.util.Vector();
//       vecReg.add(INT_LINEA, null);
////       vecReg.add(INT_VEC_CODCTA, new Integer( tblDat.getValueAt(i, INT_TBL_CODCTADEB).toString() ) );
////       vecReg.add(INT_VEC_NUMCTA, tblDat.getValueAt(i, INT_TBL_DCOCTADEB ).toString() );
//       vecReg.add(INT_VEC_BOTON, null);
////       vecReg.add(INT_VEC_NOMCTA, tblDat.getValueAt(i, INT_TBL_NOMCTADEB).toString() );
//      if(objZafParSis.getCodigoMenu()==488){
//       if(intTipMov==1){
//        vecReg.add(INT_VEC_DEBE,  new Double(0));
//        vecReg.add(INT_VEC_HABER, new Double(Math.abs(dblAbono)));
//       }else{
//        vecReg.add(INT_VEC_DEBE,  new Double(Math.abs(dblAbono)));
//        vecReg.add(INT_VEC_HABER, new Double(0));
//       }
//      }else{
//       if(intTipMov==1){
//        vecReg.add(INT_VEC_DEBE,  new Double(Math.abs(dblAbono)));
//        vecReg.add(INT_VEC_HABER, new Double(0));
//       }else{
//        vecReg.add(INT_VEC_DEBE,  new Double(0));
//        vecReg.add(INT_VEC_HABER, new Double(Math.abs(dblAbono)));
//       }
//      }
//       vecReg.add(INT_VEC_REF, null);
//       vecReg.add(INT_VEC_NUEVO, null);
//
//        vecDetDiario.add(vecReg);
//        vecReg = new java.util.Vector();
//        vecReg.add(INT_LINEA, null);
////        vecReg.add(INT_VEC_CODCTA,new Integer( tblDat.getValueAt(i, INT_TBL_CODCTAHAB).toString() ));
////        vecReg.add(INT_VEC_NUMCTA, tblDat.getValueAt(i, INT_TBL_DCOCTAHAB).toString() );
//        vecReg.add(INT_VEC_BOTON, null);
////        vecReg.add(INT_VEC_NOMCTA, tblDat.getValueAt(i, INT_TBL_NOMCTAHAB).toString() );
//       if(objZafParSis.getCodigoMenu()==488){
//        if(intTipMov==1){
//         vecReg.add(INT_VEC_DEBE,  new Double( Math.abs(dblAbono) ));
//         vecReg.add(INT_VEC_HABER, new Double(0));
//        }else{
//         vecReg.add(INT_VEC_DEBE,  new Double(0));
//         vecReg.add(INT_VEC_HABER, new Double(Math.abs(dblAbono)));
//        }
//      }else{
//        if(intTipMov==1){
//         vecReg.add(INT_VEC_DEBE,  new Double( 0 ));
//         vecReg.add(INT_VEC_HABER, new Double(Math.abs(dblAbono)));
//        }else{
//         vecReg.add(INT_VEC_DEBE,  new Double(Math.abs(dblAbono)));
//         vecReg.add(INT_VEC_HABER, new Double(0));
//        }
//      }
//        vecReg.add(INT_VEC_REF, null);
//        vecReg.add(INT_VEC_NUEVO, null);
//        vecDetDiario.add(vecReg);
//
//    }
//
//     objAsiDia.setDetalleDiario(vecDetDiario);
// }catch(Exception e) {   objUti.mostrarMsgErr_F1(this,e);  }
//}


private void generaAsiento(){
  try{
     objAsiDia.inicializar();
     int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null.
     int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema).
     int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso).
     int INT_VEC_BOTON  = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null.
     int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta.
     int INT_VEC_DEBE   = 5; //5) Debe.
     int INT_VEC_HABER  = 6; //6) Haber.
     int INT_VEC_REF    = 7; //7) Referencia: Se debe asignar una cadena vacía o null
     int INT_VEC_NUEVO    = 8; //7) Referencia: Se debe asignar una cadena vacía o null

   
       if (vecDetDiario==null) vecDetDiario = new java.util.Vector();
         else vecDetDiario.removeAllElements();

        
      java.util.Vector vecReg;

       vecReg = new java.util.Vector();
       vecReg.add(INT_LINEA, null);
       vecReg.add(INT_VEC_CODCTA, new Integer( strCodCtaCli ) );
       vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaCli );
       vecReg.add(INT_VEC_BOTON, null);
       vecReg.add(INT_VEC_NOMCTA, strNomCtaCli );
       vecReg.add(INT_VEC_DEBE,  new Double(0));
       vecReg.add(INT_VEC_HABER, new Double(0));
       vecReg.add(INT_VEC_REF, null);
       vecReg.add(INT_VEC_NUEVO, null);

        vecDetDiario.add(vecReg);
        vecReg = new java.util.Vector();
        vecReg.add(INT_LINEA, null);
        vecReg.add(INT_VEC_CODCTA,new Integer( strCodCtaEfe ));
        vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaEfe );
        vecReg.add(INT_VEC_BOTON, null);
        vecReg.add(INT_VEC_NOMCTA, strNomCtaEfe );
        vecReg.add(INT_VEC_DEBE,  new Double( 0 ));
        vecReg.add(INT_VEC_HABER, new Double(0));
        vecReg.add(INT_VEC_REF, null);
        vecReg.add(INT_VEC_NUEVO, null);
        vecDetDiario.add(vecReg);

     objAsiDia.setDetalleDiario(vecDetDiario);
 }catch(Exception e) {   objUti.mostrarMsgErr_F1(this,e);  }
}

private void generaAsiento(double dblValDoc){
  try{
     objAsiDia.inicializar();
     int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null.
     int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema).
     int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso).
     int INT_VEC_BOTON  = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null.
     int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta.
     int INT_VEC_DEBE   = 5; //5) Debe.
     int INT_VEC_HABER  = 6; //6) Haber.
     int INT_VEC_REF    = 7; //7) Referencia: Se debe asignar una cadena vacía o null
     int INT_VEC_NUEVO    = 8; //7) Referencia: Se debe asignar una cadena vacía o null

     double dblValDebCli=0;
     double dblValHabCli=0;
     double dblValDebEfe=0;
     double dblValHabEfe=0;

       if (vecDetDiario==null) vecDetDiario = new java.util.Vector();
         else vecDetDiario.removeAllElements();

        if(dblValDoc < 0 ){
            dblValDebCli= Math.abs( dblValDoc );
            dblValHabCli=0;
            dblValDebEfe=0;
            dblValHabEfe= Math.abs( dblValDoc );;
        }else{
            dblValDebCli=0;
            dblValHabCli=Math.abs( dblValDoc );;
            dblValDebEfe=Math.abs( dblValDoc );;
            dblValHabEfe=0;
        }
      java.util.Vector vecReg;

       vecReg = new java.util.Vector();
       vecReg.add(INT_LINEA, null);
       vecReg.add(INT_VEC_CODCTA, new Integer( strCodCtaCli ) );
       vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaCli );
       vecReg.add(INT_VEC_BOTON, null);
       vecReg.add(INT_VEC_NOMCTA, strNomCtaCli );
       vecReg.add(INT_VEC_DEBE,  new Double(dblValDebCli));
       vecReg.add(INT_VEC_HABER, new Double(dblValHabCli));
       vecReg.add(INT_VEC_REF, null);
       vecReg.add(INT_VEC_NUEVO, null);

        vecDetDiario.add(vecReg);
        vecReg = new java.util.Vector();
        vecReg.add(INT_LINEA, null);
        vecReg.add(INT_VEC_CODCTA,new Integer( strCodCtaEfe ));
        vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaEfe );
        vecReg.add(INT_VEC_BOTON, null);
        vecReg.add(INT_VEC_NOMCTA, strNomCtaEfe );
        vecReg.add(INT_VEC_DEBE,  new Double( dblValDebEfe ));
        vecReg.add(INT_VEC_HABER, new Double(dblValHabEfe));
        vecReg.add(INT_VEC_REF, null);
        vecReg.add(INT_VEC_NUEVO, null);
        vecDetDiario.add(vecReg);

     objAsiDia.setDetalleDiario(vecDetDiario);
 }catch(Exception e) {   objUti.mostrarMsgErr_F1(this,e);  }
}



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panCabGen = new javax.swing.JPanel();
        panCabCob = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtDesCodTitpDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblSal = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblNumDoc = new javax.swing.JLabel();
        txtAlt1 = new javax.swing.JTextField();
        valDoc = new javax.swing.JTextField();
        lblCodDoc1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        butCalCam = new javax.swing.JButton();
        lblCodDoc2 = new javax.swing.JLabel();
        txtSalCta = new javax.swing.JTextField();
        panDetRecChq = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panCotGenSur = new javax.swing.JPanel();
        panCotGenSurCen = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblObs3 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        lblObs4 = new javax.swing.JLabel();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panAsiDia = new javax.swing.JPanel();

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

        panCabGen.setLayout(new java.awt.BorderLayout());

        panCabCob.setPreferredSize(new java.awt.Dimension(100, 70));
        panCabCob.setLayout(null);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel3.setText("Tipo de documento:"); // NOI18N
        panCabCob.add(jLabel3);
        jLabel3.setBounds(10, 5, 110, 20);

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
        panCabCob.add(txtDesCodTitpDoc);
        txtDesCodTitpDoc.setBounds(120, 5, 70, 20);

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
        panCabCob.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(190, 5, 230, 20);

        butTipDoc.setText(".."); // NOI18N
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabCob.add(butTipDoc);
        butTipDoc.setBounds(420, 5, 20, 20);

        lblSal.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblSal.setText("Saldo Cta :"); // NOI18N
        panCabCob.add(lblSal);
        lblSal.setBounds(10, 45, 100, 20);

        txtCodDoc.setBackground(objZafParSis.getColorCamposSistema());
        panCabCob.add(txtCodDoc);
        txtCodDoc.setBounds(120, 25, 90, 20);

        lblNumDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNumDoc.setText("Número alterno 1:"); // NOI18N
        panCabCob.add(lblNumDoc);
        lblNumDoc.setBounds(460, 25, 120, 20);

        txtAlt1.setBackground(objZafParSis.getColorCamposObligatorios());
        panCabCob.add(txtAlt1);
        txtAlt1.setBounds(580, 25, 90, 20);

        valDoc.setBackground(objZafParSis.getColorCamposSistema());
        valDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        valDoc.setText("0.00");
        panCabCob.add(valDoc);
        valDoc.setBounds(580, 46, 90, 20);

        lblCodDoc1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodDoc1.setText("Valor del Documento:"); // NOI18N
        panCabCob.add(lblCodDoc1);
        lblCodDoc1.setBounds(460, 45, 120, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel6.setText("Fecha del documento:"); // NOI18N
        panCabCob.add(jLabel6);
        jLabel6.setBounds(460, 5, 120, 20);

        butCalCam.setText("Calcular Cambio");
        butCalCam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCalCamActionPerformed(evt);
            }
        });
        panCabCob.add(butCalCam);
        butCalCam.setBounds(250, 40, 130, 23);

        lblCodDoc2.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodDoc2.setText("Código del documento:"); // NOI18N
        panCabCob.add(lblCodDoc2);
        lblCodDoc2.setBounds(10, 25, 120, 20);

        txtSalCta.setBackground(objZafParSis.getColorCamposSistema());
        txtSalCta.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panCabCob.add(txtSalCta);
        txtSalCta.setBounds(120, 45, 90, 20);

        panCabGen.add(panCabCob, java.awt.BorderLayout.NORTH);

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

        panCabGen.add(panDetRecChq, java.awt.BorderLayout.CENTER);

        panCotGenSur.setLayout(new java.awt.BorderLayout());

        panCotGenSurCen.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.GridLayout(2, 1));

        jPanel4.setLayout(new java.awt.BorderLayout());

        lblObs3.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs3.setText("Observación 1:"); // NOI18N
        jPanel4.add(lblObs3, java.awt.BorderLayout.WEST);

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        jPanel4.add(spnObs1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel4);

        jPanel3.setPreferredSize(new java.awt.Dimension(250, 25));
        jPanel3.setLayout(new java.awt.BorderLayout());

        lblObs4.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs4.setText("Observación 2:"); // NOI18N
        jPanel3.add(lblObs4, java.awt.BorderLayout.WEST);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        jPanel3.add(spnObs2, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel3);

        panCotGenSurCen.add(jPanel5, java.awt.BorderLayout.CENTER);

        panCotGenSur.add(panCotGenSurCen, java.awt.BorderLayout.CENTER);

        panCabGen.add(panCotGenSur, java.awt.BorderLayout.SOUTH);

        tabGen.addTab("General", panCabGen);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabGen.addTab("Asiento de Diario", panAsiDia);

        getContentPane().add(tabGen, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

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

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        // TODO add your handling code here:
        objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
        objVenConTipdoc.setCampoBusqueda(1);
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE) {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
            intTipModDoc=Integer.parseInt(objVenConTipdoc.getValueAt(4).toString());
            strCodTipDoc=objVenConTipdoc.getValueAt(1);
        }
}//GEN-LAST:event_butTipDocActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        exitForm();
        
    }//GEN-LAST:event_formInternalFrameClosing

    private void butCalCamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCalCamActionPerformed
        // TODO add your handling code here:

   double dblTotPag = _getObtenerPagoTot();
   
    dblTotPag=objUti.redondear( dblTotPag, 2 );
   if(blnExiDifCli){

        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="HA SELECCIONADO CLIENTES DIFERENTES. \n ¿ESTÁ SEGURO QUE DESEA CONTINUAR?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
          ZafCxC46_01 obj = new ZafCxC46_01( javax.swing.JOptionPane.getFrameForComponent(this),  true, objZafParSis, dblTotPag );
          obj.show();
        }

     
   }else{
       if(dblTotPag > 0 ){
         ZafCxC46_01 obj = new ZafCxC46_01( javax.swing.JOptionPane.getFrameForComponent(this),  true, objZafParSis, dblTotPag );
         obj.show();
       }else
           MensajeInf("NO HAY PAGO PARA CALCULAR...");
   }

    }//GEN-LAST:event_butCalCamActionPerformed

boolean blnExiDifCli=false;
private double _getObtenerPagoTot(){
  double dblAboTot=0;
  double dblAbo=0;
  String strCodCli="";
  int intFilRec=0;
  
  try{
    int intFilSel[];
    int intFil=0;
    intFilSel=tblDat.getSelectedRows();
    blnExiDifCli=false;

    for(int i=0; i<intFilSel.length; i++) {
         intFil=intFilSel[i]; //-i;

      if(!(tblDat.getValueAt(intFil, INT_TBL_CODCLI)==null)){

         // Math.abs(
         dblAbo =  Double.parseDouble(((tblDat.getValueAt(intFil, INT_TBL_ABONO)==null)?"0":(tblDat.getValueAt(intFil, INT_TBL_ABONO).toString().equals("")?"0":tblDat.getValueAt(intFil, INT_TBL_ABONO).toString() )  )) ;

        if(intFilRec==0){
          intFilRec=1;
          strCodCli=(tblDat.getValueAt(intFil, INT_TBL_CODCLI)==null?"":tblDat.getValueAt(intFil, INT_TBL_CODCLI).toString());
        }else{

             if(!(tblDat.getValueAt(intFil, INT_TBL_CODCLI).toString().equals(strCodCli))){
                 blnExiDifCli=true;
             }

        }
         

         dblAboTot += dblAbo;
   }}
   intFilSel=null;



   }catch(Exception e) {  objUti.mostrarMsgErr_F1(this,e);  }
  return dblAboTot;
}





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
            dispose();
        }

    }


    private void MostrarCol(int intCol, int intAch){
        tblDat.getColumnModel().getColumn(intCol).setWidth(intAch);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(intAch);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(intAch);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(intAch);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);
    }

    private void ocultaCol(int intCol){
        tblDat.getColumnModel().getColumn(intCol).setWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);
    }
    

public void BuscarTipDoc(String campo,String strBusqueda,int tipo){
  objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
  if(objVenConTipdoc.buscar(campo, strBusqueda )) {
      txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
      txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
      txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
      intTipModDoc=Integer.parseInt(objVenConTipdoc.getValueAt(4).toString());
      strCodTipDoc=objVenConTipdoc.getValueAt(1);

  }else{
        objVenConTipdoc.setCampoBusqueda(tipo);
        objVenConTipdoc.cargarDatos();
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE) {
           txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
           txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
           txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
           intTipModDoc=Integer.parseInt(objVenConTipdoc.getValueAt(4).toString());
           strCodTipDoc=objVenConTipdoc.getValueAt(1);
         }else{
           txtCodTipDoc.setText(strCodTipDoc);
           txtDesCodTitpDoc.setText(strDesCodTipDoc);
           txtDesLarTipDoc.setText(strDesLarTipDoc);
  }}}



//
//public void BuscarTipDocCon(String campo,String strBusqueda,int tipo){
//  objVenConTipdocCon.setTitle("Listado de Tipo de Documentos");
//  if(objVenConTipdocCon.buscar(campo, strBusqueda )) {
//      txtCodTipDocCon.setText(objVenConTipdocCon.getValueAt(1));
//       strCodCtaDeb=objVenConTipdocCon.getValueAt(4);
//      strTxtCodCtaDeb=objVenConTipdocCon.getValueAt(5);
//      strNomCtaDeb=objVenConTipdocCon.getValueAt(6);
//      strCodCtaHab=objVenConTipdocCon.getValueAt(7);
//      strTxtCodCtaHab=objVenConTipdocCon.getValueAt(8);
//      strNomCtaHab=objVenConTipdocCon.getValueAt(9);
//
//      strCodTipDocCon=objVenConTipdocCon.getValueAt(1);
//
//      double dblMonChq=objUti.redondear(valDoc.getText(), 2);
//      generaAsiento(dblMonChq);
//
//  }else{
//        objVenConTipdocCon.setCampoBusqueda(tipo);
//        objVenConTipdocCon.cargarDatos();
//        objVenConTipdocCon.show();
//        if (objVenConTipdocCon.getSelectedButton()==objVenConTipdocCon.INT_BUT_ACE) {
//           txtCodTipDocCon.setText(objVenConTipdocCon.getValueAt(1));
//            strCodCtaDeb=objVenConTipdocCon.getValueAt(4);
//           strTxtCodCtaDeb=objVenConTipdocCon.getValueAt(5);
//           strNomCtaDeb=objVenConTipdocCon.getValueAt(6);
//           strCodCtaHab=objVenConTipdocCon.getValueAt(7);
//           strTxtCodCtaHab=objVenConTipdocCon.getValueAt(8);
//           strNomCtaHab=objVenConTipdocCon.getValueAt(9);
//
//           strCodTipDocCon=objVenConTipdocCon.getValueAt(1);
//
//           double dblMonChq=objUti.redondear(valDoc.getText(), 2);
//           generaAsiento(dblMonChq);
//
//         }else{
//           txtCodTipDocCon.setText(strCodTipDocCon);
//
//  }}}
//




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCalCam;
    private javax.swing.JButton butTipDoc;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodDoc1;
    private javax.swing.JLabel lblCodDoc2;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs3;
    private javax.swing.JLabel lblObs4;
    private javax.swing.JLabel lblSal;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panCabCob;
    private javax.swing.JPanel panCabGen;
    private javax.swing.JPanel panCotGenSur;
    private javax.swing.JPanel panCotGenSurCen;
    private javax.swing.JPanel panDetRecChq;
    private javax.swing.JPanel panNor;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtAlt1;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtDesCodTitpDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtSalCta;
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

        strAux=objTooBar.getEstadoRegistro();
        if (strAux.equals("Eliminado")) {
            MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
            return false;
        }
        if (strAux.equals("Anulado")) {
            MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
            return false;
        }

       

     conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);


      intCodDoc=Integer.parseInt(txtCodDoc.getText());
      intCodTipDoc=Integer.parseInt(txtCodTipDoc.getText());


     if(obtenerEstAnu(conn, intCodTipDoc, intCodDoc )){
        if(anularReg(conn, intCodTipDoc, intCodDoc )){
          if(objAsiDia.anularDiario(conn, objZafParSis.getCodigoEmpresa(),  objZafParSis.getCodigoLocal() , intCodTipDoc, intCodDoc )){

            conn.commit();
            blnRes=true;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
        }else conn.rollback();
       }else conn.rollback();
      }else blnRes=false;

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
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  String strSql="";
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();

               strSql="UPDATE tbm_cabpag SET st_reg='I', st_regrep='M', " +
               " fe_ultmod="+objZafParSis.getFuncionFechaHoraBaseDatos()+", " +
               " co_usrmod="+objZafParSis.getCodigoUsuario()+", "+
               " tx_commod='"+objZafParSis.getNombreComputadoraConDirIP()+"' "+
               " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
               " AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" ; " +
               " "+
               " UPDATE tbm_pagmovinv SET nd_abo=nd_abo- x.ndabo, st_regrep='M' " +
               "  FROM ( " +
               "   select co_emp, co_locpag, co_tipdocpag, co_docpag, co_regpag, nd_abo as ndabo  from tbm_detpag as a " +
               "   where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a.co_tipdoc="+intCodTipDoc+" " +
               "   and a.co_doc="+intCodDoc+" and a.st_reg='A' " +
               " ) AS x  WHERE " +
               " tbm_pagmovinv.co_emp=x.co_emp and tbm_pagmovinv.co_loc=x.co_locpag and tbm_pagmovinv.co_tipdoc=x.co_tipdocpag and tbm_pagmovinv.co_doc=x.co_docpag " +
               " and tbm_pagmovinv.co_reg=x.co_regpag ; " +
               " ";

              stmLoc.executeUpdate(strSql);

       stmLoc.close();
       stmLoc=null;

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

           

            clnTextos();

            cargarTipoDoc (2);


	}

        public void clickEliminar() {

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

    java.awt.Color colBack;
    colBack = txtCodDoc.getBackground();
    txtCodDoc.setEditable(false);
    txtCodDoc.setBackground(colBack);

    txtSalCta.setEditable(false);
    txtSalCta.setBackground(colBack);

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
    objTblMod.setDataModelChanged(false);

    cargarTipoDoc(1);

   
    
    butCalCam.setEnabled(true);

      if(rstCab!=null) {
          rstCab.close();
          rstCab=null;
      }

    
   if(!_getEstImpDoc())
     this.setEstado('w');
   else
       _getMostrarSaldoCta();
    

   }catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e); }
}



/**
  * Esta función muestra la cuenta contable predeterminado del programa
  * de acuerdo al tipo de documento predeterminado.
  * @return true: Si se pudo mostrar la cuenta contable predeterminado.
  * <BR>false: En el caso contrario.
*/
 private void _getMostrarSaldoCta(){
  java.sql.Connection  conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     if(txtCodTipDoc.getText().length()>0){

       conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
       if(conn!=null){
         stmLoc=conn.createStatement();

         strSql="SELECT sum(round((a2.nd_mondeb - a2.nd_monhab),2)) as SALDO "+
         " FROM tbm_cabdia AS a1 " +
         " INNER JOIN tbm_detdia AS a2 on (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_dia=a2.co_dia) "+
         " WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" " +
         " AND a2.co_cta = ( SELECT a1.co_ctadeb  FROM tbm_cabTipDoc AS a1 WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" AND  " +
         " a1.co_loc="+objZafParSis.getCodigoLocal()+" AND a1.co_tipDoc= "+
         " ( SELECT co_tipDoc  FROM tbr_tipDocPrg  WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
         " AND co_mnu="+objZafParSis.getCodigoMenu()+" AND st_reg='S' )  ) " +
         " AND a1.st_reg='A' ";
         System.out.println(" ->>  " + strSql );
         rstLoc=stmLoc.executeQuery(strSql);
         if(rstLoc.next()){
              txtSalCta.setText(rstLoc.getString("SALDO"));
         }
         rstLoc.close();
         rstLoc=null;
         stmLoc.close();
         stmLoc=null;
         conn.close();
         conn=null;
      }
    }}catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
      catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }
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
        " ,doc.tx_natdoc, doc.st_meringegrfisbod ,a1.co_cta, a1.tx_codcta, a1.tx_deslar AS txdeslarctaefe , a2.co_cta as cocta, a2.tx_codcta as txcodcta, a2.tx_deslar as deslarcta " +
        " ,2 as ne_tipresmoddoc " +
        " FROM tbr_tipdocprg as menu " +
        " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "+
        " inner join tbm_placta as a1 on (a1.co_emp=doc.co_emp and a1.co_cta=doc.co_ctadeb ) " +
        " inner join tbm_placta as a2 on (a2.co_emp=doc.co_emp and a2.co_cta=doc.co_ctahab ) " +
        " WHERE   menu.co_emp = "+objZafParSis.getCodigoEmpresa() + " and " +
        " menu.co_loc = " + objZafParSis.getCodigoLocal()+" AND  " +
        " menu.co_mnu = "+objZafParSis.getCodigoMenu()+" AND  menu.st_reg = 'S' ";
    }else{

        strSql= "SELECT  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor, " +
        " case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc "+
        " ,doc.tx_natdoc, doc.st_meringegrfisbod ,a1.co_cta, a1.tx_codcta, a1.tx_deslar AS txdeslarctaefe , a2.co_cta as cocta, a2.tx_codcta as txcodcta, a2.tx_deslar as deslarcta " +
        "  ,menu.ne_tipresmoddoc " +
        " FROM tbr_tipDocUsr as menu " +
        " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "+
        " inner join tbm_placta as a1 on (a1.co_emp=doc.co_emp and a1.co_cta=doc.co_ctadeb ) " +
        " inner join tbm_placta as a2 on (a2.co_emp=doc.co_emp and a2.co_cta=doc.co_ctahab ) " +
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
            intTipModDoc=rstLoc.getInt("ne_tipresmoddoc");
            strCodTipDoc=txtCodTipDoc.getText();
            if(intVal==1)
               txtAlt1.setText(((rstLoc.getString("numDoc")==null)?"":rstLoc.getString("numDoc")));

            
            strCodCtaCli=rstLoc.getString("cocta");
            strTxtCodCtaCli=rstLoc.getString("txcodcta");
            strNomCtaCli=rstLoc.getString("deslarcta");
            strCodCtaEfe=rstLoc.getString("co_cta");
            strTxtCodCtaEfe=rstLoc.getString("tx_codcta");
            strNomCtaEfe=rstLoc.getString("txdeslarctaefe");



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
       if(blnHayCam ) {
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

  return blnRes;
}








/**
 * validad campos requeridos antes de insertar o modificar
 * @return  true si esta todo bien   false   falta algun dato
 */
 private boolean validaCampos(){

    int intExiDatTbl=0;
    String strMens="RETENCIONES";
    String strCodEmp="",strCodLoc="", strCodTipDoc="", strCodDoc="";
        
  if((objZafParSis.getCodigoMenu()==256)){
       strMens="CHEQUE";
   }


  if(txtCodTipDoc.getText().trim().equals("") ){
    tabGen.setSelectedIndex(0);
    MensajeInf("El campo << Tipo de documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtCodTipDoc.requestFocus();
    return false;
   }



 


   if(txtAlt1.getText().equals("") ){
    tabGen.setSelectedIndex(0);
    MensajeInf("El campo << Número alterno 1 >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtAlt1.requestFocus();
    return false;
    }



     for(int i=0; i<tblDat.getRowCount(); i++){
       if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){

          intExiDatTbl=1;

          if (objUti.parseString(tblDat.getValueAt(i,0)).equals("I")) {
              strCodEmp=tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
              strCodLoc=tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
              strCodTipDoc=tblDat.getValueAt(i, INT_TBL_CODTID).toString();
              strCodDoc=tblDat.getValueAt(i, INT_TBL_CODDOC).toString();

              if (!isAnulado(strCodEmp, strCodLoc, strCodTipDoc, strCodDoc)) {
                  tblDat.repaint();
                  tblDat.requestFocus();
                  tblDat.setValueAt(Boolean.FALSE, i, INT_TBL_CHKSEL);
                  tblDat.setValueAt("0", i, INT_TBL_ABONO);
                  calculaTotMonAbo();
                  return false;              
              }              
          }

//         if((objZafParSis.getCodigoMenu()==256)){
//          if( ((tblDat.getValueAt(i, INT_TBL_CODBAN)==null?"":(tblDat.getValueAt(i, INT_TBL_CODBAN).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODBAN).toString())).equals("")) ){
//                MensajeInf("SELECCIONE EL BANCO. ");
//                tblDat.repaint();
//                tblDat.requestFocus();
//                tblDat.editCellAt(i, INT_TBL_NOMBAN);
//                return false;
//          }
//        }
//           if( ((tblDat.getValueAt(i, INT_TBL_NUMCHQ)==null?"":(tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString())).equals("")) ){
//                MensajeInf("DIGITE EL NUMERO DEL "+strMens+". ");
//                tblDat.repaint();
//                tblDat.requestFocus();
//                tblDat.editCellAt(i, INT_TBL_NUMCHQ);
//                return false;
//          }

//          if( Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_ABONO).toString())) ) != 0 ){
//                MensajeInf("DIGITE EL VALOR DEL "+strMens+". ");
//                tblDat.repaint();
//                tblDat.requestFocus();
//                tblDat.editCellAt(i, INT_TBL_VALCHQ);
//                return false;
//          }

          

//         if((objZafParSis.getCodigoMenu()==256)){
//          if( ((tblDat.getValueAt(i, INT_TBL_FECVENCHQ)==null?"":(tblDat.getValueAt(i, INT_TBL_FECVENCHQ).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_FECVENCHQ).toString())).equals("")) ){
//                MensajeInf("DIGITE LA FECHA DE VENCIMIENTO DEL CHEQUE. ");
//                tblDat.repaint();
//                tblDat.requestFocus();
//                tblDat.editCellAt(i, INT_TBL_FECVENCHQ);
//                return false;
//          }
//        }
      }
     }


    if(intExiDatTbl==0){
        MensajeInf("NO HAY DATOS EN DETALLE INGRESE DATOS.... ");
        return false;
    }


   return true;
 }


private boolean isAnulado(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
    boolean blnRes = true;
    java.sql.Connection conn;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSQL = "";
    try {
        conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        if (conn!=null) {
            stmLoc = conn.createStatement();

            strSQL = " SELECT co_doc FROM tbm_cabmovinv ";
            strSQL += " WHERE co_emp = " + strCodEmp + " AND co_loc = " + strCodLoc + " and co_tipdoc = " + strCodTipDoc ;
            strSQL += " AND co_doc = " + strCodDoc + " AND st_reg = 'I' " ;
            rstLoc=stmLoc.executeQuery(strSQL);
            
            if(rstLoc.next()) {
                MensajeInf("El documento que desea registrar se encuentra ANULADO.\nPor favor verifique el dato ingresado.");
                blnRes = false;
            }
            rstLoc.close();
            rstLoc=null;
            stmLoc.close();
            stmLoc=null;
        }
    }
    catch(SQLException Evt){  objUti.mostrarMsgErr_F1(this, Evt);   blnRes = false;  }
    catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);   blnRes = false;  }
    return blnRes;
}



private double verificarTotales(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc) {    
    java.sql.Connection conn;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSQL = "";
    double dblMon=0.0;
    try {
        conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        if (conn!=null) {
            stmLoc = conn.createStatement();

            strSQL = " SELECT sum(b.nd_abo) as nd_mondoc ";
            strSQL +=" FROM tbm_cabpag  as a";
            strSQL +=" INNER JOIN tbm_detpag as b ";
            strSQL +=" ON a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc";
            strSQL += " WHERE a.co_emp = " + intCodEmp + " AND a.co_loc = " + intCodLoc + " and a.co_tipdoc = " + intCodTipDoc ;
            strSQL += " AND a.co_doc = " + intCodDoc;
            rstLoc=stmLoc.executeQuery(strSQL);
            
            if(rstLoc.next()) {
              dblMon =  rstLoc.getDouble("nd_mondoc");
            }
            rstLoc.close();
            rstLoc=null;
            stmLoc.close();
            stmLoc=null;
            conn.close();
            conn=null;
        }
    }
    catch(SQLException Evt){  
        objUti.mostrarMsgErr_F1(this, Evt);
    }
    catch(Exception Evt)  {  
        objUti.mostrarMsgErr_F1(this, Evt);
    }    
    return dblMon;
}




public boolean insertar() {
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intCodDoc=0;
  ZafGenOdPryTra genODTra=new ZafGenOdPryTra();
  ZafCnfDoc objValCnf=new ZafCnfDoc(objZafParSis,this);
  try{

    if(validaCampos()){

     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);
       stmLoc=conn.createStatement();

        strSql="SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 end as codoc  FROM tbm_cabpag WHERE " +
        " co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+txtCodTipDoc.getText();
        rstLoc = stmLoc.executeQuery(strSql);
        if(rstLoc.next())
            intCodDoc = rstLoc.getInt("codoc");
         rstLoc.close();
         rstLoc=null;

        strSqlInsDet=new StringBuffer();

        INT_ENV_REC_IMP_GUIA = 0;

        if(insertarCab(conn, intCodDoc)){
          if(insertarDet(conn, intCodDoc)){
           if(objAsiDia.insertarDiario(conn, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), String.valueOf(intCodDoc), txtAlt1.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy") )) {
            if(objAjuCenAut.realizaAjuCenAut(conn, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), intCodDoc,  80,  objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy") )) {
              conn.commit();
              txtCodDoc.setText(""+intCodDoc);
              blnRes=true;
              cargarDetIns(conn, intCodDoc);
              
              //Luis Parrales ---->aqui invoca el hilo
              
                 if(INT_ENV_REC_IMP_GUIA==1) {

                     Iterator itVecOD=vecOD.iterator();
                     ZafGenGuiRem objZafGuiRem=new ZafGenGuiRem();
                     while(itVecOD.hasNext()){
                         String strDat=(String)itVecOD.next();
                         
                        String[] strArrDat=strDat.split("-");
                        
                        int intCodEmp=Integer.parseInt((String)strArrDat[0]);
                        int intCodLoc=Integer.parseInt((String)strArrDat[1]);
                        int intCodTipDoc2=Integer.parseInt((String)strArrDat[2]);
                        int intCodDocu=Integer.parseInt((String)strArrDat[3]);                        
                        
                        if(!objValCnf.isDocIngPenCnfxFac(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, "I")){
                            if(!genODTra.validarODExs(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu)){
                                if(genODTra.generarNumOD(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, false)){
                                    conn.commit();
                                    String strIp=genODTra.obtenerIpSerImp(conn);
                                    genODTra.imprimirOdLocal(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, strIp);                                                
                                    genODTra.generarTermL(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu);
                                }else{
                                    conn.rollback();
                                } 
                            } 
                        }
                     }
                     
                     
                     vecOD.clear();
//
                 }//fin de if
               
          }else conn.rollback();
         }else conn.rollback();
        }else conn.rollback();
       }else conn.rollback();
     
      strSqlInsDet=null;

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
     //  System.out.println(" OKI----->  ");
       java.net.Socket s1 = new java.net.Socket(strIp, intPuerto);
       java.io.DataOutputStream dos = new java.io.DataOutputStream(s1.getOutputStream());
       dos.writeInt(1);

       dos.close();
       s1.close();

     }catch (java.net.ConnectException connExc){   System.err.println("OCURRIO UN ERROR 1 "+connExc ); }
      catch (IOException e){  System.err.println("OCURRIO UN ERROR 2 "+ e );  }
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
 String strValDoc="", strValPen="", strValApl="";
 String strCodEmp="",strCodLoc="", strCodTipDocRec="", strCodDoc="", strCodReg="";
 int intCodReg=0;
 int intEst=0;
 int intTipMov=1;
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();
        stmLoc01=conn.createStatement();
        stmLoc02=conn.createStatement();


     stbFacSel=new StringBuffer();
     for(int i=0; i<tblDat.getRowCount(); i++){
        if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){
         if( ((tblDat.getValueAt(i, INT_TBL_CODREGEFE)==null?"":(tblDat.getValueAt(i, INT_TBL_CODREGEFE).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODREGEFE).toString())).equals("")) ){

           strValDoc=(tblDat.getValueAt(i, INT_TBL_VALDOC)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALDOC).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALDOC).toString()));
           strValPen=(tblDat.getValueAt(i, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALPEN).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALPEN).toString()));
           strValApl=(tblDat.getValueAt(i, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_ABONO).toString()));

           strCodEmp=tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
           strCodLoc=tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
           strCodTipDocRec=tblDat.getValueAt(i, INT_TBL_CODTID).toString();
           strCodDoc=tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
           strCodReg=tblDat.getValueAt(i, INT_TBL_CODREG).toString();

           strSql="SELECT "+strCodEmp+" as coemp, "+strCodLoc+" AS coloc, "+strCodTipDocRec+" as cotipdoc, "+strCodDoc+" as codoc "+
           " ,"+strCodReg+" as coreg, "+strValDoc+" as valdoc, "+strValPen+" as valpen, "+strValApl+" as valapl  ";

         //if(strEstFac.equals("S")){
          
              if(intEst==1) stbFacSel.append(" UNION ALL ");
              stbFacSel.append( strSql );
              intEst=1;
         //}
      }}}

     if(intEst==1){

         strSql="SELECT x.* FROM ( " +
         " SELECT *, ( abs(valpen)- abs(valapl) ) as val2 FROM ( "+stbFacSel.toString()+" ) AS x ) AS x " +
         " INNER JOIN tbm_emp AS a1 ON ( a1.co_emp = x.coemp )   " +
         " WHERE val2 > 0 and   ( abs(val2)  between  a1.nd_valminajucenaut and  a1.nd_valmaxajucenaut ) = false ";
         System.out.println("--> "+strSql );
         rstLoc=stmLoc.executeQuery(strSql);
         while(rstLoc.next()){

           if(rstLoc.getDouble("valdoc") > 0  ) intTipMov=1;
           else  intTipMov=-1;

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
           " 0, 0, tx_aplret, abs("+rstLoc.getDouble("val2")+")*"+intTipMov+", ne_diagra, 0 as nd_abo, st_sop,  "+
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
               " co_tipret, nd_porret, tx_aplret, abs(nd_abo)*"+intTipMov+", ne_diagra, nd_abo, st_sop,  "+
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

           strSql="UPDATE tbm_pagmovinv SET nd_abo=0,  mo_pag=abs("+rstLoc.getDouble("valapl")+")*"+intTipMov+", st_reg='C', st_regrep='M' WHERE " +
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
             strSql ="select count(ne_numdoc1) as num from tbm_cabpag WHERE " +
                     " co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" " +
                     "and co_tipdoc="+txtCodTipDoc.getText()+" and ne_numdoc1="+txtAlt1.getText();
              rstLoc = stmLoc.executeQuery(strSql);
              if(rstLoc.next()){
                  if(rstLoc.getInt("num") >= 1 ){
                        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
                        String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";
                        strMsg=" No. de Cobro ya existe... ?";
                        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE,null);
                        blnRes=true;
              }}

              rstLoc.close();
              rstLoc=null;
              if(blnRes) return false;
              blnRes=false;
        //***********************************************************************************************/

        strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";

        strSql="INSERT INTO tbm_cabpag(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc1, ne_numdoc2, tx_obs1, tx_obs2 " +
        " ,nd_mondoc, st_reg, fe_ing, co_usring, tx_coming, co_mnu, st_regrep ) "+
        " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" " +
        " ,'"+strFecDoc+"', "+(txtAlt1.getText().trim().equals("")?null:txtAlt1.getText())+", "+(txtAlt1.getText().trim().equals("")?null:txtAlt1.getText())+", '"+txaObs1.getText()+"', '"+txaObs2.getText()+"' " +
        " ,"+(valDoc.getText().equals("")?"0":valDoc.getText())+", 'A', "+objZafParSis.getFuncionFechaHoraBaseDatos()+","+objZafParSis.getCodigoUsuario()+" " +
        " ,'"+objZafParSis.getNombreComputadoraConDirIP()+"', "+objZafParSis.getCodigoMenu()+", 'I' ) ; ";

        stmLoc.executeUpdate(strSql);

      stmLoc.close();
      stmLoc=null;
    blnRes=true;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

  
  

private boolean insertarDet(java.sql.Connection conn, int intCodDoc){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strCodEmp="",strCodLoc="", strCodTipDocRec="", strCodDoc="", strCodReg="", strAbono="0", strValPen="";
 String strSql="";
 int intCodRegDet=0;
 double dblValPen=0;
 int intTipMov=1;
 vecOD.clear();
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

     for(int i=0; i<tblDat.getRowCount(); i++){
        if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){
         if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){

          strCodEmp=tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
          strCodLoc=tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
          strCodTipDocRec=tblDat.getValueAt(i, INT_TBL_CODTID).toString();
          strCodDoc=tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
          strCodReg=tblDat.getValueAt(i, INT_TBL_CODREG).toString();
          strAbono=(tblDat.getValueAt(i, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_ABONO).toString()));        


          strValPen=(tblDat.getValueAt(i, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALPEN).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALPEN).toString()));
          dblValPen = objUti.redondear(strValPen, 2);
          if(dblValPen > 0 ) intTipMov=-1;
          else intTipMov=1;

          intCodRegDet++;
          //strSqlInsDet.append(
		  
		  
		/*CMATEO 04/NOV/2015 Usado para que no haya detalles repetidos en el cobro de facturas*/
		String strSqlVal="select 1 "+
		" from tbm_detpag as a  " +
		" WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+"  and " +
		" a.co_tipdoc="+txtCodTipDoc.getText()+"  and a.co_Doc="+intCodDoc+"  and a.st_reg='A'  and co_locpag="+ strCodLoc +" and co_tipdocpag="+strCodTipDocRec+" and co_docpag="+strCodDoc+" and co_regpag="+strCodReg+" ORDER BY a.co_reg  ";
	   
		ResultSet rstLoc=stmLoc.executeQuery(strSqlVal);
		
		if(!rstLoc.next()){
		/*CMATEO 04/NOV/2015 Usado para que no haya detalles repetidos en el cobro de facturas*/		  
		  
		  strSql = " INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag," +
		  " co_docpag, co_regpag, nd_abo,  st_reg, st_regrep  )" +
		  " VALUES( "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" " +
		  " ,"+intCodRegDet+", "+strCodLoc+", "+strCodTipDocRec+", "+strCodDoc+", "+strCodReg+", "+strAbono+" * "+intTipMov+" , 'A','I' ) ; "; // );
		  stmLoc.executeUpdate( strSql );

		  //strSqlInsDet.append(
		  strSql = " UPDATE tbm_pagmovinv set nd_abo=nd_abo + "+strAbono+" * "+intTipMov+", " +
		  " st_regrep='M' WHERE " +
		  " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+" " +
		  " and co_reg="+strCodReg+" ; "; // );
		  stmLoc.executeUpdate( strSql );

		  if( objLiberaGuia._getVerificaPagTotFac(conn, strCodEmp, strCodLoc, strCodTipDocRec, strCodDoc  )){

			   //strSqlInsDet.append(
			   strSql = " UPDATE tbm_cabguirem SET " +
			   "  st_aut='A' "+
			   " ,fe_aut="+objZafParSis.getFuncionFechaHoraBaseDatos()+" "+
			   " ,tx_comAut='"+objZafParSis.getNombreComputadoraConDirIP()+"' "+
			   " ,co_usrAut="+objZafParSis.getCodigoUsuario()+" "+
			   " FROM  ( " +
			   "  select co_emp, co_loc, co_tipdoc, co_doc from tbm_detguirem where " +
			   "  co_emprel="+strCodEmp+" and co_locrel="+strCodLoc+"  and  co_tipdocrel="+strCodTipDocRec+" and co_docrel="+strCodDoc+" " +
			   "  group by co_emp, co_loc, co_tipdoc, co_doc " +
			   " ) AS x " +
			   " WHERE x.co_emp= tbm_cabguirem.co_emp and x.co_loc=tbm_cabguirem.co_loc and x.co_tipdoc=tbm_cabguirem.co_tipdoc " +
			   " and x.co_doc=tbm_cabguirem.co_doc  and  tbm_cabguirem.ne_numdoc=0 and tbm_cabguirem.st_aut='P'  ;   "; // );
			   stmLoc.executeUpdate( strSql );

			   INT_ENV_REC_IMP_GUIA=1;
			   

				//generaOD(conn);
				
				vecOD.add(tblDat.getValueAt(i, INT_TBL_CODEMP)+"-"+tblDat.getValueAt(i, INT_TBL_CODLOC)+"-"+tblDat.getValueAt(i, INT_TBL_CODTID)+"-"+tblDat.getValueAt(i, INT_TBL_CODDOC)+"-"+tblDat.getValueAt(i, INT_TBL_NUMDOC));
			   
		  }

		   blnRes=true;
		}else{
			blnRes=false;
			objUti.mostrarMsgErr_F1(this, new Exception("Posee un cobro ya registrado"));                
			break;
		}		   
          
    }}}

//      if(intCodRegDet > 0 ){
//          // System.out.println("--> "+ strSqlInsDet.toString()  );
//           stmLoc.executeUpdate(strSqlInsDet.toString());
//
//      }

      stmLoc.close();
      stmLoc=null;

 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}



//
//private boolean _getVerificaPagTotFac(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc){
// boolean blnRes=false;
// java.sql.Statement stm;
// java.sql.ResultSet rstLoc;
// String strSql="";
// try{
//  if (conn!=null){
//        stm=conn.createStatement();
//
//         strSql="select *, (pagfac+monchq) as dif from (  " +
//         " select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc,  sum(a1.mo_pag+a1.nd_Abo) as pagfac , sum(a1.nd_monchq) as monchq  " +
//         " from tbm_pagmovinv as a1 " +
//         " left join tbr_detrecdocpagmovinv as a2 " +
//         " on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg  )  " +
//         " where a1.co_emp="+strCodEmp+" and a1.co_loc="+strCodLoc+" and a1.co_tipdoc="+strCodTipDoc+" and a1.co_doc="+strCodDoc+"  and  a1.nd_porret=0  and a1.st_reg in ('A','C')  " +
//         " group by a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc  ) as x  ";
//         //System.out.println("--->  "+strSql);
//         rstLoc=stm.executeQuery(strSql);
//         if(rstLoc.next()){
//            if( rstLoc.getDouble("monchq") > 0 ){
//              if( rstLoc.getDouble("dif") >= 0 ){
//                if( _getVerificaFecChqFac(conn, rstLoc.getString("co_emp"), rstLoc.getString("co_loc"), rstLoc.getString("co_tipdoc"), rstLoc.getString("co_doc") ) )
//                 blnRes=true;
//              }else{
//                  if( rstLoc.getDouble("dif") >= -0.01 ){
//                    if( _getVerificaFecChqFac(conn, rstLoc.getString("co_emp"), rstLoc.getString("co_loc"), rstLoc.getString("co_tipdoc"), rstLoc.getString("co_doc") ) )
//                      blnRes=true;
//                  }
//              }
//            }
//         }
//         rstLoc.close();
//         rstLoc=null;
//
//        if(!blnRes){
//
//          strSql="select *, (pagfac+abofac) as dif from (  " +
//          " select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc,  sum(a1.mo_pag) as pagfac , sum(a1.nd_abo) as abofac  " +
//          " from tbm_pagmovinv as a1 " +
//          " where a1.co_emp="+strCodEmp+" and a1.co_loc="+strCodLoc+" and a1.co_tipdoc="+strCodTipDoc+" and a1.co_doc="+strCodDoc+"  and  a1.nd_porret=0  and a1.st_reg in ('A','C')  " +
//          " group by a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc ) as x   ";
//          //System.out.println("--->  "+strSql);
//          rstLoc=stm.executeQuery(strSql);
//          if(rstLoc.next()){
//            if( rstLoc.getDouble("abofac") > 0 ){
//              if( rstLoc.getDouble("dif") >= -0.01 ){
//                  blnRes=true;
//              }
//            }
//          }
//          rstLoc.close();
//          rstLoc=null;
//
//        }
//
//
//   stm.close();
//   stm=null;
//
//}}catch (java.sql.SQLException e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
//  catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
// return blnRes;
//}
//
//
//
//private boolean _getVerificaFecChqFac(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc){
// boolean blnRes=false;
// java.sql.Statement stm;
// java.sql.ResultSet rstLoc;
// String strSql="";
// try{
//  if (conn!=null){
//        stm=conn.createStatement();
//
//
//       strSql="select * " +
//       " , case when fe_venchq <= fe_ven then 'S' else 'N' end as FecCont " +
//       " , case when fe_venchq <= fecvenchq then 'S' else 'N' end as FecnoCont " +
//       " from ( "+
//       "select ne_diacre, fe_ven, (fe_ven+1) as fecvenchq , fe_venchq  from tbm_pagmovinv " +
//       " where co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" and co_doc="+strCodDoc+" and nd_porret=0 and st_reg in ('A','C') " +
//       " and  ( mo_pag + nd_Abo) != 0   ) as x  ";
//       //System.out.println("-> "+strSql );
//       rstLoc=stm.executeQuery(strSql);
//       while(rstLoc.next()){
//
//           if(rstLoc.getInt("ne_diacre")==0){
//                if(rstLoc.getString("FecCont").equals("S")){
//                    blnRes=true;
//                }else{
//                    blnRes=false;
//                    break;
//                }
//           }else{
//               if(rstLoc.getString("FecnoCont").equals("S")){
//                    blnRes=true;
//                }else{
//                    blnRes=false;
//                    break;
//                }
//           }
//
//       }
//       rstLoc.close();
//       rstLoc=null;
//       stm.close();
//       stm=null;
//
//}}catch (java.sql.SQLException e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
//  catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
// return blnRes;
//}





/**
 * Permite recargar los datos de la tabla despues de insertar o modificar con objetivo de tener ejem:  codigo del registro
 * que eso da cuando se insertar
 * @param conn   coneccion de la base
 * @param intCodDoc   codigo del documento
 * @return  true si se consulto bien   false si hay algun error.
 */
private boolean cargarDetIns(java.sql.Connection conn, int intCodDoc ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  Vector vecData;
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

        vecData = new Vector();

        strSql="select a2.co_cli, a2.tx_nomcli, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a3.tx_descor, a3.tx_deslar, " +
        " a2.ne_numdoc, a2.fe_doc ,a1.ne_diacre, a1.fe_ven, a1.nd_porret, a1.mo_pag, ( a1.mo_pag + a1.nd_abo ) as valpen, a.nd_abo, a.co_reg as coregpag " +
        " from tbm_detpag as a  " +
        " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locpag and a1.co_tipdoc=a.co_tipdocpag and a1.co_doc=a.co_docpag and a1.co_reg=a.co_regpag )  " +
        " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc )  " +
        " inner join tbm_cabtipdoc as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc  )    " +
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+"  and " +
        " a.co_tipdoc="+txtCodTipDoc.getText()+"  and a.co_Doc="+intCodDoc+"  and a.st_reg='A'  ORDER BY a.co_reg  ";
       // System.out.println("--> " +strSql);
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

           java.util.Vector vecReg = new java.util.Vector();
             vecReg.add(INT_TBL_LINEA,"");
             vecReg.add(INT_TBL_BUTCLI,"..");
             vecReg.add(INT_TBL_CHKSEL, new Boolean(true) );
             vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
             vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nomcli") );
             vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp") );
             vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
             vecReg.add(INT_TBL_CODTID, rstLoc.getString("co_tipdoc") );
             vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
             vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg") );
             vecReg.add(INT_TBL_DCTIPDOC, rstLoc.getString("tx_descor") );
             vecReg.add(INT_TBL_DLTIPDOC, rstLoc.getString("tx_deslar") );
             vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc") );
             vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc") );
             vecReg.add(INT_TBL_DIACRE, rstLoc.getString("ne_diacre") );
             vecReg.add(INT_TBL_FECVEN, rstLoc.getString("fe_ven") );
             vecReg.add(INT_TBL_PORRET, rstLoc.getString("nd_porret") );
             vecReg.add(INT_TBL_VALDOC, rstLoc.getString("mo_pag") );
             vecReg.add(INT_TBL_VALPEN, rstLoc.getString("valpen") );
             vecReg.add(INT_TBL_ABONO,  rstLoc.getString("nd_abo") );
             vecReg.add(INT_TBL_CODREGEFE,  rstLoc.getString("coregpag") );
             vecReg.add(INT_TBL_ABONOORI,  rstLoc.getString("nd_abo") );
             vecReg.add( INT_TBL_BUTFAC, "..");

            vecData.add(vecReg);
       }
        rstLoc.close();
        rstLoc=null;

        objTblMod.setData(vecData);
        tblDat .setModel(objTblMod);

       /***************************************************/

       stmLoc.close();
       stmLoc=null;

    blnRes=true;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
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

      if( intTipModDoc == 1 ){
         blnRes=false; 
         MensajeInf("NO TIENE ACCESO A MODIFICAR EL DOCUMENTO.. ");
      }else if( intTipModDoc == 2 ){
 
       strSql="SELECT st_imp FROM tbm_cabpag WHERE  co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+"" +
       " AND co_doc="+intCodDoc+"  and st_imp='S'";
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next()){
           blnRes=false;
           MensajeInf("EL DOCUMENTO YA ESTA IMPRESO Y NO SE PUEDE MODIFICAR ..");
       }
       rstLoc.close();
       rstLoc=null;

      }else if( intTipModDoc == 3 ){
         // SI PERMITE MODIFCAR
     }


       stmLoc.close();
       stmLoc=null;

  }}catch(java.sql.SQLException ex) { blnRes=false; objUti.mostrarMsgErr_F1(this, ex);   }
    catch(Exception Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}




public boolean modificar(){
    boolean blnRes=false;
  java.sql.Connection conn;
  int intCodDoc=0;
  int intCodTipDoc=0;
  String strAux="";
  ZafGenOdPryTra genODTra=new ZafGenOdPryTra();
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


      INT_ENV_REC_IMP_GUIA = 0;


    if(validaCampos()){

     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);

       intCodDoc=Integer.parseInt(txtCodDoc.getText());
       intCodTipDoc=Integer.parseInt(txtCodTipDoc.getText());

       if(obtenerEstAnu(conn, intCodTipDoc, intCodDoc )){
        if( _getEstImp( conn, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(),  intCodTipDoc,  intCodDoc ) ){

       strSqlInsDet=new StringBuffer();

     
        if(modificarCab(conn, intCodTipDoc, intCodDoc)){
         if(EliminadosRegCobEfe(conn, intCodTipDoc, intCodDoc)){
          if(modificarDet(conn, intCodTipDoc, intCodDoc)){
           if(objAsiDia.actualizarDiario(conn, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), intCodTipDoc, intCodDoc, txtCodDoc.getText(),objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), "A")){
            if(objAjuCenAut.realizaAjuCenAut(conn, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), intCodDoc,  80,  objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy") )) {
				if(verificarTotales(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), intCodTipDoc, intCodDoc)==dblAftModTot){ //AGREGADO PARA EVITAR MODIFICACIONES CON MULTIUSUARIO
               conn.commit();
               blnRes=true;
               cargarDetIns( conn,  intCodDoc);

                 //luis parrales aqui nuevamente invoca el hilo de impresion de guia
                  if(INT_ENV_REC_IMP_GUIA==1) {

                     
                     /*GENERA TERMINAL L*/
                     ZafGenGuiRem objZafGuiRem=new ZafGenGuiRem();
                     Iterator itVecOD=vecOD.iterator();
                     while(itVecOD.hasNext()){                     
                        String strDat=(String)itVecOD.next();                        
                        String[] strArrDat=strDat.split("-");                        
                        int intCodEmp=Integer.parseInt((String)strArrDat[0]);
                        int intCodLoc=Integer.parseInt((String)strArrDat[1]);
                        int intCodTipDoc2=Integer.parseInt((String)strArrDat[2]);
                        int intCodDocu=Integer.parseInt((String)strArrDat[3]);                       
                        
                        if(!objValCnf.isDocIngPenCnfxFac(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, "I")){
                            if(!genODTra.validarODExs(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu)){
                                //if(!objValCnf.isDocIngPenCnfxCob(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, "I")){
                                    if(genODTra.generarNumOD(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, false)){
                                        conn.commit();
                                        String strIp=genODTra.obtenerIpSerImp(conn);
                                        genODTra.imprimirOdLocal(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, strIp);                                                
                                        genODTra.generarTermL(conn, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu);
                                    }else{
                                        conn.rollback();
                                    } 
                            }
                        }                        
                     }
                     vecOD.clear();
                     /*GENERA LA OD REMOTA*/
                     
                  }//enviarRequisitoImp(strIpImpGuia, intPuertoImpGuia);
				}else{                   
                   conn.rollback();
                   MensajeInf("Otro usuario ha realizado cobros.\nFavor volver a consultar el documento.");
               	}

            }else conn.rollback();
           }else conn.rollback();
          }else conn.rollback();
         }else conn.rollback();
        }else conn.rollback();
     

       strSqlInsDet=null;
     }}

       conn.close();
       conn=null;
   }

  }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


public boolean EliminadosRegCobEfe(java.sql.Connection conn, int intCodTipDoc, int intCodDoc){
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
               strSql="SELECT co_emp, co_locpag, co_tipdocpag, co_docpag, co_regpag , nd_abo FROM tbm_detpag WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
               " AND co_tipdoc="+intCodTipDoc+" AND co_doc= "+intCodDoc+" AND co_reg="+intCodReg+" ";
               //System.out.println("strSql-->"+ strSql );
               rstLoc=stmLoc.executeQuery(strSql);
               if(rstLoc.next()){

                   strSql="UPDATE tbm_detpag SET st_reg='E' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND" +
                   " co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" and co_reg="+intCodReg;
                  
                   strSql+=" ; UPDATE tbm_pagmovinv SET  nd_abo = nd_abo - "+rstLoc.getString("nd_abo")+" "+
                   " WHERE co_emp="+rstLoc.getString("co_emp")+" AND co_loc="+rstLoc.getString("co_locpag")+" AND co_tipdoc="+rstLoc.getString("co_tipdocpag")+" " +
                   " AND co_doc="+rstLoc.getString("co_docpag")+" AND co_reg="+rstLoc.getString("co_regpag")+" ";

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




private boolean modificarDet(java.sql.Connection conn, int intCodTipDoc,  int intCodDoc){
 boolean blnRes=true;
 vecOD.clear();
 java.sql.Statement stmLoc;
 String strCodEmp="",strCodLoc="", strCodTipDocRec="", strCodDoc="", strCodReg="", strAbono="0", strValPen="";
 String strSql="";
 double dblValPen=0;
 int intCodRegDet=0;
 int intEstMod=0;
 int intTipMov=1;
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();



//     for(int i=0; i<tblDat.getRowCount(); i++){
//      if( !((tblDat.getValueAt(i, INT_TBL_CODREGAAJU)==null?"":tblDat.getValueAt(i, INT_TBL_CODREGAAJU).toString()).equals("")) ){
//
//          strCodEmp=tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
//          strCodLoc=tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
//          strCodTipDocRec=tblDat.getValueAt(i, INT_TBL_CODTID).toString();
//          strCodDoc=tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
//          strCodReg=tblDat.getValueAt(i, INT_TBL_CODREG).toString();
//          strCodRegAju=tblDat.getValueAt(i, INT_TBL_CODREGAAJU).toString();
//          strAbono=(tblDat.getValueAt(i, INT_TBL_ABONOORI)==null?"0":(tblDat.getValueAt(i, INT_TBL_ABONOORI).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_ABONOORI).toString()));
//
//          intEstMod=1;
//          strSqlInsDet.append(" UPDATE tbm_detpag SET  nd_abo=0,  st_reg='E', st_regrep='M' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND " +
//          " co_loc="+objZafParSis.getCodigoLocal()+" AND  co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" AND co_reg="+strCodRegAju+" ;");
//
//          strSqlInsDet.append(" UPDATE tbm_pagmovinv set nd_abo=nd_abo - "+strAbono+", st_regrep='M' " +
//          " ,tx_numchq=null,  nd_monchq=0, fe_recchq=null, fe_venchq=null WHERE " +
//          " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+" " +
//          " and co_reg="+strCodReg+" ; ");
//
//    }else{
//        strCodRegAju=tblDat.getValueAt(i, INT_TBL_CODREGAAJU).toString();
//
//
//        intEstMod=1;
//
//        strSqlInsDet.append(" UPDATE tbm_detpag SET co_tipdoccon="+strCodTipCon+", st_regrep='M' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND " +
//        " co_loc="+objZafParSis.getCodigoLocal()+" AND  co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" AND co_reg="+strCodRegAju+" ;");
//
//    }
//  }


    intCodRegDet = _getObtenerMaxCodRegDet(conn, intCodTipDoc, intCodDoc );

    for(int i=0; i<tblDat.getRowCount(); i++){
     if( ((tblDat.getValueAt(i, INT_TBL_CODREGEFE)==null?"":tblDat.getValueAt(i, INT_TBL_CODREGEFE).toString()).equals("")) ){
      if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){
       if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){

          strCodEmp=tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
          strCodLoc=tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
          strCodTipDocRec=tblDat.getValueAt(i, INT_TBL_CODTID).toString();
          strCodDoc=tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
          strCodReg=tblDat.getValueAt(i, INT_TBL_CODREG).toString();
          strAbono=(tblDat.getValueAt(i, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_ABONO).toString()));
         
          intEstMod=1;

          strValPen=(tblDat.getValueAt(i, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALPEN).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALPEN).toString()));
          dblValPen = objUti.redondear(strValPen, 2);
          if(dblValPen > 0 ) intTipMov=-1;
          else intTipMov=1;

		  
			/*CMATEO 04/NOV/2015 Usado para que no haya detalles repetidos en el cobro de facturas*/
			String strSqlVal="select 1 "+
			" from tbm_detpag as a  " +
			" WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+"  and " +
			" a.co_tipdoc="+txtCodTipDoc.getText()+"  and a.co_Doc="+intCodDoc+"  and a.st_reg='A'  and co_locpag="+ strCodLoc +" and co_tipdocpag="+strCodTipDocRec+" and co_docpag="+strCodDoc+" and co_regpag="+strCodReg+" ORDER BY a.co_reg  ";
		   
			ResultSet rstLoc=stmLoc.executeQuery(strSqlVal);
			if(!rstLoc.next()){
			/*CMATEO 04/NOV/2015 Usado para que no haya detalles repetidos en el cobro de facturas*/		  

				  // strSqlInsDet.append(
				  strSql = " INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag," +
				  " co_docpag, co_regpag, nd_abo,  st_reg, st_regrep  )" +
				  " VALUES( "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" " +
				  " ,"+intCodRegDet+", "+strCodLoc+", "+strCodTipDocRec+", "+strCodDoc+", "+strCodReg+", "+strAbono+"  * "+intTipMov+" , 'A','I' ) ; ";  //);
				  intCodRegDet++;
				  stmLoc.executeUpdate(strSql);

				  //strSqlInsDet.append(
				  strSql = " UPDATE tbm_pagmovinv set nd_abo=nd_abo + "+strAbono+" * "+intTipMov+", " +
				  " st_regrep='M' WHERE " +
				  " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+" " +
				  " and co_reg="+strCodReg+" ; "; // );
				  stmLoc.executeUpdate(strSql);



				  


				  if( objLiberaGuia._getVerificaPagTotFac(conn, strCodEmp, strCodLoc, strCodTipDocRec, strCodDoc  )){
					  
					   //strSqlInsDet.append(
					   strSql =  " UPDATE tbm_cabguirem SET " +
					   "  st_aut='A' "+
					   " ,tx_obsAut=' AUTOMATICO '  "+
					   " ,fe_aut="+objZafParSis.getFuncionFechaHoraBaseDatos()+" "+
					   " ,tx_comAut='"+objZafParSis.getNombreComputadoraConDirIP()+"' "+
					   " ,co_usrAut="+objZafParSis.getCodigoUsuario()+" "+
					   " FROM  ( " +
					   "  select co_emp, co_loc, co_tipdoc, co_doc from tbm_detguirem where " +
					   "  co_emprel="+strCodEmp+" and co_locrel="+strCodLoc+"  and  co_tipdocrel="+strCodTipDocRec+" and co_docrel="+strCodDoc+" " +
					   "  group by co_emp, co_loc, co_tipdoc, co_doc " +
					   " ) AS x " +
					   " WHERE x.co_emp= tbm_cabguirem.co_emp and x.co_loc=tbm_cabguirem.co_loc and x.co_tipdoc=tbm_cabguirem.co_tipdoc " +
					   " and x.co_doc=tbm_cabguirem.co_doc and  tbm_cabguirem.ne_numdoc=0 and tbm_cabguirem.st_aut='P' ;  ";  // );
					   stmLoc.executeUpdate(strSql);

					   INT_ENV_REC_IMP_GUIA=1;
					   
					   
					   vecOD.add(tblDat.getValueAt(i, INT_TBL_CODEMP)+"-"+tblDat.getValueAt(i, INT_TBL_CODLOC)+"-"+tblDat.getValueAt(i, INT_TBL_CODTID)+"-"+tblDat.getValueAt(i, INT_TBL_CODDOC)+"-"+tblDat.getValueAt(i, INT_TBL_NUMDOC));               
					   
				  }
			}else{
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, new Exception("Posee un cobro ya registrado"));
                break;
            }




    }}}}


//      if(intEstMod == 1 ){
//          //System.out.println("--> "+ strSqlInsDet.toString() );
//           stmLoc.executeUpdate(strSqlInsDet.toString());
//      }

      stmLoc.close();
      stmLoc=null;

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

       strSql="SELECT CASE WHEN max(co_reg) IS NULL THEN 1 ELSE max(co_reg)+1 END AS coreg FROM tbm_detpag " +
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









  /**
 * Permite saber si el documento esta anulado
 * @param conn  coneccion de la base
  @param intCodTipDoc   codigo del tipo de documento
 * @param intCodDoc      codigo de documento
 * @return  true no esta anulado   false esta anulado el registro
 */
private boolean obtenerEstAnu(java.sql.Connection conn, int intCodTipDoc, int intCodDoc){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="", strEst="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        strSql="SELECT st_reg FROM tbm_cabpag " +
        " where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" " +
        " and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and st_reg='I' ";

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
 String strSql="";
 String strFecDoc="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";

         strSql="UPDATE tbm_cabpag SET  fe_doc='"+strFecDoc+"', ne_numdoc1="+(txtAlt1.getText().trim().equals("")?null:txtAlt1.getText())+"," +
         " ne_numdoc2="+(txtAlt1.getText().trim().equals("")?null:txtAlt1.getText())+", " +
         " tx_obs1='"+txaObs1.getText()+"', tx_obs2='"+txaObs2.getText()+"', " +
         " nd_mondoc="+(valDoc.getText().equals("")?"0":valDoc.getText())+", "+
         " fe_ultmod="+objZafParSis.getFuncionFechaHoraBaseDatos()+", " +
         " co_usrmod="+objZafParSis.getCodigoUsuario()+", "+
         " tx_commod='"+objZafParSis.getNombreComputadoraConDirIP()+"', st_regrep='M' "+
         " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+intCodTipDoc+" " +
         " AND co_doc="+intCodDoc+
         //AGREGADO PARA CONTROLAR CONCURRENCIA - BLOQUEO OPTIMISTA
         " AND nd_mondoc="+dblAftModTot;
         //AGREGADO PARA CONTROLAR CONCURRENCIA - BLOQUEO OPTIMISTA
         int intResp=stmLoc.executeUpdate(strSql);
	 if(!(intResp > 0)){// si no hay registro
             MensajeInf("Otro usuario ha realizado cobros.\nFavor volver a consultar el documento.");
             blnRes=false;
         }

      stmLoc.close();
      stmLoc=null;

 }}catch(java.sql.SQLException Evt) { 
 	 blnRes=false;  
	 objUti.mostrarMsgErr_F1(this, Evt);  
 }
 catch(Exception  Evt){ 
      blnRes=false;  
	  objUti.mostrarMsgErr_F1(this, Evt); 
  }
 return blnRes;
}











     private void bloquea(javax.swing.JTextField txtFiel,  java.awt.Color colBack, boolean blnEst){
        colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }

   







 public void  clnTextos(){
    strCodTipDoc=""; strDesCodTipDoc=""; strDesLarTipDoc="";
    strCodTipDocCon=""; strDesCorTipDocCon=""; strDesLarTipDocCon="";
    strCodCtaDeb="";
    strCodCtaHab="";

    txtFecDoc.setText("");

    txtCodTipDoc.setText("");
    txtDesCodTitpDoc.setText("");
    txtDesLarTipDoc.setText("");


    valDoc.setText("0.00");
    txtFecDoc.setText("");
    txtAlt1.setText("");
   
    txtCodDoc.setText("");
    txaObs1.setText("");
    txaObs2.setText("");

    objTblMod.removeAllRows();
   
    objAsiDia.inicializar();
    objAsiDia.setEditable(true);

 }




        public boolean cancelar() {
            boolean blnRes=true;

            try {
                if (blnHayCam ) {
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
            
            dblAftModTot=verificarTotales(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
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

            _getMostrarSaldoCta();
            dblAftModTot=verificarTotales(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(),Integer.parseInt(txtCodTipDoc.getText()) , Integer.parseInt(txtCodDoc.getText()));

            return true;
        }

        public boolean afterModificar() {

           
           _getMostrarSaldoCta();
           
            dblAftModTot=verificarTotales(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(),Integer.parseInt(txtCodTipDoc.getText()) , Integer.parseInt(txtCodDoc.getText()));
           
            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
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

        abrirCon();
        if(CONN_GLO!=null) {
            STM_GLO=CONN_GLO.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY );

           strSql="Select co_emp, co_loc, co_tipdoc, co_doc  from tbm_cabpag " +
           " where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" " +
           " and st_reg not in ('E')  "+strFil+" ORDER BY  fe_doc, ne_numdoc1 ";
           System.out.println("--> "+strSql );
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
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc, rstLoc02;
    String strSql="";
    String strAux="";
    Vector vecData;
    try{
      if(conn!=null){
        stmLoc=conn.createStatement();

        /**********CARGAR DATOS DE CABEZERA ***************/

       strSql="SELECT a.st_reg, a.co_tipdoc, a1.tx_descor, a1.tx_deslar, a.co_doc, a.fe_doc, a.ne_numdoc1, a.ne_numdoc2, a.nd_mondoc, " +
       " a.tx_obs1, a.tx_obs2, a.co_mnu  from tbm_cabpag as a " +
       " INNER JOIN tbm_cabtipdoc as a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc )  " +
       " WHERE a.co_emp="+rstDatRec.getInt("co_emp")+" and a.co_loc="+rstDatRec.getInt("co_loc")+" " +
       " and a.co_tipdoc="+rstDatRec.getInt("co_tipdoc")+"  and a.co_Doc="+rstDatRec.getInt("co_doc")+" ";
       rstLoc02=stmLoc.executeQuery(strSql);
       if(rstLoc02.next()){

        txtCodTipDoc.setText( rstLoc02.getString("co_tipdoc"));
        txtDesCodTitpDoc.setText( rstLoc02.getString("tx_descor"));
        txtDesLarTipDoc.setText( rstLoc02.getString("tx_deslar"));
        txtCodDoc.setText( rstLoc02.getString("co_doc"));
        txtAlt1.setText( rstLoc02.getString("ne_numdoc1"));
      
        valDoc.setText(""+ objUti.redondear( rstLoc02.getString("nd_mondoc"), 2) );
        txaObs1.setText(rstLoc02.getString("tx_obs1"));
        txaObs2.setText(rstLoc02.getString("tx_obs2"));

        strAux=rstLoc02.getString("st_reg");

        intCodMnuDocIng=rstLoc02.getInt("co_mnu");

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

       objAsiDia.consultarDiario(rstDatRec.getInt("co_emp"), rstDatRec.getInt("co_loc"), rstDatRec.getInt("co_tipdoc"), rstDatRec.getInt("co_doc") );

       
//       cargarTiPDocCon( conn, rstDatRec.getInt("co_emp"),  rstDatRec.getInt("co_loc"),  rstDatRec.getInt("co_tipdoc"),  rstDatRec.getInt("co_doc") );

       
        /**********CARGAR DATOS DE DETALLE ***************/
        vecData = new Vector();


        strSql="select a2.co_cli, a2.tx_nomcli, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a3.tx_descor, a3.tx_deslar, " +
        " a2.ne_numdoc, a2.fe_doc ,a1.ne_diacre, a1.fe_ven, a1.nd_porret, a1.mo_pag, ( a1.mo_pag + a1.nd_abo ) as valpen, a.nd_abo, a.co_reg as coregpag " +
        " from tbm_detpag as a  " +
        " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locpag and a1.co_tipdoc=a.co_tipdocpag and a1.co_doc=a.co_docpag and a1.co_reg=a.co_regpag )  " +
        " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc )  " +
        " inner join tbm_cabtipdoc as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc  )    " +
        " WHERE a.co_emp="+rstDatRec.getInt("co_emp")+" and a.co_loc="+rstDatRec.getInt("co_loc")+"  and " +
        " a.co_tipdoc="+rstDatRec.getInt("co_tipdoc")+"  and a.co_Doc="+rstDatRec.getInt("co_doc")+"  and a.st_reg='A'  ORDER BY a.co_reg  ";

       // System.out.println("--> " +strSql);

        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

           java.util.Vector vecReg = new java.util.Vector();
             vecReg.add(INT_TBL_LINEA,"");
             vecReg.add(INT_TBL_BUTCLI,"..");
             vecReg.add(INT_TBL_CHKSEL, new Boolean(true) );
             vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
             vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nomcli") );
             vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp") );
             vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
             vecReg.add(INT_TBL_CODTID, rstLoc.getString("co_tipdoc") );
             vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
             vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg") );
             vecReg.add(INT_TBL_DCTIPDOC, rstLoc.getString("tx_descor") );
             vecReg.add(INT_TBL_DLTIPDOC, rstLoc.getString("tx_deslar") );
             vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc") );
             vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc") );
             vecReg.add(INT_TBL_DIACRE, rstLoc.getString("ne_diacre") );
             vecReg.add(INT_TBL_FECVEN, rstLoc.getString("fe_ven") );
             vecReg.add(INT_TBL_PORRET, rstLoc.getString("nd_porret") );
             vecReg.add(INT_TBL_VALDOC, rstLoc.getString("mo_pag") );
             vecReg.add(INT_TBL_VALPEN, rstLoc.getString("valpen") );
             vecReg.add(INT_TBL_ABONO,  rstLoc.getString("nd_abo") );
             vecReg.add(INT_TBL_CODREGEFE,  rstLoc.getString("coregpag") );
             vecReg.add(INT_TBL_ABONOORI,  rstLoc.getString("nd_abo") );
             vecReg.add( INT_TBL_BUTFAC, "..");

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

          _getMostrarSaldoCta();

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
    try{
    //Agregando filtro por Numero de Cotizacion


    if(!(txtCodTipDoc.getText().equals("")))
        sqlFiltro +=" and  co_tipdoc="+txtCodTipDoc.getText();
    else sqlFiltro +=" and co_tipdoc in ("+strSqlTipDocAux+") ";

    if(!txtCodDoc.getText().equals(""))
        sqlFiltro +=" and co_doc="+txtCodDoc.getText();


    if(!txtAlt1.getText().equals(""))
       sqlFiltro += " and ne_numdoc1="+txtAlt1.getText();

    if(txtFecDoc.isFecha()){
         int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
         String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
         sqlFiltro += " and fe_doc = '" +  strFecSql + "'";
     }

   }catch(Exception ev){ objUti.mostrarMsgErr_F1(this, ev);   }
    return sqlFiltro ;
}





public void clickModificar(){
 try{


  java.awt.Color colBack;
  colBack = txtCodDoc.getBackground();


  bloquea(txtCodDoc, colBack, false);
  bloquea(txtDesCodTitpDoc, colBack, false);
  bloquea(txtDesLarTipDoc, colBack, false);
  bloquea(txtSalCta, colBack, false);


//  bloquea(txtCodCli, colBack, false);
//  bloquea(txtNomCli, colBack, false);
  bloquea(valDoc, colBack, false);
//  bloquea(txtNumDoc, colBack, false);
//  bloquea(txtNomCliDes, colBack, false);
//  bloquea(txtNomCliHas, colBack, false);
//
//  bloquea(txtMinAjuEnt, colBack, false);
//  bloquea(txtMinAjuDec, colBack, false);
//  bloquea(txtMaxAjuEnt, colBack, false);
//  bloquea(txtMaxAjuDec, colBack, false);
//
//  chkMosRet.setEnabled(false);
//
  butTipDoc.setEnabled(false);
//  butCli.setEnabled(false);
//  butCons.setEnabled(false);

  butCalCam.setEnabled(true);


  setEditable(true);
  objTblMod.setDataModelChanged(false);

 blnHayCam=false;


 if( intTipModDoc == 1 ){
     MensajeInf("NO TIENE ACCESO A MODIFICAR EL DOCUMENTO.. ");
     this.setEstado('w');
 }else if( intTipModDoc == 2 ){
   if(!_estadoImpDoc())
     this.setEstado('w');
 }else if( intTipModDoc == 3 ){
    // SI PERMITE MODIFCAR 
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
 * verificar el estado de impresion  antes de insertar un nuevo documento
 * @return
 */
private boolean _getEstImpDoc(){
  boolean blnRes=true;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intCodTipDoc=0;
  try{
     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){

        stmLoc=conn.createStatement();

        intCodTipDoc=Integer.parseInt(txtCodTipDoc.getText());
     
        strSql="select  st_imp  from tbm_cabpag where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" " +
        " and co_tipdoc="+intCodTipDoc+" and ne_numdoc1 = "+(txtAlt1.getText().equals("")?"0":txtAlt1.getText())+"-1 and st_imp='N'";
        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()){
            MensajeInf("NO ESTA IMPRESO EL DOCUMENTO ANTERIOR  \nTIENE QUE REALIZAR LA IMPRESIÓN PARA PODER INSERTAR UN NUEVO DOCUMENTO..");
            blnRes=false;
        }
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;
        conn.close();
        conn=null;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
   catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}




        //******************************************************************************************************

public boolean vistaPreliminar(){

      cargarRepote(1);

//  java.sql.Connection conIns;
//            String strNomUsr="";
//            String strFecHorSer="";
//            String strNomEmp="";
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
//
//                         strNomEmp=objZafParSis.getNombreEmpresa();
//
//                        Map parameters = new HashMap();
//                        parameters.put("coemp",  new Integer(objZafParSis.getCodigoEmpresa()) );
//                        parameters.put("coloc",  new Integer(objZafParSis.getCodigoLocal()));
//                        parameters.put("cotipdoc", new Integer( Integer.parseInt(txtCodTipDoc.getText())) );
//                        parameters.put("codoc", new Integer( Integer.parseInt(txtCodDoc.getText())) );
//                        parameters.put("nomusr", strNomUsr );
//                        parameters.put("fecimp", strFecHorSer );
//                        parameters.put("nomemp", strNomEmp );
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









private void cargarRepote(int intTipo){
   if (objThrGUI==null)
    {
        objThrGUI=new ZafThreadGUI();
        objThrGUI.setIndFunEje(intTipo);
        objThrGUI.start();
    }
}




 public boolean imprimir(){

       cargarRepote(0);

// java.sql.Connection conIns;
//            String strNomUsr="";
//            String strFecHorSer="";
//            String strNomEmp="";
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
//
//                         strNomEmp=objZafParSis.getNombreEmpresa();
//
//                        Map parameters = new HashMap();
//                        parameters.put("coemp",  new Integer(objZafParSis.getCodigoEmpresa()) );
//                        parameters.put("coloc",  new Integer(objZafParSis.getCodigoLocal()));
//                        parameters.put("cotipdoc", new Integer( Integer.parseInt(txtCodTipDoc.getText())) );
//                        parameters.put("codoc", new Integer( Integer.parseInt(txtCodDoc.getText())) );
//                        parameters.put("nomusr", strNomUsr );
//                        parameters.put("fecimp", strFecHorSer );
//                        parameters.put("nomemp", strNomEmp );
//
//                        /// JasperPrint report=JasperFillManager.fillReport(DIRECCION_REPORTE, parameters,  conIns);
//                        JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
//                        JasperManager.printReport(report,false);
//
//                         cambiarEstImp( conIns, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()) );
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
            dblBefModTot=verificarTotales(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
            if (dblBefModTot==dblAftModTot){
                return true;
            }else{  
                MensajeInf("Otro usuario ha realizado cobros.\nFavor volver a consultar el documento.");
                return false;
            }
        }
        public boolean beforeVistaPreliminar() {

            return true;
        }




    }


private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblDat.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LINEA:
            strMsg="";
            break;
            

            case INT_TBL_CODCLI:
            strMsg="Código de cliente.";
            break;

            case INT_TBL_NOMCLI:
            strMsg="Nombre del cliente.";
            break;

            case INT_TBL_CODLOC:
            strMsg="Código del Local.";
            break;

            case INT_TBL_NUMDOC:
            strMsg="Número de documento.";
            break;

            case INT_TBL_FECDOC:
            strMsg="Fecha de documento.";
            break;

            case INT_TBL_FECVEN:
            strMsg="Fecha de vencimiento del cheque.";
            break;

            case INT_TBL_DIACRE:
            strMsg="Día de credito .";
            break;

            case INT_TBL_PORRET:
            strMsg="Porcentaje de retención .";
            break;

            case INT_TBL_DCTIPDOC:
            strMsg="Descripción corta del tipo de documento.";
            break;


            case INT_TBL_DLTIPDOC:
            strMsg="Descripción larga del tipo de documento.";
            break;

            case INT_TBL_VALDOC:
            strMsg="Valor del pago.";
            break;

            case INT_TBL_VALPEN:
            strMsg="Valor pendiente. ";
            break;

            case INT_TBL_ABONO:
            strMsg="Abono. ";
            break;


        default:
            strMsg="";
            break;
    }
    tblDat.getTableHeader().setToolTipText(strMsg);
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
        String strRutRpt, strNomRpt, strNomUsr="";;
        int i, intNumTotRpt;
        boolean blnRes=true;
        try
        {
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
             strNomUsr=objZafParSis.getNombreUsuario();


            if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
            {

                intNumTotRpt=objRptSis.getNumeroTotalReportes();
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
       strSql="UPDATE tbm_cabpag SET st_imp='S' WHERE  co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+"" +
       " AND co_doc="+intCodDoc+" ";
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



private void generaOD(Connection con,Vector vecFac) throws SQLException{

    ZafImp oi=new ZafImp();
    ZafMetImp om;
    boolean resp=false;             
        //for (int i = 0; i < tblDat.getRowCount()-1; i++) {
        //for(int i=0; i< vecFac.size();i++){
          Iterator itVecFac=vecFac.iterator();
          while(itVecFac.hasNext()){
            String strDat=(String)itVecFac.next();
            //String strDat=(String)vecFac.get(i);
            String[] strArrDat=strDat.split("-");
                        
            int intCodEmp=Integer.parseInt((String)strArrDat[0]);
            int intCodLoc=Integer.parseInt((String)strArrDat[1]);
            int intCodTipDoc2=Integer.parseInt((String)strArrDat[2]);
            int intCodDocu=Integer.parseInt((String)strArrDat[3]);
            int intNumDoc=Integer.parseInt((String)strArrDat[4]);
              
            oi.setEmp(intCodEmp);
            //oi.setEmp(objZafParSis.getCodigoEmpresa());
            //oi.setLoc(objZafParSis.getCodigoLocal());
            //oi.setLoc(Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODLOC).toString()));
            oi.setLoc(intCodLoc);
            oi.setTipdoc(intCodTipDoc2);
            oi.setNumdoc(intNumDoc);
            oi.setCoDoc(intCodDocu);
            //oi.setNumdoc(Integer.parseInt(tblDat.getValueAt(i, INT_TBL_NUMDOC).toString()));
            //oi.setTipdoc(Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODTID).toString()));
            om=new ZafMetImp(oi);
            //resp=om.validarOD(con);
            boolean resp2 = om.validarExiRegOD(con);
            if(resp2==false){// CUANDO ES UNA NUEVA FACTURA POR VOLVER A FACTURAR : ESTA FACTURA NO POSEE REGISTRO EN TBM_CABGUIREM
               ResultSet rs=om.obtenerFacOrg(con, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu);//OBTENER LA FACTURA ORIGEN Y VERIFICAR SI SE DEBE GENERAR OD
               if(rs!=null && rs.next()){
                   ZafImp objImp=new ZafImp();
                   objImp.setEmp(rs.getInt("co_emp"));
                   objImp.setLoc(rs.getInt("co_loc"));
                   objImp.setTipdoc(rs.getInt("co_tipdoc"));
                   objImp.setCoDoc(rs.getInt("co_doc"));
                   ZafMetImp objMetImp=new ZafMetImp(objImp);
                   boolean booRes=objMetImp.validarOD(con);
                   if(!booRes){
                    objMetImp.impresionNormal2(con);
                   }
               }            
            }
            else if (!(om.validarOD(con))) {
                om.impresionNormal2(con);
            }
            
        }//fin de for  

}//fin de generaOd

}
