/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCxC56.java
 *
 * Created on Jan 19, 2010, 4:44:52 PM
 */

package CxC.ZafCxC56;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import java.sql.DriverManager;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;

/**
 *
 * @author jayapata
 */
public class ZafCxC56 extends javax.swing.JInternalFrame {

    private ZafParSis objZafParSis;
    private ZafUtil objUti;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod, objTblModDat;
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
    private ZafTblTot objTblTotDoc;

    final int INT_TBL_EMP_LIN = 0;
    final int INT_TBL_EMP_CHK = 1;
    final int INT_TBL_EMP_CODEMP = 2;
    final int INT_TBL_EMP_CODLOC = 3;
    final int INT_TBL_EMP_NOMLOC = 4;
    final int INT_TBL_EMP_CODTIP = 5;
    final int INT_TBL_EMP_CDTIPD = 6;
    final int INT_TBL_EMP_CLTIPD = 7;



    final int INT_TBL_DAT_LIN = 0;
    final int INT_TBL_DAT_CODEMP = 1;
    final int INT_TBL_DAT_CODLOC = 2;
    final int INT_TBL_DAT_NOMLOC = 3;
    final int INT_TBL_DAT_CODTIP = 4;
    final int INT_TBL_DAT_DCTIP = 5;
    final int INT_TBL_DAT_DLTIP = 6;

    //int INT_TBL_DAT_TOT4 = 7;
    //int INT_TBL_DAT_TOT3 = 8;
    //int INT_TBL_DAT_TOT2 = 9;
    
    int INT_TBL_DAT_DOM = 7;
    int INT_TBL_DAT_SAB = 8;
    int INT_TBL_DAT_VIE = 9;
    int INT_TBL_DAT_JUE = 10;
    int INT_TBL_DAT_MIE = 11;
    int INT_TBL_DAT_MAR = 12;
    int INT_TBL_DAT_LUN = 13;
    int INT_TBL_DAT_TOT1 = 14;
    int INT_TBL_DAT_TOTFIN = 15;
    int INT_SEM_NUE=INT_TBL_DAT_DLTIP;



    final int INT_TBL_DAT_LIN2 = 0;
    final int INT_TBL_DAT_CODEMP2 = 1;
    final int INT_TBL_DAT_CODLOC2 = 2;
    final int INT_TBL_DAT_NOMLOC2 = 3;
    final int INT_TBL_DAT_CODTIP2 = 4;
    final int INT_TBL_DAT_DCTIP2 = 5;
    final int INT_TBL_DAT_DLTIP2 = 6;
    int INT_TBL_DAT_TOT12 = 7;
    int INT_TBL_DAT_TOTFIN2 = 8;
    int INT_SEM_NUE2=INT_TBL_DAT_DLTIP2;


    int intDiaHoy=-1;
    int intDia=-1;

    int intNumMesCon=0;
    int intNumSemCon=0;

    String strVersion=" Ver 0.2 ";

    String strMsnCol[] = new String[100];

    String strMsnCol2[] = new String[100];

    /** Creates new form ZafCxC56 */
    public ZafCxC56(ZafParSis objParsis) {
       try{
        this.objZafParSis = (Librerias.ZafParSis.ZafParSis) objParsis.clone();

         initComponents();
         this.setTitle(""+objZafParSis.getNombreMenu()+ strVersion );
         lblTit.setText(""+objZafParSis.getNombreMenu() );
         objUti = new ZafUtil();

        }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }


    }



private void cargarConfiguracion(){
    configurarForm();
    cargarDatEmp();

    intNumSemCon=4;
    intNumMesCon=1;
    configurarFormDat();
    configurarFormDatMes();

    spnSemCon.setValue(new Integer(4) );
    spnMesCon.setValue(new Integer(1) );

     
} 



private boolean configurarForm(){
 boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    vecCab.add(INT_TBL_EMP_LIN,"");
    vecCab.add(INT_TBL_EMP_CHK," ");
    vecCab.add(INT_TBL_EMP_CODEMP,"Cod.Emp");
    vecCab.add(INT_TBL_EMP_CODLOC,"Cod.Loc");
    vecCab.add(INT_TBL_EMP_NOMLOC,"Local");
    vecCab.add(INT_TBL_EMP_CODTIP,"Cod.TipDoc");
    vecCab.add(INT_TBL_EMP_CDTIPD,"Des.Cor.TipDoc");
    vecCab.add(INT_TBL_EMP_CLTIPD,"Des.Lar.TipDoc");

    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblEmp.setModel(objTblMod);

    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblEmp, INT_TBL_EMP_LIN);

    tblEmp.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblEmp.getColumnModel();
    tblEmp.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_EMP_LIN).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_EMP_CHK).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_EMP_CODEMP).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_EMP_CODLOC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_EMP_NOMLOC).setPreferredWidth(180);
    tcmAux.getColumn(INT_TBL_EMP_CODTIP).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_EMP_CDTIPD).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_EMP_CLTIPD).setPreferredWidth(180);


    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
     vecAux.add("" + INT_TBL_EMP_CHK);
    objTblMod.setColumnasEditables(vecAux);
    vecAux=null;
    //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblEmp);

    objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    tcmAux.getColumn(INT_TBL_EMP_CHK).setCellRenderer(objTblCelRenChk);
    objTblCelRenChk=null;








     objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

     return blnres;
}

