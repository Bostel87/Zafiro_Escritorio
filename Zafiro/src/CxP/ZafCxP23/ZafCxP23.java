/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCxP23.java
 *
 * Created on Nov 23, 2010, 11:39:25 AM
 */
  
package CxP.ZafCxP23;


import Librerias.ZafUtil.ZafUtil; /**/
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;

/**
 *
 * @author jayapata
 */
public class ZafCxP23 extends javax.swing.JInternalFrame {
  Librerias.ZafParSis.ZafParSis objZafParSis;
  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
  ZafUtil objUti; /**/
  private ZafSelFec objSelFec;
  private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButDG;
  private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGenDG;
  private ZafThreadGUI objThrGUI;
  private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
  
  ZafVenCon objVenConTipdoc;
  ZafVenCon objVenConLoc;
  ZafVenCon objVenConCLi;
      
  String strVersion=" Ver 0.2 ";
  String strDesCorTipDoc="";
  String strDesLarTipDoc="";
  String strCodTipDoc="";
  String strCodCli="";
  String strNomCli="";
  String strCodLoc="";
  String strNomLoc=""; 

   final int INT_TBL_LINEA=0;  // NUMERO DE LINEAS
   final int INT_TBL_CODEMPR =1;  // CODIGO DEL EMPRESA
   final int INT_TBL_CODLOCR =2;  // CODIGO DEL LOCAL
   final int INT_TBL_CODTIDR =3;  // CODIGO DEL TIPO DOCUMENTO
   final int INT_TBL_CODDOCR =4;  // CODIGO DEL DOCUEMNTO
   final int INT_TBL_CODREGR =5;  // CODIGO DEL REGISTRO
   final int INT_TBL_CODPRV=6; // CODIGO DEL PROVEEDOR
   final int INT_TBL_NOMPRV=7;  // NOMBRE DEL PROVEEDOR
   final int INT_TBL_NUMFAC=8;  // NUMERO DE FACTURA
   final int INT_TBL_FECFAC=9;  // FECHA DE FACTURA
   final int INT_TBL_VALFAC=10;  // VALOR DE FACTURAS
   final int INT_TBL_NUMSER=11;  // NUMERO  DE SERIE
   final int INT_TBL_NUMAUT=12;  // NUMERO DE AUTORIZACION
   final int INT_TBL_FECCAD=13;  // FECHA DE CADUCIDAD
   final int INT_TBL_BUTREC=14;  // BOTON PARA BUSCAR ORDENES DE COMPRA
   final int INT_TBL_ASIGOC=15;    // OBSERVACION
   final int INT_TBL_VALTOTASI=16;   // CODIGO DE REGISTRO
   final int INT_TBL_CODLOC =17;  // CODIGO DEL LOCAL
   final int INT_TBL_CODTID=18;  // CODIGO DEL TIPO DE DOCUMENTO
   final int INT_TBL_DCTIPDOC=19;  // VALOR APLICADO DE LAS ORDENES DE COMPRA
   final int INT_TBL_DLTIPDOC=20;   // estado si ha cambiado o agregado facturas.
   final int INT_TBL_CODDOC=21;  //  CODIGO DOCUMENTO
   final int INT_TBL_NUMDOC=22;  // VALOR DE FACTURAS ORIGEN
   final int INT_TBL_VALOC=23;  // VALOR DE FACTURAS ORIGEN
   final int INT_TBL_TIERETE=24;  // VALOR DE FACTURAS ORIGEN
   final int INT_TBL_BUTRET=25;

  
  javax.swing.JTextField txtCodTipDoc = new javax.swing.JTextField();
  Vector vecCab=new Vector();
  private Vector vecAux;

    /** Creates new form ZafCxP23 */
    public ZafCxP23(Librerias.ZafParSis.ZafParSis obj) {
       try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();

            this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
            lblTit.setText(objZafParSis.getNombreMenu());  /**/

	     objUti = new ZafUtil(); /**/
            
               //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(true);
            panFilFec.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);


//	     objTooBar = new mitoolbar(this);
//	     this.getContentPane().add(objTooBar,"South");

//              objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

//             txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
//             txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
//             txtFecDoc.setText("");
//             panCabRecChq.add(txtFecDoc);
//             txtFecDoc.setBounds(580, 8, 92, 20);

	 }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }


public void Configura_ventana_consulta()
{
    configurarVenConClientes();
    configurarVenConTipDoc();
    configurarVenConLocal();
    configurarTabla(); 
}


