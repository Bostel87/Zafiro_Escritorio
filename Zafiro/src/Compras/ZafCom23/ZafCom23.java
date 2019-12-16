/*
 * ZafCom23.java   
 * CONSULTA DE ORDENES DE DESPACHO/GUIAS DE REMISION
 *
 * Created on 30 de junio de 2008, 11:49  
 */
package Compras.ZafCom23;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * @author jayapata 
 */
public class ZafCom23 extends javax.swing.JInternalFrame 
{
    //Constantes: Jtable Detalle.
    private static final int INT_TBL_LIN = 0;
    private static final int INT_TBL_CHK_DOC = 1;
    private static final int INT_TBL_COD_ITM = 2;
    private static final int INT_TBL_COD_ALT_ITM = 3;
    private static final int INT_TBL_COD_ALT_2=4;
    private static final int INT_TBL_NOM_ITM = 5;
    private static final int INT_TBL_DES_COR_UNI_MED = 6;
    private static final int INT_TBL_CAN_ITM = 7;
    private static final int INT_TBL_NUM_DOC = 8;
    private static final int INT_TBL_COD_EMP = 9;
    private static final int INT_TBL_COD_LOC = 10;
    private static final int INT_TBL_COD_TIP_DOC = 11;
    private static final int INT_TBL_COD_DOC = 12;
    private static final int INT_TBL_COD_REG = 13;
    private static final int INT_TBL_MAR_ELI = 14;
    private static final int INT_TBL_PES_KGR = 15;
    private static final int INT_TBL_CAN_TOT_CON = 16;
    private static final int INT_TBL_CAN_PEN_ITM = 17;
    private static final int INT_TBL_CAN_GUI_REM = 18;
    private static final int INT_TBL_MER_EGR_FIS = 19;
    
    //Constantes del ArrayList Elementos Eliminados
    static final int INT_ARR_COD_EMP = 0;
    static final int INT_ARR_COD_LOC = 1;
    static final int INT_ARR_TIP_DOC = 2;
    static final int INT_ARR_COD_DOC = 3;
    
    //Constantes Envio de Requisito de Impresión.
    int INT_ENV_REC_IMP_LOCTUV = 0;
    int INT_ENV_REC_IMP_LOCDIM = 0;
    int INT_ENV_REC_IMP_LOCQUI = 0;
    int INT_ENV_REC_IMP_LOCMAN = 0;
    int INT_ENV_REC_IMP_LOCSTD = 0;
    
    //Constantes Puerto de Impresión.
    int INT_PUE_LOC_TUV = 0;
    int INT_PUE_LOC_DIM = 0;
    int INT_PUE_LOC_UIO = 0;
    int INT_PUE_LOC_MAN = 0;
    int INT_PUE_LOC_STO_DGO = 0;
    int INT_PUE_LOC_CUE = 0;
    int INT_COD_REG_CEN = 0;
    
    //Variables
    private ZafParSis objParSis;
    private mitoolbar objTooBar;
    private ZafDatePicker txtFecDoc;
    private ZafDatePicker txtFecIniTrans;
    private ZafDatePicker txtFecTerTrans;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu ZafTblPopMn;
    private ZafInvItm objInvItm;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafThreadGUI objThrGUI;
    private ZafRptSis objRptSis;                                                //Reportes del Sistema.
    private ZafUtil objUti;

    //Ventanas de Consulta ZafVenCon.
    private ZafVenCon vcoTipDoc;
    private ZafVenCon vcoPtoPar;
    private ZafVenCon vcoCliDes;
    private ZafVenCon vcoPtoLle;
    private ZafVenCon vcoMotTra;
    private ZafVenCon vcoGuiRemOrdDes;
    
    private String strFecSis;
    private String  strSQL="", strAux = "";
    private String strCodTipDoc = "", strDesCorTipDoc = "", strDesLarTipDoc = "";
    private String strCodPtoPar = "", strDesPtoPar = "";
    private String strCodCliDes = "", strNomCliDes = "", strRucCli = "", strCiuCli = "", strTelCli = "";
    private String strCodPtoLle, strDesPtoLle;
    private String strPtoDes = "";
    private String strCodMotTra = "", strDesCorMotTra = "", strDesLarMotTra = "";
    private String strCodEmpGuiPrin = "", strCodLocGuiPrin = "", strCodTipDocGuiPrin = "", strCodDocGuiPrin = "";
    private String strCodEmpSolDev = "", strCodLocSolDev = "", strCodTipDocSolDev = "", strCodDocSolDev = "";
    private String strCodEmpGuia = "", strCodLocGuia = "", strCodTipDocGuia = "", strCodDocGuia = "";
    private String strDocOriGuiRem = ""; 
    private String strCodVenGuiPrin = "", strNomVenGuiPrin = "";
    private String strEstConf = "";
    private String strDev="";
    private String strIpLocTuv = "";
    private String strIpLocDim = "";
    private String strIpLocQui = "", strIpLocMan = "", strIpLocSTD = "", strIpLocCue = "";
    private String strVersion = " v3.31 ";
    
    JTextField txtCodTipDoc = new JTextField();
    JTextField txtCodPunPar = new JTextField();
    JTextField txtCodMotTrans = new JTextField();
    JTextField txtRucCli = new JTextField();
    JTextField txtCiuCli = new JTextField();
    JTextField txtTelCli = new JTextField();
    JTextField txtNumPed = new JTextField();
    
    Connection CONN_GLO = null, conCab = null;
    Statement STM_GLO = null;
    ResultSet rstCab = null;
    private java.util.Date datFecAux; 
    Vector vecData = new Vector();
    
    private boolean blnHayCam = false;
    private boolean blnEstCarGuia = false;
    private boolean blnIsGuiRem = false;
    private JInternalFrame JIntFra; 
    
    //<editor-fold defaultstate="collapsed" desc="/* Constructores */">
    
    /**
     * Creates new form ZafCom23
     */
    public ZafCom23(Librerias.ZafParSis.ZafParSis obj) 
    {
        try 
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            this.setTitle(objParSis.getNombreMenu() + strVersion);
            lblTit.setText(objParSis.getNombreMenu());

            objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objParSis);

            objUti = new ZafUtil();
            objTooBar = new mitoolbar(this);

            this.getContentPane().add(objTooBar, "South");

            objRptSis = new ZafRptSis(JOptionPane.getFrameForComponent(this), true, objParSis);

            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleModificar(false);

            if (objParSis.getCodigoMenu() == 2216) 
            {
                objTooBar.setVisibleAnular(false);
                objTooBar.setVisibleConsultar(false);
                objTooBar.setVisibleImprimir(false);
                objTooBar.setVisibleVistaPreliminar(false);
            }

            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText(strFecSis);
            panGenCab.add(txtFecDoc);
            txtFecDoc.setBounds(569, 1, 90, 20);

            txtFecIniTrans = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecIniTrans.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecIniTrans.setText(strFecSis);
            panGenCab.add(txtFecIniTrans);
            txtFecIniTrans.setBounds(569, 20, 90, 20);

            txtFecTerTrans = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecTerTrans.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecTerTrans.setText(strFecSis);
            panGenCab.add(txtFecTerTrans);
            txtFecTerTrans.setBounds(569, 40, 90, 20);

            abrirCon();
            cargarIpPuertoGuiaEmp();
            CerrarCon();

            if (objParSis.getCodigoMenu() == 1793)   // Guia de Remisión
            {
                butGuiRem.setVisible(false);
                lblNumOrdDes.setVisible(false);
                txtNumOrdDes.setVisible(false);
                optConOrdDes.setVisible(false);
                optConGuiRem.setSelected(true);
                // txtNumDocGuia.setVisible(false);
            }

            if (objParSis.getCodigoMenu() == 3497) // Orden de Despacho
            {  
                butGuiRem.setVisible(false);
                lblNumGuiRem.setVisible(false);
                txtNumGuiRem.setVisible(false);
                optConGuiRem.setVisible(false);
            }

            if (objParSis.getCodigoMenu() == 2216) // Crear Guia 
            {  
                lblTipOrdCon.setVisible(false);
                optOrdDes.setVisible(false);
                optGuiRem.setVisible(false);
                chkTieGuiRem.setVisible(false);
                lblTieGuiRem.setVisible(false);
                butTieGuiRem.setVisible(false);
            }

        }
        catch (CloneNotSupportedException e) {  objUti.mostrarMsgErr_F1(this, e);   }
    }

    /**
     * Creates new form ZafCom23
     */
    public ZafCom23(Librerias.ZafParSis.ZafParSis obj, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) 
    {
        try
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            this.setTitle(objParSis.getNombreMenu() + strVersion);
            lblTit.setText(objParSis.getNombreMenu());

            objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objParSis);

            strCodEmpGuia = strCodEmp;
            strCodLocGuia = strCodLoc;
            strCodTipDocGuia = strCodTipDoc;
            strCodDocGuia = strCodDoc;
            blnEstCarGuia = true;

            objUti = new ZafUtil();
            objTooBar = new mitoolbar(this);

            // this.getContentPane().add(objTooBar,"South");

            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleModificar(false);

            if (objParSis.getCodigoMenu() == 2216) 
            {
                objTooBar.setVisibleAnular(false);
                objTooBar.setVisibleConsultar(false);
                objTooBar.setVisibleImprimir(false);
                objTooBar.setVisibleVistaPreliminar(false);
            }

            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText(strFecSis);
            panGenCab.add(txtFecDoc);
            txtFecDoc.setBounds(569, 1, 90, 20);

            txtFecIniTrans = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecIniTrans.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecIniTrans.setText(strFecSis);
            panGenCab.add(txtFecIniTrans);
            txtFecIniTrans.setBounds(569, 20, 90, 20);

            txtFecTerTrans = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecTerTrans.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecTerTrans.setText(strFecSis);
            panGenCab.add(txtFecTerTrans);
            txtFecTerTrans.setBounds(569, 40, 90, 20);

            abrirCon();
            cargarIpPuertoGuiaEmp();
            CerrarCon();

            if (objParSis.getCodigoMenu() == 1793) 
            {
                butGuiRem.setVisible(false);
                txtNumGuiRem.setVisible(false);
            }
            if (objParSis.getCodigoMenu() == 2216) 
            {
                lblTipOrdCon.setVisible(false);
                optOrdDes.setVisible(false);
                optGuiRem.setVisible(false);
                chkTieGuiRem.setVisible(false);
                lblTieGuiRem.setVisible(false);
                butTieGuiRem.setVisible(false);
            }
        } 
        catch (CloneNotSupportedException e) {    objUti.mostrarMsgErr_F1(this, e); }
    }
    

    public ZafCom23(Librerias.ZafParSis.ZafParSis obj, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, int intConnRem) 
    {
        try 
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            this.setTitle(objParSis.getNombreMenu() + strVersion);
            lblTit.setText(objParSis.getNombreMenu());

            objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objParSis);

            INT_COD_REG_CEN = intConnRem;
            strCodEmpGuia = strCodEmp;
            strCodLocGuia = strCodLoc;
            strCodTipDocGuia = strCodTipDoc;
            strCodDocGuia = strCodDoc;
            blnEstCarGuia = true;

            objUti = new ZafUtil();
            objTooBar = new mitoolbar(this);

            // this.getContentPane().add(objTooBar,"South");
            
            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleModificar(false);

            if (objParSis.getCodigoMenu() == 2216)
            {
                objTooBar.setVisibleAnular(false);
                objTooBar.setVisibleConsultar(false);
                objTooBar.setVisibleImprimir(false);
                objTooBar.setVisibleVistaPreliminar(false);
            }

            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText(strFecSis);
            panGenCab.add(txtFecDoc);
            txtFecDoc.setBounds(569, 1, 90, 20);

            txtFecIniTrans = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecIniTrans.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecIniTrans.setText(strFecSis);
            panGenCab.add(txtFecIniTrans);
            txtFecIniTrans.setBounds(569, 20, 90, 20);

            txtFecTerTrans = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecTerTrans.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecTerTrans.setText(strFecSis);
            panGenCab.add(txtFecTerTrans);
            txtFecTerTrans.setBounds(569, 40, 90, 20);

            abrirCon();
            cargarIpPuertoGuiaEmp();
            CerrarCon();

            if (objParSis.getCodigoMenu() == 1793) 
            {
                butGuiRem.setVisible(false);
                txtNumGuiRem.setVisible(false);
            }
            if (objParSis.getCodigoMenu() == 2216) 
            {
                lblTipOrdCon.setVisible(false);
                optOrdDes.setVisible(false);
                optGuiRem.setVisible(false);
                chkTieGuiRem.setVisible(false);
                lblTieGuiRem.setVisible(false);
                butTieGuiRem.setVisible(false);
            }

        } 
        catch (CloneNotSupportedException e) {  objUti.mostrarMsgErr_F1(this, e);   } 
        catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e);  e.printStackTrace(); }
    }
    
     /**
     * Creates new form ZafCom23 por Codigo de Menu
     */
    public ZafCom23(Librerias.ZafParSis.ZafParSis obj, int intCodMnu, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) 
    {
        try
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            this.setTitle(objParSis.getNombreMenu() + strVersion);
            lblTit.setText(objParSis.getNombreMenu());

            objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objParSis);
            
            objParSis.setCodigoMenu(intCodMnu);
            System.out.println("intCodMnu: "+intCodMnu);
            
            strCodEmpGuia = strCodEmp;
            strCodLocGuia = strCodLoc;
            strCodTipDocGuia = strCodTipDoc;
            strCodDocGuia = strCodDoc;
            blnEstCarGuia = true;

            objUti = new ZafUtil();
            objTooBar = new mitoolbar(this);

            // this.getContentPane().add(objTooBar,"South");

            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleModificar(false);

            if (objParSis.getCodigoMenu() == 2216) 
            {
                objTooBar.setVisibleAnular(false);
                objTooBar.setVisibleConsultar(false);
                objTooBar.setVisibleImprimir(false);
                objTooBar.setVisibleVistaPreliminar(false);
            }

            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText(strFecSis);
            panGenCab.add(txtFecDoc);
            txtFecDoc.setBounds(569, 1, 90, 20);

            txtFecIniTrans = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecIniTrans.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecIniTrans.setText(strFecSis);
            panGenCab.add(txtFecIniTrans);
            txtFecIniTrans.setBounds(569, 20, 90, 20);

            txtFecTerTrans = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecTerTrans.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecTerTrans.setText(strFecSis);
            panGenCab.add(txtFecTerTrans);
            txtFecTerTrans.setBounds(569, 40, 90, 20);

            abrirCon();
            cargarIpPuertoGuiaEmp();
            CerrarCon();

            if (objParSis.getCodigoMenu() == 1793) 
            {
                butGuiRem.setVisible(false);
                txtNumGuiRem.setVisible(false);
            }
            if (objParSis.getCodigoMenu() == 2216) 
            {
                lblTipOrdCon.setVisible(false);
                optOrdDes.setVisible(false);
                optGuiRem.setVisible(false);
                chkTieGuiRem.setVisible(false);
                lblTieGuiRem.setVisible(false);
                butTieGuiRem.setVisible(false);
            }
        } 
        catch (CloneNotSupportedException e) {    objUti.mostrarMsgErr_F1(this, e); }
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* Conexiones */">
    private void abrirCon() 
    {
        try 
        {
            CONN_GLO = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
        } 
        catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    private void CerrarCon() 
    {
        try 
        {
            CONN_GLO.close();
            CONN_GLO = null;
        } 
        catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* Configuración Tabla */">
    private boolean Configurartabla() 
    {
        boolean blnRes = false;
        try
        {
            //Configurar JTable: Establecer el modelo.
            Vector vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN, "");
            vecCab.add(INT_TBL_CHK_DOC, "..");
            vecCab.add(INT_TBL_COD_ITM, "");
            vecCab.add(INT_TBL_COD_ALT_ITM, "Cód.Alt.Itm.");
            vecCab.add(INT_TBL_COD_ALT_2, "Cód.Alt.2");
            vecCab.add(INT_TBL_NOM_ITM, "Item."); 
            vecCab.add(INT_TBL_DES_COR_UNI_MED, "Uni.Med.");
            vecCab.add(INT_TBL_CAN_ITM, "Cantidad.");
            vecCab.add(INT_TBL_NUM_DOC, "Num.Doc.");
            vecCab.add(INT_TBL_COD_EMP, "");
            vecCab.add(INT_TBL_COD_LOC, "");
            vecCab.add(INT_TBL_COD_TIP_DOC, "");
            vecCab.add(INT_TBL_COD_DOC, "");
            vecCab.add(INT_TBL_COD_REG, "");
            vecCab.add(INT_TBL_MAR_ELI, "");
            vecCab.add(INT_TBL_PES_KGR, "");
            vecCab.add(INT_TBL_CAN_TOT_CON, "Can.Tot.Con.");
            vecCab.add(INT_TBL_CAN_PEN_ITM, "Can.Pen.");
            vecCab.add(INT_TBL_CAN_GUI_REM, "Can.Gui.Rem.");
            vecCab.add(INT_TBL_MER_EGR_FIS, "Mer.Egr.Fis.");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);

            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat, INT_TBL_LIN);
            objTblMod.setColumnDataType(INT_TBL_CAN_ITM, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_CHK_DOC);
            vecAux.add("" + INT_TBL_CAN_GUI_REM);
            vecAux.add("" + INT_TBL_NOM_ITM);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            //Configurar ZafTblMod: Establecer las columnas ELIMINADAS
            ArrayList arlAux = new ArrayList();
            arlAux = new java.util.ArrayList();
            arlAux.add("" + INT_TBL_COD_EMP);
            arlAux.add("" + INT_TBL_COD_LOC);
            arlAux.add("" + INT_TBL_COD_TIP_DOC);
            arlAux.add("" + INT_TBL_COD_DOC);
            objTblMod.setColsSaveBeforeRemoveRow(arlAux);
            arlAux = null;

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux = tblDat.getColumnModel();

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CHK_DOC).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_COD_ALT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_NOM_ITM).setPreferredWidth(260);
            tcmAux.getColumn(INT_TBL_DES_COR_UNI_MED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CAN_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CAN_TOT_CON).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CAN_PEN_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CAN_GUI_REM).setPreferredWidth(60);
            
            tcmAux.getColumn(INT_TBL_COD_ALT_2).setPreferredWidth(60);
            

            // Columnas que van a ser ocultas.
            ArrayList arlColHid = new ArrayList();
            arlColHid.add("" + INT_TBL_COD_ITM);
            arlColHid.add("" + INT_TBL_MAR_ELI);
            arlColHid.add("" + INT_TBL_NUM_DOC);
            arlColHid.add("" + INT_TBL_COD_EMP);
            arlColHid.add("" + INT_TBL_COD_LOC);
            arlColHid.add("" + INT_TBL_COD_TIP_DOC);
            arlColHid.add("" + INT_TBL_COD_DOC);
            arlColHid.add("" + INT_TBL_COD_REG);
            arlColHid.add("" + INT_TBL_PES_KGR);
            arlColHid.add("" + INT_TBL_MER_EGR_FIS);
            if ((objParSis.getCodigoMenu() == 1793) || (objParSis.getCodigoMenu() == 3497)) 
            {
                arlColHid.add("" + INT_TBL_CHK_DOC);
            }
            if ((objParSis.getCodigoMenu() == 1793) || (objParSis.getCodigoMenu() == 3497)) 
            {
                arlColHid.add("" + INT_TBL_CAN_GUI_REM);
            }

            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid = null;

            ZafTblCelRenLbl objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_CAN_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CAN_TOT_CON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CAN_PEN_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CAN_GUI_REM).setCellRenderer(objTblCelRenLbl);
            
            ZafTblCelRenLbl objTblCelRenLbl2 = new ZafTblCelRenLbl();
            objTblCelRenLbl2.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            
            tcmAux.getColumn(INT_TBL_COD_ALT_2).setCellRenderer(objTblCelRenLbl2);

            tblDat.getTableHeader().setReorderingAllowed(false);
            //objMouMotAda=new ZafMouMotAda();
            //tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            ZafTblCelRenChk objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHK_DOC).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            ZafTblCelEdiChk objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHK_DOC).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();

                    if (objInvItm.getStringDatoValidado(tblDat.getValueAt(intNumFil, INT_TBL_CHK_DOC)).toString().equals("true"))
                    {
                        tblDat.setValueAt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intNumFil, INT_TBL_CAN_PEN_ITM)), intNumFil, INT_TBL_CAN_GUI_REM);
                        calcularPeso();
                        //double dlbCan=Double.parseDouble( objInvItm.getIntDatoValidado( tblDat.getValueAt(intNumFil, INT_TBL_CAN_PEN_ITM) ) );
                        //double dlbPesKgr=Double.parseDouble( objInvItm.getIntDatoValidado( tblDat.getValueAt(intNumFil, INT_TBL_PES_KGR) ));
                        //double dlbPesTotKgr=Double.parseDouble( txtPeso.getText() );
                        //double dlbKgr=  dlbCan*dlbPesKgr;
                        //txtPeso.setText(""+ objUti.redondear( (dlbPesTotKgr+dlbKgr), 2) );
                    } 
                    else 
                    {
                       tblDat.setValueAt("0", intNumFil, INT_TBL_CAN_GUI_REM);
                       calcularPeso();
                       //double dlbCan=Double.parseDouble( objInvItm.getIntDatoValidado( tblDat.getValueAt(intNumFil, INT_TBL_CAN_GUI_REM) ) );
                       //double dlbPesKgr=Double.parseDouble( objInvItm.getIntDatoValidado( tblDat.getValueAt(intNumFil, INT_TBL_PES_KGR) ));
                       //double dlbPesTotKgr=Double.parseDouble( txtPeso.getText() );
                       //double dlbKgr=  dlbCan*dlbPesKgr;
                       //txtPeso.setText(""+ objUti.redondear( (dlbPesTotKgr-dlbKgr), 2) );
                    }
                }
            });

            objTblCelEdiTxt = new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CAN_GUI_REM).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt) {
                    calcularPeso();
                }

                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();

                    if (tblDat.getValueAt(intNumFil, INT_TBL_CAN_GUI_REM) != null) {

                        double dlbCanGus = Double.parseDouble(objInvItm.getIntDatoValidado(tblDat.getValueAt(intNumFil, INT_TBL_CAN_GUI_REM)));
                        double dlbCanPen = Double.parseDouble(objInvItm.getIntDatoValidado(tblDat.getValueAt(intNumFil, INT_TBL_CAN_PEN_ITM)));
                        //double dlbCanGuP=Double.parseDouble( objInvItm.getIntDatoValidado( tblDat.getValueAt(intNumFil, INT_TBL_CAN_ITM) ) );
                        calcularPeso();
                        tblDat.setValueAt(true, intNumFil, INT_TBL_CHK_DOC);

                        if (dlbCanGus > dlbCanPen) {
                            MensajeInf("No puede exceder a la cantidad Pendiente.\nVerifique los datos he intente nuevamente.");
                            tblDat.setValueAt("0", intNumFil, INT_TBL_CAN_GUI_REM);
                            tblDat.setValueAt(false, intNumFil, INT_TBL_CHK_DOC);
                            calcularPeso();
                        }
                        if (dlbCanGus == 0) {
                            tblDat.setValueAt(false, intNumFil, INT_TBL_CHK_DOC);
                        }

                    }
                }
            });

            tcmAux = null;

            setEditable(true);
            ZafTblPopMn = new ZafTblPopMnu(tblDat);
            ZafTblPopMn.setInsertarFilaVisible(false);
            ZafTblPopMn.setInsertarFilasVisible(false);

      //<editor-fold defaultstate="collapsed" desc=" Comentado "> 
            
            //***********************************************************