private boolean configurarFormDat(){
 boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    int[] intCol = new int[((intNumMesCon*1)+8)];  // intNumMesCon*4
    int intArr=-1;

    vecCab.add(INT_TBL_DAT_LIN,"");
    vecCab.add(INT_TBL_DAT_CODEMP,"Cód.Emp ");
    vecCab.add(INT_TBL_DAT_CODLOC,"Cód.Loc ");
    vecCab.add(INT_TBL_DAT_NOMLOC,"Local");
    vecCab.add(INT_TBL_DAT_CODTIP,"Cód.TipDoc ");
    vecCab.add(INT_TBL_DAT_DCTIP,"Des.Cor ");
    vecCab.add(INT_TBL_DAT_DLTIP,"Des.Lar ");

    INT_SEM_NUE=INT_TBL_DAT_DLTIP;


    
    for(int i=2; i <= intNumMesCon; i++){

        
      INT_SEM_NUE++;
      intArr++;
      intCol[intArr]=INT_SEM_NUE;
      vecCab.add(INT_SEM_NUE,"Total");
//      INT_SEM_NUE++;
//      intArr++;
//      intCol[intArr]=INT_SEM_NUE;
//      vecCab.add(INT_SEM_NUE,"Total");
//      INT_SEM_NUE++;
//      intArr++;
//      intCol[intArr]=INT_SEM_NUE;
//      vecCab.add(INT_SEM_NUE,"Total");
//      INT_SEM_NUE++;
//      intArr++;
//      intCol[intArr]=INT_SEM_NUE;
//      vecCab.add(INT_SEM_NUE,"Total");
    
    }

    INT_SEM_NUE++;
    
//    INT_TBL_DAT_TOT4=INT_SEM_NUE++;
//    intArr++;
//    intCol[intArr]=INT_TBL_DAT_TOT4;
//    vecCab.add(INT_TBL_DAT_TOT4,"Total");
//
//    INT_TBL_DAT_TOT3=INT_SEM_NUE++;
//    intArr++;
//    intCol[intArr]=INT_TBL_DAT_TOT3;
//    vecCab.add(INT_TBL_DAT_TOT3,"Total");
//
//    INT_TBL_DAT_TOT2=INT_SEM_NUE++;
//     intArr++;
//    intCol[intArr]=INT_TBL_DAT_TOT2;
//    vecCab.add(INT_TBL_DAT_TOT2,"Total2");


    

    INT_TBL_DAT_LUN=INT_SEM_NUE++;
     intArr++;
    intCol[intArr]=INT_TBL_DAT_LUN;
    vecCab.add(INT_TBL_DAT_LUN,"Lunes"); //13

    INT_TBL_DAT_MAR=INT_SEM_NUE++;
     intArr++;
    intCol[intArr]=INT_TBL_DAT_MAR;
    vecCab.add(INT_TBL_DAT_MAR,"Martes"); //12

    INT_TBL_DAT_MIE=INT_SEM_NUE++;
     intArr++;
    intCol[intArr]=INT_TBL_DAT_MIE;
    vecCab.add(INT_TBL_DAT_MIE,"Miercoles"); //11

    INT_TBL_DAT_JUE=INT_SEM_NUE++;
     intArr++;
    intCol[intArr]=INT_TBL_DAT_JUE;
    vecCab.add(INT_TBL_DAT_JUE,"Jueves"); //10

    INT_TBL_DAT_VIE=INT_SEM_NUE++;
     intArr++;
    intCol[intArr]=INT_TBL_DAT_VIE;
    vecCab.add(INT_TBL_DAT_VIE,"Viernes"); //9

    INT_TBL_DAT_SAB=INT_SEM_NUE++;
    intArr++;
    intCol[intArr]=INT_TBL_DAT_SAB;
    vecCab.add(INT_TBL_DAT_SAB,"Sabado"); //8

    INT_TBL_DAT_DOM=INT_SEM_NUE++;
    intArr++;
    intCol[intArr]=INT_TBL_DAT_DOM;
    vecCab.add(INT_TBL_DAT_DOM,"Domingo"); //7








    INT_TBL_DAT_TOT1=INT_SEM_NUE++;
      intArr++;
    intCol[intArr]=INT_TBL_DAT_TOT1;
    vecCab.add(INT_TBL_DAT_TOT1,"Total");

    INT_TBL_DAT_TOTFIN=INT_SEM_NUE++;
      intArr++;
    intCol[intArr]=INT_TBL_DAT_TOTFIN;
    vecCab.add(INT_TBL_DAT_TOTFIN,"Tot.Fin");




    objTblModDat=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblModDat.setHeader(vecCab);
    tblDat.setModel(objTblModDat);

    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_DAT_LIN);

    //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
    ZafMouMotAda objMouMotAda=new ZafMouMotAda();
    tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


    //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
    objTblModDat.setColumnDataType(INT_TBL_DAT_LUN, objTblModDat.INT_COL_DBL, new Integer(0), null);
    objTblModDat.setColumnDataType(INT_TBL_DAT_MAR, objTblModDat.INT_COL_DBL, new Integer(0), null);
    objTblModDat.setColumnDataType(INT_TBL_DAT_MIE, objTblModDat.INT_COL_DBL, new Integer(0), null);
    objTblModDat.setColumnDataType(INT_TBL_DAT_JUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
    objTblModDat.setColumnDataType(INT_TBL_DAT_VIE, objTblModDat.INT_COL_DBL, new Integer(0), null);
    objTblModDat.setColumnDataType(INT_TBL_DAT_SAB, objTblModDat.INT_COL_DBL, new Integer(0), null);
    objTblModDat.setColumnDataType(INT_TBL_DAT_DOM, objTblModDat.INT_COL_DBL, new Integer(0), null);
    objTblModDat.setColumnDataType(INT_TBL_DAT_TOT1, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_TOT2, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_TOT3, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_TOT4, objTblModDat.INT_COL_DBL, new Integer(0), null);
    objTblModDat.setColumnDataType(INT_TBL_DAT_TOTFIN, objTblModDat.INT_COL_DBL, new Integer(0), null);


    INT_SEM_NUE=INT_TBL_DAT_DLTIP;


    for(int i=2; i <= intNumMesCon; i++){

    INT_SEM_NUE++;
    objTblModDat.setColumnDataType(INT_SEM_NUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
    INT_SEM_NUE++;
    objTblModDat.setColumnDataType(INT_SEM_NUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
    INT_SEM_NUE++;
    objTblModDat.setColumnDataType(INT_SEM_NUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
    INT_SEM_NUE++;
    objTblModDat.setColumnDataType(INT_SEM_NUE, objTblModDat.INT_COL_DBL, new Integer(0), null);

    }

    INT_SEM_NUE++;



    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    tblDat.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_DAT_CODEMP).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_DAT_CODLOC).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_DAT_NOMLOC).setPreferredWidth(150);
    tcmAux.getColumn(INT_TBL_DAT_CODTIP).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_DAT_DCTIP).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_DAT_DLTIP).setPreferredWidth(100);
    tcmAux.getColumn(INT_TBL_DAT_LUN).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_DAT_MAR).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_DAT_MIE).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_DAT_JUE).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_DAT_VIE).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_DAT_SAB).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_DAT_DOM).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_DAT_TOT1).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_TOT2).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_TOT3).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_TOT4).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_DAT_TOTFIN).setPreferredWidth(70);

    INT_SEM_NUE=INT_TBL_DAT_DLTIP;


    for(int i=2; i <= intNumMesCon; i++){

    INT_SEM_NUE++;
    tcmAux.getColumn(INT_SEM_NUE).setPreferredWidth(70);
//    INT_SEM_NUE++;
//    tcmAux.getColumn(INT_SEM_NUE).setPreferredWidth(70);
//    INT_SEM_NUE++;
//    tcmAux.getColumn(INT_SEM_NUE).setPreferredWidth(70);
//    INT_SEM_NUE++;
//    tcmAux.getColumn(INT_SEM_NUE).setPreferredWidth(70);

    }

    INT_SEM_NUE++;
    

    ArrayList arlColHid=new ArrayList();

 


    if( intDiaHoy == 6 ){
      arlColHid.add(""+INT_TBL_DAT_DOM);
    }

    if( intDiaHoy == 5 ){
      arlColHid.add(""+INT_TBL_DAT_DOM);
      arlColHid.add(""+INT_TBL_DAT_SAB);
    }

    if( intDiaHoy == 4 ){
      arlColHid.add(""+INT_TBL_DAT_DOM);
      arlColHid.add(""+INT_TBL_DAT_SAB);
      arlColHid.add(""+INT_TBL_DAT_VIE);
    }

    if( intDiaHoy == 3 ){
      arlColHid.add(""+INT_TBL_DAT_DOM);
      arlColHid.add(""+INT_TBL_DAT_SAB);
      arlColHid.add(""+INT_TBL_DAT_VIE);
      arlColHid.add(""+INT_TBL_DAT_JUE);
    }

     if( intDiaHoy == 2 ){
      arlColHid.add(""+INT_TBL_DAT_DOM);
      arlColHid.add(""+INT_TBL_DAT_SAB);
      arlColHid.add(""+INT_TBL_DAT_VIE);
      arlColHid.add(""+INT_TBL_DAT_JUE);
      arlColHid.add(""+INT_TBL_DAT_MIE);
    }

    if( intDiaHoy  == 1 ){
      arlColHid.add(""+INT_TBL_DAT_DOM);
      arlColHid.add(""+INT_TBL_DAT_SAB);
      arlColHid.add(""+INT_TBL_DAT_VIE);
      arlColHid.add(""+INT_TBL_DAT_JUE);
      arlColHid.add(""+INT_TBL_DAT_MIE);
      arlColHid.add(""+INT_TBL_DAT_MAR);
    }

    objTblModDat.setSystemHiddenColumns(arlColHid, tblDat);
    arlColHid=null;
    
    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
    // vecAux.add("" + INT_TBL_EMP_CHK);
    objTblModDat.setColumnasEditables(vecAux);
    vecAux=null;
    //Configurar JTable: Editor de la tabla.
    //new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);



     ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
     objTblHeaGrp.setHeight(16*2);


     int intNumSem=1;  // 4 
     intNumSem=intNumSem*intNumMesCon;
     INT_SEM_NUE=INT_TBL_DAT_DLTIP;

     for(int i=2; i <= intNumMesCon; i++){

      // for(int x=0; x<4; x++){
        INT_SEM_NUE++;
        ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Semana "+intNumSem+" " );
        objTblHeaColGrpAmeSur.setHeight(16);
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_SEM_NUE));
        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
        objTblHeaColGrpAmeSur=null;

        intNumSem--;
      // }
     }

   
        ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Semana 1 " );  // 4
//        objTblHeaColGrpAmeSur.setHeight(16);
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TOT4));
//        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
//        objTblHeaColGrpAmeSur=null;
//
//        objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Semana 3 " );
//        objTblHeaColGrpAmeSur.setHeight(16);
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TOT3));
//        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
//        objTblHeaColGrpAmeSur=null;
//
//        objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Semana 2 " );
//        objTblHeaColGrpAmeSur.setHeight(16);
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TOT2));
//        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
//        objTblHeaColGrpAmeSur=null;
//
//        objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Semana 1 " );
        objTblHeaColGrpAmeSur.setHeight(16);
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_DOM));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_SAB));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_VIE));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_JUE));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_MIE));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_MAR));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_LUN));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TOT1));
        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
        objTblHeaColGrpAmeSur=null;

        
        objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" TOTAL " );
        objTblHeaColGrpAmeSur.setHeight(16);
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TOTFIN));
        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
        objTblHeaColGrpAmeSur=null;



     Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
     objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
     objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
     objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
     objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
     tcmAux.getColumn( INT_TBL_DAT_LUN ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_DAT_MAR ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_DAT_MIE ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_DAT_JUE ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_DAT_VIE ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_DAT_SAB ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_DAT_DOM ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_DAT_TOT1 ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_TOT2 ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_TOT3 ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_TOT4 ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_DAT_TOTFIN ).setCellRenderer(objTblCelRenLbl);

    INT_SEM_NUE=INT_TBL_DAT_DLTIP;

    for(int i=2; i <= intNumMesCon; i++){

    INT_SEM_NUE++;
    tcmAux.getColumn( INT_SEM_NUE ).setCellRenderer(objTblCelRenLbl);
//    INT_SEM_NUE++;
//    tcmAux.getColumn( INT_SEM_NUE ).setCellRenderer(objTblCelRenLbl);
//    INT_SEM_NUE++;
//    tcmAux.getColumn( INT_SEM_NUE ).setCellRenderer(objTblCelRenLbl);
//    INT_SEM_NUE++;
//    tcmAux.getColumn( INT_SEM_NUE ).setCellRenderer(objTblCelRenLbl);

    }

    INT_SEM_NUE++;


     objTblCelRenLbl=null;


   
    objTblTotDoc=new ZafTblTot(spnDat, spnDoctot , tblDat, tblTotDoc, intCol);



    tcmAux=null;

     new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);

     objTblModDat.setModoOperacion(objTblModDat.INT_TBL_EDI);

     return blnres;
}

