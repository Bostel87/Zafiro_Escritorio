/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
  
/*
 * ZafCxC09.java
 *
 * Created on Jun 14, 2010, 11:54:32 AM
 */
  
package CxC.ZafCxC09;
    
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil; /**/
import java.util.Vector; /**/
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;

/**
 *
 * @author jayapata
 */
public class ZafCxC09 extends javax.swing.JInternalFrame {

  Librerias.ZafParSis.ZafParSis objZafParSis;
  ZafUtil objUti; /**/
  private Librerias.ZafDate.ZafDatePicker txtFecDoc;
  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod; /**/
  private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
  private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
  private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
  private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt;
  private Librerias.ZafAsiDia.ZafAsiDia objAsiDia; // Asiento de Diario
  private ZafSelFec objSelFec;
  mitoolbar objTooBar;
  private java.util.Date datFecAux;
  private ZafTblTot objTblTot;
  
  ZafVenCon objVenConTipdoc;
  ZafVenCon objVenConCli;

  java.sql.Connection CONN_GLO=null;
  java.sql.Statement  STM_GLO=null;
  java.sql.ResultSet  rstCab=null;
  
  String strVersion=" Ver 0.3 ";
  String strCodTipDoc="";
  String strDesCodTipDoc="";
  String strDesLarTipDoc="";
  String strCodCli="";
  String strNomCli="";
  String strSqlTipDocAux="";
  
  String strCodCtaCli="";
  String strTxtCodCtaCli="";
  String strNomCtaCli="";
  String strCodCtaAju="";
  String strTxtCodCtaAju="";
  String strNomCtaAju="";
  StringBuffer strSqlInsDet;

  boolean blnHayCam=false;
  private boolean blnMarTodCanTblDat=true;

  final int INT_TBL_LINEA=0;  // NUMERO DE LINEAS
  final int INT_TBL_CHKSEL=1; // SELECCION  DE FILA
  final int INT_TBL_CODEMP=2; // CODIGO EMPRESA
  final int INT_TBL_CODLOC=3; // CODIGO DEL LOCAL
  final int INT_TBL_CODTID=4; // CODIGO TIPO DE DOCUMENTO
  final int INT_TBL_CODDOC=5; // CODIGO DOCUMENTAL
  final int INT_TBL_CODREG=6; // CODIGO REGISTRO
  final int INT_TBL_CODCLI=7; // CODIGO CLIENTE
  final int INT_TBL_NOMCLI=8; // NOMBRE CLIENTE
  final int INT_TBL_DCTIPDOC=9; // DESCRIPCION CORTA TIPO DOCUMENTO
  final int INT_TBL_DLTIPDOC=10; // DESCRIPCION LARGA TIPO DOCUMENTO
  final int INT_TBL_NUMDOC=11; // NUMERO DOCUMENTO
  final int INT_TBL_FECDOC=12; // FECHA DOCUMENTO
  final int INT_TBL_DIACRE=13; // DIA DE CREDITO
  final int INT_TBL_FECVEN=14; // FECHE VENCIMIENTO DOCUMENTO
  final int INT_TBL_PORRET=15; // PORCENTAJE DE RETENCION
  final int INT_TBL_VALDOC=16; // VALOR DOCUMENTO
  final int INT_TBL_VALPEN=17; // VALOR PENDIENTE
  final int INT_TBL_ABONO=18; //  ABONO
  final int INT_TBL_CODREGAAJU=19; // CODIGO REGISTRO DE PAGO
  final int INT_TBL_ABONOORI=20; //  ABONO ORIGEN
  
  int intMinAjuCenEnt=0;
  int intMinAjuCenDec=0;
  int intMaxAjuCenEnt=0;
  int intMaxAjuCenDec=0;

  double dblMinAjuCen=0;
  double dblMaxAjuCen=0;

  javax.swing.JTextField txtCodTipDoc= new javax.swing.JTextField();
  Vector vecCab=new Vector();    //Almacena las cabeceras  /**/
  java.util.Vector vecDetDiario;

  ZafInt txtMinAjuEnt, txtMinAjuDec ;
  ZafInt txtMaxAjuEnt, txtMaxAjuDec ;

