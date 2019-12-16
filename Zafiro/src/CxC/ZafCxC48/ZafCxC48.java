/*
 * ZafCxC48.java
 *
 * Created on 4 de abril de 2008, 15:48
 */
package CxC.ZafCxC48;
import Librerias.ZafParSis.ZafParSis;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafUtil.UltDocPrint;
import Librerias.ZafRptSis.ZafRptSis;

/**
 *
 * @author  ilino
 */
public class ZafCxC48 extends javax.swing.JInternalFrame 
{
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private MiToolBar objTooBar;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu objTblPopMnu;
    private ZafThreadGUI objThrGUI;
    private ZafMouMotAda objMouMotAda;
    private ZafTblFilCab objTblFilCab;
    private ZafTblEdi objTblEdi;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblCelEdiTxt objTblCelEdiTxt, objTblCelEdiTxtDiaVcd;
    private java.util.Date datFecAux;
    private Vector vecReg, vecDat, vecCab, vecAux;
    private ZafDatePicker dtpFecDoc, dtpFecVnc;
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL;
    private String strDesCorTipDoc, strDesLarTipDoc, strAux, strCodCli, strDesLarCli, strIdeCli, strDirCli, strTelCli, strCiuCli;
    private ZafVenCon vcoTipDoc;                        //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoCli;                           //Ventana de consulta "Proveedor".
    private ZafTblTot objTblTot;                        //JTable de totales.
    private ZafAsiDia objAsiDia;
    private UltDocPrint objUltDocPrn;
    private boolean blnHayCam;
    private ZafRptSis objRptSis;                        //Reportes del Sistema.
    
    private java.text.SimpleDateFormat sdf;
    private java.util.Date datFecDoc;
    
    //para el modelo de la tabla
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_CHK=1;
    final int INT_TBL_DAT_COD_EMP=2;
    final int INT_TBL_DAT_COD_LOC=3;
    final int INT_TBL_DAT_COD_TIP_DOC=4;
    final int INT_TBL_DAT_DES_COR_TIP_DOC=5;
    final int INT_TBL_DAT_COD_DOC=6;
    final int INT_TBL_DAT_COD_REG=7;
    final int INT_TBL_DAT_NUM_DOC=8;
    final int INT_TBL_DAT_FEC_DOC=9;
    final int INT_TBL_DAT_DIA_CRE=10;
    final int INT_TBL_DAT_FEC_VNC=11;
    final int INT_TBL_DAT_VAL_DOC=12;
    final int INT_TBL_DAT_VAL_PND=13;
    final int INT_TBL_DAT_DIA_VCD=14;
    final int INT_TBL_DAT_INT_COB=15;
    final int INT_TBL_DAT_GTO_COB=16;
    
    final int intJspValIni=0;
    final int intJspValMin=0;
    final int intJspValMax=100;
    final int intJspValInc=1;
    

    
    
    //LOS SIGUIENTES ARRAYLIST SON USADOS PARA INSERCION
    ArrayList arlRegInsPro, arlDatInsPro;
    final int INT_ARL_INS_PRO_COD_EMP=0;
    final int INT_ARL_INS_PRO_COD_LOC=1;
    final int INT_ARL_INS_PRO_COD_TIP_DOC=2;
    final int INT_ARL_INS_PRO_DES_COR_TIP_DOC=3;
    final int INT_ARL_INS_PRO_COD_DOC=4;
    final int INT_ARL_INS_PRO_COD_REG=5;
    final int INT_ARL_INS_PRO_NUM_DOC=6;
    final int INT_ARL_INS_PRO_FEC_DOC=7;
    final int INT_ARL_INS_PRO_DIA_CRE=8;
    final int INT_ARL_INS_PRO_FEC_VEN=9;
    final int INT_ARL_INS_PRO_VAL_DOC=10;
    final int INT_ARL_INS_PRO_VAL_PEN=11;
    final int INT_ARL_INS_PRO_DIA_VCD=12;
    final int INT_ARL_INS_PRO_INT_COB=13;
    final int INT_ARL_INS_PRO_GAS_COB=14;
    
    ArrayList arlRegInsNotPro, arlDatInsNotPro;
    final int INT_ARL_INS_NOT_PRO_COD_EMP=0;
    final int INT_ARL_INS_NOT_PRO_COD_LOC=1;
    final int INT_ARL_INS_NOT_PRO_COD_TIP_DOC=2;
    final int INT_ARL_INS_NOT_PRO_COD_DOC=3;
    final int INT_ARL_INS_NOT_PRO_COD_REG=4;

    
    
    //LOS SIGUIENTES ARRAYLIST SON USADOS PARA MODIFICACION
    ArrayList arlRegPro, arlDatPro;
    final int INT_ARL_PRO_COD_EMP=0;
    final int INT_ARL_PRO_COD_LOC_INT=1;
    final int INT_ARL_PRO_COD_TIP_DOC_INT=2;
    final int INT_ARL_PRO_COD_DOC_INT=3;
    final int INT_ARL_PRO_COD_REG_INT=4;
    final int INT_ARL_PRO_DES_COR=5;
    final int INT_ARL_PRO_NUM_DOC=6;
    final int INT_ARL_PRO_FEC_DOC=7;
    final int INT_ARL_PRO_DIA_CRE=8;
    final int INT_ARL_PRO_FEC_VEN=9;
    final int INT_ARL_PRO_MON_PAG=10;
    final int INT_ARL_PRO_VAL_PND=11;
    final int INT_ARL_PRO_DIA_VCD=12;
    final int INT_ARL_PRO_INT=13;
    final int INT_ARL_PRO_GAS_COB=14;
    final int INT_ARL_PRO_EST=15;
    
    ArrayList arlRegNotPro, arlDatNotPro;
    final int INT_ARL_NOT_PRO_COD_EMP=0;
    final int INT_ARL_NOT_PRO_COD_LOC_INT=1;
    final int INT_ARL_NOT_PRO_COD_TIP_DOC_INT=2;
    final int INT_ARL_NOT_PRO_COD_DOC_INT=3;
    final int INT_ARL_NOT_PRO_COD_REG_INT=4;
    final int INT_ARL_NOT_PRO_DES_COR=5;
    final int INT_ARL_NOT_PRO_NUM_DOC=6;
    final int INT_ARL_NOT_PRO_FEC_DOC=7;
    final int INT_ARL_NOT_PRO_DIA_CRE=8;
    final int INT_ARL_NOT_PRO_FEC_VEN=9;
    final int INT_ARL_NOT_PRO_MON_PAG=10;
    final int INT_ARL_NOT_PRO_VAL_PND=11;
    final int INT_ARL_NOT_PRO_DIA_VCD=12;
    final int INT_ARL_NOT_PRO_INT=13;
    final int INT_ARL_NOT_PRO_GAS_COB=14;
    final int INT_ARL_NOT_PRO_EST=15;
    
    
    ArrayList arlRegDocPrc, arlDatDocPrc;
    final int INT_ARL_DOC_PRC_COD_EMP=0;
    final int INT_ARL_DOC_PRC_COD_LOC=1;
    final int INT_ARL_DOC_PRC_COD_TIP_DOC=2;
    final int INT_ARL_DOC_PRC_COD_DOC=3;
    final int INT_ARL_DOC_PRC_COD_REG=4;

    private int intSecGrp;
    private int intSecEmp;
    
    private String strVer=" v0.4.3";
    
