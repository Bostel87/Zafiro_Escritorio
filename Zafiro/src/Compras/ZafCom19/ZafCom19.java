/*
 * Confirmaciones de Ingreso/Egreso de Bodega 
 *
 * Created on 13 de agosto de 2008, 11:41  
 */
package Compras.ZafCom19;

import Compras.ZafCom33.ZafCom33;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafObtConCen.ZafObtConCen;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.UltDocPrint;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafValCedRuc.VerificarId;
import Librerias.ZafVenCon.ZafVenCon;
import ZafReglas.ZafGuiRem.ZafGenGuiRem;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Tnlg. Javier Ayapata
 *
 */
public class ZafCom19 extends javax.swing.JInternalFrame 
{

    //Constantes
    //JTable: tblDat
    final int INT_TBL_LINEA = 0;
    final int INT_TBL_CODITM = 1;
    final int INT_TBL_CODALT = 2;
    final int INT_TBL_CODALTDOS = 3;
    final int INT_TBL_NOMITM = 4;
    final int INT_TBL_DESUNI = 5;
    final int INT_TBL_CANMOV = 6;
    final int INT_TBL_CANTOTCON = 7;
    final int INT_TBL_CANCON = 8;
    final int INT_TBL_CANNUMREC = 9;
    final int INT_TBL_CANMALEST = 10;
    final int INT_TBL_CANTOTNUMREC = 11;
    final int INT_TBL_OBSITMCON = 12;
    final int INT_TBL_CANCON_ORI = 13;
    final int INT_TBL_CODREG = 14;
    final int INT_TBL_CODBOD = 15;
    final int INT_TBL_CANNUMREC_ORI = 16;
    final int INT_TBL_CANMALEST_ORI = 17;
    final int INT_TBL_IEBODFIS = 18;             //Estado que indica si ingresa/egresa fisicamente la mercadería en bodega.
    final int INT_TBL_CANTOTMALEST = 19;
    final int INT_TBL_CODEMPREL = 20;
    final int INT_TBL_CODLOCREL = 21;
    final int INT_TBL_CODTIDOREL = 22;
    final int INT_TBL_CODDOCREL = 23;
    final int INT_TBL_CODREGREL = 24;
    final int INT_TBL_TXNOMBODREL = 25;
    final int INT_TBL_CODBODREL = 26;
    final int INT_TBL_PESKGR = 27;
    final int INT_TBL_CODALTAUX = 28;
    final int INT_TBL_NOMITMAUX = 29;
    final int INT_TBL_CODREGRELDOCEGR = 30; /* JoseMario 7/Jul/2016 */ 

    //Variables
    private ZafInvItm objInvItm;
    private ZafTblMod objTblMod, objTblModAux;
    private ZafParSis objZafParSis;
    private ZafDatePicker txtFecDoc;
    private UltDocPrint objUltDocPrint;
    private ZafUtil objUti;
    private mitoolbar objTooBar;
    private ZafObtConCen objObtConCen;
    private ZafTblCelEdiTxt objTblCelEdiTxtCanConf;
    private ZafTblCelEdiTxt objTblCelEdiTxtCanNumRec, objTblCelEdiTxtCanMalEst;
    private ZafTblCelEdiTxt objTblCelEdiTxt, objTblCelEdiTxtCodAlt;
    private ZafRptSis objRptSis;  //Reportes del Sistema.
    private ZafThreadGUI objThrGUI;
    private ZafVenCon vcoTipDoc;
    private ZafVenCon vcoBodUsr;
    private ZafVenCon vcoVeh;
    private ZafVenCon vcoCho;

    private Vector vecAux;
    Vector vecCab = new Vector();
    boolean blnHayCam = false;
    private boolean blnMarTodCanTblDat = true;            //Coloca en todas las celdas la cantidad vendida.
    Connection CONN_GLO = null, conCab = null, conRemGlo = null;
    Statement STM_GLO = null;
    ResultSet rstCab = null;

    int INTCODREGCEN = 0;
    int INTVERCONCEN = 0;
    String STR_COD_EMP_REL = "";
    String STR_COD_LOC_REL = "";
    String STR_COD_BOD_GUIA = "";
    String STR_COD_REG_REL = "";
    String STR_CAN_CON = "";
    String STRCODEMPTRANS = "";
    String STRCODLOCTRANS = "";
    String STRCODTIPTRANS = "";
    String STRCODDOCTRANS = "";
    String strNomBodIng = "";
    boolean blnGloInsDat = false;

    //javax.swing.JTextField txtTipMovDoc = new javax.swing.JTextField();
    JTextField txtNumDocSolOcu = new JTextField();
    JTextField txtCodTipDocCon = new JTextField();
    JTextField txtCodTipDoc = new JTextField();
    JTextField txtCodLocRel = new JTextField();

    String strCodBod = "", strNomBod = "";
    String strCodTipDoc = "", strDesCorTipDoc = "", strDesLarTipDoc = "";
    String strAux = "";
    String strMerIngEgr = "", strTipIngEgr = "";
    String strTipModIngEgr = "";
    String strCodEmpConf = "";
    String strCodLocConf = "";
    String strCodTipDocConf = "";
    String strCodDocConf = "";
    String strCodLocRelConf = "";
    String strCodTipDocRelConf = "";
    String strCodDocRelConf = "";
    String strFecSisBase = "";
    private int intTipIngEgr = 0;  // 1 Egr, 2 Ing
    private boolean blnEstCarConf = false;
    private boolean blnEstCarConfIns = false;
    private JInternalFrame jfrThis;
    private final String strVersion = " v4.35 ";
    private final String strTit = "Mensaje del sistema Zafiro";
    private final String strFecDoc = "2012-08-01";           // AP. GERENCIA. NO PERMITIR CONFIRMAR EN BODEGA ORDDES MENORES A ESTA FECHA
    private final String DATE_FORMAT = "yyyy-MM-dd";
    private String strSQL;

    VerificarId validadIDRuc;
    JTextField txtRucCliPro = new JTextField();
    String strCodVeh = "", strPlaVeh = "", strDesLarVeh = "", strPesVeh = "";
    JTextField txtCodVeh = new JTextField();
    JTextField txtPesVeh = new JTextField();
    String strCodCho = "", strIdeCho = "", strNomCho = "";
    JTextField txtCodCho = new JTextField();
    String STR_COD_EMP_REL_COM_CLI_RET = "";
    private java.util.Date datFecAux;                        //Auxiliar: Para almacenar fechas.

    private String strCodEmpOrdAlm = "", strCodLocOrdAlm = "";

    private ZafStkInv objStkInv;
    /**
     * Creates new form revisionTecMer
     */
    public ZafCom19(Librerias.ZafParSis.ZafParSis obj) 
    {
        try 
        {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();
            this.setTitle(objZafParSis.getNombreMenu() + strVersion);
            lblTit.setText(objZafParSis.getNombreMenu());
            objUti = new ZafUtil();
            objRptSis = new ZafRptSis(JOptionPane.getFrameForComponent(this), true, objZafParSis);
            objTooBar = new mitoolbar(this);
            this.getContentPane().add(objTooBar, "South");
            objInvItm = new ZafInvItm(this, objZafParSis);
            objObtConCen = new ZafObtConCen(objZafParSis);
            objUltDocPrint = new UltDocPrint(objZafParSis);
            objStkInv = new Librerias.ZafStkInv.ZafStkInv(objZafParSis);
            INTCODREGCEN = objObtConCen.intCodReg;
            jfrThis = this;

            if (objZafParSis.getCodigoMenu() == 2205) 
            {
                butBusNumDocCon.setVisible(false);
            }
            else
            {
                lblNumGuiDes.setVisible(false);
                txtNumGuiDes.setVisible(false);
                butBusCodDoc.setVisible(false);
                if (objZafParSis.getCodigoMenu() != 2063) 
                {
                    tabFrm.remove(panDatDsp);
                }
            }
            if (objZafParSis.getCodigoMenu() == 2915) 
            {
                lblNumDocCon1.setVisible(false);
                txtNumDocCon.setVisible(false);
                butBusNumDocCon.setVisible(false);
            }
            txtFecDoc = new ZafDatePicker(((JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new Dimension(70, 20));
            txtFecDoc.setText("");
            PanTabGen.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);
            validadIDRuc = new VerificarId();
        }
        catch (CloneNotSupportedException e) {  objUti.mostrarMsgErr_F1(this, e);  }
    }

    public ZafCom19(Librerias.ZafParSis.ZafParSis obj, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strNumDoc, String strCodBodOri, String strCodReg, String strCodBodDst, String strCanCon) 
    {
        try 
        {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            objZafParSis.setCodigoMenu(286);
            initComponents();
            this.setTitle("Confirmación de ingresos a bodega..." + strVersion);
            lblTit.setText("Confirmación de ingresos a bodega...");
            this.setClosable(false);
            this.setMaximizable(false);
            this.setIconifiable(false);
            objUti = new ZafUtil();
            objTooBar = new mitoolbar(this);
            this.getContentPane().add(objTooBar, "South");
            objInvItm = new ZafInvItm(this, objZafParSis);
            objObtConCen = new ZafObtConCen(objZafParSis);
            objUltDocPrint = new UltDocPrint(objZafParSis);
            objRptSis = new ZafRptSis(JOptionPane.getFrameForComponent(this), true, objZafParSis);
            INTCODREGCEN = objObtConCen.intCodReg;
            jfrThis = this;
            butBusNumDocCon.setVisible(false);
            if (objZafParSis.getCodigoMenu() == 2205) 
            {
                butBusNumDocCon.setVisible(false);
            } 
            else 
            {
                lblNumGuiDes.setVisible(false);
                txtNumGuiDes.setVisible(false);
                butBusCodDoc.setVisible(false);
                if (objZafParSis.getCodigoMenu() != 2063) 
                {
                    tabFrm.remove(panDatDsp);
                }
            }
            if (objZafParSis.getCodigoMenu() == 2915)
            {
                lblNumDocCon1.setVisible(false);
                txtNumDocCon.setVisible(false);
                butBusNumDocCon.setVisible(false);
            }
            txtFecDoc = new ZafDatePicker(((JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new Dimension(70, 20));
            txtFecDoc.setText("");
            PanTabGen.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);

            configurarTablaConf();
            configurarVenConTipDoc();
            configurarVenConBodUsr();
            //objUti.activarCom(this);
            objTooBar.clickInsertar();
            objTooBar.setEstado('n');
            txtCodBod.setText(strCodBodDst);
            BuscarBod("a.co_bod", txtCodBod.getText(), 0);
            txtCodTipDocCon.setText(strCodTipDoc);
            txtCodDocCon.setText(strCodDoc);
            txtNumDocCon.setText(strNumDoc);
            STR_COD_EMP_REL = strCodEmp;
            STR_COD_LOC_REL = strCodLoc;
            STR_COD_BOD_GUIA = strCodBodOri;
            STR_COD_REG_REL = strCodReg;
            blnEstCarConfIns = true;
            STR_CAN_CON = strCanCon;
            tabFrm.setSelectedIndex(1);
        }
        catch (CloneNotSupportedException e) {  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e) {   e.printStackTrace();  }
    }

    /**
     * Creates new form revisionTecMer
     */
    public ZafCom19(Librerias.ZafParSis.ZafParSis obj, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodLocRel, String strCodTipDocRel, String strCodDocRel, int intTipo) 
    {
        try 
        {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();
            this.setTitle(objZafParSis.getNombreMenu() + strVersion);
            lblTit.setText(objZafParSis.getNombreMenu());
            objUti = new ZafUtil();
            strCodEmpConf = strCodEmp;
            strCodLocConf = strCodLoc;
            strCodTipDocConf = strCodTipDoc;
            strCodDocConf = strCodDoc;
            strCodLocRelConf = strCodLocRel;
            strCodTipDocRelConf = strCodTipDocRel;
            strCodDocRelConf = strCodDocRel;
            blnEstCarConf = true;
            intTipIngEgr = intTipo;
            objTooBar = new mitoolbar(this);
            objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);
            objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objZafParSis);
            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);
            INTCODREGCEN = objObtConCen.intCodReg;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            PanTabGen.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);
            objRptSis = new ZafRptSis(JOptionPane.getFrameForComponent(this), true, objZafParSis);
            if (intTipIngEgr == 2) {
                tabFrm.remove(panDatDsp);
            }
        } 
        catch (CloneNotSupportedException e)  {  objUti.mostrarMsgErr_F1(this, e);   }
    }

    public void setEditable(boolean editable) 
    {
        if (editable == true) 
        {
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
        } 
        else
        {
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
        }
    }

    public void abrirCon() 
    {
        try 
        {
            CONN_GLO = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
        } 
        catch (java.sql.SQLException Evt) {   objUti.mostrarMsgErr_F1(this, Evt); }
    }

    public void CerrarCon() 
    {
        try 
        {
            CONN_GLO.close();
            CONN_GLO = null;
        } 
        catch (java.sql.SQLException Evt) {     objUti.mostrarMsgErr_F1(this, Evt);   }
    }

    public void Configura_ventana_consulta() 
    {
        if (objZafParSis.getCodigoMenu() == 286) 
        {
            configurarTablaConf();
        }
        if ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915)) 
        {
            configurarTablaConf();
            configurarVenConVehiculo();
            configurarVenConChofer();
        }
        if (objZafParSis.getCodigoMenu() == 1974) 
        {
            configurarTablaIngPro();
        }
        if (objZafParSis.getCodigoMenu() == 2063) 
        {
            configurarTablaConf();
            configurarVenConVehiculo();
            configurarVenConChofer();
        }
        if (objZafParSis.getCodigoMenu() == 2073)
        {
            configurarTablaConf();
        }
        configurarVenConTipDoc();
        configurarVenConBodUsr();
        if (blnEstCarConf) 
        {
            if (intTipIngEgr == 1) { //OJOJO
                configurarTablaConf();
            }
            if (intTipIngEgr == 2) {
                configurarTablaConf();
            }
            cargarDatos(strCodEmpConf, strCodLocConf, strCodTipDocConf, strCodDocConf, intTipIngEgr);
        }
        if (blnEstCarConfIns) 
        {
            cargarDoc();
        }
    }

    /**
     * Esta Función permite cargar el documento de confirmación.
     *
     * @param intCodEmp Código de empresa a consultar
     * @param intCodLoc Código de local a consultar
     * @param intCodTipDoc Código de tipo de documento a consultar
     * @param intCodDoc Código de documento a consultar
     * @param intTipIE tipo de documento si es Ingreso o egreso
     * @return true si se consulto bien ó false si en la consulta da error
     */
    private boolean cargarDatos(String intCodEmp, String intCodLoc, String intCodTipDoc, String intCodDoc, int intTipIE) 
    {
        boolean blnRes = true;
        java.sql.Connection conn;
        try
        {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null) 
            {
                cargarDatCons(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intTipIE);
                conn.close();
                conn = null;
            }
        } 
        catch (java.sql.SQLException Evt) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, Evt);  } 
        catch (Exception Evt) {  blnRes = false;    objUti.mostrarMsgErr_F1(this, Evt);   }
        return blnRes;
    }

    private boolean cargarDatCons(java.sql.Connection conn, String intCodEmp, String intCodLoc, String intCodTipDoc, String intCodDoc, int intTipIE) 
    {
        boolean blnRes = false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.ResultSet rstDatDoc;
        String strSql = "";
        Vector vecData;
        try
        {
            if (conn != null) 
            {
                stmLoc = conn.createStatement();
                if (intTipIE == 1) //Egreso
                {
                    strSql = "SELECT a1.co_ptopar,  a.co_locrelguirem as co_locrel, a.co_tipdocrelguirem as co_tipdocrel, a.co_docrelguirem as co_docrel,"
                            + "      a.co_tipdoc as co_tipdoccon, a6.tx_descor as tx_descorcon, a6.tx_deslar as tx_deslarcon,  "
                            + "      a.st_reg,  a.co_emp, a.co_loc, a.ne_numdoc, a.co_doc, a.tx_obs1, a.tx_obs2, a.co_bod, a.fe_doc, a5.tx_nom,     "
                            + "      a1.co_tipdoc, a2.tx_descor, a2.tx_deslar, a1.co_clides AS co_cli, a1.tx_nomclides as tx_nomcli, a1.co_ven as co_com, a1.tx_nomven, a1.co_forret,   "
                            + "      a4.tx_deslar as desforret, a1.co_doc as codocdoc , a1.fe_Doc as fedocdoc, a2.st_meringegrfisbod, a2.tx_natDoc , a1.ne_numdoc as numdocdoc, "
                            + "      a7.co_veh, a7.tx_pla, a7.tx_deslar AS tx_deslarveh, a8.co_tra, a8.tx_ide AS tx_idecho, a8.tx_nom || ' ' || a8.tx_ape AS tx_nomcho, a.tx_idetra, a.tx_nomtra, a.tx_plavehtra "
                            + " FROM tbm_cabingegrmerbod AS a "
                            + " INNER JOIN tbm_cabtipdoc AS a6 ON(a6.co_emp=a.co_emp AND a6.co_loc=a.co_loc AND a6.co_tipdoc=a.co_tipdoc) "
                            + " INNER JOIN tbm_cabguirem AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=" + strCodLocRelConf + " AND a1.co_tipdoc=" + strCodTipDocRelConf + " and a1.co_doc=" + strCodDocRelConf + " )    "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipdoc=a1.co_tipdoc) "
                            + " LEFT  JOIN tbm_var AS a4 on (a4.co_reg=a1.co_forret) "
                            + " LEFT  JOIN tbm_bod AS a5 ON(a5.co_emp=a.co_emp AND a5.co_bod=a.co_bod) "
                            + " LEFT  JOIN tbm_veh AS a7 ON(a7.co_veh=a.co_veh) "
                            + " LEFT  JOIN tbm_tra AS a8 ON(a8.co_tra=a.co_cho) "
                            + " WHERE a.co_emp=" + intCodEmp + " AND a.co_loc=" + intCodLoc + "  AND "
                            + " a.co_tipdoc=" + intCodTipDoc + " AND a.co_doc=" + intCodDoc;
                } 
                else //Ingreso
                {
                    strSql = "SELECT a.co_locrel, a.co_tipdocrel, a.co_docrel,  a.co_tipdoc as co_tipdoccon, a6.tx_descor as tx_descorcon, "
                            + "      a6.tx_deslar as tx_deslarcon, a.st_reg,  a.co_emp, a.co_loc, a.ne_numdoc, a.co_doc, a.tx_obs1, "
                            + "      a.tx_obs2, a.co_bod, a.co_bod as co_ptopar, a.fe_doc, a5.tx_nom, a1.co_tipdoc, a2.tx_descor, a2.tx_deslar, "
                            + "      a1.co_cli, a1.tx_nomcli, a1.co_com, a1.tx_nomven, a1.co_forret, a4.tx_deslar as desforret, a1.co_doc as codocdoc, "
                            + "      a1.fe_Doc as fedocdoc, a2.st_meringegrfisbod, a2.tx_natDoc , a1.ne_numdoc as numdocdoc "
                            + " FROM tbm_cabingegrmerbod AS a "
                            + " INNER JOIN tbm_cabtipdoc AS a6 ON(a6.co_emp=a.co_emp AND a6.co_loc=a.co_loc AND a6.co_tipdoc=a.co_tipdoc) "
                            + " INNER JOIN tbm_cabmovinv AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=" + strCodLocRelConf + " AND a1.co_tipdoc=" + strCodTipDocRelConf + " and a1.co_doc=" + strCodDocRelConf + " ) "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipdoc=a1.co_tipdoc) "
                            + " LEFT  JOIN tbm_var AS a4 on (a4.co_reg=a1.co_forret) "
                            + " LEFT  JOIN tbm_bod AS a5 ON(a5.co_emp=a.co_emp AND a5.co_bod=a.co_bod) "
                            + " WHERE a.co_emp=" + intCodEmp + " AND a.co_loc=" + intCodLoc + "   "
                            + " AND a.co_tipdoc=" + intCodTipDoc + " AND a.co_doc=" + intCodDoc;
                }
                //System.out.println("cargarDatCons:: "+strSql);  
                rstDatDoc = stmLoc.executeQuery(strSql);
                if (rstDatDoc.next()) 
                {
                    strCodEmpOrdAlm = rstDatDoc.getString("co_emp"); //Rose
                    strCodLocOrdAlm = rstDatDoc.getString("co_locrel"); //Rose
                    
                    STR_COD_EMP_REL = rstDatDoc.getString("co_emp");
                    STR_COD_LOC_REL = rstDatDoc.getString("co_locrel");
                    STR_COD_BOD_GUIA = rstDatDoc.getString("co_ptopar");
                    STRCODEMPTRANS = rstDatDoc.getString("co_emp");
                    STRCODLOCTRANS = rstDatDoc.getString("co_locrel");
                    STRCODTIPTRANS = rstDatDoc.getString("co_tipdocrel");
                    STRCODDOCTRANS = rstDatDoc.getString("co_docrel");
                    strAux = rstDatDoc.getString("st_reg");
                    txtCodTipDoc.setText(rstDatDoc.getString("co_tipdoccon"));
                    txtDesCodTitpDoc.setText(rstDatDoc.getString("tx_descorcon"));
                    txtDesLarTipDoc.setText(rstDatDoc.getString("tx_deslarcon"));
                    strTipIngEgr = rstDatDoc.getString("tx_natDoc");
                    strMerIngEgr = rstDatDoc.getString("st_meringegrfisbod");
                    txtCodLocRel.setText(rstDatDoc.getString("co_loc"));
                    txtCodTipDocCon.setText(rstDatDoc.getString("co_tipdoc"));
                    txtDesCorTipDocCon.setText(rstDatDoc.getString("tx_descor"));
                    txtDesLarTipDocCon.setText(rstDatDoc.getString("tx_deslar"));
                    txtCodCliPro.setText(rstDatDoc.getString("co_cli"));
                    txtNomCliPro.setText(rstDatDoc.getString("tx_nomcli"));
                    txtCodVenCom.setText(rstDatDoc.getString("co_com"));
                    txtNomVenCom.setText(rstDatDoc.getString("tx_nomven"));
                    txtCodForRet.setText(rstDatDoc.getString("co_forret"));
                    txtDesForRet.setText(rstDatDoc.getString("desforret"));
                    txtCodBod.setText(rstDatDoc.getString("co_bod"));
                    txtNomBod.setText(rstDatDoc.getString("tx_nom"));
                    txtCodDoc.setText(rstDatDoc.getString("co_doc"));
                    txtNumDoc.setText(rstDatDoc.getString("ne_numdoc"));
                    txtNumDocSolOcu.setText(rstDatDoc.getString("ne_numdoc"));
                    txtNumDocCon.setText(rstDatDoc.getString("numdocdoc"));
                    txtFecDocCon.setText(rstDatDoc.getString("fedocdoc"));
                    txtCodDocCon.setText(rstDatDoc.getString("codocdoc"));
                    if (intTipIE == 1) {
                        txtCodVeh.setText(rstDatDoc.getString("co_veh"));
                        txtPlaVeh.setText(rstDatDoc.getString("tx_pla"));
                        txtDesLarVeh.setText(rstDatDoc.getString("tx_deslarveh"));
                        txtCodCho.setText(rstDatDoc.getString("co_tra"));
                        txtIdeCho.setText(rstDatDoc.getString("tx_idecho"));
                        txtNomCho.setText(rstDatDoc.getString("tx_nomcho"));
                        txtIdeTra.setText(rstDatDoc.getString("tx_idetra"));
                        txtNomTra.setText(rstDatDoc.getString("tx_nomtra"));
                        txtPlaVehTra.setText(rstDatDoc.getString("tx_plavehtra"));
                        if (rstDatDoc.getString("co_veh") == null) {
                            optRet.setSelected(true);
                            if (txtNomTra.getText().equalsIgnoreCase(txtNomCliPro.getText())) {
                                optVehCli.setSelected(true);
                            } else {
                                optVehTra.setSelected(true);
                            }
                        } else {
                            optEnv.setSelected(true);
                        }
                    }
                    txtObs1.setText(rstDatDoc.getString("tx_obs1"));
                    txtObs2.setText(rstDatDoc.getString("tx_obs2"));
                    java.util.Date dateObj = rstDatDoc.getDate("fe_doc");
                    if (dateObj == null) {
                        txtFecDoc.setText("");
                    } else {
                        java.util.Calendar calObj = java.util.Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                calObj.get(java.util.Calendar.MONTH) + 1,
                                calObj.get(java.util.Calendar.YEAR));
                    }
                }
                rstDatDoc.close();
                rstDatDoc = null;
                int intEst = 0;
                vecData = new Vector();
                if (intTipIE == 1) 
                {
                    strSql = "SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, abs(a.nd_can) as nd_canconf, a.nd_can, a.tx_obs1, (a.nd_canNunRec * -1) as nd_canNunRec, a1.nd_cancon , "
                            + " a1.co_itm, a1.co_reg, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, " + STR_COD_BOD_GUIA + " as co_bod ,  abs(a1.nd_can) as nd_canmov, a1.st_meregrfisbod "
                            + " FROM tbm_detingegrmerbod AS a "
                            + " INNER JOIN tbm_detguirem AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelguirem and a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem and a1.co_reg=a.co_regrelguirem) "
                            + " WHERE a.co_emp=" + intCodEmp + " AND a.co_loc=" + intCodLoc + " AND "
                            + " a.co_tipdoc=" + intCodTipDoc + " AND a.co_doc=" + intCodDoc + " ORDER BY a.co_reg ";
                    rstLoc = stmLoc.executeQuery(strSql);
                    while (rstLoc.next()) {
                        strTipModIngEgr = "2";
                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
                        vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                        vecReg.add(INT_TBL_CODALTDOS, null); //Rose 07/Mar/2016 
                        vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                        vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                        vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_canmov"));
                        vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));
                        vecReg.add(INT_TBL_CANCON, rstLoc.getString("nd_canconf"));
                        vecReg.add(INT_TBL_CANNUMREC, rstLoc.getString("nd_canNunRec"));
                        vecReg.add(INT_TBL_CANMALEST, "");
                        vecReg.add(INT_TBL_CANTOTNUMREC, "");
                        vecReg.add(INT_TBL_OBSITMCON, rstLoc.getString("tx_obs1"));
                        vecReg.add(INT_TBL_CANCON_ORI, rstLoc.getString("nd_canconf"));
                        vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                        vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                        vecReg.add(INT_TBL_CANNUMREC_ORI, rstLoc.getString("nd_canNunRec"));
                        vecReg.add(INT_TBL_CANMALEST_ORI, "");
                        vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meregrfisbod"));
                        vecReg.add(INT_TBL_CANTOTMALEST, "");
                        vecReg.add(INT_TBL_CODEMPREL, null);
                        vecReg.add(INT_TBL_CODLOCREL, null);
                        vecReg.add(INT_TBL_CODTIDOREL, null);
                        vecReg.add(INT_TBL_CODDOCREL, null);
                        vecReg.add(INT_TBL_CODREGREL, null);
                        vecReg.add(INT_TBL_TXNOMBODREL, null);
                        vecReg.add(INT_TBL_CODBODREL, null);
                        vecReg.add(INT_TBL_PESKGR, null);
                        vecReg.add(INT_TBL_CODALTAUX, null);
                        vecReg.add(INT_TBL_NOMITMAUX, null);
                        vecReg.add(INT_TBL_CODREGRELDOCEGR,null);
                        vecData.add(vecReg);
                    }
                    objTblMod.setData(vecData);
                    tblDat.setModel(objTblMod);
                    rstLoc.close();
                    rstLoc = null;
                }
                if (intTipIE == 2) //Rose 07/Mar/2016
                {
                    strSql = "SELECT a1.nd_canTotMalEst, a.nd_canMalEst, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, abs(a.nd_can) as nd_canconf, a.nd_can, a.tx_obs1, a1.nd_canNunRec, a1.nd_cancon ,"
                            + " a1.co_itmact, a1.co_reg, a1.tx_codalt, a2.tx_codalt2, a1.tx_nomitm, a1.tx_unimed, a1.co_bod,  abs(a1.nd_can) as nd_canmov, a1.st_meringegrfisbod "
                            + " FROM tbm_detingegrmerbod AS a "
                            + " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel) "
                            + " INNER JOIN tbm_inv as a2 on (a2.co_Emp=a1.co_Emp and a2.co_itm=a1.co_itmact) " //Rose 07/Mar/2016
                            + " WHERE a.co_emp=" + intCodEmp + " AND a.co_loc=" + intCodLoc + "  AND "
                            + " a.co_tipdoc=" + intCodTipDoc + " AND a.co_doc=" + intCodDoc + "  "
                            + " AND a.co_locrel=" + strCodLocRelConf + " and a.co_tipdocrel=" + strCodTipDocRelConf + " and a.co_docrel=" + strCodDocRelConf + "    "
                            + " ORDER BY  a.co_reg ";
                    rstLoc = stmLoc.executeQuery(strSql);
                    System.out.println("Rose.cargarDatCons: "+strSql);
                    while (rstLoc.next()) 
                    {
                        if (intEst == 0) 
                        {
                            if (rstLoc.getDouble("nd_can") > 0) 
                            {
                                strTipModIngEgr = "1";
                            }
                            if (rstLoc.getDouble("nd_can") < 0) 
                            {
                                strTipModIngEgr = "2";
                            }
                            intEst = 1;
                        }
                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itmact"));
                        vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                        // vecReg.add(INT_TBL_CODALTDOS, null); //Antes Rose 07/Mar/2016
                        vecReg.add(INT_TBL_CODALTDOS, rstLoc.getString("tx_codalt2")); //Rose 07/Mar/2016
                        vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                        vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                        vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_canmov"));
                        vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));
                        vecReg.add(INT_TBL_CANCON, rstLoc.getString("nd_canconf"));
                        vecReg.add(INT_TBL_CANNUMREC, rstLoc.getString("nd_canNunRec"));
                        vecReg.add(INT_TBL_CANMALEST, rstLoc.getString("nd_canMalEst"));
                        vecReg.add(INT_TBL_CANTOTNUMREC, "");
                        vecReg.add(INT_TBL_OBSITMCON, rstLoc.getString("tx_obs1"));
                        vecReg.add(INT_TBL_CANCON_ORI, rstLoc.getString("nd_canconf"));
                        vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                        vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                        vecReg.add(INT_TBL_CANNUMREC_ORI, rstLoc.getString("nd_canNunRec"));
                        vecReg.add(INT_TBL_CANMALEST_ORI, rstLoc.getString("nd_canMalEst"));
                        vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meringegrfisbod"));
                        vecReg.add(INT_TBL_CANTOTMALEST, rstLoc.getString("nd_canTotMalEst"));
                        vecReg.add(INT_TBL_CODEMPREL, null);
                        vecReg.add(INT_TBL_CODLOCREL, null);
                        vecReg.add(INT_TBL_CODTIDOREL, null);
                        vecReg.add(INT_TBL_CODDOCREL, null);
                        vecReg.add(INT_TBL_CODREGREL, null);
                        vecReg.add(INT_TBL_TXNOMBODREL, null);
                        vecReg.add(INT_TBL_CODBODREL, null);
                        vecReg.add(INT_TBL_PESKGR, null);
                        vecReg.add(INT_TBL_CODALTAUX, null);
                        vecReg.add(INT_TBL_NOMITMAUX, null);
                        vecReg.add(INT_TBL_CODREGRELDOCEGR, null);
                        vecData.add(vecReg);
                    }
                    objTblMod.setData(vecData);
                    tblDat.setModel(objTblMod);
                    rstLoc.close();
                    rstLoc = null;
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
            if (objZafParSis.getCodigoUsuario() == 1) {
                //Armar la sentencia SQL.
                Str_Sql = "SELECT co_bod, tx_nom FROM ( SELECT a2.co_bod, a2.tx_nom "
                        + " FROM tbm_emp AS a1 "
                        + " INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) "
                        + " WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresaGrupo() + " "
                        + " ORDER BY a1.co_emp, a2.co_bod  ) as a ";
            } else {
                Str_Sql = "SELECT co_bod, tx_nom FROM ( "
                        + " select a2.co_bodgrp as co_bod,  a1.tx_nom from tbr_bodlocprgusr as a "
                        + " inner join tbr_bodEmpBodGrp as a2 on (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) "
                        + " inner join tbm_bod as a1 on (a1.co_emp=a2.co_empgrp and a1.co_bod=a2.co_bodgrp) "
                        + " where a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                        + " and a.co_usr=" + objZafParSis.getCodigoUsuario() + " and a.co_mnu=" + objZafParSis.getCodigoMenu() + "  "
                        + " ) as a";
            }
            vcoBodUsr = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            vcoBodUsr.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConTipDoc() {
        boolean blnRes = true;
        try {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("110");
            arlAncCol.add("350");
            //Armar la sentencia SQL.   
            String Str_Sql = "";
            
            if (objZafParSis.getCodigoUsuario() == 1) 
            {
                Str_Sql = " Select a.co_tipdoc,a.tx_descor,a.tx_deslar "
                        + " from tbr_tipdocprg as b "
                        + " inner join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)"
                        + " where b.co_emp=" + objZafParSis.getCodigoEmpresa() + "  "
                        + " and b.co_loc = " + objZafParSis.getCodigoLocal() + "  "
                        + " and b.co_mnu = " + objZafParSis.getCodigoMenu();
            } 
            else
            {
                Str_Sql = " select b.co_TipDoc, b.tx_desCor, b.tx_desLar "
                        + " from tbr_tipDocUsr as a "
                        + " inner join tbm_cabTipDoc as b on (a.co_Emp=b.co_Emp and a.co_loc=b.co_loc and a.co_tipDoc=b.co_tipDoc)"
                        + " where a.co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                        + " and a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                        + " and a.co_mnu=" + objZafParSis.getCodigoMenu() + " "
                        + " and a.co_usr=" + objZafParSis.getCodigoUsuario();
            }  
      
            vcoTipDoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Vehículos".
     *
     */
    private boolean configurarVenConVehiculo() 
    {
        boolean blnRes = true;
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_veh");
            arlCam.add("a1.tx_pla");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.nd_pesSopKgr");
            arlCam.add("a2.co_tra");
            arlCam.add("a2.tx_ide");
            arlCam.add("a2.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Placa");
            arlAli.add("Vehículo");
            arlAli.add("Peso (Kg)");
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            //Armar la sentencia SQL.
            strSQL = "";
            strSQL += " SELECT a1.co_veh, a1.tx_pla, a1.tx_desLar, round(a1.nd_pessopkgr,2) AS nd_pessopkgr, a2.co_tra, a2.tx_ide, tx_nom || ' ' || tx_ape AS tx_nom ";
            strSQL += " FROM tbm_veh AS a1 LEFT JOIN tbm_tra AS a2 ON (a1.co_cho = a2.co_tra)";
            strSQL += " WHERE a1.st_reg = 'A' AND a1.tx_tipveh = 'C' order by a1.tx_desLar";
            //Ocultar columnas.
            int intColOcu[] = new int[4];
            intColOcu[0] = 1;
            intColOcu[1] = 5;
            intColOcu[2] = 6;
            intColOcu[3] = 7;
            vcoVeh = new ZafVenCon(JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Vehículos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoVeh.setConfiguracionColumna(4, JLabel.RIGHT);
        }
        catch (Exception e) {   blnRes = false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Choferes".
     *
     */
    private boolean configurarVenConChofer()
    {
        boolean blnRes = true;
        try
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            //Armar la sentencia SQL.
            strSQL = "";
            strSQL += " SELECT a1.co_tra, a1.tx_ide, a1.tx_nom || ' ' || a1.tx_ape AS tx_nom ";
            strSQL += " FROM tbm_tra AS a1";
            strSQL += " INNER JOIN tbm_traEmp AS a2 ON (a2.co_tra = a1.co_tra)";
            strSQL += " INNER JOIN tbm_carLab AS a3 ON (a3.co_car = a2.co_car)";
            strSQL += " INNER JOIN tbm_carLabPre AS a4 ON (a4.co_carpre = a3.co_carpre)"; //Indica solo las personas que tengan cargo Chofer/Empacador Cargador.
            strSQL += " WHERE a1.st_reg = 'A'  ";
            strSQL += " AND a2.st_reg in ('I', 'A')"; //Presenta los trabajadores inactivos/activos de la tabla tbm_traEmp
            strSQL += " ORDER BY a1.tx_nom || ' ' || a1.tx_ape";
            //Ocultar columnas.
            int intColOcu[] = new int[1];
            intColOcu[0] = 1;
            vcoCho = new ZafVenCon(JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Choferes", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
        } 
        catch (Exception e) { blnRes = false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }

    private boolean configurarTablaIngPro()
    {
        boolean blnRes = false;
        try 
        {
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CODITM, "Cod.Itm.");
            vecCab.add(INT_TBL_CODALT, "Cod.Alt.");
            vecCab.add(INT_TBL_NOMITM, "Nom.Itm.");
            vecCab.add(INT_TBL_DESUNI, "Uni.Med.");
            vecCab.add(INT_TBL_CANMOV, "Cantidad.");
            vecCab.add(INT_TBL_CANTOTCON, "Can.ConFir.");
            vecCab.add(INT_TBL_CANCON, "Can.Ing.");
            vecCab.add(INT_TBL_CANNUMREC, "Can.Num.Rec");
            vecCab.add(INT_TBL_CANMALEST, "Can.Mal.Est");
            vecCab.add(INT_TBL_CANTOTNUMREC, "Can.Tot.NunRec.");
            vecCab.add(INT_TBL_OBSITMCON, "Obs.Itm.Con.");
            vecCab.add(INT_TBL_CANCON_ORI, "Conf.Ori");
            vecCab.add(INT_TBL_CODREG, "Cod.Reg.Doc");
            vecCab.add(INT_TBL_CODBOD, "Cod.Bod.Doc");
            vecCab.add(INT_TBL_CANNUMREC_ORI, "Can.Num.Rec.Ori");
            vecCab.add(INT_TBL_CANMALEST_ORI, "Can.Mal.Est.Ori");
            vecCab.add(INT_TBL_IEBODFIS, "IE.Fis.Bod");
            vecCab.add(INT_TBL_CANTOTMALEST, "Can.Tot.Mal.Est");
            vecCab.add(INT_TBL_CODEMPREL, "Cod.Emp.Rel");
            vecCab.add(INT_TBL_CODLOCREL, "Cod.Loc.Rel");
            vecCab.add(INT_TBL_CODTIDOREL, "Cod.TipDoc.Rel");
            vecCab.add(INT_TBL_CODDOCREL, "Cod.Doc.Rel");
            vecCab.add(INT_TBL_CODREGREL, "Cod.Reg.Rel");
            vecCab.add(INT_TBL_TXNOMBODREL, "Nom.Bod.Rel");
            vecCab.add(INT_TBL_CODBODREL, "Cod.Bod.Rel");
            objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            ZafMouMotAda objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODALT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(185);
            tcmAux.getColumn(INT_TBL_DESUNI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CANTOTCON).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CANCON).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CANNUMREC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_OBSITMCON).setPreferredWidth(100);
            objTblMod.setColumnDataType(INT_TBL_CANMOV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANTOTCON, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANCON, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANNUMREC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANTOTCON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANCON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANNUMREC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;
            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_CANTOTCON).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_CANNUMREC).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_OBSITMCON).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            objTblCelEdiTxtCanConf = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANCON).setCellEditor(objTblCelEdiTxtCanConf);
            objTblCelEdiTxtCanConf.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    String strIngBod = (tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS) == null ? "" : tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS).toString());
                    if (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) != null) {
                        if (strIngBod.equals("S")) {
                            String strConf = (tblDat.getValueAt(intCelSel, INT_TBL_CANCON) == null ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON).toString()));
                            String strTotConf = (tblDat.getValueAt(intCelSel, INT_TBL_CANTOTCON) == null ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANTOTCON).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTCON).toString()));
                            String strConfOri = (tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI) == null ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString()));
                            double dblCanTotConf = objUti.redondear(strTotConf, 4);
                            double dblCanConf = objUti.redondear(strConf, 4);
                            double dblCanConfOri = objUti.redondear(strConfOri, 4);
                            if ((dblCanConf - dblCanConfOri) > dblCanTotConf) {
                                if (dblCanConfOri > 0) {
                                    MensajeInf("Este item ya tiene una confirmacion y No puede exceder a la cantidad.\nVerifique los datos he intente nuevamente.");
                                } else {
                                    MensajeInf("No puede exceder a la cantidad.\nVerifique los datos e intente nuevamente.");
                                }
                                tblDat.setValueAt("" + dblCanConfOri, intCelSel, INT_TBL_CANCON);
                            }
                        } else {
                            String strConf = (tblDat.getValueAt(intCelSel, INT_TBL_CANCON) == null ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON).toString()));
                            String strTotConf = (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()));
                            String strConfOri = (tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI) == null ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString()));
                            double dblCanTotConf = objUti.redondear(strTotConf, 4);
                            double dblCanConf = objUti.redondear(strConf, 4);
                            double dblCanConfOri = objUti.redondear(strConfOri, 4);
                            if ((dblCanConf - dblCanConfOri) > dblCanTotConf) {
                                if (dblCanConfOri > 0) {
                                    MensajeInf("Este item ya tiene una confirmacion y No puede exceder a la cantidad.\nVerifique los datos he intente nuevamente.");
                                } else {
                                    MensajeInf("No puede exceder a la cantidad.\nVerifique los datos e intente nuevamente.");
                                }
                                tblDat.setValueAt("" + dblCanConfOri, intCelSel, INT_TBL_CANCON);
                            }
                        }
                    }
                }
            });
            //Aqui se agrega las columnas que van a ser ocultas.
            ArrayList arlColHid = new ArrayList();
            arlColHid.add("" + INT_TBL_CODITM);
            arlColHid.add("" + INT_TBL_CANNUMREC);
            arlColHid.add("" + INT_TBL_CANCON_ORI);
            arlColHid.add("" + INT_TBL_CODREG);
            arlColHid.add("" + INT_TBL_CODBOD);
            arlColHid.add("" + INT_TBL_CANNUMREC_ORI);
            //arlColHid.add(""+INT_TBL_IEBODFIS);
            arlColHid.add("" + INT_TBL_CANMALEST);
            arlColHid.add("" + INT_TBL_CANMALEST_ORI);
            arlColHid.add("" + INT_TBL_CANTOTMALEST);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid = null;
            //para hacer editable las celdas
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_CANCON);
            vecAux.add("" + INT_TBL_CANNUMREC);
            vecAux.add("" + INT_TBL_OBSITMCON);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            ZafTblEdi zafTblEdi = new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatMouseClickIngPro(evt);
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

    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera
     * del JTable. Se utiliza ésta función especificamente para marcar todas las
     * casillas de verificación de la columna que indica la bodega seleccionada
     * en el el JTable de bodegas.
     */
    private void tblDatMouseClickIngPro(java.awt.event.MouseEvent evt) {
        int i, intNumFil;
        try {
            intNumFil = tblDat.getRowCount();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1 && tblDat.columnAtPoint(evt.getPoint()) == INT_TBL_CANCON) {
                if (blnMarTodCanTblDat) {
                    //Mostrar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        int intCelSel = i;
                        String strIngBod = (tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS) == null ? "" : tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS).toString());
                        if (strIngBod.equals("S")) {
                            String strTotConf = (tblDat.getValueAt(intCelSel, INT_TBL_CANTOTCON) == null ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANTOTCON).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTCON).toString()));
                            String strConfOri = (tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI) == null ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString()));
                            double dblCanTotConf = objUti.redondear(strTotConf, 4);
                            double dblCanConfOri = objUti.redondear(strConfOri, 4);
                            if (dblCanConfOri <= 0) {
                                if (dblCanConfOri > 0) {
                                    tblDat.setValueAt("" + dblCanConfOri, intCelSel, INT_TBL_CANCON);
                                } else {
                                    tblDat.setValueAt("" + dblCanTotConf, intCelSel, INT_TBL_CANCON);
                                }
                            }
                        } else {
                            String strTotConf = (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()));
                            String strConfOri = (tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI) == null ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString()));
                            double dblCanTotConf = objUti.redondear(strTotConf, 4);
                            double dblCanConfOri = objUti.redondear(strConfOri, 4);
                            if (dblCanConfOri <= 0) {
                                if (dblCanConfOri > 0) {
                                    tblDat.setValueAt("" + dblCanConfOri, intCelSel, INT_TBL_CANCON);
                                } else {
                                    tblDat.setValueAt("" + dblCanTotConf, intCelSel, INT_TBL_CANCON);
                                }
                            }
                        }
                    }
                    blnMarTodCanTblDat = false;
                } else {
                    //Ocultar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        tblDat.setValueAt("0", i, INT_TBL_CANCON);
                    }
                    blnMarTodCanTblDat = true;
                }
            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private boolean configurarTablaConf() 
    {
        boolean blnRes = false;
        try
        {
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CODITM, "Cod.Itm.");
            vecCab.add(INT_TBL_CODALT, "Cod.Alt.");
            vecCab.add(INT_TBL_CODALTDOS, "Cod.Let.itm."); //Rose 07/Mar/2016
            vecCab.add(INT_TBL_NOMITM, "Nom.Itm.");
            vecCab.add(INT_TBL_DESUNI, "Uni.Med.");
            vecCab.add(INT_TBL_CANMOV, "Cantidad.");
            vecCab.add(INT_TBL_CANTOTCON, "Can.Tot.Con.");
            vecCab.add(INT_TBL_CANCON, "Can.Conf.");
            if ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915)) 
            {
                vecCab.add(INT_TBL_CANNUMREC, "Can.Num.Env");
            } 
            else 
            {
                vecCab.add(INT_TBL_CANNUMREC, "Can.Num.Rec");
            }
            vecCab.add(INT_TBL_CANMALEST, "Can.Mal.Est");
            vecCab.add(INT_TBL_CANTOTNUMREC, "Can.Tot.NunRec.");
            vecCab.add(INT_TBL_OBSITMCON, "Obs.Itm.Con.");
            vecCab.add(INT_TBL_CANCON_ORI, "Conf.Ori");
            vecCab.add(INT_TBL_CODREG, "Cod.Reg.Doc");
            vecCab.add(INT_TBL_CODBOD, "Cod.Bod.Doc");
            vecCab.add(INT_TBL_CANNUMREC_ORI, "Can.Num.Rec.Ori");
            vecCab.add(INT_TBL_CANMALEST_ORI, "Can.Mal.Est.Ori");
            vecCab.add(INT_TBL_IEBODFIS, "IE.Fis.Bod");
            vecCab.add(INT_TBL_CANTOTMALEST, "Can.Tot.Mal.Est");
            vecCab.add(INT_TBL_CODEMPREL, "Cod.Emp.Rel");
            vecCab.add(INT_TBL_CODLOCREL, "Cod.Loc.Rel");
            vecCab.add(INT_TBL_CODTIDOREL, "Cod.TipDoc.Rel");
            vecCab.add(INT_TBL_CODDOCREL, "Cod.Doc.Rel");
            vecCab.add(INT_TBL_CODREGREL, "Cod.Reg.Rel");
            vecCab.add(INT_TBL_TXNOMBODREL, "Nom.Bod.Rel");
            vecCab.add(INT_TBL_CODBODREL, "Cod.Bod.Rel");
            vecCab.add(INT_TBL_PESKGR, "");
            vecCab.add(INT_TBL_CODALTAUX, "Cod.Alt.Aux.");
            vecCab.add(INT_TBL_NOMITMAUX, "Nom.Itm.Aux.");
            vecCab.add(INT_TBL_CODREGRELDOCEGR, "Cod.Reg.Rel.Doc.Egr.");
            objTblModAux = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblModAux.setHeader(vecCab);
            objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            ZafMouMotAda objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODALT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(170);
            tcmAux.getColumn(INT_TBL_DESUNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CANTOTCON).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CANCON).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CANNUMREC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_OBSITMCON).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_CANTOTNUMREC).setPreferredWidth(60);
            objTblMod.setColumnDataType(INT_TBL_CANMOV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANTOTCON, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANCON, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANNUMREC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANMALEST, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANTOTNUMREC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANTOTMALEST, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_CANCON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANNUMREC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANMALEST).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;
            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANTOTCON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANTOTNUMREC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANTOTMALEST).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_CODALTDOS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelEdiTxtCodAlt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CODALT).setCellEditor(objTblCelEdiTxtCodAlt);
            objTblCelEdiTxtCodAlt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if ((tblDat.getValueAt(intCelSel, INT_TBL_NOMITM) == null ? false : true)) {
                        objTblCelEdiTxtCodAlt.setCancelarEdicion(true);
                    }
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if (tblDat.getValueAt(intCelSel, INT_TBL_CODALT) != null) {
                        tblDat.setValueAt(tblDat.getValueAt(intCelSel, INT_TBL_CODALT).toString().toUpperCase(), intCelSel, INT_TBL_CODALT);
                        //PONE EL VALOR INGRESADO EN MAYUSCULAS 
                        if ((tblDat.getValueAt(intCelSel, INT_TBL_CODREG).equals("")) || !(tblDat.getValueAt(intCelSel, INT_TBL_CODALT).toString().equals(tblDat.getValueAt(intCelSel, INT_TBL_CODALTDOS).toString()))) {
                            boolean blnCodAltDos = false;
                            boolean existe = false;
                            int i, j;
                            for (i = 0; i < objTblModAux.getRowCount(); i++) {
                                if (objUti.parseString(objTblModAux.getValueAt(i, INT_TBL_CODALTDOS)).equals(tblDat.getValueAt(intCelSel, INT_TBL_CODALT).toString())) {
                                    blnCodAltDos = true;
                                    existe = false;
                                    for (j = 0; j < tblDat.getRowCount(); j++) {
                                        if (objUti.parseString(tblDat.getValueAt(j, INT_TBL_CODREG)).equals(objTblModAux.getValueAt(i, INT_TBL_CODREG).toString())) {
                                            existe = true;
                                            blnCodAltDos = false;
                                        }
                                    }
                                    if (!existe) {
                                        tblDat.setValueAt(objTblModAux.getValueAt(i, INT_TBL_CODITM), intCelSel, INT_TBL_CODITM);
                                        tblDat.setValueAt(objTblModAux.getValueAt(i, INT_TBL_CANMOV), intCelSel, INT_TBL_CANMOV);
                                        tblDat.setValueAt(objTblModAux.getValueAt(i, INT_TBL_CANTOTCON), intCelSel, INT_TBL_CANTOTCON);
                                        tblDat.setValueAt(objTblModAux.getValueAt(i, INT_TBL_CANTOTNUMREC), intCelSel, INT_TBL_CANTOTNUMREC);
                                        tblDat.setValueAt(objTblModAux.getValueAt(i, INT_TBL_CODREG), intCelSel, INT_TBL_CODREG);
                                        tblDat.setValueAt(objTblModAux.getValueAt(i, INT_TBL_CODBOD), intCelSel, INT_TBL_CODBOD);
                                        tblDat.setValueAt(objTblModAux.getValueAt(i, INT_TBL_IEBODFIS), intCelSel, INT_TBL_IEBODFIS);
                                        tblDat.setValueAt(objTblModAux.getValueAt(i, INT_TBL_CODEMPREL), intCelSel, INT_TBL_CODEMPREL);
                                        tblDat.setValueAt(objTblModAux.getValueAt(i, INT_TBL_CODLOCREL), intCelSel, INT_TBL_CODLOCREL);
                                        tblDat.setValueAt(objTblModAux.getValueAt(i, INT_TBL_CODTIDOREL), intCelSel, INT_TBL_CODTIDOREL);
                                        tblDat.setValueAt(objTblModAux.getValueAt(i, INT_TBL_CODDOCREL), intCelSel, INT_TBL_CODDOCREL);
                                        tblDat.setValueAt(objTblModAux.getValueAt(i, INT_TBL_CODREGREL), intCelSel, INT_TBL_CODREGREL);
                                        tblDat.setValueAt(objTblModAux.getValueAt(i, INT_TBL_CODBODREL), intCelSel, INT_TBL_CODBODREL);
                                        tblDat.setValueAt(objTblModAux.getValueAt(i, INT_TBL_PESKGR), intCelSel, INT_TBL_PESKGR);
                                        tblDat.setValueAt(objTblModAux.getValueAt(i, INT_TBL_CODALTDOS), intCelSel, INT_TBL_CODALTDOS);
                                        tblDat.setValueAt(objTblModAux.getValueAt(i, INT_TBL_CODALTAUX), intCelSel, INT_TBL_CODALTAUX);
                                        tblDat.setValueAt(objTblModAux.getValueAt(i, INT_TBL_NOMITMAUX), intCelSel, INT_TBL_NOMITMAUX);
                                        tblDat.repaint();
                                        tblDat.requestFocus();
                                        tblDat.editCellAt(intCelSel, INT_TBL_CANCON);
                                        break;
                                    }
                                }
                            }
                            if (!blnCodAltDos) {
                                MensajeInf("No se encontró ninguna coincidencia con el código alterno 2 indicado.\nVerifique y vuelva a intentarlo.");
                                tblDat.repaint();
                                tblDat.requestFocus();
                                tblDat.setValueAt("", intCelSel, INT_TBL_CODALT);
                                tblDat.setValueAt("", intCelSel, INT_TBL_CODREG);
                                tblDat.setValueAt("", intCelSel, INT_TBL_CANMOV);
                                tblDat.setValueAt("", intCelSel, INT_TBL_CANTOTCON);
                                tblDat.setValueAt("", intCelSel, INT_TBL_CANCON);
                                tblDat.setValueAt("", intCelSel, INT_TBL_IEBODFIS);
                                tblDat.editCellAt(intCelSel, INT_TBL_CODALT);
                            }
                        }
                    }
                }
            });
            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_CANTOTCON).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_OBSITMCON).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if ((tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS) == null ? false : (tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS).toString().equals("N") ? true : false))) {
                        objTblCelEdiTxt.setCancelarEdicion(true);
                    }
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            objTblCelEdiTxtCanMalEst = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANMALEST).setCellEditor(objTblCelEdiTxtCanMalEst);
            objTblCelEdiTxtCanMalEst.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if ((tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS) == null ? false : (tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS).toString().equals("N") ? true : false))) {
                        objTblCelEdiTxtCanMalEst.setCancelarEdicion(true);
                    }
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    String strNumRec = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC));
                    String strTotConf = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTCON));
                    String strConf = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANCON));
                    String strConfOri = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI));
                    String strTotMalEst = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTMALEST));
                    String strCanMalEst = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST));
                    String strCanMalEstOri = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST_ORI));
                    String strCanTotNunRec = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUMREC));
                    String strNumRecOri = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI));
                    double dblCanTotMalEst = objUti.redondear(strTotMalEst, 4);
                    double dblCanMalEst = objUti.redondear(strCanMalEst, 4);
                    double dblCanMalEstOri = objUti.redondear(strCanMalEstOri, 4);
                    double dblCanNumRec = objUti.redondear(strNumRec, 4);
                    double dblCanTotConf = objUti.redondear(strTotConf, 4);
                    double dblCanConf = objUti.redondear(strConf, 4);
                    double dblCanConfOri = objUti.redondear(strConfOri, 4);
                    double dblCanTotNunRec = objUti.redondear(strCanTotNunRec, 4);
                    double dblCanNumRecOri = objUti.redondear(strNumRecOri, 4);
                    dblCanTotConf = dblCanTotConf - dblCanConfOri;
                    dblCanTotMalEst = dblCanTotMalEst - dblCanMalEstOri;
                    dblCanTotNunRec = dblCanTotNunRec - dblCanNumRecOri;
                    dblCanTotConf = (dblCanConf + dblCanTotConf) + (dblCanNumRec + dblCanTotNunRec) + (dblCanMalEst + dblCanTotMalEst);
                    if (dblCanTotConf > objUti.redondear(tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString(), 4)) {
                        MensajeInf("Error! La cantidad en mal estado a sobrepasado la cantidad de la Confirmada ");
                        tblDat.setValueAt("", intCelSel, INT_TBL_CANMALEST);
                    }
                }
            });
            objTblCelEdiTxtCanNumRec = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANNUMREC).setCellEditor(objTblCelEdiTxtCanNumRec);
            objTblCelEdiTxtCanNumRec.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if ((tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS) == null ? false : (tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS).toString().equals("N") ? true : false))) {
                        objTblCelEdiTxtCanNumRec.setCancelarEdicion(true);
                    }
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    String strNumRec = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC));
                    String strTotConf = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTCON));
                    String strConf = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANCON));
                    String strConfOri = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI));
                    String strNumRecOri = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI));
                    String strTotMalEst = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTMALEST));
                    String strCanMalEst = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST));
                    String strCanMalEstOri = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST_ORI));
                    String strCanTotNunRec = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUMREC));
                    double dblCanTotMalEst = objUti.redondear(strTotMalEst, 4);
                    double dblCanMalEst = objUti.redondear(strCanMalEst, 4);
                    double dblCanMalEstOri = objUti.redondear(strCanMalEstOri, 4);
                    double dblCanNumRec = objUti.redondear(strNumRec, 4);
                    double dblCanNumRecOri = objUti.redondear(strNumRecOri, 4);
                    double dblCanTotConf = objUti.redondear(strTotConf, 4);
                    double dblCanConf = objUti.redondear(strConf, 4);
                    double dblCanConfOri = objUti.redondear(strConfOri, 4);
                    double dblCanTotNunRec = objUti.redondear(strCanTotNunRec, 4);
                    dblCanTotMalEst = dblCanTotMalEst - dblCanMalEstOri;
                    dblCanTotConf = dblCanTotConf - dblCanConfOri;
                    dblCanTotNunRec = dblCanTotNunRec - dblCanNumRecOri;
                    dblCanTotConf = (dblCanConf + dblCanTotConf) + (dblCanNumRec + dblCanTotNunRec) + (dblCanMalEst + dblCanTotMalEst);
                    if (dblCanTotConf > objUti.redondear(tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString(), 4)) {
                        MensajeInf("Error! La cantidad a Nunca recibida a sobrepasado la cantidad de la Confirmada ");
                        tblDat.setValueAt("", intCelSel, INT_TBL_CANNUMREC);
                    }
                }
            });
            objTblCelEdiTxtCanConf = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANCON).setCellEditor(objTblCelEdiTxtCanConf);
            objTblCelEdiTxtCanConf.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if ((tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS) == null ? false : (tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS).toString().equals("N") ? true : false))) {
                        objTblCelEdiTxtCanConf.setCancelarEdicion(true);
                    }
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) != null) {
                        String strNumRec = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC));
                        String strTotConf = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTCON));
                        String strConf = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANCON));
                        String strConfOri = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI));
                        String strTotMalEst = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTMALEST));
                        String strCanMalEst = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST));
                        String strCanMalEstOri = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST_ORI));
                        String strCanTotNunRec = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUMREC));
                        String strNumRecOri = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI));
                        double dblCanTotMalEst = objUti.redondear(strTotMalEst, 4);
                        double dblCanMalEst = objUti.redondear(strCanMalEst, 4);
                        double dblCanMalEstOri = objUti.redondear(strCanMalEstOri, 4);
                        double dblCanNumRec = objUti.redondear(strNumRec, 4);
                        double dblCanTotConf = objUti.redondear(strTotConf, 4);
                        double dblCanConf = objUti.redondear(strConf, 4);
                        double dblCanConfOri = objUti.redondear(strConfOri, 4);
                        double dblCanTotNunRec = objUti.redondear(strCanTotNunRec, 4);
                        double dblCanNumRecOri = objUti.redondear(strNumRecOri, 4);
                        dblCanTotMalEst = dblCanTotMalEst - dblCanMalEstOri;
                        dblCanTotNunRec = dblCanTotNunRec - dblCanNumRecOri;
                        if (dblCanConf > 0) {
                            if (tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS) != null) {
                                if (((dblCanTotConf - dblCanConfOri) + (dblCanConf + dblCanNumRec + dblCanTotNunRec) + (dblCanMalEst + dblCanTotMalEst)) > objUti.redondear(tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString(), 4)) {
                                    if (dblCanConfOri > 0) {
                                        MensajeInf("Este item ya tiene una confirmacion y No puede exceder a la cantidad.\nVerifique los datos he intente nuevamente.");
                                    } else {
                                        MensajeInf("No puede exceder a la cantidad.\nVerifique los datos e intente nuevamente.");
                                    }
                                    tblDat.setValueAt("" + dblCanConfOri, intCelSel, INT_TBL_CANCON);
                                }
                            } else {
                                if ((tblDat.getValueAt(intCelSel, INT_TBL_CODALT) == null ? true : (tblDat.getValueAt(intCelSel, INT_TBL_CODALT).toString().equals("") ? true : false))) {
                                    MensajeInf("Indique un código alterno 2 válido.\nVerifique los datos e intente nuevamente.");
                                    tblDat.repaint();
                                    tblDat.requestFocus();
                                    tblDat.setValueAt("", intCelSel, INT_TBL_CODALT);
                                    tblDat.editCellAt(intCelSel, INT_TBL_CODALT);
                                    tblDat.setValueAt("", intCelSel, INT_TBL_CANCON);
                                } else {
                                    if (((dblCanTotConf - dblCanConfOri) + (dblCanConf + dblCanNumRec + dblCanTotNunRec) + (dblCanMalEst + dblCanTotMalEst)) > objUti.redondear(tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString(), 4)) {
                                        if (dblCanConfOri > 0) {
                                            MensajeInf("Este item ya tiene una confirmacion y No puede exceder a la cantidad.\nVerifique los datos he intente nuevamente.");
                                        } else {
                                            MensajeInf("No puede exceder a la cantidad.\nVerifique los datos e intente nuevamente.");
                                        }
                                        tblDat.setValueAt("" + dblCanConfOri, intCelSel, INT_TBL_CANCON);
                                    }
                                }
                            }
                        }
                    }
                }
            });
            //Aqui se agrega las columnas que van a estar ocultas.
            ArrayList arlColHid = new ArrayList();
            arlColHid.add("" + INT_TBL_CODITM);
            //arlColHid.add("" + INT_TBL_CODALTDOS); //Rose 07/Mar/2016
            arlColHid.add("" + INT_TBL_CANCON_ORI);
            arlColHid.add("" + INT_TBL_CODREG);
            arlColHid.add("" + INT_TBL_CODBOD);
            arlColHid.add("" + INT_TBL_CANNUMREC_ORI);
            if (objZafParSis.getCodigoMenu() != 286)
            {
                if ((intTipIngEgr == 1) || (intTipIngEgr == 0))
                {
                    arlColHid.add("" + INT_TBL_CANMALEST); 
                    arlColHid.add("" + INT_TBL_CODALTDOS); //Rose 07/Mar/2016
                }
                arlColHid.add("" + INT_TBL_CANTOTMALEST);
            }
            if ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915)) {
                arlColHid.add("" + INT_TBL_CANNUMREC);
            }
            arlColHid.add("" + INT_TBL_CANMALEST_ORI);
            arlColHid.add("" + INT_TBL_CODEMPREL);
            arlColHid.add("" + INT_TBL_CODLOCREL);
            arlColHid.add("" + INT_TBL_CODTIDOREL);
            arlColHid.add("" + INT_TBL_CODDOCREL);
            arlColHid.add("" + INT_TBL_CODREGREL);
            arlColHid.add("" + INT_TBL_TXNOMBODREL);
            arlColHid.add("" + INT_TBL_CODBODREL);
            arlColHid.add("" + INT_TBL_PESKGR);
            arlColHid.add("" + INT_TBL_CODALTAUX);
            arlColHid.add("" + INT_TBL_NOMITMAUX);
            arlColHid.add("" + INT_TBL_CODREGRELDOCEGR);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid = null;
            //para hacer editable las celdas
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_CODALT);
            vecAux.add("" + INT_TBL_CANCON);
            if (!(objZafParSis.getCodigoMenu() == 2073 || objZafParSis.getCodigoMenu() == 2205 || objZafParSis.getCodigoMenu() == 2915)) {
                vecAux.add("" + INT_TBL_CANNUMREC);
            }
            vecAux.add("" + INT_TBL_OBSITMCON);
            if (objZafParSis.getCodigoMenu() == 286) {
                vecAux.add("" + INT_TBL_CANMALEST);
            }
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
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

    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera
     * del JTable. Se utiliza ésta función especificamente para marcar todas las
     * casillas de verificación de la columna que indica la bodega seleccionada
     * en el el JTable de bodegas.
     */
    private void tblDatMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try 
        {
//            intNumFil=tblDat.getRowCount();
//            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
//            if (evt.getButton()==MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==INT_TBL_CANCON)
//            {
//                if (blnMarTodCanTblDat)
//                {
//                    //Mostrar todas las columnas.
//                    for (i=0; i<intNumFil; i++)
//                    {
//                     int intCelSel=i; 
//                     
//                     if(!(tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS)==null?false:(tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS).toString().equals("N")?true:false))){
//
//                        String strNumRec=objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC) );
//                        String strTotConf=objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTCON) );
//                        String strConfOri=objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI) );
//                        String strCanven=objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) );
//                        String strTotMalEst=objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTMALEST) );
//                        String strCanMalEst=objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST) );
//                        String strCanMalEstOri=objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST_ORI) );
//                        String strCanTotNunRec= objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUMREC) );
//                        String strNumRecOri=objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI) );
//
//
//                        double dblCanTotMalEst= objUti.redondear(strTotMalEst,4);
//                        double dblCanMalEst   = objUti.redondear(strCanMalEst,4);
//                        double dblCanNumRec= objUti.redondear(strNumRec,4);
//                        double dblCanTotConf= objUti.redondear(strTotConf,4);
//                        double dblCanConfOri= objUti.redondear(strConfOri,4);
//                        double dblCanMov= objUti.redondear(strCanven,4);
//                        double dblCanTotNunRec = objUti.redondear(strCanTotNunRec,4);
//                        double dblCanNumRecOri= objUti.redondear(strNumRecOri,4);
//
//                       dblCanTotNunRec=dblCanTotNunRec-dblCanNumRecOri;
//                    
//
//                        dblCanMov =  ( (dblCanMov - (dblCanTotConf+dblCanNumRec+dblCanTotNunRec))-(dblCanTotMalEst+dblCanMalEst) );
//                       
//                       if( dblCanConfOri <= 0 ){
//                          if(dblCanConfOri >0)  tblDat.setValueAt(""+dblCanConfOri , intCelSel, INT_TBL_CANCON); 
//                          else  tblDat.setValueAt(""+dblCanMov , intCelSel, INT_TBL_CANCON); 
//                       }
//                    } 
//                    }
//                    blnMarTodCanTblDat=false;
//                }
//                else
//                {
//                    //Ocultar todas las columnas.
//                    for (i=0; i<intNumFil; i++)
//                    {
//                        tblDat.setValueAt("0", i, INT_TBL_CANCON);
//                    }
//                    blnMarTodCanTblDat=true;
//                }
//                
//            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    public void BuscarVehiculo(String campo, String strBusqueda, int tipo) 
    {
        vcoVeh.setTitle("Listado de Vehículos");
        if (vcoVeh.buscar(campo, strBusqueda)) 
        {
            txtCodVeh.setText(vcoVeh.getValueAt(1));
            txtPlaVeh.setText(vcoVeh.getValueAt(2));
            txtDesLarVeh.setText(vcoVeh.getValueAt(3));
            txtPesVeh.setText(vcoVeh.getValueAt(4));
            txtCodCho.setText(vcoVeh.getValueAt(5));
            txtIdeCho.setText(vcoVeh.getValueAt(6));
            txtNomCho.setText(vcoVeh.getValueAt(7));
            optEnv.setSelected(true);
        }
        else 
        {
            vcoVeh.setCampoBusqueda(tipo);
            vcoVeh.cargarDatos();
            vcoVeh.show();
            if (vcoVeh.getSelectedButton() == vcoVeh.INT_BUT_ACE) 
            {
                txtCodVeh.setText(vcoVeh.getValueAt(1));
                txtPlaVeh.setText(vcoVeh.getValueAt(2));
                txtDesLarVeh.setText(vcoVeh.getValueAt(3));
                txtPesVeh.setText(vcoVeh.getValueAt(4));
                txtCodCho.setText(vcoVeh.getValueAt(5));
                txtIdeCho.setText(vcoVeh.getValueAt(6));
                txtNomCho.setText(vcoVeh.getValueAt(7));
                optEnv.setSelected(true);
            }
            else
            {
                txtCodVeh.setText(strCodVeh);
                txtPlaVeh.setText(strPlaVeh);
                txtDesLarVeh.setText(strDesLarVeh);
                txtPesVeh.setText(strPesVeh);
                txtCodCho.setText(strCodCho);
                txtIdeCho.setText(strIdeCho);
                txtNomCho.setText(strNomCho);
            }
        }
    }

    public void BuscarChofer(String campo, String strBusqueda, int tipo) 
    {
        vcoCho.setTitle("Listado de Choferes");
        if (vcoCho.buscar(campo, strBusqueda)) 
        {
            txtCodCho.setText(vcoCho.getValueAt(1));
            txtIdeCho.setText(vcoCho.getValueAt(2));
            txtNomCho.setText(vcoCho.getValueAt(3));
            optEnv.setSelected(true);
        } 
        else 
        {
            vcoCho.setCampoBusqueda(tipo);
            vcoCho.cargarDatos();
            vcoCho.show();
            if (vcoCho.getSelectedButton() == vcoCho.INT_BUT_ACE)
            {
                txtCodCho.setText(vcoCho.getValueAt(1));
                txtIdeCho.setText(vcoCho.getValueAt(2));
                txtNomCho.setText(vcoCho.getValueAt(3));
                optEnv.setSelected(true);
            }
            else 
            {
                txtCodCho.setText(strCodCho);
                txtIdeCho.setText(strIdeCho);
                txtNomCho.setText(strNomCho);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrRet = new javax.swing.ButtonGroup();
        bgrTra = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        PanTabGen = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtCodDoc = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBusBod = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblNumDoc = new javax.swing.JLabel();
        txtDesCodTitpDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblTipDoc1 = new javax.swing.JLabel();
        lblTipDoc2 = new javax.swing.JLabel();
        panObs = new javax.swing.JPanel();
        panLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panTxa = new javax.swing.JPanel();
        spnObs3 = new javax.swing.JScrollPane();
        txtObs1 = new javax.swing.JTextArea();
        spnObs4 = new javax.swing.JScrollPane();
        txtObs2 = new javax.swing.JTextArea();
        panDatConf = new javax.swing.JPanel();
        panTblCabDat = new javax.swing.JPanel();
        lblTipdoc = new javax.swing.JLabel();
        lblComPro = new javax.swing.JLabel();
        lblVenCom = new javax.swing.JLabel();
        txtCodForRet = new javax.swing.JTextField();
        txtCodVenCom = new javax.swing.JTextField();
        txtDesCorTipDocCon = new javax.swing.JTextField();
        txtDesLarTipDocCon = new javax.swing.JTextField();
        txtCodCliPro = new javax.swing.JTextField();
        txtNomCliPro = new javax.swing.JTextField();
        txtNomVenCom = new javax.swing.JTextField();
        txtDesForRet = new javax.swing.JTextField();
        lblNumGuiDet = new javax.swing.JLabel();
        lblFecDocCon = new javax.swing.JLabel();
        txtNumDocCon = new javax.swing.JTextField();
        txtFecDocCon = new javax.swing.JTextField();
        butBusNumDocCon = new javax.swing.JButton();
        txtCodDocCon = new javax.swing.JTextField();
        lblCodDocCon1 = new javax.swing.JLabel();
        butBusCodDoc = new javax.swing.JButton();
        lblNumDocCon1 = new javax.swing.JLabel();
        txtNumGuiDes = new javax.swing.JTextField();
        lblNumGuiDes = new javax.swing.JLabel();
        lblForRet1 = new javax.swing.JLabel();
        txtMotTra = new javax.swing.JTextField();
        lblMotTra = new javax.swing.JLabel();
        panTblDetDoc = new javax.swing.JPanel();
        scrTblDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panDatDsp = new javax.swing.JPanel();
        PanDatDsp = new javax.swing.JPanel();
        optEnv = new javax.swing.JRadioButton();
        optRet = new javax.swing.JRadioButton();
        lblVeh = new javax.swing.JLabel();
        txtPlaVeh = new javax.swing.JTextField();
        txtDesLarVeh = new javax.swing.JTextField();
        butVeh = new javax.swing.JButton();
        lblPla = new javax.swing.JLabel();
        txtIdeCho = new javax.swing.JTextField();
        txtNomCho = new javax.swing.JTextField();
        butCho = new javax.swing.JButton();
        optVehCli = new javax.swing.JRadioButton();
        optVehTra = new javax.swing.JRadioButton();
        txtPlaVehTra = new javax.swing.JTextField();
        txtIdeTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        lblCho = new javax.swing.JLabel();
        lblIde = new javax.swing.JLabel();
        lblRso = new javax.swing.JLabel();

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

        panTit.setPreferredSize(new java.awt.Dimension(100, 30));

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblTit.setText("titulo");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.PAGE_START);

        panCen.setLayout(new java.awt.BorderLayout());

        tabFrm.setPreferredSize(new java.awt.Dimension(115, 100));

        panTabGen.setPreferredSize(new java.awt.Dimension(100, 90));
        panTabGen.setLayout(new java.awt.BorderLayout());

        PanTabGen.setPreferredSize(new java.awt.Dimension(100, 75));
        PanTabGen.setLayout(null);

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDoc.setText("Codigo:");
        PanTabGen.add(lblTipDoc);
        lblTipDoc.setBounds(10, 50, 110, 20);

        txtCodBod.setBackground(objZafParSis.getColorCamposObligatorios());
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
        PanTabGen.add(txtCodBod);
        txtCodBod.setBounds(135, 30, 70, 20);

        txtCodDoc.setBackground(objZafParSis.getColorCamposSistema());
        txtCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        PanTabGen.add(txtCodDoc);
        txtCodDoc.setBounds(135, 50, 70, 20);

        txtNomBod.setBackground(objZafParSis.getColorCamposObligatorios());
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
        PanTabGen.add(txtNomBod);
        txtNomBod.setBounds(205, 30, 230, 20);

        butBusBod.setText("jButton2");
        butBusBod.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusBodActionPerformed(evt);
            }
        });
        PanTabGen.add(butBusBod);
        butBusBod.setBounds(435, 30, 20, 20);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecDoc.setText("Fecha del Documento:");
        PanTabGen.add(lblFecDoc);
        lblFecDoc.setBounds(460, 10, 110, 20);

        txtNumDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        PanTabGen.add(txtNumDoc);
        txtNumDoc.setBounds(580, 30, 92, 20);

        lblNumDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc.setText("Número de Documento:");
        PanTabGen.add(lblNumDoc);
        lblNumDoc.setBounds(460, 30, 120, 20);

        txtDesCodTitpDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesCodTitpDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCodTitpDocActionPerformed(evt);
            }
        });
        txtDesCodTitpDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCodTitpDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCodTitpDocFocusLost(evt);
            }
        });
        PanTabGen.add(txtDesCodTitpDoc);
        txtDesCodTitpDoc.setBounds(135, 10, 70, 20);

        txtDesLarTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
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
        txtDesLarTipDoc.setBounds(205, 10, 230, 20);

        butTipDoc.setText(".."); // NOI18N
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        PanTabGen.add(butTipDoc);
        butTipDoc.setBounds(435, 10, 20, 20);

        lblTipDoc1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDoc1.setText("Tipo de Documento:");
        PanTabGen.add(lblTipDoc1);
        lblTipDoc1.setBounds(10, 10, 110, 20);

        lblTipDoc2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDoc2.setText("Bodega:");
        PanTabGen.add(lblTipDoc2);
        lblTipDoc2.setBounds(10, 30, 110, 20);

        panTabGen.add(PanTabGen, java.awt.BorderLayout.PAGE_START);

        panObs.setPreferredSize(new java.awt.Dimension(100, 80));
        panObs.setLayout(new java.awt.BorderLayout());

        panLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Observación 1:"); // NOI18N
        lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs1.setPreferredSize(new java.awt.Dimension(92, 15));
        panLbl.add(lblObs1);

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblObs2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs2.setText("Observación 2:"); // NOI18N
        lblObs2.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs2.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs2.setPreferredSize(new java.awt.Dimension(92, 15));
        lblObs2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        panLbl.add(lblObs2);

        panObs.add(panLbl, java.awt.BorderLayout.WEST);

        panTxa.setLayout(new java.awt.GridLayout(2, 1, 0, 1));

        txtObs1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtObs1.setLineWrap(true);
        spnObs3.setViewportView(txtObs1);

        panTxa.add(spnObs3);

        txtObs2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtObs2.setLineWrap(true);
        spnObs4.setViewportView(txtObs2);

        panTxa.add(spnObs4);

        panObs.add(panTxa, java.awt.BorderLayout.CENTER);

        panTabGen.add(panObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panTabGen);

        panDatConf.setLayout(new java.awt.BorderLayout());

        panTblCabDat.setPreferredSize(new java.awt.Dimension(200, 110));
        panTblCabDat.setLayout(null);

        lblTipdoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipdoc.setText("Tipo de Documento:");
        panTblCabDat.add(lblTipdoc);
        lblTipdoc.setBounds(10, 6, 110, 20);

        lblComPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblComPro.setText("Cliente/Proveedor");
        panTblCabDat.add(lblComPro);
        lblComPro.setBounds(10, 24, 110, 20);

        lblVenCom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblVenCom.setText("Vendedor/Comprador");
        panTblCabDat.add(lblVenCom);
        lblVenCom.setBounds(10, 46, 110, 20);

        txtCodForRet.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodForRet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodForRetKeyPressed(evt);
            }
        });
        panTblCabDat.add(txtCodForRet);
        txtCodForRet.setBounds(130, 66, 60, 20);

        txtCodVenCom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panTblCabDat.add(txtCodVenCom);
        txtCodVenCom.setBounds(130, 46, 60, 20);

        txtDesCorTipDocCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtDesCorTipDocCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocConActionPerformed(evt);
            }
        });
        panTblCabDat.add(txtDesCorTipDocCon);
        txtDesCorTipDocCon.setBounds(130, 6, 60, 20);

        txtDesLarTipDocCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panTblCabDat.add(txtDesLarTipDocCon);
        txtDesLarTipDocCon.setBounds(190, 6, 250, 20);

        txtCodCliPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panTblCabDat.add(txtCodCliPro);
        txtCodCliPro.setBounds(130, 26, 60, 20);

        txtNomCliPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panTblCabDat.add(txtNomCliPro);
        txtNomCliPro.setBounds(190, 26, 250, 20);

        txtNomVenCom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panTblCabDat.add(txtNomVenCom);
        txtNomVenCom.setBounds(190, 46, 250, 20);

        txtDesForRet.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panTblCabDat.add(txtDesForRet);
        txtDesForRet.setBounds(190, 66, 250, 20);

        lblNumGuiDet.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblNumGuiDet.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNumGuiDet.setRequestFocusEnabled(false);
        lblNumGuiDet.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        panTblCabDat.add(lblNumGuiDet);
        lblNumGuiDet.setBounds(130, 88, 310, 18);

        lblFecDocCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecDocCon.setText("Fecha del Documento:");
        panTblCabDat.add(lblFecDocCon);
        lblFecDocCon.setBounds(450, 6, 120, 20);

        txtNumDocCon.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNumDocCon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumDocConKeyPressed(evt);
            }
        });
        panTblCabDat.add(txtNumDocCon);
        txtNumDocCon.setBounds(570, 26, 90, 20);
        panTblCabDat.add(txtFecDocCon);
        txtFecDocCon.setBounds(570, 6, 90, 20);

        butBusNumDocCon.setText("jButton1");
        butBusNumDocCon.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusNumDocCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusNumDocConActionPerformed(evt);
            }
        });
        panTblCabDat.add(butBusNumDocCon);
        butBusNumDocCon.setBounds(660, 26, 20, 20);
        panTblCabDat.add(txtCodDocCon);
        txtCodDocCon.setBounds(570, 46, 90, 20);

        lblCodDocCon1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodDocCon1.setText("Código del Documento:");
        panTblCabDat.add(lblCodDocCon1);
        lblCodDocCon1.setBounds(450, 46, 120, 20);

        butBusCodDoc.setText("jButton1");
        butBusCodDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusCodDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusCodDocActionPerformed(evt);
            }
        });
        panTblCabDat.add(butBusCodDoc);
        butBusCodDoc.setBounds(660, 67, 20, 20);

        lblNumDocCon1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDocCon1.setText("Número de Documento:");
        panTblCabDat.add(lblNumDocCon1);
        lblNumDocCon1.setBounds(450, 26, 112, 20);

        txtNumGuiDes.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNumGuiDes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumGuiDesKeyPressed(evt);
            }
        });
        panTblCabDat.add(txtNumGuiDes);
        txtNumGuiDes.setBounds(570, 67, 90, 20);

        lblNumGuiDes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumGuiDes.setText("# Orden Despacho:");
        panTblCabDat.add(lblNumGuiDes);
        lblNumGuiDes.setBounds(450, 67, 110, 20);

        lblForRet1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblForRet1.setText("Forma de retiro:");
        panTblCabDat.add(lblForRet1);
        lblForRet1.setBounds(10, 66, 110, 20);

        txtMotTra.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMotTra.setForeground(new java.awt.Color(255, 0, 0));
        txtMotTra.setBorder(null);
        panTblCabDat.add(txtMotTra);
        txtMotTra.setBounds(570, 88, 110, 18);

        lblMotTra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMotTra.setText("Motivo transacción:");
        panTblCabDat.add(lblMotTra);
        lblMotTra.setBounds(450, 86, 120, 20);

        panDatConf.add(panTblCabDat, java.awt.BorderLayout.PAGE_START);

        panTblDetDoc.setLayout(new java.awt.BorderLayout());

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
        scrTblDat.setViewportView(tblDat);

        panTblDetDoc.add(scrTblDat, java.awt.BorderLayout.CENTER);

        panDatConf.add(panTblDetDoc, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Datos del documento a confirmar", panDatConf);

        panDatDsp.setPreferredSize(new java.awt.Dimension(100, 90));
        panDatDsp.setLayout(new java.awt.BorderLayout());

        PanDatDsp.setPreferredSize(new java.awt.Dimension(100, 375));
        PanDatDsp.setLayout(null);

        bgrRet.add(optEnv);
        optEnv.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optEnv.setSelected(true);
        optEnv.setLabel("La mercadería se envía al cliente en uno de nuestros vehículos");
        optEnv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optEnvActionPerformed(evt);
            }
        });
        PanDatDsp.add(optEnv);
        optEnv.setBounds(10, 10, 400, 20);

        bgrRet.add(optRet);
        optRet.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optRet.setText("El cliente retira la mercadería"); // NOI18N
        optRet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optRetActionPerformed(evt);
            }
        });
        PanDatDsp.add(optRet);
        optRet.setBounds(10, 90, 400, 20);

        lblVeh.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblVeh.setText("Vehículo:");
        PanDatDsp.add(lblVeh);
        lblVeh.setBounds(30, 40, 100, 20);

        txtPlaVeh.setBackground(objZafParSis.getColorCamposObligatorios());
        txtPlaVeh.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtPlaVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlaVehActionPerformed(evt);
            }
        });
        txtPlaVeh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPlaVehFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPlaVehFocusLost(evt);
            }
        });
        PanDatDsp.add(txtPlaVeh);
        txtPlaVeh.setBounds(150, 40, 70, 20);

        txtDesLarVeh.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesLarVeh.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtDesLarVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarVehActionPerformed(evt);
            }
        });
        txtDesLarVeh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarVehFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarVehFocusLost(evt);
            }
        });
        PanDatDsp.add(txtDesLarVeh);
        txtDesLarVeh.setBounds(220, 40, 420, 20);

        butVeh.setText("...");
        butVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVehActionPerformed(evt);
            }
        });
        PanDatDsp.add(butVeh);
        butVeh.setBounds(640, 40, 20, 20);

        lblPla.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblPla.setText("Placa:");
        PanDatDsp.add(lblPla);
        lblPla.setBounds(40, 190, 100, 20);

        txtIdeCho.setBackground(objZafParSis.getColorCamposObligatorios());
        txtIdeCho.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtIdeCho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdeChoActionPerformed(evt);
            }
        });
        txtIdeCho.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdeChoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdeChoFocusLost(evt);
            }
        });
        PanDatDsp.add(txtIdeCho);
        txtIdeCho.setBounds(150, 60, 70, 20);

        txtNomCho.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomCho.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNomCho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomChoActionPerformed(evt);
            }
        });
        txtNomCho.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomChoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomChoFocusLost(evt);
            }
        });
        PanDatDsp.add(txtNomCho);
        txtNomCho.setBounds(220, 60, 420, 20);

        butCho.setText("...");
        butCho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butChoActionPerformed(evt);
            }
        });
        PanDatDsp.add(butCho);
        butCho.setBounds(640, 60, 20, 20);

        bgrTra.add(optVehCli);
        optVehCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optVehCli.setText("Vehículo propio");
        optVehCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optVehCliActionPerformed(evt);
            }
        });
        PanDatDsp.add(optVehCli);
        optVehCli.setBounds(30, 120, 180, 20);

        bgrTra.add(optVehTra);
        optVehTra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optVehTra.setText("Transportista");
        optVehTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optVehTraActionPerformed(evt);
            }
        });
        PanDatDsp.add(optVehTra);
        optVehTra.setBounds(210, 120, 148, 20);

        txtPlaVehTra.setBackground(objZafParSis.getColorCamposObligatorios());
        PanDatDsp.add(txtPlaVehTra);
        txtPlaVehTra.setBounds(150, 190, 100, 20);

        txtIdeTra.setBackground(objZafParSis.getColorCamposObligatorios());
        txtIdeTra.setToolTipText("Identificación del cliente/proveedor");
        txtIdeTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdeTraActionPerformed(evt);
            }
        });
        txtIdeTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdeTraFocusGained(evt);
            }
        });
        PanDatDsp.add(txtIdeTra);
        txtIdeTra.setBounds(150, 150, 150, 20);

        txtNomTra.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomTra.setToolTipText("Nombre del cliente/proveedor");
        txtNomTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTraActionPerformed(evt);
            }
        });
        txtNomTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTraFocusGained(evt);
            }
        });
        PanDatDsp.add(txtNomTra);
        txtNomTra.setBounds(150, 170, 330, 20);

        lblCho.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCho.setText("Chofer:");
        PanDatDsp.add(lblCho);
        lblCho.setBounds(30, 60, 100, 20);

        lblIde.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblIde.setText("Identificación:");
        PanDatDsp.add(lblIde);
        lblIde.setBounds(40, 150, 100, 20);

        lblRso.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblRso.setText("Razón social:");
        PanDatDsp.add(lblRso);
        lblRso.setBounds(40, 170, 100, 20);

        panDatDsp.add(PanDatDsp, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Despacho", panDatDsp);

        panCen.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    Configura_ventana_consulta();
}//GEN-LAST:event_formInternalFrameOpened

private void butBusNumDocConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusNumDocConActionPerformed
    if (txtCodBod.getText().equals("")) 
    {
        MensajeInf("Seleccione la bodega antes consultar un documento. ");
        tabFrm.setSelectedIndex(0);
        txtCodBod.requestFocus();
    } 
    else 
    {
        cargarVenBusDoc("", txtCodBod.getText(), txtNomBod.getText());
    }

}//GEN-LAST:event_butBusNumDocConActionPerformed

private void txtDesCodTitpDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocActionPerformed
    txtDesCodTitpDoc.transferFocus();
}//GEN-LAST:event_txtDesCodTitpDocActionPerformed

private void txtDesCodTitpDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocFocusGained
    strDesCorTipDoc = txtDesCodTitpDoc.getText();
    txtDesCodTitpDoc.selectAll();
}//GEN-LAST:event_txtDesCodTitpDocFocusGained

private void txtDesCodTitpDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocFocusLost
    if (!txtDesCodTitpDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
        if (txtDesCodTitpDoc.getText().equals("")) {
            txtCodTipDoc.setText("");
            txtDesCodTitpDoc.setText("");
            txtDesLarTipDoc.setText("");
        } else {
            BuscarTipDoc("a.tx_descor", txtDesCodTitpDoc.getText(), 1);
        }
    } else {
        txtDesCodTitpDoc.setText(strDesCorTipDoc);
    }
}//GEN-LAST:event_txtDesCodTitpDocFocusLost

private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
    txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
    strDesLarTipDoc = txtDesLarTipDoc.getText();
    txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
    if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
        if (txtDesLarTipDoc.getText().equals("")) {
            txtCodTipDoc.setText("");
            txtDesCodTitpDoc.setText("");
            txtDesLarTipDoc.setText("");
        } else {
            BuscarTipDoc("a.tx_deslar", txtDesLarTipDoc.getText(), 2);
        }
    } else {
        txtDesLarTipDoc.setText(strDesLarTipDoc);
    }
}//GEN-LAST:event_txtDesLarTipDocFocusLost

private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
    vcoTipDoc.setTitle("Listado de Tipo de Documentos");
    vcoTipDoc.setCampoBusqueda(1);
    vcoTipDoc.show();
    if (vcoTipDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
        txtDesCodTitpDoc.setText(vcoTipDoc.getValueAt(2));
        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
        strCodTipDoc = vcoTipDoc.getValueAt(1);
    }
}//GEN-LAST:event_butTipDocActionPerformed

private void txtNumDocConKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumDocConKeyPressed
    if (java.awt.event.KeyEvent.VK_ENTER == evt.getKeyCode()) {
        if (txtNumDocCon.getText().trim().equals("")) {
            MensajeInf("Ingrese el numero de documento a buscar. ");
            txtNumDocCon.setText(txtNumDocCon.getText().trim());
            txtNumDocCon.requestFocus();
        } else {
            if (txtCodBod.getText().equals("")) {
                MensajeInf("Seleccione la bodega antes consultar un documento. ");
                tabFrm.setSelectedIndex(0);
                txtCodBod.requestFocus();
            } else {
                if (!cargarDoc(txtNumDocCon.getText())) {
                    String strSql = "";
                    String strAux = "SELECT b.co_tipdoc FROM tbr_tipdocdetprg as b WHERE b.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND "
                            + " b.co_loc = " + objZafParSis.getCodigoLocal() + " AND  b.co_mnu =" + objZafParSis.getCodigoMenu() + " ";
                    if (objZafParSis.getCodigoMenu() == 286) {
                        strSql =  " SELECT -1 AS co_empComRel, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor, a3.tx_nom as tx_deslar , a.ne_numdoc, a.fe_doc, a.co_cli, a.tx_nomcli , a2.co_bod as co_ptopar, 0 as ne_numorddes , '' AS tx_motMovInv"
                                + " FROM  tbm_cabmovinv as a "
                                + " INNER JOIN tbm_detmovinv AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc) "
                                + " INNER JOIN tbm_loc AS a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc) "
                                + " INNER JOIN tbm_bod AS a4 ON (a4.co_emp=a.co_emp AND a4.co_bod=a2.co_bod ) "
                                + " INNER JOIN tbm_cabTipDoc AS a5 ON (a5.co_emp=a.co_emp AND a5.co_loc=a.co_loc AND a5.co_tipDoc=a.co_tipDoc) "
                                + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a2.co_bod) "
                                + " WHERE a.co_tipdoc in ( " + strAux + "  ) AND a.st_reg not in ('E','I')  AND ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=" + txtCodBod.getText() + ")  "
                                + " AND ( a.co_locrelsoldevven IS NULL OR a.co_locrelsoldevven=0 ) "
                                + " AND ( a.co_tipdocrelsoldevven IS NULL OR a.co_tipdocrelsoldevven=0 )   "
                                + " AND ( a.co_docrelsoldevven IS NULL OR a.co_docrelsoldevven=0 )    "
                                + " AND  a2.nd_can > 0  AND a.st_conInv IN('P','E')  AND  a.ne_numdoc=" + txtNumDocCon.getText() + "  "
                                + " GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor, a3.tx_nom , a.ne_numdoc, a.fe_doc, a.co_cli, a.tx_nomcli, a2.co_bod  "
                                + " ORDER BY a.fe_Doc,a.ne_numdoc ";
                        //System.out.println("txtNumDocConKeyPressed:" + strSql);
                    }
                    if (objZafParSis.getCodigoMenu() == 2205) 
                    {
                        strSql = " SELECT a8.tx_nom as nomBodIng, a.st_tipguirem,  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor , a3.tx_nom as tx_deslar,  a.ne_numdoc, a.fe_doc, a.co_clides as co_cli,"
                                + "       a.tx_nomclides as tx_nomcli, null as nd_tot, a.co_ptopar "
                                + " FROM  tbm_cabguirem as a "
                                + " INNER JOIN tbm_detguirem AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc) "
                                + " INNER JOIN tbm_loc AS a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc) "
                                + " INNER JOIN tbm_bod AS a4 ON (a4.co_emp=a.co_emp AND a4.co_bod=a.co_ptopar ) "
                                + " INNER JOIN tbm_cabTipDoc AS a5 ON (a5.co_emp=a.co_emp AND a5.co_loc=a.co_loc AND a5.co_tipDoc=a.co_tipDoc) "
                                + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) "
                                + " LEFT  JOIN tbm_bod as a8 on (a8.co_emp=a.co_emp and a8.co_bod=a.co_ptodes ) "
                                + " WHERE  a.co_tipdoc in ( " + strAux + "  )  AND ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=" + txtCodBod.getText() + ") "
                                + " AND a.st_reg NOT IN('I','E') and  a.ne_numdoc > 0  AND a.st_conInv = 'P'  AND a.st_tieguisec='N'   AND a.ne_numdoc=" + txtNumDocCon.getText() + " "
                                + " GROUP BY a8.tx_nom,  a.st_tipguirem, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor, a3.tx_nom,  a.ne_numdoc, a.fe_doc, a.co_clides, a.tx_nomclides  ,a.co_ptopar  "
                                + " ORDER BY a.ne_numdoc ";
                    }
                    if (objZafParSis.getCodigoMenu() == 1974) {
                        strSql = "SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor , a3.tx_nom as tx_deslar,  a.ne_numdoc, a.fe_doc, a.co_clides as co_cli,"
                                + " a.tx_nomclides as tx_nomcli, null as nd_tot, a.co_ptopar "
                                + " FROM  tbm_cabguirem as a "
                                + "  INNER JOIN tbm_detguirem AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc) "
                                + "  INNER JOIN tbm_loc AS a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc) "
                                + "  INNER JOIN tbm_bod AS a4 ON (a4.co_emp=a.co_emp AND a4.co_bod=a.co_ptopar ) "
                                + "  INNER JOIN tbm_cabTipDoc AS a5 ON (a5.co_emp=a.co_emp AND a5.co_loc=a.co_loc AND a5.co_tipDoc=a.co_tipDoc) "
                                + "  INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) "
                                + "  WHERE  a.co_tipdoc in ( " + strAux + "  )  AND ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=" + txtCodBod.getText() + ") "
                                + "  AND a.st_reg NOT IN('I','E')  and  a.ne_numdoc > 0 AND a.st_conInv = 'C'  AND a.st_tieguisec='N'   AND a.ne_numdoc=" + txtNumDocCon.getText() + " "
                                + "  GROUP BY  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor, a3.tx_nom,  a.ne_numdoc, a.fe_doc, a.co_clides, a.tx_nomclides  ,a.co_ptopar  "
                                + "  ORDER BY a.ne_numdoc ";

                    }
                    if (objZafParSis.getCodigoMenu() == 2063) {
                        strSql =  " SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc  ,a2.tx_descor, a2.tx_deslar, a.ne_numdoc, a.fe_doc, a.co_cli, a3.tx_nom as tx_nomcli , 0.0 as nd_tot  "
                                + " FROM tbm_cabsolsaltemmer AS a "
                                + " inner join tbm_detsolsaltemmer as a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc ) "
                                + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                                + " LEFT JOIN tbm_cli AS a3 ON(a3.co_emp=a.co_emp AND a3.co_cli=a.co_cli ) "
                                + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                                + " AND  a.co_tipdoc IN( " + strAux + " )  AND a1.co_bod=" + txtCodBod.getText() + " "
                                + " AND  a.ne_numdoc=" + txtNumDocCon.getText() + " AND a.st_aut='A' and  a.st_conTotMerEgr='N'  AND a.st_reg NOT IN('I','E')"
                                + " group by a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc  ,a2.tx_descor, a2.tx_deslar, a.ne_numdoc, a.fe_doc, a.co_cli, a3.tx_nom, nd_tot ";
                    }
                    if (objZafParSis.getCodigoMenu() == 2073) {
                        strSql =  " SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc  ,a2.tx_descor, a2.tx_deslar, a.ne_numdoc, a.fe_doc, a.co_cli, a3.tx_nom as tx_nomcli , 0.0 as nd_tot  "
                                + " FROM tbm_cabsolsaltemmer AS a "
                                + " inner join tbm_detsolsaltemmer as a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc ) "
                                + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                                + " left JOIN tbm_cli AS a3 ON(a3.co_emp=a.co_emp AND a3.co_cli=a.co_cli ) "
                                + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                                + " AND  a.co_tipdoc IN( " + strAux + " )  AND a1.co_bod=" + txtCodBod.getText() + " "
                                + " AND  a.ne_numdoc=" + txtNumDocCon.getText() + " AND a.st_aut='A' and  a.st_conTotMerEgr='S' and  a.st_conTotMerIng='N'  AND a.st_reg NOT IN('I','E')"
                                + " group by a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc  ,a2.tx_descor, a2.tx_deslar, a.ne_numdoc, a.fe_doc, a.co_cli, a3.tx_nom, nd_tot ";
                    }
                    cargarVenBusDoc(strSql, txtCodBod.getText(), txtNomBod.getText());
                }
            }
        }
    }
}//GEN-LAST:event_txtNumDocConKeyPressed

private void butBusBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusBodActionPerformed
    vcoBodUsr.setTitle("Listado de Bodegas");
    vcoBodUsr.setCampoBusqueda(1);
    vcoBodUsr.show();
    if (vcoBodUsr.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
        txtCodBod.setText(vcoBodUsr.getValueAt(1));
        txtNomBod.setText(vcoBodUsr.getValueAt(2));
        strCodBod = vcoBodUsr.getValueAt(1);
        strNomBod = vcoBodUsr.getValueAt(2);
    }
}//GEN-LAST:event_butBusBodActionPerformed

private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
    txtCodBod.transferFocus();
}//GEN-LAST:event_txtCodBodActionPerformed

private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
    txtNomBod.transferFocus();
}//GEN-LAST:event_txtNomBodActionPerformed

private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
    strCodBod = txtCodBod.getText();
    txtCodBod.selectAll();
}//GEN-LAST:event_txtCodBodFocusGained

private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
    strNomBod = txtNomBod.getText();
    txtNomBod.selectAll();
}//GEN-LAST:event_txtNomBodFocusGained

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

private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
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

private void butBusCodDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusCodDocActionPerformed
    if (txtCodBod.getText().equals("")) {
        MensajeInf("Seleccione la bodega antes de consultar un documento. ");
        tabFrm.setSelectedIndex(0);
        txtCodBod.requestFocus();
    } else {
        cargarVenBusDoc("", txtCodBod.getText(), txtNomBod.getText());
    }
}//GEN-LAST:event_butBusCodDocActionPerformed

private void txtNumGuiDesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumGuiDesKeyPressed
    if (java.awt.event.KeyEvent.VK_ENTER == evt.getKeyCode()) {
        if (txtNumGuiDes.getText().trim().equals("")) {
            MensajeInf("Ingrese el numero de documento a buscar. ");
            txtNumGuiDes.setText(txtNumGuiDes.getText().trim());
            txtNumGuiDes.requestFocus();
        } else {
            if (txtCodBod.getText().equals("")) {
                MensajeInf("Seleccione la bodega antes consultar un documento. ");
                tabFrm.setSelectedIndex(0);
                txtCodBod.requestFocus();
            } else {
                if (!cargarGuiDes(txtNumGuiDes.getText())) {
                    String strSql = "";
                    String strAux = "SELECT b.co_tipdoc FROM tbr_tipdocdetprg as b WHERE b.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND "
                            + " b.co_loc = " + objZafParSis.getCodigoLocal() + " AND  b.co_mnu =" + objZafParSis.getCodigoMenu() + " ";

                    strSql = "SELECT a8.tx_nom as nomBodIng, a.st_tipguirem,  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor , a3.tx_nom as tx_deslar,  a.ne_numdoc, a.fe_doc, a.co_clides as co_cli,"
                            + " a.tx_nomclides as tx_nomcli, null as nd_tot, a.co_ptopar "
                            + " FROM  tbm_cabguirem as a "
                            + " INNER JOIN tbm_detguirem AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc) "
                            + " INNER JOIN tbm_loc AS a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc) "
                            + " INNER JOIN tbm_bod AS a4 ON (a4.co_emp=a.co_emp AND a4.co_bod=a.co_ptopar ) "
                            + " INNER JOIN tbm_cabTipDoc AS a5 ON (a5.co_emp=a.co_emp AND a5.co_loc=a.co_loc AND a5.co_tipDoc=a.co_tipDoc) "
                            + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) "
                            + " LEFT  JOIN tbm_bod as a8 on (a8.co_emp=a.co_emp and a8.co_bod=a.co_ptodes ) "
                            + " WHERE  a.co_tipdoc in ( " + strAux + "  )  AND ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=" + txtCodBod.getText() + ") "
                            + " AND a.st_reg NOT IN('I','E') and  a.ne_numdoc > 0  AND a.st_conInv in ('P','E')  AND a.st_tieguisec='N'   AND a.ne_numorddes=" + txtNumGuiDes.getText() + " "
                            + " GROUP BY a8.tx_nom,  a.st_tipguirem, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor, a3.tx_nom,  a.ne_numdoc, a.fe_doc, a.co_clides, a.tx_nomclides  ,a.co_ptopar  "
                            + " ORDER BY a.ne_numdoc ";
                    System.out.println("INGRESO EGRESO ZafCom19: " + strSql);
                    cargarVenBusDoc(strSql, txtCodBod.getText(), txtNomBod.getText());
                }
            }
        }
    }
}//GEN-LAST:event_txtNumGuiDesKeyPressed

    private void optEnvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optEnvActionPerformed
        if (optEnv.isSelected()) {
            txtPlaVeh.setText("");
            txtDesLarVeh.setText("");
            txtIdeCho.setText("");
            txtNomCho.setText("");
            txtIdeTra.setText("");
            txtNomTra.setText("");
            txtPlaVehTra.setText("");
            bloquea(txtPlaVeh, true);
            bloquea(txtDesLarVeh, true);
            butVeh.setEnabled(true);
            bloquea(txtIdeCho, true);
            bloquea(txtNomCho, true);
            butCho.setEnabled(true);
            bloquea(txtIdeTra, false);
            bloquea(txtNomTra, false);
            bloquea(txtPlaVehTra, false);
        }
    }//GEN-LAST:event_optEnvActionPerformed

    private void txtPlaVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlaVehActionPerformed
        txtPlaVeh.transferFocus();
    }//GEN-LAST:event_txtPlaVehActionPerformed

    private void txtPlaVehFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPlaVehFocusGained
        strPlaVeh = txtPlaVeh.getText();
        txtPlaVeh.selectAll();
    }//GEN-LAST:event_txtPlaVehFocusGained

    private void txtPlaVehFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPlaVehFocusLost
        if (!txtPlaVeh.getText().equalsIgnoreCase(strPlaVeh)) {
            if (txtPlaVeh.getText().equals("")) {
                txtCodVeh.setText("");
                txtPlaVeh.setText("");
                txtDesLarVeh.setText("");
                txtPesVeh.setText("");
                txtCodCho.setText("");
                txtIdeCho.setText("");
                txtNomCho.setText("");
            } else {
                BuscarVehiculo("a1.tx_pla", txtPlaVeh.getText(), 1);
            }
        } else {
            txtPlaVeh.setText(strPlaVeh);
        }
    }//GEN-LAST:event_txtPlaVehFocusLost

    private void txtDesLarVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarVehActionPerformed
        txtDesLarVeh.transferFocus();
    }//GEN-LAST:event_txtDesLarVehActionPerformed

    private void txtDesLarVehFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarVehFocusGained
        strDesLarVeh = txtDesLarVeh.getText();
        txtDesLarVeh.selectAll();
    }//GEN-LAST:event_txtDesLarVehFocusGained

    private void txtDesLarVehFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarVehFocusLost
        if (!txtDesLarVeh.getText().equalsIgnoreCase(strDesLarVeh)) {
            if (txtDesLarVeh.getText().equals("")) {
                txtCodVeh.setText("");
                txtPlaVeh.setText("");
                txtDesLarVeh.setText("");
                txtPesVeh.setText("");
                txtCodCho.setText("");
                txtIdeCho.setText("");
                txtNomCho.setText("");
            } else {
                BuscarVehiculo("a1.tx_desLar", txtDesLarVeh.getText(), 2);
            }
        } else {
            txtDesLarVeh.setText(strDesLarVeh);
        }
    }//GEN-LAST:event_txtDesLarVehFocusLost

    private void butVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVehActionPerformed
        vcoVeh.setTitle("Listado de Vehículos");
        vcoVeh.setCampoBusqueda(1);
        vcoVeh.show();
        if (vcoVeh.getSelectedButton() == vcoVeh.INT_BUT_ACE) {
            txtCodVeh.setText(vcoVeh.getValueAt(1));
            txtPlaVeh.setText(vcoVeh.getValueAt(2));
            txtDesLarVeh.setText(vcoVeh.getValueAt(3));
            txtPesVeh.setText(vcoVeh.getValueAt(4));
            txtCodCho.setText(vcoVeh.getValueAt(5));
            txtIdeCho.setText(vcoVeh.getValueAt(6));
            txtNomCho.setText(vcoVeh.getValueAt(7));
            strCodVeh = vcoVeh.getValueAt(1);
            strPesVeh = vcoVeh.getValueAt(4);
            strCodCho = vcoVeh.getValueAt(5);
            optEnv.setSelected(true);
        }
    }//GEN-LAST:event_butVehActionPerformed

    private void txtIdeChoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdeChoActionPerformed
        txtIdeCho.transferFocus();
    }//GEN-LAST:event_txtIdeChoActionPerformed

    private void txtIdeChoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeChoFocusGained
        strIdeCho = txtIdeCho.getText();
        txtIdeCho.selectAll();
    }//GEN-LAST:event_txtIdeChoFocusGained

    private void txtIdeChoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeChoFocusLost
        if (!txtIdeCho.getText().equalsIgnoreCase(strIdeCho)) {
            if (txtIdeCho.getText().equals("")) {
                txtCodCho.setText("");
                txtIdeCho.setText("");
                txtNomCho.setText("");
            } else {
                BuscarChofer("a1.tx_ide", txtIdeCho.getText(), 1);
            }
        } else {
            txtIdeCho.setText(strIdeCho);
        }
    }//GEN-LAST:event_txtIdeChoFocusLost

    private void txtNomChoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomChoActionPerformed
        txtNomCho.transferFocus();
    }//GEN-LAST:event_txtNomChoActionPerformed

    private void txtNomChoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomChoFocusGained
        strNomCho = txtNomCho.getText();
        txtNomCho.selectAll();
    }//GEN-LAST:event_txtNomChoFocusGained

    private void txtNomChoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomChoFocusLost
        if (!txtNomCho.getText().equalsIgnoreCase(strNomCho)) {
            if (txtNomCho.getText().equals("")) {
                txtCodCho.setText("");
                txtIdeCho.setText("");
                txtNomCho.setText("");
            } else {
                BuscarChofer("a1.tx_nom", txtNomCho.getText(), 2);
            }
        } else {
            txtNomCho.setText(strNomCho);
        }
    }//GEN-LAST:event_txtNomChoFocusLost

    private void butChoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butChoActionPerformed
        vcoCho.setTitle("Listado de Choferes");
        vcoCho.setCampoBusqueda(2);
        vcoCho.show();
        if (vcoCho.getSelectedButton() == vcoCho.INT_BUT_ACE) {
            txtCodCho.setText(vcoCho.getValueAt(1));
            txtIdeCho.setText(vcoCho.getValueAt(2));
            txtNomCho.setText(vcoCho.getValueAt(3));
            optEnv.setSelected(true);
        }
    }//GEN-LAST:event_butChoActionPerformed

    private void optVehTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optVehTraActionPerformed
        if (optVehTra.isSelected()) {
            txtPlaVeh.setText("");
            txtDesLarVeh.setText("");
            txtIdeCho.setText("");
            txtNomCho.setText("");
            txtIdeTra.setText("");
            txtNomTra.setText("");
            txtPlaVehTra.setText("");
            optRet.setSelected(true);

            bloquea(txtPlaVeh, false);
            bloquea(txtDesLarVeh, false);
            butVeh.setEnabled(false);
            bloquea(txtIdeCho, false);
            bloquea(txtNomCho, false);
            butCho.setEnabled(false);
            bloquea(txtIdeTra, true);
            bloquea(txtNomTra, true);
            bloquea(txtPlaVehTra, true);
        }
    }//GEN-LAST:event_optVehTraActionPerformed

    private void txtIdeTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdeTraActionPerformed
        if (!objUti.isNumero(txtIdeTra.getText())) {
            MensajeInf("Ingrese solo valores numericos");
            txtIdeTra.setText("");
            txtIdeTra.requestFocus();
        }
        txtIdeTra.transferFocus();
    }//GEN-LAST:event_txtIdeTraActionPerformed

    private void txtIdeTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeTraFocusGained
        txtIdeTra.selectAll();
    }//GEN-LAST:event_txtIdeTraFocusGained

    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        txtNomTra.transferFocus();
    }//GEN-LAST:event_txtNomTraActionPerformed

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        txtNomTra.selectAll();
    }//GEN-LAST:event_txtNomTraFocusGained

    private void optRetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optRetActionPerformed
        if (optRet.isSelected()) {
            txtPlaVeh.setText("");
            txtDesLarVeh.setText("");
            txtIdeCho.setText("");
            txtNomCho.setText("");
            txtIdeTra.setText("");
            txtNomTra.setText("");
            txtPlaVehTra.setText("");
            bloquea(txtPlaVeh, false);
            bloquea(txtDesLarVeh, false);
            butVeh.setEnabled(false);
            bloquea(txtIdeCho, false);
            bloquea(txtNomCho, false);
            butCho.setEnabled(false);
            bloquea(txtIdeTra, true);
            bloquea(txtNomTra, true);
            bloquea(txtPlaVehTra, true);
        }
    }//GEN-LAST:event_optRetActionPerformed

    private void optVehCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optVehCliActionPerformed
        if (optVehCli.isSelected()) {
            txtPlaVeh.setText("");
            txtDesLarVeh.setText("");
            txtIdeCho.setText("");
            txtNomCho.setText("");
            txtIdeTra.setText(txtRucCliPro.getText());
            txtNomTra.setText(txtNomCliPro.getText());
            txtPlaVehTra.setText("");
            optRet.setSelected(true);
            bloquea(txtPlaVeh, false);
            bloquea(txtDesLarVeh, false);
            butVeh.setEnabled(false);
            bloquea(txtIdeCho, false);
            bloquea(txtNomCho, false);
            butCho.setEnabled(false);
            bloquea(txtIdeTra, false);
            bloquea(txtNomTra, false);
            bloquea(txtPlaVehTra, true);
        }
    }//GEN-LAST:event_optVehCliActionPerformed

    private void txtDesCorTipDocConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocConActionPerformed
    }//GEN-LAST:event_txtDesCorTipDocConActionPerformed

    private void txtCodForRetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodForRetKeyPressed
    }//GEN-LAST:event_txtCodForRetKeyPressed

    /**
     * Esta funcion se encarga de buscar la factura y cargar los datos
     */
    private boolean cargarDoc(String strNumDoc) {
        boolean blnRes = false;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "", strSqlCon = "", strAux = "";
        try {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null) {
                stmLoc = conn.createStatement();
                strAux = "SELECT b.co_tipdoc FROM tbr_tipdocdetprg as b WHERE b.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND "
                        + " b.co_loc = " + objZafParSis.getCodigoLocal() + " AND  b.co_mnu =" + objZafParSis.getCodigoMenu() + " ";

                if (objZafParSis.getCodigoMenu() == 286) {
                    strSql = "SELECT  a5.st_meringegrfisbod, a5.tx_natDoc, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_Doc, a5.tx_descor, a5.tx_deslar "
                            + " ,a.co_cli, a.tx_nomcli, a.co_com, a.tx_nomven, a.co_forret ,a7.tx_deslar as desforret , a2.co_bod as co_ptopar  "
                            + " FROM  tbm_cabmovinv as a  INNER JOIN tbm_detmovinv AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc)   "
                            + " INNER JOIN tbm_loc AS a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc)  "
                            + " INNER JOIN tbm_bod AS a4 ON (a4.co_emp=a.co_emp AND a4.co_bod=a2.co_bod )   "
                            + " INNER JOIN tbm_cabTipDoc AS a5 ON (a5.co_emp=a.co_emp AND a5.co_loc=a.co_loc AND a5.co_tipDoc=a.co_tipDoc)   "
                            + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a2.co_bod)   "
                            + " LEFT  JOIN tbm_var as a7 on (a7.co_reg=a.co_forret) "
                            + " WHERE  a.co_tipdoc in ( " + strAux + "  ) AND  a.st_reg not in ('E','I')   and ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=" + txtCodBod.getText() + ")  "
                            + " AND  a2.nd_can > 0  AND a.st_conInv IN('P','E')  "
                            + " AND  ( a.co_locrelsoldevven IS NULL OR a.co_locrelsoldevven=0 )  "
                            + " AND  ( a.co_tipdocrelsoldevven IS NULL OR a.co_tipdocrelsoldevven=0 )   "
                            + " AND ( a.co_docrelsoldevven IS NULL OR a.co_docrelsoldevven=0 )  AND  a.ne_numdoc=" + strNumDoc + " "
                            + " GROUP BY a5.st_meringegrfisbod, a5.tx_natDoc, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_Doc, a5.tx_descor, a5.tx_deslar "
                            + " ,a.co_cli, a.tx_nomcli, a.co_com, a.tx_nomven, a.co_forret ,a7.tx_deslar, a2.co_bod ";
                }
                if (objZafParSis.getCodigoMenu() == 2205) {
                    strSql = "SELECT a9.co_empgrp,  a8.tx_nom as nomBodIng, a.st_tipguirem,  a5.st_meringegrfisbod, a5.tx_natDoc, a.co_emp, a.co_loc, a.co_tipdoc, a5.tx_descor, a5.tx_deslar  "
                            + " ,a.co_clides as co_cli, a.tx_nomclides as tx_nomcli, a.co_ven as co_com, a.tx_nomven, a.co_forret ,a7.tx_deslar as desforret   "
                            + "  ,a.co_doc, a.fe_Doc , a.co_ptopar , case when a.ne_numorddes is null then 0 else a.ne_numorddes end as ne_numorddes "
                            + " FROM  tbm_cabguirem as a "
                            + "  INNER JOIN tbm_detguirem AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc) "
                            + "  INNER JOIN tbm_loc AS a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc) "
                            + "  INNER JOIN tbm_bod AS a4 ON (a4.co_emp=a.co_emp AND a4.co_bod=a.co_ptopar ) "
                            + "  INNER JOIN tbm_cabTipDoc AS a5 ON (a5.co_emp=a.co_emp AND a5.co_loc=a.co_loc AND a5.co_tipDoc=a.co_tipDoc) "
                            + "  INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) "
                            + "  LEFT  JOIN tbm_var as a7 on (a7.co_reg=a.co_forret) "
                            + "  LEFT  JOIN tbm_bod as a8 on (a8.co_emp=a.co_emp and a8.co_bod=a.co_ptodes ) "
                            + "  LEFT JOIN  tbm_cli as a9 on (a9.co_emp=a.co_emp and a9.co_cli=a.co_clides )  "
                            + "  WHERE  a.co_tipdoc in ( " + strAux + "  ) "
                            + "  AND ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=" + txtCodBod.getText() + ") "
                            + "  AND  a.ne_numdoc=" + strNumDoc + " "
                            + "  AND a.st_reg NOT IN('I','E')  AND a.st_conInv IN('P','E') AND a.st_tieguisec='N'   "
                            + "  GROUP BY  a9.co_empgrp,  a8.tx_nom, a.st_tipguirem, a5.st_meringegrfisbod, a5.tx_natDoc, a.co_emp, a.co_loc, a.co_tipdoc, a5.tx_descor, a5.tx_deslar  "
                            + " ,a.co_clides , a.tx_nomclides , a.co_ven , a.tx_nomven, a.co_forret ,a7.tx_deslar, a.co_doc, a.fe_Doc, a.co_ptopar, a.ne_numorddes"
                            + "  ";
                }
                if (objZafParSis.getCodigoMenu() == 1974) {
                    strSql = "SELECT   a5.st_meringegrfisbod, a5.tx_natDoc, a.co_emp, a.co_loc, a.co_tipdoc, a5.tx_descor, a5.tx_deslar  "
                            + " ,a.co_clides as co_cli, a.tx_nomclides as tx_nomcli, a.co_ven as co_com, a.tx_nomven, a.co_forret ,a7.tx_deslar as desforret   "
                            + "  ,a.co_doc, a.fe_Doc , a.co_ptopar "
                            + " FROM  tbm_cabguirem as a "
                            + "  INNER JOIN tbm_detguirem AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc) "
                            + "  INNER JOIN tbm_loc AS a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc) "
                            + "  INNER JOIN tbm_bod AS a4 ON (a4.co_emp=a.co_emp AND a4.co_bod=a.co_ptopar ) "
                            + "  INNER JOIN tbm_cabTipDoc AS a5 ON (a5.co_emp=a.co_emp AND a5.co_loc=a.co_loc AND a5.co_tipDoc=a.co_tipDoc) "
                            + "  INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) "
                            + " LEFT  JOIN tbm_var as a7 on (a7.co_reg=a.co_forret) "
                            + "  WHERE  a.co_tipdoc in ( " + strAux + "  ) "
                            + "  AND ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=" + txtCodBod.getText() + ") "
                            + "  AND  a.ne_numdoc=" + strNumDoc + " "
                            + "  AND a.st_reg NOT IN('I','E')  AND a.st_conInv IN('C','E','F') AND a.st_tieguisec='N'   "
                            + "  GROUP BY a5.st_meringegrfisbod, a5.tx_natDoc, a.co_emp, a.co_loc, a.co_tipdoc, a5.tx_descor, a5.tx_deslar  "
                            + " ,a.co_clides , a.tx_nomclides , a.co_ven , a.tx_nomven, a.co_forret ,a7.tx_deslar, a.co_doc, a.fe_Doc, a.co_ptopar"
                            + "  ";
                }
                if (objZafParSis.getCodigoMenu() == 2063) {
                    strSql = "SELECT a2.st_meringegrfisbod, a2.tx_natDoc,  a.co_emp, a.co_loc, a.co_tipdoc, a2.tx_descor, a2.tx_deslar "
                            + " ,a.co_cli, a3.tx_nom as tx_nomcli, a.co_doc, a.fe_Doc, a4.co_usr as co_com, a4.tx_nom as tx_nomven, null as co_forret , null as desforret"
                            + " FROM tbm_cabsolsaltemmer AS a "
                            + " inner join tbm_detsolsaltemmer as a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc ) "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                            + " left JOIN tbm_cli AS a3 ON(a3.co_emp=a.co_emp AND a3.co_cli=a.co_cli ) "
                            + " left join tbm_usr as a4  ON(a4.co_usr=a.co_usrsol) "
                            + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " AND  a.co_tipdoc IN( " + strAux + " )  AND a1.co_bod=" + txtCodBod.getText() + " "
                            + " AND  a.ne_numdoc=" + strNumDoc + " AND a.st_reg NOT IN('I','E') AND  a.st_aut='A' and  a.st_conTotMerEgr='N' "
                            + " group by a2.st_meringegrfisbod, a2.tx_natDoc,  a.co_emp, a.co_loc, a.co_tipdoc, a2.tx_descor, a2.tx_deslar "
                            + " ,a.co_cli, a3.tx_nom, a.co_doc, a.fe_Doc, a4.co_usr,  a4.tx_nom  ";
                }
                if (objZafParSis.getCodigoMenu() == 2073) {
                    strSql = "SELECT a2.st_meringegrfisbod, a2.tx_natDoc,  a.co_emp, a.co_loc, a.co_tipdoc, a2.tx_descor, a2.tx_deslar "
                            + " ,a.co_cli, a3.tx_nom as tx_nomcli, a.co_doc, a.fe_Doc, a4.co_usr as co_com, a4.tx_nom as tx_nomven, null as co_forret , null as desforret"
                            + " FROM tbm_cabsolsaltemmer AS a "
                            + " inner join tbm_detsolsaltemmer as a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc ) "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                            + " left JOIN tbm_cli AS a3 ON(a3.co_emp=a.co_emp AND a3.co_cli=a.co_cli ) "
                            + " left join tbm_usr as a4  ON(a4.co_usr=a.co_usrsol) "
                            + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " AND  a.co_tipdoc IN( " + strAux + " )  AND a1.co_bod=" + txtCodBod.getText() + " "
                            + " AND  a.ne_numdoc=" + strNumDoc + " AND a.st_reg NOT IN('I','E') AND  a.st_aut='A' and  a.st_conTotMerEgr='S' and  a.st_conTotMerIng='N'  "
                            + " group by a2.st_meringegrfisbod, a2.tx_natDoc,  a.co_emp, a.co_loc, a.co_tipdoc, a2.tx_descor, a2.tx_deslar "
                            + " ,a.co_cli, a3.tx_nom, a.co_doc, a.fe_Doc, a4.co_usr,  a4.tx_nom  ";
                }
                strSqlCon = "select count(*) as cant from (" + strSql + " ) as x ";
                rstLoc = stmLoc.executeQuery(strSqlCon);
                if (rstLoc.next()) {
                    if (rstLoc.getInt("cant") > 1) {
                        blnRes = true;
                    }
                }
                if (!blnRes) {
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        STR_COD_EMP_REL = rstLoc.getString("co_emp");
                        STR_COD_LOC_REL = rstLoc.getString("co_loc");
                        STR_COD_BOD_GUIA = rstLoc.getString("co_ptopar");
                        if (verificaTipDoc(rstLoc.getString("tx_natDoc"))) {
                            if (cargarDocCab(conn, rstLoc)) {
                                if (cargarDocDet(conn, rstLoc, rstLoc.getString("tx_natDoc"))) {
                                    bloquea(txtNumDocCon, false);
                                    blnRes = true;
                                    if (objZafParSis.getCodigoMenu() == 2205) {
                                        javax.swing.JLabel objlbl = new javax.swing.JLabel();
                                        objlbl.setFont(new java.awt.Font("SansSerif Bold", 1, 16));
                                        objlbl.setForeground(new java.awt.Color(255, 0, 0));
                                        objlbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                                        if (_getVerificarPagCont(conn)) {
                                            objlbl.setText("Pago Pendiente (Venta de Contado )");
                                            this.setGlassPane(objlbl);
                                            objlbl.setVisible(true);
                                            objlbl = null;
                                        } else if (_getVerificarPagChq(conn)) {
                                            objlbl.setText("Cheque Pendiente (Cheque a Fecha )");
                                            this.setGlassPane(objlbl);
                                            objlbl.setVisible(true);
                                            objlbl = null;
                                        } else {
                                            objlbl.setText("");
                                            this.setGlassPane(objlbl);
                                            objlbl.setVisible(false);
                                        }
                                        objlbl = null;
                                        strFecSisBase = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());
                                    }
                                }
                            }
                        }
                    }
                } else {
                    blnRes = false;
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

    private boolean cargarGuiDes(String strNumDoc) {
        boolean blnRes = false;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "", strSqlCon = "";
        try {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null) {
                stmLoc = conn.createStatement();
                strAux = "SELECT b.co_tipdoc FROM tbr_tipdocdetprg as b WHERE b.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND "
                        + " b.co_loc = " + objZafParSis.getCodigoLocal() + " AND  b.co_mnu =" + objZafParSis.getCodigoMenu() + " ";

                if (objZafParSis.getCodigoMenu() == 2205) {
                    strSql = "SELECT *, CASE WHEN x.co_tipDocRel IN(1,228,124,238,127,242,125,126,166,225,206,153,58,96,97,98) THEN 'VENTA' ELSE 'REPOSICIÓN' END AS tx_motMovInv,"
                            + " CASE WHEN x.co_tipDocRel IN(1,228,124,238,127,242,125,126,166,225,206,153,58,96,97,98) THEN 'V' ELSE 'R' END AS tx_motMovInvJta "
                            + " from ( SELECT a9.co_empgrp,  a8.tx_nom as nomBodIng, a.st_tipguirem,  a5.st_meringegrfisbod, a5.tx_natDoc, a.co_emp, a.co_loc, a.co_tipdoc, a5.tx_descor, a5.tx_deslar  "
                            + " ,a.co_clides as co_cli, a.tx_rucclides as tx_ruccli, a.tx_nomclides as tx_nomcli, a.co_ven as co_com, a.tx_nomven, a.co_forret ,a7.tx_deslar as desforret   "
                            + "  ,a.co_doc, a.fe_Doc , a.co_ptopar , case when a.ne_numorddes is null then 0 else a.ne_numorddes end as ne_numorddes "
                            + " ,(  select count( x.co_reg ) from tbm_detguirem as x "
                            + " INNER JOIN tbm_detmovinv AS x1 ON(x1.co_emp=x.co_emprel AND x1.co_loc=x.co_locrel AND x1.co_tipdoc=x.co_tipdocrel and x1.co_doc=x.co_docrel and x1.co_reg=x.co_regrel )   "
                            + " where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc= a.co_doc "
                            + " and x1.st_cliretemprel is null "
                            + "  ) as exitcr "
                            + " , a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel"
                            + " FROM  tbm_cabguirem as a "
                            + "  INNER JOIN tbm_detguirem AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc) "
                            + "  INNER JOIN tbm_loc AS a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc) "
                            + "  INNER JOIN tbm_bod AS a4 ON (a4.co_emp=a.co_emp AND a4.co_bod=a.co_ptopar ) "
                            + "  INNER JOIN tbm_cabTipDoc AS a5 ON (a5.co_emp=a.co_emp AND a5.co_loc=a.co_loc AND a5.co_tipDoc=a.co_tipDoc) "
                            + "  INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) "
                            + "  LEFT  JOIN tbm_var as a7 on (a7.co_reg=a.co_forret) "
                            + "  LEFT  JOIN tbm_bod as a8 on (a8.co_emp=a.co_emp and a8.co_bod=a.co_ptodes ) "
                            + " LEFT JOIN  tbm_cli as a9 on (a9.co_emp=a.co_emp and a9.co_cli=a.co_clides )  "
                            + " WHERE  a.co_tipdoc in ( " + strAux + "  ) "
                            + "  AND ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=" + txtCodBod.getText() + ") "
                            + "  AND  a.ne_numorddes=" + strNumDoc + " "
                            + "  AND a.st_reg NOT IN('I','E')  AND a.st_conInv IN('P','E') " + //AND a.st_tieguisec='N'   " +
                            "  GROUP BY  a9.co_empgrp,  a8.tx_nom, a.st_tipguirem, a5.st_meringegrfisbod, a5.tx_natDoc, a.co_emp, a.co_loc, a.co_tipdoc, a5.tx_descor, a5.tx_deslar  "
                            + " ,a.co_clides , a.tx_rucclides, a.tx_nomclides , a.co_ven , a.tx_nomven, a.co_forret ,a7.tx_deslar, a.co_doc, a.fe_Doc, a.co_ptopar, a.ne_numorddes"
                            + " , a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel"
                            + " ) as x where exitcr > 0  ";
                } else if (objZafParSis.getCodigoMenu() == 2915) {
                    strSql = "SELECT *, CASE WHEN x.co_tipDocRel IN(1,228,124,238,127,242,125,126,166,225,206,153,58,96,97,98) THEN 'VENTA' ELSE 'REPOSICIÓN' END AS tx_motMovInv,"
                            + " CASE WHEN x.co_tipDocRel IN(1,228,124,238,127,242,125,126,166,225,206,153,58,96,97,98) THEN 'V' ELSE 'R' END AS tx_motMovInvJta "
                            + " from ( SELECT a9.co_empgrp,  a8.tx_nom as nomBodIng, a.st_tipguirem,  a5.st_meringegrfisbod, a5.tx_natDoc, a.co_emp, a.co_loc, a.co_tipdoc, a5.tx_descor, a5.tx_deslar  "
                            + " ,a.co_clides as co_cli, a.tx_rucclides as tx_ruccli, a.tx_nomclides as tx_nomcli, a.co_ven as co_com, a.tx_nomven, a.co_forret ,a7.tx_deslar as desforret   "
                            + "  ,a.co_doc, a.fe_Doc , a.co_ptopar , case when a.ne_numorddes is null then 0 else a.ne_numorddes end as ne_numorddes "
                            + " ,(  select count( x.co_reg ) from tbm_detguirem as x "
                            + " INNER JOIN tbm_detmovinv AS x1 ON(x1.co_emp=x.co_emprel AND x1.co_loc=x.co_locrel AND x1.co_tipdoc=x.co_tipdocrel and x1.co_doc=x.co_docrel and x1.co_reg=x.co_regrel )   "
                            + " INNER JOIN tbr_detmovinv as x4 ON (x4.co_emp=x1.co_emp and x4.co_loc=x1.co_loc and x4.co_tipdoc=x1.co_tipdoc and x4.co_doc=x1.co_doc and x4.co_reg=x1.co_reg and x4.st_reg= 'A' ) "
                            + " INNER JOIN tbm_detmovinv as x5 ON (x5.co_emp=x4.co_emprel and x5.co_loc=x4.co_locrel and x5.co_tipdoc=x4.co_tipdocrel and x5.co_doc=x4.co_docrel and x5.co_reg=x4.co_regrel ) "
                            + " INNER JOIN tbr_detmovinv as x6 ON (x6.co_emprel=x5.co_emp and x6.co_locrel=x5.co_loc and x6.co_tipdocrel=x5.co_tipdoc and x6.co_docrel=x5.co_doc and x6.co_regrel=x5.co_reg and "
                            + " (x6.co_emp!=x1.co_emp or x6.co_loc!=x1.co_loc or x6.co_tipdoc!=x1.co_tipdoc or x6.co_doc!=x1.co_doc) and x6.st_reg='A' ) "
                            + " INNER JOIN tbm_detmovinv as x7 ON (x7.co_emp=x6.co_emp and x7.co_loc=x6.co_loc and x7.co_tipdoc=x6.co_tipdoc and x7.co_doc=x6.co_doc and x7.co_reg=x6.co_reg ) "
                            + " INNER JOIN tbm_cabmovinv as x8 ON (x8.co_emp=x7.co_emp and x8.co_loc=x7.co_loc and x8.co_tipdoc=x7.co_tipdoc and x8.co_doc=x7.co_doc ) "
                            + " where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc= a.co_doc "
                            //          + " and x1.st_cliretemprel = 'S' "
                            + " and x1.st_cliretemprel = 'S' and x8.ne_numdoc > 0 "
                            + "  ) as exitcr "
                            + " , a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel"
                            + " FROM  tbm_cabguirem as a "
                            + "  INNER JOIN tbm_detguirem AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc) "
                            + "  INNER JOIN tbm_loc AS a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc) "
                            + "  INNER JOIN tbm_bod AS a4 ON (a4.co_emp=a.co_emp AND a4.co_bod=a.co_ptopar ) "
                            + "  INNER JOIN tbm_cabTipDoc AS a5 ON (a5.co_emp=a.co_emp AND a5.co_loc=a.co_loc AND a5.co_tipDoc=a.co_tipDoc) "
                            + "  INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) "
                            + "  LEFT  JOIN tbm_var as a7 on (a7.co_reg=a.co_forret) "
                            + "  LEFT  JOIN tbm_bod as a8 on (a8.co_emp=a.co_emp and a8.co_bod=a.co_ptodes ) "
                            + " LEFT JOIN  tbm_cli as a9 on (a9.co_emp=a.co_emp and a9.co_cli=a.co_clides )  "
                            + " WHERE  a.co_tipdoc in ( " + strAux + "  ) "
                            + "  AND ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=" + txtCodBod.getText() + ") "
                            + "  AND  a.ne_numorddes=" + strNumDoc + " "
                            + "  AND a.st_reg NOT IN('I','E')  AND a.st_conInv IN('P','E') " + //AND a.st_tieguisec='N'   " +
                            "  GROUP BY  a9.co_empgrp,  a8.tx_nom, a.st_tipguirem, a5.st_meringegrfisbod, a5.tx_natDoc, a.co_emp, a.co_loc, a.co_tipdoc, a5.tx_descor, a5.tx_deslar  "
                            + " ,a.co_clides , a.tx_rucclides, a.tx_nomclides , a.co_ven , a.tx_nomven, a.co_forret ,a7.tx_deslar, a.co_doc, a.fe_Doc, a.co_ptopar, a.ne_numorddes"
                            + " , a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel"
                            + " ) as x where exitcr > 0  ";
                }
                strSqlCon = "select count(*) as cant from (" + strSql + " ) as x ";
                System.out.println("ZafCom19.cargarGuiDes: " + strSqlCon);
                rstLoc = stmLoc.executeQuery(strSqlCon);
                if (rstLoc.next()) {
                    if (rstLoc.getInt("cant") > 1) {
                        blnRes = true;
                    }
                }
                if (!blnRes) {
                    System.out.println("ZafCom19.cargarGuiDes CABECERA RST: " + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    
                    if (rstLoc.next()) {
                        STR_COD_EMP_REL = rstLoc.getString("co_emp");
                        STR_COD_LOC_REL = rstLoc.getString("co_loc");
                        STR_COD_BOD_GUIA = rstLoc.getString("co_ptopar");
                        txtMotTra.setText(rstLoc.getString("tx_motMovInv"));

                        //System.out.println(rstLoc.getString("tx_motMovInv"));

                        if (verificaTipDoc(rstLoc.getString("tx_natDoc"))) {
                            if (cargarDocCab(conn, rstLoc)) {
                                if (cargarDocDet(conn, rstLoc, rstLoc.getString("tx_natDoc"))) {
                                    bloquea(txtNumDocCon, false);
                                    bloquea(txtNumGuiDes, false);
                                    blnRes = true;
                                }
                            }
                        }
                    }
                } else {
                    blnRes = false;
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

    public void BuscarTipDoc(String campo, String strBusqueda, int tipo) {
        vcoTipDoc.setTitle("Listado de Vendedores");
        if (vcoTipDoc.buscar(campo, strBusqueda)) {
            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
            txtDesCodTitpDoc.setText(vcoTipDoc.getValueAt(2));
            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
            strCodTipDoc = vcoTipDoc.getValueAt(1);
        } else {
            vcoTipDoc.setCampoBusqueda(tipo);
            vcoTipDoc.cargarDatos();
            vcoTipDoc.show();
            if (vcoTipDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                txtDesCodTitpDoc.setText(vcoTipDoc.getValueAt(2));
                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                strCodTipDoc = vcoTipDoc.getValueAt(1);
            } else {
                txtCodTipDoc.setText(strCodTipDoc);
                txtDesCodTitpDoc.setText(strDesCorTipDoc);
                txtDesLarTipDoc.setText(strDesLarTipDoc);
            }
        }
    }

    public void BuscarBod(String campo, String strBusqueda, int tipo) {
        vcoBodUsr.setTitle("Listado de Bodegas");
        if (vcoBodUsr.buscar(campo, strBusqueda)) {
            txtCodBod.setText(vcoBodUsr.getValueAt(1));
            txtNomBod.setText(vcoBodUsr.getValueAt(2));
            strCodBod = vcoBodUsr.getValueAt(1);
            strNomBod = vcoBodUsr.getValueAt(2);
        } else {
            vcoBodUsr.setCampoBusqueda(tipo);
            vcoBodUsr.cargarDatos();
            vcoBodUsr.show();
            if (vcoBodUsr.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                txtCodBod.setText(vcoBodUsr.getValueAt(1));
                txtNomBod.setText(vcoBodUsr.getValueAt(2));
                strCodBod = vcoBodUsr.getValueAt(1);
                strNomBod = vcoBodUsr.getValueAt(2);
            } else {
                txtCodBod.setText(strCodBod);
                txtNomBod.setText(strNomBod);
            }
        }
    }

    private void cargarVenBusDoc(String strSqlbus, String strCodBod, String strNomBod) {
        Compras.ZafCom19.ZafCom19_01 obj = new Compras.ZafCom19.ZafCom19_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis, strSqlbus, strCodBod, strNomBod);
        obj.show();
        if (obj.acepta()) {
            txtCodTipDocCon.setText(obj.GetCamSel(2));
            txtCodDocCon.setText(obj.GetCamSel(3));
            txtNumDocCon.setText(obj.GetCamSel(4));
            STR_COD_EMP_REL = obj.GetCamSel(5);
            STR_COD_LOC_REL = obj.GetCamSel(6);
            STR_COD_BOD_GUIA = obj.GetCamSel(7);
            STR_COD_EMP_REL_COM_CLI_RET = obj.GetCamSel(8);
            txtMotTra.setText(obj.GetCamSel(9));
            if (!cargarDoc()) {
                cargarVenBusDoc("", txtCodBod.getText(), txtNomBod.getText());
            }
        }
        obj.dispose();
        obj = null;
    }

    /**
     * Esta funcion se encarga de buscar el documento a confirmar.
     */
    private boolean cargarDoc() {
        boolean blnRes = false;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null) {
                stmLoc = conn.createStatement();
                strSql = "SELECT a5.st_meringegrfisbod, a5.tx_natDoc, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a5.tx_descor, a5.tx_deslar "
                        + " ,a.co_cli, a.tx_nomcli, a.co_com, a.tx_nomven, a.co_forret ,a7.tx_deslar as desforret, a.fe_Doc  "
                        + " FROM  tbm_cabmovinv as a "
                        + " INNER JOIN tbm_detmovinv AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc) "
                        + " INNER JOIN tbm_loc AS a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc) "
                        + " INNER JOIN tbm_bod AS a4 ON (a4.co_emp=a.co_emp AND a4.co_bod=a2.co_bod ) "
                        + " INNER JOIN tbm_cabTipDoc AS a5 ON (a5.co_emp=a.co_emp AND a5.co_loc=a.co_loc AND a5.co_tipDoc=a.co_tipDoc) "
                        + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a2.co_bod) "
                        + " LEFT  JOIN tbm_var as a7 on (a7.co_reg=a.co_forret) "
                        + " WHERE  a.co_emp=" + STR_COD_EMP_REL + "  AND a.co_loc=" + STR_COD_LOC_REL + "  AND  a.co_tipdoc=" + txtCodTipDocCon.getText() + " "
                        + "  AND a.co_doc=" + txtCodDocCon.getText() + " "
                        + "  AND a.st_reg not in ('E','I')   and ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=" + txtCodBod.getText() + ")  and a2.nd_can > 0   ";

                if ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915)) {
                    strSql = "SELECT a9.co_empgrp, a8.tx_nom as nomBodIng, a.st_tipguirem, a5.st_meringegrfisbod, a5.tx_natDoc, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_Doc , a5.tx_descor, a5.tx_deslar "
                            + " , a.co_clides as co_cli, a.tx_rucclides as tx_ruccli, a.tx_nomclides as tx_nomcli, a.co_ven as co_com, a.tx_nomven, a.co_forret ,a7.tx_deslar as desforret  "
                            + "  , case when a.ne_numorddes is null then 0 else a.ne_numorddes end as ne_numorddes "
                            + "  FROM  tbm_cabguirem as a "
                            + "  INNER JOIN tbm_detguirem AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc) "
                            + "  INNER JOIN tbm_loc AS a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc) "
                            + "  INNER JOIN tbm_bod AS a4 ON (a4.co_emp=a.co_emp AND a4.co_bod=a.co_ptopar ) "
                            + "  INNER JOIN tbm_cabTipDoc AS a5 ON (a5.co_emp=a.co_emp AND a5.co_loc=a.co_loc AND a5.co_tipDoc=a.co_tipDoc) "
                            + "  INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) "
                            + "  LEFT  JOIN tbm_var as a7 on (a7.co_reg=a.co_forret) "
                            + "  LEFT  JOIN tbm_bod as a8 on (a8.co_emp=a.co_emp and a8.co_bod=a.co_ptodes ) "
                            + "   LEFT JOIN  tbm_cli as a9 on (a9.co_emp=a.co_emp and a9.co_cli=a.co_clides )   "
                            + " WHERE a.co_emp=" + STR_COD_EMP_REL + "  AND a.co_loc=" + STR_COD_LOC_REL + "  AND  a.co_tipdoc=" + txtCodTipDocCon.getText() + " "
                            + "  AND a.co_doc=" + txtCodDocCon.getText() + " "
                            + "  AND ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=" + txtCodBod.getText() + ") "
                            + "  AND a.st_reg NOT IN('I','E') "
                            + "  ORDER BY a.fe_Doc ";
                }
                if (objZafParSis.getCodigoMenu() == 2063) {
                    strSql = "SELECT   a2.st_meringegrfisbod, a2.tx_natDoc,  a.co_emp, a.co_loc, a.co_tipdoc, a2.tx_descor, a2.tx_deslar "
                            + " ,a.co_cli, a3.tx_nom as tx_nomcli, a.co_doc, a.fe_Doc, a4.co_usr as co_com, a4.tx_nom as tx_nomven, null as co_forret , null as desforret"
                            + " FROM tbm_cabsolsaltemmer AS a "
                            + " inner join tbm_detsolsaltemmer as a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc ) "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                            + " left JOIN tbm_cli AS a3 ON(a3.co_emp=a.co_emp AND a3.co_cli=a.co_cli ) "
                            + " left join tbm_usr as a4  ON(a4.co_usr=a.co_usrsol) "
                            + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " AND  a.co_tipdoc =" + txtCodTipDocCon.getText() + " AND a1.co_bod=" + txtCodBod.getText() + " "
                            + " AND  a.co_doc=" + txtCodDocCon.getText() + " AND a.st_reg NOT IN('I','E') AND  a.st_aut='A' and  a.st_conTotMerEgr='N' "
                            + " group by a2.st_meringegrfisbod, a2.tx_natDoc,  a.co_emp, a.co_loc, a.co_tipdoc, a2.tx_descor, a2.tx_deslar "
                            + " ,a.co_cli, a3.tx_nom, a.co_doc, a.fe_Doc, a4.co_usr,  a4.tx_nom  ";
                }
                if (objZafParSis.getCodigoMenu() == 2073) {
                    strSql = "SELECT a2.st_meringegrfisbod, a2.tx_natDoc,  a.co_emp, a.co_loc, a.co_tipdoc, a2.tx_descor, a2.tx_deslar "
                            + " ,a.co_cli, a3.tx_nom as tx_nomcli, a.co_doc, a.fe_Doc, a4.co_usr as co_com, a4.tx_nom as tx_nomven, null as co_forret , null as desforret"
                            + " FROM tbm_cabsolsaltemmer AS a "
                            + " inner join tbm_detsolsaltemmer as a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc ) "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                            + " left JOIN tbm_cli AS a3 ON(a3.co_emp=a.co_emp AND a3.co_cli=a.co_cli ) "
                            + " left join tbm_usr as a4  ON(a4.co_usr=a.co_usrsol) "
                            + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " AND  a.co_tipdoc =" + txtCodTipDocCon.getText() + " AND a1.co_bod=" + txtCodBod.getText() + " "
                            + " AND  a.co_doc=" + txtCodDocCon.getText() + " AND a.st_reg NOT IN('I','E') AND  a.st_aut='A' and  a.st_conTotMerEgr='S' and  a.st_conTotMerIng='N' "
                            + " group by a2.st_meringegrfisbod, a2.tx_natDoc,  a.co_emp, a.co_loc, a.co_tipdoc, a2.tx_descor, a2.tx_deslar "
                            + " ,a.co_cli, a3.tx_nom, a.co_doc, a.fe_Doc, a4.co_usr,  a4.tx_nom  ";
                }
                if (objZafParSis.getCodigoMenu() == 1974) {
                    strSql = "SELECT a5.st_meringegrfisbod, a5.tx_natDoc, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_Doc , a5.tx_descor, a5.tx_deslar "
                            + " , a.co_clides as co_cli, a.tx_nomclides as tx_nomcli, a.co_ven as co_com, a.tx_nomven, a.co_forret ,a7.tx_deslar as desforret  "
                            + " FROM  tbm_cabguirem as a "
                            + "  INNER JOIN tbm_detguirem AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc) "
                            + "  INNER JOIN tbm_loc AS a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc) "
                            + "  INNER JOIN tbm_bod AS a4 ON (a4.co_emp=a.co_emp AND a4.co_bod=a.co_ptopar ) "
                            + "  INNER JOIN tbm_cabTipDoc AS a5 ON (a5.co_emp=a.co_emp AND a5.co_loc=a.co_loc AND a5.co_tipDoc=a.co_tipDoc) "
                            + "  INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) "
                            + "  LEFT  JOIN tbm_var as a7 on (a7.co_reg=a.co_forret) "
                            + "  WHERE a.co_emp=" + STR_COD_EMP_REL + "  AND a.co_loc=" + STR_COD_LOC_REL + "  AND  a.co_tipdoc=" + txtCodTipDocCon.getText() + " "
                            + "  AND a.co_doc=" + txtCodDocCon.getText() + " "
                            + "  AND ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=" + txtCodBod.getText() + ") "
                            + "  AND a.st_reg NOT IN('I','E') "
                            + "  ORDER BY a.ne_numdoc ";
                }
                System.out.println("Menu>>> : " + objZafParSis.getCodigoMenu());
                System.out.println("ZafCom19.cargarDoc " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    if (verificaTipDoc(rstLoc.getString("tx_natDoc"))) {
                        if (cargarDocCab(conn, rstLoc)) {
                            if (cargarDocDet(conn, rstLoc, rstLoc.getString("tx_natDoc"))) {
                                bloquea(txtNumDocCon, false);
                                blnRes = true;
                            }
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

    private boolean _getVerificarPagCont(java.sql.Connection conn) {
        boolean blnRes = false;
        String strSql = "";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();
                strSql = "select  a1.co_doc from ( "
                        + " select co_emprel, co_locrel, co_tipdocrel, co_docrel  from tbm_detguirem "
                        + " where  co_emp=" + STR_COD_EMP_REL + " and co_loc=" + STR_COD_LOC_REL + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + "  "
                        + " group by co_emprel, co_locrel, co_tipdocrel, co_docrel  "
                        + " ) as a "
                        + " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel) "
                        + " WHERE a1.st_reg in ('A','C') "
                        + " AND (a1.nd_porret=0 or a1.nd_porret is null ) and (a1.ne_diacre=0 or a1.ne_diacre is null)  AND (a1.nd_abo+a1.mo_pag) < 0  ";
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    blnRes = true;
                }
                rstLoc.close();
                rstLoc = null;
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

    private boolean _getVerificarPagChq(java.sql.Connection conn) {
        boolean blnRes = false;
        String strSql = "";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();
                strSql = "select  a1.co_doc from ( "
                        + " select co_emprel, co_locrel, co_tipdocrel, co_docrel  from tbm_detguirem "
                        + " where  co_emp=" + STR_COD_EMP_REL + " and co_loc=" + STR_COD_LOC_REL + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + "  "
                        + " group by co_emprel, co_locrel, co_tipdocrel, co_docrel  "
                        + " ) as a "
                        + " inner join tbm_cabmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel) "
                        + " INNER JOIN tbm_pagMovInv AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc AND a2.co_doc=a1.co_doc) "
                        + " INNER JOIN tbm_cli AS cli ON (cli.co_emp=a1.co_emp and cli.co_cli=a1.co_cli)  "
                        + " WHERE a1.st_reg NOT IN ('I','E') AND a2.st_reg IN ('A','C') AND a2.st_sop='S' "
                        + " AND CASE WHEN (a2.mo_pag+a2.nd_abo) < 0 THEN  a2.st_entsop='N' END  AND a1.fe_doc+cli.ne_diagrachqfec <= " + objZafParSis.getFuncionFechaHoraBaseDatos() + " ";
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    blnRes = true;
                }
                rstLoc.close();
                rstLoc = null;
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

    private boolean verificaTipDoc(String strCodTipDoc) {
        boolean blnRes = false;
        if (strCodTipDoc.equals("A")) {
            strTipModIngEgr = "1";
            blnRes = true;
        } else {
            blnRes = true;
        }
        return blnRes;
    }

    private void bloquea(javax.swing.JTextField txtFiel, boolean blnEst) {
        java.awt.Color colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }
    
    private String strTipMovRel;
    private int intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel;

    private boolean cargarDocCab(java.sql.Connection conn, java.sql.ResultSet rstDat) {
        boolean blnRes = false;
        try {
            if (conn != null) {
                strTipIngEgr = rstDat.getString("tx_natDoc");
                strMerIngEgr = rstDat.getString("st_meringegrfisbod");
                txtCodLocRel.setText(rstDat.getString("co_loc"));
                txtCodTipDocCon.setText(rstDat.getString("co_tipdoc"));
                txtDesCorTipDocCon.setText(rstDat.getString("tx_descor"));
                txtDesLarTipDocCon.setText(rstDat.getString("tx_deslar"));
                txtCodCliPro.setText(rstDat.getString("co_cli"));
                txtNomCliPro.setText(rstDat.getString("tx_nomcli"));
                txtCodVenCom.setText(rstDat.getString("co_com"));
                txtNomVenCom.setText(rstDat.getString("tx_nomven"));
                txtCodForRet.setText(rstDat.getString("co_forret"));
                txtDesForRet.setText(rstDat.getString("desforret"));
                txtFecDocCon.setText(rstDat.getString("fe_Doc"));
                txtCodDocCon.setText(rstDat.getString("co_doc"));
                /*JoseMario 6/Jul/2016 */
                strTipMovRel=rstDat.getString("tx_motMovInvJta"); /*JoseMario 6/Jul/2016  'V' ELSE 'R'*/
                intCodEmpRel=rstDat.getInt("co_empRel");
                intCodLocRel=rstDat.getInt("co_locRel");
                intCodTipDocRel=rstDat.getInt("co_tipDocRel");
                intCodDocRel=rstDat.getInt("co_docRel");
                /*JoseMario 6/Jul/2016 */
                if ((objZafParSis.getCodigoMenu() == 2205)) {
                    txtNumGuiDes.setText(rstDat.getString("ne_numorddes"));
                    strNomBodIng = rstDat.getString("nomBodIng");
                    txtRucCliPro.setText(rstDat.getString("tx_ruccli"));
                    if (STR_COD_BOD_GUIA.equalsIgnoreCase("15")) {
                        lblNumGuiDet.setText("Imprimir en Formulario de: " + (STR_COD_EMP_REL.equalsIgnoreCase("1") ? "** TUVAL" : (STR_COD_EMP_REL.equalsIgnoreCase("2") ? "** CASTEK" : (STR_COD_EMP_REL.equalsIgnoreCase("4") ? "** DIMULTI" : ""))));
                    }
                }
                if ((objZafParSis.getCodigoMenu() == 2915)) {
                    txtNumGuiDes.setText(rstDat.getString("ne_numorddes"));
                    strNomBodIng = rstDat.getString("nomBodIng");
                    txtRucCliPro.setText(rstDat.getString("tx_ruccli"));
                    if (STR_COD_BOD_GUIA.equalsIgnoreCase("15")) {
                        lblNumGuiDet.setText("Imprimir en Formulario de: " + (STR_COD_EMP_REL_COM_CLI_RET.equalsIgnoreCase("1") ? "** TUVAL" : (STR_COD_EMP_REL_COM_CLI_RET.equalsIgnoreCase("2") ? "** CASTEK" : (STR_COD_EMP_REL_COM_CLI_RET.equalsIgnoreCase("4") ? "** DIMULTI" : ""))));
                    }
                }
                blnRes = true;
            }
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    private boolean cargarDocDet(java.sql.Connection conn, java.sql.ResultSet rstDat, String strTipMov) {
        boolean blnRes = false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "", strSqlAux = "";
        Vector vecData, vecDataAux;
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();
                vecDataAux = new Vector();
                vecData = new Vector();
                if (strTipMov.equals("A")) {
                    if (strTipModIngEgr.equals("1")) {
                        strSqlAux = " AND  a.nd_can > 0 ";
                    }
                    if (strTipModIngEgr.equals("2")) {
                        strSqlAux = " AND  a.nd_can < 0 ";
                    }
                }
                if (objZafParSis.getCodigoMenu() == 2205) {
                    if (rstDat.getString("st_tipguirem").equals("P")) {
                        if (rstDat.getString("co_empgrp") == null) {
                            strSql = ""
                                    + " SELECT a.co_itm, a.co_reg, invbod.tx_ubi, invbod.st_impord, inv.tx_codalt2, a.tx_codalt, a.tx_nomitm, a.tx_unimed, " + STR_COD_BOD_GUIA + " as co_bod, abs(a.nd_can) as nd_can "
                                    + " ,a.nd_canNunRec, a.nd_cancon , a.st_meregrfisbod "
                                    + " ,null as co_emprel, null as co_locrel, null as co_tipdocrel, null as co_docrel, null as co_regrel "
                                    + " ,null as codbodegr "
                                    + " ,case when a.st_meregrfisbod='N' then  (abs(a.nd_can)-abs(nd_cantotguisec)) else abs(a.nd_can) end as cangui, inv.nd_pesitmkgr "
                                    + " ,case when st_impord = 'N' then 'N' else case when (abs(a.nd_can) - a.nd_cancon  - a.nd_cannunrec) <= 0 then 'N' else 'S' end end as st_ordconitm "
                                    + " FROM tbm_detguirem AS a   "
                                    + " inner join tbm_inv as inv on(inv.co_emp=a.co_emp and inv.co_itm=a.co_itm) "
                                    + " inner join tbm_invbod as invbod on(invbod.co_emp=a.co_emp and invbod.co_itm=a.co_itm and invbod.co_bod=" + STR_COD_BOD_GUIA + ") "
                                    + " WHERE a.co_emp=" + rstDat.getString("co_emp") + " AND  a.co_loc=" + rstDat.getString("co_loc")
                                    + //" AND a.co_tipdoc="+rstDat.getString("co_tipdoc")+" AND a.co_doc="+rstDat.getString("co_doc")+" AND inv.st_ser='N' ";
                                    " AND a.co_tipdoc=" + rstDat.getString("co_tipdoc") + " AND a.co_doc=" + rstDat.getString("co_doc") + " AND inv.st_ser in ('N','S') order by st_impord, st_ordconitm ";
                        } else {
                            strSql = "SELECT a.co_itm, a.co_reg, invbod.tx_ubi, invbod.st_impord, inv.tx_codalt2, a.tx_codalt, a.tx_nomitm, a.tx_unimed, " + STR_COD_BOD_GUIA + " as co_bod, abs(a.nd_can) as nd_can "
                                    + " ,a.nd_canNunRec, a.nd_cancon , a.st_meregrfisbod ,a2.co_emprel, a2.co_locrel, a2.co_tipdocrel, a2.co_docrel, a2.co_regrel, a.co_regRel as co_regRelDocEgr "/*JoseMario 7/Jul/2016 */
                                    + " ,a5.co_bod as codbodegr "
                                    + " ,case when a.st_meregrfisbod='N' then  (abs(a.nd_can)-abs(nd_cantotguisec)) else abs(a.nd_can) end as cangui, inv.nd_pesitmkgr "
                                    + " ,case when st_impord = 'N' then 'N' else case when (abs(a.nd_can) - a.nd_cancon  - a.nd_cannunrec) <= 0 then 'N' else 'S' end end as st_ordconitm "
                                    + " FROM tbm_detguirem AS a   "
                                    + " inner join tbm_inv as inv on(inv.co_emp=a.co_emp and inv.co_itm=a.co_itm) "
                                    + " inner join tbm_invbod as invbod on(invbod.co_emp=a.co_emp and invbod.co_itm=a.co_itm and invbod.co_bod=" + STR_COD_BOD_GUIA + ") "
                                    + " INNER JOIN tbm_detmovinv as a8 on(a8.co_emp=a.co_emprel and a8.co_loc=a.co_locrel and a8.co_tipdoc=a.co_tipdocrel and a8.co_doc=a.co_docrel and a8.co_reg=a.co_regrel )   "
                                    + " LEFT JOIN tbr_detmovinv as a2 on(a2.co_emp=a.co_emprel and a2.co_loc=a.co_locrel and a2.co_tipdoc=a.co_tipdocrel and a2.co_doc=a.co_docrel and a2.co_reg=a.co_regrel )   "
                                    + " LEFT JOIN tbm_detmovinv as a5 on ( a5.co_emp=a2.co_emprel and a5.co_loc=a2.co_locrel and a5.co_tipdoc=a2.co_tipdocrel and a5.co_doc=a2.co_docrel and a5.co_reg=a2.co_regrel ) "
                                    + " WHERE a.co_emp=" + rstDat.getString("co_emp") + " AND  a.co_loc=" + rstDat.getString("co_loc")
                                    + " AND a.co_tipdoc=" + rstDat.getString("co_tipdoc") + " AND a.co_doc=" + rstDat.getString("co_doc") + " AND inv.st_ser='N' and a8.st_cliretemprel is null order by st_impord, st_ordconitm ";
                        }
                    } else {
                        if (rstDat.getString("co_empgrp") == null) {
                            strSql = "SELECT a.co_itm, a.co_reg, invbod.tx_ubi, invbod.st_impord, inv.tx_codalt2, a.tx_codalt, a.tx_nomitm, a.tx_unimed, " + STR_COD_BOD_GUIA + " as co_bod, abs(a.nd_can) as nd_can "
                                    + " ,a.nd_canNunRec, a.nd_cancon , a.st_meregrfisbod  "
                                    + " ,null as co_emprel, null as co_locrel, null as co_tipdocrel, null as co_docrel, null as co_regrel  "
                                    + " ,null as codbodegr "
                                    + " ,case when a.st_meregrfisbod='N' then  (abs(a.nd_can)-abs(nd_cantotguisec)) else abs(a.nd_can) end as cangui, inv.nd_pesitmkgr "
                                    + " ,case when st_impord = 'N' then 'N' else case when (abs(a.nd_can) - a.nd_cancon  - a.nd_cannunrec) <= 0 then 'N' else 'S' end end as st_ordconitm "
                                    + " FROM tbm_detguirem AS a   "
                                    + " inner join tbm_inv as inv on(inv.co_emp=a.co_emp and inv.co_itm=a.co_itm) "
                                    + " inner join tbm_invbod as invbod on(invbod.co_emp=a.co_emp and invbod.co_itm=a.co_itm and invbod.co_bod=" + STR_COD_BOD_GUIA + ") "
                                    + " WHERE a.co_emp=" + rstDat.getString("co_emp") + " AND  a.co_loc=" + rstDat.getString("co_loc")
                                    + //" AND a.co_tipdoc="+rstDat.getString("co_tipdoc")+" AND a.co_doc="+rstDat.getString("co_doc")+" AND inv.st_ser='N' ";
                                    " AND a.co_tipdoc=" + rstDat.getString("co_tipdoc") + " AND a.co_doc=" + rstDat.getString("co_doc") + " AND inv.st_ser in ('N','S') order by st_impord, st_ordconitm ";
                        } else {
                            strSql = "SELECT a.co_itm, a.co_reg, invbod.tx_ubi, invbod.st_impord, inv.tx_codalt2, a.tx_codalt, a.tx_nomitm, a.tx_unimed, " + STR_COD_BOD_GUIA + " as co_bod, abs(a.nd_can) as nd_can "
                                    + " ,a.nd_canNunRec, a.nd_cancon , a.st_meregrfisbod  ,a4.co_emprel, a4.co_locrel, a4.co_tipdocrel, a4.co_docrel, a4.co_regrel  "
                                    + " ,a5.co_bod as codbodegr "
                                    + " ,case when a.st_meregrfisbod='N' then  (abs(a.nd_can)-abs(a.nd_cantotguisec)) else abs(a.nd_can) end as cangui, inv.nd_pesitmkgr "
                                    + " ,case when st_impord = 'N' then 'N' else case when (abs(a.nd_can) - a.nd_cancon  - a.nd_cannunrec) <= 0 then 'N' else 'S' end end as st_ordconitm "
                                    + " FROM tbm_detguirem AS a   "
                                    + " inner join tbm_inv as inv on(inv.co_emp=a.co_emp and inv.co_itm=a.co_itm) "
                                    + " inner join tbm_invbod as invbod on(invbod.co_emp=a.co_emp and invbod.co_itm=a.co_itm and invbod.co_bod=" + STR_COD_BOD_GUIA + ") "
                                    + " left join tbr_detguirem as a2 on(a2.co_emp=a.co_emp and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc and a2.co_reg=a.co_reg )    "
                                    + " left join tbm_detguirem as a3 on(a3.co_emp=a2.co_emp and a3.co_loc=a2.co_locrel and a3.co_tipdoc=a2.co_tipdocrel and a3.co_doc=a2.co_docrel and a3.co_reg=a2.co_regrel )   "
                                    + " left join tbr_detmovinv as a4 on(a4.co_emp=a3.co_emprel and a4.co_loc=a3.co_locrel and a4.co_tipdoc=a3.co_tipdocrel and a4.co_doc=a3.co_docrel and a4.co_reg=a3.co_regrel )    "
                                    + " LEFT JOIN tbm_detmovinv as a5 on ( a5.co_emp=a4.co_emprel and a5.co_loc=a4.co_locrel and a5.co_tipdoc=a4.co_tipdocrel and a5.co_doc=a4.co_docrel and a5.co_reg=a4.co_regrel ) "
                                    + " WHERE a.co_emp=" + rstDat.getString("co_emp") + " AND  a.co_loc=" + rstDat.getString("co_loc")
                                    + //" AND a.co_tipdoc="+rstDat.getString("co_tipdoc")+" AND a.co_doc="+rstDat.getString("co_doc")+" AND inv.st_ser='N' ";
                                    " AND a.co_tipdoc=" + rstDat.getString("co_tipdoc") + " AND a.co_doc=" + rstDat.getString("co_doc") + " AND inv.st_ser in ('N','S') order by st_impord, st_ordconitm ";
                        }
                    }
                    strSql = " select * from (  " + strSql + " ) as x  where  cangui > 0  ";
                    System.out.println("ZafCom19.cargarDocDet: " + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    while (rstLoc.next()) 
                    {
                        java.util.Vector vecAux = new java.util.Vector();
                        vecAux.add(INT_TBL_LINEA, "");
                        vecAux.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
                        vecAux.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                        vecAux.add(INT_TBL_CODALTDOS, rstLoc.getString("tx_codalt2"));
                        vecAux.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                        vecAux.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                        vecAux.add(INT_TBL_CANMOV, rstLoc.getString("nd_can"));
                        vecAux.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));
                        vecAux.add(INT_TBL_CANCON, "");
                        vecAux.add(INT_TBL_CANNUMREC, "");
                        vecAux.add(INT_TBL_CANMALEST, "");
                        vecAux.add(INT_TBL_CANTOTNUMREC, rstLoc.getString("nd_canNunRec"));
                        vecAux.add(INT_TBL_OBSITMCON, "");
                        vecAux.add(INT_TBL_CANCON_ORI, "");
                        vecAux.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                        vecAux.add(INT_TBL_CODBOD, STR_COD_BOD_GUIA);
                        vecAux.add(INT_TBL_CANNUMREC_ORI, "");
                        vecAux.add(INT_TBL_CANMALEST_ORI, "");
                        vecAux.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meregrfisbod"));
                        vecAux.add(INT_TBL_CANTOTMALEST, "");
                        vecAux.add(INT_TBL_CODEMPREL, rstLoc.getString("co_emprel"));
                        vecAux.add(INT_TBL_CODLOCREL, rstLoc.getString("co_locrel"));
                        vecAux.add(INT_TBL_CODTIDOREL, rstLoc.getString("co_tipdocrel"));
                        vecAux.add(INT_TBL_CODDOCREL, rstLoc.getString("co_docrel"));
                        vecAux.add(INT_TBL_CODREGREL, rstLoc.getString("co_regrel"));
                        vecAux.add(INT_TBL_TXNOMBODREL, null);
                        vecAux.add(INT_TBL_CODBODREL, rstLoc.getString("codbodegr"));
                        vecAux.add(INT_TBL_PESKGR, rstLoc.getString("nd_pesitmkgr"));
                        vecAux.add(INT_TBL_CODALTAUX, rstLoc.getString("tx_codalt"));
                        vecAux.add(INT_TBL_NOMITMAUX, rstLoc.getString("tx_nomitm"));
                        vecAux.add(INT_TBL_CODREGRELDOCEGR, rstLoc.getInt("co_regRelDocEgr"));/* JoseMario 7/Jul/2016 */
                        vecDataAux.add(vecAux);
                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
                        BigDecimal bdcCan = new BigDecimal(rstLoc.getString("nd_can"));
                        BigDecimal bdcCanCon = new BigDecimal(rstLoc.getString("nd_cancon"));
                        if ((bdcCan.compareTo(bdcCanCon) != 0) && rstLoc.getString("st_impord").equals("S")) 
                        {
                            vecReg.add(INT_TBL_CODALT, "");
                        }
                        else 
                        {
                            vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                        }
                        vecReg.add(INT_TBL_CODALTDOS, rstLoc.getString("tx_codalt2")); //Rose 07/Mar/2016
                        
                        if ((bdcCan.compareTo(bdcCanCon) != 0) && rstLoc.getString("st_impord").equals("S")) 
                        {
                            vecReg.add(INT_TBL_NOMITM, null);
                            vecReg.add(INT_TBL_DESUNI, "");
                            vecReg.add(INT_TBL_CANMOV, "");
                            vecReg.add(INT_TBL_CANTOTCON, "");
                        } 
                        else 
                        {
                            vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                            vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                            vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_can"));
                            vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));
                        }
                        vecReg.add(INT_TBL_CANCON, "");
                        vecReg.add(INT_TBL_CANNUMREC, "");
                        vecReg.add(INT_TBL_CANMALEST, "");
                        vecReg.add(INT_TBL_CANTOTNUMREC, rstLoc.getString("nd_canNunRec"));
                        vecReg.add(INT_TBL_OBSITMCON, "");
                        vecReg.add(INT_TBL_CANCON_ORI, "");
                        if ((bdcCan.compareTo(bdcCanCon) != 0) && rstLoc.getString("st_impord").equals("S")) 
                        {
                            vecReg.add(INT_TBL_CODREG, "");
                        }
                        else 
                        {
                            vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                        }
                        vecReg.add(INT_TBL_CODBOD, STR_COD_BOD_GUIA);
                        vecReg.add(INT_TBL_CANNUMREC_ORI, "");
                        vecReg.add(INT_TBL_CANMALEST_ORI, "");
                        if ((bdcCan.compareTo(bdcCanCon) != 0) && rstLoc.getString("st_impord").equals("S")) 
                        {
                            vecReg.add(INT_TBL_IEBODFIS, null);
                        } 
                        else 
                        {
                            vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meregrfisbod"));
                        }
                        vecReg.add(INT_TBL_CANTOTMALEST, "");
                        vecReg.add(INT_TBL_CODEMPREL, rstLoc.getString("co_emprel"));
                        vecReg.add(INT_TBL_CODLOCREL, rstLoc.getString("co_locrel"));
                        vecReg.add(INT_TBL_CODTIDOREL, rstLoc.getString("co_tipdocrel"));
                        vecReg.add(INT_TBL_CODDOCREL, rstLoc.getString("co_docrel"));
                        vecReg.add(INT_TBL_CODREGREL, rstLoc.getString("co_regrel"));
                        vecReg.add(INT_TBL_TXNOMBODREL, null);
                        vecReg.add(INT_TBL_CODBODREL, rstLoc.getString("codbodegr"));
                        vecReg.add(INT_TBL_PESKGR, rstLoc.getString("nd_pesitmkgr"));
                        vecReg.add(INT_TBL_CODALTAUX, rstLoc.getString("tx_codalt"));
                        vecReg.add(INT_TBL_NOMITMAUX, rstLoc.getString("tx_nomitm"));
                        vecReg.add(INT_TBL_CODREGRELDOCEGR, rstLoc.getInt("co_regRelDocEgr"));/* JoseMario 7/Jul/2016 */
                        vecData.add(vecReg);
                    }
                    objTblModAux.setData(vecDataAux);
                    objTblMod.setData(vecData);
                    tblDat.setModel(objTblMod);
                    rstLoc.close();
                    rstLoc = null;
                }
                if (objZafParSis.getCodigoMenu() == 2915) {
                    if (rstDat.getString("st_tipguirem").equals("P")) {
                        if (rstDat.getString("co_empgrp") == null) {
                            strSql = ""
                                    + " SELECT a.co_itm, a.co_reg, invbod.tx_ubi, invbod.st_impord, inv.tx_codalt2, a.tx_codalt, a.tx_nomitm, a.tx_unimed, " + STR_COD_BOD_GUIA + " as co_bod, abs(a.nd_can) as nd_can "
                                    + " ,a.nd_canNunRec, a.nd_cancon , a.st_meregrfisbod "
                                    + " ,null as co_emprel, null as co_locrel, null as co_tipdocrel, null as co_docrel, null as co_regrel "
                                    + " ,null as codbodegr "
                                    + " ,case when a.st_meregrfisbod='N' then  (abs(a.nd_can)-abs(nd_cantotguisec)) else abs(a.nd_can) end as cangui, inv.nd_pesitmkgr "
                                    + " ,case when st_impord = 'N' then 'N' else case when (abs(a.nd_can) - a.nd_cancon  - a.nd_cannunrec) <= 0 then 'N' else 'S' end end as st_ordconitm "
                                    + " FROM tbm_detguirem AS a   "
                                    + " inner join tbm_inv as inv on(inv.co_emp=a.co_emp and inv.co_itm=a.co_itm) "
                                    + " inner join tbm_invbod as invbod on(invbod.co_emp=a.co_emp and invbod.co_itm=a.co_itm and invbod.co_bod=" + STR_COD_BOD_GUIA + ") "
                                    + " WHERE a.co_emp=" + rstDat.getString("co_emp") + " AND  a.co_loc=" + rstDat.getString("co_loc")
                                    + " AND a.co_tipdoc=" + rstDat.getString("co_tipdoc") + " AND a.co_doc=" + rstDat.getString("co_doc") + " AND inv.st_ser='N' order by st_impord, st_ordconitm ";
                        } else {
                            strSql = "SELECT a.co_itm, a.co_reg, invbod.tx_ubi, invbod.st_impord, inv.tx_codalt2, a.tx_codalt, a.tx_nomitm, a.tx_unimed, " + STR_COD_BOD_GUIA + " as co_bod, abs(a.nd_can) as nd_can "
                                    + " ,a.nd_canNunRec, a.nd_cancon , a.st_meregrfisbod ,a2.co_emprel, a2.co_locrel, a2.co_tipdocrel, a2.co_docrel, a2.co_regrel, a.co_regRel as co_regRelDocEgr " /* JoseMario 7/Jul/2016 */
                                    + " ,a5.co_bod as codbodegr "
                                    + " ,case when a.st_meregrfisbod='N' then  (abs(a.nd_can)-abs(nd_cantotguisec)) else abs(a.nd_can) end as cangui, inv.nd_pesitmkgr "
                                    + " ,case when st_impord = 'N' then 'N' else case when (abs(a.nd_can) - a.nd_cancon  - a.nd_cannunrec) <= 0 then 'N' else 'S' end end as st_ordconitm "
                                    + " FROM tbm_detguirem AS a   "
                                    + " inner join tbm_inv as inv on(inv.co_emp=a.co_emp and inv.co_itm=a.co_itm) "
                                    + " inner join tbm_invbod as invbod on(invbod.co_emp=a.co_emp and invbod.co_itm=a.co_itm and invbod.co_bod=" + STR_COD_BOD_GUIA + ") "
                                    + " INNER JOIN tbm_detmovinv as a8 on(a8.co_emp=a.co_emprel and a8.co_loc=a.co_locrel and a8.co_tipdoc=a.co_tipdocrel and a8.co_doc=a.co_docrel and a8.co_reg=a.co_regrel )   "
                                    + " LEFT JOIN tbr_detmovinv as a2 on(a2.co_emp=a.co_emprel and a2.co_loc=a.co_locrel and a2.co_tipdoc=a.co_tipdocrel and a2.co_doc=a.co_docrel and a2.co_reg=a.co_regrel )   "
                                    + " LEFT JOIN tbm_detmovinv as a5 on ( a5.co_emp=a2.co_emprel and a5.co_loc=a2.co_locrel and a5.co_tipdoc=a2.co_tipdocrel and a5.co_doc=a2.co_docrel and a5.co_reg=a2.co_regrel ) "
                                    + " WHERE a.co_emp=" + rstDat.getString("co_emp") + " AND  a.co_loc=" + rstDat.getString("co_loc")
                                    + " AND a.co_tipdoc=" + rstDat.getString("co_tipdoc") + " AND a.co_doc=" + rstDat.getString("co_doc") + " AND inv.st_ser='N' and a8.st_cliretemprel = 'S' order by st_impord, st_ordconitm ";
                        }
                    } else {
                        if (rstDat.getString("co_empgrp") == null) {
                            strSql = "SELECT a.co_itm, a.co_reg, invbod.tx_ubi, invbod.st_impord, inv.tx_codalt2, a.tx_codalt, a.tx_nomitm, a.tx_unimed, " + STR_COD_BOD_GUIA + " as co_bod, abs(a.nd_can) as nd_can "
                                    + " ,a.nd_canNunRec, a.nd_cancon , a.st_meregrfisbod  "
                                    + " ,null as co_emprel, null as co_locrel, null as co_tipdocrel, null as co_docrel, null as co_regrel  "
                                    + " ,null as codbodegr "
                                    + " ,case when a.st_meregrfisbod='N' then  (abs(a.nd_can)-abs(nd_cantotguisec)) else abs(a.nd_can) end as cangui, inv.nd_pesitmkgr "
                                    + " ,case when st_impord = 'N' then 'N' else case when (abs(a.nd_can) - a.nd_cancon  - a.nd_cannunrec) <= 0 then 'N' else 'S' end end as st_ordconitm "
                                    + " FROM tbm_detguirem AS a   "
                                    + " inner join tbm_inv as inv on(inv.co_emp=a.co_emp and inv.co_itm=a.co_itm) "
                                    + " inner join tbm_invbod as invbod on(invbod.co_emp=a.co_emp and invbod.co_itm=a.co_itm and invbod.co_bod=" + STR_COD_BOD_GUIA + ") "
                                    + " WHERE a.co_emp=" + rstDat.getString("co_emp") + " AND  a.co_loc=" + rstDat.getString("co_loc")
                                    + " AND a.co_tipdoc=" + rstDat.getString("co_tipdoc") + " AND a.co_doc=" + rstDat.getString("co_doc") + " AND inv.st_ser='N' order by st_impord, st_ordconitm ";
                        } else {
                            strSql = "SELECT a.co_itm, a.co_reg, invbod.tx_ubi, invbod.st_impord, inv.tx_codalt2, a.tx_codalt, a.tx_nomitm, a.tx_unimed, " + STR_COD_BOD_GUIA + " as co_bod, abs(a.nd_can) as nd_can "
                                    + " ,a.nd_canNunRec, a.nd_cancon , a.st_meregrfisbod  ,a4.co_emprel, a4.co_locrel, a4.co_tipdocrel, a4.co_docrel, a4.co_regrel  "
                                    + " ,a5.co_bod as codbodegr "
                                    + " ,case when a.st_meregrfisbod='N' then  (abs(a.nd_can)-abs(a.nd_cantotguisec)) else abs(a.nd_can) end as cangui, inv.nd_pesitmkgr "
                                    + " ,case when st_impord = 'N' then 'N' else case when (abs(a.nd_can) - a.nd_cancon  - a.nd_cannunrec) <= 0 then 'N' else 'S' end end as st_ordconitm "
                                    + " FROM tbm_detguirem AS a   "
                                    + " inner join tbm_inv as inv on(inv.co_emp=a.co_emp and inv.co_itm=a.co_itm) "
                                    + " inner join tbm_invbod as invbod on(invbod.co_emp=a.co_emp and invbod.co_itm=a.co_itm and invbod.co_bod=" + STR_COD_BOD_GUIA + ") "
                                    + " left join tbr_detguirem as a2 on(a2.co_emp=a.co_emp and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc and a2.co_reg=a.co_reg )    "
                                    + " left join tbm_detguirem as a3 on(a3.co_emp=a2.co_emp and a3.co_loc=a2.co_locrel and a3.co_tipdoc=a2.co_tipdocrel and a3.co_doc=a2.co_docrel and a3.co_reg=a2.co_regrel )   "
                                    + " left join tbr_detmovinv as a4 on(a4.co_emp=a3.co_emprel and a4.co_loc=a3.co_locrel and a4.co_tipdoc=a3.co_tipdocrel and a4.co_doc=a3.co_docrel and a4.co_reg=a3.co_regrel )    "
                                    + " LEFT JOIN tbm_detmovinv as a5 on ( a5.co_emp=a4.co_emprel and a5.co_loc=a4.co_locrel and a5.co_tipdoc=a4.co_tipdocrel and a5.co_doc=a4.co_docrel and a5.co_reg=a4.co_regrel ) "
                                    + " WHERE a.co_emp=" + rstDat.getString("co_emp") + " AND  a.co_loc=" + rstDat.getString("co_loc")
                                    + " AND a.co_tipdoc=" + rstDat.getString("co_tipdoc") + " AND a.co_doc=" + rstDat.getString("co_doc") + " AND inv.st_ser='N' order by st_impord, st_ordconitm ";
                        }
                    }
                    strSql = " select * from (  " + strSql + " ) as x  where  cangui > 0  ";
                    System.out.println("ZafCom19.cargarDocDet2: " + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    while (rstLoc.next()) 
                    {
                        java.util.Vector vecAux = new java.util.Vector();
                        vecAux.add(INT_TBL_LINEA, "");
                        vecAux.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
                        vecAux.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                        vecAux.add(INT_TBL_CODALTDOS, rstLoc.getString("tx_codalt2"));
                        vecAux.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                        vecAux.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                        vecAux.add(INT_TBL_CANMOV, rstLoc.getString("nd_can"));
                        vecAux.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));
                        vecAux.add(INT_TBL_CANCON, "");
                        vecAux.add(INT_TBL_CANNUMREC, "");
                        vecAux.add(INT_TBL_CANMALEST, "");
                        vecAux.add(INT_TBL_CANTOTNUMREC, rstLoc.getString("nd_canNunRec"));
                        vecAux.add(INT_TBL_OBSITMCON, "");
                        vecAux.add(INT_TBL_CANCON_ORI, "");
                        vecAux.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                        vecAux.add(INT_TBL_CODBOD, STR_COD_BOD_GUIA);
                        vecAux.add(INT_TBL_CANNUMREC_ORI, "");
                        vecAux.add(INT_TBL_CANMALEST_ORI, "");
                        vecAux.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meregrfisbod"));
                        vecAux.add(INT_TBL_CANTOTMALEST, "");
                        vecAux.add(INT_TBL_CODEMPREL, rstLoc.getString("co_emprel"));
                        vecAux.add(INT_TBL_CODLOCREL, rstLoc.getString("co_locrel"));
                        vecAux.add(INT_TBL_CODTIDOREL, rstLoc.getString("co_tipdocrel"));
                        vecAux.add(INT_TBL_CODDOCREL, rstLoc.getString("co_docrel"));
                        vecAux.add(INT_TBL_CODREGREL, rstLoc.getString("co_regrel"));
                        vecAux.add(INT_TBL_TXNOMBODREL, null);
                        vecAux.add(INT_TBL_CODBODREL, rstLoc.getString("codbodegr"));
                        vecAux.add(INT_TBL_PESKGR, rstLoc.getString("nd_pesitmkgr"));
                        vecAux.add(INT_TBL_CODALTAUX, rstLoc.getString("tx_codalt"));
                        vecAux.add(INT_TBL_NOMITMAUX, rstLoc.getString("tx_nomitm"));
                        vecAux.add(INT_TBL_CODREGRELDOCEGR, rstLoc.getInt("co_regRelDocEgr"));/* JoseMario 7/Jul/2016 */
                        vecDataAux.add(vecAux);
                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
                        BigDecimal bdcCan = new BigDecimal(rstLoc.getString("nd_can"));
                        BigDecimal bdcCanCon = new BigDecimal(rstLoc.getString("nd_cancon"));
                        if ((bdcCan.compareTo(bdcCanCon) != 0) && rstLoc.getString("st_impord").equals("S")) {
                            vecReg.add(INT_TBL_CODALT, "");
                        } else {
                            vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                        }
                        vecReg.add(INT_TBL_CODALTDOS, rstLoc.getString("tx_codalt2")); //Rose 07/Mar/2016
                        
                        if ((bdcCan.compareTo(bdcCanCon) != 0) && rstLoc.getString("st_impord").equals("S")) {
                            vecReg.add(INT_TBL_NOMITM, null);
                            vecReg.add(INT_TBL_DESUNI, "");
                            vecReg.add(INT_TBL_CANMOV, "");
                            vecReg.add(INT_TBL_CANTOTCON, "");
                        } else {
                            vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                            vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                            vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_can"));
                            vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));
                        }
                        
                        vecReg.add(INT_TBL_CANCON, "");
                        vecReg.add(INT_TBL_CANNUMREC, "");
                        vecReg.add(INT_TBL_CANMALEST, "");
                        vecReg.add(INT_TBL_CANTOTNUMREC, rstLoc.getString("nd_canNunRec"));
                        vecReg.add(INT_TBL_OBSITMCON, "");
                        vecReg.add(INT_TBL_CANCON_ORI, "");
                        if ((bdcCan.compareTo(bdcCanCon) != 0) && rstLoc.getString("st_impord").equals("S")) {
                            vecReg.add(INT_TBL_CODREG, "");
                        } else {
                            vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                        }
                        vecReg.add(INT_TBL_CODBOD, STR_COD_BOD_GUIA);
                        vecReg.add(INT_TBL_CANNUMREC_ORI, "");
                        vecReg.add(INT_TBL_CANMALEST_ORI, "");
                        if ((bdcCan.compareTo(bdcCanCon) != 0) && rstLoc.getString("st_impord").equals("S")) {
                            vecReg.add(INT_TBL_IEBODFIS, null);
                        } else {
                            vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meregrfisbod"));
                        }
                        vecReg.add(INT_TBL_CANTOTMALEST, "");
                        vecReg.add(INT_TBL_CODEMPREL, rstLoc.getString("co_emprel"));
                        vecReg.add(INT_TBL_CODLOCREL, rstLoc.getString("co_locrel"));
                        vecReg.add(INT_TBL_CODTIDOREL, rstLoc.getString("co_tipdocrel"));
                        vecReg.add(INT_TBL_CODDOCREL, rstLoc.getString("co_docrel"));
                        vecReg.add(INT_TBL_CODREGREL, rstLoc.getString("co_regrel"));
                        vecReg.add(INT_TBL_TXNOMBODREL, null);
                        vecReg.add(INT_TBL_CODBODREL, rstLoc.getString("codbodegr"));
                        vecReg.add(INT_TBL_PESKGR, rstLoc.getString("nd_pesitmkgr"));
                        vecReg.add(INT_TBL_CODALTAUX, rstLoc.getString("tx_codalt"));
                        vecReg.add(INT_TBL_NOMITMAUX, rstLoc.getString("tx_nomitm"));
                        vecReg.add(INT_TBL_CODREGRELDOCEGR, rstLoc.getInt("co_regRelDocEgr"));/* JoseMario 7/Jul/2016 */
                        vecData.add(vecReg);
                    }
                    objTblModAux.setData(vecDataAux);
                    objTblMod.setData(vecData);
                    tblDat.setModel(objTblMod);
                    rstLoc.close();
                    rstLoc = null;
                }
                if (objZafParSis.getCodigoMenu() == 286)  //Rose 07/Mar/2016
                {
                    strSql = "SELECT  a.nd_canTotMalEst, a.co_itmact, a.co_reg, a.tx_codalt, inv.tx_codalt2, a.tx_nomitm, a.tx_unimed, a.co_bod,  abs(a.nd_can) as nd_can "
                            + "       ,a.nd_canNunRec, a.nd_cancon , a.st_meringegrfisbod "
                            + "       , a4.co_emp as coempegr, a4.co_loc as colocegr, a4.co_tipdoc as tipdocegr, a4.co_doc as codocegr, a4.co_reg as coregegr, a6.co_bod as codbodegr, a6.tx_nom as nombodegr "
                            + " FROM tbm_detmovinv AS a   "
                            + " INNER JOIN tbm_inv as inv on(inv.co_emp=a.co_emp and inv.co_itm=a.co_itmact) "
                            + " INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a3.co_emp=a.co_emp AND a3.co_bod=a.co_bod)   "
                            + " LEFT JOIN tbr_detmovinv as a4 on ( a4.co_emp!=a.co_emp and   a4.co_emprel=a.co_emp and a4.co_locrel=a.co_loc and a4.co_tipdocrel=a.co_tipdoc and a4.co_docrel=a.co_doc and a4.co_regrel=a.co_reg ) "
                            + " LEFT JOIN tbm_detmovinv as a5 on ( a5.co_emp=a4.co_emp and a5.co_loc=a4.co_loc and a5.co_tipdoc=a4.co_tipdoc and a5.co_doc=a4.co_doc and a5.co_reg=a4.co_reg ) "
                            + " LEFT JOIN tbm_bod as a6 on ( a6.co_emp=a5.co_emp and a6.co_bod=a5.co_bod ) "
                            + " LEFT JOIN  tbm_cabmovinv as a8 on (a8.co_emp=a5.co_emp and a8.co_loc=a5.co_loc and a8.co_tipdoc=a5.co_tipdoc and a8.co_doc=a5.co_doc )  "
                            + " LEFT JOIN  tbm_cli as a9 on (a9.co_emp=a8.co_emp and a9.co_cli=a8.co_cli  and  a9.co_empgrp =0 )  "
                            + " WHERE a.co_emp=" + rstDat.getString("co_emp") + " AND  a.co_loc=" + rstDat.getString("co_loc") + "  "
                            + "   AND  ( a3.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a3.co_bodGrp=" + txtCodBod.getText() + " )       "
                            + "   AND a.co_tipdoc=" + rstDat.getString("co_tipdoc") + " AND a.co_doc=" + rstDat.getString("co_doc") + " AND inv.st_ser='N' AND  a.nd_can > 0 ";
                    rstLoc = stmLoc.executeQuery(strSql);
                    System.out.println("Rose.cargarDocDet : "+strSql); 
                    while (rstLoc.next()) 
                    {
                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itmact"));
                        vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                        //vecReg.add(INT_TBL_CODALTDOS, null);//Antes Rose 07/Mar/2016
                        vecReg.add(INT_TBL_CODALTDOS, rstLoc.getString("tx_codalt2"));  //Rose 07/Mar/2016
                        vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                        vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                        vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_can"));
                        vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));
                        if (blnEstCarConfIns) 
                        {
                            if (rstLoc.getString("co_reg").equalsIgnoreCase(STR_COD_REG_REL)) 
                            {
                                vecReg.add(INT_TBL_CANCON, STR_CAN_CON);
                            } 
                            else
                            {
                                vecReg.add(INT_TBL_CANCON, "");
                            }
                        }
                        else
                        {
                            vecReg.add(INT_TBL_CANCON, "");
                        }
                        vecReg.add(INT_TBL_CANNUMREC, "");
                        vecReg.add(INT_TBL_CANMALEST, "");
                        vecReg.add(INT_TBL_CANTOTNUMREC, rstLoc.getString("nd_canNunRec"));
                        vecReg.add(INT_TBL_OBSITMCON, "");
                        vecReg.add(INT_TBL_CANCON_ORI, "");
                        vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                        vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                        vecReg.add(INT_TBL_CANNUMREC_ORI, "");
                        vecReg.add(INT_TBL_CANMALEST_ORI, "");
                        vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meringegrfisbod"));
                        vecReg.add(INT_TBL_CANTOTMALEST, rstLoc.getString("nd_canTotMalEst"));
                        vecReg.add(INT_TBL_CODEMPREL, rstLoc.getString("coempegr"));
                        vecReg.add(INT_TBL_CODLOCREL, rstLoc.getString("colocegr"));
                        vecReg.add(INT_TBL_CODTIDOREL, rstLoc.getString("tipdocegr"));
                        vecReg.add(INT_TBL_CODDOCREL, rstLoc.getString("codocegr"));
                        vecReg.add(INT_TBL_CODREGREL, rstLoc.getString("coregegr"));
                        vecReg.add(INT_TBL_TXNOMBODREL, rstLoc.getString("nombodegr"));
                        vecReg.add(INT_TBL_CODBODREL, rstLoc.getString("codbodegr"));
                        vecReg.add(INT_TBL_PESKGR, null);
                        vecReg.add(INT_TBL_CODALTAUX, null);
                        vecReg.add(INT_TBL_NOMITMAUX, null);
                        vecReg.add(INT_TBL_CODREGRELDOCEGR, null);/* JoseMario 7/Jul/2016 */
                        if (blnEstCarConfIns) {
                            if (rstLoc.getString("co_reg").equalsIgnoreCase(STR_COD_REG_REL)) {
                                vecData.add(vecReg);
                            }
                        } else {
                            vecData.add(vecReg);
                        }
                    }
                    objTblMod.setData(vecData);
                    tblDat.setModel(objTblMod);
                    rstLoc.close();
                    rstLoc = null;
                }
                if (objZafParSis.getCodigoMenu() == 1974) {
                    strSql = "SELECT a.co_itm, a.co_reg, a.tx_codalt, a.tx_nomitm, a.tx_unimed, " + STR_COD_BOD_GUIA + " as co_bod, abs(a.nd_can) as nd_can "
                            + "  ,a.nd_canNunRec, a.nd_cancon , a.st_meregrfisbod  "
                            + " FROM tbm_detguirem AS a   "
                            + " inner join tbm_inv as inv on(inv.co_emp=a.co_emp and inv.co_itm=a.co_itm) "
                            + " WHERE a.co_emp=" + rstDat.getString("co_emp") + " AND  a.co_loc=" + rstDat.getString("co_loc") + "  "
                            + " AND a.co_tipdoc=" + rstDat.getString("co_tipdoc") + " AND a.co_doc=" + rstDat.getString("co_doc") + " AND inv.st_ser='N' ";
                    System.out.println("ZafCom19.cargarDocDet3: " + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    while (rstLoc.next()) {
                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
                        vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                        vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                        vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                        vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_can"));
                        vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));
                        vecReg.add(INT_TBL_CANCON, "");
                        vecReg.add(INT_TBL_CANNUMREC, "");
                        vecReg.add(INT_TBL_CANMALEST, "");
                        vecReg.add(INT_TBL_CANTOTNUMREC, "");
                        vecReg.add(INT_TBL_OBSITMCON, "");
                        vecReg.add(INT_TBL_CANCON_ORI, "");
                        vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                        vecReg.add(INT_TBL_CODBOD, STR_COD_BOD_GUIA);
                        vecReg.add(INT_TBL_CANNUMREC_ORI, "");
                        vecReg.add(INT_TBL_CANMALEST_ORI, "");
                        vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meregrfisbod"));
                        vecReg.add(INT_TBL_CANTOTMALEST, "");
                        vecReg.add(INT_TBL_CODEMPREL, null);
                        vecReg.add(INT_TBL_CODLOCREL, null);
                        vecReg.add(INT_TBL_CODTIDOREL, null);
                        vecReg.add(INT_TBL_CODDOCREL, null);
                        vecReg.add(INT_TBL_CODREGREL, null);
                        vecReg.add(INT_TBL_TXNOMBODREL, null);
                        vecReg.add(INT_TBL_CODBODREL, null);
                        vecData.add(vecReg);
                    }
                    objTblMod.setData(vecData);
                    tblDat.setModel(objTblMod);
                    rstLoc.close();
                    rstLoc = null;
                }
                if (objZafParSis.getCodigoMenu() == 2063) {
                    strSql = "SELECT a.co_itm as co_itmact , a.co_reg, inv.tx_codalt, inv.tx_nomitm, var.tx_descor as tx_unimed, a.co_bod, "
                            + " abs(a.nd_can) as nd_can, a.nd_cantotegr, a.nd_cannunegr  , null as st_meringegrfisbod "
                            + " FROM tbm_detsolsaltemmer AS a   "
                            + " inner join tbm_inv as inv on(inv.co_emp=a.co_emp and inv.co_itm=a.co_itm) "
                            + " inner join tbm_var  as var ON(var.co_reg=inv.co_uni)"
                            + " WHERE a.co_emp=" + rstDat.getString("co_emp") + " AND  a.co_loc=" + rstDat.getString("co_loc") + " "
                            + " AND a.co_tipdoc=" + rstDat.getString("co_tipdoc") + " AND a.co_doc=" + rstDat.getString("co_doc") + " AND inv.st_ser='N'"
                            + " AND a.co_bod=" + txtCodBod.getText() + " ORDER BY a.co_reg ";
                    System.out.println("ZafCom19.cargarDocDet4: " + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    while (rstLoc.next()) {
                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itmact"));
                        vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                        vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                        vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                        vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_can"));
                        vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cantotegr"));
                        vecReg.add(INT_TBL_CANCON, "");
                        vecReg.add(INT_TBL_CANNUMREC, rstLoc.getString("nd_cannunegr"));
                        vecReg.add(INT_TBL_CANMALEST, "");
                        vecReg.add(INT_TBL_CANTOTNUMREC, "");
                        vecReg.add(INT_TBL_OBSITMCON, "");
                        vecReg.add(INT_TBL_CANCON_ORI, "");
                        vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                        vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                        vecReg.add(INT_TBL_CANNUMREC_ORI, "");
                        vecReg.add(INT_TBL_CANMALEST_ORI, "");
                        vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meringegrfisbod"));
                        vecReg.add(INT_TBL_CANTOTMALEST, "");
                        vecReg.add(INT_TBL_CODEMPREL, null);
                        vecReg.add(INT_TBL_CODLOCREL, null);
                        vecReg.add(INT_TBL_CODTIDOREL, null);
                        vecReg.add(INT_TBL_CODDOCREL, null);
                        vecReg.add(INT_TBL_CODREGREL, null);
                        vecReg.add(INT_TBL_TXNOMBODREL, null);
                        vecReg.add(INT_TBL_CODBODREL, null);
                        vecData.add(vecReg);
                    }
                    objTblMod.setData(vecData);
                    tblDat.setModel(objTblMod);
                    rstLoc.close();
                    rstLoc = null;
                }
                if (objZafParSis.getCodigoMenu() == 2073) {
                    strSql = "SELECT a.co_itm as co_itmact , a.co_reg, inv.tx_codalt, inv.tx_nomitm, var.tx_descor as tx_unimed, a.co_bod, "
                            + " abs(a.nd_cantotegr) as nd_can, a.nd_cantoting as nd_cancon, a.nd_cannuning, null as st_meringegrfisbod "
                            + " FROM tbm_detsolsaltemmer AS a   "
                            + " inner join tbm_inv as inv on(inv.co_emp=a.co_emp and inv.co_itm=a.co_itm) "
                            + " inner join tbm_var  as var ON(var.co_reg=inv.co_uni)"
                            + " WHERE a.co_emp=" + rstDat.getString("co_emp") + " AND  a.co_loc=" + rstDat.getString("co_loc") + " "
                            + " AND a.co_tipdoc=" + rstDat.getString("co_tipdoc") + " AND a.co_doc=" + rstDat.getString("co_doc") + " AND inv.st_ser='N'"
                            + " AND a.co_bod=" + txtCodBod.getText() + " ORDER BY a.co_reg ";
                    System.out.println("ZafCom19.cargarDocDet5: " + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    while (rstLoc.next()) {
                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itmact"));
                        vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                        vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                        vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                        vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_can"));
                        vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));
                        vecReg.add(INT_TBL_CANCON, "");
                        vecReg.add(INT_TBL_CANNUMREC, rstLoc.getString("nd_cannuning"));
                        vecReg.add(INT_TBL_CANMALEST, "");
                        vecReg.add(INT_TBL_CANTOTNUMREC, "");
                        vecReg.add(INT_TBL_OBSITMCON, "");
                        vecReg.add(INT_TBL_CANCON_ORI, "");
                        vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                        vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                        vecReg.add(INT_TBL_CANNUMREC_ORI, "");
                        vecReg.add(INT_TBL_CANMALEST_ORI, "");
                        vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meringegrfisbod"));
                        vecReg.add(INT_TBL_CANTOTMALEST, "");
                        vecReg.add(INT_TBL_CODEMPREL, null);
                        vecReg.add(INT_TBL_CODLOCREL, null);
                        vecReg.add(INT_TBL_CODTIDOREL, null);
                        vecReg.add(INT_TBL_CODDOCREL, null);
                        vecReg.add(INT_TBL_CODREGREL, null);
                        vecReg.add(INT_TBL_TXNOMBODREL, null);
                        vecReg.add(INT_TBL_CODBODREL, null);
                        vecData.add(vecReg);
                    }
                    objTblMod.setData(vecData);
                    tblDat.setModel(objTblMod);
                    rstLoc.close();
                    rstLoc = null;
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

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        @Override
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            if (objZafParSis.getCodigoMenu() == 286) {
                switch (intCol) {
                    case INT_TBL_LINEA:
                        strMsg = "";
                        break;
                    case INT_TBL_CODITM:
                        strMsg = "Código del Item";
                        break;
                    case INT_TBL_CODALT:
                        strMsg = "Código alterno del ítem";
                        break;
                    case INT_TBL_CODALTDOS:
                        strMsg = "Código en letras del ítem";
                        break;
                    case INT_TBL_NOMITM:
                        strMsg = "Nombre del ítem";
                        break;
                    case INT_TBL_DESUNI:
                        strMsg = "Unidad de medida";
                        break;
                    case INT_TBL_CANMOV:
                        strMsg = "Cantidad";
                        break;
                    case INT_TBL_CANTOTCON:
                        strMsg = "Cantidad total confirmada";
                        break;
                    case INT_TBL_CANCON:
                        strMsg = "Cantidad a Confirmar.";
                        break;
                    case INT_TBL_CANNUMREC:
                        strMsg = "Cantidad nunca recibida  ";
                        break;
                    case INT_TBL_CANMALEST:
                        strMsg = "Cantidad en mal estado. ";
                        break;
                    case INT_TBL_OBSITMCON:
                        strMsg = "Observación sobre el ítem a confirmar";
                        break;
                    case INT_TBL_CANTOTNUMREC:
                        strMsg = "Cantidad total nunca recibida.  ";
                        break;
                    case INT_TBL_CANTOTMALEST:
                        strMsg = "Cantidad total en mal estado.  ";
                        break;
                }
            }
            if (objZafParSis.getCodigoMenu() == 1974) {
                switch (intCol) {
                    case INT_TBL_LINEA:
                        strMsg = "";
                        break;
                    case INT_TBL_CODITM:
                        strMsg = "Código del Item";
                        break;
                    case INT_TBL_CODALT:
                        strMsg = "Código alterno del ítem";
                        break;
                    case INT_TBL_NOMITM:
                        strMsg = "Nombre del ítem";
                        break;
                    case INT_TBL_DESUNI:
                        strMsg = "Unidad de medida";
                        break;
                    case INT_TBL_CANMOV:
                        strMsg = "Cantidad";
                        break;
                    case INT_TBL_CANTOTCON:
                        strMsg = "Cantidad total confirmada";
                        break;
                    case INT_TBL_CANCON:
                        strMsg = "Cantidad a Ingreso Privicional.";
                        break;
                    case INT_TBL_CANNUMREC:
                        strMsg = "Cantidad nunca Enviada  ";
                        break;
                    case INT_TBL_OBSITMCON:
                        strMsg = "Observación sobre el ítem a Ingreso Privicional ";
                        break;
                }
            }
            if (objZafParSis.getCodigoMenu() == 2205) {
                switch (intCol) {
                    case INT_TBL_LINEA:
                        strMsg = "";
                        break;
                    case INT_TBL_CODITM:
                        strMsg = "Código del Item";
                        break;
                    case INT_TBL_CODALT:
                        strMsg = "Código alterno del ítem";
                        break;
                    case INT_TBL_NOMITM:
                        strMsg = "Nombre del ítem";
                        break;
                    case INT_TBL_DESUNI:
                        strMsg = "Unidad de medida";
                        break;
                    case INT_TBL_CANMOV:
                        strMsg = "Cantidad";
                        break;
                    case INT_TBL_CANTOTCON:
                        strMsg = "Cantidad total confirmada";
                        break;
                    case INT_TBL_CANCON:
                        strMsg = "Cantidad a Confirmar el egreso.";
                        break;
                    case INT_TBL_CANNUMREC:
                        strMsg = "Cantidad nunca enviada  ";
                        break;
                    case INT_TBL_OBSITMCON:
                        strMsg = "Observación  ";
                        break;
                    case INT_TBL_CANTOTNUMREC:
                        strMsg = "Cantidad total nunca enviada.  ";
                        break;
                }
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanDatDsp;
    private javax.swing.JPanel PanTabGen;
    private javax.swing.ButtonGroup bgrRet;
    private javax.swing.ButtonGroup bgrTra;
    private javax.swing.JButton butBusBod;
    private javax.swing.JButton butBusCodDoc;
    private javax.swing.JButton butBusNumDocCon;
    private javax.swing.JButton butCho;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton butVeh;
    private javax.swing.JLabel lblCho;
    private javax.swing.JLabel lblCodDocCon1;
    private javax.swing.JLabel lblComPro;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecDocCon;
    private javax.swing.JLabel lblForRet1;
    private javax.swing.JLabel lblIde;
    private javax.swing.JLabel lblMotTra;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblNumDocCon1;
    private javax.swing.JLabel lblNumGuiDes;
    private javax.swing.JLabel lblNumGuiDet;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPla;
    private javax.swing.JLabel lblRso;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTipDoc1;
    private javax.swing.JLabel lblTipDoc2;
    private javax.swing.JLabel lblTipdoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVeh;
    private javax.swing.JLabel lblVenCom;
    private javax.swing.JRadioButton optEnv;
    private javax.swing.JRadioButton optRet;
    private javax.swing.JRadioButton optVehCli;
    private javax.swing.JRadioButton optVehTra;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panDatConf;
    private javax.swing.JPanel panDatDsp;
    private javax.swing.JPanel panLbl;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTblCabDat;
    private javax.swing.JPanel panTblDetDoc;
    private javax.swing.JPanel panTit;
    private javax.swing.JPanel panTxa;
    private javax.swing.JScrollPane scrTblDat;
    private javax.swing.JScrollPane spnObs3;
    private javax.swing.JScrollPane spnObs4;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodCliPro;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodDocCon;
    private javax.swing.JTextField txtCodForRet;
    private javax.swing.JTextField txtCodVenCom;
    private javax.swing.JTextField txtDesCodTitpDoc;
    private javax.swing.JTextField txtDesCorTipDocCon;
    private javax.swing.JTextField txtDesForRet;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDesLarTipDocCon;
    private javax.swing.JTextField txtDesLarVeh;
    private javax.swing.JTextField txtFecDocCon;
    private javax.swing.JTextField txtIdeCho;
    private javax.swing.JTextField txtIdeTra;
    private javax.swing.JTextField txtMotTra;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNomCho;
    private javax.swing.JTextField txtNomCliPro;
    private javax.swing.JTextField txtNomTra;
    private javax.swing.JTextField txtNomVenCom;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtNumDocCon;
    private javax.swing.JTextField txtNumGuiDes;
    private javax.swing.JTextArea txtObs1;
    private javax.swing.JTextArea txtObs2;
    private javax.swing.JTextField txtPlaVeh;
    private javax.swing.JTextField txtPlaVehTra;
    // End of variables declaration//GEN-END:variables

    private void MensajeInf(String strMensaje) {
        JOptionPane.showMessageDialog(this, strMensaje, strTit, JOptionPane.INFORMATION_MESSAGE);
    }

    public class mitoolbar extends ZafToolBar {

        javax.swing.JInternalFrame jfrThis2;

        public mitoolbar(javax.swing.JInternalFrame jfrThis) {
            super(jfrThis, objZafParSis);
            jfrThis2 = jfrThis;
        }

        @Override
        public boolean anular() {
            boolean blnRes = false;
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

                    if (!blnRes) {
                        if (verificaIngMer(conn, "ANULADO")) {
                            if (verificaCanMalPro(conn, "ANULADO")) {
                                if (verificaSiHayCanNunEgr()) {
                                    if (anularReg(conn)) {
                                        if (conRemGlo != null) {
                                            conRemGlo.commit();
                                        }
                                        conn.commit();
                                        blnRes = true;
                                        objTooBar.setEstadoRegistro("Anulado");
                                        blnHayCam = false;
                                    } else {
                                        conn.rollback();
                                    }
                                } else {
                                    conn.rollback();
                                }
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
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean invertirTrans(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            int intCodBod = 0;

            if (Integer.parseInt(txtCodTipDocCon.getText()) == 148) {
                if (objZafParSis.getCodigoEmpresa() == 1) {
                    intCodBod = 5;
                }
                if (objZafParSis.getCodigoEmpresa() == 2) {
                    intCodBod = 7;
                }
                if (objZafParSis.getCodigoEmpresa() == 4) {
                    intCodBod = 5;
                }
            }

            if (Integer.parseInt(txtCodTipDocCon.getText()) == 149) {
                if (objZafParSis.getCodigoEmpresa() == 1) {
                    intCodBod = 6;
                }
                if (objZafParSis.getCodigoEmpresa() == 2) {
                    intCodBod = 8;
                }
                if (objZafParSis.getCodigoEmpresa() == 4) {
                    intCodBod = 6;
                }
            }

            String strSQL = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSQL = "select co_emp, co_itm, co_bod, abs(nd_can) as nd_can from tbm_detmovinv "
                            + " where co_emp=" + STRCODEMPTRANS + " and co_loc=" + STRCODLOCTRANS + " and co_tipdoc=" + STRCODTIPTRANS + " and co_doc=" + STRCODDOCTRANS + " and nd_can < 0";
                    rstLoc = stmLoc.executeQuery(strSQL);
                    strSQL = "";
                    while (rstLoc.next()) {

                        strSQL += " UPDATE tbm_invbod SET nd_stkact=nd_stkact+" + rstLoc.getString("nd_can") + " "
                                + " ,nd_canegrbod=( CASE WHEN nd_canegrbod IS NULL THEN 0 ELSE nd_canegrbod END )-" + rstLoc.getString("nd_can") + " "
                                + " WHERE co_emp=" + rstLoc.getString("co_emp") + " "
                                + " and co_bod=" + rstLoc.getString("co_bod") + " and co_itm=" + rstLoc.getString("co_itm") + " ; ";

                        strSQL += " UPDATE tbm_invbod SET nd_stkact=nd_stkact-" + rstLoc.getString("nd_can") + " "
                                + " ,nd_caningbod=( CASE WHEN nd_caningbod IS NULL THEN 0 ELSE nd_caningbod END )-" + rstLoc.getString("nd_can") + " "
                                + " WHERE co_emp=" + rstLoc.getString("co_emp") + " "
                                + " and co_bod=" + intCodBod + " and co_itm=" + rstLoc.getString("co_itm") + " ; ";
                    }
                    rstLoc.close();
                    rstLoc = null;

                    if (conRemGlo != null) {
                        java.sql.Statement stmloc = conRemGlo.createStatement();
                        stmloc.executeUpdate(strSQL);
                        stmloc.close();
                        stmloc = null;
                    }

                    strSQL += " UPDATE tbm_cabmovinv SET st_reg='I', fe_ultmod=" + objZafParSis.getFuncionFechaHoraBaseDatos() + ", co_usring=" + objZafParSis.getCodigoUsuario() + " "
                            + " WHERE co_emp=" + STRCODEMPTRANS + " and co_loc=" + STRCODLOCTRANS + " and co_tipdoc=" + STRCODTIPTRANS + " and co_doc=" + STRCODDOCTRANS + " ; ";

                    strSQL += "UPDATE tbm_cabdia SET st_reg='I', fe_ultmod=" + objZafParSis.getFuncionFechaHoraBaseDatos() + ", co_usring=" + objZafParSis.getCodigoUsuario() + " "
                            + " WHERE co_emp=" + STRCODEMPTRANS + " and co_loc=" + STRCODLOCTRANS + " and co_tipdoc=" + STRCODTIPTRANS + " and co_dia=" + STRCODDOCTRANS + " ; ";
                    stmLoc.executeUpdate(strSQL);
                    stmLoc.close();
                    stmLoc = null;

                    blnRes = true;

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

        private boolean invertirTrans_2(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            int intCodBod = 0;

            if (Integer.parseInt(txtCodTipDocCon.getText()) == 148) {
                if (objZafParSis.getCodigoEmpresa() == 1) {
                    intCodBod = 5;
                }
                if (objZafParSis.getCodigoEmpresa() == 2) {
                    intCodBod = 7;
                }
                if (objZafParSis.getCodigoEmpresa() == 4) {
                    intCodBod = 5;
                }
            }

            if (Integer.parseInt(txtCodTipDocCon.getText()) == 149) {
                if (objZafParSis.getCodigoEmpresa() == 1) {
                    intCodBod = 6;
                }
                if (objZafParSis.getCodigoEmpresa() == 2) {
                    intCodBod = 8;
                }
                if (objZafParSis.getCodigoEmpresa() == 4) {
                    intCodBod = 6;
                }
            }

            String strSQL = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSQL = "select co_emp, co_itm, co_bod, abs(nd_can) as nd_can from tbm_detmovinv "
                            + " where co_emp=" + STRCODEMPTRANS + " and co_loc=" + STRCODLOCTRANS + " and co_tipdoc=" + STRCODTIPTRANS + " and co_doc=" + STRCODDOCTRANS + " and nd_can > 0";
                    rstLoc = stmLoc.executeQuery(strSQL);
                    strSQL = "";
                    while (rstLoc.next()) {
                        strSQL += " UPDATE tbm_invbod SET nd_stkact=nd_stkact+" + rstLoc.getString("nd_can") + " "
                                + " ,nd_canegrbod=( CASE WHEN nd_canegrbod IS NULL THEN 0 ELSE nd_canegrbod END )-" + rstLoc.getString("nd_can") + " "
                                + " WHERE co_emp=" + rstLoc.getString("co_emp") + " "
                                + " and co_bod=" + intCodBod + " and co_itm=" + rstLoc.getString("co_itm") + " ; ";

                        strSQL += " UPDATE tbm_invbod SET nd_stkact=nd_stkact-" + rstLoc.getString("nd_can") + " "
                                + " ,nd_caningbod=( CASE WHEN nd_caningbod IS NULL THEN 0 ELSE nd_caningbod END )-" + rstLoc.getString("nd_can") + " "
                                + " WHERE co_emp=" + rstLoc.getString("co_emp") + " "
                                + " and co_bod=" + rstLoc.getString("co_bod") + " and co_itm=" + rstLoc.getString("co_itm") + " ; ";
                    }
                    rstLoc.close();
                    rstLoc = null;

                    if (conRemGlo != null) {
                        java.sql.Statement stmloc = conRemGlo.createStatement();
                        stmloc.executeUpdate(strSQL);
                        stmloc.close();
                        stmloc = null;
                    }

                    strSQL += " UPDATE tbm_cabmovinv SET st_reg='I', fe_ultmod=" + objZafParSis.getFuncionFechaHoraBaseDatos() + ", co_usring=" + objZafParSis.getCodigoUsuario() + " "
                            + " WHERE co_emp=" + STRCODEMPTRANS + " and co_loc=" + STRCODLOCTRANS + " and co_tipdoc=" + STRCODTIPTRANS + " and co_doc=" + STRCODDOCTRANS + " ; ";

                    strSQL += "UPDATE tbm_cabdia SET st_reg='I', fe_ultmod=" + objZafParSis.getFuncionFechaHoraBaseDatos() + ", co_usring=" + objZafParSis.getCodigoUsuario() + " "
                            + " WHERE co_emp=" + STRCODEMPTRANS + " and co_loc=" + STRCODLOCTRANS + " and co_tipdoc=" + STRCODTIPTRANS + " and co_dia=" + STRCODDOCTRANS + " ; ";
                    stmLoc.executeUpdate(strSQL);
                    stmLoc.close();
                    stmLoc = null;

                    blnRes = true;

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

        private boolean verificaCanMalPro(java.sql.Connection conn, String strEst) {
            boolean blnRes = true;
            java.sql.ResultSet rstLoc;
            java.sql.Statement stmLoc;
            String strSql = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    if (objZafParSis.getCodigoMenu() == 286) {
                        strSql = "select co_doc  from tbm_detingegrmerbod WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " "
                                + " AND  co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText() + " and nd_canmalest  > 0 "
                                + " and  ( nd_canmalestaut > 0  or nd_canmalestden > 0  )  ";
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
                            MensajeInf("NO SE PUEDE " + strEst + " PORQUE EXISTE MESCADERIA EN MAL ESTADO PROCESADO.");
                            blnRes = false;
                        }
                        rstLoc.close();
                        rstLoc = null;
                    }
                    stmLoc.close();
                    stmLoc = null;

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

        private boolean verificaIngMer(java.sql.Connection conn, String strEst) {
            boolean blnRes = true;
            java.sql.ResultSet rstLoc;
            java.sql.Statement stmLoc;
            String strSql = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    if (objZafParSis.getCodigoMenu() == 2063) {
                        strSql = "select * from ( "
                                + " select a.st_contotmeregr, a.st_contotmering, sum(a1.nd_cantotegr) as canegr, sum(a1.nd_cantoting+a1.nd_cannuning) as caning "
                                + " from tbm_cabsolsaltemmer as a "
                                + " inner join tbm_detsolsaltemmer as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) "
                                + " where a.co_emp=" + objZafParSis.getCodigoEmpresa() + " and a.co_loc=" + txtCodLocRel.getText() + " and a.co_tipdoc=" + txtCodTipDocCon.getText() + " "
                                + " and a.co_doc=" + txtCodDocCon.getText() + " "
                                + "  group by  a.st_contotmeregr, a.st_contotmering "
                                + " ) as x where x.caning > 0 ";
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
                            MensajeInf("NO SE PUEDE " + strEst + " PORQUE EXISTE MESCADERIA YA INGRESADA O FACTURADA.");
                            blnRes = false;
                        }
                        rstLoc.close();
                        rstLoc = null;
                    }
                    stmLoc.close();
                    stmLoc = null;

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

        private boolean verificaSiHayCanNunEgr() {
            boolean blnRes = true;
            double dblCanNumRec = 0;
            int intCelSel = 0;
            if (objZafParSis.getCodigoMenu() == 2063) {
                for (int i = 0; i < tblDat.getRowCount(); i++) {
                    intCelSel = i;
                    dblCanNumRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI).toString()), 2);
                }
                if (dblCanNumRec > 0) {
                    blnRes = mensajeConfir("LA CANTIDAD NUNCA RECIBIDA SERA BORRADO \n DESEA CONTINUAR DE TODAS FORMA.? ");
                }
            }
            return blnRes;
        }

        private boolean mensajeConfir(String strMsg2) {
            boolean blnres = false;
            //JOptionPane oppMsg2=new JOptionPane();
            String strTit2 = "Mensaje del sistema Zafiro";
            if (!(JOptionPane.showConfirmDialog(jfrThis, strMsg2, strTit2, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 1)) {
                blnres = true;  // SI
            } else {
                blnres = false;  // NO 
            }
            return blnres;
        }

        private boolean anularReg(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSql = "";
            int intTipAnuEgr = 0;
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "UPDATE tbm_cabingegrmerbod SET st_reg='I', co_usrMod=" + objZafParSis.getCodigoUsuario() + ", fe_ultmod=" + objZafParSis.getFuncionFechaHoraBaseDatos() + " "
                            + " WHERE co_emp=" + STR_COD_EMP_REL + " AND co_loc=" + STR_COD_LOC_REL + " "
                            + "AND  co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText();
                    stmLoc.executeUpdate(strSql);

                    if (objZafParSis.getCodigoMenu() == 2205) {

                        intTipAnuEgr = _getVerEsqAnuReg(conn, Integer.parseInt(STR_COD_EMP_REL), Integer.parseInt(STR_COD_LOC_REL), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                        if (intTipAnuEgr == 2) {
                            if (anularDetConfEgrEsqAnt(conn)) {
                                if (actEstConfEgr(conn, Integer.parseInt(STR_COD_EMP_REL), Integer.parseInt(STR_COD_LOC_REL), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), false)) {
                                    blnRes = true;
                                }
                            }
                        }
                        if (intTipAnuEgr == 1) {
                            if (anularDetConfEgrEsqNue(conn, Integer.parseInt(STR_COD_EMP_REL), Integer.parseInt(STR_COD_LOC_REL), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()))) {
                                blnRes = true;
                            }
                        }
                    }
                    if (objZafParSis.getCodigoMenu() == 2915) {

                        if (anularDetConfEgrEsqNue(conn, Integer.parseInt(STR_COD_EMP_REL), Integer.parseInt(STR_COD_LOC_REL), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()))) {
                            blnRes = true;
                        }
                    }
                    if (objZafParSis.getCodigoMenu() == 286) {
                        if (anularDetConfIng(conn)) {
                            blnRes = true;
                        }
                    }

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

        private boolean anularDetConfEgrEsqNue(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc) {
            boolean blnRes = false;
            java.sql.Statement stmLoc, stmLoc01, stmLoc02;
            java.sql.ResultSet rstLoc, rstLoc01;
            String strSql = "", strAuxSql = "";
            double dlbCanConf = 0;
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    stmLoc01 = conn.createStatement();
                    stmLoc02 = conn.createStatement();

                    strSql = "select * from ( "
                            + " select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc, case when a1.ne_numorddes is null then 0 else a1.ne_numorddes end  as nodrdes "
                            + " ,case when a3.ne_numorddes is null then 0 else a3.ne_numorddes end as  nodrdespri, a3.ne_numdoc as numguipri "
                            + " ,a3.co_emp as coempguipri, a3.co_loc as colocguipri, a3.co_tipdoc as cotipdocguipri, a3.co_doc as codocguipri, a.co_bod "
                            + " from tbm_cabingegrmerbod as a "
                            + " inner join tbm_cabguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelguirem and a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem ) "
                            + " inner join tbr_cabguirem as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc )  "
                            + " inner join tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_locrel and a3.co_tipdoc=a2.co_tipdocrel and a3.co_doc=a2.co_docrel )  "
                            + " where a.co_emp=" + intCodEmp + " and a.co_loc=" + intCodLoc + " and a.co_tipdoc=" + intCodTipDoc + " and a.co_doc= " + intCodDoc + " "
                            + " ) as x "
                            + " where  nodrdes = 0 and   ne_numdoc > 0   "
                            + " and  nodrdespri > 0 and   numguipri = 0  ";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {

                        strSql = "select a1.co_reg as coregguisec, abs(a1.nd_can) as canconf, a3.nd_cancon, a3.nd_can, a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a3.co_reg, a3.st_meregrfisbod "
                                + " ,a3.co_itm "
                                + " From  tbm_detguirem as a1 "
                                + " inner join tbr_detguirem as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc and a2.co_reg=a1.co_reg ) "
                                + " inner join tbm_detguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_locrel and a3.co_tipdoc=a2.co_tipdocrel and a3.co_doc=a2.co_docrel and a3.co_reg=a2.co_regrel )  "
                                + " where a1.co_emp=" + rstLoc.getInt("co_emp") + " and a1.co_loc=" + rstLoc.getInt("co_loc") + " and  a1.co_tipdoc=" + rstLoc.getInt("co_tipdoc") + " and a1.co_doc= " + rstLoc.getInt("co_doc") + " ";
                        rstLoc01 = stmLoc01.executeQuery(strSql);
                        while (rstLoc01.next()) {
                            strAuxSql = " ";
                            dlbCanConf = rstLoc01.getDouble("canconf");

                            if (rstLoc01.getString("st_meregrfisbod").equals("S")) {
                                strSql = " UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod+" + dlbCanConf + "  WHERE  co_emp=" + rstLoc01.getInt("co_emp") + " and co_bod=" + rstLoc.getInt("co_bod") + " and co_itm=" + rstLoc01.getInt("co_itm");
                                stmLoc02.executeUpdate(strSql);
                                strAuxSql = " nd_canCon=nd_canCon-" + dlbCanConf + ", ";
                            }

                            strSql = " UPDATE tbm_detguirem SET  " + strAuxSql + " nd_cantotguisec=nd_cantotguisec-" + dlbCanConf + " "
                                    + " WHERE  co_emp=" + rstLoc01.getInt("co_emp") + " and co_loc=" + rstLoc01.getInt("co_loc") + " "
                                    + " and co_tipdoc=" + rstLoc01.getInt("co_tipdoc") + " and co_doc=" + rstLoc01.getInt("co_doc") + " "
                                    + " and co_reg=" + rstLoc01.getInt("co_reg");
                            stmLoc02.executeUpdate(strSql);
                        }
                        rstLoc01.close();
                        rstLoc01 = null;

                        strSql = "UPDATE tbm_cabguirem SET st_reg='I', tx_obs2='Anulado por anulacion de confirmacion de egreso.'  "
                                + " WHERE co_emp=" + rstLoc.getInt("co_emp") + " and co_loc=" + rstLoc.getInt("co_loc") + " and  co_tipdoc=" + rstLoc.getInt("co_tipdoc") + " and co_doc= " + rstLoc.getInt("co_doc") + " ";
                        stmLoc02.executeUpdate(strSql);

                        strSql = " select *,  case when cantotguisec > 0 then 'S' else 'N' end as sttieguisec "
                                + " ,case when cantotconf <= 0 then 'P' else case when cantotconf < can then 'E' else case when cantotconf = can then 'C' end end end as  estconf "
                                + " from ( "
                                + " select sum(nd_Can) as can, sum(nd_cantotguisec) as cantotguisec,  sum(nd_cancon + nd_cannunrec ) as cantotconf "
                                + " from  tbm_detguirem where co_emp=" + rstLoc.getInt("coempguipri") + " and co_loc=" + rstLoc.getInt("colocguipri") + " "
                                + " and  co_tipdoc=" + rstLoc.getInt("cotipdocguipri") + "  and co_doc=" + rstLoc.getInt("codocguipri") + " "
                                + " ) as x ";
                        rstLoc01 = stmLoc01.executeQuery(strSql);
                        while (rstLoc01.next()) {

                            strSql = " update tbm_cabguirem set st_tieguisec='" + rstLoc01.getString("sttieguisec") + "', st_coninv='" + rstLoc01.getString("estconf") + "' "
                                    + " where co_emp=" + rstLoc.getInt("coempguipri") + " and co_loc=" + rstLoc.getInt("colocguipri") + " and  co_tipdoc=" + rstLoc.getInt("cotipdocguipri") + " "
                                    + " and co_doc= " + rstLoc.getInt("codocguipri") + " ";
                            stmLoc02.executeUpdate(strSql);
                            blnRes = true;

                        }
                        rstLoc01.close();
                        rstLoc01 = null;

                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                    stmLoc01.close();
                    stmLoc01 = null;
                    stmLoc02.close();
                    stmLoc02 = null;

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

        private int _getVerEsqAnuReg(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc) {
            int intRes = 0;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "select * from ( "
                            + " select a.co_emp, a1.co_loc, a1.co_tipdoc, co_docrelguirem, a1.ne_numdoc, case when a1.ne_numorddes is null then 0 else a1.ne_numorddes end as  nodrdes "
                            + " ,case when a3.ne_numorddes is null then 0 else a3.ne_numorddes end  as nodrdespri, a3.ne_numdoc as numguipri "
                            + " from tbm_cabingegrmerbod as a "
                            + " inner join tbm_cabguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelguirem and a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem ) "
                            + " inner join tbr_cabguirem as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc )  "
                            + " inner join tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_locrel and a3.co_tipdoc=a2.co_tipdocrel and a3.co_doc=a2.co_docrel )  "
                            + " where a.co_emp=" + intCodEmp + " and a.co_loc=" + intCodLoc + " and a.co_tipdoc=" + intCodTipDoc + " and a.co_doc= " + intCodDoc + " "
                            + " ) as x "
                            + " where  nodrdes = 0 and   ne_numdoc > 0   "
                            + " and  nodrdespri > 0 and   numguipri = 0  ";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        intRes = 1; // Tiene Congf. con nuevo esquema
                    } else {
                        intRes = 2; // Tiene Congf. con esquema anterior
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;

                }
            } catch (java.sql.SQLException e) {
                intRes = 0;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                intRes = 0;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return intRes;
        }

        private boolean anularDetConfEgrEsqAnt(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.PreparedStatement ps;
            String strSql = "";
            String strEstIngEgrFisBod = "";
            double dblCanNumRecOri = 0;
            double dlbCanConOri = 0;
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    for (int i = 0; i < tblDat.getRowCount(); i++) {

                        strEstIngEgrFisBod = objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_IEBODFIS));
                        dlbCanConOri = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANCON_ORI)), 2);
                        dblCanNumRecOri = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANNUMREC_ORI)), 2);

                        if (strEstIngEgrFisBod.equals("S")) {
                            strSql = " UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod+" + dlbCanConOri + "  WHERE  co_emp=" + STR_COD_EMP_REL + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                            stmLoc.executeUpdate(strSql);
                        }

                        /*strSql=" UPDATE tbm_detguirem SET nd_canNunRec= case when nd_canNunRec is null then 0 else nd_canNunRec end -"+dblCanNumRecOri+" , nd_canCon=nd_canCon-"+dlbCanConOri+" "+
                         " WHERE  co_emp="+STR_COD_EMP_REL+" and co_loc="+txtCodLocRel.getText()+" " +
                         " and co_tipdoc="+txtCodTipDocCon.getText()+" and co_doc="+txtCodDocCon.getText()+" "+
                         " and co_reg="+tblDat.getValueAt(i, INT_TBL_CODREG).toString();*/
                        //stmLoc.executeUpdate(strSql);
                        strSql = " UPDATE tbm_detguirem "
                                + " SET nd_canNunRec= case when nd_canNunRec is null then 0 else nd_canNunRec end - ? , "
                                + " nd_canCon=nd_canCon- ? "
                                + " WHERE  co_emp=" + STR_COD_EMP_REL
                                + " and co_loc=" + txtCodLocRel.getText()
                                + " and co_tipdoc=" + txtCodTipDocCon.getText()
                                + " and co_doc=" + txtCodDocCon.getText()
                                + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                        if (dblCanNumRecOri < 0) {
                            dblCanNumRecOri = dblCanNumRecOri * -1;
                        }
                        if (dlbCanConOri < 0) {
                            dlbCanConOri = dlbCanConOri * -1;
                        }

                        ps = conn.prepareStatement(strSql);
                        //ps.setDouble(1, (dblCanNumRecOri * -1));
                        //ps.setDouble(2, (dlbCanConOri * -1));
                        ps.setDouble(1, dblCanNumRecOri);
                        ps.setDouble(2, dlbCanConOri);
                        ps.executeUpdate();
                        ps.close();
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

        private boolean anularDetConfIng(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLoc, stmLoc01;
            java.sql.ResultSet rstLoc;
            String strSql = "", strSqlFic = "";
            double dlbCanRecOri = 0;
            double dblCanMalEstOri = 0;
            double dblCanNumRec = 0;
            String strEstFisBod = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    stmLoc01 = conn.createStatement();

                    for (int i = 0; i < tblDat.getRowCount(); i++) {

                        strEstFisBod = objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_IEBODFIS));
                        dlbCanRecOri = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANCON_ORI)), 2);
                        dblCanNumRec = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANNUMREC_ORI)), 2);
                        dblCanMalEstOri = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANMALEST_ORI)), 2);

                        if (strEstFisBod.equals("S")) {

                            strSqlFic = " UPDATE tbm_invbod SET nd_caningbod=nd_caningbod+" + dlbCanRecOri + "   WHERE  co_emp=" + STR_COD_EMP_REL + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                            stmLoc.executeUpdate(strSqlFic);
                        }

                        strSql = " UPDATE tbm_detmovinv SET st_regrep='M', co_usrCon=" + objZafParSis.getCodigoUsuario() + " "
                                + " ,tx_obs1='' "
                                + " ,nd_canNunRec= case when nd_canNunRec is null then 0 else nd_canNunRec end - " + dblCanNumRec + " , nd_canCon=nd_canCon-" + dlbCanRecOri + " "
                                + " ,nd_cantotmalest=nd_cantotmalest-" + dblCanMalEstOri + " "
                                + " WHERE  co_emp=" + STR_COD_EMP_REL + " and co_loc=" + txtCodLocRel.getText() + " "
                                + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                        stmLoc.executeUpdate(strSql);

                    }

                    strSql = "SELECT  *,  case when canTotConf  = 0 then 'P'  "
                            + "  else     case when nd_can = canTotConf then 'C' else 'E' end  end as estConf  "
                            + " from (  "
                            + "  SELECT sum(abs(nd_can)) as nd_can,  "
                            + " ( sum(case when nd_cancon is null then 0 else nd_cancon end ) +  "
                            + "   sum(case when nd_cannunrec is null then 0 else nd_cannunrec end )  +   "
                            + "   sum(case when  nd_canTotMalEst is null then 0 else nd_canTotMalEst end  ) ) as canTotConf "
                            + " FROM tbm_detmovinv  "
                            + " WHERE co_emp=" + STR_COD_EMP_REL + "  AND  co_loc=" + txtCodLocRel.getText() + " "
                            + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                            + " and st_meringegrfisbod = 'S' and nd_can > 0 "
                            + " ) as x ";
                    rstLoc = stmLoc.executeQuery(strSql);

                    if (rstLoc.next()) {
                        strSql = " UPDATE tbm_cabmovinv SET st_coninv='" + rstLoc.getString("estConf") + "' "
                                + " WHERE co_emp=" + STR_COD_EMP_REL + "  AND  co_loc=" + txtCodLocRel.getText() + " "
                                + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();
                        stmLoc01.executeUpdate(strSql);
                    }
                    rstLoc.close();
                    rstLoc = null;

                    stmLoc.close();
                    stmLoc = null;
                    stmLoc01.close();
                    stmLoc01 = null;
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

        private boolean anularReg_respaldo(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.Statement stm_remota;
            String strSql = "", strSqlFic = "";
            int intCelSel = 0;
            double dlbCanRecOri = 0;

            double dblCanMalEstOri = 0;
            double dblCanNumRec = 0;
            double dblCan = 0;
            double dblCanConf = 0;
            double dblCanTot = 0;
            String strEstCon = "E";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    strSql = "UPDATE tbm_cabingegrmerbod SET st_reg='I' WHERE co_emp=" + STR_COD_EMP_REL + " AND co_loc=" + STR_COD_LOC_REL + " "
                            + "AND  co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText();
                    stmLoc.executeUpdate(strSql);

                    if (objZafParSis.getCodigoMenu() == 2063) {

                        if (objUltDocPrint.verificarsiesconfirmado(conn, STRCODEMPTRANS, STRCODLOCTRANS, STRCODTIPTRANS, STRCODDOCTRANS)) {

                            invertirTrans(conn);

                            for (int i = 0; i < tblDat.getRowCount(); i++) {
                                intCelSel = i;

                                String strEstFisBod = (tblDat.getValueAt(i, INT_TBL_IEBODFIS) == null ? "" : tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());

                                dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString()), 2);
                                dblCan = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()), 2);
                                dblCanNumRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI).toString()), 2);

                                dblCanConf += dlbCanRecOri;
                                dblCanTot += dblCan;

                                strSql = " UPDATE tbm_detsolsaltemmer SET nd_cantotegr=nd_cantotegr-" + dlbCanRecOri + ", nd_cannunegr=0  "
                                        + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + txtCodLocRel.getText() + " "
                                        + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                        + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                                stmLoc.executeUpdate(strSql);

                            }

                            String strCreDocEgr = "N", strConTotMerEgr = "N", strExiMerSinEgr = "N";

                            strSql = " UPDATE tbm_cabsolsaltemmer SET st_CreDocEgr='" + strCreDocEgr + "' ,st_ConTotMerEgr='" + strConTotMerEgr + "' "
                                    + " ,st_ExiMerSinEgr='" + strExiMerSinEgr + "' WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + "  AND  co_loc=" + txtCodLocRel.getText() + " "
                                    + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();
                            stmLoc.executeUpdate(strSql);

                        } else {
                            return false;
                        }

                    } else if (objZafParSis.getCodigoMenu() == 2073) {

                        invertirTrans_2(conn);

                        for (int i = 0; i < tblDat.getRowCount(); i++) {
                            intCelSel = i;

                            String strEstFisBod = (tblDat.getValueAt(i, INT_TBL_IEBODFIS) == null ? "" : tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());

                            dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString()), 2);
                            dblCan = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()), 2);
                            dblCanNumRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI).toString()), 2);

                            dblCanConf += dlbCanRecOri;
                            dblCanTot += dblCan;

                            strSql = " UPDATE tbm_detsolsaltemmer SET nd_cantoting=nd_cantoting-" + dlbCanRecOri + "  "
                                    + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + txtCodLocRel.getText() + " "
                                    + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                    + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                            stmLoc.executeUpdate(strSql);

                        }

                        String strCreDocEgr = "N", strConTotMerEgr = "N", strExiMerSinEgr = "N";

                        strSql = " UPDATE tbm_cabsolsaltemmer SET st_CreDocIng='" + strCreDocEgr + "' ,st_ConTotMerIng='" + strConTotMerEgr + "' "
                                + " ,st_ExiMerSinIng='" + strExiMerSinEgr + "' WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + "  AND  co_loc=" + txtCodLocRel.getText() + " "
                                + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();
                        stmLoc.executeUpdate(strSql);

                    } else {

                        for (int i = 0; i < tblDat.getRowCount(); i++) {
                            intCelSel = i;

                            String strEstFisBod = (tblDat.getValueAt(i, INT_TBL_IEBODFIS) == null ? "" : tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());

                            if (objZafParSis.getCodigoMenu() == 2205) {
                                dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString()), 2);
                                dblCan = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()), 2);
                                dblCanNumRec = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI)), 2);

                                if (strEstFisBod.equals("S")) {
                                    if (strTipIngEgr.equals("A")) {

                                        String strSqlAux = "";
                                        if (strTipModIngEgr.equals("1")) {
                                            strSqlAux = " nd_caningbod=nd_caningbod+" + dlbCanRecOri;
                                        }
                                        if (strTipModIngEgr.equals("2")) {
                                            strSqlAux = " nd_canegrbod=nd_canegrbod+" + dlbCanRecOri;
                                        }

                                        strSqlFic = " UPDATE tbm_invbod SET " + strSqlAux + "  WHERE  co_emp=" + STR_COD_EMP_REL + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }

                                    if (strTipIngEgr.equals("I")) {
                                        strSqlFic = " UPDATE tbm_invbod SET nd_caningbod=nd_caningbod+" + dlbCanRecOri + "   WHERE  co_emp=" + STR_COD_EMP_REL + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }
                                    if (strTipIngEgr.equals("E")) {
                                        strSqlFic = " UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod+" + dlbCanRecOri + "  WHERE  co_emp=" + STR_COD_EMP_REL + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }
                                }
                                strSql = " UPDATE tbm_detguirem SET st_regrep='M', co_usrCon=" + objZafParSis.getCodigoUsuario() + " "
                                        + " ,tx_obscon=' ' "
                                        + " ,nd_canNunRec= case when nd_canNunRec is null then 0 else nd_canNunRec end  - " + dblCanNumRec + " , nd_canCon=nd_canCon-" + dlbCanRecOri + " "
                                        + " WHERE  co_emp=" + STR_COD_EMP_REL + " and co_loc=" + txtCodLocRel.getText() + " "
                                        + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                        + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                                stmLoc.executeUpdate(strSql);
                            }

                            if (objZafParSis.getCodigoMenu() == 286) {
                                dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString()), 2);
                                dblCan = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()), 2);
                                dblCanNumRec = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI)), 2);
                                dblCanMalEstOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST_ORI).toString()), 2);

                                if (strEstFisBod.equals("S")) {
                                    if (strTipIngEgr.equals("A")) {

                                        String strSqlAux = "";
                                        if (strTipModIngEgr.equals("1")) {
                                            strSqlAux = " nd_caningbod=nd_caningbod+" + dlbCanRecOri;
                                        }
                                        if (strTipModIngEgr.equals("2")) {
                                            strSqlAux = " nd_canegrbod=nd_canegrbod+" + dlbCanRecOri;
                                        }

                                        strSqlFic = " UPDATE tbm_invbod SET " + strSqlAux + "  WHERE  co_emp=" + STR_COD_EMP_REL + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }

                                    if (strTipIngEgr.equals("I")) {
                                        strSqlFic = " UPDATE tbm_invbod SET nd_caningbod=nd_caningbod+" + dlbCanRecOri + "   WHERE  co_emp=" + STR_COD_EMP_REL + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }
                                    if (strTipIngEgr.equals("E")) {
                                        strSqlFic = " UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod+" + dlbCanRecOri + "  WHERE  co_emp=" + STR_COD_EMP_REL + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }
                                }

                                strSql = " UPDATE tbm_detmovinv SET st_regrep='M', co_usrCon=" + objZafParSis.getCodigoUsuario() + " "
                                        + " ,tx_obs1=' ' "
                                        + " ,nd_canNunRec= case when nd_canNunRec is null then 0 else nd_canNunRec end - " + dblCanNumRec + " , nd_canCon=nd_canCon-" + dlbCanRecOri + " "
                                        + " ,nd_cantotmalest=nd_cantotmalest-" + dblCanMalEstOri + " "
                                        + " WHERE  co_emp=" + STR_COD_EMP_REL + " and co_loc=" + txtCodLocRel.getText() + " "
                                        + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                        + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                                stmLoc.executeUpdate(strSql);
                            }

                            if (objZafParSis.getCodigoMenu() == 1974) {

                                if (strEstFisBod.equals("S")) {

                                    dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString()), 2);
                                    dblCan = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()), 2);
                                    dblCanNumRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI).toString()), 2);

                                    strSqlFic = " UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod-" + dlbCanRecOri + "  WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                    stmLoc.executeUpdate(strSqlFic);
                                    if (conRemGlo != null) {
                                        stm_remota = conRemGlo.createStatement();
                                        stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                        stm_remota.close();
                                        stm_remota = null;
                                    }

                                    String strAux = "";

                                    strAux = "  , nd_canCon=nd_canCon+" + dlbCanRecOri + " ";

                                    strSql = " UPDATE tbm_detguirem SET st_regrep='M'  " + strAux + " "
                                            + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + txtCodLocRel.getText() + " "
                                            + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                            + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                                    stmLoc.executeUpdate(strSql);
                                }
                            }
                        }
                    }

                    if (objZafParSis.getCodigoMenu() == 2205) {

                        strSql = "SELECT sum(abs(nd_cancon)) as nd_cancon  FROM tbm_detguirem  "
                                + " WHERE co_emp=" + STR_COD_EMP_REL + "  AND  co_loc=" + txtCodLocRel.getText() + " "
                                + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();

                        java.sql.ResultSet rstLoc = stmLoc.executeQuery(strSql);
                        while (rstLoc.next()) {
                            dblCanConf = rstLoc.getDouble("nd_cancon");
                        }
                        rstLoc.close();
                        rstLoc = null;

                        if (dblCanConf == 0) {
                            strEstCon = "P";
                        }

                        strSql = " UPDATE tbm_cabguirem SET st_coninv='" + strEstCon + "' "
                                + " WHERE co_emp=" + STR_COD_EMP_REL + "  AND  co_loc=" + txtCodLocRel.getText() + " "
                                + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();
                        stmLoc.executeUpdate(strSql);

                    }

                    if (objZafParSis.getCodigoMenu() == 286) {

                        strSql = "SELECT  *,  case when canTotConf  = 0 then 'P'  "
                                + "  else     case when nd_can = canTotConf then 'C' else 'E' end  end as estConf  "
                                + " from (  "
                                + "  SELECT sum(abs(nd_can)) as nd_can,  "
                                + " ( sum(case when nd_cancon is null then 0 else nd_cancon end ) +  "
                                + "   sum(case when nd_cannunrec is null then 0 else nd_cannunrec end )  +   "
                                + "   sum(case when  nd_canTotMalEst is null then 0 else nd_canTotMalEst end  ) ) as canTotConf "
                                + " FROM tbm_detmovinv  "
                                + " WHERE co_emp=" + STR_COD_EMP_REL + "  AND  co_loc=" + txtCodLocRel.getText() + " "
                                + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                + " and st_meringegrfisbod = 'S' and nd_can > 0 "
                                + " ) as x ";

                        java.sql.ResultSet rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
                            strSql = " UPDATE tbm_cabmovinv SET st_coninv='" + rstLoc.getString("estConf") + "' "
                                    + " WHERE co_emp=" + STR_COD_EMP_REL + "  AND  co_loc=" + txtCodLocRel.getText() + " "
                                    + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();
                            stmLoc.executeUpdate(strSql);
                        }
                        rstLoc.close();
                        rstLoc = null;

                    }

                    if (objZafParSis.getCodigoMenu() == 1974) {

                        strSql = "SELECT * FROM ( SELECT sum(abs(nd_can)) as nd_can, sum(abs(nd_cancon)) as nd_cancon  FROM tbm_detguirem  "
                                + " WHERE co_emp=" + STR_COD_EMP_REL + "  AND  co_loc=" + txtCodLocRel.getText() + " "
                                + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                + " and st_meregrfisbod='S' ) as x WHERE x.nd_can=x.nd_cancon   ";
                        java.sql.ResultSet rstLoc = stmLoc.executeQuery(strSql);
                        while (rstLoc.next()) {
                            strEstCon = "C";
                        }
                        rstLoc.close();
                        rstLoc = null;

                        strSql = " UPDATE tbm_cabguirem SET st_coninv='" + strEstCon + "' "
                                + " WHERE co_emp=" + STR_COD_EMP_REL + "  AND  co_loc=" + txtCodLocRel.getText() + " "
                                + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();
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

        /*	
         public void clickAnterior() {
         try{
         if(rstCab != null ) {
         abrirCon();
         if(!rstCab.isFirst()) {
         if(blnHayCam) {
         if(isRegPro()) {
         rstCab.previous();
         refrescaDatos(CONN_GLO, rstCab);
         }}else {
         rstCab.previous();
         refrescaDatos(CONN_GLO, rstCab); 
         }}
         CerrarCon();
     
         }}catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
         catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
         }

         */
        @Override
        public void clickAnterior() {
            try {
                abrirCon();
                if (rstCab != null) {
                    if (!rstCab.isFirst()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.previous();
                                refrescaDatos(CONN_GLO);
                            }
                        } else {
                            rstCab.previous();
                            refrescaDatos(CONN_GLO);
                        }
                    }
                }
                CerrarCon();
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
            //clnTextos();
            noEditable(false);

            cargarTipoDoc(2);
            cargarBodPre();

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
                                refrescaDatos(CONN_GLO);
                            }
                        } else {
                            rstCab.last();
                            refrescaDatos(CONN_GLO);
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
                                refrescaDatos(CONN_GLO);
                            }
                        } else {
                            rstCab.first();
                            refrescaDatos(CONN_GLO);
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
        public void clickInsertar() {
            try {
                noEditable(false);

                java.awt.Color colBack;
                colBack = txtCodDoc.getBackground();
                txtCodDoc.setEditable(false);
                txtCodDoc.setBackground(colBack);
                txtMotTra.setEditable(false);
                clnTextos();

                //txtFecDoc.setHoy();
                datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                txtFecDoc.setText(objUti.formatearFecha(datFecAux, "dd/MM/yyyy"));

                cargarTipoDoc(1);
                cargarBodPre();

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

        /*        
         public void clickSiguiente(){ 
         try{
         if(rstCab != null ){
         abrirCon();
         if(!rstCab.isLast()) {
         if(blnHayCam || objTblMod.isDataModelChanged()) {
         if(isRegPro()) {
         rstCab.next();
         refrescaDatos(CONN_GLO, rstCab);
         }}else{
         rstCab.next();
         refrescaDatos(CONN_GLO, rstCab);
         }}
         CerrarCon();
         }}catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
         catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
         }
         */
        @Override
        public void clickSiguiente() {
            try {
                abrirCon();
                if (rstCab != null) {
                    if (!rstCab.isLast()) {
                        if (blnHayCam || objTblMod.isDataModelChanged()) {
                            if (isRegPro()) {
                                rstCab.next();
                                refrescaDatos(CONN_GLO); //cargarReg();
                            }
                        } else {
                            rstCab.next();
                            refrescaDatos(CONN_GLO); //cargarReg();
                        }
                    }
                }
                CerrarCon();
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        @Override
        public boolean eliminar() {
            boolean blnRes = false;
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

                    if (!blnRes) {
                        if (verificaIngMer(conn, "ELIMINADO")) {
                            if (verificaCanMalPro(conn, "ELIMINADO")) {
                                if (verificaSiHayCanNunEgr()) {
                                    if (eliminarReg(conn)) {
                                        if (conRemGlo != null) {
                                            conRemGlo.commit();
                                        }
                                        conn.commit();
                                        blnRes = true;
                                        objTooBar.setEstadoRegistro("Eliminado");
                                        blnHayCam = false;
                                    } else {
                                        conn.rollback();
                                    }
                                } else {
                                    conn.rollback();
                                }
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
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean eliminarReg(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.Statement stm_remota;
            String strSql = "", strSqlFic = "";
            int intCelSel = 0;
            double dlbCanRecOri = 0;

            double dblCanNumRec = 0;
            double dblCan = 0;
            String strEstCon = "E";

            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    strSql = "UPDATE tbm_cabingegrmerbod SET st_reg='E' WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + "AND  co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText();
                    stmLoc.executeUpdate(strSql);

                    if (!strAux.equals("Anulado")) {
                        for (int i = 0; i < tblDat.getRowCount(); i++) {
                            intCelSel = i;

                            String strEstFisBod = (tblDat.getValueAt(i, INT_TBL_IEBODFIS) == null ? "" : tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());

                            if (objZafParSis.getCodigoMenu() == 2205) {

                                dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString()), 2);
                                dblCan = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()), 2);
                                dblCanNumRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI).toString()), 2);

                                if (strEstFisBod.equals("S")) {
                                    if (strTipIngEgr.equals("A")) {

                                        String strSqlAux = "";
                                        if (strTipModIngEgr.equals("1")) {
                                            strSqlAux = " nd_caningbod=nd_caningbod+" + dlbCanRecOri;
                                        }
                                        if (strTipModIngEgr.equals("2")) {
                                            strSqlAux = " nd_canegrbod=nd_canegrbod+" + dlbCanRecOri;
                                        }

                                        strSqlFic = " UPDATE tbm_invbod SET " + strSqlAux + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }

                                    if (strTipIngEgr.equals("I")) {
                                        strSqlFic = " UPDATE tbm_invbod SET nd_caningbod=nd_caningbod+" + dlbCanRecOri + "   WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }
                                    if (strTipIngEgr.equals("E")) {
                                        strSqlFic = " UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod+" + dlbCanRecOri + "  WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }
                                }

                                strSql = " UPDATE tbm_detguirem SET st_regrep='M', co_usrCon=" + objZafParSis.getCodigoUsuario() + " "
                                        + " ,tx_obscon=' ' "
                                        + " ,nd_canNunRec=0, nd_canCon=nd_canCon-" + dlbCanRecOri + " "
                                        + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + txtCodLocRel.getText() + " "
                                        + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                        + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                                stmLoc.executeUpdate(strSql);
                            }

                            if (objZafParSis.getCodigoMenu() == 286) {

                                dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString()), 2);
                                dblCan = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()), 2);
                                dblCanNumRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI).toString()), 2);

                                if (strEstFisBod.equals("S")) {
                                    if (strTipIngEgr.equals("A")) {

                                        String strSqlAux = "";
                                        if (strTipModIngEgr.equals("1")) {
                                            strSqlAux = " nd_caningbod=nd_caningbod+" + dlbCanRecOri;
                                        }
                                        if (strTipModIngEgr.equals("2")) {
                                            strSqlAux = " nd_canegrbod=nd_canegrbod+" + dlbCanRecOri;
                                        }

                                        strSqlFic = " UPDATE tbm_invbod SET " + strSqlAux + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }

                                    if (strTipIngEgr.equals("I")) {
                                        strSqlFic = " UPDATE tbm_invbod SET nd_caningbod=nd_caningbod+" + dlbCanRecOri + "   WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }
                                    if (strTipIngEgr.equals("E")) {
                                        strSqlFic = " UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod+" + dlbCanRecOri + "  WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }
                                }

                                strSql = " UPDATE tbm_detmovinv SET st_regrep='M', co_usrCon=" + objZafParSis.getCodigoUsuario() + " "
                                        + " ,tx_obs1=' ' "
                                        + " ,nd_canNunRec=0, nd_canCon=nd_canCon-" + dlbCanRecOri + " "
                                        + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + txtCodLocRel.getText() + " "
                                        + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                        + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                                stmLoc.executeUpdate(strSql);
                            }

                            if (objZafParSis.getCodigoMenu() == 1974) {

                                if (strEstFisBod.equals("S")) {

                                    dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString()), 2);
                                    dblCan = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()), 2);
                                    dblCanNumRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANNUMREC_ORI).toString()), 2);

                                    strSqlFic = " UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod-" + dlbCanRecOri + "  WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                    stmLoc.executeUpdate(strSqlFic);
                                    if (conRemGlo != null) {
                                        stm_remota = conRemGlo.createStatement();
                                        stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                        stm_remota.close();
                                        stm_remota = null;
                                    }

                                    strAux = "  , nd_canCon=nd_canCon+" + dlbCanRecOri + " ";

                                    strSql = " UPDATE tbm_detguirem SET st_regrep='M'  " + strAux + " "
                                            + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + txtCodLocRel.getText() + " "
                                            + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                            + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                                    stmLoc.executeUpdate(strSql);

                                }
                            }

                        }

                        if (objZafParSis.getCodigoMenu() == 286) {
                            if (dblCan == (dlbCanRecOri + dblCanNumRec)) {
                                strEstCon = "P";
                            }
                        }

                        if (objZafParSis.getCodigoMenu() == 2205) {
                            if (dblCan == (dlbCanRecOri + dblCanNumRec)) {
                                strEstCon = "P";
                            }
                        }

                        if (objZafParSis.getCodigoMenu() == 1974) {
                            if (dblCan == (dlbCanRecOri + dblCanNumRec)) {
                                strEstCon = "C";
                            }
                        }

                        if (objZafParSis.getCodigoMenu() == 2205) {
                            strSql = " UPDATE tbm_cabguirem SET st_coninv='" + strEstCon + "' "
                                    + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + "  AND  co_loc=" + txtCodLocRel.getText() + " "
                                    + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();
                            stmLoc.executeUpdate(strSql);

                        } else {
                            if (!strTipIngEgr.equals("A")) {
                                strSql = " UPDATE tbm_cabmovinv SET st_coninv='" + strEstCon + "', st_autingegrinvcon='N' "
                                        + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + "  AND  co_loc=" + txtCodLocRel.getText() + " "
                                        + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();
                                stmLoc.executeUpdate(strSql);
                            }
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

        private boolean validaCampos() {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            java.util.Date d1 = null, d2 = null;
            String fechaDesAtr;
            if (txtCodTipDoc.getText().equals("")) {
                tabFrm.setSelectedIndex(0);
                MensajeInf("El campo << Tipo Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                txtCodTipDoc.requestFocus();
                return false;
            }

            if (!txtFecDoc.isFecha()) {
                tabFrm.setSelectedIndex(0);
                MensajeInf("El campo << Fecha Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                txtFecDoc.requestFocus();
                return false;
            }

            if (txtCodBod.getText().equals("")) {
                tabFrm.setSelectedIndex(0);
                MensajeInf("El campo << Codigo Bodega >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                txtCodBod.requestFocus();
                return false;
            }

            if (!((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915) || (objZafParSis.getCodigoMenu() == 286) || (objZafParSis.getCodigoMenu() == 1974))) {
                if (txtNumDoc.getText().equals("")) {
                    tabFrm.setSelectedIndex(0);
                    MensajeInf("El campo << Número de Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                    txtNumDoc.requestFocus();
                    return false;
                }
            }

            double dblCan = 0;
            double dblCanSol = 0;
            double dblCanNumEgr = 0;
            double dblCanMalEst = 0;
            double dblPesTotKgr = 0;
            double dblCanTotCon = 0;
            double dblCanCodAlt = 0;

            for (int intRowVal = 0; intRowVal < tblDat.getRowCount(); intRowVal++) {
                if (tblDat.getValueAt(intRowVal, INT_TBL_CODITM) != null) {

                    dblCanSol += Double.parseDouble(objInvItm.getIntDatoValidado(tblDat.getValueAt(intRowVal, INT_TBL_CANMOV)));
                    dblCanNumEgr += Double.parseDouble(objInvItm.getIntDatoValidado(tblDat.getValueAt(intRowVal, INT_TBL_CANNUMREC)));

                    dblCan += Double.parseDouble(objInvItm.getIntDatoValidado(tblDat.getValueAt(intRowVal, INT_TBL_CANCON)));
                    dblCanMalEst += Double.parseDouble(objInvItm.getIntDatoValidado(tblDat.getValueAt(intRowVal, INT_TBL_CANMALEST)));

                    dblCanTotCon += Double.parseDouble(objInvItm.getIntDatoValidado(tblDat.getValueAt(intRowVal, INT_TBL_CANTOTCON)));

                    if (((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915))) {
                        double dlbPesKgr = Double.parseDouble(objInvItm.getIntDatoValidado(tblDat.getValueAt(intRowVal, INT_TBL_PESKGR)));
                        double dblCanCon = Double.parseDouble(objInvItm.getIntDatoValidado(tblDat.getValueAt(intRowVal, INT_TBL_CANCON)));
                        double dlbKgr = dblCanCon * dlbPesKgr;
                        dblPesTotKgr += dlbKgr;
                    }

                }
            }

            if ((dblCan + dblCanNumEgr + dblCanMalEst) <= 0.00) {
                MensajeInf("Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.");
                return false;
            }

            if (blnEstCarConfIns) {
                if (dblCanSol == dblCanTotCon) {
                    MensajeInf("Este item ya ha sido confirmado.");
                    return false;
                }
            }

            if (((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915))) {
                if (optEnv.isSelected()) {
                    if (txtPlaVeh.getText().equals("")) {
                        tabFrm.setSelectedIndex(2);
                        MensajeInf("El campo << Vehículo >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                        txtPlaVeh.requestFocus();
                        return false;
                    }

                    if (txtIdeCho.getText().equals("")) {
                        tabFrm.setSelectedIndex(2);
                        MensajeInf("El campo << Chofer >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                        txtIdeCho.requestFocus();
                        return false;
                    }

                    double dlbPesVeh = Double.parseDouble(txtPesVeh.getText());

                    if (dblPesTotKgr > dlbPesVeh) {
                        String strMsgCon = "El peso de los items seleccionados excede el peso soportado por el vehículo " + txtPlaVeh.getText() + "."
                                + " \nPeso soportado por el vehículo: " + txtPesVeh.getText() + " Kg"
                                + " \nPeso total de los items seleccionados: " + objUti.redondear(dblPesTotKgr, 2) + " Kg"
                                + " \n¿Está seguro que desea realizar esta operación?";

                        if (!mensajeConfir(strMsgCon)) {
                            return false;
                        }
                    }
                }

                if (optRet.isSelected()) {
                    if (txtIdeTra.getText().equals("")) {
                        tabFrm.setSelectedIndex(2);
                        MensajeInf("El campo << Identificación >> es obligatorio.\nEscriba la Identificación y vuelva a intentarlo.");
                        txtIdeTra.requestFocus();
                        return false;
                    }

                    if (optVehTra.isSelected()) {
                        if (txtIdeTra.getText().length() > 0) {
                            if (txtIdeTra.getText().length() != 13 && txtIdeTra.getText().length() != 10) {
                                tabFrm.setSelectedIndex(2);
                                MensajeInf("El campo <<Identificación >> debe contener 10 ó 13 digitos.\nEscriba la Identificación y vuelva a intentarlo.");
                                txtIdeTra.selectAll();
                                txtIdeTra.requestFocus();
                                return false;
                            }
                        }
                    }

                    if (txtNomTra.getText().equals("")) {
                        tabFrm.setSelectedIndex(2);
                        MensajeInf("El campo << Razón Social >> es obligatorio.\nEscriba la Razón Social y vuelva a intentarlo.");
                        txtNomTra.requestFocus();
                        return false;
                    }

                    if (txtPlaVehTra.getText().equals("")) {
                        tabFrm.setSelectedIndex(2);
                        MensajeInf("El campo << Placa >> es obligatorio.\nEscriba la Placa y vuelva a intentarlo.");
                        txtPlaVehTra.requestFocus();
                        return false;
                    }

                }

//    if (optEnv.isSelected()){  
//        double dlbPesVeh=Double.parseDouble( txtPesVeh.getText() );
//
//        if (dblPesTotKgr > dlbPesVeh){
//            String strMsgCon = "El peso de los items seleccionados excede el peso soportado por el vehículo " + txtPlaVeh.getText() + "." +
//                    " \nPeso soportado por el vehículo: " + txtPesVeh.getText() + " Kg" + 
//                    " \nPeso total de los items seleccionados: " + objUti.redondear( dblPesTotKgr,2) + " Kg" + 
//                    " \n¿Está seguro que desea realizar esta operación?";
//
//            if (! mensajeConfir(strMsgCon))
//                return false; 
//        }      
//    }    
            }

//   else{
//      if( dblCan<=0.00 ){ 
//          MensajeInf("Tiene que confirmar almenos uno, caso contrario no se podra realizar la operación .\nVerifique y vuelva a intentarlo.");
//          return false; 
//      }
//   } 
            // SOLO EL USUARIO ADMINISTRADOR PODRA CONFIRMAR ORDDES MENORES A LA FECHA INDICADA
            if ((objZafParSis.getCodigoUsuario() != 1) && ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915))) {
                fechaDesAtr = objUti.formatearFecha(txtFecDocCon.getText(), "yyyy-MM-dd", DATE_FORMAT);
                try {
                    if (!fechaDesAtr.equals("[ERROR]")) {
                        d1 = sdf.parse(strFecDoc);
                        d2 = sdf.parse(fechaDesAtr);
                        if (d2.before(d1)) {
                            MensajeInf("Debe solicitar autorizacion para confirmar esta orden de despacho. ");
                            return false;
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            return true;
        }

        public boolean abrirConRem() {
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

        private boolean isAnuladoDoc(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try {
                stmLoc = conn.createStatement();

                if (objZafParSis.getCodigoMenu() == 2063) {
                    strSql = "SELECT st_reg  FROM tbm_cabsolsaltemmer WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                            + " AND co_loc=" + txtCodLocRel.getText() + " AND co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();
                } else if (objZafParSis.getCodigoMenu() == 2073) {
                    strSql = "SELECT st_reg  FROM tbm_cabsolsaltemmer WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                            + " AND co_loc=" + txtCodLocRel.getText() + " AND co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();
                } else if ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915)) {
                    strSql = "SELECT st_reg  FROM tbm_cabguirem WHERE co_emp=" + STR_COD_EMP_REL + " "
                            + " AND co_loc=" + txtCodLocRel.getText() + " AND co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();
                } else if (objZafParSis.getCodigoMenu() == 1974) {
                    strSql = "SELECT st_reg  FROM tbm_cabguirem WHERE co_emp=" + STR_COD_EMP_REL + " "
                            + " AND co_loc=" + txtCodLocRel.getText() + " AND co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();
                } else {
                    strSql = "SELECT st_reg  FROM tbm_cabmovinv WHERE co_emp=" + STR_COD_EMP_REL + " "
                            + " AND co_loc=" + txtCodLocRel.getText() + " AND co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();
                }
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    if (rstLoc.getString("st_reg").equals("I")) {
                        MensajeInf("EL DOCUMENTO FUE ANULADO NO ES POSIBLE REALIZAR LA CONFIRMACION.");
                        blnRes = false;
                    } else {
                        blnRes = true;
                    }
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
        }

        @Override
        public boolean insertar()
        {
            boolean blnRes = false;
            try 
            {
                if (validaCampos()) 
                {
                    if (objZafParSis.getCodigoMenu() == 2205 || objZafParSis.getCodigoMenu() == 2915) /* 2205 Confirmacion Egresos de bodega ' 2915 Egresos Cliente retira  */ 
                    {
                        if (validaFechaConfirmacionWerner2015()) 
                        {
                            if (insertarReg()) 
                            {
                                blnRes = true;
                            }
                        }
                    }
                    else
                    {
                        if (insertarReg())
                        {
                            blnRes = true;
                        }
                    }

                }

                ZafGenGuiRem objZafGuiRem = new ZafGenGuiRem();
                abrirCon();
                objZafGuiRem.asignarCosDet(CONN_GLO);
                objZafGuiRem.asignarCostCab(CONN_GLO);
                CerrarCon();

            } 
            catch (Exception Evt) {   blnRes = false;   objUti.mostrarMsgErr_F1(this, Evt); }
            return blnRes;
        }

        private boolean validaFechaConfirmacionWerner2015() 
        {
            boolean blnRes = false;
            java.sql.Connection conLoc;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strCadena, strMsg;
            try 
            {
                if (objZafParSis.getCodigoUsuario() == 1 /* ADMIN */
                        || objZafParSis.getCodigoUsuario() == 122 /* FERNANDO RUIZ*/
                        || objZafParSis.getCodigoUsuario() == 246 /* WERNER CAMPOVERDE */
                        || objZafParSis.getCodigoUsuario() == 24) /* LUIGI SENSI */ {
                    blnRes = true;
                } else {
                    conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                    stmLoc = conLoc.createStatement();
                    String fecha = txtFecDocCon.getText();
                    strCadena = "";
                    strCadena += " SELECT CASE WHEN '" + fecha + "' > CURRENT_DATE - 30 then 'S' ELSE 'N' END AS FECHA";
                    //  System.out.println("validaFecha: " + strCadena);
                    rstLoc = stmLoc.executeQuery(strCadena);
                    if (rstLoc.next()) 
                    {
                        //     System.out.println("validaFecha 2: " + rstLoc.getString("fecha"));
                        if (rstLoc.getString("fecha").equals("N")) 
                        {
                            blnRes = false;
                            strMsg = "<html> La fecha de la orden de despacho esta fuera del rango de autorización, no se puede confirmar. <BR>";
                            strMsg += "Por favor comunicarselo al Ing. Fernando Ruiz o al Ing. Werner Campoverde . <html>";
                            JOptionPane.showMessageDialog(jfrThis, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);
                        } 
                        else
                        {
                            blnRes = true;
                        }
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                    conLoc.close();
                    conLoc = null;
                }

            }
            catch (java.sql.SQLException e) {   blnRes = false; objUti.mostrarMsgErr_F1(this, e);  } 
            catch (Exception Evt) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, Evt);       }
            return blnRes;
        }

        public boolean insertarReg() 
        {
            boolean blnRes = false;
            java.sql.Connection conn;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            int intCodDoc = 0;
            int intNumDoc = 0;
            String strCorEleTo = "";
            try 
            {
                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) {
                    conn.setAutoCommit(false);
                    stmLoc = conn.createStatement();

                    strBuf = new StringBuffer();
                    arlItmRec = new java.util.ArrayList();
                    arlItmRec.clear();
                    blnEstMalEst = false;

                    if (isAnuladoDoc(conn)) 
                    {

                        Librerias.ZafCorElePrg.ZafCorElePrg objCorEle = new Librerias.ZafCorElePrg.ZafCorElePrg(objZafParSis, jfrThis);
                        strCorEleTo = objCorEle._getCorEleConfIngEgr_01();
                        objCorEle = null;

                        strSql = "SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 end as co_doc  FROM tbm_cabingegrmerbod WHERE "
                                + " co_emp=" + STR_COD_EMP_REL + " and co_loc=" + STR_COD_LOC_REL + " "
                                + " and co_tipDoc = " + txtCodTipDoc.getText();
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
                            intCodDoc = rstLoc.getInt("co_doc");
                        }
                        rstLoc.close();
                        rstLoc = null;

                        strSql = "SELECT CASE WHEN (ne_ultdoc+1) is null THEN 1 ELSE ne_ultdoc+1 END AS numDoc FROM tbm_cabtipdoc "
                                + " WHERE co_emp=" + STR_COD_EMP_REL + " and co_loc=" + STR_COD_LOC_REL + " and co_tipdoc=" + txtCodTipDoc.getText();
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
                            intNumDoc = rstLoc.getInt("numDoc");
                        }
                        rstLoc.close();
                        rstLoc = null;

                        if (insertarCab(conn, intCodDoc, intNumDoc)) {
                            if (insertarDet(conn, intCodDoc)) {
                                if (!_getVerificaSiHayConfirmacion(conn)) {
                                    // if(verificaCanConIngRel(conn)){
                                    if (verificaCanNunEnv(conn)) {

                                        conn.commit();

                                        blnRes = true;
                                        txtCodDoc.setText("" + intCodDoc);
                                        txtNumDoc.setText("" + intNumDoc);
                                        txtNumDocSolOcu.setText(txtNumDoc.getText());

                                        verificaConfEgr(conn);

                                        if (blnEstCanNunEnv) {
                                            if ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915)) {   // CONFIRMACION EGRESO
                                                strItmConCanNunEnv = "Confirmacion de mercaderia que no se enviara desde: <br> " + txtNomBod.getText() + " a " + strNomBodIng + "  <br>  " + strItmConCanNunEnv;
                                                objInvItm.enviarCorreo(strCorEleTo, "Zafiro: Mercaderia que no se enviara.", strItmConCanNunEnv);
                                                MensajeInf("HAY MERCADERIA NUNCA ENVIADA \n SE HA CONFIRMADO EN CANTIDAD NUNCA RECIBIDA EN EL INGRESO ..");

                                                ZafCom33 objCom_01 = new ZafCom33(objZafParSis, arlItmRec, Integer.parseInt(txtCodBod.getText()));
                                                jfrThis.getParent().add(objCom_01, javax.swing.JLayeredPane.DEFAULT_LAYER);
                                                objCom_01.show();
                                                objCom_01.dispose();

                                            }
                                            if (objZafParSis.getCodigoMenu() == 286) {   // CONFIRMACION INGRESO
                                                strItmConCanNunEnv = "Confirmacion de mercaderia que no se recibe en: <br> " + txtNomBod.getText() + "  <br>  " + strItmConCanNunEnv;
                                                objInvItm.enviarCorreo(strCorEleTo, "Zafiro: Mercaderia que no se recibe.", strItmConCanNunEnv);
                                            }
                                        }
                                        if (blnEstCanConCliRet) {
                                            MensajeInf("SE HA CONFIRMADO EL INGRESO/EGRESO DE LOS DOCUMENTOS RELACIONADOS..");

                                        }

                                        if (blnEstMalEst) {
                                            String strMensCorEle = "Confirmacion de mercadeira en mal estado:  ";
                                            strMensCorEle += " <br> " + strBuf.toString();
                                            objInvItm.enviarCorreo(strCorEleTo, "Zafiro: Mercaderia en mal estado.", strMensCorEle);
                                        }

                                        if (objZafParSis.getCodigoMenu() == 2063) {
                                            if (STRCODDOCTRANS.equals("")) {
                                                MensajeInf("NO SE GENERO NINGUN DOCUMENTO DE CONFIRMACIÓN PORQUE NO SALIO MERCADERIA.");
                                            }
                                        }

                                        if ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915))
                                        {
                                            System.out.println("CODIGO DEL LOCAL---->" + objZafParSis.getCodigoLocal());
                                            /*LuisParrales -->Begin*/
                                            int coloc = objZafParSis.getCodigoLocal();
                                            int codBodGrp = Integer.parseInt(txtCodBod.getText());
                                            int numorddes = Integer.parseInt(txtNumGuiDes.getText());
                                            ZafReglas.ZafGuiRem.ZafGenGuiRem objZafGuiRem = new ZafReglas.ZafGuiRem.ZafGenGuiRem(codBodGrp, numorddes);
                                            objZafGuiRem.generarGuiRem(coloc, conn);
                                        }

                                        cargarDetIns(conn, intCodDoc);

                                        if ((objZafParSis.getCodigoMenu() == 286) || (objZafParSis.getCodigoMenu() == 2750)) // CONFIRMACION INGRESO
                                        {
                                            if (objZafParSis.getCodigoUsuario() != 246) //Ing. Werner Campoverde
                                            {
                                                imprimirOrdenAlmacenamiento(conn);
                                            }
                                        }
                                    }
                                    else {
                                        conn.rollback();
                                    }
//           }else conn.rollback();
                                } else {
                                    conn.rollback();
                                }
                            } else {
                                conn.rollback();
                            }
                        } else {
                            conn.rollback();
                        }

                    }

                    arlItmRec = null;
                    strBuf = null;
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

        private boolean verificaConfEgr(java.sql.Connection conn) 
        {
            boolean blnRes = true;
            try
            {
                if (conn != null) {

                    strItmFal = "";

                    if ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915)) {

                        strItmFal = "<HTML> <TABLE><tr><td>ITEM PENDIENTE DE CONFIRMAR SALIDA </td></tr></table> "
                                + " <TABLE BORDER=1 > <TR> <TD> <FONT COLOR=\"blue\"> CODITM </FONT> </TD><TD>   <FONT COLOR=\"blue\"> NOMITM  </FONT>  </TD><TD>    <FONT COLOR=\"blue\"> CAN.FAL   </FONT>    </TD> </TR> ";

                        if (getEstOrdDesConfEgr(conn)) {
                            if (!mensajeConfir("LA ORDEN DE DESPACHO TIENE ITEMS PENDIENTES  \n VA A SEGUIR HACIENDO MÁS DESPACHOS..? ")) {

                                getItemPenOrdDesConfEgr(conn);

                                strItmFal += "</TABLE><TABLE><tr><td> ESTA SEGURO QUE NO VA HACER MÁS DESPACHOS..? </td></tr></table> </HTML> ";

                                if (mensajeConfir(strItmFal)) 
                                {
                                    //System.out.println(" No despacho mas....");
                                }
                            }
                        }
                        strItmFal = "";
                    }
                }
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean getEstOrdDesConfEgr(java.sql.Connection conn) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try 
            {
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();

                    strSql = "select st_coninv  from tbm_cabguirem "
                            + " where co_emp=" + STR_COD_EMP_REL + " and co_loc=" + txtCodLocRel.getText() + " and co_tipdoc=" + txtCodTipDocCon.getText() + "  and co_doc= " + txtCodDocCon.getText() + " and st_coninv in ('P','E') ";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        blnRes = true;
                    }
                    rstLoc.close();
                    rstLoc = null;
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
        String strItmFal = "";

        private boolean getItemPenOrdDesConfEgr(java.sql.Connection conn) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try
            {
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();

                    strSql = "SELECT * FROM  ( select a.st_coninv, a1.tx_codalt, a1.tx_nomitm, a1.nd_can , a1.st_meregrfisbod,  a1.nd_cancon,  round((abs(a1.nd_can)-abs(a1.nd_cancon)),2) as canfal   from tbm_cabguirem as a "
                            + " inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
                            + " where a.co_emp=" + STR_COD_EMP_REL + " and a.co_loc=" + txtCodLocRel.getText() + " and a.co_tipdoc=" + txtCodTipDocCon.getText() + "  and a.co_doc= " + txtCodDocCon.getText() + " "
                            + " and a1.st_meregrfisbod = 'S' and a.st_coninv in ('P','E')  "
                            + " ) AS x where canfal > 0 ";
                    rstLoc = stmLoc.executeQuery(strSql);
                    while (rstLoc.next()) {

                        strItmFal += "<TR><TD> " + rstLoc.getString("tx_codalt") + " </TD><TD> " + rstLoc.getString("tx_nomitm") + " </TD><TD> " + rstLoc.getString("canfal") + " </TD></TR> ";

                    }
                    rstLoc.close();
                    rstLoc = null;
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

        public boolean insertar_respaldo() 
        {
            boolean blnRes = false;
            java.sql.Connection conn;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            int intCodDoc = 0;
            int intNumDoc = 0;
            String strCorEleTo = "";
            try 
            {
                strBuf = new StringBuffer();
                blnEstMalEst = false;

                if (validaCampos())
                {
                    conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                    if (conn != null) {
                        conn.setAutoCommit(false);
                        stmLoc = conn.createStatement();

                        arlItmRec = new java.util.ArrayList();
                        arlItmRec.clear();

                        if (isAnuladoDoc(conn)) 
                        {

                            Librerias.ZafCorElePrg.ZafCorElePrg objCorEle = new Librerias.ZafCorElePrg.ZafCorElePrg(objZafParSis, jfrThis);
                            strCorEleTo = objCorEle._getCorEleConfIngEgr_01();
                            objCorEle = null;

                            strSql = "SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 end as co_doc  FROM tbm_cabingegrmerbod WHERE "
                                    + " co_emp=" + STR_COD_EMP_REL + " and co_loc=" + STR_COD_LOC_REL + " "
                                    + " and co_tipDoc = " + txtCodTipDoc.getText();
                            rstLoc = stmLoc.executeQuery(strSql);
                            if (rstLoc.next()) {
                                intCodDoc = rstLoc.getInt("co_doc");
                            }
                            rstLoc.close();
                            rstLoc = null;

                            strSql = "SELECT CASE WHEN (ne_ultdoc+1) is null THEN 1 ELSE ne_ultdoc+1 END AS numDoc FROM tbm_cabtipdoc "
                                    + " WHERE co_emp=" + STR_COD_EMP_REL + " and co_loc=" + STR_COD_LOC_REL + " and co_tipdoc=" + txtCodTipDoc.getText();
                            rstLoc = stmLoc.executeQuery(strSql);
                            if (rstLoc.next()) {
                                intNumDoc = rstLoc.getInt("numDoc");
                            }
                            rstLoc.close();
                            rstLoc = null;

                            stmLoc.close();
                            stmLoc = null;

                            if (insertarCab(conn, intCodDoc, intNumDoc)) {
                                // if(verificaCanNunRec(conn)){
                                if (insertarDet(conn, intCodDoc)) {
                                    if (!_getVerificaSiHayConfirmacion(conn)) {
                                        if (verificaCanNunEnv(conn)) {

                                            conn.commit();

                                            if (blnEstCanNunEnv) {

                                                if (objZafParSis.getCodigoMenu() == 2205) {   // CONFIRMACION EGRESO
                                                    strItmConCanNunEnv = "Confirmacion de mercaderia que no se enviara desde: <br> " + txtNomBod.getText() + " a " + strNomBodIng + "  <br>  " + strItmConCanNunEnv;
                                                    objInvItm.enviarCorreo(strCorEleTo, "Zafiro: Mercaderia que no se enviara.", strItmConCanNunEnv);
                                                    MensajeInf("HAY MERCADERIA NUNCA ENVIADA \n SE HA CONFIRMADO EN CANTIDAD NUNCA RECIVIDAD EN EL INGRESO ..");

                                                    Compras.ZafCom33.ZafCom33 objCom_01 = new Compras.ZafCom33.ZafCom33(objZafParSis, arlItmRec, Integer.parseInt(txtCodBod.getText()));
                                                    jfrThis.getParent().add(objCom_01, javax.swing.JLayeredPane.DEFAULT_LAYER);
                                                    objCom_01.show();
                                                    objCom_01.dispose();

                                                }
                                                if (objZafParSis.getCodigoMenu() == 286) {   // CONFIRMACION INGRESO
                                                    strItmConCanNunEnv = "Confirmacion de mercaderia que no se recibe en: <br> " + txtNomBod.getText() + "  <br>  " + strItmConCanNunEnv;
                                                    objInvItm.enviarCorreo(strCorEleTo, "Zafiro: Mercaderia que no se recibe.", strItmConCanNunEnv);

                                                }

                                            }

                                            txtCodDoc.setText("" + intCodDoc);
                                            txtNumDoc.setText("" + intNumDoc);
                                            txtNumDocSolOcu.setText(txtNumDoc.getText());
                                            blnRes = true;

                                            if (blnEstMalEst) {
                                                String strMensCorEle = "Confirmacion de mercadeira en mal estado:  ";
                                                strMensCorEle += " <br> " + strBuf.toString();
                                                objInvItm.enviarCorreo(strCorEleTo, "Zafiro: Mercaderia en mal estado.", strMensCorEle);
                                            }

                                            if (objZafParSis.getCodigoMenu() == 2063) {
                                                if (STRCODDOCTRANS.equals("")) {
                                                    MensajeInf("NO SE GENERO NINGUN DOCUMENTO DE CONFIRMACIÓN PORQUE NO SALIO MERCADERIA.");
                                                }
                                            }

//          if(objZafParSis.getCodigoMenu()==2205){
//            strFecSisBase = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos() );
//            String strMenArchHis="Fecha : "+strFecSisBase+"  PROCESO DE INSERTADO CON EXITO GUIA # "+txtNumDocCon.getText();
//            //almacenarArchHis(strMenArchHis);
//            //almacenarArchHis("==============================================================================================");
//          }
                                            cargarDetIns(conn, intCodDoc);

                                            //  }else conn.rollback();
                                        } else {
                                            conn.rollback();
                                        }
                                    } else {
                                        conn.rollback();
                                    }
                                } else {
                                    conn.rollback();
                                }
                            } else {
                                conn.rollback();
                            }

                        } else {
//        if(objZafParSis.getCodigoMenu()==2205){
//            strFecSisBase = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos() );
//            String strMenArchHis="Fecha : "+strFecSisBase+"  NO SE PUEDE INSERTAR LA GUIA POR QUE ESTA ANULADA # "+txtNumDocCon.getText();
//            almacenarArchHis(strMenArchHis);
//        }
                        }

                        arlItmRec = null;

                        conn.close();
                        conn = null;
                    }

                } else {
//        if(objZafParSis.getCodigoMenu()==2205){
//            strFecSisBase = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos() );
//            String strMenArchHis="Fecha : "+strFecSisBase+"  NO PASO VELIDACION AL INSERTAR LA GUIA # "+txtNumDocCon.getText();
//            almacenarArchHis(strMenArchHis);
//        }
                }

                strBuf = null;

            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }

            return blnRes;
        }

        private boolean cargarDetIns(java.sql.Connection conn, int intCodDoc) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            Vector vecData;
            double dblCanConf = 0;
            double dblCanRec = 0;
            double dblCan = 0;
            String strEstCon = "E", stmersaldemfac = "N", stfacmersaldem = "";
            try
            {
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();

                    vecData = new Vector();

                    if (objZafParSis.getCodigoMenu() == 2063) 
                    {

                        strSql = "select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,  abs(a.nd_can) as nd_can , '' as tx_obs1,  a2.nd_canNunegr as nd_canNunRec, a2.nd_cantotegr as nd_cancon "
                                + " ,a1.co_itmact, a2.co_reg, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a2.co_bod, a1.st_meringegrfisbod, abs(a2.nd_can) as nd_canmov "
                                + " from tbm_detingegrmerbod as a "
                                + " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel)  "
                                + " INNER JOIN tbm_detsolsaltemmer AS a2 ON (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrelsolsaltemmer and a2.co_tipdoc=a1.co_tipdocrelsolsaltemmer  "
                                + " and a2.co_doc=a1.co_docrelsolsaltemmer and a2.co_reg=a1.co_regrelsolsaltemmer) "
                                + " WHERE  a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " AND "
                                + " a.co_tipdoc=" + txtCodTipDoc.getText() + " AND a.co_doc=" + intCodDoc;
                        rstLoc = stmLoc.executeQuery(strSql);
                        while (rstLoc.next()) 
                        {
                            java.util.Vector vecReg = new java.util.Vector();
                            vecReg.add(INT_TBL_LINEA, "");
                            vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itmact"));
                            vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                            vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                            vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                            vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_canmov"));
                            vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));
                            vecReg.add(INT_TBL_CANCON, rstLoc.getString("nd_can"));
                            vecReg.add(INT_TBL_CANNUMREC, rstLoc.getString("nd_canNunRec"));
                            vecReg.add(INT_TBL_CANMALEST, "");
                            vecReg.add(INT_TBL_CANTOTNUMREC, "");
                            vecReg.add(INT_TBL_OBSITMCON, rstLoc.getString("tx_obs1"));

                            vecReg.add(INT_TBL_CANCON_ORI, rstLoc.getString("nd_can"));
                            vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                            vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                            vecReg.add(INT_TBL_CANNUMREC_ORI, rstLoc.getString("nd_canNunRec"));
                            vecReg.add(INT_TBL_CANMALEST_ORI, "");
                            vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meringegrfisbod"));
                            vecReg.add(INT_TBL_CANTOTMALEST, "");
                            vecReg.add(INT_TBL_CODEMPREL, null);
                            vecReg.add(INT_TBL_CODLOCREL, null);
                            vecReg.add(INT_TBL_CODTIDOREL, null);
                            vecReg.add(INT_TBL_CODDOCREL, null);
                            vecReg.add(INT_TBL_CODREGREL, null);
                            vecReg.add(INT_TBL_TXNOMBODREL, null);
                            vecReg.add(INT_TBL_CODBODREL, null);

                            dblCan += rstLoc.getDouble("nd_canmov");
                            dblCanConf += rstLoc.getDouble("nd_canCon");
                            dblCanConf += rstLoc.getDouble("nd_canNunRec");
                            dblCanRec += rstLoc.getDouble("nd_canNunRec");
                            vecData.add(vecReg);
                        }
                        objTblMod.setData(vecData);
                        tblDat.setModel(objTblMod);
                        rstLoc.close();
                        rstLoc = null;

                        String strCreDocEgr = "N", strConTotMerEgr = "N", strExiMerSinEgr = "N", st_ConTotMerIng = "N";
                        String strFec = "", strFecMer = "";
                        String strAux = "";

                        // case when ndcan = (ndcanegr+ndcannunegr) then 'T' else case when ndcanegr > 0 then 'P' else 'N' end end as stDocegr" +
                        // ",case when ndcannunegr > 0 then 'S' else 'N' end  as stExiMerSinEgr " +
                        strSql = "SELECT * "
                                + " FROM( "
                                + " select sum(nd_can) as ndcan, sum(nd_cantotegr) as ndcanegr, sum(nd_cannunegr) as ndcannunegr "
                                + " from tbm_detsolsaltemmer where co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + txtCodLocRel.getText() + " "
                                + " and  co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                + " ) AS x ";
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) 
                        {

                            if ((rstLoc.getDouble("ndcan") == rstLoc.getDouble("ndcannunegr"))) {
                                //strAux="st_ConTotMerEgr='S', st_ConTotMerIng='S',  st_ExiMerSinIng='S' ";
                                strConTotMerEgr = "S";
                                st_ConTotMerIng = "S";
                                strExiMerSinEgr = "S";
                            }

                            if (rstLoc.getDouble("ndcanegr") > 0) {
                                // strAux=" st_CreDocEgr='P' ";
                                strCreDocEgr = "P";
                            }

                            if (rstLoc.getDouble("ndcanegr") > 0) {
                                if ((rstLoc.getDouble("ndcan") == (rstLoc.getDouble("ndcanegr") + rstLoc.getDouble("ndcannunegr")))) {
                                    // strAux="st_CreDocEgr='T', fe_CretodDocEgr="+objZafParSis.getFuncionFechaHoraBaseDatos()+", st_ConTotMerEgr='S', fe_ConTotMerEgr="+objZafParSis.getFuncionFechaHoraBaseDatos()+"  ";
                                    strCreDocEgr = "T";
                                    strConTotMerEgr = "S";
                                    strAux = " fe_CretodDocEgr=" + objZafParSis.getFuncionFechaHoraBaseDatos() + ", fe_ConTotMerEgr=" + objZafParSis.getFuncionFechaHoraBaseDatos() + "  ";
                                }
                            }

                            if (rstLoc.getDouble("ndcannunegr") > 0) {
                                // if(!strAux.equals("")) strAux=strAux+",";
                                // strAux=strAux+" st_ExiMerSinEgr='S' ";
                                strExiMerSinEgr = "S";
                            }

                            /*  strCreDocEgr=rstLoc.getString("stDocegr");
                             strExiMerSinEgr=rstLoc.getString("stExiMerSinEgr");
                             if(rstLoc.getString("stDocegr").equals("T")){
                             strConTotMerEgr="S"; 
                             strFec=", fe_CretodDocEgr="+objZafParSis.getFuncionFechaHoraBaseDatos(); 
                             strFecMer=", fe_ConTotMerEgr="+objZafParSis.getFuncionFechaHoraBaseDatos(); 
                             }*/
                        }
                        rstLoc.close();
                        rstLoc = null;

                        if (strAux.equals("")) {
                            strAux = " st_ConTotMerEgr='" + strConTotMerEgr + "', st_ConTotMerIng='" + st_ConTotMerIng + "', st_ExiMerSinEgr='" + strExiMerSinEgr + "', st_CreDocEgr='" + strCreDocEgr + "' ";
                        } else {
                            strAux += " , st_ConTotMerEgr='" + strConTotMerEgr + "', st_ConTotMerIng='" + st_ConTotMerIng + "', st_ExiMerSinEgr='" + strExiMerSinEgr + "', st_CreDocEgr='" + strCreDocEgr + "' ";
                        }

                        strSql = " UPDATE tbm_cabsolsaltemmer SET " + strAux + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + "  AND  co_loc=" + txtCodLocRel.getText() + " "
                                + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();

                        stmLoc.executeUpdate(strSql);
                    }

                    if (objZafParSis.getCodigoMenu() == 2073) 
                    {

                        strSql = "select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,  abs(a.nd_can) as nd_can , '' as tx_obs1,  a2.nd_canNunIng as nd_canNunRec, a2.nd_cantoting as nd_cancon "
                                + " ,a1.co_itmact, a2.co_reg, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a2.co_bod, a1.st_meringegrfisbod, abs(a2.nd_can) as nd_canmov "
                                + " from tbm_detingegrmerbod as a "
                                + " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel)  "
                                + " INNER JOIN tbm_detsolsaltemmer AS a2 ON (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrelsolsaltemmer and a2.co_tipdoc=a1.co_tipdocrelsolsaltemmer  "
                                + " and a2.co_doc=a1.co_docrelsolsaltemmer and a2.co_reg=a1.co_regrelsolsaltemmer) "
                                + " WHERE  a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " AND "
                                + " a.co_tipdoc=" + txtCodTipDoc.getText() + " AND a.co_doc=" + intCodDoc;
                        rstLoc = stmLoc.executeQuery(strSql);
                        while (rstLoc.next()) 
                        {
                            java.util.Vector vecReg = new java.util.Vector();
                            vecReg.add(INT_TBL_LINEA, "");
                            vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itmact"));
                            vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                            vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                            vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                            vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_canmov"));
                            vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));
                            vecReg.add(INT_TBL_CANCON, rstLoc.getString("nd_can"));
                            vecReg.add(INT_TBL_CANNUMREC, rstLoc.getString("nd_canNunRec"));
                            vecReg.add(INT_TBL_CANMALEST, "");
                            vecReg.add(INT_TBL_CANTOTNUMREC, "");
                            vecReg.add(INT_TBL_OBSITMCON, rstLoc.getString("tx_obs1"));

                            vecReg.add(INT_TBL_CANCON_ORI, rstLoc.getString("nd_can"));
                            vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                            vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                            vecReg.add(INT_TBL_CANNUMREC_ORI, rstLoc.getString("nd_canNunRec"));
                            vecReg.add(INT_TBL_CANMALEST_ORI, "");
                            vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meringegrfisbod"));
                            vecReg.add(INT_TBL_CANTOTMALEST, "");
                            vecReg.add(INT_TBL_CODEMPREL, null);
                            vecReg.add(INT_TBL_CODLOCREL, null);
                            vecReg.add(INT_TBL_CODTIDOREL, null);
                            vecReg.add(INT_TBL_CODDOCREL, null);
                            vecReg.add(INT_TBL_CODREGREL, null);
                            vecReg.add(INT_TBL_TXNOMBODREL, null);
                            vecReg.add(INT_TBL_CODBODREL, null);

                            dblCan += rstLoc.getDouble("nd_canmov");
                            dblCanConf += rstLoc.getDouble("nd_canCon");
                            dblCanConf += rstLoc.getDouble("nd_canNunRec");
                            dblCanRec += rstLoc.getDouble("nd_canNunRec");
                            vecData.add(vecReg);
                        }
                        objTblMod.setData(vecData);
                        tblDat.setModel(objTblMod);
                        rstLoc.close();
                        rstLoc = null;

                        String strCreDocEgr = "N", strConTotMerEgr = "N", strExiMerSinEgr = "N";
                        String strFec = "", strFecMer = "";

                        strSql = "SELECT case when ndcan = (ndcaning+ndcannuning) then 'T' else case when ndcaning > 0 then 'P' else 'N' end end as stDocegr"
                                + ",case when ndcannuning > 0 then 'S' else 'N' end  as stExiMerSinEgr "
                                + " FROM( "
                                + " select sum(nd_cantotegr) as ndcan, sum(nd_cantoting) as ndcaning, sum(nd_cannuning) as ndcannuning "
                                + " from tbm_detsolsaltemmer where co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + txtCodLocRel.getText() + " "
                                + " and  co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                + " ) AS x ";
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
                            strCreDocEgr = rstLoc.getString("stDocegr");
                            strExiMerSinEgr = rstLoc.getString("stExiMerSinEgr");
                            if (rstLoc.getString("stDocegr").equals("T")) {
                                strConTotMerEgr = "S";
                                stmersaldemfac = "S";
                                strFec = ", fe_CretodDocIng=" + objZafParSis.getFuncionFechaHoraBaseDatos();
                                strFecMer = ", fe_ConTotMerIng=" + objZafParSis.getFuncionFechaHoraBaseDatos();
                            }
                            if (rstLoc.getString("stDocegr").equals("T")) {
                                if (rstLoc.getString("stExiMerSinEgr").equals("N")) {
                                    stmersaldemfac = "N";
                                    stfacmersaldem = "st_facmersaldem='N', ";
                                }
                            }

                        }
                        rstLoc.close();
                        rstLoc = null;

                        strSql = " UPDATE tbm_cabsolsaltemmer SET  " + stfacmersaldem + "  st_mersaldemfac='" + stmersaldemfac + "', st_CreDocIng='" + strCreDocEgr + "' " + strFec + " ,st_ConTotMerIng='" + strConTotMerEgr + "' " + strFecMer + " "
                                + " ,st_ExiMerSinIng='" + strExiMerSinEgr + "' WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + "  AND  co_loc=" + txtCodLocRel.getText() + " "
                                + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();
                        stmLoc.executeUpdate(strSql);
                    }

                    if ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915)) 
                    {

                        strSql = "SELECT  a.nd_cannunrec as cannunrecingegr,  a1.co_itm, a1.co_reg, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, " + STR_COD_BOD_GUIA + " as co_bod,  abs(a1.nd_can) as nd_can, a1.nd_canNunRec "
                                + " ,a1.nd_canCon ,abs(a.nd_can) as canconf , a.tx_obs1, a1.st_meregrfisbod "
                                + " FROM tbm_detingegrmerbod as a "
                                + " INNER JOIN tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelguirem and a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem and a1.co_reg=a.co_regrelguirem  )"
                                + " WHERE  a.co_emp=" + STR_COD_EMP_REL + " AND a.co_loc=" + STR_COD_LOC_REL + " AND "
                                + " a.co_tipdoc=" + txtCodTipDoc.getText() + " AND a.co_doc=" + intCodDoc;
                        rstLoc = stmLoc.executeQuery(strSql);
                        while (rstLoc.next()) 
                        {
                            java.util.Vector vecReg = new java.util.Vector();
                            vecReg.add(INT_TBL_LINEA, "");
                            vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
                            vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                            vecReg.add(INT_TBL_CODALTDOS, null);
                            vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                            vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                            vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_can"));
                            vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_canCon"));
                            vecReg.add(INT_TBL_CANCON, rstLoc.getString("canconf"));
                            vecReg.add(INT_TBL_CANNUMREC, rstLoc.getString("cannunrecingegr"));
                            vecReg.add(INT_TBL_CANMALEST, "");
                            vecReg.add(INT_TBL_CANTOTNUMREC, rstLoc.getString("nd_canNunRec"));
                            vecReg.add(INT_TBL_OBSITMCON, rstLoc.getString("tx_obs1"));
                            vecReg.add(INT_TBL_CANCON_ORI, rstLoc.getString("canconf"));
                            vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                            vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                            vecReg.add(INT_TBL_CANNUMREC_ORI, rstLoc.getString("cannunrecingegr"));
                            vecReg.add(INT_TBL_CANMALEST_ORI, "");
                            vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meregrfisbod"));
                            vecReg.add(INT_TBL_CANTOTMALEST, "");
                            vecReg.add(INT_TBL_CODEMPREL, null);
                            vecReg.add(INT_TBL_CODLOCREL, null);
                            vecReg.add(INT_TBL_CODTIDOREL, null);
                            vecReg.add(INT_TBL_CODDOCREL, null);
                            vecReg.add(INT_TBL_CODREGREL, null);
                            vecReg.add(INT_TBL_TXNOMBODREL, null);
                            vecReg.add(INT_TBL_CODBODREL, null);
                            vecReg.add(INT_TBL_PESKGR, null);
                            vecReg.add(INT_TBL_CODALTAUX, null);
                            vecReg.add(INT_TBL_NOMITMAUX, null);
                            vecReg.add(INT_TBL_CODREGRELDOCEGR,null);
                            vecData.add(vecReg);
                        }
                        objTblMod.setData(vecData);
                        tblDat.setModel(objTblMod);
                        rstLoc.close();
                        rstLoc = null;
                    }

                    if (objZafParSis.getCodigoMenu() == 286) //Rose 07/Mar/2016
                    {
                        strSql =  " SELECT  a.nd_cannunrec as cannunrecingegr,  a1.co_itmact, a1.co_reg, a1.tx_codalt, a2.tx_codalt2, a1.tx_nomitm, a1.tx_unimed, a1.co_bod,  abs(a1.nd_can) as nd_can"
                                + " ,case when a1.nd_canNunRec  is null then 0 else a1.nd_canNunRec end as nd_canNunRec     "
                                + " ,case when a1.nd_canTotMalEst  is null then 0 else a1.nd_canTotMalEst end as nd_canTotMalEst     "
                                + " ,case when a.nd_canMalEst  is null then 0 else a.nd_canMalEst end as nd_canMalEst     "
                                + " ,case when a1.nd_canCon  is null then 0 else a1.nd_canCon end as nd_canCon ,abs(a.nd_can) as canconf , a.tx_obs1, a1.st_meringegrfisbod "
                                + " FROM tbm_detingegrmerbod as a "
                                + " INNER JOIN tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel )"
                                + " INNER JOIN tbm_inv as a2 on (a2.co_Emp=a1.co_Emp and a2.co_itm=a1.co_itmact) "
                                + " WHERE  a.co_emp=" + STR_COD_EMP_REL + " AND a.co_loc=" + STR_COD_LOC_REL + " AND "
                                + " a.co_tipdoc=" + txtCodTipDoc.getText() + " AND a.co_doc=" + intCodDoc;
                        rstLoc = stmLoc.executeQuery(strSql);
                        System.out.println("Rose.cargarDetIns: "+strSql); 
                        while (rstLoc.next()) 
                        {
                            java.util.Vector vecReg = new java.util.Vector();
                            vecReg.add(INT_TBL_LINEA, "");
                            vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itmact"));
                            vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                            //vecReg.add(INT_TBL_CODALTDOS, null); //Antes Rose 07/Mar/2016
                            vecReg.add(INT_TBL_CODALTDOS, rstLoc.getString("tx_codalt2")); //Rose 07/Mar/2016
                            vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                            vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                            vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_can"));
                            vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_canCon"));
                            vecReg.add(INT_TBL_CANCON, rstLoc.getString("canconf"));
                            vecReg.add(INT_TBL_CANNUMREC, rstLoc.getString("cannunrecingegr"));
                            vecReg.add(INT_TBL_CANMALEST, rstLoc.getString("nd_canMalEst"));
                            vecReg.add(INT_TBL_CANTOTNUMREC, rstLoc.getString("nd_canNunRec"));
                            vecReg.add(INT_TBL_OBSITMCON, rstLoc.getString("tx_obs1"));

                            vecReg.add(INT_TBL_CANCON_ORI, rstLoc.getString("canconf"));
                            vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                            vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                            vecReg.add(INT_TBL_CANNUMREC_ORI, rstLoc.getString("cannunrecingegr"));
                            vecReg.add(INT_TBL_CANMALEST_ORI, rstLoc.getString("nd_canMalEst"));
                            vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meringegrfisbod"));
                            vecReg.add(INT_TBL_CANTOTMALEST, rstLoc.getString("nd_canTotMalEst"));
                            vecReg.add(INT_TBL_CODEMPREL, null);
                            vecReg.add(INT_TBL_CODLOCREL, null);
                            vecReg.add(INT_TBL_CODTIDOREL, null);
                            vecReg.add(INT_TBL_CODDOCREL, null);
                            vecReg.add(INT_TBL_CODREGREL, null);
                            vecReg.add(INT_TBL_TXNOMBODREL, null);
                            vecReg.add(INT_TBL_CODBODREL, null);
                            vecReg.add(INT_TBL_PESKGR, null);
                            vecReg.add(INT_TBL_CODALTAUX, null);
                            vecReg.add(INT_TBL_NOMITMAUX, null);
                            vecReg.add(INT_TBL_CODREGRELDOCEGR,null);
                            if (rstLoc.getString("st_meringegrfisbod").equals("S"))
                            {
                                dblCan += rstLoc.getDouble("nd_can");
                                dblCanConf += rstLoc.getDouble("nd_canCon");
                                dblCanConf += rstLoc.getDouble("nd_canNunRec");
                                dblCanConf += rstLoc.getDouble("nd_canTotMalEst");
                            }
                            vecData.add(vecReg);
                        }
                        objTblMod.setData(vecData);
                        tblDat.setModel(objTblMod);
                        rstLoc.close();
                        rstLoc = null;

                        if (strTipIngEgr.equals("A"))
                        {

                            strSql = "SELECT sum(abs(nd_can)) as nd_can, "
                                    + " sum(case when  nd_cancon is null then 0 else nd_cancon end ) as nd_cancon, "
                                    + " sum( case when  nd_cannunrec  is null then 0 else nd_cannunrec end  ) as nd_cannunrec,  "
                                    + " sum( case when  nd_canTotMalEst is null then 0 else nd_canTotMalEst end ) as canTotMalEst  "
                                    + " FROM tbm_detmovinv  "
                                    + " WHERE co_emp=" + STR_COD_EMP_REL + "  AND  co_loc=" + txtCodLocRel.getText() + " "
                                    + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " and nd_can > 0 ";
                            rstLoc = stmLoc.executeQuery(strSql);
                            if (rstLoc.next()) {
                                dblCan = rstLoc.getDouble("nd_can");
                                dblCanConf = rstLoc.getDouble("nd_cancon");
                                dblCanConf += rstLoc.getDouble("nd_cannunrec");
                                dblCanConf += rstLoc.getDouble("canTotMalEst");
                            }
                            rstLoc.close();
                            rstLoc = null;

                            if (dblCan == dblCanConf) {
                                strEstCon = "C";
                            }
                            strSql = " UPDATE tbm_cabmovinv SET st_coninv='" + strEstCon + "', st_autingegrinvcon='N' "
                                    + " WHERE co_emp=" + STR_COD_EMP_REL + "  AND  co_loc=" + txtCodLocRel.getText() + " "
                                    + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();
                            stmLoc.executeUpdate(strSql);
                        } else {
                            if (dblCan == dblCanConf) {
                                strEstCon = "C";
                            }

                            strEstCon = objInvItm._getEstConfirmacionIng(conn, STR_COD_EMP_REL, txtCodLocRel.getText(), txtCodTipDocCon.getText(), txtCodDocCon.getText());
                            strSql = " UPDATE tbm_cabmovinv SET st_coninv='" + strEstCon + "', st_autingegrinvcon='N' "
                                    + " WHERE co_emp=" + STR_COD_EMP_REL + "  AND  co_loc=" + txtCodLocRel.getText() + " "
                                    + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();
                            stmLoc.executeUpdate(strSql);
                        }

                    }
                    if (objZafParSis.getCodigoMenu() == 1974)
                    {
                        strSql = "SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,  abs(a.nd_can) as nd_can, a.tx_obs1, a1.nd_canNunRec, a1.nd_cancon ,"
                                + " a1.co_itm, a1.co_reg, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, " + STR_COD_BOD_GUIA + " as co_bod , a1.st_meregrfisbod as st_meringegrfisbod "
                                + " , abs(a1.nd_can) as nd_canmov "
                                + " FROM tbm_detingegrmerbod AS a "
                                + " INNER JOIN tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelguirem and a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem and a1.co_reg=a.co_regrelguirem  )"
                                + " WHERE  a.co_emp=" + STR_COD_EMP_REL + " AND a.co_loc=" + STR_COD_LOC_REL + " AND "
                                + " a.co_tipdoc=" + txtCodTipDoc.getText() + " AND a.co_doc=" + intCodDoc;
                        rstLoc = stmLoc.executeQuery(strSql);
                        while (rstLoc.next()) 
                        {
                            java.util.Vector vecReg = new java.util.Vector();
                            vecReg.add(INT_TBL_LINEA, "");
                            vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
                            vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                            vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                            vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                            vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_canmov"));
                            vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));
                            vecReg.add(INT_TBL_CANCON, rstLoc.getString("nd_can"));
                            vecReg.add(INT_TBL_CANNUMREC, rstLoc.getString("nd_canNunRec"));
                            vecReg.add(INT_TBL_CANMALEST, "");
                            vecReg.add(INT_TBL_CANTOTNUMREC, "");
                            vecReg.add(INT_TBL_OBSITMCON, rstLoc.getString("tx_obs1"));

                            vecReg.add(INT_TBL_CANCON_ORI, rstLoc.getString("nd_can"));
                            vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                            vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                            vecReg.add(INT_TBL_CANNUMREC_ORI, rstLoc.getString("nd_canNunRec"));
                            vecReg.add(INT_TBL_CANMALEST_ORI, "");
                            vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meringegrfisbod"));
                            vecReg.add(INT_TBL_CANTOTMALEST, "");
                            vecReg.add(INT_TBL_CODEMPREL, null);
                            vecReg.add(INT_TBL_CODLOCREL, null);
                            vecReg.add(INT_TBL_CODTIDOREL, null);
                            vecReg.add(INT_TBL_CODDOCREL, null);
                            vecReg.add(INT_TBL_CODREGREL, null);
                            vecReg.add(INT_TBL_TXNOMBODREL, null);
                            vecReg.add(INT_TBL_CODBODREL, null);

                            dblCan += rstLoc.getDouble("nd_canmov");
                            dblCanConf += rstLoc.getDouble("nd_cancon");
                            dblCanConf += rstLoc.getDouble("nd_canNunRec");

                            vecData.add(vecReg);
                        }
                        objTblMod.setData(vecData);
                        tblDat.setModel(objTblMod);
                        rstLoc.close();
                        rstLoc = null;

                        if (dblCan == dblCanConf) {
                            strEstCon = "C";
                        }

                        strSql = " UPDATE tbm_cabguirem SET st_coninv='" + strEstCon + "'  "
                                + " WHERE co_emp=" + STR_COD_EMP_REL + "  AND  co_loc=" + txtCodLocRel.getText() + " "
                                + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText();

                        stmLoc.executeUpdate(strSql);

                    }

                    conn.commit();

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

        private boolean genera_Trans(int intCodCot, java.sql.Connection con_remota, java.sql.Connection con_local, String strTipDoc, String strCodDoc) 
        {
            boolean blnRes = false;
            int intVal = 0;
            try
            {

                StringBuffer stb = new StringBuffer();
                blnGloInsDat = false;

                for (int i = 0; i < tblDat.getRowCount(); i++)
                {
                    double dlbCan = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANCON)), 4);
                    if (dlbCan > 0) 
                    {
                        if (intVal > 0) {
                            stb.append(" UNION ALL ");
                        }
                        stb.append("SELECT " + objZafParSis.getCodigoEmpresa() + " AS coemp ," + txtCodLocRel.getText() + " as coloc,"
                                + " " + txtCodTipDocCon.getText() + " as cotipdoc, " + txtCodDocCon.getText() + " as codoc, "
                                + " " + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + " as coreg, "
                                + " " + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + " as coitm , " + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " as cobod, " + dlbCan + " as ndcan ");
                        intVal = 1;
                    }
                }
                if (intVal > 0) 
                {
                    blnGloInsDat = true;
                    Compras.ZafCom19.ZafCom19_TRA obj1;
                    obj1 = new Compras.ZafCom19.ZafCom19_TRA(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis, intCodCot, 779, con_remota, con_local, Integer.parseInt(strTipDoc), Integer.parseInt(strCodDoc), stb, 1, Integer.parseInt(txtCodTipDocCon.getText()));
                    obj1.show();
                    if (obj1.acepta()) 
                    {

                        STRCODEMPTRANS = obj1.GetCamSel(1);
                        STRCODLOCTRANS = obj1.GetCamSel(2);
                        STRCODTIPTRANS = obj1.GetCamSel(3);
                        STRCODDOCTRANS = obj1.GetCamSel(4);

                        blnRes = true;
                    }
                    obj1.dispose();
                    obj1 = null;
                    stb = null;
                }

            } catch (Exception e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
        }

        private boolean genera_Trans_act(int intCodCot, java.sql.Connection con_remota, java.sql.Connection con_local, String strTipDoc, String strCodDoc) 
        {
            boolean blnRes = false;
            int intVal = 0;
            try
            {

                if (objUltDocPrint.verificarsiesconfirmado(con_local, STRCODEMPTRANS, STRCODLOCTRANS, STRCODTIPTRANS, STRCODDOCTRANS))
                {

                    StringBuffer stb = new StringBuffer();

                    for (int i = 0; i < tblDat.getRowCount(); i++) 
                    {
                        double dlbCan = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANCON)), 4);
                        if (intVal > 0) {
                            stb.append(" UNION ALL ");
                        }
                        stb.append("SELECT " + objZafParSis.getCodigoEmpresa() + " AS coemp ," + txtCodLocRel.getText() + " as coloc,"
                                + " " + txtCodTipDocCon.getText() + " as cotipdoc, " + txtCodDocCon.getText() + " as codoc, "
                                + " " + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + " as coreg, "
                                + " " + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + " as coitm , " + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " as cobod, " + dlbCan + " as ndcan ");
                        intVal = 1;

                    }

                    if (intVal > 0)
                    {
                        Compras.ZafCom19.ZafCom19_TRA obj1;
                        obj1 = new Compras.ZafCom19.ZafCom19_TRA(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis, intCodCot, 779, con_remota, con_local, Integer.parseInt(strTipDoc), Integer.parseInt(strCodDoc), stb, 2, STRCODEMPTRANS, STRCODLOCTRANS, STRCODTIPTRANS, STRCODDOCTRANS, Integer.parseInt(txtCodTipDocCon.getText()));
                        obj1.show();
                        if (obj1.acepta()) {

                            blnRes = true;
                        }
                        obj1.dispose();
                        obj1 = null;
                        stb = null;
                    } else {
                        blnRes = true;
                    }

                }

            } catch (Exception e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
        }

        private boolean insertarCab(java.sql.Connection conn, int intCodDoc, int intNumDoc) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try 
            {
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();

                    //************  PERMITE SABER SI EL NUMERO  ESTA DUPLICADO  *****************/
                    if (!((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915) || (objZafParSis.getCodigoMenu() == 286) || (objZafParSis.getCodigoMenu() == 1974))) {
                        strSql = "select count(ne_numdoc) as num from tbm_cabingegrmerbod WHERE "
                                + " co_emp=" + STR_COD_EMP_REL + " and co_loc=" + STR_COD_LOC_REL + " "
                                + "and co_tipdoc=" + txtCodTipDoc.getText() + " and ne_numdoc=" + txtNumDoc.getText();
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
                            if (rstLoc.getInt("num") >= 1) {
                                //JOptionPane oppMsg = new JOptionPane();
                                String strTit, strMsg;
                                strTit = "Mensaje del sistema Zafiro";
                                strMsg = " No. de Confitmación ya existe... ?";
                                JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE, null);
                                blnRes = true;
                            }
                        }
                        rstLoc.close();
                        rstLoc = null;
                        if (blnRes) {
                            stmLoc.close();
                            stmLoc = null;
                            return false;
                        }
                        blnRes = false;
                    }
                    //***********************************************************************************************/

                    if (objZafParSis.getCodigoMenu() == 286) {

                        if (ingCabConfIng(conn, intCodDoc, intNumDoc)) {
                            blnRes = true;
                        }

                    } else if ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915)) {

                        if (ingCabConfEgr(conn, intCodDoc, intNumDoc)) {
                            blnRes = true;
                        }

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

        private boolean actEstConfEgr(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, boolean blnEstActConf) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc, stmLoc01;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try
            {
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();
                    stmLoc01 = conn.createStatement();

                    strSql = "SELECT  *   "
                            + ",case when cantotconfguia = 0 then 'P' else case when cantotconfguia=nd_canguia then 'C' else 'E' end end as stEstConfguia  "
                            + ",case when cantotconfegr = 0 then  '' else case when cantotconfegr=nd_canguia then 'T' else 'P' end end as stTipConf   "
                            + "FROM (  "
                            + "select a.co_emp, a.co_locrelguirem, a.co_tipdocrelguirem, a.co_docrelguirem, sum(abs(a.nd_can)) as cantotconfegr    "
                            + ",(  "
                            + "select sum(abs( x.nd_can)) from tbm_detguirem as x where x.co_emp=a.co_emp and x.co_loc=a.co_locrelguirem and x.co_tipdoc= a.co_tipdocrelguirem and x.co_doc= a.co_docrelguirem  "
                            + "and x.st_meregrfisbod='S'  "
                            + ")  as  nd_canguia  "
                            + ",(  "
                            + "select sum((abs( x.nd_canCon)+abs( x.nd_canNunRec)))  from tbm_detguirem as x where x.co_emp=a.co_emp and x.co_loc=a.co_locrelguirem and x.co_tipdoc= a.co_tipdocrelguirem and x.co_doc= a.co_docrelguirem  "
                            + "and x.st_meregrfisbod='S'  "
                            + ")  as  cantotconfguia  "
                            + "FROM tbm_detingegrmerbod as a    "
                            + "WHERE  a.co_emp=" + intCodEmp + " AND a.co_loc=" + intCodLoc + " AND    "
                            + "a.co_tipdoc=" + intCodTipDoc + " AND a.co_doc=" + intCodDoc + "   "
                            + "GROUP BY a.co_emp, a.co_locrelguirem, a.co_tipdocrelguirem, a.co_docrelguirem  "
                            + ") AS x   ";

                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) 
                    {

                        strSql = " UPDATE tbm_cabguirem SET st_coninv='" + rstLoc.getString("stEstConfguia") + "'  "
                                + " WHERE co_emp=" + rstLoc.getInt("co_emp") + " AND co_loc=" + rstLoc.getInt("co_locrelguirem") + " "
                                + " and co_tipdoc=" + rstLoc.getInt("co_tipdocrelguirem") + " and co_doc=" + rstLoc.getInt("co_docrelguirem");
                        if (blnEstActConf) {
                            strSql += " ; UPDATE tbm_cabingegrmerbod SET tx_tipCon='" + rstLoc.getString("stTipConf") + "'  "
                                    + " WHERE co_emp=" + intCodEmp + "  AND  co_loc=" + intCodLoc + " "
                                    + " and co_tipdoc=" + intCodTipDoc + " and co_doc=" + intCodDoc;
                        }
                        stmLoc01.executeUpdate(strSql);
                        blnRes = true;
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                    stmLoc01.close();
                    stmLoc01 = null;

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

        private boolean ingCabConfEgr(java.sql.Connection conn, int intCodDoc, int intNumDoc) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSql = "";
            String strFecDoc = "";
            try 
            {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";

                    strSql = "";
                    strSql += "INSERT INTO tbm_cabingegrmerbod( co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, ";
                    strSql += " co_locrelguirem, co_tipdocrelguirem, co_docrelguirem, co_mnu, st_imp, tx_obs1, tx_obs2, st_reg, fe_ing, co_usring, ";
                    strSql += " co_veh, co_cho, tx_idetra, tx_nomtra, tx_plavehtra ) ";
                    strSql += " VALUES( " + STR_COD_EMP_REL + ", " + STR_COD_LOC_REL + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " ";
                    strSql += " ,'" + strFecDoc + "', " + intNumDoc + ", " + STR_COD_BOD_GUIA + ", ";
                    strSql += " " + txtCodLocRel.getText() + ", " + txtCodTipDocCon.getText() + ", " + txtCodDocCon.getText() + ", ";
                    strSql += " " + objZafParSis.getCodigoMenu() + ", 'N', '" + txtObs1.getText() + "', '[Ing.Maq]" + txtObs2.getText() + "', ";
                    strSql += " 'A', " + objZafParSis.getFuncionFechaHoraBaseDatos() + ", " + objZafParSis.getCodigoUsuario() + ", ";
                    strSql += " " + (txtCodVeh.getText() == null ? null : (txtCodVeh.getText().equals("") ? null : "" + txtCodVeh.getText() + "")) + ", ";
                    strSql += " " + (txtCodCho.getText() == null ? null : (txtCodCho.getText().equals("") ? null : "" + txtCodCho.getText() + "")) + ", '";

                    if (optEnv.isSelected()) {
                        strSql += "" + txtIdeCho.getText().trim() + "', '" + txtNomCho.getText() + "', '";
                    } else if (optRet.isSelected()) {
                        strSql += "" + txtIdeTra.getText().trim() + "', '" + txtNomTra.getText() + "', '";
                    }

                    strSql += " " + txtPlaVehTra.getText() + "' )";
                    //System.out.println("ERROR: " + strSql);
                    strSql += " ; UPDATE tbm_cabtipdoc SET ne_ultdoc=" + intNumDoc + " WHERE co_emp=" + STR_COD_EMP_REL + " "
                            + " AND co_loc=" + STR_COD_LOC_REL + " AND co_tipdoc=" + txtCodTipDoc.getText();
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

        private boolean ingCabConfIng(java.sql.Connection conn, int intCodDoc, int intNumDoc) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSql = "";
            String strFecDoc = "";
            try 
            {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strFecDoc = "#" + txtFecDoc.getFecha("cl/", "y/m/d") + "#";

                    //José Marín 18/Dic/2014 - Se cambia el metodo para tomar la fecha 
                    strSql = "INSERT INTO tbm_cabingegrmerbod( co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, "
                            + " co_locrel, co_tipdocrel, co_docrel, co_mnu, st_imp, tx_obs1, tx_obs2, st_reg, fe_ing, co_usring ) "
                            + " VALUES( " + STR_COD_EMP_REL + ", " + STR_COD_LOC_REL + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + ","
                            + " '" + objUti.formatearFecha(objZafParSis.getFechaHoraServidorIngresarSistema(), objZafParSis.getFormatoFechaBaseDatos()) + "', " + intNumDoc + ", " + STR_COD_BOD_GUIA + ", "
                            + " " + txtCodLocRel.getText() + ", " + txtCodTipDocCon.getText() + ", " + txtCodDocCon.getText() + ", "
                            + " " + objZafParSis.getCodigoMenu() + ", 'N', '" + txtObs1.getText() + "', '[Ing.Maq]" + txtObs2.getText() + "', "
                            + " 'A', '" + objUti.formatearFecha(objZafParSis.getFechaHoraServidorIngresarSistema(), objZafParSis.getFormatoFechaHoraBaseDatos()) + "', " + objZafParSis.getCodigoUsuario() + " )";
                    //José Marín 18/Dic/2014 - Se cambia el metodo para tomar la fecha 
                    strSql += " ; UPDATE tbm_cabtipdoc SET ne_ultdoc=" + intNumDoc + " WHERE co_emp=" + STR_COD_EMP_REL + " "
                            + " AND co_loc=" + STR_COD_LOC_REL + " AND co_tipdoc=" + txtCodTipDoc.getText();
                    // System.out.println("ingCabConfIng ====> " + strSql);
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

        private boolean insertarCab_respaldo(java.sql.Connection conn, int intCodDoc, int intNumDoc) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            String strFecDoc = "";
            String strFecSis = "";
            try 
            {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    //************  PERMITE SABER SI EL NUMERO DE Devolucion ESTA DUPLICADO  *****************/
                    if (!((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 286) || (objZafParSis.getCodigoMenu() == 1974))) {
                        strSql = "select count(ne_numdoc) as num from tbm_cabingegrmerbod WHERE "
                                + " co_emp=" + STR_COD_EMP_REL + " and co_loc=" + STR_COD_LOC_REL + " "
                                + "and co_tipdoc=" + txtCodTipDoc.getText() + " and ne_numdoc=" + txtNumDoc.getText();
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
                            if (rstLoc.getInt("num") >= 1) {
                                //JOptionPane oppMsg = new JOptionPane();
                                String strTit, strMsg;
                                strTit = "Mensaje del sistema Zafiro";
                                strMsg = " No. de Confitmación ya existe... ?";
                                JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE, null);
                                blnRes = true;
                            }
                        }
                        rstLoc.close();
                        rstLoc = null;
                        if (blnRes) {
                            stmLoc.close();
                            stmLoc = null;
                            return false;
                        }
                        blnRes = false;
                    }
                    //***********************************************************************************************/

                    strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";
                    strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());

                    if (objZafParSis.getCodigoMenu() == 2063) 
                    {

                        if (genera_Trans(0, conRemGlo, conn, txtCodTipDocCon.getText(), txtCodDocCon.getText())) {

                            strSql = "INSERT INTO tbm_cabingegrmerbod("
                                    + " co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, "
                                    + " co_locrel, co_tipdocrel, co_docrel, co_mnu, st_imp, tx_obs1, "
                                    + " tx_obs2, st_reg, fe_ing, co_usring ) "
                                    + " VALUES( "
                                    + " " + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                    + " ,'" + strFecDoc + "', " + txtNumDoc.getText() + ", " + STR_COD_BOD_GUIA + ", "
                                    + " " + STRCODLOCTRANS + ", " + STRCODTIPTRANS + ", " + STRCODDOCTRANS + ", "
                                    + " " + objZafParSis.getCodigoMenu() + ", 'N', '" + txtObs1.getText() + "', '[Ing.Maq]" + txtObs2.getText() + "', "
                                    + " 'A', '" + strFecSis + "', " + objZafParSis.getCodigoUsuario() + " )";
                            stmLoc.executeUpdate(strSql);

                            strSql = "INSERT INTO tbr_cabSolSalTemMerCabMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_locrel, co_tipdocrel, co_docrel, tx_tiprel )"
                                    + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + txtCodLocRel.getText() + ", " + txtCodTipDocCon.getText() + ", " + txtCodDocCon.getText() + " "
                                    + " ," + STRCODLOCTRANS + ", " + STRCODTIPTRANS + " , " + STRCODDOCTRANS + ", 'E' ) ";
                            stmLoc.executeUpdate(strSql);

                            strSql = "UPDATE tbm_cabtipdoc SET ne_ultdoc=" + txtNumDoc.getText() + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                    + " AND co_loc=" + objZafParSis.getCodigoLocal() + " AND co_tipdoc=" + txtCodTipDoc.getText();
                            stmLoc.executeUpdate(strSql);
                            blnRes = true;

                        } 
                        else 
                        {
                            if (!blnGloInsDat) {
                                blnRes = true;
                            }
                        }

                    } else if (objZafParSis.getCodigoMenu() == 2073) {

                        if (genera_Trans(0, conRemGlo, conn, txtCodTipDocCon.getText(), txtCodDocCon.getText())) {

                            strSql = "INSERT INTO tbm_cabingegrmerbod("
                                    + " co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, "
                                    + " co_locrel, co_tipdocrel, co_docrel, co_mnu, st_imp, tx_obs1, "
                                    + " tx_obs2, st_reg, fe_ing, co_usring ) "
                                    + " VALUES( "
                                    + " " + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                    + " ,'" + strFecDoc + "', " + txtNumDoc.getText() + ", " + STR_COD_BOD_GUIA + ", "
                                    + " " + STRCODLOCTRANS + ", " + STRCODTIPTRANS + ", " + STRCODDOCTRANS + ", "
                                    + " " + objZafParSis.getCodigoMenu() + ", 'N', '" + txtObs1.getText() + "', '[Ing.Maq]" + txtObs2.getText() + "', "
                                    + " 'A', '" + strFecSis + "', " + objZafParSis.getCodigoUsuario() + " )";
                            stmLoc.executeUpdate(strSql);

                            strSql = "INSERT INTO tbr_cabSolSalTemMerCabMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_locrel, co_tipdocrel, co_docrel, tx_tiprel )"
                                    + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + txtCodLocRel.getText() + ", " + txtCodTipDocCon.getText() + ", " + txtCodDocCon.getText() + " "
                                    + " ," + STRCODLOCTRANS + ", " + STRCODTIPTRANS + " , " + STRCODDOCTRANS + ", 'I' ) ";
                            stmLoc.executeUpdate(strSql);

                            strSql = "UPDATE tbm_cabtipdoc SET ne_ultdoc=" + txtNumDoc.getText() + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                                    + " AND co_loc=" + objZafParSis.getCodigoLocal() + " AND co_tipdoc=" + txtCodTipDoc.getText();
                            stmLoc.executeUpdate(strSql);
                            blnRes = true;

                        } else {
                            blnRes = false;
                        }

                    } 
                    else 
                    {

                        if (objZafParSis.getCodigoMenu() == 2205)
                        {

                            strSql = "INSERT INTO tbm_cabingegrmerbod("
                                    + " co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, "
                                    + " co_locrelguirem, co_tipdocrelguirem, co_docrelguirem, co_mnu, st_imp, tx_obs1, "
                                    + " tx_obs2, st_reg, fe_ing, co_usring ) "
                                    + " VALUES( "
                                    + " " + STR_COD_EMP_REL + ", " + STR_COD_LOC_REL + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                    + " ,'" + strFecDoc + "', " + intNumDoc + ", " + STR_COD_BOD_GUIA + ", "
                                    + " " + txtCodLocRel.getText() + ", " + txtCodTipDocCon.getText() + ", " + txtCodDocCon.getText() + ", "
                                    + " " + objZafParSis.getCodigoMenu() + ", 'N', '" + txtObs1.getText() + "', '[Ing.Maq]" + txtObs2.getText() + "', "
                                    + " 'A', '" + strFecSis + "', " + objZafParSis.getCodigoUsuario() + " )";
                            stmLoc.executeUpdate(strSql);

                            strSql = "UPDATE tbm_cabtipdoc SET ne_ultdoc=" + intNumDoc + " WHERE co_emp=" + STR_COD_EMP_REL + " "
                                    + " AND co_loc=" + STR_COD_LOC_REL + " AND co_tipdoc=" + txtCodTipDoc.getText();
                            stmLoc.executeUpdate(strSql);
                            blnRes = true;

                        }
                        else if (objZafParSis.getCodigoMenu() == 1974)
                        {

                            strSql = "INSERT INTO tbm_cabingegrmerbod("
                                    + " co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, "
                                    + " co_locrelguirem, co_tipdocrelguirem, co_docrelguirem, co_mnu, st_imp, tx_obs1, "
                                    + " tx_obs2, st_reg, fe_ing, co_usring ) "
                                    + " VALUES( "
                                    + " " + STR_COD_EMP_REL + ", " + STR_COD_LOC_REL + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                    + " ,'" + strFecDoc + "', " + intNumDoc + ", " + STR_COD_BOD_GUIA + ", "
                                    + " " + txtCodLocRel.getText() + ", " + txtCodTipDocCon.getText() + ", " + txtCodDocCon.getText() + ", "
                                    + " " + objZafParSis.getCodigoMenu() + ", 'N', '" + txtObs1.getText() + "', '[Ing.Maq]" + txtObs2.getText() + "', "
                                    + " 'A', '" + strFecSis + "', " + objZafParSis.getCodigoUsuario() + " )";
                            stmLoc.executeUpdate(strSql);

                            strSql = "UPDATE tbm_cabtipdoc SET ne_ultdoc=" + intNumDoc + " WHERE co_emp=" + STR_COD_EMP_REL + " "
                                    + " AND co_loc=" + STR_COD_LOC_REL + " AND co_tipdoc=" + txtCodTipDoc.getText();
                            stmLoc.executeUpdate(strSql);
                            blnRes = true;

                        } 
                        else
                        {

                            strSql = "INSERT INTO tbm_cabingegrmerbod("
                                    + " co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, "
                                    + " co_locrel, co_tipdocrel, co_docrel, co_mnu, st_imp, tx_obs1, "
                                    + " tx_obs2, st_reg, fe_ing, co_usring ) "
                                    + " VALUES( "
                                    + " " + STR_COD_EMP_REL + ", " + STR_COD_LOC_REL + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                    + " ,'" + strFecDoc + "', " + intNumDoc + ", " + STR_COD_BOD_GUIA + ", "
                                    + " " + txtCodLocRel.getText() + ", " + txtCodTipDocCon.getText() + ", " + txtCodDocCon.getText() + ", "
                                    + " " + objZafParSis.getCodigoMenu() + ", 'N', '" + txtObs1.getText() + "', '[Ing.Maq]" + txtObs2.getText() + "', "
                                    + " 'A', '" + strFecSis + "', " + objZafParSis.getCodigoUsuario() + " )";
                            stmLoc.executeUpdate(strSql);

                            strSql = "UPDATE tbm_cabtipdoc SET ne_ultdoc=" + intNumDoc + " WHERE co_emp=" + STR_COD_EMP_REL + " "
                                    + " AND co_loc=" + STR_COD_LOC_REL + " AND co_tipdoc=" + txtCodTipDoc.getText();
                            stmLoc.executeUpdate(strSql);
                            blnRes = true;
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

        private boolean _getVerificaSiHayConfirmacion(java.sql.Connection conn)
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rst;
            String strSql = "";
            try 
            {
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();

                    if ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915))
                    {
                        strSql = "select co_itm, tx_codalt,  nd_can, nd_cancon from tbm_detguirem "
                                + " WHERE  co_emp=" + STR_COD_EMP_REL + " and co_loc=" + txtCodLocRel.getText() + " "
                                + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " AND nd_cancon > nd_can ";
                        rst = stmLoc.executeQuery(strSql);
                        if (rst.next()) {
                            blnRes = true;
                            MensajeInf(" EL ITEM " + rst.getString("tx_codalt") + " YA TIENE CANTIDAD CONFIRMADA..");
                        }
                        rst.close();
                        rst = null;
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
        boolean blnEstCanNunEnv = false;
        boolean blnEstCanConCliRet = false;
        String strItmConCanNunEnv = "";
        java.util.ArrayList arlItmRec;

        private boolean verificaCanNunEnv(java.sql.Connection conn)
        {
            boolean blnRes = true;
            double dlbCanNumEnv = 0;
            double dlbCanConf = 0;
            String strCodEmpRelIng = "", strCodLocRelIng = "", strCodTipDocRelIng = "", strCodDocRelIng = "", strCodRegRelIng = "", strNomBodRel = "", strCodBodRel = "";
            String strCodItm = "", strCodItmMae = "";
            java.util.ArrayList arlReg;
            try 
            {
                if (conn != null) 
                {

                    blnEstCanNunEnv = false;
                    blnEstCanConCliRet = false;
                    strItmConCanNunEnv = "";

                    if ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915)) {   // CONFIRMACION EGRESO

                        strItmConCanNunEnv = "<table border=1><tr> <td> CODIGO </TD> <TD> DESCRIPCION </TD>  <TD> CANTIDAD </TD> </TR>";
                        for (int i = 0; i < tblDat.getRowCount(); i++) {

                            dlbCanNumEnv = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANNUMREC)), 4);
                            if (dlbCanNumEnv > 0) {

                                if (tblDat.getValueAt(i, INT_TBL_CODEMPREL) != null) {

                                    strCodItm = objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_CODITM));
                                    strCodItmMae = _getCodItmMeestro(conn, Integer.parseInt(STR_COD_EMP_REL), strCodItm);
                                    arlReg = new java.util.ArrayList();
                                    arlReg.add(0, strCodItmMae);
                                    arlItmRec.add(arlReg);

                                    blnEstCanNunEnv = true;

//              strItmConCanNunEnv+="<tr> <td>"+tblDat.getValueAt(i, INT_TBL_CODALT).toString()+" </td><td>   "+tblDat.getValueAt(i, INT_TBL_NOMITM).toString()+" </td><td>  "+dlbCanNumEnv+" </td></tr> ";
                                    strItmConCanNunEnv += "<tr> <td>" + tblDat.getValueAt(i, INT_TBL_CODALTAUX).toString() + " </td><td>   " + tblDat.getValueAt(i, INT_TBL_NOMITMAUX).toString() + " </td><td>  " + dlbCanNumEnv + " </td></tr> ";

                                    strCodEmpRelIng = tblDat.getValueAt(i, INT_TBL_CODEMPREL).toString();
                                    strCodLocRelIng = tblDat.getValueAt(i, INT_TBL_CODLOCREL).toString();
                                    strCodTipDocRelIng = tblDat.getValueAt(i, INT_TBL_CODTIDOREL).toString();
                                    strCodDocRelIng = tblDat.getValueAt(i, INT_TBL_CODDOCREL).toString();
                                    strCodRegRelIng = tblDat.getValueAt(i, INT_TBL_CODREGREL).toString();
                                    strCodBodRel = tblDat.getValueAt(i, INT_TBL_CODBODREL).toString();

                                    if (!objInvItm._getVerificaConfirmacionIng(conn, strCodEmpRelIng, strCodLocRelIng, strCodTipDocRelIng, strCodDocRelIng, strCodRegRelIng, dlbCanNumEnv, strCodBodRel, 0)) {
                                        blnRes = false;
                                        blnEstCanNunEnv = false;
                                        MensajeInf("EL INGRESO DE ESTA MERCADERIA HA SIDO CONFIRMADO \n NO SE PUEDE INSERTAR CANTIDAD NUNCA ENVIADA TIENE ANULAR EL INGRESO.  ");
                                        break;
                                    }
                                }

                            }
                        }
                        strItmConCanNunEnv += "</table>";
                    }

                    if ((objZafParSis.getCodigoMenu() == 2915)) {   // CONFIRMACION EGRESO

                        strItmConCanNunEnv = "<table border=1><tr> <td> CODIGO </TD> <TD> DESCRIPCION </TD>  <TD> CANTIDAD </TD> </TR>";
                        for (int i = 0; i < tblDat.getRowCount(); i++) {

                            dlbCanConf = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANCON)), 4);
                            if (dlbCanConf > 0) {

                                if (tblDat.getValueAt(i, INT_TBL_CODEMPREL) != null) {

                                    strCodItm = objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_CODITM));
                                    strCodItmMae = _getCodItmMeestro(conn, Integer.parseInt(STR_COD_EMP_REL), strCodItm);
                                    arlReg = new java.util.ArrayList();
                                    arlReg.add(0, strCodItmMae);
                                    arlItmRec.add(arlReg);

                                    blnEstCanConCliRet = true;

//              strItmConCanNunEnv+="<tr> <td>"+tblDat.getValueAt(i, INT_TBL_CODALT).toString()+" </td><td>   "+tblDat.getValueAt(i, INT_TBL_NOMITM).toString()+" </td><td>  "+dlbCanConf+" </td></tr> ";
                                    strItmConCanNunEnv += "<tr> <td>" + tblDat.getValueAt(i, INT_TBL_CODALTAUX).toString() + " </td><td>   " + tblDat.getValueAt(i, INT_TBL_NOMITMAUX).toString() + " </td><td>  " + dlbCanConf + " </td></tr> ";

                                    strCodEmpRelIng = tblDat.getValueAt(i, INT_TBL_CODEMPREL).toString();
                                    strCodLocRelIng = tblDat.getValueAt(i, INT_TBL_CODLOCREL).toString();
                                    strCodTipDocRelIng = tblDat.getValueAt(i, INT_TBL_CODTIDOREL).toString();
                                    strCodDocRelIng = tblDat.getValueAt(i, INT_TBL_CODDOCREL).toString();
                                    strCodRegRelIng = tblDat.getValueAt(i, INT_TBL_CODREGREL).toString();
                                    strCodBodRel = tblDat.getValueAt(i, INT_TBL_CODBODREL).toString();

                                    if (objInvItm._getVerificaConfirmacionIng(conn, strCodEmpRelIng, strCodLocRelIng, strCodTipDocRelIng, strCodDocRelIng, strCodRegRelIng, 0, strCodBodRel, dlbCanConf)) {
                                        if (objInvItm._getVerificaConfirmacionEgr(conn, strCodEmpRelIng, strCodLocRelIng, strCodTipDocRelIng, strCodDocRelIng, strCodRegRelIng, 0, strCodBodRel, dlbCanConf)) {
                                        } else {
                                            blnRes = false;
                                            blnEstCanConCliRet = false;
                                            MensajeInf("EL EGRESO DE ESTA MERCADERIA HA SIDO CONFIRMADO \n NO SE PUEDE INSERTAR CANTIDAD YA ESTA CONFIRMADO EL EGRESO TIENE ANULAR EL EGRESO.  ");
                                            break;
                                        }

                                    } else {
                                        blnRes = false;
                                        blnEstCanConCliRet = false;
                                        MensajeInf("EL INGRESO DE ESTA MERCADERIA HA SIDO CONFIRMADO \n NO SE PUEDE INSERTAR CANTIDAD YA ESTA CONFIRMADO EL INGRESO TIENE ANULAR EL INGRESO.  ");
                                        break;
                                    }

                                }

                            }
                        }
                        strItmConCanNunEnv += "</table>";
                    }

                    if (objZafParSis.getCodigoMenu() == 286) {   // CONFIRMACION INGRESO

                        strItmConCanNunEnv = "<table border=1><tr> <td> BODEGA </td>  <td> CODIGO </TD> <TD> DESCRIPCION </TD>  <TD> CANTIDAD </TD> </TR>";
                        for (int i = 0; i < tblDat.getRowCount(); i++) {

                            dlbCanNumEnv = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANNUMREC)), 4);

                            if (dlbCanNumEnv > 0) {

                                if (tblDat.getValueAt(i, INT_TBL_CODEMPREL) != null) {

                                    blnEstCanNunEnv = true;

//              strCodEmpRelIng = tblDat.getValueAt(i, INT_TBL_CODEMPREL).toString();
//              strCodLocRelIng = tblDat.getValueAt(i, INT_TBL_CODLOCREL).toString();
//              strCodTipDocRelIng=tblDat.getValueAt(i, INT_TBL_CODTIDOREL).toString();
//              strCodDocRelIng = tblDat.getValueAt(i, INT_TBL_CODDOCREL).toString();
//              strCodRegRelIng = tblDat.getValueAt(i, INT_TBL_CODREGREL).toString();
                                    strNomBodRel = tblDat.getValueAt(i, INT_TBL_TXNOMBODREL).toString();

                                    strItmConCanNunEnv += "<tr> <td> " + strNomBodRel + " </td> <td>" + tblDat.getValueAt(i, INT_TBL_CODALT).toString() + " </td><td>   " + tblDat.getValueAt(i, INT_TBL_NOMITM).toString() + " </td><td>  " + dlbCanNumEnv + " </td></tr> ";

                                }

                            }
                        }
                        strItmConCanNunEnv += "</table>";
                    }

                }
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean verificaCanConIngRel(java.sql.Connection conn) 
        {
            boolean blnRes = true;
            double dlbCanNumEnv = 0;
            double dlbCanConf = 0;
            String strCodEmpRelIng = "", strCodLocRelIng = "", strCodTipDocRelIng = "", strCodDocRelIng = "", strCodRegRelIng = "", strCodBodRel = "";
            String strSql = "";
            java.sql.Statement stmLoc01, stmLoc02;
            java.sql.ResultSet rstLoc01, rstLoc02;

            try 
            {
                if (conn != null) 
                {
                    stmLoc01 = conn.createStatement();
                    stmLoc02 = conn.createStatement();

                    if (objZafParSis.getCodigoMenu() == 2205) {   // CONFIRMACION EGRESO

                        for (int i = 0; i < tblDat.getRowCount(); i++) {
                            dlbCanConf = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANCON)), 4);

                            if (dlbCanConf > 0) {
                                if (tblDat.getValueAt(i, INT_TBL_CODEMPREL) == null) {
                                    strSql = "select co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel from tbm_detguirem"
                                            + " where co_emp=" + STR_COD_EMP_REL + " and co_loc=" + STR_COD_LOC_REL + " and co_tipdoc=" + txtCodTipDocCon.getText() + " "
                                            + " and co_doc=" + txtCodDocCon.getText() + " and co_reg=" + objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CODREG));

                                    rstLoc01 = stmLoc01.executeQuery(strSql);
                                    if (rstLoc01.next()) {
                                        strSql = "select distinct(x4.ne_numorddes), x3.co_emp, x3.tx_codalt,  x3.nd_can as nd_can, x3.tx_nomitm, x3.tx_unimed "
                                                + " ,x5.st_cliretemprel, x5.tx_obscliretemprel, x6.co_bod, x6.tx_nom, x3.co_itm, x4.st_coninv, x.co_emprel, x.co_locrel, x.co_tipdocrel, x.co_docrel, x.co_regrel "
                                                + " from tbr_detmovinv as x   "
                                                + " inner join tbr_detmovinv as x1 on (x1.co_emprel=x.co_emprel and x1.co_locrel=x.co_locrel and x1.co_tipdocrel=x.co_tipdocrel and x1.co_docrel=x.co_docrel and x1.co_regrel=x.co_regrel   "
                                                + " and ( x1.co_emp!=x.co_emp or x1.co_loc!=x.co_loc or x1.co_tipdoc!=x.co_tipdoc or x1.co_doc!=x.co_doc )  )   "
                                                + " inner join tbr_detmovinv as x2 on (x2.co_emp=x1.co_emp and x2.co_loc=x1.co_loc and x2.co_tipdoc=x1.co_tipdoc and x2.co_doc=x1.co_doc and x2.co_reg=x1.co_reg  )   "
                                                + " inner join tbm_detmovinv as x5 on (x5.co_emp=x2.co_emp and x5.co_loc=x2.co_loc and x5.co_tipdoc=x2.co_tipdoc and x5.co_doc=x2.co_doc and x5.co_reg=x2.co_reg )   "
                                                + " inner join tbm_detguirem as x3 on (x3.co_emprel=x5.co_emp and x3.co_locrel=x5.co_loc and x3.co_tipdocrel=x5.co_tipdoc and x3.co_docrel=x5.co_doc and x3.co_regrel=x5.co_reg )   "
                                                + " inner join tbm_cabguirem as x4 on (x4.co_emp=x3.co_emp and x4.co_loc=x3.co_loc and x4.co_tipdoc=x3.co_tipdoc and x4.co_doc=x3.co_doc )   "
                                                + " inner join tbm_bod as x6 on (x6.co_emp=x4.co_emp and x6.co_bod=x4.co_ptopar )   "
                                                + " where x.co_emp=" + rstLoc01.getString("co_emprel") + " and x.co_loc=" + rstLoc01.getString("co_locrel") + " and x.co_tipdoc=" + rstLoc01.getString("co_tipdocrel") + " and x.co_doc= " + rstLoc01.getString("co_docrel") + "  "
                                                + " and x.co_regrel= " + rstLoc01.getString("co_regrel") + " and x.co_emprel=" + rstLoc01.getString("co_emprel") + "  ";

                                        rstLoc02 = stmLoc02.executeQuery(strSql);
                                        if (rstLoc02.next()) 
                                        {
                                            if (rstLoc02.getString("st_cliretemprel") == null) {
                                                strCodEmpRelIng = rstLoc02.getString("co_emprel");
                                                strCodLocRelIng = rstLoc02.getString("co_locrel");
                                                strCodTipDocRelIng = rstLoc02.getString("co_tipdocrel");
                                                strCodDocRelIng = rstLoc02.getString("co_docrel");
                                                strCodRegRelIng = rstLoc02.getString("co_regrel");

                                                if (!objInvItm._getVerificaConfirmacionIngRel(conn, strCodEmpRelIng, strCodLocRelIng, strCodTipDocRelIng, strCodDocRelIng, strCodRegRelIng, dlbCanNumEnv, STR_COD_BOD_GUIA, dlbCanConf)) {
                                                    blnRes = false;
                                                    MensajeInf("EL INGRESO DE ESTA MERCADERIA HA SIDO CONFIRMADO \n NO SE PUEDE INSERTAR CANTIDAD NUNCA ENVIADA TIENE ANULAR EL INGRESO.  ");
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private int getCodigoDoc(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc) 
        {
            int intCodDoc = 0;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try 
            {
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();

                    strSql = "SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabingegrmerbod WHERE "
                            + " co_emp=" + intCodEmp + " AND co_loc=" + intCodLoc + " AND co_tipDoc=" + intCodTipDoc;
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        intCodDoc = rstLoc.getInt("co_doc");
                    }

                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;

                }
            } catch (java.sql.SQLException ex) {
                objUti.mostrarMsgErr_F1(this, ex);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            return intCodDoc;
        }

        private int getUltNumDoc(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc) 
        {
            int intUltNumDoc = 0;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try 
            {
                if (conn != null)
                {
                    stmLoc = conn.createStatement();

                    strSql = "SELECT CASE WHEN (ne_ultdoc+1) IS NULL THEN 1 ELSE (ne_ultdoc+1) END AS numdoc FROM tbm_cabtipdoc WHERE co_emp=" + intCodEmp + " AND co_loc=" + intCodLoc + " AND co_tipdoc=" + intCodTipDoc;
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        intUltNumDoc = rstLoc.getInt("numdoc");
                    }

                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;

                }
            } catch (java.sql.SQLException ex) {
                objUti.mostrarMsgErr_F1(this, ex);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            return intUltNumDoc;
        }

        private String _getCodItmMeestro(java.sql.Connection conn, int intCodEmp, String strCodItm) 
        {
            String strCodItmMae = "";
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try
            {
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();

                    strSql = "select co_itmmae from tbm_equinv where co_emp=" + intCodEmp + " and co_itm=" + strCodItm + "";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        strCodItmMae = rstLoc.getString("co_itmmae");
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;

                }
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return strCodItmMae;
        }

        private boolean verificaCanNunRec(java.sql.Connection conn) 
        {
            boolean blnRes = true;
            double dlbCanNumEnv = 0;
            String strCodEmpRelIng = "", strCodLocRelIng = "", strCodTipDocRelIng = "", strCodDocRelIng = "", strCodRegRelIng = "";
            try 
            {
                if (conn != null) 
                {

                    if (objZafParSis.getCodigoMenu() == 286) 
                    {

                        for (int i = 0; i < tblDat.getRowCount(); i++) 
                        {

                            dlbCanNumEnv = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANNUMREC)), 4);

                            strCodEmpRelIng = STR_COD_EMP_REL;
                            strCodLocRelIng = txtCodLocRel.getText();
                            strCodTipDocRelIng = txtCodTipDocCon.getText();
                            strCodDocRelIng = txtCodDocCon.getText();
                            strCodRegRelIng = tblDat.getValueAt(i, INT_TBL_CODREG).toString();

                            if (!objInvItm._getVerificaCanNunRecIng(conn, strCodEmpRelIng, strCodLocRelIng, strCodTipDocRelIng, strCodDocRelIng, strCodRegRelIng, dlbCanNumEnv)) {
                                blnRes = false;
                                MensajeInf("HAY MERCADERIA QUE YA TIENE CANTIDAD NUNCA RECIBIDAD \n NO SE PUEDE INSERTAR EL DOCUMENTO DE CONFIRMACION \n CANCELE Y CONSULTE DE NUEVO EL DOCUMENTO A CONFIRMAR..  ");
                                break;
                            }
                        }
                    }

                }
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        /**
         * Funcion que permite saber que tipo de confirmacion hacer esquema
         * anterior o esuqema nuevo de guias
         *
         * @param conn
         * @param intCodEmp codigo empresa guia
         * @param intCodLoc codigo local guia
         * @param intCodTipDoc codigo tipdoc guia
         * @param intCodDoc codigo documento guia
         * @return 0 = no hay esquema o error <br> 1 = esquema nuevo <br> 2 =
         * esquema viejo
         */
        private int _getEstConfEgr(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc) 
        {
            int intTipEst = 0;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try 
            {
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();

                    strSql = "select * from ( "
                            + " select ne_numdoc, case when ne_numorddes is null then 0 else ne_numorddes end as orddes from tbm_cabguirem  "
                            + " where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc + " and co_doc=" + intCodDoc + " "
                            + " ) as x ";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        if ((rstLoc.getInt("ne_numdoc") == 0) && (rstLoc.getInt("orddes") > 0)) {
                            intTipEst = 1; //OD
                        } else if ((rstLoc.getInt("ne_numdoc") > 0) && (rstLoc.getInt("orddes") == 0)) {
                            intTipEst = 2;
                        }
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                }
            } catch (java.sql.SQLException Evt) {
                intTipEst = 0;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                intTipEst = 0;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return intTipEst;
        }
        StringBuffer strBuf;
        boolean blnEstMalEst = false;

        private boolean insertarDet(java.sql.Connection conn, int intCodDoc) 
        {
            boolean blnRes = false;
            int intTipConfEgr = 0;
            try 
            {
                if (conn != null) {
                    if (objZafParSis.getCodigoMenu() == 286) {   /*286;2325;C;Confirmación de ingresos a Bodega...*/

                        if (ingDetConfIng(conn, intCodDoc)) {
                            blnRes = true;
                        }

                    } else if ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915)) {
                        /*2205;2341;C;Confirmación de egresos de Bodega...2915;2351;C;Confirmación de egresos de Bodega (Cliente retira)...*/
                        intTipConfEgr = _getEstConfEgr(conn, Integer.parseInt(STR_COD_EMP_REL), Integer.parseInt(txtCodLocRel.getText()), Integer.parseInt(txtCodTipDocCon.getText()), Integer.parseInt(txtCodDocCon.getText()));

                        if (intTipConfEgr == 2) {

                            if (ingDetConfEgr_EsqVie(conn, intCodDoc)) {
                                if (actEstConfEgr(conn, Integer.parseInt(STR_COD_EMP_REL), Integer.parseInt(STR_COD_LOC_REL), Integer.parseInt(txtCodTipDoc.getText()), intCodDoc, false)) {
                                    blnRes = true;
                                }
                            }

                        } else if (intTipConfEgr == 1) {/*SE CONFIRMA LA OD*/
                            if (_getVerificaConfEgrRel(conn)) {
                                if (_getVerificaConfIngRel(conn)) {
                                    if (ingDetConfEgr_EsqNue(conn, intCodDoc)) {
                                        if (actEstConfEgr(conn, Integer.parseInt(STR_COD_EMP_REL), Integer.parseInt(STR_COD_LOC_REL), Integer.parseInt(txtCodTipDoc.getText()), intCodDoc, true)) {
                                            blnRes = true;
                                        }
                                    }
                                }
                            }
                        }

                    }

                }
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean _getVerificaConfIngRel(java.sql.Connection conn)
        {
            boolean blnRes = true;
            String strEstFisBod = "";
            String strCodRegGui = "";
            String strSqlAux = "";
            double dlbCanCon = 0;
            try
            {
                if (conn != null) {

                    if (_getVerCli(conn)) {

                        strItemPenCongRel = "<HTML> <TABLE><tr><td>ITEM PENDIENTE DE CONFIRMAR INGRESO </td></tr></table> "
                                + " <TABLE BORDER=1 > <TR> <TD> <FONT COLOR=\"blue\">BODEGA </FONT> </TD><TD>   <FONT COLOR=\"blue\"> ITEM  </FONT>  </TD><TD>    <FONT COLOR=\"blue\"> CAN.FAL   </FONT>    </TD><TD> <FONT COLOR=\"blue\"> NUMDOC.  </FONT> </TD> </TR> ";

                        for (int i = 0; i < tblDat.getRowCount(); i++) {
                            dlbCanCon = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANCON)), 4);
                            strEstFisBod = objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_IEBODFIS));
                            strCodRegGui = objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CODREG));

                            if (dlbCanCon > 0) {
                                if (strEstFisBod.equals("S")) {

                                    if (!(strSqlAux.equals(""))) {
                                        strSqlAux += " UNION ALL ";
                                    }
                                    strSqlAux += "SELECT " + strCodRegGui + " AS coreg, " + dlbCanCon + " as canconf ";

                                }
                            }
                        }

                        if (!(strSqlAux.equals(""))) {
                            if (!_getObtenerDifConfIng(conn, strSqlAux)) {
                                blnExisConRelPen = false;
                                strItemPenCongRel = "";
                                blnRes = false;
                            }
                        }

                        if (blnExisConRelPen) {

                            strItemPenCongRel += " </TABLE> </HMTL> ";
                            //MensajeInf(strItemPenCongRel);
                            //blnRes=false;
                        }

                    }

                    strItemPenCongRel = "";
                    blnExisConRelPen = false;

                }
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean _ingDetConfIngRel(java.sql.Connection conn) 
        {
            boolean blnRes = true;
            String strEstFisBod = "";
            String strCodRegGui = "";
            String strSqlAux = "";
            double dlbCanCon = 0;
            try
            {
                if (conn != null) {

                    if (_getVerCli(conn)) {

                        strItemPenCongRel = "<HTML> <TABLE><tr><td>ITEM PENDIENTE DE CONFIRMAR INGRESO </td></tr></table> "
                                + " <TABLE BORDER=1 > <TR> <TD> <FONT COLOR=\"blue\">BODEGA </FONT> </TD><TD>   <FONT COLOR=\"blue\"> ITEM  </FONT>  </TD><TD>    <FONT COLOR=\"blue\"> CAN.FAL   </FONT>    </TD><TD> <FONT COLOR=\"blue\"> NUMDOC.  </FONT> </TD> </TR> ";

                        for (int i = 0; i < tblDat.getRowCount(); i++) {
                            dlbCanCon = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANCON)), 4);
                            strEstFisBod = objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_IEBODFIS));
                            strCodRegGui = objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CODREG));

                            if (dlbCanCon > 0) {
                                if (strEstFisBod.equals("S")) {

                                    if (!(strSqlAux.equals(""))) {
                                        strSqlAux += " UNION ALL ";
                                    }
                                    strSqlAux += "SELECT " + strCodRegGui + " AS coreg, " + dlbCanCon + " as canconf ";

                                }
                            }
                        }

                        if (!(strSqlAux.equals(""))) {
                            if (!_getObtenerDifConfIng(conn, strSqlAux)) {
                                blnExisConRelPen = false;
                                strItemPenCongRel = "";
                                blnRes = false;
                            }
                        }

                        if (blnExisConRelPen) {

                            strItemPenCongRel += " </TABLE> </HMTL> ";
//       MensajeInf(strItemPenCongRel);
                            blnRes = false;
                        } else {
                            for (int i = 0; i < tblDat.getRowCount(); i++) {
                                dlbCanCon = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANCON)), 4);
                                strEstFisBod = objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_IEBODFIS));
                                strCodRegGui = objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CODREG));

                                if (dlbCanCon > 0) {
                                    if (strEstFisBod.equals("S")) {
                                    }
                                }
                            }
                        }

                    }

                    strItemPenCongRel = "";
                    blnExisConRelPen = false;

                }
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean _getVerificaConfEgrRel(java.sql.Connection conn) 
        {
            boolean blnRes = true;
            String strEstFisBod = "";
            String strCodRegGui = "";
            String strSqlAux = "";
            double dlbCanCon = 0;
            try
            {
                if (conn != null) 
                {

                    if (_getVerCli(conn)) {

                        strItemPenCongRel = "<HTML> <TABLE><tr><td>ITEM PENDIENTE DE CONFIRMAR SALIDA EN PUNTO REMOTO</td></tr></table> "
                                + " <TABLE BORDER=1 > <TR> <TD> <FONT COLOR=\"blue\">BODEGA </FONT> </TD><TD>   <FONT COLOR=\"blue\"> ITEM  </FONT>  </TD><TD>    <FONT COLOR=\"blue\"> POR.ING.   </FONT>    </TD><TD> <FONT COLOR=\"blue\"> NUM.O.D.  </FONT> </TD> <TD> <FONT COLOR=\"blue\"> NUM.GUIA.  </FONT> </TD> </TR> ";

                        for (int i = 0; i < tblDat.getRowCount(); i++) {
                            dlbCanCon = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANCON)), 4);
                            strEstFisBod = objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_IEBODFIS));
                            strCodRegGui = objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CODREG));

                            if (dlbCanCon > 0) {
                                if (strEstFisBod.equals("S")) {

                                    if (!(strSqlAux.equals(""))) {
                                        strSqlAux += " UNION ALL ";
                                    }
                                    strSqlAux += "SELECT " + strCodRegGui + " AS coreg, " + dlbCanCon + " as canconf ";

                                }
                            }
                        }

                        if (!(strSqlAux.equals(""))) {
                            if (!_getObtenerDif(conn, strSqlAux)) {
                                blnExisConRelPen = false;
                                strItemPenCongRel = "";
                                blnRes = false;
                            }
                        }

                        if (blnExisConRelPen) {

                            strItemPenCongRel += " </TABLE> </HMTL> ";
                            MensajeInf(strItemPenCongRel);
                            blnRes = false;
                        }

                    }

                    strItemPenCongRel = "";
                    blnExisConRelPen = false;

                }
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }
        boolean blnExisConRelPen = false;
        String strItemPenCongRel = "";
        /*
         * MODIFICADO EFLORESA 2012-05-24
         * CONFIRMACION DE EGRESO BODEGAS RELACIONADAS. CANTIDAD NUNCA RECIBIDA.
         */

        private boolean _getObtenerDif(java.sql.Connection conn, String strSqlAux)
        {
            boolean blnRes = true;
            java.sql.Statement stmLoc, stmLoc01;
            java.sql.ResultSet rstLoc, rstLoc01;
            String strSql = "";
            try 
            {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    stmLoc01 = conn.createStatement();

//        strSql="select * from (  "
//        + " select *, case when DifConfEgr >= falcofnegre then true else false end as EstConf from ( "
//        + " select *, (abs(x.canegr) - ( abs(x.canconegr)  +  x1.canconf )) as DifConfEgr from ("
//        + " select a.co_reg as coregegr, a.nd_can as canegr, a.nd_cancon as canconegr, "
//        + " a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a3.co_reg, a5.tx_codalt, a5.nd_can, a5.nd_cancon, "
//        + " a6.ne_numorddes, case when  a6.ne_numdoc > 0 then a6.ne_numdoc else null end as ne_numdoc , a7.tx_nom, "
//        + " round((abs(a5.nd_can)-abs(a5.nd_cancon)),2) as falcofnegre "
//        + " from tbm_detguirem as a "
//        + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
//        + " inner join tbr_detmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc and a2.co_reg=a1.co_reg ) "
//        + " inner join tbr_detmovinv as a3 on (a3.co_emprel=a2.co_emprel and  a3.co_locrel=a2.co_locrel and a3.co_tipdocrel=a2.co_tipdocrel and a3.co_docrel=a2.co_docrel and a3.co_regrel=a2.co_regrel  "
//        + " and a3.co_tipdoc!=a1.co_tipdoc  "
//        + " )  "
//        + " inner join tbm_detmovinv as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and a4.co_reg=a3.co_reg )   "
//        + " inner join tbm_detguirem as a5 on (a5.co_emprel=a4.co_emp and a5.co_locrel=a4.co_loc and a5.co_tipdocrel=a4.co_tipdoc and a5.co_docrel=a4.co_doc and a5.co_regrel=a4.co_reg ) "
//        + " inner join tbm_cabguirem as a6 on (a6.co_emp=a5.co_emp and a6.co_loc=a5.co_loc and a6.co_tipdoc=a5.co_tipdoc and a6.co_doc=a5.co_doc  ) "
//        + " inner join tbm_bod as a7 on (a7.co_emp=a6.co_emp and a7.co_bod=a6.co_ptopar  )  "
//        + " where a.co_emp="+STR_COD_EMP_REL+" and a.co_loc="+txtCodLocRel.getText()+" and a.co_tipdoc="+txtCodTipDocCon.getText()+" and a.co_doc="+txtCodDocCon.getText()+"  "
//        + " ) as x inner join ( "+strSqlAux+" ) as x1 on (x1.coreg=x.coregegr) "
//        + " ) as x where  falcofnegre  >  0 "
//        + "   ) as x   where EstConf = false  ";

                    /*strSql="select * from ( "
                     + " select co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel, co_reg, ( nd_can - canpenegr ) as canfijegr, (nd_cancon + canconf) as canconfegr  from (     "
                     + " select a.co_emprel, a.co_locrel, a.co_tipdocrel, a.co_docrel, a.co_regrel, a.co_reg, a.nd_cancon, a.nd_can,  "
                     + " (  select sum(( abs(x3.nd_can) - abs(x3.nd_cancon) )) as canfal from tbr_detmovinv as x  "
                     + "   inner join tbr_detmovinv as x1 on (x1.co_emprel=x.co_emprel and x1.co_locrel=x.co_locrel and x1.co_tipdocrel=x.co_tipdocrel and x1.co_docrel=x.co_docrel and x1.co_regrel=x.co_regrel   "
                     + "   and x1.co_tipdoc!=x.co_tipdoc  )   "
                     + "   inner join tbm_detmovinv as x2 on (x2.co_emp=x1.co_emp and x2.co_loc=x1.co_loc and x2.co_tipdoc=x1.co_tipdoc and x2.co_doc=x1.co_doc and x2.co_reg=x1.co_reg )   "
                     + "   inner join tbm_detguirem as x3 on (x3.co_emprel=x2.co_emp and x3.co_locrel=x2.co_loc and x3.co_tipdocrel=x2.co_tipdoc and x3.co_docrel=x2.co_doc and x3.co_regrel=x2.co_reg )  "
                     + "   where x.co_emp= a.co_emprel  and x.co_loc= a.co_locrel and x.co_tipdoc= a.co_tipdocrel and x.co_doc= a.co_docrel  and x.co_reg= a.co_regrel   "
                     + " ) as canpenegr   "
                     + " from tbm_detguirem as a  "
                     + " where a.co_emp="+STR_COD_EMP_REL+" and a.co_loc="+txtCodLocRel.getText()+" and a.co_tipdoc="+txtCodTipDocCon.getText()+" and a.co_doc="+txtCodDocCon.getText()+"  "
                     + " ) as x inner join ( "+strSqlAux+" ) as x1 on (x.co_reg=x1.coreg)  "
                     + " ) as x where  canfijegr < canconfegr ";*/

                    /*strSql="select * from ( "
                     + " select co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel, co_reg, ( nd_can - canpenegr ) as canfijegr, (nd_cancon + canconf) as canconfegr  from (     "
                     + " select a.co_emprel, a.co_locrel, a.co_tipdocrel, a.co_docrel, a.co_regrel, a.co_reg, a.nd_cancon, a.nd_can,  "
                     + " ( select sum(( abs(x3.nd_can) - abs(case when x3.nd_cancon is null or x3.nd_cancon = 0 then case when x3.nd_cannunrec is null or x3.nd_cannunrec = 0 then 0 else x3.nd_cannunrec end else x3.nd_cancon end) )) as canfal from tbr_detmovinv as x  "
                     + "   inner join tbr_detmovinv as x1 on (x1.co_emprel=x.co_emprel and x1.co_locrel=x.co_locrel and x1.co_tipdocrel=x.co_tipdocrel and x1.co_docrel=x.co_docrel and x1.co_regrel=x.co_regrel   "
                     + "   and x1.co_tipdoc!=x.co_tipdoc  )   "
                     + "   inner join tbm_detmovinv as x2 on (x2.co_emp=x1.co_emp and x2.co_loc=x1.co_loc and x2.co_tipdoc=x1.co_tipdoc and x2.co_doc=x1.co_doc and x2.co_reg=x1.co_reg )   "
                     + "   inner join tbm_detguirem as x3 on (x3.co_emprel=x2.co_emp and x3.co_locrel=x2.co_loc and x3.co_tipdocrel=x2.co_tipdoc and x3.co_docrel=x2.co_doc and x3.co_regrel=x2.co_reg )  "
                     + "   where x.co_emp= a.co_emprel  and x.co_loc= a.co_locrel and x.co_tipdoc= a.co_tipdocrel and x.co_doc= a.co_docrel  and x.co_reg= a.co_regrel   "
                     + " ) as canpenegr   "
                     + " from tbm_detguirem as a  "
                     + " where a.co_emp="+STR_COD_EMP_REL+" and a.co_loc="+txtCodLocRel.getText()+" and a.co_tipdoc="+txtCodTipDocCon.getText()+" and a.co_doc="+txtCodDocCon.getText()+"  "
                     + " ) as x inner join ( "+strSqlAux+" ) as x1 on (x.co_reg=x1.coreg)  "
                     + " ) as x where  canfijegr < canconfegr ";*/
                    
                    strSql = "select * from ( "
                            + " select co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel, co_reg, ( nd_can - canpenegr ) as canfijegr, (nd_cancon + canconf) as canconfegr from ( "
                            + " select a.co_emprel, a.co_locrel, a.co_tipdocrel, a.co_docrel, a.co_regrel, a.co_reg, a.nd_cancon, a.nd_can, "
                            + " ( select sum(( abs(x3.nd_can) - abs(case when x3.nd_cancon is null or x3.nd_cancon = 0 then case when x3.nd_cannunrec is null or x3.nd_cannunrec = 0 then 0 else x3.nd_cannunrec end else x3.nd_cancon end) )) as canfal from tbr_detmovinv as x "
                            + " inner join tbr_detmovinv as x1 on (x1.co_emprel=x.co_emprel and x1.co_locrel=x.co_locrel and x1.co_tipdocrel=x.co_tipdocrel and x1.co_docrel=x.co_docrel and x1.co_regrel=x.co_regrel "
                            //        + " and x1.co_doc!=x.co_doc) "
                            + " and (x1.co_emp!=x.co_emp or x1.co_loc!=x.co_loc or x1.co_tipdoc!=x.co_tipdoc or x1.co_doc!=x.co_doc) ) "
                            + " inner join tbm_detmovinv as x2 on (x2.co_emp=x1.co_emp and x2.co_loc=x1.co_loc and x2.co_tipdoc=x1.co_tipdoc and x2.co_doc=x1.co_doc and x2.co_reg=x1.co_reg ) "
                            + " inner join tbm_detguirem as x3 on (x3.co_emprel=x2.co_emp and x3.co_locrel=x2.co_loc and x3.co_tipdocrel=x2.co_tipdoc and x3.co_docrel=x2.co_doc and x3.co_regrel=x2.co_reg ) "
                            + " where x.co_emp= a.co_emprel  and x.co_loc= a.co_locrel and x.co_tipdoc= a.co_tipdocrel and x.co_doc= a.co_docrel  and x.co_reg= a.co_regrel "
                            + " ) as canpenegr "
                            + " from tbm_detguirem as a "
                            + " where a.co_emp=" + STR_COD_EMP_REL + " and a.co_loc=" + txtCodLocRel.getText() + " and a.co_tipdoc=" + txtCodTipDocCon.getText() + " and a.co_doc=" + txtCodDocCon.getText() + " "
                            + " ) as x inner join ( " + strSqlAux + " ) as x1 on (x.co_reg=x1.coreg) "
                            + " ) as x where  canfijegr < canconfegr ";

                    rstLoc = stmLoc.executeQuery(strSql);
                    while (rstLoc.next())
                    {

                        /*strSql="select * from ( "
                         + " select x5.tx_nom, x3.tx_codalt, round((abs(x3.nd_can)-abs(x3.nd_cancon)),2) as canfalegr, x4.ne_numorddes, case when  x4.ne_numdoc > 0 then x4.ne_numdoc else null end as ne_numdoc "
                         + " from tbr_detmovinv as x  "
                         + " inner join tbr_detmovinv as x1 on (x1.co_emprel=x.co_emprel and x1.co_locrel=x.co_locrel and x1.co_tipdocrel=x.co_tipdocrel and x1.co_docrel=x.co_docrel and x1.co_regrel=x.co_regrel  "
                         + " and x1.co_tipdoc!=x.co_tipdoc  )   "
                         + " inner join tbm_detmovinv as x2 on (x2.co_emp=x1.co_emp and x2.co_loc=x1.co_loc and x2.co_tipdoc=x1.co_tipdoc and x2.co_doc=x1.co_doc and x2.co_reg=x1.co_reg )   "
                         + " inner join tbm_detguirem as x3 on (x3.co_emprel=x2.co_emp and x3.co_locrel=x2.co_loc and x3.co_tipdocrel=x2.co_tipdoc and x3.co_docrel=x2.co_doc and x3.co_regrel=x2.co_reg )  "
                         + " inner join tbm_cabguirem as x4 on (x4.co_emp=x3.co_emp and x4.co_loc=x3.co_loc and x4.co_tipdoc=x3.co_tipdoc and x4.co_doc=x3.co_doc  )  "
                         + " inner join tbm_bod as x5 on (x5.co_emp=x4.co_emp and x5.co_bod=x4.co_ptopar  )  "
                         + " where x.co_emp="+rstLoc.getInt("co_emprel")+" and x.co_loc="+rstLoc.getInt("co_locrel")+" and x.co_tipdoc="+rstLoc.getInt("co_tipdocrel")+" "
                         + " and x.co_doc="+rstLoc.getInt("co_docrel")+"  and x.co_reg="+rstLoc.getInt("co_regrel")+" "
                         + " ) as x where canfalegr > 0  ";*/

                        /*strSql="select * from ( "
                         + " select x5.tx_nom, x3.tx_codalt, round((abs(x3.nd_can)-abs(case when x3.nd_cancon is null or x3.nd_cancon = 0 then case when x3.nd_cannunrec is null or x3.nd_cannunrec = 0 then 0 else x3.nd_cannunrec end else x3.nd_cancon end)),2) as canfalegr, x4.ne_numorddes, case when  x4.ne_numdoc > 0 then x4.ne_numdoc else null end as ne_numdoc "
                         + " from tbr_detmovinv as x  "
                         + " inner join tbr_detmovinv as x1 on (x1.co_emprel=x.co_emprel and x1.co_locrel=x.co_locrel and x1.co_tipdocrel=x.co_tipdocrel and x1.co_docrel=x.co_docrel and x1.co_regrel=x.co_regrel  "
                         + " and x1.co_tipdoc!=x.co_tipdoc  )   "
                         + " inner join tbm_detmovinv as x2 on (x2.co_emp=x1.co_emp and x2.co_loc=x1.co_loc and x2.co_tipdoc=x1.co_tipdoc and x2.co_doc=x1.co_doc and x2.co_reg=x1.co_reg )   "
                         + " inner join tbm_detguirem as x3 on (x3.co_emprel=x2.co_emp and x3.co_locrel=x2.co_loc and x3.co_tipdocrel=x2.co_tipdoc and x3.co_docrel=x2.co_doc and x3.co_regrel=x2.co_reg )  "
                         + " inner join tbm_cabguirem as x4 on (x4.co_emp=x3.co_emp and x4.co_loc=x3.co_loc and x4.co_tipdoc=x3.co_tipdoc and x4.co_doc=x3.co_doc  )  "
                         + " inner join tbm_bod as x5 on (x5.co_emp=x4.co_emp and x5.co_bod=x4.co_ptopar  )  "
                         + " where x.co_emp="+rstLoc.getInt("co_emprel")+" and x.co_loc="+rstLoc.getInt("co_locrel")+" and x.co_tipdoc="+rstLoc.getInt("co_tipdocrel")+" "
                         + " and x.co_doc="+rstLoc.getInt("co_docrel")+"  and x.co_reg="+rstLoc.getInt("co_regrel")+" "
                         + " and x.st_reg = 'A' "        
                         + " ) as x where canfalegr > 0  ";*/
                        
                        strSql = "select * from ( "
                                + " select x5.tx_nom, x3.tx_codalt, round((abs(x3.nd_can)-abs(case when x3.nd_cancon is null or x3.nd_cancon = 0 then case when x3.nd_cannunrec is null or x3.nd_cannunrec = 0 then 0 else x3.nd_cannunrec end else x3.nd_cancon end)),2) as canfalegr, x4.ne_numorddes, case when  x4.ne_numdoc > 0 then x4.ne_numdoc else null end as ne_numdoc "
                                + " from tbr_detmovinv as x "
                                + " inner join tbr_detmovinv as x1 on (x1.co_emprel=x.co_emprel and x1.co_locrel=x.co_locrel and x1.co_tipdocrel=x.co_tipdocrel and x1.co_docrel=x.co_docrel and x1.co_regrel=x.co_regrel "
                                //+ " and x1.co_doc!=x.co_doc  )   "
                                + " and (x1.co_emp!=x.co_emp or x1.co_loc!=x.co_loc or x1.co_tipdoc!=x.co_tipdoc or x1.co_doc!=x.co_doc) ) "
                                + " inner join tbm_detmovinv as x2 on (x2.co_emp=x1.co_emp and x2.co_loc=x1.co_loc and x2.co_tipdoc=x1.co_tipdoc and x2.co_doc=x1.co_doc and x2.co_reg=x1.co_reg) "
                                + " inner join tbm_detguirem as x3 on (x3.co_emprel=x2.co_emp and x3.co_locrel=x2.co_loc and x3.co_tipdocrel=x2.co_tipdoc and x3.co_docrel=x2.co_doc and x3.co_regrel=x2.co_reg) "
                                + " inner join tbm_cabguirem as x4 on (x4.co_emp=x3.co_emp and x4.co_loc=x3.co_loc and x4.co_tipdoc=x3.co_tipdoc and x4.co_doc=x3.co_doc) "
                                + " inner join tbm_bod as x5 on (x5.co_emp=x4.co_emp and x5.co_bod=x4.co_ptopar) "
                                + " where x.co_emp=" + rstLoc.getInt("co_emprel") + " and x.co_loc=" + rstLoc.getInt("co_locrel") + " and x.co_tipdoc=" + rstLoc.getInt("co_tipdocrel") + " "
                                + " and x.co_doc=" + rstLoc.getInt("co_docrel") + "  and x.co_reg=" + rstLoc.getInt("co_regrel") + " "
                                + " and x.st_reg = 'A' "
                                + " ) as x where canfalegr > 0 ";

                        rstLoc01 = stmLoc01.executeQuery(strSql);
                        while (rstLoc01.next()) 
                        {
                            strItemPenCongRel += "<TR><TD> " + rstLoc01.getString("tx_nom") + "  </TD><TD>  " + rstLoc01.getString("tx_codalt") + " </TD><TD>  " + rstLoc01.getString("canfalegr") + "   </TD><TD> " + (rstLoc01.getString("ne_numorddes") == null ? "" : rstLoc01.getString("ne_numorddes")) + " </TD><TD> " + (rstLoc01.getString("ne_numdoc") == null ? "" : rstLoc01.getString("ne_numdoc")) + " </TD></TR>";
                            blnExisConRelPen = true;
                        }
                        rstLoc01.close();
                        rstLoc01 = null;

                    }
                    rstLoc.close();
                    rstLoc = null;

                    stmLoc.close();
                    stmLoc = null;
                    stmLoc01.close();
                    stmLoc01 = null;

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

        private boolean _getObtenerDifConfIng(java.sql.Connection conn, String strSqlAux) 
        {
            boolean blnRes = true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try 
            {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "select * from ( "
                            + "  select *, case when DifConfing >= falconfing then true else false end as EstConf from (  "
                            + " select *, "
                            + " (abs(x.canegr) - ( abs(x.canconegr)  +  x1.canconf )) as DifConfing "
                            + " from( "
                            + " select  a.co_reg , a.nd_cancon, a.nd_can as canegr, a.nd_cancon as canconegr, "
                            + " a3.tx_codalt, a3.nd_can, a7.tx_nom,  "
                            + " round((abs(a3.nd_can)-abs(a3.nd_cancon)),2) as falconfing "
                            //        + " , ( a5.tx_descor || '-' || a4.ne_numdoc ) as numdoc "
                            + " , ( a5.tx_descor || '-' || a4.ne_numdoc ) as numdoc, a3.co_emp as co_empreling, a3.co_loc as co_locreling, a3.co_tipdoc as co_tipdocreling, a3.co_doc as co_docreling, a3.co_reg as co_regreling "
                            + " from tbm_detguirem as a  "
                            + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel )  "
                            + " inner join tbr_detmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc and a2.co_reg=a1.co_reg )            "
                            + " inner join tbm_detmovinv as a3 on (a3.co_emp=a2.co_emprel and  a3.co_loc=a2.co_locrel and a3.co_tipdoc=a2.co_tipdocrel and a3.co_doc=a2.co_docrel and a3.co_reg=a2.co_regrel  )     "
                            + " inner join tbm_cabmovinv as a4 on (a4.co_emp=a3.co_emp and  a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc )  "
                            + " inner join tbm_cabtipdoc as a5 on (a5.co_emp=a4.co_emp and  a5.co_loc=a4.co_loc and a5.co_tipdoc=a4.co_tipdoc  )   "
                            + " inner join tbm_bod as a7 on (a7.co_emp=a3.co_emp and a7.co_bod=a3.co_bod  )  "
                            + " where a.co_emp=" + STR_COD_EMP_REL + " and a.co_loc=" + txtCodLocRel.getText() + "  and a.co_tipdoc= " + txtCodTipDocCon.getText() + "  and a.co_doc= " + txtCodDocCon.getText() + "   "
                            + " ) as x  "
                            + " inner join (  "
                            + " " + strSqlAux + " "
                            + " ) as x1 on (x1.coreg=x.co_reg) "
                            + " ) as x where  falconfing  >  0  "
                            + " ) as x   where EstConf = false  ";

                    rstLoc = stmLoc.executeQuery(strSql);
                    while (rstLoc.next()) 
                    {

                        String strCodEmpRelIng = rstLoc.getString("co_empreling");
                        String strCodLocRelIng = rstLoc.getString("co_locreling");
                        String strCodTipDocRelIng = rstLoc.getString("co_tipdocreling");
                        String strCodDocRelIng = rstLoc.getString("co_docreling");
                        String strCodRegRelIng = rstLoc.getString("co_regreling");
                        Double dlbCanConf = objUti.redondear(objInvItm.getIntDatoValidado(rstLoc.getString("falconfing")), 2);
                        dlbCanConf = dlbCanConf - objUti.redondear(objInvItm.getIntDatoValidado(rstLoc.getString("DifConfing")), 2);

                        if (!objInvItm._getVerificaConfirmacionIngRel(conn, strCodEmpRelIng, strCodLocRelIng, strCodTipDocRelIng, strCodDocRelIng, strCodRegRelIng, 0, STR_COD_BOD_GUIA, dlbCanConf)) {
//                blnRes=false;
//                MensajeInf("EL INGRESO DE ESTA MERCADERIA HA SIDO CONFIRMADO.  ");
//                break;
                        }

                        strItemPenCongRel += "<TR><TD> " + rstLoc.getString("tx_nom") + "  </TD><TD>  " + rstLoc.getString("tx_codalt") + " </TD><TD>  " + rstLoc.getString("falconfing") + "   </TD><TD> " + rstLoc.getString("numdoc") + " </TD></TR>";
                        blnExisConRelPen = true;
                    }
                    rstLoc.close();
                    rstLoc = null;

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
         * Funcion que permite saber que la saldida de mercaderia no sea del
         * grupo como es cosenco dimulti castek tuval
         *
         * @param conn
         * @return false es cliente del grupo <br> true no es cliente del grupo.
         */
        private boolean _getVerCli(java.sql.Connection conn)
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try
            {
                if (conn != null)
                {
                    stmLoc = conn.createStatement();

                    strSql = "select  a3.co_cli from tbm_detguirem as a "
                            + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "
                            + " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) "
                            + " inner join tbm_cli as a3 on (a3.co_emp=a2.co_emp and a3.co_cli=a2.co_cli )  "
                            + " where a.co_emp=" + STR_COD_EMP_REL + " and a.co_loc=" + txtCodLocRel.getText() + " and a.co_tipdoc=" + txtCodTipDocCon.getText() + " and a.co_doc=" + txtCodDocCon.getText() + " "
                            + " and a3.co_empgrp is null "
                            + " group by a3.co_cli  ";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        blnRes = true;
                    }
                    rstLoc.close();
                    rstLoc = null;
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

        private boolean ingDetConfEgr_EsqNue(java.sql.Connection conn, int intCodDoc) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSql = "";
            String strEstFisBod = "";
            String strCodRegGui = "";
            double dlbCanGui = 0;
            double dlbCanCon = 0;
            double dlbCanNumRec = 0;
            double dblcanguisec = 0;
            double dblcantotNunRec = 0;
            try 
            {
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();
                // JoseMario 23/Mayo/2016 para trabajar con la nueva clase que maneja el stock!!!! PILAS!!!!
                    arlDatStkInvItm = new ArrayList(); 
                // JoseMario 23/Mayo/2016 para trabajar con la nueva clase que maneja el stock!!!! PILAS!!!!
                    for (int i = 0; i < tblDat.getRowCount(); i++) {
                        dlbCanGui = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANMOV)), 4);
                        dlbCanCon = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANCON)), 4);
                        dlbCanNumRec = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANNUMREC)), 4);
                        dblcantotNunRec = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANTOTNUMREC)), 4);
                        strEstFisBod = objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_IEBODFIS));
                        strCodRegGui = objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CODREG));

                        if (((dlbCanCon + dlbCanNumRec) > 0) || (strEstFisBod.equals("N"))) { //if( ((dlbCanCon > 0) ||  (dlbCanNumRec > 0))  || (strEstFisBod.equals("N"))   ){

                            if (strEstFisBod.equals("S")) {
                                dblcanguisec = dlbCanCon;
                            } else {
                                dblcanguisec = (dlbCanGui - dblcantotNunRec);
                            }

                            strSql = "INSERT INTO tbm_detingegrmerbod( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrelguirem, co_tipdocrelguirem, "
                                    + " co_docrelguirem, co_regrelguirem, co_itm, co_bod,  nd_can, tx_obs1, nd_cannunrec  ) "
                                    + " VALUES(" + STR_COD_EMP_REL + ", " + STR_COD_LOC_REL + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                    + " ," + (i + 1) + ", " + txtCodLocRel.getText() + ", " + txtCodTipDocCon.getText() + ", " + txtCodDocCon.getText() + ", "
                                    + " " + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + ",  "
                                    + " " + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + ", " + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + "  "
                                    + " ,abs(" + dlbCanCon + ")*-1, "
                                    + " '" + objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_OBSITMCON)) + "', " + dlbCanNumRec + " ) ";

                            strSql += " ; UPDATE tbm_detguirem SET st_regrep='M', co_usrCon=" + objZafParSis.getCodigoUsuario() + " "
                                    + " ,tx_obscon='" + objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_OBSITMCON)) + "' "
                                    + " ,nd_canCon = ( CASE WHEN nd_canCon IS NULL THEN 0 ELSE nd_canCon END ) + " + dlbCanCon + "  "
                                    + " ,nd_canNunRec = case when nd_canNunRec is null then 0 else nd_canNunRec end + " + dlbCanNumRec + "  ";

                            if (strEstFisBod.equals("N")) { // no se pone porque cuando genere guia ahi se pone en cantidad totGUISEC
                                strSql += " ,nd_cantotguisec = case when nd_cantotguisec is null then 0 else nd_cantotguisec end + " + (dblcanguisec + dlbCanNumRec) + "  ";
                            }

                            strSql += " WHERE co_emp=" + STR_COD_EMP_REL + " and co_loc=" + txtCodLocRel.getText() + " "
                                    + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                    + " and co_reg=" + strCodRegGui;

                            stmLoc.executeUpdate(strSql);
                            //NUEVO GENERAR CONTENEDOR Y MODIFICAR DETALLE DE MOVIMIENTO
                            if(objZafParSis.getCodigoMenu()==2205 ){
                                if(!generaNuevoContenedorItemsMovimientoStock(Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODITM).toString()),
                                                                              (dlbCanCon+dlbCanNumRec),
                                                                              Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODBOD).toString()))){
                                    blnRes=false;
                                }
                                
                                //DETALLE MOVIMIENTO DE INVENTARIO 
                                strSql="";
                                strSql+=" UPDATE tbm_detMovInv SET nd_canEgrBod=(CASE WHEN nd_canEgrBod IS NULL THEN 0 ELSE nd_canEgrBod END) + "+dlbCanCon+" ";
                                strSql+=" WHERE co_emp="+intCodEmpRel+" AND co_loc="+intCodLocRel+" AND co_tipDoc="+intCodTipDocRel+" AND co_doc="+intCodDocRel;
                                strSql+=" AND co_reg="+Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODREGRELDOCEGR).toString())+";";
                                strSql+=" UPDATE tbm_detMovInv SET nd_canTra=(CASE WHEN nd_canTra IS NULL THEN 0 ELSE nd_canTra END) - "+dlbCanCon+" ";
                                strSql+=" WHERE co_emp="+intCodEmpRel+" AND co_loc="+intCodLocRel+" AND co_tipDoc="+intCodTipDocRel+" AND co_doc="+intCodDocRel;
                                strSql+=" AND co_reg="+Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODREGRELDOCEGR).toString())+";";
                                System.out.println("ingDetConfEgr_EsqNue (Jota): " + strSql);
                                stmLoc.executeUpdate(strSql);
                                
                            }
                            
                            if (strEstFisBod.equals("S")) {
                                
                                
                                
//                                strSql = " UPDATE tbm_invbod SET nd_canegrbod=( CASE WHEN nd_canegrbod IS NULL THEN 0 ELSE nd_canegrbod END )-(" + dlbCanCon + "+" + dlbCanNumRec + " ) "
//                                        + " WHERE  co_emp=" + STR_COD_EMP_REL + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
//                                stmLoc.executeUpdate(strSql);
                            }

                        }

                    }
                    if(objZafParSis.getCodigoMenu()==2205 ){ /*EGRESOS - REPOSICION*/
                        if(objStkInv.actualizaInventario(conn, objZafParSis.getCodigoEmpresa(),INT_ARL_STK_INV_CAN_EGR_BOD, "+", 0, arlDatStkInvItm)){
                            if(objStkInv.actualizaInventario(conn, objZafParSis.getCodigoEmpresa(),INT_ARL_STK_INV_CAN_TRA, "-", 0, arlDatStkInvItm)){
                                System.out.println("ZafCom19.Mover Inventario ZafStkInv....  ");
                            }else{blnRes=false;}
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
        
         /**
        * Función que permite obtener el nombre del campo que se desea actualizar
        * @param indiceNombreCampo 
        *          <HTML>
        *              <BR>  0: Actualiza en campo "nd_stkAct"
        *              <BR>  1: Actualiza en campo "nd_canPerIng"
        *              <BR>  2: Actualiza en campo "nd_canPerEgr"
        *              <BR>  3: Actualiza en campo "nd_canBodIng"
        *              <BR>  4: Actualiza en campo "nd_canBodEgr"
        *              <BR>  5: Actualiza en campo "nd_canDesIng"
        *              <BR>  6: Actualiza en campo "nd_canDesEgr"
        *              <BR>  7: Actualiza en campo "nd_canTra"
        *              <BR>  8: Actualiza en campo "nd_canRev"
        *              <BR>  9: Actualiza en campo "nd_canRes"
        *              <BR> 10: Actualiza en campo "nd_canDis"
        *          </HTML>
        * @return true: Si se pudo obtener el nombre del campo
        * <BR> false: Caso contrario
        */
       final int INT_ARL_STK_INV_STK_ACT=0;  // nd_stkAct
       final int INT_ARL_STK_INV_NOM_CAM_ACT=1;
       final int INT_ARL_STK_INV_NOM_CAM_ACT_2=2;
       final int INT_ARL_STK_INV_CAN_ING_BOD=3;  // nd_canBodIng --> transferencia afectar ingreso 
       final int INT_ARL_STK_INV_CAN_EGR_BOD=4;  // nd_canBodEgr --> transferencia afectar egreso
       final int INT_ARL_STK_INV_CAN_DES_ENT_BOD=5;
       final int INT_ARL_STK_INV_CAN_DES_ENT_CLI=6;
       final int INT_ARL_STK_INV_CAN_TRA=7;
       final int INT_ARL_STK_INV_CAN_REV=8;
       final int INT_ARL_STK_INV_CAN_RES=9;
       final int INT_ARL_STK_INV_CAN_DIS=10;  // nd_canDis
       final int INT_ARL_STK_INV_CAN_RES_VEN=11; // Cantidad en reserva de venta 
        
        /* NUEVO CONTENEDOR PARA ITEMS ZafStkInv MovimientoStock */

        private static final int INT_STK_INV_COD_ITM_GRP=0;
        private static final int INT_STK_INV_COD_ITM_EMP=1;
        private static final int INT_STK_INV_COD_ITM_MAE=2;    
        private static final int INT_STK_INV_COD_LET_ITM=3;     
        private static final int INT_STK_INV_CAN_ITM=4;
        private static final int INT_STK_INV_COD_BOD_EMP=5; 
        private ArrayList arlRegStkInvItm, arlDatStkInvItm;
    
        private boolean generaNuevoContenedorItemsMovimientoStock(int intCodItm, double dlbCanMov,int intCodBod){
        boolean blnRes=true;
        double dblAux;
        int intCodigoItemGrupo=0, intCodigoItemMaestro=0;
        String strCodTresLetras="";
        try{
            intCodigoItemGrupo=getCodigoItemGrupo(objZafParSis.getCodigoEmpresa(),intCodItm);
            intCodigoItemMaestro=getCodigoMaestroItemGrupo(objZafParSis.getCodigoEmpresa(),intCodItm);
            strCodTresLetras=getCodigoLetraItem(objZafParSis.getCodigoEmpresa(),intCodItm);
            if(intCodigoItemGrupo==0 || intCodigoItemMaestro==0 || strCodTresLetras.equals("")){
                blnRes=false;
            }
            
            arlRegStkInvItm = new ArrayList();
            arlRegStkInvItm.add(INT_STK_INV_COD_ITM_GRP,intCodigoItemGrupo);
            arlRegStkInvItm.add(INT_STK_INV_COD_ITM_EMP,intCodItm);
            arlRegStkInvItm.add(INT_STK_INV_COD_ITM_MAE,intCodigoItemMaestro);
            arlRegStkInvItm.add(INT_STK_INV_COD_LET_ITM, strCodTresLetras);
            dblAux=dlbCanMov;
            if(dblAux<0){
                dblAux=dblAux*-1;
            }
            arlRegStkInvItm.add(INT_STK_INV_CAN_ITM,dblAux );
            arlRegStkInvItm.add(INT_STK_INV_COD_BOD_EMP,intCodBod );
            arlDatStkInvItm.add(arlRegStkInvItm);
            
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
        }
        return blnRes;
    } 
        
         private String getCodigoLetraItem(int intCodEmp, int intCodItm){
            String strCodLetItm="";
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            String strCadena;
            try{
                conLoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conLoc != null) {
                    stmLoc=conLoc.createStatement();
                    strCadena="";
                    strCadena+=" SELECT CASE WHEN tx_codAlt2 IS NULL THEN tx_codAlt ELSE tx_codAlt2 END AS tx_codAlt2 \n";
                    strCadena+=" FROM tbm_inv as x1 \n";
                    strCadena+=" WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+intCodItm+" \n";
                    rstLoc=stmLoc.executeQuery(strCadena);
                    if(rstLoc.next()){
                        strCodLetItm=rstLoc.getString("tx_codAlt2");
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                strCodLetItm="";
            }
            return strCodLetItm;
        }
        
        private int getCodigoItemGrupo(int intCodEmp, int intCodItm){
            int intCodItmGru=0;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            String strCadena;
            try{
                conLoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conLoc != null) {
                    stmLoc=conLoc.createStatement();
                    strCadena="";
                    strCadena+=" SELECT co_itm \n";
                    strCadena+=" FROM tbm_equInv as x1 \n";
                    strCadena+=" WHERE x1.co_itmMae = ( \n";
                    strCadena+="                        select co_itmMae  \n";
                    strCadena+="                        from tbm_Equinv as a1 \n";
                    strCadena+="                        where co_emp="+intCodEmp+" and co_itm="+intCodItm+")  \n";
                    strCadena+=" and x1.co_emp="+objZafParSis.getCodigoEmpresaGrupo()+" \n";
                    rstLoc=stmLoc.executeQuery(strCadena);
                    if(rstLoc.next()){
                        intCodItmGru=rstLoc.getInt("co_itm");
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                intCodItmGru=0;
            }
            return intCodItmGru;
        }
        
        
        private int getCodigoMaestroItemGrupo(int intCodEmp, int intCodItm){
            int intCodItmMae=0;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            String strCadena;
            try{
                conLoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conLoc != null) {
                    stmLoc=conLoc.createStatement();
                    strCadena="";
                    strCadena+=" SELECT x1.co_itmMae \n";
                    strCadena+=" FROM tbm_equInv as x1 \n";
                    strCadena+=" WHERE x1.co_emp="+intCodEmp+" and x1.co_itm="+intCodItm+" \n";
                    rstLoc=stmLoc.executeQuery(strCadena);
                    if(rstLoc.next()){
                        intCodItmMae=rstLoc.getInt("co_itmMae");
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                intCodItmMae=0;
            }
            return intCodItmMae;
        }
        

        private boolean ingDetConfEgr_EsqVie(java.sql.Connection conn, int intCodDoc) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSql = "";
            String strEstFisBod = "";
            double dlbCanCon = 0;
            double dlbCanNumRec = 0;
            try 
            {
                if (conn != null)
                {
                    stmLoc = conn.createStatement();

                    for (int i = 0; i < tblDat.getRowCount(); i++) {
                        dlbCanCon = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANCON)), 4);
                        dlbCanNumRec = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANNUMREC)), 4);
                        strEstFisBod = objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_IEBODFIS));

                        if ((dlbCanCon > 0) || (dlbCanNumRec > 0)) {

                            strSql = "INSERT INTO tbm_detingegrmerbod( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrelguirem, co_tipdocrelguirem, "
                                    + " co_docrelguirem, co_regrelguirem, co_itm, co_bod,  nd_can, tx_obs1, nd_cannunrec  ) "
                                    + " VALUES(" + STR_COD_EMP_REL + ", " + STR_COD_LOC_REL + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                    + " ," + (i + 1) + ", " + txtCodLocRel.getText() + ", " + txtCodTipDocCon.getText() + ", " + txtCodDocCon.getText() + ", "
                                    + " " + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + ",  "
                                    + " " + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + ", " + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + "  "
                                    + " ,abs(" + dlbCanCon + ")*-1, "
                                    + " '" + (tblDat.getValueAt(i, INT_TBL_OBSITMCON) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSITMCON).toString()) + "', " + dlbCanNumRec + " ) ";

                            strSql += " ; UPDATE tbm_detguirem SET st_regrep='M', co_usrCon=" + objZafParSis.getCodigoUsuario() + " "
                                    + " ,tx_obscon='" + (tblDat.getValueAt(i, INT_TBL_OBSITMCON) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSITMCON).toString()) + "' "
                                    + " ,nd_canCon = ( CASE WHEN nd_canCon IS NULL THEN 0 ELSE nd_canCon END ) + " + dlbCanCon + "  "
                                    + " ,nd_canNunRec = case when nd_canNunRec is null then 0 else nd_canNunRec end + " + dlbCanNumRec + "  "
                                    + " WHERE  co_emp=" + STR_COD_EMP_REL + " and co_loc=" + txtCodLocRel.getText() + " "
                                    + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                    + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                            stmLoc.executeUpdate(strSql);

                            if (strEstFisBod.equals("S")) {

                                strSql = " UPDATE tbm_invbod SET nd_canegrbod=( CASE WHEN nd_canegrbod IS NULL THEN 0 ELSE nd_canegrbod END )-(" + dlbCanCon + "+" + dlbCanNumRec + " ) "
                                        + " WHERE  co_emp=" + STR_COD_EMP_REL + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
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

        private boolean ingDetConfIng(java.sql.Connection conn, int intCodDoc) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSql = "";
            String strEstFisBod = "";
            double dlbCanCon = 0;
            double dlbCanNumRec = 0;
            double dblCanMalEst = 0;
            try 
            {
                if (conn != null) 
                {
                    // JoseMario 23/Mayo/2016 para trabajar con la nueva clase que maneja el stock!!!! PILAS!!!!
                    arlDatStkInvItm = new ArrayList(); 
                    // JoseMario 23/Mayo/2016 para trabajar con la nueva clase que maneja el stock!!!! PILAS!!!!
                    stmLoc = conn.createStatement();

                    strBuf.append("<table border=1> <tr><td> BODEGA </TD> <td> CODIGO </td> <TD> DESCRIPCION</TD> <td> CANTIDAD </td></tr> ");

                    for (int i = 0; i < tblDat.getRowCount(); i++) 
                    {

                        dlbCanCon = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANCON)), 4);
                        dlbCanNumRec = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANNUMREC)), 4);
                        dblCanMalEst = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANMALEST)), 4);
                        strEstFisBod = objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_IEBODFIS));

                        strSql = "INSERT INTO tbm_detingegrmerbod( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel, "
                                + " co_docrel, co_regrel, co_itm, co_bod,  nd_can, tx_obs1, nd_canMalEst, st_solProReaMerMalEst, nd_cannunrec, st_solProReaMerNunRec ) "
                                + " VALUES(" + STR_COD_EMP_REL + ", " + STR_COD_LOC_REL + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                + " ," + (i + 1) + ", " + txtCodLocRel.getText() + ", " + txtCodTipDocCon.getText() + ", " + txtCodDocCon.getText() + ", "
                                + " " + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + ",  "
                                + " " + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + ", " + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + "  "
                                + " ,abs(" + dlbCanCon + "), "
                                + " '" + objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_OBSITMCON)) + "' "
                                + " ," + dblCanMalEst + " , '" + (dblCanMalEst > 0 ? "P" : "") + "' ," + dlbCanNumRec + ", "
                                + " " + (dlbCanNumRec > 0 ? "'P'" : null) + " ) ";

                        strSql += " ; UPDATE tbm_detmovinv SET st_regrep='M', co_usrCon=" + objZafParSis.getCodigoUsuario() + " "
                                + " ,tx_obs1='" + objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_OBSITMCON)) + "' "
                                + " ,nd_canCon = ( CASE WHEN nd_canCon IS NULL THEN 0 ELSE nd_canCon END ) + " + dlbCanCon + "  "
                                + " ,nd_canNunRec= ( CASE WHEN nd_canNunRec IS NULL THEN 0 ELSE nd_canNunRec END ) + " + dlbCanNumRec + "  "
                                + " ,nd_canTotMalEst= ( CASE WHEN nd_canTotMalEst IS NULL THEN 0 ELSE nd_canTotMalEst END ) + " + dblCanMalEst + " "
                                + " WHERE  co_emp=" + STR_COD_EMP_REL + " and co_loc=" + txtCodLocRel.getText() + " "
                                + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                        System.out.println("INGRESO ANTES = " + strSql);
                                
                        stmLoc.executeUpdate(strSql);
                        
                        //NUEVO GENERAR CONTENEDOR Y MODIFICAR DETALLE DE MOVIMIENTO
                        if(objZafParSis.getCodigoMenu()==286 ){
                            if(!generaNuevoContenedorItemsMovimientoStock(Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODITM).toString()),
                                                                          (dlbCanCon+dlbCanNumRec+dblCanMalEst),
                                                                          Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODBOD).toString()))){
                                blnRes=false;
                            }

                            //DETALLE MOVIMIENTO DE INVENTARIO 
                            strSql="";
                            strSql+=" UPDATE tbm_detMovInv SET nd_canEgrBod=( CASE WHEN nd_canEgrBod IS NULL THEN 0 ELSE nd_canEgrBod END) + "+dlbCanCon+" ";
                            strSql+=" WHERE co_emp="+intCodEmpRel+" AND co_loc="+intCodLocRel+" AND co_tipDoc="+intCodTipDocRel+" AND co_doc="+intCodDocRel;
                            strSql+=" AND co_reg="+Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODREGRELDOCEGR).toString())+";";
                            strSql+=" UPDATE tbm_detMovInv SET nd_canTra=(CASE WHEN nd_canTra IS NULL THEN 0 ELSE nd_canTra END)- "+dlbCanCon+" ";
                            strSql+=" WHERE co_emp="+intCodEmpRel+" AND co_loc="+intCodLocRel+" AND co_tipDoc="+intCodTipDocRel+" AND co_doc="+intCodDocRel;
                            strSql+=" AND co_reg="+Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODREG).toString())+";";
                            System.out.println("ingDetConfEgr_EsqNue (Jota): " + strSql);
                            stmLoc.executeUpdate(strSql);
                        }
                    
//                        if (strEstFisBod.equals("S")) {
//
//                            strSql = " UPDATE tbm_invbod SET nd_caningbod=( CASE WHEN nd_caningbod IS NULL THEN 0 ELSE nd_caningbod END ) - (" + dlbCanCon + "+" + dlbCanNumRec + "+" + dblCanMalEst + " )   WHERE  co_emp=" + STR_COD_EMP_REL + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
//                            stmLoc.executeUpdate(strSql);
//                        }

                        if (dblCanMalEst > 0) {
                            strBuf.append("<tr><td> " + txtNomBod.getText() + " </td>  "
                                    + " <td> " + objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_CODALT)) + " </td> "
                                    + " <td> " + objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_NOMITM)) + " </td><td> " + dblCanMalEst + " </td></tr> ");
                            blnEstMalEst = true;
                        }
                    }
                    
                    if(objZafParSis.getCodigoMenu()==286 ){ /*EGRESOS - REPOSICION*/
                        if(objStkInv.actualizaInventario(conn, objZafParSis.getCodigoEmpresa(),INT_ARL_STK_INV_CAN_EGR_BOD, "+", 0, arlDatStkInvItm)){
                            if(objStkInv.actualizaInventario(conn, objZafParSis.getCodigoEmpresa(),INT_ARL_STK_INV_CAN_TRA, "-", 0, arlDatStkInvItm)){
                                System.out.println("ZafCom19.Mover Inventario ZafStkInv....  ");
                            }else{blnRes=false;}
                        }
                    }
                    
                    
                    stmLoc.close();
                    stmLoc = null;
                    strBuf.append("</table>");
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

        private boolean insertarDet_respaldo(java.sql.Connection conn, int intCodDoc) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.Statement stm_remota;
            java.sql.ResultSet rst;
            String strSql = "", strSqlFic = "";
            String strCodRegRel = "";
            int intNumReg = 0;
            int intTipMov = 1;
            try 
            {
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();

                    strBuf.append("<table border=1> <tr><td> BODEGA </TD> <td> CODIGO </td> <TD> DESCRIPCION</TD> <td> CANTIDAD </td></tr> ");

                    if (objZafParSis.getCodigoMenu() == 2063) {

                        for (int i = 0; i < tblDat.getRowCount(); i++) {
                            intNumReg++;

                            double dlbCan = objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANCON) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANCON).toString())), 4);
                            if (dlbCan > 0) {

                                if (strTipIngEgr.equals("A")) {
                                    if (strTipModIngEgr.equals("1")) {
                                        intTipMov = 1;
                                    }
                                    if (strTipModIngEgr.equals("2")) {
                                        intTipMov = -1;
                                    }
                                }

                                strSql = "select co_reg from tbm_detmovinv where co_emp=" + STRCODEMPTRANS + " and co_loc=" + STRCODLOCTRANS + " "
                                        + " and co_tipdoc=" + STRCODTIPTRANS + " and co_doc= " + STRCODDOCTRANS + "  and nd_can < 0"
                                        + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                rst = stmLoc.executeQuery(strSql);
                                if (rst.next()) {
                                    strCodRegRel = rst.getString("co_reg");
                                }
                                rst.close();
                                rst = null;

                                strSql = "INSERT INTO tbm_detingegrmerbod( "
                                        + " co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel, "
                                        + " co_docrel, co_regrel, co_itm, co_bod,  nd_can, tx_obs1  ) "
                                        + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                        + " ," + intNumReg + ", " + STRCODLOCTRANS + ", " + STRCODTIPTRANS + ", " + STRCODDOCTRANS + ", "
                                        + " " + strCodRegRel + ",  "
                                        + " " + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + ", " + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + "  "
                                        + " ," + (dlbCan * intTipMov) + ", "
                                        + " '" + (tblDat.getValueAt(i, INT_TBL_OBSITMCON) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSITMCON).toString()) + "' ) ";
                                stmLoc.executeUpdate(strSql);

                                strSql = " UPDATE tbm_detsolsaltemmer SET nd_cantotegr= ( CASE WHEN nd_cantotegr IS NULL THEN 0 ELSE nd_cantotegr END ) + " + (tblDat.getValueAt(i, INT_TBL_CANCON) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANCON).toString())) + "  "
                                        + " ,nd_cannunegr=" + (tblDat.getValueAt(i, INT_TBL_CANNUMREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString())) + "  "
                                        + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + txtCodLocRel.getText() + " "
                                        + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                        + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                                stmLoc.executeUpdate(strSql);

                            } else {

                                strSql = " UPDATE tbm_detsolsaltemmer SET nd_cannunegr=" + (tblDat.getValueAt(i, INT_TBL_CANNUMREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString())) + "  "
                                        + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + txtCodLocRel.getText() + " "
                                        + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                        + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                                stmLoc.executeUpdate(strSql);

                            }
                        }

                    } else if (objZafParSis.getCodigoMenu() == 2073) {

                        for (int i = 0; i < tblDat.getRowCount(); i++) {
                            intNumReg++;

                            double dlbCan = objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANCON) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANCON).toString())), 4);
                            if (dlbCan > 0) {

                                if (strTipIngEgr.equals("A")) {
                                    if (strTipModIngEgr.equals("1")) {
                                        intTipMov = 1;
                                    }
                                    if (strTipModIngEgr.equals("2")) {
                                        intTipMov = -1;
                                    }
                                }

                                strSql = "select co_reg from tbm_detmovinv where co_emp=" + STRCODEMPTRANS + " and co_loc=" + STRCODLOCTRANS + " "
                                        + " and co_tipdoc=" + STRCODTIPTRANS + " and co_doc= " + STRCODDOCTRANS + "  and nd_can > 0"
                                        + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                rst = stmLoc.executeQuery(strSql);
                                if (rst.next()) {
                                    strCodRegRel = rst.getString("co_reg");
                                }
                                rst.close();
                                rst = null;

                                strSql = "INSERT INTO tbm_detingegrmerbod( "
                                        + " co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel, "
                                        + " co_docrel, co_regrel, co_itm, co_bod,  nd_can, tx_obs1  ) "
                                        + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                        + " ," + intNumReg + ", " + STRCODLOCTRANS + ", " + STRCODTIPTRANS + ", " + STRCODDOCTRANS + ", "
                                        + " " + strCodRegRel + ",  "
                                        + " " + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + ", " + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + "  "
                                        + " ," + (dlbCan * intTipMov) + ", "
                                        + " '" + (tblDat.getValueAt(i, INT_TBL_OBSITMCON) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSITMCON).toString()) + "' ) ";
                                stmLoc.executeUpdate(strSql);

                                strSql = " UPDATE tbm_detsolsaltemmer SET nd_cantoting= ( CASE WHEN nd_cantoting IS NULL THEN 0 ELSE nd_cantoting END ) + " + (tblDat.getValueAt(i, INT_TBL_CANCON) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANCON).toString())) + "  "
                                        + " ,nd_cannuning=" + (tblDat.getValueAt(i, INT_TBL_CANNUMREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString())) + "  "
                                        + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + txtCodLocRel.getText() + " "
                                        + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                        + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                                stmLoc.executeUpdate(strSql);

                            }
                        }

                    } else {

                        for (int i = 0; i < tblDat.getRowCount(); i++) {
                            intNumReg++;

                            if (strTipIngEgr.equals("I")) {
                                intTipMov = 1;
                            }
                            if (strTipIngEgr.equals("E")) {
                                intTipMov = -1;
                            }
                            if (strTipIngEgr.equals("A")) {
                                if (strTipModIngEgr.equals("1")) {
                                    intTipMov = 1;
                                }
                                if (strTipModIngEgr.equals("2")) {
                                    intTipMov = -1;
                                }
                            }
                            double dlbCan = objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANCON) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANCON).toString())), 4);
                            double dlbCanNumRec = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANNUMREC)), 4);

                            String strCanMalEst = (tblDat.getValueAt(i, INT_TBL_CANMALEST) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANMALEST).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANMALEST).toString()));
                            double dblCanMalEst = objUti.redondear(strCanMalEst, 4);
                            String EstMerMalEst = "";
                            if (dblCanMalEst > 0) {
                                EstMerMalEst = "P";

                            }

                            String strEstCanNunRec = null;
                            if (dlbCanNumRec > 0) {
                                strEstCanNunRec = "P";
                            }

                            if (objZafParSis.getCodigoMenu() == 2205) {
                                /*      confirmacion de egreso         */

                                strSql = "INSERT INTO tbm_detingegrmerbod( "
                                        + " co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrelguirem, co_tipdocrelguirem, "
                                        + " co_docrelguirem, co_regrelguirem, co_itm, co_bod,  nd_can, tx_obs1, nd_cannunrec  ) "
                                        + " VALUES(" + STR_COD_EMP_REL + ", " + STR_COD_LOC_REL + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                        + " ," + intNumReg + ", " + txtCodLocRel.getText() + ", " + txtCodTipDocCon.getText() + ", " + txtCodDocCon.getText() + ", "
                                        + " " + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + ",  "
                                        + " " + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + ", " + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + "  "
                                        + " ," + (dlbCan * intTipMov) + ", "
                                        + " '" + (tblDat.getValueAt(i, INT_TBL_OBSITMCON) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSITMCON).toString()) + "', " + dlbCanNumRec + " ) ";
                                stmLoc.executeUpdate(strSql);

                            } else if (objZafParSis.getCodigoMenu() == 1974) {
                                strSql = "INSERT INTO tbm_detingegrmerbod( "
                                        + " co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrelguirem, co_tipdocrelguirem, "
                                        + " co_docrelguirem, co_regrelguirem, co_itm, co_bod,  nd_can, tx_obs1  ) "
                                        + " VALUES(" + STR_COD_EMP_REL + ", " + STR_COD_LOC_REL + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                        + " ," + intNumReg + ", " + txtCodLocRel.getText() + ", " + txtCodTipDocCon.getText() + ", " + txtCodDocCon.getText() + ", "
                                        + " " + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + ",  "
                                        + " " + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + ", " + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + "  "
                                        + " ," + (dlbCan * intTipMov) + ", "
                                        + " '" + (tblDat.getValueAt(i, INT_TBL_OBSITMCON) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSITMCON).toString()) + "' ) ";
                                stmLoc.executeUpdate(strSql);

                            } else {

                                strSql = "INSERT INTO tbm_detingegrmerbod( "
                                        + " co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel, "
                                        + " co_docrel, co_regrel, co_itm, co_bod,  nd_can, tx_obs1, nd_canMalEst, st_solProReaMerMalEst, nd_cannunrec, st_solProReaMerNunRec ) "
                                        + " VALUES(" + STR_COD_EMP_REL + ", " + STR_COD_LOC_REL + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                        + " ," + intNumReg + ", " + txtCodLocRel.getText() + ", " + txtCodTipDocCon.getText() + ", " + txtCodDocCon.getText() + ", "
                                        + " " + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + ",  "
                                        + " " + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + ", " + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + "  "
                                        + " ," + (dlbCan * intTipMov) + ", "
                                        + " '" + (tblDat.getValueAt(i, INT_TBL_OBSITMCON) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSITMCON).toString()) + "' "
                                        + " ," + strCanMalEst + " ,'" + EstMerMalEst + "' ," + dlbCanNumRec + ", " + (strEstCanNunRec == null ? null : "'" + strEstCanNunRec + "'") + " ) ";
                                stmLoc.executeUpdate(strSql);

                            }

                            String strEstFisBod = (tblDat.getValueAt(i, INT_TBL_IEBODFIS) == null ? "" : tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());

                            if (objZafParSis.getCodigoMenu() == 2205) {

                                if (strEstFisBod.equals("S")) {
                                    if (strTipIngEgr.equals("E")) {

                                        String strCanConf = (tblDat.getValueAt(i, INT_TBL_CANCON) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANCON).toString()));
                                        String strCanNumRec = (tblDat.getValueAt(i, INT_TBL_CANNUMREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString()));

                                        strSqlFic = " UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod-(" + strCanConf + "+" + strCanNumRec + " ) "
                                                + " WHERE  co_emp=" + STR_COD_EMP_REL + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }
                                }
                                strSql = " UPDATE tbm_detguirem SET st_regrep='M', co_usrCon=" + objZafParSis.getCodigoUsuario() + " "
                                        + " ,tx_obscon='" + (tblDat.getValueAt(i, INT_TBL_OBSITMCON) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSITMCON).toString()) + "' "
                                        + " ,nd_canCon = ( CASE WHEN nd_canCon IS NULL THEN 0 ELSE nd_canCon END ) + " + (tblDat.getValueAt(i, INT_TBL_CANCON) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANCON).toString())) + "  "
                                        + " ,nd_canNunRec = case when nd_canNunRec is null then 0 else nd_canNunRec end + " + dlbCanNumRec + "  "
                                        + " WHERE  co_emp=" + STR_COD_EMP_REL + " and co_loc=" + txtCodLocRel.getText() + " "
                                        + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                        + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                                stmLoc.executeUpdate(strSql);
                            }

                            if (objZafParSis.getCodigoMenu() == 286) {

                                if (strEstFisBod.equals("S")) {

                                    if (strTipIngEgr.equals("A")) {
                                        String strSqlAux = "";
                                        if (strTipModIngEgr.equals("1")) {
                                            strSqlAux = " nd_caningbod=nd_caningbod-" + (dlbCan + dlbCanNumRec + dblCanMalEst);
                                        }
                                        if (strTipModIngEgr.equals("2")) {
                                            strSqlAux = " nd_canegrbod=nd_canegrbod-" + (dlbCan + dlbCanNumRec + dblCanMalEst);
                                        }

                                        strSqlFic = " UPDATE tbm_invbod SET " + strSqlAux + " WHERE  co_emp=" + STR_COD_EMP_REL + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }

                                    String strCanConf = (tblDat.getValueAt(i, INT_TBL_CANCON) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANCON).toString()));
                                    String strCanNumRec = (tblDat.getValueAt(i, INT_TBL_CANNUMREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString()));

                                    //  if(strMerIngEgr.equals("S")){
                                    if (strTipIngEgr.equals("I")) {
                                        strSqlFic = " UPDATE tbm_invbod SET nd_caningbod=nd_caningbod- (" + strCanConf + "+" + strCanNumRec + "+" + dblCanMalEst + " )   WHERE  co_emp=" + STR_COD_EMP_REL + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }
                                    if (strTipIngEgr.equals("E")) {
                                        strSqlFic = " UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod- (" + strCanConf + "+" + strCanNumRec + "+" + dblCanMalEst + " )  WHERE  co_emp=" + STR_COD_EMP_REL + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }
                                }

                                if (dblCanMalEst > 0) {
                                    EstMerMalEst = "P";

                                    strBuf.append("<tr><td> " + txtNomBod.getText() + " </td>  "
                                            + " <td> " + objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_CODALT)) + " </td> "
                                            + " <td> " + objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_NOMITM)) + " </td><td> " + dblCanMalEst + " </td></tr> ");
                                    blnEstMalEst = true;

                                }

                                strSql = " UPDATE tbm_detmovinv SET st_regrep='M', co_usrCon=" + objZafParSis.getCodigoUsuario() + " "
                                        + " ,tx_obs1='" + (tblDat.getValueAt(i, INT_TBL_OBSITMCON) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSITMCON).toString()) + "' "
                                        + " ,nd_canCon = ( CASE WHEN nd_canCon IS NULL THEN 0 ELSE nd_canCon END ) + " + (tblDat.getValueAt(i, INT_TBL_CANCON) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANCON).toString())) + "  "
                                        + " ,nd_canNunRec= ( CASE WHEN nd_canNunRec IS NULL THEN 0 ELSE nd_canNunRec END ) + " + (tblDat.getValueAt(i, INT_TBL_CANNUMREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString())) + "  "
                                        + " ,nd_canTotMalEst= ( CASE WHEN nd_canTotMalEst IS NULL THEN 0 ELSE nd_canTotMalEst END ) + " + strCanMalEst + " "
                                        + " WHERE  co_emp=" + STR_COD_EMP_REL + " and co_loc=" + txtCodLocRel.getText() + " "
                                        + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                        + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                                stmLoc.executeUpdate(strSql);
                            }

                            if (objZafParSis.getCodigoMenu() == 1974) {

                                if (dlbCan > 0) {

                                    strSqlFic = " UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod+" + (tblDat.getValueAt(i, INT_TBL_CANCON) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANCON).toString())) + "  WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                    stmLoc.executeUpdate(strSqlFic);
                                    if (conRemGlo != null) {
                                        stm_remota = conRemGlo.createStatement();
                                        stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                        stm_remota.close();
                                        stm_remota = null;
                                    }

                                    String strAux = "";
                                    if (strEstFisBod.equals("S")) {
                                        strAux = "  , nd_cancon=( CASE WHEN nd_canCon IS NULL THEN 0 ELSE nd_canCon END )-abs(" + dlbCan + ") ";
                                    }

                                    strSql = " UPDATE tbm_detguirem SET st_regrep='M', st_meregrfisbod='S' " + strAux + " "
                                            + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + txtCodLocRel.getText() + " "
                                            + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                            + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                                    stmLoc.executeUpdate(strSql);

                                }
                            }

                        }
                    }

                    strBuf.append("</table>");

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
            if (txtCodTipDoc.getText().trim().equals(""))
            {
                MensajeInf("Ingrese tipo el documento");
                return false;
            }

            return blnRes;
        }

        /**
         * Metodo verificar que no este anulada
         *
         * @autor: jayapata
         */
        private boolean isAnuladaDocaConf(java.sql.Connection conn)
        {
            boolean blnRes = true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSQL = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    if (objZafParSis.getCodigoMenu() == 2205) {
                        strSQL = " Select st_reg from tbm_cabguirem "
                                + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and  co_loc=" + txtCodLocRel.getText() + " and  co_tipDoc=" + txtCodTipDocCon.getText() + " and  co_doc=" + txtCodDocCon.getText() + " and st_reg='I'";
                    } else {
                        strSQL = " Select st_reg from tbm_cabmovinv "
                                + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and  co_loc=" + txtCodLocRel.getText() + " and  co_tipDoc=" + txtCodTipDocCon.getText() + " and  co_doc=" + txtCodDocCon.getText() + " and st_reg='I'";
                    }
                    rstLoc = stmLoc.executeQuery(strSQL);
                    if (rstLoc.next()) {
                        blnRes = false;
                        MensajeInf("El Documento que esta Confirmando esta Anulado y no es posible Insertar ó Modificar");
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;

                }
            } catch (java.sql.SQLException Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
                blnRes = false;
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
                blnRes = false;
            }
            return blnRes;
        }

        @Override
        public boolean modificar()
        {
            boolean blnRes = false;
            java.sql.Connection conn;
            int intCodDoc = 0;
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

                        if (isAnuladaDocaConf(conn)) {

                            if (!abrirConRem()) {
                                return false;
                            }

                            intCodDoc = Integer.parseInt(txtCodDoc.getText());

                            if (verificaIngMer(conn, "MODIFICAR")) {
                                if (modificarCab(conn, intCodDoc)) {
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
                                conn.rollback();
                            }

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
            java.sql.ResultSet rst;
            String strSql = "", strSqlFic = "", sqlAux = "";
            String strCodRegRel = "";
            int intNumReg = 0;
            int intCelSel = 0;
            int intTipMov = 0;
            double dlbCanRec = 0;
            double dlbCanRecOri = 0;
            try 
            {
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();

                    strSql = "DELETE FROM tbm_detingegrmerbod WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " AND co_tipdoc=" + txtCodTipDoc.getText() + "  AND co_doc=" + intCodDoc;
                    stmLoc.executeUpdate(strSql);

                    if (objZafParSis.getCodigoMenu() == 2063) {

                        for (int i = 0; i < tblDat.getRowCount(); i++) {
                            intNumReg++;
                            intCelSel = i;

                            intTipMov = 1;
                            double dlbCan = objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANCON) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANCON).toString())), 4);

                            strSql = "select co_reg from tbm_detmovinv where co_emp=" + STRCODEMPTRANS + " and co_loc=" + STRCODLOCTRANS + " "
                                    + " and co_tipdoc=" + STRCODTIPTRANS + " and co_doc= " + STRCODDOCTRANS + "  and nd_can < 0"
                                    + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                            rst = stmLoc.executeQuery(strSql);
                            if (rst.next()) {
                                strCodRegRel = rst.getString("co_reg");
                            }
                            rst.close();
                            rst = null;

                            strSql = "INSERT INTO tbm_detingegrmerbod( "
                                    + " co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel, "
                                    + " co_docrel, co_regrel, co_itm, co_bod,  nd_can, tx_obs1  ) "
                                    + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                    + " ," + intNumReg + ", " + STRCODLOCTRANS + ", " + STRCODTIPTRANS + ", " + STRCODDOCTRANS + ", "
                                    + " " + strCodRegRel + ",  "
                                    + " " + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + ", " + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + "  "
                                    + " ," + (dlbCan * intTipMov) + ", "
                                    + " '" + (tblDat.getValueAt(i, INT_TBL_OBSITMCON) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSITMCON).toString()) + "' ) ";
                            stmLoc.executeUpdate(strSql);

                            dlbCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANCON) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON).toString()), 2);
                            dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString()), 2);

                            sqlAux = "";
                            if (dlbCanRec != dlbCanRecOri) {
                                if (dlbCanRec > dlbCanRecOri) {
                                    sqlAux += " ,nd_cantotegr=nd_cantotegr+" + (dlbCanRec - dlbCanRecOri) + " ";
                                }
                                if (dlbCanRec < dlbCanRecOri) {
                                    sqlAux += " ,nd_cantotegr=nd_cantotegr-" + (dlbCanRecOri - dlbCanRec) + " ";
                                }
                            }

                            strSql = " UPDATE tbm_detsolsaltemmer SET nd_cannunegr=" + (tblDat.getValueAt(i, INT_TBL_CANNUMREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString())) + "  "
                                    + " " + sqlAux + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + txtCodLocRel.getText() + " "
                                    + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                    + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                            stmLoc.executeUpdate(strSql);

                        }

                    } else if (objZafParSis.getCodigoMenu() == 2073) {

                        for (int i = 0; i < tblDat.getRowCount(); i++) {
                            intNumReg++;
                            intCelSel = i;

                            intTipMov = 1;
                            double dlbCan = objUti.redondear((tblDat.getValueAt(i, INT_TBL_CANCON) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANCON).toString())), 4);

                            strSql = "select co_reg from tbm_detmovinv where co_emp=" + STRCODEMPTRANS + " and co_loc=" + STRCODLOCTRANS + " "
                                    + " and co_tipdoc=" + STRCODTIPTRANS + " and co_doc= " + STRCODDOCTRANS + "  and nd_can > 0 "
                                    + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                            rst = stmLoc.executeQuery(strSql);
                            if (rst.next()) {
                                strCodRegRel = rst.getString("co_reg");
                            }
                            rst.close();
                            rst = null;

                            strSql = "INSERT INTO tbm_detingegrmerbod( "
                                    + " co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel, "
                                    + " co_docrel, co_regrel, co_itm, co_bod,  nd_can, tx_obs1  ) "
                                    + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                    + " ," + intNumReg + ", " + STRCODLOCTRANS + ", " + STRCODTIPTRANS + ", " + STRCODDOCTRANS + ", "
                                    + " " + strCodRegRel + ",  "
                                    + " " + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + ", " + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + "  "
                                    + " ," + (dlbCan * intTipMov) + ", "
                                    + " '" + (tblDat.getValueAt(i, INT_TBL_OBSITMCON) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSITMCON).toString()) + "' ) ";
                            stmLoc.executeUpdate(strSql);

                            dlbCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANCON) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON).toString()), 2);
                            dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString()), 2);

                            sqlAux = "";
                            if (dlbCanRec != dlbCanRecOri) {
                                if (dlbCanRec > dlbCanRecOri) {
                                    sqlAux += " ,nd_cantotIng=nd_cantotIng+" + (dlbCanRec - dlbCanRecOri) + " ";
                                }
                                if (dlbCanRec < dlbCanRecOri) {
                                    sqlAux += " ,nd_cantotIng=nd_cantotIng-" + (dlbCanRecOri - dlbCanRec) + " ";
                                }
                            }

                            strSql = " UPDATE tbm_detsolsaltemmer SET nd_cannunIng=" + (tblDat.getValueAt(i, INT_TBL_CANNUMREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString())) + "  "
                                    + " " + sqlAux + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + txtCodLocRel.getText() + " "
                                    + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                    + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                            stmLoc.executeUpdate(strSql);

                        }

                    } else {

                        for (int i = 0; i < tblDat.getRowCount(); i++) {
                            intNumReg++;
                            intCelSel = i;

                            if (objZafParSis.getCodigoMenu() == 2205) {
                                strSql = "INSERT INTO tbm_detingegrmerbod( "
                                        + " co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrelguirem, co_tipdocrelguirem, "
                                        + " co_docrelguirem, co_regrelguirem, co_itm, co_bod,  nd_can, tx_obs1  ) "
                                        + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                        + " ," + intNumReg + ", " + txtCodLocRel.getText() + ", " + txtCodTipDocCon.getText() + ", " + txtCodDocCon.getText() + ", "
                                        + " " + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + ",  "
                                        + " " + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + ", " + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + "  "
                                        + " ," + (tblDat.getValueAt(i, INT_TBL_CANCON) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANCON).toString())) + ", "
                                        + " '" + (tblDat.getValueAt(i, INT_TBL_OBSITMCON) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSITMCON).toString()) + "' ) ";
                            } else {

                                strSql = "INSERT INTO tbm_detingegrmerbod( "
                                        + " co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel, "
                                        + " co_docrel, co_regrel, co_itm, co_bod,  nd_can, tx_obs1  ) "
                                        + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                        + " ," + intNumReg + ", " + txtCodLocRel.getText() + ", " + txtCodTipDocCon.getText() + ", " + txtCodDocCon.getText() + ", "
                                        + " " + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + ",  "
                                        + " " + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + ", " + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + "  "
                                        + " ," + (tblDat.getValueAt(i, INT_TBL_CANCON) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANCON).toString())) + ", "
                                        + " '" + (tblDat.getValueAt(i, INT_TBL_OBSITMCON) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSITMCON).toString()) + "' ) ";
                            }

                            stmLoc.executeUpdate(strSql);

                            dlbCanRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANCON) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON).toString()), 2);
                            dlbCanRecOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANCON_ORI).toString()), 2);

                            sqlAux = "";
                            String strEstFisBod = (tblDat.getValueAt(i, INT_TBL_IEBODFIS) == null ? "" : tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());

                            if (objZafParSis.getCodigoMenu() == 2205) {

                                if (strEstFisBod.equals("S")) {

                                    if (strTipIngEgr.equals("E")) {
                                        if (dlbCanRec != dlbCanRecOri) {
                                            if (dlbCanRec > dlbCanRecOri) {
                                                sqlAux += " nd_canegrbod=nd_canegrbod-" + (dlbCanRec - dlbCanRecOri) + " ";
                                            }
                                            if (dlbCanRec < dlbCanRecOri) {
                                                sqlAux += " nd_canegrbod=nd_canegrbod+" + (dlbCanRecOri - dlbCanRec) + " ";
                                            }
                                        }
                                        if (!sqlAux.equals("")) {
                                            strSqlFic = " UPDATE tbm_invbod SET " + sqlAux + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
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
                                sqlAux = "";
                                if (dlbCanRec != dlbCanRecOri) {
                                    if (dlbCanRec > dlbCanRecOri) {
                                        sqlAux += " ,nd_canCon=nd_canCon+" + (dlbCanRec - dlbCanRecOri) + " ";
                                    }
                                    if (dlbCanRec < dlbCanRecOri) {
                                        sqlAux += " ,nd_canCon=nd_canCon-" + (dlbCanRecOri - dlbCanRec) + " ";
                                    }
                                }
                                strSql = " UPDATE tbm_detguirem SET st_regrep='M', co_usrCon=" + objZafParSis.getCodigoUsuario() + " "
                                        + " ,tx_obs1='" + (tblDat.getValueAt(i, INT_TBL_OBSITMCON) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSITMCON).toString()) + "' "
                                        + " ,nd_canNunRec=" + (tblDat.getValueAt(i, INT_TBL_CANNUMREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString())) + "  "
                                        + " " + sqlAux + " "
                                        + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + txtCodLocRel.getText() + " "
                                        + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                        + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                                stmLoc.executeUpdate(strSql);
                            }

                            if (objZafParSis.getCodigoMenu() == 286) {

                                if (strEstFisBod.equals("S")) {
                                    //if(strMerIngEgr.equals("S")){

                                    if (strTipIngEgr.equals("A")) {
                                        if (strTipModIngEgr.equals("1")) {
                                            if (dlbCanRec != dlbCanRecOri) {
                                                if (dlbCanRec > dlbCanRecOri) {
                                                    sqlAux += " nd_caningbod=nd_caningbod-" + (dlbCanRec - dlbCanRecOri) + " ";
                                                }
                                                if (dlbCanRec < dlbCanRecOri) {
                                                    sqlAux += " nd_caningbod=nd_caningbod+" + (dlbCanRecOri - dlbCanRec) + " ";
                                                }
                                            }
                                        }
                                        if (strTipModIngEgr.equals("2")) {
                                            if (dlbCanRec != dlbCanRecOri) {
                                                if (dlbCanRec > dlbCanRecOri) {
                                                    sqlAux += " nd_canegrbod=nd_canegrbod-" + (dlbCanRec - dlbCanRecOri) + " ";
                                                }
                                                if (dlbCanRec < dlbCanRecOri) {
                                                    sqlAux += " nd_canegrbod=nd_canegrbod+" + (dlbCanRecOri - dlbCanRec) + " ";
                                                }
                                            }
                                        }

                                        if (!sqlAux.equals("")) {
                                            strSqlFic = " UPDATE tbm_invbod SET " + sqlAux + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                            stmLoc.executeUpdate(strSqlFic);
                                            if (conRemGlo != null) {
                                                stm_remota = conRemGlo.createStatement();
                                                stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                                stm_remota.close();
                                                stm_remota = null;
                                            }
                                        }
                                    }

                                    if (strTipIngEgr.equals("I")) {
                                        if (dlbCanRec != dlbCanRecOri) {
                                            if (dlbCanRec > dlbCanRecOri) {
                                                sqlAux += " nd_caningbod=nd_caningbod-" + (dlbCanRec - dlbCanRecOri) + " ";
                                            }
                                            if (dlbCanRec < dlbCanRecOri) {
                                                sqlAux += " nd_caningbod=nd_caningbod+" + (dlbCanRecOri - dlbCanRec) + " ";
                                            }
                                        }
                                        if (!sqlAux.equals("")) {
                                            strSqlFic = " UPDATE tbm_invbod SET " + sqlAux + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                            stmLoc.executeUpdate(strSqlFic);
                                            if (conRemGlo != null) {
                                                stm_remota = conRemGlo.createStatement();
                                                stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                                stm_remota.close();
                                                stm_remota = null;
                                            }
                                        }
                                    }
                                    if (strTipIngEgr.equals("E")) {
                                        if (dlbCanRec != dlbCanRecOri) {
                                            if (dlbCanRec > dlbCanRecOri) {
                                                sqlAux += " nd_canegrbod=nd_canegrbod-" + (dlbCanRec - dlbCanRecOri) + " ";
                                            }
                                            if (dlbCanRec < dlbCanRecOri) {
                                                sqlAux += " nd_canegrbod=nd_canegrbod+" + (dlbCanRecOri - dlbCanRec) + " ";
                                            }
                                        }
                                        if (!sqlAux.equals("")) {
                                            strSqlFic = " UPDATE tbm_invbod SET " + sqlAux + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
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
                                sqlAux = "";
                                if (dlbCanRec != dlbCanRecOri) {
                                    if (dlbCanRec > dlbCanRecOri) {
                                        sqlAux += " ,nd_canCon=nd_canCon+" + (dlbCanRec - dlbCanRecOri) + " ";
                                    }
                                    if (dlbCanRec < dlbCanRecOri) {
                                        sqlAux += " ,nd_canCon=nd_canCon-" + (dlbCanRecOri - dlbCanRec) + " ";
                                    }
                                }
                                strSql = " UPDATE tbm_detmovinv SET st_regrep='M', co_usrCon=" + objZafParSis.getCodigoUsuario() + " "
                                        + " ,tx_obs1='" + (tblDat.getValueAt(i, INT_TBL_OBSITMCON) == null ? "" : tblDat.getValueAt(i, INT_TBL_OBSITMCON).toString()) + "' "
                                        + " ,nd_canNunRec=" + (tblDat.getValueAt(i, INT_TBL_CANNUMREC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANNUMREC).toString())) + "  "
                                        + " " + sqlAux + " "
                                        + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + txtCodLocRel.getText() + " "
                                        + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                        + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                                stmLoc.executeUpdate(strSql);
                            }

                            if (objZafParSis.getCodigoMenu() == 1974) {

                                if (strTipIngEgr.equals("I")) {
                                    if (dlbCanRec != dlbCanRecOri) {
                                        if (dlbCanRec > dlbCanRecOri) {
                                            sqlAux += " nd_caningbod=nd_caningbod+" + (dlbCanRec - dlbCanRecOri) + " ";
                                        }
                                        if (dlbCanRec < dlbCanRecOri) {
                                            sqlAux += " nd_caningbod=nd_caningbod-" + (dlbCanRecOri - dlbCanRec) + " ";
                                        }
                                    }
                                    if (!sqlAux.equals("")) {
                                        strSqlFic = " UPDATE tbm_invbod SET " + sqlAux + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
//             strSqlFic += " ; Update tbm_invbod " +
//                         " set "+sqlAux+"  "+
//                         " where co_emp=" + objZafParSis.getCodigoEmpresaGrupo() + " and co_itm =" +
//                         " (Select co_itm from tbm_equinv where co_emp=" + objZafParSis.getCodigoEmpresaGrupo() + " and co_itmmae IN " +
//                         " (Select co_itmmae from tbm_equinv where co_emp ="+ objZafParSis.getCodigoEmpresa() +" and co_itm="+ tblDat.getValueAt(i, INT_TBL_CODITM).toString() +") )" +
//                         " and co_bod =" +
//                         " (select co_bodgrp from  tbr_bodempbodgrp where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_bod="+tblDat.getValueAt(i, INT_TBL_CODBOD).toString()+") ";
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }
                                }
                                if (strTipIngEgr.equals("E")) {
                                    if (dlbCanRec != dlbCanRecOri) {
                                        if (dlbCanRec > dlbCanRecOri) {
                                            sqlAux += " nd_canegrbod=nd_canegrbod+" + (dlbCanRec - dlbCanRecOri) + " ";
                                        }
                                        if (dlbCanRec < dlbCanRecOri) {
                                            sqlAux += " nd_canegrbod=nd_canegrbod-" + (dlbCanRecOri - dlbCanRec) + " ";
                                        }
                                    }
                                    if (!sqlAux.equals("")) {
                                        strSqlFic = " UPDATE tbm_invbod SET " + sqlAux + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_bod=" + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + " and co_itm=" + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
//             strSqlFic +="; Update tbm_invbod " +
//                         " set "+sqlAux+" "+
//                         " where co_emp=" + objZafParSis.getCodigoEmpresaGrupo() + " and co_itm =" +
//                         " (Select co_itm from tbm_equinv where co_emp=" + objZafParSis.getCodigoEmpresaGrupo() + " and co_itmmae IN " +
//                         " (Select co_itmmae from tbm_equinv where co_emp ="+ objZafParSis.getCodigoEmpresa() +" and co_itm="+ tblDat.getValueAt(i, INT_TBL_CODITM).toString() +") )" +
//                         " and co_bod =" +
//                         " (select co_bodgrp from  tbr_bodempbodgrp where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_bod="+tblDat.getValueAt(i, INT_TBL_CODBOD).toString()+") ";
                                        stmLoc.executeUpdate(strSqlFic);
                                        if (conRemGlo != null) {
                                            stm_remota = conRemGlo.createStatement();
                                            stm_remota.executeUpdate(strSqlFic);  // REMOTAMENTE
                                            stm_remota.close();
                                            stm_remota = null;
                                        }
                                    }
                                }
                                sqlAux = "";
                                if (dlbCanRec != dlbCanRecOri) {
                                    if (dlbCanRec > dlbCanRecOri) {
                                        sqlAux += " ,nd_canCon=nd_canCon-" + (dlbCanRec - dlbCanRecOri) + " ";
                                    }
                                    if (dlbCanRec < dlbCanRecOri) {
                                        sqlAux += " ,nd_canCon=nd_canCon+" + (dlbCanRecOri - dlbCanRec) + " ";
                                    }
                                }
                                strSql = " UPDATE tbm_detmovinv SET st_regrep='M', st_meringegrfisbod='S' "
                                        + " " + sqlAux + " "
                                        + " WHERE  co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + txtCodLocRel.getText() + " "
                                        + " and co_tipdoc=" + txtCodTipDocCon.getText() + " and co_doc=" + txtCodDocCon.getText() + " "
                                        + " and co_reg=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString();
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

        private boolean modificarCab(java.sql.Connection conn, int intCodDoc)
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            String strFecDoc = "";
            String strFecSis = "";
            try 
            {
                if (conn != null)
                {
                    stmLoc = conn.createStatement();

                    //************  PERMITE SABER SI EL NUMERO DE Devolucion ESTA DUPLICADO  *****************/ 
                    if (!txtNumDocSolOcu.getText().equals(txtNumDoc.getText())) {
                        strSql = "select count(ne_numdoc) as num from tbm_cabingegrmerbod WHERE "
                                + " co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + objZafParSis.getCodigoLocal() + " "
                                + "and co_tipdoc=" + txtCodTipDoc.getText() + " and ne_numdoc=" + txtNumDoc.getText();
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
                            if (rstLoc.getInt("num") >= 1) {
                                //JOptionPane oppMsg = new JOptionPane();
                                String strTit, strMsg;
                                strTit = "Mensaje del sistema Zafiro";
                                strMsg = " No. de Confirmación ya existe... ?";
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

                    strSql = "DELETE FROM tbm_detingegrmerbod WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " AND co_tipdoc=" + txtCodTipDoc.getText() + "  AND co_doc=" + intCodDoc;
                    stmLoc.executeUpdate(strSql);

                    if (objZafParSis.getCodigoMenu() == 2063) {
                        if (genera_Trans_act(0, conRemGlo, conn, txtCodTipDocCon.getText(), txtCodDocCon.getText())) {

                            strSql = "UPDATE tbm_cabingegrmerbod SET   "
                                    + " fe_doc='" + strFecDoc + "', ne_numdoc=" + txtNumDoc.getText() + ",  "
                                    + " tx_obs1='" + txtObs1.getText() + "', tx_obs2='" + txtObs2.getText() + "', fe_ultmod='" + strFecSis + "', co_usrmod=" + objZafParSis.getCodigoUsuario() + " "
                                    + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " AND co_tipdoc=" + txtCodTipDoc.getText() + "  AND co_doc=" + intCodDoc;
                            stmLoc.executeUpdate(strSql);
                            blnRes = true;
                        } else {
                            blnRes = false;
                        }
                    } else if (objZafParSis.getCodigoMenu() == 2073) {
                        if (genera_Trans_act(0, conRemGlo, conn, txtCodTipDocCon.getText(), txtCodDocCon.getText())) {

                            strSql = "UPDATE tbm_cabingegrmerbod SET   "
                                    + " fe_doc='" + strFecDoc + "', ne_numdoc=" + txtNumDoc.getText() + ",  "
                                    + " tx_obs1='" + txtObs1.getText() + "', tx_obs2='" + txtObs2.getText() + "', fe_ultmod='" + strFecSis + "', co_usrmod=" + objZafParSis.getCodigoUsuario() + " "
                                    + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " AND co_tipdoc=" + txtCodTipDoc.getText() + "  AND co_doc=" + intCodDoc;
                            stmLoc.executeUpdate(strSql);
                            blnRes = true;

                        } else {
                            blnRes = false;
                        }
                    } else {

                        strSql = "UPDATE tbm_cabingegrmerbod SET   "
                                + " fe_doc='" + strFecDoc + "', ne_numdoc=" + txtNumDoc.getText() + ",  "
                                + " tx_obs1='" + txtObs1.getText() + "', tx_obs2='" + txtObs2.getText() + "', fe_ultmod='" + strFecSis + "', co_usrmod=" + objZafParSis.getCodigoUsuario() + " "
                                + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " AND co_tipdoc=" + txtCodTipDoc.getText() + "  AND co_doc=" + intCodDoc;
                        stmLoc.executeUpdate(strSql);
                        blnRes = true;
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

        private void bloquea(javax.swing.JTextField txtFiel, java.awt.Color colBack, boolean blnEst) {
            colBack = txtFiel.getBackground();
            txtFiel.setEditable(blnEst);
            txtFiel.setBackground(colBack);
        }

        private void noEditable(boolean blnEst)
        {
            java.awt.Color colBack = txtCodCliPro.getBackground();
            bloquea(txtCodCliPro, colBack, blnEst);
            bloquea(txtNomCliPro, colBack, blnEst);
            bloquea(txtDesCorTipDocCon, colBack, blnEst);
            bloquea(txtDesLarTipDocCon, colBack, blnEst);
            bloquea(txtCodVenCom, colBack, blnEst);
            bloquea(txtNomVenCom, colBack, blnEst);
            bloquea(txtCodForRet, colBack, blnEst);
            bloquea(txtDesForRet, colBack, blnEst);
            bloquea(txtFecDocCon, colBack, blnEst);
            bloquea(txtNumDoc, colBack, blnEst);
            bloquea(txtCodDocCon, colBack, blnEst);

            if (objZafParSis.getCodigoMenu() == 2205) {
                //bloquea(txtNumDocCon, colBack, blnEst);
            }

            //txaObs1.setEditable(blnEst);
            //txaObs2.setEditable(blnEst);
        }

        public void cargarBodPre() 
        {
            java.sql.Connection conn;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try 
            {
                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();

                    strSql =  " SELECT co_bod, tx_nom FROM ( "
                            + " select a2.co_bodgrp as co_bod,  a1.tx_nom from tbr_bodlocprgusr as a "
                            + " inner join tbr_bodEmpBodGrp as a2 on (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) "
                            + " inner join tbm_bod as a1 on (a1.co_emp=a2.co_empgrp and a1.co_bod=a2.co_bodgrp) "
                            + " where a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " and a.co_usr=" + objZafParSis.getCodigoUsuario() + " and a.co_mnu=" + objZafParSis.getCodigoMenu() + "  and a.st_reg='P' "
                            + " ) as a";

                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) 
                    {
                        txtCodBod.setText(rstLoc.getString("co_bod"));
                        txtNomBod.setText(rstLoc.getString("tx_nom"));
                        strCodBod = rstLoc.getString("co_bod");
                        strNomBod = rstLoc.getString("tx_nom");
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

        public void cargarTipoDoc(int intVal) 
        {
            java.sql.Connection conn;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try 
            {
                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();

                    if (objZafParSis.getCodigoUsuario() == 1) 
                    {
                        strSql =  " Select  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor , doc.ne_numVisBue  ,case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc  "
                                + " from tbr_tipdocprg as menu  "
                                + " inner join  tbm_cabtipdoc as doc on ( doc.co_emp = menu.co_emp and doc.co_loc=menu.co_loc and  doc.co_tipdoc=menu.co_tipdoc)   "
                                + " where   menu.co_emp = " + objZafParSis.getCodigoEmpresa() + " and "
                                + " menu.co_loc = " + objZafParSis.getCodigoLocal() + " and "
                                + " menu.co_mnu = " + objZafParSis.getCodigoMenu() + " and "
                                + " menu.st_reg = 'S'";
                    }
                    else 
                    {
                        strSql =  " Select  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor , doc.ne_numVisBue  ,case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc  "
                                + " from tbr_tipdocUsr as menu  "
                                + " inner join  tbm_cabtipdoc as doc on ( doc.co_emp = menu.co_emp and doc.co_loc=menu.co_loc and  doc.co_tipdoc=menu.co_tipdoc)   "
                                + " where   menu.co_emp = " + objZafParSis.getCodigoEmpresa() + " and "
                                + " menu.co_loc = " + objZafParSis.getCodigoLocal() + " and "
                                + " menu.co_mnu = " + objZafParSis.getCodigoMenu() + " and "
                                + " menu.co_usr = " + objZafParSis.getCodigoUsuario() + " and "
                                + "  menu.st_reg = 'S'";
                    }

                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next())
                    {
                        txtCodTipDoc.setText(((rstLoc.getString("co_tipdoc") == null) ? "" : rstLoc.getString("co_tipdoc")));
                        txtDesCodTitpDoc.setText(((rstLoc.getString("tx_descor") == null) ? "" : rstLoc.getString("tx_descor")));
                        txtDesLarTipDoc.setText(((rstLoc.getString("tx_deslar") == null) ? "" : rstLoc.getString("tx_deslar")));
                        strCodTipDoc = txtCodTipDoc.getText();
                        //if(intVal==1)
                        //  txtNumDoc.setText(((rstLoc.getString("numDoc")==null)?"":rstLoc.getString("numDoc")));
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
            txtCodBod.setText("");
            txtNomBod.setText("");
            txtCodDoc.setText("");
            txtNumDoc.setText("");
            txtNumGuiDes.setText("");

            txtDesLarTipDocCon.setText("");
            txtDesCorTipDocCon.setText("");
            txtCodCliPro.setText("");
            txtNomCliPro.setText("");
            txtCodVenCom.setText("");
            txtNomVenCom.setText("");
            txtCodForRet.setText("");
            txtDesForRet.setText("");
            txtFecDocCon.setText("");
            txtNumDocCon.setText("");
            txtCodDocCon.setText("");

            txtObs1.setText("");
            txtObs2.setText("");

            txtCodVeh.setText("");
            txtPlaVeh.setText("");
            txtDesLarVeh.setText("");
            txtPesVeh.setText("");
            txtCodCho.setText("");
            txtIdeCho.setText("");
            txtNomCho.setText("");
            txtIdeTra.setText("");
            txtNomTra.setText("");
            txtPlaVehTra.setText("");
            lblNumGuiDet.setText("");
            txtMotTra.setText("");

            STRCODEMPTRANS = "";
            STRCODLOCTRANS = "";
            STRCODTIPTRANS = "";
            STRCODDOCTRANS = "";

            javax.swing.JLabel objlbl = new javax.swing.JLabel();
            objlbl.setText("");
            jfrThis2.setGlassPane(objlbl);
            objlbl.setVisible(false);
            objlbl = null;

            objTblMod.removeAllRows();
        }

        @Override
        public boolean cancelar() 
        {
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
        public boolean afterInsertar() {
            this.setEstado('w');

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
        private int mostrarMsgCon(String strMsg) 
        {
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
        private boolean isRegPro() 
        {
            boolean blnRes = true;
            String strAux = "¿Desea guardar los cambios efectuados a éste registro?\n";
            strAux += "Si no guarda los cambios perderá toda la información que no haya guardado.";
            switch (mostrarMsgCon(strAux)) 
            {
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
        public boolean consultar() 
        {
            //Esto se realiza en el modo de Consulta.
            if (txtCodBod.getText().equals("")) 
            {
                MensajeInf("Seleccione la bodega antes consultar un documento. ");
                tabFrm.setSelectedIndex(0);
                txtCodBod.requestFocus();
                return false;
            }
            else 
            {
                return _consultar(FilSql());
            }

        }

        private boolean _consultar(String strFil) 
        {
            boolean blnRes = false;
            String strSql = "";
            try
            {
                if (!validarDat()) 
                {
                    return false;
                }

                abrirCon();
                if (CONN_GLO != null) 
                {
                    STM_GLO = CONN_GLO.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY);

                    if ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915)) 
                    {
                        strSql =  " SELECT  a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc FROM tbm_cabingegrmerbod AS a  "
                                + " INNER JOIN tbm_cabguirem AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrelguirem AND a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem)    "
                                + " INNER JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_bod=a1.co_ptopar)  "
                                + " WHERE  ( a2.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a2.co_bodGrp=" + txtCodBod.getText() + ")  AND  a.st_reg NOT IN('E')    "
                                + " " + strFil + " AND a.co_mnu=" + objZafParSis.getCodigoMenu() + " ORDER BY a.ne_numdoc  ";
                    } 
                    else if (objZafParSis.getCodigoMenu() == 286) 
                    {
                        strSql =  " SELECT a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a2.co_bod "
                                + " FROM tbm_cabingegrmerbod AS a  "
                                + " INNER JOIN tbm_cabmovinv AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel)    "
                                + " INNER JOIN tbm_detmovinv AS a2 ON(a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc)    "
                                + " INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a3.co_emp=a2.co_emp AND a3.co_bod=a2.co_bod)   "
                                + " WHERE a.st_reg NOT IN('E') AND  ( a3.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a3.co_bodGrp=" + txtCodBod.getText() + ") "
                                + " AND  a2.nd_can > 0  AND a.st_reg NOT IN('E') " + strFil + " AND a.co_mnu in ( " + objZafParSis.getCodigoMenu() + ",2750) " //  codigo menu 2750  es  confirmacion relacionada
                                + " GROUP BY a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a2.co_bod, a.ne_numdoc   ORDER BY a.ne_numdoc ";
                    }
                    else 
                    {
                        strSql =  " SELECT  a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc FROM tbm_cabingegrmerbod AS a "
                                + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " and a.co_loc=" + objZafParSis.getCodigoLocal() + " AND a.st_reg NOT IN('E') "
                                + " " + strFil + " AND a.co_mnu=" + objZafParSis.getCodigoMenu() + " ORDER BY a.ne_numdoc ";
                    }

                    rstCab = STM_GLO.executeQuery(strSql);
                    if (rstCab.next()) 
                    {
                        rstCab.last();
                        setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                        refrescaDatos(CONN_GLO);
                        blnRes = true;
                    } 
                    else 
                    {
                        setMenSis("0 Registros encontrados");
                        clnTextos();
                    }

                    CerrarCon();
                }
            } 
            catch (java.sql.SQLException Evt) {   blnRes = false;  objUti.mostrarMsgErr_F1(this, Evt);  } 
            catch (Exception Evt) {   blnRes = false;  objUti.mostrarMsgErr_F1(this, Evt);         }

            System.gc();
            return blnRes;
        }

        private boolean refrescaDatos(java.sql.Connection conn) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.ResultSet rstDatDoc;
            String strSql = "";
            Vector vecData;
            try 
            {
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();

                    if (objZafParSis.getCodigoMenu() == 2063) 
                    {

                        strSql =  " SELECT a.co_tipdoc as co_tipdoccon, a6.tx_descor as tx_descorcon, a6.tx_deslar as tx_deslarcon, "
                                + "        a.st_reg,  a.co_emp, a.co_loc, a.ne_numdoc, a.co_doc, a.tx_obs1, a.tx_obs2, a.co_bod, a.fe_doc, a5.tx_nom, "
                                + "        a2.co_tipdoc, a7.tx_descor, a7.tx_deslar, a3.co_cli, a3.tx_nom as tx_nomcli, a2.co_usrsol as co_com, "
                                + "        a4.tx_nom as tx_nomven, '' as co_forret, '' as desforret, a2.co_doc as codocdoc , a2.fe_Doc as fedocdoc, "
                                + "        a7.st_meringegrfisbod, a7.tx_natDoc , a2.ne_numdoc as numdocdoc, "
                                + "        a.co_locrel, a.co_tipdocrel, a.co_docrel "
                                + " FROM tbm_cabingegrmerbod as a "
                                + " INNER JOIN tbm_cabtipdoc AS a6 ON(a6.co_emp=a.co_emp AND a6.co_loc=a.co_loc AND a6.co_tipdoc=a.co_tipdoc) "
                                + " INNER JOIN tbr_cabSolSalTemMerCabMovInv as a1 on (a1.co_emp=a.co_emp and a1.co_locrel=a.co_locrel and a1.co_tipdocrel=a.co_tipdocrel and a1.co_docrel=a.co_docrel ) "
                                + " INNER JOIN tbm_cabsolsaltemmer as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) "
                                + " INNER JOIN tbm_cabtipdoc AS a7 ON(a7.co_emp=a2.co_emp AND a7.co_loc=a2.co_loc AND a7.co_tipdoc=a2.co_tipdoc) "
                                + " LEFT JOIN tbm_cli AS a3 ON(a3.co_emp=a2.co_emp AND a3.co_cli=a2.co_cli )  "
                                + " LEFT JOIN tbm_usr as a4  ON(a4.co_usr=a2.co_usrsol) "
                                + " LEFT JOIN tbm_bod AS a5 ON(a5.co_emp=a.co_emp AND a5.co_bod=a.co_bod) "
                                + " WHERE a.co_emp=" + rstCab.getString("co_emp") + " AND a.co_loc=" + rstCab.getString("co_loc") + "  AND "
                                + " a.co_tipdoc=" + rstCab.getString("co_tipdoc") + " AND a.co_doc=" + rstCab.getString("co_doc");

                    } 
                    else if (objZafParSis.getCodigoMenu() == 2073)
                    {

                        strSql = "select a.co_tipdoc as co_tipdoccon, a6.tx_descor as tx_descorcon, a6.tx_deslar as tx_deslarcon, "
                                + " a.st_reg,  a.co_emp, a.co_loc, a.ne_numdoc, a.co_doc, a.tx_obs1, a.tx_obs2, a.co_bod, a.fe_doc, a5.tx_nom, "
                                + " a2.co_tipdoc, a7.tx_descor, a7.tx_deslar, a3.co_cli, a3.tx_nom as tx_nomcli, a2.co_usrsol as co_com, a4.tx_nom as tx_nomven, '' as co_forret "
                                + " ,'' as desforret, a2.co_doc as codocdoc , a2.fe_Doc as fedocdoc, a7.st_meringegrfisbod, a7.tx_natDoc , a2.ne_numdoc as numdocdoc "
                                + " ,a.co_locrel, a.co_tipdocrel, a.co_docrel "
                                + " from tbm_cabingegrmerbod as a "
                                + " INNER JOIN tbm_cabtipdoc AS a6 ON(a6.co_emp=a.co_emp AND a6.co_loc=a.co_loc AND a6.co_tipdoc=a.co_tipdoc) "
                                + " inner join tbr_cabSolSalTemMerCabMovInv as a1 on (a1.co_emp=a.co_emp and a1.co_locrel=a.co_locrel and a1.co_tipdocrel=a.co_tipdocrel and a1.co_docrel=a.co_docrel ) "
                                + " inner join tbm_cabsolsaltemmer as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) "
                                + " INNER JOIN tbm_cabtipdoc AS a7 ON(a7.co_emp=a2.co_emp AND a7.co_loc=a2.co_loc AND a7.co_tipdoc=a2.co_tipdoc) "
                                + "   left JOIN tbm_cli AS a3 ON(a3.co_emp=a2.co_emp AND a3.co_cli=a2.co_cli )  "
                                + "   left join tbm_usr as a4  ON(a4.co_usr=a2.co_usrsol) "
                                + " LEFT  JOIN tbm_bod AS a5 ON(a5.co_emp=a.co_emp AND a5.co_bod=a.co_bod) "
                                + " WHERE a.co_emp=" + rstCab.getString("co_emp") + " AND a.co_loc=" + rstCab.getString("co_loc") + "  AND "
                                + " a.co_tipdoc=" + rstCab.getString("co_tipdoc") + " AND a.co_doc=" + rstCab.getString("co_doc");

                    }
                    else if ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915))
                    {
                        strSql = "SELECT a1.co_ptopar,  a.co_locrelguirem as co_locrel, a.co_tipdocrelguirem as co_tipdocrel, a.co_docrelguirem as co_docrel,  a.co_tipdoc as co_tipdoccon, a6.tx_descor as tx_descorcon, a6.tx_deslar as tx_deslarcon,  "
                                + "  a.st_reg,  a.co_emp, a.co_loc, a.ne_numdoc, a.co_doc, a.tx_obs1, a.tx_obs2, a.co_bod, a.fe_doc, a5.tx_nom,     "
                                + "  a1.co_tipdoc, a2.tx_descor, a2.tx_deslar, a1.co_clides AS co_cli, a1.tx_nomclides as tx_nomcli, a1.co_ven as co_com, a1.tx_nomven, a1.co_forret   "
                                + "  ,a4.tx_deslar as desforret, a1.co_doc as codocdoc , a1.fe_Doc as fedocdoc, a2.st_meringegrfisbod, a2.tx_natDoc , a1.ne_numdoc as numdocdoc, a1.ne_numorddes "
                                + "  ,a7.co_veh, a7.tx_pla, a7.tx_deslar AS tx_deslarveh, a8.co_tra, a8.tx_ide AS tx_idecho, a8.tx_nom || ' ' || a8.tx_ape AS tx_nomcho, a.tx_idetra, a.tx_nomtra, a.tx_plavehtra "
                                + " FROM tbm_cabingegrmerbod AS a "
                                + " INNER JOIN tbm_cabtipdoc AS a6 ON(a6.co_emp=a.co_emp AND a6.co_loc=a.co_loc AND a6.co_tipdoc=a.co_tipdoc) "
                                + " INNER JOIN tbm_cabguirem AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrelguirem AND a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem)    "
                                + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipdoc=a1.co_tipdoc) "
                                + " LEFT  JOIN tbm_var AS a4 on (a4.co_reg=a1.co_forret) "
                                + " LEFT  JOIN tbm_bod AS a5 ON(a5.co_emp=a.co_emp AND a5.co_bod=a.co_bod) "
                                + " LEFT  JOIN tbm_veh AS a7 ON(a7.co_veh=a.co_veh) "
                                + " LEFT  JOIN tbm_tra AS a8 ON(a8.co_tra=a.co_cho) "
                                + " WHERE a.co_emp=" + rstCab.getString("co_emp") + " AND a.co_loc=" + rstCab.getString("co_loc") + "  AND "
                                + " a.co_tipdoc=" + rstCab.getString("co_tipdoc") + " AND a.co_doc=" + rstCab.getString("co_doc");

                    }
                    else if (objZafParSis.getCodigoMenu() == 1974)
                    {
                        strSql = "SELECT a1.co_ptopar,  a.co_locrelguirem as co_locrel, a.co_tipdocrelguirem as co_tipdocrel, a.co_docrelguirem as co_docrel,  a.co_tipdoc as co_tipdoccon, a6.tx_descor as tx_descorcon, a6.tx_deslar as tx_deslarcon,  "
                                + "  a.st_reg,  a.co_emp, a.co_loc, a.ne_numdoc, a.co_doc, a.tx_obs1, a.tx_obs2, a.co_bod, a.fe_doc, a5.tx_nom,     "
                                + "  a1.co_tipdoc, a2.tx_descor, a2.tx_deslar, a1.co_clides AS co_cli, a1.tx_nomclides as tx_nomcli, a1.co_ven as co_com, a1.tx_nomven, a1.co_forret   "
                                + "  ,a4.tx_deslar as desforret, a1.co_doc as codocdoc , a1.fe_Doc as fedocdoc, a2.st_meringegrfisbod, a2.tx_natDoc , a1.ne_numdoc as numdocdoc "
                                + " FROM tbm_cabingegrmerbod AS a "
                                + " INNER JOIN tbm_cabtipdoc AS a6 ON(a6.co_emp=a.co_emp AND a6.co_loc=a.co_loc AND a6.co_tipdoc=a.co_tipdoc) "
                                + " INNER JOIN tbm_cabguirem AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrelguirem AND a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem)    "
                                + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipdoc=a1.co_tipdoc) "
                                + " LEFT  JOIN tbm_var AS a4 on (a4.co_reg=a1.co_forret) "
                                + " LEFT  JOIN tbm_bod AS a5 ON(a5.co_emp=a.co_emp AND a5.co_bod=a.co_bod) "
                                + " WHERE a.co_emp=" + rstCab.getString("co_emp") + " AND a.co_loc=" + rstCab.getString("co_loc") + "  AND "
                                + " a.co_tipdoc=" + rstCab.getString("co_tipdoc") + " AND a.co_doc=" + rstCab.getString("co_doc");

                    }
                    else 
                    {

                        strSql =  " SELECT a.co_locrel, a.co_tipdocrel, a.co_docrel,  a.co_tipdoc as co_tipdoccon, a6.tx_descor as tx_descorcon, a6.tx_deslar as tx_deslarcon, "
                                + "        a.st_reg,  a.co_emp, a.co_loc, a.ne_numdoc, a.co_doc, a.tx_obs1, a.tx_obs2, " + rstCab.getString("co_bod") + " as co_ptopar, a.fe_doc, a5.tx_nom, "
                                + "        a1.co_tipdoc, a2.tx_descor, a2.tx_deslar, a1.co_cli, a1.tx_nomcli, a1.co_com, a1.tx_nomven, a1.co_forret, a4.tx_deslar as desforret,  "
                                + "        a1.co_doc as codocdoc , a1.fe_Doc as fedocdoc, a2.st_meringegrfisbod, a2.tx_natDoc , a1.ne_numdoc as numdocdoc "
                                + " FROM tbm_cabingegrmerbod AS a "
                                + " INNER JOIN tbm_cabtipdoc AS a6 ON(a6.co_emp=a.co_emp AND a6.co_loc=a.co_loc AND a6.co_tipdoc=a.co_tipdoc) "
                                + " INNER JOIN tbm_cabmovinv AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel) "
                                + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipdoc=a1.co_tipdoc) "
                                + " LEFT  JOIN tbm_var AS a4 on (a4.co_reg=a1.co_forret) "
                                + " LEFT  JOIN tbm_bod AS a5 ON(a5.co_emp=a.co_emp AND a5.co_bod=a.co_bod) "
                                + " WHERE a.co_emp=" + rstCab.getString("co_emp") + " AND a.co_loc=" + rstCab.getString("co_loc") + "   "
                                + " AND a.co_tipdoc=" + rstCab.getString("co_tipdoc") + " AND a.co_doc=" + rstCab.getString("co_doc");
                    }

                    rstDatDoc = stmLoc.executeQuery(strSql);
                    if (rstDatDoc.next()) 
                    {
                        STR_COD_EMP_REL = rstDatDoc.getString("co_emp");
                        STR_COD_LOC_REL = rstDatDoc.getString("co_locrel");
                        STR_COD_BOD_GUIA = rstDatDoc.getString("co_ptopar");

                        if (objZafParSis.getCodigoMenu() == 2205) {
                            txtNumGuiDes.setText(rstDatDoc.getString("ne_numorddes"));
                        }

                        STRCODEMPTRANS = rstDatDoc.getString("co_emp");
                        STRCODLOCTRANS = rstDatDoc.getString("co_locrel");
                        STRCODTIPTRANS = rstDatDoc.getString("co_tipdocrel");
                        STRCODDOCTRANS = rstDatDoc.getString("co_docrel");

                        strAux = rstDatDoc.getString("st_reg");

                        txtCodTipDoc.setText(rstDatDoc.getString("co_tipdoccon"));
                        txtDesCodTitpDoc.setText(rstDatDoc.getString("tx_descorcon"));
                        txtDesLarTipDoc.setText(rstDatDoc.getString("tx_deslarcon"));

                        strTipIngEgr = rstDatDoc.getString("tx_natDoc");
                        strMerIngEgr = rstDatDoc.getString("st_meringegrfisbod");

                        txtCodLocRel.setText(rstDatDoc.getString("co_loc"));
                        txtCodTipDocCon.setText(rstDatDoc.getString("co_tipdoc"));
                        txtDesCorTipDocCon.setText(rstDatDoc.getString("tx_descor"));
                        txtDesLarTipDocCon.setText(rstDatDoc.getString("tx_deslar"));
                        txtCodCliPro.setText(rstDatDoc.getString("co_cli"));
                        txtNomCliPro.setText(rstDatDoc.getString("tx_nomcli"));
                        txtCodVenCom.setText(rstDatDoc.getString("co_com"));
                        txtNomVenCom.setText(rstDatDoc.getString("tx_nomven"));
                        txtCodForRet.setText(rstDatDoc.getString("co_forret"));
                        txtDesForRet.setText(rstDatDoc.getString("desforret"));
                        txtFecDocCon.setText(rstDatDoc.getString("fedocdoc"));
                        txtCodDocCon.setText(rstDatDoc.getString("codocdoc"));

                        ///  txtCodBod.setText(rstDatDoc.getString("co_bod"));
                        //  txtNomBod.setText(rstDatDoc.getString("tx_nom"));
                        //       strCodBod=rstDatDoc.getString("co_ptopar");
                        //       strNomBod=rstDatDoc.getString("tx_nom");
                        
                        txtCodDoc.setText(rstDatDoc.getString("co_doc"));
                        txtNumDoc.setText(rstDatDoc.getString("ne_numdoc"));
                        txtNumDocSolOcu.setText(rstDatDoc.getString("ne_numdoc"));

                        txtNumDocCon.setText(rstDatDoc.getString("numdocdoc"));

                        if ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915)) 
                        {
                            txtCodVeh.setText(rstDatDoc.getString("co_veh"));
                            txtPlaVeh.setText(rstDatDoc.getString("tx_pla"));
                            txtDesLarVeh.setText(rstDatDoc.getString("tx_deslarveh"));
                            txtCodCho.setText(rstDatDoc.getString("co_tra"));
                            txtIdeCho.setText(rstDatDoc.getString("tx_idecho"));
                            txtNomCho.setText(rstDatDoc.getString("tx_nomcho"));
                            txtIdeTra.setText(rstDatDoc.getString("tx_idetra"));
                            txtNomTra.setText(rstDatDoc.getString("tx_nomtra"));
                            txtPlaVehTra.setText(rstDatDoc.getString("tx_plavehtra"));

                            if (rstDatDoc.getString("co_veh") == null) {
                                optRet.setSelected(true);

                                if (txtNomTra.getText().equalsIgnoreCase(txtNomCliPro.getText())) {
                                    optVehCli.setSelected(true);
                                } else {
                                    optVehTra.setSelected(true);
                                }
                            } else {
                                optEnv.setSelected(true);
                            }
                        }

                        txtObs1.setText(rstDatDoc.getString("tx_obs1"));
                        txtObs2.setText(rstDatDoc.getString("tx_obs2"));
                        java.util.Date dateObj = rstDatDoc.getDate("fe_doc");
                        if (dateObj == null) {
                            txtFecDoc.setText("");
                        } else {
                            java.util.Calendar calObj = java.util.Calendar.getInstance();
                            calObj.setTime(dateObj);
                            txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                    calObj.get(java.util.Calendar.MONTH) + 1,
                                    calObj.get(java.util.Calendar.YEAR));
                        }

                    }
                    rstDatDoc.close();
                    rstDatDoc = null;

                    int intEst = 0;
                    vecData = new Vector();

                    String strAux2 = " , CASE WHEN ( (trim(SUBSTR (UPPER(a1.tx_codalt), length(a1.tx_codalt) ,1)) IN ( "
                                   + " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a "
                                   + " INNER JOIN tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) "
                                   + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " and a.co_loc=" + objZafParSis.getCodigoLocal() + " and a1.st_reg='A' and   a.st_reg='P' ))) "
                                   + " THEN 'S' ELSE 'N' END AS proconf  ";

                    if (objZafParSis.getCodigoMenu() == 2063) 
                    {
                        strSql = "select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,  abs(a.nd_can) as nd_can , a.tx_obs1,  a2.nd_canNunegr as nd_canNunRec, a2.nd_cantotegr as nd_cancon "
                                + " ,a1.co_itmact, a2.co_reg, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a2.co_bod, a1.st_meringegrfisbod, abs(a2.nd_can) as nd_canmov "
                                + " from tbm_detingegrmerbod as a "
                                + " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel)  "
                                + " INNER JOIN tbm_detsolsaltemmer AS a2 ON (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrelsolsaltemmer and a2.co_tipdoc=a1.co_tipdocrelsolsaltemmer  "
                                + " and a2.co_doc=a1.co_docrelsolsaltemmer and a2.co_reg=a1.co_regrelsolsaltemmer) "
                                + " WHERE a.co_emp=" + rstCab.getString("co_emp") + " AND a.co_loc=" + rstCab.getString("co_loc") + "  AND "
                                + " a.co_tipdoc=" + rstCab.getString("co_tipdoc") + " AND a.co_doc=" + rstCab.getString("co_doc") + "  ORDER BY  a.co_reg ";
                        rstLoc = stmLoc.executeQuery(strSql);
                        while (rstLoc.next()) 
                        {
                            java.util.Vector vecReg = new java.util.Vector();
                            vecReg.add(INT_TBL_LINEA, "");
                            vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itmact"));
                            vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                            vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                            vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                            vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_canmov"));
                            vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));
                            vecReg.add(INT_TBL_CANCON, rstLoc.getString("nd_can"));
                            vecReg.add(INT_TBL_CANNUMREC, rstLoc.getString("nd_canNunRec"));
                            vecReg.add(INT_TBL_CANMALEST, "");
                            vecReg.add(INT_TBL_CANTOTNUMREC, "");
                            vecReg.add(INT_TBL_OBSITMCON, rstLoc.getString("tx_obs1"));

                            vecReg.add(INT_TBL_CANCON_ORI, rstLoc.getString("nd_can"));
                            vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                            vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                            vecReg.add(INT_TBL_CANNUMREC_ORI, rstLoc.getString("nd_canNunRec"));
                            vecReg.add(INT_TBL_CANMALEST_ORI, "");
                            vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meringegrfisbod"));
                            vecReg.add(INT_TBL_CANTOTMALEST, "");
                            vecReg.add(INT_TBL_CODEMPREL, null);
                            vecReg.add(INT_TBL_CODLOCREL, null);
                            vecReg.add(INT_TBL_CODTIDOREL, null);
                            vecReg.add(INT_TBL_CODDOCREL, null);
                            vecReg.add(INT_TBL_CODREGREL, null);
                            vecReg.add(INT_TBL_TXNOMBODREL, null);
                            vecReg.add(INT_TBL_CODBODREL, null);

                            vecData.add(vecReg);
                        }
                        objTblMod.setData(vecData);
                        tblDat.setModel(objTblMod);
                        rstLoc.close();
                        rstLoc = null;
                    }

                    if ((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 2915)) 
                    {
                        /*strSql="SELECT a.nd_canNunRec as cannunrecingegr,  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, abs(a.nd_can) as nd_canconf, a.nd_can, a.tx_obs1, a1.nd_canNunRec, a1.nd_cancon ,  "+
                         " a1.co_itm, a1.co_reg, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, "+STR_COD_BOD_GUIA+" as co_bod ,  abs(a1.nd_can) as nd_canmov, a1.st_meregrfisbod   " +
                         " FROM tbm_detingegrmerbod AS a " +
                         " INNER JOIN tbm_detguirem AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelguirem and a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem and a1.co_reg=a.co_regrelguirem)  " +
                         " WHERE a.co_emp="+rstCab.getString("co_emp")+" AND a.co_loc="+rstCab.getString("co_loc")+"  AND " +
                         " a.co_tipdoc="+rstCab.getString("co_tipdoc")+" AND a.co_doc="+rstCab.getString("co_doc")+"  ORDER BY  a.co_reg ";*/
                        strSql =  " SELECT a.nd_canNunRec as cannunrecingegr,  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, abs(a.nd_can) as nd_canconf, a.nd_can, a.tx_obs1, abs(a.nd_canNunRec) as nd_canNunRec, a1.nd_cancon ,  "
                                + "        a1.co_itm, a1.co_reg, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, " + STR_COD_BOD_GUIA + " as co_bod ,  abs(a1.nd_can) as nd_canmov, a1.st_meregrfisbod   "
                                + " FROM tbm_detingegrmerbod AS a "
                                + " INNER JOIN tbm_detguirem AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelguirem and a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem and a1.co_reg=a.co_regrelguirem)  "
                                + " WHERE a.co_emp=" + rstCab.getString("co_emp") + " AND a.co_loc=" + rstCab.getString("co_loc") + "  AND "
                                + " a.co_tipdoc=" + rstCab.getString("co_tipdoc") + " AND a.co_doc=" + rstCab.getString("co_doc") + "  ORDER BY  a.co_reg ";
                        rstLoc = stmLoc.executeQuery(strSql);
                        while (rstLoc.next()) 
                        {

                            strTipModIngEgr = "2";

                            java.util.Vector vecReg = new java.util.Vector();
                            vecReg.add(INT_TBL_LINEA, "");
                            vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
                            vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                            vecReg.add(INT_TBL_CODALTDOS, null);
                            vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                            vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                            vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_canmov"));
                            vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));
                            vecReg.add(INT_TBL_CANCON, rstLoc.getString("nd_canconf"));
                            vecReg.add(INT_TBL_CANNUMREC, rstLoc.getString("cannunrecingegr"));
                            vecReg.add(INT_TBL_CANMALEST, "");
                            vecReg.add(INT_TBL_CANTOTNUMREC, rstLoc.getString("nd_canNunRec"));
                            vecReg.add(INT_TBL_OBSITMCON, rstLoc.getString("tx_obs1"));

                            vecReg.add(INT_TBL_CANCON_ORI, rstLoc.getString("nd_canconf"));
                            vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                            vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                            vecReg.add(INT_TBL_CANNUMREC_ORI, rstLoc.getString("cannunrecingegr"));
                            vecReg.add(INT_TBL_CANMALEST_ORI, "");
                            vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meregrfisbod"));
                            vecReg.add(INT_TBL_CANTOTMALEST, "");
                            vecReg.add(INT_TBL_CODEMPREL, null);
                            vecReg.add(INT_TBL_CODLOCREL, null);
                            vecReg.add(INT_TBL_CODTIDOREL, null);
                            vecReg.add(INT_TBL_CODDOCREL, null);
                            vecReg.add(INT_TBL_CODREGREL, null);
                            vecReg.add(INT_TBL_TXNOMBODREL, null);
                            vecReg.add(INT_TBL_CODBODREL, null);
                            vecReg.add(INT_TBL_PESKGR, null);
                            vecReg.add(INT_TBL_CODALTAUX, null);
                            vecReg.add(INT_TBL_NOMITMAUX, null);
                            vecReg.add(INT_TBL_CODREGRELDOCEGR, null);

                            vecData.add(vecReg);
                        }
                        objTblMod.setData(vecData);
                        tblDat.setModel(objTblMod);
                        rstLoc.close();
                        rstLoc = null;
                    }

                    if (objZafParSis.getCodigoMenu() == 286) //Rose 07/Mar/2016
                    {
                        strSql =  " SELECT  a.nd_canNunRec as cannunrecingegr, a.nd_canMalEst, a1.nd_canTotMalEst, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, abs(a.nd_can) as nd_canconf, a.nd_can, a.tx_obs1, a1.nd_canNunRec, a1.nd_cancon ,"
                                + "         a1.co_itmact, a1.co_reg, a1.tx_codalt, a2.tx_codalt2, a1.tx_nomitm, a1.tx_unimed, a1.co_bod,  abs(a1.nd_can) as nd_canmov, a1.st_meringegrfisbod "
                                + " FROM tbm_detingegrmerbod AS a "
                                + " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel) "
                                + " INNER JOIN tbm_inv as a2 on (a2.co_Emp=a1.co_Emp and a2.co_itm=a1.co_itmact) "
                                + " WHERE a.co_emp=" + rstCab.getString("co_emp") + " AND a.co_loc=" + rstCab.getString("co_loc") + "  AND "
                                + " a.co_tipdoc=" + rstCab.getString("co_tipdoc") + " AND a.co_doc=" + rstCab.getString("co_doc") + "  ORDER BY  a.co_reg ";
                        rstLoc = stmLoc.executeQuery(strSql);
                        System.out.println("Rose.refrescaDatos: "+strSql);
                        while (rstLoc.next()) 
                        {
                            if (intEst == 0) 
                            {
                                if (rstLoc.getDouble("nd_can") > 0) 
                                {
                                    strTipModIngEgr = "1";
                                }
                                if (rstLoc.getDouble("nd_can") < 0) 
                                {
                                    strTipModIngEgr = "2";
                                }
                                intEst = 1;
                            }

                            java.util.Vector vecReg = new java.util.Vector();
                            vecReg.add(INT_TBL_LINEA, "");
                            vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itmact"));
                            vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                            //vecReg.add(INT_TBL_CODALTDOS, null); //Antes Rose 07/Mar/2016
                            vecReg.add(INT_TBL_CODALTDOS, rstLoc.getString("tx_codalt2"));  //Rose 07/Mar/2016
                            vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                            vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                            vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_canmov"));
                            vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));
                            vecReg.add(INT_TBL_CANCON, rstLoc.getString("nd_canconf"));
                            vecReg.add(INT_TBL_CANNUMREC, rstLoc.getString("cannunrecingegr"));
                            vecReg.add(INT_TBL_CANMALEST, rstLoc.getString("nd_canMalEst"));
                            vecReg.add(INT_TBL_CANTOTNUMREC, rstLoc.getString("nd_canNunRec"));
                            vecReg.add(INT_TBL_OBSITMCON, rstLoc.getString("tx_obs1"));

                            vecReg.add(INT_TBL_CANCON_ORI, rstLoc.getString("nd_canconf"));
                            vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                            vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                            vecReg.add(INT_TBL_CANNUMREC_ORI, rstLoc.getString("cannunrecingegr"));
                            vecReg.add(INT_TBL_CANMALEST_ORI, rstLoc.getString("nd_canMalEst"));
                            vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meringegrfisbod"));
                            vecReg.add(INT_TBL_CANTOTMALEST, rstLoc.getString("nd_canTotMalEst"));
                            vecReg.add(INT_TBL_CODEMPREL, null);
                            vecReg.add(INT_TBL_CODLOCREL, null);
                            vecReg.add(INT_TBL_CODTIDOREL, null);
                            vecReg.add(INT_TBL_CODDOCREL, null);
                            vecReg.add(INT_TBL_CODREGREL, null);
                            vecReg.add(INT_TBL_TXNOMBODREL, null);
                            vecReg.add(INT_TBL_CODBODREL, null);
                            vecReg.add(INT_TBL_PESKGR, null);
                            vecReg.add(INT_TBL_CODALTAUX, null);
                            vecReg.add(INT_TBL_NOMITMAUX, null);
                            vecReg.add(INT_TBL_CODREGRELDOCEGR,null);
                            vecData.add(vecReg);
                        }
                        objTblMod.setData(vecData);
                        tblDat.setModel(objTblMod);
                        rstLoc.close();
                        rstLoc = null;
                    }
                    if (objZafParSis.getCodigoMenu() == 1974) 
                    {
                        strSql = "SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,  abs(a.nd_can) as nd_can , a.tx_obs1, a1.nd_canNunRec, a1.nd_cancon ,"
                                + " a1.co_itm, a1.co_reg, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, " + STR_COD_BOD_GUIA + " as co_bod , a1.st_meregrfisbod as st_meringegrfisbod "
                                + //" CASE WHEN a1.st_meringegrfisbod IN ('S') THEN abs(a1.nd_cancon) ELSE abs(a1.nd_can) END AS nd_canmov "+
                                " , abs(a1.nd_can) as nd_canmov "
                                + " FROM tbm_detingegrmerbod AS a "
                                + " INNER JOIN tbm_detguirem AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelguirem and a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem and a1.co_reg=a.co_regrelguirem)  "
                                + " WHERE a.co_emp=" + rstCab.getString("co_emp") + " AND a.co_loc=" + rstCab.getString("co_loc") + "  AND "
                                + " a.co_tipdoc=" + rstCab.getString("co_tipdoc") + " AND a.co_doc=" + rstCab.getString("co_doc") + "  ORDER BY  a.co_reg ";
                        rstLoc = stmLoc.executeQuery(strSql);
                        while (rstLoc.next()) 
                        {
                            java.util.Vector vecReg = new java.util.Vector();
                            vecReg.add(INT_TBL_LINEA, "");
                            vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
                            vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                            vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                            vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                            vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_canmov"));
                            vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));
                            vecReg.add(INT_TBL_CANCON, rstLoc.getString("nd_can"));
                            vecReg.add(INT_TBL_CANNUMREC, rstLoc.getString("nd_canNunRec"));
                            vecReg.add(INT_TBL_CANMALEST, "");
                            vecReg.add(INT_TBL_CANTOTNUMREC, "");
                            vecReg.add(INT_TBL_OBSITMCON, rstLoc.getString("tx_obs1"));

                            vecReg.add(INT_TBL_CANCON_ORI, rstLoc.getString("nd_can"));
                            vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                            vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                            vecReg.add(INT_TBL_CANNUMREC_ORI, rstLoc.getString("nd_canNunRec"));
                            vecReg.add(INT_TBL_CANMALEST_ORI, "");
                            vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meringegrfisbod"));
                            vecReg.add(INT_TBL_CANTOTMALEST, "");
                            vecReg.add(INT_TBL_CODEMPREL, null);
                            vecReg.add(INT_TBL_CODLOCREL, null);
                            vecReg.add(INT_TBL_CODTIDOREL, null);
                            vecReg.add(INT_TBL_CODDOCREL, null);
                            vecReg.add(INT_TBL_CODREGREL, null);
                            vecReg.add(INT_TBL_TXNOMBODREL, null);
                            vecReg.add(INT_TBL_CODBODREL, null);
                            vecData.add(vecReg);
                        }
                        objTblMod.setData(vecData);
                        tblDat.setModel(objTblMod);
                        rstLoc.close();
                        rstLoc = null;
                    }

                    if (objZafParSis.getCodigoMenu() == 2073) 
                    {
                        strSql =  " SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,  abs(a.nd_can) as nd_can , a.tx_obs1,  a2.nd_canNunIng as nd_canNunRec, a2.nd_cantoting as nd_cancon, "
                                + "        a1.co_itmact, a2.co_reg, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a2.co_bod, a1.st_meringegrfisbod, abs(a2.nd_can) as nd_canmov "
                                + " FROM tbm_detingegrmerbod as a "
                                + " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel)  "
                                + " INNER JOIN tbm_detsolsaltemmer AS a2 ON (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrelsolsaltemmer and a2.co_tipdoc=a1.co_tipdocrelsolsaltemmer  "
                                + " AND a2.co_doc=a1.co_docrelsolsaltemmer and a2.co_reg=a1.co_regrelsolsaltemmer) "
                                + " WHERE a.co_emp=" + rstCab.getString("co_emp") + " "
                                + " AND a.co_loc=" + rstCab.getString("co_loc") + "  "
                                + " AND a.co_tipdoc=" + rstCab.getString("co_tipdoc") + " "
                                + " AND a.co_doc=" + rstCab.getString("co_doc") + "  "
                                + " ORDER BY  a.co_reg ";
                        rstLoc = stmLoc.executeQuery(strSql);
                        while (rstLoc.next()) 
                        {
                            java.util.Vector vecReg = new java.util.Vector();
                            vecReg.add(INT_TBL_LINEA, "");
                            vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itmact"));
                            vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                            vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                            vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                            vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_canmov"));
                            vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));
                            vecReg.add(INT_TBL_CANCON, rstLoc.getString("nd_can"));
                            vecReg.add(INT_TBL_CANNUMREC, rstLoc.getString("nd_canNunRec"));
                            vecReg.add(INT_TBL_CANMALEST, "");
                            vecReg.add(INT_TBL_CANTOTNUMREC, "");
                            vecReg.add(INT_TBL_OBSITMCON, rstLoc.getString("tx_obs1"));
                            vecReg.add(INT_TBL_CANCON_ORI, rstLoc.getString("nd_can"));
                            vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                            vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                            vecReg.add(INT_TBL_CANNUMREC_ORI, rstLoc.getString("nd_canNunRec"));
                            vecReg.add(INT_TBL_CANMALEST_ORI, "");
                            vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meringegrfisbod"));
                            vecReg.add(INT_TBL_CANTOTMALEST, "");
                            vecReg.add(INT_TBL_CODEMPREL, null);
                            vecReg.add(INT_TBL_CODLOCREL, null);
                            vecReg.add(INT_TBL_CODTIDOREL, null);
                            vecReg.add(INT_TBL_CODDOCREL, null);
                            vecReg.add(INT_TBL_CODREGREL, null);
                            vecReg.add(INT_TBL_TXNOMBODREL, null);
                            vecReg.add(INT_TBL_CODBODREL, null);
                            vecData.add(vecReg);
                        }
                        objTblMod.setData(vecData);
                        tblDat.setModel(objTblMod);
                        rstLoc.close();
                        rstLoc = null;
                    }

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

                    int intPosRel = rstCab.getRow();
                    rstCab.last();
                    objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
                    rstCab.absolute(intPosRel);

                }
            } 
            catch (java.sql.SQLException Evt) {     blnRes = false;    objUti.mostrarMsgErr_F1(this, Evt);    } 
            catch (Exception Evt) {    blnRes = false;   objUti.mostrarMsgErr_F1(this, Evt);  }
            return blnRes;
        }

        private String FilSql() 
        {
            String sqlFiltro = "";
            
            if (!txtCodTipDoc.getText().equals("")) {
                sqlFiltro = sqlFiltro + " and a.co_tipdoc=" + txtCodTipDoc.getText();
            }

            if (!((objZafParSis.getCodigoMenu() == 2205) || (objZafParSis.getCodigoMenu() == 286) || (objZafParSis.getCodigoMenu() == 2915))) {
                if (!txtCodBod.getText().equals("")) {
                    sqlFiltro = sqlFiltro + " and a.co_bod=" + txtCodBod.getText();
                }
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
            bloquea(txtDesCodTitpDoc, colBack, false);
            bloquea(txtDesLarTipDoc, colBack, false);
            bloquea(txtCodBod, colBack, false);
            bloquea(txtNomBod, colBack, false);
            bloquea(txtNumDocCon, colBack, false);
            bloquea(txtMotTra, colBack, false);

            butTipDoc.setEnabled(false);
            butBusBod.setEnabled(false);
            butBusNumDocCon.setEnabled(false);

            this.setEnabledConsultar(false);

            objTblMod.setDataModelChanged(false);
            blnHayCam = false;
        }

        //******************************************************************************************************
        @Override
        public boolean vistaPreliminar() {
            cargarReporte(1);
            return true;
        }

        @Override
        public boolean imprimir() {
            return true;
        }

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
        public boolean beforeInsertar() {
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

    private void cargarReporte(int intTipo) 
    {
        if (objThrGUI == null) 
        {
            objThrGUI = new ZafThreadGUI();
            objThrGUI.setIndFunEje(intTipo);
            objThrGUI.start();
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
    private class ZafThreadGUI extends Thread
    {
        private int intIndFun;

        public ZafThreadGUI() 
        {
            intIndFun = 0;
        }

        @Override
        public void run() 
        {
            switch (intIndFun) 
            {
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
        public void setIndFunEje(int indice) 
        {
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
        String strRutRpt, strNomRpt, strFecHorSer, strSQLRep;
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
                if (datFecAux == null) 
                {
                    return false;
                }
                strFecHorSer = objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                datFecAux = null;
                intNumTotRpt = objRptSis.getNumeroTotalReportes();
                for (i = 0; i < intNumTotRpt; i++) 
                {
                    if (objRptSis.isReporteSeleccionado(i)) 
                    {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i))) 
                        {
                            case 4:
                                break;
                            default: //Orden de Almacenamiento
                                if(objZafParSis.getCodigoMenu()==286)
                                {
                                    strRutRpt = objRptSis.getRutaReporte(i);
                                    strNomRpt = objRptSis.getNombreReporte(i);

                                    strSQLRep ="";
                                    strSQLRep+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a2.co_reg,a2.co_itm,a2.co_bod, ";
                                    strSQLRep+="        CASE WHEN a5.st_impord='S' THEN a4.tx_codAlt2 ELSE '' END AS tx_codAlt, ";
                                    strSQLRep+="        CASE WHEN a5.st_impord='N' THEN a4.tx_nomItm ELSE '' END AS tx_nomItm, ";
                                    strSQLRep+="        a2.nd_can,a5.tx_ubi,a6.tx_desLar as tx_uniMed, a7.tx_nom as tx_nomEmp, a8.tx_nom as tx_nomLoc, ";
                                    strSQLRep+="        a9.ne_numDoc,a3.tx_desLar as tx_desTipDoc, b1.co_usr, b1.tx_usr, b1.tx_nom AS tx_nomUsrIng ";
                                    strSQLRep+=" FROM (tbm_cabIngEgrMerBod  as a1 INNER JOIN tbm_usr AS b1 ON a1.co_usrIng=b1.co_usr)";
                                    strSQLRep+=" INNER JOIN tbm_detIngEgrMerBod as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_TipDoc AND a1.co_doc=a2.co_doc)";
                                    strSQLRep+=" INNER JOIN tbm_cabTipDoc as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_TipDoc)";
                                    strSQLRep+=" INNER JOIN tbm_inv as a4 ON (a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm)";
                                    strSQLRep+=" INNER JOIN tbm_invBod as a5 ON (a2.co_emp=a5.co_emp AND a2.co_bod=a5.co_bod AND a2.co_itm=a5.co_itm)";
                                    strSQLRep+=" LEFT OUTER JOIN tbm_var as a6 ON (a4.co_uni=a6.co_reg)";
                                    strSQLRep+=" INNER JOIN tbm_emp as a7 ON (a1.co_emp=a7.co_emp)";
                                    strSQLRep+=" INNER JOIN tbm_loc as a8 ON (a1.co_emp=a8.co_emp AND a1.co_loc=a8.co_loc)";
                                    strSQLRep+=" INNER JOIN tbm_cabMovInv as a9 ON (a1.co_emp=a9.co_emp AND a1.co_locRel=a9.co_loc AND a1.co_tipDocRel=a9.co_tipDoc AND a1.co_docRel=a9.co_doc)";
                                    strSQLRep+=" WHERE a1.co_emp=" + STR_COD_EMP_REL + "";
                                    strSQLRep+=" AND a1.co_loc=" + STR_COD_LOC_REL + "";
                                    strSQLRep+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                                    strSQLRep+=" AND a1.co_doc=" + txtCodDoc.getText() + "";
                                    strSQLRep+=" AND a2.nd_can NOT IN(0)";

                                    //Inicializar los parametros que se van a pasar al reporte.
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strCamAudRpt", objZafParSis.getNombreUsuario() + "   " + strFecHorSer + "   " + this.getClass().getName() + "   " + strNomRpt);
                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                    //System.out.println("generarRpt:  " + strSQLRep);
                                }
                                break;
                        }
                    }
                }
            }
        } 
        catch (Exception e) {  blnRes = false;     objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }

    /**
     * Función que permite imprimir la Orden de Almacenamiento
     */
    private boolean imprimirOrdenAlmacenamiento(Connection conexion) 
    {
        boolean blnRes = true;
        String strSQLRep = "";
        Connection conImpOrdAlm = conexion;
        Statement stmImpOrdAlm;
        ResultSet rstImpOrdAlm;

        String strUsu = "", strFecHorSer = "";
        String strNomPrnSer = "";
        String strRutRepOrdAlm = "";
        java.util.Date datFecAux;
        try
        {
            if (conImpOrdAlm != null) 
            {
                switch (Integer.parseInt(txtCodBod.getText())) 
                {
                    case 1://California
                        strNomPrnSer = "od_califormia";
                        break;
                    case 2://Vía Daule
                        strNomPrnSer = "od_dimulti";
                        break;
                    case 3://Quito Norte
                        strNomPrnSer = "od_quito";
                        break;
                    case 5://Manta
                        strNomPrnSer = "od_manta";
                        break;
                    case 11://Santo Domingo
                        strNomPrnSer = "od_stodgo";
                        break;
                    case 15://Inmaconsa
                        strNomPrnSer = "od_inmaconsa";
                        break;
                    case 28://Cuenca
                        strNomPrnSer = "od_cuenca";
                        break;
                    default:
                        break;
                }

                //Obtener la fecha y hora del servidor.
                datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                strFecHorSer = objUti.formatearFecha(datFecAux, "dd/MM/yyyy   HH:mm:ss");
                datFecAux = null;

                stmImpOrdAlm = conImpOrdAlm.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a2.co_reg,a2.co_itm,a2.co_bod, ";
                strSQL+="        CASE WHEN a5.st_impord='S' THEN a4.tx_codAlt2 ELSE '' END AS tx_codAlt, ";
                strSQL+="        CASE WHEN a5.st_impord='N' THEN a4.tx_nomItm ELSE '' END AS tx_nomItm, ";
                strSQL+="        a2.nd_can,a5.tx_ubi,a6.tx_desLar as tx_uniMed, a7.tx_nom as tx_nomEmp, a8.tx_nom as tx_nomLoc, ";
                strSQL+="        a9.ne_numDoc,a3.tx_desLar as tx_desTipDoc, b1.co_usr, b1.tx_usr, b1.tx_nom AS tx_nomUsrIng";
                strSQL+=" FROM (tbm_cabIngEgrMerBod  as a1 INNER JOIN tbm_usr AS b1 ON a1.co_usrIng=b1.co_usr)";
                strSQL+=" INNER JOIN tbm_detIngEgrMerBod as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_TipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_TipDoc)";
                strSQL+=" INNER JOIN tbm_inv as a4 ON (a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm)";
                strSQL+=" INNER JOIN tbm_invBod as a5 ON (a2.co_emp=a5.co_emp AND a2.co_bod=a5.co_bod AND a2.co_itm=a5.co_itm)";
                strSQL+=" LEFT OUTER JOIN tbm_var as a6 ON (a4.co_uni=a6.co_reg)";
                strSQL+=" INNER JOIN tbm_emp as a7 ON (a1.co_emp=a7.co_emp)";
                strSQL+=" INNER JOIN tbm_loc as a8 ON (a1.co_emp=a8.co_emp AND a1.co_loc=a8.co_loc)";
                strSQL+=" INNER JOIN tbm_cabMovInv as a9 ON (a1.co_emp=a9.co_emp AND a1.co_locRel=a9.co_loc AND a1.co_tipDocRel=a9.co_tipDoc AND a1.co_docRel=a9.co_doc)";
                strSQL+=" WHERE a1.co_emp=" + STR_COD_EMP_REL + "";
                strSQL+=" AND a1.co_loc=" + STR_COD_LOC_REL + "";
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND a1.co_doc=" + txtCodDoc.getText() + "";
                strSQL+=" AND a2.nd_can NOT IN(0)";

                strSQLRep = strSQL;
                //System.out.println("imprimirOrdenAlmacenamiento: " + strSQLRep);
                rstImpOrdAlm = stmImpOrdAlm.executeQuery(strSQLRep);
                if (rstImpOrdAlm.next()) 
                {
                    strUsu = rstImpOrdAlm.getString("tx_usr");
                }

                java.util.Map mapPar = new java.util.HashMap();
                //Inicializar los parametros que se van a pasar al reporte.
                mapPar.put("strSQLRep", strSQLRep);
                mapPar.put("strCamAudRpt", "" + strUsu + "    " + strFecHorSer + "");

                //Ruta del Reporte
                if (System.getProperty("os.name").equals("Linux")) //Linux
                {
                    strRutRepOrdAlm = "/Zafiro/Reportes/Compras/ZafCom19/ZafRptCom19.jasper";
                } else //Windows
                {
                    strRutRepOrdAlm = "//172.16.1.2/Zafiro/Reportes/Compras/ZafCom19/ZafRptCom19.jasper";
                }

                // strRutRepOrdAlm=objZafParSis.getDirectorioSistema() + "/Reportes/Compras/ZafCom19/ZafRptCom19.jasper"; //Buscaba la ruta C:Zafiro
                
                //System.out.println("Ruta Orden Almacenamiento: " + strRutRepOrdAlm);

                javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet = new javax.print.attribute.HashPrintRequestAttributeSet();
                objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);

                JasperPrint reportGuiaRem = JasperFillManager.fillReport(strRutRepOrdAlm, mapPar, conImpOrdAlm);
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

                //System.out.println("Se imprimio Orden de Almacenamiento");

                stmImpOrdAlm.close();
                stmImpOrdAlm = null;
                rstImpOrdAlm.close();
                rstImpOrdAlm = null;

            }
        } 
        catch (java.sql.SQLException e) {   objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e) {    objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
}
