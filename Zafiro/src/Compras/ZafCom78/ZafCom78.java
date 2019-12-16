/*
 * ZafCom78.java
 * Seguimiento Ordenes de Despacho.
 * @author Javier
 * Created on 20/09/2011, 10:31:09 PM
 */
package Compras.ZafCom78;

import Compras.ZafCom23.ZafCom23;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Maestros.ZafMae07.ZafMae07_01;
import Ventas.ZafVen21.ZafVen21_01;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableColumnModel;

public class ZafCom78 extends javax.swing.JInternalFrame 
{
    //Constantes
    // TABLA DE DATOS
    private static final int INT_TBL_DAT_LIN = 0;
    private static final int INT_TBL_DAT_CODSEG = 1;
    private static final int INT_TBL_DAT_CODEMP = 2;
    private static final int INT_TBL_DAT_NOMEMP = 3; 
    private static final int INT_TBL_DAT_CODLOC = 4;
    private static final int INT_TBL_DAT_NOMLOC = 5; 
    private static final int INT_TBL_DAT_CODTID = 6;
    private static final int INT_TBL_DAT_DESTIDC = 7;
    private static final int INT_TBL_DAT_CODDOC = 8;
    private static final int INT_TBL_DAT_NUMDOC = 9;
    private static final int INT_TBL_DAT_MOT_TRA = 10;
    private static final int INT_TBL_DAT_FECDOC = 11;
    private static final int INT_TBL_DAT_TXDESTI = 12;
    private static final int INT_TBL_DAT_PTOLLE = 13;
    private static final int INT_TBL_DAT_ZONCIU = 14; 
    private static final int INT_TBL_DAT_FORRET = 15;
    private static final int INT_TBL_DAT_PERRET = 16; 
    private static final int INT_TBL_DAT_PESO = 17;
    private static final int INT_TBL_DAT_BUTOD = 18;
    private static final int INT_TBL_DAT_FECING = 19;
    private static final int INT_TBL_DAT_STCONPEN = 20;
    private static final int INT_TBL_DAT_STCONPAR = 21;
    private static final int INT_TBL_DAT_STCONTOT = 22;
    private static final int INT_TBL_DAT_BUTCONF = 23;
    private static final int INT_TBL_DAT_BUTGUIA = 24;
    private static final int INT_TBL_DAT_ESTDIA = 25;
    private static final int INT_TBL_DAT_STFALSTK = 26;
    private static final int INT_TBL_DAT_STDANIADOS = 27;
    private static final int INT_TBL_DAT_BUTFAL = 28;
    private static final int INT_TBL_DAT_DIADESATR = 29;
    private static final int INT_TBL_DAT_FECDESATR = 30;
    private static final int INT_TBL_DAT_OBSDESATR = 31;
    private static final int INT_TBL_DAT_BUTDESATR = 32;
    private static final int INT_TBL_DAT_COD_BOD = 33;
    private static final int INT_TBL_DAT_COD_BODGRP = 34;
    private static final int INT_TBL_DAT_COD_EMPGRP = 35;
    
    // TABLA DE BODEGAS
    private static final int INT_TBL_LIN = 0;
    private static final int INT_TBL_CHKBOD = 1;
    private static final int INT_TBL_CODBOD = 2;
    private static final int INT_TBL_NOMBOD = 3;
    
    //Variables 
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafSelFec objSelFec;
    private ZafTblMod objTblModBE, objTblModDT;
    private ZafTblCelRenChk objTblCelRenChkBE, ZafTblCelRenChkDat;
    private ZafTblCelEdiChk objTblCelEdiChkBE;
    private ZafTblCelRenBut objTblCelRenButDG;
    private ZafTblCelEdiButGen objTblCelEdiButGenDG;
    private ZafTblCelRenLbl objTblCelRenLblCol;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafThreadGUI objThrGUI;
    private ZafPerUsr objPerUsr;
    private ZafDtePckEdi objDtePckEdi;
    private ZafDatePicker txtFecDoc = null;
    private ZafVenCon vcoZonCiu;                                //Ventana de consulta.
    private ZafVenCon vcoCli;
    private Frame frmPadre;
    //private ZafCom78_02 objCom78_02;
    
    private Vector vecDat, vecReg;
    private boolean blnMarTodCanTblBod = true;
    private final Color colFonCol = new Color(255, 172, 165);
    private final Color colFonFalDan = new Color(255, 0, 0);
    private String strFormatoFecha = "y/m/d";
    private String DATE_FORMAT = "yyyy-MM-dd";
    private String strCodDest, strDesLarDest;
    private String strCodZon, strZon;
    private String strTit = "Mensaje del Sistema Zafiro.";
    
    private String strSQL="";
    private String strVersion = " v1.19 ";
    
    /**
     * Creates new form ZafCom78
     */
    public ZafCom78(ZafParSis objZafParsis) 
    {
        try 
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) objZafParsis.clone();
            objUti = new ZafUtil();

            objTblCelRenLblCol = new ZafTblCelRenLbl();
            ZafTblCelRenChkDat = new ZafTblCelRenChk();
            objTblCelRenLbl = new ZafTblCelRenLbl();

            objDtePckEdi = new ZafDtePckEdi(strFormatoFecha);
            txtFecDoc = new ZafDatePicker(((JFrame) this.getParent()), strFormatoFecha);
            //txtFecDoc = new ZafDatePicker(((JFrame)jfrThis.getParent()),"d/m/y"); 
            objPerUsr = new ZafPerUsr(objParSis);

            initComponents();

            vecDat = new Vector();

            //Configurar ZafSelFec:
            objSelFec = new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(false);

