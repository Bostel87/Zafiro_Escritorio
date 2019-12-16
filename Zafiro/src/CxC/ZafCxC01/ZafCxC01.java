/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCxC01.java
 *
 * Created on Jun 18, 2010, 10:51:48 AM
 */
  
package CxC.ZafCxC01;

import Librerias.ZafAjuCenAut.ZafAjuCenAut;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafVenCon.ZafVenConCxC01;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author jayapata
 * 
 * RETENCIONES RECIBIDAS
 */
public class ZafCxC01 extends javax.swing.JInternalFrame {
  private ZafParSis objZafParSis;
  private ZafUtil objUti; /**/
  private ZafDatePicker txtFecDoc;
  private ZafTblMod objTblMod, objTblModRet; /**/
  private ZafTblCelRenLbl objTblCelRenLbl;
  private ZafTblCelRenBut objTblCelRenBut;
  private ZafTblCelRenChk objTblCelRenChk;
  private ZafTblCelEdiChk objTblCelEdiChk;
  private ZafTblCelEdiTxt objTblCelEdiTxt, objTblCelEdiTxtNumRetChq;
  private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoTipDoc, objTblCelEdiTxtVcoBan, objTblCelEdiTxtVcoFacRet;
  private ZafTblCelEdiButGen objTblCelEdiButGen;      //Editor: JButton en celda.
  private ZafAsiDia objAsiDia; // Asiento de Diario
  private ZafAjuCenAut objAjuCenAut;
  private ZafTblPopMnu objTblPopMnu;
  private java.util.Date datFecAux;
  private mitoolbar objTooBar;
  private ZafThreadGUI objThrGUI;
  private ZafRptSis objRptSis;                                //Reportes del Sistema.
  private boolean blnMarTodCanTblDat=true;
 
  private ZafVenCon objVenConTipdoc;
  private ZafVenCon objVenConTipdocCon;
  private ZafVenConCxC01 objVenConCxC01;
  private ZafVenCon objVenConBan;
  private ZafVenCon objVenConFac;
  private ZafVenCon vcoRet;
  private Connection CONN_GLO=null;
  private Statement  STM_GLO=null;
  private ResultSet  rstCab=null;

  private String strVersion=" v. 2.13 ";
  private String strCodTipDoc="";
  private String strDesCodTipDoc="";
  private String strDesLarTipDoc="";
  private String strCodTipDocCon="";
  private String strDesCorTipDocCon="";
  private String strDesLarTipDocCon="";
  private String strSqlTipDocAux="";

  private String strCodCtaDeb="";
  private String strTxtCodCtaDeb="";
  private String strNomCtaDeb="";
  private String strCodCtaHab="";
  private String strTxtCodCtaHab="";
  private String strNomCtaHab="";
  private StringBuffer strSqlInsDet;
  private StringBuffer strSqlDetUpdCli;
  private String strFormatoFecha="d/m/y";
  private StringBuffer stbFacSel;

  private static final int INT_TBL_LINEA=0;  // NUMERO DE LINEAS
  private static final int INT_TBL_CHKSEL=1; // SELECCION  DE FILA
  private static final int INT_TBL_BUTCLI=2; // BUTON PARA BUSCAR DOCUMENTO
  private static final int INT_TBL_CODCLI=3; // CODIGO CLIENTE
  private static final int INT_TBL_NOMCLI=4; // NOMBRE CLIENTE
  private static final int INT_TBL_CODEMP=5; // CODIGO EMPRESA
  private static final int INT_TBL_CODLOC=6; // CODIGO DEL LOCAL
  private static final int INT_TBL_CODTID=7; // CODIGO TIPO DE DOCUMENTO
  private static final int INT_TBL_CODDOC=8; // CODIGO DOCUMENTAL
  private static final int INT_TBL_CODREG=9; // CODIGO REGISTRO
  private static final int INT_TBL_DCTIPDOC=10; // DESCRIPCION CORTA TIPO DOCUMENTO
  private static final int INT_TBL_DLTIPDOC=11; // DESCRIPCION LARGA TIPO DOCUMENTO
  private static final int INT_TBL_NUMDOC=12; // NUMERO DOCUMENTO
  private static final int INT_TBL_FECDOC=13; // FECHA DOCUMENTO
  private static final int INT_TBL_DIACRE=14; // DIA DE CREDITO
  private static final int INT_TBL_FECVEN=15; // FECHE VENCIMIENTO DOCUMENTO
  private static final int INT_TBL_PORRET=16; // PORCENTAJE DE RETENCION
  private static final int INT_TBL_VALDOC=17; // VALOR DOCUMENTO
  private static final int INT_TBL_VALPEN=18; // VALOR PENDIENTE
  private static final int INT_TBL_ABONO=19; //  ABONO
  private static final int INT_TBL_CODREGAAJU=20; // CODIGO REGISTRO DE PAGO
  private static final int INT_TBL_ABONOORI=21; //  ABONO ORIGEN
  private static final int INT_TBL_CODTIPDOC=22; // CODIGO TIPO DE DOCUMENTO
  private static final int INT_TBL_DCOTIPDOC=23; // DESCRIPCION CORTA DE TIPO DOCUMENTO
  private static final int INT_TBL_BUTTIPDOC=24;  // BOTON DE BUSQUEDA DE  TIPO DOCUMENTO
  private static final int INT_TBL_DLATIPDOC=25; // DESCRIPCION LARGA TIPO DE DOCUMENTO
  private static final int INT_TBL_CODBAN=26;  // CODIGO DEL BANCO
  private static final int INT_TBL_DSCBAN=27;  // DESCRIPCION CORTA DEL BANCO
  private static final int INT_TBL_BUTBAN=28;  // BOTON PARA BUSCAR BANCOS
  private static final int INT_TBL_NOMBAN=29;  // NOMBRE DEL BANCO
  private static final int INT_TBL_NUMCTA=30;  // NUMERO DE CUENTA DEL BANCO
  private static final int INT_TBL_BUTRET=31; // BUTON PARA BUSCAR RETENCION
  private static final int INT_TBL_IDEEMI=32;  // IDENTIFICACION DEL EMISOR
  private static final int INT_TBL_NUMCHQ=33;  // NUMERO DEL CHEQUE/RETENCION
  private static final int INT_TBL_FECVENCHQ=34;  // FECHA VENCIMIENTO EL CHEQUE
  private static final int INT_TBL_VALCHQ=35;  // VALOR DEL CHEQUE
  private static final int INT_TBL_CODCTADEB=36;
  private static final int INT_TBL_DCOCTADEB=37;
  private static final int INT_TBL_NOMCTADEB=38;
  private static final int INT_TBL_CODCTAHAB=39;
  private static final int INT_TBL_DCOCTAHAB=40;
  private static final int INT_TBL_NOMCTAHAB=41;
  private static final int INT_TBL_NUMSECDOC=42;  // NUMERO DE SERIE DEL DOCUMENTO
  private static final int INT_TBL_NUMAUTSRI=43;  // NUMERO DE AUTORIZACION DEL SRI
  private static final int INT_TBL_FECCATDOC=44;  // FECHA CADUCIDA DEL DOCUMENTO
  private static final int INT_TBL_CODSRIDOC=45;  // CODIGO DE SRI

  private int intCodMnuDocIng=0;

  private double dblMinAjuCenAut=0;
  private double dblMaxAjuCenAut=0;

  private JTextField txtCodTipDoc= new JTextField();
  private JTextField txtCodTipDocCon= new JTextField();

  private Vector vecCab=new Vector();    //Almacena las cabeceras  /**/
  private Vector vecDetDiario;
  
  private boolean blnHayCam=false;  
  
  private String strCodEmp="";
  private String strCodLoc="";
  private String strCodTip="";
  private String strCodDoc="";
  private String strGloNumAutSri, strIdeEmi, strNumSer, strNumRet;
  private boolean blnEstCar=false;
  private boolean blnBtnCanCarArcRet; //Var. booleana que indica si se presiono el Boton <Cancelar> en la ventana de Carga de Archivo de Retenciones.
  private ZafTblCelEdiTxt objTblCelEdiNumRet;
    

    /** Creates new form ZafCxC01 */
    public ZafCxC01(Librerias.ZafParSis.ZafParSis obj) {
        try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();

            this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
            lblTit.setText(objZafParSis.getNombreMenu());  /**/

	     objUti = new ZafUtil(); /**/
	     objTooBar = new mitoolbar(this);
	     this.getContentPane().add(objTooBar,"South");
             objTooBar.agregarBoton(butCarArcRet);
             butCarArcRet.setEnabled(true);

             objAjuCenAut = new  Librerias.ZafAjuCenAut.ZafAjuCenAut(this, objZafParSis);

             objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);


             objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia(this.objZafParSis);
             objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
             @Override
             public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                 if (txtCodTipDoc.getText().equals(""))
                     objAsiDia.setCodigoTipoDocumento(-1);
                 else
                    objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
              }
            });

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


    public ZafCxC01(Librerias.ZafParSis.ZafParSis obj, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodMnu) {
        try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();

            this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
            lblTit.setText(objZafParSis.getNombreMenu());  /**/

            objZafParSis.setCodigoMenu(intCodMnu);

             objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);


	     objUti = new ZafUtil(); /**/
	     objTooBar = new mitoolbar(this);
	    
             objAjuCenAut = new  Librerias.ZafAjuCenAut.ZafAjuCenAut(this, objZafParSis);

             strCodEmp= String.valueOf( intCodEmp );
             strCodLoc= String.valueOf( intCodLoc );
             strCodTip= String.valueOf( intCodTipDoc );
             strCodDoc= String.valueOf( intCodDoc );
             blnEstCar=true;

             objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia(this.objZafParSis);
             objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
             @Override
             public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                 if (txtCodTipDoc.getText().equals(""))
                     objAsiDia.setCodigoTipoDocumento(-1);
                 else
                    objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
              }
            });

       
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


///**
// * Esta funcion permite extraer los rangos de ajuste de centavos automativos
// * @return true si no hay problema false por algun error.
// */
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
    configurarVenConTipDocCon();
    configurarVenConDocAbi();
    configurarVenConBanco();

    cargarRangoAjuCenAut();

    configurarVenConFacturas();

    if(objZafParSis.getCodigoMenu()==1648)  ConfigurarTabla();  // emisión de retenciones.
    else if(objZafParSis.getCodigoMenu()==256) ConfigurarTablaCobroMasivo();
    else  ConfigurarTablaPagoMasivo();

    if (objZafParSis.getCodigoMenu() == 1648)
    {  //Emision de retenciones
       butCarArcRet.setVisible(true);
    }
    else
       butCarArcRet.setVisible(false);

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
        txtAlt2.setText( rstLoc02.getString("ne_numdoc2"));
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


       cargarTiPDocCon( conn, Integer.parseInt(strCodEmp), Integer.parseInt(strCodLoc), Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc) );


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
/*
 * MODIFICADO EFLORESA 2012-03-23
 * Retenciones Recibidas:
 * Al consultar el detalle se agrega que busque en los campos TX_NUMSER, TX_NUMAUTSRI, TX_FECCAD, TXCODSRI de la tabla TBM_PAGMOVINV
 *
*/
private boolean cargarDetReg(Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc   ){
    boolean blnRes=false;
    Statement stmLoc, stmLoc01;
    ResultSet rstLoc;
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

        /*strSql="SELECT a2.tx_numautsri, a2.tx_secdoc, a2.tx_feccad, a2.tx_codsri, " +
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
        " ORDER BY a.co_reg ";*/

        /*strSql="SELECT case when a2.tx_numautsri is null then a1.tx_numautsri else a2.tx_numautsri end as tx_numautsri, " + 
        " case when a2.tx_secdoc is null then a1.tx_numser else a2.tx_secdoc end as tx_secdoc, " +
        " case when a2.tx_feccad is null then a1.tx_feccad else a2.tx_feccad end as tx_feccad, " + 
        " case when a2.tx_codsri is null then a1.tx_codsri else a2.tx_codsri end as tx_codsri, " +
        " a1.tx_numctachq, a1.nd_monchq, a1.co_banchq, ban.tx_deslar as dlbanco,  a1.fe_venchq, a1.tx_numchq, a.co_reg as coregpag, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg "+sqlAuxDif+"  " +
        " ,a2.co_cli, a2.tx_nomcli, a3.tx_descor, a3.tx_deslar, a2.ne_numdoc, a2.fe_doc, a1.ne_diacre, a1.nd_porret, a1.fe_ven, a1.mo_pag, a.nd_abo " +
        " ,a.co_tipdoccon, a4.tx_descor as txdctipdoc, a4.tx_deslar as txdltipdoc " +
        " ,a4.co_ctadeb, a5.tx_codcta AS txctadeb, a5.tx_deslar as nomctadeb,  a4.co_ctahab, a6.tx_codcta as txctahab, a6.tx_deslar as nomctahab " +
        " FROM tbm_detpag as a " +
        " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locpag and a1.co_tipdoc=a.co_tipdocpag and a1.co_doc=a.co_docpag and a1.co_reg=a.co_regpag ) " +
        " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) " +
        " inner join tbm_cabtipdoc as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc  ) " +
        " inner join tbm_cabtipdoc as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoccon  ) " +
        " inner join tbm_placta as a5 on (a5.co_emp=a4.co_emp and a5.co_cta=a4.co_ctadeb ) " +
        " inner join tbm_placta as a6 on (a6.co_emp=a4.co_emp and a6.co_cta=a4.co_ctahab ) " +
        " LEFT  JOIN tbm_var as ban ON (ban.co_reg=a.co_banchq ) " +
        " WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" " +
        " and a.co_tipdoc="+strCodTipDoc+"  and a.co_Doc="+strCodDoc+" and a.st_reg='A' "+
        " ORDER BY a.co_reg ";*/
         
        strSql="SELECT case when a2.tx_numautsri is null then a1.tx_numautsri else a2.tx_numautsri end as tx_numautsri, " + 
        " case when a2.tx_secdoc is null then a1.tx_numser else a2.tx_secdoc end as tx_secdoc, " +
        " case when a2.tx_feccad is null then a1.tx_feccad else a2.tx_feccad end as tx_feccad, " + 
        " case when a1.tx_codsri is null then a2.tx_codsri else a1.tx_codsri end as tx_codsri, " +
        " a1.tx_numctachq, a1.nd_monchq, a1.co_banchq, ban.tx_deslar as dlbanco, a1.fe_venchq, a1.tx_numchq, a.co_reg as coregpag, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg "+sqlAuxDif+"  " +
        " ,a2.co_cli, a2.tx_nomcli, a3.tx_descor, a3.tx_deslar, a2.ne_numdoc, a2.fe_doc, a1.ne_diacre, a1.nd_porret, a1.fe_ven, a1.mo_pag, a.nd_abo " +
        " ,a.co_tipdoccon, a4.tx_descor as txdctipdoc, a4.tx_deslar as txdltipdoc " +
        " ,a4.co_ctadeb, a5.tx_codcta AS txctadeb, a5.tx_deslar as nomctadeb,  a4.co_ctahab, a6.tx_codcta as txctahab, a6.tx_deslar as nomctahab " +
        " FROM tbm_detpag as a " +
        " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locpag and a1.co_tipdoc=a.co_tipdocpag and a1.co_doc=a.co_docpag and a1.co_reg=a.co_regpag ) " +
        " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) " +
        " inner join tbm_cabtipdoc as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc  ) " +
        " inner join tbm_cabtipdoc as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoccon  ) " +
        " inner join tbm_placta as a5 on (a5.co_emp=a4.co_emp and a5.co_cta=a4.co_ctadeb ) " +
        " inner join tbm_placta as a6 on (a6.co_emp=a4.co_emp and a6.co_cta=a4.co_ctahab ) " +
        " LEFT  JOIN tbm_var as ban ON (ban.co_reg=a.co_banchq ) " +
        " WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" " +
        " and a.co_tipdoc="+strCodTipDoc+"  and a.co_Doc="+strCodDoc+" and a.st_reg='A' "+
        " ORDER BY a.co_reg ";
        
        System.out.println("ZafCxC01.cargarDetReg: " + strSql);
        
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

           java.util.Vector vecReg = new java.util.Vector();
             vecReg.add(INT_TBL_LINEA,"");
             vecReg.add(INT_TBL_CHKSEL, true);
             vecReg.add(INT_TBL_BUTCLI,"..");
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
             vecReg.add(INT_TBL_CODREGAAJU,  rstLoc.getString("coregpag") );
             vecReg.add(INT_TBL_ABONOORI,  rstLoc.getString("nd_abo") );
             vecReg.add(INT_TBL_CODTIPDOC, rstLoc.getString("co_tipdoccon") );
             vecReg.add(INT_TBL_DCOTIPDOC, rstLoc.getString("txdctipdoc") );
             vecReg.add(INT_TBL_BUTTIPDOC,"");
             vecReg.add(INT_TBL_DLATIPDOC, rstLoc.getString("txdltipdoc") );
             vecReg.add(INT_TBL_CODBAN, rstLoc.getString("co_banchq") );
             vecReg.add(INT_TBL_DSCBAN,"");
             vecReg.add(INT_TBL_BUTBAN,"");
             vecReg.add(INT_TBL_NOMBAN, rstLoc.getString("dlbanco") );
             vecReg.add(INT_TBL_NUMCTA, rstLoc.getString("tx_numctachq") );
             vecReg.add(INT_TBL_BUTRET,"..");
             vecReg.add(INT_TBL_IDEEMI, "");
             vecReg.add(INT_TBL_NUMCHQ, rstLoc.getString("tx_numchq") );

             if(!(rstLoc.getString("fe_venchq")==null))
             vecReg.add(INT_TBL_FECVENCHQ, objUti.formatearFecha(rstLoc.getDate("fe_venchq"), "dd/MM/yyyy") );
             else vecReg.add(INT_TBL_FECVENCHQ, "" );

             vecReg.add(INT_TBL_VALCHQ,  rstLoc.getString("nd_monchq") );
             vecReg.add(INT_TBL_CODCTADEB,  rstLoc.getString("co_ctadeb") );
             vecReg.add(INT_TBL_DCOCTADEB,  rstLoc.getString("txctadeb") );
             vecReg.add(INT_TBL_NOMCTADEB,  rstLoc.getString("nomctadeb") );
             vecReg.add(INT_TBL_CODCTAHAB,  rstLoc.getString("co_ctahab") );
             vecReg.add(INT_TBL_DCOCTAHAB,  rstLoc.getString("txctahab") );
             vecReg.add(INT_TBL_NOMCTAHAB,  rstLoc.getString("nomctahab") );

             vecReg.add(INT_TBL_NUMSECDOC, rstLoc.getString("tx_secdoc") );
             vecReg.add(INT_TBL_NUMAUTSRI, rstLoc.getString("tx_numautsri") );
             vecReg.add(INT_TBL_FECCATDOC, rstLoc.getString("tx_feccad") );
             vecReg.add(INT_TBL_CODSRIDOC, rstLoc.getString("tx_codsri") );

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

}}catch(SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
 catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

return blnRes;
}


private void cargarTiPDocCon(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
   java.sql.Statement stmLoc;
   java.sql.ResultSet rstLoc;
   String strSql="";
   try{
      if(conn!=null){
        stmLoc=conn.createStatement();

       strSql="SELECT a.co_tipdoccon, a1.tx_descor, a1.tx_deslar " +
       " ,a1.co_ctadeb, a3.tx_codcta AS txctadeb, a3.tx_deslar as nomctadeb,  a1.co_ctahab, a4.tx_codcta as txctahab, a4.tx_deslar as nomctahab  " +
       " ,( select count(distinct(x.co_tipdoccon)) from tbm_detpag as x where x.co_emp = "+intCodEmp+"  and x.co_loc= "+intCodLoc+" and x.co_tipdoc="+intCodTipDoc+" and x.co_doc="+intCodDoc+" and x.st_reg='A'  ) as cantipdoccon " +
       " FROM tbm_detpag as a " +
       " inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoccon ) " +
       " LEFT JOIN tbm_placta AS a3 on (a3.co_emp = a1.co_emp and a3.co_cta = a1.co_ctadeb )  " +
       " LEFT JOIN tbm_placta AS a4 on (a4.co_emp = a1.co_emp and a4.co_cta = a1.co_ctahab ) " +
       " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.st_reg='A' " +
       " GROUP BY a.co_tipdoccon, a1.tx_descor, a1.tx_deslar " +
       "  ,a1.co_ctadeb, a3.tx_codcta , a3.tx_deslar ,  a1.co_ctahab, a4.tx_codcta , a4.tx_deslar   ";
       
       System.out.println("ZafCxC01.cargarTiPDocCon: " + strSql);
       
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next()){

          if( rstLoc.getInt("cantipdoccon") == 1 ){
              rdaAplMisCta.setSelected(true);
              txtCodTipDocCon.setText(rstLoc.getString("co_tipdoccon"));
              txtDesCorTipDocCon.setText(rstLoc.getString("tx_descor"));
              txtDesLarTipDocCon.setText(rstLoc.getString("tx_deslar"));
              txtDesCorTipDocCon.setEditable(true);
              txtDesLarTipDocCon.setEditable(true);
              butTipDocCon.setEnabled(true);

              strCodCtaDeb=rstLoc.getString("co_ctadeb");
              strTxtCodCtaDeb=rstLoc.getString("txctadeb");
              strNomCtaDeb=rstLoc.getString("nomctadeb");
              strCodCtaHab=rstLoc.getString("co_ctahab");
              strTxtCodCtaHab=rstLoc.getString("txctahab");
              strNomCtaHab=rstLoc.getString("nomctahab");

              ocultaCol(INT_TBL_DCOTIPDOC);
              ocultaCol(INT_TBL_BUTTIPDOC);
              ocultaCol(INT_TBL_DLATIPDOC);

          }else{

             rdaAplDifCta.setSelected(true);
             MostrarCol(INT_TBL_DCOTIPDOC,50);
             MostrarCol(INT_TBL_BUTTIPDOC,20);
             MostrarCol(INT_TBL_DLATIPDOC,150);

             java.awt.Color colBack;
             colBack = txtDesCorTipDocCon.getBackground();
             txtDesCorTipDocCon.setEditable(false);
             txtDesCorTipDocCon.setBackground(colBack);
             txtDesLarTipDocCon.setEditable(false);
             txtDesLarTipDocCon.setBackground(colBack);
             butTipDocCon.setEnabled(false);

             txtCodTipDocCon.setText("");
             txtDesCorTipDocCon.setText("");
             txtDesLarTipDocCon.setText("");

             strCodCtaDeb="";
             strTxtCodCtaDeb="";
             strNomCtaDeb="";
             strCodCtaHab="";
             strTxtCodCtaHab="";
             strNomCtaHab="";

          }

       }
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;

 }}catch(java.sql.SQLException Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  }
   catch(Exception Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  }
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
        arlCam.add("a.tx_numserret");
        arlCam.add("a.tx_numautsriret");
        arlCam.add("a.tx_feccadret");
        arlCam.add("a.tx_codsriret");
  
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
        arlAli.add("tx_numserret");
        arlAli.add("tx_numautsriret");
        arlAli.add("tx_feccadret");
        arlAli.add("tx_codsriret");


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
        if(objZafParSis.getCodigoMenu()==1648)  strAuxFil=" AND a3.ne_mod IN (1, 3)  AND a2.nd_porRet>0  ";
        if(objZafParSis.getCodigoMenu()==256)  strAuxFil=" AND a3.ne_mod IN (1, 3)  AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0) ";
        if(objZafParSis.getCodigoMenu()==488)  strAuxFil=" AND a3.ne_mod IN (2, 4)  ";

        

        strSQL="SELECT * FROM ( SELECT  a1.co_cli, a1.tx_nomcli, a1.co_emp, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, a2.mo_pag, a2.nd_abo " +
        " , (a2.mo_pag+a2.nd_abo) AS nd_pen, a2.st_sop, a2.st_entSop, a2.st_pos, a2.co_banChq, a4.tx_desLar AS a4_tx_desLar, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq " +
        " , a2.nd_monChq, b1.tx_numSerRet, b1.tx_numAutSRIRet, b1.tx_fecCadRet, b1.tx_codSRIRet " +
        " FROM  tbm_cabMovInv AS a1  " +
        " INNER JOIN tbm_cli AS b1 ON (a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli) " +
        " INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) " +
        " INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)  " +
        " LEFT OUTER JOIN tbm_var AS a4 ON (a2.co_banChq=a4.co_reg) " +
        " WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+"   "+strAux+" "+
        " AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C')  "+strAuxFil+" " +
        " AND (a2.mo_pag+a2.nd_abo)<>0 " +
        " ) AS a ORDER BY co_emp, co_loc, co_tipDoc, co_doc, co_reg ";

       // System.out.println("->"+strSQL);

        objVenConFac=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol, intColOcu );
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

        objVenConFac.setConfiguracionColumna(14, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objZafParSis.getFormatoNumero(),false,true);
            

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

    ///cobros masivos//
    if(objZafParSis.getCodigoMenu()== 256)
      objVenConCxC01.setTipoConsulta(1);
     else{
        ///Retenciones Recibidas///
        if(objZafParSis.getCodigoMenu()!= 488)
           objVenConCxC01.setTipoConsulta(3);
        else  ///Pagos Masivos///
           objVenConCxC01.setTipoConsulta(4);
      }
  }catch (Exception e){   blnRes=false;   objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}


private boolean ConfigurarTablaPagoMasivo() {
 boolean blnRes=false;
 try{
     //Configurar JTable: Establecer el modelo.
        vecCab.clear();
        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_CHKSEL,"");
        vecCab.add(INT_TBL_BUTCLI,"");
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
        vecCab.add(INT_TBL_CODREGAAJU,"");
        vecCab.add(INT_TBL_ABONOORI,"");
        vecCab.add(INT_TBL_CODTIPDOC,"");
        vecCab.add(INT_TBL_DCOTIPDOC,"DC.Tip.Doc");
        vecCab.add(INT_TBL_BUTTIPDOC,"");
        vecCab.add(INT_TBL_DLATIPDOC,"DL.Tip.Doc");
        vecCab.add(INT_TBL_CODBAN,"Cód.Ban.");
        vecCab.add(INT_TBL_DSCBAN,"Banco.");
        vecCab.add(INT_TBL_BUTBAN,"");
        vecCab.add(INT_TBL_NOMBAN,"Nom.Ban");
        vecCab.add(INT_TBL_NUMCTA,"Num.Cta.");
        vecCab.add(INT_TBL_BUTBAN,"Num.Chq.");
        vecCab.add(INT_TBL_IDEEMI,"Ide.Emi.");
        vecCab.add(INT_TBL_NUMCHQ,"Num.Chq.");
        vecCab.add(INT_TBL_FECVENCHQ,"Fec.Ven.Chq.");
        vecCab.add(INT_TBL_VALCHQ,"Val.Chq.");
        vecCab.add(INT_TBL_CODCTADEB,"deb");
        vecCab.add(INT_TBL_DCOCTADEB,"deb");
        vecCab.add(INT_TBL_NOMCTADEB,"deb");
        vecCab.add(INT_TBL_CODCTAHAB,"hab");
        vecCab.add(INT_TBL_DCOCTAHAB,"hab");
        vecCab.add(INT_TBL_NOMCTAHAB,"hab");
        vecCab.add(INT_TBL_NUMSECDOC,"Num.Sec.Doc");
        vecCab.add(INT_TBL_NUMAUTSRI,"Num.Aut.Sri");
        vecCab.add(INT_TBL_FECCATDOC,"Fec.Cad.Doc");
        vecCab.add(INT_TBL_CODSRIDOC,"Cod.Sri");


	objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        ZafColNumerada zafColNumerada = new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);

        //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
        objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());
        tblDat.getTableHeader().setReorderingAllowed(false);

	//Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();  /**/

        objTblMod.setColumnDataType(INT_TBL_DIACRE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_PORRET, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_VALDOC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_VALPEN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_ABONO, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_VALCHQ, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_NUMDOC, ZafTblMod.INT_COL_DBL, new Integer(0), null);

        objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_DIACRE).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_PORRET).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_VALDOC).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_VALPEN).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_VALCHQ).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;


        objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_ABONO).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;

         //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        ZafMouMotAda objMouMotAda=new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

         //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CHKSEL).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_BUTCLI).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(40);
        tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_DCTIPDOC).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DLTIPDOC).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DIACRE).setPreferredWidth(35);
        tcmAux.getColumn(INT_TBL_FECVEN).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_PORRET).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_VALDOC).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_VALPEN).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_ABONO).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DSCBAN).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_BUTBAN).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_NOMBAN).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_NUMCTA).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_FECVENCHQ).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_BUTRET).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_IDEEMI).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_NUMCHQ).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_VALCHQ).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DCOTIPDOC).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_BUTTIPDOC).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_DLATIPDOC).setPreferredWidth(100);

        tcmAux.getColumn(INT_TBL_FECVENCHQ).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));


        /* Aqui se agrega las columnas que van
             ha hacer ocultas
         * */
        ArrayList arlColHid=new ArrayList();
        arlColHid.add(""+INT_TBL_CODEMP);
        arlColHid.add(""+INT_TBL_CODTID);
        arlColHid.add(""+INT_TBL_CODDOC);
        arlColHid.add(""+INT_TBL_CODREG);
        arlColHid.add(""+INT_TBL_CODREGAAJU);
        arlColHid.add(""+INT_TBL_ABONOORI);
        arlColHid.add(""+INT_TBL_DIACRE);
        arlColHid.add(""+INT_TBL_FECVEN);
        arlColHid.add(""+INT_TBL_CODBAN);
        arlColHid.add(""+INT_TBL_CODTIPDOC);
        arlColHid.add(""+INT_TBL_DCOTIPDOC);
        arlColHid.add(""+INT_TBL_BUTTIPDOC);
        arlColHid.add(""+INT_TBL_DLATIPDOC);
        arlColHid.add(""+INT_TBL_CODCTADEB);
        arlColHid.add(""+INT_TBL_DCOCTADEB);
        arlColHid.add(""+INT_TBL_NOMCTADEB);
        arlColHid.add(""+INT_TBL_CODCTAHAB);
        arlColHid.add(""+INT_TBL_DCOCTAHAB);
        arlColHid.add(""+INT_TBL_NOMCTAHAB);
        arlColHid.add(""+INT_TBL_DSCBAN);
        arlColHid.add(""+INT_TBL_BUTRET);
        arlColHid.add(""+INT_TBL_IDEEMI);
        arlColHid.add(""+INT_TBL_NUMSECDOC);
        arlColHid.add(""+INT_TBL_NUMAUTSRI);
        arlColHid.add(""+INT_TBL_FECCATDOC);
        arlColHid.add(""+INT_TBL_CODSRIDOC);
        objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;

        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_CHKSEL);
        vecAux.add("" + INT_TBL_BUTCLI);
        vecAux.add("" + INT_TBL_ABONO);
        vecAux.add("" + INT_TBL_DSCBAN);
        vecAux.add("" + INT_TBL_BUTBAN);
        vecAux.add("" + INT_TBL_NOMBAN);
        vecAux.add("" + INT_TBL_NUMCTA);
        vecAux.add("" + INT_TBL_NUMCHQ);
        vecAux.add("" + INT_TBL_DCOTIPDOC);
        vecAux.add("" + INT_TBL_BUTTIPDOC);
        vecAux.add("" + INT_TBL_FECVENCHQ);
        vecAux.add("" + INT_TBL_NUMDOC);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;
        
        ZafTblEdi zafTblEdi = new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

        objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_CHKSEL).setCellRenderer(objTblCelRenChk);
        objTblCelRenChk=null;


        objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_CHKSEL).setCellEditor(objTblCelEdiChk);
        objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            @Override
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

            }
            @Override
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
              int intNunFil = tblDat.getSelectedRow();

               if(tblDat.getValueAt(intNunFil, INT_TBL_CHKSEL).toString().equals("true")){

                if( tblDat.getValueAt(intNunFil, INT_TBL_CODREGAAJU)==null){
                  //String strValPen = (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString()));
                  double dblValPen = objUti.redondear( (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString())), 2);
                  dblValPen = Math.abs(dblValPen);

                  tblDat.setValueAt(""+dblValPen, intNunFil, INT_TBL_ABONO);
                  tblDat.setValueAt(""+dblValPen, intNunFil, INT_TBL_VALCHQ);
                }else{
                    tblDat.setValueAt( tblDat.getValueAt(intNunFil, INT_TBL_ABONOORI), intNunFil, INT_TBL_ABONO);
                    tblDat.setValueAt( tblDat.getValueAt(intNunFil, INT_TBL_ABONOORI), intNunFil, INT_TBL_VALCHQ);
                }
               }else{
                    tblDat.setValueAt("0", intNunFil, INT_TBL_ABONO);
                    tblDat.setValueAt("0", intNunFil, INT_TBL_VALCHQ);
               }

              calculaTotMonAbo();
            }
        });


    objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
    tcmAux.getColumn(INT_TBL_ABONO).setCellEditor(objTblCelEdiTxt);
    objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        @Override
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
           int intNumFil = tblDat.getSelectedRow();

           String strCodCli=  (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
           if((strCodCli.equals("")))
               objTblCelEdiTxt.setCancelarEdicion(true);


           if( !(tblDat.getValueAt(intNumFil, INT_TBL_CODREGAAJU)==null))
               objTblCelEdiTxt.setCancelarEdicion(true);



        }
        @Override
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
          int intNunFil = tblDat.getSelectedRow();

            double dblValApl = objUti.redondear( (tblDat.getValueAt(intNunFil, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_ABONO).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_ABONO).toString())), 4);
            double dblValPen = objUti.redondear( (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString())), 4);

            dblValPen = Math.abs(dblValPen);


          if(dblValApl > dblValPen){
                MensajeInf("EL VALOR QUE ESTA APLICANDO ES MAYOR AL VALOR PENDIENTE..");
                tblDat.setValueAt( false, intNunFil, INT_TBL_CHKSEL );
                tblDat.setValueAt("0" , intNunFil, INT_TBL_VALCHQ);
                tblDat.setValueAt("0" , intNunFil, INT_TBL_ABONO);
           }else{

                if(dblValApl != 0 ) tblDat.setValueAt( true, intNunFil, INT_TBL_CHKSEL );
                else tblDat.setValueAt( false, intNunFil, INT_TBL_CHKSEL );

                tblDat.setValueAt(""+dblValApl , intNunFil, INT_TBL_VALCHQ);
           }

         

           calculaTotMonAbo();
         }
    });



    objTblCelEdiTxtNumRetChq=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
    tcmAux.getColumn(INT_TBL_NUMCTA).setCellEditor(objTblCelEdiTxtNumRetChq);
    tcmAux.getColumn(INT_TBL_NUMCHQ).setCellEditor(objTblCelEdiTxtNumRetChq);
    objTblCelEdiTxtNumRetChq.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        @Override
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
           int intNumFil = tblDat.getSelectedRow();

           if( !(tblDat.getValueAt(intNumFil, INT_TBL_CODREGAAJU)==null))
               objTblCelEdiTxtNumRetChq.setCancelarEdicion(true);

        }
        @Override
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

         }
    });




        int intColFac[]=new int[20];
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
        intColFac[16]=24;
        intColFac[17]=21;
        intColFac[18]=22;
        intColFac[19]=23;



        int intColTblFac[]=new int[20];
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
        intColTblFac[16]=INT_TBL_NUMCHQ;
        intColTblFac[17]=INT_TBL_CODBAN;
        intColTblFac[18]=INT_TBL_NOMBAN;
        intColTblFac[19]=INT_TBL_NUMCTA;

        objTblCelEdiTxtVcoFacRet=new ZafTblCelEdiTxtVco(tblDat, objVenConFac, intColFac, intColTblFac );
        tcmAux.getColumn(INT_TBL_NUMDOC).setCellEditor(objTblCelEdiTxtVcoFacRet);
        objTblCelEdiTxtVcoFacRet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            @Override
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){


            }
            @Override
            public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                objVenConFac.setCampoBusqueda(9);
                objVenConFac.setCriterio1(11);
            }
            @Override
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               
            }
        });






        int intColCli[]=new int[9];
        intColCli[0]=1;
        intColCli[1]=2;
        intColCli[2]=3;
        intColCli[3]=4;
        intColCli[4]=5;
        intColCli[5]=6;
        intColCli[6]=7;
        intColCli[7]=8;
        intColCli[8]=9;

        int intColTblCli[]=new int[9];
        intColTblCli[0]=INT_TBL_CODTIPDOC;
        intColTblCli[1]=INT_TBL_DCOTIPDOC;
        intColTblCli[2]=INT_TBL_DLATIPDOC;
        intColTblCli[3]=INT_TBL_CODCTADEB;
        intColTblCli[4]=INT_TBL_DCOCTADEB;
        intColTblCli[5]=INT_TBL_NOMCTADEB;
        intColTblCli[6]=INT_TBL_CODCTAHAB;
        intColTblCli[7]=INT_TBL_DCOCTAHAB;
        intColTblCli[8]=INT_TBL_NOMCTAHAB;


        objTblCelEdiTxtVcoTipDoc=new ZafTblCelEdiTxtVco(tblDat, objVenConTipdocCon, intColCli, intColTblCli );
        tcmAux.getColumn(INT_TBL_DCOTIPDOC).setCellEditor(objTblCelEdiTxtVcoTipDoc);
        objTblCelEdiTxtVcoTipDoc.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            @Override
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                int intNumFil = tblDat.getSelectedRow();
                String strAsiFac= ""; // (tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC).toString()));
                String strDepo  = ""; // (tblDat.getValueAt(intNumFil, INT_TBL_CHKDEPO)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKDEPO).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKDEPO).toString()));
                String strSelRec=  (tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).toString()));

                if((strAsiFac.equals("false")))
                    objTblCelEdiTxtVcoTipDoc.setCancelarEdicion(true);

                if(strDepo.equals("true"))
                    objTblCelEdiTxtVcoTipDoc.setCancelarEdicion(true);

                if((strSelRec.equals("false")))
                    objTblCelEdiTxtVcoTipDoc.setCancelarEdicion(true);
            }
            @Override
            public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                objVenConTipdocCon.setCampoBusqueda(1);
                objVenConTipdocCon.setCriterio1(11);
            }
            @Override
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow();