private boolean configurarFormDatMes(){
 boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    int[] intCol = new int[((intNumMesCon*1)+1)];  // intNumMesCon*4
    int intArr=-1;

    vecCab.add(INT_TBL_DAT_LIN2,"");
    vecCab.add(INT_TBL_DAT_CODEMP2,"Cód.Emp ");
    vecCab.add(INT_TBL_DAT_CODLOC2,"Cód.Loc ");
    vecCab.add(INT_TBL_DAT_NOMLOC2,"Local");
    vecCab.add(INT_TBL_DAT_CODTIP2,"Cód.TipDoc ");
    vecCab.add(INT_TBL_DAT_DCTIP2,"Des.Cor ");
    vecCab.add(INT_TBL_DAT_DLTIP2,"Des.Lar ");

    INT_SEM_NUE2=INT_TBL_DAT_DLTIP2;






    for(int i=2; i <= intNumMesCon; i++){


      INT_SEM_NUE2++;
      intArr++;
      intCol[intArr]=INT_SEM_NUE2;
      vecCab.add(INT_SEM_NUE2,"Total");
//      INT_SEM_NUE++;
//      intArr++;
//      intCol[intArr]=INT_SEM_NUE;
//      vecCab.add(INT_SEM_NUE,"Total");
//      INT_SEM_NUE++;
//      intArr++;
//      intCol[intArr]=INT_SEM_NUE;
//      vecCab.add(INT_SEM_NUE,"Total");
//      INT_SEM_NUE++;
//      intArr++;
//      intCol[intArr]=INT_SEM_NUE;
//      vecCab.add(INT_SEM_NUE,"Total");

    }

   // INT_SEM_NUE++;

//    INT_SEM_NUE++;
//    intArr++;
//    intCol[intArr]=INT_SEM_NUE;
//    vecCab.add(INT_SEM_NUE,"Total");
//
//    INT_SEM_NUE++;
//    intArr++;
//    intCol[intArr]=INT_SEM_NUE;
//    vecCab.add(INT_SEM_NUE,"Total");
//
//    INT_SEM_NUE++;
//    intArr++;
//    intCol[intArr]=INT_SEM_NUE;
//    vecCab.add(INT_SEM_NUE,"Total");


     INT_SEM_NUE2++;
/*

    INT_TBL_DAT_LUN=INT_SEM_NUE++;
     intArr++;
    intCol[intArr]=INT_TBL_DAT_LUN;
    vecCab.add(INT_TBL_DAT_LUN,"Lunes"); //13

    INT_TBL_DAT_MAR=INT_SEM_NUE++;
     intArr++;
    intCol[intArr]=INT_TBL_DAT_MAR;
    vecCab.add(INT_TBL_DAT_MAR,"Martes"); //12

    INT_TBL_DAT_MIE=INT_SEM_NUE++;
     intArr++;
    intCol[intArr]=INT_TBL_DAT_MIE;
    vecCab.add(INT_TBL_DAT_MIE,"Miercoles"); //11

    INT_TBL_DAT_JUE=INT_SEM_NUE++;
     intArr++;
    intCol[intArr]=INT_TBL_DAT_JUE;
    vecCab.add(INT_TBL_DAT_JUE,"Jueves"); //10

    INT_TBL_DAT_VIE=INT_SEM_NUE++;
     intArr++;
    intCol[intArr]=INT_TBL_DAT_VIE;
    vecCab.add(INT_TBL_DAT_VIE,"Viernes"); //9

    INT_TBL_DAT_SAB=INT_SEM_NUE++;
    intArr++;
    intCol[intArr]=INT_TBL_DAT_SAB;
    vecCab.add(INT_TBL_DAT_SAB,"Sabado"); //8

    INT_TBL_DAT_DOM=INT_SEM_NUE++;
    intArr++;
    intCol[intArr]=INT_TBL_DAT_DOM;
    vecCab.add(INT_TBL_DAT_DOM,"Domingo"); //7


*/





    INT_TBL_DAT_TOT12=INT_SEM_NUE2++;
      intArr++;
    intCol[intArr]=INT_TBL_DAT_TOT12;
    vecCab.add(INT_TBL_DAT_TOT12,"Total");

    INT_TBL_DAT_TOTFIN2=INT_SEM_NUE2++;
      intArr++;
    intCol[intArr]=INT_TBL_DAT_TOTFIN2;
    vecCab.add(INT_TBL_DAT_TOTFIN2,"Tot.Fin");




    objTblModDat=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblModDat.setHeader(vecCab);
    tblDat1.setModel(objTblModDat);

    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat1, INT_TBL_DAT_LIN2);

    //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
    ZafMouMotAdaMes objMouMotAda=new ZafMouMotAdaMes();
    tblDat1.getTableHeader().addMouseMotionListener(objMouMotAda);


    //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//    objTblModDat.setColumnDataType(INT_TBL_DAT_LUN, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_MAR, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_MIE, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_JUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_VIE, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_SAB, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_DOM, objTblModDat.INT_COL_DBL, new Integer(0), null);
    objTblModDat.setColumnDataType(INT_TBL_DAT_TOT12, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_TOT2, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_TOT3, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_TOT4, objTblModDat.INT_COL_DBL, new Integer(0), null);
    objTblModDat.setColumnDataType(INT_TBL_DAT_TOTFIN2, objTblModDat.INT_COL_DBL, new Integer(0), null);


    INT_SEM_NUE2=INT_TBL_DAT_DLTIP2;


    for(int i=2; i <= intNumMesCon; i++){

    INT_SEM_NUE2++;
    objTblModDat.setColumnDataType(INT_SEM_NUE2, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    INT_SEM_NUE++;
//    objTblModDat.setColumnDataType(INT_SEM_NUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    INT_SEM_NUE++;
//    objTblModDat.setColumnDataType(INT_SEM_NUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    INT_SEM_NUE++;
//    objTblModDat.setColumnDataType(INT_SEM_NUE, objTblModDat.INT_COL_DBL, new Integer(0), null);

    }

   // INT_SEM_NUE++;

//    INT_SEM_NUE++;
//    objTblModDat.setColumnDataType(INT_SEM_NUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    INT_SEM_NUE++;
//    objTblModDat.setColumnDataType(INT_SEM_NUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    INT_SEM_NUE++;
//    objTblModDat.setColumnDataType(INT_SEM_NUE, objTblModDat.INT_COL_DBL, new Integer(0), null);



    tblDat1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat1.getColumnModel();
    tblDat1.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_DAT_LIN2).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_DAT_CODEMP2).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_DAT_CODLOC2).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_DAT_NOMLOC2).setPreferredWidth(150);
    tcmAux.getColumn(INT_TBL_DAT_CODTIP2).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_DAT_DCTIP2).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_DAT_DLTIP2).setPreferredWidth(100);
//    tcmAux.getColumn(INT_TBL_DAT_LUN).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_MAR).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_MIE).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_JUE).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_VIE).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_SAB).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_DOM).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_DAT_TOT12).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_TOT2).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_TOT3).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_TOT4).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_DAT_TOTFIN2).setPreferredWidth(70);

    INT_SEM_NUE2=INT_TBL_DAT_DLTIP2;


    for(int i=2; i <= intNumMesCon; i++){

    INT_SEM_NUE2++;
    tcmAux.getColumn(INT_SEM_NUE2).setPreferredWidth(70);
//    INT_SEM_NUE++;
//    tcmAux.getColumn(INT_SEM_NUE).setPreferredWidth(70);
//    INT_SEM_NUE++;
//    tcmAux.getColumn(INT_SEM_NUE).setPreferredWidth(70);
//    INT_SEM_NUE++;
//    tcmAux.getColumn(INT_SEM_NUE).setPreferredWidth(70);

    }

   // INT_SEM_NUE++;

//    INT_SEM_NUE++;
//    tcmAux.getColumn(INT_SEM_NUE).setPreferredWidth(70);
//
//    INT_SEM_NUE++;
//    tcmAux.getColumn(INT_SEM_NUE).setPreferredWidth(70);
//
//    INT_SEM_NUE++;
//    tcmAux.getColumn(INT_SEM_NUE).setPreferredWidth(70);

/*
    
    ArrayList arlColHid=new ArrayList();




    if( intDiaHoy == 6 ){
      arlColHid.add(""+INT_TBL_DAT_DOM);
    }

    if( intDiaHoy == 5 ){
      arlColHid.add(""+INT_TBL_DAT_DOM);
      arlColHid.add(""+INT_TBL_DAT_SAB);
    }

    if( intDiaHoy == 4 ){
      arlColHid.add(""+INT_TBL_DAT_DOM);
      arlColHid.add(""+INT_TBL_DAT_SAB);
      arlColHid.add(""+INT_TBL_DAT_VIE);
    }

    if( intDiaHoy == 3 ){
      arlColHid.add(""+INT_TBL_DAT_DOM);
      arlColHid.add(""+INT_TBL_DAT_SAB);
      arlColHid.add(""+INT_TBL_DAT_VIE);
      arlColHid.add(""+INT_TBL_DAT_JUE);
    }

     if( intDiaHoy == 2 ){
      arlColHid.add(""+INT_TBL_DAT_DOM);
      arlColHid.add(""+INT_TBL_DAT_SAB);
      arlColHid.add(""+INT_TBL_DAT_VIE);
      arlColHid.add(""+INT_TBL_DAT_JUE);
      arlColHid.add(""+INT_TBL_DAT_MIE);
    }

    if( intDiaHoy  == 1 ){
      arlColHid.add(""+INT_TBL_DAT_DOM);
      arlColHid.add(""+INT_TBL_DAT_SAB);
      arlColHid.add(""+INT_TBL_DAT_VIE);
      arlColHid.add(""+INT_TBL_DAT_JUE);
      arlColHid.add(""+INT_TBL_DAT_MIE);
      arlColHid.add(""+INT_TBL_DAT_MAR);
    }

    objTblModDat.setSystemHiddenColumns(arlColHid, tblDat1);
    arlColHid=null;

    */

    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
    // vecAux.add("" + INT_TBL_EMP_CHK);
    objTblModDat.setColumnasEditables(vecAux);
    vecAux=null;
    //Configurar JTable: Editor de la tabla.
    //new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);



     ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat1.getTableHeader();
     objTblHeaGrp.setHeight(16*2);


     int intNumSem=1;  // 4
     intNumSem=intNumSem*intNumMesCon;
     INT_SEM_NUE2=INT_TBL_DAT_DLTIP2;


   

     for(int i=2; i <= intNumMesCon; i++){

      // for(int x=0; x<4; x++){

      
        INT_SEM_NUE2++;
        ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Mes "+intNumSem+" ");
        objTblHeaColGrpAmeSur.setHeight(16);
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_SEM_NUE2));
        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
        objTblHeaColGrpAmeSur=null;

        intNumSem--;

      
      // }
     }


      
      //  INT_SEM_NUE++;
/*
        INT_SEM_NUE++;
        ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Semana 4 " );  // 4
        objTblHeaColGrpAmeSur.setHeight(16);
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_SEM_NUE));
        //objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
        objTblHeaColGrpPri.add(objTblHeaColGrpAmeSur);
        objTblHeaColGrpAmeSur=null;

        INT_SEM_NUE++;
        objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Semana 3 " );
        objTblHeaColGrpAmeSur.setHeight(16);
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_SEM_NUE));
        //objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
        objTblHeaColGrpPri.add(objTblHeaColGrpAmeSur);
        objTblHeaColGrpAmeSur=null;

        INT_SEM_NUE++;
        objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Semana 2 " );
        objTblHeaColGrpAmeSur.setHeight(16);
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_SEM_NUE));
        //objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
        objTblHeaColGrpPri.add(objTblHeaColGrpAmeSur);
        objTblHeaColGrpAmeSur=null;
*/
        ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Mes 1 " );
        objTblHeaColGrpAmeSur.setHeight(16);
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_DOM));
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_SAB));
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_VIE));
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_JUE));
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_MIE));
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_MAR));
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_LUN));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TOT12));
        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
        objTblHeaColGrpAmeSur=null;


       


        objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" TOTAL " );
        objTblHeaColGrpAmeSur.setHeight(16);
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TOTFIN2));
        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
        objTblHeaColGrpAmeSur=null;



     Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
     objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
     objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
     objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
     objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
