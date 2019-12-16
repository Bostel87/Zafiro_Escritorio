
/*
 * ZafCom25.java  spnNumMesRep
 *
 * Created on 18 de octubre de 2007, 12:29 PM
 */
         
package Compras.ZafCom58;
import Librerias.ZafParSis.ZafParSis;
import java.util.Vector;
import Librerias.ZafVenCon.ZafVenCon; 
import java.util.ArrayList;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.Statement;  
import java.sql.ResultSet; 
import java.sql.DriverManager;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafToolBar.ZafToolBar;

/**  
 *           
 * @author  jayapata   
 */

public class ZafCom58 extends javax.swing.JInternalFrame {
    
    ZafParSis objZafParSis;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod,objTblMod2, objTblModCotEmi, objTblModPrv, objTblModCotRec, objTblModOrdCom;
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;        
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;        
    private Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi objTblEdi, objTblEdiCotEmi;
    private java.util.Date datFecAux;  
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
    private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt;
    private Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu ZafTblPopMn;
    private ZafMouMotAda objMouMotAda;
    private ZafTblFilCab objTblFilCab;
    mitoolbar objTooBar;
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    Librerias.ZafUtil.UltDocPrint objUltDocPrint;
    private ZafMouMotAdaOrdCom objMouMotAdaOrdCom;
    private ZafMouMotAdaCotEmi objMouMotAdaCotEmi;


  
    ZafVenCon objVenConBod,objVenConItm;

    ZafVenCon objVenConTipdoc; //*****************
    ZafVenCon objVenConBodUsr; //*****************
    ZafVenCon objVenConCla, objVenConGrp; //*****************
    

    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    ZafUtil objUti;
   // private ZafThreadGUI objThrGUI;
    javax.swing.JInternalFrame jfrThis; //Hace referencia a this

    // TABLA DE HISTORICAS DE MESE
    final int INT_TBL_LIN   = 0 ;
    final int INT_TBL_CHK   = 1 ;
    final int INT_TBL_ANI   = 2 ;
    final int INT_TBL_MES   = 3 ;
    final int INT_TBL_MESABR = 4 ;
    final int INT_TBL_CODMES = 5 ;

    // TAB DE REPORTE
    public int INT_TBL2_LINEA   = 0 ;
    int INT_TBL2_COSIS = 1 ;
    int INT_TBL2_COALT = 2 ;
    int INT_TBL2_NOMBR = 3 ;
    int INT_TBL2_UNIMED= 4 ;
    int INT_TBL2_TOTUNI=5;
    int INT_TBL2_TOTVEC=6;
    int INT_TBL2_TOTMES=7;
    int INT_TBL2_STKACT=8;
    int INT_TBL2_NUMMRE=9;  // numero de meses a reponer.
    int INT_TBL2_PROCAL=10;
    int INT_TBL2_PROMAN=11;
    int INT_TBL2_STKMIN=12;
    int INT_TBL2_REPOSI=13; // cantidad de reposicion
    int INT_TBL2_CANCOM=14; // cantidad que se comprara
    int INT_TBL2_BUTREP=15;
    int INT_TBL2_PRVELE=16;
    int INT_TBL2_COTGEN=17;
    int INT_TBL2_COTING=18;
    int INT_TBL2_NTCCAN=19;
    int INT_TBL2_NTCOMP=20;
    int INT_TBL2_CTNCOM=21;  // cantidad total numca comprada
    int INT_TBL2_CODREG=22;
    int INT_TBL2_CTNCOMORI=23;
    int INT_TBL2_CANCOMORI=24;

     // TAB PROVEEDORES
    final int INT_TBL_PRVLIN = 0 ;
    final int INT_TBL_PRVCOD = 1 ;
    final int INT_TBL_PRVNOM = 2 ;

    // TAB COTIZACIONES EMITIDAS
    final int INT_TBL_CELIN   = 0 ;
    final int INT_TBL_CECOLOC = 1 ;
    final int INT_TBL_CECODOC = 2 ;
    final int INT_TBL_CEFECOT = 3 ;
    final int INT_TBL_CECOPRV = 4 ;
    final int INT_TBL_CENOPRV = 5 ;
    final int INT_TBL_CEBUTCOT= 6 ;

   // TAB COTIZACIONES RECIBIDAS
    final int INT_TBL_CRLIN = 0 ;
    final int INT_TBL_CRNCOT= 1 ;
    final int INT_TBL_CRFCOT= 2 ;
    final int INT_TBL_CRBSCO= 3 ;

   // TAB ORDENES DE COMPRA
    final int INT_TBL_OCLIN = 0 ;
    final int INT_TBL_OCCODLOC= 1 ;
    final int INT_TBL_OCTIPDOC= 2 ;
    final int INT_TBL_OCDTIPDOC=3 ;
    final int INT_TBL_OCDLTD=  4 ;
    final int INT_TBL_OCCODOC= 5 ;
    final int INT_TBL_OCNUDOC= 6 ;
    final int INT_TBL_OCFDOC=  7 ;
    final int INT_TBL_OCCOPRV= 8 ;
    final int INT_TBL_OCNOPRV= 9 ;
    final int INT_TBL_OCTOT= 10 ;
    final int INT_TBL_OCBOC= 11 ;

          
    String strCodTipDoc="", strDesCorTipDoc="",strDesLarTipDoc="";
    String strCodBod="", strNomBod="";
    String strCodCla="", strDesCla="";
    String strCodItm="",strDesItm="";
    String strNumOcGen="";
    
    String strnombod="",strcodbod="",strcodalt="",strnomitm="";
    int intCodPer=0;
    private boolean blnCon;     

    javax.swing.JTextField txtCodTipDoc = new javax.swing.JTextField();

    java.sql.Connection CONN_GLO;
    java.sql.Statement STM_GLO;
    java.sql.ResultSet RST_LOC;

    StringBuffer stbDocRelEmpLoc;
    int intDocRelEmpLoc=0;

    String strCodGrp="",strDesGrp="";
     
    int intValBus=0;
    int intCodLoc=0;
    int intCodTipDoc=0;
    int intCodDoc=0;
    int intCodBod=0;

    /** Creates new form ZafCom25 */
    public ZafCom58(ZafParSis objParsis) {
       
        try{
        this.objZafParSis = (Librerias.ZafParSis.ZafParSis) objParsis.clone(); 
        objUti = new ZafUtil();
       
        initComponents();
        jfrThis = this;

        objTooBar = new mitoolbar(this);
	    this.getContentPane().add(objTooBar,"South");
        objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);

        objTooBar.agregarSeparador();
        objTooBar.agregarBoton(butCot);
        objTooBar.agregarSeparador();
        objTooBar.agregarBoton(butOrdCom);


        txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y"); 
        txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtFecDoc.setText("");
        PanTabGen.add(txtFecDoc);
        txtFecDoc.setBounds(580, 9, 90, 20);

        objTooBar.setVisibleAnular(false);
        objTooBar.setVisibleEliminar(false);

        panDatHis.setVisible(false);
        panDatFil.setVisible(false);
        lblMesRep.setVisible(false);
        spnNumMesRep.setVisible(false);
        butCon.setVisible(false);


        if( objZafParSis.getTipGrpClaInvPreUsr()=='P')
          radPub.setSelected(true);

         if( objZafParSis.getTipGrpClaInvPreUsr()=='R')
           radPri.setSelected(true);
        
        this.setTitle(objZafParSis.getNombreMenu()+" v.0.9 ");
        lblTit.setText( objZafParSis.getNombreMenu() );
       }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); } 
    }

 


public ZafCom58(ZafParSis objParsis, int intCodEmp, int intCodLocSel, int intCodTipDocSel, int intCodDocSel, int intCodBodSel ) {
    this(objParsis);
    //intCodEmp = intCodEmp;
    intCodLoc = intCodLocSel;
    intCodTipDoc= intCodTipDocSel;
    intCodDoc= intCodDocSel;
    intCodBod=intCodBodSel;

    intValBus = 1;
}

      
      public void Configura_ventana_consulta(){

        configurarVenConItm();
        configurarVenConTipDoc();
        configurarVenConBodUsr();
        configurarVenConClasificacion();
        configurarVenConGrp();

        
        configurarForm();
        configurarTblPrv();
        configurarTblCotEmi();
        configurarTblCotRec();
        configurarTblOrdCom();
        
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



private boolean configurarVenConGrp() {
 boolean blnRes=true;
 try {
    ArrayList arlCam=new ArrayList();
    arlCam.add("co_grp");
    arlCam.add("tx_descor");
    arlCam.add("tx_deslar");

    ArrayList arlAli=new ArrayList();
    arlAli.add("Cód.Grp");
    arlAli.add("Des.Cor.");
    arlAli.add("Des.Lar.");

    ArrayList arlAncCol=new ArrayList();
    arlAncCol.add("80");
    arlAncCol.add("110");
    arlAncCol.add("350");

  //Armar la sentencia SQL.   a7.nd_stkTot,
  String Str_Sql="";
  Str_Sql="SELECT co_grp, tx_descor, tx_deslar FROM tbm_grpclainv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND st_reg='A' ";
  objVenConGrp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
  arlCam=null;
  arlAli=null;
  arlAncCol=null;

 }catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}



private boolean configurarVenConItm() {
    boolean blnRes=true;
    try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("co_itm");
        arlCam.add("tx_codalt");
        arlCam.add("tx_descor");
        arlCam.add("tx_nomitm");
        ArrayList arlAli=new ArrayList();
        arlAli.add("Cód.Itm");
        arlAli.add("Cód.Alt.");
        arlAli.add("Unidad.");
        arlAli.add("Nom.Itm.");
        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("50");
        arlAncCol.add("100");
        arlAncCol.add("60");
        arlAncCol.add("300");
       //Armar la sentencia SQL.
        String  strSQL="";
        String strAuxInv="",strConInv="";

     if(objZafParSis.getCodigoUsuario()!=1){
        strAuxInv = ",CASE WHEN (" +
        " (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
        " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
        " AND co_tipdoc= 2   AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
        " ))) THEN 'S' ELSE 'N' END  as isterL";

        strConInv=" WHERE isterL='S' ";
    }

        strSQL="SELECT * FROM( SELECT * "+strAuxInv+" FROM( select a.co_itm, a.tx_codalt, a1.tx_descor, a.tx_nomitm   from tbm_inv as a " +
        " left join tbm_var as a1 on (a1.co_reg=a.co_uni) " +
        " WHERE  a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.st_reg not in ('U','T')   ) AS x ) AS x  "+strConInv+" ";
        objVenConItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
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
    Str_Sql="Select a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocprg as b " +
" inner join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
    " where   b.co_emp="+objZafParSis.getCodigoEmpresa()+" and " +
    " b.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
    " b.co_mnu = "+objZafParSis.getCodigoMenu();

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

private boolean configurarVenConBodUsr() {
boolean blnRes=true;
try {
    ArrayList arlCam=new ArrayList();
    arlCam.add("a.co_bod");
    arlCam.add("a.tx_nom");

    ArrayList arlAli=new ArrayList();
    arlAli.add("Código");
    arlAli.add("Nom.Bod");

    ArrayList arlAncCol=new ArrayList();
    arlAncCol.add("80");
    arlAncCol.add("350");

    //Armar la sentencia SQL.   a7.nd_stkTot,
    String Str_Sql="";
    Str_Sql="SELECT co_bod, tx_nom FROM ( " +
    " select a.co_bod,  a1.tx_nom from tbr_bodlocprgusr as a " +
    " inner join tbm_bod as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod) " +
    " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" " +
    " and a.co_usr="+objZafParSis.getCodigoUsuario()+" and a.co_mnu="+objZafParSis.getCodigoMenu()+"  " +
    " ) as a";

    objVenConBodUsr=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
    arlCam=null;
    arlAli=null;
    arlAncCol=null;

    objVenConBodUsr.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
}
catch (Exception e) {
    blnRes=false;
    objUti.mostrarMsgErr_F1(this, e);
}
return blnRes;
}

private boolean configurarVenConClasificacion() {
    boolean blnRes=true;
    try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("co_grp");
        arlCam.add("nomgrp");
        arlCam.add("co_cla");
        arlCam.add("nomcla");
        ArrayList arlAli=new ArrayList();
        arlAli.add("Cód.Grp");
        arlAli.add("Nom.Grp.");
        arlAli.add("Cód.Cla");
        arlAli.add("Nom.Cla.");
        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("50");
        arlAncCol.add("200");
        arlAncCol.add("50");
        arlAncCol.add("200");
        //Armar la sentencia SQL.
        String  strSQL="";
        strSQL="SELECT * FROM( SELECT a.co_grp, a1.tx_deslar as nomgrp, a.co_cla, a.tx_deslar as nomcla FROM tbm_clainv as a " +
        " LEFT JOIN tbm_grpclainv as a1 on (a1.co_emp=a.co_emp and a1.co_grp=a.co_grp) " +
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and  a.st_reg not in ('E')  ) AS x ";
        objVenConCla=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
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


private boolean configurarTblPrv(){
 boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    vecCab.add(INT_TBL_PRVLIN,"");
    vecCab.add(INT_TBL_PRVCOD,"Cod.Prv");
    vecCab.add(INT_TBL_PRVNOM,"Proveedor");

    objTblModPrv=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblModPrv.setHeader(vecCab);
    tblPrv.setModel(objTblModPrv);

    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblPrv, INT_TBL_PRVLIN);

    tblPrv.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblPrv.getColumnModel();
    tblPrv.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_PRVLIN).setPreferredWidth(30);
    tcmAux.getColumn(INT_TBL_PRVCOD).setPreferredWidth(100);
    tcmAux.getColumn(INT_TBL_PRVNOM).setPreferredWidth(500);

    objTblModPrv.setModoOperacion(objTblModPrv.INT_TBL_EDI);

    tcmAux=null;

    return blnres;
}

private boolean configurarTblCotRec(){
  boolean blnres=false;
   Vector vecCab=new Vector();    //Almacena las cabeceras
   vecCab.clear();

    vecCab.add(INT_TBL_CRLIN,"");
    vecCab.add(INT_TBL_CRNCOT,"Núm.Cot");
    vecCab.add(INT_TBL_CRFCOT,"Fec.Cot");
    vecCab.add(INT_TBL_CRBSCO,"..");

    objTblModCotRec=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblModCotRec.setHeader(vecCab);
    tblCotRec.setModel(objTblModCotRec);

    tblCotRec.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblCotRec.getColumnModel();
    tblCotRec.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_CRLIN).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CRNCOT).setPreferredWidth(100);
    tcmAux.getColumn(INT_TBL_CRFCOT).setPreferredWidth(100);
    tcmAux.getColumn(INT_TBL_CRBSCO).setPreferredWidth(25);

    Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButCotEmi=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
    tcmAux.getColumn(INT_TBL_CRBSCO).setCellRenderer(objTblCelRenButCotEmi);
    objTblCelRenButCotEmi=null;
    new ButCotRec(tblCotRec, INT_TBL_CRBSCO);   //*****

    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
    vecAux.add("" + INT_TBL_CRBSCO);
    objTblModCotRec.setColumnasEditables(vecAux);
    vecAux=null;
    //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblCotRec);

     //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblCotRec, INT_TBL_CRLIN);

    objTblModCotRec.setModoOperacion(objTblModCotRec.INT_TBL_EDI);

     return blnres;
}

private class ButCotRec extends Librerias.ZafTableColBut.ZafTableColBut_uni{
    public ButCotRec(javax.swing.JTable tbl, int intIdx){
        super(tbl,intIdx, "Guía de remisión.");
    }
    public void butCLick() {
       int intCol = tblCotRec.getSelectedRow();
      // String strObs = ( tblDat.getValueAt(intCol,  INT_TBL_OBS  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_OBS  ).toString());
      // llamarVenObs(strObs, intCol);
    }
}


private boolean configurarTblOrdCom(){
  boolean blnres=false;
   Vector vecCab=new Vector();    //Almacena las cabeceras
   vecCab.clear();

    vecCab.add(INT_TBL_OCLIN,"");
    vecCab.add(INT_TBL_OCCODLOC,"Cód.Loc");
    vecCab.add(INT_TBL_OCTIPDOC,"Cód.Tip.Doc");
    vecCab.add(INT_TBL_OCDTIPDOC,"Tip.Doc");
    vecCab.add(INT_TBL_OCDLTD,"Des.Tip.Doc");
    vecCab.add(INT_TBL_OCCODOC,"Cód.Doc");
    vecCab.add(INT_TBL_OCNUDOC,"Núm.Doc");
    vecCab.add(INT_TBL_OCFDOC,"Fec.Doc");
    vecCab.add(INT_TBL_OCCOPRV,"Cód.Prv");
    vecCab.add(INT_TBL_OCNOPRV,"Nom.Prv");
    vecCab.add(INT_TBL_OCTOT,"Total");
    vecCab.add(INT_TBL_OCBOC,"..");

    objTblModOrdCom=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblModOrdCom.setHeader(vecCab);
    tblOrdCom.setModel(objTblModOrdCom);


     //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
    objMouMotAdaOrdCom=new ZafMouMotAdaOrdCom();
    tblOrdCom.getTableHeader().addMouseMotionListener(objMouMotAdaOrdCom);

    
    ArrayList arlColHid=new ArrayList();
    arlColHid.add(""+INT_TBL_OCTIPDOC);
    arlColHid.add(""+INT_TBL_OCCODOC);
    arlColHid.add(""+INT_TBL2_CANCOMORI);
    objTblModOrdCom.setSystemHiddenColumns(arlColHid, tblOrdCom);
    arlColHid=null;

    tblOrdCom.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblOrdCom.getColumnModel();
    tblOrdCom.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_OCLIN).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_OCCODLOC).setPreferredWidth(40);
    tcmAux.getColumn(INT_TBL_OCDTIPDOC).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_OCDLTD).setPreferredWidth(120);
    tcmAux.getColumn(INT_TBL_OCNUDOC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_OCFDOC).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_OCCOPRV).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_OCNOPRV).setPreferredWidth(150);
    tcmAux.getColumn(INT_TBL_OCTOT).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_OCBOC).setPreferredWidth(25);

    objTblModOrdCom.setColumnDataType(INT_TBL_OCTOT, objTblModOrdCom.INT_COL_DBL, new Integer(0), null);

     Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLblOC=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
     objTblCelRenLblOC.setBackground(objZafParSis.getColorCamposObligatorios());
     objTblCelRenLblOC.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
     objTblCelRenLblOC.setTipoFormato(objTblCelRenLblOC.INT_FOR_NUM);
     objTblCelRenLblOC.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
     tcmAux.getColumn( INT_TBL_OCTOT ).setCellRenderer(objTblCelRenLblOC);
     objTblCelRenLblOC=null;

    Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButCotEmi=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
    tcmAux.getColumn(INT_TBL_OCBOC).setCellRenderer(objTblCelRenButCotEmi);
    objTblCelRenButCotEmi=null;
    new ButOrdCom(tblOrdCom, INT_TBL_OCBOC);   //*****

    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
    vecAux.add("" + INT_TBL_OCBOC);
    objTblModOrdCom.setColumnasEditables(vecAux);
    vecAux=null;
    //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblOrdCom);

     //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblOrdCom, INT_TBL_OCLIN);

    objTblModOrdCom.setModoOperacion(objTblModOrdCom.INT_TBL_EDI);

     return blnres;
}

private class ButOrdCom extends Librerias.ZafTableColBut.ZafTableColBut_uni{
    public ButOrdCom(javax.swing.JTable tbl, int intIdx){
        super(tbl,intIdx, "Guía de remisión.");
    }
    public void butCLick() {
       int intCol = tblOrdCom.getSelectedRow();
       String strCodLoc = ( tblOrdCom.getValueAt(intCol,  INT_TBL_OCCODLOC  )==null?"":tblOrdCom.getValueAt(intCol,  INT_TBL_OCCODLOC  ).toString());
       String strCodTipDoc = ( tblOrdCom.getValueAt(intCol,  INT_TBL_OCTIPDOC  )==null?"":tblOrdCom.getValueAt(intCol,  INT_TBL_OCTIPDOC  ).toString());
       String strCodDoc = ( tblOrdCom.getValueAt(intCol,  INT_TBL_OCCODOC  )==null?"":tblOrdCom.getValueAt(intCol,  INT_TBL_OCCODOC  ).toString());

       llamarVenOrdCom(objZafParSis.getCodigoEmpresa(), Integer.parseInt(strCodLoc), Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc)  );
    }
}

private void llamarVenOrdCom(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 
   Compras.ZafCom02.ZafCom02 obj = new Compras.ZafCom02.ZafCom02(objZafParSis,  intCodEmp, intCodLoc,  intCodTipDoc, intCodDoc, 45  );
   this.getParent().add(obj,javax.swing.JLayeredPane.DEFAULT_LAYER);
   obj.show();

}


