/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCxC35.java
 *
 * Created on Jun 3, 2010, 4:11:19 PM
 */
  
package CxC.ZafCxC35;
    
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil; /**/
import java.util.Vector; /**/
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafRptSis.ZafRptSis;
/**
 *
 * @author jayapata
 */
public class ZafCxC35 extends javax.swing.JInternalFrame {

  Librerias.ZafParSis.ZafParSis objZafParSis;
  ZafUtil objUti; /**/
  private Librerias.ZafDate.ZafDatePicker txtFecDoc;
  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod; /**/
  private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
  private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;
  private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
  private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
  private Librerias.ZafAsiDia.ZafAsiDia objAsiDia; // Asiento de Diario
  private Librerias.ZafAjuCenAut.ZafAjuCenAut objAjuCenAut;
  private java.util.Date datFecAux;
  private ZafThreadGUI objThrGUI;
  private ZafRptSis objRptSis;

  ZafVenCon objVenConTipdoc;
  ZafVenCon objVenConTipdocCon;

  mitoolbar objTooBar;
  
  java.sql.Connection CONN_GLO=null;
  java.sql.Statement  STM_GLO=null;
  java.sql.ResultSet  rstCab=null;
    
  String strVersion=" Ver 0.8 ";
  String strCodTipDoc="";
  String strDesCodTipDoc="";
  String strDesLarTipDoc="";
  String strCodTipDocCon="";
  String strDesCorTipDocCon="";
  String strDesLarTipDocCon="";
  String strSqlTipDocAux="";
  String strNumDocAnu="";
  String strPreTipDocCon="";
  String strFecPreBusDocCon="";
   
  final int INT_TBL_LINEA=0;  // NUMERO DE LINEAS
  final int INT_TBL_CHKSEL=1; // SELECCION  DE FILA
  final int INT_TBL_CODEMP=2; // CODIGO EMPRESA
  final int INT_TBL_CODLOC=3; // CODIGO DEL LOCAL
  final int INT_TBL_CODTID=4; // CODIGO TIPO DE DOCUMENTO
  final int INT_TBL_CODDOC=5; // CODIGO DOCUMENTAL
  final int INT_TBL_CODREG=6; // CODIGO REGISTRO
  final int INT_TBL_CODCLI=7; // CODIGO DEL CLIENTE
  final int INT_TBL_NOMCLI=8;  // NOMBRE DEL CLIENTE
  final int INT_TBL_CODBAN=9;  // CODIGO DEL BANCO
  final int INT_TBL_DSCBAN=10;  // DESCRIPCION CORTA DEL BANCO
  final int INT_TBL_NOMBAN=11;  // NOMBRE DEL BANCO
  final int INT_TBL_NUMCTA=12;  // NUMERO DE CUENTA DEL BANCO
  final int INT_TBL_NUMCHQ=13;  // NUMERO DEL CHEQUE
  final int INT_TBL_FECVEN=14;  // FECHA DE VENCIMIENTO
  final int INT_TBL_VALCHQ=15;  // VALOR DEL CHEQUE
  final int INT_TBL_BUTFACASI=16; // BUTON PARA VER FACTURAS QUE APLICAN EL CHEQUE
  final int INT_TBL_VALPENASI=17; // VALOR PENDIEN DE ASIGNAR

  
  Vector vecCab=new Vector();    //Almacena las cabeceras  /**/
  javax.swing.JTextField txtCodTipDoc= new javax.swing.JTextField();
  javax.swing.JTextField txtCodTipDocCon= new javax.swing.JTextField();

  boolean blnHayCam=false;
  private boolean blnMarTodCanTblDat=true;


  StringBuffer strSqlInsDet;
  int intRegStbSqlInsDet=0;

    String strCodEmp="";
    String strCodLoc="";
    String strCodTip="";
    String strCodCliBus="";
    String strCodBan="";
    String strNumCta="";
    String strNumChqBus="";
    String strMonChqBus="";
    boolean blnEstCar=false;


  
    /** Creates new form ZafCxC35 */
    public ZafCxC35(Librerias.ZafParSis.ZafParSis obj) {
          try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();

            this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
            lblTit.setText(objZafParSis.getNombreMenu());  /**/

	     objUti = new ZafUtil(); /**/
	     objTooBar = new mitoolbar(this);
	     this.getContentPane().add(objTooBar,"South");
             
             objAjuCenAut = new  Librerias.ZafAjuCenAut.ZafAjuCenAut(this, objZafParSis);

             objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

             objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia(this.objZafParSis);
             objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
             public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                 if (txtCodTipDoc.getText().equals(""))
                     objAsiDia.setCodigoTipoDocumento(-1);
                 else
                    objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
              }
            });

            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);


         
             txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
             txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
             txtFecDoc.setText("");
             panCabCob.add(txtFecDoc);
             txtFecDoc.setBounds(580, 8, 92, 20);

	 }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }



       public ZafCxC35(Librerias.ZafParSis.ZafParSis obj, Integer intCodEmp, Integer intCodLoc, Integer intCodTipDoc, String strCodCliRec, String strCodBanRec, String strNumChqRec , String strNumCtaRec, String strMonChqRec, int intCodMnu){
        try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();

            this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
            lblTit.setText(objZafParSis.getNombreMenu());  /**/

	     objUti = new ZafUtil(); /**/
	     objTooBar = new mitoolbar(this);
	    

             objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);


             objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia(this.objZafParSis);
             objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
             public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                 if (txtCodTipDoc.getText().equals(""))
                     objAsiDia.setCodigoTipoDocumento(-1);
                 else
                    objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
              }
            });

            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);


             strCodEmp= String.valueOf( intCodEmp.intValue() );
             strCodLoc= String.valueOf( intCodLoc.intValue() );
             strCodTip= String.valueOf( intCodTipDoc.intValue() );
             strCodCliBus= strCodCliRec;
             strCodBan= strCodBanRec;
             strNumCta= strNumCtaRec;
             strNumChqBus= strNumChqRec;
             strMonChqBus=strMonChqRec;
             blnEstCar=true;

             txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
             txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
             txtFecDoc.setText("");
             panCabCob.add(txtFecDoc);
             txtFecDoc.setBounds(580, 8, 92, 20);

	 }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }




/**
 * Carga ventanas de consulta y configuracion de la tabla
 */
public void Configura_ventana_consulta(){

    configurarVenConTipDoc();
    configurarVenConTipDocCon();

    ConfigurarTabla();


     if(blnEstCar)
          cargarDatos( strCodEmp, strCodLoc, strCodTip, strCodCliBus, strCodBan, strNumCta, strNumChqBus, strMonChqBus  );

}


