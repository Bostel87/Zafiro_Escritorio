/*
 * Created on 13 de agosto de 2008, 11:41 
 */
package Ventas.ZafVen27;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafObtConCen.ZafObtConCen;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Ventas.ZafVen28.ZafVen28;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Anl. Javier Ayapata RECEPCION DE MERCADERIA A DEVOLVER SUJETA A
 * REVISION/REVISION TECNICA DE LA MERCADERIA
 */
public class ZafVen27 extends javax.swing.JInternalFrame
{
    private ZafTblMod objTblMod;
    private ZafTblCelEdiTxt objTblCelEdiTxtObs, objTblCelEdiTxtCanRec, objTblCelEdiTxtCanNumRec, objTblCelEdiTxtCanAce, objTblCelEdiTxtCanReh;
    private ZafParSis objZafParSis;
    private ZafUtil objUti;
    private mitoolbar objTooBar;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafDatePicker txtFecDoc;
    private ZafMouMotAda objMouMotAda;                            //ToolTipText en TableHeader.
    private ZafObtConCen objObtConCen;
    private ZafThreadGUI objThrGUI;
    private ZafRptSis objRptSis;                                 //Reportes del Sistema.
    private ZafVenCon objVenConTipdoc; 
    private ZafVenCon objVenConVen;
    private ZafPerUsr objPerUsr;
    
    //Columnas de Jtable: TblDat
    private static final int INT_TBL_LINEA = 0;
    private static final int INT_TBL_CODITM = 1;
    private static final int INT_TBL_CODALT = 2;
    private static final int INT_TBL_COD3LET=3;
    private static final int INT_TBL_NOMITM = 4;
    private static final int INT_TBL_DESUNI = 5;
    private static final int INT_TBL_CODBOD = 6;
    private static final int INT_TBL_NOMBOD = 7;
    private static final int INT_TBL_CANVEN = 8;
    private static final int INT_TBL_CANDEV = 9;
    private static final int INT_TBL_FALSTKFIC = 10;
    private static final int INT_TBL_REVTEC = 11;
    private static final int INT_TBL_CANTOTREC = 12;
    private static final int INT_TBL_CANTOTNUNREC = 13;
    private static final int INT_TBL_CAN_TEC_REV_ACE = 14;
    private static final int INT_TBL_CAN_TEC_REV_REH = 15;
    private static final int INT_TBL_CAN_BOD_REV_ACE = 16;
    private static final int INT_TBL_CAN_BOD_REV_REH = 17;
    private static final int INT_TBL_CANREC = 18;
    private static final int INT_TBL_OBSCANREC = 19;
    private static final int INT_TBL_CANACE = 20;
    private static final int INT_TBL_CANRCH = 21;
    private static final int INT_TBL_OBSREVCONFING = 22;
    private static final int INT_TBL_EST_REC_TEC = 23;
    private static final int INT_TBL_CODREG = 24;
    private static final int INT_TBL_CANTOTNUNREC_ORI = 25;
    private static final int INT_TBL_CANREC_ORI = 26;
    private static final int INT_TBL_CANACE_ORI = 27;
    private static final int INT_TBL_CANRCH_ORI = 28;
    private static final int INT_TBL_OBSREVTEC = 29;
    private static final int INT_TBL_CANTOTDEVCLI = 30;
    private static final int INT_TBL_CANDEVCLI_ORI = 31;
    private static final int INT_TBL_CANDEVCLI = 32;
    private static final int INT_TBL_OBSDEVCLI = 33;
    private static final int INT_TBL_FALSTKFICACE = 34;
    private static final int INT_TBL_IEBODFIS = 35;            //Estado que indica si ingresa/egresa fisicamente en bodega la mercadería.
    private static final int INT_TBL_CANSDEVPRV = 36;           //Cantidad que se devuelve al proveedor.
    private static final int INT_TBL_CANNDEVPRV = 37;           //Cantidad que no se devuelve al proveedor.
    
    
    private int intAnchura = 90;
    private int INTCODREGCEN = 0;
    private int INTVERCONCEN = 0;
    private int intCodEmpSol = 0;
    private int intCodLocSol = 0;
    private int intCodTipDocSol = 0;
    private int intCodDocSol = 0;
    
    private Vector vecCab = new Vector();
    private Connection CONN_GLO = null, conRemGlo = null;
    private Statement STM_GLO = null;
    private ResultSet rstCab = null;

    private boolean blnHayCam = false;
    private boolean blnMarTodCanTblDat = true;                    //Coloca en todas las celdas la cantidad devuelta.
    private boolean blnCarReg = false;
    private JTextField txtCodTipDoc = new JTextField();
    private JTextField txtCodTipDocSol = new JTextField();
    private JTextField txtNumDocOcu = new JTextField();

    private java.util.Date datFecAux;                           //Auxiliar: Para almacenar fechas. 
    private String strMerIngEgr = "", strTipIngEgr = "";
    private String strCodRes = "", strNomRes = "";
    private String strCodTipDoc = "", strDesCorTipDoc = "", strDesLarTipDoc = "";
    private String strAux = "", strOrdAlm="";
    
    private boolean blnPruebas=false;    // blnPruebas= false para pasar a produccion.
    private ZafVen28 objZafVen28;
    
    private final String strVer = " v2.20 ";


    /**
     * Creates new form revisionTecMer
     */
    public ZafVen27(Librerias.ZafParSis.ZafParSis obj) 
    {
        try 
        {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();
            this.setTitle(objZafParSis.getNombreMenu() + strVer);
            lblTit.setText(objZafParSis.getNombreMenu());
            objUti = new ZafUtil();

            objTooBar = new mitoolbar(this);
            this.getContentPane().add(objTooBar, "South");

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            PanTabGen.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);

            objRptSis = new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
            objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objZafParSis);
            objPerUsr = new ZafPerUsr(objZafParSis);

            INTCODREGCEN = objObtConCen.intCodReg;

        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    public ZafVen27(Librerias.ZafParSis.ZafParSis obj, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodMnu)
    {
        try 
        {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();
            this.setTitle(objZafParSis.getNombreMenu() + strVer);
            lblTit.setText(objZafParSis.getNombreMenu());
            objUti = new ZafUtil();
            objTooBar = new mitoolbar(this);
            this.getContentPane().add(objTooBar, "South");

            blnCarReg = true;
            intCodEmpSol = intCodEmp;
            intCodLocSol = intCodLoc;
            intCodTipDocSol = intCodTipDoc;
            intCodDocSol = intCodDoc;
            objZafParSis.setCodigoMenu(intCodMnu);

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            PanTabGen.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);

            objRptSis = new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
            objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objZafParSis);
            INTCODREGCEN = objObtConCen.intCodReg;

        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    public void abrirCon() 
    {
        try {
            CONN_GLO = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    public void CerrarCon() 
    {
        try {
            CONN_GLO.close();
            CONN_GLO = null;
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    public void Configura_ventana_consulta() 
    {
        configurarVenConTipDoc();
        configurarVenConVendedor();

        chkMasInfo.setVisible(false);

        if (objZafParSis.getCodigoMenu() == 1888) {
            chkMasInfo.setVisible(true);
            configurarTablaRecMer();
        }
        if (objZafParSis.getCodigoMenu() == 1898) {
            configurarTablaRevTec();
        }
        if (objZafParSis.getCodigoMenu() == 1908) {
            configurarTablaRevConfir();
        }
        if (objZafParSis.getCodigoMenu() == 1928) {
            configurarTablaDevCli();
        }

        if (blnCarReg) {
            objTooBar._consultar(intCodEmpSol, intCodLocSol, intCodTipDocSol, intCodDocSol);
        }
    }

    private boolean configurarVenConTipDoc() 
    {
        boolean blnRes = true;
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");
            arlCam.add("a.st_meringegrfisbod");
            arlCam.add("a.tx_natdoc");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Mer.IngEgr.");
            arlAli.add("Tip.Doc.");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("110");
            arlAncCol.add("350");
            arlAncCol.add("20");
            arlAncCol.add("20");

            //Armar la sentencia SQL.   
            String Str_Sql = "";
            Str_Sql = "Select a.co_tipdoc,a.tx_descor,a.tx_deslar, a.st_meringegrfisbod, a.tx_natdoc from tbr_tipdocprg as b "
                    + " inner join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)"
                    + " where   b.co_emp=" + objZafParSis.getCodigoEmpresa() + " and "
                    + " b.co_loc = " + objZafParSis.getCodigoLocal() + " and "
                    + " b.co_mnu = " + objZafParSis.getCodigoMenu();

            objVenConTipdoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

            objVenConTipdoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConVendedor() 
    {
        boolean blnRes = true;
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_usr");
            arlCam.add("a.tx_nom");
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre.");
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
            String strSQL = "";
            strSQL = "select a.co_usr, a.tx_nom  from tbr_usremp as b"
                    + " inner join tbm_usr as a on (a.co_usr=b.co_usr) "
                    + " where b.co_emp=" + objZafParSis.getCodigoEmpresa() + " and a.st_reg not in ('I')  order by a.tx_nom";

            objVenConVen = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarTablaRevTec() 
    {
        boolean blnRes = false;
        try 
        {
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CODITM, "Cod.Itm.");
            vecCab.add(INT_TBL_CODALT, "Cod.Alt.");
            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
            vecCab.add(INT_TBL_COD3LET,"Cod. 3 Letras");
            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/            
            vecCab.add(INT_TBL_NOMITM, "Nom.Itm.");
            vecCab.add(INT_TBL_DESUNI, "Uni.Med");
            vecCab.add(INT_TBL_CODBOD, "Cod.Bod.");
            vecCab.add(INT_TBL_NOMBOD, "Nom.Bod.");
            vecCab.add(INT_TBL_CANVEN, "Can.Ven.");
            vecCab.add(INT_TBL_CANDEV, "Can.Dev.");
            vecCab.add(INT_TBL_FALSTKFIC, "Fal.Fis");
            vecCab.add(INT_TBL_REVTEC, "Rev.Tec.");
            vecCab.add(INT_TBL_CANTOTREC, "Can.Tot.Rec.");
            vecCab.add(INT_TBL_CANTOTNUNREC, "Can.Tot.Nun.Rec.");

            vecCab.add(INT_TBL_CAN_TEC_REV_ACE, "Can.Tot.Ace.Tec.");
            vecCab.add(INT_TBL_CAN_TEC_REV_REH, "Can.Tot.Rec.Tec.");
            vecCab.add(INT_TBL_CAN_BOD_REV_ACE, "Can.Tot.Ace.Bod.");
            vecCab.add(INT_TBL_CAN_BOD_REV_REH, "Can.Tot.Rec.Bod.");

            vecCab.add(INT_TBL_CANREC, "Can.Rec.");
            vecCab.add(INT_TBL_OBSCANREC, "Obs.Can.Rec.");
            vecCab.add(INT_TBL_CANACE, "Can.Ace.");
            vecCab.add(INT_TBL_CANRCH, "Can.Rch.");
            vecCab.add(INT_TBL_OBSREVCONFING, "Obs.Rev.Con.Ing.");
            vecCab.add(INT_TBL_EST_REC_TEC, "");
            vecCab.add(INT_TBL_CODREG, "");
            vecCab.add(INT_TBL_CANTOTNUNREC_ORI, "Can.Tot.Nun.Rec.");
            vecCab.add(INT_TBL_CANREC_ORI, "Can.Rec.");
            vecCab.add(INT_TBL_CANACE_ORI, "Can.Ace.");
            vecCab.add(INT_TBL_CANRCH_ORI, "Can.Rch.");
            vecCab.add(INT_TBL_OBSREVTEC, "Obs.Rev.Tec");
            vecCab.add(INT_TBL_CANTOTDEVCLI, "Can.Dev.Cli");
            vecCab.add(INT_TBL_CANDEVCLI_ORI, "Can.Dev.Cli");
            vecCab.add(INT_TBL_CANDEVCLI, "Can.Dev.Cli");
            vecCab.add(INT_TBL_OBSDEVCLI, "Obs.Dev.Cli");
            vecCab.add(INT_TBL_FALSTKFICACE, "Fal.stk.Ace");
            vecCab.add(INT_TBL_IEBODFIS, "IE.Fis.Bod");

            vecCab.add(INT_TBL_CANSDEVPRV, "Can.Se.DevPrv.");
            vecCab.add(INT_TBL_CANNDEVPRV, "Can.No.DevPrv.");

            objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            java.awt.Color colFonCol;
            colFonCol = new java.awt.Color(228, 228, 203);

            objTblMod.setColumnDataType(INT_TBL_CANVEN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANDEV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANTOTREC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANTOTNUNREC, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            objTblMod.setColumnDataType(INT_TBL_CAN_TEC_REV_ACE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CAN_TEC_REV_REH, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            objTblMod.setColumnDataType(INT_TBL_CANREC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANACE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANRCH, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            objTblMod.setColumnDataType(INT_TBL_CANSDEVPRV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANNDEVPRV, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();  /**/

            Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_CANVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANDEV).setCellRenderer(objTblCelRenLbl);

            tcmAux.getColumn(INT_TBL_CANTOTNUNREC).setCellRenderer(objTblCelRenLbl);

            tcmAux.getColumn(INT_TBL_CAN_TEC_REV_ACE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CAN_TEC_REV_REH).setCellRenderer(objTblCelRenLbl);

            tcmAux.getColumn(INT_TBL_CANREC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANACE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANRCH).setCellRenderer(objTblCelRenLbl);

            tcmAux.getColumn(INT_TBL_CANSDEVPRV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANNDEVPRV).setCellRenderer(objTblCelRenLbl);

            objTblCelRenLbl = null;

            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(colFonCol);
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_CANTOTREC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_CANACE);
            vecAux.add("" + INT_TBL_CANRCH);
            vecAux.add("" + INT_TBL_OBSREVTEC);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            //Columnas Ocultas
            ArrayList arlColHid = new ArrayList();
            arlColHid.add("" + INT_TBL_CODITM);
            arlColHid.add("" + INT_TBL_EST_REC_TEC);
            arlColHid.add("" + INT_TBL_CODREG);
            //arlColHid.add(""+INT_TBL_CANTOTNUNREC_ORI);
            arlColHid.add("" + INT_TBL_CANREC_ORI);
            arlColHid.add("" + INT_TBL_CANACE_ORI);
            arlColHid.add("" + INT_TBL_CANRCH_ORI);
            arlColHid.add("" + INT_TBL_FALSTKFIC);
            arlColHid.add("" + INT_TBL_REVTEC);
            arlColHid.add("" + INT_TBL_CODBOD);
            arlColHid.add("" + INT_TBL_NOMBOD);
            arlColHid.add("" + INT_TBL_CANTOTNUNREC);
            arlColHid.add("" + INT_TBL_CANREC);
            arlColHid.add("" + INT_TBL_OBSCANREC);
            arlColHid.add("" + INT_TBL_OBSREVCONFING);

            arlColHid.add("" + INT_TBL_CAN_BOD_REV_ACE);
            arlColHid.add("" + INT_TBL_CAN_BOD_REV_REH);

            arlColHid.add("" + INT_TBL_CANDEVCLI_ORI);
            arlColHid.add("" + INT_TBL_CANTOTDEVCLI);
            arlColHid.add("" + INT_TBL_CANDEVCLI);
            arlColHid.add("" + INT_TBL_OBSDEVCLI);
            arlColHid.add("" + INT_TBL_FALSTKFICACE);
            arlColHid.add("" + INT_TBL_IEBODFIS);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid = null;

            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANVEN).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_CANDEV).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_CANTOTREC).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            objTblCelEdiTxt = null;


            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANACE).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    double dlbTotRec = 0.00;
                    double dlbCanAceOri = 0.00;
                    double dlbCanAce = 0.00;
                    double dlbCanRec = 0.00;
                    double dlbSumTotRecAce = 0.00;
                    double dlbSumTotRecRec = 0.00;
                    double dlbCanRecOri = 0.00;

                    dlbCanAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);
                    dlbCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString()), 2);
                    dlbSumTotRecAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE).toString()), 2);
                    dlbSumTotRecRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_REH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_REH).toString()), 2);
                    dlbTotRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString()), 2);
                    dlbCanAceOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString()), 2);
                    dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString()), 2);

                    if (dlbCanAce > dlbCanAceOri) {
                        //System.out.println("-- " + (dlbSumTotRecAce+dlbSumTotRecRec+((dlbCanRec-dlbCanRecOri)+(dlbCanAce-dlbCanAceOri))) +" > "+ dlbTotRec );
                        //System.out.println("-- " + dlbSumTotRecAce +" + "+ dlbSumTotRecRec +" +(( "+ dlbCanRecOri +" - "+ dlbCanRec +" )+( "+ dlbCanAce +" - "+ dlbCanAceOri +")))  > "+ dlbTotRec );
                        if ((dlbSumTotRecAce + dlbSumTotRecRec + ((dlbCanRec - dlbCanRecOri) + (dlbCanAce - dlbCanAceOri))) > dlbTotRec) {
                            MensajeInf("Error! La cantidad a Aceptada a sobrepasado la cantidad total recibida. ");
                            tblDat.setValueAt("" + dlbCanAceOri, intCelSel, INT_TBL_CANACE);
                        }
                    }

                }
            });
            objTblCelEdiTxt = null;


            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANRCH).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    double dlbTotRec = 0.00;
                    double dlbCanAceOri = 0.00;
                    double dlbCanAce = 0.00;
                    double dlbCanRec = 0.00;
                    double dlbSumTotRecAce = 0.00;
                    double dlbSumTotRecRec = 0.00;
                    double dlbCanRecOri = 0.00;

                    dlbCanAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);
                    dlbCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString()), 2);
                    dlbSumTotRecAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE).toString()), 2);
                    dlbSumTotRecRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_REH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_REH).toString()), 2);
                    dlbTotRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString()), 2);
                    dlbCanAceOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString()), 2);
                    dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString()), 2);

                    if (dlbCanRec > dlbCanRecOri) {
                        // System.out.println("-- " + (dlbSumTotRecAce+dlbSumTotRecRec+((dlbCanRecOri-dlbCanRec)+(dlbCanAce-dlbCanAceOri))) +" > "+ dlbTotRec );
                        // System.out.println("-- " + dlbSumTotRecAce +" + "+ dlbSumTotRecRec +" +(( "+ dlbCanRecOri +" - "+ dlbCanRec +" )+( "+ dlbCanAce +" - "+ dlbCanAceOri +")))  > "+ dlbTotRec );
                        if ((dlbSumTotRecAce + dlbSumTotRecRec + ((dlbCanRec - dlbCanRecOri) + (dlbCanAce - dlbCanAceOri))) > dlbTotRec) {
                            MensajeInf("Error! La cantidad a Aceptada a sobrepasado la cantidad total recibida. ");
                            tblDat.setValueAt("" + dlbCanRecOri, intCelSel, INT_TBL_CANRCH);
                        }
                    }

//                    double dlbSumTotRec=0.00;
//                    double dlbRec=0.00;
//                    dlbSumTotRec += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);
//                    dlbSumTotRec += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString()), 2);
//                    dlbSumTotRec += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE).toString()), 2);
//                    dlbSumTotRec += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_REH)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_REH).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_REH).toString()), 2);
//                   
//                    dlbRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString()), 2);
//                   
//                    if(dlbSumTotRec>dlbRec){
//                       MensajeInf("Error! La cantidad a rechazada a sobrepasado la cantidad total recibida. ");
//                       tblDat.setValueAt("", intCelSel, INT_TBL_CANRCH);
//                    }
                }
            });
            objTblCelEdiTxt = null;

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODALT).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(165);
            tcmAux.getColumn(INT_TBL_DESUNI).setPreferredWidth(46);
            tcmAux.getColumn(INT_TBL_CANVEN).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CANDEV).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CANTOTREC).setPreferredWidth(intAnchura);

            tcmAux.getColumn(INT_TBL_CAN_TEC_REV_ACE).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CAN_TEC_REV_REH).setPreferredWidth(intAnchura);

            tcmAux.getColumn(INT_TBL_CANACE).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CANRCH).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_OBSREVTEC).setPreferredWidth(100);

            tcmAux.getColumn(INT_TBL_CANSDEVPRV).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CANNDEVPRV).setPreferredWidth(intAnchura);

            Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
            Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_REVTEC).setCellRenderer(objTblCelRenChk);
            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_REVTEC).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if (tblDat.getValueAt(intCelSel, INT_TBL_EST_REC_TEC).toString().equals("S")) {
                        tblDat.setValueAt(true, intCelSel, INT_TBL_REVTEC);
                    }

                }
            });
            objTblCelRenChk = null;
            objTblCelEdiChk = null;
            ZafTblEdi zafTblEdi = new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatMouseClickedRevTec(evt);
                }
            });

            tcmAux = null;

            setEditable(true);
            blnRes = true;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void tblDatMouseClickedRevTec(MouseEvent evt) 
    {
        int intNumFil;
        int intCelSel = 0;
        double dlbCanTecAce = 0, dlbCanTecReh = 0, dlbCanAceOri = 0, dlbCanAce = 0, dlbCanBodAce = 0, dlbCanAce_Ori = 0, dlbTotRec = 0, dlbCanBodRec = 0, dlbCanRec = 0, dlbCanRecOri = 0;
        try 
        {
            intNumFil = tblDat.getRowCount();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.

            if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1 && tblDat.columnAtPoint(evt.getPoint()) == INT_TBL_CANACE) {
                if (blnMarTodCanTblDat) {
                    //Mostrar todas las columnas.
                    for (int i = 0; i < intNumFil; i++) {

                        intCelSel = i;

                        dlbCanAceOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);
                        dlbCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString()), 2);

                        dlbCanTecAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE).toString()), 2);
                        dlbCanTecReh = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_REH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_REH).toString()), 2);

                        dlbTotRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString()), 2);
                        dlbCanAce_Ori = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString()), 2);
                        dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString()), 2);

                        dlbCanAce = (dlbTotRec - (dlbCanTecAce + dlbCanTecReh + (dlbCanRec - dlbCanRecOri)));

                        if (dlbCanAceOri <= 0) {
                            if (dlbCanAce_Ori > 0) {
                                tblDat.setValueAt("" + dlbCanAce_Ori, intCelSel, INT_TBL_CANACE);
                            } else {
                                tblDat.setValueAt("" + dlbCanAce, intCelSel, INT_TBL_CANACE);
                            }
                        }
                    }

                    blnMarTodCanTblDat = false;
                } else {
                    //Ocultar todas las columnas.
                    for (int i = 0; i < intNumFil; i++) {
                        tblDat.setValueAt("0", i, INT_TBL_CANACE);
                    }
                    blnMarTodCanTblDat = true;
                }

            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private boolean configurarTablaRevConfir() 
    {
        boolean blnRes = false;
        try 
        {
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CODITM, "Cod.Itm.");
            vecCab.add(INT_TBL_CODALT, "Cod.Alt.");
            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
            vecCab.add(INT_TBL_COD3LET,"Cod. 3 Letras");
            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
            
            vecCab.add(INT_TBL_NOMITM, "Nom.Itm.");
            vecCab.add(INT_TBL_DESUNI, "Des.Uni.");
            vecCab.add(INT_TBL_CODBOD, "Cod.Bod.");
            vecCab.add(INT_TBL_NOMBOD, "Nom.Bod.");
            vecCab.add(INT_TBL_CANVEN, "Can.Ven.");
            vecCab.add(INT_TBL_CANDEV, "Can.Dev.");
            vecCab.add(INT_TBL_FALSTKFIC, "Fal.Fis.");
            vecCab.add(INT_TBL_REVTEC, "Rev.Tec.");
            vecCab.add(INT_TBL_CANTOTREC, "Can.Tot.Rec.");
            vecCab.add(INT_TBL_CANTOTNUNREC, "Can.Tot.Nun.Rec.");

            vecCab.add(INT_TBL_CAN_TEC_REV_ACE, "Can.Tot.Ace.Tec.");
            vecCab.add(INT_TBL_CAN_TEC_REV_REH, "Can.Tot.Rec.Tec.");
            vecCab.add(INT_TBL_CAN_BOD_REV_ACE, "Can.Tot.Ace.Bod.");
            vecCab.add(INT_TBL_CAN_BOD_REV_REH, "Can.Tot.Rec.Bod.");

            vecCab.add(INT_TBL_CANREC, "Can.Rec.");
            vecCab.add(INT_TBL_OBSCANREC, "Obs.Can.Rec.");
            vecCab.add(INT_TBL_CANACE, "Can.Ace.");
            vecCab.add(INT_TBL_CANRCH, "Can.Rch.");
            vecCab.add(INT_TBL_OBSREVCONFING, "Obs.Rev.Con.Ing.");
            vecCab.add(INT_TBL_EST_REC_TEC, "");
            vecCab.add(INT_TBL_CODREG, "");
            vecCab.add(INT_TBL_CANTOTNUNREC_ORI, "Can.Tot.Nun.Rec.");
            vecCab.add(INT_TBL_CANREC_ORI, "Can.Rec.");
            vecCab.add(INT_TBL_CANACE_ORI, "Can.Ace.");
            vecCab.add(INT_TBL_CANRCH_ORI, "Can.Rch.");
            vecCab.add(INT_TBL_OBSREVTEC, "Obs.Rev.Tec");
            vecCab.add(INT_TBL_CANTOTDEVCLI, "Can.Dev.Cli");
            vecCab.add(INT_TBL_CANDEVCLI_ORI, "Can.Dev.Cli");
            vecCab.add(INT_TBL_CANDEVCLI, "Can.Dev.Cli");
            vecCab.add(INT_TBL_OBSDEVCLI, "Obs.Dev.Cli");
            vecCab.add(INT_TBL_FALSTKFICACE, "Fal.Fis.");
            vecCab.add(INT_TBL_IEBODFIS, "IE.Fis.Bod");

            vecCab.add(INT_TBL_CANSDEVPRV, "Can.Se.DevPrv.");
            vecCab.add(INT_TBL_CANNDEVPRV, "Can.No.DevPrv.");

            objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            java.awt.Color colFonCol;
            colFonCol = new java.awt.Color(228, 228, 203);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            objTblMod.setColumnDataType(INT_TBL_CANVEN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANDEV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANTOTREC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANTOTNUNREC, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            objTblMod.setColumnDataType(INT_TBL_CAN_TEC_REV_ACE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CAN_TEC_REV_REH, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CAN_BOD_REV_ACE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CAN_BOD_REV_REH, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            objTblMod.setColumnDataType(INT_TBL_CANREC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANACE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANRCH, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            objTblMod.setColumnDataType(INT_TBL_CANSDEVPRV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANNDEVPRV, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();  /**/

            Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_CANVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANDEV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANTOTREC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANTOTNUNREC).setCellRenderer(objTblCelRenLbl);

            tcmAux.getColumn(INT_TBL_CAN_BOD_REV_ACE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CAN_BOD_REV_REH).setCellRenderer(objTblCelRenLbl);

            tcmAux.getColumn(INT_TBL_CANREC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANACE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANRCH).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANSDEVPRV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANNDEVPRV).setCellRenderer(objTblCelRenLbl);

            objTblCelRenLbl = null;

            
            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(colFonCol);
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_CAN_TEC_REV_ACE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CAN_TEC_REV_REH).setCellRenderer(objTblCelRenLbl);

            tcmAux.getColumn(INT_TBL_CANSDEVPRV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANNDEVPRV).setCellRenderer(objTblCelRenLbl);


            objTblCelRenLbl = null;


            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_CANTOTNUNREC);
            vecAux.add("" + INT_TBL_CANACE);
            vecAux.add("" + INT_TBL_CANRCH);
            vecAux.add("" + INT_TBL_OBSREVCONFING);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            //Columnas Ocultas
            ArrayList arlColHid = new ArrayList();
            arlColHid.add("" + INT_TBL_CODITM);
            arlColHid.add("" + INT_TBL_EST_REC_TEC);
            arlColHid.add("" + INT_TBL_CODREG);
            //arlColHid.add(""+INT_TBL_CANTOTNUNREC_ORI);
            arlColHid.add("" + INT_TBL_CANREC_ORI);
            arlColHid.add("" + INT_TBL_CANACE_ORI);
            arlColHid.add("" + INT_TBL_CANRCH_ORI);
            arlColHid.add("" + INT_TBL_OBSREVTEC);
            arlColHid.add("" + INT_TBL_CANREC);
            arlColHid.add("" + INT_TBL_OBSCANREC);
            arlColHid.add("" + INT_TBL_CANTOTDEVCLI);
            arlColHid.add("" + INT_TBL_CANDEVCLI_ORI);
            arlColHid.add("" + INT_TBL_CANDEVCLI);
            arlColHid.add("" + INT_TBL_OBSDEVCLI);
            arlColHid.add("" + INT_TBL_FALSTKFIC);
            arlColHid.add("" + INT_TBL_FALSTKFICACE);
            arlColHid.add("" + INT_TBL_IEBODFIS);

            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid = null;

            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANVEN).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_CANDEV).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_CANTOTREC).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            objTblCelEdiTxt = null;

            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANTOTNUNREC).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    double dlbSumTotCanRec = 0.00;
                    double dlbSumTot = 0.00;
                    double dlbDev = 0.00;
                    double dlbRecOri = 0.00;
                    dlbSumTotCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString()), 2);
                    dlbSumTot += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC).toString()), 2);
                    dlbSumTot += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString()), 2);
                    dlbDev = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString()), 2);
                    dlbRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI).toString()), 2);

                    dlbSumTotCanRec = dlbSumTotCanRec - dlbRecOri;
                    dlbSumTot = dlbSumTot + dlbSumTotCanRec;
                    if (dlbSumTot > dlbDev) {
                        MensajeInf("Error! La cantidad a Nunca recibida a sobrepasado la cantidad de la devolución ");
                        tblDat.setValueAt("", intCelSel, INT_TBL_CANTOTNUNREC);
                    }
                }
            });
            objTblCelEdiTxt = null;

            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANREC).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    double dlbSumTotCanRec = 0.00;
                    double dlbSumTot = 0.00;
                    double dlbDev = 0.00;
                    double dlbSumTotRec = 0.00;
                    double dlbRec = 0.00, dlbRecOri = 0.00;
                    dlbSumTotCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString()), 2);
                    dlbSumTot += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC).toString()), 2);
                    dlbSumTot += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString()), 2);
                    dlbDev = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString()), 2);

                    dlbSumTotRec += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);
                    dlbSumTotRec += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString()), 2);
                    dlbRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString()), 2);
                    dlbRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI).toString()), 2);

                    if (dlbRec < dlbSumTotRec) {
                        MensajeInf("Error! La cantidad a recibida no es igual a la suma de cantidad aceptada y rechazada. ");
                        tblDat.setValueAt("" + dlbRecOri, intCelSel, INT_TBL_CANREC);
                    }

                    dlbSumTotCanRec = dlbSumTotCanRec - dlbRecOri;
                    dlbSumTot = dlbSumTot + dlbSumTotCanRec;
                    if (dlbSumTot > dlbDev) {
                        MensajeInf("Error! La cantidad recibida ha sobrepasado la cantidad de la devolución.");
                        tblDat.setValueAt("" + dlbRecOri, intCelSel, INT_TBL_CANREC);
                    }
                }
            });
            objTblCelEdiTxt = null;


            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANACE).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    double dlbSumTotRec = 0.00;
                    double dlbRec = 0.00;
                    double dlbCanAceOri = 0.00;
                    double dlbCanAce = 0.00;
                    double dlbCanBodAce = 0.00;
                    double dlbCanTecAce = 0.00;

                    if (tblDat.getValueAt(intCelSel, INT_TBL_EST_REC_TEC).toString().equals("S")) {

                        dlbCanAceOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString()), 2);
                        dlbCanAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);

                        dlbCanBodAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE).toString()), 2);
                        dlbCanTecAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE).toString()), 2);

                        if (dlbCanAce > dlbCanAceOri) {
                            if ((dlbCanBodAce + (dlbCanAce - dlbCanAceOri) > dlbCanTecAce)) {
                                MensajeInf("Error! La cantidad a Aceptada a sobrepasado la cantidad recibida.  ");
                                tblDat.setValueAt("" + dlbCanAceOri, intCelSel, INT_TBL_CANACE);
                            }
                        }
                    } 
                    else
                    {
                        double dlbCanRec = 0;
                        double dlbSumCanBod = 0;
                        double dlbCanRecOri = 0;
                        dlbCanAceOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString()), 2);
                        dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString()), 2);

                        dlbCanAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);
                        dlbCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString()), 2);

                        dlbSumCanBod += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE).toString()), 2);
                        dlbSumCanBod += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH).toString()), 2);

                        dlbRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString()), 2);

                        if (dlbCanAce > dlbCanAceOri) {
                            if ((dlbSumCanBod + ((dlbCanAce - dlbCanAceOri) + (dlbCanRec - dlbCanRecOri)) > dlbRec)) {
                                MensajeInf("Error! La cantidad a Aceptada a sobrepasado la cantidad recibida.  ");
                                tblDat.setValueAt("" + dlbCanAceOri, intCelSel, INT_TBL_CANACE);
                            }
                        }

                    }
                }
            });
            objTblCelEdiTxt = null;

            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANRCH).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    double dlbRec = 0.00;
                    double dlbCanRecOri = 0.00;
                    double dlbCanRec = 0.00;
                    double dlbCanBodRec = 0.00;
                    double dlbCanTecRec = 0.00;

                    if (tblDat.getValueAt(intCelSel, INT_TBL_EST_REC_TEC).toString().equals("S")) {

                        dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString()), 2);
                        dlbCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString()), 2);

                        dlbCanBodRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH).toString()), 2);
                        dlbCanTecRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_REH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_REH).toString()), 2);

                        if (dlbCanRec > dlbCanRecOri) {
                            if ((dlbCanBodRec + (dlbCanRec - dlbCanRecOri) > dlbCanTecRec)) {
                                MensajeInf("Error! La cantidad a rechazada a sobrepasado la cantidad recibida.  ");
                                tblDat.setValueAt("" + dlbCanRecOri, intCelSel, INT_TBL_CANRCH);
                            }
                        }

                    } 
                    else
                    {
                        double dlbCanAceOri = 0;
                        double dlbSumCanBod = 0;
                        double dlbCanAce = 0;
                        dlbCanAceOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString()), 2);
                        dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString()), 2);

                        dlbCanAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);
                        dlbCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString()), 2);

                        dlbSumCanBod += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE).toString()), 2);
                        dlbSumCanBod += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH).toString()), 2);

                        dlbRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString()), 2);

                        if (dlbCanRec > dlbCanRecOri) {
                            if ((dlbSumCanBod + ((dlbCanAce - dlbCanAceOri) + (dlbCanRec - dlbCanRecOri)) > dlbRec)) {
                                MensajeInf("Error! La cantidad a rechazada a sobrepasado la cantidad recibida. ");
                                tblDat.setValueAt("" + dlbCanRecOri, intCelSel, INT_TBL_CANRCH);
                            }
                        }