private boolean configurarTblCotEmi(){
 boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    vecCab.add(INT_TBL_CELIN,"");
    vecCab.add(INT_TBL_CECOLOC,"Cod.Loc");
    vecCab.add(INT_TBL_CECODOC,"Cod.Cot");
    vecCab.add(INT_TBL_CEFECOT,"Fec.Cot");
    vecCab.add(INT_TBL_CECOPRV,"Cod.Prv");
    vecCab.add(INT_TBL_CENOPRV,"Proveedor");
    vecCab.add(INT_TBL_CEBUTCOT,"..");

    objTblModCotEmi=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblModCotEmi.setHeader(vecCab);
    tblCotEmi.setModel(objTblModCotEmi);

     //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
    objMouMotAdaCotEmi=new ZafMouMotAdaCotEmi();
    tblCotEmi.getTableHeader().addMouseMotionListener(objMouMotAdaCotEmi);

    tblCotEmi.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblCotEmi.getColumnModel();
    tblCotEmi.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_CELIN).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CECOLOC).setPreferredWidth(65);
    tcmAux.getColumn(INT_TBL_CECODOC).setPreferredWidth(65);
    tcmAux.getColumn(INT_TBL_CEFECOT).setPreferredWidth(100);
    tcmAux.getColumn(INT_TBL_CECOPRV).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_CENOPRV).setPreferredWidth(315);
    tcmAux.getColumn(INT_TBL_CEBUTCOT).setPreferredWidth(25);

    Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButCotEmi=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
    tcmAux.getColumn(INT_TBL_CEBUTCOT).setCellRenderer(objTblCelRenButCotEmi);
    objTblCelRenButCotEmi=null;
    new ButCotEmi(tblCotEmi, INT_TBL_CEBUTCOT);   //*****

    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
    vecAux.add("" + INT_TBL_CEBUTCOT);
    objTblModCotEmi.setColumnasEditables(vecAux);
    vecAux=null;
    //Configurar JTable: Editor de la tabla.
    objTblEdiCotEmi=new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblCotEmi);

     //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblCotEmi, INT_TBL_LIN);

    objTblModCotEmi.setModoOperacion(objTblModCotEmi.INT_TBL_EDI);
 
     return blnres;
}


private class ButCotEmi extends Librerias.ZafTableColBut.ZafTableColBut_uni{
    public ButCotEmi(javax.swing.JTable tbl, int intIdx){
        super(tbl,intIdx, "Guía de remisión.");
    }
    public void butCLick() {
       int intCol = tblCotEmi.getSelectedRow();

       String strCodLoc = ( tblCotEmi.getValueAt(intCol,  INT_TBL_CECOLOC  )==null?"":tblCotEmi.getValueAt(intCol,  INT_TBL_CECOLOC  ).toString());
       String strCodCot = ( tblCotEmi.getValueAt(intCol,  INT_TBL_CECODOC  )==null?"":tblCotEmi.getValueAt(intCol,  INT_TBL_CECODOC  ).toString());

       llamarVenCotEmi(objZafParSis.getCodigoEmpresa(), strCodLoc, strCodCot);

    }
}
  
private void llamarVenCotEmi(int intCodEmp, String strCodLoc, String strCodCot ){
   Compras.ZafCom01.ZafCom01 obj = new Compras.ZafCom01.ZafCom01(objZafParSis,  intCodEmp, strCodLoc,  strCodCot );
   this.getParent().add(obj,javax.swing.JLayeredPane.DEFAULT_LAYER);
   obj.show();
}


private boolean configurarForm(){
 boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    vecCab.add(INT_TBL_LIN,"");
    vecCab.add(INT_TBL_CHK," ");
    vecCab.add(INT_TBL_ANI,"Año");
    vecCab.add(INT_TBL_MES,"Mes");
    vecCab.add(INT_TBL_MESABR,"Mes.Abr");
    vecCab.add(INT_TBL_CODMES,"Cod.Mes");

    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblAnMe.setModel(objTblMod);
            
    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblAnMe, INT_TBL_LIN);
             
    tblAnMe.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblAnMe.getColumnModel();
    tblAnMe.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CHK).setPreferredWidth(30);
    tcmAux.getColumn(INT_TBL_ANI).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_MES).setPreferredWidth(130);

    ArrayList arlColHid=new ArrayList();
    arlColHid.add(""+INT_TBL_MESABR);
    arlColHid.add(""+INT_TBL_CODMES);
    arlColHid.add(""+INT_TBL2_CANCOMORI);
    objTblMod.setSystemHiddenColumns(arlColHid, tblAnMe);
    arlColHid=null;

    ////*****tblAnMe.getTableHeader().addMouseMotionListener(objTblMod);
    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
    vecAux.add("" + INT_TBL_CHK);
    objTblMod.setColumnasEditables(vecAux);
    vecAux=null;
    //Configurar JTable: Editor de la tabla.
    objTblEdi=new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblAnMe);

    objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    tcmAux.getColumn(INT_TBL_CHK).setCellRenderer(objTblCelRenChk);
    objTblCelRenChk=null;

    objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_CHK).setCellEditor(objTblCelEdiChk);
    objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
        }
    });
        
    Vector vecCab2=new Vector();    //Almacena las cabeceras
    vecCab2.clear();
    vecCab2.add(INT_TBL2_LINEA,"");
    vecCab2.add(INT_TBL2_COSIS,"Cod.Itm.");
    vecCab2.add(INT_TBL2_COALT,"Cod.Alt.");
    vecCab2.add(INT_TBL2_NOMBR,"Nombre");
    vecCab2.add(INT_TBL2_UNIMED,"Unidad");
  
    objTblMod2=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod2.setHeader(vecCab2);
    tblDat.setModel(objTblMod2);

     //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL2_LINEA);
            
    //**********************************************************************************************

     datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
     String strPer =  String.valueOf((datFecAux.getYear()+1900));
     intCodPer=Integer.parseInt(strPer);

    setEditable(true);
    setEditable2(true);

    cargarAnio();

     //Configurar JTable: Centrar columnas.
    ZafTblPopMn = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
    ZafTblPopMn.setInsertarFilaVisible(false);
    ZafTblPopMn.setInsertarFilasVisible(false);

     return blnres;  
}
    
    
       

private void cargarAnio(){
 Vector vecData = new Vector();
 for(int i=2001 ; i<=intCodPer ; i++){
  java.util.Vector vecReg = new java.util.Vector();
   vecReg.add(INT_TBL_LIN, "");
   vecReg.add(INT_TBL_CHK, new Boolean(false) );
   vecReg.add(INT_TBL_ANI, ""+i );
   vecReg.add(INT_TBL_MES, "");
   vecReg.add(INT_TBL_MESABR, "");
   vecReg.add(INT_TBL_CODMES, "");
   vecData.add(vecReg);
}
objTblMod.setData(vecData);
tblAnMe .setModel(objTblMod);       
}


    
    
     private void cargarAnioMes(){
         Vector vecData = new Vector();
         for(int i=2001 ; i<=intCodPer ; i++){
           for(int j=1 ; j<=12 ; j++){ 
             java.util.Vector vecReg = new java.util.Vector();
               vecReg.add(INT_TBL_LIN, "");
               vecReg.add(INT_TBL_CHK, new Boolean(false) );
               vecReg.add(INT_TBL_ANI, ""+i );
              
               switch(j){
                  case(1): vecReg.add(INT_TBL_MES, "Enero");       vecReg.add(INT_TBL_MESABR, "Ene"); break;
                  case(2): vecReg.add(INT_TBL_MES, "Febrero");     vecReg.add(INT_TBL_MESABR, "Feb"); break;
                  case(3):  vecReg.add(INT_TBL_MES, "Marzo");      vecReg.add(INT_TBL_MESABR, "Mar"); break;
                  case(4):  vecReg.add(INT_TBL_MES, "Abril");      vecReg.add(INT_TBL_MESABR, "Abr");  break;
                  case(5):  vecReg.add(INT_TBL_MES, "Mayo");       vecReg.add(INT_TBL_MESABR, "May");  break;
                  case(6):  vecReg.add(INT_TBL_MES, "Junio");      vecReg.add(INT_TBL_MESABR, "Jun");  break;
                  case(7):  vecReg.add(INT_TBL_MES, "Julio");      vecReg.add(INT_TBL_MESABR, "Jul");  break;
                  case(8):  vecReg.add(INT_TBL_MES, "Agosto");     vecReg.add(INT_TBL_MESABR, "Ago");  break;
                  case(9):  vecReg.add(INT_TBL_MES, "Septiembre"); vecReg.add(INT_TBL_MESABR, "Sep");  break;
                  case(10):  vecReg.add(INT_TBL_MES, "Octubre");   vecReg.add(INT_TBL_MESABR, "Oct");  break;
                  case(11):  vecReg.add(INT_TBL_MES, "Noviembre"); vecReg.add(INT_TBL_MESABR, "Nov");  break;
                  case(12):  vecReg.add(INT_TBL_MES, "Diciembre"); vecReg.add(INT_TBL_MESABR, "Dic");  break;
                
                   default:
                    break;
               }
               
               vecReg.add(INT_TBL_CODMES, ""+j);
               vecData.add(vecReg);  
         }}
          objTblMod.setData(vecData);
          tblAnMe .setModel(objTblMod);       
    }
    
    
    
    private void setEditable(boolean editable)
   {
        if (editable==true){
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
        }else{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }       
       
   }
    
    
      
   private void setEditable2(boolean editable)
   {
        if (editable==true){
            objTblMod2.setModoOperacion(objTblMod.INT_TBL_EDI);
            
        }else{
            objTblMod2.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }       
       
   }
      
     
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OptGrp = new javax.swing.ButtonGroup();
        OptGrp2 = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        TabGen = new javax.swing.JTabbedPane();
        PanFil = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        panDatHis = new javax.swing.JPanel();
        OptRepAnu = new javax.swing.JRadioButton();
        OptRepMes = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAnMe = new javax.swing.JTable();
        panDatFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblCli = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtDesItm = new javax.swing.JTextField();
        butSol = new javax.swing.JButton();
        lbltipgrp = new javax.swing.JLabel();
        radPub = new javax.swing.JRadioButton();
        radPri = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCodCla = new javax.swing.JTextField();
        txtCodGrp = new javax.swing.JTextField();
        txtDesCla = new javax.swing.JTextField();
        txtDesGrp = new javax.swing.JTextField();
        butCla = new javax.swing.JButton();
        butSol1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtcodaltdes = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtcodalthas = new javax.swing.JTextField();
        butCon = new javax.swing.JButton();
        butCot = new javax.swing.JButton();
        butOrdCom = new javax.swing.JButton();
        PanTabGen = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        lblBod = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtCodDoc = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBusBod = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblNumDoc = new javax.swing.JLabel();
        txtDesCodTitpDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblMesRep = new javax.swing.JLabel();
        spnNumMesRep = new javax.swing.JSpinner();
        panObs = new javax.swing.JPanel();
        panLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panTxa = new javax.swing.JPanel();
        spnObs3 = new javax.swing.JScrollPane();
        txtObs1 = new javax.swing.JTextArea();
        spnObs4 = new javax.swing.JScrollPane();
        txtObs2 = new javax.swing.JTextArea();
        PanRep = new javax.swing.JPanel();
        scrtbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panPrv = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPrv = new javax.swing.JTable();
        panCotEmi = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCotEmi = new javax.swing.JTable();
        panCorRec = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCotRec = new javax.swing.JTable();
        panordcom = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblOrdCom = new javax.swing.JTable();

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

        lblTit.setText("Titulo");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        PanFil.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(null);

        panDatHis.setBorder(javax.swing.BorderFactory.createTitledBorder("Histórico de Ventas"));
        panDatHis.setLayout(null);

        OptGrp.add(OptRepAnu);
        OptRepAnu.setFont(new java.awt.Font("SansSerif", 0, 11));
        OptRepAnu.setSelected(true);
        OptRepAnu.setText("Históricos Anual");
        OptRepAnu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OptRepAnuActionPerformed(evt);
            }
        });
        panDatHis.add(OptRepAnu);
        OptRepAnu.setBounds(10, 20, 110, 20);

        OptGrp.add(OptRepMes);
        OptRepMes.setFont(new java.awt.Font("SansSerif", 0, 11));
        OptRepMes.setText("Históricos Mensuales");
        OptRepMes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                OptRepMesItemStateChanged(evt);
            }
        });
        panDatHis.add(OptRepMes);
        OptRepMes.setBounds(150, 20, 130, 20);

        tblAnMe.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblAnMe);

        panDatHis.add(jScrollPane1);
        jScrollPane1.setBounds(10, 40, 280, 120);

        jPanel1.add(panDatHis);
        panDatHis.setBounds(4, 4, 300, 180);

        panDatFil.setLayout(null);

        OptGrp2.add(optTod);
        optTod.setFont(new java.awt.Font("SansSerif", 0, 11));
        optTod.setSelected(true);
        optTod.setText("Todos las Items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panDatFil.add(optTod);
        optTod.setBounds(0, 0, 330, 20);

        OptGrp2.add(optFil);
        optFil.setFont(new java.awt.Font("SansSerif", 0, 11));
        optFil.setText("Sólo los Items que cumplan el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panDatFil.add(optFil);
        optFil.setBounds(0, 20, 330, 20);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli.setText("Item:");
        panDatFil.add(lblCli);
        lblCli.setBounds(20, 100, 40, 20);

        txtCodItm.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodItmActionPerformed(evt);
            }
        });
        txtCodItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodItmFocusLost(evt);
            }
        });
        panDatFil.add(txtCodItm);
        txtCodItm.setBounds(90, 100, 50, 20);

        txtDesItm.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesItmActionPerformed(evt);
            }
        });
        txtDesItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesItmFocusLost(evt);
            }
        });
        panDatFil.add(txtDesItm);
        txtDesItm.setBounds(140, 100, 200, 20);

        butSol.setText(".."); // NOI18N
        butSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSolActionPerformed(evt);
            }
        });
        panDatFil.add(butSol);
        butSol.setBounds(340, 100, 20, 20);

        lbltipgrp.setFont(new java.awt.Font("SansSerif", 0, 11));
        lbltipgrp.setText("Tipo de grupo:");
        panDatFil.add(lbltipgrp);
        lbltipgrp.setBounds(20, 40, 100, 20);

        buttonGroup1.add(radPub);
        radPub.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        radPub.setText("Público");
        radPub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPubActionPerformed(evt);
            }
        });
        panDatFil.add(radPub);
        radPub.setBounds(120, 40, 70, 20);

        buttonGroup1.add(radPri);
        radPri.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        radPri.setText("Privado");
        radPri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPriActionPerformed(evt);
            }
        });
        panDatFil.add(radPri);
        radPri.setBounds(190, 40, 110, 20);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel3.setText("Grupo:"); // NOI18N
        panDatFil.add(jLabel3);
        jLabel3.setBounds(20, 60, 60, 20);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel2.setText("Clasificación:"); // NOI18N
        panDatFil.add(jLabel2);
        jLabel2.setBounds(20, 80, 70, 20);

        txtCodCla.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodCla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodClaActionPerformed(evt);
            }
        });
        txtCodCla.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodClaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodClaFocusLost(evt);
            }
        });
        panDatFil.add(txtCodCla);
        txtCodCla.setBounds(90, 80, 50, 20);

        txtCodGrp.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodGrpActionPerformed(evt);
            }
        });
        txtCodGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodGrpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodGrpFocusLost(evt);
            }
        });
        panDatFil.add(txtCodGrp);
        txtCodGrp.setBounds(90, 60, 50, 20);

        txtDesCla.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesCla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesClaActionPerformed(evt);
            }
        });
        txtDesCla.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesClaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesClaFocusLost(evt);
            }
        });
        panDatFil.add(txtDesCla);
        txtDesCla.setBounds(140, 80, 200, 20);

        txtDesGrp.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesGrpActionPerformed(evt);
            }
        });
        txtDesGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesGrpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesGrpFocusLost(evt);
            }
        });
        panDatFil.add(txtDesGrp);
        txtDesGrp.setBounds(140, 60, 200, 20);

        butCla.setText(".."); // NOI18N
        butCla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butClaActionPerformed(evt);
            }
        });
        panDatFil.add(butCla);
        butCla.setBounds(340, 80, 20, 20);

        butSol1.setText(".."); // NOI18N
        butSol1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSol1ActionPerformed(evt);
            }
        });
        panDatFil.add(butSol1);
        butSol1.setBounds(340, 60, 20, 20);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        jPanel5.setLayout(null);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel4.setText("Desde:");
        jPanel5.add(jLabel4);
        jLabel4.setBounds(12, 24, 40, 20);

        txtcodaltdes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcodaltdesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcodaltdesFocusLost(evt);
            }
        });
        jPanel5.add(txtcodaltdes);
        txtcodaltdes.setBounds(52, 24, 80, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel5.setText("Hasta:");
        jPanel5.add(jLabel5);
        jLabel5.setBounds(148, 28, 31, 15);

        txtcodalthas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcodalthasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcodalthasFocusLost(evt);
            }
        });
        jPanel5.add(txtcodalthas);
        txtcodalthas.setBounds(184, 24, 80, 20);

        panDatFil.add(jPanel5);
        jPanel5.setBounds(20, 120, 300, 50);

        butCon.setText("Consultar");
        butCon.setToolTipText("Consulatr Items a Reponer");
        butCon.setPreferredSize(new java.awt.Dimension(90, 20));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panDatFil.add(butCon);
        butCon.setBounds(320, 150, 50, 20);

        jPanel1.add(panDatFil);
        panDatFil.setBounds(310, 0, 370, 180);

        butCot.setText("Cot");
        butCot.setToolTipText("Generar las Cotizaciones de Compra");
        butCot.setPreferredSize(new java.awt.Dimension(90, 20));
        butCot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCotActionPerformed(evt);
            }
        });
        jPanel1.add(butCot);
        butCot.setBounds(430, 190, 31, 31);

        butOrdCom.setText("OC");
        butOrdCom.setToolTipText("Genrar Ordenes Compras.");
        butOrdCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butOrdComActionPerformed(evt);
            }
        });
        jPanel1.add(butOrdCom);
        butOrdCom.setBounds(460, 190, 31, 31);

        PanFil.add(jPanel1, java.awt.BorderLayout.CENTER);

        PanTabGen.setPreferredSize(new java.awt.Dimension(100, 75));
        PanTabGen.setLayout(null);

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTipDoc.setText("Tipo de Documento:");
        PanTabGen.add(lblTipDoc);
        lblTipDoc.setBounds(10, 10, 110, 20);

        lblBod.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblBod.setText("Bodega:");
        PanTabGen.add(lblBod);
        lblBod.setBounds(10, 30, 110, 20);

        lblCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodDoc.setText("Código del Documento:");
        PanTabGen.add(lblCodDoc);
        lblCodDoc.setBounds(460, 50, 110, 20);

        txtCodBod.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodBod.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtCodBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodActionPerformed(evt);
            }
        });
        txtCodBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodFocusLost(evt);
            }
        });
        PanTabGen.add(txtCodBod);
        txtCodBod.setBounds(135, 30, 70, 20);

        txtCodDoc.setBackground(objZafParSis.getColorCamposSistema());
        txtCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        PanTabGen.add(txtCodDoc);
        txtCodDoc.setBounds(580, 50, 90, 20);

        txtNomBod.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomBod.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtNomBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodActionPerformed(evt);
            }
        });
        txtNomBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodFocusLost(evt);
            }
        });
        PanTabGen.add(txtNomBod);
        txtNomBod.setBounds(205, 30, 230, 20);

        butBusBod.setText("jButton2");
        butBusBod.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusBodActionPerformed(evt);
            }
        });
        PanTabGen.add(butBusBod);
        butBusBod.setBounds(435, 30, 20, 20);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecDoc.setText("Fecha del Documento:");
        PanTabGen.add(lblFecDoc);
        lblFecDoc.setBounds(460, 10, 110, 20);

        txtNumDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        PanTabGen.add(txtNumDoc);
        txtNumDoc.setBounds(580, 30, 90, 20);

        lblNumDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNumDoc.setText("Número de Documento:");
        PanTabGen.add(lblNumDoc);
        lblNumDoc.setBounds(460, 30, 120, 20);

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
        PanTabGen.add(txtDesCodTitpDoc);
        txtDesCodTitpDoc.setBounds(135, 10, 70, 20);

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
        PanTabGen.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(205, 10, 230, 20);

        butTipDoc.setText(".."); // NOI18N
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        PanTabGen.add(butTipDoc);
        butTipDoc.setBounds(435, 10, 20, 20);

        lblMesRep.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblMesRep.setText("Meses a reponer:");
        PanTabGen.add(lblMesRep);
        lblMesRep.setBounds(10, 50, 100, 20);

        spnNumMesRep.setRequestFocusEnabled(false);
        PanTabGen.add(spnNumMesRep);
        spnNumMesRep.setBounds(135, 50, 70, 20);

        PanFil.add(PanTabGen, java.awt.BorderLayout.NORTH);

        panObs.setPreferredSize(new java.awt.Dimension(100, 65));
        panObs.setLayout(new java.awt.BorderLayout());

        panLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 12));
        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Observación 1:"); // NOI18N
        lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs1.setPreferredSize(new java.awt.Dimension(92, 15));
        panLbl.add(lblObs1);

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 12));
        lblObs2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs2.setText("Observación 2:"); // NOI18N
        lblObs2.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs2.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs2.setPreferredSize(new java.awt.Dimension(92, 15));
        lblObs2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        panLbl.add(lblObs2);

        panObs.add(panLbl, java.awt.BorderLayout.WEST);

        panTxa.setLayout(new java.awt.GridLayout(2, 1, 0, 1));

        txtObs1.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtObs1.setLineWrap(true);
        spnObs3.setViewportView(txtObs1);

        panTxa.add(spnObs3);

        txtObs2.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtObs2.setLineWrap(true);
        spnObs4.setViewportView(txtObs2);

        panTxa.add(spnObs4);

        panObs.add(panTxa, java.awt.BorderLayout.CENTER);

        PanFil.add(panObs, java.awt.BorderLayout.SOUTH);

        TabGen.addTab("Filtro", PanFil);

        PanRep.setLayout(new java.awt.BorderLayout());

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
        scrtbl.setViewportView(tblDat);

        PanRep.add(scrtbl, java.awt.BorderLayout.CENTER);

        TabGen.addTab("Reporte", PanRep);

        panPrv.setLayout(new java.awt.BorderLayout());

        tblPrv.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblPrv);

        panPrv.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        TabGen.addTab("Proveedores", panPrv);

        panCotEmi.setLayout(new java.awt.BorderLayout());

        tblCotEmi.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblCotEmi);

        panCotEmi.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        TabGen.addTab("Cotizaciones Emitidas", panCotEmi);

        panCorRec.setLayout(new java.awt.BorderLayout());

        tblCotRec.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblCotRec);

        panCorRec.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        TabGen.addTab("Cotizaciones recibidas", panCorRec);

        panordcom.setLayout(new java.awt.BorderLayout());

        tblOrdCom.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblOrdCom);

        panordcom.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        TabGen.addTab("Ordenes de Compra", panordcom);

        panCen.add(TabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
         exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    
     
     
    private void exitForm() 
    {
        String strTit, strMsg;
       // javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
     }    
      
    
    
    
    
    
  
     
     
    

private boolean cargarDatosMensuales(){
 boolean blnRes=false;
 String strAux="";
 String strAuxInv="", strConInv="";
 String sql="";
 int intAnoDiv=0;
 try{

    objTooBar.confirgurarTblDatMen(1);
     
  for(int i=0; i< tblAnMe.getRowCount(); i++){
    if(tblAnMe.getValueAt(i,INT_TBL_CHK).toString().equals("true")){
     intAnoDiv+=12;
  }}

    if (txtCodItm.getText().length()>0)
        strAux+=" AND a.co_itm=" + txtCodItm.getText();
    if (txtcodaltdes.getText().length()>0 || txtcodalthas.getText().length()>0)
        strAux+=" AND ((LOWER(a.tx_codAlt) BETWEEN '" + txtcodaltdes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtcodalthas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a.tx_codAlt) LIKE '" + txtcodalthas.getText().replaceAll("'", "''").toLowerCase() + "%')";


   
    if(objZafParSis.getCodigoUsuario()!=1){
        strAuxInv = ",CASE WHEN (" +
        " (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
        " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
        " AND co_tipdoc= 2   AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
        " ))) THEN 'S' ELSE 'N' END  as isterL";

        strConInv=" WHERE isterL='S' ";
    }

      if(txtCodCla.getText().trim().length()>0){
         if(strConInv.equals("")) strConInv += " WHERE co_cla="+txtCodCla.getText()+" ";
         else  strConInv += " AND co_cla="+txtCodCla.getText()+" ";
      }



    sql="SELECT * FROM ( SELECT * FROM ( SELECT * "+strAuxInv+" FROM ("+
    " select b1.co_cla, a.co_itm, a.tx_codalt, a.tx_nomitm, sum(b.nd_stkact) as nd_stkact , var.tx_descor as unidad " +
    "  , a.nd_promenvtaman, a.nd_stkmin, sum(b.nd_promenvtaman) as  nd_promenvtamanbod, sum(b.nd_stkmin) as nd_stkminbod " +
    "  from tbm_inv as a "+
    " inner join tbm_var as var on (var.co_reg=a.co_uni ) "+
    " inner join tbm_invbod as b on (b.co_emp=a.co_emp and b.co_itm=a.co_itm) "+
    "  LEFT JOIN  tbr_invcla AS b1 ON (b1.co_emp=a.co_emp and b1.co_itm=a.co_itm and b1.st_reg='A' )  "+
    " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.st_reg='A'  ";

     if(!txtCodBod.getText().equals(""))
       sql += " and b.co_bod="+txtCodBod.getText();


                sql += strAux;

               sql +=" group by b1.co_cla, a.co_itm, a.tx_codalt, a.tx_nomitm, var.tx_descor, a.nd_promenvtaman , a.nd_stkmin "+
                       " order by a.co_itm "+
                " ) as x  ) AS x  "+strConInv+" ) AS x ";

     System.out.println(" >>> "+ sql );

    int intx=1;
    for(int i=0; i< tblAnMe.getRowCount(); i++){
       if(tblAnMe.getValueAt(i,INT_TBL_CHK).toString().equals("true")){

          sql+=" LEFT JOIN ( " +
          " select co_itm as co_itm"+intx+",  sum(a.nd_univen) as unidades"+intx+"   , sum(a.ne_numVec) as veces"+intx+"    , count(distinct(a.ne_mes)) as numeses"+intx+"  " +
          " FROM  tbm_venMenInvBod AS a  " +
          " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+"  and a.ne_ani ="+tblAnMe.getValueAt(i,INT_TBL_ANI).toString()+" and a.ne_mes="+tblAnMe.getValueAt(i,INT_TBL_CODMES).toString()+" "+
          " GROUP BY  a.co_itm, a.co_bod,  a.ne_nummes  " +
          " ) as y"+intx+" on (y"+intx+".co_itm"+intx+"=x.co_itm) ";
          intx++;
     }}

    System.out.println(" >>> "+ sql );



                double dblProMan=0,dblStkMin=0;
                Connection con;
                Statement stm,stm2;
                ResultSet rst,rst2;
                con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
                if(con!=null)
                {
                   stm=con.createStatement();
                   stm2=con.createStatement();
                   rst=stm.executeQuery(sql);
                   java.util.Vector vecData = new java.util.Vector();
                   while(rst.next()){
                         java.util.Vector vecReg = new java.util.Vector();
                         vecReg.add(INT_TBL2_LINEA, "");
                         vecReg.add(INT_TBL2_COSIS, rst.getString("co_itm") );
                         vecReg.add(INT_TBL2_COALT, rst.getString("tx_codalt") );
                         vecReg.add(INT_TBL2_NOMBR, rst.getString("tx_nomitm") );
                         vecReg.add(INT_TBL2_UNIMED,rst.getString("unidad") );


                         double dlbTotUni=0;
                         double dlbTotVec=0;
                         double dlbTotNMe=0;


                         int INT_DET_TBL2= INT_TBL2_UNIMED;
                            intx=1;
                          for(int i=0; i< tblAnMe.getRowCount(); i++){
                            if(tblAnMe.getValueAt(i,INT_TBL_CHK).toString().equals("true")){

                                     INT_DET_TBL2++;
                                     vecReg.add(INT_DET_TBL2, rst.getString("unidades"+intx+"") );
                                     INT_DET_TBL2++;
                                     vecReg.add(INT_DET_TBL2, rst.getString("veces"+intx+"") );
                                     INT_DET_TBL2++;
                                     vecReg.add(INT_DET_TBL2, rst.getString("numeses"+intx+"")  );

                                     dlbTotUni+=rst.getDouble("unidades"+intx+"");
                                     dlbTotVec+=rst.getDouble("veces"+intx+"");
                                     dlbTotNMe+=rst.getDouble("numeses"+intx+"");

                                     intx++;
                            }}


                         INT_DET_TBL2++;
                         vecReg.add(INT_TBL2_TOTUNI, " "+dlbTotUni  );
                         INT_DET_TBL2++;
                         vecReg.add(INT_TBL2_TOTVEC, " "+dlbTotVec );
                         INT_DET_TBL2++;
                         vecReg.add(INT_TBL2_TOTMES, " "+dlbTotNMe );

                         INT_DET_TBL2++;
                         vecReg.add(INT_TBL2_STKACT, rst.getString("nd_stkact") );

                         INT_DET_TBL2++;
                         vecReg.add(INT_TBL2_NUMMRE, spnNumMesRep.getModel().getValue().toString() );
                         

                         dlbTotUni=dlbTotUni/intAnoDiv;
                         INT_DET_TBL2++;
                         vecReg.add(INT_TBL2_PROCAL, " "+dlbTotUni );
                         INT_DET_TBL2++;

                          if(!txtCodBod.getText().equals("")) {
                              dblProMan=rst.getDouble("nd_promenvtamanbod");
                              dblStkMin=rst.getDouble("nd_stkminbod");
                          }else {
                              dblProMan=rst.getDouble("nd_promenvtaman");
                              dblStkMin=rst.getDouble("nd_stkmin");
                          }


                         vecReg.add(INT_TBL2_PROMAN, " "+dblProMan );
                         INT_DET_TBL2++;
                         vecReg.add(INT_TBL2_STKMIN, " "+dblStkMin  );
                         INT_DET_TBL2++;

                      
                         if(dblProMan==0 &&  dblStkMin ==0 )
                           dlbTotUni=(dlbTotUni*Integer.parseInt(spnNumMesRep.getModel().getValue().toString()))-rst.getDouble("nd_stkact");
                         else if(dblStkMin==0 && dblProMan!=0 )
                             dlbTotUni=(dblProMan*Integer.parseInt(spnNumMesRep.getModel().getValue().toString()))-rst.getDouble("nd_stkact");
                         else if(dblProMan ==0  && dblStkMin!=0 )
                               dlbTotUni=dblStkMin - rst.getDouble("nd_stkact");
                         else if(dblProMan !=0  && dblStkMin!=0 )
                               dlbTotUni=dblStkMin - rst.getDouble("nd_stkact");


                         if(dlbTotUni<=0) vecReg.add(INT_TBL2_REPOSI, " ");
                         else vecReg.add(INT_TBL2_REPOSI, " "+dlbTotUni );

                         if(dlbTotUni<=0) vecReg.add(INT_TBL2_CANCOM, " ");
                         else vecReg.add(INT_TBL2_CANCOM, " "+dlbTotUni );

                         vecReg.add(INT_TBL2_BUTREP, "..");
                         vecReg.add(INT_TBL2_PRVELE, "");
                         vecReg.add(INT_TBL2_COTGEN, "");
                         vecReg.add(INT_TBL2_COTING, "");
                         vecReg.add(INT_TBL2_NTCCAN, "");
                         vecReg.add(INT_TBL2_NTCOMP, "");
                         vecReg.add(INT_TBL2_CTNCOM, "");
                         vecReg.add(INT_TBL2_CODREG, "" );
                         vecReg.add(INT_TBL2_CTNCOMORI, "" );
                         vecReg.add(INT_TBL2_CANCOMORI, "" );





                        if(dlbTotUni > 0)
                          vecData.add(vecReg);

                   }
                    objTblMod2.setData(vecData);
                    tblDat .setModel(objTblMod2);
                 rst.close();
                 rst=null;
                 stm.close();
                 stm=null;
                 con.close();
                 con=null;
                 blnRes=true;


 }}catch (java.sql.SQLException e) {  objUti.mostrarMsgErr_F1(this, e); }
   catch (Exception e)  {  objUti.mostrarMsgErr_F1(this, e); }
return blnRes;
}


     
     