    /** Creates new form ZafCxC09 */
    public ZafCxC09(Librerias.ZafParSis.ZafParSis obj) {
        try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();

            this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
            lblTit.setText(objZafParSis.getNombreMenu());  /**/

            
            txtMinAjuEnt= new ZafInt(10,1,1);
            jPanel7.add(txtMinAjuEnt);
            txtMinAjuEnt.setBounds(65, 25, 27, 20);
            txtMinAjuEnt.setBackground(objZafParSis.getColorCamposObligatorios());
            txtMinAjuEnt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            
            txtMinAjuDec = new ZafInt(10,1,3);
            jPanel7.add(txtMinAjuDec);
            txtMinAjuDec.setBounds(100, 25, 30, 20);
            txtMinAjuDec.setBackground(objZafParSis.getColorCamposObligatorios());


            txtMaxAjuEnt= new ZafInt(10,1,1);
            jPanel7.add(txtMaxAjuEnt);
            txtMaxAjuEnt.setBounds(65, 45, 27, 20);
            txtMaxAjuEnt.setBackground(objZafParSis.getColorCamposObligatorios());
            txtMaxAjuEnt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

            txtMaxAjuDec = new ZafInt(10,1,3);
            jPanel7.add(txtMaxAjuDec);
            txtMaxAjuDec.setBounds(100, 45, 30, 20);
            txtMaxAjuDec.setBackground(objZafParSis.getColorCamposObligatorios());

            
	     objUti = new ZafUtil(); /**/
	     objTooBar = new mitoolbar(this);
	     this.getContentPane().add(objTooBar,"South");

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

           /*
           * Aqui se verifica si estoy en linux
           */
           if(System.getProperty("os.name").equals("Linux")){
               //DIRECCION_REPORTE="//zafiro//reportes_impresos//ZafCxC35/ZafCxC35.jrxml";
           }

             txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
             txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
             txtFecDoc.setText("");
             panCabCob.add(txtFecDoc);
             txtFecDoc.setBounds(580, 8, 92, 20);


            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(false);
            panDatFil.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);

            cargarRangoAjuCenAut();

	 }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
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
 * Esta funcion permite extraer los rangos de ajuste de centavos manual
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

      strSql="SELECT abs(nd_valminajucenman) as nd_valminajucenman , nd_valmaxajucenman, trunc(abs(nd_valminajucenman)) as canentmin, round(abs((((nd_valminajucenman - trunc(nd_valminajucenman)))*1000) / 10 ) ) as candecmin " +
      " ,trunc(abs(nd_valmaxajucenman)) as canentmax, round(abs((((nd_valmaxajucenman - trunc(nd_valmaxajucenman)))*1000) /10 )) as candecmax " +
      "  FROM tbm_emp WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" ";
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
         intMinAjuCenEnt=rstLoc.getInt("canentmin");
         intMinAjuCenDec=rstLoc.getInt("candecmin");
         intMaxAjuCenEnt=rstLoc.getInt("canentmax");
         intMaxAjuCenDec=rstLoc.getInt("candecmax");
         dblMinAjuCen=objUti.redondear(rstLoc.getDouble("nd_valminajucenman"), 3);
         dblMaxAjuCen=objUti.redondear(rstLoc.getDouble("nd_valmaxajucenman"), 3);

         txtMinAjuEnt.setText(""+intMinAjuCenEnt);
         txtMinAjuDec.setText(""+ ( (intMinAjuCenDec < 10)?"0"+""+intMinAjuCenDec:""+intMinAjuCenDec) ) ; 
         txtMaxAjuEnt.setText(""+intMaxAjuCenEnt);
         txtMaxAjuDec.setText(""+ ( (intMaxAjuCenDec < 10)?"0"+""+intMaxAjuCenDec:""+intMaxAjuCenDec) ) ;
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

    configurarVenConTipDoc();
    configurarVenConClientes();

    ConfigurarTabla();

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

        if(!(objZafParSis.getCodigoUsuario()==1) ){

           if(!objUti.utilizarClientesEmpresa(objZafParSis, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), objZafParSis.getCodigoUsuario())){
              strSQL = " SELECT a.co_cli, a.tx_ide, a.tx_nom FROM tbr_cliloc as b " +
              " inner join tbm_cli as a on (a.co_emp=b.co_emp and a.co_cli=b.co_cli) " +
              " WHERE  b.co_emp ="+objZafParSis.getCodigoEmpresa()+" and b.co_loc="+objZafParSis.getCodigoLocal()+" and  a.st_reg  in ('A') ORDER BY a.tx_nom ";
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
        vecCab.add(INT_TBL_CODCLI,"Cód.Cli");
        vecCab.add(INT_TBL_NOMCLI,"Nom.CLi");
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

        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_CHKSEL);
        vecAux.add("" + INT_TBL_ABONO);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;

        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

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
        tcmAux.getColumn(INT_TBL_CHKSEL).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(40);
        tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_DCTIPDOC).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DLTIPDOC).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DIACRE).setPreferredWidth(35);
        tcmAux.getColumn(INT_TBL_FECVEN).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_PORRET).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_VALDOC).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_VALPEN).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_ABONO).setPreferredWidth(60);


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
        objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;

        objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_CHKSEL).setCellRenderer(objTblCelRenChk);
        objTblCelRenChk=null;


        objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_CHKSEL).setCellEditor(objTblCelEdiChk);
        objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
             
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
              int intNunFil = tblDat.getSelectedRow();

               if(tblDat.getValueAt(intNunFil, INT_TBL_CHKSEL).toString().equals("true")){
                
                if( tblDat.getValueAt(intNunFil, INT_TBL_CODREGAAJU)==null){
                  String strValPen = (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString()));
                  tblDat.setValueAt(strValPen, intNunFil, INT_TBL_ABONO);
                }else{
                    tblDat.setValueAt( tblDat.getValueAt(intNunFil, INT_TBL_ABONOORI), intNunFil, INT_TBL_ABONO);
                }
               }else{
                    tblDat.setValueAt("0", intNunFil, INT_TBL_ABONO);
               }

              calculaTotMon();
            }
        });


    objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
    tcmAux.getColumn(INT_TBL_ABONO).setCellEditor(objTblCelEdiTxt);
    objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
            int intNumFil = tblDat.getSelectedRow();

           if( !(tblDat.getValueAt(intNumFil, INT_TBL_CODREGAAJU)==null))
               objTblCelEdiTxt.setCancelarEdicion(true);

        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
          int intNunFil = tblDat.getSelectedRow();

            double dblValApl = objUti.redondear( (tblDat.getValueAt(intNunFil, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_ABONO).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_ABONO).toString())), 2);
            double dblValPen = objUti.redondear( (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString())), 2);

            if(dblValApl > dblValPen){
                MensajeInf("EL VALOR QUE ESTA ABONANDO ES MAYOR AL VALOR PENDIENTE..");
                tblDat.setValueAt("0", intNunFil, INT_TBL_ABONO );
                tblDat.setValueAt( new Boolean(false), intNunFil, INT_TBL_CHKSEL );
            }else{
                if(dblValApl != 0 ) tblDat.setValueAt( new Boolean(true), intNunFil, INT_TBL_CHKSEL );
                else tblDat.setValueAt( new Boolean(false), intNunFil, INT_TBL_CHKSEL );
            }

           calculaTotMon();
         }
    });


     int intCol[]={ INT_TBL_ABONO };
     objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);


      //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatMouseClicked(evt);
                }
            });
   
     new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);


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
                      tblDat.setValueAt( new Boolean(true) , i, INT_TBL_CHKSEL);
                      String strValPen = (tblDat.getValueAt(i, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALPEN).toString()));
                      tblDat.setValueAt(strValPen, i, INT_TBL_ABONO);
                  
                    }
                    blnMarTodCanTblDat=false;
               }else{
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        tblDat.setValueAt( new Boolean(false) , i, INT_TBL_CHKSEL);
                        tblDat.setValueAt("0", i, INT_TBL_ABONO);
                    }
                    blnMarTodCanTblDat=true;
                }
                 calculaTotMon();
             }
        } catch (Exception e) {     objUti.mostrarMsgErr_F1(this, e);  }
    }


 /**
 * Calcula el monto total de los cheques
 *
 */
