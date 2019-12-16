/*
 * ZafCom48.java  
 *
 * Created on Nov 30, 2010, 3:03:54 PM
 */
package Compras.ZafCom48;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafObtConCen.ZafObtConCen;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
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
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author jayapata
 */
public class ZafCom48 extends javax.swing.JInternalFrame {
    //Constantes: Jtable

    // TABLA DE BODEGAS
    private static final int INT_TBL_LIN = 0;
    private static final int INT_TBL_CHKBOD = 1;
    private static final int INT_TBL_CODBOD = 2;
    private static final int INT_TBL_NOMBOD = 3;

    // TABLA DE DATOS
    private static final int INT_TBLD_LIN = 0;
    private static final int INT_TBLD_CODEMP = 1;
    private static final int INT_TBLD_CODLOC = 2;
    private static final int INT_TBLD_NOMLOC = 3;
    private static final int INT_TBLD_CODTID = 4;
    private static final int INT_TBLD_DESTIDC = 5;
    private static final int INT_TBLD_DESTIDL = 6;
    private static final int INT_TBLD_CODDOC = 7;
    private static final int INT_TBLD_NUMDOC = 8;
    private static final int INT_TBLD_NUMORDES = 9;
    private static final int INT_TBLD_BUTGUIA = 10;
    private static final int INT_TBLD_PESOKG = 11;
    private static final int INT_TBLD_PESOKGPEN = 12;
    private static final int INT_TBLD_FECDOC = 13;
    private static final int INT_TBLD_CODBOD = 14;
    private static final int INT_TBLD_NOMBOD = 15;
    private static final int INT_TBLD_CLIRET = 16;
    private static final int INT_TBLD_STCONPEN = 17;
    private static final int INT_TBLD_STCONPAR = 18;
    private static final int INT_TBLD_STCONTOT = 19;
    private static final int INT_TBLD_BUTCONF = 20;

    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafSelFec objSelFec;
    private ZafTblMod objTblModBE, objTblModDT, objTblModTG;
    private ZafTblCelRenChk objTblCelRenChkBE, ZafTblCelRenChkDat;
    private ZafTblCelEdiChk objTblCelEdiChkBE;
    private ZafTblCelRenBut objTblCelRenButDG;
    private ZafTblCelEdiButGen objTblCelEdiButGenDG;
    private ZafThreadGUI objThrGUI;
    private ZafMouMotAda objMouMotAda;
    private ZafObtConCen objObtConCen;
    private ZafVenCon vcoItm;
    private ZafVenCon vcoBod;

    private Vector vecDat, vecReg;
    private boolean blnMarTodCanTblBod = true;

    int INTCODREGCEN = 0;

    private String strCodBod = "", strNomBod = "";
    private String strCodItm = "", strDesItm = "";

    private String strVersion = " v2.0 ";

