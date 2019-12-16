/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCom50.java
 *
 * Created on Jan 10, 2011, 9:37:23 AM
 */

package Compras.ZafCom50;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafRptSis.ZafRptSis;
/**
 *
 * @author jayapata
 */
public class ZafCom50 extends javax.swing.JInternalFrame {

     ZafParSis objParSis;
    ZafUtil objUti;
    private ZafSelFec objSelFec;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod, objTblMod1;
    private ZafThreadGUI objThrGUI;
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblTot objTblTotDoc;
    ZafVenCon objVenConBodUsr; //*****************
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButDG;
    private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGenDG;
    private ZafMouMotAda objMouMotAda;
    private ZafMouMotAda1 objMouMotAda1;
    private ZafRptSis objRptSis;
    private ZafThreadGUIRtp objThrGUIRpt;

    
    String strCodBod="", strNomBod="";
    String strVersion=" ver. 0.1 ";

     // TABLA DE CAMPOS1
    final int INT_TBL_LINEA  =0;
    final int INT_TBL_DESCRI =1;
    final int INT_TBL_TOTDOC =2;
    final int INT_TBL_PESTOT =3;
    final int INT_TBL_PENDIE =4;
    final int INT_TBL_PARCIA =5;
    final int INT_TBL_TOTCON =6;
    final int INT_TBL_PESCON =7;
    final int INT_TBL_CUMPLI =8;

    
       // TABLA DE CAMPOS2
    final int INT_TBL1_LINEA  =0;
    final int INT_TBL1_CODEMP =1;
    final int INT_TBL1_CODLOC =2;
    final int INT_TBL1_NOMLOC =3;
    final int INT_TBL1_CODTID =4;
    final int INT_TBL1_DETIDC =5;
    final int INT_TBL1_DETIDL =6;
    final int INT_TBL1_CODDOC =7;
    final int INT_TBL1_NUMDOC =8;
    final int INT_TBL1_FECDOC =9;
    final int INT_TBL1_CODCPR =10;
    final int INT_TBL1_NOMCPR =11;
    final int INT_TBL1_PESDOC =12;
    final int INT_TBL1_PENCONF=13;
    final int INT_TBL1_PARCONF=14;
    final int INT_TBL1_CONCONF=15;
    final int INT_TBL1_PESCONF=16;
    final int INT_TBL1_BUTITM =17;

    
    private Vector vecDat, vecReg;

    /** Creates new form ZafCom50 */
     public ZafCom50(ZafParSis objZafParsis) {
         try{
        this.objParSis = (Librerias.ZafParSis.ZafParSis) objZafParsis.clone();
        objUti = new ZafUtil();


        objTblCelEdiButGenDG=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();

        objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);


        initComponents();



        vecDat=new Vector();

            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(true);
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

      configurarForm();
      configurarForm1();

      configurarVenConBodUsr();

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

             //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
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
                //Armar la sentencia SQL.

            Str_Sql="SELECT co_bod, tx_nom FROM ( " +
            " select a2.co_bodgrp as co_bod,  a1.tx_nom from tbr_bodlocprgusr as a " +
            " inner join tbr_bodEmpBodGrp as a2 on (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) "+
            " inner join tbm_bod as a1 on (a1.co_emp=a2.co_empgrp and a1.co_bod=a2.co_bodgrp) " +
            " where a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal()+" " +
            " and a.co_usr="+objParSis.getCodigoUsuario()+" and a.co_mnu="+objParSis.getCodigoMenu()+" " +
            " and a.tx_natbod = 'A' " +
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



 private boolean configurarForm(){
  boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    vecCab.add(INT_TBL_LINEA,"");
    vecCab.add(INT_TBL_DESCRI,"Descripción");
    vecCab.add(INT_TBL_TOTDOC,"Tot.Doc");
    vecCab.add(INT_TBL_PESTOT,"Peso");
    vecCab.add(INT_TBL_PENDIE,"Pediente");
    vecCab.add(INT_TBL_PARCIA,"Parcial");
    vecCab.add(INT_TBL_TOTCON,"Total");
    vecCab.add(INT_TBL_PESCON,"Peso");
    vecCab.add(INT_TBL_CUMPLI,"Cumplimiento");

    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);

     //Configurar JTable: Establecer tipo de selección.
    tblDat.setRowSelectionAllowed(true);
    tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
     objMouMotAda=new ZafMouMotAda();
     tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

     //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
    objTblMod.setColumnDataType(INT_TBL_TOTDOC, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_PESTOT, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_PENDIE, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_PARCIA, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_TOTCON, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_PESCON, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_CUMPLI, objTblMod.INT_COL_DBL, new Integer(0), null);

    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    tblDat.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_DESCRI).setPreferredWidth(110);
    tcmAux.getColumn(INT_TBL_TOTDOC).setPreferredWidth(75);
    tcmAux.getColumn(INT_TBL_PESTOT).setPreferredWidth(75);
    tcmAux.getColumn(INT_TBL_PENDIE).setPreferredWidth(75);
    tcmAux.getColumn(INT_TBL_PARCIA).setPreferredWidth(75);
    tcmAux.getColumn(INT_TBL_TOTCON).setPreferredWidth(75);
    tcmAux.getColumn(INT_TBL_PESCON).setPreferredWidth(75);
    tcmAux.getColumn(INT_TBL_CUMPLI).setPreferredWidth(75);

    objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
    objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
    objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
    objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
    objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
    tcmAux.getColumn(INT_TBL_PESTOT).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_PESCON).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_CUMPLI).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_TOTDOC).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_PENDIE).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_PARCIA).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_TOTCON).setCellRenderer(objTblCelRenLbl);
    objTblCelRenLbl=null;


     ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
     objTblHeaGrp.setHeight(16*2);

     ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" DOCUMENTOS QUE SI SE CONFIRMAN " );
     objTblHeaColGrpAmeSur.setHeight(16);

            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DESCRI));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TOTDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_PESTOT));

       objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
       objTblHeaColGrpAmeSur=null;

       objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" DATOS DE LA CONFIRMACION " );
       objTblHeaColGrpAmeSur.setHeight(16);

            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_PENDIE));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_PARCIA));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TOTCON));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_PESCON));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CUMPLI));

         objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
         objTblHeaColGrpAmeSur=null;




    //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);


    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
    tcmAux=null;


     //Configurar JTable: Establecer el ListSelectionListener.
     javax.swing.ListSelectionModel lsm=tblDat.getSelectionModel();
     lsm.addListSelectionListener(new ZafLisSelLis());

     return blnres;
}

 private boolean configurarForm1(){
  boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    vecCab.add(INT_TBL1_LINEA,"");
    vecCab.add(INT_TBL1_CODEMP,"Cód.Emp");
    vecCab.add(INT_TBL1_CODLOC,"Cód.Loc");
    vecCab.add(INT_TBL1_NOMLOC,"Nom.Loc.");
    vecCab.add(INT_TBL1_CODTID,"Cód.Tip.Doc");
    vecCab.add(INT_TBL1_DETIDC,"Des.Cor.TipDoc");
    vecCab.add(INT_TBL1_DETIDL,"Des.Lar.TipDoc");
    vecCab.add(INT_TBL1_CODDOC,"Cód.Doc");
    vecCab.add(INT_TBL1_NUMDOC,"Num.Doc");
    vecCab.add(INT_TBL1_FECDOC,"Fec.Doc");
    vecCab.add(INT_TBL1_CODCPR,"Cód.Prv");
    vecCab.add(INT_TBL1_NOMCPR,"Nom.Prv");
    vecCab.add(INT_TBL1_PESDOC,"Peso.Doc");
    vecCab.add(INT_TBL1_PENCONF,"Pendiente");
    vecCab.add(INT_TBL1_PARCONF,"Parcial");
    vecCab.add(INT_TBL1_CONCONF,"Total");
    vecCab.add(INT_TBL1_PESCONF,"Peso.Conf");
    vecCab.add(INT_TBL1_BUTITM,"..");

    objTblMod1=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod1.setHeader(vecCab);
    tblDat1.setModel(objTblMod1);

     //Configurar JTable: Establecer tipo de selección.
    tblDat1.setRowSelectionAllowed(true);
    tblDat1.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

     //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
     objMouMotAda1=new ZafMouMotAda1();
     tblDat1.getTableHeader().addMouseMotionListener(objMouMotAda1);



     //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
    objTblMod1.setColumnDataType(INT_TBL1_PESCONF, objTblMod1.INT_COL_DBL, new Integer(0), null);
   
    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat1, INT_TBL_LINEA);

     /* Aqui se agrega las columnas que van
             ha hacer ocultas
         * */
        ArrayList arlColHid=new ArrayList();
        arlColHid.add(""+INT_TBL1_CODEMP);
        arlColHid.add(""+INT_TBL1_CODLOC);
        arlColHid.add(""+INT_TBL1_CODTID);
        arlColHid.add(""+INT_TBL1_CODDOC);
        objTblMod1.setSystemHiddenColumns(arlColHid, tblDat1);
        arlColHid=null;

    tblDat1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat1.getColumnModel();
    tblDat1.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL1_LINEA).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL1_NOMLOC).setPreferredWidth(100);
    tcmAux.getColumn(INT_TBL1_CODTID).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL1_DETIDC).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL1_DETIDL).setPreferredWidth(120);
    tcmAux.getColumn(INT_TBL1_NUMDOC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL1_FECDOC).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL1_CODCPR).setPreferredWidth(40);
    tcmAux.getColumn(INT_TBL1_NOMCPR).setPreferredWidth(120);
    tcmAux.getColumn(INT_TBL1_PESDOC).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL1_PENCONF).setPreferredWidth(35);
    tcmAux.getColumn(INT_TBL1_PARCONF).setPreferredWidth(35);
    tcmAux.getColumn(INT_TBL1_CONCONF).setPreferredWidth(35);
    tcmAux.getColumn(INT_TBL1_PESCONF).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL1_BUTITM).setPreferredWidth(25);



    objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
    objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
    objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
    objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
    objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
    tcmAux.getColumn(INT_TBL1_PESDOC).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL1_PESCONF).setCellRenderer(objTblCelRenLbl);
    objTblCelRenLbl=null;



    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
       vecAux.add("" + INT_TBL1_BUTITM);
    objTblMod1.setColumnasEditables(vecAux);
    vecAux=null;
    //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat1);




            objTblCelRenButDG=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL1_BUTITM).setCellRenderer(objTblCelRenButDG);
            objTblCelRenButDG.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    switch (objTblCelRenButDG.getColumnRender())
                    {
                      case INT_TBL1_BUTITM:
                            if (objTblMod1.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL1_CONCONF).toString().equals("true") )
                                objTblCelRenButDG.setText("");
                            else
                                objTblCelRenButDG.setText("...");
                       break;


                    }
                }
            });


          //Configurar JTable: Editor de celdas.
          
            tcmAux.getColumn(INT_TBL1_BUTITM).setCellEditor(objTblCelEdiButGenDG);
            objTblCelEdiButGenDG.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat1.getSelectedRow();
                    intColSel=tblDat1.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                          case INT_TBL1_BUTITM:

                              if (objTblMod1.getValueAt(intFilSel, INT_TBL1_CONCONF).toString().equals("true") )
                                objTblCelEdiButGenDG.setCancelarEdicion(true);
                         break;


                       }
                     }
                    }
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel=tblDat1.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                          case INT_TBL1_BUTITM:
                          if (objTblMod1.getValueAt(intFilSel, INT_TBL1_CONCONF).toString().equals("false") ){
                             llamarPant( objTblMod1.getValueAt(intFilSel, INT_TBL1_CODEMP).toString()
                                    ,objTblMod1.getValueAt(intFilSel, INT_TBL1_CODLOC).toString()
                                    ,objTblMod1.getValueAt(intFilSel, INT_TBL1_CODTID).toString()
                                    ,objTblMod1.getValueAt(intFilSel, INT_TBL1_CODDOC).toString()
                                 );
                          }
                           break;

                        }
                }}
           });


    //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat1);



     ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat1.getTableHeader();
     objTblHeaGrp.setHeight(16*2);

     ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" DOCUMENTOS QUE SI SE CONFIRMAN " );
     objTblHeaColGrpAmeSur.setHeight(16);

            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL1_CODEMP));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL1_CODLOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL1_NOMLOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL1_CODTID));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL1_DETIDC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL1_DETIDL));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL1_CODDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL1_NUMDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL1_FECDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL1_CODCPR));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL1_NOMCPR));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL1_PESDOC));
            
       objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
       objTblHeaColGrpAmeSur=null;

       objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" DATOS DE LA CONFIRMACION " );
       objTblHeaColGrpAmeSur.setHeight(16);

            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL1_PENCONF));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL1_PARCONF));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL1_CONCONF));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL1_PESCONF));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL1_BUTITM));

         objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
         objTblHeaColGrpAmeSur=null;



    objTblMod1.setModoOperacion(objTblMod1.INT_TBL_EDI);
    tcmAux=null;

    int intCol[]={ INT_TBL1_PESDOC,  INT_TBL1_PESCONF };
    objTblTotDoc=new ZafTblTot(spnDoc, spnDoc , tblDat1, tblTotDoc, intCol);


     return blnres;
}