private class ButBod extends Librerias.ZafTableColBut.ZafTableColBut_uni{
    public ButBod(javax.swing.JTable tbl, int intIdx){
        super(tbl,intIdx, "Guía de remisión.");
    }
    public void butCLick() {
      int intCol = tblDat.getSelectedRow();
      String strCodItm = ( tblDat.getValueAt(intCol,  INT_TBL2_COSIS  )==null?"":tblDat.getValueAt(intCol,  INT_TBL2_COSIS  ).toString());
      String strCodReg = ( tblDat.getValueAt(intCol,  INT_TBL2_CODREG  )==null?"":tblDat.getValueAt(intCol,  INT_TBL2_CODREG  ).toString());

      String strCanNumCom=(tblDat.getValueAt(intCol, INT_TBL2_CTNCOM)==null?"0":tblDat.getValueAt(intCol, INT_TBL2_CTNCOM).toString());
      String strCanRep=(tblDat.getValueAt(intCol, INT_TBL2_CANCOM)==null?"":tblDat.getValueAt(intCol, INT_TBL2_CANCOM).toString());
      double dblCanNumCom = objUti.redondear( Double.parseDouble((strCanNumCom.equals("")?"0":strCanNumCom)),4);
      double dblCanRep = objUti.redondear( Double.parseDouble((strCanRep.equals("")?"0":strCanRep)),4);
      double dblCanTotRep=dblCanRep - dblCanNumCom;
      dblCanTotRep= objUti.redondear(dblCanTotRep, 4);

      llamarVentanaFac(strCodItm, strCodReg, dblCanTotRep );
    }
}

private void llamarVentanaFac(String strCodItm, String strCodReg, double dblCanTotRep ){
  String strSql="";

     ZafCom58_01 obj = new  ZafCom58_01(javax.swing.JOptionPane.getFrameForComponent(this), true,  objZafParSis, strCodItm, txtCodTipDoc.getText(), txtCodDoc.getText(), strCodReg, dblCanTotRep  );
     obj.show();
 
     if(obj.acepta()){
       refrescaDatosDet(0);
     }
    obj.dispose();
    obj=null;
}

private boolean refrescaDatosDet(int intTip){
    boolean blnRes=false;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSql="";
    try{
       abrirCon();
       if(CONN_GLO!=null){
          stmLoc=CONN_GLO.createStatement();

          java.util.Vector vecData = new java.util.Vector();
          objTooBar.confirgurarTblDatAnu(2);

          strSql="SELECT a.co_itm, a2.tx_codalt, a2.tx_nomitm, var.tx_descor, a.nd_canrep, a.ne_numtotprvele, a.ne_numtotcotgen, " +
          " a.ne_numtotcoting, a.ne_numtotcotcan, a.nd_cantotcom, a.nd_cantotnuncom , a.co_reg, a.nd_cancom " +
          " FROM tbm_detrepinvprv AS a " +
          " inner join tbm_inv as a2 on (a2.co_emp=a.co_emp and a2.co_itm=a.co_itm) " +
          " left join tbm_var as var on (var.co_reg=a2.co_uni) " +
          " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a.co_tipdoc="+txtCodTipDoc.getText()+" " +
          " and a.co_doc="+txtCodDoc.getText()+" ORDER BY a.co_reg ";

          rstLoc=stmLoc.executeQuery(strSql);
          while(rstLoc.next()){

           java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL2_LINEA, "");
            vecReg.add(INT_TBL2_COSIS, rstLoc.getString("co_itm") );
            vecReg.add(INT_TBL2_COALT, rstLoc.getString("tx_codalt") );
            vecReg.add(INT_TBL2_NOMBR, rstLoc.getString("tx_nomitm") );
            vecReg.add(INT_TBL2_UNIMED, rstLoc.getString("tx_descor") );
            vecReg.add(INT_TBL2_TOTUNI, "");
            vecReg.add(INT_TBL2_TOTVEC, "");
            vecReg.add(INT_TBL2_TOTMES, "");
            vecReg.add(INT_TBL2_STKACT, "");
            vecReg.add(INT_TBL2_NUMMRE, "");
            vecReg.add(INT_TBL2_PROCAL, "");
            vecReg.add(INT_TBL2_PROMAN, "");
            vecReg.add(INT_TBL2_STKMIN, "");
            vecReg.add(INT_TBL2_REPOSI, rstLoc.getString("nd_canrep") );
            vecReg.add(INT_TBL2_CANCOM, rstLoc.getString("nd_cancom") );
            vecReg.add(INT_TBL2_BUTREP, "..");
            vecReg.add(INT_TBL2_PRVELE, rstLoc.getString("ne_numtotprvele") );
            vecReg.add(INT_TBL2_COTGEN, rstLoc.getString("ne_numtotcotgen") );
            vecReg.add(INT_TBL2_COTING, rstLoc.getString("ne_numtotcoting") );
            vecReg.add(INT_TBL2_NTCCAN, rstLoc.getString("ne_numtotcotcan") );
            vecReg.add(INT_TBL2_NTCOMP, rstLoc.getString("nd_cantotcom") );
            vecReg.add(INT_TBL2_CTNCOM, rstLoc.getString("nd_cantotnuncom") );
            vecReg.add(INT_TBL2_CODREG, rstLoc.getString("co_reg") );
            vecReg.add(INT_TBL2_CTNCOMORI, rstLoc.getString("nd_cantotnuncom") );
            vecReg.add(INT_TBL2_CANCOMORI, rstLoc.getString("nd_cancom") );

           vecData.add(vecReg);

          }
          rstLoc.close();
          rstLoc=null;

          objTblMod2.setData(vecData);
          tblDat.setModel(objTblMod2);

          stmLoc.close();
          stmLoc=null;

          if(intTip==1){
           objTooBar.cargarTabPrv(CONN_GLO, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()),  Integer.parseInt(txtCodDoc.getText())  );
           objTooBar.cargarTabCotEmi(CONN_GLO, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()),  Integer.parseInt(txtCodDoc.getText())  );
           objTooBar.cargarTabOrdCom(CONN_GLO, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()),  Integer.parseInt(txtCodDoc.getText())  );
          }


          CerrarCon();

  }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}