    /** Creates new form ZafCxC48 */
    public ZafCxC48(ZafParSis obj) {
        try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            arlDatPro=new ArrayList();
            arlDatNotPro=new ArrayList();
            arlDatInsPro=new ArrayList();
            arlDatInsNotPro=new ArrayList();
            arlDatDocPrc=new ArrayList();
        }
        catch (CloneNotSupportedException e){
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

        tabFrm = new javax.swing.JTabbedPane();
        panGrl = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        lblTipDoc = new javax.swing.JLabel();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblPrv = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        txtCodDoc = new javax.swing.JTextField();
        lblCodDoc = new javax.swing.JLabel();
        lblTasInt = new javax.swing.JLabel();
        jspTasInt = new javax.swing.JSpinner();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblMonDoc = new javax.swing.JLabel();
        txtMonDoc = new javax.swing.JTextField();
        lblFecVnc = new javax.swing.JLabel();
        lblGtoCob = new javax.swing.JLabel();
        jspGtoCob = new javax.swing.JSpinner();
        panCen = new javax.swing.JPanel();
        panRep = new javax.swing.JPanel();
        sppRpt = new javax.swing.JSplitPane();
        panFilGrlCli = new javax.swing.JPanel();
        spnIntSimDat = new javax.swing.JScrollPane();
        tblIntSim = new javax.swing.JTable();
        spnIntSimTot = new javax.swing.JScrollPane();
        tblIntSimTot = new javax.swing.JTable();
        panFilDetCli = new javax.swing.JPanel();
        chkMosMovReg = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        spnDetDat = new javax.swing.JScrollPane();
        tblTblAmr = new javax.swing.JTable();
        spnDetTot = new javax.swing.JScrollPane();
        tblDetTot = new javax.swing.JTable();
        panIntSim = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        panUniTie = new javax.swing.JPanel();
        optUniTieDia = new javax.swing.JRadioButton();
        optUniTieMes = new javax.swing.JRadioButton();
        optUniTieAni = new javax.swing.JRadioButton();
        panMstDocAbiCer = new javax.swing.JPanel();
        chkMosDctAbi = new javax.swing.JCheckBox();
        chkMosDctCer = new javax.swing.JCheckBox();
        panPie = new javax.swing.JPanel();
        panObs = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblObsDos = new javax.swing.JLabel();
        lblObsUno = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panTooBar = new javax.swing.JPanel();
        panAsiDia = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();

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

        panGrl.setLayout(new java.awt.BorderLayout());

        panCab.setFocusCycleRoot(true);
        panCab.setPreferredSize(new java.awt.Dimension(0, 80));
        panCab.setLayout(null);

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
        panCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(114, 1, 56, 20);
        panCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(82, 1, 32, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCab.add(lblTipDoc);
        lblTipDoc.setBounds(0, 0, 100, 20);

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
        panCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(170, 1, 240, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCab.add(butTipDoc);
        butTipDoc.setBounds(410, 1, 20, 20);

        lblPrv.setText("Cliente:");
        lblPrv.setToolTipText("Proveedor");
        panCab.add(lblPrv);
        lblPrv.setBounds(0, 21, 100, 20);

        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        panCab.add(txtCodCli);
        txtCodCli.setBounds(114, 21, 56, 20);

        txtDesLarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCliActionPerformed(evt);
            }
        });
        txtDesLarCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusLost(evt);
            }
        });
        panCab.add(txtDesLarCli);
        txtDesLarCli.setBounds(170, 21, 240, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panCab.add(butCli);
        butCli.setBounds(410, 21, 20, 20);
        panCab.add(txtCodDoc);
        txtCodDoc.setBounds(114, 41, 80, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCab.add(lblCodDoc);
        lblCodDoc.setBounds(0, 44, 112, 20);

        lblTasInt.setText("Tasa de interés:");
        lblTasInt.setToolTipText("Código del documento");
        panCab.add(lblTasInt);
        lblTasInt.setBounds(0, 60, 100, 20);

        jspTasInt.setBackground(new java.awt.Color(255, 255, 220));
        jspTasInt.setForeground(new java.awt.Color(255, 255, 220));
        jspTasInt.setModel(new javax.swing.SpinnerNumberModel(intJspValIni, intJspValMin, intJspValMax, intJspValInc));
        panCab.add(jspTasInt);
        jspTasInt.setBounds(114, 61, 80, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCab.add(lblFecDoc);
        lblFecDoc.setBounds(444, 1, 110, 20);

        lblNumDoc.setText("Número aterno 1:");
        lblNumDoc.setToolTipText("Número alterno 1");
        panCab.add(lblNumDoc);
        lblNumDoc.setBounds(444, 40, 100, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.setToolTipText("Número de egreso");
        panCab.add(txtNumDoc);
        txtNumDoc.setBounds(560, 40, 120, 20);

        lblMonDoc.setText("Valor del documento:");
        lblMonDoc.setToolTipText("Valor del documento");
        panCab.add(lblMonDoc);
        lblMonDoc.setBounds(444, 60, 110, 20);

        txtMonDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCab.add(txtMonDoc);
        txtMonDoc.setBounds(560, 60, 120, 20);

        lblFecVnc.setText("Fecha de vencimiento:");
        lblFecVnc.setToolTipText("Fecha del documento");
        panCab.add(lblFecVnc);
        lblFecVnc.setBounds(444, 20, 110, 20);

        lblGtoCob.setText("Gastos de Cobranzas:");
        panCab.add(lblGtoCob);
        lblGtoCob.setBounds(210, 60, 120, 14);

        jspGtoCob.setBackground(new java.awt.Color(255, 255, 220));
        jspGtoCob.setForeground(new java.awt.Color(255, 255, 220));
        jspGtoCob.setModel(new javax.swing.SpinnerNumberModel(intJspValIni, intJspValMin, intJspValMax, intJspValInc));
        panCab.add(jspGtoCob);
        jspGtoCob.setBounds(310, 61, 80, 20);

        panGrl.add(panCab, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        panRep.setLayout(new java.awt.BorderLayout());

        sppRpt.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppRpt.setResizeWeight(0.5);
        sppRpt.setOneTouchExpandable(true);

        panFilGrlCli.setPreferredSize(new java.awt.Dimension(452, 452));
        panFilGrlCli.setLayout(new java.awt.BorderLayout());

        tblIntSim.setModel(new javax.swing.table.DefaultTableModel(
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
        spnIntSimDat.setViewportView(tblIntSim);

        panFilGrlCli.add(spnIntSimDat, java.awt.BorderLayout.CENTER);

        spnIntSimTot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblIntSimTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnIntSimTot.setViewportView(tblIntSimTot);

        panFilGrlCli.add(spnIntSimTot, java.awt.BorderLayout.SOUTH);

        sppRpt.setTopComponent(panFilGrlCli);

        panFilDetCli.setPreferredSize(new java.awt.Dimension(454, 440));
        panFilDetCli.setRequestFocusEnabled(false);
        panFilDetCli.setLayout(new java.awt.BorderLayout());

        chkMosMovReg.setText("Mostrar el moviminto del cliente seleccionado");
        panFilDetCli.add(chkMosMovReg, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.BorderLayout());

        tblTblAmr.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDetDat.setViewportView(tblTblAmr);

        jPanel1.add(spnDetDat, java.awt.BorderLayout.CENTER);

        spnDetTot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblDetTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnDetTot.setViewportView(tblDetTot);

        jPanel1.add(spnDetTot, java.awt.BorderLayout.SOUTH);

        panFilDetCli.add(jPanel1, java.awt.BorderLayout.CENTER);

        sppRpt.setBottomComponent(panFilDetCli);

        panRep.add(sppRpt, java.awt.BorderLayout.CENTER);

        panIntSim.setPreferredSize(new java.awt.Dimension(10, 80));
        panIntSim.setLayout(new java.awt.BorderLayout());

        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Utilizar \"Interés Simple\"");
        jRadioButton1.setPreferredSize(new java.awt.Dimension(91, 16));
        panIntSim.add(jRadioButton1, java.awt.BorderLayout.NORTH);

        panUniTie.setBorder(javax.swing.BorderFactory.createTitledBorder("Unidad de Tiempo"));
        panUniTie.setPreferredSize(new java.awt.Dimension(10, 32));
        panUniTie.setLayout(null);

        optUniTieDia.setSelected(true);
        optUniTieDia.setText("Días");
        optUniTieDia.setPreferredSize(new java.awt.Dimension(91, 16));
        optUniTieDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optUniTieDiaActionPerformed(evt);
            }
        });
        panUniTie.add(optUniTieDia);
        optUniTieDia.setBounds(50, 13, 80, 14);

        optUniTieMes.setText("Meses");
        optUniTieMes.setPreferredSize(new java.awt.Dimension(91, 16));
        optUniTieMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optUniTieMesActionPerformed(evt);
            }
        });
        panUniTie.add(optUniTieMes);
        optUniTieMes.setBounds(150, 13, 80, 14);

        optUniTieAni.setText("Años");
        optUniTieAni.setPreferredSize(new java.awt.Dimension(91, 16));
        optUniTieAni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optUniTieAniActionPerformed(evt);
            }
        });
        panUniTie.add(optUniTieAni);
        optUniTieAni.setBounds(250, 13, 80, 14);

        panIntSim.add(panUniTie, java.awt.BorderLayout.CENTER);

        panMstDocAbiCer.setPreferredSize(new java.awt.Dimension(10, 30));
        panMstDocAbiCer.setRequestFocusEnabled(false);
        panMstDocAbiCer.setLayout(null);

        chkMosDctAbi.setSelected(true);
        chkMosDctAbi.setText("Mostrar documentos abiertos");
        chkMosDctAbi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDctAbiActionPerformed(evt);
            }
        });
        panMstDocAbiCer.add(chkMosDctAbi);
        chkMosDctAbi.setBounds(60, 2, 250, 14);

        chkMosDctCer.setText("Mostrar documentos cerrados");
        chkMosDctCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDctCerActionPerformed(evt);
            }
        });
        panMstDocAbiCer.add(chkMosDctCer);
        chkMosDctCer.setBounds(60, 16, 250, 14);

        panIntSim.add(panMstDocAbiCer, java.awt.BorderLayout.SOUTH);

        panRep.add(panIntSim, java.awt.BorderLayout.NORTH);

        panCen.add(panRep, java.awt.BorderLayout.CENTER);

        panGrl.add(panCen, java.awt.BorderLayout.CENTER);

        panPie.setPreferredSize(new java.awt.Dimension(10, 100));
        panPie.setLayout(new java.awt.BorderLayout());

        panObs.setPreferredSize(new java.awt.Dimension(34, 44));
        panObs.setRequestFocusEnabled(false);
        panObs.setLayout(new java.awt.BorderLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(86, 30));
        jPanel2.setLayout(new java.awt.BorderLayout());

        lblObsDos.setText("Observación 2:");
        lblObsDos.setPreferredSize(new java.awt.Dimension(34, 15));
        jPanel2.add(lblObsDos, java.awt.BorderLayout.CENTER);

        lblObsUno.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObsUno.setText("Observación 1:");
        jPanel2.add(lblObsUno, java.awt.BorderLayout.NORTH);

        panObs.add(jPanel2, java.awt.BorderLayout.WEST);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(2, 22));

        txaObs1.setPreferredSize(new java.awt.Dimension(0, 20));
        jScrollPane1.setViewportView(txaObs1);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.NORTH);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(2, 22));

        txaObs2.setPreferredSize(new java.awt.Dimension(0, 20));
        jScrollPane2.setViewportView(txaObs2);

        jPanel3.add(jScrollPane2, java.awt.BorderLayout.SOUTH);

        panObs.add(jPanel3, java.awt.BorderLayout.CENTER);

        panPie.add(panObs, java.awt.BorderLayout.NORTH);

        panTooBar.setPreferredSize(new java.awt.Dimension(10, 18));
        panTooBar.setLayout(new java.awt.BorderLayout());
        panPie.add(panTooBar, java.awt.BorderLayout.CENTER);

        panGrl.add(panPie, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Filtro", panGrl);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de Diario", panAsiDia);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="�Est� seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexi�n si est� abierta.
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

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        // TODO add your handling code here:
        strCodCli=txtCodCli.getText();
        mostrarVenConCli(0);
        if (!txtCodCli.getText().equals("")){
            //Cargar los documentos pendientes s�lo si ha cambiado el beneficiario.
            if(objTooBar.getEstado()=='n'){
                if(  (!txtCodCli.getText().equalsIgnoreCase(strCodCli))    &&  (    (chkMosDctAbi.isSelected() ||  chkMosDctCer.isSelected() ) )  )
                        cargarRegistros();
                else{
                    mostrarMsgInf("<HTML>Debe seleccionar alg�n filtro de documentos abiertos o cerrados</HTML>");
                    txtCodCli.setText("");
                    txtDesLarCli.setText("");
                }
            }
        }
    }//GEN-LAST:event_butCliActionPerformed

    private void optUniTieAniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optUniTieAniActionPerformed
        // TODO add your handling code here:
        if(optUniTieAni.isSelected()){
            optUniTieDia.setSelected(false);
            optUniTieMes.setSelected(false);
        }
    }//GEN-LAST:event_optUniTieAniActionPerformed

    private void optUniTieMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optUniTieMesActionPerformed
        // TODO add your handling code here:
        if(optUniTieMes.isSelected()){
            optUniTieDia.setSelected(false);
            optUniTieAni.setSelected(false);            
        }
    }//GEN-LAST:event_optUniTieMesActionPerformed

    private void optUniTieDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optUniTieDiaActionPerformed
        // TODO add your handling code here:
        if(optUniTieDia.isSelected()){
            optUniTieMes.setSelected(false);
            optUniTieAni.setSelected(false);
        }
    }//GEN-LAST:event_optUniTieDiaActionPerformed

    private void chkMosDctAbiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDctAbiActionPerformed
        // TODO add your handling code here:
        if(chkMosDctAbi.isSelected()){
            chkMosDctCer.setSelected(false);
            if(   (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')  ){
                if(!  txtCodCli.getText().toString().equals("")){
                    optUniTieDia.setEnabled(true);
                    optUniTieMes.setEnabled(true);
                    optUniTieAni.setEnabled(true);
                    if(  (optUniTieDia.isSelected())  ||  (optUniTieMes.isSelected())  ||  (optUniTieAni.isSelected()) ){
                        if(cargarRegModificar()){
                        }
                    }
                    else{
                        mostrarMsgInf("<HTML>Debe seleccionar una Unidad de Tiempo para realizar la consulta<BR>Seleccione una Unidad de Tiempo y vuelva a intentarlo</HTML>");
                        chkMosDctAbi.setSelected(false);
                    }
                    
                }
                else{
                    mostrarMsgInf("<HTML>Debe seleccionar un cliente para poder realizar la consulta<BR>Ingrese un cliente y vuelva a intentarlo</HTML>");
                }
            }
        }
    }//GEN-LAST:event_chkMosDctAbiActionPerformed

    private void chkMosDctCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDctCerActionPerformed
        // TODO add your handling code here:
        if(chkMosDctCer.isSelected()){
            chkMosDctAbi.setSelected(false);
            if(   (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')  ){
                if(!  txtCodCli.getText().toString().equals("")){
                    if(  (optUniTieDia.isSelected())  ||  (optUniTieMes.isSelected())  ||  (optUniTieAni.isSelected()) ){
                        if(cargarRegModificar()){
                        }
                    }
                    else{
                        mostrarMsgInf("<HTML>Debe seleccionar una Unidad de Tiempo para realizar la consulta<BR>Seleccione una Unidad de Tiempo y vuelva a intentarlo</HTML>");
                        chkMosDctCer.setSelected(false);
                    }

                }
                else{
                    mostrarMsgInf("<HTML>Debe seleccionar un cliente para poder realizar la consulta<BR>Ingrese un cliente y vuelva a intentarlo</HTML>");
                }
            }
        }
    }//GEN-LAST:event_chkMosDctCerActionPerformed

    private void txtDesLarCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli)){
            if (txtDesLarCli.getText().equals("")){
                txtCodCli.setText("");
                txtDesLarCli.setText("");
                objTblMod.removeAllRows();
                txtMonDoc.setText("");
            }
            else{
                mostrarVenConCli(2);
                if(objTooBar.getEstado()=='n'){
                    //Cargar los documentos pendientes s�lo si ha cambiado el beneficiario.
                    if( (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli))    &&  (    (chkMosDctAbi.isSelected() ||  chkMosDctCer.isSelected() ) )  )
                        cargarRegistros();
                    else{
                        mostrarMsgInf("<HTML>Debe seleccionar alg�n filtro de documentos abiertos o cerrados</HTML>");
                        txtCodCli.setText("");
                        txtDesLarCli.setText("");
                    }
                }
            }
        }
        else
            txtDesLarCli.setText(strDesLarCli);
    }//GEN-LAST:event_txtDesLarCliFocusLost

    private void txtDesLarCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusGained
        // TODO add your handling code here:
        strDesLarCli=txtDesLarCli.getText();
        txtDesLarCli.selectAll();
    }//GEN-LAST:event_txtDesLarCliFocusGained

    private void txtDesLarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCliActionPerformed
        // TODO add your handling code here:
        txtDesLarCli.transferFocus();
    }//GEN-LAST:event_txtDesLarCliActionPerformed

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtDesLarCli.setText("");
                objTblMod.removeAllRows();
                txtMonDoc.setText("");
            }
            else
            {
                mostrarVenConCli(1);
                if(objTooBar.getEstado()=='n'){
                    //Cargar los documentos pendientes s�lo si ha cambiado el beneficiario.
                     if( (!txtCodCli.getText().equalsIgnoreCase(strCodCli))  &&  (    (chkMosDctAbi.isSelected() ||  chkMosDctCer.isSelected() ) )  )
                        cargarRegistros();
                     else{
                         mostrarMsgInf("<HTML>Debe seleccionar alg�n filtro de documentos abiertos o cerrados</HTML>");
                         txtCodCli.setText("");
                         txtDesLarCli.setText("");
                     }
                }
            }
        }
        else
            txtCodCli.setText(strCodCli);
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        // TODO add your handling code here:
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        // TODO add your handling code here:
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        // TODO add your handling code here:
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(2);
            }
        }
        else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        // TODO add your handling code here:
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        // TODO add your handling code here:
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtDesCorTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(1);
            }
        }
        else
            txtDesCorTipDoc.setText(strDesCorTipDoc);
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        // TODO add your handling code here:
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        // TODO add your handling code here:
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        configurarFrm();
//        agregarDocLis();
    }//GEN-LAST:event_formInternalFrameOpened
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCli;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JCheckBox chkMosDctAbi;
    private javax.swing.JCheckBox chkMosDctCer;
    private javax.swing.JCheckBox chkMosMovReg;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jspGtoCob;
    private javax.swing.JSpinner jspTasInt;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecVnc;
    private javax.swing.JLabel lblGtoCob;
    private javax.swing.JLabel lblMonDoc;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObsDos;
    private javax.swing.JLabel lblObsUno;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblTasInt;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optUniTieAni;
    private javax.swing.JRadioButton optUniTieDia;
    private javax.swing.JRadioButton optUniTieMes;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panFilDetCli;
    private javax.swing.JPanel panFilGrlCli;
    private javax.swing.JPanel panGrl;
    private javax.swing.JPanel panIntSim;
    private javax.swing.JPanel panMstDocAbiCer;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panPie;
    private javax.swing.JPanel panRep;
    private javax.swing.JPanel panTooBar;
    private javax.swing.JPanel panUniTie;
    private javax.swing.JScrollPane spnDetDat;
    private javax.swing.JScrollPane spnDetTot;
    private javax.swing.JScrollPane spnIntSimDat;
    private javax.swing.JScrollPane spnIntSimTot;
    private javax.swing.JSplitPane sppRpt;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDetTot;
    private javax.swing.JTable tblIntSim;
    private javax.swing.JTable tblIntSimTot;
    private javax.swing.JTable tblTblAmr;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtDesLarTipDoc;
    public javax.swing.JTextField txtMonDoc;
    private javax.swing.JTextField txtNumDoc;
    // End of variables declaration//GEN-END:variables

    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            panTooBar.add(objTooBar);
            
            objTooBar.setVisibleModificar(false);
            
            
            vecDat=new Vector();
            
            objUltDocPrn=new UltDocPrint(objParSis);
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(560, 1, 120, 20);
            
            dtpFecVnc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecVnc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecVnc.setText("");
            panCab.add(dtpFecVnc);
            dtpFecVnc.setBounds(560, 19, 120, 20);
            
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            
            
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodCli.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarCli.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            
//            jspTasInt.setModel(new javax.swing.SpinnerNumberModel(intJspValIni, intJspValMin, intJspValMax, intJspValInc));
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtMonDoc.setBackground(objParSis.getColorCamposSistema());
            
            txtCodTipDoc.setEditable(false);
            txtCodTipDoc.setVisible(false);
            txtCodDoc.setEnabled(false);
            txtMonDoc.setEnabled(false);

            objAsiDia=new ZafAsiDia(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
            
            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            
            
            vecCab=new Vector(17);
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_CHK, "");
            vecCab.add(INT_TBL_DAT_COD_EMP, "COD.EMP.");
            vecCab.add(INT_TBL_DAT_COD_LOC, "COD.LOC.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC, "COD.TIP.DOC.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC, "DESC.TIP.DOC.");
            vecCab.add(INT_TBL_DAT_COD_DOC, "COD.DOC.");
            vecCab.add(INT_TBL_DAT_COD_REG, "COD.REG.");
            vecCab.add(INT_TBL_DAT_NUM_DOC, "NUM.DOC.");
            vecCab.add(INT_TBL_DAT_FEC_DOC, "FEC.DOC.");
            vecCab.add(INT_TBL_DAT_DIA_CRE, "DIA.CRE.");
            vecCab.add(INT_TBL_DAT_FEC_VNC, "FEC.VENCIM.");
            vecCab.add(INT_TBL_DAT_VAL_DOC, "VAL.DOC.");
            vecCab.add(INT_TBL_DAT_VAL_PND, "VAL.PNDTE.");
            vecCab.add(INT_TBL_DAT_DIA_VCD, "DIA.VENCID.");
            vecCab.add(INT_TBL_DAT_INT_COB, "INT.COBRAR");
            vecCab.add(INT_TBL_DAT_GTO_COB, "GTO.COBR.");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_DOC, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_PND, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_INT_COB, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_GTO_COB, objTblMod.INT_COL_DBL, new Integer(0), null);
            
            tblIntSim.setModel(objTblMod);
            
            //Configurar JTable: Establecer relaci�n entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_VAL_DOC, INT_TBL_DAT_VAL_PND, INT_TBL_DAT_INT_COB,INT_TBL_DAT_GTO_COB};
            objTblTot=new ZafTblTot(spnIntSimDat, spnIntSimTot, tblIntSim, tblIntSimTot, intCol);
            
            
            //Configurar JTable: Establecer tipo de selecci�n.
            tblIntSim.setRowSelectionAllowed(true);
            tblIntSim.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el men� de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblIntSim);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblIntSim.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblIntSim.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_FEC_VNC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PND).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DIA_VCD).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_INT_COB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_GTO_COB).setPreferredWidth(80);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblIntSim.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblIntSim.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblIntSim);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK);
            vecAux.add("" + INT_TBL_DAT_GTO_COB);
            vecAux.add("" + INT_TBL_DAT_FEC_VNC);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblIntSim);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            
            
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PND).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_INT_COB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_GTO_COB).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblIntSim);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilSel=tblIntSim.getSelectedRow();
                    if (objTblCelEdiChk.isChecked()){
                        //calcularValorInteres(intFilSel);
                        if(  (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m') ){
                            calcularValorGtoCobranzas(intFilSel);
                        }
                        calculaValorTotalValorDcto();
                        calculaValorTotalValorPndte();
                        calculaValorTotalInteres();
                        calculaValorTotalGtosCobranzas();
                        calculaValorTotalDocumento();
                        objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDoc.getText(), txtMonDoc.getText());                        
                    }
                    else{
                        //calcularValorInteres(intFilSel);
                        if(  (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m') ){
                            
                            objTblMod.setValueAt("" +new Double(0.00), intFilSel, INT_TBL_DAT_GTO_COB);
                            
                        }
                        calculaValorTotalValorDcto();
                        calculaValorTotalValorPndte();
                        calculaValorTotalInteres();
                        calculaValorTotalGtosCobranzas();
                        calculaValorTotalDocumento();
                        objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDoc.getText(), txtMonDoc.getText());
                    }
                }
            });
            
            
            
            
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblIntSim);
            tcmAux.getColumn(INT_TBL_DAT_GTO_COB).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxt.getText().equals(""))
                    {
                        objTblMod.setChecked(false, tblIntSim.getSelectedRow(), INT_TBL_DAT_CHK);
                    }
                    else
                    {
                        objTblMod.setChecked(true, tblIntSim.getSelectedRow(), INT_TBL_DAT_CHK);
                        
                    }
                calculaValorTotalValorDcto();
                calculaValorTotalValorPndte();
                calculaValorTotalInteres();
                calculaValorTotalGtosCobranzas();
                calculaValorTotalDocumento();
                objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDoc.getText(), txtMonDoc.getText());
                }
            });
            
            
            
            objTblCelEdiTxtDiaVcd=new ZafTblCelEdiTxt(tblIntSim);
            tcmAux.getColumn(INT_TBL_DAT_FEC_VNC).setCellEditor(objTblCelEdiTxtDiaVcd);
            objTblCelEdiTxtDiaVcd.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilSel=tblIntSim.getSelectedRow();
                    int intDiaVcdCal=0;                    
                    try{
                        Connection conTmp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                        Statement stmTmp;
                        ResultSet rstTmp;
                        if(conTmp!=null){
                            stmTmp=conTmp.createStatement();
                            strAux="";
//                            strAux+="select cast('" + objTblMod.getValueAt(intFilSel, INT_TBL_DAT_FEC_VNC) + "' as date) - cast('" + objTblMod.getValueAt(intFilSel, INT_TBL_DAT_FEC_DOC) + "' as date) AS diaVcd";
                            strAux+="select CURRENT_DATE - cast('" + objTblMod.getValueAt(intFilSel, INT_TBL_DAT_FEC_VNC) + "' as date) AS diaVcd";
                            rstTmp=stmTmp.executeQuery(strAux);
                            if(rstTmp.next()){
                                intDiaVcdCal=rstTmp.getInt("diaVcd");
                            }
                            conTmp.close();
                            conTmp=null;
                            stmTmp.close();
                            stmTmp=null;
                            rstTmp.close();
                            rstTmp=null;
                        }
                            
                    }
                    catch(java.sql.SQLException e){
                        System.err.println(e.getMessage());
                    }
                    
                    objTblMod.setValueAt("" + intDiaVcdCal, intFilSel, INT_TBL_DAT_DIA_VCD);
                    objTblMod.setChecked(true, tblIntSim.getSelectedRow(), INT_TBL_DAT_CHK);
                    
                    calcularValorInteres(intFilSel);
                    calculaValorTotalValorDcto();
                    calculaValorTotalValorPndte();
                    calculaValorTotalInteres();