//                        dlbSumTotRec += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);
//                        dlbSumTotRec += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString()), 2);
//                        dlbSumTotRec += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE).toString()), 2);
//                        dlbSumTotRec += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH).toString()), 2);
//                        
//                        dlbRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString()), 2);
//                          // System.out.println(" "+  dlbSumTotRec +" --- "+  dlbRec );
//                        if(dlbSumTotRec>dlbRec){
//                           MensajeInf("Error! La cantidad a rechazada a sobrepasado la cantidad recibida. ");
//                           tblDat.setValueAt("", intCelSel, INT_TBL_CANRCH);
//                        }

                    }
                }
            });
            objTblCelEdiTxt = null;

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODALT).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(165);
            tcmAux.getColumn(INT_TBL_DESUNI).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CANVEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CANDEV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_REVTEC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CANTOTREC).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CANTOTNUNREC).setPreferredWidth(intAnchura);

            tcmAux.getColumn(INT_TBL_CAN_TEC_REV_ACE).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CAN_TEC_REV_REH).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CAN_BOD_REV_ACE).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CAN_BOD_REV_REH).setPreferredWidth(intAnchura);

            tcmAux.getColumn(INT_TBL_CANREC).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_OBSCANREC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CANACE).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CANRCH).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_OBSREVCONFING).setPreferredWidth(80);

            tcmAux.getColumn(INT_TBL_CANSDEVPRV).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CANNDEVPRV).setPreferredWidth(intAnchura);


            Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
            Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_REVTEC).setCellRenderer(objTblCelRenChk);
            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_REVTEC).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if (tblDat.getValueAt(intCelSel, INT_TBL_EST_REC_TEC).toString().equals("S")) {
                        tblDat.setValueAt(true, intCelSel, INT_TBL_REVTEC);
                    }

                }
            });
            objTblCelRenChk = null;
            objTblCelEdiChk = null;
            ZafTblEdi zafTblEdi = new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatMouseClickedConf(evt);
                }
            });


            tcmAux = null;

            setEditable(true);
            blnRes = true;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void tblDatMouseClickedConf(MouseEvent evt) 
    {
        int intNumFil;
        int intCelSel = 0;
        double dlbCanTecAce = 0, dlbCanAceOri = 0, dlbCanAce = 0, dlbCanBodAce = 0, dlbCanAce_Ori = 0, dlbTotRec = 0, dlbCanBodRec = 0, dlbCanRec = 0, dlbCanRecOri = 0;
        try 
        {
            intNumFil = tblDat.getRowCount();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1 && tblDat.columnAtPoint(evt.getPoint()) == INT_TBL_CANRCH) {
                if (blnMarTodCanTblDat) {
                    //Mostrar todas las columnas.
                    for (int i = 0; i < intNumFil; i++) {

                        intCelSel = i;
                        if (tblDat.getValueAt(intCelSel, INT_TBL_EST_REC_TEC).toString().equals("S")) {

                            dlbCanAce_Ori = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString()), 2);
                            dlbCanAceOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString()), 2);
                            dlbCanBodAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH).toString()), 2);
                            dlbCanTecAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_REH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_REH).toString()), 2);

                            dlbCanAce = (dlbCanTecAce - dlbCanBodAce);

                            if (dlbCanAceOri <= 0) {
                                if (dlbCanAce_Ori > 0) {
                                    tblDat.setValueAt("" + dlbCanAce_Ori, intCelSel, INT_TBL_CANRCH);
                                } else {
                                    tblDat.setValueAt("" + dlbCanAce, intCelSel, INT_TBL_CANRCH);
                                }
                            }
                        }
                    }

                    blnMarTodCanTblDat = false;
                } else {
                    //Ocultar todas las columnas.
                    for (int i = 0; i < intNumFil; i++) {
                        tblDat.setValueAt("0", i, INT_TBL_CANRCH);
                    }
                    blnMarTodCanTblDat = true;
                }

            }


            if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1 && tblDat.columnAtPoint(evt.getPoint()) == INT_TBL_CANACE) {
                if (blnMarTodCanTblDat) {
                    //Mostrar todas las columnas.
                    for (int i = 0; i < intNumFil; i++) {

                        intCelSel = i;
                        if (tblDat.getValueAt(intCelSel, INT_TBL_EST_REC_TEC).toString().equals("S")) {

                            dlbCanAce_Ori = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString()), 2);
                            dlbCanAceOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);
                            dlbCanBodAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE).toString()), 2);
                            dlbCanTecAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE).toString()), 2);

                            dlbCanAce = (dlbCanTecAce - dlbCanBodAce);

                            if (dlbCanAceOri <= 0) {
                                if (dlbCanAce_Ori > 0) {
                                    tblDat.setValueAt("" + dlbCanAce_Ori, intCelSel, INT_TBL_CANACE);
                                } else {
                                    tblDat.setValueAt("" + dlbCanAce, intCelSel, INT_TBL_CANACE);
                                }
                            }
                        } else {

                            dlbCanAce_Ori = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString()), 2);
                            dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString()), 2);

                            dlbCanAceOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);
                            dlbCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString()), 2);

                            dlbCanBodAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE).toString()), 2);
                            dlbCanBodRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH).toString()), 2);
                            dlbTotRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString()), 2);

                            dlbCanAce = (dlbTotRec - (dlbCanBodAce + dlbCanBodRec + (dlbCanRec - dlbCanRecOri)));

                            if (dlbCanAceOri <= 0) {
                                if (dlbCanAce_Ori > 0) {
                                    tblDat.setValueAt("" + dlbCanAce_Ori, intCelSel, INT_TBL_CANACE);
                                } else {
                                    tblDat.setValueAt("" + dlbCanAce, intCelSel, INT_TBL_CANACE);
                                }
                            }

                        }

                    }

                    blnMarTodCanTblDat = false;
                } else {
                    //Ocultar todas las columnas.
                    for (int i = 0; i < intNumFil; i++) {
                        tblDat.setValueAt("0", i, INT_TBL_CANACE);
                    }
                    blnMarTodCanTblDat = true;
                }

            }

        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private void ocultaCol(int intCol) {
        tblDat.getColumnModel().getColumn(intCol).setWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);
    }

    private boolean configurarTablaRecMer() 
    {
        boolean blnRes = false;
        try
        {
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CODITM, "Cod.Itm.");
            vecCab.add(INT_TBL_CODALT, "Cod.Alt.");
            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
            vecCab.add(INT_TBL_COD3LET,"Cod. 3 Letras");
            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
            vecCab.add(INT_TBL_NOMITM, "Nom.Itm.");
            vecCab.add(INT_TBL_DESUNI, "Des.Uni.");
            vecCab.add(INT_TBL_CODBOD, "Cod.Bod.");
            vecCab.add(INT_TBL_NOMBOD, "Nom.Bod.");
            vecCab.add(INT_TBL_CANVEN, "Can.Ven.");
            vecCab.add(INT_TBL_CANDEV, "Can.Dev.");
            vecCab.add(INT_TBL_FALSTKFIC, "Fal.stk.");
            vecCab.add(INT_TBL_REVTEC, "Rev.Tec.");
            vecCab.add(INT_TBL_CANTOTREC, "Can.Tot.Rec.");
            vecCab.add(INT_TBL_CANTOTNUNREC, "Can.Tot.Nun.Rec.");

            vecCab.add(INT_TBL_CAN_TEC_REV_ACE, "Can.Tot.Ace.Tec.");
            vecCab.add(INT_TBL_CAN_TEC_REV_REH, "Can.Tot.Rec.Tec.");
            vecCab.add(INT_TBL_CAN_BOD_REV_ACE, "Can.Tot.Ace.Bod.");
            vecCab.add(INT_TBL_CAN_BOD_REV_REH, "Can.Tot.Rec.Bod.");

            vecCab.add(INT_TBL_CANREC, "Can.Rec.");
            vecCab.add(INT_TBL_OBSCANREC, "Obs.Can.Rec.");
            vecCab.add(INT_TBL_CANACE, "Can.Ace.");
            vecCab.add(INT_TBL_CANRCH, "Can.Rch.");
            vecCab.add(INT_TBL_OBSREVCONFING, "Obs.Rev.Con.Ing.");
            vecCab.add(INT_TBL_EST_REC_TEC, "");
            vecCab.add(INT_TBL_CODREG, "");
            vecCab.add(INT_TBL_CANTOTNUNREC_ORI, "Can.Tot.Nun.Rec.");
            vecCab.add(INT_TBL_CANREC_ORI, "Can.Rec.");
            vecCab.add(INT_TBL_CANACE_ORI, "Can.Ace.");
            vecCab.add(INT_TBL_CANRCH_ORI, "Can.Rch.");
            vecCab.add(INT_TBL_OBSREVTEC, "Obs.Rev.Tec");
            vecCab.add(INT_TBL_CANTOTDEVCLI, "Can.Dev.Cli");
            vecCab.add(INT_TBL_CANDEVCLI_ORI, "Can.Dev.Cli");
            vecCab.add(INT_TBL_CANDEVCLI, "Can.Dev.Cli");
            vecCab.add(INT_TBL_OBSDEVCLI, "Obs.Dev.Cli");
            vecCab.add(INT_TBL_FALSTKFICACE, "Fal.stk.Ace");
            vecCab.add(INT_TBL_IEBODFIS, "IE.Fis.Bod");

            vecCab.add(INT_TBL_CANSDEVPRV, "Can.Se.DevPrv.");
            vecCab.add(INT_TBL_CANNDEVPRV, "Can.No.DevPrv.");

            objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            objTblMod.setColumnDataType(INT_TBL_CANVEN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANDEV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANTOTREC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANTOTNUNREC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANREC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANACE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANRCH, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANSDEVPRV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANNDEVPRV, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();  /**/

            Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_CANVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANDEV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANTOTREC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANTOTNUNREC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANREC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANACE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANRCH).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANSDEVPRV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANNDEVPRV).setCellRenderer(objTblCelRenLbl);

            objTblCelRenLbl = null;

            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_REVTEC);
            vecAux.add("" + INT_TBL_CANTOTNUNREC);
            vecAux.add("" + INT_TBL_CANREC);
            vecAux.add("" + INT_TBL_OBSCANREC);
            vecAux.add("" + INT_TBL_CANACE);
            vecAux.add("" + INT_TBL_CANRCH);
            vecAux.add("" + INT_TBL_OBSREVCONFING);
            vecAux.add("" + INT_TBL_FALSTKFICACE);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            //Columnas Ocultas
            ArrayList arlColHid = new ArrayList();
            arlColHid.add("" + INT_TBL_CODITM);
            arlColHid.add("" + INT_TBL_EST_REC_TEC);
            arlColHid.add("" + INT_TBL_CODREG);
            //  arlColHid.add(""+INT_TBL_CANTOTNUNREC_ORI);
            arlColHid.add("" + INT_TBL_CANREC_ORI);
            arlColHid.add("" + INT_TBL_CANACE_ORI);
            arlColHid.add("" + INT_TBL_CANRCH_ORI);
            arlColHid.add("" + INT_TBL_OBSREVTEC);

            arlColHid.add("" + INT_TBL_CAN_TEC_REV_ACE);
            arlColHid.add("" + INT_TBL_CAN_TEC_REV_REH);
            arlColHid.add("" + INT_TBL_CAN_BOD_REV_ACE);
            arlColHid.add("" + INT_TBL_CAN_BOD_REV_REH);
            arlColHid.add("" + INT_TBL_CANTOTDEVCLI);
            arlColHid.add("" + INT_TBL_CANDEVCLI_ORI);
            arlColHid.add("" + INT_TBL_CANDEVCLI);
            arlColHid.add("" + INT_TBL_OBSDEVCLI);
            arlColHid.add("" + INT_TBL_IEBODFIS);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid = null;

            objTblCelEdiTxtObs = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANVEN).setCellEditor(objTblCelEdiTxtObs);
            tcmAux.getColumn(INT_TBL_CANDEV).setCellEditor(objTblCelEdiTxtObs);
            tcmAux.getColumn(INT_TBL_CANTOTREC).setCellEditor(objTblCelEdiTxtObs);
            tcmAux.getColumn(INT_TBL_OBSREVTEC).setCellEditor(objTblCelEdiTxtObs);
            tcmAux.getColumn(INT_TBL_OBSREVCONFING).setCellEditor(objTblCelEdiTxtObs);
            objTblCelEdiTxtObs.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if ((tblDat.getValueAt(intCelSel, INT_TBL_REVTEC) == null ? false : (tblDat.getValueAt(intCelSel, INT_TBL_REVTEC).toString().equals("true") ? true : false))) {
                        objTblCelEdiTxtObs.setCancelarEdicion(true);
                    }
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });


            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_OBSCANREC).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            objTblCelEdiTxt = null;



            objTblCelEdiTxtCanNumRec = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANTOTNUNREC).setCellEditor(objTblCelEdiTxtCanNumRec);
            objTblCelEdiTxtCanNumRec.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    int intCelSel=tblDat.getSelectedRow();
//                    if((tblDat.getValueAt(intCelSel, INT_TBL_REVTEC)==null?false:(tblDat.getValueAt(intCelSel, INT_TBL_REVTEC).toString().equals("true")?true:false)))
//                       objTblCelEdiTxtCanNumRec.setCancelarEdicion(true); 
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    double dlbSumTotCanRec = 0.00;
                    double dlbSumTot = 0.00;
                    double dlbDev = 0.00;
                    double dlbRecOri = 0.00;
                    dlbSumTotCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString()), 2);
                    dlbSumTot += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC).toString()), 2);
                    dlbSumTot += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString()), 2);
                    dlbDev = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString()), 2);
                    dlbRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI).toString()), 2);

                    dlbSumTotCanRec = dlbSumTotCanRec - dlbRecOri;
                    dlbSumTot = dlbSumTot + dlbSumTotCanRec;
                    if (dlbSumTot > dlbDev) {
                        MensajeInf("Error! La cantidad a Nunca recibida a sobrepasado la cantidad de la devolución ");
                        tblDat.setValueAt("", intCelSel, INT_TBL_CANTOTNUNREC);
                    }
                }
            });

            objTblCelEdiTxtCanRec = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANREC).setCellEditor(objTblCelEdiTxtCanRec);
            objTblCelEdiTxtCanRec.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    int intCelSel=tblDat.getSelectedRow();
//                    if((tblDat.getValueAt(intCelSel, INT_TBL_REVTEC)==null?false:(tblDat.getValueAt(intCelSel, INT_TBL_REVTEC).toString().equals("true")?true:false)))
//                       objTblCelEdiTxtCanRec.setCancelarEdicion(true); 
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    double dlbSumTotCanRec = 0.00;
                    double dlbSumTot = 0.00;
                    double dlbDev = 0.00;
                    double dlbSumTotRec = 0.00;
                    double dlbRec = 0.00, dlbRecOri = 0.00;
                    double dlbSumTotRec_ori = 0, dlbSumTotReh_ori = 0;
                    dlbSumTotCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString()), 2);
                    dlbSumTot += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC).toString()), 2);
                    dlbSumTot += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString()), 2);
                    dlbDev = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString()), 2);

                    dlbSumTotRec += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);
                    dlbSumTotRec += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString()), 2);
                    dlbRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString()), 2);
                    dlbRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI).toString()), 2);

                    dlbSumTotRec_ori += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString()), 2);
                    dlbSumTotReh_ori += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString()), 2);

                    if (dlbRec < dlbSumTotRec) {
                        MensajeInf("Error! La cantidad a recibida no es igual a la suma de cantidad aceptada y rechazada. ");
                        tblDat.setValueAt("" + dlbRecOri, intCelSel, INT_TBL_CANREC);
                        tblDat.setValueAt("" + dlbSumTotRec_ori, intCelSel, INT_TBL_CANACE);
                        tblDat.setValueAt("" + dlbSumTotReh_ori, intCelSel, INT_TBL_CANRCH);
                    }

                    dlbSumTotCanRec = dlbSumTotCanRec - dlbRecOri;
                    dlbSumTot = dlbSumTot + dlbSumTotCanRec;
                    if (dlbSumTot > dlbDev) {
                        MensajeInf("Error! La cantidad recibida ha sobrepasado la cantidad de la devolución.");
                        tblDat.setValueAt("" + dlbRecOri, intCelSel, INT_TBL_CANREC);
                        tblDat.setValueAt("" + dlbSumTotRec_ori, intCelSel, INT_TBL_CANACE);
                        tblDat.setValueAt("" + dlbSumTotReh_ori, intCelSel, INT_TBL_CANRCH);
                    }

                }
            });


            objTblCelEdiTxtCanAce = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANACE).setCellEditor(objTblCelEdiTxtCanAce);
            objTblCelEdiTxtCanAce.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if ((tblDat.getValueAt(intCelSel, INT_TBL_REVTEC) == null ? false : (tblDat.getValueAt(intCelSel, INT_TBL_REVTEC).toString().equals("true") ? true : false))) {
                        objTblCelEdiTxtCanAce.setCancelarEdicion(true);
                    }
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    double dlbSumTotRec = 0.00;
                    double dlbRec = 0.00;
                    double dlbDev = 0;
                    double dlbCanAceOri = 0;
                    double dlbSumTot = 0;
                    double dlbSumTotCanRec = 0;
                    double dlbRecOri = 0;
                    dlbCanAceOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString()), 2);

                    dlbSumTotCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString()), 2);

                    dlbSumTot += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC).toString()), 2);
                    dlbSumTot += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);

                    dlbRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI).toString()), 2);


                    dlbSumTotRec += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);
                    dlbSumTotRec += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString()), 2);
                    dlbRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString()), 2);

                    dlbDev = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString()), 2);


                    if (dlbRec <= 0) {
                        int intEst = 0;

                        if (dlbSumTotRec > dlbDev) {
                            intEst = 1;
                        } else {
                            tblDat.setValueAt("" + dlbSumTotRec, intCelSel, INT_TBL_CANREC);
                        }


                        dlbSumTotCanRec = dlbSumTotCanRec - dlbRecOri;
                        dlbSumTot = dlbSumTot + dlbSumTotCanRec;
                        if (dlbSumTot > dlbDev) {
                            intEst = 2;
                            //tblDat.setValueAt(""+dlbSumTotRec_ori, intCelSel, INT_TBL_CANACE);
                            //tblDat.setValueAt(""+dlbSumTotReh_ori, intCelSel, INT_TBL_CANRCH);
                        }

                        if (intEst == 1) {
                            MensajeInf("Error! La cantidad recibida ha sobrepasado la cantidad de la devolución.");
                            tblDat.setValueAt("" + dlbCanAceOri, intCelSel, INT_TBL_CANACE);
                        }
                        if (intEst == 2) {
                            MensajeInf("Error! La cantidad recibida ha sobrepasado la cantidad de la devolución.");
                            tblDat.setValueAt("", intCelSel, INT_TBL_CANREC);
                            tblDat.setValueAt("" + dlbCanAceOri, intCelSel, INT_TBL_CANACE);
                        }

                    } else {
                        if (dlbSumTotRec > dlbRec) {
                            MensajeInf("Error! La cantidad a Aceptada a sobrepasado la cantidad recibida. ");
                            tblDat.setValueAt("", intCelSel, INT_TBL_CANACE);
                        }
                    }

                }
            });

            objTblCelEdiTxtCanReh = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANRCH).setCellEditor(objTblCelEdiTxtCanReh);
            objTblCelEdiTxtCanReh.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if ((tblDat.getValueAt(intCelSel, INT_TBL_REVTEC) == null ? false : (tblDat.getValueAt(intCelSel, INT_TBL_REVTEC).toString().equals("true") ? true : false))) {
                        objTblCelEdiTxtCanReh.setCancelarEdicion(true);
                    }
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    double dlbSumTotRec = 0.00;
                    double dlbRec = 0.00;
                    dlbSumTotRec += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);
                    dlbSumTotRec += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString()), 2);
                    dlbRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString()), 2);

                    if (dlbSumTotRec > dlbRec) {
                        MensajeInf("Error! La cantidad a rechazada a sobrepasado la cantidad recibida. ");
                        tblDat.setValueAt("", intCelSel, INT_TBL_CANRCH);
                    }
                }
            });

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODALT).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(165);
            tcmAux.getColumn(INT_TBL_DESUNI).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CANVEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CANDEV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_FALSTKFIC).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_REVTEC).setPreferredWidth(20);
            //tcmAux.getColumn(INT_TBL_CANTOTREC).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CANTOTNUNREC).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CANREC).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_OBSCANREC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CANACE).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CANRCH).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_OBSREVCONFING).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FALSTKFICACE).setPreferredWidth(35);

            tcmAux.getColumn(INT_TBL_CANSDEVPRV).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CANNDEVPRV).setPreferredWidth(intAnchura);

            ocultaCol(INT_TBL_CANTOTREC);

            Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
            Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_FALSTKFIC).setCellRenderer(objTblCelRenChk);
            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_FALSTKFIC).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //int intCelSel=tblDat.getSelectedRow();
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            objTblCelRenChk = null;
            objTblCelEdiChk = null;


            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_REVTEC).setCellRenderer(objTblCelRenChk);
            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_REVTEC).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //int intCelSel=tblDat.getSelectedRow();
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if (tblDat.getValueAt(intCelSel, INT_TBL_EST_REC_TEC).toString().equals("S")) {
                        tblDat.setValueAt(true, intCelSel, INT_TBL_REVTEC);
                    } else {

                        if (tblDat.getValueAt(intCelSel, INT_TBL_FALSTKFIC).toString().equals("true")) {
                            tblDat.setValueAt(false, intCelSel, INT_TBL_REVTEC);
                            MensajeInf("Error! No es posible establecer revisión técnica porque este ítem tiene faltante físico de stock. ");
                        } else {
                            if ((tblDat.getValueAt(intCelSel, INT_TBL_REVTEC) == null ? false : (tblDat.getValueAt(intCelSel, INT_TBL_REVTEC).toString().equals("true") ? true : false))) {
                                if (MensjConfir("¿Esta seguro que necesita revición tecnica este item.?")) {
                                    tblDat.setValueAt(false, intCelSel, INT_TBL_REVTEC);
                                } else {
                                    tblDat.setValueAt(null, intCelSel, INT_TBL_CANACE);
                                    tblDat.setValueAt(null, intCelSel, INT_TBL_CANRCH);
                                    tblDat.setValueAt(null, intCelSel, INT_TBL_OBSREVCONFING);
                                }
                            }
                        }
                    }

                }
            });
            objTblCelRenChk = null;
            objTblCelEdiChk = null;




            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_FALSTKFICACE).setCellRenderer(objTblCelRenChk);
            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_FALSTKFICACE).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //int intCelSel=tblDat.getSelectedRow();
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();

                    if (!(tblDat.getValueAt(intCelSel, INT_TBL_FALSTKFIC).toString().equals("true"))) {
                        tblDat.setValueAt(false, intCelSel, INT_TBL_FALSTKFICACE);
                        MensajeInf("Error! No es posible aceptar devolución por faltante de stock porque no esta marcado como faltante de stock.");
                    }
                }
            });
            objTblCelRenChk = null;
            objTblCelEdiChk = null;
            ZafTblEdi zafTblEdi = new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatMouseClicked(evt);
                }
            });

            tcmAux = null;

            setEditable(true);
            blnRes = true;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
