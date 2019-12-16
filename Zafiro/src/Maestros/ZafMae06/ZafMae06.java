/*
 * ZafMae06.java    chkEsItmSer
 * Esta clase sirve para almacenar los registros de inventario  System.out
 * Created on 11 de octubre de 2004, 9:20   tbm_invmae
 */
     
package Maestros.ZafMae06;
  
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;    
import java.sql.SQLException;
import javax.swing.JOptionPane;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafParSis.ZafParSis;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;


import Librerias.ZafVenCon.ZafVenCon;  //**************************
import java.util.ArrayList;  //*******************
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import java.awt.Color;
import java.util.Random;

/**
 * @author  kcerezo
 * corregido por jsalazar date: 12/Oct/2005
 * ultima actualizacion : 16/Ene/2006
 */

public class ZafMae06 extends javax.swing.JInternalFrame {
    Connection conCab, conRemGlo=null;;       //Variable para conexiÃ³n a la Base de Datos
    Statement stmCab;        //Variable para ejecuciÃ³n de sentencias SQL
    ResultSet rstCab;        //Variable para manipular registro de la tabla en ejecuciÃ³n
    Librerias.ZafInventario.ZafInventario objInventario;
    String strSql;        //Variable de tipo cadena en la cual se almacenarÃ¡ el Query 
    String strSqlAux;     //Variable de tipo cadena en la cual se almacenarÃ¡ el Query Auxiliar
    String strMsg;        //Variable de tipo cadena para enviar los mensajes por pantalla
    String strFil;        //Filtro para almacenar el criterio de bÃºsqueda actual
    String strAux;        //Variable auxiliar de tipo string la cual servira para guardar aquellas cadenas en las cuales me esten enviando algun caracter invalido
    String strPat;        //PatrÃ³n
    String strFec;        //Fecha formateada de acuerdo al patrÃ³n
    boolean blnCmb = false; //Detecta si se realizaron cambios en el documento dentro de las caja de texto
    boolean blnCln = false; //Si los TextField estan vacios
    final String strTit = "Sistema Zafiro"; //Constante del titulo del mensaje
    ToolBar objTooBar;       //Objeto de tipo ToolBar para poder manipular la clase ZafToolBar
    ZafUtil objUti;       //Objeto del tipo de la clase ZafUtil, el cual me va a permitir manipular los diferentes metodos de esta clase
    ZafParSis objZafParSis;  //Objeto que me permitira obtener los parametros del sistema, como podria ser la ruta de la base de datos, etc.  
    Calendar objCal;      //Objeto que me permitira obtener la fecha del sistema  
    JOptionPane oppMsg;   //Objeto de tipo OptionPane para presentar Mensajes
    LisTxt objLisCmb;     //Instancia de clase que detecta cambios
    javax.swing.JInternalFrame jfrThis;   
    private final String VERSION = " v 3.3 ";
    Librerias.ZafObtConCen.ZafObtConCen  objObtConCen;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblModCla, objTblModPrv;
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButCla, objTblCelRenButPrv;
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk  objTblCelRenChkP,objTblCelRenChkA, objTblCelRenChkI, objTblRenChkClaA, objTblRenChkClaI;        //Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk  objTblCelEdiChkP,objTblCelEdiChkA, objTblCelEdiChkI, objTblEdiChkClaA, objTblEdiChkClaI;        //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoPrv;

    ZafVenCon objVenConItm; //*****************  
    ZafVenCon objVenConUni; //*****************  
    ZafVenCon objVenConLin; //*****************  
    ZafVenCon objVenConGrp; //*****************  
    ZafVenCon objVenConMar; //*****************  
    ZafVenCon objVenConPrv; //*****************
    ZafVenCon objVenConCla; //*****************

    int intEst=0;
    String strItm="";
         
    private String
    strCodLin="", strDesLarLin="",
    strCodGrp="", strDesLarGrp="",
    strCodMar="", strDesLarMar="", 
    strCodUni="", strDesLarUni="";   

    int INTCODREGCEN=0;
    int INTVERCONCEN=0;

   // DATOS DE LA TABLA DE CLASIFICACIONES
   final int INT_TBL_CLALIN=0;
   final int INT_TBL_CLACOD=1;
   final int INT_TBL_CLABUT=2;
   final int INT_TBL_CLANOM=3;
   final int INT_TBL_CLAACT=4;
   final int INT_TBL_CLAINA=5;
   final int INT_TBL_CODREGCLA=6;
   final int INT_TBL_CODGRP=7;
   //Constantes del ArrayList Elementos Eliminados
   final int INT_ARR_CODCLA   = 0;
   //****************************

   // DATOS DE LA TABLA DE PROVEEDORES
   final int INT_TBL_PRVLIN=0;
   final int INT_TBL_PRVCODCLI=1;
   final int INT_TBL_PRVBUTCLI=2;
   final int INT_TBL_PRVNOMCLI=3;
   final int INT_TBL_PRVACT=4;
   final int INT_TBL_PRVINA=5;
   final int INT_TBL_PRVPRE=6;
   final int INT_TBL_CODREGPRV=7;
   //Constantes del ArrayList Elementos Eliminados
   final int INT_ARR_CODPRV   = 0;
   //****************************
   private ZafHisTblBasDat objHisTblBasDat;
   private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.

    /** Creates new form ZafMae06 */
    public ZafMae06(ZafParSis obj) {
        initComponents();
        addListenerCambios();
      try{
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jfrThis    = this;
            jfrThis.setTitle(objZafParSis.getNombreMenu()+ " " + VERSION);  
            lblTit.setText(objZafParSis.getNombreMenu() );            
            objTooBar = new ToolBar(this);
            objInventario= new Librerias.ZafInventario.ZafInventario(this, objZafParSis ); 
            objHisTblBasDat=new ZafHisTblBasDat();
            objUti = new ZafUtil();
            oppMsg = new JOptionPane();    
            txtCodAltAux.setVisible(false);
            txtStkMinAux.setVisible(false);
            txtStkMaxAux.setVisible(false);
            txaObs1.setLineWrap(true);
            txaObs1.setWrapStyleWord(true);
            txaObs2.setLineWrap(true);
            txaObs2.setWrapStyleWord(true);
            spnObs1.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            spnObs1.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            spnObs2.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            spnObs2.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            deshabilitarDatInv();
            txtNomItm.setBackground(objZafParSis.getColorCamposObligatorios());
            txtCodItm.setBackground(objZafParSis.getColorCamposSistema());
            txtCosUni.setEnabled(false);
            txtCodItm.setEnabled(false);
            txtStkAct.setEnabled(false);
         //   this.setTitle(objZafParSis.getNombreMenu());

            objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objZafParSis);
            INTCODREGCEN=objObtConCen.intCodReg;

            

            /**Alineando las cajas de texto que contendran valores numericos*/
            txtCodItm.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCosUni.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCosUni1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCosUni2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCosUni3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtStkAct.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtStkMin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtStkMax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodAlt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodAlt2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodLin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodMar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodGrp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodUniMed.setHorizontalAlignment(javax.swing.JTextField.RIGHT); 
            txtItmUni.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

           
            this.getContentPane().add(objTooBar, "South");

            strPat = "dd/MM/yyyy";
            SimpleDateFormat sdfFec = new SimpleDateFormat (strPat);
            Date datFec = new Date();
            strFec = sdfFec.format(datFec);
            txtCosUni.setEnabled(false);
            txtCosUni1.setEnabled(false);
            txtCosUni2.setEnabled(false);
            txtCosUni3.setEnabled(false);
            this.setBounds(10,10, 700, 450);
            //System.out.println(strFec);
     }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
      }        
    }
    

   


private void ConfigurarTblClasificacion() {
  try{
    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();
    vecCab.add(INT_TBL_CLALIN,"");
    vecCab.add(INT_TBL_CLACOD,"Cod.Cla");
    vecCab.add(INT_TBL_CLABUT,"..");
    vecCab.add(INT_TBL_CLANOM,"Clasificacón.");
    vecCab.add(INT_TBL_CLAACT,"Activo.");
    vecCab.add(INT_TBL_CLAINA,"Inactivo");
    vecCab.add(INT_TBL_CODREGCLA,"Cod.Reg.");
    vecCab.add(INT_TBL_CODGRP,"Cod.Grp.");

    objTblModCla=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblModCla.setHeader(vecCab);
    tblCla.setModel(objTblModCla);
    tblCla.setRowSelectionAllowed(false);
    tblCla.setCellSelectionEnabled(true);
    tblCla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    new Librerias.ZafColNumerada.ZafColNumerada(tblCla,INT_TBL_CLALIN);

    //tblCla.getModel().addTableModelListener(new LisCambioTblAcc());
    tblCla.getTableHeader().setReorderingAllowed(false);


    tblCla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblCla.getColumnModel();

    tcmAux.getColumn(INT_TBL_CLALIN).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CLACOD).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_CLABUT).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CLANOM).setPreferredWidth(450);
    tcmAux.getColumn(INT_TBL_CLAACT).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_CLAINA).setPreferredWidth(50);

    objTblRenChkClaA = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    objTblRenChkClaI = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    tcmAux.getColumn(INT_TBL_CLAACT).setCellRenderer(objTblRenChkClaA);
    tcmAux.getColumn(INT_TBL_CLAINA).setCellRenderer(objTblRenChkClaI);

    objTblEdiChkClaA = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_CLAACT).setCellEditor(objTblEdiChkClaA);

    objTblEdiChkClaI = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_CLAINA).setCellEditor(objTblEdiChkClaI);

    
     objTblEdiChkClaA.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            int intNumFil = tblCla.getSelectedRow();
              tblCla.setValueAt(new Boolean(true), intNumFil, INT_TBL_CLAACT);
              tblCla.setValueAt(new Boolean(false), intNumFil, INT_TBL_CLAINA);
         }
    });

    objTblEdiChkClaI.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
        }

        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            int intNumFil = tblCla.getSelectedRow();
              tblCla.setValueAt(new Boolean(false), intNumFil, INT_TBL_CLAACT);
              tblCla.setValueAt(new Boolean(true), intNumFil, INT_TBL_CLAINA);

        }
    });


     java.util.ArrayList arlAux=new java.util.ArrayList();
    //Configurar ZafTblMod: Establecer las columnas ELIMINADAS
    arlAux=new java.util.ArrayList();
    arlAux.add("" + INT_TBL_CODREGCLA);
    objTblModCla.setColsSaveBeforeRemoveRow(arlAux);
    arlAux=null;

      ArrayList arlColHid=new ArrayList();
      arlColHid.add(""+INT_TBL_CODREGCLA);
      arlColHid.add(""+INT_TBL_CODGRP); 
      objTblModCla.setSystemHiddenColumns(arlColHid, tblCla);
      arlColHid=null;
 
    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
    vecAux.add("" + INT_TBL_CLABUT);
    vecAux.add("" + INT_TBL_CLAACT);
    vecAux.add("" + INT_TBL_CLAINA);
    objTblModCla.setColumnasEditables(vecAux);
    vecAux=null;


    objTblCelRenButCla=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
    tcmAux.getColumn(INT_TBL_CLABUT).setCellRenderer(objTblCelRenButCla);
    objTblCelRenButCla=null;
    new ButCla(tblCla, INT_TBL_CLABUT);   //*****

    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblCla);
    tcmAux=null;
    objTblModCla.setModoOperacion(objTblModCla.INT_TBL_INS);

    new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblCla);

  }catch(Exception e) {   objUti.mostrarMsgErr_F1(this, e); }
}

private void ConfigurarTblProveedores() {
  try{
    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();
    vecCab.add(INT_TBL_PRVLIN,"");
    vecCab.add(INT_TBL_PRVCODCLI,"Cod.Cli");
    vecCab.add(INT_TBL_PRVBUTCLI,"..");
    vecCab.add(INT_TBL_PRVNOMCLI,"Nom.Cli.");
    vecCab.add(INT_TBL_PRVACT,"Activo");
    vecCab.add(INT_TBL_PRVINA,"Inactivo");
    vecCab.add(INT_TBL_PRVPRE,"Predeterminado");
    vecCab.add(INT_TBL_CODREGPRV,"Cod.Reg.Prv");

    objTblModPrv=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblModPrv.setHeader(vecCab);
    tblPrv.setModel(objTblModPrv);
    tblPrv.setRowSelectionAllowed(false);
    tblPrv.setCellSelectionEnabled(true);
    tblPrv.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    new Librerias.ZafColNumerada.ZafColNumerada(tblPrv,INT_TBL_PRVLIN);

    //tblPrv.getModel().addTableModelListener(new LisCambioTblAcc());
    tblPrv.getTableHeader().setReorderingAllowed(false);

    tblPrv.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblPrv.getColumnModel();

    tcmAux.getColumn(INT_TBL_PRVLIN).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_PRVCODCLI).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_PRVBUTCLI).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_PRVNOMCLI).setPreferredWidth(390);
    tcmAux.getColumn(INT_TBL_PRVACT).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_PRVINA).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_PRVPRE).setPreferredWidth(50);


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
        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            int intNumFil = tblPrv.getSelectedRow();
            for(int x=0; x<tblPrv.getRowCount(); x++){
                tblPrv.setValueAt(new Boolean(false), x, INT_TBL_PRVPRE);
             }
            tblPrv.setValueAt(new Boolean(true), intNumFil, INT_TBL_PRVPRE);
            tblPrv.setValueAt(new Boolean(false), intNumFil, INT_TBL_PRVACT);
            tblPrv.setValueAt(new Boolean(false), intNumFil, INT_TBL_PRVINA);

        }
    });


      objTblCelEdiChkA.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            int intNumFil = tblPrv.getSelectedRow();
              tblPrv.setValueAt(new Boolean(false), intNumFil, INT_TBL_PRVPRE);
              tblPrv.setValueAt(new Boolean(true), intNumFil, INT_TBL_PRVACT);
              tblPrv.setValueAt(new Boolean(false), intNumFil, INT_TBL_PRVINA);
         }
    });



            objTblCelEdiChkI.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                }
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblPrv.getSelectedRow();
                      tblPrv.setValueAt(new Boolean(false), intNumFil, INT_TBL_PRVPRE);
                      tblPrv.setValueAt(new Boolean(false), intNumFil, INT_TBL_PRVACT);
                      tblPrv.setValueAt(new Boolean(true), intNumFil, INT_TBL_PRVINA);

                }
            });



    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
    vecAux.add("" + INT_TBL_PRVCODCLI);
    vecAux.add("" + INT_TBL_PRVBUTCLI);
    vecAux.add("" + INT_TBL_PRVPRE);
    vecAux.add("" + INT_TBL_PRVACT);
    vecAux.add("" + INT_TBL_PRVINA);
    objTblModPrv.setColumnasEditables(vecAux);
    vecAux=null;

    java.util.ArrayList arlAux=new java.util.ArrayList();
    //Configurar ZafTblMod: Establecer las columnas ELIMINADAS
    arlAux=new java.util.ArrayList();
    arlAux.add("" + INT_TBL_CODREGPRV);
    objTblModPrv.setColsSaveBeforeRemoveRow(arlAux);
    arlAux=null;

      ArrayList arlColHid=new ArrayList();
      arlColHid.add(""+INT_TBL_CODREGPRV);
      objTblModPrv.setSystemHiddenColumns(arlColHid, tblPrv);
      arlColHid=null;

    int intColVen3[]=new int[2];
    intColVen3[0]=1;
    intColVen3[1]=2;
    int intColTbl3[]=new int[2];
    intColTbl3[0]=INT_TBL_PRVCODCLI;
    intColTbl3[1]=INT_TBL_PRVNOMCLI;
    objTblCelEdiTxtVcoPrv=new ZafTblCelEdiTxtVco(tblPrv, objVenConPrv, intColVen3, intColTbl3);  //********
    tcmAux.getColumn(INT_TBL_PRVCODCLI).setCellEditor(objTblCelEdiTxtVcoPrv);  //******
    objTblCelEdiTxtVcoPrv.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {   //******
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            int intNumFil = tblPrv.getSelectedRow();
            tblPrv.setValueAt(new Boolean(true),intNumFil, INT_TBL_PRVACT);
       }
    });

    objTblCelRenButPrv=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
    tcmAux.getColumn(INT_TBL_PRVBUTCLI).setCellRenderer(objTblCelRenButPrv);
    objTblCelRenButPrv=null;
    new ButPrv(tblPrv, INT_TBL_PRVBUTCLI);   //*****



    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblPrv);
    tcmAux=null;
    objTblModPrv.setModoOperacion(objTblModPrv.INT_TBL_INS);

    new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblPrv);

  }catch(Exception e) {   objUti.mostrarMsgErr_F1(this, e); }
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
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.st_reg NOT IN ('E') ) AS x ";
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


private class ButCla extends Librerias.ZafTableColBut.ZafTableColBut_uni{
 public ButCla(javax.swing.JTable tbl, int intIdx){
 super(tbl,intIdx, "Cotización.");
}
public void butCLick() {
   int intNumFil = tblCla.getSelectedRow();

   if(intNumFil >= 0 )
    listaClasificaciones(intNumFil );
}
}

private void listaClasificaciones(int intNumFil){
 objVenConCla.setTitle("Listado de Clasificaciones");
 objVenConCla.show();
 if (objVenConCla.getSelectedButton()==objVenConCla.INT_BUT_ACE) {
    tblCla.setValueAt(objVenConCla.getValueAt(3),intNumFil ,INT_TBL_CLACOD);
    tblCla.setValueAt(objVenConCla.getValueAt(4),intNumFil, INT_TBL_CLANOM);
    tblCla.setValueAt(new Boolean(true),intNumFil, INT_TBL_CLAACT);
    tblCla.setValueAt(objVenConCla.getValueAt(1),intNumFil, INT_TBL_CODGRP);
 }
}




private class ButPrv extends Librerias.ZafTableColBut.ZafTableColBut_uni{
 public ButPrv(javax.swing.JTable tbl, int intIdx){
 super(tbl,intIdx, "Cotización.");
}
public void butCLick() {
   int intNumFil = tblPrv.getSelectedRow();

   if(intNumFil >= 0 ) 
    listaProveedor(intNumFil );
   
}
}

private void listaProveedor(int intNumFil){
 objVenConPrv.setTitle("Listado de Proveedores");
 objVenConPrv.show();
 if (objVenConPrv.getSelectedButton()==objVenConPrv.INT_BUT_ACE) {
    tblPrv.setValueAt(objVenConPrv.getValueAt(1),intNumFil ,INT_TBL_PRVCODCLI);
    tblPrv.setValueAt(objVenConPrv.getValueAt(2),intNumFil, INT_TBL_PRVNOMCLI);
    tblPrv.setValueAt(new Boolean(true),intNumFil, INT_TBL_PRVACT);
 }
}




public boolean abrirConRem(){
    boolean blnres=false;
    try{
        int intIndEmp=INTCODREGCEN;
        if(intIndEmp != 0){
            conRemGlo=DriverManager.getConnection(objZafParSis.getStringConexion(intIndEmp), objZafParSis.getUsuarioBaseDatos(intIndEmp), objZafParSis.getClaveBaseDatos(intIndEmp));
            conRemGlo.setAutoCommit(false);
        }else {
            conRemGlo=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            conRemGlo.setAutoCommit(false);
        }
        blnres=true;
    }
    catch (java.sql.SQLException e) {
        mostrarMsg("NO SE PUEDE ESTABLECER LA CONEXION REMOTA CON LA BASE CENTRAL..");
        INTVERCONCEN=1;
        return false;
    }
    return blnres;
}