public void calculaTotMon(){
  double dblValChq=0;
  try{

    for(int i=0; i<tblDat.getRowCount(); i++){
      if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){
      
          dblValChq += Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_ABONO)==null)?"0":(tblDat.getValueAt(i, INT_TBL_ABONO).toString())));

    }}
    dblValChq=objUti.redondear(dblValChq, 6);
    objTblTot.setValueAt( new Double(dblValChq) ,0, 18 );

    valDoc.setText(""+dblValChq);

    generaAsiento(dblValChq);
  

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
     double dblValDebAju=0;
     double dblValHabAju=0;

       if (vecDetDiario==null) vecDetDiario = new java.util.Vector();
         else vecDetDiario.removeAllElements();

        if(dblValDoc < 0 ){
            dblValDebCli= Math.abs( dblValDoc );
            dblValHabCli=0;
            dblValDebAju=0;
            dblValHabAju= Math.abs( dblValDoc );;
        }else{
            dblValDebCli=0;
            dblValHabCli=Math.abs( dblValDoc );;
            dblValDebAju=Math.abs( dblValDoc );;
            dblValHabAju=0;
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
        vecReg.add(INT_VEC_CODCTA,new Integer( strCodCtaAju ));
        vecReg.add(INT_VEC_NUMCTA, strTxtCodCtaAju );
        vecReg.add(INT_VEC_BOTON, null);
        vecReg.add(INT_VEC_NOMCTA, strNomCtaAju );
        vecReg.add(INT_VEC_DEBE,  new Double( dblValDebAju ));
        vecReg.add(INT_VEC_HABER, new Double(dblValHabAju));
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

        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panCabGen = new javax.swing.JPanel();
        panCabCob = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtDesCodTitpDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        valDoc = new javax.swing.JTextField();
        lblCodDoc1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
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
        panFil = new javax.swing.JPanel();
        panDatFil = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNomCliDes = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNomCliHas = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        butCons = new javax.swing.JButton();
        chkMosRet = new javax.swing.JCheckBox();
        panTbl = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
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

        lblCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodDoc.setText("Código del documento:"); // NOI18N
        panCabCob.add(lblCodDoc);
        lblCodDoc.setBounds(10, 30, 120, 20);

        txtCodDoc.setBackground(objZafParSis.getColorCamposSistema());
        panCabCob.add(txtCodDoc);
        txtCodDoc.setBounds(120, 30, 90, 20);

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

        panCotGenSur.setPreferredSize(new java.awt.Dimension(250, 80));
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

        panFil.setLayout(new java.awt.BorderLayout());

        panDatFil.setPreferredSize(new java.awt.Dimension(685, 165));
        panDatFil.setLayout(null);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre del cliente"));
        jPanel6.setLayout(null);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel4.setText("Desde:");
        jPanel6.add(jLabel4);
        jLabel4.setBounds(12, 20, 40, 20);

        txtNomCliDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusLost(evt);
            }
        });
        jPanel6.add(txtNomCliDes);
        txtNomCliDes.setBounds(52, 20, 150, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel5.setText("Hasta:");
        jPanel6.add(jLabel5);
        jLabel5.setBounds(210, 20, 31, 15);

        txtNomCliHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusLost(evt);
            }
        });
        jPanel6.add(txtNomCliHas);
        txtNomCliHas.setBounds(245, 20, 180, 20);

        panDatFil.add(jPanel6);
        jPanel6.setBounds(10, 108, 460, 50);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Valor pendiente"));
        jPanel7.setLayout(null);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel7.setText("Desde:");
        jPanel7.add(jLabel7);
        jLabel7.setBounds(12, 24, 40, 20);

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel8.setText("Hasta:");
        jPanel7.add(jLabel8);
        jLabel8.setBounds(12, 45, 31, 15);

        jLabel1.setText("(-)");
        jPanel7.add(jLabel1);
        jLabel1.setBounds(50, 28, 20, 14);

        jLabel2.setText(".");
        jPanel7.add(jLabel2);
        jLabel2.setBounds(95, 28, 10, 14);

        jLabel10.setText("(+)");
        jPanel7.add(jLabel10);
        jLabel10.setBounds(50, 46, 20, 14);

        jLabel11.setText(".");
        jPanel7.add(jLabel11);
        jLabel11.setBounds(95, 47, 10, 14);

        panDatFil.add(jPanel7);
        jPanel7.setBounds(520, 10, 150, 90);

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel9.setText("Cliente:"); // NOI18N
        panDatFil.add(jLabel9);
        jLabel9.setBounds(15, 80, 50, 20);

        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        panDatFil.add(txtCodCli);
        txtCodCli.setBounds(70, 81, 70, 20);

        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        panDatFil.add(txtNomCli);
        txtNomCli.setBounds(140, 81, 230, 20);

        butCli.setText(".."); // NOI18N
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panDatFil.add(butCli);
        butCli.setBounds(370, 81, 20, 20);

        butCons.setText("Consultar");
        butCons.setPreferredSize(new java.awt.Dimension(90, 70));
        butCons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConsActionPerformed(evt);
            }
        });
        panDatFil.add(butCons);
        butCons.setBounds(520, 130, 90, 20);

        chkMosRet.setFont(new java.awt.Font("SansSerif", 0, 11));
        chkMosRet.setText("Mostrar las retenciones");
        panDatFil.add(chkMosRet);
        chkMosRet.setBounds(520, 100, 160, 18);

        panFil.add(panDatFil, java.awt.BorderLayout.NORTH);

        panTbl.setRequestFocusEnabled(false);
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
        spnDat.setViewportView(tblDat);

        panTbl.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTot.setViewportView(tblTot);

        panTbl.add(spnTot, java.awt.BorderLayout.SOUTH);

        panFil.add(panTbl, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Filtro", panFil);

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
            strCodTipDoc=objVenConTipdoc.getValueAt(1);
        }
}//GEN-LAST:event_butTipDocActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:

        Configura_ventana_consulta();
        
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtNomCliDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusGained
        // TODO add your handling code here:
        txtNomCliDes.selectAll();
}//GEN-LAST:event_txtNomCliDesFocusGained

    private void txtNomCliDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusLost
        // TODO add your handling code here:

        if (txtNomCliDes.getText().length()>0) {
            if (txtNomCliHas.getText().length()==0)
                txtNomCliHas.setText(txtNomCliDes.getText());
        }
}//GEN-LAST:event_txtNomCliDesFocusLost

    private void txtNomCliHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusGained
        // TODO add your handling code here:
        txtNomCliHas.selectAll();
}//GEN-LAST:event_txtNomCliHasFocusGained

    private void txtNomCliHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusLost
        // TODO add your handling code here:

}//GEN-LAST:event_txtNomCliHasFocusLost

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        // TODO add your handling code here:
         txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        // TODO add your handling code here:
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        // TODO add your handling code here:
         if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)) {
            if (txtCodCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }else
                BuscarCliente("a.co_cli",txtCodCli.getText(),0);
        }else
            txtCodCli.setText(strCodCli);

    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        // TODO add your handling code here:
        txtNomCli.transferFocus();
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        // TODO add your handling code here:
        strNomCli=txtNomCli.getText();
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        // TODO add your handling code here:
        if (!txtNomCli.getText().equalsIgnoreCase(strNomCli)) {
            if (txtNomCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }else
                BuscarCliente("a.tx_nom",txtNomCli.getText(),2);
        }else
            txtNomCli.setText(strNomCli);

    }//GEN-LAST:event_txtNomCliFocusLost

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        // TODO add your handling code here:
        objVenConCli.setTitle("Listado de cliente");
        objVenConCli.setCampoBusqueda(1);
        objVenConCli.show();
        if (objVenConCli.getSelectedButton()==objVenConCli.INT_BUT_ACE) {
            txtCodCli.setText(objVenConCli.getValueAt(1));
            txtNomCli.setText(objVenConCli.getValueAt(3));
        }
    }//GEN-LAST:event_butCliActionPerformed

    private void butConsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConsActionPerformed
        // TODO add your handling code here:
     try{

         double dblMinAju = objUti.redondear( (txtMinAjuEnt.getText()+"."+txtMinAjuDec.getText()), 3);
         double dblMaxAju = objUti.redondear( (txtMaxAjuEnt.getText()+"."+txtMaxAjuDec.getText()), 3);

         if(_getValidarValPen( dblMinAju , dblMinAjuCen) ){
          if(_getValidarValPen( dblMaxAju , dblMaxAjuCen) ){

              dblMinAju=dblMinAju*-1;
              cargarDatAju( dblMinAju, dblMaxAju );

          }else  MensajeInf("VALOR PENDIENTE ( + ) NO PUEDE SER MENOR AL VALOR ACTUAL ");
         }else  MensajeInf("VALOR PENDIENTE ( - ) NO PUEDE SER MENOR AL VALOR ACTUAL ");

     }catch(Exception Evt) { objUti.mostrarMsgErr_F1(this, Evt);  }
               
    }//GEN-LAST:event_butConsActionPerformed

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
            objVenConTipdoc.dispose();
            objVenConTipdoc=null;
            objUti=null;
            objTooBar=null;
            objZafParSis=null;
            txtFecDoc=null;


        }
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }



    
private boolean _getValidarValPen(double dblValIng, double dblValSis){
 boolean blnRes=true;
 try{
     
    if( dblValIng > dblValSis )
        blnRes=false;
    
 }catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
 return blnRes;    
}