private boolean configurarTabla(){
  boolean blnRes=false;
  try{
    vecCab.clear();

    vecCab.add(INT_TBL_LINEA,"");
    vecCab.add(INT_TBL_CODEMPR,"Cód.EMP.");
    vecCab.add(INT_TBL_CODLOCR,"Cód.Loc.");
    vecCab.add(INT_TBL_CODTIDR,"Cód.TIP.");
    vecCab.add(INT_TBL_CODDOCR,"Cód.DOC.");
    vecCab.add(INT_TBL_CODREGR,"Cód.REG.");
    vecCab.add(INT_TBL_CODPRV,"Cód.Prv.");
    vecCab.add(INT_TBL_NOMPRV,"Nom.Pro.");
    vecCab.add(INT_TBL_NUMFAC,"Núm.Fac.");
    vecCab.add(INT_TBL_FECFAC,"Fec.Fac.");
    vecCab.add(INT_TBL_VALFAC,"Val.Fac.");
    vecCab.add(INT_TBL_NUMSER,"Núm.ser.Fac.");
    vecCab.add(INT_TBL_NUMAUT,"Núm.Aut.");
    vecCab.add(INT_TBL_FECCAD,"Fec.Cac.");
    vecCab.add(INT_TBL_BUTREC,"");
    vecCab.add(INT_TBL_ASIGOC,"Asi.Ord.Com");
    vecCab.add(INT_TBL_VALTOTASI,"Val.Tot.Asi.");
    vecCab.add(INT_TBL_CODLOC,"Cod.Loc.");
    vecCab.add(INT_TBL_CODTID,"Cod.Tip.Doc.");
    vecCab.add(INT_TBL_DCTIPDOC,"TipDoc");
    vecCab.add(INT_TBL_DLTIPDOC,"TipDoc.");
    vecCab.add(INT_TBL_CODDOC,"Cod.Doc");
    vecCab.add(INT_TBL_NUMDOC,"Num.Doc");
    vecCab.add(INT_TBL_VALOC,"Val.Doc.");
    vecCab.add(INT_TBL_TIERETE,"Ret.Emi.");
    vecCab.add(INT_TBL_BUTRET,"");

     objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
     objTblMod.setHeader(vecCab);
     tblDat.setModel(objTblMod);

     tblDat.setRowSelectionAllowed(true);
     tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
     new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);

    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
     tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
     javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();


     objTblMod.setColumnDataType(INT_TBL_VALFAC, objTblMod.INT_COL_DBL, new Integer(0), null);
     objTblMod.setColumnDataType(INT_TBL_VALOC, objTblMod.INT_COL_DBL, new Integer(0), null);
     objTblMod.setColumnDataType(INT_TBL_VALTOTASI, objTblMod.INT_COL_DBL, new Integer(0), null);


        objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_VALFAC).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_VALOC).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_VALTOTASI).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;


      //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
//      objMouMotAda=new ZafMouMotAda();
//      tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


        /* Aqui se agrega las columnas que van
             ha hacer ocultas
         * */
        ArrayList arlColHid=new ArrayList();
        arlColHid.add(""+INT_TBL_CODEMPR);
        arlColHid.add(""+INT_TBL_CODTIDR);
        arlColHid.add(""+INT_TBL_CODDOCR);
        arlColHid.add(""+INT_TBL_CODREGR);
        arlColHid.add(""+INT_TBL_CODTID);
        arlColHid.add(""+INT_TBL_CODDOC);
        objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;


//     para hacer editable las celdas
    vecAux=new Vector();
    vecAux.add("" + INT_TBL_BUTREC);
    vecAux.add("" + INT_TBL_BUTRET);
    objTblMod.setColumnasEditables(vecAux);
    vecAux=null;

    new ZafTblEdi(tblDat);

   //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CODLOCR).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_CODPRV).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_NOMPRV).setPreferredWidth(180);
    tcmAux.getColumn(INT_TBL_NUMFAC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_FECFAC).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_VALFAC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_CODDOC).setPreferredWidth(40);
    tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_NUMSER).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_NUMAUT).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_FECCAD).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_BUTREC).setPreferredWidth(20);
    tcmAux.getColumn(INT_TBL_ASIGOC).setPreferredWidth(30);
    tcmAux.getColumn(INT_TBL_VALTOTASI).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(40);
    tcmAux.getColumn(INT_TBL_CODTID).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_DCTIPDOC).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_DLTIPDOC).setPreferredWidth(110);
    tcmAux.getColumn(INT_TBL_VALOC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_TIERETE).setPreferredWidth(30);
    tcmAux.getColumn(INT_TBL_BUTRET).setPreferredWidth(20);
    
//    java.awt.Color colFonCol;
//    colFonCol=new java.awt.Color(228,228,203);
     Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
    Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
    objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
//    objTblCelRenChk.setBackground(colFonCol);
    tcmAux.getColumn(INT_TBL_ASIGOC).setCellRenderer(objTblCelRenChk);
    tcmAux.getColumn(INT_TBL_TIERETE).setCellRenderer(objTblCelRenChk);


//    Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
//    objTblCelEdiTxt.setBackground(colFonCol);
//    tcmAux.getColumn(INT_TBL_OBSAUT).setCellEditor(objTblCelEdiTxt);
//    objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
//      }
//      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//      }
//     });
//    objTblCelEdiTxt=null;