//                String strTipDoc  =  (tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOC)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOC).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOC).toString()));
//                String strTipDocOri=  (tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOCORI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOCORI).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOCORI).toString()));
//
//                if(!strTipDoc.equals(strTipDocOri)){
//                  tblDat.setValueAt("N",intNumFil, INT_TBL_ESTCHK);
//                }



                generaAsiento();


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
            @Override
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow();
                if(intNumFil >= 0 ) {

                   String strEstApl= ""; // (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
                   String strAsigBan= ""; // (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));
                   String strSelRec=  (tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).toString()));

                   if( !(tblDat.getValueAt(intNumFil, INT_TBL_CODREGAAJU)==null))
                     objTblCelEdiTxtVcoBan.setCancelarEdicion(true);

                   if((strEstApl.equals("S")))
                    objTblCelEdiTxtVcoBan.setCancelarEdicion(true);

                   if((strAsigBan.equals("S")))
                    objTblCelEdiTxtVcoBan.setCancelarEdicion(true);

                   if((strSelRec.equals("false")))
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
            @Override
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
        tcmAux.getColumn(INT_TBL_BUTTIPDOC).setCellRenderer(objTblCelRenBut);
        tcmAux.getColumn(INT_TBL_BUTBAN).setCellRenderer(objTblCelRenBut);
        tcmAux.getColumn(INT_TBL_BUTTIPDOC).setCellRenderer(objTblCelRenBut);
        objTblCelRenBut=null;
        
        ButBan butBan = new ButBan(tblDat, INT_TBL_BUTBAN);
        ButTipDoc butTipDoc1 = new ButTipDoc(tblDat, INT_TBL_BUTTIPDOC);

          //------------------------------------------------------------------
    //Eddye_ventana de documentos pendientes//
    objTblCelEdiButGen=new ZafTblCelEdiButGen();
    tblDat.getColumnModel().getColumn(INT_TBL_BUTCLI).setCellEditor(objTblCelEdiButGen);
    objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        @Override
        public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            int intFilSel, intFilSelVenCon[], i, j;
            String strCodCli, strNomCli, strValDoc;
            double dblValDoc=0;
            if (tblDat.getSelectedColumn()==INT_TBL_BUTCLI)
            {
                objVenConCxC01.setVisible(true);
            }
            if (objVenConCxC01.getSelectedButton()==ZafVenConCxC01.INT_BUT_ACE)
            {
                intFilSel=tblDat.getSelectedRow();
                intFilSelVenCon=objVenConCxC01.getFilasSeleccionadas();
                strCodCli=objVenConCxC01.getCodigoCliente();
                strNomCli=objVenConCxC01.getNombreCliente();
                j=intFilSel;
                for (i=0; i<intFilSelVenCon.length; i++)
                {
                    //if (objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_LIN)!="P")
                    if (! objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_LIN).toString().equals("P") )
                    {
                        objTblMod.insertRow(j);
                        objTblMod.setValueAt(strCodCli, j, INT_TBL_CODCLI);
                        objTblMod.setValueAt(strNomCli, j, INT_TBL_NOMCLI);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_COD_LOC), j, INT_TBL_CODLOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_COD_TIP_DOC), j, INT_TBL_CODTID);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_DEC_TIP_DOC), j, INT_TBL_DCTIPDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_DEL_TIP_DOC), j, INT_TBL_DLTIPDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_COD_DOC), j, INT_TBL_CODDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_COD_REG), j, INT_TBL_CODREG);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_NUM_DOC), j, INT_TBL_NUMDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_FEC_DOC), j, INT_TBL_FECDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_DIA_CRE), j, INT_TBL_DIACRE);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_FEC_VEN), j, INT_TBL_FECVEN);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_POR_RET), j, INT_TBL_PORRET);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_VAL_DOC), j, INT_TBL_VALDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_VAL_PEN), j, INT_TBL_VALPEN);
                        objTblMod.setValueAt( ""+objZafParSis.getCodigoEmpresa()  , j, INT_TBL_CODEMP );
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_COD_BAN), j, INT_TBL_CODBAN);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_NOM_BAN), j, INT_TBL_NOMBAN);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_NUM_CTA), j, INT_TBL_NUMCTA);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_NUM_CHQ), j, INT_TBL_NUMCHQ);
                        objVenConCxC01.setFilaProcesada(intFilSelVenCon[i]);
                        j++;
                    }
                }
                tblDat.requestFocus();
                calculaTotMonAbo();
                //objTblMod.removeEmptyRows();
            }
        }
    });
  //------------------------------------------------------------------


    setEditable(false);

//     new ZafTblOrd(tblDat);
   
    objTblPopMnu = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
   // objTblPopMnu.setEliminarFilaVisible(false);
    objTblPopMnu.setInsertarFilasVisible(false);
    objTblPopMnu.setInsertarFilaVisible(false);

    objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
        @Override
        public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
            if(objTblPopMnu.isClickEliminarFila()){
              if(!verificaSelEli()){
                 MensajeInf("NO SE PUEDE ELIMINAR. SOLO SE PUEDE ELIMINAR DATOS NUEVOS.  ");
                 objTblPopMnu.cancelarClick();
               }
            }
        }
        @Override
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


     //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatMouseClicked(evt);
                }
            });

            
    tcmAux=null;


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
         strEstApl = (tblDat.getValueAt(intFil, INT_TBL_CODREGAAJU)==null?"N":(tblDat.getValueAt(intFil, INT_TBL_CODREGAAJU).equals("")?"N":tblDat.getValueAt(intFil, INT_TBL_CODREGAAJU).toString()));
         if(!(strEstApl.equals("N"))){
             blnRes=false;
             break;
         }
   }
   intFilSel=null;

   }catch(Exception e) { blnRes=false;  objUti.mostrarMsgErr_F1(this,e);  }
  return blnRes;
}


private boolean ConfigurarTablaCobroMasivo() {
 boolean blnRes=false;
 try{
     //Configurar JTable: Establecer el modelo.
        vecCab.clear();
        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_CHKSEL,"");
        vecCab.add(INT_TBL_BUTCLI,"");
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
        vecCab.add(INT_TBL_CODREGAAJU,"");
        vecCab.add(INT_TBL_ABONOORI,"");
        vecCab.add(INT_TBL_CODTIPDOC,"");
        vecCab.add(INT_TBL_DCOTIPDOC,"DC.Tip.Doc");
        vecCab.add(INT_TBL_BUTTIPDOC,"");
        vecCab.add(INT_TBL_DLATIPDOC,"DL.Tip.Doc");
        vecCab.add(INT_TBL_CODBAN,"Cód.Ban.");
        vecCab.add(INT_TBL_DSCBAN,"Banco.");
        vecCab.add(INT_TBL_BUTBAN,"");
        vecCab.add(INT_TBL_NOMBAN,"Nom.Ban");
        vecCab.add(INT_TBL_NUMCTA,"Num.Cta.");
        vecCab.add(INT_TBL_BUTRET,"");
        vecCab.add(INT_TBL_IDEEMI,"Ide.Emi.");
        vecCab.add(INT_TBL_NUMCHQ,"Num.Chq.");
        vecCab.add(INT_TBL_FECVENCHQ,"Fec.Ven.Chq.");
        vecCab.add(INT_TBL_VALCHQ,"Val.Chq.");
        vecCab.add(INT_TBL_CODCTADEB,"deb");
        vecCab.add(INT_TBL_DCOCTADEB,"deb");
        vecCab.add(INT_TBL_NOMCTADEB,"deb");
        vecCab.add(INT_TBL_CODCTAHAB,"hab");
        vecCab.add(INT_TBL_DCOCTAHAB,"hab");
        vecCab.add(INT_TBL_NOMCTAHAB,"hab");
        vecCab.add(INT_TBL_NUMSECDOC,"Num.Sec.Doc");
        vecCab.add(INT_TBL_NUMAUTSRI,"Num.Aut.Sri");
        vecCab.add(INT_TBL_FECCATDOC,"Fec.Cad.Doc");
        vecCab.add(INT_TBL_CODSRIDOC,"Cod.Ret");


	objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        ZafColNumerada zafColNumerada = new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);

        //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
        objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());
        tblDat.getTableHeader().setReorderingAllowed(false);

	//Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();  /**/

        objTblMod.setColumnDataType(INT_TBL_DIACRE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_PORRET, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_VALDOC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_VALPEN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_ABONO, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_VALCHQ, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_NUMDOC, ZafTblMod.INT_COL_DBL, new Integer(0), null);


        


        objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_DIACRE).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_PORRET).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_VALDOC).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_VALPEN).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_VALCHQ).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;


        objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_ABONO).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;

         //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        ZafMouMotAda objMouMotAda=new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

         //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CHKSEL).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_BUTCLI).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(40);
        tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_DCTIPDOC).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DLTIPDOC).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DIACRE).setPreferredWidth(35);
        tcmAux.getColumn(INT_TBL_FECVEN).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_PORRET).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_VALDOC).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_VALPEN).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_ABONO).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DSCBAN).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_BUTBAN).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_NOMBAN).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_NUMCTA).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_FECVENCHQ).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_BUTRET).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_IDEEMI).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_NUMCHQ).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_VALCHQ).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DCOTIPDOC).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_BUTTIPDOC).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_DLATIPDOC).setPreferredWidth(100);

        tcmAux.getColumn(INT_TBL_FECVENCHQ).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));


        /* Aqui se agrega las columnas que van
             ha hacer ocultas
         * */
        ArrayList arlColHid=new ArrayList();
        arlColHid.add(""+INT_TBL_CODEMP);
        arlColHid.add(""+INT_TBL_CODTID);
        arlColHid.add(""+INT_TBL_CODDOC);
        arlColHid.add(""+INT_TBL_CODREG);
        arlColHid.add(""+INT_TBL_CODREGAAJU);
        arlColHid.add(""+INT_TBL_ABONOORI);
        arlColHid.add(""+INT_TBL_DIACRE);
        arlColHid.add(""+INT_TBL_FECVEN);
        arlColHid.add(""+INT_TBL_CODBAN);
        arlColHid.add(""+INT_TBL_CODTIPDOC);
        arlColHid.add(""+INT_TBL_DCOTIPDOC);
        arlColHid.add(""+INT_TBL_BUTTIPDOC);
        arlColHid.add(""+INT_TBL_DLATIPDOC);
        arlColHid.add(""+INT_TBL_CODCTADEB);
        arlColHid.add(""+INT_TBL_DCOCTADEB);
        arlColHid.add(""+INT_TBL_NOMCTADEB);
        arlColHid.add(""+INT_TBL_CODCTAHAB);
        arlColHid.add(""+INT_TBL_DCOCTAHAB);
        arlColHid.add(""+INT_TBL_NOMCTAHAB);
        arlColHid.add(""+INT_TBL_DSCBAN);
        arlColHid.add(""+INT_TBL_BUTRET);
        arlColHid.add(""+INT_TBL_IDEEMI);
        arlColHid.add(""+INT_TBL_NUMSECDOC);
        arlColHid.add(""+INT_TBL_NUMAUTSRI);
        arlColHid.add(""+INT_TBL_FECCATDOC);
        arlColHid.add(""+INT_TBL_CODSRIDOC);
        objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;

        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_CHKSEL);
        vecAux.add("" + INT_TBL_BUTCLI);
        vecAux.add("" + INT_TBL_ABONO);
        vecAux.add("" + INT_TBL_DSCBAN);
        vecAux.add("" + INT_TBL_BUTBAN);
        vecAux.add("" + INT_TBL_NOMBAN);
        vecAux.add("" + INT_TBL_NUMCTA);
        vecAux.add("" + INT_TBL_NUMCHQ);
        vecAux.add("" + INT_TBL_DCOTIPDOC);
        vecAux.add("" + INT_TBL_BUTTIPDOC);
        vecAux.add("" + INT_TBL_FECVENCHQ);
        vecAux.add("" + INT_TBL_NUMDOC);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;
        
        ZafTblEdi zafTblEdi = new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);


        objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_CHKSEL).setCellRenderer(objTblCelRenChk);
        objTblCelRenChk=null;


        objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_CHKSEL).setCellEditor(objTblCelEdiChk);
        objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            @Override
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

            }
            @Override
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
              int intNunFil = tblDat.getSelectedRow();

               if(tblDat.getValueAt(intNunFil, INT_TBL_CHKSEL).toString().equals("true")){

                if( tblDat.getValueAt(intNunFil, INT_TBL_CODREGAAJU)==null){
                  //String strValPen = (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString()));
                  double dblValPen = objUti.redondear( (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString())), 2);
                  dblValPen = Math.abs(dblValPen);

                  tblDat.setValueAt(""+dblValPen, intNunFil, INT_TBL_ABONO);
                  tblDat.setValueAt(""+dblValPen, intNunFil, INT_TBL_VALCHQ);
                }else{
                    tblDat.setValueAt( tblDat.getValueAt(intNunFil, INT_TBL_ABONOORI), intNunFil, INT_TBL_ABONO);
                    tblDat.setValueAt( tblDat.getValueAt(intNunFil, INT_TBL_ABONOORI), intNunFil, INT_TBL_VALCHQ);
                }
               }else{
                    tblDat.setValueAt("0", intNunFil, INT_TBL_ABONO);
                    tblDat.setValueAt("0", intNunFil, INT_TBL_VALCHQ);
               }

              calculaTotMonAbo();
            }
        });


    objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
    tcmAux.getColumn(INT_TBL_ABONO).setCellEditor(objTblCelEdiTxt);
    objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        @Override
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
           int intNumFil = tblDat.getSelectedRow();

           String strCodCli=  (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
           if((strCodCli.equals("")))
               objTblCelEdiTxt.setCancelarEdicion(true);


           if( !(tblDat.getValueAt(intNumFil, INT_TBL_CODREGAAJU)==null))
               objTblCelEdiTxt.setCancelarEdicion(true);



        }
        @Override
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
          int intNunFil = tblDat.getSelectedRow();

            double dblValApl = objUti.redondear( (tblDat.getValueAt(intNunFil, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_ABONO).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_ABONO).toString())), 4);
            double dblValPen = objUti.redondear( (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString())), 4);

            dblValPen = Math.abs(dblValPen);


          if(dblValApl > dblValPen){
                MensajeInf("EL VALOR QUE ESTA APLICANDO ES MAYOR AL VALOR PENDIENTE..");
                tblDat.setValueAt( true, intNunFil, INT_TBL_CHKSEL );
           }else{

                if(dblValApl != 0 ) tblDat.setValueAt( true, intNunFil, INT_TBL_CHKSEL );
                else tblDat.setValueAt( false, intNunFil, INT_TBL_CHKSEL );
           }

          tblDat.setValueAt(""+dblValApl , intNunFil, INT_TBL_VALCHQ);

           calculaTotMonAbo();
         }
    });



    objTblCelEdiTxtNumRetChq=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
    tcmAux.getColumn(INT_TBL_NUMCTA).setCellEditor(objTblCelEdiTxtNumRetChq);
    tcmAux.getColumn(INT_TBL_NUMCHQ).setCellEditor(objTblCelEdiTxtNumRetChq);
    objTblCelEdiTxtNumRetChq.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        @Override
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
           int intNumFil = tblDat.getSelectedRow();

           if( !(tblDat.getValueAt(intNumFil, INT_TBL_CODREGAAJU)==null))
               objTblCelEdiTxtNumRetChq.setCancelarEdicion(true);

        }
        @Override
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

         }
    });


        int intColFac[]=new int[23];
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
        intColFac[16]=27;
        intColFac[17]=24;
        intColFac[18]=21;
        intColFac[19]=22;
        intColFac[20]=23;
        intColFac[21]=26;
        intColFac[22]=27;
        
        int intColTblFac[]=new int[23];
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
        intColTblFac[16]=INT_TBL_ABONO;
        intColTblFac[17]=INT_TBL_NUMCHQ;
        intColTblFac[18]=INT_TBL_CODBAN;
        intColTblFac[19]=INT_TBL_NOMBAN;
        intColTblFac[20]=INT_TBL_NUMCTA;
        intColTblFac[21]=INT_TBL_FECVENCHQ;
        intColTblFac[22]=INT_TBL_VALCHQ;

        objTblCelEdiTxtVcoFacRet=new ZafTblCelEdiTxtVco(tblDat, objVenConFac, intColFac, intColTblFac );
        tcmAux.getColumn(INT_TBL_NUMDOC).setCellEditor(objTblCelEdiTxtVcoFacRet);
        objTblCelEdiTxtVcoFacRet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            @Override
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){


            }
            @Override
            public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                objVenConFac.setCampoBusqueda(9);
                objVenConFac.setCriterio1(11);
            }
            @Override
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow();


                String strFecVenChq = tblDat.getValueAt(intNumFil, INT_TBL_FECVENCHQ)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_FECVENCHQ).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_FECVENCHQ).toString());

                if(!(strFecVenChq.equals("")))
                  tblDat.setValueAt( objUti.formatearFecha( strFecVenChq, "yyyy-MM-dd", "dd/MM/yyyy") , intNumFil, INT_TBL_FECVENCHQ);
                else  tblDat.setValueAt("", intNumFil, INT_TBL_FECVENCHQ);


                double dblValAbo = objUti.redondear( (tblDat.getValueAt(intNumFil, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(intNumFil, INT_TBL_ABONO).equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_ABONO).toString())), 4);
                if(dblValAbo != 0){
                    tblDat.setValueAt(true, intNumFil, INT_TBL_CHKSEL);
                }else{
                    tblDat.setValueAt(false, intNumFil, INT_TBL_CHKSEL);
                }
                calculaTotMonAbo();

            }
        });

        int intColCli[]=new int[9];
        intColCli[0]=1;
        intColCli[1]=2;
        intColCli[2]=3;
        intColCli[3]=4;
        intColCli[4]=5;
        intColCli[5]=6;
        intColCli[6]=7;
        intColCli[7]=8;
        intColCli[8]=9;

        int intColTblCli[]=new int[9];
        intColTblCli[0]=INT_TBL_CODTIPDOC;
        intColTblCli[1]=INT_TBL_DCOTIPDOC;
        intColTblCli[2]=INT_TBL_DLATIPDOC;
        intColTblCli[3]=INT_TBL_CODCTADEB;
        intColTblCli[4]=INT_TBL_DCOCTADEB;
        intColTblCli[5]=INT_TBL_NOMCTADEB;
        intColTblCli[6]=INT_TBL_CODCTAHAB;
        intColTblCli[7]=INT_TBL_DCOCTAHAB;
        intColTblCli[8]=INT_TBL_NOMCTAHAB;


        objTblCelEdiTxtVcoTipDoc=new ZafTblCelEdiTxtVco(tblDat, objVenConTipdocCon, intColCli, intColTblCli );
        tcmAux.getColumn(INT_TBL_DCOTIPDOC).setCellEditor(objTblCelEdiTxtVcoTipDoc);
        objTblCelEdiTxtVcoTipDoc.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            @Override
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                int intNumFil = tblDat.getSelectedRow();
                String strAsiFac= ""; // (tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC).toString()));
                String strDepo  = ""; // (tblDat.getValueAt(intNumFil, INT_TBL_CHKDEPO)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKDEPO).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKDEPO).toString()));
                String strSelRec=  (tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).toString()));

                if((strAsiFac.equals("false")))
                    objTblCelEdiTxtVcoTipDoc.setCancelarEdicion(true);

                if(strDepo.equals("true"))
                    objTblCelEdiTxtVcoTipDoc.setCancelarEdicion(true);

                if((strSelRec.equals("false")))
                    objTblCelEdiTxtVcoTipDoc.setCancelarEdicion(true);
            }
            @Override
            public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                objVenConTipdocCon.setCampoBusqueda(1);
                objVenConTipdocCon.setCriterio1(11);
            }
            @Override
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow();

//                String strTipDoc  =  (tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOC)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOC).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOC).toString()));
//                String strTipDocOri=  (tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOCORI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOCORI).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOCORI).toString()));
//
//                if(!strTipDoc.equals(strTipDocOri)){
//                  tblDat.setValueAt("N",intNumFil, INT_TBL_ESTCHK);
//                }

                generaAsiento();
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
            @Override
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow();
                if(intNumFil >= 0 ) {

                   String strEstApl= ""; // (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
                   String strAsigBan= ""; // (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));
                   String strSelRec=  (tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).toString()));

                   if( !(tblDat.getValueAt(intNumFil, INT_TBL_CODREGAAJU)==null))
                     objTblCelEdiTxtVcoBan.setCancelarEdicion(true);

                   if((strEstApl.equals("S")))
                    objTblCelEdiTxtVcoBan.setCancelarEdicion(true);

                   if((strAsigBan.equals("S")))
                    objTblCelEdiTxtVcoBan.setCancelarEdicion(true);

                   if((strSelRec.equals("false")))
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
            @Override
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
        tcmAux.getColumn(INT_TBL_BUTTIPDOC).setCellRenderer(objTblCelRenBut);
        tcmAux.getColumn(INT_TBL_BUTBAN).setCellRenderer(objTblCelRenBut);
        tcmAux.getColumn(INT_TBL_BUTTIPDOC).setCellRenderer(objTblCelRenBut);
        objTblCelRenBut=null;
        
        ButBan butBan = new ButBan(tblDat, INT_TBL_BUTBAN);
        ButTipDoc butTipDoc1 = new ButTipDoc(tblDat, INT_TBL_BUTTIPDOC);

          //------------------------------------------------------------------
    //Eddye_ventana de documentos pendientes//
    objTblCelEdiButGen=new ZafTblCelEdiButGen();
    tblDat.getColumnModel().getColumn(INT_TBL_BUTCLI).setCellEditor(objTblCelEdiButGen);
    objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        @Override
        public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            int intFilSel, intFilSelVenCon[], i, j;
            String strCodCli, strNomCli, strValDoc;
            double dblValDoc=0;
            if (tblDat.getSelectedColumn()==INT_TBL_BUTCLI)
            {
                objVenConCxC01.setVisible(true);
            }
            if (objVenConCxC01.getSelectedButton()==ZafVenConCxC01.INT_BUT_ACE)
            {
                intFilSel=tblDat.getSelectedRow();
                intFilSelVenCon=objVenConCxC01.getFilasSeleccionadas();
                strCodCli=objVenConCxC01.getCodigoCliente();
                strNomCli=objVenConCxC01.getNombreCliente();
                j=intFilSel;
                for (i=0; i<intFilSelVenCon.length; i++)
                {
                    //if (objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_LIN)!="P")
                    if (! objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_LIN).toString().equals("P"))
                    {
                        objTblMod.insertRow(j);

                        strValDoc=objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_VAL_CHQ);
                        strValDoc=(strValDoc==null?"0":(strValDoc.equals("")?"0":strValDoc));
                        dblValDoc=objUti.redondear(strValDoc, 4);

                       // if( dblValDoc > 0 ) objTblMod.setValueAt( new Boolean(true) , j, INT_TBL_CHKSEL );
                      //  else objTblMod.setValueAt( new Boolean(false) , j, INT_TBL_CHKSEL );

                      /**/  objTblMod.setValueAt( false, j, INT_TBL_CHKSEL );

                        objTblMod.setValueAt(strCodCli, j, INT_TBL_CODCLI);
                        objTblMod.setValueAt(strNomCli, j, INT_TBL_NOMCLI);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_COD_LOC), j, INT_TBL_CODLOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_COD_TIP_DOC), j, INT_TBL_CODTID);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_DEC_TIP_DOC), j, INT_TBL_DCTIPDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_DEL_TIP_DOC), j, INT_TBL_DLTIPDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_COD_DOC), j, INT_TBL_CODDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_COD_REG), j, INT_TBL_CODREG);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_NUM_DOC), j, INT_TBL_NUMDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_FEC_DOC), j, INT_TBL_FECDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_DIA_CRE), j, INT_TBL_DIACRE);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_FEC_VEN), j, INT_TBL_FECVEN);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_POR_RET), j, INT_TBL_PORRET);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_VAL_DOC), j, INT_TBL_VALDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_VAL_PEN), j, INT_TBL_VALPEN);
                        objTblMod.setValueAt( ""+objZafParSis.getCodigoEmpresa()  , j, INT_TBL_CODEMP );
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_COD_BAN), j, INT_TBL_CODBAN);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_NOM_BAN), j, INT_TBL_NOMBAN);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_NUM_CTA), j, INT_TBL_NUMCTA);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_NUM_CHQ), j, INT_TBL_NUMCHQ);

                        //objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_VAL_CHQ), j, INT_TBL_ABONO);

                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_VAL_CHQ), j, INT_TBL_VALCHQ);
//                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_EST_SOP), j, INT_TBL_SOP_NEC);
//                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_SOP_ENT), j, INT_TBL_SOP_ENT);
//                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_NUM_DOC), j, INT_TBL_NUM_DOC_AUX);

                        String strFecVenChq=objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_FEC_VEN_CHQ);
                        if(!(strFecVenChq.equals("")))
                          objTblMod.setValueAt( objUti.formatearFecha( strFecVenChq ,  "yyyy-MM-dd", "dd/MM/yyyy") , j, INT_TBL_FECVENCHQ);


                        objVenConCxC01.setFilaProcesada(intFilSelVenCon[i]);
                        j++;
                    }
                }
                tblDat.requestFocus();
                calculaTotMonAbo();
                //objTblMod.removeEmptyRows();
            }
        }
    });
  //------------------------------------------------------------------


    setEditable(false);

//     new ZafTblOrd(tblDat);

    objTblPopMnu = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
   // objTblPopMnu.setEliminarFilaVisible(false);
    objTblPopMnu.setInsertarFilasVisible(false);
    objTblPopMnu.setInsertarFilaVisible(false);

    objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
        @Override
        public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
            if(objTblPopMnu.isClickEliminarFila()){
              if(!verificaSelEli()){
                 MensajeInf("NO SE PUEDE ELIMINAR. SOLO SE PUEDE ELIMINAR DATOS NUEVOS.  ");
                 objTblPopMnu.cancelarClick();
               }
            }
        }
        @Override
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


     //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
               @Override
               public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatMouseClicked(evt);
                }
            });

            
    tcmAux=null;


      blnRes=true;
   }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
    return blnRes;
}


