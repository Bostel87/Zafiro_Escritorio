/*
 * ZafCon06.java
 *
 *  Created on 02 de noviembre de 2005, 11:25 PM
 */
package Contabilidad.ZafCon09;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafAsiDia.ZafAsiDia;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafRptSis.ZafRptSis;

import java.math.BigDecimal;
import java.math.BigInteger;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCon09 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable.
    final int INT_TBL_DAT_LIN=0;                        //Lánea.
    final int INT_TBL_DAT_CHK=1;                        //Casilla de verificacián.
    final int INT_TBL_DAT_COD_LOC=2;                    //Cádigo del local.
    final int INT_TBL_DAT_COD_TIP_DOC=3;                //Cádigo del tipo de documento.
    final int INT_TBL_DAT_DEC_TIP_DOC=4;                //Descripcián corta del tipo de documento.
    final int INT_TBL_DAT_DEL_TIP_DOC=5;                //Descripcián larga del tipo de documento.
    final int INT_TBL_DAT_COD_DOC=6;                    //Cádigo del documento (Sistema).
    final int INT_TBL_DAT_COD_REG=7;                    //Cádigo del registro (Sistema).
    final int INT_TBL_DAT_NUM_DOC=8;                    //Námero del documento.
    final int INT_TBL_DAT_FEC_DOC=9;                    //Fecha del documento.
    final int INT_TBL_DAT_DIA_CRE=10;                   //Dáas de crádito.
    final int INT_TBL_DAT_FEC_VEN=11;                   //Fecha de vencimiento.
    final int INT_TBL_DAT_VAL_DOC=12;                   //Valor del documento.
    final int INT_TBL_DAT_VAL_PEN=13;                   //Valor pendiente.
    final int INT_TBL_DAT_AUX_ABO=14;                   //Auxiliar:Abono del documento.
    final int INT_TBL_DAT_AUX_NAT_DOC=15;               //Auxiliar:Naturaleza del documento.
    final int INT_TBL_DAT_VAL_APL=16;                   //Valor que se puede aplicar al haber restado la FACCOM de DEVCOM(valor a pagar)
    final int INT_TBL_DAT_VAL_APL_USR=17;               //Valor que  el usuario aplica
    //Variables generales.
    private ZafDatePicker dtpFecDoc, dtpFecVen;
    private String strFecDocIni;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