private boolean cargarDatosAnuales(){
 boolean blnRes=false;
  int intAnoDiv=0;
 try{

     objTooBar.confirgurarTblDatAnu(1);

     for(int i=0; i< tblAnMe.getRowCount(); i++){
      if(tblAnMe.getValueAt(i,INT_TBL_CHK).toString().equals("true")){
         intAnoDiv+=12;
        }
    }

    String strAux="";
    if (txtCodItm.getText().length()>0)
        strAux+=" AND a.co_itm=" + txtCodItm.getText();
    if (txtcodaltdes.getText().length()>0 || txtcodalthas.getText().length()>0)
        strAux+=" AND ((LOWER(a.tx_codAlt) BETWEEN '" + txtcodaltdes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtcodalthas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a.tx_codAlt) LIKE '" + txtcodalthas.getText().replaceAll("'", "''").toLowerCase() + "%')";

    
    String strAuxInv="", strConInv="";
    if(objZafParSis.getCodigoUsuario()!=1){
        strAuxInv = ",CASE WHEN (" +
        " (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
        " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
        " AND co_tipdoc= 2   AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
        " ))) THEN 'S' ELSE 'N' END  as isterL";

        strConInv=" WHERE isterL='S' ";
    }

      if(txtCodCla.getText().trim().length()>0){
         if(strConInv.equals("")) strConInv += " WHERE co_cla="+txtCodCla.getText()+" ";
         else  strConInv += " AND co_cla="+txtCodCla.getText()+" ";
      }
    

               String sql="";

                sql="SELECT * FROM ( SELECT * FROM ( SELECT * "+strAuxInv+" FROM ("+
                       " select b1.co_cla, a.co_itm, a.tx_codalt, a.tx_nomitm, sum(b.nd_stkact) as nd_stkact , var.tx_descor as unidad " +
                       "  , a.nd_promenvtaman, a.nd_stkmin, sum(b.nd_promenvtaman) as  nd_promenvtamanbod, sum(b.nd_stkmin) as nd_stkminbod " +
                       "  from tbm_inv as a "+
                       " inner join tbm_var as var on (var.co_reg=a.co_uni ) "+
                       " inner join tbm_invbod as b on (b.co_emp=a.co_emp and b.co_itm=a.co_itm) "+
                       "  LEFT JOIN  tbr_invcla AS b1 ON (b1.co_emp=a.co_emp and b1.co_itm=a.co_itm  and b1.st_reg='A' )  "+
                       " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.st_reg='A'   ";

                if(!txtCodBod.getText().equals(""))
                   sql += " and b.co_bod="+txtCodBod.getText();


                sql += strAux;

                sql +=" group by b1.co_cla, a.co_itm, a.tx_codalt, a.tx_nomitm, var.tx_descor, a.nd_promenvtaman , a.nd_stkmin "+
                       " order by a.co_itm "+
                " ) as x  ) AS x  "+strConInv+" ) AS x ";


                System.out.println(" >>> "+ sql );




                int intx=1;
                for(int i=0; i< tblAnMe.getRowCount(); i++){
                   if(tblAnMe.getValueAt(i,INT_TBL_CHK).toString().equals("true")){

                      sql+=" LEFT JOIN ( " +
                      " select co_itm as co_itm"+intx+",  sum(a.nd_univen) as unidades"+intx+"   , sum(a.ne_numVec) as veces"+intx+"    , count(distinct(a.ne_mes))  as numeses"+intx+"  " +
                      " FROM  tbm_venMenInvBod AS a  " +
                      " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+"  and  a.ne_ani ="+tblAnMe.getValueAt(i,INT_TBL_ANI).toString()+" "+
                      " GROUP BY a.ne_ani, a.co_itm, a.co_bod,  a.ne_nummes  " +
                      " ) as y"+intx+" on (y"+intx+".co_itm"+intx+"=x.co_itm) ";
                      intx++;
                 }}

                System.out.println(" >>> "+ sql );


                double dblProMan=0, dblStkMin=0;
                Connection con;
                Statement stm,stm2;
                ResultSet rst,rst2;
                con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
                if(con!=null)
                {
                   stm=con.createStatement();
                   stm2=con.createStatement();
                   rst=stm.executeQuery(sql);
                   java.util.Vector vecData = new java.util.Vector();
                   while(rst.next()){
                         java.util.Vector vecReg = new java.util.Vector();
                         vecReg.add(INT_TBL2_LINEA, "");
                         vecReg.add(INT_TBL2_COSIS, rst.getString("co_itm") );
                         vecReg.add(INT_TBL2_COALT, rst.getString("tx_codalt") );
                         vecReg.add(INT_TBL2_NOMBR, rst.getString("tx_nomitm") );
                         vecReg.add(INT_TBL2_UNIMED,rst.getString("unidad") );
                        

                         double dlbTotUni=0;
                         double dlbTotVec=0;
                         double dlbTotNMe=0;


                         int INT_DET_TBL2= INT_TBL2_UNIMED;
                            intx=1;
                          for(int i=0; i< tblAnMe.getRowCount(); i++){
                            if(tblAnMe.getValueAt(i,INT_TBL_CHK).toString().equals("true")){

                                     INT_DET_TBL2++;
                                     vecReg.add(INT_DET_TBL2, rst.getString("unidades"+intx+"") );
                                     INT_DET_TBL2++;
                                     vecReg.add(INT_DET_TBL2, rst.getString("veces"+intx+"") );
                                     INT_DET_TBL2++;
                                     vecReg.add(INT_DET_TBL2, rst.getString("numeses"+intx+"")  );

                                     dlbTotUni+=rst.getDouble("unidades"+intx+"");
                                     dlbTotVec+=rst.getDouble("veces"+intx+"");
                                     dlbTotNMe+=rst.getDouble("numeses"+intx+"");

                                     intx++;
                            }}


                         INT_DET_TBL2++;
                         vecReg.add(INT_TBL2_TOTUNI, " "+dlbTotUni  );
                         INT_DET_TBL2++;
                         vecReg.add(INT_TBL2_TOTVEC, " "+dlbTotVec );
                         INT_DET_TBL2++;
                         vecReg.add(INT_TBL2_TOTMES, " "+dlbTotNMe );


                         INT_DET_TBL2++;
                         vecReg.add(INT_TBL2_STKACT, rst.getString("nd_stkact") );

                         INT_DET_TBL2++;
                         vecReg.add(INT_TBL2_NUMMRE, spnNumMesRep.getModel().getValue().toString() );
                         INT_DET_TBL2++;

                         dlbTotUni=dlbTotUni/intAnoDiv;
                         INT_DET_TBL2++;


                         if(intAnoDiv==0)    vecReg.add(INT_TBL2_PROCAL, "0" );
                         else   vecReg.add(INT_TBL2_PROCAL, " "+dlbTotUni );


                         if(!txtCodBod.getText().equals("")) {
                              dblProMan=rst.getDouble("nd_promenvtamanbod");
                              dblStkMin=rst.getDouble("nd_stkminbod");
                          }else {
                              dblProMan=rst.getDouble("nd_promenvtaman");
                              dblStkMin=rst.getDouble("nd_stkmin");
                          }


                         vecReg.add(INT_TBL2_PROMAN, " "+ dblProMan  );
                         INT_DET_TBL2++;
                         vecReg.add(INT_TBL2_STKMIN, " "+dblStkMin  );
                         INT_DET_TBL2++;

                     
                       
                         if(dblProMan==0 &&  dblStkMin ==0 )
                           dlbTotUni=(dlbTotUni*Integer.parseInt(spnNumMesRep.getModel().getValue().toString()))-rst.getDouble("nd_stkact");
                         else if(dblStkMin==0 && dblProMan!=0 )
                             dlbTotUni=(dblProMan*Integer.parseInt(spnNumMesRep.getModel().getValue().toString()))-rst.getDouble("nd_stkact");
                         else if(dblProMan ==0  && dblStkMin!=0 )
                               dlbTotUni=dblStkMin - rst.getDouble("nd_stkact");
                         else if(dblProMan !=0  && dblStkMin!=0 )
                               dlbTotUni=dblStkMin - rst.getDouble("nd_stkact");



                         if(dlbTotUni<=0) vecReg.add(INT_TBL2_REPOSI, " ");
                         else vecReg.add(INT_TBL2_REPOSI, " "+dlbTotUni );

                         if(dlbTotUni<=0) vecReg.add(INT_TBL2_CANCOM, " ");
                         else vecReg.add(INT_TBL2_CANCOM, " "+dlbTotUni );


                         vecReg.add(INT_TBL2_BUTREP, "..");
                         vecReg.add(INT_TBL2_PRVELE, "");
                         vecReg.add(INT_TBL2_COTGEN, "");
                         vecReg.add(INT_TBL2_COTING, "");
                         vecReg.add(INT_TBL2_NTCCAN, "");
                         vecReg.add(INT_TBL2_NTCOMP, "");
                         vecReg.add(INT_TBL2_CTNCOM, "");
                         vecReg.add(INT_TBL2_CODREG, "" );
                         vecReg.add(INT_TBL2_CTNCOMORI, "" );
                         vecReg.add(INT_TBL2_CANCOMORI ,"");

                        if(dlbTotUni > 0)
                          vecData.add(vecReg);


                   }
                    objTblMod2.setData(vecData);
                    tblDat .setModel(objTblMod2);

                 //   objTblTotDocVen.calcularTotales();


                    blnRes=true;
                 rst.close();
                 rst=null;
                 stm.close();
                 stm=null;
                 con.close();
                 con=null;
                }
        } catch (java.sql.SQLException e) {  objUti.mostrarMsgErr_F1(this, e); }
          catch (Exception e)  {  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
        }

    
    
    private void OptRepMesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_OptRepMesItemStateChanged
        // TODO add your handling code here:
        cargarAnioMes();
    }//GEN-LAST:event_OptRepMesItemStateChanged

      
    
    
    
    
    
  
    
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();

        if(intValBus==1){

            txtCodTipDoc.setText(""+intCodTipDoc);
            txtCodBod.setText(""+intCodBod);

            objTooBar._consultar(intCodLoc, intCodDoc);

            objTooBar.setEstado('w');    
        }



    }//GEN-LAST:event_formInternalFrameOpened

    private void OptRepAnuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OptRepAnuActionPerformed
        // TODO add your handling code here:
        cargarAnio();
    }//GEN-LAST:event_OptRepAnuActionPerformed

    private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
        // TODO add your handling code here:
        txtCodBod.transferFocus();
}//GEN-LAST:event_txtCodBodActionPerformed

    private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
        // TODO add your handling code here:
        strCodBod=txtCodBod.getText();
        txtCodBod.selectAll();
}//GEN-LAST:event_txtCodBodFocusGained

    private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
        // TODO add your handling code here:
        if (!txtCodBod.getText().equalsIgnoreCase(strCodBod)) {
            if (txtCodBod.getText().equals("")) {
                txtCodBod.setText("");
                txtNomBod.setText("");
            }else
                BuscarBod("a.co_bod",txtCodBod.getText(),0);
        }else
            txtCodBod.setText(strCodBod);
}//GEN-LAST:event_txtCodBodFocusLost

    private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
        // TODO add your handling code here:
        txtNomBod.transferFocus();
}//GEN-LAST:event_txtNomBodActionPerformed

    private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
        // TODO add your handling code here:
        strNomBod=txtNomBod.getText();
        txtNomBod.selectAll();
}//GEN-LAST:event_txtNomBodFocusGained

    private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
        // TODO add your handling code here:
        if (!txtNomBod.getText().equalsIgnoreCase(strNomBod)) {
            if (txtNomBod.getText().equals("")) {
                txtCodBod.setText("");
                txtNomBod.setText("");
            }else
                BuscarBod("a.tx_nom",txtNomBod.getText(),1);
        }else
            txtNomBod.setText(strNomBod);
}//GEN-LAST:event_txtNomBodFocusLost

    private void butBusBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusBodActionPerformed
        // TODO add your handling code here:
        objVenConBodUsr.setTitle("Listado de Bodegas");
        objVenConBodUsr.setCampoBusqueda(1);
        objVenConBodUsr.show();
        if (objVenConBodUsr.getSelectedButton()==objVenConBodUsr.INT_BUT_ACE) {
            txtCodBod.setText(objVenConBodUsr.getValueAt(1));
            txtNomBod.setText(objVenConBodUsr.getValueAt(2));
            strCodBod=objVenConBodUsr.getValueAt(1);
            strNomBod=objVenConBodUsr.getValueAt(2);
        }
}//GEN-LAST:event_butBusBodActionPerformed

    private void txtDesCodTitpDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocActionPerformed
        // TODO add your handling code here:
        txtDesCodTitpDoc.transferFocus();
}//GEN-LAST:event_txtDesCodTitpDocActionPerformed

    private void txtDesCodTitpDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocFocusGained
        // TODO add your handling code here:
        strDesCorTipDoc=txtDesCodTitpDoc.getText();
        txtDesCodTitpDoc.selectAll();
}//GEN-LAST:event_txtDesCodTitpDocFocusGained

    private void txtDesCodTitpDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocFocusLost
        // TODO add your handling code here:

        if (!txtDesCodTitpDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCodTitpDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCodTitpDoc.setText("");
                txtDesLarTipDoc.setText("");
            }else
                BuscarTipDoc("a.tx_descor",txtDesCodTitpDoc.getText(),1);
        }else
            txtDesCodTitpDoc.setText(strDesCorTipDoc);
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

    private void txtCodItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodItmActionPerformed
        // TODO add your handling code here:
        txtCodItm.transferFocus();
}//GEN-LAST:event_txtCodItmActionPerformed

    private void txtCodItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodItmFocusGained
        // TODO add your handling code here:
        strCodItm=txtCodItm.getText();
        txtCodItm.selectAll();
}//GEN-LAST:event_txtCodItmFocusGained

    private void txtCodItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodItmFocusLost
        // TODO add your handling code here:
        if (!txtCodItm.getText().equalsIgnoreCase(strCodItm)) {
            if(txtCodItm.getText().equals("")) {
                txtCodItm.setText("");
                txtDesItm.setText("");
            }else
                BuscarItm("co_itm",txtCodItm.getText(),0);
        }else
            txtCodItm.setText(strCodItm);
}//GEN-LAST:event_txtCodItmFocusLost

    private void txtDesItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesItmActionPerformed
        // TODO add your handling code here:
        txtDesItm.transferFocus();
}//GEN-LAST:event_txtDesItmActionPerformed

    private void txtDesItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesItmFocusGained
        // TODO add your handling code here:
        strDesItm=txtDesItm.getText();
        txtDesItm.selectAll();
}//GEN-LAST:event_txtDesItmFocusGained

    private void txtDesItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesItmFocusLost
        // TODO add your handling code here:
        if (!txtDesItm.getText().equalsIgnoreCase(strDesItm)) {
            if(txtDesItm.getText().equals("")) {
                txtCodItm.setText("");
                txtDesItm.setText("");
            }else
                BuscarItm("tx_nomitm",txtDesItm.getText(),3);
        }else
            txtDesItm.setText(strDesItm);
}//GEN-LAST:event_txtDesItmFocusLost

    private void butSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSolActionPerformed
        // TODO add your handling code here:
        objVenConItm.setTitle("Listado de Items");
        objVenConItm.setCampoBusqueda(1);
        objVenConItm.show();
        if (objVenConItm.getSelectedButton()==objVenConItm.INT_BUT_ACE) {
            txtCodItm.setText(objVenConItm.getValueAt(1));
            txtDesItm.setText(objVenConItm.getValueAt(4));
            optFil.setSelected(true);

        }
}//GEN-LAST:event_butSolActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected()) {


            txtCodItm.setText("");
            txtDesItm.setText("");
            strCodItm="";
            strDesItm="";

            txtcodaltdes.setText("");
            txtcodalthas.setText("");
            



        }
}//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:


        txtCodItm.setText("");
        txtDesItm.setText("");
        strCodItm="";
        strDesItm="";

        txtCodCla.setText("");
        txtDesCla.setText("");
        strCodCla="";
        strDesCla="";  

        txtcodaltdes.setText("");
        txtcodalthas.setText("");
        

    }//GEN-LAST:event_optTodActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:


    }//GEN-LAST:event_optFilItemStateChanged

    private void txtcodaltdesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodaltdesFocusGained
        // TODO add your handling code here:
        txtcodaltdes.selectAll();
}//GEN-LAST:event_txtcodaltdesFocusGained

    private void txtcodaltdesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodaltdesFocusLost
        // TODO add your handling code here:

        if (txtcodaltdes.getText().length()>0) {
            optFil.setSelected(true);
            if (txtcodalthas.getText().length()==0)
                txtcodalthas.setText(txtcodaltdes.getText());
        }
    }//GEN-LAST:event_txtcodaltdesFocusLost

    private void txtcodalthasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodalthasFocusGained
        // TODO add your handling code here:
        txtcodalthas.selectAll();
}//GEN-LAST:event_txtcodalthasFocusGained

    private void txtcodalthasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodalthasFocusLost
        // TODO add your handling code here:
        if ( txtcodalthas.getText().length()>0)
            optFil.setSelected(true);
}//GEN-LAST:event_txtcodalthasFocusLost

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:

       if( Integer.parseInt(spnNumMesRep.getModel().getValue().toString()) > 0 ){

        if(OptRepAnu.isSelected()){
          if( cargarDatosAnuales() )
             TabGen.setSelectedIndex(1);
        }

        if(OptRepMes.isSelected()){
          if(cargarDatosMensuales())
          TabGen.setSelectedIndex(1);
        }

       }else{
           MensajeInf("NÚMERO DE MESES A REPONER TIENE QUE SER MAYOR A 0 ");
       }

    }//GEN-LAST:event_butConActionPerformed

    private void butCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCotActionPerformed
        // TODO add your handling code here:

        if(generarCotzaciones()){

            refrescaDatosDet(1);


            MensajeInf("Las Cotizaciones se Procesaron con éxito... ");
        }
    }//GEN-LAST:event_butCotActionPerformed

    private void butOrdComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butOrdComActionPerformed
        // TODO add your handling code here:

        stbDocRelEmpLoc =new StringBuffer();
        intDocRelEmpLoc=0;
        strNumOcGen="";
        
       if( generarOrdCom() ){

           refrescaDatosDet(1);

           MensajeInf("Las Ordenes de Compra "+strNumOcGen+" \n Procesaron con éxito... ");
         
       }
        stbDocRelEmpLoc=null;
        
    }//GEN-LAST:event_butOrdComActionPerformed

    private void radPubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPubActionPerformed
        // TODO add your handling code here:
        txtCodGrp.setText("");
        txtDesGrp.setText("");
        txtCodCla.setText("");
        txtDesCla.setText("");
}//GEN-LAST:event_radPubActionPerformed

    private void radPriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPriActionPerformed
        // TODO add your handling code here:
        txtCodGrp.setText("");
        txtDesGrp.setText("");
        txtCodCla.setText("");
        txtDesCla.setText("");
}//GEN-LAST:event_radPriActionPerformed

    private void txtCodClaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodClaActionPerformed
        // TODO add your handling code here:
        txtCodCla.transferFocus();
}//GEN-LAST:event_txtCodClaActionPerformed

    private void txtCodClaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodClaFocusGained
        // TODO add your handling code here:
        strCodCla=txtCodCla.getText();
        txtCodCla.selectAll();
}//GEN-LAST:event_txtCodClaFocusGained

    private void txtCodClaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodClaFocusLost
        // TODO add your handling code here:
        if (!txtCodCla.getText().equalsIgnoreCase(strCodCla)) {
            if(txtCodCla.getText().equals("")) {
                txtCodCla.setText("");
                txtDesCla.setText("");
            }else
                BuscarCla("co_cla",txtCodCla.getText(),2);
        }else
            txtCodCla.setText(strCodCla);
}//GEN-LAST:event_txtCodClaFocusLost

    private void txtCodGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodGrpActionPerformed
        // TODO add your handling code here:
        txtCodGrp.transferFocus();
}//GEN-LAST:event_txtCodGrpActionPerformed

    private void txtCodGrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpFocusGained
        // TODO add your handling code here:
        strCodGrp=txtCodGrp.getText();
        txtCodGrp.selectAll();
}//GEN-LAST:event_txtCodGrpFocusGained

    private void txtCodGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpFocusLost
        // TODO add your handling code here:
        if (!txtCodGrp.getText().equalsIgnoreCase(strCodGrp)) {
            if(txtCodGrp.getText().equals("")) {
                txtCodGrp.setText("");
                txtDesGrp.setText("");
            }else
                BuscarGrp("co_grp",txtCodGrp.getText(),0);
        }else
            txtCodGrp.setText(strCodGrp);
}//GEN-LAST:event_txtCodGrpFocusLost

    private void txtDesClaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesClaActionPerformed
        // TODO add your handling code here:
        txtDesCla.transferFocus();
}//GEN-LAST:event_txtDesClaActionPerformed

    private void txtDesClaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesClaFocusGained
        // TODO add your handling code here:
        strDesCla=txtDesCla.getText();
        txtDesCla.selectAll();
}//GEN-LAST:event_txtDesClaFocusGained

    private void txtDesClaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesClaFocusLost
        // TODO add your handling code here:
        if (!txtDesCla.getText().equalsIgnoreCase(strDesCla)) {
            if(txtDesCla.getText().equals("")) {
                txtCodCla.setText("");
                txtDesCla.setText("");
            }else
                BuscarCla("tx_deslar",txtDesCla.getText(),3);
        }else
            txtDesCla.setText(strDesCla);
}//GEN-LAST:event_txtDesClaFocusLost

    private void txtDesGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesGrpActionPerformed
        // TODO add your handling code here:
        txtDesGrp.transferFocus();
}//GEN-LAST:event_txtDesGrpActionPerformed

    private void txtDesGrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesGrpFocusGained
        // TODO add your handling code here:
        strDesGrp=txtDesGrp.getText();
        txtDesGrp.selectAll();
}//GEN-LAST:event_txtDesGrpFocusGained

    private void txtDesGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesGrpFocusLost
        // TODO add your handling code here:
        if (!txtDesGrp.getText().equalsIgnoreCase(strDesGrp)) {
            if(txtDesGrp.getText().equals("")) {
                txtCodGrp.setText("");
                txtDesGrp.setText("");
            }else
                BuscarGrp("tx_deslar",txtDesGrp.getText(),2);
        }else
            txtDesGrp.setText(strDesGrp);
}//GEN-LAST:event_txtDesGrpFocusLost

    private void butClaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butClaActionPerformed
        // TODO add your handling code here:


        String strSql="";
        if(!txtCodGrp.getText().equals("")){
            strSql="SELECT * FROM( SELECT a.co_grp, a1.tx_deslar as nomgrp, a.co_cla, a.tx_deslar as nomcla FROM tbm_clainv as a " +
                    " inner JOIN tbm_grpclainv as a1 on (a1.co_emp=a.co_emp and a1.co_grp=a.co_grp) " +
                    " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and  a.st_reg not in ('E')  and a1.co_grp="+txtCodGrp.getText()+"  ) AS x ";

            objVenConCla.setTitle("Listado de Clientes");
            objVenConCla.setSentenciaSQL(strSql);
            objVenConCla.setCampoBusqueda(1);
            objVenConCla.cargarDatos();
            objVenConCla.show();
            if (objVenConCla.getSelectedButton()==objVenConCla.INT_BUT_ACE) {
                txtCodCla.setText(objVenConCla.getValueAt(3));
                txtDesCla.setText(objVenConCla.getValueAt(4));
                 optFil.setSelected(true);

            }  
        }
}//GEN-LAST:event_butClaActionPerformed

    private void butSol1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSol1ActionPerformed
        // TODO add your handling code here:

        String strSql="";
        if(radPub.isSelected())
            strSql="SELECT co_grp, tx_descor, tx_deslar FROM tbm_grpclainv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND st_reg='A'" +
                    " and tx_tipgrp='P' ";

        if(radPri.isSelected())
            strSql="SELECT a1.co_grp, a1.tx_descor, a1.tx_deslar FROM tbr_grpclainvusr as a " +
                    " inner join tbm_grpclainv as a1 on (a1.co_emp=a.co_emp and a1.co_grp=a.co_grp) " +
                    " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a1.st_reg='A' and a.co_usr="+objZafParSis.getCodigoUsuario()+" ";

        objVenConGrp.setTitle("Listado de Clientes");
        objVenConGrp.setSentenciaSQL(strSql);
        objVenConGrp.setCampoBusqueda(1);
        objVenConGrp.cargarDatos();
        objVenConGrp.show();
        if (objVenConGrp.getSelectedButton()==objVenConGrp.INT_BUT_ACE) {
            txtCodGrp.setText(objVenConGrp.getValueAt(1));
            txtDesGrp.setText(objVenConGrp.getValueAt(3));
            txtCodCla.setText("");
            txtDesCla.setText("");
             optFil.setSelected(true);
        }
}//GEN-LAST:event_butSol1ActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_optFilActionPerformed

private boolean generarOrdCom(){
  boolean blnRes=false;
  java.sql.Connection conn;
  try{
     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);

       if(procesarOrdCom(conn)){
           conn.commit();
           conn.setAutoCommit(true);
           
           if(! stbDocRelEmpLoc.toString().equals("") )
             asignaSecEmpGrp(conn, conn, stbDocRelEmpLoc );
           
           blnRes=true;
       }else conn.rollback();

       conn.close();
       conn=null;
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

    System.gc();
    return blnRes;
}
  