private boolean cargarDatos(String intCodEmp, String intCodLoc, String intCodTipDoc, String strCodCli, String strCodBan, String strNumCta, String strNumChq, String strMonChq  ){
  boolean blnRes=true;
  java.sql.Connection conn;
  try{
     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){

       cargarCabReg(conn, intCodEmp, intCodLoc, intCodTipDoc, strCodCli, strCodBan, strNumCta, strNumChq, strMonChq );
       cargarDetReg(conn, intCodEmp, intCodLoc, intCodTipDoc, strCodCli, strCodBan, strNumCta, strNumChq, strMonChq );

       seleccionReg(strCodCli);

      conn.close();
      conn=null;
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

public void seleccionReg(String strCodCli){
  String strCodRegBus="";
  try{

    for(int i=0; i<tblDat.getRowCount(); i++){

      strCodRegBus=  (tblDat.getValueAt(i, INT_TBL_CODCLI)==null?"":(tblDat.getValueAt(i, INT_TBL_CODCLI).equals("")?"":tblDat.getValueAt(i, INT_TBL_CODCLI).toString()));
      if(strCodRegBus.equals(strCodCli)){
           tblDat.changeSelection( i, 2, false, false);
      }

   }

 }catch(Exception e) {   objUti.mostrarMsgErr_F1(this,e);  }
}


private boolean cargarCabReg(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodCli, String strCodBan, String strNumCta, String strNumChq, String strMonChq  ){
    boolean blnRes=false;
    java.sql.Statement stmLoc, stmLoc01;
    java.sql.ResultSet rstLoc01, rstLoc02;
    String strSql="",strAux="";
    try{
      if(conn!=null){
        stmLoc=conn.createStatement();
        stmLoc01=conn.createStatement();

        if(strNumCta.equals("")) strAux=" and a.tx_numctachq is null ";
        else strAux=" and a.tx_numctachq = '"+strNumCta+"' ";

        strSql="SELECT a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_tipdoccon, a3.tx_descor, a3.tx_deslar  from tbm_pagmovinv as a " +
        " INNER JOIN tbm_cabmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) " +
        " inner join tbm_detpag as a2 on (a2.co_emp=a.co_emp and a2.co_locpag=a.co_loc and a2.co_tipdocpag=a.co_tipdoc and a2.co_docpag=a.co_doc and a2.co_regpag=a.co_reg) " +
        " inner join tbm_cabpag as a4 on (a4.co_emp=a2.co_emp and a4.co_loc=a2.co_loc and a4.co_tipdoc=a2.co_tipdoc and a4.co_doc=a2.co_doc )  " +
        " LEFT  JOIN tbm_cabtipdoc as a3 ON (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoccon ) " +
        " WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" and  a.co_banchq= "+strCodBan+" and a.tx_numchq= '"+strNumChq+"'  "+strAux+" " +
        " and a1.co_cli= "+strCodCli+" and a.nd_monchq="+strMonChq+"  and a4.co_mnu=1831 "+  //co_tipdoc not in (55, 80 )  " +
        " GROUP BY a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_tipdoccon, a3.tx_descor, a3.tx_deslar ";
        rstLoc01=stmLoc.executeQuery(strSql);
        if(rstLoc01.next()){

           strSql="SELECT  a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a.fe_doc, a.ne_numdoc1, a.ne_numdoc2, a.nd_mondoc, a.tx_obs1, a.tx_obs2 " +
           " ,a1.tx_descor, a1.tx_deslar, a1.tx_natDoc, a.st_reg  " +
           " FROM tbm_cabpag AS a " +
           " INNER JOIN tbm_cabtipdoc AS a1 on ( a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc ) " +
           " WHERE a.co_emp="+rstLoc01.getInt("co_emp")+" and a.co_loc="+rstLoc01.getInt("co_loc")+" " +
           " and a.co_tipdoc="+rstLoc01.getInt("co_tipdoc")+"  and a.co_Doc="+rstLoc01.getInt("co_doc")+" ";
           rstLoc02=stmLoc01.executeQuery(strSql);
           if(rstLoc02.next()){

               txtCodTipDoc.setText(rstLoc02.getString("co_tipdoc"));
               txtDesCodTitpDoc.setText(rstLoc02.getString("tx_descor"));
               txtDesLarTipDoc.setText(rstLoc02.getString("tx_deslar"));
               txtCodDoc.setText(rstLoc02.getString("co_doc"));
               txaObs1.setText(rstLoc02.getString("tx_obs1"));
               txaObs2.setText(rstLoc02.getString("tx_obs2"));
               txtNumDoc.setText( rstLoc02.getString("ne_numdoc1"));
               valDoc.setText(""+ objUti.redondear( rstLoc02.getString("nd_mondoc"), 2) );

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

               strAux=rstLoc02.getString("st_reg");
           }
           rstLoc02.close();
           rstLoc02=null;

           txtCodTipDocCon.setText(rstLoc01.getString("co_tipdoccon"));
           txtDesCorTipDocCon.setText(rstLoc01.getString("tx_descor"));
           txtDesLarTipDocCon.setText(rstLoc01.getString("tx_deslar"));

           objAsiDia.consultarDiario(rstLoc01.getInt("co_emp"), rstLoc01.getInt("co_loc"), rstLoc01.getInt("co_tipdoc"), rstLoc01.getInt("co_doc") );

        }
        rstLoc01.close();
        rstLoc01=null;

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

private boolean cargarDetReg(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodCli, String strCodBan, String strNumCta, String strNumChq, String strMonChq  ){
    boolean blnRes=false;
    java.sql.Statement stmLoc, stmLoc01;
    java.sql.ResultSet rstLoc01, rstLoc02;
    String strSql="",strAux="";
    Vector vecData;
    try{
      if(conn!=null){
        stmLoc=conn.createStatement();
        stmLoc01=conn.createStatement();

        vecData = new Vector();

        if(strNumCta.equals("")) strAux=" and a.tx_numctachq is null ";
        else strAux=" and a.tx_numctachq = '"+strNumCta+"' ";

        strSql="SELECT a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_tipdoccon, a3.tx_descor, a3.tx_deslar from tbm_pagmovinv as a " +
        " INNER JOIN tbm_cabmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) " +
        " inner join tbm_detpag as a2 on (a2.co_emp=a.co_emp and a2.co_locpag=a.co_loc and a2.co_tipdocpag=a.co_tipdoc and a2.co_docpag=a.co_doc and a2.co_regpag=a.co_reg) " +
        " inner join tbm_cabpag as a4 on (a4.co_emp=a2.co_emp and a4.co_loc=a2.co_loc and a4.co_tipdoc=a2.co_tipdoc and a4.co_doc=a2.co_doc )  " +
        " LEFT  JOIN tbm_cabtipdoc as a3 ON (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoccon ) " +
        " WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" and  a.co_banchq= "+strCodBan+" and a.tx_numchq= '"+strNumChq+"'  "+strAux+" " +
        " and a1.co_cli= "+strCodCli+" and a.nd_monchq="+strMonChq+"  and a4.co_mnu = 1831 "+  //co_tipdoc not in (55, 80 )  " +
        " GROUP BY a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_tipdoccon, a3.tx_descor, a3.tx_deslar ";
        rstLoc01=stmLoc.executeQuery(strSql);
        if(rstLoc01.next()){

           strSql="SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,  a2.co_cli, a2.tx_nomcli, a.co_banchq, a3.tx_descor,  a3.tx_deslar, " +
           " a.co_tipdoccon,  a.tx_numctachq, a.tx_numchq, a.fe_recchq, a.fe_venchq, sum(a.nd_abo) as valchq " +
           " FROM tbm_detpag as a " +
           " INNER JOIN tbm_pagmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_locpag and a1.co_tipdoc=a.co_tipdocpag and a1.co_doc=a.co_docpag and a1.co_reg=a.co_regpag) " +
           " INNER JOIN tbm_cabmovinv AS a2 ON (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc) " +
           " LEFT OUTER JOIN tbm_var AS a3 ON (a3.co_reg=a.co_banchq) " +
           " WHERE a.co_emp="+rstLoc01.getInt("co_emp")+" and a.co_loc="+rstLoc01.getInt("co_loc")+" and a.co_tipdoc="+rstLoc01.getInt("co_tipdoc")+" " +
           " and a.co_doc= "+rstLoc01.getInt("co_doc")+" " +
           " GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a2.co_cli, a2.tx_nomcli,  a3.tx_descor,  a3.tx_deslar, a.co_tipdoccon, a.co_banchq, a.tx_numctachq, a.tx_numchq, a.fe_recchq, a.fe_venchq " +
           " ORDER BY a2.tx_nomcli ";
           rstLoc02=stmLoc01.executeQuery(strSql);
           while(rstLoc02.next()){

            java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL_LINEA,"");
            vecReg.add(INT_TBL_CHKSEL, new Boolean(true) );
            vecReg.add(INT_TBL_CODEMP, rstLoc02.getString("co_emp") );
            vecReg.add(INT_TBL_CODLOC, rstLoc02.getString("co_loc") );
            vecReg.add(INT_TBL_CODTID, rstLoc02.getString("co_tipdoc") );
            vecReg.add(INT_TBL_CODDOC, rstLoc02.getString("co_doc") );
            vecReg.add(INT_TBL_CODREG, "" );
            vecReg.add(INT_TBL_CODCLI, rstLoc02.getString("co_cli") );
            vecReg.add(INT_TBL_NOMCLI, rstLoc02.getString("tx_nomcli") );
            vecReg.add(INT_TBL_CODBAN, rstLoc02.getString("co_banchq") );
            vecReg.add(INT_TBL_DSCBAN, rstLoc02.getString("tx_descor") );
            vecReg.add(INT_TBL_NOMBAN, rstLoc02.getString("tx_deslar") );
            vecReg.add(INT_TBL_NUMCTA, rstLoc02.getString("tx_numctachq") );
            vecReg.add(INT_TBL_NUMCHQ, rstLoc02.getString("tx_numchq") );
            vecReg.add(INT_TBL_FECVEN, rstLoc02.getString("fe_venchq") );
            vecReg.add(INT_TBL_VALCHQ, rstLoc02.getString("valchq") );
            vecReg.add(INT_TBL_BUTFACASI, "" );
            vecReg.add(INT_TBL_VALPENASI, "" );

            vecData.add(vecReg);

           }
           rstLoc02.close();
           rstLoc02=null;

        }
        rstLoc01.close();
        rstLoc01=null;

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
    arlCam.add("a.st_reg ");

    ArrayList arlAli=new ArrayList();
    arlAli.add("Código");
    arlAli.add("Des.Cor.");
    arlAli.add("Des.Lar.");
    arlAli.add("Predeterminado.");

    ArrayList arlAncCol=new ArrayList();
    arlAncCol.add("80");
    arlAncCol.add("90");
    arlAncCol.add("320");
    arlAncCol.add("60");

    //Armar la sentencia SQL.   a7.nd_stkTot,
    String Str_Sql="";

     if(objZafParSis.getCodigoUsuario()==1){
      Str_Sql="select * from ( Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar, b.st_reg from tbr_tipdocdetprg as b " +
      " left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
      " where   b.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
      " b.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
      " b.co_mnu = " + objZafParSis.getCodigoMenu()+" ) as a ";
     }else {
            Str_Sql ="select * from ( SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar, a1.st_reg  "+
            " FROM tbr_tipdocdetusr AS a1 inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"+
            " WHERE "+
            "  a1.co_emp=" + objZafParSis.getCodigoEmpresa()+""+
            " AND a1.co_loc=" + objZafParSis.getCodigoLocal()+""+
            " AND a1.co_mnu=" + objZafParSis.getCodigoMenu()+""+
            " AND a1.co_usr=" + objZafParSis.getCodigoUsuario()+" ) as a ";
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




private boolean ConfigurarTabla() {
 boolean blnRes=false;
 try{
     //Configurar JTable: Establecer el modelo.
        vecCab.clear();
        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_CHKSEL,"");
        vecCab.add(INT_TBL_CODEMP,"");
        vecCab.add(INT_TBL_CODLOC,"Cód.Loc");
        vecCab.add(INT_TBL_CODTID,"");
        vecCab.add(INT_TBL_CODDOC,"");
        vecCab.add(INT_TBL_CODREG,"");
        vecCab.add(INT_TBL_CODCLI,"Cód.Cli.");
        vecCab.add(INT_TBL_NOMCLI,"Cliente.");
        vecCab.add(INT_TBL_CODBAN,"Cód.Ban.");
        vecCab.add(INT_TBL_DSCBAN,"Banco.");
        vecCab.add(INT_TBL_NOMBAN,"Nom.Ban");
        vecCab.add(INT_TBL_NUMCTA,"Núm.Cta.");
        vecCab.add(INT_TBL_NUMCHQ,"Núm.Chq.");
        vecCab.add(INT_TBL_FECVEN,"Fec.Ven.");
        vecCab.add(INT_TBL_VALCHQ,"Val.Chq.");
        vecCab.add(INT_TBL_BUTFACASI,"");
        vecCab.add(INT_TBL_VALPENASI,"Val.Pen.Asi.");

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

        objTblMod.setColumnDataType(INT_TBL_VALCHQ, objTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_VALPENASI, objTblMod.INT_COL_DBL, new Integer(0), null);

        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_CHKSEL);
        vecAux.add("" + INT_TBL_BUTFACASI);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;

        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

        objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_VALCHQ).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_VALPENASI).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;

         //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
         ZafMouMotAda objMouMotAda=new ZafMouMotAda();
         tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

         //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CHKSEL).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(40);
        tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_DSCBAN).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_NOMBAN).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_NUMCTA).setPreferredWidth(77);
        tcmAux.getColumn(INT_TBL_NUMCHQ).setPreferredWidth(77);
        tcmAux.getColumn(INT_TBL_VALCHQ).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_FECVEN).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_BUTFACASI).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_VALPENASI).setPreferredWidth(60);

        /* Aqui se agrega las columnas que van
             ha hacer ocultas
         * */
        ArrayList arlColHid=new ArrayList();
        arlColHid.add(""+INT_TBL_CODBAN);
        arlColHid.add(""+INT_TBL_CODEMP);
        arlColHid.add(""+INT_TBL_CODLOC);
        arlColHid.add(""+INT_TBL_CODTID);
        arlColHid.add(""+INT_TBL_CODDOC);
        arlColHid.add(""+INT_TBL_CODREG);
        objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;

        objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_CHKSEL).setCellRenderer(objTblCelRenChk);
        objTblCelRenChk=null;


        objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_CHKSEL).setCellEditor(objTblCelEdiChk);
        objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                int intNumFil = tblDat.getSelectedRow();
              
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
              int intNumFil = tblDat.getSelectedRow();


                String strValPenAsi = (tblDat.getValueAt(intNumFil, INT_TBL_VALPENASI)==null?"0":(tblDat.getValueAt(intNumFil, INT_TBL_VALPENASI).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_VALPENASI).toString()));
                 double dblCanPenAsi = objUti.redondear( strValPenAsi , 3 );
                 if(dblCanPenAsi!=0){
                     MensajeInf("EL CHEQUE TIENE UN VALOR PENDIENTE DE ASIGNAR. \n ASIGNE EL VALOR PENDIENTE A ALGUNA FACTURA PARA PODER COBRARLO.  ");
                     tblDat.setValueAt( new Boolean(false), intNumFil, INT_TBL_CHKSEL);
                 }

              calculaTotMonChq();
              
            }
        });


        objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBL_BUTFACASI).setCellRenderer(objTblCelRenBut);
        objTblCelRenBut=null;


         new ButFacAsi(tblDat, INT_TBL_BUTFACASI);

          //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatMouseClicked(evt);
                }
            });

     new ZafTblOrd(tblDat);
     new ZafTblBus(tblDat);

     tcmAux=null;
     setEditable(true);

      blnRes=true;
   }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
    return blnRes;
}