//    objTblCelLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
//    tcmAux.getColumn(INT_TBL_FECAUT).setCellRenderer(objTblCelLbl);
//    objTblCelLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//       public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
//           objTblCelLbl.setToolTipText("Fecha Autorizacón: "+objTblCelLbl.getText());
//       }
//    });


    objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_ASIGOC).setCellEditor(objTblCelEdiChk);
    tcmAux.getColumn(INT_TBL_TIERETE).setCellEditor(objTblCelEdiChk);
    objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
    }
    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
    }
   });
 objTblCelRenChk=null;
 objTblCelEdiChk=null;


            objTblCelRenButDG=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTREC).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_BUTRET).setCellRenderer(objTblCelRenButDG);
           
            objTblCelRenButDG.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    switch (objTblCelRenButDG.getColumnRender())
                    {
                        case INT_TBL_BUTREC:

//                            if (objTblMod.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL_RECMERDEV).toString().equals("true") )
                                objTblCelRenButDG.setText("...");
//                            else
//                                objTblCelRenButDG.setText("");

                       break;

                       case INT_TBL_BUTRET:

//                            if (objTblMod.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL_REVTECMERDEV).toString().equals("true") )
                                objTblCelRenButDG.setText("...");
//                            else
//                                objTblCelRenButDG.setText("");

                       break;

                    }
                }
            });


            objTblCelEdiButGenDG=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_BUTREC).setCellEditor(objTblCelEdiButGenDG);
            tcmAux.getColumn(INT_TBL_BUTRET).setCellEditor(objTblCelEdiButGenDG);
            
             objTblCelEdiButGenDG.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;
               public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1){

                        switch (intColSel)
                        {
                          case INT_TBL_BUTREC:

//                           if (objTblMod.getValueAt(intFilSel, INT_TBL_RECMERDEV).toString().equals("false") )
//                              objTblCelEdiButGenDG.setCancelarEdicion(true);

                          break;

                         case INT_TBL_BUTRET:

//                           if (objTblMod.getValueAt(intFilSel, INT_TBL_REVTECMERDEV).toString().equals("false") )
//                              objTblCelEdiButGenDG.setCancelarEdicion(true);

                          break;

                        
                        }

                 }}
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel=tblDat.getSelectedColumn();
                    if(intFilSel!=-1){
                        switch (intColSel)
                        {
                          case INT_TBL_BUTREC:

                                 ventRecChq( intFilSel);

                          break;

                          case INT_TBL_BUTRET:

//                             llamarRevTec( objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString()
//                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString()
//                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOC).toString()
//                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
//                                 );

                          break;

                        }

                }}
           });



       ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
       objTblHeaGrp.setHeight(16*2);

           ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("Reporte");
           objTblHeaColGrpAmeSur.setHeight(16);
           objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODEMPR));
           objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODLOCR));
           objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODTIDR));
           objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODDOCR));
           objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODREGR));
           objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODPRV));
           objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NOMPRV));
           objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
           objTblHeaColGrpAmeSur=null;

           objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Factura Proveedor ");
           objTblHeaColGrpAmeSur.setHeight(16);
               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NUMFAC));
               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECFAC));
               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_VALFAC));
               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NUMSER));
               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NUMAUT));
               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECCAD));
               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTREC));
               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_ASIGOC));
               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_VALTOTASI));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;


            objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Ordenes de Compra ");
            objTblHeaColGrpAmeSur.setHeight(16);
               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODDOC));
               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NUMDOC));
               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODLOC));
               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODTID));
               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DCTIPDOC));
               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DLTIPDOC));
               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_VALOC));
               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TIERETE));
               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTRET));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;

          new ZafTblOrd(tblDat);

          tblDat.getTableHeader().setReorderingAllowed(false);

        tcmAux=null;
        setEditable(true);
        blnRes=true;

      }catch (Exception e){
          blnRes=false;
          objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
      }



    public void setEditable(boolean editable) {
        if (editable==true){
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }else{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }


 private void ventRecChq(int intNumFil){
  String strCodEmp="",strCodLoc="", strCodTipDoc="", strCodDoc="", strCodReg="";
  try{

         strCodEmp=tblDat.getValueAt(intNumFil, INT_TBL_CODEMPR).toString();
         strCodLoc=tblDat.getValueAt(intNumFil, INT_TBL_CODLOCR).toString();
         strCodTipDoc=tblDat.getValueAt(intNumFil, INT_TBL_CODTIDR).toString();
         strCodDoc=tblDat.getValueAt(intNumFil, INT_TBL_CODDOCR).toString();
         strCodReg=tblDat.getValueAt(intNumFil, INT_TBL_CODREGR).toString();

         CxP.ZafCxP09.ZafCxP09 obj1 = new CxP.ZafCxP09.ZafCxP09(objZafParSis,  new Integer(Integer.parseInt(strCodEmp)), new Integer(Integer.parseInt(strCodLoc)), new Integer(Integer.parseInt(strCodTipDoc)), new Integer(Integer.parseInt(strCodDoc)), strCodReg, 1739 );
         this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
         obj1.show();

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
        " AND a.st_prv='S' AND a.st_reg NOT IN('I','T')  order by a.tx_nom ";

        objVenConCLi=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
   return blnRes;
}

private boolean configurarVenConLocal() {
    boolean blnRes=true;
    try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("a.co_loc");
        arlCam.add("a.tx_nom");

        ArrayList arlAli=new ArrayList();
        arlAli.add("Código");
        arlAli.add("Nom.Loc.");

        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("50");
        arlAncCol.add("350");

        //Armar la sentencia SQL.
        String  strSQL;

        strSQL="select  co_loc, tx_nom   from tbm_loc where co_emp="+objZafParSis.getCodigoEmpresa()+" and st_reg in ('P','A') ";

        objVenConLoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
   return blnRes;
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
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panGenTabGen = new javax.swing.JPanel();
        panFildat = new javax.swing.JPanel();
        radTotDoc = new javax.swing.JRadioButton();
        optCri = new javax.swing.JRadioButton();
        lblTipdoc = new javax.swing.JLabel();
        lblCli1 = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        lblSol1 = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butBusTipDoc = new javax.swing.JButton();
        butBusCli = new javax.swing.JButton();
        butBusLoc = new javax.swing.JButton();
        chkFacPenAsiOc = new javax.swing.JCheckBox();
        chkFacAsiOc = new javax.swing.JCheckBox();
        chkOcPenAsiFac = new javax.swing.JCheckBox();
        chkOcAsiFac = new javax.swing.JCheckBox();
        panFilFec = new javax.swing.JPanel();
        panDat = new javax.swing.JPanel();
        panFilTbl = new javax.swing.JPanel();
        scrTblSegSolDev = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

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

        panFildat.setPreferredSize(new java.awt.Dimension(100, 200));
        panFildat.setRequestFocusEnabled(false);
        panFildat.setLayout(null);

        buttonGroup1.add(radTotDoc);
        radTotDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        radTotDoc.setSelected(true);
        radTotDoc.setText("Todos los Documentos");
        radTotDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radTotDocActionPerformed(evt);
            }
        });
        panFildat.add(radTotDoc);
        radTotDoc.setBounds(10, 0, 160, 20);

        buttonGroup1.add(optCri);
        optCri.setFont(new java.awt.Font("SansSerif", 0, 11));
        optCri.setText("Solo los documentos que cumplan el criterio seleccionado");
        panFildat.add(optCri);
        optCri.setBounds(10, 20, 340, 20);

        lblTipdoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTipdoc.setText("Tipo de Documento:");
        panFildat.add(lblTipdoc);
        lblTipdoc.setBounds(40, 40, 100, 20);

        lblCli1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli1.setText("Proveedor:");
        panFildat.add(lblCli1);
        lblCli1.setBounds(40, 60, 60, 20);

        txtDesCorTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        panFildat.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(170, 40, 60, 20);

        txtDesLarTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
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
        panFildat.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(230, 40, 260, 20);

        txtCodCli.setFont(new java.awt.Font("SansSerif", 0, 11));
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
        panFildat.add(txtCodCli);
        txtCodCli.setBounds(170, 60, 60, 20);

        txtNomCli.setFont(new java.awt.Font("SansSerif", 0, 11));
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
        panFildat.add(txtNomCli);
        txtNomCli.setBounds(230, 60, 260, 20);

        lblSol1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblSol1.setText("Local:");
        panFildat.add(lblSol1);
        lblSol1.setBounds(40, 80, 60, 20);

        txtCodLoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtCodLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLocActionPerformed(evt);
            }
        });
        txtCodLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLocFocusLost(evt);
            }
        });
        panFildat.add(txtCodLoc);
        txtCodLoc.setBounds(170, 80, 60, 20);

        txtNomLoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtNomLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomLocActionPerformed(evt);
            }
        });
        txtNomLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomLocFocusLost(evt);
            }
        });
        panFildat.add(txtNomLoc);
        txtNomLoc.setBounds(230, 80, 260, 20);

        butBusTipDoc.setText("jButton2");
        butBusTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusTipDocActionPerformed(evt);
            }
        });
        panFildat.add(butBusTipDoc);
        butBusTipDoc.setBounds(490, 40, 20, 20);

        butBusCli.setText("jButton2");
        butBusCli.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusCliActionPerformed(evt);
            }
        });
        panFildat.add(butBusCli);
        butBusCli.setBounds(490, 60, 20, 20);

        butBusLoc.setText("jButton2");
        butBusLoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusLocActionPerformed(evt);
            }
        });
        panFildat.add(butBusLoc);
        butBusLoc.setBounds(490, 80, 20, 20);

        chkFacPenAsiOc.setFont(new java.awt.Font("SansSerif", 0, 11));
        chkFacPenAsiOc.setText("Mostrar las facturas que ésten pendiente de asignar ordenes de compra.");
        chkFacPenAsiOc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFacPenAsiOcActionPerformed(evt);
            }
        });
        panFildat.add(chkFacPenAsiOc);
        chkFacPenAsiOc.setBounds(40, 110, 430, 20);

        chkFacAsiOc.setFont(new java.awt.Font("SansSerif", 0, 11));
        chkFacAsiOc.setText("Mostrar las facturas a las que se le asigno ordenes de compra.");
        chkFacAsiOc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFacAsiOcActionPerformed(evt);
            }
        });
        panFildat.add(chkFacAsiOc);
        chkFacAsiOc.setBounds(40, 130, 430, 20);

        chkOcPenAsiFac.setFont(new java.awt.Font("SansSerif", 0, 11));
        chkOcPenAsiFac.setText("Mostrar las ordenes de compra que ésten pendiente de asignar las facturas.");
        chkOcPenAsiFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOcPenAsiFacActionPerformed(evt);
            }
        });
        panFildat.add(chkOcPenAsiFac);
        chkOcPenAsiFac.setBounds(40, 150, 430, 20);

        chkOcAsiFac.setFont(new java.awt.Font("SansSerif", 0, 11));
        chkOcAsiFac.setText("Mostrar las ordenes de compra que ya estan asignadas a factura.");
        chkOcAsiFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOcAsiFacActionPerformed(evt);
            }
        });
        panFildat.add(chkOcAsiFac);
        chkOcAsiFac.setBounds(40, 170, 430, 20);

        panGenTabGen.add(panFildat, java.awt.BorderLayout.CENTER);

        panFilFec.setPreferredSize(new java.awt.Dimension(100, 85));

        javax.swing.GroupLayout panFilFecLayout = new javax.swing.GroupLayout(panFilFec);
        panFilFec.setLayout(panFilFecLayout);
        panFilFecLayout.setHorizontalGroup(
            panFilFecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 685, Short.MAX_VALUE)
        );
        panFilFecLayout.setVerticalGroup(
            panFilFecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 85, Short.MAX_VALUE)
        );

        panGenTabGen.add(panFilFec, java.awt.BorderLayout.NORTH);

        tabGen.addTab("General", panGenTabGen);

        panDat.setLayout(new java.awt.BorderLayout());

        panFilTbl.setLayout(new java.awt.BorderLayout());

        scrTblSegSolDev.setPreferredSize(new java.awt.Dimension(454, 400));

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
        scrTblSegSolDev.setViewportView(tblDat);

        panFilTbl.add(scrTblSegSolDev, java.awt.BorderLayout.CENTER);

        panDat.add(panFilTbl, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Reporte", panDat);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(199, 30));
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

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void radTotDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radTotDocActionPerformed
        // TODO add your handling code here:

        limpiarText();
    }//GEN-LAST:event_radTotDocActionPerformed