//     tcmAux.getColumn( INT_TBL_DAT_LUN ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_MAR ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_MIE ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_JUE ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_VIE ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_SAB ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_DOM ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_DAT_TOT12 ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_TOT2 ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_TOT3 ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_TOT4 ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_DAT_TOTFIN2 ).setCellRenderer(objTblCelRenLbl);

    INT_SEM_NUE2=INT_TBL_DAT_DLTIP2;

    for(int i=2; i <= intNumMesCon; i++){

    INT_SEM_NUE2++;
    tcmAux.getColumn( INT_SEM_NUE2 ).setCellRenderer(objTblCelRenLbl);
//    INT_SEM_NUE++;
//    tcmAux.getColumn( INT_SEM_NUE ).setCellRenderer(objTblCelRenLbl);
//    INT_SEM_NUE++;
//    tcmAux.getColumn( INT_SEM_NUE ).setCellRenderer(objTblCelRenLbl);
//    INT_SEM_NUE++;
//    tcmAux.getColumn( INT_SEM_NUE ).setCellRenderer(objTblCelRenLbl);

    }

   // INT_SEM_NUE++;

//     INT_SEM_NUE++;
//    tcmAux.getColumn( INT_SEM_NUE ).setCellRenderer(objTblCelRenLbl);
//     INT_SEM_NUE++;
//    tcmAux.getColumn( INT_SEM_NUE ).setCellRenderer(objTblCelRenLbl);
//     INT_SEM_NUE++;
//    tcmAux.getColumn( INT_SEM_NUE ).setCellRenderer(objTblCelRenLbl);

     objTblCelRenLbl=null;



    objTblTotDoc=new ZafTblTot(spnDat1, spnDoctot1 , tblDat1, tblTotDoc1, intCol);



    tcmAux=null;

     new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat1);

     objTblModDat.setModoOperacion(objTblModDat.INT_TBL_EDI);

     return blnres;
}


//private boolean configurarFormDat_res(){
// boolean blnres=false;
//
//    Vector vecCab=new Vector();    //Almacena las cabeceras
//    vecCab.clear();
//
//    int[] intCol = new int[((intNumMesCon*4)+8)];
//    int intArr=-1;
//
//    vecCab.add(INT_TBL_DAT_LIN,"");
//    vecCab.add(INT_TBL_DAT_CODEMP,"Cód.Emp ");
//    vecCab.add(INT_TBL_DAT_CODLOC,"Cód.Loc ");
//    vecCab.add(INT_TBL_DAT_NOMLOC,"Local");
//    vecCab.add(INT_TBL_DAT_CODTIP,"Cód.TipDoc ");
//    vecCab.add(INT_TBL_DAT_DCTIP,"Des.Cor ");
//    vecCab.add(INT_TBL_DAT_DLTIP,"Des.Lar ");
//
//    INT_SEM_NUE=INT_TBL_DAT_DLTIP;
//
//
//
//    for(int i=2; i <= intNumMesCon; i++){
//
//
//      INT_SEM_NUE++;
//      intArr++;
//      intCol[intArr]=INT_SEM_NUE;
//      vecCab.add(INT_SEM_NUE,"Total");
//      INT_SEM_NUE++;
//      intArr++;
//      intCol[intArr]=INT_SEM_NUE;
//      vecCab.add(INT_SEM_NUE,"Total");
//      INT_SEM_NUE++;
//      intArr++;
//      intCol[intArr]=INT_SEM_NUE;
//      vecCab.add(INT_SEM_NUE,"Total");
//      INT_SEM_NUE++;
//      intArr++;
//      intCol[intArr]=INT_SEM_NUE;
//      vecCab.add(INT_SEM_NUE,"Total");
//
//    }
//
//    INT_SEM_NUE++;
//
//    INT_TBL_DAT_TOT4=INT_SEM_NUE++;
//    intArr++;
//    intCol[intArr]=INT_TBL_DAT_TOT4;
//    vecCab.add(INT_TBL_DAT_TOT4,"Total");
//
//    INT_TBL_DAT_TOT3=INT_SEM_NUE++;
//    intArr++;
//    intCol[intArr]=INT_TBL_DAT_TOT3;
//    vecCab.add(INT_TBL_DAT_TOT3,"Total");
//
//    INT_TBL_DAT_TOT2=INT_SEM_NUE++;
//     intArr++;
//    intCol[intArr]=INT_TBL_DAT_TOT2;
//    vecCab.add(INT_TBL_DAT_TOT2,"Total2");
//
//
//
//
//    INT_TBL_DAT_LUN=INT_SEM_NUE++;
//     intArr++;
//    intCol[intArr]=INT_TBL_DAT_LUN;
//    vecCab.add(INT_TBL_DAT_LUN,"Lunes"); //13
//
//    INT_TBL_DAT_MAR=INT_SEM_NUE++;
//     intArr++;
//    intCol[intArr]=INT_TBL_DAT_MAR;
//    vecCab.add(INT_TBL_DAT_MAR,"Martes"); //12
//
//    INT_TBL_DAT_MIE=INT_SEM_NUE++;
//     intArr++;
//    intCol[intArr]=INT_TBL_DAT_MIE;
//    vecCab.add(INT_TBL_DAT_MIE,"Miercoles"); //11
//
//    INT_TBL_DAT_JUE=INT_SEM_NUE++;
//     intArr++;
//    intCol[intArr]=INT_TBL_DAT_JUE;
//    vecCab.add(INT_TBL_DAT_JUE,"Jueves"); //10
//
//    INT_TBL_DAT_VIE=INT_SEM_NUE++;
//     intArr++;
//    intCol[intArr]=INT_TBL_DAT_VIE;
//    vecCab.add(INT_TBL_DAT_VIE,"Viernes"); //9
//
//    INT_TBL_DAT_SAB=INT_SEM_NUE++;
//    intArr++;
//    intCol[intArr]=INT_TBL_DAT_SAB;
//    vecCab.add(INT_TBL_DAT_SAB,"Sabado"); //8
//
//    INT_TBL_DAT_DOM=INT_SEM_NUE++;
//    intArr++;
//    intCol[intArr]=INT_TBL_DAT_DOM;
//    vecCab.add(INT_TBL_DAT_DOM,"Domingo"); //7
//
//
//
//
//
//
//
//
//    INT_TBL_DAT_TOT1=INT_SEM_NUE++;
//      intArr++;
//    intCol[intArr]=INT_TBL_DAT_TOT1;
//    vecCab.add(INT_TBL_DAT_TOT1,"Total");
//
//    INT_TBL_DAT_TOTFIN=INT_SEM_NUE++;
//      intArr++;
//    intCol[intArr]=INT_TBL_DAT_TOTFIN;
//    vecCab.add(INT_TBL_DAT_TOTFIN,"Tot.Fin");
//
//
//
//
//    objTblModDat=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
//    objTblModDat.setHeader(vecCab);
//    tblDat.setModel(objTblModDat);
//
//    //Configurar JTable: Establecer la fila de cabecera.
//    new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_DAT_LIN);
//
//    //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
//    ZafMouMotAda objMouMotAda=new ZafMouMotAda();
//    tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
//
//
//    //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//    objTblModDat.setColumnDataType(INT_TBL_DAT_LUN, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_MAR, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_MIE, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_JUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_VIE, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_SAB, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_DOM, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_TOT1, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_TOT2, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_TOT3, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_TOT4, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    objTblModDat.setColumnDataType(INT_TBL_DAT_TOTFIN, objTblModDat.INT_COL_DBL, new Integer(0), null);
//
//
//    INT_SEM_NUE=INT_TBL_DAT_DLTIP;
//
//
//    for(int i=2; i <= intNumMesCon; i++){
//
//    INT_SEM_NUE++;
//    objTblModDat.setColumnDataType(INT_SEM_NUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    INT_SEM_NUE++;
//    objTblModDat.setColumnDataType(INT_SEM_NUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    INT_SEM_NUE++;
//    objTblModDat.setColumnDataType(INT_SEM_NUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
//    INT_SEM_NUE++;
//    objTblModDat.setColumnDataType(INT_SEM_NUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
//
//    }
//
//    INT_SEM_NUE++;
//
//
//
//    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
//    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
//    tblDat.getTableHeader().setReorderingAllowed(false);
//    //Tamaño de las celdas
//    tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
//    tcmAux.getColumn(INT_TBL_DAT_CODEMP).setPreferredWidth(50);
//    tcmAux.getColumn(INT_TBL_DAT_CODLOC).setPreferredWidth(50);
//    tcmAux.getColumn(INT_TBL_DAT_NOMLOC).setPreferredWidth(150);
//    tcmAux.getColumn(INT_TBL_DAT_CODTIP).setPreferredWidth(50);
//    tcmAux.getColumn(INT_TBL_DAT_DCTIP).setPreferredWidth(60);
//    tcmAux.getColumn(INT_TBL_DAT_DLTIP).setPreferredWidth(100);
//    tcmAux.getColumn(INT_TBL_DAT_LUN).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_MAR).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_MIE).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_JUE).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_VIE).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_SAB).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_DOM).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_TOT1).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_TOT2).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_TOT3).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_TOT4).setPreferredWidth(70);
//    tcmAux.getColumn(INT_TBL_DAT_TOTFIN).setPreferredWidth(70);
//
//    INT_SEM_NUE=INT_TBL_DAT_DLTIP;
//
//
//    for(int i=2; i <= intNumMesCon; i++){
//
//    INT_SEM_NUE++;
//    tcmAux.getColumn(INT_SEM_NUE).setPreferredWidth(70);
//    INT_SEM_NUE++;
//    tcmAux.getColumn(INT_SEM_NUE).setPreferredWidth(70);
//    INT_SEM_NUE++;
//    tcmAux.getColumn(INT_SEM_NUE).setPreferredWidth(70);
//    INT_SEM_NUE++;
//    tcmAux.getColumn(INT_SEM_NUE).setPreferredWidth(70);
//
//    }
//
//    INT_SEM_NUE++;
//
//
//    ArrayList arlColHid=new ArrayList();
//
//
//
//
//    if( intDiaHoy == 6 ){
//      arlColHid.add(""+INT_TBL_DAT_DOM);
//    }
//
//    if( intDiaHoy == 5 ){
//      arlColHid.add(""+INT_TBL_DAT_DOM);
//      arlColHid.add(""+INT_TBL_DAT_SAB);
//    }
//
//    if( intDiaHoy == 4 ){
//      arlColHid.add(""+INT_TBL_DAT_DOM);
//      arlColHid.add(""+INT_TBL_DAT_SAB);
//      arlColHid.add(""+INT_TBL_DAT_VIE);
//    }
//
//    if( intDiaHoy == 3 ){
//      arlColHid.add(""+INT_TBL_DAT_DOM);
//      arlColHid.add(""+INT_TBL_DAT_SAB);
//      arlColHid.add(""+INT_TBL_DAT_VIE);
//      arlColHid.add(""+INT_TBL_DAT_JUE);
//    }
//
//     if( intDiaHoy == 2 ){
//      arlColHid.add(""+INT_TBL_DAT_DOM);
//      arlColHid.add(""+INT_TBL_DAT_SAB);
//      arlColHid.add(""+INT_TBL_DAT_VIE);
//      arlColHid.add(""+INT_TBL_DAT_JUE);
//      arlColHid.add(""+INT_TBL_DAT_MIE);
//    }
//
//    if( intDiaHoy  == 1 ){
//      arlColHid.add(""+INT_TBL_DAT_DOM);
//      arlColHid.add(""+INT_TBL_DAT_SAB);
//      arlColHid.add(""+INT_TBL_DAT_VIE);
//      arlColHid.add(""+INT_TBL_DAT_JUE);
//      arlColHid.add(""+INT_TBL_DAT_MIE);
//      arlColHid.add(""+INT_TBL_DAT_MAR);
//    }
//
//    objTblModDat.setSystemHiddenColumns(arlColHid, tblDat);
//    arlColHid=null;
//
//    //Configurar JTable: Establecer columnas editables.
//    Vector vecAux=new Vector();
//    // vecAux.add("" + INT_TBL_EMP_CHK);
//    objTblModDat.setColumnasEditables(vecAux);
//    vecAux=null;
//    //Configurar JTable: Editor de la tabla.
//    //new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
//
//
//
//     ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
//     objTblHeaGrp.setHeight(16*2);
//
//
//     int intNumSem=4;
//     intNumSem=intNumSem*intNumMesCon;
//     INT_SEM_NUE=INT_TBL_DAT_DLTIP;
//
//     for(int i=2; i <= intNumMesCon; i++){
//
//       for(int x=0; x<4; x++){
//        INT_SEM_NUE++;
//        ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Semana "+intNumSem+" " );
//        objTblHeaColGrpAmeSur.setHeight(16);
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_SEM_NUE));
//        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
//        objTblHeaColGrpAmeSur=null;
//
//        intNumSem--;
//       }
//     }
//
//
//        ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Semana 4 " );
//        objTblHeaColGrpAmeSur.setHeight(16);
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TOT4));
//        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
//        objTblHeaColGrpAmeSur=null;
//
//        objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Semana 3 " );
//        objTblHeaColGrpAmeSur.setHeight(16);
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TOT3));
//        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
//        objTblHeaColGrpAmeSur=null;
//
//        objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Semana 2 " );
//        objTblHeaColGrpAmeSur.setHeight(16);
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TOT2));
//        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
//        objTblHeaColGrpAmeSur=null;
//
//        objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Semana 1 " );
//        objTblHeaColGrpAmeSur.setHeight(16);
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_DOM));
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_SAB));
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_VIE));
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_JUE));
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_MIE));
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_MAR));
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_LUN));
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TOT1));
//        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
//        objTblHeaColGrpAmeSur=null;
//
//
//        objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" TOTAL " );
//        objTblHeaColGrpAmeSur.setHeight(16);
//        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TOTFIN));
//        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
//        objTblHeaColGrpAmeSur=null;
//
//
//
//     Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
//     objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
//     objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//     objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
//     objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
//     tcmAux.getColumn( INT_TBL_DAT_LUN ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_MAR ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_MIE ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_JUE ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_VIE ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_SAB ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_DOM ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_TOT1 ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_TOT2 ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_TOT3 ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_TOT4 ).setCellRenderer(objTblCelRenLbl);
//     tcmAux.getColumn( INT_TBL_DAT_TOTFIN ).setCellRenderer(objTblCelRenLbl);
//
//    INT_SEM_NUE=INT_TBL_DAT_DLTIP;
//
//    for(int i=2; i <= intNumMesCon; i++){
//
//    INT_SEM_NUE++;
//    tcmAux.getColumn( INT_SEM_NUE ).setCellRenderer(objTblCelRenLbl);
//    INT_SEM_NUE++;
//    tcmAux.getColumn( INT_SEM_NUE ).setCellRenderer(objTblCelRenLbl);
//    INT_SEM_NUE++;
//    tcmAux.getColumn( INT_SEM_NUE ).setCellRenderer(objTblCelRenLbl);
//    INT_SEM_NUE++;
//    tcmAux.getColumn( INT_SEM_NUE ).setCellRenderer(objTblCelRenLbl);
//
//    }
//
//    INT_SEM_NUE++;
//
//
//     objTblCelRenLbl=null;
//
//
//
//    objTblTotDoc=new ZafTblTot(spnDat, spnDoctot , tblDat, tblTotDoc, intCol);
//
//
//
//    tcmAux=null;
//
//     new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
//
//     objTblModDat.setModoOperacion(objTblModDat.INT_TBL_EDI);
//
//     return blnres;
//}
//