public void setEditable(boolean editable) {
  if (editable==true){
    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

 }else{  objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI); }
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


             if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==INT_TBL_CHKSEL){
              if (blnMarTodCanTblDat){
                   //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {

                         String strValPenAsi = (tblDat.getValueAt(i, INT_TBL_VALPENASI)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALPENASI).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALPENASI).toString()));
                         double dblCanPenAsi = objUti.redondear( strValPenAsi , 3 );
                         if(dblCanPenAsi==0)
                             tblDat.setValueAt( new Boolean(true) , i, INT_TBL_CHKSEL);
                        
                    }
                    blnMarTodCanTblDat=false;
               }else{
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        tblDat.setValueAt( new Boolean(false) , i, INT_TBL_CHKSEL);

                    }
                    blnMarTodCanTblDat=true;
                }
                 calculaTotMonChq();
             }
        } catch (Exception e) {     objUti.mostrarMsgErr_F1(this, e);  }
    }





/**
 * Calcula el monto total de los cheques ingresados
 */
public void calculaTotMonChq(){
  double dblMonChq=0;
  try{
    for(int i=0; i<tblDat.getRowCount(); i++){
      if( ( (tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){
          
          dblMonChq += Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_VALCHQ)==null)?"0":(tblDat.getValueAt(i, INT_TBL_VALCHQ).toString())));

    }}
   dblMonChq=objUti.redondear(dblMonChq, 2);
   valDoc.setText(""+dblMonChq);

   objAsiDia.inicializar();
   objAsiDia.generarDiario( Integer.parseInt( txtCodTipDocCon.getText()), dblMonChq, dblMonChq);


 }catch(Exception e) {   objUti.mostrarMsgErr_F1(this,e);  }
}



 private class ButFacAsi extends Librerias.ZafTableColBut.ZafTableColBut{
    public ButFacAsi(javax.swing.JTable tbl, int intIdx){
         super(tbl,intIdx);
    }
    public void butCLick() {

     int intNumFil = tblDat.getSelectedRow();
      if(intNumFil >= 0 ) {

        listaFacAsig(intNumFil);
     }
 }}

 private void listaFacAsig(int intNumFil){
  String strCodEmp="",strCodLoc="", strCodTipDocRec="", strCodDoc="", strCodReg="";
  String strSql="";
  try{

      strCodEmp=tblDat.getValueAt(intNumFil, INT_TBL_CODEMP).toString();
      strCodLoc=tblDat.getValueAt(intNumFil, INT_TBL_CODLOC).toString();
      strCodTipDocRec=tblDat.getValueAt(intNumFil, INT_TBL_CODTID).toString();
      strCodDoc=tblDat.getValueAt(intNumFil, INT_TBL_CODDOC).toString();
      strCodReg=tblDat.getValueAt(intNumFil, INT_TBL_CODREG).toString();

      strSql="SELECT  a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a3.tx_descor, a3.tx_deslar, a2.ne_numdoc, a2.fe_doc , a1.ne_diacre, " +
      " a1.fe_ven, a1.mo_pag,   (a1.mo_pag +a1.nd_abo) as valpen,  a1.nd_monchq  " +
      " FROM tbr_detrecdocpagmovinv as a " +
      " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel) " +
      " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) " +
      "INNER JOIN tbm_cabtipdoc as a3 ON (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc ) "+
      " WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" and a.co_tipdoc="+strCodTipDocRec+"  and a.co_doc= "+strCodDoc+"   and a.co_reg= "+strCodReg+" and a.st_reg='A'  ";

      ZafCxC35_01 obj = new  ZafCxC35_01(javax.swing.JOptionPane.getFrameForComponent(this), true,  objZafParSis, strSql  );
      obj.show();
      obj.dispose();
      obj=null;

   }catch(Exception evt){ objUti.mostrarMsgErr_F1(this, evt); }

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
        txtNumDoc = new javax.swing.JTextField();
        valDoc = new javax.swing.JTextField();
        lblCodDoc1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
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

        panCabCob.setPreferredSize(new java.awt.Dimension(100, 80));
        panCabCob.setLayout(null);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel3.setText("Tipo de documento:"); // NOI18N
        panCabCob.add(jLabel3);
        jLabel3.setBounds(10, 10, 110, 20);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel2.setText("TipDoc. control :"); // NOI18N
        panCabCob.add(jLabel2);
        jLabel2.setBounds(10, 30, 90, 20);

        txtDesCorTipDocCon.setBackground(objZafParSis.getColorCamposObligatorios());
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
        txtDesCorTipDocCon.setBounds(120, 30, 70, 20);

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
        txtDesCodTitpDoc.setBounds(120, 10, 70, 20);

        txtDesLarTipDocCon.setBackground(objZafParSis.getColorCamposObligatorios());
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
        txtDesLarTipDocCon.setBounds(190, 30, 230, 20);

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
        txtDesLarTipDoc.setBounds(190, 10, 230, 20);

        butTipDoc.setText(".."); // NOI18N
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabCob.add(butTipDoc);
        butTipDoc.setBounds(420, 10, 20, 20);

        butTipDocCon.setText(".."); // NOI18N
        butTipDocCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocConActionPerformed(evt);
            }
        });
        panCabCob.add(butTipDocCon);
        butTipDocCon.setBounds(420, 30, 20, 20);

        lblCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodDoc.setText("Código del documento:"); // NOI18N
        panCabCob.add(lblCodDoc);
        lblCodDoc.setBounds(10, 50, 120, 20);

        txtCodDoc.setBackground(objZafParSis.getColorCamposSistema());
        panCabCob.add(txtCodDoc);
        txtCodDoc.setBounds(120, 50, 90, 20);

        lblNumDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNumDoc.setText("Número de documento:"); // NOI18N
        panCabCob.add(lblNumDoc);
        lblNumDoc.setBounds(460, 30, 120, 20);

        txtNumDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        panCabCob.add(txtNumDoc);
        txtNumDoc.setBounds(580, 30, 90, 20);

        valDoc.setBackground(objZafParSis.getColorCamposSistema());
        valDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        valDoc.setText("0.00");
        panCabCob.add(valDoc);
        valDoc.setBounds(580, 50, 90, 20);

        lblCodDoc1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodDoc1.setText("Valor del Documento:"); // NOI18N
        panCabCob.add(lblCodDoc1);
        lblCodDoc1.setBounds(460, 50, 120, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel6.setText("Fecha del documento:"); // NOI18N
        panCabCob.add(jLabel6);
        jLabel6.setBounds(460, 10, 120, 20);

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
                objTblMod.removeAllRows();
                valDoc.setText("0.00");
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
                objTblMod.removeAllRows();
                valDoc.setText("0.00");
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
        if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE) {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
            strCodTipDoc=objVenConTipdoc.getValueAt(1);
        }
}//GEN-LAST:event_butTipDocActionPerformed

    private void butTipDocConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocConActionPerformed
        // TODO add your handling code here:
        objVenConTipdocCon.setTitle("Listado de Tippos de docuemntos");
        objVenConTipdocCon.setCampoBusqueda(1);
        objVenConTipdocCon.show();
        if (objVenConTipdocCon.getSelectedButton()==objVenConTipdocCon.INT_BUT_ACE) {
            txtCodTipDocCon.setText(objVenConTipdocCon.getValueAt(1));
            txtDesCorTipDocCon.setText(objVenConTipdocCon.getValueAt(2));
            txtDesLarTipDocCon.setText(objVenConTipdocCon.getValueAt(3));
            strCodTipDocCon=objVenConTipdoc.getValueAt(1);
            strPreTipDocCon= objVenConTipdocCon.getValueAt(4);

            if(strPreTipDocCon.equals("S")){
                ZafCxC35_02 obj = new  ZafCxC35_02(javax.swing.JOptionPane.getFrameForComponent(this), true,  objZafParSis );
                obj.show();
                if(obj.acepta()){
                   strFecPreBusDocCon = obj.GetCamSel();
                }
                obj.dispose();
                obj=null;
            }

            cargarDat();
        }
}//GEN-LAST:event_butTipDocConActionPerformed




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