private void llamarPant(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc){

   Compras.ZafCom50.ZafCom50_01 obj = new Compras.ZafCom50.ZafCom50_01(objParSis, Integer.parseInt( strCodEmp) , Integer.parseInt( strCodLoc) ,  Integer.parseInt( strCodTipDoc)  , Integer.parseInt( strCodDoc)  );
   this.getParent().add(obj,javax.swing.JLayeredPane.DEFAULT_LAYER);
   obj.show();

}


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFilCab = new javax.swing.JPanel();
        lblBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBusBod = new javax.swing.JButton();
        panRepot = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat1 = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        chkbMosDoc = new javax.swing.JCheckBox();
        spnDoc = new javax.swing.JScrollPane();
        tblTotDoc = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        jPanel2 = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butVisPre = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
        butCer2 = new javax.swing.JButton();
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

        panFilCab.setLayout(null);

        lblBod.setText("Bodega destino:");
        panFilCab.add(lblBod);
        lblBod.setBounds(10, 90, 110, 20);

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
        txtCodBod.setBounds(130, 90, 70, 20);

        txtNomBod.setBackground(objParSis.getColorCamposObligatorios());
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
        panFilCab.add(txtNomBod);
        txtNomBod.setBounds(200, 90, 230, 20);

        butBusBod.setText("jButton2");
        butBusBod.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusBodActionPerformed(evt);
            }
        });
        panFilCab.add(butBusBod);
        butBusBod.setBounds(430, 90, 20, 20);

        tabFrm.addTab("General", panFilCab);

        panRepot.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(120);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());

        tblDat1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblDat1);

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        chkbMosDoc.setText("Mostrar los documento");
        chkbMosDoc.setPreferredSize(new java.awt.Dimension(269, 20));
        chkbMosDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkbMosDocActionPerformed(evt);
            }
        });
        jPanel5.add(chkbMosDoc, java.awt.BorderLayout.NORTH);

        spnDoc.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTotDoc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDoc.setViewportView(tblTotDoc);

        jPanel5.add(spnDoc, java.awt.BorderLayout.SOUTH);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel4);

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
        jScrollPane4.setViewportView(tblDat);

        jSplitPane1.setLeftComponent(jScrollPane4);

        panRepot.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRepot);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

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

        butVisPre.setText("Vista Previa");
        butVisPre.setToolTipText("Vista Previa");
        butVisPre.setPreferredSize(new java.awt.Dimension(92, 25));
        butVisPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVisPreActionPerformed(evt);
            }
        });
        panBot.add(butVisPre);

        butImp.setText("Imprimir");
        butImp.setToolTipText("Imprimir");
        butImp.setPreferredSize(new java.awt.Dimension(92, 25));
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panBot.add(butImp);

        butCer2.setText("Cerrar");
        butCer2.setToolTipText("Cierra la ventana.");
        butCer2.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCer2ActionPerformed(evt);
            }
        });
        panBot.add(butCer2);

        jPanel2.add(panBot, java.awt.BorderLayout.CENTER);

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

        jPanel2.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").



        if (butCon.getText().equals("Consultar")) {

            if (objThrGUI==null) {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }
        }
    }//GEN-LAST:event_butConActionPerformed




private boolean validarDat(){
  boolean blnRes=true;
 
  if(txtCodBod.getText().equals("")){
     MensajeInf("LA BODEGA DE DESTINO ES OBLIGATORIO...");
     txtCodBod.requestFocus();
     return false;
  }



 return blnRes;
}

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

        if(validarDat()){

             if (!cargarDetReg())
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
         }

         objThrGUI=null;

        }
    }




