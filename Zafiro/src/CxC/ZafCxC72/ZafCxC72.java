/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCom60.java
 *  
 * Created on Jan 10, 2011, 9:37:23 AM
 */

package CxC.ZafCxC72;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Date;
/**
 *
 * @author jayapata
 */
public class ZafCxC72 extends javax.swing.JInternalFrame {

    ZafParSis objParSis;
    ZafUtil objUti;
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
    ZafVenCon objVenConCli; //*****************
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButDG;
    private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGenDG;
    private ZafMouMotAda objMouMotAda;
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk, objTblCelEdiChkAut,  objTblCelEdiChkDen;
    private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt, objTblCelEdiTxtDiaSol;
    
    String strCodBod="", strNomBod="";
    String strVersion=" ver. 0.1 ";

    String strCodCli="";
    String strNomCli="";
    String strFormatoFecha="d/m/y";

       // TABLA DE CAMPOS2
    final int INT_TBL_LINEA  =0;
    final int INT_TBL_CODEMP =1;
    final int INT_TBL_CODLOC =2;
    final int INT_TBL_CODTID =3;
    final int INT_TBL_CODDOC =4;
    final int INT_TBL_CODREG =5;
    final int INT_TBL_CODCLI =6;
    final int INT_TBL_NOMCLI =7;
    final int INT_TBL_FECDOC =8;
    final int INT_TBL_NUMCHQ =9;
    final int INT_TBL_VALCHQ =10;
    final int INT_TBL_FECVEN =11;
    final int INT_TBL_CANSOL =12;
    final int INT_TBL_NDIAPO =13;
    final int INT_TBL_FECPOS =14;
    final int INT_TBL_TXTOBS =15;
    final int INT_TBL_BUTOBS =16;
    final int INT_TBL_AUTPOS =17;
    final int INT_TBL_DENPOS =18;
    final int INT_TBL_ADIPOS =19;
    final int INT_TBL_AFEPOS =20;
    final int INT_TBL_TXTOBA =21;
    final int INT_TBL_BUTOBA =22;
    final int INT_TBL_NTOTPO =23;
    final int INT_TBL_BUTPO1 =24;
    final int INT_TBL_BUTPO2= 25;
    final int INT_TBL_DIASOL_ORI =26;
    final int INT_TBL_CODPOS = 27;
    final int INT_TBL_DIAAUT_ORI =28;
    final int INT_TBL_ESTAUT_ORI =29;
  

    private Vector vecDat, vecReg;

    /** Creates new form ZafCom50 */
     public ZafCxC72(ZafParSis objZafParsis) {
         try{
        this.objParSis = (Librerias.ZafParSis.ZafParSis) objZafParsis.clone();
        objUti = new ZafUtil();


        objTblCelEdiButGenDG=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
        

        initComponents();

        
        vecDat=new Vector();


          txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
          txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
          txtFecDoc.setText("");
          panFilCab.add(txtFecDoc);
          txtFecDoc.setBounds(410, 33, 92, 20);


          if(objParSis.getCodigoMenu()==2526){
             chkNoSolPos.setSelected(true);
             chkSolPosAut.setSelected(true);
             chkSolPosCan.setSelected(true);
             chkSolPosDen.setSelected(true);
             chkSolPosPen.setSelected(true);
          }else{
             chkSolPosPen.setSelected(true);
          }

        

        this.setTitle(objParSis.getNombreMenu()+" "+ strVersion );
        lblTit.setText( objParSis.getNombreMenu() );
       }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }
    }