private boolean ConfigurarTabla() {
 boolean blnRes=false;
 try{
     //Configurar JTable: Establecer el modelo.
        vecCab.clear();
        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_CHKSEL,"");
        vecCab.add(INT_TBL_BUTCLI,"");
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
        vecCab.add(INT_TBL_CODREGAAJU,"");
        vecCab.add(INT_TBL_ABONOORI,"");
        vecCab.add(INT_TBL_CODTIPDOC,"");
        vecCab.add(INT_TBL_DCOTIPDOC,"DC.Tip.Doc");
        vecCab.add(INT_TBL_BUTTIPDOC,"");
        vecCab.add(INT_TBL_DLATIPDOC,"DL.Tip.Doc");
        vecCab.add(INT_TBL_CODBAN,"Cód.Ban.");
        vecCab.add(INT_TBL_DSCBAN,"Banco.");
        vecCab.add(INT_TBL_BUTBAN,"");
        vecCab.add(INT_TBL_NOMBAN,"Nom.Ban");
        vecCab.add(INT_TBL_NUMCTA,"Num.Cta.");
        vecCab.add(INT_TBL_BUTRET,"");
        vecCab.add(INT_TBL_IDEEMI,"Ide.Emi.");
        vecCab.add(INT_TBL_NUMCHQ,"Num.Ret.");
        vecCab.add(INT_TBL_FECVENCHQ,"Fec.Ven.Chq.");
        vecCab.add(INT_TBL_VALCHQ,"Val.Chq.");
        vecCab.add(INT_TBL_CODCTADEB,"deb");
        vecCab.add(INT_TBL_DCOCTADEB,"deb");
        vecCab.add(INT_TBL_NOMCTADEB,"deb");
        vecCab.add(INT_TBL_CODCTAHAB,"hab");
        vecCab.add(INT_TBL_DCOCTAHAB,"hab");
        vecCab.add(INT_TBL_NOMCTAHAB,"hab");
        vecCab.add(INT_TBL_NUMSECDOC,"Num.Ser.Doc");
        vecCab.add(INT_TBL_NUMAUTSRI,"Num.Aut.Sri");
        vecCab.add(INT_TBL_FECCATDOC,"Fec.Cad.Doc");
        vecCab.add(INT_TBL_CODSRIDOC,"Cod.Sri");


	objTblMod=new ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LINEA);

        //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
        objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());
        tblDat.getTableHeader().setReorderingAllowed(false);

	//Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  /**/
        TableColumnModel tcmAux=tblDat.getColumnModel();  /**/

        objTblMod.setColumnDataType(INT_TBL_DIACRE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_PORRET, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_VALDOC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_VALPEN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_ABONO, ZafTblMod.INT_COL_DBL, new Integer(0), null);


        objTblCelRenLbl=new ZafTblCelRenLbl();
        objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_DIACRE).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_PORRET).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_VALDOC).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_VALPEN).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;

        objTblCelRenLbl=new ZafTblCelRenLbl();
        objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_ABONO).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;

         //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        ZafMouMotAda objMouMotAda=new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

         //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CHKSEL).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_BUTCLI).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(40);
        tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_DCTIPDOC).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DLTIPDOC).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DIACRE).setPreferredWidth(35);
        tcmAux.getColumn(INT_TBL_FECVEN).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_PORRET).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_VALDOC).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_VALPEN).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_ABONO).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DSCBAN).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_BUTBAN).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_NOMBAN).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_NUMCTA).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_BUTRET).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_IDEEMI).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_NUMCHQ).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_VALCHQ).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DCOTIPDOC).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_BUTTIPDOC).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_DLATIPDOC).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_NUMSECDOC).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_NUMAUTSRI).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_FECCATDOC).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_CODSRIDOC).setPreferredWidth(80);


        /* Aqui se agrega las columnas que van
             ha hacer ocultas
         * */
        ArrayList arlColHid=new ArrayList();
        arlColHid.add(""+INT_TBL_CODEMP);
        arlColHid.add(""+INT_TBL_CODTID);
        arlColHid.add(""+INT_TBL_CODDOC);
        arlColHid.add(""+INT_TBL_CODREG);
        arlColHid.add(""+INT_TBL_CODREGAAJU);
        arlColHid.add(""+INT_TBL_ABONOORI);
        arlColHid.add(""+INT_TBL_DIACRE);
        arlColHid.add(""+INT_TBL_FECVEN);
        arlColHid.add(""+INT_TBL_CODBAN);
        arlColHid.add(""+INT_TBL_CODTIPDOC);
        arlColHid.add(""+INT_TBL_DCOTIPDOC);
        arlColHid.add(""+INT_TBL_BUTTIPDOC);
        arlColHid.add(""+INT_TBL_DLATIPDOC);
        arlColHid.add(""+INT_TBL_DSCBAN);
        arlColHid.add(""+INT_TBL_BUTBAN);
        arlColHid.add(""+INT_TBL_NOMBAN);
        arlColHid.add(""+INT_TBL_NUMCTA);
        arlColHid.add(""+INT_TBL_VALCHQ);
        arlColHid.add(""+INT_TBL_IDEEMI);
        arlColHid.add(""+INT_TBL_CODCTADEB);
        arlColHid.add(""+INT_TBL_DCOCTADEB);
        arlColHid.add(""+INT_TBL_NOMCTADEB);
        arlColHid.add(""+INT_TBL_CODCTAHAB);
        arlColHid.add(""+INT_TBL_DCOCTAHAB);
        arlColHid.add(""+INT_TBL_NOMCTAHAB);
        arlColHid.add(""+INT_TBL_FECVENCHQ);
        objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;

        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_CHKSEL);
        vecAux.add("" + INT_TBL_BUTCLI);
        vecAux.add("" + INT_TBL_ABONO);
        vecAux.add("" + INT_TBL_DSCBAN);
        vecAux.add("" + INT_TBL_BUTBAN);
        vecAux.add("" + INT_TBL_NOMBAN);
        vecAux.add("" + INT_TBL_NUMCTA);
        vecAux.add("" + INT_TBL_BUTRET);
        //vecAux.add("" + INT_TBL_IDEEMI); //No editable
        vecAux.add("" + INT_TBL_NUMCHQ);
        vecAux.add("" + INT_TBL_VALCHQ);
        vecAux.add("" + INT_TBL_DCOTIPDOC);
        vecAux.add("" + INT_TBL_BUTTIPDOC);
        vecAux.add("" + INT_TBL_NUMSECDOC);
        vecAux.add("" + INT_TBL_NUMAUTSRI);
        vecAux.add("" + INT_TBL_FECCATDOC);
        //vecAux.add("" + INT_TBL_CODSRIDOC); //El 11/Jun/2015 se puso como col. 'no editable'
        vecAux.add("" + INT_TBL_NUMDOC);

        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;
        
        ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);


        objTblCelRenChk = new ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_CHKSEL).setCellRenderer(objTblCelRenChk);
        objTblCelRenChk=null;


        objTblCelEdiChk = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_CHKSEL).setCellEditor(objTblCelEdiChk);
        objTblCelEdiChk.addTableEditorListener(new ZafTableAdapter() {
            @Override
            public void beforeEdit(ZafTableEvent evt){

            } 
            @Override
            public void afterEdit(ZafTableEvent evt) {
              int intNunFil = tblDat.getSelectedRow();

               if(tblDat.getValueAt(intNunFil, INT_TBL_CHKSEL).toString().equals("true")){

                if( tblDat.getValueAt(intNunFil, INT_TBL_CODREGAAJU)==null){
                  //String strValPen = (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString()));
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


    objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
    tcmAux.getColumn(INT_TBL_ABONO).setCellEditor(objTblCelEdiTxt);
    objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
        @Override
        public void beforeEdit(ZafTableEvent evt){
           int intNumFil = tblDat.getSelectedRow();

           String strCodCli=  (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
           if((strCodCli.equals("")))
               objTblCelEdiTxt.setCancelarEdicion(true);

           if( !(tblDat.getValueAt(intNumFil, INT_TBL_CODREGAAJU)==null))
               objTblCelEdiTxt.setCancelarEdicion(true);

        }
        @Override
        public void afterEdit(ZafTableEvent evt) {
          int intNunFil = tblDat.getSelectedRow();

            double dblValApl = objUti.redondear( (tblDat.getValueAt(intNunFil, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_ABONO).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_ABONO).toString())), 4);
            double dblValPen = objUti.redondear( (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString())), 4);

            dblValPen = Math.abs(dblValPen);

            //dblValPen = Math.abs(dblValPen);
            

//            if(dblValApl > dblValPen){
//                MensajeInf("EL VALOR QUE ESTA ABONANDO ES MAYOR AL VALOR PENDIENTE..");
//                tblDat.setValueAt("0", intNunFil, INT_TBL_ABONO );
//                tblDat.setValueAt( new Boolean(false), intNunFil, INT_TBL_CHKSEL );
//            }else{
//                if(dblValApl != 0 ) tblDat.setValueAt( new Boolean(true), intNunFil, INT_TBL_CHKSEL );
//                else tblDat.setValueAt( new Boolean(false), intNunFil, INT_TBL_CHKSEL );
//            }
            

               double dblDif=objUti.redondear((dblValPen-dblValApl), 4);
               if( !(( dblDif >= dblMinAjuCenAut ) && ( dblDif <= dblMaxAjuCenAut ))   ){
                   MensajeInf("EL VALOR APLICADO ESTA FUERA DE RANGO DE AJUSTE DE CENTAVOS.");
                   tblDat.setValueAt("", intNunFil, INT_TBL_ABONO );
                   tblDat.setValueAt( false, intNunFil, INT_TBL_CHKSEL );
               }else{
                  if(dblValApl != 0 ) tblDat.setValueAt( true, intNunFil, INT_TBL_CHKSEL );
                  else tblDat.setValueAt( false, intNunFil, INT_TBL_CHKSEL );
               }




           calculaTotMonAbo();
         }
    });


    objTblCelEdiTxtNumRetChq=new ZafTblCelEdiTxt(tblDat);
    objTblCelEdiTxtNumRetChq.setBackground(objZafParSis.getColorCamposObligatorios());
    tcmAux.getColumn(INT_TBL_NUMCHQ).setCellEditor(objTblCelEdiTxtNumRetChq);
    tcmAux.getColumn(INT_TBL_NUMSECDOC).setCellEditor(objTblCelEdiTxtNumRetChq);
    tcmAux.getColumn(INT_TBL_NUMAUTSRI).setCellEditor(objTblCelEdiTxtNumRetChq);
    tcmAux.getColumn(INT_TBL_FECCATDOC).setCellEditor(objTblCelEdiTxtNumRetChq);
    tcmAux.getColumn(INT_TBL_CODSRIDOC).setCellEditor(objTblCelEdiTxtNumRetChq);
    objTblCelEdiTxtNumRetChq.addTableEditorListener(new ZafTableAdapter() {
        @Override
        public void beforeEdit(ZafTableEvent evt){
           int intNumFil = tblDat.getSelectedRow();

           String strCodCli=  (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
           if((strCodCli.equals("")))
               objTblCelEdiTxtNumRetChq.setCancelarEdicion(true);

           if( !(tblDat.getValueAt(intNumFil, INT_TBL_CODREGAAJU)==null))
               objTblCelEdiTxtNumRetChq.setCancelarEdicion(true);

        }
        @Override
        public void afterEdit(ZafTableEvent evt) {
          
         }
    });
    
    /*
     * MODIFICADO EFLORESA 2012-06-12
     * NO INGRESAR EL MISMO NUMERO DE RETENCION.
     */
    objTblCelEdiNumRet=new ZafTblCelEdiTxt(tblDat);
    objTblCelEdiNumRet.setBackground(objZafParSis.getColorCamposObligatorios());
    tcmAux.getColumn(INT_TBL_NUMCHQ).setCellEditor(objTblCelEdiNumRet);
    objTblCelEdiNumRet.addTableEditorListener(new ZafTableAdapter() {
        @Override
        public void afterEdit(ZafTableEvent e){
            if(objZafParSis.getCodigoMenu()==1648){ 
                int intNumFil = tblDat.getSelectedRow();
                for(int i=0; i<tblDat.getColumnCount();i++){
                    if(tblDat.getValueAt(i, INT_TBL_NUMCHQ)!=null && !tblDat.getValueAt(i, INT_TBL_NUMCHQ).equals("") && i != intNumFil ){
                        if (tblDat.getValueAt(i, INT_TBL_NUMCHQ).equals(tblDat.getValueAt(intNumFil, INT_TBL_NUMCHQ))){
                            if (tblDat.getValueAt(i, INT_TBL_CODCLI)!=null && i != intNumFil ) {
                                MensajeInf("El numero de retencion que esta ingresando ya existe");
                                tblDat.setValueAt("", intNumFil, tblDat.getSelectedColumn());
                            }
                        }
                    }                
                }
            }
        }
    });
    
    
        int intColFac[]=new int[23];
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
        intColFac[16]=27;
        intColFac[17]=27; //IdeEmi
        intColFac[18]=24;
        intColFac[19]=28;
        intColFac[20]=29;
        intColFac[21]=30;
        intColFac[22]=31;

        int intColTblFac[]=new int[23];
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
        intColTblFac[16]=INT_TBL_ABONO;
        intColTblFac[17]=INT_TBL_IDEEMI;
        intColTblFac[18]=INT_TBL_NUMCHQ;
        intColTblFac[19]=INT_TBL_NUMSECDOC;
        intColTblFac[20]=INT_TBL_NUMAUTSRI;
        intColTblFac[21]=INT_TBL_FECCATDOC;
        intColTblFac[22]=INT_TBL_CODSRIDOC;

        objTblCelEdiTxtVcoFacRet=new ZafTblCelEdiTxtVco(tblDat, objVenConFac, intColFac, intColTblFac );
        tcmAux.getColumn(INT_TBL_NUMDOC).setCellEditor(objTblCelEdiTxtVcoFacRet);
        objTblCelEdiTxtVcoFacRet.addTableEditorListener(new ZafTableAdapter() {
            @Override
            public void beforeEdit(ZafTableEvent evt){
                
                
            }
            @Override
            public void beforeConsultar(ZafTableEvent evt) {
                objVenConFac.setCampoBusqueda(9);
                objVenConFac.setCriterio1(11);
            }
            @Override
            public void afterEdit(ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow(), intCodTipRet;
                String strCodSri1, strCodSri2, strAux, strCodLocTbl, strNumDocTbl, strIsItmFacSrvTra;
                BigDecimal bdePorRet, bdeValRetTbl;
                
                if (verificaPorcentajeRetencionExisteTabla(tblDat, tblDat.getSelectedRow()) == true)
                {  //No se debe permitir que la informacion sea ingresada en el JTable.
                   return;
                }
                
                double dblValAbo = objUti.redondear( (tblDat.getValueAt(intNumFil, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(intNumFil, INT_TBL_ABONO).equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_ABONO).toString())), 4);
                if(dblValAbo != 0){
                    tblDat.setValueAt(true, intNumFil, INT_TBL_CHKSEL);
                     calculaTotMonAbo();
                }

             // int intNumFilDat =  intNumFil-1;
              abrirCon();
              
              if (intNumFil == 0)
              {  strAux = tblDat.getValueAt(intNumFil, INT_TBL_PORRET) == null ? "" :tblDat.getValueAt(intNumFil, INT_TBL_PORRET).toString();
                 if (!strAux.equals(""))
                 {  bdePorRet = new BigDecimal(tblDat.getValueAt(intNumFil, INT_TBL_PORRET).toString());
                    bdePorRet = objUti.redondearBigDecimal(bdePorRet, objZafParSis.getDecimalesMostrar());
                    intCodTipRet = getCodTipRet(bdePorRet);
                    strCodLocTbl = tblDat.getValueAt(intNumFil, INT_TBL_CODLOC) == null ? "" :tblDat.getValueAt(intNumFil, INT_TBL_CODLOC).toString();
                    strNumDocTbl = tblDat.getValueAt(intNumFil, INT_TBL_NUMDOC) == null ? "" :tblDat.getValueAt(intNumFil, INT_TBL_NUMDOC).toString();
                    
                    strAux = tblDat.getValueAt(intNumFil, INT_TBL_VALDOC) == null? "" :tblDat.getValueAt(intNumFil, INT_TBL_VALDOC).toString();
                     
                    if (strAux.equals(""))
                    {  bdeValRetTbl = new BigDecimal("0.00");
                    }
                    else
                    {  bdeValRetTbl = new BigDecimal(strAux);
                       bdeValRetTbl = objUti.redondearBigDecimal(bdeValRetTbl, objZafParSis.getDecimalesMostrar());
                    }
                    
                    strIsItmFacSrvTra = isItemFacturaServicioTransporte(strCodLocTbl, strNumDocTbl, bdePorRet, bdeValRetTbl);
                    strAux = getCodSri(intCodTipRet, strIsItmFacSrvTra);
                    objTblMod.setValueAt(strAux, intNumFil, INT_TBL_CODSRIDOC);
                 }
              }
              
              int i =  intNumFil-1;
              for(int intNumFilDat=i; intNumFilDat >=  0;   intNumFilDat-- ){
              //  System.out.println("Numero de busqueda..> "+intNumFilDat  );
               //if( intNumFilDat != -1 ){
               //if( intNumFilDat != -1 ){
               if( intNumFilDat >= 0 ){
                   String strCodCli1 =  (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_CODCLI).toString());
                   String strCodCli2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_CODCLI)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_CODCLI).toString());
                   
                   bdePorRet = new BigDecimal(tblDat.getValueAt(intNumFil, INT_TBL_PORRET).toString());
                   bdePorRet = objUti.redondearBigDecimal(bdePorRet, objZafParSis.getDecimalesMostrar());
                   intCodTipRet = getCodTipRet(bdePorRet);
                   strCodLocTbl = tblDat.getValueAt(intNumFil, INT_TBL_CODLOC) == null ? "" :tblDat.getValueAt(intNumFil, INT_TBL_CODLOC).toString();
                   strNumDocTbl = tblDat.getValueAt(intNumFil, INT_TBL_NUMDOC) == null ? "" :tblDat.getValueAt(intNumFil, INT_TBL_NUMDOC).toString();
                    
                   strAux = tblDat.getValueAt(intNumFil, INT_TBL_VALDOC) == null? "" :tblDat.getValueAt(intNumFil, INT_TBL_VALDOC).toString();
                     
                   if (strAux.equals(""))
                   {  bdeValRetTbl = new BigDecimal("0.00");
                   }
                   else
                   {  bdeValRetTbl = new BigDecimal(strAux);
                      bdeValRetTbl = objUti.redondearBigDecimal(bdeValRetTbl, objZafParSis.getDecimalesMostrar());
                   }
                    
                   strIsItmFacSrvTra = isItemFacturaServicioTransporte(strCodLocTbl, strNumDocTbl, bdePorRet, bdeValRetTbl);
                   strAux = getCodSri(intCodTipRet, strIsItmFacSrvTra);
                   objTblMod.setValueAt(strAux, intNumFil, INT_TBL_CODSRIDOC);
                   
                 if(strCodCli1.equals(strCodCli2)){
                   String strNumSerDoc1 =  (tblDat.getValueAt(intNumFil, INT_TBL_NUMSECDOC)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_NUMSECDOC).toString()).trim();
                   String strNumSerDoc2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_NUMSECDOC)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_NUMSECDOC).toString()).trim();

                   String strNumAutSri1 =  (tblDat.getValueAt(intNumFil, INT_TBL_NUMAUTSRI)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_NUMAUTSRI).toString()).trim();
                   String strNumAutSri2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_NUMAUTSRI)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_NUMAUTSRI).toString()).trim();

                   String strFecCabSri1 =  (tblDat.getValueAt(intNumFil, INT_TBL_FECCATDOC)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_FECCATDOC).toString()).trim();
                   String strFecCabSri2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_FECCATDOC)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_FECCATDOC).toString()).trim();

                   strCodSri1 = "";
                   strCodSri2 = "";
                   
                   if (intCodTipRet == 0)
                   {  strCodSri1 = (tblDat.getValueAt(intNumFil, INT_TBL_CODSRIDOC)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_CODSRIDOC).toString()).trim();
                      strCodSri2 = (tblDat.getValueAt(intNumFilDat, INT_TBL_CODSRIDOC)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_CODSRIDOC).toString()).trim();
                   }
/*
                    if(strNumSerDoc1.equals(""))
                       tblDat.setValueAt(""+strNumSerDoc2, intNumFil, INT_TBL_NUMSECDOC);

                    if(strNumAutSri1.equals(""))
                       tblDat.setValueAt(""+strNumAutSri2, intNumFil, INT_TBL_NUMAUTSRI);

                    if(strFecCabSri1.equals(""))
                       tblDat.setValueAt(""+strFecCabSri2, intNumFil, INT_TBL_FECCATDOC);

                    if(strCodSri1.equals(""))
                       tblDat.setValueAt(""+strCodSri2, intNumFil, INT_TBL_CODSRIDOC);
*/

                   if(strNumSerDoc1.equals("")) objTblMod.setValueAt(""+strNumSerDoc2, intNumFil, INT_TBL_NUMSECDOC);
                   else if(!(strNumSerDoc1.equals(strNumSerDoc2)))  objTblMod.setValueAt(""+strNumSerDoc2, intNumFil, INT_TBL_NUMSECDOC);

                   if(strNumAutSri1.equals("")) objTblMod.setValueAt(""+strNumAutSri2, intNumFil, INT_TBL_NUMAUTSRI);
                   else if(!(strNumAutSri1.equals(strNumAutSri2))) objTblMod.setValueAt(""+strNumAutSri2, intNumFil, INT_TBL_NUMAUTSRI);

                   if(strFecCabSri1.equals("")) objTblMod.setValueAt(""+strFecCabSri2, intNumFil, INT_TBL_FECCATDOC);
                   else if(!(strFecCabSri1.equals(strFecCabSri2))) objTblMod.setValueAt(""+strFecCabSri2, intNumFil, INT_TBL_FECCATDOC);

                   if (intCodTipRet == 0)
                   {  if(strCodSri1.equals(""))  objTblMod.setValueAt(""+strCodSri2, intNumFil, INT_TBL_CODSRIDOC);
                      else if(!(strCodSri1.equals(strCodSri2)))  objTblMod.setValueAt(""+strCodSri2, intNumFil, INT_TBL_CODSRIDOC);
                   }
                    break;
                 }
               }else break;
            }
            CerrarCon();
            }
        });

        int intColCli[]=new int[9];
        intColCli[0]=1;
        intColCli[1]=2;
        intColCli[2]=3;
        intColCli[3]=4;
        intColCli[4]=5;
        intColCli[5]=6;
        intColCli[6]=7;
        intColCli[7]=8;
        intColCli[8]=9;

        int intColTblCli[]=new int[9];
        intColTblCli[0]=INT_TBL_CODTIPDOC;
        intColTblCli[1]=INT_TBL_DCOTIPDOC;
        intColTblCli[2]=INT_TBL_DLATIPDOC;
        intColTblCli[3]=INT_TBL_CODCTADEB;
        intColTblCli[4]=INT_TBL_DCOCTADEB;
        intColTblCli[5]=INT_TBL_NOMCTADEB;
        intColTblCli[6]=INT_TBL_CODCTAHAB;
        intColTblCli[7]=INT_TBL_DCOCTAHAB;
        intColTblCli[8]=INT_TBL_NOMCTAHAB;

        
        objTblCelEdiTxtVcoTipDoc=new ZafTblCelEdiTxtVco(tblDat, objVenConTipdocCon, intColCli, intColTblCli );
        tcmAux.getColumn(INT_TBL_DCOTIPDOC).setCellEditor(objTblCelEdiTxtVcoTipDoc);
        objTblCelEdiTxtVcoTipDoc.addTableEditorListener(new ZafTableAdapter() {
            @Override
            public void beforeEdit(ZafTableEvent evt){
                int intNumFil = tblDat.getSelectedRow();
                String strAsiFac= ""; // (tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC).toString()));
                String strDepo  = ""; // (tblDat.getValueAt(intNumFil, INT_TBL_CHKDEPO)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKDEPO).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKDEPO).toString()));
                String strSelRec=  (tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).toString()));

                if((strAsiFac.equals("false")))
                    objTblCelEdiTxtVcoTipDoc.setCancelarEdicion(true);

                if(strDepo.equals("true"))
                    objTblCelEdiTxtVcoTipDoc.setCancelarEdicion(true);

                if((strSelRec.equals("false")))
                    objTblCelEdiTxtVcoTipDoc.setCancelarEdicion(true);
            }
            @Override
            public void beforeConsultar(ZafTableEvent evt) {
                objVenConTipdocCon.setCampoBusqueda(1);
                objVenConTipdocCon.setCriterio1(11);
            }
            @Override
            public void afterEdit(ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow();

//                String strTipDoc  =  (tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOC)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOC).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOC).toString()));
//                String strTipDocOri=  (tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOCORI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOCORI).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOCORI).toString()));
//
//                if(!strTipDoc.equals(strTipDocOri)){
//                  tblDat.setValueAt("N",intNumFil, INT_TBL_ESTCHK);
//                }

                generaAsiento();
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
        objTblCelEdiTxtVcoBan.addTableEditorListener(new ZafTableAdapter() {
            @Override
            public void beforeEdit(ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow();
                if(intNumFil >= 0 ) {

                   String strEstApl= ""; // (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
                   String strAsigBan= ""; // (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));

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
            @Override
            public void afterEdit(ZafTableEvent evt) {

                  int intNumFil = tblDat.getSelectedRow();
                  String strCodBan = (tblDat.getValueAt(intNumFil, INT_TBL_CODBAN)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString()));
                  if((strCodBan.trim().equals(""))) {
                     tblDat.setValueAt("", intNumFil, INT_TBL_NUMCTA);
                     tblDat.setValueAt("", intNumFil, INT_TBL_NUMCHQ);
                  }

            }
        });
        

        objTblCelRenBut=new ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBL_BUTCLI).setCellRenderer(objTblCelRenBut);
        tcmAux.getColumn(INT_TBL_BUTTIPDOC).setCellRenderer(objTblCelRenBut);
        tcmAux.getColumn(INT_TBL_BUTBAN).setCellRenderer(objTblCelRenBut);
        tcmAux.getColumn(INT_TBL_BUTTIPDOC).setCellRenderer(objTblCelRenBut);
        tcmAux.getColumn(INT_TBL_BUTRET).setCellRenderer(objTblCelRenBut);
        objTblCelRenBut=null;
        
        ButBan butBan = new ButBan(tblDat, INT_TBL_BUTBAN);
        ButTipDoc butTipDoc1 = new ButTipDoc(tblDat, INT_TBL_BUTTIPDOC);

          //------------------------------------------------------------------
    //Eddye_ventana de documentos pendientes//
    objTblCelEdiButGen=new ZafTblCelEdiButGen();
    tblDat.getColumnModel().getColumn(INT_TBL_BUTCLI).setCellEditor(objTblCelEdiButGen);
    objTblCelEdiButGen.addTableEditorListener(new ZafTableAdapter() {
        @Override
        public void actionPerformed(ZafTableEvent evt) {
            int intFilSel, intFilSelVenCon[], i, j, intCodTipRet;
            String strCodCli, strNomCli, strValDoc, strAux, strCodLocTbl, strNumDocTbl, strIsItmFacSrvTra;
            double dblValDoc=0;
            BigDecimal bdePorRet, bdeValRetTbl;

           String strNumSerDoc1 = "";
           String strNumSerDoc2 = "";
           String strNumAutSri1 = "";
           String strNumAutSri2 = "";
           String strFecCabSri1 = "";
           String strFecCabSri2 = "";
           String strCodSri1 =  "";
           String strCodSri2 = "";

            int intNumFil = tblDat.getSelectedRow();
            //int intNumFilDat =  intNumFil-1;
            int intConFil=0;
            int x1 =  intNumFil-1;

//            if( intNumFilDat != -1 ){
//                 String strCodCli1 =  (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_CODCLI).toString());
//                 String strCodCli2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_CODCLI)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_CODCLI).toString());
//                 if(strCodCli1.equals(strCodCli2)){
//
//                   String strNumSerDoc1 =  (tblDat.getValueAt(intNumFil, INT_TBL_NUMSECDOC)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_NUMSECDOC).toString()).trim();
//                   String strNumSerDoc2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_NUMSECDOC)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_NUMSECDOC).toString()).trim();
//                   String strNumAutSri1 =  (tblDat.getValueAt(intNumFil, INT_TBL_NUMAUTSRI)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_NUMAUTSRI).toString()).trim();
//                   String strNumAutSri2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_NUMAUTSRI)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_NUMAUTSRI).toString()).trim();
//                   String strFecCabSri1 =  (tblDat.getValueAt(intNumFil, INT_TBL_FECCATDOC)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_FECCATDOC).toString()).trim();
//                   String strFecCabSri2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_FECCATDOC)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_FECCATDOC).toString()).trim();
//                   String strCodSri1 =  (tblDat.getValueAt(intNumFil, INT_TBL_CODSRIDOC)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_CODSRIDOC).toString()).trim();
//                   String strCodSri2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_CODSRIDOC)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_CODSRIDOC).toString()).trim();
//
//                   if(strNumSerDoc1.equals("")) tblDat.setValueAt(""+strNumSerDoc2, intNumFil, INT_TBL_NUMSECDOC);
//                   if(strNumAutSri1.equals("")) tblDat.setValueAt(""+strNumAutSri2, intNumFil, INT_TBL_NUMAUTSRI);
//                   if(strFecCabSri1.equals("")) tblDat.setValueAt(""+strFecCabSri2, intNumFil, INT_TBL_FECCATDOC);
//                   if(strCodSri1.equals(""))  tblDat.setValueAt(""+strCodSri2, intNumFil, INT_TBL_CODSRIDOC);
//
//                 }
//            }

            if (tblDat.getSelectedColumn()==INT_TBL_BUTCLI)
            {
                objVenConCxC01.setVisible(true);
            }
            if (objVenConCxC01.getSelectedButton()==ZafVenConCxC01.INT_BUT_ACE)
            {   abrirCon();
                intFilSel=tblDat.getSelectedRow();
                intFilSelVenCon=objVenConCxC01.getFilasSeleccionadas();
                strCodCli=objVenConCxC01.getCodigoCliente();
                strNomCli=objVenConCxC01.getNombreCliente();
                j=intFilSel;
                for (i=0; i<intFilSelVenCon.length; i++)
                {
                    //if (objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_LIN)!="P")
                    if (! objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_LIN).toString().equals("P"))
                    {
                        objTblMod.insertRow(j);
                        
                        strValDoc=objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_VAL_CHQ);
                        strValDoc=(strValDoc==null?"0":(strValDoc.equals("")?"0":strValDoc));
                        dblValDoc=objUti.redondear(strValDoc, 4);

                        if( dblValDoc > 0 ) objTblMod.setValueAt( true, j, INT_TBL_CHKSEL );
                        else objTblMod.setValueAt( false, j, INT_TBL_CHKSEL );

                        objTblMod.setValueAt(strCodCli, j, INT_TBL_CODCLI);
                        objTblMod.setValueAt(strNomCli, j, INT_TBL_NOMCLI);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_COD_LOC), j, INT_TBL_CODLOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_COD_TIP_DOC), j, INT_TBL_CODTID);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_DEC_TIP_DOC), j, INT_TBL_DCTIPDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_DEL_TIP_DOC), j, INT_TBL_DLTIPDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_COD_DOC), j, INT_TBL_CODDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_COD_REG), j, INT_TBL_CODREG);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_NUM_DOC), j, INT_TBL_NUMDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_FEC_DOC), j, INT_TBL_FECDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_DIA_CRE), j, INT_TBL_DIACRE);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_FEC_VEN), j, INT_TBL_FECVEN);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_POR_RET), j, INT_TBL_PORRET);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_VAL_DOC), j, INT_TBL_VALDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_VAL_PEN), j, INT_TBL_VALPEN);
                        objTblMod.setValueAt( ""+objZafParSis.getCodigoEmpresa()  , j, INT_TBL_CODEMP );
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_NUM_CHQ), j, INT_TBL_NUMCHQ);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_VAL_CHQ), j, INT_TBL_ABONO);

                        objTblMod.setValueAt(objVenConCxC01.getNumeroSerieRetencion(), j, INT_TBL_NUMSECDOC);
                        objTblMod.setValueAt(objVenConCxC01.getNumeroAutorizacionRetencion(), j, INT_TBL_NUMAUTSRI);
                        objTblMod.setValueAt(objVenConCxC01.getFechaCaducidadRetencion(), j, INT_TBL_FECCATDOC);
                        
                        bdePorRet = new BigDecimal(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_POR_RET));
                        bdePorRet = objUti.redondearBigDecimal(bdePorRet, objZafParSis.getDecimalesMostrar());
                        intCodTipRet = getCodTipRet(bdePorRet);
                        strCodLocTbl = tblDat.getValueAt(j, INT_TBL_CODLOC) == null ? "" :tblDat.getValueAt(j, INT_TBL_CODLOC).toString();
                        strNumDocTbl = tblDat.getValueAt(j, INT_TBL_NUMDOC) == null ? "" :tblDat.getValueAt(j, INT_TBL_NUMDOC).toString();

                        strAux = tblDat.getValueAt(j, INT_TBL_VALDOC) == null? "" :tblDat.getValueAt(j, INT_TBL_VALDOC).toString();

                        if (strAux.equals(""))
                        {  bdeValRetTbl = new BigDecimal("0.00");
                        }
                        else
                        {  bdeValRetTbl = new BigDecimal(strAux);
                           bdeValRetTbl = objUti.redondearBigDecimal(bdeValRetTbl, objZafParSis.getDecimalesMostrar());
                        }

                        strIsItmFacSrvTra = isItemFacturaServicioTransporte(strCodLocTbl, strNumDocTbl, bdePorRet, bdeValRetTbl);
                        
                        if (intCodTipRet == 0)
                           objTblMod.setValueAt(objVenConCxC01.getCodigoSRIRetencion(), j, INT_TBL_CODSRIDOC);
                        else
                        {  strAux = getCodSri(intCodTipRet, strIsItmFacSrvTra);
                           objTblMod.setValueAt(strAux, j, INT_TBL_CODSRIDOC);
                        }
                        
                        if (verificaPorcentajeRetencionExisteTabla(tblDat, j) == true)
                        {  //No se debe permitir que la informacion sea ingresada en el JTable.
                           return;
                        }
                        
                     //   if( intNumFilDat != -1 ){
                                 
                              if(intConFil==0){
                               for(int intNumFilDat=x1; intNumFilDat >=  0;   intNumFilDat-- ){
                                 // System.out.println("Caso--> "+intNumFilDat );
                                  if( intNumFilDat >= 0 ){
                                       String strCodCli1 =  (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_CODCLI).toString());
                                       String strCodCli2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_CODCLI)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_CODCLI).toString());
                                       if(strCodCli1.equals(strCodCli2)){
                                             if(intConFil==0){
                                                strNumSerDoc1 =  (tblDat.getValueAt(intNumFil, INT_TBL_NUMSECDOC)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_NUMSECDOC).toString()).trim();
                                                strNumSerDoc2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_NUMSECDOC)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_NUMSECDOC).toString()).trim();
                                                strNumAutSri1 =  (tblDat.getValueAt(intNumFil, INT_TBL_NUMAUTSRI)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_NUMAUTSRI).toString()).trim();
                                                strNumAutSri2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_NUMAUTSRI)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_NUMAUTSRI).toString()).trim();
                                                strFecCabSri1 =  (tblDat.getValueAt(intNumFil, INT_TBL_FECCATDOC)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_FECCATDOC).toString()).trim();
                                                strFecCabSri2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_FECCATDOC)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_FECCATDOC).toString()).trim();
                                                
                                                if (intCodTipRet == 0)
                                                {  strCodSri1 =  (tblDat.getValueAt(intNumFil, INT_TBL_CODSRIDOC)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_CODSRIDOC).toString()).trim();
                                                   strCodSri2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_CODSRIDOC)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_CODSRIDOC).toString()).trim();
                                                }

                                                intConFil=1;
                                             }
                                           break;
                                       }
                                  }else break;
                               }
                              }

                             if(intConFil==1){
                               if(strNumSerDoc1.equals("")) objTblMod.setValueAt(""+strNumSerDoc2, j, INT_TBL_NUMSECDOC);
                               else if(!(strNumSerDoc1.equals(strNumSerDoc2)))  objTblMod.setValueAt(""+strNumSerDoc2, j, INT_TBL_NUMSECDOC);

                               if(strNumAutSri1.equals("")) objTblMod.setValueAt(""+strNumAutSri2, j, INT_TBL_NUMAUTSRI);
                               else if(!(strNumAutSri1.equals(strNumAutSri2))) objTblMod.setValueAt(""+strNumAutSri2, j, INT_TBL_NUMAUTSRI);

                               if(strFecCabSri1.equals("")) objTblMod.setValueAt(""+strFecCabSri2, j, INT_TBL_FECCATDOC);
                               else if(!(strFecCabSri1.equals(strFecCabSri2))) objTblMod.setValueAt(""+strFecCabSri2, j, INT_TBL_FECCATDOC);

                               if (intCodTipRet == 0)
                               {  if(strCodSri1.equals(""))  objTblMod.setValueAt(""+strCodSri2, j, INT_TBL_CODSRIDOC);
                                  else if(!(strCodSri1.equals(strCodSri2)))  objTblMod.setValueAt(""+strCodSri2, j, INT_TBL_CODSRIDOC);
                               }
                             }


                           //  }
                       // }

                        objVenConCxC01.setFilaProcesada(intFilSelVenCon[i]);
                        j++;
                    }
                }
                tblDat.requestFocus();
                calculaTotMonAbo();
                CerrarCon();
                //objTblMod.removeEmptyRows();
            } //if (objVenConCxC01.getSelectedButton()==ZafVenConCxC01.INT_BUT_ACE)
        }
    });
    
    tblDat.getColumnModel().getColumn(INT_TBL_BUTRET).setCellEditor(objTblCelEdiButGen);
    objTblCelEdiButGen.addTableEditorListener(new ZafTableAdapter() {
        @Override
        public void actionPerformed(ZafTableEvent evt) {
            int intFilSel, intFilSelVenCon[], i, j, intCodTipRet, intCodEmp, intCodCli;
            String strCodCli, strNomCli, strValDoc, strAux;
            double dblValDoc=0;
            BigDecimal bdePorRet;
            java.sql.Connection conn;

           String strNumSerDoc1 = "";
           String strNumSerDoc2 = "";
           String strNumAutSri1 = "";
           String strNumAutSri2 = "";
           String strFecCabSri1 = "";
           String strFecCabSri2 = "";
           String strCodSri1 =  "";
           String strCodSri2 = "", strLocIdeEmi;

            int intNumFil = tblDat.getSelectedRow();
            int intConFil=0;
            int x1 =  intNumFil-1;
            
            strIdeEmi = "";
            strNumRet = "";
            strNumSer = "";
            strGloNumAutSri = "";
            objTblMod.setValueAt("", intNumFil, INT_TBL_IDEEMI);
            objTblMod.setValueAt("", intNumFil, INT_TBL_NUMCHQ);
            objTblMod.setValueAt("", intNumFil, INT_TBL_NUMSECDOC);
            objTblMod.setValueAt("", intNumFil, INT_TBL_NUMAUTSRI);
            strCodCli = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI) == null? "" :tblDat.getValueAt(intNumFil,INT_TBL_CODCLI).toString());
            
            if (strCodCli.equals(""))
            {  
               MensajeInf("Primero debe seleccionar un cliente antes de pulsar el botón de búsqueda de Retenciones");
               return;
            }

            if (tblDat.getSelectedColumn()==INT_TBL_BUTRET)
            {
                //objVenConCxC01.setVisible(true);
               //configurarVenConRet("0991513345001");
               strAux = tblDat.getValueAt(intNumFil, INT_TBL_CODEMP).toString();
               intCodEmp = Integer.parseInt(strAux);
               strAux = tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString();
               intCodCli = Integer.parseInt(strAux);
               strLocIdeEmi = getIdeEmisor1(intCodEmp, intCodCli);
               configurarVenConRet(strLocIdeEmi);
               mostrarVenConRet(0);
            }
            
            //if (objVenConCxC01.getSelectedButton()==ZafVenConCxC01.INT_BUT_ACE)
            if (vcoRet.getSelectedButton() == vcoRet.INT_BUT_ACE)
            {   intFilSel = tblDat.getSelectedRow();
                objTblMod.setValueAt(strIdeEmi, intNumFil, INT_TBL_IDEEMI);
                objTblMod.setValueAt(strNumRet, intNumFil, INT_TBL_NUMCHQ);
                objTblMod.setValueAt(strNumSer, intNumFil, INT_TBL_NUMSECDOC);
                objTblMod.setValueAt(strGloNumAutSri, intNumFil, INT_TBL_NUMAUTSRI);
                
                //************************************************************************
                /*
                abrirCon();
                intFilSel=tblDat.getSelectedRow();
                intFilSelVenCon=objVenConCxC01.getFilasSeleccionadas();
                strCodCli=objVenConCxC01.getCodigoCliente();
                strNomCli=objVenConCxC01.getNombreCliente();
                j=intFilSel;
                for (i=0; i<intFilSelVenCon.length; i++)
                {
                    if (! objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_LIN).toString().equals("P"))
                    {
                        objTblMod.insertRow(j);
                        
                        strValDoc=objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_VAL_CHQ);
                        strValDoc=(strValDoc==null?"0":(strValDoc.equals("")?"0":strValDoc));
                        dblValDoc=objUti.redondear(strValDoc, 4);

                        if( dblValDoc > 0 ) objTblMod.setValueAt( true, j, INT_TBL_CHKSEL );
                        else objTblMod.setValueAt( false, j, INT_TBL_CHKSEL );

                        objTblMod.setValueAt(strCodCli, j, INT_TBL_CODCLI);
                        objTblMod.setValueAt(strNomCli, j, INT_TBL_NOMCLI);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_COD_LOC), j, INT_TBL_CODLOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_COD_TIP_DOC), j, INT_TBL_CODTID);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_DEC_TIP_DOC), j, INT_TBL_DCTIPDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_DEL_TIP_DOC), j, INT_TBL_DLTIPDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_COD_DOC), j, INT_TBL_CODDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_COD_REG), j, INT_TBL_CODREG);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_NUM_DOC), j, INT_TBL_NUMDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_FEC_DOC), j, INT_TBL_FECDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_DIA_CRE), j, INT_TBL_DIACRE);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_FEC_VEN), j, INT_TBL_FECVEN);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_POR_RET), j, INT_TBL_PORRET);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_VAL_DOC), j, INT_TBL_VALDOC);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_VAL_PEN), j, INT_TBL_VALPEN);
                        objTblMod.setValueAt( ""+objZafParSis.getCodigoEmpresa()  , j, INT_TBL_CODEMP );
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_NUM_CHQ), j, INT_TBL_NUMCHQ);
                        objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_VAL_CHQ), j, INT_TBL_ABONO);

                        objTblMod.setValueAt(objVenConCxC01.getNumeroSerieRetencion(), j, INT_TBL_NUMSECDOC);
                        objTblMod.setValueAt(objVenConCxC01.getNumeroAutorizacionRetencion(), j, INT_TBL_NUMAUTSRI);
                        objTblMod.setValueAt(objVenConCxC01.getFechaCaducidadRetencion(), j, INT_TBL_FECCATDOC);
                        
                        bdePorRet = new BigDecimal(objVenConCxC01.getValueAt(intFilSelVenCon[i], ZafVenConCxC01.INT_TBL_DAT_POR_RET));
                        bdePorRet = objUti.redondearBigDecimal(bdePorRet, objZafParSis.getDecimalesMostrar());
                        intCodTipRet = getCodTipRet(bdePorRet);
                        
                        if (intCodTipRet == 0)
                           objTblMod.setValueAt(objVenConCxC01.getCodigoSRIRetencion(), j, INT_TBL_CODSRIDOC);
                        else
                        {  strAux = getCodSri(intCodTipRet);
                           objTblMod.setValueAt(strAux, j, INT_TBL_CODSRIDOC);
                        }
                        
                              if(intConFil==0){
                               for(int intNumFilDat=x1; intNumFilDat >=  0;   intNumFilDat-- ){
                                 // System.out.println("Caso--> "+intNumFilDat );
                                  if( intNumFilDat >= 0 ){
                                       String strCodCli1 =  (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_CODCLI).toString());
                                       String strCodCli2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_CODCLI)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_CODCLI).toString());
                                       if(strCodCli1.equals(strCodCli2)){
                                             if(intConFil==0){
                                                strNumSerDoc1 =  (tblDat.getValueAt(intNumFil, INT_TBL_NUMSECDOC)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_NUMSECDOC).toString()).trim();
                                                strNumSerDoc2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_NUMSECDOC)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_NUMSECDOC).toString()).trim();
                                                strNumAutSri1 =  (tblDat.getValueAt(intNumFil, INT_TBL_NUMAUTSRI)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_NUMAUTSRI).toString()).trim();
                                                strNumAutSri2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_NUMAUTSRI)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_NUMAUTSRI).toString()).trim();
                                                strFecCabSri1 =  (tblDat.getValueAt(intNumFil, INT_TBL_FECCATDOC)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_FECCATDOC).toString()).trim();
                                                strFecCabSri2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_FECCATDOC)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_FECCATDOC).toString()).trim();
                                                
                                                if (intCodTipRet == 0)
                                                {  strCodSri1 =  (tblDat.getValueAt(intNumFil, INT_TBL_CODSRIDOC)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_CODSRIDOC).toString()).trim();
                                                   strCodSri2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_CODSRIDOC)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_CODSRIDOC).toString()).trim();
                                                }

                                                intConFil=1;
                                             }
                                           break;
                                       }
                                  }else break;
                               }
                              }

                             if(intConFil==1){
                               if(strNumSerDoc1.equals("")) objTblMod.setValueAt(""+strNumSerDoc2, j, INT_TBL_NUMSECDOC);
                               else if(!(strNumSerDoc1.equals(strNumSerDoc2)))  objTblMod.setValueAt(""+strNumSerDoc2, j, INT_TBL_NUMSECDOC);

                               if(strNumAutSri1.equals("")) objTblMod.setValueAt(""+strNumAutSri2, j, INT_TBL_NUMAUTSRI);
                               else if(!(strNumAutSri1.equals(strNumAutSri2))) objTblMod.setValueAt(""+strNumAutSri2, j, INT_TBL_NUMAUTSRI);

                               if(strFecCabSri1.equals("")) objTblMod.setValueAt(""+strFecCabSri2, j, INT_TBL_FECCATDOC);
                               else if(!(strFecCabSri1.equals(strFecCabSri2))) objTblMod.setValueAt(""+strFecCabSri2, j, INT_TBL_FECCATDOC);

                               if (intCodTipRet == 0)
                               {  if(strCodSri1.equals(""))  objTblMod.setValueAt(""+strCodSri2, j, INT_TBL_CODSRIDOC);
                                  else if(!(strCodSri1.equals(strCodSri2)))  objTblMod.setValueAt(""+strCodSri2, j, INT_TBL_CODSRIDOC);
                               }
                             }

                        objVenConCxC01.setFilaProcesada(intFilSelVenCon[i]);
                        j++;
                    }
                }
                tblDat.requestFocus();
                calculaTotMonAbo();
                CerrarCon();
                */
            }
        }
    });
  //------------------------------------------------------------------


    setEditable(false);

