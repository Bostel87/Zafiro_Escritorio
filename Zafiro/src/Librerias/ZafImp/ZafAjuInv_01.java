/*
 * ZafMae06.java    chkEsItmSer
 * Esta clase sirve para almacenar los registros de inventario  System.out
 * Created on 11 de octubre de 2004, 9:20   tbm_invmae
 */  

package Librerias.ZafImp;
  
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafParSis.ZafParSis;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import Librerias.ZafVenCon.ZafVenCon;  //**************************
import java.util.ArrayList;  //*******************
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import java.sql.DriverManager;
import java.util.Random;

/**
 * @author  ilino
 */

public class ZafAjuInv_01 extends javax.swing.JDialog {
    private Connection conCreItm;       //Variable para conexión a la Base de Datos
    private Librerias.ZafInventario.ZafInventario objInventario;
    private String strSql;        //Variable de tipo cadena en la cual se almacenará el Query 
    private String strMsg;        //Variable de tipo cadena para enviar los mensajes por pantalla
    private String strAux;        //Variable auxiliar de tipo string la cual servira para guardar aquellas cadenas en las cuales me esten enviando algun caracter invalido
    private String strPat;        //Patrón
    boolean blnCmb = false; //Detecta si se realizaron cambios en el documento dentro de las caja de texto
    boolean blnCln = false; //Si los TextField estan vacios
    final String strTit = "Sistema Zafiro"; //Constante del titulo del mensaje
    private ZafUtil objUti;       //Objeto del tipo de la clase ZafUtil, el cual me va a permitir manipular los diferentes metodos de esta clase
    private ZafParSis objZafParSis;  //Objeto que me permitira obtener los parametros del sistema, como podria ser la ruta de la base de datos, etc.  
    private JOptionPane oppMsg;   //Objeto de tipo OptionPane para presentar Mensajes
    private LisTxt objLisCmb;     //Instancia de clase que detecta cambios
    private java.awt.Component jfrThis;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblModCla, objTblModPrv;
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButCla, objTblCelRenButPrv;
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk  objTblCelRenChkP,objTblCelRenChkA, objTblCelRenChkI, objTblRenChkClaA, objTblRenChkClaI;        //Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk  objTblCelEdiChkP,objTblCelEdiChkA, objTblCelEdiChkI, objTblEdiChkClaA, objTblEdiChkClaI;        //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoPrv;
    private ZafVenCon objVenConItm; //*****************  
    private ZafVenCon objVenConUni; //*****************  
    private ZafVenCon objVenConLin; //*****************  
    private ZafVenCon objVenConGrp; //*****************  
    private ZafVenCon objVenConMar; //*****************  
    private ZafVenCon objVenConPrv; //*****************
    private ZafVenCon objVenConCla; //*****************
         
    private String
        strCodLin="", strDesLarLin="",
        strCodGrp="", strDesLarGrp="",
        strCodMar="", strDesLarMar="", 
        strCodUni="", strDesLarUni="";   
    
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
    
    private int intButPre;
    
    private ArrayList arlRegItm, arlDatItm;
    private final int INT_ARL_ITM_COD_EMP=0;
    private final int INT_ARL_ITM_COD_ITM_GRP=1;
    private final int INT_ARL_ITM_COD_ITM_EMP=2;
    private final int INT_ARL_ITM_COD_ITM_MAE=3;
    private final int INT_ARL_ITM_COD_ALT_ITM=4;
    private final int INT_ARL_ITM_COD_ITM_LET=5;
    private final int INT_ARL_ITM_NOM_ITM=6;
    private final int INT_ARL_ITM_UNI_MED=7;
    
    
    private String strSQLInsItm;
    
    