public void Configura_ventana_consulta(){

      configurarForm();
     
      configurarVenConClientes();


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
         " WHERE a.co_emp ="+objParSis.getCodigoEmpresa()+"  and a.st_reg IN('A')  and a.st_cli='S' ORDER BY a.tx_nom  ";

        if(!(objParSis.getCodigoUsuario()==1) ){

           if(!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
              strSQL = " SELECT a.co_cli, a.tx_ide, a.tx_nom FROM tbr_cliloc as b " +
              " inner join tbm_cli as a on (a.co_emp=b.co_emp and a.co_cli=b.co_cli) " +
              " WHERE  b.co_emp ="+objParSis.getCodigoEmpresa()+" and b.co_loc="+objParSis.getCodigoLocal()+" and  a.st_reg  in ('A') ORDER BY a.tx_nom ";
           }
        }

        objVenConCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
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




 private boolean configurarForm(){
  boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    vecCab.add(INT_TBL_LINEA,"");
    vecCab.add(INT_TBL_CODEMP,"Cód.Emp");
    vecCab.add(INT_TBL_CODLOC,"Cód.Loc");
    vecCab.add(INT_TBL_CODTID,"Cód.Tip.Doc");
    vecCab.add(INT_TBL_CODDOC,"Cód.Doc");
    vecCab.add(INT_TBL_CODREG,"Cód.Reg");
    vecCab.add(INT_TBL_CODCLI,"Cód.Cli");
    vecCab.add(INT_TBL_NOMCLI,"Cliente");
    vecCab.add(INT_TBL_FECDOC,"Fec.Doc");
    vecCab.add(INT_TBL_NUMCHQ,"Num.Chq");
    vecCab.add(INT_TBL_VALCHQ,"Val.Chq");
    vecCab.add(INT_TBL_FECVEN,"Fec.Ven");
    vecCab.add(INT_TBL_CANSOL,"Cancelar");
    vecCab.add(INT_TBL_NDIAPO,"Dias");
    vecCab.add(INT_TBL_FECPOS,"Fecha");
    vecCab.add(INT_TBL_TXTOBS,"Observacion");
    vecCab.add(INT_TBL_BUTOBS,"");
    vecCab.add(INT_TBL_AUTPOS,"Autorizacion");
    vecCab.add(INT_TBL_DENPOS,"Denegar");
    vecCab.add(INT_TBL_ADIPOS,"Dias");
    vecCab.add(INT_TBL_AFEPOS,"Fecha");
    vecCab.add(INT_TBL_TXTOBA,"Observacion");
    vecCab.add(INT_TBL_BUTOBA,"");
    vecCab.add(INT_TBL_NTOTPO,"Total");
    vecCab.add(INT_TBL_BUTPO1,"");
    vecCab.add(INT_TBL_BUTPO2,"");
    vecCab.add(INT_TBL_DIASOL_ORI,"");
    vecCab.add(INT_TBL_CODPOS,"");
    vecCab.add(INT_TBL_DIAAUT_ORI,"");
    vecCab.add(INT_TBL_ESTAUT_ORI,"");




    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);

     //Configurar JTable: Establecer tipo de selección.
    tblDat.setRowSelectionAllowed(true);
    tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

     //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
     objMouMotAda=new ZafMouMotAda();
     tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

     /* Aqui se agrega las columnas que van
             ha hacer ocultas
         * */
        ArrayList arlColHid=new ArrayList();
        arlColHid.add(""+INT_TBL_CODEMP);
        arlColHid.add(""+INT_TBL_CODLOC);
        arlColHid.add(""+INT_TBL_CODTID);
        arlColHid.add(""+INT_TBL_CODDOC);
        arlColHid.add(""+INT_TBL_CODREG);
        arlColHid.add(""+INT_TBL_FECDOC);

        arlColHid.add(""+INT_TBL_DIASOL_ORI);
        arlColHid.add(""+INT_TBL_CODPOS);
        arlColHid.add(""+INT_TBL_DIAAUT_ORI);
        arlColHid.add(""+INT_TBL_ESTAUT_ORI);


        objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;

        
    objTblMod.setColumnDataType(INT_TBL_VALCHQ, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_ADIPOS, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_NDIAPO, objTblMod.INT_COL_DBL, new Integer(0), null);



    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    tblDat.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(200);
    tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_NUMCHQ).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_VALCHQ).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_FECVEN).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_NDIAPO).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_FECPOS).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_TXTOBS).setPreferredWidth(120);
    tcmAux.getColumn(INT_TBL_BUTOBS).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_AUTPOS).setPreferredWidth(35);
    tcmAux.getColumn(INT_TBL_CANSOL).setPreferredWidth(35);
    tcmAux.getColumn(INT_TBL_DENPOS).setPreferredWidth(35);
    tcmAux.getColumn(INT_TBL_TXTOBA).setPreferredWidth(120);
    tcmAux.getColumn(INT_TBL_BUTOBA).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_ADIPOS).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_AFEPOS).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_NTOTPO).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_BUTPO1).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_BUTPO2).setPreferredWidth(25);


  

    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();

    if(objParSis.getCodigoMenu()==2526){
       vecAux.add("" + INT_TBL_CANSOL);
       vecAux.add("" + INT_TBL_NDIAPO);
       //vecAux.add("" + INT_TBL_FECPOS);
       vecAux.add("" + INT_TBL_TXTOBS);
       
    }else{
       vecAux.add("" + INT_TBL_AUTPOS);
       vecAux.add("" + INT_TBL_DENPOS);
       vecAux.add("" + INT_TBL_ADIPOS);
       //vecAux.add("" + INT_TBL_AFEPOS);
       vecAux.add("" + INT_TBL_TXTOBA);
      
    }


       vecAux.add("" + INT_TBL_BUTOBS);
       vecAux.add("" + INT_TBL_BUTOBA);
       vecAux.add("" + INT_TBL_BUTPO1);
       vecAux.add("" + INT_TBL_BUTPO2);




    objTblMod.setColumnasEditables(vecAux);
    vecAux=null;
    //Configurar JTable: Editor de la tabla.


    
  

    objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();//inincializo el renderizador
    objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);//alineacion del contenido de la celda
    objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);//formato de la celda, este es numero
    objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
    tcmAux.getColumn(INT_TBL_VALCHQ).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_NTOTPO).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_ADIPOS).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_NDIAPO).setCellRenderer(objTblCelRenLbl);
    objTblCelRenLbl=null;



     objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
     tcmAux.getColumn(INT_TBL_TXTOBS).setCellEditor(objTblCelEdiTxt);
     tcmAux.getColumn(INT_TBL_TXTOBA).setCellEditor(objTblCelEdiTxt);
     objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

              int intCol = tblDat.getSelectedRow();