private void limpiarText(){

// txtCodLoc.setText("");
// txtNomLoc.setText("");
 txtDesCorTipDoc.setText("");
 txtDesLarTipDoc.setText("");
 txtCodCli.setText("");
 txtNomCli.setText("");
 txtCodLoc.setText("");
 txtNomLoc.setText("");

 chkFacPenAsiOc.setSelected(false);
 chkFacAsiOc.setSelected(false);
 chkOcAsiFac.setSelected(false);
 chkOcPenAsiFac.setSelected(false);


}


    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }else
                BuscarTipDoc("a.tx_descor",txtDesCorTipDoc.getText(),1);
        }else
            txtDesCorTipDoc.setText(strDesCorTipDoc);
    }//GEN-LAST:event_txtDesCorTipDocFocusLost


public void BuscarTipDoc(String campo,String strBusqueda,int tipo){
  objVenConTipdoc.setTitle("Listado de Vendedores");
  if(objVenConTipdoc.buscar(campo, strBusqueda )) {
      txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
      txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
      txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
      strCodTipDoc=objVenConTipdoc.getValueAt(1);
      strDesCorTipDoc=objVenConTipdoc.getValueAt(2);
      strDesLarTipDoc=objVenConTipdoc.getValueAt(3);
      optCri.setSelected(true);
  }else{
        objVenConTipdoc.setCampoBusqueda(tipo);
        objVenConTipdoc.cargarDatos();
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE) {
           txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
           txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
           txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
           strCodTipDoc=objVenConTipdoc.getValueAt(1);
           strDesCorTipDoc=objVenConTipdoc.getValueAt(2);
           strDesLarTipDoc=objVenConTipdoc.getValueAt(3);
           optCri.setSelected(true);
        }else{
           txtCodTipDoc.setText(strCodTipDoc);
           txtDesCorTipDoc.setText(strDesCorTipDoc);
           txtDesLarTipDoc.setText(strDesLarTipDoc);
  }}}



     
    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }else
                BuscarTipDoc("a.tx_deslar",txtDesLarTipDoc.getText(),2);
        }else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
}//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
}//GEN-LAST:event_txtCodCliActionPerformed

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
}//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        // TODO add your handling code here:
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)) {
            if(txtCodCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }else
                BuscarCliente("a.co_cli",txtCodCli.getText(),0);
        }else
            txtCodCli.setText(strCodCli);
    }//GEN-LAST:event_txtCodCliFocusLost