private boolean procesarOrdCom(java.sql.Connection conn){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="", strSqlEst="";
  try{
     if(conn!=null){
       stmLoc=conn.createStatement();

      strSql="SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_prv FROM tbm_procomitmrepinvprv as a "+
      " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" AND a.co_tipdoc="+txtCodTipDoc.getText()+" " +
      " AND a.co_doc="+txtCodDoc.getText()+" AND a.nd_cancom > 0  AND a.nd_preuni > 0 AND a.st_cotgen='S' AND a.st_comgen='N'  " +
      " GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,  a.co_prv ";
      rstLoc = stmLoc.executeQuery(strSql);
      while(rstLoc.next()){

        Ventas.ZafVen01.ZafVen01_OC obj1 = new  Ventas.ZafVen01.ZafVen01_OC(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis,   conn ,  45,  rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), rstLoc.getInt("co_prv"), 0 );
        obj1.show();

        if(obj1.acepta()){
           obj1.dispose();
           obj1=null;
           blnRes=false;
           break;
       }else {
          if(intDocRelEmpLoc==1) stbDocRelEmpLoc.append(" UNION ALL ");
          stbDocRelEmpLoc.append( obj1.stbDocRelEmp.toString() );
          intDocRelEmpLoc=1;
       }
       obj1.dispose();
       obj1=null;
      }
      rstLoc.close();
      rstLoc=null;

     if(blnRes){
      if( intDocRelEmpLoc==1 ){

          strSqlEst="update tbm_procomitmrepinvprv set st_comgen='S' " +
          " FROM ( " +
          " select  co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_prv from tbm_procomitmrepinvprv " +
          " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
          " AND co_tipdoc="+txtCodTipDoc.getText()+" AND co_doc="+txtCodDoc.getText()+" AND nd_cancom > 0  AND nd_preuni > 0 " +
          " AND st_cotgen='S' AND st_comgen='N'  " +
          " ) as x where tbm_procomitmrepinvprv.co_emp=x.co_emp and tbm_procomitmrepinvprv.co_loc=x.co_loc " +
          "  and tbm_procomitmrepinvprv.co_tipdoc=x.co_tipdoc and tbm_procomitmrepinvprv.co_doc=x.co_doc " +
          "  and tbm_procomitmrepinvprv.co_reg=x.co_reg   and tbm_procomitmrepinvprv.co_prv=x.co_prv ";
         stmLoc.executeUpdate(strSqlEst);


        strSql="UPDATE tbm_cabrepinvprv set st_procom='"+objTooBar.estadoProCom(conn)+"' " +
        " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+txtCodTipDoc.getText()+" " +
        " AND co_doc="+txtCodDoc.getText();
        stmLoc.executeUpdate( strSql );
        blnRes=true;
      }else blnRes=false;
     }else blnRes=false;

      stmLoc.close();
      stmLoc=null;
 
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

    return blnRes;
}

private boolean asignaSecEmpGrp(java.sql.Connection connLoc, java.sql.Connection connRem,  StringBuffer stbDocRelSec ){
  boolean blnRes=false;
  java.sql.ResultSet rstLoc, rstLoc01;
  java.sql.Statement stmLoc, stmLoc01;
  String strSql="";
  int intSecEmp=0,intSecGrp=0, intNumDoc=0, intCodRegCom=0;
  try{
   if(connRem!=null){
     stmLoc=connLoc.createStatement();
     stmLoc01=connLoc.createStatement();
     strSql="SELECT * FROM( "+stbDocRelSec.toString()+" ) AS x";
     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next()){

        intSecEmp=objUltDocPrint.getNumSecDoc(connRem, rstLoc.getInt("coemp") );
        intSecGrp=objUltDocPrint.getNumSecDoc(connRem, objZafParSis.getCodigoEmpresaGrupo() );

        strSql="SELECT CASE WHEN (ne_ultDoc+1) IS NULL THEN 1 ELSE (ne_ultDoc+1) END AS ultnum, st_predoc FROM tbm_cabTipDoc " +
        " WHERE co_emp="+rstLoc.getInt("coemp")+" AND co_loc="+rstLoc.getInt("coloc")+" AND co_tipDoc="+rstLoc.getInt("cotipdoc");
        rstLoc01=stmLoc01.executeQuery(strSql);
        if(rstLoc01.next())
           intNumDoc = rstLoc01.getInt("ultnum");
        rstLoc01.close();
        rstLoc01=null;

        strNumOcGen +=" \n "+intNumDoc;

           strSql=" SELECT CASE WHEN Max(co_reg)+1 IS NULL THEN 1 ELSE Max(co_reg)+1 END as coreg FROM tbm_comemirepinvprv " +
           " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+txtCodTipDoc.getText()+"  AND co_doc="+txtCodDoc.getText()+" ";
           rstLoc01 = stmLoc01.executeQuery(strSql);
           if(rstLoc01.next())
               intCodRegCom=rstLoc01.getInt("coreg");
           rstLoc01.close();
           rstLoc01=null;

         strSql="UPDATE tbm_cabTipDoc SET ne_ultDoc="+intNumDoc+"" +
         " WHERE co_emp="+rstLoc.getInt("coemp")+" AND co_loc="+rstLoc.getInt("coloc")+" AND co_tipDoc="+rstLoc.getInt("cotipdoc");
         strSql+=" ; UPDATE tbm_cabmovinv SET ne_numdoc="+intNumDoc+", st_reg='A', ne_SecEmp="+intSecEmp+", ne_SecGrp="+intSecGrp+" WHERE co_emp="+rstLoc.getInt("coemp")+" AND co_loc="+rstLoc.getInt("coloc")+" " +
         " AND co_tipdoc="+rstLoc.getInt("cotipdoc")+" AND  co_doc="+rstLoc.getInt("codoc")+"";
         strSql+=" ; INSERT INTO tbm_comemirepinvprv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel, co_docrel, st_regrep ) " +
         " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+txtCodDoc.getText()+" " +
         " , "+intCodRegCom+", "+rstLoc.getInt("coloc")+", "+rstLoc.getInt("cotipdoc")+", "+rstLoc.getInt("codoc")+", 'I' ) ";
         stmLoc01.executeUpdate(strSql);
         
     }
     rstLoc.close();
     rstLoc=null;

    stmLoc.close();
    stmLoc=null;
    stmLoc01.close();
    stmLoc01=null;

   }}catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    catch(Exception Evt) {  blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
  return blnRes;
}



private boolean generarCotzaciones(){
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc, stmLoc01;
  java.sql.ResultSet rstLoc, rstLoc01;
  String strSql="";
  int intNumCot=0;
  int intEst=0;
  int intCodRegCot=0;
  try{
     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);
       stmLoc=conn.createStatement();
       stmLoc01=conn.createStatement();

      datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());


      strSql="SELECT  a.co_prv  FROM tbm_procomitmrepinvprv as a "+
      " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" AND a.co_tipdoc="+txtCodTipDoc.getText()+" " +
      " AND a.co_doc="+txtCodDoc.getText()+" AND a.st_cotgen='N' AND a.st_gencot='S' GROUP BY a.co_prv ";
      rstLoc = stmLoc.executeQuery(strSql);
      while(rstLoc.next()){

           strSql=" SELECT CASE WHEN Max(co_cot)+1 IS NULL THEN 1 ELSE Max(co_cot)+1 END as co_cot FROM tbm_cabCotCom WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal();
           rstLoc01 = stmLoc01.executeQuery(strSql);
           if(rstLoc01.next())
               intNumCot=rstLoc01.getInt("co_cot");
           rstLoc01.close();
           rstLoc01=null;

           strSql=" SELECT CASE WHEN Max(co_reg)+1 IS NULL THEN 1 ELSE Max(co_reg)+1 END as coreg FROM tbm_cotemirepinvprv " +
           " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+txtCodTipDoc.getText()+"  AND co_doc="+txtCodDoc.getText()+" ";
           rstLoc01 = stmLoc01.executeQuery(strSql);
           if(rstLoc01.next())
               intCodRegCot=rstLoc01.getInt("coreg");
           rstLoc01.close();
           rstLoc01=null;

           
           if(generaCot(conn, rstLoc.getInt("co_prv"), intNumCot, datFecAux, intCodRegCot  )){
               intEst=1;
           }else{ intEst=0;
                  break;
           }
      }
      rstLoc.close();
      rstLoc=null;

      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;


      if(intEst==1){
        if(numeroCot(conn)){
         conn.commit();
         blnRes=true;
      }}
      else conn.rollback();

       conn.close();
       conn=null;
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

    System.gc();
    return blnRes;
}



public boolean numeroCot(java.sql.Connection conn ){
   boolean blnRes=false;
   java.sql.Statement stmLoc;
   java.sql.ResultSet rstLoc;
   String strSql="";
   try{
      stmLoc=conn.createStatement();

      strSql="select  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, count(a.co_reg) as numcot  from tbm_procomitmrepinvprv as a " +
      " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" AND a.co_tipdoc="+txtCodTipDoc.getText()+" " +
      " AND a.co_doc="+txtCodDoc.getText()+" AND a.st_cotgen='S' "+
      " GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg ";
      rstLoc = stmLoc.executeQuery(strSql);
      strSql="";
      while(rstLoc.next()){
          strSql+="UPDATE tbm_detrepinvprv SET ne_numtotcotgen="+rstLoc.getInt("numcot")+" " +
          " WHERE co_emp="+rstLoc.getInt("co_emp")+" AND co_loc="+rstLoc.getInt("co_loc")+" AND co_tipdoc="+rstLoc.getInt("co_tipdoc")+" " +
          " AND co_doc="+rstLoc.getInt("co_doc")+" AND co_reg="+rstLoc.getInt("co_reg")+"  ; ";
      }

      if(!strSql.equals(""))
        stmLoc.executeUpdate(strSql);

      stmLoc.close();
      stmLoc=null;
      blnRes=true;

   }catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
    catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    return blnRes;
}




public boolean generaCot(java.sql.Connection conn, int intCodPrv, int intCodCot, java.util.Date FecAuxDoc, int intCodRegCot  ){
   boolean blnRes=false;
   java.sql.Statement stmLoc;
   java.sql.ResultSet rstLoc;
   String strSql="";
   int intCodReg=0;
   try{
      stmLoc=conn.createStatement();

      strSql="INSERT INTO tbm_cabCotCom (co_emp, co_loc, co_cot, fe_cot, co_prv, co_com, nd_sub, nd_valiva, " +
      " nd_tot, nd_porIva, fe_ing, co_usring, st_regrep ) "+
      " VALUES("+objZafParSis.getCodigoEmpresa()+ ", "+objZafParSis.getCodigoLocal()+", "+intCodCot+", '"+FecAuxDoc+"', "+
      intCodPrv+", "+objZafParSis.getCodigoUsuario()+", 0, 0, 0, 12, "+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getCodigoUsuario()+", 'I' )";

      strSql+=" ; INSERT INTO tbm_cotemirepinvprv (co_emp, co_loc, co_tipdoc, co_doc , co_reg,  co_locrel, co_cotrel, st_regrep ) "+
      " VALUES("+objZafParSis.getCodigoEmpresa()+ ", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+txtCodDoc.getText()+", "+
      intCodRegCot+", "+objZafParSis.getCodigoLocal()+", "+intCodCot+", 'I' )";

      stmLoc.executeUpdate(strSql);

      
      strSql="select  a1.co_itm, a2.tx_nomitm,  a.co_reg, a.co_prv, a1.nd_cancom, a2.st_ivacom, a.nd_preunicot, a.nd_pordescot  " +
      " from tbm_procomitmrepinvprv as a " +
      "  inner join tbm_detrepinvprv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc and a1.co_reg=a.co_reg) " +
      "  inner join tbm_inv as a2 on (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm) " +
      "  where a.co_emp="+objZafParSis.getCodigoEmpresa()+ " and a.co_loc="+objZafParSis.getCodigoLocal()+" " +
      "  AND a.co_tipdoc="+txtCodTipDoc.getText()+" AND a.co_doc="+txtCodDoc.getText()+" AND a.st_cotgen='N' AND a.st_gencot='S'  and a.co_prv="+intCodPrv;
      rstLoc = stmLoc.executeQuery(strSql);
      strSql="";
      while(rstLoc.next()){
          intCodReg++;
          strSql+="INSERT INTO tbm_detCotCom(co_emp, co_loc, co_cot, co_reg, co_itm, tx_nomItm, co_bod, " +
          " nd_can, nd_cosuni, nd_porDes, st_ivaCom ,st_regrep  ) "+
          " VALUES("+objZafParSis.getCodigoEmpresa()+ ", "+objZafParSis.getCodigoLocal()+", "+intCodCot+", "+intCodReg+" "+
          " , "+rstLoc.getInt("co_itm")+", '"+rstLoc.getString("tx_nomitm")+"', "+txtCodBod.getText()+" "+
          " ,"+rstLoc.getString("nd_cancom")+", "+rstLoc.getDouble("nd_preunicot")+", "+rstLoc.getDouble("nd_pordescot")+", '"+rstLoc.getString("st_ivacom")+"', 'I' ) ; ";

          strSql+="UPDATE tbm_procomitmrepinvprv SET st_cotgen='S' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND" +
          " co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+txtCodTipDoc.getText()+" AND co_doc="+txtCodDoc.getText()+" " +
          " AND co_reg="+rstLoc.getInt("co_reg")+"  AND co_prv="+rstLoc.getInt("co_prv")+"  ; ";


      }

      stmLoc.executeUpdate(strSql);

  
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

   }catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
    catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    return blnRes;
}








                                       





    

 public void BuscarTipDoc(String campo,String strBusqueda,int tipo){
  objVenConTipdoc.setTitle("Listado de Vendedores");
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
           txtDesCodTitpDoc.setText(strDesCorTipDoc);
           txtDesLarTipDoc.setText(strDesLarTipDoc);
  }}}




 public void BuscarBod(String campo,String strBusqueda,int tipo){
  objVenConBodUsr.setTitle("Listado de Bodegas");
  if(objVenConBodUsr.buscar(campo, strBusqueda )) {
        txtCodBod.setText(objVenConBodUsr.getValueAt(1));
        txtNomBod.setText(objVenConBodUsr.getValueAt(2));
        strCodBod=objVenConBodUsr.getValueAt(1);
        strNomBod=objVenConBodUsr.getValueAt(2);
  }else{
        objVenConBodUsr.setCampoBusqueda(tipo);
        objVenConBodUsr.cargarDatos();
        objVenConBodUsr.show();
        if (objVenConBodUsr.getSelectedButton()==objVenConBodUsr.INT_BUT_ACE) {
           txtCodBod.setText(objVenConBodUsr.getValueAt(1));
           txtNomBod.setText(objVenConBodUsr.getValueAt(2));
           strCodBod=objVenConBodUsr.getValueAt(1);
           strNomBod=objVenConBodUsr.getValueAt(2);
        }else{
           txtCodBod.setText(strCodBod);
           txtNomBod.setText(strNomBod);
  }}}


 public void BuscarGrp(String campo,String strBusqueda,int tipo){
  objVenConGrp.setTitle("Listado de Vendedores");

  String strSql="";
   if(radPub.isSelected())
      strSql="SELECT co_grp, tx_descor, tx_deslar FROM tbm_grpclainv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND st_reg='A'" +
      " and tx_tipgrp='P' ";

  if(radPri.isSelected())
     strSql="SELECT a1.co_grp, a1.tx_descor, a1.tx_deslar FROM tbr_grpclainvusr as a " +
     " inner join tbm_grpclainv as a1 on (a1.co_emp=a.co_emp and a1.co_grp=a.co_grp) " +
     " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a1.st_reg='A' and a.co_usr="+objZafParSis.getCodigoUsuario()+" ";

  objVenConGrp.setSentenciaSQL(strSql);
 // objVenConGrp.cargarDatos();


  if(objVenConGrp.buscar(campo, strBusqueda )) {
      txtCodGrp.setText(objVenConGrp.getValueAt(1));
      txtDesGrp.setText(objVenConGrp.getValueAt(3));
       optFil.setSelected(true);
       txtCodCla.setText("");
       txtDesCla.setText("");
  }else{
        objVenConGrp.setCampoBusqueda(tipo);
        objVenConGrp.cargarDatos();
        objVenConGrp.show();
        if (objVenConGrp.getSelectedButton()==objVenConGrp.INT_BUT_ACE) {
            txtCodGrp.setText(objVenConGrp.getValueAt(1));
            txtDesGrp.setText(objVenConGrp.getValueAt(3));
             optFil.setSelected(true);
             txtCodCla.setText("");
       txtDesCla.setText("");
        }else{
            txtCodGrp.setText(strCodGrp);
            txtDesGrp.setText(strDesGrp);
             txtCodCla.setText("");
              optFil.setSelected(true);
       txtDesCla.setText("");
  }}}



 public void BuscarCla(String campo,String strBusqueda,int tipo){

   String strSql="";
   if(!txtCodGrp.getText().equals("")){
      strSql="SELECT * FROM( SELECT a.co_grp, a1.tx_deslar as nomgrp, a.co_cla, a.tx_deslar as nomcla FROM tbm_clainv as a " +
      " inner JOIN tbm_grpclainv as a1 on (a1.co_emp=a.co_emp and a1.co_grp=a.co_grp) " +
      " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and  a.st_reg not in ('E')  and a1.co_grp="+txtCodGrp.getText()+"  ) AS x ";


  objVenConCla.setTitle("Listado de Clasificación");

  objVenConCla.setSentenciaSQL(strSql);
  //objVenConCla.cargarDatos();


  if(objVenConCla.buscar(campo, strBusqueda )) {
       txtCodCla.setText(objVenConCla.getValueAt(3));
       txtDesCla.setText(objVenConCla.getValueAt(4));
        optFil.setSelected(true);

  }else{
        objVenConCla.setCampoBusqueda(tipo);
        objVenConCla.cargarDatos();
        objVenConCla.show();
        if (objVenConCla.getSelectedButton()==objVenConCla.INT_BUT_ACE) {
             txtCodCla.setText(objVenConCla.getValueAt(3));
             txtDesCla.setText(objVenConCla.getValueAt(4));
              optFil.setSelected(true);

        }else{
            txtCodCla.setText(strCodCla);
            txtDesCla.setText(strDesCla);
             optFil.setSelected(true);
  }}}else{
       txtCodCla.setText("");
       txtDesCla.setText("");
  }}



 public void BuscarItm(String campo,String strBusqueda,int tipo){
  objVenConItm.setTitle("Listado de Vendedores");
  if(objVenConItm.buscar(campo, strBusqueda )) {
      txtCodItm.setText(objVenConItm.getValueAt(1));
      txtDesItm.setText(objVenConItm.getValueAt(4));
      optFil.setSelected(true);
  }else{
        objVenConItm.setCampoBusqueda(tipo);
        objVenConItm.cargarDatos();
        objVenConItm.show();
        if (objVenConItm.getSelectedButton()==objVenConItm.INT_BUT_ACE) {
            txtCodItm.setText(objVenConItm.getValueAt(1));
            txtDesItm.setText(objVenConItm.getValueAt(4));
            optFil.setSelected(true);
        }else{
            txtCodItm.setText(strCodItm);
            txtDesItm.setText(strDesItm);
  }}}





      


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup OptGrp;
    private javax.swing.ButtonGroup OptGrp2;
    private javax.swing.JRadioButton OptRepAnu;
    private javax.swing.JRadioButton OptRepMes;
    private javax.swing.JPanel PanFil;
    private javax.swing.JPanel PanRep;
    private javax.swing.JPanel PanTabGen;
    private javax.swing.JTabbedPane TabGen;
    private javax.swing.JButton butBusBod;
    private javax.swing.JButton butCla;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butCot;
    private javax.swing.JButton butOrdCom;
    private javax.swing.JButton butSol;
    private javax.swing.JButton butSol1;
    private javax.swing.JButton butTipDoc;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblMesRep;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lbltipgrp;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panCorRec;
    private javax.swing.JPanel panCotEmi;
    private javax.swing.JPanel panDatFil;
    private javax.swing.JPanel panDatHis;
    private javax.swing.JPanel panLbl;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panPrv;
    private javax.swing.JPanel panTit;
    private javax.swing.JPanel panTxa;
    private javax.swing.JPanel panordcom;
    private javax.swing.JRadioButton radPri;
    private javax.swing.JRadioButton radPub;
    private javax.swing.JScrollPane scrtbl;
    private javax.swing.JSpinner spnNumMesRep;
    private javax.swing.JScrollPane spnObs3;
    private javax.swing.JScrollPane spnObs4;
    private javax.swing.JTable tblAnMe;
    private javax.swing.JTable tblCotEmi;
    private javax.swing.JTable tblCotRec;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblOrdCom;
    private javax.swing.JTable tblPrv;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodCla;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodGrp;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtDesCla;
    private javax.swing.JTextField txtDesCodTitpDoc;
    private javax.swing.JTextField txtDesGrp;
    private javax.swing.JTextField txtDesItm;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextArea txtObs1;
    private javax.swing.JTextArea txtObs2;
    private javax.swing.JTextField txtcodaltdes;
    private javax.swing.JTextField txtcodalthas;
    // End of variables declaration//GEN-END:variables
    
    
       
//
//     private class ZafThreadGUI extends Thread
//    {
//        public void run()
//        {
//
//           butCon.setText("Detener");
//           lblMsgSis.setText("Procesando datos...");
//           pgrSis.setIndeterminate(true);
//
//           if(OptRepAnu.isSelected()){
//             if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
//               if(cargarDatosAnualesGrp()){
//                      lblMsgSis.setText("Listo");
//                      pgrSis.setIndeterminate(false);
//                      pgrSis.setValue(0);
//                      butCon.setText("Consultar");
//                      TabGen.setSelectedIndex(1);
//              }}else {
//                   if(cargarDatosAnuales()){
//                       lblMsgSis.setText("Listo");
//                       pgrSis.setIndeterminate(false);
//                       pgrSis.setValue(0);
//                       butCon.setText("Consultar");
//                       TabGen.setSelectedIndex(1);
//              }}
//           }
//
//
//
//            if(OptRepMes.isSelected()){
//               if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
//                if(cargarDatosMensualesGrp()){
//                     lblMsgSis.setText("Listo");
//                     pgrSis.setIndeterminate(false);
//                     pgrSis.setValue(0);
//                     butCon.setText("Consultar");
//                     TabGen.setSelectedIndex(1);
//               }}else{
//                     if(cargarDatosMensuales()){
//                        lblMsgSis.setText("Listo");
//                        pgrSis.setIndeterminate(false);
//                        pgrSis.setValue(0);
//                        butCon.setText("Consultar");
//                        TabGen.setSelectedIndex(1);
//               }}
//            }
//
//
//
//            objThrGUI=null;
//        }
//    }
//
    



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

  return blnRes;
}





        public void clickAceptar() {
            setEstadoBotonMakeFac();
        }


public void clickAnterior() {
 try{
  if(RST_LOC != null ) {
     abrirCon();
     if(!RST_LOC.isFirst()) {
          RST_LOC.previous();
          refrescaDatos(CONN_GLO, RST_LOC);
       }
     CerrarCon();
  }} catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
}




        public void clickAnular() {

        }


        public void clickConsultar() {
            clnTextos();

            cargarTipoDoc (2);
            cargarBodPre();


	}






