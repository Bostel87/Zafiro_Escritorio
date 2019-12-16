/*  
 *  Created on 07 de septiembre de 2009, 10:10 PM
 */  
package Maestros.ZafMae55;
      
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import java.sql.Connection;
import java.sql.DriverManager;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import java.util.Vector;
import java.util.ArrayList;  //*******************
import Librerias.ZafVenCon.ZafVenCon; 
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
/**
 *
 * @author  Javier Ayapata
 */
public class ZafMae55 extends javax.swing.JInternalFrame
{

    Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
  
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblModCla;
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk  objTblRenChkClaA, objTblRenChkClaI;        //Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk  objTblEdiChkClaA, objTblEdiChkClaI;        //Editor: JButton en celda.
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut  objTblCelRenButPrv;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoPrv;

    //Constantes: Columnas del JTable:
    final int INT_TBL_LIN=0;                        //Línea
    final int INT_TBL_CODGRP =1;                    //Código del item (Sistema).
    final int INT_TBL_TXTGRP =2;                    //Código del item (Sistema).
    final int INT_TBL_CODCLA =3;                    //Código del item (Alterno).
    final int INT_TBL_TXTCLA =4;                    //Código del item (Alterno).


    //Constantes: Columnas del JTable:
    final int INT_TBL_LINITM=0;                        //Línea
    final int INT_TBL_CODITM =1;                    //Código del item (Sistema).
    final int INT_TBL_CODALT =2;                    //Código del item (Sistema).
    final int INT_TBL_BUTITM =3;                    //Código del item (Alterno).
    final int INT_TBL_NOMITM =4;                    //Código del item (Alterno).
    final int INT_TBL_ACTCLA =5;                    //Código del item (Alterno).
    final int INT_TBL_INACLA =6;                    //Código del item (Alterno).


    //Variables
    private ZafParSis objZafParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private Connection con;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.

    ZafVenCon objVenConGrp; //*****************
    ZafVenCon objVenConCla;
    ZafVenCon objVenConItm;
   
    String strCodGrp="",strDesGrp="";
    String strCodCla="",strDesCla="";

    String strCodBod="", strNomBod="";
    String strCodCli="";
    String strDesCli="";
    String strCodVen="";
    String strDesVen="";
    String strVersion=" v 0.4 ";


/** Crea una nueva instancia de la clase ZafIndRpt. */
public ZafMae55(ZafParSis obj)
{
 try{ /**/
    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
    objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        
    initComponents();

    objUti=new ZafUtil();
    strAux=objZafParSis.getNombreMenu();
    this.setTitle(strAux+" "+ strVersion );
    lblTit.setText(strAux);

     if( objZafParSis.getTipGrpClaInvPreUsr()=='P')
           radPub.setSelected(true);

         if( objZafParSis.getTipGrpClaInvPreUsr()=='R')
           radPri.setSelected(true);

  
      

  }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
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
        " INNER JOIN tbm_grpclainv as a1 on (a1.co_emp=a.co_emp and a1.co_grp=a.co_grp) " +
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

private boolean configurarTblRep(){
 boolean blnRes=true;
 try{
    //Configurar JTable: Establecer el modelo.
    vecCab=new Vector();  //Almacena las cabeceras
    vecCab.clear();
    vecCab.add(INT_TBL_LIN,"");
    vecCab.add(INT_TBL_CODGRP,"Cod.Grp.");
    vecCab.add(INT_TBL_TXTGRP,"Grupo.");
    vecCab.add(INT_TBL_CODCLA,"Cod.Cla");
    vecCab.add(INT_TBL_TXTCLA,"Clasificación");

   // vecCab.add(INT_TBL_DAT_TOT_COS,"Total.Costo");
    objTblMod=new ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);
    //Configurar JTable: Establecer tipo de selección.
    tblDat.setRowSelectionAllowed(true);
    tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    //Configurar JTable: Establecer la fila de cabecera.
    new ZafColNumerada(tblDat,INT_TBL_LIN);
    //Configurar JTable: Establecer el menú de contexto.
    new ZafTblPopMnu(tblDat);
    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    //Configurar JTable: Establecer el ancho de las columnas.
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();

    tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
    tcmAux.getColumn(INT_TBL_CODGRP).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_TXTGRP).setPreferredWidth(235);
    tcmAux.getColumn(INT_TBL_CODCLA).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_TXTCLA).setPreferredWidth(235);