/**
* Esta función permite consultar los registros de acuerdo al criterio seleccionado.
* @return true: Si se pudo consultar los registros.
* <BR>false: En el caso contrario.
*/
private boolean cargarDetReg(){
 boolean blnRes=true;
 java.sql.Connection conn;
 java.sql.Statement stm;
 java.sql.ResultSet rst;
 int intNumTotReg=0, i=0;
 String strSql="", strSqlAuxFec="";
 
 try{
    butCon.setText("Detener");
    lblMsgSis.setText("Obteniendo datos...");

    conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());


    if (conn!=null){
        stm=conn.createStatement();
      //Obtener la condición.
  

        if(objSelFec.isCheckBoxChecked() ){
          switch (objSelFec.getTipoSeleccion())
          {
                    case 0: //Búsqueda por rangos
                        strSqlAuxFec+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strSqlAuxFec+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strSqlAuxFec+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
        }}


/**********************************INGRESOS *****************************************************/
 strSql=" select *,  (( pesoconf / pestotdoc) *100 ) as porcumpli from ( " +
 " select ( " +
 " select count(*)  from ( " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabmovinv as a " +
" inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a1.co_emp AND a6.co_bod=a1.co_bod) " +
" where  a.st_reg not in ('E','I') "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+" ) " +
" AND  ( a.co_locrelsoldevven IS NULL OR a.co_locrelsoldevven=0 ) " +
" AND  ( a.co_tipdocrelsoldevven IS NULL OR a.co_tipdocrelsoldevven=0 ) " +
" AND ( a.co_docrelsoldevven IS NULL OR a.co_docrelsoldevven=0 ) " +
" AND  a1.nd_can > 0  AND a.st_conInv IN('P','E','C') and a1.st_meringegrfisbod='S' " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" ) as x ) as cantotdoc, " +
" (" +
" select sum(kgramo)  from ( " +
" select sum(( b.nd_pesitmkgr*abs(a1.nd_can))) as kgramo   from tbm_cabmovinv as a " +
" inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a1.co_emp AND a6.co_bod=a1.co_bod) " +
" INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm) " +
" where a.st_reg not in ('E','I')   "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" AND  ( a.co_locrelsoldevven IS NULL OR a.co_locrelsoldevven=0 ) " +
" AND  ( a.co_tipdocrelsoldevven IS NULL OR a.co_tipdocrelsoldevven=0 ) " +
" AND ( a.co_docrelsoldevven IS NULL OR a.co_docrelsoldevven=0 ) " +
" AND  a1.nd_can > 0  AND a.st_conInv IN('P','E','C') and a1.st_meringegrfisbod='S' " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" ) as x  ) as pestotdoc, " +
" (" +
" select count(*)  from ( " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabmovinv as a " +
" inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a1.co_emp AND a6.co_bod=a1.co_bod) " +
" where a.st_reg not in ('E','I')  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" AND  ( a.co_locrelsoldevven IS NULL OR a.co_locrelsoldevven=0 ) " +
" AND  ( a.co_tipdocrelsoldevven IS NULL OR a.co_tipdocrelsoldevven=0 ) " +
" AND ( a.co_docrelsoldevven IS NULL OR a.co_docrelsoldevven=0 ) " +
" AND  a1.nd_can > 0  AND a.st_conInv IN('P') and a1.st_meringegrfisbod='S' " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" ) as x ) as penconf, " +
" (" +
" select count(*)  from ( " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabmovinv as a " +
" inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a1.co_emp AND a6.co_bod=a1.co_bod) " +
" where a.st_reg not in ('E','I')  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" AND  ( a.co_locrelsoldevven IS NULL OR a.co_locrelsoldevven=0 ) " +
" AND  ( a.co_tipdocrelsoldevven IS NULL OR a.co_tipdocrelsoldevven=0 ) " +
" AND ( a.co_docrelsoldevven IS NULL OR a.co_docrelsoldevven=0 ) " +
" AND  a1.nd_can > 0  AND a.st_conInv IN('E') and a1.st_meringegrfisbod='S' " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" ) as x  ) as parconf, " +
" ( " +
" select count(*)  from ( " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabmovinv as a " +
" inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a1.co_emp AND a6.co_bod=a1.co_bod) " +
" where  a.st_reg not in ('E','I')  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" AND  ( a.co_locrelsoldevven IS NULL OR a.co_locrelsoldevven=0 ) " +
" AND  ( a.co_tipdocrelsoldevven IS NULL OR a.co_tipdocrelsoldevven=0 ) " +
" AND ( a.co_docrelsoldevven IS NULL OR a.co_docrelsoldevven=0 ) " +
" AND  a1.nd_can > 0  AND a.st_conInv IN('C') and a1.st_meringegrfisbod='S' " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" ) as x  ) as conftot, " +
" ( " +
" select sum(kgramo)  from ( " +
" select sum(( b.nd_pesitmkgr*abs( ( a1.nd_cancon  )  ))) as kgramo   from tbm_cabmovinv as a " +
" inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a1.co_emp AND a6.co_bod=a1.co_bod) " +
" INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm) " +
" where  a.st_reg not in ('E','I')  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" AND  ( a.co_locrelsoldevven IS NULL OR a.co_locrelsoldevven=0 ) " +
" AND  ( a.co_tipdocrelsoldevven IS NULL OR a.co_tipdocrelsoldevven=0 ) " +
" AND ( a.co_docrelsoldevven IS NULL OR a.co_docrelsoldevven=0 ) " +
" AND  a1.nd_can > 0  AND a.st_conInv IN('P','E','C') and a1.st_meringegrfisbod='S' " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" ) as x " +
" ) as pesoconf " +
" ) as x " +
" union all " +

/*******************************EGRESOS*************************************************/

" select *,  (( pesoconf / pestotdoc) *100 ) as porcumpli from (" +
" select" +
" ( select count(*) from (" +
" select * from (" +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc  from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='N' and a.ne_numdoc >= 1  and a.st_coninv not in ('F') and st_meregrfisbod='S' " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" union all " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='S' and a.st_cretodguisec='N' and a.ne_numdoc >= 1 and a.st_coninv not in ('F') and a1.st_meregrfisbod='S' " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" union all " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='S' and a.st_tieguisec='N' and ne_numdoc >= 1 and a.st_coninv not in ('F') and a1.st_meregrfisbod='S' " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numdoc " +
" ) as x " +
" group by  co_emp, co_loc, co_tipdoc, co_doc " +
" ) as x  ) as canttotDoc, " +
" ( select sum(kgramo)  from ( " +
" select sum(( b.nd_pesitmkgr*abs(a1.nd_can))) as kgramo   from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm) " +
" where a.co_tipdoc in ( 101,102 )   "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='N' and a.ne_numdoc >= 1  and a.st_coninv not in ('F') and a1.st_meregrfisbod='S' " +
" union all " +
" select  sum(( b.nd_pesitmkgr*abs( ( a1.nd_can - a1.nd_cantotguisec ) ))) as kgramo   from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='S' and a.st_cretodguisec='N' and a.ne_numdoc >= 1 and a.st_coninv not in ('F') " +
" union all " +
" select sum(( b.nd_pesitmkgr*abs(a1.nd_can))) as kgramo  from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='S' and a.st_tieguisec='N' and a.ne_numdoc >= 1 and a.st_coninv not in ('F') and a1.st_meregrfisbod='S'  " +
" ) as x  ) as pestotdoc, " +
" ( " +
" select count(*) from ( " +
" select * from ( " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc  from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='N' and a.ne_numdoc >= 1  and a.st_coninv = 'P' and a1.st_meregrfisbod='S'  " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" union all " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='S' and a.st_cretodguisec='N' and a.ne_numdoc >= 1 and a.st_coninv = 'P'  and a1.st_meregrfisbod='S'  " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" union all " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='S' and a.st_tieguisec='N' and ne_numdoc >= 1 and a.st_coninv = 'P'  and a1.st_meregrfisbod='S'  " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numdoc " +
" ) as x " +
" group by  co_emp, co_loc, co_tipdoc, co_doc " +
" ) as x  ) as  penconf, " +
" ( " +
" select count(*) from ( " +
" select * from ( " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc  from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='N' and a.ne_numdoc >= 1  and a.st_coninv = 'E'  and a1.st_meregrfisbod='S'  " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" union all " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='S' and a.st_cretodguisec='N' and a.ne_numdoc >= 1 and a.st_coninv = 'E'  and a1.st_meregrfisbod='S'  " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" union all " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='S' and a.st_tieguisec='N' and ne_numdoc >= 1 and a.st_coninv = 'E'  and a1.st_meregrfisbod='S'  " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numdoc " +
" ) as x " +
" group by  co_emp, co_loc, co_tipdoc, co_doc " +
" ) as x  ) as  parconf, " +
" ( " +
" select count(*) from ( " +
" select * from ( " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc  from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='N' and a.ne_numdoc >= 1  and a.st_coninv = 'C'  and a1.st_meregrfisbod='S'  " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" union all " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='S' and a.st_cretodguisec='N' and a.ne_numdoc >= 1 and a.st_coninv = 'C'  and a1.st_meregrfisbod='S'  " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" union all " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='S' and a.st_tieguisec='N' and ne_numdoc >= 1 and a.st_coninv = 'C'  and a1.st_meregrfisbod='S'  " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numdoc " +
" ) as x " +
" group by  co_emp, co_loc, co_tipdoc, co_doc " +
" ) as x  ) as  conftot, " +
" ( " +
" select sum(kgramo)  from ( " +
" select sum(( b.nd_pesitmkgr*abs( ( a1.nd_cancon  ) ))) as kgramo   from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='N' and a.ne_numdoc >= 1  and a.st_coninv not in ('F')  and a1.st_meregrfisbod='S'  " +
" union all " +
" select  sum(( b.nd_pesitmkgr*abs( ( a1.nd_cancon  ) ))) as kgramo   from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='S' and a.st_cretodguisec='N' and a.ne_numdoc >= 1 and a.st_coninv not in ('F')   and a1.st_meregrfisbod='S'  " +
" union all " +
" select sum(( b.nd_pesitmkgr*abs( ( a1.nd_cancon  ) ))) as kgramo from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+" ) " +
" and a.st_tipguirem='S' and a.st_tieguisec='N' and a.ne_numdoc >= 1 and a.st_coninv not in ('F')  and a1.st_meregrfisbod='S'  " +
" ) as x " +
" ) as pesoconf " +
" ) as x  ";

        System.out.println("-->"+ strSql );
        rst=stm.executeQuery(strSql);
        vecDat.clear();
        lblMsgSis.setText("Cargando datos...");
        pgrSis.setMinimum(0);
        pgrSis.setMaximum(intNumTotReg);
        pgrSis.setValue(0);
        i=0;
        while (rst.next())
        {

            vecReg=new Vector();
            vecReg.add(INT_TBL_LINEA,"");

            if(i==0) vecReg.add(INT_TBL_DESCRI, "INGRESO" );
            else  vecReg.add(INT_TBL_DESCRI, "EGRESO" );

            vecReg.add(INT_TBL_TOTDOC,  rst.getString("cantotdoc"));
            vecReg.add(INT_TBL_PESTOT,  rst.getString("pestotdoc"));
            vecReg.add(INT_TBL_PENDIE,  rst.getString("penconf"));
            vecReg.add(INT_TBL_PARCIA,  rst.getString("parconf"));
            vecReg.add(INT_TBL_TOTCON,  rst.getString("conftot"));
            vecReg.add(INT_TBL_PESCON,  rst.getString("pesoconf"));
            vecReg.add(INT_TBL_CUMPLI,  rst.getString("porcumpli"));

           vecDat.add(vecReg);
           i++;
           pgrSis.setValue(i);
     }
    rst.close();
    rst=null;

     //Asignar vectores al modelo.
    objTblMod.setData(vecDat);
    tblDat.setModel(objTblMod);
    vecDat.clear();


    stm.close();
    stm=null;


    lblMsgSis.setText("Se encontraron "+tblDat.getRowCount()+" registros.");

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






    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed

       if(validarDat()){
         cargarRepote(1);
       }

}//GEN-LAST:event_butVisPreActionPerformed