public void BuscarTipDocCon(String campo,String strBusqueda,int tipo){
  objVenConTipdocCon.setTitle("Listado de Tipo de Documentos");
  if(objVenConTipdocCon.buscar(campo, strBusqueda )) {
      txtCodTipDocCon.setText(objVenConTipdocCon.getValueAt(1));
      txtDesCorTipDocCon.setText(objVenConTipdocCon.getValueAt(2));
      txtDesLarTipDocCon.setText(objVenConTipdocCon.getValueAt(3));
      strCodTipDocCon=objVenConTipdocCon.getValueAt(1);
      strPreTipDocCon= objVenConTipdocCon.getValueAt(4);

            if(strPreTipDocCon.equals("S")){
                ZafCxC35_02 obj = new  ZafCxC35_02(javax.swing.JOptionPane.getFrameForComponent(this), true,  objZafParSis );
                obj.show();
                if(obj.acepta()){
                   strFecPreBusDocCon = obj.GetCamSel();
                }
                obj.dispose();
                obj=null;
            }

      cargarDat();

  }else{
        objVenConTipdocCon.setCampoBusqueda(tipo);
        objVenConTipdocCon.cargarDatos();
        objVenConTipdocCon.show();
        if (objVenConTipdocCon.getSelectedButton()==objVenConTipdocCon.INT_BUT_ACE) {
           txtCodTipDocCon.setText(objVenConTipdocCon.getValueAt(1));
           txtDesCorTipDocCon.setText(objVenConTipdocCon.getValueAt(2));
           txtDesLarTipDocCon.setText(objVenConTipdocCon.getValueAt(3));
           strCodTipDocCon=objVenConTipdocCon.getValueAt(1);
           strPreTipDocCon= objVenConTipdocCon.getValueAt(4);

            if(strPreTipDocCon.equals("S")){
                ZafCxC35_02 obj = new  ZafCxC35_02(javax.swing.JOptionPane.getFrameForComponent(this), true,  objZafParSis );
                obj.show();
                if(obj.acepta()){
                   strFecPreBusDocCon = obj.GetCamSel();
                }
                obj.dispose();
                obj=null;
            }

           cargarDat();
         }else{
           txtCodTipDocCon.setText(strCodTipDocCon);
           txtDesCorTipDocCon.setText(strDesCorTipDocCon);
           txtDesLarTipDocCon.setText(strDesLarTipDocCon);
  }}}





/**
 * Se encarga de cargar la informacion en la tabla
 * @return  true si esta correcto y false  si hay algun error
 */