//                    if(   (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')  ){
//                        double dblVerGtoCob=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_GTO_COB)==null?0:(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_GTO_COB)==""?0:Double.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_GTO_COB).toString()));
//                        if(dblVerGtoCob!=0){
//                            calcularValorGastosCobranzas(intFilSel);
//                        }
//                        
//                    }
                    calculaValorTotalGtosCobranzas();
                    calculaValorTotalDocumento();

                    
                    objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDoc.getText(), txtMonDoc.getText());
                    
                }
            });
            
            
            
            configurarVenConTipDoc();
            configurarVenConCli();
            
            tblIntSimTot.setRowSelectionAllowed(true);
            tblIntSimTot.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            //objColNum=new ZafColNumerada(tblTotal,INT_TBL_DAT_LIN);            
            tblIntSimTot.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            tcmAux=tblIntSimTot.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_FEC_VNC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PND).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DIA_VCD).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_INT_COB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_GTO_COB).setPreferredWidth(80);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            
            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblIntSim.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Codigo de empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="C�digo del local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="C�digo del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Descripci�n corta del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="C�digo del documento";
                    break;
                case INT_TBL_DAT_COD_REG:
                    strMsg="C�digo del registro";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="N�mero de documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_DIA_CRE:
                    strMsg="D�as de cr�dito";
                    break;
                case INT_TBL_DAT_FEC_VNC:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                case INT_TBL_DAT_VAL_PND:
                    strMsg="Valor pendiente";
                    break;
                case INT_TBL_DAT_DIA_VCD:
                    strMsg="D�as vencidos";
                    break;
                case INT_TBL_DAT_INT_COB:
                    strMsg="Intereses a cobrar";
                    break;
                case INT_TBL_DAT_GTO_COB:
                    strMsg="Gastos de cobranza";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblIntSim.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    
    /**
     * Esta funci�n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de b�squeda determina si se debe hacer
     * una b�squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se est� buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opci�n que desea utilizar.
     * @param intTipBus El tipo de b�squeda a realizar.
     * @return true: Si no se present� ning�n problema.
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
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    break;
                case 1: //B�squeda directa por "Descripci�n corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
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
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //B�squeda directa por "Descripci�n larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
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
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
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
     * Esta funci�n configura la "Ventana de consulta" que ser� utilizada para
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
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("C�digo");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";                
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+=" FROM tbr_tipDocUsr AS a2 inner join  tbm_cabTipDoc AS a1";
                strSQL+=" ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
            }
            

            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=5;
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
     * Esta funci�n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de b�squeda determina si se debe hacer
     * una b�squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se est� buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opci�n que desea utilizar.
     * @param intTipBus El tipo de b�squeda a realizar.
     * @return true: Si no se present� ning�n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCli(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.show();
                    if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        strIdeCli=vcoCli.getValueAt(2);
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                        strDirCli=vcoCli.getValueAt(4);
                        strTelCli=vcoCli.getValueAt(5);
                        strCiuCli=vcoCli.getValueAt(6);
                    }
                    break;
                case 1: //B�squeda directa por "N�mero de cuenta".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        strIdeCli=vcoCli.getValueAt(2);
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                        strDirCli=vcoCli.getValueAt(4);
                        strTelCli=vcoCli.getValueAt(5);
                        strCiuCli=vcoCli.getValueAt(6);
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            strIdeCli=vcoCli.getValueAt(2);
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                            strDirCli=vcoCli.getValueAt(4);
                            strTelCli=vcoCli.getValueAt(5);
                            strCiuCli=vcoCli.getValueAt(6);
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //B�squeda directa por "Descripci�n larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        strIdeCli=vcoCli.getValueAt(2);
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                        strDirCli=vcoCli.getValueAt(4);
                        strTelCli=vcoCli.getValueAt(5);
                        strCiuCli=vcoCli.getValueAt(6);
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            strIdeCli=vcoCli.getValueAt(2);
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                            strDirCli=vcoCli.getValueAt(4);
                            strTelCli=vcoCli.getValueAt(5);
                            strCiuCli=vcoCli.getValueAt(6);
                        }
                        else
                        {
                            txtDesLarCli.setText(strDesLarCli);
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
     * Esta funci�n configura la "Ventana de consulta" que ser� utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConCli()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            arlCam.add("a1.tx_tel");
            arlCam.add("a1.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("C�digo");
            arlAli.add("Identificaci�n");
            arlAli.add("Nombre");
            arlAli.add("Direcci�n");
            arlAli.add("Tel�fono");
            arlAli.add("Ciudad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel, a2.tx_deslar";
            strSQL+=" FROM (tbm_cli AS a1 INNER JOIN tbm_ciu AS a2 ON a1.co_ciu=a2.co_ciu)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_cli='S'";
            strSQL+=" ORDER BY a1.tx_nom";
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=4;
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    /**
     * Esta funci�n muestra el tipo de documento predeterminado del programa.
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
                strSQL="";
                strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a2.nd_tasInt";
                strSQL+=" FROM (tbm_cabTipDoc AS a1 INNER JOIN tbm_emp AS a2 ON a1.co_emp=a2.co_emp)";
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
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                                                    jspTasInt.setModel(new javax.swing.SpinnerNumberModel(rst.getDouble("nd_tasInt"), intJspValMin, intJspValMax, intJspValInc));
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
     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    
    
    
    
    
    
    private class MiToolBar extends ZafToolBar{
        public MiToolBar(javax.swing.JInternalFrame ifrFrm){
            super(ifrFrm, objParSis);
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
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            objTooBar.setEstado('w');
            objAsiDia.setDiarioModificado(false);
            consultarReg();
            blnHayCam=false;
            return true;
        }
        
        public boolean afterModificar() {
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            return true;
        }
        
        public boolean afterVistaPreliminar() {
            return true;
        }
        
        public boolean anular() {
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }
        
        public boolean beforeAceptar() {
            return true;
        }
        
        public boolean beforeAnular() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento est� ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")){
                mostrarMsgInf("El documento ya est� ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            
            
            if(existePagosAsociados()){
                mostrarMsgInf("El documento tiene pagos asociados.\nNo es posible anular un documento que tiene pagos asociados.");
                return false;
            }
            
            
            return true;
        }
        
        public boolean beforeCancelar() {
            return true;
        }
        
        public boolean beforeConsultar() {
            return true;
        }
        
        public boolean beforeEliminar() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento ya est� ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            
            if(existePagosAsociados()){
                mostrarMsgInf("El documento tiene pagos asociados.\nNo es posible eliminar un documento que tiene pagos asociados.");
                return false;
            }
            
            return true;
        }
        
        public boolean beforeImprimir() {
            return true;
        }
        
        public boolean beforeInsertar() {
            if (!isCamVal())
                return false;
            if (objAsiDia.getGeneracionDiario()==2)
                return regenerarDiario();
            return true;
        }
        
        public boolean beforeModificar() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento est� ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")){
                mostrarMsgInf("El documento est� ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            if (!isCamVal())
                return false;
            if (objAsiDia.getGeneracionDiario()==2)
                return regenerarDiario();
            return true;
        }
        
        public boolean beforeVistaPreliminar() {
            return true;
        }
        
        public boolean cancelar() {
            boolean blnRes=true;
            try{
                if (blnHayCam || objAsiDia.isDiarioModificado()){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
                        if (!isRegPro())
                            return false;
                    }
                }
                if (rstCab!=null){
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }
        
        public void clickAceptar() {
        }
        
        public void clickAnterior() {
            try{
                if (!rstCab.isFirst()){
                    if (blnHayCam || objAsiDia.isDiarioModificado()){
                        if (isRegPro()){
                            rstCab.previous();
                            cargarReg();
                        }
                    }
                    else{
                        rstCab.previous();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickAnular() {
            cargarDetReg();
        }
        
        public void clickCancelar() {
        }
        
        public void clickConsultar() {            
            txtCodDoc.setEnabled(true);
            optUniTieDia.setSelected(false);
            optUniTieMes.setSelected(false);
            optUniTieAni.setSelected(false);
        }
        
        public void clickEliminar() {
            cargarDetReg();
        }
        
        public void clickFin() {
            try{
                if (!rstCab.isLast()){
                    if (blnHayCam || objAsiDia.isDiarioModificado()){
                        if (isRegPro()){
                            rstCab.last();
                            cargarReg();
                        }
                    }
                    else{
                        rstCab.last();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickImprimir() {
        }
        
        public void clickInicio() {
            try{
                if (!rstCab.isFirst()){
                    if (blnHayCam || objAsiDia.isDiarioModificado()){
                        if (isRegPro()){
                            rstCab.first();
                            cargarReg();
                        }
                    }
                    else{
                        rstCab.first();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickInsertar() {
            try{
                if (blnHayCam){
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
                objTblMod.removeAllRows();
                objTblTot.setValueAt("0.00", 0, INT_TBL_DAT_VAL_DOC);
                objTblTot.setValueAt("0.00", 0, INT_TBL_DAT_VAL_PND);
                objTblTot.setValueAt("0.00", 0, INT_TBL_DAT_INT_COB);
                objTblTot.setValueAt("0.00", 0, INT_TBL_DAT_GTO_COB);
                
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                dtpFecVnc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                mostrarTipDocPre();
                objAsiDia.inicializar();
                txaObs2.setText("Por cobro de intereses.");
                chkMosDctAbi.setSelected(true);
                blnHayCam=false;

                
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }

        }
        
        public void clickModificar() {
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            txtCodCli.setEditable(false);
            txtDesLarCli.setEditable(false);
            butCli.setEnabled(false);
            txtCodDoc.setEditable(false);
            txtMonDoc.setEditable(false);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objAsiDia.setEditable(true);
//            cargarRegModificar();
            txtNumDoc.selectAll();
            txtNumDoc.requestFocus();
//            dtpFecDoc.setEnabled(false);
//            dtpFecVnc.setEnabled(false);
            optUniTieDia.setEnabled(true);
            optUniTieMes.setEnabled(true);
            optUniTieAni.setEnabled(true);
            chkMosDctAbi.setSelected(false);
            
            
            
            
        }
        
        public void clickSiguiente() {
            try{
                if (!rstCab.isLast()){
                    if (blnHayCam || objAsiDia.isDiarioModificado()){
                        if (isRegPro()){
                            rstCab.next();
                            cargarReg();
                        }
                    }
                    else{
                        rstCab.next();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickVisPreliminar() {
        }
        
        public boolean consultar() {
            consultarReg();
            return true;
        }
        
        public boolean eliminar() {
            try{
                if (!eliminarReg())
                    return false;
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast()){
                    rstCab.next();
                    cargarReg();
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }
                blnHayCam=false;
            }
            catch (java.sql.SQLException e){
                return true;
            }
            return true;            
        }
        
        public boolean imprimir() {
            if (objThrGUI==null){
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
        }
        
        public boolean insertar() {
            if (!insertarReg())
                return false;
            return true;
        }
        
        public boolean modificar() {
            if (!actualizarReg())
                return false;
            return true;
        }
        
        public boolean vistaPreliminar() {
            if (objThrGUI==null){
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
        }
    }
    
    
    private boolean cargarRegistros(){
        boolean blnRes=true;
        objTblMod.removeAllRows();
        arlDatInsPro.clear();
        arlDatInsNotPro.clear();
        
        String strInsProCodEmp="", strInsProCodLoc="", strInsProCodTipDoc="", strInsProCodDoc="", strInsProCodReg="";
        String strInsNotProCodEmp="", strInsNotProCodLoc="", strInsNotProCodTipDoc="", strInsNotProCodDoc="", strInsNotProCodReg="";
        int intFlg=0;
        double dblGtoCob=0.00;
        
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, b1.tx_desCor,";
                strSQL+=" a2.co_reg, a1.ne_numDoc, a1.fe_doc, a2.fe_ven,  a2.ne_diacre,  abs(a2.mo_pag) AS mo_pag, abs(a2.nd_abo) AS nd_abo,";
                //strSQL+=" (abs(a2.mo_pag) - abs(a2.nd_abo)) AS valPnd, (a4.fe_doc-a2.fe_ven) AS diaVcd, a1.co_cli";
                strSQL+=" (abs(a2.mo_pag) - abs(a2.nd_abo)) AS valPnd, ";
                strSQL+=" CASE WHEN abs(a2.mo_pag) > abs(a2.nd_abo) THEN ";
                strSQL+=" ('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven) ";
                strSQL+=" ELSE (a4.fe_doc-a2.fe_ven) END AS diaVcd,";
                strSQL+=" a1.co_cli,";
                
                //para dias
                if(optUniTieDia.isSelected()){
                    strSQL+=" CASE WHEN abs(a2.mo_pag) > abs(a2.nd_abo) THEN ";
                    strSQL+=" (((abs(a2.mo_pag) - abs(a2.nd_abo))*('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)*" + (Double.parseDouble(jspTasInt.getValue().toString())) + ")/36000)";
                    strSQL+=" ELSE ((abs(a3.nd_abo)*(a4.fe_doc-a2.fe_ven)*" + (Double.parseDouble(jspTasInt.getValue().toString())) + ")/36000) END  AS intCob";
                                        
                    strSQL+=" ,CASE WHEN abs(a2.mo_pag) > abs(a2.nd_abo) THEN ";
                    strSQL+=" (((abs(a2.mo_pag) - abs(a2.nd_abo))*('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)*" + (Double.parseDouble(jspGtoCob.getValue().toString())) + ")/36000)";
                    strSQL+=" ELSE ((abs(a3.nd_abo)*(a4.fe_doc-a2.fe_ven)*" + (Double.parseDouble(jspGtoCob.getValue().toString())) + ")/36000) END  AS gasCob";
                }
                //para meses
                if(optUniTieMes.isSelected()){
                    strSQL+=" CASE WHEN abs(a2.mo_pag) > abs(a2.nd_abo) THEN ";
                    strSQL+=" (((abs(a2.mo_pag) - abs(a2.nd_abo))*(('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)/30)*" + (Double.parseDouble(jspTasInt.getValue().toString())) + ")/1200)";
                    strSQL+=" ELSE ((abs(a3.nd_abo)*((a4.fe_doc-a2.fe_ven)/30)*" + (Double.parseDouble(jspTasInt.getValue().toString())) + ")/1200) END AS intCob";
                    
                    strSQL+=" ,CASE WHEN abs(a2.mo_pag) > abs(a2.nd_abo) THEN ";
                    strSQL+=" (((abs(a2.mo_pag) - abs(a2.nd_abo))*(('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)/30)*" + (Double.parseDouble(jspGtoCob.getValue().toString())) + ")/1200)";
                    strSQL+=" ELSE ((abs(a3.nd_abo)*((a4.fe_doc-a2.fe_ven)/30)*" + (Double.parseDouble(jspGtoCob.getValue().toString())) + ")/1200) END AS gasCob";
                }
                //para anios
                if(optUniTieAni.isSelected()){
                    strSQL+=" CASE WHEN abs(a2.mo_pag) > abs(a2.nd_abo) THEN ";
                    strSQL+=" (((abs(a2.mo_pag) - abs(a2.nd_abo))*(('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)/360)*" + (Double.parseDouble(jspTasInt.getValue().toString())) + ")/100) AS intCob";
                    strSQL+=" ELSE ((abs(a3.nd_abo)*((a4.fe_doc-a2.fe_ven)/360)*" + (Double.parseDouble(jspTasInt.getValue().toString())) + ")/100) END AS intCob";
                    
                    strSQL+=" ,CASE WHEN abs(a2.mo_pag) > abs(a2.nd_abo) THEN ";
                    strSQL+=" (((abs(a2.mo_pag) - abs(a2.nd_abo))*(('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)/360)*" + (Double.parseDouble(jspGtoCob.getValue().toString())) + ")/100) AS intCob";
                    strSQL+=" ELSE ((abs(a3.nd_abo)*((a4.fe_doc-a2.fe_ven)/360)*" + (Double.parseDouble(jspGtoCob.getValue().toString())) + ")/100) END AS gasCob";
                }
               
                strSQL+="      FROM";
                strSQL+="      tbm_cabTipDoc AS b1 INNER JOIN";
                strSQL+="      tbm_cabMovInv AS a1";
                strSQL+="      ON b1.co_emp=a1.co_emp AND b1.co_loc=a1.co_loc AND b1.co_tipDoc=a1.co_tipDoc";
                strSQL+="      INNER JOIN tbm_pagMovInv AS a2";
                strSQL+="      ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a2.st_reg IN('A','C')";
                strSQL+="      LEFT OUTER JOIN tbm_detPag AS a3";
                strSQL+="      ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_locPag AND a2.co_tipDoc=a3.co_tipDocPag AND a2.co_doc=a3.co_docPag AND a2.co_reg=a3.co_regPag";
                strSQL+="      INNER JOIN tbm_cabPag AS a4";
                strSQL+="      ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strSQL+="      WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                //PARA DOCUMENTOS CERRADOS
                if(chkMosDctCer.isSelected()){
                    strSQL+="      AND a2.fe_ven<('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') AND abs(mo_pag)<=abs(a2.nd_abo)";
                }
                //PARA DOCUMENTOS ABIERTOS
                if(chkMosDctAbi.isSelected()){
                    strSQL+="      AND a2.fe_ven<('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') AND abs(a2.mo_pag)>abs(a2.nd_abo)";
                }
                strSQL+="      AND b1.ne_mod IN(1,3) AND b1.tx_natDoc IN('E') AND a1.st_reg NOT IN('I','E')";
                strSQL+="      AND a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="      AND a4.st_reg NOT IN('I','E') AND a2.fe_ven<a4.fe_doc";
                strSQL+="      AND a1.co_cli IN(" + txtCodCli.getText() + ")";
                strSQL+="      AND a4.co_tipDoc NOT IN(62) AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
                strSQL+="      GROUP BY";
                strSQL+="      a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, b1.tx_desCor, a2.co_reg, a1.ne_numDoc, a2.fe_ven, a1.fe_doc, a2.ne_diacre,";
                strSQL+="      a2.mo_pag, a2.nd_abo, a3.nd_abo, a1.co_cli, a4.fe_doc";
                if(chkMosDctAbi.isSelected()){
                    strSQL+=" UNION";
                    strSQL+="     SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, b1.tx_desCor,";
                    strSQL+="      a2.co_reg, a1.ne_numDoc, a1.fe_doc, a2.fe_ven,  a2.ne_diacre,  abs(a2.mo_pag) AS mo_pag, abs(a2.nd_abo) AS nd_abo,";
                    strSQL+="      (abs(a2.mo_pag) - abs(a2.nd_abo)) AS valPnd, ('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven) AS diaVcd, a1.co_cli";
                    //para dias
                    if(optUniTieDia.isSelected()){
                        strSQL+=" ,(((abs(a2.mo_pag) - abs(a2.nd_abo))*('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)*" + (Double.parseDouble(jspTasInt.getValue().toString())) + ")/36000) AS intCob";
                        strSQL+=" ,(((abs(a2.mo_pag) - abs(a2.nd_abo))*('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)*" + (Double.parseDouble(jspGtoCob.getValue().toString())) + ")/36000) AS gasCob";
                    }

                    //para meses
                    if(optUniTieMes.isSelected()){
                        strSQL+=" ,(((abs(a2.mo_pag) - abs(a2.nd_abo))*(('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)/30)*" + (Double.parseDouble(jspTasInt.getValue().toString())) + ")/1200) AS intCob";
                        strSQL+=" ,(((abs(a2.mo_pag) - abs(a2.nd_abo))*(('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)/30)*" + (Double.parseDouble(jspGtoCob.getValue().toString())) + ")/1200) AS gasCob";                        
                    }

                    //para anios
                    if(optUniTieAni.isSelected()){
                        strSQL+=" ,(((abs(a2.mo_pag) - abs(a2.nd_abo))*(('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)/360)*" + (Double.parseDouble(jspTasInt.getValue().toString())) + ")/100) AS intCob";
                        strSQL+=" ,(((abs(a2.mo_pag) - abs(a2.nd_abo))*(('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)/360)*" + (Double.parseDouble(jspGtoCob.getValue().toString())) + ")/100) AS gasCob";
                    }

                        
                    strSQL+="      FROM tbm_pagMovInv AS a2 INNER JOIN tbm_cabMovInv AS a1";
                    strSQL+="      ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc ";
                    strSQL+="      INNER JOIN tbm_cabTipDoc AS b1";
                    strSQL+="      ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc";
                    strSQL+="      WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    //PARA DOCUMENTOS CERRADOS
                    if(chkMosDctCer.isSelected()){
                        strSQL+=" AND a2.fe_ven<('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') AND abs(mo_pag)<=abs(a2.nd_abo)";
                    }
                    //PARA DOCUMENTOS ABIERTOS
                    if(chkMosDctAbi.isSelected()){
                        strSQL+=" AND a2.fe_ven<('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') AND abs(a2.mo_pag)>abs(a2.nd_abo)";
                    }
                    strSQL+="      AND b1.ne_mod IN(1,3) AND b1.tx_natDoc IN('E') AND a1.st_reg NOT IN('I','E') AND a2.st_reg IN('A','C')";
                    strSQL+="      AND a1.co_cli IN(" + txtCodCli.getText() + ")";
                    strSQL+="      AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
                    strSQL+="      GROUP BY";
                    strSQL+="      a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, b1.tx_desCor, a2.co_reg, a1.ne_numDoc, a2.fe_ven, a1.fe_doc, a2.ne_diacre,";
                    strSQL+="      a2.mo_pag, a2.nd_abo, a1.co_cli";
                    strSQL+="      order by co_emp, co_loc, co_tipdoc, ne_numDoc, co_doc";
                }


                System.out.println("SQL cargarRegistros: " + strSQL);
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegInsPro=new ArrayList();
                    arlRegInsPro.add(INT_ARL_INS_PRO_COD_EMP,            "" + rst.getString("co_emp"));
                    arlRegInsPro.add(INT_ARL_INS_PRO_COD_LOC,            "" + rst.getString("co_loc"));
                    arlRegInsPro.add(INT_ARL_INS_PRO_COD_TIP_DOC,        "" + rst.getString("co_tipDoc"));
                    arlRegInsPro.add(INT_ARL_INS_PRO_DES_COR_TIP_DOC,    "" + rst.getString("tx_desCor"));
                    arlRegInsPro.add(INT_ARL_INS_PRO_COD_DOC,            "" + rst.getString("co_doc"));
                    arlRegInsPro.add(INT_ARL_INS_PRO_COD_REG,            "" + rst.getString("co_reg"));
                    arlRegInsPro.add(INT_ARL_INS_PRO_NUM_DOC,            "" + rst.getString("ne_numDoc"));
                    arlRegInsPro.add(INT_ARL_INS_PRO_FEC_DOC,            "" + rst.getString("fe_doc"));
                    arlRegInsPro.add(INT_ARL_INS_PRO_DIA_CRE,            "" + rst.getString("ne_diacre"));
                    arlRegInsPro.add(INT_ARL_INS_PRO_FEC_VEN,            "" + rst.getString("fe_ven"));
                    arlRegInsPro.add(INT_ARL_INS_PRO_VAL_DOC,            "" + rst.getString("mo_pag"));
                    arlRegInsPro.add(INT_ARL_INS_PRO_VAL_PEN,            "" + rst.getString("valPnd"));
                    arlRegInsPro.add(INT_ARL_INS_PRO_DIA_VCD,            "" + Math.abs(rst.getDouble("diaVcd")));
                    arlRegInsPro.add(INT_ARL_INS_PRO_INT_COB,            "" + Math.abs(rst.getDouble("intCob")));
                    arlRegInsPro.add(INT_ARL_INS_PRO_GAS_COB,            "" + Math.abs(rst.getDouble("gasCob")));
                    arlDatInsPro.add(arlRegInsPro);
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
                
                
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a3.co_emp, a3.co_locInt, a3.co_tipDocInt, a3.co_docInt, a3.co_regInt";
                strSQL+=" FROM (tbm_intMovInv AS a3 INNER JOIN tbm_cabMovInv AS a2";
                strSQL+=" ON a3.co_emp=a2.co_emp AND a3.co_loc=a2.co_loc AND a3.co_tipDoc=a2.co_tipDoc AND a3.co_doc=a2.co_doc";
                strSQL+=" ) INNER JOIN tbm_cabMovInv AS d1";
                strSQL+=" ON a3.co_emp=d1.co_emp AND a3.co_locInt=d1.co_loc AND a3.co_tipDocInt=d1.co_tipDoc AND a3.co_docInt=d1.co_doc";
                strSQL+=" WHERE a3.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a3.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND a2.st_reg NOT IN('I','E')";
                strSQL+=" GROUP BY a3.co_emp, a3.co_locInt, a3.co_tipDocInt, a3.co_docInt, a3.co_regInt";
                System.out.println("PROCESADOS: " + strSQL);
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegInsNotPro=new ArrayList();
                    arlRegInsNotPro.add(INT_ARL_INS_NOT_PRO_COD_EMP,         "" + rst.getString("co_emp"));
                    arlRegInsNotPro.add(INT_ARL_INS_NOT_PRO_COD_LOC,         "" + rst.getString("co_locInt"));
                    arlRegInsNotPro.add(INT_ARL_INS_NOT_PRO_COD_TIP_DOC,     "" + rst.getString("co_tipDocInt"));
                    arlRegInsNotPro.add(INT_ARL_INS_NOT_PRO_COD_DOC,         "" + rst.getString("co_docInt"));
                    arlRegInsNotPro.add(INT_ARL_INS_NOT_PRO_COD_REG,         "" + rst.getString("co_regInt"));
                    arlDatInsNotPro.add(arlRegInsNotPro);
                }
                
                for(int i=0; i<arlDatInsPro.size(); i++){
                    strInsProCodEmp=objUti.getStringValueAt(arlDatInsPro, i, INT_ARL_INS_PRO_COD_EMP);
                    strInsProCodLoc=objUti.getStringValueAt(arlDatInsPro, i, INT_ARL_INS_PRO_COD_LOC);
                    strInsProCodTipDoc=objUti.getStringValueAt(arlDatInsPro, i, INT_ARL_INS_PRO_COD_TIP_DOC);
                    strInsProCodDoc=objUti.getStringValueAt(arlDatInsPro, i, INT_ARL_INS_PRO_COD_DOC);
                    strInsProCodReg=objUti.getStringValueAt(arlDatInsPro, i, INT_ARL_INS_PRO_COD_REG);
                    intFlg=0;
                    for(int j=0; j<arlDatInsNotPro.size();j++){
                        strInsNotProCodEmp=objUti.getStringValueAt(arlDatInsNotPro, j, INT_ARL_INS_NOT_PRO_COD_EMP);
                        strInsNotProCodLoc=objUti.getStringValueAt(arlDatInsNotPro, j, INT_ARL_INS_NOT_PRO_COD_LOC);
                        strInsNotProCodTipDoc=objUti.getStringValueAt(arlDatInsNotPro, j, INT_ARL_INS_NOT_PRO_COD_TIP_DOC);
                        strInsNotProCodDoc=objUti.getStringValueAt(arlDatInsNotPro, j, INT_ARL_INS_NOT_PRO_COD_DOC);
                        strInsNotProCodReg=objUti.getStringValueAt(arlDatInsNotPro, j, INT_ARL_INS_NOT_PRO_COD_REG);
                        if(  (strInsProCodEmp.equals(strInsNotProCodEmp))  && (strInsProCodLoc.equals(strInsNotProCodLoc)) && (strInsProCodTipDoc.equals(strInsNotProCodTipDoc))  && (strInsProCodDoc.equals(strInsNotProCodDoc)) && (strInsProCodReg.equals(strInsNotProCodReg))  ){
                            intFlg++;
                        }
                    }
                    if(intFlg==0){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN, "");
                        vecReg.add(INT_TBL_DAT_CHK, "");
                        vecReg.add(INT_TBL_DAT_COD_EMP,         "" + strInsProCodEmp);
                        vecReg.add(INT_TBL_DAT_COD_LOC,         "" + strInsProCodLoc);
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     "" + strInsProCodTipDoc);
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, "" + objUti.getStringValueAt(arlDatInsPro, i, INT_ARL_INS_PRO_DES_COR_TIP_DOC));
                        vecReg.add(INT_TBL_DAT_COD_DOC,         "" + strInsProCodDoc);
                        vecReg.add(INT_TBL_DAT_COD_REG,         "" + strInsProCodReg);
                        vecReg.add(INT_TBL_DAT_NUM_DOC,         "" + objUti.getStringValueAt(arlDatInsPro, i, INT_ARL_INS_PRO_NUM_DOC));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,         "" + objUti.getStringValueAt(arlDatInsPro, i, INT_ARL_INS_PRO_FEC_DOC));
                        vecReg.add(INT_TBL_DAT_DIA_CRE,         "" + objUti.getStringValueAt(arlDatInsPro, i, INT_ARL_INS_PRO_DIA_CRE));
                        vecReg.add(INT_TBL_DAT_FEC_VNC,         "" + objUti.getStringValueAt(arlDatInsPro, i, INT_ARL_INS_PRO_FEC_VEN));
                        vecReg.add(INT_TBL_DAT_VAL_DOC,         "" + objUti.getStringValueAt(arlDatInsPro, i, INT_ARL_INS_PRO_VAL_DOC));
                        vecReg.add(INT_TBL_DAT_VAL_PND,         "" + objUti.getStringValueAt(arlDatInsPro, i, INT_ARL_INS_PRO_VAL_PEN));
                        vecReg.add(INT_TBL_DAT_DIA_VCD,         "" + objUti.getStringValueAt(arlDatInsPro, i, INT_ARL_INS_PRO_DIA_VCD));
                        vecReg.add(INT_TBL_DAT_INT_COB,         "" + objUti.getStringValueAt(arlDatInsPro, i, INT_ARL_INS_PRO_INT_COB));
                        vecReg.add(INT_TBL_DAT_GTO_COB,         "" + objUti.getStringValueAt(arlDatInsPro, i, INT_ARL_INS_PRO_GAS_COB));
                        vecDat.add(vecReg);
                    }
                    else{
                        System.out.println("NO MUESTRA ESE REGISTRO PORQ YA SE LO HA PROCESADO CON ANTERIORIDAD!!!!");
                    }
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
                
                con.close();
                con=null;
                
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblIntSim.setModel(objTblMod);
                vecDat.clear();
                
                //objTblTot.calcularTotales();
                calculaValorTotalInteres();
                calculaValorTotalGtosCobranzas();
                calculaValorTotalValorDcto();
                calculaValorTotalValorPndte();
                calculaValorTotalDocumento();
                objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDoc.getText(), txtMonDoc.getText());
                
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
    
    private void calculaValorTotalDocumento(){
        double dblValInt=0.00;
        double dblValGtoCob=0.00;
        double dblValDoc=0.00;
        dblValInt=Double.parseDouble(objTblTot.getValueAt(0, INT_TBL_DAT_INT_COB).toString());
        dblValGtoCob=Double.parseDouble(objTblTot.getValueAt(0, INT_TBL_DAT_GTO_COB).toString());
        
        dblValDoc=dblValInt+dblValGtoCob;
        txtMonDoc.setText("" + objUti.redondear(dblValDoc,objParSis.getDecimalesMostrar()));
    }
    
    
    
    private void calculaValorTotalInteres(){
        double dblValInt=0.00;
        for(int i=0;i<objTblMod.getRowCountTrue();i++){
//            if(objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString().equals("M")){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    dblValInt+=objTblMod.getValueAt(i, INT_TBL_DAT_INT_COB)==null?0:Double.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_INT_COB).toString());
                }
//            }
        }
        objTblTot.setValueAt("" + dblValInt, 0, INT_TBL_DAT_INT_COB);
    }
    
    
    
    private void calculaValorTotalGtosCobranzas(){
        double dblValGtoCob=0.00;
        for(int i=0;i<objTblMod.getRowCountTrue();i++){
            if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                dblValGtoCob+=objTblMod.getValueAt(i, INT_TBL_DAT_GTO_COB)==null?0:Double.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_GTO_COB).toString());
            }
        }
        objTblTot.setValueAt("" + dblValGtoCob, 0, INT_TBL_DAT_GTO_COB);
    }
    
    
    
    private void calculaValorTotalValorDcto(){
        double dblValDoc=0.00;
        for(int i=0;i<objTblMod.getRowCountTrue();i++){
            if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                dblValDoc+=objTblMod.getValueAt(i, INT_TBL_DAT_VAL_DOC)==null?0:Double.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_DOC).toString());
            }
        }
        objTblTot.setValueAt("" + dblValDoc, 0, INT_TBL_DAT_VAL_DOC);
    }
                
    private void calculaValorTotalValorPndte(){
        double dblValPnd=0.00;
        for(int i=0;i<objTblMod.getRowCountTrue();i++){
            if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                dblValPnd+=objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PND)==null?0:Double.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PND).toString());
            }
        }
        objTblTot.setValueAt("" + dblValPnd, 0, INT_TBL_DAT_VAL_PND);
    }
    
    
    
    
    private void calcularValorInteres(int filaSeleccionada){
        int intFilSelFecVncCam=filaSeleccionada;
        double dblValPnd=0.00;
        double dblPorAplEmp=0.00;
        double dblNumDia=0.00;
        double dblValIntCob=0.00;
        
        dblValPnd=Double.parseDouble(objTblMod.getValueAt(intFilSelFecVncCam, INT_TBL_DAT_VAL_PND)==null?"0.00":(objTblMod.getValueAt(intFilSelFecVncCam, INT_TBL_DAT_VAL_PND)==""?"0.00":objTblMod.getValueAt(intFilSelFecVncCam, INT_TBL_DAT_VAL_PND).toString()));
        dblPorAplEmp=(Double.parseDouble(jspTasInt.getValue().toString()));
        dblNumDia=Double.parseDouble(objTblMod.getValueAt(intFilSelFecVncCam, INT_TBL_DAT_DIA_VCD)==null?"0.00":(objTblMod.getValueAt(intFilSelFecVncCam, INT_TBL_DAT_DIA_VCD)==""?"0.00":objTblMod.getValueAt(intFilSelFecVncCam, INT_TBL_DAT_DIA_VCD).toString()));
        
        if(optUniTieDia.isSelected())
            dblValIntCob=(dblValPnd*dblNumDia*dblPorAplEmp)/36000;
        //para meses
        if(optUniTieMes.isSelected())
            dblValIntCob=(dblValPnd*(dblNumDia/30)*dblPorAplEmp)/1200;
        //para anios
        if(optUniTieAni.isSelected())
            dblValIntCob=(dblValPnd*(dblNumDia/360)*dblPorAplEmp)/100;

        objTblMod.setValueAt("" +dblValIntCob, intFilSelFecVncCam, INT_TBL_DAT_INT_COB);
    }
    
    
    
    private void calcularValorGtoCobranzas(int filaSeleccionada){
        int intFilSelFecVncCam=filaSeleccionada;
        double dblValPnd=0.00;
        double dblPorAplEmp=0.00;
        double dblNumDia=0.00;
        double dblValIntCob=0.00;
        
        dblValPnd=Double.parseDouble(objTblMod.getValueAt(intFilSelFecVncCam, INT_TBL_DAT_VAL_PND)==null?"0.00":(objTblMod.getValueAt(intFilSelFecVncCam, INT_TBL_DAT_VAL_PND)==""?"0.00":objTblMod.getValueAt(intFilSelFecVncCam, INT_TBL_DAT_VAL_PND).toString()));
        dblPorAplEmp=(Double.parseDouble(jspGtoCob.getValue().toString()));
        dblNumDia=Double.parseDouble(objTblMod.getValueAt(intFilSelFecVncCam, INT_TBL_DAT_DIA_VCD)==null?"0.00":(objTblMod.getValueAt(intFilSelFecVncCam, INT_TBL_DAT_DIA_VCD)==""?"0.00":objTblMod.getValueAt(intFilSelFecVncCam, INT_TBL_DAT_DIA_VCD).toString()));
        
        if(optUniTieDia.isSelected())
            dblValIntCob=(dblValPnd*dblNumDia*dblPorAplEmp)/36000;
        //para meses
        if(optUniTieMes.isSelected())
            dblValIntCob=(dblValPnd*(dblNumDia/30)*dblPorAplEmp)/1200;
        //para anios
        if(optUniTieAni.isSelected())
            dblValIntCob=(dblValPnd*(dblNumDia/360)*dblPorAplEmp)/100;

        objTblMod.setValueAt("" +dblValIntCob, intFilSelFecVncCam, INT_TBL_DAT_GTO_COB);
        
        
        
        
        
    }
    
    
    
    /*
    private void calcularValorGastosCobranzas(int filaSeleccionada){
        int intFilSelFecVncCam=filaSeleccionada;
        double dblValDoc=0.00;
        double dblValGtoCob=0.00;
        
        dblValDoc=Double.parseDouble(objTblMod.getValueAt(intFilSelFecVncCam, INT_TBL_DAT_VAL_DOC)==null?"0.00":(objTblMod.getValueAt(intFilSelFecVncCam, INT_TBL_DAT_VAL_DOC)==""?"0.00":objTblMod.getValueAt(intFilSelFecVncCam, INT_TBL_DAT_VAL_DOC).toString()));
       
        dblValGtoCob=(dblValDoc*dblPorGtoCob);

        objTblMod.setValueAt("" +dblValGtoCob, intFilSelFecVncCam, INT_TBL_DAT_GTO_COB);
    }
    */
    
    
    
    /**
     * Esta funci�n muestra un mensaje "showConfirmDialog". Presenta las opciones
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
     * Esta funci�n se utiliza para regenerar el asiento de diario. 
     * La idea principal de �sta funci�n es regenerar el asiento de diario. Pero,
     * puede darse el caso en el que el asiento de diario fue modificado manualmente
     * por el usuario. En  cuyo caso, primero se pregunta si desea regenerar el asiento
     * de diario, no regenerarlo o cancelar.
     * @return true: Si se decidi� regenerar/no regenerar el asiento de diario.
     * <BR>false: Si se cancel� la regeneraci�n del asiento de diario.
     * Nota.- Como se puede apreciar la funci�n retorna "false" s�lo cuando se di�
     * click en el bot�n "Cancelar".
     */
    private boolean regenerarDiario()
    {
        boolean blnRes=true;
        if (objAsiDia.getGeneracionDiario()==2)
        {
            strAux="�Desea regenerar el asiento de diario?\n";
            strAux+="El asiento de diario ha sido modificado manualmente.";
            strAux+="\nSi desea que el sistema regenere el asiento de diario de click en SI.";
            strAux+="\nSi desea grabar el asiento de diario tal como est� de click en NO.";
            strAux+="\nSi desea cancelar �sta operaci�n de click en CANCELAR.";
            switch (mostrarMsgCon(strAux))
            {
                case 0: //YES_OPTION
                    objAsiDia.setGeneracionDiario((byte)0);
                    objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDoc.getText(), txtMonDoc.getText());
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
            objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDoc.getText(), txtMonDoc.getText());
        }
        return blnRes;
    }
    

    /**
     * Esta funci�n inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());

            intSecGrp=objUltDocPrn.getNumSecDoc(con, objParSis.getCodigoEmpresaGrupo());
            intSecEmp=objUltDocPrn.getNumSecDoc(con, objParSis.getCodigoEmpresa());


            con.setAutoCommit(false);
            if (con!=null){
                if (insertarCabMovInv()){
                    if (insertarPagMovInv()){
                        if (insertarIntMovInv()){
                            if (objAsiDia.insertarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtCodDoc.getText(), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"))){
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
            con.close();
            con=null;
            intSecGrp=-1;
            intSecEmp=-1;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funci�n permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCabMovInv(){
        int intCodUsr, intUltReg;
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                intCodUsr=objParSis.getCodigoUsuario();
                //Obtener el c�digo del �ltimo registro.
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);
                //Obtener la fecha del servidor. --se usa para la fecha de ingreso
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabmovinv(";
                strSQL+="             co_emp, co_loc, co_tipdoc, co_doc, ne_numcot, ";
                strSQL+="             ne_numdoc, ne_numgui, fe_doc, fe_ven, co_cli, tx_ruc, tx_nomcli, tx_dircli, ";
                strSQL+="             tx_telcli, tx_ciucli, co_com, tx_nomven, tx_ate, co_forpag, tx_desforpag, ";
                strSQL+="             nd_sub, nd_tot, nd_poriva, tx_ptopar, tx_tra, co_mottra, tx_desmottra, ";
                strSQL+="             co_cta, co_motdoc, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, ";
                strSQL+="             co_usring, co_usrmod, fe_con, tx_obs3, co_forret, tx_numped, ";
                strSQL+="             co_dia, tx_vehret, tx_choret, ne_secgrp, ne_secemp, tx_numautsri, ";
                strSQL+="             tx_secdoc, tx_feccad, nd_valiva, ";
                strSQL+="             co_mnu, st_tipdev, ";
                strSQL+="             st_regrep, st_coninv, ne_numdocree, st_imp, ";
                strSQL+="             tx_obssolaut, tx_obsautsol, st_aut)";
                strSQL+="             VALUES(";
                strSQL+="" + objParSis.getCodigoEmpresa() + ",";
                strSQL+="" + objParSis.getCodigoLocal() + ",";
                strSQL+="" + txtCodTipDoc.getText() + ",";
                strSQL+="" + txtCodDoc.getText() + ",";
                //strSQL+=" Null,";
                strSQL+=" Null,";
                //strSQL+=" Null,";
                strSQL+="" + objUti.codificar(txtNumDoc.getText(),2) + ",";
                strSQL+=" Null,";
                strSQL+="'" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "',";
                strSQL+="'" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "',";
                strSQL+="" + objUti.codificar(txtCodCli.getText(),2) + ",";
                strSQL+="" + objUti.codificar(strIdeCli) + ",";
                strSQL+="'" + objUti.codificar(txtDesLarCli.getText(),2) + "',";
                strSQL+="" + objUti.codificar(strDirCli) + ",";
                strSQL+="" + objUti.codificar(strTelCli) + ",";
                strSQL+="" + objUti.codificar(strCiuCli) + ",";
                strSQL+=" Null,"; //co_com
                strSQL+=" Null,"; //tx_nomven
                strSQL+=" Null,"; //tx_ate
                strSQL+=" Null,"; //co_forpag
                strSQL+=" Null,"; //tx_desforpag
                strSQL+="" + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (Double.parseDouble(txtMonDoc.getText())):"0"),3) + ",";//nd_sub;
                strSQL+="" + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (Double.parseDouble(txtMonDoc.getText())):"0"),3) + ","; //nd_tot
                strSQL+=" Null,";//nd_poriva
                strSQL+=" Null,";//tx_ptopar
                strSQL+=" Null,";//tx_tra
                strSQL+=" Null,";//co_mottra
                strSQL+=" Null,";//tx_desmottra
                strSQL+=" Null,";//co_cta
                strSQL+=" Null,";//co_motdoc
                strSQL+="" + objUti.codificar(txaObs1.getText()) + ",";//tx_obs1
                strSQL+="" + objUti.codificar(txaObs2.getText()) + ",";//tx_obs2
                strSQL+="'A',";//st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+="'" + strAux + "',";//fe_ing
                strSQL+="'" + strAux + "',";//fe_ultmod
                strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usring
                strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usrmod
                strSQL+=" Null,";//fe_con
                strSQL+=" Null,";//tx_obs3
                strSQL+=" Null,";//co_forret
                strSQL+=" Null,";//tx_numped
                strSQL+="" + objUti.codificar(txtCodDoc.getText(),2) + ",";//co_dia
                strSQL+=" Null,";//tx_vehret
                strSQL+=" Null,";//tx_choret
                strSQL+="" + intSecGrp + ",";//ne_secgrp
                strSQL+="" + intSecEmp + ",";//ne_secemp
                strSQL+=" Null,";//tx_numautsri
                strSQL+=" Null,";//tx_secdoc
                strSQL+=" Null,";//tx_feccad
                strSQL+=" Null,";//nd_valiva
                strSQL+="" + objParSis.getCodigoMenu() + ",";//co_mnu
                strSQL+=" Null,";//st_tipdev
                strSQL+="'I',";//st_regrep
                strSQL+="'P',";//st_coninv
                strSQL+=" Null,";//ne_numdocree
                strSQL+=" 'N',";//st_imp
                strSQL+=" 'Null',";//tx_obssolaut
                strSQL+=" 'Null',";//tx_obsautsol
                strSQL+=" 'N'";//st_aut
                strSQL+=")";
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
     * Esta funci�n permite insertar los pagos de un documento.
     * @return true: Si se pudieron insertar los pagos del documento.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarPagMovInv(){
        boolean blnRes=true;
        int j=0;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="INSERT INTO tbm_pagmovinv(";
                strSQL+="             co_emp, co_loc, co_tipdoc, co_doc, co_reg, ne_diacre, fe_ven, ";
                strSQL+="             co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop, ";
                strSQL+="             st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq, ";
                strSQL+="             fe_venchq, nd_monchq, co_prochq, st_reg, st_regrep)";
                strSQL+="     VALUES (";
                strSQL+="" + objParSis.getCodigoEmpresa() + ",";//co_emp
                strSQL+="" + objParSis.getCodigoLocal() + ",";//co_loc
                strSQL+="" + txtCodTipDoc.getText() + ",";//co_tipdoc
                strSQL+="" + txtCodDoc.getText() + ",";//co_doc
                j++;
                strSQL+="" + j + ",";//co_reg
                strSQL+=" " + calculaDiasCredito() + ",";//ne_diacre
                strSQL+=" '" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "',";//fe_ven
                strSQL+=" Null,";//co_tipret
                strSQL+=" Null,";//nd_porret
                strSQL+=" Null,";//tx_aplret
                strSQL+="" + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (Double.parseDouble(txtMonDoc.getText())):"0"),3) + ",";//mo_pag
                strSQL+=" 0,";//ne_diagra
                strSQL+=" 0.00,";//nd_abo
                strSQL+=" 'N',";//st_sop
                strSQL+=" 'N',";//st_entsop
                strSQL+=" 'N',";//st_pos
                strSQL+=" Null,";//co_banchq
                strSQL+=" Null,";//tx_numctachq
                strSQL+=" Null,";//tx_numchq
                strSQL+=" Null,";//fe_recchq
                strSQL+=" Null,";//fe_venchq
                strSQL+=" Null,";//nd_monchq
                strSQL+=" Null,";//co_prochq
                strSQL+=" 'A',";//st_reg
                strSQL+=" 'I'";//st_regrep
                strSQL+=" )";
                stm.executeUpdate(strSQL);


                stm.close();
                stm=null;
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
     * Esta funci�n permite insertar los datos de intereses y gastos a cobrar.
     * @return true: Si se pudieron insertar registros de intereses y gastos por cobranzas del documento.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarIntMovInv(){
        boolean blnRes=true;
        int j=0;
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
//                    if(objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString().equals("M")){
                        if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                            strSQL="";
                            strSQL+="INSERT INTO tbm_intmovinv(";
                            strSQL+="             co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locint, co_tipdocint, ";
                            strSQL+="             co_docint, co_regint, fe_ven, ne_diaven, nd_int, nd_gascob, st_regRep)";
                            strSQL+="     VALUES (";
                            strSQL+="" + objParSis.getCodigoEmpresa() + ",";
                            strSQL+="" + objParSis.getCodigoLocal() + ",";
                            strSQL+="" + txtCodTipDoc.getText() + ",";
                            strSQL+="" + txtCodDoc.getText() + ",";
                            j++;
                            strSQL+="" + j + ",";//co_reg
                            strSQL+="" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC),3) + ",";//co_locint
                            strSQL+="" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC),3) + ",";//co_tipdocint
                            strSQL+="" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC),3) + ",";//co_docint
                            strSQL+="" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG),3) + ",";//co_regint
                            
                            
                            System.out.println(" LA FECHA CONTIENE: " + objTblMod.getValueAt(i, INT_TBL_DAT_FEC_VNC).toString());
                            
                            strSQL+="'" + objUti.formatearFecha(objTblMod.getValueAt(i, INT_TBL_DAT_FEC_VNC).toString(), "yyyy-MM-dd", objParSis.getFormatoFechaBaseDatos()) + "',";//fe_ven
                            strSQL+="" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_DIA_VCD),3) + ",";//ne_diaven
                            strSQL+="" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_INT_COB),3) + ",";//nd_int
                            strSQL+="" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_GTO_COB),3) + ",";//nd_gascob
                            strSQL+="'I'";
                            strSQL+=")";
                            System.out.println("SQL de insertarIntMovInv: " + strSQL);
                            stm.executeUpdate(strSQL);
                        }
//                    }
                }
                stm.close();
                stm=null;
                
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
     * Esta funci�n permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    
    private boolean consultarReg(){
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try{
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null){
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que s�lo se muestre los documentos asignados al programa.
                if (txtCodTipDoc.getText().equals("")){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                    strSQL+=" LEFT OUTER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    ///strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a5.co_mnu=" + objParSis.getCodigoMenu();
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    ///strSQL+=" AND a1.co_loc=" + intCodLoc;
                }
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc LIKE '" + strAux.replaceAll("'", "''") + "'";
                strAux=txtCodCli.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_cli LIKE '" + strAux.replaceAll("'", "''") + "'";
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
//                if (dtpFecVen.isFecha())
//                    strSQL+=" AND a1.fe_ven='" + objUti.formatearFecha(dtpFecVen.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc LIKE '" + strAux.replaceAll("'", "''") + "'";
                strAux=txtNumDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc LIKE '" + strAux.replaceAll("'", "''") + "'";
                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" ORDER BY a1.co_loc, a1.co_tipDoc, a1.co_doc";
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next()){
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                }
                else{
                    mostrarMsgInf("No se ha encontrado ning�n registro que cumpla el criterio de b�squeda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
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
     * Esta funci�n permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    
    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            if (cargarCabReg()){
                if (cargarDetReg()){
                    objAsiDia.consultarDiario(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                        calculaValorTotalValorDcto();
                        calculaValorTotalValorPndte();
                        calculaValorTotalInteres();
                        calculaValorTotalGtosCobranzas();
                        calculaValorTotalDocumento();
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
     * Esta funci�n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    
    private boolean cargarCabReg(){
        int intPosRel;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_cli, a1.tx_nomcli, a2.tx_desCor, a2.tx_desLar,";
                strSQL+=" a3.nd_tasInt, a1.fe_doc, a1.fe_ven, a1.ne_numdoc, a1.nd_tot,";
                strSQL+=" a1.co_cli, a1.tx_ruc, a1.tx_nomCli, a1.tx_dirCli, a1.tx_telCli, a1.tx_obs1, a1.tx_obs2, a1.st_reg";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_emp AS a3 ON a2.co_emp=a3.co_emp";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    dtpFecVnc.setText(objUti.formatearFecha(rst.getDate("fe_ven"),"dd/MM/yyyy"));
                    strAux=rst.getString("ne_numDoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);
                    jspTasInt.setModel(new javax.swing.SpinnerNumberModel(rst.getDouble("nd_tasInt"), intJspValMin, intJspValMax, intJspValInc));
                    
                    strAux=rst.getString("co_cli");
                    txtCodCli.setText((strAux==null)?"":strAux);
                    strIdeCli=rst.getString("tx_ruc");
                    strTelCli=rst.getString("tx_telCli");
                    
                    strAux=rst.getString("tx_nomCli");
                    txtDesLarCli.setText((strAux==null)?"":strAux);
                    strDirCli=rst.getString("tx_dirCli");
                    
                    
                    
                    txtMonDoc.setText("" + Math.abs(rst.getDouble("nd_tot")));
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));
                    
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Mostrar la posici�n relativa del registro.
            intPosRel=rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
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
     * Esta funci�n permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(){
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try{
            if (!txtCodCli.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    intCodEmp=objParSis.getCodigoEmpresa();
                    //Armar la sentencia SQL.

                    strSQL="";
                    strSQL+="SELECT y.codEmp, y.codLoc, y.codTipDoc, y.codDoc, y.codReg";
                    strSQL+="	, y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.tx_desCor, y.co_reg, y.ne_numDoc";
                    strSQL+="	, y.fe_doc, y.ne_diaCre, y.mo_pag, y.fe_ven, y.ne_diaVen, y.nd_int, y.nd_gasCob ";
                    strSQL+="	, y.valPnd";
                    strSQL+=" FROM(";
                    strSQL+="	SELECT a1.co_emp AS codEmp, a1.co_loc AS codLoc, a1.co_tipDoc AS codTipDoc, a1.co_doc AS codDoc, a1.co_reg AS codReg";
                    strSQL+="	,a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a4.tx_desCor, a2.co_reg, a3.ne_numDoc";
                    strSQL+=" 	, a3.fe_doc, a2.ne_diaCre, abs(a2.mo_pag) AS mo_pag, a1.fe_ven, a1.ne_diaVen, a1.nd_int, a1.nd_gasCob ";
                    strSQL+=" 	, (abs(a2.mo_pag) - abs(nd_abo)) AS valPnd";
                    strSQL+="	FROM tbm_intMovInv AS a1";
                    strSQL+="	INNER JOIN tbm_pagMovInv AS a2";
                    strSQL+="	ON a1.co_emp=a2.co_emp AND a1.co_locInt=a2.co_loc AND a1.co_tipDocInt=a2.co_tipDoc AND a1.co_docInt=a2.co_doc AND a1.co_regInt=a2.co_reg";
                    strSQL+="	INNER JOIN tbm_cabMovInv AS a3";
                    strSQL+="	ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                    strSQL+="	INNER JOIN tbm_cabTipDoc AS a4 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc";
                    strSQL+=" 	WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                    strSQL+="   AND a1.co_loc=" + rstCab.getString("co_loc") + "";
                    strSQL+="   AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                    strSQL+="   AND a1.co_doc=" + rstCab.getString("co_doc") + "";
                    strSQL+="    ) AS y";
                    strSQL+=" LEFT OUTER JOIN(";
                    strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg, a1.co_locInt, a1.co_tipDocInt, a1.co_docInt, a1.co_regInt";
                    strSQL+="	FROM tbm_intMovInv AS a1";
                    strSQL+=" 	INNER JOIN tbm_pagMovInv AS a2";
                    strSQL+="	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg";
                    strSQL+=" 	INNER JOIN tbm_cabMovInv AS a3";
                    strSQL+=" 	ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                    strSQL+=" 	WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                    strSQL+="   AND a1.co_loc=" + rstCab.getString("co_loc") + "";
                    strSQL+="   AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                    strSQL+="   AND a1.co_doc=" + rstCab.getString("co_doc") + " ) AS x";
                    strSQL+=" ON x.co_emp=y.codEmp AND x.co_loc=y.codLoc AND x.co_tipDoc=y.codTipDoc AND x.co_doc=y.codDoc AND x.co_reg=y.codReg";
                    System.out.println("EN CARGARDETREG: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_CHK,null);
                        vecReg.add(INT_TBL_DAT_COD_EMP,        "" + rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,        "" + rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,    "" + rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC,"" + rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,        "" + rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_COD_REG,        "" + rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,        "" + rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,        "" + rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_DIA_CRE,        "" + rst.getString("ne_diaCre"));
                        vecReg.add(INT_TBL_DAT_FEC_VNC,        "" + rst.getString("fe_ven"));
                        vecReg.add(INT_TBL_DAT_VAL_DOC,        "" + rst.getString("mo_pag"));
                        vecReg.add(INT_TBL_DAT_VAL_PND,        "" + rst.getString("valPnd"));
                        vecReg.add(INT_TBL_DAT_DIA_VCD,        "" + rst.getString("ne_diaVen"));
                        vecReg.add(INT_TBL_DAT_INT_COB,        "" + rst.getString("nd_int"));
                        vecReg.add(INT_TBL_DAT_GTO_COB,        "" + rst.getString("nd_gasCob"));
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
                    tblIntSim.setModel(objTblMod);
                    vecDat.clear();
                }
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
     * Esta funci�n obtiene la descripci�n larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripci�n larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la funci�n devuelve una cadena vac�a.
     */
    private String getEstReg(String estado){
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0)){
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
                    estado="Autorizaci�n denegada";
                    break;
                case 'R':
                    estado="Pendiente de impresi�n";
                    break;
                case 'C':
                    estado="Pendiente confirmaci�n de inventario";
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
    
    
    
    
    
    
    private int calculaDiasCredito(){
        String strFecDocCalDiaCre="";
        String strFecVncCalDiaCre="";
        int intDiaVcdCal=0;
        try{
            strFecDocCalDiaCre=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
            strFecVncCalDiaCre=objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
            
            Connection conTmp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            Statement stmTmp;
            ResultSet rstTmp;
            if(conTmp!=null){
                stmTmp=conTmp.createStatement();
                strAux="";
                strAux+="select cast('" + strFecVncCalDiaCre + "' as date) - cast('" + strFecDocCalDiaCre + "' as date) AS diaVcd";
                rstTmp=stmTmp.executeQuery(strAux);
                if(rstTmp.next()){
                    intDiaVcdCal=rstTmp.getInt("diaVcd");
                }
                conTmp.close();
                conTmp=null;
                stmTmp.close();
                stmTmp=null;
                rstTmp.close();
                rstTmp=null;
            }

        }
        catch(java.sql.SQLException e){
            System.err.println(e.getMessage());
        }
        return intDiaVcdCal;
    }
    
    
    
    
    
    
    private void limpiarFrm(){
        try{
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodCli.setText("");
            txtDesLarCli.setText("");
            txtCodDoc.setText("");
            jspTasInt.setModel(new javax.swing.SpinnerNumberModel(intJspValIni, intJspValMin, intJspValMax, intJspValInc));
            txtNumDoc.setText("");
            txtMonDoc.setText("");
            optUniTieDia.setSelected(true);
            optUniTieMes.setSelected(false);
            optUniTieAni.setSelected(false);
            chkMosDctAbi.setSelected(false);
            chkMosDctCer.setSelected(false);
            txaObs1.setText("");
            txaObs2.setText("");
            tblIntSim.removeAll();
            tblIntSimTot.removeAll();
            dtpFecDoc.setText("");
            dtpFecVnc.setText("");
            objTblMod.removeAllRows();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    
    private boolean cargarRegModificar(){
        boolean blnRes=true;
        arlDatPro.clear();
        arlDatNotPro.clear();
        String strCodEmpPro="", strCodEmpNotPro="";
        String strCodLocPro="", strCodLocNotPro="";
        String strCodTipDocPro="", strCodTipDocNotPro="";
        String strCodDocPro="", strCodDocNotPro="";
        String strCodRegPro="", strCodRegNotPro="";
        String strEstArl="";
        vecDat.clear();
        
        String strCodEmpTmp="", strCodLocTmp="", strCodTipDocTmp="", strCodDocTmp="", strCodRegTmp="";
        
        double dblGtoCob=0.00;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                isDocumentoProcesado();
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a3.co_locInt, a3.co_tipDocInt, a3.co_docInt, a3.co_regInt, d3.tx_desCor, d1.ne_numDoc, d1.fe_doc, d2.ne_diaCre,";
                strSQL+=" a3.fe_ven, abs(d2.mo_pag) AS mo_pag, (abs(d2.mo_pag) - abs(d2.nd_abo)) AS valPnd,";
                strSQL+=" a3.ne_diaven AS diaVcd, a3.nd_int, a3.nd_gasCob";
                strSQL+=" FROM (tbm_intMovInv AS a3 INNER JOIN tbm_cabMovInv AS a2";
                strSQL+=" ON a3.co_emp=a2.co_emp AND a3.co_loc=a2.co_loc AND a3.co_tipDoc=a2.co_tipDoc AND a3.co_doc=a2.co_doc";
                strSQL+=" ) INNER JOIN";
                strSQL+=" tbm_pagMovInv AS d2";
                strSQL+=" ON a3.co_emp=d2.co_emp AND a3.co_locInt=d2.co_loc AND a3.co_tipDocInt=d2.co_tipDoc AND a3.co_docInt=d2.co_doc AND a3.co_regInt=d2.co_reg";
                strSQL+=" INNER JOIN tbm_cabMovInv AS d1";
                strSQL+=" ON d2.co_emp=d1.co_emp AND d2.co_loc=d1.co_loc AND d2.co_tipDoc=d1.co_tipDoc AND d2.co_doc=d1.co_doc";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS d3";
                strSQL+=" ON d1.co_emp=d3.co_emp AND d1.co_loc=d3.co_loc AND d1.co_tipDoc=d3.co_tipDoc";
                strSQL+=" WHERE a3.co_emp=" + rstCab.getString("co_emp") + "";
                strSQL+=" AND a3.co_loc=" + rstCab.getString("co_loc") + "";
                strSQL+=" AND a3.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                strSQL+=" AND a3.co_doc=" + rstCab.getString("co_doc") + "";
                strSQL+=" AND a2.st_reg NOT IN('I','E')";
                strSQL+=" GROUP BY a3.co_locInt, a3.co_tipDocInt, a3.co_docInt, a3.co_regInt, d3.tx_desCor, d1.ne_numDoc, d1.fe_doc, d2.ne_diaCre,";
                strSQL+=" a3.fe_ven, d2.mo_pag, d2.nd_abo,";
                strSQL+=" a3.nd_int, a3.nd_gasCob, a3.ne_diaven";
                System.out.println("1.- SQL : " + strSQL);
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegPro=new ArrayList();
                    arlRegPro.add(INT_ARL_PRO_COD_EMP,         "" + objParSis.getCodigoEmpresa());
                    arlRegPro.add(INT_ARL_PRO_COD_LOC_INT,     "" + rst.getString("co_locInt"));
                    arlRegPro.add(INT_ARL_PRO_COD_TIP_DOC_INT, "" + rst.getString("co_tipDocInt"));
                    arlRegPro.add(INT_ARL_PRO_COD_DOC_INT,     "" + rst.getString("co_docInt"));
                    arlRegPro.add(INT_ARL_PRO_COD_REG_INT,     "" + rst.getString("co_regInt"));
                    arlRegPro.add(INT_ARL_PRO_DES_COR,         "" + rst.getString("tx_desCor"));
                    arlRegPro.add(INT_ARL_PRO_NUM_DOC,         "" + rst.getString("ne_numDoc"));
                    arlRegPro.add(INT_ARL_PRO_FEC_DOC,         "" + rst.getString("fe_doc"));
                    arlRegPro.add(INT_ARL_PRO_DIA_CRE,         "" + rst.getString("ne_diaCre"));
                    arlRegPro.add(INT_ARL_PRO_FEC_VEN,         "" + rst.getString("fe_ven"));
                    arlRegPro.add(INT_ARL_PRO_MON_PAG,         "" + rst.getString("mo_pag"));
                    arlRegPro.add(INT_ARL_PRO_VAL_PND,         "" + rst.getString("valPnd"));
                    arlRegPro.add(INT_ARL_PRO_DIA_VCD,         "" + Math.abs(rst.getDouble("diaVcd")));
                    arlRegPro.add(INT_ARL_PRO_INT,             "" + Math.abs(rst.getDouble("nd_int")));
                    arlRegPro.add(INT_ARL_PRO_GAS_COB,         "" + Math.abs(rst.getDouble("nd_gasCob")));
                    arlRegPro.add(INT_ARL_PRO_EST,             "N");
                    arlDatPro.add(arlRegPro);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, b1.tx_desCor,";
                strSQL+=" a2.co_reg, a1.ne_numDoc, a1.fe_doc, a2.fe_ven,  a2.ne_diacre,  abs(a2.mo_pag) AS mo_pag, abs(a2.nd_abo) AS nd_abo,";
                strSQL+=" (abs(a2.mo_pag) - abs(a2.nd_abo)) AS valPnd, ";
                strSQL+=" CASE WHEN abs(a2.mo_pag) > abs(a2.nd_abo) THEN ";
                strSQL+=" ('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven) ";
                strSQL+=" ELSE (a4.fe_doc-a2.fe_ven) END AS diaVcd,";
                strSQL+=" a1.co_cli,";
                
                //para dias
                if(optUniTieDia.isSelected()){
                    strSQL+=" CASE WHEN abs(a2.mo_pag) > abs(a2.nd_abo) THEN ";
                    strSQL+=" (((abs(a2.mo_pag) - abs(a2.nd_abo))*('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)*" + (Double.parseDouble(jspTasInt.getValue().toString())) + ")/36000)";
                    strSQL+=" ELSE ((abs(a3.nd_abo)*(a4.fe_doc-a2.fe_ven)*" + (Double.parseDouble(jspTasInt.getValue().toString())) + ")/36000) END  AS intCob";
                    
                    strSQL+=" ,CASE WHEN abs(a2.mo_pag) > abs(a2.nd_abo) THEN ";
                    strSQL+=" (((abs(a2.mo_pag) - abs(a2.nd_abo))*('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)*" + (Double.parseDouble(jspGtoCob.getValue().toString())) + ")/36000)";
                    strSQL+=" ELSE ((abs(a3.nd_abo)*(a4.fe_doc-a2.fe_ven)*" + (Double.parseDouble(jspGtoCob.getValue().toString())) + ")/36000) END  AS gasCob";
                }
                //para meses
                if(optUniTieMes.isSelected()){
                    strSQL+=" CASE WHEN abs(a2.mo_pag) > abs(a2.nd_abo) THEN ";
                    strSQL+=" (((abs(a2.mo_pag) - abs(a2.nd_abo))*(('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)/30)*" + (Double.parseDouble(jspTasInt.getValue().toString())) + ")/1200)";
                    strSQL+=" ELSE ((abs(a3.nd_abo)*((a4.fe_doc-a2.fe_ven)/30)*" + (Double.parseDouble(jspTasInt.getValue().toString())) + ")/1200) END AS intCob";
                    
                    strSQL+=" ,CASE WHEN abs(a2.mo_pag) > abs(a2.nd_abo) THEN ";
                    strSQL+=" (((abs(a2.mo_pag) - abs(a2.nd_abo))*(('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)/30)*" + (Double.parseDouble(jspGtoCob.getValue().toString())) + ")/1200)";
                    strSQL+=" ELSE ((abs(a3.nd_abo)*((a4.fe_doc-a2.fe_ven)/30)*" + (Double.parseDouble(jspGtoCob.getValue().toString())) + ")/1200) END AS gasCob";
                }
                //para anios
                if(optUniTieAni.isSelected()){
                    strSQL+=" CASE WHEN abs(a2.mo_pag) > abs(a2.nd_abo) THEN ";
                    strSQL+=" (((abs(a2.mo_pag) - abs(a2.nd_abo))*(('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)/360)*" + (Double.parseDouble(jspTasInt.getValue().toString())) + ")/100) AS intCob";
                    strSQL+=" ELSE ((abs(a3.nd_abo)*((a4.fe_doc-a2.fe_ven)/360)*" + (Double.parseDouble(jspTasInt.getValue().toString())) + ")/100) END AS intCob";
                    
                    strSQL+=" ,CASE WHEN abs(a2.mo_pag) > abs(a2.nd_abo) THEN ";
                    strSQL+=" (((abs(a2.mo_pag) - abs(a2.nd_abo))*(('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)/360)*" + (Double.parseDouble(jspGtoCob.getValue().toString())) + ")/100) AS intCob";
                    strSQL+=" ELSE ((abs(a3.nd_abo)*((a4.fe_doc-a2.fe_ven)/360)*" + (Double.parseDouble(jspGtoCob.getValue().toString())) + ")/100) END AS gasCob";
                }
               
                strSQL+="      FROM";
                strSQL+="      tbm_cabTipDoc AS b1 INNER JOIN";
                strSQL+="      tbm_cabMovInv AS a1";
                strSQL+="      ON b1.co_emp=a1.co_emp AND b1.co_loc=a1.co_loc AND b1.co_tipDoc=a1.co_tipDoc";
                strSQL+="      INNER JOIN tbm_pagMovInv AS a2";
                strSQL+="      ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a2.st_reg IN('A','C')";
                strSQL+="      LEFT OUTER JOIN tbm_detPag AS a3";
                strSQL+="      ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_locPag AND a2.co_tipDoc=a3.co_tipDocPag AND a2.co_doc=a3.co_docPag AND a2.co_reg=a3.co_regPag";
                strSQL+="      INNER JOIN tbm_cabPag AS a4";
                strSQL+="      ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strSQL+="      WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                //PARA DOCUMENTOS CERRADOS
                if(chkMosDctCer.isSelected()){
                    strSQL+="      AND a2.fe_ven<('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') AND abs(mo_pag)<=abs(a2.nd_abo)";
                }
                //PARA DOCUMENTOS ABIERTOS
                if(chkMosDctAbi.isSelected()){
                    strSQL+="      AND a2.fe_ven<('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') AND abs(a2.mo_pag)>abs(a2.nd_abo)";
                }
                strSQL+="      AND b1.ne_mod IN(1,3) AND b1.tx_natDoc IN('E') AND a1.st_reg NOT IN('I','E')";
                strSQL+="      AND a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="      AND a4.st_reg NOT IN('I','E') AND a2.fe_ven<a4.fe_doc";
                strSQL+="      AND a1.co_cli IN(" + txtCodCli.getText() + ")";
                strSQL+="      AND a4.co_tipDoc NOT IN(62) AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
                strSQL+="      GROUP BY";
                strSQL+="      a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, b1.tx_desCor, a2.co_reg, a1.ne_numDoc, a2.fe_ven, a1.fe_doc, a2.ne_diacre,";
                strSQL+="      a2.mo_pag, a2.nd_abo, a3.nd_abo, a1.co_cli, a4.fe_doc";
                if(chkMosDctAbi.isSelected()){
                    strSQL+=" UNION";
                    strSQL+="     SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, b1.tx_desCor,";
                    strSQL+="      a2.co_reg, a1.ne_numDoc, a1.fe_doc, a2.fe_ven,  a2.ne_diacre,  abs(a2.mo_pag) AS mo_pag, abs(a2.nd_abo) AS nd_abo,";
                    strSQL+="      (abs(a2.mo_pag) - abs(a2.nd_abo)) AS valPnd, ('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven) AS diaVcd, a1.co_cli";
                    //para dias
                    if(optUniTieDia.isSelected()){
                        strSQL+=" ,(((abs(a2.mo_pag) - abs(a2.nd_abo))*('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)*" + (Double.parseDouble(jspTasInt.getValue().toString())) + ")/36000) AS intCob";
                        strSQL+=" ,(((abs(a2.mo_pag) - abs(a2.nd_abo))*('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)*" + (Double.parseDouble(jspGtoCob.getValue().toString())) + ")/36000) AS gasCob";
                    }
                    
                    //para meses
                    if(optUniTieMes.isSelected()){
                        strSQL+=" ,(((abs(a2.mo_pag) - abs(a2.nd_abo))*(('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)/30)*" + (Double.parseDouble(jspTasInt.getValue().toString())) + ")/1200) AS intCob";
                        strSQL+=" ,(((abs(a2.mo_pag) - abs(a2.nd_abo))*(('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)/30)*" + (Double.parseDouble(jspGtoCob.getValue().toString())) + ")/1200) AS gasCob";
                    }
                    //para anios
                    if(optUniTieAni.isSelected()){
                        strSQL+=" ,(((abs(a2.mo_pag) - abs(a2.nd_abo))*(('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)/360)*" + (Double.parseDouble(jspTasInt.getValue().toString())) + ")/100) AS intCob";
                        strSQL+=" ,(((abs(a2.mo_pag) - abs(a2.nd_abo))*(('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'-a2.fe_ven)/360)*" + (Double.parseDouble(jspGtoCob.getValue().toString())) + ")/100) AS gasCob";
                    }
                    
                    strSQL+="      FROM tbm_pagMovInv AS a2 INNER JOIN tbm_cabMovInv AS a1";
                    strSQL+="      ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc ";
                    strSQL+="      INNER JOIN tbm_cabTipDoc AS b1";
                    strSQL+="      ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc";
                    strSQL+="      WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    //PARA DOCUMENTOS CERRADOS
                    if(chkMosDctCer.isSelected()){
                        strSQL+=" AND a2.fe_ven<('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') AND abs(mo_pag)<=abs(a2.nd_abo)";
                    }
                    //PARA DOCUMENTOS ABIERTOS
                    if(chkMosDctAbi.isSelected()){
                        strSQL+=" AND a2.fe_ven<('" + objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "') AND abs(a2.mo_pag)>abs(a2.nd_abo)";
                    }
                    strSQL+="      AND b1.ne_mod IN(1,3) AND b1.tx_natDoc IN('E') AND a1.st_reg NOT IN('I','E') AND a2.st_reg IN('A','C')";
                    strSQL+="      AND a1.co_cli IN(" + txtCodCli.getText() + ")";
                    strSQL+="      AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
                    strSQL+="      GROUP BY";
                    strSQL+="      a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, b1.tx_desCor, a2.co_reg, a1.ne_numDoc, a2.fe_ven, a1.fe_doc, a2.ne_diacre,";
                    strSQL+="      a2.mo_pag, a2.nd_abo, a1.co_cli";
                    strSQL+="      order by co_emp, co_loc, co_tipdoc, ne_numDoc, co_doc";
                }                                
               
                System.out.println("2.- SQL : " + strSQL);
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegNotPro=new ArrayList();
                    arlRegNotPro.add(INT_ARL_NOT_PRO_COD_EMP,         "" + rst.getString("co_emp"));
                    arlRegNotPro.add(INT_ARL_NOT_PRO_COD_LOC_INT,     "" + rst.getString("co_loc"));
                    arlRegNotPro.add(INT_ARL_NOT_PRO_COD_TIP_DOC_INT, "" + rst.getString("co_tipDoc"));
                    arlRegNotPro.add(INT_ARL_NOT_PRO_COD_DOC_INT,     "" + rst.getString("co_doc"));
                    arlRegNotPro.add(INT_ARL_NOT_PRO_COD_REG_INT,     "" + rst.getString("co_reg"));
                    arlRegNotPro.add(INT_ARL_NOT_PRO_DES_COR,         "" + rst.getString("tx_desCor"));
                    arlRegNotPro.add(INT_ARL_NOT_PRO_NUM_DOC,         "" + rst.getString("ne_numDoc"));
                    arlRegNotPro.add(INT_ARL_NOT_PRO_FEC_DOC,         "" + rst.getString("fe_doc"));
                    arlRegNotPro.add(INT_ARL_NOT_PRO_DIA_CRE,         "" + rst.getString("ne_diacre"));
                    arlRegNotPro.add(INT_ARL_NOT_PRO_FEC_VEN,         "" + rst.getString("fe_ven"));
                    arlRegNotPro.add(INT_ARL_NOT_PRO_MON_PAG,         "" + rst.getString("mo_pag"));
                    arlRegNotPro.add(INT_ARL_NOT_PRO_VAL_PND,         "" + rst.getString("valPnd"));
                    arlRegNotPro.add(INT_ARL_NOT_PRO_DIA_VCD,         "" + Math.abs(rst.getDouble("diaVcd")));
                    arlRegNotPro.add(INT_ARL_NOT_PRO_INT,             "" + Math.abs(rst.getDouble("intCob")));
                    arlRegNotPro.add(INT_ARL_NOT_PRO_GAS_COB,         "" + Math.abs(rst.getDouble("gasCob")));
                    arlRegNotPro.add(INT_ARL_NOT_PRO_EST,             "N");
                    arlDatNotPro.add(arlRegNotPro);
                }
                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                
                
                System.out.println("EL PRIMER ARRAYLIST CONTIENE:  " + arlDatPro.toString());
                System.out.println("EL SEGUNDO ARRAYLIST CONTIENE: " + arlDatNotPro.toString());
                
                
                
                for(int i=0; i<arlDatPro.size(); i++){
                    strCodEmpPro=objUti.getStringValueAt(arlDatPro, i, INT_ARL_PRO_COD_EMP);
                    strCodLocPro=objUti.getStringValueAt(arlDatPro, i, INT_ARL_PRO_COD_LOC_INT);
                    strCodTipDocPro=objUti.getStringValueAt(arlDatPro, i, INT_ARL_PRO_COD_TIP_DOC_INT);
                    strCodDocPro=objUti.getStringValueAt(arlDatPro, i, INT_ARL_PRO_COD_DOC_INT);
                    strCodRegPro=objUti.getStringValueAt(arlDatPro, i, INT_ARL_PRO_COD_REG_INT);
                   
                    for(int j=0; j<arlDatNotPro.size(); j++){
                        strCodEmpNotPro=objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_COD_EMP);
                        strCodLocNotPro=objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_COD_LOC_INT);
                        strCodTipDocNotPro=objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_COD_TIP_DOC_INT);
                        strCodDocNotPro=objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_COD_DOC_INT);
                        strCodRegNotPro=objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_COD_REG_INT);
                        
                       if(  (strCodEmpPro.equals(strCodEmpNotPro)) && (strCodLocPro.equals(strCodLocNotPro)) && (strCodTipDocPro.equals(strCodTipDocNotPro)) && (strCodDocPro.equals(strCodDocNotPro)) && (strCodRegPro.equals(strCodRegNotPro))   ){
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN, "");
                            vecReg.add(INT_TBL_DAT_CHK, "");
                            vecReg.add(INT_TBL_DAT_COD_EMP,         "" + strCodEmpPro);
                            vecReg.add(INT_TBL_DAT_COD_LOC,         "" + strCodLocPro);
                            vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     "" + strCodTipDocPro);
                            vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, "" + objUti.getStringValueAt(arlDatPro, i, INT_ARL_PRO_DES_COR));
                            vecReg.add(INT_TBL_DAT_COD_DOC,         "" + strCodDocPro);
                            vecReg.add(INT_TBL_DAT_COD_REG,         "" + strCodRegPro);
                            vecReg.add(INT_TBL_DAT_NUM_DOC,         "" + objUti.getStringValueAt(arlDatPro, i, INT_ARL_PRO_NUM_DOC));
                            vecReg.add(INT_TBL_DAT_FEC_DOC,         "" + objUti.getStringValueAt(arlDatPro, i, INT_ARL_PRO_FEC_DOC));
                            vecReg.add(INT_TBL_DAT_DIA_CRE,         "" + objUti.getStringValueAt(arlDatPro, i, INT_ARL_PRO_DIA_CRE));
                            vecReg.add(INT_TBL_DAT_FEC_VNC,         "" + objUti.getStringValueAt(arlDatPro, i, INT_ARL_PRO_FEC_VEN));
                            vecReg.add(INT_TBL_DAT_VAL_DOC,         "" + objUti.getDoubleValueAt(arlDatPro, i, INT_ARL_PRO_MON_PAG));
                            vecReg.add(INT_TBL_DAT_VAL_PND,         "" + objUti.getDoubleValueAt(arlDatPro, i, INT_ARL_PRO_VAL_PND));
                            vecReg.add(INT_TBL_DAT_DIA_VCD,         "" + objUti.getDoubleValueAt(arlDatPro, i, INT_ARL_PRO_DIA_VCD));
                            vecReg.add(INT_TBL_DAT_INT_COB,         "" + objUti.getDoubleValueAt(arlDatPro, i, INT_ARL_PRO_INT));
                            
                            
                            vecReg.add(INT_TBL_DAT_GTO_COB,         "" + objUti.getDoubleValueAt(arlDatPro, i, INT_ARL_PRO_GAS_COB));
                            vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_CHK);
                            vecDat.add(vecReg);
                            objUti.setStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_EST, "S");
                            objUti.setStringValueAt(arlDatPro,    i, INT_ARL_PRO_EST, "S");
                            
                            
                            break;
                        }
                   }
                }

                
                
                
