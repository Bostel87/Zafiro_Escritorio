/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCom58_01.java
 *
 * Created on Sep 21, 2009, 4:16:06 PM
 */

package Compras.ZafCom58;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import java.sql.DriverManager;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import java.util.ArrayList;

/**
 *
 * @author jayapata
 */
public class ZafCom58_01 extends javax.swing.JDialog {
   ZafParSis objZafParSis;
   ZafUtil objUti;
   private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
   private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
   private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk, objTblCelEdiChk2, objTblCelEdiChk3;
   private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
   private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt, objTblCelEdiTxt2, objTblCelEdiTxt3;
   private ZafMouMotAda objMouMotAda;

    final int INT_TBL_LIN = 0;
    final int INT_TBL_CHK = 1;
    final int INT_TBL_PRV = 2;
    final int INT_TBL_NPR = 3;
    final int INT_TBL_PUNCOT = 4;
    final int INT_TBL_PDECOT = 5;
    final int INT_TBL_CHGC = 6;
    final int INT_TBL_CHG = 7;
    final int INT_TBL_BDC = 8;
    final int INT_TBL_NCO = 9;
    final int INT_TBL_FCO = 10;
    final int INT_TBL_PUN = 11;
    final int INT_TBL_PDE = 12;
    final int INT_TBL_CHC = 13;
    final int INT_TBL_CAN = 14;
    final int INT_TBL_OBS = 15;
    final int INT_TBL_BOB = 16;
    final int INT_TBL_COPRV = 17;
    final int INT_TBL_CHGOC = 18;

    String strCodItm="";
    String strCodTipDoc="";
    String strCodDoc="";
    String strCodReg="";

    double dblCanTotRep=0;

    boolean blnEst=false;

  
    /** Creates new form ZafCom58_01 */
    public ZafCom58_01(java.awt.Frame parent, boolean modal, ZafParSis objParsis ,String strCodItmSel, String strCodTipDocSel, String strCodDocSel, String strCodRegSel, double dblCanTotRepSel ) {
       super(parent, modal);
       try{
          this.objZafParSis = (Librerias.ZafParSis.ZafParSis) objParsis.clone();
          strCodItm=strCodItmSel;
          strCodTipDoc=strCodTipDocSel;
          strCodDoc=strCodDocSel;
          strCodReg=strCodRegSel;
          dblCanTotRep=dblCanTotRepSel;
           
          initComponents();
           this.setTitle(""+objZafParSis.getNombreMenu() );
           lblTit.setText(""+objZafParSis.getNombreMenu() );
           objUti = new ZafUtil();

           configurarForm();

           
        }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }
    }


public boolean acepta(){
    return blnEst;
}