public void cargarBodPre(){
  java.sql.Connection  conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
        stmLoc=conn.createStatement();

        strSql="SELECT co_bod, tx_nom FROM ( " +
        " select a.co_bod,  a1.tx_nom from tbr_bodlocprgusr as a " +
        " inner join tbm_bod as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod ) " +
        " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" " +
        " and a.co_usr="+objZafParSis.getCodigoUsuario()+" and a.co_mnu="+objZafParSis.getCodigoMenu()+"  and a.st_reg='S' " +
        " ) as a";

        System.out.println(""+ strSql);
        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()){
            txtCodBod.setText(rstLoc.getString("co_bod"));
            txtNomBod.setText(rstLoc.getString("tx_nom"));
            strCodBod=rstLoc.getString("co_bod");
            strNomBod=rstLoc.getString("tx_nom");
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




public void cargarTipoDoc(int intVal){
  java.sql.Connection  conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
        stmLoc=conn.createStatement();

        strSql= "Select  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor , doc.ne_numVisBue  ,case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc  " +
        " from tbr_tipdocprg as menu  " +
        " inner join  tbm_cabtipdoc as doc on ( doc.co_emp = menu.co_emp and doc.co_loc=menu.co_loc and  doc.co_tipdoc=menu.co_tipdoc)   " +
        " where   menu.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
        " menu.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
        " menu.co_mnu = "+objZafParSis.getCodigoMenu()+" and " +
        "  menu.st_reg = 'S'";
        System.out.println(""+ strSql);
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






public void clickFin(){
 try{
    if(RST_LOC != null ){
     abrirCon();
      if(!RST_LOC.isLast()){
            RST_LOC.last();
            refrescaDatos(CONN_GLO, RST_LOC);
      }
    CerrarCon();
   }}catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
}







public void clickInicio(){
 try{
   if(RST_LOC != null ){
     abrirCon();
     if(!RST_LOC.isFirst()) {
            RST_LOC.first();
            refrescaDatos(CONN_GLO, RST_LOC);
       }
    CerrarCon();
  }}catch (Exception e) { objUti.mostrarMsgErr_F1(this, e);  }
}





public void clickInsertar() {

    txtFecDoc.setHoy();
    cargarTipoDoc(1);
    cargarBodPre();
    spnNumMesRep.setValue(new Integer(1) );
    
    ocultarDat(true);
    
}


private void ocultarDat(boolean blnest){
     panDatHis.setVisible(blnest);
     panDatFil.setVisible(blnest);
     lblMesRep.setVisible(blnest);
     spnNumMesRep.setVisible(blnest);
     butCon.setVisible(blnest);

    objTblMod2.removeAllRows();
    objTblModCotEmi.removeAllRows();
    objTblModOrdCom.removeAllRows();
    objTblModPrv.removeAllRows();
    objTblModCotRec.removeAllRows();
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
   if(RST_LOC != null ){
      abrirCon();
      if(!RST_LOC.isLast()) {
               RST_LOC.next();
               refrescaDatos( CONN_GLO, RST_LOC);
       }
      CerrarCon();
  }}catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
}




public void clickEliminar() {

}




public boolean eliminar() {
  boolean blnRes=false;
   return blnRes;
}







private boolean validaCampos(){

  if(txtDesCodTitpDoc.getText().equals("") ){
    TabGen.setSelectedIndex(0);
    MensajeInf("El campo <<  Tipo de Documento  >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtDesCodTitpDoc.requestFocus();
    return false;
  }

 if(txtCodBod.getText().equals("") ){
    TabGen.setSelectedIndex(0);
    MensajeInf("El campo << Bodega  >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtCodBod.requestFocus();
    return false;
  }


 if(txtNumDoc.getText().equals("") ){
    TabGen.setSelectedIndex(0);
    MensajeInf("El campo << Número de Documento  >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtNumDoc.requestFocus();
    return false;
  }

 if( Integer.parseInt(spnNumMesRep.getModel().getValue().toString()) <= 0 ){
    TabGen.setSelectedIndex(0);
    MensajeInf("El campo << Meses Reponer  >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    return false;
  }


 return true;
}

public boolean insertar() {
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  int intCodReg=0;
  String strSql="";
  try{
    if(validaCampos()){

     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
      conn.setAutoCommit(false);
        stmLoc=conn.createStatement();
        strSql="SELECT CASE WHEN max(co_doc)+1 IS NULL THEN 1 ELSE max(co_doc)+1 END AS codoc FROM tbm_cabrepinvprv " +
        " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal();
        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()){
           intCodReg=rstLoc.getInt("codoc");
        }
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;

        if(insertarRegCab(conn, intCodReg)){
         if(insertarRegDet(conn, intCodReg)){
            conn.commit();
            blnRes=true;
            txtCodDoc.setText(""+intCodReg);
         }else { conn.rollback(); this.MensajeInf(" No hay detalle para insertar.. "); }
        }else conn.rollback();

       conn.close();
       conn=null;
     }

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

private boolean insertarRegCab(java.sql.Connection conn, int intCodReg){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 String strFecDoc="";
 try{
    if(conn!=null){
       stmLoc=conn.createStatement();
       strFecDoc = "'#" + txtFecDoc.getFecha("/", "y/m/d") + "#'";
       
       strSql="INSERT INTO tbm_cabrepinvprv(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, ne_nummesrep, st_imp, " +
       " tx_obs1, tx_obs2, st_reg, fe_ing, co_usring, st_regrep ) " +
       " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodReg+", " +
       " "+strFecDoc+", "+txtNumDoc.getText()+", "+txtCodBod.getText()+", "+spnNumMesRep.getModel().getValue().toString()+", " +
       " 'N', '"+txtObs1.getText()+"', '"+txtObs2.getText()+"', 'A', "+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getCodigoUsuario()+", 'I' ) ";
       
       strSql+=" ; UPDATE tbm_cabtipdoc set ne_ultdoc="+txtNumDoc.getText()+" WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
       " and co_loc="+objZafParSis.getCodigoLocal()+" and co_tipdoc= "+txtCodTipDoc.getText()+" ";



       System.out.println("-> "+strSql);
       stmLoc.executeUpdate(strSql);
    stmLoc.close();
    stmLoc=null;
    blnRes=true;
  
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

public boolean insertarRegDet(java.sql.Connection conn, int intCodDoc ){
   boolean blnRes=false;
   java.sql.Statement stmLoc;
   String strSql="";
   StringBuffer stbIns;
   int intConIns=0;
   try{
      stmLoc=conn.createStatement();
      stbIns=new StringBuffer();

       for(int i=0; i<tblDat.getRowCount(); i++){
        if(tblDat.getValueAt(i, INT_TBL2_COSIS) != null){
         if(!tblDat.getValueAt(i, INT_TBL2_COSIS).toString().equals("")){

           strSql=" INSERT INTO tbm_detrepinvprv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, co_itm, nd_canrep, nd_cancom,  st_regrep ) " +
           " VALUES("+objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+","+(i+1)+" "+
           " ,"+tblDat.getValueAt(i, INT_TBL2_COSIS).toString()+", "+objUti.redondear(  Double.parseDouble( tblDat.getValueAt(i, INT_TBL2_REPOSI).toString() ), 2 )+", "+objUti.redondear(  Double.parseDouble( tblDat.getValueAt(i, INT_TBL2_CANCOM).toString() ), 2 )+", 'I' ) ;";
           stbIns.append( strSql );
           intConIns=1;

       }}}

      if(intConIns==1 ){
          stmLoc.executeUpdate(stbIns.toString());
          blnRes=true;
      }
      stbIns=null;
      
      stmLoc.close();
      stmLoc=null;
      

   }catch(java.sql.SQLException Evt){ blnRes=false; MensajeInf("Error. Al Insertar Proveedores posible duplicidad de datos.\nVerifique los datos he intente nuevamente.");  }
    catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    return blnRes;
}









public boolean modificar(){
  boolean blnRes=false;
  java.sql.Connection conn;
  try{
    if(validaCampos()){
      conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
      conn.setAutoCommit(false);

      if(modificarRegRep(conn)){
          conn.commit();
          blnRes=true;
      }else  conn.rollback();

       conn.close();
       conn=null;
     }

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


    
public boolean modificarRegRep(java.sql.Connection conn ){
   boolean blnRes=false;
   java.sql.Statement stmLoc;
   StringBuffer stbIns;
   int intConIns=0;
   String strSql="";
   try{
      stmLoc=conn.createStatement();

      stbIns=new StringBuffer();

       for(int i=0; i<tblDat.getRowCount(); i++){
         String strCanTotNumCom=( (tblDat.getValueAt(i, INT_TBL2_CTNCOM)==null)?"":tblDat.getValueAt(i, INT_TBL2_CTNCOM).toString() );
         String strCanCom=( (tblDat.getValueAt(i, INT_TBL2_CANCOM)==null)?"":tblDat.getValueAt(i, INT_TBL2_CANCOM).toString() );
         String strCodReg= tblDat.getValueAt(i, INT_TBL2_CODREG).toString();

         stbIns.append(" UPDATE tbm_detrepinvprv SET nd_cantotnuncom ="+ objUti.redondear( Double.parseDouble((strCanTotNumCom.equals("")?"0":strCanTotNumCom)),4)+" ," +
         " nd_cancom ="+ objUti.redondear( Double.parseDouble((strCanCom.equals("")?"0":strCanCom)),4)+"  " +
         " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+txtCodTipDoc.getText()+" " +
         " AND co_doc="+txtCodDoc.getText()+" AND co_reg="+strCodReg+" ; ");
         intConIns=1;

       }

      if(intConIns==1){
        stmLoc.executeUpdate(stbIns.toString());

        strSql="UPDATE tbm_cabrepinvprv set st_procom='"+ estadoProCom(conn)+"' " +
        " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+txtCodTipDoc.getText()+" " +
        " AND co_doc="+txtCodDoc.getText();
        stmLoc.executeUpdate( strSql );

      }

      stbIns=null;

      stmLoc.close();
      stmLoc=null;
      blnRes=true;

   }catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
    catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    return blnRes;
}



public String estadoProCom(java.sql.Connection conn ){
   String strEst="N";
   java.sql.Statement stmLoc;
   java.sql.ResultSet rstLoc;
   String strSql="";
   try{
      stmLoc=conn.createStatement();

      strSql="select CASE WHEN cancom = com THEN 'S' else 'N' end as procom from ( " +
      " select  sum(nd_cancom) as cancom , sum( nd_cantotcom + nd_cantotnuncom ) as com  from tbm_detrepinvprv " +
      " where co_emp="+objZafParSis.getCodigoEmpresa()+"  AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+txtCodTipDoc.getText()+" " +
      "  AND co_doc="+txtCodDoc.getText()+"  ) AS x ";
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next())
          strEst=rstLoc.getString("procom");

      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      
   }catch(java.sql.SQLException Evt){ strEst="N"; objUti.mostrarMsgErr_F1(this, Evt); }
    catch(Exception Evt){ strEst="N";  objUti.mostrarMsgErr_F1(this, Evt); }
    return strEst;
}








      private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }



 public void  clnTextos(){
     txtCodCla.setText("");
     txtDesCla.setText("");
     txtNumDoc.setText("");
     txtCodDoc.setText("");

     //objTblModCla.removeAllRows();
 }




        public boolean cancelar() {
            boolean blnRes=true;


            clnTextos();

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
        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux)) {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado()) {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
//                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
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
  java.sql.Connection conn;
  String strSql="";
  try{
     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);

          STM_GLO=conn.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY );

           strSql="SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_bod, a.ne_numdoc, a.fe_doc, a.ne_nummesrep  FROM tbm_cabrepinvprv AS a " +
           " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" " +
           " AND a.co_tipdoc="+txtCodTipDoc.getText()+"  AND   a.st_reg NOT IN('E') " +
           " AND a.co_bod="+txtCodBod.getText()+"  "+strFil+" ORDER BY a.ne_numdoc ";
           RST_LOC=STM_GLO.executeQuery(strSql);
           if(RST_LOC.next()){
              RST_LOC.last();
              setMenSis("Se encontraron " + RST_LOC.getRow() + " registros");
              refrescaDatos(conn, RST_LOC);
              blnRes=true;
           }else{
                setMenSis("0 Registros encontrados");
                clnTextos();
           }

       conn.close();
       conn=null;
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

    System.gc();
    return blnRes;
}


private boolean _consultar(int intCodLoc, int intCodDoc ){
  boolean blnRes=false;
  java.sql.Connection conn;
  String strSql="";
  try{
     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);

          STM_GLO=conn.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY );

           strSql="SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_bod, a.ne_numdoc, a.fe_doc, a.ne_nummesrep  " +
           " ,a1.tx_descor, a1.tx_deslar, a2.tx_nom  FROM tbm_cabrepinvprv AS a " +
           " inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc) " +
           " inner join tbm_bod as a2 on (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod ) "+
           " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+intCodLoc+" " +
           " AND a.co_tipdoc="+txtCodTipDoc.getText()+"  AND   a.st_reg NOT IN('E') " +
           " AND a.co_bod="+txtCodBod.getText()+"  and a.co_doc="+intCodDoc+" ORDER BY a.ne_numdoc ";
           RST_LOC=STM_GLO.executeQuery(strSql);
           if(RST_LOC.next()){

              txtNumDoc.setText( RST_LOC.getString("ne_numdoc") );
              txtDesCodTitpDoc.setText( RST_LOC.getString("tx_descor") );
              txtDesLarTipDoc.setText( RST_LOC.getString("tx_deslar") );
              txtNomBod.setText( RST_LOC.getString("tx_nom") );

              RST_LOC.last();
              setMenSis("Se encontraron " + RST_LOC.getRow() + " registros");
              refrescaDatos(conn, RST_LOC);
              blnRes=true;
           }else{
                setMenSis("0 Registros encontrados");
                clnTextos();
           }

       conn.close();
       conn=null;
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

    System.gc();
    return blnRes;
}


private boolean confirgurarTblDatAnu(int intTipConf){
 boolean blnRes=false;
 try{
    Vector vecCab2=new Vector();    //Almacena las cabeceras
    vecCab2.clear();
    vecCab2.add(INT_TBL2_LINEA,"");
    vecCab2.add(INT_TBL2_COSIS,"Cod.Itm.");
    vecCab2.add(INT_TBL2_COALT,"Cod.Alt.");
    vecCab2.add(INT_TBL2_NOMBR,"Nombre");
    vecCab2.add(INT_TBL2_UNIMED,"Unidad");

    int INT_DET_TBL= INT_TBL2_UNIMED;

    if(intTipConf==1){
     for(int i=0; i< tblAnMe.getRowCount(); i++){
      if(tblAnMe.getValueAt(i,INT_TBL_CHK).toString().equals("true")){
         INT_DET_TBL++;
         vecCab2.add(INT_DET_TBL,"Unidad");
         INT_DET_TBL++;
         vecCab2.add(INT_DET_TBL,"Veces");
         INT_DET_TBL++;
         vecCab2.add(INT_DET_TBL,"Meses");
     }}
    }

     INT_DET_TBL++;
     INT_TBL2_TOTUNI=INT_DET_TBL;
     vecCab2.add(INT_TBL2_TOTUNI,"Unidad");
     INT_DET_TBL++;
     INT_TBL2_TOTVEC=INT_DET_TBL;
     vecCab2.add(INT_TBL2_TOTVEC,"Veces");
     INT_DET_TBL++;
     INT_TBL2_TOTMES=INT_DET_TBL;
     vecCab2.add(INT_TBL2_TOTMES,"Meses");
     INT_DET_TBL++;
     INT_TBL2_STKACT=INT_DET_TBL;
     vecCab2.add(INT_TBL2_STKACT,"Stk.Act.");
     INT_DET_TBL++;
     INT_TBL2_NUMMRE=INT_DET_TBL;
     vecCab2.add(INT_TBL2_NUMMRE,"Num.Mes.Rep");
     INT_DET_TBL++;
     INT_TBL2_PROCAL=INT_DET_TBL;
     vecCab2.add(INT_TBL2_PROCAL,"Pro.Cal");
     INT_DET_TBL++;
     INT_TBL2_PROMAN=INT_DET_TBL;
     vecCab2.add(INT_TBL2_PROMAN,"Pro.Man");
     INT_DET_TBL++;
     INT_TBL2_STKMIN=INT_DET_TBL;
     vecCab2.add(INT_TBL2_STKMIN,"Minimo");
     INT_DET_TBL++;
     INT_TBL2_REPOSI=INT_DET_TBL;
     vecCab2.add(INT_TBL2_REPOSI,"Reposicion");
     INT_DET_TBL++;
     INT_TBL2_CANCOM=INT_DET_TBL;
     vecCab2.add(INT_TBL2_CANCOM,"Can.Com");
     INT_DET_TBL++;
     INT_TBL2_BUTREP=INT_DET_TBL;
     vecCab2.add(INT_TBL2_BUTREP,"..");
     INT_DET_TBL++;
     INT_TBL2_PRVELE=INT_DET_TBL;
     vecCab2.add(INT_TBL2_PRVELE,"Prv.Ele.");
     INT_DET_TBL++;
     INT_TBL2_COTGEN=INT_DET_TBL;
     vecCab2.add(INT_TBL2_COTGEN,"Cot.Gen.");
     INT_DET_TBL++;
     INT_TBL2_COTING=INT_DET_TBL;
     vecCab2.add(INT_TBL2_COTING,"Cot.Ing.");
     INT_DET_TBL++;
     INT_TBL2_NTCCAN=INT_DET_TBL;
     vecCab2.add(INT_TBL2_NTCCAN,"Cot.Can.");
     INT_DET_TBL++;
     INT_TBL2_NTCOMP=INT_DET_TBL;
     vecCab2.add(INT_TBL2_NTCOMP,"Can.Tot.Com");
     INT_DET_TBL++;
     INT_TBL2_CTNCOM=INT_DET_TBL;
     vecCab2.add(INT_TBL2_CTNCOM,"Can.Tot.Num.Com");
     INT_DET_TBL++;
     INT_TBL2_CODREG=INT_DET_TBL;
     vecCab2.add(INT_TBL2_CODREG,"");

     INT_DET_TBL++;
     INT_TBL2_CTNCOMORI=INT_DET_TBL;
     vecCab2.add(INT_TBL2_CTNCOMORI,"");

     INT_DET_TBL++;
     INT_TBL2_CANCOMORI=INT_DET_TBL;
     vecCab2.add(INT_TBL2_CANCOMORI,"");

     


    objTblMod2=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod2.setHeader(vecCab2);
    tblDat.setModel(objTblMod2);

     tblDat.setRowSelectionAllowed(true);
     tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
     new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL2_LINEA);

     //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
    objMouMotAda=new ZafMouMotAda();
    tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


    //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
    objTblMod2.setColumnDataType(INT_TBL2_PROMAN, objTblMod2.INT_COL_DBL, new Integer(0), null);
    objTblMod2.setColumnDataType(INT_TBL2_STKMIN, objTblMod2.INT_COL_DBL, new Integer(0), null);
    objTblMod2.setColumnDataType(INT_TBL2_CTNCOM, objTblMod2.INT_COL_DBL, new Integer(0), null);
    objTblMod2.setColumnDataType(INT_TBL2_CANCOM, objTblMod2.INT_COL_DBL, new Integer(0), null);


   //Configurar ZafTblMod: Establecer las columnas obligatorias.
    java.util.ArrayList arlAux=new java.util.ArrayList();
    arlAux.add("" + INT_TBL2_PROMAN);
    arlAux.add("" + INT_TBL2_STKMIN);
    objTblMod2.setColumnasObligatorias(arlAux);
    arlAux=null;
    //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
    objTblMod2.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());

     tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
   // tblDat.getTableHeader().setReorderingAllowed(false);

    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    tcmAux.getColumn(INT_TBL2_LINEA).setPreferredWidth(30);
    tcmAux.getColumn(INT_TBL2_COSIS).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL2_COALT).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL2_NOMBR).setPreferredWidth(150);
    tcmAux.getColumn(INT_TBL2_UNIMED).setPreferredWidth(50);



    int intColTblDat=0;
          for( intColTblDat=INT_TBL2_UNIMED+1; intColTblDat<=INT_DET_TBL ; intColTblDat++){
                 objTblCelRenLbl=null;

                 objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
                 objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
                 objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                 objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
                 objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
                 tcmAux.getColumn( intColTblDat ).setCellRenderer(objTblCelRenLbl);
                 //Tamaño de las celdas
                 if(INT_TBL2_BUTREP==intColTblDat)
                   tcmAux.getColumn(intColTblDat).setPreferredWidth(25);
                 else  tcmAux.getColumn(intColTblDat).setPreferredWidth(60);
          }


    Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
    tcmAux.getColumn(INT_TBL2_BUTREP).setCellRenderer(objTblCelRenBut);
    objTblCelRenBut=null;
    new ButBod(tblDat, INT_TBL2_BUTREP);   //*****

      tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

    new ZafTblOrd(tblDat);
    new ZafTblBus(tblDat);


//*************************************************
    objTblFilCab=new ZafTblFilCab(tblDat);
    tcmAux.getColumn(INT_TBL2_LINEA).setCellRenderer(objTblFilCab);

    ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
    objTblHeaGrp.setHeight(16*2);
    INT_DET_TBL= INT_TBL2_UNIMED;

   int intConMesRep=0;

  if(intTipConf==1){
    for(int i=0; i< tblAnMe.getRowCount(); i++){
      if(tblAnMe.getValueAt(i,INT_TBL_CHK).toString().equals("true")){
         System.out.println(""+ tblAnMe.getValueAt(i,INT_TBL_ANI).toString() );
         String strAn=tblAnMe.getValueAt(i,INT_TBL_ANI).toString();

            ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Año "+strAn );
            objTblHeaColGrpAmeSur.setHeight(16);

            INT_DET_TBL++;
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_DET_TBL));
            INT_DET_TBL++;
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_DET_TBL));
            INT_DET_TBL++;
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_DET_TBL));

            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;
            intConMesRep++;
    }}
  }

            ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" TOTALES " );
            objTblHeaColGrpAmeSur.setHeight(16);
            INT_DET_TBL++;
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_DET_TBL));
            INT_DET_TBL++;
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_DET_TBL));
            INT_DET_TBL++;
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_DET_TBL));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;


  ArrayList arlColHid=new ArrayList();

   arlColHid.add(""+INT_TBL2_CODREG);
   arlColHid.add(""+INT_TBL2_CTNCOMORI);
   arlColHid.add(""+INT_TBL2_CANCOMORI);
   
   if(intTipConf==1){
      if(intConMesRep == 1){
         arlColHid.add(""+INT_TBL2_TOTUNI);
         arlColHid.add(""+INT_TBL2_TOTVEC);
         arlColHid.add(""+INT_TBL2_TOTMES);
       }
   }else{
         arlColHid.add(""+INT_TBL2_TOTUNI);
         arlColHid.add(""+INT_TBL2_TOTVEC);
         arlColHid.add(""+INT_TBL2_TOTMES);
         arlColHid.add(""+INT_TBL2_STKACT);
         arlColHid.add(""+INT_TBL2_NUMMRE);
         arlColHid.add(""+INT_TBL2_PROCAL);
         arlColHid.add(""+INT_TBL2_PROMAN);
         arlColHid.add(""+INT_TBL2_STKMIN);
   }
   
    objTblMod2.setSystemHiddenColumns(arlColHid, tblDat);
    arlColHid=null;

    objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Promedio Mensual de Ventas " );
    objTblHeaColGrpAmeSur.setHeight(16);
    objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL2_PROCAL));
    objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL2_PROMAN));
    objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
    objTblHeaColGrpAmeSur=null;