//     new ZafTblOrd(tblDat);

   objTblPopMnu = new ZafTblPopMnu(tblDat);
   // objTblPopMnu.setEliminarFilaVisible(false);
    objTblPopMnu.setInsertarFilasVisible(false);
    objTblPopMnu.setInsertarFilaVisible(false);

    objTblPopMnu.addTblPopMnuListener(new ZafTblPopMnuAdapter() {
        @Override
        public void beforeClick(ZafTblPopMnuEvent evt) {
            if(objTblPopMnu.isClickEliminarFila()){
              if(!verificaSelEli()){
                 MensajeInf("NO SE PUEDE ELIMINAR. SOLO SE PUEDE ELIMINAR DATOS NUEVOS.  ");
                 objTblPopMnu.cancelarClick();
               }
            }
        }
        @Override
        public void afterClick(ZafTblPopMnuEvent evt) {
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


     //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    tblDatMouseClicked(evt);
                }
            });
            
      configurarVenConRet("");
      blnRes=true;
   }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
    return blnRes;
}

public void setEditable(boolean editable) {
  if (editable==true){
    objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);

 }else{  objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI); }
}

     /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConRet(int intTipBus)
    {
        boolean blnRes = true;
        String strAux1, strAux2, strNumSerNumRet;
        int intAux;
        
        try
        {  switch (intTipBus)
           {  case 0: //Mostrar la ventana de consulta
                 vcoRet.setCampoBusqueda(0);
                 vcoRet.show();
                 if (vcoRet.getSelectedButton() == vcoRet.INT_BUT_ACE)
                 {  strNumSerNumRet = vcoRet.getValueAt(2);
                    strGloNumAutSri = vcoRet.getValueAt(3);
                    strIdeEmi = vcoRet.getValueAt(4);
                    strAux1 = strNumSerNumRet.substring(0, 3); // Si strNumSerNumRet = 024-025-000002449, va a extraer "024"
                    strAux2 = strNumSerNumRet.substring(4, 7); // Si strNumSerNumRet = 024-025-000002449, va a extraer "025"
                    strNumSer = strAux1 + strAux2;
                    strAux1 = strNumSerNumRet.substring(8, 17); // Si strNumSerNumRet = 024-025-000002449, va a extraer "000002449"
                    intAux = Integer.parseInt(strAux1);
                    strNumRet = Integer.toString(intAux);
                    //objTblMod.removeAllRows();
                 }
                 break;
                
              /*case 1: //Busqueda directa por "Codigo de proveedor"
                 if (vcoRet.buscar("a1.co_cli", txtCodPrv.getText()))
                 {  //txtCodPrv.setText(vcoRet.getValueAt(1));
                    //txtDesLarPrv.setText(vcoRet.getValueAt(3));
                    objTblMod.removeAllRows();
                 }
                 else
                 {  vcoRet.setCampoBusqueda(0);
                    vcoRet.setCriterio1(11);
                    vcoRet.cargarDatos();
                    vcoRet.show();
                    if (vcoRet.getSelectedButton() == vcoRet.INT_BUT_ACE)
                    {  //txtCodPrv.setText(vcoRet.getValueAt(1));
                       //txtDesLarPrv.setText(vcoRet.getValueAt(3));
                       objTblMod.removeAllRows();
                    }
                    //else
                    //   txtCodPrv.setText(strCodPrv);
                 }
                 break;*/
                 
              /*case 2: //Busqueda directa por "Descripcion larga"
                 if (vcoRet.buscar("a1.tx_nom", txtDesLarPrv.getText()))
                 {  txtCodPrv.setText(vcoRet.getValueAt(1));
                    txtDesLarPrv.setText(vcoRet.getValueAt(3));
                    objTblMod.removeAllRows();
                 }
                 else
                 {  vcoRet.setCampoBusqueda(2);
                    vcoRet.setCriterio1(11);
                    vcoRet.cargarDatos();
                    vcoRet.show();
                    if (vcoRet.getSelectedButton() == vcoRet.INT_BUT_ACE)
                    {  txtCodPrv.setText(vcoRet.getValueAt(1));
                       txtDesLarPrv.setText(vcoRet.getValueAt(3));
                       objTblMod.removeAllRows();
                    }
                    else
                       txtDesLarPrv.setText(strDesLarPrv);
                 }
                 break;*/
            }
        }
        
        catch (Exception e)
        {  blnRes = false;
           objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    } //Funcion mostrarVenConRet()
    
    /**
     * Esta funcion configura la "Ventana de consulta" que será utilizada para
     * mostrar los Num. de autorizacion de las Retenciones.
     */
    private boolean configurarVenConRet(String strPrmIdeEmi)
    {
       boolean blnRes = true;
       String strSQL;
       
       try
       {  //Listado de campos
          ArrayList arlCam = new ArrayList();
          arlCam.add("tx_nomemi");
          arlCam.add("tx_numdoc");
          arlCam.add("tx_numaut");
          arlCam.add("tx_ideemi");
          //Alias de los campos
          ArrayList arlAli = new ArrayList();
          arlAli.add("Nombre");
          arlAli.add("Num. retención");
          arlAli.add("Num. autorización");
          arlAli.add("ID emisor");
          //Ancho de las columnas
          ArrayList arlAncCol = new ArrayList();
          arlAncCol.add("250");
          arlAncCol.add("120");
          arlAncCol.add("270");
          arlAncCol.add("50");
          //Armar la sentencia SQL
          strSQL = "";
          strSQL += "SELECT (select tx_nom from tbm_cli where st_reg = 'A' and tx_ide = a1.tx_ideemi and co_emp = " + objZafParSis.getCodigoEmpresa() + ") as tx_nomemi, ";
          strSQL += "tx_numdoc, tx_numaut, tx_ideemi ";
          strSQL += "FROM tbm_cabdocelesri as a1 ";
          strSQL += "WHERE tx_tipdoc = 'R' and st_reg = 'A' and (st_asidocrel = 'N' or st_asidocrel is null) ";
          strSQL += "and tx_iderec = (select tx_ruc from tbm_emp where st_reg = 'A' and co_emp = " + objZafParSis.getCodigoEmpresa() + ") ";
          strSQL += "and tx_ideemi = '" + strPrmIdeEmi + "' ";
          strSQL += "ORDER BY tx_nomemi, tx_numdoc";

          //Ocultar columnas
          int intColOcu[] = new int[1];
          intColOcu[0] = 4;
          vcoRet = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de retenciones", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
          
          arlCam = null;
          arlAli = null;
          arlAncCol = null;
          intColOcu = null;
          //Configurar columnas
          //vcoRet.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
       }
       
       catch (Exception e)
       {  blnRes = false;
          objUti.mostrarMsgErr_F1(this, e);
       }
       
       return blnRes;
    }

 /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     */
    private void tblDatMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=tblDat.getRowCount();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.


             if (evt.getButton()==MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==INT_TBL_CHKSEL){
              if (blnMarTodCanTblDat){
                   //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                 if(objZafParSis.getCodigoMenu()==256){

                    if( tblDat.getValueAt(i, INT_TBL_CODREGAAJU)==null){
                      //String strValPen = (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString()));
                      double dblValPen = objUti.redondear( (tblDat.getValueAt(i, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALPEN).toString())), 2);
                      dblValPen = Math.abs(dblValPen);

                      tblDat.setValueAt(""+dblValPen, i, INT_TBL_ABONO);
                      tblDat.setValueAt(""+dblValPen, i, INT_TBL_VALCHQ);
                    }else{
                        tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_ABONOORI), i, INT_TBL_ABONO);
                        tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_ABONOORI), i, INT_TBL_VALCHQ);
                    }
                 }else if(objZafParSis.getCodigoMenu()==1648){

                     if( tblDat.getValueAt(i, INT_TBL_CODREGAAJU)==null){
                        double dblValPen = objUti.redondear( (tblDat.getValueAt(i, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALPEN).toString())), 2);
                        dblValPen = Math.abs(dblValPen);
                       tblDat.setValueAt(""+dblValPen, i, INT_TBL_ABONO);
                     }else{
                        tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_ABONOORI), i, INT_TBL_ABONO);
                     }

                 }else{
                       if( tblDat.getValueAt(i, INT_TBL_CODREGAAJU)==null){
                          //String strValPen = (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString()));
                          double dblValPen = objUti.redondear( (tblDat.getValueAt(i, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALPEN).toString())), 2);
                          dblValPen = Math.abs(dblValPen);

                          tblDat.setValueAt(""+dblValPen, i, INT_TBL_ABONO);
                          tblDat.setValueAt(""+dblValPen, i, INT_TBL_VALCHQ);
                        }else{
                            tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_ABONOORI), i, INT_TBL_ABONO);
                            tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_ABONOORI), i, INT_TBL_VALCHQ);
                        }

                 }





                      tblDat.setValueAt( true, i, INT_TBL_CHKSEL);

                    }
                    blnMarTodCanTblDat=false;
               }else{
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        tblDat.setValueAt( false, i, INT_TBL_CHKSEL);


                         if(objZafParSis.getCodigoMenu()==256){

                                tblDat.setValueAt(  "0", i, INT_TBL_ABONO);
                                tblDat.setValueAt(  "0", i, INT_TBL_VALCHQ);

                         }  else if(objZafParSis.getCodigoMenu()==1648){
                               tblDat.setValueAt( "0", i, INT_TBL_ABONO);
                         }else{
                               tblDat.setValueAt( "0", i, INT_TBL_ABONO);
                               tblDat.setValueAt( "0", i, INT_TBL_VALCHQ);
                         }
                       




                    }
                    blnMarTodCanTblDat=true;
                }
                 calculaTotMonAbo();
             }
        } catch (Exception e) {     objUti.mostrarMsgErr_F1(this, e);  }
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
      if( ( (tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){

          strValDoc=(tblDat.getValueAt(i, INT_TBL_VALDOC)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALDOC).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALDOC).toString()));

          dblValDoc=objUti.redondear(strValDoc, 4);
          if(dblValDoc > 0 ){
            intTipMov=-1;
          }else{
              intTipMov=1;
          }

          dblMonChq += Math.abs( Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_ABONO)==null)?"0":(tblDat.getValueAt(i, INT_TBL_ABONO).toString()))) ) * intTipMov;

    }}
   dblMonChq=objUti.redondear(dblMonChq, 2);
   valDoc.setText(""+dblMonChq);

   if(!(strCodCtaDeb.equals("")))
     generaAsiento(dblMonChq);
   else generaAsiento();

 }catch(Exception e) {   objUti.mostrarMsgErr_F1(this,e);  }
}



private class ButBan extends Librerias.ZafTableColBut.ZafTableColBut{
    public ButBan(javax.swing.JTable tbl, int intIdx){
         super(tbl,intIdx);
    }
    
    @Override
    public void butCLick() {

     int intNumFil = tblDat.getSelectedRow();
      if(intNumFil >= 0 ) {

       String strSelRec=  (tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).toString()));


      String strEstApl= "N"; // (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
      String strExiRegIng= (tblDat.getValueAt(intNumFil, INT_TBL_CODREGAAJU)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_CODREGAAJU).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_CODREGAAJU).toString()));
      String strCodCLi = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
       System.out.println("strCodCLi--> "+ strCodCLi );
      if(!(strCodCLi.trim().equals(""))){
        if((strSelRec.equals("true"))){
         if(strEstApl.equals("N")){
          if(strExiRegIng.equals("N")){
                listaBancos();
          }
         }
      }}

    }
 }}

private void listaBancos(){
      objVenConBan.setTitle("Listado de Clientes");
      objVenConBan.show();
      if (objVenConBan.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
          tblDat.setValueAt(objVenConBan.getValueAt(1),tblDat.getSelectedRow(),INT_TBL_CODBAN);
          tblDat.setValueAt(objVenConBan.getValueAt(2),tblDat.getSelectedRow(),INT_TBL_DSCBAN);
          tblDat.setValueAt(objVenConBan.getValueAt(3),tblDat.getSelectedRow(),INT_TBL_NOMBAN);
}}
  
  

 private class ButTipDoc extends Librerias.ZafTableColBut.ZafTableColBut{
    public ButTipDoc(javax.swing.JTable tbl, int intIdx){
         super(tbl,intIdx);
    }
    
    @Override
    public void butCLick() {

     int intNumFil = tblDat.getSelectedRow();
      if(intNumFil >= 0 ) {

      String strSelRec=  (tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).toString()));

    



      String strEstApl= "N"; // (tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
      String strAsigBan= "N"; // (tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));
      String strCodCLi = (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString()));
       System.out.println("strCodCLi--> "+ strCodCLi );
      if(!(strCodCLi.trim().equals(""))){
       if((strSelRec.equals("true"))){
         if(strEstApl.equals("N")){
          if(strAsigBan.equals("N")){
                butCLickButTipDoc(intNumFil);
          }
         }
      }}

    }
 }}

public void butCLickButTipDoc(int intNumFil) {
    try{
      if(intNumFil >= 0 ) {
        String strAsiFac= "true";// (tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC).toString()));
        String strDepo  = "false"; // (tblDat.getValueAt(intNumFil, INT_TBL_CHKDEPO)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKDEPO).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKDEPO).toString()));
        String strSelRec= "true"; // (tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).toString()));


        if(strAsiFac.equals("true")){
         if(strDepo.equals("false")){
           if(strSelRec.equals("true")){
              listaTipDoc(intNumFil);
           }else  MensajeInf("SELECCIONE EL REGISTRO.. ");
         }else  MensajeInf("YA TIENE DEPOSITO NO ES POSOBLE CAMBIAR BANCO.. ");
       }else  MensajeInf("NO TIENE ASIGNADO FACTURAS EN SU TOTALIDAD ");
    }
  }catch(Exception evt){ objUti.mostrarMsgErr_F1(this, evt); }
 }

private void listaTipDoc(int intNumFil){
  try{
    objVenConTipdocCon.setTitle("Listado de tipo documento");
    objVenConTipdocCon.show();
    if (objVenConTipdocCon.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
        tblDat.setValueAt(objVenConTipdocCon.getValueAt(1),intNumFil,INT_TBL_CODTIPDOC);
        tblDat.setValueAt(objVenConTipdocCon.getValueAt(2),intNumFil,INT_TBL_DCOTIPDOC);
        tblDat.setValueAt(objVenConTipdocCon.getValueAt(3),intNumFil,INT_TBL_DLATIPDOC);
        tblDat.setValueAt(objVenConTipdocCon.getValueAt(4),intNumFil,INT_TBL_CODCTADEB);
        tblDat.setValueAt(objVenConTipdocCon.getValueAt(5),intNumFil,INT_TBL_DCOCTADEB);
        tblDat.setValueAt(objVenConTipdocCon.getValueAt(6),intNumFil,INT_TBL_NOMCTADEB);
        tblDat.setValueAt(objVenConTipdocCon.getValueAt(7),intNumFil,INT_TBL_CODCTAHAB);
        tblDat.setValueAt(objVenConTipdocCon.getValueAt(8),intNumFil,INT_TBL_DCOCTAHAB);
        tblDat.setValueAt(objVenConTipdocCon.getValueAt(9),intNumFil,INT_TBL_NOMCTAHAB);

        generaAsiento();

//         String strTipDoc  =  (tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOC)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOC).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOC).toString()));
//         String strTipDocOri=  (tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOCORI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOCORI).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOCORI).toString()));
//
//         if(!strTipDoc.equals(strTipDocOri)){
//            tblDat.setValueAt("N",intNumFil, INT_TBL_ESTCHK);
//         }

    }
  }catch(Exception evt){ objUti.mostrarMsgErr_F1(this, evt); }
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

private boolean configurarVenConTipDocCon() {
boolean blnRes=true;
try {
    ArrayList arlCam=new ArrayList();
    arlCam.add("a.co_tipdoc");
    arlCam.add("a.tx_descor");
    arlCam.add("a.tx_deslar");
    arlCam.add("a.co_ctadeb");
    arlCam.add("a.txctadeb");
    arlCam.add("a.nomctadeb");
    arlCam.add("a.co_ctahab");
    arlCam.add("a.txctahab");
    arlCam.add("a.nomctahab");

    ArrayList arlAli=new ArrayList();
    arlAli.add("Código");
    arlAli.add("Des.Cor.");
    arlAli.add("Des.Lar.");
    arlAli.add("");
    arlAli.add("");
    arlAli.add("");
    arlAli.add("");
    arlAli.add("");
    arlAli.add("");

    ArrayList arlAncCol=new ArrayList();
    arlAncCol.add("80");
    arlAncCol.add("110");
    arlAncCol.add("350");
    arlAncCol.add("80");
    arlAncCol.add("80");
    arlAncCol.add("80");
    arlAncCol.add("80");
    arlAncCol.add("80");
    arlAncCol.add("80");

    //Armar la sentencia SQL.   a7.nd_stkTot,
    String Str_Sql="";

     if(objZafParSis.getCodigoUsuario()==1){
      Str_Sql="SELECT * FROM ( Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar " +
      " ,a.co_ctadeb, a1.tx_codcta AS txctadeb, a1.tx_deslar as nomctadeb,  a.co_ctahab, a2.tx_codcta as txctahab, a2.tx_deslar as nomctahab   " +
      " FROM tbr_tipdocdetprg as b " +
      " left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
      " LEFT JOIN tbm_placta AS a1 on (a1.co_emp = a.co_emp and a1.co_cta = a.co_ctadeb ) " +
      " LEFT JOIN tbm_placta AS a2 on (a2.co_emp = a.co_emp and a2.co_cta = a.co_ctahab ) " +
      " WHERE   b.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
      " b.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
      " b.co_mnu = " + objZafParSis.getCodigoMenu()+" ) AS a ";
     }else {
            Str_Sql ="SELECT * FROM ( SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar " +
            " ,a.co_ctadeb, a3.tx_codcta AS txctadeb, a3.tx_deslar as nomctadeb,  a.co_ctahab, a2.tx_codcta as txctahab, a2.tx_deslar as nomctahab  "+
            " FROM tbr_tipdocdetusr AS a1 " +
            " inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"+
            " LEFT JOIN tbm_placta AS a3 on (a3.co_emp = a.co_emp and a3.co_cta = a.co_ctadeb ) " +
            " LEFT JOIN tbm_placta AS a2 on (a2.co_emp = a.co_emp and a2.co_cta = a.co_ctahab ) " +
            " WHERE "+
            "  a1.co_emp=" + objZafParSis.getCodigoEmpresa()+""+
            " AND a1.co_loc=" + objZafParSis.getCodigoLocal()+""+
            " AND a1.co_mnu=" + objZafParSis.getCodigoMenu()+""+
            " AND a1.co_usr=" + objZafParSis.getCodigoUsuario()+"  ) AS a  ";
     }

    objVenConTipdocCon=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
    arlCam=null;
    arlAli=null;
    arlAncCol=null;

    objVenConTipdocCon.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
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


/**
 * MODIFICADO EFLORESA. MEJORA A RECEPCION DE RETENCIONES POR ECUATOSA. NO ESTA GENERANDO CORRECTAMENTE EL ASIENTO CONTABLE.
 */
private void generaAsiento(double dblValDoc){
  try{
     objAsiDia.inicializar();
     int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null.
     int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema).
     int INT_VEC_NUMCTA = 2; //2) Numero de la cuenta (Preimpreso).
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
       vecReg.add(INT_VEC_CODCTA, (strCodCtaDeb.equals("")?null:new Integer(strCodCtaDeb) ) );
       vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaDeb );
       vecReg.add(INT_VEC_BOTON, null);
       vecReg.add(INT_VEC_NOMCTA, strNomCtaDeb );
      if(objZafParSis.getCodigoMenu()==488){
       if(dblValDoc > 0 ){
          vecReg.add(INT_VEC_DEBE,  new Double(0));
          vecReg.add(INT_VEC_HABER, new Double(Math.abs( dblValDoc )));
       }else{
         vecReg.add(INT_VEC_DEBE,  new Double(Math.abs( dblValDoc )));
         vecReg.add(INT_VEC_HABER, new Double(0));
       }
      }else{
       if(dblValDoc > 0 ){
          vecReg.add(INT_VEC_DEBE,  new Double(Math.abs( dblValDoc )));
          vecReg.add(INT_VEC_HABER, new Double(0));
       }else{
         vecReg.add(INT_VEC_DEBE,  new Double(0));
         vecReg.add(INT_VEC_HABER, new Double(Math.abs( dblValDoc )));
       }
      }
       vecReg.add(INT_VEC_REF, null);
       vecReg.add(INT_VEC_NUEVO, null);

        vecDetDiario.add(vecReg);
        vecReg = new java.util.Vector();
        vecReg.add(INT_LINEA, null);
        
        //vecReg.add(INT_VEC_CODCTA,new Integer( strCodCtaHab ));
        //if (strCodCtaHab.equals(""))
        //    this.MensajeInf("No se ha encontrado la cuenta del HABER. Por favor no olvide ingresarla.");
        
        vecReg.add(INT_VEC_CODCTA,(strCodCtaHab.equals("")?null:new Integer( strCodCtaHab) ));
        
        vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaHab );
        vecReg.add(INT_VEC_BOTON, null);
        vecReg.add(INT_VEC_NOMCTA, strNomCtaHab );
       if(objZafParSis.getCodigoMenu()==488){
        if(dblValDoc > 0 ){
          vecReg.add(INT_VEC_DEBE,  new Double( Math.abs( dblValDoc ) ));
          vecReg.add(INT_VEC_HABER, new Double(0));
        }else{
          vecReg.add(INT_VEC_DEBE,  new Double(0));
          vecReg.add(INT_VEC_HABER, new Double( Math.abs( dblValDoc ) ));
        }
     }else{
        if(dblValDoc > 0 ){
          vecReg.add(INT_VEC_DEBE,  new Double( 0 ));
          vecReg.add(INT_VEC_HABER, new Double(Math.abs( dblValDoc )));
        }else{
          vecReg.add(INT_VEC_DEBE,  new Double(Math.abs( dblValDoc )));
          vecReg.add(INT_VEC_HABER, new Double( 0 ));
        }
       }
        vecReg.add(INT_VEC_REF, null);
        vecReg.add(INT_VEC_NUEVO, null);
        vecDetDiario.add(vecReg);

     objAsiDia.setDetalleDiario(vecDetDiario);
 }catch(Exception e) {   objUti.mostrarMsgErr_F1(this,e);  }
}