public void BuscarCliente(String campo,String strBusqueda,int tipo){
  objVenConCLi.setTitle("Listado de Clientes");




  if(objVenConCLi.buscar(campo, strBusqueda )) {
      txtCodCli.setText(objVenConCLi.getValueAt(1));
      txtNomCli.setText(objVenConCLi.getValueAt(2));
      strCodCli=objVenConCLi.getValueAt(1);
      strNomCli=objVenConCLi.getValueAt(2);
      optCri.setSelected(true);
  }else{
        objVenConCLi.setCampoBusqueda(tipo);
        objVenConCLi.cargarDatos();
        objVenConCLi.show();
        if (objVenConCLi.getSelectedButton()==objVenConCLi.INT_BUT_ACE) {
           txtCodCli.setText(objVenConCLi.getValueAt(1));
           txtNomCli.setText(objVenConCLi.getValueAt(2));
           strCodCli=objVenConCLi.getValueAt(1);
           strNomCli=objVenConCLi.getValueAt(2);
           optCri.setSelected(true);
        }else{
            txtCodCli.setText(strCodCli);
            txtNomCli.setText(strNomCli);
  }}}




    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        txtNomCli.transferFocus();
}//GEN-LAST:event_txtNomCliActionPerformed

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
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
                BuscarCliente("a.tx_nom",txtNomCli.getText(),1);
        }else
            txtNomCli.setText(strNomCli);
    }//GEN-LAST:event_txtNomCliFocusLost

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        txtCodLoc.transferFocus();
}//GEN-LAST:event_txtCodLocActionPerformed

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        strCodLoc=txtCodLoc.getText();
        txtCodLoc.selectAll();
}//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc)) {
            if(txtCodLoc.getText().equals("")) {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            }else
                BuscarLocal("a.co_loc",txtCodLoc.getText(),0);
        }else
            txtCodLoc.setText(strCodLoc);
}//GEN-LAST:event_txtCodLocFocusLost