//            ZafTblPopMn.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
//                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
//                    String strCodLoc, strCodTipDoc, strCodDoc, strNumDoc;
//                    int i, intFilSel[];
//                    intFilSel=tblDat.getSelectedRows();
//
//
//                    if (ZafTblPopMn.isClickInsertarFila()) {
//                       // System.out.println("beforeClick: isClickInsertarFila");
//                        //Cancelar la edición cuado sea necesario.
//                        if (tblDat.getSelectedRow()==1)
//                            ZafTblPopMn.cancelarClick();
//                    }
//                    else if (ZafTblPopMn.isClickEliminarFila()) {
//                        for (i=0; i<intFilSel.length; i++){
//                            strCodLoc = ((tblDat.getValueAt((intFilSel[i]), INT_TBL_COD_LOC)==null)?"":tblDat.getValueAt((intFilSel[i]), INT_TBL_COD_LOC).toString());
//                            strCodTipDoc = ((tblDat.getValueAt((intFilSel[i]), INT_TBL_COD_TIP_DOC)==null)?"":tblDat.getValueAt((intFilSel[i]), INT_TBL_COD_TIP_DOC).toString());
//                            strCodDoc = ((tblDat.getValueAt((intFilSel[i]), INT_TBL_COD_DOC)==null)?"":tblDat.getValueAt((intFilSel[i]), INT_TBL_COD_DOC).toString());
//                            strNumDoc = ((tblDat.getValueAt((intFilSel[i]), INT_TBL_NUM_DOC)==null)?"":tblDat.getValueAt((intFilSel[i]), INT_TBL_NUM_DOC).toString());
//
//                            marcarDatTblEli(strCodLoc, strCodTipDoc, strCodDoc, strNumDoc);
//                            ZafTblPopMn.cancelarClick();
//
//                        }
//                        cargarDatTblEli();
//                    }
//
//                    intFilSel=null;
//
//                }
//                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
//                    if (ZafTblPopMn.isClickInsertarFila()) {
//                        //Escriba aquí el código que se debe realizar luego de insertar la fila.
//                       // System.out.println("afterClick: isClickInsertarFila");
//                    }
//                    else if (ZafTblPopMn.isClickEliminarFila()) {
//                       // System.out.println("afterClick: isClickEliminarFila");
//                        //  javax.swing.JOptionPane.showMessageDialog(null, "Las filas se eliminaron con éxito.");
//                    }
//                }
//            });

            //***********************************************************

    //</editor-fold>

            blnRes = true;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="/* Configuraciones de Ventanas */">
    
    public void Configura_ventana_consulta() 
    {
        configurarVenConTipDoc();
        configurarVenConPtoPar();
        configurarVenConCliDes();
        configurarVenConPtoLle();
        configurarVenConMotTra();
        configurarVenConOrdDesGuiRem();

        Configurartabla();

        if (blnEstCarGuia) 
        {
            cargarDatos(strCodEmpGuia, strCodLocGuia, strCodTipDocGuia, strCodDocGuia);
        }
    }

    /* Filtro Consulta Tipos Documentos por Usuario */
    private String sqlConTipDoc() 
    {
        String strSqlTipDoc = "";

        if (objParSis.getCodigoUsuario() == 1) 
        {
            strSqlTipDoc+=" select a.co_tipdoc from tbr_tipdocprg as b ";
            strSqlTipDoc+=" inner join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc) ";
            strSqlTipDoc+=" where b.co_emp=" + objParSis.getCodigoEmpresa() ;
            strSqlTipDoc+=" and b.co_loc =" + objParSis.getCodigoLocal() ;
            strSqlTipDoc+=" and b.co_mnu =" + objParSis.getCodigoMenu() ;
        } 
        else 
        {
            strSqlTipDoc+=" select a.co_tipdoc from tbr_tipDocUsr as a1 ";
            strSqlTipDoc+=" inner join tbm_cabTipDoc as a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc) ";
            strSqlTipDoc+=" where a1.co_emp=" + objParSis.getCodigoEmpresa() ;
            strSqlTipDoc+=" and a1.co_loc=" + objParSis.getCodigoLocal() ;
            strSqlTipDoc+=" and a1.co_mnu=" + objParSis.getCodigoMenu() ;
            strSqlTipDoc+=" and a1.co_usr=" + objParSis.getCodigoUsuario();
        }

        return strSqlTipDoc;
    }
    
    
    /* Filtro Consulta Bodegas por Usuario */
    private String sqlConBod() 
    {
        String strSqlBod = "";

        strSqlBod+=" SELECT co_bod FROM ( ";
        strSqlBod+="     select a2.co_bodgrp as co_bod,  a1.tx_nom from tbr_bodlocprgusr as a ";
        strSqlBod+="     inner join tbr_bodEmpBodGrp as a2 on (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) ";
        strSqlBod+="     inner join tbm_bod as a1 on (a1.co_emp=a2.co_empgrp and a1.co_bod=a2.co_bodgrp) ";
        strSqlBod+="     where a.co_emp=" + objParSis.getCodigoEmpresa() ;
        strSqlBod+="     and a.co_loc=" + objParSis.getCodigoLocal() ;
        strSqlBod+="     and a.co_mnu=" + objParSis.getCodigoMenu() ;
        strSqlBod+="     and a.co_usr=" + objParSis.getCodigoUsuario() ;
        strSqlBod+="     and a.st_reg in ('A','P') ";
        strSqlBod+=" ) as a";
        
        //<editor-fold defaultstate="collapsed" desc="Consultar Bodega Predeterminada de Acuerdo al Local y no al Usuario">
            /*strSqlBod+=" SELECT co_bod FROM ( ";  
              strSqlBod+=" select a2.co_bodgrp as co_bod,  a1.tx_nom from tbr_bodloc as a "  ;
              strSqlBod+=" inner join tbr_bodEmpBodGrp as a2 on (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) " ;
              strSqlBod+=" inner join tbm_bod as a1 on (a1.co_emp=a2.co_empgrp and a1.co_bod=a2.co_bodgrp) " ;
              strSqlBod+=" where a.co_emp=" + objParSis.getCodigoEmpresa();
              strSqlBod+=" and a.co_loc=" + objParSis.getCodigoLocal() ;
              strSqlBod+=" and a.st_reg in ('P') " ; 
              strSqlBod+="  ) as a ; ";  
             */
        //</editor-fold>
        
        return strSqlBod;
    }
    
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes = true;
        String strSQL = "";
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("85");
            arlAncCol.add("105");
            arlAncCol.add("350");

            //Armar la sentencia SQL. 
            if (objParSis.getCodigoUsuario() == 1) 
            {
                strSQL = " Select a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocprg as b "
                        + " left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)"
                        + " where b.co_emp=" + objParSis.getCodigoEmpresa() + " and "
                        + " b.co_loc = " + objParSis.getCodigoLocal() + " and "
                        + " b.co_mnu = " + objParSis.getCodigoMenu();
            }
            else 
            {
                strSQL = " SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar "
                        + " FROM tbr_tipDocUsr AS a1 inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"
                        + " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa()+""
                        + " AND a1.co_loc=" + objParSis.getCodigoLocal() +""
                        + " AND a1.co_mnu=" + objParSis.getCodigoMenu() + ""
                        + " AND a1.co_usr=" + objParSis.getCodigoUsuario();
            }

            vcoTipDoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    private boolean configurarVenConOrdDesGuiRem() 
    {
        boolean blnRes = true;
        String strFilTipDoc, strFilBod, strSQL;
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_emp");
            arlCam.add("a.co_loc");
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.co_doc");
            arlCam.add("a.ne_numdoc");
            arlCam.add("a.tx_nomclides");
            arlCam.add("a.fe_doc");
            arlCam.add("a.ne_numorddes");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Cód.Emp.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Cód.Tip.Doc.");
            arlAli.add("Cód.Doc.");
            arlAli.add("Num.Gui.Rem.");
            arlAli.add("Cliente");
            arlAli.add("Fecha");
            arlAli.add("Num.Ord.Des.");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("100");
            arlAncCol.add("350");
            arlAncCol.add("80");
            arlAncCol.add("80");
            
            // Filtro Consulta Tipos Documentos por Usuario.
            strFilTipDoc=sqlConTipDoc(); 
            //System.out.println("ConsultaTipDoc: " + strFilTipDoc);

            // Filtro Consulta Bodegas por Usuario.
            strFilBod=sqlConBod();
            //System.out.println("ConsultaBodega: " + strFilBod);
            
            //Armar la sentencia SQL. 
            if (objParSis.getCodigoMenu() == 1793) 
            {
                strSQL =" SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numdoc, a.ne_numorddes, a.tx_nomclides, a.fe_doc ";
                strSQL+=" FROM tbm_cabguirem as a  ";
                strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) ";
                strSQL+=" WHERE  a.st_reg not in ('E')  and a.co_tipdoc in ( " + strFilTipDoc + " )  ";
                strSQL+=" AND ( a6.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp in (" + strFilBod + ") ) ";
                strSQL+=" AND a.ne_numdoc >0  ";
                strSQL+=" ORDER BY a.fe_Doc desc, a.ne_numdoc ";
            }
            else if ( objParSis.getCodigoMenu() == 3497) 
            {
                strSQL =" SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numdoc, a.ne_numorddes, a.tx_nomclides, a.fe_doc ";
                strSQL+=" FROM tbm_cabguirem as a  ";
                strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) ";
                strSQL+=" WHERE  a.st_reg not in ('E')  and a.co_tipdoc in ( " + strFilTipDoc + " ) ";
                strSQL+=" AND ( a6.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp in (" + strFilBod + ") ) ";
                strSQL+=" AND a.ne_numdoc=0  and (a.fe_doc >= '#2011/09/26#')";
                strSQL+=" ORDER BY a.fe_Doc desc, a.ne_numOrdDes ";
            }
            else
            {
                strSQL =" SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numdoc, a.ne_numorddes, a.tx_nomclides, a.fe_doc ";
                strSQL+=" FROM tbm_cabguirem as a  ";
                strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) ";
                strSQL+=" WHERE  a.st_reg not in ( 'E')  and a.co_tipdoc in ( " + strFilTipDoc + " )  ";
                strSQL+=" AND ( a6.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp in (" + strFilBod + ") ) ";
                strSQL+=" AND  a.st_coninv='P' and a.st_tipguirem='P' and a.st_creTodGuiSec='N' and a.ne_numdoc > 0 ";
                strSQL+=" ORDER BY a.ne_numdoc ";
            }
            System.out.println("ConsultaOrdenDespacho/GuíaRemisión: " + strSQL);

            int intColOcu[] = new int[4];
            intColOcu[0] = 1; //co_emp
            intColOcu[1] = 2; //co_loc
            intColOcu[2] = 3; //co_tipdoc
            intColOcu[3] = 4; //co_doc
            
            vcoGuiRemOrdDes = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

            vcoGuiRemOrdDes.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConPtoPar() 
    {
        boolean blnRes = true;
        String strSQL = "";
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_bod");
            arlCam.add("a.tx_dir");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción.");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("120");
            arlAncCol.add("420");

            //Armar la sentencia SQL. 
            strSQL = "select a.co_bod, a.tx_dir from tbm_bod as a where a.co_emp=" + objParSis.getCodigoEmpresa() + " and a.st_reg not in ('I')";

            vcoPtoPar = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

            vcoPtoPar.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConCliDes() 
    {
        boolean blnRes = true;
        String strSQL = "";
        try
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_nom");
            arlCam.add("a.tx_ide");
            arlCam.add("a.tx_deslar");
            arlCam.add("a.tx_tel");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Cli.");
            arlAli.add("Ruc.");
            arlAli.add("Ciudad.");
            arlAli.add("Teléfono.");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("250");
            arlAncCol.add("70");
            arlAncCol.add("80");
            arlAncCol.add("80");

            //Armar la sentencia SQL.
            
            strSQL = " SELECT * FROM (  "
                   + "                select a.co_cli, a.tx_nom, a.tx_ide , b.tx_deslar, a.tx_tel  "
                   + "                from tbm_cli as aleft join tbm_ciu as b on (b.co_ciu=a.co_ciu) "
                   + "                where a.co_emp =" + objParSis.getCodigoEmpresa() + ""
                   + " ) as a ";

            vcoCliDes = new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConPtoLle() 
    {
        boolean blnRes = true;
        String strSQL = "";
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_dir");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Dirección");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("120");
            arlAncCol.add("420");

            //Armar la sentencia SQL.
            strSQL = "SELECT a.co_cli, a.tx_dir  FROM  tbm_dircli as a WHERE  a.co_emp=" + objParSis.getCodigoEmpresa() + " and a.co_cli=0 ";

            vcoPtoLle = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConMotTra() 
    {
        boolean blnRes = true;
        String strSQL = "";
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_reg");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("120");
            arlAncCol.add("120");
            arlAncCol.add("300");

            //Armar la sentencia SQL.
            strSQL = "select a.co_reg, a.tx_descor, a.tx_deslar from tbm_var as a where a.co_grp = 7  ";

            vcoMotTra = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    //</editor-fold>
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTit = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        panTabGenCab = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblPtoPar = new javax.swing.JLabel();
        txtCodPtoPar = new javax.swing.JTextField();
        txtDesPtoPar = new javax.swing.JTextField();
        lblDes = new javax.swing.JLabel();
        txtCodDes = new javax.swing.JTextField();
        txtNomDes = new javax.swing.JTextField();
        lblPtoLle = new javax.swing.JLabel();
        txtDesCorPtoLle = new javax.swing.JTextField();
        txtDesLarPtoLle = new javax.swing.JTextField();
        lblMotTra = new javax.swing.JLabel();
        txtDesCorMotTra = new javax.swing.JTextField();
        txtDesLarMotTra = new javax.swing.JTextField();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblFecIniTra = new javax.swing.JLabel();
        lblFecTerTra = new javax.swing.JLabel();
        lblNumGuiRem = new javax.swing.JLabel();
        txtNumGuiRem = new javax.swing.JTextField();
        butGuiRem = new javax.swing.JButton();
        lblNumOrdDes = new javax.swing.JLabel();
        txtNumOrdDes = new javax.swing.JTextField();
        lblDocOri = new javax.swing.JLabel();
        txtDocOri = new javax.swing.JTextField();
        lblPes = new javax.swing.JLabel();
        txtPes = new javax.swing.JTextField();
        lblTipGui = new javax.swing.JLabel();
        optOrdDes = new javax.swing.JRadioButton();
        optGuiRem = new javax.swing.JRadioButton();
        lblTipOrdCon = new javax.swing.JLabel();
        optConOrdDes = new javax.swing.JRadioButton();
        optConGuiRem = new javax.swing.JRadioButton();
        chkTieGuiRem = new javax.swing.JCheckBox();
        lblTieGuiRem = new javax.swing.JLabel();
        butTieGuiRem = new javax.swing.JButton();
        chkTieDev = new javax.swing.JCheckBox();
        lblTieDev = new javax.swing.JLabel();
        butTieDev = new javax.swing.JButton();
        panTabGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panTabGenObs = new javax.swing.JPanel();
        panObs1 = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txtObs1 = new javax.swing.JTextArea();
        panObs2 = new javax.swing.JPanel();
        lblObs2 = new javax.swing.JLabel();
        spnObs2 = new javax.swing.JScrollPane();
        txtObs2 = new javax.swing.JTextArea();
        PanTabForRet = new javax.swing.JPanel();
        panForRet = new javax.swing.JPanel();
        lblForRet = new javax.swing.JLabel();
        txtCodForRet = new javax.swing.JTextField();
        txtDesForRet = new javax.swing.JTextField();
        butForRet = new javax.swing.JButton();
        lblVehRet = new javax.swing.JLabel();
        txtVehRet = new javax.swing.JTextField();
        lblChoRet = new javax.swing.JLabel();
        txtChoRet = new javax.swing.JTextField();

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("jLabel1");

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

        panFrm.setLayout(new java.awt.BorderLayout());

        panTabGen.setLayout(new java.awt.BorderLayout());

        panTabGenCab.setPreferredSize(new java.awt.Dimension(100, 160));
        panTabGenCab.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(100, 20));
        panGenCab.setLayout(null);

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDoc.setText("Tipo de Documento:");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(5, 0, 116, 20);

        txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(120, 0, 50, 20);

        txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(170, 0, 210, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(380, 0, 20, 20);

        lblPtoPar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblPtoPar.setText("Punto de Partida:");
        panGenCab.add(lblPtoPar);
        lblPtoPar.setBounds(5, 20, 116, 14);

        txtCodPtoPar.setBackground(objParSis.getColorCamposObligatorios());
        panGenCab.add(txtCodPtoPar);
        txtCodPtoPar.setBounds(120, 20, 50, 20);

        txtDesPtoPar.setBackground(objParSis.getColorCamposObligatorios());
        txtDesPtoPar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesPtoParFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesPtoParFocusLost(evt);
            }
        });
        txtDesPtoPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesPtoParActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesPtoPar);
        txtDesPtoPar.setBounds(170, 20, 210, 20);

        lblDes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDes.setText("Destinatario:");
        panGenCab.add(lblDes);
        lblDes.setBounds(5, 40, 116, 14);

        txtCodDes.setBackground(objParSis.getColorCamposObligatorios());
        txtCodDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDesFocusLost(evt);
            }
        });
        txtCodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDesActionPerformed(evt);
            }
        });
        panGenCab.add(txtCodDes);
        txtCodDes.setBounds(120, 40, 50, 20);

        txtNomDes.setBackground(objParSis.getColorCamposObligatorios());
        txtNomDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDesFocusLost(evt);
            }
        });
        txtNomDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDesActionPerformed(evt);
            }
        });
        panGenCab.add(txtNomDes);
        txtNomDes.setBounds(170, 40, 210, 20);

        lblPtoLle.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblPtoLle.setText("Punto de Llegada:");
        panGenCab.add(lblPtoLle);
        lblPtoLle.setBounds(5, 60, 116, 14);

        txtDesCorPtoLle.setBackground(objParSis.getColorCamposObligatorios());
        txtDesCorPtoLle.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorPtoLleFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorPtoLleFocusLost(evt);
            }
        });
        txtDesCorPtoLle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorPtoLleActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesCorPtoLle);
        txtDesCorPtoLle.setBounds(120, 60, 50, 20);

        txtDesLarPtoLle.setBackground(objParSis.getColorCamposObligatorios());
        txtDesLarPtoLle.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarPtoLleFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarPtoLleFocusLost(evt);
            }
        });
        txtDesLarPtoLle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarPtoLleActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesLarPtoLle);
        txtDesLarPtoLle.setBounds(170, 60, 210, 20);

        lblMotTra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMotTra.setText("Motivo de Traslado:");
        panGenCab.add(lblMotTra);
        lblMotTra.setBounds(5, 80, 116, 14);

        txtDesCorMotTra.setBackground(objParSis.getColorCamposObligatorios());
        txtDesCorMotTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorMotTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorMotTraFocusLost(evt);
            }
        });
        txtDesCorMotTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorMotTraActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesCorMotTra);
        txtDesCorMotTra.setBounds(120, 80, 50, 20);

        txtDesLarMotTra.setBackground(objParSis.getColorCamposObligatorios());
        txtDesLarMotTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarMotTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarMotTraFocusLost(evt);
            }
        });
        txtDesLarMotTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarMotTraActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesLarMotTra);
        txtDesLarMotTra.setBounds(170, 80, 210, 20);

        lblCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodDoc.setText("Código de Documento:");
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(5, 100, 116, 14);

        txtCodDoc.setBackground(objParSis.getColorCamposSistema());
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(120, 100, 70, 20);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecDoc.setText("Fecha del Documento:");
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(405, 0, 160, 20);

        lblFecIniTra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecIniTra.setText("Fecha de Inicio del Traslado:");
        panGenCab.add(lblFecIniTra);
        lblFecIniTra.setBounds(405, 20, 160, 20);

        lblFecTerTra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecTerTra.setText("Fecha de Termino de Traslado:");
        panGenCab.add(lblFecTerTra);
        lblFecTerTra.setBounds(405, 40, 160, 20);

        lblNumGuiRem.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumGuiRem.setText("Número de Guía Remisión:");
        panGenCab.add(lblNumGuiRem);
        lblNumGuiRem.setBounds(405, 60, 160, 20);

        txtNumGuiRem.setBackground(objParSis.getColorCamposObligatorios());
        txtNumGuiRem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumGuiRemKeyPressed(evt);
            }
        });
        panGenCab.add(txtNumGuiRem);
        txtNumGuiRem.setBounds(569, 60, 90, 20);

        butGuiRem.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butGuiRem.setText("...");
        butGuiRem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuiRemActionPerformed(evt);
            }
        });
        panGenCab.add(butGuiRem);
        butGuiRem.setBounds(659, 60, 22, 20);

        lblNumOrdDes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumOrdDes.setText("Número Orden Despacho:");
        panGenCab.add(lblNumOrdDes);
        lblNumOrdDes.setBounds(405, 80, 160, 20);

        txtNumOrdDes.setBackground(objParSis.getColorCamposObligatorios());
        txtNumOrdDes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumOrdDesKeyPressed(evt);
            }
        });
        panGenCab.add(txtNumOrdDes);
        txtNumOrdDes.setBounds(569, 80, 90, 20);

        lblDocOri.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDocOri.setText("Documento origen:");
        panGenCab.add(lblDocOri);
        lblDocOri.setBounds(405, 100, 160, 20);

        txtDocOri.setBackground(objParSis.getColorCamposObligatorios());
        panGenCab.add(txtDocOri);
        txtDocOri.setBounds(569, 100, 90, 20);

        lblPes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblPes.setText("Peso (Kg):");
        panGenCab.add(lblPes);
        lblPes.setBounds(405, 120, 160, 20);

        txtPes.setBackground(objParSis.getColorCamposSistema());
        panGenCab.add(txtPes);
        txtPes.setBounds(569, 120, 90, 20);

        lblTipGui.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipGui.setText("Tipo Guía:");
        panGenCab.add(lblTipGui);
        lblTipGui.setBounds(5, 130, 50, 14);

        buttonGroup1.add(optOrdDes);
        optOrdDes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optOrdDes.setText("OD");
        panGenCab.add(optOrdDes);
        optOrdDes.setBounds(54, 120, 110, 18);

        buttonGroup1.add(optGuiRem);
        optGuiRem.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optGuiRem.setText("Guía Remisión");
        panGenCab.add(optGuiRem);
        optGuiRem.setBounds(54, 140, 100, 18);

        lblTipOrdCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipOrdCon.setText("Orden de Consulta:");
        panGenCab.add(lblTipOrdCon);
        lblTipOrdCon.setBounds(200, 105, 100, 14);

        buttonGroup2.add(optConOrdDes);
        optConOrdDes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optConOrdDes.setSelected(true);
        optConOrdDes.setText("Por OD");
        panGenCab.add(optConOrdDes);
        optConOrdDes.setBounds(200, 120, 90, 18);

        buttonGroup2.add(optConGuiRem);
        optConGuiRem.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optConGuiRem.setText("Por GR");
        panGenCab.add(optConGuiRem);
        optConGuiRem.setBounds(290, 120, 80, 18);
        panGenCab.add(chkTieGuiRem);
        chkTieGuiRem.setBounds(400, 140, 20, 21);

        lblTieGuiRem.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTieGuiRem.setText("¿Tiene guías remisión?");
        panGenCab.add(lblTieGuiRem);
        lblTieGuiRem.setBounds(420, 140, 110, 20);

        butTieGuiRem.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butTieGuiRem.setText("...");
        butTieGuiRem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTieGuiRemActionPerformed(evt);
            }
        });
        panGenCab.add(butTieGuiRem);
        butTieGuiRem.setBounds(530, 140, 22, 20);
        panGenCab.add(chkTieDev);
        chkTieDev.setBounds(200, 140, 20, 21);

        lblTieDev.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTieDev.setText("¿Tiene Solicitud Devolucion?");
        panGenCab.add(lblTieDev);
        lblTieDev.setBounds(220, 140, 150, 20);

        butTieDev.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butTieDev.setText("...");
        butTieDev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTieDevActionPerformed(evt);
            }
        });
        panGenCab.add(butTieDev);
        butTieDev.setBounds(360, 140, 22, 20);

        panTabGenCab.add(panGenCab, java.awt.BorderLayout.CENTER);

        panTabGen.add(panTabGenCab, java.awt.BorderLayout.NORTH);

        panTabGenDet.setPreferredSize(new java.awt.Dimension(452, 380));
        panTabGenDet.setLayout(new java.awt.BorderLayout());

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

        panTabGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panTabGen.add(panTabGenDet, java.awt.BorderLayout.CENTER);

        panTabGenObs.setPreferredSize(new java.awt.Dimension(100, 50));
        panTabGenObs.setLayout(new java.awt.GridLayout(0, 1));

        panObs1.setLayout(new javax.swing.BoxLayout(panObs1, javax.swing.BoxLayout.LINE_AXIS));

        lblObs1.setText("Observación 1:");
        panObs1.add(lblObs1);

        txtObs1.setColumns(20);
		txtObs1.setLineWrap(true);
        txtObs1.setRows(5);
        spnObs1.setViewportView(txtObs1);

        panObs1.add(spnObs1);

        panTabGenObs.add(panObs1);

        panObs2.setLayout(new javax.swing.BoxLayout(panObs2, javax.swing.BoxLayout.LINE_AXIS));

        lblObs2.setText("Observación 2:");
        panObs2.add(lblObs2);

        txtObs2.setColumns(20);
		txtObs2.setLineWrap(true);
        txtObs2.setRows(5);
        spnObs2.setViewportView(txtObs2);

        panObs2.add(spnObs2);

        panTabGenObs.add(panObs2);

        panTabGen.add(panTabGenObs, java.awt.BorderLayout.SOUTH);

        tabGen.addTab("General", panTabGen);

        PanTabForRet.setLayout(new java.awt.BorderLayout());

        panForRet.setLayout(null);

        lblForRet.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblForRet.setText("Forma de Retiro:");
        panForRet.add(lblForRet);
        lblForRet.setBounds(20, 20, 100, 15);

        txtCodForRet.setEditable(false);
        panForRet.add(txtCodForRet);
        txtCodForRet.setBounds(130, 20, 40, 20);
        panForRet.add(txtDesForRet);
        txtDesForRet.setBounds(170, 20, 180, 20);

        butForRet.setText("...");
        butForRet.setPreferredSize(new java.awt.Dimension(35, 30));
        butForRet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butForRetActionPerformed(evt);
            }
        });
        panForRet.add(butForRet);
        butForRet.setBounds(350, 20, 22, 20);

        lblVehRet.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblVehRet.setText("Vehiculo de Retiro:");
        panForRet.add(lblVehRet);
        lblVehRet.setBounds(20, 45, 100, 15);
        panForRet.add(txtVehRet);
        txtVehRet.setBounds(130, 45, 220, 20);

        lblChoRet.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblChoRet.setText("Chofer de Retiro:");
        panForRet.add(lblChoRet);
        lblChoRet.setBounds(20, 75, 100, 15);
        panForRet.add(txtChoRet);
        txtChoRet.setBounds(130, 70, 220, 20);

        PanTabForRet.add(panForRet, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Forma de retiro", PanTabForRet);

        panFrm.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents
    
    //<editor-fold defaultstate="collapsed" desc="/* Eventos */">
    
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            } else {
                BuscarTipDoc("a.tx_deslar", txtDesLarTipDoc.getText(), 2);
            }
        } else {
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc = txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            } else {
                BuscarTipDoc("a.tx_descor", txtDesCorTipDoc.getText(), 1);
            }
        } else {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc = txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesPtoParFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesPtoParFocusLost
        if (!txtDesPtoPar.getText().equalsIgnoreCase(strDesPtoPar)) {
            if (txtDesPtoPar.getText().equals("")) {
                txtCodPunPar.setText("");
                txtDesPtoPar.setText("");
            } else {
                BuscarPtoPar("a.tx_dir", txtDesPtoPar.getText(), 1);
            }
        } else {
            txtDesPtoPar.setText(strDesPtoPar);
        }
    }//GEN-LAST:event_txtDesPtoParFocusLost

    private void txtDesPtoParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesPtoParActionPerformed
        txtDesPtoPar.transferFocus();
    }//GEN-LAST:event_txtDesPtoParActionPerformed

    private void txtDesPtoParFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesPtoParFocusGained
        strDesPtoPar = txtDesPtoPar.getText();
        txtDesPtoPar.selectAll();
    }//GEN-LAST:event_txtDesPtoParFocusGained

    private void txtCodDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDesFocusLost
        if (!txtCodDes.getText().equalsIgnoreCase(strCodCliDes)) {
            if (txtCodDes.getText().equals("")) {
                txtCodDes.setText("");
                txtNomDes.setText("");
            } else {
                BuscarCliDes("a.co_cli", txtCodDes.getText(), 1);
            }
        } else {
            txtCodDes.setText(strCodCliDes);
        }
    }//GEN-LAST:event_txtCodDesFocusLost

    private void txtNomDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDesActionPerformed
        txtNomDes.transferFocus();
    }//GEN-LAST:event_txtNomDesActionPerformed

    private void txtNomDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDesFocusLost
        if (!txtNomDes.getText().equalsIgnoreCase(strNomCliDes)) {
            if (txtNomDes.getText().equals("")) {
                txtCodDes.setText("");
                txtNomDes.setText("");
            } else {
                BuscarCliDes("a.tx_nom", txtNomDes.getText(), 2);
            }
        } else {
            txtNomDes.setText(strNomCliDes);
        }
    }//GEN-LAST:event_txtNomDesFocusLost

    private void txtCodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDesActionPerformed
        txtCodDes.transferFocus();
    }//GEN-LAST:event_txtCodDesActionPerformed

    private void txtCodDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDesFocusGained
        strCodCliDes = txtCodDes.getText();
        txtCodDes.selectAll();
    }//GEN-LAST:event_txtCodDesFocusGained

    private void txtNomDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDesFocusGained
        strNomCliDes = txtNomDes.getText();
        txtNomDes.selectAll();
    }//GEN-LAST:event_txtNomDesFocusGained

    private void txtDesLarPtoLleFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPtoLleFocusGained
        strDesPtoLle = txtDesLarPtoLle.getText();
        txtDesLarPtoLle.selectAll();
    }//GEN-LAST:event_txtDesLarPtoLleFocusGained

    private void txtDesCorPtoLleFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorPtoLleFocusGained
        strCodPtoLle = txtDesCorPtoLle.getText();
        txtDesCorPtoLle.selectAll();
    }//GEN-LAST:event_txtDesCorPtoLleFocusGained

    private void txtDesLarPtoLleFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPtoLleFocusLost
        if (!txtDesLarPtoLle.getText().equalsIgnoreCase(strDesPtoLle)) {
            if (txtDesLarPtoLle.getText().equals("")) {
                txtDesCorPtoLle.setText("");
                txtDesLarPtoLle.setText("");
            } else {
                BuscarPtoLle();
            }
        } else {
            txtDesLarPtoLle.setText(strDesPtoLle);
        }
    }//GEN-LAST:event_txtDesLarPtoLleFocusLost

    private void txtDesCorPtoLleFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorPtoLleFocusLost
        if (!txtDesCorPtoLle.getText().equalsIgnoreCase(strCodCliDes)) {
            if (txtDesCorPtoLle.getText().equals("")) {
                txtDesCorPtoLle.setText("");
                txtDesLarPtoLle.setText("");
            } else {
                BuscarPtoLle();
            }
        } else {
            txtDesCorPtoLle.setText(strCodCliDes);
        }
    }//GEN-LAST:event_txtDesCorPtoLleFocusLost

    private void txtDesLarPtoLleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarPtoLleActionPerformed
        txtDesLarPtoLle.transferFocus();
    }//GEN-LAST:event_txtDesLarPtoLleActionPerformed

    private void txtDesCorPtoLleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorPtoLleActionPerformed
        txtDesCorPtoLle.transferFocus();
    }//GEN-LAST:event_txtDesCorPtoLleActionPerformed

    private void txtDesLarMotTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarMotTraFocusLost
        if (!txtDesLarMotTra.getText().equalsIgnoreCase(strDesLarMotTra)) {
            if (txtDesLarMotTra.getText().equals("")) {
                txtDesCorMotTra.setText("");
                txtDesLarMotTra.setText("");
            } else {
                BuscarMotTra("a.tx_Deslar", txtDesLarMotTra.getText(), 2);
            }
        } else {
            txtDesLarMotTra.setText(strDesLarMotTra);
        }
    }//GEN-LAST:event_txtDesLarMotTraFocusLost

    private void txtDesCorMotTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorMotTraFocusLost
        if (!txtDesCorMotTra.getText().equalsIgnoreCase(strDesCorMotTra)) {
            if (txtDesCorMotTra.getText().equals("")) {
                txtDesCorMotTra.setText("");
                txtDesLarMotTra.setText("");
            } else {
                BuscarMotTra("a.tx_descor", txtDesCorMotTra.getText(), 1);
            }
        } else {
            txtDesCorMotTra.setText(strDesCorMotTra);
        }
    }//GEN-LAST:event_txtDesCorMotTraFocusLost

    private void txtDesLarMotTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarMotTraActionPerformed
        txtDesLarMotTra.transferFocus();
    }//GEN-LAST:event_txtDesLarMotTraActionPerformed

    private void txtDesCorMotTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorMotTraActionPerformed
        txtDesCorMotTra.transferFocus();
    }//GEN-LAST:event_txtDesCorMotTraActionPerformed

    private void txtDesLarMotTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarMotTraFocusGained
        strDesLarMotTra = txtDesLarMotTra.getText();
        txtDesLarMotTra.selectAll();
    }//GEN-LAST:event_txtDesLarMotTraFocusGained

    private void txtDesCorMotTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorMotTraFocusGained
        strDesCorMotTra = txtDesCorMotTra.getText();
        txtDesCorMotTra.selectAll();
    }//GEN-LAST:event_txtDesCorMotTraFocusGained

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        BuscarTipDoc("a.co_tipdoc", "", 0);
}//GEN-LAST:event_butTipDocActionPerformed

    private void butGuiRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuiRemActionPerformed
        BuscarGuiPrin("a.co_emp", "", 0);
    }//GEN-LAST:event_butGuiRemActionPerformed

    private void butForRetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butForRetActionPerformed
        Ventas.ZafVen01.ZafVen01_02 obj = new Ventas.ZafVen01.ZafVen01_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
        obj.show();

        if (obj.acepta()) 
        {
            txtCodForRet.setText(obj.GetCamSel(1));
            txtDesForRet.setText(obj.GetCamSel(2));
        }
}//GEN-LAST:event_butForRetActionPerformed

    private void txtNumGuiRemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumGuiRemKeyPressed
        if (java.awt.event.KeyEvent.VK_ENTER == evt.getKeyCode()) 
        {
            if (txtNumGuiRem.getText().equals(""))
            {
                txtNumGuiRem.setText("");
            } 
            else 
            {
                //BuscarGuiPrin("a.ne_numdoc", txtNumGuiRem.getText(), 4);
                _consultar(FilSql());
                
                txtNumGuiRem.setEditable(false);
                butGuiRem.setEnabled(false);
                objTooBar.setEstado('w');
                butTieGuiRem.setEnabled(true);
                butTieDev.setEnabled(true);
            }
        }
        
    }//GEN-LAST:event_txtNumGuiRemKeyPressed

    private void butTieGuiRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTieGuiRemActionPerformed
       String strSql;
        if (chkTieGuiRem.isSelected()) 
        {
            strSql =" select a2.tx_descor, a1.ne_numdoc, a1.fe_doc from tbr_cabguirem as a ";
            strSql+=" inner join tbm_cabguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) ";
            strSql+=" inner join tbm_cabtipdoc as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc) ";
            strSql+=" where a.co_emp=" + strCodEmpGuiPrin + " and a.co_locrel=" + strCodLocGuiPrin + " and a.co_tipdocrel=" + strCodTipDocGuiPrin + " and a.co_docrel=" + strCodDocGuiPrin ;
            strSql+=" and a1.st_reg='A' ";
            
            //System.out.println("chkTieGuiRem: "+strSql);
            ZafCom23_01 obj1 = new ZafCom23_01(JOptionPane.getFrameForComponent(this), true, objParSis, txtCodPunPar.getText(), this, strSql);
            obj1.show();
        }
    }//GEN-LAST:event_butTieGuiRemActionPerformed

    private void txtNumOrdDesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumOrdDesKeyPressed
        if (java.awt.event.KeyEvent.VK_ENTER == evt.getKeyCode()) 
        {
            if (txtNumOrdDes.getText().equals("")) 
            {
                txtNumOrdDes.setText("");
            } 
            else 
            {
                //BuscarGuiPrin("a.ne_numorddes", txtNumOrdDes.getText(), 7);
                _consultar(FilSql());
                
                txtNumGuiRem.setEditable(false);
                butGuiRem.setEnabled(false);
                objTooBar.setEstado('w');
                butTieGuiRem.setEnabled(true);
                butTieDev.setEnabled(true);
            }
        }
    }//GEN-LAST:event_txtNumOrdDesKeyPressed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 ) {
            Runtime.getRuntime().gc();
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void butTieDevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTieDevActionPerformed
        if (chkTieDev.isSelected()) 
        {
            ZafCom23_02 obj1 = new ZafCom23_02(JOptionPane.getFrameForComponent(this), true, objParSis, this, strDev);
            obj1.show();
        }
    }//GEN-LAST:event_butTieDevActionPerformed
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* Variables declaration - do not modify */">    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanTabForRet;
    private javax.swing.JButton butForRet;
    private javax.swing.JButton butGuiRem;
    private javax.swing.JButton butTieDev;
    private javax.swing.JButton butTieGuiRem;
    private javax.swing.JButton butTipDoc;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JCheckBox chkTieDev;
    private javax.swing.JCheckBox chkTieGuiRem;
    private javax.swing.JLabel lblChoRet;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblDes;
    private javax.swing.JLabel lblDocOri;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecIniTra;
    private javax.swing.JLabel lblFecTerTra;
    private javax.swing.JLabel lblForRet;
    private javax.swing.JLabel lblMotTra;
    private javax.swing.JLabel lblNumGuiRem;
    private javax.swing.JLabel lblNumOrdDes;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPes;
    private javax.swing.JLabel lblPtoLle;
    private javax.swing.JLabel lblPtoPar;
    private javax.swing.JLabel lblTieDev;
    private javax.swing.JLabel lblTieGuiRem;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTipGui;
    private javax.swing.JLabel lblTipOrdCon;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVehRet;
    private javax.swing.JRadioButton optConGuiRem;
    private javax.swing.JRadioButton optConOrdDes;
    private javax.swing.JRadioButton optGuiRem;
    private javax.swing.JRadioButton optOrdDes;
    private javax.swing.JPanel panForRet;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panObs1;
    private javax.swing.JPanel panObs2;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTabGenCab;
    private javax.swing.JPanel panTabGenDet;
    private javax.swing.JPanel panTabGenObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtChoRet;
    private javax.swing.JTextField txtCodDes;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodForRet;
    private javax.swing.JTextField txtCodPtoPar;
    private javax.swing.JTextField txtDesCorMotTra;
    private javax.swing.JTextField txtDesCorPtoLle;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesForRet;
    private javax.swing.JTextField txtDesLarMotTra;
    private javax.swing.JTextField txtDesLarPtoLle;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDesPtoPar;
    private javax.swing.JTextField txtDocOri;
    private javax.swing.JTextField txtNomDes;
    private javax.swing.JTextField txtNumGuiRem;
    private javax.swing.JTextField txtNumOrdDes;
    private javax.swing.JTextArea txtObs1;
    private javax.swing.JTextArea txtObs2;
    private javax.swing.JTextField txtPes;
    private javax.swing.JTextField txtVehRet;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="/* Metodos */">
    
    //<editor-fold defaultstate=""collapsed" desc="/* Funciones de Búsqueda */">
    
    public void BuscarTipDoc(String campo, String strBusqueda, int tipo) 
    {
        vcoTipDoc.setTitle("Listado de Tipos de Documentos");
        if (vcoTipDoc.buscar(campo, strBusqueda)) 
        {
            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
            strCodTipDoc = vcoTipDoc.getValueAt(1);
            strDesCorTipDoc = vcoTipDoc.getValueAt(2);
            strDesLarTipDoc = vcoTipDoc.getValueAt(3);
        } 
        else 
        {
            vcoTipDoc.setCampoBusqueda(tipo);
            vcoTipDoc.cargarDatos();
            vcoTipDoc.show();
            if (vcoTipDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
            {
                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                strCodTipDoc = vcoTipDoc.getValueAt(1);
                strDesCorTipDoc = vcoTipDoc.getValueAt(2);
                strDesLarTipDoc = vcoTipDoc.getValueAt(3);
            }
            else 
            {
                txtCodTipDoc.setText(strCodTipDoc);
                txtDesCorTipDoc.setText(strDesCorTipDoc);
                txtDesLarTipDoc.setText(strDesLarTipDoc);
            }
        }
    }

    public void BuscarPtoPar(String campo, String strBusqueda, int tipo) 
    {
        vcoPtoPar.setTitle("Listado de Puntos de partida.");
        if (vcoPtoPar.buscar(campo, strBusqueda)) 
        {
            txtCodPunPar.setText(vcoPtoPar.getValueAt(1));
            txtDesPtoPar.setText(vcoPtoPar.getValueAt(2));
            strCodPtoPar = vcoPtoPar.getValueAt(1);
            strDesPtoPar = vcoPtoPar.getValueAt(2);
        }
        else
        {
            vcoPtoPar.setCampoBusqueda(tipo);
            vcoPtoPar.cargarDatos();
            vcoPtoPar.show();
            if (vcoPtoPar.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
            {
                txtCodPunPar.setText(vcoPtoPar.getValueAt(1));
                txtDesPtoPar.setText(vcoPtoPar.getValueAt(2));
                strCodPtoPar = vcoPtoPar.getValueAt(1);
                strDesPtoPar = vcoPtoPar.getValueAt(2);
            }
            else 
            {
                txtCodPunPar.setText(strCodPtoPar);
                txtDesPtoPar.setText(strDesPtoPar);
            }
        }
    }

    public void BuscarCliDes(String campo, String strBusqueda, int tipo) 
    {
        vcoCliDes.setTitle("Listado de Clientes/Proveedores.");
        if (vcoCliDes.buscar(campo, strBusqueda)) 
        {
            txtCodDes.setText(vcoCliDes.getValueAt(1));
            txtNomDes.setText(vcoCliDes.getValueAt(2));
            txtRucCli.setText(vcoCliDes.getValueAt(3));
            txtCiuCli.setText(vcoCliDes.getValueAt(4));
            txtTelCli.setText(vcoCliDes.getValueAt(5));
            strCodCliDes = vcoCliDes.getValueAt(1);
            strNomCliDes = vcoCliDes.getValueAt(2);
            strRucCli = vcoCliDes.getValueAt(3);
            strCiuCli = vcoCliDes.getValueAt(4);
            strTelCli = vcoCliDes.getValueAt(5);
        }
        else
        {
            vcoCliDes.setCampoBusqueda(tipo);
            vcoCliDes.cargarDatos();
            vcoCliDes.show();
            if (vcoCliDes.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
            {
                txtCodDes.setText(vcoCliDes.getValueAt(1));
                txtNomDes.setText(vcoCliDes.getValueAt(2));
                txtRucCli.setText(vcoCliDes.getValueAt(3));
                txtCiuCli.setText(vcoCliDes.getValueAt(4));
                txtTelCli.setText(vcoCliDes.getValueAt(5));
                strCodCliDes = vcoCliDes.getValueAt(1);
                strNomCliDes = vcoCliDes.getValueAt(2);
                strRucCli = vcoCliDes.getValueAt(3);
                strCiuCli = vcoCliDes.getValueAt(4);
                strTelCli = vcoCliDes.getValueAt(5);

            } 
            else
            {
                txtCodDes.setText(strCodCliDes);
                txtNomDes.setText(strNomCliDes);
                txtRucCli.setText(strRucCli);
                txtCiuCli.setText(strCiuCli);
                txtTelCli.setText(strTelCli);
            }
        }
    }

    public void BuscarPtoLle() 
    {
        vcoPtoLle.setTitle("Listado de Direcciones de Clientes");
        vcoPtoLle.setSentenciaSQL("select co_cli, tx_dir  from tbm_dircli where co_emp=" + objParSis.getCodigoEmpresa() + " and co_cli=" + (txtCodDes.getText().equals("") ? "0" : txtCodDes.getText()));
        vcoPtoLle.setCampoBusqueda(1);
        vcoPtoLle.cargarDatos();
        vcoPtoLle.show();
        if (vcoPtoLle.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
        {
            txtDesCorPtoLle.setText(vcoPtoLle.getValueAt(1));
            txtDesLarPtoLle.setText(vcoPtoLle.getValueAt(2));
            strCodPtoLle = vcoPtoLle.getValueAt(1);
            strDesPtoLle = vcoPtoLle.getValueAt(2);
        }
    }

    public void BuscarMotTra(String campo, String strBusqueda, int tipo) 
    {
        vcoMotTra.setTitle("Listado de Motivos de Traslado");
        if (vcoMotTra.buscar(campo, strBusqueda)) 
        {
            txtCodMotTrans.setText(vcoMotTra.getValueAt(1));
            txtDesCorMotTra.setText(vcoMotTra.getValueAt(2));
            txtDesLarMotTra.setText(vcoMotTra.getValueAt(3));
            strCodMotTra = vcoMotTra.getValueAt(1);
            strDesCorMotTra = vcoMotTra.getValueAt(2);
            strDesLarMotTra = vcoMotTra.getValueAt(3);
        } 
        else 
        {
            vcoMotTra.setCampoBusqueda(tipo);
            vcoMotTra.cargarDatos();
            vcoMotTra.show();
            if (vcoMotTra.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
            {
                txtCodMotTrans.setText(vcoMotTra.getValueAt(1));
                txtDesCorMotTra.setText(vcoMotTra.getValueAt(2));
                txtDesLarMotTra.setText(vcoMotTra.getValueAt(3));
                strCodMotTra = vcoMotTra.getValueAt(1);
                strDesCorMotTra = vcoMotTra.getValueAt(2);
                strDesLarMotTra = vcoMotTra.getValueAt(3);
            }
            else 
            {
                txtCodMotTrans.setText(strCodMotTra);
                txtDesCorMotTra.setText(strDesCorMotTra);
                txtDesLarMotTra.setText(strDesLarMotTra);
            }
        }
    }
    
    public void BuscarGuiPrin(String campo, String strBusqueda, int tipo) 
    {
        vcoGuiRemOrdDes.setTitle("Listado de Documentos");
        if (vcoGuiRemOrdDes.buscar(campo, strBusqueda))
        {
            cargarDatos(vcoGuiRemOrdDes.getValueAt(1), vcoGuiRemOrdDes.getValueAt(2), vcoGuiRemOrdDes.getValueAt(3), vcoGuiRemOrdDes.getValueAt(4));
            txtNumGuiRem.setEditable(false);
            butGuiRem.setEnabled(false);

            if ((objParSis.getCodigoMenu() == 1793) || (objParSis.getCodigoMenu() == 3497)) 
            {
                objTooBar.setEstado('w');
                butTieGuiRem.setEnabled(true);
                butTieDev.setEnabled(true);
            }
        } 
        else 
        {
            vcoGuiRemOrdDes.setCampoBusqueda(tipo);
            vcoGuiRemOrdDes.cargarDatos();
            vcoGuiRemOrdDes.show();
            if (vcoGuiRemOrdDes.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
            {
                cargarDatos(vcoGuiRemOrdDes.getValueAt(1), vcoGuiRemOrdDes.getValueAt(2), vcoGuiRemOrdDes.getValueAt(3), vcoGuiRemOrdDes.getValueAt(4));
                txtNumGuiRem.setEditable(false);
                butGuiRem.setEnabled(false);

                if ((objParSis.getCodigoMenu() == 1793) || (objParSis.getCodigoMenu() == 3497)) 
                {
                    objTooBar.setEstado('w');
                    butTieGuiRem.setEnabled(true);
                    butTieDev.setEnabled(true);
                }

            }

        }
    }
    
    //</editor-fold>
    
     /**
     * Permite obtener la Ip de impresion de guia.
     */
    private void cargarIpPuertoGuiaEmp() 
    {
        java.sql.Statement stmTipEmp;
        java.sql.ResultSet rstEmp;
        String strSQL;
        try 
        {
            if (CONN_GLO != null) 
            {
                stmTipEmp = CONN_GLO.createStatement();

                strSQL = " SELECT a1.co_emp, a1.co_loc, a1.tx_dirser, a1.ne_pueser "
                     + " FROM tbm_serCliSer AS a "
                     + " INNER JOIN tbm_serCliSerLoc AS a1 ON( a1.co_ser=a.co_ser ) "
                     + " WHERE a.co_ser=1  ";
                rstEmp = stmTipEmp.executeQuery(strSQL);
                
                while (rstEmp.next()) 
                {
                    if ((rstEmp.getInt("co_emp") == 1) && (rstEmp.getInt("co_loc") == 4)) 
                    {
                        strIpLocTuv = rstEmp.getString("tx_dirser");
                        INT_PUE_LOC_TUV = rstEmp.getInt("ne_pueser");
                    }
                    if ((rstEmp.getInt("co_emp") == 2) && (rstEmp.getInt("co_loc") == 1))
                    {
                        strIpLocQui = rstEmp.getString("tx_dirser");
                        INT_PUE_LOC_UIO = rstEmp.getInt("ne_pueser");
                    }
                    if ((rstEmp.getInt("co_emp") == 2) && (rstEmp.getInt("co_loc") == 4)) 
                    {
                        strIpLocMan = rstEmp.getString("tx_dirser");
                        INT_PUE_LOC_MAN = rstEmp.getInt("ne_pueser");
                    }
                    if ((rstEmp.getInt("co_emp") == 2) && (rstEmp.getInt("co_loc") == 6)) 
                    {
                        strIpLocSTD = rstEmp.getString("tx_dirser");
                        INT_PUE_LOC_STO_DGO = rstEmp.getInt("ne_pueser");
                    }
                    if ((rstEmp.getInt("co_emp") == 2) && (rstEmp.getInt("co_loc") == 10)) 
                    {
                        strIpLocCue = rstEmp.getString("tx_dirser");
                        INT_PUE_LOC_CUE = rstEmp.getInt("ne_pueser");
                    }
                    if ((rstEmp.getInt("co_emp") == 4) && (rstEmp.getInt("co_loc") == 3)) 
                    {
                        strIpLocDim = rstEmp.getString("tx_dirser");
                        INT_PUE_LOC_DIM = rstEmp.getInt("ne_pueser");
                    }
                }
                rstEmp.close();
                stmTipEmp.close();
                stmTipEmp = null;
                rstEmp = null;
            }
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    /**
     * Actualiza estado de Impresión en las Guías de Remisión y Ordenes de Despacho reimpresas.
     * @return 
     */
    public boolean cambiarEstadoImpresion() 
    {
        boolean blnRes = false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try 
        {
            conLoc = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                strSQL = "";
                if (objParSis.getCodigoMenu() == 1793) //Guia Remisión
                {
                    strSQL += " UPDATE tbm_cabGuiRem";
                    strSQL += " SET st_imp='S'";
                    strSQL += " WHERE co_emp=" + rstCab.getString("co_emp");
                    strSQL += " AND co_loc=" + rstCab.getString("co_loc");
                    strSQL += " AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                    strSQL += " AND co_doc=" + rstCab.getString("co_doc");
                    strSQL += " AND ne_numDoc>0 " ;
                    strSQL += "; ";
                }
                else if (objParSis.getCodigoMenu() == 3497) //Ordenes Despacho
                {
                    strSQL += " UPDATE tbm_cabGuiRem";
                    strSQL += " SET st_impOrdDes='S'";
                    strSQL += " WHERE co_emp=" + rstCab.getString("co_emp");
                    strSQL += " AND co_loc=" + rstCab.getString("co_loc");
                    strSQL += " AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                    strSQL += " AND co_doc=" + rstCab.getString("co_doc");
                    strSQL += " AND ne_numOrdDes>0 " ;
                    strSQL += "; ";
                }
                
                //System.out.println("cambiarEstadoImpresion" + strSQL);
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

    private void calcularPeso() 
    {
        double dlbPesTotKgr = 0;
        try {
            for (int i = 0; i < tblDat.getRowCount(); i++) {
                if (tblDat.getValueAt(i, INT_TBL_CAN_GUI_REM) != null) {

                    double dlbCan = Double.parseDouble(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CAN_GUI_REM)));
                    double dlbPesKgr = Double.parseDouble(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_PES_KGR)));
                    double dlbKgr = dlbCan * dlbPesKgr;
                    dlbPesTotKgr += dlbKgr;
                }
            }
            txtPes.setText("" + objUti.redondear(dlbPesTotKgr, 2));
        }
        catch (Exception e) { objUti.mostrarMsgErr_F1(this, e);   }
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
    

    private boolean cargarDatos(String intCodEmp, String intCodLoc, String intCodTipDoc, String intCodDoc) 
    {
        boolean blnRes = true;
        java.sql.Connection conn;
        try 
        {
            if (INT_COD_REG_CEN != 0) 
            {
                conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(INT_COD_REG_CEN), objParSis.getUsuarioBaseDatos(INT_COD_REG_CEN), objParSis.getClaveBaseDatos(INT_COD_REG_CEN));
            }
            else
            {
                conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            }

            if (conn != null) 
            {
                cargarCabReg(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
                cargarDetReg(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);

                conn.close();
                conn = null;
            }
        } 
        catch (java.sql.SQLException Evt) {  blnRes = false; objUti.mostrarMsgErr_F1(this, Evt);  } 
        catch (Exception Evt) {  blnRes = false; objUti.mostrarMsgErr_F1(this, Evt);   }
        return blnRes;
    }

    private boolean cargarCabReg(java.sql.Connection conn, String intCodEmp, String intCodLoc, String intCodTipDoc, String intCodDoc) 
    {
        boolean blnRes = true;
        String strSQL ;
        try 
        {
            if (conn != null)
            {
                java.sql.Statement stmCab = conn.createStatement();

                strSQL =" SELECT  a.co_ptodes, a.tx_numped, a.st_tipguirem,  a.co_ven, a.tx_nomven, a.co_forRet, forret.tx_deslar as desforret, a.tx_vehRet, a.tx_choRet,  ";
                strSQL+="         a.tx_datdocoriguirem, a1.co_tipdoc as codTipDoc, a1.tx_descor as desCorTipDoc, a1.tx_deslar as desLarTipDoc,  ";
                strSQL+="         a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a.fe_initra, a.fe_tertra, a.ne_numdoc, a.tx_ptopar, a.co_clides, ";
                strSQL+="         a.tx_rucclides, a.tx_nomclides, a.tx_dirclides, a.tx_telclides, a.tx_ciuclides, a.co_mottra, a.tx_desmottra, ";
                strSQL+="         a.nd_pestotkgr, a.st_imp, a.tx_obs1, a.tx_obs2, a.st_reg  , v.tx_descor, a.co_ptopar , a.st_tieGuiSec as TieGuiRem, a.ne_numorddes ";
                strSQL+=" FROM tbm_cabguirem as a ";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a1 ON(a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc ) ";
                strSQL+=" LEFT JOIN tbm_var as v  on (v.co_reg=a.co_mottra) ";
                strSQL+=" LEFT JOIN tbm_var as forret  on (forret.co_reg=a.co_forRet) ";
                strSQL+=" WHERE a.co_emp=" + intCodEmp + " AND a.co_loc=" + intCodLoc;
                strSQL+=" AND a.co_tipdoc=" + intCodTipDoc + " AND co_doc=" + intCodDoc;
                System.out.println("cargarCabReg--> (GUIA)" + strSQL);
                java.sql.ResultSet rst = stmCab.executeQuery(strSQL);

                vecData.clear();
                while (rst.next()) 
                {
                    strPtoDes = rst.getString("co_ptodes");
                    strCodEmpGuiPrin = intCodEmp;
                    strCodLocGuiPrin = intCodLoc;
                    strCodTipDocGuiPrin = intCodTipDoc;
                    strCodDocGuiPrin = intCodDoc;
                    strDocOriGuiRem = rst.getString("desCorTipDoc") + "-" + rst.getString("ne_numdoc");
                    strCodVenGuiPrin = rst.getString("co_ven");
                    strNomVenGuiPrin = rst.getString("tx_nomven");

                    if (rst.getString("st_tipGuiRem").equals("P")) optOrdDes.setSelected(true);
                    if (rst.getString("st_tipGuiRem").equals("S")) optGuiRem.setSelected(true);
                    
                    txtNumPed.setText(rst.getString("tx_numped"));
                    txtCodPtoPar.setText(rst.getString("co_ptopar"));

                    if (rst.getString("co_ptopar").equalsIgnoreCase("15")) 
                    {
                        lblNumOrdDes.setText("Orden Despacho: " + (intCodEmp.equalsIgnoreCase("1") ? "** TUVAL" : (intCodEmp.equalsIgnoreCase("2") ? "** CASTEK" : (intCodEmp.equalsIgnoreCase("4") ? "** DIMULTI" : ""))));
                    }

                    if (rst.getString("TieGuiRem").equals("S")) 
                        chkTieGuiRem.setSelected(true); 
                    else     
                        chkTieGuiRem.setSelected(false);
                    

                    if (rst.getDate("fe_doc") == null) 
                    {
                        txtFecDoc.setText("");
                    } 
                    else 
                    {
                        java.util.Date dateObj = rst.getDate("fe_doc");
                        Calendar calObj = Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecDoc.setText(calObj.get(Calendar.DAY_OF_MONTH), calObj.get(Calendar.MONTH) + 1, calObj.get(Calendar.YEAR));
                    }

                    if (rst.getDate("fe_initra") == null) 
                    {
                        txtFecIniTrans.setText("");
                    } 
                    else {
                        java.util.Date dateObj = rst.getDate("fe_initra");
                        Calendar calObj = Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecIniTrans.setText(calObj.get(Calendar.DAY_OF_MONTH), calObj.get(Calendar.MONTH) + 1, calObj.get(Calendar.YEAR));
                    }

                    if (rst.getDate("fe_tertra") == null) 
                    {
                        txtFecTerTrans.setText("");
                    }
                    else 
                    {
                        java.util.Date dateObj = rst.getDate("fe_tertra");
                        Calendar calObj = Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecTerTrans.setText(calObj.get(Calendar.DAY_OF_MONTH), calObj.get(Calendar.MONTH) + 1, calObj.get(Calendar.YEAR));
                    }

                    txtCodForRet.setText(rst.getString("co_forRet"));
                    txtDesForRet.setText(rst.getString("desforret"));
                    txtVehRet.setText(rst.getString("tx_vehRet"));
                    txtChoRet.setText(rst.getString("tx_choRet"));
                    txtCodTipDoc.setText(rst.getString("codTipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("desCorTipDoc"));
                    txtDesLarTipDoc.setText(rst.getString("desLarTipDoc"));
                    //  strCodTipDoc=rst.getString("codTipDoc");
                    //  strDesCorTipDoc=rst.getString("desCorTipDoc");
                    //  strDesLarTipDoc=rst.getString("desLarTipDoc");

                    txtCodPunPar.setText(rst.getString("co_ptopar"));
                    txtDesPtoPar.setText(rst.getString("tx_ptopar"));
                    txtCodDes.setText(rst.getString("co_clides"));
                    txtNomDes.setText(rst.getString("tx_nomclides"));
                    txtRucCli.setText(rst.getString("tx_rucclides"));
                    txtCiuCli.setText(rst.getString("tx_ciuclides"));
                    txtTelCli.setText(rst.getString("tx_telclides"));

                    txtDesCorPtoLle.setText(rst.getString("co_clides"));
                    txtDesLarPtoLle.setText(rst.getString("tx_dirclides"));

                    txtCodMotTrans.setText(rst.getString("co_mottra"));
                    txtDesCorMotTra.setText(rst.getString("tx_descor"));
                    txtDesLarMotTra.setText(rst.getString("tx_desmottra"));

                    txtNumGuiRem.setText(rst.getString("ne_numdoc"));
                    txtPes.setText(rst.getString("nd_pestotkgr"));
                    txtNumOrdDes.setText(rst.getString("ne_numorddes"));
                    txtCodDoc.setText(intCodDoc);

                    txtDocOri.setText(rst.getString("tx_datdocoriguirem"));

                    //Mostrar el estado del registro.
                    strAux = rst.getString("st_reg");
                    if (strAux.equals("A")) {
                        strAux = "Activo";
                    } else if (strAux.equals("I")) {
                        strAux = "Anulado";
                    } else {
                        strAux = "Otro";
                    }
                    objTooBar.setEstadoRegistro(strAux);

                    double dblNumDoc = objUti.parseDouble(rst.getString("ne_numdoc"));
                    if (dblNumDoc > 0) {
                        blnIsGuiRem = true;
                    } else {
                        blnIsGuiRem = false;
                    }
                }
                rst.close();
                rst = null;
                stmCab.close();
                stmCab = null;
            }
        } 
        catch (java.sql.SQLException Evt) { blnRes = false; objUti.mostrarMsgErr_F1(this, Evt);  } 
        catch (Exception Evt) {    blnRes = false;   objUti.mostrarMsgErr_F1(this, Evt);  }
        return blnRes;
    }

    public boolean cargarDetReg(java.sql.Connection conn, String intCodEmp, String intCodLoc, String intCodTipDoc, String intCodDoc)
    {
        boolean blnRes = true;
        double dlbPesGramo = 0;
        String strSQL;
        try 
        {
            if (conn != null)
            {
                java.sql.Statement stmCab = conn.createStatement();
                
                if(objParSis.getCodigoMenu()==3497)
                {
                    strSQL =" SELECT x.*,  ";    
                    strSQL+="        CASE WHEN (x.st_merEgrFisBod = 'N') THEN (nd_CanCon + nd_canNunRec) ";
                    strSQL+="        ELSE CASE WHEN (x.st_merEgrFisBod = 'A') THEN nd_Can ";
                    strSQL+="        ELSE (nd_CanCon + nd_canNunRec + nd_canTra) END END as nd_canTotCon,  "; 
                    strSQL+="        CASE WHEN (x.st_merEgrFisBod = 'N' AND nd_CanTotGui>0) THEN (nd_Can - nd_canTotGui) ";
                    strSQL+="        ELSE CASE WHEN (x.st_merEgrFisBod = 'A') THEN nd_canPen ";
                    strSQL+="        ELSE (nd_Can - (nd_CanCon + nd_canNunRec + nd_canTra)) END END as nd_canTotPen,";
                    strSQL+="        (x.nd_pesitmkgr*nd_Can) as kgramo, (((x.nd_pesitmkgr*nd_Can)*2.2)/100) as kilo ";           
                    strSQL+=" FROM "; 
                    strSQL+=" ( "; 
                    strSQL+="     SELECT c.st_merIngEgrfisbod AS st_merEgrFisBod, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, a.ne_numdocrel, "; 
                    strSQL+="            a.co_itm, a.tx_codalt, b.tx_codalt2, a.tx_nomitm, a.tx_unimed, "; 
                    strSQL+="            CASE WHEN c.nd_Can IS NULL THEN 0 ELSE abs(c.nd_Can) END as nd_Can, "; 
                    strSQL+="            CASE WHEN a.nd_CanCon = 0 THEN abs(c.nd_CanCon) ELSE abs(a.nd_CanCon) END as nd_CanCon,   ";
                    strSQL+="            CASE WHEN c.nd_canNunRec = 0 THEN abs(a.nd_CanNunRec) ELSE abs(c.nd_canNunRec) END as nd_canNunRec, "; 
                    strSQL+="            CASE WHEN c.nd_canTra IS NULL THEN 0 ELSE abs(c.nd_canTra) END as nd_canTra, "; 
                    strSQL+="            CASE WHEN c.nd_canEgrBod IS NULL THEN 0 ELSE abs(c.nd_canEgrBod) END as nd_canEgrBod, "; 
                    strSQL+="            CASE WHEN c.nd_canDesEntCli IS NULL THEN 0 ELSE abs(c.nd_canDesEntCli) END as nd_canDesEntCli, "; 
                    strSQL+="            CASE WHEN c.nd_canPen IS NULL THEN 0 ELSE abs(c.nd_canPen) END as nd_canPen, "; 
                    strSQL+="            CASE WHEN a.nd_canTotGuiSec IS NULL THEN 0 ELSE abs(a.nd_canTotGuiSec) END as nd_canTotGui, "; 
                    strSQL+="            a.nd_pestotkgr, b.nd_pesitmkgr "; 
                    strSQL+="     FROM tbm_detguirem as a  "; 
                    strSQL+="     INNER JOIN tbm_inv as b on (b.co_emp=a.co_emp and b.co_itm=a.co_itm)  "; 
                    strSQL+="     INNER JOIN tbm_detmovinv as c on (a.co_emprel=c.co_emp and a.co_locrel=c.co_loc and a.co_tipdocrel=c.co_tipdoc and a.co_docrel=c.co_doc and a.co_regrel=c.co_reg)  "; 
                    strSQL+="     WHERE a.co_emp=" + intCodEmp;
                    strSQL+="     AND a.co_loc=" + intCodLoc;
                    strSQL+="     AND a.co_tipdoc=" + intCodTipDoc;
                    strSQL+="     AND a.co_doc=" + intCodDoc;
                    strSQL+=" ) as x "; 
                }
                else  //if (objParSis.getCodigoMenu()==1793)  
                {
                    strSQL =" SELECT x.*, (x.nd_pesitmkgr*nd_Can) as kgramo, (((x.nd_pesitmkgr*nd_Can)*2.2)/100) as kilo  ";
                    strSQL+=" FROM  ";
                    strSQL+=" (   ";
                    strSQL+="    SELECT a.st_meregrfisbod, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, a.ne_numdocrel, ";
                    strSQL+="           a.co_itm, a.tx_codalt, b.tx_codalt2, a.tx_nomitm, a.tx_unimed,  ";
                    strSQL+="           CASE WHEN a.nd_Can IS NULL THEN 0 ELSE abs(a.nd_Can) END as nd_Can,  ";
                    strSQL+="           CASE WHEN (a.st_meregrfisbod IN  ('N', 'A') ) THEN abs(a.nd_canNunRec) ELSE abs(a.nd_CanCon) END as nd_canCon,  "; 
                    strSQL+="           CASE WHEN (a.st_meregrfisbod IN  ('N', 'A') ) THEN abs(a.nd_canNunRec) ELSE abs(a.nd_CanCon) END as nd_canTotCon,  ";                     
                    strSQL+="           0 as nd_canTotPen, a.nd_pestotkgr, b.nd_pesitmkgr ";
                    strSQL+="     FROM tbm_detguirem as a  ";
                    strSQL+="     INNER JOIN tbm_inv as b on (b.co_emp=a.co_emp and b.co_itm=a.co_itm) ";
                    strSQL+="     WHERE a.co_emp=" + intCodEmp;
                    strSQL+="     AND a.co_loc=" + intCodLoc;
                    strSQL+="     AND a.co_tipdoc=" + intCodTipDoc;
                    strSQL+="     AND a.co_doc=" + intCodDoc;
                    strSQL+=" ) as x ";
                    
                }
                System.out.println("ZafCom23.cargarDetReg: " + strSQL);

                java.sql.ResultSet rst = stmCab.executeQuery(strSQL);

                vecData.clear();
                while (rst.next())
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LIN, "");
                    vecReg.add(INT_TBL_CHK_DOC, "...");
                    vecReg.add(INT_TBL_COD_ITM, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_COD_ALT_ITM, rst.getString("tx_codalt"));
                    vecReg.add(INT_TBL_COD_ALT_2, rst.getString("tx_codalt2"));
                    vecReg.add(INT_TBL_NOM_ITM, rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_DES_COR_UNI_MED, rst.getString("tx_unimed"));
                    vecReg.add(INT_TBL_CAN_ITM, rst.getString("nd_can"));
                    vecReg.add(INT_TBL_NUM_DOC, rst.getString("ne_numdocrel"));
                    vecReg.add(INT_TBL_COD_EMP, rst.getString("co_emp"));
                    vecReg.add(INT_TBL_COD_LOC, rst.getString("co_loc"));
                    vecReg.add(INT_TBL_COD_TIP_DOC, rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_COD_DOC, rst.getString("co_doc"));
                    vecReg.add(INT_TBL_COD_REG, rst.getString("co_reg"));
                    vecReg.add(INT_TBL_MAR_ELI, "");
                    vecReg.add(INT_TBL_PES_KGR, rst.getString("nd_pesitmkgr"));
                    vecReg.add(INT_TBL_CAN_TOT_CON, rst.getString("nd_canTotCon"));
                    vecReg.add(INT_TBL_CAN_PEN_ITM, rst.getString("nd_canTotPen"));
                    vecReg.add(INT_TBL_CAN_GUI_REM, "");
                    vecReg.add(INT_TBL_MER_EGR_FIS, rst.getString("st_merEgrFisBod"));

                    dlbPesGramo += rst.getDouble("kgramo");
                    vecData.add(vecReg);
                }
                objTblMod.setData(vecData);
                tblDat.setModel(objTblMod);
                rst.close();
                rst = null;

                // if(objParSis.getCodigoMenu()==1793)
                txtPes.setText("" + objUti.redondear(dlbPesGramo, 2));

                stmCab.close();
                stmCab = null;

                objTblMod.setDataModelChanged(false);
                blnHayCam = false;
            }
        } 
        catch (java.sql.SQLException Evt) {  objUti.mostrarMsgErr_F1(this, Evt);   } 
        catch (Exception Evt) {   objUti.mostrarMsgErr_F1(this, Evt);  }
        return blnRes;
    }
   

    public String FilSql() 
    {
        String sqlFiltro = "";

        if (!txtCodTipDoc.getText().equals("")) {
            sqlFiltro = sqlFiltro + " AND a.co_tipdoc =" + txtCodTipDoc.getText() + "";
        }

        if (!txtCodDoc.getText().equals("")) {
            sqlFiltro = sqlFiltro + " AND a.co_doc =" + txtCodDoc.getText() + "";
        }

        if (!txtNumGuiRem.getText().equals("")) {
            sqlFiltro = sqlFiltro + " AND a.ne_numdoc =" + txtNumGuiRem.getText() + "";
        }

        if (!txtNumOrdDes.getText().equals("")) {
            sqlFiltro = sqlFiltro + " AND a.ne_numorddes =" + txtNumOrdDes.getText() + "";
        }

        //Filtro por Fecha
        if (txtFecDoc.isFecha()) {
            int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
            String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" + FecSql[0] + "#";
            sqlFiltro = sqlFiltro + " AND a.fe_doc = '" + strFecSql + "'";
        }

        //Filtro por Documento Origen
        strAux = txtDocOri.getText();
        if (!txtDocOri.getText().equals("")) {
            sqlFiltro = sqlFiltro + " AND LOWER( a.tx_datdocoriguirem) LIKE '%" + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "%' ";
        }

        //if(rdaGuiaPrin.isSelected())
        //  sqlFiltro = sqlFiltro + " AND a.st_tipguirem='P' ";
        //if(rdaGuiSecun.isSelected())
        //  sqlFiltro = sqlFiltro + " AND a.st_tipguirem='S' ";
        
        //Agregando filtro por Codigo de Cliente
        // if(!txtDesCorDes.getText().equals(""))
        //    sqlFiltro = sqlFiltro + " AND a.co_cli =" + txtDesCorDes.getText() + "";
        
        return sqlFiltro;
    }
    
    private boolean _consultar(String strFil) 
    {
        String strSQL = "";;
        String strFilTipDoc="", strFilBod="";
        try 
        {
            abrirCon();
            if (CONN_GLO != null) 
            {
                STM_GLO = CONN_GLO.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                strFilTipDoc=sqlConTipDoc();               
                strFilBod=sqlConBod(); 

                strSQL =" SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc ";
                strSQL+=" FROM tbm_cabguirem as a  ";
                strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) ";
                strSQL+=" WHERE a.st_reg not in('E') ";
                strSQL+=" AND a.co_tipdoc in ( " + strFilTipDoc + " )  ";
                strSQL+=" AND ( a6.co_empGrp=" + objParSis.getCodigoEmpresaGrupo()+" AND a6.co_bodGrp IN ( " + strFilBod + " ) ) ";
                strSQL+=strFil ;

                if (optConOrdDes.isSelected()) {
                    strSQL += " AND a.ne_numdoc=0 AND (a.fe_doc >= '#2011/09/26#') ";
                    strSQL += " ORDER BY a.fe_doc, a.ne_numorddes ";
                }

                if (optConGuiRem.isSelected()) {
                    strSQL += " AND (a.ne_numdoc is not null and a.ne_numdoc > 0 ) ";
                    strSQL += " ORDER BY a.fe_doc, a.ne_numdoc ";
                }

                //System.out.println("ZafCom23._consultar: " + strSQL);
                rstCab = STM_GLO.executeQuery(strSQL);

                if (rstCab.next()) {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    cargarReg();
                } else {
                    objTooBar.setMenSis("0 Registros encontrados");
                    clnTextos();
                    return false;
                }
            }
            CerrarCon();
        }
        catch (SQLException Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  return false;  } 
        catch (Exception Evt) {  return false;  }

        Runtime.getRuntime().gc();
        return true;
    }

    /**
     * Esta función permite cargar el registro seleccionado.
     *
     * @return true: Si se pudo cargar el registro. <BR>false: En el caso
     * contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes = true;
        try 
        {
            if (cargarCabReg()) 
            {
                //refrescaDatos();
                cargarDetReg(CONN_GLO, rstCab.getString("co_emp"), rstCab.getString("co_loc"), rstCab.getString("co_tipdoc"), rstCab.getString("co_doc"));
            }
            else 
            {
                MensajeInf("Error al cargar registro");
                objTblMod.setDataModelChanged(false);
                blnHayCam = false;
            }
        }
        catch (Exception e) {  blnRes = false; }
        return blnRes;
    }

       
    /**
     * MEJORA A NAVEGACION DE REGISTROS.
     */
    private boolean cargarCabReg()
    {
        boolean blnRes = true;
        int intPosRel;
        String strSQL;
        try 
        {
            if (CONN_GLO != null)
            {
                strSQL =" SELECT a.st_coninv, a.co_forRet, forret.tx_deslar as desforret, a.tx_vehRet, a.tx_choRet,  a.st_tieGuiSec as guisec, ";
                strSQL+="        a.st_tipGuiRem ,a.tx_datdocoriguirem, a1.co_tipdoc as codTipDoc, a1.tx_descor as desCorTipDoc, a1.tx_deslar as desLarTipDoc,  ";
                strSQL+="        a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a.fe_initra, a.fe_tertra, a.ne_numdoc, a.tx_ptopar, a.co_clides, ";
                strSQL+="        a.tx_rucclides, a.tx_nomclides, a.tx_dirclides, a.tx_telclides, a.tx_ciuclides, a.co_mottra, ";
                strSQL+="        a.tx_desmottra, a.nd_pestotkgr, a.st_imp, a.tx_obs1, a.tx_obs2, a.st_reg  , v.tx_descor as desCorMotTra, a.co_ptopar, a.ne_numorddes ";
                strSQL+=" FROM tbm_cabguirem as a ";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a1 ON(a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc ) ";
                strSQL+=" LEFT JOIN tbm_var as v  on (v.co_reg=a.co_mottra) ";
                strSQL+=" LEFT JOIN tbm_var as forret  on (forret.co_reg=a.co_forRet) ";
                strSQL+=" WHERE a.co_emp=" + rstCab.getString("co_emp") + " and a.co_loc=" + rstCab.getString("co_loc") ;
                strSQL+=" AND a.co_tipdoc=" + rstCab.getString("co_tipdoc") + " and co_doc=" + rstCab.getString("co_doc");
                // System.out.println("cargarCabReg: "+strSQL);
                Statement stmCab = CONN_GLO.createStatement();
                ResultSet rst = stmCab.executeQuery(strSQL);

                vecData.clear();
                while (rst.next()) 
                {
                    strEstConf = rst.getString("st_coninv");
                    strCodEmpGuiPrin = rstCab.getString("co_emp");
                    strCodLocGuiPrin = rstCab.getString("co_loc");
                    strCodTipDocGuiPrin = rstCab.getString("co_tipdoc");
                    strCodDocGuiPrin = rstCab.getString("co_doc");

                    txtCodForRet.setText(rst.getString("co_forRet"));
                    txtDesForRet.setText(rst.getString("desforret"));
                    txtVehRet.setText(rst.getString("tx_vehRet"));
                    txtChoRet.setText(rst.getString("tx_choRet"));

                    txtCodTipDoc.setText(rst.getString("codTipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("desCorTipDoc"));
                    txtDesLarTipDoc.setText(rst.getString("desLarTipDoc"));
                    strCodTipDoc = rst.getString("codTipDoc");
                    strDesCorTipDoc = rst.getString("desCorTipDoc");
                    strDesLarTipDoc = rst.getString("desLarTipDoc");

                    txtCodPunPar.setText(rst.getString("co_ptopar"));
                    txtDesPtoPar.setText(rst.getString("tx_ptopar"));

                    txtCodDes.setText(rst.getString("co_clides"));
                    txtNomDes.setText(rst.getString("tx_nomclides"));
                    txtRucCli.setText(rst.getString("tx_rucclides"));
                    txtCiuCli.setText(rst.getString("tx_ciuclides"));
                    txtTelCli.setText(rst.getString("tx_telclides"));

                    txtDesCorPtoLle.setText(rst.getString("co_clides"));
                    txtDesLarPtoLle.setText(rst.getString("tx_dirclides"));

                    txtCodMotTrans.setText(rst.getString("co_mottra"));
                    txtDesCorMotTra.setText(rst.getString("desCorMotTra"));
                    txtDesLarMotTra.setText(rst.getString("tx_desmottra"));

                    txtCodDoc.setText(rst.getString("co_doc"));
                    txtNumGuiRem.setText(rst.getString("ne_numdoc"));
                    txtNumOrdDes.setText(rst.getString("ne_numorddes"));
                    txtPes.setText(rst.getString("nd_pestotkgr"));

                    if (rst.getString("co_ptopar").equalsIgnoreCase("15"))
                    {
                        lblNumOrdDes.setText("Orden Despacho: " + (strCodEmpGuiPrin.equalsIgnoreCase("1") ? "** TUVAL" : (strCodEmpGuiPrin.equalsIgnoreCase("2") ? "** CASTEK" : (strCodEmpGuiPrin.equalsIgnoreCase("4") ? "** DIMULTI" : ""))));
                    }

                    txtObs1.setText(((rst.getString("tx_obs1") == null) ? "" : rst.getString("tx_obs1")));
                    txtObs2.setText(((rst.getString("tx_obs2") == null) ? "" : rst.getString("tx_obs2")));

                    if (rst.getString("st_tipGuiRem").equals("P")) {
                        optOrdDes.setSelected(true);
                    }
                    if (rst.getString("st_tipGuiRem").equals("S")) {
                        optGuiRem.setSelected(true);
                    }

                    txtDocOri.setText(rst.getString("tx_datdocoriguirem"));

                    if (rst.getString("guisec").equals("S")) {
                        chkTieGuiRem.setSelected(true);
                    } else {
                        chkTieGuiRem.setSelected(false);
                    }

                    if (rst.getDate("fe_doc") == null) {
                        txtFecDoc.setText("");
                    } else {
                        Date dateObj = rst.getDate("fe_doc");
                        Calendar calObj = Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecDoc.setText(calObj.get(Calendar.DAY_OF_MONTH), calObj.get(Calendar.MONTH) + 1, calObj.get(Calendar.YEAR));
                    }

                    if (rst.getDate("fe_initra") == null) {
                        txtFecIniTrans.setText("");
                    } else {
                        Date dateObj = rst.getDate("fe_initra");
                        Calendar calObj = Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecIniTrans.setText(calObj.get(Calendar.DAY_OF_MONTH), calObj.get(Calendar.MONTH) + 1, calObj.get(Calendar.YEAR));
                    }

                    if (rst.getDate("fe_tertra") == null) {
                        txtFecTerTrans.setText("");
                    } else {
                        Date dateObj = rst.getDate("fe_tertra");
                        Calendar calObj = Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecTerTrans.setText(calObj.get(Calendar.DAY_OF_MONTH), calObj.get(Calendar.MONTH) + 1, calObj.get(Calendar.YEAR));
                    }

                    //Mostrar el estado del registro.
                    strAux = rst.getString("st_reg");
                    if (strAux.equals("A")) {
                        strAux = "Activo";
                    } else if (strAux.equals("I")) {
                        strAux = "Anulado";
                    } else {
                        strAux = "Otro";
                    }
                    objTooBar.setEstadoRegistro(strAux);

                    double dblNumDoc = objUti.parseDouble(rst.getString("ne_numdoc"));
                    if (dblNumDoc > 0) {
                        blnIsGuiRem = true;
                    } else {
                        blnIsGuiRem = false;
                    }
                    
                    if(objParSis.getCodigoMenu()==3497)
                    {
                        consultaDevolucion(CONN_GLO,  rstCab.getString("co_emp"),  rstCab.getString("co_loc"),  rstCab.getString("co_tipDoc"),  rstCab.getString("co_doc"));
                    }
                    
                }
                rst.close();
                rst = null;
                stmCab.close();
                stmCab = null;

                intPosRel = rstCab.getRow();
                rstCab.last();
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
                rstCab.absolute(intPosRel);
                blnHayCam = false;
            }
        } 
        catch (SQLException Evt) {  blnRes = false;   objUti.mostrarMsgErr_F1(this, Evt);  }
        catch (Exception Evt) {  blnRes = false; objUti.mostrarMsgErr_F1(this, Evt);  }
        return blnRes;
    }
    
    
    public void clnTextos() 
    {
        //Cabecera
        txtDesPtoPar.setText("");
        txtCodDes.setText("");
        txtNomDes.setText("");
        txtDesCorPtoLle.setText("");
        txtFecDoc.setText("");
        txtDesLarPtoLle.setText("");
        txtDesCorMotTra.setText("");
        txtDesLarMotTra.setText("");
        txtCodDoc.setText("");
        txtCodTipDoc.setText("");
        txtCodPunPar.setText("");
        txtCodMotTrans.setText("");
        txtNumGuiRem.setText("");
        txtDocOri.setText("");

        strCodTipDoc = "";
        strDesCorTipDoc = "";
        strDesLarTipDoc = "";
        strCodPtoPar = "";
        strDesPtoPar = "";
        strCodCliDes = "";
        strNomCliDes = "";
        strCodPtoLle = "";
        strDesPtoLle = "";
        strCodMotTra = "";
        strDesCorMotTra = "";
        strDesLarMotTra = "";

        txtCodTipDoc.setText("");
        txtDesCorTipDoc.setText("");
        txtDesLarTipDoc.setText("");
        txtCodPtoPar.setText("");
        strCodTipDoc = "";
        strDesCorTipDoc = "";
        strDesLarTipDoc = "";

        //Detalle
        objTblMod.removeAllRows();
        vecData.clear();

        //Pie de pagina
        txtPes.setText("");
        txtNumOrdDes.setText("");
        txtObs1.setText("");
        txtObs2.setText("");

        txtFecDoc.setText("");
        txtFecIniTrans.setText("");
        txtFecTerTrans.setText("");

        lblNumOrdDes.setText("Orden Despacho: ");

    }
    
    private void MensajeInf(String strMensaje) 
    {
        //JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this, strMensaje, strTit, JOptionPane.INFORMATION_MESSAGE);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* ToolBar */">
    public class mitoolbar extends ZafToolBar 
    {

        public mitoolbar(javax.swing.JInternalFrame jfrThis) 
        {
            super(jfrThis, objParSis);
        }

        @Override
        public boolean anular() 
        {
            strAux = objTooBar.getEstadoRegistro();

            if (optOrdDes.isSelected()) {
                MensajeInf("No es posible anular Guía Principal solo se puede anular Guía Secundaria..");
                return false;
            }

            if (strAux.equals("Eliminado")) {
                MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")) {
                MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            try
            {
                abrirCon();
                if (CONN_GLO != null) {
                    CONN_GLO.setAutoCommit(false);
                    if (anularReg()) {
                        CONN_GLO.commit();
                        objTooBar.setEstadoRegistro("Anulado");
                    } else {
                        CONN_GLO.rollback();
                    }

                    CerrarCon();
                    blnHayCam = false;

                }
            } 
            catch (java.sql.SQLException Evt) {    objUti.mostrarMsgErr_F1(this, Evt);   return false;   } 
            catch (Exception Evt) {   objUti.mostrarMsgErr_F1(this, Evt);    return false;  }
            return true;
        }

        public boolean anularReg() 
        {
            boolean blnRes = false;
            java.sql.ResultSet rstLoc;
            java.sql.Statement stmLoc, stmLoc01;
            String strSql = "";
            String strCoEmpGuiPri = "";
            String strCoLocGuiPri = "";
            String strCoTipDocGuiPri = "";
            String strCoDocGuiPri = "";
            try 
            {
                if (CONN_GLO != null) 
                {
                    stmLoc = CONN_GLO.createStatement();
                    stmLoc01 = CONN_GLO.createStatement();

                    strSql = "SELECT st_coninv FROM "
                            + " tbm_cabguirem WHERE co_emp=" + strCodEmpGuiPrin + " and co_loc=" + strCodLocGuiPrin + " and co_tipdoc=" + strCodTipDocGuiPrin + " and co_doc=" + strCodDocGuiPrin;
                    rstLoc = stmLoc.executeQuery(strSql);
                    while (rstLoc.next()) {
                        strEstConf = rstLoc.getString("st_coninv");
                    }
                    rstLoc.close();
                    rstLoc = null;
                    if (strEstConf.equals("P")) 
                    {
                        strSql = "UPDATE tbm_cabguirem SET "
                               + " st_reg  = 'I' ,"
                               + " fe_ultMod  =  " + objParSis.getFuncionFechaHoraBaseDatos() + "  , "
                               + " co_usrMod   = " + objParSis.getCodigoUsuario() + " , "
                               + " tx_comultmod='" + objParSis.getNombreComputadoraConDirIP() + "' "
                               + " WHERE  co_emp = " + strCodEmpGuiPrin +""
                               + " AND co_loc = " + strCodLocGuiPrin + ""
                               + " AND co_tipdoc = " + strCodTipDocGuiPrin + ""
                               + " AND co_doc = " + strCodDocGuiPrin ;
                        stmLoc.executeUpdate(strSql);

                        strSql = "SELECT a1.co_emp, a1.co_locrel, a1.co_tipdocrel, a1.co_docrel, a1.co_regrel, abs(a.nd_can) as nd_can  FROM "
                               + " tbm_detguirem as a "
                               + " LEFT JOIN tbr_detguirem as a1 "
                               + " ON(a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc and a1.co_reg=a.co_reg) "
                               + " WHERE a.co_emp=" + strCodEmpGuiPrin + " and a.co_loc=" + strCodLocGuiPrin + " and a.co_tipdoc=" + strCodTipDocGuiPrin + " and a.co_doc=" + strCodDocGuiPrin;
                        rstLoc = stmLoc.executeQuery(strSql);
                        
                        while (rstLoc.next()) 
                        {
                            strCoEmpGuiPri = rstLoc.getString("co_emp");
                            strCoLocGuiPri = rstLoc.getString("co_locrel");
                            strCoTipDocGuiPri = rstLoc.getString("co_tipdocrel");
                            strCoDocGuiPri = rstLoc.getString("co_docrel");

                            strSql =" UPDATE tbm_detguirem SET  nd_cantotguisec=nd_cantotguisec-" + rstLoc.getString("nd_can") + "  "
                                   +" WHERE co_emp=" + rstLoc.getString("co_emp") + " and co_loc=" + rstLoc.getString("co_locrel") + " and co_tipdoc=" + rstLoc.getString("co_tipdocrel") + " and co_doc=" + rstLoc.getString("co_docrel") + " and co_reg=" + rstLoc.getString("co_regrel");
                            stmLoc01.executeUpdate(strSql);
                        }
                        rstLoc.close();
                        rstLoc = null;

                        strSql = " UPDATE tbm_cabguirem SET st_creTodGuiSec='N' "
                               + " WHERE co_emp=" + strCoEmpGuiPri + " AND co_loc=" + strCoLocGuiPri + " AND co_tipdoc=" + strCoTipDocGuiPri + " AND  co_doc=" + strCoDocGuiPri + " ";
                        stmLoc01.executeUpdate(strSql);

                        strSql = " SELECT * FROM(  SELECT sum(abs(nd_cantotguisec)) as cantot FROM tbm_detguirem "
                               + " WHERE co_emp=" + strCoEmpGuiPri + " and co_loc=" + strCoLocGuiPri + " and co_tipdoc=" + strCoTipDocGuiPri + " and co_doc=" + strCoDocGuiPri + " "
                               + " ) as x  WHERE cantot=0 ";
                        rstLoc = stmLoc.executeQuery(strSql);
                        
                        while (rstLoc.next()) 
                        {
                            strSql = " UPDATE tbm_cabguirem SET st_creTodGuiSec='N', st_tieguisec='N' "
                                   + " WHERE co_emp=" + strCoEmpGuiPri + " AND co_loc=" + strCoLocGuiPri + " AND co_tipdoc=" + strCoTipDocGuiPri + " AND  co_doc=" + strCoDocGuiPri + " ";
                            stmLoc01.executeUpdate(strSql);
                        }
                        rstLoc.close();
                        rstLoc = null;
                        blnRes = true;
                    }
                    else 
                    {
                        MensajeInf("No es posible anular porque esta confirmado anula las confirmaciones para poder anular.");
                        blnRes = false;
                    }
                    stmLoc.close();
                    stmLoc = null;
                    stmLoc01.close();
                    stmLoc01 = null;
                }
            }
            catch (java.sql.SQLException Evt) { objUti.mostrarMsgErr_F1(this, Evt);  blnRes = false; } 
            catch (Exception Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  blnRes = false; }
            return blnRes;
        }

        @Override
        public void clickAceptar() {
            setEstadoBotonMakeFac();
        }

        @Override
        public void clickAnterior() 
        {
            try {
                abrirCon();
                if (rstCab != null) {
                    if (!rstCab.isFirst()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.previous();
                                cargarReg();
                            }
                        } else {
                            rstCab.previous();
                            cargarReg();
                        }
                    }
                }
                CerrarCon();
            } 
            catch (java.sql.SQLException e) {  objUti.mostrarMsgErr_F1(this, e);  } 
            catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e); }
        }

        @Override
        public void clickAnular() {
            noEditable(false);
        }

        @Override
        public void clickConsultar() 
        {
            clnTextos();
            txtFecDoc.setText("");
            txtFecIniTrans.setText("");
            txtFecTerTrans.setText("");
            cargarTipoDoc(2);
        }

        @Override
        public void clickEliminar() {
            noEditable(false);
        }

        @Override
        public void clickFin() {
            try {
                abrirCon();
                if (rstCab != null) {
                    if (!rstCab.isLast()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.last();
                                cargarReg();
                            }
                        } else {
                            rstCab.last();
                            cargarReg();
                        }
                    }
                }
                CerrarCon();
            }
            catch (java.sql.SQLException e) {   objUti.mostrarMsgErr_F1(this, e);   } 
            catch (Exception e) {   objUti.mostrarMsgErr_F1(this, e); }
        }

        @Override
        public void clickInicio() {
            try {
                abrirCon();
                if (rstCab != null) {
                    if (!rstCab.isFirst()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.first();
                                cargarReg();
                            }
                        } else {
                            rstCab.first();
                            cargarReg();
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
        public void clickInsertar() 
        {
            try 
            {
                clnTextos();
                noEditable(false);
                cargarTipoDoc(1);

                if (rstCab != null) {
                    rstCab.close();
                    rstCab = null;
                }

                txtFecDoc.setHoy();
                txtFecIniTrans.setHoy();
                txtFecTerTrans.setHoy();
            } 
            catch (java.sql.SQLException e) {  objUti.mostrarMsgErr_F1(this, e);  } 
            catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e);  }
        }

        public void setEstadoBotonMakeFac() 
        {
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
        public void clickSiguiente() 
        {
            try {
                abrirCon();
                if (rstCab != null) {
                    if (!rstCab.isLast()) {
                        if (blnHayCam || objTblMod.isDataModelChanged()) {
                            if (isRegPro()) {
                                rstCab.next();
                                cargarReg();
                            }
                        } else {
                            rstCab.next();
                            cargarReg();
                        }
                    }
                }
                CerrarCon();
            } 
            catch (java.sql.SQLException e) {   objUti.mostrarMsgErr_F1(this, e);   } 
            catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
        }

        @Override
        public boolean eliminar() 
        {
            try
            {
                strAux = objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado")) {
                    MensajeInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                    return false;
                }

                abrirCon();

                if (!eliminarReg()) {
                    CerrarCon();
                    return false;
                }

                objTooBar.setEstadoRegistro("Eliminado");
                clnTextos();
                CerrarCon();

                blnHayCam = false;
            } 
            catch (Exception e) {   return true;  }
            return true;
        }

        public boolean eliminarReg() 
        {
            try 
            {
                try 
                {
                    if (CONN_GLO != null) {
                        CONN_GLO.setAutoCommit(false);
                        java.sql.Statement stmCot = CONN_GLO.createStatement();

                        
                        // Update para Anular Cotizacion 
                        String strSQL = "Update tbm_cabguirem set "
                                    + " st_reg  = 'E' ,"
                                    + " fe_ultMod  =  " + objParSis.getFuncionFechaHoraBaseDatos() + "  , "
                                    + " co_usrMod   = " + objParSis.getCodigoUsuario() + " , "
                                    + " tx_comultmod='" + objParSis.getNombreComputadoraConDirIP() + "' "
                                    + "  WHERE  "
                                    + " co_emp = " + objParSis.getCodigoEmpresa() + " and "
                                    + " co_loc = " + objParSis.getCodigoLocal() + " and "
                                    + " co_tipdoc = " + txtCodTipDoc.getText() + " and "
                                    + " co_doc = " + txtCodDoc.getText();
                        stmCot.executeUpdate(strSQL);

                        strSQL = "UPDATE  tbm_cabmovinv set st_creguirem='N'  FROM( "
                             + " select co_emprel, co_locrel, co_tipdocrel, co_docrel from tbm_detguirem where co_emp=" + objParSis.getCodigoEmpresa() + " "
                             + " and co_loc=" + objParSis.getCodigoLocal() + " and co_tipdoc=" + txtCodTipDoc.getText() + " "
                             + " and co_doc=" + txtCodDoc.getText() + " "
                             + " ) AS x WHERE co_emp=x.co_emprel and co_loc=x.co_locrel and co_tipdoc=x.co_tipdocrel and co_doc=x.co_docrel ";
                        stmCot.executeUpdate(strSQL);


                        strSQL = "UPDATE tbm_invbod set nd_canegrbod=nd_canegrbod+abs(x.ndcan) FROM ( "
                             + " select a1.co_emp as coemp,  a1.co_itm as coitm, a1.co_bod as cobod , a1.nd_can as ndcan "
                             + " FROM( "
                             + " select  co_emprel, co_locrel, co_tipdocrel, co_docrel from tbm_detguirem as a "
                             + " where a.co_emp=" + objParSis.getCodigoEmpresa() + " and a.co_loc=" + objParSis.getCodigoLocal() + " and a.co_tipdoc=" + txtCodTipDoc.getText() + " and a.co_doc=" + txtCodDoc.getText() + " "
                             + " group by  co_emprel, co_locrel, co_tipdocrel, co_docrel "
                             + " ) AS x"
                             + " INNER JOIN tbm_detmovinv AS a1 on(a1.co_emp=x.co_emprel and a1.co_loc=x.co_locrel and a1.co_tipdoc=x.co_tipdocrel and a1.co_doc=x.co_docrel and a1.nd_can < 0 ) "
                             + " ) as x where co_emp=x.coemp and co_itm=x.coitm and co_bod=x.cobod ";
                        stmCot.executeUpdate(strSQL);

                        CONN_GLO.commit();
                    }
                } 
                catch (java.sql.SQLException Evt) 
                {
                    CONN_GLO.rollback();
                    objUti.mostrarMsgErr_F1(this, Evt);
                    return false;
                }
            } 
            catch (Exception Evt) 
            {
                objUti.mostrarMsgErr_F1(this, Evt);
                return false;
            }
            return true;
        }

        @Override
        public boolean insertar() 
        {
            boolean blnRes = false;

            if (validarDat()) {

                INT_ENV_REC_IMP_LOCTUV = 0;
                INT_ENV_REC_IMP_LOCDIM = 0;
                INT_ENV_REC_IMP_LOCQUI = 0;
                INT_ENV_REC_IMP_LOCMAN = 0;
                INT_ENV_REC_IMP_LOCSTD = 0;

                if (insertarReg()) {
                    blnRes = true;
                    if (objParSis.getCodigoEmpresa() == 1) {
                        INT_ENV_REC_IMP_LOCTUV = 1;
                    }
                    if (objParSis.getCodigoEmpresa() == 2) {
                        if (objParSis.getCodigoLocal() == 1) {
                            INT_ENV_REC_IMP_LOCQUI = 1;
                        }
                        if (objParSis.getCodigoLocal() == 4) {
                            INT_ENV_REC_IMP_LOCMAN = 1;
                        }
                        if (objParSis.getCodigoLocal() == 6) {
                            INT_ENV_REC_IMP_LOCSTD = 1;
                        }
                    }
                    if (objParSis.getCodigoEmpresa() == 4) {
                        INT_ENV_REC_IMP_LOCDIM = 1;
                    }

                    if (INT_ENV_REC_IMP_LOCTUV == 1) {
                        enviarRequisitoImp(strIpLocTuv, INT_PUE_LOC_TUV);
                    }
                    if (INT_ENV_REC_IMP_LOCDIM == 1) {
                        enviarRequisitoImp(strIpLocDim, INT_PUE_LOC_DIM);
                    }
                    if (INT_ENV_REC_IMP_LOCQUI == 1) {
                        enviarRequisitoImp(strIpLocQui, INT_PUE_LOC_UIO);
                    }
                    if (INT_ENV_REC_IMP_LOCMAN == 1) {
                        enviarRequisitoImp(strIpLocMan, INT_PUE_LOC_MAN);
                    }
                    if (INT_ENV_REC_IMP_LOCSTD == 1) {
                        enviarRequisitoImp(strIpLocSTD, INT_PUE_LOC_STO_DGO);
                    }
                }

            }

            return blnRes;
        }

        private void enviarRequisitoImp(String strIp, int intPuerto) 
        {
            try {
                java.net.Socket s1 = new java.net.Socket(strIp, intPuerto);
                java.io.DataOutputStream dos = new java.io.DataOutputStream(s1.getOutputStream());
                dos.writeInt(1);

                dos.close();
                s1.close();

            } catch (java.net.ConnectException connExc) {
                System.err.println("OCURRIO UN ERROR 1 " + connExc);
            } catch (java.io.IOException e) {
                System.err.println("OCURRIO UN ERROR 2 " + e);
            }
        }

        private boolean validarDat() {
            boolean blnRes = true;
            double dblCanGuiSec = 0;
            if (txtCodTipDoc.getText().trim().equals("")) {
                MensajeInf("Ingrese tipo el documento");
                return false;
            }
            if (txtCodForRet.getText().trim().equals("")) {
                MensajeInf("Ingrese la forma de retiro.");
                tabGen.setSelectedIndex(1);
                return false;
            }

            for (int i = 0; i < tblDat.getRowCount(); i++) {
                if (tblDat.getValueAt(i, INT_TBL_CAN_GUI_REM) != null) {
                    dblCanGuiSec += Double.parseDouble(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CAN_GUI_REM)));
                }
            }

            if (dblCanGuiSec == 0) {
                MensajeInf("Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.");
                return false;
            }

            return blnRes;
        }

        private boolean insertarReg() 
        {
            boolean blnRes = false;
            String strSql = "";
            int intDocDoc = 0;
            int intNumDoc = 0;

            try {
                Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conn != null) {
                    conn.setAutoCommit(false);

                    java.sql.Statement stmPrede = conn.createStatement();
                    java.sql.ResultSet rstPrede;

                    strSql = "SELECT  case when (max(co_doc)+1) is null then 1 else max(co_doc)+1 end as codoc FROM "
                            + " tbm_cabguirem where co_emp=" + strCodEmpGuiPrin + " and co_loc=" + strCodLocGuiPrin + " "
                            + " and co_tipdoc=" + txtCodTipDoc.getText();
                    rstPrede = stmPrede.executeQuery(strSql);
                    if (rstPrede.next()) {
                        intDocDoc = rstPrede.getInt("codoc");
                    }
                    rstPrede.close();
                    rstPrede = null;


                    stmPrede.close();
                    stmPrede = null;


                    if (insertarRegCab(conn, intDocDoc, intNumDoc)) {
                        if (insertarRegDet(conn, intDocDoc)) {
                            conn.commit();
                            txtCodDoc.setText("" + intDocDoc);
                            blnRes = true;

                            recargarDetReg(conn, strCodEmpGuiPrin, strCodLocGuiPrin, strCodTipDocGuiPrin, strCodDocGuiPrin);
                            conn.commit();

                        } else {
                            conn.rollback();
                        }
                    } else {
                        conn.rollback();
                    }


                }
                conn.close();
                conn = null;

            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }

            return blnRes;
        }
        
        
        public boolean recargarDetReg(java.sql.Connection conn, String intCodEmp, String intCodLoc, String intCodTipDoc, String intCodDoc) 
        {
            boolean blnRes = true;
            double dlbPesGramo = 0;
            double dlbCanPen = 0;
            String strSql = "", strSQL;
            try
            {
                if (conn != null) 
                {
                    java.sql.Statement stmCab = conn.createStatement();
                   
                    if(objParSis.getCodigoMenu()==3497)
                    {
                        strSQL =" SELECT x.*,  ";    
                        strSQL+="        CASE WHEN (x.st_merEgrFisBod = 'N') THEN (nd_CanCon + nd_canNunRec) ";
                        strSQL+="        ELSE CASE WHEN (x.st_merEgrFisBod = 'A') THEN nd_Can ";
                        strSQL+="        ELSE (nd_CanCon + nd_canNunRec + nd_canTra) END END as nd_canTotCon,  "; 
                        strSQL+="        CASE WHEN (x.st_merEgrFisBod = 'N' AND nd_CanTotGui>0) THEN (nd_Can - nd_canTotGui) ";
                        strSQL+="        ELSE CASE WHEN (x.st_merEgrFisBod = 'A') THEN nd_canPen ";
                        strSQL+="        ELSE (nd_Can - (nd_CanCon + nd_canNunRec + nd_canTra)) END END as nd_canTotPen, ";
                        strSQL+="        (x.nd_pesitmkgr*nd_Can) as kgramo, (((x.nd_pesitmkgr*nd_Can)*2.2)/100) as kilo ";           
                        strSQL+=" FROM "; 
                        strSQL+=" ( "; 
                        strSQL+="     SELECT c.st_merIngEgrfisbod AS st_merEgrFisBod, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, a.ne_numdocrel, "; 
                        strSQL+="            a.co_itm, a.tx_codalt, b.tx_codalt2, a.tx_nomitm, a.tx_unimed, "; 
                        strSQL+="            CASE WHEN c.nd_Can IS NULL THEN 0 ELSE abs(c.nd_Can) END as nd_Can, "; 
                        strSQL+="            CASE WHEN a.nd_CanCon = 0 THEN abs(c.nd_CanCon) ELSE abs(a.nd_CanCon) END as nd_CanCon,   ";
                        strSQL+="            CASE WHEN c.nd_canNunRec = 0 THEN abs(a.nd_CanNunRec) ELSE abs(c.nd_canNunRec) END as nd_canNunRec, "; 
                        strSQL+="            CASE WHEN c.nd_canTra IS NULL THEN 0 ELSE abs(c.nd_canTra) END as nd_canTra, "; 
                        strSQL+="            CASE WHEN c.nd_canEgrBod IS NULL THEN 0 ELSE abs(c.nd_canEgrBod) END as nd_canEgrBod, "; 
                        strSQL+="            CASE WHEN c.nd_canDesEntCli IS NULL THEN 0 ELSE abs(c.nd_canDesEntCli) END as nd_canDesEntCli, "; 
                        strSQL+="            CASE WHEN c.nd_canPen IS NULL THEN 0 ELSE abs(c.nd_canPen) END as nd_canPen, "; 
                        strSQL+="            CASE WHEN a.nd_canTotGuiSec IS NULL THEN 0 ELSE abs(a.nd_canTotGuiSec) END as nd_canTotGui, "; 
                        strSQL+="            a.nd_pestotkgr, b.nd_pesitmkgr "; 
                        strSQL+="     FROM tbm_detguirem as a  "; 
                        strSQL+="     INNER JOIN tbm_inv as b on (b.co_emp=a.co_emp and b.co_itm=a.co_itm)  "; 
                        strSQL+="     INNER JOIN tbm_detmovinv as c on (a.co_emprel=c.co_emp and a.co_locrel=c.co_loc and a.co_tipdocrel=c.co_tipdoc and a.co_docrel=c.co_doc and a.co_regrel=c.co_reg)  "; 
                        strSQL+="     WHERE a.co_emp=" + intCodEmp;
                        strSQL+="     AND a.co_loc=" + intCodLoc;
                        strSQL+="     AND a.co_tipdoc=" + intCodTipDoc;
                        strSQL+="     AND a.co_doc=" + intCodDoc;
                        strSQL+=" ) as x "; 
                    }
                    else
                    {
                        strSQL =" SELECT x.*, (x.nd_pesitmkgr*nd_Can) as kgramo, (((x.nd_pesitmkgr*nd_Can)*2.2)/100) as kilo  ";
                        strSQL+=" FROM  ";
                        strSQL+=" (   ";
                        strSQL+="    SELECT a.st_merEgrFisBod, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, a.ne_numdocrel, ";
                        strSQL+="           a.co_itm, a.tx_codalt, b.tx_codalt2, a.tx_nomitm, a.tx_unimed,  ";
                        strSQL+="           CASE WHEN a.nd_Can IS NULL THEN 0 ELSE abs(a.nd_Can) END as nd_Can,  ";
                        strSQL+="           CASE WHEN (a.st_meregrfisbod IN  ('N', 'A') ) THEN abs(a.nd_canNunRec) ELSE abs(a.nd_CanCon) END as nd_canCon,  "; 
                        strSQL+="           CASE WHEN (a.st_meregrfisbod IN  ('N', 'A') ) THEN abs(a.nd_canNunRec) ELSE abs(a.nd_CanCon) END as nd_canTotCon,  "; 
                        strSQL+="           0 as nd_canTotPen, a.nd_pestotkgr, b.nd_pesitmkgr ";
                        strSQL+="     FROM tbm_detguirem as a  ";
                        strSQL+="     INNER JOIN tbm_inv as b on (b.co_emp=a.co_emp and b.co_itm=a.co_itm) ";
                        strSQL+="     WHERE a.co_emp=" + intCodEmp;
                        strSQL+="     AND a.co_loc=" + intCodLoc;
                        strSQL+="     AND a.co_tipdoc=" + intCodTipDoc;
                        strSQL+="     AND a.co_doc=" + intCodDoc;
                        strSQL+=" ) as x ";
                    }      
                    System.out.println("recargarDetReg: "+strSQL);
                    java.sql.ResultSet rst = stmCab.executeQuery(strSQL);

                    vecData.clear();
                    while (rst.next()) 
                    {
                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_LIN, "");
                        vecReg.add(INT_TBL_CHK_DOC, false);
                        vecReg.add(INT_TBL_COD_ITM, rst.getString("co_itm"));
                        vecReg.add(INT_TBL_COD_ALT_ITM, rst.getString("tx_codalt"));
                        vecReg.add(INT_TBL_NOM_ITM, rst.getString("tx_nomitm"));
                        vecReg.add(INT_TBL_DES_COR_UNI_MED, rst.getString("tx_unimed"));
                        vecReg.add(INT_TBL_CAN_ITM, rst.getString("nd_can"));
                        vecReg.add(INT_TBL_NUM_DOC, rst.getString("ne_numdocrel"));
                        vecReg.add(INT_TBL_COD_EMP, rst.getString("co_emp"));
                        vecReg.add(INT_TBL_COD_LOC, rst.getString("co_loc"));
                        vecReg.add(INT_TBL_COD_TIP_DOC, rst.getString("co_tipdoc"));
                        vecReg.add(INT_TBL_COD_DOC, rst.getString("co_doc"));
                        vecReg.add(INT_TBL_COD_REG, rst.getString("co_reg"));
                        vecReg.add(INT_TBL_MAR_ELI, "");
                        vecReg.add(INT_TBL_PES_KGR, rst.getString("nd_pesitmkgr"));
                        vecReg.add(INT_TBL_CAN_TOT_CON, rst.getString("nd_canTotCon"));
                        vecReg.add(INT_TBL_CAN_PEN_ITM, rst.getString("nd_canTotPen"));
                        vecReg.add(INT_TBL_CAN_GUI_REM, "");
                        vecReg.add(INT_TBL_MER_EGR_FIS, rst.getString("st_merEgrFisBod"));
                        dlbCanPen += rst.getDouble("nd_canPen");

                        dlbPesGramo += rst.getDouble("kgramo");
                        vecData.add(vecReg);
                    }
                    objTblMod.setData(vecData);
                    tblDat.setModel(objTblMod);
                    rst.close();
                    rst = null;

                    //System.out.println("" + dlbCanPen);

                    if (dlbCanPen == 0) {
                        strSql = "UPDATE tbm_cabguirem SET st_creTodGuiSec='S' WHERE co_emp=" + intCodEmp + " AND co_loc=" + intCodLoc + " "
                                + " AND co_tipdoc=" + intCodTipDoc + " AND  co_doc=" + intCodDoc + " ";
                        //System.out.println("" + strSql);
                        stmCab.executeUpdate(strSql);
                    }

                    if ((objParSis.getCodigoMenu() == 1793) || (objParSis.getCodigoMenu() == 3497)) {
                        txtPes.setText("" + objUti.redondear(dlbPesGramo, 2));
                    }

                    stmCab.close();
                    stmCab = null;

                    objTblMod.setDataModelChanged(false);
                    blnHayCam = false;

                }
            } catch (java.sql.SQLException Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }        
        

        private boolean insertarRegCab(java.sql.Connection conIns, int intCodDoc, int intNumDoc) 
        {
            boolean blnRes = false;
            String strSql = "";

            try 
            {
                java.sql.Statement stmPrede = conIns.createStatement();

                String strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";
                String strFecIniTrans = "#" + txtFecIniTrans.getFecha("/", "y/m/d") + "#";
                String strFecTerTrans = "#" + txtFecTerTrans.getFecha("/", "y/m/d") + "#";

                strSql = " INSERT INTO tbm_cabguirem (co_emp, co_loc, co_tipdoc, co_doc, fe_doc, fe_initra, fe_tertra, "
                        + " ne_numdoc, tx_ptopar, co_clides, tx_rucclides, tx_nomclides, tx_dirclides, tx_telclides,  "
                        + " tx_ciuclides, "
                        + //" co_mottra, tx_desmottra, " +
                        " nd_pestotkgr, st_imp, tx_obs1, tx_obs2, st_reg, fe_ing, "
                        + " fe_ultmod, co_usring, co_usrmod, tx_coming, tx_comultmod, co_ptopar, st_tipGuiRem, tx_datdocoriguirem "
                        + " ,co_forRet,tx_vehRet,tx_choRet, co_ven, tx_nomven, tx_numped, co_ptodes  ) "
                        + " VALUES (" + strCodEmpGuiPrin + ", " + strCodLocGuiPrin + ", " + txtCodTipDoc.getText() + ", "
                        + " " + intCodDoc + ", '" + strFecDoc + "', '" + strFecIniTrans + "', '" + strFecTerTrans + "', "
                        + " " + intNumDoc + ", '" + txtDesPtoPar.getText() + "', " + txtCodDes.getText() + ", '" + txtRucCli.getText() + "', '" + txtNomDes.getText() + "', "
                        + " '" + txtDesLarPtoLle.getText() + "', '" + txtTelCli.getText() + "', '" + txtCiuCli.getText() + "', "
                        + // " "+txtCodMotTrans.getText()+", '"+txtDesLarMotTrans.getText()+"', " +
                        " " + (txtPes.getText().equals("") ? "0" : txtPes.getText()) + ", 'N', '" + txtObs1.getText() + "', '" + txtDocOri.getText() + "','A', " + objParSis.getFuncionFechaHoraBaseDatos() + ", "
                        + " " + objParSis.getFuncionFechaHoraBaseDatos() + ", " + objParSis.getCodigoUsuario() + ", " + objParSis.getCodigoUsuario() + ",  "
                        + " '" + objParSis.getNombreComputadoraConDirIP() + "', '" + objParSis.getNombreComputadoraConDirIP() + "', " + txtCodPunPar.getText() + ", 'S', '" + strDocOriGuiRem + "' "
                        + " ," + txtCodForRet.getText() + ", '" + txtVehRet.getText() + "', '" + txtChoRet.getText() + "', " + strCodVenGuiPrin + " ,'" + strNomVenGuiPrin + "', '" + txtNumPed.getText() + "' ," + (strPtoDes == null ? null : (strPtoDes.equals("") ? null : "" + strPtoDes + "")) + "  )";

                strSql += "; INSERT INTO tbr_cabguirem (co_emp, co_loc, co_tipdoc, co_doc, co_locrel, co_tipdocrel, co_docrel, st_regrep)"
                        + " VALUES( " + strCodEmpGuiPrin + ", " + strCodLocGuiPrin + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + ", " + strCodLocGuiPrin + ""
                        + " ," + strCodTipDocGuiPrin + ", " + strCodDocGuiPrin + ", 'I' ) ";

                strSql += "; UPDATE tbm_cabguirem SET st_tieGuiSec='S' WHERE co_emp=" + strCodEmpGuiPrin + " AND co_loc=" + strCodLocGuiPrin + " "
                        + " AND co_tipdoc=" + strCodTipDocGuiPrin + " AND  co_doc=" + strCodDocGuiPrin + " ";

                //System.out.println(""+ strSql );

                stmPrede.executeUpdate(strSql);
                stmPrede.close();
                stmPrede = null;
                blnRes = true;
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }

            return blnRes;
        }

        private boolean insertarRegDet(java.sql.Connection conIns, int intCodDoc) 
        {
            boolean blnRes = false;
            String strSql = "";

            try 
            {
                java.sql.Statement stmPrede = conIns.createStatement();

                for (int i = 0; i < tblDat.getRowCount(); i++) {
                    if (tblDat.getValueAt(i, INT_TBL_COD_LOC) != null) {
                        if (Double.parseDouble(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CAN_GUI_REM))) > 0) {


                            strSql = " INSERT INTO tbm_detguirem(co_emp, co_loc, co_tipdoc, co_doc, co_reg,  "
                                    + "  co_itm, tx_codalt,  "
                                    + "  tx_nomitm, tx_unimed, nd_can, st_regrep, nd_pestotkgr, tx_obs1, st_meregrfisbod ) "
                                    + " VALUES (" + strCodEmpGuiPrin + ", " + strCodLocGuiPrin + ", " + txtCodTipDoc.getText() + ", "
                                    + " " + intCodDoc + ", " + (i + 1) + ", "
                                    + " " + tblDat.getValueAt(i, INT_TBL_COD_ITM).toString() + ", '" + tblDat.getValueAt(i, INT_TBL_COD_ALT_ITM).toString() + "', "
                                    + " '" + tblDat.getValueAt(i, INT_TBL_NOM_ITM).toString() + "' ,'" + tblDat.getValueAt(i, INT_TBL_DES_COR_UNI_MED).toString() + "', "
                                    + " " + tblDat.getValueAt(i, INT_TBL_CAN_GUI_REM).toString() + " ,'I' , " + (tblDat.getValueAt(i, INT_TBL_PES_KGR) == null ? "0" : tblDat.getValueAt(i, INT_TBL_PES_KGR).toString()) + ",'' "
                                    + " ,'" + objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_MER_EGR_FIS)) + "'  )";

                            strSql += "; INSERT INTO tbr_detguirem (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel, co_docrel, co_regrel, st_regrep)"
                                    + " VALUES( " + strCodEmpGuiPrin + ", " + strCodLocGuiPrin + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + ", " + (i + 1) + ", " + strCodLocGuiPrin + ""
                                    + " ," + strCodTipDocGuiPrin + ", " + strCodDocGuiPrin + ", " + objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_COD_REG)) + ", 'I' "
                                    + "  ) ";

                            strSql += "; UPDATE tbm_detguirem SET "
                                    + " nd_cantotguisec=nd_cantotguisec+" + objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CAN_GUI_REM)) + " "
                                    + " WHERE co_emp=" + strCodEmpGuiPrin + " AND co_loc=" + strCodLocGuiPrin + "  AND  co_tipdoc=" + strCodTipDocGuiPrin + ""
                                    + "  AND  co_doc=" + strCodDocGuiPrin + "  AND  co_reg=" + objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_COD_REG)) + " ";

                            stmPrede.executeUpdate(strSql);

                            //strSql=" UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod-abs(x.ndcan) FROM ( " +
                            //        " SELECT co_emp as coemp,  co_itm as coitm, co_bod as cobod , nd_can as ndcan  " +
                            //        " FROM tbm_detmovinv " +
                            //        " WHERE  co_emp="+tblDat.getValueAt(i, INT_TBL_COD_EMP).toString()+" " +
                            //        " AND co_loc="+tblDat.getValueAt(i, INT_TBL_COD_LOC).toString()+" AND co_tipdoc="+tblDat.getValueAt(i, INT_TBL_COD_TIP_DOC).toString()+" " +
                            //        " AND co_doc="+tblDat.getValueAt(i, INT_TBL_COD_DOC).toString()+" AND nd_can < 0 "+
                            //        " ) AS x WHERE co_emp=x.coemp AND co_itm=x.coitm AND co_bod=x.cobod ";
                            //stmPrede.executeUpdate(strSql);

                        }
                    }
                }

                stmPrede.close();
                stmPrede = null;
                blnRes = true;
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }

            return blnRes;
        }

        @Override
        public boolean modificar() {
            boolean blnRes = false;

            strAux = objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                MensajeInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }

            if (strAux.equals("Anulado")) {
                MensajeInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }

            abrirCon();

            if (validarDat()) {
                if (modificarReg()) {
                    blnRes = true;
                }
            }

            CerrarCon();

            objTblMod.setDataModelChanged(false);


            blnHayCam = false;
            return blnRes;
        }

        private boolean modificarReg() 
        {
            boolean blnRes = false;

            try 
            {
                java.sql.Connection conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conn != null) {
                    conn.setAutoCommit(false);

                    if (modifcarCab(conn, txtCodDoc.getText())) {
                        if (modificarDet(conn, txtCodDoc.getText())) {
                            conn.commit();
                            blnRes = true;
                        } else {
                            conn.rollback();
                        }
                    } else {
                        conn.rollback();
                    }
                }
                conn.close();
                conn = null;

            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }

            return blnRes;
        }

        private boolean modificarDet(java.sql.Connection conMod, String strCodDoc)
        {
            boolean blnRes = false;
            String strSql = "";

            try {

                java.sql.Statement stmPrede = conMod.createStatement();

                strSql = "UPDATE  tbm_cabmovinv set st_creguirem='N'  FROM( "
                        + " select co_emprel, co_locrel, co_tipdocrel, co_docrel from tbm_detguirem where co_emp=" + objParSis.getCodigoEmpresa() + " "
                        + " and co_loc=" + objParSis.getCodigoLocal() + " and co_tipdoc=" + txtCodTipDoc.getText() + " "
                        + " and co_doc=" + strCodDoc + " "
                        + " ) AS x WHERE co_emp=x.co_emprel and co_loc=x.co_locrel and co_tipdoc=x.co_tipdocrel and co_doc=x.co_docrel ";
                stmPrede.executeUpdate(strSql);


                strSql = "UPDATE tbm_invbod set nd_canegrbod=nd_canegrbod+abs(x.ndcan) FROM ( "
                        + " select a1.co_emp as coemp,  a1.co_itm as coitm, a1.co_bod as cobod , a1.nd_can as ndcan "
                        + " FROM( "
                        + " select  co_emprel, co_locrel, co_tipdocrel, co_docrel from tbm_detguirem as a "
                        + " where a.co_emp=" + objParSis.getCodigoEmpresa() + " and a.co_loc=" + objParSis.getCodigoLocal() + " and a.co_tipdoc=" + txtCodTipDoc.getText() + " and a.co_doc=" + strCodDoc + " "
                        + " group by  co_emprel, co_locrel, co_tipdocrel, co_docrel "
                        + " ) AS x"
                        + " INNER JOIN tbm_detmovinv AS a1 on(a1.co_emp=x.co_emprel and a1.co_loc=x.co_locrel and a1.co_tipdoc=x.co_tipdocrel and a1.co_doc=x.co_docrel and a1.nd_can < 0 ) "
                        + " ) as x where co_emp=x.coemp and co_itm=x.coitm and co_bod=x.cobod ";
                stmPrede.executeUpdate(strSql);


                strSql = " DELETE FROM tbm_detguirem WHERE  co_emp=" + objParSis.getCodigoEmpresa() + " "
                        + " and co_loc=" + objParSis.getCodigoLocal() + " and co_tipdoc=" + txtCodTipDoc.getText() + " "
                        + " and co_doc=" + strCodDoc + " ";
                // System.out.println(""+ strSql );     
                stmPrede.executeUpdate(strSql);


                for (int i = 0; i < tblDat.getRowCount(); i++) {
                    if (tblDat.getValueAt(i, INT_TBL_COD_LOC) != null) {

                        strSql = " INSERT INTO tbm_detguirem(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel, "
                                + "  co_tipdocrel, co_docrel, co_regrel, ne_numdocrel, co_itm, tx_codalt,  "
                                + "  tx_nomitm, tx_unimed, nd_can, st_regrep, nd_pestotkgr  ) "
                                + " VALUES (" + objParSis.getCodigoEmpresa() + ", " + objParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", "
                                + " " + strCodDoc + ", " + (i + 1) + ", " + tblDat.getValueAt(i, INT_TBL_COD_EMP).toString() + ", " + tblDat.getValueAt(i, INT_TBL_COD_LOC).toString() + ", "
                                + " " + tblDat.getValueAt(i, INT_TBL_COD_TIP_DOC).toString() + ", " + tblDat.getValueAt(i, INT_TBL_COD_DOC).toString() + ", " + tblDat.getValueAt(i, INT_TBL_COD_REG).toString() + ", " + tblDat.getValueAt(i, INT_TBL_NUM_DOC).toString() + ", "
                                + " " + tblDat.getValueAt(i, INT_TBL_COD_ITM).toString() + ", '" + tblDat.getValueAt(i, INT_TBL_COD_ALT_ITM).toString() + "', "
                                + " '" + tblDat.getValueAt(i, INT_TBL_NOM_ITM).toString() + "' ,'" + tblDat.getValueAt(i, INT_TBL_DES_COR_UNI_MED).toString() + "', "
                                + " " + tblDat.getValueAt(i, INT_TBL_CAN_ITM).toString() + ",'M', " + (tblDat.getValueAt(i, INT_TBL_PES_KGR) == null ? "0" : tblDat.getValueAt(i, INT_TBL_PES_KGR).toString()) + " )";
                        // System.out.println(""+ strSql );        
                        stmPrede.executeUpdate(strSql);

                        strSql = " UPDATE  tbm_cabmovinv set st_creguirem='S' WHERE  co_emp=" + tblDat.getValueAt(i, INT_TBL_COD_EMP).toString() + " "
                                + " and co_loc=" + tblDat.getValueAt(i, INT_TBL_COD_LOC).toString() + " and co_tipdoc=" + tblDat.getValueAt(i, INT_TBL_COD_TIP_DOC).toString() + " "
                                + " and co_doc=" + tblDat.getValueAt(i, INT_TBL_COD_DOC).toString() + " ";
                        stmPrede.executeUpdate(strSql);

                        strSql = " UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod-abs(x.ndcan) FROM ( "
                                + " SELECT co_emp as coemp,  co_itm as coitm, co_bod as cobod , nd_can as ndcan  "
                                + " FROM tbm_detmovinv "
                                + " WHERE  co_emp=" + tblDat.getValueAt(i, INT_TBL_COD_EMP).toString() + " "
                                + " AND co_loc=" + tblDat.getValueAt(i, INT_TBL_COD_LOC).toString() + " AND co_tipdoc=" + tblDat.getValueAt(i, INT_TBL_COD_TIP_DOC).toString() + " "
                                + " AND co_doc=" + tblDat.getValueAt(i, INT_TBL_COD_DOC).toString() + " AND nd_can < 0 "
                                + " ) AS x WHERE co_emp=x.coemp AND co_itm=x.coitm AND co_bod=x.cobod ";
                        stmPrede.executeUpdate(strSql);

                    }
                }

                stmPrede.close();
                stmPrede = null;
                blnRes = true;
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }

            return blnRes;
        }

        private boolean modifcarCab(java.sql.Connection conMod, String strCodDoc) {
            boolean blnRes = false;
            String strSql = "";
            try {
                java.sql.Statement stmPrede = conMod.createStatement();

                String strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";
                String strFecIniTrans = "#" + txtFecIniTrans.getFecha("/", "y/m/d") + "#";
                String strFecTerTrans = "#" + txtFecTerTrans.getFecha("/", "y/m/d") + "#";

                strSql = " UPDATE tbm_cabguirem SET   fe_doc='" + strFecDoc + "', fe_initra='" + strFecIniTrans + "', fe_tertra='" + strFecTerTrans + "', "
                        + " ne_numdoc=" + txtNumOrdDes.getText() + " , "
                        + " co_ptopar=" + txtCodPunPar.getText() + ", "
                        + "  tx_ptopar='" + txtDesPtoPar.getText() + "', co_clides=" + txtCodDes.getText() + ", tx_rucclides='" + txtRucCli.getText() + "',"
                        + " tx_nomclides='" + txtNomDes.getText() + "', tx_dirclides='" + txtDesLarPtoLle.getText() + "', tx_telclides='" + txtTelCli.getText() + "', "
                        + " tx_ciuclides='" + txtCiuCli.getText() + "', co_mottra=" + txtCodMotTrans.getText() + ", tx_desmottra='" + txtDesLarMotTra.getText() + "', "
                        + " nd_pestotkgr=" + (txtPes.getText().equals("") ? "0" : txtPes.getText()) + ", tx_obs1='" + txtObs1.getText() + "', tx_obs2='" + txtObs2.getText() + "', "
                        + " fe_ultmod=" + objParSis.getFuncionFechaHoraBaseDatos() + ", co_usrmod=" + objParSis.getCodigoUsuario() + ", "
                        + " tx_comultmod='" + objParSis.getNombreComputadoraConDirIP() + "' WHERE co_emp=" + objParSis.getCodigoEmpresa() + " and "
                        + " co_loc = " + objParSis.getCodigoLocal() + " and co_tipdoc = " + txtCodTipDoc.getText() + " and co_doc=" + strCodDoc;

                //System.out.println(""+ strSql );
                stmPrede.executeUpdate(strSql);


                strSql = "UPDATE tbm_cabtipdoc SET ne_ultdoc=" + txtNumOrdDes.getText() + " WHERE co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_tipdoc=" + txtCodTipDoc.getText();
                stmPrede.executeUpdate(strSql);

                stmPrede.close();
                stmPrede = null;
                blnRes = true;
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }

            return blnRes;
        }

        public void noEditable(boolean blnEditable) {
            java.awt.Color colBack = txtDesCorTipDoc.getBackground();
            txtDesCorTipDoc.setEditable(blnEditable);
            txtDesLarTipDoc.setEditable(blnEditable);

            txtCodPtoPar.setEditable(blnEditable);
            txtDesPtoPar.setEditable(blnEditable);
            txtCodDes.setEditable(blnEditable);
            txtNomDes.setEditable(blnEditable);
            txtDesCorPtoLle.setEditable(blnEditable);
            txtDesLarPtoLle.setEditable(blnEditable);
            txtDesCorMotTra.setEditable(blnEditable);
            txtDesLarMotTra.setEditable(blnEditable);
            txtDocOri.setEditable(blnEditable);

            txtDesForRet.setEditable(blnEditable);
            txtCodForRet.setEditable(blnEditable);

            txtCodDoc.setEditable(blnEditable);
            txtPes.setEditable(blnEditable);
            txtDesCorPtoLle.setEditable(blnEditable);
            txtDesLarPtoLle.setEditable(blnEditable);

            txtNumOrdDes.setEditable(blnEditable);
            // txtSub.setBackground(colBack);
        }

        /**
         * Carga Tipo Documento para el Programa Especificado
         *
         * @param intVal , 1=Insertar; 2=Consultar.
         */
        public void cargarTipoDoc(int intVal) 
        {
            try 
            {
                Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                Statement stmPrede = conn.createStatement();
                ResultSet rstPrede;

                String sqlPrede = "";

                if (objParSis.getCodigoUsuario() == 1) //Admin
                {
                    sqlPrede = "Select  a.co_tipdoc,a.tx_deslar,a.tx_descor "
                            + " ,case when (a.ne_ultdoc+1) is null then 1 else a.ne_ultdoc+1 end as numDoc  "
                            + " from tbr_tipdocprg as a1 "
                            + " inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"
                            + " where   a1.co_emp = " + objParSis.getCodigoEmpresa() + " and "
                            + " a1.co_loc = " + objParSis.getCodigoLocal() + " and "
                            + " a1.co_mnu = " + objParSis.getCodigoMenu() + " and "
                            + "  a1.st_reg = 'S'";
                }
                else //Otros Usuarios
                {
                    sqlPrede = "Select  a.co_tipdoc,a.tx_deslar,a.tx_descor "
                            + " ,case when (a.ne_ultdoc+1) is null then 1 else a.ne_ultdoc+1 end as numDoc  "
                            + " from tbr_tipDocUsr as a1 "
                            + " inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"
                            + " where   a1.co_emp = " + objParSis.getCodigoEmpresa() + " and "
                            + " a1.co_loc = " + objParSis.getCodigoLocal() + " and "
                            + " a1.co_mnu = " + objParSis.getCodigoMenu() + "  "
                            + " AND a1.co_usr=" + objParSis.getCodigoUsuario() + " and a1.st_reg = 'S'";
                }
                rstPrede = stmPrede.executeQuery(sqlPrede);

                if (rstPrede.next()) 
                {
                    if (intVal == 1) 
                    {
                        txtCodTipDoc.setText(((rstPrede.getString("co_tipdoc") == null) ? "" : rstPrede.getString("co_tipdoc")));
                        txtDesCorTipDoc.setText(((rstPrede.getString("tx_descor") == null) ? "" : rstPrede.getString("tx_descor")));
                        txtDesLarTipDoc.setText(((rstPrede.getString("tx_deslar") == null) ? "" : rstPrede.getString("tx_deslar")));
                        strCodTipDoc = rstPrede.getString("co_tipdoc");
                        strDesCorTipDoc = rstPrede.getString("tx_descor");
                        strDesLarTipDoc = rstPrede.getString("tx_deslar");
                    }
                    //  if(intVal==1)  txtNumDoc.setText(((rstPrede.getString("numDoc")==null)?"":rstPrede.getString("numDoc")));
                }

                stmPrede.close();
                rstPrede.close();
                conn.close();
                stmPrede = null;
                rstPrede = null;
                conn = null;
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }

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
            butTieGuiRem.setEnabled(true);
            butTieDev.setEnabled(true);
            return true;
        }

        @Override
        public boolean afterEliminar() {
            return true;
        }

        @Override
        public boolean afterImprimir() 
        {
            if (objParSis.getCodigoUsuario() == 1)
            {
                cambiarEstadoImpresion();
            }
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
        private boolean isRegPro() 
        {
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
                            blnRes = objTooBar.modificar();
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

        /**
         * Esto Hace en caso de que el modo de operacion sea Consulta.
         */
        @Override
        public boolean consultar() {
            return _consultar(FilSql());
        }


        @Override
        public void clickModificar() {
            setEditable(true);
            noEditable(false);
            //txtNumDoc.setEditable(false);
            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
            blnHayCam = false;
        }

        //******************************************************************************************************
        @Override
        public boolean vistaPreliminar() {
            cargarReporte(1);
            return true;
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

        @Override
        public boolean imprimir() {
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
    //</editor-fold>

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
     */
    private boolean generarRpt(int intTipRpt) 
    {
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes = true;
        String strSQL, strRutAbsRpt, strNomPrn = "";
        String strSQLRep = "", strSQLSubRep = "" ;
        Connection conn;
        Statement stm;
        ResultSet rst;

        try 
        {
            boolean cargarListadoReportes = objRptSis.cargarListadoReportes(conCab);
            objRptSis.setVisible(true);
            Map mapPar = new HashMap();
            if (objRptSis.getOpcionSeleccionada() == ZafRptSis.INT_OPC_ACE) {
                //Obtener la fecha y hora del servidor.
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                strFecHorSer = objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                datFecAux = null;
                intNumTotRpt = objRptSis.getNumeroTotalReportes();
                for (i = 0; i < intNumTotRpt; i++) {
                    if (objRptSis.isReporteSeleccionado(i)) {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i))) 
                        {
                            case 439://Orden de despacho(importaciones)
                                strSQLRep = "";
                                strSQLRep += "SELECT d1.co_emp, d1.co_loc, d1.co_tipdoc, d1.co_doc, d1.feimpguia, d1.fe_ing, d1.obs2,";
                                strSQLRep += " d1.tx_numped, d1.tx_nom, d1.tx_descor, d1.fe_ing, d1.tx_datdocoriguirem, d1.tx_ptopar, ";
                                strSQLRep += " d1.empresa, d1.tx_telclides, d1.tx_rucclides, d1.ne_numorddes, d1.fe_doc, d1.tx_nomclides, ";
                                strSQLRep += " d1.co_clides, d1.tx_nomven, d1.tx_dirclides, d1.tx_vehret, d1.tx_choret, d1.tx_deslar,";
                                strSQLRep += " d1.cuidad, d1.contri1, d1.empresa, d1.nd_kgr, d1.nd_kil,";
                                strSQLRep += " d1.co_empIngImp, d1.co_locIngImp, d1.co_tipDocIngImp, d1.co_docIngImp, d1.tx_numIngImp";
                                strSQLRep += " FROM ( SELECT";
                                strSQLRep += "        b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc,";
                                strSQLRep += "        CURRENT_TIMESTAMP as feimpguia, b1.fe_ing, trim(b1.tx_obs2) as obs2,";
                                strSQLRep += "        b1.tx_numped, b8.tx_nom, b9.tx_descor, b1.tx_datdocoriguirem,";
                                strSQLRep += "        b1.tx_ptopar, b1.tx_telclides, b1.tx_rucclides,";
                                strSQLRep += "        b1.ne_numorddes, b1.fe_doc, b1.tx_nomclides, b1.co_clides, b1.tx_nomven,";
                                strSQLRep += "        b1.tx_dirclides, b1.tx_vehret, b1.tx_choret, b4.tx_deslar,";
                                strSQLRep += "        b1.tx_ciuclides as cuidad,";
                                strSQLRep += "        b8.tx_desconesp as contri1, b8.tx_nom  as empresa,";
                                strSQLRep += "        (	select sum( (inv.nd_pesitmkgr*abs(x.nd_can)) ) as kgramo";
                                strSQLRep += " 		from tbm_detguirem as x inner join tbm_inv as inv";
                                strSQLRep += " 		on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                                strSQLRep += " 		WHERE x.co_emp=b1.co_emp and x.co_loc=b1.co_loc  and x.co_tipdoc=b1.co_tipdoc and x.co_doc=b1.co_doc";
                                strSQLRep += " 	) as nd_kgr,";
                                strSQLRep += "        (";
                                strSQLRep += " 		select sum( (((inv.nd_pesitmkgr*abs(x.nd_can))*2.2)/100)  ) as kilo";
                                strSQLRep += " 		from tbm_detguirem as x inner join tbm_inv as inv";
                                strSQLRep += " 		on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                                strSQLRep += " 		WHERE x.co_emp=b1.co_emp and x.co_loc=b1.co_loc  and x.co_tipdoc=b1.co_tipdoc and x.co_doc=b1.co_doc";
                                strSQLRep += " 	) as nd_kil";
                                strSQLRep += " 	, c3.co_emp AS co_empIngImp, c3.co_loc AS co_locIngImp, c3.co_tipDoc AS co_tipDocIngImp, c3.co_doc AS co_docIngImp, c3.tx_numdoc2 AS tx_numIngImp";
                                strSQLRep += "        FROM (tbm_cabguirem as b1 INNER JOIN tbm_detGuiRem AS b2 ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                                strSQLRep += " 			INNER JOIN tbm_detMovInv AS b3 ON b2.co_empRel=b3.co_emp AND b2.co_locRel=b3.co_loc AND b2.co_tipDocRel=b3.co_tipDoc AND b2.co_docRel=b3.co_doc AND b2.co_regRel=b3.co_reg";
                                strSQLRep += " 			INNER JOIN tbm_cabMovInv AS c1 ON b3.co_Emp=c1.co_emp AND b3.co_loc=c1.co_loc AND b3.co_tipDoc=c1.co_tipDoc AND b3.co_doc=c1.co_doc";
                                strSQLRep += " 			INNER JOIN tbr_cabMovInv AS c2 ON c1.co_emp=c2.co_emp AND c1.co_loc=c2.co_loc AND c1.co_tipDoc=c2.co_tipDoc AND c1.co_doc=c2.co_doc";
                                strSQLRep += " 			INNER JOIN tbm_cabMovInv AS c3 ON c2.co_empRel=c3.co_emp AND c2.co_locRel=c3.co_loc AND c2.co_tipDocRel=c3.co_tipDoc AND c2.co_docRel=c3.co_doc AND c3.co_tipDoc IN (14,234)";
                                strSQLRep += " 	    )";
                                strSQLRep += "        LEFT OUTER JOIN tbm_var as b4 ON(b1.co_forret = b4.co_reg )";
                                strSQLRep += "        inner JOIN tbm_loc as b6 ON( b1.co_emp = b6.co_emp and  b1.co_loc=b6.co_loc)";
                                strSQLRep += "        inner JOIN tbm_ciu as b7 ON( b6.co_ciu = b7.co_ciu)";
                                strSQLRep += "        inner JOIN tbm_emp as b8 ON( b8.co_emp = b1.co_emp)";
                                strSQLRep += "        inner JOIN tbm_cabtipdoc as b9 ON( b9.co_emp = b1.co_emp and b9.co_loc=b1.co_loc and b9.co_tipdoc=b1.co_tipdoc)";
                                strSQLRep += "        WHERE b1.co_emp=" + new Integer(strCodEmpGuiPrin) + "";
                                strSQLRep += "        and b1.co_loc=" + new Integer(strCodLocGuiPrin) + "";
                                strSQLRep += "        and b1.co_tipdoc=" + new Integer(strCodTipDocGuiPrin) + "";
                                strSQLRep += "        and b1.co_doc=" + new Integer(strCodDocGuiPrin) + "";
                                strSQLRep += "     ) as d1";
                                strSQLRep += " GROUP BY d1.co_emp, d1.co_loc, d1.co_tipdoc, d1.co_doc,";
                                strSQLRep += " d1.feimpguia, d1.fe_ing, d1.obs2,";
                                strSQLRep += " d1.tx_numped, d1.tx_nom, d1.tx_descor, d1.fe_ing, d1.tx_datdocoriguirem,";
                                strSQLRep += " d1.tx_ptopar, d1.empresa, d1.tx_telclides, d1.tx_rucclides,";
                                strSQLRep += " d1.ne_numorddes, d1.fe_doc, d1.tx_nomclides, d1.co_clides, d1.tx_nomven,";
                                strSQLRep += " d1.tx_dirclides, d1.tx_vehret, d1.tx_choret, d1.tx_deslar, d1.cuidad,";
                                strSQLRep += " d1.contri1, d1.empresa, d1.nd_kgr, d1.nd_kil, d1.co_empIngImp, d1.co_locIngImp, d1.co_tipDocIngImp, d1.co_docIngImp, d1.tx_numIngImp";
                                //System.out.println("strSQLRep: " + strSQLRep);

                                strSQLSubRep = "";
                                strSQLSubRep += "select *";
                                strSQLSubRep += " from ( select";
                                strSQLSubRep += "        b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc, b2.co_reg,";
                                strSQLSubRep += "        current_timestamp as feimpguia, b1.fe_ing, b2.tx_obs1, trim(b1.tx_obs2) as obs2,";
                                strSQLSubRep += "        b1.tx_numped, b8.tx_nom, b9.tx_descor, b1.fe_ing, b1.tx_datdocoriguirem,";
                                strSQLSubRep += "        b1.tx_ptopar, b8.tx_nom  as empresa, b1.tx_telclides, b1.tx_rucclides,";
                                strSQLSubRep += "        b1.ne_numorddes, b1.fe_doc, b1.tx_nomclides, b1.co_clides, b1.tx_nomven,";
                                strSQLSubRep += "        b1.tx_dirclides, b1.tx_vehret, b1.tx_choret, b4.tx_deslar, b2.tx_codalt, b5.tx_codalt2,";
                                strSQLSubRep += "        b2.tx_nomitm, abs(b2.nd_can) AS nd_can, b2.tx_unimed, b1.tx_ciuclides as cuidad,";
                                strSQLSubRep += "        b8.tx_desconesp as contri1, b8.tx_nom  as empresa,";
                                strSQLSubRep += "        (	select sum( (inv.nd_pesitmkgr*abs(x.nd_can)) ) as kgramo";
                                strSQLSubRep += " 		from tbm_detguirem as x inner join tbm_inv as inv";
                                strSQLSubRep += " 		on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                                strSQLSubRep += " 		WHERE x.co_emp=b1.co_emp and x.co_loc=b1.co_loc  and x.co_tipdoc=b1.co_tipdoc and x.co_doc=b1.co_doc";
                                strSQLSubRep += " 	) as nd_kgr,";
                                strSQLSubRep += "        (";
                                strSQLSubRep += " 		select sum( (((inv.nd_pesitmkgr*abs(x.nd_can))*2.2)/100)  ) as kilo";
                                strSQLSubRep += " 		from tbm_detguirem as x inner join tbm_inv as inv";
                                strSQLSubRep += " 		on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                                strSQLSubRep += " 		WHERE x.co_emp=b1.co_emp and x.co_loc=b1.co_loc  and x.co_tipdoc=b1.co_tipdoc and x.co_doc=b1.co_doc";
                                strSQLSubRep += " 	) as nd_kil";
                                strSQLSubRep += "        FROM tbm_cabguirem as b1 INNER JOIN tbm_detguirem as b2";
                                strSQLSubRep += "        ON(b1.co_emp=b2.co_emp and b1.co_loc=b2.co_loc and b1.co_tipdoc=b2.co_tipdoc and b1.co_doc=b2.co_doc)";
                                strSQLSubRep += "        inner join tbm_detmovinv as b3";
                                strSQLSubRep += "        on (b3.co_emp=b2.co_emprel and b3.co_loc=b2.co_locrel and b3.co_tipdoc=b2.co_tipdocrel and b3.co_doc=b2.co_docrel and b3.co_reg=b2.co_regrel )";
                                strSQLSubRep += "        LEFT OUTER JOIN tbm_var as b4 ON(b1.co_forret = b4.co_reg )";
                                strSQLSubRep += "        inner join tbm_inv as b5 on(b2.co_emp=b5.co_emp and b2.co_itm=b5.co_itm)";
                                strSQLSubRep += "        inner JOIN tbm_loc as b6 ON( b1.co_emp = b6.co_emp and  b1.co_loc=b6.co_loc)";
                                strSQLSubRep += "        inner JOIN tbm_ciu as b7 ON( b6.co_ciu = b7.co_ciu)";
                                strSQLSubRep += "        inner JOIN tbm_emp as b8 ON( b8.co_emp = b1.co_emp)";
                                strSQLSubRep += "        inner JOIN tbm_cabtipdoc as b9 ON( b9.co_emp = b1.co_emp and b9.co_loc=b1.co_loc and b9.co_tipdoc=b1.co_tipdoc)";
                                strSQLSubRep += "        WHERE b1.co_emp=" + new Integer(strCodEmpGuiPrin) + "";
                                strSQLSubRep += "        and b1.co_loc=" + new Integer(strCodLocGuiPrin) + "";
                                strSQLSubRep += "        and b1.co_tipdoc=" + new Integer(strCodTipDocGuiPrin) + "";
                                strSQLSubRep += "        and b1.co_doc=" + new Integer(strCodDocGuiPrin) + "";
                                strSQLSubRep += "     ) as x";
                                strSQLSubRep += " order by co_reg";
                                //System.out.println("strSQLSubRep Importaciones: " + strSQLSubRep);

                                strRutRpt = objRptSis.getRutaReporte(i);
                                //System.out.println("RUTA REPORTE: " + strRutRpt);
                                strNomRpt = objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.
                                mapPar.put("co_emp", new Integer(strCodEmpGuiPrin));
                                mapPar.put("co_loc", new Integer(strCodLocGuiPrin));
                                mapPar.put("co_tipdoc", new Integer(strCodTipDocGuiPrin));
                                mapPar.put("co_doc", new Integer(strCodDocGuiPrin));
                                mapPar.put("nomUsrImp", objParSis.getNombreUsuario()); // Jose Mario Marin 07/Jul/2015
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                mapPar.put("strSQLRep", strSQLRep);
                                mapPar.put("strSQLSubRep", strSQLSubRep);

                                // mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;

                            case 440://Guia de remision(importaciones)
                                strSQLRep = "";
                                strSQLRep += "SELECT b1.fe_initra, b1.fe_tertra, b1.NumSri, b1.obs2, b1.tx_numped, b1.tx_nom, b1.tx_descor,";
                                strSQLRep += "         b1.fe_ing,b1.tx_datdocoriguirem,b1.co_ptopar,b1.tx_ptopar, b1.puntopartida, b1.tx_telclides,";
                                strSQLRep += " 	b1.tx_rucclides,b1.ne_numdoc,b1.fe_doc,b1.tx_nomclides,b1.co_clides,b1.tx_nomven,b1.tx_dirclides,";
                                strSQLRep += " 	b1.tx_vehret,b1.tx_choret,b1.tx_deslar,	b1.cuidad, b1.contri1, b1.empresa, b1.kgramo, b1.kilo";
                                strSQLRep += " FROM(";
                                strSQLRep += " 	SELECT CASE WHEN inv.nd_pesitmkgr IS NULL THEN '(P0)' ELSE CASE  WHEN inv.nd_pesitmkgr <= 0 THEN '(P0)' else '' END END AS tiepes,";
                                strSQLRep += " 	DocCab.fe_initra, DocCab.fe_tertra, DocCab.fe_ing,";
                                strSQLRep += " 	(";
                                strSQLRep += " 		SELECT ( x5.tx_descor || '     ' || x4.tx_numautsri ) AS NumSri FROM tbm_cabguirem AS x";
                                strSQLRep += " 		INNER JOIN tbr_cabguirem as x1 ON (x1.co_emp=x.co_emp AND x1.co_loc=x.co_loc AND x1.co_tipdoc=x.co_tipdoc AND x1.co_doc=x.co_doc )";
                                strSQLRep += " 		INNER JOIN tbm_detguirem as x2 ON (x2.co_emp=x1.co_emp AND x2.co_loc=x1.co_locrel AND x2.co_tipdoc=x1.co_tipdocrel AND x2.co_doc=x1.co_docrel )";
                                strSQLRep += " 		INNER JOIN tbm_cabmovinv as x3 ON (x3.co_emp=x2.co_emprel AND x3.co_loc=x2.co_locrel AND x3.co_tipdoc=x2.co_tipdocrel AND x3.co_doc=x2.co_docrel )";
                                strSQLRep += " 		INNER JOIN tbm_datautsri as x4 ON (x4.co_emp=x3.co_emp AND x4.co_loc=x3.co_loc AND x4.co_tipdoc=x3.co_tipdoc )";
                                strSQLRep += " 		INNER JOIN tbm_cabtipdoc as x5 ON (x5.co_emp=x4.co_emp AND x5.co_loc=x4.co_loc AND x5.co_tipdoc=x4.co_tipdoc )";
                                strSQLRep += " 		where x.co_emp=DocCab.co_emp and x.co_loc=DocCab.co_loc AND x.co_tipdoc=DocCab.co_tipdoc AND x.co_doc= DocCab.co_doc";
                                strSQLRep += " 		AND x3.ne_numdoc BETWEEN x4.ne_numdocdes AND x4.ne_numdochas";
                                strSQLRep += " 		GROUP BY x5.tx_descor, x4.tx_numautsri";
                                strSQLRep += "	) as NumSri,";
                                strSQLRep += " 	TRIM(DocCab.tx_obs2) as obs2,";
                                strSQLRep += " 	CASE WHEN DocCab.tx_numped IS NULL THEN '' else DocCab.tx_numped END AS tx_numped,";
                                strSQLRep += " 	emp.tx_nom, tipdoc.tx_descor,DocCab.tx_datdocoriguirem,DocCab.co_ptopar,DocCab.tx_ptopar,";
                                strSQLRep += "	DocCab.co_ptopar AS puntopartida,DocCab.tx_telclides,";
                                strSQLRep += " 	DocCab.tx_rucclides,DocCab.ne_numdoc,DocCab.fe_doc,DocCab.tx_nomclides,DocCab.co_clides,DocCab.tx_nomven,DocCab.tx_dirclides,";
                                strSQLRep += " 	DocCab.tx_vehret,DocCab.tx_choret,a3.tx_deslar,";
                                strSQLRep += " 	DocCab.tx_ciuclides as cuidad,";
                                strSQLRep += " 	emp.tx_desconesp as contri1,";
                                strSQLRep += " 	emp.tx_nom  AS empresa,";
                                strSQLRep += " 	(	SELECT sum( (inv.nd_pesitmkgr*abs(x.nd_can)) ) AS kgramo FROM tbm_detguirem AS x";
                                strSQLRep += " 		INNER JOIN tbm_inv AS inv ON(x.co_emp=inv.co_emp AND x.co_itm=inv.co_itm)";
                                strSQLRep += " 		WHERE x.co_emp=DocCab.co_emp AND x.co_loc=DocCab.co_loc  AND x.co_tipdoc=DocCab.co_tipdoc AND x.co_doc=DocCab.co_doc";
                                strSQLRep += " 	) AS kgramo,";
                                strSQLRep += " 	(	SELECT sum( (((inv.nd_pesitmkgr*abs(x.nd_can))*2.2)/100)  ) AS kilo FROM tbm_detguirem AS x";
                                strSQLRep += " 		INNER JOIN tbm_inv AS inv ON(x.co_emp=inv.co_emp AND x.co_itm=inv.co_itm)";
                                strSQLRep += " 		WHERE x.co_emp=DocCab.co_emp AND x.co_loc=DocCab.co_loc  AND x.co_tipdoc=DocCab.co_tipdoc AND x.co_doc=DocCab.co_doc";
                                strSQLRep += " 	) AS kilo";
                                strSQLRep += " 	FROM tbm_cabguirem as DocCab";
                                strSQLRep += " 	INNER JOIN tbm_detguirem AS a2 ON (DocCab.co_emp=a2.co_emp AND DocCab.co_loc=a2.co_loc AND DocCab.co_tipdoc=a2.co_tipdoc AND DocCab.co_doc=a2.co_doc)";
                                strSQLRep += " 	LEFT OUTER JOIN tbm_var AS a3 ON (DocCab.co_forret = a3.co_reg )";
                                strSQLRep += " 	INNER JOIN tbm_inv AS inv on(a2.co_emp=inv.co_emp AND a2.co_itm=inv.co_itm)";
                                strSQLRep += " 	INNER JOIN tbm_loc AS loc ON ( doccab.co_emp = loc.co_emp AND  doccab.co_loc=loc.co_loc)";
                                strSQLRep += " 	INNER JOIN tbm_ciu AS ciu ON ( loc.co_ciu = ciu.co_ciu)";
                                strSQLRep += " 	INNER JOIN tbm_emp AS emp ON ( emp.co_emp = DocCab.co_emp)";
                                strSQLRep += " 	INNER JOIN tbm_cabtipdoc as tipdoc ON ( tipdoc.co_emp = DocCab.co_emp AND tipdoc.co_loc=DocCab.co_loc AND tipdoc.co_tipdoc=DocCab.co_tipdoc)";
                                strSQLRep += " 	WHERE DocCab.co_emp=" + new Integer(strCodEmpGuiPrin) + "";
                                strSQLRep += " 	AND DocCab.co_loc=" + new Integer(strCodLocGuiPrin) + "";
                                strSQLRep += " 	AND DocCab.co_tipdoc=" + new Integer(strCodTipDocGuiPrin) + "";
                                strSQLRep += " 	AND DocCab.co_doc=" + new Integer(strCodDocGuiPrin) + "";
                                strSQLRep += " 	ORDER BY a2.co_reg";
                                strSQLRep += " ) AS b1";
                                strSQLRep += " GROUP BY b1.fe_initra, b1.fe_tertra, b1.NumSri, b1.obs2,b1.tx_numped,b1.tx_nom,";
                                strSQLRep += " b1.tx_descor,b1.fe_ing,b1.tx_datdocoriguirem,b1.co_ptopar,b1.tx_ptopar,b1.puntopartida,";
                                strSQLRep += " b1.tx_telclides,b1.tx_rucclides,b1.ne_numdoc,b1.fe_doc,b1.tx_nomclides,b1.co_clides,b1.tx_nomven,";
                                strSQLRep += " b1.tx_dirclides,b1.tx_vehret,b1.tx_choret,b1.tx_deslar,b1.cuidad,b1.contri1,b1.empresa,b1.kgramo,b1.kilo";
                                //System.out.println("strSQLRep: " + strSQLRep);

                                strSQLSubRep = "";
                                strSQLSubRep += "select case when inv.nd_pesitmkgr is null then '(P0)' else case  when inv.nd_pesitmkgr <= 0 then '(P0)' else '' end end as tiepes,";
                                strSQLSubRep += " a2.tx_obs1,a2.tx_codalt, abs(a2.nd_can) as nd_can,a2.tx_unimed,";
                                strSQLSubRep += " CASE WHEN trim(a2.tx_nomitm)=trim(inv.tx_nomitm) THEN a2.tx_nomitm ELSE a2.tx_nomitm || ' (**) ' END as tx_nomitm";
                                strSQLSubRep += " FROM tbm_cabguirem as DocCab";
                                strSQLSubRep += " INNER JOIN tbm_detguirem as a2 ON (DocCab.co_emp=a2.co_emp and DocCab.co_loc=a2.co_loc and DocCab.co_tipdoc=a2.co_tipdoc and DocCab.co_doc=a2.co_doc)";
                                strSQLSubRep += " LEFT OUTER JOIN tbm_var as a3 ON (DocCab.co_forret = a3.co_reg )";
                                strSQLSubRep += " inner join tbm_inv as inv on(a2.co_emp=inv.co_emp and a2.co_itm=inv.co_itm)";
                                strSQLSubRep += " inner JOIN tbm_loc as loc ON ( doccab.co_emp = loc.co_emp and  doccab.co_loc=loc.co_loc)";
                                strSQLSubRep += " inner JOIN tbm_ciu as ciu ON ( loc.co_ciu = ciu.co_ciu)";
                                strSQLSubRep += " inner JOIN tbm_emp as emp ON ( emp.co_emp = DocCab.co_emp)";
                                strSQLSubRep += " inner JOIN tbm_cabtipdoc as tipdoc ON ( tipdoc.co_emp = DocCab.co_emp and tipdoc.co_loc=DocCab.co_loc and tipdoc.co_tipdoc=DocCab.co_tipdoc)";
                                strSQLSubRep += " WHERE DocCab.co_emp=" + new Integer(strCodEmpGuiPrin) + "";
                                strSQLSubRep += " and DocCab.co_loc=" + new Integer(strCodLocGuiPrin) + "";
                                strSQLSubRep += " and DocCab.co_tipdoc=" + new Integer(strCodTipDocGuiPrin) + "";
                                strSQLSubRep += " and DocCab.co_doc=" + new Integer(strCodDocGuiPrin) + "";
                                strSQLSubRep += " order by a2.co_reg";
                                //System.out.println("strSQLSubRep: " + strSQLSubRep);
                                strRutRpt = objRptSis.getRutaReporte(i);
                                strNomRpt = objRptSis.getNombreReporte(i);

                                //Inicializar los parametros que se van a pasar al reporte.
                                mapPar.put("co_emp", new Integer(strCodEmpGuiPrin));
                                mapPar.put("co_loc", new Integer(strCodLocGuiPrin));
                                mapPar.put("co_tipdoc", new Integer(strCodTipDocGuiPrin));
                                mapPar.put("co_doc", new Integer(strCodDocGuiPrin));
                                mapPar.put("nomUsrImp", objParSis.getNombreUsuario());
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                mapPar.put("strSQLRep", strSQLRep);
                                mapPar.put("strSQLSubRep", strSQLSubRep);

                                // mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;

                            default:
                                strRutRpt = objRptSis.getRutaReporte(i);
                                strNomRpt = objRptSis.getNombreReporte(i);
                                //System.out.println("RUTA REPORTE: " + strRutRpt);
                                //Inicializar los parametros que se van a pasar al reporte.
                                if (objParSis.getCodigoMenu() == 1793) //1793: Guia de remisión
                                {
                                    if (System.getProperty("os.name").equals("Linux")) {
                                        strRutAbsRpt = "/Zafiro";
                                    } else {
                                        conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                                        strRutAbsRpt = "";

                                        if (conn != null) {
                                            stm = conn.createStatement();
                                            strSQL = "select tx_rutAbsRpt from tbm_rptsis where st_reg = 'A' and co_rpt = 471"; //471: Guias de remision (Electronica)
                                            rst = stm.executeQuery(strSQL);

                                            if (rst.next()) {
                                                strRutAbsRpt = rst.getString("tx_rutAbsRpt");
                                            }

                                            rst.close();
                                            rst = null;
                                            stm.close();
                                            stm = null;
                                        }
                                    }
                                    //Query del reporte
                                    strSQLRep = "";
                                    strSQLRep += "select DISTINCT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                                    strSQLRep += ", a1.tx_numautfacele, a1.fe_autfacele, a1.tx_claaccfacele";
                                    strSQLRep += " , a1.fe_initra, a1.fe_tertra, a1.fe_ing, trim(a1.tx_obs2) as obs2,";
                                    strSQLRep += " case when a1.tx_numped is null then '' else a1.tx_numped end as tx_numped,";
                                    strSQLRep += " emp.tx_nom, tipdoc.tx_descor, a1.fe_ing, a1.st_imp as st_impGuia, a1.tx_datdocoriguirem,a1.co_ptopar, a1.tx_ptopar,";
                                    strSQLRep += " case when a1.co_ptopar =15 then 'INMACONSA' else '' end as puntopartida,";
                                    strSQLRep += " emp.tx_nom  as empresa, a1.tx_telclides, a1.tx_rucclides,";
                                    strSQLRep += " (substring(a1.tx_numserdocori,1,3) || '-' || substring(a1.tx_numserdocori,5,3) || '-' || to_char(a1.ne_numdoc, '000000000')) as tx_numdoc,";
                                    strSQLRep += " a1.ne_numdoc, a1.fe_doc, a1.tx_nomclides, a1.co_clides,";
                                    /*AGREGADO POR LA DIRECCION DE LAS GUIAS CASO DURAN*/
                                    //strSQLRep += " a1.tx_nomven, a1.tx_dirclides, a1.tx_vehret, a1.tx_choret, a3.tx_deslar,";
                                    /*AGREGADO POR LA DIRECCION DE LAS GUIAS CASO DURAN*/
                                    strSQLRep += " a1.tx_nomven, a1.tx_vehret, a1.tx_choret, a3.tx_deslar,";
                                    strSQLRep += " a1.tx_ciuclides as cuidad, emp.tx_desconesp as contri1, emp.tx_nom  as empresa,";
                                    strSQLRep += " (select sum( (inv.nd_pesitmkgr*abs(x.nd_can)) ) as kgramo from tbm_detguirem as x";
                                    strSQLRep += " inner join tbm_inv as inv on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                                    strSQLRep += " WHERE x.co_emp=a1.co_emp and x.co_loc=a1.co_loc  and x.co_tipdoc=a1.co_tipdoc and x.co_doc=a1.co_doc ) as kgramo,";
                                    strSQLRep += " (select sum( (((inv.nd_pesitmkgr*abs(x.nd_can))*2.2)/100)  ) as kilo from tbm_detguirem as x";
                                    strSQLRep += " inner join tbm_inv as inv on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                                    strSQLRep += " WHERE x.co_emp=a1.co_emp and x.co_loc=a1.co_loc  and x.co_tipdoc=a1.co_tipdoc and x.co_doc=a1.co_doc ) as kilo,";
                                    strSQLRep += " CASE WHEN veh.tx_pla is null THEN a1.tx_idetra ELSE tra.tx_ide END AS tx_idetra,";
                                    strSQLRep += " CASE WHEN veh.tx_pla is null THEN a1.tx_nomtra ELSE tra.tx_nom || ' ' || tra.tx_ape END AS tx_nomtra,";
                                    strSQLRep += " CASE WHEN veh.tx_pla is null THEN a1.tx_plavehtra ELSE veh.tx_pla END AS tx_plaveh, usr.tx_usr, ";
                                    
                                    /*SE AGREGA PARA EL PROYECTO DE TRANSFERENCIAS*/
                                    strSQLRep+=" CASE WHEN detord.co_tipdocrel IN (1,228,124,238,127,242,125,126,166,225,206,153,58,96,97,98) THEN ";
                                    strSQLRep+="      'VENTA' ";
                                    strSQLRep+=" ELSE ";
                                    strSQLRep+="      CASE WHEN detord.co_tipDocRel IN (4,46,172,204,234) THEN ";
                                    strSQLRep+="           'REPOSICIÓN' ";
                                    strSQLRep+="       END ";
                                    //strSQLRep+=" END as documento, a1.tx_obs1 ,case when boddes.tx_dir is null then a1.tx_dirclides else boddes.tx_dir end as tx_dirclides  ";
                                    strSQLRep+=" END as documento, a1.tx_obs1 ";
                                    
                                    /*AGREGADO POR LA DIRECCION DE LAS GUIAS CASO DURAN*/
                                    strSQLRep+=", case when boddes.tx_dir is null then ";
                                    strSQLRep+=" a1.tx_dirclides ";
                                    strSQLRep+=" else ";
                                    //strSQLRep+="   case when boddes.tx_dir ='Av.113 y Calle Oliva Miranda C.C. Puerto Plaza' then 'Manta: '||boddes.tx_dir ";// Manta
                                    strSQLRep+="   case when bodgrp.co_bodgrp=5 then 'Manta: '||boddes.tx_dir ";// Manta
                                    strSQLRep+="      else case when bodgrp.co_bodgrp=3 then 'Quito: '||boddes.tx_dir "; //Quito
                                    strSQLRep+="                else case when bodgrp.co_bodgrp=28 then 'Cuenca: '||boddes.tx_dir "; // Cuenca
                                    strSQLRep+="                    else case when bodgrp.co_bodgrp=11 then 'Santo Domingo: '||boddes.tx_dir ";//Santo domingo*
                                    strSQLRep+="                           else boddes.tx_dir";
                                    strSQLRep+=" end end end end ";
                                    strSQLRep+=" end as tx_dirclides "; 
                                    /*AGREGADO POR LA DIRECCION DE LAS GUIAS CASO DURAN*/
									
                                    /*CAMBIO DIRECCION POFASA : AGREGAR NUMDOC DE FAC Y NUM DE AUTORIZACION SRI FAC*/
                                    strSQLRep+=" , locser.tx_secdoc|| '-' ||to_char(fac.ne_numdoc, 'fm000000000') as numdocfac, fac.ne_numdoc, fac.tx_claaccfacele as claaccfac"; 
                                    /*CAMBIO DIRECCION POFASA : AGREGAR NUMDOC DE FAC Y NUM DE AUTORIZACION SRI FAC*/
									
                                    
                                    /*SE AGREGA PARA EL PROYECTO DE TRANSFERENCIAS*/        
                                                                        
                                    strSQLRep += " FROM tbm_cabguirem as a1";
                                    strSQLRep += " LEFT OUTER JOIN tbm_var as a3 ON (a1.co_forret = a3.co_reg )";
                                    strSQLRep += " inner JOIN tbm_loc as loc ON ( a1.co_emp = loc.co_emp and  a1.co_loc=loc.co_loc)";
                                    strSQLRep += " inner JOIN tbm_ciu as ciu ON ( loc.co_ciu = ciu.co_ciu)";
                                    strSQLRep += " inner JOIN tbm_emp as emp ON ( emp.co_emp = a1.co_emp)";
                                    strSQLRep += " inner JOIN tbm_cabtipdoc as tipdoc ON ( tipdoc.co_emp = a1.co_emp and tipdoc.co_loc=a1.co_loc and tipdoc.co_tipdoc=a1.co_tipdoc)";
                                    strSQLRep += " LEFT JOIN tbm_veh as veh on (veh.co_veh = a1.co_veh)";
                                    strSQLRep += " LEFT JOIN tbm_tra as tra on (tra.co_tra = a1.co_cho)";
                                    /*SE AGREGA PARA MOSTRAR EL USUARIO QUE CONFIRMA*/
                                    strSQLRep+=" LEFT JOIN tbm_usr as usr on (usr.co_usr = a1.co_usring)";
                                    /*SE AGREGA PARA MOSTRAR EL USUARIO QUE CONFIRMA*/
                                    

                                    
                                    /*SE AGREGA PARA EL PROYECTO DE TRANSFERENCIAS*/
                                    strSQLRep+=" INNER JOIN tbr_cabguirem as rel ";
                                    strSQLRep+=" on (rel.co_emp= a1.co_emp and rel.co_loc=a1.co_loc and rel.co_tipdoc=a1.co_tipdoc and rel.co_doc=a1.co_doc) ";
                                    strSQLRep+=" INNER JOIN tbm_cabguirem as orden ";
                                    strSQLRep+=" on (orden.co_emp=rel.co_emp and orden.co_loc=rel.co_locrel and orden.co_tipdoc=rel.co_tipdocrel and orden.co_doc=rel.co_docrel) ";

                                    
                                    /*AGREGADO POR LA DIRECCION DE LAS GUIAS CASO DURAN*/
                                    strSQLRep+=" left outer join tbm_bod as boddes ";
                                    strSQLRep+=" on (boddes.co_emp=orden.co_emp and boddes.co_bod=orden.co_ptodes) ";                    
                                    
                                    strSQLRep+=" left outer join tbr_bodempbodgrp as bodgrp ";
                                    strSQLRep+=" on (bodgrp.co_emp=boddes.co_emp and bodgrp.co_bod=boddes.co_bod and bodgrp.co_empgrp=0) ";                    
                                    
                                    /*AGREGADO POR LA DIRECCION DE LAS GUIAS CASO DURAN*/
                                    
                                    
                                    strSQLRep+=" INNER JOIN tbm_detguirem as detord ";
                                    strSQLRep+=" on (detord.co_emp=orden.co_emp and detord.co_loc=orden.co_loc and detord.co_tipdoc=orden.co_tipdoc and detord.co_doc=orden.co_doc) ";
									
                                    /*CAMBIO DIRECCION POFASA : AGREGAR NUMDOC DE FAC Y NUM DE AUTORIZACION SRI FAC*/
                                    strSQLRep+=" LEFT OUTER JOIN tbm_cabmovinv as fac ";
                                    strSQLRep+=" on (fac.co_emp=detord.co_emprel and fac.co_loc=detord.co_locrel and fac.co_tipdoc=detord.co_tipdocrel and fac.co_doc=detord.co_docrel and fac.co_tipdoc=228) ";
                                    strSQLRep+=" LEFT OUTER JOIN tbm_loc as locser ";
                                    strSQLRep+=" on locser.co_emp=fac.co_emp and locser.co_loc=fac.co_loc ";
                                    /*CAMBIO DIRECCION POFASA : AGREGAR NUMDOC DE FAC Y NUM DE AUTORIZACION SRI FAC*/                                    

									
                                    /*SE AGREGA PARA EL PROYECTO DE TRANSFERENCIAS*/

                                    
                                    
                                    
                                    
                                    strSQLRep += " WHERE a1.co_emp=" + strCodEmpGuiPrin + "";
                                    strSQLRep += " and a1.co_loc=" + strCodLocGuiPrin + "";
                                    strSQLRep += " and a1.co_tipdoc=" + strCodTipDocGuiPrin + "";
                                    strSQLRep += " and a1.co_doc=" + strCodDocGuiPrin + "";
                                    //System.out.println("strSQLRepGR:"+strSQLRep);
                                    
                                    //Query del subReporte
                                    strSQLSubRep = "";
                                    strSQLSubRep += "select case when inv.nd_pesitmkgr is null then '(P0)' else case  when inv.nd_pesitmkgr <= 0 then '(P0)' else '' end end as tiepes,";
                                    strSQLSubRep += " a2.tx_obs1, emp.tx_nom, a2.tx_codalt, inv.tx_codalt2, ";
                                    strSQLSubRep += " CASE WHEN trim(a2.tx_nomitm)=trim(inv.tx_nomitm) THEN a2.tx_nomitm ELSE a2.tx_nomitm || ' (**) ' END as tx_nomitm,";
                                    strSQLSubRep += " abs(a2.nd_can) as can,a2.tx_unimed";
                                    strSQLSubRep += " FROM tbm_cabguirem as DocCab";
                                    strSQLSubRep += " INNER JOIN tbm_detguirem as a2";
                                    strSQLSubRep += " ON (DocCab.co_emp=a2.co_emp and DocCab.co_loc=a2.co_loc and DocCab.co_tipdoc=a2.co_tipdoc and DocCab.co_doc=a2.co_doc)";
                                    strSQLSubRep += " inner join tbm_inv as inv on(a2.co_emp=inv.co_emp and a2.co_itm=inv.co_itm)";
                                    strSQLSubRep += " inner JOIN tbm_emp as emp ON ( emp.co_emp = a2.co_emp)";
                                    strSQLSubRep += " WHERE DocCab.co_emp=" + strCodEmpGuiPrin + "";
                                    strSQLSubRep += " and DocCab.co_loc=" + strCodLocGuiPrin + "";
                                    strSQLSubRep += " and DocCab.co_tipdoc=" + strCodTipDocGuiPrin + "";
                                    strSQLSubRep += " and DocCab.co_doc=" + strCodDocGuiPrin + "";
                                    strSQLSubRep += " order by a2.co_reg";
                                    //System.out.println("strSQLSubRepGRRR:"+strSQLSubRep);
                                    
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRep", strSQLSubRep);
                                    mapPar.put("SUBREPORT_DIR", strRutAbsRpt);
                                    //mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("imgCodEmp", strCodEmpGuiPrin);
                                    mapPar.put("imgRut", strRutAbsRpt);
                                }
                                else //Ordenes de Despacho
                                {
                                    mapPar.put("co_emp", new Integer(strCodEmpGuiPrin));
                                    mapPar.put("co_loc", new Integer(strCodLocGuiPrin));
                                    mapPar.put("co_tipdoc", new Integer(strCodTipDocGuiPrin));
                                    mapPar.put("co_doc", new Integer(strCodDocGuiPrin));
                                    mapPar.put("nomUsrImp", objParSis.getNombreUsuario() + "  -  " + strFecHorSer);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRep", strSQLSubRep);
                                }
                                if ((objParSis.getCodigoUsuario() == 1) || (objParSis.getCodigoMenu() == 1793) || (intTipRpt == 2)) 
                                {
                                    // mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                }
                                else
                                {
                                    if (objParSis.getCodigoMenu() == 3497) {
                                        try {
                                            conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                                            if (conn != null) {
                                                if (strCodEmpGuiPrin.equalsIgnoreCase("1")) {
                                                    switch (Integer.parseInt(strCodLocGuiPrin)) {
                                                        case 4:
                                                            strNomPrn = "od_califormia";
                                                            break;
                                                        case 10:
                                                            strNomPrn = "od_inmaconsa";
                                                            break;
                                                    }
                                                } else if (strCodEmpGuiPrin.equalsIgnoreCase("2")) {
                                                    switch (Integer.parseInt(strCodLocGuiPrin)) {
                                                        case 1:
                                                            strNomPrn = "od_quito";
                                                            break;
                                                        case 4:
                                                            strNomPrn = "od_manta";
                                                            break;
                                                        case 5:
                                                            strNomPrn = "od_inmaconsa";
                                                            break;
                                                        case 6:
                                                            strNomPrn = "od_stodgo";
                                                            break;
                                                        case 10:
                                                            strNomPrn = "od_cuenca";
                                                            break;
                                                    }
                                                } else if (strCodEmpGuiPrin.equalsIgnoreCase("4")) {
                                                    switch (Integer.parseInt(strCodLocGuiPrin)) {
                                                        case 3:
                                                            strNomPrn = "od_dimulti";
                                                            break;
                                                        case 10:
                                                            strNomPrn = "od_inmaconsa";
                                                            break;
                                                    }
                                                }
                                                javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet = new javax.print.attribute.HashPrintRequestAttributeSet();
                                                objPriReqAttSet.add(new MediaPrintableArea(0f, 0f, 8.5f, 11f, Size2DSyntax.INCH));//sirve para el area de impresion, si NO se lo coloca la impresion de las Guias sale despues de 3 cm hacia abajo aprox. no presentando la informacion de arriba del reporte.
                                                objPriReqAttSet.add(OrientationRequested.PORTRAIT);
                                                JasperPrint reportGuiaRem = JasperFillManager.fillReport(strRutRpt + strNomRpt, mapPar, conn);
                                                javax.print.attribute.standard.PrinterName printerName = new javax.print.attribute.standard.PrinterName(strNomPrn, null);
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
                                                conn.close();
                                                conn = null;
                                           
                                            }
                                        } 
                                        catch (JRException ej) {    blnRes = false;  objUti.mostrarMsgErr_F1(this, ej);   } 
                                        catch (SQLException ex) {  blnRes = false; objUti.mostrarMsgErr_F1(this, ex);    }
                                    }
                                }
                                break;
                        }
                    }
                }
            }
        } 
        catch (Exception e) {   blnRes = false;   objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }

    
    public void consultaDevolucion(java.sql.Connection conn, String strCodEmpOD, String strCodLocOD, String strCodTipDocOD, String strCodDocOD) 
    {
        try 
        {
            Statement stmDev = conn.createStatement();
            ResultSet rstDev;

            strDev =" SELECT a.fe_Doc, a.co_emp, a.co_loc, a.co_tipDoc, a4.tx_DesCor, a4.tx_desLar, a.co_doc, a.ne_numDoc "; 
            strDev+=" FROM tbm_cabSolDevVen as a ";
            strDev+=" INNER JOIN tbm_cabMovInv as a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_locRel AND a1.co_tipDoc=a.co_tipDocRel AND a1.co_doc=a.co_docRel) ";
            strDev+=" INNER JOIN tbm_detGuiRem as a2 ON (a2.co_empRel=a1.co_emp AND a2.co_locRel=a1.co_loc AND a2.co_tipDocRel=a1.co_tipDoc AND a2.co_docRel=a1.co_doc) ";
            strDev+=" INNER JOIN tbm_cabGuiRem as a3 ON (a3.co_emp=a2.co_emp AND a3.co_loc=a2.co_loc AND a3.co_tipDoc=a2.co_tipDoc AND a3.co_doc=a2.co_doc) ";
            strDev+=" INNER JOIN tbm_CabTipDoc as a4 ON (a4.co_emp=a.co_emp AND a4.co_loc=a.co_loc AND a4.co_tipDoc=a.co_TipDoc  ) ";
            strDev+=" WHERE a3.st_Reg='A' AND a3.co_emp=" + rstCab.getString("co_emp") + " AND a3.co_loc=" + rstCab.getString("co_loc") ;
            strDev+=" AND a3.co_tipdoc=" + rstCab.getString("co_tipdoc") + " AND a3.co_doc=" + rstCab.getString("co_doc");
            strDev+=" GROUP BY a.fe_Doc, a.co_emp, a.co_loc, a.co_tipDoc, a4.tx_DesCor, a4.tx_desLar, a.co_doc, a.ne_numDoc ";

            rstDev = stmDev.executeQuery(strDev);

            if (rstDev.next()) 
            {
                chkTieDev.setSelected(true);
                strCodEmpSolDev =  rstDev.getString("co_emp"); 
                strCodLocSolDev =  rstDev.getString("co_loc"); 
                strCodTipDocSolDev =  rstDev.getString("co_tipDoc"); 
                strCodDocSolDev =  rstDev.getString("co_doc"); 
            }
            else
            {
                chkTieDev.setSelected(false);
            }

            rstDev.close();
            rstDev = null;
            stmDev.close();
            stmDev = null;

        } 
        catch (java.sql.SQLException e) {       objUti.mostrarMsgErr_F1(this, e);            } 
        catch (Exception Evt) {             objUti.mostrarMsgErr_F1(this, Evt);            }

    }
    

    
}