private boolean cargarDat(){
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  double dblValChq=0;
   try{
      conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
      if(conn!=null){
           stmLoc=conn.createStatement();
           java.util.Vector vecData = new java.util.Vector();

          if(strPreTipDocCon.equals("S")){
            strSql="SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg,  a1.co_cli, a3.tx_nom, a1.co_banchq, a2.tx_descor,  " +
           " a2.tx_deslar,a1.tx_numctachq, a1.tx_numchq, a1.fe_venchq, a1.nd_monchq " +
           " FROM tbm_cabrecdoc AS a " +
           " INNER JOIN tbm_detrecdoc AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
           " INNER JOIN tbm_cli as a3 ON (a3.co_emp=a1.co_emp and a3.co_cli=a1.co_cli) " +
           " LEFT  JOIN tbm_var as a2 ON (a2.co_reg=a1.co_banchq ) " +
           " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" AND a.co_tipdoc= 94 " +
           " and a.st_reg not in ('E','I') and a1.st_reg = 'A' " +
           " AND a1.nd_valapl = 0 AND ( a1.co_tipdoccon is null  or  a1.co_tipdoccon = "+txtCodTipDocCon.getText()+" )  " +
          // " AND a1.st_asidocrel='S' " +
           " AND  CASE WHEN a1.co_tipdoccon is null then a1.fe_venchq <= "+strFecPreBusDocCon+" else a1.co_tipdoccon = "+txtCodTipDocCon.getText()+"   end " +
           " ";

          }else{

           strSql="SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg,  a1.co_cli, a3.tx_nom, a1.co_banchq, a2.tx_descor,  " +
           " a2.tx_deslar,a1.tx_numctachq, a1.tx_numchq, a1.fe_venchq, a1.nd_monchq " +
           " FROM tbm_cabrecdoc AS a " +
           " INNER JOIN tbm_detrecdoc AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
           " INNER JOIN tbm_cli as a3 ON (a3.co_emp=a1.co_emp and a3.co_cli=a1.co_cli) " +
           " LEFT  JOIN tbm_var as a2 ON (a2.co_reg=a1.co_banchq ) " +
           " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" AND a.co_tipdoc= 94 " +
           " and a.st_reg not in ('E','I') and a1.st_reg = 'A' " +
           " AND a1.nd_valapl = 0 AND a1.co_tipdoccon = "+txtCodTipDocCon.getText()+"  ";
          }


           

           strSql="select  "
           + "  ( select sum(x1.nd_monchq) from tbr_detrecdocpagmovinv as x  "
           + "    inner join tbm_pagmovinv as x1 on (x1.co_emp=x.co_emprel and x1.co_loc=x.co_locrel and x1.co_tipdoc=x.co_tipdocrel and x1.co_doc=x.co_docrel and x1.co_reg=x.co_regrel)  "
           + "    where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc and x.co_reg=a.co_reg and x.st_reg='A' "
           + "  ) as totvalasi "
           + " ,* from ( "+strSql+" ) as a ";


           strSql="select  ( nd_monchq - case when totvalasi is null then 0 else totvalasi end ) as valpenasifac  "
           + " ,* from ( "+strSql+" ) as x ";

         //  System.out.println("--> "+ strSql );

           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){

               java.util.Vector vecReg = new java.util.Vector();
                vecReg.add(INT_TBL_LINEA, "");
                vecReg.add(INT_TBL_CHKSEL, new Boolean(  (rstLoc.getDouble("valpenasifac")==0?true:false) )  );
                vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp") );
                vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
                vecReg.add(INT_TBL_CODTID, rstLoc.getString("co_tipdoc") );
                vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
                vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg") );
                vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
                vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nom") );
                vecReg.add(INT_TBL_CODBAN, rstLoc.getString("co_banchq") );
                vecReg.add(INT_TBL_DSCBAN, rstLoc.getString("tx_descor") );
                vecReg.add(INT_TBL_NOMBAN, rstLoc.getString("tx_deslar") );
                vecReg.add(INT_TBL_NUMCTA, rstLoc.getString("tx_numctachq") );
                vecReg.add(INT_TBL_NUMCHQ, rstLoc.getString("tx_numchq") );
                vecReg.add(INT_TBL_FECVEN, rstLoc.getString("fe_venchq") );
                vecReg.add(INT_TBL_VALCHQ, rstLoc.getString("nd_monchq") );
                vecReg.add(INT_TBL_BUTFACASI, "" );
                vecReg.add(INT_TBL_VALPENASI, rstLoc.getString("valpenasifac") );


               vecData.add(vecReg);
               
               if(rstLoc.getDouble("valpenasifac")==0)
                  dblValChq+=rstLoc.getDouble("nd_monchq");

           }
           rstLoc.close();
           rstLoc=null;

           objTblMod.setData(vecData);
           tblDat .setModel(objTblMod);

           dblValChq=objUti.redondear(dblValChq, 2);
           valDoc.setText(""+dblValChq);

           objAsiDia.inicializar();
           objAsiDia.generarDiario( Integer.parseInt( txtCodTipDocCon.getText()), dblValChq, dblValChq);


      stmLoc.close();
      stmLoc=null;
      conn.close();
      conn=null;

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    System.gc();
    return blnRes;
}




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
            dispose();
        }

    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton butTipDocCon;
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
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtDesCodTitpDoc;
    private javax.swing.JTextField txtDesCorTipDocCon;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDesLarTipDocCon;
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
               " " +
               " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
               " AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" ; " +
               " "+
               " UPDATE tbm_pagmovinv SET nd_abo=nd_abo- x.ndabo, st_regrep='M' FROM ( " +
               "    select co_emp, co_locpag, co_tipdocpag, co_docpag, co_regpag, nd_abo as ndabo  from tbm_detpag as a " +
               " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a.co_tipdoc="+intCodTipDoc+" " +
               " and a.co_doc="+intCodDoc+" and a.st_reg='A' " +
               " ) AS x  WHERE " +
               " tbm_pagmovinv.co_emp=x.co_emp and tbm_pagmovinv.co_loc=x.co_locpag and tbm_pagmovinv.co_tipdoc=x.co_tipdocpag and tbm_pagmovinv.co_doc=x.co_docpag " +
               " and tbm_pagmovinv.co_reg=x.co_regpag ; " +
               " " +
               " UPDATE tbm_detrecdoc SET nd_valapl=0, st_regrep='M' FROM ( " +
               "    select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg  from tbm_detpag as a " +
               "    inner join tbr_detrecdocpagmovinv as a1 on (a1.co_emprel=a.co_emp and a1.co_locrel=a.co_locpag and a1.co_tipdocrel=a.co_tipdocpag and a1.co_docrel=a.co_docpag " +
               "    and a1.co_regrel=a.co_regpag ) " +
               "    where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a.co_tipdoc="+intCodTipDoc+" " +
               "    and a.co_doc="+intCodDoc+" and a.st_reg='A'  and a1.st_reg='A'  " +
               "    GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg " +
               " ) AS x  WHERE " +
               " tbm_detrecdoc.co_emp=x.co_emp and tbm_detrecdoc.co_loc=x.co_loc and tbm_detrecdoc.co_tipdoc=x.co_tipdoc and tbm_detrecdoc.co_doc=x.co_doc " +
               " and tbm_detrecdoc.co_reg=x.co_reg ; ";

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

            noEditable(false);

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
    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
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


