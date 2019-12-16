/*  
 *  Created on 07 de septiembre de 2009, 10:10 PM
 */  
package Maestros.ZafMae57;
     
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;  //*******************
import Librerias.ZafVenCon.ZafVenCon; 
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafPerUsr.ZafPerUsr;
/**
 *
 * @author  Javier Ayapata
 */
public class ZafMae57 extends javax.swing.JInternalFrame{

    Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblModCla;
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut  objTblCelRenButPrv;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoPrv;
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk  objTblCelRenChkP,objTblCelRenChkA, objTblCelRenChkI;        //Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk  objTblCelEdiChkP,objTblCelEdiChkA, objTblCelEdiChkI;        //Editor: JButton en celda.
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario.


    //Constantes: Columnas del JTable:
    final int INT_TBL_LIN=0;                        //Línea
    final int INT_TBL_CODITM =1;                    //Código del item (Sistema).
    final int INT_TBL_CODALT =2;                    //Código del item (Sistema).
    final int INT_TBL_NOMITM =3;                    //Código del item (Alterno).


    //Constantes: Columnas del JTable:
    final int INT_TBL_LINITM=0;                        //Línea
    final int INT_TBL_CODPRV =1;                    //Código del item (Sistema).
    final int INT_TBL_BUTPRV =2;                    //Código del item (Alterno).
    final int INT_TBL_NOMPRV =3;                    //Código del item (Alterno).
    final int INT_TBL_PRVACT=4;
    final int INT_TBL_PRVINA=5;
    final int INT_TBL_PRVPRE=6;
    final int INT_TBL_CODINS=7;

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

    ZafVenCon objVenConPrv;
    ZafVenCon objVenConItm;
   
    String strCodItm="",strDesItm="";
    