private boolean cargarMsnCabTblDat(){
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
   try{
      conn=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
      if(conn!=null){
           stmLoc=conn.createStatement();


        int intDiaRetro=0;

       if( (1-intDiaHoy) < 0 ){
             intDiaRetro=(intDiaHoy-1);
             for(int i=(intDiaHoy-1); i>0; i-- ){
                strSql+=" ,(current_date-"+i+") as valdoc ";
              }
           }

           int intDiaInc=0;
           for( intDiaInc=0; intDiaInc<=(7-intDiaHoy); intDiaInc++){
                strSql+=" ,  (current_date+"+intDiaInc+") as valdoc ";
            }


           intDiaInc=intDiaInc-1;

           strSql+=" , ( (current_date-"+intDiaRetro+")   || '  ' ||  current_date  ) as semana1 ";


//           strSql+=" , ( (current_date-"+(intDiaRetro+7)+")   || '  ' || (current_date-"+(intDiaRetro+1)+")  ) as semana2 ";
//
//
//           strSql+=" ,( (current_date-"+(intDiaRetro+14)+")   || '  ' || (current_date-"+(intDiaRetro+8)+")  ) as semana3 ";
//
//
//           strSql+=" ,( (current_date-"+(intDiaRetro+21)+")   || '  ' ||  (current_date-"+(intDiaRetro+15)+")  ) as semana4 ";



            int intNumSem=1;  // 4
            int DiaDes=0;  //21
            int DiaHas=0;  //21
            for(int i=2; i <= intNumMesCon; i++){

              intNumSem++;
              DiaHas=DiaDes+1;
              DiaDes=DiaDes+7;
              strSql+=" ,( (current_date-"+(intDiaRetro+DiaDes)+")   || '  ' || (current_date-"+(intDiaRetro+DiaHas)+")  ) as semana"+intNumSem+" ";

//              intNumSem++;
//              DiaHas=DiaDes+1;
//              DiaDes=DiaDes+7;
//              strSql+=",( (current_date-"+(intDiaRetro+DiaDes)+")   || '  ' ||  (current_date-"+(intDiaRetro+DiaHas)+")  ) as semana"+intNumSem+" ";
//
//              intNumSem++;
//              DiaHas=DiaDes+1;
//              DiaDes=DiaDes+7;
//              strSql+=" ,( (current_date-"+(intDiaRetro+DiaDes)+")   || '  ' ||  (current_date-"+(intDiaRetro+DiaHas)+")  ) as semana"+intNumSem+" ";
//
//              intNumSem++;
//              DiaHas=DiaDes+1;
//              DiaDes=DiaDes+7;
//              strSql+=" ,( (current_date-"+(intDiaRetro+DiaDes)+")   || '  ' ||  (current_date-"+(intDiaRetro+DiaHas)+")  ) as semana"+intNumSem+" ";

            }


           strSql="SELECT  1  "+strSql+" ";

           int intNumSemMes=0;
           int intArr=-1;
           //System.out.println(""+strSql );

           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){

               intNumSemMes = (1*intNumMesCon)+1;  //  (4*intNumMesCon)+1;
               INT_SEM_NUE=INT_TBL_DAT_DLTIP;
               for(int i=2; i <= intNumMesCon; i++){

                      intArr++;
                      intNumSemMes--;
                      strMsnCol[intArr]=rstLoc.getString("semana"+intNumSemMes);
//                      intArr++;
//                      intNumSemMes--;
//                      strMsnCol[intArr]=rstLoc.getString("semana"+intNumSemMes);
//                      intArr++;
//                      intNumSemMes--;
//                      strMsnCol[intArr]=rstLoc.getString("semana"+intNumSemMes);
//                      intArr++;
//                      intNumSemMes--;
//                      strMsnCol[intArr]=rstLoc.getString("semana"+intNumSemMes);
                }

//                intArr++;
//                strMsnCol[intArr]=rstLoc.getString(12);
//
//                intArr++;
//                strMsnCol[intArr]=rstLoc.getString(11);
//
//                intArr++;
//                strMsnCol[intArr]=rstLoc.getString(10);

                intArr++;
                strMsnCol[intArr]=rstLoc.getString(2);
                 intArr++;
                strMsnCol[intArr]=rstLoc.getString(3);
                 intArr++;
                strMsnCol[intArr]=rstLoc.getString(4);
                 intArr++;
                strMsnCol[intArr]=rstLoc.getString(5);
                 intArr++;
                strMsnCol[intArr]=rstLoc.getString(6);
                 intArr++;
                strMsnCol[intArr]=rstLoc.getString(7);
                 intArr++;
                strMsnCol[intArr]=rstLoc.getString(8);
                 intArr++;
                strMsnCol[intArr]=rstLoc.getString(9);



                  
           }
           rstLoc.close();
           rstLoc=null;

      stmLoc.close();
      stmLoc=null;
      conn.close();
      conn=null;

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    System.gc();
    return blnRes;
}

private boolean cargarMsnCabTblDatMes(){
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="", strSql2="";
   try{
      conn=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
      if(conn!=null){
           stmLoc=conn.createStatement();



           strSql+=" , ( (current_date-"+(intDia-1)+")   || '  ' ||  current_date  ) as mes1 ";


            int intNumSem=1; 
            int DiaDes=intDia;  
            int DiaHas=intDia;  
            for(int i=2; i <= intNumMesCon; i++){


              strSql2="select  extract(day from    (current_date-("+DiaDes+"))   )  as dia ";
              rstLoc=stmLoc.executeQuery(strSql2);
              if(rstLoc.next()){
                  DiaDes=DiaDes+rstLoc.getInt("dia");
              }
              rstLoc.close();
              rstLoc=null;

  
              intNumSem++;
              strSql+=" ,( (current_date-"+(DiaDes-1)+")   || '  ' || (current_date-"+(DiaHas)+")  ) as mes"+intNumSem+" ";

              DiaHas=DiaDes;


            }


           strSql="SELECT  1  "+strSql+" ";

           int intNumSemMes=0;
           int intArr=-1;
         //  System.out.println(""+strSql );

           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){

               intNumSemMes = (1*intNumMesCon)+1;  //  (4*intNumMesCon)+1;
               INT_SEM_NUE=INT_TBL_DAT_DLTIP;
               for(int i=2; i <= intNumMesCon; i++){

                      intArr++;
                      intNumSemMes--;
                      strMsnCol2[intArr]=rstLoc.getString("mes"+intNumSemMes);
                }


                intArr++;
                strMsnCol2[intArr]=rstLoc.getString("mes1");
               


           }
           rstLoc.close();
           rstLoc=null;

      stmLoc.close();
      stmLoc=null;
      conn.close();
      conn=null;

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    System.gc();
    return blnRes;
}


private boolean cargarDatEmp(){
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

           if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo())
             strSql="select  a.co_emp, a.co_loc, a1.tx_nom, a.co_tipdoc,  a2.tx_descor, a2.tx_deslar  from tbr_tipdocusr as a " +
             " inner join tbm_loc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc ) " +
             " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc ) " +
             " where  a.co_usr="+objZafParSis.getCodigoUsuario()+" "+
             " and  a.co_mnu="+objZafParSis.getCodigoMenu()+"";

           else
              strSql="select  a.co_emp, a.co_loc, a1.tx_nom, a.co_tipdoc,  a2.tx_descor, a2.tx_deslar  from tbr_tipdocusr as a " +
              " inner join tbm_loc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc ) " +
              " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc ) " +
              " where a.co_emp="+objZafParSis.getCodigoEmpresa()+"  and a.co_loc="+objZafParSis.getCodigoLocal()+" and a.co_usr="+objZafParSis.getCodigoUsuario()+" " +
              " and  a.co_mnu="+objZafParSis.getCodigoMenu()+"";


           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){
               java.util.Vector vecReg = new java.util.Vector();
                 vecReg.add(INT_TBL_EMP_LIN, "");
                 vecReg.add(INT_TBL_EMP_CHK,  new Boolean(true) );
                 vecReg.add(INT_TBL_EMP_CODEMP, rstLoc.getString("co_emp") );
                 vecReg.add(INT_TBL_EMP_CODLOC, rstLoc.getString("co_loc") );
                 vecReg.add(INT_TBL_EMP_NOMLOC, rstLoc.getString("tx_nom") );
                 vecReg.add(INT_TBL_EMP_CODTIP, rstLoc.getString("co_tipdoc") );
                 vecReg.add(INT_TBL_EMP_CDTIPD, rstLoc.getString("tx_descor") );
                 vecReg.add(INT_TBL_EMP_CLTIPD, rstLoc.getString("tx_deslar") );

                vecData.add(vecReg);
           }
           rstLoc.close();
           rstLoc=null;

           objTblMod.setData(vecData);
           tblEmp .setModel(objTblMod);


           strSql="select date_part('dow',CURRENT_DATE) as diahoy, extract(day from (current_date)) as dia ";
           rstLoc=stmLoc.executeQuery(strSql);
           if(rstLoc.next()){
               intDiaHoy=Integer.parseInt( rstLoc.getString("diahoy") );
               intDia=Integer.parseInt( rstLoc.getString("dia") );
           }
           if(intDiaHoy==0) intDiaHoy=7;
           rstLoc.close();
           rstLoc=null;

          // System.out.println("-> "+ intDiaHoy );

      stmLoc.close();
      stmLoc=null;
      conn.close();
      conn=null;

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    System.gc();
    return blnRes;
}





