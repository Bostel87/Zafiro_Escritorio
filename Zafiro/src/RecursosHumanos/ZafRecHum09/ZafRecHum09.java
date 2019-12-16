/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RecursosHumanos.ZafRecHum09;

import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenDep;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenEmp;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenTra;
import Librerias.ZafRecHum.ZafVenFun.ZafVenFun;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Maestros.ZafMae07.ZafMae07_01;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * Listado de Marcaciones de Empleados
 *
 * @author Roberto Flores Guayaquil 22/08/2012
 */
public class ZafRecHum09 extends javax.swing.JInternalFrame {

    private Librerias.ZafParSis.ZafParSis objZafParSis;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                                            //Editor: Editor del JTable.
    private ZafUtil objUti;
    private ZafTblCelEdiChk zafTblCelEdiChkLab;                             //Editor de Check Box para campo Laborable
    private ZafTblCelRenChk zafTblCelRenChkLab;                             //Renderer de Check Box para campo Laborable
    private ZafTblCelRenLbl objTblCelRenLbl;                                //Render: Presentar JLabel en JTable.
    private ZafVenTra zafVenTra;                                            //Ventana de consulta de Empleados
    private ZafVenDep zafVenDep;                                            //Ventana de consulta de Departamentos
    private ZafVenEmp zafVenEmp;                                            //Ventana de consulta de Empresas
    private ZafThreadGUI objThrGUI;
    private ZafSelFec objSelFec;
    private ZafMouMotAda objMouMotAda;                                      //ToolTipText en TableHeader.
    private ZafThreadGUIRpt objThrGUIRpt;
    private ZafRptSis objRptSis;
    private ZafTblPopMnu objTblPopMnu;                         //PopupMenu: Establecer PeopuMenú en JTable.
    private static final int INT_TBL_DAT_NUM_TOT_CDI = 16;                   //Número total de columnas dinámicas.
    private static final int INT_TBL_LINEA = 0;                            //Índice de columna Linea.
    private static final int INT_TBL_FECHA = 1;                            //Índice de columna Día.
    private static final int INT_TBL_CODTRA = 2;
    private static final int INT_TBL_NOMTRA = 3;
    private static final int INT_TBL_MARENT = 4;
    private static final int INT_TBL_MARSAL = 5;
    private static final int INT_TBL_TIEATR = 6;
    private static final int INT_TBL_TIEADE = 7;
    private static final int INT_TBL_CHKFAL = 8;
    private static final int INT_TBL_CHKJUSATR = 9;
//  private static final int INT_TBL_CHKDESATR=10;
    private static final int INT_TBL_OBSATR = 10;                           //Índice de columna Observación de la Justificación del atraso.
    private static final int INT_TBL_BUTOBSATR = 11;                       //Índice de columna para ingresar la observación del atraso.
     private static final int INT_TBL_CHKJUSPER=12;
    private static final int INT_TBL_CHKJUSFAL = 13;
//  private static final int INT_TBL_CHKDESFAL=14;
    private static final int INT_TBL_OBSFAL = 14;                          //Índice de columna Observación de la Justificación de la falta.
    private static final int INT_TBL_BUTOBSFAL = 15;                       //Índice de columna para ingresar la observación de la falta.
//  private static final int INT_TBL_HORFUEOFI = 16;                       //Índice de columna para ingresar la observación de la falta.
    private static final int INT_TBL_CHKJUSSALADE = 16;
    private static final int INT_TBL_OBSSALADE = 17;                           //Índice de columna Observación de la Justificación del atraso.
    private static final int INT_TBL_BUTOBSSALADE = 18;                       //Índice de columna para ingresar la observación del atraso.
    private static final int INT_TBL_CHKJUSMARINC = 19;
    private static final int INT_TBL_OBSMARINC = 20;                           //Índice de columna Observación de la Justificación del atraso.
    private static final int INT_TBL_BUTOBSMARINC = 21;                       //Índice de columna para ingresar la observación del atraso.
    private String strVersion = " v1.5 ";
    private Vector vecCab = new Vector();                                  //Vector que contiene la cabecera del Table.
    private int intCodEmp;                                                 //Código de la empresa.
    private boolean blnMod;                                                //Indica si han habido cambios en el documento
    private boolean blnConsDat;
    private ZafTblOrd objTblOrd;
    private ZafTblBus objTblBus;
    private ZafTblTot objTblTot;                        //JTable de totales.
    private String strCodEmp, strNomEmp;
    private String strCodDep = "";
    private String strDesLarDep = "";
    private String strCodTra = "";
    private String strNomTra = "";
    private String strFecDes = "";
    private String strFecHas = "";
    private ZafVenCon vcoEmp;                                   //Ventana de consulta.
    private ZafVenCon vcoDep;                                   //Ventana de consulta.
    private ZafVenCon vcoTra;
    private ZafPerUsr objPerUsr;
    private boolean blnEstCarMarcEmp = false;

    /**
     * Creates new form ZafRecHum14
     */
    public ZafRecHum09(Librerias.ZafParSis.ZafParSis obj) {

        try {

            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            objPerUsr = new ZafPerUsr(objZafParSis);


//            if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
//                txtCodEmp.setEnabled(true);
//                txtNomEmp.setEnabled(true);
//                butEmp.setEnabled(true);
//            }


            this.setTitle(objZafParSis.getNombreMenu() + "  " + strVersion + " ");
            lblTit.setText(objZafParSis.getNombreMenu());

            objUti = new ZafUtil();

            objRptSis = new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

            configuraTbl();
            agregarColTblDat();
            tblDat.getModel().addTableModelListener(new MyTableModelListener(tblDat));

            //Configurar las ZafVenCon.
            configurarVenConDep();
            configurarVenConTra();
            configurarVenConEmp();

            //Configurar ZafSelFec:
            objSelFec = new ZafSelFec();
            objSelFec.setTitulo("Rango de fechas");
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setCheckBoxChecked(false);
            panCab.add(objSelFec);
            objSelFec.setBounds(4, 20, 472, 72);

            //*****************************************************************************
            Librerias.ZafDate.ZafDatePicker txtFecDoc;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setHoy();
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
            int fecDoc[] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if (fecDoc != null) {
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(java.util.Calendar.DATE, -30);

            dtePckPag.setAnio(objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes(objFecPagActual.get(java.util.Calendar.MONTH) + 1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(), "dd/MM/yyyy", "yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha, "yyyy/MM/dd");

            objSelFec.setFechaDesde(objUti.formatearFecha(fe1, "dd/MM/yyyy"));
            //*******************************************************************************

            blnMod = false;
            blnConsDat = false;

        } catch (Exception e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }  /**/
    }

    /**
     * Creates new form ZafRecHum27
     */
    public ZafRecHum09(Librerias.ZafParSis.ZafParSis obj, String strCoEmp, String strCoTra, String strFecDes, String strFecHas, int intOp) {
        try {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            objPerUsr = new ZafPerUsr(objZafParSis);


            this.setTitle(objZafParSis.getNombreMenu() + "  " + strVersion + " ");
            lblTit.setText(objZafParSis.getNombreMenu());

            objUti = new ZafUtil();

            objRptSis = new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

            configuraTbl();
            agregarColTblDat();
            tblDat.getModel().addTableModelListener(new MyTableModelListener(tblDat));

            //Configurar las ZafVenCon.
            configurarVenConDep();
            configurarVenConTra();
            configurarVenConEmp();

            //Configurar ZafSelFec:
            objSelFec = new ZafSelFec();
            objSelFec.setTitulo("Rango de fechas");
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setCheckBoxChecked(false);
            panCab.add(objSelFec);
            objSelFec.setBounds(4, 20, 472, 72);

            objSelFec.setFechaDesde(strFecDes);
            objSelFec.setFechaHasta(strFecHas);

            this.strFecDes = strFecDes;
            this.strFecHas = strFecHas;

            //*****************************************************************************
            Librerias.ZafDate.ZafDatePicker txtFecDoc;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setHoy();
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
            int fecDoc[] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if (fecDoc != null) {
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(java.util.Calendar.DATE, -30);

            dtePckPag.setAnio(objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes(objFecPagActual.get(java.util.Calendar.MONTH) + 1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(), "dd/MM/yyyy", "yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha, "yyyy/MM/dd");

            objSelFec.setFechaDesde(objUti.formatearFecha(fe1, "dd/MM/yyyy"));
            //*******************************************************************************

            butVisPre.setVisible(false);
            butCon.setVisible(false);
            butImp.setVisible(false);

            blnMod = false;
            blnConsDat = false;
            blnEstCarMarcEmp = true;

            if (blnEstCarMarcEmp) {
                this.strCodEmp = strCoEmp;
                this.strCodTra = strCoTra;
                //vecDat=null; 
                //cargarDatos();
                if (objThrGUI == null) {
                    objThrGUI = new ZafThreadGUI(intOp);
                    objThrGUI.start();
                }
            }


        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    public class MyTableModelListener implements TableModelListener {

        JTable table;

        // It is necessary to keep the table since it is not possible
        // to determine the table from the event's source
        MyTableModelListener(JTable table) {
            this.table = table;
        }

        public void tableChanged(TableModelEvent e) {
            if (!blnConsDat) {
                switch (e.getType()) {
                    case TableModelEvent.UPDATE:
                        blnMod = true;
                        break;
                }
            }
            blnConsDat = false;
        }
    }

    private boolean configuraTbl() {

        boolean blnRes = false;

        try {

            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_FECHA, "Fecha");
            vecCab.add(INT_TBL_CODTRA, "Código");
            vecCab.add(INT_TBL_NOMTRA, "Empleado");//vecCab.add(INT_TBL_APETRA,"Apellidos");
            vecCab.add(INT_TBL_MARENT, "Entrada");
            vecCab.add(INT_TBL_MARSAL, "Salida");
            vecCab.add(INT_TBL_TIEATR, "Atrasos");
            vecCab.add(INT_TBL_TIEADE, "Adelantos");
            vecCab.add(INT_TBL_CHKFAL, "Faltas");
            vecCab.add(INT_TBL_CHKJUSATR, "Justificar");
//        vecCab.add(INT_TBL_CHKDESATR, "Descontar");
            vecCab.add(INT_TBL_OBSATR, "Observación");
            vecCab.add(INT_TBL_BUTOBSATR, "...");
            vecCab.add(INT_TBL_CHKJUSPER,"Permiso");
            vecCab.add(INT_TBL_CHKJUSFAL, "Justificar");
//        vecCab.add(INT_TBL_CHKDESFAL, "Descontar");
            vecCab.add(INT_TBL_OBSFAL, "Observación");
            vecCab.add(INT_TBL_BUTOBSFAL, "...");
//        vecCab.add(INT_TBL_HORFUEOFI,"Hor.Fue.Ofi.");

            vecCab.add(INT_TBL_CHKJUSSALADE, "Justificar");
            vecCab.add(INT_TBL_OBSSALADE, "Observación");
            vecCab.add(INT_TBL_BUTOBSSALADE, "...");

            vecCab.add(INT_TBL_CHKJUSMARINC, "Justificar");
            vecCab.add(INT_TBL_OBSMARINC, "Observación");
            vecCab.add(INT_TBL_BUTOBSMARINC, "...");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_FECHA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CODTRA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NOMTRA).setPreferredWidth(240);
            tcmAux.getColumn(INT_TBL_MARENT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_MARSAL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_TIEATR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_TIEADE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CHKFAL).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_CHKJUSATR).setPreferredWidth(55);
//        tcmAux.getColumn(INT_TBL_CHKDESATR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_OBSATR).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_BUTOBSATR).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CHKJUSPER).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_CHKJUSFAL).setPreferredWidth(55);
//        tcmAux.getColumn(INT_TBL_CHKDESFAL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_OBSFAL).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_BUTOBSFAL).setPreferredWidth(20);
//        tcmAux.getColumn(INT_TBL_HORFUEOFI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CHKJUSSALADE).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_OBSSALADE).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_BUTOBSSALADE).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CHKJUSMARINC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_OBSMARINC).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_BUTOBSMARINC).setPreferredWidth(20);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_BUTOBSATR);
            vecAux.add("" + INT_TBL_BUTOBSFAL);
            vecAux.add("" + INT_TBL_BUTOBSSALADE);
            vecAux.add("" + INT_TBL_BUTOBSMARINC);
            //vecAux.add("" + INT_TBL_DAT_BUT_ITM);
            //objTblMod.setColumnasEditables(vecAux);
            objTblMod.addColumnasEditables(vecAux);
            vecAux = null;

            //Configurar JTable: Editor de la tabla.
            objTblEdi = new ZafTblEdi(tblDat);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);

            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTOBSATR).setCellRenderer(zafTblDocCelRenBut);
            ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_BUTOBSATR, "Justificación") {
                public void butCLick() {
                    int intSelFil = tblDat.getSelectedRow();
                    String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_OBSATR) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_OBSATR).toString());
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum09.this), true, strObs);
                    zafMae07_01.show();
                }
            };

            zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTOBSFAL).setCellRenderer(zafTblDocCelRenBut);

            zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_BUTOBSFAL, "Justificación") {
                public void butCLick() {
                    int intSelFil = tblDat.getSelectedRow();
                    String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_OBSFAL) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_OBSFAL).toString());
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum09.this), true, strObs);
                    zafMae07_01.show();
                    /*if (zafMae07_01.getAceptar()) {
                     * tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_OBSFAL);
                     * }*/
                }
            };

            zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTOBSSALADE).setCellRenderer(zafTblDocCelRenBut);

            zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_BUTOBSSALADE, "Justificación") {
                public void butCLick() {
                    int intSelFil = tblDat.getSelectedRow();
                    String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_OBSSALADE) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_OBSSALADE).toString());
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum09.this), true, strObs);
                    zafMae07_01.show();
                    /*if (zafMae07_01.getAceptar()) {
                     * tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_OBSFAL);
                     * }*/
                }
            };

            zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTOBSMARINC).setCellRenderer(zafTblDocCelRenBut);

            zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_BUTOBSMARINC, "Justificación") {
                public void butCLick() {
                    int intSelFil = tblDat.getSelectedRow();
                    String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_OBSMARINC) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_OBSMARINC).toString());
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum09.this), true, strObs);
                    zafMae07_01.show();
                    /*if (zafMae07_01.getAceptar()) {
                     * tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_OBSFAL);
                     * }*/
                }
            };



            /*Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
             Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
             objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
             objTblCelRenChk=null;
             objTblCelEdiChk=null;*/


            Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk = new ZafTblCelRenChk();
            Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKFAL).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_CHKFAL).setCellEditor(objTblCelEdiChk);

            tcmAux.getColumn(INT_TBL_CHKJUSATR).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_CHKJUSATR).setCellEditor(objTblCelEdiChk);

//                tcmAux.getColumn(INT_TBL_CHKDESATR).setCellRenderer(objTblCelRenChk);
//                tcmAux.getColumn(INT_TBL_CHKDESATR).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_CHKJUSPER).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_CHKJUSPER).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_CHKJUSFAL).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_CHKJUSFAL).setCellEditor(objTblCelEdiChk);

//                tcmAux.getColumn(INT_TBL_CHKDESFAL).setCellRenderer(objTblCelRenChk);
//                tcmAux.getColumn(INT_TBL_CHKDESFAL).setCellEditor(objTblCelEdiChk);

            tcmAux.getColumn(INT_TBL_CHKJUSSALADE).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_CHKJUSSALADE).setCellEditor(objTblCelEdiChk);

            tcmAux.getColumn(INT_TBL_CHKJUSMARINC).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_CHKJUSMARINC).setCellEditor(objTblCelEdiChk);

            //Configurar JTable: Establecer el ordenamiento en la tabla.
            objTblOrd = new ZafTblOrd(tblDat);

            //Configurar JTable: Editor de búsqueda.
            objTblBus = new ZafTblBus(tblDat);

            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[] = {};
            objTblTot = new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            blnRes = true;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable. <BR>false: En el caso
     * contrario.
     */