//
//              if(tblDat.getSelectedColumn() == INT_TBL_NDIAPO ){
//
//              }else if(tblDat.getSelectedColumn() == INT_TBL_ADIPOS ){
//
//              }

      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
      }
      });

     objTblCelEdiTxt=null;


     

        objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_AUTPOS).setCellRenderer(objTblCelRenChk);
        tcmAux.getColumn(INT_TBL_CANSOL).setCellRenderer(objTblCelRenChk);
        tcmAux.getColumn(INT_TBL_DENPOS).setCellRenderer(objTblCelRenChk);
        objTblCelRenChk=null;


        objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_CANSOL).setCellEditor(objTblCelEdiChk);
        objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
              int intNunFil = tblDat.getSelectedRow();

             
            }
        });
       objTblCelEdiChk=null;


        objTblCelEdiChkAut = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_AUTPOS).setCellEditor(objTblCelEdiChkAut);
        objTblCelEdiChkAut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
              int intCol = tblDat.getSelectedRow();
              String strDias=( (tblDat.getValueAt(intCol, INT_TBL_NDIAPO)==null)?"":tblDat.getValueAt(intCol, INT_TBL_NDIAPO).toString() );
              String strCodPos = (tblDat.getValueAt(intCol, INT_TBL_CODPOS)==null?"":tblDat.getValueAt(intCol, INT_TBL_CODPOS).toString());
              String strEstCan = (tblDat.getValueAt(intCol, INT_TBL_CANSOL)==null?"":tblDat.getValueAt(intCol, INT_TBL_CANSOL).toString());

              if(strDias.equals("")){
                 objTblCelEdiChkAut.setCancelarEdicion(true);
              }

              if(!strCodPos.equals("")){
                if(strEstCan.equals("true"))
                  objTblCelEdiChkAut.setCancelarEdicion(true);
              }


            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               int intCol = tblDat.getSelectedRow();
               tblDat.setValueAt( new Boolean(false), intCol, INT_TBL_DENPOS);

               String strDias=( (tblDat.getValueAt(intCol, INT_TBL_NDIAPO)==null)?"0":tblDat.getValueAt(intCol, INT_TBL_NDIAPO).toString() );
               String strFec=( (tblDat.getValueAt(intCol, INT_TBL_FECPOS)==null)?"":tblDat.getValueAt(intCol, INT_TBL_FECPOS).toString() );

               tblDat.setValueAt( strDias,  intCol, INT_TBL_ADIPOS);
               tblDat.setValueAt( strFec ,  intCol, INT_TBL_AFEPOS);
           
            }
        });
        


        objTblCelEdiChkDen = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_DENPOS).setCellEditor(objTblCelEdiChkDen);
        objTblCelEdiChkDen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
              int intCol = tblDat.getSelectedRow();
              String strDias=( (tblDat.getValueAt(intCol, INT_TBL_NDIAPO)==null)?"":tblDat.getValueAt(intCol, INT_TBL_NDIAPO).toString() );
              String strCodPos = (tblDat.getValueAt(intCol, INT_TBL_CODPOS)==null?"":tblDat.getValueAt(intCol, INT_TBL_CODPOS).toString());
              String strEstCan = (tblDat.getValueAt(intCol, INT_TBL_CANSOL)==null?"":tblDat.getValueAt(intCol, INT_TBL_CANSOL).toString());

              if(strDias.equals("")){
                 objTblCelEdiChkDen.setCancelarEdicion(true);
              }

              if(!strCodPos.equals("")){
                if(strEstCan.equals("true"))
                  objTblCelEdiChkDen.setCancelarEdicion(true);
              }


            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
              int intCol = tblDat.getSelectedRow();
              tblDat.setValueAt( new Boolean(false), intCol, INT_TBL_AUTPOS);

             // tblDat.setValueAt( "0",  intCol, INT_TBL_ADIPOS);
             // tblDat.setValueAt( "",  intCol, INT_TBL_AFEPOS);

            }
        });
      



            objTblCelRenButDG=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTOBS).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_BUTOBA).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_BUTPO1).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_BUTPO2).setCellRenderer(objTblCelRenButDG);
            objTblCelRenButDG.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    switch (objTblCelRenButDG.getColumnRender())
                    {
                      case INT_TBL_BUTOBS:
                            objTblCelRenButDG.setText("...");
                       break;
                       case INT_TBL_BUTOBA:
                            objTblCelRenButDG.setText("...");
                       break;
                        case INT_TBL_BUTPO1:
                            objTblCelRenButDG.setText("...");
                       break;
                        case INT_TBL_BUTPO2:
                            objTblCelRenButDG.setText("...");
                       break;
                    }
                }
            });


          //Configurar JTable: Editor de celdas.
          
            tcmAux.getColumn(INT_TBL_BUTOBS).setCellEditor(objTblCelEdiButGenDG);
            tcmAux.getColumn(INT_TBL_BUTOBA).setCellEditor(objTblCelEdiButGenDG);
            tcmAux.getColumn(INT_TBL_BUTPO1).setCellEditor(objTblCelEdiButGenDG);
            tcmAux.getColumn(INT_TBL_BUTPO2).setCellEditor(objTblCelEdiButGenDG);
            objTblCelEdiButGenDG.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                          case INT_TBL_BUTOBS:
                          break;

                          case INT_TBL_BUTOBA:
                          break;

                          case INT_TBL_BUTPO1:
                          break;

                          case INT_TBL_BUTPO2:
                          break;

                       }
                     }
                    }
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel=tblDat.getSelectedColumn();
                    int intCol = tblDat.getSelectedRow();
                    boolean blnEstBut=false;
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                          case INT_TBL_BUTOBS:

                            
                            if(objParSis.getCodigoMenu()==2526) blnEstBut=true;
                            else blnEstBut=false;
                              
                            llamarVenObs( (objTblMod.getValueAt(intFilSel, INT_TBL_TXTOBS)==null?"":objTblMod.getValueAt(intFilSel, INT_TBL_TXTOBS).toString()) ,intFilSel, INT_TBL_TXTOBS, blnEstBut );

                           break;

                           case INT_TBL_BUTOBA:
                              
                               if(objParSis.getCodigoMenu()==2526) blnEstBut=false;
                               else blnEstBut=true;

                               llamarVenObs( (objTblMod.getValueAt(intFilSel, INT_TBL_TXTOBA)==null?"":objTblMod.getValueAt(intFilSel, INT_TBL_TXTOBA).toString()) ,intFilSel, INT_TBL_TXTOBA, blnEstBut );

                           break;

                           case INT_TBL_BUTPO1:



                               llamarListadoSolAnt( objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString()
                                       ,objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString()
                                       ,objTblMod.getValueAt(intFilSel, INT_TBL_CODTID).toString()
                                       ,objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
                                       ,objTblMod.getValueAt(intFilSel, INT_TBL_CODREG).toString()
                                       ,(objTblMod.getValueAt(intFilSel, INT_TBL_CODPOS)==null?"0":objTblMod.getValueAt(intFilSel, INT_TBL_CODPOS).toString())
                                       );


                           break;


                           case INT_TBL_BUTPO2:

                                 llamarPantHisSolPos( objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString()
                                       ,objTblMod.getValueAt(intFilSel, INT_TBL_CODCLI).toString() );

                           break;

                     

                        }
                }}
           });


    //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);



     ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
     objTblHeaGrp.setHeight(16*2);

     ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("   " );
     objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODCLI));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NOMCLI));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECDOC));
       objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
       objTblHeaColGrpAmeSur=null;

       objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("DATOS DEL CHEQUE ");
       objTblHeaColGrpAmeSur.setHeight(16);

            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NUMCHQ));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_VALCHQ));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECVEN));
          

         objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
         objTblHeaColGrpAmeSur=null;


         objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("SOLICITUD ");
         objTblHeaColGrpAmeSur.setHeight(16);


            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CANSOL));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NDIAPO));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECPOS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TXTOBS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTOBS));

         objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
         objTblHeaColGrpAmeSur=null;


         objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("AUTORIZACION ");
         objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_AUTPOS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DENPOS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_ADIPOS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_AFEPOS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TXTOBA));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTOBA));
         objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
         objTblHeaColGrpAmeSur=null;


         objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("POSTERGACIONES");
         objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NTOTPO));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTPO1));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTPO2));
         objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
         objTblHeaColGrpAmeSur=null;





     objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
     tcmAux.getColumn(INT_TBL_NDIAPO).setCellEditor(objTblCelEdiTxt);
     objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

              int intCol = tblDat.getSelectedRow();

             
              

      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

              int intCol = tblDat.getSelectedRow();

              String strDias=( (tblDat.getValueAt(intCol, INT_TBL_NDIAPO)==null)?"0":tblDat.getValueAt(intCol, INT_TBL_NDIAPO).toString() );
              String strFec=( (tblDat.getValueAt(intCol, INT_TBL_FECVEN)==null)?"":tblDat.getValueAt(intCol, INT_TBL_FECVEN).toString() );


              if(!strDias.equals("")){
                double dblDia = Double.parseDouble(strDias);
                int intDia = (int)dblDia;
                Date datFechaCal =  _getCalculaFec(strFec, intDia );
                tblDat.setValueAt(  objUti.formatearFecha(datFechaCal, "dd/MM/yyyy") ,  intCol, INT_TBL_FECPOS);
              }else
                  tblDat.setValueAt( null,  intCol, INT_TBL_FECPOS);

              
      }
      });
      objTblCelEdiTxt=null;

     objTblCelEdiTxtDiaSol=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
     tcmAux.getColumn(INT_TBL_ADIPOS).setCellEditor(objTblCelEdiTxtDiaSol);
     objTblCelEdiTxtDiaSol.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

              int intCol = tblDat.getSelectedRow();

              String strCodPos = (tblDat.getValueAt(intCol, INT_TBL_CODPOS)==null?"":tblDat.getValueAt(intCol, INT_TBL_CODPOS).toString());
              String strEstCan = (tblDat.getValueAt(intCol, INT_TBL_CANSOL)==null?"":tblDat.getValueAt(intCol, INT_TBL_CANSOL).toString());

               if(!strCodPos.equals("")){
                if(strEstCan.equals("true"))
                  objTblCelEdiTxtDiaSol.setCancelarEdicion(true);
              }


      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

              int intCol = tblDat.getSelectedRow();

              String strDias=( (tblDat.getValueAt(intCol, INT_TBL_ADIPOS)==null)?"0":tblDat.getValueAt(intCol, INT_TBL_ADIPOS).toString() );
              String strFec=( (tblDat.getValueAt(intCol, INT_TBL_FECVEN)==null)?"":tblDat.getValueAt(intCol, INT_TBL_FECVEN).toString() );


              if(!strDias.equals("")){
                tblDat.setValueAt( new Boolean(true), intCol, INT_TBL_AUTPOS);
                tblDat.setValueAt( new Boolean(false), intCol, INT_TBL_DENPOS);
                double dblDia = Double.parseDouble(strDias);
                int intDia = (int)dblDia;
                Date datFechaCal =  _getCalculaFec(strFec, intDia );
                tblDat.setValueAt(  objUti.formatearFecha(datFechaCal, "dd/MM/yyyy") ,  intCol, INT_TBL_AFEPOS);
              }else{
                  tblDat.setValueAt( null,  intCol, INT_TBL_AFEPOS);
                  tblDat.setValueAt( new Boolean(false), intCol, INT_TBL_AUTPOS);
              }

      }
      });




    tcmAux.getColumn(INT_TBL_FECPOS).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));
    tcmAux.getColumn(INT_TBL_AFEPOS).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));

      