private void generaAsiento(){
  try{
     objAsiDia.inicializar();
     int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null.
     int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema).
     int INT_VEC_NUMCTA = 2; //2) Numero de la cuenta (Preimpreso).
     int INT_VEC_BOTON  = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null.
     int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta.
     int INT_VEC_DEBE   = 5; //5) Debe.
     int INT_VEC_HABER  = 6; //6) Haber.
     int INT_VEC_REF    = 7; //7) Referencia: Se debe asignar una cadena vacía o null
     int INT_VEC_NUEVO  = 8; //7) Referencia: Se debe asignar una cadena vacía o null

     double dblAbono=0, dblValDoc=0;
     String strValDoc="";
     int intTipMov=0;


       if (vecDetDiario==null) vecDetDiario = new java.util.Vector();
         else vecDetDiario.removeAllElements();


    for(int i=0; i < tblDat.getRowCount(); i++){
     if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){
      if( !((tblDat.getValueAt(i, INT_TBL_CODTIPDOC)==null?"":(tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString())).equals("")) ){


       dblAbono= objUti.redondear( (tblDat.getValueAt(i, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_ABONO).toString())), 2 );


      strValDoc=(tblDat.getValueAt(i, INT_TBL_VALDOC)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALDOC).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALDOC).toString()));
      dblValDoc=objUti.redondear(strValDoc, 4);
      if(dblValDoc > 0 ){
        intTipMov=-1;
      }else{
          intTipMov=1;
      }

      java.util.Vector vecReg;

       vecReg = new java.util.Vector();
       vecReg.add(INT_LINEA, null);
       vecReg.add(INT_VEC_CODCTA, new Integer( tblDat.getValueAt(i, INT_TBL_CODCTADEB).toString() ) );
       vecReg.add(INT_VEC_NUMCTA, tblDat.getValueAt(i, INT_TBL_DCOCTADEB ).toString() );
       vecReg.add(INT_VEC_BOTON, null);
       vecReg.add(INT_VEC_NOMCTA, tblDat.getValueAt(i, INT_TBL_NOMCTADEB).toString() );
      if(objZafParSis.getCodigoMenu()==488){
       if(intTipMov==1){
        vecReg.add(INT_VEC_DEBE,  new Double(0));
        vecReg.add(INT_VEC_HABER, new Double(Math.abs(dblAbono)));
       }else{
        vecReg.add(INT_VEC_DEBE,  new Double(Math.abs(dblAbono)));
        vecReg.add(INT_VEC_HABER, new Double(0));
       }
      }else{
       if(intTipMov==1){
        vecReg.add(INT_VEC_DEBE,  new Double(Math.abs(dblAbono)));
        vecReg.add(INT_VEC_HABER, new Double(0));
       }else{
        vecReg.add(INT_VEC_DEBE,  new Double(0));
        vecReg.add(INT_VEC_HABER, new Double(Math.abs(dblAbono)));
       }
      }
       vecReg.add(INT_VEC_REF, null);
       vecReg.add(INT_VEC_NUEVO, null);

        vecDetDiario.add(vecReg);
        vecReg = new java.util.Vector();
        vecReg.add(INT_LINEA, null);
        vecReg.add(INT_VEC_CODCTA,new Integer( tblDat.getValueAt(i, INT_TBL_CODCTAHAB).toString() ));
        vecReg.add(INT_VEC_NUMCTA, tblDat.getValueAt(i, INT_TBL_DCOCTAHAB).toString() );
        vecReg.add(INT_VEC_BOTON, null);
        vecReg.add(INT_VEC_NOMCTA, tblDat.getValueAt(i, INT_TBL_NOMCTAHAB).toString() );
       if(objZafParSis.getCodigoMenu()==488){
        if(intTipMov==1){
         vecReg.add(INT_VEC_DEBE,  new Double( Math.abs(dblAbono) ));
         vecReg.add(INT_VEC_HABER, new Double(0));
        }else{
         vecReg.add(INT_VEC_DEBE,  new Double(0));
         vecReg.add(INT_VEC_HABER, new Double(Math.abs(dblAbono)));
        }
      }else{
        if(intTipMov==1){
         vecReg.add(INT_VEC_DEBE,  new Double( 0 ));
         vecReg.add(INT_VEC_HABER, new Double(Math.abs(dblAbono)));
        }else{
         vecReg.add(INT_VEC_DEBE,  new Double(Math.abs(dblAbono)));
         vecReg.add(INT_VEC_HABER, new Double(0));
        }
      }
        vecReg.add(INT_VEC_REF, null);
        vecReg.add(INT_VEC_NUEVO, null);
        vecDetDiario.add(vecReg);

    }}}

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
      jLabel2 = new javax.swing.JLabel();
      txtDesCorTipDocCon = new javax.swing.JTextField();
      txtDesCodTitpDoc = new javax.swing.JTextField();
      txtDesLarTipDocCon = new javax.swing.JTextField();
      txtDesLarTipDoc = new javax.swing.JTextField();
      butTipDoc = new javax.swing.JButton();
      butTipDocCon = new javax.swing.JButton();
      lblCodDoc = new javax.swing.JLabel();
      txtCodDoc = new javax.swing.JTextField();
      lblNumDoc = new javax.swing.JLabel();
      txtAlt1 = new javax.swing.JTextField();
      valDoc = new javax.swing.JTextField();
      lblCodDoc1 = new javax.swing.JLabel();
      jLabel6 = new javax.swing.JLabel();
      rdaAplMisCta = new javax.swing.JRadioButton();
      rdaAplDifCta = new javax.swing.JRadioButton();
      lblNumDoc1 = new javax.swing.JLabel();
      txtAlt2 = new javax.swing.JTextField();
      butCarArcRet = new javax.swing.JButton();
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

      panCabCob.setPreferredSize(new java.awt.Dimension(100, 115));
      panCabCob.setLayout(null);

      jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      jLabel3.setText("Tipo de documento:"); // NOI18N
      panCabCob.add(jLabel3);
      jLabel3.setBounds(10, 5, 110, 20);

      jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      jLabel2.setText("TipDoc. control :"); // NOI18N
      panCabCob.add(jLabel2);
      jLabel2.setBounds(40, 66, 90, 20);

      txtDesCorTipDocCon.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtDesCorTipDocConActionPerformed(evt);
         }
      });
      txtDesCorTipDocCon.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtDesCorTipDocConFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtDesCorTipDocConFocusLost(evt);
         }
      });
      panCabCob.add(txtDesCorTipDocCon);
      txtDesCorTipDocCon.setBounds(120, 66, 70, 20);

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

      txtDesLarTipDocCon.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtDesLarTipDocConActionPerformed(evt);
         }
      });
      txtDesLarTipDocCon.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtDesLarTipDocConFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtDesLarTipDocConFocusLost(evt);
         }
      });
      panCabCob.add(txtDesLarTipDocCon);
      txtDesLarTipDocCon.setBounds(190, 66, 230, 20);

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

      butTipDocCon.setText(".."); // NOI18N
      butTipDocCon.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butTipDocConActionPerformed(evt);
         }
      });
      panCabCob.add(butTipDocCon);
      butTipDocCon.setBounds(420, 66, 20, 20);

      lblCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblCodDoc.setText("Código del documento:"); // NOI18N
      panCabCob.add(lblCodDoc);
      lblCodDoc.setBounds(10, 25, 120, 20);

      txtCodDoc.setBackground(objZafParSis.getColorCamposSistema());
      panCabCob.add(txtCodDoc);
      txtCodDoc.setBounds(120, 25, 90, 20);

      lblNumDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
      valDoc.setBounds(580, 65, 90, 20);

      lblCodDoc1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblCodDoc1.setText("Valor del Documento:"); // NOI18N
      panCabCob.add(lblCodDoc1);
      lblCodDoc1.setBounds(460, 65, 120, 20);

      jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      jLabel6.setText("Fecha del documento:"); // NOI18N
      panCabCob.add(jLabel6);
      jLabel6.setBounds(460, 5, 120, 20);

      buttonGroup1.add(rdaAplMisCta);
      rdaAplMisCta.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      rdaAplMisCta.setSelected(true);
      rdaAplMisCta.setText("Aplican a la misma cuenta contable");
      rdaAplMisCta.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            rdaAplMisCtaActionPerformed(evt);
         }
      });
      panCabCob.add(rdaAplMisCta);
      rdaAplMisCta.setBounds(10, 48, 250, 18);

      buttonGroup1.add(rdaAplDifCta);
      rdaAplDifCta.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      rdaAplDifCta.setText("Aplican a diferentes cuenta contable");
      rdaAplDifCta.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            rdaAplDifCtaActionPerformed(evt);
         }
      });
      panCabCob.add(rdaAplDifCta);
      rdaAplDifCta.setBounds(10, 87, 250, 18);

      lblNumDoc1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblNumDoc1.setText("Número alterno 2:"); // NOI18N
      panCabCob.add(lblNumDoc1);
      lblNumDoc1.setBounds(460, 45, 120, 20);
      panCabCob.add(txtAlt2);
      txtAlt2.setBounds(580, 45, 90, 20);

      butCarArcRet.setText("Cargar arch. Retenciones");
      butCarArcRet.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butCarArcRetActionPerformed(evt);
         }
      });
      panCabCob.add(butCarArcRet);
      butCarArcRet.setBounds(280, 30, 160, 23);

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

      lblObs3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblObs3.setText("Observación 1:"); // NOI18N
      jPanel4.add(lblObs3, java.awt.BorderLayout.WEST);

      txaObs1.setLineWrap(true);
      spnObs1.setViewportView(txaObs1);

      jPanel4.add(spnObs1, java.awt.BorderLayout.CENTER);

      jPanel5.add(jPanel4);

      jPanel3.setPreferredSize(new java.awt.Dimension(250, 25));
      jPanel3.setLayout(new java.awt.BorderLayout());

      lblObs4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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

    private void txtDesCorTipDocConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocConActionPerformed
        // TODO add your handling code here:
        txtDesCorTipDocCon.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocConActionPerformed

    private void txtDesCorTipDocConFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocConFocusGained
        // TODO add your handling code here:
        strDesCorTipDocCon=txtDesCorTipDocCon.getText();
        txtDesCorTipDocCon.selectAll();
}//GEN-LAST:event_txtDesCorTipDocConFocusGained

    private void txtDesCorTipDocConFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocConFocusLost
        // TODO add your handling code here:
        if (!txtDesCorTipDocCon.getText().equalsIgnoreCase(strDesCorTipDocCon)) {
            if(txtDesCorTipDocCon.getText().equals("")) {
                txtCodTipDocCon.setText("");
                txtDesCorTipDocCon.setText("");
                txtDesLarTipDocCon.setText("");
                objAsiDia.inicializar();

            }else
                BuscarTipDocCon("a.tx_descor",txtDesCorTipDocCon.getText(),1);
        }else
            txtDesCorTipDocCon.setText(strDesCorTipDocCon);
}//GEN-LAST:event_txtDesCorTipDocConFocusLost

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
        
        if (objTooBar.getEstado() != 'c')
        {  //c = Modo Consultar.
           cargarNumeroDoc();
        }
}//GEN-LAST:event_txtDesCodTitpDocFocusLost

    private void txtDesLarTipDocConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocConActionPerformed
        // TODO add your handling code here:
        txtDesLarTipDocCon.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocConActionPerformed

    private void txtDesLarTipDocConFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocConFocusGained
        // TODO add your handling code here:
        strDesLarTipDocCon=txtDesLarTipDocCon.getText();
        txtDesLarTipDocCon.selectAll();
}//GEN-LAST:event_txtDesLarTipDocConFocusGained

    private void txtDesLarTipDocConFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocConFocusLost
        // TODO add your handling code here:
        if (!txtDesLarTipDocCon.getText().equalsIgnoreCase(strDesLarTipDocCon)) {
            if(txtDesLarTipDocCon.getText().equals("")) {
                txtCodTipDocCon.setText("");
                txtDesCorTipDocCon.setText("");
                txtDesLarTipDocCon.setText("");
                objAsiDia.inicializar();

            }else
                BuscarTipDocCon("a.tx_deslar",txtDesLarTipDocCon.getText(),2);
        }else
            txtDesLarTipDocCon.setText(strDesLarTipDocCon);
}//GEN-LAST:event_txtDesLarTipDocConFocusLost

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
        if (objVenConTipdoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
            strCodTipDoc=objVenConTipdoc.getValueAt(1);
        }
        
        if (objTooBar.getEstado() != 'c')
        {  //c = Modo Consultar.
           cargarNumeroDoc();
        }
        
}//GEN-LAST:event_butTipDocActionPerformed

    private void butTipDocConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocConActionPerformed
        // TODO add your handling code here:
        objVenConTipdocCon.setTitle("Listado de Tippos de docuemntos");
        objVenConTipdocCon.setCampoBusqueda(1);
        objVenConTipdocCon.show();
        if (objVenConTipdocCon.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
            txtCodTipDocCon.setText(objVenConTipdocCon.getValueAt(1));
            txtDesCorTipDocCon.setText(objVenConTipdocCon.getValueAt(2));
            txtDesLarTipDocCon.setText(objVenConTipdocCon.getValueAt(3));

            strCodCtaDeb=objVenConTipdocCon.getValueAt(4);
            strTxtCodCtaDeb=objVenConTipdocCon.getValueAt(5);
            strNomCtaDeb=objVenConTipdocCon.getValueAt(6);
            strCodCtaHab=objVenConTipdocCon.getValueAt(7);
            strTxtCodCtaHab=objVenConTipdocCon.getValueAt(8);
            strNomCtaHab=objVenConTipdocCon.getValueAt(9);

            strCodTipDocCon=objVenConTipdoc.getValueAt(1);

            double dblMonChq=objUti.redondear(valDoc.getText(), 2);
            generaAsiento(dblMonChq);

        }
}//GEN-LAST:event_butTipDocConActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void rdaAplDifCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdaAplDifCtaActionPerformed
        // TODO add your handling code here:


      if(rdaAplDifCta.isSelected()){

         MostrarCol(INT_TBL_DCOTIPDOC,50);
         MostrarCol(INT_TBL_BUTTIPDOC,20);
         MostrarCol(INT_TBL_DLATIPDOC,150);

         java.awt.Color colBack;
         colBack = txtDesCorTipDocCon.getBackground();
         txtDesCorTipDocCon.setEditable(false);
         txtDesCorTipDocCon.setBackground(colBack);
         txtDesLarTipDocCon.setEditable(false);
         txtDesLarTipDocCon.setBackground(colBack);
         butTipDocCon.setEnabled(false);

         txtCodTipDocCon.setText("");
         txtDesCorTipDocCon.setText("");
         txtDesLarTipDocCon.setText("");

         generaAsiento();
      }


    }//GEN-LAST:event_rdaAplDifCtaActionPerformed

    private void rdaAplMisCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdaAplMisCtaActionPerformed
        // TODO add your handling code here:

      if(rdaAplMisCta.isSelected()){
          ocultaCol(INT_TBL_DCOTIPDOC);
          ocultaCol(INT_TBL_BUTTIPDOC);
          ocultaCol(INT_TBL_DLATIPDOC);

          txtDesCorTipDocCon.setEditable(true);
          txtDesLarTipDocCon.setEditable(true);
          butTipDocCon.setEnabled(true);

          objAsiDia.inicializar();
      }

    }//GEN-LAST:event_rdaAplMisCtaActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        exitForm();
        
    }//GEN-LAST:event_formInternalFrameClosing

   private void butCarArcRetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCarArcRetActionPerformed
      // TODO add your handling code here:
      String strAux;
      
      try
      {        
         abrirCon();
         CONN_GLO.setAutoCommit(false);
         
         if (RetSri() == true)
         {  CONN_GLO.commit();
            MensajeInf("La carga del archivo de Retenciones fue exitosa.");
         }
         else
         {  CONN_GLO.rollback();
            if (blnBtnCanCarArcRet == false)
            {  //Var. booleana que indica si se presiono el Boton <Cancelar> en la ventana de Carga de Archivo de Retenciones.
               strAux =  "La carga del archivo de Retenciones no fue exitosa." + "\n\n";
               strAux += "Notifique esta novedad al Administrador del sistema.";
               MensajeInf(strAux);
            }
         }
         
         CerrarCon();
      }
      
      catch(Exception e)
      {  
         objUti.mostrarMsgErr_F1(this, e);
      }
   }//GEN-LAST:event_butCarArcRetActionPerformed


         /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de salidad.
     */
    private void exitForm(){

        String strTit, strMsg;
        //JOptionPane oppMsg=new JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
            Runtime.getRuntime().gc();
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
      strCodTipDoc=objVenConTipdoc.getValueAt(1);

  }else{
        objVenConTipdoc.setCampoBusqueda(tipo);
        objVenConTipdoc.cargarDatos();
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
           txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
           txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
           txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
           strCodTipDoc=objVenConTipdoc.getValueAt(1);
         }else{
           txtCodTipDoc.setText(strCodTipDoc);
           txtDesCodTitpDoc.setText(strDesCodTipDoc);
           txtDesLarTipDoc.setText(strDesLarTipDoc);
  }}}




public void BuscarTipDocCon(String campo,String strBusqueda,int tipo){
  objVenConTipdocCon.setTitle("Listado de Tipo de Documentos");
  if(objVenConTipdocCon.buscar(campo, strBusqueda )) {
      txtCodTipDocCon.setText(objVenConTipdocCon.getValueAt(1));
      txtDesCorTipDocCon.setText(objVenConTipdocCon.getValueAt(2));
      txtDesLarTipDocCon.setText(objVenConTipdocCon.getValueAt(3));
      strCodCtaDeb=objVenConTipdocCon.getValueAt(4);
      strTxtCodCtaDeb=objVenConTipdocCon.getValueAt(5);
      strNomCtaDeb=objVenConTipdocCon.getValueAt(6);
      strCodCtaHab=objVenConTipdocCon.getValueAt(7);
      strTxtCodCtaHab=objVenConTipdocCon.getValueAt(8);
      strNomCtaHab=objVenConTipdocCon.getValueAt(9);

      strCodTipDocCon=objVenConTipdocCon.getValueAt(1);

      double dblMonChq=objUti.redondear(valDoc.getText(), 2);
      generaAsiento(dblMonChq);

  }else{
        objVenConTipdocCon.setCampoBusqueda(tipo);
        objVenConTipdocCon.cargarDatos();
        objVenConTipdocCon.show();
        if (objVenConTipdocCon.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
           txtCodTipDocCon.setText(objVenConTipdocCon.getValueAt(1));
           txtDesCorTipDocCon.setText(objVenConTipdocCon.getValueAt(2));
           txtDesLarTipDocCon.setText(objVenConTipdocCon.getValueAt(3));
           strCodCtaDeb=objVenConTipdocCon.getValueAt(4);
           strTxtCodCtaDeb=objVenConTipdocCon.getValueAt(5);
           strNomCtaDeb=objVenConTipdocCon.getValueAt(6);
           strCodCtaHab=objVenConTipdocCon.getValueAt(7);
           strTxtCodCtaHab=objVenConTipdocCon.getValueAt(8);
           strNomCtaHab=objVenConTipdocCon.getValueAt(9);

           strCodTipDocCon=objVenConTipdocCon.getValueAt(1);

           double dblMonChq=objUti.redondear(valDoc.getText(), 2);
           generaAsiento(dblMonChq);

         }else{
           txtCodTipDocCon.setText(strCodTipDocCon);
           txtDesCorTipDocCon.setText(strDesCorTipDocCon);
           txtDesLarTipDocCon.setText(strDesLarTipDocCon);
  }}}





   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton butCarArcRet;
   private javax.swing.JButton butTipDoc;
   private javax.swing.JButton butTipDocCon;
   private javax.swing.ButtonGroup buttonGroup1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel jLabel6;
   private javax.swing.JPanel jPanel3;
   private javax.swing.JPanel jPanel4;
   private javax.swing.JPanel jPanel5;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JLabel lblCodDoc;
   private javax.swing.JLabel lblCodDoc1;
   private javax.swing.JLabel lblNumDoc;
   private javax.swing.JLabel lblNumDoc1;
   private javax.swing.JLabel lblObs3;
   private javax.swing.JLabel lblObs4;
   private javax.swing.JLabel lblTit;
   private javax.swing.JPanel panAsiDia;
   private javax.swing.JPanel panCabCob;
   private javax.swing.JPanel panCabGen;
   private javax.swing.JPanel panCotGenSur;
   private javax.swing.JPanel panCotGenSurCen;
   private javax.swing.JPanel panDetRecChq;
   private javax.swing.JPanel panNor;
   private javax.swing.JRadioButton rdaAplDifCta;
   private javax.swing.JRadioButton rdaAplMisCta;
   private javax.swing.JScrollPane spnObs1;
   private javax.swing.JScrollPane spnObs2;
   private javax.swing.JTabbedPane tabGen;
   private javax.swing.JTable tblDat;
   private javax.swing.JTextArea txaObs1;
   private javax.swing.JTextArea txaObs2;
   private javax.swing.JTextField txtAlt1;
   private javax.swing.JTextField txtAlt2;
   private javax.swing.JTextField txtCodDoc;
   private javax.swing.JTextField txtDesCodTitpDoc;
   private javax.swing.JTextField txtDesCorTipDocCon;
   private javax.swing.JTextField txtDesLarTipDoc;
   private javax.swing.JTextField txtDesLarTipDocCon;
   private javax.swing.JTextField valDoc;
   // End of variables declaration//GEN-END:variables




    private void MensajeInf(String strMensaje){
        //JOptionPane obj =new JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this,strMensaje,strTit,JOptionPane.INFORMATION_MESSAGE);
    }
    
   private String getIdeEmisor1(int intCodEmp, int intCodCli)
   {
      String strRes = "", strSQL;
      java.sql.Connection connLoc;
      java.sql.Statement stmLoc;
      java.sql.ResultSet rstLoc;
      
      try
      {  connLoc = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
         
         if (connLoc != null)
         {  stmLoc = connLoc.createStatement();
            strRes = "";   
            strSQL =  "SELECT tx_ide FROM tbm_cli WHERE st_reg = 'A' and co_emp = " + intCodEmp;
            strSQL += " and co_cli = " + intCodCli;
            rstLoc = stmLoc.executeQuery(strSQL);

            if (rstLoc.next())
               strRes = rstLoc.getString("tx_ide");
            
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
         }
         
         connLoc.close();
         connLoc = null;
      } //try
      
      catch(Exception e)
      {  
         strRes = "";
      }
      
      return strRes;
   } //Funcion getIdeEmisor1()


      public class mitoolbar extends ZafToolBar{
        public mitoolbar(javax.swing.JInternalFrame jfrThis){
            super(jfrThis, objZafParSis);
        }


@Override
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

        if(!(intCodMnuDocIng == objZafParSis.getCodigoMenu()) ){
            MensajeInf("NO SE PUEDE ANULAR ESTE DOCUMENTO NO CORRESPONDE A ESTE PROGRAMA.\nNo es posible anular un documento anulado.");
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
               " ,tx_numchq=null,  nd_monchq=0, fe_recchq=null, fe_venchq=null   FROM ( " +
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

        @Override
        public void clickAceptar() {
            setEstadoBotonMakeFac();
        }



@Override
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




        @Override
        public void clickAnular() {

        }


        @Override
        public void clickConsultar() {

           

            clnTextos();

            cargarTipoDoc (2);


	}

        @Override
        public void clickEliminar() {

	}





@Override
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






@Override
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




@Override
public void clickInsertar() {
 try{
    clnTextos();

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
    objTblMod.setDataModelChanged(false);

    cargarTipoDoc(1);

      if(rstCab!=null) {
          rstCab.close();
          rstCab=null;
      }

   }catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e); }
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
               txtAlt1.setText(((rstLoc.getString("numDoc")==null)?"":rstLoc.getString("numDoc")));


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



@Override
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

@Override
public boolean eliminar() {
  boolean blnRes=false;

  return blnRes;
}

/**
 * validad campos requeridos antes de insertar o modificar
 * @return  true si esta todo bien   false   falta algun dato
 */
 private boolean validaCampos(){

  int intExiDatTbl=0, intCodTipRet;
  String strMens="RETENCIONES";
  String strCodCli="", strAux, strCodTipDoc, strCodTipDocCon, strNumSerLoc, strAux1, strAux2, strYearMes_Det, strDiaLimIngRet, strFecSrv;
  String strFecDet, strFecLimIngRet, strYearMesFecSrv;
  int intValCamp = 1, intCon, intNumDec, intAux;
  boolean blnRes, blnFound, blnIsCos_Ecu_Det;
  BigDecimal bdePorRet, bdeAux, bdeTotAbo;
  Date datFecAuxLoc, datFecLimIngRet, datFecSrv, datFecDet;
  StringTokenizer strTokCad;

  if((objZafParSis.getCodigoMenu()==256)){
       strMens="CHEQUE";
   }


  if(txtCodTipDoc.getText().trim().equals("") ){
    tabGen.setSelectedIndex(0);
    MensajeInf("El campo << Tipo de documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtCodTipDoc.requestFocus();
    return false;
   }

 if (rdaAplMisCta.isSelected())
 {  if (txtCodTipDocCon.getText().trim().equals("") )
    {  tabGen.setSelectedIndex(0);
       MensajeInf("El campo << Tipo de documento contable >> es obligatorio.\nEscoja y vuelva a intentarlo.");
       txtCodTipDocCon.requestFocus();
       return false;
    }
 
    strCodTipDoc = txtCodTipDoc.getText().trim();
    strCodTipDocCon = txtCodTipDocCon.getText().trim();
    
    if (!strCodTipDoc.equals(strCodTipDocCon))
    {  tabGen.setSelectedIndex(0);
       strAux = "<HTML>Los campos <FONT COLOR=\"blue\">Tipo de documento</FONT> y <FONT COLOR=\"blue\">Tipo de documento contable</FONT> deben ser ";
       strAux += "iguales.<BR>Verifique y vuelva a intentarlo.</HTML>";
       MensajeInf(strAux);
       txtCodTipDocCon.requestFocus();
       return false;
    }
 }

   if(txtAlt1.getText().equals("") ){
    tabGen.setSelectedIndex(0);
    MensajeInf("El campo << Numero alterno 1 >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtAlt1.requestFocus();
    return false;
    }

    BigDecimal bdeValDia=new BigDecimal(BigInteger.ZERO);
    BigDecimal bdeValDoc=new BigDecimal(BigInteger.ZERO);

    if(objAsiDia.isDiarioCuadrado()){
        bdeValDia=objUti.redondearBigDecimal(new BigDecimal(objAsiDia.getMontoDebe()), objZafParSis.getDecimalesMostrar());
        bdeValDoc=new BigDecimal(valDoc.getText()).abs();
    } else {
        MensajeInf("Asiento descuadrado.");
        tabGen.setSelectedIndex(1);
        return false;          
    }

    if(bdeValDia.compareTo(bdeValDoc)!=0){
        MensajeInf("<HTML>El asiento de diario tiene un valor diferente al valor del documento<BR>Verifique el valor del asiento de diario y vuelva a intentarlo.</HTML>");
        valDoc.selectAll();
        valDoc.requestFocus();
        return false;
    }
   
 if((objZafParSis.getCodigoMenu()==1648)){  // RETENCIONES
  for(int i=0; i<tblDat.getRowCount(); i++){
   if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){
    if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("false")) ){
        tabGen.setSelectedIndex(0);
        MensajeInf("HAY ALGUN DATO QUE NO ESTA SELECCIONADO..");
        txtAlt1.requestFocus();
        break;
  }}}
 }

        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        objTblMod.removeEmptyRows();
        blnIsCos_Ecu_Det = (objZafParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO") != -1
                || objZafParSis.getNombreEmpresa().toUpperCase().indexOf("ECUATOSA") != -1
                || objZafParSis.getNombreEmpresa().toUpperCase().indexOf("DETOPACIO") != -1)? true: false;
        
        String strLngNumAutSri="";
        String strLngFecCad="";
        bdeTotAbo = new BigDecimal("0");
        strYearMes_Det = "";
        datFecAuxLoc = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
        strFecSrv = objUti.formatearFecha(datFecAuxLoc, objZafParSis.getFormatoFechaHoraBaseDatos());
        strYearMesFecSrv = strFecSrv.substring(0, 7); //Si strFecSrv es 2016-12-01, va a extraer "2016-12"
        strFecSrv = strFecSrv.substring(0, 10); //De la fecha/hora del servidor, se va a extraer solo la fecha
        strFecLimIngRet = "";
        strDiaLimIngRet = "";

        if (objZafParSis.getCodigoEmpresa() == 1)
        {  //Para Tuval, la fecha limite para ingreso de retenciones es el dia 14 de cada mes
           strDiaLimIngRet = "14";
           strFecLimIngRet = strYearMesFecSrv + "-" + strDiaLimIngRet;
        }
        else if (objZafParSis.getCodigoEmpresa() == 2 || objZafParSis.getCodigoEmpresa() == 4)
        {  //Para Castek y Dimulti, la fecha limite para ingreso de retenciones es el dia 8 de cada mes
           strDiaLimIngRet = "08";
           strFecLimIngRet = strYearMesFecSrv + "-" + strDiaLimIngRet;
        }
        else
           strFecLimIngRet = strYearMesFecSrv + "-01";

        strFecSrv = strFecSrv.replace("-", "/");
        strFecLimIngRet = strFecLimIngRet.replace("-", "/");

     for(int i=0; i<tblDat.getRowCount(); i++){
      if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){
       if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){

          strCodCli =tblDat.getValueAt(i, INT_TBL_CODCLI).toString();

          intExiDatTbl=1;

         if(rdaAplDifCta.isSelected()){
            if( ((tblDat.getValueAt(i, INT_TBL_CODTIPDOC)==null?"":(tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString())).equals("")) ){
                MensajeInf("SELECCIONE EL TIPO CUENTA CONTABLE. ");
                tblDat.repaint();
                tblDat.requestFocus();
                tblDat.editCellAt(i, INT_TBL_DCTIPDOC);
                return false;
           }
         }

         if((objZafParSis.getCodigoMenu()==1648)){  // RETENCIONES
         // intValCamp = ValidarCodigoCliente(strCodCli);
          if(intValCamp==1){
          if (rdaAplMisCta.isSelected())
          {  strCodTipDoc = txtCodTipDoc.getText().trim();
             bdePorRet = new BigDecimal(tblDat.getValueAt(i, INT_TBL_PORRET).toString());
             bdePorRet = objUti.redondearBigDecimal(bdePorRet, objZafParSis.getDecimalesMostrar());
             
             strAux1 = "";
             strAux2 = "";
             strAux = tblDat.getValueAt(i, INT_TBL_ABONO).toString();
             strAux = strAux.indexOf(".") < 0 ? strAux : strAux.replaceAll("0*$", "").replaceAll("\\.$", ""); //Para suprimir ceros a la derecha del punto decimal.
             strTokCad = new StringTokenizer(strAux, ".");
             intCon = 0; //Contador
             intNumDec = 0;
             
             while (strTokCad.hasMoreElements())
             {  intCon++;
                strAux = strTokCad.nextToken().toString();
                
                if (intCon == 1)
                {  //Cuando intCon = 1, quiere decir que en la variable strAux estaran todos los digitos que estan a la izquierda del punto decimal.
                   strAux1 = strAux; //strAux1 tendra la parte entera del numero.
                }
                else if (intCon == 2)
                {  //Cuando intCon = 2, quiere decir que en la variable strAux estaran todos los digitos que estan a la derecha del punto decimal.
                   strAux2 = strAux; //strAux2 tendra la parte decimal del numero.
                   intNumDec = strAux.length();
                }
             }
             
             if (intNumDec > 2)
             {  //En la col. 'Abono' solo se debe permitir un maximo de 2 decimales.
                strAux1 = strAux1 + "." + strAux2; //Se adjunta la parte entera (strAux1) y la parte decimal (strAux2) del numero.
                tabGen.setSelectedIndex(0);
                strAux =  "<HTML>En el detalle hay un valor de abono (" + strAux1 + ") que tiene mas de 2 decimales.<BR>";
                strAux += "<HTML>El valor de abono puede tener como máximo 2 decimales.<BR>Esto ocurrió para la siguiente línea de detalle:<BR><BR>";
                strAux += "Nom. cliente: " + tblDat.getValueAt(i, INT_TBL_NOMCLI).toString() + "<BR>";
                strAux += "Num. documento: " + tblDat.getValueAt(i, INT_TBL_NUMDOC).toString() + "<BR><BR>";
                strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                mostrarMsgInf(strAux);
                return false;
             }
             
             bdeAux = new BigDecimal(tblDat.getValueAt(i, INT_TBL_ABONO).toString());
             bdeAux = objUti.redondearBigDecimal(bdeAux, objZafParSis.getDecimalesMostrar());
             bdeTotAbo = bdeTotAbo.add(bdeAux);
             
             if (strCodTipDoc.equals("26") && !bdePorRet.equals(new BigDecimal("30.00")))
             {  //CodTipDoc 26 = RFIV30 = Retencion al IVA 30%
                tabGen.setSelectedIndex(0);
                strAux =  "<HTML>En el detalle hay un porcentaje de retención que no corresponde al Tipo de Documento seleccionado.<BR>Esto ocurrió para la siguiente línea de detalle:<BR><BR>";
                strAux += "Nom. cliente: " + tblDat.getValueAt(i, INT_TBL_NOMCLI).toString() + "<BR>";
                strAux += "Num. documento: " + tblDat.getValueAt(i, INT_TBL_NUMDOC).toString() + "<BR><BR>";
                strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                mostrarMsgInf(strAux);
                return false;
             }
             
             if (strCodTipDoc.equals("292") && !bdePorRet.equals(new BigDecimal("50.00")))
             {  //CodTipDoc 292 = RFIV50 = Retencion al IVA 50%
                tabGen.setSelectedIndex(0);
                strAux =  "<HTML>En el detalle hay un porcentaje de retención que no corresponde al Tipo de Documento seleccionado.<BR>Esto ocurrió para la siguiente línea de detalle:<BR><BR>";
                strAux += "Nom. cliente: " + tblDat.getValueAt(i, INT_TBL_NOMCLI).toString() + "<BR>";
                strAux += "Num. documento: " + tblDat.getValueAt(i, INT_TBL_NUMDOC).toString() + "<BR><BR>";
                strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                mostrarMsgInf(strAux);
                return false;
             }
             
             if (strCodTipDoc.equals("27") && !bdePorRet.equals(new BigDecimal("70.00")))
             {  //CodTipDoc 27 = RFIV70 = Retencion al IVA 70%
                tabGen.setSelectedIndex(0);
                strAux =  "<HTML>En el detalle hay un porcentaje de retención que no corresponde al Tipo de Documento seleccionado.<BR>Esto ocurrió para la siguiente línea de detalle:<BR><BR>";
                strAux += "Nom. cliente: " + tblDat.getValueAt(i, INT_TBL_NOMCLI).toString() + "<BR>";
                strAux += "Num. documento: " + tblDat.getValueAt(i, INT_TBL_NUMDOC).toString() + "<BR><BR>";
                strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                mostrarMsgInf(strAux);
                return false;
             }
             
             if (strCodTipDoc.equals("84") && !bdePorRet.equals(new BigDecimal("100.00")))
             {  //CodTipDoc 84 = RFIV100 = Retencion al IVA 100%
                tabGen.setSelectedIndex(0);
                strAux =  "<HTML>En el detalle hay un porcentaje de retención que no corresponde al Tipo de Documento seleccionado.<BR>Esto ocurrió para la siguiente línea de detalle:<BR><BR>";
                strAux += "Nom. cliente: " + tblDat.getValueAt(i, INT_TBL_NOMCLI).toString() + "<BR>";
                strAux += "Num. documento: " + tblDat.getValueAt(i, INT_TBL_NUMDOC).toString() + "<BR><BR>";
                strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                mostrarMsgInf(strAux);
                return false;
             }
             
             if (strCodTipDoc.equals("247") && !bdePorRet.equals(new BigDecimal("10.00")))
             {  //CodTipDoc 247 = RFIV10 = Retencion al IVA 10%
                tabGen.setSelectedIndex(0);
                strAux =  "<HTML>En el detalle hay un porcentaje de retención que no corresponde al Tipo de Documento seleccionado.<BR>Esto ocurrió para la siguiente línea de detalle:<BR><BR>";
                strAux += "Nom. cliente: " + tblDat.getValueAt(i, INT_TBL_NOMCLI).toString() + "<BR>";
                strAux += "Num. documento: " + tblDat.getValueAt(i, INT_TBL_NUMDOC).toString() + "<BR><BR>";
                strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                mostrarMsgInf(strAux);
                return false;
             }
             
             if (strCodTipDoc.equals("248") && !bdePorRet.equals(new BigDecimal("20.00")))
             {  //CodTipDoc 248 = RFIV20 = Retencion al IVA 20%
                tabGen.setSelectedIndex(0);
                strAux =  "<HTML>En el detalle hay un porcentaje de retención que no corresponde al Tipo de Documento seleccionado.<BR>Esto ocurrió para la siguiente línea de detalle:<BR><BR>";
                strAux += "Nom. cliente: " + tblDat.getValueAt(i, INT_TBL_NOMCLI).toString() + "<BR>";
                strAux += "Num. documento: " + tblDat.getValueAt(i, INT_TBL_NUMDOC).toString() + "<BR><BR>";
                strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                mostrarMsgInf(strAux);
                return false;
             }
             
             if (strCodTipDoc.equals("25") && !bdePorRet.equals(new BigDecimal("1.00")))
             {  //CodTipDoc 25 = RETE = Retenciones en la Fuente 1%
                tabGen.setSelectedIndex(0);
                strAux =  "<HTML>En el detalle hay un porcentaje de retención que no corresponde al Tipo de Documento seleccionado.<BR>Esto ocurrió para la siguiente línea de detalle:<BR><BR>";
                strAux += "Nom. cliente: " + tblDat.getValueAt(i, INT_TBL_NOMCLI).toString() + "<BR>";
                strAux += "Num. documento: " + tblDat.getValueAt(i, INT_TBL_NUMDOC).toString() + "<BR><BR>";
                strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                mostrarMsgInf(strAux);
                return false;
             }
             
             if (strCodTipDoc.equals("73") && !bdePorRet.equals(new BigDecimal("2.00")))
             {  //CodTipDoc 73 = RETE2 = Retenciones en la Fuente 2%
                tabGen.setSelectedIndex(0);
                strAux =  "<HTML>En el detalle hay un porcentaje de retención que no corresponde al Tipo de Documento seleccionado.<BR>Esto ocurrió para la siguiente línea de detalle:<BR><BR>";
                strAux += "Nom. cliente: " + tblDat.getValueAt(i, INT_TBL_NOMCLI).toString() + "<BR>";
                strAux += "Num. documento: " + tblDat.getValueAt(i, INT_TBL_NUMDOC).toString() + "<BR><BR>";
                strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                mostrarMsgInf(strAux);
                return false;
             }
          } //if (rdaAplMisCta.isSelected())
          
          strAux = tblDat.getValueAt(i, INT_TBL_CODSRIDOC).toString();
          
          if (strAux.equals(""))
          {  tabGen.setSelectedIndex(0);
             strAux =  "<HTML>En el detalle no hay valor de código del SRI.<BR>Esto ocurrió para la siguiente línea de detalle:<BR><BR>";
             strAux += "Nom. cliente: " + tblDat.getValueAt(i, INT_TBL_NOMCLI).toString() + "<BR>";
             strAux += "Num. documento: " + tblDat.getValueAt(i, INT_TBL_NUMDOC).toString() + "<BR><BR>";
             strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
             mostrarMsgInf(strAux);
             return false;
          }
             
          if( ((tblDat.getValueAt(i, INT_TBL_NUMCHQ)==null?"":(tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString().trim().equals("")?"":tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString())).trim().equals("")) ){
                MensajeInf("AGREGE EL NUMERO DE RETENCIÓN.. ");
                tblDat.repaint();
                tblDat.requestFocus();
                tblDat.editCellAt(i, INT_TBL_NUMCHQ);
                return false;
          }
          if( ((tblDat.getValueAt(i, INT_TBL_NUMSECDOC)==null?"":(tblDat.getValueAt(i, INT_TBL_NUMSECDOC).toString().trim().equals("")?"":tblDat.getValueAt(i, INT_TBL_NUMSECDOC).toString())).trim().equals("")) ){
                MensajeInf("AGREGE EL NUMERO DE SERIE.. ");
                tblDat.repaint();
                tblDat.requestFocus();
                tblDat.editCellAt(i, INT_TBL_NUMSECDOC);
                return false;
          }
          if( ((tblDat.getValueAt(i, INT_TBL_NUMAUTSRI)==null?"":(tblDat.getValueAt(i, INT_TBL_NUMAUTSRI).toString().trim().equals("")?"":tblDat.getValueAt(i, INT_TBL_NUMAUTSRI).toString())).trim().equals("")) ){
                MensajeInf("AGREGE EL NUMERO DE AUTORIZACIÓN DEL SRI.. ");
                tblDat.repaint();
                tblDat.requestFocus();
                tblDat.editCellAt(i, INT_TBL_NUMAUTSRI);
                return false;
          }
          
          /*
           *
           *     José Mario 22/Agosto/2014
           * 
           */
          
//          if( ((tblDat.getValueAt(i, INT_TBL_FECCATDOC)==null?"":(tblDat.getValueAt(i, INT_TBL_FECCATDOC).toString().trim().equals("")?"":tblDat.getValueAt(i, INT_TBL_FECCATDOC).toString())).trim().equals("")) ){
//                MensajeInf("AGREGE LA FECHA DE CADUCIDAD DEL DOCUMENTO.. ");
//                tblDat.repaint();
//                tblDat.requestFocus();
//                tblDat.editCellAt(i, INT_TBL_FECCATDOC);
//                return false;
//          }

        if ((objZafParSis.getCodigoMenu() == 1648)){  // RETENCIONES
            //se valida si debe llevar FECHA CADUCIDAD
            strLngNumAutSri=objTblMod.getValueAt(i, INT_TBL_NUMAUTSRI)==null?"":objTblMod.getValueAt(i, INT_TBL_NUMAUTSRI).toString();
            strLngFecCad=objTblMod.getValueAt(i, INT_TBL_FECCATDOC)==null?"":objTblMod.getValueAt(i, INT_TBL_FECCATDOC).toString();
            if( (strLngNumAutSri.length()==37 || strLngNumAutSri.length()==49) && (strLngFecCad.length()!=0) ){
                mostrarMsgInf("<HTML>El campo fecha de caducidad no debe ser ingresado.<BR>Elimine el dato ingresado y vuelva a intentarlo.</HTML>");
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.changeSelection(i, INT_TBL_FECCATDOC, true, true);
                tblDat.requestFocus();
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                return false;
            }
            else if(   (strLngNumAutSri.length()==10)  &&  (strLngFecCad.length()==0)  ){
                mostrarMsgInf("<HTML>El campo fecha de caducidad es obligatorio.<BR>Ingrese el dato fecha de caducidad y vuelva a intentarlo.</HTML>");
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.changeSelection(i, INT_TBL_FECCATDOC, true, true);
                tblDat.requestFocus();
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                return false;
            }
            else if (strLngNumAutSri.length() == 10){
                blnRes = isFechaValida(strLngFecCad);
                if (blnRes == false)
                {  strAux =  "<HTML>El campo fecha de caducidad no es válido para la siguiente línea de detalle:<BR><BR>";
                   strAux += "Nom. cliente: " + tblDat.getValueAt(i, INT_TBL_NOMCLI).toString() + "<BR>";
                   strAux += "Num. documento: " + tblDat.getValueAt(i, INT_TBL_NUMDOC).toString() + "<BR>";
                   strAux += "Num. retención: " + tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString() + "<BR><BR>";
                   strAux += "Verifique que la fecha esté en formato dd/mm/aaaa y vuelva a intentarlo.</HTML>";
                   mostrarMsgInf(strAux);
                   tblDat.setRowSelectionInterval(0, 0);
                   tblDat.changeSelection(i, INT_TBL_FECCATDOC, true, true);
                   tblDat.requestFocus();
                   objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                   return false;
                }
            }
            else if( (strLngNumAutSri.length()!=10) && (strLngNumAutSri.length()!=37) && (strLngNumAutSri.length()!=49) ){
                mostrarMsgInf("<HTML>El campo número de autorización debe tener 10, 37 ó 49 dígitos.<BR>Verifique y vuelva a intentarlo.</HTML>");
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.changeSelection(i, INT_TBL_NUMAUTSRI, true, true);
                tblDat.requestFocus();
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                return false;
            }
            
            strNumSerLoc = objTblMod.getValueAt(i, INT_TBL_NUMSECDOC) == null? "" :objTblMod.getValueAt(i, INT_TBL_NUMSECDOC).toString();
            
            if (strNumSerLoc.length() != 6)
            {  mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Num.Ser.Doc.</FONT> debe tener 6 digitos sin guion.<BR>Verifique y vuelva a intentarlo.</HTML>");
               tblDat.setRowSelectionInterval(0, 0);
               tblDat.changeSelection(i, INT_TBL_NUMSECDOC, true, true);
               tblDat.requestFocus();
               objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
               return false;
            }
            
            strAux1 = strNumSerLoc.substring(0, 3); // Si la serie es 001002, va a extraer "001"
            strAux2 = strNumSerLoc.substring(3, 6); // Si la serie es 001002, va a extraer "002"
            blnFound = true;

            if (strNumSerLoc.lastIndexOf("-") == -1)
               blnFound = false;

            if (objUti.isNumero(strAux1) == false || objUti.isNumero(strAux2) == false || blnFound == true)
            {  mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Num.Ser.Doc.</FONT> debe ser numérico sin guion.<BR>Verifique y vuelva a intentarlo.</HTML>");
               tblDat.setRowSelectionInterval(0, 0);
               tblDat.changeSelection(i, INT_TBL_NUMSECDOC, true, true);
               tblDat.requestFocus();
               objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
               return false;
            }
            
            //De la columna "Fec.Doc." se va a extraer aaaa-mm
            strAux1 = objTblMod.getValueAt(i, INT_TBL_FECDOC) == null? "" :objTblMod.getValueAt(i, INT_TBL_FECDOC).toString();
            strAux1 = strAux1.substring(0, 7); // Si strAux1 es 2016-12-01, va a extraer "2016-12"
            
            if (i > 0 && !strYearMes_Det.equals(strAux1))
            {  //En el detalle en la columna "Fec.Doc." se encontró documentos con "aaaa-mm" que no son iguales, y por tanto no se debe permitir
               //grabar.
               tabGen.setSelectedIndex(0);
               strAux =  "<HTML>En el detalle en la columna <FONT COLOR=\"blue\">Fec.Doc.</FONT> se encontró documentos con 'aaaa-mm' que no son iguales.<BR><BR>";
               strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
               mostrarMsgInf(strAux);
               return false;
            }
            
            if (blnIsCos_Ecu_Det == false)
            {  strYearMes_Det = strAux1;
               //strFecDet va a estar en formato "yyyy/MM/dd"
               strFecDet = strYearMes_Det.substring(0, 4) + "/" + strYearMes_Det.substring(5, 7) + "/" + strFecSrv.substring(8, 10);

               datFecSrv = objUti.parseDate(strFecSrv, "yyyy/MM/dd");
               datFecLimIngRet = objUti.parseDate(strFecLimIngRet, "yyyy/MM/dd");
               datFecDet = objUti.parseDate(strFecDet, "yyyy/MM/dd");

               if (datFecSrv.compareTo(datFecLimIngRet) > 0 && datFecDet.compareTo(datFecSrv) < 0)
               {  //No se debe permitir el ingreso de la retencion
                  tabGen.setSelectedIndex(0);
                  strAux =  "<HTML>En el detalle hay una retención que no puede ser ingresada por<BR>";
                  strAux += "sobrepasar la fecha límite para el ingreso de retenciones.<BR>";
                  strAux += "Fecha límite: <FONT COLOR=\"blue\">" + strFecLimIngRet + "</FONT><BR>";
                  strAux += "Esto ocurrió para la siguiente línea de detalle:<BR><BR>";
                  strAux += "Nom. cliente: " + tblDat.getValueAt(i, INT_TBL_NOMCLI).toString() + "<BR>";
                  strAux += "Num. documento: " + tblDat.getValueAt(i, INT_TBL_NUMDOC).toString() + "<BR>";
                  strAux += "Fecha documento: " + tblDat.getValueAt(i, INT_TBL_FECDOC).toString() + "<BR><BR>";
                  strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                  mostrarMsgInf(strAux);
                  return false;
               }
            }
        } //if ((objZafParSis.getCodigoMenu() == 1648))
          
            /*
           *
           *     José Mario 22/Agosto/2014
           * 
           */
          
          if( ((tblDat.getValueAt(i, INT_TBL_CODSRIDOC)==null?"":(tblDat.getValueAt(i, INT_TBL_CODSRIDOC).toString().trim().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODSRIDOC).toString())).trim().equals("")) ){
                MensajeInf("AGREGE EL CÓDIGO DEL SRI.. ");
                tblDat.repaint();
                tblDat.requestFocus();
                tblDat.editCellAt(i, INT_TBL_CODSRIDOC);
                return false;
          }
        }}

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
     }}
     
     if (objZafParSis.getCodigoMenu() == 1648 && bdeValDoc.compareTo(bdeTotAbo) != 0)
     {  //Cod. menu 1648 = Retenciones recibidas
        MensajeInf("<HTML>El valor del documento tiene un valor diferente al total abonado<BR>Verifique estos valores y vuelva a intentarlo.</HTML>");
        valDoc.selectAll();
        valDoc.requestFocus();
        return false;
     }

    if(intExiDatTbl==0){
        MensajeInf("NO HAY DATOS EN DETALLE INGRESE DATOS.... ");
        return false;
    }

   return true;
 }
 
   /**
   * Esta función se encarga de validar si una fecha es valida o no.
   * Retorna true si la fecha es valida.
   * Retorna false si la fecha no es valida.
   */
   private boolean isFechaValida(String strFecha)
   {  
      String strFormatoFecha = "dd/MM/yyyy";
      
      try
      {
         DateFormat df = new SimpleDateFormat(strFormatoFecha);
         df.setLenient(false);
         df.parse(strFecha);
         return true;
      } 
      
      catch (Exception e)
      {
         return false;
      }
   }