//**************************************************

      tblDat.getTableHeader().setReorderingAllowed(false);
      // objMouMotAda=new ZafMouMotAda();
      //  tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

      //Configurar JTable: Establecer columnas editables.
      Vector vecAux=new Vector();
      if(intTipConf!=1)
        vecAux.add("" + INT_TBL2_BUTREP);

       vecAux.add("" + INT_TBL2_CANCOM);
       vecAux.add("" + INT_TBL2_CTNCOM);
      objTblMod2.setColumnasEditables(vecAux);
      vecAux=null;

      objTblEdi=new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

      objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
      tcmAux.getColumn(INT_TBL2_CTNCOM).setCellEditor(objTblCelEdiTxt);
      objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
         int intCelSel=tblDat.getSelectedRow();
              String strCanNumCom=(tblDat.getValueAt(intCelSel, INT_TBL2_CTNCOM)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL2_CTNCOM).toString());
              String strCanRep=(tblDat.getValueAt(intCelSel, INT_TBL2_CANCOM)==null?"":tblDat.getValueAt(intCelSel, INT_TBL2_CANCOM).toString());
              String strCanCom=(tblDat.getValueAt(intCelSel, INT_TBL2_NTCOMP)==null?"":tblDat.getValueAt(intCelSel, INT_TBL2_NTCOMP).toString());
              String strCanNumComOri=(tblDat.getValueAt(intCelSel, INT_TBL2_CTNCOMORI)==null?"":tblDat.getValueAt(intCelSel, INT_TBL2_CTNCOMORI).toString());

              double dblCanNumCom = objUti.redondear( Double.parseDouble((strCanNumCom.equals("")?"0":strCanNumCom)),4);
              double dblCanNumComOri = objUti.redondear( Double.parseDouble((strCanNumComOri.equals("")?"0":strCanNumComOri)),4);
              double dblCanRep = objUti.redondear( Double.parseDouble((strCanRep.equals("")?"0":strCanRep)),4);
              double dblCanCom = objUti.redondear( Double.parseDouble((strCanCom.equals("")?"0":strCanCom)),4);

             if(dblCanNumCom > dblCanNumComOri ){

               if( (dblCanNumCom+dblCanCom) >  dblCanRep  ){
                  if(dblCanNumCom > 0 )
                     MensajeInf("No puede exceder a la cantidad de reposición.\nVerifique los datos he intente nuevamente.");
                   else  MensajeInf("No puede exceder a la cantidad origen.\nVerifique los datos he intente nuevamente.");
                  tblDat.setValueAt( ""+dblCanNumComOri , intCelSel, INT_TBL2_CTNCOM);
               }

             }

      }
      });
      objTblCelEdiTxt=null;

      objTblEdi=new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

      objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
      tcmAux.getColumn(INT_TBL2_NUMMRE).setCellEditor(objTblCelEdiTxt);
      objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
       
      }
      });
      objTblCelEdiTxt=null;


      objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
      tcmAux.getColumn(INT_TBL2_CANCOM).setCellEditor(objTblCelEdiTxt);
      objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
              int intCelSel=tblDat.getSelectedRow();
              String strCanCom=(tblDat.getValueAt(intCelSel, INT_TBL2_CANCOM)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL2_CANCOM).toString());
              String strCanComOri=(tblDat.getValueAt(intCelSel, INT_TBL2_CANCOMORI)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL2_CANCOMORI).toString());
              String strCanTotCom=(tblDat.getValueAt(intCelSel, INT_TBL2_NTCOMP)==null?"":tblDat.getValueAt(intCelSel, INT_TBL2_NTCOMP).toString());
              String strCanNumCom=(tblDat.getValueAt(intCelSel, INT_TBL2_CTNCOM)==null?"":tblDat.getValueAt(intCelSel, INT_TBL2_CTNCOM).toString());

              double dblCanCom = objUti.redondear( Double.parseDouble((strCanCom.equals("")?"0":strCanCom)),4);
              double dblCanComOri = objUti.redondear( Double.parseDouble((strCanComOri.equals("")?"0":strCanComOri)),4);
              double dblCanNumCom = objUti.redondear( Double.parseDouble((strCanNumCom.equals("")?"0":strCanNumCom)),4);
              double dblCanTotCom = objUti.redondear( Double.parseDouble((strCanTotCom.equals("")?"0":strCanTotCom)),4);

              if(dblCanCom < dblCanComOri ){
               if( (dblCanNumCom+dblCanTotCom) >  dblCanCom  ){
                  if(dblCanCom > 0 )
                     MensajeInf("No puede exceder a la cantidad de compra.\nVerifique los datos he intente nuevamente.");
                   else  MensajeInf("No puede exceder a la cantidad origen.\nVerifique los datos he intente nuevamente.");

                 tblDat.setValueAt( ""+dblCanComOri , intCelSel, INT_TBL2_CANCOM);
               }else tblDat.setValueAt( ""+dblCanCom , intCelSel, INT_TBL2_CANCOMORI);
             }else tblDat.setValueAt( ""+dblCanCom , intCelSel, INT_TBL2_CANCOMORI);

      }
      });
      objTblCelEdiTxt=null;


      objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
      tcmAux.getColumn(INT_TBL2_PROMAN).setCellEditor(objTblCelEdiTxt);
      objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
          tblDat.setValueAt("", tblDat.getSelectedRow(), INT_TBL2_STKMIN);
          double dlbProMan = Double.parseDouble(  //tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROMAN).toString());
             ((tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROMAN)==null || tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROMAN).equals(""))?"0":
               tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROMAN).toString()));

          double dlbNumMesRep = Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_NUMMRE).toString());
          double dlbRep = (dlbProMan * dlbNumMesRep )-Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_STKACT).toString());

           if(dlbProMan<=0){
              double dlbProCal = Double.parseDouble(
             ((tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROCAL)==null || tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROCAL).equals(""))?"0":
              tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROCAL).toString()));
              dlbRep = (dlbProCal * dlbNumMesRep )-Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_STKACT).toString());
          }

          if(dlbRep<=0) tblDat.setValueAt("" , tblDat.getSelectedRow(), INT_TBL2_REPOSI);
           else tblDat.setValueAt(""+dlbRep , tblDat.getSelectedRow(), INT_TBL2_REPOSI);

      }
      });
      objTblCelEdiTxt=null;




      objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
      tcmAux.getColumn(INT_TBL2_STKMIN).setCellEditor(objTblCelEdiTxt);
      objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
         tblDat.setValueAt("", tblDat.getSelectedRow(), INT_TBL2_PROMAN);
          double dlbStkMin = Double.parseDouble(  //tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_STKMIN).toString());
             ((tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_STKMIN)==null || tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_STKMIN).equals(""))?"0":
               tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_STKMIN).toString()));

          double dlbStkAct = Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_STKACT).toString());
          double dlbRep = dlbStkMin -  dlbStkAct;


            if(dlbStkMin<=0){
              double dlbProCal = Double.parseDouble(
             ((tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROCAL)==null || tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROCAL).equals(""))?"0":
              tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROCAL).toString()));
              double dlbNumMesRep = Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_NUMMRE).toString());
              dlbRep = (dlbProCal * dlbNumMesRep )-dlbStkAct;
            }


          if(dlbRep<=0) tblDat.setValueAt("" , tblDat.getSelectedRow(), INT_TBL2_REPOSI);
           else tblDat.setValueAt(""+dlbRep , tblDat.getSelectedRow(), INT_TBL2_REPOSI);

      }
      });
      objTblCelEdiTxt=null;

      tcmAux=null;


    setEditable2(true);


    ZafTblPopMn = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
    ZafTblPopMn.setInsertarFilaVisible(false);
    ZafTblPopMn.setInsertarFilasVisible(false);


 }catch (Exception e)  {  objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}

private boolean confirgurarTblDatMen(int intTipConf){
 boolean blnRes=false;
 try{
    Vector vecCab2=new Vector();    //Almacena las cabeceras
    vecCab2.clear();
    vecCab2.add(INT_TBL2_LINEA,"");
    vecCab2.add(INT_TBL2_COSIS,"Cod.Itm.");
    vecCab2.add(INT_TBL2_COALT,"Cod.Alt.");
    vecCab2.add(INT_TBL2_NOMBR,"Nombre");
    vecCab2.add(INT_TBL2_UNIMED,"Unidad");

    int INT_DET_TBL= INT_TBL2_UNIMED;

    if(intTipConf==1){
     for(int i=0; i< tblAnMe.getRowCount(); i++){
      if(tblAnMe.getValueAt(i,INT_TBL_CHK).toString().equals("true")){
         INT_DET_TBL++;
         vecCab2.add(INT_DET_TBL,"Unidad");
         INT_DET_TBL++;
         vecCab2.add(INT_DET_TBL,"Veces");
         INT_DET_TBL++;
         vecCab2.add(INT_DET_TBL,"Meses");
     }}
    }

      

     INT_DET_TBL++;
     INT_TBL2_TOTUNI=INT_DET_TBL;
     vecCab2.add(INT_TBL2_TOTUNI,"Unidad");
     INT_DET_TBL++;
     INT_TBL2_TOTVEC=INT_DET_TBL;
     vecCab2.add(INT_TBL2_TOTVEC,"Veces");
     INT_DET_TBL++;
     INT_TBL2_TOTMES=INT_DET_TBL;
     vecCab2.add(INT_TBL2_TOTMES,"Meses");
     INT_DET_TBL++;
     INT_TBL2_STKACT=INT_DET_TBL;
     vecCab2.add(INT_TBL2_STKACT,"Stk.Act.");
     INT_DET_TBL++;
     INT_TBL2_NUMMRE=INT_DET_TBL;
     vecCab2.add(INT_TBL2_NUMMRE,"Num.Mes.Rep");
     INT_DET_TBL++;
     INT_TBL2_PROCAL=INT_DET_TBL;
     vecCab2.add(INT_TBL2_PROCAL,"Pro.Cal");
     INT_DET_TBL++;
     INT_TBL2_PROMAN=INT_DET_TBL;
     vecCab2.add(INT_TBL2_PROMAN,"Pro.Man");
     INT_DET_TBL++;
     INT_TBL2_STKMIN=INT_DET_TBL;
     vecCab2.add(INT_TBL2_STKMIN,"Minimo");
     INT_DET_TBL++;
     INT_TBL2_REPOSI=INT_DET_TBL;
     vecCab2.add(INT_TBL2_REPOSI,"Reposicion");
     INT_DET_TBL++;
     INT_TBL2_CANCOM=INT_DET_TBL;
     vecCab2.add(INT_TBL2_CANCOM,"Can.Com");
     INT_DET_TBL++;
     INT_TBL2_BUTREP=INT_DET_TBL;
     vecCab2.add(INT_TBL2_BUTREP,"..");
     INT_DET_TBL++;
     INT_TBL2_PRVELE=INT_DET_TBL;
     vecCab2.add(INT_TBL2_PRVELE,"Prv.Ele.");
     INT_DET_TBL++;
     INT_TBL2_COTGEN=INT_DET_TBL;
     vecCab2.add(INT_TBL2_COTGEN,"Cot.Gen.");
     INT_DET_TBL++;
     INT_TBL2_COTING=INT_DET_TBL;
     vecCab2.add(INT_TBL2_COTING,"Cot.Ing.");
     INT_DET_TBL++;
     INT_TBL2_NTCCAN=INT_DET_TBL;
     vecCab2.add(INT_TBL2_NTCCAN,"Cot.Can.");
     INT_DET_TBL++;
     INT_TBL2_NTCOMP=INT_DET_TBL;
     vecCab2.add(INT_TBL2_NTCOMP,"Can.Tot.Com");
     INT_DET_TBL++;
     INT_TBL2_CTNCOM=INT_DET_TBL;
     vecCab2.add(INT_TBL2_CTNCOM,"Can.Tot.Num.Com");
     INT_DET_TBL++;
     INT_TBL2_CODREG=INT_DET_TBL;
     vecCab2.add(INT_TBL2_CODREG,"");
     INT_DET_TBL++;
     INT_TBL2_CTNCOMORI=INT_DET_TBL;
     vecCab2.add(INT_TBL2_CTNCOMORI,"");

     INT_DET_TBL++;
     INT_TBL2_CANCOMORI=INT_DET_TBL;
     vecCab2.add(INT_TBL2_CANCOMORI,"");

    objTblMod2=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod2.setHeader(vecCab2);
    tblDat.setModel(objTblMod2);

     tblDat.setRowSelectionAllowed(true);
     tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
     new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL2_LINEA);

    //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
    objTblMod2.setColumnDataType(INT_TBL2_PROMAN, objTblMod2.INT_COL_DBL, new Integer(0), null);
    objTblMod2.setColumnDataType(INT_TBL2_STKMIN, objTblMod2.INT_COL_DBL, new Integer(0), null);
    objTblMod2.setColumnDataType(INT_TBL2_CTNCOM, objTblMod2.INT_COL_DBL, new Integer(0), null);


   //Configurar ZafTblMod: Establecer las columnas obligatorias.
    java.util.ArrayList arlAux=new java.util.ArrayList();
    arlAux.add("" + INT_TBL2_PROMAN);
    arlAux.add("" + INT_TBL2_STKMIN);
    objTblMod2.setColumnasObligatorias(arlAux);
    arlAux=null;
    //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
    objTblMod2.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());

     tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
   // tblDat.getTableHeader().setReorderingAllowed(false);

    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    tcmAux.getColumn(INT_TBL2_LINEA).setPreferredWidth(30);
    tcmAux.getColumn(INT_TBL2_COSIS).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL2_COALT).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL2_NOMBR).setPreferredWidth(130);
    tcmAux.getColumn(INT_TBL2_UNIMED).setPreferredWidth(50);

    int intColTblDat=0;
          for( intColTblDat=INT_TBL2_UNIMED+1; intColTblDat<=INT_DET_TBL ; intColTblDat++){
                 objTblCelRenLbl=null;

                 objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
                 objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
                 objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                 objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
                 objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
                 tcmAux.getColumn( intColTblDat ).setCellRenderer(objTblCelRenLbl);
                 //Tamaño de las celdas
                 if(INT_TBL2_BUTREP==intColTblDat)
                   tcmAux.getColumn(intColTblDat).setPreferredWidth(25);
                 else  tcmAux.getColumn(intColTblDat).setPreferredWidth(60);
          }


    Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
    tcmAux.getColumn(INT_TBL2_BUTREP).setCellRenderer(objTblCelRenBut);
    objTblCelRenBut=null;
    new ButBod(tblDat, INT_TBL2_BUTREP);   //*****

      tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

    new ZafTblOrd(tblDat);
    new ZafTblBus(tblDat);


//*************************************************

    ArrayList arlCam=new ArrayList();
    String stnAni="2000";
    int xx=1;
    for(int i=0; i< tblAnMe.getRowCount(); i++){
     if(tblAnMe.getValueAt(i,INT_TBL_CHK).toString().equals("true")){
           String strAn=tblAnMe.getValueAt(i,INT_TBL_ANI).toString();
            if(!(stnAni.equals(strAn))){
                xx=1;
                stnAni=strAn;
            } else {  stnAni=strAn; xx=0; }
            if(xx==1)  arlCam.add(strAn);
     }}



    objTblFilCab=new ZafTblFilCab(tblDat);
    tcmAux.getColumn(INT_TBL2_LINEA).setCellRenderer(objTblFilCab);

    ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
    objTblHeaGrp.setHeight(16*3);
    INT_DET_TBL= INT_TBL2_UNIMED;

  int intConMesRep=0;

  if(intTipConf==1){

      for(int z=0; z< arlCam.size(); z++){
            String strAnio = arlCam.get(z).toString();
            ZafTblHeaColGrp  objTblHeaColGrpPri=new ZafTblHeaColGrp(" Año "+strAnio );
            objTblHeaColGrpPri.setHeight(16);
            for(int i=0; i< tblAnMe.getRowCount(); i++){
              if(tblAnMe.getValueAt(i,INT_TBL_CHK).toString().equals("true")){
                 String strAn=tblAnMe.getValueAt(i,INT_TBL_ANI).toString();
                 String strMes=tblAnMe.getValueAt(i,INT_TBL_MES).toString();
                if(strAnio.equals(strAn)){
                    ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp( strMes );
                    objTblHeaColGrpAmeSur.setHeight(16);
                    INT_DET_TBL++;
                    objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_DET_TBL));
                    INT_DET_TBL++;
                    objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_DET_TBL));
                    INT_DET_TBL++;
                    objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_DET_TBL));
                    objTblHeaColGrpPri.add(objTblHeaColGrpAmeSur);
                    objTblHeaColGrpAmeSur=null;
                    intConMesRep++;
                  }
             }}
                 objTblHeaGrp.addColumnGroup(objTblHeaColGrpPri);
                 objTblHeaColGrpPri=null;
       }
       arlCam=null;
  }

            ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" TOTALES " );
            objTblHeaColGrpAmeSur.setHeight(16);
            INT_DET_TBL++;
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_DET_TBL));
            INT_DET_TBL++;
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_DET_TBL));
            INT_DET_TBL++;
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_DET_TBL));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;


   ArrayList arlColHid=new ArrayList();
    arlColHid.add(""+INT_TBL2_CTNCOMORI);
    arlColHid.add(""+INT_TBL2_CODREG);
    arlColHid.add(""+INT_TBL2_CANCOMORI);
   if(intTipConf==1){
        if(intConMesRep == 1){
         arlColHid.add(""+INT_TBL2_TOTUNI);
         arlColHid.add(""+INT_TBL2_TOTVEC);
         arlColHid.add(""+INT_TBL2_TOTMES);
        }
   }else{
         arlColHid.add(""+INT_TBL2_TOTUNI);
         arlColHid.add(""+INT_TBL2_TOTVEC);
         arlColHid.add(""+INT_TBL2_TOTMES);
         arlColHid.add(""+INT_TBL2_STKACT);
         arlColHid.add(""+INT_TBL2_NUMMRE);
         arlColHid.add(""+INT_TBL2_PROCAL);
         arlColHid.add(""+INT_TBL2_PROMAN);
         arlColHid.add(""+INT_TBL2_STKMIN);
   }

    objTblMod2.setSystemHiddenColumns(arlColHid, tblDat);
    arlColHid=null;




            objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Promedio Mensual de Ventas " );
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL2_PROCAL));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL2_PROMAN));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;