//     objTblCelEdiTxtFec=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
//     tcmAux.getColumn(INT_TBL_FECPOS).setCellEditor(objTblCelEdiTxtFec);
//     objTblCelEdiTxtFec.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
//
//              int intCol = tblDat.getSelectedRow();
//
//              //INT_TBL_FECVEN
//              //  INT_TBL_FECPOS
//
//      }
//      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//
//              int intCol = tblDat.getSelectedRow();
//
//              System.out.println("--> 009 " );
//
//      }
//      });





    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
    tcmAux=null;


     return blnres;
}



private Date  _getCalculaFec(String strFech, int intDia ){
  Date datFec=null;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql = "";
  try{
     conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
     if (conn!=null){
         stmLoc=conn.createStatement();

         strSql="SELECT (Date('"+strFech+"')+ "+intDia+" ) as fecha ";
        // System.out.println("-->  "+ strSql );
         rstLoc=stmLoc.executeQuery(strSql);
         if(rstLoc.next()){
             datFec=rstLoc.getDate("fecha");
         }
         rstLoc.close();
         rstLoc=null;
        stmLoc.close();
        stmLoc=null;
        conn.close();
        conn=null;
  }}catch (java.sql.SQLException e){ datFec=null;  objUti.mostrarMsgErr_F1(this, e);     }
    catch (Exception e){ datFec=null;  objUti.mostrarMsgErr_F1(this, e);     }
    return datFec;
 }





private void llamarVenObs(String strObs, int intFil, int intCol, boolean blnEstButAce){
 ZafCxC72_02 obj1 = new  ZafCxC72_02(javax.swing.JOptionPane.getFrameForComponent(this), true , strObs, blnEstButAce );
 obj1.show();
 if(obj1.getAceptar())
   tblDat.setValueAt( obj1.getObser(), intFil, intCol );
 obj1=null;
}


private void llamarListadoSolAnt(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodReg, String strCodPos ){
       ZafCxC72_01 obj1 = new  ZafCxC72_01(objParSis, Integer.parseInt(strCodEmp), Integer.parseInt(strCodLoc)
       ,  Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc), Integer.parseInt(strCodReg)
       ,Integer.parseInt(strCodPos ) );
       this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
       obj1.show();
}



private void llamarPantHisSolPos(String strCodEmp, String strCodCli ){
       CxC.ZafCxC77.ZafCxC77 obj1 = new CxC.ZafCxC77.ZafCxC77(objParSis, new Integer(strCodEmp), new Integer(strCodCli) );
       this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
       obj1.show();
}





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
        jLabel9 = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtNumChq = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        chkNoSolPos = new javax.swing.JCheckBox();
        chkSolPosPen = new javax.swing.JCheckBox();
        chkSolPosCan = new javax.swing.JCheckBox();
        chkSolPosAut = new javax.swing.JCheckBox();
        chkSolPosDen = new javax.swing.JCheckBox();
        panRepot = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        jPanel2 = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGuardar = new javax.swing.JButton();
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

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel9.setText("Cliente:"); // NOI18N
        panFilCab.add(jLabel9);
        jLabel9.setBounds(10, 10, 50, 20);

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
        txtCodCli.setBounds(110, 10, 70, 20);

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
        panFilCab.add(txtNomCli);
        txtNomCli.setBounds(180, 10, 300, 20);

        butCli.setText(".."); // NOI18N
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFilCab.add(butCli);
        butCli.setBounds(480, 10, 20, 20);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel4.setText("Numero de cheque");
        panFilCab.add(jLabel4);
        jLabel4.setBounds(10, 33, 110, 20);

        txtNumChq.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumChqFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumChqFocusLost(evt);
            }
        });
        panFilCab.add(txtNumChq);
        txtNumChq.setBounds(110, 33, 80, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel5.setText("Fecha de vencimiento");
        panFilCab.add(jLabel5);
        jLabel5.setBounds(280, 33, 120, 15);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mostrar los cheques que:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel1.setLayout(null);

        chkNoSolPos.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkNoSolPos.setText("No tienen solicitud de postergacion");
        jPanel1.add(chkNoSolPos);
        chkNoSolPos.setBounds(10, 20, 300, 23);

        chkSolPosPen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkSolPosPen.setText("Tienen solicitud de postergacion pendiente");
        jPanel1.add(chkSolPosPen);
        chkSolPosPen.setBounds(10, 40, 290, 23);

        chkSolPosCan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkSolPosCan.setText("Tienen solicitud de postergacion cancelada");
        jPanel1.add(chkSolPosCan);
        chkSolPosCan.setBounds(10, 60, 280, 23);

        chkSolPosAut.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkSolPosAut.setText("Tienen solicitud de postergacion autorizada");
        jPanel1.add(chkSolPosAut);
        chkSolPosAut.setBounds(10, 80, 290, 23);

        chkSolPosDen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkSolPosDen.setText("Tienen solicitud de postergacion denegacion");
        jPanel1.add(chkSolPosDen);
        chkSolPosDen.setBounds(10, 100, 300, 23);

        panFilCab.add(jPanel1);
        jPanel1.setBounds(10, 80, 340, 140);

        tabFrm.addTab("General", panFilCab);

        panRepot.setLayout(new java.awt.BorderLayout());

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

        panRepot.add(jScrollPane1, java.awt.BorderLayout.CENTER);

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

        butGuardar.setText("Guardar");
        butGuardar.setPreferredSize(new java.awt.Dimension(92, 25));
        butGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuardarActionPerformed(evt);
            }
        });
        panBot.add(butGuardar);

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
             objThrGUI=null;
        }


    }//GEN-LAST:event_butConActionPerformed