public void BuscarLocal(String campo,String strBusqueda,int tipo){
  objVenConLoc.setTitle("Listado de Local");

  if(objVenConLoc.buscar(campo, strBusqueda )) {
      txtCodLoc.setText(objVenConLoc.getValueAt(1));
      txtNomLoc.setText(objVenConLoc.getValueAt(2));
      strCodLoc=objVenConLoc.getValueAt(1);
      strNomLoc=objVenConLoc.getValueAt(2);
      optCri.setSelected(true);
  }else{
        objVenConLoc.setCampoBusqueda(tipo);
        objVenConLoc.cargarDatos();
        objVenConLoc.show();
        if (objVenConLoc.getSelectedButton()==objVenConLoc.INT_BUT_ACE) {
          txtCodLoc.setText(objVenConLoc.getValueAt(1));
          txtNomLoc.setText(objVenConLoc.getValueAt(2));
          strCodLoc=objVenConLoc.getValueAt(1);
          strNomLoc=objVenConLoc.getValueAt(2);
           optCri.setSelected(true);
        }else{
            txtCodLoc.setText(strCodLoc);
            txtNomLoc.setText(strNomLoc);
  }}}




    private void txtNomLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLocActionPerformed
        txtNomLoc.transferFocus();
}//GEN-LAST:event_txtNomLocActionPerformed

    private void txtNomLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusGained
        strNomLoc=txtNomLoc.getText();
        txtNomLoc.selectAll();
}//GEN-LAST:event_txtNomLocFocusGained

    private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
        if (!txtNomLoc.getText().equalsIgnoreCase(strNomLoc)) {
            if(txtNomLoc.getText().equals("")) {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            }else
                BuscarLocal("a.tx_nom",txtNomLoc.getText(),1);
        }else
            txtNomLoc.setText(strNomLoc);
    }//GEN-LAST:event_txtNomLocFocusLost

    private void butBusTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusTipDocActionPerformed
        objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
        objVenConTipdoc.setCampoBusqueda(1);
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE) {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));

            strCodTipDoc=objVenConTipdoc.getValueAt(1);
            strDesCorTipDoc=objVenConTipdoc.getValueAt(2);
            strDesLarTipDoc=objVenConTipdoc.getValueAt(3);

            optCri.setSelected(true);
        }

    }//GEN-LAST:event_butBusTipDocActionPerformed

    private void butBusCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusCliActionPerformed
        // TODO add your handling code here:

        objVenConCLi.setTitle("Listado de Clientes");

        objVenConCLi.setCampoBusqueda(1);
        objVenConCLi.show();
        if (objVenConCLi.getSelectedButton()==objVenConCLi.INT_BUT_ACE) {
            txtCodCli.setText(objVenConCLi.getValueAt(1));
            txtNomCli.setText(objVenConCLi.getValueAt(2));
            strCodCli=objVenConCLi.getValueAt(1);
            strNomCli=objVenConCLi.getValueAt(2);
            optCri.setSelected(true);
        }
}//GEN-LAST:event_butBusCliActionPerformed

    private void butBusLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusLocActionPerformed
        // TODO add your handling code here:
        objVenConLoc.setTitle("Listado de Solicitantes");

        objVenConLoc.setCampoBusqueda(1);
        objVenConLoc.show();
        if (objVenConLoc.getSelectedButton()==objVenConLoc.INT_BUT_ACE) {
            txtCodLoc.setText(objVenConLoc.getValueAt(1));
            txtNomLoc.setText(objVenConLoc.getValueAt(2));
            strCodLoc=objVenConLoc.getValueAt(1);
            strNomLoc=objVenConLoc.getValueAt(2);
            optCri.setSelected(true);
        }
        
}//GEN-LAST:event_butBusLocActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
        
    }//GEN-LAST:event_formInternalFrameOpened

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed

        if (objThrGUI==null) {
            objThrGUI=new ZafThreadGUI();
            objThrGUI.start();
        }

    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed

        cerrarVen();
}//GEN-LAST:event_butCerActionPerformed

    private void chkFacPenAsiOcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFacPenAsiOcActionPerformed
        // TODO add your handling code here:
        optCri.setSelected(true);
    }//GEN-LAST:event_chkFacPenAsiOcActionPerformed

    private void chkFacAsiOcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFacAsiOcActionPerformed
        // TODO add your handling code here:
        optCri.setSelected(true);
    }//GEN-LAST:event_chkFacAsiOcActionPerformed

    private void chkOcPenAsiFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOcPenAsiFacActionPerformed
        // TODO add your handling code here:
        optCri.setSelected(true);
    }//GEN-LAST:event_chkOcPenAsiFacActionPerformed

    private void chkOcAsiFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOcAsiFacActionPerformed
        // TODO add your handling code here:
        optCri.setSelected(true);
    }//GEN-LAST:event_chkOcAsiFacActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        cerrarVen();
    }//GEN-LAST:event_formInternalFrameClosing