private boolean configurarForm(){
 boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    vecCab.add(INT_TBL_LIN,"");
    vecCab.add(INT_TBL_CHK," ");
    vecCab.add(INT_TBL_PRV,"Cod.Prv");
    vecCab.add(INT_TBL_NPR,"Proveedor");
    vecCab.add(INT_TBL_PUNCOT,"Pre.Uni");
    vecCab.add(INT_TBL_PDECOT,"Desc.");
    vecCab.add(INT_TBL_CHGC,"Gen.Cot");
    vecCab.add(INT_TBL_CHG,"Cot.Gen");
    vecCab.add(INT_TBL_BDC,"..");
    vecCab.add(INT_TBL_NCO,"Núm.Cot");
    vecCab.add(INT_TBL_FCO,"Fec.Cot");
    vecCab.add(INT_TBL_PUN,"Pre.Uni");
    vecCab.add(INT_TBL_PDE,"Por.Des.");
    vecCab.add(INT_TBL_CHC,"Cot.Can.");
    vecCab.add(INT_TBL_CAN,"Cantidad");
    vecCab.add(INT_TBL_OBS,"Observación");
    vecCab.add(INT_TBL_BOB,"..");
    vecCab.add(INT_TBL_COPRV,"");
    vecCab.add(INT_TBL_CHGOC,"Ord.Gen.");
    
    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);

    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LIN);

    //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
    objMouMotAda=new ZafMouMotAda();
    tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


     //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
    objTblMod.setColumnDataType(INT_TBL_PUN, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_PDE, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_CAN, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_PUNCOT, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_PDECOT, objTblMod.INT_COL_DBL, new Integer(0), null);

    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    tblDat.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CHK).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_PRV).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_NPR).setPreferredWidth(130);
    tcmAux.getColumn(INT_TBL_PUNCOT).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_PDECOT).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_CHGC).setPreferredWidth(30);
    tcmAux.getColumn(INT_TBL_CHG).setPreferredWidth(30);
    tcmAux.getColumn(INT_TBL_BDC).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_NCO).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_FCO).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_PUN).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_PDE).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_CHC).setPreferredWidth(30);
    tcmAux.getColumn(INT_TBL_CAN).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_OBS).setPreferredWidth(100);
    tcmAux.getColumn(INT_TBL_BOB).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CHGOC).setPreferredWidth(30);


    ArrayList arlColHid=new ArrayList();
    arlColHid.add(""+INT_TBL_COPRV);
    objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
    arlColHid=null;

     ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
     objTblHeaGrp.setHeight(16*2);



            ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Datos de la Cotizaciones " );
            objTblHeaColGrpAmeSur.setHeight(16);

            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_PUNCOT));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_PDECOT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;

            objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Datos de la Cotizaciones recibidas " );
            objTblHeaColGrpAmeSur.setHeight(16);

            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BDC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NCO));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FCO));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_PUN));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_PDE));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CHGC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CHG));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CHC));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;


            objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Datos de la Compras " );
            objTblHeaColGrpAmeSur.setHeight(16);

            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CAN));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_OBS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BOB));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;


    ////*****tblAnMe.getTableHeader().addMouseMotionListener(objTblMod);
    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
     vecAux.add("" + INT_TBL_CHK);
     vecAux.add("" + INT_TBL_BDC);
     vecAux.add("" + INT_TBL_PUN);
     vecAux.add("" + INT_TBL_PDE);
     vecAux.add("" + INT_TBL_CAN);
     vecAux.add("" + INT_TBL_OBS);
     vecAux.add("" + INT_TBL_CHGC);
     vecAux.add("" + INT_TBL_CHC);
     vecAux.add("" + INT_TBL_BOB);
     vecAux.add("" + INT_TBL_PUNCOT);
     vecAux.add("" + INT_TBL_PDECOT);
    objTblMod.setColumnasEditables(vecAux);
    vecAux=null;
    //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
  
    objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    tcmAux.getColumn(INT_TBL_CHK).setCellRenderer(objTblCelRenChk);
    tcmAux.getColumn(INT_TBL_CHGC).setCellRenderer(objTblCelRenChk);
    tcmAux.getColumn(INT_TBL_CHG).setCellRenderer(objTblCelRenChk);
    tcmAux.getColumn(INT_TBL_CHC).setCellRenderer(objTblCelRenChk);
    tcmAux.getColumn(INT_TBL_CHGOC).setCellRenderer(objTblCelRenChk);
    objTblCelRenChk=null;


    objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_CHK).setCellEditor(objTblCelEdiChk);
    objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
           int intColSel=tblDat.getSelectedRow();
           String strCodPrv=( (tblDat.getValueAt(intColSel, INT_TBL_CHG)==null)?"false":tblDat.getValueAt(intColSel, INT_TBL_CHG).toString() );
           if(strCodPrv.equals("true"))
               objTblCelEdiChk.setCancelarEdicion(true);

        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
        }
    });

   objTblCelEdiChk3 = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_CHGC).setCellEditor(objTblCelEdiChk3);
    objTblCelEdiChk3.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
           int intColSel=tblDat.getSelectedRow();
           String strCodPrv=( (tblDat.getValueAt(intColSel, INT_TBL_CHG)==null)?"false":tblDat.getValueAt(intColSel, INT_TBL_CHG).toString() );
           if(strCodPrv.equals("true"))
               objTblCelEdiChk3.setCancelarEdicion(true);

           strCodPrv=( (tblDat.getValueAt(intColSel, INT_TBL_CHK)==null)?"false":tblDat.getValueAt(intColSel, INT_TBL_CHK).toString() );
           if(strCodPrv.equals("false"))
               objTblCelEdiChk3.setCancelarEdicion(true);

        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
        }
    });


    objTblCelEdiChk2 = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_CHC).setCellEditor(objTblCelEdiChk2);
    objTblCelEdiChk2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
           int intColSel=tblDat.getSelectedRow();
           String strCodPrv=( (tblDat.getValueAt(intColSel, INT_TBL_CHG)==null)?"false":tblDat.getValueAt(intColSel, INT_TBL_CHG).toString() );
           if(strCodPrv.equals("false"))
               objTblCelEdiChk2.setCancelarEdicion(true);

           String strOrdGen=( (tblDat.getValueAt(intColSel, INT_TBL_CHGOC)==null)?"false":tblDat.getValueAt(intColSel, INT_TBL_CHGOC).toString() );
           if(strOrdGen.equals("true"))
               objTblCelEdiChk2.setCancelarEdicion(true);



        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
           int intColSel=tblDat.getSelectedRow();
           String strCotCan=( (tblDat.getValueAt(intColSel, INT_TBL_CHC)==null)?"false":tblDat.getValueAt(intColSel, INT_TBL_CHC).toString() );
           if(strCotCan.equals("true")){

              tblDat.setValueAt("0", intColSel, INT_TBL_PUN);
              tblDat.setValueAt("0", intColSel, INT_TBL_PDE);
              tblDat.setValueAt("0", intColSel, INT_TBL_CAN);
           }

        }
    });
    
     objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
     objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
     objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
     objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
     objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
     tcmAux.getColumn( INT_TBL_PUN ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_PDE ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_CAN ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_PUNCOT ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_PDECOT ).setCellRenderer(objTblCelRenLbl);
     objTblCelRenLbl=null;

     objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
     tcmAux.getColumn(INT_TBL_PUN).setCellEditor(objTblCelEdiTxt);
     tcmAux.getColumn(INT_TBL_PDE).setCellEditor(objTblCelEdiTxt);
      objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
          int intColSel=tblDat.getSelectedRow();
          String strCodPrv=( (tblDat.getValueAt(intColSel, INT_TBL_CHG)==null)?"false":tblDat.getValueAt(intColSel, INT_TBL_CHG).toString() );
          if(strCodPrv.equals("false"))
             objTblCelEdiTxt.setCancelarEdicion(true);
          
          String strCotCan=( (tblDat.getValueAt(intColSel, INT_TBL_CHC)==null)?"false":tblDat.getValueAt(intColSel, INT_TBL_CHC).toString() );
          if(strCotCan.equals("true"))
             objTblCelEdiTxt.setCancelarEdicion(true);

          String strOrdGen=( (tblDat.getValueAt(intColSel, INT_TBL_CHGOC)==null)?"false":tblDat.getValueAt(intColSel, INT_TBL_CHGOC).toString() );
           if(strOrdGen.equals("true"))
               objTblCelEdiTxt.setCancelarEdicion(true);
         
      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
      }
      });
     

      

     objTblCelEdiTxt2=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
     tcmAux.getColumn(INT_TBL_CAN).setCellEditor(objTblCelEdiTxt2);
      objTblCelEdiTxt2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
          int intColSel=tblDat.getSelectedRow();
          String strCodPrv=( (tblDat.getValueAt(intColSel, INT_TBL_CHG)==null)?"false":tblDat.getValueAt(intColSel, INT_TBL_CHG).toString() );
          if(strCodPrv.equals("false"))
             objTblCelEdiTxt2.setCancelarEdicion(true);

          String strCotCan=( (tblDat.getValueAt(intColSel, INT_TBL_CHC)==null)?"false":tblDat.getValueAt(intColSel, INT_TBL_CHC).toString() );
          if(strCotCan.equals("true"))
             objTblCelEdiTxt2.setCancelarEdicion(true);

          String strOrdGen=( (tblDat.getValueAt(intColSel, INT_TBL_CHGOC)==null)?"false":tblDat.getValueAt(intColSel, INT_TBL_CHGOC).toString() );
           if(strOrdGen.equals("true"))
               objTblCelEdiTxt2.setCancelarEdicion(true);
      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
          int intColSel=tblDat.getSelectedRow();
          double dblCanTotCom=0;
          for(int i=0; i < tblDat.getRowCount(); i++){
             String strCanCom=(tblDat.getValueAt(i, INT_TBL_CAN)==null?"":tblDat.getValueAt(i, INT_TBL_CAN).toString());
             dblCanTotCom += objUti.redondear( Double.parseDouble((strCanCom.equals("")?"0":strCanCom)),4);
          }

          dblCanTotCom= objUti.redondear(dblCanTotCom, 4);
        
          if( dblCanTotCom >  dblCanTotRep  ){
                MensajeInf("No puede exceder a la cantidad de reposición.\nVerifique los datos he intente nuevamente.");
                tblDat.setValueAt( "0" , intColSel, INT_TBL_CAN);
          }

      }
      });




     objTblCelEdiTxt3=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
     tcmAux.getColumn(INT_TBL_PUNCOT).setCellEditor(objTblCelEdiTxt3);
     tcmAux.getColumn(INT_TBL_PDECOT).setCellEditor(objTblCelEdiTxt3);
     objTblCelEdiTxt3.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
          int intColSel=tblDat.getSelectedRow();
          
          String strSelPrv=( (tblDat.getValueAt(intColSel, INT_TBL_CHK)==null)?"false":tblDat.getValueAt(intColSel, INT_TBL_CHK).toString() );
          if(strSelPrv.equals("false"))
             objTblCelEdiTxt3.setCancelarEdicion(true);
          
          String strCotGen=( (tblDat.getValueAt(intColSel, INT_TBL_CHG)==null)?"false":tblDat.getValueAt(intColSel, INT_TBL_CHG).toString() );
          if(strCotGen.equals("true"))
             objTblCelEdiTxt3.setCancelarEdicion(true);

      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
      }
      });



    Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
    tcmAux.getColumn(INT_TBL_BDC).setCellRenderer(objTblCelRenBut);
    objTblCelRenBut=null;
    new ButDat(tblDat, INT_TBL_BDC);   //*****

    objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
    tcmAux.getColumn(INT_TBL_BOB).setCellRenderer(objTblCelRenBut);
    objTblCelRenBut=null;
    new ButObs(tblDat, INT_TBL_BOB);   //*****

    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

     return blnres;
}