/////////////////////////////////////////////////////////////                
                strCodEmpPro="";
                strCodLocPro="";
                strCodTipDocPro="";
                strCodDocPro="";
                strCodRegPro="";
                int bandera=0;
                
                for(int j=0; j<arlDatPro.size(); j++){
                    strEstArl=objUti.getStringValueAt(arlDatPro, j, INT_ARL_PRO_EST);
                    strCodEmpPro=objUti.getStringValueAt(arlDatPro, j, INT_ARL_PRO_COD_EMP);
                    strCodLocNotPro=objUti.getStringValueAt(arlDatPro, j, INT_ARL_PRO_COD_LOC_INT);
                    strCodTipDocNotPro=objUti.getStringValueAt(arlDatPro, j, INT_ARL_PRO_COD_TIP_DOC_INT);
                    strCodDocNotPro=objUti.getStringValueAt(arlDatPro, j, INT_ARL_PRO_COD_DOC_INT);
                    strCodRegNotPro=objUti.getStringValueAt(arlDatPro, j, INT_ARL_PRO_COD_REG_INT);
                    bandera=0;
                    if( ! strEstArl.equals("S")){
                        for(int y=0; y<arlDatDocPrc.size(); y++){
                            strCodEmpTmp=objUti.getStringValueAt(arlDatDocPrc, y, INT_ARL_DOC_PRC_COD_EMP);
                            strCodLocTmp=objUti.getStringValueAt(arlDatDocPrc, y, INT_ARL_DOC_PRC_COD_LOC);
                            strCodTipDocTmp=objUti.getStringValueAt(arlDatDocPrc, y, INT_ARL_DOC_PRC_COD_TIP_DOC);
                            strCodDocTmp=objUti.getStringValueAt(arlDatDocPrc, y, INT_ARL_DOC_PRC_COD_DOC);
                            strCodRegTmp=objUti.getStringValueAt(arlDatDocPrc, y, INT_ARL_DOC_PRC_COD_REG);
                            
                            if( (  (strCodEmpPro.equals(strCodEmpTmp))  &&  (strCodLocPro.equals(strCodLocTmp))   &&  (strCodTipDocPro.equals(strCodTipDocTmp))  &&  (strCodDocPro.equals(strCodDocTmp))  && (strCodRegPro.equals(strCodRegTmp))  )   ){
                                bandera++;
                            }                            
                        }
                        if(bandera==0){
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN, "");
                            vecReg.add(INT_TBL_DAT_CHK, "");
                            vecReg.add(INT_TBL_DAT_COD_EMP,         "" + objUti.getStringValueAt(arlDatPro, j, INT_ARL_PRO_COD_EMP));
                            vecReg.add(INT_TBL_DAT_COD_LOC,         "" + objUti.getStringValueAt(arlDatPro, j, INT_ARL_PRO_COD_LOC_INT));
                            vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     "" + objUti.getStringValueAt(arlDatPro, j, INT_ARL_PRO_COD_TIP_DOC_INT));
                            vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, "" + objUti.getStringValueAt(arlDatPro, j, INT_ARL_PRO_DES_COR));
                            vecReg.add(INT_TBL_DAT_COD_DOC,         "" + objUti.getStringValueAt(arlDatPro, j, INT_ARL_PRO_COD_DOC_INT));
                            vecReg.add(INT_TBL_DAT_COD_REG,         "" + objUti.getStringValueAt(arlDatPro, j, INT_ARL_PRO_COD_REG_INT));
                            vecReg.add(INT_TBL_DAT_NUM_DOC,         "" + objUti.getStringValueAt(arlDatPro, j, INT_ARL_PRO_NUM_DOC));
                            vecReg.add(INT_TBL_DAT_FEC_DOC,         "" + objUti.getStringValueAt(arlDatPro, j, INT_ARL_PRO_FEC_DOC));
                            vecReg.add(INT_TBL_DAT_DIA_CRE,         "" + objUti.getStringValueAt(arlDatPro, j, INT_ARL_PRO_DIA_CRE));
                            vecReg.add(INT_TBL_DAT_FEC_VNC,         "" + objUti.getStringValueAt(arlDatPro, j, INT_ARL_PRO_FEC_VEN));
                            vecReg.add(INT_TBL_DAT_VAL_DOC,         "" + objUti.getDoubleValueAt(arlDatPro, j, INT_ARL_PRO_MON_PAG));
                            vecReg.add(INT_TBL_DAT_VAL_PND,         "" + objUti.getDoubleValueAt(arlDatPro, j, INT_ARL_PRO_VAL_PND));
                            vecReg.add(INT_TBL_DAT_DIA_VCD,         "" + objUti.getDoubleValueAt(arlDatPro, j, INT_ARL_PRO_DIA_VCD));
                            vecReg.add(INT_TBL_DAT_INT_COB,         "" + objUti.getDoubleValueAt(arlDatPro, j, INT_ARL_PRO_INT));
                            vecReg.add(INT_TBL_DAT_GTO_COB,         "" + objUti.getDoubleValueAt(arlDatPro, j, INT_ARL_PRO_GAS_COB));
                            vecReg.add(INT_TBL_DAT_GTO_COB,         "" + objUti.getDoubleValueAt(arlDatPro, j, INT_ARL_PRO_EST));
                            vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_CHK);
                            vecDat.add(vecReg);
                        }           
                    }
                }
                
                