/**
 *  Función que se encargar de validar si el cliente es una empresa de las compañias como tuval, dimulti en estos
 *  casos no validad
 *
 * @param strCodCli Código del cliente
 * @return 1 : Si Validad
 * @return 0 : No Validad
 */
public int ValidarCodigoCliente(String strCodCli){
 int intTipCli=1;
 try{
       ///******************************* TUVAL ******************************
        if(objZafParSis.getCodigoEmpresa()==1){
        if( (strCodCli.equals("603")) || (strCodCli.equals("2600")) || (strCodCli.equals("1039"))  ) {
              intTipCli=0;   //no Validad
        }else{
               intTipCli=1;
        }}
        ///******************************* CASTEK ******************************
        if(objZafParSis.getCodigoEmpresa()==2){
        if( (strCodCli.equals("2854")) || (strCodCli.equals("2105"))  || (strCodCli.equals("789")) ) {
             intTipCli=0;   //no Validad
         }else{
               intTipCli=1;
        }}
        ///******************************* NOSITOL  ******************************
         if(objZafParSis.getCodigoEmpresa()==3){
         if( (strCodCli.equals("2858")) || (strCodCli.equals("453")) || (strCodCli.equals("832"))  ) {
               intTipCli=0;   //no Validad
         }else{
               intTipCli=1;
        }}
        ///*******************************  DIMULTI  ******************************
          if(objZafParSis.getCodigoEmpresa()==4){
          if( (strCodCli.equals("3117")) || (strCodCli.equals("498")) || (strCodCli.equals("2294"))  ) {
                intTipCli=0;   //no Validad
         }else{
              intTipCli=1;
        }}
 }catch(Exception e){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
 return intTipCli;
}


private boolean _getValidarFecFacFecDoc(){
 boolean blnRes=false;
 String strFecDoc="";
 String strFecFacTbl="";
 int intAnoDoc=0;
 int intMesDoc=0;
 int intAnoFacDoc=0;
 int intMesFacDoc=0;
 try{
    strFecDoc = txtFecDoc.getFecha("/", "y/m/d");
    intAnoDoc = objUti.getAnio(strFecDoc, "yyyy/MM/dd");
    intMesDoc = objUti.getMes(strFecDoc, "yyyy/MM/dd")+1;
    if(objZafParSis.getCodigoMenu()==1648){
    for(int i=0; i<tblDat.getRowCount(); i++){
      if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){
       if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){

         strFecFacTbl = tblDat.getValueAt(i, INT_TBL_FECDOC).toString();
         intAnoFacDoc = objUti.getAnio(strFecFacTbl, "yyyy-MM-dd");
         intMesFacDoc = objUti.getMes(strFecFacTbl, "yyyy-MM-dd")+1;

         if(intAnoDoc==intAnoFacDoc){
          if(intMesDoc==intMesFacDoc){
               blnRes=true;
          }else{ blnRes=false; break; }
         }else{ blnRes=false; break; }
    
    }}}
   }else blnRes=true;

 }catch(Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
 return blnRes;
}


private boolean _getValidadIdeSisIdeIng(){
 boolean blnRes=false;
 boolean blnEstBuf=false;
 StringBuffer strBufBus = new StringBuffer();
  try{
  
     for(int i=0; i<tblDat.getRowCount(); i++){
      if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){
        if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){

          if(blnEstBuf) strBufBus.append(" UNION ALL ");
          strBufBus.append(" SELECT "+tblDat.getValueAt(i, INT_TBL_CODEMP).toString()+" AS co_emp "
          + ", "+tblDat.getValueAt(i, INT_TBL_CODLOC).toString()+" AS co_loc "
          + ", "+tblDat.getValueAt(i, INT_TBL_CODTID).toString()+" AS co_tipdoc "
          + ", "+tblDat.getValueAt(i, INT_TBL_CODDOC).toString()+" AS co_doc " );
          blnEstBuf=true;
    }}
   }

     ZafCxC01_01 obj = new ZafCxC01_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis, strBufBus );
     obj.show();
     if(obj.acepta()){
         blnRes=true;
     }
     obj.dispose();
     obj=null;
           
 }catch(Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
 return blnRes;
}




@Override
public boolean insertar() {
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intCodDoc=0;
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
        strSqlDetUpdCli=new StringBuffer();

     if(objZafParSis.getCodigoMenu()==1648){  // RETENCIONES RECIBIDAS
       if(_getValidarFecFacFecDoc()){
        //if(_getValidadIdeSisIdeIng()){
        if(insertarCab(conn, intCodDoc)){
          if(insertarDet(conn, intCodDoc)){
           if(objAsiDia.insertarDiario(conn, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), String.valueOf(intCodDoc), txtAlt1.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy") )) {
            if(objAjuCenAut.realizaAjuCenAut(conn, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), intCodDoc,  80,  objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy") )) {
              conn.commit();
              txtCodDoc.setText(""+intCodDoc);
              blnRes=true;
              cargarDetIns(conn, intCodDoc);
          }else conn.rollback();
         }else conn.rollback();
        }else conn.rollback();
       }else conn.rollback();
      //}else conn.rollback(); //Este 'else' va relacionado con "if(_getValidadIdeSisIdeIng()"
      }else{  conn.rollback(); MensajeInf("EXISTE REGISTROS CON FECHA DIFERENTE HA LA FECHA DEL DOCUMENTO \n REVISE ANTES DE INGRESAR.."); }
     }
     if(objZafParSis.getCodigoMenu()==256){ // PAGO MASIVO A CLIENTE
        if(insertarCab(conn, intCodDoc)){
         if(reestructurarFacturas(conn)){
          if(insertarDetCobMas(conn, intCodDoc)){
           if(objAsiDia.insertarDiario(conn, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), String.valueOf(intCodDoc), txtAlt1.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy") )) {
            if(objAjuCenAut.realizaAjuCenAut(conn, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), intCodDoc,  80,  objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy") )) {
              conn.commit();
              txtCodDoc.setText(""+intCodDoc);
              blnRes=true;
              cargarDetIns(conn, intCodDoc);
          }else conn.rollback();
         }else conn.rollback();
        }else conn.rollback();
       }else conn.rollback();
      }else conn.rollback();
     }
    if(objZafParSis.getCodigoMenu()==488){  // PAGO MASIVO A PROVEEDORES
        if(insertarCab(conn, intCodDoc)){
         if(reestructurarFacturas(conn)){
          if(insertarDetCobMas(conn, intCodDoc)){
           if(objAsiDia.insertarDiario(conn, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), String.valueOf(intCodDoc), txtAlt1.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy") )) {
              conn.commit();
              txtCodDoc.setText(""+intCodDoc);
              blnRes=true;
              cargarDetIns(conn, intCodDoc);
         }else conn.rollback();
        }else conn.rollback();
       }else conn.rollback();
      }else conn.rollback();
     }

      strSqlInsDet=null;
      strSqlDetUpdCli=null;

       stmLoc.close();
       stmLoc=null;
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
      if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){
        if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){
         if( ((tblDat.getValueAt(i, INT_TBL_CODREGAAJU)==null?"":(tblDat.getValueAt(i, INT_TBL_CODREGAAJU).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODREGAAJU).toString())).equals("")) ){

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
      }}}}

     if(intEst==1){

         strSql="SELECT x.* FROM ( " +
         " SELECT *, ( abs(valpen)- abs(valapl) ) as val2 FROM ( "+stbFacSel.toString()+" ) AS x ) AS x " +
         " INNER JOIN tbm_emp AS a1 ON ( a1.co_emp = x.coemp )   " +
         " WHERE val2 > 0 and   ( abs(val2)  between  a1.nd_valminajucenaut and  a1.nd_valmaxajucenaut ) = false ";
         
         System.out.println("ZafCxC01.reestructurarFacturas: " + strSql );
         
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
           
           System.out.println("ZafCxC01.reestructurarFacturas: " + strSql );
           
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
                      
           System.out.println("ZafCxC01.reestructurarFacturas: " + strSql );
           
           stmLoc01.executeUpdate(strSql);


           /*************** SI HAY PAGO REALIZADO GENERE REGISTRO DE ESE PAGO Y CAMBIA DETPAG AL REGISTRO NUEVO **********************/

           strSql="SELECT nd_abo FROM tbm_pagmovinv WHERE co_emp="+rstLoc.getInt("coemp")+" and co_loc="+rstLoc.getInt("coloc")+" and " +
           " co_tipdoc="+rstLoc.getInt("cotipdoc")+"  and co_doc="+rstLoc.getInt("codoc")+"  and co_reg="+rstLoc.getInt("coreg")+"  " +
           " AND nd_abo > 0 ";
           
           System.out.println("ZafCxC01.reestructurarFacturas: " + strSql );
           
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
               
               System.out.println("ZafCxC01.reestructurarFacturas: " + strSql );
               
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
           
           System.out.println("ZafCxC01.reestructurarFacturas: " + strSql );
           
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
                        //JOptionPane oppMsg = new JOptionPane();
                        String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";
                        strMsg=" No. de Cobro ya existe... ?";
                        JOptionPane.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE,null);
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
        " ,'"+strFecDoc+"', "+(txtAlt1.getText().trim().equals("")?null:txtAlt1.getText())+", "+(txtAlt2.getText().trim().equals("")?null:txtAlt2.getText())+", '"+txaObs1.getText()+"', '"+txaObs2.getText()+"' " +
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


/*
 tx_numserret character varying(7),
  tx_numautsriret character varying(12),
  tx_feccadret character varying(7),
  tx_codsriret ch


select x.*, x1.tx_codsriret from (
select * from (
  select 1 as coemp, 603 as codcli, '301' as codsri  union all
  select 1 as coemp, 603 as cocli, '301' as codsri  union all
  select 1 as coemp, 603 as cocli, '301' as codsri union all
  select 1 as coemp, 1039 as cocli, '301' as codsri union all
  select 1 as coemp, 603 as cocli, '301'  as codsri
) as x
group by coemp, codcli, codsri
) as x
inner join tbm_cli as x1 on (x1.co_emp=x.coemp and x1.co_cli=x.codcli )
where x1.tx_codsriret != x.codsri


         */

/*
 * MODIFICADO EFLORESA 2012-03-23
 * Retenciones Recibidas:
 * Se quita que actualize los campos TX_SECDOC, TX_NUMAUTSRI, TX_FECCAD de la tabla TBM_cABMOVINV
 * Se quita que actualize el campo TX_CODSRI de la tabla TBM_DETPAG
 * Se agrega para que se actualizen los campos TX_NUMSER, TX_NUMAUTSRI, TX_FECCAD, TXCODSRI de la tabla TBM_PAGMOVINV 
 */
private boolean insertarDet(Connection conn, int intCodDoc){
 boolean blnRes=false;
 Statement stmLoc;
 String strCodEmp="",strCodLoc="", strCodTipDocRec="", strCodDoc="", strCodReg="", strAbono="0", strNumRet="", strFecDoc="";
 String strCodTipCon="", strNumAutSri="", strSecDoc="", strFecCad="", strCodSri="", strCodCli="", strAux, strLocIdeEmi, strNumSerNumRet;
 String strSql="";
 int intCodRegDet=0;
 int intConUpCli=0, intAux;
 Date datFecAuxLoc;
 
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";

       if(rdaAplMisCta.isSelected()) strCodTipCon= txtCodTipDocCon.getText();



     for(int i=0; i<tblDat.getRowCount(); i++){
      if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){
        if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){

          strCodCli =tblDat.getValueAt(i, INT_TBL_CODCLI).toString();
          strCodEmp=tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
          strCodLoc=tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
          strCodTipDocRec=tblDat.getValueAt(i, INT_TBL_CODTID).toString();
          strCodDoc=tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
          strCodReg=tblDat.getValueAt(i, INT_TBL_CODREG).toString();
          strAbono=(tblDat.getValueAt(i, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_ABONO).toString()));
          strNumRet=(tblDat.getValueAt(i, INT_TBL_NUMCHQ)==null?"":(tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString()));

          strSecDoc=(tblDat.getValueAt(i, INT_TBL_NUMSECDOC)==null?"":(tblDat.getValueAt(i, INT_TBL_NUMSECDOC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_NUMSECDOC).toString()));
          strNumAutSri=(tblDat.getValueAt(i, INT_TBL_NUMAUTSRI)==null?"":(tblDat.getValueAt(i, INT_TBL_NUMAUTSRI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_NUMAUTSRI).toString()));
          strFecCad=(tblDat.getValueAt(i, INT_TBL_FECCATDOC)==null?"":(tblDat.getValueAt(i, INT_TBL_FECCATDOC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_FECCATDOC).toString()));
          strCodSri=(tblDat.getValueAt(i, INT_TBL_CODSRIDOC)==null?"":(tblDat.getValueAt(i, INT_TBL_CODSRIDOC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODSRIDOC).toString()));
          strLocIdeEmi = "";
          strNumSerNumRet = "";
          
          if (strNumAutSri.length() != 10)
          {  //Esta verificacion solo se debera realizar cuando la autorizacion es electronica.
             strLocIdeEmi = (tblDat.getValueAt(i, INT_TBL_IDEEMI) == null? "" : (tblDat.getValueAt(i, INT_TBL_IDEEMI).toString().equals("")? "" :tblDat.getValueAt(i, INT_TBL_IDEEMI).toString()));
             intAux = Integer.parseInt(strNumRet);
             strAux = String.format("%09d", intAux); // => Ej: "000000001"
             strNumSerNumRet = strSecDoc.substring(0, 3) + "-" + strSecDoc.substring(3, 6) + "-" + strAux;
          }
          
          if(rdaAplDifCta.isSelected())
             strCodTipCon=(tblDat.getValueAt(i, INT_TBL_CODTIPDOC)==null?"":(tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString()));
             
          intCodRegDet++;
          /*strSqlInsDet.append(" INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag," +
          " co_docpag, co_regpag, nd_abo,  st_reg, st_regrep, co_tipdoccon, tx_numchq, tx_codsri )" +
          " VALUES( "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" " +
          " ,"+intCodRegDet+", "+strCodLoc+", "+strCodTipDocRec+", "+strCodDoc+", "+strCodReg+", "+strAbono+", 'A','I', "+strCodTipCon+", '"+strNumRet+"', '"+strCodSri+"'  ) ; ");

          strSqlInsDet.append(" UPDATE tbm_pagmovinv set nd_abo=nd_abo + "+strAbono+", " +
          " tx_numchq='"+strNumRet+"',  nd_monchq="+strAbono+", fe_recchq='"+strFecDoc+"', fe_venchq='"+strFecDoc+"',  " +
          " st_regrep='M' WHERE " +
          " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+" " +
          " and co_reg="+strCodReg+" ; ");


          strSqlInsDet.append(" UPDATE tbm_cabmovinv set tx_numautsri='"+strNumAutSri+"', tx_secdoc='"+strSecDoc+"', tx_feccad='"+strFecCad+"', tx_codsri='"+strCodSri+"' " +
          " WHERE co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+"  ; ");*/

          strSqlInsDet.append(" INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag," +
          " co_docpag, co_regpag, nd_abo,  st_reg, st_regrep, co_tipdoccon, tx_numchq )" +
          " VALUES( "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" " +
          " ,"+intCodRegDet+", "+strCodLoc+", "+strCodTipDocRec+", "+strCodDoc+", "+strCodReg+", "+strAbono+", 'A','I', "+strCodTipCon+", '"+strNumRet+"' ) ; ");

          /*
          strSqlInsDet.append(" UPDATE tbm_pagmovinv set nd_abo=nd_abo + "+strAbono+", " +
          " tx_numchq='"+strNumRet+"', nd_monchq="+strAbono+", fe_recchq='"+strFecDoc+"', fe_venchq='"+strFecDoc+"',  " +
          " st_regrep='M', tx_numautsri='"+strNumAutSri+"', tx_feccad='"+strFecCad+"', tx_codsri='"+strCodSri+"', tx_numser='"+strSecDoc+"' " +
          " WHERE " +
          " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+" " +
          " and co_reg="+strCodReg+" ; ");
          */
          
          strSql = " UPDATE tbm_pagmovinv set nd_abo=nd_abo + "+strAbono+", " +
          " tx_numchq='"+strNumRet+"', nd_monchq="+strAbono+", fe_recchq='"+strFecDoc+"', fe_venchq='"+strFecDoc+"',  " +
          " st_regrep='M', tx_numautsri='"+strNumAutSri+"', tx_feccad='"+strFecCad+"', tx_codsri='"+strCodSri+"', tx_numser='"+strSecDoc+"' " +
          " WHERE " +
          " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+" " +
          " and co_reg="+strCodReg;
          stmLoc.executeUpdate(strSql.toString());

          strSqlInsDet.append(" UPDATE tbm_cabmovinv set tx_codsri='"+strCodSri+"' " +
          " WHERE co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+"  ; ");
          
          if (strNumAutSri.length() != 10 && verificarValRetClientePagMovInv(conn, strCodEmp, strCodLoc, strCodTipDocRec, strCodDoc) == true)
          {  //Esta verificacion solo se debera realizar cuando la autorizacion es electronica.
             //Si es verdadero, significa que todos los valores de Retenciones de los clientes que aparecen en tbm_pagMovInv han sido dados de baja
             datFecAuxLoc = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
             strAux = objUti.formatearFecha(datFecAuxLoc, objZafParSis.getFormatoFechaHoraBaseDatos());
             strSqlInsDet.append(" UPDATE tbm_cabDocEleSri set st_asidocrel = 'S', fe_ultmod = '" + strAux + "', co_usrmod = " + objZafParSis.getCodigoUsuario() +
             " WHERE tx_ideemi = '" + strLocIdeEmi + "' and tx_tipdoc = 'R' and tx_numdoc = '" + strNumSerNumRet +"'; ");
          }
          
          if(intConUpCli==1) strSqlDetUpdCli.append(" UNION ALL ");
          strSqlDetUpdCli.append(" SELECT "+strCodEmp+" as coemp, "+strCodCli+" as cocli, text('"+strCodSri+"') as codsri, text('"+strSecDoc+"')  as numserret,text('"+strNumAutSri+"')  as numautsri,text('"+strFecCad+"')  as feccadsri   ");
          intConUpCli=1;

    }}}

      if(intCodRegDet > 0 ){
           System.out.println("ZafCxC01.insertarDet"+ strSqlInsDet.toString() );
           stmLoc.executeUpdate(strSqlInsDet.toString());


           strSql=" UPDATE tbm_cli SET tx_codsriret=x.codsri, tx_numserret=x.numserret " +
           " ,tx_numautsriret=x.numautsri, tx_feccadret=x.feccadsri  FROM ( " +
           " select x.*, x1.tx_codsriret, x1.tx_numserret, x1.tx_numautsriret, x1.tx_feccadret from ( " +
           " select * from ( "+strSqlDetUpdCli.toString()+" ) AS x "+
           " group by coemp, cocli, codsri, numserret, numautsri, feccadsri " +
           " ) as x " +
           " inner join tbm_cli as x1 on (x1.co_emp=x.coemp and x1.co_cli=x.cocli ) " +
           " WHERE ( x1.tx_codsriret is null or  x1.tx_codsriret != x.codsri ) " +
           " or ( x1.tx_numserret is null or  x1.tx_numserret != x.numserret ) " +
           " or ( x1.tx_numautsriret is null or  x1.tx_numautsriret != x.numautsri ) " +
           " or ( x1.tx_feccadret is null or  x1.tx_feccadret != x.feccadsri ) " +
           " ) AS x WHERE  tbm_cli.co_emp=x.coemp and tbm_cli.co_cli=x.cocli  ";
           System.out.println("insertarDet: "+ strSql  );
           stmLoc.executeUpdate(strSql);

           blnRes=true;
      }

      stmLoc.close();
      stmLoc=null;

 }}catch(SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


private boolean insertarDetCobMas(java.sql.Connection conn, int intCodDoc){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strCodEmp="",strCodLoc="", strCodTipDocRec="", strCodDoc="", strCodReg="", strAbono="0", strNumCta="",strNumChq="", strFecDoc="";
 String strCodTipCon="", strCodBan="", strFevVenChq="", strValDoc="";
 int intCodRegDet=0;
 double dblValDoc=0;
 int intTipMov=1;
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";

       if(rdaAplMisCta.isSelected()) strCodTipCon= txtCodTipDocCon.getText();



     for(int i=0; i<tblDat.getRowCount(); i++){
      if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){
        if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){

          strCodEmp=tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
          strCodLoc=tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
          strCodTipDocRec=tblDat.getValueAt(i, INT_TBL_CODTID).toString();
          strCodDoc=tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
          strCodReg=tblDat.getValueAt(i, INT_TBL_CODREG).toString();
          strAbono=(tblDat.getValueAt(i, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_ABONO).toString()));
          strNumCta=(tblDat.getValueAt(i, INT_TBL_NUMCTA)==null?"":(tblDat.getValueAt(i, INT_TBL_NUMCTA).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_NUMCTA).toString()));
          strNumChq=(tblDat.getValueAt(i, INT_TBL_NUMCHQ)==null?"":(tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString()));
          strCodBan=(tblDat.getValueAt(i, INT_TBL_CODBAN)==null?null:(tblDat.getValueAt(i, INT_TBL_CODBAN).toString().equals("")?null:tblDat.getValueAt(i, INT_TBL_CODBAN).toString()));
          strFevVenChq=(tblDat.getValueAt(i, INT_TBL_FECVENCHQ)==null?"":(tblDat.getValueAt(i, INT_TBL_FECVENCHQ).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_FECVENCHQ).toString()));

          strValDoc=(tblDat.getValueAt(i, INT_TBL_VALDOC)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALDOC).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALDOC).toString()));

          dblValDoc=objUti.redondear(strValDoc, 4);
          if(dblValDoc > 0 ){
            intTipMov=-1;
          }else{
              intTipMov=1;
          }

          if(rdaAplDifCta.isSelected())
             strCodTipCon=(tblDat.getValueAt(i, INT_TBL_CODTIPDOC)==null?"":(tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString()));

          if(!(strFevVenChq.equals("")))
            strFevVenChq = objUti.formatearFecha( strFevVenChq,"dd/MM/yyyy","yyyy/MM/dd");

          intCodRegDet++;
          strSqlInsDet.append(" INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag," +
          " co_docpag, co_regpag, nd_abo,  st_reg, st_regrep, co_tipdoccon, co_banchq, tx_numctachq, tx_numchq, fe_recchq, fe_venchq )" +
          " VALUES( "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" " +
          " ,"+intCodRegDet+", "+strCodLoc+", "+strCodTipDocRec+", "+strCodDoc+", "+strCodReg+", ("+strAbono+"*"+intTipMov+") , 'A','I', "+strCodTipCon+" " +
          " ,"+strCodBan+", '"+strNumCta+"', '"+strNumChq+"', '"+strFecDoc+"' " +
          " , "+(strFevVenChq.equals("")?null:"'"+strFevVenChq+"'")+"  ) ; ");

          strSqlInsDet.append(" UPDATE tbm_pagmovinv set nd_abo=nd_abo + ("+strAbono+"*"+intTipMov+"), " +
          " tx_numctachq='"+strNumCta+"', tx_numchq='"+strNumChq+"',  nd_monchq=("+strAbono+"*"+intTipMov+"), fe_recchq='"+strFecDoc+"', " +
          " fe_venchq="+(strFevVenChq.equals("")?null:"'"+strFevVenChq+"'")+",  " +
          " st_regrep='M' WHERE " +
          " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+" " +
          " and co_reg="+strCodReg+" ; ");

    }}}

      if(intCodRegDet > 0 ){
           System.out.println("--> "+ strSqlInsDet.toString()  );
           stmLoc.executeUpdate(strSqlInsDet.toString());
           blnRes=true;
      }

      stmLoc.close();
      stmLoc=null;

 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