//    private boolean configuraTbl(){
//
//        boolean blnRes=false;
//
//           try{
//
//                //Configurar JTable: Establecer el modelo.
//                vecCab.clear();
//                vecCab.add(INT_TBL_LINEA,"");
//                vecCab.add(INT_TBL_FECHA,"Fecha");
//                vecCab.add(INT_TBL_CODTRA,"Código");
//                vecCab.add(INT_TBL_NOMTRA,"Empleado");//vecCab.add(INT_TBL_APETRA,"Apellidos");
//                vecCab.add(INT_TBL_MARENT,"Entrada");
//                vecCab.add(INT_TBL_MARSAL,"Salida");
//                vecCab.add(INT_TBL_TIEATR, "Atrasos");
//                vecCab.add(INT_TBL_CHKFAL, "Faltas");
//                vecCab.add(INT_TBL_CHKJUSATR, "");
//                vecCab.add(INT_TBL_OBSATR,"Atrasos");
//                vecCab.add(INT_TBL_BUTOBSATR,"...");
//                vecCab.add(INT_TBL_CHKJUSFAL, "");
//                vecCab.add(INT_TBL_OBSFAL,"Faltas");
//                vecCab.add(INT_TBL_BUTOBSFAL,"...");
//                vecCab.add(INT_TBL_HORFUEOFI,"Hor.Fue.Ofi.");
//
//                objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
//                objTblMod.setHeader(vecCab);
//                tblDat.setModel(objTblMod);
//
//                //Configurar JTable: Establecer tipo de selección.
//                tblDat.setRowSelectionAllowed(true);
//                tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//                new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);
//
//                //Configurar JTable: Establecer el menú de contexto.
//                objTblPopMnu=new ZafTblPopMnu(tblDat);
//                
//                //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
//                tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
//                javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
//
//                //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
//                objMouMotAda=new ZafMouMotAda();
//                tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
//
//                intCodEmp = objZafParSis.getCodigoEmpresa();
//                
//                Vector vecAux=null;
//
//                vecAux=new Vector();
//                vecAux.add("" + INT_TBL_BUTOBSATR);
//                vecAux.add("" + INT_TBL_BUTOBSFAL);
//                objTblMod.setColumnasEditables(vecAux);
//                vecAux=null;
//
//                //Configurar JTable: Editor de la tabla.
//                objTblEdi=new ZafTblEdi(tblDat);
//
//                //Configurar JTable: Renderizar celdas.
//                objTblCelRenLbl=new ZafTblCelRenLbl();
//                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
//
//                //Tamaño de las celdas
//                tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
//                tcmAux.getColumn(INT_TBL_FECHA).setPreferredWidth(90);
//                tcmAux.getColumn(INT_TBL_CODTRA).setPreferredWidth(50);
//                tcmAux.getColumn(INT_TBL_NOMTRA).setPreferredWidth(260);
//                tcmAux.getColumn(INT_TBL_MARENT).setPreferredWidth(60);
//                tcmAux.getColumn(INT_TBL_MARSAL).setPreferredWidth(60);
//                tcmAux.getColumn(INT_TBL_TIEATR).setPreferredWidth(60);
//                tcmAux.getColumn(INT_TBL_CHKFAL).setPreferredWidth(50);
//                tcmAux.getColumn(INT_TBL_CHKJUSATR).setPreferredWidth(30);
//                tcmAux.getColumn(INT_TBL_OBSATR).setPreferredWidth(170);
//                tcmAux.getColumn(INT_TBL_BUTOBSATR).setPreferredWidth(30);
//                tcmAux.getColumn(INT_TBL_CHKJUSFAL).setPreferredWidth(30);
//                tcmAux.getColumn(INT_TBL_OBSFAL).setPreferredWidth(170);
//                tcmAux.getColumn(INT_TBL_BUTOBSFAL).setPreferredWidth(30);
//                tcmAux.getColumn(INT_TBL_HORFUEOFI).setPreferredWidth(60);
//
//                new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
//                //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
//                //tblDat.getTableHeader().setReorderingAllowed(false);
//
//                Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
//                tcmAux.getColumn(INT_TBL_BUTOBSATR).setCellRenderer(zafTblDocCelRenBut);
//                ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_BUTOBSATR, "Justificación") {
//                    public void butCLick() {
//                        int intSelFil = tblDat.getSelectedRow();
//                        String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_OBSATR) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_OBSATR).toString());
//                        ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum14.this), true, strObs);
//                        zafMae07_01.show();
//                    }
//                };
//
//                zafTblDocCelRenBut = new ZafTblCelRenBut();
//                tcmAux.getColumn(INT_TBL_BUTOBSFAL).setCellRenderer(zafTblDocCelRenBut);
//
//                zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_BUTOBSFAL, "Justificación") {
//                    public void butCLick() {
//                        int intSelFil = tblDat.getSelectedRow();
//                        String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_OBSFAL) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_OBSFAL).toString());
//                        ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum14.this), true, strObs);
//                        zafMae07_01.show();
//                        /*if (zafMae07_01.getAceptar()) {
//                         * tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_OBSFAL);
//                         * }*/
//                    }
//                };
//                
//                
//
//                /*Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
//                Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
//                objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
//                objTblCelRenChk=null;
//                objTblCelEdiChk=null;*/
//                
//                
//                Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk = new ZafTblCelRenChk();
//                Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk = new ZafTblCelEdiChk();
//                tcmAux.getColumn(INT_TBL_CHKFAL).setCellRenderer(objTblCelRenChk);
//                tcmAux.getColumn(INT_TBL_CHKFAL).setCellEditor(objTblCelEdiChk);
//                
//                tcmAux.getColumn(INT_TBL_CHKJUSATR).setCellRenderer(objTblCelRenChk);
//                tcmAux.getColumn(INT_TBL_CHKJUSATR).setCellEditor(objTblCelEdiChk);
//                
//                tcmAux.getColumn(INT_TBL_CHKJUSFAL).setCellRenderer(objTblCelRenChk);
//                tcmAux.getColumn(INT_TBL_CHKJUSFAL).setCellEditor(objTblCelEdiChk);
//                
//                //Configurar JTable: Establecer el ordenamiento en la tabla.
//                objTblOrd=new ZafTblOrd(tblDat);
//                
//                //Configurar JTable: Editor de búsqueda.
//                objTblBus=new ZafTblBus(tblDat);
//                
//                //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
//                int intCol[]={};
//                objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
//                
//                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
//                blnRes=true;
//              
//           }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
//           return blnRes;
//    }
    private boolean agregarColTblDat() {

        int i, intNumFil, intNumColTblDat;
        ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16 * 2);
        ZafTblHeaColGrp objTblHeaColGrpEmp = null;
        java.awt.Color colFonCol;
        boolean blnRes = true;

        try {
            intNumFil = objTblMod.getRowCountTrue();
            intNumColTblDat = objTblMod.getColumnCount();

            for (i = 0; i < 1; i++) {

                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Marcaciones");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_MARENT + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_MARSAL + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_TIEATR + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_TIEADE + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKFAL + i * INT_TBL_DAT_NUM_TOT_CDI));


                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Justificaciones de atrasos");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);


                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKJUSATR + i * INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKDESATR+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_BUTOBSATR + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_OBSATR + i * INT_TBL_DAT_NUM_TOT_CDI));


                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Justificaciones de faltas");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKJUSPER + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKJUSFAL + i * INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKDESFAL+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_BUTOBSFAL + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_OBSFAL + i * INT_TBL_DAT_NUM_TOT_CDI));

                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Justificaciones de salidas adelantadas");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKJUSSALADE + i * INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKDESFAL+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_BUTOBSSALADE + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_OBSSALADE + i * INT_TBL_DAT_NUM_TOT_CDI));

                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Justificaciones de marcaciones incompletas");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKJUSMARINC + i * INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKDESFAL+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_BUTOBSMARINC + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_OBSMARINC + i * INT_TBL_DAT_NUM_TOT_CDI));


//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_HORFUEOFI+i*INT_TBL_DAT_NUM_TOT_CDI));

            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public void setEditable(boolean editable) {
        if (editable == true) {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        } else {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        panCab = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCodDep = new javax.swing.JTextField();
        txtNomDep = new javax.swing.JTextField();
        butDep = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butVisPre = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
        butCerr = new javax.swing.JButton();
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

        panFil.setLayout(null);

        buttonGroup1.add(optTod);
        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setSelected(true);
        optTod.setText("Todos los empleados");
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
        optTod.setBounds(10, 120, 330, 20);

        buttonGroup1.add(optFil);
        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Sólo los empleados que cumplan con el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(10, 140, 370, 20);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel4.setText("Empresa:"); // NOI18N
        panFil.add(jLabel4);
        jLabel4.setBounds(40, 160, 100, 20);

        txtCodEmp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(140, 160, 60, 20);

        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        panFil.add(txtNomEmp);
        txtNomEmp.setBounds(200, 160, 250, 20);

        butEmp.setText(".."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(450, 160, 20, 20);

        panCab.setPreferredSize(new java.awt.Dimension(0, 130));
        panCab.setLayout(null);
        panFil.add(panCab);
        panCab.setBounds(0, 0, 690, 130);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Departamento:"); // NOI18N
        panFil.add(jLabel5);
        jLabel5.setBounds(40, 180, 100, 20);

        txtCodDep.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDepActionPerformed(evt);
            }
        });
        txtCodDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDepFocusLost(evt);
            }
        });
        panFil.add(txtCodDep);
        txtCodDep.setBounds(140, 180, 60, 20);

        txtNomDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDepActionPerformed(evt);
            }
        });
        txtNomDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDepFocusLost(evt);
            }
        });
        panFil.add(txtNomDep);
        txtNomDep.setBounds(200, 180, 250, 20);

        butDep.setText(".."); // NOI18N
        butDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDepActionPerformed(evt);
            }
        });
        panFil.add(butDep);
        butDep.setBounds(450, 180, 20, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel6.setText("Empleado:"); // NOI18N
        panFil.add(jLabel6);
        jLabel6.setBounds(40, 200, 100, 20);

        txtCodTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTraActionPerformed(evt);
            }
        });
        txtCodTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTraFocusLost(evt);
            }
        });
        panFil.add(txtCodTra);
        txtCodTra.setBounds(140, 200, 60, 20);

        txtNomTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTraActionPerformed(evt);
            }
        });
        txtNomTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTraFocusLost(evt);
            }
        });
        panFil.add(txtNomTra);
        txtNomTra.setBounds(200, 200, 250, 20);

        butTra.setText(".."); // NOI18N
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFil.add(butTra);
        butTra.setBounds(450, 200, 20, 20);

        tabGen.addTab("Filtro", null, panFil, "Filtro");

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
        spnDat.setViewportView(tblDat);

        jPanel1.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTot.setViewportView(tblTot);

        jPanel1.add(spnTot, java.awt.BorderLayout.SOUTH);

        tabGen.addTab("Reporte", jPanel1);

        getContentPane().add(tabGen, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        butCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(90, 23));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        jPanel5.add(butCon);

        butVisPre.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butVisPre.setText("Vista Preliminar");
        butVisPre.setPreferredSize(new java.awt.Dimension(90, 23));
        butVisPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVisPreActionPerformed(evt);
            }
        });
        jPanel5.add(butVisPre);

        butImp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butImp.setText("Imprimir");
        butImp.setPreferredSize(new java.awt.Dimension(90, 23));
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        jPanel5.add(butImp);

        butCerr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCerr.setText("Cerrar");
        butCerr.setPreferredSize(new java.awt.Dimension(90, 23));
        butCerr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerrActionPerformed(evt);
            }
        });
        jPanel5.add(butCerr);

        jPanel3.add(jPanel5, java.awt.BorderLayout.EAST);

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

        jPanel3.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        //consultarRepEmp();
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    public void consultarRepTra() {

        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";

        try {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());

            if (conn != null && txtCodTra.getText().compareTo("") != 0) {

                stmLoc = conn.createStatement();

                strSql = "SELECT tx_ape , tx_nom from tbm_tra where co_tra =  " + txtCodTra.getText();
                rstLoc = stmLoc.executeQuery(strSql);

                if (rstLoc.next()) {
                    txtNomTra.setText(rstLoc.getString("tx_ape") + " " + rstLoc.getString("tx_nom"));
                    txtNomTra.setHorizontalAlignment(2);
                    txtNomTra.setEnabled(false);
                } else {
                    mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                    txtNomTra.setText("");
                    txtCodTra.setText("");
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
            }
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
            txtCodTra.setText("");
            txtNomTra.setText("");
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
            txtCodTra.setText("");
            txtNomTra.setText("");
        }
    }

    public void consultarRepEmp() {

        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";

        try {

            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());

            if (conn != null && txtCodEmp.getText().compareTo("") != 0) {

                stmLoc = conn.createStatement();

                strSql = "SELECT co_emp , tx_nom from tbm_emp where co_emp =  " + txtCodEmp.getText() + " and st_reg like 'A' and co_emp not in (0) ";
                rstLoc = stmLoc.executeQuery(strSql);

                if (rstLoc.next()) {
                    txtNomEmp.setText(rstLoc.getString("tx_nom"));
                    txtNomEmp.setHorizontalAlignment(2);
                    txtNomEmp.setEnabled(false);
                } else {
                    mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                    txtNomEmp.setText("");
                    txtCodEmp.setText("");
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
            }
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    public void consultarRepDep() {

        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";

        try {

            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());

            if (conn != null && txtCodDep.getText().compareTo("") != 0) {

                stmLoc = conn.createStatement();

                strSql = "SELECT co_dep,tx_deslar from tbm_dep where co_dep =  " + txtCodDep.getText();
                rstLoc = stmLoc.executeQuery(strSql);

                if (rstLoc.next()) {
                    txtNomDep.setText(rstLoc.getString("tx_deslar"));
                    txtNomDep.setHorizontalAlignment(2);
                    txtNomDep.setEnabled(false);
                } else {
                    mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                    txtNomDep.setText("");
                    txtCodDep.setText("");
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
            }
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
            txtCodDep.setText("");
            txtNomDep.setText("");
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
            txtCodDep.setText("");
            txtNomDep.setText("");

        }
    }

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     *
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        //txtCodEmp.selectAll();
        strCodEmp = txtCodEmp.getText();
        txtCodEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost

        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp)) {
            if (txtCodEmp.getText().equals("")) {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            } else {
                BuscarEmp("a1.co_emp", txtCodEmp.getText(), 0);
            }
        } else {
            txtCodEmp.setText(strCodEmp);
        }
}//GEN-LAST:event_txtCodEmpFocusLost

    public void BuscarEmp(String campo, String strBusqueda, int tipo) {

        vcoEmp.setTitle("Listado de Empresas");
        if (vcoEmp.buscar(campo, strBusqueda)) {
            txtCodEmp.setText(vcoEmp.getValueAt(1));
            txtNomEmp.setText(vcoEmp.getValueAt(2));
        } else {
            vcoEmp.setCampoBusqueda(tipo);
            vcoEmp.cargarDatos();
            vcoEmp.show();
            if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) {
                txtCodEmp.setText(vcoEmp.getValueAt(1));
                txtNomEmp.setText(vcoEmp.getValueAt(2));
            } else {
                txtCodEmp.setText(strCodEmp);
                txtNomEmp.setText(strNomEmp);
            }
        }
    }

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        //txtNomEmp.transferFocus();
        txtNomEmp.transferFocus();

    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        strNomEmp = txtNomEmp.getText();
        txtNomEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);

}//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        if (txtNomEmp.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp)) {
                if (txtNomEmp.getText().equals("")) {
                    txtCodEmp.setText("");
                    txtNomEmp.setText("");
                } else {
                    mostrarVenConEmp(2);
                }
            } else {
                txtNomEmp.setText(strNomEmp);
            }
        }

}//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed

//        try{
//            zafVenEmp.limpiar();
//            zafVenEmp.setCampoBusqueda(0);
//            zafVenEmp.setVisible(true);
//
//            if (zafVenEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
//                txtCodEmp.setText(String.valueOf(zafVenEmp.getValueAt(1)));
//                txtNomEmp.setText(String.valueOf(zafVenEmp.getValueAt(2)));
//            }
//        }catch (Exception ex){
//            objUti.mostrarMsgErr_F1(this, ex);
//        }
        strCodEmp = txtCodEmp.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    }//GEN-LAST:event_formInternalFrameOpened

    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
        // TODO add your handling code here:

        cargarRepote(1);

//         java.sql.Connection conIns;
//         String DIRECCION_REPORTE="C:/zafiro/reportes_impresos/ZafRecHum09/ZafRecHum09.jrxml";
//         String DIRECCION_REPORTE_DETALLE="C:/zafiro/reportes_impresos/ZafRecHum09/ZafCxC59_01.jrxml";
//         String sqlAux="";
//         String strNomUsr="";
//         String strFecHorSer="";
//         try{
//             conIns =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//            if(conIns!=null){
//
//                if(System.getProperty("os.name").equals("Linux")){
//                   DIRECCION_REPORTE="//zafiro/reportes_impresos/ZafRecHum09/ZafRecHum09.jrxml";
//                   DIRECCION_REPORTE_DETALLE="//zafiro/reportes_impresos/ZafRecHum09/ZafCxC59_01.jrxml";
//                }
//
//                JasperDesign jasperDesign = JasperManager.loadXmlDesign(DIRECCION_REPORTE);
//                JasperDesign jasperDesign2 = JasperManager.loadXmlDesign(DIRECCION_REPORTE_DETALLE);
//
//                JasperReport jasperReport2 = JasperManager.compileReport(jasperDesign);
//                JasperReport subReport = JasperManager.compileReport(jasperDesign2);
//
//
//                 switch (objSelFec.getTipoSeleccion())
//                 {
//                    case 0: //Búsqueda por rangos
//                        sqlAux+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
//                        break;
//                    case 1: //Fechas menores o iguales que "Hasta".
//                        sqlAux+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
//                        break;
//                    case 2: //Fechas mayores o iguales que "Desde".
//                        sqlAux+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
//                        break;
//                    case 3: //Todo.
//                        break;
//                 }
//
//                  if(!(txtCodLoc.getText().equals(""))){
//                      sqlAux+=" AND a.co_loc="+txtCodLoc.getText()+" ";
//                  }else{
//                     sqlAux+=" AND a.co_loc in ( "+strSqlLoc+" ) ";
//                  }
//
//                  if(!(txtCodTipDoc.getText().equals(""))){
//                      sqlAux+=" AND a.co_tipdoc="+txtCodTipDoc.getText()+" ";
//                  }
//
//
//                 if(!(txtCodUsr.getText().equals(""))){
//                     sqlAux+=" AND a.co_usring="+txtCodUsr.getText()+" ";
//                 }
//
//
//                 //Obtener la fecha y hora del servidor.
//                    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
//                    if (datFecAux!=null){
//                      strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
//                      datFecAux=null;
//                    }
//                    strNomUsr=objZafParSis.getNombreUsuario();
//
//                   Map parameters = new HashMap();
//
//                   parameters.put("coemp", ""+objZafParSis.getCodigoEmpresa() );
//                   parameters.put("strAux",  sqlAux );
//                   parameters.put("nomusr", strNomUsr );
//                   parameters.put("fecimp", strFecHorSer );
//                   parameters.put("SUBREPORT", subReport);
//
//                  JasperPrint report = JasperFillManager.fillReport(jasperReport2, parameters, conIns);
//                  JasperViewer.viewReport(report, false);
//
//                  conIns.close();
//                  conIns=null;
//
//        }}catch (JRException e){  System.out.println("Excepción: " + e.toString()); }
//          catch(java.sql.SQLException ex) { System.out.println("Error al conectarse a la base"); }



    }//GEN-LAST:event_butVisPreActionPerformed

    private void butCerrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerrActionPerformed
        exitForm();
}//GEN-LAST:event_butCerrActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (objThrGUI == null) {
            int intOp = -1;
            objThrGUI = new ZafThreadGUI(intOp);
            objThrGUI.start();
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        cargarRepote(0);