private void mostrarMsg(String strMsg) {
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.OK_OPTION);
}


    
    
    public ZafMae06(ZafParSis obj,String strcoditm) {
        initComponents();
        addListenerCambios();
      try{
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jfrThis    = this;
            jfrThis.setTitle(objZafParSis.getNombreMenu()+ VERSION);        
            objTooBar = new ToolBar(this);
            objUti = new ZafUtil();
            oppMsg = new JOptionPane();    
            txtCodAltAux.setVisible(false);
            txtStkMinAux.setVisible(false);
            txtStkMaxAux.setVisible(false);
            txaObs1.setLineWrap(true);
            txaObs1.setWrapStyleWord(true);
            txaObs2.setLineWrap(true);
            txaObs2.setWrapStyleWord(true);
            spnObs1.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            spnObs1.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            spnObs2.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            spnObs2.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            deshabilitarDatInv();
            txtNomItm.setBackground(objZafParSis.getColorCamposObligatorios());
            txtCodItm.setBackground(objZafParSis.getColorCamposSistema());
            txtCosUni.setEnabled(false);
            txtCodItm.setEnabled(false);
            txtStkAct.setEnabled(false);
         //   this.setTitle(objZafParSis.getNombreMenu());

            /**Alineando las cajas de texto que contendran valores numericos*/
            txtCodItm.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCosUni.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCosUni1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCosUni2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCosUni3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtStkAct.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtStkMin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtStkMax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodAlt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodAlt2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodLin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodMar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodGrp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodUniMed.setHorizontalAlignment(javax.swing.JTextField.RIGHT); 
            txtItmUni.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

            this.getContentPane().add(objTooBar, "South");

            intEst=1;
            txtCodItm.setText(""+strcoditm);
            
            strPat = "dd/MM/yyyy";
            SimpleDateFormat sdfFec = new SimpleDateFormat (strPat);
            Date datFec = new Date();
            strFec = sdfFec.format(datFec);
            txtCosUni.setEnabled(false);
            txtCosUni1.setEnabled(false);
            txtCosUni2.setEnabled(false);
            txtCosUni3.setEnabled(false);
            this.setBounds(10,10, 700, 450);
            
            if(objZafParSis.getCodigoMenu()==424){
                objTooBar.setVisible(false);
            }
            
            //System.out.println(strFec);
     }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
      }        
    }
   
    
    
    
    public void Configura_ventana_consulta(){
         configurarVenConItem();
         configurarVenConUnidad();
         configurarVenConLinea();
         configurarVenConGrupo();
         configurarVenConMarca();
         configurarVenConProveedor();
         configurarVenConClasificacion();
         ConfigurarTblClasificacion();
         ConfigurarTblProveedores();
     }
    
    
    
    
      private boolean configurarVenConMarca()
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_reg");
            arlCam.add("a.tx_desCor");
            arlCam.add("a.tx_desLar");
            arlCam.add("a.st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 4"; 
           
            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
    
    
      private boolean configurarVenConGrupo()
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_reg");
            arlCam.add("a.tx_desCor");
            arlCam.add("a.tx_desLar");
            arlCam.add("a.st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 3"; 
           
            objVenConGrp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
    
    
        private boolean configurarVenConLinea()
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_reg");
            arlCam.add("a.tx_desCor");
            arlCam.add("a.tx_desLar");
            arlCam.add("a.st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 2"; 
           
            objVenConLin=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
    
    
        private boolean configurarVenConUnidad()
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_reg");
            arlCam.add("a.tx_desCor");
            arlCam.add("a.tx_desLar");
            arlCam.add("a.st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp =5"; 
           
            objVenConUni=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
    
    
       private boolean configurarVenConItem()
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_itm");
            arlCam.add("a.tx_codAlt");
            arlCam.add("a.tx_nomItm");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("CódAlt.");
            arlAli.add("Nombre.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("100");
            arlAncCol.add("143");
            arlAncCol.add("300");
            //Armar la sentencia SQL. 
             String  strSQL="";
             strSQL+="SELECT a.co_itm,a.tx_codAlt,a.tx_nomItm FROM tbm_inv as a WHERE  a.st_reg not in('T','U') AND a.co_emp = " + objZafParSis.getCodigoEmpresa(); 
             
            objVenConItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
             
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
 
    private void MensajeInf(String strMensaje){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Zafiro.- Maestro Inventario";
            obj.showMessageDialog(jfrThis,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

      /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit=" Sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
      /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnCmb=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }   
    
  /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            if (!cargarCabReg())            
            {
                MensajeInf("Error al cargar registro");
                blnCmb=false;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }        
    
    
    
    

private boolean cargarTabPrv(java.sql.Connection conn, int intCodItm ){
 boolean blnRes=true;
 java.sql.ResultSet rst;
 java.sql.Statement stm;
 try{
   if(conn!=null){
     stm=conn.createStatement();
     strSql="";
     strSql="SELECT a.co_prv, a1.tx_nom, a.st_reg FROM tbr_prvinv AS a " +
     " INNER JOIN tbm_cli AS a1 ON(a1.co_emp=a.co_emp AND a1.co_cli=a.co_prv) " +
     " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_itm="+intCodItm+"  ORDER BY a.fe_ing ";
     rst = stm.executeQuery(strSql);
     Vector vecData = new Vector();
     while(rst.next()){
         java.util.Vector vecReg = new java.util.Vector();

         vecReg.add(INT_TBL_PRVLIN, "");
         vecReg.add(INT_TBL_PRVCODCLI, rst.getString("co_prv") );
         vecReg.add(INT_TBL_PRVBUTCLI,"··");
         vecReg.add(INT_TBL_PRVNOMCLI, rst.getString("tx_nom") );

         Boolean blnIvaP = new Boolean(rst.getString("st_reg").equals("P"));
         Boolean blnIvaA = new Boolean(rst.getString("st_reg").equals("A"));
         Boolean blnIvaI = new Boolean(rst.getString("st_reg").equals("I"));

         vecReg.add(INT_TBL_PRVACT, blnIvaA);
         vecReg.add(INT_TBL_PRVINA, blnIvaI);
         vecReg.add(INT_TBL_PRVPRE, blnIvaP);
         vecReg.add(INT_TBL_CODREGPRV, null );
        vecData.add(vecReg);
     }
     objTblModPrv.setData(vecData);
     tblPrv.setModel(objTblModPrv);
    rst.close();
    rst=null;
    stm.close();
    stm=null;

 }}catch (java.sql.SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
   catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}

private boolean cargarTabCla(java.sql.Connection conn, int intCodItm ){
 boolean blnRes=true;
 java.sql.ResultSet rst;
 java.sql.Statement stm;
 try{
   if(conn!=null){
     stm=conn.createStatement();
     strSql="";
     strSql="SELECT a.co_cla, a1.co_grp,  a1.tx_deslar, a.st_reg  FROM tbr_invcla AS a " +
     " INNER JOIN tbm_clainv AS a1 ON(a1.co_emp=a.co_emp and a1.co_cla=a.co_cla) " +
     " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_itm="+intCodItm+" ORDER BY a.fe_ing ";
     rst = stm.executeQuery(strSql);
     Vector vecData = new Vector();
     while(rst.next()){
         java.util.Vector vecReg = new java.util.Vector();

         vecReg.add(INT_TBL_CLALIN, "");
         vecReg.add(INT_TBL_CLACOD, rst.getString("co_cla") );
         vecReg.add(INT_TBL_CLABUT,"··");
         vecReg.add(INT_TBL_CLANOM, rst.getString("tx_deslar") );

         Boolean blnIvaA = new Boolean(rst.getString("st_reg").equals("A"));
         Boolean blnIvaI = new Boolean(rst.getString("st_reg").equals("I"));

         vecReg.add(INT_TBL_CLAACT, blnIvaA);
         vecReg.add(INT_TBL_CLAINA, blnIvaI);
         vecReg.add(INT_TBL_CODREGCLA, null );
         vecReg.add(INT_TBL_CODGRP, rst.getString("co_grp") );
        vecData.add(vecReg);
     }
     objTblModCla.setData(vecData);
     tblCla.setModel(objTblModCla);
    rst.close();
    rst=null;
    stm.close();
    stm=null;

 }}catch (java.sql.SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
   catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}


    
    
    
private boolean cargarCabReg(){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc, rstLoc01;
 int intPosRel;
 String strSql="";
 try{
    if(abrirConRem()){
     if(conRemGlo!=null){
       
       stmLoc=conRemGlo.createStatement();
       stmLoc01=conRemGlo.createStatement();

      strSql="SELECT * FROM tbm_inv Where st_reg not in('T','E') and co_emp="+rstCab.getString("co_emp")+" AND co_itm="+rstCab.getString("co_itm");
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
        txtCodItm.setText(rstLoc.getString("co_itm"));
        txtCodAlt.setText(((rstLoc.getString("tx_codAlt")==null)?"":rstLoc.getString("tx_codAlt")));
        txtCodAlt2.setText(((rstLoc.getString("tx_codAlt2")==null)?"":rstLoc.getString("tx_codAlt2")));
        txtNomItm.setText(((rstLoc.getString("tx_nomItm")==null)?"":rstLoc.getString("tx_nomItm")));
        txtItmUni.setText(((rstLoc.getString("nd_itmUni")==null)?"":rstLoc.getString("nd_itmUni")));
        txtCosUni.setText(((rstLoc.getString("nd_cosUni")==null)?"":rstLoc.getString("nd_cosUni")));
        txtCosUni1.setText(((rstLoc.getString("nd_preVta1")==null)?"":rstLoc.getString("nd_preVta1")));
        txtCosUni2.setText(((rstLoc.getString("nd_preVta2")==null)?"":rstLoc.getString("nd_preVta2")));
        txtCosUni3.setText(((rstLoc.getString("nd_preVta3")==null)?"":rstLoc.getString("nd_preVta3")));
        txtStkAct.setText(((rstLoc.getString("nd_stkAct")==null)?"":rstLoc.getString("nd_stkAct")));
        txtStkMin.setText(((rstLoc.getString("nd_stkMin")==null)?"":rstLoc.getString("nd_stkMin")));
        txtStkMax.setText(((rstLoc.getString("nd_stkMax")==null)?"":rstLoc.getString("nd_stkMax")));
        //txtCodLin.setText(((rstLoc.getString("co_cla1")==null)?"":rstLoc.getString("co_cla1")));
        spnPeso.setValue(new Double(rstLoc.getDouble("nd_pesitmkgr")));

        if(rstLoc.getString("st_blqPreVta")==null) chkCamPreVta.setSelected(false);
        else  chkCamPreVta.setSelected(true);
        
        /* JM: 16/Feb/2018 Modificacion para trabajar con el nuevo estado de tbm_inv.st_oblCamNomItm, Estado de obligatorio cambiar nombre del item. */
        if(rstLoc.getString("st_oblCamNomItm")==null){
            chkOblCamNomItm.setSelected(false);
        }
        else{
            chkOblCamNomItm.setSelected(true);
        }


        txaObs1.setText(((rstLoc.getString("tx_obs1")==null)?"":rstLoc.getString("tx_obs1")));
        txaObs2.setText(((rstLoc.getString("tx_obs2")==null)?"":rstLoc.getString("tx_obs2")));

        if(rstLoc.getString("st_reg").equals("I")) cboEst.setSelectedIndex(1);
        else cboEst.setSelectedIndex(0);

        if(rstLoc.getString("st_ivaCom").equals("S")) chkIvaCom.setSelected(true);
        else chkIvaCom.setSelected(false);

        if(rstLoc.getString("st_ivaVen").equals("S")) chkIvaVta.setSelected(true);
        else chkIvaVta.setSelected(false);
                      
       chkEsItmSer.setSelected(false);
       chkEsItmTra.setSelected(false);
       chkEsItmOtr.setSelected(false);


        if(rstLoc.getString("st_ser").equals("S")) chkEsItmSer.setSelected(true);
        if(rstLoc.getString("st_ser").equals("T")) chkEsItmTra.setSelected(true);
        if(rstLoc.getString("st_ser").equals("O")) chkEsItmOtr.setSelected(true);


        chkpermodnomven.setSelected(false);
        if(rstLoc.getString("st_permodnomitmven").equals("S")) chkpermodnomven.setSelected(true);
        chkpermodnomcom.setSelected(false);
        if(rstLoc.getString("st_permodnomitmcom").equals("S")) chkpermodnomcom.setSelected(true);


        String strAux=rstLoc.getString("st_reg");
        if (strAux.equals("A"))
            strAux="Activo";
        else if (strAux.equals("I"))
            strAux="Anulado";
        else
            strAux="Otro";
        objTooBar.setEstadoRegistro(strAux);
                    
                    
        //*********************************************************************************  
        String strfec = String.valueOf(rstLoc.getDate("fe_ing"));
        if(strfec.equalsIgnoreCase("null")) strfec="";
        String strfec2= String.valueOf(rstLoc.getDate("fe_ultmod"));
        if(strfec2.equalsIgnoreCase("null")) strfec2="";

        txtfecing.setText(" "+strfec );
        txtfecmod.setText(" "+ strfec2 );
                         
        strSql="SELECT ( SELECT tx_nom FROM tbm_usr WHERE co_usr="+rstLoc.getInt("co_usring")+" ) AS nom1 "+
        " ,( SELECT tx_nom FROM tbm_usr WHERE co_usr="+rstLoc.getInt("co_usrmod")+" ) AS nom2 ";
        rstLoc01=stmLoc01.executeQuery(strSql);
        if (rstLoc01.next()){
            lblusuing.setText(" "+ ((rstLoc01.getString("nom1")==null)?"":rstLoc01.getString("nom1")) );
            lblusuing1.setText(" "+ ((rstLoc01.getString("nom2")==null)?"":rstLoc01.getString("nom2")) );
        }
        rstLoc01.close();
        rstLoc01=null;
        //********************************************************************************* 
                    
                    
        if (!txtCodLin.getText().equals("")){
            strSql = "";
            strSql = "SELECT tx_desLar FROM tbm_var WHERE co_reg = " + txtCodLin.getText() .replaceAll("'", "''")+ " AND co_grp = 2";
            rstLoc01 = stmLoc01.executeQuery(strSql);
            if (rstLoc01.next())
                txtLin.setText(((rstLoc01.getString("tx_desLar")==null)?"":rstLoc01.getString("tx_desLar")));
             rstLoc01.close();
             rstLoc01=null;
        }else{
            txtLin.setText("");}
                        
        //txtCodGrp.setText(((rstCab.getString("co_cla2")==null)?"":rstCab.getString("co_cla2")));
        if (!txtCodGrp.getText().equals("")){
            strSql = "";
            strSql = "SELECT tx_desLar FROM tbm_var WHERE co_reg = " + txtCodGrp.getText().replaceAll("'", "''") + " AND co_grp = 3";
            rstLoc01 = stmLoc01.executeQuery(strSql);
            if (rstLoc01.next())
                txtGrp.setText(((rstLoc01.getString("tx_desLar")==null)?"":rstLoc01.getString("tx_desLar")));
             rstLoc01.close();
             rstLoc01=null;
        }else{
            txtGrp.setText("");}
                    
        //txtCodMar.setText(((rstCab.getString("co_cla3")==null)?"":rstCab.getString("co_cla3")));
        if (!txtCodMar.getText().equals("")){
            strSql = "";
            strSql = "SELECT tx_desLar FROM tbm_var WHERE co_reg = " + txtCodMar.getText().replaceAll("'", "''") + " AND co_grp = 4";
            rstLoc01 = stmLoc01.executeQuery(strSql);
            if (rstLoc01.next())
                txtMar.setText(((rstLoc01.getString("tx_desLar")==null)?"":rstLoc01.getString("tx_desLar")));
           rstLoc01.close();
           rstLoc01=null;
        }else{
            txtMar.setText("");}

        txtCodUniMed.setText(((rstCab.getString("co_uni")==null)?"":rstCab.getString("co_uni")));
        if (!txtCodUniMed.getText().equals("")){
            strSql = "";
            strSql = "SELECT tx_desLar FROM tbm_var WHERE co_reg = " + txtCodUniMed.getText().replaceAll("'", "''")+ " AND co_grp = 5";
            rstLoc01 = stmLoc01.executeQuery(strSql);
            if (rstLoc01.next())
                txtUniMed.setText(((rstLoc01.getString("tx_desLar")==null)?"":rstLoc01.getString("tx_desLar")));
             rstLoc01.close();
             rstLoc01=null;
        }else{
            txtUniMed.setText("");}

        cargarTabPrv(conRemGlo, rstLoc.getInt("co_itm") );
        cargarTabCla(conRemGlo, rstLoc.getInt("co_itm") );

    }
    else
    {
        objTooBar.setEstadoRegistro("Eliminado");
        txtCln();
    }
            
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
    conRemGlo.close();
    conRemGlo=null;

    intPosRel=rstCab.getRow();
    rstCab.last();
    objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
    rstCab.absolute(intPosRel);
    blnCmb=false;
    blnRes=true;

   }}}catch (java.sql.SQLException e){   blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
      catch (Exception e){   blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
      return blnRes;
  }



    
    public class ToolBar extends ZafToolBar {
    
        public ToolBar (javax.swing.JInternalFrame ifrTmp) {
            super (ifrTmp, objZafParSis);
        }
        public boolean beforeInsertar()
        {
            return true;
        }
        
        public boolean beforeConsultar()
        {
            return true;
        }

        public boolean beforeModificar()
        {
            return true;
        }

        public boolean beforeEliminar()
        {
            return true;
        }

        public boolean beforeAnular()
        {
            return true;
        }

        public boolean beforeImprimir()
        {
            return true;
        }

        public boolean beforeVistaPreliminar()
        {
            return true;
        }

        public boolean beforeAceptar()
        {
            return true;
        }
        
        public boolean beforeCancelar()
        {
            return true;
        }
        
        public boolean anular()
        {
            String strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            if(itemSinStock()){
                if (!anularReg())
                return false;
            }else{
                MensajeInf("El Item que desea anular posee Stock.\nFavor revisar y volver a intentarlo.");
                return false;
            }
            
            objTooBar.setEstadoRegistro("Anulado");
            blnCmb=false;
            return true;
        }

        public void clickAnterior(){
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnCmb)
                    {
                        if (isRegPro())
                        {
                            rstCab.previous();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.previous();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickFin() {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnCmb)
                    {
                        if (isRegPro())
                        {
                            rstCab.last();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.last();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInicio(){
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnCmb)
                    {
                        if (isRegPro())
                        {
                            rstCab.first();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.first();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInsertar()
        {
            try
            {
                if (blnCmb)
                {
                    isRegPro();
                }
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    rstCab=null;
                    stmCab=null;
                }

                limpiarDatInv();
                habilitarDatInv();
                cboEst.setSelectedIndex(0);
                txtStkAct.setText("0");
                txtCodItm.setEditable(false);
                butCod.setEnabled(false);
                chkIvaCom.setSelected(true);
                chkIvaVta.setSelected(true);
                txtCodAlt.requestFocus();
                blnCmb=false;
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickSiguiente(){
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnCmb)
                    {
                        if (isRegPro())
                        {
                            rstCab.next();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.next();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public boolean consultar() 
        {
            consultarReg();
            return true;
        }

        public boolean eliminar()
        {
            try
            {
                String strAux=objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado"))
                {
                    MensajeInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                    return false;
                }
                if (!eliminarReg())
                    return false;
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast())
                {
                    rstCab.next();
                    cargarReg();
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    txtCln();
                }
                blnCmb=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }
        public void clickCancelar() {
            
        }

        public boolean insertar()
        {
            if (!insertarReg())
                return false;
            txtCln();
            blnCmb=false;
            return true;
        }

        public boolean modificar()
        {
            String strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                MensajeInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                deshabilitarDatInv();
                if (mensaje("Para modificar primero debe reactivar el item \n ¿Desea reactivarlo?")==JOptionPane.YES_OPTION){
                    habilitarDatInv();
                    return Reactivar();
                }

            }
            
            if(!chkIvaVta.isSelected()){
                if (mensaje("Esta seguro que el Item No marca Iva en ventas.. \n ¿Desea continuar?")==JOptionPane.YES_OPTION){
             
                 if (!actualizarReg())
                      return false;
                      blnCmb=false;
               }else return false;
            }else{
                 if (!actualizarReg())
                      return false;
                      blnCmb=false;
            }
            
          return true;
        }
          
        
        
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (blnCmb)
                {
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                    {
                        if (!isRegPro())
                            return false;
                    }
                }
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    rstCab=null;
                    stmCab=null;
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            txtCln();
            limpiarDatInv();
            blnCmb=false;
            return blnRes;
        }
                   
      public boolean anularReg() {
        try{
            java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            try{
                if (con!=null){
                    con.setAutoCommit(false);
//                     if(!CreaItem_His(con, objZafParSis.getCodigoEmpresa(), Integer.parseInt(txtCodItm.getText()))){
//                            con.rollback();
//                            con.close();
//                            con=null;
//                            return false;
//                        }
                    if(cambiarEstadoItemTodasLasEmpresas(con, objZafParSis.getCodigoEmpresa(), Integer.parseInt(txtCodItm.getText()), "I")){
                        con.commit();
                        con.close();
                    }
                    else{
                        con.rollback();
                        con.close();
                    }
//                    strSQL = " Update tbm_inv set st_reg= 'I' where co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_itm = " +txtCodItm.getText();                             
//                    pstReg = con.prepareStatement(strSQL);
//                    pstReg.executeUpdate();
                    
                }
            }catch(SQLException e){
                con.rollback();
                con.close();
                objUti.mostrarMsgErr_F1(this, e);   
                return false;
            }
        }catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            return false;
        }
        return true;            
      }        
          
       
         
        public void clickAceptar() {
        }
        
        public void clickAnular() {
        }
        
       
        public void clickConsultar() {
            txtCodItm.setEditable(true);
            txtCodItm.setEnabled(true);
            habilitarDatInv();
            txtCln();
            limpiarDatInv();
        }
        
        public void clickEliminar() {
            deshabilitarDatInv();
        }
       
        public void clickImprimir() {
        }
        public void clickModificar() {
            habilitarDatInv();
            txtCodItm.setEnabled(false);
            txtCodAlt.requestFocus();
            chkIvaCom.setSelected(true);
            chkIvaVta.setSelected(true);
            
            chkEsItmSer.setEnabled(false);
            chkEsItmTra.setEnabled(false);
            chkEsItmOtr.setEnabled(false);
        }
        
        public void clickVisPreliminar() {
        }
        
        public boolean consultarReg() {
           return consultarDatInv(filtarSql());
        }

        
public boolean insertarReg() {
 boolean blnRes=false;
txtCln();
if (blnCln) {

    if(validadCampos()){
      if(insertarDatReg()){   
          blnRes=true;
      }
    }
}
 else {
    strMsg="No se ha realizado ningún cambio que se pueda guardar.";
    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
 }
  return blnRes;
}




private boolean validadCampos(){
  boolean blnRes=true;
  try{

     if(txtNomItm.getText().equals("")) {
        strMsg = "El campo <<Nombre del Item>> es obligatorio.\nEscriba el Nombre del Item y vuelva a intentarlo.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        tabGenInv.setSelectedIndex(0);
        txtNomItm.requestFocus();
        blnRes=false;
     }else if(!chkEsItmSer.isSelected()){
          if(!chkIvaCom.isSelected()&&!chkIvaVta.isSelected()){
                strMsg = "El campo  <<IVA>> es obligatorio.\nSeleccione una clase de IVA y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                tabGenInv.setSelectedIndex(0);
                chkIvaCom.requestFocus();
                 blnRes=false;
          }
      }else if (txtItmUni.getText().length()>6){
            valTxt(txtItmUni, txtItmUni, "Item(s) por Unidad", "", 2, "el", 0);
             blnRes=false;
      }else if(!txtStkMin.getText().equals("") && !txtStkMin.getText().equals("")){
         if (Integer.parseInt(txtStkMin.getText())>=Integer.parseInt(txtStkMax.getText())) {
            valTxt(txtStkMin, txtStkMax, "Stock Mínimo / Stock Máximo", "<<Stock Mínimo>> es mayor o igual al <<Stock Máximo>>", 3, "el", 1);
            blnRes=false;
         }
      }

  }catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}





        public boolean actualizarReg() {
//            if (blnCmb){
                return modificarDatInv();     
//            }
//            else {
//                strMsg="No se ha realizado ningÃºn cambio que se pueda guardar.";
//                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
//                 
//                limpiarDatInv();
//                return false;  
//        }
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
             consultarReg();
            return true;
        }
        
        public boolean afterModificar() {
            return true;
        }
        
        public boolean afterVistaPreliminar() {
            return true;
        }
        
        public boolean imprimir() {
            return true;
        }
        
        public boolean vistaPreliminar() {
            return true;
        }
        
    }
    
    
    private int mensaje(){
        strMsg = "¿Desea guardar los cambios efectuados a este registro?\n";
        strMsg+= "Si no guarda los cambios perderá toda la información que no haya guardado.";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);                
    }
    private int mensaje(String strMsg){
        return oppMsg.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);                
    }
    
    public boolean txtCln(){
        if ((txtCodAlt.getText().equals("")) &&  (txtCodAlt2.getText().equals("")) && (txtNomItm.getText().equals("")) && (txtCodLin.getText().equals("")) && (txtCodMar.getText().equals("")) && (txtCodGrp.getText().equals("")) && (txtCodUniMed.getText().equals("")) && (txtItmUni.getText().equals("")) && (txtCosUni1.getText().equals("")) && (txtCosUni2.getText().equals("")) && (txtCosUni3.getText().equals("")) && (txtStkMin.getText().equals("")) && (txtStkMax.getText().equals("")) && (txaObs1.getText().equals("")) && (txaObs2.getText().equals(""))){    
            blnCln = false;
        }
        else {
            blnCln = true;
        }
        return blnCln;
    }
        
    public void asignarDat(){
        try{ 
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            java.sql.Statement stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
            java.sql.ResultSet rstAux;
            int intCodInv = 0;
                if(rstCab != null){

                    }
            blnCmb = false;
                }
        catch(SQLException Evt)
        {
          objUti.mostrarMsgErr_F1(this, Evt);
        }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       

        catch(Exception Evt)
        {
          objUti.mostrarMsgErr_F1(this, Evt);
        }                       
    }         
    private boolean Reactivar()
    {
   try{
            java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            try{
                if (con!=null){
                    if(cambiarEstadoItemTodasLasEmpresas(con, objZafParSis.getCodigoEmpresa(), Integer.parseInt(txtCodItm.getText()), "A")){
                        con.commit();
                        con.close();
                    }else{
                        con.rollback();
                        con.close();
                    }
//                    java.sql.PreparedStatement pstReg;
//                    String strSQL="";
//                    strSQL = " Update tbm_inv set st_reg= 'A' where co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_itm = " + txtCodItm.getText();
////                    System.out.println(strSQL);
//                    pstReg = con.prepareStatement(strSQL);
//                    pstReg.executeUpdate();
//                    con.commit();
//                    con.close();
                    objTooBar.setEstadoRegistro("Activo");
                    cboEst.setSelectedIndex(0);
                }
            }catch(SQLException e){
                con.rollback();
                con.close();
                objUti.mostrarMsgErr_F1(this, e);   
                return false;
            }
        }catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            return false;
        }
        return true;        
    }




private boolean insertarDatReg(){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 int intCodEqu=0;
 boolean blnCodAlt = false;
 try{

 if(abrirConRem()){

 if(conRemGlo!=null){
    stmLoc=conRemGlo.createStatement();

    intCodEqu=getCodItemMaestro(conRemGlo);
 
    if(validadCodAlt(conRemGlo, txtCodAlt.getText() ,"tx_codAlt" )) {
        String strTerItm = txtCodAlt.getText().trim().toUpperCase().substring(txtCodAlt.getText().trim().length()-1, txtCodAlt.getText().trim().length());
        if (strTerItm.equals("I") || strTerItm.equals("S")) {
            if(!(chkEsItmSer.isSelected() || chkEsItmTra.isSelected() || chkEsItmOtr.isSelected())){
                while (!blnCodAlt) {
                    txtCodAlt2.setText(randomString("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 3));
                    blnCodAlt = validadCodAlt(conRemGlo, txtCodAlt2.getText(), "tx_codAlt2" );                
                }                
            } else {
                blnCodAlt = validadCodAlt(conRemGlo, txtCodAlt2.getText() ,"tx_codAlt2" );
            }
        } else {
            blnCodAlt = validadCodAlt(conRemGlo, txtCodAlt2.getText() ,"tx_codAlt2" );
        }

     if(blnCodAlt) {
//     if(validadCodAlt(conRemGlo, txtCodAlt2.getText() ,"tx_codAlt2" )) {
      if(intCodEqu!=0){
       if(_CrearItemEmpresas(conRemGlo, intCodEqu )){
        if(_creaInvMovEmpGrp(conRemGlo, intCodEqu )){

            cboEst.setSelectedIndex(0);
            txtStkAct.setText("0");
            txtCodItm.setEditable(false);
            butCod.setEnabled(false);
            chkIvaCom.setSelected(true);
            chkIvaVta.setSelected(true);
            txtCodAlt.requestFocus();
            conRemGlo.commit();
            blnRes=true;
            
        }else conRemGlo.rollback();
       }else conRemGlo.rollback();
      }else{ MensajeInf("Problemas al crear codigo de item consolidado");  conRemGlo.rollback(); }
     }else{  valTxt(txtCodAlt, txtCodAlt, "Codigo alterno 2", "<<Codigo alterno 2>> ya existe", 3, "el", 0);  conRemGlo.rollback(); }
    }else{ valTxt(txtCodAlt, txtCodAlt, "Codigo alterno", "<<Codigo alterno>> ya existe", 3, "el", 0);  conRemGlo.rollback(); }

    stmLoc.close();
    stmLoc=null;
    conRemGlo.close();
    conRemGlo=null;
  }
 }}catch(SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);   }
   catch(Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);   }
 return blnRes;
}

public boolean insertarPrvItm(java.sql.Connection conn,int intCodEmp,int intCodItm ){
   boolean blnRes=false;
   java.sql.Statement stmLoc;
   strSql="";
   String strEst="";
   try{
      stmLoc=conn.createStatement();

       for(int i=0; i<tblPrv.getRowCount(); i++){
        if(tblPrv.getValueAt(i, INT_TBL_PRVCODCLI) != null){
         if(!tblPrv.getValueAt(i, INT_TBL_PRVCODCLI).toString().equals("")){

               strEst="A";
                if(tblPrv.getValueAt( i, INT_TBL_PRVPRE)!=null){
                  if(tblPrv.getValueAt( i, INT_TBL_PRVPRE).toString().equalsIgnoreCase("true"))
                         strEst="P";
                }
                if(tblPrv.getValueAt( i, INT_TBL_PRVACT)!=null){
                   if(tblPrv.getValueAt( i, INT_TBL_PRVACT).toString().equalsIgnoreCase("true"))
                        strEst="A";
                }
                if(tblPrv.getValueAt( i, INT_TBL_PRVINA)!=null){
                  if(tblPrv.getValueAt( i, INT_TBL_PRVINA).toString().equalsIgnoreCase("true"))
                         strEst="I";
                }

               strSql+=" INSERT INTO tbr_prvinv(co_emp, co_itm, co_prv ,st_reg, st_regrep, fe_ing ) VALUES" +
               " ("+objZafParSis.getCodigoEmpresa()+","+ intCodItm +", "+tblPrv.getValueAt(i, INT_TBL_PRVCODCLI).toString()+" "+
               " ,'"+strEst+"', 'I', "+objZafParSis.getFuncionFechaHoraBaseDatos()+" ) ; ";

       }}}

      if(!strSql.equals(""))
          stmLoc.executeUpdate(strSql);

      stmLoc.close();
      stmLoc=null;
      blnRes=true;

   }catch(java.sql.SQLException Evt){ blnRes=false;   MensajeInf("Error. Al Insertar Proveedores posible duplicidad de datos.\nVerifique los datos he intente nuevamente."); }
    catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    return blnRes;
}

private boolean _CrearItemEmpresas(java.sql.Connection conn,int intCodEqu){
 boolean blnRes=false;
 java.sql.Statement stmInv,stmEmp;
 java.sql.ResultSet rstLoc,rstEmp;
 int intCodItm=0;
 int intControl=0;
 String chrItmSer="N", chrIvaCom="", chrIvaVta="", chrEst="", strAuxCodAlt="", strCamPreVta="";
 String strFecUltMod;
 try{
    stmEmp = conn.createStatement();
    stmInv = conn.createStatement();
 
    if(chkEsItmSer.isSelected()) chrItmSer="S";
    if(chkEsItmTra.isSelected()) chrItmSer="T";
    if(chkEsItmOtr.isSelected()) chrItmSer="O";


    if(chkIvaCom.isSelected())  chrIvaCom="S";
    else  chrIvaCom="N";

    if(chkIvaVta.isSelected()) chrIvaVta="S";
    else  chrIvaVta="N";

    if(cboEst.getSelectedIndex()==0) chrEst="A";
    else  chrEst="I";

    if(chkCamPreVta.isSelected()) strCamPreVta="S";


    strSql="SELECT co_emp FROM tbm_emp ORDER BY co_emp";
    rstEmp=stmEmp.executeQuery(strSql);
    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
    if (datFecAux==null)
      return false;
    strFecUltMod=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
    while(rstEmp.next()){

       strSql="select case  when MAX(co_itm) is null then 1 else MAX(co_itm)+1 end  as co_itm  from tbm_inv where co_emp="+rstEmp.getString("co_emp");
       rstLoc=stmInv.executeQuery(strSql);
       if(rstLoc.next())
          intCodItm = rstLoc.getInt("co_itm");
       rstLoc.close();
       rstLoc=null;

        strAuxCodAlt=txtCodAlt.getText().replaceAll("'", "''");

        strSql="INSERT INTO tbm_inv ( co_emp, co_itm, tx_codAlt,tx_codAlt2, tx_nomItm, co_uni "+
        " ,nd_itmUni, st_ser, nd_cosUni,nd_cosult, nd_preVta1, nd_preVta2, nd_preVta3, nd_stkAct, nd_stkMin, nd_stkMax "+
        " ,st_ivaCom, st_ivaVen, tx_obs1, tx_obs2, st_reg, co_usrIng,co_usrmod, fe_ing, fe_ultmod, nd_pesitmkgr, st_permodnomitmven, " +
        " st_permodnomitmcom, st_blqPreVta,st_oblCamNomItm ) "+
        " VALUES("+rstEmp.getInt("co_emp")+", "+intCodItm+", "+((strAux=txtCodAlt.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux.toUpperCase()+"'")+", "+
        " "+((strAux=txtCodAlt2.getText().replaceAll("'", "''")).equals("")?"'"+strAuxCodAlt.toUpperCase()+"'":"'"+strAux.toUpperCase()+"'")+", "+
        "  "+((strAux=txtNomItm.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        "  "+((strAux=txtCodUniMed.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        "  "+((strAux=txtItmUni.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        " '"+chrItmSer+"', "+
        "  "+((strAux=txtCosUni.getText().replaceAll("'", "''")).equals("")?"0":strAux )+",0, "+
        "  "+((strAux=txtCosUni1.getText().replaceAll("'", "''")).equals("")?"0":strAux )+", "+
        "  "+((strAux=txtCosUni2.getText().replaceAll("'", "''")).equals("")?"0":strAux )+", "+
        "  "+((strAux=txtCosUni3.getText().replaceAll("'", "''")).equals("")?"0":strAux )+", "+
        "  "+((strAux=txtStkAct.getText().replaceAll("'", "''")).equals("")?"Null":strAux)+", "+
        "  "+((strAux=txtStkMin.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        "  "+((strAux=txtStkMax.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        " '"+chrIvaCom+"', '"+chrIvaVta+"', "+
        "  "+((strAux=txaObs1.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        "  "+((strAux=txaObs2.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        "  '"+chrEst+"', "+objZafParSis.getCodigoUsuario()+", "+objZafParSis.getCodigoUsuario()+", '"+strFecUltMod+"', '"+strFecUltMod+"',  "+
        "  "+spnPeso.getModel().getValue().toString()+", '"+(chkpermodnomven.isSelected()?"S":"N")+"', "+
        "  '"+(chkpermodnomcom.isSelected()?"S":"N")+"', "+
        "  "+(strCamPreVta.equals("")?null:"'"+strCamPreVta+"'")+" ,"+
        "  "+(chkOblCamNomItm.isSelected()?"'S'":"NULL")+" "+
        "  "+" ); ";
        stmInv.executeUpdate(strSql);
        
        
        
        strSql="INSERT INTO tbm_equinv (co_itmmae, co_emp, co_itm) values ("+intCodEqu +", "+rstEmp.getInt("co_emp") +","+intCodItm+")";
        stmInv.executeUpdate(strSql);

        strSql="SELECT co_bod FROM tbm_bod WHERE co_emp="+rstEmp.getInt("co_emp")+" ORDER BY co_bod ";
        rstLoc=stmInv.executeQuery(strSql);
        while(rstLoc.next()){
            
            if(!creaItemBodega(conn, rstEmp.getInt("co_emp"), intCodItm,  rstLoc.getInt("co_bod") )){
                intControl=1;
                break;
            }

        }
        rstLoc.close();
        rstLoc=null;

        if(rstEmp.getInt("co_emp")==objZafParSis.getCodigoEmpresa()){
         if(!insertarPrvItm(conn, rstEmp.getInt("co_emp"), intCodItm )){
            intControl=1;
            break;
         }
        }

        if(!insertarClaItm(conn, rstEmp.getInt("co_emp"), intCodItm )){
            intControl=1;
            break;

         }

    }
    //tony implementacion historico de inventario
    objHisTblBasDat.insertarHistoricoMasivo(conn, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objZafParSis.getCodigoUsuario());
    rstEmp.close();
    rstEmp=null;

    stmInv.close();
    stmInv=null;
    stmEmp.close();
    stmEmp=null;
    if(intControl==0) blnRes=true;
   

  }catch(SQLException evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, evt); }
   catch(Exception evt){  blnRes=false;  objUti.mostrarMsgErr_F1(this, evt); }
  return blnRes;
}

public boolean creaItemBodega(java.sql.Connection conn,int intCodEmp,int intCodItm, int intCodBod){
   boolean blnRes=false;
   java.sql.Statement stmLoc;
   String strSql="";
   try{
      stmLoc=conn.createStatement();
      strSql="INSERT INTO tbm_invbod(co_emp, co_bod, co_itm, nd_stkact, nd_stkmin, nd_stkmax ) " +
      " VALUES("+intCodEmp+", "+intCodBod+", "+intCodItm+ ", 0, 0, 0)";
      stmLoc.executeUpdate(strSql);
      stmLoc.close();
      stmLoc=null;
      blnRes=true;
      
   }catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    return blnRes;
}

public boolean _creaInvMovEmpGrp(java.sql.Connection conn, int intCodEqu ){
 boolean blnRes=false;
 java.sql.Statement stmLoc,stmLoc01, stmLoc02;
 java.sql.ResultSet rstLoc,rstLoc01;
 String strSql="";
 try{
   stmLoc=conn.createStatement();
   stmLoc01=conn.createStatement();
   stmLoc02=conn.createStatement();

   strSql="SELECT co_emp, co_itm FROM tbm_equinv WHERE co_emp NOT IN (0,3) AND co_itmmae="+intCodEqu+" ";
   rstLoc=stmLoc.executeQuery(strSql);
   while(rstLoc.next()){

      strSql="SELECT co_emp, co_itm FROM tbm_equinv WHERE co_emp NOT IN (0,3) AND co_itmmae="+intCodEqu+" " +
      " AND co_emp NOT IN ("+rstLoc.getString("co_emp")+") AND co_itm NOT IN ( "+rstLoc.getString("co_itm")+" ) ";
      rstLoc01 = stmLoc01.executeQuery(strSql);
      while(rstLoc01.next()){

        strSql="INSERT INTO tbm_invmovempgrp(co_emp, co_itm, co_emprel, co_itmrel, nd_salven, nd_salcom, st_regrep) " +
        "  VALUES("+rstLoc.getString("co_emp")+", "+rstLoc.getString("co_itm")+", "+rstLoc01.getString("co_emp")+", "+rstLoc01.getString("co_itm")+", 0.00, 0.00, 'I' ) ";
        stmLoc02.executeUpdate(strSql);

      }
      rstLoc01.close();
      rstLoc01=null;
   }
   rstLoc.close();
   rstLoc=null;

  stmLoc.close();
  stmLoc=null;
  stmLoc01.close();
  stmLoc01=null;
  stmLoc02.close();
  stmLoc02=null;
  blnRes=true;

  }catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
   catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
  return blnRes;
}

    private boolean validadCodAlt(java.sql.Connection conn, String strCodAlt, String strCampCodAlt){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        try{
            if(conn!=null){
                stmLoc=conn.createStatement();
                strSql=" SELECT tx_codAlt FROM tbm_inv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND UPPER("+strCampCodAlt+")='"+strCodAlt.trim().replaceAll("'", "''").toUpperCase()+"'";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    blnRes=false;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }  
        } 
        catch(SQLException e){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, e);   
        }
        catch(Exception e){ 
                blnRes=false; 
                objUti.mostrarMsgErr_F1(this, e);   
        }
        return blnRes;
    }

public String randomString(String chars, int len) {
    Random rnd = new Random();

    StringBuilder sb = new StringBuilder( len );
    for( int i = 0; i < len; i++ ) {
       sb.append( chars.charAt( rnd.nextInt(chars.length()) ) );            
    }
    return sb.toString();
 }
    
public boolean insertarClaItm(java.sql.Connection conn,int intCodEmp,int intCodItm ){
   boolean blnRes=true;
   java.sql.Statement stmLoc;
   strSql="";
   String strEst="";
   try{
      stmLoc=conn.createStatement();

       for(int i=0; i<tblCla.getRowCount(); i++){
        if(tblCla.getValueAt(i, INT_TBL_CLACOD) != null){
         if(!tblCla.getValueAt(i, INT_TBL_CLACOD).toString().equals("")){

               strEst="A";
                if(tblCla.getValueAt( i, INT_TBL_CLAACT)!=null){
                   if(tblCla.getValueAt( i, INT_TBL_CLAACT).toString().equalsIgnoreCase("true"))
                        strEst="A";
                }
                if(tblCla.getValueAt( i, INT_TBL_CLAINA)!=null){
                  if(tblCla.getValueAt( i, INT_TBL_CLAINA).toString().equalsIgnoreCase("true"))
                         strEst="I";
                }

               strSql+=" INSERT INTO tbr_invcla(co_emp, co_itm, co_cla ,st_reg, st_regrep, fe_ing, co_grp ) VALUES" +
               " ("+intCodEmp+","+ intCodItm +", "+tblCla.getValueAt(i, INT_TBL_CLACOD).toString()+" "+
               " ,'"+strEst+"', 'I' ,"+objZafParSis.getFuncionFechaHoraBaseDatos()+" , "+tblCla.getValueAt( i, INT_TBL_CODGRP)+" ) ; ";
       }}}

     
      if(!strSql.equals(""))
          stmLoc.executeUpdate(strSql);

      stmLoc.close();
      stmLoc=null;
   
   }catch(java.sql.SQLException Evt){ blnRes=false;   MensajeInf("Error. Al Insertar Clasificaciones posible duplicidad de datos.\nVerifique los datos he intente nuevamente."); }
    catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    return blnRes;
}





private boolean consultarDatInv(String strFlt){
 boolean blnRes=false;
 strFil = strFlt;
 try{
  if(abrirConRem()){
   if(conRemGlo!=null){

    stmCab = conRemGlo.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );

    strSql="SELECT * FROM tbm_inv WHERE st_reg not in ('T','E','U') and co_emp="+objZafParSis.getCodigoEmpresa()+" "+
    " "+strFil+" ORDER BY co_itm ";
    rstCab = stmCab.executeQuery(strSql);
    conRemGlo.close();
    conRemGlo=null;
    if(rstCab.next()){
        rstCab.last();
        objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros.");
        cargarReg();
        blnRes=true;
    }
    else{
        objTooBar.setMenSis("Se encontraron 0 registros...");
        strMsg="No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        rstCab = null;
        stmCab.close();
        conCab.close();
        stmCab=null;
        conCab=null;
        limpiarDatInv();
        blnRes=false;
    }
   blnCmb = false;
   
  }}}catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
     catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}







        /**FunciÃ³n que me permÃ­te guardar los datos del Articulo*/
    public boolean guardarDatInv() 
    {
        String strFecUltMod;
         datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
        /**Validar que el CÃ³digo alterno no exista duplicado.*/
        strSql = "";
        strSql = strSql + " SELECT tx_codAlt";
        strSql = strSql + " FROM tbm_inv";
        strSql = strSql + " WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "";
        strSql = strSql + " and UPPER(tx_codAlt) = '" + txtCodAlt.getText().trim().replaceAll("'", "''").toUpperCase() + "'";
        if (!objUti.isCodigoUnico(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSql))
        { 
            valTxt(txtCodAlt, txtCodAlt, "Codigo alterno", "<<Codigo alterno>> ya existe", 3, "el", 0);
            return false;
        }      
        
        
        strSql = "";
        strSql = strSql + " SELECT tx_codAlt2";
        strSql = strSql + " FROM tbm_inv";
        strSql = strSql + " WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "";
        strSql = strSql + " and UPPER(tx_codAlt2) = '" + txtCodAlt2.getText().trim().replaceAll("'", "''").toUpperCase() + "'";
        if (!objUti.isCodigoUnico(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSql))
        { 
            valTxt(txtCodAlt, txtCodAlt, "Codigo alterno 2", "<<Codigo alterno 2>> ya existe", 3, "el", 0);
            return false;
        }      
        
        
        
        
       if (txtNomItm.getText().equals("")) {
            strMsg = "El campo <<Nombre del Item>> es obligatorio.\nEscriba el Nombre del Item y vuelva a intentarlo.";
            oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
            tabGenInv.setSelectedIndex(0);
            txtNomItm.requestFocus();
            return false;}
        else if (!this.chkEsItmSer.isSelected()){
            if (!this.chkIvaCom.isSelected()&&!this.chkIvaVta.isSelected()){
                strMsg = "El campo  <<IVA>> es obligatorio.\nSeleccione una clase de IVA y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                tabGenInv.setSelectedIndex(0);
                chkIvaCom.requestFocus(); 
                return false;}   
        }
        else if (txtItmUni.getText().length()>6){
            valTxt(txtItmUni, txtItmUni, "Item(s) por Unidad", "", 2, "el", 0);
            return false;}
        if (!txtStkMin.getText().equals("") && !txtStkMin.getText().equals("")){
            if (Integer.parseInt(txtStkMin.getText())>=Integer.parseInt(txtStkMax.getText())) {
                valTxt(txtStkMin, txtStkMax, "Stock MÃ­nimo / Stock MÃ¡ximo", "<<Stock MÃ­nimo>> es mayor o igual al <<Stock MÃ¡ximo>>", 3, "el", 1);
                return false;
            }
        }

        /**Almacena el estado que haya seleccionado el usuario en una variable*/
        char chrEst,chrItmSer,chrIvaCom,chrIvaVta;

        /**Asigno a la variable el valor segun lo que haya seleccionado el usuario en el combo*/
        if (cboEst.getSelectedIndex()==0)
            chrEst= 'A';
        else
            chrEst= 'I';

            try
            {
                java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                con.setAutoCommit(false);
                java.sql.PreparedStatement pstInv;  
              try{
                if (con!=null)
                {
                    java.sql.Statement stm = con.createStatement();
                    strSql = "";
                    strSql = strSql + "SELECT case  when MAX(co_itm) is null then 1 else MAX(co_itm)+1 end  as co_itm  from tbm_inv where co_emp = " + objZafParSis.getCodigoEmpresa();
                    java.sql.ResultSet rst = stm.executeQuery(strSql);

                    /**Bloque que almacena el numero de registro que toca guardar, caso contrario
                     *que no haya registros en la tabla automaticamente guarda en esta !*/
                    int intNumReg=0;
                    if (rst.next())
                        intNumReg=rst.getInt("co_itm");
                   


                    /**Bloque que almacena en una variable de tipo char si es item de servicio
                     *el articulo segun si han marcado la casilla o no*/

                    chrItmSer = 'N';
                      
                    if (chkEsItmSer.isSelected())
                        chrItmSer = 'S';
                   
                    if (chkEsItmTra.isSelected())
                        chrItmSer = 'T';

                     if (chkEsItmOtr.isSelected())
                        chrItmSer = 'O';
                   
                    
                    
                    /**Bloque que almacena en una variable de tipo char si el articulo
                     *tiene Iva en Compras o no*/             
                    if (chkIvaCom.isSelected())
                        chrIvaCom = 'S';
                    else 
                        chrIvaCom = 'N';

                    /**Bloque que almacena en una variable de tipo char si el articulo
                     *tiene Iva en Ventas o no*/             
                    if (chkIvaVta.isSelected())
                        chrIvaVta = 'S';
                    else 
                        chrIvaVta = 'N';    
                    
                    strAux = txtCodAlt.getText().replaceAll("'", "''");
                    if(strAux.equals(""))
                      strAux="Null";
                    String strAux2="Null"; 
                    
                      strAux2 = txtCodAlt2.getText().replaceAll("'", "''");
                         if(strAux2.equals(""))
                           strAux2="Null";
                    
                    if(txtCodAlt2.getText().trim().equals(""))
                       strAux2=strAux; 
                   
                    
                    /**Haciendo el Insert en la tabla*/
                    strSql="";
                    strSql+="INSERT INTO tbm_inv (co_emp, co_itm, tx_codAlt,tx_codAlt2, tx_nomItm, co_cla1, co_cla2, co_cla3, co_uni";
                    strSql+=", nd_itmUni, st_ser, nd_cosUni,nd_cosult, nd_preVta1, nd_preVta2, nd_preVta3, nd_stkAct, nd_stkMin, nd_stkMax";
                    strSql+=", st_ivaCom, st_ivaVen, tx_obs1, tx_obs2, st_reg, co_usrIng,co_usrmod, fe_ing, fe_ultmod, ";
                    strSql+="  nd_pesitmkgr, st_permodnomitmven, st_permodnomitmcom, st_oblCamNomItm) ";
                    strSql+=" VALUES (" + objZafParSis.getCodigoEmpresa() + ", " +  intNumReg  + "";
                    strSql+=", " + ((strAux = txtCodAlt.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux.toUpperCase() + "'");
                    strSql+=", '" + strAux2.toUpperCase() + "'";
                
                    strSql+=", " + ((strAux = txtNomItm.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");

                    if (txtCodLin.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtCodLin.getText().replaceAll("'", "''") + "";}

                    if (txtCodGrp.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtCodGrp.getText().replaceAll("'", "''") + "";}

                    if (txtCodMar.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtCodMar.getText().replaceAll("'", "''") + "";}

                    if (txtCodUniMed.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtCodUniMed.getText().replaceAll("'", "''") + "";}

                    if (txtItmUni.getText().equals(""))
                        strSql+=", 0";
                    else{
                        strSql+=", " + txtItmUni.getText().replaceAll("'", "''") + "";}

                    strSql+=", '" + chrItmSer + "'";

                    if (txtCosUni.getText().equals(""))
                        strSql+=", 0,0";
                    else{
                        strSql+=", " + txtCosUni.getText().replaceAll("'", "''") + ",0";}

                    if (txtCosUni1.getText().equals(""))
                        strSql+=", 0";
                    else{
                        strSql+=", " + txtCosUni1.getText().replaceAll("'", "''") + "";}

                    if (txtCosUni2.getText().equals(""))
                        strSql+=", 0";
                    else{
                        strSql+=", " + txtCosUni2.getText().replaceAll("'", "''") + "";}

                    if (txtCosUni3.getText().equals(""))
                        strSql+=", 0";
                    else{
                        strSql+=", " + txtCosUni3.getText().replaceAll("'", "''") + "";}

                    if (txtStkAct.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtStkAct.getText().replaceAll("'", "''") + "";}

                    if (txtStkMin.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtStkMin.getText().replaceAll("'", "''") + "";}

                    if (txtStkMax.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtStkMax.getText().replaceAll("'", "''") + "";}

                    strSql+=", '" + chrIvaCom + "', '" + chrIvaVta + "'";
                    strSql+=", " + ((strAux = txaObs1.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql+=", " + ((strAux = txaObs2.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql+=", '" + chrEst + "', " + objZafParSis.getCodigoUsuario() + ", " + objZafParSis.getCodigoUsuario() + ", '" + strFecUltMod + "',  '" + strFecUltMod + "' " +
                    " , "+spnPeso.getModel().getValue().toString()+", '"+(chkpermodnomven.isSelected()?"S":"N")+"', '"+(chkpermodnomcom.isSelected()?"S":"N")+"', "; 
                    strSql+=" "+(chkOblCamNomItm.isSelected()?"'S'":"NULL")+"";
                    strSql+=" )";
                    //strSql = strSql + ", '" + objUti.getFecha() + "')";   
                    //System.out.println(strSql);   
                    //stm.executeUpdate(strSql);
                    pstInv = con.prepareStatement(strSql);
                    pstInv.executeUpdate();
                    int intCodEqu  = getCodItemMaestro(con);
                    if (intCodEqu == 0){
                        MensajeInf("Problemas al crear codigo de item consolidado");
                        return false;
                    }
                    objHisTblBasDat.insertarHistoricoIndividual(con, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objZafParSis.getCodigoUsuario());

                    strSql = "";
                    strSql = " Insert into tbm_equinv (co_itmmae, co_emp, co_itm)values ("+intCodEqu +", " + objZafParSis.getCodigoEmpresa() +","+ intNumReg + ")";
                    pstInv = con.prepareStatement(strSql);
                    pstInv.executeUpdate();
                    CrearItemEmpresas(con,intCodEqu);
                    //Creacion de Inventario Bodega
                    
                    strSql = " Select co_bod from tbm_bod where co_emp ="+objZafParSis.getCodigoEmpresa()+"  and co_bod not in " +
                             " (select co_bod from tbm_invbod where co_emp ="+objZafParSis.getCodigoEmpresa()+" and co_itm ="+ intNumReg+") " +
                             " order by co_bod ";
                    rst = stm.executeQuery(strSql);                    
                    while(rst.next()){
                        if (!objInventario.creaItemBodega(con,objZafParSis.getCodigoEmpresa(),intNumReg,rst.getInt("co_bod"))){
                            con.rollback();
                            con.close();
                            return false;
                        }
                    }   
                    rst.close();
                    rst=null;
                      
                    java.sql.ResultSet rstLoc;
                    java.sql.Statement stmLoc=con.createStatement();
                    java.sql.Statement stmLoc01=con.createStatement();
                    strSql="SELECT co_emp, co_itm FROM tbm_equinv WHERE co_emp NOT IN (0,3) AND co_itmmae IN( SELECT co_itmmae FROM tbm_equinv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+intNumReg+" ) ";
                    rst = stm.executeQuery(strSql);                    
                    while(rst.next()){

                     strSql="SELECT co_emp, co_itm FROM tbm_equinv WHERE co_emp NOT IN (0,3) AND co_itmmae " +
                     " IN( SELECT co_itmmae FROM tbm_equinv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+intNumReg+" ) " +
                     " AND co_emp NOT IN ("+rst.getString("co_emp")+") AND co_itm NOT IN ( "+rst.getString("co_itm")+" ) ";
                     rstLoc = stmLoc.executeQuery(strSql);                    
                     while(rstLoc.next()){

                        strSql="INSERT INTO tbm_invmovempgrp(co_emp, co_itm, co_emprel, co_itmrel, nd_sal, st_regrep) " +
                        "  VALUES("+rst.getString("co_emp")+", "+rst.getString("co_itm")+", "+rstLoc.getString("co_emp")+", "+rstLoc.getString("co_itm")+", 0.00, 'I' ) ";
                        stmLoc01.executeUpdate(strSql);
                        
                     }   
                     rstLoc.close();
                     rstLoc=null;
                   }
                    
                   stmLoc.close();
                   stmLoc=null;
                   stmLoc01.close();
                   stmLoc01=null;
                    
                    
                    con.commit();
                    con.setAutoCommit(true);
                    stm.close();
                    rst.close();
                    con.close();
                   cboEst.setSelectedIndex(0);
                    txtStkAct.setText("0");
                    txtCodItm.setEditable(false);
                    butCod.setEnabled(false);
                    chkIvaCom.setSelected(true);
                    chkIvaVta.setSelected(true);
                    txtCodAlt.requestFocus();
                   
                    
                    
                    
                   


                    
                    
                    
                }  
            }catch(SQLException evt){
                con.rollback();
                con.close();
                objUti.mostrarMsgErr_F1(this, evt);
                return false;
            }
        }catch(Exception evt){
            objUti.mostrarMsgErr_F1(this, evt);
            return false;
        }
        blnCmb = false;
        return true;
    }
    
    
    
      
    
    
      
    
    

private int getCodItemMaestro(java.sql.Connection conn){
 int intRes = 0;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSQL="";
 try{
  if(conn!=null){
    stmLoc = conn.createStatement();
    strSQL="SELECT  CASE WHEN MAX(co_itmmae) is null THEN 1 else MAX(co_itmmae)+1 END AS co_itmmae FROM tbm_equinv";
    rstLoc=stmLoc.executeQuery(strSQL);
    if(rstLoc.next()){
      intRes = rstLoc.getInt("co_itmmae");
    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;

 }}catch (java.sql.SQLException e){  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
   catch(Exception e){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
 return intRes;
}



    
    

private boolean CrearItemEmpresas(java.sql.Connection conInv,int intCodEqu){
 boolean blnRes=false;
 java.sql.PreparedStatement pstInv;
 java.sql.Statement stmInv,stmEmp;
 java.sql.ResultSet rstInv,rstEmp;
 String strFecUltMod;
 int intCodItm=0;
 char chrEst,chrItmSer= 'N',chrIvaCom,chrIvaVta;
 try{
    stmEmp = conInv.createStatement();
    stmInv = conInv.createStatement();

    strSql="Select co_emp from tbm_emp where co_emp <> "+objZafParSis.getCodigoEmpresa()+" and st_reg='A' order by co_emp";
    rstEmp=stmEmp.executeQuery(strSql);
    
    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
    while(rstEmp.next()){

       strSql="select case  when MAX(co_itm) is null then 1 else MAX(co_itm)+1 end  as co_itm  from tbm_inv where co_emp="+rstEmp.getString("co_emp");
       rstInv=stmInv.executeQuery(strSql);
       if(rstInv.next())
          intCodItm = rstInv.getInt("co_itm");
       rstInv.close();
       rstInv=null;

       if(cboEst.getSelectedIndex()==0)
                        chrEst= 'A';
                    else
                        chrEst= 'I';
                
                    if (chkEsItmSer.isSelected())
                        chrItmSer = 'S';
                   if (chkEsItmTra.isSelected())
                                    chrItmSer = 'T';
                   if (chkEsItmOtr.isSelected())
                                    chrItmSer = 'O';



                    if (chkIvaCom.isSelected())
                        chrIvaCom = 'S';
                    else 
                        chrIvaCom = 'N';
                    if (chkIvaVta.isSelected())
                        chrIvaVta = 'S';
                    else 
                        chrIvaVta = 'N';                    
                    
                
                    strAux = txtCodAlt.getText().replaceAll("'", "''");
                    if(strAux.equals(""))
                      strAux="Null";
                    String strAux2="Null"; 
                    
                      strAux2 = txtCodAlt2.getText().replaceAll("'", "''");
                         if(strAux2.equals(""))
                           strAux2="Null";
                    
                    if(txtCodAlt2.getText().trim().equals(""))
                       strAux2=strAux; 
                    
                    strSql="";
                    strSql+="INSERT INTO tbm_inv (co_emp, co_itm, tx_codAlt,tx_codAlt2, tx_nomItm, co_cla1, co_cla2, co_cla3, co_uni";
                    strSql+=", nd_itmUni, st_ser, nd_cosUni,nd_cosult, nd_preVta1, nd_preVta2, nd_preVta3, nd_stkAct, nd_stkMin, nd_stkMax";
                    strSql+=", st_ivaCom, st_ivaVen, tx_obs1, tx_obs2, st_reg, co_usrIng, co_usrmod, fe_ing, fe_ultmod, nd_pesitmkgr, ";
                    strSql+="  st_permodnomitmven, st_permodnomitmcom,st_oblCamNomItm ) ";
                    strSql+=" VALUES (" + rstEmp.getInt("co_emp") + ", " +  intCodItm  + "";
                    strSql+=", " + ((strAux = txtCodAlt.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux.toUpperCase() + "'");
                    strSql+=", '" +  strAux2 + "'";
                    strSql+=", " + ((strAux = txtNomItm.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");

                    if (txtCodLin.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtCodLin.getText().replaceAll("'", "''") + "";}

                    if (txtCodGrp.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtCodGrp.getText().replaceAll("'", "''") + "";}

                    if (txtCodMar.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtCodMar.getText().replaceAll("'", "''") + "";}

                    if (txtCodUniMed.getText().equals(""))
                        strSql+= ", " + null + "";
                    else{
                        strSql+=", " + txtCodUniMed.getText().replaceAll("'", "''") + "";}

                    if (txtItmUni.getText().equals(""))
                        strSql+=", 0";
                    else{
                        strSql+=", " + txtItmUni.getText().replaceAll("'", "''") + "";}
     

                    strSql+=", '" + chrItmSer + "'";

                    if (txtCosUni.getText().equals(""))
                        strSql+=", 0,0";
                    else{
                        strSql+=", " + txtCosUni.getText().replaceAll("'", "''") + ",0";}

                    if (txtCosUni1.getText().equals(""))
                        strSql+=", 0";
                    else{
                        strSql+=", " + txtCosUni1.getText().replaceAll("'", "''") + "";}

                    if (txtCosUni2.getText().equals(""))
                        strSql+=", 0";
                    else{
                        strSql+=", " + txtCosUni2.getText().replaceAll("'", "''") + "";}

                    if (txtCosUni3.getText().equals(""))
                        strSql+=", 0";
                    else{
                        strSql+=", " + txtCosUni3.getText().replaceAll("'", "''") + "";}

                    if (txtStkAct.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtStkAct.getText().replaceAll("'", "''") + "";}

                    if (txtStkMin.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtStkMin.getText().replaceAll("'", "''") + "";}

                    if (txtStkMax.getText().equals(""))
                        strSql+=", " + null + "";
                    else{
                        strSql+=", " + txtStkMax.getText().replaceAll("'", "''") + "";}

                    strSql+=", '" + chrIvaCom + "', '" + chrIvaVta + "'";
                    strSql+=", " + ((strAux = txaObs1.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql+=", " + ((strAux = txaObs2.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql+=", '" + chrEst + "', " + objZafParSis.getCodigoUsuario() + ",  " + objZafParSis.getCodigoUsuario() + ", '" + strFecUltMod + "' , '" + strFecUltMod + "' " +
                    " , "+spnPeso.getModel().getValue().toString()+", '"+(chkpermodnomven.isSelected()?"S":"N")+"', '"+(chkpermodnomcom.isSelected()?"S":"N")+"', ";   
                    strSql+=" "+(chkOblCamNomItm.isSelected()?"'S'":"NULL")+"";
                    strSql+=" )";
                    pstInv = conInv.prepareStatement(strSql);
                    pstInv.executeUpdate();
                    strSql = "";
                    strSql = " Insert into tbm_equinv (co_itmmae, co_emp, co_itm)values ("+intCodEqu +", " + rstEmp.getInt("co_emp") +","+ intCodItm + ")";
                    pstInv = conInv.prepareStatement(strSql);
                    pstInv.executeUpdate();
                    //Creacion de Inventario Bodega                    
                    strSql = " Select co_bod from tbm_bod where co_emp ="+ rstEmp.getInt("co_emp") +"  and co_bod not in " +
                             " (select co_bod from tbm_invbod where co_emp ="+ rstEmp.getInt("co_emp") +" and co_itm ="+ intCodItm+") " +
                             " order by co_bod ";
                    java.sql.ResultSet rst = stmInv.executeQuery(strSql);                    
                    while(rst.next()){
                        if (!objInventario.creaItemBodega(conInv,rstEmp.getInt("co_emp"),intCodItm,rst.getInt("co_bod"))){
                            conInv.rollback();
                            conInv.close();
                            return false;
                        }
                    }  
                    rst.close();
                    rst=null;
                    

            }
           objHisTblBasDat.insertarHistoricoMasivo(conInv, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objZafParSis.getCodigoUsuario());
           stmInv.close();
           stmInv=null;
           stmEmp.close();
           stmEmp=null;
        }catch(SQLException evt)  {
            try{
                conInv.rollback();
                conInv.close();
            }catch(SQLException Evts){
                objUti.mostrarMsgErr_F1(this, evt);
                return false;
            }                        
            objUti.mostrarMsgErr_F1(this, evt);
            return false;
        } catch(Exception evt){
            objUti.mostrarMsgErr_F1(this, evt);
            return false;
        }
        return true;
    } 





//public boolean actualizarTabCla(java.sql.Connection conMod, String strCodItm ){
// boolean blnRes=true;
// Statement stm;
// String strEst="A";
// String strSqlCla="";
// try{
// if(conMod!=null){
// stm=conMod.createStatement();
//
// strSqlCla="DELETE FROM tbr_invcla  WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_itm="+strCodItm;
// stm.executeUpdate(strSqlCla);
// strSqlCla="";
//
// for(int x=0; x<tblCla.getRowCount(); x++){
//  if(tblCla.getValueAt(x, INT_TBL_CLACOD) != null){
//   if(!tblCla.getValueAt(x, INT_TBL_CLACOD).toString().equals("")){
//    strEst="A";
//    if(tblCla.getValueAt( x, INT_TBL_CLAACT)!=null){
//     if(tblCla.getValueAt( x, INT_TBL_CLAACT).toString().equalsIgnoreCase("true"))
//           strEst="A";
//    }
//    if(tblCla.getValueAt( x, INT_TBL_CLAINA)!=null){
//        if(tblCla.getValueAt( x, INT_TBL_CLAINA).toString().equalsIgnoreCase("true"))
//           strEst="I";
//    }
//
//   strSqlCla+=" INSERT INTO tbr_invcla(co_emp, co_itm, co_cla ,st_reg, st_regrep, fe_ing ) VALUES" +
//   " ("+objZafParSis.getCodigoEmpresa()+","+ strCodItm +", "+tblCla.getValueAt(x, INT_TBL_CLACOD).toString()+" "+
//   " ,'"+strEst+"', 'I' ,"+objZafParSis.getFuncionFechaHoraBaseDatos()+"  ) ; ";
//
// }}}
//
// if(!strSqlCla.equals(""))
//   stm.executeUpdate(strSqlCla);
//
//
// stm.close();
// stm=null;
//}}catch (SQLException evt){ blnRes=false;   MensajeInf("Error. Al Modificar Clasificaciónes posible duplicidad de datos.\nVerifique los datos he intente nuevamente."); }
//  catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt);  }
// return blnRes;
//}




public boolean actualizarTabCla(java.sql.Connection conMod, String strCodItm  ){
 boolean blnRes=true;
 java.sql.ResultSet rstLoc;
 Statement stmLoc;
 String strEst="A";
 String strSqlCla="";
 String strSqlInv="";
 String strCodCla="";
 try{
 if(conMod!=null){
  stmLoc=conMod.createStatement();

    strSqlInv="SELECT co_emp , co_itm FROM tbm_equInv  WHERE "+
    " co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" "+
    " AND co_itm="+strCodItm+")  ";
    rstLoc = stmLoc.executeQuery(strSqlInv);
    while(rstLoc.next()){

       strSqlCla+="DELETE FROM tbr_invcla  WHERE co_emp="+rstLoc.getInt("co_emp")+" and co_itm="+rstLoc.getInt("co_itm")+" ; ";

         for(int x=0; x<tblCla.getRowCount(); x++){
          if(tblCla.getValueAt(x, INT_TBL_CLACOD) != null){
           if(!tblCla.getValueAt(x, INT_TBL_CLACOD).toString().equals("")){
            strEst="A";
            if(tblCla.getValueAt( x, INT_TBL_CLAACT)!=null){
             if(tblCla.getValueAt( x, INT_TBL_CLAACT).toString().equalsIgnoreCase("true"))
                   strEst="A";
            }
            if(tblCla.getValueAt( x, INT_TBL_CLAINA)!=null){
                if(tblCla.getValueAt( x, INT_TBL_CLAINA).toString().equalsIgnoreCase("true"))
                   strEst="I";
            }
           strCodCla=tblCla.getValueAt(x, INT_TBL_CLACOD).toString();
           strSqlCla+=" INSERT INTO tbr_invcla(co_emp, co_itm, co_cla ,st_reg, st_regrep, fe_ing, co_grp ) VALUES" +
           " ("+rstLoc.getInt("co_emp")+","+ rstLoc.getInt("co_itm") +", "+strCodCla+" "+
           " ,'"+strEst+"', 'I' ,"+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+tblCla.getValueAt( x, INT_TBL_CODGRP)+"  ) ; ";

         }}}
    }
    rstLoc.close();
    rstLoc=null;

    System.out.println(" strSqlCla : "+strSqlCla );

 if(!strSqlCla.equals(""))
   stmLoc.executeUpdate(strSqlCla);


 stmLoc.close();
 stmLoc=null;
}}catch (SQLException evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, evt);  MensajeInf("Error. Al Modificar Clasificaciónes posible duplicidad de datos.\nVerifique los datos he intente nuevamente."); }
  catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt);  }
 return blnRes;
}

 





public boolean actualizarTabPrv(java.sql.Connection conMod, String strCodItm ){
 boolean blnRes=true;
 Statement stm;
 String strEst="A";
 String strSqlPrv="";
 try{
 if(conMod!=null){
 stm=conMod.createStatement();

  strSqlPrv="DELETE FROM tbr_prvinv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_itm="+strCodItm;
  stm.executeUpdate(strSqlPrv);
  strSqlPrv="";

 for(int x=0; x<tblPrv.getRowCount(); x++){
  if(tblPrv.getValueAt(x, INT_TBL_PRVCODCLI) != null){
   if(!tblPrv.getValueAt(x, INT_TBL_PRVCODCLI).toString().equals("")){
    strEst="A";
    if(tblPrv.getValueAt( x, INT_TBL_PRVPRE)!=null){
     if(tblPrv.getValueAt( x, INT_TBL_PRVPRE).toString().equalsIgnoreCase("true"))
        strEst="P";
    }
    if(tblPrv.getValueAt( x, INT_TBL_PRVACT)!=null){
     if(tblPrv.getValueAt( x, INT_TBL_PRVACT).toString().equalsIgnoreCase("true"))
           strEst="A";
    }
    if(tblPrv.getValueAt( x, INT_TBL_PRVINA)!=null){
        if(tblPrv.getValueAt( x, INT_TBL_PRVINA).toString().equalsIgnoreCase("true"))
           strEst="I";
    }
    
       strSqlPrv+=" INSERT INTO tbr_prvinv(co_emp, co_itm, co_prv ,st_reg, st_regrep, fe_ing ) VALUES" +
       " ("+objZafParSis.getCodigoEmpresa()+","+ strCodItm +", "+tblPrv.getValueAt(x, INT_TBL_PRVCODCLI).toString()+" "+
       " ,'"+strEst+"', 'I', "+objZafParSis.getFuncionFechaHoraBaseDatos()+" ) ; ";

 }}}

 if(!strSqlPrv.equals(""))
   stm.executeUpdate(strSqlPrv);
 

 stm.close();
 stm=null;
}}catch (SQLException evt){ blnRes=false;  MensajeInf("Error. Al Modificar Proveedores posible duplicidad de datos.\nVerifique los datos he intente nuevamente."); }
  catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt);  }
 return blnRes;
}

public boolean modificarDatInv(){
 boolean blnRes=false;
 java.sql.Connection conn;
 try{
   conn = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn != null){
      conn.setAutoCommit(false);
   
   if(validadCampos()){

    if(validadCodAltMod(conn, txtCodItm.getText(), txtCodAlt.getText() ,"tx_codAlt" )) {
     if(validadCodAltMod(conn, txtCodItm.getText(), txtCodAlt2.getText() ,"tx_codAlt2" )) {
    //  if(validadStkMaxMin(conn, txtCodItm.getText() )) {  SE QUITA POR SEGUNDA ORDEN 
       if(modificarInv(conn)){
        if(actualizarTabPrv(conn, txtCodItm.getText() )){
          if(actualizarTabCla(conn, txtCodItm.getText() )){
         
              conn.commit();
              blnRes=true;
          
          }else conn.rollback();
        }else conn.rollback();
       }else conn.rollback();
    //  }else{ valTxt(txtStkMin, txtStkMax, "Stock MÃ­nimo / Stock MÃ¡ximo", "<<Stock MÃ­nimo>> es mayor o igual al <<Stock MÃ¡ximo>>", 3, "el", 1); conn.rollback(); }
     }else{  valTxt(txtCodAlt, txtCodAlt, "Codigo alterno 2", "<<Codigo alterno 2>> ya existe", 3, "el", 0);  conn.rollback(); }
    }else{ valTxt(txtCodAlt, txtCodAlt, "Codigo alterno", "<<Codigo alterno>> ya existe", 3, "el", 0);  conn.rollback(); }

   }
     conn.close();
     conn=null;
      
 }}catch (SQLException evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt); }
  catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt);  }
 return blnRes;
}

private boolean validadCampos(){
  boolean blnRes=true;
  try{

     if(txtNomItm.getText().equals("")) {
        strMsg = "El campo <<Nombre del Item>> es obligatorio.\nEscriba el Nombre del Item y vuelva a intentarlo.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        tabGenInv.setSelectedIndex(0);
        txtNomItm.requestFocus();
        blnRes=false;
     }else if(!chkEsItmSer.isSelected()){
          if(!chkIvaCom.isSelected()&&!chkIvaVta.isSelected()){
                strMsg = "El campo  <<IVA>> es obligatorio.\nSeleccione una clase de IVA y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                tabGenInv.setSelectedIndex(0);
                chkIvaCom.requestFocus();
                 blnRes=false;
          }
      }else if (txtItmUni.getText().length()>6){
            valTxt(txtItmUni, txtItmUni, "Item(s) por Unidad", "", 2, "el", 0);
             blnRes=false;
      }else if(!txtStkMin.getText().equals("") && !txtStkMin.getText().equals("")){
         if (Integer.parseInt(txtStkMin.getText())>=Integer.parseInt(txtStkMax.getText())) {
            valTxt(txtStkMin, txtStkMax, "Stock MÃ­nimo / Stock MÃ¡ximo", "<<Stock MÃ­nimo>> es mayor o igual al <<Stock MÃ¡ximo>>", 3, "el", 1);
            blnRes=false;
         }
      }

  }catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}

private boolean validadCodAltMod(java.sql.Connection conn, String strCodItm, String strCodAlt, String strCampCodAlt){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 try{
 if(conn!=null){
    stmLoc=conn.createStatement();

    strSql="SELECT * FROM ( select "+strCampCodAlt+"," +
    " CASE WHEN ( " +
            " select "+strCampCodAlt+" from tbm_inv where co_emp="+objZafParSis.getCodigoEmpresa()+" "+
            " AND UPPER("+strCampCodAlt+") = '"+strCodAlt.trim().replaceAll("'", "''").toUpperCase()+"' "+
            " )  IS NOT NULL  THEN  'S' ELSE 'N' END AS tip " +
            "," +
            " CASE WHEN  "+strCampCodAlt+" IN ( " +
            "   select "+strCampCodAlt+" from tbm_inv where co_emp="+objZafParSis.getCodigoEmpresa()+" " +
            " AND UPPER("+strCampCodAlt+") = '"+strCodAlt.trim().replaceAll("'", "''").toUpperCase()+"' "+
            " )   THEN  'S' ELSE 'N' END AS est " +
            " FROM tbm_inv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_itm="+strCodItm+"  "+
     " ) AS x WHERE tip='S' and est='N' ";
    System.out.println("-->"+strSql);
    rstLoc=stmLoc.executeQuery(strSql);
    if(rstLoc.next()){
      blnRes=false;
    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
 }}catch(SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);   }
   catch(Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);   }
 return blnRes;
}

private boolean validadStkMaxMin(java.sql.Connection conn, String strCodItm ){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 try{
 if(conn!=null){
    stmLoc=conn.createStatement();

    strSql="SELECT nd_stkMin, nd_stkMax FROM tbm_inv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+strCodItm+" " +
    " AND nd_stkMin >= nd_stkMax ";
    rstLoc=stmLoc.executeQuery(strSql);
    if(rstLoc.next()){
      blnRes=false;
    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
 }}catch(SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);   }
   catch(Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);   }
 return blnRes;
}

public boolean modificarInv(java.sql.Connection conn ){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc;
 String chrItmSer="N", chrIvaCom="", chrIvaVta="", chrEst="", strAuxCodAlt="", strCamPreVta="";
 String strFecUltMod;
 int intControl=0;
 try{
  if(conn != null){
    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
    if (datFecAux==null)
        return false;
    strFecUltMod=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
    stmLoc = conn.createStatement();
    stmLoc01 = conn.createStatement();

    if(chkEsItmSer.isSelected()) chrItmSer="S";
    if(chkEsItmTra.isSelected()) chrItmSer="T";
    if(chkEsItmOtr.isSelected()) chrItmSer="O";


    if(chkIvaCom.isSelected())  chrIvaCom="S";
    else  chrIvaCom="N";

    if(chkIvaVta.isSelected()) chrIvaVta="S";
    else  chrIvaVta="N";

    if(cboEst.getSelectedIndex()==0) chrEst="A";
    else  chrEst="I";

    if(chkCamPreVta.isSelected()) strCamPreVta="S";

    strSql="SELECT co_emp , co_itm FROM tbm_equInv  WHERE "+
    " co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" "+
    " AND co_itm="+txtCodItm.getText()+")";
    rstLoc = stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
       //if(!CreaItem_His(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_itm") )){
       //   intControl=1;
       //   break;
       //}
        strSql="UPDATE tbm_inv SET  st_blqPreVta = "+(strCamPreVta.equals("")?null:"'"+strCamPreVta+"'")+" , " +
        " tx_codAlt="+((strAux=txtCodAlt.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux.toUpperCase()+"'")+", " +
        " tx_codAlt2="+((strAux=txtCodAlt2.getText().replaceAll("'", "''")).equals("")?"'"+strAuxCodAlt.toUpperCase()+"'":"'"+strAux.toUpperCase()+"'")+", "+
        " tx_nomItm="+((strAux=txtNomItm.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        " co_uni="+((strAux=txtCodUniMed.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        " nd_itmUni="+((strAux=txtItmUni.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        " st_ser='"+chrItmSer+"', "+
        " nd_cosUni="+((strAux=txtCosUni.getText().replaceAll("'", "''")).equals("")?"0":strAux )+",nd_cosult=0, "+
        " nd_preVta1="+((strAux=txtCosUni1.getText().replaceAll("'", "''")).equals("")?"0":strAux )+", "+
        " nd_preVta2="+((strAux=txtCosUni2.getText().replaceAll("'", "''")).equals("")?"0":strAux )+", "+
        " nd_preVta3="+((strAux=txtCosUni3.getText().replaceAll("'", "''")).equals("")?"0":strAux )+", "+
        " nd_stkAct="+((strAux=txtStkAct.getText().replaceAll("'", "''")).equals("")?"Null":strAux)+", "+
        " nd_stkMin="+((strAux=txtStkMin.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        " nd_stkMax="+((strAux=txtStkMax.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        " st_ivaCom='"+chrIvaCom+"', st_ivaVen='"+chrIvaVta+"', "+
        " tx_obs1="+((strAux=txaObs1.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        " tx_obs2="+((strAux=txaObs2.getText().replaceAll("'", "''")).equals("")?"Null":"'"+strAux+"'")+", "+
        " st_reg='"+chrEst+"', co_usrMod="+objZafParSis.getCodigoUsuario()+", fe_ultmod='"+strFecUltMod +"', "+
        " nd_pesitmkgr="+spnPeso.getModel().getValue().toString()+", st_permodnomitmven='"+(chkpermodnomven.isSelected()?"S":"N")+"', st_permodnomitmcom= '"+(chkpermodnomcom.isSelected()?"S":"N")+"', "+
        " st_oblCamNomItm="+(chkOblCamNomItm.isSelected()?"'S'":"NULL")+        
        " WHERE co_emp="+rstLoc.getInt("co_emp")+" AND co_itm="+rstLoc.getInt("co_itm");
       
        stmLoc01.executeUpdate(strSql);
    }
    objHisTblBasDat.insertarHistoricoMasivo(conn, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objZafParSis.getCodigoUsuario());
    rstLoc.close();
    rstLoc=null;
    stmLoc01.close();
    stmLoc01=null;
    stmLoc.close();
    stmLoc=null;

    if(intControl==0) blnRes=true;

  }}catch(SQLException evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, evt); }
    catch(Exception evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, evt); }
  return blnRes;
}


//
///**FunciÃ³n que me permÃ­te modificar los datos del Articulo seleccionado*/
//public boolean modificarInv(java.sql.Connection con ){
// boolean blnRes=false;
// java.sql.Statement stm;
// java.sql.ResultSet rst;
// txtCodAltAux.setText("");
// try{
//  if(con != null){
//    stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
//    strSql="";
//    strSql = strSql + " SELECT tx_codAlt";
//    strSql = strSql + " FROM tbm_inv";
//    strSql = strSql + " WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "";
//    strSql = strSql + " and co_itm = " + txtCodItm.getText().replaceAll("'", "''") + "";
//
//    rst = stm.executeQuery(strSql);
//    if (rst.next())
//        txtCodAltAux.setText(((rst.getString("tx_codAlt")==null)?"":rst.getString("tx_codAlt")));
//    rst.close();
//    rst=null;
//
//    if (!txtCodAltAux.getText().equals(txtCodAlt.getText().replaceAll("'", "''"))){
//        strSql = "";
//        strSql = strSql + " SELECT tx_codAlt";
//        strSql = strSql + " FROM tbm_inv";
//        strSql = strSql + " WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "";
//        strSql = strSql + " and UPPER(tx_codAlt) = '" + txtCodAlt.getText().replaceAll("'", "''").toUpperCase() + "'";
//        if (!objUti.isCodigoUnico(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSql))
//        {
//            valTxt(txtCodAlt, txtCodAlt, "CÃ³digo alterno", "<<CÃ³digo alterno>> ya existe", 3, "el", 0);
//            return false;
//        }
//
//        strSql = "";
//        strSql = strSql + " SELECT tx_codAlt2";
//        strSql = strSql + " FROM tbm_inv";
//        strSql = strSql + " WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "";
//        strSql = strSql + " and UPPER(tx_codAlt2) = '" + txtCodAlt2.getText().replaceAll("'", "''").toUpperCase() + "'";
//        if (!objUti.isCodigoUnico(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSql))
//        {
//            valTxt(txtCodAlt, txtCodAlt, "Codigo alterno 2", "<<Codigo alterno 2>> ya existe", 3, "el", 0);
//            return false;
//        }
//    }
//
//
//
//
//
//
//                        strSql="";
//                        strSql = strSql + " SELECT nd_stkMin, nd_stkMax";
//                        strSql = strSql + " FROM tbm_inv";
//                        strSql = strSql + " WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "";
//                        strSql = strSql + " and co_itm = " + txtCodItm.getText().replaceAll("'", "''") + "";
//
//                        rst = stm.executeQuery(strSql);
//                        if (rst.next()){
//                            txtStkMinAux.setText(((rst.getString("nd_stkMin")==null)?"":rst.getString("nd_stkMin")));
//                            txtStkMaxAux.setText(((rst.getString("nd_stkMax")==null)?"":rst.getString("nd_stkMax")));
//                        }
//                        if ((!txtStkMinAux.getText().equals(txtStkMin.getText())) || (!txtStkMaxAux.getText().equals(txtStkMax.getText()))){
//                            //Validando los Stocks.
//                            double dblStkMin = Double.parseDouble(txtStkMin.getText().toString());
//                            double dblStkMax = Double.parseDouble(txtStkMax.getText().toString());
//                            if (dblStkMin >= dblStkMax) {
//                                valTxt(txtStkMin, txtStkMax, "Stock MÃ­nimo / Stock MÃ¡ximo", "<<Stock MÃ­nimo>> es mayor o igual al <<Stock MÃ¡ximo>>", 3, "el", 1);
//                                return false;
//                            }
//                        }
//                        rst.close();
//
//                        /**Almacena el estado que haya seleccionado el usuario en una variable*/
//                        char chrEst,chrItmSer,chrIvaCom,chrIvaVta;
//
//                        /**Asigno a la variable el valor segun lo que haya seleccionado el usuario en el combo*/
//                        if (cboEst.getSelectedIndex()==0)
//                            chrEst= 'A';
//                        else
//                            chrEst= 'I';
//
//                       /**Bloque que almacena en una variable de tipo char si es item de servicio
//                         *el articulo segun si han marcado la casilla o no*/
//                         chrItmSer = 'N';
//
//                        if (chkEsItmSer.isSelected())
//                            chrItmSer = 'S';
//
//                        if (chkEsItmTra.isSelected())
//                            chrItmSer = 'T';
//
//                        /**Bloque que almacena en una variable de tipo char si el articulo
//                         tiene Iva en Compras o no*/
//                        if (chkIvaCom.isSelected())
//                            chrIvaCom = 'S';
//                        else
//                            chrIvaCom = 'N';
//
//                        /**Bloque que almacena en una variable de tipo char si el articulo
//                         tiene Iva en Ventas o no*/
//                        if (chkIvaVta.isSelected())
//                            chrIvaVta = 'S';
//                        else
//                            chrIvaVta = 'N';
//
//
//
//                         String  sql ="SELECT co_emp , co_itm FROM tbm_equInv  WHERE " +
//                         " co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
//                         "  AND co_itm="+txtCodItm.getText()+")";
//                        // System.out.println(">>>>>>> "+ sql );
//                          java.sql.Statement stmMae=con.createStatement();
//                          java.sql.ResultSet rstMae;
//                          rstMae = stm.executeQuery(sql);
//                          while(rstMae.next()){
//
//                               // System.out.println("OKKK 1.. ");
//
//                           if(!CreaItem_His(con, rstMae.getInt(1), rstMae.getInt(2) )){
//                          // System.out.println(" ERROR .......... ");
//                            con.rollback();
//                            con.close();
//                            con=null;
//                            return false;
//                           }
//
//
//                         strAux = txtCodAlt.getText().replaceAll("'", "''");
//                         if(strAux.equals(""))
//                           strAux="Null";
//
//                         String strAux2="Null";
//
//                          strAux2 = txtCodAlt2.getText().replaceAll("'", "''");
//                         if(strAux2.equals(""))
//                           strAux2="Null";
//
//                        if(txtCodAlt2.getText().trim().equals(""))
//                             strAux2=strAux;
//
//                        /**Haciendo la modificaciÃ³n en la tabla segun el codigo del articulo
//                        al que estemos haciendo referencia*/
//                        strSql = "";
//                        strSql = strSql + "UPDATE tbm_inv SET";
//                        strSql = strSql + " tx_codAlt = " + ((strAux = txtCodAlt.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux.toUpperCase() + "'");
//                        strSql = strSql + ", tx_codAlt2 = '" + strAux2.toUpperCase() + "' " +
//                                "" +
//                        " ,st_permodnomitmven='"+(chkpermodnomven.isSelected()?"S":"N")+"' "+
//                        " ,st_permodnomitmcom='"+(chkpermodnomcom.isSelected()?"S":"N")+"' "+
//                        "";
//
//                        strSql = strSql + ", tx_nomItm = " + ((strAux = txtNomItm.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
//
////                        if (txtCodLin.getText().equals(""))
////                            strSql = strSql + ", co_cla1 = " + null + "";
////                        else{
////                            strSql = strSql + ", co_cla1 = " + txtCodLin.getText().replaceAll("'", "''") + "";}
////
////                        if (txtCodGrp.getText().equals(""))
////                            strSql = strSql + ", co_cla2 = " + null + "";
////                        else{
////                            strSql = strSql + ", co_cla2 = " + txtCodGrp.getText().replaceAll("'", "''") + "";}
////
////                        if (txtCodMar.getText().equals(""))
////                            strSql = strSql + ", co_cla3 = " + null + "";
////                        else{
////                            strSql = strSql + ", co_cla3 = " + txtCodMar.getText().replaceAll("'", "''") + "";}
//
//                        if (txtCodUniMed.getText().equals(""))
//                            strSql = strSql + ", co_uni = " + null + "";
//                        else{
//                            strSql = strSql + ", co_uni = " + txtCodUniMed.getText().replaceAll("'", "''") + "";}
//
//                        if (txtItmUni.getText().equals(""))
//                            strSql = strSql + ", nd_itmUni = " + null + "";
//                        else{
//                            strSql = strSql + ", nd_itmUni = " + txtItmUni.getText().replaceAll("'", "''") + "";}
//
//                        strSql = strSql + ", st_ser = '" + chrItmSer + "'";
//
//                        if (txtCosUni.getText().equals(""))
//                            strSql = strSql + ", nd_cosUni = " + null + "";
//                        else{
//                            strSql = strSql + ", nd_cosUni = " + txtCosUni.getText().replaceAll("'", "''") + "";}
//
//                        if (txtCosUni1.getText().equals(""))
//                            strSql = strSql + ", nd_preVta1 = " + null + "";
//                        else{
//                            strSql = strSql + ", nd_preVta1 = " + txtCosUni1.getText().replaceAll("'", "''") + "";}
//
//                        if (txtCosUni2.getText().equals(""))
//                            strSql = strSql + ", nd_preVta2 = " + null + "";
//                        else{
//                            strSql = strSql + ", nd_preVta2 = " + txtCosUni2.getText().replaceAll("'", "''") + "";}
//
//                        if (txtCosUni3.getText().equals(""))
//                            strSql = strSql + ", nd_preVta3 = " + null + "";
//                        else{
//                            strSql = strSql + ", nd_preVta3 = " + txtCosUni3.getText().replaceAll("'", "''") + "";}
//
//
//                        if (txtStkMin.getText().equals(""))
//                            strSql = strSql + ", nd_stkMin = " + null + "";
//                        else{
//                            strSql = strSql + ", nd_stkMin = " + txtStkMin.getText().replaceAll("'", "''") + "";}
//
//                        if (txtStkMax.getText().equals(""))
//                            strSql = strSql + ", nd_stkMax = " + null + "";
//                        else{
//                            strSql = strSql + ", nd_stkMax = " + txtStkMax.getText().replaceAll("'", "''") + "";}
//
//                        strSql = strSql + ", st_ivaCom = '" + chrIvaCom + "'";
//                        strSql = strSql + ", st_ivaVen = '" + chrIvaVta + "'";
//                        strSql = strSql + ", tx_obs1 = " + ((strAux = txaObs1.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
//                        strSql = strSql + ", tx_obs2 = " + ((strAux = txaObs2.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
//                        strSql = strSql + ", st_reg = '" + chrEst + "'";
//                        strSql = strSql + ", co_usrmod = " + objZafParSis.getCodigoUsuario() + "";
//
//                        strSql = strSql + ", fe_ultmod = '"+objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos())+"'";
//
//                        strSql = strSql + ", nd_pesitmkgr = "+spnPeso.getModel().getValue().toString();
//
//                        strSql = strSql + " WHERE co_emp = " + rstMae.getInt(1) + "";
//                        strSql = strSql + " and co_itm = " + rstMae.getInt(2) + "";
//                        //System.out.println(strSql);
//                        stmMae.executeUpdate(strSql);
//                           //  System.out.println("OK.......... ");
//
//                       }    //*****}
//                       //  System.out.println(" ERROR 222.......... ");
//                        con.commit();
//                        stm.close();
//                        con.close();
//                    }
//                }
//		catch(SQLException evt)
//		{
//                    objUti.mostrarMsgErr_F1(this, evt);
//                    return false;
//		}
//		catch(Exception evt)
//		{
//                    objUti.mostrarMsgErr_F1(this, evt);
//                    return false;
//		}
//            blnCmb = false;
//            return true;
//	}
//



//*********************************************************************************************************  
private boolean CreaItem_His(java.sql.Connection conn,int coemp,int codItm){
  boolean blnres=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rst;
  String sql="";
  try{ 
   int intCorRegPre=0;
   stmLoc=conn.createStatement();

    sql="select count(co_reg)+1 as co_reg from tbh_inv where co_emp="+coemp+" and co_itm="+codItm;
    rst = stmLoc.executeQuery(sql);
    if(rst.next()) intCorRegPre=rst.getInt(1);
    else  intCorRegPre=1;
        sql="INSERT INTO tbh_inv(co_emp,co_itm,co_reg,tx_codAlt,tx_codAlt2,tx_nomItm,   co_uni,nd_itmUni," +
        "st_ser,nd_preVta1,nd_preVta2,nd_preVta3,nd_stkMin,nd_stkMax,st_ivaCom,st_ivaVen,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultMod," +
        "co_usrIng,co_usrMod,nd_stkact, nd_pesitmkgr, st_permodnomitmcom, st_permodnomitmven )" +
        " SELECT co_emp,co_itm, "+intCorRegPre+",tx_codAlt,tx_codAlt2,tx_nomItm, co_uni,nd_itmUni," +
        "st_ser,nd_preVta1,nd_preVta2,nd_preVta3,nd_stkMin,nd_stkMax,st_ivaCom,st_ivaVen,tx_obs1,tx_obs2,st_reg,fe_ing," +
        "fe_ultMod,co_usrIng,co_usrMod,nd_stkact, nd_pesitmkgr, st_permodnomitmcom, st_permodnomitmven  " +
        " FROM tbm_inv WHERE co_emp="+coemp+" and co_itm="+codItm;
        stmLoc.executeUpdate(sql);

        stmLoc.close();
        stmLoc=null;
        rst.close();
        rst=null;
     
   }catch (java.sql.SQLException e) { blnres=false;  objUti.mostrarMsgErr_F1(this, e);  }
    catch (Exception e)  {  blnres=false;  objUti.mostrarMsgErr_F1(this, e); }
  return blnres;
}
   
    //*********************************************************************************************************  
   
    
    
    /**FunciÃ³n que permitirÃ¡ eliminar un registro*/
    public boolean eliminarReg() {
        java.sql.PreparedStatement pstInv;
        String strFecUltMod;
        boolean blnMaestro=false;
        try
        {
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            try{
              if (con!=null){
                  datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
                
                java.sql.Statement stm = con.createStatement();
                strSql=" Select count(co_reg) as num from tbm_detmovinv as det ," +
                       " (Select co_itmmae,co_emp, co_itm  from tbm_equinv where co_itmmae = (select co_itmmae from tbm_equinv " +
                       " where co_emp = "+ objZafParSis.getCodigoEmpresa() + " and  co_itm="+ txtCodItm.getText()+") order by co_emp) as a2 "+
                       " where det.co_emp=a2.co_emp and det.co_itm = a2.co_itm";
                java.sql.ResultSet rst = stm.executeQuery(strSql);
                if (rst.next()){
                    if (rst.getInt("num")>0){
                        MensajeInf("No se puede eliminar existen movimientos asociados con el item");
                        rst.close();
                        stm.close();
                        con.close();
                        return false;
                    }                        
                }else{
                    MensajeInf("Error!! \n"+strSql);
                    rst.close();
                    stm.close();
                    con.close();
                    return false;
                }
                rst.close();
                strSql = "";
                strSql = " Select co_itmmae,co_emp, co_itm  from tbm_equinv where co_itmmae = (select co_itmmae from tbm_equinv where co_emp = " + objZafParSis.getCodigoEmpresa() +" and  co_itm="+ txtCodItm.getText().replaceAll("'", "''") + ") order by co_emp";
                rst = stm.executeQuery(strSql);
                while (rst.next())
                {                    
                    strSql = "";
                    strSql = strSql + " DELETE FROM tbm_equinv";
                    strSql = strSql + " WHERE co_itmmae =" + rst.getInt("co_itmmae");
                    strSql = strSql + " and co_emp = " + rst.getInt("co_emp") + "";
                    strSql = strSql + " and co_itm = " + rst.getInt("co_itm") + "";

                   // pstInv = con.prepareStatement(strSql);
                  //  pstInv.executeUpdate();                    

                    
//                     if(!CreaItem_His(con, objZafParSis.getCodigoEmpresa(), Integer.parseInt(txtCodItm.getText()))){
//                            con.rollback();
//                            con.close();
//                            con=null;
//                            return false;
//                        }
//                      
                    strSql = "";
                    strSql = strSql + " DELETE FROM tbm_invbod";
                    strSql = strSql + " WHERE co_emp = " + rst.getInt("co_emp") + "";
                    strSql = strSql + " and co_itm = " + rst.getInt("co_itm") + "";

                   // pstInv = con.prepareStatement(strSql);
                   // pstInv.executeUpdate();
                    strSql = "";
                    strSql = strSql + " update tbm_inv set st_reg='E' , fe_ultmod='"+strFecUltMod+"', co_usrmod="+objZafParSis.getCodigoUsuario();
                    strSql = strSql + " WHERE co_emp = " + rst.getInt("co_emp") + "";
                    strSql = strSql + " and co_itm = " + rst.getInt("co_itm") + "";

                    pstInv = con.prepareStatement(strSql);
                    pstInv.executeUpdate();
                }
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objZafParSis.getCodigoUsuario());
                con.commit();
                con.setAutoCommit(true);
                rst.close();
                stm.close();
                con.close();

                limpiarDatInv();
                deshabilitarDatInv();
              }
            }catch(SQLException evt){
                con.rollback();
                con.close();
                objUti.mostrarMsgErr_F1(this, evt);
                return false;
            }
        }catch(Exception evt){
            objUti.mostrarMsgErr_F1(this, evt);
            return false;
        }
    blnCmb = false;
    return true;
   }
        
    
    private String filtarSql() {
        String sqlFlt = "";
        //Agregando filtro por Codigo del Item
        if(!txtCodItm.getText().equals(""))
            sqlFlt = sqlFlt + " and co_itm = " + txtCodItm.getText().replaceAll("'", "''") + "";

        //Agregando filtro por Codigo Alterno
        if(!txtCodAlt.getText().equals(""))
            sqlFlt = sqlFlt + " and LOWER(tx_codAlt) LIKE '" + txtCodAlt.getText().replaceAll("'", "''").toLowerCase() + "%' ";


          if(!txtCodAlt2.getText().equals(""))
            sqlFlt = sqlFlt + " and LOWER(tx_codAlt2) LIKE '" + txtCodAlt2.getText().replaceAll("'", "''").toLowerCase() + "%' ";

        
        //Agregando filtro por Nombre
        if(!txtNomItm.getText().equals(""))
            sqlFlt = sqlFlt + " and LOWER(tx_nomItm) LIKE '" + txtNomItm.getText().replaceAll("'", "''").replace('*', '%').toLowerCase() + "%' ";
        
        //Agregando filtro por Codigo de Linea
        if(!txtCodLin.getText().equals(""))
            sqlFlt = sqlFlt + " and co_cla1 = " + txtCodLin.getText().replaceAll("'", "''") + "";
        
        //Agregando filtro por Codigo de Grupo
        if(!txtCodGrp.getText().equals(""))
            sqlFlt = sqlFlt + " and co_cla2 = " + txtCodGrp.getText().replaceAll("'", "''") + "";
        
        //Agregando filtro por Codigo de Marca
        if(!txtCodMar.getText().equals(""))
            sqlFlt = sqlFlt + " and co_cla3 = " + txtCodMar.getText().replaceAll("'", "''") + "";
        
        //Agregando filtro por Codigo de Unidad de Medida
        if(!txtCodUniMed.getText().equals(""))
            sqlFlt = sqlFlt + " and co_uni = " + txtCodUniMed.getText().replaceAll("'", "''") + "";
        
        //Agregando filtro por Item(s) por Unidad
        if(!txtItmUni.getText().equals(""))
            sqlFlt = sqlFlt + " and nd_itmUni = " + txtItmUni.getText().replaceAll("'", "''") + "";
              
        
      return sqlFlt ;
    }
    
//
//    private boolean consultarDatInv(String strFlt){
//       strFil = strFlt;
//       try{
//            conCab = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//            if (conCab!=null){
//
//                stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
//
//                /**Agregando el Sql de Consulta para el Maestro*/
//                strSql = "";
//                strSql = strSql + "SELECT * FROM tbm_inv WHERE st_reg not in ('T','E','U') and co_emp = " + objZafParSis.getCodigoEmpresa() + "";
//                strSql = strSql + strFil + " ORDER BY co_itm";
//
//              //  System.out.println("====> "+strSql);
//
//                rstCab = stmCab.executeQuery(strSql);
//
//                if(rstCab.next()){
//                    rstCab.last();
//                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros.");
//                    cargarReg();
//                }
//                else{
//                    objTooBar.setMenSis("Se encontraron 0 registros...");
//                    strMsg="No se ha encontrado ningÃºn registro que cumpla el criterio de bÃºsqueda especÃ­ficado.";
//                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
//                    rstCab = null;
//                    stmCab.close();
//                    conCab.close();
//                    stmCab=null;
//                    conCab=null;
//                    limpiarDatInv();
//                    return false;
//                }
//            blnCmb = false;
//            }
//   }
//   catch(SQLException Evt)
//   {
//      objUti.mostrarMsgErr_F1(this, Evt);
//      return false;
//   }
//   catch(Exception Evt)
//   {
//      objUti.mostrarMsgErr_F1(this, Evt);
//      return false;
//   }
//   return true;
//}
//
    
    
    
    /* Agrega el listener para detectar que hubo algun cambio en la caja de texto*/
    private void addListenerCambios(){
        objLisCmb = new LisTxt();
        txtCodItm.setText("");
        txtCodItm.getDocument().addDocumentListener(objLisCmb);
        txtCodAlt.getDocument().addDocumentListener(objLisCmb);
        txtCodAlt2.getDocument().addDocumentListener(objLisCmb);
        txtCosUni.getDocument().addDocumentListener(objLisCmb);
        txtCosUni1.getDocument().addDocumentListener(objLisCmb);
        txtCosUni2.getDocument().addDocumentListener(objLisCmb);
        txtCosUni3.getDocument().addDocumentListener(objLisCmb);
        txtItmUni.getDocument().addDocumentListener(objLisCmb);
        txtNomItm.getDocument().addDocumentListener(objLisCmb);
        txtStkAct.getDocument().addDocumentListener(objLisCmb);
        txtStkMax.getDocument().addDocumentListener(objLisCmb);
        txtStkMin.getDocument().addDocumentListener(objLisCmb);
        txtCodLin.getDocument().addDocumentListener(objLisCmb);
        txtLin.getDocument().addDocumentListener(objLisCmb);
        txtCodGrp.getDocument().addDocumentListener(objLisCmb);
        txtGrp.getDocument().addDocumentListener(objLisCmb);
        txtCodMar.getDocument().addDocumentListener(objLisCmb); 
        txtMar.getDocument().addDocumentListener(objLisCmb);
        txtCodUniMed.getDocument().addDocumentListener(objLisCmb);
        txtUniMed.getDocument().addDocumentListener(objLisCmb);
        txaObs1.getDocument().addDocumentListener(objLisCmb);
        txaObs2.getDocument().addDocumentListener(objLisCmb);
    }   
    

     /* Clase de tipo DocumentListener para detectar los cambios en los textcomponent*/
    class LisTxt implements javax.swing.event.DocumentListener {
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            blnCmb = true;
        }
        
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            blnCmb = true;
        }
        
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            blnCmb = true;
        }
    }
    
    
    /**Procedimiento que limpia todas las cajas de texto que existen en el frame*/
    public void limpiarDatInv()
    { 
        txtCodItm.setText("");
        txtCodAlt.setText("");
        txtCodAlt2.setText("");
        txtNomItm.setText("");
        txtItmUni.setText("");
        txtCosUni.setText("");
        txtCosUni1.setText("");
        txtCosUni2.setText("");
        txtCosUni3.setText("");
        txtStkAct.setText("");
        txtStkMin.setText("");
        txtStkMax.setText("");
        txtCodLin.setText("");
        txtLin.setText("");
        txtCodGrp.setText("");
        txtGrp.setText("");
        txtCodMar.setText("");
        txtMar.setText("");
        txtCodUniMed.setText("");
        txtUniMed.setText("");
        chkEsItmSer.setSelected(false);
        chkEsItmOtr.setSelected(false);
        chkEsItmTra.setSelected(false);
        chkIvaCom.setSelected(false);
        chkIvaVta.setSelected(false);
        chkCamPreVta.setSelected(false);

        chkOblCamNomItm.setSelected(false);

        txaObs1.setText("");
        txaObs2.setText(""); 
        txtfecing.setText("");
        txtfecmod.setText("");
        lblusuing.setText("");
        lblusuing1.setText("");
      
        spnPeso.setValue(new Double(0.00));
        objTblModPrv.removeAllRows();
        objTblModCla.removeAllRows();


        strCodLin="";
        strDesLarLin="";
        strCodGrp="";
        strDesLarGrp="";
        strCodMar="";
        strDesLarMar=""; 
        strCodUni="";
        strDesLarUni="";  
    }
        
    
    
    
    /**Procedimiento que me permÃ­te habilitar las cajas de texto para que estas puedan 
    ser manipuladas por el usuario*/
    public void habilitarDatInv()
    {                                
       txtCodAlt.setEnabled(true);
       txtNomItm.setEditable(true);
       txtItmUni.setEnabled(true);
       //txtCosUni1.setEnabled(true);
       //txtCosUni2.setEnabled(true);
       //txtCosUni3.setEnabled(true);
       txtStkMin.setEnabled(true);
       txtStkMax.setEnabled(true);
       txtCodLin.setEnabled(true);
       txtLin.setEnabled(true);
       txtCodGrp.setEnabled(true);
       txtGrp.setEnabled(true);
       txtCodMar.setEnabled(true);
       txtMar.setEnabled(true);
       txtCodUniMed.setEnabled(true);
       txtUniMed.setEnabled(true);
       chkEsItmSer.setEnabled(true);
       chkEsItmOtr.setEnabled(true);
       chkIvaCom.setEnabled(true);
       chkIvaVta.setEnabled(true);
       txaObs1.setEditable(true);
       txaObs2.setEditable(true);
       cboEst.setEnabled(true);
       butGrp.setEnabled(true);
       butLin.setEnabled(true);
       butMar.setEnabled(true);
       butUniMed.setEnabled(true);
       butCod.setEnabled(true);
    }
    
    
    /**Procedimiento que deshabilita las cajas de texto para que estas no puedan ser 
    manipuladas por el usuario*/
    public void deshabilitarDatInv()
    {
       txtCodItm.setEditable(false);
       txtCodAlt.setEnabled(false);
       txtNomItm.setEditable(false);
       txtItmUni.setEnabled(false);
       txtCosUni1.setEnabled(false);
       txtCosUni2.setEnabled(false);
       txtCosUni3.setEnabled(false);
       txtStkMin.setEnabled(false);
       txtStkMax.setEnabled(false);
       txtCodLin.setEnabled(false);
       txtLin.setEnabled(false);
       txtCodGrp.setEnabled(false);
       txtGrp.setEnabled(false);
       txtCodMar.setEnabled(false);
       txtMar.setEnabled(false);
       txtCodUniMed.setEnabled(false);
       txtUniMed.setEnabled(false);
       chkEsItmSer.setEnabled(false);
       chkEsItmOtr.setEnabled(false);
       chkIvaCom.setEnabled(false);
       chkIvaVta.setEnabled(false);
       txaObs1.setEditable(false);
       txaObs2.setEditable(false);
       cboEst.setEnabled(false);
       butGrp.setEnabled(false);
       butLin.setEnabled(false);
       butMar.setEnabled(false);
       butUniMed.setEnabled(false);
       butCod.setEnabled(false);
    }
    
    
    /**Procedimiento para hacer editable las Cajas de Texto*/
    public void editableDatInv()
    {
       txtCodAlt.setEditable(true);
       txtNomItm.setEditable(true);
       txtItmUni.setEditable(true);
       txtCosUni1.setEditable(true);
       txtCosUni2.setEditable(true);
       txtCosUni3.setEditable(true);
       txtStkMin.setEditable(true);
       txtStkMax.setEditable(true);
       txtCodLin.setEditable(true);
       txtLin.setEditable(true);
       txtCodGrp.setEditable(true);
       txtGrp.setEditable(true);
       txtCodMar.setEditable(true);
       txtMar.setEditable(true);
       txtCodUniMed.setEditable(true);
       txtUniMed.setEditable(true);
       chkEsItmSer.setEnabled(true);
       chkEsItmOtr.setEnabled(true);
       chkIvaCom.setEnabled(true);
       chkIvaVta.setEnabled(true);
       txaObs1.setEditable(true);
       txaObs2.setEditable(true);
       cboEst.setEnabled(true);
       butGrp.setEnabled(false);
       butLin.setEnabled(true);
       butMar.setEnabled(true);
       butUniMed.setEnabled(true);
       butCod.setEnabled(true);
    }
    
    
    private boolean flagNum(boolean blnFlgNum)
    {
        return blnFlgNum; 
    }
    
    
    private boolean flagCon(boolean blnFlgCon)
    {
        return blnFlgCon; 
    }
    
    
    /**<PRE>
     *FunciÃ³n quÃ© permitirÃ¡ validar que contenido de las cajas de texto segÃºn la validaciÃ³n que se desee
     *realizar. 
     *Esta funciÃ³n recibirÃ¡ los siquientes parÃ¡metros:
     *
     *jtfTxt: Objeto de tipo JTextField
     *jtfTxtAux: AlgÃºn objeto adicional de tipo JtextField
     *strCam: El campo al quÃ© estamos haciendo referencia.
     *strMsj: Si se envÃ­a como parÃ¡metro 3 en intOpc en estÃ¡ variable se deberÃ¡ enviar el Mensaje 
     *        de validaciÃ³n y el campo, pero si se envia 1 o 2 se la envÃ­a vacÃ­a. 
     *        (Ejm: "El campo <<X>> ya existe")
     *intOpc: se enviarÃ¡ como parÃ¡metro 1 si es una validaciÃ³n de nÃºmeros y 2 si es de lÃ­mite.
     *strMsjGen: El generÃ³ del campo (Ejm: "el")
     *intTab: Variable de tipo entero que recibirÃ¡ el Ã­ndice de JTabbedPane al que va a hacer referencia
     *</PRE>**/
    private void valTxt (javax.swing.JTextField jtfTxt, javax.swing.JTextField jtfTxtAux, String strCam, String strMsj, int intOpc,String strMsjGen, int intTab) {
        if (intOpc == 1){
            strMsg = "El campo <<"+ strCam +">> sólo acepta valores numéricos.\n¿Desea corregir " + strMsjGen + " <<"+ strCam +">> que ha ingresado?\nSi selecciona NO el sistema borrará los datos antes de abandonar este campo.";
        }
        if (intOpc == 2) {
            strMsg = "El campo <<"+ strCam +">> ha sobrepasado el límite.\n¿Desea corregir " + strMsjGen + " <<"+ strCam +">> que ha ingresado?\nSi selecciona NO el sistema borrará los datos antes de abandonar este campo.";
        }
        if (intOpc == 3){
            strMsg = "El campo " + strMsj + ".\n¿Desea corregir " + strMsjGen + " <<"+ strCam +">> que ha ingresado?\nSi selecciona NO el sistema borrará los datos antes de abandonar este campo.";
        }
        int intOpp = oppMsg.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if (intOpp == JOptionPane.YES_OPTION){
            tabGenInv.setSelectedIndex(intTab);
            jtfTxt.selectAll();
            jtfTxt.requestFocus();
        }
        else {
            tabGenInv.setSelectedIndex(intTab);
            jtfTxt.setText(""); 
            jtfTxtAux.setText("");
            jtfTxt.requestFocus();            
            }
    }    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panGen = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabGenInv = new javax.swing.JTabbedPane();
        panPriInv = new javax.swing.JPanel();
        lblCodClaInv = new javax.swing.JLabel();
        lblCodAlt = new javax.swing.JLabel();
        txtCodAlt = new javax.swing.JTextField();
        lblNomItm = new javax.swing.JLabel();
        txtNomItm = new javax.swing.JTextField();
        lblgrp = new javax.swing.JLabel();
        lblLin = new javax.swing.JLabel();
        lblMar = new javax.swing.JLabel();
        lblUniMed = new javax.swing.JLabel();
        lblEst = new javax.swing.JLabel();
        cboEst = new javax.swing.JComboBox();
        lblItmUni = new javax.swing.JLabel();
        txtItmUni = new javax.swing.JTextField();
        panItmSer = new javax.swing.JPanel();
        chkEsItmSer = new javax.swing.JCheckBox();
        chkEsItmTra = new javax.swing.JCheckBox();
        chkEsItmOtr = new javax.swing.JCheckBox();
        panIva = new javax.swing.JPanel();
        chkIvaVta = new javax.swing.JCheckBox();
        chkIvaCom = new javax.swing.JCheckBox();
        butLin = new javax.swing.JButton();
        txtLin = new javax.swing.JTextField();
        txtGrp = new javax.swing.JTextField();
        txtMar = new javax.swing.JTextField();
        txtUniMed = new javax.swing.JTextField();
        butMar = new javax.swing.JButton();
        butGrp = new javax.swing.JButton();
        butUniMed = new javax.swing.JButton();
        txtCodLin = new javax.swing.JTextField();
        txtCodGrp = new javax.swing.JTextField();
        txtCodMar = new javax.swing.JTextField();
        txtCodUniMed = new javax.swing.JTextField();
        butCod = new javax.swing.JButton();
        txtCodItm = new javax.swing.JTextField();
        txtCodAltAux = new javax.swing.JTextField();
        lblCodAlt1 = new javax.swing.JLabel();
        txtCodAlt2 = new javax.swing.JTextField();
        lblWebCli1 = new javax.swing.JLabel();
        lblWebCli2 = new javax.swing.JLabel();
        txtfecing = new javax.swing.JLabel();
        lblWebCli4 = new javax.swing.JLabel();
        lblusuing = new javax.swing.JLabel();
        txtfecmod = new javax.swing.JLabel();
        lblWebCli5 = new javax.swing.JLabel();
        lblusuing1 = new javax.swing.JLabel();
        lblPeso = new javax.swing.JLabel();
        spnPeso = new javax.swing.JSpinner();
        panItmSer1 = new javax.swing.JPanel();
        chkpermodnomcom = new javax.swing.JCheckBox();
        chkpermodnomven = new javax.swing.JCheckBox();
        chkOblCamNomItm = new javax.swing.JCheckBox();
        chkCamPreVta = new javax.swing.JCheckBox();
        panDatTec = new javax.swing.JPanel();
        panGrpPre = new javax.swing.JPanel();
        lblCosUni = new javax.swing.JLabel();
        txtCosUni = new javax.swing.JTextField();
        txtCosUni1 = new javax.swing.JTextField();
        lblCosUni1 = new javax.swing.JLabel();
        lblCosUni2 = new javax.swing.JLabel();
        txtCosUni2 = new javax.swing.JTextField();
        txtCosUni3 = new javax.swing.JTextField();
        lblCosUni3 = new javax.swing.JLabel();
        lblObs1 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        lblObs2 = new javax.swing.JLabel();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panStk = new javax.swing.JPanel();
        lblStkAct = new javax.swing.JLabel();
        txtStkAct = new javax.swing.JTextField();
        lblStkMin = new javax.swing.JLabel();
        txtStkMin = new javax.swing.JTextField();
        txtStkMax = new javax.swing.JTextField();
        lblStkMax = new javax.swing.JLabel();
        txtStkMinAux = new javax.swing.JTextField();
        txtStkMaxAux = new javax.swing.JTextField();
        panClasi = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCla = new javax.swing.JTable();
        panPrv = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPrv = new javax.swing.JTable();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setFrameIcon(null);
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

        panGen.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblTit.setForeground(new java.awt.Color(10, 36, 106));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Maestro de Inventario");
        panGen.add(lblTit, java.awt.BorderLayout.NORTH);

        tabGenInv.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N

        panPriInv.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        panPriInv.setLayout(null);

        lblCodClaInv.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodClaInv.setText("Código ");
        panPriInv.add(lblCodClaInv);
        lblCodClaInv.setBounds(20, 24, 60, 15);

        lblCodAlt.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodAlt.setText("Codigo Alterno");
        panPriInv.add(lblCodAlt);
        lblCodAlt.setBounds(20, 45, 116, 15);

        txtCodAlt.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });
        txtCodAlt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltFocusLost(evt);
            }
        });
        panPriInv.add(txtCodAlt);
        txtCodAlt.setBounds(136, 46, 84, 20);

        lblNomItm.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNomItm.setText("Nombre del Item");
        panPriInv.add(lblNomItm);
        lblNomItm.setBounds(20, 91, 120, 15);

        txtNomItm.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });
        panPriInv.add(txtNomItm);
        txtNomItm.setBounds(136, 90, 328, 20);

        lblgrp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblgrp.setText("Grupo");
        panPriInv.add(lblgrp);
        lblgrp.setBounds(20, 135, 160, 15);

        lblLin.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblLin.setText("Linea");
        panPriInv.add(lblLin);
        lblLin.setBounds(20, 112, 160, 15);

        lblMar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMar.setText("Marca");
        panPriInv.add(lblMar);
        lblMar.setBounds(20, 158, 160, 15);

        lblUniMed.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblUniMed.setText("Unidad de Medida");
        panPriInv.add(lblUniMed);
        lblUniMed.setBounds(20, 182, 160, 15);

        lblEst.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblEst.setText("Estado");
        panPriInv.add(lblEst);
        lblEst.setBounds(20, 229, 160, 15);

        cboEst.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        cboEst.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "Inactivo" }));
        cboEst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstActionPerformed(evt);
            }
        });
        panPriInv.add(cboEst);
        cboEst.setBounds(136, 229, 184, 20);

        lblItmUni.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblItmUni.setText("Item(s) por Unidad");
        panPriInv.add(lblItmUni);
        lblItmUni.setBounds(20, 205, 140, 15);

        txtItmUni.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtItmUni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtItmUniActionPerformed(evt);
            }
        });
        txtItmUni.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtItmUniFocusLost(evt);
            }
        });
        panPriInv.add(txtItmUni);
        txtItmUni.setBounds(136, 205, 184, 20);

        panItmSer.setBorder(javax.swing.BorderFactory.createTitledBorder("Estado del Item"));
        panItmSer.setName("Estado del Item"); // NOI18N
        panItmSer.setLayout(null);

        chkEsItmSer.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkEsItmSer.setText("Es item de Servicio");
        chkEsItmSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEsItmSerActionPerformed(evt);
            }
        });
        panItmSer.add(chkEsItmSer);
        chkEsItmSer.setBounds(10, 20, 160, 20);

        chkEsItmTra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkEsItmTra.setText("Es item de Transporte");
        chkEsItmTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEsItmTraActionPerformed(evt);
            }
        });
        panItmSer.add(chkEsItmTra);
        chkEsItmTra.setBounds(10, 40, 156, 20);

        chkEsItmOtr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkEsItmOtr.setText("Otros");
        chkEsItmOtr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEsItmOtrActionPerformed(evt);
            }
        });
        panItmSer.add(chkEsItmOtr);
        chkEsItmOtr.setBounds(10, 60, 152, 20);

        panPriInv.add(panItmSer);
        panItmSer.setBounds(310, 0, 180, 90);

        panIva.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Iva", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 11))); // NOI18N
        panIva.setLayout(null);

        chkIvaVta.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkIvaVta.setText("Iva en Ventas");
        chkIvaVta.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkIvaVtaItemStateChanged(evt);
            }
        });
        chkIvaVta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIvaVtaActionPerformed(evt);
            }
        });
        panIva.add(chkIvaVta);
        chkIvaVta.setBounds(8, 40, 116, 23);

        chkIvaCom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkIvaCom.setText("Iva en Compras");
        chkIvaCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIvaComActionPerformed(evt);
            }
        });
        panIva.add(chkIvaCom);
        chkIvaCom.setBounds(8, 16, 101, 23);

        panPriInv.add(panIva);
        panIva.setBounds(500, 0, 180, 72);

        butLin.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butLin.setText("...");
        butLin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLinActionPerformed(evt);
            }
        });
        panPriInv.add(butLin);
        butLin.setBounds(320, 112, 24, 20);

        txtLin.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtLin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLinActionPerformed(evt);
            }
        });
        txtLin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLinFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLinFocusLost(evt);
            }
        });
        panPriInv.add(txtLin);
        txtLin.setBounds(176, 112, 144, 20);

        txtGrp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGrpActionPerformed(evt);
            }
        });
        txtGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtGrpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGrpFocusLost(evt);
            }
        });
        panPriInv.add(txtGrp);
        txtGrp.setBounds(176, 135, 144, 20);

        txtMar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMarActionPerformed(evt);
            }
        });
        txtMar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMarFocusLost(evt);
            }
        });
        panPriInv.add(txtMar);
        txtMar.setBounds(176, 158, 144, 20);

        txtUniMed.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtUniMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUniMedActionPerformed(evt);
            }
        });
        txtUniMed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUniMedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUniMedFocusLost(evt);
            }
        });
        panPriInv.add(txtUniMed);
        txtUniMed.setBounds(176, 182, 144, 20);

        butMar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butMar.setText("...");
        butMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butMarActionPerformed(evt);
            }
        });
        panPriInv.add(butMar);
        butMar.setBounds(320, 158, 24, 20);

        butGrp.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butGrp.setText("...");
        butGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGrpActionPerformed(evt);
            }
        });
        panPriInv.add(butGrp);
        butGrp.setBounds(320, 135, 24, 20);

        butUniMed.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butUniMed.setText("...");
        butUniMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butUniMedActionPerformed(evt);
            }
        });
        panPriInv.add(butUniMed);
        butUniMed.setBounds(320, 182, 24, 20);

        txtCodLin.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodLin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLinActionPerformed(evt);
            }
        });
        txtCodLin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLinFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLinFocusLost(evt);
            }
        });
        panPriInv.add(txtCodLin);
        txtCodLin.setBounds(136, 112, 40, 20);

        txtCodGrp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        panPriInv.add(txtCodGrp);
        txtCodGrp.setBounds(136, 135, 40, 20);

        txtCodMar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodMarActionPerformed(evt);
            }
        });
        txtCodMar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodMarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodMarFocusLost(evt);
            }
        });
        panPriInv.add(txtCodMar);
        txtCodMar.setBounds(136, 158, 40, 20);

        txtCodUniMed.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodUniMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodUniMedActionPerformed(evt);
            }
        });
        txtCodUniMed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodUniMedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodUniMedFocusLost(evt);
            }
        });
        panPriInv.add(txtCodUniMed);
        txtCodUniMed.setBounds(136, 182, 40, 20);

        butCod.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butCod.setText("...");
        butCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCodActionPerformed(evt);
            }
        });
        panPriInv.add(butCod);
        butCod.setBounds(176, 24, 24, 20);

        txtCodItm.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodItmActionPerformed(evt);
            }
        });
        txtCodItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodItmFocusLost(evt);
            }
        });
        panPriInv.add(txtCodItm);
        txtCodItm.setBounds(136, 24, 40, 20);

        txtCodAltAux.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panPriInv.add(txtCodAltAux);
        txtCodAltAux.setBounds(220, 45, 84, 20);

        lblCodAlt1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodAlt1.setText("Codigo Alterno 2");
        panPriInv.add(lblCodAlt1);
        lblCodAlt1.setBounds(20, 68, 116, 15);

        txtCodAlt2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodAlt2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodAlt2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusGained(evt);
            }
        });
        panPriInv.add(txtCodAlt2);
        txtCodAlt2.setBounds(136, 68, 84, 20);

        lblWebCli1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblWebCli1.setText("Fecha Ingreso:");
        lblWebCli1.setToolTipText("Direcciòn de la pagina del cliente/proveedor");
        panPriInv.add(lblWebCli1);
        lblWebCli1.setBounds(20, 256, 100, 20);

        lblWebCli2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblWebCli2.setText("Fecha Modificacion:");
        lblWebCli2.setToolTipText("Direcciòn de la pagina del cliente/proveedor");
        panPriInv.add(lblWebCli2);
        lblWebCli2.setBounds(20, 280, 100, 20);

        txtfecing.setBackground(new java.awt.Color(255, 255, 255));
        txtfecing.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtfecing.setToolTipText("Direcciòn de la pagina del cliente/proveedor");
        txtfecing.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtfecing.setOpaque(true);
        panPriInv.add(txtfecing);
        txtfecing.setBounds(136, 256, 112, 20);

        lblWebCli4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblWebCli4.setText("Usuario Ingreso:");
        lblWebCli4.setToolTipText("Direcciòn de la pagina del cliente/proveedor");
        panPriInv.add(lblWebCli4);
        lblWebCli4.setBounds(296, 256, 96, 20);

        lblusuing.setBackground(new java.awt.Color(255, 255, 255));
        lblusuing.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblusuing.setToolTipText("Direcciòn de la pagina del cliente/proveedor");
        lblusuing.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblusuing.setOpaque(true);
        panPriInv.add(lblusuing);
        lblusuing.setBounds(388, 256, 172, 20);

        txtfecmod.setBackground(new java.awt.Color(255, 255, 255));
        txtfecmod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtfecmod.setToolTipText("Direcciòn de la pagina del cliente/proveedor");
        txtfecmod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtfecmod.setOpaque(true);
        panPriInv.add(txtfecmod);
        txtfecmod.setBounds(136, 280, 112, 20);

        lblWebCli5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblWebCli5.setText("Usuario Modfico:");
        lblWebCli5.setToolTipText("Direcciòn de la pagina del cliente/proveedor");
        panPriInv.add(lblWebCli5);
        lblWebCli5.setBounds(296, 280, 96, 20);

        lblusuing1.setBackground(new java.awt.Color(255, 255, 255));
        lblusuing1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblusuing1.setToolTipText("Direcciòn de la pagina del cliente/proveedor");
        lblusuing1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblusuing1.setOpaque(true);
        panPriInv.add(lblusuing1);
        lblusuing1.setBounds(388, 280, 172, 20);

        lblPeso.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblPeso.setText("Peso:");
        panPriInv.add(lblPeso);
        lblPeso.setBounds(360, 229, 60, 15);

        spnPeso.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));
        panPriInv.add(spnPeso);
        spnPeso.setBounds(400, 229, 80, 20);

        panItmSer1.setBorder(javax.swing.BorderFactory.createTitledBorder("Modifica Nombre Itm."));
        panItmSer1.setName("Desc.Itm"); // NOI18N
        panItmSer1.setLayout(null);

        chkpermodnomcom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkpermodnomcom.setText("Permite cambiar nombre del item  en compras.");
        chkpermodnomcom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkpermodnomcomActionPerformed(evt);
            }
        });
        panItmSer1.add(chkpermodnomcom);
        chkpermodnomcom.setBounds(10, 50, 260, 23);

        chkpermodnomven.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkpermodnomven.setText("Permite modificar nombre del item  en ventas.");
        chkpermodnomven.setActionCommand("Permite modificar nombre del item en ventas.");
        chkpermodnomven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkpermodnomvenActionPerformed(evt);
            }
        });
        panItmSer1.add(chkpermodnomven);
        chkpermodnomven.setBounds(10, 20, 260, 30);

        chkOblCamNomItm.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkOblCamNomItm.setText("Obligatorio cambiar nombre del ítem");
        panItmSer1.add(chkOblCamNomItm);
        chkOblCamNomItm.setBounds(10, 80, 260, 23);

        panPriInv.add(panItmSer1);
        panItmSer1.setBounds(380, 110, 280, 110);

        chkCamPreVta.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkCamPreVta.setText("Bloquear cambio de precio ventas.");
        panPriInv.add(chkCamPreVta);
        chkCamPreVta.setBounds(470, 80, 210, 23);

        tabGenInv.addTab("General", panPriInv);

        panDatTec.setLayout(null);

        panGrpPre.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Precios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 11))); // NOI18N
        panGrpPre.setLayout(null);

        lblCosUni.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCosUni.setText("Costo Unitario");
        panGrpPre.add(lblCosUni);
        lblCosUni.setBounds(8, 20, 140, 15);

        txtCosUni.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panGrpPre.add(txtCosUni);
        txtCosUni.setBounds(128, 20, 120, 21);

        txtCosUni1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCosUni1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCosUni1ActionPerformed(evt);
            }
        });
        txtCosUni1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCosUni1FocusLost(evt);
            }
        });
        panGrpPre.add(txtCosUni1);
        txtCosUni1.setBounds(128, 44, 120, 21);

        lblCosUni1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCosUni1.setText("Precio Unitario 1");
        panGrpPre.add(lblCosUni1);
        lblCosUni1.setBounds(8, 44, 140, 15);

        lblCosUni2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCosUni2.setText("Precio Unitario 2");
        panGrpPre.add(lblCosUni2);
        lblCosUni2.setBounds(8, 68, 140, 15);

        txtCosUni2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCosUni2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCosUni2ActionPerformed(evt);
            }
        });
        txtCosUni2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCosUni2FocusLost(evt);
            }
        });
        panGrpPre.add(txtCosUni2);
        txtCosUni2.setBounds(128, 68, 120, 21);

        txtCosUni3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCosUni3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCosUni3ActionPerformed(evt);
            }
        });
        txtCosUni3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCosUni3FocusLost(evt);
            }
        });
        panGrpPre.add(txtCosUni3);
        txtCosUni3.setBounds(128, 92, 120, 21);

        lblCosUni3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCosUni3.setText("Precio Unitario 3");
        panGrpPre.add(lblCosUni3);
        lblCosUni3.setBounds(8, 92, 140, 15);

        panDatTec.add(panGrpPre);
        panGrpPre.setBounds(16, 12, 256, 120);

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblObs1.setText("Observación 1");
        panDatTec.add(lblObs1);
        lblObs1.setBounds(20, 152, 136, 15);

        txaObs1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txaObs1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txaObs1FocusLost(evt);
            }
        });
        spnObs1.setViewportView(txaObs1);

        panDatTec.add(spnObs1);
        spnObs1.setBounds(112, 152, 408, 52);

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblObs2.setText("Observación 2");
        panDatTec.add(lblObs2);
        lblObs2.setBounds(20, 208, 128, 15);

        txaObs2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txaObs2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txaObs2FocusLost(evt);
            }
        });
        spnObs2.setViewportView(txaObs2);

        panDatTec.add(spnObs2);
        spnObs2.setBounds(112, 208, 408, 52);

        panStk.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Stock", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 11))); // NOI18N
        panStk.setLayout(null);

        lblStkAct.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblStkAct.setText("Stock Actual");
        panStk.add(lblStkAct);
        lblStkAct.setBounds(12, 16, 92, 15);

        txtStkAct.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panStk.add(txtStkAct);
        txtStkAct.setBounds(96, 16, 112, 21);

        lblStkMin.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblStkMin.setText("Stock Mínimo");
        panStk.add(lblStkMin);
        lblStkMin.setBounds(12, 40, 96, 15);

        txtStkMin.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtStkMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStkMinActionPerformed(evt);
            }
        });
        txtStkMin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStkMinFocusLost(evt);
            }
        });
        panStk.add(txtStkMin);
        txtStkMin.setBounds(96, 40, 112, 21);

        txtStkMax.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtStkMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStkMaxActionPerformed(evt);
            }
        });
        txtStkMax.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStkMaxFocusLost(evt);
            }
        });
        panStk.add(txtStkMax);
        txtStkMax.setBounds(96, 64, 112, 21);

        lblStkMax.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblStkMax.setText("Stock Máximo");
        panStk.add(lblStkMax);
        lblStkMax.setBounds(12, 64, 92, 15);

        panDatTec.add(panStk);
        panStk.setBounds(312, 12, 220, 96);

        txtStkMinAux.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panDatTec.add(txtStkMinAux);
        txtStkMinAux.setBounds(316, 112, 112, 21);

        txtStkMaxAux.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panDatTec.add(txtStkMaxAux);
        txtStkMaxAux.setBounds(428, 112, 112, 21);

        tabGenInv.addTab("Datos Técnicos", panDatTec);

        panClasi.setLayout(new java.awt.BorderLayout());

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
        jScrollPane1.setViewportView(tblCla);

        panClasi.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        tabGenInv.addTab("Clasificado", panClasi);

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
        jScrollPane2.setViewportView(tblPrv);

        panPrv.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        tabGenInv.addTab("Proveedores", panPrv);

        panGen.add(tabGenInv, java.awt.BorderLayout.CENTER);

        getContentPane().add(panGen, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void chkIvaVtaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkIvaVtaItemStateChanged
        // TODO add your handling code here:
        
        
       
        
        
    }//GEN-LAST:event_chkIvaVtaItemStateChanged

    private void chkEsItmTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEsItmTraActionPerformed
        // TODO add your handling code here:
          chkEsItmSer.setSelected(false);
          chkEsItmOtr.setSelected(false);
              
        if (chkEsItmTra.isSelected()) {
            txtCodAlt2.setEditable(true);
        } else {
            txtCodAlt2.setEditable(false);
        }          
    }//GEN-LAST:event_chkEsItmTraActionPerformed

    private void txtUniMedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUniMedFocusGained
        // TODO add your handling code here:
         strDesLarUni=txtUniMed.getText();
        txtUniMed.selectAll();
    }//GEN-LAST:event_txtUniMedFocusGained

    private void txtCodUniMedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodUniMedFocusGained
        // TODO add your handling code here:
        strCodUni=txtCodUniMed.getText();
        txtCodUniMed.selectAll();
    }//GEN-LAST:event_txtCodUniMedFocusGained

    private void txtMarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMarFocusGained
        // TODO add your handling code here:
        strDesLarMar=txtMar.getText();
        txtMar.selectAll();
    }//GEN-LAST:event_txtMarFocusGained

    private void txtCodMarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMarFocusGained
        // TODO add your handling code here:
        strCodMar=txtCodMar.getText();
        txtCodMar.selectAll();
    }//GEN-LAST:event_txtCodMarFocusGained

    private void txtGrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGrpFocusGained
        // TODO add your handling code here:
         strDesLarGrp=txtGrp.getText();
         txtGrp.selectAll();
    }//GEN-LAST:event_txtGrpFocusGained

    private void txtCodGrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpFocusGained
        // TODO add your handling code here:
          strCodGrp=txtCodGrp.getText();
          txtCodGrp.selectAll();
    }//GEN-LAST:event_txtCodGrpFocusGained

    private void txtLinFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLinFocusGained
        // TODO add your handling code here:
         strDesLarLin=txtLin.getText();
         txtLin.selectAll();
    }//GEN-LAST:event_txtLinFocusGained

    private void txtCodLinFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLinFocusGained
        // TODO add your handling code here:
          strCodLin=txtCodLin.getText();
          txtCodLin.selectAll();
    }//GEN-LAST:event_txtCodLinFocusGained