private void cargarRepote(int intTipo){
   if (objThrGUIRpt==null)
    {
        objThrGUIRpt=new ZafThreadGUIRtp();
        objThrGUIRpt.setIndFunEje(intTipo);
        objThrGUIRpt.start();
    }
}




private void cerrarVen(){
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
    System.gc();
    dispose();
}}




    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

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
  }}
 }

 
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

    private void chkbMosDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkbMosDocActionPerformed
        // TODO add your handling code here:

        if (chkbMosDoc.isSelected()){

            objTblMod1.removeAllRows();

            cargarMovDoc();

        }
    }//GEN-LAST:event_chkbMosDocActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
         cerrarVen();
    }//GEN-LAST:event_formInternalFrameClosing

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        // TODO add your handling code here:
      if(validarDat()){
        cargarRepote(0);
      }
    }//GEN-LAST:event_butImpActionPerformed

    private void butCer2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCer2ActionPerformed
        // TODO add your handling code here:
         cerrarVen();
    }//GEN-LAST:event_butCer2ActionPerformed


 

      private boolean cargarMovDoc(){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSqlAuxFec="";
        String sql = "";
        try
        {
            if (tblDat.getSelectedRow()!=-1)
            {
                java.sql.Connection con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {

                    stmLoc=con.createStatement();

                if(objSelFec.isCheckBoxChecked() ){
                  switch (objSelFec.getTipoSeleccion())
                  {
                            case 0: //Búsqueda por rangos
                                strSqlAuxFec+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                            case 1: //Fechas menores o iguales que "Hasta".
                                strSqlAuxFec+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                            case 2: //Fechas mayores o iguales que "Desde".
                                strSqlAuxFec+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                            case 3: //Todo.
                                break;
                }}


                   if( objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DESCRI).toString().equals("INGRESO") ){


                    sql =" select  *   from ( " +
                    " select a.co_emp, a.co_loc, a7.tx_nom, a.co_tipdoc, a8.tx_descor, a8.tx_deslar, a.co_doc, a.ne_numdoc, a.fe_doc, a.co_cli, a.tx_nomcli,  a.st_coninv " +
                    " ,sum(( b.nd_pesitmkgr*abs(a1.nd_can))) as kgramodoc, sum(( b.nd_pesitmkgr*abs(a1.nd_cancon))) as kgramoconf " +
                    " from tbm_cabmovinv as a  " +
                    " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  " +
                    " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a1.co_emp AND a6.co_bod=a1.co_bod) " +
                    " INNER JOIN tbm_loc AS a7 ON (a7.co_emp=a.co_emp AND a7.co_loc=a.co_loc) " +
                    " INNER JOIN tbm_cabtipdoc AS a8 ON (a8.co_emp=a.co_emp AND a8.co_loc=a.co_loc and a8.co_tipdoc=a.co_tipdoc) " +
                    " INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm)  " +
                    " where  a.st_reg not in ('E','I')    " +strSqlAuxFec+" "+
                    " AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
                    " AND  ( a.co_locrelsoldevven IS NULL OR a.co_locrelsoldevven=0 ) " +
                    " AND  ( a.co_tipdocrelsoldevven IS NULL OR a.co_tipdocrelsoldevven=0 ) " +
                    " AND ( a.co_docrelsoldevven IS NULL OR a.co_docrelsoldevven=0 ) " +
                    " AND  a1.nd_can > 0  AND a.st_conInv IN('P','E','C') and a1.st_meringegrfisbod='S'	 " +
                    " group by  a.co_emp, a.co_loc, a7.tx_nom, a.co_tipdoc, a8.tx_descor, a8.tx_deslar, a.co_doc, a.ne_numdoc, a.fe_doc, a.co_cli, a.tx_nomcli,  a.st_coninv " +
                    " ) as x  order by fe_doc, ne_numdoc  ";

                   }else{

                        sql =" select a.co_emp, a.co_loc, a7.tx_nom, a.co_tipdoc, a8.tx_descor, a8.tx_deslar,  a.co_doc, a1.ne_numdoc, a1.fe_doc, a1.co_clides as co_Cli, a1.tx_nomclides as tx_nomcli, a.kgramodoc, a1.st_coninv , a.kgramoconf  from ( " +
                        " select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc ,sum(( b.nd_pesitmkgr*abs(a1.nd_can))) as kgramodoc, sum(( b.nd_pesitmkgr*abs(a1.nd_cancon))) as kgramoconf " +
                        " from tbm_cabguirem as a " +
                        " inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  " +
                        " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
                        " INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm)  " +
                        " where a.co_tipdoc in ( 101,102 )  " +strSqlAuxFec+" "+
                        " AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
                        " and a.st_tipguirem='P' and a.st_tieguisec='N' and a.ne_numdoc >= 1  and a.st_coninv not in ('F')  and a1.st_meregrfisbod='S'  " +
                        " group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
                        " union all " +
                        " select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc ,sum(( b.nd_pesitmkgr*abs(( a1.nd_can - a1.nd_cantotguisec )))) as kgramodoc, sum(( b.nd_pesitmkgr*abs(a1.nd_cancon))) as kgramoconf " +
                        " from tbm_cabguirem as a " +
                        " inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  " +
                        " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
                        " INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm)  " +
                        " where a.co_tipdoc in ( 101,102 )  " +strSqlAuxFec+" "+
                        " AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
                        " and a.st_tipguirem='P' and a.st_tieguisec='S' and a.st_cretodguisec='N' and a.ne_numdoc >= 1 and a.st_coninv not in ('F')  and a1.st_meregrfisbod='S'  " +
                        " group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
                        " union all " +
                        " select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc ,sum(( b.nd_pesitmkgr*abs(a1.nd_can))) as kgramodoc, sum(( b.nd_pesitmkgr*abs(a1.nd_cancon))) as kgramoconf " +
                        " from tbm_cabguirem as a  " +
                        " inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  " +
                        " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
                        " INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm)  " +
                        " where a.co_tipdoc in ( 101,102 )  " +strSqlAuxFec+" "+
                        " AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
                        " and a.st_tipguirem='S' and a.st_tieguisec='N' and ne_numdoc >= 1 and a.st_coninv not in ('F')  and a1.st_meregrfisbod='S'  " +
                        " group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numdoc " +
                        " ) as a  " +
                        " inner join tbm_cabguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  " +
                        " INNER JOIN tbm_loc AS a7 ON (a7.co_emp=a.co_emp AND a7.co_loc=a.co_loc) " +
                        " INNER JOIN tbm_cabtipdoc AS a8 ON (a8.co_emp=a.co_emp AND a8.co_loc=a.co_loc and a8.co_tipdoc=a.co_tipdoc) " +
                        "    order by a1.fe_doc, a1.ne_numdoc  ";


                   }
                   
                    rstLoc=stmLoc.executeQuery(sql);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    while (rstLoc.next())
                        {

                                vecReg=new Vector();
                                    vecReg.add(INT_TBL1_LINEA,"");
                                    vecReg.add(INT_TBL1_CODEMP, rstLoc.getString("co_emp") );
                                    vecReg.add(INT_TBL1_CODLOC, rstLoc.getString("co_loc") );
                                    vecReg.add(INT_TBL1_NOMLOC, rstLoc.getString("tx_nom") );
                                    vecReg.add(INT_TBL1_CODTID, rstLoc.getString("co_tipdoc") );
                                    vecReg.add(INT_TBL1_DETIDC, rstLoc.getString("tx_descor") );
                                    vecReg.add(INT_TBL1_DETIDL, rstLoc.getString("tx_deslar") );
                                    vecReg.add(INT_TBL1_CODDOC, rstLoc.getString("co_Doc") );
                                    vecReg.add(INT_TBL1_NUMDOC, rstLoc.getString("ne_numdoc") );
                                    vecReg.add(INT_TBL1_FECDOC, rstLoc.getString("fe_doc") );
                                    vecReg.add(INT_TBL1_CODCPR, rstLoc.getString("co_Cli") );
                                    vecReg.add(INT_TBL1_NOMCPR, rstLoc.getString("tx_nomcli") );
                                    vecReg.add(INT_TBL1_PESDOC, rstLoc.getString("kgramodoc") );
                                    vecReg.add(INT_TBL1_PENCONF, new Boolean(rstLoc.getString("st_coninv").equals("P")?true:false) );
				    vecReg.add(INT_TBL1_PARCONF, new Boolean(rstLoc.getString("st_coninv").equals("E")?true:false) );
	                            vecReg.add(INT_TBL1_CONCONF, new Boolean(rstLoc.getString("st_coninv").equals("C")?true:false) );
	                            vecReg.add(INT_TBL1_PESCONF, rstLoc.getString("kgramoconf") );
                                    vecReg.add(INT_TBL1_BUTITM, "" );

                                vecDat.add(vecReg);

                        }
                     objTblMod1.setData(vecDat);
                     tblDat1.setModel(objTblMod1);
                     vecDat.clear();

                    objTblTotDoc.calcularTotales();
                    

                    rstLoc.close();
                    stmLoc.close();
                    con.close();
                    rstLoc=null;
                    stmLoc=null;
                    con=null;



                }
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



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBusBod;
    private javax.swing.JButton butCer2;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butVisPre;
    private javax.swing.JCheckBox chkbMosDoc;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFilCab;
    private javax.swing.JPanel panRepot;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDoc;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDat1;
    private javax.swing.JTable tblTotDoc;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtNomBod;
    // End of variables declaration//GEN-END:variables



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
            int intMax=lsm.getMaxSelectionIndex();
            String strAux;
            if (!lsm.isSelectionEmpty())
            {

                if (chkbMosDoc.isSelected()){

                    objTblMod1.removeAllRows();

                    cargarMovDoc();

                }

            }
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
            case INT_TBL_DESCRI:
            strMsg="Descripción.";
            break;
        case INT_TBL_TOTDOC:
            strMsg="Cantidad total de documentos.";
            break;
        case INT_TBL_PESTOT:
            strMsg="Peso (Kg).";
            break;
        case INT_TBL_PENDIE:
            strMsg="Cantidad total de documentos pendientes.";
            break;
        case INT_TBL_PARCIA:
            strMsg="Cantidad total de documentos confirmando parcialmente.";
            break;

       case INT_TBL_TOTCON:
            strMsg="Cantidad total de documentos confirmando.";
            break;

       case INT_TBL_PESCON:
            strMsg="Peso total de documentos confirmandos.";
            break;

       case INT_TBL_CUMPLI:
            strMsg="Porcentaje de cumplimiento.";
            break;


        default:
            strMsg="";
            break;
    }
    tblDat.getTableHeader().setToolTipText(strMsg);
}
}