            panFilCab.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 68);
            objSelFec.setFlechaDerechaSeleccionada(true);
            objSelFec.setFlechaIzquierdaSeleccionada(true);

            frmPadre = JOptionPane.getFrameForComponent(this);
            this.setTitle(objParSis.getNombreMenu() + " " + strVersion);
            lblTit.setText(objParSis.getNombreMenu());

        } 
        catch (CloneNotSupportedException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    public void Configura_ventana_consulta() 
    {
        configurarFormBE();
        configurarFormDT();
        cargarBod();
        configurarVentanaPermisos();
    }

    private boolean configurarFormDT() 
    {
        boolean blnres = false;

        Vector vecCab = new Vector();    //Almacena las cabeceras
        vecCab.clear();
        vecCab.add(INT_TBL_DAT_LIN, "");
        vecCab.add(INT_TBL_DAT_CODSEG, "Cód.Seg.");
        vecCab.add(INT_TBL_DAT_CODEMP, "Cód.Emp");
        vecCab.add(INT_TBL_DAT_NOMEMP, "Empresa"); 
        vecCab.add(INT_TBL_DAT_CODLOC, "Cód.Loc");
        vecCab.add(INT_TBL_DAT_NOMLOC, "Local");
        vecCab.add(INT_TBL_DAT_CODTID, "Cód.Tip.Doc");
        vecCab.add(INT_TBL_DAT_DESTIDC, "Des.Tip.Doc");
        vecCab.add(INT_TBL_DAT_CODDOC, "Cód.Doc");
        vecCab.add(INT_TBL_DAT_NUMDOC, "Núm.O.D.");
        vecCab.add(INT_TBL_DAT_MOT_TRA, "Mot.Tra.");
        vecCab.add(INT_TBL_DAT_FECDOC, "Fec.Doc");
        vecCab.add(INT_TBL_DAT_TXDESTI, "Destinatario");
        vecCab.add(INT_TBL_DAT_PTOLLE, "Destino.");
        vecCab.add(INT_TBL_DAT_ZONCIU, "Zona."); 
        vecCab.add(INT_TBL_DAT_FORRET, "For.Ret");
        vecCab.add(INT_TBL_DAT_PERRET, "Per.Ret"); 
        vecCab.add(INT_TBL_DAT_PESO, "Peso");
        vecCab.add(INT_TBL_DAT_BUTOD, "..");
        vecCab.add(INT_TBL_DAT_FECING, "Fec.Ing."); 
        vecCab.add(INT_TBL_DAT_STCONPEN, "Pendiente");
        vecCab.add(INT_TBL_DAT_STCONPAR, "Parcial");
        vecCab.add(INT_TBL_DAT_STCONTOT, "Total");
        vecCab.add(INT_TBL_DAT_BUTCONF, "..");
        //vecCab.add(INT_TBL_DAT_STFALSTK,"Fal.Stk");
        vecCab.add(INT_TBL_DAT_BUTGUIA, "Gui. Rem.");
        vecCab.add(INT_TBL_DAT_ESTDIA, "");
        vecCab.add(INT_TBL_DAT_STFALSTK, "Faltantes");
        vecCab.add(INT_TBL_DAT_STDANIADOS, "Dañados");
        vecCab.add(INT_TBL_DAT_BUTFAL, "..");
        vecCab.add(INT_TBL_DAT_DIADESATR, "Dias.Ant.");
        vecCab.add(INT_TBL_DAT_FECDESATR, "Fec.Acor.");
        vecCab.add(INT_TBL_DAT_OBSDESATR, "Motivo");
        vecCab.add(INT_TBL_DAT_BUTDESATR, "..");
        vecCab.add(INT_TBL_DAT_COD_BOD, "Cod.Bod");
        vecCab.add(INT_TBL_DAT_COD_BODGRP, "Cod.Bod.Grp");
        vecCab.add(INT_TBL_DAT_COD_EMPGRP, "Cod.Emp.Grp");

        objTblModDT = new ZafTblMod();
        objTblModDT.setHeader(vecCab);
        tblDat.setModel(objTblModDT);

        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        objTblModDT.setColumnDataType(INT_TBL_DAT_NUMDOC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblModDT.setColumnDataType(INT_TBL_DAT_PESO, ZafTblMod.INT_COL_DBL, new Integer(0), null);

        //Configurar JTable: Establecer la fila de cabecera.
        ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat, INT_TBL_DAT_LIN);

        //Configurar JTable: Editor de búsqueda.
        ZafTblBus zafTblBus = new ZafTblBus(tblDat);
        ZafTblOrd zafTblOrd = new ZafTblOrd(tblDat);
        ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDat);
        ZafMouMotAda objMouMotAda = new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

        tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tcmAux = tblDat.getColumnModel();

        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_DAT_CODSEG).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DAT_NOMEMP).setPreferredWidth(85); 
        tcmAux.getColumn(INT_TBL_DAT_NOMLOC).setPreferredWidth(50); 
        tcmAux.getColumn(INT_TBL_DAT_DESTIDC).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_DAT_NUMDOC).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DAT_MOT_TRA).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DAT_FECDOC).setPreferredWidth(85);
        tcmAux.getColumn(INT_TBL_DAT_TXDESTI).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_DAT_PTOLLE).setPreferredWidth(180);
        tcmAux.getColumn(INT_TBL_DAT_ZONCIU).setPreferredWidth(100); 
        tcmAux.getColumn(INT_TBL_DAT_FORRET).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_DAT_PERRET).setPreferredWidth(80); 
        tcmAux.getColumn(INT_TBL_DAT_PESO).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DAT_BUTOD).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_DAT_FECING).setPreferredWidth(95);
        tcmAux.getColumn(INT_TBL_DAT_STCONPEN).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DAT_STCONPAR).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DAT_STCONTOT).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DAT_BUTCONF).setPreferredWidth(20);
        //tcmAux.getColumn(INT_TBL_DAT_STFALSTK).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DAT_BUTGUIA).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DAT_STFALSTK).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DAT_STDANIADOS).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DAT_BUTFAL).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_DAT_DIADESATR).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DAT_FECDESATR).setPreferredWidth(85);
        tcmAux.getColumn(INT_TBL_DAT_OBSDESATR).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_DAT_BUTDESATR).setPreferredWidth(20);

        ArrayList arlColHid = new ArrayList();
        arlColHid.add("" + INT_TBL_DAT_CODEMP);
        arlColHid.add("" + INT_TBL_DAT_CODLOC);
        arlColHid.add("" + INT_TBL_DAT_CODTID);
        arlColHid.add("" + INT_TBL_DAT_CODDOC);
        arlColHid.add("" + INT_TBL_DAT_ESTDIA);
        arlColHid.add("" + INT_TBL_DAT_COD_BOD);
        arlColHid.add("" + INT_TBL_DAT_COD_BODGRP);
        arlColHid.add("" + INT_TBL_DAT_COD_EMPGRP);
        objTblModDT.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid = null;

        tcmAux.getColumn(INT_TBL_DAT_CODSEG).setCellRenderer(objTblCelRenLblCol); 
        tcmAux.getColumn(INT_TBL_DAT_NOMEMP).setCellRenderer(objTblCelRenLblCol); 
        tcmAux.getColumn(INT_TBL_DAT_NOMLOC).setCellRenderer(objTblCelRenLblCol); 
        tcmAux.getColumn(INT_TBL_DAT_DESTIDC).setCellRenderer(objTblCelRenLblCol);
        tcmAux.getColumn(INT_TBL_DAT_NUMDOC).setCellRenderer(objTblCelRenLblCol);
        tcmAux.getColumn(INT_TBL_DAT_MOT_TRA).setCellRenderer(objTblCelRenLblCol);
        tcmAux.getColumn(INT_TBL_DAT_FECDOC).setCellRenderer(objTblCelRenLblCol);
        tcmAux.getColumn(INT_TBL_DAT_TXDESTI).setCellRenderer(objTblCelRenLblCol);
        tcmAux.getColumn(INT_TBL_DAT_PTOLLE).setCellRenderer(objTblCelRenLblCol);
        tcmAux.getColumn(INT_TBL_DAT_ZONCIU).setCellRenderer(objTblCelRenLblCol); 
        tcmAux.getColumn(INT_TBL_DAT_FORRET).setCellRenderer(objTblCelRenLblCol);
        tcmAux.getColumn(INT_TBL_DAT_PERRET).setCellRenderer(objTblCelRenLblCol); 

        objTblCelRenLblCol.addTblCelRenListener(new ZafTblCelRenAdapter() {
            @Override
            public void beforeRender(ZafTblCelRenEvent evt) {
                //Mostrar de color gris las columnas impares.

                int intCell = objTblCelRenLblCol.getRowRender();

                String str = tblDat.getValueAt(intCell, INT_TBL_DAT_ESTDIA).toString();

                if (str.equals("S")) {
                    objTblCelRenLblCol.setBackground(colFonCol);
                    objTblCelRenLblCol.setForeground(Color.BLACK);
                } else {
                    objTblCelRenLblCol.setBackground(Color.WHITE);
                    objTblCelRenLblCol.setForeground(Color.BLACK);
                }

                if (tblDat.getValueAt(intCell, INT_TBL_DAT_STFALSTK).toString().equals("true") || tblDat.getValueAt(intCell, INT_TBL_DAT_STDANIADOS).toString().equals("true")) {
                    objTblCelRenLblCol.setBackground(colFonFalDan);
                    objTblCelRenLblCol.setForeground(Color.BLACK);
                }

            }
        });

        objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
        tcmAux.getColumn(INT_TBL_DAT_PESO).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl.addTblCelRenListener(new ZafTblCelRenAdapter() {
            @Override
            public void beforeRender(ZafTblCelRenEvent evt) {
                //Mostrar de color gris las columnas impares.

                int intCell = objTblCelRenLbl.getRowRender();

                String str = tblDat.getValueAt(intCell, INT_TBL_DAT_ESTDIA).toString();

                if (str.equals("S")) {
                    objTblCelRenLbl.setBackground(colFonCol);
                    objTblCelRenLbl.setForeground(Color.BLACK);
                } else {
                    objTblCelRenLbl.setBackground(Color.WHITE);
                    objTblCelRenLbl.setForeground(Color.BLACK);
                }

                if (tblDat.getValueAt(intCell, INT_TBL_DAT_STFALSTK).toString().equals("true") || tblDat.getValueAt(intCell, INT_TBL_DAT_STDANIADOS).toString().equals("true")) {
                    objTblCelRenLbl.setBackground(colFonFalDan);
                    objTblCelRenLbl.setForeground(Color.BLACK);
                }

            }
        });

        ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16 * 2);

        ZafTblHeaColGrp objTblHeaColGrp = new ZafTblHeaColGrp(" DATOS DE LA ORDEN DE DESPACHO ");
        objTblHeaColGrp.setHeight(16);
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_CODEMP));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_NOMEMP));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_CODLOC));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_NOMLOC));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_CODTID));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_DESTIDC));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_CODDOC));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_NUMDOC));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_MOT_TRA));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_FECDOC));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_TXDESTI));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_PTOLLE));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_ZONCIU));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_FORRET));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_PERRET));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_PESO));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BUTOD));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_FECING));
        objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
        objTblHeaColGrp = null;

        objTblHeaColGrp = new ZafTblHeaColGrp(" CONFIRMACION ");
        objTblHeaColGrp.setHeight(16);
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_STCONPEN));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_STCONPAR));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_STCONTOT));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BUTCONF));
        //objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_STFALSTK));
        objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
        objTblHeaColGrp = null;

        objTblHeaColGrp = new ZafTblHeaColGrp(" GUÍAS DE REMISION ");
        objTblHeaColGrp.setHeight(16);
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BUTGUIA));
        objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
        objTblHeaColGrp = null;

        objTblHeaColGrp = new ZafTblHeaColGrp(" FALTANTES / DAÑADOS ");
        objTblHeaColGrp.setHeight(16);
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_STFALSTK));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_STDANIADOS));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BUTFAL));
        objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
        objTblHeaColGrp = null;

        objTblHeaColGrp = new ZafTblHeaColGrp(" DESPACHOS ATRASADOS ");
        objTblHeaColGrp.setHeight(16);
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_DIADESATR));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_FECDESATR));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_OBSDESATR));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BUTDESATR));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_BOD));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_BODGRP));
        objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_EMPGRP));
        objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
        objTblHeaColGrp = null;

        //Configurar JTable: Establecer columnas editables.
        /*Vector vecAux=new Vector();
         vecAux.add("" + INT_TBL_DAT_BUTGUIA);
         vecAux.add("" + INT_TBL_DAT_BUTCONF);
         vecAux.add("" + INT_TBL_DAT_BUTOD);
         vecAux.add("" + INT_TBL_DAT_BUTFAL);
         //vecAux.add("" + INT_TBL_DAT_FECDESATR);
         //vecAux.add("" + INT_TBL_DAT_BUTDESATR);
         objTblModDT.setColumnasEditables(vecAux);
         vecAux=null;*/

        //Configurar JTable: Editor de la tabla.
        ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);

        tblDat.getTableHeader().setReorderingAllowed(false);

        tcmAux.getColumn(INT_TBL_DAT_STCONPEN).setCellRenderer(ZafTblCelRenChkDat);
        tcmAux.getColumn(INT_TBL_DAT_STCONPAR).setCellRenderer(ZafTblCelRenChkDat);
        tcmAux.getColumn(INT_TBL_DAT_STCONTOT).setCellRenderer(ZafTblCelRenChkDat);
        tcmAux.getColumn(INT_TBL_DAT_STFALSTK).setCellRenderer(ZafTblCelRenChkDat);
        tcmAux.getColumn(INT_TBL_DAT_STDANIADOS).setCellRenderer(ZafTblCelRenChkDat);

        ZafTblCelRenChkDat.addTblCelRenListener(new ZafTblCelRenAdapter() {
            @Override
            public void beforeRender(ZafTblCelRenEvent evt) {
                //Mostrar de color gris las columnas impares.

                int intCell = ZafTblCelRenChkDat.getRowRender();

                String str = tblDat.getValueAt(intCell, INT_TBL_DAT_ESTDIA).toString();

                if (str.equals("S")) {
                    ZafTblCelRenChkDat.setBackground(colFonCol);
                    ZafTblCelRenChkDat.setForeground(Color.BLACK);
                } else {
                    ZafTblCelRenChkDat.setBackground(Color.WHITE);
                    ZafTblCelRenChkDat.setForeground(Color.BLACK);
                }

                if (tblDat.getValueAt(intCell, INT_TBL_DAT_STFALSTK).toString().equals("true") || tblDat.getValueAt(intCell, INT_TBL_DAT_STDANIADOS).toString().equals("true")) {
                    ZafTblCelRenChkDat.setBackground(colFonFalDan);
                    ZafTblCelRenChkDat.setForeground(Color.BLACK);
                }
            }
        });

        objTblCelRenButDG = new ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBL_DAT_BUTGUIA).setCellRenderer(objTblCelRenButDG);
        tcmAux.getColumn(INT_TBL_DAT_BUTCONF).setCellRenderer(objTblCelRenButDG);
        tcmAux.getColumn(INT_TBL_DAT_BUTOD).setCellRenderer(objTblCelRenButDG);
        tcmAux.getColumn(INT_TBL_DAT_BUTFAL).setCellRenderer(objTblCelRenButDG);
        tcmAux.getColumn(INT_TBL_DAT_BUTDESATR).setCellRenderer(objTblCelRenButDG);

        objTblCelRenButDG.addTblCelRenListener(new ZafTblCelRenAdapter() {
            int intFilSel, intColSel;

            @Override
            public void beforeRender(ZafTblCelRenEvent evt) {
                intColSel = objTblCelRenButDG.getColumnRender();
                switch (intColSel) {

                    case INT_TBL_DAT_BUTOD:
                        objTblCelRenButDG.setText("...");
                        break;

                    case INT_TBL_DAT_BUTCONF:
                        //  objTblCelRenButDG.setText("...");

                        if (objTblModDT.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL_DAT_STCONPEN).toString().equals("true")) {
                            objTblCelRenButDG.setText("");
                        } else {
                            objTblCelRenButDG.setText("...");
                        }

                        break;

                    case INT_TBL_DAT_BUTGUIA:

                        if (objTblModDT.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL_DAT_STCONPEN).toString().equals("true")) {
                            objTblCelRenButDG.setText("");
                        } else {
                            objTblCelRenButDG.setText("...");
                        }

                        break;

                    case INT_TBL_DAT_BUTFAL:
                        if (objTblModDT.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL_DAT_STFALSTK).toString().equals("true") || objTblModDT.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL_DAT_STDANIADOS).toString().equals("true")) {
                            objTblCelRenButDG.setText("...");
                        } else {
                            objTblCelRenButDG.setText("");
                        }
                        break;
                }
            }
        });

        //Configurar JTable: Editor de celdas.
        objTblCelEdiButGenDG = new ZafTblCelEdiButGen();
        tcmAux.getColumn(INT_TBL_DAT_BUTGUIA).setCellEditor(objTblCelEdiButGenDG);
        tcmAux.getColumn(INT_TBL_DAT_BUTCONF).setCellEditor(objTblCelEdiButGenDG);
        tcmAux.getColumn(INT_TBL_DAT_BUTOD).setCellEditor(objTblCelEdiButGenDG);
        tcmAux.getColumn(INT_TBL_DAT_BUTFAL).setCellEditor(objTblCelEdiButGenDG);
        tcmAux.getColumn(INT_TBL_DAT_BUTDESATR).setCellEditor(objTblCelEdiButGenDG);

        objTblCelEdiButGenDG.addTableEditorListener(new ZafTableAdapter() {
            int intFilSel, intColSel;

            @Override
            public void beforeEdit(ZafTableEvent evt) {
                intFilSel = tblDat.getSelectedRow();
                intColSel = tblDat.getSelectedColumn();
                if (intFilSel != -1) {
                    switch (intColSel) {
                        case INT_TBL_DAT_BUTOD:

                            break;


                        case INT_TBL_DAT_BUTCONF:

                            if (objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_STCONPEN).toString().equals("true")) {
                                objTblCelEdiButGenDG.setCancelarEdicion(true);
                            }


                            break;

                        case INT_TBL_DAT_BUTGUIA:

                            if (objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_STCONPEN).toString().equals("true")) {
                                objTblCelEdiButGenDG.setCancelarEdicion(true);
                            }


                            break;
                        case INT_TBL_DAT_BUTFAL:
                            if (objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_STCONTOT).toString().equals("true")) {
                                objTblCelEdiButGenDG.setCancelarEdicion(true);
                            }
                            break;
                        case INT_TBL_DAT_BUTDESATR:
                            if (objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_STCONTOT).toString().equals("true")) {
                                objTblCelEdiButGenDG.setCancelarEdicion(true);
                            }
                            break;

                    }
                }
            }

            @Override
            public void afterEdit(ZafTableEvent evt) {
                intColSel = tblDat.getSelectedColumn();
                if (intFilSel != -1) {
                    switch (intColSel) {

                        case INT_TBL_DAT_BUTOD:

                            llamarOD(objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString(), objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString(), objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_CODTID).toString(), objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString());

                            break;


                        case INT_TBL_DAT_BUTGUIA:

                            if (objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_STCONPEN).toString().equals("false")) {

                                mostrarGuiasRemision(objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString(), objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString(), objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_CODTID).toString(), objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString());
                            }
                            break;

                        case INT_TBL_DAT_BUTCONF:

                            if (objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_STCONPEN).toString().equals("false")) {

                                llamarVtaConf(objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString(), objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString(), objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_CODTID).toString(), objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString());
                            }

                            break;
                        case INT_TBL_DAT_BUTFAL:
                            if (objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_STCONTOT).toString().equals("false")) {
                                ZafCom78_02 objCom78_02 = new ZafCom78_02(JOptionPane.getFrameForComponent(ZafCom78.this), true, objParSis);
                                objCom78_02.setParams(Integer.parseInt(objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString()),
                                        Integer.parseInt(objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString()),
                                        Integer.parseInt(objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_CODTID).toString()),
                                        Integer.parseInt(objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString()),
                                        Integer.parseInt(objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_COD_BOD).toString()),
                                        Integer.parseInt(objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_COD_BODGRP).toString()),
                                        ZafCom78.this);
                                if (objCom78_02.cargarReg()) {
                                    objCom78_02.setVisible(true);
                                }

                                objCom78_02 = null;
                            }
                            break;

                        case INT_TBL_DAT_BUTDESATR:
                            if (objTblModDT.getValueAt(intFilSel, INT_TBL_DAT_STCONTOT).toString().equals("false")) {
                                String strObs = (tblDat.getValueAt(intFilSel, INT_TBL_DAT_OBSDESATR) == null ? "" : tblDat.getValueAt(intFilSel, INT_TBL_DAT_OBSDESATR).toString());
                                ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafCom78.this), true, strObs);
                                zafMae07_01.setVisible(true);
                                if (zafMae07_01.getAceptar()) {
                                    tblDat.setValueAt(zafMae07_01.getObser(), intFilSel, INT_TBL_DAT_OBSDESATR);
                                }
                                zafMae07_01 = null;
                            }
                            break;
                    }
                }
            }
        });

        tcmAux.getColumn(INT_TBL_DAT_FECDESATR).setCellEditor(objDtePckEdi);
        objDtePckEdi.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingCanceled(ChangeEvent evt) {
            }

            @Override
            public void editingStopped(ChangeEvent evt) {
            }
        });

        objTblModDT.setModoOperacion(ZafTblMod.INT_TBL_EDI);

        return blnres;
    }
    
    /**
     * Tooltips de las columnas.
     */
    private class ZafMouMotAda extends MouseMotionAdapter
    {
        @Override
        public void mouseMoved(MouseEvent evt) 
        {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) 
            {
                case INT_TBL_DAT_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_DAT_CODSEG:
                    strMsg = "Código de seguimiento";
                    break;
                case INT_TBL_DAT_CODEMP:
                    strMsg = "Código de empresa";
                    break;
                case INT_TBL_DAT_NOMEMP:
                    strMsg = "Nombre de empresa";
                    break;
                case INT_TBL_DAT_CODLOC:
                    strMsg = "Código de local ";
                    break;
                case INT_TBL_DAT_NOMLOC:
                    strMsg = "Nombre de local";
                    break;                    
                case INT_TBL_DAT_CODTID:
                    strMsg = "Tipo de Documento ";
                    break;
                case INT_TBL_DAT_DESTIDC:
                    strMsg = "Descripción corta del tipo de Documento ";
                    break;
                case INT_TBL_DAT_NUMDOC:
                    strMsg = "Número de Documento ";
                    break;
                case INT_TBL_DAT_MOT_TRA:
                    strMsg = "Motivo por el que se realizó la transacción ";
                    break;
                case INT_TBL_DAT_FECDOC:
                    strMsg = "Fecha de Documento ";
                    break;
                case INT_TBL_DAT_TXDESTI:
                    strMsg = "Destinatario. ";
                    break;
                case INT_TBL_DAT_PTOLLE:
                    strMsg = "Destino( punto de llegada ) ";
                    break;
                case INT_TBL_DAT_ZONCIU:
                    strMsg = "Zona de la ciudad ";
                    break;    
                case INT_TBL_DAT_FORRET:
                    strMsg = "Forma de retiro ";
                    break;
                case INT_TBL_DAT_PERRET:
                    strMsg = "Persona que retira ";
                    break;    
                case INT_TBL_DAT_PESO:
                    strMsg = "Peso (KG). ";
                    break;
                case INT_TBL_DAT_BUTOD:
                    strMsg = "Muestra la orden de despacho ";
                    break;
                case INT_TBL_DAT_FECING:
                    strMsg = "Fecha de ingreso";
                    break;                    
                case INT_TBL_DAT_STCONPEN:
                    strMsg = "Confirmacion Pendiente.? ";
                    break;
                case INT_TBL_DAT_STCONPAR:
                    strMsg = "Confirmacion parcial ";
                    break;
                case INT_TBL_DAT_STCONTOT:
                    strMsg = "Confirmacion total ";
                    break;
                case INT_TBL_DAT_STFALSTK:
                    strMsg = "¿Hay faltante de stock?";
                    break;
                case INT_TBL_DAT_BUTCONF:
                    strMsg = "Muestra el listado de confirmación";
                    break;
                case INT_TBL_DAT_BUTGUIA:
                    strMsg = "Muestra la(s) guías de remisión ";
                    break;
                case INT_TBL_DAT_STDANIADOS:
                    strMsg = "¿Hay items dañados? ";
                    break;
                case INT_TBL_DAT_BUTFAL:
                    strMsg = "Muestra la notificacion de egresos con faltantes/dañados ";
                    break;
                case INT_TBL_DAT_DIADESATR:
                    strMsg = "Dias de antiguedad de la orden de despacho ";
                    break;
                case INT_TBL_DAT_FECDESATR:
                    strMsg = "Fecha acordada con el cliente para los despachos ";
                    break;
                case INT_TBL_DAT_OBSDESATR:
                    strMsg = "Motivo de los despachos atrasados ";
                    break;
                case INT_TBL_DAT_BUTDESATR:
                    strMsg = "Motivo de los despachos atrasados ";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }    
    
    /**
     * Esta función permite utilizar la "Ventana de Consulta-Destinatario" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConDest(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(0);
                    vcoCli.setVisible(true);
                    if (vcoCli.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                        txtCodDest.setText(vcoCli.getValueAt(1));
                        txtDesDest.setText(vcoCli.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a.co_clides", txtCodDest.getText())) {
                        txtCodDest.setText(vcoCli.getValueAt(1));
                        txtDesDest.setText(vcoCli.getValueAt(2));
                    } else {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodDest.setText(vcoCli.getValueAt(1));
                            txtDesDest.setText(vcoCli.getValueAt(2));
                        } else {
                            txtCodDest.setText(strCodDest);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a.tx_nomclides", txtDesDest.getText())) {
                        txtCodDest.setText(vcoCli.getValueAt(1));
                        txtDesDest.setText(vcoCli.getValueAt(2));
                    } else {
                        vcoCli.setCampoBusqueda(1);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodDest.setText(vcoCli.getValueAt(1));
                            txtDesDest.setText(vcoCli.getValueAt(2));
                        } else {
                            txtDesDest.setText(strDesLarDest);
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
     * Configuración de Ventana de Consulta Destinatario.
     * @return 
     */
    private boolean configurarVenConDest() 
    {
        boolean blnRes = true;
        String strSql;
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_clides");
            arlCam.add("a.tx_nomclides");

            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");

            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("400");

            strSql = " select distinct a.co_clides, trim(a.tx_nomclides) as tx_nomclides "
                    + " from tbm_cabguirem as a "
                    + " where co_emp=" + objParSis.getCodigoEmpresa() + " "
                    + " and co_loc=" + objParSis.getCodigoLocal() + " "
                    + " and co_tipdoc=( select co_tipdoc from tbm_cabtipdoc where co_emp=a.co_emp and co_loc=a.co_loc and co_tipdoc=a.co_tipdoc ) "
                    + " and ( ne_numdoc is null or ne_numdoc = 0 ) "
                    + " and st_reg='A' "
                    + " order by 2 ";

            //System.out.println("ZafCom78.configurarVenConDest: " + strSql);

            vcoCli = new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, "Listado de Destinatarios", strSql, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, JLabel.RIGHT);
            //vcoCli.setConfiguracionColumna(2, JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta-Zona Ciudad" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema. <BR>false: En el caso
     * contrario.
     */
    private boolean mostrarVenConZonCiu(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoZonCiu.setCampoBusqueda(2);
                    vcoZonCiu.setVisible(true);
                    if (vcoZonCiu.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                        txtCodZon.setText(vcoZonCiu.getValueAt(1));
                        txtZon.setText(vcoZonCiu.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoZonCiu.buscar("a.co_reg", txtCodZon.getText())) {
                        txtCodZon.setText(vcoZonCiu.getValueAt(1));
                        txtZon.setText(vcoZonCiu.getValueAt(2));
                    } else {
                        vcoZonCiu.setCampoBusqueda(0);
                        vcoZonCiu.setCriterio1(11);
                        vcoZonCiu.cargarDatos();
                        vcoZonCiu.setVisible(true);
                        if (vcoZonCiu.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodZon.setText(vcoZonCiu.getValueAt(1));
                            txtZon.setText(vcoZonCiu.getValueAt(2));
                        } else {
                            txtCodZon.setText(strCodZon);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoZonCiu.buscar("a.tx_deslar", txtZon.getText())) {
                        txtCodZon.setText(vcoZonCiu.getValueAt(1));
                        txtZon.setText(vcoZonCiu.getValueAt(2));
                    } else {
                        vcoZonCiu.setCampoBusqueda(1);
                        vcoZonCiu.setCriterio1(11);
                        vcoZonCiu.cargarDatos();
                        vcoZonCiu.setVisible(true);
                        if (vcoZonCiu.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodZon.setText(vcoZonCiu.getValueAt(1));
                            txtZon.setText(vcoZonCiu.getValueAt(2));
                        } else {
                            txtZon.setText(strZon);
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
     * Configuración de Ventana de Consulta Zona Ciudad.
     * @return 
     */
    private boolean configurarVenConZonCiu() 
    {
        boolean blnRes = true;
        try 
        {
            //  System.out.println("configurarVenConZonCiu");
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_reg");
            arlCam.add("a.tx_deslar");
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Zona.");
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
            String strSQL = "";
            strSQL = " SELECT a.co_reg, a.tx_deslar FROM tbm_var as a where a.co_grp=6 and a.st_reg='A' ORDER BY a.co_reg ";

            vcoZonCiu = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Zonas de la Ciudad", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Configura Permisos de Usuarios
     */
    private void configurarVentanaPermisos()
    {
        Vector vecAux = new Vector();
        vecAux.add("" + INT_TBL_DAT_BUTGUIA);
        vecAux.add("" + INT_TBL_DAT_BUTCONF);
        vecAux.add("" + INT_TBL_DAT_BUTOD);
        //vecAux.add("" + INT_TBL_DAT_BUTFAL);

        if (objPerUsr.isOpcionEnabled(3113)) {
            butGuardar.setEnabled(true);
        }

        if (objPerUsr.isOpcionEnabled(3114)) {
            vecAux.add("" + INT_TBL_DAT_FECDESATR);
        }

        if (objPerUsr.isOpcionEnabled(3115)) {
            vecAux.add("" + INT_TBL_DAT_BUTDESATR);
        }

        if (objPerUsr.isOpcionEnabled(3116) || objPerUsr.isOpcionEnabled(3117) || objPerUsr.isOpcionEnabled(3118) || objPerUsr.isOpcionEnabled(3119)) {
            vecAux.add("" + INT_TBL_DAT_BUTFAL);
        }

        if (!vecAux.isEmpty()) {
            objTblModDT.setColumnasEditables(vecAux);
            vecAux = null;
        }

    }    

    /**
     * Muestra ventana con listado de confirmaciones de la OD seleccionada.
     * @param strCodEmp
     * @param strCodLoc
     * @param strCodTipDoc
     * @param strCodDoc
     */
    private void llamarVtaConf(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) 
    {
        ZafCom78_01 obj1 = new ZafCom78_01(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }

    /**
     * Muestra ventana con listado de ordenes de despacho.
     * @param strCodEmp
     * @param strCodLoc
     * @param strCodTipDoc
     * @param strCodDoc
     */
    private void llamarOD(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) 
    {
        int intCodMnu=3497;
        ZafCom23 obj1 = new ZafCom23(objParSis, intCodMnu, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }

    /**
     * Muestra guias de remisión de la OD.
     * @param strCodEmp
     * @param strCodLoc
     * @param strCodTipDoc
     * @param strCodDoc 
     */
    private void mostrarGuiasRemision(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) 
    {
        String strSqlGR = ""; 
        try 
        {
            strSqlGR = "select a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, a5.tx_descor, a4.ne_numdoc, a4.fe_doc "
                    + " from tbr_detguirem as a2  "
                    + " inner join tbm_detguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc and a3.co_reg=a2.co_reg ) "
                    + " inner join tbm_cabguirem as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc  ) "
                    + " inner join tbm_cabtipdoc as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_loc and a5.co_tipdoc=a4.co_tipdoc) "
                    + " where a2.co_emp=" + strCodEmp + " and a2.co_locrel=" + strCodLoc + " and a2.co_tipdocrel=" + strCodTipDoc + " and a2.co_docrel=" + strCodDoc + " and a4.st_reg='A' "
                    + " group by a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, a5.tx_descor, a4.ne_numdoc, a4.fe_doc "
                    + " order by  a4.ne_numdoc ";

            ZafVen21_01 obj1 = new ZafVen21_01(objParSis, this, strSqlGR);
            this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj1.show();

        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /**
     * Carga el listado de bodegas.
     * @return 
     */
    private boolean cargarBod() 
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        boolean blnRes = true;
        String strSQL = "";
        try 
        {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement();

                //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                if (objParSis.getCodigoUsuario() == 1) 
                {
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL += " SELECT a2.co_bod, a2.tx_nom ";
                    strSQL += " FROM tbm_emp AS a1";
                    strSQL += " INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                    strSQL += " ORDER BY a1.co_emp, a2.co_bod";
                    rst = stm.executeQuery(strSQL);
                }
                else //Otros Usuarios
                {
                    //Armar la sentencia SQL.
                    strSQL  = " select  a1.co_bodgrp as co_bod, a2.tx_nom  from tbr_bodLocPrgUsr as a " ;
                    strSQL += " inner join tbr_bodEmpBodGrp as a1  on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod ) "; 
                    strSQL += " inner join tbm_bod as a2  on (a2.co_emp=a1.co_empgrp and a2.co_bod=a1.co_bodgrp ) ";
                    strSQL += " where a.co_emp=" + objParSis.getCodigoEmpresa() + " and a.co_loc=" + objParSis.getCodigoLocal() + " ";
                    strSQL += " and a.co_usr=" + objParSis.getCodigoUsuario() + " and  a.co_mnu=" + objParSis.getCodigoMenu() + " ";
                    strSQL += " and a.tx_natBod IN  ('E') ";
                    strSQL += " group by a1.co_bodgrp, a2.tx_nom  order by co_bodgrp ";

                    rst = stm.executeQuery(strSQL);
                }

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()) 
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_LIN, "");
                    vecReg.add(INT_TBL_CHKBOD, true);
                    vecReg.add(INT_TBL_CODBOD, rst.getString("co_bod"));
                    vecReg.add(INT_TBL_NOMBOD, rst.getString("tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                rst = null;
                stm = null;
                con.close();
                con = null;

                //Asignar vectores al modelo.
                objTblModBE.setData(vecDat);
                tblBod.setModel(objTblModBE);

                vecDat.clear();

            }
        } 
        catch (java.sql.SQLException e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarFormBE() 
    {
        boolean blnres = false;

        Vector vecCab = new Vector();    //Almacena las cabeceras
        vecCab.clear();

        vecCab.add(INT_TBL_LIN, "");
        vecCab.add(INT_TBL_CHKBOD, " ");
        vecCab.add(INT_TBL_CODBOD, "Cód.Bod");
        vecCab.add(INT_TBL_NOMBOD, "Nom.Bod");

        objTblModBE = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblModBE.setHeader(vecCab);
        tblBod.setModel(objTblModBE);

        //Configurar JTable: Establecer la fila de cabecera.
        ZafColNumerada zafColNumerada = new Librerias.ZafColNumerada.ZafColNumerada(tblBod, INT_TBL_LIN);

        tblBod.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux = tblBod.getColumnModel();
        tblBod.getTableHeader().setReorderingAllowed(false);
        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(380);
        tcmAux.getColumn(INT_TBL_CHKBOD).setPreferredWidth(60);

        //Configurar JTable: Establecer columnas editables.
        Vector vecAux = new Vector();
        vecAux.add("" + INT_TBL_CHKBOD);
        objTblModBE.setColumnasEditables(vecAux);
        vecAux = null;
        //Configurar JTable: Editor de la tabla.
        ZafTblEdi zafTblEdi = new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblBod);

        objTblCelRenChkBE = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_CHKBOD).setCellRenderer(objTblCelRenChkBE);
        objTblCelRenChkBE = null;

        objTblCelEdiChkBE = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_CHKBOD).setCellEditor(objTblCelEdiChkBE);
        objTblCelEdiChkBE.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            @Override
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            }

            @Override
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            }
        });

        //Configurar JTable: Establecer los listener para el TableHeader.
        tblBod.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatMouseClickedBE(evt);
            }
        });

        objTblModBE.setModoOperacion(ZafTblMod.INT_TBL_EDI);

        return blnres;
    }

    private void tblDatMouseClickedBE(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try 
        {
            intNumFil = tblBod.getRowCount();
            
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1 && tblBod.columnAtPoint(evt.getPoint()) == INT_TBL_CHKBOD)
            {
                if (blnMarTodCanTblBod) 
                {
                    //Mostrar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        tblBod.setValueAt(false, i, INT_TBL_CHKBOD);
                    }
                    blnMarTodCanTblBod = false;
                }
                else
                {
                    //Ocultar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        tblBod.setValueAt(true, i, INT_TBL_CHKBOD);
                    }
                    blnMarTodCanTblBod = true;
                }
            }

        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
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
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        spnFiltro = new javax.swing.JScrollPane();
        panFilCab = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        chkDocConfPar = new javax.swing.JCheckBox();
        chkDocConfTot = new javax.swing.JCheckBox();
        chkMosPenConf = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDesDest = new javax.swing.JTextField();
        txtCodDest = new javax.swing.JTextField();
        butDest = new javax.swing.JButton();
        lblZon = new javax.swing.JLabel();
        txtZon = new javax.swing.JTextField();
        txtCodZon = new javax.swing.JTextField();
        butZon = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
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
        butGuardar = new javax.swing.JButton();
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
                cerrar(evt);
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

        panFilCab.setAutoscrolls(true);
        panFilCab.setPreferredSize(new java.awt.Dimension(0, 400));
        panFilCab.setLayout(null);

        buttonGroup1.add(optTod);
        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        optTod.setBounds(10, 160, 330, 20);

        buttonGroup1.add(optFil);
        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
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
        panFilCab.add(optFil);
        optFil.setBounds(10, 180, 370, 20);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de bodega"));
        jPanel4.setPreferredSize(new java.awt.Dimension(294, 100));
        jPanel4.setRequestFocusEnabled(false);
        jPanel4.setLayout(new java.awt.BorderLayout());

        tblBod.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblBod);

        jPanel4.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        panFilCab.add(jPanel4);
        jPanel4.setBounds(10, 70, 630, 90);

        chkDocConfPar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkDocConfPar.setSelected(true);
        chkDocConfPar.setText("Mostrar los documentos que están confirmados parcialmente.");
        chkDocConfPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDocConfParActionPerformed(evt);
            }
        });
        panFilCab.add(chkDocConfPar);
        chkDocConfPar.setBounds(30, 220, 410, 20);

        chkDocConfTot.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkDocConfTot.setText("Mostrar los documentos que están confirmados totalmente.");
        chkDocConfTot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDocConfTotActionPerformed(evt);
            }
        });
        panFilCab.add(chkDocConfTot);
        chkDocConfTot.setBounds(30, 240, 410, 20);

        chkMosPenConf.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkMosPenConf.setSelected(true);
        chkMosPenConf.setText("Mostrar los documentos que están pendiente de confirmar.");
        chkMosPenConf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosPenConfActionPerformed(evt);
            }
        });
        panFilCab.add(chkMosPenConf);
        chkMosPenConf.setBounds(30, 200, 410, 20);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Número de orden de despacho"));
        jPanel3.setLayout(null);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        jPanel3.setBounds(30, 320, 500, 50);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel4.setText("Destinatario:");
        panFilCab.add(jLabel4);
        jLabel4.setBounds(30, 270, 70, 15);

        txtDesDest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesDestActionPerformed(evt);
            }
        });
        txtDesDest.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesDestFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesDestFocusLost(evt);
            }
        });
        panFilCab.add(txtDesDest);
        txtDesDest.setBounds(140, 270, 340, 20);

        txtCodDest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDestActionPerformed(evt);
            }
        });
        txtCodDest.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDestFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDestFocusLost(evt);
            }
        });
        panFilCab.add(txtCodDest);
        txtCodDest.setBounds(100, 270, 40, 20);

        butDest.setText("...");
        butDest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDestActionPerformed(evt);
            }
        });
        panFilCab.add(butDest);
        butDest.setBounds(490, 270, 20, 20);

        lblZon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblZon.setText("Zona:");
        panFilCab.add(lblZon);
        lblZon.setBounds(30, 290, 70, 15);

        txtZon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtZonActionPerformed(evt);
            }
        });
        txtZon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtZonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtZonFocusLost(evt);
            }
        });
        panFilCab.add(txtZon);
        txtZon.setBounds(140, 290, 340, 20);

        txtCodZon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodZonActionPerformed(evt);
            }
        });
        txtCodZon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodZonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodZonFocusLost(evt);
            }
        });
        panFilCab.add(txtCodZon);
        txtCodZon.setBounds(100, 290, 40, 20);

        butZon.setText("...");
        butZon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butZonActionPerformed(evt);
            }
        });
        panFilCab.add(butZon);
        butZon.setBounds(490, 290, 20, 20);

        spnFiltro.setViewportView(panFilCab);

        tabFrm.addTab("Filtro", spnFiltro);

        jPanel9.setLayout(new java.awt.BorderLayout());

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

        jPanel9.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", jPanel9);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);
        tabFrm.getAccessibleContext().setAccessibleName("Filtro");

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
        butGuardar.setToolTipText("");
        butGuardar.setEnabled(false);
        butGuardar.setPreferredSize(new java.awt.Dimension(92, 25));
        butGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuardarActionPerformed(evt);
            }
        });
        panBot.add(butGuardar);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

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
        setBounds((screenSize.width-700)/2, (screenSize.height-501)/2, 700, 501);
    }// </editor-fold>//GEN-END:initComponents

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected()) 
        {
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
            txtCodDest.setText("");
            txtDesDest.setText("");
            txtCodZon.setText("");
            txtZon.setText("");
        }
}//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        chkMosPenConf.setSelected(true);
        chkDocConfPar.setSelected(true);
        chkDocConfTot.setSelected(false);
        txtCodAltDes.setText("");
        txtCodAltHas.setText("");
        txtCodDest.setText("");
        txtDesDest.setText("");
        txtCodZon.setText("");
        txtZon.setText("");
}//GEN-LAST:event_optTodActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
}//GEN-LAST:event_optFilItemStateChanged

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_optFilActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        consultar();
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitform();
}//GEN-LAST:event_butCerActionPerformed

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
}//GEN-LAST:event_txtCodAltDesFocusGained

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length() > 0) {
            optFil.setSelected(true);
            if (txtCodAltHas.getText().length() == 0) {
                txtCodAltHas.setText(txtCodAltDes.getText());
            }
        }
}//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        txtCodAltHas.selectAll();
}//GEN-LAST:event_txtCodAltHasFocusGained

    private void cerrar(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_cerrar
        exitform();
    }//GEN-LAST:event_cerrar

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        Configura_ventana_consulta();
        configurarVenConDest();
        configurarVenConZonCiu();
    }//GEN-LAST:event_formInternalFrameOpened

    private void chkMosPenConfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosPenConfActionPerformed
        optFil.setSelected(true);
    }//GEN-LAST:event_chkMosPenConfActionPerformed

    private void chkDocConfParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDocConfParActionPerformed
        optFil.setSelected(true);
    }//GEN-LAST:event_chkDocConfParActionPerformed

    private void chkDocConfTotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDocConfTotActionPerformed
        optFil.setSelected(true);
    }//GEN-LAST:event_chkDocConfTotActionPerformed

    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        String strMsg = "¿Esta seguro que desea modificar la informacion?";
        if (JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) 
        {
            guardarDetReg();
        }

        if (objThrGUI == null) 
        {
            objThrGUI = new ZafThreadGUI();
            objThrGUI.start();
        }

    }//GEN-LAST:event_butGuardarActionPerformed

    private void txtDesDestFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesDestFocusGained
        strDesLarDest = txtDesDest.getText();
        txtDesDest.selectAll();
    }//GEN-LAST:event_txtDesDestFocusGained

    private void txtDesDestFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesDestFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesDest.getText().equalsIgnoreCase(strDesLarDest))
        {
            if (txtDesDest.getText().equals(""))
            {
                txtCodDest.setText("");
                txtDesDest.setText("");
            }
            else
            {
                mostrarVenConDest(2);
            }
        }
        else
            txtDesDest.setText(strDesLarDest);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtDesDest.getText().length()>0)
        {
            optFil.setSelected(true);
        }
       
    }//GEN-LAST:event_txtDesDestFocusLost

    private void txtCodDestFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDestFocusGained
        strCodDest = txtCodDest.getText();
        txtCodDest.selectAll();
    }//GEN-LAST:event_txtCodDestFocusGained

    private void txtCodDestFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDestFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodDest.getText().equalsIgnoreCase(strCodDest))
        {
            if (txtCodDest.getText().equals(""))
            {
                txtCodDest.setText("");
                txtDesDest.setText("");
            }
            else
            {
                mostrarVenConDest(1);
            }
        }
        else
            txtCodDest.setText(strCodDest);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodDest.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodDestFocusLost

    private void butDestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDestActionPerformed
        mostrarVenConDest(0);
    }//GEN-LAST:event_butDestActionPerformed

    private void txtCodDestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDestActionPerformed
        txtCodDest.transferFocus();
    }//GEN-LAST:event_txtCodDestActionPerformed

    private void txtDesDestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesDestActionPerformed
        txtDesDest.transferFocus();
    }//GEN-LAST:event_txtDesDestActionPerformed

    private void txtZonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtZonActionPerformed
        txtZon.transferFocus();
    }//GEN-LAST:event_txtZonActionPerformed

    private void txtZonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtZonFocusGained
        strZon = txtZon.getText();
        txtZon.selectAll();
    }//GEN-LAST:event_txtZonFocusGained

    private void txtZonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtZonFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtZon.getText().equalsIgnoreCase(strZon))
        {
            if (txtZon.getText().equals(""))
            {
                txtCodZon.setText("");
                txtZon.setText("");
            }
            else
            {
                mostrarVenConZonCiu(2);
            }
        }
        else
            txtZon.setText(strZon);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtZon.getText().length()>0)
        {
            optFil.setSelected(true);
        }
        
    }//GEN-LAST:event_txtZonFocusLost

    private void txtCodZonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodZonActionPerformed
        txtCodZon.transferFocus();
    }//GEN-LAST:event_txtCodZonActionPerformed

    private void txtCodZonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodZonFocusGained
        strCodZon = txtCodZon.getText();
        txtCodZon.selectAll();
    }//GEN-LAST:event_txtCodZonFocusGained

    private void txtCodZonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodZonFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodZon.getText().equalsIgnoreCase(strCodZon))
        {
            if (txtCodZon.getText().equals(""))
            {
                txtCodZon.setText("");
                txtZon.setText("");
            }
            else
            {
                mostrarVenConZonCiu(1);
            }
        }
        else
            txtCodZon.setText(strCodZon);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodZon.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodZonFocusLost

    private void butZonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butZonActionPerformed
        mostrarVenConZonCiu(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodZon.getText().length() > 0) {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butZonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butDest;
    private javax.swing.JButton butGuardar;
    private javax.swing.JButton butZon;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkDocConfPar;
    private javax.swing.JCheckBox chkDocConfTot;
    private javax.swing.JCheckBox chkMosPenConf;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblZon;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFilCab;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnFiltro;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodDest;
    private javax.swing.JTextField txtCodZon;
    private javax.swing.JTextField txtDesDest;
    private javax.swing.JTextField txtZon;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
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

    private void exitform() 
    {
        String strMsg;
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) 
        {
            Runtime.getRuntime().gc();
            dispose();
        }
    }
    
    /**
     * Función que realiza la consulta de las Ordenes de Despachos.
     */
    public void consultar() 
    {
        if (isCamVal()) 
        {
            //if(actualizaEstadoConInv())
            //{
                if (butCon.getText().equals("Consultar"))
                {
                    if (objThrGUI == null) 
                    {
                        objThrGUI = new ZafThreadGUI();
                        objThrGUI.start();
                    }
                }
            //}
        }
    }

    private class ZafThreadGUI extends Thread 
    {
        @Override
        public void run() 
        {
            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);

            if (!cargarDetReg(sqlConFil())) 
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }

            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount() > 0) 
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI = null;

            pgrSis.setValue(0);
            pgrSis.setIndeterminate(false);

        }
    }
   
    private boolean isCamVal() 
    {
        boolean blnRes = true;
        int intEstBod = 0;

        for (int x = 0; x < tblBod.getRowCount(); x++) 
        {
            if (tblBod.getValueAt(x, INT_TBL_CHKBOD).toString().equals("true")) {
                intEstBod = 1;
            }
        }

        if (intEstBod == 0)
        {
            mostrarMsgInf("LA BODEGA ORIGEN ES OBLIGATORIO...");
            return false;
        }

        return blnRes;
    }    

    private String sqlConFil() 
    {
        String sqlFil = "";
        String strBodDes = "";
        
        //Rango de Fechas.
        switch (objSelFec.getTipoSeleccion()) 
        {
            case 0: //Búsqueda por rangos
                sqlFil += " and a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' and '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                break;
            case 1: //Fechas menores o iguales que "Hasta".
                sqlFil += " and a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                break;
            case 2: //Fechas mayores o iguales que "Desde".
                sqlFil += " and a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                break;
            case 3: //Todo.
                break;
        }

        //<editor-fold defaultstate="collapsed" desc="/* Filtro:  Bodegas (Punto de Partida). */ ">
        for (int x = 0; x < tblBod.getRowCount(); x++) 
        {
            if (tblBod.getValueAt(x, INT_TBL_CHKBOD).toString().equals("true"))
            {
                if (!strBodDes.equals("")) {
                    strBodDes += ",";
                }
                strBodDes += tblBod.getValueAt(x, INT_TBL_CODBOD).toString();
            }
        }

        sqlFil += " and ( a6.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " and a6.co_bodGrp in ( " + strBodDes + " ) ) ";
        //</editor-fold>
        
        //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Número OD. */">
        if (optFil.isSelected()) 
        {
            if (txtCodAltDes.getText().length() > 0 || txtCodAltHas.getText().length() > 0) 
            {
                sqlFil += " and a.ne_numorddes BETWEEN " + txtCodAltDes.getText() + " and " + txtCodAltHas.getText() + "  ";
            }
        }
        //</editor-fold>
        
        //Filtro: Destinatario OD.
        if (!txtCodDest.getText().equals("")) 
        {
            sqlFil += " and a.co_clides = " + objUti.codificar(txtCodDest.getText()) + " ";
            //sqlFil += " and a.tx_nomclides like '%" + objUti.codificar(txtDesDest.getText()) + "%' ";
        }
        //Filtro: Zona de la Ciudad OD.
        if (!txtCodZon.getText().equals(""))
        {
            sqlFil += " and c.co_zon = " + objUti.codificar(txtCodZon.getText()) + " ";
        }
        return sqlFil;
    }
    
    /**
     * Función que presenta el detalle de las ordenes de despachos
     * @param strFil Filtros adicionales para presentar la consulta
     * @return
     */
    private boolean cargarDetReg(String strFil) 
    {
        boolean blnRes = true;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        //String strSQL = "";
        String strAux = "";
        String strConInv="";
        try 
        {
            butCon.setText("Detener");
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null)
            {
                stmLoc = conn.createStatement();
                
                //<editor-fold defaultstate="collapsed" desc="/* Filtro: Estado de confirmación de la OD. */">
                //OD Confirmación Pendiente.
                if (chkMosPenConf.isSelected()) 
                {
                    if (strAux.equals("")) {
                        strAux += "'P'";
                    } else {
                        strAux += ",'P'";
                    }
                }
                //OD Confirmación Parcial.
                if (chkDocConfPar.isSelected()) 
                {
                    if (strAux.equals("")) {
                        strAux += "'E'";
                    } else {
                        strAux += ",'E'";
                    }
                }
                //OD Confirmación Total.
                if (chkDocConfTot.isSelected()) 
                {
                    if (strAux.equals("")) {
                        strAux += "'C','F'";
                    } else {
                        strAux += ",'C','F'";
                    }
                }
                //Filtro
                if (!strAux.equals("")) 
                {
                    strConInv = " y.EstConInv IN (" + strAux + ") ";
                }
                else
                {
                    strConInv = " y.EstConInv IN ('P', 'C', 'E', 'F') ";
                }
                //</editor-fold>

                strSQL ="";
                strSQL+=" SELECT Seg.co_Seg, * FROM ( ";
                strSQL+=" SELECT * ";
                strSQL+="      ,CASE WHEN EstConInv = 'C' THEN null ELSE (current_date - fe_doc) END AS dias_doc ";
                strSQL+="      ,CASE WHEN abs( fe_doc - current_date ) > 14 THEN case when EstConInv in ('P','E') then 'S' else 'N' end else 'N' END AS diatranOD  ";
                strSQL+=" FROM  ";
                strSQL+=" ( ";
                strSQL+=" 	SELECT z.*,  ";
                strSQL+=" 	(   ";            
                strSQL+=" 	     select sum(( x1.nd_pesitmkgr*abs(x.nd_can))) as kgramo  ";             
                strSQL+=" 	     from tbm_detguirem as x ";         
                strSQL+=" 	     inner join tbm_inv as x1 on (x1.co_emp=x.co_emp and x1.co_itm=x.co_itm) ";             
                strSQL+=" 	     where x.co_emp=z.co_emp and x.co_loc=z.co_loc and x.co_tipdoc=z.co_tipdoc and x.co_doc=  z.co_doc ";           
                strSQL+=" 	) as kgramo, ";
                strSQL+=" 	CASE WHEN z.co_tipDocRel IN(1,228,124,238,127,242,125,126,166,225,206,153,58,96,97,98) THEN 'VENTA' ELSE 'REPOSICIÓN' END AS tx_motMovInv,   ";     
                //strSQL+=" 	CASE WHEN EsqNueSolTra='S' THEN st_conInvMovInv ELSE st_conInvGuiRem END as EstConInv	 ";  
                strSQL+=" 	CASE WHEN st_conInvGuiRem IN ('P','E') THEN st_conInvMovInv ELSE st_conInvGuiRem END as EstConInv	 ";  
                strSQL+=" 	FROM   ";         
                strSQL+=" 	(      ";         
                strSQL+=" 	     select a.co_emp, a7.tx_nom as tx_nomEmp, a.co_loc, a.co_tipdoc, a.co_doc,  a9.tx_nom as tx_nomLoc, a.co_usrIng, a10.tx_usr, a.fe_ing";	  
                strSQL+=" 		  , case when a.co_tipDoc in (101,102) then  cast('ORDDES' as character varying) else a1.tx_desCor end as DesCorOrd "; 
                strSQL+=" 		  , case when a.co_tipDoc in (101,102) then  cast('Orden de Despacho' as character varying) else a1.tx_deslar end as DesLarOrd  ";		                  
                strSQL+=" 		  , a.fe_doc, a.tx_nomclides, case when a.ne_numorddes is null then 0 else a.ne_numorddes end as NumOrdDes  ";
                strSQL+=" 		  , a.tx_dirclides, v.tx_deslar as zona, a2.tx_deslar as forret, a.tx_choRet as perRet, a.fe_acoclidesatr, a.tx_motdesatr  ";  
                strSQL+=" 		  , case when a.st_orddesfal is null or a.st_orddesfal = 'N' then 'N' else 'S' end as faltantes ";                  
                strSQL+=" 		  , case when a.st_orddesdan is null or a.st_orddesdan = 'N' then 'N' else 'S' end as daniados ";                    
                strSQL+=" 		  , a6.co_bod, a6.co_bodGrp, a6.co_empGrp, a3.co_empRel, a3.co_locRel, a3.co_tipDocRel, a3.co_docRel "; 
                strSQL+=" 		  , a.st_coninv as st_conInvGuiRem, case when a8.st_coninv is null then 'P' else a8.st_coninv end as st_conInvMovInv ";
                strSQL+=" 		  , case when a.co_tipdoc not in (101,102) then 'S' else 'N' end as EsqNueSolTra, a8.tx_tipMov ";
                strSQL+=" 	     from tbm_cabguirem as a ";         
                strSQL+=" 	     left outer join tbm_cli as c on (a.co_emp=c.co_emp and  a.co_clides=c.co_cli) ";           
                strSQL+=" 	     inner join tbm_detguirem as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc)  ";              
                strSQL+=" 	     inner join tbm_emp as a7 ON (a.co_Emp=a7.co_Emp)  ";         
                strSQL+=" 	     inner join tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) ";            
                strSQL+=" 	     inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc )  ";         
                strSQL+=" 	     left join tbm_var as a2 on (a2.co_reg=a.co_forret) ";           
                strSQL+=" 	     left join tbm_var as v on (v.co_reg=c.co_zon and v.co_grp=6) ";            
                strSQL+=" 	     left join tbm_grpvar g on (g.co_grp=v.co_grp )     ";   
                strSQL+=" 	     inner join tbm_cabmovInv as a8 on (a8.co_Emp=a3.co_EmpRel and a8.co_loc=a3.co_locRel and a8.co_tiPDoc=a3.co_tipDocRel and a8.co_doc=a3.co_docRel)	 ";	  
                strSQL+=" 	     inner join tbm_loc as a9 ON (a.co_emp=a9.co_emp AND a.co_loc=a9.co_loc)    ";    
                strSQL+=" 	     inner join tbm_usr as a10 ON (a.co_usrIng=a10.co_usr)";
                strSQL+=" 	     where a.st_reg='A' and a.ne_numorddes > 0  "+ strFil;
                strSQL+=" 	) as z  ";
                strSQL+=" 	GROUP BY co_emp, tx_nomEmp, co_loc, co_tipdoc, co_doc, tx_nomLoc, co_usrIng, tx_usr, fe_ing, DesCorOrd, DesLarOrd, fe_doc, tx_nomclides, NumOrdDes, tx_dirclides, zona, forret, perRet ";      
                strSQL+=" 	       , fe_acoclidesatr, faltantes, daniados, tx_motdesatr, kgramo, co_bod, co_bodGrp, co_empGrp, co_empRel, co_locRel, co_tipDocRel, co_docRel ";
                strSQL+=" 	       , st_conInvGuiRem, st_conInvMovInv, EsqNueSolTra, tx_tipMov ";
                strSQL+=" ) as y  ";
                strSQL+=" WHERE "+strConInv;
                strSQL+=" ) as z ";
                strSQL+=" LEFT OUTER JOIN tbm_cabSegMovInv as Seg ";
                strSQL+=" ON (Seg.co_empRelCabGuiRem=z.co_emp AND Seg.co_locRelCabGuiRem=z.co_loc AND Seg.co_tipDocRelCabGuiRem=z.co_tipDoc AND Seg.co_DocRelCabGuiRem=z.co_Doc )";
                strSQL+=" ORDER BY z.NumOrdDes  ";

                //System.out.println("ZafCom78.cargarDetReg: " + strSQL);

                rstLoc = stmLoc.executeQuery(strSQL);
                vecDat.clear();
                while (rstLoc.next()) 
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_CODSEG,  rstLoc.getString("co_seg"));
                    vecReg.add(INT_TBL_DAT_CODEMP,  rstLoc.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_NOMEMP,  rstLoc.getString("tx_nomEmp"));
                    vecReg.add(INT_TBL_DAT_CODLOC,  rstLoc.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_NOMLOC,  rstLoc.getString("tx_nomLoc"));
                    vecReg.add(INT_TBL_DAT_CODTID,  rstLoc.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_DAT_DESTIDC, rstLoc.getString("DesCorOrd"));
                    vecReg.add(INT_TBL_DAT_CODDOC,  rstLoc.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_NUMDOC,  rstLoc.getString("NumOrdDes"));
                    vecReg.add(INT_TBL_DAT_MOT_TRA, rstLoc.getString("tx_motMovInv"));           
                    vecReg.add(INT_TBL_DAT_FECDOC,  rstLoc.getString("fe_doc"));
                    vecReg.add(INT_TBL_DAT_TXDESTI, rstLoc.getString("tx_nomclides"));
                    vecReg.add(INT_TBL_DAT_PTOLLE,  rstLoc.getString("tx_dirclides"));
                    vecReg.add(INT_TBL_DAT_ZONCIU,  rstLoc.getString("zona"));
                    vecReg.add(INT_TBL_DAT_FORRET,  rstLoc.getString("forret"));
                    vecReg.add(INT_TBL_DAT_PERRET,  rstLoc.getString("perRet"));
                    vecReg.add(INT_TBL_DAT_PESO,    rstLoc.getString("kgramo"));
                    vecReg.add(INT_TBL_DAT_BUTOD, "");
                    vecReg.add(INT_TBL_DAT_FECING,  rstLoc.getString("fe_ing"));
                    vecReg.add(INT_TBL_DAT_STCONPEN, (rstLoc.getString("EstConInv").equals("P") ? true : false));
                    vecReg.add(INT_TBL_DAT_STCONPAR, (rstLoc.getString("EstConInv").equals("E") ? true : false));
                    vecReg.add(INT_TBL_DAT_STCONTOT, (rstLoc.getString("EstConInv").equals("C") || rstLoc.getString("EstConInv").equals("F") ? true : false));
                    vecReg.add(INT_TBL_DAT_BUTCONF, "");
                    //vecReg.add(INT_TBL_DAT_STFALSTK, false);
                    vecReg.add(INT_TBL_DAT_BUTGUIA, "");
                    vecReg.add(INT_TBL_DAT_ESTDIA,      rstLoc.getString("diatranOD"));
                    vecReg.add(INT_TBL_DAT_STFALSTK,   (rstLoc.getString("faltantes").equals("S") ? true : false));
                    vecReg.add(INT_TBL_DAT_STDANIADOS, (rstLoc.getString("daniados").equals("S") ? true : false));
                    vecReg.add(INT_TBL_DAT_BUTFAL, "");
                    vecReg.add(INT_TBL_DAT_DIADESATR,  rstLoc.getString("dias_doc"));
                    vecReg.add(INT_TBL_DAT_FECDESATR,  rstLoc.getString("fe_acoclidesatr"));
                    vecReg.add(INT_TBL_DAT_OBSDESATR,  rstLoc.getString("tx_motdesatr"));
                    vecReg.add(INT_TBL_DAT_BUTDESATR, "");
                    vecReg.add(INT_TBL_DAT_COD_BOD,    rstLoc.getString("co_bod"));
                    vecReg.add(INT_TBL_DAT_COD_BODGRP, rstLoc.getString("co_bodGrp"));
                    vecReg.add(INT_TBL_DAT_COD_EMPGRP, rstLoc.getString("co_empGrp"));
                    vecDat.add(vecReg);
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
                //Asignar vectores al modelo.
                objTblModDT.setData(vecDat);
                tblDat.setModel(objTblModDT);
                vecDat.clear();

                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
            }
        }
        catch (java.sql.SQLException e) {       blnRes = false;       objUti.mostrarMsgErr_F1(this, e);    } 
        catch (Exception e) {       blnRes = false;       objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }

    /**
     * Función que guarda detalle de los registros de OD.
     * @return 
     */
    private boolean guardarDetReg() 
    {
        boolean blnRes = false;
        Connection conn;
        try 
        {
            conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) 
            {
                conn.setAutoCommit(false);
                if (guardarDespachosAtrasados(conn)) 
                {
                    conn.commit();
                    mostrarMsgInf("Los datos se modificaron con exito.");
                } 
                else 
                {
                    conn.rollback();
                }
            }
            conn.close();
            conn = null;
        } catch (SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } finally {
        }

        return blnRes;

    }

    /**
     * Función guarda despachos atrasados.
     * @param conn
     * @return 
     */
    private boolean guardarDespachosAtrasados(Connection conn)
    {
        boolean blnRes = false;
        boolean blnEjecuta = false;
        PreparedStatement pst = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        java.util.Date d1 = null, d2 = null;
        String fechaDesAtr = null;
        String motivoDesAtr = null;
        String fechaBD = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), DATE_FORMAT);
        String strSql2 = "", strSql = " update tbm_cabguirem set fe_ultmoddesatr = current_timestamp, co_usrmoddesatr = ? ";
        String strCondicion = " where co_emp = ? and co_loc = ? and co_tipdoc = ? and co_doc = ? ";
        String strColumnas = "";
        int regs = 0;

        try 
        {
            if (conn != null) {
                for (int i = 0; i < tblDat.getRowCount(); i++) {
                    if (tblDat.getValueAt(i, INT_TBL_DAT_NUMDOC) != null) {
                        if (tblDat.getValueAt(i, INT_TBL_DAT_LIN).toString().compareTo("M") == 0) {
                            //VALIDAR DATOS
                            strColumnas = "";
                            blnEjecuta = false;
                            if (tblDat.getValueAt(i, INT_TBL_DAT_OBSDESATR) != null) {
                                motivoDesAtr = tblDat.getValueAt(i, INT_TBL_DAT_OBSDESATR).toString();
                                strColumnas = strColumnas + " , tx_motdesatr = " + objUti.codificar(motivoDesAtr);
                                blnEjecuta = true;
                            }

                            if (tblDat.getValueAt(i, INT_TBL_DAT_FECDESATR) != null) {
                                fechaDesAtr = tblDat.getValueAt(i, INT_TBL_DAT_FECDESATR).toString();
                                if (fechaDesAtr.equals("")) {
                                    strColumnas = strColumnas + " , fe_acoclidesatr = " + objUti.codificar(fechaDesAtr);
                                    blnEjecuta = true;
                                } else {
                                    fechaDesAtr = objUti.formatearFecha(fechaDesAtr, "yyyy/MM/dd", DATE_FORMAT);
                                    if (!fechaDesAtr.equals("[ERROR]")) {
                                        d1 = sdf.parse(fechaBD);
                                        d2 = sdf.parse(fechaDesAtr);
                                        if (d2.before(d1)) {
                                            mostrarMsgInf("Orden de Despacho: " + tblDat.getValueAt(i, INT_TBL_DAT_NUMDOC).toString() + ". La fecha acordada con el cliente no puede ser menor a la fecha actual. Fecha Ingresada: " + fechaDesAtr);
                                            blnEjecuta = false;
                                        } else {
                                            strColumnas = strColumnas + " , fe_acoclidesatr = " + objUti.codificar(fechaDesAtr);
                                            blnEjecuta = true;
                                        }
                                    }
                                }
                            }

                            if (tblDat.getValueAt(i, INT_TBL_DAT_STCONTOT).toString().equals("true")) {
                                mostrarMsgInf("Orden de Despacho: " + tblDat.getValueAt(i, INT_TBL_DAT_NUMDOC).toString() + ". La orden esta confirmada totalmente.");
                                blnEjecuta = false;
                            }

                            if (blnEjecuta) {
                                strSql2 = strSql + strColumnas + strCondicion;
                                pst = conn.prepareStatement(strSql2);
                                pst.setLong(1, objParSis.getCodigoUsuario());
                                pst.setLong(2, Long.parseLong(tblDat.getValueAt(i, INT_TBL_DAT_CODEMP).toString()));
                                pst.setLong(3, Long.parseLong(tblDat.getValueAt(i, INT_TBL_DAT_CODLOC).toString()));
                                pst.setLong(4, Long.parseLong(tblDat.getValueAt(i, INT_TBL_DAT_CODTID).toString()));
                                pst.setLong(5, Long.parseLong(tblDat.getValueAt(i, INT_TBL_DAT_CODDOC).toString()));
                                pst.executeUpdate();
                                regs++;
                            }
                        }
                    }
                }

                if (regs > 0) {
                    blnRes = true;
                }

                if (pst != null) {
                    pst.close();
                }

                pst = null;
            }
        } catch (ParseException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    private boolean actualizaEstadoConInv() 
    {
        boolean blnRes = true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try 
        {
            conLoc = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                strSQL = "";

                strSQL+=" UPDATE tbm_cabMovInv SET st_conInv='C' ";
                strSQL+=" FROM "; 
                strSQL+=" ( ";
                strSQL+="     SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc ";
                strSQL+="     FROM tbm_cabMovInv AS a1 ";
                strSQL+="     INNER JOIN tbm_detMovInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc ";
                strSQL+="     WHERE a1.st_reg='A' AND a1.fe_doc  >='2016-07-01'   ";
                strSQL+="     AND (a2.nd_can - a2.nd_canCon - a2.nd_canNunRec) =0 ";
                strSQL+="     AND a1.st_genOrdDes='S' AND a1.st_ordDesGen='S' ";
                strSQL+="     AND a1.tx_tipMov IS NOT NULL ";
                strSQL+="     AND (a1.st_conInv IN('P','E') OR a1.st_conInv IS NULL) ";
                strSQL+="     GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc ";
                strSQL+="        EXCEPT ";
                strSQL+="     SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc ";
                strSQL+="     FROM tbm_cabMovInv AS a1 ";
                strSQL+="     INNER JOIN tbm_detMovInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc ";
                strSQL+="     WHERE a1.st_reg='A' AND a1.fe_doc  >='2016-07-01'   ";
                strSQL+="     AND (a2.nd_can - a2.nd_canCon - a2.nd_canNunRec) !=0 ";
                strSQL+="     AND a1.st_genOrdDes='S' AND a1.st_ordDesGen='S' ";
                strSQL+="     AND a1.tx_tipMov IS NOT NULL ";
                strSQL+="     AND (a1.st_conInv IN('P','E') OR a1.st_conInv IS NULL) ";
                strSQL+="     GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc "; 
                strSQL+=" ) AS x ";
                strSQL+=" WHERE tbm_cabMovInv.co_emp=x.co_emp AND tbm_cabMovInv.co_loc=x.co_loc AND tbm_cabMovInv.co_tipDoc=x.co_tipDoc AND tbm_cabMovInv.co_doc=x.co_doc ;";
                
                //System.out.println("actualizaEstadoConInv:" + strSQL);
                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                conLoc.close();
                stmLoc = null;
                conLoc = null;
            }
            return blnRes;
        } 
        catch (java.sql.SQLException e) {   blnRes = false;  objUti.mostrarMsgErr_F1(this, e);   } 
        catch (Exception e) {  blnRes = false;   objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }    


   
    
    
    
}