    //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
    tblDat.getTableHeader().setReorderingAllowed(false);
    objMouMotAda=new ZafMouMotAda();
    tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

     //Configurar JTable: Establecer el ListSelectionListener.
    javax.swing.ListSelectionModel lsm=tblDat.getSelectionModel();
    lsm.addListSelectionListener(new ZafLisSelLis());

    //new ZafTblBus(tblDat);
    //new ZafTblOrd(tblDat);

    tcmAux=null;
    setEditable(true);
 }
 catch(Exception e) { blnRes=false;   objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}

/**
* Esta clase implementa la interface "ListSelectionListener" para determinar
* cambios en la selección. Es decir, cada vez que se selecciona una fila
* diferente en el JTable se ejecutará el "ListSelectionListener".
*/
private class ZafLisSelLis implements javax.swing.event.ListSelectionListener
{
public void valueChanged(javax.swing.event.ListSelectionEvent e)
{
javax.swing.ListSelectionModel lsm=(javax.swing.ListSelectionModel)e.getSource();
if (!lsm.isSelectionEmpty())
{

    if (chkMos.isSelected()){

        objTblModCla.removeAllRows();
        cargarItmCla();

    }

}
}
}


private void ConfigurarTblClasificacion() {
  try{
    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    vecCab.add(INT_TBL_LINITM,"");
    vecCab.add(INT_TBL_CODITM,"Cod.Itm");
    vecCab.add(INT_TBL_CODALT,"Cod.Alt.");
    vecCab.add(INT_TBL_BUTITM,"..");
    vecCab.add(INT_TBL_NOMITM,"Nom.Itm.");
    vecCab.add(INT_TBL_ACTCLA,"Activo.");
    vecCab.add(INT_TBL_INACLA,"Inactivo");

    objTblModCla=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblModCla.setHeader(vecCab);
    tblCla.setModel(objTblModCla);
    tblCla.setRowSelectionAllowed(false);
    tblCla.setCellSelectionEnabled(true);
    tblCla.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
    new Librerias.ZafColNumerada.ZafColNumerada(tblCla,INT_TBL_LINITM);

    //tblCla.getModel().addTableModelListener(new LisCambioTblAcc());
    tblCla.getTableHeader().setReorderingAllowed(false);


    tblCla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblCla.getColumnModel();

    tcmAux.getColumn(INT_TBL_LINITM).setPreferredWidth(30);
    tcmAux.getColumn(INT_TBL_CODALT).setPreferredWidth(100);
    tcmAux.getColumn(INT_TBL_BUTITM).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(400);
    tcmAux.getColumn(INT_TBL_ACTCLA).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_INACLA).setPreferredWidth(50);


    int intColVen3[]=new int[3];
    intColVen3[0]=4;
    intColVen3[1]=1;
    intColVen3[2]=3;
    int intColTbl3[]=new int[3];
    intColTbl3[0]=INT_TBL_CODITM;
    intColTbl3[1]=INT_TBL_CODALT;
    intColTbl3[2]=INT_TBL_NOMITM;
    objTblCelEdiTxtVcoPrv=new ZafTblCelEdiTxtVco(tblCla, objVenConItm, intColVen3, intColTbl3);  //********
    tcmAux.getColumn(INT_TBL_CODALT).setCellEditor(objTblCelEdiTxtVcoPrv);  //******
    objTblCelEdiTxtVcoPrv.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {   //******
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            int intNumFil = tblCla.getSelectedRow();
            tblCla.setValueAt(new Boolean(true),intNumFil, INT_TBL_ACTCLA);
            tblCla.setValueAt(new Boolean(false),intNumFil, INT_TBL_INACLA);
       }
    });


    objTblCelRenButPrv=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
    tcmAux.getColumn(INT_TBL_BUTITM).setCellRenderer(objTblCelRenButPrv);
    objTblCelRenButPrv=null;
    new ButItm(tblCla, INT_TBL_BUTITM);   //*****


    objTblRenChkClaA = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    objTblRenChkClaI = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    tcmAux.getColumn(INT_TBL_ACTCLA).setCellRenderer(objTblRenChkClaA);
    tcmAux.getColumn(INT_TBL_INACLA).setCellRenderer(objTblRenChkClaI);

    objTblEdiChkClaA = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_ACTCLA).setCellEditor(objTblEdiChkClaA);

    objTblEdiChkClaI = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_INACLA).setCellEditor(objTblEdiChkClaI);


     objTblEdiChkClaA.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            int intNumFil = tblCla.getSelectedRow();
              tblCla.setValueAt(new Boolean(true), intNumFil, INT_TBL_ACTCLA);
              tblCla.setValueAt(new Boolean(false), intNumFil, INT_TBL_INACLA);
         }
    });

    objTblEdiChkClaI.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
        }

        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            int intNumFil = tblCla.getSelectedRow();
              tblCla.setValueAt(new Boolean(false), intNumFil, INT_TBL_ACTCLA);
              tblCla.setValueAt(new Boolean(true), intNumFil, INT_TBL_INACLA);
 
        }
    });


      ArrayList arlColHid=new ArrayList();
      arlColHid.add(""+INT_TBL_CODITM);
      objTblModCla.setSystemHiddenColumns(arlColHid, tblCla);
      arlColHid=null;

    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
    vecAux.add("" + INT_TBL_CODALT);
    vecAux.add("" + INT_TBL_BUTITM);
    vecAux.add("" + INT_TBL_ACTCLA);
    vecAux.add("" + INT_TBL_INACLA);
    objTblModCla.setColumnasEditables(vecAux);
    vecAux=null;

    

    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblCla);
    tcmAux=null;
    objTblModCla.setModoOperacion(objTblModCla.INT_TBL_EDI);

    new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblCla);

  }catch(Exception e) {   objUti.mostrarMsgErr_F1(this, e); }
}

