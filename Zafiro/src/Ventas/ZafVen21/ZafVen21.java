/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafVen21.java  
 *
 * Created on Sep 20, 2011, 11:00:03 AM
 */

package Ventas.ZafVen21;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

/**   
 *
 * @author jayapata
 */
public class ZafVen21 extends javax.swing.JInternalFrame {
  ZafParSis objParSis;
  ZafUtil objUti;
  private ZafSelFec objSelFec;
  private ZafTblMod objTblMod;
  private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
  Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
  private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButDG1;
  private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGenDG1;
  ArrayList arlColHidGui=new ArrayList();
  ArrayList arlColHid=new ArrayList();
  ZafVenCon objVenConCLi;
  ZafVenCon objVenConVen;

  String strVersion=" v0.10 ";

   String strCodCli="";
   String strDesCli="";
   String strCodVen="";
   String strDesVen="";
   private String strCodLoc, strNomLoc;                        //Contenido del campo al obtener el foco.
   private String strSQL="";
    final int INT_TBL_LIN=0;                        //Línea
    final int INT_TBL_CODEMP =1;                    //Código Empresa
    final int INT_TBL_CODLOC =2;                    //Código Local
    final int INT_TBL_CODTIPDOC =3;                 //Código Tipo Documento
    final int INT_TBL_DESCORTIPDOC =4;                 //Descripcion Corta Tipo Documento
    final int INT_TBL_DESCTIDOC =5;                 //Descripcion Tipo Documento
    final int INT_TBL_CODDOC =6;                    //Codigo Documento
    final int INT_TBL_NUMDOC=7;                     //Numero Documento
    final int INT_TBL_FECDOC=8;                     //Fecha Documento
    final int INT_TBL_CODCLI=9;                     //Codigo Cliente
    final int INT_TBL_NOMCLI=10;                     //Nombre Cliente
    final int INT_TBL_VALDOC=11;                    //Valor Documento
    final int INT_TBL_BUTFAC =12;                   // Muestra  Factura
    final int INT_TBL_CHKCXC =13;                   // SI NECESITA CXC PARA IMP
    final int INT_TBL_CHKCRE =14;                   // SI CXC SE HICE EL PAGO
    final int INT_TBL_NUMOD =15;                    // Numero Orden de Despacho
    final int INT_TBL_FECOD =16;                    // Fecha Orden de Despacho
    final int INT_TBL_FECULTMODOD =17;               // Fecha ultima modificacion Orden de Despacho
    final int INT_TBL_PENCONF=18;                   // Pendiente Confirmar
    final int INT_TBL_PARCONF=19;                   //Parcial Confirmar
    final int INT_TBL_TOTCONF=20;                   //Total Confirmar
    final int INT_TBL_FALSTK=21;                    //Fal Stk Confirmar
    final int INT_TBL_BUTITMFAL=22;                 // Boton items faltantes
    final int INT_TBL_CODEMPGUIREM=23;                 // CODIGO EMPRESA GUIA DE REMISION
    final int INT_TBL_CODLOCGUIREM=24;                 // CODIGO LOCAL GUIA DE REMISION
    final int INT_TBL_CODTIPDOCGUIREM=25;                 // CODIGO TIPO DE DOCUMENTO GUIA DE REMISION
    final int INT_TBL_DESCORTIPDOCGUIREM=26;                 // DESCRIPCION CORTA TIPO DE DOCUMNETO GUIA DE REMISION 
    final int INT_TBL_DESCTIDOCGUIREM=27;                // DESCRIPCION LARGA TIPO DOCUMENTO GUIA DE REMISION
    final int INT_TBL_CODDOCGUIREM=28;                 // CODIGO DOCUMENTO GUIA DE REMISINO
    final int INT_TBL_NUMDOCGUIREM=29;                 // NUMERO DOCUMENTO GUIA DE REMISION
    final int INT_TBL_FECDOCGUIREM=30;                 // FECHA DOCUMENTO GUIA DE REMISION
    final int INT_TBL_FECINGGUIREM=31;                 // FECHA INGRESO GUIA DE REMISION
    final int INT_TBL_TIEMPO=32;                 // TIEMPO TRANSCURRIDO ENTRE LA FECHA DE ULTIMA ODIFICACION DE OD Y LA FECHA DE INGRESO DE LA GUIA DE REMISION
    final int INT_TBL_BUTGUIA=33;                   // Boton guias remision
    

    private Vector vecDat, vecCab, vecReg;
    private ZafVenCon vcoLoc;                                   //Ventana de consulta.
    private ZafThreadGUI objThrGUI;


    /** Creates new form ZafVen21 */
    public ZafVen21(ZafParSis objZafParsis) {
       try{
        this.objParSis = (Librerias.ZafParSis.ZafParSis) objZafParsis.clone();
        objUti = new ZafUtil();
        objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        objTblCelEdiButGenDG1=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();

        objTblCelRenButDG1=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();

        initComponents();

            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(false);
            panFilCab.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);

            //*****************************************************************************
            Librerias.ZafDate.ZafDatePicker txtFecDoc;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setHoy();
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
            int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if(fecDoc!=null){
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(java.util.Calendar.DATE, -30 );

            dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");

            objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );

            //*******************************************************************************
            

        this.setTitle(objParSis.getNombreMenu()+" "+ strVersion );
        lblTit.setText( objParSis.getNombreMenu() );
       }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }

    }