private boolean validarDat(){
  boolean blnRes=true;
 
  

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

            pgrSis.setIndeterminate(true);

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

           pgrSis.setIndeterminate(false);
         }

         objThrGUI=null;

        }
    }





      private boolean cargarDetReg(){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try
        {
           butCon.setText("Detener");
           lblMsgSis.setText("Obteniendo datos...");

                java.sql.Connection con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {

                    stmLoc=con.createStatement();

                    
                    String strAux="";
                    if(!txtNumChq.getText().equals("")){
                        strAux=" AND a.tx_numchq='"+txtNumChq.getText()+"' ";
                    }
                    if(!txtCodCli.getText().equals("")){
                        strAux=" AND a.co_cli="+txtCodCli.getText()+" ";
                    }



                    if(txtFecDoc.isFecha()){
                         int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
                         String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
                         strAux = strAux + " AND a.fe_venchq = '" +  strFecSql + "'";
                     }


                    String strAuxFil="";
                    String strEstSol="";

                    if(chkNoSolPos.isSelected())  strAuxFil="  a1.st_solpos is null ";
                    if(chkSolPosPen.isSelected()) strEstSol+="'P'";
                    if(chkSolPosCan.isSelected()){ if(strEstSol.equals("")) strEstSol+="'C'"; else strEstSol+=",'C'"; }
                    if(chkSolPosAut.isSelected()){ if(strEstSol.equals("")) strEstSol+="'A'"; else strEstSol+=",'A'"; }
                    if(chkSolPosDen.isSelected()){ if(strEstSol.equals("")) strEstSol+="'D'"; else strEstSol+=",'D'"; }



                    strSql="select ne_diapos, ne_numtotpos, co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_cli, tx_nom  ,  tx_numchq, nd_monchq, fe_venchq  " +
                    "   from ( "
                    + " SELECT a.ne_diapos, a.ne_numtotpos, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, a.fe_asitipdoccon, "
                    + " CASE WHEN a.co_tipdoccon IS NULL THEN 'N' ELSE 'S' END AS est,  a.co_cli, a3.tx_nom, a.co_banchq, a2.tx_descor, a2.tx_deslar , a.tx_numctachq, a.tx_numchq, a.nd_monchq, "
                    + " a.fe_recchq, a.fe_venchq  , a.co_tipdoccon, a4.tx_descor as descortipdoc, a4.tx_deslar as deslartipdoc, a.nd_valapl, a.st_asidocrel "
                    + " FROM tbm_detrecdoc as a "
                    + " INNER JOIN tbm_cabrecdoc AS a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc) "
                    + " INNER JOIN tbm_cli as a3 ON (a3.co_emp=a.co_emp and a3.co_cli=a.co_cli) "
                    + " LEFT  JOIN tbm_var as a2 ON (a2.co_reg=a.co_banchq ) "
                    + " LEFT  JOIN tbm_cabtipdoc as a4 ON (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoccon ) "
                    + " WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal()+" "
                    + " AND a.co_tipdoc= 94    AND a.st_reg = 'A'  "
                    + " "+strAux+" AND a1.st_reg NOT IN ('I','E') "
                    + " and a.nd_valapl =  0 "
                    + " ) as x  ORDER BY  fe_venchq, tx_nom ";



                    strSql=" select a.*, a1.co_pos ,a1.ne_diasolpos, " +
                    " case  when a1.ne_diasolpos is null then null else (fe_venchq+a1.ne_diasolpos) end  as fe_solpos " +
                    "  , a1.tx_obssolpos, a1.st_solpos " +
                    " , case  when a1.ne_diaautpos is null then null else (fe_venchq+a1.ne_diaautpos) end  as fe_autpos " +
                    " , a1.ne_diaautpos, a1.tx_obsautpos  from ( " +
                    " "+  strSql +"  ) as a " +
                    " left join tbm_posChqRecDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc and a1.co_reg=a.co_reg " +
                    "  and  a1.co_pos =  ( select max(co_pos) as copos  from tbm_posChqRecDoc x where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc= a.co_tipdoc and x.co_doc= a.co_doc and x.co_reg=a.co_reg ) " +
                    " )    ";
                    if(!strAuxFil.equals("")){
                        strSql+= " WHERE  "+strAuxFil+" ";
                        if (!strEstSol.equals("")) strSql+= "  OR  a1.st_solpos in ("+strEstSol+") ";
                    }else if (!strEstSol.equals("")) strSql+= " WHERE a1.st_solpos in ("+strEstSol+") ";


                            
                    strSql= strSql+" ORDER BY  tx_nom , tx_numchq, fe_venchq  ";

                    //System.out.println("---> "+ strSql );

                   vecDat.clear();

                    rstLoc=stmLoc.executeQuery(strSql);
                    while (rstLoc.next())
                    {

                                vecReg=new Vector();
                                    vecReg.add(INT_TBL_LINEA,"");
                                    vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp") );
                                    vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
                                    vecReg.add(INT_TBL_CODTID, rstLoc.getString("co_tipdoc") );
                                    vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
                                    vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg") );
                                    vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
                                    vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nom") );
                                    vecReg.add(INT_TBL_FECDOC, "" );
                                    vecReg.add(INT_TBL_NUMCHQ, rstLoc.getString("tx_numchq") );
                                    vecReg.add(INT_TBL_VALCHQ, rstLoc.getString("nd_monchq") );
                                    vecReg.add(INT_TBL_FECVEN, rstLoc.getString("fe_venchq") );
                                    vecReg.add(INT_TBL_CANSOL, new Boolean(  (rstLoc.getString("st_solpos")==null?false:(rstLoc.getString("st_solpos").equals("C")?true:false)) ) );
                                    vecReg.add(INT_TBL_NDIAPO, rstLoc.getString("ne_diasolpos") );
                                    vecReg.add(INT_TBL_FECPOS, (rstLoc.getDate("fe_solpos")==null?"":objUti.formatearFecha(rstLoc.getDate("fe_solpos"), "dd/MM/yyyy")) );
                                    vecReg.add(INT_TBL_TXTOBS, rstLoc.getString("tx_obssolpos") );
                                    vecReg.add(INT_TBL_BUTOBS, "" );
                                    vecReg.add(INT_TBL_AUTPOS, new Boolean( (rstLoc.getString("st_solpos")==null?false:(rstLoc.getString("st_solpos").equals("A")?true:false)) ) );
                                    vecReg.add(INT_TBL_DENPOS, new Boolean( (rstLoc.getString("st_solpos")==null?false:(rstLoc.getString("st_solpos").equals("D")?true:false)) ) );
                                    vecReg.add(INT_TBL_ADIPOS, rstLoc.getString("ne_diaautpos") );
                                    vecReg.add(INT_TBL_AFEPOS, (rstLoc.getDate("fe_autpos")==null?"":objUti.formatearFecha(rstLoc.getDate("fe_autpos"), "dd/MM/yyyy")) );

                                    vecReg.add(INT_TBL_TXTOBA, rstLoc.getString("tx_obsautpos") );
                                    vecReg.add(INT_TBL_BUTOBA, "" );
                                    vecReg.add(INT_TBL_NTOTPO, rstLoc.getString("ne_numtotpos") );
                                    vecReg.add(INT_TBL_BUTPO1, "" );
                                    vecReg.add(INT_TBL_BUTPO2, "" );
                                    vecReg.add(INT_TBL_DIASOL_ORI, rstLoc.getString("ne_diasolpos") );
                                    vecReg.add(INT_TBL_CODPOS, rstLoc.getString("co_pos") );
                                    vecReg.add(INT_TBL_DIAAUT_ORI, rstLoc.getString("ne_diaautpos") );
                                    vecReg.add(INT_TBL_ESTAUT_ORI, (rstLoc.getString("st_solpos")==null?"":rstLoc.getString("st_solpos")) );


                                vecDat.add(vecReg);

                        }
                     objTblMod.setData(vecDat);
                     tblDat.setModel(objTblMod);
                     vecDat.clear();

                    rstLoc.close();
                    stmLoc.close();
                    con.close();
                    rstLoc=null;
                    stmLoc=null;
                    con=null;

           
           lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
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



 
    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
         cerrarVen();
    }//GEN-LAST:event_formInternalFrameClosing

    private void butCer2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCer2ActionPerformed
        // TODO add your handling code here:
         cerrarVen();
    }//GEN-LAST:event_butCer2ActionPerformed

    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        // TODO add your handling code here:

     if(objParSis.getCodigoMenu()==2526)
         guardarDatPos();
     else
        guardarDatAut();

        cargarDetReg();
      

    }//GEN-LAST:event_butGuardarActionPerformed

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

    private void txtNumChqFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumChqFocusGained
        // TODO add your handling code here:
        txtNumChq.selectAll();
}//GEN-LAST:event_txtNumChqFocusGained

    private void txtNumChqFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumChqFocusLost
        // TODO add your handling code here:

       
}//GEN-LAST:event_txtNumChqFocusLost



