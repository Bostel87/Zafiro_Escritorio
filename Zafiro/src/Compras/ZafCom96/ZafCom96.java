/*
 * ZafCom96.java  
 *
 * Created on Jun 21, 2016, 11:00:03 AM
 */
package Compras.ZafCom96;

import Compras.ZafCom83.ZafCom83;
import Compras.ZafCom84.ZafCom84;
import Compras.ZafCom91.ZafCom91;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
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
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Tony Sanginez
 */
public class ZafCom96 extends javax.swing.JInternalFrame {

    ZafParSis objParSis;
    ZafUtil objUti;
    ZafCom96_01 objZafCom96_01;
    private ZafSelFec objSelFec;
    private ZafTblMod objTblMod;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenBut objTblCelRenButDG1;
    private ZafTblCelEdiButGen objTblCelEdiButGenDG1;
    private ArrayList arlColHid = new ArrayList();
    private Connection con;
    private Date datFecAux;                          //Auxiliar: Para almacenar fechas.
    private String strVersion = "v2.4";
    private Statement stm;
    private ResultSet rst;
    private String strSQL = "";
    private ZafTblMod objTblModBodOri;
    private boolean blnMarTodChkTblBodOri = true;                               //Marcar todas las casillas de verificación del JTable de bodegas.
    private ZafTblPopMnu objTblPopMnu;
    private Vector vecAux;
    private ZafTblFilCab objTblFilCab;
    private ZafTblBus objTblBus;     //Se usa para buscar en la tabla
    private ZafTblCelRenLbl objTblCelRenLbl;
    //JTable: Tabla de Bodega Origen.
    private static final int INT_TBL_BODORI_LIN = 0;
    private static final int INT_TBL_BODORI_CHKBOD = 1;
    private static final int INT_TBL_BODORI_CODBOD = 2;
    private static final int INT_TBL_BODORI_NOMBOD = 3;
    //* CONTENEDOR PARA CONSULTAR*//
    //CONTENEDOR PARA CARGAR LA INFORMACION EN LA PANTALLA DE CANCELACION//
    private static final int INT_TBL_LIN = 0;                 //Línea
    private static final int INT_TBL_CODSEG=1;               //Codigo de Seguimiento
    private static final int INT_TBL_CODEMP = 2;                 //Codigo Empresa
    private static final int INT_TBL_CODLOC = 3;                 //Codigo Local
    private static final int INT_TBL_CODTIPDOC = 4;                 //Codigo tipo de documento
    private static final int INT_TBL_TIPDOC = 5;                 //Descripcion corta tipo de documento
    private static final int INT_TBL_BODORG = 6;                 //Nombre de bodega de origen
    private static final int INT_TBL_BODDES = 7;                 //Nombre de bodega destino
    private static final int INT_TBL_CODDOC = 8;                 //Codigo de documento
    private static final int INT_TBL_NUMDOC = 9;                 //Numero de documento
    private static final int INT_TBL_FECDOC = 10;                 //Fecha de Documento
    private static final int INT_TBL_BUTSOL = 11;                 //Boton muestra solicitud de tranferencia
    private static final int INT_TBL_CHKPENCONF = 12;                 //Confirmacion pendiente
    private static final int INT_TBL_CHKPARCONF = 13;                //Confirmacion parcial
    private static final int INT_TBL_CHKTOT = 14;                //Confirmacion total
    private static final int INT_TBL_BUTCAN = 15;                //Muestra la  cancelacion
    private static final int INT_TBL_BUTSEGSOLCAB = 16;                //Muestra los seguimientos de la solicitud FORMATO#1 CABECERA
    private static final int INT_TBL_BUTSEGSOLDET = 17;                //Muestra los seguimientos de la solicitud FORMATO#2 DETALLE
    private static final int INT_TBL_ESTAUT = 18;                //Estado Autorizado
    private static final int INT_TBL_DAT_EST = 19;                //Estado Adicional Para controlar los datos cargados
    JTextField txtCodItm = new JTextField();
    private ZafVenCon vcoItm;                                                   //Ventana de consulta.
    private String strCodItm="", strCodAlt="", strCodLetItm="",  strNomItm="";
    private Vector vecDat, vecCab, vecReg;
    private ZafThreadGUI objThrGUI;