public void Configura_ventana_consulta(){

  configurarFrm();
  configurarVenConClientes();
  configurarVenConVendedor();
  configurarVenConLoc();
}

    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {

            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(8);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN,"");
            vecCab.add(INT_TBL_CODEMP,"Cod.Emp.");
            vecCab.add(INT_TBL_CODLOC,"Cod.Loc.");
            vecCab.add(INT_TBL_CODTIPDOC,"Cod.Tip.Doc.");
            vecCab.add(INT_TBL_DESCORTIPDOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DESCTIDOC,"Tipo de documento");
            vecCab.add(INT_TBL_CODDOC,"Cod.Doc.");
            vecCab.add(INT_TBL_NUMDOC,"Num.Doc.");
            vecCab.add(INT_TBL_FECDOC,"Fec.Doc.");
            vecCab.add(INT_TBL_CODCLI,"Cod.Cli.");
            vecCab.add(INT_TBL_NOMCLI,"Nom.Cli.");
            vecCab.add(INT_TBL_VALDOC,"Val.Doc.");
            vecCab.add(INT_TBL_BUTFAC,"..");
            vecCab.add(INT_TBL_CHKCXC,"Nec.Cob.");
            vecCab.add(INT_TBL_CHKCRE,"Cob.Rea.");
            vecCab.add(INT_TBL_NUMOD,"Num.Ord.Des.");
            vecCab.add(INT_TBL_FECOD,"Fec.Ord.Des.");
            vecCab.add(INT_TBL_FECULTMODOD,"Fec.Ult.Mod.");
            vecCab.add(INT_TBL_PENCONF,"Pendiente");
            vecCab.add(INT_TBL_PARCONF,"Parcial");
            vecCab.add(INT_TBL_TOTCONF,"Total");
            vecCab.add(INT_TBL_FALSTK,"Fal.Stk");
            vecCab.add(INT_TBL_BUTITMFAL,"Can.Pen.");
            vecCab.add(INT_TBL_CODEMPGUIREM,"Cód.Emp.");
            vecCab.add(INT_TBL_CODLOCGUIREM,"Cód.Loc.");
            vecCab.add(INT_TBL_CODTIPDOCGUIREM,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DESCORTIPDOCGUIREM,"Tip.Doc.");
            vecCab.add(INT_TBL_DESCTIDOCGUIREM,"Tipo de documento");
            vecCab.add(INT_TBL_CODDOCGUIREM,"Cód.Doc.");
            vecCab.add(INT_TBL_NUMDOCGUIREM,"Núm.Doc.");
            vecCab.add(INT_TBL_FECDOCGUIREM,"Fec.Doc.");
            vecCab.add(INT_TBL_FECINGGUIREM,"Fec.Ing.");
            vecCab.add(INT_TBL_TIEMPO,"Tiempo");
            vecCab.add(INT_TBL_BUTGUIA,"..");
            



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
            
            
            objTblMod.setColumnDataType(INT_TBL_VALDOC, objTblMod.INT_COL_DBL, new Integer(0), null);


            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODEMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODTIPDOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DESCORTIPDOC).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DESCTIDOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CODDOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_VALDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BUTFAC).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CHKCXC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CHKCRE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NUMOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FECOD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FECULTMODOD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_PENCONF).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PARCONF).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_TOTCONF).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_FALSTK).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_BUTGUIA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODEMPGUIREM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODLOCGUIREM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODTIPDOCGUIREM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DESCORTIPDOCGUIREM).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DESCTIDOCGUIREM).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CODDOCGUIREM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NUMDOCGUIREM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FECDOCGUIREM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_FECINGGUIREM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_TIEMPO).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_BUTITMFAL).setPreferredWidth(50);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
               vecAux.add("" + INT_TBL_BUTFAC);
               vecAux.add("" + INT_TBL_BUTGUIA);
               vecAux.add("" + INT_TBL_BUTITMFAL);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

            Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_VALDOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;



	    objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKCXC).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_CHKCRE).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_PENCONF).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_PARCONF).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_TOTCONF).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_FALSTK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;

            tcmAux.getColumn(INT_TBL_CHKCXC).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_CHKCRE).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_PENCONF).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_PARCONF).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_TOTCONF).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_FALSTK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {


                }
	         });
            objTblCelRenChk=null;

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            ZafMouMotAda objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            new ZafTblBus(tblDat);
            //Configurar JTable: Renderizar celdas.

            //Libero los objetos auxiliares.
            new ZafTblOrd(tblDat);

             
               arlColHid.add(""+INT_TBL_CODEMP);
               arlColHid.add(""+INT_TBL_CODTIPDOC);
               arlColHid.add(""+INT_TBL_CODDOC);
               arlColHid.add(""+INT_TBL_CODEMPGUIREM);
               arlColHid.add(""+INT_TBL_CODLOCGUIREM);
               arlColHid.add(""+INT_TBL_CODTIPDOCGUIREM);
               arlColHid.add(""+INT_TBL_CODDOCGUIREM);
               arlColHid.add(""+INT_TBL_CODDOC);
               
               arlColHid.add(""+INT_TBL_NUMDOCGUIREM);
               arlColHid.add(""+INT_TBL_DESCORTIPDOCGUIREM);
               arlColHid.add(""+INT_TBL_DESCTIDOCGUIREM);
               arlColHid.add(""+INT_TBL_FECDOCGUIREM);
               arlColHid.add(""+INT_TBL_FECINGGUIREM);
               arlColHid.add(""+INT_TBL_TIEMPO);
              
              objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
               arlColHidGui.add(""+INT_TBL_CODEMP);
               arlColHidGui.add(""+INT_TBL_CODTIPDOC);
               arlColHidGui.add(""+INT_TBL_CODDOC);
               arlColHidGui.add(""+INT_TBL_CODEMPGUIREM);
               arlColHidGui.add(""+INT_TBL_CODLOCGUIREM);
               arlColHidGui.add(""+INT_TBL_CODTIPDOCGUIREM);
               arlColHidGui.add(""+INT_TBL_CODDOCGUIREM);
               arlColHidGui.add(""+INT_TBL_CODDOC);
               objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
             // arlColHid=null;
              //arlColHidGui=null;





            
            tcmAux.getColumn(INT_TBL_BUTFAC).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUTGUIA).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUTITMFAL).setCellRenderer(objTblCelRenButDG1);

            objTblCelRenButDG1.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    switch (objTblCelRenButDG1.getColumnRender())
                    {

                       case INT_TBL_BUTFAC:
                               objTblCelRenButDG1.setText("...");
                       break;

                        case INT_TBL_BUTGUIA:
                            if(objTblMod.getValueAt(objTblCelRenButDG1.getRowRender(), INT_TBL_PENCONF).toString().equals("true"))
                                objTblCelRenButDG1.setText("");
                            else
                             objTblCelRenButDG1.setText("...");

                        break;

                        case INT_TBL_BUTITMFAL:
                            if( (objTblMod.getValueAt(objTblCelRenButDG1.getRowRender(), INT_TBL_PENCONF).toString().equals("true")) || (objTblMod.getValueAt(objTblCelRenButDG1.getRowRender(), INT_TBL_TOTCONF).toString().equals("true")) )
                                objTblCelRenButDG1.setText("");
                            else
                             objTblCelRenButDG1.setText("...");

                        break;
                    }
                }
            });

             //Configurar JTable: Editor de celdas.
            tcmAux.getColumn(INT_TBL_BUTFAC).setCellEditor(objTblCelEdiButGenDG1);
            tcmAux.getColumn(INT_TBL_BUTGUIA).setCellEditor(objTblCelEdiButGenDG1);
            tcmAux.getColumn(INT_TBL_BUTITMFAL).setCellEditor(objTblCelEdiButGenDG1);

            objTblCelEdiButGenDG1.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                          case INT_TBL_BUTFAC:

                         break;

                         case INT_TBL_BUTGUIA:
                              if(objTblMod.getValueAt(intFilSel, INT_TBL_PENCONF).toString().equals("true"))
                                 objTblCelEdiButGenDG1.setCancelarEdicion(true);

                         break;

                          case INT_TBL_BUTITMFAL:
                              if( (objTblMod.getValueAt(intFilSel, INT_TBL_PENCONF).toString().equals("true")) || (objTblMod.getValueAt(intFilSel, INT_TBL_TOTCONF).toString().equals("true")) ){
                                 objTblCelEdiButGenDG1.setCancelarEdicion(true);
                               }

                         break;

                       }
                     }
                    }
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                          case INT_TBL_BUTFAC:

                             llamarVenFac( objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString()
                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString()
                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOC).toString()
                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
                                 );

                           break;


                           case INT_TBL_BUTGUIA:
                                if (chkMosGuiRem.isSelected()) {
                                    if(objTblMod.getValueAt(intFilSel, INT_TBL_PENCONF).toString().equals("false")){
                                llamarPantGuia(objTblMod.getValueAt(intFilSel, INT_TBL_CODEMPGUIREM).toString(),
                                               objTblMod.getValueAt(intFilSel, INT_TBL_CODLOCGUIREM).toString(), 
                                               objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOCGUIREM).toString(), 
                                               objTblMod.getValueAt(intFilSel, INT_TBL_NUMDOCGUIREM).toString());
                                    }
                                }else{
                                    if(objTblMod.getValueAt(intFilSel, INT_TBL_PENCONF).toString().equals("false")){
                                                           mostrarGuiasRemision( objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString()
                                                               ,objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString()
                                                               ,objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOC).toString()
                                                               ,objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
                                                            );
                                                          }
                                }
                             
                           break;


                            case INT_TBL_BUTITMFAL:

                              if( (objTblMod.getValueAt(intFilSel, INT_TBL_PENCONF).toString().equals("false")) && (objTblMod.getValueAt(intFilSel, INT_TBL_TOTCONF).toString().equals("false")) ){
                                mostrarItmPenConfEgr( objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString()
                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString()
                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOC).toString()
                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
                                 );
                               }
                           break;



                        }
                }}
           });


   ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
     objTblHeaGrp.setHeight(16*2);
     ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("Paso 1: Facturas de venta  " );
     objTblHeaColGrpAmeSur.setHeight(16);
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODEMP));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODLOC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODTIPDOC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DESCORTIPDOC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DESCTIDOC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODDOC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NUMDOC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECDOC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODCLI));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NOMCLI));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_VALDOC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTFAC));
        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
       objTblHeaColGrpAmeSur=null;
     
     objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("Paso 2: CxC " );
     objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CHKCXC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CHKCRE));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
     objTblHeaColGrpAmeSur=null;


     objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("Paso 3: Ordenes de despacho " );
     objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NUMOD));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECOD));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECULTMODOD));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
     objTblHeaColGrpAmeSur=null;


     objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("Paso 4: Confirmaciones " );
     objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_PENCONF));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_PARCONF));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TOTCONF));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FALSTK));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTITMFAL));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
     objTblHeaColGrpAmeSur=null;


    objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("Paso 5: Guias de remisión " );
     objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODEMPGUIREM));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODLOCGUIREM));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODTIPDOCGUIREM));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DESCORTIPDOCGUIREM));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DESCTIDOCGUIREM));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODDOCGUIREM));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NUMDOCGUIREM));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECDOCGUIREM));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECINGGUIREM));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TIEMPO));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTGUIA));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
     objTblHeaColGrpAmeSur=null;
   
     tcmAux=null;
      setEditable(true);

  }catch(Exception e){  blnRes=false;   objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}