    /** Creates new form ZafAjuInv_01 */
    public ZafAjuInv_01(java.awt.Frame parent, ZafParSis obj, Connection conexion) {
        super(parent, true);
        initComponents();
        addListenerCambios();
        intButPre=0;//no se ha seleccionado el boton
        strSQLInsItm="";
        try{
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jfrThis    = parent;
            lblTit.setText(objZafParSis.getNombreMenu() );            
            objInventario= new Librerias.ZafInventario.ZafInventario(this, objZafParSis ); 
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
            //deshabilitarDatInv();
            txtNomItm.setBackground(objZafParSis.getColorCamposObligatorios());
            txtCodItm.setBackground(objZafParSis.getColorCamposSistema());
            txtCosUni.setEnabled(false);
            txtCodItm.setEnabled(false);
            txtStkAct.setEnabled(false);

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

            strPat = "dd/MM/yyyy";
            SimpleDateFormat sdfFec = new SimpleDateFormat (strPat);
            Date datFec = new Date();
            txtCosUni.setEnabled(false);
            txtCosUni1.setEnabled(false);
            txtCosUni2.setEnabled(false);
            txtCosUni3.setEnabled(false);
            this.setBounds(10,10, 700, 450);
            isAntCarFrm();
            arlDatItm=new ArrayList();
            conCreItm=conexion;
            Configura_ventana_consulta();
        }
        catch (CloneNotSupportedException e){
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

        }
        catch(Exception e) {
            objUti.mostrarMsgErr_F1(this, e); 
        }
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

        }
        catch(Exception e) {
            objUti.mostrarMsgErr_F1(this, e); 
        }
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
        public void butCLick(){
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




    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.OK_OPTION);
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
 
    public boolean isFrmClean(){
        if ((txtCodAlt.getText().equals("")) &&  (txtCodAlt2.getText().equals("")) && (txtNomItm.getText().equals("")) && (txtCodLin.getText().equals("")) && (txtCodMar.getText().equals("")) && (txtCodGrp.getText().equals("")) && (txtCodUniMed.getText().equals("")) && (txtItmUni.getText().equals("")) && (txtCosUni1.getText().equals("")) && (txtCosUni2.getText().equals("")) && (txtCosUni3.getText().equals("")) && (txtStkMin.getText().equals("")) && (txtStkMax.getText().equals("")) && (txaObs1.getText().equals("")) && (txaObs2.getText().equals(""))){    
            blnCln=false;
        }
        else {
            blnCln = true;
        }
        return blnCln;
    }