//  if(txtDesLarga.getText().trim().equals("") ){
//    tabGen.setSelectedIndex(0);
//    MensajeInf("El campo << Descripción larga >> es obligatorio.\nEscoja y vuelva a intentarlo.");
//    txtDesLarga.setText(txtDesLarga.getText().trim());
//    txtDesLarga.requestFocus();
//    return false;
//    }


   if(txtNumDoc.equals("") ){
    tabGen.setSelectedIndex(0);
    MensajeInf("El campo << Número de Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtNumDoc.requestFocus();
    return false;
    }



   return true;
 }







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
        intRegStbSqlInsDet=0;
        
        if(insertarCab(conn, intCodDoc)){
          if(insertarDet(conn, intCodDoc)){
           if(objAsiDia.insertarDiario(conn, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), String.valueOf(intCodDoc), txtNumDoc.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy") )) {
            if(objAjuCenAut.realizaAjuCenAut(conn, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), intCodDoc,  80,  objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy") )) {
              conn.commit();
              txtCodDoc.setText(""+intCodDoc);
              blnRes=true;
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
                     "and co_tipdoc="+txtCodTipDoc.getText()+" and ne_numdoc1="+txtNumDoc.getText();
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
        " ,'"+strFecDoc+"', "+txtNumDoc.getText()+", "+txtNumDoc.getText()+", '"+txaObs1.getText()+"', '"+txaObs2.getText()+"' " +
        " ,"+(valDoc.getText().equals("")?"0":valDoc.getText())+", 'A', "+objZafParSis.getFuncionFechaHoraBaseDatos()+","+objZafParSis.getCodigoUsuario()+" " +
        " ,'"+objZafParSis.getNombreComputadoraConDirIP()+"', "+objZafParSis.getCodigoMenu()+", 'I' ) ; ";

//        strSql+=" UPDATE tbm_cabtipdoc SET ne_ultdoc="+txtNumDoc.getText()+" WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
//        " AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+txtCodTipDoc.getText();

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
 String strCodEmp="",strCodLoc="", strCodTipDocRec="", strCodDoc="", strCodReg="";
 String strSql="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        strNumDocAnu="";

     for(int i=0; i<tblDat.getRowCount(); i++){
      if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){

          strCodEmp=tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
          strCodLoc=tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
          strCodTipDocRec=tblDat.getValueAt(i, INT_TBL_CODTID).toString();
          strCodDoc=tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
          strCodReg=tblDat.getValueAt(i, INT_TBL_CODREG).toString();

       if(asignarTiPDocCon(conn, strCodEmp, strCodLoc, strCodTipDocRec, strCodDoc, strCodReg)){
         if(_getVerificaEstRegCabMovInv(conn, strCodEmp, strCodLoc, strCodTipDocRec, strCodDoc, strCodReg  )){
          if(_getVerificaEstRegPagMovInv(conn, strCodEmp, strCodLoc, strCodTipDocRec, strCodDoc, strCodReg  )){
           if(_realizaCobro(conn, strCodEmp, strCodLoc, strCodTipDocRec, strCodDoc, strCodReg  )){
            if(_agregarSqlDetIns(conn, intCodDoc, strCodEmp, strCodLoc, strCodTipDocRec, strCodDoc, strCodReg  )){

                blnRes=true;
                
                strSql +=" UPDATE tbm_detrecdoc SET nd_valapl= nd_monchq, st_regrep='M' WHERE co_emp="+strCodEmp+" AND co_loc="+strCodLoc+" AND " +
                " co_tipdoc="+strCodTipDocRec+" AND co_doc="+strCodDoc+" AND co_reg="+strCodReg+"  ;  ";

            }else{ blnRes=false;  break;   }
           }else{ blnRes=false;  break;   }
          }else{
             blnRes=false;
             MensajeInf(" ESTA APUNTADO A UN REGISTRO ANULADO EN PAGO MOVINV ");
             break;
          }
        }else{ blnRes=false; MensajeInf(" ESTA APUNTADO A UN DOCUMENTO ANULADO  # "+strNumDocAnu );  break;   }
      }
    }}

      if(blnRes){


           strSql +=" ; INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag," +
           " co_docpag, co_regpag, nd_abo, co_tipdoccon, co_banchq, tx_numctachq, tx_numchq, fe_recchq, fe_venchq, st_regrep )" +
           " "+strSqlInsDet.toString()+" ";
           
           System.out.println("--> "+ strSql  );

           stmLoc.executeUpdate(strSql);
      }

      stmLoc.close();
      stmLoc=null;
    
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}





/**
 * Permite cargar datos para la insertar el detalle
 * @param conn Coneccion de la base
 * @param intCodDoc  codigo del documeto de detpag
 * @param strCodEmp  codigo empresa
 * @param strCodLoc  codigo local
 * @param strCodTipDoc  codigo tipo documento
 * @param strCodDoc    codigo documento
 * @param strCodReg    codigo registro
 * @return  true si todo esta bien  false si hay algun problema
 */
private boolean _agregarSqlDetIns(java.sql.Connection conn, int intCodDoc, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodReg ){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        strSql=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg , a1.nd_monchq, a2.co_tipdoccon, a2.co_banchq, a2. tx_numctachq, a2.tx_numchq, a2.fe_recchq, a2.fe_venchq FROM " +
        " tbr_detrecdocpagmovinv as a " +
        " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel) " +
        " INNER JOIN tbm_detrecdoc as a2 ON (a2.co_emp=a.co_emp and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc and a2.co_reg=a.co_reg )         "+
        " WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" and a.co_tipdoc="+strCodTipDoc+"  and a.co_doc= "+strCodDoc+"  " +
        " and a.co_reg= "+strCodReg+" and a.st_reg='A'  ";
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){
            
            intRegStbSqlInsDet++;

            if(intRegStbSqlInsDet > 1)  strSqlInsDet.append(" UNION ALL ");
            strSqlInsDet.append("SELECT  "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+", " +
            " "+intRegStbSqlInsDet+", "+rstLoc.getInt("co_loc")+", "+rstLoc.getInt("co_tipdoc")+", "+rstLoc.getInt("co_doc")+", " +
            " "+rstLoc.getInt("co_reg")+", "+rstLoc.getString("nd_monchq")+", "+rstLoc.getInt("co_tipdoccon")+", "+rstLoc.getInt("co_banchq")+", " +
            " '"+rstLoc.getString("tx_numctachq")+"',  '"+rstLoc.getString("tx_numchq")+"', date('"+rstLoc.getString("fe_recchq")+"') , " +
            " date('"+rstLoc.getString("fe_venchq")+"') , 'I' " );
              
        }
        rstLoc.close();
        rstLoc=null;

        stmLoc.close();
        stmLoc=null;
    blnRes=true;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


/**
 * Permite anular el cobro a las facturas que aplican el cheque
 * @param conn Coneccion de la base
 * @param strCodEmp  codigo empresa
 * @param strCodLoc  codigo local
 * @param strCodTipDoc  codigo tipo documento
 * @param strCodDoc    codigo documento
 * @param strCodReg    codigo registro
 * @return  true si todo esta bien  false si hay algun problema
 */
private boolean _realizaAnuCobro(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodReg ){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 String strSql="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        strSql="UPDATE tbm_pagmovinv set nd_abo=nd_abo - x.nd_monchq, st_regrep='M' FROM (" +
        " SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a.st_reg, a1.nd_monchq FROM " +
        " tbr_detrecdocpagmovinv as a " +
        " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel) " +
        " WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" and a.co_tipdoc="+strCodTipDoc+"  and a.co_doc= "+strCodDoc+"  " +
        " and a.co_reg= "+strCodReg+"  and a.st_reg='A'  " +
        " ) AS x  WHERE tbm_pagmovinv.co_emp=x.co_emp and tbm_pagmovinv.co_loc=x.co_loc and tbm_pagmovinv.co_tipdoc=x.co_tipdoc and tbm_pagmovinv.co_doc=x.co_doc " +
        " and tbm_pagmovinv.co_reg=x.co_reg   ";
        stmLoc.executeUpdate(strSql);

        stmLoc.close();
        stmLoc=null;
    blnRes=true;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}



/**
 * Permite dar de baja las facturas que aplican el cheque
 * @param conn Coneccion de la base
 * @param strCodEmp  codigo empresa
 * @param strCodLoc  codigo local
 * @param strCodTipDoc  codigo tipo documento
 * @param strCodDoc    codigo documento
 * @param strCodReg    codigo registro
 * @return  true si todo esta bien  false si hay algun problema
 */
private boolean _realizaCobro(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodReg ){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 String strSql="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        strSql="UPDATE tbm_pagmovinv set nd_abo=nd_abo + x.nd_monchq, st_regrep='M' FROM (" +
        " SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a.st_reg, a1.nd_monchq FROM " +
        " tbr_detrecdocpagmovinv as a " +
        " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel) " +
        " WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" and a.co_tipdoc="+strCodTipDoc+"  and a.co_doc= "+strCodDoc+"  " +
        " and a.co_reg= "+strCodReg+" and a.st_reg='A' " +
        " ) AS x  WHERE tbm_pagmovinv.co_emp=x.co_emp and tbm_pagmovinv.co_loc=x.co_loc and tbm_pagmovinv.co_tipdoc=x.co_tipdoc and tbm_pagmovinv.co_doc=x.co_doc " +
        " and tbm_pagmovinv.co_reg=x.co_reg   ";
        stmLoc.executeUpdate(strSql);

        stmLoc.close();
        stmLoc=null;
    blnRes=true;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


/**
 * Permite saber si tiene algun estado diferente a A o C
 * @param conn Coneccion de la base
 * @param strCodEmp  codigo empresa
 * @param strCodLoc  codigo local
 * @param strCodTipDoc  codigo tipo documento
 * @param strCodDoc    codigo documento
 * @param strCodReg    codigo registro
 * @return  true si todo esta bien  false si hay algun problema o existe estados I o F
 */
private boolean _getVerificaEstRegPagMovInv(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodReg ){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement(); 

        strSql="SELECT co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel, a.st_reg FROM " +
        " tbr_detrecdocpagmovinv as a " +
        " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel) " +
        " WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" and a.co_tipdoc="+strCodTipDoc+"  and a.co_doc= "+strCodDoc+"  " +
        " and a.co_reg= "+strCodReg+" and a.st_reg='A'  and a1.st_reg IN ('F','I')  ";
        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()){
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



private boolean asignarTiPDocCon(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodReg ){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 String strSql="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();
       if(strPreTipDocCon.equals("S")){
        strSql="UPDATE tbm_detrecdoc set co_tipdoccon="+txtCodTipDocCon.getText()+" " +
        " WHERE co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+"  and co_doc= "+strCodDoc+"  " +
        " and co_reg= "+strCodReg+" ";
        stmLoc.executeUpdate(strSql);
       }
      stmLoc.close();
      stmLoc=null;

 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}



/**
 * Permite saber si esta anulado el documento que se realizara el cobro de cheque 
 * @param conn Coneccion de la base
 * @param strCodEmp  codigo empresa
 * @param strCodLoc  codigo local
 * @param strCodTipDoc  codigo tipo documento
 * @param strCodDoc    codigo documento
 * @param strCodReg    codigo registro
 * @return  true si todo esta bien  false si hay algun documento anulado o eliminado 
 */
private boolean _getVerificaEstRegCabMovInv(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodReg ){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        strSql="SELECT co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel, a.st_reg, a1.ne_numdoc  FROM " +
        " tbr_detrecdocpagmovinv as a " +
        " inner join tbm_cabmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel  ) " +
        " WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" and a.co_tipdoc="+strCodTipDoc+"  and a.co_doc= "+strCodDoc+"  " +
        " and a.co_reg= "+strCodReg+" and a.st_reg='A'  and a1.st_reg IN ('E','I')  ";
        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()){
            strNumDocAnu= rstLoc.getString("ne_numdoc");
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

       intCodDoc=Integer.parseInt(txtCodDoc.getText());
       intCodTipDoc=Integer.parseInt(txtCodTipDoc.getText());

       if(obtenerEstAnu(conn, intCodTipDoc, intCodDoc )){

       strSqlInsDet=new StringBuffer();

        if(modificarCab(conn, intCodTipDoc, intCodDoc)){
          if(modificarDet(conn, intCodTipDoc, intCodDoc)){
           if(objAsiDia.actualizarDiario(conn, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), intCodTipDoc, intCodDoc, txtCodDoc.getText(),objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), "A")){

               conn.commit();
               blnRes=true;
               cargarDet(conn, intCodTipDoc, intCodDoc);
          }else conn.rollback();
         }else conn.rollback();
        }else conn.rollback();

       strSqlInsDet=null;
      }

       conn.close();
       conn=null;
   }

  }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}








/**
 * Permite recargar los datos de la tabla despues de insertar o modificar con objetivo de tener ejem:  codigo del registro
 * que eso da cuando se insertar
 * @param conn   coneccion de la base
 * @param intCodTipDoc   codigo de tipo documento
 * @param intCodDoc   codigo del documento
 * @return  true si se consulto bien   false si hay algun error.
 */
private boolean cargarDet(java.sql.Connection conn, int intCodTipDoc, int intCodDoc ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  Vector vecData;
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();
 
       vecData = new Vector();

        strSql="SELECT x.*, a3.tx_descor as dcban, a3.tx_deslar as dlban FROM ( SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a2.co_cli, a3.tx_nom, a2.co_banchq, a4.tx_descor, " +
        " a4.tx_deslar ,a2.tx_numctachq, a2.tx_numchq, a2.fe_venchq, a2.nd_monchq, a2.co_tipdoccon " +
        " from tbm_detpag as a " +
        " LEFT JOIN tbr_detrecdocpagmovinv as a1 ON (a1.co_emprel=a.co_emp and a1.co_locrel=a.co_locpag and a1.co_tipdocrel=a.co_tipdocpag and a1.co_docrel=a.co_docpag and a1.co_regrel=a.co_regpag ) " +
        " LEFT JOIN tbm_detrecdoc as a2 ON (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc and a2.co_reg=a1.co_reg )  " +
        " INNER JOIN tbm_cli as a3 ON (a3.co_emp=a2.co_emp and a3.co_cli=a2.co_cli) " +
        " LEFT  JOIN tbm_var as a4 ON (a4.co_reg=a2.co_banchq ) " +
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" " +
        " and a.co_tipdoc="+intCodTipDoc+"  and a.co_Doc="+intCodDoc+"  and a.st_reg='A' " +
        " GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a2.co_cli, a3.tx_nom, a2.co_banchq, a4.tx_descor, " +
        " a4.tx_deslar ,a2.tx_numctachq, a2.tx_numchq, a2.fe_venchq, a2.nd_monchq, a2.co_tipdoccon "+
        " ) AS x " +
        " LEFT  JOIN tbm_cabtipdoc as a3 ON (a3.co_emp=x.co_emp and a3.co_loc=x.co_loc and a3.co_tipdoc=x.co_tipdoccon ) " +
        " ORDER BY co_reg ";
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

          java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL_LINEA,"");
            vecReg.add(INT_TBL_CHKSEL, new Boolean(true) );
            vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp") );
            vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
            vecReg.add(INT_TBL_CODTID, rstLoc.getString("co_tipdoc") );
            vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
            vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg") );
            vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
            vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nom") );
            vecReg.add(INT_TBL_CODBAN, rstLoc.getString("co_banchq") );
            vecReg.add(INT_TBL_DSCBAN, rstLoc.getString("tx_descor") );
            vecReg.add(INT_TBL_NOMBAN, rstLoc.getString("tx_deslar") );
            vecReg.add(INT_TBL_NUMCTA, rstLoc.getString("tx_numctachq") );
            vecReg.add(INT_TBL_NUMCHQ, rstLoc.getString("tx_numchq") );
            vecReg.add(INT_TBL_FECVEN, rstLoc.getString("fe_venchq") );
            vecReg.add(INT_TBL_VALCHQ, rstLoc.getString("nd_monchq") );
            vecReg.add(INT_TBL_BUTFACASI, "" );
            vecReg.add(INT_TBL_VALPENASI, "" );

            txtCodTipDocCon.setText(rstLoc.getString("co_tipdoccon"));
            txtDesCorTipDocCon.setText(rstLoc.getString("dcban"));
            txtDesLarTipDocCon.setText(rstLoc.getString("dlban"));

            vecData.add(vecReg);
        }
        objTblMod.setData(vecData);
        tblDat .setModel(objTblMod);


       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;
    blnRes=true;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
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

        strSql="UPDATE tbm_cabpag SET  fe_doc='"+strFecDoc+"', ne_numdoc1="+txtNumDoc.getText()+", ne_numdoc2="+txtNumDoc.getText()+", " +
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


private boolean modificarDet(java.sql.Connection conn, int intCodTipDoc, int intCodDoc){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strCodEmp="",strCodLoc="", strCodTipDocRec="", strCodDoc="", strCodReg="";
 String strSql="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

     for(int i=0; i<tblDat.getRowCount(); i++){
      if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("false")) ){

          strCodEmp=tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
          strCodLoc=tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
          strCodTipDocRec=tblDat.getValueAt(i, INT_TBL_CODTID).toString();
          strCodDoc=tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
          strCodReg=tblDat.getValueAt(i, INT_TBL_CODREG).toString();


          
           if(_realizaAnuCobro(conn, strCodEmp, strCodLoc, strCodTipDocRec, strCodDoc, strCodReg  )){
            if(_eliminarRegDetPag(conn, intCodDoc, strCodEmp, strCodLoc, strCodTipDocRec, strCodDoc, strCodReg  )){

                blnRes=true;

                strSql +=" UPDATE tbm_detrecdoc SET nd_valapl=0, st_regrep='M' WHERE co_emp="+strCodEmp+" AND co_loc="+strCodLoc+" AND " +
                " co_tipdoc="+strCodTipDocRec+" AND co_doc="+strCodDoc+" AND co_reg="+strCodReg+"  ;  ";

            }else{ blnRes=false;  break;   }
           }else{ blnRes=false;  break;   }
          

    }}

      if(blnRes){


           strSql +=" ; "+strSqlInsDet.toString()+" ";

           System.out.println("--> "+ strSql  );

           stmLoc.executeUpdate(strSql);
      }

      stmLoc.close();
      stmLoc=null;

 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}