public void setEditable(boolean editable) {
 if (editable==true)
    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
 else
    objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
}



private void llamarVenFac(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc){
   try{

       Ventas.ZafVen02.ZafVen02 obj1 = new Ventas.ZafVen02.ZafVen02( objParSis , strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, 14 );
       this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER );
       obj1.show();

    }catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);     }
}



private void mostrarGuiasRemision(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){
  String strSql02="";
  try{
 
    strSql02="select a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, a5.tx_descor, a4.ne_numdoc, a4.fe_doc  from tbm_detmovinv as  a "
    + " inner join tbm_detguirem as a1 on (a1.co_emprel=a.co_emp and a1.co_locrel=a.co_loc and a1.co_tipdocrel=a.co_tipdoc and a1.co_docrel=a.co_doc and a1.co_regrel=a.co_reg ) "
    + " inner join tbr_detguirem as a2 on (a2.co_emp=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) "
    + " inner join tbm_detguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc and a3.co_reg=a2.co_reg ) "
    + " inner join tbm_cabguirem as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc  ) "
    + " inner join tbm_cabtipdoc as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_loc and a5.co_tipdoc=a4.co_tipdoc) "
    + " where a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" and a.co_tipdoc="+strCodTipDoc+" and a.co_doc="+strCodDoc+" and a4.st_reg='A' "
    + " group by a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, a5.tx_descor, a4.ne_numdoc, a4.fe_doc "
    + " order by  a4.ne_numdoc ";


     ZafVen21_01 obj1 = new  ZafVen21_01(objParSis, this, strSql02 );
     this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER );
     obj1.show();


  }catch(Exception e) {   objUti.mostrarMsgErr_F1(this,e);  }
}



private void mostrarItmPenConfEgr(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc )
{
   String strSql02="";
   try
   {
        strSql02 =" SELECT * FROM ";
        strSql02+=" ( ";
        strSql02+="     SELECT x.*, ";
        strSql02+="            CASE WHEN (x.st_meregrfisbod = 'N' AND nd_CanTotGui>0) THEN (nd_Can - nd_canTotGui) ELSE CASE WHEN (x.st_meregrfisbod = 'A' ) THEN nd_canPen ";
        strSql02+="            ELSE (nd_Can - (nd_CanCon + nd_canNunRec + nd_canTra)) END END as nd_canTotPen ";
        strSql02+="     FROM  ";
        strSql02+="     (     ";
        strSql02+="         SELECT a.st_meregrfisbod, a.tx_codalt, b.tx_codalt2, a.tx_nomitm,   ";              
        strSql02+="                CASE WHEN c.nd_Can IS NULL THEN 0 ELSE abs(c.nd_Can) END as nd_Can, ";             
        strSql02+="                CASE WHEN a.nd_CanCon = 0 THEN abs(c.nd_CanCon) ELSE abs(a.nd_CanCon) END as nd_CanCon,  ";             
        strSql02+="                CASE WHEN c.nd_canNunRec = 0 THEN abs(a.nd_CanNunRec) ELSE abs(c.nd_canNunRec) END as nd_canNunRec,  ";           
        strSql02+="                CASE WHEN c.nd_canTra IS NULL THEN 0 ELSE abs(c.nd_canTra) END as nd_canTra, ";              
        strSql02+="                CASE WHEN c.nd_canPen IS NULL THEN 0 ELSE abs(c.nd_canPen) END as nd_canPen, ";            
        strSql02+="                CASE WHEN a.nd_canTotGuiSec IS NULL THEN 0 ELSE abs(a.nd_canTotGuiSec) END as nd_canTotGui  ";
        strSql02+="         FROM tbm_detguirem as a  ";     
        strSql02+="         INNER JOIN tbm_inv as b on (b.co_emp=a.co_emp and b.co_itm=a.co_itm) ";       
        strSql02+="         INNER JOIN tbm_detmovinv as c on (a.co_emprel=c.co_emp and a.co_locrel=c.co_loc and a.co_tipdocrel=c.co_tipdoc and a.co_docrel=c.co_doc and a.co_regrel=c.co_reg) ";      
        strSql02+="         WHERE c.co_emp="+strCodEmp;
        strSql02+="         AND c.co_loc="+strCodLoc;   
        strSql02+="         AND c.co_tipdoc="+strCodTipDoc;
        strSql02+="         AND c.co_doc="+strCodDoc;
        strSql02+="     ) as x ";
        strSql02+=" ) as b ";
        strSql02+=" WHERE b.nd_canTotPen>0 ";

        ZafVen21_02 obj1 = new  ZafVen21_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, this, strSql02 );
        obj1.show();
  }
  catch(Exception e) {   objUti.mostrarMsgErr_F1(this,e);  }
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
        //strSQL="SELECT a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide FROM tbm_cli as a " +
        //" WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.st_cli='S' order by a.tx_nom ";

        strSQL="SELECT a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide FROM tbr_cliloc as a1 " +
        " INNER JOIN tbm_cli as a ON(a.co_emp=a1.co_emp AND a.co_cli=a1.co_cli) " +
        " WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal()+" " +
        " AND  a.st_reg NOT IN('I','T')  order by a.tx_nom ";

        objVenConCLi=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
   return blnRes;
}