//    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;            //Editor: JTextField en celda.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMená en JTable.
    private ZafVenCon vcoTipDoc;                        //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoCta;                           //Ventana de consulta "Cuenta".
    private ZafVenCon vcoPrv;                           //Ventana de consulta "Proveedor".
    private ZafVenCon vcoBen;                           //Ventana de consulta "Beneficiario".
    private MiToolBar objTooBar;
    private ZafAsiDia objAsiDia;
    private ZafAutPrg objAutPrg;
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL, strAux, strSQLCon;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                             //true: Continua la ejecucián del hilo.
    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    private ZafDocLis objDocLis;
    private String strDesCorTipDoc, strDesLarTipDoc;    //Contenido del campo al obtener el foco.
    private String strDesCorCta, strDesLarCta;          //Contenido del campo al obtener el foco.
    private String strCodPrv, strDesLarPrv;             //Contenido del campo al obtener el foco.
    private String strCodBen, strNomBen;                //Contenido del campo al obtener el foco.
    private String strNumDoc1, strNumDoc2;              //Contenido del campo al obtener el foco.
    private String strMonDoc;                           //Contenido del campo al obtener el foco.
    private int intSig=1;                               //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private String strTblActNumDoc="";
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
    private int intAutDoc;                              //Autorizaciones: 1: No se necesita autorizacián; 2:Si se necesita autorizacián.
    //Variables de la clase.
    private String strUbiCta, strAutCta;                //Campos: Ubicacián y Estado de autorizacián de la cuenta.
    private String strIdePrv, strDirPrv;                //Campos: RUC y Direccián del Beneficiario.
    
    private ZafRptSis objRptSis;                        //Reportes del Sistema.
    
    //VARIABLES Q CONTENDRAN EL CODIGO DE LOCAL Q SE CARGA A TRAVES DE LA CABECERA Q NO NECESARIAMENTE ES EL MISMO LOCAL POR DONDE SE INGRESA
    private int intLocCab;
    
    private String strNomSubRep="";
    private String strNomRepTuv="";
    
    private ZafCon09_01 objCon09_01;
    private ZafCon09_02 objCon09_02;
    
    private String strEstImpDoc;
    private String strCodGrpTipDoc;

    private boolean blnNecAutAnu;//true si necesita de autorizacion el documento, si no necesita es false
    private boolean blnDocAut;//true si el documento ya fue autorizado para ser anulado, false si falta todavia autorizarlo

    private ZafThreadGUIVisPre objThrGUIVisPre;

    private Vector vecRegDia, vecDatDia;
    final int INT_VEC_DIA_LIN=0;
    final int INT_VEC_DIA_COD_CTA=1;
    final int INT_VEC_DIA_NUM_CTA=2;
    final int INT_VEC_DIA_BUT_CTA=3;
    final int INT_VEC_DIA_NOM_CTA=4;
    final int INT_VEC_DIA_DEB=5;
    final int INT_VEC_DIA_HAB=6;
    final int INT_VEC_DIA_REF=7;
    final int INT_VEC_DIA_EST_CON=8;

    private String strNumRetFacComSel;
    
    private int INT_COD_MNU_EGR_TRS=3880;
    private int INT_COD_TIP_DOC_EGR_TRS=222;


    /** Crea una nueva instancia de la clase ZafCon06. */
    public ZafCon09(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

            configurarFrm();
            agregarDocLis();
            vecDatDia=new Vector();

        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    public ZafCon09(ZafParSis obj, Integer codigoCuenta)
    {
        this(obj);
//        txtCodSis.setText(codigoCuenta.toString());
        objTooBar.setEstado('c');
        objTooBar.consultar();
        objTooBar.setEstado('w');
        objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

        configurarFrm();
        agregarDocLis();
        vecDatDia=new Vector();
    }



    /** Crea una nueva instancia de la clase ZafCon06. */
    public ZafCon09(ZafParSis obj, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento, int codigoMenu)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

            configurarFrm();
            agregarDocLis();

            txtCodTipDoc.setText(""+codigoTipoDocumento);
            txtCodDoc.setText(""+codigoDocumento);
            objParSis.setCodigoLocal(codigoLocal);
            objParSis.setCodigoMenu(codigoMenu);

            consultarReg();
            objTooBar.setVisible(false);
            vecDatDia=new Vector();


        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }




    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        lblTipDoc = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        txtDesLarTipDoc = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc1 = new javax.swing.JLabel();
        txtNumDoc1 = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        lbCta = new javax.swing.JLabel();
        txtCodCta = new javax.swing.JTextField();
        txtDesCorCta = new javax.swing.JTextField();
        txtDesLarCta = new javax.swing.JTextField();
        butCta = new javax.swing.JButton();
        lblMonDoc = new javax.swing.JLabel();
        txtMonDoc = new javax.swing.JTextField();
        lblPrv = new javax.swing.JLabel();
        txtCodPrv = new javax.swing.JTextField();
        txtDesLarPrv = new javax.swing.JTextField();
        butPrv = new javax.swing.JButton();
        lblNumDoc2 = new javax.swing.JLabel();
        txtNumDoc2 = new javax.swing.JTextField();
        lblFecVen = new javax.swing.JLabel();
        lblBen = new javax.swing.JLabel();
        txtCodBen = new javax.swing.JTextField();
        txtNomBen = new javax.swing.JTextField();
        butBen = new javax.swing.JButton();
        lblMonDocPal = new javax.swing.JLabel();
        txtMonDocPal = new javax.swing.JTextField();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenTot = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panAsiDia = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 144));
        panGenCab.setRequestFocusEnabled(false);
        panGenCab.setLayout(null);

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
        panGenCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(100, 4, 56, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 100, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(0, 84, 100, 20);
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(100, 84, 80, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(420, 4, 20, 20);

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
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(156, 4, 264, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(444, 4, 100, 20);

        lblNumDoc1.setText("Número aterno 1:");
        lblNumDoc1.setToolTipText("Número alterno 1");
        panGenCab.add(lblNumDoc1);
        lblNumDoc1.setBounds(444, 44, 100, 20);

        txtNumDoc1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc1.setToolTipText("Número de egreso");
        txtNumDoc1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDoc1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDoc1FocusLost(evt);
            }
        });
        panGenCab.add(txtNumDoc1);
        txtNumDoc1.setBounds(544, 44, 120, 20);
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(68, 4, 32, 20);

        lbCta.setText("Cuenta:");
        lbCta.setToolTipText("Cuenta");
        panGenCab.add(lbCta);
        lbCta.setBounds(0, 24, 90, 20);
        panGenCab.add(txtCodCta);
        txtCodCta.setBounds(68, 24, 32, 20);

        txtDesCorCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorCtaActionPerformed(evt);
            }
        });
        txtDesCorCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorCtaFocusLost(evt);
            }
        });
        panGenCab.add(txtDesCorCta);
        txtDesCorCta.setBounds(100, 24, 80, 20);

        txtDesLarCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCtaActionPerformed(evt);
            }
        });
        txtDesLarCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCtaFocusLost(evt);
            }
        });
        panGenCab.add(txtDesLarCta);
        txtDesLarCta.setBounds(180, 24, 240, 20);

        butCta.setText("...");
        butCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaActionPerformed(evt);
            }
        });
        panGenCab.add(butCta);
        butCta.setBounds(420, 24, 20, 20);

        lblMonDoc.setText("Valor del documento:");
        lblMonDoc.setToolTipText("Valor del documento");
        panGenCab.add(lblMonDoc);
        lblMonDoc.setBounds(444, 84, 100, 20);

        txtMonDoc.setEditable(false);
        txtMonDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMonDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMonDocActionPerformed(evt);
            }
        });
        txtMonDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMonDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMonDocFocusLost(evt);
            }
        });
        panGenCab.add(txtMonDoc);
        txtMonDoc.setBounds(544, 84, 120, 20);

        lblPrv.setText("Proveedor:");
        lblPrv.setToolTipText("Proveedor");
        panGenCab.add(lblPrv);
        lblPrv.setBounds(0, 44, 100, 20);

        txtCodPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPrvActionPerformed(evt);
            }
        });
        txtCodPrv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPrvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPrvFocusLost(evt);
            }
        });
        panGenCab.add(txtCodPrv);
        txtCodPrv.setBounds(100, 44, 56, 20);

        txtDesLarPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarPrvActionPerformed(evt);
            }
        });
        txtDesLarPrv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarPrvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarPrvFocusLost(evt);
            }
        });
        panGenCab.add(txtDesLarPrv);
        txtDesLarPrv.setBounds(156, 44, 264, 20);

        butPrv.setText("...");
        butPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvActionPerformed(evt);
            }
        });
        panGenCab.add(butPrv);
        butPrv.setBounds(420, 44, 20, 20);

        lblNumDoc2.setText("Número aterno 2:");
        lblNumDoc2.setToolTipText("Número alterno 2");
        panGenCab.add(lblNumDoc2);
        lblNumDoc2.setBounds(444, 64, 100, 20);

        txtNumDoc2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc2.setToolTipText("Número de cheque");
        txtNumDoc2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDoc2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDoc2FocusLost(evt);
            }
        });
        panGenCab.add(txtNumDoc2);
        txtNumDoc2.setBounds(544, 64, 120, 20);

        lblFecVen.setText("Fecha de vencimiento:");
        lblFecVen.setToolTipText("Fecha de vencimiento");
        panGenCab.add(lblFecVen);
        lblFecVen.setBounds(444, 24, 100, 20);

        lblBen.setText("Beneficiario:");
        lblBen.setToolTipText("Beneficiario");
        panGenCab.add(lblBen);
        lblBen.setBounds(0, 64, 100, 20);

        txtCodBen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBenActionPerformed(evt);
            }
        });
        txtCodBen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBenFocusLost(evt);
            }
        });
        panGenCab.add(txtCodBen);
        txtCodBen.setBounds(100, 64, 56, 20);

        txtNomBen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBenActionPerformed(evt);
            }
        });
        txtNomBen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBenFocusLost(evt);
            }
        });
        panGenCab.add(txtNomBen);
        txtNomBen.setBounds(156, 64, 264, 20);

        butBen.setText("...");
        butBen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBenActionPerformed(evt);
            }
        });
        panGenCab.add(butBen);
        butBen.setBounds(420, 64, 20, 20);

        lblMonDocPal.setText("Valor del documento:");
        lblMonDocPal.setToolTipText("Valor del documento en palabras");
        panGenCab.add(lblMonDocPal);
        lblMonDocPal.setBounds(0, 104, 100, 20);
        panGenCab.add(txtMonDocPal);
        txtMonDocPal.setBounds(100, 104, 564, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        panGenDet.setLayout(new java.awt.BorderLayout());

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

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        panGenTot.setPreferredSize(new java.awt.Dimension(34, 70));
        panGenTot.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panGenTot.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panGenTot.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panGen.add(panGenTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de diario", panAsiDia);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumDoc2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDoc2FocusGained
        strNumDoc2=txtNumDoc2.getText();
        txtNumDoc2.selectAll();
    }//GEN-LAST:event_txtNumDoc2FocusGained

    private void txtNumDoc1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDoc1FocusGained
        strNumDoc1=txtNumDoc1.getText();
        txtNumDoc1.selectAll();
    }//GEN-LAST:event_txtNumDoc1FocusGained

    private void txtMonDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMonDocActionPerformed
        txtMonDoc.transferFocus();
    }//GEN-LAST:event_txtMonDocActionPerformed

    private void txtMonDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMonDocFocusGained
        strMonDoc=txtMonDoc.getText();
        txtMonDoc.selectAll();
    }//GEN-LAST:event_txtMonDocFocusGained

    private void txtNumDoc2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDoc2FocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtNumDoc2.getText().equalsIgnoreCase(strNumDoc2))
        {
            actualizarGlo();
        }
    }//GEN-LAST:event_txtNumDoc2FocusLost

    private void txtNumDoc1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDoc1FocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtNumDoc1.getText().equalsIgnoreCase(strNumDoc1))
        {
            actualizarGlo();
        }
    }//GEN-LAST:event_txtNumDoc1FocusLost

    private void butBenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBenActionPerformed
        mostrarVenConBen(0);
        if (  (!txtCodPrv.getText().equals(""))  && (!txtCodBen.getText().equals(""))  )
        {
            //Cargar los documentos pendientes sálo si ha cambiado el beneficiario.
            if (!txtCodBen.getText().equalsIgnoreCase(strCodBen))
                if (vcoBen.getSelectedButton()==vcoBen.INT_BUT_ACE){
                    if( ! txtCodCta.getText().equals("")){
                        objCon09_02=new ZafCon09_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, Integer.parseInt(txtCodCta.getText()), Integer.parseInt(txtCodPrv.getText()), Integer.parseInt(txtCodBen.getText())  );
                        if(objCon09_02.getExisteRegistroFecha()>0){
                            if(objCon09_02.getExisteRegistroFecha()==1){
                                objCon09_02.setFiltroFecha(objCon09_02.getExisteUnRegistroFecha());
                                cargarDocPen();
                            }
                            else{
                                objCon09_02.show();
                                if( ! objCon09_02.getFiltroFecha().equals("")){
                                    cargarDocPen();
                                    objCon09_02=null;
                                }
                            }

                        }
                        else{
                            //objCon09_02.setFiltroFecha("2009-07-28");
                            objCon09_02.setFiltroFecha("" + objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(),  "yyyy-MM-dd"));
                            cargarDocPen();
                        }
                    }
                }
        }
    }//GEN-LAST:event_butBenActionPerformed

    private void txtNomBenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBenFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtNomBen.getText().equalsIgnoreCase(strNomBen))
        {
            if (txtNomBen.getText().equals(""))
            {
                txtCodBen.setText("");
                txtNomBen.setText("");
            }
            else
            {
                mostrarVenConBen(2);
                //cargarDocPen();
                if (  (!txtCodPrv.getText().equals(""))  && (!txtCodBen.getText().equals(""))  )
                {
                    //Cargar los documentos pendientes sálo si ha cambiado el beneficiario.
                    if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv))
                        if (vcoPrv.getSelectedButton()==vcoCta.INT_BUT_ACE){
                            if( ! txtCodCta.getText().equals("")){
                                objCon09_02=new ZafCon09_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, Integer.parseInt(txtCodCta.getText()), Integer.parseInt(txtCodPrv.getText()), Integer.parseInt(txtCodBen.getText())  );
                                if(objCon09_02.getExisteRegistroFecha()>0){
                                    if(objCon09_02.getExisteRegistroFecha()==1){
                                        objCon09_02.setFiltroFecha(objCon09_02.getExisteUnRegistroFecha());
                                        cargarDocPen();
                                    }
                                    else{
                                        objCon09_02.show();
                                        if( ! objCon09_02.getFiltroFecha().equals("")){
                                            cargarDocPen();
                                            objCon09_02=null;
                                        }
                                    }

                                }
                                else{
                                    //objCon09_02.setFiltroFecha("2009-07-28");
                                    objCon09_02.setFiltroFecha("" + objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(),  "yyyy-MM-dd"));
                                    cargarDocPen();
                                }
                            }
                        }
                }
            }
        }
        else
            txtNomBen.setText(strNomBen);
    }//GEN-LAST:event_txtNomBenFocusLost

    private void txtNomBenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBenFocusGained
        strNomBen=txtNomBen.getText();
        txtNomBen.selectAll();
    }//GEN-LAST:event_txtNomBenFocusGained

    private void txtNomBenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBenActionPerformed
        txtNomBen.transferFocus();
    }//GEN-LAST:event_txtNomBenActionPerformed

    private void txtCodBenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBenFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtCodBen.getText().equalsIgnoreCase(strCodBen))
        {
            if (txtCodBen.getText().equals(""))
            {
                txtCodBen.setText("");
                txtNomBen.setText("");
            }
            else
            {
                mostrarVenConBen(1);
                //cargarDocPen();
                if (  (!txtCodPrv.getText().equals(""))  && (!txtCodBen.getText().equals(""))  )
                {
                    //Cargar los documentos pendientes sálo si ha cambiado el beneficiario.
                    if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv))
                        if (vcoPrv.getSelectedButton()==vcoCta.INT_BUT_ACE){
                            if( ! txtCodCta.getText().equals("")){
                                objCon09_02=new ZafCon09_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, Integer.parseInt(txtCodCta.getText()), Integer.parseInt(txtCodPrv.getText()), Integer.parseInt(txtCodBen.getText())  );
                                if(objCon09_02.getExisteRegistroFecha()>0){
                                    if(objCon09_02.getExisteRegistroFecha()==1){
                                        objCon09_02.setFiltroFecha(objCon09_02.getExisteUnRegistroFecha());
                                        cargarDocPen();
                                    }
                                    else{
                                        objCon09_02.show();
                                        if( ! objCon09_02.getFiltroFecha().equals("")){
                                            cargarDocPen();
                                            objCon09_02=null;
                                        }
                                    }

                                }
                                else{
                                    //objCon09_02.setFiltroFecha("2009-07-28");
                                    objCon09_02.setFiltroFecha("" + objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(),  "yyyy-MM-dd"));
                                    cargarDocPen();
                                }
                            }
                        }
                }
            }
        }
        else
            txtCodBen.setText(strCodBen);
    }//GEN-LAST:event_txtCodBenFocusLost

    private void txtCodBenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBenFocusGained
        strCodBen=txtCodBen.getText();
        txtCodBen.selectAll();
    }//GEN-LAST:event_txtCodBenFocusGained

    private void txtCodBenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBenActionPerformed
        txtCodBen.transferFocus();
    }//GEN-LAST:event_txtCodBenActionPerformed

    private void txtMonDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMonDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtMonDoc.getText().equalsIgnoreCase(strMonDoc))
        {
            //Desmarcar los documentos en el JTable.
            for (int i=0; i<objTblMod.getRowCountTrue(); i++)
                objTblMod.setChecked(false, i, INT_TBL_DAT_CHK);
            objAsiDia.generarDiario(txtCodTipDoc.getText(), txtCodCta.getText(), strUbiCta, txtMonDoc.getText(), txtMonDoc.getText());
            //Cantidad en palabras.
            try{
             Librerias.ZafUtil.ZafNumLetra numero;
             double cantidad= objUti.redondear(txtMonDoc.getText(), objParSis.getDecimalesMostrar());
             String decimales=String.valueOf(cantidad).toString();
                    decimales=decimales.substring(decimales.indexOf('.') + 1); 
                    decimales=(decimales.length()<2?decimales + "0":decimales.substring(0,2));
             int deci= Integer.parseInt(decimales);
             int m_pesos=(int)cantidad;
            numero = new Librerias.ZafUtil.ZafNumLetra(m_pesos);
            String	res = numero.convertirLetras(m_pesos);
            res = res+" "+decimales+"/100  DOLARES";
            res=res.toUpperCase(); 	
            txtMonDocPal.setText(""+res);
            numero=null;        
            }catch(Exception e){  objUti.mostrarMsgErr_F1(this, e);  }
        }
    }//GEN-LAST:event_txtMonDocFocusLost

    private void txtDesLarPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarPrv.getText().equalsIgnoreCase(strDesLarPrv))
        {
            if (txtDesLarPrv.getText().equals(""))
            {
                txtCodPrv.setText("");
                txtDesLarPrv.setText("");
                txtCodBen.setText("");
                txtNomBen.setText("");
                objTblMod.removeAllRows();
                txtMonDoc.setText("");
            }
            else
            {
                mostrarVenConPrv(2);
                //Cargar los documentos pendientes sálo si ha cambiado el beneficiario.
                if( (!txtDesLarPrv.getText().equalsIgnoreCase(strDesLarPrv))  &&  (! txtCodCta.getText().equals(""))  ){
                    objCon09_02=new ZafCon09_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, Integer.parseInt(txtCodCta.getText()), Integer.parseInt(txtCodPrv.getText()), Integer.parseInt(txtCodBen.getText()) );
                    if(objCon09_02.getExisteRegistroFecha()>0){
                        if(objCon09_02.getExisteRegistroFecha()==1){
                            objCon09_02.setFiltroFecha(objCon09_02.getExisteUnRegistroFecha());
                            cargarDocPen();
                        }
                        else{
                            objCon09_02.show();
                            if( ! objCon09_02.getFiltroFecha().equals("")){
                                cargarDocPen();
                                objCon09_02=null;
                            }
                        }

                    }
                    else{
                        //objCon09_02.setFiltroFecha("2009-07-28");
                        objCon09_02.setFiltroFecha("" + objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(),  "yyyy-MM-dd"));
                        cargarDocPen();
                    }
                }
            }
        }
        else
            txtDesLarPrv.setText(strDesLarPrv);
    }//GEN-LAST:event_txtDesLarPrvFocusLost

    private void txtDesLarPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusGained
        strDesLarPrv=txtDesLarPrv.getText();
        txtDesLarPrv.selectAll();
    }//GEN-LAST:event_txtDesLarPrvFocusGained

    private void txtDesLarPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarPrvActionPerformed
        txtDesLarPrv.transferFocus();
    }//GEN-LAST:event_txtDesLarPrvActionPerformed

    private void txtCodPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv))
        {
            if (txtCodPrv.getText().equals(""))
            {
                txtCodPrv.setText("");
                txtDesLarPrv.setText("");
                txtCodBen.setText("");
                txtNomBen.setText("");
                objTblMod.removeAllRows();
                txtMonDoc.setText("");
            }
            else
            {
                mostrarVenConPrv(1);
                //Cargar los documentos pendientes sálo si ha cambiado el beneficiario.
                 if( (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv))  &&  ( ! txtCodCta.getText().equals(""))  ){
                    objCon09_02=new ZafCon09_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, Integer.parseInt(txtCodCta.getText()), Integer.parseInt(txtCodPrv.getText()), Integer.parseInt(txtCodBen.getText())  );
                    if(objCon09_02.getExisteRegistroFecha()>0){
                        if(objCon09_02.getExisteRegistroFecha()==1){
                            objCon09_02.setFiltroFecha(objCon09_02.getExisteUnRegistroFecha());
                            cargarDocPen();
                        }
                        else{
                            objCon09_02.show();
                            if( ! objCon09_02.getFiltroFecha().equals("")){
                                cargarDocPen();
                                objCon09_02=null;
                            }
                        }

                    }
                    else{
                        //objCon09_02.setFiltroFecha("2009-07-28");
                        objCon09_02.setFiltroFecha("" + objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(),  "yyyy-MM-dd"));


                        cargarDocPen();
                    }
                 }
            }
        }
        else
            txtCodPrv.setText(strCodPrv);
    }//GEN-LAST:event_txtCodPrvFocusLost

    private void txtCodPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusGained
        strCodPrv=txtCodPrv.getText();
        txtCodPrv.selectAll();
    }//GEN-LAST:event_txtCodPrvFocusGained

    private void txtCodPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvActionPerformed
        txtCodPrv.transferFocus();
    }//GEN-LAST:event_txtCodPrvActionPerformed

    private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
        strCodPrv=txtCodPrv.getText();
        mostrarVenConPrv(0);
        
        if (!txtCodPrv.getText().equals(""))
        {
            //Cargar los documentos pendientes sálo si ha cambiado el beneficiario.
            if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv))
                if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE){
                    if( ! txtCodCta.getText().equals("")){
                        objCon09_02=new ZafCon09_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, Integer.parseInt(txtCodCta.getText()), Integer.parseInt(txtCodPrv.getText()), Integer.parseInt(txtCodBen.getText())  );
                        if(objCon09_02.getExisteRegistroFecha()>0){
                            if(objCon09_02.getExisteRegistroFecha()==1){
                                objCon09_02.setFiltroFecha(objCon09_02.getExisteUnRegistroFecha());
                                cargarDocPen();
                            }
                            else{
                                objCon09_02.show();
                                if( ! objCon09_02.getFiltroFecha().equals("")){
                                    cargarDocPen();
                                    objCon09_02=null;
                                }
                            }

                        }
                        else{
                            //objCon09_02.setFiltroFecha("2009-07-28");
                            objCon09_02.setFiltroFecha("" + objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(),  "yyyy-MM-dd"));
                            cargarDocPen();
                        }
                    }
                }
        }
    }//GEN-LAST:event_butPrvActionPerformed

    private void txtDesLarCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarCta.getText().equalsIgnoreCase(strDesLarCta))
        {
            if (txtDesLarCta.getText().equals(""))
            {
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConCta(2);
                if( ! txtCodPrv.getText().equals("")){
                    objCon09_02=new ZafCon09_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, Integer.parseInt(txtCodCta.getText()), Integer.parseInt(txtCodPrv.getText()), Integer.parseInt(txtCodBen.getText()) );
                    if(objCon09_02.getExisteRegistroFecha()>0){
                        if(objCon09_02.getExisteRegistroFecha()==1){
                            objCon09_02.setFiltroFecha(objCon09_02.getExisteUnRegistroFecha());
                            cargarDocPen();
                        }
                        else{
                            objCon09_02.show();
                            if( ! objCon09_02.getFiltroFecha().equals("")){
                                cargarDocPen();
                                objCon09_02=null;
                            }
                        }

                    }
                    else{
                        //objCon09_02.setFiltroFecha("2009-07-28");
                        objCon09_02.setFiltroFecha("" + objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(),  "yyyy-MM-dd"));
                        cargarDocPen();
                    }
                }
            }
        }
        else
            txtDesLarCta.setText(strDesLarCta);
    }//GEN-LAST:event_txtDesLarCtaFocusLost

    private void txtDesLarCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaFocusGained
        strDesLarCta=txtDesLarCta.getText();
        txtDesLarCta.selectAll();
    }//GEN-LAST:event_txtDesLarCtaFocusGained

    private void txtDesLarCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCtaActionPerformed
        txtDesLarCta.transferFocus();
    }//GEN-LAST:event_txtDesLarCtaActionPerformed

    private void txtDesCorCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesCorCta.getText().equalsIgnoreCase(strDesCorCta))
        {
            if (txtDesCorCta.getText().equals(""))
            {
                txtCodCta.setText("");
                txtDesLarCta.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConCta(1);
                if( ! txtCodPrv.getText().equals("")){
                    objCon09_02=new ZafCon09_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, Integer.parseInt(txtCodCta.getText()), Integer.parseInt(txtCodPrv.getText()), Integer.parseInt(txtCodBen.getText())   );
                    if(objCon09_02.getExisteRegistroFecha()>0){
                        if(objCon09_02.getExisteRegistroFecha()==1){
                            objCon09_02.setFiltroFecha(objCon09_02.getExisteUnRegistroFecha());
                            cargarDocPen();
                        }
                        else{
                            objCon09_02.show();
                            if( ! objCon09_02.getFiltroFecha().equals("")){
                                cargarDocPen();
                                objCon09_02=null;
                            }
                        }

                    }
                    else{
                        //objCon09_02.setFiltroFecha("2009-07-28");
                        objCon09_02.setFiltroFecha("" + objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(),  "yyyy-MM-dd"));
                        cargarDocPen();
                    }
                }   
            }
        }
        else
            txtDesCorCta.setText(strDesCorCta);
    }//GEN-LAST:event_txtDesCorCtaFocusLost

    private void txtDesCorCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaFocusGained
        strDesCorCta=txtDesCorCta.getText();
        txtDesCorCta.selectAll();
    }//GEN-LAST:event_txtDesCorCtaFocusGained

    private void txtDesCorCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorCtaActionPerformed
        txtDesCorCta.transferFocus();
    }//GEN-LAST:event_txtDesCorCtaActionPerformed

    private void butCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaActionPerformed
        mostrarVenConCta(0);
        if (!txtCodCta.getText().equals(""))
        {
            if (vcoCta.getSelectedButton()==vcoCta.INT_BUT_ACE){
                if( ! txtCodPrv.getText().equals("")){
                    objCon09_02=new ZafCon09_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, Integer.parseInt(txtCodCta.getText()), Integer.parseInt(txtCodPrv.getText()), Integer.parseInt(txtCodBen.getText())  );
                    if(objCon09_02.getExisteRegistroFecha()>0){
                        if(objCon09_02.getExisteRegistroFecha()==1){
                            objCon09_02.setFiltroFecha(objCon09_02.getExisteUnRegistroFecha());
                            cargarDocPen();
                        }
                        else{
                            objCon09_02.show();
                            if( ! objCon09_02.getFiltroFecha().equals("")){
                                cargarDocPen();
                                objCon09_02=null;
                            }
                        }

                    }
                    else{
                        //objCon09_02.setFiltroFecha("2009-07-28");
                        objCon09_02.setFiltroFecha("" + objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(),  "yyyy-MM-dd"));
                        cargarDocPen();
                    }
                }
            }
        }
    }//GEN-LAST:event_butCtaActionPerformed

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                txtDesLarCta.setText("");
            }
            else
            {
                mostrarVenConTipDoc(2);
            }
        }
        else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtDesCorTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                txtDesLarCta.setText("");
            }
            else
            {
                mostrarVenConTipDoc(1);
            }
        }
        else
            txtDesCorTipDoc.setText(strDesCorTipDoc);
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    /** Cerrar la aplicacián. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="áEstá seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexián si está abierta.
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    /** Cerrar la aplicacián. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBen;
    private javax.swing.JButton butCta;
    private javax.swing.JButton butPrv;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lbCta;
    private javax.swing.JLabel lblBen;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecVen;
    private javax.swing.JLabel lblMonDoc;
    private javax.swing.JLabel lblMonDocPal;
    private javax.swing.JLabel lblNumDoc1;
    private javax.swing.JLabel lblNumDoc2;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenTot;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodBen;
    private javax.swing.JTextField txtCodCta;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodPrv;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorCta;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarCta;
    private javax.swing.JTextField txtDesLarPrv;
    private javax.swing.JTextField txtDesLarTipDoc;
    public javax.swing.JTextField txtMonDoc;
    public javax.swing.JTextField txtMonDocPal;
    private javax.swing.JTextField txtNomBen;
    private javax.swing.JTextField txtNumDoc1;
    private javax.swing.JTextField txtNumDoc2;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(544, 4, 120, 20);
            
            if (objParSis.getCodigoMenu() == 4215)
            {  //co_mnu 4215 = Pagos a clientes utilizando transferencias bancarias.
               lblPrv.setText("Cliente:");
            }
            
            dtpFecVen=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecVen.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecVen.setText("");
            panGenCab.add(dtpFecVen);
            dtpFecVen.setBounds(544, 24, 120, 20);
            dtpFecVen.setEnabled(false);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            objAsiDia=new ZafAsiDia(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
            
            objDocLis=new ZafDocLis();
            objAutPrg=new ZafAutPrg(this);
            panBar.add(objTooBar);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.9.7");//para codigo con autorizaciones es 0.16 y para produccion 0.13.1
            lblTit.setText(strAux);
            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorCta.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarCta.setBackground(objParSis.getColorCamposObligatorios());
            txtCodPrv.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarPrv.setBackground(objParSis.getColorCamposObligatorios());
            txtCodBen.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBen.setBackground(objParSis.getColorCamposObligatorios());
            txtMonDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc1.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc2.setBackground(objParSis.getColorCamposObligatorios());
            txtCodTipDoc.setVisible(false);
            txtCodCta.setVisible(false);
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            configurarVenConCta();
            configurarVenConPrv();
            configurarVenConBen();
            //Configurar los JTables.
            configurarTblDat();
                        

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(18);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cád.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cád.Tip.Doc");
            vecCab.add(INT_TBL_DAT_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cod.Doc.");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cod.Reg.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Num.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_DIA_CRE,"Dia.Cre.");
            vecCab.add(INT_TBL_DAT_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Val.Doc.");
            vecCab.add(INT_TBL_DAT_VAL_PEN,"Val.Pen.");
            vecCab.add(INT_TBL_DAT_AUX_ABO,"Aux.Abo.");
            vecCab.add(INT_TBL_DAT_AUX_NAT_DOC,"Aux.Nat.Doc.");
            vecCab.add(INT_TBL_DAT_VAL_APL,"Val.Apl.");
            vecCab.add(INT_TBL_DAT_VAL_APL_USR,"Val.Apl.Usr.");

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_APL_USR, objTblMod.INT_COL_DBL, new Integer(0), null);
//            //Configurar ZafTblMod: Establecer las columnas obligatorias.
//            java.util.ArrayList arlAux=new java.util.ArrayList();
//            arlAux.add("" + INT_TBL_DAT_COD_CTA);
//            objTblMod.setColumnasObligatorias(arlAux);
//            arlAux=null;
//            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
//            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DEC_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FEC_VEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_AUX_ABO).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_AUX_NAT_DOC).setPreferredWidth(70);
            
            tcmAux.getColumn(INT_TBL_DAT_VAL_APL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_APL_USR).setPreferredWidth(70);

            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_AUX_ABO, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_AUX_NAT_DOC, tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK);
            vecAux.add("" + INT_TBL_DAT_VAL_APL_USR);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_APL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_APL_USR).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;




            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bdeValAplEvn=new BigDecimal(0);
                int intCodCtaDeb, intCodCtaHab;
                boolean blnIsTuv_Cas_Dim;
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiChk.isChecked())
                    {
                        bdeValAplEvn=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_APL)==null?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_APL).toString());
                        if(bdeValAplEvn.compareTo(new BigDecimal(0))>0){
                            objTblMod.setValueAt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_APL), tblDat.getSelectedRow(), INT_TBL_DAT_VAL_APL_USR);
                        }
                        else{
                            mostrarMsgInf("<HTML>El documento no puede ser seleccionado por tener valor negativo o cero.<BR>Este documento puede tener notas de crédito que aplicar por lo que el valor de cheque a generar<BR>se muestra en negativo o en valor cero.</HTML>");
                            objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_VAL_APL_USR);
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                        }
                    }
                    else
                    {
                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_VAL_APL_USR);
                    }
                    calcularAboTot();
                    blnIsTuv_Cas_Dim = (objParSis.getNombreEmpresa().toUpperCase().indexOf("TUVAL") != -1
                                || objParSis.getNombreEmpresa().toUpperCase().indexOf("CASTEK") != -1
                                || objParSis.getNombreEmpresa().toUpperCase().indexOf("DIMULTI") != -1)? true: false;
                    
                    if (objParSis.getCodigoMenu() == 4215 && blnIsTuv_Cas_Dim == true)
                    {  //co_mnu 4215 = Pagos a clientes utilizando transferencias bancarias.
                       intCodCtaDeb = 0;
                       intCodCtaHab = 0;
                       
                       switch (objParSis.getCodigoEmpresa())
                       {  case 1: //Tuval
                             intCodCtaDeb = 29; //CLIENTES GUAYAQUIL
                             intCodCtaHab = 4052; //TRANSFERENCIAS BANCARIAS
                             break;
                          
                          case 2: //Castek
                             intCodCtaHab = 1879; //TRANSFERENCIAS BANCARIAS
                             
                             switch (objParSis.getCodigoLocal())
                             {  case 1: case 11: //Castek Quito
                                   intCodCtaDeb = 24; //CLIENTES QUITO
                                   break;
                                case 4: case 12: //Castek Manta
                                   intCodCtaDeb = 1035; //CLIENTES MANTA
                                   break;
                                case 6: case 13: //Castek Santo Domingo
                                   intCodCtaDeb = 1161; //CLIENTES SANTO DOMINGO
                                   break;
                                case 10: case 14: //Castek Cuenca
                                   intCodCtaDeb = 1905; //CLIENTES CUENCA
                                   break;
                             }
                             break;
                          
                          case 4: //Dimulti
                             intCodCtaHab = 2911; //TRANSFERENCIAS BANCARIAS
                             
                             switch (objParSis.getCodigoLocal())
                             {  case 12: case 13:
                                   intCodCtaDeb = 3329; //CLIENTES DURAN
                                   break;
                                default:
                                   intCodCtaDeb = 28; //CLIENTES GUAYAQUIL
                                   break;
                             }
                             break;
                       } //switch (objParSis.getCodigoEmpresa())
                       objAsiDia.generarDiario(txtCodTipDoc.getText(), intCodCtaDeb,  txtMonDoc.getText(), intCodCtaHab, txtMonDoc.getText());
                    } //if (objParSis.getCodigoMenu() == 4215 && blnIsTuv_Cas_Dim == true)
                    else if( (objParSis.getCodigoMenu()==2024) && (objParSis.getCodigoEmpresa()==2) )
                        objAsiDia.generarDiario(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodCta.getText()), txtMonDoc.getText(), Integer.parseInt(txtCodCta.getText()), txtMonDoc.getText());
                    else
                        objAsiDia.generarDiario(txtCodTipDoc.getText(), txtCodCta.getText(), txtMonDoc.getText(), txtMonDoc.getText());
                    actualizarGlo();
                }
            });
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_VAL_APL_USR).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxt.getText().equals(""))
                    {
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                    }
                    else
                    {
                        objTblMod.setChecked(true, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                    }
                    calcularAboTot();

                    objAsiDia.generarDiario(txtCodTipDoc.getText(), txtCodCta.getText(), strUbiCta, txtMonDoc.getText(), txtMonDoc.getText());
                    actualizarGlo();
                }
            });

            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
            
    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegacián
     * que permiten desplazarse al primero, anterior, siguiente y áltimo registro.
     */
    private class MiToolBar extends ZafToolBar
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objParSis);
        }

        public boolean anular()
        {
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }

        public void clickAceptar()
        {
            
        }
        
        public void clickAnterior() 
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnHayCam || objAsiDia.isDiarioModificado())
                    {
                        if (isRegPro())
                        {
                            rstCab.previous();
                            cargarReg();
                            if(camposInactivosPermisoModifi()){
                            }
                        }
                    }
                    else
                    {
                        rstCab.previous();
                        cargarReg();
                        if(camposInactivosPermisoModifi()){
                        }
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

        public void clickAnular()
        {
            cargarDetReg();
        }

        public void clickCancelar()
        {

        }

        public void clickConsultar() 
        {
            cargarDetReg();
            txtDesCorTipDoc.requestFocus();
        }

        public void clickEliminar()
        {
            cargarDetReg();
        }

        public void clickFin() 
        {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam || objAsiDia.isDiarioModificado())
                    {
                        if (isRegPro())
                        {
                            rstCab.last();
                            cargarReg();
                            if(camposInactivosPermisoModifi()){
                            }
                        }
                    }
                    else
                    {
                        rstCab.last();
                        cargarReg();
                        if(camposInactivosPermisoModifi()){
                        }
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

        public void clickImprimir(){
            

            
            
        }

        public void clickInicio()
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnHayCam || objAsiDia.isDiarioModificado())
                    {
                        if (isRegPro())
                        {
                            rstCab.first();
                            cargarReg();
                            if(camposInactivosPermisoModifi()){
                            }
                        }
                    }
                    else
                    {
                        rstCab.first();
                        cargarReg();
                        if(camposInactivosPermisoModifi()){
                        }
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
                if (blnHayCam)
                {
                    isRegPro();
                }
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                limpiarFrm();
                txtCodDoc.setEditable(false);
//                txtMonDoc.setEditable(false);
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                dtpFecVen.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                dtpFecVen.setEnabled(false);
                datFecAux=null;
                mostrarTipDocPre();
                if (!txtCodTipDoc.getText().equals(""))
                    mostrarCtaPre();
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                objAsiDia.setEditable(true);
                txtNumDoc1.selectAll();
                txtNumDoc1.requestFocus();
                //Inicializar las variables que indican cambios.
                objAsiDia.setDiarioModificado(false);
                blnHayCam=false;
                txtMonDoc.setEditable(false);
                
                txtCodBen.setEnabled(true);
                txtNomBen.setEnabled(true);
                txtMonDocPal.setEnabled(true);
                dtpFecDoc.setEnabled(true);
//                txtNumDoc1.setEnabled(false);
//                txtNumDoc2.setEnabled(false);
                
                strNumRetFacComSel="";
                
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

        public void clickModificar(){
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            txtCodPrv.setEditable(false);
            txtDesLarPrv.setEditable(false);
            butPrv.setEnabled(false);
            txtCodBen.setEditable(false);
            txtNomBen.setEditable(false);
            butBen.setEnabled(false);
            txtCodDoc.setEditable(false);
            txtMonDoc.setEditable(false);
            txtCodCta.setEditable(false);
            txtDesCorCta.setEditable(false);
            txtDesLarCta.setEditable(false);
            butCta.setEnabled(false);
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objAsiDia.setEditable(true);
            cargarReg();
            txtNumDoc1.selectAll();
            txtNumDoc1.requestFocus();
//            mostrarCtaPre();
            dtpFecVen.setEnabled(false);
            


            if(camposInactivosPermisoModifi()){
                
            }
            

            
            
            
            
            
        }

        public void clickSiguiente()
        {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam || objAsiDia.isDiarioModificado())
                    {
                        if (isRegPro())
                        {
                            rstCab.next();
                            cargarReg();
                            if(camposInactivosPermisoModifi()){
                            }
                        }
                    }
                    else
                    {
                        rstCab.next();
                        cargarReg();
                        if(camposInactivosPermisoModifi()){
                        }
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

        public void clickVisPreliminar() 
        {
            
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
                    limpiarFrm();
                }
                blnHayCam=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }

        public boolean insertar()
        {
            if (!insertarReg())
                return false;
            return true;
        }

        public boolean modificar()
        {
            if (!actualizarReg())
                return false;
            return true;
        }
        
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (blnHayCam || objAsiDia.isDiarioModificado())
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
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
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
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }
        
        public boolean vistaPreliminar(){
            if (objThrGUIVisPre==null)
            {
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(1);
                objThrGUIVisPre.start();
            }
            return true;
            
        }
        
        public boolean aceptar()
        {
            return true;
        }
        
        public boolean imprimir(){           
            if (objThrGUIVisPre==null)
            {
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(0);
                objThrGUIVisPre.start();
            }
            return true;



            
        }
        
        public boolean beforeInsertar(){
            boolean blnRes=true;
//            if(objParSis.getCodigoMenu()==574){//solo se aplica para proveedores porq solo ahi se debe bloquear si tienen notas credito sin aplicar, para clientes se autorizan
//                if( ! existeDocumentosCruzar()){
//                    if(isBanSelBie()){
//                        if (!isCamVal())
//                            return false;
//                        if (objAsiDia.getGeneracionDiario()==2)
//                            return regenerarDiario();
//                    }
//                    else
//                        blnRes=false;
//                }
//                else{
//                    mostrarMsgInf("<HTML>El egreso no se pudo guardar.<BR>El proveedor tiene notas de crédito asociadas que no han sido cruzadas.<BR>Cruce esos documentos y luego procese el egreso.</HTML>");
//                    blnRes=false;
//                }
//            }
//            else{
//                if(isBanSelBie()){
//                    if (!isCamVal())
//                        return false;
//                    if (objAsiDia.getGeneracionDiario()==2)
//                        return regenerarDiario();
//                }
//                else
//                    blnRes=false;
//            }

            //if((objParSis.getCodigoMenu()==574) || (objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS) ){
            if((objParSis.getCodigoMenu()==574) ){
                if( ! existeNotaCreditoSeleccionado()){
                    if( ! existePagoNegativoCero()){
                        if( ! valorPagoExcede()){//si el valor aplicado excede el valor permitido FACCOM - DEVCOM
                            if(isBanSelBie()){
                                if (!isCamVal())
                                    return false;
                                if (objAsiDia.getGeneracionDiario()==2)
                                    return regenerarDiario();
                            }
                            else
                                blnRes=false;
                        }
                        else{
                            mostrarMsgInf("<HTML>El egreso no se pudo guardar.<BR>Algún valor aplicado a un documento es mayor al valor permitido<BR>Verifique y vuelva a intentarlo</HTML>");
                            blnRes=false;
                        }
                    }
                    else{
                        mostrarMsgInf("<HTML>El egreso no se pudo guardar.<BR>Existen pagos aplicados en negativo o cero.<BR>Verifique y vuelva a intentarlo</HTML>");
                        blnRes=false;
                    }
                }
                else{
                    mostrarMsgInf("<HTML>El egreso no se pudo guardar.<BR>El proveedor tiene notas de crédito seleccionadas y estás no se deben seleccionar.<BR>Quite la selección de las Notas de crédito y vuelva a intentarlo.</HTML>");
                    blnRes=false;
                }
            }
            else{
                if( ! existePagoNegativoCero()){
                    if( ! valorPagoExcede()){//si el valor aplicado excede el valor permitido FACCOM - DEVCOM
                        if(isBanSelBie()){
                            if (!isCamVal())
                                return false;
                            if (objAsiDia.getGeneracionDiario()==2)
                                return regenerarDiario();
                        }
                        else
                            blnRes=false;
                    }
                    else{
                        mostrarMsgInf("<HTML>El egreso no se pudo guardar.<BR>Algún valor aplicado a un documento es mayor al valor permitido<BR>Verifique y vuelva a intentarlo</HTML>");
                        blnRes=false;
                    }
                }
                else{
                    mostrarMsgInf("<HTML>El egreso no se pudo guardar.<BR>Existen pagos aplicados en negativo o cero.<BR>Verifique y vuelva a intentarlo</HTML>");
                    blnRes=false;
                }
            }








            return blnRes;
        }
        
        public boolean beforeConsultar()
        {
            return true;
        }

        public boolean beforeModificar()
        {
            boolean blnRes=true;
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                blnRes=false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                blnRes=false;
            }
//            if(objParSis.getCodigoMenu()==574){//solo se aplica para proveedores porq solo ahi se debe bloquear si tienen notas credito sin aplicar, para clientes se autorizan
//                if( ! existeDocumentosCruzar()){
//                    if (!isCamVal())
//                        return false;
//                    if (objAsiDia.getGeneracionDiario()==2)
//                        return regenerarDiario();
//                }
//                else{
//                    mostrarMsgInf("<HTML>El egreso no se pudo modificar.<BR>El proveedor tiene notas de crédito asociadas que no han sido cruzadas.<BR>Cruce esos documentos y luego modifique el egreso.</HTML>");
//                    blnRes=false;
//                }
//            }
//            else{
//                if (!isCamVal())
//                    blnRes=false;
//                if (objAsiDia.getGeneracionDiario()==2)
//                    return regenerarDiario();
//            }


            if( (objParSis.getCodigoMenu()==574) || (objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS) ) {
                if( ! existeNotaCreditoSeleccionado()){
                    if( ! existePagoNegativoCero()){
                        if( ! existePagoSuperior()){
                            if (!isCamVal())
                                blnRes=false;
                            if (objAsiDia.getGeneracionDiario()==2)
                                return regenerarDiario();
                        }
                        else{
                            mostrarMsgInf("<HTML>El egreso no se pudo modificar porque existe un abono mayor al que se puede aplicar.<BR>Verifique y vuelva a modificarlo.</HTML>");
                            blnRes=false;
                        }
                    }
                    else{
                        mostrarMsgInf("<HTML>El egreso no se pudo guardar.<BR>Existen pagos aplicados en negativo o cero.<BR>Verifique y vuelva a intentarlo</HTML>");
                        blnRes=false;
                    }
                }
                else{
                    mostrarMsgInf("<HTML>El egreso no se pudo guardar.<BR>El proveedor tiene notas de crédito seleccionadas y estás no se deben seleccionar.<BR>Quite la selección de las Notas de crédito y vuelva a intentarlo.</HTML>");
                    blnRes=false;
                }

            }

            return blnRes;
        }

        public boolean beforeEliminar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeAnular()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            if((blnNecAutAnu) && (!blnDocAut)){
                mostrarMsgInf("El documento requiere autorización para ser anulado.\nNo es posible anular un documento que requiere autorización y no está autorizado.");
                return false;
            }


            return true;
        }

        public boolean beforeImprimir(){
            boolean blnRes=true;
//            if(strEstImpDoc.equals("N")){
//            }
//            else{
//                mostrarMsgInf("<HTML>El documento tiene estado impreso.<BR>No se puede imprimir un documento impreso si no se cuenta con los permisos respectivos.<BR>Si necesita imprimir el documento, solicite al Administrador del Sistema le conceda los permisos respectivos.</HTML>");
//                blnRes=false;
//            }
            return blnRes;
        }

        public boolean beforeVistaPreliminar()
        {
            boolean blnRes=true;
//            if(strEstImpDoc.equals("N")){
//            }
//            else{
//                mostrarMsgInf("<HTML>El documento tiene estado impreso.<BR>No se puede imprimir un documento impreso si no se cuenta con los permisos respectivos.<BR>Si necesita imprimir el documento, solicite al Administrador del Sistema le conceda los permisos respectivos.</HTML>");
//                blnRes=false;
//            }
            return blnRes;
        }

        public boolean beforeAceptar()
        {
            return true;
        }
        
        public boolean beforeCancelar()
        {
            return true;
        }
        
        public boolean afterInsertar()
        {
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            //Configurar JFrame de acuerdo al estado del registro.
            if (intAutDoc==2) //Solicitar autorizacián.
            {
                objTooBar.setEstado('m');
                objTooBar.setEnabledImprimir(false);
                objTooBar.setEnabledVistaPreliminar(false);
            }
            else
            {
                objTooBar.setEstado('w');
            }
//            objTooBar.setEstado('w');
            consultarReg();
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            return true;
        }

        public boolean afterConsultar()
        {
            cargarUbicacionCuenta();
            return true;
        }

        public boolean afterModificar()
        {
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
//            objTooBar.setEstado('w');
//            cargarReg();
            strFecDocIni=dtpFecDoc.getText();
            return true;
        }

        public boolean afterEliminar()
        {
            return true;
        }

        public boolean afterAnular()
        {
            return true;
        }

        public boolean afterImprimir()
        {
            return true;
        }

        public boolean afterVistaPreliminar()
        {
            return true;
        }

        public boolean afterAceptar()
        {
            return true;
        }
        
        public boolean afterCancelar()
        {
            return true;
        }
        
    }
    
    /**
     * Esta funcián determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        int intTipCamFec;
        String strFecDocTmp="";
        String strFecAuxTmp="";
        
        if (objTblMod.getRowCountTrue() == 0 || objTblMod.isCheckedAnyRow(INT_TBL_DAT_CHK) == false)
        {   tabFrm.setSelectedIndex(0);     
            mostrarMsgInf("No es posible grabar el documento sin detalle.\nSeleccione el detalle para poder emitir el pago.");                 
            return false;
        }
        
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        //Validar la "Cuenta".
        if (txtCodCta.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cuenta</FONT> es obligatorio.<BR>Escriba o seleccione una cuenta y vuelva a intentarlo.</HTML>");
            txtDesCorCta.requestFocus();
            return false;
        }
        //Validar el "Proveedor".
        if (txtCodPrv.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Proveedor</FONT> es obligatorio.<BR>Escriba o seleccione un proveedor y vuelva a intentarlo.</HTML>");
            txtCodPrv.requestFocus();
            return false;
        }
        //Validar el "Beneficiario".
        if (txtCodBen.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Beneficiario</FONT> es obligatorio.<BR>Escriba o seleccione un beneficiario y vuelva a intentarlo.</HTML>");
            txtCodBen.requestFocus();
            return false;
        }
        
        //Validar el "Numero alterno 1".
        if (txtNumDoc1.getText().equals(""))
        {   tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número alterno 1</FONT> es obligatorio.<BR>Verifique y vuelva a intentarlo.</HTML>");
            txtNumDoc1.requestFocus();
            return false;
        }
        
        if (objUti.isNumero(txtNumDoc1.getText()) == false)
        {  mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número alterno 1</FONT> debe ser numérico.<BR>Verifique y vuelva a intentarlo.</HTML>");
           tabFrm.setSelectedIndex(0);
           txtNumDoc1.requestFocus();
           return false;
        }
        
        //Validar el "Numero alterno 2".
        if (txtNumDoc2.getText().equals(""))
        {   tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número alterno 2</FONT> es obligatorio.<BR>Verifique y vuelva a intentarlo.</HTML>");
            txtNumDoc2.requestFocus();
            return false;
        }
        
        if (objUti.isNumero(txtNumDoc2.getText()) == false)
        {  mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número alterno 2</FONT> debe ser numérico.<BR>Verifique y vuelva a intentarlo.</HTML>");
           tabFrm.setSelectedIndex(0);
           txtNumDoc2.requestFocus();
           return false;
        }
        
        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        else{
            intTipCamFec=canChangeDate();
            switch(intTipCamFec){
                case 0://esto lo coloque en caso que el registro no se encuentre en tbr_tipDocUsr porque devuelve 0 la función.
                    if(objParSis.getCodigoUsuario()!=1){
                        if(objTooBar.getEstado()=='n'){//insertar
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            String strMsj="";
                            strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                            strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                            strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                            strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                            strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                            strMsj+="<BR>      El documento se guardará con fecha del día así ud. coloque otra fecha.";
                            strMsj+="<BR>  Está seguro que desea continuar?</HTML>";
                            //mostrarMsgInf("<HTML> " + strMsj + "</HTML>");
                            
                            switch (mostrarMsgCon(strMsj)){
                                case 0: //YES_OPTION
                                    System.out.println("POR YES");
                                    return true;
                                case 1: //NO_OPTION
                                    System.out.println("POR NO");
                                    return false;
                                case 2: //CANCEL_OPTION
                                    System.out.println("POR CANCEL");
                                    return false;
                            }
                            datFecAux=null;
                        }
                        else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            String strMsj="";
                            strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                            strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                            strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                            strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                            strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                            strMsj+="<BR>      El documento se guardará con la fecha inicialmente almacenada así ud. coloque otra fecha.";
                            strMsj+="<BR>  Está seguro que desea continuar?</HTML>";
                            //mostrarMsgInf("<HTML> " + strMsj + "</HTML>");
                            
                            switch (mostrarMsgCon(strMsj)){
                                case 0: //YES_OPTION
                                    return true;
                                case 1: //NO_OPTION
                                    return false;
                                case 2: //CANCEL_OPTION
                                    return false;
                            }
                            
                        }
                    }
                    break;
                case 1://no puede cambiarla para nada
                    if(objTooBar.getEstado()=='n'){//insertar
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecDocTmp="";strFecAuxTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                        strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) != 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permisos para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }
                    }
                    else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        strFecDocTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) != 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permiso para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }  
                    }
                    
                    break;
                case 2://la fecha puede ser menor o igual a la q se presenta
                    if(objTooBar.getEstado()=='n'){//insertar
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecDocTmp="";strFecAuxTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                        strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) > 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha ingresada en el documento es mayor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha posterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }
                    }
                    else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        strFecDocTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) > 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha de modificación del documento es mayor a la fecha ingresada inicialmente en el documento.<BR>Ud. no tiene permiso para colocar fecha posterior a la fecha ingresada inicialmente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }  
                    }
                    break;
                case 3://la fecha puede ser mayor o igual a la q se presenta
                    if(objTooBar.getEstado()=='n'){//insertar
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecDocTmp="";strFecAuxTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                        strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) < 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha ingresada en el documento es menor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha anterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }
                    }
                    else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        strFecDocTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) < 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha de modificación del documento es menor a la fecha ingresada inicialmente en el documento.<BR>Ud. no tiene permiso para colocar fecha anterior a la fecha ingresada inicialmente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }  
                    }
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        }
        
        //Validar el "Fecha de vencimiento".
        if (!dtpFecVen.isFecha())
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de vencimiento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha de vencimiento para el documento y vuelva a intentarlo.</HTML>");
            dtpFecVen.requestFocus();
            return false;
        }

        //Validar que el "Cádigo alterno" no se repita.
        if (!txtNumDoc1.getText().equals("")){
            strSQL="";
            strSQL+="SELECT a1.ne_numdoc1";
            strSQL+=" FROM tbm_cabPag AS a1 ";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
            strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
            strSQL+=" AND a1.ne_numdoc1='" + txtNumDoc1.getText().replaceAll("'", "''") + "'";
            strSQL+=" AND a1.st_reg<>'E'";
            if (objTooBar.getEstado()=='m')
                strSQL+=" AND a1.co_doc<>" + txtCodDoc.getText();
            if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL))
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El número de egreso <FONT COLOR=\"blue\">" + txtNumDoc1.getText() + "</FONT> ya existe.<BR>Escriba otro número de egreso y vuelva a intentarlo.</HTML>");
                txtNumDoc1.selectAll();
                txtNumDoc1.requestFocus();
                return false;
            }
        }
        
        //Validar que el "Diario está cuadrado".
        if (!objAsiDia.isDiarioCuadrado())
        {
            mostrarMsgInf("<HTML>El asiento de diario está <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
            return false;
        }
        //Validar que el "Monto del diario" sea igual al monto del documento.
        if (!objAsiDia.isDocumentoCuadrado(txtMonDoc.getText()))
        {
            mostrarMsgInf("<HTML>El valor del documento no coincide con el valor del asiento de diario.<BR>Cuadre el valor del documento con el valor del asiento de diario y vuelva a intentarlo.</HTML>");
            txtMonDoc.selectAll();
            txtMonDoc.requestFocus();
            return false;
        }
        //Validar que se cumplan los controles asociados al programa.
//        switch (objTooBar.getEstado())
//        {
//            case 'n': //Insertar.
//                switch (objAutPrg.validar())
//                {
//                    case 0: //Ocurriá una excepcián.
//                        return false;
//                    case 1: //El documento no necesita autorizacián.
//                        intAutDoc=1;
//                        break;
//                    case 2: //Solicitar autorizacián.
//                        intAutDoc=2;
//                        break;
//                    case 3: //Cancelar.
//                        return false;
//                }
//                break;
//            case 'm': //Modificar.
//                switch (objAutPrg.checkCtlsPag(objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
//                switch (objAutPrg.checkCtls("tbm_cabAutPag", "tbm_detAutPag", Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
//                {
//                    case -1: //Error.
//                        System.out.println("-1");
//                        return false;
//                    case 1: //Se pasaron todos los controles.
//                        System.out.println("1");
//                        intAutDoc=1;
//                        break;
//                    case 2: //Solicitar autorizacián.
//                        System.out.println("2");
//                        intAutDoc=2;
//                        break;
//                    case 3: //Cancelar.
//                        System.out.println("3");
//                        return false;
//                }
//                break;
//        }
        
        intAutDoc=1;
        return true;
    }
    
    
    /**
     * Esta función muestra un mensaje de confirmación de selección del banco al momento de ingresar el documento .
     * @return 0: Si se desea continuar con la inserción.
     * <BR>1: Si no se desea continuar
     * <BR>2: Si desea cancelar la inserción
     */
    private boolean isBanSelBie()
    {
        boolean blnRes=true;
        strAux="<HTML>El banco seleccionado es <FONT COLOR=\"blue\">" + txtDesLarCta.getText() + "</FONT>.<BR>Está seguro que desea continuar con la inserción del documento?</HTML>";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                blnRes=true;
                break;
            case 1: //NO_OPTION
                blnRes=false;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }
    
    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa utilizar
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

    /**
     * Esta funcián muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta funcián muestra un mensaje de advertencia al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifáquelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Esta función permite cargar los documentos que están pendientes de pago de un proveedor
     * @return true: Si se pudo cargar la información
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDocPen(){
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        String strFecVctCre="";
        int intCodEmpPen, intCodLocPen, intCodTipDocPen, intCodDocPen;
        BigDecimal bdeValPenDoc=new BigDecimal(0);
        try{
            objTooBar.setMenSis("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.tx_desCor, x.tx_desLar, x.co_doc, x.co_reg, x.ne_numDoc";
                strSQL+=" , x.fe_doc, x.ne_diaCre, x.fe_ven, x.mo_pag, x.nd_pen, x.tx_obs1, x.fe_venChqAutPag";
                strSQL+="  ,x.tx_natdoc FROM(";
                strSQL+=" SELECT distinct(a2.co_emp) AS co_emp, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc";
                strSQL+=" , a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen, a2.tx_obs1";
                strSQL+="  ,CASE WHEN a2.fe_venChqAutPag IS NULL THEN CURRENT_DATE";
                strSQL+="        ELSE a2.fe_venChqAutPag END AS fe_venChqAutPag";
                strSQL+="        ,a3.tx_natdoc";
                strSQL+="  FROM tbm_cabMovInv AS a1, tbm_pagMovInv AS a2, tbm_cabTipDoc AS a3, tbm_autPagMovInv AS a4";
                strSQL+="  WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="  AND a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+="  AND a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc AND a2.co_doc=a4.co_doc AND a2.co_reg=a4.co_reg";
                strSQL+=" AND a1.co_emp=" + intCodEmp + "";
                strSQL+=" AND a1.co_cli=" + txtCodPrv.getText() + "";
                strSQL+=" AND a1.co_ben=" + txtCodBen.getText() + "";
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)>0 ";//proveedores y clientes                    
                strSQL+=" AND (a2.nd_porRet=0 OR a2.nd_porRet IS NULL)";
                strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" AND a2.st_autPag IN ('S') AND a2.co_ctaAutPag=" + txtCodCta.getText() + "";
                if( (objParSis.getCodigoMenu()==574) )
                    strSQL+=" AND a3.ne_mod IN(2,4,5) ";//proveedores
                else if( (objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS )  )
                    strSQL+=" AND a3.ne_mod IN(1,3, 2,4,5) ";//clientes y proveedores
                else
                    strSQL+=" AND a3.ne_mod IN(1,3)";//clientes
                strSQL+=" ORDER BY a2.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                strSQL+=" ) AS x";
                strSQL+=" WHERE x.fe_venChqAutPag='" + objCon09_02.getFiltroFecha() + "'";

                if(  (objParSis.getCodigoMenu()==574) || (objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS)  ){
                    strSQL+=" UNION ";
                    strSQL+=" SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.tx_desCor, x.tx_desLar, x.co_doc, x.co_reg, x.ne_numDoc";
                    strSQL+="  , x.fe_doc, x.ne_diaCre, x.fe_ven, x.mo_pag, x.nd_pen, x.tx_obs1, x.fe_venChqAutPag";
                    strSQL+="  ,x.tx_natdoc FROM(";
                    strSQL+=" SELECT distinct(a2.co_emp) AS co_emp, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc";
                    strSQL+=" , a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen, a2.tx_obs1";

                    if(objCon09_02.getFiltroFecha().equals("")){
                        strSQL+=" ,CURRENT_DATE AS fe_venChqAutPag";
                    }
                    else{
                        strSQL+=" ,CAST('" + objCon09_02.getFiltroFecha() + "' AS DATE) AS fe_venChqAutPag";
                    }
                    strSQL+="        ,a3.tx_natdoc";
                    strSQL+="  FROM tbm_cabMovInv AS a1 INNER JOIN tbm_pagMovInv AS a2";
                    strSQL+="  ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a3";
                    strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                    strSQL+=" LEFT OUTER JOIN tbm_autPagMovInv AS a4";
                    strSQL+=" ON a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc AND a2.co_doc=a4.co_doc AND a2.co_reg=a4.co_reg";
                    strSQL+="  WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_cli=" + txtCodPrv.getText() + "";
                    if( (objParSis.getCodigoMenu()==574) ||  (objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS)  )
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<0 ";//proveedores
                    else
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)>0 ";//clientes
                    strSQL+=" AND (a2.nd_porRet=0 OR a2.nd_porRet IS NULL)";
                    strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                    strSQL+=" AND a2.st_reg IN ('A','C')";
                    if( (objParSis.getCodigoMenu()==574)  || (objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS) ){
                        strSQL+=" AND a3.ne_mod IN(2,4,5)";//proveedores
                        strSQL+=" AND a3.tx_natdoc='E' ";//proveedores
                    }
                    else{
                        strSQL+=" AND a3.ne_mod IN(1,3)";//clientes
                        strSQL+=" AND a3.tx_natdoc='I' ";//clientes
                    }

                    strSQL+=" ORDER BY a2.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                    strSQL+=" ) AS x";
                }
                System.out.println("cargar documentos pendientes: " + strSQL);

                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                objTooBar.setMenSis("Cargando datos...");
                while (rst.next()){
                    vecReg=new Vector();


                    intCodEmpPen=rst.getInt("co_emp");
                    intCodLocPen=rst.getInt("co_loc");
                    intCodTipDocPen=rst.getInt("co_tipDoc");
                    intCodDocPen=rst.getInt("co_doc");
                    bdeValPenDoc=rst.getBigDecimal("nd_pen");


                    vecReg.add(INT_TBL_DAT_LIN,         "");
                    vecReg.add(INT_TBL_DAT_CHK,         "");
                    vecReg.add(INT_TBL_DAT_COD_LOC,     "" + intCodLocPen);
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC, "" + intCodTipDocPen);
                    vecReg.add(INT_TBL_DAT_DEC_TIP_DOC, rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DEL_TIP_DOC, rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_COD_DOC,     "" + intCodDocPen);
                    vecReg.add(INT_TBL_DAT_COD_REG,     rst.getString("co_reg"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC,     rst.getString("ne_numDoc"));
                    vecReg.add(INT_TBL_DAT_FEC_DOC,     rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_DAT_DIA_CRE,     rst.getString("ne_diaCre"));
                    vecReg.add(INT_TBL_DAT_FEC_VEN,     rst.getString("fe_ven"));
                    vecReg.add(INT_TBL_DAT_VAL_DOC,     rst.getString("mo_pag"));
                    vecReg.add(INT_TBL_DAT_VAL_PEN,     "" + bdeValPenDoc);
                    vecReg.add(INT_TBL_DAT_AUX_ABO,     "");
                    vecReg.add(INT_TBL_DAT_AUX_NAT_DOC, rst.getString("tx_natdoc"));
                    
                    vecReg.add(INT_TBL_DAT_VAL_APL,     "" + bdeValPenDoc.subtract(valorAplicar(con, intCodEmpPen, intCodLocPen, intCodTipDocPen, intCodDocPen).abs()));
                    vecReg.add(INT_TBL_DAT_VAL_APL_USR, "");


                    vecDat.add(vecReg);
                }

                dtpFecVen.setText(objUti.formatearFecha(objCon09_02.getFiltroFecha(), "yyyy-MM-dd", "dd/MM/yyyy"));
//                switch(objParSis.getCodigoEmpresa()){
//                    case 1:
//                        if(txtCodCta.getText().equals("18"))
//                            dtpFecVen.setText(objUti.formatearFecha("2009-06-30", "yyyy-MM-dd", "dd/MM/yyyy"));
//                        if(txtCodCta.getText().equals("19"))
//                            dtpFecVen.setText(objUti.formatearFecha("2009-06-30", "yyyy-MM-dd", "dd/MM/yyyy"));
//                        if(txtCodCta.getText().equals("17"))
//                            dtpFecVen.setText(objUti.formatearFecha("2009-06-30", "yyyy-MM-dd", "dd/MM/yyyy"));
//                        break;
//                    case 2:
//                        if(txtCodCta.getText().equals("942"))
//                            dtpFecVen.setText(objUti.formatearFecha("2009-06-30", "yyyy-MM-dd", "dd/MM/yyyy"));
//                        if(txtCodCta.getText().equals("15"))
//                            dtpFecVen.setText(objUti.formatearFecha("2009-06-30", "yyyy-MM-dd", "dd/MM/yyyy"));
//                        break;
//                    case 4:
//                        if(txtCodCta.getText().equals("1802"))
//                            dtpFecVen.setText(objUti.formatearFecha("2009-06-30", "yyyy-MM-dd", "dd/MM/yyyy"));
//                        if(txtCodCta.getText().equals("1820"))
//                            dtpFecVen.setText(objUti.formatearFecha("2009-06-30", "yyyy-MM-dd", "dd/MM/yyyy"));
//                        break;
//                    default:
//                        break;
//                }

                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                objTooBar.setMenSis("Listo");


            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                switch (intAutDoc)
                {
                    case 1: //Se pasaron todos los controles.
                            if (insertarCab()){
                                if (insertarDet()){
                                    System.out.println("Aaaaaaaa");
                                    if (actualizarTbmPagMovInv(0)){
                                        objAsiDia.setCamTblUltDoc(strTblActNumDoc);
                                        if(strTblActNumDoc.equals("G"))
                                            objAsiDia.setCodTipDocGrp(strCodGrpTipDoc);
                                        if (objAsiDia.insertarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtNumDoc1.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"))){
                                            if(actualizaNumChqTbmPlaCta()){
                                                con.commit();
                                                blnRes=true;
                                            }
                                            else
                                                con.rollback();
                                        }
                                        else
                                            con.rollback();
                                    }
                                    else
                                        con.rollback();
                                }
                                else
                                    con.rollback();
                            }
                            else
                                con.rollback();                        

                        break;
                    case 2: //Solicitar autorizacián.
//                        if (insertarCab())
//                        {
//                            if (insertarDet())
//                            {
//                                int intClaPri[]={objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()),  Integer.parseInt(txtCodDoc.getText())};
//                                if (objAutPrg.insertarCabDetAut(con, intClaPri , 2))
//                                {
//                                    con.commit();
//                                    blnRes=true;
//                                }
//                                else
//                                    con.rollback();
//                            }
//                            else
//                                con.rollback();
//                        }
//                        else
//                            con.rollback();
                        break;
                }
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite consultar el registro insertado.
     * @return true: Si se pudo consultar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarRegIns()
    {
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            intLocCab=intCodLoc;
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" FROM tbm_cabPag AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND a1.co_doc=" + txtCodDoc.getText();
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontrá " + rstCab.getRow() + " registro");
                    objTooBar.setPosicionRelativa("1 / 1");
                    if (intAutDoc==2)
                        objTooBar.setEstadoRegistro(getEstReg("P"));
                    else
                        objTooBar.setEstadoRegistro(getEstReg("A"));
                    rstCab.first();
                    strSQLCon=strSQL;
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningán registro que cumpla el criterio de básqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg()
    {
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que sálo se muestre los documentos asignados al programa.
                if (txtCodTipDoc.getText().equals(""))
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_ven";
                    //strSQL+=" FROM tbm_cabPag AS a1";


                    strSQL+=" , a1.co_usrIng, a15.tx_usr AS tx_nomUsrIng";
                    strSQL+=" , a1.co_usrMod, a16.tx_usr AS tx_nomUsrMod";
                    strSQL+=" FROM (          (tbm_cabPag AS a1 INNER JOIN tbm_usr AS a15 ON a1.co_usrIng=a15.co_usr)";
                    strSQL+="                  INNER JOIN tbm_usr AS a16 ON a1.co_usrMod=a16.co_usr";
                    strSQL+="      )";


                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbm_plaCta AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                    strSQL+=" LEFT OUTER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                    strSQL+=" LEFT OUTER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a5.co_mnu=" + objParSis.getCodigoMenu();
                }
                else
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_ven";
                    //strSQL+=" FROM tbm_cabPag AS a1";

                    strSQL+=" , a1.co_usrIng, a15.tx_usr AS tx_nomUsrIng";
                    strSQL+=" , a1.co_usrMod, a16.tx_usr AS tx_nomUsrMod";
                    strSQL+=" FROM (          (tbm_cabPag AS a1 INNER JOIN tbm_usr AS a15 ON a1.co_usrIng=a15.co_usr)";
                    strSQL+="                  INNER JOIN tbm_usr AS a16 ON a1.co_usrMod=a16.co_usr";
                    strSQL+="      )";


                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbm_plaCta AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                    strSQL+=" LEFT OUTER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                }
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc = " + strAux + "";
                strAux=txtCodCta.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_cta = " + strAux + "";
                strAux=txtCodPrv.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_cli = " + strAux + "";
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                if (dtpFecVen.isFecha())
                    strSQL+=" AND a1.fe_ven='" + objUti.formatearFecha(dtpFecVen.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc = " + strAux + "";
                strAux=txtNumDoc1.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc1 = " + strAux.replaceAll("'", "''") + "";
                strAux=txtNumDoc2.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc2 = " + strAux.replaceAll("'", "''") + "";
                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" ORDER BY a1.co_loc, a1.co_tipDoc, a1.co_doc";
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                    strSQLCon=strSQL;
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                switch (intAutDoc)
                {
                    case 1: //Se pasaron todos los controles.
                        if (actualizarCab())
                        {
                            if (eliminarDet())
                            {
                                if (insertarDet())
                                {
                                    if (actualizarTbmPagMovInv(1))
                                    {
                                        if (objAsiDia.actualizarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumDoc1.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), "A"))
                                        {
                                            con.commit();
                                            blnRes=true;
                                        }
                                        else
                                            con.rollback();
                                    }
                                    else
                                        con.rollback();
                                }
                                else
                                    con.rollback();
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                        break;
                    case 2: //Solicitar autorizacián.
                        if (actualizarCab())
                        {
                            if (eliminarDet())
                            {
                                if (insertarDet())
                                {
                                    if (actualizarTbmPagMovInv(1))
                                    {
                                        if (objAsiDia.actualizarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumDoc1.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), "A"))
                                        {
                                            con.commit();
                                            blnRes=true;
                                        }
                                        else
                                            con.rollback();
                                    }
                                    else
                                        con.rollback();
                                }
                                else
                                    con.rollback();
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                        break;
                }
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián elimina el registro de la base de datos.
     * @return true: Si se pudo eliminar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (eliminarCab())
                {
                    if (actualizarTbmPagMovInv(2))
                    {
                        if (objAsiDia.eliminarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
                        {
                            con.commit();
                            blnRes=true;
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (anularCab())
                {
                    if (actualizarTbmPagMovInv(3))
                    {
                        if (objAsiDia.anularDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
                        {
                            con.commit();
                            blnRes=true;
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab()
    {
        int intCodUsr, intUltReg;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intCodUsr=objParSis.getCodigoUsuario();
                //Obtener el cádigo del áltimo registro.
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabPag AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabPag (co_emp, co_loc, co_tipDoc, co_doc, fe_doc, fe_ven, ne_numDoc1, ne_numDoc2, co_dia, co_cta, co_cli, tx_ruc";
                strSQL+=", tx_nomCli, tx_dirCli, co_ben, tx_benChq, nd_monDoc, tx_monDocPal, co_mnu, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod, tx_comIng, tx_comMod)";
                strSQL+=" VALUES (";
                strSQL+=objParSis.getCodigoEmpresa(); //co_emp
                strSQL+=", " + objParSis.getCodigoLocal(); //co_loc
                strSQL+=", " + txtCodTipDoc.getText(); //co_tipDoc
                strSQL+=", " + intUltReg; //co_doc
                strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_doc
                strSQL+=", '" + objUti.formatearFecha(dtpFecVen.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_ven
                strSQL+=", " + objUti.codificar(txtNumDoc1.getText(),2); //ne_numDoc1
                strSQL+=", " + objUti.codificar(txtNumDoc2.getText(),2); //ne_numDoc2
                strSQL+=", Null"; //co_dia
                strSQL+=", " + txtCodCta.getText(); //co_cta
                strSQL+=", " + objUti.codificar(txtCodPrv.getText(),2); //co_cli
                strSQL+=", " + objUti.codificar(strIdePrv); //tx_ruc
                strSQL+=", " + objUti.codificar(txtDesLarPrv.getText()); //tx_nomCli
                strSQL+=", " + objUti.codificar(strDirPrv); //tx_dirCli
                strSQL+=", " + objUti.codificar(txtCodBen.getText(),2); //co_ben
                strSQL+=", " + objUti.codificar(txtNomBen.getText()); //tx_benChq
                strSQL+=", " + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (intSig*Double.parseDouble(txtMonDoc.getText())):"0"),3); //nd_monDoc
                strSQL+=", " + objUti.codificar(txtMonDocPal.getText()); //tx_monDocPal
                strSQL+=", " + objParSis.getCodigoMenu(); //co_mnu
                strSQL+=", " + objUti.codificar(txaObs1.getText()); //tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText()); //tx_obs2
                //Autorizaciones.
                switch (intAutDoc)
                {
                    case 1: //Se pasaron todos los controles.
                        strSQL+=", 'A'"; //st_reg
                        break;
                    case 2: //Solicitar autorizacián.
                        strSQL+=", 'P'"; //st_reg
                        break;
                }
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'"; //fe_ing
                strSQL+=", '" + strAux + "'"; //fe_ultMod
                strSQL+=", " + intCodUsr; //co_usrIng
                strSQL+=", " + intCodUsr; //co_usrMod
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=")";
                System.out.println("INSERTAR CABECERA: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }

//            actualizarGlo();

        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDet()
    {
        int intCodEmp, intCodLoc, i, j;
        String strCodTipDoc, strCodDoc;
        boolean blnRes=true;
        int intInsDetAdi=0;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                strCodTipDoc=txtCodTipDoc.getText();
                strCodDoc=txtCodDoc.getText();
                j=1;
                
                for (i=0;i<objTblMod.getRowCountTrue();i++){
                    if (objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                        intInsDetAdi++;
                    }
                }
                
                if(intInsDetAdi==0){
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detPag (co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_locPag, co_tipDocPag, co_docPag";
                    strSQL+=", co_regPag, nd_abo, co_tipDocCon, co_banChq, tx_numCtaChq, tx_numChq, fe_recChq, fe_venChq)";
                    strSQL+=" VALUES (";
                    strSQL+="" + intCodEmp;
                    strSQL+=", " + intCodLoc;
                    strSQL+=", " + strCodTipDoc;
                    strSQL+=", " + strCodDoc;
                    strSQL+=", " + j;
                    strSQL+=", Null";
                    strSQL+=", Null";
                    strSQL+=", Null";
                    strSQL+=", Null";
                    strSQL+=", " + intSig*Double.parseDouble(objUti.codificar(txtMonDoc.getText(), 3));
                    strSQL+=", Null";
                    strSQL+=", Null";
                    strSQL+=", Null";
                    strSQL+=", Null";
                    strSQL+=", Null";
                    strSQL+=", Null";
                    strSQL+=")";
                    stm.executeUpdate(strSQL);
                }
                else{
                    for (i=0;i<objTblMod.getRowCountTrue();i++)
                    {
                        if (objTblMod.isChecked(i, INT_TBL_DAT_CHK))
                        {
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="INSERT INTO tbm_detPag (co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_locPag, co_tipDocPag, co_docPag";
                            strSQL+=", co_regPag, nd_abo, co_tipDocCon, co_banChq, tx_numCtaChq, tx_numChq, fe_recChq, fe_venChq)";
                            strSQL+=" VALUES (";
                            strSQL+="" + intCodEmp;
                            strSQL+=", " + intCodLoc;
                            strSQL+=", " + strCodTipDoc;
                            strSQL+=", " + strCodDoc;
                            strSQL+=", " + j;
                            strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC);
                            strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                            strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);
                            strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG);
                            strSQL+=", " + intSig*Double.parseDouble(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_VAL_APL_USR), 3));
                            strSQL+=", Null";
                            strSQL+=", Null";
                            strSQL+=", Null";
                            strSQL+=", Null";
                            strSQL+=", Null";
                            strSQL+=", Null";
                            strSQL+=")";
                            stm.executeUpdate(strSQL);
                            j++;
                        }
                    }
                }
                
                
                


//                System.out.println("EN EL DETALLE SE INSERTA: " +strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite actualizar la tabla "tbm_pagMovInv".
     * @return true: Si se pudo actualizar la tabla.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarTbmPagMovInv(int intTipOpe)
    {
        System.out.println("Aa");
        int intCodEmp, i;
        double dblAbo1, dblAbo2;
        boolean blnRes=true;
        try
        {
            if (!objTooBar.getEstadoRegistro().equals("Anulado"))
            {
                System.out.println("Bb");
                if (con!=null)
                {
                    System.out.println("Cc");
                    stm=con.createStatement();
                    intCodEmp=objParSis.getCodigoEmpresa();
                    
                    for (i=0;i<objTblMod.getRowCountTrue();i++)
                    {
                        System.out.println("Dd");
                        switch (intTipOpe)
                        {
                            
                            case 0:
                            case 1:
                                System.out.println("Ee");
                                if (objTblMod.getValueAt(i,INT_TBL_DAT_VAL_APL_USR)!=objTblMod.getValueAt(i,INT_TBL_DAT_AUX_ABO))
                                {
                                    System.out.println("Ff");
                                    dblAbo1=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_VAL_APL_USR));
                                    dblAbo2=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    //Armar la sentencia SQL.
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";
                                    strSQL+=" SET nd_abo=nd_abo+" + intSig*objUti.redondear((dblAbo1-dblAbo2),objParSis.getDecimalesBaseDatos());
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    System.out.println("actualizarTbmPagMovInv 0 y 1.- " +strSQL);
                                    stm.executeUpdate(strSQL);
                                    //objTblMod.setValueAt(objTblMod.getValueAt(i,INT_TBL_DAT_VAL_APL_USR),i,INT_TBL_DAT_AUX_ABO);
                                }
                                break;
                            case 2:
                            case 3:
                                System.out.println("Gg");
                                dblAbo1=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_VAL_APL_USR));
                                dblAbo2=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE tbm_pagMovInv";
                                //strSQL+=" SET nd_abo=nd_abo+" + intSig*(dblAbo2);
                                strSQL+=" SET nd_abo=nd_abo+" + intSig*(-dblAbo2);
                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                                strSQL+=" AND co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                strSQL+=" AND co_reg=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG);
                                System.out.println("actualizarTbmPagMovInv 2 y 3.- " +strSQL);
                                stm.executeUpdate(strSQL);
                                
                                break;
                                
                                
                        }
                    }
                    stm.close();
                    stm=null;
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite actualizar la tabla "tbm_plaCta".
     * @return true: Si se pudo actualizar la tabla.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizaNumChqTbmPlaCta(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="UPDATE tbm_plaCta";
                
                if(objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS)
                    strSQL+=" SET ne_ultNumTra=(ne_ultNumTra+1)";
                else
                    strSQL+=" SET ne_ultnumchq=(ne_ultnumchq+1)";                
                
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_cta=" + txtCodCta.getText() + "";
                strSQL+=";";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
                
    /**
     * Esta funcián permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            if (cargarCabReg())
            {
                if (cargarDetReg())
                {
                    objAsiDia.consultarDiario(objParSis.getCodigoEmpresa(), intLocCab, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                }
            }
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta funcián permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg()
    {
        int intPosRel;
        boolean blnRes=true;
        blnNecAutAnu=false;
        blnDocAut=false;
        
        try
        {
            intLocCab=rstCab.getInt("co_loc");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a1.co_doc, a1.fe_doc, a1.fe_ven, a1.ne_numDoc1";
                strSQL+=", a1.ne_numDoc2, a1.co_cta, a3.tx_codCta, a3.tx_desLar AS a3_tx_desLar, a1.co_cli, a1.tx_ruc, a1.tx_nomCli";
                strSQL+=", a1.tx_dirCli, a1.co_ben, a1.tx_benChq, a1.nd_monDoc, tx_monDocPal, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.st_imp";
                strSQL+=", a1.st_autAnu";
                strSQL+=" FROM tbm_cabPag AS a1";
                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" LEFT OUTER JOIN tbm_plaCta AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_natDoc");
                    intSig=(strAux.equals("I")?1:-1);
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    
                    strFecDocIni=dtpFecDoc.getText();
                    strEstImpDoc=rst.getString("st_imp");
                    
                    dtpFecVen.setText(objUti.formatearFecha(rst.getDate("fe_ven"),"dd/MM/yyyy"));
                    strAux=rst.getString("ne_numDoc1");
                    txtNumDoc1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("ne_numDoc2");
                    txtNumDoc2.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_cta");
                    txtCodCta.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_codCta");
                    txtDesCorCta.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("a3_tx_desLar");
                    txtDesLarCta.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_cli");
                    txtCodPrv.setText((strAux==null)?"":strAux);
                    strIdePrv=rst.getString("tx_ruc");
                    strAux=rst.getString("tx_nomCli");
                    txtDesLarPrv.setText((strAux==null)?"":strAux);
                    strDirPrv=rst.getString("tx_dirCli");
                    strAux=rst.getString("co_ben");
                    txtCodBen.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_benChq");
                    txtNomBen.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_monDocPal");
                    txtMonDocPal.setText((strAux==null)?"":strAux);
                    txtMonDoc.setText("" + Math.abs(rst.getDouble("nd_monDoc")));
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));

                    if((rst.getObject("st_autAnu")==null?"":rst.getString("st_autAnu")).equals("A"))
                        blnDocAut=true;
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }


                strSQL="";
                strSQL+="SELECT st_necAutAnuDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND st_necAutAnuDoc='S'";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    blnNecAutAnu=true;
                }




            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Mostrar la posicián relativa del registro.
            intPosRel=rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
//        System.out.println("cargarDetReg");
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        int intCodEmpPen, intCodLocPen, intCodTipDocPen, intCodDocPen;
        BigDecimal bdeValPenDoc=new BigDecimal(0);
        BigDecimal bdeValAboApl=new BigDecimal(0);
        try
        {
            objTblMod.removeAllRows();
            if (!txtCodPrv.getText().equals(""))
            {
//                objTooBar.setMenSis("Obteniendo datos...");
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {
                    stm=con.createStatement();
                    intCodEmp=objParSis.getCodigoEmpresa();
                    intCodLoc=intLocCab;
                    
                    
                    //Armar la sentencia SQL.
                    if (objTooBar.getEstado()=='x' || objTooBar.getEstado()=='m')
                    {
                        //Para el modo "Modificar" se muestra: documentos pendientes + documentos pagados.
                        strSQL="";
                        strSQL+="SELECT a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc, a2.tx_obs1";
                        strSQL+=", a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen, ABS(a4.nd_abo) AS nd_abo";
                        strSQL+=" ,a3.tx_natDoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1";
                        strSQL+=" LEFT OUTER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" LEFT OUTER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag";
                        strSQL+=" AND a4.co_emp=" + rstCab.getString("co_emp");
//                        strSQL+=" AND a4.co_loc=" + intCodLoc;
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc");
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + ")";
                        strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
//                        strSQL+=" AND a1.co_loc=" + intCodLoc;
                        strSQL+=" AND a1.co_cli=" + txtCodPrv.getText();
                        strSQL+=" AND ((a2.mo_pag+a2.nd_abo)>0 OR a4.nd_abo IS NOT NULL)";
                        strSQL+=" AND (a2.nd_porRet=0 or a2.nd_porRet is null)";
                        strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                        strSQL+=" AND a2.st_reg IN ('A','C')";
                        strSQL+=" AND a2.st_autPag IN ('S') AND a2.co_ctaAutPag=" + txtCodCta.getText() + "";
                        //strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";


                        if( (objParSis.getCodigoMenu()==574) ||  (objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS) ){
                            strSQL+=" UNION";
                            strSQL+=" SELECT x.co_loc, x.co_tipDoc, x.tx_desCor, x.tx_desLar, x.co_doc, x.co_reg, x.ne_numDoc, x.tx_obs1";
                            strSQL+=" , x.fe_doc, x.ne_diaCre, x.fe_ven, x.mo_pag, x.nd_pen, null";
                            strSQL+=" ,x.tx_natdoc FROM(";
                            strSQL+=" SELECT distinct(a2.co_emp) AS co_emp, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc";
                            strSQL+=" , a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen, a2.tx_obs1";
                            strSQL+=" ,CAST('" + objUti.formatearFecha(dtpFecVen.getText(),"dd/MM/yyyy","yyyy-MM-dd") + "' AS DATE) AS fe_venChqAutPag";
                            strSQL+="    ,a3.tx_natdoc";
                            strSQL+=" FROM tbm_cabMovInv AS a1 INNER JOIN tbm_pagMovInv AS a2";
                            strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+=" INNER JOIN tbm_cabTipDoc AS a3";
                            strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                            strSQL+=" LEFT OUTER JOIN tbm_autPagMovInv AS a4";
                            strSQL+=" ON a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc AND a2.co_doc=a4.co_doc AND a2.co_reg=a4.co_reg";
                            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+=" AND a1.co_cli=" + txtCodPrv.getText() + "";
                            if( (objParSis.getCodigoMenu()==574) || (objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS) )
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<0 ";//proveedores
                            else
                                strSQL+=" AND (a2.mo_pag+a2.nd_abo)>0 ";//clientes
                            strSQL+=" AND (a2.nd_porRet=0 OR a2.nd_porRet IS NULL)";
                            strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                            strSQL+=" AND a2.st_reg IN ('A','C')";
                            if(  (objParSis.getCodigoMenu()==574) ||  (objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS)  ){
                                
                                strSQL+=" AND a3.ne_mod IN(2,4,5)";//proveedores
                                strSQL+=" AND a3.tx_natdoc='E'";//proveedores
                                }
                            else{
                                strSQL+=" AND a3.ne_mod IN(1,3)";//clientes
                                strSQL+=" AND a3.tx_natdoc='I' ";//clientes
                                }
                            strSQL+=" ORDER BY a2.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                            strSQL+=" ) AS x";
                        }
                        strSQL+=" ORDER BY co_loc, co_tipDoc, co_doc, co_reg";
                    }
                    else
                    {
                        //Para los demás modos se muestra: sálo los documentos pagados.
                        strSQL="";
                        strSQL+="SELECT a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc, a2.tx_obs1";
                        strSQL+=", a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen, ABS(a4.nd_abo) AS nd_abo";
                        strSQL+=" ,a3.tx_natDoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1, tbm_pagMovInv AS a2, tbm_cabTipDoc AS a3, tbm_detPag AS a4";
                        strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+=" AND a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                        strSQL+=" AND a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag";
                        strSQL+=" AND a4.co_emp=" + rstCab.getString("co_emp");
                        strSQL+=" AND a4.co_loc=" + intCodLoc;
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc");
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc");
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg";
                    }
//                    System.out.println("EN CARGARDETREG: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
//                    objTooBar.setMenSis("Cargando datos...");
                    while (rst.next())
                    {
                        vecReg=new Vector();

                        intCodEmpPen=objParSis.getCodigoEmpresa();
                        intCodLocPen=rst.getInt("co_loc");
                        intCodTipDocPen=rst.getInt("co_tipDoc");
                        intCodDocPen=rst.getInt("co_doc");
                        bdeValPenDoc=rst.getBigDecimal("nd_pen");

                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_CHK,null);
                        vecReg.add(INT_TBL_DAT_COD_LOC,     "" + intCodLocPen);
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC, "" + intCodTipDocPen);
                        vecReg.add(INT_TBL_DAT_DEC_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DEL_TIP_DOC,rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,     "" + intCodDocPen);
                        vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_DIA_CRE,rst.getString("ne_diaCre"));
                        vecReg.add(INT_TBL_DAT_FEC_VEN,rst.getString("fe_ven"));
                        vecReg.add(INT_TBL_DAT_VAL_DOC,rst.getString("mo_pag"));
                        vecReg.add(INT_TBL_DAT_VAL_PEN,     "" + bdeValPenDoc);
                        strAux=rst.getString("nd_abo");
                        bdeValAboApl=rst.getObject("nd_abo")==null?new BigDecimal(0):rst.getBigDecimal("nd_abo");
                        vecReg.add(INT_TBL_DAT_AUX_ABO,strAux);

                        vecReg.add(INT_TBL_DAT_AUX_NAT_DOC,rst.getString("tx_natDoc"));

                        vecReg.add(INT_TBL_DAT_VAL_APL,bdeValPenDoc.subtract(valorAplicar(con, intCodEmpPen, intCodLocPen, intCodTipDocPen, intCodDocPen).abs()));
                        vecReg.add(INT_TBL_DAT_VAL_APL_USR,strAux);

                        if (strAux!=null)
                            vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_CHK);
                        vecDat.add(vecReg);
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    //Asignar vectores al modelo.
                    objTblMod.setData(vecDat);
                    tblDat.setModel(objTblMod);
                    vecDat.clear();
//                    objTooBar.setMenSis("Listo");
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCab()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabPag";
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=" SET fe_doc='" + strAux + "'";
                strAux=objUti.formatearFecha(dtpFecVen.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=", fe_ven='" + strAux + "'";
                strSQL+=", ne_numDoc1=" + objUti.codificar(txtNumDoc1.getText(),2);
                strSQL+=", ne_numDoc2=" + objUti.codificar(txtNumDoc2.getText(),2);
                strSQL+=", co_cta=" + txtCodCta.getText();
                strSQL+=", co_cli=" + objUti.codificar(txtCodPrv.getText(),2);
                strSQL+=", tx_ruc=" + objUti.codificar(strIdePrv);
                strSQL+=", tx_nomCli=" + objUti.codificar(txtDesLarPrv.getText());
                strSQL+=", tx_dirCli=" + objUti.codificar(strDirPrv);
                strSQL+=", co_ben=" + objUti.codificar(txtCodBen.getText(),2);
                strSQL+=", tx_benChq=" + objUti.codificar(txtNomBen.getText());
                strSQL+=", nd_monDoc=" + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (intSig*Double.parseDouble(txtMonDoc.getText())):"0"),3);
                strSQL+=", tx_monDocPal=" + objUti.codificar(txtMonDocPal.getText());
                strSQL+=", co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText());
                strSQL+=", tx_obs2=" + objUti.codificar(txaObs2.getText());
                //Autorizaciones.
                switch (intAutDoc)
                {
                    case 1: //Se pasaron todos los controles.
                        strSQL+=", st_reg='A'";
                        break;
                    case 2: //Solicitar autorizacián.
                        strSQL+=", st_reg='P'";
                        break;
                }
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián permite eliminar la cabecera de un registro.
     * @return true: Si se pudo eliminar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarCab()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabPag";
                strSQL+=" SET st_reg='E'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián permite eliminar el detalle de un registro.
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarDet()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE FROM tbm_detPag";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularCab()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabPag";
                strSQL+=" SET st_reg='I'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodCta.setText("");
            txtDesCorCta.setText("");
            txtDesLarCta.setText("");
            txtCodPrv.setText("");
            strIdePrv="";
            txtDesLarPrv.setText("");
            strDirPrv="";
            txtCodBen.setText("");
            txtNomBen.setText("");
            dtpFecDoc.setText("");
            dtpFecVen.setText("");
            txtCodDoc.setText("");
            txtNumDoc1.setText("");
            txtNumDoc2.setText("");
            txtMonDocPal.setText("");
            objTblMod.removeAllRows();
            txtMonDoc.setText("");
            objAsiDia.inicializar();
            txaObs1.setText("");
            txaObs2.setText("");
            strNumRetFacComSel="";
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            arlCam.add("tblActNumDoc");
            arlCam.add("a2.co_grpTipDoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Ref.Tbl.Act.");
            arlAli.add("Cod.Grp.Tbl.Tip.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, ";
                strSQL+=" CASE WHEN a2.ne_ultDoc IS NULL THEN a1.ne_ultDoc ELSE a2.ne_ultDoc END AS ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc";
                strSQL+=" ,CASE WHEN a2.ne_ultDoc IS NULL THEN 'L' ELSE 'G' END AS tblActNumDoc, a2.co_grpTipDoc";
                strSQL+=" FROM (tbm_cabTipDoc AS a1 LEFT OUTER JOIN tbm_cabGrpTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_grpTipDoc=a2.co_grpTipDoc)";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar,";
                strSQL+=" CASE WHEN a2.ne_ultDoc IS NULL THEN a1.ne_ultDoc ELSE a2.ne_ultDoc END AS ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc";
                strSQL+=" ,CASE WHEN a2.ne_ultDoc IS NULL THEN 'L' ELSE 'G' END AS tblActNumDoc, a2.co_grpTipDoc";
                strSQL+=" FROM tbr_tipDocUsr AS a3 inner join  (tbm_cabTipDoc AS a1 LEFT OUTER JOIN tbm_cabGrpTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_grpTipDoc=a2.co_grpTipDoc)";
                strSQL+=" ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a3.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario() + "";
            }


            //Ocultar columnas.
            int intColOcu[]=new int[3];
            intColOcu[0]=7;
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
            
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConCta()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a2.tx_ubiCta");
            arlCam.add("a1.st_aut");
            arlCam.add("a1.ne_ultnumchq");
            arlCam.add("a1.ne_ultnumtra");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cuenta");
            arlAli.add("Nombre");
            arlAli.add("Ubicación");
            arlAli.add("Autorización");
            arlAli.add("Número de Cheque");
            arlAli.add("Número de Transferencia");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=6;
            intColOcu[1]=7;
            vcoCta=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de cuentas", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoCta.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCta.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
            
            
            
            
            
            
            
            
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConPrv()
    {
        boolean blnRes=true;
        String strTitVen;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cádigo");
            arlAli.add("Identificacián");
            arlAli.add("Nombre");
            arlAli.add("Direccián");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
            strSQL+=" FROM tbm_cli AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            if (objParSis.getCodigoMenu() == 4215)
            {  //co_mnu 4215 = Pagos a clientes utilizando transferencias bancarias.
               strSQL+=" AND a1.st_cli='S'";
            }
            else if((objParSis.getCodigoMenu()==574)  || (objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS) )
                strSQL+=" AND a1.st_prv='S'";
            else
                strSQL+=" AND a1.st_cli='S'";

            strSQL+=" AND a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_nom";
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=4;

            if (objParSis.getCodigoMenu() == 4215)
            {  //co_mnu 4215 = Pagos a clientes utilizando transferencias bancarias.
               strTitVen = "Listado de clientes";
            }
            else
            {
               strTitVen = "Listado de proveedores";
            }
            vcoPrv=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, strTitVen, strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoPrv.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Beneficiarios".
     */
    private boolean configurarVenConBen()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_reg");
            arlCam.add("a1.tx_benChq");
            arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cádigo");
            arlAli.add("Beneficiario");
            arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