/**
 * Permite cambiar de estado en tbm_detpag 'E' Eliminado en detalle
 * @param conn Coneccion de la base
 * @param intCodDoc  codigo del documeto de detpag
 * @param strCodEmp  codigo empresa
 * @param strCodLoc  codigo local
 * @param strCodTipDoc  codigo tipo documento
 * @param strCodDoc    codigo documento
 * @param strCodReg    codigo registro
 * @return  true si todo esta bien  false si hay algun problema
 */
private boolean _eliminarRegDetPag(java.sql.Connection conn, int intCodDoc, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodReg ){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

        strSql=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg , a1.nd_monchq, a2.co_tipdoccon, a2.co_banchq, a2. tx_numctachq, a2.tx_numchq, a2.fe_recchq, a2.fe_venchq FROM " +
        " tbr_detrecdocpagmovinv as a " +
        " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel) " +
        " INNER JOIN tbm_detrecdoc as a2 ON (a2.co_emp=a.co_emp and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc and a2.co_reg=a.co_reg )         "+
        " WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" and a.co_tipdoc="+strCodTipDoc+"  and a.co_doc= "+strCodDoc+"  " +
        " and a.co_reg= "+strCodReg+" and a.st_reg='A'  ";
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

            strSqlInsDet.append(" UPDATE tbm_detpag SET st_reg='E', st_regrep='M' WHERE  " +
            " co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and co_tipdoc="+txtCodTipDoc.getText()+" " +
            " and co_doc="+intCodDoc+" and co_locpag="+rstLoc.getInt("co_loc")+" and co_tipdocpag="+rstLoc.getInt("co_tipdoc")+" " +
            " and co_docpag="+rstLoc.getInt("co_doc")+" and co_regpag="+rstLoc.getInt("co_reg")+" ; ");

        }
        rstLoc.close();
        rstLoc=null;

        stmLoc.close();
        stmLoc=null;
    blnRes=true;
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

      }








 public void  clnTextos(){
    strCodTipDoc=""; strDesCodTipDoc=""; strDesLarTipDoc="";
    strCodTipDocCon=""; strDesCorTipDocCon=""; strDesLarTipDocCon="";

    txtFecDoc.setText("");

    txtCodTipDoc.setText("");
    txtDesCodTitpDoc.setText("");
    txtDesLarTipDoc.setText("");
    txtCodTipDocCon.setText("");
    txtDesCorTipDocCon.setText("");
    txtDesLarTipDocCon.setText("");

    valDoc.setText("0.00");
    txtFecDoc.setText("");
    txtNumDoc.setText("");
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
           " and st_reg not in ('E')  "+strFil;
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

       strSql="SELECT a.st_reg, a.co_tipdoc, a1.tx_descor, a1.tx_deslar, a.co_doc, a.fe_doc, a.ne_numdoc1, a.nd_mondoc, " +
       " a.tx_obs1, a.tx_obs2  from tbm_cabpag as a " +
       " INNER JOIN tbm_cabtipdoc as a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc )  " +
       " WHERE a.co_emp="+rstDatRec.getInt("co_emp")+" and a.co_loc="+rstDatRec.getInt("co_loc")+" " +
       " and a.co_tipdoc="+rstDatRec.getInt("co_tipdoc")+"  and a.co_Doc="+rstDatRec.getInt("co_doc")+" ";
       rstLoc02=stmLoc.executeQuery(strSql);
       if(rstLoc02.next()){

        txtCodTipDoc.setText( rstLoc02.getString("co_tipdoc"));
        txtDesCodTitpDoc.setText( rstLoc02.getString("tx_descor"));
        txtDesLarTipDoc.setText( rstLoc02.getString("tx_deslar"));
        txtCodDoc.setText( rstLoc02.getString("co_doc"));
        txtNumDoc.setText( rstLoc02.getString("ne_numdoc1"));
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

        objAsiDia.consultarDiario(rstDatRec.getInt("co_emp"), rstDatRec.getInt("co_loc"), rstDatRec.getInt("co_tipdoc"), rstDatRec.getInt("co_doc") );


        /**********CARGAR DATOS DE DETALLE ***************/
        vecData = new Vector();

        strSql="SELECT x.*, a3.tx_descor as dcban, a3.tx_deslar as dlban FROM ( " +
        " SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a2.co_cli, a3.tx_nom, a2.co_banchq, a4.tx_descor, " +
        " a4.tx_deslar ,a2.tx_numctachq, a2.tx_numchq, a2.fe_venchq, a2.nd_monchq, a.co_tipdoccon " +
        " from tbm_detpag as a " +
        " LEFT JOIN tbr_detrecdocpagmovinv as a1 ON (a1.co_emprel=a.co_emp and a1.co_locrel=a.co_locpag and a1.co_tipdocrel=a.co_tipdocpag and a1.co_docrel=a.co_docpag and a1.co_regrel=a.co_regpag and a1.st_reg='A' ) " +
        " LEFT JOIN tbm_detrecdoc as a2 ON (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc and a2.co_reg=a1.co_reg )  " +
        " INNER JOIN tbm_cli as a3 ON (a3.co_emp=a2.co_emp and a3.co_cli=a2.co_cli) " +
        " LEFT  JOIN tbm_var as a4 ON (a4.co_reg=a2.co_banchq ) " +
        " WHERE a.co_emp="+rstDatRec.getInt("co_emp")+" and a.co_loc="+rstDatRec.getInt("co_loc")+" " +
        " and a.co_tipdoc="+rstDatRec.getInt("co_tipdoc")+"  and a.co_Doc="+rstDatRec.getInt("co_doc")+"  and a.st_reg='A' " +
        " GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a2.co_cli, a3.tx_nom, a2.co_banchq, a4.tx_descor, " +
        " a4.tx_deslar ,a2.tx_numctachq, a2.tx_numchq, a2.fe_venchq, a2.nd_monchq, a.co_tipdoccon "+
        " ) AS x " +
        " LEFT  JOIN tbm_cabtipdoc as a3 ON (a3.co_emp=x.co_emp and a3.co_loc=x.co_loc and a3.co_tipdoc=x.co_tipdoccon ) " +
        " ORDER BY co_reg ";
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

           java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL_LINEA,"");
            vecReg.add(INT_TBL_CHKSEL, new Boolean(true) );
            vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp") );
            vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
            vecReg.add(INT_TBL_CODTID, rstLoc.getString("co_tipdoc") );
            vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
            vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg") );
            vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
            vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nom") );
            vecReg.add(INT_TBL_CODBAN, rstLoc.getString("co_banchq") );
            vecReg.add(INT_TBL_DSCBAN, rstLoc.getString("tx_descor") );
            vecReg.add(INT_TBL_NOMBAN, rstLoc.getString("tx_deslar") );
            vecReg.add(INT_TBL_NUMCTA, rstLoc.getString("tx_numctachq") );
            vecReg.add(INT_TBL_NUMCHQ, rstLoc.getString("tx_numchq") );
            vecReg.add(INT_TBL_FECVEN, rstLoc.getString("fe_venchq") );
            vecReg.add(INT_TBL_VALCHQ, rstLoc.getString("nd_monchq") );
            vecReg.add(INT_TBL_BUTFACASI, "" );
            vecReg.add(INT_TBL_VALPENASI, "" );

            txtCodTipDocCon.setText(rstLoc.getString("co_tipdoccon"));
            txtDesCorTipDocCon.setText(rstLoc.getString("dcban"));
            txtDesLarTipDocCon.setText(rstLoc.getString("dlban"));

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
    try{
    //Agregando filtro por Numero de Cotizacion


    if(!(txtCodTipDoc.getText().equals("")))
        sqlFiltro +=" and  co_tipdoc="+txtCodTipDoc.getText();
    else sqlFiltro +=" and co_tipdoc in ("+strSqlTipDocAux+") ";

    if(!(txtCodDoc.getText().equals("")))
        sqlFiltro +=" and co_doc="+txtCodDoc.getText();
   

    if(!(txtNumDoc.getText().equals("")))
       sqlFiltro += " and ne_numdoc1="+txtNumDoc.getText();

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

  

  bloquea(txtCodDoc, colBack, false);
  bloquea(txtDesCodTitpDoc, colBack, false);
  bloquea(txtDesLarTipDoc, colBack, false);
  bloquea(txtDesCorTipDocCon, colBack, false);
  bloquea(txtDesLarTipDocCon, colBack, false);
  bloquea(valDoc, colBack, false);
  bloquea(txtNumDoc, colBack, false);

  butTipDoc.setEnabled(false);
  butTipDocCon.setEnabled(false);

 blnHayCam=false;


 }catch(Exception evt){ objUti.mostrarMsgErr_F1(this, evt); }
}





        //******************************************************************************************************

      public boolean vistaPreliminar(){

             cargarRepote(1);

//            java.sql.Connection conIns;
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




        public boolean imprimir(){

               cargarRepote(0);

//            java.sql.Connection conIns;
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








private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblDat.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LINEA:
            strMsg="";
            break;
            case INT_TBL_CHKSEL:
            strMsg="Selección de cheques.";
            break;

            case INT_TBL_CODLOC:
            strMsg="Código de local.";
            break;

            case INT_TBL_CODCLI:
            strMsg="Código de cliente.";
            break;

            case INT_TBL_NOMCLI:
            strMsg="Nombre del cliente.";
            break;

            case INT_TBL_DSCBAN:
            strMsg="Banco.";
            break;

            case INT_TBL_NOMBAN:
            strMsg="Nombre del Banco.";
            break;

            case INT_TBL_NUMCTA:
            strMsg="Número de cuenta.";
            break;

            case INT_TBL_NUMCHQ:
            strMsg="Número de cheque.";
            break;

            case INT_TBL_VALCHQ:
            strMsg="valor del cheque.";
            break;

           
            case INT_TBL_FECVEN:
            strMsg="Fecha de vencimiento del cheque.";
            break;

            case INT_TBL_BUTFACASI:
            strMsg="Muesta listado de facturas que aplican al cheque.";
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




    protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }






}