/**
 * Permite recargar los datos de la tabla despues de insertar o modificar con objetivo de tener ejem:  codigo del registro
 * que eso da cuando se insertar
 * @param conn   coneccion de la base
 * @param intCodDoc   codigo del documento
 * @return  true si se consulto bien   false si hay algun error.
 * 
 * MODIFICADO EFLORESA 2012-03-23
 * Retenciones Recibidas:
 * Al consultar el detalle se agrega que busque en los campos TX_NUMSER, TX_NUMAUTSRI, TX_FECCAD, TXCODSRI de la tabla TBM_PAGMOVINV
 *
 */
private boolean cargarDetIns(Connection conn, int intCodDoc ){
  boolean blnRes=false;
  Statement stmLoc;
  ResultSet rstLoc;
  String strSql="";
  Vector vecData;
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

        vecData = new Vector();

        String sqlAuxDif="";
         if(objZafParSis.getCodigoMenu()==1648) sqlAuxDif=" , (abs(a1.mo_pag)-abs(a1.nd_abo) ) as dif ";  // retencion
         if(objZafParSis.getCodigoMenu()==256) sqlAuxDif=" , (abs(a1.mo_pag)-abs(a1.nd_abo) ) as dif "; //(abs(a1.nd_abo)-abs(a1.mo_pag) ) as dif ";
         if(objZafParSis.getCodigoMenu()==488) sqlAuxDif=" , (abs(a1.mo_pag)-abs(a1.nd_abo) ) as dif ";

        /*strSql="SELECT a2.tx_numautsri, a2.tx_secdoc, a2.tx_feccad, a2.tx_codsri,  " +
        " a1.tx_numctachq, a1.nd_monchq, a1.co_banchq, ban.tx_deslar as dlbanco,  a1.fe_venchq, a1.tx_numchq,  a.co_reg as coregpag, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg  "+sqlAuxDif+" " +
        " ,a2.co_cli, a2.tx_nomcli, a3.tx_descor, a3.tx_deslar, a2.ne_numdoc, a2.fe_doc, a1.ne_diacre, a1.nd_porret, a1.fe_ven, a1.mo_pag, a.nd_abo " +
        " ,a.co_tipdoccon, a4.tx_descor as txdctipdoc, a4.tx_deslar as txdltipdoc" +
        " ,a4.co_ctadeb, a5.tx_codcta AS txctadeb, a5.tx_deslar as nomctadeb,  a4.co_ctahab, a6.tx_codcta as txctahab, a6.tx_deslar as nomctahab " +
        " FROM tbm_detpag as a " +
        " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locpag and a1.co_tipdoc=a.co_tipdocpag and a1.co_doc=a.co_docpag and a1.co_reg=a.co_regpag ) " +
        " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) " +
        " inner join tbm_cabtipdoc as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc  ) " +
        " " +
        " inner join tbm_cabtipdoc as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoccon  ) " +
        " inner join tbm_placta as a5 on (a5.co_emp=a4.co_emp and a5.co_cta=a4.co_ctadeb ) " +
        " inner join tbm_placta as a6 on (a6.co_emp=a4.co_emp and a6.co_cta=a4.co_ctahab ) " +
        " LEFT  JOIN tbm_var as ban ON (ban.co_reg=a.co_banchq ) "+
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" " +
        " and a.co_tipdoc="+txtCodTipDoc.getText()+"  and a.co_Doc="+intCodDoc+" and a.st_reg='A' "+
        " ORDER BY a.co_reg ";*/
        
        /*strSql="SELECT case when a2.tx_numautsri is null then a1.tx_numautsri else a2.tx_numautsri end as tx_numautsri, " + 
        " case when a2.tx_secdoc is null then a1.tx_numser else a2.tx_secdoc end as tx_secdoc, " +
        " case when a2.tx_feccad is null then a1.tx_feccad else a2.tx_feccad end as tx_feccad, " + 
        " case when a2.tx_codsri is null then a1.tx_codsri else a2.tx_codsri end as tx_codsri, " +
        " a1.tx_numctachq, a1.nd_monchq, a1.co_banchq, ban.tx_deslar as dlbanco,  a1.fe_venchq, a1.tx_numchq,  a.co_reg as coregpag, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg  "+sqlAuxDif+" " +
        " ,a2.co_cli, a2.tx_nomcli, a3.tx_descor, a3.tx_deslar, a2.ne_numdoc, a2.fe_doc, a1.ne_diacre, a1.nd_porret, a1.fe_ven, a1.mo_pag, a.nd_abo " +
        " ,a.co_tipdoccon, a4.tx_descor as txdctipdoc, a4.tx_deslar as txdltipdoc" +
        " ,a4.co_ctadeb, a5.tx_codcta AS txctadeb, a5.tx_deslar as nomctadeb,  a4.co_ctahab, a6.tx_codcta as txctahab, a6.tx_deslar as nomctahab " +
        " FROM tbm_detpag as a " +
        " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locpag and a1.co_tipdoc=a.co_tipdocpag and a1.co_doc=a.co_docpag and a1.co_reg=a.co_regpag ) " +
        " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) " +
        " inner join tbm_cabtipdoc as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc  ) " +
        " " +
        " inner join tbm_cabtipdoc as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoccon  ) " +
        " inner join tbm_placta as a5 on (a5.co_emp=a4.co_emp and a5.co_cta=a4.co_ctadeb ) " +
        " inner join tbm_placta as a6 on (a6.co_emp=a4.co_emp and a6.co_cta=a4.co_ctahab ) " +
        " LEFT  JOIN tbm_var as ban ON (ban.co_reg=a.co_banchq ) "+
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" " +
        " and a.co_tipdoc="+txtCodTipDoc.getText()+"  and a.co_Doc="+intCodDoc+" and a.st_reg='A' "+
        " ORDER BY a.co_reg ";*/
        
        strSql="SELECT case when a2.tx_numautsri is null then a1.tx_numautsri else a2.tx_numautsri end as tx_numautsri, " + 
        " case when a2.tx_secdoc is null then a1.tx_numser else a2.tx_secdoc end as tx_secdoc, " +
        " case when a2.tx_feccad is null then a1.tx_feccad else a2.tx_feccad end as tx_feccad, " + 
        " case when a1.tx_codsri is null then a2.tx_codsri else a1.tx_codsri end as tx_codsri, " +
        " a1.tx_numctachq, a1.nd_monchq, a1.co_banchq, ban.tx_deslar as dlbanco,  a1.fe_venchq, a1.tx_numchq,  a.co_reg as coregpag, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg  "+sqlAuxDif+" " +
        " ,a2.co_cli, a2.tx_nomcli, a3.tx_descor, a3.tx_deslar, a2.ne_numdoc, a2.fe_doc, a1.ne_diacre, a1.nd_porret, a1.fe_ven, a1.mo_pag, a.nd_abo " +
        " ,a.co_tipdoccon, a4.tx_descor as txdctipdoc, a4.tx_deslar as txdltipdoc" +
        " ,a4.co_ctadeb, a5.tx_codcta AS txctadeb, a5.tx_deslar as nomctadeb,  a4.co_ctahab, a6.tx_codcta as txctahab, a6.tx_deslar as nomctahab " +
        " FROM tbm_detpag as a " +
        " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locpag and a1.co_tipdoc=a.co_tipdocpag and a1.co_doc=a.co_docpag and a1.co_reg=a.co_regpag ) " +
        " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) " +
        " inner join tbm_cabtipdoc as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc  ) " +
        " " +
        " inner join tbm_cabtipdoc as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoccon  ) " +
        " inner join tbm_placta as a5 on (a5.co_emp=a4.co_emp and a5.co_cta=a4.co_ctadeb ) " +
        " inner join tbm_placta as a6 on (a6.co_emp=a4.co_emp and a6.co_cta=a4.co_ctahab ) " +
        " LEFT  JOIN tbm_var as ban ON (ban.co_reg=a.co_banchq ) "+
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" " +
        " and a.co_tipdoc="+txtCodTipDoc.getText()+"  and a.co_Doc="+intCodDoc+" and a.st_reg='A' "+
        " ORDER BY a.co_reg ";
        
        System.out.println("ZafCxC01.cargarDetIns: " + strSql);
        
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

          java.util.Vector vecReg = new java.util.Vector();
             vecReg.add(INT_TBL_LINEA,"");
             vecReg.add(INT_TBL_CHKSEL, true);
             vecReg.add(INT_TBL_BUTCLI,"..");
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
             vecReg.add(INT_TBL_CODREGAAJU,  rstLoc.getString("coregpag") );
             vecReg.add(INT_TBL_ABONOORI,  rstLoc.getString("nd_abo") );
             vecReg.add(INT_TBL_CODTIPDOC, rstLoc.getString("co_tipdoccon") );
             vecReg.add(INT_TBL_DCOTIPDOC, rstLoc.getString("txdctipdoc") );
             vecReg.add(INT_TBL_BUTTIPDOC,"");
             vecReg.add(INT_TBL_DLATIPDOC, rstLoc.getString("txdltipdoc") );
             vecReg.add(INT_TBL_CODBAN, rstLoc.getString("co_banchq") );
             vecReg.add(INT_TBL_DSCBAN,"");
             vecReg.add(INT_TBL_BUTBAN,"");
             vecReg.add(INT_TBL_NOMBAN, rstLoc.getString("dlbanco") );
             vecReg.add(INT_TBL_NUMCTA, rstLoc.getString("tx_numctachq") );
             vecReg.add(INT_TBL_BUTRET,"..");
             vecReg.add(INT_TBL_IDEEMI,"");
             vecReg.add(INT_TBL_NUMCHQ, rstLoc.getString("tx_numchq") );
           
             if(!(rstLoc.getString("fe_venchq")==null))
             vecReg.add(INT_TBL_FECVENCHQ, objUti.formatearFecha(rstLoc.getDate("fe_venchq"), "dd/MM/yyyy") );
             else vecReg.add(INT_TBL_FECVENCHQ, "" );

             vecReg.add(INT_TBL_VALCHQ,  rstLoc.getString("nd_monchq") );
             vecReg.add(INT_TBL_CODCTADEB,  rstLoc.getString("co_ctadeb") );
             vecReg.add(INT_TBL_DCOCTADEB,  rstLoc.getString("txctadeb") );
             vecReg.add(INT_TBL_NOMCTADEB,  rstLoc.getString("nomctadeb") );
             vecReg.add(INT_TBL_CODCTAHAB,  rstLoc.getString("co_ctahab") );
             vecReg.add(INT_TBL_DCOCTAHAB,  rstLoc.getString("txctahab") );
             vecReg.add(INT_TBL_NOMCTAHAB,  rstLoc.getString("nomctahab") );

             vecReg.add(INT_TBL_NUMSECDOC, rstLoc.getString("tx_secdoc") );
             vecReg.add(INT_TBL_NUMAUTSRI, rstLoc.getString("tx_numautsri") );
             vecReg.add(INT_TBL_FECCATDOC, rstLoc.getString("tx_feccad") );
             vecReg.add(INT_TBL_CODSRIDOC, rstLoc.getString("tx_codsri") );

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
 }}catch(SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

@Override
public boolean modificar(){
    boolean blnRes=false;
  java.sql.Connection conn;
  int intCodDoc=0;
  int intCodTipDoc=0;
  String strAux="";
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

       if(!(intCodMnuDocIng == objZafParSis.getCodigoMenu()) ){
            MensajeInf("NO SE PUEDE ANULAR ESTE DOCUMENTO NO CORRESPONDE A ESTE PROGRAMA.\nNo es posible anular un documento anulado.");
            return false;
        }

    if(validaCampos()){

     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);

       intCodDoc=Integer.parseInt(txtCodDoc.getText());
       intCodTipDoc=Integer.parseInt(txtCodTipDoc.getText());

       if(obtenerEstAnu(conn, intCodTipDoc, intCodDoc )){

       strSqlInsDet=new StringBuffer();

       if(objZafParSis.getCodigoMenu()==1648){  // RETENCIONES RECIBIDAS
        if(modificarCab(conn, intCodTipDoc, intCodDoc)){
          if(modificarDet(conn, intCodTipDoc, intCodDoc)){
           if(objAsiDia.actualizarDiario(conn, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), intCodTipDoc, intCodDoc, txtCodDoc.getText(),objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), "A")){
            if(objAjuCenAut.realizaAjuCenAut(conn, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), intCodDoc,  80,  objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy") )) {
               conn.commit();
               blnRes=true;
               cargarDetIns( conn,  intCodDoc);
           }else conn.rollback();
          }else conn.rollback();
         }else conn.rollback();
        }else conn.rollback();
      }
      if(objZafParSis.getCodigoMenu()==256){  // COBRO MASIVO A CLIENTES
        if(modificarCab(conn, intCodTipDoc, intCodDoc)){
         if(reestructurarFacturas(conn)){
          if(modificarDetCobMas(conn, intCodTipDoc, intCodDoc)){
           if(objAsiDia.actualizarDiario(conn, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), intCodTipDoc, intCodDoc, txtCodDoc.getText(),objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), "A")){
            if(objAjuCenAut.realizaAjuCenAut(conn, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), intCodDoc,  80,  objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy") )) {
               conn.commit();
               blnRes=true;
               cargarDetIns( conn,  intCodDoc);
           }else conn.rollback();
          }else conn.rollback();
         }else conn.rollback();
        }else conn.rollback();
       }else conn.rollback();
      }
     if(objZafParSis.getCodigoMenu()==488){ // PAGO MASIVO PROVEEDORES
        if(modificarCab(conn, intCodTipDoc, intCodDoc)){
         if(reestructurarFacturas(conn)){
          if(modificarDetCobMas(conn, intCodTipDoc, intCodDoc)){
           if(objAsiDia.actualizarDiario(conn, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), intCodTipDoc, intCodDoc, txtCodDoc.getText(),objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), "A")){
               conn.commit();
               blnRes=true;
               cargarDetIns( conn,  intCodDoc);
          }else conn.rollback();
         }else conn.rollback();
        }else conn.rollback();
       }else conn.rollback();
      }
       strSqlInsDet=null;
     }

       conn.close();
       conn=null;
   }

  }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

private boolean modificarDetCobMas(java.sql.Connection conn, int intCodTipDoc,  int intCodDoc){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 String strCodEmp="",strCodLoc="", strCodTipDocRec="", strCodDoc="", strCodReg="", strAbono="0", strCodRegAju="", strNumRet="";
 String strCodTipCon="", strFecDoc="", strNumCta="", strNumChq="",strCodBan="", strFevVenChq="", strValDoc="";
 int intCodRegDet=0;
 int intEstMod=0;
 int intTipMov=1;
 double dblValDoc=0;
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

       strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";
       if(rdaAplMisCta.isSelected()) strCodTipCon= txtCodTipDocCon.getText();


     for(int i=0; i<tblDat.getRowCount(); i++){
      if( !((tblDat.getValueAt(i, INT_TBL_CODREGAAJU)==null?"":tblDat.getValueAt(i, INT_TBL_CODREGAAJU).toString()).equals("")) ){
       if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("false")) ){

          strCodEmp=tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
          strCodLoc=tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
          strCodTipDocRec=tblDat.getValueAt(i, INT_TBL_CODTID).toString();
          strCodDoc=tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
          strCodReg=tblDat.getValueAt(i, INT_TBL_CODREG).toString();
          strCodRegAju=tblDat.getValueAt(i, INT_TBL_CODREGAAJU).toString();
          strAbono=(tblDat.getValueAt(i, INT_TBL_ABONOORI)==null?"0":(tblDat.getValueAt(i, INT_TBL_ABONOORI).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_ABONOORI).toString()));

          intEstMod=1;
          strSqlInsDet.append(" UPDATE tbm_detpag SET  nd_abo=0,  st_reg='E', st_regrep='M' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND " +
          " co_loc="+objZafParSis.getCodigoLocal()+" AND  co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" AND co_reg="+strCodRegAju+" ;");

          strSqlInsDet.append(" UPDATE tbm_pagmovinv set nd_abo=nd_abo - "+strAbono+", st_regrep='M' " +
          " ,tx_numchq=null,  nd_monchq=0, fe_recchq=null, fe_venchq=null WHERE " +
          " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+" " +
          " and co_reg="+strCodReg+" ; ");

    }else{
        strCodRegAju=tblDat.getValueAt(i, INT_TBL_CODREGAAJU).toString();

        if(rdaAplDifCta.isSelected())
          strCodTipCon=(tblDat.getValueAt(i, INT_TBL_CODTIPDOC)==null?"":(tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString()));

        intEstMod=1;

        strSqlInsDet.append(" UPDATE tbm_detpag SET  co_tipdoccon="+strCodTipCon+", st_regrep='M' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND " +
        " co_loc="+objZafParSis.getCodigoLocal()+" AND  co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" AND co_reg="+strCodRegAju+" ;");

    }
  }}


    intCodRegDet = _getObtenerMaxCodRegDet(conn, intCodTipDoc, intCodDoc );

    for(int i=0; i<tblDat.getRowCount(); i++){
     if( ((tblDat.getValueAt(i, INT_TBL_CODREGAAJU)==null?"":tblDat.getValueAt(i, INT_TBL_CODREGAAJU).toString()).equals("")) ){
      if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){
        if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){

         
          strCodEmp=tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
          strCodLoc=tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
          strCodTipDocRec=tblDat.getValueAt(i, INT_TBL_CODTID).toString();
          strCodDoc=tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
          strCodReg=tblDat.getValueAt(i, INT_TBL_CODREG).toString();
          strAbono=(tblDat.getValueAt(i, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_ABONO).toString()));
          strNumCta=(tblDat.getValueAt(i, INT_TBL_NUMCTA)==null?"":(tblDat.getValueAt(i, INT_TBL_NUMCTA).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_NUMCTA).toString()));
          strNumChq=(tblDat.getValueAt(i, INT_TBL_NUMCHQ)==null?"":(tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString()));
          strCodBan=(tblDat.getValueAt(i, INT_TBL_CODBAN)==null?null:(tblDat.getValueAt(i, INT_TBL_CODBAN).toString().equals("")?null:tblDat.getValueAt(i, INT_TBL_CODBAN).toString()));
          strFevVenChq=(tblDat.getValueAt(i, INT_TBL_FECVENCHQ)==null?"":(tblDat.getValueAt(i, INT_TBL_FECVENCHQ).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_FECVENCHQ).toString()));

          strValDoc=(tblDat.getValueAt(i, INT_TBL_VALDOC)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALDOC).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALDOC).toString()));

          dblValDoc=objUti.redondear(strValDoc, 4);
          if(dblValDoc > 0 ){
            intTipMov=-1;
          }else{
              intTipMov=1;
          }

          if(rdaAplDifCta.isSelected())
             strCodTipCon=(tblDat.getValueAt(i, INT_TBL_CODTIPDOC)==null?"":(tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString()));

          if(!(strFevVenChq.equals("")))
            strFevVenChq = objUti.formatearFecha( strFevVenChq,"dd/MM/yyyy","yyyy/MM/dd");

          intCodRegDet++;
          strSqlInsDet.append(" INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag," +
          " co_docpag, co_regpag, nd_abo,  st_reg, st_regrep, co_tipdoccon, co_banchq, tx_numctachq, tx_numchq, fe_recchq, fe_venchq )" +
          " VALUES( "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" " +
          " ,"+intCodRegDet+", "+strCodLoc+", "+strCodTipDocRec+", "+strCodDoc+", "+strCodReg+", ("+strAbono+"*"+intTipMov+") , 'A','I', "+strCodTipCon+" " +
          " ,"+strCodBan+", '"+strNumCta+"', '"+strNumChq+"', '"+strFecDoc+"' " +
          " , "+(strFevVenChq.equals("")?null:"'"+strFevVenChq+"'")+"  ) ; ");

          strSqlInsDet.append(" UPDATE tbm_pagmovinv set nd_abo=nd_abo + ("+strAbono+"*"+intTipMov+"), " +
          " tx_numctachq='"+strNumCta+"', tx_numchq='"+strNumChq+"',  nd_monchq=("+strAbono+"*"+intTipMov+"), fe_recchq='"+strFecDoc+"', " +
          " fe_venchq="+(strFevVenChq.equals("")?null:"'"+strFevVenChq+"'")+",  " +
          " st_regrep='M' WHERE " +
          " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+" " +
          " and co_reg="+strCodReg+" ; ");

       
    }}}}


      if(intEstMod == 1 ){
          System.out.println("--> "+ strSqlInsDet.toString() );
           stmLoc.executeUpdate(strSqlInsDet.toString());
      }

      stmLoc.close();
      stmLoc=null;

 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

/*
 * MODIFICADO EFLORESA 2012-03-23
 * Retenciones Recibidas:
 * Se quita que actualize los campos TX_SECDOC, TX_NUMAUTSRI, TX_FECCAD de la tabla TBM_cABMOVINV
 * Se quita que actualize el campo TX_CODSRI de la tabla TBM_DETPAG
 * Se agrega para que se actualizen los campos TX_NUMSER, TX_NUMAUTSRI, TX_FECCAD, TXCODSRI de la tabla TBM_PAGMOVINV 
 */

