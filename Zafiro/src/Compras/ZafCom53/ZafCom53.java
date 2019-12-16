/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
  
/*
 * ZafCom57.java
 *
 * Created on Mar 1, 2010, 9:46:43 AM
 */
  
package Compras.ZafCom53;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import java.util.Vector;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import java.sql.Connection;
import java.sql.DriverManager;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafRptSis.ZafRptSis;

/**
 *
 * @author jayapata
 */
public class ZafCom53 extends javax.swing.JInternalFrame {
  ZafParSis objParSis;
  ZafUtil objUti;
  String strVersion=" 1.2 ";
  private ZafSelFec objSelFec;
  private ZafTblMod objTblMod, objTblModSec;
  Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
  private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt;
  private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButDG, objTblCelRenButDG1;
  private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGenDG, objTblCelEdiButGenDG1;
  private ZafRptSis objRptSis;
  private ZafThreadGUIRpt objThrGUIRpt;
  private java.util.Date datFecAux;
  private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;

  ZafVenCon objVenConBodUsr;
  ZafVenCon objVenConCLi;
  ZafVenCon objVenConVen;

   String strCodBod="", strNomBod="";
    String strCodCli="";
    String strDesCli="";
    String strCodVen="";
    String strDesVen="";

   private Vector vecDat, vecCab, vecReg;
   private boolean blnCon;

   private ZafThreadGUI objThrGUI;
   
   
    final int INT_TBL_LIN=0;                        //Línea
    final int INT_TBL_CHKSEL =1;
    final int INT_TBL_CODEMP =2;                    //Código del item (Sistema).
    final int INT_TBL_CODLOC =3;                    //Código del item (Sistema).
    final int INT_TBL_CODTIPDOC =4;                    //Código del item (Alterno).
    final int INT_TBL_DESCTIDOC =5;                    //Código del item (Alterno).
    final int INT_TBL_CODDOC =6;                    //Nombre del item.
    final int INT_TBL_NUMDOC=7;                    //Descripción corta de la unidad de medida.
    final int INT_TBL_NUMDOCORI=8;
    final int INT_TBL_FECDOC=9;                    //Stock consolidado.
    final int INT_TBL_CODCLI=10;                   //Precio de venta 1.
    final int INT_TBL_NOMCLI=11;                   //Precio de venta 1.
    final int INT_TBL_ESTDOC=12;                   //Precio de venta 1.
    final int INT_TBL_GUIPRI =13;
    final int INT_TBL_GUISEC =14;
    final int INT_TBL_TIGUSE =15;
    final int INT_TBL_CTOGUS =16;
    final int INT_TBL_BUTGUP =17;
    final int INT_TBL_PENCONF=18;
    final int INT_TBL_PARCONF=19;
    final int INT_TBL_TOTCONF=20;
    final int INT_TBL_BUTGUIS1=21;



    final int INT_TBL2_LIN=0;                        //Línea
    final int INT_TBL2_CODEMP =1;                    //Código del item (Sistema).
    final int INT_TBL2_CODLOC =2;                    //Código del item (Sistema).
    final int INT_TBL2_CODTIPDOC =3;                    //Código del item (Alterno).
    final int INT_TBL2_DESCTIDOC =4;                    //Código del item (Alterno).
    final int INT_TBL2_CODDOC =5;                    //Nombre del item.
    final int INT_TBL2_NUMDOC=6;                    //Descripción corta de la unidad de medida.
    final int INT_TBL2_FECDOC=7;                    //Stock consolidado.
    final int INT_TBL2_CODCLI=8;                   //Precio de venta 1.
    final int INT_TBL2_NOMCLI=9;                   //Precio de venta 1.
    final int INT_TBL2_PENCONF=10;
    final int INT_TBL2_PARCONF=11;
    final int INT_TBL2_TOTCONF=12;
    final int INT_TBL2_BUTGUIS=13;





    /** Creates new form ZafCom57 */
    public ZafCom53(ZafParSis objZafParsis) {
      try{
        this.objParSis = (Librerias.ZafParSis.ZafParSis) objZafParsis.clone();
        objUti = new ZafUtil();

         objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
         objTblCelEdiButGenDG1=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();

         objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);


        initComponents();

            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(true);
            panFilCab.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);

            
        this.setTitle(objParSis.getNombreMenu()+" "+ strVersion );
        lblTit.setText( objParSis.getNombreMenu() );
       }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */



       /** Configurar el formulario. */
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
            vecCab.add(INT_TBL_CHKSEL,"");
            vecCab.add(INT_TBL_CODEMP,"Cod.Emp.");
            vecCab.add(INT_TBL_CODLOC,"Cod.Loc.");
            vecCab.add(INT_TBL_CODTIPDOC,"Cod.Tip.Doc");
            vecCab.add(INT_TBL_DESCTIDOC,"Des.Tip.Doc");
            vecCab.add(INT_TBL_CODDOC,"Cod.Doc");
            vecCab.add(INT_TBL_NUMDOC,"Num.Doc");
            vecCab.add(INT_TBL_NUMDOCORI,"Ori.Num.Doc");
            vecCab.add(INT_TBL_FECDOC,"Fec.Doc");
            vecCab.add(INT_TBL_CODCLI,"Cod.Cli");
            vecCab.add(INT_TBL_NOMCLI,"Nom.Cli");
            vecCab.add(INT_TBL_ESTDOC,"Est.Doc.");
            vecCab.add(INT_TBL_GUIPRI,"Gui.Pri");
            vecCab.add(INT_TBL_GUISEC,"Gui.Sec");
            vecCab.add(INT_TBL_TIGUSE,"Tie.Gui.Sec");
            vecCab.add(INT_TBL_CTOGUS,"Cre.Tod.Gui.Sec");
            vecCab.add(INT_TBL_BUTGUP,"..");
            vecCab.add(INT_TBL_PENCONF,"Pendiente");
            vecCab.add(INT_TBL_PARCONF,"Parcial");
            vecCab.add(INT_TBL_TOTCONF,"Total");
            vecCab.add(INT_TBL_BUTGUIS1,"..");
           
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
            tcmAux.getColumn(INT_TBL_DESCTIDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(250);
            tcmAux.getColumn(INT_TBL_BUTGUIS1).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_GUIPRI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_GUISEC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_TIGUSE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CTOGUS).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PENCONF).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PARCONF).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_TOTCONF).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_BUTGUP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_ESTDOC).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CHKSEL).setPreferredWidth(25);




        objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_CHKSEL).setCellRenderer(objTblCelRenChk);
        objTblCelRenChk=null;


            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
               vecAux.add("" + INT_TBL_BUTGUP);
               vecAux.add("" + INT_TBL_BUTGUIS1);
               vecAux.add("" + INT_TBL_CHKSEL);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);


	    Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_GUIPRI).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_GUISEC).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_TIGUSE).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_CTOGUS).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_PENCONF).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_PARCONF).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_TOTCONF).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;

            tcmAux.getColumn(INT_TBL_GUIPRI).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_GUIPRI).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_GUISEC).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_TIGUSE).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_CTOGUS).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_PENCONF).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_PARCONF).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_TOTCONF).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_CHKSEL).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                  
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                  

                }
	         });
            objTblCelRenChk=null;

	