    private boolean insertarDatReg(){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        int intCodEqu=0;
        boolean blnCodAlt = false;
        try{
            if(conCreItm!=null){
                stmLoc=conCreItm.createStatement();
                intCodEqu=getCodItemMaestro(conCreItm);
                if(validadCodAlt(conCreItm, txtCodAlt.getText() ,"tx_codAlt" )){
                    String strTerItm = txtCodAlt.getText().trim().toUpperCase().substring(txtCodAlt.getText().trim().length()-1, txtCodAlt.getText().trim().length());
                    if (strTerItm.equals("I") || strTerItm.equals("S")) {
                        if(!(chkEsItmSer.isSelected() || chkEsItmTra.isSelected() || chkEsItmOtr.isSelected())){
                            while (!blnCodAlt) {
                                txtCodAlt2.setText(randomString("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 3));
                                blnCodAlt = validadCodAlt(conCreItm, txtCodAlt2.getText(), "tx_codAlt2" );                
                            }                
                        } 
                        else{
                            blnCodAlt=validadCodAlt(conCreItm, txtCodAlt2.getText() ,"tx_codAlt2" );
                        }
                    } 
                    else{
                        blnCodAlt=validadCodAlt(conCreItm, txtCodAlt2.getText() ,"tx_codAlt2" );
                    }

                    if(blnCodAlt){
                        if(intCodEqu!=0){
                            if(_CrearItemEmpresas(conCreItm, intCodEqu )){
                                if(_creaInvMovEmpGrp(conCreItm, intCodEqu )){
                                    cboEst.setSelectedIndex(0);
                                    txtStkAct.setText("0");
                                    txtCodItm.setEditable(false);
                                    butCod.setEnabled(false);
                                    chkIvaCom.setSelected(true);
                                    chkIvaVta.setSelected(true);
                                    txtCodAlt.requestFocus();
                                    blnRes=true;
                                }
                            }
                        }
                        else
                            MensajeInf("Problemas al crear codigo de item consolidado");  
                    }
                    else
                        valTxt(txtCodAlt, txtCodAlt, "Codigo alterno 2", "<<Codigo alterno 2>> ya existe", 3, "el", 0);  
                }
                else
                    valTxt(txtCodAlt, txtCodAlt, "Codigo alterno", "<<Codigo alterno>> ya existe", 3, "el", 0);
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
                    }
                }
            }

           if(!strSql.equals(""))
               stmLoc.executeUpdate(strSql);//strSQLInsItm+="" + strSql;//stmLoc.executeUpdate(strSql);

           stmLoc.close();
           stmLoc=null;
           blnRes=true;
        }
        catch(java.sql.SQLException Evt){
            blnRes=false;   
            MensajeInf("Error. Al Insertar Proveedores posible duplicidad de datos.\nVerifique los datos he intente nuevamente."); 
        }
        catch(Exception Evt){ 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, Evt); 
        }
        return blnRes;
    }

    private boolean _CrearItemEmpresas(java.sql.Connection conn,int intCodEqu){
        boolean blnRes=false;
        java.sql.Statement stmInv,stmEmp;
        java.sql.ResultSet rstLoc,rstEmp;
        int intCodItm=0;
        int intControl=0;
        String chrItmSer="N", chrIvaCom="", chrIvaVta="", chrEst="", strAuxCodAlt="", strCamPreVta="";
        arlDatItm.clear();
        
        try{
            stmEmp = conn.createStatement();
            stmInv = conn.createStatement();

            if(chkEsItmSer.isSelected()) 
                chrItmSer="S";
            if(chkEsItmTra.isSelected()) 
                chrItmSer="T";
            if(chkEsItmOtr.isSelected()) 
                chrItmSer="O";

            if(chkIvaCom.isSelected())  
                chrIvaCom="S";
            else  
                chrIvaCom="N";

            if(chkIvaVta.isSelected()) 
                chrIvaVta="S";
            else  
                chrIvaVta="N";

            if(cboEst.getSelectedIndex()==0) 
                chrEst="A";
            else  
                chrEst="I";

            if(chkCamPreVta.isSelected()) 
                strCamPreVta="S";

            strSql="SELECT co_emp FROM tbm_emp ORDER BY co_emp";
            rstEmp=stmEmp.executeQuery(strSql);
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
                " st_permodnomitmcom, st_blqPreVta ) "+
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
                "  '"+chrEst+"', "+objZafParSis.getCodigoUsuario()+", "+objZafParSis.getCodigoUsuario()+", "+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getFuncionFechaHoraBaseDatos()+",  "+
                "  "+spnPeso.getModel().getValue().toString()+", '"+(chkpermodnomven.isSelected()?"S":"N")+"', '"+(chkpermodnomcom.isSelected()?"S":"N")+"', "+(strCamPreVta.equals("")?null:"'"+strCamPreVta+"'")+" ) ";
                stmInv.executeUpdate(strSql);//strSQLInsItm+=";" + strSql;//stmInv.executeUpdate(strSql);

                strSql="INSERT INTO tbm_equinv (co_itmmae, co_emp, co_itm) values ("+intCodEqu +", "+rstEmp.getInt("co_emp") +","+intCodItm+")";
                stmInv.executeUpdate(strSql);//strSQLInsItm+=";" + strSql;//stmInv.executeUpdate(strSql);
                
                //Arreglo para datos de programa de Ajuste
                arlRegItm=new ArrayList();
                arlRegItm.add(INT_ARL_ITM_COD_EMP,      "" + rstEmp.getInt("co_emp"));
                arlRegItm.add(INT_ARL_ITM_COD_ITM_GRP,  "" + intCodItm);
                arlRegItm.add(INT_ARL_ITM_COD_ITM_EMP,  "" + intCodItm);
                arlRegItm.add(INT_ARL_ITM_COD_ITM_MAE,  "" + intCodEqu);
                arlRegItm.add(INT_ARL_ITM_COD_ALT_ITM,  "" + ((strAux=txtCodAlt.getText().replaceAll("'", "''")).equals("")?"Null":""+strAux.toUpperCase()+""));
                arlRegItm.add(INT_ARL_ITM_COD_ITM_LET,  "" + ((strAux=txtCodAlt2.getText().replaceAll("'", "''")).equals("")?""+strAuxCodAlt.toUpperCase()+"":""+strAux.toUpperCase()+""));
                arlRegItm.add(INT_ARL_ITM_NOM_ITM,      "" + ((strAux=txtNomItm.getText().replaceAll("'", "''")).equals("")?"Null":""+strAux+""));
                arlRegItm.add(INT_ARL_ITM_UNI_MED,      "" + ((strAux=txtCodUniMed.getText().replaceAll("'", "''")).equals("")?"Null":""+strAux+""));
                arlDatItm.add(arlRegItm);

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
            rstEmp.close();
            rstEmp=null;

            stmInv.close();
            stmInv=null;
            stmEmp.close();
            stmEmp=null;
            if(intControl==0) 
                blnRes=true;

        }
        catch(SQLException evt){
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, evt); 
        }
        catch(Exception evt){
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, evt); 
        }
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
            stmLoc.executeUpdate(strSql);//strSQLInsItm+=";" + strSql;//stmLoc.executeUpdate(strSql);
            stmLoc.close();
            stmLoc=null;
            blnRes=true;

        }
        catch(java.sql.SQLException Evt){
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, Evt); 
        }
        catch(Exception Evt){ 
            blnRes=false;  
        objUti.mostrarMsgErr_F1(this, Evt); 
        }
        return blnRes;
    }

    public boolean _creaInvMovEmpGrp(java.sql.Connection conn, int intCodEqu){
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
                    stmLoc02.executeUpdate(strSql);//strSQLInsItm+=";" + strSql;//stmLoc02.executeUpdate(strSql);
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

         }
        catch(java.sql.SQLException Evt){
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, Evt); 
        }
        catch(Exception Evt){ 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, Evt); 
        }
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
        for(int i = 0; i < len; i++ ) {
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
                    }
                }
            }
            if(!strSql.equals(""))
                stmLoc.executeUpdate(strSql);//strSQLInsItm+=";" + strSql;//stmLoc.executeUpdate(strSql);

            stmLoc.close();
            stmLoc=null;

        }
        catch(java.sql.SQLException Evt){
            blnRes=false;  
            MensajeInf("Error. Al Insertar Clasificaciones posible duplicidad de datos.\nVerifique los datos he intente nuevamente."); 
        }
        catch(Exception Evt){ 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, Evt); 
        }
        return blnRes;
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

           }
        }
        catch (java.sql.SQLException e){  
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); 
        }
        catch(Exception e){ 
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); 
        }
        return intRes;
    }

    
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
    


    
    
    /**Procedimiento que limpia todas las cajas de texto que existen en el frame*/
    public void limpiarDatInv(){ 
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
        txaObs1.setText("");
        txaObs2.setText(""); 
        txtfecing.setText("");
        txtfecmod.setText("");
        lblusuing.setText("");
        lblusuing1.setText("");

        spnPeso.setValue(new Double(0.00));



        strCodLin="";
        strDesLarLin="";
        strCodGrp="";
        strDesLarGrp="";
        strCodMar="";
        strDesLarMar=""; 
        strCodUni="";
        strDesLarUni="";  
    }
        
    
    /**Procedimiento que deshabilita las cajas de texto para que estas no puedan ser 
    manipuladas por el usuario*/