//         java.sql.Connection conIns;
//         String DIRECCION_REPORTE="C:/zafiro/reportes_impresos/ZafRecHum09/ZafRecHum09.jrxml";
//         String DIRECCION_REPORTE_DETALLE="C:/zafiro/reportes_impresos/ZafRecHum09/ZafCxC59_01.jrxml";
//         String sqlAux="";
//         String strNomUsr="";
//         String strFecHorSer="";
//         try{
//             conIns =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//            if(conIns!=null){
//
//                if(System.getProperty("os.name").equals("Linux")){
//                   DIRECCION_REPORTE="//zafiro/reportes_impresos/ZafRecHum09/ZafRecHum09.jrxml";
//                   DIRECCION_REPORTE_DETALLE="//zafiro/reportes_impresos/ZafRecHum09/ZafCxC59_01.jrxml";
//                }
//
//                JasperDesign jasperDesign = JasperManager.loadXmlDesign(DIRECCION_REPORTE);
//                JasperDesign jasperDesign2 = JasperManager.loadXmlDesign(DIRECCION_REPORTE_DETALLE);
//
//                JasperReport jasperReport2 = JasperManager.compileReport(jasperDesign);
//                JasperReport subReport = JasperManager.compileReport(jasperDesign2);
//
//
//                 switch (objSelFec.getTipoSeleccion())
//                 {
//                    case 0: //Búsqueda por rangos
//                        sqlAux+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
//                        break;
//                    case 1: //Fechas menores o iguales que "Hasta".
//                        sqlAux+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
//                        break;
//                    case 2: //Fechas mayores o iguales que "Desde".
//                        sqlAux+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
//                        break;
//                    case 3: //Todo.
//                        break;
//                 }
//
//                  if(!(txtCodLoc.getText().equals(""))){
//                      sqlAux+=" AND a.co_loc="+txtCodLoc.getText()+" ";
//                  }else {
//                     sqlAux+=" AND a.co_loc in ( "+strSqlLoc+" ) ";
//                  }
//
//                  if(!(txtCodTipDoc.getText().equals(""))){
//                      sqlAux+=" AND a.co_tipdoc="+txtCodTipDoc.getText()+" ";
//                  }
//
//
//                 if(!(txtCodUsr.getText().equals(""))){
//                     sqlAux+=" AND a.co_usring="+txtCodUsr.getText()+" ";
//                 }
//
//
//                   //Obtener la fecha y hora del servidor.
//                    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
//                    if (datFecAux!=null){
//                      strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
//                      datFecAux=null;
//                    }
//                    strNomUsr=objZafParSis.getNombreUsuario();
//
//                   Map parameters = new HashMap();
//
//                   parameters.put("coemp", ""+objZafParSis.getCodigoEmpresa() );
//                   parameters.put("strAux",  sqlAux );
//                   parameters.put("nomusr", strNomUsr );
//                   parameters.put("fecimp", strFecHorSer );
//                   parameters.put("SUBREPORT", subReport);
//
//                  JasperPrint report = JasperFillManager.fillReport(jasperReport2, parameters, conIns);
//                  JasperManager.printReport(report,false);
//
//                  conIns.close();
//                  conIns=null;
//
//        }}catch (JRException e){  System.out.println("Excepción: " + e.toString()); }
//          catch(java.sql.SQLException ex) { System.out.println("Error al conectarse a la base"); }

    }//GEN-LAST:event_butImpActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