//            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
//            tcmAux.getColumn(INT_TBL_OBSREP).setCellEditor(objTblCelEdiTxt);
//            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
//                    int intFil=tblDat.getSelectedRow();
//                     if( tblDat.getValueAt( intFil, INT_TBL_ESTCHK).toString().equals("S") ){
//                            objTblCelEdiTxt.setCancelarEdicion(true);
//                     }
//                }
//
//                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                   int intFil=tblDat.getSelectedRow();
//                     if( tblDat.getValueAt( intFil, INT_TBL_ESTCHK).toString().equals("S") ){
//                            objTblCelEdiTxt.setCancelarEdicion(true);
//                     }
//                }
//            });


            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            ZafMouMotAda objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            new ZafTblBus(tblDat);
            //Configurar JTable: Renderizar celdas.

            //Libero los objetos auxiliares.
            new ZafTblOrd(tblDat);

              ArrayList arlColHid=new ArrayList();
               arlColHid.add(""+INT_TBL_CODEMP);
               arlColHid.add(""+INT_TBL_CODLOC);
               arlColHid.add(""+INT_TBL_CODTIPDOC);
               arlColHid.add(""+INT_TBL_CODDOC);
               arlColHid.add(""+INT_TBL_NUMDOCORI);
              objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
              arlColHid=null;


            //Configurar JTable: Establecer el ListSelectionListener.
            javax.swing.ListSelectionModel lsm=tblDat.getSelectionModel();
            lsm.addListSelectionListener(new ZafLisSelLis());




            objTblCelRenButDG1=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTGUIS1).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUTGUP).setCellRenderer(objTblCelRenButDG1);

            objTblCelRenButDG1.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    switch (objTblCelRenButDG1.getColumnRender())
                    {
                      case INT_TBL_BUTGUIS1:
                               objTblCelRenButDG1.setText("...");
                       break;

                        case INT_TBL_BUTGUP:
                          if (objTblMod.getValueAt(objTblCelRenButDG1.getRowRender(), INT_TBL_TIGUSE).toString().equals("true") ){
                           if (objTblMod.getValueAt(objTblCelRenButDG1.getRowRender(), INT_TBL_CTOGUS).toString().equals("false") ){
                            objTblCelRenButDG1.setText("...");
                           }else{ objTblCelRenButDG1.setText(""); }
                          }else{ objTblCelRenButDG1.setText(""); }

                        break;
                    }
                }
            });

             //Configurar JTable: Editor de celdas.
            tcmAux.getColumn(INT_TBL_BUTGUIS1).setCellEditor(objTblCelEdiButGenDG1);
            tcmAux.getColumn(INT_TBL_BUTGUP).setCellEditor(objTblCelEdiButGenDG1);

            objTblCelEdiButGenDG1.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                          case INT_TBL_BUTGUIS1:

                         break;

                         case INT_TBL_BUTGUP:

                       
                          if (objTblMod.getValueAt(intFilSel, INT_TBL_TIGUSE).toString().equals("true") ){
                           if (objTblMod.getValueAt(intFilSel, INT_TBL_CTOGUS).toString().equals("false") ){


                           }else{  objTblCelEdiButGenDG1.setCancelarEdicion(true); }
                          }else{  objTblCelEdiButGenDG1.setCancelarEdicion(true); }

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
                          case INT_TBL_BUTGUIS1:

                             llamarPantGuia( objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString()
                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString()
                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOC).toString()
                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
                                 );

                           break;


                           case INT_TBL_BUTGUP:

                          if (objTblMod.getValueAt(intFilSel, INT_TBL_TIGUSE).toString().equals("true") ){
                           if (objTblMod.getValueAt(intFilSel, INT_TBL_CTOGUS).toString().equals("false") ){

                             llamarVenFalItmGSec( objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString()
                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString()
                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOC).toString()
                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
                                 );

                          }else{  objTblCelEdiButGenDG1.setCancelarEdicion(true); }
                          }else{  objTblCelEdiButGenDG1.setCancelarEdicion(true); }


                           break;



                        }
                }}
           });


   ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
     objTblHeaGrp.setHeight(16*2);

     ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" DATOS DE LA CONFIRMACION  " );
     objTblHeaColGrpAmeSur.setHeight(16);


            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_PENCONF));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_PARCONF));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TOTCONF));

       objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
       objTblHeaColGrpAmeSur=null;


    
     objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("  " );
     objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DESCTIDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NUMDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODCLI));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NOMCLI));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_ESTDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_GUIPRI));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_GUISEC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TIGUSE));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CTOGUS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTGUP));
       objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
       objTblHeaColGrpAmeSur=null;


     objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("  " );
     objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTGUIS1));
       objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
       objTblHeaColGrpAmeSur=null;

  
            tcmAux=null;
            setEditable(true);

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



     public void setEditable(boolean editable) {
        if (editable==true){
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }else{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
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

               if (chkMosGuSec.isSelected()){

                    objTblModSec.removeAllRows();

                    cargarGuiSec();

                }

            }
        }
    }


    

         /** Configurar el formulario. */
    private boolean configurarFrmSec()
    {
        boolean blnRes=true;
        try
        {

            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(8);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL2_LIN,"");
            vecCab.add(INT_TBL2_CODEMP,"Cod.Emp.");
            vecCab.add(INT_TBL2_CODLOC,"Cod.Loc.");
            vecCab.add(INT_TBL2_CODTIPDOC,"Cod.Tip.Doc");
            vecCab.add(INT_TBL2_DESCTIDOC,"Des.Tip.Doc");
            vecCab.add(INT_TBL2_CODDOC,"Cod.Doc");
            vecCab.add(INT_TBL2_NUMDOC,"Num.Doc");
            vecCab.add(INT_TBL2_FECDOC,"Fec.Doc");
            vecCab.add(INT_TBL2_CODCLI,"Cod.Cli");
            vecCab.add(INT_TBL2_NOMCLI,"Nom.Cli");
            vecCab.add(INT_TBL2_PENCONF,"Pendiente");
            vecCab.add(INT_TBL2_PARCONF,"Parcial");
            vecCab.add(INT_TBL2_TOTCONF,"Total");
            vecCab.add(INT_TBL2_BUTGUIS,"");


           // vecCab.add(INT_TBL_DAT_TOT_COS,"Total.Costo");
            objTblModSec=new ZafTblMod();
            objTblModSec.setHeader(vecCab);
            tblSec.setModel(objTblModSec);
            //Configurar JTable: Establecer tipo de selección.
            tblSec.setRowSelectionAllowed(true);
            tblSec.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            new ZafColNumerada(tblSec,INT_TBL2_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            new ZafTblPopMnu(tblSec);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblSec.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblSec.getColumnModel();

            tcmAux.getColumn(INT_TBL2_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL2_DESCTIDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL2_NUMDOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL2_FECDOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL2_CODCLI).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL2_NOMCLI).setPreferredWidth(250);
            tcmAux.getColumn(INT_TBL2_BUTGUIS).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL2_PENCONF).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL2_PARCONF).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL2_TOTCONF).setPreferredWidth(50);

            



            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
               vecAux.add("" + INT_TBL2_BUTGUIS);
            objTblModSec.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);


            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblSec.getTableHeader().setReorderingAllowed(false);
          //  ZafMouMotAda objMouMotAda=new ZafMouMotAda();
          //  tblSec.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            new ZafTblBus(tblSec);
            //Configurar JTable: Renderizar celdas.

            //Libero los objetos auxiliares.
            new ZafTblOrd(tblSec);

              ArrayList arlColHid=new ArrayList();
               arlColHid.add(""+INT_TBL2_CODEMP);
               arlColHid.add(""+INT_TBL2_CODLOC);
               arlColHid.add(""+INT_TBL2_CODTIPDOC);
              arlColHid.add(""+INT_TBL2_CODDOC);
              objTblModSec.setSystemHiddenColumns(arlColHid, tblSec);
              arlColHid=null;


            objTblCelRenButDG=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL2_BUTGUIS).setCellRenderer(objTblCelRenButDG);
            objTblCelRenButDG.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    switch (objTblCelRenButDG.getColumnRender())
                    {
                      case INT_TBL2_BUTGUIS:
                               objTblCelRenButDG.setText("...");
                       break;
                    }
                }
            });

             //Configurar JTable: Editor de celdas.
            objTblCelEdiButGenDG=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL2_BUTGUIS).setCellEditor(objTblCelEdiButGenDG);
            objTblCelEdiButGenDG.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblSec.getSelectedRow();
                    intColSel=tblSec.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                          case INT_TBL2_BUTGUIS:

                         break;

                       }
                     }
                    }
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel=tblSec.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                          case INT_TBL2_BUTGUIS:

                             llamarPantGuia( objTblModSec.getValueAt(intFilSel, INT_TBL2_CODEMP).toString()
                                    ,objTblModSec.getValueAt(intFilSel, INT_TBL2_CODLOC).toString()
                                    ,objTblModSec.getValueAt(intFilSel, INT_TBL2_CODTIPDOC).toString()
                                    ,objTblModSec.getValueAt(intFilSel, INT_TBL2_CODDOC).toString()
                                 );

                           break;

                        }
                }}
           });




     ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblSec.getTableHeader();
     objTblHeaGrp.setHeight(16*2);

     ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" DATOS DE LA CONFIRMACION  " );
     objTblHeaColGrpAmeSur.setHeight(16);


            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL2_PENCONF));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL2_PARCONF));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL2_TOTCONF));

       objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
       objTblHeaColGrpAmeSur=null;


    
     objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("  " );
     objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL2_DESCTIDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL2_NUMDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL2_FECDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL2_CODCLI));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL2_NOMCLI));
       objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
       objTblHeaColGrpAmeSur=null;


     objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("  " );
     objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL2_BUTGUIS));
       objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
       objTblHeaColGrpAmeSur=null;





            tcmAux=null;
            objTblModSec.setModoOperacion(objTblModSec.INT_TBL_EDI);

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }





private void llamarPantGuia(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){
       Compras.ZafCom23.ZafCom23 obj1 = new  Compras.ZafCom23.ZafCom23(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, 0 );
       this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
       obj1.show();
}


private void llamarVenFalItmGSec(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){
       Compras.ZafCom53.ZafCom53_01 obj1 = new  Compras.ZafCom53.ZafCom53_01(objParSis, Integer.parseInt(strCodEmp), Integer.parseInt(strCodLoc), Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc)  );
       this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
       obj1.show();
}






     public void Configura_ventana_consulta(){
        
        configurarVenConBodUsr();
        configurarVenConClientes();
        configurarVenConVendedor();
        cargarBodPre();
        configurarFrm();
        configurarFrmSec();
        
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

            if (objParSis.getCodigoUsuario()==1)
            {
                //Armar la sentencia SQL.
                Str_Sql="SELECT co_bod, tx_nom FROM ( SELECT a2.co_bod, a2.tx_nom "+
                " FROM tbm_emp AS a1 "+
                " INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) "+
                " WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo()+" "+
                " ORDER BY a1.co_emp, a2.co_bod  ) as a ";
             }
            else
            {
                Str_Sql="SELECT co_bod, tx_nom FROM ( " +
                " select a2.co_bodgrp as co_bod,  a1.tx_nom from tbr_bodlocprgusr as a " +
                " inner join tbr_bodEmpBodGrp as a2 on (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) "+
                " inner join tbm_bod as a1 on (a1.co_emp=a2.co_empgrp and a1.co_bod=a2.co_bodgrp) " +
                " where a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal()+" " +
                " and a.co_usr="+objParSis.getCodigoUsuario()+" and a.co_mnu="+objParSis.getCodigoMenu()+"  " +
                " ) as a";
            }
    objVenConBodUsr=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
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





public void cargarBodPre(){
  java.sql.Connection  conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     conn =  java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
     if(conn!=null){
        stmLoc=conn.createStatement();

        strSql="SELECT co_bod, tx_nom FROM ( " +
        " select a2.co_bodgrp as co_bod,  a1.tx_nom from tbr_bodlocprgusr as a " +
        " inner join tbr_bodEmpBodGrp as a2 on (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) "+
        " inner join tbm_bod as a1 on (a1.co_emp=a2.co_empgrp and a1.co_bod=a2.co_bodgrp) " +
        " where a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal()+" " +
        " and a.co_usr="+objParSis.getCodigoUsuario()+" and a.co_mnu="+objParSis.getCodigoMenu()+"  and a.st_reg='P' " +
        " ) as a";

        //System.out.println(""+ strSql);
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







    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        scrBar = new javax.swing.JScrollPane();
        panFilCab = new javax.swing.JPanel();
        lblBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBusBod = new javax.swing.JButton();
        chkNo = new javax.swing.JCheckBox();
        chkSi = new javax.swing.JCheckBox();
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
        jLabel1 = new javax.swing.JLabel();
        cboEstDoc = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        chkConfTotG = new javax.swing.JCheckBox();
        chkConfParG = new javax.swing.JCheckBox();
        chkConfPenG = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        cboEstImp = new javax.swing.JComboBox();
        panRep = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSec = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        chkMosGuSec = new javax.swing.JCheckBox();
        panSur = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butVisPre = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
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

        lblTit.setText("jLabel1");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        panFilCab.setAutoscrolls(true);
        panFilCab.setPreferredSize(new java.awt.Dimension(0, 400));
        panFilCab.setLayout(null);

        lblBod.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblBod.setText("Bodega:");
        panFilCab.add(lblBod);
        lblBod.setBounds(10, 80, 70, 20);

        txtCodBod.setBackground(objParSis.getColorCamposObligatorios());
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
        panFilCab.add(txtCodBod);
        txtCodBod.setBounds(70, 80, 70, 20);

        txtNomBod.setBackground(objParSis.getColorCamposObligatorios());
        txtNomBod.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtNomBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodFocusLost(evt);
            }
        });
        panFilCab.add(txtNomBod);
        txtNomBod.setBounds(140, 80, 230, 20);

        butBusBod.setText("jButton2");
        butBusBod.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusBodActionPerformed(evt);
            }
        });
        panFilCab.add(butBusBod);
        butBusBod.setBounds(370, 80, 20, 20);

        chkNo.setFont(new java.awt.Font("SansSerif", 0, 11));
        chkNo.setSelected(true);
        chkNo.setText("Mostrar los documentos que no tienen guía secundaria");
        panFilCab.add(chkNo);
        chkNo.setBounds(5, 130, 410, 20);

        chkSi.setFont(new java.awt.Font("SansSerif", 0, 11));
        chkSi.setSelected(true);
        chkSi.setText("Mostrar los documentos que si tienen guía secundaria");
        panFilCab.add(chkSi);
        chkSi.setBounds(5, 110, 410, 20);

        buttonGroup1.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        panFilCab.add(optFil);
        optFil.setBounds(5, 170, 400, 20);

        buttonGroup1.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
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
        optTod.setBounds(5, 150, 400, 20);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli.setText("Cliente/Proveedor:");
        panFilCab.add(lblCli);
        lblCli.setBounds(30, 190, 100, 20);

        lblCli1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli1.setText("Vend / Comp:");
        panFilCab.add(lblCli1);
        lblCli1.setBounds(30, 210, 100, 20);

        txtCodVen.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtCodVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVenActionPerformed(evt);
            }
        });
        txtCodVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVenFocusLost(evt);
            }
        });
        panFilCab.add(txtCodVen);
        txtCodVen.setBounds(130, 210, 50, 20);

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
        panFilCab.add(txtCodCli);
        txtCodCli.setBounds(130, 190, 50, 20);

        txtNomCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        panFilCab.add(txtNomCli);
        txtNomCli.setBounds(180, 190, 280, 20);

        txtNomVen.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtNomVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVenActionPerformed(evt);
            }
        });
        txtNomVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVenFocusLost(evt);
            }
        });
        panFilCab.add(txtNomVen);
        txtNomVen.setBounds(180, 210, 280, 20);

        butVen.setFont(new java.awt.Font("SansSerif", 0, 11));
        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFilCab.add(butVen);
        butVen.setBounds(460, 210, 20, 20);

        butcli.setFont(new java.awt.Font("SansSerif", 0, 11));
        butcli.setText("...");
        butcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butcliActionPerformed(evt);
            }
        });
        panFilCab.add(butcli);
        butcli.setBounds(460, 190, 20, 20);

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel1.setText("Estado:");
        panFilCab.add(jLabel1);
        jLabel1.setBounds(350, 280, 70, 20);

        cboEstDoc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos", "A: Activos", "I : Anulados" }));
        cboEstDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstDocActionPerformed(evt);
            }
        });
        panFilCab.add(cboEstDoc);
        cboEstDoc.setBounds(160, 282, 170, 20);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Número de documento"));
        jPanel3.setLayout(null);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel2.setText("Desde:");
        jPanel3.add(jLabel2);
        jLabel2.setBounds(30, 20, 50, 15);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        jPanel3.add(txtCodAltDes);
        txtCodAltDes.setBounds(80, 20, 100, 20);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel3.setText("Hasta:");
        jPanel3.add(jLabel3);
        jLabel3.setBounds(240, 20, 50, 15);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
        });
        jPanel3.add(txtCodAltHas);
        txtCodAltHas.setBounds(280, 20, 100, 20);

        panFilCab.add(jPanel3);
        jPanel3.setBounds(30, 230, 500, 50);

        chkConfTotG.setFont(new java.awt.Font("SansSerif", 0, 11));
        chkConfTotG.setSelected(true);
        chkConfTotG.setText("Mostrar los documetos que estan confirmado totalmente");
        chkConfTotG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkConfTotGActionPerformed(evt);
            }
        });
        panFilCab.add(chkConfTotG);
        chkConfTotG.setBounds(30, 350, 340, 20);

        chkConfParG.setFont(new java.awt.Font("SansSerif", 0, 11));
        chkConfParG.setSelected(true);
        chkConfParG.setText("Mostrar los documentos que estan confirmado parcialmente");
        chkConfParG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkConfParGActionPerformed(evt);
            }
        });
        panFilCab.add(chkConfParG);
        chkConfParG.setBounds(30, 330, 340, 20);

        chkConfPenG.setFont(new java.awt.Font("SansSerif", 0, 11));
        chkConfPenG.setSelected(true);
        chkConfPenG.setText("Mostrar los documentos que estan pendientes de confirmar.");
        chkConfPenG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkConfPenGActionPerformed(evt);
            }
        });
        panFilCab.add(chkConfPenG);
        chkConfPenG.setBounds(30, 310, 350, 20);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel4.setText("Estado del documento :");
        panFilCab.add(jLabel4);
        jLabel4.setBounds(35, 285, 120, 15);

        cboEstImp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos", "(PMC)", "(PXC)" }));
        cboEstImp.setVerifyInputWhenFocusTarget(false);
        cboEstImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstImpActionPerformed(evt);
            }
        });
        panFilCab.add(cboEstImp);
        cboEstImp.setBounds(395, 282, 130, 20);

        scrBar.setViewportView(panFilCab);

        tabFrm.addTab("Filtro", scrBar);

        panRep.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(190);
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
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnDat.setViewportView(tblDat);

        jPanel1.add(spnDat, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        tblSec.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblSec);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        chkMosGuSec.setText("Mostrar guías secundarias");
        chkMosGuSec.setPreferredSize(new java.awt.Dimension(269, 20));
        chkMosGuSec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosGuSecActionPerformed(evt);
            }
        });
        jPanel2.add(chkMosGuSec, java.awt.BorderLayout.NORTH);

        jSplitPane1.setRightComponent(jPanel2);

        panRep.add(jSplitPane1, java.awt.BorderLayout.CENTER);

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

        butVisPre.setText("Vista Preliminar");
        butVisPre.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butVisPre.setPreferredSize(new java.awt.Dimension(92, 25));
        butVisPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVisPreActionPerformed(evt);
            }
        });
        panBot.add(butVisPre);

        butImp.setText("Imprimir");
        butImp.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butImp.setPreferredSize(new java.awt.Dimension(92, 25));
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panBot.add(butImp);

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

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents



       private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
      }


    private class ZafThreadGUI extends Thread
    {
        public void run()
        {

          lblMsgSis.setText("Obteniendo datos...");
          pgrSis.setIndeterminate(true);


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


          
private String sqlConFil(){
   String sqlFil="";

   if(chkSi.isSelected()) sqlFil="  AND a.st_tieguisec='S' ";
   if(chkNo.isSelected()) sqlFil="  AND a.st_tieguisec='N'  ";

   if( chkSi.isSelected() && chkNo.isSelected()  ) sqlFil="  AND a.st_tieguisec in ('S','N') ";


   if(cboEstDoc.getSelectedIndex() > 0 ){
       if( cboEstDoc.getSelectedIndex() == 1 ) sqlFil+=" AND a.st_reg = 'A' ";
        else if( cboEstDoc.getSelectedIndex() == 2 ) sqlFil+=" AND a.st_reg = 'I' ";
   }



   if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
     sqlFil+=" AND a.ne_numdoc BETWEEN "+txtCodAltDes.getText()+" AND "+txtCodAltHas.getText()+"  ";

    if(optFil.isSelected()){

        if(!txtCodCli.getText().equals(""))
            sqlFil+=" AND a.co_clides="+txtCodCli.getText();

        if(!txtNomCli.getText().equals(""))
            sqlFil+=" AND a.tx_nomclides lIKE '"+txtNomCli.getText()+"'";

        if(!txtCodVen.getText().equals(""))
            sqlFil+=" AND a.co_ven="+txtCodVen.getText();
    }

       if(objSelFec.isCheckBoxChecked() ){
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
        }}
      return sqlFil;
    }

  

/**
* Esta función permite consultar los registros de acuerdo al criterio seleccionado.
* @return true: Si se pudo consultar los registros.
* <BR>false: En el caso contrario.
*/
private boolean cargarDetReg(String strFil){
 boolean blnRes=true;
 java.sql.Connection conn;
 java.sql.Statement stm;
 java.sql.ResultSet rst;
 int intNumTotReg=0, i=0;
 String strSql="", strSQL="";
 try{
    butCon.setText("Detener");
    conn=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
    if (conn!=null){
        stm=conn.createStatement();
                //Obtener la condición.


        String Str_Sql="";
        if(objParSis.getCodigoUsuario()==1){
           Str_Sql="Select a.co_tipdoc from tbr_tipdocprg as b " +
           " inner join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
                " where   b.co_emp="+objParSis.getCodigoEmpresa()+" and " +
                " b.co_loc = " + objParSis.getCodigoLocal()   + " and " +
                " b.co_mnu = "+objParSis.getCodigoMenu();
         }else {
                Str_Sql ="SELECT a.co_tipdoc "+
                " FROM tbr_tipDocUsr AS a1 " +
                " inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"+
                " WHERE "+
                "  a1.co_emp=" + objParSis.getCodigoEmpresa()+""+
                " AND a1.co_loc=" + objParSis.getCodigoLocal()+""+
                " AND a1.co_mnu=" + objParSis.getCodigoMenu()+""+
                " AND a1.co_usr=" + objParSis.getCodigoUsuario();
         }

       strSql="SELECT  a.st_coninv, a.st_cretodguisec,  a.st_tipguirem,a.st_reg, a.co_emp, a.co_loc, a.co_tipdoc, a7.tx_descor, a.co_doc , a.ne_numdoc, a.fe_doc, a.co_clides , a.tx_nomclides, a.st_tieguisec ,a8.tx_obsrec " +
       " ,CASE WHEN a8.co_emp IS NULL THEN 'N' ELSE 'S' END AS est , a.tx_datdocoriguirem  FROM tbm_cabguirem as a  " +
       " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
       " INNER JOIN tbm_cabtipdoc as a7 on (a7.co_emp = a.co_emp and a7.co_loc = a.co_loc and a7.co_tipdoc = a.co_tipdoc ) "+
       " LEFT JOIN tbm_recguirem AS a8 ON(a8.co_emp=a.co_emp and a8.co_loc=a.co_loc and a8.co_tipdoc=a.co_tipdoc and a8.co_doc=a.co_doc) "+
       " WHERE  a.st_reg not in('E')   and a.co_tipdoc in ( "+Str_Sql+" )  " +
       " AND ( a6.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" AND a6.co_bodGrp=( "+txtCodBod.getText()+" ) ) " +
       " AND  a.ne_numdoc > 0  "+strFil+"    ";


        strSQL="SELECT * FROM ( "+strSql+" ) AS X  ";

        String strEstConf="";

        if( chkConfPenG.isSelected() && chkConfParG.isSelected() && chkConfTotG.isSelected()  ) strEstConf="'F'";
     

        if(chkConfPenG.isSelected()) { if(strEstConf.equals("")) strEstConf+="'P'"; else strEstConf+=",'P'"; }
        if(chkConfParG.isSelected()) { if(strEstConf.equals("")) strEstConf+="'E'"; else strEstConf+=",'E'"; }
//        if(chkConfTotG.isSelected()) { if(strEstConf.equals("")) strEstConf+="'C'"; else strEstConf+=",'C'"; }
        if(chkConfTotG.isSelected()) { if(strEstConf.equals("")) strEstConf+="'C','F'"; else strEstConf+=",'C','F'"; }

        


        if(!strEstConf.equals(""))
            strSQL += " WHERE st_coninv IN ("+strEstConf+") ";

        if(!chkConfTotG.isSelected())
            strSQL += " and st_cretodguisec = 'N' ";
        

        strSQL += "  ORDER BY ne_numdoc ";



         System.out.println("-->"+ strSQL );

        rst=stm.executeQuery(strSQL);
        vecDat.clear();
        while (rst.next())
        {
            if (blnCon)
            {
            vecReg=new Vector();
            vecReg.add(INT_TBL_LIN,"");
            vecReg.add(INT_TBL_CHKSEL, new Boolean(false));
            vecReg.add(INT_TBL_CODEMP, rst.getString("co_emp"));
            vecReg.add(INT_TBL_CODLOC, rst.getString("co_loc"));
            vecReg.add(INT_TBL_CODTIPDOC, rst.getString("co_tipdoc"));
            vecReg.add(INT_TBL_DESCTIDOC,  rst.getString("tx_descor"));
            vecReg.add(INT_TBL_CODDOC, rst.getString("co_doc"));
            vecReg.add(INT_TBL_NUMDOC, rst.getString("ne_numdoc"));
            vecReg.add(INT_TBL_NUMDOCORI, rst.getString("tx_datdocoriguirem"));
            vecReg.add(INT_TBL_FECDOC, rst.getString("fe_doc"));
            vecReg.add(INT_TBL_CODCLI, rst.getString("co_clides"));
            vecReg.add(INT_TBL_NOMCLI, rst.getString("tx_nomclides"));
            vecReg.add(INT_TBL_ESTDOC, rst.getString("st_reg"));

            vecReg.add(INT_TBL_GUIPRI, new Boolean(rst.getString("st_tipguirem").equals("P")?true:false) );
            vecReg.add(INT_TBL_GUISEC, new Boolean(rst.getString("st_tipguirem").equals("S")?true:false) );
            vecReg.add(INT_TBL_TIGUSE, new Boolean(rst.getString("st_tieguisec").equals("S")?true:false) );
            vecReg.add(INT_TBL_CTOGUS, new Boolean(rst.getString("st_cretodguisec").equals("S")?true:false) );
            vecReg.add(INT_TBL_BUTGUP, "" );
            vecReg.add(INT_TBL_PENCONF, new Boolean(rst.getString("st_coninv").equals("P")?true:false) );
            vecReg.add(INT_TBL_PARCONF, new Boolean(rst.getString("st_coninv").equals("E")?true:false) );
//            vecReg.add(INT_TBL_TOTCONF, new Boolean(rst.getString("st_coninv").equals("C")?true:false) );
            vecReg.add(INT_TBL_TOTCONF, new Boolean(rst.getString("st_coninv").equals("C") || rst.getString("st_coninv").equals("F")?true:false) );

            
           vecReg.add(INT_TBL_BUTGUIS1, "" );


            vecDat.add(vecReg);
         
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
    conn.close();
    conn=null;
    //Asignar vectores al modelo.
    objTblMod.setData(vecDat);
    tblDat.setModel(objTblMod);
    vecDat.clear();


        lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
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

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:

        

    }//GEN-LAST:event_optFilItemStateChanged

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected()) {
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");

            strCodCli="";  strDesCli="";
            strCodVen="";  strDesVen="";

            txtCodCli.setText("");
            txtNomCli.setText("");
            txtCodVen.setText("");
            txtNomVen.setText("");

            chkConfPenG.setSelected(true);
            chkConfParG.setSelected(true);
            chkConfTotG.setSelected(true);

            
            cboEstDoc.setSelectedIndex(0);

        }
}//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:



    }//GEN-LAST:event_optTodActionPerformed

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
}//GEN-LAST:event_txtCodAltDesFocusGained

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length()>0) {
            optFil.setSelected(true);
            if (txtCodAltHas.getText().length()==0)
                txtCodAltHas.setText(txtCodAltDes.getText());
        }
}//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        txtCodAltHas.selectAll();
}//GEN-LAST:event_txtCodAltHasFocusGained

    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        if (txtCodAltHas.getText().length()>0)
            optFil.setSelected(true);
}//GEN-LAST:event_txtCodAltHasFocusLost

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        // TODO add your handling code here:
        txtCodVen.transferFocus();
}//GEN-LAST:event_txtCodVenActionPerformed

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        // TODO add your handling code here:
        strCodVen=txtCodVen.getText();
        txtCodVen.selectAll();
}//GEN-LAST:event_txtCodVenFocusGained

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
            if(txtCodCli.getText().equals("")) {
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
        strDesCli=txtNomCli.getText();
        txtNomCli.selectAll();
}//GEN-LAST:event_txtNomCliFocusGained

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

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        // TODO add your handling code here:
        txtNomVen.transferFocus();
}//GEN-LAST:event_txtNomVenActionPerformed

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        // TODO add your handling code here:
        strDesVen=txtNomVen.getText();
        txtNomVen.selectAll();
}//GEN-LAST:event_txtNomVenFocusGained

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

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:

        Configura_ventana_consulta();

    }//GEN-LAST:event_formInternalFrameOpened

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").

        if(txtCodBod.getText().equals("")){
            MensajeInf("Seleccione la bodega antes consultar un documento. ");
            tabFrm.setSelectedIndex(0);
            txtCodBod.requestFocus();
        } else{


            if (butCon.getText().equals("Consultar")) {
                blnCon=true;
                if (objThrGUI==null) {
                    objThrGUI=new ZafThreadGUI();
                    objThrGUI.start();
                }
            } else {
                blnCon=false;
            }
        }
}//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
}//GEN-LAST:event_butCerActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        // TODO add your handling code here:
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }

    }//GEN-LAST:event_exitForm

    private void chkMosGuSecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosGuSecActionPerformed
        // TODO add your handling code here:

        if (chkMosGuSec.isSelected()){

            objTblModSec.removeAllRows();

            cargarGuiSec();

        }
    }//GEN-LAST:event_chkMosGuSecActionPerformed

    private void cboEstDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstDocActionPerformed
        // TODO add your handling code here:

      if(cboEstDoc.getSelectedIndex() > 0 )
        optFil.setSelected(true);
        
    }//GEN-LAST:event_cboEstDocActionPerformed

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        // TODO add your handling code here:


        if(txtCodBod.getText().equals("")){
            MensajeInf("Seleccione la bodega antes consultar un documento. ");
            tabFrm.setSelectedIndex(0);
            txtCodBod.requestFocus();
        } else{

             cargarRepote(0);
        }


    }//GEN-LAST:event_butImpActionPerformed

    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
        // TODO add your handling code here:
       

       if(txtCodBod.getText().equals("")){
            MensajeInf("Seleccione la bodega antes consultar un documento. ");
            tabFrm.setSelectedIndex(0);
            txtCodBod.requestFocus();
        } else{

            cargarRepote(1);
        }


    }//GEN-LAST:event_butVisPreActionPerformed

    private void lblCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lblCodAltDesFocusGained
        // TODO add your handling code here:
        txtCodAltDes.selectAll();

    }//GEN-LAST:event_lblCodAltDesFocusGained

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

    private void chkConfTotGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkConfTotGActionPerformed
        // TODO add your handling code here:

         if(!chkConfTotG.isSelected())
           optFil.setSelected(true);
         
    }//GEN-LAST:event_chkConfTotGActionPerformed

    private void cboEstImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstImpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboEstImpActionPerformed


  private void cargarRepote(int intTipo){
   if (objThrGUIRpt==null)
    {
        objThrGUIRpt=new ZafThreadGUIRpt();
        objThrGUIRpt.setIndFunEje(intTipo);
        objThrGUIRpt.start();
    }
}