private boolean configurarVenConItm() {
    boolean blnRes=true;
    try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("tx_codalt");
        arlCam.add("tx_descor");
        arlCam.add("tx_nomitm");
        arlCam.add("co_itm");
        ArrayList arlAli=new ArrayList();
        arlAli.add("Cód.Alt.");
        arlAli.add("Unidad.");
        arlAli.add("Nom.Itm.");
        arlAli.add("Cód.Itm");
        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("100");
        arlAncCol.add("60");
        arlAncCol.add("300");
        arlAncCol.add("50");
       //Armar la sentencia SQL.
        String  strSQL="";
        String strAuxInv="";
        String strConInv="";

 
     if(objZafParSis.getCodigoUsuario()!=1){
        strAuxInv = ",CASE WHEN (" +
        " (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
        " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
        " AND co_tipdoc= 2   AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
        " ))) THEN 'S' ELSE 'N' END  as isterL";

        strConInv=" WHERE isterL='S' ";
      }

        strSQL="SELECT * FROM ( SELECT * "+strAuxInv+" FROM ( SELECT * FROM( select  a.tx_codalt, a1.tx_descor, a.tx_nomitm,  a.co_itm  from tbm_inv as a " +
        " left join tbm_var as a1 on (a1.co_reg=a.co_uni) " +
        " WHERE  a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.st_reg not in ('U','T')   ) AS x "+
          " ) AS x ) AS x  "+strConInv;
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


private class ButItm extends Librerias.ZafTableColBut.ZafTableColBut_uni{
 public ButItm(javax.swing.JTable tbl, int intIdx){
 super(tbl,intIdx, "Cotización.");
}
public void butCLick() {
   int intNumFil = tblCla.getSelectedRow();

   if(intNumFil >= 0 )
    listaProveedor(intNumFil );

}
}

private void listaProveedor(int intNumFil){
 objVenConItm.setTitle("Listado de Items");
 objVenConItm.show();
 if (objVenConItm.getSelectedButton()==objVenConItm.INT_BUT_ACE) {
    tblCla.setValueAt(objVenConItm.getValueAt(4),intNumFil ,INT_TBL_CODITM);
    tblCla.setValueAt(objVenConItm.getValueAt(1),intNumFil, INT_TBL_CODALT);
    tblCla.setValueAt(objVenConItm.getValueAt(3),intNumFil, INT_TBL_NOMITM);
    tblCla.setValueAt(new Boolean(true),intNumFil, INT_TBL_ACTCLA);
    tblCla.setValueAt(new Boolean(false),intNumFil, INT_TBL_INACLA);
 }
}


/**
* Esta función permite consultar los registros de acuerdo al criterio seleccionado.
* @return true: Si se pudo consultar los registros.
* <BR>false: En el caso contrario.
*/
private boolean cargarItmCla(){
 boolean blnRes=true;
 java.sql.Statement stm;
 java.sql.ResultSet rst;
 String strSql="";
 String strAuxInv="";
 String strConInv="";
 try{
    con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
    if (con!=null){
        stm=con.createStatement();

        vecDat=new Vector();

      if(objZafParSis.getCodigoUsuario()!=1){
        strAuxInv = ",CASE WHEN (" +
        " (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
        " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
        " AND co_tipdoc= 2   AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
        " ))) THEN 'S' ELSE 'N' END  as isterL";

        strConInv=" WHERE isterL='S' ";
     }

        strSql="SELECT * FROM ( SELECT * "+strAuxInv+" FROM ( select a.co_itm, a2.tx_codalt, a2.tx_nomitm, a.st_reg from tbr_invcla as a " +
        " INNER JOIN tbm_clainv AS a1 ON (a1.co_emp=a.co_emp and a1.co_cla=a.co_cla) " +
        " INNER JOIN tbm_inv AS a2 ON(a2.co_emp=a.co_emp and a2.co_itm=a.co_itm) " +
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" " +
        " AND a.co_grp="+objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODGRP) +" "+  
        " AND a.co_cla="+objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODCLA)+" "+
        " ) AS x ) AS x  "+strConInv;
        rst=stm.executeQuery(strSql);
        while (rst.next())
        {
            vecReg=new Vector();
            vecReg.add(INT_TBL_LINITM,"");
            vecReg.add(INT_TBL_CODITM, rst.getString("co_itm") );
            vecReg.add(INT_TBL_CODALT, rst.getString("tx_codalt") );
            vecReg.add(INT_TBL_BUTITM, ".." );
            vecReg.add(INT_TBL_NOMITM, rst.getString("tx_nomitm") );
            vecReg.add(INT_TBL_ACTCLA, new Boolean ( rst.getString("st_reg").equals("A") ) );
            vecReg.add(INT_TBL_INACLA, new Boolean ( rst.getString("st_reg").equals("I") ) );
            vecDat.add(vecReg);
       }
    rst.close();
    rst=null;
    stm.close();
    stm=null;
    con.close();
    con=null;
    //Asignar vectores al modelo.
    objTblModCla.setData(vecDat);
    tblCla.setModel(objTblModCla);
    vecDat.clear();


}}catch (java.sql.SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
  catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
return blnRes;
}





    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        lbltipgrp = new javax.swing.JLabel();
        radPub = new javax.swing.JRadioButton();
        radPri = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCodCla = new javax.swing.JTextField();
        txtCodGrp = new javax.swing.JTextField();
        txtDesGrp = new javax.swing.JTextField();
        txtDesCla = new javax.swing.JTextField();
        butCla = new javax.swing.JButton();
        butSol = new javax.swing.JButton();
        panRpt = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        chkMos = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCla = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                exitForm(evt);
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
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        lbltipgrp.setFont(new java.awt.Font("SansSerif", 0, 11));
        lbltipgrp.setText("Tipo de grupo:");
        panFil.add(lbltipgrp);
        lbltipgrp.setBounds(10, 10, 100, 20);

        buttonGroup1.add(radPub);
        radPub.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        radPub.setText("Público");
        radPub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPubActionPerformed(evt);
            }
        });
        panFil.add(radPub);
        radPub.setBounds(110, 10, 70, 20);

        buttonGroup1.add(radPri);
        radPri.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        radPri.setText("Privado");
        radPri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPriActionPerformed(evt);
            }
        });
        panFil.add(radPri);
        radPri.setBounds(180, 10, 110, 20);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel3.setText("Grupo:"); // NOI18N
        panFil.add(jLabel3);
        jLabel3.setBounds(10, 30, 90, 20);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel2.setText("Clasificación:"); // NOI18N
        panFil.add(jLabel2);
        jLabel2.setBounds(10, 50, 90, 20);

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
        panFil.add(txtCodCla);
        txtCodCla.setBounds(110, 50, 70, 20);

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
        panFil.add(txtCodGrp);
        txtCodGrp.setBounds(110, 30, 70, 20);

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
        panFil.add(txtDesGrp);
        txtDesGrp.setBounds(180, 30, 310, 20);

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
        panFil.add(txtDesCla);
        txtDesCla.setBounds(180, 50, 310, 20);

        butCla.setText(".."); // NOI18N
        butCla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butClaActionPerformed(evt);
            }
        });
        panFil.add(butCla);
        butCla.setBounds(490, 50, 20, 20);

        butSol.setText(".."); // NOI18N
        butSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSolActionPerformed(evt);
            }
        });
        panFil.add(butSol);
        butSol.setBounds(490, 30, 20, 20);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(150);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel1.setLayout(new java.awt.BorderLayout());

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

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setPreferredSize(new java.awt.Dimension(100, 25));
        jPanel3.setLayout(null);

        chkMos.setText("Mostrar Los Items de la Clasificación");
        chkMos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosActionPerformed(evt);
            }
        });
        jPanel3.add(chkMos);
        chkMos.setBounds(0, 0, 300, 23);

        jPanel2.add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanel4.setLayout(new java.awt.BorderLayout());

        tblCla.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblCla);

        jPanel4.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel2);

        panRpt.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    
    
         
       
    
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
          Configura_ventana_consulta();
         
        
    }//GEN-LAST:event_formInternalFrameOpened

    
    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        
     
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }            
        }
        else
        {
            blnCon=false;
        }
       
    }//GEN-LAST:event_butConActionPerformed

    
       private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    


    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        // TODO add your handling code here:

    String strMsg = "¿Está Seguro que desea Guardar la Imformación ?";
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {

      guardarClaitm();
    
    }


    }//GEN-LAST:event_butGuaActionPerformed

    private void chkMosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosActionPerformed
        // TODO add your handling code here:

         objTblModCla.setModoOperacion(objTblModCla.INT_TBL_EDI);
         objTblModCla.removeAllRows();

         if( tblDat.getSelectedRow() == -1 ){
            MensajeInf("Seleccione algun resgistro de clasificacion. ");
          }else{
           if(chkMos.isSelected()){
               objTblModCla.setModoOperacion(objTblModCla.INT_TBL_INS);
               cargarItmCla();
          }}

    }//GEN-LAST:event_chkMosActionPerformed

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

            }
        }
}//GEN-LAST:event_butClaActionPerformed

    private void butSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSolActionPerformed
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
        }
}//GEN-LAST:event_butSolActionPerformed





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
  objVenConGrp.cargarDatos();


  if(objVenConGrp.buscar(campo, strBusqueda )) {
      txtCodGrp.setText(objVenConGrp.getValueAt(1));
      txtDesGrp.setText(objVenConGrp.getValueAt(3));
       txtCodCla.setText("");
       txtDesCla.setText("");
  }else{
        objVenConGrp.setCampoBusqueda(tipo);
        objVenConGrp.cargarDatos();
        objVenConGrp.show();
        if (objVenConGrp.getSelectedButton()==objVenConGrp.INT_BUT_ACE) {
            txtCodGrp.setText(objVenConGrp.getValueAt(1));
            txtDesGrp.setText(objVenConGrp.getValueAt(3));
             txtCodCla.setText("");
       txtDesCla.setText("");
        }else{
            txtCodGrp.setText(strCodGrp);
            txtDesGrp.setText(strDesGrp);
             txtCodCla.setText("");
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
  objVenConCla.cargarDatos();


  if(objVenConCla.buscar(campo, strBusqueda )) {
       txtCodCla.setText(objVenConCla.getValueAt(3));
       txtDesCla.setText(objVenConCla.getValueAt(4));

  }else{
        objVenConCla.setCampoBusqueda(tipo);
        objVenConCla.cargarDatos();
        objVenConCla.show();
        if (objVenConCla.getSelectedButton()==objVenConCla.INT_BUT_ACE) {
             txtCodCla.setText(objVenConCla.getValueAt(3));
             txtDesCla.setText(objVenConCla.getValueAt(4));

        }else{
            txtCodCla.setText(strCodCla);
            txtDesCla.setText(strDesCla);
  }}}else{
       txtCodCla.setText("");
       txtDesCla.setText("");
  }}










 private boolean guardarClaitm(){
  boolean blnRes=true;
  java.sql.Connection conn;
  try{
    conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
    if(conn!=null){
     conn.setAutoCommit(false);
  
     if(guardarClaInv(conn, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODCLA).toString(), objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODGRP).toString()  )){
         conn.commit();
         MensajeInf("Los datos se Guardar con exito.. ");
     }else conn.rollback();

     conn.close();
     conn=null;
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
  return  blnRes;
}