private class ButDat extends Librerias.ZafTableColBut.ZafTableColBut_uni{
    public ButDat(javax.swing.JTable tbl, int intIdx){
        super(tbl,intIdx, "Guía de remisión.");
    }
    public void butCLick() {
       int intCol = tblDat.getSelectedRow();
        llamarVentanaDat(intCol);
    }
}
private void llamarVentanaDat(int intCol){
  String strSql="";

 //    ZafCom58_01 obj = new  ZafCom58_01(javax.swing.JOptionPane.getFrameForComponent(this), true,  objZafParSis );
  //   obj.show();

   //    if(obj.acepta()){
     //      tblDat.setValueAt(obj.GetCamSel(1), intCol, INT_TBL_CODBOD);
     //      tblDat.setValueAt(obj.GetCamSel(2), intCol, INT_TBL_NOMBOD);
     //   }
  //  obj.dispose();
  //  obj=null;

}





private class ButObs extends Librerias.ZafTableColBut.ZafTableColBut_uni{
    public ButObs(javax.swing.JTable tbl, int intIdx){
        super(tbl,intIdx, "Guía de remisión.");
    }
    public void butCLick() {
       int intCol = tblDat.getSelectedRow();
       String strObs = ( tblDat.getValueAt(intCol,  INT_TBL_OBS  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_OBS  ).toString());
       llamarVenObs(strObs, intCol);
    }
}