    /**
     * Creates new form ZafVen96
     * @param objZafParsis
     */
    public ZafCom96(ZafParSis objZafParsis) {
        try {
            this.objParSis = (ZafParSis) objZafParsis.clone();
            objUti = new ZafUtil();
            objTblCelEdiChk = new ZafTblCelEdiChk();
            objTblCelEdiButGenDG1 = new ZafTblCelEdiButGen();
            objTblCelRenButDG1 = new ZafTblCelRenBut();
            datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            initComponents();
            configurarTblBodOri();
            cargarBodOri();
            configurarZafSelFec();
            configurarVenConItm();
            this.setTitle(objParSis.getNombreMenu() + " " + strVersion);
            lblTit.setText(objParSis.getNombreMenu());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    private void configurarZafSelFec(){//Configurar ZafSelFec
            objSelFec = new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(false);
            panFilCab1.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
    }
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(8);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN, "");
            vecCab.add(INT_TBL_CODSEG,"Cod.Seg.");
            vecCab.add(INT_TBL_CODEMP, "Cód.Emp.");
            vecCab.add(INT_TBL_CODLOC, "Cód.Loc.");
            vecCab.add(INT_TBL_CODTIPDOC, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_TIPDOC, "Tip.Doc.");
            vecCab.add(INT_TBL_BODORG, "Bod.Org.");
            vecCab.add(INT_TBL_BODDES, "Bod.Des.");
            vecCab.add(INT_TBL_CODDOC, "Cód.Doc.");
            vecCab.add(INT_TBL_NUMDOC, "Núm.Doc.");
            vecCab.add(INT_TBL_FECDOC, "Fec.Doc.");
            vecCab.add(INT_TBL_BUTSOL, "...");
            vecCab.add(INT_TBL_CHKPENCONF, "Pendiente");
            vecCab.add(INT_TBL_CHKPARCONF, "Parcial");
            vecCab.add(INT_TBL_CHKTOT, "Total");
            vecCab.add(INT_TBL_BUTCAN, "Cancelar");
            vecCab.add(INT_TBL_BUTSEGSOLCAB, "...");
            vecCab.add(INT_TBL_BUTSEGSOLDET, "...");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            new ZafColNumerada(tblDat, INT_TBL_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            TableColumnModel tcmAux = tblDat.getColumnModel();

            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODSEG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CODEMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CODTIPDOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_TIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BODORG).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_BODDES).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_CODDOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BUTSOL).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CHKPENCONF).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_CHKPARCONF).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_CHKTOT).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_BUTCAN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BUTSEGSOLCAB).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_BUTSEGSOLDET).setPreferredWidth(25);
            
            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_BUTSOL);
            vecAux.add("" + INT_TBL_BUTCAN);
            vecAux.add("" + INT_TBL_BUTSEGSOLCAB);
            vecAux.add("" + INT_TBL_BUTSEGSOLDET);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            //Configurar JTable: Editor de la tabla.
            new ZafTblEdi(tblDat);

            ZafTblCelRenLbl objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), true, true);
            objTblCelRenLbl = null;

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            ZafMouMotAda objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            new ZafTblBus(tblDat);
            //Libero los objetos auxiliares.
            new ZafTblOrd(tblDat);

            arlColHid.add("" + INT_TBL_CODEMP);
            arlColHid.add("" + INT_TBL_CODLOC);
            arlColHid.add("" + INT_TBL_CODTIPDOC);
            arlColHid.add("" + INT_TBL_CODDOC);

            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);

            arlColHid = null;
            ZafTblCelRenBut objTblCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTCAN).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut = null;

            tcmAux.getColumn(INT_TBL_BUTSOL).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUTCAN).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUTSEGSOLCAB).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUTSEGSOLDET).setCellRenderer(objTblCelRenButDG1);

            objTblCelRenButDG1.addTblCelRenListener(new ZafTblCelRenAdapter() {
                public void beforeRender(ZafTblCelRenEvent evt) {
                    switch (objTblCelRenButDG1.getColumnRender()) {
                        case INT_TBL_BUTSOL:
                            objTblCelRenButDG1.setText("...");
                            break;
                        case INT_TBL_BUTCAN:
                            objTblCelRenButDG1.setText("...");
                            break;
                        case INT_TBL_BUTSEGSOLCAB:
                            objTblCelRenButDG1.setText("...");
                            break;
                        case INT_TBL_BUTSEGSOLDET:
                            objTblCelRenButDG1.setText("...");
                            break;
                    }
                }
            });

            //Configurar JTable: Editor de celdas.
            tcmAux.getColumn(INT_TBL_BUTSOL).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUTCAN).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUTSEGSOLCAB).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUTSEGSOLDET).setCellRenderer(objTblCelRenButDG1);

            objTblCelRenButDG1.addTblCelRenListener(new ZafTblCelRenAdapter() {
                public void beforeRender(ZafTblCelRenEvent evt) {
                    switch (objTblCelRenButDG1.getColumnRender()) {
                    }
                }
            });

            //Configurar JTable: Editor de celdas.
            tcmAux.getColumn(INT_TBL_BUTSOL).setCellEditor(objTblCelEdiButGenDG1);
            tcmAux.getColumn(INT_TBL_BUTCAN).setCellEditor(objTblCelEdiButGenDG1);
            tcmAux.getColumn(INT_TBL_BUTSEGSOLCAB).setCellEditor(objTblCelEdiButGenDG1);
            tcmAux.getColumn(INT_TBL_BUTSEGSOLDET).setCellEditor(objTblCelEdiButGenDG1);

            objTblCelEdiButGenDG1.addTableEditorListener(new ZafTableAdapter() {
                int intFilSel, intColSel;

                public void beforeEdit(ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_BUTCAN:
                                if (objTblMod.getValueAt(intFilSel, INT_TBL_CHKTOT).toString().equals("true") && !objTblMod.getValueAt(objTblCelRenButDG1.getRowRender(), INT_TBL_LIN).toString().equals("M")) {
                                    //objTblCelEdiButGenDG1.setCancelarEdicion(true);
                                }
                                break;
                        }
                    }
                }

                public void afterEdit(ZafTableEvent evt) {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_BUTSOL:
                                llamarSolTraInv(objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOC).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
                                );
                                break;
                            case INT_TBL_BUTCAN:
                                llamarCan(Integer.valueOf(objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString()), Integer.valueOf(objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString()), Integer.valueOf(objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOC).toString()), Integer.valueOf(objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()), obtieneEstTra(intFilSel)
                                );
                                break;
                            case INT_TBL_BUTSEGSOLCAB:
                                llamarSegSolTraInvCab(objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOC).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
                                );

                                break;
                            case INT_TBL_BUTSEGSOLDET:
                                llamarSegSolTraInvDet(objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOC).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
                                );
                                break;
                        }
                    }
                }
            });

            ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16 * 2);

            ZafTblHeaColGrp objTblHeaColGrpAmeSur = new ZafTblHeaColGrp("Paso 1: Solicitudes de tranferencia de inventario");
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODSEG));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODEMP));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODLOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODTIPDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TIPDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BODORG));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BODDES));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NUMDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTSOL));

            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur = null;

            objTblHeaColGrpAmeSur = new ZafTblHeaColGrp("Paso 2: Confirmaciones de la solicitud");
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CHKPENCONF));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CHKPARCONF));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CHKTOT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur = null;

            objTblHeaColGrpAmeSur = new ZafTblHeaColGrp("Paso 3: Cancelación");
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTCAN));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTSEGSOLCAB));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTSEGSOLDET));

            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur = null; 
            tcmAux = null;
            setEditable(true);
            objTblBus = new ZafTblBus(tblDat);

        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void setEditable(boolean editable) {
        if (editable == true) {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        } else {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Metodo que llama a la pantalla de tranferencias">
    private void llamarSolTraInv(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        try {
            ZafCom91 obj1 = new ZafCom91(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
            this.getParent().add(obj1, JLayeredPane.DEFAULT_LAYER);
            obj1.show();
        } catch (Exception Evt) {
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodo que llama a la pantalla de solicitud de detalle tranferencia">
    private void llamarSegSolTraInvDet(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        HashMap map = new HashMap();
        map.put("objParSis", objParSis);
        map.put("strCodEmp", strCodEmp);
        map.put("strCodLoc", strCodLoc);
        map.put("strCodTipDoc", strCodTipDoc);
        map.put("strCodDoc", strCodDoc);
        try {
            ZafCom84 obj1 = new ZafCom84(map);
            this.getParent().add(obj1, JLayeredPane.DEFAULT_LAYER);
            obj1.show();
        } catch (Exception Evt) {
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }//</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGrp = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFilCab = new javax.swing.JPanel();
        panFilCab1 = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        panSolTra = new javax.swing.JPanel();
        lblSolTraDes = new javax.swing.JLabel();
        txtSolTraDes = new javax.swing.JTextField();
        lblSolTraHas = new javax.swing.JLabel();
        txtSolTraHas = new javax.swing.JTextField();
        chkSolTraProPen = new javax.swing.JCheckBox();
        chkSolTraProPar = new javax.swing.JCheckBox();
        panSeg = new javax.swing.JPanel();
        lblSegDes = new javax.swing.JLabel();
        txtSegDes = new javax.swing.JTextField();
        lblSegHas = new javax.swing.JLabel();
        txtSegHas = new javax.swing.JTextField();
        chkSolTraProCom = new javax.swing.JCheckBox();
        panBodOri = new javax.swing.JPanel();
        spnBodOri = new javax.swing.JScrollPane();
        tblBodOri = new javax.swing.JTable();
        panBusItm = new javax.swing.JPanel();
        lblItm = new javax.swing.JLabel();
        txtCodAlt = new javax.swing.JTextField();
        txtCodAlt2 = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panItmDesHas = new javax.swing.JPanel();
        lblDesItm = new javax.swing.JLabel();
        txtCodAltItmDes = new javax.swing.JTextField();
        lblHasItm = new javax.swing.JLabel();
        txtCodAltItmHas = new javax.swing.JTextField();
        panItmTer = new javax.swing.JPanel();
        lblItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
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

        tabFrm.setMinimumSize(new java.awt.Dimension(96, 90));
        tabFrm.setPreferredSize(new java.awt.Dimension(459, 450));

        panFilCab.setPreferredSize(new java.awt.Dimension(300, 500));
        panFilCab.setLayout(null);

        panFilCab1.setAutoscrolls(true);
        panFilCab1.setPreferredSize(new java.awt.Dimension(0, 400));
        panFilCab1.setLayout(null);

        btnGrp.add(optTod);
        optTod.setText("Todos los documentos");
        optTod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optTodStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFilCab1.add(optTod);
        optTod.setBounds(20, 180, 550, 20);

        btnGrp.add(optFil);
        optFil.setSelected(true);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        optFil.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optFilStateChanged(evt);
            }
        });
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFilCab1.add(optFil);
        optFil.setBounds(20, 200, 590, 20);

        panSolTra.setBorder(javax.swing.BorderFactory.createTitledBorder("Número Solicitud de Transferencia "));
        panSolTra.setLayout(null);

        lblSolTraDes.setText("Desde:");
        panSolTra.add(lblSolTraDes);
        lblSolTraDes.setBounds(12, 20, 48, 20);

        txtSolTraDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSolTraDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSolTraDesFocusLost(evt);
            }
        });
        panSolTra.add(txtSolTraDes);
        txtSolTraDes.setBounds(60, 20, 90, 20);

        lblSolTraHas.setText("Hasta:");
        panSolTra.add(lblSolTraHas);
        lblSolTraHas.setBounds(160, 20, 48, 20);

        txtSolTraHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSolTraHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSolTraHasFocusLost(evt);
            }
        });
        panSolTra.add(txtSolTraHas);
        txtSolTraHas.setBounds(210, 20, 90, 20);

        panFilCab1.add(panSolTra);
        panSolTra.setBounds(30, 230, 310, 50);

        chkSolTraProPen.setSelected(true);
        chkSolTraProPen.setText("Mostrar los documentos que están pendientes de confirmar.");
        chkSolTraProPen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolTraProPenActionPerformed(evt);
            }
        });
        panFilCab1.add(chkSolTraProPen);
        chkSolTraProPen.setBounds(40, 290, 460, 20);

        chkSolTraProPar.setSelected(true);
        chkSolTraProPar.setText("Mostrar los documentos que están confirmados parcialmente.");
        chkSolTraProPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolTraProParActionPerformed(evt);
            }
        });
        panFilCab1.add(chkSolTraProPar);
        chkSolTraProPar.setBounds(40, 310, 460, 20);

        panSeg.setBorder(javax.swing.BorderFactory.createTitledBorder("Número Seguimiento"));
        panSeg.setLayout(null);

        lblSegDes.setText("Desde:");
        panSeg.add(lblSegDes);
        lblSegDes.setBounds(12, 20, 48, 20);

        txtSegDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSegDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSegDesFocusLost(evt);
            }
        });
        panSeg.add(txtSegDes);
        txtSegDes.setBounds(60, 20, 90, 20);

        lblSegHas.setText("Hasta:");
        panSeg.add(lblSegHas);
        lblSegHas.setBounds(160, 20, 48, 20);

        txtSegHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSegHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSegHasFocusLost(evt);
            }
        });
        panSeg.add(txtSegHas);
        txtSegHas.setBounds(210, 20, 90, 20);

        panFilCab1.add(panSeg);
        panSeg.setBounds(350, 230, 310, 50);

        chkSolTraProCom.setText("Mostrar los documentos que están confirmados totalmente.");
        panFilCab1.add(chkSolTraProCom);
        chkSolTraProCom.setBounds(40, 330, 460, 20);

        panBodOri.setBorder(javax.swing.BorderFactory.createTitledBorder("Origen: Listado de bodegas"));
        panBodOri.setLayout(new java.awt.BorderLayout());

        tblBodOri.setModel(new javax.swing.table.DefaultTableModel(
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
        spnBodOri.setViewportView(tblBodOri);

        panBodOri.add(spnBodOri, java.awt.BorderLayout.CENTER);

        panFilCab1.add(panBodOri);
        panBodOri.setBounds(20, 80, 320, 100);

        panBusItm.setBorder(javax.swing.BorderFactory.createTitledBorder("Búsqueda por ítem"));
        panBusItm.setLayout(null);

        lblItm.setText("Item:");
        panBusItm.add(lblItm);
        lblItm.setBounds(10, 20, 40, 20);

        txtCodAlt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltFocusLost(evt);
            }
        });
        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });
        panBusItm.add(txtCodAlt);
        txtCodAlt.setBounds(50, 20, 90, 20);

        txtCodAlt2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusLost(evt);
            }
        });
        txtCodAlt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAlt2ActionPerformed(evt);
            }
        });
        panBusItm.add(txtCodAlt2);
        txtCodAlt2.setBounds(140, 20, 63, 20);

        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });
        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        panBusItm.add(txtNomItm);
        txtNomItm.setBounds(200, 20, 320, 20);

        butItm.setText(".."); // NOI18N
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panBusItm.add(butItm);
        butItm.setBounds(520, 20, 20, 20);

        panItmDesHas.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del Item"));
        panItmDesHas.setLayout(null);

        lblDesItm.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDesItm.setText("Desde:");
        panItmDesHas.add(lblDesItm);
        lblDesItm.setBounds(12, 20, 60, 16);

        txtCodAltItmDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusLost(evt);
            }
        });
        panItmDesHas.add(txtCodAltItmDes);
        txtCodAltItmDes.setBounds(60, 20, 80, 20);

        lblHasItm.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblHasItm.setText("Hasta:");
        panItmDesHas.add(lblHasItm);
        lblHasItm.setBounds(150, 20, 50, 16);

        txtCodAltItmHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusLost(evt);
            }
        });
        panItmDesHas.add(txtCodAltItmHas);
        txtCodAltItmHas.setBounds(190, 20, 90, 20);

        panBusItm.add(panItmDesHas);
        panItmDesHas.setBounds(20, 50, 290, 48);

        panItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del Item"));
        panItmTer.setLayout(null);

        lblItmTer.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblItmTer.setText("Terminan con:");
        panItmTer.add(lblItmTer);
        lblItmTer.setBounds(12, 20, 80, 16);

        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(96, 20, 92, 20);

        panBusItm.add(panItmTer);
        panItmTer.setBounds(310, 50, 230, 48);

        panFilCab1.add(panBusItm);
        panBusItm.setBounds(40, 360, 560, 110);

        panFilCab.add(panFilCab1);
        panFilCab1.setBounds(0, 0, 710, 470);

        tabFrm.addTab("General", null, panFilCab, "");

        panRep.setLayout(new java.awt.BorderLayout());

        spnDat.setPreferredSize(new java.awt.Dimension(452, 100));

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

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);

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

        setBounds(0, 0, 884, 622);
    }// </editor-fold>//GEN-END:initComponents

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
       public void run() {
            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);
            if (!cargarDetReg(sqlConFil())) {
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            if (tblDat.getRowCount() > 0) {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI = null;
            pgrSis.setValue(0);
            pgrSis.setIndeterminate(false);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Cargar Datos. Todas las transferencias.">
    private boolean cargarDetReg(String strFil) {
        boolean blnRes = true;
        Connection conn;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql;
        try {
            butCon.setText("Detener");
            conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) {
                stmLoc = conn.createStatement();
                if (objParSis.getCodigoUsuario() == 1) {
                    strSql = "";
                    //strSql += "select a1.co_emp,a1.co_loc, a1.co_tipdoc, a3.tx_descor, a1.co_doc, a1.ne_numdoc, a1.fe_doc, a1.st_coninv,a1.st_can,a1.tx_obscan,a1.st_aut, a4.co_seg,aOrg.tx_nom as bodOrg,aDes.tx_nom as bodDes\n";
                    strSql += "select a1.co_emp,a1.co_loc, a1.co_tipdoc, a3.tx_descor, a1.co_doc, a1.ne_numdoc, a1.fe_doc, a1.st_coninv,a1.st_aut, a4.co_seg,aOrg.tx_nom as bodOrg,aDes.tx_nom as bodDes\n";
                    strSql += " from tbm_cabsoltrainv as a1\n";
                    strSql += " inner join tbm_bod as aOrg on (aOrg.co_emp=a1.co_emp and aOrg.co_bod=a1.co_bodOrg and aOrg.st_reg='A')\n";
                    strSql += " inner join tbm_bod as aDes on (aDes.co_emp=a1.co_emp and aDes.co_bod=a1.co_bodDes and aDes.st_reg='A')\n";
                    strSql += " inner join tbr_tipdocprg as a5 on (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipDoc=a5.co_tipDoc)\n";
                    strSql += " inner join tbm_detsoltrainv as a2 on (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc)\n";
                    strSql += " inner join tbm_cabtipdoc as a3 on (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc)\n";
                    strSql += " inner join tbm_cabsegmovinv as a4 on (a1.co_emp=a4.co_emprelcabsoltrainv and a1.co_loc=a4.co_locrelcabsoltrainv and a1.co_tipdoc=a4.co_tipdocrelcabsoltrainv and a1.co_doc=a4.co_docrelcabsoltrainv)\n";
                    strSql += " INNER JOIN tbm_inv as inv ON (inv.co_Emp=a2.co_Emp AND inv.co_itm=a2.co_itm) \n";
                    strSql += " where (a1.st_aut!='D' or a1.st_aut is null or a1.st_reg='A') AND a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + " AND a5.co_mnu=" + objParSis.getCodigoMenu();
                    strSql += strFil;
                    //strSql += " group by a1.co_emp,a1.co_loc, a1.co_tipdoc, a3.tx_descor, a1.co_doc, a1.ne_numdoc, a1.fe_doc, a1.st_coninv,a1.st_can,a1.tx_obscan,a1.st_aut,a4.co_seg,aOrg.tx_nom,aDes.tx_nom\n";
                    strSql += " group by a1.co_emp,a1.co_loc, a1.co_tipdoc, a3.tx_descor, a1.co_doc, a1.ne_numdoc, a1.fe_doc, a1.st_coninv,a1.st_aut,a4.co_seg,aOrg.tx_nom,aDes.tx_nom\n";
                    strSql += " order by a1.co_emp,a1.co_loc, a1.co_tipdoc, a3.tx_descor, a1.co_doc, a1.ne_numdoc, a1.fe_doc";
                } else {
                    strSql = "";
                    //strSql += "select a1.co_emp,a1.co_loc, a1.co_tipdoc, a3.tx_descor, a1.co_doc, a1.ne_numdoc, a1.fe_doc, a1.st_coninv,a1.st_can,a1.tx_obscan,a1.st_aut, a4.co_seg,aOrg.tx_nom as bodOrg,aDes.tx_nom as bodDes\n";
                    strSql += "select a1.co_emp,a1.co_loc, a1.co_tipdoc, a3.tx_descor, a1.co_doc, a1.ne_numdoc, a1.fe_doc, a1.st_coninv,a1.st_aut, a4.co_seg,aOrg.tx_nom as bodOrg,aDes.tx_nom as bodDes\n";
                    strSql += "                        from tbm_cabsoltrainv as a1\n";
                    strSql += " inner join tbm_bod as aOrg on (aOrg.co_emp=a1.co_emp and aOrg.co_bod=a1.co_bodOrg and aOrg.st_reg='A')\n";
                    strSql += " inner join tbm_bod as aDes on (aDes.co_emp=a1.co_emp and aDes.co_bod=a1.co_bodDes and aDes.st_reg='A')\n";
                    strSql += "                        inner join tbr_tipdocprg as a5 on (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipDoc=a5.co_tipDoc)\n";
                    strSql += "                        inner join tbm_detsoltrainv as a2 on (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc)\n";
                    strSql += "                        inner join tbm_cabtipdoc as a3 on (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc)\n";
                    strSql += " inner join tbm_cabsegmovinv as a4 on (a1.co_emp=a4.co_emprelcabsoltrainv and a1.co_loc=a4.co_locrelcabsoltrainv and a1.co_tipdoc=a4.co_tipdocrelcabsoltrainv and a1.co_doc=a4.co_docrelcabsoltrainv)\n";
                    strSql += " INNER JOIN tbm_inv as inv ON (inv.co_Emp=a2.co_Emp AND inv.co_itm=a2.co_itm) \n";
                    strSql += " where (a1.st_aut!='D' or a1.st_aut is null or a1.st_reg='A') AND a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + " AND a5.co_mnu=" + objParSis.getCodigoMenu();
                    //strSql += "                         AND a5.co_usr=" + objParSis.getCodigoUsuario() + " " ;
                    strSql += strFil;
                    //strSql += " group by a1.co_emp,a1.co_loc, a1.co_tipdoc, a3.tx_descor, a1.co_doc, a1.ne_numdoc, a1.fe_doc, a1.st_coninv,a1.st_can,a1.tx_obscan,a1.st_aut,a4.co_seg,aOrg.tx_nom,aDes.tx_nom\n";
                    strSql += " group by a1.co_emp,a1.co_loc, a1.co_tipdoc, a3.tx_descor, a1.co_doc, a1.ne_numdoc, a1.fe_doc, a1.st_coninv,a1.st_aut,a4.co_seg,aOrg.tx_nom,aDes.tx_nom\n";
                    strSql += "                        order by a1.co_emp,a1.co_loc, a1.co_tipdoc, a3.tx_descor, a1.co_doc, a1.ne_numdoc, a1.fe_doc";
                }
                //System.out.println(strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                vecDat.clear();
                while (rstLoc.next()) {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_LIN, "");
                    vecReg.add(INT_TBL_CODSEG,rstLoc.getString("co_seg"));
                    vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp"));
                    vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc"));
                    vecReg.add(INT_TBL_CODTIPDOC, rstLoc.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_TIPDOC, rstLoc.getString("tx_descor"));
                    vecReg.add(INT_TBL_BODORG, rstLoc.getString("bodOrg"));
                    vecReg.add(INT_TBL_BODDES, rstLoc.getString("bodDes"));
                    vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc"));
                    vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc"));
                    vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc"));
                    vecReg.add(INT_TBL_BUTSOL, "");
                    if (rstLoc.getString("st_coninv") != null) {
                        vecReg.add(INT_TBL_CHKPENCONF, rstLoc.getString("st_coninv").equals("P") ? true : false);
                        vecReg.add(INT_TBL_CHKPARCONF, rstLoc.getString("st_coninv").equals("E") ? true : false);
                        vecReg.add(INT_TBL_CHKTOT, rstLoc.getString("st_coninv").equals("C") ? true : false);
                    } else {
                        vecReg.add(INT_TBL_CHKPENCONF, false);
                        vecReg.add(INT_TBL_CHKPARCONF, false);
                        vecReg.add(INT_TBL_CHKTOT, false);
                    }
                    vecReg.add(INT_TBL_BUTCAN, "");
                    vecReg.add(INT_TBL_BUTSEGSOLCAB, "");
                    vecReg.add(INT_TBL_BUTSEGSOLDET, "");
                    vecReg.add(INT_TBL_ESTAUT, rstLoc.getString("st_aut"));
//                    if (rstLoc.getString("st_can") != null) {
//                        vecReg.add(INT_TBL_DAT_EST, "C");
//                    } else {
//                        vecReg.add(INT_TBL_DAT_EST, "");
//                    }
                    vecReg.add(INT_TBL_DAT_EST, "");
                    vecDat.add(vecReg);
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
            }
        } catch (SQLException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }//</editor-fold>

    private String sqlConFil() {
        String sqlFil = "";
        boolean blnBodOrg = false, blnTerItm=false;
        String strBodOrg = "", strTerItm="";
        switch (objSelFec.getTipoSeleccion()) {
            case 0: //Búsqueda por rangos
                sqlFil += " and a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                break;
            case 1: //Fechas menores o iguales que "Hasta".
                sqlFil += " and a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                break;
            case 2: //Fechas mayores o iguales que "Desde".
                sqlFil += " and a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                break;
            case 3: //Todo.
                break;
        }
        //<editor-fold defaultstate="collapsed" desc="/* Filtro: Bodega Origen. */">
        for (int j = 0; j < tblBodOri.getRowCount(); j++) {
            if (tblBodOri.getValueAt(j, INT_TBL_BODORI_CHKBOD).toString().equals("true")) {
                if (!blnBodOrg) //Primera vez que ingresa.
                {
                    strBodOrg += " AND ( ";
                } else {
                    strBodOrg += " OR ";
                }
                strBodOrg += " (a1.co_bodOrg=" + tblBodOri.getValueAt(j, INT_TBL_BODORI_CODBOD).toString() + ") ";
                blnBodOrg = true;
            }
        }
        if (blnBodOrg) {
            strBodOrg += " ) ";
            sqlFil += strBodOrg;
        }
        //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Número Solicitud. */">
        if (txtSolTraDes.getText().length() > 0 && txtSolTraHas.getText().length() > 0) {
            sqlFil += "  AND a1.ne_numDoc BETWEEN " + objUti.codificar(txtSolTraDes.getText()) + " AND " + objUti.codificar(txtSolTraHas.getText());
        }
            //</editor-fold>

        //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Número Seguimiento. */">
        if (txtSegDes.getText().length() > 0 && txtSegHas.getText().length() > 0) {
            sqlFil += "  AND a4.co_Seg BETWEEN " + objUti.codificar(txtSegDes.getText()) + " AND " + objUti.codificar(txtSegHas.getText());
        }
        //</editor-fold>

        if (chkSolTraProPar.isSelected() && chkSolTraProCom.isSelected() && chkSolTraProCom.isSelected()) {
            sqlFil += " AND a1.st_conInv IN ('E', 'P', 'C')";
        } else if (chkSolTraProPar.isSelected() && chkSolTraProPen.isSelected()) {
            sqlFil += " AND a1.st_conInv IN ('E', 'P')";
        } else if (chkSolTraProPar.isSelected() && chkSolTraProCom.isSelected()) {
            sqlFil += " AND a1.st_conInv IN ('E', 'C')";
        } else if (chkSolTraProPen.isSelected() && chkSolTraProCom.isSelected()) {
            sqlFil += " AND a1.st_conInv IN ('P', 'C')";
        } else if (chkSolTraProPen.isSelected()) {
            sqlFil += " AND a1.st_conInv IN ('P')";
        } else if (chkSolTraProPar.isSelected()) {
            sqlFil += " AND a1.st_conInv IN ('E')";
        } else if (chkSolTraProCom.isSelected()) {
            sqlFil += " AND a1.st_conInv IN ('C')";
        }
       
                //Búsqueda por ítem.
                if (txtCodAlt.getText().length()>0)
                    sqlFil+=" AND inv.tx_codalt="+objUti.codificar(txtCodAlt.getText());
                if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
                   sqlFil+=" AND ((LOWER(inv.tx_codalt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(inv.tx_codalt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

                //Items Terminan Con.
                if (!txtCodAltItmTer.getText().equals("")) 
                {
                    //blnTerItm=false;
                    //String[] result = objUti.codificar(txtCodAltItmTer.getText()).split(",");
                    String[] result = txtCodAltItmTer.getText().split(",");
                    strTerItm = " AND  ( ";
                    for (int x = 0; x < result.length; x++)
                    {
                        if (blnTerItm) 
                        {
                            strTerItm += " or ";
                        }
                        strTerItm += "  upper(inv.tx_codalt) like '%" + result[x].toString().toUpperCase() + "'";
                        blnTerItm=true;
                    }
                    strTerItm+= " ) ";
                    sqlFil+=strTerItm;
                }
        return sqlFil;
    }

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        ExiForm();
}//GEN-LAST:event_butCerActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        ExiForm();
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        if ((objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) && (objParSis.getCodigoMenu() == 3997)) {
            configurarFrm();
        } else if ((objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) && (objParSis.getCodigoMenu() == 3997)) {
            mostrarMsgInf("<HTML>Este programa sólo se puede ejecutar a través de Grupo.</HTML>");
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameOpened

    private void optTodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optTodStateChanged
        if (optTod.isSelected()) {
            txtSolTraDes.setText("");
            txtSolTraHas.setText("");
            txtSegDes.setText("");
            txtSegHas.setText("");
            chkSolTraProPar.setSelected(false);
            chkSolTraProCom.setSelected(false);
            chkSolTraProPen.setSelected(false);
            optFil.setSelected(false);
            txtCodAlt.setText("");
            txtCodAlt2.setText("");
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
            txtCodItm.setText("");
            txtNomItm.setText("");
        }
    }//GEN-LAST:event_optTodStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected()) {
            optFil.setSelected(false);
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optFilStateChanged
        if (optFil.isSelected()) {
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_optFilStateChanged

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected()) {
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_optFilActionPerformed

    private void txtSolTraDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolTraDesFocusGained
        txtSolTraDes.selectAll();
    }//GEN-LAST:event_txtSolTraDesFocusGained

    private void txtSolTraDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolTraDesFocusLost
        if (txtSolTraDes.getText().length() > 0) {
            optFil.setSelected(true);
            if (txtSolTraHas.getText().length() == 0) {
                txtSolTraHas.setText(txtSolTraDes.getText());
            }
        }
    }//GEN-LAST:event_txtSolTraDesFocusLost

    private void txtSolTraHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolTraHasFocusGained
        txtSolTraHas.selectAll();
    }//GEN-LAST:event_txtSolTraHasFocusGained

    private void txtSolTraHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolTraHasFocusLost
        if (txtSolTraHas.getText().length() > 0) {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtSolTraHasFocusLost

    private void chkSolTraProPenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSolTraProPenActionPerformed
        if (!chkSolTraProPen.isSelected()) {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_chkSolTraProPenActionPerformed

    private void chkSolTraProParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSolTraProParActionPerformed
        if (!chkSolTraProPar.isSelected()) {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_chkSolTraProParActionPerformed

    private void txtSegDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSegDesFocusGained
        txtSegDes.selectAll();
    }//GEN-LAST:event_txtSegDesFocusGained

    private void txtSegDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSegDesFocusLost
        if (txtSegDes.getText().length() > 0) {
            optFil.setSelected(true);
            if (txtSegHas.getText().length() == 0) {
                txtSegHas.setText(txtSegDes.getText());
            }
        }
    }//GEN-LAST:event_txtSegDesFocusLost

    private void txtSegHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSegHasFocusGained
        txtSegHas.selectAll();
    }//GEN-LAST:event_txtSegHasFocusGained

    private void txtSegHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSegHasFocusLost
        if (txtSegHas.getText().length() > 0) {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtSegHasFocusLost

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
        strCodAlt=txtCodAlt.getText();
        txtCodAlt.selectAll();
    }//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAlt.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(1);
            }
        }
        else
        txtCodAlt.setText(strCodAlt);
    }//GEN-LAST:event_txtCodAltFocusLost

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtCodAlt.transferFocus();
        optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void txtCodAlt2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusGained
        strCodLetItm=txtCodAlt2.getText();
        txtCodAlt2.selectAll();
    }//GEN-LAST:event_txtCodAlt2FocusGained

    private void txtCodAlt2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt2.getText().equalsIgnoreCase(strCodLetItm))
        {
            if (txtCodAlt2.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
        txtCodAlt2.setText(strCodLetItm);
    }//GEN-LAST:event_txtCodAlt2FocusLost

    private void txtCodAlt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAlt2ActionPerformed
        txtCodAlt2.transferFocus();
         if (txtCodAlt.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodAlt2ActionPerformed

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(3);
            }
        }
        else
        txtNomItm.setText(strNomItm);
    }//GEN-LAST:event_txtNomItmFocusLost

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
        if (txtCodAlt.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
        if (txtCodAlt.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butItmActionPerformed

    private void txtCodAltItmDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusGained
        txtCodAltItmDes.selectAll();
    }//GEN-LAST:event_txtCodAltItmDesFocusGained

    private void txtCodAltItmDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusLost
        if (txtCodAltItmDes.getText().length() > 0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            txtCodAlt.setText("");
            txtNomItm.setText("");
            if (txtCodAltItmHas.getText().length() == 0)
            {
                txtCodAltItmHas.setText(txtCodAltItmDes.getText());
            }
        }
    }//GEN-LAST:event_txtCodAltItmDesFocusLost

    private void txtCodAltItmHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmHasFocusGained
        txtCodAltItmHas.selectAll();
    }//GEN-LAST:event_txtCodAltItmHasFocusGained

    private void txtCodAltItmHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmHasFocusLost
        if (txtCodAltItmHas.getText().length() > 0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            txtCodAlt.setText("");
            txtNomItm.setText("");
        }
    }//GEN-LAST:event_txtCodAltItmHasFocusLost

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
    }//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length() > 0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            txtCodAlt.setText("");
            txtNomItm.setText("");
        }
    }//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void ExiForm() {
        String strTit, strMsg;
        JOptionPane oppMsg = new JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGrp;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butItm;
    private javax.swing.JCheckBox chkSolTraProCom;
    private javax.swing.JCheckBox chkSolTraProPar;
    private javax.swing.JCheckBox chkSolTraProPen;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblDesItm;
    private javax.swing.JLabel lblHasItm;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblItmTer;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblSegDes;
    private javax.swing.JLabel lblSegHas;
    private javax.swing.JLabel lblSolTraDes;
    private javax.swing.JLabel lblSolTraHas;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBodOri;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panBusItm;
    private javax.swing.JPanel panFilCab;
    private javax.swing.JPanel panFilCab1;
    private javax.swing.JPanel panItmDesHas;
    private javax.swing.JPanel panItmTer;
    private javax.swing.JPanel panRep;
    private javax.swing.JPanel panSeg;
    private javax.swing.JPanel panSolTra;
    private javax.swing.JPanel panSur;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBodOri;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBodOri;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAlt2;
    private javax.swing.JTextField txtCodAltItmDes;
    private javax.swing.JTextField txtCodAltItmHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtNomItm;
    private javax.swing.JTextField txtSegDes;
    private javax.swing.JTextField txtSegHas;
    private javax.swing.JTextField txtSolTraDes;
    private javax.swing.JTextField txtSolTraHas;
    // End of variables declaration//GEN-END:variables

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {
       public void mouseMoved(MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_CODEMP:
                    strMsg = "Código de la empresa";
                    break;
                case INT_TBL_CODLOC:
                    strMsg = "Código del local";
                    break;
                case INT_TBL_CODTIPDOC:
                    strMsg = "Código del tipo de documento";
                    break;
                case INT_TBL_TIPDOC:
                    strMsg = "Descripción corta del tipo de documento";
                    break;
                case INT_TBL_CODDOC:
                    strMsg = "Código del documento";
                    break;
                case INT_TBL_NUMDOC:
                    strMsg = "Número de documento";
                    break;
                case INT_TBL_FECDOC:
                    strMsg = "Fecha del documento";
                    break;
                case INT_TBL_BUTSOL:
                    strMsg = "Solicitud de transferencia de inventario";
                    break;
                case INT_TBL_CHKPENCONF:
                    strMsg = "Confirmación pendiente";
                    break;
                case INT_TBL_CHKPARCONF:
                    strMsg = "Confirmación parcial";
                    break;
                case INT_TBL_CHKTOT:
                    strMsg = "Confirmación total";
                    break;
                case INT_TBL_BUTCAN:
                    strMsg = "Cancelación de solicitudes de transferencias de inventario";
                    break;
                case INT_TBL_BUTSEGSOLCAB:
                    strMsg = "Seguimiento de solicitudes de transferencias de inventario (Formato 1: Cabecera)";
                    break;
                case INT_TBL_BUTSEGSOLDET:
                    strMsg = "Seguimiento de solicitudes de transferencias de inventario (Formato 2: Detallado por item)";
                    break;
                default:
                    strMsg = " ";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {
        JOptionPane oppMsg = new JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);
    }

    //<editor-fold defaultstate="collapsed" desc="Llama a la pantalla de seguimimento de solicitudes (Cabecera)"> 
    private void llamarSegSolTraInvCab(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        HashMap map = new HashMap();
        map.put("objParSis", objParSis);
        map.put("strCodEmp", strCodEmp);
        map.put("strCodLoc", strCodLoc);
        map.put("strCodTipDoc", strCodTipDoc);
        map.put("strCodDoc", strCodDoc);
        try {
            ZafCom83 obj1 = new ZafCom83(map);
            this.getParent().add(obj1, JLayeredPane.DEFAULT_LAYER);
            obj1.show();
        } catch (Exception Evt) {
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }//</editor-fold> 

    private void llamarCan(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strEstTra) {
        HashMap map = new HashMap();
        map.put("objParSis", objParSis);
        map.put("intCodEmp", intCodEmp);
        map.put("intCodLoc", intCodLoc);
        map.put("intCodTipDoc", intCodTipDoc);
        map.put("intCodDoc", intCodDoc);
        map.put("strEstTra", strEstTra);
        map.put("strVersion", strVersion);
        try {
            objZafCom96_01 = new ZafCom96_01(JOptionPane.getFrameForComponent(this), true, objParSis, map);
            objZafCom96_01.show();
        } catch (Exception Evt) {
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(this, Evt);
        }
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
    private boolean cargarBodOri() {
        boolean blnRes = true;
        try {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement();

                //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                if (objParSis.getCodigoUsuario() == 1) {
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL += " SELECT a2.co_bod, a2.tx_nom, a2.st_reg ";
                    strSQL += " FROM tbm_emp AS a1 ";
                    strSQL += " INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) ";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + " AND a2.st_reg='A' ";
                    strSQL += " ORDER BY a1.co_emp, a2.co_bod ";
                } else {
                    if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) {
                        //Armar la sentencia SQL.
                        strSQL = "";
                        strSQL += " SELECT a1.co_bodGrp as co_bod, a2.tx_nom ";
                        strSQL += " FROM tbr_bodlocprgusr as a ";
                        strSQL += " INNER JOIN tbr_bodEmpBodGrp as a1 ON (a.co_emp=a1.co_emp AND a.co_bod=a1.co_bod) ";
                        strSQL += " INNER JOIN tbm_bod as a2 ON (a1.co_empGrp=a2.co_emp AND a1.co_bodGrp=a2.co_bod) ";
                        strSQL += " WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL += " AND a.co_loc=" + objParSis.getCodigoLocal();
                        strSQL += " AND a.co_mnu=" + objParSis.getCodigoMenu();
                        strSQL += " AND a.co_usr=" + objParSis.getCodigoUsuario();
                        strSQL += " AND a.tx_natBod in ('E', 'A')  ";
                        strSQL += " ORDER BY a1.co_bodGrp ";
                    } else {
                        strSQL = "";
                        strSQL += " SELECT a.co_bod, a2.tx_nom ";
                        strSQL += " FROM tbr_bodlocprgusr as a  ";
                        strSQL += " INNER JOIN tbm_bod as a2 ON (a.co_Emp=a2.co_emp and a.co_bod=a2.co_bod) ";
                        strSQL += " WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL += " AND a.co_loc=" + objParSis.getCodigoLocal();
                        strSQL += " AND a.co_mnu=" + objParSis.getCodigoMenu();
                        strSQL += " AND a.co_usr=" + objParSis.getCodigoUsuario();
                        strSQL += " AND a.tx_natBod in ('E', 'A') ";
                        strSQL += " ORDER BY a.co_bod ";
                    }
                }
                rst = stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()) {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_BODORI_LIN, "");
                    vecReg.add(INT_TBL_BODORI_CHKBOD, true);
                    vecReg.add(INT_TBL_BODORI_CODBOD, rst.getString("co_bod"));
                    vecReg.add(INT_TBL_BODORI_NOMBOD, rst.getString("tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
                //Asignar vectores al modelo.
                objTblModBodOri.setData(vecDat);
                tblBodOri.setModel(objTblModBodOri);
                vecDat.clear();
                blnMarTodChkTblBodOri = false;
            }
        } catch (SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblBodOri".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblBodOri() {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(4);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_BODORI_LIN, "");
            vecCab.add(INT_TBL_BODORI_CHKBOD, "");
            vecCab.add(INT_TBL_BODORI_CODBOD, "Cód.Bod.");
            vecCab.add(INT_TBL_BODORI_NOMBOD, "Bodega");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBodOri = new ZafTblMod();
            objTblModBodOri.setHeader(vecCab);
            tblBodOri.setModel(objTblModBodOri);
            //Configurar JTable: Establecer tipo de selección.
            tblBodOri.setRowSelectionAllowed(true);
            tblBodOri.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblBodOri);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblBodOri.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            TableColumnModel tcmAux = tblBodOri.getColumnModel();
            tcmAux.getColumn(INT_TBL_BODORI_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BODORI_CHKBOD).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BODORI_CODBOD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_BODORI_NOMBOD).setPreferredWidth(190);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BODORI_CHKBOD).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBodOri.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblBodOri.getTableHeader().addMouseMotionListener(new ZafCom96.ZafMouMotAdaBodOri());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblBodOri.getTableHeader().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    tblBodOriMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_BODORI_CHKBOD);
            objTblModBodOri.setColumnasEditables(vecAux);
            vecAux = null;
            //Configurar JTable: Editor de la tabla.
            //objTblEdi=new ZafTblEdi(tblBodOri);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblBodOri);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblBodOri);
            tcmAux.getColumn(INT_TBL_BODORI_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BODORI_CHKBOD).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_BODORI_CODBOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk = new ZafTblCelEdiChk(tblBodOri);
            tcmAux.getColumn(INT_TBL_BODORI_CHKBOD).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    //Mostrar las columnas "Cód.Bod." y "Bodega" sólo si hay seleccionada más de una bodega.