public boolean guardarClaInv(java.sql.Connection conMod, String strCodCla, String strCodGrp ){
 boolean blnRes=true;
 java.sql.ResultSet rstLoc;
 java.sql.Statement stmLoc;
 String strEst="A";
 String strSqlInv="";
 String strSql="";
 int intStrBuf=0;
 StringBuffer stbSqlCla;
 try{
 if(conMod!=null){
  stmLoc=conMod.createStatement();

   stbSqlCla=new StringBuffer();

 if(tblCla.getRowCount()>0){
   strSql="DELETE FROM tbr_invcla WHERE co_cla="+strCodCla+" and co_grp="+strCodGrp;
   stmLoc.executeUpdate( strSql );
 }
      
 for(int x=0; x<tblCla.getRowCount(); x++){
  if(tblCla.getValueAt(x, INT_TBL_CODITM) != null){
   if(!tblCla.getValueAt(x, INT_TBL_CODITM).toString().equals("")){

    strEst="A";
    if(tblCla.getValueAt( x, INT_TBL_ACTCLA)!=null){
     if(tblCla.getValueAt( x, INT_TBL_ACTCLA).toString().equalsIgnoreCase("true"))
           strEst="A";
    }
    if(tblCla.getValueAt( x, INT_TBL_INACLA)!=null){
        if(tblCla.getValueAt( x, INT_TBL_INACLA).toString().equalsIgnoreCase("true"))
           strEst="I";
    }

    strSqlInv="SELECT co_emp , co_itm FROM tbm_equInv  WHERE "+
    " co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" "+
    " AND co_itm="+tblCla.getValueAt(x, INT_TBL_CODITM)+")  ";
    rstLoc = stmLoc.executeQuery(strSqlInv);
    while(rstLoc.next()){

      strSql=" INSERT INTO tbr_invcla(co_emp, co_itm, co_grp, co_cla ,st_reg, st_regrep, fe_ing ) VALUES" +
      " ("+rstLoc.getInt("co_emp")+","+ rstLoc.getInt("co_itm") +", "+strCodGrp+", "+strCodCla+" "+
      " ,'"+strEst+"', 'I' ,"+objZafParSis.getFuncionFechaHoraBaseDatos()+"  ) ; ";
      stbSqlCla.append( strSql+ " ; ");
      intStrBuf=1;

    }
    rstLoc.close();
    rstLoc=null;


  }}}

 if( intStrBuf==1 )
   stmLoc.executeUpdate(stbSqlCla.toString());

 stbSqlCla=null;
 stmLoc.close();
 stmLoc=null;
}}catch (java.sql.SQLException evt){ blnRes=false;   MensajeInf("Error. Al Modificar Clasificaciónes posible duplicidad de datos.\nVerifique los datos he intente nuevamente."); }
  catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, evt);  }
 return blnRes;
}





      
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCla;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butSol;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkMos;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lbltipgrp;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JRadioButton radPri;
    private javax.swing.JRadioButton radPub;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblCla;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCla;
    private javax.swing.JTextField txtCodGrp;
    private javax.swing.JTextField txtDesCla;
    private javax.swing.JTextField txtDesGrp;
    // End of variables declaration//GEN-END:variables
   
    

    

     public void setEditable(boolean editable) {
        if (editable==true){
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }else{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }


    
      public void Configura_ventana_consulta(){
        configurarVenConGrp();
        configurarVenConClasificacion();
        configurarVenConItm();
        configurarTblRep();
        ConfigurarTblClasificacion();

    }
     




private String sqlConFil(){
   String sqlFil="";
 
    
        if(!txtCodGrp.getText().equals("")){
            sqlFil+=" AND a.co_grp="+txtCodGrp.getText();

            if(!txtCodCla.getText().equals(""))
            sqlFil+=" AND a.co_cla="+txtCodCla.getText();
        }
    
  
      return sqlFil;
    }



/**
* Esta función permite consultar los registros de acuerdo al criterio seleccionado.
* @return true: Si se pudo consultar los registros.
* <BR>false: En el caso contrario.
*/
private boolean cargarDetReg(String strFil){
 boolean blnRes=true;
 java.sql.Statement stm;
 java.sql.ResultSet rst;
 int intNumTotReg=0, i=0;
 String strSql="";
 try{
    butCon.setText("Detener");
    lblMsgSis.setText("Obteniendo datos...");
    con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
    if (con!=null){
        stm=con.createStatement();
                //Obtener la condición.


        if(radPub.isSelected()){

         strSql="SELECT a.co_grp, a1.tx_deslar as nomgrp, a.co_cla, a.tx_deslar as nomcla FROM tbm_clainv as a " +
         " inner JOIN tbm_grpclainv as a1 on (a1.co_emp=a.co_emp and a1.co_grp=a.co_grp) " +
         " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and  a.st_reg not in ('E') and a1.tx_tipgrp='P'   "+strFil;

        }
        if(radPri.isSelected()){

         strSql="SELECT a.co_grp, a1.tx_deslar as nomgrp, a.co_cla, a.tx_deslar as nomcla FROM tbm_clainv as a " +
         " inner JOIN tbm_grpclainv as a1 on (a1.co_emp=a.co_emp and a1.co_grp=a.co_grp) " +
         " inner JOIN tbr_grpclainvusr as a2 on (a2.co_emp=a1.co_emp and a2.co_grp=a1.co_grp) "+
         " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and  a.st_reg not in ('E') and a2.co_usr="+objZafParSis.getCodigoUsuario()+"   "+strFil;

        }


        strSQL="SELECT COUNT(*) FROM ( "+strSql+" ) AS X ";
        intNumTotReg=objUti.getNumeroRegistro(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQL);
        if (intNumTotReg==-1)
           return false;

        strSQL="SELECT * FROM ( "+strSql+" ) AS X  ORDER BY co_grp ";

         rst=stm.executeQuery(strSQL);
        //vecDat.clear();
        vecDat=new Vector();
        lblMsgSis.setText("Cargando datos...");
        pgrSis.setMinimum(0);
        pgrSis.setMaximum(intNumTotReg);
        pgrSis.setValue(0);
        i=0;
        while (rst.next())
        {
         if (blnCon)
          {
            vecReg=new Vector();
            vecReg.add(INT_TBL_LIN,"");
            vecReg.add(INT_TBL_CODGRP, rst.getString("co_grp") );
            vecReg.add(INT_TBL_TXTGRP, rst.getString("nomgrp") );
            vecReg.add(INT_TBL_CODCLA, rst.getString("co_cla") );
            vecReg.add(INT_TBL_TXTCLA, rst.getString("nomcla") );
            vecDat.add(vecReg);
            i++;
            pgrSis.setValue(i);
        }
        else
        {
            break;
        }
    }
    rst.close();
    rst=null;
    stm.close();
    stm=null;
    con.close();
    con=null;
    //Asignar vectores al modelo.
    objTblMod.setData(vecDat);
    tblDat.setModel(objTblMod);
    vecDat.clear();


    if (intNumTotReg==tblDat.getRowCount())
        lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
    else
        lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
    pgrSis.setValue(0);
    butCon.setText("Consultar");
}
}
catch (java.sql.SQLException e)
{
blnRes=false;
objUti.mostrarMsgErr_F1(this, e);
}
catch (Exception e)
{
blnRes=false;
objUti.mostrarMsgErr_F1(this, e);
}
return blnRes;
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
        public void run()
        {
    

             if (!cargarDetReg( sqlConFil()))
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
         
            
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_LIN:
                    strMsg="";
                    break;
               
                    
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}