private void llamarVenObs(String strObs, int intCol){
 Maestros.ZafMae07.ZafMae07_01 obj1 = new  Maestros.ZafMae07.ZafMae07_01(javax.swing.JOptionPane.getFrameForComponent(this), true , strObs );
 obj1.show();
 if(obj1.getAceptar())
   tblDat.setValueAt( obj1.getObser(), intCol, INT_TBL_OBS );
 obj1=null;
}







  
private boolean cargarDatCla(){
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
   try{
      conn=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
      if(conn!=null){
           stmLoc=conn.createStatement();
           java.util.Vector vecData = new java.util.Vector();
           
           strSql="SELECT x.*, CASE WHEN a.co_reg IS NULL THEN 'N' ELSE 'S' END as est, a.co_prv as coprv, CASE WHEN a.st_cotgen IS NULL THEN 'N' ELSE a.st_cotgen END as estgen" +
           " ,a.nd_preuni, a.nd_pordes, CASE WHEN a.st_cotcan IS NULL THEN 'N' ELSE a.st_cotcan END as stcotcan,  a.nd_cancom, a.tx_obs1  " +
           " , CASE WHEN a.st_comgen IS NULL THEN 'N' ELSE a.st_comgen END as  stcomgen " +
           " ,a.nd_preunicot, a.nd_pordescot   "+
           " , CASE WHEN a.st_gencot IS NULL THEN 'N' ELSE a.st_gencot END as  stgencot " +
           " FROM ( " +
           " select a1.co_prv, a2.tx_nom  from tbr_invcla as a  " +
           " inner join tbr_prvclainv as a1 on (a1.co_emp=a.co_emp and a1.co_cla=a.co_cla) " +
           " inner join tbm_cli as a2 on (a2.co_emp=a1.co_emp and a2.co_cli=a1.co_prv) " +
           " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_itm= "+strCodItm+"  and a1.st_reg not in ('I') " +
           "  UNION " +
           " select a.co_prv, a1.tx_nom from tbr_prvinv as a  " +
           " inner join tbm_cli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_prv) " +
           " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_itm= "+strCodItm+" and a1.st_reg not in ('I') " +
           " ) AS x  "+
           " LEFT JOIN tbm_procomitmrepinvprv AS a ON (a.co_emp="+objZafParSis.getCodigoEmpresa()+"  and a.co_loc="+objZafParSis.getCodigoLocal()+" " +
           " AND a.co_tipdoc="+strCodTipDoc+" AND a.co_doc="+strCodDoc+" AND a.co_reg="+strCodReg+" AND  a.co_prv=x.co_prv)  ";
    
           
           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){
               java.util.Vector vecReg = new java.util.Vector();
                 vecReg.add(INT_TBL_LIN, "");
                 vecReg.add(INT_TBL_CHK,  new Boolean( (rstLoc.getString("est").equals("S")?true:false) ) );
                 vecReg.add(INT_TBL_PRV, rstLoc.getString("co_prv") );
                 vecReg.add(INT_TBL_NPR, rstLoc.getString("tx_nom") );
                 vecReg.add(INT_TBL_PUNCOT, rstLoc.getString("nd_preunicot") );
                 vecReg.add(INT_TBL_PDECOT, rstLoc.getString("nd_pordescot") );
                 vecReg.add(INT_TBL_CHGC, new Boolean( (rstLoc.getString("stgencot").equals("S")?true:false) ) );
                 vecReg.add(INT_TBL_CHG, new Boolean( (rstLoc.getString("estgen").equals("S")?true:false) ) );
                 vecReg.add(INT_TBL_BDC,  ".." );
                 vecReg.add(INT_TBL_NCO,  "" );
                 vecReg.add(INT_TBL_FCO,  "" );
                 vecReg.add(INT_TBL_PUN, rstLoc.getString("nd_preuni") );
                 vecReg.add(INT_TBL_PDE, rstLoc.getString("nd_pordes") );
                 vecReg.add(INT_TBL_CHC,  new Boolean( (rstLoc.getString("stcotcan").equals("S")?true:false) ) );
                 vecReg.add(INT_TBL_CAN,  rstLoc.getString("nd_cancom") );
                 vecReg.add(INT_TBL_OBS,  rstLoc.getString("tx_obs1") );
                 vecReg.add(INT_TBL_BOB,  ".." );
                 vecReg.add(INT_TBL_COPRV, rstLoc.getString("coprv") );
                 vecReg.add(INT_TBL_CHGOC,  new Boolean( (rstLoc.getString("stcomgen").equals("S")?true:false) ) );

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








    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lblTit.setText("jLabel1");
        jPanel1.add(lblTit);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

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

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("General", jPanel4);

        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        butAce.setFont(new java.awt.Font("SansSerif", 0, 11));
        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(90, 23));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        jPanel5.add(butAce);

        jButton2.setFont(new java.awt.Font("SansSerif", 0, 11));
        jButton2.setText("Cancelar");
        jButton2.setPreferredSize(new java.awt.Dimension(90, 23));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton2);

        jPanel3.add(jPanel5, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-860)/2, (screenSize.height-400)/2, 860, 400);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:

        cargarDatCla();
        

    }//GEN-LAST:event_formWindowOpened

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        // TODO add your handling code here:

        if(guardarDat()){
            cargarDatCla();
            blnEst=true;
             dispose();
        }
        
}//GEN-LAST:event_butAceActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_formWindowClosing

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


       private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    