//
//     public int getCodItemenMaestro(java.sql.Connection con){
//        int intRes = 0;
//        try{
//                if (con!=null){
//                    java.sql.Statement stm = con.createStatement();
//                    String strSQL = "select max(co_itmmae) + 1 as co_itmmae from tbm_equinv";
//                    java.sql.ResultSet rst = stm.executeQuery(strSQL);
//                    if (rst.next()){
//                        intRes = rst.getInt("co_itmmae");
//                    }else{
//                        intRes = 1;
//                    }
//                }
//            }catch (java.sql.SQLException e){
//                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
//            }
//    
//        return intRes;
//    }
//    
//    
//     
//      private boolean CrearItemenEmpresas(java.sql.Connection conInv,int intCodEqu , int intCodIt, String Ter)
//    {
//        java.sql.PreparedStatement pstInv;
//        java.sql.Statement stmInv,stmEmp;
//        java.sql.ResultSet rstInv,rstEmp;
//        int intCodItm=0;
//        try
//        {
//            stmEmp = conInv.createStatement();
//            stmInv = conInv.createStatement();
//            strSql = "";
//            strSql = " Select co_emp from tbm_emp where  st_reg = 'A' order by co_emp ";
//            rstEmp = stmEmp.executeQuery(strSql);
//            char chrEst,chrItmSer,chrIvaCom,chrIvaVta;
//            while (rstEmp.next())
//            {
//                strSql = "";
//                strSql = "select max(co_itm)+1 as co_itm from tbm_inv where co_emp = " + rstEmp.getString("co_emp") +" and st_reg = 'A'";
//                rstInv = stmInv.executeQuery(strSql);
//                if (rstInv.next())
//                    intCodItm = rstInv.getInt("co_itm");
//                rstInv.close();
//                 
//                    strSql = "";
//                    strSql = strSql + "INSERT INTO tbm_inv (co_emp, co_itm, tx_codAlt,tx_codAlt2, tx_nomItm,  co_uni";
//                    strSql = strSql + ", st_ser, nd_cosUni,nd_cosult, nd_preVta1, nd_preVta2, nd_preVta3, nd_stkAct, nd_stkMin, nd_stkMax";
//                    strSql = strSql + ", st_ivaCom, st_ivaVen, tx_obs1, tx_obs2, st_reg, co_usrIng, fe_ing)";
//                   
//                   strSql = strSql + " select "+rstEmp.getString("co_emp")+", "+intCodItm+" ,  REPLACE (tx_codalt, '"+Ter+"' , 'S'),REPLACE (tx_codalt, '"+Ter+"' , 'S'), tx_nomitm, co_uni , st_ser, nd_cosuni,0, nd_prevta1"; 
//                   strSql = strSql + ",0,0, 0, 0,0, st_ivacom ,st_ivaven, '','', st_reg,9, '2006-10-28' from tbm_inv where co_emp=1 and co_itm="+intCodIt;
//
//                   //System.out.println("OK......"+strSql);
//                   
//                    pstInv = conInv.prepareStatement(strSql);
//                    pstInv.executeUpdate();
//                    
//                    strSql = "";
//                    strSql = " Insert into tbm_equinv (co_itmmae, co_emp, co_itm)values ("+intCodEqu +", " + rstEmp.getInt("co_emp") +","+ intCodItm + ")";
//                    pstInv = conInv.prepareStatement(strSql);
//                    pstInv.executeUpdate();
//                    
//                    //Creacion de Inventario Bodega                    
//                    strSql = " Select co_bod from tbm_bod where co_emp ="+ rstEmp.getInt("co_emp") +" and st_reg='A' and co_bod not in " +
//                             " (select co_bod from tbm_invbod where co_emp ="+ rstEmp.getInt("co_emp") +" and co_itm ="+ intCodItm+") " +
//                             " order by co_bod ";
//                    java.sql.ResultSet rst = stmInv.executeQuery(strSql);                    
//                    while(rst.next()){
//                        if (!creaItemenBodega(conInv,rstEmp.getInt("co_emp"),intCodItm,rst.getInt("co_bod"))){
//                            conInv.rollback();
//                            conInv.close();
//                            return false;
//                        }
//                    }  
//                    rst.close();
//                    stmInv.close();
//                    
//
//            }
//        }catch(SQLException evt)  {
//            try{
//                conInv.rollback();
//                conInv.close();
//            }catch(SQLException Evts){
//                objUti.mostrarMsgErr_F1(this, evt);
//                return false;
//            }                        
//            objUti.mostrarMsgErr_F1(this, evt);
//            return false;
//        } catch(Exception evt){
//            objUti.mostrarMsgErr_F1(this, evt);
//            return false;
//        }
//        return true;
//    } 
//       
//    
//      public boolean creaItemenBodega(java.sql.Connection con,int intCodEmp,int intCodItm, int intCodBod){
//        
//        try{                           
//            String strSQL = " Insert into tbm_invbod  (co_emp, co_bod, co_itm, nd_stkact, nd_stkmin, nd_stkmax ) " +
//                            " values( " + intCodEmp + ", "+  intCodBod + ", "+  intCodItm + ", 0, 0, 0)"; //stock actual, minimo y maximo==0  
//            java.sql.PreparedStatement pstInvBod = con.prepareStatement(strSQL);
//           // System.out.println(strSQL);
//            pstInvBod.executeUpdate();                           
//         }catch(java.sql.SQLException Evt){
//                try{
//                        con.rollback();
//                        con.close();
//                   }catch(java.sql.SQLException Evts){
//                        objUti.mostrarMsgErr_F1(this, Evts);
//                   }
//                   objUti.mostrarMsgErr_F1(this, Evt); 
//                   return false;
//            }catch(Exception Evt){
//                objUti.mostrarMsgErr_F1(this, Evt);
//                return false;
//            }       
//        return true;
//     }    
//    
//    
//      
      
      
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:

         Configura_ventana_consulta();
         

        if(intEst==1){
            
         objTooBar.consultar();
         
         objTooBar.setEstado('w');
         
        }
        
          
        
        
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtStkMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStkMaxActionPerformed
        txaObs1.requestFocus();
    }//GEN-LAST:event_txtStkMaxActionPerformed

    private void txtStkMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStkMinActionPerformed
        txtStkMax.requestFocus();
    }//GEN-LAST:event_txtStkMinActionPerformed

    private void txtCosUni3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCosUni3ActionPerformed
        txtStkMin.requestFocus();   
    }//GEN-LAST:event_txtCosUni3ActionPerformed

    private void txtCosUni2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCosUni2ActionPerformed
        txtCosUni3.requestFocus();
    }//GEN-LAST:event_txtCosUni2ActionPerformed

    private void txtCosUni1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCosUni1ActionPerformed
        txtCosUni2.requestFocus();
    }//GEN-LAST:event_txtCosUni1ActionPerformed

    private void txtItmUniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItmUniActionPerformed
        cboEst.requestFocus();
    }//GEN-LAST:event_txtItmUniActionPerformed

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtCodLin.requestFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtNomItm.requestFocus();
        
        
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void txaObs2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txaObs2FocusLost
        if (!txaObs2.getText().equals("")) {
            if (txaObs2.getText().length()>200 ){ 
                strMsg="El campo <<Observación 2>> ha sobrepasado el límite.\nDigite caracteres válidos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                tabGenInv.setSelectedIndex(1);
                txaObs2.setText("");
                txaObs2.requestFocus();
            } 
        }
    }//GEN-LAST:event_txaObs2FocusLost

    private void txaObs1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txaObs1FocusLost
        if (!txaObs1.getText().equals("")) {
            if (txaObs1.getText().length()>200 ){ 
                strMsg="El campo <<Observación 1>> ha sobrepasado el límite.\nDigite caracteres válidos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                tabGenInv.setSelectedIndex(1);
                txaObs1.setText("");
                txaObs1.requestFocus();
            } 
        }
    }//GEN-LAST:event_txaObs1FocusLost

    private void txtUniMedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUniMedFocusLost
      /*  if (!txtUniMed.getText().equals("")) {
            if (txtUniMed.getText().length()>30){ 
                strMsg="El campo <<DescripciÃ³n de Unidad de Medida>> ha sobrepasado el lÃ­mite.\nDigite caracteres vÃ¡lidos y vuelva a intentarlo.";
                oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                txtUniMed.setText("");
                tabGenInv.setSelectedIndex(0);
                txtUniMed.requestFocus();
            } 
        }    
        if (txtUniMed.getText().equals("")) {
            txtCodUniMed.setText("");
        }
        */
        
         
          if (!txtUniMed.getText().equalsIgnoreCase(strDesLarUni))
        {
            if (txtUniMed.getText().equals(""))
            {
                txtCodUniMed.setText("");
                txtUniMed.setText("");
            }
            else
            {
              BuscarUnidad("a.tx_desLar",txtUniMed.getText(),2);
            }
        }
        else
            txtUniMed.setText(strDesLarUni);
    }//GEN-LAST:event_txtUniMedFocusLost

    private void txtMarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMarFocusLost
    
       if (!txtMar.getText().equalsIgnoreCase(strDesLarMar))
        {
            if (txtMar.getText().equals(""))
            {
                txtCodMar.setText("");
                txtMar.setText("");
            }
            else
            {
              BuscarMarca("a.tx_desLar",txtMar.getText(),2);  
            }
        }
        else
            txtMar.setText(strDesLarMar);
    }//GEN-LAST:event_txtMarFocusLost

    private void txtGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGrpFocusLost
       if (!txtGrp.getText().equalsIgnoreCase(strDesLarGrp))
        {
            if (txtGrp.getText().equals(""))
            {
                txtCodGrp.setText("");
                txtGrp.setText("");
            }
            else
            {
              BuscarGrupo("a.tx_desLar",txtGrp.getText(),2);
            }
        }
        else
            txtGrp.setText(strDesLarGrp);
    }//GEN-LAST:event_txtGrpFocusLost

    private void txtLinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLinFocusLost

          
         if (!txtLin.getText().equalsIgnoreCase(strDesLarLin))
        {
            if (txtLin.getText().equals(""))
            {
                txtCodLin.setText("");
                txtLin.setText("");
            }
            else
            {
               BuscarLinea("a.tx_desLar",txtLin.getText(),2);
            }
        }
        else
            txtLin.setText(strDesLarLin);
    }//GEN-LAST:event_txtLinFocusLost

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
            if (!txtNomItm.getText().equals("")) {
                if (txtNomItm.getText().length()>80 ){ 
                    strMsg="El campo <<Nombre del Item>> ha sobrepasado el límite.\nDigite caracteres válidos y vuelva a intentarlo.";
                    oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE); 
                    txtNomItm.setText("");
                    tabGenInv.setSelectedIndex(0);
                    txtNomItm.requestFocus();
                } 
            }
    }//GEN-LAST:event_txtNomItmFocusLost

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        if (txtCodAlt.isEditable() || txtCodAlt.isEnabled()){
            String strTerItm = txtCodAlt.getText().trim().toUpperCase().substring(txtCodAlt.getText().trim().length()-1, txtCodAlt.getText().trim().length());
            if (strTerItm.equals("I") || strTerItm.equals("S")) {
                if(!(chkEsItmSer.isSelected() || chkEsItmTra.isSelected() || chkEsItmOtr.isSelected())){
                    txtCodAlt2.setEditable(false);
                } else {
                    txtCodAlt2.setEditable(true);
                    txtCodAlt2.setBackground(txtCodAlt.getBackground());                    
                }
            } else {
                txtCodAlt2.setEditable(true);
                txtCodAlt2.setBackground(txtCodAlt.getBackground());
            }
        }
    }//GEN-LAST:event_txtCodAltFocusLost

    private void txtCodItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodItmFocusLost
        
    }//GEN-LAST:event_txtCodItmFocusLost

    private void txtStkMaxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStkMaxFocusLost

    }//GEN-LAST:event_txtStkMaxFocusLost

    private void txtStkMinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStkMinFocusLost
    }//GEN-LAST:event_txtStkMinFocusLost

    private void txtCosUni3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCosUni3FocusLost
    }//GEN-LAST:event_txtCosUni3FocusLost

    private void txtCosUni2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCosUni2FocusLost
    }//GEN-LAST:event_txtCosUni2FocusLost

    private void txtCosUni1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCosUni1FocusLost
    }//GEN-LAST:event_txtCosUni1FocusLost

    private void txtItmUniFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtItmUniFocusLost
       
    }//GEN-LAST:event_txtItmUniFocusLost

    private void txtCodMarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMarFocusLost

  if (!txtCodMar.getText().equalsIgnoreCase(strCodMar))
        {
            if (txtCodMar.getText().equals(""))
            {
                txtCodMar.setText("");
                txtMar.setText("");
            }
            else
            {
                 BuscarMarca("a.co_reg",txtCodMar.getText(),0);     
            }
        }
        else
            txtCodMar.setText(strCodMar);
    }//GEN-LAST:event_txtCodMarFocusLost

    private void txtCodGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpFocusLost

  if (!txtCodGrp.getText().equalsIgnoreCase(strCodGrp))
        {
            if (txtCodGrp.getText().equals(""))
            {
                txtCodGrp.setText("");
                txtGrp.setText("");
            }
            else
            {
                 BuscarGrupo("a.co_reg",txtCodGrp.getText(),0);    
            }
        }
        else
            txtCodGrp.setText(strCodGrp);
    }//GEN-LAST:event_txtCodGrpFocusLost

    private void txtCodLinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLinFocusLost
     
      
      if (!txtCodLin.getText().equalsIgnoreCase(strCodLin))
        {
            if (txtCodLin.getText().equals(""))
            {
                txtCodLin.setText("");
                txtLin.setText("");
            }
            else
            {
                BuscarLinea("a.co_reg",txtCodLin.getText(),0);
            }
        }
        else
            txtCodLin.setText(strCodLin);
     
    }//GEN-LAST:event_txtCodLinFocusLost

    private void txtCodUniMedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodUniMedFocusLost
  
   if (!txtCodUniMed.getText().equalsIgnoreCase(strCodUni))
        {
            if (txtCodUniMed.getText().equals(""))
            {
                txtCodUniMed.setText("");
                txtUniMed.setText("");
            }
            else
            {
               BuscarUnidad("a.co_reg",txtCodUniMed.getText(),0);
            }
        }
        else
            txtCodUniMed.setText(strCodUni);
    }//GEN-LAST:event_txtCodUniMedFocusLost

    private void txtCodItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodItmActionPerformed
      //  FndItem(txtCodItm.getText(),1);
        BuscarItem("a.co_itm",txtCodItm.getText(),0);
    }//GEN-LAST:event_txtCodItmActionPerformed

    private void chkIvaVtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIvaVtaActionPerformed
        blnCmb = true;
    }//GEN-LAST:event_chkIvaVtaActionPerformed

    private void chkIvaComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIvaComActionPerformed
        blnCmb = true;
    }//GEN-LAST:event_chkIvaComActionPerformed

    private void chkEsItmSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEsItmSerActionPerformed
        blnCmb = true;
        
        chkEsItmTra.setSelected(false);
        chkEsItmOtr.setSelected(false);
        
        if (chkEsItmSer.isSelected()) {
            txtCodAlt2.setEditable(true);
        } else {
            txtCodAlt2.setEditable(false);
        }
    }//GEN-LAST:event_chkEsItmSerActionPerformed

    private void cboEstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstActionPerformed
        blnCmb = true;
    }//GEN-LAST:event_cboEstActionPerformed

    private void butCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCodActionPerformed
        //FndItem("",0);
        
        objVenConItm.setTitle("Listado de Items");         
              objVenConItm.setCampoBusqueda(1);
             // objVenConItm.cargarDatos();
              objVenConItm.show();
              
             if (objVenConItm.getSelectedButton()==objVenConItm.INT_BUT_ACE)
             {
                txtCodItm.setText(objVenConItm.getValueAt(1) );
                txtCodAlt.setText(objVenConItm.getValueAt(2) );
                txtCodAlt2.setText(objVenConItm.getValueAt(2) );
                txtNomItm.setText(objVenConItm.getValueAt(3) );
             
             } 
              
        
    }//GEN-LAST:event_butCodActionPerformed

    
      public void BuscarItem(String campo,String strBusqueda,int tipo){
         objVenConItm.setTitle("Listado de Items");         
         if (objVenConItm.buscar(campo, strBusqueda ))
        {
                txtCodItm.setText(objVenConItm.getValueAt(1) );
                txtCodAlt.setText(objVenConItm.getValueAt(2) );
                txtCodAlt2.setText(objVenConItm.getValueAt(2) );
                txtNomItm.setText(objVenConItm.getValueAt(3) );
        }
        else
        {     objVenConItm.setCampoBusqueda(tipo);
              objVenConItm.cargarDatos();
              objVenConItm.show();
             if (objVenConItm.getSelectedButton()==objVenConItm.INT_BUT_ACE)
            {
                txtCodItm.setText(objVenConItm.getValueAt(1) );
                txtCodAlt.setText(objVenConItm.getValueAt(2) );
                txtCodAlt2.setText(objVenConItm.getValueAt(2) );
                txtNomItm.setText(objVenConItm.getValueAt(3) );
             }
        }
     }
     
    
    
    private void txtCodUniMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodUniMedActionPerformed
       // FndGrupoVarios(txtCodUniMed.getText(),1,5);
         
          txtCodUniMed.transferFocus();  
    }//GEN-LAST:event_txtCodUniMedActionPerformed

    private void txtCodMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodMarActionPerformed
      // FndGrupoVarios(txtCodMar.getText(),1,4);
       
         txtCodMar.transferFocus();  
    }//GEN-LAST:event_txtCodMarActionPerformed

    private void txtCodGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodGrpActionPerformed
       // FndGrupoVarios(txtCodGrp.getText(),1,3);
        
          txtCodGrp.transferFocus();  
    }//GEN-LAST:event_txtCodGrpActionPerformed

    private void txtCodLinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLinActionPerformed
      //  FndGrupoVarios(txtCodLin.getText(),1,2);
         txtCodLin.transferFocus();  
    }//GEN-LAST:event_txtCodLinActionPerformed

    private void txtUniMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUniMedActionPerformed
      //  FndGrupoVarios(txtCodMar.getText(),1,2);
        
            txtUniMed.transferFocus();  
    }//GEN-LAST:event_txtUniMedActionPerformed

    private void butUniMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butUniMedActionPerformed
        
              objVenConUni.setTitle("Listado de Unidad de Medidas");         
              objVenConUni.setCampoBusqueda(1);
              objVenConUni.show();
              objVenConUni.cargarDatos();
             if (objVenConUni.getSelectedButton()==objVenConUni.INT_BUT_ACE)
             {
                txtCodUniMed.setText(objVenConUni.getValueAt(1) );
                txtUniMed.setText(objVenConUni.getValueAt(3) );
               
             
             } 
              
              
              
    }//GEN-LAST:event_butUniMedActionPerformed

    
       public void BuscarMarca(String campo,String strBusqueda,int tipo){
         objVenConMar.setTitle("Listado de Linea");   
        if (objVenConMar.buscar(campo, strBusqueda ))
        {
             txtCodMar.setText(objVenConMar.getValueAt(1) );
             txtMar.setText(objVenConMar.getValueAt(3) );
        }
        else
        {     objVenConMar.setCampoBusqueda(tipo);
              objVenConMar.cargarDatos();
              objVenConMar.show();
             if (objVenConMar.getSelectedButton()==objVenConMar.INT_BUT_ACE)
            {
                txtCodMar.setText(objVenConMar.getValueAt(1) );
                txtMar.setText(objVenConMar.getValueAt(3) );
             }
              else{
                    txtCodMar.setText(strCodMar);
                    txtMar.setText(strDesLarMar);
                  }
              
        }
     }
    
    
    
       public void BuscarGrupo(String campo,String strBusqueda,int tipo){
         objVenConGrp.setTitle("Listado de Linea");   
        if (objVenConGrp.buscar(campo, strBusqueda ))
        {
              txtCodGrp.setText(objVenConGrp.getValueAt(1) );
              txtGrp.setText(objVenConGrp.getValueAt(3) );
        }
        else
        {     objVenConGrp.setCampoBusqueda(tipo);
              objVenConGrp.cargarDatos();
              objVenConGrp.show();
             if (objVenConGrp.getSelectedButton()==objVenConGrp.INT_BUT_ACE)
            {
                 txtCodGrp.setText(objVenConGrp.getValueAt(1) );
                 txtGrp.setText(objVenConGrp.getValueAt(3) );
             }
              else{
                    txtCodGrp.setText(strCodGrp);
                    txtGrp.setText(strDesLarGrp);
                  }
        }
     }
    
    
    
    
    
     public void BuscarLinea(String campo,String strBusqueda,int tipo){
         objVenConLin.setTitle("Listado de Linea");   
        if (objVenConLin.buscar(campo, strBusqueda ))
        {
               txtCodLin.setText(objVenConLin.getValueAt(1) );
               txtLin.setText(objVenConLin.getValueAt(3) );
        }
        else
        {     objVenConLin.setCampoBusqueda(tipo);
              objVenConLin.cargarDatos();
              objVenConLin.show();
             if (objVenConLin.getSelectedButton()==objVenConLin.INT_BUT_ACE)
            {
                 txtCodLin.setText(objVenConLin.getValueAt(1) );
                 txtLin.setText(objVenConLin.getValueAt(3) );
             }
              else{
                    txtCodLin.setText(strCodLin);
                    txtLin.setText(strDesLarLin);
                  }
        }
     }
    
    
       public void BuscarUnidad(String campo,String strBusqueda,int tipo){
        objVenConUni.setTitle("Listado de Unidad de Medidas");   
        if (objVenConUni.buscar(campo, strBusqueda ))
        {
                txtCodUniMed.setText(objVenConUni.getValueAt(1) );
                txtUniMed.setText(objVenConUni.getValueAt(3) );
        }
        else
        {     objVenConUni.setCampoBusqueda(tipo);
              objVenConUni.cargarDatos();
              objVenConUni.show();
             if (objVenConUni.getSelectedButton()==objVenConUni.INT_BUT_ACE)
            {
                txtCodUniMed.setText(objVenConUni.getValueAt(1) );
                txtUniMed.setText(objVenConUni.getValueAt(3) );
             }
              else{
                    txtCodUniMed.setText(strCodUni);
                    txtUniMed.setText(strDesLarUni);
                  }
        }
     }
       
    
    
    
    private void txtMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMarActionPerformed
      //  FndGrupoVarios(txtMar.getText(),2,4);
        
          txtMar.transferFocus();
    }//GEN-LAST:event_txtMarActionPerformed

    private void butMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMarActionPerformed
       // FndGrupoVarios("",0,4);
        
              objVenConMar.setTitle("Listado de Linea");         
              objVenConMar.setCampoBusqueda(1);
              objVenConMar.show();
             if (objVenConMar.getSelectedButton()==objVenConMar.INT_BUT_ACE)
             {
                 txtCodMar.setText(objVenConMar.getValueAt(1) );
                 txtMar.setText(objVenConMar.getValueAt(3) );
             } 
        
        
    }//GEN-LAST:event_butMarActionPerformed

    private void txtGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGrpActionPerformed
      // FndGrupoVarios(txtGrp.getText(),2,3);
        
         txtGrp.transferFocus();
    }//GEN-LAST:event_txtGrpActionPerformed

    private void butGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGrpActionPerformed
      /// FndGrupoVarios("",0,3);
        
              objVenConGrp.setTitle("Listado de Linea");         
              objVenConGrp.setCampoBusqueda(1);
              objVenConGrp.show();
             if (objVenConGrp.getSelectedButton()==objVenConGrp.INT_BUT_ACE)
             {
                 txtCodGrp.setText(objVenConGrp.getValueAt(1) );
                 txtGrp.setText(objVenConGrp.getValueAt(3) );
             } 
        
              
    }//GEN-LAST:event_butGrpActionPerformed

    private void txtLinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLinActionPerformed
   //       FndGrupoVarios(txtLin.getText(),2,2);
      
          txtLin.transferFocus();
    }//GEN-LAST:event_txtLinActionPerformed

    private void butLinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLinActionPerformed
     //  FndGrupoVarios("",0,2);
        
        
              objVenConLin.setTitle("Listado de Linea");         
              objVenConLin.setCampoBusqueda(1);
              objVenConLin.show();
             if (objVenConLin.getSelectedButton()==objVenConLin.INT_BUT_ACE)
             {
                 txtCodLin.setText(objVenConLin.getValueAt(1) );
                 txtLin.setText(objVenConLin.getValueAt(3) );
             } 
        
    }//GEN-LAST:event_butLinActionPerformed

    /**Cerrar la aplicaciÃ³n*/
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void chkpermodnomcomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkpermodnomcomActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_chkpermodnomcomActionPerformed

    private void chkpermodnomvenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkpermodnomvenActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_chkpermodnomvenActionPerformed

    private void chkEsItmOtrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEsItmOtrActionPerformed
        // TODO add your handling code here:

        chkEsItmTra.setSelected(false);
        chkEsItmSer.setSelected(false);

        if (chkEsItmOtr.isSelected()) {
            txtCodAlt2.setEditable(true);
        } else {
            txtCodAlt2.setEditable(false);
        } 
    }//GEN-LAST:event_chkEsItmOtrActionPerformed

    private void txtCodAlt2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusGained
        // TODO add your handling code here:
        if (txtCodAlt.isEditable() || txtCodAlt.isEnabled()){
            String strTerItm = txtCodAlt.getText().trim().toUpperCase().substring(txtCodAlt.getText().trim().length()-1, txtCodAlt.getText().trim().length());
            if (strTerItm.equals("I") || strTerItm.equals("S")) {
                if(!(chkEsItmSer.isSelected() || chkEsItmTra.isSelected() || chkEsItmOtr.isSelected())){
                    txtCodAlt2.setEditable(false);
                } else {
                    txtCodAlt2.setEditable(true);
                    txtCodAlt2.setBackground(txtCodAlt.getBackground());                    
                }
            } else {
                txtCodAlt2.setEditable(true);
                txtCodAlt2.setBackground(txtCodAlt.getBackground());
            }
        }        
    }//GEN-LAST:event_txtCodAlt2FocusGained