private void cerrarVen(){
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
        System.gc();
        //blnAcepta=false;
        dispose();
    }
 }


private class ZafThreadGUI extends Thread
{
 public void run(){

    lblMsgSis.setText("Obteniendo datos...");
    pgrSis.setIndeterminate(true);

    consultar(condiconSql());
    tabGen.setSelectedIndex(1);

   objThrGUI=null;
}
}


private void consultar(String strFil){
java.sql.Connection conn;
java.sql.Statement  stm;
java.sql.ResultSet rst;
String strSql="";
try
{

   conn = java.sql.DriverManager.getConnection( objZafParSis.getStringConexion() , objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
   if (conn!=null){

        stm=conn.createStatement();

        String strAux="";
         if(optCri.isSelected()){
                if(chkOcPenAsiFac.isSelected()){
                   if(strAux.equals("")) strAux="  WHERE nd_tot <> valtotasi ";
                }

                if(chkOcAsiFac.isSelected()){
                   if(strAux.equals("")) strAux="  WHERE nd_tot = valtotasi ";
                   else strAux+="  or nd_tot = valtotasi ";

                }
          }

        strSql="SELECT * FROM ( SELECT  a1.nd_valtotasi,  CASE WHEN a1.nd_valapl > 0 THEN 'S' ELSE  'N' END AS estApl, " +
        " a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.co_cli, a2.tx_nom,  a1.tx_numchq, a1.fe_recchq,  " +
        " a1.fe_venchq, a1.nd_monchq, a1.tx_obs1, a1.nd_valapl, a1.fe_asitipdoccon, a1.st_asidocrel, a1.st_regrep, a1.st_reg  " +
        " ,a1.tx_numser, a1.tx_numautsri, a1.tx_feccad " +
        " ,a4.co_loc as colococ, a4.co_tipdoc as cotipdococ, a5.tx_descor, a5.tx_deslar, a4.co_doc as codococ, a4.ne_numdoc, a4.nd_tot  " +
        " ,( select sum(x.nd_valasi) from tbr_detrecdoccabmovinv as x where x.co_emprel=a4.co_emp and x.co_locrel=a4.co_loc and x.co_tipdocrel=a4.co_tipdoc and x.co_docrel=a4.co_doc " +
        " and x.st_reg = 'A' ) as valtotasi " +
        " FROM tbm_cabrecdoc as a " +
        " inner join tbm_detrecdoc as a1 on(a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
        " INNER JOIN tbm_cli as a2 ON (a2.co_emp=a1.co_emp and a2.co_cli=a1.co_cli) " +
        " left join tbr_detrecdoccabmovinv as a3 on (a3.co_emp=a1.co_emp and a3.co_loc=a1.co_loc and a3.co_tipdoc=a1.co_tipdoc and a3.co_doc=a1.co_doc and a3.co_reg=a1.co_reg AND a3.st_reg = 'A' ) " +
        " left join tbm_cabmovinv as a4 on (a4.co_emp=a3.co_emprel and a4.co_loc=a3.co_locrel and a4.co_tipdoc=a3.co_tipdocrel and a4.co_doc=a3.co_docrel )  " +
        " left join tbm_cabtipdoc as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_loc and a5.co_tipdoc=a4.co_tipdoc )         " +
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+"  " +
        " AND a.st_reg NOT IN ('E', 'I') AND a1.st_reg NOT IN ('E','I')    "+strFil+" " +
        " ORDER BY a1.co_reg   ) as x  "+strAux+" ";

        System.out.println(" "+ strSql );
        Vector vecData = new Vector();
        rst = stm.executeQuery(strSql);
        while(rst.next()){
  
                 java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add(INT_TBL_CODEMPR, rst.getString("co_emp") );
                    vecReg.add(INT_TBL_CODLOCR, rst.getString("co_loc") );
                    vecReg.add(INT_TBL_CODTIDR, rst.getString("co_tipdoc") );
                    vecReg.add(INT_TBL_CODDOCR, rst.getString("co_doc") );
                    vecReg.add(INT_TBL_CODREGR, rst.getString("co_reg") );
                    vecReg.add(INT_TBL_CODPRV, rst.getString("co_cli") );
                    vecReg.add(INT_TBL_NOMPRV, rst.getString("tx_nom") );
                    vecReg.add(INT_TBL_NUMFAC, rst.getString("tx_numchq") );
                    vecReg.add(INT_TBL_FECFAC, rst.getString("fe_venchq") );
                    vecReg.add(INT_TBL_VALFAC, rst.getString("nd_monchq") );
                    vecReg.add(INT_TBL_NUMSER, rst.getString("tx_numser") );
                    vecReg.add(INT_TBL_NUMAUT, rst.getString("tx_numautsri") );
                    vecReg.add(INT_TBL_FECCAD, rst.getString("tx_feccad") );
                    vecReg.add(INT_TBL_BUTREC, "" );
                    vecReg.add(INT_TBL_ASIGOC, new Boolean(  (rst.getString("st_asidocrel")==null?false:(rst.getString("st_asidocrel").equals("S")?true:false)) ) );
                    vecReg.add(INT_TBL_VALTOTASI,  rst.getString("nd_valtotasi") );
                    vecReg.add(INT_TBL_CODLOC, rst.getString("colococ") );
                    vecReg.add(INT_TBL_CODTID, rst.getString("cotipdococ") );
                    vecReg.add(INT_TBL_DCTIPDOC, rst.getString("tx_descor") );
                    vecReg.add(INT_TBL_DLTIPDOC, rst.getString("tx_deslar") );
                    vecReg.add(INT_TBL_CODDOC, rst.getString("codococ") );
                    vecReg.add(INT_TBL_NUMDOC, rst.getString("ne_numdoc") );
                    vecReg.add(INT_TBL_VALOC, rst.getString("nd_tot") );
                    vecReg.add(INT_TBL_TIERETE, new Boolean(  (rst.getString("estApl")==null?false:(rst.getString("estApl").equals("S")?true:false)) ) );
                    vecReg.add(INT_TBL_BUTRET, "" );

                    
//                    vecReg.add(INT_TBL_DEVAUT, new Boolean( (rst.getString("st_aut").equals("A")?true:false) ) );
                 
                  vecData.add(vecReg);
         }
         objTblMod.setData(vecData);
         tblDat .setModel(objTblMod);

      rst.close();
      rst=null;
      stm.close();
      stm=null;
      conn.close();
      conn=null;
    }
     lblMsgSis.setText("Listo");
     pgrSis.setValue(0);
     pgrSis.setIndeterminate(false);

}
catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e);   }
catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e);          }
}