private boolean configurarVenConVendedor() {
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
        " where b.co_emp="+objParSis.getCodigoEmpresa()+" and b.st_ven='S' and a.st_reg not in ('I')  order by a.tx_nom";

        objVenConVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }catch (Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
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
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panFilCab = new javax.swing.JPanel();
        optFil = new javax.swing.JRadioButton();
        optTod = new javax.swing.JRadioButton();
        lblCli = new javax.swing.JLabel();
        lblCli1 = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        butcli = new javax.swing.JButton();
        chkMosGuiRem = new javax.swing.JCheckBox();
        chkConfParG = new javax.swing.JCheckBox();
        chkConfPenG = new javax.swing.JCheckBox();
        chkConfTotG1 = new javax.swing.JCheckBox();
        lblLoc = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        panRep = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panSur = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
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

        lblTit.setText("jLabel1");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        panFilCab.setPreferredSize(new java.awt.Dimension(0, 400));
        panFilCab.setLayout(null);

        buttonGroup1.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        panFilCab.add(optFil);
        optFil.setBounds(10, 100, 400, 20);

        buttonGroup1.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
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
        panFilCab.add(optTod);
        optTod.setBounds(10, 80, 400, 20);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCli.setText("Cliente:");
        panFilCab.add(lblCli);
        lblCli.setBounds(30, 140, 100, 20);

        lblCli1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCli1.setText("Vendedor :");
        panFilCab.add(lblCli1);
        lblCli1.setBounds(30, 160, 100, 20);

        txtCodVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVenFocusLost(evt);
            }
        });
        txtCodVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVenActionPerformed(evt);
            }
        });
        panFilCab.add(txtCodVen);
        txtCodVen.setBounds(100, 160, 50, 20);

        txtCodCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        panFilCab.add(txtCodCli);
        txtCodCli.setBounds(100, 140, 50, 20);

        txtNomCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        panFilCab.add(txtNomCli);
        txtNomCli.setBounds(150, 140, 280, 20);

        txtNomVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNomVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVenFocusLost(evt);
            }
        });
        txtNomVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVenActionPerformed(evt);
            }
        });
        panFilCab.add(txtNomVen);
        txtNomVen.setBounds(150, 160, 280, 20);

        butVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFilCab.add(butVen);
        butVen.setBounds(430, 160, 20, 20);

        butcli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butcli.setText("...");
        butcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butcliActionPerformed(evt);
            }
        });
        panFilCab.add(butcli);
        butcli.setBounds(430, 140, 20, 20);

        chkMosGuiRem.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkMosGuiRem.setText("Mostrar las guías de remisión");
        chkMosGuiRem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosGuiRemActionPerformed(evt);
            }
        });
        panFilCab.add(chkMosGuiRem);
        chkMosGuiRem.setBounds(10, 240, 450, 20);

        chkConfParG.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkConfParG.setSelected(true);
        chkConfParG.setText("Mostrar los documentos que están confirmados parcialmente");
        chkConfParG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkConfParGActionPerformed(evt);
            }
        });
        panFilCab.add(chkConfParG);
        chkConfParG.setBounds(30, 200, 440, 20);

        chkConfPenG.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkConfPenG.setSelected(true);
        chkConfPenG.setText("Mostrar los documentos que están pendientes de confirmar.");
        chkConfPenG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkConfPenGActionPerformed(evt);
            }
        });
        panFilCab.add(chkConfPenG);
        chkConfPenG.setBounds(30, 180, 460, 20);

        chkConfTotG1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkConfTotG1.setText("Mostrar los documetos que están confirmados totalmente");
        chkConfTotG1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkConfTotG1ActionPerformed(evt);
            }
        });
        panFilCab.add(chkConfTotG1);
        chkConfTotG1.setBounds(30, 220, 450, 20);

        lblLoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblLoc.setText("Local:");
        lblLoc.setToolTipText("Local");
        panFilCab.add(lblLoc);
        lblLoc.setBounds(30, 120, 50, 20);

        txtCodLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLocFocusLost(evt);
            }
        });
        txtCodLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLocActionPerformed(evt);
            }
        });
        panFilCab.add(txtCodLoc);
        txtCodLoc.setBounds(100, 120, 50, 20);

        txtNomLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomLocFocusLost(evt);
            }
        });
        txtNomLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomLocActionPerformed(evt);
            }
        });
        panFilCab.add(txtNomLoc);
        txtNomLoc.setBounds(150, 120, 280, 20);

        butLoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butLoc.setText("...");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFilCab.add(butLoc);
        butLoc.setBounds(430, 120, 20, 20);

        tabFrm.addTab("General", panFilCab);

        panRep.setLayout(new java.awt.BorderLayout());

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
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnDat.setViewportView(tblDat);

        panRep.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRep);

        panCen.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panSur.setLayout(new java.awt.BorderLayout());

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

        panSur.add(panBot, java.awt.BorderLayout.CENTER);

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

        panSur.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panSur, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
       // if (verificaCheck()) {
            if (butCon.getText().equals("Consultar")) {
                if (objThrGUI == null) {
                    objThrGUI = new ZafThreadGUI();
                    objThrGUI.start();
                }
            }            
       // } else {
          //  mostrarMsgInf("Debe seleccionar al menos un estado de los documentos.");
        ///    chkConfPenG.setSelected(true);
     //   }
}//GEN-LAST:event_butConActionPerformed
//private boolean verificaCheck(){
//    if (!chkConfPenG.isSelected() && !chkConfParG.isSelected() && !chkConfTotG1.isSelected()) {
//        return false;
//    }
//    return true;
//}