private boolean cargarGuiSec(){
boolean blnRes=true;
java.sql.Statement stmLoc;
java.sql.ResultSet rstLoc;
int intColSel=0;
String strSql="";
try
{
    if (tblDat.getSelectedRow()!=-1)
    {
        Connection con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
        if (con!=null)
        {
           stmLoc=con.createStatement();

            intColSel=tblDat.getSelectedRow();

             strSql="select  a1.st_coninv, a1.co_emp, a1.co_loc, a1.co_tipdoc, a7.tx_descor, a1.co_doc, a1.ne_numdoc, a1.fe_doc, a1.co_clides, a1.tx_nomclides " +
             "  from tbr_cabguirem as a " +
             " inner join tbm_cabguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) " +
             " INNER JOIN tbm_cabtipdoc as a7 on (a7.co_emp = a1.co_emp and a7.co_loc = a1.co_loc and a7.co_tipdoc = a1.co_tipdoc ) " +
             " where a.co_emp="+objTblMod.getValueAt(intColSel, INT_TBL_CODEMP)+" and a.co_locrel="+objTblMod.getValueAt(intColSel, INT_TBL_CODLOC)+" " +
             " and a.co_tipdocrel="+objTblMod.getValueAt(intColSel, INT_TBL_CODTIPDOC)+" and a.co_docrel="+objTblMod.getValueAt(intColSel, INT_TBL_CODDOC)+" ORDER BY  a1.ne_numdoc ";

            // System.out.println("-> "+strSql);

            vecDat.clear();
            rstLoc=stmLoc.executeQuery(strSql);
            while (rstLoc.next()){

                vecReg=new Vector();
                vecReg.add(INT_TBL2_LIN,"");
                vecReg.add(INT_TBL2_CODEMP, rstLoc.getString("co_emp") );
                vecReg.add(INT_TBL2_CODLOC, rstLoc.getString("co_loc") );
                vecReg.add(INT_TBL2_CODTIPDOC, rstLoc.getString("co_tipdoc") );
                vecReg.add(INT_TBL2_DESCTIDOC, rstLoc.getString("tx_descor") );
                vecReg.add(INT_TBL2_CODDOC, rstLoc.getString("co_doc") );
                vecReg.add(INT_TBL2_NUMDOC, rstLoc.getString("ne_numdoc") );
                vecReg.add(INT_TBL2_FECDOC, rstLoc.getString("fe_doc") );
                vecReg.add(INT_TBL2_CODCLI, rstLoc.getString("co_clides") );
                vecReg.add(INT_TBL2_NOMCLI, rstLoc.getString("tx_nomclides") );
                vecReg.add(INT_TBL2_PENCONF, new Boolean(rstLoc.getString("st_coninv").equals("P")?true:false) );
                vecReg.add(INT_TBL2_PARCONF, new Boolean(rstLoc.getString("st_coninv").equals("E")?true:false) );
                vecReg.add(INT_TBL2_TOTCONF, new Boolean(rstLoc.getString("st_coninv").equals("C")?true:false) );
                vecReg.add(INT_TBL2_BUTGUIS,"");
                vecDat.add(vecReg);
          }
         objTblModSec.setData(vecDat);
         tblSec.setModel(objTblModSec);
         vecDat.clear();

        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;
        con.close();
        con=null;

 }}}catch (java.sql.SQLException e){   blnRes=false;   objUti.mostrarMsgErr_F1(this, e);    }
    catch (Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
   return blnRes;
 }







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
    private javax.swing.JButton butBusBod;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butVen;
    private javax.swing.JButton butVisPre;
    private javax.swing.JButton butcli;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboEstDoc;
    private javax.swing.JComboBox cboEstImp;
    private javax.swing.JCheckBox chkConfParG;
    private javax.swing.JCheckBox chkConfPenG;
    private javax.swing.JCheckBox chkConfTotG;
    private javax.swing.JCheckBox chkMosGuSec;
    private javax.swing.JCheckBox chkNo;
    private javax.swing.JCheckBox chkSi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCli1;
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
    private javax.swing.JScrollPane scrBar;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblSec;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomVen;
    // End of variables declaration//GEN-END:variables


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

                 case INT_TBL_ESTDOC:
                    strMsg="Estado del Documento ";
                    break;
                case INT_TBL_GUIPRI:
                    strMsg="?Es guia principal.? ";
                    break;
                 case INT_TBL_GUISEC:
                    strMsg="?Es guia secundaria.? ";
                    break;

                case INT_TBL_TIGUSE:
                    strMsg="?Tiene guias secundaria.? ";
                    break;
                 case INT_TBL_CTOGUS:
                    strMsg="?Estan creadas todas las guias secundaria.? ";
                    break;

               case INT_TBL_BUTGUP:
                    strMsg="Ver los items que faltan de generar guia secundaria. ";
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

                  case INT_TBL_BUTGUIS1:
                    strMsg="Ver la guia de remicion ";
                    break;

                default:
                    strMsg=" ";
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
    private class ZafThreadGUIRpt extends Thread
    {
        private int intIndFun;

        public ZafThreadGUIRpt()
        {
            intIndFun=0;
        }

        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                   // objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                  //  objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                 //   objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                   // objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUIRpt=null;
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



private String cargarSql(String strFil){
 String strSql="", strSQL="";
 try{

        String Str_Sql="";
        if(objParSis.getCodigoUsuario()==1){
           Str_Sql="Select a.co_tipdoc from tbr_tipdocprg as b " +
           " inner join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
                " where   b.co_emp="+objParSis.getCodigoEmpresa()+" and " +
                " b.co_loc = " + objParSis.getCodigoLocal()   + " and " +
                " b.co_mnu = "+objParSis.getCodigoMenu();
         }else {
                Str_Sql ="SELECT a.co_tipdoc "+
                " FROM tbr_tipDocUsr AS a1 " +
                " inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"+
                " WHERE "+
                "  a1.co_emp=" + objParSis.getCodigoEmpresa()+""+
                " AND a1.co_loc=" + objParSis.getCodigoLocal()+""+
                " AND a1.co_mnu=" + objParSis.getCodigoMenu()+""+
                " AND a1.co_usr=" + objParSis.getCodigoUsuario();
         }

       strSql="SELECT  case when a.st_reg in ('I') then 'Anulado' else 'Activos' end as st_reg  , a.st_coninv, a.st_cretodguisec,  a.st_tipguirem, a.co_emp, a.co_loc, a.co_tipdoc, a7.tx_descor, a.co_doc , a.ne_numdoc, a.fe_doc, a.co_clides , a.tx_nomclides, a.st_tieguisec ,a8.tx_obsrec " +
       " ,CASE WHEN a8.co_emp IS NULL THEN 'N' ELSE 'S' END AS est , a.tx_datdocoriguirem  FROM tbm_cabguirem as a  " +
       " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
       " INNER JOIN tbm_cabtipdoc as a7 on (a7.co_emp = a.co_emp and a7.co_loc = a.co_loc and a7.co_tipdoc = a.co_tipdoc ) "+
       " LEFT JOIN tbm_recguirem AS a8 ON(a8.co_emp=a.co_emp and a8.co_loc=a.co_loc and a8.co_tipdoc=a.co_tipdoc and a8.co_doc=a.co_doc) "+
       " WHERE  a.st_reg not in('E')   and a.co_tipdoc in ( "+Str_Sql+" )  " +
       " AND ( a6.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" AND a6.co_bodGrp=( "+txtCodBod.getText()+" ) ) " +
       " AND  a.ne_numdoc > 0  "+strFil+"    ";



        strSQL="SELECT * FROM ( SELECT " +
        " case when st_tieguisec in ('S') then  " +
        " case when st_cretodguisec in ('S') then  '' else ' (PMC)' end  " +
        " else   " +
        " case when st_coninv in ('E','P') then  ' (PXC) ' else '' end  " +
        " end as EstRegConf,* FROM ( "+strSql+" ) AS X ) AS X    ";

        String strEstConf="";

        if( chkConfPenG.isSelected() && chkConfParG.isSelected() && chkConfTotG.isSelected()  ) strEstConf="'F'";


        if(chkConfPenG.isSelected()) { if(strEstConf.equals("")) strEstConf+="'P'"; else strEstConf+=",'P'"; }
        if(chkConfParG.isSelected()) { if(strEstConf.equals("")) strEstConf+="'E'"; else strEstConf+=",'E'"; }
        if(chkConfTotG.isSelected()) { if(strEstConf.equals("")) strEstConf+="'C'"; else strEstConf+=",'C'"; }




        if(!strEstConf.equals(""))
            strSQL += " WHERE st_coninv IN ("+strEstConf+") ";

        if(!chkConfTotG.isSelected())
            strSQL += " and st_cretodguisec = 'N' ";







   if(cboEstImp.getSelectedIndex() > 0 ){
       if( cboEstImp.getSelectedIndex() == 1 ) strSQL += "  and  trim(EstRegConf)='(PMC)' ";
        else if( cboEstImp.getSelectedIndex() == 2 ) strSQL += "  and  trim(EstRegConf)='(PXC)' ";
   }





        strSQL += "  ORDER BY  EstRegConf, ne_numdoc ";


         //System.out.println("-->"+ strSQL );


     }catch (Exception e){  objUti.mostrarMsgErr_F1(this, e); }
       return strSQL;
     }


private String cargarSqlRpt2(){
 String strSQL="";
 String strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="";
 StringBuffer strBufSql;
 int intSecBuf=0;
 try{

     strBufSql=new StringBuffer();

     for(int i=0; i<tblDat.getRowCount(); i++){
      if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){

          strCodEmp=tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
          strCodLoc=tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
          strCodTipDoc=tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString();
          strCodDoc=tblDat.getValueAt(i, INT_TBL_CODDOC).toString();

        
       strSQL=" select co_emp, co_loc, co_tipdoc, co_doc, tx_nomclides from tbm_cabguirem "
       + " where co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+"  "
       + " and co_doc="+strCodDoc+"  ";


       if(intSecBuf==1) strBufSql.append(" UNION ALL ");
       strBufSql.append( strSQL );

       intSecBuf=1;

      }}


     strSQL=" select * from ( "+strBufSql.toString()+" ) as x order by tx_nomclides ";

      System.out.println("-->"+ strSQL );

     strBufSql=null;

     }catch (Exception e){  objUti.mostrarMsgErr_F1(this, e); }
       return strSQL;
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
        boolean blnRes=true;
        String strRutRpt, strNomRpt;
        int i, intNumTotRpt;
        String sqlAux="";
         try
        {
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
            {
              intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {

                        System.out.println(""+Integer.parseInt(objRptSis.getCodigoReporte(i)) );

                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            case 382:

                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.


                                   sqlAux =  cargarSql( sqlConFil());



                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("strSql",  sqlAux );

                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);

                                break;

                                 case 387:

                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.

                                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                                    if (datFecAux==null)
                                        return false;

                                String strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MM/yyyy   HH:mm:ss");
                               
                                   sqlAux =  cargarSqlRpt2();



                                java.util.Map mapPar2=new java.util.HashMap();
                                mapPar2.put("strSql",  sqlAux );
                                mapPar2.put("strCamAudRpt", ""+ this.getClass().getName() + "      " +  strNomRpt +"   " + objParSis.getNombreUsuario() + "      " + strFecHorSer +"      v0.1    " );
                                mapPar2.put("strNomBod", txtNomBod.getText() );
                                mapPar2.put("strFecRpt", strFecHorSer );

                                mapPar2.put("SUBREPORT_DIR", strRutRpt);





//                                mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar2, intTipRpt);

                                break;

                            default:
                              
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



    

}