    /**
     * Creates new form ZafCom48
     */
    public ZafCom48(ZafParSis objZafParsis) {
        try {
            this.objParSis = (ZafParSis) objZafParsis.clone();
            objUti = new ZafUtil();

            initComponents();

            objObtConCen = new ZafObtConCen(objParSis);
            INTCODREGCEN = objObtConCen.intCodReg;

            vecDat = new Vector();

            //Configurar ZafSelFec:
            objSelFec = new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(true);
            panFilCab.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);

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
            java.util.Calendar objFecPagActual = Calendar.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(java.util.Calendar.DATE, -30);

            dtePckPag.setAnio(objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes(objFecPagActual.get(java.util.Calendar.MONTH) + 1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(), "dd/MM/yyyy", "yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha, "yyyy/MM/dd");

            objSelFec.setFechaDesde(objUti.formatearFecha(fe1, "dd/MM/yyyy"));

            //*******************************************************************************
            this.setTitle(objParSis.getNombreMenu() + " " + strVersion);
            lblTit.setText(objParSis.getNombreMenu());
        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    public void Configura_ventana_consulta() {
        configurarVenConItm();
        configurarFormBE();
        configurarFormDT();
        configurarTblGui();
        cargarBod();
        configurarVenConBodUsr();
    }

    private boolean configurarVenConBodUsr() {
        boolean blnRes = true;
        try {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_bod");
            arlCam.add("a.tx_nom");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Bod");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("350");

            //Armar la sentencia SQL.   a7.nd_stkTot,
            String Str_Sql = "";

            //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
            if (objParSis.getCodigoUsuario() == 1) {
                //Armar la sentencia SQL.
                Str_Sql = "SELECT co_bod, tx_nom FROM ( SELECT a2.co_bod, a2.tx_nom "
                        + " FROM tbm_emp AS a1 "
                        + " INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) "
                        + " WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + " "
                        + " ORDER BY a1.co_emp, a2.co_bod  ) as a ";
            } else {
                //Armar la sentencia SQL.

                Str_Sql = "SELECT co_bod, tx_nom FROM ( "
                        + " select a2.co_bodgrp as co_bod,  a1.tx_nom from tbr_bodlocprgusr as a "
                        + " inner join tbr_bodEmpBodGrp as a2 on (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) "
                        + " inner join tbm_bod as a1 on (a1.co_emp=a2.co_empgrp and a1.co_bod=a2.co_bodgrp) "
                        + " where a.co_emp=" + objParSis.getCodigoEmpresa() + " AND a.co_loc=" + objParSis.getCodigoLocal() + " "
                        + " and a.co_usr=" + objParSis.getCodigoUsuario() + " and a.co_mnu=" + objParSis.getCodigoMenu() + " "
                        + " and a.tx_natbod in ( 'I', 'A' )  "
                        + " ) as a";

            }

            vcoBod = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

            vcoBod.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarFormBE() {
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
        ZafColNumerada zafColNumerada = new ZafColNumerada(tblBod, INT_TBL_LIN);

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
        ZafTblEdi zafTblEdi = new ZafTblEdi(tblBod);

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

    private void tblDatMouseClickedBE(java.awt.event.MouseEvent evt) {
        int i, intNumFil;
        try {
            intNumFil = tblBod.getRowCount();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.

            if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1 && tblBod.columnAtPoint(evt.getPoint()) == INT_TBL_CHKBOD) {
                if (blnMarTodCanTblBod) {
                    //Mostrar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        tblBod.setValueAt(false, i, INT_TBL_CHKBOD);
                    }
                    blnMarTodCanTblBod = false;
                } else {
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

    private boolean configurarVenConItm() {
        boolean blnRes = true;
        try {
            ArrayList arlCam = new ArrayList();
            arlCam.add("co_itm");
            arlCam.add("tx_codalt");
            arlCam.add("tx_descor");
            arlCam.add("tx_nomitm");
            ArrayList arlAli = new ArrayList();
            arlAli.add("Cód.Itm");
            arlAli.add("Cód.Alt.");
            arlAli.add("Unidad.");
            arlAli.add("Nom.Itm.");
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("60");
            arlAncCol.add("300");
            //Armar la sentencia SQL.
            String strSQL = "";
            String strAuxInv = "", strConInv = "";

            strSQL = "SELECT * FROM( SELECT * " + strAuxInv + " FROM( select a.co_itm, a.tx_codalt, a1.tx_descor, a.tx_nomitm   from tbm_inv as a "
                    + " left join tbm_var as a1 on (a1.co_reg=a.co_uni) "
                    + " WHERE  a.co_emp=" + objParSis.getCodigoEmpresa() + " and a.st_reg not in ('U','T')   ) AS x ) AS x  " + strConInv + " ";
            vcoItm = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarFormDT() {
        boolean blnres = false;

        Vector vecCab = new Vector();    //Almacena las cabeceras
        vecCab.clear();

        /*vecCab.add(INT_TBLD_LIN,"");
         vecCab.add(INT_TBLD_CODEMP,"Cód.Emp");
         vecCab.add(INT_TBLD_CODLOC,"Cód.Loc");
         vecCab.add(INT_TBLD_NOMLOC,"Nom.Loc");
         vecCab.add(INT_TBLD_CODTID,"Cód.Tip.Doc");
         vecCab.add(INT_TBLD_DESTIDC,"Des.Tip.Doc");
         vecCab.add(INT_TBLD_DESTIDL,"Des.Tip.Doc");
         vecCab.add(INT_TBLD_CODDOC,"Cód.Doc");
         vecCab.add(INT_TBLD_NUMDOC,"Núm.Doc");
         vecCab.add(INT_TBLD_NUMORDES,"Ord.Des");
         vecCab.add(INT_TBLD_BUTGUIA,".."); 
         vecCab.add(INT_TBLD_PESOKG,"Peso.");
         vecCab.add(INT_TBLD_FECDOC,"Fec.Doc");
         vecCab.add(INT_TBLD_CODBOD,"Cód.Bod");
         vecCab.add(INT_TBLD_NOMBOD,"Bodega");
         vecCab.add(INT_TBLD_STCONPEN,"Pendiente");
         vecCab.add(INT_TBLD_STCONPAR,"Parcial");
         vecCab.add(INT_TBLD_STCONTOT,"Total");
         vecCab.add(INT_TBLD_BUTCONF,"..");*/
        vecCab.add(INT_TBLD_LIN, "");
        vecCab.add(INT_TBLD_CODEMP, "Cód.Emp");
        vecCab.add(INT_TBLD_CODLOC, "Cód.Loc");
        vecCab.add(INT_TBLD_NOMLOC, "Nom.Loc");
        vecCab.add(INT_TBLD_CODTID, "Cód.Tip.Doc");
        vecCab.add(INT_TBLD_DESTIDC, "Des.Tip.Doc");
        vecCab.add(INT_TBLD_DESTIDL, "Des.Tip.Doc");
        vecCab.add(INT_TBLD_CODDOC, "Cód.Doc");
        vecCab.add(INT_TBLD_NUMDOC, "Núm.Doc");
        vecCab.add(INT_TBLD_NUMORDES, "Ord.Des");
        vecCab.add(INT_TBLD_BUTGUIA, "..");
        vecCab.add(INT_TBLD_PESOKG, "Peso.");
        vecCab.add(INT_TBLD_PESOKGPEN, "Pes.Pen.");  // José Marín M. 1/Agosto/2014
        vecCab.add(INT_TBLD_FECDOC, "Fec.Doc");
        vecCab.add(INT_TBLD_CODBOD, "Cód.Bod");
        vecCab.add(INT_TBLD_NOMBOD, "Bodega Origen");
        vecCab.add(INT_TBLD_CLIRET, "Cli.Ret.");
        vecCab.add(INT_TBLD_STCONPEN, "Pendiente");
        vecCab.add(INT_TBLD_STCONPAR, "Parcial");
        vecCab.add(INT_TBLD_STCONTOT, "Total");
        vecCab.add(INT_TBLD_BUTCONF, "..");

        objTblModDT = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblModDT.setHeader(vecCab);
        tblDat.setModel(objTblModDT);

        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        objTblModDT.setColumnDataType(INT_TBLD_NUMDOC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblModDT.setColumnDataType(INT_TBLD_NUMORDES, ZafTblMod.INT_COL_DBL, new Integer(0), null);

        //Configurar JTable: Establecer la fila de cabecera.
        ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat, INT_TBLD_LIN);

        //Configurar JTable: Editor de búsqueda.
        ZafTblBus zafTblBus = new ZafTblBus(tblDat);
        ZafTblOrd zafTblOrd = new ZafTblOrd(tblDat);
        ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDat);

        objMouMotAda = new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();

        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBLD_LIN).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBLD_NOMLOC).setPreferredWidth(120);
        tcmAux.getColumn(INT_TBLD_DESTIDC).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBLD_DESTIDL).setPreferredWidth(120);
        tcmAux.getColumn(INT_TBLD_NUMDOC).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBLD_NUMORDES).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBLD_PESOKG).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBLD_PESOKGPEN).setPreferredWidth(60);  // José Marín M. 1/Ago/2014
        tcmAux.getColumn(INT_TBLD_FECDOC).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBLD_NOMBOD).setPreferredWidth(120);
        tcmAux.getColumn(INT_TBLD_CLIRET).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBLD_BUTGUIA).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBLD_STCONPEN).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBLD_STCONPAR).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBLD_STCONTOT).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBLD_BUTCONF).setPreferredWidth(20);

        Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
        tcmAux.getColumn(INT_TBLD_PESOKG).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBLD_PESOKGPEN).setCellRenderer(objTblCelRenLbl);

        ArrayList arlColHid = new ArrayList();
        arlColHid.add("" + INT_TBLD_CODEMP);
        arlColHid.add("" + INT_TBLD_CODLOC);
        arlColHid.add("" + INT_TBLD_CODTID);
        arlColHid.add("" + INT_TBLD_CODDOC);
        arlColHid.add("" + INT_TBLD_CODBOD);
        objTblModDT.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid = null;

        ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16 * 2);

        ZafTblHeaColGrp objTblHeaColGrpAmeSur = new ZafTblHeaColGrp(" DATOS DE EGRESO DE MERCADERIA ");
        objTblHeaColGrpAmeSur.setHeight(16);

        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_CODEMP));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_CODLOC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_NOMLOC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_CODTID));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_DESTIDC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_DESTIDL));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_CODDOC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_NUMDOC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_NUMORDES));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_PESOKG));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_PESOKGPEN));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_FECDOC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_CODBOD));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_NOMBOD));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_CLIRET));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_BUTGUIA));

        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
        objTblHeaColGrpAmeSur = null;

        objTblHeaColGrpAmeSur = new ZafTblHeaColGrp(" DATOS DE LA CONFIRMACION ");
        objTblHeaColGrpAmeSur.setHeight(16);

        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_STCONPEN));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_STCONPAR));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_STCONTOT));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_BUTCONF));

        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
        objTblHeaColGrpAmeSur = null;

        //Configurar JTable: Establecer columnas editables.
        Vector vecAux = new Vector();
        vecAux.add("" + INT_TBLD_BUTGUIA);
        vecAux.add("" + INT_TBLD_BUTCONF);
        objTblModDT.setColumnasEditables(vecAux);
        vecAux = null;

        //Configurar JTable: Editor de la tabla.
        ZafTblEdi zafTblEdi = new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

        tblDat.getTableHeader().setReorderingAllowed(false);

        ZafTblCelRenChkDat = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBLD_STCONPEN).setCellRenderer(ZafTblCelRenChkDat);
        tcmAux.getColumn(INT_TBLD_STCONPAR).setCellRenderer(ZafTblCelRenChkDat);
        tcmAux.getColumn(INT_TBLD_STCONTOT).setCellRenderer(ZafTblCelRenChkDat);
        ZafTblCelRenChkDat = null;

        objTblCelRenButDG = new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBLD_BUTGUIA).setCellRenderer(objTblCelRenButDG);
        tcmAux.getColumn(INT_TBLD_BUTCONF).setCellRenderer(objTblCelRenButDG);

        objTblCelRenButDG.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
            @Override
            public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                switch (objTblCelRenButDG.getColumnRender()) {
                    case INT_TBLD_BUTGUIA:
                        objTblCelRenButDG.setText("...");
                        break;

                    case INT_TBLD_BUTCONF:
                        objTblCelRenButDG.setText("...");
                        break;

                }
            }
        });

        //Configurar JTable: Editor de celdas.
        objTblCelEdiButGenDG = new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
        tcmAux.getColumn(INT_TBLD_BUTGUIA).setCellEditor(objTblCelEdiButGenDG);
        tcmAux.getColumn(INT_TBLD_BUTCONF).setCellEditor(objTblCelEdiButGenDG);
        objTblCelEdiButGenDG.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            int intFilSel, intColSel;

            @Override
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                intFilSel = tblDat.getSelectedRow();
                intColSel = tblDat.getSelectedColumn();
                if (intFilSel != -1) {
                    switch (intColSel) {
                        case INT_TBLD_BUTGUIA:

                            break;

                        case INT_TBLD_BUTCONF:

                            break;

                    }
                }
            }

            @Override
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                intColSel = tblDat.getSelectedColumn();
                if (intFilSel != -1) {
                    switch (intColSel) {
                        case INT_TBLD_BUTGUIA:

                            llamarPantOrdDes(objTblModDT.getValueAt(intFilSel, INT_TBLD_CODEMP).toString(), objTblModDT.getValueAt(intFilSel, INT_TBLD_CODLOC).toString(), objTblModDT.getValueAt(intFilSel, INT_TBLD_CODTID).toString(), objTblModDT.getValueAt(intFilSel, INT_TBLD_CODDOC).toString()
                            );

                            break;

                        case INT_TBLD_BUTCONF:

                            llamarVtaConf(objTblModDT.getValueAt(intFilSel, INT_TBLD_CODEMP).toString(), objTblModDT.getValueAt(intFilSel, INT_TBLD_CODLOC).toString(), objTblModDT.getValueAt(intFilSel, INT_TBLD_CODTID).toString(), objTblModDT.getValueAt(intFilSel, INT_TBLD_CODDOC).toString(), 1
                            );
                            break;

                    }
                }
            }
        });

        //Configurar JTable: Establecer el ListSelectionListener.
        javax.swing.ListSelectionModel lsm = tblDat.getSelectionModel();
        lsm.addListSelectionListener(new ZafLisSelLis());

        objTblModDT.setModoOperacion(ZafTblMod.INT_TBL_EDI);

        return blnres;
    }

    /**
     * Esta clase implementa la interface "ListSelectionListener" para
     * determinar cambios en la selección. Es decir, cada vez que se selecciona
     * una fila diferente en el JTable se ejecutará el "ListSelectionListener".
     */
    private class ZafLisSelLis implements javax.swing.event.ListSelectionListener {

        @Override
        public void valueChanged(javax.swing.event.ListSelectionEvent e) {
            javax.swing.ListSelectionModel lsm = (javax.swing.ListSelectionModel) e.getSource();
            if (!lsm.isSelectionEmpty()) {
                if (chkMosGuiRem.isSelected()) {
                    objTblModTG.removeAllRows();
                    cargarGuiSec();
                }
            }
        }
    }

    private boolean configurarTblGui() {
        boolean blnres = false;

        Vector vecCab = new Vector();    //Almacena las cabeceras
        vecCab.clear();

        /*vecCab.add(INT_TBLD_LIN,"");
         vecCab.add(INT_TBLD_CODEMP,"Cód.Emp");
         vecCab.add(INT_TBLD_CODLOC,"Cód.Loc");
         vecCab.add(INT_TBLD_NOMLOC,"Nom.Loc");
         vecCab.add(INT_TBLD_CODTID,"Cód.Tip.Doc");
         vecCab.add(INT_TBLD_DESTIDC,"Des.Tip.Doc");
         vecCab.add(INT_TBLD_DESTIDL,"Des.Tip.Doc");
         vecCab.add(INT_TBLD_CODDOC,"Cód.Doc");
         vecCab.add(INT_TBLD_NUMDOC,"Núm.Doc");
         vecCab.add(INT_TBLD_NUMORDES,"Ord.Des");
         vecCab.add(INT_TBLD_BUTGUIA,"..");
         vecCab.add(INT_TBLD_PESOKG,"Peso.");
         vecCab.add(INT_TBLD_FECDOC,"Fec.Doc");
         vecCab.add(INT_TBLD_CODBOD,"Cód.Bod");
         vecCab.add(INT_TBLD_NOMBOD,"Bodega");
         vecCab.add(INT_TBLD_STCONPEN,"Pendiente");
         vecCab.add(INT_TBLD_STCONPAR,"Parcial");
         vecCab.add(INT_TBLD_STCONTOT,"Total");
         vecCab.add(INT_TBLD_BUTCONF,"..");*/
        vecCab.add(INT_TBLD_LIN, "");
        vecCab.add(INT_TBLD_CODEMP, "Cód.Emp");
        vecCab.add(INT_TBLD_CODLOC, "Cód.Loc");
        vecCab.add(INT_TBLD_NOMLOC, "Nom.Loc");
        vecCab.add(INT_TBLD_CODTID, "Cód.Tip.Doc");
        vecCab.add(INT_TBLD_DESTIDC, "Des.Tip.Doc");
        vecCab.add(INT_TBLD_DESTIDL, "Des.Tip.Doc");
        vecCab.add(INT_TBLD_CODDOC, "Cód.Doc");
        vecCab.add(INT_TBLD_NUMDOC, "Núm.Doc");
        vecCab.add(INT_TBLD_NUMORDES, "Ord.Des");
        vecCab.add(INT_TBLD_BUTGUIA, "..");
        vecCab.add(INT_TBLD_PESOKG, "Peso.");
        vecCab.add(INT_TBLD_PESOKGPEN, "Pes.Pen");  // José Marín M 1/Agos/2014

        vecCab.add(INT_TBLD_FECDOC, "Fec.Doc");
        vecCab.add(INT_TBLD_CODBOD, "Cód.Bod");
        vecCab.add(INT_TBLD_NOMBOD, "Bodega");
        vecCab.add(INT_TBLD_CLIRET, "");
        vecCab.add(INT_TBLD_STCONPEN, "Pendiente");
        vecCab.add(INT_TBLD_STCONPAR, "Parcial");
        vecCab.add(INT_TBLD_STCONTOT, "Total");
        vecCab.add(INT_TBLD_BUTCONF, "..");

        objTblModTG = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblModTG.setHeader(vecCab);
        tblgui.setModel(objTblModTG);

        //Configurar JTable: Establecer tipo de selección.
        tblgui.setRowSelectionAllowed(true);
        tblgui.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        //Configurar JTable: Establecer la fila de cabecera.
        ZafColNumerada zafColNumerada = new Librerias.ZafColNumerada.ZafColNumerada(tblgui, INT_TBLD_LIN);

        //Configurar JTable: Editor de búsqueda.
        ZafTblBus zafTblBus = new ZafTblBus(tblgui);

        ZafTblOrd zafTblOrd = new ZafTblOrd(tblgui);
        ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblgui);

        objMouMotAda = new ZafMouMotAda();
        tblgui.getTableHeader().addMouseMotionListener(objMouMotAda);

        tblgui.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux = tblgui.getColumnModel();

        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBLD_LIN).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBLD_NOMLOC).setPreferredWidth(120);
        tcmAux.getColumn(INT_TBLD_DESTIDC).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBLD_DESTIDL).setPreferredWidth(120);
        tcmAux.getColumn(INT_TBLD_NUMDOC).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBLD_PESOKG).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBLD_PESOKGPEN).setPreferredWidth(60);

        tcmAux.getColumn(INT_TBLD_FECDOC).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBLD_NOMBOD).setPreferredWidth(120);
        tcmAux.getColumn(INT_TBLD_BUTGUIA).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBLD_STCONPEN).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBLD_STCONPAR).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBLD_STCONTOT).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBLD_BUTCONF).setPreferredWidth(20);

        Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
        tcmAux.getColumn(INT_TBLD_PESOKG).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBLD_PESOKGPEN).setCellRenderer(objTblCelRenLbl);

        ArrayList arlColHid = new ArrayList();
        arlColHid.add("" + INT_TBLD_CODEMP);
        arlColHid.add("" + INT_TBLD_CODLOC);
        arlColHid.add("" + INT_TBLD_CODTID);
        arlColHid.add("" + INT_TBLD_CODDOC);
        arlColHid.add("" + INT_TBLD_CODBOD);
        arlColHid.add("" + INT_TBLD_NUMORDES);
        arlColHid.add("" + INT_TBLD_CLIRET);
        objTblModTG.setSystemHiddenColumns(arlColHid, tblgui);
        arlColHid = null;

        ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblgui.getTableHeader();
        objTblHeaGrp.setHeight(16 * 2);

        ZafTblHeaColGrp objTblHeaColGrpAmeSur = new ZafTblHeaColGrp(" DATOS DE EGRESO DE MERCADERIA ");
        objTblHeaColGrpAmeSur.setHeight(16);

        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_CODEMP));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_CODLOC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_NOMLOC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_CODTID));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_DESTIDC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_DESTIDL));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_CODDOC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_NUMDOC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_NUMORDES));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_PESOKG));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_PESOKGPEN));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_FECDOC));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_CODBOD));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_NOMBOD));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_CLIRET));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_BUTGUIA));

        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
        objTblHeaColGrpAmeSur = null;

        objTblHeaColGrpAmeSur = new ZafTblHeaColGrp(" DATOS DE LA CONFIRMACION ");
        objTblHeaColGrpAmeSur.setHeight(16);

        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_STCONPEN));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_STCONPAR));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_STCONTOT));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBLD_BUTCONF));

        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
        objTblHeaColGrpAmeSur = null;

        //Configurar JTable: Establecer columnas editables.
        Vector vecAux = new Vector();
        vecAux.add("" + INT_TBLD_BUTGUIA);
        vecAux.add("" + INT_TBLD_BUTCONF);
        objTblModTG.setColumnasEditables(vecAux);
        vecAux = null;

        //Configurar JTable: Editor de la tabla.
        ZafTblEdi zafTblEdi = new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblgui);

        tblgui.getTableHeader().setReorderingAllowed(false);

        ZafTblCelRenChkDat = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBLD_STCONPEN).setCellRenderer(ZafTblCelRenChkDat);
        tcmAux.getColumn(INT_TBLD_STCONPAR).setCellRenderer(ZafTblCelRenChkDat);
        tcmAux.getColumn(INT_TBLD_STCONTOT).setCellRenderer(ZafTblCelRenChkDat);
        ZafTblCelRenChkDat = null;

        objTblCelRenButDG = new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBLD_BUTGUIA).setCellRenderer(objTblCelRenButDG);
        tcmAux.getColumn(INT_TBLD_BUTCONF).setCellRenderer(objTblCelRenButDG);

        objTblCelRenButDG.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
            @Override
            public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                switch (objTblCelRenButDG.getColumnRender()) {
                    case INT_TBLD_BUTGUIA:
                        objTblCelRenButDG.setText("...");
                        break;

                    case INT_TBLD_BUTCONF:
                        objTblCelRenButDG.setText("...");
                        break;

                }
            }
        });

        //Configurar JTable: Editor de celdas.
        objTblCelEdiButGenDG = new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
        tcmAux.getColumn(INT_TBLD_BUTGUIA).setCellEditor(objTblCelEdiButGenDG);
        tcmAux.getColumn(INT_TBLD_BUTCONF).setCellEditor(objTblCelEdiButGenDG);
        objTblCelEdiButGenDG.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            int intFilSel, intColSel;

            @Override
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                intFilSel = tblgui.getSelectedRow();
                intColSel = tblgui.getSelectedColumn();
                if (intFilSel != -1) {
                    switch (intColSel) {
                        case INT_TBLD_BUTGUIA:

                            break;

                        case INT_TBLD_BUTCONF:

                            break;

                    }
                }
            }

            @Override
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                intColSel = tblgui.getSelectedColumn();
                if (intFilSel != -1) {
                    switch (intColSel) {
                        case INT_TBLD_BUTGUIA:

                            llamarPantGuia(objTblModTG.getValueAt(intFilSel, INT_TBLD_CODEMP).toString(), objTblModTG.getValueAt(intFilSel, INT_TBLD_CODLOC).toString(), objTblModTG.getValueAt(intFilSel, INT_TBLD_CODTID).toString(), objTblModTG.getValueAt(intFilSel, INT_TBLD_CODDOC).toString()
                            );

                            break;

                        case INT_TBLD_BUTCONF:

                            llamarVtaConf(objTblModTG.getValueAt(intFilSel, INT_TBLD_CODEMP).toString(), objTblModTG.getValueAt(intFilSel, INT_TBLD_CODLOC).toString(), objTblModTG.getValueAt(intFilSel, INT_TBLD_CODTID).toString(), objTblModTG.getValueAt(intFilSel, INT_TBLD_CODDOC).toString(), 1
                            );
                            break;

                    }
                }
            }
        });

        objTblModTG.setModoOperacion(ZafTblMod.INT_TBL_EDI);

        return blnres;
    }

    private void llamarPantGuia(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        int intCodMnu = 1793;
        Compras.ZafCom23.ZafCom23 obj = new Compras.ZafCom23.ZafCom23(objParSis, intCodMnu, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
    }

    private void llamarPantOrdDes(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        int intCodMnu = 3497;
        Compras.ZafCom23.ZafCom23 obj = new Compras.ZafCom23.ZafCom23(objParSis, intCodMnu, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
    }

    private void llamarVtaConf(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, int inTipConf) {
        Compras.ZafCom47.ZafCom47_01 obj1 = new Compras.ZafCom47.ZafCom47_01(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, inTipConf);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }

    /**
     * Esta función permite consultar las bodegas de acuerdo al siguiente
     * criterio: El listado de bodegas se presenta en función de la empresa a la
     * que se ingresa (Empresa Grupo u otra empresa) , el usuario que ingresa
     * (Administrador u otro usuario) y el menú desde el cual es llamado (237,
     * 886 o 907).
     *
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarBod() {
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        boolean blnRes = true;
        String strSQL = "";
        try {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement();

                //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                if (objParSis.getCodigoUsuario() == 1) {
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL += "SELECT a2.co_bod, a2.tx_nom ";
                    strSQL += " FROM tbm_emp AS a1";
                    strSQL += " INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                    strSQL += " ORDER BY a1.co_emp, a2.co_bod";
                    rst = stm.executeQuery(strSQL);
                } else {
                    //Armar la sentencia SQL.
                    strSQL = "select  a1.co_bodgrp as co_bod, a2.tx_nom   from tbr_bodLocPrgUsr as a "
                            + " inner join tbr_bodEmpBodGrp as a1  on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod ) "
                            + " inner join tbm_bod as a2  on (a2.co_emp=a1.co_empgrp and a2.co_bod=a1.co_bodgrp ) "
                            + " where a.co_emp=" + objParSis.getCodigoEmpresa() + " and a.co_loc=" + objParSis.getCodigoLocal() + " "
                            + " and a.co_usr=" + objParSis.getCodigoUsuario() + " and  a.co_mnu=" + objParSis.getCodigoMenu() + " "
                            + "  and a.tx_natBod IN  ('E','A') "
                            + " group by a1.co_bodgrp, a2.tx_nom  order by co_bodgrp ";

                    rst = stm.executeQuery(strSQL);
                }

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()) {
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
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
        scrBar = new javax.swing.JScrollPane();
        panFilCab = new javax.swing.JPanel();
        panCodAltItmTer = new javax.swing.JPanel();
        lblCodAltItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        lblCli = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtDesItm = new javax.swing.JTextField();
        butSol = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtcodaltdes = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtcodalthas = new javax.swing.JTextField();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        chkDocConfPar = new javax.swing.JCheckBox();
        chkDocConfTot = new javax.swing.JCheckBox();
        chkMosPenConf = new javax.swing.JCheckBox();
        lblBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBusBod = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        lblOrdDes = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblgui = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        chkMosGuiRem = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
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

        scrBar.setPreferredSize(new java.awt.Dimension(0, 400));

        panFilCab.setAutoscrolls(true);
        panFilCab.setPreferredSize(new java.awt.Dimension(0, 400));
        panFilCab.setLayout(null);

        panCodAltItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panCodAltItmTer.setLayout(null);

        lblCodAltItmTer.setText("Terminan con:");
        panCodAltItmTer.add(lblCodAltItmTer);
        lblCodAltItmTer.setBounds(12, 20, 100, 20);

        txtCodAltItmTer.setToolTipText("<HTML>\nSi desea consultar más de un terminal separe cada terminal por medio de una coma.\n<BR><FONT COLOR=\"blue\">Por ejemplo:</FONT> S,L,T\n</HTML>");
        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panCodAltItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(112, 20, 130, 20);

        panFilCab.add(panCodAltItmTer);
        panCodAltItmTer.setBounds(360, 260, 260, 52);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCli.setText("Item:");
        panFilCab.add(lblCli);
        lblCli.setBounds(30, 240, 40, 20);

        txtCodItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodItmActionPerformed(evt);
            }
        });
        txtCodItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodItmFocusLost(evt);
            }
        });
        panFilCab.add(txtCodItm);
        txtCodItm.setBounds(90, 240, 100, 20);

        txtDesItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesItmFocusLost(evt);
            }
        });
        txtDesItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesItmActionPerformed(evt);
            }
        });
        panFilCab.add(txtDesItm);
        txtDesItm.setBounds(190, 240, 320, 20);

        butSol.setText(".."); // NOI18N
        butSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSolActionPerformed(evt);
            }
        });
        panFilCab.add(butSol);
        butSol.setBounds(510, 240, 20, 20);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        jPanel5.setLayout(null);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel4.setText("Desde:");
        jPanel5.add(jLabel4);
        jLabel4.setBounds(12, 24, 40, 20);

        txtcodaltdes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcodaltdesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcodaltdesFocusLost(evt);
            }
        });
        jPanel5.add(txtcodaltdes);
        txtcodaltdes.setBounds(52, 24, 80, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Hasta:");
        jPanel5.add(jLabel5);
        jLabel5.setBounds(148, 28, 31, 15);

        txtcodalthas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcodalthasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcodalthasFocusLost(evt);
            }
        });
        jPanel5.add(txtcodalthas);
        txtcodalthas.setBounds(184, 24, 80, 20);

        panFilCab.add(jPanel5);
        jPanel5.setBounds(30, 260, 330, 50);

        buttonGroup1.add(optTod);
        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setSelected(true);
        optTod.setText("Todos los Items");
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
        optTod.setBounds(10, 190, 330, 20);

        buttonGroup1.add(optFil);
        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Sólo los Items que cumplan el criterio seleccionado");
        panFilCab.add(optFil);
        optFil.setBounds(10, 210, 330, 20);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Bodega Origen"));
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
        panFilCab.add(chkDocConfPar);
        chkDocConfPar.setBounds(10, 330, 410, 20);

        chkDocConfTot.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkDocConfTot.setText("Mostrar los documentos que están confirmados totalmente.");
        panFilCab.add(chkDocConfTot);
        chkDocConfTot.setBounds(10, 350, 410, 20);

        chkMosPenConf.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkMosPenConf.setSelected(true);
        chkMosPenConf.setText("Mostrar los documentos que estan pendiente de confirmar.");
        chkMosPenConf.setActionCommand("Mostrar los documentos que están pendiente de confirmar.");
        panFilCab.add(chkMosPenConf);
        chkMosPenConf.setBounds(10, 310, 410, 20);

        lblBod.setText("Bodega destino:");
        panFilCab.add(lblBod);
        lblBod.setBounds(20, 160, 110, 20);

        txtCodBod.setBackground(objParSis.getColorCamposObligatorios());
        txtCodBod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        txtCodBod.setBounds(140, 160, 70, 20);

        txtNomBod.setBackground(objParSis.getColorCamposObligatorios());
        txtNomBod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        txtNomBod.setBounds(210, 160, 230, 20);

        butBusBod.setText("jButton2");
        butBusBod.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusBodActionPerformed(evt);
            }
        });
        panFilCab.add(butBusBod);
        butBusBod.setBounds(440, 160, 20, 20);

        scrBar.setViewportView(panFilCab);

        tabFrm.addTab("Filtro", scrBar);

        jPanel9.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(180);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel3.setLayout(new java.awt.BorderLayout());

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

        jPanel3.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        lblOrdDes.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        lblOrdDes.setText(" Orden de despacho");
        jPanel3.add(lblOrdDes, java.awt.BorderLayout.PAGE_START);

        jSplitPane1.setLeftComponent(jPanel3);
        jPanel3.getAccessibleContext().setAccessibleParent(jSplitPane1);

        jPanel1.setLayout(new java.awt.BorderLayout());

        tblgui.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblgui);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        chkMosGuiRem.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        chkMosGuiRem.setText("Mostrar guías de remisión.");
        chkMosGuiRem.setPreferredSize(new java.awt.Dimension(269, 20));
        chkMosGuiRem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosGuiRemActionPerformed(evt);
            }
        });
        jPanel1.add(chkMosGuiRem, java.awt.BorderLayout.NORTH);

        jSplitPane1.setRightComponent(jPanel1);

        jPanel9.add(jSplitPane1, java.awt.BorderLayout.PAGE_START);

        tabFrm.addTab("Reporte", jPanel9);

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

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
}//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length() > 0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void txtCodItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodItmActionPerformed
        txtCodItm.transferFocus();
}//GEN-LAST:event_txtCodItmActionPerformed

    private void txtCodItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodItmFocusGained
        strCodItm = txtCodItm.getText();
        txtCodItm.selectAll();
}//GEN-LAST:event_txtCodItmFocusGained

    private void txtCodItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodItmFocusLost
        if (!txtCodItm.getText().equalsIgnoreCase(strCodItm)) {
            if (txtCodItm.getText().equals("")) {
                txtCodItm.setText("");
                txtDesItm.setText("");
            } else {
                BuscarItm("co_itm", txtCodItm.getText(), 1);
            }
        } else {
            txtCodItm.setText(strCodItm);
        }
}//GEN-LAST:event_txtCodItmFocusLost

    private void txtDesItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesItmActionPerformed
        txtDesItm.transferFocus();
}//GEN-LAST:event_txtDesItmActionPerformed

    private void txtDesItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesItmFocusGained
        strDesItm = txtDesItm.getText();
        txtDesItm.selectAll();
}//GEN-LAST:event_txtDesItmFocusGained

    private void txtDesItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesItmFocusLost
        if (!txtDesItm.getText().equalsIgnoreCase(strDesItm)) {
            if (txtDesItm.getText().equals("")) {
                txtCodItm.setText("");
                txtDesItm.setText("");
            } else {
                BuscarItm("tx_nomitm", txtDesItm.getText(), 3);
            }
        } else {
            txtDesItm.setText(strDesItm);
        }
}//GEN-LAST:event_txtDesItmFocusLost

    private void butSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSolActionPerformed
        vcoItm.setTitle("Listado de Items");
        vcoItm.setCampoBusqueda(1);
        vcoItm.show();
        if (vcoItm.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
            txtCodItm.setText(vcoItm.getValueAt(2));
            txtDesItm.setText(vcoItm.getValueAt(4));
            optFil.setSelected(true);

        }
}//GEN-LAST:event_butSolActionPerformed

    public void BuscarItm(String campo, String strBusqueda, int tipo) {
        vcoItm.setTitle("Listado de Vendedores");
        if (vcoItm.buscar(campo, strBusqueda)) {
            txtCodItm.setText(vcoItm.getValueAt(2));
            txtDesItm.setText(vcoItm.getValueAt(4));
            optFil.setSelected(true);
        } else {
            vcoItm.setCampoBusqueda(tipo);
            vcoItm.cargarDatos();
            vcoItm.show();
            if (vcoItm.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                txtCodItm.setText(vcoItm.getValueAt(2));
                txtDesItm.setText(vcoItm.getValueAt(4));
                optFil.setSelected(true);
            } else {
                txtCodItm.setText(strCodItm);
                txtDesItm.setText(strDesItm);
            }
        }
    }


    private void txtcodaltdesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodaltdesFocusGained
        txtcodaltdes.selectAll();
}//GEN-LAST:event_txtcodaltdesFocusGained

    private void txtcodaltdesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodaltdesFocusLost
        if (txtcodaltdes.getText().length() > 0) {
            optFil.setSelected(true);
            if (txtcodalthas.getText().length() == 0) {
                txtcodalthas.setText(txtcodaltdes.getText());
            }
        }
}//GEN-LAST:event_txtcodaltdesFocusLost

    private void txtcodalthasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodalthasFocusGained
        txtcodalthas.selectAll();
}//GEN-LAST:event_txtcodalthasFocusGained

    private void txtcodalthasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodalthasFocusLost
        if (txtcodalthas.getText().length() > 0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtcodalthasFocusLost

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected()) {

            txtCodItm.setText("");
            txtDesItm.setText("");
            strCodItm = "";
            strDesItm = "";

            txtcodaltdes.setText("");
            txtcodalthas.setText("");

        }
}//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        txtCodItm.setText("");
        txtDesItm.setText("");
        strCodItm = "";
        strDesItm = "";
        txtCodAltItmTer.setText("");
        txtcodaltdes.setText("");
        txtcodalthas.setText("");
}//GEN-LAST:event_optTodActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar")) {

            if (objThrGUI == null) {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
            }
        }


    }//GEN-LAST:event_butConActionPerformed

    private class ZafThreadGUI extends Thread {

        @Override
        public void run() {

            if (validarDat()) {

                if (!cargarDetReg()) {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                }

                //Establecer el foco en el JTable sólo cuando haya datos.
                if (tblDat.getRowCount() > 0) {
                    tabFrm.setSelectedIndex(1);
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.requestFocus();
                }
            }

            objThrGUI = null;

        }
    }

    private boolean validarDat() {
        boolean blnRes = true;
        int intEstBod = 0;

        for (int x = 0; x < tblBod.getRowCount(); x++) {
            if (tblBod.getValueAt(x, INT_TBL_CHKBOD).toString().equals("true")) {
                intEstBod = 1;
            }
        }

        if (intEstBod == 0) {
            MensajeInf("LA BODEGA ORIGEN ES OBLIGATORIO...");
            return false;
        }

        if (txtCodBod.getText().equals("")) {
            MensajeInf("LA BODEGA DE DESTINO ES OBLIGATORIO...");
            txtCodBod.requestFocus();
            return false;
        }
        return blnRes;
    }

    private void MensajeInf(String strMensaje) {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this, strMensaje, strTit, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función obtiene la condición SQL adicional para los campos que
     * "Terminan con". La cadena recibida es separada para formar la condición
     * que se agregará la sentencia SQL. Por ejemplo: Si strCam="a2.tx_codAlt" y
     * strCad="I, S, L" el resultado sería "AND (a2.tx_codalt LIKE '%I' OR
     * a2.tx_codalt LIKE '%S' OR a2.tx_codalt LIKE '%L')"
     *
     * @param strCam El campo que se utilizará para la condición.
     * @param strCad La cadena que se separará para formar la condición.
     * @return La cadena que contiene la condición SQL .
     */
    private String getConSQLAdiCamTer(String strCam, String strCad) {
        byte i;
        String strRes = "";
        try {
            if (strCad.length() > 0) {
                java.util.StringTokenizer stkAux = new java.util.StringTokenizer(strCad, ",", false);
                i = 0;
                while (stkAux.hasMoreTokens()) {
                    if (i == 0) {
                        strRes += " (LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    } else {
                        strRes += " OR LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    }
                    i++;
                }
                strRes += ")";
            }
        } catch (java.util.NoSuchElementException e) {
            strRes = "";
        }
        return strRes;
    }

    /**
     * Esta función permite consultar los registros de acuerdo al criterio
     * seleccionado.
     *
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg() {
        boolean blnRes = true;
        java.sql.Connection conn;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        int intNumTotReg = 0, i = 0;
        String strSql = "", strSqlAuxFec = "", strSqlAuxConf = "", strSqlAuxItm = "";
        String strBodDes = "", strEstConf = "";
        try {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");

            if (INTCODREGCEN != 0) {
                conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(INTCODREGCEN), objParSis.getUsuarioBaseDatos(INTCODREGCEN), objParSis.getClaveBaseDatos(INTCODREGCEN));
            } else {
                conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            }

            if (conn != null) {
                stm = conn.createStatement();

                if (objSelFec.isCheckBoxChecked()) {
                    switch (objSelFec.getTipoSeleccion()) {
                        case 0: //Búsqueda por rangos
                            strSqlAuxFec += " AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strSqlAuxFec += " AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strSqlAuxFec += " AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }

                for (int x = 0; x < tblBod.getRowCount(); x++) {
                    if (tblBod.getValueAt(x, INT_TBL_CHKBOD).toString().equals("true")) {
                        if (!strBodDes.equals("")) {
                            strBodDes += ",";
                        }
                        strBodDes += tblBod.getValueAt(x, INT_TBL_CODBOD).toString();
                    }
                }

                strSqlAuxConf = "";

                if (chkMosPenConf.isSelected()) {
                    strEstConf = "'P'";
                }
                if (chkDocConfPar.isSelected()) {
                    if (strEstConf.equals("")) {
                        strEstConf = "'E'";
                    } else {
                        strEstConf += ",'E'";
                    }
                }
                if (chkDocConfTot.isSelected()) {
                    if (strEstConf.equals("")) {
                        strEstConf = "'C'";
                    } else {
                        strEstConf += ",'C'";
                    }
                }

                if (!strEstConf.equals("")) {
                    strSqlAuxConf = "  AND (CASE WHEN a.st_coninv IN ('P','E') THEN a11.st_coninv IN (" + strEstConf + ") ELSE a.st_coninv IN (" + strEstConf + ") END)";
                }

                if (optFil.isSelected()) {
                    if (txtcodaltdes.getText().length() > 0 || txtcodalthas.getText().length() > 0) {
                        strSqlAuxItm += " AND ((LOWER(a1.tx_codalt) BETWEEN '" + txtcodaltdes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtcodalthas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codalt) LIKE '" + txtcodalthas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                    }

                    if (txtCodItm.getText().length() > 0) {
                        strSqlAuxItm += "  AND LOWER(a1.tx_codalt) LIKE '%" + txtCodItm.getText().replaceAll("'", "''").toLowerCase() + "%' ";
                    }

                    if (txtCodAltItmTer.getText().length() > 0) {
                        strSqlAuxItm += "  AND " + getConSQLAdiCamTer("a1.tx_codAlt", txtCodAltItmTer.getText());
                    }
                }


                /*strSql="SELECT " +
                 " ( select sum(( x1.nd_pesitmkgr*abs(x.nd_can))) as kgramo  from tbm_detguirem as x  " +
                 " INNER JOIN TBM_INV as x1 on (x1.co_emp=x.co_emp and x1.co_itm=x.co_itm)  " +
                 " where x.co_emp=z.co_emp and x.co_loc=z.co_loc and x.co_tipdoc=z.co_tipdoc and x.co_doc=  z.co_doc " +
                 " ) as  kgramo  ,z.*  FROM ( " +
                 " SELECT a.co_emp, a.co_loc, a10.tx_nom as nomloc, a.co_tipdoc, a.co_doc, a.co_clides, a.tx_nomclides,  a8.tx_descor, a8.tx_deslar, case when a.ne_numdoc = 0 then null else a.ne_numdoc end as ne_numdoc, " +
                 " a.fe_doc, a.co_ptopar as co_bod, a9.tx_nom as nombod, a.st_coninv as estConfGuia, a7.co_bodGrp, a.ne_numorddes " +
                 " FROM tbm_cabguirem as a  " +
                 "  INNER JOIN tbm_detguirem as a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )   " +
                 " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
                 " INNER JOIN tbr_bodEmpBodGrp AS a7 ON (a7.co_emp=a.co_emp AND a7.co_bod=a.co_ptodes) " +
                 " INNER JOIN  tbm_cabtipdoc as a8 ON (a8.co_emp=a.co_emp and a8.co_loc=a.co_loc and a8.co_tipdoc=a.co_tipdoc ) " +
                 " INNER JOIN  tbm_bod as a9 ON (a9.co_emp=a.co_emp and a9.co_bod=a.co_ptopar  )  " +
                 " INNER JOIN  tbm_loc as a10 ON (a10.co_emp=a.co_emp and a10.co_loc=a.co_loc  )    " +
                 " WHERE  a.st_reg not in('E')   and a.co_tipdoc in ( 101,102 ) " +
                 " AND ( a6.co_empGrp= 0  AND a6.co_bodGrp in ( "+strBodDes+"  ) ) " +
                 "               AND ( a7.co_empGrp= 0  AND a7.co_bodGrp =  "+txtCodBod.getText()+"  ) " +
                 "               "+strSqlAuxFec+" " +
                 "               "+strSqlAuxConf+"   "+strSqlAuxItm+"  and  (  a.ne_numdoc > 0  or a.ne_numorddes > 0 )  and  a1.co_tipdocrel  is not null   " +
                 "     GROUP BY " +
                 "  a.co_emp, a.co_loc, a10.tx_nom, a.co_tipdoc, a.co_doc, a.co_clides, a.tx_nomclides,  a8.tx_descor, a8.tx_deslar, " +
                 "  a.ne_numdoc, a.fe_doc, a.co_ptopar, a9.tx_nom, a.st_coninv,  a7.co_bodGrp  , a.ne_numorddes " +
                 "         ORDER BY a.ne_numdoc  ) as z  ORDER BY ne_numdoc, ne_numorddes  ";*/
                // José Marín M. 1/Ago/2014
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {
                    strSql = " SELECT (        \n";
                    //strSql+="            SELECT sum(( x1.nd_pesitmkgr*abs( (x.nd_can - x.nd_canNunRec -x.nd_canCon)  ))) as kgramo \n";
                    strSql += "      select   sum(x1.nd_pesitmkgr*abs((x.nd_can-x.nd_canNunRec- x.nd_cancon)+ (x2.nd_can+x2.nd_cannunrec))) as kgramo  \n";
                    strSql += "            FROM tbm_detguirem as x \n";
                    strSql += "            INNER JOIN TBM_INV as x1 on (x1.co_emp=x.co_emp and x1.co_itm=x.co_itm) \n";
                    strSql += "            INNER JOIN tbm_detingegrmerbod as x2 on (x2.co_emp=x.co_emp and x2.co_locrelguirem=x.co_loc and x2.co_tipdocrelguirem=x.co_tipdoc and x2.co_docrelguirem=x.co_doc and x2.co_regrelguirem=x.co_reg) \n";
                    strSql += "            WHERE x.co_emp=z.co_emp and x.co_loc=z.co_loc and x.co_tipdoc=z.co_tipdoc and x.co_doc=  z.co_doc \n";
                    strSql += "       ) as  kgramoPend, \n";
                    strSql += "  \n";
                    strSql += "       (    SELECT sum(( x1.nd_pesitmkgr*abs(x.nd_can))) as kgramo \n";
                    strSql += "            FROM tbm_detguirem as x \n";
                    strSql += "            INNER JOIN TBM_INV as x1 on (x1.co_emp=x.co_emp and x1.co_itm=x.co_itm) \n";
                    strSql += "            WHERE x.co_emp=z.co_emp and x.co_loc=z.co_loc and x.co_tipdoc=z.co_tipdoc and x.co_doc=  z.co_doc \n";
                    strSql += "        ) as  kgramo, \n";
                    strSql += "  \n";
                    strSql += " z.* \n";
                    strSql += " FROM ( \n";
                    strSql += " SELECT a.co_emp, a.co_loc, a10.tx_nom as nomloc, a.co_tipdoc, a.co_doc, a.co_clides, a.tx_nomclides,  a8.tx_descor, \n";
                    strSql += "        a8.tx_deslar, case when a.ne_numdoc = 0 then null else a.ne_numdoc end as ne_numdoc, \n";
                    strSql += "        a.fe_doc, a.co_ptopar as co_bod, a9.tx_nom as nombod,  a7.co_bodGrp, a.ne_numorddes, a3.st_cliretemprel as cliente_retira, \n";
                    strSql += "  CASE WHEN a.st_coninv IN ('P','E') THEN a11.st_coninv ELSE a.st_coninv END as estConfGuia\n";
                    strSql += " FROM tbm_cabguirem as a \n";
                    strSql += " INNER JOIN tbm_detguirem as a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) \n";
                    strSql += " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) \n";
                    //strSql+=" INNER JOIN tbr_bodEmpBodGrp AS a7 ON (a7.co_bodgrp=a.co_ptodes or (a7.co_bod=a.co_ptodes and a7.co_emp=a.co_emp))  \n" ;
                    strSql += " INNER JOIN tbr_bodEmpBodGrp AS a7";
                    strSql += " ON ( case when a1.co_tipdocrel =206 and a.fe_doc>='2016-07-26' and a.fe_doc<'2016-09-09'  then  (a7.co_bodgrp=a.co_ptodes )  else (a7.co_bod=a.co_ptodes and a7.co_emp=a.co_emp) end ) \n";

                    strSql += " INNER JOIN tbm_cabtipdoc as a8 ON (a8.co_emp=a.co_emp and a8.co_loc=a.co_loc and a8.co_tipdoc=a.co_tipdoc ) \n";
                    strSql += " INNER JOIN tbm_bod as a9 ON (a9.co_emp=a.co_emp and a9.co_bod=a.co_ptopar ) \n";
                    strSql += " INNER JOIN tbm_loc as a10 ON (a10.co_emp=a.co_emp and a10.co_loc=a.co_loc ) \n";
                    strSql += " inner join tbm_detmovinv as a3 on (a3.co_emp=a1.co_emprel and a3.co_loc=a1.co_locrel and a3.co_tipdoc=a1.co_tipdocrel and  \n";
                    strSql += "                                    a3.co_doc=a1.co_docrel and a3.co_reg=a1.co_regrel )\n";
                    strSql += " inner join tbr_detmovinv as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and  \n";
                    strSql += "                                    a4.co_doc=a3.co_doc and a4.co_reg=a3.co_reg ) \n";
                    strSql += " inner join tbm_detmovinv as a5 on (a5.co_emp=a4.co_emprel and a5.co_loc=a4.co_locrel and a5.co_tipdoc=a4.co_tipdocrel and  \n";
                    strSql += "                                    a5.co_doc=a4.co_docrel and a5.co_reg=a4.co_regrel ) \n";
                    strSql += " inner join tbm_cabmovinv as a11 on (a11.co_emp=a3.co_emp and a11.co_loc=a3.co_loc and a11.co_tipdoc=a3.co_tipdoc and  \n";
                    strSql += "                                    a11.co_doc=a3.co_doc ) \n";
                    strSql += " WHERE a.st_reg not in('E', 'I') and a.co_tipdoc in ( 101,102,271 ) \n";
                    strSql += " AND ( a6.co_empGrp= 0 AND a6.co_bodGrp in ( " + strBodDes + "  ) ) \n";
                    strSql += " AND ( a7.co_empGrp= 0 AND a7.co_bodGrp = " + txtCodBod.getText() + " ) \n";
                    strSql += " " + strSqlAuxFec + " \n";
                    strSql += " " + strSqlAuxConf + " \n";
                    strSql += " " + strSqlAuxItm + " \n";
                    strSql += " and ( a.ne_numdoc > 0 or a.ne_numorddes > 0 ) and a1.co_tipdocrel is not null \n";
                    strSql += " GROUP BY a.co_emp, a.co_loc, a10.tx_nom, a.co_tipdoc, a.co_doc, a.co_clides, a.tx_nomclides,  a8.tx_descor, a8.tx_deslar,   \n";
                    strSql += "          a.ne_numdoc, a.fe_doc, a.co_ptopar, a9.tx_nom, a11.st_coninv, a7.co_bodGrp, a.ne_numorddes, a3.st_cliretemprel \n";
                    strSql += " ORDER BY a.ne_numdoc \n";
                    strSql += " ) as z \n";
                    strSql += " ORDER BY ne_numdoc, ne_numorddes \n";
                } else {
                    strSql = " SELECT (        \n";
                    //strSql+="            SELECT sum(( x1.nd_pesitmkgr*abs( (x.nd_can - x.nd_canNunRec -x.nd_canCon)  ))) as kgramo \n";
                    strSql += "   select    sum(x1.nd_pesitmkgr*abs((x.nd_can-x.nd_canNunRec- x.nd_cancon)+ (x2.nd_can+x2.nd_cannunrec))) as kgramo  \n";
                    strSql += "            FROM tbm_detguirem as x \n";
                    strSql += "            INNER JOIN TBM_INV as x1 on (x1.co_emp=x.co_emp and x1.co_itm=x.co_itm) \n";
                    strSql += "            INNER JOIN tbm_detingegrmerbod as x2 on (x2.co_emp=x.co_emp and x2.co_locrelguirem=x.co_loc and x2.co_tipdocrelguirem=x.co_tipdoc and x2.co_docrelguirem=x.co_doc and x2.co_regrelguirem=x.co_reg) \n";
                    strSql += "            WHERE x.co_emp=z.co_emp and x.co_loc=z.co_loc and x.co_tipdoc=z.co_tipdoc and x.co_doc=  z.co_doc \n";
                    strSql += "       ) as  kgramoPend, \n";
                    strSql += "  \n";
                    strSql += "       (    SELECT sum(( x1.nd_pesitmkgr*abs(x.nd_can))) as kgramo \n";
                    strSql += "            FROM tbm_detguirem as x \n";
                    strSql += "            INNER JOIN TBM_INV as x1 on (x1.co_emp=x.co_emp and x1.co_itm=x.co_itm) \n";
                    strSql += "            WHERE x.co_emp=z.co_emp and x.co_loc=z.co_loc and x.co_tipdoc=z.co_tipdoc and x.co_doc=  z.co_doc \n";
                    strSql += "        ) as  kgramo, \n";
                    strSql += "  \n";
                    strSql += " z.* \n";
                    strSql += " FROM ( \n";
                    strSql += " SELECT a.co_emp, a.co_loc, a10.tx_nom as nomloc, a.co_tipdoc, a.co_doc, a.co_clides, a.tx_nomclides,  a8.tx_descor, \n";
                    strSql += "        a8.tx_deslar, case when a.ne_numdoc = 0 then null else a.ne_numdoc end as ne_numdoc, \n";
                    strSql += "        a.fe_doc, a.co_ptopar as co_bod, a9.tx_nom as nombod,  a7.co_bodGrp, a.ne_numorddes, a3.st_cliretemprel as cliente_retira, \n";
                    strSql += "  CASE WHEN a.st_coninv IN ('P','E') THEN a11.st_coninv ELSE a.st_coninv END as estConfGuia\n";
                    strSql += " FROM tbm_cabguirem as a \n";
                    strSql += " INNER JOIN tbm_detguirem as a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) \n";
                    strSql += " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) \n";
                    //strSql+=" INNER JOIN tbr_bodEmpBodGrp AS a7 ON (a7.co_bodgrp=a.co_ptodes or (a7.co_bod=a.co_ptodes and a7.co_emp=a.co_emp))  \n" ;
                    strSql += " INNER JOIN tbr_bodEmpBodGrp AS a7";
                    strSql += " ON ( case when a1.co_tipdocrel =206 and a.fe_doc>='2016-07-26' and a.fe_doc<'2016-09-09'  then  (a7.co_bodgrp=a.co_ptodes )  else (a7.co_bod=a.co_ptodes and a7.co_emp=a.co_emp) end ) \n";
                    strSql += " INNER JOIN tbm_cabtipdoc as a8 ON (a8.co_emp=a.co_emp and a8.co_loc=a.co_loc and a8.co_tipdoc=a.co_tipdoc ) \n";
                    strSql += " INNER JOIN tbm_bod as a9 ON (a9.co_emp=a.co_emp and a9.co_bod=a.co_ptopar ) \n";
                    strSql += " INNER JOIN tbm_loc as a10 ON (a10.co_emp=a.co_emp and a10.co_loc=a.co_loc ) \n";
                    strSql += " inner join tbm_detmovinv as a3 on (a3.co_emp=a1.co_emprel and a3.co_loc=a1.co_locrel and a3.co_tipdoc=a1.co_tipdocrel and  \n";
                    strSql += "                                    a3.co_doc=a1.co_docrel and a3.co_reg=a1.co_regrel )\n";
                    strSql += " inner join tbr_detmovinv as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and  \n";
                    strSql += "                                    a4.co_doc=a3.co_doc and a4.co_reg=a3.co_reg ) \n";
                    strSql += " inner join tbm_detmovinv as a5 on (a5.co_emp=a4.co_emprel and a5.co_loc=a4.co_locrel and a5.co_tipdoc=a4.co_tipdocrel and  \n";
                    strSql += "                                    a5.co_doc=a4.co_docrel and a5.co_reg=a4.co_regrel ) \n";
                    strSql += " inner join tbm_cabmovinv as a11 on (a11.co_emp=a3.co_emp and a11.co_loc=a3.co_loc and a11.co_tipdoc=a3.co_tipdoc and  \n";
                    strSql += "                                    a11.co_doc=a3.co_doc ) \n";
                    strSql += " WHERE a.st_reg not in('E', 'I') and a.co_tipdoc in ( 101,102,271 ) \n";
                    strSql += " AND ( a6.co_empGrp= 0 AND a6.co_bodGrp in ( " + strBodDes + "  ) ) \n";
                    strSql += " AND ( a7.co_empGrp= 0 AND a7.co_bodGrp = " + txtCodBod.getText() + " ) \n";
                    strSql += " " + strSqlAuxFec + " \n";
                    strSql += " " + strSqlAuxConf + " \n";
                    strSql += " " + strSqlAuxItm + " \n";
                    strSql += " and ( a.ne_numdoc > 0 or a.ne_numorddes > 0 ) and a1.co_tipdocrel is not null \n";
                    strSql += " GROUP BY a.co_emp, a.co_loc, a10.tx_nom, a.co_tipdoc, a.co_doc, a.co_clides, a.tx_nomclides,  a8.tx_descor, a8.tx_deslar,   \n";
                    strSql += "          a.ne_numdoc, a.fe_doc, a.co_ptopar, a9.tx_nom, a.st_coninv, a11.st_coninv, a7.co_bodGrp, a.ne_numorddes, a3.st_cliretemprel \n";
                    strSql += " ORDER BY a.ne_numdoc \n";
                    strSql += " ) as z  \n";
                    //strSql+=" ) as z where z.co_emp="+objParSis.getCodigoEmpresa() ; // Rose
                    strSql += " ORDER BY ne_numdoc, ne_numorddes \n";
                }

                //System.out.println("ZafCom48.cargarDetReg: " + strSql);
                rst = stm.executeQuery(strSql);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i = 0;
                while (rst.next()) {

                    vecReg = new Vector();
                    vecReg.add(INT_TBLD_LIN, "");
                    vecReg.add(INT_TBLD_CODEMP, rst.getString("co_emp"));
                    vecReg.add(INT_TBLD_CODLOC, rst.getString("co_loc"));
                    vecReg.add(INT_TBLD_NOMLOC, rst.getString("nomloc"));
                    vecReg.add(INT_TBLD_CODTID, rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBLD_DESTIDC, rst.getString("tx_descor"));
                    vecReg.add(INT_TBLD_DESTIDL, rst.getString("tx_deslar"));
                    vecReg.add(INT_TBLD_CODDOC, rst.getString("co_doc"));
                    vecReg.add(INT_TBLD_NUMDOC, rst.getString("ne_numdoc"));
                    vecReg.add(INT_TBLD_NUMORDES, rst.getString("ne_numorddes"));
                    vecReg.add(INT_TBLD_BUTGUIA, "...");

                    vecReg.add(INT_TBLD_PESOKG, rst.getString("kgramo"));
                    if (rst.getString("estConfGuia").equals("P")) {
                        vecReg.add(INT_TBLD_PESOKGPEN, rst.getString("kgramo"));
                    } else {
                        vecReg.add(INT_TBLD_PESOKGPEN, rst.getString("kgramoPend"));
                    }

                    vecReg.add(INT_TBLD_FECDOC, rst.getString("fe_doc"));
                    vecReg.add(INT_TBLD_CODBOD, rst.getString("co_bod"));
                    vecReg.add(INT_TBLD_NOMBOD, rst.getString("nombod"));

                    vecReg.add(INT_TBLD_CLIRET, (rst.getString("cliente_retira") == null ? "N" : rst.getString("cliente_retira").equals("S") ? "S" : "N"));

                    if (rst.getString("estConfGuia").equals("P")) {
                        vecReg.add(INT_TBLD_STCONPEN, true);
                        vecReg.add(INT_TBLD_STCONPAR, false);
                        vecReg.add(INT_TBLD_STCONTOT, false);
                    } else if (rst.getString("estConfGuia").equals("C")) {
                        vecReg.add(INT_TBLD_STCONPEN, false);
                        vecReg.add(INT_TBLD_STCONPAR, false);
                        vecReg.add(INT_TBLD_STCONTOT, true);
                    } else if (rst.getString("estConfGuia").equals("E")) {
                        vecReg.add(INT_TBLD_STCONPEN, false);
                        vecReg.add(INT_TBLD_STCONPAR, true);
                        vecReg.add(INT_TBLD_STCONTOT, false);
                    } else if (rst.getString("estConfGuia").equals("F")) {
                        vecReg.add(INT_TBLD_STCONPEN, false);
                        vecReg.add(INT_TBLD_STCONPAR, false);
                        vecReg.add(INT_TBLD_STCONTOT, false);
                    } else {
                        vecReg.add(INT_TBLD_STCONPEN, false);
                        vecReg.add(INT_TBLD_STCONPAR, false);
                        vecReg.add(INT_TBLD_STCONTOT, false);
                    }

                    vecReg.add(INT_TBLD_BUTCONF, "...");

                    vecDat.add(vecReg);
                    i++;
                    pgrSis.setValue(i);
                }
                rst.close();
                rst = null;

                //Asignar vectores al modelo.
                objTblModDT.setData(vecDat);
                tblDat.setModel(objTblModDT);
                vecDat.clear();

                stm.close();
                stm = null;

                if (INTCODREGCEN != 0) {
                    conn.close();
                    conn = null;
                } else {
                    conn.close();
                    conn = null;
                }

                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");

                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
}//GEN-LAST:event_butCerActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (JOptionPane.showConfirmDialog(this, strMsg, strTit, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
        txtCodBod.transferFocus();
}//GEN-LAST:event_txtCodBodActionPerformed

    private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
        strCodBod = txtCodBod.getText();
        txtCodBod.selectAll();
}//GEN-LAST:event_txtCodBodFocusGained

    private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
        if (!txtCodBod.getText().equalsIgnoreCase(strCodBod)) {
            if (txtCodBod.getText().equals("")) {
                txtCodBod.setText("");
                txtNomBod.setText("");
            } else {
                BuscarBod("a.co_bod", txtCodBod.getText(), 0);
            }
        } else {
            txtCodBod.setText(strCodBod);
        }
}//GEN-LAST:event_txtCodBodFocusLost

    private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
        txtNomBod.transferFocus();
}//GEN-LAST:event_txtNomBodActionPerformed

    private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
        strNomBod = txtNomBod.getText();
        txtNomBod.selectAll();
}//GEN-LAST:event_txtNomBodFocusGained

    public void BuscarBod(String campo, String strBusqueda, int tipo) {
        vcoBod.setTitle("Listado de Bodegas");
        if (vcoBod.buscar(campo, strBusqueda)) {
            txtCodBod.setText(vcoBod.getValueAt(1));
            txtNomBod.setText(vcoBod.getValueAt(2));
            strCodBod = vcoBod.getValueAt(1);
            strNomBod = vcoBod.getValueAt(2);
        } else {
            vcoBod.setCampoBusqueda(tipo);
            vcoBod.cargarDatos();
            vcoBod.show();
            if (vcoBod.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                txtCodBod.setText(vcoBod.getValueAt(1));
                txtNomBod.setText(vcoBod.getValueAt(2));
                strCodBod = vcoBod.getValueAt(1);
                strNomBod = vcoBod.getValueAt(2);
            } else {
                txtCodBod.setText(strCodBod);
                txtNomBod.setText(strNomBod);
            }
        }
    }

    private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
        if (!txtNomBod.getText().equalsIgnoreCase(strNomBod)) {
            if (txtNomBod.getText().equals("")) {
                txtCodBod.setText("");
                txtNomBod.setText("");
            } else {
                BuscarBod("a.tx_nom", txtNomBod.getText(), 1);
            }
        } else {
            txtNomBod.setText(strNomBod);
        }
}//GEN-LAST:event_txtNomBodFocusLost

    private void butBusBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusBodActionPerformed
        vcoBod.setTitle("Listado de Bodegas");
        vcoBod.setCampoBusqueda(1);
        vcoBod.show();
        if (vcoBod.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
            txtCodBod.setText(vcoBod.getValueAt(1));
            txtNomBod.setText(vcoBod.getValueAt(2));
            strCodBod = vcoBod.getValueAt(1);
            strNomBod = vcoBod.getValueAt(2);
        }
}//GEN-LAST:event_butBusBodActionPerformed

    private void chkMosGuiRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosGuiRemActionPerformed
        objTblModTG.removeAllRows();
        if (chkMosGuiRem.isSelected()) {
            cargarGuiSec();
        }
}//GEN-LAST:event_chkMosGuiRemActionPerformed

    private boolean cargarGuiSec() {
        boolean blnRes = true;
        java.sql.Connection conn;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        int intNumTotReg = 0, i = 0;
        String strSql = "";
        int intColSel = 0;
        try {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");

            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());

            if (conn != null) {
                stm = conn.createStatement();
                intColSel = tblDat.getSelectedRow();

                strSql = " SELECT  ";
                strSql += "        ( select sum(( x1.nd_pesitmkgr*abs(x.nd_can))) as kgramo  from tbm_detguirem as x    ";
                strSql += "             INNER JOIN TBM_INV as x1 on (x1.co_emp=x.co_emp and x1.co_itm=x.co_itm)   ";
                strSql += "             where x.co_emp=z.co_emp and x.co_loc=z.co_loc and x.co_tipdoc=z.co_tipdoc and x.co_doc=  z.co_doc  ) as  kgramo, ";
                strSql += "         (   SELECT sum(( x1.nd_pesitmkgr*abs( (x.nd_can - x.nd_canNunRec -x.nd_canCon)  ))) as kgramo \n";
                //strSql+="         (   SELECT sum(x1.nd_pesitmkgr*abs((x.nd_can-x.nd_canNunRec- x.nd_cancon)+ (x2.nd_can+x2.nd_cannunrec))) as kgramo  \n";
                strSql += "             FROM tbm_detguirem as x \n";
                strSql += "              INNER JOIN TBM_INV as x1 on (x1.co_emp=x.co_emp and x1.co_itm=x.co_itm) \n";
                //strSql+="              INNER JOIN tbm_detingegrmerbod as x2 on (x2.co_emp=x.co_emp and x2.co_locrelguirem=x.co_loc and x2.co_tipdocrelguirem=x.co_tipdoc and x2.co_docrelguirem=x.co_doc and x2.co_regrelguirem=x.co_reg) \n";
                strSql += "              WHERE x.co_emp=z.co_emp and x.co_loc=z.co_loc and x.co_tipdoc=z.co_tipdoc and x.co_doc=  z.co_doc \n";
                strSql += "         ) as  kgramoPend, \n";
                strSql += " z.* ";
                strSql += " from ( ";
                strSql += " SELECT  ";
                strSql += " a.co_emp, a.co_loc, a10.tx_nom as nomloc, a.co_tipdoc, a.co_doc, a.co_clides, a.tx_nomclides,  a8.tx_descor, a8.tx_deslar, a.ne_numdoc,   ";
                strSql += "  a.ne_numorddes, ";
                strSql += "  a.fe_doc,  ";
                strSql += "  a.co_ptopar as co_bod, a9.tx_nom as nombod, a.st_coninv as estConfGuia, a7.co_bodGrp   ";
                strSql += "  FROM tbr_cabguirem as x ";
                strSql += "  INNER JOIN tbm_cabguirem as a ON (a.co_emp=x.co_emp and a.co_loc=x.co_loc and a.co_tipdoc=x.co_tipdoc and a.co_doc=x.co_doc )     ";
                strSql += "  INNER JOIN tbm_detguirem as a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  ";
                strSql += "  INNER JOIN  tbm_cabtipdoc as a8 ON (a8.co_emp=a.co_emp and a8.co_loc=a.co_loc and a8.co_tipdoc=a.co_tipdoc )  ";
                strSql += "  INNER JOIN tbr_bodEmpBodGrp AS a7 ON (a7.co_emp=a.co_emp AND a7.co_bod=a.co_ptodes)   ";
                strSql += "  INNER JOIN  tbm_bod as a9 ON (a9.co_emp=a.co_emp and a9.co_bod=a.co_ptopar  )    ";
                strSql += "  INNER JOIN  tbm_loc as a10 ON (a10.co_emp=a.co_emp and a10.co_loc=a.co_loc  )      ";
                strSql += "  where x.co_emp=" + objTblModDT.getValueAt(intColSel, INT_TBLD_CODEMP) + " and x.co_locrel=" + objTblModDT.getValueAt(intColSel, INT_TBLD_CODLOC) + " ";
                strSql += "  and x.co_tipdocrel= " + objTblModDT.getValueAt(intColSel, INT_TBLD_CODTID) + " and x.co_docrel=  " + objTblModDT.getValueAt(intColSel, INT_TBLD_CODDOC) + " and a.st_reg='A' ";
                strSql += " group by  a.co_emp, a.co_loc, a10.tx_nom , a.co_tipdoc, a.co_doc, a.co_clides, a.tx_nomclides,  a8.tx_descor, a8.tx_deslar,  ";
                strSql += " a.ne_numdoc, a.ne_numorddes, a.fe_doc,  a.co_ptopar, a9.tx_nom, a.st_coninv , a7.co_bodGrp  ";
                strSql += " order by a.ne_numdoc  ) as z ";

                rst = stm.executeQuery(strSql);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i = 0;
                while (rst.next()) {
                    vecReg = new Vector();
                    vecReg.add(INT_TBLD_LIN, "");
                    vecReg.add(INT_TBLD_CODEMP, rst.getString("co_emp"));
                    vecReg.add(INT_TBLD_CODLOC, rst.getString("co_loc"));
                    vecReg.add(INT_TBLD_NOMLOC, rst.getString("nomloc"));
                    vecReg.add(INT_TBLD_CODTID, rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBLD_DESTIDC, rst.getString("tx_descor"));
                    vecReg.add(INT_TBLD_DESTIDL, rst.getString("tx_deslar"));
                    vecReg.add(INT_TBLD_CODDOC, rst.getString("co_doc"));
                    vecReg.add(INT_TBLD_NUMDOC, rst.getString("ne_numdoc"));
                    vecReg.add(INT_TBLD_NUMORDES, "");
                    vecReg.add(INT_TBLD_BUTGUIA, "...");

                    vecReg.add(INT_TBLD_PESOKG, rst.getString("kgramo"));
                    vecReg.add(INT_TBLD_PESOKGPEN, rst.getString("kgramoPend"));

                    vecReg.add(INT_TBLD_FECDOC, rst.getString("fe_doc"));
                    vecReg.add(INT_TBLD_CODBOD, rst.getString("co_bod"));
                    vecReg.add(INT_TBLD_NOMBOD, rst.getString("nombod"));

                    vecReg.add(INT_TBLD_CLIRET, "");

                    if (rst.getString("estConfGuia").equals("P")) {
                        vecReg.add(INT_TBLD_STCONPEN, true);
                        vecReg.add(INT_TBLD_STCONPAR, false);
                        vecReg.add(INT_TBLD_STCONTOT, false);
                    } else if (rst.getString("estConfGuia").equals("C")) {
                        vecReg.add(INT_TBLD_STCONPEN, false);
                        vecReg.add(INT_TBLD_STCONPAR, false);
                        vecReg.add(INT_TBLD_STCONTOT, true);
                    } else if (rst.getString("estConfGuia").equals("E")) {
                        vecReg.add(INT_TBLD_STCONPEN, false);
                        vecReg.add(INT_TBLD_STCONPAR, true);
                        vecReg.add(INT_TBLD_STCONTOT, false);
                    } else if (rst.getString("estConfGuia").equals("F")) {
                        vecReg.add(INT_TBLD_STCONPEN, false);
                        vecReg.add(INT_TBLD_STCONPAR, false);
                        vecReg.add(INT_TBLD_STCONTOT, false);
                    } else {
                        vecReg.add(INT_TBLD_STCONPEN, false);
                        vecReg.add(INT_TBLD_STCONPAR, false);
                        vecReg.add(INT_TBLD_STCONTOT, false);
                    }

                    vecReg.add(INT_TBLD_BUTCONF, "...");
                    vecDat.add(vecReg);
                    i++;
                    pgrSis.setValue(i);
                }
                rst.close();
                rst = null;

                //Asignar vectores al modelo.
                objTblModTG.setData(vecDat);
                tblgui.setModel(objTblModTG);
                vecDat.clear();

                stm.close();
                stm = null;
                conn.close();
                conn = null;

                lblMsgSis.setText("Se encontraron " + tblgui.getRowCount() + " registros.");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBusBod;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butSol;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkDocConfPar;
    private javax.swing.JCheckBox chkDocConfTot;
    private javax.swing.JCheckBox chkMosGuiRem;
    private javax.swing.JCheckBox chkMosPenConf;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCodAltItmTer;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblOrdDes;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCodAltItmTer;
    private javax.swing.JPanel panFilCab;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scrBar;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblgui;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtDesItm;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtcodaltdes;
    private javax.swing.JTextField txtcodalthas;
    // End of variables declaration//GEN-END:variables

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        @Override
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBLD_LIN:
                    strMsg = "";
                    break;
                case INT_TBLD_CODLOC:
                    strMsg = "Código local.";
                    break;

                case INT_TBLD_NOMLOC:
                    strMsg = "Nombre del local.";
                    break;

                case INT_TBLD_DESTIDC:
                    strMsg = "Descripcion corta del tipo documento .";
                    break;

                case INT_TBLD_DESTIDL:
                    strMsg = "Descripcion larga del tipo documento .";
                    break;

                case INT_TBLD_NUMDOC:
                    strMsg = "Número del docuemento.";
                    break;

                case INT_TBLD_NUMORDES:
                    strMsg = "Número de orden de despacho.";
                    break;

                case INT_TBLD_PESOKG:
                    strMsg = "Peso.";
                    break;

                case INT_TBLD_PESOKGPEN:  // José Marín M.
                    strMsg = "Peso Pendiente (Kg)";
                    break;

                case INT_TBLD_FECDOC:
                    strMsg = "Fecha del documento.";
                    break;

                case INT_TBLD_NOMBOD:
                    strMsg = "Nombre bodega origen.";
                    break;

                case INT_TBLD_STCONPEN:
                    strMsg = "Confirmación pendiente.";
                    break;

                case INT_TBLD_STCONPAR:
                    strMsg = "Confirmación parcial.";
                    break;

                case INT_TBLD_STCONTOT:
                    strMsg = "Confirmación total.";
                    break;

                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        //System.gc();
        Runtime.getRuntime().gc();
        super.finalize();
    }
}