//                    if (objTblModBodOri.getRowCountChecked(INT_TBL_BOD_CHK)>1)
//                    {
//                        //Mostrar columnas.
//                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
//                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
//                    }
//                    else
//                    {
//                        //Ocultar columnas.
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
//                    }
                }
            });
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblBodOri+.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModBodOri.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux = null;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera
     * del JTable. Se utiliza ésta función especificamente para marcar todas las
     * casillas de verificación de la columna que indica la bodega seleccionada
     * en el el JTable de bodegas.
     */
    private void tblBodOriMouseClicked(MouseEvent evt) {
        int i, intNumFil;
        try {
            intNumFil = objTblModBodOri.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1 && tblBodOri.columnAtPoint(evt.getPoint()) == INT_TBL_BODORI_CHKBOD) {
                if (blnMarTodChkTblBodOri) {
                    //Mostrar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        objTblModBodOri.setChecked(true, i, INT_TBL_BODORI_CHKBOD);
                    }
                    blnMarTodChkTblBodOri = false;
                } else {
                    //Ocultar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        objTblModBodOri.setChecked(false, i, INT_TBL_BODORI_CHKBOD);
                    }
                    blnMarTodChkTblBodOri = true;
                }
            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaBodOri extends MouseMotionAdapter {

        @Override
        public void mouseMoved(MouseEvent evt) {
            int intCol = tblBodOri.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_BODORI_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_BODORI_CHKBOD:
                    strMsg = "";
                    break;
                case INT_TBL_BODORI_CODBOD:
                    strMsg = "Código de la bodega";
                    break;
                case INT_TBL_BODORI_NOMBOD:
                    strMsg = "Nombre de la bodega";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblBodOri.getTableHeader().setToolTipText(strMsg);
        }
    }
    private String obtieneEstTra(int intFilSel) {
        String strEstTra = "";
        if (objTblMod.getValueAt(intFilSel, INT_TBL_CHKPENCONF).equals(true)) {
            strEstTra = "P";
        } else if (objTblMod.getValueAt(intFilSel, INT_TBL_CHKPARCONF).equals(true)) {
            strEstTra = "E";
        } else if (objTblMod.getValueAt(intFilSel, INT_TBL_CHKTOT).equals(true)) {
            strEstTra = "C";
        }
        return strEstTra;
    }
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConItm(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.setVisible(true);
                    if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Codigo alterno 2".
                    if (vcoItm.buscar("a1.tx_codAlt2", txtCodAlt2.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAlt2.setText(strCodLetItm);
                        }
                    }
                    break;   
                case 3: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(3);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtNomItm.setText(strNomItm);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)  {    blnRes=false;      objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()
    {
        boolean blnRes = true;
        String strSQL="";
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("d1.co_itm");
            arlCam.add("d1.tx_codAlt");
            arlCam.add("d1.tx_codAlt2");
            arlCam.add("d1.tx_nomItm");
            arlCam.add("d4.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.Itm.");
            arlAli.add("Cód.Let.Itm.");
            arlAli.add("Item");
            arlAli.add("Uni.Med.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("60");
            arlAncCol.add("300");
            arlAncCol.add("60");
            
            //Armar la sentencia SQL.
            strSQL+=" SELECT a.co_itm, a.tx_codAlt, a.tx_codAlt2, a.tx_nomItm, a1.tx_desCor ";
            strSQL+=" FROM tbm_inv as a LEFT JOIN tbm_var as a1 ON (a1.co_reg=a.co_uni) ";
            strSQL+=" WHERE  a.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a.st_reg NOT IN ('U','T') ";  
            strSQL+=" ORDER BY a.tx_codalt ";
            
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de items", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(3, javax.swing.JLabel.CENTER);
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
        }
        catch (Exception e) {     blnRes=false;    objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
}