//Este es
    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) 
    {
        int i, intNumFil;
        double dlbSumTot = 0;

        try {
            intNumFil = tblDat.getRowCount();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1 && tblDat.columnAtPoint(evt.getPoint()) == INT_TBL_CANACE) {
                if (blnMarTodCanTblDat) {
                    //Mostrar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        String strCanven = (tblDat.getValueAt(i, INT_TBL_CANDEV) == null ? "" : tblDat.getValueAt(i, INT_TBL_CANDEV).toString());

                        dlbSumTot += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTREC) == null ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTREC).toString()), 2);
                        dlbSumTot += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC) == null ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString()), 2);
                        if (!(tblDat.getValueAt(i, INT_TBL_REVTEC) == null ? false : (tblDat.getValueAt(i, INT_TBL_REVTEC).toString().equals("true") ? true : false))) {
                            if (dlbSumTot <= 0) {
                                tblDat.setValueAt(strCanven, i, INT_TBL_CANACE);
                                tblDat.setValueAt(strCanven, i, INT_TBL_CANREC);
                                tblDat.setValueAt("0", i, INT_TBL_CANRCH);
                            }
                        }
                    }
                    blnMarTodCanTblDat = false;
                } else {
                    //Ocultar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        tblDat.setValueAt("0", i, INT_TBL_CANACE);
                    }
                    blnMarTodCanTblDat = true;
                }

            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private boolean configurarTablaDevCli() 
    {
        boolean blnRes = false;
        try 
        {
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CODITM, "Cod.Itm.");
            vecCab.add(INT_TBL_CODALT, "Cod.Alt.");
            vecCab.add(INT_TBL_NOMITM, "Nom.Itm.");
            vecCab.add(INT_TBL_DESUNI, "Des.Uni.");
            vecCab.add(INT_TBL_CODBOD, "Cod.Bod.");
            vecCab.add(INT_TBL_NOMBOD, "Nom.Bod.");
            vecCab.add(INT_TBL_CANVEN, "Can.Ven.");
            vecCab.add(INT_TBL_CANDEV, "Can.Dev.");
            vecCab.add(INT_TBL_FALSTKFIC, "Fal.stk.");
            vecCab.add(INT_TBL_REVTEC, "Rev.Tec.");
            vecCab.add(INT_TBL_CANTOTREC, "Can.Tot.Rec.");
            vecCab.add(INT_TBL_CANTOTNUNREC, "Can.Tot.Nun.Rec.");

            vecCab.add(INT_TBL_CAN_TEC_REV_ACE, "Can.Tot.Ace.Tec.");
            vecCab.add(INT_TBL_CAN_TEC_REV_REH, "Can.Tot.Rec.Tec.");
            vecCab.add(INT_TBL_CAN_BOD_REV_ACE, "Can.Tot.Ace.Bod.");
            vecCab.add(INT_TBL_CAN_BOD_REV_REH, "Can.Tot.Rec.Bod.");

            vecCab.add(INT_TBL_CANREC, "Can.Rec.");
            vecCab.add(INT_TBL_OBSCANREC, "Obs.Can.Rec.");
            vecCab.add(INT_TBL_CANACE, "Can.Ace.");
            vecCab.add(INT_TBL_CANRCH, "Can.Rch.");
            vecCab.add(INT_TBL_OBSREVCONFING, "Obs.Rev.Con.Ing.");
            vecCab.add(INT_TBL_EST_REC_TEC, "");
            vecCab.add(INT_TBL_CODREG, "");
            vecCab.add(INT_TBL_CANTOTNUNREC_ORI, "Can.Tot.Nun.Rec.");
            vecCab.add(INT_TBL_CANREC_ORI, "Can.Rec.");
            vecCab.add(INT_TBL_CANACE_ORI, "Can.Ace.");
            vecCab.add(INT_TBL_CANRCH_ORI, "Can.Rch.");
            vecCab.add(INT_TBL_OBSREVTEC, "Obs.Rev.Tec");
            vecCab.add(INT_TBL_CANTOTDEVCLI, "Can.tot.Dev.Cli");
            vecCab.add(INT_TBL_CANDEVCLI_ORI, "Can.Dev.Cli.ori");
            vecCab.add(INT_TBL_CANDEVCLI, "Can.Dev.Cli");
            vecCab.add(INT_TBL_OBSDEVCLI, "Obs.Dev.Cli");
            vecCab.add(INT_TBL_FALSTKFICACE, "Fal.Fis.");
            vecCab.add(INT_TBL_IEBODFIS, "IE.Fis.Bod");

            vecCab.add(INT_TBL_CANSDEVPRV, "Can.Se.DevPrv.");
            vecCab.add(INT_TBL_CANNDEVPRV, "Can.No.DevPrv.");

            objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            objTblMod.setColumnDataType(INT_TBL_CANVEN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANDEV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANTOTREC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANTOTNUNREC, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            objTblMod.setColumnDataType(INT_TBL_CAN_TEC_REV_ACE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CAN_TEC_REV_REH, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CAN_BOD_REV_ACE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CAN_BOD_REV_REH, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            objTblMod.setColumnDataType(INT_TBL_CANREC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANACE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANRCH, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANDEVCLI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANTOTDEVCLI, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            objTblMod.setColumnDataType(INT_TBL_CANSDEVPRV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANNDEVPRV, ZafTblMod.INT_COL_DBL, new Integer(0), null);


            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();  /**/

            Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_CANVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANDEV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANTOTREC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANTOTNUNREC).setCellRenderer(objTblCelRenLbl);

            tcmAux.getColumn(INT_TBL_CAN_TEC_REV_ACE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CAN_TEC_REV_REH).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CAN_BOD_REV_ACE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CAN_BOD_REV_REH).setCellRenderer(objTblCelRenLbl);

            tcmAux.getColumn(INT_TBL_CANREC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANACE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANRCH).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANDEVCLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANTOTDEVCLI).setCellRenderer(objTblCelRenLbl);

            objTblCelRenLbl = null;


            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_CANDEVCLI);
            vecAux.add("" + INT_TBL_OBSDEVCLI);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            //Columnas Ocultas
            ArrayList arlColHid = new ArrayList();

            arlColHid.add("" + INT_TBL_CODITM);
            arlColHid.add("" + INT_TBL_EST_REC_TEC);
            arlColHid.add("" + INT_TBL_CODREG);
            //    arlColHid.add(""+INT_TBL_CANTOTNUNREC_ORI);
            arlColHid.add("" + INT_TBL_CANREC_ORI);
            arlColHid.add("" + INT_TBL_CANACE_ORI);
            arlColHid.add("" + INT_TBL_CANRCH_ORI);
            arlColHid.add("" + INT_TBL_OBSREVTEC);
            arlColHid.add("" + INT_TBL_CANREC);
            arlColHid.add("" + INT_TBL_OBSCANREC);
            arlColHid.add("" + INT_TBL_CAN_TEC_REV_ACE);
            arlColHid.add("" + INT_TBL_CAN_TEC_REV_REH);
            arlColHid.add("" + INT_TBL_CAN_BOD_REV_ACE);
            arlColHid.add("" + INT_TBL_CAN_BOD_REV_REH);
            arlColHid.add("" + INT_TBL_CANACE);
            arlColHid.add("" + INT_TBL_CANRCH);
            arlColHid.add("" + INT_TBL_OBSREVCONFING);
            arlColHid.add("" + INT_TBL_CANDEVCLI_ORI);
            arlColHid.add("" + INT_TBL_CANTOTDEVCLI);
            arlColHid.add("" + INT_TBL_FALSTKFIC);
            arlColHid.add("" + INT_TBL_FALSTKFICACE);
            arlColHid.add("" + INT_TBL_IEBODFIS);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid = null;

            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANVEN).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_CANDEV).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_CANTOTREC).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            objTblCelEdiTxt = null;

            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANTOTNUNREC).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    double dlbSumTotCanRec = 0.00;
                    double dlbSumTot = 0.00;
                    double dlbDev = 0.00;
                    double dlbRecOri = 0.00;
                    dlbSumTotCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString()), 2);
                    dlbSumTot += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC).toString()), 2);
                    dlbSumTot += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString()), 2);
                    dlbDev = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString()), 2);
                    dlbRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI).toString()), 2);

                    dlbSumTotCanRec = dlbSumTotCanRec - dlbRecOri;
                    dlbSumTot = dlbSumTot + dlbSumTotCanRec;
                    if (dlbSumTot > dlbDev) {
                        MensajeInf("Error! La cantidad a Nunca recibida a sobrepasado la cantidad de la devolución ");
                        tblDat.setValueAt("", intCelSel, INT_TBL_CANTOTNUNREC);
                    }
                }
            });
            objTblCelEdiTxt = null;

            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANREC).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    double dlbSumTotCanRec = 0.00;
                    double dlbSumTot = 0.00;
                    double dlbDev = 0.00;
                    double dlbSumTotRec = 0.00;
                    double dlbRec = 0.00, dlbRecOri = 0.00;
                    dlbSumTotCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString()), 2);
                    dlbSumTot += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC).toString()), 2);
                    dlbSumTot += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString()), 2);
                    dlbDev = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString()), 2);

                    dlbSumTotRec += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);
                    dlbSumTotRec += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString()), 2);
                    dlbRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString()), 2);
                    dlbRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI).toString()), 2);

                    if (dlbRec < dlbSumTotRec) {
                        MensajeInf("Error! La cantidad a recibida no es igual a la suma de cantidad aceptada y rechazada. ");
                        tblDat.setValueAt("" + dlbRecOri, intCelSel, INT_TBL_CANREC);
                    }

                    dlbSumTotCanRec = dlbSumTotCanRec - dlbRecOri;
                    dlbSumTot = dlbSumTot + dlbSumTotCanRec;
                    if (dlbSumTot > dlbDev) {
                        MensajeInf("Error! La cantidad recibida ha sobrepasado la cantidad de la devolución.");
                        tblDat.setValueAt("" + dlbRecOri, intCelSel, INT_TBL_CANREC);
                    }
                }
            });
            objTblCelEdiTxt = null;


            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANACE).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    double dlbRec = 0.00;
                    double dlbCanAceOri = 0.00;
                    double dlbCanAce = 0.00;
                    double dlbCanBodAce = 0.00;
                    double dlbCanTecAce = 0.00;

                    if (tblDat.getValueAt(intCelSel, INT_TBL_EST_REC_TEC).toString().equals("S")) {

                        dlbCanAceOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString()), 2);
                        dlbCanAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);

                        dlbCanBodAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE).toString()), 2);
                        dlbCanTecAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_TEC_REV_ACE).toString()), 2);

                        if (dlbCanAce > dlbCanAceOri) {
                            if ((dlbCanBodAce + (dlbCanAce - dlbCanAceOri) > dlbCanTecAce)) {
                                MensajeInf("Error! La cantidad a Aceptada a sobrepasado la cantidad recibida.  ");
                                tblDat.setValueAt("" + dlbCanAceOri, intCelSel, INT_TBL_CANACE);
                            }
                        }

                    } else {
                        double dlbCanRec = 0;
                        double dlbSumCanBod = 0;
                        double dlbCanRecOri = 0;
                        dlbCanAceOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString()), 2);
                        dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString()), 2);

                        dlbCanAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);
                        dlbCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString()), 2);

                        dlbSumCanBod += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_ACE).toString()), 2);
                        dlbSumCanBod += objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH).toString()), 2);

                        dlbRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTREC).toString()), 2);

                        if (dlbCanAce > dlbCanAceOri) {
                            if ((dlbSumCanBod + ((dlbCanAce - dlbCanAceOri) + (dlbCanRec - dlbCanRecOri)) > dlbRec)) {
                                MensajeInf("Error! La cantidad a Aceptada a sobrepasado la cantidad recibida.  ");
                                tblDat.setValueAt("" + dlbCanAceOri, intCelSel, INT_TBL_CANACE);
                            }
                        }


                    }
                }
            });
            objTblCelEdiTxt = null;

            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANDEVCLI).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    double dlbTotNumRec = 0.00;
                    double dlbCanDevCli = 0.00;
                    double dlbCanTotDevCli = 0.00;
                    double dlbCanDevCli_Ori = 0.00;
                    double dlbCanDevRec = 0;


                    dlbTotNumRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC).toString()), 6);
                    dlbCanDevCli = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEVCLI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEVCLI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEVCLI).toString()), 6);
                    dlbCanTotDevCli = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTDEVCLI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTDEVCLI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTDEVCLI).toString()), 6);
                    dlbCanDevCli_Ori = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEVCLI_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEVCLI_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEVCLI_ORI).toString()), 6);

                    dlbCanDevRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_BOD_REV_REH).toString()), 6);


                    if (dlbCanDevCli > dlbCanDevCli_Ori) {
                        if ((dlbCanTotDevCli + ((dlbCanDevCli - dlbCanDevCli_Ori)) > dlbCanDevRec)) { // dlbTotNumRec ) ){
                            MensajeInf("Error! La cantidad a devolver a sobrepasado la cantidad recibida. ");
                            tblDat.setValueAt("" + dlbCanDevCli_Ori, intCelSel, INT_TBL_CANDEVCLI);
                        }
                    }
                }
            });
            objTblCelEdiTxt = null;

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODALT).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(165);
            tcmAux.getColumn(INT_TBL_DESUNI).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CANVEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CANDEV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_REVTEC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CANTOTREC).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CANTOTNUNREC).setPreferredWidth(intAnchura);

            tcmAux.getColumn(INT_TBL_CAN_TEC_REV_ACE).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CAN_TEC_REV_REH).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CAN_BOD_REV_ACE).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CAN_BOD_REV_REH).setPreferredWidth(intAnchura);

            tcmAux.getColumn(INT_TBL_CANREC).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_OBSCANREC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CANACE).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CANRCH).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_OBSREVCONFING).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CANDEVCLI).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CANTOTDEVCLI).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_OBSDEVCLI).setPreferredWidth(80);

            tcmAux.getColumn(INT_TBL_CANSDEVPRV).setPreferredWidth(intAnchura);
            tcmAux.getColumn(INT_TBL_CANNDEVPRV).setPreferredWidth(intAnchura);

            Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
            Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_REVTEC).setCellRenderer(objTblCelRenChk);
            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_REVTEC).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if (tblDat.getValueAt(intCelSel, INT_TBL_EST_REC_TEC).toString().equals("S")) {
                        tblDat.setValueAt(true, intCelSel, INT_TBL_REVTEC);
                    }

                }
            });
            objTblCelRenChk = null;
            objTblCelEdiChk = null;
            ZafTblEdi zafTblEdi = new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);

            tcmAux = null;

            setEditable(true);
            blnRes = true;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean MensjConfir(String strMensj)
    {
        boolean blnRes = false;
        String strTit = "Mensaje del sistema Zafiro";
        if (JOptionPane.showConfirmDialog(this, strMensj, strTit, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 1) {
            blnRes = true;
        }

        return blnRes;
    }

    public void setEditable(boolean editable)
    {
        if (editable == true) {
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
        } else {
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        PanTabGen = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        lblRes = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        txtCodRes = new javax.swing.JTextField();
        txtCodDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        txtNomRes = new javax.swing.JTextField();
        butBusTipDoc = new javax.swing.JButton();
        butBusRes = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtNumDoc = new javax.swing.JTextField();
        lblNumDoc = new javax.swing.JLabel();
        panObsTanGen = new javax.swing.JPanel();
        panObs = new javax.swing.JPanel();
        panLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panTxa = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txtObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txtObs2 = new javax.swing.JTextArea();
        panTabDatSolDev = new javax.swing.JPanel();
        panDatSolDev = new javax.swing.JPanel();
        lblLoc = new javax.swing.JLabel();
        lblTipdoc = new javax.swing.JLabel();
        lblCli = new javax.swing.JLabel();
        lblSol = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        txtDesCorTipDocSol = new javax.swing.JTextField();
        txtCodCli = new javax.swing.JTextField();
        txtCodSol = new javax.swing.JTextField();
        txtDesLarTipDocSol = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        txtDesSol = new javax.swing.JTextField();
        butBusNumDoc = new javax.swing.JButton();
        lblFecDto = new javax.swing.JLabel();
        lblCodDto = new javax.swing.JLabel();
        txtFecDocSol = new javax.swing.JTextField();
        txtCodDocSol = new javax.swing.JTextField();
        txtNumDocSol = new javax.swing.JTextField();
        lblNumDto = new javax.swing.JLabel();
        chkMasInfo = new javax.swing.JCheckBox();
        panTblSolDev = new javax.swing.JPanel();
        srcTblSolDev = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        PanObsSol = new javax.swing.JPanel();
        panLbl1 = new javax.swing.JPanel();
        lblObs3 = new javax.swing.JLabel();
        lblObs4 = new javax.swing.JLabel();
        panTxa1 = new javax.swing.JPanel();
        spnObs3 = new javax.swing.JScrollPane();
        txtObs1Sol = new javax.swing.JTextArea();
        spnObs4 = new javax.swing.JScrollPane();
        txtObs2Sol = new javax.swing.JTextArea();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

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

        panTit.setPreferredSize(new java.awt.Dimension(100, 24));

        lblTit.setText("titulo");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        tabGen.setPreferredSize(new java.awt.Dimension(115, 100));

        panTabGen.setPreferredSize(new java.awt.Dimension(100, 90));
        panTabGen.setLayout(new java.awt.BorderLayout());

        PanTabGen.setPreferredSize(new java.awt.Dimension(100, 75));
        PanTabGen.setLayout(null);

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTipDoc.setText("Tipo de Documento:");
        PanTabGen.add(lblTipDoc);
        lblTipDoc.setBounds(10, 10, 120, 20);

        lblRes.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblRes.setText("Responsable:");
        PanTabGen.add(lblRes);
        lblRes.setBounds(10, 30, 110, 20);

        lblCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodDoc.setText("Código del Documento:");
        PanTabGen.add(lblCodDoc);
        lblCodDoc.setBounds(10, 50, 120, 20);

        txtCodRes.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodRes.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtCodRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodResActionPerformed(evt);
            }
        });
        txtCodRes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodResFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodResFocusLost(evt);
            }
        });
        PanTabGen.add(txtCodRes);
        txtCodRes.setBounds(140, 30, 60, 20);

        txtCodDoc.setBackground(objZafParSis.getColorCamposSistema());
        txtCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        PanTabGen.add(txtCodDoc);
        txtCodDoc.setBounds(140, 50, 90, 20);

        txtDesLarTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesLarTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        PanTabGen.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(200, 10, 230, 20);

        txtNomRes.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomRes.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtNomRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomResActionPerformed(evt);
            }
        });
        txtNomRes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomResFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomResFocusLost(evt);
            }
        });
        PanTabGen.add(txtNomRes);
        txtNomRes.setBounds(200, 30, 230, 20);

        butBusTipDoc.setText("jButton1");
        butBusTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusTipDocActionPerformed(evt);
            }
        });
        PanTabGen.add(butBusTipDoc);
        butBusTipDoc.setBounds(430, 10, 20, 20);

        butBusRes.setText("jButton2");
        butBusRes.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusResActionPerformed(evt);
            }
        });
        PanTabGen.add(butBusRes);
        butBusRes.setBounds(430, 30, 20, 20);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecDoc.setText("Fecha del Documento:");
        PanTabGen.add(lblFecDoc);
        lblFecDoc.setBounds(460, 10, 110, 20);

        txtDesCorTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesCorTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        PanTabGen.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(140, 10, 60, 20);

        txtNumDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNumDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocActionPerformed(evt);
            }
        });
        PanTabGen.add(txtNumDoc);
        txtNumDoc.setBounds(580, 30, 90, 20);

        lblNumDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNumDoc.setText("Número de Documento:");
        PanTabGen.add(lblNumDoc);
        lblNumDoc.setBounds(460, 30, 120, 20);

        panTabGen.add(PanTabGen, java.awt.BorderLayout.PAGE_START);

        panObsTanGen.setPreferredSize(new java.awt.Dimension(100, 80));
        panObsTanGen.setLayout(new java.awt.BorderLayout());

        panObs.setPreferredSize(new java.awt.Dimension(518, 20));
        panObs.setLayout(new java.awt.BorderLayout());

        panLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 12));
        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Observación 1:");
        lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs1.setPreferredSize(new java.awt.Dimension(92, 15));
        panLbl.add(lblObs1);

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 12));
        lblObs2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs2.setText("Observación 2:");
        lblObs2.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs2.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs2.setPreferredSize(new java.awt.Dimension(92, 15));
        lblObs2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        panLbl.add(lblObs2);

        panObs.add(panLbl, java.awt.BorderLayout.WEST);

        panTxa.setLayout(new java.awt.GridLayout(2, 1, 0, 1));

        txtObs1.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtObs1.setLineWrap(true);
        spnObs1.setViewportView(txtObs1);

        panTxa.add(spnObs1);

        txtObs2.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtObs2.setLineWrap(true);
        spnObs2.setViewportView(txtObs2);

        panTxa.add(spnObs2);

        panObs.add(panTxa, java.awt.BorderLayout.CENTER);

        panObsTanGen.add(panObs, java.awt.BorderLayout.CENTER);

        panTabGen.add(panObsTanGen, java.awt.BorderLayout.PAGE_END);

        tabGen.addTab("General", panTabGen);

        panTabDatSolDev.setLayout(new java.awt.BorderLayout());

        panDatSolDev.setPreferredSize(new java.awt.Dimension(100, 90));
        panDatSolDev.setLayout(null);

        lblLoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblLoc.setText("Local:");
        panDatSolDev.add(lblLoc);
        lblLoc.setBounds(10, 0, 60, 20);

        lblTipdoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTipdoc.setText("Tipo de Documento:");
        panDatSolDev.add(lblTipdoc);
        lblTipdoc.setBounds(10, 20, 100, 20);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli.setText("Cliente:");
        panDatSolDev.add(lblCli);
        lblCli.setBounds(10, 40, 60, 20);

        lblSol.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblSol.setText("Solicitante:");
        panDatSolDev.add(lblSol);
        lblSol.setBounds(10, 60, 60, 20);

        txtCodLoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        panDatSolDev.add(txtCodLoc);
        txtCodLoc.setBounds(140, 0, 70, 20);

        txtNomLoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        panDatSolDev.add(txtNomLoc);
        txtNomLoc.setBounds(210, 0, 240, 20);

        txtDesCorTipDocSol.setFont(new java.awt.Font("SansSerif", 0, 11));
        panDatSolDev.add(txtDesCorTipDocSol);
        txtDesCorTipDocSol.setBounds(140, 20, 70, 20);

        txtCodCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        panDatSolDev.add(txtCodCli);
        txtCodCli.setBounds(140, 40, 70, 20);

        txtCodSol.setFont(new java.awt.Font("SansSerif", 0, 11));
        panDatSolDev.add(txtCodSol);
        txtCodSol.setBounds(140, 60, 70, 20);

        txtDesLarTipDocSol.setFont(new java.awt.Font("SansSerif", 0, 11));
        panDatSolDev.add(txtDesLarTipDocSol);
        txtDesLarTipDocSol.setBounds(210, 20, 240, 20);

        txtNomCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        panDatSolDev.add(txtNomCli);
        txtNomCli.setBounds(210, 40, 240, 20);

        txtDesSol.setFont(new java.awt.Font("SansSerif", 0, 11));
        panDatSolDev.add(txtDesSol);
        txtDesSol.setBounds(210, 60, 240, 20);

        butBusNumDoc.setText("jButton1");
        butBusNumDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusNumDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusNumDocActionPerformed(evt);
            }
        });
        panDatSolDev.add(butBusNumDoc);
        butBusNumDoc.setBounds(660, 20, 20, 20);

        lblFecDto.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecDto.setText("Fecha del Documento:");
        panDatSolDev.add(lblFecDto);
        lblFecDto.setBounds(460, 0, 120, 20);

        lblCodDto.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodDto.setText("Código del Documento:");
        panDatSolDev.add(lblCodDto);
        lblCodDto.setBounds(460, 40, 120, 20);
        panDatSolDev.add(txtFecDocSol);
        txtFecDocSol.setBounds(580, 0, 80, 20);
        panDatSolDev.add(txtCodDocSol);
        txtCodDocSol.setBounds(580, 40, 80, 20);

        txtNumDocSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocSolActionPerformed(evt);
            }
        });
        txtNumDocSol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumDocSolKeyPressed(evt);
            }
        });
        panDatSolDev.add(txtNumDocSol);
        txtNumDocSol.setBounds(580, 20, 80, 20);

        lblNumDto.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNumDto.setText("Número de Documento:");
        panDatSolDev.add(lblNumDto);
        lblNumDto.setBounds(460, 20, 112, 20);

        chkMasInfo.setFont(new java.awt.Font("SansSerif", 0, 11));
        chkMasInfo.setText("Mostrar más Información"); // NOI18N
        chkMasInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMasInfoActionPerformed(evt);
            }
        });
        panDatSolDev.add(chkMasInfo);
        chkMasInfo.setBounds(460, 65, 180, 20);

        panTabDatSolDev.add(panDatSolDev, java.awt.BorderLayout.NORTH);

        panTblSolDev.setPreferredSize(new java.awt.Dimension(100, 120));
        panTblSolDev.setLayout(new java.awt.BorderLayout());

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
        srcTblSolDev.setViewportView(tblDat);

        panTblSolDev.add(srcTblSolDev, java.awt.BorderLayout.CENTER);

        panTabDatSolDev.add(panTblSolDev, java.awt.BorderLayout.CENTER);

        PanObsSol.setPreferredSize(new java.awt.Dimension(100, 60));
        PanObsSol.setLayout(new java.awt.BorderLayout());

        panLbl1.setLayout(new java.awt.GridLayout(2, 1));

        lblObs3.setFont(new java.awt.Font("SansSerif", 0, 12));
        lblObs3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs3.setText("Observación 1:");
        lblObs3.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs3.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs3.setPreferredSize(new java.awt.Dimension(92, 15));
        panLbl1.add(lblObs3);

        lblObs4.setFont(new java.awt.Font("SansSerif", 0, 12));
        lblObs4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs4.setText("Observación 2:");
        lblObs4.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs4.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs4.setPreferredSize(new java.awt.Dimension(92, 15));
        lblObs4.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        panLbl1.add(lblObs4);

        PanObsSol.add(panLbl1, java.awt.BorderLayout.WEST);

        panTxa1.setLayout(new java.awt.GridLayout(2, 1, 0, 1));

        txtObs1Sol.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtObs1Sol.setLineWrap(true);
        spnObs3.setViewportView(txtObs1Sol);

        panTxa1.add(spnObs3);

        txtObs2Sol.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtObs2Sol.setLineWrap(true);
        spnObs4.setViewportView(txtObs2Sol);

        panTxa1.add(spnObs4);

        PanObsSol.add(panTxa1, java.awt.BorderLayout.CENTER);

        panTabDatSolDev.add(PanObsSol, java.awt.BorderLayout.SOUTH);

        tabGen.addTab("Datos de la Solicitud de Devolución", panTabDatSolDev);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butBusTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusTipDocActionPerformed
        objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
        objVenConTipdoc.setCampoBusqueda(1);
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
            strCodTipDoc = objVenConTipdoc.getValueAt(1);
            strMerIngEgr = objVenConTipdoc.getValueAt(4);
            strTipIngEgr = objVenConTipdoc.getValueAt(5);
        }
    }//GEN-LAST:event_butBusTipDocActionPerformed

    private void butBusResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusResActionPerformed
        objVenConVen.setTitle("Listado de Clientes");
        objVenConVen.setCampoBusqueda(1);
        objVenConVen.show();
        if (objVenConVen.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
            txtCodRes.setText(objVenConVen.getValueAt(1));
            txtNomRes.setText(objVenConVen.getValueAt(2));
        }
    }//GEN-LAST:event_butBusResActionPerformed

    private void txtCodResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodResActionPerformed
        txtCodRes.transferFocus();
    }//GEN-LAST:event_txtCodResActionPerformed

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtNomResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomResActionPerformed
        txtNomRes.transferFocus();
    }//GEN-LAST:event_txtNomResActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc = txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc = txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtCodResFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodResFocusGained
        strCodRes = txtCodRes.getText();
        txtCodRes.selectAll();
    }//GEN-LAST:event_txtCodResFocusGained

    private void txtNomResFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomResFocusGained
        strNomRes = txtNomRes.getText();
        txtNomRes.selectAll();
    }//GEN-LAST:event_txtNomResFocusGained

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                BuscarTipDoc("a.tx_descor", txtDesCorTipDoc.getText(), 1);
            }
        } else {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                BuscarTipDoc("a.tx_deslar", txtDesLarTipDoc.getText(), 2);
            }
        } else {
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtCodResFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodResFocusLost
        if (!txtCodRes.getText().equalsIgnoreCase(strCodRes)) {
            if (txtCodRes.getText().equals("")) {
                txtCodRes.setText("");
                txtNomRes.setText("");
            } else {
                BuscarVendedor("a.co_usr", txtCodRes.getText(), 0);
            }
        } else {
            txtCodRes.setText(strCodRes);
        }
    }//GEN-LAST:event_txtCodResFocusLost

    private void txtNomResFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomResFocusLost
        if (!txtNomRes.getText().equalsIgnoreCase(strNomRes)) {
            if (txtNomRes.getText().equals("")) {
                txtCodRes.setText("");
                txtNomRes.setText("");
            } else {
                BuscarVendedor("a.tx_nom", txtNomRes.getText(), 1);
            }
        } else {
            txtNomRes.setText(strNomRes);
        }
    }//GEN-LAST:event_txtNomResFocusLost

    private void butBusNumDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusNumDocActionPerformed
        cargarVenBusSol();
    }//GEN-LAST:event_butBusNumDocActionPerformed

    private void txtNumDocSolKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumDocSolKeyPressed
        if (java.awt.event.KeyEvent.VK_ENTER == evt.getKeyCode()) {
            if (!cargarSol(txtNumDocSol.getText())) {
                cargarVenBusSol();
            }
        }
    }//GEN-LAST:event_txtNumDocSolKeyPressed

    private void txtNumDocSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocSolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumDocSolActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        //JOptionPane oppMsg=JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (JOptionPane.showConfirmDialog(this, strMsg, strTit, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {

            if (rstCab != null) {
                rstCab = null;
            }
            if (STM_GLO != null) {
                STM_GLO = null;
            }

            System.gc();
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void chkMasInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMasInfoActionPerformed
        masInformacion(chkMasInfo.isSelected());
    }//GEN-LAST:event_chkMasInfoActionPerformed

    private void txtNumDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumDocActionPerformed

    private void masInformacion(boolean blnMostrar) {
        if (blnMostrar) {
            MostrarCol(INT_TBL_CANTOTREC);

        } else {
            ocultaCol(INT_TBL_CANTOTREC);
        }

    }

    private void MostrarCol(int intCol) 
    {
        tblDat.getColumnModel().getColumn(intCol).setWidth(80);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(80);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(80);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(80);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);
    }

    private void cargarVenBusSol() 
    {
        Ventas.ZafVen27.ZafVen27_01 obj = new Ventas.ZafVen27.ZafVen27_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
        obj.show();
        if (obj.acepta()) {
            txtNumDocSol.setText(obj.GetCamSel(4));
            if (!cargarSol(txtNumDocSol.getText())) {
                cargarVenBusSol();
            }
        }
        obj.dispose();
        obj = null;
    }

    private void bloquea(javax.swing.JTextField txtFiel, boolean blnEst)
    {
        java.awt.Color colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }

    private boolean cargarSol(String strNumDoc)
    {
        boolean blnRes = false;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try 
        {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null) {
                stmLoc = conn.createStatement();
                if (objZafParSis.getCodigoMenu() == 1888) 
                {
                    strSql = "SELECT * from ( "
                            + " SELECT  "
                            + " ( select  sum(x1.nd_cansolnodevprv) as  cansolstk   from tbm_cabsoldevven as x "
                            + " inner join tbm_detsoldevven as x1 on (x1.co_emp=x.co_emp and x1.co_loc=x.co_loc and x1.co_tipdoc=x.co_tipdoc and x1.co_doc=x.co_doc )  "
                            + " where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc   "
                            + " ) as   cansolstk,"                            
                            + " (SELECT "
                            /*CAMBIO POR PROYECTO TRANSFERENCIAS*/
                            //+ "sum((y2.nd_can - (y1.nd_candev - y1.nd_canvolfac) - y2.nd_cancon)) as nd_canconguirem "
                            //+ " case when y2.co_tipdoc=102 then "
                            //+ "	     sum((y2.nd_can - (y1.nd_candev - y1.nd_canvolfac) - y2.nd_cancon)) "
                            //+ " else"
                            + "     sum((dd.nd_can - (y1.nd_candev - y1.nd_canvolfac) - dd.nd_cancon)) " 	
                            //+ " end "
                            + " as nd_canconguirem "
                            /*CAMBIO POR PROYECTO TRANSFERENCIAS*/
                            + " FROM tbm_detsoldevven as y1 "
                            + " INNER JOIN tbm_detguirem as y2 on (y2.co_emprel=y1.co_emp  and y2.co_locrel=y1.co_locrel and y2.co_tipdocrel=y1.co_tipdocrel and y2.co_docrel=y1.co_docrel and y2.co_regrel=y1.co_regrel ) "
                            /*CAMBIO POR PROYECTO TRANSFERENCIAS*/
                            + " inner join tbm_detmovinv as dd"
                            + " on dd.co_emp=y2.co_emprel and dd.co_loc=y2.co_locrel and dd.co_tipdoc=y2.co_tipdocrel and dd.co_doc=y2.co_docrel and dd.co_reg=y2.co_regrel"                            
                            /*CAMBIO POR PROYECTO TRANSFERENCIAS*/                                                        
                            + " WHERE y1.co_emp=a.co_emp and y1.co_loc=a.co_loc and y1.co_tipdoc=a.co_tipdoc and y1.co_doc=a.co_doc and y1.nd_candev > 0 and y2.st_meregrfisbod = 'S' group by y2.co_tipdoc ) as nd_canconguirem, "
                            + " a.st_impguiremaut, a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a1.tx_nom as tx_desloc ,a.tx_obs1, a.tx_obs2,  "
                            + " a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli,a.co_usrsol, a.tx_nomusrsol , a.fe_doc,  a.ne_numdoc, a.tx_obs1, a.tx_obs2 "
                            + " FROM tbm_cabsoldevven AS a "
                            + " INNER JOIN tbm_loc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel) "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                            + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " AND a.co_tipdoc IN ("
                            + " SELECT b.co_tipdoc FROM tbr_tipdocdetprg as b WHERE b.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND "
                            + " b.co_loc = " + objZafParSis.getCodigoLocal() + " AND  b.co_mnu =" + objZafParSis.getCodigoMenu() + " ) "
                            + "   AND a.ne_numdoc=" + strNumDoc + " AND a.st_reg ='A' AND a.st_aut ='A' and a.st_recMerDev='N' AND a.st_tipdev='C' "
                            + " ) as x  where CASE WHEN cansolstk = 0 THEN ((st_impguiremaut = 'S') or (st_impguiremaut = 'N' and nd_canconguirem < 0)) ELSE cansolstk > 0 END ";
                }
                if (objZafParSis.getCodigoMenu() == 1898) 
                {
                    strSql = "SELECT  a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a1.tx_nom as tx_desloc ,a.tx_obs1, a.tx_obs2,  "
                            + " a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli,a.co_usrsol, a.tx_nomusrsol , a.fe_doc,  a.ne_numdoc, a.tx_obs1, a.tx_obs2 "
                            + " FROM tbm_cabsoldevven AS a "
                            + " INNER JOIN  tbm_detsoldevven AS d ON(d.co_emp=a.co_emp AND d.co_loc=a.co_loc AND d.co_tipdoc=a.co_tipdoc AND d.co_doc=a.co_doc ) "
                            + " INNER JOIN tbm_loc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel) "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                            + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " AND a.co_tipdoc IN ("
                            + " SELECT b.co_tipdoc FROM tbr_tipdocdetprg as b WHERE b.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND "
                            + " b.co_loc = " + objZafParSis.getCodigoLocal() + " AND  b.co_mnu =" + objZafParSis.getCodigoMenu() + " ) "
                            + " AND a.ne_numdoc=" + strNumDoc + " AND a.st_reg = 'A' AND a.st_aut ='A' AND a.st_revTecMerDev='N'  AND a.st_tipdev='C'  AND a.st_revtec='S' AND d.st_revtec='S' AND d.nd_canrec > 0  "
                            + " GROUP BY a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a1.tx_nom ,a.tx_obs1, a.tx_obs2,  "
                            + " a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli,a.co_usrsol, a.tx_nomusrsol , a.fe_doc,  a.ne_numdoc ";
                }
                if (objZafParSis.getCodigoMenu() == 1908) 
                {
                    strSql = "SELECT  a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a1.tx_nom as tx_desloc ,a.tx_obs1, a.tx_obs2,  "
                            + " a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli,a.co_usrsol, a.tx_nomusrsol , a.fe_doc,  a.ne_numdoc, a.tx_obs1, a.tx_obs2 "
                            + " FROM tbm_cabsoldevven AS a "
                            + " INNER JOIN  tbm_detsoldevven AS d ON(d.co_emp=a.co_emp AND d.co_loc=a.co_loc AND d.co_tipdoc=a.co_tipdoc AND d.co_doc=a.co_doc ) "
                            + " INNER JOIN tbm_loc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel) "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                            + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " AND a.co_tipdoc IN ("
                            + " SELECT b.co_tipdoc FROM tbr_tipdocdetprg as b WHERE b.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND "
                            + " b.co_loc = " + objZafParSis.getCodigoLocal() + " AND  b.co_mnu =" + objZafParSis.getCodigoMenu() + " ) "
                            + " AND a.ne_numdoc=" + strNumDoc + " "
                            + " AND a.st_reg ='A'  AND a.st_aut ='A' AND a.st_RevBodMerDev='N' "
                            + " AND a.st_tipdev='C' AND ( ( d.st_revtec='N' AND d.nd_canrec > 0 )  "
                            + " OR  ( d.st_revtec='S' AND d.nd_canrevtecace > 0 )  "
                            + " OR  ( d.st_revtec='S' AND d.nd_canrevtecrec > 0 )  )"
                            + " GROUP BY a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a1.tx_nom ,a.tx_obs1, a.tx_obs2,  "
                            + " a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli,a.co_usrsol, a.tx_nomusrsol , a.fe_doc,  a.ne_numdoc ";
                }

                if (objZafParSis.getCodigoMenu() == 1928) 
                {
                    strSql = "SELECT  a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a1.tx_nom as tx_desloc ,a.tx_obs1, a.tx_obs2,  "
                            + " a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli,a.co_usrsol, a.tx_nomusrsol , a.fe_doc,  a.ne_numdoc, a.tx_obs1, a.tx_obs2 "
                            + " FROM tbm_cabsoldevven AS a "
                            + " INNER JOIN tbm_loc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel) "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                            + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " AND a.co_tipdoc IN ("
                            + " SELECT b.co_tipdoc FROM tbr_tipdocdetprg as b WHERE b.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND "
                            + " b.co_loc = " + objZafParSis.getCodigoLocal() + " AND  b.co_mnu =" + objZafParSis.getCodigoMenu() + " ) "
                            + "   AND a.ne_numdoc=" + strNumDoc + " AND a.st_reg ='A' AND a.st_aut ='A' AND a.st_tipdev='C' "
                            + " AND a.st_exiMerRec='S' AND a.st_MerRecDevCli='N' AND a.st_revBodMerDev='S' ";
                }
                System.out.println("ZafVen27.cargarSol: " + strSql);

                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    if (cargarSolCab(conn, rstLoc)) {
                        if (cargarSolDet(conn, rstLoc)) {
                            bloquea(txtNumDocSol, false);
                            blnRes = true;
                        }
                    }
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    private boolean cargarSolCab(java.sql.Connection conn, java.sql.ResultSet rstDat) 
    {
        boolean blnRes = false;
        try
        {
            if (conn != null) {
                txtCodLoc.setText(rstDat.getString("co_loc"));
                txtNomLoc.setText(rstDat.getString("tx_desloc"));
                txtCodTipDocSol.setText(rstDat.getString("co_tipdoc"));
                txtDesCorTipDocSol.setText(rstDat.getString("tx_descor"));
                txtDesLarTipDocSol.setText(rstDat.getString("tx_deslar"));
                txtCodCli.setText(rstDat.getString("co_cli"));
                txtNomCli.setText(rstDat.getString("tx_nomcli"));
                txtCodSol.setText(rstDat.getString("co_usrsol"));
                txtDesSol.setText(rstDat.getString("tx_nomusrsol"));
                txtFecDocSol.setText(rstDat.getString("fe_doc"));
                txtCodDocSol.setText(rstDat.getString("co_doc"));
                txtObs1Sol.setText(rstDat.getString("tx_obs1"));
                txtObs2Sol.setText(rstDat.getString("tx_obs2"));

                blnRes = true;
            }
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    private boolean cargarSolDet(java.sql.Connection conn, java.sql.ResultSet rstDat) 
    {
        boolean blnRes = false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        Vector vecData;
        try 
        {
            if (conn != null) {
                stmLoc = conn.createStatement();

                String strAux2 = " , CASE WHEN ( (trim(SUBSTR (UPPER(a.tx_codalt), length(a.tx_codalt) ,1)) IN ( "
                        + " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a "
                        + " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) "
                        + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " and a.co_loc=" + objZafParSis.getCodigoLocal() + " and a1.st_reg='A' and   a.st_reg='P' ))) "
                        + " THEN 'S' ELSE 'N' END AS proconf  ";

                vecData = new Vector();
                System.out.println("menu "+objZafParSis.getCodigoMenu());
                if (objZafParSis.getCodigoMenu() == 1888) 
                {
                    strSql = "SELECT case when  proconf in ('N') then nd_cansolnodevprv  else  nd_candev_01 end as nd_candev ,*  FROM (   "
                            + " SELECT case when (a.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a.nd_canvolfac ) ) > 0 then   "
                            //          + " (a.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a.nd_canvolfac ) )  else 0 end as nd_candev_01,  "
                            //+ " (a.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a.nd_canvolfac ) )  else (case when a3.nd_cannunrec is null then 0 else a.nd_candev - a.nd_canvolfac end) end as nd_candev_01,  "
                            + " (a.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a.nd_canvolfac ) )  else (case when a3.nd_cannunrec is null then 0 else a.nd_candev - abs(a3.nd_cannunrec) - a.nd_canvolfac end) end as nd_candev_01,  "
                            + " a.nd_canvolfac, a.nd_cansolnodevprv,  a.nd_cannodevprv, a.nd_candevprv, a.st_devFalStkAce,    "
                            + " a.st_devFalStk, a.nd_canRec as nd_cantotRec, a.nd_canNunRec,  a.co_reg,  a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed, a.co_bod ,a1.tx_nom , a.nd_can, a.st_revtec    "
                            + " ,a.nd_canRevTecAce, a.nd_canRevTecRec, a.nd_canRevBodAce, a.nd_canRevBodRec , a.nd_canRecDevCli     "
                            + " ,a2.st_meringegrfisbod as proconf   "
                            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                            + " ,INV.TX_CODALT2 "
                            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                            + " FROM tbm_detsoldevven AS a  "
                            + " INNER JOIN tbm_detmovinv as a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_locrel and a2.co_tipdoc=a.co_tipdocrel and a2.co_doc=a.co_docrel and a2.co_reg=a.co_regrel )    "
                            + " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod)    "
                            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                            + " INNER JOIN TBM_INV AS INV ON(INV.CO_EMP=A.CO_EMP  AND INV.CO_ITM=A.CO_ITM)"
                            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                            
                            //+ " LEFT JOIN  tbm_detingegrmerbod AS a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_locrelcon and a3.co_tipdoc=a.co_tipdocrelcon and a3.co_doc=a.co_docrelcon and a3.co_reg=a.co_regrelcon )  "
                            + " LEFT JOIN ("
                            + " tbm_detingegrmerbod as a3 "
                            + " INNER JOIN tbm_Cabingegrmerbod as cabing"
                            + "  on (a3.co_emp=cabing.co_emp "
                            + " and a3.co_loc=cabing.co_loc "
                            + " and a3.co_tipdoc=cabing.co_tipdoc "
                            + " and a3.co_doc=cabing.co_doc "
                            + " and cabing.st_reg='A'))"
                            + " on (a3.co_emp=a.co_emp and a3.co_loc=a.co_locrelcon and a3.co_tipdoc=a.co_tipdocrelcon and a3.co_doc=a.co_docrelcon and a3.co_reg=a.co_regrelcon )  "
                            + " WHERE a.co_emp=" + rstDat.getString("co_emp") + " AND  a.co_loc=" + rstDat.getString("co_loc") + "  AND a.co_tipdoc=" + rstDat.getString("co_tipdoc") + " AND a.co_doc=" + rstDat.getString("co_doc") + "   "
                            + " ) AS x "
                            + " order by co_reg ";  // el mismo 1
                }

                if (objZafParSis.getCodigoMenu() == 1898) 
                {
                    strSql = "SELECT (a.nd_candev - a.nd_canvolfac) as nd_candev, a.nd_cannodevprv, a.nd_candevprv, a.st_devFalStkAce,  a.st_devFalStk, a.nd_canRec as nd_cantotRec, a.nd_canNunRec,  a.co_reg,  a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed, a.co_bod ,a1.tx_nom , a.nd_can,  a.st_revtec "
                            + " ,a.nd_canRevTecAce, a.nd_canRevTecRec, a.nd_canRevBodAce, a.nd_canRevBodRec , a.nd_canRecDevCli "
                            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                            + " ,INV.TX_CODALT2 "
                            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                            
                            + strAux2 + " FROM tbm_detsoldevven AS a "
                            + " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) "

                            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                            + " INNER JOIN TBM_INV AS INV ON(INV.CO_EMP=A.CO_EMP  AND INV.CO_ITM=A.CO_ITM)"
                            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                            
                            
                            + " WHERE a.co_emp=" + rstDat.getString("co_emp")
                            + " AND  a.co_loc=" + rstDat.getString("co_loc")
                            + "  AND a.co_tipdoc=" + rstDat.getString("co_tipdoc")
                            + " AND a.co_doc=" + rstDat.getString("co_doc")
                            + " AND a.st_revtec='S' "
                            + " order by a.co_reg  ";
                }

                if (objZafParSis.getCodigoMenu() == 1908) 
                {
                        // strSql="SELECT case when  proconf in ('S') then nd_cansolnodevprv  else  nd_candev_01 end as nd_candev,  *  FROM ( " +
                        //        "SELECT (a.nd_candev - a.nd_canvolfac) as nd_candev_01, a.nd_cansolnodevprv,  a.nd_cannodevprv, a.nd_candevprv,  a.st_devFalStkAce,   a.st_devFalStk, a.nd_canRec as nd_cantotRec, a.nd_canNunRec,  a.co_reg,  a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed, a.co_bod ,a1.tx_nom , a.nd_can, a.st_revtec " +
                        //        " ,a.nd_canRevTecAce, a.nd_canRevTecRec, a.nd_canRevBodAce, a.nd_canRevBodRec , a.nd_canRecDevCli "+
                        //  strAux2+" FROM tbm_detsoldevven AS a " +
                        //          " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) " +
                        //          " WHERE a.co_emp="+rstDat.getString("co_emp")+" AND  a.co_loc="+rstDat.getString("co_loc")+"  AND a.co_tipdoc="+rstDat.getString("co_tipdoc")+" AND a.co_doc="+rstDat.getString("co_doc")+" " +
                        //          " ) AS x ";

                    strSql = "SELECT case when  proconf in ('N') then nd_cansolnodevprv  else  nd_candev_01 end as nd_candev ,*  FROM (   "
                            + " SELECT case when (a.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a.nd_canvolfac ) ) > 0 then   "
                            //          + " (a.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a.nd_canvolfac ) )  else 0 end as nd_candev_01,  "
                            + " (a.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a.nd_canvolfac ) )  else (case when a3.nd_cannunrec is null then 0 else a.nd_candev - a.nd_canvolfac end) end as nd_candev_01,  "
                            + " a.nd_canvolfac, a.nd_cansolnodevprv,  a.nd_cannodevprv, a.nd_candevprv, a.st_devFalStkAce,    "
                            + " a.st_devFalStk, a.nd_canRec as nd_cantotRec, a.nd_canNunRec,  a.co_reg,  a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed, a.co_bod ,a1.tx_nom , a.nd_can, a.st_revtec    "
                            + " ,a.nd_canRevTecAce, a.nd_canRevTecRec, a.nd_canRevBodAce, a.nd_canRevBodRec , a.nd_canRecDevCli     "
                            + " ,a2.st_meringegrfisbod as proconf   "
                            
                            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                            + " ,INV.TX_CODALT2 "
                            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                            
                            
                            + " FROM tbm_detsoldevven AS a  "
                            + " INNER JOIN tbm_detmovinv as a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_locrel and a2.co_tipdoc=a.co_tipdocrel and a2.co_doc=a.co_docrel and a2.co_reg=a.co_regrel )    "
                            + " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod)    "

                            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                            + " INNER JOIN TBM_INV AS INV ON(INV.CO_EMP=A.CO_EMP  AND INV.CO_ITM=A.CO_ITM)"
                            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                            

                            
                            + " LEFT JOIN  tbm_detingegrmerbod AS a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_locrelcon and a3.co_tipdoc=a.co_tipdocrelcon and a3.co_doc=a.co_docrelcon and a3.co_reg=a.co_regrelcon )  "
                            + " WHERE a.co_emp=" + rstDat.getString("co_emp") + " AND  a.co_loc=" + rstDat.getString("co_loc") + "  AND a.co_tipdoc=" + rstDat.getString("co_tipdoc") + " AND a.co_doc=" + rstDat.getString("co_doc") + "   "
                            + " ) AS x "
                            + " order by co_reg ";  // el mismo 1
                }

                if (objZafParSis.getCodigoMenu() == 1928) 
                {
                    strSql = "SELECT (a.nd_candev - a.nd_canvolfac) as nd_candev, a.nd_cannodevprv, a.nd_candevprv,  a.st_devFalStkAce,  a.st_devFalStk, a.nd_canRec as nd_cantotRec, a.nd_canNunRec, "
                            + "  a.co_reg,  a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed, a.co_bod ,a1.tx_nom , a.nd_can, a.st_revtec "
                            + " ,a.nd_canRevTecAce, a.nd_canRevTecRec, a.nd_canRevBodAce, a.nd_canRevBodRec , a.nd_canRecDevCli "

                            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                            + " ,INV.TX_CODALT2 "
                            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                            
                            
                            + strAux2 + " FROM tbm_detsoldevven AS a "
                            + " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) "
                            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                            + " INNER JOIN TBM_INV AS INV ON(INV.CO_EMP=A.CO_EMP  AND INV.CO_ITM=A.CO_ITM)"
                            /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/                            
                            
                            + " WHERE a.co_emp=" + rstDat.getString("co_emp")
                            + " AND  a.co_loc=" + rstDat.getString("co_loc")
                            + " AND a.co_tipdoc=" + rstDat.getString("co_tipdoc")
                            + " AND a.co_doc=" + rstDat.getString("co_doc")
                            + " order by a.co_reg ";
                }
                System.out.println("ZafVen27.cargarSolDet: " + strSql);

                rstLoc = stmLoc.executeQuery(strSql);
                while (rstLoc.next())
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
                    vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));  
					/*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/ 
                    vecReg.add(INT_TBL_COD3LET, rstLoc.getString("TX_CODALT2"));
					/*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/                    
                    vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                    vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                    vecReg.add(INT_TBL_NOMBOD, rstLoc.getString("tx_nom"));
                    vecReg.add(INT_TBL_CANVEN, rstLoc.getString("nd_can"));
                    vecReg.add(INT_TBL_CANDEV, rstLoc.getString("nd_candev"));

                    vecReg.add(INT_TBL_FALSTKFIC, ((rstLoc.getString("st_devFalStk").equals("S")) ? true : false));

                    vecReg.add(INT_TBL_REVTEC, ((rstLoc.getString("st_revtec").equals("S")) ? true : false));
                    vecReg.add(INT_TBL_CANTOTREC, rstLoc.getString("nd_cantotRec"));
                    vecReg.add(INT_TBL_CANTOTNUNREC, rstLoc.getString("nd_canNunRec"));

                    vecReg.add(INT_TBL_CAN_TEC_REV_ACE, rstLoc.getString("nd_canRevTecAce"));
                    vecReg.add(INT_TBL_CAN_TEC_REV_REH, rstLoc.getString("nd_canRevTecRec"));
                    vecReg.add(INT_TBL_CAN_BOD_REV_ACE, rstLoc.getString("nd_canRevBodAce"));
                    vecReg.add(INT_TBL_CAN_BOD_REV_REH, rstLoc.getString("nd_canRevBodRec"));

                    vecReg.add(INT_TBL_CANREC, "");
                    vecReg.add(INT_TBL_OBSCANREC, "");
                    vecReg.add(INT_TBL_CANACE, "");
                    vecReg.add(INT_TBL_CANRCH, "");
                    vecReg.add(INT_TBL_OBSREVCONFING, "");
                    vecReg.add(INT_TBL_EST_REC_TEC, rstLoc.getString("st_revtec"));
                    vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                    vecReg.add(INT_TBL_CANTOTNUNREC_ORI, rstLoc.getString("nd_canNunRec"));
                    vecReg.add(INT_TBL_CANREC_ORI, "");
                    vecReg.add(INT_TBL_CANACE_ORI, "");
                    vecReg.add(INT_TBL_CANRCH_ORI, "");
                    vecReg.add(INT_TBL_OBSREVTEC, "");
                    vecReg.add(INT_TBL_CANTOTDEVCLI, rstLoc.getString("nd_canRecDevCli"));
                    vecReg.add(INT_TBL_CANDEVCLI_ORI, "");
                    double dblCanDevCli = rstLoc.getDouble("nd_canRevBodRec") - rstLoc.getDouble("nd_canRecDevCli");
                    vecReg.add(INT_TBL_CANDEVCLI, "" + dblCanDevCli);
                    vecReg.add(INT_TBL_OBSDEVCLI, "");
                    vecReg.add(INT_TBL_FALSTKFICACE, ((rstLoc.getString("st_devFalStkAce").equals("S")) ? true : false));
                    vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("proconf"));

                    vecReg.add(INT_TBL_CANSDEVPRV, rstLoc.getString("nd_candevprv"));
                    vecReg.add(INT_TBL_CANNDEVPRV, rstLoc.getString("nd_cannodevprv"));

                    vecData.add(vecReg);
                }
                objTblMod.setData(vecData);
                tblDat.setModel(objTblMod);
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                blnRes = true;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    public void BuscarTipDoc(String campo, String strBusqueda, int tipo) 
    {
        objVenConTipdoc.setTitle("Listado de Vendedores");
        if (objVenConTipdoc.buscar(campo, strBusqueda)) {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
            strCodTipDoc = objVenConTipdoc.getValueAt(1);
            strMerIngEgr = objVenConTipdoc.getValueAt(4);
            strTipIngEgr = objVenConTipdoc.getValueAt(5);

        } else {
            objVenConTipdoc.setCampoBusqueda(tipo);
            objVenConTipdoc.cargarDatos();
            objVenConTipdoc.show();
            if (objVenConTipdoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
                txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
                txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
                strCodTipDoc = objVenConTipdoc.getValueAt(1);
                strMerIngEgr = objVenConTipdoc.getValueAt(4);
                strTipIngEgr = objVenConTipdoc.getValueAt(5);
            } else {
                txtCodTipDoc.setText(strCodTipDoc);
                txtDesCorTipDoc.setText(strDesCorTipDoc);
                txtDesLarTipDoc.setText(strDesLarTipDoc);
            }
        }
    }

    public void BuscarVendedor(String campo, String strBusqueda, int tipo)
    {
        objVenConVen.setTitle("Listado de Vendedores");
        if (objVenConVen.buscar(campo, strBusqueda)) {
            txtCodRes.setText(objVenConVen.getValueAt(1));
            txtNomRes.setText(objVenConVen.getValueAt(2));
        } else {
            objVenConVen.setCampoBusqueda(tipo);
            objVenConVen.cargarDatos();
            objVenConVen.show();
            if (objVenConVen.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                txtCodRes.setText(objVenConVen.getValueAt(1));
                txtNomRes.setText(objVenConVen.getValueAt(2));
            } else {
                txtCodRes.setText(strCodRes);
                txtNomRes.setText(strNomRes);
            }
        }
    }

    private void cargarResponsable() 
    {
        String strSql = "";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try 
        {
            abrirCon();
            if (CONN_GLO != null) {
                stmLoc = CONN_GLO.createStatement();

                strSql = "SELECT co_usr, tx_usr FROM tbm_usr WHERE co_usr=" + objZafParSis.getCodigoUsuario();
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    txtCodRes.setText(rstLoc.getString("co_usr"));
                    txtNomRes.setText(rstLoc.getString("tx_usr"));
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                CerrarCon();
            }
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanObsSol;
    private javax.swing.JPanel PanTabGen;
    private javax.swing.JButton butBusNumDoc;
    private javax.swing.JButton butBusRes;
    private javax.swing.JButton butBusTipDoc;
    private javax.swing.JCheckBox chkMasInfo;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblCodDto;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecDto;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblNumDto;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblObs3;
    private javax.swing.JLabel lblObs4;
    private javax.swing.JLabel lblRes;
    private javax.swing.JLabel lblSol;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTipdoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panDatSolDev;
    private javax.swing.JPanel panLbl;
    private javax.swing.JPanel panLbl1;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panObsTanGen;
    private javax.swing.JPanel panTabDatSolDev;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTblSolDev;
    private javax.swing.JPanel panTit;
    private javax.swing.JPanel panTxa;
    private javax.swing.JPanel panTxa1;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JScrollPane spnObs3;
    private javax.swing.JScrollPane spnObs4;
    private javax.swing.JScrollPane srcTblSolDev;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodDocSol;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodRes;
    private javax.swing.JTextField txtCodSol;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesCorTipDocSol;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDesLarTipDocSol;
    private javax.swing.JTextField txtDesSol;
    private javax.swing.JTextField txtFecDocSol;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomLoc;
    private javax.swing.JTextField txtNomRes;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtNumDocSol;
    private javax.swing.JTextArea txtObs1;
    private javax.swing.JTextArea txtObs1Sol;
    private javax.swing.JTextArea txtObs2;
    private javax.swing.JTextArea txtObs2Sol;
    // End of variables declaration//GEN-END:variables

    private void MensajeInf(String strMensaje) 
    {
        //JOptionPane obj =new JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this, strMensaje, strTit, JOptionPane.INFORMATION_MESSAGE);
    }

    public class mitoolbar extends ZafToolBar {

        public mitoolbar(javax.swing.JInternalFrame jfrThis) {
            super(jfrThis, objZafParSis);
        }

        @Override
        public boolean anular() {
            boolean blnRes = false;
            int intCodDoc = 0;
            java.sql.Connection conn;
            try {
                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) {
                    conn.setAutoCommit(false);

                    if (!abrirConRem()) {
                        return false;
                    }

                    strAux = objTooBar.getEstadoRegistro();
                    if (strAux.equals("Eliminado")) {
                        MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                        blnRes = true;
                    }
                    if (strAux.equals("Anulado")) {
                        MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                        blnRes = true;
                    }

                    if (!verificaAnuEli(conn)) {
                        MensajeInf("El documento ya tiene Nota de Credito.\nNo es posible anular el documento.");
                        blnRes = true;
                    }

                    if (objZafParSis.getCodigoMenu() == 1888) {
                        if (!verificaAnuEliDoc(conn)) {
                            MensajeInf("NO SE PUEDE ANULAR PORQUE HAY UNA REVICIÓN TECNICA. ");
                            blnRes = true;
                        }
                    }

                    if (!blnRes) {

                        intCodDoc = Integer.parseInt(txtCodDoc.getText());

                        if (anularReg(conn)) {
                            if (conRemGlo != null) {
                                conRemGlo.commit();
                            }
                            conn.commit();
                            cargarDetIns(conn, intCodDoc);
                            blnRes = true;
                            objTooBar.setEstadoRegistro("Anulado");
                            blnHayCam = false;
                        } else {
                            conn.rollback();
                        }
                    } else {
                        blnRes = false;
                    }

                    if (conRemGlo != null) {
                        conRemGlo.close();
                        conRemGlo = null;
                    }
                    conn.close();
                    conn = null;
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean verificaAnuEli(java.sql.Connection conn)
        {
            boolean blnRes = true;
            String strSql = "";
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            try 
            {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "SELECT * FROM(  SELECT sum(nd_canAceIngSis) as valor FROM tbm_detsoldevven AS a "
                            + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                            + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText() + " ) as x where valor > 0 ";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        blnRes = false;
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean verificaAnuEliDoc(java.sql.Connection conn) 
        {
            boolean blnRes = true;
            String strSql = "";
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            try 
            {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "SELECT * FROM( SELECT (sum(nd_canrevtecace) + sum(nd_canrevtecrec)) as valor FROM tbm_detsoldevven "
                            + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                            + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText() + " ) as x where valor > 0 ";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        blnRes = false;
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean anularReg(java.sql.Connection conn)
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.Statement stm_remota;
            String strSql = "", strSqlFic = "";
            String strRecMerDev = "N";
            String strRecTecMerDev = "N";
            String stMerRecDevCli = "N";
            String strRevBodMerRev = "N";
            try
            {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    strSql = "UPDATE tbm_cabRecMerSolDevVen SET st_reg='I' WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + "AND  co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText();
                    stmLoc.executeUpdate(strSql);

                    if (objZafParSis.getCodigoMenu() == 1888) {
                        strSql = "UPDATE tbm_cabsoldevven SET   st_recmerdev='" + strRecMerDev + "' WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText();
                        stmLoc.executeUpdate(strSql);
                    }
                    if (objZafParSis.getCodigoMenu() == 1908) {
                        strSql = "UPDATE tbm_cabsoldevven SET  st_revBodMerDev ='" + strRevBodMerRev + "' WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText();
                        stmLoc.executeUpdate(strSql);
                    }

                    if (objZafParSis.getCodigoMenu() == 1898) {
                        strSql = "UPDATE tbm_cabsoldevven SET   st_revTecMerDev='" + strRecTecMerDev + "' WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText();
                        stmLoc.executeUpdate(strSql);
                    }
                    if (objZafParSis.getCodigoMenu() == 1928) {
                        strSql = "UPDATE tbm_cabsoldevven SET  st_MerRecDevCli='" + stMerRecDevCli + "' WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText();
                        stmLoc.executeUpdate(strSql);
                    }


                    for (int i = 0; i < tblDat.getRowCount(); i++) {

                        String strEstFisBod = (tblDat.getValueAt(i, INT_TBL_IEBODFIS) == null ? "" : tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());

                        if (objZafParSis.getCodigoMenu() == 1888) 
                        {
                            strSql = "UPDATE tbm_detsoldevven SET  "
                                    + " nd_canrec=nd_canrec-" + (tblDat.getValueAt(i, INT_TBL_CANREC_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANREC_ORI).toString())) + ",  "
                                    + " nd_canNunRec=nd_canNunRec-" + (tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC_ORI).toString())) + " "
                                    + " ,nd_canRevBodAce=nd_canRevBodAce-" + (tblDat.getValueAt(i, INT_TBL_CANACE_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString())) + " "
                                    + " ,nd_canRevBodRec=nd_canRevBodRec-" + (tblDat.getValueAt(i, INT_TBL_CANRCH_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString())) + " "
                                    + " ,st_devFalStkAce='N' "
                                    + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                    + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText() + " AND co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();

                            double dblCan = objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANACE_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString())), 4);
                            dblCan += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC_ORI).toString())), 4);
                            dblCan += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANRCH_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString())), 4);

                            strSqlFic = "";
                            if (strEstFisBod.equals("N")) {
                                if (strMerIngEgr.equals("S")) {
                                    strSqlFic = " UPDATE tbm_invbod SET nd_caningbod=nd_caningbod+" + dblCan + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                    stmLoc.executeUpdate(strSqlFic);
                                    if (conRemGlo != null) {
                                        stm_remota = conRemGlo.createStatement();
                                        stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                        stm_remota.close();
                                        stm_remota = null;
                                    }
                                }
                            }
                        }
                        if (objZafParSis.getCodigoMenu() == 1898)
                        {
                            strSql = "UPDATE tbm_detsoldevven SET  "
                                    + " nd_canrevtecace=nd_canrevtecace-" + (tblDat.getValueAt(i, INT_TBL_CANACE_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString())) + " "
                                    + " ,nd_canrevtecrec=nd_canrevtecrec-" + (tblDat.getValueAt(i, INT_TBL_CANRCH_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString())) + " "
                                    + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                    + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText() + " AND co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                        }
                        if (objZafParSis.getCodigoMenu() == 1908)
                        {
                            strSql = "UPDATE tbm_detsoldevven SET  "
                                    + " nd_canRevBodAce=nd_canRevBodAce-" + (tblDat.getValueAt(i, INT_TBL_CANACE_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString())) + " "
                                    + " ,nd_canRevBodRec=nd_canRevBodRec-" + (tblDat.getValueAt(i, INT_TBL_CANRCH_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString())) + " "
                                    + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                    + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText() + " AND co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();

                            double dblCan = objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANACE_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString())), 4);
                            dblCan += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANRCH_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString())), 4);

                            strSqlFic = "";
                            if (strEstFisBod.equals("N")) {
                                if (strMerIngEgr.equals("S")) {
                                    strSqlFic = " UPDATE tbm_invbod SET nd_caningbod=nd_caningbod+" + dblCan + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                    stmLoc.executeUpdate(strSqlFic);
                                    if (conRemGlo != null) {
                                        stm_remota = conRemGlo.createStatement();
                                        stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                        stm_remota.close();
                                        stm_remota = null;
                                    }
                                }
                            }

                        }
                        if (objZafParSis.getCodigoMenu() == 1928) 
                        {
                            strSql = "UPDATE tbm_detsoldevven SET  "
                                    + " nd_canRecDevCli=nd_canRecDevCli-" + (tblDat.getValueAt(i, INT_TBL_CANDEVCLI_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANDEVCLI_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANDEVCLI_ORI).toString())) + " "
                                    + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                    + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText() + " AND co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                        }

                        stmLoc.executeUpdate(strSql);
                    }

                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        @Override
        public void clickAceptar() {
            setEstadoBotonMakeFac();
        }

        @Override
        public void clickAnterior() {
            try {
                if (rstCab != null) {
                    abrirCon();
                    if (!rstCab.isFirst()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.previous();
                                refrescaDatos(CONN_GLO, rstCab);
                            }
                        } else {
                            rstCab.previous();
                            refrescaDatos(CONN_GLO, rstCab);
                        }
                    }
                    CerrarCon();

                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        @Override
        public void clickAnular() {
        }

        @Override
        public void clickConsultar() {
            clnTextos();
            noEditable(false);

            java.awt.Color colBack;
            colBack = txtNumDocSol.getBackground();
            bloquea(txtNumDocSol, colBack, false);

            butBusNumDoc.setEnabled(false);

            cargarTipoDoc(2);
        }

        @Override
        public void clickEliminar() {
//          noEditable(false);
        }

        @Override
        public void clickFin() {
            try {
                if (rstCab != null) {
                    abrirCon();
                    if (!rstCab.isLast()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.last();
                                refrescaDatos(CONN_GLO, rstCab);
                            }
                        } else {
                            rstCab.last();
                            refrescaDatos(CONN_GLO, rstCab);
                        }
                    }
                    CerrarCon();
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        @Override
        public void clickInicio() {
            try {
                if (rstCab != null) {
                    abrirCon();
                    if (!rstCab.isFirst()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.first();
                                refrescaDatos(CONN_GLO, rstCab);
                            }
                        } else {
                            rstCab.first();
                            refrescaDatos(CONN_GLO, rstCab);
                        }
                    }
                    CerrarCon();
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        @Override
        public void clickInsertar() 
        {
            try 
            {
                clnTextos();
                noEditable(false);

                java.awt.Color colBack;
                colBack = txtCodDoc.getBackground();
                txtCodDoc.setEditable(false);
                txtCodDoc.setBackground(colBack);

                //Obtener la fecha y hora del servidor.  
                datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                txtFecDoc.setText(objUti.formatearFecha(datFecAux, "dd/MM/yyyy"));

                cargarTipoDoc(1);

                txtCodRes.setText("" + objZafParSis.getCodigoUsuario());
                cargarResponsable();

                //BuscarVendedor("a.co_usr",txtCodRes.getText(),0);

                if (rstCab != null) {
                    rstCab.close();
                    rstCab = null;
                }

            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void setEstadoBotonMakeFac() {
            switch (getEstado()) {
                case 'l'://Estado 0 => Listo
                    break;
                case 'x'://Estado click modificar
                    break;
                case 'c'://Estado Consultar
                    break;
                case 'y':
                    break;
                case 'z':
                    break;
                default:
                    break;
            }
        }

        @Override
        public void clickSiguiente() {
            try {
                if (rstCab != null) {
                    abrirCon();
                    if (!rstCab.isLast()) {
                        if (blnHayCam || objTblMod.isDataModelChanged()) {
                            if (isRegPro()) {
                                rstCab.next();
                                refrescaDatos(CONN_GLO, rstCab);
                            }
                        } else {
                            rstCab.next();
                            refrescaDatos(CONN_GLO, rstCab);
                        }
                    }
                    CerrarCon();
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        @Override
        public boolean eliminar() {
            boolean blnRes = false;
            int intCodDoc = 0;
            java.sql.Connection conn;
            try {
                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) {
                    conn.setAutoCommit(false);


                    if (!abrirConRem()) {
                        return false;
                    }

                    strAux = objTooBar.getEstadoRegistro();
                    if (strAux.equals("Eliminado")) {
                        MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                        blnRes = true;
                    }

                    if (!verificaAnuEli(conn)) {
                        MensajeInf("El documento ya tiene Nota de Credito.\nNo es posible eliminar el documento.");
                        blnRes = true;
                    }

                    if (objZafParSis.getCodigoMenu() == 1888) {
                        if (!verificaAnuEliDoc(conn)) {
                            MensajeInf("NO SE PUEDE ANULAR PORQUE HAY UNA REVICIÓN TECNICA. ");
                            blnRes = true;
                        }
                    }
                    intCodDoc = Integer.parseInt(txtCodDoc.getText());

                    if (!blnRes) {
                        if (eliminarReg(conn)) {
                            if (conRemGlo != null) {
                                conRemGlo.commit();
                            }
                            conn.commit();
                            cargarDetIns(conn, intCodDoc);
                            blnRes = true;
                            objTooBar.setEstadoRegistro("Eliminado");
                            blnHayCam = false;
                        } else {
                            conn.rollback();
                        }
                    } else {
                        blnRes = false;
                    }

                    if (conRemGlo != null) {
                        conRemGlo.close();
                        conRemGlo = null;
                    }
                    conn.close();
                    conn = null;
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean eliminarReg(java.sql.Connection conn) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.Statement stm_remota;
            String strSql = "", strSqlFic = "";
            String strRecMerDev = "N";
            String strRecTecMerDev = "N";
            String stMerRecDevCli = "N";
            String strRevBodMerRev = "N";

            try 
            {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    strSql = "UPDATE tbm_cabRecMerSolDevVen SET st_reg='E' WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + "AND  co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText();
                    stmLoc.executeUpdate(strSql);


                    if (!strAux.equals("Anulado")) {

                        if (objZafParSis.getCodigoMenu() == 1888) {
                            strSql = "UPDATE tbm_cabsoldevven SET   st_recmerdev='" + strRecMerDev + "' WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                    + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText();
                            stmLoc.executeUpdate(strSql);
                        }
                        if (objZafParSis.getCodigoMenu() == 1908) {
                            strSql = "UPDATE tbm_cabsoldevven SET   st_revBodMerDev='" + strRevBodMerRev + "' WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                    + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText();
                            stmLoc.executeUpdate(strSql);
                        }

                        if (objZafParSis.getCodigoMenu() == 1898) {
                            strSql = "UPDATE tbm_cabsoldevven SET   st_revTecMerDev='" + strRecTecMerDev + "' WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                    + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText();
                            stmLoc.executeUpdate(strSql);
                        }
                        if (objZafParSis.getCodigoMenu() == 1928) {
                            strSql = "UPDATE tbm_cabsoldevven SET  st_MerRecDevCli='" + stMerRecDevCli + "' WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                    + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText();
                            stmLoc.executeUpdate(strSql);
                        }

                        for (int i = 0; i < tblDat.getRowCount(); i++) 
                        {
                            String strEstFisBod = (tblDat.getValueAt(i, INT_TBL_IEBODFIS) == null ? "" : tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());

                            if (objZafParSis.getCodigoMenu() == 1898) 
                            {
                                strSql = "UPDATE tbm_detsoldevven SET  "
                                        + " nd_canrec=nd_canrec-" + (tblDat.getValueAt(i, INT_TBL_CANREC_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANREC_ORI).toString())) + ",  "
                                        + " nd_canNunRec=" + (tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC_ORI).toString())) + " "
                                        + " ,nd_canRevBodAce=nd_canRevBodAce-" + (tblDat.getValueAt(i, INT_TBL_CANACE_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString())) + " "
                                        + " ,nd_canRevBodRec=nd_canRevBodRec-" + (tblDat.getValueAt(i, INT_TBL_CANRCH_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString())) + " "
                                        + " ,st_devFalStkAce='N' "
                                        + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                        + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText() + " AND co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();


                                double dblCan = objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANACE_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString())), 4);
                                strSqlFic = "";
                                if (strEstFisBod.equals("N")) {
                                    if (strMerIngEgr.equals("S")) {
                                        if (strTipIngEgr.equals("I")) {
                                            strSqlFic = " UPDATE tbm_invbod SET nd_caningbod=nd_caningbod-" + dblCan + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();

                                            strSqlFic += "; Update tbm_invbod "
                                                    + " set nd_caningbod=nd_caningbod-abs(" + dblCan + ") "
                                                    + " where co_emp=" + objZafParSis.getCodigoEmpresaGrupo() + " and co_itm ="
                                                    + " (Select co_itm from tbm_equinv where co_emp=" + objZafParSis.getCodigoEmpresaGrupo() + " and co_itmmae IN "
                                                    + " (Select co_itmmae from tbm_equinv where co_emp =" + objZafParSis.getCodigoEmpresa() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + ") )"
                                                    + " and co_bod ="
                                                    + " (select co_bodgrp from  tbr_bodempbodgrp where co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + ") ";
                                            stmLoc.executeUpdate(strSqlFic);
                                            if (conRemGlo != null) {
                                                stm_remota = conRemGlo.createStatement();
                                                stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                                stm_remota.close();
                                                stm_remota = null;
                                            }
                                        }
                                        if (strTipIngEgr.equals("E")) {
                                            strSqlFic = " UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod-" + dblCan + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();

                                            strSqlFic += "; Update tbm_invbod "
                                                    + " set nd_canegrbod=nd_canegrbod-abs(" + dblCan + ") "
                                                    + " where co_emp=" + objZafParSis.getCodigoEmpresaGrupo() + " and co_itm ="
                                                    + " (Select co_itm from tbm_equinv where co_emp=" + objZafParSis.getCodigoEmpresaGrupo() + " and co_itmmae IN "
                                                    + " (Select co_itmmae from tbm_equinv where co_emp =" + objZafParSis.getCodigoEmpresa() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + ") )"
                                                    + " and co_bod ="
                                                    + " (select co_bodgrp from  tbr_bodempbodgrp where co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + ") ";
                                            stmLoc.executeUpdate(strSqlFic);
                                            if (conRemGlo != null) {
                                                stm_remota = conRemGlo.createStatement();
                                                stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                                stm_remota.close();
                                                stm_remota = null;
                                            }

                                        }
                                    }
                                }

                            }
                            if (objZafParSis.getCodigoMenu() == 1898) 
                            {
                                strSql = "UPDATE tbm_detsoldevven SET  "
                                        + " nd_canrevtecace=nd_canrevtecace-" + (tblDat.getValueAt(i, INT_TBL_CANACE_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString())) + " "
                                        + " ,nd_canrevtecrec=nd_canrevtecrec-" + (tblDat.getValueAt(i, INT_TBL_CANRCH_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString())) + " "
                                        + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                        + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText() + " AND co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                            }
                            if (objZafParSis.getCodigoMenu() == 1908) 
                            {
                                strSql = "UPDATE tbm_detsoldevven SET  "
                                        + " nd_canRevBodAce=nd_canRevBodAce-" + (tblDat.getValueAt(i, INT_TBL_CANACE_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString())) + " "
                                        + " ,nd_canRevBodRec=nd_canRevBodRec-" + (tblDat.getValueAt(i, INT_TBL_CANRCH_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString())) + " "
                                        + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                        + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText() + " AND co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();


                                double dblCan = objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANACE_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString())), 4);
                                strSqlFic = "";
                                if (strEstFisBod.equals("N")) {
                                    if (strMerIngEgr.equals("S")) {
                                        if (strTipIngEgr.equals("I")) {
                                            strSqlFic = " UPDATE tbm_invbod SET nd_caningbod=nd_caningbod-" + dblCan + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();

                                            strSqlFic += "; Update tbm_invbod "
                                                    + " set nd_caningbod=nd_caningbod-abs(" + dblCan + ") "
                                                    + " where co_emp=" + objZafParSis.getCodigoEmpresaGrupo() + " and co_itm ="
                                                    + " (Select co_itm from tbm_equinv where co_emp=" + objZafParSis.getCodigoEmpresaGrupo() + " and co_itmmae IN "
                                                    + " (Select co_itmmae from tbm_equinv where co_emp =" + objZafParSis.getCodigoEmpresa() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + ") )"
                                                    + " and co_bod ="
                                                    + " (select co_bodgrp from  tbr_bodempbodgrp where co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + ") ";
                                            stmLoc.executeUpdate(strSqlFic);
                                            if (conRemGlo != null) {
                                                stm_remota = conRemGlo.createStatement();
                                                stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                                stm_remota.close();
                                                stm_remota = null;
                                            }
                                        }
                                        if (strTipIngEgr.equals("E")) {
                                            strSqlFic = " UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod-" + dblCan + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();

                                            strSqlFic += "; Update tbm_invbod "
                                                    + " set nd_canegrbod=nd_canegrbod-abs(" + dblCan + ") "
                                                    + " where co_emp=" + objZafParSis.getCodigoEmpresaGrupo() + " and co_itm ="
                                                    + " (Select co_itm from tbm_equinv where co_emp=" + objZafParSis.getCodigoEmpresaGrupo() + " and co_itmmae IN "
                                                    + " (Select co_itmmae from tbm_equinv where co_emp =" + objZafParSis.getCodigoEmpresa() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + ") )"
                                                    + " and co_bod ="
                                                    + " (select co_bodgrp from  tbr_bodempbodgrp where co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + ") ";
                                            stmLoc.executeUpdate(strSqlFic);
                                            if (conRemGlo != null) {
                                                stm_remota = conRemGlo.createStatement();
                                                stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                                stm_remota.close();
                                                stm_remota = null;
                                            }

                                        }
                                    }
                                }

                            }
//      if(objZafParSis.getCodigoMenu() == 1908){    
//          strSql="UPDATE tbm_detsoldevven SET  " +
//         " nd_canrevtecace=nd_canrevtecace-"+(tblDat.getValueAt(i, INT_TBL_CANACE_ORI)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString() ) )+" " +
//         " ,nd_canrevtecrec=nd_canrevtecrec-"+(tblDat.getValueAt(i, INT_TBL_CANRCH_ORI)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString() ) )+" " +
//         " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
//         " AND co_loc="+txtCodLoc.getText()+" AND co_tipdoc="+txtCodTipDocSol.getText()+" AND co_Doc="+txtCodDocSol.getText()+" AND co_reg="+tblDat.getValueAt(i, INT_TBL_CODREG).toString();
//     }  
                            if (objZafParSis.getCodigoMenu() == 1928) {
                                strSql = "UPDATE tbm_detsoldevven SET  "
                                        + " nd_canRecDevCli=nd_canRecDevCli-" + (tblDat.getValueAt(i, INT_TBL_CANDEVCLI_ORI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANDEVCLI_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANDEVCLI_ORI).toString())) + " "
                                        + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                        + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText() + " AND co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                            }
                            //System.out.println(">>"+ strSql );
                            stmLoc.executeUpdate(strSql);
                        }
                    }

                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean validaCampos() 
        {
            boolean blnRec=false;
            if (txtDesCorTipDoc.getText().equals("")) {
                tabGen.setSelectedIndex(0);
                MensajeInf("El campo << Tipo Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                txtDesCorTipDoc.requestFocus();
                return false;
            }

            if (!txtFecDoc.isFecha()) {
                tabGen.setSelectedIndex(0);
                MensajeInf("El campo << Fecha Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                txtFecDoc.requestFocus();
                return false;
            }

            if (txtCodRes.getText().equals("")) {
                tabGen.setSelectedIndex(0);
                MensajeInf("El campo << Solicitente >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                txtCodRes.requestFocus();
                return false;
            }

            if (txtNumDoc.getText().equals("")) {
                tabGen.setSelectedIndex(0);
                MensajeInf("El campo << Número de Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                txtNumDoc.requestFocus();
                return false;
            }

            if (txtNumDocSol.getText().equals("")) {
                tabGen.setSelectedIndex(1);
                MensajeInf("Escoja la Solicitud.");
                txtNumDocSol.requestFocus();
                return false;
            }
            
            //Validar que siempre exista una cantidad recibida antes de guardar el documento.
            if(objZafParSis.getCodigoMenu()==1888)
            {
                for (int j = 0; j < tblDat.getRowCount(); j++) 
                {   
                    if (objUti.redondear((tblDat.getValueAt(j, INT_TBL_CANDEV) == null ? "0" : (tblDat.getValueAt(j, INT_TBL_CANDEV).toString().equals("") ? "0" : tblDat.getValueAt(j, INT_TBL_CANDEV).toString())), 6) > 0) 
                    {
                        if  ( 
                                (objUti.redondear((tblDat.getValueAt(j, INT_TBL_CANREC) == null ? "0" : (tblDat.getValueAt(j, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(j, INT_TBL_CANREC).toString())), 6) > 0) 
                                || 
                                (objUti.redondear((tblDat.getValueAt(j, INT_TBL_CANTOTNUNREC) == null ? "0" : (tblDat.getValueAt(j, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(j, INT_TBL_CANTOTNUNREC).toString())), 6) > 0) 
                            )
                        {
                            blnRec=true;
                        }
                    }
                }   
                
                if(!blnRec)               
                {
                    tabGen.setSelectedIndex(1);
                    MensajeInf("Debe ingresar cantidad recibida.");
                }
                return blnRec;
            }

            return true;
        }

        public boolean abrirConRem() 
        {
            boolean blnres = false;
            try {
                int intIndEmp = INTCODREGCEN;
                if (intIndEmp != 0) {
                    conRemGlo = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(intIndEmp), objZafParSis.getUsuarioBaseDatos(intIndEmp), objZafParSis.getClaveBaseDatos(intIndEmp));
                    conRemGlo.setAutoCommit(false);
                }
                blnres = true;
            } catch (java.sql.SQLException e) {
                MensajeInf("NO SE PUEDE ESTABLECER LA CONEXION REMOTA CON LA BASE CENTRAL..");
                INTVERCONCEN = 1;
                return false;
            }
            return blnres;
        }

        @Override
        public boolean insertar() 
        {
            boolean blnRes = false;
            java.sql.Connection conn;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            String strTipRev = "B";
            String strRevTec = "N";
            String strExiMerRec = "N";
            String strRecMerDev = "N";
            String strRecTecMerDev = "N";
            String strRecBodMerDev = "N";

            int intCodDoc = 0;
            try
            {


                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) 
                {
                    conn.setAutoCommit(false);
                    stmLoc = conn.createStatement();

                    if (!abrirConRem()) {
                        return false;
                    }

                    strSql = "SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 end as co_doc  FROM tbm_cabRecMerSolDevVen WHERE "
                            + " co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " and co_tipDoc = " + txtCodTipDoc.getText();
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        intCodDoc = rstLoc.getInt("co_doc");
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;

                    double dlbCanTotRev = 0;
                    double dlbSumTotRecAceReh = 0;
                    double dlbCanTotDev = 0;
                    double dlbSumTotDev = 0;

                    double dlbCanTotRecTecDev = 0;
                    double dlbSumTotRecTecDev = 0;

                    for (int i = 0; i < tblDat.getRowCount(); i++) 
                    {

                        dlbCanTotRev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTREC).toString())), 6);
                        dlbCanTotRev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString())), 6);


                        /*if(tblDat.getValueAt(i, INT_TBL_EST_REC_TEC).toString().equals("S")){ 
                         dlbSumTotRecAceReh += objUti.redondear( (tblDat.getValueAt(i, INT_TBL_CANACE)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANACE).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANACE).toString())) ,6 );  
                         dlbSumTotRecAceReh += objUti.redondear( (tblDat.getValueAt(i, INT_TBL_CANRCH)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANRCH).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANRCH).toString())) ,6 );  
                         }else{*/
                        dlbSumTotRecAceReh += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE).toString())), 6);
                        dlbSumTotRecAceReh += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANRCH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH).toString())), 6);
                        dlbSumTotRecAceReh += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_ACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_ACE).toString())), 6);
                        dlbSumTotRecAceReh += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH).toString())), 6);
                        //  }


                        dlbSumTotDev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString())), 6);
                        dlbSumTotDev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANREC).toString())), 6);
                        dlbCanTotDev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANDEV) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANDEV).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANDEV).toString())), 6);

                        dlbSumTotRecTecDev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE).toString())), 6);
                        dlbSumTotRecTecDev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANRCH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH).toString())), 6);
                        dlbSumTotRecTecDev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_ACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_ACE).toString())), 6);
                        dlbSumTotRecTecDev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH).toString())), 6);

                        dlbCanTotRecTecDev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTREC).toString())), 6);

                        if (tblDat.getValueAt(i, INT_TBL_REVTEC) != null) {
                            if (tblDat.getValueAt(i, INT_TBL_REVTEC).toString().equals("true")) {
                                strRevTec = "S";
                            }
                        }

                    }

                    if (dlbCanTotDev == dlbSumTotDev) {
                        strRecMerDev = "S";
                    }
                    if (objZafParSis.getCodigoMenu() == 1898) {
                        strTipRev = "T";
                        if (dlbCanTotRecTecDev == dlbSumTotRecTecDev) {
                            strRecTecMerDev = "S";
                        }
                    }

                    if (insertarCab(conn, intCodDoc, strTipRev, strRevTec, strExiMerRec, strRecMerDev, strRecTecMerDev, strRecBodMerDev)) {
                        if (insertarDet(conn, intCodDoc)) {
                            if (realizaConfGuiSal(conn, objZafParSis.getCodigoEmpresa(), Integer.parseInt(txtCodLoc.getText()), Integer.parseInt(txtCodTipDocSol.getText()), Integer.parseInt(txtCodDocSol.getText()))) 
                            {
                                if (conRemGlo != null) {
                                    conRemGlo.commit();
                                }
                                conn.commit();
                                txtCodDoc.setText("" + intCodDoc);
                                blnRes = true;
                                System.out.println("hola");
                                cargarDetIns(conn, intCodDoc);
                                txtNumDocOcu.setText(txtNumDoc.getText());

                            } else {    conn.rollback();     }
                        } else {    conn.rollback();     }
                    } else {    conn.rollback();     }

                    if (conRemGlo != null) 
                    {
                        conRemGlo.close();
                        conRemGlo = null;
                    }
                    conn.close();
                    conn = null;

                }
                
                if(procesaDevolucion(objZafParSis.getCodigoEmpresa(), Integer.parseInt(txtCodLoc.getText()), Integer.parseInt(txtCodTipDocSol.getText()), Integer.parseInt(txtCodDocSol.getText())))
                {
                    System.out.println("PROCESA");
                }

            }
            catch (java.sql.SQLException Evt)
            {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            catch (Exception Evt) 
            {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean realizaConfGuiSal(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc) 
        {
            boolean blnRes = true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            String strFecDoc = "";
            String strSqlPrin = "";
            int intCodTipDocConfEgr = 114;
            int intCodDocConfEgr = 0;
            int intNumRegConfEgr = 0;
            int intNumDocConfEgr = 0;
            try 
            {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    if (objZafParSis.getCodigoMenu() == 1888) 
                    {
                        intCodDocConfEgr = _getObtenerMaxCodDocConfEgr(conn, intCodEmp, intCodLoc, intCodTipDocConfEgr);
                        intNumDocConfEgr = _getObtenerNumDocConfEgr(conn, intCodEmp, intCodLoc, intCodTipDocConfEgr);

                        strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";

                        strSql = "SELECT * FROM ( "
                                + " select a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, "
                                + " a2.co_reg, a3.co_ptopar, a2.co_itm, a3.ne_numdoc, "
                                + " a2.nd_can, a2.nd_cancon, a2.nd_cannunrec, "
                                /* AGREGADO POR PROYECTO TRANSFERENCIAS */
                                + " a1.co_emp as co_empdetMovinv, a1.co_loc as co_locdetMovinv, a1.co_tipdoc as co_tipdocdetMovinv,"
                                + " a1.co_doc as co_docdetMovinv ,"
                                + " case when a3.co_tipdoc=102 then "
                                + " ( a2.nd_cancon + a2.nd_cannunrec ) "
                                + " else "
                                + " ( a1.nd_cancon + a1.nd_cannunrec ) "
                                + " end as canconf, "                                
                                
                                /* AGREGADO POR PROYECTO TRANSFERENCIAS */
                                //+ " ( a2.nd_cancon + a2.nd_cannunrec ) as canconf,  "
                                + " a4.nd_candev , a4.nd_canrevbodace, a4.nd_cannunrec from tbm_cabsoldevven as a  "
                                + " inner join tbm_detsoldevven as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoc and a4.co_doc=a.co_doc )  "
                                + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a4.co_regrel )  "
                                + " inner join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg)  "
                                + " inner join tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc )  "
                                + " where a.co_emp=" + intCodEmp + " and a.co_loc=" + intCodLoc + " and a.co_tipdoc=" + intCodTipDoc + " and a.co_doc=" + intCodDoc + " and a2.st_meregrfisbod='S'  and a4.nd_canrevbodace > 0  "
                                + " ) as x  "
                                + " where (case when x.co_tipdoc=102 then \n" +
                                "	canconf <= 0   \n" +
                                "      else \n " +
                                "	canconf >= 0   \n" +
                                "      end)"
                                + " and nd_can = nd_canrevbodace "
                                
                                
                                +" union "

                                +" SELECT * "
                                +" FROM (  "
                                +"         select a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc,  "
                                +"                a5.co_reg, a3.co_ptopar, a5.co_itm, a3.ne_numdoc,  "
                                +"                a5.nd_can, a5.nd_cancon, a5.nd_cannunrec,  "
                                +"                 a1.co_emp as co_empdetMovinv, a1.co_loc as co_locdetMovinv, a1.co_tipdoc as co_tipdocdetMovinv, "
                                +"                 a1.co_doc as co_docdetMovinv , "
                                +"                case when a3.co_tipdoc=102 then  "
                                +"                         ( a5.nd_cancon + a5.nd_cannunrec )  "
                                +"                 else  "
                                +"                         ( detres.nd_cancon + detres.nd_cannunrec )  "
                                +"                 end as canconf,  a4.nd_candev , a4.nd_canrevbodace, a4.nd_cannunrec "
                                +"         FROM tbm_cabsoldevven as a  "
                                +"        inner join tbm_detsoldevven as a4 "
                                +"         on (a.co_emp=a4.co_emp  and a.co_loc=a4.co_loc and a.co_tipdoc=a4.co_tipdoc and a.co_doc=a4.co_doc )  "
                                +"         INNER JOIN tbm_detmovinv as a1 "
                                +"         on (a1.co_emp=a.co_emp  and a1.co_loc=a.co_locrel  and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a4.co_docrel) "
                                +"         inner join tbm_cabsegmovinv as s "
                                +"         on (s.co_emprelcabmovinv =a1.co_emp and s.co_locrelcabmovinv=a1.co_loc and s.co_tipdocrelcabmovinv=a1.co_tipdoc and s.co_docrelcabmovinv=a1.co_doc) "
                                +"         inner join tbm_cabsegmovinv as s2 "
                                +"         on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271) "
                                +"         INNER JOIN tbm_cabguirem as a3  "
                                +"         on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem ) "
                                +"         INNER JOIN tbm_detguirem as a5 "
                                +"         on(a5.co_emp=a3.co_emp and a5.co_loc=a3.co_loc and a5.co_tipdoc=a3.co_tipdoc and a5.co_doc=a3.co_doc) "
                                +"         INNER JOIN tbm_detmovinv as detres "                                                                                 
                                +"         on (detres.co_emp=a5.co_emprel  and detres.co_loc=a5.co_locrel  and detres.co_tipdoc=a5.co_tipdocrel and detres.co_doc=a5.co_docrel and detres.co_reg=a5.co_regrel and detres.co_tipdoc<>228 and detres.co_tipdoc=294) "
                                +"         WHERE a.co_emp="+intCodEmp+" and a.co_loc= "+intCodLoc
                                +"         and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc
                                +"         and a5.st_meregrfisbod='S'  and a4.nd_canrevbodace > 0 ) as x   "
                                +"         where (case when x.co_tipdoc=102 then "
                                +"         canconf <= 0   "
                                +"       else "
                                +"         canconf >= 0   "
                                +"       end) and nd_can = nd_canrevbodace "
                                
                                ;
                        rstLoc = stmLoc.executeQuery(strSql);
                        while (rstLoc.next()) {

                            intNumRegConfEgr++;

                            if (intNumRegConfEgr == 1) {
                                strSqlPrin = "INSERT INTO tbm_cabingegrmerbod(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, co_mnu, st_imp, "
                                        + " tx_obs1, st_reg, fe_ing,  co_usring,  st_regrep, co_locrelguirem, co_tipdocrelguirem, co_docrelguirem ) "
                                        + " "
                                        + " SELECT " + rstLoc.getInt("co_emp") + ", " + rstLoc.getInt("co_loc") + ", " + intCodTipDocConfEgr + " , " + intCodDocConfEgr + ", "
                                        + " '" + strFecDoc + "', " + intNumDocConfEgr + ", " + rstLoc.getInt("co_ptopar") + ", 2205, 'N', 'GENERADO DE MANERA AUTOMATICO', "
                                        + " 'A', " + objZafParSis.getFuncionFechaHoraBaseDatos() + ", " + objZafParSis.getCodigoUsuario() + ", 'I', "
                                        + " " + rstLoc.getInt("co_loc") + ", " + rstLoc.getInt("co_tipdoc") + ", " + rstLoc.getInt("co_doc") + " ";
                                strSqlPrin += " ; UPDATE tbm_cabtipdoc SET  st_regrep='M', ne_ultdoc=" + intNumDocConfEgr + " WHERE co_emp=" + intCodEmp + " and "
                                        + " co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDocConfEgr + " ";
                            }

                            strSqlPrin += " ; INSERT INTO tbm_detingegrmerbod(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, co_bod, nd_can, tx_obs1, st_regrep,"
                                    + " co_locrelguirem, co_tipdocrelguirem, co_docrelguirem, co_regrelguirem ) "
                                    + " "
                                    + " SELECT " + rstLoc.getInt("co_emp") + ", " + rstLoc.getInt("co_loc") + ", " + intCodTipDocConfEgr + " , " + intCodDocConfEgr + " "
                                    + ", " + intNumRegConfEgr + ", " + rstLoc.getInt("co_itm") + ", " + rstLoc.getInt("co_ptopar") + ", " + rstLoc.getString("nd_canrevbodace") + "*-1, '', 'I', "
                                    + " " + rstLoc.getInt("co_loc") + ", " + rstLoc.getInt("co_tipdoc") + ", " + rstLoc.getInt("co_doc") + ", " + rstLoc.getInt("co_reg") + " ";

                            if(rstLoc.getInt("co_tipdoc")==102){
                            
                                strSqlPrin += " ; UPDATE  tbm_detguirem SET st_regrep='M', nd_cancon=" + rstLoc.getString("nd_canrevbodace") + " WHERE co_emp=" + rstLoc.getInt("co_emp") + " "
                                        + " and co_loc=" + rstLoc.getInt("co_loc") + " and co_tipdoc=" + rstLoc.getInt("co_tipdoc") + " and co_doc=" + rstLoc.getInt("co_doc") + " "
                                        + " and co_reg=" + rstLoc.getInt("co_reg") + " ";
                            }else{
                                strSqlPrin += " ; UPDATE  tbm_detmovinv SET nd_cancon=" + rstLoc.getString("nd_canrevbodace") + " WHERE co_emp=" + rstLoc.getInt("co_empdetMovinv") + " "
                                        + " and co_loc=" + rstLoc.getInt("co_locdetMovinv") + " and co_tipdoc=" + rstLoc.getInt("co_tipdocdetMovinv") + " and co_doc=" + rstLoc.getInt("co_docdetMovinv") + " "
                                        + " and co_reg=" + rstLoc.getInt("co_reg") + " ";
                            
                            }
                        }
                        rstLoc.close();
                        rstLoc = null;

                        if (intNumRegConfEgr != 0) {
                            //System.out.println("--> "+strSqlPrin );
                            stmLoc.executeUpdate(strSqlPrin);
                        }

                    }

                    stmLoc.close();
                    stmLoc = null;

                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        /**
         * Obtiene el maximo codigo de documento de la tabla tbm_cabingegrmerbod
         *
         * @param conn Coneccion de la base
         * @param intCodEmp Codigo de empresa
         * @param intCodLoc Codigo de local
         * @param intCodTipDoc Codigo tipo de documento
         * @return codigo del documento.
         */
        public int _getObtenerMaxCodDocConfEgr(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc) 
        {
            int intCodDoc = -1;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "SELECT CASE WHEN max(co_doc) IS NULL THEN 1 ELSE max(co_doc)+1 END AS codoc FROM tbm_cabingegrmerbod "
                            + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " AND co_tipdoc=" + intCodTipDoc + "  ";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        intCodDoc = rstLoc.getInt("codoc");
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return intCodDoc;
        }

        /**
         * Obtiene el numero del tipo de documento
         *
         * @param conn Coneccion de la base
         * @param intCodEmp Codigo de empresa
         * @param intCodLoc Codigo de local
         * @param intCodTipDoc Codigo tipo de documento
         * @return numero del documento.
         */
        public int _getObtenerNumDocConfEgr(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc) 
        {
            int intNumDoc = -1;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "SELECT CASE WHEN max(ne_ultdoc) IS NULL THEN 1 ELSE max(ne_ultdoc)+1 END AS numdoc FROM tbm_cabtipdoc "
                            + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " AND co_tipdoc=" + intCodTipDoc + "  ";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        intNumDoc = rstLoc.getInt("numdoc");
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return intNumDoc;
        }

        private boolean cargarDetIns(java.sql.Connection conn, int intCodDoc) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            Vector vecData;
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    vecData = new Vector();

                    String strAux2 = " , CASE WHEN ( (trim(SUBSTR (UPPER(a1.tx_codalt), length(a1.tx_codalt) ,1)) IN ( "
                            + " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a "
                            + " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) "
                            + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " and a.co_loc=" + objZafParSis.getCodigoLocal() + " and a1.st_reg='A' and   a.st_reg='P' ))) "
                            + " THEN 'S' ELSE 'N' END AS proconf  ";


                    if (objZafParSis.getCodigoMenu() == 1888) {

//   strSql="SELECT  case when  proconf in ('S') then nd_cansolnodevprv  else  nd_candev_01 end as nd_candev,  *  from ( " +
//   " SELECT (a1.nd_candev - a1.nd_canvolfac) as nd_candev_01,  a1.nd_cansolnodevprv,   a1.nd_candevprv, a1.nd_cannodevprv, a1.st_devFalStkace, a1.st_devFalStk, a.tx_obsCanRevTec, a1.nd_canRec as nd_cantotRec , a.tx_obsCanRevBod, a.nd_canRevBodRec as nd_canRechazada, a.nd_canRevBodAce as nd_canAceptada,  a1.nd_canNunRec, a.nd_canRec, a.tx_obsCanRec,  a1.co_reg, a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a1.co_bod, a2.tx_nom, a1.nd_can, a1.st_revtec  " +
//   " ,a1.nd_canRevTecAce as nd_canRevTecAceSol, a1.nd_canRevTecRec as nd_canRevTecRecSol, a1.nd_canRevBodAce as nd_canRevBodAceSol, a1.nd_canRevBodRec as nd_canRevBodRecSol " +
//   "  ,a1.nd_canRecDevCli  " +
//   strAux2+" FROM tbm_detRecMerSolDevVen AS a " +
//   " INNER JOIN tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel AND a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) " +
//   " INNER JOIN  tbm_bod AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_bod=a1.co_bod) " +
//   " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+"  AND " +
//   " a.co_tipdoc="+txtCodTipDoc.getText()+" AND a.co_doc="+intCodDoc+" " +
//   " ) as x ";


                        strSql = " SELECT case when  proconf in ('N') then nd_cansolnodevprv  else  nd_candev_01 end as nd_candev ,*  FROM ( "
                                + " SELECT case when (a1.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a1.nd_canvolfac ) ) > 0 then "
                                //   + " (a1.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a1.nd_canvolfac ) )  else 0 end as nd_candev_01, "
                                //+ " (a1.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a1.nd_canvolfac ) )  else (case when a3.nd_cannunrec is null then 0 else a1.nd_candev - a1.nd_canvolfac end) end as nd_candev_01, "
                                + " (a1.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a1.nd_canvolfac ) )  else (case when a3.nd_cannunrec is null then 0 else a1.nd_candev - abs(a3.nd_cannunrec) - a1.nd_canvolfac end) end as nd_candev_01, "
                                + " a1.nd_cansolnodevprv,  a1.nd_candevprv, a1.nd_cannodevprv, a1.st_devFalStkAce, a1.st_devFalStk, a.tx_obsCanRevTec, a1.nd_canRec as nd_cantotRec , "
                                + " a.tx_obsCanRevBod, a.nd_canRevBodRec as nd_canRechazada, a.nd_canRevBodAce as nd_canAceptada ,  a1.nd_canNunRec, a.nd_canRec, "
                                + " a.tx_obsCanRec,  a1.co_reg, a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a1.co_bod, a4.tx_nom, a1.nd_can, a1.st_revtec   "
                                + " ,a1.nd_canRevTecAce as nd_canRevTecAceSol, a1.nd_canRevTecRec as nd_canRevTecRecSol, "
                                + " a1.nd_canRevBodAce as nd_canRevBodAceSol, a1.nd_canRevBodRec as nd_canRevBodRecSol  "
                                + " ,a1.nd_canRecDevCli  ,a2.st_meringegrfisbod  as proconf"
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                + " , inv.tx_codalt2 "
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                + "  FROM tbm_detRecMerSolDevVen AS a  "
                                + "  INNER JOIN tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel AND a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel )  "
                                + "  INNER JOIN tbm_detmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrel and a2.co_tipdoc=a1.co_tipdocrel and a2.co_doc=a1.co_docrel and a2.co_reg=a1.co_regrel )  "
                                + "  INNER JOIN  tbm_bod AS a4 ON (a4.co_emp=a1.co_emp AND a4.co_bod=a1.co_bod)  "
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                + "  INNER JOIN  tbm_inv AS inv ON (inv.co_emp=a1.co_emp AND inv.co_itm=a1.co_itm)  "
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                + "  LEFT JOIN  tbm_detingegrmerbod AS a3 on (a3.co_emp=a1.co_emp and a3.co_loc=a1.co_locrelcon and a3.co_tipdoc=a1.co_tipdocrelcon and a3.co_doc=a1.co_docrelcon and a3.co_reg=a1.co_regrelcon ) "
                                + "  WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + "  AND  "
                                + "  a.co_tipdoc=" + txtCodTipDoc.getText() + "  AND a.co_doc=" + intCodDoc + " "
                                + " ) AS x "
                                + " order by co_reg ";
                    }

                    if (objZafParSis.getCodigoMenu() == 1898) 
                    {
                        strSql = "SELECT (a1.nd_candev - a1.nd_canvolfac) as nd_candev, a1.nd_candevprv, a1.nd_cannodevprv, a1.st_devFalStkace, a1.st_devFalStk,  a.tx_obsCanRevTec, a1.nd_canRec as nd_cantotRec, "
                                + " a.tx_obsCanRevBod, a.nd_canrevtecrec as nd_canRechazada, a.nd_canrevtecace as nd_canAceptada,  a1.nd_canNunRec, a.nd_canRec, a.tx_obsCanRec, "
                                + " a1.co_reg, a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a1.co_bod, a2.tx_nom, a1.nd_can,  a1.st_revtec  "
                                + " ,a1.nd_canRevTecAce as nd_canRevTecAceSol, a1.nd_canRevTecRec as nd_canRevTecRecSol, a1.nd_canRevBodAce as nd_canRevBodAceSol, a1.nd_canRevBodRec as nd_canRevBodRecSol "
                                + " ,a1.nd_canRecDevCli  "
                                
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                + " , inv.tx_codalt2 "
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                
                                + strAux2 + " FROM tbm_detRecMerSolDevVen AS a "
                                + " INNER JOIN tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel AND a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
                                + " INNER JOIN  tbm_bod AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_bod=a1.co_bod) "
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                + "  INNER JOIN  tbm_inv AS inv ON (inv.co_emp=a1.co_emp AND inv.co_itm=a1.co_itm)  "
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                
                                + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + "  AND "
                                + " a.co_tipdoc=" + txtCodTipDoc.getText() + " AND a.co_doc=" + intCodDoc + " order by a1.co_reg ";
                    }

                    if (objZafParSis.getCodigoMenu() == 1908) {
//   strSql=" SELECT  case when  proconf in ('S') then nd_cansolnodevprv  else  nd_candev_01 end as nd_candev,  *  from ( " +
//   " SELECT (a1.nd_candev - a1.nd_canvolfac) as nd_candev_01,  a1.nd_cansolnodevprv,  a1.nd_candevprv, a1.nd_cannodevprv, a1.st_devFalStkace, a1.st_devFalStk, a.tx_obsCanRevTec, a1.nd_canRec as nd_cantotRec , a.tx_obsCanRevBod, a.nd_canrevtecrec as nd_canRechazada, a.nd_canrevtecace as nd_canAceptada,  a1.nd_canNunRec, a.nd_canRec, a.tx_obsCanRec,  a1.co_reg, a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a1.co_bod, a2.tx_nom, a1.nd_can,  a1.st_revtec  " +
//   " ,a1.nd_canRevTecAce as nd_canRevTecAceSol, a1.nd_canRevTecRec as nd_canRevTecRecSol, a1.nd_canRevBodAce as nd_canRevBodAceSol, a1.nd_canRevBodRec as nd_canRevBodRecSol " +
//   " ,a1.nd_canRecDevCli " +
//   strAux2+" FROM tbm_detRecMerSolDevVen AS a " +
//   " INNER JOIN tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel AND a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) " +
//   " INNER JOIN  tbm_bod AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_bod=a1.co_bod) " +
//   " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+"  AND " +
//   " a.co_tipdoc="+txtCodTipDoc.getText()+" AND a.co_doc="+intCodDoc+" " +
//   " ) as x ";

                        strSql = " SELECT case when  proconf in ('N') then nd_cansolnodevprv  else  nd_candev_01 end as nd_candev ,*  FROM ( "
                                + " SELECT case when (a1.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a1.nd_canvolfac ) ) > 0 then "
                                //   + " (a1.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a1.nd_canvolfac ) )  else 0 end as nd_candev_01, "
                                + " (a1.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a1.nd_canvolfac ) )  else (case when a3.nd_cannunrec is null then 0 else a1.nd_candev - a1.nd_canvolfac end) end as nd_candev_01, "
                                + " a1.nd_cansolnodevprv,  a1.nd_candevprv, a1.nd_cannodevprv, a1.st_devFalStkAce, a1.st_devFalStk, a.tx_obsCanRevTec, a1.nd_canRec as nd_cantotRec , a.tx_obsCanRevBod, a.nd_canRevBodRec as nd_canRechazada, a.nd_canRevBodAce as nd_canAceptada ,  a1.nd_canNunRec, a.nd_canRec, "
                                + "  a.tx_obsCanRec,  a1.co_reg, a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a1.co_bod, a4.tx_nom, a1.nd_can, a1.st_revtec   "
                                + "  ,a1.nd_canRevTecAce as nd_canRevTecAceSol, a1.nd_canRevTecRec as nd_canRevTecRecSol, a1.nd_canRevBodAce as nd_canRevBodAceSol, a1.nd_canRevBodRec as nd_canRevBodRecSol  "
                                + "   ,a1.nd_canRecDevCli  ,a2.st_meringegrfisbod  as proconf"
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                + " , inv.tx_codalt2 "
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                
                                + "  FROM tbm_detRecMerSolDevVen AS a  "
                                + "  INNER JOIN tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel AND a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel )  "
                                + "  INNER JOIN tbm_detmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrel and a2.co_tipdoc=a1.co_tipdocrel and a2.co_doc=a1.co_docrel and a2.co_reg=a1.co_regrel )  "
                                + "  INNER JOIN  tbm_bod AS a4 ON (a4.co_emp=a1.co_emp AND a4.co_bod=a1.co_bod)  "
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                + "  INNER JOIN  tbm_inv AS inv ON (inv.co_emp=a1.co_emp AND inv.co_itm=a1.co_itm)  "
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/                                
                                + "  LEFT JOIN  tbm_detingegrmerbod AS a3 on (a3.co_emp=a1.co_emp and a3.co_loc=a1.co_locrelcon and a3.co_tipdoc=a1.co_tipdocrelcon and a3.co_doc=a1.co_docrelcon and a3.co_reg=a1.co_regrelcon ) "
                                + "  WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + "  AND  "
                                + "  a.co_tipdoc=" + txtCodTipDoc.getText() + "  AND a.co_doc=" + intCodDoc + " "
                                + " ) AS x "
                                + " order by co_reg ";
                    }

                    if (objZafParSis.getCodigoMenu() == 1928) {
                        strSql = "SELECT  (a1.nd_candev - a1.nd_canvolfac) as nd_candev,  a1.nd_candevprv, a1.nd_cannodevprv,  a1.st_devFalStkace, a1.st_devFalStk, a.tx_obsCanRevTec, a1.nd_canRec as nd_cantotRec , a.tx_obsCanRevBod, a.nd_canRevBodRec as nd_canRechazada, a.nd_canRevBodAce as nd_canAceptada,  a1.nd_canNunRec, a.nd_canRec, a.tx_obsCanRec,  a1.co_reg, a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a1.co_bod, a2.tx_nom, a1.nd_can,  a1.st_revtec  "
                                + " ,a1.nd_canRevTecAce as nd_canRevTecAceSol, a1.nd_canRevTecRec as nd_canRevTecRecSol, a1.nd_canRevBodAce as nd_canRevBodAceSol, a1.nd_canRevBodRec as nd_canRevBodRecSol "
                                + "  ,a1.nd_canRecDevCli "
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                + " , inv.tx_codalt2 "
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/                                
                                + strAux2 + "  FROM tbm_detRecMerSolDevVen AS a "
                                + " INNER JOIN tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel AND a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
                                + " INNER JOIN  tbm_bod AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_bod=a1.co_bod) "
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                + "  INNER JOIN  tbm_inv AS inv ON (inv.co_emp=a1.co_emp AND inv.co_itm=a1.co_itm)  "
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/                                                                
                                
                                + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + "  AND "
                                + " a.co_tipdoc=" + txtCodTipDoc.getText() + " AND a.co_doc=" + intCodDoc + " order by a1.co_reg ";;
                    }
                    System.out.println("ZafVen27.cargarDetIns: " + strSql);

                    rstLoc = stmLoc.executeQuery(strSql);
                    while (rstLoc.next()) {
                        java.util.Vector vecReg = new java.util.Vector();

                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
                        vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                        /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                        vecReg.add(INT_TBL_COD3LET, rstLoc.getString("tx_codalt2"));
                        /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                        vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                        vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                        vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                        vecReg.add(INT_TBL_NOMBOD, rstLoc.getString("tx_nom"));
                        vecReg.add(INT_TBL_CANVEN, rstLoc.getString("nd_can"));
                        vecReg.add(INT_TBL_CANDEV, rstLoc.getString("nd_candev"));
                        vecReg.add(INT_TBL_FALSTKFIC, ((rstLoc.getString("st_devFalStk").equals("S")) ? true : false));
                        vecReg.add(INT_TBL_REVTEC, ((rstLoc.getString("st_revtec").equals("S")) ? true : false));
                        vecReg.add(INT_TBL_CANTOTREC, rstLoc.getString("nd_cantotRec"));
                        vecReg.add(INT_TBL_CANTOTNUNREC, rstLoc.getString("nd_canNunRec"));

                        vecReg.add(INT_TBL_CAN_TEC_REV_ACE, rstLoc.getString("nd_canRevTecAceSol"));
                        vecReg.add(INT_TBL_CAN_TEC_REV_REH, rstLoc.getString("nd_canRevTecRecSol"));
                        vecReg.add(INT_TBL_CAN_BOD_REV_ACE, rstLoc.getString("nd_canRevBodAceSol"));
                        vecReg.add(INT_TBL_CAN_BOD_REV_REH, rstLoc.getString("nd_canRevBodRecSol"));

                        vecReg.add(INT_TBL_CANREC, rstLoc.getString("nd_canRec"));
                        vecReg.add(INT_TBL_OBSCANREC, rstLoc.getString("tx_obsCanRec"));
                        vecReg.add(INT_TBL_CANACE, rstLoc.getString("nd_canAceptada"));
                        vecReg.add(INT_TBL_CANRCH, rstLoc.getString("nd_canRechazada"));
                        vecReg.add(INT_TBL_OBSREVCONFING, rstLoc.getString("tx_obsCanRevBod"));
                        vecReg.add(INT_TBL_EST_REC_TEC, rstLoc.getString("st_revtec"));
                        vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                        vecReg.add(INT_TBL_CANTOTNUNREC_ORI, rstLoc.getString("nd_canNunRec"));
                        vecReg.add(INT_TBL_CANREC_ORI, rstLoc.getString("nd_canRec"));
                        vecReg.add(INT_TBL_CANACE_ORI, rstLoc.getString("nd_canAceptada"));
                        vecReg.add(INT_TBL_CANRCH_ORI, rstLoc.getString("nd_canRechazada"));
                        vecReg.add(INT_TBL_OBSREVTEC, rstLoc.getString("tx_obsCanRevTec"));
                        vecReg.add(INT_TBL_CANTOTDEVCLI, rstLoc.getString("nd_canRecDevCli"));
                        vecReg.add(INT_TBL_CANDEVCLI_ORI, rstLoc.getString("nd_canRec"));
                        vecReg.add(INT_TBL_CANDEVCLI, rstLoc.getString("nd_canRec"));
                        vecReg.add(INT_TBL_OBSDEVCLI, rstLoc.getString("tx_obsCanRec"));
                        vecReg.add(INT_TBL_FALSTKFICACE, ((rstLoc.getString("st_devFalStkAce").equals("S")) ? true : false));
                        vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("proconf"));

                        vecReg.add(INT_TBL_CANSDEVPRV, rstLoc.getString("nd_candevprv"));
                        vecReg.add(INT_TBL_CANNDEVPRV, rstLoc.getString("nd_cannodevprv"));

                        vecData.add(vecReg);
                    }
                    objTblMod.setData(vecData);
                    tblDat.setModel(objTblMod);
                    rstLoc.close();
                    rstLoc = null;

                    if (objZafParSis.getCodigoMenu() == 1888)
                    {
                        double dlbCanTotRev = 0;
                        double dlbCanTotRec = 0;
                        double dlbSumTotRecAceReh = 0;
                        double dlbCanTotDev = 0;
                        String strRecBodMerDev = "N";
                        double dlbSumTotMerRec = 0;
                        String strExiMerRec = "N";
                        String strRecMerDev = "N";

                        for (int i = 0; i < tblDat.getRowCount(); i++) 
                        {

                            dlbCanTotRev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTREC).toString())), 6);
                            dlbCanTotRev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString())), 6);
                            dlbCanTotRec += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTREC).toString())), 6);

                            dlbSumTotRecAceReh += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_ACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_ACE).toString())), 6);
                            dlbSumTotRecAceReh += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH).toString())), 6);

                            dlbCanTotDev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANDEV) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANDEV).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANDEV).toString())), 6);

                            dlbSumTotMerRec += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH).toString())), 6);
                            dlbSumTotMerRec += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH).toString())), 6);
                        }
                        //System.out.println(" Paso 1 "+ dlbCanTotDev +"  -- "+ dlbCanTotRev );  
                        if (dlbCanTotDev == dlbCanTotRev) {
                            strRecMerDev = "S";
                            // System.out.println(" Paso 2 "+ dlbCanTotRec +"  -- "+ dlbSumTotRecAceReh );
                            if (dlbCanTotRec == dlbSumTotRecAceReh) {
                                strRecBodMerDev = "S";
                            }
                        }

                        if (dlbSumTotMerRec > 0) {
                            strExiMerRec = "S";
                        }

                        strSql = "UPDATE tbm_cabsoldevven SET st_RevBodMerDev='" + strRecBodMerDev + "', st_ExiMerRec='" + strExiMerRec + "', st_recmerdev='" + strRecMerDev + "'  WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText();
                        stmLoc.executeUpdate(strSql);
                        conn.commit();

                    }

                    if (objZafParSis.getCodigoMenu() == 1908) {

                        double dlbCanTotRev = 0;
                        double dlbCanTotRec = 0;
                        double dlbSumTotRecAceReh = 0;
                        double dlbCanTotDev = 0;
                        String strRecBodMerDev = "N";
                        double dlbSumTotMerRec = 0;
                        String strExiMerRec = "N";

                        for (int i = 0; i < tblDat.getRowCount(); i++) {

                            dlbCanTotRev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTREC).toString())), 6);
                            dlbCanTotRev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString())), 6);
                            dlbCanTotRec += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTREC).toString())), 6);

                            dlbSumTotRecAceReh += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_ACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_ACE).toString())), 6);
                            dlbSumTotRecAceReh += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH).toString())), 6);

                            dlbCanTotDev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANDEV) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANDEV).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANDEV).toString())), 6);

                            dlbSumTotMerRec += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH).toString())), 6);
                            dlbSumTotMerRec += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH).toString())), 6);
                        }
                        //System.out.println(" Paso 1 "+ dlbCanTotDev +"  -- "+ dlbCanTotRev );  
                        if (dlbCanTotDev == dlbCanTotRev) {
                            // System.out.println(" Paso 2 "+ dlbCanTotRec +"  -- "+ dlbSumTotRecAceReh );
                            if (dlbCanTotRec == dlbSumTotRecAceReh) {
                                strRecBodMerDev = "S";
                            }
                        }

                        if (dlbSumTotMerRec > 0) {
                            strExiMerRec = "S";
                        }

                        strSql = "UPDATE tbm_cabsoldevven SET st_RevBodMerDev='" + strRecBodMerDev + "', st_ExiMerRec='" + strExiMerRec + "'  WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText();
                        stmLoc.executeUpdate(strSql);
                        conn.commit();

                    }

                    if (objZafParSis.getCodigoMenu() == 1898) {
                        double dlbSumCanTotTecRec = 0;
                        double dlbCanTotTecRec = 0;
                        String strRecTecMerDev = "N";
                        double dlbSumTotMerRec = 0;
                        String strExiMerRec = "N";


                        for (int i = 0; i < tblDat.getRowCount(); i++) {
                            dlbSumCanTotTecRec += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_ACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_ACE).toString())), 6);
                            dlbSumCanTotTecRec += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH).toString())), 6);

                            dlbCanTotTecRec += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTREC).toString())), 6);

                            dlbSumTotMerRec += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH).toString())), 6);
                            dlbSumTotMerRec += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH).toString())), 6);

                        }
                        if (dlbCanTotTecRec == dlbSumCanTotTecRec) {
                            strRecTecMerDev = "S";
                        }

                        if (dlbSumTotMerRec > 0) {
                            strExiMerRec = "S";
                        }

                        strSql = "UPDATE tbm_cabsoldevven SET  st_revtecmerdev='" + strRecTecMerDev + "', st_ExiMerRec='" + strExiMerRec + "'  WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText();
                        stmLoc.executeUpdate(strSql);
                        conn.commit();
                    }

                    if (objZafParSis.getCodigoMenu() == 1928)
                    {
                        double dlbCanTotDevCli = 0;
                        double dlbCanTotNumRec = 0;
                        String strMerRecDevCli = "N";
                        double dlbSumTotMerRec = 0;
                        String strExiMerRec = "N";

                        for (int i = 0; i < tblDat.getRowCount(); i++) {
                            dlbCanTotDevCli += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTDEVCLI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANTOTDEVCLI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTDEVCLI).toString())), 6);
                            //dlbCanTotNumRec += objUti.redondear( (tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString())) ,6 );  
                            dlbCanTotNumRec += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH).toString())), 6);

                            dlbSumTotMerRec += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH).toString())), 6);
                            dlbSumTotMerRec += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_REH).toString())), 6);

                        }

                        if (dlbCanTotDevCli == dlbCanTotNumRec) {
                            strMerRecDevCli = "S";
                        }

                        if (dlbSumTotMerRec > 0) {
                            strExiMerRec = "S";
                        }

                        strSql = "UPDATE tbm_cabsoldevven SET st_MerRecDevCli='" + strMerRecDevCli + "', st_ExiMerRec='" + strExiMerRec + "'   WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText();
                        stmLoc.executeUpdate(strSql);
                        conn.commit();
                    }

                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean insertarCab(java.sql.Connection conn, int intCodDoc, String strTipRev, String strRevTec, String strExiMerRec, String strRecMerDev, String strRecTecMerDev, String strRecBodMerDev)
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            String strFecDoc = "";
            String strFecSis = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    //************  PERMITE SABER SI EL NUMERO DE Devolucion ESTA DUPLICADO  *****************/ 
                    strSql = "select count(ne_numdoc) as num FROM tbm_cabRecMerSolDevVen WHERE "
                            + " co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + "and co_tipdoc=" + txtCodTipDoc.getText() + " and ne_numdoc=" + txtNumDoc.getText();
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        if (rstLoc.getInt("num") >= 1) {
                            //JOptionPane oppMsg = new JOptionPane();
                            String strTit, strMsg;
                            strTit = "Mensaje del sistema Zafiro";
                            strMsg = " No. de Solicitud ya existe... ?";
                            JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE, null);
                            blnRes = true;
                        }
                    }
                    rstLoc.close();
                    rstLoc = null;
                    if (blnRes) {
                        return false;
                    }
                    blnRes = false;
                    //***********************************************************************************************/
                    strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";
                    strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());

                    strSql = "INSERT INTO tbm_cabRecMerSolDevVen( co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc,  tx_tiprev, "
                            + " co_usrres, co_locrel, co_tipdocrel, co_docrel,  co_mnu, st_imp, tx_obs1, tx_obs2, st_reg, fe_ing, co_usring  ) "
                            + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                            + " ,'" + strFecDoc + "', " + txtNumDoc.getText() + ", '" + strTipRev + "',  " + txtCodRes.getText() + ", " + txtCodLoc.getText() + ", "
                            + " " + txtCodTipDocSol.getText() + ", " + txtCodDocSol.getText() + ", " + objZafParSis.getCodigoMenu() + ", 'N', '" + txtObs1.getText() + "', '" + txtObs2.getText() + "' "
                            + " , 'A', '" + strFecSis + "', " + objZafParSis.getCodigoUsuario() + " )";
                    //System.out.println(" > "+ strSql );
                    stmLoc.executeUpdate(strSql);

                    strSql = "UPDATE tbm_cabtipdoc SET ne_ultdoc=" + txtNumDoc.getText() + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                            + " AND co_loc=" + objZafParSis.getCodigoLocal() + " AND co_tipdoc=" + txtCodTipDoc.getText();
                    stmLoc.executeUpdate(strSql);

                    if (objZafParSis.getCodigoMenu() == 1888) { //"Recepción de mercadería a devolver sujeta a revisión..."
        /*st_revtec Estado de Revisión Técnica S=Si;N=No */
                        strSql = "UPDATE tbm_cabsoldevven SET  st_revtec='" + strRevTec + "'  WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText();

                    }
                    if (objZafParSis.getCodigoMenu() == 1898) {//"Revisión técnica de la mercadería devuelta..."
        /*st_RevTecMerDev Estado de revisión técnica de la mercadería devuelta S=Si;N=No*/
                        strSql = "UPDATE tbm_cabsoldevven SET  st_RevTecMerDev ='" + strRecTecMerDev + "' WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText();
                    }
                    stmLoc.executeUpdate(strSql);


                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean insertarDet(java.sql.Connection conn, int intCodDoc) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.Statement stm_remota;
            String strSql = "", strSqlFic = "";
            int intNumReg = 0;
            try 
            {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    for (int i = 0; i < tblDat.getRowCount(); i++) {
                        intNumReg++;

                        String strEstFisBod = (tblDat.getValueAt(i, INT_TBL_IEBODFIS) == null ? "" : tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());

                        if (objZafParSis.getCodigoMenu() == 1888) {
                            strSql = "INSERT INTO tbm_detRecMerSolDevVen( co_emp, co_loc, co_tipdoc, co_doc,  co_reg, "
                                    + " co_locrel,  co_tipdocrel , co_docrel, co_regrel, nd_canrec, tx_obscanrec, tx_obsCanRevBod, nd_canRevBodAce, nd_canRevBodRec ) "
                                    + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                    + " ," + intNumReg + ", " + txtCodLoc.getText() + ", " + txtCodTipDocSol.getText() + ", " + txtCodDocSol.getText() + " "
                                    + " ," + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + ", " + (tblDat.getValueAt(i, INT_TBL_CANREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANREC).toString())) + " "
                                    + " ,'" + (tblDat.getValueAt(i, INT_TBL_OBSCANREC) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSCANREC)) + "'"
                                    + " ,'" + (tblDat.getValueAt(i, INT_TBL_OBSREVCONFING) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSREVCONFING)) + "', "
                                    + " " + (tblDat.getValueAt(i, INT_TBL_CANACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE).toString())) + " "
                                    + " ," + (tblDat.getValueAt(i, INT_TBL_CANRCH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH).toString())) + " "
                                    + " )";
                        }
                        if (objZafParSis.getCodigoMenu() == 1898) {
                            strSql = "INSERT INTO tbm_detRecMerSolDevVen( co_emp, co_loc, co_tipdoc, co_doc,  co_reg, "
                                    + " co_locrel,  co_tipdocrel , co_docrel, co_regrel, nd_canrec, tx_obscanrec,   tx_obscanrevtec, nd_canrevtecace, nd_canrevtecrec ) "
                                    + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                    + " ," + intNumReg + ", " + txtCodLoc.getText() + ", " + txtCodTipDocSol.getText() + ", " + txtCodDocSol.getText() + " "
                                    + " ," + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + ", " + (tblDat.getValueAt(i, INT_TBL_CANREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANREC).toString())) + " "
                                    + " ,'" + (tblDat.getValueAt(i, INT_TBL_OBSCANREC) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSCANREC)) + "'"
                                    + " ,'" + (tblDat.getValueAt(i, INT_TBL_OBSREVTEC) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSREVTEC)) + "', "
                                    + " " + (tblDat.getValueAt(i, INT_TBL_CANACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE).toString())) + " "
                                    + " ," + (tblDat.getValueAt(i, INT_TBL_CANRCH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH).toString())) + " "
                                    + " )";
                        }
                        if (objZafParSis.getCodigoMenu() == 1908) {
                            /*strSql="INSERT INTO tbm_detRecMerSolDevVen( co_emp, co_loc, co_tipdoc, co_doc,  co_reg, " +
                             " co_locrel,  co_tipdocrel , co_docrel, co_regrel, nd_canrec, tx_obscanrec,   tx_obscanrevtec,tx_obsCanRevBod,  nd_canrevtecace, nd_canrevtecrec  ) " +
                             " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" " +  
                             " ,"+intNumReg+", "+txtCodLoc.getText()+", "+txtCodTipDocSol.getText()+", "+txtCodDocSol.getText()+" " +
                             " ,"+tblDat.getValueAt(i, INT_TBL_CODREG).toString()+", "+(tblDat.getValueAt(i, INT_TBL_CANREC)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANREC).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANREC).toString() ))+" " +
                             " ,'"+(tblDat.getValueAt(i, INT_TBL_OBSCANREC)==null?"":tblDat.getValueAt(i, INT_TBL_OBSCANREC)) +"'" +
                             " ,'"+(tblDat.getValueAt(i, INT_TBL_OBSREVTEC)==null?"":tblDat.getValueAt(i, INT_TBL_OBSREVTEC)) +"' " +
                             " ,'"+(tblDat.getValueAt(i, INT_TBL_OBSREVCONFING)==null?"":tblDat.getValueAt(i, INT_TBL_OBSREVCONFING)) +"', " +
                             " "+(tblDat.getValueAt(i, INT_TBL_CANACE)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANACE).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANACE).toString() ))+" " +
                             " ,"+(tblDat.getValueAt(i, INT_TBL_CANRCH)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANRCH).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANRCH).toString() ))+" " +
                             " )";*/

                            strSql = "INSERT INTO tbm_detRecMerSolDevVen( co_emp, co_loc, co_tipdoc, co_doc,  co_reg, "
                                    + " co_locrel,  co_tipdocrel , co_docrel, co_regrel, nd_canrec, tx_obscanrec,   tx_obscanrevtec,tx_obsCanRevBod,  nd_canrevtecace, nd_canrevtecrec  ) "
                                    + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                    + " ," + intNumReg + ", " + txtCodLoc.getText() + ", " + txtCodTipDocSol.getText() + ", " + txtCodDocSol.getText() + " "
                                    + " ," + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + ", " + (tblDat.getValueAt(i, INT_TBL_CANREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANREC).toString())) + " "
                                    + " ,'" + (tblDat.getValueAt(i, INT_TBL_OBSCANREC) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSCANREC)) + "'"
                                    + " ,'" + (tblDat.getValueAt(i, INT_TBL_OBSREVTEC) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSREVTEC)) + "' "
                                    + " ,'" + (tblDat.getValueAt(i, INT_TBL_OBSREVCONFING) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSREVCONFING)) + "', "
                                    + " " + (tblDat.getValueAt(i, INT_TBL_CANACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE).toString())) + " "
                                    + " ," + (tblDat.getValueAt(i, INT_TBL_CANRCH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH).toString())) + " "
                                    + " )";
                        }

                        if (objZafParSis.getCodigoMenu() == 1928) {
                            strSql = "INSERT INTO tbm_detRecMerSolDevVen( co_emp, co_loc, co_tipdoc, co_doc,  co_reg, "
                                    + " co_locrel,  co_tipdocrel , co_docrel, co_regrel, nd_canrec, tx_obscanrec ) "
                                    + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                    + " ," + intNumReg + ", " + txtCodLoc.getText() + ", " + txtCodTipDocSol.getText() + ", " + txtCodDocSol.getText() + " "
                                    + " ," + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + ", " + (tblDat.getValueAt(i, INT_TBL_CANDEVCLI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANDEVCLI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANDEVCLI).toString())) + " "
                                    + " ,'" + (tblDat.getValueAt(i, INT_TBL_OBSDEVCLI) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSDEVCLI)) + "' )";
                        }

                        System.out.println("ZafVen27.insertarDet: " + strSql);
                        stmLoc.executeUpdate(strSql);

                        if (objZafParSis.getCodigoMenu() == 1888) 
                        {
                            strSql = "UPDATE tbm_detsoldevven SET st_revtec='" + (tblDat.getValueAt(i, INT_TBL_REVTEC) == null ? "N" : (tblDat.getValueAt(i, INT_TBL_REVTEC).toString().equals("true") ? "S" : "N")) + "' "
                                    + " ,nd_canrec=nd_canrec+" + (tblDat.getValueAt(i, INT_TBL_CANREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANREC).toString())) + ",  "
                                    + " nd_canNunRec=" + (tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString())) + " "
                                    + " ,nd_canRevBodAce=nd_canRevBodAce+" + (tblDat.getValueAt(i, INT_TBL_CANACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE).toString())) + " "
                                    + " ,nd_canRevBodRec=nd_canRevBodRec+" + (tblDat.getValueAt(i, INT_TBL_CANRCH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH).toString())) + " "
                                    + " ,st_devFalStkAce='" + (tblDat.getValueAt(i, INT_TBL_FALSTKFICACE) == null ? "N" : (tblDat.getValueAt(i, INT_TBL_FALSTKFICACE).toString().equals("true") ? "S" : "N")) + "' "
                                    + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                    + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText() + " AND co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();

                            double dblCan = objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE).toString())), 4);
                            dblCan += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANRCH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH).toString())), 4);


                            double dlbCanTotNunRec = objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC) == null ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString()), 2);
                            double dlbCanTotNunRecOri = objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC_ORI) == null ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC_ORI).toString()), 2);

                            if (dlbCanTotNunRec != dlbCanTotNunRecOri) {
                                if (dlbCanTotNunRec > dlbCanTotNunRecOri) {
                                    dblCan += +(dlbCanTotNunRec - dlbCanTotNunRecOri);
                                }
                                if (dlbCanTotNunRec < dlbCanTotNunRecOri) {
                                    dblCan += -(dlbCanTotNunRecOri - dlbCanTotNunRec);
                                }
                            }

                            strSqlFic = "";
                            if (strEstFisBod.equals("N")) {
                                if (strMerIngEgr.equals("S")) {
                                    strSqlFic = " UPDATE tbm_invbod SET nd_caningbod=nd_caningbod-" + dblCan + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                    stmLoc.executeUpdate(strSqlFic);
                                    if (conRemGlo != null) {
                                        stm_remota = conRemGlo.createStatement();
                                        stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                        stm_remota.close();
                                        stm_remota = null;
                                    }
                                }
                            }

                        }
                        if (objZafParSis.getCodigoMenu() == 1898) 
                        {
                            strSql = "UPDATE tbm_detsoldevven SET "
                                    + " nd_canrevtecace=nd_canrevtecace+" + (tblDat.getValueAt(i, INT_TBL_CANACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE).toString())) + " "
                                    + " ,nd_canrevtecrec=nd_canrevtecrec+" + (tblDat.getValueAt(i, INT_TBL_CANRCH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH).toString())) + " "
                                    + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                    + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText() + " AND co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                        }
                        if (objZafParSis.getCodigoMenu() == 1908)
                        {
                            strSql = "UPDATE tbm_detsoldevven SET "
                                    + " nd_canRevBodAce=nd_canRevBodAce+" + (tblDat.getValueAt(i, INT_TBL_CANACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE).toString())) + " "
                                    + " ,nd_canRevBodRec=nd_canRevBodRec+" + (tblDat.getValueAt(i, INT_TBL_CANRCH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH).toString())) + " "
                                    + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                    + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText() + " AND co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();

                            double dblCan = objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE).toString())), 4);
                            dblCan += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANRCH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH).toString())), 4);

                            double dlbCanTotNunRec = objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC) == null ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString()), 2);
                            double dlbCanTotNunRecOri = objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC_ORI) == null ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC_ORI).toString()), 2);

                            if (dlbCanTotNunRec != dlbCanTotNunRecOri) {
                                if (dlbCanTotNunRec > dlbCanTotNunRecOri) {
                                    dblCan += +(dlbCanTotNunRec - dlbCanTotNunRecOri);
                                }
                                if (dlbCanTotNunRec < dlbCanTotNunRecOri) {
                                    dblCan += -(dlbCanTotNunRecOri - dlbCanTotNunRec);
                                }
                            }

                            strSqlFic = "";
                            if (strEstFisBod.equals("N")) {
                                if (strMerIngEgr.equals("S")) {
                                    strSqlFic = " UPDATE tbm_invbod SET nd_caningbod=nd_caningbod-" + dblCan + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                    stmLoc.executeUpdate(strSqlFic);
                                    if (conRemGlo != null) {
                                        stm_remota = conRemGlo.createStatement();
                                        stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                        stm_remota.close();
                                        stm_remota = null;
                                    }
                                }
                            }

                        }

                        if (objZafParSis.getCodigoMenu() == 1928) 
                        {
                            strSql = "UPDATE tbm_detsoldevven SET "
                                    + " nd_canRecDevCli=nd_canRecDevCli+" + (tblDat.getValueAt(i, INT_TBL_CANDEVCLI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANDEVCLI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANDEVCLI).toString())) + " "
                                    + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                    + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText() + " AND co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                        }

                        stmLoc.executeUpdate(strSql);
                    }

                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean validarDat() 
        {
            boolean blnRes = true;
            if (txtCodTipDoc.getText().trim().equals("")) {
                MensajeInf("Ingrese tipo el documento");
                return false;
            }
            return blnRes;
        }

        @Override
        public boolean modificar() 
        {
            boolean blnRes = false;
            java.sql.Connection conn;
            int intCodDoc = 0;
            String strTipRev = "B";
            String strRevTec = "N";
            String strExiMerRec = "N";
            String strRecMerDev = "N";
            String strRecTecMerDev = "N";
            String strRecBodMerDev = "N";
            try {

                strAux = objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado")) {
                    MensajeInf("El documento está ELIMINADO.\nNo es posible modifcar un documento eliminado.");
                    return false;
                }
                if (strAux.equals("Anulado")) {
                    MensajeInf("El documento ya está ANULADO.\nNo es posible modifcar un documento anulado.");
                    return false;
                }


                if (validaCampos()) {

                    conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                    if (conn != null) {
                        conn.setAutoCommit(false);


                        if (!abrirConRem()) {
                            return false;
                        }


                        if (!verificaAnuEli(conn)) {
                            MensajeInf("El documento ya tiene Nota de Credito.\nNo es posible modificar el documento.");
                            blnRes = true;
                        }


                        if (objZafParSis.getCodigoMenu() == 1888) {
                            if (!verificaAnuEliDoc(conn)) {
                                MensajeInf("NO SE PUEDE ANULAR PORQUE HAY UNA REVICIÓN TECNICA. ");
                                blnRes = true;
                            }
                        }


                        if (!blnRes) {


                            intCodDoc = Integer.parseInt(txtCodDoc.getText());

                            double dlbCanTotRevTec = 0;
                            double dlbSumTotRecAceReh = 0;
                            double dlbCanTotDev = 0;
                            double dlbSumTotDev = 0;

                            double dlbCanTotRecTecDev = 0;
                            double dlbSumTotRecTecDev = 0;

                            for (int i = 0; i < tblDat.getRowCount(); i++) {

                                dlbCanTotRevTec += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_ACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_ACE).toString())), 6);
                                dlbSumTotRecAceReh += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE).toString())), 6);
                                dlbSumTotRecAceReh += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANRCH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH).toString())), 6);

                                dlbSumTotDev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC).toString())), 6);
                                dlbSumTotDev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANREC).toString())), 6);
                                dlbCanTotDev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANDEV) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANDEV).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANDEV).toString())), 6);

                                dlbSumTotRecTecDev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE).toString())), 6);
                                dlbSumTotRecTecDev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANRCH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH).toString())), 6);
                                dlbSumTotRecTecDev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_ACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_ACE).toString())), 6);
                                dlbSumTotRecTecDev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_TEC_REV_REH).toString())), 6);

                                dlbCanTotRecTecDev += objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANTOTREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANTOTREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANTOTREC).toString())), 6);


                                if (tblDat.getValueAt(i, INT_TBL_REVTEC) != null) {
                                    if (tblDat.getValueAt(i, INT_TBL_REVTEC).toString().equals("true")) {
                                        strRevTec = "S";
                                    }
                                }


                                if (tblDat.getValueAt(i, INT_TBL_CANRCH) != null) {
                                    if (objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH).toString()), 6) > 0.00) {
                                        strExiMerRec = "S";
                                    }
                                }
                            }


                            if (dlbCanTotRevTec == dlbSumTotRecAceReh) {
                                strRecBodMerDev = "S";
                            }


                            if (dlbCanTotDev == dlbSumTotDev) {
                                strRecMerDev = "S";
                            }

                            if (objZafParSis.getCodigoMenu() == 1898) {
                                strTipRev = "T";
                                if (dlbCanTotRecTecDev == dlbSumTotRecTecDev) {
                                    strRecTecMerDev = "S";
                                }
                            }

                            if (modificarCab(conn, intCodDoc, strTipRev, strRevTec, strExiMerRec, strRecMerDev, strRecTecMerDev)) {
                                if (modificarDet(conn, intCodDoc)) {
                                    if (conRemGlo != null) {
                                        conRemGlo.commit();
                                    }
                                    conn.commit();
                                    blnRes = true;
                                    cargarDetIns(conn, intCodDoc);
                                } else {
                                    conn.rollback();
                                }
                            } else {
                                conn.rollback();
                            }
                        } else {
                            blnRes = false;
                        }

                        if (conRemGlo != null) {
                            conRemGlo.close();
                            conRemGlo = null;
                        }
                        conn.close();
                        conn = null;
                    }

                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean modificarDet(java.sql.Connection conn, int intCodDoc) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.Statement stm_remota;
            String strSql = "";
            String strSqlAux = "", strSqlFic = "";
            int intNumReg = 0;
            double dlbCanRecOri = 0.00;
            double dlbCanRec = 0.00;
            double dlbCanTotNunRecOri = 0.00;
            double dlbCanTotNunRec = 0.00;
            double dlbCanAce = 0.00;
            double dlbCanAceOri = 0.00;
            double dlbCanRech = 0.00;
            double dlbCanRechOri = 0.00;
            double dlbCanDevCli = 0;
            double dlbCanDevCli_Ori = 0;
            int intCelSel = 0;

            try 
            {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "DELETE FROM tbm_detRecMerSolDevVen WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " AND co_tipdoc=" + txtCodTipDoc.getText() + "  AND co_doc=" + intCodDoc;
                    stmLoc.executeUpdate(strSql);

                    for (int i = 0; i < tblDat.getRowCount(); i++) {
                        intNumReg++;

                        String strEstFisBod = (tblDat.getValueAt(i, INT_TBL_IEBODFIS) == null ? "" : tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());

                        if (objZafParSis.getCodigoMenu() == 1888) {
                            strSql = "INSERT INTO tbm_detRecMerSolDevVen( co_emp, co_loc, co_tipdoc, co_doc,  co_reg, "
                                    + " co_locrel,  co_tipdocrel , co_docrel, co_regrel, nd_canrec, tx_obscanrec, tx_obsCanRevBod, nd_canRevBodAce, nd_canRevBodRec ) "
                                    + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                    + " ," + intNumReg + ", " + txtCodLoc.getText() + ", " + txtCodTipDocSol.getText() + ", " + txtCodDocSol.getText() + " "
                                    + " ," + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + ", " + (tblDat.getValueAt(i, INT_TBL_CANREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANREC).toString())) + " "
                                    + " ,'" + (tblDat.getValueAt(i, INT_TBL_OBSCANREC) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSCANREC).toString()) + "'"
                                    + " ,'" + (tblDat.getValueAt(i, INT_TBL_OBSREVCONFING) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSREVCONFING).toString()) + "', "
                                    + " " + (tblDat.getValueAt(i, INT_TBL_CANACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE).toString())) + " "
                                    + " ," + (tblDat.getValueAt(i, INT_TBL_CANRCH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH).toString())) + " "
                                    + " )";
                        }
                        if (objZafParSis.getCodigoMenu() == 1898) {
                            strSql = "INSERT INTO tbm_detRecMerSolDevVen( co_emp, co_loc, co_tipdoc, co_doc,  co_reg, "
                                    + " co_locrel,  co_tipdocrel , co_docrel, co_regrel, nd_canrec, tx_obscanrec,   tx_obscanrevtec, nd_canrevtecace, nd_canrevtecrec ) "
                                    + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                    + " ," + intNumReg + ", " + txtCodLoc.getText() + ", " + txtCodTipDocSol.getText() + ", " + txtCodDocSol.getText() + " "
                                    + " ," + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + ", " + (tblDat.getValueAt(i, INT_TBL_CANREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANREC).toString())) + " "
                                    + " ,'" + (tblDat.getValueAt(i, INT_TBL_OBSCANREC) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSCANREC).toString()) + "'"
                                    + " ,'" + (tblDat.getValueAt(i, INT_TBL_OBSREVTEC) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSREVTEC).toString()) + "', "
                                    + " " + (tblDat.getValueAt(i, INT_TBL_CANACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE).toString())) + " "
                                    + " ," + (tblDat.getValueAt(i, INT_TBL_CANRCH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH).toString())) + " "
                                    + " )";
                        }
                        if (objZafParSis.getCodigoMenu() == 1908) {
                            strSql = "INSERT INTO tbm_detRecMerSolDevVen( co_emp, co_loc, co_tipdoc, co_doc,  co_reg, "
                                    + " co_locrel,  co_tipdocrel , co_docrel, co_regrel, nd_canrec, tx_obscanrec,   tx_obscanrevtec, tx_obsCanRevBod,  nd_canrevtecace, nd_canrevtecrec ) "
                                    + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                    + " ," + intNumReg + ", " + txtCodLoc.getText() + ", " + txtCodTipDocSol.getText() + ", " + txtCodDocSol.getText() + " "
                                    + " ," + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + ", " + (tblDat.getValueAt(i, INT_TBL_CANREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANREC).toString())) + " "
                                    + " ,'" + (tblDat.getValueAt(i, INT_TBL_OBSCANREC) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSCANREC).toString()) + "'"
                                    + " ,'" + (tblDat.getValueAt(i, INT_TBL_OBSREVTEC) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSREVTEC).toString()) + "', "
                                    + " '" + (tblDat.getValueAt(i, INT_TBL_OBSREVCONFING) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSREVCONFING).toString()) + "', "
                                    + " " + (tblDat.getValueAt(i, INT_TBL_CANACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE).toString())) + " "
                                    + " ," + (tblDat.getValueAt(i, INT_TBL_CANRCH) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRCH).toString())) + " "
                                    + " )";
                        }

                        if (objZafParSis.getCodigoMenu() == 1928) {
                            strSql = "INSERT INTO tbm_detRecMerSolDevVen( co_emp, co_loc, co_tipdoc, co_doc,  co_reg, "
                                    + " co_locrel,  co_tipdocrel , co_docrel, co_regrel, nd_canrec, tx_obscanrec ) "
                                    + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                    + " ," + intNumReg + ", " + txtCodLoc.getText() + ", " + txtCodTipDocSol.getText() + ", " + txtCodDocSol.getText() + " "
                                    + " ," + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + ", " + (tblDat.getValueAt(i, INT_TBL_CANDEVCLI) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANDEVCLI).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANDEVCLI).toString())) + " "
                                    + " ,'" + (tblDat.getValueAt(i, INT_TBL_OBSDEVCLI) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSDEVCLI)) + "' )";
                        }

                        //System.out.println(">>"+ strSql );
                        stmLoc.executeUpdate(strSql);

                        intCelSel = i;
                        dlbCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC).toString()), 2);
                        dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANREC_ORI).toString()), 2);
                        dlbCanTotNunRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC).toString()), 2);
                        dlbCanTotNunRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC_ORI).toString()), 2);
                        dlbCanAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE).toString()), 2);
                        dlbCanAceOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANACE_ORI).toString()), 2);
                        dlbCanRech = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH).toString()), 2);
                        dlbCanRechOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANRCH_ORI).toString()), 2);

                        dlbCanDevCli = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEVCLI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEVCLI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEVCLI).toString()), 2);
                        dlbCanDevCli_Ori = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEVCLI_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEVCLI_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEVCLI_ORI).toString()), 2);

                        if (objZafParSis.getCodigoMenu() == 1888) {
                            strSql = "UPDATE tbm_detsoldevven SET "
                                    + " st_devFalStkAce='" + (tblDat.getValueAt(i, INT_TBL_FALSTKFICACE) == null ? "N" : (tblDat.getValueAt(i, INT_TBL_FALSTKFICACE).toString().equals("true") ? "S" : "N")) + "' "
                                    + " ,st_revtec='" + (tblDat.getValueAt(i, INT_TBL_REVTEC) == null ? "N" : (tblDat.getValueAt(i, INT_TBL_REVTEC).toString().equals("true") ? "S" : "N")) + "' ";

                            if (dlbCanRec != dlbCanRecOri) {
                                if (dlbCanRec > dlbCanRecOri) {
                                    strSql += " ,nd_canrec=nd_canrec+" + (dlbCanRec - dlbCanRecOri) + " ";
                                }
                                if (dlbCanRec < dlbCanRecOri) {
                                    strSql += " ,nd_canrec=nd_canrec-" + (dlbCanRecOri - dlbCanRec) + " ";
                                }
                            }
                            if (dlbCanTotNunRec != dlbCanTotNunRecOri) {
                                if (dlbCanTotNunRec > dlbCanTotNunRecOri) {
                                    strSql += " ,nd_canNunRec=nd_canNunRec+" + (dlbCanTotNunRec - dlbCanTotNunRecOri) + " ";
                                }
                                if (dlbCanTotNunRec < dlbCanTotNunRecOri) {
                                    strSql += " ,nd_canNunRec=nd_canNunRec-" + (dlbCanTotNunRecOri - dlbCanTotNunRec) + " ";
                                }
                            }
                            if (dlbCanAce != dlbCanAceOri) {
                                if (dlbCanAce > dlbCanAceOri) {
                                    strSql += " ,nd_canRevBodAce=nd_canRevBodAce+" + (dlbCanAce - dlbCanAceOri) + " ";
                                }
                                if (dlbCanAce < dlbCanAceOri) {
                                    strSql += " ,nd_canRevBodAce=nd_canRevBodAce-" + (dlbCanAceOri - dlbCanAce) + " ";
                                }
                            }
                            if (dlbCanRech != dlbCanRechOri) {
                                if (dlbCanRech > dlbCanRechOri) {
                                    strSql += " ,nd_canRevBodRec=nd_canRevBodRec+" + (dlbCanRech - dlbCanRechOri) + " ";
                                }
                                if (dlbCanRech < dlbCanRechOri) {
                                    strSql += " ,nd_canRevBodRec=nd_canRevBodRec-" + (dlbCanRechOri - dlbCanRech) + " ";
                                }
                            }

                            strSql += " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                    + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText() + " AND co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                            stmLoc.executeUpdate(strSql);

                            String sqlAux = "";
                            //     if(dlbCanAce!=dlbCanAceOri){

                            strSqlFic = "";
                            if (strEstFisBod.equals("N")) {
                                if (strMerIngEgr.equals("S")) {

                                    double dlbCan = 0;

                                    if (dlbCanTotNunRec != dlbCanTotNunRecOri) {
                                        if (dlbCanTotNunRec > dlbCanTotNunRecOri) {
                                            dlbCan += -(dlbCanTotNunRec - dlbCanTotNunRecOri);
                                        }
                                        if (dlbCanTotNunRec < dlbCanTotNunRecOri) {
                                            dlbCan += +(dlbCanTotNunRecOri - dlbCanTotNunRec);
                                        }
                                    }

                                    if (dlbCanRech != dlbCanRechOri) {
                                        if (dlbCanRech > dlbCanRechOri) {
                                            dlbCan += -(dlbCanRech - dlbCanRechOri);
                                        }
                                        if (dlbCanRech < dlbCanRechOri) {
                                            dlbCan += +(dlbCanRechOri - dlbCanRech);
                                        }
                                    }

                                    if (dlbCanAce != dlbCanAceOri) {
                                        if (dlbCanAce > dlbCanAceOri) {
                                            dlbCan += -(dlbCanAce - dlbCanAceOri);   //sqlAux += " nd_caningbod=nd_caningbod-"+(dlbCanAce-dlbCanAceOri)+dlbCan+" ";
                                        }
                                        if (dlbCanAce < dlbCanAceOri) {
                                            dlbCan += +(dlbCanAceOri - dlbCanAce);      // sqlAux += " nd_caningbod=nd_caningbod+"+(dlbCanAceOri-dlbCanAce)+dlbCan+" ";
                                        }
                                    }

                                    System.out.println("dlbCan--> " + dlbCan);

                                    sqlAux += " nd_caningbod=nd_caningbod+" + dlbCan + " ";

                                    strSqlFic = " UPDATE tbm_invbod SET " + sqlAux + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                    if (!sqlAux.equals("")) {
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }
                                }
                            }
                            //  }

                        }
                        if (objZafParSis.getCodigoMenu() == 1898) {
                            strSqlAux = "";
                            if (dlbCanAce != dlbCanAceOri) {
                                if (dlbCanAce > dlbCanAceOri) {
                                    strSqlAux += " nd_canrevtecace=nd_canrevtecace+" + (dlbCanAce - dlbCanAceOri) + " ";
                                }
                                if (dlbCanAce < dlbCanAceOri) {
                                    strSqlAux += " nd_canrevtecace=nd_canrevtecace-" + ((dlbCanAce == 0 ? dlbCanAceOri : (dlbCanAceOri - dlbCanAce))) + " ";
                                }
                            }
                            if (dlbCanRech != dlbCanRechOri) {
                                if (!strSqlAux.equals("")) {
                                    strSqlAux += " ,";
                                }
                                if (dlbCanRech > dlbCanRechOri) {
                                    strSqlAux += " nd_canrevtecrec=nd_canrevtecrec-" + (dlbCanRech - dlbCanRechOri) + " ";
                                }
                                if (dlbCanRech < dlbCanRechOri) {
                                    strSqlAux += " nd_canrevtecrec=nd_canrevtecrec+" + ((dlbCanRech == 0 ? dlbCanRechOri : (dlbCanRechOri - dlbCanRech))) + " ";
                                }
                            }

                            strSql = "UPDATE tbm_detsoldevven SET " + strSqlAux + " "
                                    + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                    + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText() + " AND co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                            if (!strSqlAux.equals("")) {
                                //System.out.println(">>"+ strSql );
                                stmLoc.executeUpdate(strSql);
                            }
                        }

                        if (objZafParSis.getCodigoMenu() == 1908) {
                            strSqlAux = "";

                            if (dlbCanTotNunRec != dlbCanTotNunRecOri) {
                                if (dlbCanTotNunRec > dlbCanTotNunRecOri) {
                                    strSqlAux += " nd_canNunRec=nd_canNunRec+" + (dlbCanTotNunRec - dlbCanTotNunRecOri) + " ";
                                }
                                if (dlbCanTotNunRec < dlbCanTotNunRecOri) {
                                    strSqlAux += " nd_canNunRec=nd_canNunRec-" + (dlbCanTotNunRecOri - dlbCanTotNunRec) + " ";
                                }
                            }

                            if (dlbCanAce != dlbCanAceOri) {
                                if (!strSqlAux.equals("")) {
                                    strSqlAux += " ,";
                                }
                                if (dlbCanAce > dlbCanAceOri) {
                                    strSqlAux += " nd_canRevBodAce=nd_canRevBodAce+" + (dlbCanAce - dlbCanAceOri) + " ";
                                }
                                if (dlbCanAce < dlbCanAceOri) {
                                    strSqlAux += " nd_canRevBodAce=nd_canRevBodAce-" + ((dlbCanAce == 0 ? dlbCanAceOri : (dlbCanAceOri - dlbCanAce))) + " ";
                                }
                            }
                            if (dlbCanRech != dlbCanRechOri) {
                                if (!strSqlAux.equals("")) {
                                    strSqlAux += " ,";
                                }
                                if (dlbCanRech > dlbCanRechOri) {
                                    strSqlAux += " nd_canRevBodRec=nd_canRevBodRec+" + (dlbCanRech - dlbCanRechOri) + " ";
                                }
                                if (dlbCanRech < dlbCanRechOri) {
                                    strSqlAux += " nd_canRevBodRec=nd_canRevBodRec-" + ((dlbCanRech == 0 ? dlbCanRechOri : (dlbCanRechOri - dlbCanRech))) + " ";
                                }
                            }

                            strSql = "UPDATE tbm_detsoldevven SET " + strSqlAux + " "
                                    + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                    + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText() + " AND co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                            if (!strSqlAux.equals("")) {
                                //System.out.println(">>"+ strSql );
                                stmLoc.executeUpdate(strSql);
                            }


                            String sqlAux = "";
                            //  if(dlbCanAce!=dlbCanAceOri){

                            strSqlFic = "";
                            if (strEstFisBod.equals("N")) {
                                if (strMerIngEgr.equals("S")) {
//                if(dlbCanAce > dlbCanAceOri)
//                 sqlAux += " nd_caningbod=nd_caningbod+"+(dlbCanAce-dlbCanAceOri)+" ";
//                if(dlbCanAce < dlbCanAceOri)
//                 sqlAux += " nd_caningbod=nd_caningbod-"+(dlbCanAceOri-dlbCanAce)+" ";


                                    double dlbCan = 0;

                                    if (dlbCanTotNunRec != dlbCanTotNunRecOri) {
                                        if (dlbCanTotNunRec > dlbCanTotNunRecOri) {
                                            dlbCan += -(dlbCanTotNunRec - dlbCanTotNunRecOri);
                                        }
                                        if (dlbCanTotNunRec < dlbCanTotNunRecOri) {
                                            dlbCan += +(dlbCanTotNunRecOri - dlbCanTotNunRec);
                                        }
                                    }


                                    if (dlbCanRech != dlbCanRechOri) {
                                        if (dlbCanRech > dlbCanRechOri) {
                                            dlbCan += -(dlbCanRech - dlbCanRechOri);
                                        }
                                        if (dlbCanRech < dlbCanRechOri) {
                                            dlbCan += +(dlbCanRechOri - dlbCanRech);
                                        }
                                    }

                                    if (dlbCanAce != dlbCanAceOri) {
                                        if (dlbCanAce > dlbCanAceOri) {
                                            dlbCan += -(dlbCanAce - dlbCanAceOri);   //sqlAux += " nd_caningbod=nd_caningbod-"+(dlbCanAce-dlbCanAceOri)+dlbCan+" ";
                                        }
                                        if (dlbCanAce < dlbCanAceOri) {
                                            dlbCan += +(dlbCanAceOri - dlbCanAce);      // sqlAux += " nd_caningbod=nd_caningbod+"+(dlbCanAceOri-dlbCanAce)+dlbCan+" ";
                                        }
                                    }

                                    System.out.println("dlbCan--> " + dlbCan);

                                    sqlAux += " nd_caningbod=nd_caningbod+" + dlbCan + " ";

                                    strSqlFic = " UPDATE tbm_invbod SET " + sqlAux + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                    if (!sqlAux.equals("")) {
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }
                                }
                            }
                            //   }

                        }


                        if (objZafParSis.getCodigoMenu() == 1928) {
                            strSqlAux = "";

                            if (dlbCanDevCli != dlbCanDevCli_Ori) {
                                if (dlbCanDevCli > dlbCanDevCli_Ori) {
                                    strSqlAux += " nd_canRecDevCli=nd_canRecDevCli+" + (dlbCanDevCli - dlbCanDevCli_Ori) + " ";
                                }
                                if (dlbCanDevCli < dlbCanDevCli_Ori) {
                                    strSqlAux += " nd_canRecDevCli=nd_canRecDevCli-" + (dlbCanDevCli_Ori - dlbCanDevCli) + " ";
                                }
                            }

                            strSql = "UPDATE tbm_detsoldevven SET " + strSqlAux + " "
                                    + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                    + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText() + " AND co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                            if (!strSqlAux.equals("")) {
                                stmLoc.executeUpdate(strSql);
                            }
                        }
                    }

                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean modificarCab(java.sql.Connection conn, int intCodDoc, String strTipRev, String strRevTec, String strExiMerRec, String strRecMerDev, String strRecTecMerDev) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            String strFecDoc = "";
            String strFecSis = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    //************  PERMITE SABER SI EL NUMERO DE Devolucion ESTA DUPLICADO  *****************/ 
                    if (!txtNumDocOcu.getText().equals(txtNumDoc.getText())) {
                        strSql = "select count(ne_numdoc) as num from tbm_cabRecMerSolDevVen WHERE "
                                + " co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + objZafParSis.getCodigoLocal() + " "
                                + " and co_tipdoc=" + txtCodTipDoc.getText() + " and ne_numdoc=" + txtNumDoc.getText();
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
                            if (rstLoc.getInt("num") >= 1) {
                                //JOptionPane oppMsg = new JOptionPane();
                                String strTit, strMsg;
                                strTit = "Mensaje del sistema Zafiro";
                                strMsg = " No. de Solicitud ya existe... ?";
                                JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE, null);
                                blnRes = true;
                            }
                        }
                        rstLoc.close();
                        rstLoc = null;
                        if (blnRes) {
                            return false;
                        }
                        blnRes = false;
                    }
                    //***********************************************************************************************/

                    strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";
                    strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());

                    strSql = "UPDATE tbm_cabRecMerSolDevVen SET   fe_doc='" + strFecDoc + "',   ne_numdoc=" + txtNumDoc.getText() + ", co_usrres=" + txtCodRes.getText() + ",  "
                            + " tx_obs1='" + txtObs1.getText() + "', tx_obs2='" + txtObs2.getText() + "', fe_ultmod='" + strFecSis + "', co_usrmod=" + objZafParSis.getCodigoUsuario() + " "
                            + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " AND co_tipdoc=" + txtCodTipDoc.getText() + "  AND co_doc=" + intCodDoc;
                    //System.out.println(""+ strSql); 
                    stmLoc.executeUpdate(strSql);

                    if (objZafParSis.getCodigoMenu() == 1888) {
                        strSql = "UPDATE tbm_cabsoldevven SET st_revtec='" + strRevTec + "', st_eximerrec='" + strExiMerRec + "'  WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText();
                    }
                    if (objZafParSis.getCodigoMenu() == 1898) {
                        strSql = "UPDATE tbm_cabsoldevven SET  st_eximerrec='" + strExiMerRec + "' WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                + " AND co_loc=" + txtCodLoc.getText() + " AND co_tipdoc=" + txtCodTipDocSol.getText() + " AND co_Doc=" + txtCodDocSol.getText();
                    }
                    //System.out.println(""+ strSql);
                    stmLoc.executeUpdate(strSql);

                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private void bloquea(javax.swing.JTextField txtFiel, java.awt.Color colBack, boolean blnEst) 
        {
            colBack = txtFiel.getBackground();
            txtFiel.setEditable(blnEst);
            txtFiel.setBackground(colBack);
        }

        private void noEditable(boolean blnEst)
        {
            java.awt.Color colBack = txtCodLoc.getBackground();
            bloquea(txtCodLoc, colBack, blnEst);
            bloquea(txtNomLoc, colBack, blnEst);
            bloquea(txtDesCorTipDocSol, colBack, blnEst);
            bloquea(txtDesLarTipDocSol, colBack, blnEst);
            bloquea(txtCodCli, colBack, blnEst);
            bloquea(txtNomCli, colBack, blnEst);
            bloquea(txtCodSol, colBack, blnEst);
            bloquea(txtDesSol, colBack, blnEst);
            bloquea(txtFecDocSol, colBack, blnEst);
            bloquea(txtCodDocSol, colBack, blnEst);
        }

        public void cargarTipoDoc(int intVal) 
        {
            java.sql.Connection conn;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try {
                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "Select  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor , doc.ne_numVisBue "
                            + " ,case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc, doc.st_meringegrfisbod, doc.tx_natdoc  "
                            + " from tbm_cabtipdoc as doc"
                            + " inner join tbr_tipdocprg as menu ON (menu.co_emp=doc.co_emp and menu.co_loc=doc.co_loc and menu.co_tipdoc=doc.co_tipdoc)  "
                            + " where   menu.co_emp = " + objZafParSis.getCodigoEmpresa() + " and "
                            + " menu.co_loc = " + objZafParSis.getCodigoLocal() + " and "
                            + " menu.co_mnu = " + objZafParSis.getCodigoMenu() + " and "
                            + " menu.st_reg = 'S'";
                    // System.out.println(""+ strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        txtCodTipDoc.setText(((rstLoc.getString("co_tipdoc") == null) ? "" : rstLoc.getString("co_tipdoc")));
                        txtDesCorTipDoc.setText(((rstLoc.getString("tx_descor") == null) ? "" : rstLoc.getString("tx_descor")));
                        txtDesLarTipDoc.setText(((rstLoc.getString("tx_deslar") == null) ? "" : rstLoc.getString("tx_deslar")));
                        strCodTipDoc = txtCodTipDoc.getText();
                        strMerIngEgr = (rstLoc.getString("st_meringegrfisbod") == null) ? "" : rstLoc.getString("st_meringegrfisbod");
                        strTipIngEgr = (rstLoc.getString("tx_natdoc") == null) ? "" : rstLoc.getString("tx_natdoc");

                        if (intVal == 1) {
                            txtNumDoc.setText(((rstLoc.getString("numDoc") == null) ? "" : rstLoc.getString("numDoc")));
                        }
                    }
                    rstLoc.close();
                    stmLoc.close();
                    stmLoc = null;
                    rstLoc = null;
                    conn.close();
                    conn = null;
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
        }

        public void clnTextos()
        {
            strCodTipDoc = "";
            strDesCorTipDoc = "";
            strDesLarTipDoc = "";
            strCodRes = "";
            strNomRes = "";

            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodRes.setText("");
            txtNomRes.setText("");
            txtCodDoc.setText("");
            txtNumDoc.setText("");
            txtFecDoc.setText("");
            txtObs1.setText("");
            txtObs2.setText("");
            txtObs1Sol.setText("");
            txtObs2Sol.setText("");

            txtCodLoc.setText("");
            txtNomLoc.setText("");
            txtCodTipDocSol.setText("");
            txtDesCorTipDocSol.setText("");
            txtDesLarTipDocSol.setText("");
            txtCodCli.setText("");
            txtNomCli.setText("");
            txtCodSol.setText("");
            txtDesSol.setText("");
            txtFecDocSol.setText("");
            txtNumDocSol.setText("");
            txtCodDocSol.setText("");

            objTblMod.removeAllRows();
        }

        @Override
        public boolean cancelar() {
            boolean blnRes = true;

            try {
                if (blnHayCam || objTblMod.isDataModelChanged()) {
                    if (objTooBar.getEstado() == 'n' || objTooBar.getEstado() == 'm') {
                        if (!isRegPro()) {
                            return false;
                        }
                    }
                }
                if (rstCab != null) {
                    rstCab.close();
                    if (STM_GLO != null) {
                        STM_GLO.close();
                        STM_GLO = null;
                    }
                    rstCab = null;

                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            clnTextos();
            blnHayCam = false;

            return blnRes;
        }

        @Override
        public boolean aceptar() {
            return true;
        }

        @Override
        public boolean afterAceptar() {
            return true;
        }

        @Override
        public boolean afterAnular() {
            return true;
        }

        @Override
        public boolean afterCancelar() {
            return true;
        }

        @Override
        public boolean afterConsultar() {
            return true;
        }

        @Override
        public boolean afterEliminar() {
            return true;
        }

        @Override
        public boolean afterImprimir() {
            return true;
        }

        @Override
        public boolean afterInsertar() 
        {
            this.setEstado('w');   
            //[1888 - RECEPCION DE LA MERCADERIA SUJETA A REVISION]
            if ((objZafParSis.getCodigoMenu() == 1888))
            { 
                imprimirRecepcionDevolucion();
                imprimirOrdenAlmacenamiento();
//                for (int i = 0; i < tblDat.getRowCount(); i++) 
//                {
//                    if (objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANACE).toString())), 6) > 0) 
//                    {
//                        imprimirOrdenAlmacenamiento();
//                    }
//                }
            }

            //[1908 - REVISION Y CONFIRMACION DE INGRESO A BODEGA DE LA MERCADERIA DEVUELTA]
            if (objZafParSis.getCodigoMenu() == 1908) 
            { 
                imprimirOrdenAlmacenamiento();
//                for (int i = 0; i < tblDat.getRowCount(); i++) 
//                {
//                    if (objUti.redondear((tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_ACE) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_ACE).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_BOD_REV_ACE).toString())), 6) > 0) {
//                        imprimirOrdenAlmacenamiento();
//                    }
//                }
            }
            
            return true;
        }

        @Override
        public boolean afterModificar() {
            this.setEstado('w');
            return true;
        }

        @Override
        public boolean afterVistaPreliminar() {
            return true;
        }

        /**
         * Esta función muestra un mensaje "showConfirmDialog". Presenta las
         * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
         * hacer el sistema seleccionando una de las opciones que se presentan.
         */
        private int mostrarMsgCon(String strMsg) {
            //JOptionPane oppMsg=new JOptionPane();
            String strTit;
            strTit = "Mensaje del sistema Zafiro";
            return JOptionPane.showConfirmDialog(this, strMsg, strTit, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        }

        /**
         * Esta función se encarga de agregar el listener "DocumentListener" a
         * los objTooBars de tipo texto para poder determinar si su contenido a
         * cambiado o no.
         */
        private boolean isRegPro() {
            boolean blnRes = true;
            String strAux = "¿Desea guardar los cambios efectuados a éste registro?\n";
            strAux += "Si no guarda los cambios perderá toda la información que no haya guardado.";
            switch (mostrarMsgCon(strAux)) {
                case 0: //YES_OPTION
                    switch (objTooBar.getEstado()) {
                        case 'n': //Insertar
                            blnRes = objTooBar.insertar();
                            break;
                        case 'm': //Modificar
//                        blnRes=objTooBar.modificar();
                            break;
                    }
                    break;
                case 1: //NO_OPTION
                    objTblMod.setDataModelChanged(false);
                    blnHayCam = false;
                    blnRes = true;
                    break;
                case 2: //CANCEL_OPTION
                    blnRes = false;
                    break;
            }
            return blnRes;
        }

        @Override
        public boolean consultar() {
            /*
             * Esto Hace en caso de que el modo de operacion sea Consulta
             */
            return _consultar(FilSql());
        }

        private boolean _consultar(String strFil) 
        {
            boolean blnRes = false;
            String strSql = "";
            try {
                if (!validarDat()) {
                    return false;
                }

                abrirCon();
                if (CONN_GLO != null) {
                    STM_GLO = CONN_GLO.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY);

                    strSql = "SELECT  a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc FROM tbm_cabRecMerSolDevVen AS a "
                            + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " and a.co_loc=" + objZafParSis.getCodigoLocal() + " AND a.st_reg NOT IN('E') " + strFil + " ORDER BY a.ne_numdoc ";

                    System.out.println("ZafVen27._consultar: " + strSql);

                    rstCab = STM_GLO.executeQuery(strSql);
                    if (rstCab.next()) {
                        rstCab.last();
                        setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                        refrescaDatos(CONN_GLO, rstCab);
                        blnRes = true;
                    } else {
                        setMenSis("0 Registros encontrados");
                        clnTextos();
                    }

                    CerrarCon();
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }

            System.gc();
            return blnRes;
        }

        private boolean _consultar(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc) 
        {
            boolean blnRes = false;
            String strSql = "";
            try {
                abrirCon();
                if (CONN_GLO != null) {
                    STM_GLO = CONN_GLO.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY);

                    strSql = "SELECT  a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc FROM tbm_cabRecMerSolDevVen AS a "
                            + " WHERE a.co_emp=" + intCodEmp + " and a.co_loc=" + intCodLoc + " AND a.st_reg NOT IN('E') "
                            + " and a.co_tipdoc=" + intCodTipDoc + " and a.co_doc=" + intCodDoc + " ";
                    rstCab = STM_GLO.executeQuery(strSql);
                    if (rstCab.next()) {
                        rstCab.last();
                        setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                        refrescaDatos(CONN_GLO, rstCab);
                        blnRes = true;
                    } else {
                        setMenSis("0 Registros encontrados");
                        clnTextos();
                    }

                    CerrarCon();
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }

            System.gc();
            return blnRes;
        }

      
        private boolean refrescaDatos(java.sql.Connection conn, java.sql.ResultSet rstDatSolCon) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.ResultSet rstDatSol;
            String strSql = "";
            Vector vecData;
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "SELECT a.co_tipdoc, a.st_reg, a2.tx_descor, a2.tx_deslar, a.co_usrres , a3.tx_nom, a.co_doc, a.fe_doc, a.ne_numdoc, a.tx_obs1, a.tx_obs2 "
                            + ", a4.co_loc, a5.tx_nom as tx_desloc, a4.co_tipdoc as co_tipdocsol,  a6.tx_descor as descorsol, a6.tx_deslar as deslarsol , a4.co_cli, a4.tx_nomcli "
                            + ",a4.co_usrsol, a4.tx_nomusrsol, a4.fe_doc as fesol, a4.ne_numdoc as numdocsol, a4.co_Doc as codocsol "
                            + " ,a4.tx_obs1 as tx_obs1sol, a4.tx_obs2 as tx_obs2sol  "
                            + " FROM tbm_cabRecMerSolDevVen AS a "
                            + " INNER JOIN tbm_loc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel) "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                            + " INNER JOIN tbm_usr AS a3 ON(a3.co_usr=a.co_usrres) "
                            + " INNER JOIN tbm_cabsoldevven AS a4 ON(a4.co_emp=a.co_emp AND a4.co_loc=a.co_locrel AND a4.co_tipdoc=a.co_tipdocrel and a4.co_doc=a.co_docrel) "
                            + " INNER JOIN tbm_loc AS a5 ON(a5.co_emp=a4.co_emp AND a5.co_loc=a4.co_locrel) "
                            + " INNER JOIN tbm_cabtipdoc AS a6 ON(a6.co_emp=a4.co_emp AND a6.co_loc=a.co_loc AND a6.co_tipdoc=a4.co_tipdoc) "
                            + " WHERE a.co_emp=" + rstDatSolCon.getString("co_emp")
                            + " AND a.co_loc=" + rstDatSolCon.getString("co_loc")
                            + " AND a.co_tipdoc=" + rstDatSolCon.getString("co_tipdoc")
                            + " AND a.co_doc=" + rstDatSolCon.getString("co_doc");

                    System.out.println("ZafVen27.refrescaDatos: " + strSql);

                    rstDatSol = stmLoc.executeQuery(strSql);
                    if (rstDatSol.next()) {
                        strAux = rstDatSol.getString("st_reg");

                        txtCodTipDoc.setText(rstDatSol.getString("co_tipdoc"));
                        txtDesCorTipDoc.setText(rstDatSol.getString("tx_descor"));
                        txtDesLarTipDoc.setText(rstDatSol.getString("tx_deslar"));
                        txtCodRes.setText(rstDatSol.getString("co_usrres"));
                        txtNomRes.setText(rstDatSol.getString("tx_nom"));
                        txtCodDoc.setText(rstDatSol.getString("co_doc"));
                        txtNumDoc.setText(rstDatSol.getString("ne_numdoc"));
                        txtObs1.setText(rstDatSol.getString("tx_obs1"));
                        txtObs2.setText(rstDatSol.getString("tx_obs2"));
                        txtNumDocOcu.setText(rstDatSol.getString("ne_numdoc"));
                        txtObs1Sol.setText(rstDatSol.getString("tx_obs1sol"));
                        txtObs2Sol.setText(rstDatSol.getString("tx_obs2sol"));

                        txtCodLoc.setText(rstDatSol.getString("co_loc"));
                        txtNomLoc.setText(rstDatSol.getString("tx_desloc"));
                        txtCodTipDocSol.setText(rstDatSol.getString("co_tipdocsol"));
                        txtDesCorTipDocSol.setText(rstDatSol.getString("descorsol"));
                        txtDesLarTipDocSol.setText(rstDatSol.getString("deslarsol"));
                        txtCodCli.setText(rstDatSol.getString("co_cli"));
                        txtNomCli.setText(rstDatSol.getString("tx_nomcli"));
                        txtCodSol.setText(rstDatSol.getString("co_usrsol"));
                        txtDesSol.setText(rstDatSol.getString("tx_nomusrsol"));
                        txtFecDocSol.setText(rstDatSol.getString("fesol"));
                        txtCodDocSol.setText(rstDatSol.getString("codocsol"));
                        txtNumDocSol.setText(rstDatSol.getString("numdocsol"));

                        Date dateObj = rstDatSol.getDate("fe_doc");

                        if (dateObj == null) {
                            txtFecDoc.setText("");
                        } else {
                            Calendar calObj = Calendar.getInstance();
                            calObj.setTime(dateObj);
                            txtFecDoc.setText(calObj.get(Calendar.DAY_OF_MONTH), calObj.get(Calendar.MONTH) + 1, calObj.get(Calendar.YEAR));
                        }
                    }
                    rstDatSol.close();
                    rstDatSol = null;

                    vecData = new Vector();

                    String strAux2 = " , CASE WHEN ( (trim(SUBSTR (UPPER(a1.tx_codalt), length(a1.tx_codalt) ,1)) IN ( "
                            + " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a "
                            + " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) "
                            + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " and a.co_loc=" + objZafParSis.getCodigoLocal() + " and a1.st_reg='A' and   a.st_reg='P' ))) "
                            + " THEN 'S' ELSE 'N' END AS proconf  ";

                    if (objZafParSis.getCodigoMenu() == 1888) {

//    strSql=" SELECT case when  proconf in ('S') then nd_cansolnodevprv  else  nd_candev_01 end as nd_candev,  *  FROM ( " +
//   " SELECT (a1.nd_candev - a1.nd_canvolfac) as nd_candev_01,  a1.nd_cansolnodevprv,  a1.nd_candevprv, a1.nd_cannodevprv, a1.st_devFalStkAce, a1.st_devFalStk, a.tx_obsCanRevTec, a1.nd_canRec as nd_cantotRec , a.tx_obsCanRevBod, a.nd_canRevBodRec as nd_canRechazada, a.nd_canRevBodAce as nd_canAceptada ,  a1.nd_canNunRec, a.nd_canRec, a.tx_obsCanRec,  a1.co_reg, a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a1.co_bod, a2.tx_nom, a1.nd_can, a1.st_revtec  " +
//   " ,a1.nd_canRevTecAce as nd_canRevTecAceSol, a1.nd_canRevTecRec as nd_canRevTecRecSol, a1.nd_canRevBodAce as nd_canRevBodAceSol, a1.nd_canRevBodRec as nd_canRevBodRecSol " +
//   "  ,a1.nd_canRecDevCli " +
//   strAux2+" FROM tbm_detRecMerSolDevVen AS a " +
//   " INNER JOIN tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel AND a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) " +
//   " INNER JOIN  tbm_bod AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_bod=a1.co_bod) " +
//   " WHERE a.co_emp="+rstDatSolCon.getString("co_emp")+" AND a.co_loc="+rstDatSolCon.getString("co_loc")+"  AND " +
//   " a.co_tipdoc="+rstDatSolCon.getString("co_tipdoc")+" AND a.co_doc="+rstDatSolCon.getString("co_doc")+" " +
//   " ) as  x  ";

                        strSql = " SELECT case when  proconf in ('N') then nd_cansolnodevprv  else  nd_candev_01 end as nd_candev ,*  FROM ( "
                                + " SELECT case when (a1.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a1.nd_canvolfac ) ) > 0 then "
                                //   + " (a1.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a1.nd_canvolfac ) )  else 0 end as nd_candev_01, "
                                + " (a1.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a1.nd_canvolfac ) )  else (case when a3.nd_cannunrec is null then 0 else a1.nd_candev - a1.nd_canvolfac end) end as nd_candev_01, "
                                + " a1.nd_cansolnodevprv,  a1.nd_candevprv, a1.nd_cannodevprv, a1.st_devFalStkAce, a1.st_devFalStk, a.tx_obsCanRevTec, a1.nd_canRec as nd_cantotRec , a.tx_obsCanRevBod, a.nd_canRevBodRec as nd_canRechazada, a.nd_canRevBodAce as nd_canAceptada ,  a1.nd_canNunRec, a.nd_canRec, "
                                + "  a.tx_obsCanRec,  a1.co_reg, a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a1.co_bod, a4.tx_nom, a1.nd_can, a1.st_revtec   "
                                + "  ,a1.nd_canRevTecAce as nd_canRevTecAceSol, a1.nd_canRevTecRec as nd_canRevTecRecSol, a1.nd_canRevBodAce as nd_canRevBodAceSol, a1.nd_canRevBodRec as nd_canRevBodRecSol  "
                                + "   ,a1.nd_canRecDevCli  ,a2.st_meringegrfisbod  as proconf"
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                + " ,INV.TX_CODALT2 "
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                
                                + "  FROM tbm_detRecMerSolDevVen AS a  "
                                + "  INNER JOIN tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel AND a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel )  "
                                + "  INNER JOIN tbm_detmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrel and a2.co_tipdoc=a1.co_tipdocrel and a2.co_doc=a1.co_docrel and a2.co_reg=a1.co_regrel )  "
                                + "  INNER JOIN  tbm_bod AS a4 ON (a4.co_emp=a1.co_emp AND a4.co_bod=a1.co_bod)  "
                                
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                + " INNER JOIN TBM_INV AS INV ON(INV.CO_EMP=A.CO_EMP  AND INV.CO_ITM=A1.CO_ITM)"
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                
                                
                                + "  LEFT JOIN  tbm_detingegrmerbod AS a3 on (a3.co_emp=a1.co_emp and a3.co_loc=a1.co_locrelcon and a3.co_tipdoc=a1.co_tipdocrelcon and a3.co_doc=a1.co_docrelcon and a3.co_reg=a1.co_regrelcon ) "
                                + "  WHERE a.co_emp=" + rstDatSolCon.getString("co_emp") + " AND a.co_loc=" + rstDatSolCon.getString("co_loc") + "  AND  "
                                + "  a.co_tipdoc=" + rstDatSolCon.getString("co_tipdoc") + "  AND a.co_doc=" + rstDatSolCon.getString("co_doc") + " "
                                + " ) AS x "
                                + " order by co_reg ";
                    }

                    if (objZafParSis.getCodigoMenu() == 1898) {
                        strSql = "SELECT (a1.nd_candev - a1.nd_canvolfac) as nd_candev, a1.nd_candevprv, a1.nd_cannodevprv, a1.st_devFalStkAce, a1.st_devFalStk, a.tx_obsCanRevTec, a1.nd_canRec as nd_cantotRec , a.tx_obsCanRevBod, a.nd_canrevtecrec as nd_canRechazada, a.nd_canrevtecace as nd_canAceptada,  a1.nd_canNunRec, a.nd_canRec, a.tx_obsCanRec,  a1.co_reg, a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a1.co_bod, a2.tx_nom, a1.nd_can,  a1.st_revtec  "
                                + " ,a1.nd_canRevTecAce as nd_canRevTecAceSol, a1.nd_canRevTecRec as nd_canRevTecRecSol, a1.nd_canRevBodAce as nd_canRevBodAceSol, a1.nd_canRevBodRec as nd_canRevBodRecSol "
                                + " ,a1.nd_canRecDevCli "
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                + " ,INV.TX_CODALT2 "
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                
                                + strAux2 + " FROM tbm_detRecMerSolDevVen AS a "
                                + " INNER JOIN tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel AND a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
                                + " INNER JOIN  tbm_bod AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_bod=a1.co_bod) "
                                
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                + " INNER JOIN TBM_INV AS INV ON(INV.CO_EMP=A1.CO_EMP  AND INV.CO_ITM=A1.CO_ITM)"
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                
                                + " WHERE a.co_emp=" + rstDatSolCon.getString("co_emp") + " AND a.co_loc=" + rstDatSolCon.getString("co_loc") + "  AND "
                                + " a.co_tipdoc=" + rstDatSolCon.getString("co_tipdoc") + " AND a.co_doc=" + rstDatSolCon.getString("co_doc")
                                + " order by a1.co_reg ";
                    }

                    if (objZafParSis.getCodigoMenu() == 1908) {
//   strSql=" SELECT case when  proconf in ('S') then nd_cansolnodevprv  else  nd_candev_01 end as nd_candev,  *  FROM ( " +
//   " SELECT (a1.nd_candev - a1.nd_canvolfac) as nd_candev, a1.nd_cansolnodevprv,  a1.nd_candevprv, a1.nd_cannodevprv, a1.st_devFalStkAce, a1.st_devFalStk, a.tx_obsCanRevTec, a1.nd_canRec as nd_cantotRec , a.tx_obsCanRevBod, a.nd_canrevtecrec as nd_canRechazada, a.nd_canrevtecace as nd_canAceptada,  a1.nd_canNunRec, a.nd_canRec, a.tx_obsCanRec,  a1.co_reg, a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a1.co_bod, a2.tx_nom, a1.nd_can,  a1.st_revtec  " +
//   " ,a1.nd_canRevTecAce as nd_canRevTecAceSol, a1.nd_canRevTecRec as nd_canRevTecRecSol, a1.nd_canRevBodAce as nd_canRevBodAceSol, a1.nd_canRevBodRec as nd_canRevBodRecSol " +
//   " ,a1.nd_canRecDevCli " +
//   strAux2+" FROM tbm_detRecMerSolDevVen AS a " +
//   " INNER JOIN tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel AND a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) " +
//   " INNER JOIN  tbm_bod AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_bod=a1.co_bod) " +
//   " WHERE a.co_emp="+rstDatSolCon.getString("co_emp")+" AND a.co_loc="+rstDatSolCon.getString("co_loc")+"  AND " +
//   " a.co_tipdoc="+rstDatSolCon.getString("co_tipdoc")+" AND a.co_doc="+rstDatSolCon.getString("co_doc")+" " +
//   " ) as  x  ";

                        /*strSql=" SELECT case when  proconf in ('N') then nd_cansolnodevprv  else  nd_candev_01 end as nd_candev ,*  FROM ( "
                         + " SELECT case when (a1.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a1.nd_canvolfac ) ) > 0 then "
                         + " (a1.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a1.nd_canvolfac ) )  else 0 end as nd_candev_01, "
                         + " a1.nd_cansolnodevprv,  a1.nd_candevprv, a1.nd_cannodevprv, a1.st_devFalStkAce, a1.st_devFalStk, a.tx_obsCanRevTec, a1.nd_canRec as nd_cantotRec , a.tx_obsCanRevBod, a.nd_canRevBodRec as nd_canRechazada, a.nd_canRevBodAce as nd_canAceptada ,  a1.nd_canNunRec, a.nd_canRec, "
                         + "  a.tx_obsCanRec,  a1.co_reg, a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a1.co_bod, a4.tx_nom, a1.nd_can, a1.st_revtec   "
                         + "  ,a1.nd_canRevTecAce as nd_canRevTecAceSol, a1.nd_canRevTecRec as nd_canRevTecRecSol, a1.nd_canRevBodAce as nd_canRevBodAceSol, a1.nd_canRevBodRec as nd_canRevBodRecSol  "
                         + "   ,a1.nd_canRecDevCli  ,a2.st_meringegrfisbod  as proconf"
                         + "  FROM tbm_detRecMerSolDevVen AS a  "
                         + "  INNER JOIN tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel AND a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel )  "
                         + "  INNER JOIN tbm_detmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrel and a2.co_tipdoc=a1.co_tipdocrel and a2.co_doc=a1.co_docrel and a2.co_reg=a1.co_regrel )  "
                         + "  INNER JOIN  tbm_bod AS a4 ON (a4.co_emp=a1.co_emp AND a4.co_bod=a1.co_bod)  "
                         + "  LEFT JOIN  tbm_detingegrmerbod AS a3 on (a3.co_emp=a1.co_emp and a3.co_loc=a1.co_locrelcon and a3.co_tipdoc=a1.co_tipdocrelcon and a3.co_doc=a1.co_docrelcon and a3.co_reg=a1.co_regrelcon ) "
                         + "  WHERE a.co_emp="+rstDatSolCon.getString("co_emp")+" AND a.co_loc="+rstDatSolCon.getString("co_loc")+"  AND  "
                         + "  a.co_tipdoc="+rstDatSolCon.getString("co_tipdoc")+"  AND a.co_doc="+rstDatSolCon.getString("co_doc")+" "
                         + " ) AS x  ";*/

                        strSql = " SELECT case when  proconf in ('N') then nd_cansolnodevprv  else  nd_candev_01 end as nd_candev ,* FROM ( "
                                + " SELECT case when (a1.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a1.nd_canvolfac ) ) > 0 then "
                                + " (a1.nd_candev - (case when a3.nd_cannunrec is null then 0 else abs(a3.nd_cannunrec) end +  a1.nd_canvolfac ) )  else 0 end as nd_candev_01, "
                                + " a1.nd_cansolnodevprv,  a1.nd_candevprv, a1.nd_cannodevprv, a1.st_devFalStkAce, a1.st_devFalStk, a.tx_obsCanRevTec, a1.nd_canRec as nd_cantotRec , a.tx_obsCanRevBod, a.nd_canRevBodRec as nd_canRechazada, a.nd_canRevBodAce as nd_canAceptada ,  a1.nd_canNunRec, a.nd_canRec, "
                                + " a.tx_obsCanRec,  a1.co_reg, a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a1.co_bod, a4.tx_nom, a1.nd_can, a1.st_revtec, "
                                + " a1.nd_canRevTecAce as nd_canRevTecAceSol, a1.nd_canRevTecRec as nd_canRevTecRecSol, a1.nd_canRevBodAce as nd_canRevBodAceSol, a1.nd_canRevBodRec as nd_canRevBodRecSol, "
                                + " a1.nd_canRecDevCli, a2.st_meringegrfisbod as proconf "

                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                + " ,INV.TX_CODALT2 "
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/                                
                                
                                + " FROM tbm_detRecMerSolDevVen AS a "
                                + " INNER JOIN tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel AND a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
                                + " INNER JOIN tbm_detmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrel and a2.co_tipdoc=a1.co_tipdocrel and a2.co_doc=a1.co_docrel and a2.co_reg=a1.co_regrel ) "
                                + " INNER JOIN  tbm_bod AS a4 ON (a4.co_emp=a1.co_emp AND a4.co_bod=a1.co_bod) "
                                
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                + " INNER JOIN TBM_INV AS INV ON(INV.CO_EMP=A1.CO_EMP  AND INV.CO_ITM=A1.CO_ITM)"
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/                                
                                
                                + " LEFT JOIN  tbm_detingegrmerbod AS a3 on (a3.co_emp=a1.co_emp and a3.co_loc=a1.co_locrelcon and a3.co_tipdoc=a1.co_tipdocrelcon and a3.co_doc=a1.co_docrelcon and a3.co_reg=a1.co_regrelcon ) "
                                + " WHERE a.co_emp=" + rstDatSolCon.getString("co_emp") + " AND a.co_loc=" + rstDatSolCon.getString("co_loc") + "  AND "
                                + " a.co_tipdoc=" + rstDatSolCon.getString("co_tipdoc") + "  AND a.co_doc=" + rstDatSolCon.getString("co_doc") + " "
                                + " ) AS x "
                                + " order by co_reg ";
                    }

                    if (objZafParSis.getCodigoMenu() == 1928) {
                        strSql = "SELECT (a1.nd_candev - a1.nd_canvolfac) as nd_candev,  a1.nd_candevprv, a1.nd_cannodevprv, a1.st_devFalStkAce, a1.st_devFalStk, a.tx_obsCanRevTec, a1.nd_canRec as nd_cantotRec , a.tx_obsCanRevBod, a.nd_canRevBodRec as nd_canRechazada, a.nd_canRevBodAce as nd_canAceptada ,  a1.nd_canNunRec, a.nd_canRec, a.tx_obsCanRec,  a1.co_reg, a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a1.co_bod, a2.tx_nom, a1.nd_can,  a1.st_revtec  "
                                + " ,a1.nd_canRevTecAce as nd_canRevTecAceSol, a1.nd_canRevTecRec as nd_canRevTecRecSol, a1.nd_canRevBodAce as nd_canRevBodAceSol, a1.nd_canRevBodRec as nd_canRevBodRecSol "
                                + "  ,a1.nd_canRecDevCli "

                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                + " ,INV.TX_CODALT2 "
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                
                                + strAux2 + " FROM tbm_detRecMerSolDevVen AS a "
                                + " INNER JOIN tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel AND a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
                                + " INNER JOIN  tbm_bod AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_bod=a1.co_bod) "

                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                + " INNER JOIN TBM_INV AS INV ON(INV.CO_EMP=A1.CO_EMP  AND INV.CO_ITM=A1.CO_ITM)"
                                /*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                                
                                
                                + " WHERE a.co_emp=" + rstDatSolCon.getString("co_emp") + " AND a.co_loc=" + rstDatSolCon.getString("co_loc") + "  AND "
                                + " a.co_tipdoc=" + rstDatSolCon.getString("co_tipdoc")
                                + " AND a.co_doc=" + rstDatSolCon.getString("co_doc")
                                + " order by a1.co_reg ";
                    }

                    System.out.println("ZafVen27.refrescaDatos: codigo de menu :" + objZafParSis.getCodigoMenu() + " sentencia: " + strSql);

                    rstLoc = stmLoc.executeQuery(strSql);
                    while (rstLoc.next()) {
                        java.util.Vector vecReg = new java.util.Vector();

                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
                        vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
						/*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/
                        vecReg.add(INT_TBL_COD3LET, rstLoc.getString("TX_CODALT2")); 
						/*INCLUSION CODIGO DE 3 LETRAS 12 JUN 2019*/                       
                        vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                        vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                        vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                        vecReg.add(INT_TBL_NOMBOD, rstLoc.getString("tx_nom"));
                        vecReg.add(INT_TBL_CANVEN, rstLoc.getString("nd_can"));
                        vecReg.add(INT_TBL_CANDEV, rstLoc.getString("nd_candev"));
                        vecReg.add(INT_TBL_FALSTKFIC, ((rstLoc.getString("st_devFalStk").equals("S")) ? true : false));

                        vecReg.add(INT_TBL_REVTEC, ((rstLoc.getString("st_revtec").equals("S")) ? true : false));
                        vecReg.add(INT_TBL_CANTOTREC, rstLoc.getString("nd_cantotRec"));
                        vecReg.add(INT_TBL_CANTOTNUNREC, rstLoc.getString("nd_canNunRec"));

                        vecReg.add(INT_TBL_CAN_TEC_REV_ACE, rstLoc.getString("nd_canRevTecAceSol"));
                        vecReg.add(INT_TBL_CAN_TEC_REV_REH, rstLoc.getString("nd_canRevTecRecSol"));
                        vecReg.add(INT_TBL_CAN_BOD_REV_ACE, rstLoc.getString("nd_canRevBodAceSol"));
                        vecReg.add(INT_TBL_CAN_BOD_REV_REH, rstLoc.getString("nd_canRevBodRecSol"));

                        vecReg.add(INT_TBL_CANREC, rstLoc.getString("nd_canRec"));
                        vecReg.add(INT_TBL_OBSCANREC, rstLoc.getString("tx_obsCanRec"));
                        vecReg.add(INT_TBL_CANACE, rstLoc.getString("nd_canAceptada"));
                        vecReg.add(INT_TBL_CANRCH, rstLoc.getString("nd_canRechazada"));
                        vecReg.add(INT_TBL_OBSREVCONFING, rstLoc.getString("tx_obsCanRevBod"));
                        vecReg.add(INT_TBL_EST_REC_TEC, rstLoc.getString("st_revtec"));
                        vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                        vecReg.add(INT_TBL_CANTOTNUNREC_ORI, rstLoc.getString("nd_canNunRec"));
                        vecReg.add(INT_TBL_CANREC_ORI, rstLoc.getString("nd_canRec"));
                        vecReg.add(INT_TBL_CANACE_ORI, rstLoc.getString("nd_canAceptada"));
                        vecReg.add(INT_TBL_CANRCH_ORI, rstLoc.getString("nd_canRechazada"));
                        vecReg.add(INT_TBL_OBSREVTEC, rstLoc.getString("tx_obsCanRevTec"));
                        vecReg.add(INT_TBL_CANTOTDEVCLI, rstLoc.getString("nd_canRecDevCli"));
                        vecReg.add(INT_TBL_CANDEVCLI_ORI, rstLoc.getString("nd_canRec"));
                        vecReg.add(INT_TBL_CANDEVCLI, rstLoc.getString("nd_canRec"));
                        vecReg.add(INT_TBL_OBSDEVCLI, rstLoc.getString("tx_obsCanRec"));
                        vecReg.add(INT_TBL_FALSTKFICACE, ((rstLoc.getString("st_devFalStkAce").equals("S")) ? true : false));
                        vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("proconf"));

                        vecReg.add(INT_TBL_CANSDEVPRV, rstLoc.getString("nd_candevprv"));
                        vecReg.add(INT_TBL_CANNDEVPRV, rstLoc.getString("nd_cannodevprv"));

                        vecData.add(vecReg);
                    }
                    objTblMod.setData(vecData);
                    tblDat.setModel(objTblMod);
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;

                    if (strAux.equals("A")) {
                        strAux = "Activo";
                    } else if (strAux.equals("I")) {
                        strAux = "Anulado";
                    } else if (strAux.equals("E")) {
                        strAux = "Eliminado";
                    } else {
                        strAux = "Otro";
                    }
                    objTooBar.setEstadoRegistro(strAux);

                    int intPosRel = rstDatSolCon.getRow();
                    rstDatSolCon.last();
                    objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstDatSolCon.getRow());
                    rstDatSolCon.absolute(intPosRel);

                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }

            return blnRes;
        }

        private String FilSql() 
        {
            String sqlFiltro = "";
            //Agregando filtro por Numero de Cotizacion
            if (!txtCodTipDoc.getText().equals("")) {
                sqlFiltro = sqlFiltro + " and a.co_tipdoc=" + txtCodTipDoc.getText();
            }

            if (!txtCodRes.getText().equals("")) {
                sqlFiltro = sqlFiltro + " and a.co_usrres=" + txtCodRes.getText();
            }

            if (!txtCodDoc.getText().equals("")) {
                sqlFiltro = sqlFiltro + " and a.co_doc=" + txtCodDoc.getText();
            }


            if (!txtNumDoc.getText().equals("")) {
                sqlFiltro = sqlFiltro + " and a.ne_numdoc=" + txtNumDoc.getText();
            }


            if (txtFecDoc.isFecha()) {
                int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
                String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" + FecSql[0] + "#";
                sqlFiltro = sqlFiltro + " and a.fe_doc = '" + strFecSql + "'";
            }


            return sqlFiltro;
        }

        @Override
        public void clickModificar()
        {
            setEditable(true);
            noEditable(false);

            java.awt.Color colBack;
            colBack = txtCodDoc.getBackground();

            bloquea(txtCodDoc, colBack, false);
            bloquea(txtDesCorTipDoc, colBack, false);
            bloquea(txtDesLarTipDoc, colBack, false);
            bloquea(txtNumDocSol, colBack, false);


            butBusTipDoc.setEnabled(false);
            butBusNumDoc.setEnabled(false);

            this.setEnabledConsultar(false);

            objTblMod.setDataModelChanged(false);
            blnHayCam = false;
        }

        //******************************************************************************************************
        @Override
        public boolean vistaPreliminar()
        {
            cargarReporte(1);
            return true;
        }

        private void cargarReporte(int intTipo)
        {
            if (objThrGUI == null) {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.setIndFunEje(intTipo);
                objThrGUI.start();
            }
        }

        @Override
        public boolean imprimir() 
        {
            cargarReporte(0);
            return true;
        }

        //******************************************************************************************************
        @Override
        public void clickImprimir() {
        }

        @Override
        public void clickVisPreliminar() {
        }

        @Override
        public void clickCancelar() {
        }

        public void cierraConnections() {
        }

        @Override
        public boolean beforeAceptar() {
            return true;
        }

        @Override
        public boolean beforeAnular() {
            return true;
        }

        @Override
        public boolean beforeCancelar() {
            return true;
        }

        @Override
        public boolean beforeConsultar() {
            return true;
        }

        @Override
        public boolean beforeEliminar() {
            return true;
        }

        @Override
        public boolean beforeImprimir() {
            return true;
        }

        @Override
        public boolean beforeInsertar()
        {
            if(!validaCampos())
                return false;
            
            return true;
        }

        @Override
        public boolean beforeModificar() {
            return true;
        }

        @Override
        public boolean beforeVistaPreliminar() {
            return true;
        }
    }

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {

        @Override
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_LINEA:
                    strMsg = "";
                    break;
                case INT_TBL_CODITM:
                    strMsg = "Cóidigo del item.";
                    break;
                case INT_TBL_CODALT:
                    strMsg = "Cóidigo Alterno del item.";
                    break;
                case INT_TBL_NOMITM:
                    strMsg = "Nombre del item.";
                    break;
                case INT_TBL_DESUNI:
                    strMsg = "Descripción de la unidad de medida.";
                    break;
                case INT_TBL_CODBOD:
                    strMsg = "Codigo de la Bodega.";
                    break;
                case INT_TBL_NOMBOD:
                    strMsg = "Nombre de la Bodega.";
                    break;
                case INT_TBL_CANVEN:
                    strMsg = "Cantidad del item Facturado.";
                    break;
                case INT_TBL_CANDEV:
                    strMsg = "Cantidad de item que se Devolvera.";
                    break;
                case INT_TBL_REVTEC:
                    strMsg = "Si necesita revisión técnica.";
                    break;
                case INT_TBL_CANTOTREC:
                    strMsg = "Cantidad total recibida.";
                    break;
                case INT_TBL_CANTOTNUNREC:
                    strMsg = "Cantidad total nunca recibida.";
                    break;

                case INT_TBL_CAN_TEC_REV_ACE:
                    strMsg = "Cantidad total aceptada en la revisión tecnica.";
                    break;
                case INT_TBL_CAN_TEC_REV_REH:
                    strMsg = "Cantidad total rechazada en la revisión tecnica.";
                    break;
                case INT_TBL_CAN_BOD_REV_ACE:
                    strMsg = "Cantidad total aceptada en la revisión de bodega.";
                    break;
                case INT_TBL_CAN_BOD_REV_REH:
                    strMsg = "Cantidad total rechazada en la revisión de bodega.";
                    break;

                case INT_TBL_CANREC:
                    strMsg = "Cantidad recibida.";
                    break;
                case INT_TBL_OBSCANREC:
                    strMsg = "Observación cantidad recibida.";
                    break;
                case INT_TBL_CANACE:
                    strMsg = "Cantidad aceptada.";
                    break;
                case INT_TBL_CANRCH:
                    strMsg = "Cantidad rechazada.";
                    break;
                case INT_TBL_OBSREVCONFING:
                    strMsg = "Observación de la revisión confirmada de ingreso/egreso.";
                    break;
                case INT_TBL_OBSREVTEC:
                    strMsg = "Observación de la revisión técnica.";
                    break;

                case INT_TBL_CANTOTDEVCLI:
                    strMsg = "Cantidad Total Devuelta al Cliente.";
                    break;
                case INT_TBL_CANDEVCLI:
                    strMsg = "Cantidad Devuelta al Cliente.";
                    break;
                case INT_TBL_OBSDEVCLI:
                    strMsg = "Observación de la Cantidad Devuelta al Cliente.";
                    break;
                case INT_TBL_FALSTKFIC:
                    strMsg = "Devolución por faltante de stock.";
                    break;

                case INT_TBL_FALSTKFICACE:
                    strMsg = "Acepta Devolución por faltante de stock.";
                    break;

                case INT_TBL_CANSDEVPRV:
                    strMsg = "Cantidad que Se Devolución al proveedor.";
                    break;

                case INT_TBL_CANNDEVPRV:
                    strMsg = "Cantidad que No se Devolución al proveedor.";
                    break;


                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
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
    private class ZafThreadGUI extends Thread {

        private int intIndFun;

        public ZafThreadGUI() {
            intIndFun = 0;
        }

        @Override
        public void run() {
            switch (intIndFun) {
                case 0: //Botón "Imprimir".
                    objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                    objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUI = null;
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
     *
     */
    private boolean generarRpt(int intTipRpt) 
    {
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes = true;
        try 
        {
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
            Map mapPar = new HashMap();
            if (objRptSis.getOpcionSeleccionada() == ZafRptSis.INT_OPC_ACE) 
            {
                //Obtener la fecha y hora del servidor.
                datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                strFecHorSer = objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                datFecAux = null;
                intNumTotRpt = objRptSis.getNumeroTotalReportes();
                for (i = 0; i < intNumTotRpt; i++) 
                {
                    if (objRptSis.isReporteSeleccionado(i)) {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i))) 
                        {
                            case 476: //Orden de Almacenamiento
                                strRutRpt = objRptSis.getRutaReporte(i);
                                strNomRpt = objRptSis.getNombreReporte(i);
                                strOrdAlm = "";
                                strOrdAlm += " SELECT a2.co_emp,a2.co_loc,a2.co_tipDoc,a4.tx_desCor as tx_desTipDoc,a2.co_doc,a1.ne_numDoc,a2.co_reg,a3.co_itm,\n";
                                strOrdAlm += "        CASE WHEN a5.st_impord='S' THEN ";
                                strOrdAlm += "        (substring(a9.tx_codalt2 from 1 for 1 ) || ' ' || substring(a9.tx_codalt2 from 2 for 1 ) || ' ' || substring(a9.tx_codalt2 from 3 for  1 ))";
                                strOrdAlm += "        ELSE a3.tx_codAlt END AS tx_codAlt,\n";
                                strOrdAlm += "        CASE WHEN a5.st_impord='S' THEN a5.tx_ubi ELSE '' END AS tx_ubi,\n";
                                strOrdAlm += "        CASE WHEN a5.st_impord='N' THEN a3.tx_nomItm ELSE '' END AS tx_nomItm,\n";
                                strOrdAlm += "        a3.tx_uniMed, a8.co_bodgrp as co_bod,";
                                if (objZafParSis.getCodigoMenu() == 1908) {
                                    strOrdAlm += " a2.nd_canRevTecAce as nd_can,\n";
                                } else if (objZafParSis.getCodigoMenu() == 1888) {
                                    strOrdAlm += " a2.nd_canRevBodAce as nd_can,\n";
                                }
                                strOrdAlm += "        a6.tx_nom as tx_nomEmp, a7.tx_nom as tx_nomLoc \n";
                                strOrdAlm += " FROM tbm_cabRecMerSolDevVen as a1 \n";
                                strOrdAlm += " INNER JOIN tbm_detRecMerSolDevVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
                                strOrdAlm += " INNER JOIN tbm_detSolDevVen as a3 ON (a2.co_emp=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_tipDocRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc AND a2.co_regRel=a3.co_reg) \n";
                                strOrdAlm += " INNER JOIN tbm_cabTipDoc as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc) \n";
                                strOrdAlm += " INNER JOIN tbm_invBod as a5 ON (a3.co_emp=a5.co_emp AND a3.co_bod=a5.co_bod AND a3.co_itm=a5.co_itm) \n";
                                strOrdAlm += " INNER JOIN tbm_loc as a6 ON (a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc) \n";
                                strOrdAlm += " INNER JOIN tbm_emp as a7 ON (a1.co_emp=a7.co_emp) \n";
                                strOrdAlm += " INNER JOIN tbr_bodEmpBodGrp as a8 ON (a8.co_Emp=a3.co_Emp and a8.co_bod=a3.co_bod) \n";
                                strOrdAlm += " LEFT OUTER JOIN tbm_inv as a9 ON (a3.co_Emp=a9.co_emp AND a3.co_itm=a9.co_itm) \n"; //Rose
                                strOrdAlm += " WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa() + " \n";
                                strOrdAlm += " AND a1.co_loc=" + objZafParSis.getCodigoLocal() + " \n";
                                strOrdAlm += " AND a1.co_tipDoc=" + txtCodTipDoc.getText() + " \n";
                                strOrdAlm += " AND a1.co_doc=" + txtCodDoc.getText() + " \n";
                                if (objZafParSis.getCodigoMenu() == 1908) {
                                    strOrdAlm += " AND a2.nd_canRevTecAce > 0 \n";
                                } else if (objZafParSis.getCodigoMenu() == 1888) {
                                    strOrdAlm += " AND a2.nd_canRevBodAce > '0.000000' \n";
                                }
            
                                //Inicializar los parametros que se van a pasar al reporte.
                                mapPar.put("strSQLRep", strOrdAlm);
                                mapPar.put("strCamAudRpt", objZafParSis.getNombreUsuario() + "   " + strFecHorSer + "   " + this.getClass().getName() + "   " + strNomRpt);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                System.out.println("OrdenAlmacenamiento:  " + strOrdAlm);
                                break;
                            default: //Ticket de Devolución
                                if ((objZafParSis.getCodigoEmpresa() == 2 && objZafParSis.getCodigoLocal() == 4) || (objZafParSis.getCodigoEmpresa() == 2 && objZafParSis.getCodigoLocal() == 6))//Manta y Sto Domingo tienen Impresora Normal.
                                {
                                    strRutRpt = objRptSis.getRutaReporte(i) + "/Castek/";
                                } else //Formato Impresora Etiquetera
                                {
                                    strRutRpt = objRptSis.getRutaReporte(i);
                                }

                                strNomRpt = objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.
                                mapPar.put("codEmp", new Integer(objZafParSis.getCodigoEmpresa()));
                                mapPar.put("codLoc", new Integer(objZafParSis.getCodigoLocal()));
                                mapPar.put("codTipDoc", new Integer(Integer.parseInt(txtCodTipDoc.getText())));
                                mapPar.put("codDoc", new Integer(Integer.parseInt(txtCodDoc.getText())));
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                // mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    @Override
    protected void finalize() throws Throwable {
        //System.gc();
        Runtime.getRuntime().gc();
        super.finalize();
    }

    
        
    /**
     * Procesa devolucion de venta de manera automática.
     * @param intCodEmp Código de la empresa de la solicitud.
     * @param intCodLoc Código del local de la solicitud.
     * @param intCodTipDoc Código del tipo de documento de la solicitud.
     * @param intCodDoc Código de documento de la solicitud.
     * @return 
     */
    private boolean procesaDevolucion(int intCodEmp, int intCodLoc, int intCodTipDoc,int intCodDoc)
    {
        boolean blnRes=false;
        java.sql.Connection  conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        ZafCorEle objCorEle=null;
        try
        {
            objCorEle = new ZafCorEle(objZafParSis);  
            conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if(conn!=null){
                stmLoc=conn.createStatement();
             
                strSql="";
                strSql+="  select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli, a.fe_doc,   a.ne_numdoc, a3.ne_numdoc as numfac  ,a.fe_aut \n";
                strSql+="   from( \n";
                strSql+="       select * \n";
                strSql+="       from( \n";
                strSql+="           select \n";
                strSql+="                   ( \n";
                strSql+="                       select  sum(x1.nd_cansolnodevprv) as  cansolstk   \n";
                strSql+="                       from tbm_cabsoldevven as x  \n";
                strSql+="                       inner join tbm_detsoldevven as x1 on (x1.co_emp=x.co_emp and x1.co_loc=x.co_loc and x1.co_tipdoc=x.co_tipdoc and x1.co_doc=x.co_doc)   \n";
                strSql+="                       where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc   \n";
                strSql+="                   ) as   cansolstk, \n";
                strSql+="                   CASE WHEN (  \n";
                strSql+="                               select sum((x1.nd_candev - x1.nd_canvolfac)) as dat from  tbm_cabsoldevven as x  \n";
                strSql+="                               inner join tbm_detsoldevven as x1 on (x1.co_emp=x.co_emp and x1.co_loc=x.co_loc and x1.co_tipdoc=x.co_tipdoc and x1.co_doc=x.co_doc )  \n";
                strSql+="                               where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc \n";
                strSql+="                   ) != 0  THEN 'N' ELSE 'S' END as st_volfac, \n";
                /*MODIFICADO POR EL PROYECTO TRANSFERENCIAS 19-07-2016 */
                /* (SELECT sum((y2.nd_can - (y1.nd_candev - y1.nd_canvolfac) - y2.nd_cancon)) as nd_canconguirem */
                strSql+="               ( \n";
                strSql+="                   SELECT case when y2.co_tipdoc=102 then sum((y2.nd_can - (y1.nd_candev - y1.nd_canvolfac) - y2.nd_cancon))  \n";
                strSql+="                           else sum((abs(det.nd_can) - (y1.nd_candev - y1.nd_canvolfac) - abs(det.nd_cancon))) end as nd_canconguirem     \n";
                /*MODIFICADO POR EL PROYECTO TRANSFERENCIAS 19-07-2016 */   
                strSql+="                   FROM tbm_detsoldevven as y1  \n";
                strSql+="                   INNER JOIN tbm_detguirem as y2 on (y2.co_emprel=y1.co_emp  and y2.co_locrel=y1.co_locrel and y2.co_tipdocrel=y1.co_tipdocrel and y2.co_docrel=y1.co_docrel and y2.co_regrel=y1.co_regrel )  \n";
                /*MODIFICADO POR EL PROYECTO TRANSFERENCIAS 19-07-2016 */
                strSql+="                   INNER JOIN tbm_detmovinv as det on (y1.co_emp=det.co_emp  and y1.co_locrel=det.co_loc and y1.co_tipdocrel=det.co_tipdoc and y1.co_docrel=det.co_doc and y1.co_regrel=det.co_reg ) \n";
                /*MODIFICADO POR EL PROYECTO TRANSFERENCIAS 19-07-2016 */
                strSql+="                   WHERE y1.co_emp=a.co_emp and y1.co_loc=a.co_loc and y1.co_tipdoc=a.co_tipdoc and y1.co_doc=a.co_doc and y1.co_reg=a1.co_reg and y1.nd_candev > 0 and y2.st_meregrfisbod = 'S'  \n";
                strSql+="                   group by y2.co_tipdoc  \n";
                
                /*MODIFICADO POR RESERVAS DE MERCADERIAS */
                strSql+=" union  \n";

		strSql+="  SELECT case when a5.co_tipdoc=102 then  \n";
		strSql+="  sum((a5.nd_can - (y1.nd_candev - y1.nd_canvolfac) - a5.nd_cancon))  \n";
		strSql+="  else  \n";
		strSql+="  sum((abs(detres.nd_can) - (y1.nd_candev - y1.nd_canvolfac) - abs(detres.nd_cancon)))  \n";
		strSql+="  end as nd_canconguirem  \n";
		strSql+="  FROM tbm_detsoldevven as y1  \n";
		strSql+="    INNER JOIN tbm_detmovinv as det on (y1.co_emp=det.co_emp  and y1.co_locrel=det.co_loc and y1.co_tipdocrel=det.co_tipdoc and y1.co_docrel=det.co_doc and y1.co_regrel=det.co_reg )  \n";
		strSql+="  INNER JOIN tbm_cabsegmovinv as s  \n";
		strSql+="  on (s.co_emprelcabmovinv =det.co_emp and s.co_locrelcabmovinv=det.co_loc and s.co_tipdocrelcabmovinv=det.co_tipdoc and s.co_docrelcabmovinv=det.co_doc)  \n";
		strSql+="  INNER JOIN tbm_cabsegmovinv as s2  \n";
		strSql+="  on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271)  \n";
		strSql+="  INNER JOIN tbm_cabguirem as a3  \n";
		strSql+="  on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem )  \n";
		strSql+="  INNER JOIN tbm_detguirem as a5  \n";
		strSql+="  on(a5.co_emp=a3.co_emp and a5.co_loc=a3.co_loc and a5.co_tipdoc=a3.co_tipdoc and a5.co_doc=a3.co_doc and a5.co_reg=y1.co_reg)  \n";
		strSql+="  INNER JOIN tbm_detmovinv as detres  \n";
		strSql+="  on (detres.co_emp=a5.co_emprel  and detres.co_loc=a5.co_locrel  and detres.co_tipdoc=a5.co_tipdocrel and detres.co_doc=a5.co_docrel and detres.co_reg=a5.co_regrel and detres.co_tipdoc=294)  \n";
		strSql+="  WHERE y1.co_emp=a.co_emp  \n";
		strSql+="  and y1.co_loc=a.co_loc  \n";
		strSql+="  and y1.co_tipdoc=a.co_tipdoc  \n";
		strSql+="  and y1.co_doc=a.co_doc  \n";
		strSql+="  and y1.co_reg=a1.co_reg  \n";
		strSql+="  and y1.nd_candev > 0  \n";
		strSql+="  and a5.st_meregrfisbod = 'S'  \n";
		strSql+="   group by a5.co_tipdoc  \n";
                strSql+="               ) as nd_canconguirem,  \n";                
                
               /*MODIFICADO POR RESERVAS DE MERCADERIAS */ 
                
                strSql+="               a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc , a.co_locrel, a.co_tipdocrel,   \n";
                strSql+="               a.co_docrel, a.co_cli, a.tx_nomcli, a.fe_doc,  a.ne_numdoc, a.fe_aut ,a.st_tipDev, a.st_RevBodMerDev, a.st_meraceingsis, a1.nd_canRevBodAce , a.st_impguiremaut   \n";
                strSql+="           FROM tbm_cabsoldevven AS a  \n";
                strSql+="           INNER JOIN  tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc )  \n";
                strSql+="           WHERE a.co_Emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipDoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and \n";
                strSql+="                   a.st_reg ='A'  AND a.st_aut ='A' AND a.st_meraceingsis='N'  \n";
                strSql+="       ) AS x \n";
                strSql+=" WHERE CASE WHEN ((st_impguiremaut = 'S') or (st_impguiremaut = 'N' and nd_canconguirem < 0)) THEN  \n";
                strSql+="       CASE WHEN x.st_volfac IN ('N') THEN  \n";
                strSql+="       CASE WHEN x.st_tipDev  IN ('C') THEN  x.st_RevBodMerDev='S' AND x.st_meraceingsis='N' AND x.nd_canRevBodAce > 0  \n";
                strSql+="           ELSE x.st_meraceingsis='N' END ELSE x.st_meraceingsis='N' END  \n";
                strSql+="           ELSE  CASE  WHEN x.cansolstk = 0 THEN x.st_meraceingsis='N'  \n";
                strSql+="               ELSE CASE WHEN x.st_tipDev  IN ('C') THEN  x.st_RevBodMerDev='S' AND x.st_meraceingsis='N' AND x.nd_canRevBodAce > 0  \n";
                strSql+="               ELSE x.st_meraceingsis='N' END  \n";
                strSql+="           END  \n";
                strSql+="       END   \n";
                strSql+=" ) AS a  \n";
                strSql+=" INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc)  \n";
                strSql+=" INNER JOIN tbm_cabmovinv AS a3 ON(a3.co_emp=a.co_emp AND a3.co_loc=a.co_locrel AND a3.co_tipdoc=a.co_tipdocrel AND a3.co_doc=a.co_docrel)  \n";
                strSql+=" GROUP BY a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc,  a2.tx_descor, a2.tx_deslar,  a.co_cli, a.tx_nomcli, a.fe_doc,  a.ne_numdoc, a3.ne_numdoc,a.fe_aut \n";
                strSql+=" ORDER BY a.ne_numdoc \n";
                strSql+=" \n";
                strSql+=" \n";
                System.out.println("JOTA >>> ZafVen27_01 consultarDat (Modificado Transferencias de Inventario JM):  " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                {
                    objCorEle.enviarCorreoMasivo("sistemas5@tuvalsa.com", "Devolucion automatica pasa contabilidad","Solicitud Devolucion pasa contabilidad despues de receptar "+ intCodEmp+" "+intCodLoc+" "+intCodTipDoc+" "+intCodDoc);
                     objZafVen28=new ZafVen28(objZafParSis,intCodEmp,intCodLoc,intCodTipDoc,intCodDoc, 
                                              String.valueOf(intCodEmp), String.valueOf(intCodLoc), String.valueOf(intCodTipDoc), 
                                              String.valueOf(intCodDoc), this );
                }
                rstLoc.close();
                stmLoc.close();
                stmLoc=null;
                rstLoc=null;
                conn.close();
                conn=null;

            }
                
        }
        catch(java.sql.SQLException e)  
        {   
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;  
        }
        catch(Exception Evt)  
        {  
            objUti.mostrarMsgErr_F1(this, Evt);
            objCorEle.enviarCorreoMasivo("sistemas5@tuvalsa.com", "Devolucion automatica","Solicitud Devolucion error al generar devolucion ZafVen27 catch \n\r"+ intCodEmp+" "+intCodLoc+" "+intCodTipDoc+" "+intCodDoc+"\n\r"+ Evt.toString());            
            blnRes=false;  
        }

            return blnRes;
    }        

    private boolean imprimirOrdenAlmacenamiento() 
    {
        boolean blnRes = true;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        java.util.Date datFecAux;
        String strFecHorSer, strNomPrnSer = "", strRutRepOrdAlm = "";
        String strRutImp="", strRutRpt ="", strNomRpt="", strRutRelRpt="", strRutAbsRpt="" ;  
        int intCodBod = 0;
        int intNumTotRpt=-1;
        try 
        { 
            conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos()); 
            if(conLoc!=null)
            {
                datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                strFecHorSer = objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                datFecAux = null;    
                
                objRptSis.cargarListadoReportes(null);
                intNumTotRpt=objRptSis.getNumeroTotalReportes(); 
                for (int i=0;i<intNumTotRpt;i++) 
                {                
                    switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                    { 
                        case 476:    
                            strRutRpt=objRptSis.getRutaReporte(i);  
                            strRutRelRpt=objRptSis.getRutaRelativaReporte(i);  
                            strRutAbsRpt=objRptSis.getRutaAbsolutaReporte(i);  
                            strNomRpt=objRptSis.getNombreReporte(i); //Nombre del Reporte  
                            
                            //Ruta del Reporte
                            if (System.getProperty("os.name").equals("Linux")) {//Linux
                                strRutRepOrdAlm = "/Zafiro"+strRutRelRpt+""; 
                            }
                            else {//Windows
                                strRutRepOrdAlm = strRutRpt; 
                            }    
                            strRutImp=strRutRepOrdAlm+strNomRpt;

                            //Armar sentencia SQL
                            stmLoc = conLoc.createStatement();  
                            strOrdAlm ="";
                            strOrdAlm+=" SELECT a2.co_emp,a2.co_loc,a2.co_tipDoc,a4.tx_desCor as tx_desTipDoc,a2.co_doc,a1.ne_numDoc,a2.co_reg,a3.co_itm";
                            strOrdAlm+="      , CASE WHEN a5.st_impord='S' THEN (substring(a9.tx_codalt2 from 1 for 1 ) || ' ' || substring(a9.tx_codalt2 from 2 for 1 ) || ' ' || substring(a9.tx_codalt2 from 3 for  1 ))";
                            strOrdAlm+="        ELSE a3.tx_codAlt END AS tx_codAlt";
                            strOrdAlm+="      , CASE WHEN a5.st_impord='S' THEN a5.tx_ubi ELSE '' END AS tx_ubi";
                            strOrdAlm+="      , CASE WHEN a5.st_impord='N' THEN a3.tx_nomItm ELSE '' END AS tx_nomItm";
                            strOrdAlm+="      , a3.tx_uniMed, a8.co_bodgrp as co_bod";
                            if (objZafParSis.getCodigoMenu() == 1908)    {
                                strOrdAlm+=" , a2.nd_canRevTecAce as nd_can";
                            }
                            else if (objZafParSis.getCodigoMenu() == 1888)  {
                                strOrdAlm+=" , a2.nd_canRevBodAce as nd_can";
                            }
                            strOrdAlm+="     , a6.tx_nom as tx_nomEmp, a7.tx_nom as tx_nomLoc\n";
                            strOrdAlm+=" FROM tbm_cabRecMerSolDevVen as a1 \n";
                            strOrdAlm+=" INNER JOIN tbm_detRecMerSolDevVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
                            strOrdAlm+=" INNER JOIN tbm_detSolDevVen as a3 ON (a2.co_emp=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_tipDocRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc AND a2.co_regRel=a3.co_reg) \n";
                            strOrdAlm+=" INNER JOIN tbm_cabTipDoc as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc) \n";
                            strOrdAlm+=" INNER JOIN tbm_invBod as a5 ON (a3.co_emp=a5.co_emp AND a3.co_bod=a5.co_bod AND a3.co_itm=a5.co_itm) \n";
                            strOrdAlm+=" INNER JOIN tbm_loc as a6 ON (a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc) \n";
                            strOrdAlm+=" INNER JOIN tbm_emp as a7 ON (a1.co_emp=a7.co_emp) \n";
                            strOrdAlm+=" INNER JOIN tbr_bodEmpBodGrp as a8 ON (a8.co_Emp=a3.co_Emp and a8.co_bod=a3.co_bod) \n";
                            strOrdAlm+=" LEFT OUTER JOIN tbm_inv as a9 ON (a3.co_Emp=a9.co_emp AND a3.co_itm=a9.co_itm) \n"; //Rose
                            strOrdAlm+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa() + " \n";
                            strOrdAlm+=" AND a1.co_loc=" + objZafParSis.getCodigoLocal() + " \n";
                            strOrdAlm+=" AND a1.co_tipDoc=" +  txtCodTipDoc.getText() + " \n";
                            strOrdAlm+=" AND a1.co_doc=" + txtCodDoc.getText() + " \n";
                            if (objZafParSis.getCodigoMenu() == 1908)   {
                                strOrdAlm+=" AND a2.nd_canRevTecAce > 0 \n";
                            } 
                            else if (objZafParSis.getCodigoMenu() == 1888)     {
                                strOrdAlm+=" AND a2.nd_canRevBodAce > '0.000000' \n";
                            }
                            rstLoc = stmLoc.executeQuery(strOrdAlm);  
                            if (rstLoc.next())   {  
                                intCodBod = rstLoc.getInt("co_bod");   
                            }
                            strNomPrnSer = obtieneNombrePrintServer(blnPruebas, intCodBod) ;
                            stmLoc.close(); 
                            stmLoc = null; 
                            rstLoc.close(); 
                            rstLoc = null;                                 

                            //Inicializar los parametros que se van a pasar al reporte.
                            java.util.Map mapPar = new java.util.HashMap();
                            mapPar.put("strSQLRep", strOrdAlm);
                            mapPar.put("strCamAudRpt", objZafParSis.getNombreUsuario() + "   " + strFecHorSer + "   " + this.getClass().getName() + "   ");

                            javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet = new javax.print.attribute.HashPrintRequestAttributeSet();
                            objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
                            JasperPrint reportGuiaRem = JasperFillManager.fillReport(strRutImp, mapPar, conLoc);
                            javax.print.attribute.standard.PrinterName printerName = new javax.print.attribute.standard.PrinterName(strNomPrnSer, null);
                            javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet = new javax.print.attribute.HashPrintServiceAttributeSet();
                            printServiceAttributeSet.add(printerName);
                            net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp = new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                            objJRPSerExp.exportReport();
                            objPriReqAttSet = null;
                            break;
                    }
                }
                conLoc.close();
                conLoc = null;
            }
        } 
        catch(java.sql.SQLException e)    {   
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;  
        }        
        catch(Exception Evt)     {  
            objUti.mostrarMsgErr_F1(this, Evt);
            blnRes=false;  
        }
        return blnRes;
    }
    
    /**
     * Esta función permite imprimir de forma automática la recepcion de mercaderia de venta por devolucion
     * @return true: Si se pudo imprmir la recepcion.
     * <BR>false: En el caso contrario.
     */
    private boolean imprimirRecepcionDevolucion() 
    {
        boolean blnRes = false;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;       
        java.util.Date datFecAux;
        String strRutImp="", strRutRpt ="", strNomRpt="", strRutRelRpt="", strRutAbsRpt="" ;   
        String strFecHorSer, strNomPrnSer="", strSubRep = "";
        String strSQL="";
        int intCodBod = 0;
        int intNumTotRpt=-1;
        try
        {
            conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos()); 
            if(conLoc!=null)
            {
                datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                strFecHorSer = objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                datFecAux = null;
                
                objRptSis.cargarListadoReportes(null);
                intNumTotRpt=objRptSis.getNumeroTotalReportes(); 
                for (int i=0;i<intNumTotRpt;i++) 
                {                
                    switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                    { 
                        case 284:    //Recepción de devolución
                            strRutRpt=objRptSis.getRutaReporte(i);  
                            strRutRelRpt=objRptSis.getRutaRelativaReporte(i);  
                            strRutAbsRpt=objRptSis.getRutaAbsolutaReporte(i);  
                            strNomRpt=objRptSis.getNombreReporte(i); //Nombre del Reporte  
                            
                            //Ruta del Reporte
                            if (System.getProperty("os.name").equals("Linux")) { //Linux
                                strSubRep = "/Zafiro"+strRutRelRpt+"";  
                            }
                            else { //Windows
                                strSubRep = strRutRpt;  
                            }        
                            strRutImp=strSubRep+strNomRpt;

                            //Armar sentencia SQL
                            stmLoc = conLoc.createStatement(); 
                            strSQL =" SELECT b.co_bodGrp FROM tbm_cabrecmersoldevven AS a   ";
                            strSQL+=" INNER JOIN tbm_detSolDevVen as a2 ON (a2.co_emp=a.co_emp AND a2.co_loc=a.co_locRel AND a2.co_tiPDoc=a.co_tiPDocRel AND a2.co_Doc=a.co_docRel)  ";
                            strSQL+=" INNER JOIN tbr_bodEmpBodGrp as b ON (b.co_emp=a2.co_emp AND b.co_bod=a2.co_bod)";
                            strSQL+=" WHERE a.co_emp="+objZafParSis.getCodigoEmpresa();
                            strSQL+=" AND a.co_loc="+objZafParSis.getCodigoLocal();
                            strSQL+=" AND a.co_tipdoc="+txtCodTipDoc.getText();
                            strSQL+=" AND a.co_doc="+txtCodDoc.getText();
                            strSQL+=" GROUP BY b.co_bodGrp ";
                            rstLoc = stmLoc.executeQuery(strSQL); 
                            if (rstLoc.next())    { 
                                intCodBod = rstLoc.getInt("co_bodGrp"); 
                            }
                            strNomPrnSer = obtieneNombrePrintServer(blnPruebas, intCodBod);
                            stmLoc.close(); 
                            stmLoc = null;
                            rstLoc.close(); 
                            rstLoc = null;                                    

                            //Inicializar los parametros que se van a pasar al reporte.
                            java.util.Map mapPar = new java.util.HashMap();           
                            mapPar.put("codEmp", new Integer(objZafParSis.getCodigoEmpresa()));
                            mapPar.put("codLoc", new Integer(objZafParSis.getCodigoLocal()));
                            mapPar.put("codTipDoc", new Integer(Integer.parseInt(txtCodTipDoc.getText())));
                            mapPar.put("codDoc", new Integer(Integer.parseInt(txtCodDoc.getText())));
                            mapPar.put("SUBREPORT_DIR", strSubRep);
                            // mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);

                            //Impresion Automatica
                            javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet = new javax.print.attribute.HashPrintRequestAttributeSet();
                            objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
                            JasperPrint reportGuiaRem = JasperFillManager.fillReport(strRutImp, mapPar, conLoc);
                            javax.print.attribute.standard.PrinterName printerName = new javax.print.attribute.standard.PrinterName(strNomPrnSer, null);
                            javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet = new javax.print.attribute.HashPrintServiceAttributeSet();
                            printServiceAttributeSet.add(printerName);
                            net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp = new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                            objJRPSerExp.exportReport();
                            objPriReqAttSet = null;
                            break;
                    }
                }
                conLoc.close();
                conLoc = null;
            }
        } 
        catch(java.sql.SQLException e)    {   
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;  
        }        
        catch(Exception Evt)     {  
            objUti.mostrarMsgErr_F1(this, Evt);
            blnRes=false;  
        }
        return blnRes;
    } 
    
    private String obtieneNombrePrintServer(Boolean blnPruebas, int intCodBod)  
    {
        String strNomPrnSer = "";
        if(blnPruebas)  {
            strNomPrnSer="laser_sistemas";
        }
        else
        {
            switch (intCodBod) 
            {
                case 1://California
                    strNomPrnSer="od_califormia";
                    break;
                case 2://Vía Daule
                    strNomPrnSer="od_dimulti";
                    break;
                case 3://Quito Norte
                    strNomPrnSer="od_quito";
                    break;
                case 5://Manta
                    strNomPrnSer="od_manta";
                    break;
                case 11://Santo Domingo
                    strNomPrnSer="od_stodgo";
                    break;
                case 15://Inmaconsa
                    strNomPrnSer="od_inmaconsa";
                    break;
                case 28://Cuenca
                    strNomPrnSer="od_cuenca";
                    break;
                default:
                    break;
            }
        }
        return strNomPrnSer;
    }
    
    
}