private class ZafMouMotAda1 extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblDat1.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL1_LINEA:
            strMsg="";
            break;
            case INT_TBL1_CODEMP:
            strMsg="Código de empresa.";
            break;
        case INT_TBL1_CODLOC:
            strMsg="Código de local.";
            break;
        case INT_TBL1_NOMLOC:
            strMsg="Nombre del local ";
            break;
        case INT_TBL1_CODTID:
            strMsg="Código de tipo de documento.";
            break;
        case INT_TBL1_DETIDC:
            strMsg="Descripción corta del tipo documento.";
            break;

       case INT_TBL1_DETIDL:
            strMsg="Descripción larga del tipo documento.";
            break;

       case INT_TBL1_CODDOC:
            strMsg="Código de documento.";
            break;

       case INT_TBL1_NUMDOC:
            strMsg="Numero de documento.";
            break;

            case INT_TBL1_FECDOC:
            strMsg="Fecha de documento.";
            break;

            case INT_TBL1_CODCPR:
            strMsg="Código de cliente/proveedor.";
            break;

            case INT_TBL1_NOMCPR:
            strMsg="Nombre del cliente/proveedor .";
            break;

            case INT_TBL1_PESDOC:
            strMsg="Peso (Kg) .";
            break;

            case INT_TBL1_PENCONF:
            strMsg="Pendiente de confirmar.";
            break;

            case INT_TBL1_PARCONF:
            strMsg="Confirmación parcial .";
            break;

            case INT_TBL1_CONCONF:
            strMsg="Confirmación total .";
            break;

            case INT_TBL1_PESCONF:
            strMsg="Peso (kg) confirmado .";
            break;

             case INT_TBL1_BUTITM:
            strMsg="Items que estan pendiente de confirmar .";
            break;

        default:
            strMsg="";
            break;
    }
    tblDat1.getTableHeader().setToolTipText(strMsg);
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
    private class ZafThreadGUIRtp extends Thread
    {
        private int intIndFun;

        public ZafThreadGUIRtp()
        {
            intIndFun=0;
        }

        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                    butImp.setEnabled(false);
                    generarRpt(1);
                    butImp.setEnabled(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                    butVisPre.setEnabled(false);
                    generarRpt(2);
                    butVisPre.setEnabled(true);
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
        String strSqlAuxFec="", strSql="";
        try
        {
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
            

        if(objSelFec.isCheckBoxChecked() ){
          switch (objSelFec.getTipoSeleccion())
          {
                    case 0: //Búsqueda por rangos
                        strSqlAuxFec+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strSqlAuxFec+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strSqlAuxFec+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
        }}


/**********************************INGRESOS *****************************************************/
 strSql=" select *,  (( pesoconf / pestotdoc) *100 ) as porcumpli from ( " +
 " select text('INGRESOS') as descrip, ( " +
 " select count(*)  from ( " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabmovinv as a " +
" inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a1.co_emp AND a6.co_bod=a1.co_bod) " +
" where  a.st_reg not in ('E','I') "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+" ) " +
" AND  ( a.co_locrelsoldevven IS NULL OR a.co_locrelsoldevven=0 ) " +
" AND  ( a.co_tipdocrelsoldevven IS NULL OR a.co_tipdocrelsoldevven=0 ) " +
" AND ( a.co_docrelsoldevven IS NULL OR a.co_docrelsoldevven=0 ) " +
" AND  a1.nd_can > 0  AND a.st_conInv IN('P','E','C') and a1.st_meringegrfisbod='S' " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" ) as x ) as cantotdoc, " +
" (" +
" select sum(kgramo)  from ( " +
" select sum(( b.nd_pesitmkgr*abs(a1.nd_can))) as kgramo   from tbm_cabmovinv as a " +
" inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a1.co_emp AND a6.co_bod=a1.co_bod) " +
" INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm) " +
" where a.st_reg not in ('E','I')   "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" AND  ( a.co_locrelsoldevven IS NULL OR a.co_locrelsoldevven=0 ) " +
" AND  ( a.co_tipdocrelsoldevven IS NULL OR a.co_tipdocrelsoldevven=0 ) " +
" AND ( a.co_docrelsoldevven IS NULL OR a.co_docrelsoldevven=0 ) " +
" AND  a1.nd_can > 0  AND a.st_conInv IN('P','E','C') and a1.st_meringegrfisbod='S' " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" ) as x  ) as pestotdoc, " +
" (" +
" select count(*)  from ( " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabmovinv as a " +
" inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a1.co_emp AND a6.co_bod=a1.co_bod) " +
" where a.st_reg not in ('E','I')  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" AND  ( a.co_locrelsoldevven IS NULL OR a.co_locrelsoldevven=0 ) " +
" AND  ( a.co_tipdocrelsoldevven IS NULL OR a.co_tipdocrelsoldevven=0 ) " +
" AND ( a.co_docrelsoldevven IS NULL OR a.co_docrelsoldevven=0 ) " +
" AND  a1.nd_can > 0  AND a.st_conInv IN('P') and a1.st_meringegrfisbod='S' " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" ) as x ) as penconf, " +
" (" +
" select count(*)  from ( " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabmovinv as a " +
" inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a1.co_emp AND a6.co_bod=a1.co_bod) " +
" where a.st_reg not in ('E','I')  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" AND  ( a.co_locrelsoldevven IS NULL OR a.co_locrelsoldevven=0 ) " +
" AND  ( a.co_tipdocrelsoldevven IS NULL OR a.co_tipdocrelsoldevven=0 ) " +
" AND ( a.co_docrelsoldevven IS NULL OR a.co_docrelsoldevven=0 ) " +
" AND  a1.nd_can > 0  AND a.st_conInv IN('E') and a1.st_meringegrfisbod='S' " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" ) as x  ) as parconf, " +
" ( " +
" select count(*)  from ( " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabmovinv as a " +
" inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a1.co_emp AND a6.co_bod=a1.co_bod) " +
" where  a.st_reg not in ('E','I')  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" AND  ( a.co_locrelsoldevven IS NULL OR a.co_locrelsoldevven=0 ) " +
" AND  ( a.co_tipdocrelsoldevven IS NULL OR a.co_tipdocrelsoldevven=0 ) " +
" AND ( a.co_docrelsoldevven IS NULL OR a.co_docrelsoldevven=0 ) " +
" AND  a1.nd_can > 0  AND a.st_conInv IN('C') and a1.st_meringegrfisbod='S' " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" ) as x  ) as conftot, " +
" ( " +
" select sum(kgramo)  from ( " +
" select sum(( b.nd_pesitmkgr*abs( ( a1.nd_cancon  )  ))) as kgramo   from tbm_cabmovinv as a " +
" inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a1.co_emp AND a6.co_bod=a1.co_bod) " +
" INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm) " +
" where  a.st_reg not in ('E','I')  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" AND  ( a.co_locrelsoldevven IS NULL OR a.co_locrelsoldevven=0 ) " +
" AND  ( a.co_tipdocrelsoldevven IS NULL OR a.co_tipdocrelsoldevven=0 ) " +
" AND ( a.co_docrelsoldevven IS NULL OR a.co_docrelsoldevven=0 ) " +
" AND  a1.nd_can > 0  AND a.st_conInv IN('P','E','C') and a1.st_meringegrfisbod='S' " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" ) as x " +
" ) as pesoconf " +
" ) as x " +
" union all " +

/*******************************EGRESOS*************************************************/

" select *,  (( pesoconf / pestotdoc) *100 ) as porcumpli from (" +
" select text('EGRESOS') as descrip, " +
" ( select count(*) from (" +
" select * from (" +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc  from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='N' and a.ne_numdoc >= 1  and a.st_coninv not in ('F') and st_meregrfisbod='S' " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" union all " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='S' and a.st_cretodguisec='N' and a.ne_numdoc >= 1 and a.st_coninv not in ('F') and a1.st_meregrfisbod='S' " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" union all " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='S' and a.st_tieguisec='N' and ne_numdoc >= 1 and a.st_coninv not in ('F') and a1.st_meregrfisbod='S' " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numdoc " +
" ) as x " +
" group by  co_emp, co_loc, co_tipdoc, co_doc " +
" ) as x  ) as canttotDoc, " +
" ( select sum(kgramo)  from ( " +
" select sum(( b.nd_pesitmkgr*abs(a1.nd_can))) as kgramo   from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm) " +
" where a.co_tipdoc in ( 101,102 )   "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='N' and a.ne_numdoc >= 1  and a.st_coninv not in ('F') and a1.st_meregrfisbod='S' " +
" union all " +
" select  sum(( b.nd_pesitmkgr*abs( ( a1.nd_can - a1.nd_cantotguisec ) ))) as kgramo   from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='S' and a.st_cretodguisec='N' and a.ne_numdoc >= 1 and a.st_coninv not in ('F') " +
" union all " +
" select sum(( b.nd_pesitmkgr*abs(a1.nd_can))) as kgramo  from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='S' and a.st_tieguisec='N' and a.ne_numdoc >= 1 and a.st_coninv not in ('F') and a1.st_meregrfisbod='S'  " +
" ) as x  ) as pestotdoc, " +
" ( " +
" select count(*) from ( " +
" select * from ( " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc  from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='N' and a.ne_numdoc >= 1  and a.st_coninv = 'P' and a1.st_meregrfisbod='S'  " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" union all " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='S' and a.st_cretodguisec='N' and a.ne_numdoc >= 1 and a.st_coninv = 'P'  and a1.st_meregrfisbod='S'  " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" union all " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='S' and a.st_tieguisec='N' and ne_numdoc >= 1 and a.st_coninv = 'P'  and a1.st_meregrfisbod='S'  " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numdoc " +
" ) as x " +
" group by  co_emp, co_loc, co_tipdoc, co_doc " +
" ) as x  ) as  penconf, " +
" ( " +
" select count(*) from ( " +
" select * from ( " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc  from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='N' and a.ne_numdoc >= 1  and a.st_coninv = 'E'  and a1.st_meregrfisbod='S'  " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" union all " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='S' and a.st_cretodguisec='N' and a.ne_numdoc >= 1 and a.st_coninv = 'E'  and a1.st_meregrfisbod='S'  " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" union all " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='S' and a.st_tieguisec='N' and ne_numdoc >= 1 and a.st_coninv = 'E'  and a1.st_meregrfisbod='S'  " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numdoc " +
" ) as x " +
" group by  co_emp, co_loc, co_tipdoc, co_doc " +
" ) as x  ) as  parconf, " +
" ( " +
" select count(*) from ( " +
" select * from ( " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc  from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='N' and a.ne_numdoc >= 1  and a.st_coninv = 'C'  and a1.st_meregrfisbod='S'  " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" union all " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='S' and a.st_cretodguisec='N' and a.ne_numdoc >= 1 and a.st_coninv = 'C'  and a1.st_meregrfisbod='S'  " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
" union all " +
" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='S' and a.st_tieguisec='N' and ne_numdoc >= 1 and a.st_coninv = 'C'  and a1.st_meregrfisbod='S'  " +
" group by  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numdoc " +
" ) as x " +
" group by  co_emp, co_loc, co_tipdoc, co_doc " +
" ) as x  ) as  conftot, " +
" ( " +
" select sum(kgramo)  from ( " +
" select sum(( b.nd_pesitmkgr*abs( ( a1.nd_cancon  ) ))) as kgramo   from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='N' and a.ne_numdoc >= 1  and a.st_coninv not in ('F')  and a1.st_meregrfisbod='S'  " +
" union all " +
" select  sum(( b.nd_pesitmkgr*abs( ( a1.nd_cancon  ) ))) as kgramo   from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+"  ) " +
" and a.st_tipguirem='P' and a.st_tieguisec='S' and a.st_cretodguisec='N' and a.ne_numdoc >= 1 and a.st_coninv not in ('F')   and a1.st_meregrfisbod='S'  " +
" union all " +
" select sum(( b.nd_pesitmkgr*abs( ( a1.nd_cancon  ) ))) as kgramo from tbm_cabguirem as a " +
" inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
" INNER JOIN TBM_INV as b on (b.co_emp=a1.co_emp and b.co_itm=a1.co_itm) " +
" where a.co_tipdoc in ( 101,102 )  "+strSqlAuxFec+" "+
" AND ( a6.co_empGrp=0 AND a6.co_bodGrp= "+txtCodBod.getText()+" ) " +
" and a.st_tipguirem='S' and a.st_tieguisec='N' and a.ne_numdoc >= 1 and a.st_coninv not in ('F')  and a1.st_meregrfisbod='S'  " +
" ) as x " +
" ) as pesoconf " +
" ) as x  ";

       // System.out.println("-->"+ strSql );


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
                                mapPar.put("sql", strSql );
                                
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