private boolean modificarDet(Connection conn, int intCodTipDoc,  int intCodDoc){
 boolean blnRes=true;
 Statement stmLoc;
 String strCodEmp="",strCodLoc="", strCodTipDocRec="", strCodDoc="", strCodReg="", strAbono="0", strCodRegAju="", strNumRet="";
 String strCodTipCon="", strFecDoc="";
 String strNumAutSri="", strSecDoc="", strFecCad="", strCodSri="";
 int intCodRegDet=0;
 int intEstMod=0;
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

       strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";
       if(rdaAplMisCta.isSelected()) strCodTipCon= txtCodTipDocCon.getText();

        
     for(int i=0; i<tblDat.getRowCount(); i++){
      if( !((tblDat.getValueAt(i, INT_TBL_CODREGAAJU)==null?"":tblDat.getValueAt(i, INT_TBL_CODREGAAJU).toString()).equals("")) ){
       if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("false")) ){

          strCodEmp=tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
          strCodLoc=tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
          strCodTipDocRec=tblDat.getValueAt(i, INT_TBL_CODTID).toString();
          strCodDoc=tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
          strCodReg=tblDat.getValueAt(i, INT_TBL_CODREG).toString();
          strCodRegAju=tblDat.getValueAt(i, INT_TBL_CODREGAAJU).toString();
          strAbono=(tblDat.getValueAt(i, INT_TBL_ABONOORI)==null?"0":(tblDat.getValueAt(i, INT_TBL_ABONOORI).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_ABONOORI).toString()));

          intEstMod=1;
          strSqlInsDet.append(" UPDATE tbm_detpag SET  nd_abo=0,  st_reg='E', st_regrep='M' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND " +
          " co_loc="+objZafParSis.getCodigoLocal()+" AND  co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" AND co_reg="+strCodRegAju+" ;");

          strSqlInsDet.append(" UPDATE tbm_pagmovinv set nd_abo=nd_abo - "+strAbono+", st_regrep='M' " +
          " ,tx_numchq=null,  nd_monchq=0, fe_recchq=null, fe_venchq=null WHERE " +
          " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+" " +
          " and co_reg="+strCodReg+" ; ");

    }else{
        strCodRegAju=tblDat.getValueAt(i, INT_TBL_CODREGAAJU).toString();
        
        if(rdaAplDifCta.isSelected())
          strCodTipCon=(tblDat.getValueAt(i, INT_TBL_CODTIPDOC)==null?"":(tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString()));

        intEstMod=1;

        strSqlInsDet.append(" UPDATE tbm_detpag SET co_tipdoccon="+strCodTipCon+", st_regrep='M' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND " +
        " co_loc="+objZafParSis.getCodigoLocal()+" AND  co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" AND co_reg="+strCodRegAju+" ;");
           
    }
  }}


    intCodRegDet = _getObtenerMaxCodRegDet(conn, intCodTipDoc, intCodDoc );

    for(int i=0; i<tblDat.getRowCount(); i++){
     if( ((tblDat.getValueAt(i, INT_TBL_CODREGAAJU)==null?"":tblDat.getValueAt(i, INT_TBL_CODREGAAJU).toString()).equals("")) ){
      if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){
        if( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) ){

          strCodEmp=tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
          strCodLoc=tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
          strCodTipDocRec=tblDat.getValueAt(i, INT_TBL_CODTID).toString();
          strCodDoc=tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
          strCodReg=tblDat.getValueAt(i, INT_TBL_CODREG).toString();
          strAbono=(tblDat.getValueAt(i, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_ABONO).toString()));
          strNumRet=(tblDat.getValueAt(i, INT_TBL_NUMCHQ)==null?"":(tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_NUMCHQ).toString()));

          strNumAutSri=(tblDat.getValueAt(i, INT_TBL_NUMAUTSRI)==null?"":(tblDat.getValueAt(i, INT_TBL_NUMAUTSRI).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_NUMAUTSRI).toString()));
          strSecDoc=(tblDat.getValueAt(i, INT_TBL_NUMSECDOC)==null?"":(tblDat.getValueAt(i, INT_TBL_NUMSECDOC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_NUMSECDOC).toString()));
          strFecCad=(tblDat.getValueAt(i, INT_TBL_FECCATDOC)==null?"":(tblDat.getValueAt(i, INT_TBL_FECCATDOC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_FECCATDOC).toString()));
          strCodSri=(tblDat.getValueAt(i, INT_TBL_CODSRIDOC)==null?"":(tblDat.getValueAt(i, INT_TBL_CODSRIDOC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODSRIDOC).toString()));


          if(rdaAplDifCta.isSelected())
             strCodTipCon=(tblDat.getValueAt(i, INT_TBL_CODTIPDOC)==null?"":(tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString()));

          intEstMod=1;
         
          /*strSqlInsDet.append(" INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag," +
          " co_docpag, co_regpag, nd_abo,  st_reg, st_regrep, co_tipdoccon, tx_numchq, tx_codsri  )" +
          " VALUES( "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" " +
          " ,"+intCodRegDet+", "+strCodLoc+", "+strCodTipDocRec+", "+strCodDoc+", "+strCodReg+", "+strAbono+", 'A','I', "+strCodTipCon+", '"+strNumRet+"', '"+strCodSri+"'  ) ; ");
          intCodRegDet++;
          
          strSqlInsDet.append(" UPDATE tbm_pagmovinv set nd_abo=nd_abo + "+strAbono+", " +
          " tx_numchq='"+strNumRet+"',  nd_monchq="+strAbono+", fe_recchq='"+strFecDoc+"', fe_venchq='"+strFecDoc+"',  " +
          " st_regrep='M' WHERE " +
          " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+" " +
          " and co_reg="+strCodReg+" ; ");

          strSqlInsDet.append(" UPDATE tbm_cabmovinv set tx_numautsri='"+strNumAutSri+"', tx_secdoc='"+strSecDoc+"', tx_feccad='"+strFecCad+"', tx_codsri='"+strCodSri+"' " +
          " WHERE co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+"  ; ");*/

          strSqlInsDet.append(" INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag," +
          " co_docpag, co_regpag, nd_abo,  st_reg, st_regrep, co_tipdoccon, tx_numchq )" +
          " VALUES( "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" " +
          " ,"+intCodRegDet+", "+strCodLoc+", "+strCodTipDocRec+", "+strCodDoc+", "+strCodReg+", "+strAbono+", 'A','I', "+strCodTipCon+", '"+strNumRet+"' ) ; ");
          intCodRegDet++;
          
          strSqlInsDet.append(" UPDATE tbm_pagmovinv set nd_abo=nd_abo + "+strAbono+", " +
          " tx_numchq='"+strNumRet+"',  nd_monchq="+strAbono+", fe_recchq='"+strFecDoc+"', fe_venchq='"+strFecDoc+"',  " +
          " st_regrep='M', tx_numautsri='"+strNumAutSri+"', tx_feccad='"+strFecCad+"', tx_codsri='"+strCodSri+"', tx_numser = '"+strSecDoc+"' " +
          " WHERE " +
          " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+" " +
          " and co_reg="+strCodReg+" ; ");

          strSqlInsDet.append(" UPDATE tbm_cabmovinv set tx_codsri='"+strCodSri+"' " +
          " WHERE co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+"  ; ");



    }}}}


      if(intEstMod == 1 ){
           System.out.println("ZafCxC01.modificarDet"+ strSqlInsDet.toString() );
           stmLoc.executeUpdate(strSqlInsDet.toString());
      }

      stmLoc.close();
      stmLoc=null;

 }}catch(SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
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
         " ne_numdoc2="+(txtAlt2.getText().trim().equals("")?null:txtAlt2.getText())+", " +
         " tx_obs1='"+txaObs1.getText()+"', tx_obs2='"+txaObs2.getText()+"', " +
         " nd_mondoc="+(valDoc.getText().equals("")?"0":valDoc.getText())+", "+
         " fe_ultmod="+objZafParSis.getFuncionFechaHoraBaseDatos()+", " +
         " co_usrmod="+objZafParSis.getCodigoUsuario()+", "+
         " tx_commod='"+objZafParSis.getNombreComputadoraConDirIP()+"', st_regrep='M' "+
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

   







 public void  clnTextos(){
    strCodTipDoc=""; strDesCodTipDoc=""; strDesLarTipDoc="";
    strCodTipDocCon=""; strDesCorTipDocCon=""; strDesLarTipDocCon="";
    strCodCtaDeb="";
    strCodCtaHab="";

    txtFecDoc.setText("");

    txtCodTipDoc.setText("");
    txtDesCodTitpDoc.setText("");
    txtDesLarTipDoc.setText("");

    txtCodTipDocCon.setText("");
    txtDesCorTipDocCon.setText("");
    txtDesLarTipDocCon.setText("");

    valDoc.setText("0.00");
    txtFecDoc.setText("");
    txtAlt1.setText("");
    txtAlt2.setText("");
    txtCodDoc.setText("");
    txaObs1.setText("");
    txaObs2.setText("");

    objTblMod.removeAllRows();
   
    objAsiDia.inicializar();
    objAsiDia.setEditable(true);

 }




        @Override
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
                butCarArcRet.setEnabled(true);
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




        @Override
        public boolean aceptar() {
            return true;
        }

        @Override
        public boolean afterAceptar() {
            return true;
        }

        @Override
        public boolean afterAnular() {
            return true;
        }

        @Override
        public boolean afterCancelar() {
            butCarArcRet.setEnabled(true);
            return true;
        }

        @Override
        public boolean afterConsultar() {

            return true;
        }

        @Override
        public boolean afterEliminar() {
            return true;
        }

        @Override
        public boolean afterImprimir() {
            return true;
        }

        @Override
        public boolean afterInsertar() {
            this.setEstado('w');

            return true;
        }

        @Override
        public boolean afterModificar() {

           this.setEstado('w');

            return true;
        }

        @Override
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





        @Override
        public boolean consultar() {
                /*
                 * Esto Hace en caso de que el modo de operacion sea Consulta
                 */
           butCarArcRet.setEnabled(true);
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
 * 
 * MODIFICADO EFLORESA 2012-03-23
 * Retenciones Recibidas:
 * Al consultar el detalle se agrega que busque en los campos TX_NUMSER, TX_NUMAUTSRI, TX_FECCAD, TXCODSRI de la tabla TBM_PAGMOVINV
 *
 */
private boolean refrescaDatos(Connection conn, java.sql.ResultSet rstDatRec ){
    boolean blnRes=false;
    Statement stmLoc;
    ResultSet rstLoc, rstLoc02;
    String strSql="";
    String strAux="", strIdeEmi;
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
        txtAlt2.setText( rstLoc02.getString("ne_numdoc2"));
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

       
       cargarTiPDocCon( conn, rstDatRec.getInt("co_emp"),  rstDatRec.getInt("co_loc"),  rstDatRec.getInt("co_tipdoc"),  rstDatRec.getInt("co_doc") );

       
        /**********CARGAR DATOS DE DETALLE ***************/
        vecData = new Vector();

         String sqlAuxDif="";
         if(objZafParSis.getCodigoMenu()==1648) sqlAuxDif=" , ( a1.mo_pag + a1.nd_abo  ) as dif "; //retencion
         if(objZafParSis.getCodigoMenu()==256) sqlAuxDif=" , ( a1.mo_pag + a1.nd_abo  ) as dif ";  // (abs(a1.nd_abo)-abs(a1.mo_pag) ) as dif ";
         if(objZafParSis.getCodigoMenu()==488) sqlAuxDif=" , ( a1.mo_pag + a1.nd_abo  ) as dif ";

        /*strSql="SELECT a2.tx_numautsri, a2.tx_secdoc, a2.tx_feccad, a.tx_codsri, " +
        " a1.tx_numctachq, a1.nd_monchq, a1.co_banchq, ban.tx_deslar as dlbanco,  a1.fe_venchq, a.tx_numchq, a.co_reg as coregpag, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg "+sqlAuxDif+"  " +
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
        "  LEFT join tbm_placta as a5 on (a5.co_emp=a4.co_emp and a5.co_cta=a4.co_ctadeb ) " +
        "  LEFT join tbm_placta as a6 on (a6.co_emp=a4.co_emp and a6.co_cta=a4.co_ctahab ) " +
        "  LEFT  JOIN tbm_var as ban ON (ban.co_reg=a.co_banchq ) " +
        " WHERE a.co_emp="+rstDatRec.getInt("co_emp")+" and a.co_loc="+rstDatRec.getInt("co_loc")+" " +
        " and a.co_tipdoc="+rstDatRec.getInt("co_tipdoc")+"  and a.co_Doc="+rstDatRec.getInt("co_doc")+" and a.st_reg='A' "+
        " ORDER BY a.co_reg ";*/
         
        /*strSql="SELECT case when a2.tx_numautsri is null then a1.tx_numautsri else a2.tx_numautsri end as tx_numautsri, " + 
        " case when a2.tx_secdoc is null then a1.tx_numser else a2.tx_secdoc end as tx_secdoc, " +
        " case when a2.tx_feccad is null then a1.tx_feccad else a2.tx_feccad end as tx_feccad, " + 
        " case when a2.tx_codsri is null then a1.tx_codsri else a2.tx_codsri end as tx_codsri, " +
        " a1.tx_numctachq, a1.nd_monchq, a1.co_banchq, ban.tx_deslar as dlbanco,  a1.fe_venchq, a.tx_numchq, a.co_reg as coregpag, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg "+sqlAuxDif+"  " +
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
        "  LEFT join tbm_placta as a5 on (a5.co_emp=a4.co_emp and a5.co_cta=a4.co_ctadeb ) " +
        "  LEFT join tbm_placta as a6 on (a6.co_emp=a4.co_emp and a6.co_cta=a4.co_ctahab ) " +
        "  LEFT  JOIN tbm_var as ban ON (ban.co_reg=a.co_banchq ) " +
        " WHERE a.co_emp="+rstDatRec.getInt("co_emp")+" and a.co_loc="+rstDatRec.getInt("co_loc")+" " +
        " and a.co_tipdoc="+rstDatRec.getInt("co_tipdoc")+"  and a.co_Doc="+rstDatRec.getInt("co_doc")+" and a.st_reg='A' "+
        " ORDER BY a.co_reg ";*/
         
        strSql="SELECT case when a2.tx_numautsri is null then a1.tx_numautsri else a2.tx_numautsri end as tx_numautsri, " + 
        " case when a2.tx_secdoc is null then a1.tx_numser else a2.tx_secdoc end as tx_secdoc, " +
        " case when a2.tx_feccad is null then a1.tx_feccad else a2.tx_feccad end as tx_feccad, " + 
        " case when a1.tx_codsri is null then a2.tx_codsri else a1.tx_codsri end as tx_codsri, " +
        " a1.tx_numctachq, a1.nd_monchq, a1.co_banchq, ban.tx_deslar as dlbanco,  a1.fe_venchq, a.tx_numchq, a.co_reg as coregpag, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg "+sqlAuxDif+"  " +
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
        "  LEFT join tbm_placta as a5 on (a5.co_emp=a4.co_emp and a5.co_cta=a4.co_ctadeb ) " +
        "  LEFT join tbm_placta as a6 on (a6.co_emp=a4.co_emp and a6.co_cta=a4.co_ctahab ) " +
        "  LEFT  JOIN tbm_var as ban ON (ban.co_reg=a.co_banchq ) " +
        " WHERE a.co_emp="+rstDatRec.getInt("co_emp")+" and a.co_loc="+rstDatRec.getInt("co_loc")+" " +
        " and a.co_tipdoc="+rstDatRec.getInt("co_tipdoc")+"  and a.co_Doc="+rstDatRec.getInt("co_doc")+" and a.st_reg='A' "+
        " ORDER BY a.co_reg ";
        
        System.out.println("ZafCxC01.refrescaDatos: " + strSql);
        
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){
             strIdeEmi = getIdeEmisor(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_cli"));
             java.util.Vector vecReg = new java.util.Vector();
             vecReg.add(INT_TBL_LINEA,"");
             vecReg.add(INT_TBL_CHKSEL, true);
             vecReg.add(INT_TBL_BUTCLI,"..");
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
             vecReg.add(INT_TBL_CODREGAAJU,  rstLoc.getString("coregpag") );
             vecReg.add(INT_TBL_ABONOORI,  rstLoc.getString("nd_abo") );
             vecReg.add(INT_TBL_CODTIPDOC, rstLoc.getString("co_tipdoccon") );
             vecReg.add(INT_TBL_DCOTIPDOC, rstLoc.getString("txdctipdoc") );
             vecReg.add(INT_TBL_BUTTIPDOC,"");
             vecReg.add(INT_TBL_DLATIPDOC, rstLoc.getString("txdltipdoc") );
             vecReg.add(INT_TBL_CODBAN, rstLoc.getString("co_banchq") );
             vecReg.add(INT_TBL_DSCBAN,"");
             vecReg.add(INT_TBL_BUTBAN,"");
             vecReg.add(INT_TBL_NOMBAN, rstLoc.getString("dlbanco") );
             vecReg.add(INT_TBL_NUMCTA, rstLoc.getString("tx_numctachq") );
             vecReg.add(INT_TBL_BUTRET,"..");
             vecReg.add(INT_TBL_IDEEMI, strIdeEmi);
             vecReg.add(INT_TBL_NUMCHQ, rstLoc.getString("tx_numchq") );
             
             if(!(rstLoc.getString("fe_venchq")==null))
             vecReg.add(INT_TBL_FECVENCHQ, objUti.formatearFecha(rstLoc.getDate("fe_venchq"), "dd/MM/yyyy") );
             else vecReg.add(INT_TBL_FECVENCHQ, "" );

             vecReg.add(INT_TBL_VALCHQ,  rstLoc.getString("nd_monchq") );
             vecReg.add(INT_TBL_CODCTADEB,  rstLoc.getString("co_ctadeb") );
             vecReg.add(INT_TBL_DCOCTADEB,  rstLoc.getString("txctadeb") );
             vecReg.add(INT_TBL_NOMCTADEB,  rstLoc.getString("nomctadeb") );
             vecReg.add(INT_TBL_CODCTAHAB,  rstLoc.getString("co_ctahab") );
             vecReg.add(INT_TBL_DCOCTAHAB,  rstLoc.getString("txctahab") );
             vecReg.add(INT_TBL_NOMCTAHAB,  rstLoc.getString("nomctahab") );
             vecReg.add(INT_TBL_NUMSECDOC, rstLoc.getString("tx_secdoc") );
             vecReg.add(INT_TBL_NUMAUTSRI, rstLoc.getString("tx_numautsri") );
             vecReg.add(INT_TBL_FECCATDOC, rstLoc.getString("tx_feccad") );
             vecReg.add(INT_TBL_CODSRIDOC, rstLoc.getString("tx_codsri") );

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
 }}catch(SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
   catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}

   private String getIdeEmisor(Connection conn, int intCodEmp, int intCodCli)
   {
      String strRes = "", strSQL;
      java.sql.Statement stmLoc;
      java.sql.ResultSet rstLoc;
      
      try
      {  if (conn != null)
         {  stmLoc = conn.createStatement();
            strRes = "";   
            strSQL =  "SELECT tx_ide FROM tbm_cli WHERE st_reg = 'A' and co_emp = " + intCodEmp;
            strSQL += " and co_cli = " + intCodCli;
            rstLoc = stmLoc.executeQuery(strSQL);

            if (rstLoc.next())
               strRes = rstLoc.getString("tx_ide");
            
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
         }
      } //try
      
      catch(Exception e)
      {  
         strRes = "";
      }
      
      return strRes;
   } //Funcion getIdeEmisor()

   /**
    * Este metodo se encarga de verificar si todos los valores de Retenciones de los clientes que aparecen en tbm_pagMovInv han sido dados de baja.
    * @param con Conexion con la base de datos.
    * @param intCodEmp Codigo de empresa.
    * @param intCodLoc Codigo del local.
    * @param intCodTipDoc Codigo de tipo de documento.
    * @param intCodDoc Codigo del documento.
    * @return true: Indica que todos los valores de Retenciones de los clientes que aparecen en tbm_pagMovInv han sido dados de baja.
   * <BR>false: En el caso contrario.
   */
   private boolean verificarValRetClientePagMovInv(Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc)
   {
      boolean blnRes = true;
      String strSQL;
      int i, intConRetDadBaj;
      BigDecimal bdeAux, bdePorRet;
      java.sql.Statement stmLoc;
      java.sql.ResultSet rstLoc;
      
      try
      {  if (conn != null)
         {  stmLoc = conn.createStatement();
            strSQL =  "SELECT (mo_pag + nd_abo) as val_pen, nd_porret ";
            strSQL += "FROM   tbm_pagmovinv ";
            strSQL += "WHERE  st_reg in ('A','C') and nd_porret <> 0 ";
            strSQL += "       and co_emp = " + strCodEmp;
            strSQL += "       and co_loc = " + strCodLoc;
            strSQL += "       and co_tipdoc = " + strCodTipDoc;
            strSQL += "       and co_doc = " + strCodDoc;
            rstLoc = stmLoc.executeQuery(strSQL);
            i = 0;
            intConRetDadBaj = 0;

            while (rstLoc.next())
            {  i++;
               bdePorRet = rstLoc.getBigDecimal("nd_porret");
               bdePorRet = objUti.redondearBigDecimal(bdePorRet, objZafParSis.getDecimalesMostrar());
               bdeAux = rstLoc.getBigDecimal("val_pen");
               bdeAux = objUti.redondearBigDecimal(bdeAux, objZafParSis.getDecimalesMostrar());
               if (bdeAux.equals(new BigDecimal("0.00")))
               {  //Si es cero, significa que dicho valor de Retencion que aparece en tbm_pagMovInv ya fue dado de baja
                  intConRetDadBaj++; //Contador de Retenciones Dadas de Baja
               }
            }
            
            if (i == intConRetDadBaj)
            {  //Si es igual, significa que todos los valores de Retenciones de los clientes que aparecen en tbm_pagMovInv han sido dados de baja
               blnRes = true;
            }
            else
            {  //Si no es igual, significa que hay valores de Retenciones de los clientes que aparecen en tbm_pagMovInv pendientes en ser dados de baja
               blnRes = false;
            }
         
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
         } //if (con != null)
      } //try
      
      catch(Exception e)
      {  
         blnRes = false;
      }
      
      return blnRes;
   } //Funcion verificarValRetClientePagMovInv()

   
private void cargarTiPDocCon(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
   java.sql.Statement stmLoc;
   java.sql.ResultSet rstLoc;
   String strSql="";
   try{
      if(conn!=null){
        stmLoc=conn.createStatement();

       strSql="SELECT a.co_tipdoccon, a1.tx_descor, a1.tx_deslar " +
       " ,a1.co_ctadeb, a3.tx_codcta AS txctadeb, a3.tx_deslar as nomctadeb,  a1.co_ctahab, a4.tx_codcta as txctahab, a4.tx_deslar as nomctahab  " +
       " ,( select count(distinct(x.co_tipdoccon)) from tbm_detpag as x where x.co_emp = "+intCodEmp+"  and x.co_loc= "+intCodLoc+" and x.co_tipdoc="+intCodTipDoc+" and x.co_doc="+intCodDoc+" and x.st_reg='A'  ) as cantipdoccon " +
       " FROM tbm_detpag as a " +
       " inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoccon ) " +
       " LEFT JOIN tbm_placta AS a3 on (a3.co_emp = a1.co_emp and a3.co_cta = a1.co_ctadeb )  " +
       " LEFT JOIN tbm_placta AS a4 on (a4.co_emp = a1.co_emp and a4.co_cta = a1.co_ctahab ) " +
       " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a.st_reg='A' " +
       " GROUP BY a.co_tipdoccon, a1.tx_descor, a1.tx_deslar " +
       "  ,a1.co_ctadeb, a3.tx_codcta , a3.tx_deslar ,  a1.co_ctahab, a4.tx_codcta , a4.tx_deslar   ";
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next()){
          
          if( rstLoc.getInt("cantipdoccon") == 1 ){
              rdaAplMisCta.setSelected(true);
              txtCodTipDocCon.setText(rstLoc.getString("co_tipdoccon"));
              txtDesCorTipDocCon.setText(rstLoc.getString("tx_descor"));
              txtDesLarTipDocCon.setText(rstLoc.getString("tx_deslar"));
              txtDesCorTipDocCon.setEditable(true);
              txtDesLarTipDocCon.setEditable(true);
              butTipDocCon.setEnabled(true);

              strCodCtaDeb=rstLoc.getString("co_ctadeb");
              strTxtCodCtaDeb=rstLoc.getString("txctadeb");
              strNomCtaDeb=rstLoc.getString("nomctadeb");
              strCodCtaHab=rstLoc.getString("co_ctahab");
              strTxtCodCtaHab=rstLoc.getString("txctahab");
              strNomCtaHab=rstLoc.getString("nomctahab");

              ocultaCol(INT_TBL_DCOTIPDOC);
              ocultaCol(INT_TBL_BUTTIPDOC);
              ocultaCol(INT_TBL_DLATIPDOC);

          }else{
              
             rdaAplDifCta.setSelected(true);
             MostrarCol(INT_TBL_DCOTIPDOC,50);
             MostrarCol(INT_TBL_BUTTIPDOC,20);
             MostrarCol(INT_TBL_DLATIPDOC,150);

             java.awt.Color colBack;
             colBack = txtDesCorTipDocCon.getBackground();
             txtDesCorTipDocCon.setEditable(false);
             txtDesCorTipDocCon.setBackground(colBack);
             txtDesLarTipDocCon.setEditable(false);
             txtDesLarTipDocCon.setBackground(colBack);
             butTipDocCon.setEnabled(false);

             txtCodTipDocCon.setText("");
             txtDesCorTipDocCon.setText("");
             txtDesLarTipDocCon.setText("");

             strCodCtaDeb="";
             strTxtCodCtaDeb="";
             strNomCtaDeb="";
             strCodCtaHab="";
             strTxtCodCtaHab="";
             strNomCtaHab="";

          }

       }
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;
       
 }}catch(java.sql.SQLException Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  }
   catch(Exception Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  }
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





        @Override
public void clickModificar(){
 try{


  java.awt.Color colBack;
  colBack = txtCodDoc.getBackground();


  bloquea(txtCodDoc, colBack, false);
  bloquea(txtDesCodTitpDoc, colBack, false);
  bloquea(txtDesLarTipDoc, colBack, false);

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


  if( rdaAplDifCta.isSelected()){
     bloquea(txtDesCorTipDocCon, colBack, false);
     bloquea(txtDesLarTipDocCon, colBack, false);
     butTipDocCon.setEnabled(false);
  }


  setEditable(true);
  objTblMod.setDataModelChanged(false);

 blnHayCam=false;


 }catch(Exception evt){ objUti.mostrarMsgErr_F1(this, evt); }
}





        //******************************************************************************************************

        @Override
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




@Override
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
//                        conIns.close();
//                        conIns=null;
//                    }
//                }
//                catch (JRException e) {  objUti.mostrarMsgErr_F1(this, e);    }
//            } catch(java.sql.SQLException ex) {  objUti.mostrarMsgErr_F1(this, ex);   }
            return true;
        }


        //******************************************************************************************************


        @Override
        public void clickImprimir(){
        }

        @Override
        public void clickVisPreliminar(){
        }

        @Override
        public void clickCancelar(){

        }

        public void cierraConnections(){

        }

        @Override
        public boolean beforeAceptar() {
            return true;
        }

        @Override
        public boolean beforeAnular() {
            return true;
        }

        @Override
        public boolean beforeCancelar() {
            return true;
        }

        @Override
        public boolean beforeConsultar() {
            return true;
        }

        @Override
        public boolean beforeEliminar() {
            return true;
        }

        @Override
        public boolean beforeImprimir() {

            return true;
        }

        @Override
        public boolean beforeInsertar() {
            
            
            
            return true;
        }

        @Override
        public boolean beforeModificar() {
            return true;
        }

        @Override
        public boolean beforeVistaPreliminar() {

            return true;
        }




    }


private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 @Override
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblDat.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LINEA:
            strMsg="";
            break;
            case INT_TBL_CHKSEL:
            strMsg="Selección de factura.";
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
            strMsg="Numero de documento.";
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

            case INT_TBL_IDEEMI:
            strMsg="ID de emisor.. ";
            break;
               
            case INT_TBL_NUMCHQ:
            strMsg="Numero de retención.. ";
            break;

            case INT_TBL_NUMSECDOC:
            strMsg="Numero de serie del documento. ";
            break;

            case INT_TBL_NUMAUTSRI:
            strMsg="Numero de autorización del sri. ";
            break;

            case INT_TBL_FECCATDOC:
            strMsg="Fecha de caducidad del documento. ";
            break;


            case INT_TBL_CODSRIDOC:
            strMsg="Código del sri. ";
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

        @Override
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
        String strRutRpt, strNomRpt, strNomUsr="";
        int i, intNumTotRpt;
        boolean blnRes=true;
        try
        {
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
             strNomUsr=objZafParSis.getNombreUsuario();


            if (objRptSis.getOpcionSeleccionada()==ZafRptSis.INT_OPC_ACE)
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

    @Override
    protected void finalize() throws Throwable {   
        //System.gc();
        Runtime.getRuntime().gc();
        super.finalize();
    }  
    
    
    public void cargarNumeroDoc(){
  java.sql.Connection  conn;
  java.sql.Statement stmNum;
  java.sql.ResultSet rstNum;
  String strSql="";
  try{
     conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
        stmNum=conn.createStatement();


        strSql+="";
        strSql+=" SELECT MAX(doc.ne_ultdoc)+1 as numDoc";
        strSql+=" FROM tbm_cabtipdoc as doc ";
        strSql+=" WHERE   doc.co_emp =" + objZafParSis.getCodigoEmpresa() +"  and doc.co_loc = " + objZafParSis.getCodigoLocal() + " AND  ";
        strSql+=" doc.tx_desCor like '" + txtDesCodTitpDoc.getText() + "' AND doc.st_reg = 'A' ";
        
        rstNum=stmNum.executeQuery(strSql);
        if(rstNum.next()){
               txtAlt1.setText(((rstNum.getString("numDoc")==null)?"":rstNum.getString("numDoc")));
        }
        rstNum.close();
        stmNum.close();
        stmNum=null;
        rstNum=null;
        conn.close();
        conn=null;
  }}catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }
}
    
   private String getCodSri(int intCodTipRet, String strIsItmFacSrvTra)
   {
      String strRes = "", strSQL;
      java.sql.Statement stm;
      java.sql.ResultSet rst;
      
      try
      {  //strIsItmFacSrvTra = Parametro que indica si el item de la factura es un Servicio de transporte.
         stm = CONN_GLO.createStatement();
         strSQL =  "SELECT distinct tx_codsri ";
         strSQL += "FROM   tbm_polret ";
         strSQL += "WHERE  st_reg = 'A' and tx_codsri is not null and fe_vighas is null ";
         strSQL += "       and co_emp = " + objZafParSis.getCodigoEmpresa();
         strSQL += "       and co_tipret = " + intCodTipRet;
         
         if (strIsItmFacSrvTra.equals("S"))
         {  //Si strIsItmFacSrvTra = "S", significa que el item de la factura que aparece en el JTable es un Servicio de transporte.
            strSQL += " and co_mottra = 8"; //8 = Servicio de transporte, especificado en la tabla tbm_motdoc.
         }
         else
         {  //Caso contrario, el item de la factura que aparece en el JTable no es un Servicio de transporte.
            strSQL += " and co_mottra <> 8";
         }
         
         rst = stm.executeQuery(strSQL);
         
         if (rst.next())
         {  strRes = rst.getString("tx_codsri") == null? "" :rst.getString("tx_codsri");
         }
         
         rst.close();
         rst = null;
         stm.close();
         stm = null;
      } //try
      
      catch(Exception e)
      {  
         strRes = "";
      }
      
      return strRes;
   } //Funcion getCodSri()
   
   private int getCodTipRet(BigDecimal bdePorRet)
   {
      int intRes = 0, intCodTipRet;
      
      try
      {  intCodTipRet = 0;
         
         if (bdePorRet.equals(new BigDecimal("30.00")))
            intCodTipRet = 4; //Retención al IVA 30%
         if (bdePorRet.equals(new BigDecimal("50.00")))
            intCodTipRet = 16; //Retención al IVA 50%
         else if (bdePorRet.equals(new BigDecimal("70.00")))
            intCodTipRet = 5; //Retención al IVA 70%
         else if (bdePorRet.equals(new BigDecimal("100.00")))
            intCodTipRet = 6; //Retención al IVA 100%
         else if (bdePorRet.equals(new BigDecimal("10.00")))
            intCodTipRet = 14; //Retención al IVA 10%
         else if (bdePorRet.equals(new BigDecimal("20.00")))
            intCodTipRet = 15; //Retención al IVA 20%
         else if (bdePorRet.equals(new BigDecimal("1.00")))
            intCodTipRet = 1; //Retención en la fuente 1%
         else if (bdePorRet.equals(new BigDecimal("2.00")))
            intCodTipRet = 8; //Retención en la fuente 2%
         else if (bdePorRet.equals(new BigDecimal("8.00")))
            intCodTipRet = 3; //Retención en la fuente 8%
         
         intRes = intCodTipRet;
      } //try
      
      catch(Exception e)
      {  
         intRes = 0;
      }
      
      return intRes;
   } //Funcion getCodTipRet()
   
   private String isItemFacturaServicioTransporte(String strCodLocTbl, String strNumDocTbl, BigDecimal bdePorRetTbl, BigDecimal bdeValRetTbl)
   {
      String strRes = "N", strSQL;
      BigDecimal bdeAux, bdeValRetCal;
      java.sql.Statement stm;
      java.sql.ResultSet rst;
      
      try
      {
         stm = CONN_GLO.createStatement();
         bdeAux = bdePorRetTbl.divide(new BigDecimal(100));
         strSQL =  "SELECT b1.*, (round(b1.nd_tot * " + bdeAux + ", 2)) as nd_ValRetCal " + "\n";
         strSQL += "FROM ( SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.ne_numdoc, a3.st_ser, sum(a1.nd_tot) as nd_tot " + "\n";
         strSQL += "       FROM tbm_detmovinv as a1 " + "\n";
         strSQL += "       INNER JOIN tbm_cabmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc and a2.co_doc = a1.co_doc " + "\n";
         strSQL += "       INNER JOIN tbm_inv as a3 on a1.co_emp = a3.co_emp and a1.co_itm = a3.co_itm " + "\n";
         strSQL += "       WHERE a2.st_reg = 'A' and a1.co_emp = " + objZafParSis.getCodigoEmpresa() + "\n";
         strSQL += "          and a1.co_loc = " + strCodLocTbl + "\n";
         strSQL += "          and a1.co_tipdoc = 228" + "\n"; //228 = FACVENE
         strSQL += "          and a2.ne_numdoc = " + strNumDocTbl + "\n";
         strSQL += "       GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.ne_numdoc, a3.st_ser " + "\n";
         strSQL += "     ) as b1";
         
         
         rst = stm.executeQuery(strSQL);
         
         while (rst.next())
         {  bdeValRetCal = rst.getBigDecimal("nd_ValRetCal");
            if (rst.getString("st_ser").equals("T") && bdeValRetCal.equals(bdeValRetTbl))
            {  //T = El item de la factura es de transporte.
               strRes = "S";
               break;
            }
         }
         
         rst.close();
         rst = null;
         stm.close();
         stm = null;
      } //try
      
      catch(Exception e)
      {  
         strRes = "N";
      }
      
      return strRes;
   } //Funcion isItemFacturaServicioTransporte()
   
   private boolean RetSri()
   {
      boolean blnRes = true;
      Statement stmLoc;
      ResultSet rstLoc;
      int i, j, intCodUsr;
      String strAux, strSQL, strIdeEmi, strTipDocEle, strNumDoc, strFecEmi, strFecAut, strTipEmi, strIdeRec, strClvAcc, strNumAut, strDD, strMM, strAAAA;
      String strHorMinSeg;
      StringTokenizer strTokCad;
      Date datFecAux;
      
      try
      {  blnBtnCanCarArcRet = false;
         if (CONN_GLO != null)
         {  stmLoc = CONN_GLO.createStatement();
            strSQL = "select count(1) from tbm_emp where 1 = 0";
            rstLoc = stmLoc.executeQuery(strSQL); //Se pone esta sentencia SQL para que Java no de error en la linea de abajo donde aparece "rstLoc.close()"
            String cadena, archivo;
            
            archivo = "";
            JFileChooser objFilCho = new JFileChooser();
            objFilCho.setDialogTitle("Abrir");
            objFilCho.setFileSelectionMode(JFileChooser.FILES_ONLY);
            objFilCho.setCurrentDirectory(new File("C:\\"));
            FileNameExtensionFilter objFilNamExt = new FileNameExtensionFilter("Archivos de texto", "txt");
            objFilCho.setFileFilter(objFilNamExt);
            
            if (objFilCho.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                archivo = objFilCho.getSelectedFile().getPath();
            }
            
            if (archivo.equals(""))
            {  //Si el usuario no selecciono ningun archivo, se debe abandonar el proceso
               blnBtnCanCarArcRet = true;
               return false;
            }
            
            FileReader f = new FileReader(archivo);
            BufferedReader b = new BufferedReader(f);
            intCodUsr = objZafParSis.getCodigoUsuario();
            i = 0;
            
            while ((cadena = b.readLine()) != null)
            {  i++;
               int intRes = i % 2;
               //Del archivo leido solo se tomaran en cuenta las lineas impares a partir de la linea 3
               if (intRes != 0 && i >= 3)
               {  strIdeEmi = "";
                  strTipDocEle = "";
                  strNumDoc = "";
                  strFecEmi = "";
                  strFecAut = "";
                  strTipEmi = "";
                  strIdeRec = "";
                  strClvAcc = "";
                  strNumAut = "";
                  strTokCad = new StringTokenizer(cadena, "\t");
                  j = 0;
                  
                  while (strTokCad.hasMoreElements())
                  {  j++;
                     strAux = strTokCad.nextToken().toString();
                     
                     switch (j)
                     {  case 1:
                           if (strAux.toUpperCase().lastIndexOf("COMPROBANTE") != -1)
                           {  
                              strTipDocEle = "R"; //Comprobante de retencion
                           }
                           break;
                        
                        case 2:
                           strNumDoc = strAux;
                           break;
                                   
                        case 3:
                           strIdeEmi = strAux;
                           break;
                        
                        case 5:
                           datFecAux = objUti.parseDate(strAux, "dd/MM/yyyy");
                           strFecEmi = objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
                           break;
                        
                        case 6:
                           strDD = strAux.substring(0, 2);
                           strMM = strAux.substring(3, 5);
                           strAAAA = strAux.substring(6, 10);
                           strHorMinSeg = strAux.substring(11, 19);
                           strFecAut = strAAAA + "-" + strMM + "-" + strDD + " " + strHorMinSeg;
                           break;
                           
                        case 7:
                           if (strAux.toUpperCase().lastIndexOf("NORMAL") != -1)
                           {   strTipEmi = "N"; } //Normal
                           else
                           {   strTipEmi = "C"; } //Contingencia
                           break;
                           
                        case 9:
                           strIdeRec = strAux;
                           break;
                           
                        case 10:
                           strClvAcc = strAux;
                           break;
                           
                        case 11:
                           strNumAut = strAux;
                           break;
                     } //switch (j)
                  } //while (strTokCad.hasMoreElements())
                  
                  //Se va a verificar si el registro leido en el archivo texto ya se encuentra ingresado en tbm_cabDocEleSri
                  strSQL =  "SELECT count(1) as cont_reg ";
                  strSQL += "FROM tbm_cabDocEleSri ";
                  strSQL += "WHERE tx_ideemi = '" + strIdeEmi + "'";
                  strSQL += "      and tx_tipdoc = '" + strTipDocEle + "'";
                  strSQL += "      and tx_numdoc = '" + strNumDoc + "'";
                  rstLoc = stmLoc.executeQuery(strSQL);
                  
                  if (rstLoc.next())
                  {  if (rstLoc.getInt("cont_reg") == 0)
                     {  //El registro leido en el archivo texto no se encuentra ingresado en tbm_cabDocEleSri
                        datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                        strAux = objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
                        strSQL = "INSERT INTO tbm_cabDocEleSri ";
                        strSQL += "(tx_ideemi, tx_tipdoc, tx_numdoc, fe_emi, fe_aut, tx_tipemi, tx_iderec, tx_claacc, tx_numaut, st_asidocrel, ";
                        strSQL += "st_reg, fe_ing, co_usring) ";
                        strSQL += "VALUES (";
                        strSQL += "'" + strIdeEmi + "', "; //tx_ideemi
                        strSQL += "'" + strTipDocEle + "', "; //tx_tipdoc
                        strSQL += "'" + strNumDoc + "', "; //tx_numdoc
                        strSQL += "'" + strFecEmi + "', "; //fe_emi
                        strSQL += "'" + strFecAut + "', "; //fe_aut
                        strSQL += "'" + strTipEmi + "', "; //tx_tipemi
                        strSQL += "'" + strIdeRec + "', "; //tx_iderec
                        strSQL += "'" + strClvAcc + "', "; //tx_claacc
                        strSQL += "'" + strNumAut + "', "; //tx_numaut
                        strSQL += "'N', "; //st_asidocrel
                        strSQL += "'A', "; //st_reg
                        strSQL += "'" + strAux + "', "; //fe_ing
                        strSQL += intCodUsr; //co_usring
                        strSQL += ")";
                        stmLoc.executeUpdate(strSQL);
                     }
                  }
               } //if (intRes != 0 && i >= 3)
            } //while ((cadena = b.readLine()) != null)
            b.close();
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
         } //if (con != null)
      } //try
      
      catch(Exception e)
      {  blnRes = false;
         objUti.mostrarMsgErr_F1(this, e);
      }
         
      return blnRes;
   } //Funcion RetSri()
   
   private boolean verificaPorcentajeRetencionExisteTabla(javax.swing.JTable tblDat, int intNumFilSel)
   {
      boolean blnRes = false;
      String strAux, strCodCliTbl, strNumDocTbl, strNumDocVenCon, strCodCliVenCon;
      BigDecimal bdePorRetTbl, bdeValRetTbl, bdePorRetVenCon;
      
      try
      {  strCodCliVenCon = tblDat.getValueAt(intNumFilSel, INT_TBL_CODCLI) == null? "" :tblDat.getValueAt(intNumFilSel, INT_TBL_CODCLI).toString();
         strNumDocVenCon = tblDat.getValueAt(intNumFilSel, INT_TBL_NUMDOC) == null? "" :tblDat.getValueAt(intNumFilSel, INT_TBL_NUMDOC).toString();

         strAux = tblDat.getValueAt(intNumFilSel, INT_TBL_PORRET) == null? "" :tblDat.getValueAt(intNumFilSel, INT_TBL_PORRET).toString();

         if (strAux.equals(""))
         {  bdePorRetVenCon = new BigDecimal("0.00");
         }
         else
         {  bdePorRetVenCon = new BigDecimal(strAux);
            bdePorRetVenCon = objUti.redondearBigDecimal(bdePorRetVenCon, objZafParSis.getDecimalesMostrar());
         }
               
         for (int i = 0; i < tblDat.getRowCount(); i++)
         {  if ( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null? "": (tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")? "" :tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) )
            {  if (i != intNumFilSel)
               {  strCodCliTbl = tblDat.getValueAt(i, INT_TBL_CODCLI) == null? "" :tblDat.getValueAt(i, INT_TBL_CODCLI).toString();
                  strNumDocTbl = tblDat.getValueAt(i, INT_TBL_NUMDOC) == null? "" :tblDat.getValueAt(i, INT_TBL_NUMDOC).toString();

                  strAux = tblDat.getValueAt(i, INT_TBL_PORRET) == null? "" :tblDat.getValueAt(i, INT_TBL_PORRET).toString();

                  if (strAux.equals(""))
                  {  bdePorRetTbl = new BigDecimal("0.00");
                  }
                  else
                  {  bdePorRetTbl = new BigDecimal(strAux);
                     bdePorRetTbl = objUti.redondearBigDecimal(bdePorRetTbl, objZafParSis.getDecimalesMostrar());
                  }

                  strAux = tblDat.getValueAt(i, INT_TBL_VALDOC) == null? "" :tblDat.getValueAt(i, INT_TBL_VALDOC).toString();

                  if (strAux.equals(""))
                  {  bdeValRetTbl = new BigDecimal("0.00");
                  }
                  else
                  {  bdeValRetTbl = new BigDecimal(strAux);
                     bdeValRetTbl = objUti.redondearBigDecimal(bdeValRetTbl, objZafParSis.getDecimalesMostrar());
                  }

                  if (strCodCliTbl.equals(strCodCliVenCon) && strNumDocTbl.equals(strNumDocVenCon) && bdePorRetTbl.equals(bdePorRetVenCon))
                  {  strAux =  "<HTML>En el detalle se está intentando ingresar un valor de retención que ya existe.<BR>";
                     strAux += "Esto ocurrió para la siguiente línea de detalle:<BR><BR>";
                     strAux += "Nom. cliente: " + tblDat.getValueAt(i, INT_TBL_NOMCLI).toString() + "<BR>";
                     strAux += "Num. documento: " + tblDat.getValueAt(i, INT_TBL_NUMDOC).toString() + "<BR>";
                     strAux += "Porcentaje de retención: " + bdePorRetTbl + "<BR>";
                     strAux += "Valor de retención: " + bdeValRetTbl + "<BR><BR>";
                     strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                     mostrarMsgInf(strAux);
                     objTblMod.removeRow(intNumFilSel);
                     blnRes = true;
                     return blnRes;
                  }
               } //if (i != intNumFilSel)
            } //if ( !((tblDat.getValueAt(i, INT_TBL_CODCLI)==null? "": (tblDat.getValueAt(i, INT_TBL_CODCLI).toString().equals("")? "" :tblDat.getValueAt(i, INT_TBL_CODCLI).toString())).equals("")) )
         } //for (int i = 0; i < tblDat.getRowCount(); i++)
      } //try
      
      catch(Exception e)
      {  
         blnRes = false;
      }
      
      return blnRes;
   } //Funcion verificaPorcentajeRetencionExisteTabla()
   
   /**
   * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
   * para mostrar al usuario un mensaje que indique el campo que es invalido y que
   * debe llenar o corregir.
   */
   private void mostrarMsgInf(String strMsg)
   {
       javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
       String strTit;
       strTit="Mensaje del sistema Zafiro";
       oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
   }
}