private boolean guardarDat(){
  boolean blnRes=false;
  java.sql.Connection conn;
  try{
     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);

       if(insertarCotPrv(conn)){
           conn.commit();
           blnRes=true;
           MensajeInf("Los datos se Guardar con éxito.. ");
       }else conn.rollback();

       
       conn.close();
       conn=null;
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

    System.gc();
    return blnRes;
}




public boolean insertarCotPrv(java.sql.Connection conn ){
   boolean blnRes=false;
   java.sql.Statement stmLoc;
   String strSql="";
   int intNumPrvEle=0;
   int intNumTotCotCan=0;
   double dblCanTotCom=0;
   try{
      stmLoc=conn.createStatement();

      

       for(int i=0; i<tblDat.getRowCount(); i++){
         String strCodPrv=( (tblDat.getValueAt(i, INT_TBL_COPRV)==null)?"":tblDat.getValueAt(i, INT_TBL_COPRV).toString() );
         String strObs=( (tblDat.getValueAt(i, INT_TBL_OBS)==null)?"":tblDat.getValueAt(i, INT_TBL_OBS).toString() );

        if(tblDat.getValueAt(i, INT_TBL_CHK) != null){
         if(tblDat.getValueAt(i, INT_TBL_CHK).toString().equals("true")){
             intNumPrvEle++;

            if(strCodPrv.equals("")){
               strSql+=" INSERT INTO tbm_procomitmrepinvprv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, co_prv,  tx_obs1, st_regrep, nd_preunicot, nd_pordescot, st_gencot  ) " +
               " VALUES ("+objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal()+", "+strCodTipDoc+", "+strCodDoc+" "+
               " ,"+strCodReg+", "+tblDat.getValueAt(i, INT_TBL_PRV).toString()+", '"+strObs+"', 'I', " +
               " "+( (tblDat.getValueAt(i, INT_TBL_PUNCOT)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PUNCOT).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_PUNCOT).toString()) )+" "+
               " ,"+( (tblDat.getValueAt(i, INT_TBL_PDECOT)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PDECOT).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_PDECOT).toString()) )+" "+
               " , '"+(tblDat.getValueAt(i, INT_TBL_CHGC).toString().equals("true")?'S':'N')+"' ) ; ";
            }else{
               String strEstCan=( (tblDat.getValueAt(i, INT_TBL_CHC)==null)?"false":tblDat.getValueAt(i, INT_TBL_CHC).toString() );
               double dblCanCom= objUti.redondear( Double.parseDouble( ((tblDat.getValueAt(i, INT_TBL_CAN)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CAN).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CAN).toString()) ) ), 2 );

               strSql+=" UPDATE tbm_procomitmrepinvprv SET " +
               " nd_preunicot="+( (tblDat.getValueAt(i, INT_TBL_PUNCOT)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PUNCOT).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_PUNCOT).toString()) )+" "+
               " ,nd_pordescot="+( (tblDat.getValueAt(i, INT_TBL_PDECOT)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PDECOT).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_PDECOT).toString()) )+" "+
               " ,nd_preuni="+( (tblDat.getValueAt(i, INT_TBL_PUN)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PUN).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_PUN).toString()) )+" "+
               " ,nd_pordes="+( (tblDat.getValueAt(i, INT_TBL_PDE)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PDE).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_PDE).toString()) )+" "+
               " ,st_cotcan='"+(strEstCan.equals("true")?"S":"N")+"' "+
               " ,nd_cancom="+dblCanCom+" "+
               " ,tx_obs1='"+strObs+"' "+
               " ,st_regrep='I' "+
               " ,st_gencot='"+(tblDat.getValueAt(i, INT_TBL_CHGC).toString().equals("true")?'S':'N')+"' "+
               " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND  co_loc="+objZafParSis.getCodigoLocal()+" AND " +
               " co_tipdoc="+strCodTipDoc+" AND co_doc="+strCodDoc+" AND  co_reg="+strCodReg+" AND  co_prv="+strCodPrv+" ; ";
               if(strEstCan.equals("true")) intNumTotCotCan++;

               dblCanTotCom += dblCanCom;

            }

         }else{

           if(!strCodPrv.equals("")){

                strSql+="DELETE FROM tbm_procomitmrepinvprv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
                " AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+strCodTipDoc+" AND co_doc="+strCodDoc+" AND co_reg="+strCodReg+" " +
                " AND co_prv="+strCodPrv+" ; ";
          }

       }}}

     // System.out.println("-> "+ strSql );
    
        strSql +=" UPDATE tbm_detrepinvprv SET ne_numtotprvele="+intNumPrvEle+", ne_numtotcotcan="+intNumTotCotCan+" " +
        " , nd_cantotcom="+dblCanTotCom+" WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
        " AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+strCodTipDoc+" AND co_doc="+strCodDoc+" AND co_reg="+strCodReg+" ; ";
        stmLoc.executeUpdate(strSql);
    
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

   }catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
    catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    return blnRes;
}








    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables


private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblDat.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LIN:
            strMsg="";
            break;
        case INT_TBL_PRV:
            strMsg="Cóidigo Proveedor.";
            break;
        case INT_TBL_NPR:
            strMsg="Nombre del Proveedor.";
            break;

        case INT_TBL_CHGC:
            strMsg="¿ Cotización que se Generara ? ";
            break;

        case INT_TBL_CHG:
            strMsg="¿ Cotización Generada ? ";
            break;
        case INT_TBL_NCO:
            strMsg="¨Número de Cotización.";
            break;
        case INT_TBL_FCO:
            strMsg="Fecha de Cotización.";
            break;
        case INT_TBL_PUN:
            strMsg="Precio Unitario.";
            break;
        case INT_TBL_PDE:
            strMsg="Porcentaje de Descuento.";
            break;
        case INT_TBL_CHC:
            strMsg="¿ Cotización Cancelada ?";
            break;
        case INT_TBL_CAN:
            strMsg="Cantidad a Comprar.";
            break;
        case INT_TBL_OBS:
            strMsg="Observación.";
            break;

       case INT_TBL_CHGOC:
           strMsg="Orden de Compra Generada.";
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