    String strCodBod="", strNomBod="";
    String strCodCli="";
    String strDesCli="";
    String strCodVen="";
    String strDesVen="";
    String strVersion=" v 0.3 ";
 

/** Crea una nueva instancia de la clase ZafIndRpt. */
public ZafMae57(ZafParSis obj)
{
 try{ /**/
    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
    objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        
    initComponents();

    objUti=new ZafUtil();
    strAux=objZafParSis.getNombreMenu();
    this.setTitle(strAux+" "+ strVersion );
    lblTit.setText(strAux);

    objPerUsr=new ZafPerUsr(this.objZafParSis);


   if(!(objZafParSis.getCodigoUsuario()==1)){
    if(!objPerUsr.isOpcionEnabled(2309) )
        butCon.setVisible(false);

    if(!objPerUsr.isOpcionEnabled(2310) )
        butGua.setVisible(false);

    if(!objPerUsr.isOpcionEnabled(2311) )
        butCer.setVisible(false);
    }

  }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
}


private boolean configurarVenConProveedor() {
    boolean blnRes=true;
    try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("a1.co_cli");
        arlCam.add("a1.tx_nom");
        arlCam.add("a1.tx_dir");
        arlCam.add("a1.tx_ide");
        ArrayList arlAli=new ArrayList();
        arlAli.add("Código");
        arlAli.add("Nom.Prv.");
        arlAli.add("Dirección");
        arlAli.add("RUC/CI");
        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("50");
        arlAncCol.add("190");
        arlAncCol.add("220");
        arlAncCol.add("80");
        //Armar la sentencia SQL.
        String  strSQL="";
        strSQL+="select a1.co_cli,a1.tx_nom,a1.tx_dir,a1.tx_ide  FROM " +
        " tbr_cliloc AS a " +
        " INNER JOIN tbm_cli as a1 ON (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli)   " +
        " where a1.st_reg in('A','N')  AND   a1.st_prv = 'S' and a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" order by a1.tx_nom";

        objVenConPrv=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
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
    vecCab.add(INT_TBL_CODITM,"Cod.Itm.");
    vecCab.add(INT_TBL_CODALT,"Cod.Alt");
    vecCab.add(INT_TBL_NOMITM,"Nom.Itm");
   
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
    tcmAux.getColumn(INT_TBL_CODITM).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_CODALT).setPreferredWidth(125);
    tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(420);
   
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
    vecCab.add(INT_TBL_CODPRV,"Cod.Prv");
    vecCab.add(INT_TBL_BUTPRV,"..");
    vecCab.add(INT_TBL_NOMPRV,"Nom.Prv.");
    vecCab.add(INT_TBL_PRVACT,"Activo.");
    vecCab.add(INT_TBL_PRVINA,"Inactivo");
    vecCab.add(INT_TBL_PRVPRE,"Predeterminado");
    vecCab.add(INT_TBL_CODINS,"CodIns");

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
    tcmAux.getColumn(INT_TBL_CODPRV).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_BUTPRV).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_NOMPRV).setPreferredWidth(380);
    tcmAux.getColumn(INT_TBL_PRVACT).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_PRVINA).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_PRVPRE).setPreferredWidth(50);


    int intColVen3[]=new int[2];
    intColVen3[0]=1;
    intColVen3[1]=2;
    int intColTbl3[]=new int[2];
    intColTbl3[0]=INT_TBL_CODPRV;
    intColTbl3[1]=INT_TBL_NOMPRV;
    objTblCelEdiTxtVcoPrv=new ZafTblCelEdiTxtVco(tblCla, objVenConPrv, intColVen3, intColTbl3);  //********
    tcmAux.getColumn(INT_TBL_CODPRV).setCellEditor(objTblCelEdiTxtVcoPrv);  //******
    objTblCelEdiTxtVcoPrv.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {   //******
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
          int intNumFil = tblCla.getSelectedRow();
          if(intNumFil >= 0 ) {

           if(!(objZafParSis.getCodigoUsuario()==1)){
            String strCodPrv = ((tblCla.getValueAt(intNumFil, INT_TBL_CODINS)==null)?"":tblCla.getValueAt(intNumFil, INT_TBL_CODINS).toString());
            if(!(strCodPrv.trim().equals(""))) {
                objTblCelEdiTxtVcoPrv.setCancelarEdicion(true);
           }else{
              if(!objPerUsr.isOpcionEnabled(2324) )
                objTblCelEdiTxtVcoPrv.setCancelarEdicion(true);
           }


           }
          }
        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            int intNumFil = tblCla.getSelectedRow();
            int intEstChk=0;

          if(intNumFil >= 0 ) {
           if(!(objZafParSis.getCodigoUsuario()==1)){
             String strCodPrv = ((tblCla.getValueAt(intNumFil, INT_TBL_CODINS)==null)?"":tblCla.getValueAt(intNumFil, INT_TBL_CODINS).toString());
             if(!(strCodPrv.trim().equals(""))) {
               objTblCelEdiTxtVcoPrv.setCancelarEdicion(true);
             }else{
              if(!objPerUsr.isOpcionEnabled(2324) )
                objTblCelEdiTxtVcoPrv.setCancelarEdicion(true);
              else intEstChk=1;
             }
           }else intEstChk=1;

            if(intEstChk==1){


               String strCodPrv1 = ((tblCla.getValueAt(intNumFil, INT_TBL_CODPRV)==null)?"":tblCla.getValueAt(intNumFil, INT_TBL_CODPRV).toString());
               String strCodPrv2 = ((tblCla.getValueAt(intNumFil, INT_TBL_CODINS)==null)?"":tblCla.getValueAt(intNumFil, INT_TBL_CODINS).toString());
               if(!strCodPrv1.equals(strCodPrv2)){
                  tblCla.setValueAt("",intNumFil, INT_TBL_CODINS);
                  tblCla.setValueAt(new Boolean(true),intNumFil, INT_TBL_PRVACT);
                  tblCla.setValueAt(new Boolean(false),intNumFil, INT_TBL_PRVINA);
                  tblCla.setValueAt(new Boolean(false),intNumFil, INT_TBL_PRVPRE);
               }

            }


          }}
    });




    objTblCelRenButPrv=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
    tcmAux.getColumn(INT_TBL_BUTPRV).setCellRenderer(objTblCelRenButPrv);
    objTblCelRenButPrv=null;
    new ButItm(tblCla, INT_TBL_BUTPRV);   //*****


    objTblCelRenChkP = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    objTblCelRenChkA = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    objTblCelRenChkI = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();

    tcmAux.getColumn(INT_TBL_PRVPRE).setCellRenderer(objTblCelRenChkP);
    tcmAux.getColumn(INT_TBL_PRVACT).setCellRenderer(objTblCelRenChkA);
    tcmAux.getColumn(INT_TBL_PRVINA).setCellRenderer(objTblCelRenChkI);

    objTblCelEdiChkP = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_PRVPRE).setCellEditor(objTblCelEdiChkP);

    objTblCelEdiChkA = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_PRVACT).setCellEditor(objTblCelEdiChkA);

    objTblCelEdiChkI = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_PRVINA).setCellEditor(objTblCelEdiChkI);


    objTblCelEdiChkP.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
          int intNumFil = tblCla.getSelectedRow();
          if(intNumFil >= 0 ) {
           if(!(objZafParSis.getCodigoUsuario()==1)){
            String strCodPrv = ((tblCla.getValueAt(intNumFil, INT_TBL_CODINS)==null)?"":tblCla.getValueAt(intNumFil, INT_TBL_CODINS).toString());
            if(!(strCodPrv.trim().equals(""))) {
              if(!objPerUsr.isOpcionEnabled(2326) )
                objTblCelEdiChkP.setCancelarEdicion(true);

            }else {
              if(!objPerUsr.isOpcionEnabled(2324) )
                objTblCelEdiChkP.setCancelarEdicion(true);
              }

           }
          }

        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
          int intNumFil = tblCla.getSelectedRow();
          int intEdiChk=0;
          if(intNumFil >= 0 ) {
          if(!(objZafParSis.getCodigoUsuario()==1)){
            String strCodPrv = ((tblCla.getValueAt(intNumFil, INT_TBL_CODINS)==null)?"":tblCla.getValueAt(intNumFil, INT_TBL_CODINS).toString());
            if(!(strCodPrv.trim().equals(""))) {
              if(!objPerUsr.isOpcionEnabled(2326) )
               objTblCelEdiChkP.setCancelarEdicion(true);
              else intEdiChk=1;
           }else{
              if(!objPerUsr.isOpcionEnabled(2324) )
                objTblCelEdiChkP.setCancelarEdicion(true);
              else intEdiChk=1;
           }
          }else intEdiChk=1;

           if(intEdiChk==1){
            for(int x=0; x<tblCla.getRowCount(); x++){
                tblCla.setValueAt(new Boolean(false), x, INT_TBL_PRVPRE);
            }
            tblCla.setValueAt(new Boolean(true), intNumFil, INT_TBL_PRVPRE);
            tblCla.setValueAt(new Boolean(false), intNumFil, INT_TBL_PRVACT);
            tblCla.setValueAt(new Boolean(false), intNumFil, INT_TBL_PRVINA);
           }

      }
     }});


    objTblCelEdiChkA.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
          int intNumFil = tblCla.getSelectedRow();
          if(intNumFil >= 0 ) {
           if(!(objZafParSis.getCodigoUsuario()==1)){
            String strCodPrv = ((tblCla.getValueAt(intNumFil, INT_TBL_CODINS)==null)?"":tblCla.getValueAt(intNumFil, INT_TBL_CODINS).toString());
            if(!(strCodPrv.trim().equals(""))) {
              if(!objPerUsr.isOpcionEnabled(2326) )
                objTblCelEdiChkA.setCancelarEdicion(true);
            }else{
              if(!objPerUsr.isOpcionEnabled(2324) )
                objTblCelEdiChkA.setCancelarEdicion(true);
            }
           }
          }
        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
          int intNumFil = tblCla.getSelectedRow();
          int intEdiChk=0;
          if(intNumFil >= 0 ) {
          if(!(objZafParSis.getCodigoUsuario()==1)){
            String strCodPrv = ((tblCla.getValueAt(intNumFil, INT_TBL_CODINS)==null)?"":tblCla.getValueAt(intNumFil, INT_TBL_CODINS).toString());
            if(!(strCodPrv.trim().equals(""))) {
              if(!objPerUsr.isOpcionEnabled(2326) )
               objTblCelEdiChkA.setCancelarEdicion(true);
              else intEdiChk=1;
           }else{
               if(!objPerUsr.isOpcionEnabled(2324) )
                objTblCelEdiChkA.setCancelarEdicion(true);
               else intEdiChk=1;
           }
          }else intEdiChk=1;

           if(intEdiChk==1){
            tblCla.setValueAt(new Boolean(false), intNumFil, INT_TBL_PRVPRE);
            tblCla.setValueAt(new Boolean(true), intNumFil, INT_TBL_PRVACT);
            tblCla.setValueAt(new Boolean(false), intNumFil, INT_TBL_PRVINA);
           }
        }

      }});


    objTblCelEdiChkI.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
          int intNumFil = tblCla.getSelectedRow();
          if(intNumFil >= 0 ) {
           if(!(objZafParSis.getCodigoUsuario()==1)){
            String strCodPrv = ((tblCla.getValueAt(intNumFil, INT_TBL_CODINS)==null)?"":tblCla.getValueAt(intNumFil, INT_TBL_CODINS).toString());
            if(!(strCodPrv.trim().equals(""))) {
              if(!objPerUsr.isOpcionEnabled(2326) )
                objTblCelEdiChkI.setCancelarEdicion(true);
           }else{
              if(!objPerUsr.isOpcionEnabled(2324) )
                objTblCelEdiChkI.setCancelarEdicion(true);
           }
           }
          }
        }

        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
          int intNumFil = tblCla.getSelectedRow();
          int intEdiChk=0;
          if(intNumFil >= 0 ) {
          if(!(objZafParSis.getCodigoUsuario()==1)){
            String strCodPrv = ((tblCla.getValueAt(intNumFil, INT_TBL_CODINS)==null)?"":tblCla.getValueAt(intNumFil, INT_TBL_CODINS).toString());
            if(!(strCodPrv.trim().equals(""))) {
              if(!objPerUsr.isOpcionEnabled(2326) )
               objTblCelEdiChkI.setCancelarEdicion(true);
              else intEdiChk=1;
           }else{
               if(!objPerUsr.isOpcionEnabled(2324) )
                objTblCelEdiChkI.setCancelarEdicion(true);
               else intEdiChk=1;
           }
          }else intEdiChk=1;

           if(intEdiChk==1){
              tblCla.setValueAt(new Boolean(false), intNumFil, INT_TBL_PRVPRE);
              tblCla.setValueAt(new Boolean(false), intNumFil, INT_TBL_PRVACT);
              tblCla.setValueAt(new Boolean(true), intNumFil, INT_TBL_PRVINA);
           }
        }


    }});




    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
    vecAux.add("" + INT_TBL_CODPRV);
    vecAux.add("" + INT_TBL_BUTPRV);
    vecAux.add("" + INT_TBL_PRVACT);
    vecAux.add("" + INT_TBL_PRVINA);
    vecAux.add("" + INT_TBL_PRVPRE);
    objTblModCla.setColumnasEditables(vecAux);
    vecAux=null;



      ArrayList arlColHid=new ArrayList();
      arlColHid.add(""+INT_TBL_CODINS);
      objTblModCla.setSystemHiddenColumns(arlColHid, tblCla);
      arlColHid=null;
      

    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblCla);
    tcmAux=null;
    objTblModCla.setModoOperacion(objTblModCla.INT_TBL_EDI);

    Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu ZafTblPopMn = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblCla);
    if(!(objZafParSis.getCodigoUsuario()==1)){
        if(!objPerUsr.isOpcionEnabled(2325)){
            ZafTblPopMn.setEliminarFilaVisible(false);
            
    }}

  }catch(Exception e) {   objUti.mostrarMsgErr_F1(this, e); }
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