//            strSQL="";
//            strSQL+="SELECT a1.co_reg, a1.tx_benChq, a1.st_reg";
//            strSQL+=" FROM tbm_benChq AS a1";
//            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//            strSQL+=" AND a1.co_cli=" + txtCodPrv.getText();
//            strSQL+=" AND a1.st_reg IN ('A','P')";
//            strSQL+=" ORDER BY a1.co_reg";
            //Ocultar columnas.
//            int intColOcu[]=new int[1];
//            intColOcu[0]=1;
            vcoBen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de beneficiarios", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
//            intColOcu=null;
            //Configurar columnas.
            vcoBen.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoBen.setConfiguracionColumna(3, javax.swing.JLabel.CENTER);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a2.ne_ultDoc AS ne_ultDocGrp, a1.tx_natDoc,a2.co_grpTipDoc";
                    strSQL+=" FROM (tbm_cabTipDoc AS a1 LEFT OUTER JOIN tbm_cabGrpTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_grpTipDoc=a2.co_grpTipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocPrg";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a2.ne_ultDoc AS ne_ultDocGrp, a1.tx_natDoc,a2.co_grpTipDoc";
                    strSQL+=" FROM (tbm_cabTipDoc AS a1 LEFT OUTER JOIN tbm_cabGrpTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_grpTipDoc=a2.co_grpTipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocUsr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                }
                System.out.println("mostrarTipDocPre: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    String strNumDoc=rst.getObject("ne_ultDocGrp")==null?"":rst.getString("ne_ultDocGrp");
                    if(strNumDoc.equals("")){
                        txtNumDoc1.setText("" + (rst.getInt("ne_ultDoc")+1));
                        strTblActNumDoc="L";
                        strCodGrpTipDoc="";
                    }
                    else{
                        txtNumDoc1.setText("" + (rst.getInt("ne_ultDocGrp")+1));
                        strTblActNumDoc="G";
                        strCodGrpTipDoc=rst.getString("co_grpTipDoc");
                    }
                    intSig=(rst.getString("tx_natDoc").equals("I")?1:-1);
                    
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián muestra la cuenta contable predeterminado del programa 
     * de acuerdo al tipo de documento predeterminado.
     * @return true: Si se pudo mostrar la cuenta contable predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarCtaPre()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta, a1.st_aut, a1.ne_ultnumchq , a1.ne_ultnumTra";
                strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();

                System.out.println("ESTADO: " + objTooBar.getEstado());

                if(objTooBar.getEstado()=='n')
                    strSQL+=" AND a2.st_reg='S'";
//                else
//                    strSQL+=" AND a2.co_cta=" + txtCodCta.getText() + "";
                
                
                System.out.println("FALLO: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodCta.setText(rst.getString("co_cta"));
                    txtDesCorCta.setText(rst.getString("tx_codCta"));
                    txtDesLarCta.setText(rst.getString("tx_desLar"));
                    strUbiCta=rst.getString("tx_ubiCta");
                    strAutCta=rst.getString("st_aut");
                    if(objTooBar.getEstado()=='n'){
                        if(objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS)
                            txtNumDoc2.setText("" + (objUti.isNumero(rst.getString("ne_ultnumtra"))?Integer.parseInt(rst.getString("ne_ultnumtra"))+1:1));
                        else
                            txtNumDoc2.setText("" + (objUti.isNumero(rst.getString("ne_ultnumchq"))?Integer.parseInt(rst.getString("ne_ultnumchq"))+1:1));
                    }
                    
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián muestra el beneficiario predeterminado del cheque 
     * de acuerdo al proveedor seleccionado.
     * @return true: Si se pudo mostrar el beneficiario predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarBenPre()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_reg, a1.tx_benChq";
                strSQL+=" FROM tbm_benChq AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_cli=" + txtCodPrv.getText();
                strSQL+=" AND a1.st_reg='P'";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodBen.setText(rst.getString("co_reg"));
                    txtNomBen.setText(rst.getString("tx_benChq"));
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
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
                        strTblActNumDoc=vcoTipDoc.getValueAt(6);
                        strCodGrpTipDoc=vcoTipDoc.getValueAt(7);
                        mostrarCtaPre();
                        actualizarGlo();
                        txtNumDoc1.selectAll();
                        txtNumDoc1.requestFocus();
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
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
                        strTblActNumDoc=vcoTipDoc.getValueAt(6);
                        strCodGrpTipDoc=vcoTipDoc.getValueAt(7);
                        mostrarCtaPre();
                        actualizarGlo();
                        txtNumDoc1.selectAll();
                        txtNumDoc1.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
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
                            strTblActNumDoc=vcoTipDoc.getValueAt(6);
                            strCodGrpTipDoc=vcoTipDoc.getValueAt(7);
                            mostrarCtaPre();
                            actualizarGlo();
                            txtNumDoc1.selectAll();
                            txtNumDoc1.requestFocus();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
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
                        strTblActNumDoc=vcoTipDoc.getValueAt(6);
                        strCodGrpTipDoc=vcoTipDoc.getValueAt(7);
                        mostrarCtaPre();
                        actualizarGlo();
                        txtNumDoc1.selectAll();
                        txtNumDoc1.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
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
                            strTblActNumDoc=vcoTipDoc.getValueAt(6);
                            strCodGrpTipDoc=vcoTipDoc.getValueAt(7);
                            mostrarCtaPre();
                            actualizarGlo();
                            txtNumDoc1.selectAll();
                            txtNumDoc1.requestFocus();
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCta(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            //Validar que sálo se pueda seleccionar la "Cuenta" si se seleccioná el "Tipo de documento".
            if (txtCodTipDoc.getText().equals(""))
            {
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                txtDesLarCta.setText("");
                strUbiCta="";
                strAutCta="";
                mostrarMsgInf("<HTML>Primero debe seleccionar un <FONT COLOR=\"blue\">Tipo de documento</FONT>.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                txtDesCorTipDoc.requestFocus();
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta, a1.st_aut, a1.ne_ultnumchq, a1.ne_ultnumtra";
                strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" ORDER BY a1.tx_codCta";
                System.out.println("mostrarVenConCta:" + strSQL);

                System.out.println("NEFWNFKWF: " + objTooBar.getEstado());

                vcoCta.setSentenciaSQL(strSQL);
                switch (intTipBus)
                {
                    case 0: //Mostrar la ventana de consulta.
                        vcoCta.setCampoBusqueda(1);
                        vcoCta.show();
                        if (vcoCta.getSelectedButton()==vcoCta.INT_BUT_ACE)
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            strUbiCta=vcoCta.getValueAt(4);
                            strAutCta=vcoCta.getValueAt(5);
                            if(objTooBar.getEstado()=='n'){
                                if(objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS)
                                    txtNumDoc2.setText("" + (objUti.isNumero(vcoCta.getValueAt(7))?Integer.parseInt(vcoCta.getValueAt(7))+1:1));
                                else
                                    txtNumDoc2.setText("" + (objUti.isNumero(vcoCta.getValueAt(6))?Integer.parseInt(vcoCta.getValueAt(6))+1:1));
                            }
                            
                            mostrarNumeroCheque();
                            regenerarDiario();
                            actualizarGlo();
                            txtNumDoc1.selectAll();
                            txtNumDoc1.requestFocus();
                            objTblMod.removeAllRows();
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".
                        if (vcoCta.buscar("a1.tx_codCta", txtDesCorCta.getText()))
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            strUbiCta=vcoCta.getValueAt(4);
                            strAutCta=vcoCta.getValueAt(5);
                            if(objTooBar.getEstado()=='n'){
                                if(objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS)
                                    txtNumDoc2.setText("" + (objUti.isNumero(vcoCta.getValueAt(7))?Integer.parseInt(vcoCta.getValueAt(7))+1:1));
                                else
                                    txtNumDoc2.setText("" + (objUti.isNumero(vcoCta.getValueAt(6))?Integer.parseInt(vcoCta.getValueAt(6))+1:1));
                            }
                            mostrarNumeroCheque();
                            regenerarDiario();
                            actualizarGlo();
                            txtNumDoc1.selectAll();
                            txtNumDoc1.requestFocus();
                            objTblMod.removeAllRows();
                        }
                        else
                        {
                            vcoCta.setCampoBusqueda(0);
                            vcoCta.setCriterio1(11);
                            vcoCta.cargarDatos();
                            vcoCta.show();
                            if (vcoCta.getSelectedButton()==vcoCta.INT_BUT_ACE)
                            {
                                txtCodCta.setText(vcoCta.getValueAt(1));
                                txtDesCorCta.setText(vcoCta.getValueAt(2));
                                txtDesLarCta.setText(vcoCta.getValueAt(3));
                                strUbiCta=vcoCta.getValueAt(4);
                                strAutCta=vcoCta.getValueAt(5);
                                if(objTooBar.getEstado()=='n'){
                                    if(objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS)
                                        txtNumDoc2.setText("" + (objUti.isNumero(vcoCta.getValueAt(7))?Integer.parseInt(vcoCta.getValueAt(7))+1:1));
                                    else
                                        txtNumDoc2.setText("" + (objUti.isNumero(vcoCta.getValueAt(6))?Integer.parseInt(vcoCta.getValueAt(6))+1:1));
                                }
                                mostrarNumeroCheque();
                                regenerarDiario();
                                actualizarGlo();
                                txtNumDoc1.selectAll();
                                txtNumDoc1.requestFocus();
                                objTblMod.removeAllRows();
                            }
                            else
                            {
                                txtDesCorCta.setText(strDesCorCta);
                            }
                        }
                        break;
                    case 2: //Básqueda directa por "Descripcián larga".
                        if (vcoCta.buscar("a1.tx_desLar", txtDesLarCta.getText()))
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            strUbiCta=vcoCta.getValueAt(4);
                            strAutCta=vcoCta.getValueAt(5);
                            if(objTooBar.getEstado()=='n'){
                                if(objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS)
                                    txtNumDoc2.setText("" + (objUti.isNumero(vcoCta.getValueAt(7))?Integer.parseInt(vcoCta.getValueAt(7))+1:1));
                                else
                                    txtNumDoc2.setText("" + (objUti.isNumero(vcoCta.getValueAt(6))?Integer.parseInt(vcoCta.getValueAt(6))+1:1));
                            }
                            mostrarNumeroCheque();
                            regenerarDiario();
                            actualizarGlo();
                            txtNumDoc1.selectAll();
                            txtNumDoc1.requestFocus();
                            objTblMod.removeAllRows();
                        }
                        else
                        {
                            vcoCta.setCampoBusqueda(1);
                            vcoCta.setCriterio1(11);
                            vcoCta.cargarDatos();
                            vcoCta.show();
                            if (vcoCta.getSelectedButton()==vcoCta.INT_BUT_ACE)
                            {
                                txtCodCta.setText(vcoCta.getValueAt(1));
                                txtDesCorCta.setText(vcoCta.getValueAt(2));
                                txtDesLarCta.setText(vcoCta.getValueAt(3));
                                strUbiCta=vcoCta.getValueAt(4);
                                strAutCta=vcoCta.getValueAt(5);
                                if(objTooBar.getEstado()=='n'){
                                    if(objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS)
                                        txtNumDoc2.setText("" + (objUti.isNumero(vcoCta.getValueAt(7))?Integer.parseInt(vcoCta.getValueAt(7))+1:1));
                                    else
                                        txtNumDoc2.setText("" + (objUti.isNumero(vcoCta.getValueAt(6))?Integer.parseInt(vcoCta.getValueAt(6))+1:1));
                                }
                                mostrarNumeroCheque();
                                regenerarDiario();
                                actualizarGlo();
                                txtNumDoc1.selectAll();
                                txtNumDoc1.requestFocus();
                                objTblMod.removeAllRows();
                            }
                            else
                            {
                                txtDesLarCta.setText(strDesLarCta);
                            }
                        }
                        break;
                }

                

            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConPrv(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoPrv.setCampoBusqueda(2);
                    vcoPrv.show();
                    if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                        mostrarBenPre();
                        actualizarGlo();
                        objTblMod.removeAllRows();
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoPrv.buscar("a1.co_cli", txtCodPrv.getText()))
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                        mostrarBenPre();
                        actualizarGlo();
                        objTblMod.removeAllRows();
                    }
                    else
                    {
                        vcoPrv.setCampoBusqueda(0);
                        vcoPrv.setCriterio1(11);
                        vcoPrv.cargarDatos();
                        vcoPrv.show();
                        if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
                        {
                            txtCodPrv.setText(vcoPrv.getValueAt(1));
                            strIdePrv=vcoPrv.getValueAt(2);
                            txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                            strDirPrv=vcoPrv.getValueAt(4);
                            mostrarBenPre();
                            actualizarGlo();
                            objTblMod.removeAllRows();
                        }
                        else
                        {
                            txtCodPrv.setText(strCodPrv);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoPrv.buscar("a1.tx_nom", txtDesLarPrv.getText()))
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                        mostrarBenPre();
                        actualizarGlo();
                        objTblMod.removeAllRows();
                    }
                    else
                    {
                        vcoPrv.setCampoBusqueda(2);
                        vcoPrv.setCriterio1(11);
                        vcoPrv.cargarDatos();
                        vcoPrv.show();
                        if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
                        {
                            txtCodPrv.setText(vcoPrv.getValueAt(1));
                            strIdePrv=vcoPrv.getValueAt(2);
                            txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                            strDirPrv=vcoPrv.getValueAt(4);
                            mostrarBenPre();
                            actualizarGlo();
                            objTblMod.removeAllRows();
                        }
                        else
                        {
                            txtDesLarPrv.setText(strDesLarPrv);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConBen(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            //Validar que se pueda seleccionar el "Beneficiario" sálo si se seleccioná el "Proveedor".
            if (txtCodPrv.getText().equals(""))
            {
                txtCodBen.setText("");
                txtNomBen.setText("");
                mostrarMsgInf("<HTML>Primero debe seleccionar un <FONT COLOR=\"blue\">Proveedor</FONT>.<BR>Escriba o seleccione un proveedor y vuelva a intentarlo.</HTML>");
                txtCodPrv.requestFocus();
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_reg, a1.tx_benChq, a1.st_reg";
                strSQL+=" FROM tbm_benChq AS a1";
                strSQL+=" INNER JOIN tbm_cabMovInv AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli AND a1.co_reg=a2.co_ben";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc ";
                strSQL+=" AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_cli=" + txtCodPrv.getText();
                strSQL+=" AND a1.st_reg IN ('A','P')";
                strSQL+=" AND a3.st_reg IN('A','C') AND a3.st_autpag='S'";
                strSQL+=" GROUP BY a1.co_reg, a1.tx_benChq, a1.st_reg";
                strSQL+=" ORDER BY a1.co_reg";
                vcoBen.setSentenciaSQL(strSQL);
                switch (intTipBus)
                {
                    case 0: //Mostrar la ventana de consulta.
                        vcoBen.setCampoBusqueda(1);
                        vcoBen.show();
                        if (vcoBen.getSelectedButton()==vcoBen.INT_BUT_ACE)
                        {
                            txtCodBen.setText(vcoBen.getValueAt(1));
                            txtNomBen.setText(vcoBen.getValueAt(2));
                            actualizarGlo();
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".
                        if (vcoBen.buscar("a1.co_reg", txtCodBen.getText()))
                        {
                            txtCodBen.setText(vcoBen.getValueAt(1));
                            txtNomBen.setText(vcoBen.getValueAt(2));
                            actualizarGlo();
                        }
                        else
                        {
                            vcoBen.setCampoBusqueda(0);
                            vcoBen.setCriterio1(11);
                            vcoBen.cargarDatos();
                            vcoBen.show();
                            if (vcoBen.getSelectedButton()==vcoBen.INT_BUT_ACE)
                            {
                                txtCodBen.setText(vcoBen.getValueAt(1));
                                txtNomBen.setText(vcoBen.getValueAt(2));
                                actualizarGlo();
                            }
                            else
                            {
                                txtCodBen.setText(strCodBen);
                            }
                        }
                        break;
                    case 2: //Básqueda directa por "Descripcián larga".
                        if (vcoBen.buscar("a1.tx_benChq", txtNomBen.getText()))
                        {
                            txtCodBen.setText(vcoBen.getValueAt(1));
                            txtNomBen.setText(vcoBen.getValueAt(2));
                            actualizarGlo();
                        }
                        else
                        {
                            vcoBen.setCampoBusqueda(1);
                            vcoBen.setCriterio1(11);
                            vcoBen.cargarDatos();
                            vcoBen.show();
                            if (vcoBen.getSelectedButton()==vcoBen.INT_BUT_ACE)
                            {
                                txtCodBen.setText(vcoBen.getValueAt(1));
                                txtNomBen.setText(vcoBen.getValueAt(2));
                                actualizarGlo();
                            }
                            else
                            {
                                txtNomBen.setText(strNomBen);
                            }
                        }
                        break;
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    
    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algán cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
    }

    /**
     * Esta funcián se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodPrv.getDocument().addDocumentListener(objDocLis);
        txtDesLarPrv.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtNumDoc1.getDocument().addDocumentListener(objDocLis);
        txtNumDoc2.getDocument().addDocumentListener(objDocLis);
    }   

    /**
     * Esta funcián se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="áDesea guardar los cambios efectuados a áste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la informacián que no haya guardado.";
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
                blnHayCam=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }
    
    /**
     * Esta funcián calcula el abono total.
     */
    private void calcularAboTot()
    {
        double dblVal=0, dblTot=0;
        int intFilPro, i;
        String strConCel;
        try
        {
            intFilPro=objTblMod.getRowCount();
            for (i=0; i<intFilPro; i++)
            {
                strConCel=(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_USR)==null)?"":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_USR).toString();
                dblVal=(objUti.isNumero(strConCel))?Double.parseDouble(strConCel):0;
                dblTot+=dblVal;
            }
            //Calcular la diferencia.
            txtMonDoc.setText("" + objUti.redondeo(dblTot,objParSis.getDecimalesBaseDatos()));
            // txaObs1.setText(objUti.getCantidadPalabras(objUti.redondear(txtMonDoc.getText(), objParSis.getDecimalesMostrar()), "DOLARES"));
                  try{
                          Librerias.ZafUtil.ZafNumLetra numero;
                          double cantidad= objUti.redondear(txtMonDoc.getText(), objParSis.getDecimalesMostrar());
                          String decimales=String.valueOf(cantidad).toString();
                          decimales=decimales.substring(decimales.indexOf('.') + 1); 
                          decimales=(decimales.length()<2?decimales + "0":decimales.substring(0,2));
                          int deci= Integer.parseInt(decimales);
                          int m_pesos=(int)cantidad;
                          numero = new Librerias.ZafUtil.ZafNumLetra(m_pesos);
	                  String	res = numero.convertirLetras(m_pesos);
                          res = res+" "+decimales+"/100  DOLARES";
	                  res=res.toUpperCase(); 	
                          txtMonDocPal.setText(""+res);
                          numero=null;        
                    }catch(Exception e){  objUti.mostrarMsgErr_F1(this, e);  }
            
        }
        catch (NumberFormatException e)
        {
            txtMonDoc.setText("[ERROR]");
        }
    }

    /**
     * Esta funcián se utiliza para regenerar el asiento de diario. 
     * La idea principal de ásta funcián es regenerar el asiento de diario. Pero,
     * puede darse el caso en el que el asiento de diario fue modificado manualmente
     * por el usuario. En  cuyo caso, primero se pregunta si desea regenerar el asiento
     * de diario, no regenerarlo o cancelar.
     * @return true: Si se decidiá regenerar/no regenerar el asiento de diario.
     * <BR>false: Si se cancelá la regeneracián del asiento de diario.
     * Nota.- Como se puede apreciar la funcián retorna "false" sálo cuando se diá
     * click en el botán "Cancelar".
     */
    private boolean regenerarDiario()
    {
        boolean blnRes=true;
        if (objAsiDia.getGeneracionDiario()==2)
        {
            strAux="áDesea regenerar el asiento de diario?\n";
            strAux+="El asiento de diario ha sido modificado manualmente.";
            strAux+="\nSi desea que el sistema regenere el asiento de diario de click en SI.";
            strAux+="\nSi desea grabar el asiento de diario tal como está de click en NO.";
            strAux+="\nSi desea cancelar ásta operacián de click en CANCELAR.";
            switch (mostrarMsgCon(strAux))
            {
                case 0: //YES_OPTION
                    objAsiDia.setGeneracionDiario((byte)0);


                    if( (objParSis.getCodigoMenu()==2024) && (objParSis.getCodigoEmpresa()==2) )
                        objAsiDia.generarDiario(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodCta.getText()), txtMonDoc.getText(), Integer.parseInt(txtCodCta.getText()), txtMonDoc.getText());
                    else
                        objAsiDia.generarDiario(txtCodTipDoc.getText(), txtCodCta.getText(), txtMonDoc.getText(), txtMonDoc.getText());
                    break;
                case 1: //NO_OPTION
                    break;
                case 2: //CANCEL_OPTION
                    blnRes=false;
            }
        }
        else
        {
            objAsiDia.setGeneracionDiario((byte)0);
            if( (objParSis.getCodigoMenu()==2024) && (objParSis.getCodigoEmpresa()==2) )
                objAsiDia.generarDiario(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodCta.getText()), txtMonDoc.getText(), Integer.parseInt(txtCodCta.getText()), txtMonDoc.getText());
            else
                objAsiDia.generarDiario(txtCodTipDoc.getText(), txtCodCta.getText(), txtMonDoc.getText(), txtMonDoc.getText());
        }
        return blnRes;
    }

    /**
     * Esta funcián obtiene la descripcián larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripción larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la funcián devuelve una cadena vacáa.
     */
    private String getEstReg(String estado)
    {
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0))
            {
                case 'A':
                    estado="Activo";
                    break;
                case 'I':
                    estado="Anulado";
                    break;
                case 'P':
                    estado="Pendiente de autorizar";
                    break;
                case 'D':
                    estado="Autorizacián denegada";
                    break;
                case 'R':
                    estado="Pendiente de impresián";
                    break;
                case 'C':
                    estado="Pendiente confirmacián de inventario";
                    break;
                case 'F':
                    estado="Existen diferencias de inventario";
                    break;
                default:
                    estado="Desconocido";
                    break;
            }
        return estado;
    }
    
    /**
     * Esta funcián se utiliza para actualizar la glosa del asiento de diario.
     * Los usuarios necesitaban que aparecieran ciertos datos del documento en la glosa.
     */
    private void actualizarGlo()
    {
        strAux="";
        strAux+="Egreso:" + txtNumDoc1.getText();
        if(objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS)
            strAux+="; Transferencia: " + txtNumDoc2.getText();
        else
            strAux+="; Cheque: " + txtNumDoc2.getText();
        strAux+="; Cuenta: " + txtDesLarCta.getText();
        strAux+="; Beneficiario: " + txtNomBen.getText();


        if (objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHK)){
            strNumRetFacComSel+="; Doc: ";
            strNumRetFacComSel+=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_NUM_DOC) + ",";
            strNumRetFacComSel+="; Ret: ";
            obtieneNumeroRetencion(objParSis.getCodigoEmpresa(), Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_LOC).toString()), Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_TIP_DOC).toString()), Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_DOC).toString()) );
            strAux+="" + strNumRetFacComSel + ",";
        }
        else{
            strNumRetFacComSel="";
            for (int i=0; i<objTblMod.getRowCountTrue(); i++){
                if (objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    strAux+="; Doc: ";
                    strAux+=objTblMod.getValueAt(i, INT_TBL_DAT_NUM_DOC) + ",";
                    strAux+="; Ret: ";                    
                    strAux+="" + obtieneNumeroRetencion(objParSis.getCodigoEmpresa(), Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC).toString()), Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString()), Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC).toString()) ) + ",";
                    strNumRetFacComSel+=strAux;
                }
            }
            

        }
        objAsiDia.setGlosa(strAux);
    }
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podráa presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaráa informado en todo
     * momento de lo que ocurre. Si se desea hacer ásto es necesario utilizar ásta clase
     * ya que si no sálo se apreciaráa los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        int intCodEmp, intLoc, intTipDoc, intCodDia;
        
        public ZafThreadGUI(int empresa, int local, int tipoDocumento, int diario)
        {
            intCodEmp=empresa;
            intLoc=local;
            intTipDoc=tipoDocumento;
            intCodDia=diario;
        }
        
        public void run()
        {
//            if (!cargarDetReg(intCodEmp,intLoc,intTipDoc,intCodDia))
//            {
//                //Inicializar objetos si no se pudo cargar los datos.
////                lblMsgSis.setText("Listo");
////                pgrSis.setValue(0);
////                butCon.setText("Consultar");
//                System.out.println("Carga Ok...");
//            }
//            //Establecer el foco en el JTable sálo cuando haya datos.
//            if (tblDat.getRowCount()>0)
//            {
////                tabFrm.setSelectedIndex(1);
//                tblDat.setRowSelectionInterval(0, 0);
//                tblDat.requestFocus();
//            }
//            objThrGUI=null;
        }
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Codigo del Local";
                    break;
                case INT_TBL_DAT_DEC_TIP_DOC:
                    strMsg="Descripcián corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DEL_TIP_DOC:
                    strMsg="Descripcián larga del tipo de documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Námero de documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_DIA_CRE:
                    strMsg="Dáas de crádito";
                    break;
                case INT_TBL_DAT_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                case INT_TBL_DAT_VAL_PEN:
                    strMsg="Valor pendiente";
                    break;
                case INT_TBL_DAT_VAL_APL:
                    strMsg="Valor que se puede aplicar";
                    break;
                case INT_TBL_DAT_VAL_APL_USR:
                    strMsg="Valor a abonar";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase contiene todos los controles que debe cumplir el programa antes de
     * poder realizar una operacián. Por ejemplo, se podráa controlar que no se pueda
     * emitir cheques por valores menores a $10. Si el programa trata de grabar un
     * cheque por un valor menor a $10 apareceráa la ventana de "Autorizaciones" para
     * que el usuario decida si desea solicitar autorizacián o no.
     * <BR>Nota.- Las funciones deben devolver:
     * <UL>
     * <LI>true: Si se pasá el control.
     * <LI>false: Si no se pasá el control.
     * </UL>
     */
    public class ZafAutPrg extends Librerias.ZafAut.ZafAut
    {
        public ZafAutPrg(javax.swing.JInternalFrame ifr)
        {
            super(ifr,objParSis);
        }
        
        /**
         * Esta funcián determina si es necesaria una autorizacián para grabar el documento.
         * Por lo general al hacer un cheque no es necesaria autorizacián. Pero, hay ocasiones
         * en las que se puede establecer el campo "tbm_plaCta.st_aut" para que se solicite
         * autorizacián para poder emitir un cheque de una determinada cuenta bancaria.
         * @return true: Si el documento requiere autorizacián.
         * <BR>false: En el caso contrario.
         */
        public boolean isFecDocPer()
        {
            if (strAutCta.equals("S"))
                return false;
            else
                return true;
        }
        
        /**
         * Esta funcián determina si es necesaria una autorizacián para grabar el documento.
         * Por lo general al hacer un cheque no es necesaria autorizacián. Pero, hay ocasiones
         * en las que se puede establecer el campo "tbm_plaCta.st_aut" para que se solicite
         * autorizacián para poder emitir un cheque de una determinada cuenta bancaria.
         * @return true: Si el documento requiere autorizacián.
         * <BR>false: En el caso contrario.
         */
        public boolean validarAutCta()
        {
            if (strAutCta.equals("S"))
                return false;
            else
                return true;
        }
        
    }
     
    /**
     * Esta función obtiene el nombre de la empresa
     * se usa cuando se va a imprimir el documento(se la pasa por parámetro al reporte
     * @return auxTipDoc: El nombre de la empresa
     */
    public String retNomEmp(int codEmp){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";        
        try{            
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                que="";
                que+=" select tx_nom from tbm_emp";
                que+=" where co_emp=" + codEmp + "";                                                
//                System.out.println("el query del nombre de la empresa es:"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_nom");
                }
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }        
        return auxTipDoc;
        
    }        
     
    /**
     * Esta función obtiene la glosa del diario del documento
     * se usa cuando se va a imprimir el documento(se la pasa por parámetro al reporte
     * @return auxTipDoc: La glosa del documento
     */
    public String retGlosa(){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";        
        try{            
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                que="";
                que+="select distinct a1.tx_glo";
                que+=" from tbm_cabdia as a1, tbm_detdia as a2, tbm_placta as a3";
                que+=" where a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc";
                que+=" and a1.co_tipdoc=a2.co_tipdoc";
                que+=" and a1.co_dia=a2.co_dia";
                que+=" and a2.co_emp=a3.co_emp";
                que+=" and a2.co_cta=a3.co_cta";
                que+=" and a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                que+=" and a1.co_loc=" + objParSis.getCodigoLocal() + "";
                que+=" and a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                que+=" and a1.co_dia=" + txtCodDoc.getText() +"";                
                
//                System.out.println("la glosa es:"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_glo");
                }
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }        
        return auxTipDoc;
        
    }        
    
    /**
     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes=true;
        strAux="";
        String strSQLRep="", strSQLSubRep="";
        Statement stmIns;
        try
        {
            if(conCab!=null){
                objRptSis.cargarListadoReportes();
                objRptSis.setVisible(true);
                if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE){
                    //Obtener la fecha y hora del servidor.
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux==null)
                        return false;
                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MM/yyyy   HH:mm:ss");
                    datFecAux=null;
                    intNumTotRpt=objRptSis.getNumeroTotalReportes();
                    for (i=0;i<intNumTotRpt;i++){
                        if (objRptSis.isReporteSeleccionado(i)){
                            switch (Integer.parseInt(objRptSis.getCodigoReporte(i))){
                                case 0:
                                default:
                                    strSQL="";
                                    strSQL+="SELECT a4.ne_numDoc1 AS ne_numEgr, a5.tx_nom AS tx_nomEmp, a4.fe_doc, ABS(a4.nd_monDoc) AS nd_monDoc";
                                    strSQL+=" ,a4.co_cli, a4.tx_benchq, a7.tx_glo";
                                    strSQL+=" ,a4.tx_benchq,a4.tx_mondocpal ,  a6.tx_usr";
                                    strSQL+=" ,a9.tx_codCta, a9.tx_desLar AS tx_nomCta, a4.ne_numDoc2 AS ne_numChq, a4.co_mnu";
                                    strSQL+=" FROM ((tbm_cabpag as a4 INNER JOIN tbm_emp AS a5 ON a4.co_emp=a5.co_emp)";
                                    strSQL+=" INNER JOIN tbm_usr as a6 on(a4.co_usring=a6.co_usr)";
                                    strSQL+=" INNER JOIN tbm_cabDia AS a7";
                                    strSQL+=" ON a4.co_emp=a7.co_emp AND a4.co_loc=a7.co_loc AND a4.co_tipdoc=a7.co_tipDoc AND a4.co_doc=a7.co_dia)";
                                    strSQL+=" INNER JOIN tbm_plaCta AS a9 ON a4.co_emp=a9.co_emp AND a4.co_cta=a9.co_cta";
                                    strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                                    strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";
                                    strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                                    strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                                    strSQL+=" AND a9.st_ctaBan='S'";
                                    strSQLRep=strSQL;
                                    System.out.println("strSQLRep: " + strSQLRep);

                                    strSQL="";
                                    strSQL+="select a3.tx_codcta, a3.tx_deslar, round(a2.nd_mondeb,2) as nd_mondeb, round(a2.nd_monhab,2) as nd_monhab";
                                    strSQL+=" from tbm_cabdia as a1, tbm_detdia as a2, tbm_placta as a3";
                                    strSQL+=" where a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc";
                                    strSQL+=" and a1.co_tipdoc=a2.co_tipdoc";
                                    strSQL+=" and a1.co_dia=a2.co_dia";
                                    strSQL+=" and a2.co_emp=a3.co_emp";
                                    strSQL+=" and a2.co_cta=a3.co_cta";
                                    strSQL+=" and a1.co_emp=" + rstCab.getString("co_emp") + "";
                                    strSQL+=" and a1.co_loc=" + rstCab.getString("co_loc") + "";
                                    strSQL+=" and a1.co_tipdoc=" + rstCab.getString("co_tipDoc") + "";
                                    strSQL+=" and a1.co_dia=" + rstCab.getString("co_doc") + "";
                                    strSQLSubRep=strSQL;
                                    System.out.println("strSQLSubRep: " + strSQLSubRep);

                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);

                                    System.out.println("strRutRpt: " + strRutRpt);
                                    System.out.println("strNomRpt: " + strNomRpt);



                                    //Inicializar los parametros que se van a pasar al reporte.


                                    java.util.Map mapPar = new java.util.HashMap();
                                    mapPar.put("strCamAudRpt", "" + (rstCab.getString("tx_nomUsrIng") + " / " + rstCab.getString("tx_nomUsrMod") + " / " + objParSis.getNombreUsuario()) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ");
                                    //mapPar.put("strCamAudRpt", "");//sirve para que no salga la linea

                                    mapPar.put("codEmp", new Integer(rstCab.getInt("co_emp")));
                                    mapPar.put("codLoc", new Integer(rstCab.getInt("co_loc")));
                                    mapPar.put("codTipDoc", new Integer(rstCab.getInt("co_tipDoc")));
                                    mapPar.put("codDoc", new Integer(rstCab.getInt("co_doc")));

                                    //mapPar.put("fe_chq", objUti.formatearFecha(rstCab.getString("fe_ven"), "yyyy-MM-dd", "dd 'de' MMMMM 'de' yyyy") );
                                    mapPar.put("fe_chq", objUti.formatearFecha(rstCab.getString("fe_ven"), "yyyy-MM-dd", "yyyy/MM/dd") );
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRep", strSQLSubRep);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);


                                    System.out.println("strRutRpt: " + strRutRpt);
                                    System.out.println("strNomRpt: " + strNomRpt);
                                    System.out.println("intTipRpt: " + intTipRpt);


                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);

                                    stmIns=conCab.createStatement();
                                    strSQL="";
                                    strSQL+="UPDATE tbm_cabPag";
                                    strSQL+=" SET st_imp='S'";
                                    strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp") + "";
                                    strSQL+=" AND co_loc=" + rstCab.getString("co_loc") + "";
                                    strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                                    strSQL+=" AND co_doc=" + rstCab.getString("co_doc") + "";
                                    strSQL+=";";
                                    strSQL+="UPDATE tbm_cabDia";
                                    strSQL+=" SET st_imp='S'";
                                    strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp") + "";
                                    strSQL+=" AND co_loc=" + rstCab.getString("co_loc") + "";
                                    strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                                    strSQL+=" AND co_dia=" + rstCab.getString("co_doc") + "";
                                    stmIns.executeUpdate(strSQL);

                                    stmIns.close();
                                    stmIns=null;

                                    strEstImpDoc="S";

                                    break;


                            }
                        }
                    }
                }
            }

        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }



    /**
     * Esta función obtiene el tipo de modificación de fecha que el usurio puede realizar en el tipo de documento
     * <BR>Nota.- Los valores que puede tener son:
     * <UL>
     * <LI>1: No se puede modificar la fecha el tipo de documento
     * <LI>2: Se puede modificar la fecha hacia atrás
     * <LI>3: Se puede modificar la fecha hacia adelante
     * <LI>4: Se puede modificar la fecha
     * </UL>
     * @return intTipModFec: El tipo de modificación de fecha que el usuario puede realizar en el tipo de documento
     */
    private int canChangeDate(){
        Connection conChaDat;
        Statement stmChaDat;
        ResultSet rstChaDat;
        int intTipModFec=0;
        try{
            conChaDat=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conChaDat!=null){
                stmChaDat=conChaDat.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    intTipModFec=4;
                }
                else{
                    strSQL="";
                    strSQL+="SELECT ne_tipresmodfecdoc";
                    strSQL+=" FROM tbr_tipdocusr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                    rstChaDat=stmChaDat.executeQuery(strSQL);
                    while(rstChaDat.next()){
                        intTipModFec=rstChaDat.getInt("ne_tipresmodfecdoc");
                    }
                    
                    stmChaDat.close();
                    stmChaDat=null;
                    rstChaDat.close();
                    rstChaDat=null;
                }
                conChaDat.close();
                conChaDat=null;

            }
        }
        
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intTipModFec;
        
    }    

    /**
     * Esta función obtiene el tipo de modificación que el usurio puede realizar en el tipo de documento
     * <BR>Nota.- Los valores que puede tener son:
     * <UL>
     * <LI>1: No se puede modificar el tipo de documento
     * <LI>2: Se puede modificar el tipo de documento si no ha sido impreso todavía
     * <LI>3: El documento se puede modificar
     * </UL>
     * @return intTipModTipDocUsr: El tipo de modificación que el usuario puede realizar en el tipo de documento
     */
    private int canTipoModificacion(){
        Connection conChaDat;
        Statement stmChaDat;
        ResultSet rstChaDat;
        int intTipModTipDocUsr=0;
        try{
            conChaDat=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conChaDat!=null){
                stmChaDat=conChaDat.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    intTipModTipDocUsr=3;
                }
                else{
                    strSQL="";
                    strSQL+="SELECT ne_tipresmoddoc";
                    strSQL+=" FROM tbr_tipdocusr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                    rstChaDat=stmChaDat.executeQuery(strSQL);
                    while(rstChaDat.next()){
                        intTipModTipDocUsr=rstChaDat.getInt("ne_tipresmoddoc");
                    }
                    stmChaDat.close();
                    stmChaDat=null;
                    rstChaDat.close();
                    rstChaDat=null;
                }
                conChaDat.close();
                conChaDat=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intTipModTipDocUsr;
        
    }

    /**
     * Esta función permite inactivar los campos del formulario
     * de acuerdo al tipo de permiso que tenga el usuario para modificar el tipo de documento
     * @return true: Si se realizó con éxito la conexión a la base de datos
     * <BR>false: En el caso contrario.
     */
    private boolean camposInactivosPermisoModifi(){
        boolean blnRes=true;
        try{
            int intTipModDoc;
            String strFecDocTmp;
            intTipModDoc=canTipoModificacion();
            switch(intTipModDoc){
                case 0://esto lo coloque en caso que el registro no se encuentre en tbr_tipDocUsr porque devuelve 0 la función.
                    if(objParSis.getCodigoUsuario()!=1){
                        if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                            txtCodBen.setEditable(false);
                            txtNomBen.setEditable(false);
                            butBen.setEnabled(false);
                            dtpFecDoc.setEnabled(false);
                            txtNumDoc1.setEditable(false);
                            txtNumDoc1.setBackground(new java.awt.Color(255, 255, 255));
                            txtNumDoc2.setEditable(false);
                            txtNumDoc2.setBackground(new java.awt.Color(255, 255, 255));                        
                            txaObs1.setEditable(false);
                            txaObs2.setEditable(false);
                            txtMonDocPal.setEditable(false);
                            txtMonDocPal.setBackground(new java.awt.Color(255, 255, 255));
                            objAsiDia.setEditable(false);
                            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                            if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                            }
                            else{
                                String strMsj="";
                                strMsj+="<HTML>EL documento no se puede modificar por las siguientes razones:";
                                strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                                strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                                strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                                strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios al documento.";
                                strMsj+="</HTML>";
                                mostrarMsgInf(strMsj);
                            }

                        }
                    }
                    break;
                case 1://no puede modificar nada, incluyendo fecha del documento
                    if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        txtCodBen.setEditable(false);
                        txtNomBen.setEditable(false);
                        butBen.setEnabled(false);
                        dtpFecDoc.setEnabled(false);
                        txtNumDoc1.setEditable(false);
                        txtNumDoc1.setBackground(new java.awt.Color(255, 255, 255));
                        txtNumDoc2.setEditable(false);
                        txtNumDoc2.setBackground(new java.awt.Color(255, 255, 255));                        
                        txaObs1.setEditable(false);
                        txaObs2.setEditable(false);
                        txtMonDocPal.setEditable(false);
                        txtMonDocPal.setBackground(new java.awt.Color(255, 255, 255));
                        objAsiDia.setEditable(false);
                        objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                        if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                        }
                        else
                            mostrarMsgInf("<HTML>Ud. no cuenta con ningún tipo de permiso para Modificar.<BR>.Solicite a su Adminsitrador del Sistema dicho permiso y vuelva a intentarlo.</HTML>");
                    }
                    
                    break;
                case 2://modificación parcial la modificación de la fecha dependerá de si se cuenta con este permiso
                    if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        if( ! strEstImpDoc.equals("S")){//si el documento no está impreso se puede modificar, la modif. de fecha depende de tbr_tipDocUsr.ne_tipresmodfecdoc
                            dtpFecDoc.setEnabled(true);
                            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                        }
                        else{//si el documento está impreso no se permite modificar
                            dtpFecDoc.setEnabled(false);
                            txtCodBen.setEditable(false);
                            txtNomBen.setEditable(false);
                            butBen.setEnabled(false);
                            dtpFecDoc.setEnabled(false);
                            txtNumDoc1.setEditable(false);
                            txtNumDoc1.setBackground(new java.awt.Color(255, 255, 255));
                            txtNumDoc2.setEditable(false);
                            txtNumDoc2.setBackground(new java.awt.Color(255, 255, 255));                        
                            txaObs1.setEditable(false);
                            txaObs2.setEditable(false);
                            txtMonDocPal.setEditable(false);
                            txtMonDocPal.setBackground(new java.awt.Color(255, 255, 255));
                            objAsiDia.setEditable(false);
                            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                            if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                            }
                            else
                                mostrarMsgInf("<HTML>El documento consultado no se puede modificar porque ya está impreso.</HTML>");
                        }
                    }
                    break;
                case 3://modificación completa, la modificación de la fecha dependerá de si se cuenta con este permiso
                    dtpFecDoc.setEnabled(true);
                    break;
                default:
                    break;
            }
            objTooBar.setOperacionSeleccionada("n");
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función verifica el estado de impresión del documento
     * @return true: Si el documento ya fue impreso
     * <BR>false: En el caso contrario.
     */
    private boolean puedeImprimir(Connection conImprimir){
        boolean blnRes=false;
        Statement stmImp;
        ResultSet rstImp;
        try{
            if(conImprimir!=null){
                stmImp=conImprimir.createStatement();
                strSQL="";
                strSQL+="SELECT st_imp FROM tbm_cabPag";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                strSQL+=" AND st_reg NOT IN('I','E')";
                strSQL+=" AND st_imp='N'";
                rstImp=stmImp.executeQuery(strSQL);
                if(rstImp.next()){
                    blnRes=true;
                }
                stmImp.close();
                stmImp=null;
                rstImp.close();
                rstImp=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función muestra el número de cheque asociado a la cuenta bancaria y a la empresa y local
     * Se coloca el valor obtenido en el query directamente en el campor del formulario
     * @return true: Si la consulta se ejecutó correctamente
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarNumeroCheque(){
        boolean blnRes=true;
        Connection conNumChq;
        try
        {
            conNumChq=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conNumChq!=null)
            {
                stm=conNumChq.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.ne_ultnumchq, a1.ne_ultnumtra";
                strSQL+=" FROM tbm_plaCta AS a1";
                strSQL+=" WHERE ";
                strSQL+=" a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_cta=" + vcoCta.getValueAt(1);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    if(objParSis.getCodigoMenu()==INT_COD_MNU_EGR_TRS)
                        txtNumDoc2.setText("" + (objUti.isNumero(rst.getString("ne_ultnumtra"))?Integer.parseInt(rst.getString("ne_ultnumtra"))+1:1));
                    else
                        txtNumDoc2.setText("" + (objUti.isNumero(rst.getString("ne_ultnumchq"))?Integer.parseInt(rst.getString("ne_ultnumchq"))+1:1));
                }
                rst.close();
                stm.close();
                conNumChq.close();
                rst=null;
                stm=null;
                conNumChq=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función verifica la naturaleza del documento y con ello se obtiene si existen documentos pendientes de cruzar
     * @return true: Si existe algún documento pendiente de cruzar
     * <BR>false: En el caso contrario.
     */
    private boolean existeDocumentosCruzar(){
        boolean blnRes=false;
        int intNumNotCre=0;
        String strNatDoc="";
        try{
            for(int i=0;i<objTblMod.getRowCountTrue();i++){
                strNatDoc=objTblMod.getValueAt(i, INT_TBL_DAT_AUX_NAT_DOC)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_AUX_NAT_DOC).toString();
                if(strNatDoc.equals("E")){
                    intNumNotCre++;
                }
            }
            if(intNumNotCre>0){
                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }

    /**
     * Esta función obtiene el valor que se puede aplicar al documento
     * @return bdeValApl: El valor a aplicar
     */
    private BigDecimal valorAplicar(Connection conexion, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento){
        boolean blnRes=true;
        Statement stmValApl;
        ResultSet rstValApl;
        BigDecimal bdeValApl=new BigDecimal(0);
        try{
            if(conexion!=null){
                stmValApl=conexion.createStatement();
                strSQL="";
                strSQL+="SELECT (b2.mo_pag + b2.nd_abo) AS nd_valApl";
                strSQL+=" FROM tbr_cabMovInv AS b1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS b2";
                strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc";
                strSQL+=" AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                strSQL+=" WHERE b1.co_emp=" + codigoEmpresa + "";
                strSQL+=" AND b1.co_locRel=" + codigoLocal + "";
                strSQL+=" AND b1.co_tipDocRel=" + codigoTipoDocumento + "";
                strSQL+=" AND b1.co_docRel=" + codigoDocumento + "";
                strSQL+=" AND b1.co_tipDoc=4";
                strSQL+=" AND b1.st_reg NOT IN('I','E')";
                strSQL+=" AND (b2.mo_pag+b2.nd_abo)<0";
                strSQL+=" AND (b2.nd_porRet=0 OR b2.nd_porRet IS NULL)";
                strSQL+=" AND b2.st_reg IN ('A','C')";
                rstValApl=stmValApl.executeQuery(strSQL);
                if(rstValApl.next())
                    bdeValApl=rstValApl.getBigDecimal("nd_valApl");
                else
                    bdeValApl=new BigDecimal(0);
                stmValApl.close();
                stmValApl=null;
                rstValApl.close();
                rstValApl=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return bdeValApl;
    }

    /**
     * Esta función permite saber si el pago seleccionado es mayor al pago que se puede aplicar
     * @return true: Si el valor seleccionado excede al que se puede aplicar
     * <BR>false: En el caso contrario.
     */
    private boolean valorPagoExcede(){
        boolean blnRes=false;
        int intNumExc=0;
        BigDecimal bdeValApl=new BigDecimal(0);
        BigDecimal bdeValAplUsr=new BigDecimal(0);
        try{
            for(int i=0;i<objTblMod.getRowCountTrue();i++){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    bdeValApl=objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL)==null?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL).toString());
                    bdeValAplUsr=objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_USR)==null?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_USR).toString());
                    if(bdeValApl.compareTo(bdeValAplUsr)<0){
                        intNumExc++;
                        break;
                    }
                }
            }
            if(intNumExc>0){
                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }

    /**
     * Esta función permite saber si el abono que se está seleccionando o digitando
     * es un valor positivo(en la mayoría de los casos los valores negativos es por notas de crédito
     * y estas deben ser cruzadas y no generadas cheques.)
     * @return true: Si existe algún valor negativo seleccionado
     * <BR>false: En el caso contrario.
     */
    private boolean existePagoNegativoCero(){
        boolean blnRes=false;
        int intNumExc=0;
        BigDecimal bdeValAplUsr=new BigDecimal(0);
        try{
            for(int i=0;i<objTblMod.getRowCountTrue();i++){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    bdeValAplUsr=objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_USR)==null?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_USR).toString());
                    if(bdeValAplUsr.compareTo(new BigDecimal(0))<=0){
                        intNumExc++;
                        break;
                    }
                }
            }
            if(intNumExc>0){
                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }

     /**
     * Esta función permite saber si el abono que se está seleccionando o digitando
     * es mayor al valor que se puede aplicar, esto se da por notas de crédito asociadas
     * y que al aplicarlas da un valor menor al valor adeudado.
     * @return true: Si el pago es superior
     * <BR>false: En el caso contrario.
     */
    private boolean existePagoSuperior(){
        boolean blnRes=false;
        int intNumExc=0;
        BigDecimal bdeValAboAux=new BigDecimal(0);
        BigDecimal bdeValApl=new BigDecimal(0);
        BigDecimal bdeValAplUsr=new BigDecimal(0);

        try{
            for(int i=0;i<objTblMod.getRowCountTrue();i++){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    bdeValAboAux=objTblMod.getValueAt(i, INT_TBL_DAT_AUX_ABO)==null?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_AUX_ABO).toString());
                    bdeValApl=objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL)==null?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL).toString());
                    bdeValAplUsr=objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_USR)==null?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_APL_USR).toString());
                    if((bdeValApl.add(bdeValAboAux)).compareTo(bdeValAplUsr)<0){
                        intNumExc++;
                        break;
                    }
                }
            }
            if(intNumExc>0){
                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }

    /**
     * Esta función permite obtener la ubicación de la cuenta asociada al Tipo de documento.
     * @return true: Si se ejecutó correctamente el query.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarUbicacionCuenta()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta, a1.st_aut, a1.ne_ultnumchq, a1.ne_ultnumtra";
                strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    strUbiCta=rst.getString("tx_ubiCta");
                    strAutCta=rst.getString("st_aut");
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite saber si el usuario ha seleccionado alguna nota de crédito.
     * @return true: Si existen notas de crédito seleccionadas.
     * <BR>false: En el caso contrario.
     */
    private boolean existeNotaCreditoSeleccionado(){
        boolean blnRes=false;
        int intNumNotCre=0;
        String strNatDoc="";
        try{
            for(int i=0;i<objTblMod.getRowCountTrue();i++){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    strNatDoc=objTblMod.getValueAt(i, INT_TBL_DAT_AUX_NAT_DOC)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_AUX_NAT_DOC).toString();
                    if(strNatDoc.equals("E")){
                        intNumNotCre++;
                        break;
                    }
                }

            }
            if(intNumNotCre>0){
                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }





    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUIVisPre extends Thread{
        private int intIndFun;
        public ZafThreadGUIVisPre(){
            intIndFun=0;
        }
        public void run(){
            switch (intIndFun){
                case 0: //Botón "Imprimir".
                    generarRpt(0);
                    break;
                case 1: //Botón "Vista Preliminar".
                    generarRpt(2);
                    break;
            }
            objThrGUIVisPre=null;
        }

        /**
         * Esta función establece el indice de la función a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta función sirve para determinar
         * la función que debe ejecutar el Thread.
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }




    private String obtieneNumeroRetencion(int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento){
        String strNumRetAux="";
        Connection conNumRet;
        Statement stmNumRet;
        ResultSet rstNumRet;
        String strNumRet;
        try{
            conNumRet=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conNumRet!=null){
                stmNumRet=conNumRet.createStatement();
                strNumRet="";
                strNumRet+="SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a1.ne_numDoc1";
                strNumRet+=" FROM tbm_cabPag AS a1 INNER JOIN tbm_detPag AS a2";
                strNumRet+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strNumRet+=" INNER JOIN tbm_pagMovInv AS a3";
                strNumRet+=" ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc";
                strNumRet+=" AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg";
                strNumRet+=" WHERE a3.nd_porRet>0 AND a1.st_reg IN('A','C')";
                strNumRet+=" AND a3.co_emp=" + codigoEmpresa + "";
                strNumRet+=" AND a3.co_loc=" + codigoLocal + "";
                strNumRet+=" AND a3.co_tipDoc=" + codigoTipoDocumento + "";
                strNumRet+=" AND a3.co_doc=" + codigoDocumento + "";
                strNumRet+=" GROUP BY a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a1.ne_numDoc1";
                System.out.println("obtieneNumeroRetencion: " + strNumRet);
                rstNumRet=stmNumRet.executeQuery(strNumRet);
                while(rstNumRet.next()){
//                    if(j==0){
                        strNumRetFacComSel+="," + rstNumRet.getObject("ne_numDoc1")==null?"":rstNumRet.getString("ne_numDoc1");
                        strNumRetAux="," + rstNumRet.getObject("ne_numDoc1")==null?"":rstNumRet.getString("ne_numDoc1");
                        System.out.println("strNumRetFacComSel: " + strNumRetFacComSel);
//                    }
//                    else{
//                        strNumRetFacComSel+=",";
//                        strNumRetFacComSel+=rstNumRet.getObject("ne_numDoc1")==null?"":rstNumRet.getString("ne_numDoc1");
//                    }
                   
                    
                }
                conNumRet.close();
                conNumRet=null;
                stmNumRet.close();
                stmNumRet=null;
                rstNumRet.close();
                rstNumRet=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strNumRetAux;
    }
}