private class ZafThreadGUI extends Thread{
 public void run(){

  lblMsgSis.setText("Obteniendo datos...");
  pgrSis.setIndeterminate(true);
     if (chkMosGuiRem.isSelected()) {
          objTblMod.setSystemHiddenColumns(arlColHidGui, tblDat);
     }else{
         objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
     }
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


  pgrSis.setValue(0);
  pgrSis.setIndeterminate(false);


}
}

private boolean cargarDetReg(String strFil){
 boolean blnRes=true;
 java.sql.Connection conn;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="", strEstConf="", strSqlAux="", strSqlTipDoc="", strSqlAux01="";
 try{
    butCon.setText("Detener");
    conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
    if (conn!=null){
        stmLoc=conn.createStatement();
                //Obtener la condición.

        if(chkConfPenG.isSelected()) { if(strEstConf.equals("")) strEstConf+="'P'"; else strEstConf+=",'P'"; }
        if(chkConfParG.isSelected()) { if(strEstConf.equals("")) strEstConf+="'E'"; else strEstConf+=",'E'"; }
        if(chkConfTotG1.isSelected()) { if(strEstConf.equals("")) strEstConf+="'C'"; else strEstConf+=",'C'"; }

        if(!strEstConf.equals(""))
            strSqlAux = " WHERE st_coninv IN ("+strEstConf+") ";

       strSqlTipDoc="Select a.co_tipdoc from tbr_tipdocprg as b " +
                    " inner join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
                    " where   b.co_emp="+objParSis.getCodigoEmpresa()+" and " +
                    " b.co_loc = " + objParSis.getCodigoLocal()   + " and " +
                    " b.co_mnu = "+objParSis.getCodigoMenu();

        if (!(objParSis.getCodigoUsuario() == 1)) {
            if (txtCodLoc.getText().length() > 0) {
                strSqlAux01 = " and a.co_loc=" + txtCodLoc.getText() + " ";
            } else {
                strSqlAux01 = " and a.co_loc=" + objParSis.getCodigoLocal() + " ";
            }
        } else {
            if (txtCodLoc.getText().length() > 0) {
                strSqlAux01 = " and a.co_loc=" + txtCodLoc.getText() + " ";
            }
        }
               
        if (chkMosGuiRem.isSelected()) 
        {
            strSql  =" select * "
                    +"      ,case when st_aut is null then false else true end as  neccob "
                    +"      ,case when st_aut is not null then  case when  ne_numorddes > 0 then true else "
                    +"      case when numguia > 0 then true else false end  end  else false end as  cobrea "
                    +" from ( "
                    +"      select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a.ne_numdoc, a.co_cli, a.tx_nomcli, abs(a.nd_tot) as nd_tot, a3.tx_descor, a3.tx_deslar\n" 
                    +"             ,case when a2.ne_numorddes is null then 0 else a2.ne_numorddes end as ne_numorddes, a2.fe_orddes, a2.fe_ultmod\n" 
                    //+"           , a2.st_coninv\n" +
                    +"             , case when a2.st_coninv in ('P','E') then case when  a.st_conInv  is null then 'P' else  a.st_conInv end else a2.st_coninv end as st_coninv\n" 
                    +"             , a2.st_aut, a2.ne_numdoc as numguia, a2.co_emp as empgui,a2.co_loc as locgui, a2.co_doc as docgui,a2.fe_doc as fedocgui\n" 
                    +"             ,(select tx_descor from tbm_cabtipdoc where co_emp=a2.co_emp and co_loc=a2.co_loc and co_tipdoc=a2.co_tipdoc) as descorgui\n" 
                    +"             ,(select tx_deslar from tbm_cabtipdoc where co_emp=a2.co_emp and co_loc=a2.co_loc and co_tipdoc=a2.co_tipdoc) as deslargui\n" 
                    +"             ,a5.co_tipdoc as tipdocgui    ,a5.co_doc as numdocgui "
                    +"             ,(select fe_ing from tbm_cabguirem where co_emp=a5.co_emp and co_loc=a5.co_loc and co_doc=a5.co_doc and co_tipdoc=231 ) as feinggui "
                    +"       from tbm_cabmovinv as a\n" 
                    +"       inner join tbm_detguirem as a1 on (a1.co_emprel=a.co_emp and a1.co_locrel=a.co_loc and a1.co_tipdocrel=a.co_tipdoc and a1.co_docrel=a.co_doc)\n" 
                    +"       inner join tbm_cabguirem as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc )  \n" 
                    +"       inner join tbr_cabguirem as a5 on (a5.co_emp=a2.co_emp and a5.co_locrel=a2.co_loc and a5.co_tipdocrel=a2.co_tipdoc and a5.co_docrel=a2.co_doc  )  \n"  //and a5.co_tipdoc=231
                    +"       inner join tbm_cabtipdoc as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc ) "
                    +"       where a.co_emp="+objParSis.getCodigoEmpresa()+" "+strSqlAux01+" and "
                    +"       a.co_tipdoc in ( "+strSqlTipDoc+" ) and a.st_reg='A' and a2.st_reg='A'  "+strFil+" "
                    +"       and a.ne_numdoc > 0   and a2.ne_numdoc = 0   "
                    +"       group by a2.ne_numdoc, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a3.tx_descor, a3.tx_deslar, a.fe_doc, a.co_cli, a.ne_numdoc, a5.co_doc,\n" 
                    +"	              a.tx_nomcli, a.nd_tot,a2.co_emp,a2.co_loc, a2.co_tipdoc,a2.co_doc\n" 
                    +"                ,a2.ne_numorddes, a2.fe_orddes,a2.fe_ultmod, a2.st_coninv, a.st_conInv, a2.st_aut,a2.fe_doc, a2.fe_ing,a5.co_tipdoc,a5.co_emp, a5.co_loc \n" 
                    + " ) as x  "+strSqlAux+"  ORDER BY ne_numdoc ";

        }
        else
        {
            strSql  ="select * "
                    +" ,case when st_aut is null then false else true end as  neccob "
                    +" ,case when st_aut is not null then  case when  ne_numorddes > 0 then true else "
                    +" case when numguia > 0 then true else false end  end  else false end as  cobrea "
                    +" from ( "
                    +" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a3.tx_descor, a3.tx_deslar, a.fe_doc, a.ne_numdoc, a.co_cli, a.tx_nomcli, abs(a.nd_tot) as nd_tot  "
                    +"       , case when a2.ne_numorddes is null then 0 else a2.ne_numorddes end as ne_numorddes, a2.fe_orddes,a2.fe_ultmod "
                    +"       , case when a2.st_coninv in ('P','E') then case when  a.st_conInv  is null then 'P' else  a.st_conInv end else a2.st_coninv end as st_coninv "
                    +"       , a2.st_aut, a2.ne_numdoc as numguia, a2.co_emp as empgui,a2.co_loc as locgui,  (select tx_descor from tbm_cabtipdoc where co_emp=a2.co_emp and co_loc=a2.co_loc and co_tipdoc=a2.co_tipdoc) as descorgui,(select tx_deslar from tbm_cabtipdoc where co_emp=a2.co_emp and co_loc=a2.co_loc and co_tipdoc=a2.co_tipdoc) as deslargui, a2.co_doc as docgui, a2.ne_numdoc as numdocgui,a2.fe_doc as fedocgui, a2.fe_ing as feinggui,(a2.fe_ing - a2.fe_ultmod) as tiempo, a2.co_tipdoc as tipdocgui  "
                    +" from tbm_cabmovinv as a "
                    +" inner join tbm_detguirem as a1 on (a1.co_emprel=a.co_emp and a1.co_locrel=a.co_loc and a1.co_tipdocrel=a.co_tipdoc and a1.co_docrel=a.co_doc ) "
                    +" inner join tbm_cabguirem as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) "
                    +" inner join tbm_cabtipdoc as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc ) "
                    +" where a.co_emp="+objParSis.getCodigoEmpresa()+" "+strSqlAux01+" and "
                    +" a.co_tipdoc in ( "+strSqlTipDoc+" ) and a.st_reg='A' and a2.st_reg='A'  "+strFil+" "
                    +" and a.ne_numdoc > 0   and a2.ne_numdoc = 0   "
                    +" group by a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a3.tx_descor, a3.tx_deslar, a.fe_doc, a.ne_numdoc, a.co_cli, a.tx_nomcli, a.nd_tot,a2.co_emp,a2.co_loc, a2.co_tipdoc,a2.co_doc, a2.ne_numdoc "
                    +" , a2.ne_numorddes, a2.fe_orddes,a2.fe_ultmod, a2.st_coninv, a.st_conInv, a2.st_aut, a2.ne_numdoc,a2.fe_doc, a2.fe_ing, a2.co_tipdoc "
                    +" ) as x  "+strSqlAux+"  ORDER BY ne_numdoc ";
        }
       
      System.out.println("ZafVen21.cargarDetReg: "+ strSql );

       rstLoc=stmLoc.executeQuery(strSql);
       vecDat.clear();
       while (rstLoc.next()){

            vecReg=new Vector();
            vecReg.add(INT_TBL_LIN,"");
            vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp"));
            vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc"));
            vecReg.add(INT_TBL_CODTIPDOC, rstLoc.getString("co_tipdoc"));
            vecReg.add(INT_TBL_DESCORTIPDOC, rstLoc.getString("tx_descor"));
            vecReg.add(INT_TBL_DESCTIDOC,  rstLoc.getString("tx_deslar"));
            vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc"));
            vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc"));
            vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc"));
            vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli"));
            vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nomcli"));
            vecReg.add(INT_TBL_VALDOC, rstLoc.getString("nd_tot"));
            vecReg.add(INT_TBL_BUTFAC,"");
            vecReg.add(INT_TBL_CHKCXC, new Boolean(rstLoc.getBoolean("neccob")) );
            vecReg.add(INT_TBL_CHKCRE, new Boolean(rstLoc.getBoolean("cobrea")) );
            vecReg.add(INT_TBL_NUMOD, rstLoc.getString("ne_numorddes"));
            vecReg.add(INT_TBL_FECOD, rstLoc.getString("fe_orddes"));
            vecReg.add(INT_TBL_FECULTMODOD,rstLoc.getString("fe_ultmod"));
            vecReg.add(INT_TBL_PENCONF, new Boolean(rstLoc.getString("st_coninv").equals("P")?true:false) );
            vecReg.add(INT_TBL_PARCONF, new Boolean(rstLoc.getString("st_coninv").equals("E")?true:false) );
            vecReg.add(INT_TBL_TOTCONF, new Boolean(rstLoc.getString("st_coninv").equals("C")?true:false) );
            vecReg.add(INT_TBL_FALSTK, new Boolean(false) );
            vecReg.add(INT_TBL_BUTITMFAL,"");
            vecReg.add(INT_TBL_CODEMPGUIREM,rstLoc.getString("empgui"));
            vecReg.add(INT_TBL_CODLOCGUIREM,rstLoc.getString("locgui"));
            vecReg.add(INT_TBL_CODTIPDOCGUIREM,rstLoc.getString("tipdocgui"));
            vecReg.add(INT_TBL_DESCORTIPDOCGUIREM,rstLoc.getString("descorgui"));
            vecReg.add(INT_TBL_DESCTIDOCGUIREM,rstLoc.getString("deslargui"));
            vecReg.add(INT_TBL_CODDOCGUIREM,rstLoc.getString("docgui"));
            vecReg.add(INT_TBL_NUMDOCGUIREM,rstLoc.getString("numdocgui"));
            vecReg.add(INT_TBL_FECDOCGUIREM,rstLoc.getString("fedocgui"));
            vecReg.add(INT_TBL_FECINGGUIREM,rstLoc.getString("feinggui"));
            String str;
            if (rstLoc.getDate("fe_ultmod")!=null && rstLoc.getDate("feinggui")!=null) {
               str = retornaDiferenciaTiempo(rstLoc.getTimestamp("feinggui"),rstLoc.getTimestamp("fe_ultmod"));
            }else {
                str="";
            }
            
            vecReg.add(INT_TBL_TIEMPO,str);
            vecReg.add(INT_TBL_BUTGUIA,"");
            
            vecDat.add(vecReg);


    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
    conn.close();
    conn=null;
    //Asignar vectores al modelo.
    objTblMod.setData(vecDat);
    tblDat.setModel(objTblMod);
    vecDat.clear();


    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
    butCon.setText("Consultar");

}}catch (java.sql.SQLException e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
  catch (Exception e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
return blnRes;
}



private String sqlConFil(){
   String sqlFil="";

    if(optFil.isSelected()){

        if(!txtCodCli.getText().equals(""))
            sqlFil+=" AND a.co_cli="+txtCodCli.getText();

        if(!txtNomCli.getText().equals(""))
            sqlFil+=" AND a.tx_nomcli lIKE '"+txtNomCli.getText()+"'";

        if(!txtCodVen.getText().equals(""))
            sqlFil+=" AND a.co_com="+txtCodVen.getText();
    }

         switch (objSelFec.getTipoSeleccion())
         {
                    case 0: //Búsqueda por rangos
                        sqlFil+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        sqlFil+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        sqlFil+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
        }
      return sqlFil;
 }




    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
         ExiForm();
}//GEN-LAST:event_butCerActionPerformed

    private void chkConfPenGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkConfPenGActionPerformed
        // TODO add your handling code here:

        if(!chkConfPenG.isSelected())
            optFil.setSelected(true);
}//GEN-LAST:event_chkConfPenGActionPerformed

    private void chkConfParGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkConfParGActionPerformed
        // TODO add your handling code here:
        if(!chkConfParG.isSelected())
            optFil.setSelected(true);
}//GEN-LAST:event_chkConfParGActionPerformed

    private void chkMosGuiRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosGuiRemActionPerformed
        // TODO add your handling code here:

        if(!chkMosGuiRem.isSelected())
            optFil.setSelected(true);
}//GEN-LAST:event_chkMosGuiRemActionPerformed

    private void butcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butcliActionPerformed
        // TODO add your handling code here:
        objVenConCLi.setTitle("Listado de Clientes");
        objVenConCLi.setCampoBusqueda(1);
        objVenConCLi.show();
        if (objVenConCLi.getSelectedButton()==objVenConCLi.INT_BUT_ACE) {
            txtCodCli.setText(objVenConCLi.getValueAt(1));
            txtNomCli.setText(objVenConCLi.getValueAt(2));
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butcliActionPerformed

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        // TODO add your handling code here:
        objVenConVen.setTitle("Listado de Clientes");
        objVenConVen.setCampoBusqueda(1);
        objVenConVen.show();
        if (objVenConVen.getSelectedButton()==objVenConVen.INT_BUT_ACE) {
            txtCodVen.setText(objVenConVen.getValueAt(1));
            txtNomVen.setText(objVenConVen.getValueAt(2));
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butVenActionPerformed

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        // TODO add your handling code here:
        if (!txtNomVen.getText().equalsIgnoreCase(strDesVen)) {
            if(txtNomVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }else
                BuscarVendedor("a.tx_nom",txtNomVen.getText(),1);
        }else
            txtNomVen.setText(strDesVen);
}//GEN-LAST:event_txtNomVenFocusLost

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        // TODO add your handling code here:
        strDesVen=txtNomVen.getText();
        txtNomVen.selectAll();
}//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        // TODO add your handling code here:
        txtNomVen.transferFocus();
}//GEN-LAST:event_txtNomVenActionPerformed

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        // TODO add your handling code here:
        if (!txtNomCli.getText().equalsIgnoreCase(strDesCli)) {
            if (txtNomCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }else
                BuscarCliente("a.tx_nom",txtNomCli.getText(),1);
        }else
            txtNomCli.setText(strDesCli);
}//GEN-LAST:event_txtNomCliFocusLost

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        // TODO add your handling code here:
        strDesCli=txtNomCli.getText();
        txtNomCli.selectAll();
}//GEN-LAST:event_txtNomCliFocusGained

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

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        // TODO add your handling code here:
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
}//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        // TODO add your handling code here:
        txtCodCli.transferFocus();
}//GEN-LAST:event_txtCodCliActionPerformed

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        // TODO add your handling code here:
        if (!txtCodVen.getText().equalsIgnoreCase(strCodVen)) {
            if(txtCodVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }else
                BuscarVendedor("a.co_usr",txtCodVen.getText(),0);
        }else
            txtCodVen.setText(strCodVen);
}//GEN-LAST:event_txtCodVenFocusLost

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        // TODO add your handling code here:
        strCodVen=txtCodVen.getText();
        txtCodVen.selectAll();
}//GEN-LAST:event_txtCodVenFocusGained

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        // TODO add your handling code here:
        txtCodVen.transferFocus();
}//GEN-LAST:event_txtCodVenActionPerformed

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_optTodActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected()) {
         
            strCodCli="";  strDesCli="";
            strCodVen="";  strDesVen="";

            txtCodCli.setText("");
            txtNomCli.setText("");
            txtCodVen.setText("");
            txtNomVen.setText("");

            chkConfPenG.setSelected(true);
            chkConfParG.setSelected(true);
            chkMosGuiRem.setSelected(false);

        }
}//GEN-LAST:event_optTodItemStateChanged

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_optFilItemStateChanged

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        ExiForm();
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void chkConfTotG1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkConfTotG1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkConfTotG1ActionPerformed

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        strCodLoc=txtCodLoc.getText();
        txtCodLoc.selectAll();
    }//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc))
        {
            if (txtCodLoc.getText().equals(""))
            {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            }
            else
            {
                mostrarVenConLoc(1);
            }
        }
        else
        txtCodLoc.setText(strCodLoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodLocFocusLost

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        txtCodLoc.transferFocus();
    }//GEN-LAST:event_txtCodLocActionPerformed

    private void txtNomLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusGained
        strNomLoc=txtNomLoc.getText();
        txtNomLoc.selectAll();
    }//GEN-LAST:event_txtNomLocFocusGained

    private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomLoc.getText().equalsIgnoreCase(strNomLoc))
        {
            if (txtNomLoc.getText().equals(""))
            {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            }
            else
            {
                mostrarVenConLoc(2);
            }
        }
        else
        txtNomLoc.setText(strNomLoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomLocFocusLost

    private void txtNomLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLocActionPerformed
        txtNomLoc.transferFocus();
    }//GEN-LAST:event_txtNomLocActionPerformed

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        mostrarVenConLoc(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butLocActionPerformed

    private void ExiForm(){
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }



public void BuscarCliente(String campo,String strBusqueda,int tipo){
  objVenConCLi.setTitle("Listado de Clientes");
  if(objVenConCLi.buscar(campo, strBusqueda )) {
      txtCodCli.setText(objVenConCLi.getValueAt(1));
      txtNomCli.setText(objVenConCLi.getValueAt(2));
      optFil.setSelected(true);
  }else{
        objVenConCLi.setCampoBusqueda(tipo);
        objVenConCLi.cargarDatos();
        objVenConCLi.show();
        if (objVenConCLi.getSelectedButton()==objVenConCLi.INT_BUT_ACE) {
           txtCodCli.setText(objVenConCLi.getValueAt(1));
           txtNomCli.setText(objVenConCLi.getValueAt(2));
           optFil.setSelected(true);
        }else{
            txtCodCli.setText(strCodCli);
            txtNomCli.setText(strDesCli);
  }}}


public void BuscarVendedor(String campo,String strBusqueda,int tipo){
  objVenConVen.setTitle("Listado de Vendedores");
  if(objVenConVen.buscar(campo, strBusqueda )) {
      txtCodVen.setText(objVenConVen.getValueAt(1));
      txtNomVen.setText(objVenConVen.getValueAt(2));
      optFil.setSelected(true);
  }else{
        objVenConVen.setCampoBusqueda(tipo);
        objVenConVen.cargarDatos();
        objVenConVen.show();
        if (objVenConVen.getSelectedButton()==objVenConVen.INT_BUT_ACE) {
            txtCodVen.setText(objVenConVen.getValueAt(1));
            txtNomVen.setText(objVenConVen.getValueAt(2));
            optFil.setSelected(true);
        }else{
            txtCodVen.setText(strCodVen);
            txtNomVen.setText(strDesVen);
  }}}




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butVen;
    private javax.swing.JButton butcli;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkConfParG;
    private javax.swing.JCheckBox chkConfPenG;
    private javax.swing.JCheckBox chkConfTotG1;
    private javax.swing.JCheckBox chkMosGuiRem;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCli1;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panFilCab;
    private javax.swing.JPanel panRep;
    private javax.swing.JPanel panSur;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomLoc;
    private javax.swing.JTextField txtNomVen;
    // End of variables declaration//GEN-END:variables


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
                case INT_TBL_CODEMP:
                    strMsg="Código de empresa";
                    break;
                case INT_TBL_CODLOC:
                    strMsg="Código de local ";
                    break;
                case INT_TBL_DESCTIDOC:
                    strMsg="Tipo de Documento ";
                    break;
                case INT_TBL_NUMDOC:
                    strMsg="Número de Documento ";
                    break;
                case INT_TBL_FECDOC:
                    strMsg="Fecha de Documento ";
                    break;
                case INT_TBL_CODCLI:
                    strMsg="Código de Cliente ";
                    break;
                 case INT_TBL_NOMCLI:
                    strMsg="Nombre de Cliente ";
                    break;

               case INT_TBL_VALDOC:
                    strMsg="Valor del Documento ";
                    break;
                case INT_TBL_BUTFAC:
                    strMsg="Muesta la factura de venta ";
                    break;
                 case INT_TBL_CHKCXC:
                    strMsg="¿Es necesario realizar cobro para librerar la orden de despacho. ? ";
                    break;

                case INT_TBL_CHKCRE:
                    strMsg="¿Cobro realizado.? ";
                    break;
                 case INT_TBL_NUMOD:
                    strMsg="Número de orden de despacho";
                    break;

               case INT_TBL_FECOD:
                    strMsg="Fecha de orden de despacho. ";
                    break;
                 case INT_TBL_PENCONF:
                    strMsg="Confirmacion Pendiente.? ";
                    break;

                case INT_TBL_PARCONF:
                    strMsg="Confirmacion parcial ";
                    break;
                 case INT_TBL_TOTCONF:
                    strMsg="Confirmacion total ";
                    break;

                  case INT_TBL_FALSTK:
                    strMsg="¿Hay faltante de stock?";
                    break;

                 case INT_TBL_BUTGUIA:
                    strMsg="Muesta la(s) guías de remisión ";
                    break;


                case INT_TBL_BUTITMFAL:
                    strMsg="Muesta los items pendientes por despachar ";
                    break;



                default:
                    strMsg=" ";
                    break;


            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
 /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConLoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoLoc.setCampoBusqueda(2);
                    vcoLoc.setVisible(true);
                    if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(0);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtCodLoc.setText(strCodLoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoLoc.buscar("a1.tx_nom", txtNomLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(2);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtNomLoc.setText(strNomLoc);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
/**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Locales".
     */
    private boolean configurarVenConLoc()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_loc");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("400");
            //Validar los locales a presentar.
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los locales.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
//                    strSQL="";
//                    strSQL+="SELECT a1.co_loc, a1.tx_nom";
//                    strSQL+=" FROM tbm_loc AS a1";
//                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//                    strSQL+=" ORDER BY a1.co_loc";
                }
                else
                {
                    //Armar la sentencia SQL.
//                    strSQL="";
//                    strSQL+="SELECT a1.co_loc, a1.tx_nom";
//                    strSQL+=" FROM tbm_loc AS a1";
//                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
//                    strSQL+=" ORDER BY a1.co_loc";
                }
            }
            else
            {
                //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_loc, a1.tx_nom";
                    strSQL+=" FROM tbm_loc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" ORDER BY a1.co_loc";
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_loc, a1.tx_nom";
                    strSQL+=" FROM tbm_loc AS a1";
                    strSQL+=" INNER JOIN";
                    strSQL+=" (";
                    strSQL+=" SELECT co_empRel AS co_emp, co_locRel AS co_loc";
                    strSQL+=" FROM tbr_locPrgUsr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" ) AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc)";
                    strSQL+=" ORDER BY a1.co_loc";
                }
            }
            vcoLoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de locales", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoLoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    public String retornaDiferenciaTiempo(Date datFec1, Date datFec2){
        String strTiempo;
         // Crear 2 instancias de Calendar
         Calendar cal1 = Calendar.getInstance();
         Calendar cal2 = Calendar.getInstance();
        // Establecer las fechas
        cal1.set(datFec1.getYear(), datFec1.getMonth(), datFec1.getDay());
        cal2.set(datFec2.getYear(), datFec2.getMonth(), datFec2.getDay());
        // conseguir la representacion de la fecha en milisegundos
        long milis1 = datFec1.getTime();//cal1.getTimeInMillis();
        long milis2 = datFec2.getTime();
        // calcular la diferencia en milisengundos
        long diff = milis1 - milis2;
        long diffDays;
        long diffResto;
        long diffHours;
        long diffMinutes;
        // calcular la diferencia en segundos
        long diffSeconds = diff / 1000;
        //Calcular Dias
        if ((diff / (24 * 60 * 60 * 1000))>=1) {
            diffDays = diff / (24 * 60 * 60 * 1000);
        }else{
            diffDays = 0;
        }
        diffResto = diffDays*(24 * 60 * 60 * 1000);
        diff= diff-diffResto;
        //Calcular Horas
        if ((diff / (60 * 60 * 1000))>=1) {
            diffHours = diff / (60 * 60 * 1000);
        }else{
            diffHours = 0;
        }
        diffResto = diffHours*(60 * 60 * 1000);
        diff= diff-diffResto;
        //Calcular Minutos
        if ((diff / (60 * 1000))>=1) {
            diffMinutes = diff / (60 * 1000);
        }else{
            diffMinutes = 0;
        }
        diffResto = diffMinutes*(60 * 1000);
        diff= diff-diffResto;
        //Calcular Segundos
        if ((diff / (1000))>=1) {
            diffSeconds = diff / (1000);
        }else{
            diffSeconds = 0;
        }
        diffResto = diffMinutes*(1000);
        diff= diff-diffResto;
        //System.out.println("REsto:"+diff);
        if (diffDays>0) {
            diffDays=diffDays*24;
            diffHours=diffHours+diffDays;
        }
        String strH= diffHours + "";
        String strM= diffMinutes + "";
        String strS= diffSeconds + "";
        if (strH.length()<2) {
            strH = "0" + diffHours;
        }
        if (strM.length()<2) {
            strM = "0" + diffMinutes;
        }
        if (strS.length()<2) {
            strS = "0" + diffSeconds;
        }
        strTiempo =  strH + ":" + strM + ":"+strS ;
    return strTiempo;
    }
    private void llamarPantGuia(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){

       Compras.ZafCom23.ZafCom23 obj1 = new  Compras.ZafCom23.ZafCom23(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, 0 );
       this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
       obj1.show();
}
    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa utilizar
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