//    private void FndGrupoVarios(String strBusqueda,int TipoBusqueda, int intTipoGrupo){
//     try{
//        Librerias.ZafConsulta.ZafConsulta objFndGrupoVarios = 
//            new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this),
//            "Codigo,Desc Corta,Desc Larga,Estado","co_reg,tx_desCor,tx_desLar,st_reg",
//            "SELECT co_reg,tx_desCor,tx_desLar,st_reg FROM tbm_var WHERE co_grp = " + intTipoGrupo ,
//            strBusqueda, 
//            objZafParSis.getStringConexion(), 
//            objZafParSis.getUsuarioBaseDatos(), 
//            objZafParSis.getClaveBaseDatos()
//            );
//  
//        objFndGrupoVarios.setTitle("Listado de Unidad de Medida");
//        switch (TipoBusqueda)
//            {
//                case 1:                                        
//                     objFndGrupoVarios.setSelectedCamBus(0);
//                    if(!objFndGrupoVarios.buscar("co_reg = " + (strBusqueda.equals("")?"0":strBusqueda)));
//                        objFndGrupoVarios.show();
//                     break;
//                case 2:                                        
//                     objFndGrupoVarios.setSelectedCamBus(2);
//                    if(!objFndGrupoVarios.buscar("tx_desLar = '" +strBusqueda+"'"));
//                        objFndGrupoVarios.show();
//                     break;
//                default:
//                    objFndGrupoVarios.show();
//                    break;             
//             }                     
//            if(!objFndGrupoVarios.GetCamSel(1).equals("")){
//                switch (intTipoGrupo){
//                    case 2:
//                        txtCodLin.setText(objFndGrupoVarios.GetCamSel(1).toString());
//                        txtLin.setText(objFndGrupoVarios.GetCamSel(3).toString());
//                        break;
//                    case 3:
//                        txtCodGrp.setText(objFndGrupoVarios.GetCamSel(1).toString());
//                        txtGrp.setText(objFndGrupoVarios.GetCamSel(3).toString());
//                        break;
//                    case 4:
//                        txtCodMar.setText(objFndGrupoVarios.GetCamSel(1).toString());
//                        txtMar.setText(objFndGrupoVarios.GetCamSel(3).toString());
//                        break;
//                    case 5:
//                        txtCodUniMed.setText(objFndGrupoVarios.GetCamSel(1).toString());
//                        txtUniMed.setText(objFndGrupoVarios.GetCamSel(3).toString());
//                        break;                        
//                }
//          }else{
//                switch (intTipoGrupo){
//                    case 2:
//                        txtCodLin.setText("");
//                        txtLin.setText("");
//                        break;
//                    case 3:
//                        txtCodGrp.setText("");
//                        txtGrp.setText("");
//                        break;
//                    case 4:
//                        txtCodMar.setText("");
//                        txtMar.setText("");
//                        break;
//                    case 5:
//                        txtCodUniMed.setText("");
//                        txtUniMed.setText("");
//                        break;                        
//                }
//          }
//        } catch(Exception Evt) {
//                objUti.mostrarMsgErr_F1(this, Evt);                    
//        }                           
//                   
//        
//    }
//    
      