private void BuscarTipDoc(String campo,String strBusqueda,int tipo){
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



private void BuscarCliente(String campo,String strBusqueda,int tipo){
  objVenConCli.setTitle("Listado de cliente");
  if(objVenConCli.buscar(campo, strBusqueda )) {
      txtCodCli.setText(objVenConCli.getValueAt(1));
      txtNomCli.setText(objVenConCli.getValueAt(3));
  }else{
        objVenConCli.setCampoBusqueda(tipo);
        objVenConCli.cargarDatos();
        objVenConCli.show();
        if (objVenConCli.getSelectedButton()==objVenConCli.INT_BUT_ACE) {
            txtCodCli.setText(objVenConCli.getValueAt(1));
            txtNomCli.setText(objVenConCli.getValueAt(3));
        }else{
            txtCodCli.setText(strCodCli);
            txtNomCli.setText(strNomCli);
  }}}







private boolean cargarDatAju(double dblMinAju, double dblMaxAju){
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="", sqlAux="", sqlAuxRet="", sqlAuxDif="";
   try{
      conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
      if(conn!=null){
           stmLoc=conn.createStatement();
           java.util.Vector vecData = new java.util.Vector();

          switch (objSelFec.getTipoSeleccion())
          {
                    case 0: //Búsqueda por rangos
                        sqlAux+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        sqlAux+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        sqlAux+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
          }

         if (txtCodCli.getText().length()>0)
           sqlAux+=" AND a.co_cli=" + txtCodCli.getText();
         if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
           sqlAux+=" AND ((LOWER(a.tx_nomcli) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a.tx_nomcli) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";



         if(!(chkMosRet.isSelected())) sqlAuxRet=" AND a1.nd_porret=0 ";

         if(objZafParSis.getCodigoMenu()==452) sqlAuxDif=" , (abs(a1.mo_pag)-abs(a1.nd_abo) ) as dif ";
         if(objZafParSis.getCodigoMenu()==1063) sqlAuxDif=" , (abs(a1.nd_abo)-abs(a1.mo_pag) ) as dif ";

           strSql="select  a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg  "+sqlAuxDif+" " +
           " ,a.co_cli, a.tx_nomcli, a2.tx_descor, a2.tx_deslar, a.ne_numdoc, a.fe_doc, a1.ne_diacre, a1.nd_porret, a1.fe_ven, a1.mo_pag  " +
           " from tbm_cabmovinv as a  " +
           " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  " +
           " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc  ) " +
           " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" " +
           " and a.co_tipdoc IN ( select co_tipdoc  from tbr_tipdocdetprg where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and co_mnu= "+objZafParSis.getCodigoMenu()+" ) " +
           " "+sqlAuxRet+"  "+sqlAux+" "+
           " and (abs(a1.mo_pag)-abs(a1.nd_abo) ) != 0  " +
           "  and a.st_reg not in ('I','E')  and a1.st_reg in ('A','C')  " +
           "  and (abs(a1.mo_pag)-abs(a1.nd_abo) )  between  "+dblMinAju+" and  "+dblMaxAju+"  " +
           "  order by fe_doc, tx_nomcli ";

           //System.out.println("--> "+ strSql );

           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){


               java.util.Vector vecReg = new java.util.Vector();
                 vecReg.add(INT_TBL_LINEA, "");
                 vecReg.add(INT_TBL_CHKSEL,  new Boolean( false ) );
                 vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp") );
                 vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
                 vecReg.add(INT_TBL_CODTID, rstLoc.getString("co_tipdoc") );
                 vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
                 vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg") );
                 vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
                 vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nomcli") );
                 vecReg.add(INT_TBL_DCTIPDOC, rstLoc.getString("tx_descor") );
                 vecReg.add(INT_TBL_DLTIPDOC, rstLoc.getString("tx_deslar") );
                 vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc") );
                 vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc") );
                 vecReg.add(INT_TBL_DIACRE, rstLoc.getString("ne_diacre") );
                 vecReg.add(INT_TBL_FECVEN, rstLoc.getString("fe_ven") );
                 vecReg.add(INT_TBL_PORRET, rstLoc.getString("nd_porret") );
                 vecReg.add(INT_TBL_VALDOC, rstLoc.getString("mo_pag") );
                 vecReg.add(INT_TBL_VALPEN, rstLoc.getString("dif") );
                 vecReg.add(INT_TBL_ABONO, "0");
                 vecReg.add(INT_TBL_CODREGAAJU, null);
                 vecReg.add(INT_TBL_ABONOORI, null);
               vecData.add(vecReg);

           }
           rstLoc.close();
           rstLoc=null;

           objTblMod.setData(vecData);
           tblDat .setModel(objTblMod);

      stmLoc.close();
      stmLoc=null;
      conn.close();
      conn=null;

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    System.gc();
    return blnRes;
}






    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCons;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JCheckBox chkMosRet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
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
    private javax.swing.JPanel panDatFil;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panTbl;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtDesCodTitpDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomCliDes;
    private javax.swing.JTextField txtNomCliHas;
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

               strSql="UPDATE tbm_cabpag SET st_reg='I', st_regrep='M' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
               " AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" ; " +
               " "+
               " UPDATE tbm_pagmovinv SET nd_abo=nd_abo- x.ndabo, st_regrep='M' FROM ( " +
               "    select co_emp, co_locpag, co_tipdocpag, co_docpag, co_regpag, nd_abo as ndabo  from tbm_detpag as a " +
               " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a.co_tipdoc="+intCodTipDoc+" " +
               " and a.co_doc="+intCodDoc+" and a.st_reg='A' " +
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
        " ,doc.tx_natdoc, doc.st_meringegrfisbod ,a1.co_cta, a1.tx_codcta, a1.tx_deslar AS txdeslarctaaju , a2.co_cta as cocta, a2.tx_codcta as txcodcta, a2.tx_deslar as deslarcta " +
        " FROM tbr_tipdocprg as menu " +
        " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "+
        " inner join tbm_placta as a1 on (a1.co_emp=doc.co_emp and a1.co_cta=doc.co_ctadeb ) " +
        " inner join tbm_placta as a2 on (a2.co_emp=doc.co_emp and a2.co_cta=doc.co_ctahab )" +
        " WHERE   menu.co_emp = "+objZafParSis.getCodigoEmpresa() + " and " +
        " menu.co_loc = " + objZafParSis.getCodigoLocal()+" AND  " +
        " menu.co_mnu = "+objZafParSis.getCodigoMenu()+" AND  menu.st_reg = 'S' ";
    }else{

        strSql= "SELECT  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor, " +
        " case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc "+
        " ,doc.tx_natdoc, doc.st_meringegrfisbod ,a1.co_cta, a1.tx_codcta, a1.tx_deslar AS txdeslarctaaju , a2.co_cta as cocta, a2.tx_codcta as txcodcta, a2.tx_deslar as deslarcta " +
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
            strCodTipDoc=txtCodTipDoc.getText();
            if(intVal==1)
               txtNumDoc.setText(((rstLoc.getString("numDoc")==null)?"":rstLoc.getString("numDoc")));


           strCodCtaCli=rstLoc.getString("cocta");
           strTxtCodCtaCli=rstLoc.getString("txcodcta");
           strNomCtaCli=rstLoc.getString("deslarcta");
           strCodCtaAju=rstLoc.getString("co_cta");
           strTxtCodCtaAju=rstLoc.getString("tx_codcta");
           strNomCtaAju=rstLoc.getString("txdeslarctaaju");

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
        
        if(insertarCab(conn, intCodDoc)){
          if(insertarDet(conn, intCodDoc)){
           if(objAsiDia.insertarDiario(conn, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), String.valueOf(intCodDoc), txtNumDoc.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy") )) {
              conn.commit();
              txtCodDoc.setText(""+intCodDoc);
              blnRes=true;
              cargarDetIns(conn, intCodDoc);
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
 String strCodEmp="",strCodLoc="", strCodTipDocRec="", strCodDoc="", strCodReg="", strAbono="0";
 int intCodRegDet=0;
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

     for(int i=0; i<tblDat.getRowCount(); i++){
      if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){

          strCodEmp=tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
          strCodLoc=tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
          strCodTipDocRec=tblDat.getValueAt(i, INT_TBL_CODTID).toString();
          strCodDoc=tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
          strCodReg=tblDat.getValueAt(i, INT_TBL_CODREG).toString();
          strAbono=(tblDat.getValueAt(i, INT_TBL_ABONO)==null?"0":(tblDat.getValueAt(i, INT_TBL_ABONO).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_ABONO).toString()));


          intCodRegDet++;
          strSqlInsDet.append(" INSERT INTO tbm_detpag( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag," +
          " co_docpag, co_regpag, nd_abo,  st_reg, st_regrep )" +
          " VALUES( "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" " +
          " ,"+intCodRegDet+", "+strCodLoc+", "+strCodTipDocRec+", "+strCodDoc+", "+strCodReg+", "+strAbono+", 'A','I' ) ; ");

          strSqlInsDet.append(" UPDATE tbm_pagmovinv set nd_abo=nd_abo + "+strAbono+", st_regrep='M' WHERE " +
          " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+" " +
          " and co_reg="+strCodReg+" ; ");

    }}

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

        String sqlAuxDif="";
         if(objZafParSis.getCodigoMenu()==452) sqlAuxDif=" , (abs(a1.mo_pag)-abs(a1.nd_abo) ) as dif ";
         if(objZafParSis.getCodigoMenu()==1063) sqlAuxDif=" , (abs(a1.nd_abo)-abs(a1.mo_pag) ) as dif ";

        strSql="SELECT a.co_reg as coregpag, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg  "+sqlAuxDif+" " +
        " ,a2.co_cli, a2.tx_nomcli, a3.tx_descor, a3.tx_deslar, a2.ne_numdoc, a2.fe_doc, a1.ne_diacre, a1.nd_porret, a1.fe_ven, a1.mo_pag, a.nd_abo " +
        " from tbm_detpag as a " +
        " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locpag and a1.co_tipdoc=a.co_tipdocpag and a1.co_doc=a.co_docpag and a1.co_reg=a.co_regpag ) " +
        " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) " +
        " inner join tbm_cabtipdoc as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc  ) " +
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" " +
        " and a.co_tipdoc="+txtCodTipDoc.getText()+"  and a.co_Doc="+intCodDoc+" and a.st_reg='A' "+
        " ORDER BY a.co_reg ";
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

           java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL_LINEA,"");
             vecReg.add(INT_TBL_CHKSEL,  new Boolean( true ) );
             vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp") );
             vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
             vecReg.add(INT_TBL_CODTID, rstLoc.getString("co_tipdoc") );
             vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
             vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg") );
             vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
             vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nomcli") );
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
               cargarDetIns( conn,  intCodDoc);
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


private boolean modificarDet(java.sql.Connection conn, int intCodTipDoc,  int intCodDoc){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 String strCodEmp="",strCodLoc="", strCodTipDocRec="", strCodDoc="", strCodReg="", strAbono="0", strCodRegAju="";
 int intCodRegDet=0;
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
          strCodRegAju=tblDat.getValueAt(i, INT_TBL_CODREGAAJU).toString();
          strAbono=(tblDat.getValueAt(i, INT_TBL_ABONOORI)==null?"0":(tblDat.getValueAt(i, INT_TBL_ABONOORI).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_ABONOORI).toString()));

          intCodRegDet++;
          strSqlInsDet.append(" UPDATE tbm_detpag SET st_reg='E' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND " +
          " co_loc="+objZafParSis.getCodigoLocal()+" AND  co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" AND co_reg="+strCodRegAju+" ;");
         
          strSqlInsDet.append(" UPDATE tbm_pagmovinv set nd_abo=nd_abo - "+strAbono+", st_regrep='M' WHERE " +
          " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDocRec+" and co_doc="+strCodDoc+" " +
          " and co_reg="+strCodReg+" ; ");

    }}

      if(intCodRegDet > 0 ){
           stmLoc.executeUpdate(strSqlInsDet.toString());
      }

      stmLoc.close();
      stmLoc=null;

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











     private void bloquea(javax.swing.JTextField txtFiel,  java.awt.Color colBack, boolean blnEst){
        colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }

     private void noEditable(boolean blnEst){

      }








 public void  clnTextos(){
    strCodTipDoc=""; strDesCodTipDoc=""; strDesLarTipDoc="";
    strCodCli=""; strNomCli="";
    
    txtFecDoc.setText("");

    txtCodTipDoc.setText("");
    txtDesCodTitpDoc.setText("");
    txtDesLarTipDoc.setText("");
    txtCodCli.setText("");
    txtNomCli.setText("");
    
    valDoc.setText("0.00");
    txtFecDoc.setText("");
    txtNumDoc.setText("");
    txtCodDoc.setText("");
    txaObs1.setText("");
    txaObs2.setText("");

    objTblMod.removeAllRows();
    objTblTot.setValueAt( new Double(0.00) ,0, 18 );

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
    double dblValAju=0;
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

         String sqlAuxDif="";
         if(objZafParSis.getCodigoMenu()==452) sqlAuxDif=" , (abs(a1.mo_pag)-abs(a1.nd_abo) ) as dif ";
         if(objZafParSis.getCodigoMenu()==1063) sqlAuxDif=" , (abs(a1.nd_abo)-abs(a1.mo_pag) ) as dif ";


        strSql="SELECT a.co_reg as coregpag, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg "+sqlAuxDif+"  " +
        " ,a2.co_cli, a2.tx_nomcli, a3.tx_descor, a3.tx_deslar, a2.ne_numdoc, a2.fe_doc, a1.ne_diacre, a1.nd_porret, a1.fe_ven, a1.mo_pag, a.nd_abo " +
        " from tbm_detpag as a " +
        " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locpag and a1.co_tipdoc=a.co_tipdocpag and a1.co_doc=a.co_docpag and a1.co_reg=a.co_regpag ) " +
        " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) " +
        " inner join tbm_cabtipdoc as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc  ) " +
        " WHERE a.co_emp="+rstDatRec.getInt("co_emp")+" and a.co_loc="+rstDatRec.getInt("co_loc")+" " +
        " and a.co_tipdoc="+rstDatRec.getInt("co_tipdoc")+"  and a.co_Doc="+rstDatRec.getInt("co_doc")+" and a.st_reg='A' "+
        " ORDER BY a.co_reg ";
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

           java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL_LINEA,"");
             vecReg.add(INT_TBL_CHKSEL,  new Boolean( true ) );
             vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp") );
             vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
             vecReg.add(INT_TBL_CODTID, rstLoc.getString("co_tipdoc") );
             vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
             vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg") );
             vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
             vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nomcli") );
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
             dblValAju+=rstLoc.getDouble("nd_abo");

            vecData.add(vecReg);
       }
        rstLoc.close();
        rstLoc=null;

        dblValAju=objUti.redondear(dblValAju, 2);
        objTblTot.setValueAt( new Double(dblValAju) ,0, 18 );


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

    if(!txtCodDoc.getText().equals(""))
        sqlFiltro +=" and co_doc="+txtCodDoc.getText();


    if(!txtNumDoc.getText().equals(""))
       sqlFiltro = sqlFiltro + " and ne_numdoc1="+txtNumDoc.getText();

    if(txtFecDoc.isFecha()){
         int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
         String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
         sqlFiltro = sqlFiltro + " and fe_doc = '" +  strFecSql + "'";
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
  bloquea(txtCodCli, colBack, false);
  bloquea(txtNomCli, colBack, false);
  bloquea(valDoc, colBack, false);
  bloquea(txtNumDoc, colBack, false);
  bloquea(txtNomCliDes, colBack, false);
  bloquea(txtNomCliHas, colBack, false);

  bloquea(txtMinAjuEnt, colBack, false);
  bloquea(txtMinAjuDec, colBack, false);
  bloquea(txtMaxAjuEnt, colBack, false);
  bloquea(txtMaxAjuDec, colBack, false);

  chkMosRet.setEnabled(false);

  butTipDoc.setEnabled(false);
  butCli.setEnabled(false);
  butCons.setEnabled(false);

 blnHayCam=false;


 }catch(Exception evt){ objUti.mostrarMsgErr_F1(this, evt); }
}





        //******************************************************************************************************

      public boolean vistaPreliminar(){
            
            return true;
        }









        public boolean imprimir(){
            
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
  
        


 protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }






 
}