private boolean guardarDatPos(){
  boolean blnRes=false;
  java.sql.Connection conn;
  try{
     conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);

       if(guardarRegPos(conn)){
           conn.commit();
           blnRes=true;
           MensajeInf("Los datos se Guardaron con éxito.. ");
       }else conn.rollback();


       conn.close();
       conn=null;
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

    System.gc();
    return blnRes;
}


private boolean guardarDatAut(){
  boolean blnRes=false;
  java.sql.Connection conn;
  try{
     conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);

       if(guardarRegAut(conn)){
           conn.commit();
           blnRes=true;
           MensajeInf("Los datos se Guardaron con éxito.. ");
       }else conn.rollback();


       conn.close();
       conn=null;
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

    System.gc();
    return blnRes;
}




public boolean guardarRegPos(java.sql.Connection conn ){
   boolean blnRes=false;
   java.sql.Statement stmLoc;
   java.sql.ResultSet rstLoc;
   String strSql="";
   String strObs="", strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="", strCodReg="";
   String strDiaPos="", strDiaPosOri="", strCodPos="", strEstSol="P";
   int intCodPos=0;
   int intDiaPos=0, intDiaPosOri=0;
   try{
     if(conn!=null){
      stmLoc=conn.createStatement();

       for(int i=0; i<tblDat.getRowCount(); i++){
        String strFecPos = (tblDat.getValueAt(i, INT_TBL_FECPOS)==null?"":tblDat.getValueAt(i, INT_TBL_FECPOS).toString());
        if(!strFecPos.equals("")){


            strCodEmp = tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
            strCodLoc = tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
            strCodTipDoc=tblDat.getValueAt(i, INT_TBL_CODTID).toString();
            strCodDoc = tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
            strCodReg = tblDat.getValueAt(i, INT_TBL_CODREG).toString();
            
            strObs = (tblDat.getValueAt(i, INT_TBL_TXTOBS)==null?"":tblDat.getValueAt(i, INT_TBL_TXTOBS).toString());
            strDiaPosOri = (tblDat.getValueAt(i, INT_TBL_DIASOL_ORI)==null?"0":tblDat.getValueAt(i, INT_TBL_DIASOL_ORI).toString());
            strDiaPos = (tblDat.getValueAt(i, INT_TBL_NDIAPO)==null?"0":tblDat.getValueAt(i, INT_TBL_NDIAPO).toString());
            strCodPos = (tblDat.getValueAt(i, INT_TBL_CODPOS)==null?"":tblDat.getValueAt(i, INT_TBL_CODPOS).toString());


            strEstSol="P";
            if( (tblDat.getValueAt(i, INT_TBL_CANSOL)==null?"false":tblDat.getValueAt(i, INT_TBL_CANSOL).toString()).equals("true") ){
                strEstSol="C";
            }else if( (tblDat.getValueAt(i, INT_TBL_AUTPOS)==null?"false":tblDat.getValueAt(i, INT_TBL_AUTPOS).toString()).equals("true") ){
                strEstSol="A";
            }else if( (tblDat.getValueAt(i, INT_TBL_DENPOS)==null?"false":tblDat.getValueAt(i, INT_TBL_DENPOS).toString()).equals("true") ){
                strEstSol="D";
            }


            intDiaPos = (int)Double.parseDouble(strDiaPos);
            intDiaPosOri = (int)Double.parseDouble(strDiaPosOri);

            if(intDiaPos != intDiaPosOri ){

                strSql="SELECT case when (Max(co_pos)+1) is null then 1 else Max(co_pos)+1 end as copos  FROM tbm_poschqrecdoc WHERE " +
                " co_emp="+strCodEmp+" AND co_loc="+strCodLoc+" AND co_tipdoc="+strCodTipDoc+" AND co_doc="+strCodDoc+" "
                + " AND co_reg="+strCodReg+" ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                    intCodPos = rstLoc.getInt("copos");
                 rstLoc.close();
                 rstLoc=null;


                strSql=" INSERT INTO  tbm_poschqrecdoc(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_pos, st_solpos "
                + " ,ne_diasolpos,   tx_obssolpos, fe_ingsolpos, co_usringsolpos, tx_comingsolpos ,st_regrep ) "
                + " VALUES("+strCodEmp+", "+strCodLoc+", "+strCodTipDoc+", "+strCodDoc+", "+strCodReg+", "+intCodPos+" "
                + " ,'P', "+strDiaPos+",  "
                + "  '"+strObs+"', "+objParSis.getFuncionFechaHoraBaseDatos()+","+objParSis.getCodigoUsuario()+" "
                + " ,'"+objParSis.getNombreComputadoraConDirIP()+"', 'I' ) ";
              
           }else{

                strSql=" UPDATE tbm_poschqrecdoc SET st_solpos='"+strEstSol+"', tx_obssolpos='"+strObs+"' WHERE "
                + " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
                + " and co_doc="+strCodDoc+" and co_reg="+strCodReg+" and co_pos="+strCodPos+" ";
                
           }

           //  System.out.println(" --> "+ strSql);
            stmLoc.executeUpdate(strSql);

            blnRes=true;



       }}

      stmLoc.close();
      stmLoc=null;

   }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
     catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    return blnRes;
}


public boolean guardarRegAut(java.sql.Connection conn ){
   boolean blnRes=false;
   java.sql.Statement stmLoc;
   java.sql.ResultSet rstLoc;
   String strSql="",strSql2="";
   String strObsAut="", strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="", strCodReg="";
   String strDiaPos="", strDiaPosOri="", strCodPos="", strEstSol="P", strDiaSolPos="", strEstSolPosOri="";
   int intCodPos=0;
   int intDiaPos=0, intDiaPosOri=0;
   try{
     if(conn!=null){
      stmLoc=conn.createStatement();

       for(int i=0; i<tblDat.getRowCount(); i++){
        //String strFecPos = (tblDat.getValueAt(i, INT_TBL_AFEPOS)==null?"":tblDat.getValueAt(i, INT_TBL_AFEPOS).toString());
        //if(!strFecPos.equals("")){

            strEstSol="P";
            if( (tblDat.getValueAt(i, INT_TBL_AUTPOS)==null?"false":tblDat.getValueAt(i, INT_TBL_AUTPOS).toString()).equals("true") ){
                strEstSol="A";
            }else if( (tblDat.getValueAt(i, INT_TBL_DENPOS)==null?"false":tblDat.getValueAt(i, INT_TBL_DENPOS).toString()).equals("true") ){
                strEstSol="D";
            }
            

         if( (strEstSol.equals("A")) || (strEstSol.equals("D")) ){


            String strFecAut = (tblDat.getValueAt(i, INT_TBL_AFEPOS)==null?"":tblDat.getValueAt(i, INT_TBL_AFEPOS).toString());
            if(strFecAut.equals("")){
                strFecAut=null;
            }else{
                strFecAut="'"+objUti.formatearFecha(tblDat.getValueAt(i,INT_TBL_AFEPOS).toString(),"dd/MM/yyyy","yyyy/MM/dd")+"'";
            }


            strCodEmp = tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
            strCodLoc = tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
            strCodTipDoc=tblDat.getValueAt(i, INT_TBL_CODTID).toString();
            strCodDoc = tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
            strCodReg = tblDat.getValueAt(i, INT_TBL_CODREG).toString();

            strObsAut = (tblDat.getValueAt(i, INT_TBL_TXTOBA)==null?"":tblDat.getValueAt(i, INT_TBL_TXTOBA).toString());

            strDiaSolPos =( (tblDat.getValueAt(i, INT_TBL_NDIAPO)==null)?"0":tblDat.getValueAt(i, INT_TBL_NDIAPO).toString() );

            
            strDiaPosOri = (tblDat.getValueAt(i, INT_TBL_DIAAUT_ORI)==null?"0":tblDat.getValueAt(i, INT_TBL_DIAAUT_ORI).toString());
            strEstSolPosOri = (tblDat.getValueAt(i, INT_TBL_ESTAUT_ORI)==null?"0":tblDat.getValueAt(i, INT_TBL_ESTAUT_ORI).toString());

            strDiaPos = (tblDat.getValueAt(i, INT_TBL_ADIPOS)==null?"0":tblDat.getValueAt(i, INT_TBL_ADIPOS).toString());
            strCodPos = (tblDat.getValueAt(i, INT_TBL_CODPOS)==null?"0":tblDat.getValueAt(i, INT_TBL_CODPOS).toString());


            
            intDiaPos = (int)Double.parseDouble(strDiaPos);
            intDiaPosOri = (int)Double.parseDouble(strDiaPosOri);

            intCodPos=Integer.parseInt(strCodPos);

           // System.out.println("intCodPos --> "+intCodPos );
            
            if(intCodPos == 0  ){

                strSql="SELECT case when (Max(co_pos)+1) is null then 1 else Max(co_pos)+1 end as copos  FROM tbm_poschqrecdoc WHERE " +
                " co_emp="+strCodEmp+" AND co_loc="+strCodLoc+" AND co_tipdoc="+strCodTipDoc+" AND co_doc="+strCodDoc+" "
                + " AND co_reg="+strCodReg+" ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                    intCodPos = rstLoc.getInt("copos");
                 rstLoc.close();
                 rstLoc=null;


                strSql=" INSERT INTO  tbm_poschqrecdoc(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_pos, st_solpos "
                + " ,ne_diasolpos,  tx_obssolpos, fe_ingsolpos, co_usringsolpos, tx_comingsolpos ,st_regrep ) "
                + " VALUES("+strCodEmp+", "+strCodLoc+", "+strCodTipDoc+", "+strCodDoc+", "+strCodReg+", "+intCodPos+" "
                + " ,'P', "+strDiaPos+",  "
                + "  '', "+objParSis.getFuncionFechaHoraBaseDatos()+","+objParSis.getCodigoUsuario()+" "
                + " ,'"+objParSis.getNombreComputadoraConDirIP()+"', 'I' ) ";

                stmLoc.executeUpdate(strSql);
                
         }

            if(!strEstSol.equals("P")){

                if(strEstSol.equals("A")){

                    strSql=" UPDATE tbm_poschqrecdoc SET st_solpos='"+strEstSol+"', tx_obsautpos='"+strObsAut+"' "
                    + "  ,ne_diaautpos="+intDiaPos+" " +
                    "  ,fe_ingautpos="+objParSis.getFuncionFechaHoraBaseDatos()+"" +
                    " , co_usringautpos="+objParSis.getCodigoUsuario()+", tx_comingautpos ='"+objParSis.getNombreComputadoraConDirIP()+"'" +
                    "  WHERE "
                    + " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
                    + " and co_doc="+strCodDoc+" and co_reg="+strCodReg+" and co_pos="+intCodPos+" ";

                    
                


                 if(intDiaPos != intDiaPosOri ){
                   if(strEstSolPosOri.equals("A")){
                        strSql+="  ; UPDATE tbm_detrecdoc SET ne_diapos="+intDiaPos+" "
                        + " WHERE "
                        + " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
                        + " and co_doc="+strCodDoc+" and co_reg="+strCodReg+" ";
                   }else{
                    strSql+="  ; UPDATE tbm_detrecdoc SET ne_diapos="+intDiaPos+" "
                    + ", ne_numtotpos = CASE when ne_numtotpos+1 is null THEN 1 else ne_numtotpos+1 END  "
                    + " WHERE "
                    + " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
                    + " and co_doc="+strCodDoc+" and co_reg="+strCodReg+" ";
                 }}


                }
                else if(strEstSol.equals("D")){

                    strSql=" UPDATE tbm_poschqrecdoc SET st_solpos='"+strEstSol+"', tx_obsautpos='"+strObsAut+"' "
                    + "  ,ne_diaautpos=null " +
                    "  ,fe_ingautpos="+objParSis.getFuncionFechaHoraBaseDatos()+"" +
                    " , co_usringautpos="+objParSis.getCodigoUsuario()+", tx_comingautpos ='"+objParSis.getNombreComputadoraConDirIP()+"'" +
                    "  WHERE "
                    + " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
                    + " and co_doc="+strCodDoc+" and co_reg="+strCodReg+" and co_pos="+intCodPos+" ";


                   if(intDiaPosOri > 0  ){

                    strSql2="select co_pos, ne_diaautpos from tbm_poschqrecdoc  "
                    + " where co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc= "+strCodTipDoc+" and co_doc=  "+strCodDoc+"  and co_reg="+strCodReg+" "
                    + " and st_solpos='A' and co_pos= "
                    + " ( select max(co_pos) as copos  from tbm_posChqRecDoc x "
                    + " where x.co_emp="+strCodEmp+" and x.co_loc="+strCodLoc+" and x.co_tipdoc= "+strCodTipDoc+" and x.co_doc= "+strCodDoc+" and x.co_reg="+strCodReg+" "
                    + " and co_pos != "+intCodPos+"  and st_solpos='A'  )  ";
                    rstLoc = stmLoc.executeQuery(strSql2);
                    if(rstLoc.next()){
                        strSql+="  ; UPDATE tbm_detrecdoc SET ne_diapos="+rstLoc.getInt("ne_diaautpos")+" "
                        + " ,ne_numtotpos = CASE when ne_numtotpos-1 is null THEN null else ne_numtotpos-1 END "
                        + " WHERE "
                        + " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
                        + " and co_doc="+strCodDoc+" and co_reg="+strCodReg+" ";
                    }else{
                        strSql+="  ; UPDATE tbm_detrecdoc SET ne_diapos=null, ne_numtotpos=null  "
                        + " WHERE "
                        + " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
                        + " and co_doc="+strCodDoc+" and co_reg="+strCodReg+" ";
                    }
                    rstLoc.close();
                    rstLoc=null;

                   }

                }
            }

//           }

           // System.out.println(" --> "+ strSql);
            stmLoc.executeUpdate(strSql);


          blnRes=true;

       }}

      stmLoc.close();
      stmLoc=null;

   }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
     catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    return blnRes;
}




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer2;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGuardar;
    private javax.swing.JCheckBox chkNoSolPos;
    private javax.swing.JCheckBox chkSolPosAut;
    private javax.swing.JCheckBox chkSolPosCan;
    private javax.swing.JCheckBox chkSolPosDen;
    private javax.swing.JCheckBox chkSolPosPen;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFilCab;
    private javax.swing.JPanel panRepot;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNumChq;
    // End of variables declaration//GEN-END:variables



    






private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblDat.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LINEA:
            strMsg="";
            break;
            case INT_TBL_CODEMP:
            strMsg="Código de empresa.";
            break;
            case INT_TBL_CODLOC:
            strMsg="Código de local.";
            break;
     
            case INT_TBL_CODTID:
            strMsg="Código de tipo de documento.";
            break;
            case INT_TBL_CODDOC:
            strMsg="Código de documento.";
            break;
            case INT_TBL_CODREG:
            strMsg="Código de registro.";
            break;

            case INT_TBL_CODCLI:
            strMsg="Código del cliente.";
            break;
            case INT_TBL_NOMCLI:
            strMsg="Nombre del cliente.";
            break;
            case INT_TBL_FECDOC:
            strMsg="Fecha de vencimiento del documento.";
            break;

            case INT_TBL_NUMCHQ:
            strMsg="Número del cheque.";
            break;
            case INT_TBL_VALCHQ:
            strMsg="Valor del Cheque.";
            break;
            case INT_TBL_FECVEN:
            strMsg="Fecha de vencimiento del cheque.";
            break;

            case INT_TBL_CANSOL:
            strMsg="Cancelar.";
            break;
            case INT_TBL_NDIAPO:
            strMsg="Dias de postergación.";
            break;
            case INT_TBL_FECPOS:
            strMsg="Fecha de postergación.";
            break;

            case INT_TBL_TXTOBS:
            strMsg="Observacion de la solicitud de postergación.";
            break;
            case INT_TBL_BUTOBS:
            strMsg="";
            break;
            case INT_TBL_AUTPOS:
            strMsg="Autorización";
            break;

            case INT_TBL_DENPOS:
            strMsg="Denegar.";
            break;
            case INT_TBL_ADIPOS:
            strMsg="Dias de la postergación autorizada";
            break;
            case INT_TBL_AFEPOS:
            strMsg="Fecha de postergacion autorizada";
            break;

            case INT_TBL_TXTOBA:
            strMsg="Observacion de la autorización.";
            break;
            case INT_TBL_BUTOBA:
            strMsg="";
            break;
            case INT_TBL_NTOTPO:
            strMsg="Número total del postergaciones de cheque.";
            break;

            case INT_TBL_BUTPO1:
            strMsg="Ver todas las postergaciones de cheques que fueron autorizadas";
            break;
            case INT_TBL_BUTPO2:
            strMsg="Ver todas las postergaciones de cheques  ";
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