private String condiconSql(){
   String sqlFil="";

      if(optCri.isSelected()){

        if(!txtCodTipDoc.getText().equals(""))
            sqlFil+=" AND a.co_tipdoc="+txtCodTipDoc.getText();
        else
            sqlFil+=" AND a.co_tipdoc in (  Select a.co_tipdoc from tbr_tipdocprg as b " +
	    " inner join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
            " where   b.co_emp="+objZafParSis.getCodigoEmpresa()+" and " +
            " b.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
            " b.co_mnu = "+objZafParSis.getCodigoMenu()+ " ) ";


        if(!txtCodCli.getText().equals(""))
            sqlFil+=" AND a2.co_cli="+txtCodCli.getText();

        if(!txtNomCli.getText().equals(""))
            sqlFil+=" AND a2.tx_nom lIKE '"+txtNomCli.getText()+"'";


        if(!txtCodLoc.getText().equals(""))
            sqlFil+=" AND a.co_loc = "+txtCodLoc.getText()+" ";


        String strAux1="";
        if(chkFacPenAsiOc.isSelected()){
            strAux1="'N'";
        }

        if(chkFacAsiOc.isSelected()){
           if(strAux1.equals("")) strAux1="'S'"; else strAux1+=",'S'";
        }
        if(strAux1.equals("")) strAux1="'S','N'";


        sqlFil+=" AND a1.st_asidocrel in ("+strAux1+") ";


      }else{
         
            sqlFil+=" AND a.co_tipdoc in (  Select a.co_tipdoc from tbr_tipdocprg as b " +
	    " inner join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
            " where   b.co_emp="+objZafParSis.getCodigoEmpresa()+" and " +
            " b.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
            " b.co_mnu = "+objZafParSis.getCodigoMenu()+ " ) ";
      }

       if(objSelFec.isCheckBoxChecked() ){
         switch (objSelFec.getTipoSeleccion())
         {
                    case 0: //Búsqueda por rangos
                        sqlFil+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        sqlFil+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        sqlFil+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
        }}
   return sqlFil;
}



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBusCli;
    private javax.swing.JButton butBusLoc;
    private javax.swing.JButton butBusTipDoc;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkFacAsiOc;
    private javax.swing.JCheckBox chkFacPenAsiOc;
    private javax.swing.JCheckBox chkOcAsiFac;
    private javax.swing.JCheckBox chkOcPenAsiFac;
    private javax.swing.JLabel lblCli1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblSol1;
    private javax.swing.JLabel lblTipdoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optCri;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panFilFec;
    private javax.swing.JPanel panFilTbl;
    private javax.swing.JPanel panFildat;
    private javax.swing.JPanel panGenTabGen;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JRadioButton radTotDoc;
    private javax.swing.JScrollPane scrTblSegSolDev;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomLoc;
    // End of variables declaration//GEN-END:variables

}