//**************************************************

      tblDat.getTableHeader().setReorderingAllowed(false);
      // objMouMotAda=new ZafMouMotAda();
      //  tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

      //Configurar JTable: Establecer columnas editables.
      Vector vecAux=new Vector();
      if(intTipConf!=1)
        vecAux.add("" + INT_TBL2_BUTREP);

       vecAux.add("" + INT_TBL2_CANCOM);
       vecAux.add("" + INT_TBL2_CTNCOM);
      objTblMod2.setColumnasEditables(vecAux);
      vecAux=null;

      objTblEdi=new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

      objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
      tcmAux.getColumn(INT_TBL2_CTNCOM).setCellEditor(objTblCelEdiTxt);
      objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

           int intCelSel=tblDat.getSelectedRow();
              String strCanNumCom=(tblDat.getValueAt(intCelSel, INT_TBL2_CTNCOM)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL2_CTNCOM).toString());
              String strCanRep=(tblDat.getValueAt(intCelSel, INT_TBL2_REPOSI)==null?"":tblDat.getValueAt(intCelSel, INT_TBL2_REPOSI).toString());
              String strCanCom=(tblDat.getValueAt(intCelSel, INT_TBL2_NTCOMP)==null?"":tblDat.getValueAt(intCelSel, INT_TBL2_NTCOMP).toString());
              String strCanNumComOri=(tblDat.getValueAt(intCelSel, INT_TBL2_CTNCOMORI)==null?"":tblDat.getValueAt(intCelSel, INT_TBL2_CTNCOMORI).toString());

              double dblCanNumCom = objUti.redondear( Double.parseDouble((strCanNumCom.equals("")?"0":strCanNumCom)),4);
              double dblCanNumComOri = objUti.redondear( Double.parseDouble((strCanNumComOri.equals("")?"0":strCanNumComOri)),4);
              double dblCanRep = objUti.redondear( Double.parseDouble((strCanRep.equals("")?"0":strCanRep)),4);
              double dblCanCom = objUti.redondear( Double.parseDouble((strCanCom.equals("")?"0":strCanCom)),4);

             if(dblCanNumCom > dblCanNumComOri ){

               if( (dblCanNumCom+dblCanCom) >  dblCanRep  ){
                  if(dblCanNumCom > 0 )
                     MensajeInf("No puede exceder a la cantidad de reposición.\nVerifique los datos he intente nuevamente.");
                   else  MensajeInf("No puede exceder a la cantidad origen.\nVerifique los datos he intente nuevamente.");
                  tblDat.setValueAt( ""+dblCanNumComOri , intCelSel, INT_TBL2_CTNCOM);
               }

             }
      }
      });
      objTblCelEdiTxt=null;

      objTblEdi=new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

      objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
      tcmAux.getColumn(INT_TBL2_NUMMRE).setCellEditor(objTblCelEdiTxt);
      objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

      }
      });
      objTblCelEdiTxt=null;



      objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
      tcmAux.getColumn(INT_TBL2_CANCOM).setCellEditor(objTblCelEdiTxt);
      objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
              int intCelSel=tblDat.getSelectedRow();
              String strCanCom=(tblDat.getValueAt(intCelSel, INT_TBL2_CANCOM)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL2_CANCOM).toString());
              String strCanComOri=(tblDat.getValueAt(intCelSel, INT_TBL2_CANCOMORI)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL2_CANCOMORI).toString());
              String strCanTotCom=(tblDat.getValueAt(intCelSel, INT_TBL2_NTCOMP)==null?"":tblDat.getValueAt(intCelSel, INT_TBL2_NTCOMP).toString());
              String strCanNumCom=(tblDat.getValueAt(intCelSel, INT_TBL2_CTNCOM)==null?"":tblDat.getValueAt(intCelSel, INT_TBL2_CTNCOM).toString());

              double dblCanCom = objUti.redondear( Double.parseDouble((strCanCom.equals("")?"0":strCanCom)),4);
              double dblCanComOri = objUti.redondear( Double.parseDouble((strCanComOri.equals("")?"0":strCanComOri)),4);
              double dblCanNumCom = objUti.redondear( Double.parseDouble((strCanNumCom.equals("")?"0":strCanNumCom)),4);
              double dblCanTotCom = objUti.redondear( Double.parseDouble((strCanTotCom.equals("")?"0":strCanTotCom)),4);

              if(dblCanCom < dblCanComOri ){
               if( (dblCanNumCom+dblCanTotCom) >  dblCanCom  ){
                  if(dblCanCom > 0 )
                     MensajeInf("No puede exceder a la cantidad de compra.\nVerifique los datos he intente nuevamente.");
                   else  MensajeInf("No puede exceder a la cantidad origen.\nVerifique los datos he intente nuevamente.");

                 tblDat.setValueAt( ""+dblCanComOri , intCelSel, INT_TBL2_CANCOM);
               }else tblDat.setValueAt( ""+dblCanCom , intCelSel, INT_TBL2_CANCOMORI);
             }else tblDat.setValueAt( ""+dblCanCom , intCelSel, INT_TBL2_CANCOMORI);

      }
      });
      objTblCelEdiTxt=null;


      objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
      tcmAux.getColumn(INT_TBL2_PROMAN).setCellEditor(objTblCelEdiTxt);
      objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
          tblDat.setValueAt("", tblDat.getSelectedRow(), INT_TBL2_STKMIN);
          double dlbProMan = Double.parseDouble(  //tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROMAN).toString());
             ((tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROMAN)==null || tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROMAN).equals(""))?"0":
               tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROMAN).toString()));

          double dlbNumMesRep = Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_NUMMRE).toString());
          double dlbRep = (dlbProMan * dlbNumMesRep )-Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_STKACT).toString());

           if(dlbProMan<=0){
              double dlbProCal = Double.parseDouble(
             ((tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROCAL)==null || tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROCAL).equals(""))?"0":
              tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROCAL).toString()));
              dlbRep = (dlbProCal * dlbNumMesRep )-Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_STKACT).toString());
          }

          if(dlbRep<=0) tblDat.setValueAt("" , tblDat.getSelectedRow(), INT_TBL2_REPOSI);
           else tblDat.setValueAt(""+dlbRep , tblDat.getSelectedRow(), INT_TBL2_REPOSI);

      }
      });
      objTblCelEdiTxt=null;




      objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
      tcmAux.getColumn(INT_TBL2_STKMIN).setCellEditor(objTblCelEdiTxt);
      objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
         tblDat.setValueAt("", tblDat.getSelectedRow(), INT_TBL2_PROMAN);
          double dlbStkMin = Double.parseDouble(  //tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_STKMIN).toString());
             ((tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_STKMIN)==null || tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_STKMIN).equals(""))?"0":
               tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_STKMIN).toString()));

          double dlbStkAct = Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_STKACT).toString());
          double dlbRep = dlbStkMin -  dlbStkAct;


            if(dlbStkMin<=0){
              double dlbProCal = Double.parseDouble(
             ((tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROCAL)==null || tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROCAL).equals(""))?"0":
              tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_PROCAL).toString()));
              double dlbNumMesRep = Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL2_NUMMRE).toString());
              dlbRep = (dlbProCal * dlbNumMesRep )-dlbStkAct;
            }


          if(dlbRep<=0) tblDat.setValueAt("" , tblDat.getSelectedRow(), INT_TBL2_REPOSI);
           else tblDat.setValueAt(""+dlbRep , tblDat.getSelectedRow(), INT_TBL2_REPOSI);

      }
      });
      objTblCelEdiTxt=null;

      tcmAux=null;


    setEditable2(true);


    ZafTblPopMn = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
    ZafTblPopMn.setInsertarFilaVisible(false);
    ZafTblPopMn.setInsertarFilasVisible(false);


 }catch (Exception e)  {  objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}


private boolean refrescaDatos(java.sql.Connection conn, java.sql.ResultSet rstDatCla){
    boolean blnRes=false;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSql="";
    try{
       if(conn!=null){
          stmLoc=conn.createStatement();

        txtCodBod.setText( ""+rstDatCla.getString("co_bod") );
        txtNumDoc.setText( ""+rstDatCla.getString("ne_numdoc") );
        txtCodDoc.setText( ""+rstDatCla.getString("co_doc") );

        spnNumMesRep.setValue(new Integer(rstDatCla.getInt("ne_nummesrep")) );

        if(rstDatCla.getDate("fe_doc")==null){
          txtFecDoc.setText("");
        }else{
            java.util.Date dateObj = rstDatCla.getDate("fe_doc");
            java.util.Calendar calObj = java.util.Calendar.getInstance();
            calObj.setTime(dateObj);
            txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                              calObj.get(java.util.Calendar.MONTH)+1     ,
                              calObj.get(java.util.Calendar.YEAR)        );
        }

          java.util.Vector vecData = new java.util.Vector();
          confirgurarTblDatAnu(2);

          cargarTabPrv(conn, rstDatCla.getInt("co_emp"), rstDatCla.getInt("co_loc"), rstDatCla.getInt("co_tipdoc"),  rstDatCla.getInt("co_doc")  );
          cargarTabCotEmi(conn, rstDatCla.getInt("co_emp"), rstDatCla.getInt("co_loc"), rstDatCla.getInt("co_tipdoc"),  rstDatCla.getInt("co_doc")  );
          cargarTabOrdCom(conn, rstDatCla.getInt("co_emp"), rstDatCla.getInt("co_loc"), rstDatCla.getInt("co_tipdoc"),  rstDatCla.getInt("co_doc")  );

          

          strSql="SELECT a.co_itm, a2.tx_codalt, a2.tx_nomitm, var.tx_descor, a.nd_canrep, a.ne_numtotprvele, a.ne_numtotcotgen, " +
          " a.ne_numtotcoting, a.ne_numtotcotcan, a.nd_cantotcom, a.nd_cantotnuncom , a.co_reg, a.nd_cancom " +
          " FROM tbm_detrepinvprv AS a " +
          " inner join tbm_inv as a2 on (a2.co_emp=a.co_emp and a2.co_itm=a.co_itm) " +
          " left join tbm_var as var on (var.co_reg=a2.co_uni) " +
          " WHERE a.co_emp="+rstDatCla.getInt("co_emp")+" and a.co_loc="+rstDatCla.getInt("co_loc")+" and a.co_tipdoc="+rstDatCla.getInt("co_tipdoc")+" " +
          " and a.co_doc="+rstDatCla.getInt("co_doc")+" ORDER BY a.co_reg ";

          rstLoc=stmLoc.executeQuery(strSql);
          while(rstLoc.next()){

           java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL2_LINEA, "");
            vecReg.add(INT_TBL2_COSIS, rstLoc.getString("co_itm") );
            vecReg.add(INT_TBL2_COALT, rstLoc.getString("tx_codalt") );
            vecReg.add(INT_TBL2_NOMBR, rstLoc.getString("tx_nomitm") );
            vecReg.add(INT_TBL2_UNIMED, rstLoc.getString("tx_descor") );
            vecReg.add(INT_TBL2_TOTUNI, "");
            vecReg.add(INT_TBL2_TOTVEC, "");
            vecReg.add(INT_TBL2_TOTMES, "");
            vecReg.add(INT_TBL2_STKACT, "");
            vecReg.add(INT_TBL2_NUMMRE, "");
            vecReg.add(INT_TBL2_PROCAL, "");
            vecReg.add(INT_TBL2_PROMAN, "");
            vecReg.add(INT_TBL2_STKMIN, "");
            vecReg.add(INT_TBL2_REPOSI, rstLoc.getString("nd_canrep") );
            vecReg.add(INT_TBL2_CANCOM, rstLoc.getString("nd_cancom") );
            vecReg.add(INT_TBL2_BUTREP, "..");
            vecReg.add(INT_TBL2_PRVELE, rstLoc.getString("ne_numtotprvele") );
            vecReg.add(INT_TBL2_COTGEN, rstLoc.getString("ne_numtotcotgen") );
            vecReg.add(INT_TBL2_COTING, rstLoc.getString("ne_numtotcoting") );
            vecReg.add(INT_TBL2_NTCCAN, rstLoc.getString("ne_numtotcotcan") );
            vecReg.add(INT_TBL2_NTCOMP, rstLoc.getString("nd_cantotcom") );
            vecReg.add(INT_TBL2_CTNCOM, rstLoc.getString("nd_cantotnuncom") );
            vecReg.add(INT_TBL2_CODREG, rstLoc.getString("co_reg") );
            vecReg.add(INT_TBL2_CTNCOMORI, rstLoc.getString("nd_cantotnuncom") );
            vecReg.add(INT_TBL2_CANCOMORI, rstLoc.getString("nd_cancom") );

           vecData.add(vecReg);

          }
          rstLoc.close();
          rstLoc=null;

          objTblMod2.setData(vecData);
          tblDat.setModel(objTblMod2);


          stmLoc.close();
          stmLoc=null;

          int intPosRel=rstDatCla.getRow();
          rstDatCla.last();
          objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstDatCla.getRow());
          rstDatCla.absolute(intPosRel);

  }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}


private void cargarTabOrdCom(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSql="";
    try{
       if(conn!=null){
          stmLoc=conn.createStatement();
          java.util.Vector vecData = new java.util.Vector();

          strSql="SELECT a1.co_loc, a1.co_tipdoc, a2.tx_descor, a2.tx_deslar, a1.co_doc, a1.ne_numdoc, a1.fe_doc, a1.co_cli, a1.tx_nomcli, a1.nd_tot " +
          " FROM tbm_comemirepinvprv as a " +
          " INNER JOIN tbm_cabmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel) " +
          " INNER JOIN tbm_cabtipdoc as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc ) " +
          " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" ";
    
          rstLoc=stmLoc.executeQuery(strSql);
          while(rstLoc.next()){

           java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL_OCLIN, "");
            vecReg.add(INT_TBL_OCCODLOC, rstLoc.getString("co_loc") );
            vecReg.add(INT_TBL_OCTIPDOC, rstLoc.getString("co_tipdoc") );
            vecReg.add(INT_TBL_OCDTIPDOC, rstLoc.getString("tx_descor") );
            vecReg.add(INT_TBL_OCDLTD, rstLoc.getString("tx_deslar") );
            vecReg.add(INT_TBL_OCCODOC,  rstLoc.getString("co_doc") );
            vecReg.add(INT_TBL_OCNUDOC, rstLoc.getString("ne_numdoc") );
            vecReg.add(INT_TBL_OCFDOC, rstLoc.getString("fe_doc") );
            vecReg.add(INT_TBL_OCCOPRV,  rstLoc.getString("co_cli") );
            vecReg.add(INT_TBL_OCNOPRV, rstLoc.getString("tx_nomcli") );
            vecReg.add(INT_TBL_OCTOT,  rstLoc.getString("nd_tot") );
            vecReg.add(INT_TBL_OCBOC, ".." );
           vecData.add(vecReg);

          }
          rstLoc.close();
          rstLoc=null;

          objTblModOrdCom.setData(vecData);
          tblOrdCom.setModel(objTblModOrdCom);

          stmLoc.close();
          stmLoc=null;

  }}catch(java.sql.SQLException Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) {   objUti.mostrarMsgErr_F1(this, Evt);  }
}

private void cargarTabCotEmi(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSql="";
    try{
       if(conn!=null){
          stmLoc=conn.createStatement();
          java.util.Vector vecData = new java.util.Vector();

          strSql=" select a.co_locrel, a1.co_cot, a1.fe_cot, a1.co_prv, a2.tx_nom, a1.nd_tot  from tbm_cotemirepinvprv as a " +
          "  inner join tbm_cabcotcom as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_cot=a.co_cotrel ) " +
          "  inner join tbm_cli as a2 on (a2.co_emp=a1.co_emp and a2.co_cli=a1.co_prv ) " +
          "  where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" ";

          rstLoc=stmLoc.executeQuery(strSql);
          while(rstLoc.next()){

           java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL_CELIN, "");
            vecReg.add(INT_TBL_CECOLOC, rstLoc.getString("co_locrel") );
            vecReg.add(INT_TBL_CECODOC, rstLoc.getString("co_cot") );
            vecReg.add(INT_TBL_CEFECOT, rstLoc.getString("fe_cot") );
            vecReg.add(INT_TBL_CECOPRV, rstLoc.getString("co_prv") );
            vecReg.add(INT_TBL_CENOPRV,  rstLoc.getString("tx_nom") );
            vecReg.add(INT_TBL_CEBUTCOT, ".." );
           vecData.add(vecReg);

          }
          rstLoc.close();
          rstLoc=null;

          objTblModCotEmi.setData(vecData);
          tblCotEmi.setModel(objTblModCotEmi);

          stmLoc.close();
          stmLoc=null;

  }}catch(java.sql.SQLException Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) {   objUti.mostrarMsgErr_F1(this, Evt);  }
}

private void cargarTabPrv(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSql="";
    try{
       if(conn!=null){
          stmLoc=conn.createStatement();
          java.util.Vector vecData = new java.util.Vector();

         strSql="SELECT  a1.co_prv, a2.tx_nom  FROM tbm_procomitmrepinvprv as a1 "+
         " inner join tbm_cli as a2 on (a2.co_emp=a1.co_emp and a2.co_cli=a1.co_prv ) " +
         " WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_tipdoc="+intCodTipDoc+" " +
         " AND a1.co_doc="+intCodDoc+"  GROUP BY a1.co_prv, a2.tx_nom  ORDER BY a2.tx_nom   ";

          rstLoc=stmLoc.executeQuery(strSql);
          while(rstLoc.next()){
           java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL_PRVLIN, "");
            vecReg.add(INT_TBL_PRVCOD, rstLoc.getString("co_prv") );
            vecReg.add(INT_TBL_PRVNOM,  rstLoc.getString("tx_nom") );
           vecData.add(vecReg);

          }
          rstLoc.close();
          rstLoc=null;

          objTblModPrv.setData(vecData);
          tblPrv.setModel(objTblModPrv);

          stmLoc.close();
          stmLoc=null;

  }}catch(java.sql.SQLException Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) {   objUti.mostrarMsgErr_F1(this, Evt);  }
}


 


private String FilSql() {
    String sqlFiltro = "";
    //Agregando filtro por Numero de Cotizacion

     if(!txtCodDoc.getText().equals(""))
            sqlFiltro+=" AND a.co_doc="+txtCodDoc.getText();


    return sqlFiltro ;
}





public void clickModificar(){

    java.awt.Color colBack;
    colBack = txtDesCodTitpDoc.getBackground();
    txtDesCodTitpDoc.setEditable(false);
    txtDesCodTitpDoc.setBackground(colBack);
    colBack=null;

    colBack = txtDesLarTipDoc.getBackground();
    txtDesLarTipDoc.setEditable(false);
    txtDesLarTipDoc.setBackground(colBack);
    colBack=null;

    butTipDoc.setEnabled(false);

    colBack = txtCodBod.getBackground();
    txtCodBod.setEditable(false);
    txtCodBod.setBackground(colBack);
    colBack=null;

    colBack = txtNomBod.getBackground();
    txtNomBod.setEditable(false);
    txtNomBod.setBackground(colBack);
    colBack=null;

    butBusBod.setEnabled(false);

    colBack = txtCodDoc.getBackground();
    txtCodDoc.setEditable(false);
    txtCodDoc.setBackground(colBack);
    colBack=null;

    butCot.setEnabled(true);
    butOrdCom.setEnabled(true);

 this.setEnabledConsultar(false);

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
            ocultarDat(false);
            
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

    if( intCol == INT_TBL2_LINEA )
        strMsg="";
    else if( intCol == INT_TBL2_COSIS )
            strMsg="Cóidigo Item.";
    else if( intCol == INT_TBL2_COALT )
            strMsg="Cóidigo alterno del item.";
    else if( intCol == INT_TBL2_NOMBR )
            strMsg="Nombre del Item ";
    else if( intCol == INT_TBL2_UNIMED )
            strMsg="Unidad de Medidad";
    else if( intCol == INT_TBL2_STKACT )
            strMsg="Stock Actual. ";
    else if( intCol == INT_TBL2_NUMMRE )
            strMsg="Número de meses a Reponer.";
    else if( intCol == INT_TBL2_PROCAL )
          strMsg="Promedio Mensula de Ventas ( Calculado ).";
    else if( intCol == INT_TBL2_PROMAN )
            strMsg="Promedio Mensula de Ventas ( Manual ).";
    else if( intCol == INT_TBL2_STKMIN )
            strMsg="Stock Minimo.";
    else if( intCol == INT_TBL2_REPOSI )
            strMsg="Cantidad a Reponer.";
    else if( intCol == INT_TBL2_PRVELE )
            strMsg="Número total de proveedores elegidos.";
    else if( intCol == INT_TBL2_COTGEN )
          strMsg="Número total de cotizaciones generadas.";
    else if( intCol == INT_TBL2_COTING )
            strMsg="Número total de cotizaciones ingresadas.";
    else if( intCol == INT_TBL2_NTCCAN )
            strMsg="Número total de cotizaciones canceladas.";
    else if( intCol == INT_TBL2_NTCOMP )
            strMsg="Cantidad total Comprada.";
    else if( intCol == INT_TBL2_CTNCOM )
            strMsg="Cantidad total numca Comprada.";
    else
        strMsg="";
      
    tblDat.getTableHeader().setToolTipText(strMsg);
}
}




private class ZafMouMotAdaOrdCom extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblOrdCom.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_OCLIN:
            strMsg="";
            break;
        case INT_TBL_OCCODLOC:
            strMsg="Cóidigo local.";
            break;
        case INT_TBL_OCDTIPDOC:
            strMsg="Descripción corta del tipo de documento.";
            break;
        case INT_TBL_OCDLTD:
            strMsg="Descripción larga del tipo de documento.";
            break;
        case INT_TBL_OCNUDOC:
            strMsg="Número del documento.";
            break;
        case INT_TBL_OCFDOC:
            strMsg="Fecha del documento.";
            break;
        case INT_TBL_OCCOPRV:
            strMsg="Código Proveedor";
            break;
        case INT_TBL_OCNOPRV:
            strMsg="Nombre Proveedor";
            break;
        case INT_TBL_OCTOT:
            strMsg="Valor Total de documento.";
            break;
        case INT_TBL_OCBOC:
            strMsg="Ver la Orden de Compra.";
            break;

        default:
            strMsg="";
            break;
    }
    tblOrdCom.getTableHeader().setToolTipText(strMsg);
}
}



private class ZafMouMotAdaCotEmi extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblCotEmi.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_CELIN:
            strMsg="";
            break;
        case INT_TBL_CECOLOC:
            strMsg="Cóidigo local.";
            break;
        case INT_TBL_CECODOC:
            strMsg="Número de la cotizacion.";
            break;
        case INT_TBL_CEFECOT:
            strMsg="Fecha de Cotización.";
            break;
        case INT_TBL_CECOPRV:
            strMsg="Código de proveedor.";
            break;
        case INT_TBL_CENOPRV:
            strMsg="Nombre de proveedor.";
            break;
        case INT_TBL_CEBUTCOT:
            strMsg="Ver la Cotización de Compra.";
            break;

        default:
            strMsg="";
            break;
    }
    tblCotEmi.getTableHeader().setToolTipText(strMsg);
}
}


     protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }
    

  
    
}