//    private void FndItem (String strBusqueda,int TipoBusqueda){
//        try{
//              Librerias.ZafConsulta.ZafConsulta objFndCodItm = 
//                    new Librerias.ZafConsulta.ZafConsulta(new javax.swing.JFrame(),
//                    "Codigo,Cod Alt,Nombre,Prec Unit,Stk Act,Estado","co_itm,tx_codAlt,tx_codAlt2,tx_nomItm,nd_cosUni,nd_stkAct,st_reg",
//                    "SELECT co_itm,tx_codAlt,tx_codAlt2,tx_nomItm,nd_cosUni,nd_stkAct,st_reg FROM tbm_inv WHERE st_reg not in('T') and  co_emp = " + objZafParSis.getCodigoEmpresa(),
//                    strBusqueda, 
//                    objZafParSis.getStringConexion(), 
//                    objZafParSis.getUsuarioBaseDatos(), 
//                    objZafParSis.getClaveBaseDatos()
//                    );
//
//               objFndCodItm.setTitle("Listado de Productos");
//            switch (TipoBusqueda)
//            {
//                case 1:                                        
//                     objFndCodItm.setSelectedCamBus(0);
//                    if(!objFndCodItm.buscar("co_itm = " + (strBusqueda.equals("")?"0":strBusqueda)));
//                        objFndCodItm.show();
//                     break;
//                default:
//                    objFndCodItm.show();
//                    break;             
//             }                     
//            if(!objFndCodItm.GetCamSel(1).equals("")){            
//                txtCodItm.setText(objFndCodItm.GetCamSel(1).toString());
//                txtCodAlt.setText(objFndCodItm.GetCamSel(2).toString());
//                txtCodAlt2.setText(objFndCodItm.GetCamSel(3).toString());
//                txtNomItm.setText(objFndCodItm.GetCamSel(4).toString());
//                
//            }
//            
//        } catch(Exception Evt) {
//                objUti.mostrarMsgErr_F1(this, Evt);                    
//        }                           
//        
//    }
//    
    
   /**Cerrar la aplicaciÃ³n*/
    private void exitForm() 
    {
        dispose();
    } 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCod;
    private javax.swing.JButton butGrp;
    private javax.swing.JButton butLin;
    private javax.swing.JButton butMar;
    private javax.swing.JButton butUniMed;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboEst;
    private javax.swing.JCheckBox chkCamPreVta;
    private javax.swing.JCheckBox chkEsItmOtr;
    private javax.swing.JCheckBox chkEsItmSer;
    private javax.swing.JCheckBox chkEsItmTra;
    private javax.swing.JCheckBox chkIvaCom;
    private javax.swing.JCheckBox chkIvaVta;
    private javax.swing.JCheckBox chkOblCamNomItm;
    private javax.swing.JCheckBox chkpermodnomcom;
    private javax.swing.JCheckBox chkpermodnomven;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCodAlt;
    private javax.swing.JLabel lblCodAlt1;
    private javax.swing.JLabel lblCodClaInv;
    private javax.swing.JLabel lblCosUni;
    private javax.swing.JLabel lblCosUni1;
    private javax.swing.JLabel lblCosUni2;
    private javax.swing.JLabel lblCosUni3;
    private javax.swing.JLabel lblEst;
    private javax.swing.JLabel lblItmUni;
    private javax.swing.JLabel lblLin;
    private javax.swing.JLabel lblMar;
    private javax.swing.JLabel lblNomItm;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPeso;
    private javax.swing.JLabel lblStkAct;
    private javax.swing.JLabel lblStkMax;
    private javax.swing.JLabel lblStkMin;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblUniMed;
    private javax.swing.JLabel lblWebCli1;
    private javax.swing.JLabel lblWebCli2;
    private javax.swing.JLabel lblWebCli4;
    private javax.swing.JLabel lblWebCli5;
    private javax.swing.JLabel lblgrp;
    private javax.swing.JLabel lblusuing;
    private javax.swing.JLabel lblusuing1;
    private javax.swing.JPanel panClasi;
    private javax.swing.JPanel panDatTec;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGrpPre;
    private javax.swing.JPanel panItmSer;
    private javax.swing.JPanel panItmSer1;
    private javax.swing.JPanel panIva;
    private javax.swing.JPanel panPriInv;
    private javax.swing.JPanel panPrv;
    private javax.swing.JPanel panStk;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JSpinner spnPeso;
    private javax.swing.JTabbedPane tabGenInv;
    private javax.swing.JTable tblCla;
    private javax.swing.JTable tblPrv;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAlt2;
    private javax.swing.JTextField txtCodAltAux;
    private javax.swing.JTextField txtCodGrp;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtCodLin;
    private javax.swing.JTextField txtCodMar;
    private javax.swing.JTextField txtCodUniMed;
    private javax.swing.JTextField txtCosUni;
    private javax.swing.JTextField txtCosUni1;
    private javax.swing.JTextField txtCosUni2;
    private javax.swing.JTextField txtCosUni3;
    private javax.swing.JTextField txtGrp;
    private javax.swing.JTextField txtItmUni;
    private javax.swing.JTextField txtLin;
    private javax.swing.JTextField txtMar;
    private javax.swing.JTextField txtNomItm;
    private javax.swing.JTextField txtStkAct;
    private javax.swing.JTextField txtStkMax;
    private javax.swing.JTextField txtStkMaxAux;
    private javax.swing.JTextField txtStkMin;
    private javax.swing.JTextField txtStkMinAux;
    private javax.swing.JTextField txtUniMed;
    private javax.swing.JLabel txtfecing;
    private javax.swing.JLabel txtfecmod;
    // End of variables declaration//GEN-END:variables
 

    /**
     * Valida si el item posee stock 
     * @return 
     */

    private boolean itemSinStock(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT nd_stkAct  \n";
                strSql+=" FROM(  \n";
                strSql+="       SELECT SUM(CASE WHEN a.nd_stkAct IS NULL THEN 0 ELSE a.nd_stkAct END) as nd_stkAct \n";
                strSql+="       FROM tbm_invbod as a  \n";
                strSql+="       INNER JOIN tbm_equinv as a2 on (a2.co_emp=a.co_emp and a2.co_itm=a.co_itm)  \n";
                strSql+="       INNER JOIN tbm_inv AS inv ON(inv.co_emp=a.co_emp AND inv.co_itm=a.co_itm) \n";
                strSql+="       WHERE a2.co_itmmae= ( SELECT a2.co_itmMae  \n";
                strSql+="                             FROM tbm_inv as a1   \n";
                strSql+="                             INNER JOIN tbm_equinv as a2 ON (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm) \n";
                strSql+="                             WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" and a1.co_itm="+txtCodItm.getText()+"  \n";
                strSql+="                           )    \n";
                strSql+=" ) AS x   \n";
                strSql+=" WHERE nd_stkAct > 0  \n";
                System.out.println("itemSinStock: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    //if(rstLoc.getDouble("nd_stkAct")==0){
                        blnRes=false;
                    //}
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }catch(Exception evt){
            objUti.mostrarMsgErr_F1(this, evt);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    private boolean cambiarEstadoItemTodasLasEmpresas(java.sql.Connection conExt,int CodEmp, int CodItm,String EstReg ){
        boolean blnRes=false;
        String strFecUltMod;
        java.sql.Statement stmLoc, stmIns;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
                stmLoc=conExt.createStatement();stmIns=conExt.createStatement();
                strSql= "";
                strSql+=" SELECT a2.co_itmMae, a2.co_emp,a2.co_itm   \n";
                strSql+=" FROM tbm_equinv as a2  \n";
                strSql+=" INNER JOIN tbm_inv AS inv ON(inv.co_emp=a2.co_emp AND inv.co_itm=a2.co_itm )     \n";
                strSql+=" WHERE a2.co_itmmae= ( SELECT a2.co_itmMae \n";
                strSql+="                       FROM tbm_inv as a1  \n";
                strSql+="                       INNER JOIN tbm_equinv as a2 ON (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm) \n";
                strSql+="                       WHERE a1.co_emp="+CodEmp+" and a1.co_itm="+CodItm+" \n";
                strSql+=" ) ";
                rstLoc = stmLoc.executeQuery(strSql) ;
                while(rstLoc.next()){
                    strSql = " UPDATE tbm_inv SET st_reg= '"+EstReg+"',co_usrMod="+objZafParSis.getCodigoUsuario()+", fe_ultMod='"+strFecUltMod+"'";
                    strSql+= " WHERE co_emp ="+rstLoc.getInt("co_emp")+" AND co_itm="+rstLoc.getInt("co_itm")+" ;";    
                    stmIns.executeUpdate(strSql);
                }
                objHisTblBasDat.insertarHistoricoMasivo(conExt, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objZafParSis.getCodigoUsuario());
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                stmIns.close();
                stmIns=null;
                blnRes=true;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }catch(Exception evt){
            objUti.mostrarMsgErr_F1(this, evt);
            blnRes=false;
        }
        return blnRes;
    }


}
    