private boolean cargarDatChqPenCob(){
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strEmp="";
  String strSqlPri="";

  int intEmp=0;
   try{
      conn=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
      if(conn!=null){
           stmLoc=conn.createStatement();
           java.util.Vector vecData = new java.util.Vector();

           for(int i=0; i<tblEmp.getRowCount(); i++){
             if(tblEmp.getValueAt(i, INT_TBL_EMP_CHK).toString().equals("true")){
               if(intEmp==1) strEmp+=" union all ";
               strEmp+="select "+tblEmp.getValueAt(i, INT_TBL_EMP_CODEMP).toString()+" as co_emp, "+tblEmp.getValueAt(i, INT_TBL_EMP_CODLOC).toString()+" as co_loc,  " +
               "'"+tblEmp.getValueAt(i, INT_TBL_EMP_NOMLOC).toString()+"' as nomemp" +
               ", "+tblEmp.getValueAt(i, INT_TBL_EMP_CODTIP).toString()+" as co_tipdoc "+
               " ,'"+tblEmp.getValueAt(i, INT_TBL_EMP_CDTIPD).toString()+"' as descor " +
               " ,'"+tblEmp.getValueAt(i, INT_TBL_EMP_CLTIPD).toString()+"' as deslar ";

               intEmp=1;
          }}




         int intDiaRetro=0;

         if(intEmp==1){

           if( (1-intDiaHoy) < 0 ){
             intDiaRetro=(intDiaHoy-1);
             for(int i=(intDiaHoy-1); i>0; i-- ){
                strSql+=" ,(select sum(nd_mondoc) as valcob from tbm_cabpag as a " +
                " where co_emp=x.co_emp and co_loc=x.co_loc and co_tipdoc=x.co_tipdoc and  st_reg not in ('E','I' ) and fe_doc = (current_date-"+i+") ) as valdoc ";
                
              }
           }
           
           int intDiaInc=0;
           for( intDiaInc=0; intDiaInc<=(7-intDiaHoy); intDiaInc++){
                strSql+=" ,(select sum(nd_mondoc) as valcob from tbm_cabpag as a " +
                " where co_emp=x.co_emp and co_loc=x.co_loc and co_tipdoc=x.co_tipdoc and  st_reg not in ('E','I' ) and fe_doc = (current_date+"+intDiaInc+")  ) as valdoc ";


           }

           
           intDiaInc=intDiaInc-1;

           strSql+=" ,(select sum(nd_mondoc) as valcob from tbm_cabpag as a " +
           " where co_emp=x.co_emp and co_loc=x.co_loc and co_tipdoc=x.co_tipdoc and  st_reg not in ('E','I' ) and fe_doc between  (current_date-"+intDiaRetro+")  and  current_date  ) as semana1 ";


//           strSql+=" ,(select sum(nd_mondoc) as valcob from tbm_cabpag as a " +
//           " where co_emp=x.co_emp and co_loc=x.co_loc and co_tipdoc=x.co_tipdoc and  st_reg not in ('E','I' ) and fe_doc between  current_date-"+(intDiaRetro+7)+"  and (current_date-"+(intDiaRetro+1)+")  ) as semana2 ";
//
//
//           strSql+=" ,(select sum(nd_mondoc) as valcob from tbm_cabpag as a " +
//           " where co_emp=x.co_emp and co_loc=x.co_loc and co_tipdoc=x.co_tipdoc and  st_reg not in ('E','I' ) and fe_doc between  current_date-"+(intDiaRetro+14)+"  and (current_date-"+(intDiaRetro+8)+")  ) as semana3 ";
//
//
//           strSql+=" ,(select sum(nd_mondoc) as valcob from tbm_cabpag as a " +
//           " where co_emp=x.co_emp and co_loc=x.co_loc and co_tipdoc=x.co_tipdoc and  st_reg not in ('E','I' ) and fe_doc between  current_date-"+(intDiaRetro+21)+"  and (current_date-"+(intDiaRetro+15)+")  ) as semana4 ";



            int intNumSem=1;  // 4
            int DiaDes=0;  // 21
            int DiaHas=0;  // 21
            for(int i=2; i <= intNumMesCon; i++){

              intNumSem++;
              DiaHas=DiaDes+1;
              DiaDes=DiaDes+7;
              strSql+=" ,(select sum(nd_mondoc) as valcob from tbm_cabpag as a " +
              " where co_emp=x.co_emp and co_loc=x.co_loc and co_tipdoc=x.co_tipdoc and  st_reg not in ('E','I' ) and fe_doc between  current_date-"+(intDiaRetro+DiaDes)+"  and (current_date-"+(intDiaRetro+DiaHas)+")  ) as semana"+intNumSem+" ";

//              intNumSem++;
//              DiaHas=DiaDes+1;
//              DiaDes=DiaDes+7;
//              strSql+=" ,(select sum(nd_mondoc) as valcob from tbm_cabpag as a " +
//              " where co_emp=x.co_emp and co_loc=x.co_loc and co_tipdoc=x.co_tipdoc and  st_reg not in ('E','I' ) and fe_doc between  current_date-"+(intDiaRetro+DiaDes)+"  and (current_date-"+(intDiaRetro+DiaHas)+")  ) as semana"+intNumSem+" ";
//
//              intNumSem++;
//              DiaHas=DiaDes+1;
//              DiaDes=DiaDes+7;
//              strSql+=" ,(select sum(nd_mondoc) as valcob from tbm_cabpag as a " +
//              " where co_emp=x.co_emp and co_loc=x.co_loc and co_tipdoc=x.co_tipdoc and  st_reg not in ('E','I' ) and fe_doc between  current_date-"+(intDiaRetro+DiaDes)+"  and (current_date-"+(intDiaRetro+DiaHas)+")  ) as semana"+intNumSem+" ";
//
//              intNumSem++;
//              DiaHas=DiaDes+1;
//              DiaDes=DiaDes+7;
//              strSql+=" ,(select sum(nd_mondoc) as valcob from tbm_cabpag as a " +
//              " where co_emp=x.co_emp and co_loc=x.co_loc and co_tipdoc=x.co_tipdoc and  st_reg not in ('E','I' ) and fe_doc between  current_date-"+(intDiaRetro+DiaDes)+"  and (current_date-"+(intDiaRetro+DiaHas)+")  ) as semana"+intNumSem+" ";

            }



              int intNumSemMes = (1*intNumMesCon);  //  (4*intNumMesCon);
              String strSumTotSem="";
              for(int i=1; i <= intNumMesCon; i++){
                  if(!strSumTotSem.equals("")){ strSumTotSem+=" + "; intNumSemMes--; }

                  strSumTotSem+="( case when semana"+intNumSemMes+" is null then 0 else semana"+intNumSemMes+" end )  ";  // end ) + 
//                  intNumSemMes--;
//                  strSumTotSem+="( case when semana"+intNumSemMes+" is null then 0 else semana"+intNumSemMes+" end ) + ";
//                  intNumSemMes--;
//                  strSumTotSem+="( case when semana"+intNumSemMes+" is null then 0 else semana"+intNumSemMes+" end ) + ";
//                  intNumSemMes--;
//                  strSumTotSem+="( case when semana"+intNumSemMes+" is null then 0 else semana"+intNumSemMes+" end )  ";

             }

           strSqlPri="SELECT *  ,(  "+strSumTotSem+"  ) as totfin   FROM ( " +
           " SELECT * "+strSql+" FROM ( " +
           "" +strEmp+ " "+
           " ) AS x "+
           " ) AS x ";


          // System.out.println(""+strSqlPri );


           rstLoc=stmLoc.executeQuery(strSqlPri);
           while(rstLoc.next()){
               java.util.Vector vecReg = new java.util.Vector();
                 vecReg.add(INT_TBL_DAT_LIN, "");
                 vecReg.add(INT_TBL_DAT_CODEMP, rstLoc.getString(1) );
                 vecReg.add(INT_TBL_DAT_CODLOC, rstLoc.getString(2) );
                 vecReg.add(INT_TBL_DAT_NOMLOC, rstLoc.getString(3) );
                 vecReg.add(INT_TBL_DAT_CODTIP, rstLoc.getString(4) );
                 vecReg.add(INT_TBL_DAT_DCTIP, rstLoc.getString(5) );
                 vecReg.add(INT_TBL_DAT_DLTIP, rstLoc.getString(6) );

                  INT_SEM_NUE=INT_TBL_DAT_DLTIP;


                   intNumSemMes = (1*intNumMesCon)+1;  //  (4*intNumMesCon)+1;

                    for(int i=2; i <= intNumMesCon; i++){

                      INT_SEM_NUE++;
                      intNumSemMes--;
                      vecReg.add(INT_SEM_NUE, rstLoc.getString("semana"+intNumSemMes ) );
//                      INT_SEM_NUE++;
//                      intNumSemMes--;
//                      vecReg.add(INT_SEM_NUE, rstLoc.getString("semana"+intNumSemMes) );
//                      INT_SEM_NUE++;
//                      intNumSemMes--;
//                      vecReg.add(INT_SEM_NUE, rstLoc.getString("semana"+intNumSemMes) );
//                      INT_SEM_NUE++;
//                      intNumSemMes--;
//                      vecReg.add(INT_SEM_NUE, rstLoc.getString("semana"+intNumSemMes) );

                    }
 
//                 vecReg.add(INT_TBL_DAT_TOT4, rstLoc.getString(17) );
//                 vecReg.add(INT_TBL_DAT_TOT3, rstLoc.getString(16) );
//                 vecReg.add(INT_TBL_DAT_TOT2, rstLoc.getString(15) );

                 vecReg.add(INT_TBL_DAT_LUN, rstLoc.getString(7) );
                 vecReg.add(INT_TBL_DAT_MAR, rstLoc.getString(8) );
                 vecReg.add(INT_TBL_DAT_MIE, rstLoc.getString(9) );
                 vecReg.add(INT_TBL_DAT_JUE, rstLoc.getString(10) );
                 vecReg.add(INT_TBL_DAT_VIE, rstLoc.getString(11)  );
                 vecReg.add(INT_TBL_DAT_SAB, rstLoc.getString(12) );
                 vecReg.add(INT_TBL_DAT_DOM, rstLoc.getString(13) );


                 vecReg.add(INT_TBL_DAT_TOT1,rstLoc.getString(14) );
                 vecReg.add(INT_TBL_DAT_TOTFIN, rstLoc.getString("totfin") );

                vecData.add(vecReg);
           }
           rstLoc.close();
           rstLoc=null;

           objTblModDat.setData(vecData);
           tblDat .setModel(objTblModDat);

           objTblTotDoc.calcularTotales();
           
         }


      stmLoc.close();
      stmLoc=null;
      conn.close();
      conn=null;

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    System.gc();
    return blnRes;
}



private boolean cargarDatChqPenCobMes(){
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="", strSql2="";
  String strEmp="";
  String strSqlPri="";

  int intEmp=0;
   try{
      conn=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
      if(conn!=null){
           stmLoc=conn.createStatement();
           java.util.Vector vecData = new java.util.Vector();

           for(int i=0; i<tblEmp.getRowCount(); i++){
             if(tblEmp.getValueAt(i, INT_TBL_EMP_CHK).toString().equals("true")){
               if(intEmp==1) strEmp+=" union all ";
               strEmp+="select "+tblEmp.getValueAt(i, INT_TBL_EMP_CODEMP).toString()+" as co_emp, "+tblEmp.getValueAt(i, INT_TBL_EMP_CODLOC).toString()+" as co_loc,  " +
               "'"+tblEmp.getValueAt(i, INT_TBL_EMP_NOMLOC).toString()+"' as nomemp" +
               ", "+tblEmp.getValueAt(i, INT_TBL_EMP_CODTIP).toString()+" as co_tipdoc "+
               " ,'"+tblEmp.getValueAt(i, INT_TBL_EMP_CDTIPD).toString()+"' as descor " +
               " ,'"+tblEmp.getValueAt(i, INT_TBL_EMP_CLTIPD).toString()+"' as deslar ";

               intEmp=1;
          }}




         if(intEmp==1){


           



         
           strSql+=" ,(select sum(nd_mondoc) as valcob from tbm_cabpag as a " +
           " where co_emp=x.co_emp and co_loc=x.co_loc and co_tipdoc=x.co_tipdoc and  st_reg not in ('E','I' ) and fe_doc " +
           " between  (current_date-"+(intDia-1)+")  and  current_date  ) as mes1 ";



            int intNumSem=1;  // 4
            int DiaDes=intDia;  // 21
            int DiaHas=intDia;  // 21
            for(int i=2; i <= intNumMesCon; i++){


              strSql2="select  extract(day from    (current_date-("+DiaDes+"))   )  as dia ";
              rstLoc=stmLoc.executeQuery(strSql2);
              if(rstLoc.next()){
                  DiaDes=DiaDes+rstLoc.getInt("dia");
              }
              rstLoc.close();
              rstLoc=null;



              intNumSem++;
             
              strSql+=" ,(select sum(nd_mondoc) as valcob from tbm_cabpag as a " +
              " where co_emp=x.co_emp and co_loc=x.co_loc and co_tipdoc=x.co_tipdoc and  st_reg not in ('E','I' ) and fe_doc " +
              "  between  current_date-"+(DiaDes-1)+"  and (current_date-"+(DiaHas)+")  ) as mes"+intNumSem+" ";

              DiaHas=DiaDes;


            }



              int intNumSemMes = (1*intNumMesCon);  //  (4*intNumMesCon);
              String strSumTotSem="";
              for(int i=1; i <= intNumMesCon; i++){
                  if(!strSumTotSem.equals("")){ strSumTotSem+=" + "; intNumSemMes--; }

                  strSumTotSem+="( case when mes"+intNumSemMes+" is null then 0 else mes"+intNumSemMes+" end )  ";  // end ) +

             }

           strSqlPri="SELECT *  ,(  "+strSumTotSem+"  ) as totfin   FROM ( " +
           " SELECT * "+strSql+" FROM ( " +
           "" +strEmp+ " "+
           " ) AS x "+
           " ) AS x ";


          // System.out.println(""+strSqlPri );


           rstLoc=stmLoc.executeQuery(strSqlPri);
           while(rstLoc.next()){
               java.util.Vector vecReg = new java.util.Vector();
                 vecReg.add(INT_TBL_DAT_LIN2, "");
                 vecReg.add(INT_TBL_DAT_CODEMP2, rstLoc.getString(1) );
                 vecReg.add(INT_TBL_DAT_CODLOC2, rstLoc.getString(2) );
                 vecReg.add(INT_TBL_DAT_NOMLOC2, rstLoc.getString(3) );
                 vecReg.add(INT_TBL_DAT_CODTIP2, rstLoc.getString(4) );
                 vecReg.add(INT_TBL_DAT_DCTIP2, rstLoc.getString(5) );
                 vecReg.add(INT_TBL_DAT_DLTIP2, rstLoc.getString(6) );

                  INT_SEM_NUE2=INT_TBL_DAT_DLTIP2;


                   intNumSemMes = (1*intNumMesCon)+1;  //  (4*intNumMesCon)+1;

                    for(int i=2; i <= intNumMesCon; i++){

                      INT_SEM_NUE2++;
                      intNumSemMes--;
                      vecReg.add(INT_SEM_NUE2, rstLoc.getString("mes"+intNumSemMes ) );

                    }



                 vecReg.add(INT_TBL_DAT_TOT12,rstLoc.getString("mes1") );
                 vecReg.add(INT_TBL_DAT_TOTFIN2, rstLoc.getString("totfin") );

                vecData.add(vecReg);
           }
           rstLoc.close();
           rstLoc=null;

           objTblModDat.setData(vecData);
           tblDat1 .setModel(objTblModDat);

           objTblTotDoc.calcularTotales();

         }


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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        spnSemCon = new javax.swing.JSpinner();
        chksem = new javax.swing.JCheckBox();
        chkmes = new javax.swing.JCheckBox();
        spnMesCon = new javax.swing.JSpinner();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmp = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel13 = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnDoctot = new javax.swing.JScrollPane();
        tblTotDoc = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        spnDoctot1 = new javax.swing.JScrollPane();
        tblTotDoc1 = new javax.swing.JTable();
        spnDat1 = new javax.swing.JScrollPane();
        tblDat1 = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
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
        jPanel1.add(lblTit);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel8.setPreferredSize(new java.awt.Dimension(685, 60));
        jPanel8.setLayout(null);
        jPanel8.add(spnSemCon);
        spnSemCon.setBounds(150, 10, 80, 20);

        chksem.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chksem.setSelected(true);
        chksem.setText("Semanas a consultar:");
        jPanel8.add(chksem);
        chksem.setBounds(10, 10, 140, 20);

        chkmes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkmes.setText("Meses a consultar:");
        jPanel8.add(chkmes);
        chkmes.setBounds(10, 30, 130, 20);
        jPanel8.add(spnMesCon);
        spnMesCon.setBounds(150, 30, 80, 20);

        jPanel3.add(jPanel8, java.awt.BorderLayout.NORTH);

        jSplitPane1.setDividerLocation(90);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setMinimumSize(new java.awt.Dimension(41, 122));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de empresas"));
        jPanel4.setPreferredSize(new java.awt.Dimension(324, 120));
        jPanel4.setLayout(new java.awt.BorderLayout());

        tblEmp.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblEmp);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel4);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel7.setPreferredSize(new java.awt.Dimension(657, 190));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jSplitPane2.setDividerLocation(80);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Semanal"));
        jPanel13.setLayout(new java.awt.BorderLayout());

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

        jPanel13.add(spnDat, java.awt.BorderLayout.CENTER);

        spnDoctot.setPreferredSize(new java.awt.Dimension(454, 18));

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
        spnDoctot.setViewportView(tblTotDoc);

        jPanel13.add(spnDoctot, java.awt.BorderLayout.SOUTH);

        jSplitPane2.setLeftComponent(jPanel13);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Meses"));
        jPanel14.setLayout(new java.awt.BorderLayout());

        spnDoctot1.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTotDoc1.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDoctot1.setViewportView(tblTotDoc1);

        jPanel14.add(spnDoctot1, java.awt.BorderLayout.SOUTH);

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
        spnDat1.setViewportView(tblDat1);

        jPanel14.add(spnDat1, java.awt.BorderLayout.CENTER);

        jSplitPane2.setRightComponent(jPanel14);

        jPanel7.add(jSplitPane2, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel7);

        jPanel3.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Filtro", jPanel3);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jPanel2.setPreferredSize(new java.awt.Dimension(690, 35));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jButton1.setFont(new java.awt.Font("SansSerif", 0, 11));
        jButton1.setText("Consultar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton1);

        jButton2.setFont(new java.awt.Font("SansSerif", 0, 11));
        jButton2.setText("Cerrar");
        jButton2.setPreferredSize(new java.awt.Dimension(79, 23));
        jPanel5.add(jButton2);

        jPanel2.add(jPanel5, java.awt.BorderLayout.EAST);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 690, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel6, java.awt.BorderLayout.WEST);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:

        cargarConfiguracion();

   
    }//GEN-LAST:event_formInternalFrameOpened

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

     if(chksem.isSelected()){

       intNumMesCon=Integer.parseInt(spnSemCon.getModel().getValue().toString());
       intNumSemCon=Integer.parseInt(spnSemCon.getModel().getValue().toString());

      if( intNumMesCon > 0 ){

           configurarFormDat();
           cargarMsnCabTblDat();
           cargarDatChqPenCob();
        
       }else{
           MensajeInf("NÚMERO DE SEMANA A CONSULTAR TIENE QUE SER MAYOR A 0 ");
       }
     }


     
     if(chkmes.isSelected()){
        intNumMesCon=Integer.parseInt(spnMesCon.getModel().getValue().toString());
      if( intNumMesCon > 0 ){

           configurarFormDatMes();
           cargarMsnCabTblDatMes();
           cargarDatChqPenCobMes();
       

       }else{
           MensajeInf("NÚMERO DE MESES A CONSULTAR TIENE QUE SER MAYOR A 0 ");
       }
     }




    }//GEN-LAST:event_jButton1ActionPerformed



      private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkmes;
    private javax.swing.JCheckBox chksem;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnDat1;
    private javax.swing.JScrollPane spnDoctot;
    private javax.swing.JScrollPane spnDoctot1;
    private javax.swing.JSpinner spnMesCon;
    private javax.swing.JSpinner spnSemCon;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDat1;
    private javax.swing.JTable tblEmp;
    private javax.swing.JTable tblTotDoc;
    private javax.swing.JTable tblTotDoc1;
    // End of variables declaration//GEN-END:variables



public String _getNomDat(int intCol){
  String strNom="";

  strNom="Corte de Fecha:\n "+strMsnCol[intCol];

  return strNom;
}


public String _getNomDatMes(int intCol){
  String strNom="";

  strNom="Corte de Fecha:\n "+strMsnCol2[intCol];

  return strNom;
}



private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblDat.columnAtPoint(evt.getPoint());
    String strMsg="";
    int intArr=-1;
    //switch (intCol){

//       case INT_TBL_DAT_LIN:
//           strMsg=_getNomDat(INT_TBL_DAT_LIN);
//          break;

    INT_SEM_NUE=INT_TBL_DAT_DLTIP;

    for(int i=2; i <= intNumSemCon; i++){
 
          INT_SEM_NUE++;
          intArr++;
          if( intCol == INT_SEM_NUE ){
           strMsg=_getNomDat(intArr);
           break;
          }
//          INT_SEM_NUE++;
//          intArr++;
//          if( intCol == INT_SEM_NUE ){
//           strMsg=_getNomDat(intArr);
//           break;
//          }
//
//         INT_SEM_NUE++;
//         intArr++;
//          if( intCol == INT_SEM_NUE ){
//           strMsg=_getNomDat(intArr);
//           break;
//          }
//
//          INT_SEM_NUE++;
//          intArr++;
//          if( intCol == INT_SEM_NUE ){
//           strMsg=_getNomDat(intArr);
//           break;
//          }
    }

intArr++;
//     if( intCol == INT_TBL_DAT_TOT4 ){
//        strMsg=_getNomDat(intArr);
//     }

//     else intArr++; if( intCol == INT_TBL_DAT_TOT3 )
//         strMsg=_getNomDat(intArr);
//
//    else intArr++; if( intCol == INT_TBL_DAT_TOT2 )
//        strMsg=_getNomDat(intArr);
  
   

     //else intArr++;
     if( intCol == INT_TBL_DAT_LUN )
        strMsg=_getNomDat(intArr);
    
     else intArr++; if( intCol == INT_TBL_DAT_MAR )
        strMsg=_getNomDat(intArr);
    
     else intArr++; if( intCol == INT_TBL_DAT_MIE )
        strMsg=_getNomDat(intArr);
   
  else intArr++; if( intCol == INT_TBL_DAT_JUE )
        strMsg=_getNomDat(intArr);
   
   else intArr++; if( intCol == INT_TBL_DAT_VIE )
        strMsg=_getNomDat(intArr);
   
   else intArr++; if( intCol == INT_TBL_DAT_SAB )
        strMsg=_getNomDat(intArr);
   
   else intArr++; if( intCol == INT_TBL_DAT_DOM )
        strMsg=_getNomDat(intArr);


  else intArr++; if( intCol == INT_TBL_DAT_TOT1 )
        strMsg=_getNomDat(intArr);



   
    tblDat.getTableHeader().setToolTipText(strMsg);
}
}




private class ZafMouMotAdaMes extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblDat1.columnAtPoint(evt.getPoint());
    String strMsg="";
    int intArr=-1;


    INT_SEM_NUE2=INT_TBL_DAT_DLTIP2;

    for(int i=2; i <= intNumMesCon; i++){

          INT_SEM_NUE2++;
          intArr++;
          if( intCol == INT_SEM_NUE2 ){
           strMsg=_getNomDatMes(intArr);
           break;
          }

    }

intArr++;


   
  if( intCol == INT_TBL_DAT_TOT12 )
        strMsg=_getNomDatMes(intArr);




    tblDat1.getTableHeader().setToolTipText(strMsg);
}
}




  protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }


  
}