//        if(optFil.isSelected()){
//
//            if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
//                txtCodEmp.setEnabled(true);
//                butEmp.setEnabled(true);
//                txtCodEmp.requestFocus();
//            }
//            else{
//                //txtCodEmp.setEnabled(false);
//                //butEmp.setEnabled(false);
//                txtCodDep.requestFocus();
//            }
//
//            /*txtCodDep.setEnabled(true);
//            txtCodTra.setEnabled(true);
//            butDep.setEnabled(true);
//            butTra.setEnabled(true);*/
//        }
}//GEN-LAST:event_optFilActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
}//GEN-LAST:event_optFilItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed

        if (optTod.isSelected()) {
//            txtCodEmp.setEnabled(false);
//            butEmp.setEnabled(false);
            txtCodEmp.setText("");
            txtNomEmp.setText("");
//            txtCodDep.setEnabled(false);
//            butDep.setEnabled(false);
            txtCodDep.setText("");
            txtNomDep.setText("");
//            txtCodTra.setEnabled(false);
//            butTra.setEnabled(false);
            txtCodTra.setText("");
            txtNomTra.setText("");
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
}//GEN-LAST:event_optTodItemStateChanged

    private void txtCodDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDepActionPerformed
        //consultarRepDep();
        txtCodDep.transferFocus();
    }//GEN-LAST:event_txtCodDepActionPerformed

    private void txtCodDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusGained
        // TODO add your handling code here:
        strCodDep = txtCodDep.getText();
        txtCodDep.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodDepFocusGained

    private void txtCodDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusLost
        // TODO add your handling code here:
        if (!txtCodDep.getText().equalsIgnoreCase(strCodDep)) {
            if (txtCodDep.getText().equals("")) {
                txtCodDep.setText("");
                txtNomDep.setText("");
            } else {
                BuscarDep("a1.co_dep", txtCodDep.getText(), 0);
            }
        } else {
            txtCodDep.setText(strCodDep);
        }
    }//GEN-LAST:event_txtCodDepFocusLost

    public void BuscarDep(String campo, String strBusqueda, int tipo) {

        vcoDep.setTitle("Listado de Departamentos");
        if (vcoDep.buscar(campo, strBusqueda)) {
            txtCodDep.setText(vcoDep.getValueAt(1));
            txtNomDep.setText(vcoDep.getValueAt(3));
        } else {
            vcoDep.setCampoBusqueda(tipo);
            vcoDep.cargarDatos();
            vcoDep.show();
            if (vcoDep.getSelectedButton() == vcoDep.INT_BUT_ACE) {
                txtCodDep.setText(vcoDep.getValueAt(1));
                txtNomDep.setText(vcoDep.getValueAt(3));
            } else {
                txtCodDep.setText(strCodDep);
                txtNomDep.setText(strDesLarDep);
            }
        }
    }

    public void BuscarTra(String campo, String strBusqueda, int tipo) {

        vcoTra.setTitle("Listado de Empleados");
        if (vcoTra.buscar(campo, strBusqueda)) {
            txtCodTra.setText(vcoTra.getValueAt(1));
            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        } else {
            vcoTra.setCampoBusqueda(tipo);
            vcoTra.cargarDatos();
            vcoTra.show();
            if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                txtCodTra.setText(vcoTra.getValueAt(1));
                txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
            } else {
                txtCodTra.setText(strCodTra);
                txtNomTra.setText(strNomTra);
            }
        }
    }

    private void txtNomDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDepActionPerformed

        // TODO add your handling code here:
        txtNomDep.transferFocus();
    }//GEN-LAST:event_txtNomDepActionPerformed

    private void txtNomDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusGained
        // TODO add your handling code here:
        strDesLarDep = txtNomDep.getText();
        txtNomDep.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtNomDepFocusGained

    private void txtNomDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusLost
        // TODO add your handling code here:
        if (txtNomDep.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomDep.getText().equalsIgnoreCase(strDesLarDep)) {
                if (txtNomDep.getText().equals("")) {
                    txtCodDep.setText("");
                    txtNomDep.setText("");
                } else {
                    mostrarVenConDep(2);
                }
            } else {
                txtNomDep.setText(strDesLarDep);
            }
        }
    }//GEN-LAST:event_txtNomDepFocusLost

    private void butDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDepActionPerformed
        strCodDep = txtCodDep.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConDep(0);
    }//GEN-LAST:event_butDepActionPerformed

    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        //consultarRepTra();
        txtCodTra.transferFocus();
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void txtCodTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusGained
        // TODO add your handling code here:
        strCodTra = txtCodTra.getText();
        txtCodTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodTraFocusGained

    private void txtCodTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusLost
        // TODO add your handling code here:
        if (!txtCodTra.getText().equalsIgnoreCase(strCodTra)) {
            if (txtCodTra.getText().equals("")) {
                txtCodTra.setText("");
                txtNomTra.setText("");
            } else {
                BuscarTra("a1.co_tra", txtCodTra.getText(), 0);
            }
        } else {
            txtCodTra.setText(strCodTra);
        }
    }//GEN-LAST:event_txtCodTraFocusLost

    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        // TODO add your handling code here:
        txtNomTra.transferFocus();

    }//GEN-LAST:event_txtNomTraActionPerformed

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        // TODO add your handling code here:
        strNomTra = txtNomTra.getText();
        txtNomTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusLost
        // TODO add your handling code here:
        if (txtNomTra.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomTra.getText().equalsIgnoreCase(strNomTra)) {
                if (txtNomTra.getText().equals("")) {
                    txtCodTra.setText("");
                    txtNomTra.setText("");
                } else {
                    mostrarVenConTra(2);
                }
            } else {
                txtNomTra.setText(strNomTra);
            }
        }
    }//GEN-LAST:event_txtNomTraFocusLost

    private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed
        strCodTra = txtCodTra.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConTra(0);
    }//GEN-LAST:event_butTraActionPerformed

    private class ZafThreadGUI extends Thread {

        private int intOp;

        public ZafThreadGUI(int intOp) {
            this.intOp = intOp;
        }

        public void run() {

            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);

            cargarDat(intOp);

            tabGen.setSelectedIndex(1);

            objThrGUI = null;
        }
    }

    /**
     * Se encarga de cargar la informacion en la tabla
     *
     * @return true si esta correcto y false si hay algun error
     */
    private boolean cargarDat(int intOp) 
    {

        blnConsDat = true;
        boolean blnRes = false;
        blnMod = false;

        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "", sqlFil = "";
        Vector vecData;
        Vector vecDataCon;
        ArrayList<String> arrLisAtrTra = null;

        try 
        {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null) {
                stmLoc = conn.createStatement();
                //vecData = new Vector();
                vecDataCon = new Vector();

                if (blnEstCarMarcEmp) 
                {
                    if (intOp == 2)
                    {
                        sqlFil += " WHERE c.fe_dia BETWEEN '" + objUti.formatearFecha(strFecDes, "dd/MM/yyyy", "yyyy-MM-dd") + "' AND '" + objUti.formatearFecha(strFecHas, "dd/MM/yyyy", "yyyy-MM-dd") + "' ";
                        ZafVenFun zafVenFun = new ZafVenFun(objZafParSis, objUti);
                        arrLisAtrTra = zafVenFun.obtenerDiasAtrasoEmpleado(conn, this, Integer.parseInt(strCodTra), objUti.formatearFecha(strFecDes, "dd/MM/yyyy", "yyyy-MM-dd"), objUti.formatearFecha(strFecHas, "dd/MM/yyyy", "yyyy-MM-dd"));
                    }
                    else if (intOp == 3) 
                    {
//                        String strFeDesde=objUti.formatearFecha(strFecDes, "dd/MM/yyyy", "yyyy-MM-dd");
//                        String[] arrFeDesde=strFeDesde.split("-");
//                        strFeDesde=arrFeDesde[0]+"-"+arrFeDesde[1]+"-"+"25";
//                        
//                        String strFeHasta=objUti.formatearFecha(strFecHas, "dd/MM/yyyy", "yyyy-MM-dd");
//                        String[] arrFeHasta=strFeHasta.split("-");
//                        strFeHasta=arrFeHasta[0]+"-"+arrFeHasta[1]+"-"+"25";
//                        sqlFil+=" WHERE c.fe_dia BETWEEN '" + strFeDesde + "' AND '" + strFeHasta + "' ";

                        sqlFil += " WHERE c.fe_dia BETWEEN '" + objUti.formatearFecha(strFecDes, "dd/MM/yyyy", "yyyy-MM-dd") + "' AND '" + objUti.formatearFecha(strFecHas, "dd/MM/yyyy", "yyyy-MM-dd") + "' ";
                        ZafVenFun zafVenFun = new ZafVenFun(objZafParSis, objUti);
                        arrLisAtrTra = zafVenFun.obtenerDiasMIEmpleado(conn, this, Integer.parseInt(strCodTra), objUti.formatearFecha(strFecDes, "dd/MM/yyyy", "yyyy-MM-dd"), objUti.formatearFecha(strFecHas, "dd/MM/yyyy", "yyyy-MM-dd"));
                    }

                }
                else
                {
                    System.out.println(objSelFec.getFechaDesde());
                    
                    switch (objSelFec.getTipoSeleccion()) 
                    {
                        case 0: //Búsqueda por rangos
                            sqlFil += " WHERE c.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            sqlFil += " WHERE c.fe_dia<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            sqlFil += " WHERE c.fe_dia>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                            break;
                        case 3: //Todo.
                            //Si no hay un Where y hay un filtro por trabajador igual se mostraban todos. Se anadio lo sig para controlarlo. tony
                            sqlFil += " WHERE c.fe_dia>='" + objUti.formatearFecha("01/01/1500", objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                            break;
                    }
                }

                String strSqlDep = "";
                if (objZafParSis.getCodigoUsuario() != 1) 
                {
                    strSqlDep = "inner join tbr_depprgusr h on(h.co_dep=f.co_dep and h.co_dep in (select co_dep from tbr_depprgusr where co_usr = " + objZafParSis.getCodigoUsuario() + " "
                            + "and co_mnu=" + objZafParSis.getCodigoMenu() + ")) ";
                }
                System.out.println("GO 1875 => " + strSqlDep);
                //sqlFil=" WHERE c.fe_dia BETWEEN " + "'2012-06-05' AND '2012-06-05' ";

                String sqlFilEmp = "";
                boolean blnV1 = false;
                boolean blnV2 = false;

                if (blnEstCarMarcEmp) {
                    sqlFilEmp += " AND f.co_emp  = " + strCodEmp + " ";
                    txtCodEmp.setText(strCodEmp);
                    blnV1 = true;

                    sqlFilEmp += " AND c.co_tra  = " + strCodTra + " ";
                    txtCodTra.setText(strCodTra);
                    blnV2 = true;
                } else {
                    if (txtCodEmp.getText().compareTo("") != 0) {
                        sqlFilEmp += " AND f.co_emp  = " + txtCodEmp.getText() + " ";
                        blnV1 = true;
                    }

                    if (blnV1) {
                        if (txtCodTra.getText().compareTo("") != 0) {
                            sqlFilEmp += " AND c.co_tra  = " + txtCodTra.getText() + " ";
                        }
                    } else {
                        if (txtCodTra.getText().compareTo("") != 0) {
                            sqlFilEmp += " AND c.co_tra  = " + txtCodTra.getText() + " ";
                            blnV2 = true;
                        }
                    }
                }

                if (blnV1 || blnV2) {
                    if (txtCodDep.getText().compareTo("") != 0) {
                        sqlFilEmp += " AND d.co_dep  = " + txtCodDep.getText() + " ";
                    }
                } else {
                    if (txtCodDep.getText().compareTo("") != 0) {
                        sqlFilEmp += " AND d.co_dep  = " + txtCodDep.getText() + " ";
                        blnV2 = true;
                    }
                }


                if (objZafParSis.getCodigoEmpresa() == objZafParSis.getCodigoEmpresaGrupo())//Grupo
                {
                    if (objZafParSis.getCodigoUsuario() == 1) //Admin
                    {
//                        strSql =//"SELECT * FROM ( "+
//                                "select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, "
//                                + "(case c.st_jusatr when 'S' then true else false end) as chkJusAtr, c.tx_obsjusatr, "
//                                + "(case c.st_jusfal when 'S' then true else false end) as chkJusFal, c.tx_obsjusfal, "
//                                + "(case c.st_jussalade when 'S' then true else false end) as chkJusSalAde, c.tx_obsjussalade, "
//                                + "(case c.st_jusmarinc when 'S' then true else false end) as chkJusMarInc, c.tx_obsjusmarinc, "
//                                + "g.ho_ent as ho_entHorTra, g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra,(case c.ho_sal < g.ho_sal when true then (g.ho_sal-c.ho_sal) else null end) as hor_ade from ( "
//                                + "select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, "
//                                + "a.st_jusfal , a.tx_obsjusfal , a.st_jussalade , a.tx_obsjussalade, a.st_jusmarinc , a.tx_obsjusmarinc , b.st_reg from tbm_cabconasitra a "
//                                + "inner join tbm_tra b on (a.co_tra = b.co_tra) )c "
//                                + "inner join tbm_traemp f on (f.co_tra=c.co_tra  ) "
//                                + "left outer join tbm_dep d on d.co_dep=f.co_dep  "
//                                + "left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))  " //+ sqlFilEmp + " "+
//                                + sqlFil + sqlFilEmp
//                                + "UNION "
//                                + "select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, "
//                                + "(case c.st_jusatr when 'S' then true else false end) as chkJusAtr, c.tx_obsjusatr, "
//                                + "(case c.st_jusfal when 'S' then true else false end) as chkJusFal, c.tx_obsjusfal, "
//                                + "(case c.st_jussalade when 'S' then true else false end) as chkJusSalAde, c.tx_obsjussalade, "
//                                + "(case c.st_jusmarinc when 'S' then true else false end) as chkJusMarInc, c.tx_obsjusmarinc, "
//                                + "g.ho_ent as ho_entHorTra, g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra,(case c.ho_sal < g.ho_sal when true then (g.ho_sal-c.ho_sal) else null end) as hor_ade from (select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, "
//                                + "a.st_jusfal,a.tx_obsjusfal, a.st_jussalade , a.tx_obsjussalade, a.st_jusmarinc , a.tx_obsjusmarinc , b.st_reg from tbm_cabconasitra a "
//                                + "inner join tbm_tra b on (a.co_tra = b.co_tra and (a.ho_ent is not null or a.ho_sal is not null)))c "
//                                + "inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_horfij like 'N' ) "
//                                + "left outer join tbm_dep d on d.co_dep=f.co_dep  "
//                                + "left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))  " //+ sqlFilEmp + " "+
//                                + sqlFil + sqlFilEmp
//                                + "order by fe_dia asc,tx_ape,tx_nom";
                        //Modificaciones horario. tony
                    strSql = "select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal,"
                            + " (case c.st_jusatr when 'S' then true else false end) as chkJusAtr, c.tx_obsjusatr, "+
                            "(case c.st_jusfal when 'P' then true else false end) as chkJusPer, "
                            + " (case c.st_jusfal when 'S' then true else false end) as chkJusFal, c.tx_obsjusfal, "
                            + " (case c.st_jussalade when 'S' then true else false end) as chkJusSalAde, c.tx_obsjussalade, "
                            + " (case c.st_jusmarinc when 'S' then true else false end) as chkJusMarInc, c.tx_obsjusmarinc, "
                            + "  c.ho_entasi as ho_entHorTra, c.ho_salasi as ho_salHorTra, c.ho_mingraasient as ho_mingraentHorTra,(case c.ho_sal < c.ho_salasi when true then (c.ho_salasi-c.ho_sal) else null end) as hor_ade from ( "
                            + "  select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, "
                            + "  a.st_jusfal , a.tx_obsjusfal , a.st_jussalade , a.tx_obsjussalade, a.st_jusmarinc , a.tx_obsjusmarinc , b.st_reg,"
                            + "  a.co_hor, a.ho_entasi,a.ho_salasi,a.ho_mingraasient from tbm_cabconasitra a "
                            + " inner join tbm_tra b on (a.co_tra = b.co_tra) )c "
                            + " inner join tbm_traemp f on (f.co_tra=c.co_tra  ) "
                            + " left outer join tbm_dep d on d.co_dep=f.co_dep  "
                          //  + "                                --left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))  
                            + sqlFil + sqlFilEmp
                           + "UNION"
                            + " select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, "
                            + "(case c.st_jusatr when 'S' then true else false end) as chkJusAtr, c.tx_obsjusatr, "+
                            "(case c.st_jusfal when 'P' then true else false end) as chkJusPer, "
                            + "(case c.st_jusfal when 'S' then true else false end) as chkJusFal, c.tx_obsjusfal, "
                            + "(case c.st_jussalade when 'S' then true else false end) as chkJusSalAde, c.tx_obsjussalade, "
                            + " (case c.st_jusmarinc when 'S' then true else false end) as chkJusMarInc, c.tx_obsjusmarinc, "
                            + " c.ho_entasi as ho_entHorTra, c.ho_salasi as ho_salHorTra, c.ho_mingraasient as ho_mingraentHorTra,(case c.ho_sal < c.ho_salasi when true then (c.ho_salasi-c.ho_sal) else null end) as hor_ade from (select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, "
                            + " a.st_jusfal,a.tx_obsjusfal, a.st_jussalade , a.tx_obsjussalade, a.st_jusmarinc , a.tx_obsjusmarinc , b.st_reg,"
                            + " a.co_hor, a.ho_entasi,a.ho_salasi,a.ho_mingraasient from tbm_cabconasitra a "
                            + " inner join tbm_tra b on (a.co_tra = b.co_tra and (a.ho_ent is not null or a.ho_sal is not null)))c "
                            + " inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_horfij like 'N' ) "
                            + "  left outer join tbm_dep d on d.co_dep=f.co_dep  "
                            //+ "                                --left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))   "
                            + sqlFil + sqlFilEmp
                            + " order by fe_dia asc,tx_ape,tx_nom";
                    } 
                    else //Otros Usuarios
                    {
//                        strSql =//"SELECT * FROM ( "+
//                                "select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, "
//                                + "(case c.st_jusatr when 'S' then true else false end) as chkJusAtr, c.tx_obsjusatr, "
//                                + "(case c.st_jusfal when 'S' then true else false end) as chkJusFal, c.tx_obsjusfal, "
//                                + "(case c.st_jussalade when 'S' then true else false end) as chkJusSalAde, c.tx_obsjussalade, "
//                                + "(case c.st_jusmarinc when 'S' then true else false end) as chkJusMarInc, c.tx_obsjusmarinc, "
//                                + "g.ho_ent as ho_entHorTra, g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra,(case c.ho_sal < g.ho_sal when true then (g.ho_sal-c.ho_sal) else null end) as hor_ade from ( "
//                                + "select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, "
//                                + "a.st_jusfal,a.tx_obsjusfal, a.st_jussalade , a.tx_obsjussalade, a.st_jusmarinc , a.tx_obsjusmarinc , b.st_reg from tbm_cabconasitra a "
//                                + "inner join tbm_tra b on (a.co_tra = b.co_tra) )c "
//                                + "inner join tbm_traemp f on (f.co_tra=c.co_tra ) "
//                                + "left outer join tbm_dep d on d.co_dep=f.co_dep  "
//                                + "left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))  " 
//                                + //sqlFilEmp + " AND f.co_emp = " + objZafParSis.getCodigoEmpresa() + " "+
//                                strSqlDep
//                                + sqlFil + sqlFilEmp
//                                + //" AND f.co_emp = " + objZafParSis.getCodigoEmpresa() + 
//                                "UNION "
//                                + "select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, "
//                                + "(case c.st_jusatr when 'S' then true else false end) as chkJusAtr, c.tx_obsjusatr, "
//                                + "(case c.st_jusfal when 'S' then true else false end) as chkJusFal, c.tx_obsjusfal, "
//                                + "(case c.st_jussalade when 'S' then true else false end) as chkJusSalAde, c.tx_obsjussalade, "
//                                + "(case c.st_jusmarinc when 'S' then true else false end) as chkJusMarInc, c.tx_obsjusmarinc, "
//                                + "g.ho_ent as ho_entHorTra, g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra,(case c.ho_sal < g.ho_sal when true then (g.ho_sal-c.ho_sal) else null end) as hor_ade from (select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, "
//                                + "a.st_jusfal,a.tx_obsjusfal, a.st_jussalade , a.tx_obsjussalade, a.st_jusmarinc , a.tx_obsjusmarinc , b.st_reg from tbm_cabconasitra a "
//                                + "inner join tbm_tra b on (a.co_tra = b.co_tra and b.st_reg like 'A' and (a.ho_ent is not null or a.ho_sal is not null)))c "
//                                + "inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_horfij like 'N'  ) "
//                                + "left outer join tbm_dep d on d.co_dep=f.co_dep  "
//                                + "left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))  " +//+ sqlFilEmp + " AND f.co_emp = " + objZafParSis.getCodigoEmpresa() + " "+
//                                strSqlDep
//                                + sqlFil + sqlFilEmp
//                                + //" AND f.co_emp = " + objZafParSis.getCodigoEmpresa() + 
//                                "order by fe_dia asc,tx_ape, tx_nom";
                        //Modificaciones horario. tony
                        strSql="                                select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, \n" +
"                              (case c.st_jusatr when 'S' then true else false end) as chkJusAtr, c.tx_obsjusatr, \n" +
                                "(case c.st_jusfal when 'P' then true else false end) as chkJusPer, "+
"                              (case c.st_jusfal when 'S' then true else false end) as chkJusFal, c.tx_obsjusfal, \n" +
"                              (case c.st_jussalade when 'S' then true else false end) as chkJusSalAde, c.tx_obsjussalade, \n" +
"                              (case c.st_jusmarinc when 'S' then true else false end) as chkJusMarInc, c.tx_obsjusmarinc, \n" +
"                              c.ho_entasi as ho_entHorTra, c.ho_salasi as ho_salHorTra, c.ho_mingraasient as ho_mingraentHorTra,(case c.ho_sal < c.ho_salasi when true then (c.ho_salasi-c.ho_sal) else null end) as hor_ade from ( \n" +
"                              select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, \n" +
"                              a.st_jusfal,a.tx_obsjusfal, a.st_jussalade , a.tx_obsjussalade, a.st_jusmarinc , a.tx_obsjusmarinc , b.st_reg,\n" +
"                              a.co_hor,a.ho_entasi,a.ho_salasi,a.ho_mingraasient from tbm_cabconasitra a \n" +
"                              inner join tbm_tra b on (a.co_tra = b.co_tra) )c \n" +
"                              inner join tbm_traemp f on (f.co_tra=c.co_tra ) \n" +
"                              left outer join tbm_dep d on d.co_dep=f.co_dep  \n" +
"                                \n" +strSqlDep
                                + sqlFil + sqlFilEmp +
"                                UNION \n" +
"                              select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, \n" +
"                              (case c.st_jusatr when 'S' then true else false end) as chkJusAtr, c.tx_obsjusatr, \n" +
                                "(case c.st_jusfal when 'P' then true else false end) as chkJusPer, "+
"                              (case c.st_jusfal when 'S' then true else false end) as chkJusFal, c.tx_obsjusfal, \n" +
"                              (case c.st_jussalade when 'S' then true else false end) as chkJusSalAde, c.tx_obsjussalade, \n" +
"                              (case c.st_jusmarinc when 'S' then true else false end) as chkJusMarInc, c.tx_obsjusmarinc, \n" +
"                              c.ho_entasi as ho_entHorTra, c.ho_salasi as ho_salHorTra, c.ho_mingraasient as ho_mingraentHorTra,(case c.ho_sal < c.ho_salasi when true then (c.ho_salasi-c.ho_sal) else null end) as hor_ade from (select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, \n" +
"                              a.st_jusfal,a.tx_obsjusfal, a.st_jussalade , a.tx_obsjussalade, a.st_jusmarinc , a.tx_obsjusmarinc , b.st_reg,\n" +
"                              a.co_hor,a.ho_entasi,a.ho_salasi,a.ho_mingraasient from tbm_cabconasitra a \n" +
"                              inner join tbm_tra b on (a.co_tra = b.co_tra and b.st_reg like 'A' and (a.ho_ent is not null or a.ho_sal is not null)))c \n" +
"                              inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_horfij like 'N'  ) \n" +
"                              left outer join tbm_dep d on d.co_dep=f.co_dep  \n" +
                                strSqlDep 
                                + sqlFil + sqlFilEmp+
                                "                                order by fe_dia asc,tx_ape, tx_nom";
                    }
                }
                else //Empresa
                {
                    if (objZafParSis.getCodigoUsuario() == 1) //Admin
                    {
//                        strSql =//"SELECT * FROM ( "+
//                                "select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, "
//                                + "(case c.st_jusatr when 'S' then true else false end) as chkJusAtr, c.tx_obsjusatr, "
//                                + "(case c.st_jusfal when 'S' then true else false end) as chkJusFal, c.tx_obsjusfal, "
//                                + "(case c.st_jussalade when 'S' then true else false end) as chkJusSalAde, c.tx_obsjussalade, "
//                                + "(case c.st_jusmarinc when 'S' then true else false end) as chkJusMarInc, c.tx_obsjusmarinc, "
//                                + "g.ho_ent as ho_entHorTra, g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra,(case c.ho_sal < g.ho_sal when true then (g.ho_sal-c.ho_sal) else null end) as hor_ade from ( "
//                                + "select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, "
//                                + "a.st_jusfal,a.tx_obsjusfal, a.st_jussalade , a.tx_obsjussalade, a.st_jusmarinc , a.tx_obsjusmarinc , b.st_reg from tbm_cabconasitra a "
//                                + "inner join tbm_tra b on (a.co_tra = b.co_tra) )c "
//                                + "inner join tbm_traemp f on (f.co_tra=c.co_tra AND f.co_emp = " + objZafParSis.getCodigoEmpresa() + " ) "
//                                + "left outer join tbm_dep d on d.co_dep=f.co_dep  "
//                                + "left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))  " //+ sqlFilEmp + " "+
//                                + sqlFil + sqlFilEmp
//                                + "UNION "
//                                + "select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, "
//                                + "(case c.st_jusatr when 'S' then true else false end) as chkJusAtr, c.tx_obsjusatr, "
//                                + "(case c.st_jusfal when 'S' then true else false end) as chkJusFal, c.tx_obsjusfal, "
//                                + "(case c.st_jussalade when 'S' then true else false end) as chkJusSalAde, c.tx_obsjussalade, "
//                                + "(case c.st_jusmarinc when 'S' then true else false end) as chkJusMarInc, c.tx_obsjusmarinc, "
//                                + "g.ho_ent as ho_entHorTra, g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra,(case c.ho_sal < g.ho_sal when true then (g.ho_sal-c.ho_sal) else null end) as hor_ade from (select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, "
//                                + "a.st_jusfal,a.tx_obsjusfal, a.st_jussalade , a.tx_obsjussalade, a.st_jusmarinc , a.tx_obsjusmarinc , b.st_reg from tbm_cabconasitra a "
//                                + "inner join tbm_tra b on (a.co_tra = b.co_tra and (a.ho_ent is not null or a.ho_sal is not null)))c "
//                                + "inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_horfij like 'N' ) "
//                                + "left outer join tbm_dep d on d.co_dep=f.co_dep  "
//                                + "left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))  " //+ sqlFilEmp + " "+
//                                + sqlFil + sqlFilEmp
//                                + "order by fe_dia asc,tx_ape,tx_nom";
                            //Marcaciones modificacion. tony
                        strSql="select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, \n" +
                        "(case c.st_jusatr when 'S' then true else false end) as chkJusAtr, c.tx_obsjusatr, \n" +
                        "(case c.st_jusfal when 'P' then true else false end) as chkJusPer, "+
                        "(case c.st_jusfal when 'S' then true else false end) as chkJusFal, c.tx_obsjusfal, \n" +
                                
                        "(case c.st_jussalade when 'S' then true else false end) as chkJusSalAde, c.tx_obsjussalade, \n" +
                        "(case c.st_jusmarinc when 'S' then true else false end) as chkJusMarInc, c.tx_obsjusmarinc, \n" +
                        "c.ho_entasi as ho_entHorTra, c.ho_salasi as ho_salHorTra, c.ho_mingraasient as ho_mingraentHorTra,(case c.ho_sal < c.ho_salasi when true then (c.ho_salasi-c.ho_sal) else null end) as hor_ade from ( \n" +
                        "select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, \n" +
                        "a.st_jusfal,a.tx_obsjusfal, a.st_jussalade , a.tx_obsjussalade, a.st_jusmarinc , a.tx_obsjusmarinc , b.st_reg,\n" +
                        "a.co_hor,a.ho_entasi,a.ho_salasi,a.ho_mingraasient from tbm_cabconasitra a \n" +
                        "inner join tbm_tra b on (a.co_tra = b.co_tra) )c \n" +
                        "inner join tbm_traemp f on (f.co_tra=c.co_tra AND f.co_emp = " + objZafParSis.getCodigoEmpresa() + " ) " +
                        "left outer join tbm_dep d on d.co_dep=f.co_dep  \n" +
                        //"--left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))  \n" +
                        sqlFil + sqlFilEmp +
                        "UNION \n" +
                        "select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, \n" +
                        "(case c.st_jusatr when 'S' then true else false end) as chkJusAtr, c.tx_obsjusatr, \n" +
                        "(case c.st_jusfal when 'P' then true else false end) as chkJusPer, "+
                        "(case c.st_jusfal when 'S' then true else false end) as chkJusFal, c.tx_obsjusfal, \n" +
                        "(case c.st_jussalade when 'S' then true else false end) as chkJusSalAde, c.tx_obsjussalade, \n" +
                        "(case c.st_jusmarinc when 'S' then true else false end) as chkJusMarInc, c.tx_obsjusmarinc, \n" +
                        "c.ho_entasi as ho_entHorTra, c.ho_salasi as ho_salHorTra, c.ho_mingraasient as ho_mingraentHorTra,(case c.ho_sal < c.ho_salasi when true then (c.ho_salasi-c.ho_sal) else null end) as hor_ade from (select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, \n" +
                        "a.st_jusfal,a.tx_obsjusfal, a.st_jussalade , a.tx_obsjussalade, a.st_jusmarinc , a.tx_obsjusmarinc , b.st_reg,\n" +
                        "a.co_hor,a.ho_entasi,a.ho_salasi, a.ho_mingraasient from tbm_cabconasitra a \n" +
                        "inner join tbm_tra b on (a.co_tra = b.co_tra and (a.ho_ent is not null or a.ho_sal is not null)))c \n" +
                        "inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_horfij like 'N' ) \n" +
                        "left outer join tbm_dep d on d.co_dep=f.co_dep  \n" +
                       // "--left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))   \n" +
                        sqlFil + sqlFilEmp+
                        "order by fe_dia asc,tx_ape,tx_nom";
                    } 
                    else //Otros Usuarios
                    {
//                        strSql =//"SELECT * FROM ( "+
//                                "select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, "
//                                + "(case c.st_jusatr when 'S' then true else false end) as chkJusAtr, c.tx_obsjusatr, "
//                                + "(case c.st_jusfal when 'S' then true else false end) as chkJusFal, c.tx_obsjusfal, "
//                                + "(case c.st_jussalade when 'S' then true else false end) as chkJusSalAde, c.tx_obsjussalade, "
//                                + "(case c.st_jusmarinc when 'S' then true else false end) as chkJusMarInc, c.tx_obsjusmarinc, "
//                                + "g.ho_ent as ho_entHorTra, g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra,(case c.ho_sal < g.ho_sal when true then (g.ho_sal-c.ho_sal) else null end) as hor_ade from ( "
//                                + "select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, "
//                                + "a.st_jusfal,a.tx_obsjusfal, a.st_jussalade , a.tx_obsjussalade, a.st_jusmarinc , a.tx_obsjusmarinc , b.st_reg from tbm_cabconasitra a "
//                                + "inner join tbm_tra b on (a.co_tra = b.co_tra) )c "
//                                + "inner join tbm_traemp f on (f.co_tra=c.co_tra  AND f.co_emp = " + objZafParSis.getCodigoEmpresa() + " ) "
//                                + "left outer join tbm_dep d on d.co_dep=f.co_dep  "
//                                + "left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))  "
//                                + strSqlDep
//                                + sqlFil + sqlFilEmp
//                                + "UNION "
//                                + "select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, "
//                                + "(case c.st_jusatr when 'S' then true else false end) as chkJusAtr, c.tx_obsjusatr, "
//                                + "(case c.st_jusfal when 'S' then true else false end) as chkJusFal, c.tx_obsjusfal, "
//                                + "(case c.st_jussalade when 'S' then true else false end) as chkJusSalAde, c.tx_obsjussalade, "
//                                + "(case c.st_jusmarinc when 'S' then true else false end) as chkJusMarInc, c.tx_obsjusmarinc, "
//                                + "g.ho_ent as ho_entHorTra, g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra,(case c.ho_sal < g.ho_sal when true then (g.ho_sal-c.ho_sal) else null end) as hor_ade from (select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, "
//                                + "a.st_jusfal,a.tx_obsjusfal, a.st_jussalade , a.tx_obsjussalade, a.st_jusmarinc , a.tx_obsjusmarinc , b.st_reg from tbm_cabconasitra a "
//                                + "inner join tbm_tra b on (a.co_tra = b.co_tra and (a.ho_ent is not null or a.ho_sal is not null)))c "
//                                + "inner join tbm_traemp f on (f.co_tra=c.co_tra  and f.st_horfij like 'N'  AND f.co_emp = " + objZafParSis.getCodigoEmpresa() + " ) "
//                                + "left outer join tbm_dep d on d.co_dep=f.co_dep  "
//                                + "left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))  " +//+ sqlFilEmp + " AND f.co_emp = " + objZafParSis.getCodigoEmpresa() + " "+
//                                strSqlDep
//                                + sqlFil + sqlFilEmp
//                                + "order by fe_dia asc,tx_ape, tx_nom";
                                  //Marcaciones modificacion. tony
                        strSql="select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, \n" +
                        "(case c.st_jusatr when 'S' then true else false end) as chkJusAtr, c.tx_obsjusatr, \n" +
                        "(case c.st_jusfal when 'P' then true else false end) as chkJusPer, "+
                        "(case c.st_jusfal when 'S' then true else false end) as chkJusFal, c.tx_obsjusfal, \n" +
                        "(case c.st_jussalade when 'S' then true else false end) as chkJusSalAde, c.tx_obsjussalade, \n" +
                        "(case c.st_jusmarinc when 'S' then true else false end) as chkJusMarInc, c.tx_obsjusmarinc, \n" +
                        "c.ho_entasi as ho_entHorTra, c.ho_salasi as ho_salHorTra, c.ho_mingraasient as ho_mingraentHorTra,(case c.ho_sal < c.ho_salasi when true then (c.ho_salasi-c.ho_sal) else null end) as hor_ade from ( \n" +
                        "select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, \n" +
                        "a.st_jusfal,a.tx_obsjusfal, a.st_jussalade , a.tx_obsjussalade, a.st_jusmarinc , a.tx_obsjusmarinc , b.st_reg,\n" +
                        "a.co_hor,a.ho_entasi,a.ho_salasi,a.ho_mingraasient from tbm_cabconasitra a \n" +
                        "inner join tbm_tra b on (a.co_tra = b.co_tra) )c \n" +
                        "inner join tbm_traemp f on (f.co_tra=c.co_tra  AND f.co_emp = " + objZafParSis.getCodigoEmpresa() + " ) " +
                        "left outer join tbm_dep d on d.co_dep=f.co_dep  \n" +
                         strSqlDep
                        + sqlFil + sqlFilEmp+                                
                        "UNION \n" +
                        "select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, \n" +
                        "(case c.st_jusatr when 'S' then true else false end) as chkJusAtr, c.tx_obsjusatr, \n" +
                        "(case c.st_jusfal when 'P' then true else false end) as chkJusPer, "+
                        "(case c.st_jusfal when 'S' then true else false end) as chkJusFal, c.tx_obsjusfal, \n" +
                        "(case c.st_jussalade when 'S' then true else false end) as chkJusSalAde, c.tx_obsjussalade, \n" +
                        "(case c.st_jusmarinc when 'S' then true else false end) as chkJusMarInc, c.tx_obsjusmarinc, \n" +
                        "c.ho_entasi as ho_entHorTra, c.ho_salasi as ho_salHorTra, c.ho_mingraasient as ho_mingraentHorTra,(case c.ho_sal < c.ho_salasi when true then (c.ho_salasi-c.ho_sal) else null end) as hor_ade from (select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, \n" +
                        "a.st_jusfal,a.tx_obsjusfal, a.st_jussalade , a.tx_obsjussalade, a.st_jusmarinc , a.tx_obsjusmarinc , b.st_reg,\n" +
                        "a.co_hor,a.ho_entasi,a.ho_salasi,a.ho_mingraasient from tbm_cabconasitra a \n" +
                        "inner join tbm_tra b on (a.co_tra = b.co_tra and (a.ho_ent is not null or a.ho_sal is not null)))c \n" +
                        "inner join tbm_traemp f on (f.co_tra=c.co_tra  and f.st_horfij like 'N'  AND f.co_emp = " + objZafParSis.getCodigoEmpresa() + " ) "+
                        "left outer join tbm_dep d on d.co_dep=f.co_dep  \n" +
                         strSqlDep
                        + sqlFil + sqlFilEmp +
                        "order by fe_dia asc,tx_ape, tx_nom";
                    }
                }

                System.out.println("cargarDat: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);

                int intPos1 = 0;

                while (rstLoc.next()) {

                    if (blnEstCarMarcEmp) {
                        /**/
                        for (Iterator<String> it = arrLisAtrTra.iterator(); it.hasNext();) {
                            String strFeDia = it.next();
                            if (rstLoc.getString("fe_dia").compareTo(strFeDia) == 0) {

                                java.util.Vector vecReg = new java.util.Vector();

                                vecReg.add(INT_TBL_LINEA, "");
                                vecReg.add(INT_TBL_FECHA, rstLoc.getString("fe_dia"));
                                vecReg.add(INT_TBL_CODTRA, rstLoc.getInt("co_tra"));
                                vecReg.add(INT_TBL_NOMTRA, rstLoc.getString("tx_ape") + " " + rstLoc.getString("tx_nom"));
                                if (rstLoc.getString("ho_ent") != null) {
                                    vecReg.add(INT_TBL_MARENT, rstLoc.getString("ho_ent").substring(0, 5));
                                } else {
                                    vecReg.add(INT_TBL_MARENT, rstLoc.getString("ho_ent"));
                                }
                                //vecReg.add(INT_TBL_MARENT,rstLoc.getString("ho_ent"));

                                if (rstLoc.getString("ho_sal") != null) {
                                    vecReg.add(INT_TBL_MARSAL, rstLoc.getString("ho_sal").substring(0, 5));
                                } else {
                                    vecReg.add(INT_TBL_MARSAL, rstLoc.getString("ho_sal"));
                                }
                                //vecReg.add(INT_TBL_MARSAL,rstLoc.getString("ho_sal"));


                                java.sql.Time t1 = rstLoc.getTime("ho_enthortra");//hora de entrada segun horario de trabajo
                                java.sql.Time t2 = rstLoc.getTime("ho_mingraenthortra");//minutos de gracia segun horario de trabajo

                                /*FECHA DEL DIA*/
                                String strAAAAFeDia = rstLoc.getString("fe_dia").replace("-", "").substring(0, 4);
                                String strMMFeDia = rstLoc.getString("fe_dia").replace("-", "").substring(4, 6);
                                String strDDFeDia = rstLoc.getString("fe_dia").replace("-", "").substring(6, 8);

                                /*DEFINIMOS LA HORA DE ENTRADA SEGUN EL HORARIO DE TRABAJO*/
                                String strHHHorTra = "00";
                                String strMMHorTra = "00";
                                String strSSHorTra = "00";
                                boolean blnCalAtr = false;
                                if (rstLoc.getString("ho_enthortra") != null) {
                                    strHHHorTra = rstLoc.getString("ho_enthortra").replace(":", "").substring(0, 2);
                                    strMMHorTra = rstLoc.getString("ho_enthortra").replace(":", "").substring(2, 4);
                                    strSSHorTra = rstLoc.getString("ho_enthortra").replace(":", "").substring(4, 6);
                                    blnCalAtr = true;
                                }

                                // año, mes, día, horas, minutos
                                GregorianCalendar calHorTra = new GregorianCalendar(Integer.valueOf(strAAAAFeDia), Integer.valueOf(strMMFeDia), Integer.valueOf(strDDFeDia), Integer.valueOf(strHHHorTra), Integer.valueOf(strMMHorTra), Integer.valueOf(strSSHorTra));
                                //tiempo que se suma - minutos de gracia

                                /*DEFINIMOS EL TIEMPO DE GRACIA SEGUN EL HORARIO DE TRABAJO*/
                                String strHHMinGraHorTra = "00";
                                String strMMMinGraHorTra = "00";
                                String strSSMinGraHorTra = "00";
                                if (rstLoc.getString("ho_mingraenthortra") != null) {
                                    strHHMinGraHorTra = rstLoc.getString("ho_mingraenthortra").replace(":", "").substring(0, 2);
                                    strMMMinGraHorTra = rstLoc.getString("ho_mingraenthortra").replace(":", "").substring(2, 4);
                                    strSSMinGraHorTra = rstLoc.getString("ho_mingraenthortra").replace(":", "").substring(4, 6);

                                }

                                /*DEFINIR LAS HORAS ESTABLECIDAS DE TRABAJO*/
                                int intHHEst = Integer.valueOf(strHHMinGraHorTra) + Integer.valueOf(strHHHorTra);
                                int intMMEst = Integer.valueOf(strMMMinGraHorTra) + Integer.valueOf(strMMHorTra);
                                int intSSEst = Integer.valueOf(strSSMinGraHorTra) + Integer.valueOf(strSSHorTra);

                                //calHorTra.add(GregorianCalendar.HOUR, intHHEst);
                                calHorTra.add(GregorianCalendar.HOUR, 0);
                                calHorTra.add(GregorianCalendar.MINUTE, intMMEst);
                                calHorTra.add(GregorianCalendar.SECOND, intSSEst);

                                DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                                Date dateHorTra = calHorTra.getTime();
                                String strHorTra = getDateTime(dateHorTra);

                                //obtener la hora de entrada del empleado
                                String strTra = "";
                                if (blnCalAtr) {
                                    if (rstLoc.getString("ho_ent") != null) {
                                        Time tHoEnt = rstLoc.getTime("ho_ent");

                                        /*HORA DE ENTRADA DEL TRABAJADOR*/
                                        String strHHEntTra = rstLoc.getString("ho_ent").replace(":", "").substring(0, 2);
                                        String strMMEntTra = rstLoc.getString("ho_ent").replace(":", "").substring(2, 4);
                                        String strSSEntTra = rstLoc.getString("ho_ent").replace(":", "").substring(4, 6);

                                        // año, mes, día, horas, minutos
                                        GregorianCalendar calTra = new GregorianCalendar(Integer.valueOf(strAAAAFeDia), Integer.valueOf(strMMFeDia), Integer.valueOf(strDDFeDia), Integer.valueOf(strHHEntTra), Integer.valueOf(strMMEntTra), Integer.valueOf(strSSEntTra));
                                        //tiempo que se suma - minutos de gracia

                                        Date dateTra = calTra.getTime();;
                                        strTra = getDateTime(dateTra);

                                        int intHorTra = calHorTra.get(Calendar.HOUR);
                                        //if(Integer.valueOf(strHHEntTra)<calHorTra.get(Calendar.HOUR)){
                                        if (Integer.valueOf(strHHEntTra) < intHorTra) {
                                            strTra = "";//"00:00";//:00";
                                        } else {

                                            int intHHRes = 0;
                                            String strHHRes = "00";

                                            String strMMRes = "00";
                                            //if(Integer.valueOf(strHHEntTra)>calHorTra.get(Calendar.HOUR)){
                                            if (Integer.valueOf(strHHEntTra) > intHHEst) {
                                                //intHHRes=Integer.valueOf(strHHEntTra)-calHorTra.get(Calendar.HOUR);

                                                int rt = ((intMMEst - Integer.valueOf(strMMEntTra)) - 60) * -1;
                                                if (rt > 60) {
                                                    intHHRes = Integer.valueOf(strHHEntTra) - intHHEst;
                                                    if (String.valueOf(intHHRes).length() == 1) {
                                                        strHHRes = "0" + String.valueOf(intHHRes);
                                                    }
                                                } else {
                                                    strHHRes = "00";
                                                    intHHRes = Integer.valueOf(strHHEntTra) - intHHEst;
                                                    if (rt > 0) {
                                                        intHHRes--;
                                                        strHHRes = String.valueOf(intHHRes);
                                                    }

                                                    if (strHHRes.length() == 1) {
                                                        strHHRes = "0" + strHHRes;
                                                    }
                                                    strMMRes = String.valueOf(rt);
                                                    if (strMMRes.length() == 1) {
                                                        strMMRes = "0" + strMMRes;

                                                    }

                                                }

                                                strTra = strHHRes + ":" + strMMRes;

                                            }


                                            int intMMRes = 0;
                                            String strSSRes = "00";
                                            int intSSRes = 0;
                                            if (Integer.valueOf(strMMRes) == 0) {
                                                if (Integer.valueOf(strMMEntTra) > calHorTra.get(Calendar.MINUTE) || intHHRes > 0) {

                                                    //intMMRes=Integer.valueOf(strMMEntTra)-calHorTra.get(Calendar.MINUTE);
                                                    intMMRes = Integer.valueOf(strMMEntTra) - intMMEst;
                                                    if (String.valueOf(intMMRes).length() == 1) {
                                                        strMMRes = "0" + String.valueOf(intMMRes);
                                                    } else {
                                                        strMMRes = String.valueOf(intMMRes);
                                                    }

                                                    if (Integer.valueOf(strSSEntTra) > calHorTra.get(Calendar.SECOND)) {

                                                        intSSRes = 60 + calHorTra.get(Calendar.SECOND) - Integer.valueOf(strSSEntTra);
                                                        if (String.valueOf(intSSRes).length() == 1) {
                                                            strSSRes = "0" + String.valueOf(intSSRes);
                                                        } else {
                                                            strSSRes = String.valueOf(intSSRes);
                                                        }
                                                        //intMMRes--;
                                                        if (String.valueOf(intMMRes).length() == 1) {
                                                            strMMRes = "0" + String.valueOf(intMMRes);
                                                        } else {
                                                            strMMRes = String.valueOf(intMMRes);
                                                        }

                                                    }

                                                    strTra = strHHRes + ":" + strMMRes;//+":"+strSSRes;

                                                } else {
                                                    strTra = "";
                                                }
                                            }


                                        }
                                    }
                                    vecReg.add(INT_TBL_TIEATR, strTra);
                                } else {
                                    vecReg.add(INT_TBL_TIEATR, "");
                                }


                                if (rstLoc.getString("hor_ade") != null) {

                                    String strHorAde = rstLoc.getString("hor_ade");
                                    //String[] strHorAdeArr = strHorAde.split(":");
                                    //int intHHAde = Integer.valueOf(strHorAdeArr[0]);
                                    //if(intHHAde>0){
                                    // vecReg.add(INT_TBL_TIEADE,strHorAde);  
                                    //}else{
                                    //vecReg.add(INT_TBL_TIEADE,strHorAde.substring(2, strHorAde.length()));
                                    //}
                                    //00:00:00
                                    if (strHorAde.substring(0, 5).compareTo("00:00") == 0) {
                                        vecReg.add(INT_TBL_TIEADE, "");
                                    } else {
                                        vecReg.add(INT_TBL_TIEADE, strHorAde.substring(0, 5));
                                    }


                                    //0 years 0 mons 0 days 0 hours -29 mins -48.00 secs
                                } else {
                                    vecReg.add(INT_TBL_TIEADE, "");
                                }



                                boolean blnEsFal;
                                //if(rstLoc.getString("ho_ent")==null && rstLoc.getString("ho_sal")==null && !rstLoc.getBoolean("chkJusFal")){
                                if (rstLoc.getString("ho_ent") == null && rstLoc.getString("ho_sal") == null) {
                                    vecReg.add(INT_TBL_CHKFAL, Boolean.TRUE);
                                } else {
                                    vecReg.add(INT_TBL_CHKFAL, Boolean.FALSE);
                                }

                                vecReg.add(INT_TBL_CHKJUSATR, rstLoc.getBoolean("chkJusAtr"));
//                                vecReg.add(INT_TBL_CHKDESATR, Boolean.FALSE);
                                vecReg.add(INT_TBL_OBSATR, rstLoc.getString("tx_obsJusAtr"));
                                vecReg.add(INT_TBL_BUTOBSATR, "..");
                                vecReg.add(INT_TBL_CHKJUSPER,rstLoc.getBoolean("chkJusPer"));
                                vecReg.add(INT_TBL_CHKJUSFAL, rstLoc.getBoolean("chkJusFal"));
//                                vecReg.add(INT_TBL_CHKDESFAL, Boolean.FALSE);
                                vecReg.add(INT_TBL_OBSFAL, rstLoc.getString("tx_obsJusFal"));
                                vecReg.add(INT_TBL_BUTOBSFAL, "..");

                                vecReg.add(INT_TBL_CHKJUSSALADE, rstLoc.getBoolean("chkJusSalAde"));
//                                vecReg.add(INT_TBL_CHKDESFAL, Boolean.FALSE);
                                vecReg.add(INT_TBL_OBSSALADE, rstLoc.getString("tx_obsJusSalAde"));
                                vecReg.add(INT_TBL_BUTOBSSALADE, "..");

                                vecReg.add(INT_TBL_CHKJUSMARINC, rstLoc.getBoolean("chkJusMarInc"));
//                                vecReg.add(INT_TBL_CHKDESFAL, Boolean.FALSE);
                                vecReg.add(INT_TBL_OBSMARINC, rstLoc.getString("tx_obsJusMarInc"));
                                vecReg.add(INT_TBL_BUTOBSMARINC, "..");

//                                vecReg.add(INT_TBL_HORFUEOFI, "" );


                                vecDataCon.add(vecReg);
                                intPos1++;

                            }
                        }
                    } else {

                        java.util.Vector vecReg = new java.util.Vector();

                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_FECHA, rstLoc.getString("fe_dia"));
                        vecReg.add(INT_TBL_CODTRA, rstLoc.getInt("co_tra"));
                        vecReg.add(INT_TBL_NOMTRA, rstLoc.getString("tx_ape") + " " + rstLoc.getString("tx_nom"));
                        if (rstLoc.getString("ho_ent") != null) {
                            vecReg.add(INT_TBL_MARENT, rstLoc.getString("ho_ent").substring(0, 5));
                        } else {
                            vecReg.add(INT_TBL_MARENT, rstLoc.getString("ho_ent"));
                        }
                        //vecReg.add(INT_TBL_MARENT,rstLoc.getString("ho_ent"));

                        if (rstLoc.getString("ho_sal") != null) {
                            vecReg.add(INT_TBL_MARSAL, rstLoc.getString("ho_sal").substring(0, 5));
                        } else {
                            vecReg.add(INT_TBL_MARSAL, rstLoc.getString("ho_sal"));
                        }
                        //vecReg.add(INT_TBL_MARSAL,rstLoc.getString("ho_sal"));


                        java.sql.Time t1 = rstLoc.getTime("ho_enthortra");//hora de entrada segun horario de trabajo
                        java.sql.Time t2 = rstLoc.getTime("ho_mingraenthortra");//minutos de gracia segun horario de trabajo

                        /*FECHA DEL DIA*/
                        String strAAAAFeDia = rstLoc.getString("fe_dia").replace("-", "").substring(0, 4);
                        String strMMFeDia = rstLoc.getString("fe_dia").replace("-", "").substring(4, 6);
                        String strDDFeDia = rstLoc.getString("fe_dia").replace("-", "").substring(6, 8);

                        /*DEFINIMOS LA HORA DE ENTRADA SEGUN EL HORARIO DE TRABAJO*/
                        String strHHHorTra = "00";
                        String strMMHorTra = "00";
                        String strSSHorTra = "00";
                        boolean blnCalAtr = false;
                        if (rstLoc.getString("ho_enthortra") != null) {
                            strHHHorTra = rstLoc.getString("ho_enthortra").replace(":", "").substring(0, 2);
                            strMMHorTra = rstLoc.getString("ho_enthortra").replace(":", "").substring(2, 4);
                            strSSHorTra = rstLoc.getString("ho_enthortra").replace(":", "").substring(4, 6);
                            blnCalAtr = true;
                        }

                        // año, mes, día, horas, minutos
                        GregorianCalendar calHorTra = new GregorianCalendar(Integer.valueOf(strAAAAFeDia), Integer.valueOf(strMMFeDia), Integer.valueOf(strDDFeDia), Integer.valueOf(strHHHorTra), Integer.valueOf(strMMHorTra), Integer.valueOf(strSSHorTra));
                        //tiempo que se suma - minutos de gracia

                        /*DEFINIMOS EL TIEMPO DE GRACIA SEGUN EL HORARIO DE TRABAJO*/
                        String strHHMinGraHorTra = "00";
                        String strMMMinGraHorTra = "00";
                        String strSSMinGraHorTra = "00";
                        if (rstLoc.getString("ho_mingraenthortra") != null) {
                            strHHMinGraHorTra = rstLoc.getString("ho_mingraenthortra").replace(":", "").substring(0, 2);
                            strMMMinGraHorTra = rstLoc.getString("ho_mingraenthortra").replace(":", "").substring(2, 4);
                            strSSMinGraHorTra = rstLoc.getString("ho_mingraenthortra").replace(":", "").substring(4, 6);

                        }

                        /*DEFINIR LAS HORAS ESTABLECIDAS DE TRABAJO*/
                        int intHHEst = Integer.valueOf(strHHMinGraHorTra) + Integer.valueOf(strHHHorTra);
                        int intMMEst = Integer.valueOf(strMMMinGraHorTra) + Integer.valueOf(strMMHorTra);
                        int intSSEst = Integer.valueOf(strSSMinGraHorTra) + Integer.valueOf(strSSHorTra);

                        //calHorTra.add(GregorianCalendar.HOUR, intHHEst);
                        calHorTra.add(GregorianCalendar.HOUR, 0);
                        calHorTra.add(GregorianCalendar.MINUTE, intMMEst);
                        calHorTra.add(GregorianCalendar.SECOND, intSSEst);

                        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                        Date dateHorTra = calHorTra.getTime();
                        String strHorTra = getDateTime(dateHorTra);

                        //obtener la hora de entrada del empleado
                        String strTra = "";
                        if (blnCalAtr) {
                            if (rstLoc.getString("ho_ent") != null) {
                                Time tHoEnt = rstLoc.getTime("ho_ent");

                                /*HORA DE ENTRADA DEL TRABAJADOR*/
                                String strHHEntTra = rstLoc.getString("ho_ent").replace(":", "").substring(0, 2);
                                String strMMEntTra = rstLoc.getString("ho_ent").replace(":", "").substring(2, 4);
                                String strSSEntTra = rstLoc.getString("ho_ent").replace(":", "").substring(4, 6);

                                // año, mes, día, horas, minutos
                                GregorianCalendar calTra = new GregorianCalendar(Integer.valueOf(strAAAAFeDia), Integer.valueOf(strMMFeDia), Integer.valueOf(strDDFeDia), Integer.valueOf(strHHEntTra), Integer.valueOf(strMMEntTra), Integer.valueOf(strSSEntTra));
                                //tiempo que se suma - minutos de gracia

                                Date dateTra = calTra.getTime();;
                                strTra = getDateTime(dateTra);

                                int intHorTra = calHorTra.get(Calendar.HOUR);
                                //if(Integer.valueOf(strHHEntTra)<calHorTra.get(Calendar.HOUR)){
                                if (Integer.valueOf(strHHEntTra) < intHorTra) {
                                    strTra = "";//"00:00";//:00";
                                } else {

                                    int intHHRes = 0;
                                    String strHHRes = "00";

                                    String strMMRes = "00";
                                    //if(Integer.valueOf(strHHEntTra)>calHorTra.get(Calendar.HOUR)){
                                    if (Integer.valueOf(strHHEntTra) > intHHEst) {
                                        //intHHRes=Integer.valueOf(strHHEntTra)-calHorTra.get(Calendar.HOUR);

                                        int rt = ((intMMEst - Integer.valueOf(strMMEntTra)) - 60) * -1;
                                        if (rt > 60) {
                                            intHHRes = Integer.valueOf(strHHEntTra) - intHHEst;
                                            if (String.valueOf(intHHRes).length() == 1) {
                                                strHHRes = "0" + String.valueOf(intHHRes);
                                            }
                                        } else {
                                            strHHRes = "00";
                                            intHHRes = Integer.valueOf(strHHEntTra) - intHHEst;
                                            if (rt > 0) {
                                                intHHRes--;
                                                strHHRes = String.valueOf(intHHRes);
                                            }

                                            if (strHHRes.length() == 1) {
                                                strHHRes = "0" + strHHRes;
                                            }
                                            strMMRes = String.valueOf(rt);
                                            if (strMMRes.length() == 1) {
                                                strMMRes = "0" + strMMRes;

                                            }

                                        }

                                        strTra = strHHRes + ":" + strMMRes;

                                    }


                                    int intMMRes = 0;
                                    String strSSRes = "00";
                                    int intSSRes = 0;
                                    if (Integer.valueOf(strMMRes) == 0) {
                                        if (Integer.valueOf(strMMEntTra) > calHorTra.get(Calendar.MINUTE) || intHHRes > 0) {

                                            //intMMRes=Integer.valueOf(strMMEntTra)-calHorTra.get(Calendar.MINUTE);
                                            intMMRes = Integer.valueOf(strMMEntTra) - intMMEst;
                                            if (String.valueOf(intMMRes).length() == 1) {
                                                strMMRes = "0" + String.valueOf(intMMRes);
                                            } else {
                                                strMMRes = String.valueOf(intMMRes);
                                            }

                                            if (Integer.valueOf(strSSEntTra) > calHorTra.get(Calendar.SECOND)) {

                                                intSSRes = 60 + calHorTra.get(Calendar.SECOND) - Integer.valueOf(strSSEntTra);
                                                if (String.valueOf(intSSRes).length() == 1) {
                                                    strSSRes = "0" + String.valueOf(intSSRes);
                                                } else {
                                                    strSSRes = String.valueOf(intSSRes);
                                                }
                                                //intMMRes--;
                                                if (String.valueOf(intMMRes).length() == 1) {
                                                    strMMRes = "0" + String.valueOf(intMMRes);
                                                } else {
                                                    strMMRes = String.valueOf(intMMRes);
                                                }

                                            }

                                            strTra = strHHRes + ":" + strMMRes;//+":"+strSSRes;

                                        } else {
                                            strTra = "";
                                        }
                                    }


                                }
                            }
                            vecReg.add(INT_TBL_TIEATR, strTra);
                        } else {
                            vecReg.add(INT_TBL_TIEATR, "");
                        }


                        if (rstLoc.getString("hor_ade") != null) {

                            String strHorAde = rstLoc.getString("hor_ade");
                            //String[] strHorAdeArr = strHorAde.split(":");
                            //int intHHAde = Integer.valueOf(strHorAdeArr[0]);
                            //if(intHHAde>0){
                            // vecReg.add(INT_TBL_TIEADE,strHorAde);  
                            //}else{
                            //vecReg.add(INT_TBL_TIEADE,strHorAde.substring(2, strHorAde.length()));
                            //}
                            //00:00:00
                            if (strHorAde.substring(0, 5).compareTo("00:00") == 0) {
                                vecReg.add(INT_TBL_TIEADE, "");
                            } else {
                                vecReg.add(INT_TBL_TIEADE, strHorAde.substring(0, 5));
                            }


                            //0 years 0 mons 0 days 0 hours -29 mins -48.00 secs
                        } else {
                            vecReg.add(INT_TBL_TIEADE, "");
                        }



                        boolean blnEsFal;
                        //if(rstLoc.getString("ho_ent")==null && rstLoc.getString("ho_sal")==null && !rstLoc.getBoolean("chkJusFal")){
                        if (rstLoc.getString("ho_ent") == null && rstLoc.getString("ho_sal") == null) {
                            vecReg.add(INT_TBL_CHKFAL, Boolean.TRUE);
                        } else {
                            vecReg.add(INT_TBL_CHKFAL, Boolean.FALSE);
                        }

                        vecReg.add(INT_TBL_CHKJUSATR, rstLoc.getBoolean("chkJusAtr"));
//                        vecReg.add(INT_TBL_CHKDESATR, Boolean.FALSE);
                        vecReg.add(INT_TBL_OBSATR, rstLoc.getString("tx_obsJusAtr"));
                        vecReg.add(INT_TBL_BUTOBSATR, "..");
                                vecReg.add(INT_TBL_CHKJUSPER,rstLoc.getBoolean("chkJusPer"));
                        vecReg.add(INT_TBL_CHKJUSFAL, rstLoc.getBoolean("chkJusFal"));
//                        vecReg.add(INT_TBL_CHKDESFAL, Boolean.FALSE);
                        vecReg.add(INT_TBL_OBSFAL, rstLoc.getString("tx_obsJusFal"));
                        vecReg.add(INT_TBL_BUTOBSFAL, "..");

                        vecReg.add(INT_TBL_CHKJUSSALADE, rstLoc.getBoolean("chkJusSalAde"));
//                                vecReg.add(INT_TBL_CHKDESFAL, Boolean.FALSE);
                        vecReg.add(INT_TBL_OBSSALADE, rstLoc.getString("tx_obsJusSalAde"));
                        vecReg.add(INT_TBL_BUTOBSSALADE, "..");

                        vecReg.add(INT_TBL_CHKJUSMARINC, rstLoc.getBoolean("chkJusMarInc"));
//                                vecReg.add(INT_TBL_CHKDESFAL, Boolean.FALSE);
                        vecReg.add(INT_TBL_OBSMARINC, rstLoc.getString("tx_obsJusMarInc"));
                        vecReg.add(INT_TBL_BUTOBSMARINC, "..");

//                        vecReg.add(INT_TBL_HORFUEOFI, "" );


                        vecDataCon.add(vecReg);
                        intPos1++;

                    }


                }

                objTblMod.setData(vecDataCon);
                tblDat.setModel(objTblMod);

                //if()
//                if(arrLisAtrTra!=null){
//                    for(int intFil=0; intFil<objTblMod.getColumnCount();intFil++){
//                        for(Iterator<String> it = arrLisAtrTra.iterator();it.hasNext();){
//                            String strFeDia=it.next();
//                            if(objTblMod.getValueAt(intFil, INT_TBL_FECHA).toString().compareTo(strFeDia)==0){
////                                AbstractTableModel modelo =(AbstractTableModel)tblDat.getModel();
//                                
////                                tblDat.removeRowSelectionInterval(intFil, intFil);
//                            }
//                        }
//                    }
//                }
                //Calcular totales.
                //objTblTot.calcularTotales();
                calcularTotales();
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
                //lblMsgSis.setText("Listo");
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);
                blnRes = true;
            }
        } catch (java.sql.SQLException Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    private String getDateTime(Date date) {
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        //Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Esta función calcula el total de las columnas especificadas.
     *
     * @return true: Si se pudo calcular el total de las columnas especificadas
     * sin que ocurra ningún error. <BR>false: En el caso contrario.
     */
    public boolean calcularTotales() {
        int intNumFil, intNumCol, i, j;
        int intHHTieAtr = 0;
        int intMMTieAtr = 0;
        int intSSTieAtr = 0;
        boolean blnRes = true;
        try {
            intNumFil = tblDat.getRowCount();
            intNumCol = tblDat.getColumnCount();
            /*double dblTot[]=new double[intNumCol];
             for (i=0; i<intNumFil; i++)
             {
             for (j=0; j<intNumCol; j++)
             dblTot[j]+=objUti.parseDouble(tblDat.getValueAt(i, intColTot[j]));
             }*/
            /*for (j=0; j<intNumCol; j++)
             tblTot.setValueAt("" + objUti.redondear(dblTot[j], intNumDec), 0, intColTot[j]);*/

            int intHH = 0;
            int intMM = 0;
            int intSS = 0;

            for (i = 0; i < intNumFil; i++) {
                String strTieAtr = tblDat.getValueAt(i, INT_TBL_TIEATR).toString().replaceAll(":", "");
                int intStrTieAtrLgt = strTieAtr.length();
                if (strTieAtr != null && !strTieAtr.equals("")) {

                    if (strTieAtr.length() == 6) {//hhmmss
                        intSSTieAtr = Integer.valueOf(strTieAtr.substring(4, 6));
                    }

                    intHHTieAtr = intHHTieAtr + Integer.valueOf(strTieAtr.substring(0, 2));
                    intMMTieAtr = intMMTieAtr + Integer.valueOf(strTieAtr.substring(2, 4));
                }
            }
//            for (j=0; j<intNumCol; j++)
//                tblTot.setValueAt("" + objUti.redondear(dblTot[j], intNumDec), 0, intColTot[j]);



            String strResTieAtr = "00:00";
            String strMMTieAtr = "";
            String strHHTieAtr = "";
            int intSumHor = 0;
            int intSumDifMin = 0;
            if (intMMTieAtr > 0) {
                if (intMMTieAtr > 60) {
                    intSumHor = intMMTieAtr / 60;
                    if (intSumHor > 0) {
                        intSumDifMin = (intSumHor * 60) - intMMTieAtr;
//                    if(intSumDifMin>0){
                        intMMTieAtr = (intSumDifMin) * -1;
//                    }


                    }


                }
                strMMTieAtr = String.valueOf(intMMTieAtr);
                if (strMMTieAtr.length() == 1) {
                    strMMTieAtr = "0" + strMMTieAtr;
                } else {
                }
            } else {
                strMMTieAtr += "00";
            }
            if (intHHTieAtr > 0 || intSumHor > 0) {
                strHHTieAtr = String.valueOf(intHHTieAtr + intSumHor);
                if (strHHTieAtr.length() == 1) {
                    strHHTieAtr = "0" + strHHTieAtr;
                }
            } else {
                strHHTieAtr += "00";
            }


            if (intMMTieAtr > 0 || intHHTieAtr > 0) {
                strResTieAtr = strHHTieAtr + ":" + strMMTieAtr;
            }

            if (strResTieAtr.compareTo("00:00") == 0) {
                objTblTot.setValueAt("", 0, INT_TBL_TIEATR);
            } else {
                objTblTot.setValueAt(strResTieAtr, 0, INT_TBL_TIEATR);
            }




            /*CALCULO DE TIEMPO DE ADELANTOS*/

            int intHHTieAde = 0;
            int intMMTieAde = 0;
            int intSSTieAde = 0;

            intNumFil = tblDat.getRowCount();
            intNumCol = tblDat.getColumnCount();
            /*double dblTot[]=new double[intNumCol];
             for (i=0; i<intNumFil; i++)
             {
             for (j=0; j<intNumCol; j++)
             dblTot[j]+=objUti.parseDouble(tblDat.getValueAt(i, intColTot[j]));
             }*/
            /*for (j=0; j<intNumCol; j++)
             tblTot.setValueAt("" + objUti.redondear(dblTot[j], intNumDec), 0, intColTot[j]);*/

            intHH = 0;
            intMM = 0;
            intSS = 0;

            for (i = 0; i < intNumFil; i++) {
                String strTieAde = tblDat.getValueAt(i, INT_TBL_TIEADE).toString().replaceAll(":", "");
                int intStrTieAdeLgt = strTieAde.length();
                if (strTieAde != null && !strTieAde.equals("")) {

                    if (strTieAde.length() == 6) {//hhmmss
                        intSSTieAde = Integer.valueOf(strTieAde.substring(4, 6));
                    }

                    intHHTieAde = intHHTieAde + Integer.valueOf(strTieAde.substring(0, 2));
                    intMMTieAde = intMMTieAde + Integer.valueOf(strTieAde.substring(2, 4));
                }
            }
//            for (j=0; j<intNumCol; j++)
//                tblTot.setValueAt("" + objUti.redondear(dblTot[j], intNumDec), 0, intColTot[j]);



            String strResTieAde = "00:00";
            String strMMTieAde = "";
            String strHHTieAde = "";
            intSumHor = 0;
            intSumDifMin = 0;
            if (intMMTieAde > 0) {
                if (intMMTieAde > 60) {
                    intSumHor = intMMTieAde / 60;
                    if (intSumHor > 0) {
                        intSumDifMin = (intSumHor * 60) - intMMTieAde;
//                    if(intSumDifMin>0){
                        intMMTieAde = (intSumDifMin) * -1;
//                    }


                    }


                }
                strMMTieAde = String.valueOf(intMMTieAde);
                if (strMMTieAde.length() == 1) {
                    strMMTieAde = "0" + strMMTieAde;
                } else {
                }
            } else {
                strMMTieAde += "00";
            }
            if (intHHTieAde > 0 || intSumHor > 0) {
                strHHTieAde = String.valueOf(intHHTieAde + intSumHor);
                if (strHHTieAde.length() == 1) {
                    strHHTieAde = "0" + strHHTieAde;
                }
            } else {
                strHHTieAde += "00";
            }


            if (intMMTieAde > 0 || intHHTieAde > 0) {
                strResTieAde = strHHTieAde + ":" + strMMTieAde;
            }

            if (strResTieAde.compareTo("00:00") == 0) {
                objTblTot.setValueAt("", 0, INT_TBL_TIEADE);
            } else {
                objTblTot.setValueAt(strResTieAde, 0, INT_TBL_TIEADE);
            }


            /*CALCULO DE TIEMPOS DE ADELANTOS*/



            /**
             * ***************SUMA DE EXCEDENTES****************
             */
            //calculoExcedenteTiempo();
            /**
             * ***************SUMA DE EXCEDENTES****************
             */
            /**
             * ***************SUMA DE FALTAS****************
             */
            int intContFal = 0;
            for (i = 0; i < intNumFil; i++) {

                if ((Boolean) objTblMod.getValueAt(i, INT_TBL_CHKFAL) == true) {
                    intContFal++;
                }
            }

            if (intContFal > 0) {
                objTblTot.setValueAt(intContFal, 0, INT_TBL_CHKFAL);
            } else {
                objTblTot.setValueAt("", 0, INT_TBL_CHKFAL);
            }

            /**
             * ***************SUMA DE FALTAS****************
             */
            /**
             * ***************SUMA DE JUSTIFICACION DE ATRASOS****************
             */
            int intContJusAtr = 0;
            for (i = 0; i < intNumFil; i++) {

                if ((Boolean) objTblMod.getValueAt(i, INT_TBL_CHKJUSATR) == true) {
                    intContJusAtr++;
                }
            }

            if (intContJusAtr > 0) {
                objTblTot.setValueAt(intContJusAtr, 0, INT_TBL_CHKJUSATR);
            } else {
                objTblTot.setValueAt("", 0, INT_TBL_CHKJUSATR);
            }


            /**
             * ***************SUMA DE JUSTIFICACION DE ATRASOS****************
             */
            /**
             * ***************SUMA DE DESCUENTOS POR ATRASOS****************
             */
//        int intContJusDesAtr=0;
//        for (i=0; i<intNumFil; i++)
//        {
//            
//            if((Boolean)objTblMod.getValueAt(i, INT_TBL_CHKDESATR)==true){
//                intContJusDesAtr++;
//            }
//        }
//
//        if(intContJusDesAtr>0){
//            objTblTot.setValueAt(intContJusDesAtr, 0, INT_TBL_CHKDESATR);
//        }else{
//            objTblTot.setValueAt("", 0, INT_TBL_CHKDESATR);
//        }
             /**
             * ***************SUMA DE JUSTIFICACION DE PERMISOS****************
             */
            int intContJusPer = 0;
            for (i = 0; i < intNumFil; i++) {

                if ((Boolean) objTblMod.getValueAt(i, INT_TBL_CHKJUSPER) == true) {
                    intContJusPer++;
                }
            }

            if (intContJusPer > 0) {
                objTblTot.setValueAt(intContJusPer, 0, INT_TBL_CHKJUSPER);
            } else {
                objTblTot.setValueAt("", 0, INT_TBL_CHKJUSPER);
            }
            /**
            
            
            /**
             * ***************SUMA DE JUSTIFICACION DE ATRASOS****************
             */
            /**
             * ***************SUMA DE JUSTIFICACION DE FALTAS****************
             */
            int intContJusFal = 0;
            for (i = 0; i < intNumFil; i++) {

                if ((Boolean) objTblMod.getValueAt(i, INT_TBL_CHKJUSFAL) == true) {
                    intContJusFal++;
                }
            }

            if (intContJusFal > 0) {
                objTblTot.setValueAt(intContJusFal, 0, INT_TBL_CHKJUSFAL);
            } else {
                objTblTot.setValueAt("", 0, INT_TBL_CHKJUSFAL);
            }

            
            

            /**
             * ***************SUMA DE JUSTIFICACION DE ATRASOS****************
             */
            /**
             * ***************SUMA DE DESCUENTOS POR ATRASOS****************
             */
//        int intContJusDesFal=0;
//        for (i=0; i<intNumFil; i++)
//        {
//            
//            if((Boolean)objTblMod.getValueAt(i, INT_TBL_CHKDESFAL)==true){
//                intContJusDesFal++;
//            }
//        }
//
//        if(intContJusDesFal>0){
//            objTblTot.setValueAt(intContJusDesFal, 0, INT_TBL_CHKDESFAL);
//        }else{
//            objTblTot.setValueAt("", 0, INT_TBL_CHKDESFAL);
//        }
            /**
             * ***************SUMA DE JUSTIFICACION DE ATRASOS****************
             */
            /**
             * ***************SUMA DE SALIDAS ADELANTADAS****************
             */
            int intContSalAde = 0;
            for (i = 0; i < intNumFil; i++) {

                if ((Boolean) objTblMod.getValueAt(i, INT_TBL_CHKJUSSALADE) == true) {
                    intContSalAde++;
                }
            }

            if (intContSalAde > 0) {
                objTblTot.setValueAt(intContSalAde, 0, INT_TBL_CHKJUSSALADE);
            } else {
                objTblTot.setValueAt("", 0, INT_TBL_CHKJUSSALADE);
            }

            /**
             * ***************SUMA DE SALIDAS ADELANTADAS****************
             */
            /**
             * ***************SUMA DE MARCACIONES INCOMPLETAS****************
             */
            int intContMarInc = 0;
            for (i = 0; i < intNumFil; i++) {

                if ((Boolean) objTblMod.getValueAt(i, INT_TBL_CHKJUSMARINC) == true) {
                    intContMarInc++;
                }
            }

            if (intContMarInc > 0) {
                objTblTot.setValueAt(intContMarInc, 0, INT_TBL_CHKJUSMARINC);
            } else {
                objTblTot.setValueAt("", 0, INT_TBL_CHKJUSMARINC);
            }

            /**
             * ***************SUMA DE SALIDAS ADELANTADAS****************
             */
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de
     * salidad.
     */
    private void exitForm() {

        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCerr;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butDep;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butTra;
    private javax.swing.JButton butVisPre;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodDep;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtNomDep;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomTra;
    // End of variables declaration//GEN-END:variables

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {

            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";

            switch (intCol) {

                case INT_TBL_LINEA:
                    strMsg = "";
                    break;
                case INT_TBL_FECHA:
                    strMsg = "Fecha";
                    break;
                case INT_TBL_CODTRA:
                    strMsg = "Código del empleado";
                    break;
                case INT_TBL_NOMTRA:
                    strMsg = "Nombres y Apellidos del empleado";
                    break;
                case INT_TBL_MARENT:
                    strMsg = "Entrada";
                    break;
                case INT_TBL_MARSAL:
                    strMsg = "Salida";
                    break;
                case INT_TBL_TIEATR:
                    strMsg = "Tiempo de atraso";
                    break;
                case INT_TBL_TIEADE:
                    strMsg = "Tiempo de salida adelantada";
                    break;
                case INT_TBL_CHKFAL:
                    strMsg = "¿Tiene falta?";
                    break;
                case INT_TBL_CHKJUSATR:
                    strMsg = "¿Justificar el atraso?";
                    break;
//                 case INT_TBL_CHKDESATR:
//                    strMsg="¿Descontar el atraso?";
//                    break;
                case INT_TBL_OBSATR:
                    strMsg = "Observación de la justificación del atraso";
                    break;
                case INT_TBL_BUTOBSATR:
                    strMsg = "";
                    break;
                case INT_TBL_CHKJUSPER:
                    strMsg = "¿Justificar permiso?";
                    break;
                case INT_TBL_CHKJUSFAL:
                    strMsg = "¿Justificar la falta?";
                    break;
//                 case INT_TBL_CHKDESFAL:
//                    strMsg="¿Descontar la falta?";
//                    break;
                case INT_TBL_OBSFAL:
                    strMsg = "Observación de la justificación de la falta";
                    break;
                case INT_TBL_CHKJUSSALADE:
                    strMsg = "¿Justificar la salida adelantada?";
                    break;
//                 case INT_TBL_CHKDESATR:
//                    strMsg="¿Descontar el atraso?";
//                    break;
                case INT_TBL_OBSSALADE:
                    strMsg = "Observación de la salida adelantada";
                    break;
                case INT_TBL_BUTOBSSALADE:
                    strMsg = "";
                    break;
                case INT_TBL_CHKJUSMARINC:
                    strMsg = "¿Justificar la marcación incompleta?";
                    break;
//                 case INT_TBL_CHKDESATR:
//                    strMsg="¿Descontar el atraso?";
//                    break;
                case INT_TBL_OBSMARINC:
                    strMsg = "Observación de la marcación incompleta";
                    break;
                case INT_TBL_BUTOBSMARINC:
                    strMsg = "";
                    break;
//                case INT_TBL_HORFUEOFI:
//                    strMsg="Total de horas fuera de la oficina";
//                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    protected void finalize() throws Throwable {
        System.gc();
        super.finalize();
    }

    private void cargarRepote(int intTipo) {
        if (objThrGUIRpt == null) {
            objThrGUIRpt = new ZafThreadGUIRpt();
            objThrGUIRpt.setIndFunEje(intTipo);
            objThrGUIRpt.start();
        }
    }

    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que está ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podría presentar mensajes informativos en un JLabel e ir incrementando un
     * JProgressBar con lo cual el usuario estaría informado en todo momento de
     * lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase ya
     * que si no sólo se apreciaría los cambios cuando ha terminado todo el
     * proceso.
     */
    private class ZafThreadGUIRpt extends Thread {

        private int intIndFun;

        public ZafThreadGUIRpt() {
            intIndFun = 0;
        }

        public void run() {
            switch (intIndFun) {
                case 0: //Botón "Imprimir".
                    // objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    //  objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                    //   objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    //google objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUIRpt = null;
        }

        /**
         * Esta función establece el indice de la función a ejecutar. En la
         * clase Thread se pueden ejecutar diferentes funciones. Esta función
         * sirve para determinar la función que debe ejecutar el Thread.
         *
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice) {
            intIndFun = indice;
        }
    }

    /**
     * Esta función permite generar el reporte de acuerdo al criterio
     * seleccionado.
     *
     * @param intTipRpt El tipo de reporte a generar. <BR>Puede tomar uno de los
     * siguientes valores: <UL> <LI>0: Impresión directa. <LI>1: Impresión
     * directa (Cuadro de dialogo de impresión). <LI>2: Vista preliminar. </UL>
     * @return true: Si se pudo generar el reporte. <BR>false: En el caso
     * contrario.
     */
    private boolean generarRpt(int intTipRpt) {
        String strRutRpt, strNomRpt, strFecHorSer = "", strCamAudRpt = "";
        int i, intNumTotRpt;
        boolean blnRes = true;
        String strSql = "", sqlFil = "", sqlFil2 = "";
        try {
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada() == objRptSis.INT_OPC_ACE) {
                intNumTotRpt = objRptSis.getNumeroTotalReportes();
                for (i = 0; i < intNumTotRpt; i++) {
                    if (objRptSis.isReporteSeleccionado(i)) {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i))) {
                            //case 415:
                            default:

                                //Inicializar los parametros que se van a pasar al reporte.

                                //Obtener la fecha y hora del servidor.
                                Date datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                                if (datFecAux != null) {
                                    strFecHorSer = objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                                }

                                switch (objSelFec.getTipoSeleccion()) {
                                    case 0: //Búsqueda por rangos
                                        sqlFil += " WHERE a.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                                        //sqlFil2+=" WHERE x.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                                        break;
                                    case 1: //Fechas menores o iguales que "Hasta".
                                        sqlFil += " WHERE a.fe_dia<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                                        //sqlFil2+=" WHERE x.fe_dia<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                                        break;
                                    case 2: //Fechas mayores o iguales que "Desde".
                                        sqlFil += " WHERE a.fe_dia>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                                        //sqlFil2+=" WHERE a.fe_dia>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                                        break;
                                    case 3: //Todo.
                                        break;
                                }

                                String sqlFilEmp = "";

                                boolean blnV1 = false;
                                if (txtCodEmp.getText().compareTo("") != 0) {
                                    sqlFilEmp += " AND f.co_emp  = " + txtCodEmp.getText() + " ";
                                    blnV1 = true;
                                }

                                boolean blnV2 = false;
                                if (blnV1) {
                                    if (txtCodTra.getText().compareTo("") != 0) {
                                        sqlFilEmp += " AND a.co_tra  = " + txtCodTra.getText() + " ";
                                    }
                                } else {
                                    if (txtCodTra.getText().compareTo("") != 0) {
                                        sqlFilEmp = " AND a.co_tra  = " + txtCodTra.getText() + " ";
                                        blnV2 = true;
                                    }
                                }

                                if (blnV1 || blnV2) {
                                    if (txtCodDep.getText().compareTo("") != 0) {
                                        sqlFilEmp += " AND d.co_dep  = " + txtCodDep.getText() + " ";
                                    }
                                } else {
                                    if (txtCodDep.getText().compareTo("") != 0) {
                                        sqlFilEmp = " AND d.co_dep  = " + txtCodDep.getText() + " ";
                                        blnV2 = true;
                                    }
                                }

                                strRutRpt = objRptSis.getRutaReporte(i);
                                strNomRpt = objRptSis.getNombreReporte(i);
                                strCamAudRpt = "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " + strNomRpt + " v1.1    ";
                                /*strSql="select distinct fe_dia,co_tra, '" + objZafParSis.getNombreEmpresa() + "' as empresa, current_date as fecha, '" + strCamAudRpt + 
                                 "' as strCamAudRpt from tbm_cabconasitra " + sqlFil + " " +
                                 "order by fe_dia,co_tra";*/

                                String strSqlCab = "";
                                String strSqlDet = "";

                                if (objZafParSis.getCodigoEmpresa() == objZafParSis.getCodigoEmpresaGrupo()) {

                                    /*strSql="select c.fe_dia,c.co_tra,c.tx_nom, c.tx_ape, c.ho_ent, c.ho_sal, (case c.st_jusatr when 'S' then true else false end) as chkJusAtr, "+
                                     "c.tx_obsjusatr,(case c.st_jusfal when 'S' then true else false end) as chkJusFal, "+
                                     "c.tx_obsjusfal, g.ho_ent as ho_entHorTra, g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra "+
                                     ", '" + objZafParSis.getNombreEmpresa() + "' as empresa, current_date as fecha, '" + strCamAudRpt+"' as strCamAudRpt "+
                                     "from ( "+
                                     "select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal,a.tx_obsjusfal,b.st_reg "+
                                     "from tbm_cabconasitra a inner join tbm_tra b on (a.co_tra = b.co_tra) and b.st_reg like 'A')c "+
                                     "inner join tbm_traemp f on (f.co_tra=c.co_tra) "+
                                     "left join tbm_dep d on d.co_dep=f.co_dep  "+
                                     "inner join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia))) "+
                                     sqlFil + sqlFilEmp + //" and c.co_tra=21 "+
                                     //"WHERE c.fe_dia BETWEEN '2012-04-30' AND '2012-04-30'  AND c.co_tra=12 "+
                                     "order by c.fe_dia, c.co_tra";*/

                                    strSql =//"select a.fe_dia,a.co_tra,b.tx_nom,b.tx_ape,a.ho_ent,a.ho_sal,g.ho_ent as hoenthortra, g.ho_sal as hosalhortra,g.ho_mingraent as homingraenthortra, "+
                                            "select distinct a.fe_dia "
                                            + "from tbm_cabconasitra a "
                                            + "inner join tbm_tra b on (a.co_tra=b.co_tra) "
                                            + "inner join tbm_traemp f on (f.co_tra=b.co_tra) "
                                            + "left outer join tbm_dep d on d.co_dep=f.co_dep "
                                            + "inner join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from a.fe_dia))) "
                                            + sqlFil + sqlFilEmp + " "
                                            + "order by a.fe_dia";

                                    strSqlCab = strSql;

                                    /*strSqlDet="select a.fe_dia,a.co_tra,b.tx_nom,b.tx_ape,a.ho_ent,a.ho_sal,g.ho_ent as hoenthortra, g.ho_sal as hosalhortra,g.ho_mingraent as homingraenthortra, "
                                     + "(g.ho_ent::time + g.ho_mingraent::interval)::time as hoentmingra, "
                                     + "(a.ho_ent-(g.ho_ent+g.ho_mingraent::interval))::time as tiempoAtraso, "
                                     + "case a.st_jusfal when 'S' then 'SI' else null end as strFalD, "
                                     + "case a.st_jusatr when 'S' then 'SI' else null end as st_jusatr , "
                                     + "case a.st_jusatr when 'S' then 'SI' else null end as strAtr, "
                                     + "case a.st_jusfal when 'S' then 'SI' else null end as st_jusfal "
                                     + "from tbm_cabconasitra a "
                                     + "inner join tbm_tra b on (a.co_tra=b.co_tra) "
                                     + "inner join tbm_traemp f on (f.co_tra=b.co_tra) "
                                     + "left join tbm_dep d on d.co_dep=f.co_dep "
                                     + "inner join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from a.fe_dia))) "
                                     + sqlFil + sqlFilEmp + " and a.fe_dia in ($P{da_parDia})"
                                     + "order by a.fe_dia asc,b.tx_ape, b.tx_nom";*/

                                    strSqlDet = "select * from ( "
                                            + "select a.fe_dia,a.co_tra,b.tx_nom,b.tx_ape,a.ho_ent,a.ho_sal,g.ho_ent as hoenthortra, g.ho_sal as hosalhortra,g.ho_mingraent as homingraenthortra, "
                                            + "(g.ho_ent::time + g.ho_mingraent::interval)::time as hoentmingra, "
                                            + "(a.ho_ent-(g.ho_ent+g.ho_mingraent::interval))::time as tiempoAtraso, "
                                            + "(case a.ho_sal < g.ho_sal when true then (g.ho_sal-a.ho_sal) else null end) as tiempoAdelanto ,"
                                            //+ "(g.ho_sal-a.ho_sal) as tiempoAdelanto ,"
                                            + "case a.st_jusfal when 'S' then 'SI' else null end as strFalD, "
                                            + "case a.st_jusatr when 'S' then 'SI' else null end as st_jusatr , "
                                            + "case a.st_jusatr when 'S' then 'SI' else null end as strAtr, "
                                            + "case a.st_jusfal when 'S' then 'SI' else null end as st_jusfal "
                                            + "from tbm_cabconasitra a "
                                            + "inner join tbm_tra b on (a.co_tra=b.co_tra) "
                                            + "inner join tbm_traemp f on (f.co_tra=b.co_tra) "
                                            + "left outer join tbm_dep d on d.co_dep=f.co_dep "
                                            + "inner join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from a.fe_dia))) "
                                            + sqlFil + sqlFilEmp + " and a.fe_dia in ($P{da_parDia})"
                                            //+ "order by a.fe_dia asc,b.tx_ape, b.tx_nom "
                                            + "union "
                                            + "select a.fe_dia,a.co_tra,b.tx_nom,b.tx_ape,a.ho_ent,a.ho_sal,g.ho_ent as hoenthortra, g.ho_sal as hosalhortra,g.ho_mingraent as homingraenthortra, "
                                            + "(g.ho_ent::time + g.ho_mingraent::interval)::time as hoentmingra, "
                                            + "(a.ho_ent-(g.ho_ent+g.ho_mingraent::interval))::time as tiempoAtraso, "
                                            + "(case a.ho_sal < g.ho_sal when true then (g.ho_sal-a.ho_sal) else null end) as tiempoAdelanto ,"
                                            //+ "(g.ho_sal-a.ho_sal) as tiempoAdelanto ,"
                                            + "case a.st_jusfal when 'S' then 'SI' else null end as strFalD, "
                                            + "case a.st_jusatr when 'S' then 'SI' else null end as st_jusatr , "
                                            + "case a.st_jusatr when 'S' then 'SI' else null end as strAtr, "
                                            + "case a.st_jusfal when 'S' then 'SI' else null end as st_jusfal "
                                            + "from tbm_cabconasitra a "
                                            + "inner join tbm_tra b on (a.co_tra=b.co_tra) "
                                            + "inner join tbm_traemp f on (f.co_tra=b.co_tra) "
                                            + "left outer join tbm_dep d on d.co_dep=f.co_dep "
                                            + "left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from a.fe_dia))) "
                                            + sqlFil + sqlFilEmp + " and a.fe_dia in ($P{da_parDia})"
                                            + ") x " //+ sqlFil2
                                            + "order by x.fe_dia asc,x.tx_ape, x.tx_nom ";

                                } else {

                                    /*strSql="select c.fe_dia,c.co_tra,c.tx_nom, c.tx_ape, c.ho_ent, c.ho_sal, (case c.st_jusatr when 'S' then true else false end) as chkJusAtr, "+
                                     "c.tx_obsjusatr,(case c.st_jusfal when 'S' then true else false end) as chkJusFal, "+
                                     "c.tx_obsjusfal, g.ho_ent as ho_entHorTra, g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra "+
                                     ", '" + objZafParSis.getNombreEmpresa() + "' as empresa, current_date as fecha, '" + strCamAudRpt+"' as strCamAudRpt "+
                                     "from ( "+
                                     "select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, sqlFil2a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal,a.tx_obsjusfal,b.st_reg "+
                                     "from tbm_cabconasitra a inner join tbm_tra b on (a.co_tra = b.co_tra) and b.st_reg like 'A')c "+
                                     "inner join tbm_traemp f on (f.co_tra=c.co_tra) "+
                                     "left join tbm_dep d on d.co_dep=f.co_dep  "+
                                     "inner join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia))) "+
                                     sqlFil + sqlFilEmp + " AND e.co_emp = " + objZafParSis.getCodigoEmpresa() + 
                                     //"WHERE c.fe_dia BETWEEN '2012-04-30' AND '2012-04-30'  AND c.co_tra=12 "+
                                     "order by c.fe_dia, c.co_tra";*/

                                    strSql =//"select a.fe_dia,a.co_tra,b.tx_nom,b.tx_ape,a.ho_ent,a.ho_sal,g.ho_ent as hoenthortra, g.ho_sal as hosalhortra,g.ho_mingraent as homingraenthortra, "+
                                            "select distinct a.fe_dia "
                                            + "from tbm_cabconasitra a "
                                            + "inner join tbm_tra b on (a.co_tra=b.co_tra) "
                                            + "inner join tbm_traemp f on (f.co_tra=b.co_tra) "
                                            + "left outer join tbm_dep d on d.co_dep=f.co_dep "
                                            + "inner join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from a.fe_dia))) "
                                            + sqlFil + sqlFilEmp + " AND f.co_emp = " + objZafParSis.getCodigoEmpresa() + " "
                                            + "order by a.fe_dia";

                                    strSqlCab = strSql;

                                    strSqlDet = "select * from ( "
                                            + "select a.fe_dia,a.co_tra,b.tx_nom,b.tx_ape,a.ho_ent,a.ho_sal,g.ho_ent as hoenthortra, g.ho_sal as hosalhortra,g.ho_mingraent as homingraenthortra, "
                                            + "(g.ho_ent::time + g.ho_mingraent::interval)::time as hoentmingra, "
                                            + "(a.ho_ent-(g.ho_ent+g.ho_mingraent::interval))::time as tiempoAtraso, "
                                            + "(case a.ho_sal < g.ho_sal when true then (g.ho_sal-a.ho_sal) else null end) as tiempoAdelanto ,"
                                            + "case a.st_jusfal when 'S' then 'SI' else null end as strFalD, "
                                            + "case a.st_jusatr when 'S' then 'SI' else null end as st_jusatr , "
                                            + "case a.st_jusatr when 'S' then 'SI' else null end as strAtr, "
                                            + "case a.st_jusfal when 'S' then 'SI' else null end as st_jusfal "
                                            + "from tbm_cabconasitra a "
                                            + "inner join tbm_tra b on (a.co_tra=b.co_tra) "
                                            + "inner join tbm_traemp f on (f.co_tra=b.co_tra) "
                                            + "left outer join tbm_dep d on d.co_dep=f.co_dep "
                                            + "inner join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from a.fe_dia))) "
                                            + sqlFil + sqlFilEmp + " AND f.co_emp = " + objZafParSis.getCodigoEmpresa() + " and a.fe_dia in ($P{da_parDia}) "
                                            //+ "order by a.fe_dia asc,b.tx_ape, b.tx_nom";

                                            + "union "
                                            + "select a.fe_dia,a.co_tra,b.tx_nom,b.tx_ape,a.ho_ent,a.ho_sal,g.ho_ent as hoenthortra, g.ho_sal as hosalhortra,g.ho_mingraent as homingraenthortra, "
                                            + "(g.ho_ent::time + g.ho_mingraent::interval)::time as hoentmingra, "
                                            + "(a.ho_ent-(g.ho_ent+g.ho_mingraent::interval))::time as tiempoAtraso, "
                                            + "(case a.ho_sal < g.ho_sal when true then (g.ho_sal-a.ho_sal) else null end) as tiempoAdelanto ,"
                                            + "case a.st_jusfal when 'S' then 'SI' else null end as strFalD, "
                                            + "case a.st_jusatr when 'S' then 'SI' else null end as st_jusatr , "
                                            + "case a.st_jusatr when 'S' then 'SI' else null end as strAtr, "
                                            + "case a.st_jusfal when 'S' then 'SI' else null end as st_jusfal "
                                            + "from tbm_cabconasitra a "
                                            + "inner join tbm_tra b on (a.co_tra=b.co_tra) "
                                            + "inner join tbm_traemp f on (f.co_tra=b.co_tra) "
                                            + "left outer join tbm_dep d on d.co_dep=f.co_dep "
                                            + "left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from a.fe_dia))) "
                                            + sqlFil + sqlFilEmp + " AND f.co_emp = " + objZafParSis.getCodigoEmpresa() + " and a.fe_dia in ($P{da_parDia}) "
                                            + ") x " //+ sqlFil2
                                            + "order by x.fe_dia asc,x.tx_ape, x.tx_nom ";
                                }

                                //System.out.println("vista:" +strSql);
                                java.util.Map mapPar = new java.util.HashMap();
                                mapPar.put("strsqlcab", strSqlCab);
                                mapPar.put("strsqldet", strSqlDet);
                                mapPar.put("empresa", objZafParSis.getNombreEmpresa());
                                mapPar.put("fecha", datFecAux);
                                mapPar.put("strCamAudRpt", strCamAudRpt);
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                //mapPar.put("prueba",vecDataCon);
                                /*java.util.Map mapPar=new java.util.HashMap();
                                 mapPar.put("coemp", new Integer(objZafParSis.getCodigoEmpresa()) );
                                 mapPar.put("strAux",  sqlAux );
                                 mapPar.put("nomusr", strNomUsr );
                                 mapPar.put("fecimp", strFecHorSer );
                                 mapPar.put("SUBREPORT_DIR", strRutRpt );
                                 mapPar.put("strCamAudRpt", "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ");
                                 */
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);

                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean mostrarVenConEmp(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
//                        txtCodCli.setEditable(true);
//                        txtNomCli.setEditable(true);
//                        butCli.setEnabled(true);
//                        vcoLoc.limpiar();
//                        vcoCli.limpiar();
//                        vcoVen.limpiar();
//                        txtCodLoc.setText("");
//                        txtNomLoc.setText("");
//                        txtCodCli.setText("");
//                        txtRucCli.setText("");
//                        txtNomCli.setText("");
//                        txtCodVen.setText("");
//                        txtAliVen.setText("");
//                        txtNomVen.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText())) {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
//                        txtCodCli.setEditable(true);
//                        txtNomCli.setEditable(true);
//                        butCli.setEnabled(true);
//                        vcoLoc.limpiar();
//                        vcoCli.limpiar();
//                        vcoVen.limpiar();
//                        txtCodLoc.setText("");
//                        txtNomLoc.setText("");
//                        txtCodCli.setText("");
//                        txtRucCli.setText("");
//                        txtNomCli.setText("");
//                        txtCodVen.setText("");
//                        txtAliVen.setText("");
//                        txtNomVen.setText("");
                    } else {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
//                            txtCodCli.setEditable(true);
//                            txtNomCli.setEditable(true);
//                            butCli.setEnabled(true);
//                            vcoLoc.limpiar();
//                            vcoCli.limpiar();
//                            vcoVen.limpiar();
//                            txtCodLoc.setText("");
//                            txtNomLoc.setText("");
//                            txtCodCli.setText("");
//                            txtRucCli.setText("");
//                            txtNomCli.setText("");
//                            txtCodVen.setText("");
//                            txtAliVen.setText("");
//                            txtNomVen.setText("");
                        } else {
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText())) {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
//                        txtCodCli.setEditable(true);
//                        txtNomCli.setEditable(true);
//                        butCli.setEnabled(true);
//                        vcoLoc.limpiar();
//                        vcoCli.limpiar();
//                        vcoVen.limpiar();
//                        txtCodLoc.setText("");
//                        txtNomLoc.setText("");
//                        txtCodCli.setText("");
//                        txtRucCli.setText("");
//                        txtNomCli.setText("");
//                        txtCodVen.setText("");
//                        txtAliVen.setText("");
//                        txtNomVen.setText("");

                    } else {
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
//                            txtCodCli.setEditable(true);
//                            txtNomCli.setEditable(true);
//                            butCli.setEnabled(true);
//                            vcoLoc.limpiar();
//                            vcoCli.limpiar();
//                            vcoVen.limpiar();
//                            txtCodLoc.setText("");
//                            txtNomLoc.setText("");
//                            txtCodCli.setText("");
//                            txtRucCli.setText("");
//                            txtNomCli.setText("");
//                            txtCodVen.setText("");
//                            txtAliVen.setText("");
//                            txtNomVen.setText("");

                        } else {
                            txtNomEmp.setText(strNomEmp);
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean mostrarVenConTra(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.show();
                    if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
//                        txtCodCli.setEditable(true);
//                        txtNomCli.setEditable(true);
//                        butCli.setEnabled(true);
//                        vcoLoc.limpiar();
//                        vcoCli.limpiar();
//                        vcoVen.limpiar();
//                        txtCodLoc.setText("");
//                        txtNomLoc.setText("");
//                        txtCodCli.setText("");
//                        txtRucCli.setText("");
//                        txtNomCli.setText("");
//                        txtCodVen.setText("");
//                        txtAliVen.setText("");
//                        txtNomVen.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoTra.buscar("a1.co_tra", txtCodTra.getText())) {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
//                        txtCodCli.setEditable(true);
//                        txtNomCli.setEditable(true);
//                        butCli.setEnabled(true);
//                        vcoLoc.limpiar();
//                        vcoCli.limpiar();
//                        vcoVen.limpiar();
//                        txtCodLoc.setText("");
//                        txtNomLoc.setText("");
//                        txtCodCli.setText("");
//                        txtRucCli.setText("");
//                        txtNomCli.setText("");
//                        txtCodVen.setText("");
//                        txtAliVen.setText("");
//                        txtNomVen.setText("");
                    } else {
                        vcoTra.setCampoBusqueda(0);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
//                            txtCodCli.setEditable(true);
//                            txtNomCli.setEditable(true);
//                            butCli.setEnabled(true);
//                            vcoLoc.limpiar();
//                            vcoCli.limpiar();
//                            vcoVen.limpiar();
//                            txtCodLoc.setText("");
//                            txtNomLoc.setText("");
//                            txtCodCli.setText("");
//                            txtRucCli.setText("");
//                            txtNomCli.setText("");
//                            txtCodVen.setText("");
//                            txtAliVen.setText("");
//                            txtNomVen.setText("");
                        } else {
                            txtCodTra.setText(strCodTra);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTra.buscar("a1.tx_ape", txtNomTra.getText())) {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
//                        txtCodCli.setEditable(true);
//                        txtNomCli.setEditable(true);
//                        butCli.setEnabled(true);
//                        vcoLoc.limpiar();
//                        vcoCli.limpiar();
//                        vcoVen.limpiar();
//                        txtCodLoc.setText("");
//                        txtNomLoc.setText("");
//                        txtCodCli.setText("");
//                        txtRucCli.setText("");
//                        txtNomCli.setText("");
//                        txtCodVen.setText("");
//                        txtAliVen.setText("");
//                        txtNomVen.setText("");

                    } else {
                        vcoTra.setCampoBusqueda(1);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
//                            txtCodCli.setEditable(true);
//                            txtNomCli.setEditable(true);
//                            butCli.setEnabled(true);
//                            vcoLoc.limpiar();
//                            vcoCli.limpiar();
//                            vcoVen.limpiar();
//                            txtCodLoc.setText("");
//                            txtNomLoc.setText("");
//                            txtCodCli.setText("");
//                            txtRucCli.setText("");
//                            txtNomCli.setText("");
//                            txtCodVen.setText("");
//                            txtAliVen.setText("");
//                            txtNomVen.setText("");

                        } else {
                            txtNomTra.setText(strNomTra);
                        }
                    }
                    break;

            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConEmp() {
        boolean blnRes = true;
        String strTitVenCon = "";
        String strSQL = "";
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.

//            String strAux = "";
//            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
//                strAux = " and a1.co_emp in ("+objParSis.getCodigoEmpresa() +")";
//            }

            if (objZafParSis.getCodigoUsuario() == 1) {
                if (objZafParSis.getCodigoEmpresa() == objZafParSis.getCodigoEmpresaGrupo()) {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.tx_nom";
                    strSQL += " FROM tbm_emp AS a1";
                    strSQL += " WHERE a1.co_emp<>" + objZafParSis.getCodigoEmpresaGrupo() + "";
                    strSQL += " AND a1.st_reg NOT IN('I','E')";
                    strSQL += " ORDER BY a1.co_emp";
                } else {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.tx_nom";
                    strSQL += " FROM tbm_emp AS a1";
                    strSQL += " WHERE a1.co_emp in (" + objZafParSis.getCodigoEmpresa() + ")" + "";
                    strSQL += " AND a1.st_reg NOT IN('I','E')";
                    strSQL += " ORDER BY a1.co_emp";
                }

            } else {
//                strSQL="";
//                strSQL+="SELECT a1.co_emp, a1.tx_nom";
//                strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
//                strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
//                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
//                strSQL+=" AND a1.st_reg NOT IN('I','E')" + strAux;
//                strSQL+=" ORDER BY a1.co_emp";
                if (objZafParSis.getCodigoEmpresa() == objZafParSis.getCodigoEmpresaGrupo()) {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.tx_nom";
                    strSQL += " FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL += " ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objZafParSis.getCodigoUsuario() + "";
                    strSQL += " WHERE a1.co_emp<>" + objZafParSis.getCodigoEmpresaGrupo() + "";
                    strSQL += " AND a1.st_reg NOT IN('I','E')";
                    strSQL += " ORDER BY a1.co_emp";
                } else {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.tx_nom";
                    strSQL += " FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL += " ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objZafParSis.getCodigoUsuario() + "";
                    strSQL += " WHERE a1.co_emp in (" + objZafParSis.getCodigoEmpresa() + ")" + "";
                    strSQL += " AND a1.st_reg NOT IN('I','E')";
                    strSQL += " ORDER BY a1.co_emp";
                }
            }
            vcoEmp = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema. <BR>false: En el caso
     * contrario.
     */
    private boolean mostrarVenConDep(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoDep.setCampoBusqueda(2);
                    vcoDep.setVisible(true);
                    if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));

                        /*if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoDep.getValueAt(7))))
                         {
                            
                         if (tooBar.getEstado()=='n')
                         {
                         //strAux=vcoTipDoc.getValueAt(4);
                         //txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                         }
                         //intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                         //strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                         //txtNumDoc1.selectAll();
                         //txtNumDoc1.requestFocus();
                         }*/
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    //vcoDep.setCampoBusqueda(0);
                    vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.co_dep", txtCodDep.getText())) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));

                        /*if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7))))
                         {
                         txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                         txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                         txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                         if (objTooBar.getEstado()=='n')
                         {
                         strAux=vcoTipDoc.getValueAt(4);
                         txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                         }
                         intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                         strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                         txtNumDoc1.selectAll();
                         txtNumDoc1.requestFocus();
                         }
                         else
                         {
                         txtDesCorTipDoc.setText(strDesCorTipDoc);
                         }*/
                    } else {
                        vcoDep.setCampoBusqueda(1);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {

                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));
                            /*if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7))))
                             {
                             txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                             txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                             txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                             if (objTooBar.getEstado()=='n')
                             {
                             strAux=vcoTipDoc.getValueAt(4);
                             txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                             }
                             intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                             strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                             txtNumDoc1.selectAll();
                             txtNumDoc1.requestFocus();
                             }
                             else
                             {
                             txtDesCorTipDoc.setText(strDesCorTipDoc);
                             }*/
                        } else {
                            txtNomDep.setText(strDesLarDep);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoDep.setCampoBusqueda(2);
                    //vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.tx_desLar", txtNomDep.getText())) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                        //if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoDep.getValueAt(4))))
                        //{
                        //txtCodTipDoc.setText(vcoDep.getValueAt(1));
                        //txtDesCorTipDoc.setText(vcoDep.getValueAt(2));
                        //txtDesLarTipDoc.setText(vcoDep.getValueAt(3));
                        //if (tooBar.getEstado()=='n')
                        //{
                        //strAux=vcoTipDoc.getValueAt(4);
                        //txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        //}
                            /*intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                         strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                         txtNumDoc1.selectAll();
                         txtNumDoc1.requestFocus();*/
                        //}
                        //else
                        //{
                        //txtNomDep.setText(strNomDep);
                        //}
                    } else {
                        vcoDep.setCampoBusqueda(2);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            //if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoDep.getValueAt(3))))
                            //{

                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));

                            //txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            //txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            //if (tooBar.getEstado()=='n')
                            //{
                                    /*strAux=vcoTipDoc.getValueAt(4);
                             txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));*/
                            //}
                                /*intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                             strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                             txtNumDoc1.selectAll();
                             txtNumDoc1.requestFocus();*/
                            //}
//                            else
//                            {
//                                txtNomDep.setText(strNomDep);
//                            }
                        } else {
                            txtNomDep.setText(strDesLarDep);
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConDep() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_dep");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción corta");
            arlAli.add("Descripción larga");
            arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            arlAncCol.add("110");
            arlAncCol.add("40");

            String strSQL = "";
            if (objZafParSis.getCodigoUsuario() == 1) {
                strSQL = "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where st_reg like 'A' order by co_dep";
            } else {
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                 "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
                strSQL = "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr=" + objZafParSis.getCodigoUsuario() + " "
                        + "and co_mnu=" + objZafParSis.getCodigoMenu() + ")";
            }

            //Ocultar columnas.
            int intColOcu[] = new int[1];
            vcoDep = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado Departamentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoDep.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoDep.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Empleados".
     */
    private boolean configurarVenConTra() {
        boolean blnRes = true;
        String strSQL = "";
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ape");
            arlCam.add("a1.tx_nom");
            //arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            //arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");
            //arlAncCol.add("40");


            if (objZafParSis.getCodigoEmpresa() == objZafParSis.getCodigoEmpresaGrupo()) {
                strSQL = "select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where a.st_reg like 'A' "
                        + "order by (a.tx_ape || ' ' || a.tx_nom)";
            } else {
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                 "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
                strSQL = "select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where a.st_reg like 'A' and co_emp = " + objZafParSis.getCodigoEmpresa() + " "
                        + "order by (a.tx_ape || ' ' || a.tx_nom)";
            }

            //Ocultar columnas.
            int intColOcu[] = new int[1];
            //intColOcu[0]=3;
            vcoTra = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
//            vcoTra.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
//            vcoTra.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
}