private class ButItm extends Librerias.ZafTableColBut.ZafTableColBut_uni{
 public ButItm(javax.swing.JTable tbl, int intIdx){
 super(tbl,intIdx, "Cotización.");
}
public void butCLick() {
   int intNumFil = tblCla.getSelectedRow();

    if(intNumFil >= 0 ){
     if(objZafParSis.getCodigoUsuario()==1){
       listaProveedor(intNumFil );
     }else{
      String strCodPrv = ((tblCla.getValueAt(intNumFil, INT_TBL_CODINS)==null)?"":tblCla.getValueAt(intNumFil, INT_TBL_CODINS).toString());
      if((strCodPrv.trim().equals(""))){
         if(objPerUsr.isOpcionEnabled(2324) )
          listaProveedor(intNumFil );
      }
      
     }

   }

}
}

private void listaProveedor(int intNumFil){
 objVenConPrv.setTitle("Listado de Proveedores");
 objVenConPrv.show();
 if (objVenConPrv.getSelectedButton()==objVenConPrv.INT_BUT_ACE) {
    tblCla.setValueAt(objVenConPrv.getValueAt(1),intNumFil ,INT_TBL_CODPRV);
    tblCla.setValueAt(objVenConPrv.getValueAt(2),intNumFil, INT_TBL_NOMPRV);
       String strCodPrv1 = ((tblCla.getValueAt(intNumFil, INT_TBL_CODPRV)==null)?"":tblCla.getValueAt(intNumFil, INT_TBL_CODPRV).toString());
       String strCodPrv2 = ((tblCla.getValueAt(intNumFil, INT_TBL_CODINS)==null)?"":tblCla.getValueAt(intNumFil, INT_TBL_CODINS).toString());
       if(!strCodPrv1.equals(strCodPrv2)){
          tblCla.setValueAt("",intNumFil, INT_TBL_CODINS);
          tblCla.setValueAt(new Boolean(true),intNumFil, INT_TBL_PRVACT);
          tblCla.setValueAt(new Boolean(false),intNumFil, INT_TBL_PRVINA);
          tblCla.setValueAt(new Boolean(false),intNumFil, INT_TBL_PRVPRE);
       }
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
 try{
    con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
    if (con!=null){
        stm=con.createStatement();

        vecDat=new Vector();

        strSql="SELECT a.co_prv, a2.tx_nom,  a.st_reg  from tbr_prvinv as a " +
        " INNER JOIN tbm_cli AS a2 ON(a2.co_emp=a.co_emp and a2.co_cli=a.co_prv) " +
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" " +
        " AND a.co_itm="+objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODITM)+" ";
        rst=stm.executeQuery(strSql);
        while (rst.next())
        {
            vecReg=new Vector();
            vecReg.add(INT_TBL_LINITM,"");
            vecReg.add(INT_TBL_CODPRV, rst.getString("co_prv") );
            vecReg.add(INT_TBL_BUTPRV, ".." );
            vecReg.add(INT_TBL_NOMPRV, rst.getString("tx_nom") );
            vecReg.add(INT_TBL_PRVACT, new Boolean ( rst.getString("st_reg").equals("A") ) );
            vecReg.add(INT_TBL_PRVINA, new Boolean ( rst.getString("st_reg").equals("I") ) );
            vecReg.add(INT_TBL_PRVPRE, new Boolean ( rst.getString("st_reg").equals("P") ) );
            vecReg.add(INT_TBL_CODINS, rst.getString("co_prv") );
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
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblCli = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtDesItm = new javax.swing.JTextField();
        butSol = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtcodaltdes = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtcodalthas = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtTer = new javax.swing.JTextField();
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

        bgrFil.add(optTod);
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
        panFil.add(optTod);
        optTod.setBounds(0, 10, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los Items que cumplan el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(0, 30, 400, 20);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli.setText("Item:");
        panFil.add(lblCli);
        lblCli.setBounds(30, 50, 100, 20);

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
        panFil.add(txtCodItm);
        txtCodItm.setBounds(130, 50, 50, 20);

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
        panFil.add(txtDesItm);
        txtDesItm.setBounds(180, 50, 280, 20);

        butSol.setText(".."); // NOI18N
        butSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSolActionPerformed(evt);
            }
        });
        panFil.add(butSol);
        butSol.setBounds(460, 50, 20, 20);

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

        panFil.add(jPanel5);
        jPanel5.setBounds(20, 70, 300, 60);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        jPanel7.setLayout(null);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel6.setText("Termina con:");
        jPanel7.add(jLabel6);
        jLabel6.setBounds(16, 28, 80, 16);

        txtTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTerFocusLost(evt);
            }
        });
        jPanel7.add(txtTer);
        txtTer.setBounds(104, 24, 90, 20);

        panFil.add(jPanel7);
        jPanel7.setBounds(320, 70, 220, 60);

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

        chkMos.setText("Mostrar Los Proveedores ");
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

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:


        txtCodItm.setText("");
        txtDesItm.setText("");
        strCodItm="";
        strDesItm="";

        txtcodaltdes.setText("");
        txtcodalthas.setText("");
        txtTer.setText("");

   
    }//GEN-LAST:event_optTodActionPerformed

    
    
         
       
    
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
          Configura_ventana_consulta();
         
        
    }//GEN-LAST:event_formInternalFrameOpened

    
    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
           

        txtCodItm.setText("");
        txtDesItm.setText("");
        strCodItm="";
        strDesItm="";

        txtcodaltdes.setText("");
        txtcodalthas.setText("");
        txtTer.setText("");

           

        }
    }//GEN-LAST:event_optTodItemStateChanged

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

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:

 

    }//GEN-LAST:event_optFilItemStateChanged

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        // TODO add your handling code here:

    String strMsg = "¿Está Seguro que desea Guardar la Imformación ?";
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {

     if( guardarClaitm() )
      cargarItmCla();
    
    }


    }//GEN-LAST:event_butGuaActionPerformed

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

    private void txtTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTerFocusLost
        // TODO add your handling code here:
         if ( txtTer.getText().length()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtTerFocusLost









 private boolean guardarClaitm(){
  boolean blnRes=false;
  java.sql.Connection conn;
  try{
    conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
    if(conn!=null){
     conn.setAutoCommit(false);
  
     if(guardarClaPrv(conn, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODITM).toString()  )){
         conn.commit();
         MensajeInf("Los datos se Guardar con exito.. ");
         blnRes=true;
     }else conn.rollback();

     conn.close();
     conn=null;
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
  return  blnRes;
}

public boolean guardarClaPrv(java.sql.Connection conn, String strCodItm ){
   boolean blnRes=false;
   java.sql.Statement stmLoc;
   String strSql="";
   String strEst="";
   try{
      stmLoc=conn.createStatement();

      strSql="DELETE FROM tbr_prvinv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+strCodItm;
      stmLoc.executeUpdate( strSql );
      strSql="";

       for(int i=0; i<tblCla.getRowCount(); i++){
        if(tblCla.getValueAt(i, INT_TBL_CODPRV) != null){
         if(!tblCla.getValueAt(i, INT_TBL_CODPRV).toString().equals("")){

               strEst="A";
                if(tblCla.getValueAt( i, INT_TBL_PRVPRE)!=null){
                  if(tblCla.getValueAt( i, INT_TBL_PRVPRE).toString().equalsIgnoreCase("true"))
                         strEst="P";
                }
                if(tblCla.getValueAt( i, INT_TBL_PRVACT)!=null){
                   if(tblCla.getValueAt( i, INT_TBL_PRVACT).toString().equalsIgnoreCase("true"))
                        strEst="A";
                }
                if(tblCla.getValueAt( i, INT_TBL_PRVINA)!=null){
                  if(tblCla.getValueAt( i, INT_TBL_PRVINA).toString().equalsIgnoreCase("true"))
                         strEst="I";
                }

               strSql+=" INSERT INTO tbr_prvinv(co_emp, co_itm, co_prv ,st_reg, st_regrep, fe_ing ) VALUES" +
               " ("+objZafParSis.getCodigoEmpresa()+","+ strCodItm +", "+tblCla.getValueAt(i, INT_TBL_CODPRV).toString()+" "+
               " ,'"+strEst+"', 'I', "+objZafParSis.getFuncionFechaHoraBaseDatos()+" ) ; ";

       }}}

      if(!strSql.equals(""))
          stmLoc.executeUpdate(strSql);

      stmLoc.close();
      stmLoc=null;
      blnRes=true;

   }catch(java.sql.SQLException Evt){ blnRes=false; MensajeInf("Error. Al Guardar Proveedores posible duplicidad de datos.\nVerifique los datos he intente nuevamente.");  }
    catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    return blnRes;
}





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
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butSol;
    private javax.swing.JCheckBox chkMos;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblCla;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtDesItm;
    private javax.swing.JTextField txtTer;
    private javax.swing.JTextField txtcodaltdes;
    private javax.swing.JTextField txtcodalthas;
    // End of variables declaration//GEN-END:variables
   
    

    

     public void setEditable(boolean editable) {
        if (editable==true){
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }else{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }


    
      public void Configura_ventana_consulta(){
        configurarVenConProveedor();
        configurarVenConItm();
        configurarTblRep();
        ConfigurarTblClasificacion();

    }
     




private String sqlConFil(){
   String sqlFil="";
 
    if(optFil.isSelected()){

        if (txtCodItm.getText().length()>0)
            sqlFil+=" AND co_itm=" + txtCodItm.getText();
        if (txtcodaltdes.getText().length()>0 || txtcodalthas.getText().length()>0)
            sqlFil+=" AND ((LOWER(tx_codAlt) BETWEEN '" + txtcodaltdes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtcodalthas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(tx_codAlt) LIKE '" + txtcodalthas.getText().replaceAll("'", "''").toLowerCase() + "%')";

        
       if(!txtTer.getText().equals("")){
            String[] result = txtTer.getText().split(",");
            sqlFil+=" AND  ( ";
            int intVal=0;
             for (int x=0; x<result.length; x++) {
                 if(intVal==1)  strAux+=" OR ";
                 sqlFil+="  upper(tx_codalt) like '%"+result[x].toString().toUpperCase()+"'";
                 intVal=1;
             }
          sqlFil+=" ) ";
       }


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
 String strAuxInv="";
 String strConInv="";
 try{
    butCon.setText("Detener");
    lblMsgSis.setText("Obteniendo datos...");
    con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
    if (con!=null){
        stm=con.createStatement();
                //Obtener la condición.
       
        
     if(objZafParSis.getCodigoUsuario()!=1){
        strAuxInv = ",CASE WHEN (" +
        " (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
        " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
        " AND co_tipdoc= 2   AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
        " ))) THEN 'S' ELSE 'N' END  as isterL";

        strConInv=" WHERE isterL='S' ";
      }

        strSql="SELECT * FROM ( SELECT * "+strAuxInv+" FROM ( SELECT co_itm, tx_codalt, tx_nomitm  FROM tbm_inv " +
        " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and  st_reg not in ('U','T')  "+strFil+" "+
        " ) AS x ) AS x  "+strConInv;

        strSQL="SELECT COUNT(*) FROM ( "+strSql+" ) AS X ";

        intNumTotReg=objUti.getNumeroRegistro(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQL);
        if (intNumTotReg==-1)
           return false;

        strSQL="SELECT * FROM ( "+strSql+" ) AS X  ORDER BY tx_codalt ";

        System.out.println("-->"+ strSQL );

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
            vecReg.add(INT_TBL_CODITM, rst.getString("co_itm") );
            vecReg.add(INT_TBL_CODALT, rst.getString("tx_codalt") );
            vecReg.add(INT_TBL_NOMITM, rst.getString("tx_nomitm") );
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