//    public void deshabilitarDatInv(){
//       txtCodItm.setEditable(false);
//       txtCodAlt.setEnabled(false);
//       txtNomItm.setEditable(false);
//       txtItmUni.setEnabled(false);
//       txtCosUni1.setEnabled(false);
//       txtCosUni2.setEnabled(false);
//       txtCosUni3.setEnabled(false);
//       txtStkMin.setEnabled(false);
//       txtStkMax.setEnabled(false);
//       txtCodLin.setEnabled(false);
//       txtLin.setEnabled(false);
//       txtCodGrp.setEnabled(false);
//       txtGrp.setEnabled(false);
//       txtCodMar.setEnabled(false);
//       txtMar.setEnabled(false);
//       txtCodUniMed.setEnabled(false);
//       txtUniMed.setEnabled(false);
//       chkEsItmSer.setEnabled(false);
//       chkEsItmOtr.setEnabled(false);
//       chkIvaCom.setEnabled(false);
//       chkIvaVta.setEnabled(false);
//       txaObs1.setEditable(false);
//       txaObs2.setEditable(false);
//       cboEst.setEnabled(false);
//       butGrp.setEnabled(false);
//       butLin.setEnabled(false);
//       butMar.setEnabled(false);
//       butUniMed.setEnabled(false);
//       butCod.setEnabled(false);
//    }
    
    /**<PRE>
     *Función qué permitirá validar que contenido de las cajas de texto según la validación que se desee
     *realizar. 
     *Esta función recibirá los siquientes parámetros:
     *
     *jtfTxt: Objeto de tipo JTextField
     *jtfTxtAux: Algún objeto adicional de tipo JtextField
     *strCam: El campo al qué estamos haciendo referencia.
     *strMsj: Si se envía como parámetro 3 en intOpc en está variable se deberá enviar el Mensaje 
     *        de validación y el campo, pero si se envia 1 o 2 se la envía vacía. 
     *        (Ejm: "El campo <<X>> ya existe")
     *intOpc: se enviará como parámetro 1 si es una validación de números y 2 si es de límite.
     *strMsjGen: El generó del campo (Ejm: "el")
     *intTab: Variable de tipo entero que recibirá el índice de JTabbedPane al que va a hacer referencia
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
        chkCamPreVta = new javax.swing.JCheckBox();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
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
        lblPeso.setBounds(360, 210, 60, 15);

        spnPeso.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));
        panPriInv.add(spnPeso);
        spnPeso.setBounds(400, 210, 80, 20);

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

        panPriInv.add(panItmSer1);
        panItmSer1.setBounds(380, 110, 280, 90);

        chkCamPreVta.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkCamPreVta.setText("Bloquear cambio de precio ventas.");
        panPriInv.add(chkCamPreVta);
        chkCamPreVta.setBounds(470, 80, 210, 23);

        butGua.setText("Crear Item");
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panPriInv.add(butGua);
        butGua.setBounds(486, 310, 90, 26);

        butCer.setText("Cerrar");
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panPriInv.add(butCer);
        butCer.setBounds(580, 310, 90, 26);

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

        setSize(new java.awt.Dimension(700, 450));
        setLocationRelativeTo(null);
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

      
      

    private void txtItmUniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItmUniActionPerformed
        cboEst.requestFocus();
    }//GEN-LAST:event_txtItmUniActionPerformed

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtCodLin.requestFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtNomItm.requestFocus();
        
        
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void txtUniMedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUniMedFocusLost
         
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

    private void txtCosUni1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCosUni1ActionPerformed
        txtCosUni2.requestFocus();
    }//GEN-LAST:event_txtCosUni1ActionPerformed

    private void txtCosUni1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCosUni1FocusLost

    }//GEN-LAST:event_txtCosUni1FocusLost

    private void txtCosUni2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCosUni2ActionPerformed
        txtCosUni3.requestFocus();
    }//GEN-LAST:event_txtCosUni2ActionPerformed

    private void txtCosUni2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCosUni2FocusLost

    }//GEN-LAST:event_txtCosUni2FocusLost

    private void txtCosUni3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCosUni3ActionPerformed
        txtStkMin.requestFocus();
    }//GEN-LAST:event_txtCosUni3ActionPerformed

    private void txtCosUni3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCosUni3FocusLost

    }//GEN-LAST:event_txtCosUni3FocusLost

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

    private void txtStkMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStkMinActionPerformed
        txtStkMax.requestFocus();
    }//GEN-LAST:event_txtStkMinActionPerformed

    private void txtStkMinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStkMinFocusLost

    }//GEN-LAST:event_txtStkMinFocusLost

    private void txtStkMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStkMaxActionPerformed
        txaObs1.requestFocus();
    }//GEN-LAST:event_txtStkMaxActionPerformed

    private void txtStkMaxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStkMaxFocusLost

    }//GEN-LAST:event_txtStkMaxFocusLost

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        // TODO add your handling code here:
        intButPre=1;//se presiono guardar
        if(!isInsItm())
            mostrarMsgInf("<HTML>La información no se pudo guardar. </HTML>Verifique y vuelva a intentarlo.</HTML>");
        else
            dispose();
    }//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        // TODO add your handling code here:
        intButPre=2;
        arlDatItm.clear();
        strSQLInsItm="";
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        // TODO add your handling code here:
        // TODO add your handling code here:
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            intButPre=2;//se presiono la X de cerrar formulario
            dispose();
        }
    }//GEN-LAST:event_exitForm


    
   /**Cerrar la aplicación*/
    private void exitForm() 
    {
        dispose();
    } 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCod;
    private javax.swing.JButton butGrp;
    private javax.swing.JButton butGua;
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
 
    
    private boolean isInsItm(){
        boolean blnRes=true;
        try{
            if (!insertarReg())
                return false;
            isFrmClean();
            blnCmb=false;
            return true;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jfrThis, e);
        }
        return blnRes;        
    }
    
    
    private boolean insertarReg() {
        boolean blnRes=false;
        isFrmClean();
        if(blnCln){
            if(intButPre==1){//se presiono guardar
                if(isCamValToo()){
                    if(insertarDatReg()){   
                        blnRes=true;
                    }
                }
            }
            else{
                blnRes=true;
                mostrarMsgInf("<HTML>El item no se creó.<BR>Verifique y vuelva a intentarlo.</HTML>");
            }

        }
        else{
           strMsg="No se han realizado cambios en el formulario";
           oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        }
        return blnRes;
    }
    
    

    private boolean isCamValToo(){
        boolean blnRes=true;
        try{
           if(txtNomItm.getText().equals("")) {
              strMsg = "El campo <<Nombre del Item>> es obligatorio.\nEscriba el Nombre del Item y vuelva a intentarlo.";
              oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
              tabGenInv.setSelectedIndex(0);
              txtNomItm.requestFocus();
              blnRes=false;
           }
           else if(!chkEsItmSer.isSelected()){
                if(!chkIvaCom.isSelected()&&!chkIvaVta.isSelected()){
                      strMsg = "El campo  <<IVA>> es obligatorio.\nSeleccione una clase de IVA y vuelva a intentarlo.";
                      oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                      tabGenInv.setSelectedIndex(0);
                      chkIvaCom.requestFocus();
                       blnRes=false;
                }
            }
            else if(txtItmUni.getText().length()>6){
                  valTxt(txtItmUni, txtItmUni, "Item(s) por Unidad", "", 2, "el", 0);
                   blnRes=false;
            }
            else if(!txtStkMin.getText().equals("") && !txtStkMin.getText().equals("")){
               if (Integer.parseInt(txtStkMin.getText())>=Integer.parseInt(txtStkMax.getText())) {
                  valTxt(txtStkMin, txtStkMax, "Stock Mínimo / Stock Máximo", "<<Stock Mínimo>> es mayor o igual al <<Stock Máximo>>", 3, "el", 1);
                  blnRes=false;
               }
            }
        }
        catch(Exception e){
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, e); 
        }
        return blnRes;
    }
    
    
    private boolean isAntCarFrm(){
        boolean blnRes=true;
        try{
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
        catch(Exception e){
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, e); 
        }
       return blnRes; 
    }
    
    
    /**Procedimiento que me permÃ­te habilitar las cajas de texto para que estas puedan 
    ser manipuladas por el usuario*/
    public void habilitarDatInv(){                                
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
    
    public int getBotonPresionado() {
        return intButPre;
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

    public ArrayList getDatItm() {
        return arlDatItm;
    }

    public String getSQLInsItm() {
        return strSQLInsItm;
    }
    
    
    
    
    
}