////////////////////////////////////////////////////////////////                
                strCodEmpNotPro="";
                strCodLocNotPro="";
                strCodTipDocNotPro="";
                strCodDocNotPro="";
                strCodRegNotPro="";
                bandera=0;
                
                for(int j=0; j<arlDatNotPro.size(); j++){
                    strEstArl=objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_EST);
                    strCodEmpNotPro=objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_COD_EMP);
                    strCodLocNotPro=objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_COD_LOC_INT);
                    strCodTipDocNotPro=objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_COD_TIP_DOC_INT);
                    strCodDocNotPro=objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_COD_DOC_INT);
                    strCodRegNotPro=objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_COD_REG_INT);
                    bandera=0;
                    if( ! strEstArl.equals("S")){
                        for(int y=0; y<arlDatDocPrc.size(); y++){
                            strCodEmpTmp=objUti.getStringValueAt(arlDatDocPrc, y, INT_ARL_DOC_PRC_COD_EMP);
                            strCodLocTmp=objUti.getStringValueAt(arlDatDocPrc, y, INT_ARL_DOC_PRC_COD_LOC);
                            strCodTipDocTmp=objUti.getStringValueAt(arlDatDocPrc, y, INT_ARL_DOC_PRC_COD_TIP_DOC);
                            strCodDocTmp=objUti.getStringValueAt(arlDatDocPrc, y, INT_ARL_DOC_PRC_COD_DOC);
                            strCodRegTmp=objUti.getStringValueAt(arlDatDocPrc, y, INT_ARL_DOC_PRC_COD_REG);
                            
                            if( (  (strCodEmpNotPro.equals(strCodEmpTmp))  &&  (strCodLocNotPro.equals(strCodLocTmp))   &&  (strCodTipDocNotPro.equals(strCodTipDocTmp))  &&  (strCodDocNotPro.equals(strCodDocTmp))  && (strCodRegNotPro.equals(strCodRegTmp))  )   ){
                                bandera++;
                            }                            
                        }
                        if(bandera==0){
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN, "");
                            vecReg.add(INT_TBL_DAT_CHK, "");
                            vecReg.add(INT_TBL_DAT_COD_EMP,         "" + objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_COD_EMP));
                            vecReg.add(INT_TBL_DAT_COD_LOC,         "" + objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_COD_LOC_INT));
                            vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     "" + objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_COD_TIP_DOC_INT));
                            vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, "" + objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_DES_COR));
                            vecReg.add(INT_TBL_DAT_COD_DOC,         "" + objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_COD_DOC_INT));
                            vecReg.add(INT_TBL_DAT_COD_REG,         "" + objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_COD_REG_INT));
                            vecReg.add(INT_TBL_DAT_NUM_DOC,         "" + objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_NUM_DOC));
                            vecReg.add(INT_TBL_DAT_FEC_DOC,         "" + objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_FEC_DOC));
                            vecReg.add(INT_TBL_DAT_DIA_CRE,         "" + objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_DIA_CRE));
                            vecReg.add(INT_TBL_DAT_FEC_VNC,         "" + objUti.getStringValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_FEC_VEN));
                            vecReg.add(INT_TBL_DAT_VAL_DOC,         "" + objUti.getDoubleValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_MON_PAG));
                            vecReg.add(INT_TBL_DAT_VAL_PND,         "" + objUti.getDoubleValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_VAL_PND));
                            vecReg.add(INT_TBL_DAT_DIA_VCD,         "" + objUti.getDoubleValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_DIA_VCD));
                            vecReg.add(INT_TBL_DAT_INT_COB,         "" + objUti.getDoubleValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_INT));
                            vecReg.add(INT_TBL_DAT_GTO_COB,         "" + objUti.getDoubleValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_GAS_COB));
                            vecReg.add(INT_TBL_DAT_GTO_COB,         "" + objUti.getDoubleValueAt(arlDatNotPro, j, INT_ARL_NOT_PRO_EST));
                            vecReg.setElementAt(new Boolean(false),INT_TBL_DAT_CHK);
                            vecDat.add(vecReg);
                        }           
                    }
                }
                
                
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblIntSim.setModel(objTblMod);
                vecDat.clear();
           }
            con.close();
            con=null;
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
     * Esta funci�n determina si los campos son v�lidos.
     * @return true: Si los campos son v�lidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        int intConFilChk=0;
        
        
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }

        //Validar el "Cliente".
        if (txtCodCli.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cliente</FONT> es obligatorio.<BR>Escriba o seleccione un cliente y vuelva a intentarlo.</HTML>");
            txtCodCli.requestFocus();
            return false;
        }
        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        //Validar el "Fecha de vencimiento".
        if (!dtpFecVnc.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de vencimiento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha de vencimiento para el documento y vuelva a intentarlo.</HTML>");
            dtpFecVnc.requestFocus();
            return false;
        }
        

        //Validar que el "C�digo alterno" no se repita.
        if (!txtNumDoc.getText().equals("")){
            strSQL="";
            strSQL+="SELECT a1.ne_numDoc";
            strSQL+=" FROM tbm_cabMovInv AS a1 ";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
            strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
            strSQL+=" AND a1.ne_numDoc='" + txtNumDoc.getText().replaceAll("'", "''") + "'";
            strSQL+=" AND a1.st_reg NOT IN ('I', 'E')";
            if (objTooBar.getEstado()=='m')
                strSQL+=" AND a1.co_doc<>" + txtCodDoc.getText();
                        
            if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL)){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El n�mero de documento <FONT COLOR=\"blue\">" + txtNumDoc.getText() + "</FONT> ya existe.<BR>Escriba otro n�mero de documento y vuelva a intentarlo.</HTML>");
                txtNumDoc.selectAll();
                txtNumDoc.requestFocus();
                return false;
            }
        }
        else{
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número alterno</FONT> es obligatorio.<BR>Escriba un número alterno y vuelva a intentarlo.</HTML>");
            return false;
        }

        //Validar que el "Diario est� cuadrado".
        if (!objAsiDia.isDiarioCuadrado()){
            mostrarMsgInf("<HTML>El asiento de diario est� <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
            return false;
        }
        //Validar que el "Monto del diario" sea igual al monto del documento.
        if (!objAsiDia.isDocumentoCuadrado(txtMonDoc.getText())){
            mostrarMsgInf("<HTML>El valor del documento no coincide con el valor del asiento de diario.<BR>Cuadre el valor del documento con el valor del asiento de diario y vuelva a intentarlo.</HTML>");
            txtMonDoc.selectAll();
            txtMonDoc.requestFocus();
            return false;
        }
        
        if(objTooBar.getEstado()=='n'){
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    intConFilChk++;
                }
            }
            
            if(intConFilChk==0){
                mostrarMsgInf("<HTML>El documento que se desea insertar no cuenta con ning�n registro seleccionado.<BR> Verifique y vuelva a intentarlo</HTML>");
                return false;
            }
            
        }

        
        
        return true;
    }
    
    
    /**
     * Esta funci�n actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (actualizarCabMovInv()){
                    if (eliminarPagMovInv()){
                        if (eliminarIntMovInv()){
                                if (insertarPagMovInv()){
                                    if (insertarIntMovInv()){
                                        if (objAsiDia.actualizarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), "A")){
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
                }
                else
                    con.rollback();

            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funci�n permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCabMovInv(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabMovInv";
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=" SET fe_doc='" + strAux + "'";
                strAux=objUti.formatearFecha(dtpFecVnc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=", fe_ven='" + strAux + "'";
                strSQL+=", ne_numDoc=" + objUti.codificar(txtNumDoc.getText(),2);
                strSQL+=", nd_sub=" + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (Double.parseDouble(txtMonDoc.getText())):"0"),3);
                strSQL+=", nd_tot=" + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (Double.parseDouble(txtMonDoc.getText())):"0"),3);
                strSQL+=", co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText());
                strSQL+=", tx_obs2=" + objUti.codificar(txaObs2.getText());
                strSQL+=", st_reg='A'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
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
     * Esta funci�n permite eliminar el detalle de un registro.
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarPagMovInv(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE FROM tbm_pagMovInv";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
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
     * Esta funci�n permite eliminar el detalle de un registro.
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarIntMovInv(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE FROM tbm_intMovInv";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
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
     * Esta funci�n se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro(){
        boolean blnRes=true;
        strAux="�Desea guardar los cambios efectuados a �ste registro?\n";
        strAux+="Si no guarda los cambios perder� toda la informaci�n que no haya guardado.";
        switch (mostrarMsgCon(strAux)){
            case 0: //YES_OPTION
                switch (objTooBar.getEstado()){
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
     * Esta funci�n anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (anularCabMovInv()){
                    if (objAsiDia.anularDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()))){
                        con.commit();
                        blnRes=true;
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
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    /**
     * Esta funci�n permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularCabMovInv(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabMovInv";
                strSQL+=" SET st_reg='I'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
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
     * Esta funci�n elimina el registro de la base de datos.
     * @return true: Si se pudo eliminar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (eliminarCab()){
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
     * Esta funci�n permite eliminar la cabecera de un registro.
     * @return true: Si se pudo eliminar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarCab(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabMovInv";
                strSQL+=" SET st_reg='E'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
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
    
    private boolean existePagosAsociados(){
        boolean blnRes=false;
        Connection ConPagAso;
        Statement stmPagAso;
        ResultSet rstPagAso;
        try{
            ConPagAso=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(ConPagAso!=null){
                stmPagAso=ConPagAso.createStatement();
                strSQL="";
                strSQL+="SELECT b2.tx_desCor, b1.ne_numDoc1";
                strSQL+=" FROM tbm_detPag AS a1";
                strSQL+=" INNER JOIN (tbm_cabPag AS b1 INNER JOIN tbm_cabTipDoc AS b2";
                strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc)";
                strSQL+=" ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_doc=b1.co_doc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_locPag=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                rstPagAso=stmPagAso.executeQuery(strSQL);
                if(rstPagAso.next()){
                    blnRes=true;
                }
            ConPagAso.close();
            ConPagAso=null;
            stmPagAso.close();
            stmPagAso=null;
            rstPagAso.close();
            rstPagAso=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    /**
     * Esta clase crea un hilo que permite manipular la interface gr�fica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que est� ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podr�a presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estar�a informado en todo
     * momento de lo que ocurre. Si se desea hacer �sto es necesario utilizar �sta clase
     * ya que si no s�lo se apreciar�a los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread{
        private int intIndFun;
        
        public ZafThreadGUI(){
            intIndFun=0;
        }
        
        public void run(){
            switch (intIndFun){
                case 0: //Bot�n "Imprimir".
                    objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Bot�n "Vista Preliminar".
                    objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUI=null;
        }
        
        /**
         * Esta funci�n establece el indice de la funci�n a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta funci�n sirve para determinar
         * la funci�n que debe ejecutar el Thread.
         * @param indice El indice de la funci�n a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }
    
    
    /**
     * Esta funci�n permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresi�n directa.
     * <LI>1: Impresi�n directa (Cuadro de dialogo de impresi�n).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt){
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes=true;
        double dblValInt=0.00;
        double dblValGtoCob=0.00;
        double dblValPen=0.00;
        
        double dblValPag=0.00;
        try{
            
            
            
            
            objRptSis.cargarListadoReportes(conCab);
            objRptSis.show();
            if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE){
                //Obtener la fecha y hora del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                datFecAux=null;
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++){
                    if (objRptSis.isReporteSeleccionado(i)){
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i))){
                            case 263:
                            case 264:
                            default:
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.
                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("co_emp", Short.valueOf(rstCab.getString("co_emp")));
                                mapPar.put("co_loc", Short.valueOf(rstCab.getString("co_loc")));
                                mapPar.put("co_tipDoc", Short.valueOf(rstCab.getString("co_tipDoc")));
                                mapPar.put("co_doc", Integer.valueOf(rstCab.getString("co_doc")));
                                mapPar.put("nomCli", String.valueOf(txtDesLarCli.getText()));
                                mapPar.put("usuIng", String.valueOf(nombreUsuario()));
                                
//                                dblValInt=objUti.redondear(Double.parseDouble(objTblTot.getValueAt(0, INT_TBL_DAT_INT_COB).toString()), objParSis.getDecimalesMostrar());;
//                                dblValGtoCob=objUti.redondear(Double.parseDouble(objTblTot.getValueAt(0, INT_TBL_DAT_GTO_COB).toString()), objParSis.getDecimalesMostrar());;
                                dblValPen=objUti.redondear(Double.parseDouble(objTblTot.getValueAt(0, INT_TBL_DAT_VAL_PND).toString()), objParSis.getDecimalesMostrar());;
//                                
//                                dblValPag=dblValInt+dblValGtoCob+dblValPen;
                                dblValPag=Double.parseDouble((txtMonDoc.getText()))+dblValPen;
                                mapPar.put("valPag", Double.valueOf(""+dblValPag));
//                                mapPar.put("valPag", Double.valueOf(""+txtMonDoc.getText()));
                                
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
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
    
    
    private String nombreUsuario(){
        String strNomUsr="";
        Connection conNomUsr;
        Statement stmNomUsr;
        ResultSet rstNomUsr;
        try{
            conNomUsr=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conNomUsr!=null){
                stmNomUsr=conNomUsr.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
                strSQL+=" FROM tbm_usr AS a1 INNER JOIN tbm_cabMovInv AS a2";
                strSQL+=" ON a1.co_usr=a2.co_usrIng";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND a2.co_doc=" + txtCodDoc.getText() + "";
                
                rstNomUsr=stmNomUsr.executeQuery(strSQL);
                if(rstNomUsr.next()){
                    strNomUsr=rstNomUsr.getString("tx_nom");
                }
                
                conNomUsr.close();
                conNomUsr=null;
                stmNomUsr.close();
                stmNomUsr=null;
                rstNomUsr.close();
                rstNomUsr=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }

        return strNomUsr;
    }
    
    
    private boolean isDocumentoProcesado(){
        boolean blnRes=true;
        Connection conTmpDocPrc;
        Statement stmTmpDocPrc;
        ResultSet rstTmpDocPrc;
        arlDatDocPrc.clear();
        try{
            conTmpDocPrc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTmpDocPrc!=null){
                stmTmpDocPrc=conTmpDocPrc.createStatement();
                strSQL="";
                strSQL+="SELECT a3.co_emp, a3.co_locInt, a3.co_tipDocInt, a3.co_docInt, a3.co_regInt";
                strSQL+=" FROM (tbm_intMovInv AS a3 INNER JOIN tbm_cabMovInv AS a2";
                strSQL+=" ON a3.co_emp=a2.co_emp AND a3.co_loc=a2.co_loc AND a3.co_tipDoc=a2.co_tipDoc AND a3.co_doc=a2.co_doc";
                strSQL+=" ) INNER JOIN tbm_cabMovInv AS d1";
                strSQL+=" ON a3.co_emp=d1.co_emp AND a3.co_locInt=d1.co_loc AND a3.co_tipDocInt=d1.co_tipDoc AND a3.co_docInt=d1.co_doc";
                strSQL+=" WHERE a3.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a3.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND a3.co_doc NOT IN(" + txtCodDoc.getText() + ")";
                strSQL+=" AND a2.st_reg NOT IN('I','E')";
                strSQL+=" GROUP BY a3.co_emp, a3.co_locInt, a3.co_tipDocInt, a3.co_docInt, a3.co_regInt";
                System.out.println("PROCESADOS AL MODIFICAR: " + strSQL);
                rstTmpDocPrc=stmTmpDocPrc.executeQuery(strSQL);
                while(rstTmpDocPrc.next()){
                    arlRegDocPrc=new ArrayList();
                    arlRegDocPrc.add(INT_ARL_DOC_PRC_COD_EMP,       "" + rstTmpDocPrc.getString("co_emp"));
                    arlRegDocPrc.add(INT_ARL_DOC_PRC_COD_LOC,       "" + rstTmpDocPrc.getString("co_locInt"));
                    arlRegDocPrc.add(INT_ARL_DOC_PRC_COD_TIP_DOC,   "" + rstTmpDocPrc.getString("co_tipDocInt"));
                    arlRegDocPrc.add(INT_ARL_DOC_PRC_COD_DOC,       "" + rstTmpDocPrc.getString("co_docInt"));
                    arlRegDocPrc.add(INT_ARL_DOC_PRC_COD_REG,       "" + rstTmpDocPrc.getString("co_regInt"));
                    arlDatDocPrc.add(arlRegDocPrc);
                }
                conTmpDocPrc.close();
                conTmpDocPrc=null;
                stmTmpDocPrc.close();
                stmTmpDocPrc=null;
                rstTmpDocPrc.close();
                rstTmpDocPrc=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
}
