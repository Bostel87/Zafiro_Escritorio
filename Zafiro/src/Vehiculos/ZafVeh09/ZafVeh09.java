/*
 * ZafVeh09.java
 *
 * Created on 10 Septiembre , 2013, 8:44 PM
 */


package Vehiculos.ZafVeh09;
import Vehiculos.ZafVeh07.ZafVeh07;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
/**
 *
 * @author  José Marín 
 */
public class ZafVeh09 extends javax.swing.JInternalFrame {
    
    
    //Constantes: Columnas del JTable:
    static final int INT_TBL_COM_LIN=0;                         //Línea.
    static final int INT_TBL_COM_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_COM_COD_COM=2;                     //Código de la empresa.
    static final int INT_TBL_COM_NOM_COM=3;                     //Nombre de la empresa.
    private ZafTblMod objTblModLoc;
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.

    private ZafTblTot objTblTot;                                //JTable de totales.
    private ZafThreadGUI objThrGUI;
    private ZafThreadGUIRpt objThrGUIRpt;
    private ZafRptSis objRptSis;
    
    private ZafParSis objParSis;
    private ZafVenCon vcoPrg;
    private ZafTblCelEdiTxt objTblCelEdiTxt;//Editor: JTextField en celda.
    private ZafUtil objUti;//Objeto del tipo de la clase ZafUtil, el cual me va a permitir 
    private ZafTblMod objTblMod;
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelRenBut objTblCelRenBut;//Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButDG1;
    private ZafTblOrd objTblOrd;
    private ZafTblBus objTblBus;
    private ZafVenCon vcoUsr;
    private ZafVenCon vecLoc; 
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg;//Editor: JButton en celda (Bodega origen).
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg2;//Editor: JButton en celda (Bodega origen).
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg3;
    private ZafSelFec objSelFec;
    private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGenDG1;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenCbo objTblCelRenCmbBox;
    private ZafTblCelEdiCbo objTblCelEdiCmbBox;
    private ZafTblCelRenCbo objTblCelRenCmbBox2;
    private ZafTblCelEdiCbo objTblCelEdiCmbBox2;
    private boolean blnHayCam;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private boolean blnCon;//true: Continua la ejecución del hilo. // Continuidad del hilo
    
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenLbl objTblCelRenLblCod;                 //Render: Presentar JLabel en JTable (Números).
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
    private String strMarCod;
    private String strMarNom;
    private ZafVenCon vcoLoc;                           //Ventana de consulta <jota>
    private String strComCod;
    private String strComNom;
    
    private ZafVenCon vcoMar;//j
    private ZafVenCon vcoCom;//j
    private ZafVenCon vcoDoc;//j
    private ZafVenCon vcoVeh;//j    
        
        
    private String strChoCod;
    private String strChoNom;
    private ZafVenCon vcoCho;//j
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;           //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm2;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm3;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm4;
    private String strCodLoc, strNomLoc;
    private String strSQL, strAux, strConSQL;
    private Vector vecDat, vecReg, vecCab, vecAux;
    private Vector vecVeh, vecCom;
    private String strTipPrg;
    private String strNomPrg;
    private Connection con;
    private Connection conCab;
    private Statement stm;
    private ResultSet rst;
    private int intSelPrd;
    private int intSelRepSelPrd;
    
    private String strVehiculo;
    private String strDocCod;
    private String strDocNom;
    private String strVehCod;
    private String strVehNom;
    
    private String strVersion=" v 0.3";
    //José Marín M. 19/Ene/2015
    //Tabla 
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_COD_LOC=1;
    private final int INT_TBL_DAT_COD_TIP_DOC=2;
    private final int INT_TBL_DAT_DES_COR_TIP_DOC=3;
    private final int INT_TBL_DAT_DES_LAR_TIP_DOC=4;
    private final int INT_TBL_DAT_COD_DOC=5;
    private final int INT_TBL_DAT_NUM_DOC=6;
    private final int INT_TBL_DAT_FEC_DOC=7;
    private final int INT_TBL_DAT_DES_LAR_VEH=8;
    private final int INT_TBL_DAT_VAL_CAL=9;
    private final int INT_TBL_DAT_VAL_MAN=10;
    private final int INT_TBL_DAT_VAL_EFE=11;
    private final int INT_TBL_DAT_VAL_DOC=12;
    private final int INT_TBL_DAT_VAL_DOC_GAS=13;
    private final int INT_TBL_DAT_CON_TIK=14;
    private final int INT_TBL_DAT_CHK_CON=15;
    private final int INT_TBL_DAT_TRUE_FALSE=16;
    
    private String strCodMar, strNomMar;                //Contenido del campo al obtener el foco.
    private String strCodCom, strNomCom;                //Contenido del campo al obtener el foco.
    private String strDesCorTipDoc, strDesLarTipDoc;
    public String strWhere, strFecIni, strFecFin, strFecDoc,tot_doc="",tot_docGas="";
    private Vector vecCodLoc, vecCodTipDoc, vecDesCorTipDoc, vecDesLarTipDoc;
    private Vector vecCodDoc, vecNomDoc, vecFecDoc, vecNumDoc;
    private Vector vecNomVeh, vecValCal, vecValMan, vecValEfe; 
    private Vector vecValDoc, vecStAut, vecValDocGas;
    private Vector vecChkCon, vecTru;
    private Vehiculos.ZafVeh07.ZafVeh07 objReg;
    private ZafPerUsr objPerUsr;                               //Objeto que almacena el perfil del usuario.
    private ZafDocLis objDocLis;
    
    /** Creates new form ZafVeh09 */
    public ZafVeh09(ZafParSis obj) {
        try{
            initComponents();
            
            objUti = new ZafUtil();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objRptSis = new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButGenDG1=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            objTblCelRenButDG1=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            if (!configurarFrm())
                exitForm();
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
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        bgrDoc = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panDat = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        txtNumCta = new javax.swing.JTextField();
        txtNumPro = new javax.swing.JTextField();
        butMar = new javax.swing.JButton();
        txtNomMar = new javax.swing.JTextField();
        txtCodMar = new javax.swing.JTextField();
        lblMarca = new javax.swing.JLabel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblTipVeh = new javax.swing.JLabel();
        lblVen1 = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        txtDesCorDoc = new javax.swing.JTextField();
        txtNomDoc = new javax.swing.JTextField();
        butDoc = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtCodVeh = new javax.swing.JTextField();
        txtNomVeh = new javax.swing.JTextField();
        butVeh = new javax.swing.JButton();
        chkMon = new javax.swing.JCheckBox();
        chkCar = new javax.swing.JCheckBox();
        chkMot = new javax.swing.JCheckBox();
        panLoc = new javax.swing.JPanel();
        spnLoc = new javax.swing.JScrollPane();
        tblLoc = new javax.swing.JTable();
        optFecDoc = new javax.swing.JRadioButton();
        optFecCon = new javax.swing.JRadioButton();
        panRpt = new javax.swing.JPanel();
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butvpre = new javax.swing.JButton();
        butimp = new javax.swing.JButton();
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
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Información del registro actual");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(459, 473));

        panDat.setPreferredSize(new java.awt.Dimension(600, 80));
        panDat.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(610, 300));
        panCab.setLayout(null);

        txtNumCta.setMaximumSize(null);
        txtNumCta.setPreferredSize(new java.awt.Dimension(70, 20));
        panCab.add(txtNumCta);
        txtNumCta.setBounds(180, 25, 0, 0);

        txtNumPro.setMaximumSize(null);
        txtNumPro.setPreferredSize(new java.awt.Dimension(70, 20));
        panCab.add(txtNumPro);
        txtNumPro.setBounds(180, 46, 0, 0);

        butMar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butMar.setText("...");
        butMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butMarActionPerformed(evt);
            }
        });
        panCab.add(butMar);
        butMar.setBounds(660, 160, 20, 20);

        txtNomMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomMarActionPerformed(evt);
            }
        });
        txtNomMar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomMarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomMarFocusLost(evt);
            }
        });
        panCab.add(txtNomMar);
        txtNomMar.setBounds(290, 160, 370, 19);

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
        panCab.add(txtCodMar);
        txtCodMar.setBounds(200, 160, 90, 19);

        lblMarca.setText("Marca:");
        panCab.add(lblMarca);
        lblMarca.setBounds(60, 160, 39, 15);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        optTod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                optTodFocusGained(evt);
            }
        });
        panCab.add(optTod);
        optTod.setBounds(10, 80, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        panCab.add(optFil);
        optFil.setBounds(10, 100, 400, 20);

        lblTipVeh.setText("Tipo de vehículo:"); // NOI18N
        panCab.add(lblTipVeh);
        lblTipVeh.setBounds(60, 200, 130, 15);

        lblVen1.setText("Local");
        lblVen1.setToolTipText("Vendedor/Comprador");
        panCab.add(lblVen1);
        lblVen1.setBounds(60, 120, 40, 20);

        txtCodLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLocActionPerformed(evt);
            }
        });
        txtCodLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLocFocusLost(evt);
            }
        });
        panCab.add(txtCodLoc);
        txtCodLoc.setBounds(200, 120, 90, 20);

        txtNomLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomLocActionPerformed(evt);
            }
        });
        txtNomLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomLocFocusLost(evt);
            }
        });
        panCab.add(txtNomLoc);
        txtNomLoc.setBounds(290, 120, 370, 20);

        butLoc.setText("...");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panCab.add(butLoc);
        butLoc.setBounds(660, 120, 20, 20);

        jLabel11.setText("Tipo de documento:");
        panCab.add(jLabel11);
        jLabel11.setBounds(60, 140, 130, 15);

        txtCodDoc.setEnabled(false);
        txtCodDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDocActionPerformed(evt);
            }
        });
        txtCodDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDocFocusLost(evt);
            }
        });
        panCab.add(txtCodDoc);
        txtCodDoc.setBounds(180, 140, 20, 19);

        txtDesCorDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorDocActionPerformed(evt);
            }
        });
        txtDesCorDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorDocFocusLost(evt);
            }
        });
        panCab.add(txtDesCorDoc);
        txtDesCorDoc.setBounds(200, 140, 90, 19);

        txtNomDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDocActionPerformed(evt);
            }
        });
        txtNomDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDocFocusLost(evt);
            }
        });
        panCab.add(txtNomDoc);
        txtNomDoc.setBounds(290, 140, 370, 19);

        butDoc.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butDoc.setText("...");
        butDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDocActionPerformed(evt);
            }
        });
        panCab.add(butDoc);
        butDoc.setBounds(660, 140, 20, 20);

        jLabel13.setText("Vehículo:");
        panCab.add(jLabel13);
        jLabel13.setBounds(60, 180, 130, 15);

        txtCodVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVehActionPerformed(evt);
            }
        });
        txtCodVeh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVehFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVehFocusLost(evt);
            }
        });
        panCab.add(txtCodVeh);
        txtCodVeh.setBounds(200, 180, 90, 19);

        txtNomVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVehActionPerformed(evt);
            }
        });
        txtNomVeh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVehFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVehFocusLost(evt);
            }
        });
        panCab.add(txtNomVeh);
        txtNomVeh.setBounds(290, 180, 370, 19);

        butVeh.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butVeh.setText("...");
        butVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVehActionPerformed(evt);
            }
        });
        panCab.add(butVeh);
        butVeh.setBounds(660, 180, 20, 20);

        chkMon.setSelected(true);
        chkMon.setText("Montacargas");
        chkMon.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkMonItemStateChanged(evt);
            }
        });
        panCab.add(chkMon);
        chkMon.setBounds(460, 200, 110, 23);

        chkCar.setSelected(true);
        chkCar.setText("Carro");
        chkCar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkCarItemStateChanged(evt);
            }
        });
        panCab.add(chkCar);
        chkCar.setBounds(200, 200, 90, 23);

        chkMot.setSelected(true);
        chkMot.setText("Moto");
        chkMot.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkMotItemStateChanged(evt);
            }
        });
        panCab.add(chkMot);
        chkMot.setBounds(330, 200, 60, 23);

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de combustibles"));
        panLoc.setAutoscrolls(true);
        panLoc.setLayout(new java.awt.BorderLayout());

        tblLoc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnLoc.setViewportView(tblLoc);

        panLoc.add(spnLoc, java.awt.BorderLayout.CENTER);

        panCab.add(panLoc);
        panLoc.setBounds(60, 220, 360, 92);
        panLoc.getAccessibleContext().setAccessibleName("");
        panLoc.getAccessibleContext().setAccessibleDescription("");

        bgrDoc.add(optFecDoc);
        optFecDoc.setSelected(true);
        optFecDoc.setText("Fecha del Documento");
        panCab.add(optFecDoc);
        optFecDoc.setBounds(10, 0, 159, 23);

        bgrDoc.add(optFecCon);
        optFecCon.setText("Fecha de Conciliación");
        panCab.add(optFecCon);
        optFecCon.setBounds(170, 0, 170, 23);

        panDat.add(panCab, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panDat);

        panRpt.setLayout(new java.awt.BorderLayout());

        panDet.setLayout(new java.awt.BorderLayout());

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
        spnDet.setViewportView(tblDat);

        panDet.add(spnDet, java.awt.BorderLayout.CENTER);

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

        panDet.add(spnTot, java.awt.BorderLayout.SOUTH);

        panRpt.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

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

        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butvpre.setText("Vis.Previa");
        butvpre.setPreferredSize(new java.awt.Dimension(90, 23));
        butvpre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butvpreActionPerformed(evt);
            }
        });
        panBot.add(butvpre);

        butimp.setText("Imprimir");
        butimp.setPreferredSize(new java.awt.Dimension(90, 23));
        butimp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butimpActionPerformed(evt);
            }
        });
        panBot.add(butimp);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

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

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
// TODO add your handling code here:
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                if(con!=null){
                    con.close();
                    
                    con=null;
                    
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e)
        {
            dispose();
        }        
}//GEN-LAST:event_exitForm

    private void butMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMarActionPerformed
        // TODO add your handling code here:
        strMarCod=txtCodMar.getText();
        mostrarMarcas(0);
        optFil.setSelected(true);
    }//GEN-LAST:event_butMarActionPerformed

    private void txtNomMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomMarActionPerformed
        // TODO add your handling code here:
        txtNomMar.transferFocus();
    }//GEN-LAST:event_txtNomMarActionPerformed

    private void txtNomMarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomMarFocusGained
        // TODO add your handling code here:
        strMarNom=txtNomMar.getText();
        txtNomMar.selectAll();
        optFil.setSelected(true);
    }//GEN-LAST:event_txtNomMarFocusGained

    private void txtNomMarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomMarFocusLost
        // TODO add your handling code here:
        if (!txtNomMar.getText().equalsIgnoreCase(strMarNom)){
            if (txtNomMar.getText().equals("")){
                txtCodMar.setText("");
                txtNomMar.setText("");
            }
            else{
                mostrarMarcas(2);
            }
        }
        else
            txtNomMar.setText(strMarNom);
    }//GEN-LAST:event_txtNomMarFocusLost

    private void txtCodMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodMarActionPerformed
        // TODO add your handling code here:
        txtCodMar.transferFocus();
    }//GEN-LAST:event_txtCodMarActionPerformed

    private void txtCodMarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMarFocusGained
        // TODO add your handling code here:
        strMarCod=txtCodMar.getText();
        txtCodMar.selectAll();
        optFil.setSelected(true);
    }//GEN-LAST:event_txtCodMarFocusGained

    private void txtCodMarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMarFocusLost
        // TODO add your handling code here:
        if (!txtCodMar.getText().equalsIgnoreCase(strMarCod)){
            if (txtCodMar.getText().equals("")){
                txtCodMar.setText("");
                txtNomMar.setText("");
            }
            else
            mostrarMarcas(1);
        }
        else
            txtCodMar.setText(strMarCod);
    }//GEN-LAST:event_txtCodMarFocusLost

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        //objTblMod.removeAllRows();
         if (isCamVal())
        {
            //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
            if (butCon.getText().equals("Consultar"))
            {
                blnCon=true;
                if (objThrGUI==null)
                {
                    objThrGUI=new ZafVeh09.ZafThreadGUI();
                    objThrGUI.start();
                }            
            }
            else
            {
                blnCon=false;
            }
        }
        
    }//GEN-LAST:event_butConActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
      if (objTblMod.isDataModelChanged())
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                if (actualizarDet())
                        mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                else
                    mostrarMsgInf("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
            }
        }
        else
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");

    }//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void optTodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_optTodFocusGained
        // TODO add your handling code here:
        if (optTod.isSelected())
        {
            txtCodMar.setText("");
            txtNomMar.setText("");  
            txtCodLoc.setText("");
            txtNomLoc.setText("");
            txtCodDoc.setText("");
            txtNomDoc.setText("");
            txtDesCorDoc.setText("");
            txtCodVeh.setText("");
            txtNomVeh.setText("");
            chkCar.setSelected(true);
            chkMot.setSelected(true);
            chkMon.setSelected(true);
            
        }
    }//GEN-LAST:event_optTodFocusGained

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodMar.setText("");
            txtNomMar.setText("");  
            txtCodLoc.setText("");
            txtNomLoc.setText("");
            txtCodDoc.setText("");
            txtNomDoc.setText("");
            txtDesCorDoc.setText("");
            txtCodVeh.setText("");
            txtNomVeh.setText("");
            chkCar.setSelected(true);
            chkMot.setSelected(true);
            chkMon.setSelected(true);          
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        txtCodLoc.transferFocus();
    }//GEN-LAST:event_txtCodLocActionPerformed

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        strCodLoc=txtCodLoc.getText();
        txtCodLoc.selectAll();
        optFil.setSelected(true);
    }//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc))
        {
            if (txtCodLoc.getText().equals("")){
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            }
            else{
                mostrarLocal(1);
            }
        }
        else
        txtCodLoc.setText(strCodLoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0){
            optFil.setSelected(true);

    }//GEN-LAST:event_txtCodLocFocusLost
}
    
    private void txtNomLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLocActionPerformed
        txtNomLoc.transferFocus();
    }//GEN-LAST:event_txtNomLocActionPerformed

    private void txtNomLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusGained
        strNomLoc=txtNomLoc.getText();
        txtNomLoc.selectAll();
         optFil.setSelected(true);
    }//GEN-LAST:event_txtNomLocFocusGained

    private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
        if (!txtNomLoc.getText().equalsIgnoreCase(strNomLoc))
        {
            if (txtNomLoc.getText().equals("")){
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            }
            else{
                mostrarLocal(2);
            }
        }
        else
        txtNomLoc.setText(strNomLoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomLocFocusLost

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        //        if(txtCodEmp.getText().length()>0)
        //            configurarLocal();
        mostrarLocal(0);
         optFil.setSelected(true);
    }//GEN-LAST:event_butLocActionPerformed

    private void txtCodDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDocActionPerformed
        // TODO add your handling code here:
        txtCodDoc.transferFocus();
    }//GEN-LAST:event_txtCodDocActionPerformed

    private void txtCodDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDocFocusGained
        // TODO add your handling code here:
        strDocCod=txtCodDoc.getText();
        txtCodDoc.selectAll();
    }//GEN-LAST:event_txtCodDocFocusGained

    private void txtCodDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDocFocusLost
        // TODO add your handling code here:
        if (!txtCodDoc.getText().equalsIgnoreCase(strDocCod)){
            if (txtCodDoc.getText().equals("")){
                txtCodDoc.setText("");
                txtNomDoc.setText("");
            }
            else
            mostrarDocumentos(1);
        }
        else
        txtCodDoc.setText(strDocCod);
    }//GEN-LAST:event_txtCodDocFocusLost

    private void txtNomDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDocActionPerformed
        // TODO add your handling code here:
        txtNomDoc.transferFocus();
    }//GEN-LAST:event_txtNomDocActionPerformed

    private void txtNomDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDocFocusGained
        // TODO add your handling code here:
        strDocNom=txtNomDoc.getText();
        txtNomDoc.selectAll();
        
         optFil.setSelected(true);
    }//GEN-LAST:event_txtNomDocFocusGained

    private void txtNomDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDocFocusLost
        // TODO add your handling code here:
        if (!txtNomDoc.getText().equalsIgnoreCase(strDocNom))
        {
            if (txtNomDoc.getText().equals(""))
            {
                txtCodDoc.setText("");
                txtDesCorDoc.setText("");
                txtNomDoc.setText("");
            }
            else
            {
                mostrarDocumentos(2);
            }
        }
        else
        txtNomDoc.setText(strDocNom);
    }//GEN-LAST:event_txtNomDocFocusLost

    private void butDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDocActionPerformed
        // TODO add your handling code here:
        strDocCod=txtCodDoc.getText();
        mostrarDocumentos(0);
         optFil.setSelected(true);
    }//GEN-LAST:event_butDocActionPerformed

    private void txtCodVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVehActionPerformed
         txtCodVeh.transferFocus();      
    }//GEN-LAST:event_txtCodVehActionPerformed

    private void txtCodVehFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVehFocusGained
        strVehCod=txtCodVeh.getText();
        txtCodVeh.selectAll();
    }//GEN-LAST:event_txtCodVehFocusGained

    private void txtCodVehFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVehFocusLost
         if (!txtCodVeh.getText().equalsIgnoreCase(strVehCod)){
            if (txtCodVeh.getText().equals("")){
                txtCodVeh.setText("");
                txtNomVeh.setText("");
            }
            else
            mostrarVehiculos(1);
        }
        else
        txtCodVeh.setText(strVehCod);
    }//GEN-LAST:event_txtCodVehFocusLost

    private void txtNomVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVehActionPerformed
        txtNomVeh.transferFocus();
    }//GEN-LAST:event_txtNomVehActionPerformed

    private void txtNomVehFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVehFocusGained
       strVehNom=txtNomVeh.getText();
        txtNomVeh.selectAll();
    }//GEN-LAST:event_txtNomVehFocusGained

    private void txtNomVehFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVehFocusLost
        if (!txtNomVeh.getText().equalsIgnoreCase(strVehNom))
        {
            if (txtNomVeh.getText().equals(""))
            {
                txtCodVeh.setText("");
                txtNomVeh.setText("");
                }
            else
            {
                mostrarVehiculos(2);
            }
        }
        else
            txtNomVeh.setText(strVehNom);
    }//GEN-LAST:event_txtNomVehFocusLost

    private void butVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVehActionPerformed
        // TODO add your handling code here:
       // configurarVehiculos();  // ESTO NECESITA ACTUALIZARCE PARA EL KILOMETRAJE ZafVen07
        strVehCod=txtCodVeh.getText();
        mostrarVehiculos(0);
         optFil.setSelected(true);
    }//GEN-LAST:event_butVehActionPerformed

    private void chkCarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkCarItemStateChanged
         if (!chkCar.isSelected() && !optFil.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkCarItemStateChanged

    private void chkMotItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkMotItemStateChanged
          if (!chkMot.isSelected() && !optFil.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkMotItemStateChanged

    private void chkMonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkMonItemStateChanged
         if (!chkMon.isSelected() && !optFil.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkMonItemStateChanged

    private void txtDesCorDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorDocActionPerformed
        txtDesCorDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorDocActionPerformed

    private void txtDesCorDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorDocFocusGained
       strDesCorTipDoc=txtDesCorDoc.getText();
        txtDesCorDoc.selectAll();
        optFil.setSelected(true);
    }//GEN-LAST:event_txtDesCorDocFocusGained

    private void txtDesCorDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorDocFocusLost
        if (!txtDesCorDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtDesCorDoc.getText().equals(""))
            {
                txtCodDoc.setText("");
                txtDesCorDoc.setText("");
                txtNomDoc.setText("");
            }
            else
            {
                mostrarDocumentos(1);
            }
        }
        else
            txtDesCorDoc.setText(strDesCorTipDoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtDesCorDoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesCorDocFocusLost

    private void butimpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butimpActionPerformed
        // TODO add your handling code here:
         cargarDetReg();
         cargarRepote(0);
    }//GEN-LAST:event_butimpActionPerformed

    private void butvpreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butvpreActionPerformed
        // TODO add your handling code here:
        cargarDetReg();
        cargarRepote(1);
    }//GEN-LAST:event_butvpreActionPerformed

    private void cargarRepote(int intTipo){
   if (objThrGUIRpt==null)
    {
        objThrGUIRpt=new ZafThreadGUIRpt();
        objThrGUIRpt.setIndFunEje(intTipo);
        objThrGUIRpt.start();
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrDoc;
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butDoc;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butMar;
    private javax.swing.JButton butVeh;
    private javax.swing.JButton butimp;
    private javax.swing.JButton butvpre;
    private javax.swing.JCheckBox chkCar;
    private javax.swing.JCheckBox chkMon;
    private javax.swing.JCheckBox chkMot;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipVeh;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen1;
    private javax.swing.JRadioButton optFecCon;
    private javax.swing.JRadioButton optFecDoc;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblLoc;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodMar;
    private javax.swing.JTextField txtCodVeh;
    private javax.swing.JTextField txtDesCorDoc;
    private javax.swing.JTextField txtNomDoc;
    private javax.swing.JTextField txtNomLoc;
    private javax.swing.JTextField txtNomMar;
    private javax.swing.JTextField txtNomVeh;
    private javax.swing.JTextField txtNumCta;
    private javax.swing.JTextField txtNumPro;
    // End of variables declaration//GEN-END:variables

//    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }   
    
       
    
//    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Inicializar objetos.
//            System.out.println("configurarFrm....");
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            panCab.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            objUti=new ZafUtil();
            objPerUsr=new ZafPerUsr(objParSis);
            intSelPrd=0;
             configurarTblLoc();
            cargarCom();
            txtCodDoc.setVisible(false);
            this.setTitle(objParSis.getNombreMenu() + strVersion);
            lblTit.setText(objParSis.getNombreMenu());
            switch (objParSis.getCodigoMenu())
            {
                case 3744: //"Novedades de tickets de combustible..."...
                            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
                            if (!objPerUsr.isOpcionEnabled(3746))
                            {
                                butGua.setVisible(false);
                            }
                            butvpre.setVisible(false);
                            butimp.setVisible(false);
                    break;
                case 3748: //Listado de tickets de combustible..
                            butGua.setVisible(false);
                    break;
                    
            }
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_VEH,"Vehículo");
            vecCab.add(INT_TBL_DAT_VAL_CAL,"Val.Cal.");
            vecCab.add(INT_TBL_DAT_VAL_MAN,"Val.Man.");
            vecCab.add(INT_TBL_DAT_VAL_EFE,"Val.Efe.");
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Val.Doc.");
            vecCab.add(INT_TBL_DAT_VAL_DOC_GAS,"Val.Doc.Gas.");
            vecCab.add(INT_TBL_DAT_CON_TIK,"");
            vecCab.add(INT_TBL_DAT_CHK_CON,"Conciliado");
            vecCab.add(INT_TBL_DAT_TRUE_FALSE,"Hide.Pre");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_VEH).setPreferredWidth(200);            
            tcmAux.getColumn(INT_TBL_DAT_VAL_CAL).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_VAL_MAN).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_VAL_EFE).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC_GAS).setPreferredWidth(55); 
            tcmAux.getColumn(INT_TBL_DAT_CON_TIK).setPreferredWidth(20);
             tcmAux.getColumn(INT_TBL_DAT_CHK_CON).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_TRUE_FALSE).setPreferredWidth(30);
            
//            if(objParSis.getCodigoMenu()==3748) // si es listadoDeTickets ocultamos 
//            {objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_CON, tblDat);}
            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TRUE_FALSE, tblDat);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CON_TIK).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CON).setResizable(false);
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            tcmAux.getColumn(INT_TBL_DAT_VAL_CAL).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_VAL_MAN).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_VAL_EFE).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC_GAS).setCellRenderer(objTblCelRenLblNum);
            
            
            objTblCelRenLblCod=new ZafTblCelRenLbl();
            objTblCelRenLblCod.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLblCod);
            
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_DOC_GAS, ZafTblMod.INT_COL_DBL, new Integer(0), null);
           
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            if(objParSis.getCodigoMenu()==3744)
            {   vecAux.add("" + INT_TBL_DAT_VAL_DOC_GAS);}
            vecAux.add("" + INT_TBL_DAT_CON_TIK);
            
            if(objParSis.getCodigoMenu()==3744)
            vecAux.add(""+ INT_TBL_DAT_CHK_CON);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //Agregamos ChkBox
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_CON).setCellRenderer(objTblCelRenChk);
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CON).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_TRUE_FALSE).toString().equals("1")){
                        objTblMod.setValueAt(true, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_CON);
                    }
                }
            });
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            objTblFilCab=null;
            
            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_CON_TIK).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            new ButConOcp(tblDat, INT_TBL_DAT_CON_TIK);
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC_GAS).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            Double precio,befAft;
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
            {
                precio=objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_DOC_GAS));
                befAft=objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_DOC));
            }

            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                if(objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_DOC_GAS))> befAft){
                    objTblMod.setValueAt(befAft, tblDat.getSelectedRow(), INT_TBL_DAT_VAL_DOC_GAS);         
                }
                if(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_TRUE_FALSE).toString().equals("1")){
                    objTblMod.setValueAt(precio, tblDat.getSelectedRow(), INT_TBL_DAT_VAL_DOC_GAS);
                    mostrarMsgInf("Valor ya ha sido conciliado");                 
                }
                if(befAft!=objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_DOC_GAS)))
                {
//                    System.out.println("PRECIO: " + precio);
//                    System.out.println("VALOR: " + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_DOC_GAS));
                    objTblMod.setChecked(true, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_CON);
                }
                
            }
            });
            configurarDocumentos();
            configurarLocal();
            configurarMarcas();
            configurarVehiculos();
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblOrd=new ZafTblOrd(tblDat);
            objTblBus=new ZafTblBus(tblDat);
            objDocLis=new ZafDocLis();
//            System.out.println("Se configuara la tabla!!!!... ");
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);           
            int intCol[]={INT_TBL_DAT_VAL_DOC, INT_TBL_DAT_VAL_DOC_GAS};
            objTblTot=new ZafTblTot(spnDet, spnTot, tblDat, tblTot, intCol);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
    
///**
// * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
// * @return true: Si se pudo consultar los registros.
// * <BR>false: En el caso contrario.
// */
    
    private boolean cargarDetReg(){
        int band=0,j=0,intNumFilTblBod,i;
           intNumFilTblBod=objTblModLoc.getRowCountTrue();
        objUti=new ZafUtil();
        boolean blnRes=true;
        strAux="";
        String strAux2="",strConSQL2="";
        try{
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
                
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    
                strConSQL="";
                strFecIni=objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), "dd/MMM/yyyy");
                strFecFin=objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), "dd/MMM/yyyy");
                if(optFecDoc.isSelected()){
                    switch (objSelFec.getTipoSeleccion())
                    {
                        case 0: //Búsqueda por rangos
                            strConSQL+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strConSQL+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strConSQL+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
                else if(optFecCon.isSelected()){
                    switch (objSelFec.getTipoSeleccion())
                    {
                        case 0: //Búsqueda por rangos
                            strConSQL+=" AND a1.fe_con BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaHoraBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos() + " 24:00:00") + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strConSQL+=" AND a1.fe_con<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos() + " 24:00:00") + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strConSQL+=" AND a1.fe_con>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos() + " 24:00:00") + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
                //Obtener los locales a consultar.
                intNumFilTblBod=objTblModLoc.getRowCountTrue();
                i=0;
                strAux=" and a5.co_com in ( ";
                for (j=0; j<intNumFilTblBod; j++)
                {
                    if (objTblModLoc.isChecked(j, INT_TBL_COM_CHK))
                    {
                        if(band==0)
                        {
                            strAux+="" + objTblModLoc.getValueAt(j, INT_TBL_COM_COD_COM) + "";
                            band++;
                        }
                        strAux+="," + objTblModLoc.getValueAt(j, INT_TBL_COM_COD_COM) + "";
                    }
                }
                strAux+=")";    
                //por si no selecciono ningun combustible 
                if(band==0)
                    strAux="";
                
                //Filtro: Tipo de item.
                strAux2="";
                if (chkCar.isSelected())
                {
                    strAux2+="'C'";
                }
                if (chkMot.isSelected())
                {
                    strAux2+=(strAux2.equals("")?"": ", ") + "'M'";
                }
                if (chkMon.isSelected())
                {
                    strAux2+=(strAux2.equals("")?"": ", ") + "'N'";
                }
                strConSQL2=" and a3.tx_tipVeh IN (" + strAux2 + ")";
                //Si es el administrador
                    strSQL=" ";
                    strSQL+=" SELECT DISTINCT a1.co_loc,a1.co_tipDoc,a4.tx_desCor as tx_desCorTipDoc, \n";
                    strSQL+="                 a4.tx_desLar as tx_deslarTipDoc,a1.co_doc,a1.ne_numDoc, \n";
                    strSQL+="                 a1.fe_doc,a3.tx_desLar as tx_desLarVeh, \n";
                    strSQL+="                 ROUND(a1.nd_valCal,2) as nd_valCal, \n";
                    strSQL+="                 ROUND(a1.nd_valMan,2) as nd_valMan, \n";
                    strSQL+="                 ROUND(a1.nd_valEfe,2) as nd_valEfe, \n";
                    strSQL+="                 ROUND(a1.nd_valDoc,2) as nd_valDoc,  \n";
                    strSQL+="                 ROUND(a1.nd_valDocGas,2) as nd_valDocGas, \n";
                    strSQL+="                 a1.st_aut, a7.tx_nom as tx_deslarEmp,a5.tx_desLar as tx_desLarCom, \n";
                    strSQL+="                 (a8.tx_ape || ' ' || a8.tx_nom) as nombreChofer,a1.st_con \n";
                    strSQL+=" FROM tbm_cabTicCom as a1 \n";
                    strSQL+=" INNER JOIN tbr_tipDocPrg as a2 ON (a1.co_emp=a2.co_emp and \n";
                    strSQL+="                                    a1.co_tipDoc=a2.co_tipDoc and a1.co_loc=a2.co_loc) \n";
                    strSQL+=" INNER JOIN tbm_veh as a3 ON (a1.co_veh=a3.co_veh) \n";
                    strSQL+=" INNER JOIN tbm_cabTipDoc as a4 ON (a1.co_emp=a4.co_emp and \n";
                    strSQL+="                                    a1.co_tipDoc=a4.co_tipDoc and a1.co_loc=a4.co_loc) \n";
                    strSQL+=" LEFT OUTER JOIN tbm_comVeh as a5 ON (a1.co_com=a5.co_com) \n";
                    strSQL+=" LEFT OUTER JOIN tbm_marVeh as a6 ON (a3.co_mar=a6.co_mar) \n"; 
                    strSQL+=" INNER JOIN tbm_emp as a7 ON (a1.co_emp=a7.co_emp) \n";
                    strSQL+=" LEFT OUTER JOIN tbm_tra as a8 ON (a1.co_cho=a8.co_tra) \n";
                    if(objParSis.getCodigoUsuario()!=1){
                        strSQL+=" INNER JOIN tbr_tipDocUsr as a9 ON (a1.co_emp=a9.co_emp and \n";
                        strSQL+="                                    a1.co_loc=a9.co_loc and a1.co_tipDoc=a9.co_tipDoc) \n";
                    }
                    strSQL+=" WHERE a1.st_reg='A'";
                    strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                    if(objParSis.getCodigoUsuario()!=1){
                        strSQL+=" AND a9.co_mnu=" + objParSis.getCodigoMenu();
                        strSQL+=" AND a9.co_usr=" + objParSis.getCodigoUsuario();
                    }
                    else{
                        strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                    }
                    if(optFil.isSelected())
                    {
                        if(txtCodLoc.getText().length()>0) {
                            strSQL+=" AND a1.co_loc=" + txtCodLoc.getText();
                        }
                        if(txtCodMar.getText().length()>0) {
                            strSQL+=" AND a6.co_mar=" + txtCodMar.getText();
                        }
                        if(txtCodDoc.getText().length()>0) {
                            strSQL+=" AND a1.co_tipDoc=" + txtCodDoc.getText();
                        }
                        if(txtCodVeh.getText().length()>0) {
                            strSQL+=" AND a1.co_veh=" + txtCodVeh.getText();
                        }
                    }
                    strSQL+=strAux;
                    strSQL+=strConSQL;
                    strSQL+=strConSQL2;
                strWhere=strSQL;
//                System.out.println("zabVeh09: "+strSQL);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                i=0;
                j=0;
                vecCodLoc = new Vector();
                vecCodTipDoc = new Vector();
                vecDesCorTipDoc = new Vector();
                vecDesLarTipDoc = new Vector();
                vecCodDoc = new Vector();
                vecNumDoc = new Vector();
                vecFecDoc = new Vector();
                vecNomVeh = new Vector();
                vecValCal = new Vector();
                vecValMan = new Vector();
                vecValEfe = new Vector();
                vecValDoc = new Vector();
                vecStAut = new Vector();
                vecValDocGas = new Vector();
                vecChkCon = new Vector();
                vecTru = new Vector();
                while(rst.next()){
                    if (blnCon){
                       vecCodLoc.add(j,rst.getString("co_loc"));
                       if(rst.getString("co_TipDoc")!=null) {vecCodTipDoc.add(j,rst.getString("co_tipDoc"));}
                       else {vecCodTipDoc.add(j,"");}
                       if(rst.getString("tx_desCorTipDoc")!=null) {vecDesCorTipDoc.add(j,rst.getString("tx_desCorTipDoc"));}
                       else {vecDesCorTipDoc.add(j,"");}
                       if(rst.getString("tx_desLarTipDoc")!=null) {vecDesLarTipDoc.add(j,rst.getString("tx_desLarTipDoc"));}
                       else {vecDesLarTipDoc.add(j,"");}
                       if(rst.getString("co_doc")!=null) {vecCodDoc.add(j,rst.getString("co_doc"));}
                       else {vecCodDoc.add(j,"");}
                       if(rst.getString("ne_numDoc")!=null) {vecNumDoc.add(j,rst.getString("ne_numDoc"));}
                       else {vecNumDoc.add(j,"");}
                       if(rst.getString("fe_doc")!=null) {vecFecDoc.add(j,rst.getString("fe_doc"));}
                       else {vecFecDoc.add(j,"");}
                       if(rst.getString("tx_desLarVeh")!=null) {vecNomVeh.add(j,rst.getString("tx_desLarVeh"));}
                       else {vecNomVeh.add(j,"");}
                       if(rst.getString("nd_valCal")!=null) {vecValCal.add(j,rst.getString("nd_valCal"));}
                       else {vecValCal.add(j,"");}  
                       if(rst.getString("nd_valMan")!=null) {vecValMan.add(j,rst.getString("nd_valMan"));}
                       else {vecValMan.add(j,"");}
                       if(rst.getString("nd_valEfe")!=null) {vecValEfe.add(j,rst.getString("nd_valEfe"));}
                       else {vecValEfe.add(j,"");}
                       if(rst.getString("nd_valDoc")!=null) {vecValDoc.add(j,rst.getString("nd_valDoc"));}
                       else {vecValDoc.add(j,"");}
                       if(rst.getString("st_aut")!=null) {vecStAut.add(j,rst.getString("st_aut"));}
                       else {vecStAut.add(j,"");}
                       if(rst.getString("nd_valDocGas")!=null) {vecValDocGas.add(j,rst.getString("nd_valDocGas"));}
                       else {vecValDocGas.add(j,"");}
                       if(rst.getString("st_con")!=null){
                           vecChkCon.add(j,"1");
                           }
                       else {
                           vecChkCon.add(j,"0");
                       }
                       j++;
                    }
                    else{break;}
                  }
                int jota=0;
                while(j>jota){
                   vecReg= new Vector(); 
                   vecReg.add(INT_TBL_DAT_LIN,"");
                   if(vecCodLoc.get(jota)!=null){vecReg.add(INT_TBL_DAT_COD_LOC,vecCodLoc.get(jota).toString());}
                   else{vecReg.add(INT_TBL_DAT_COD_LOC," ");}
                   if(vecCodTipDoc.get(jota)!=null) {vecReg.add(INT_TBL_DAT_COD_TIP_DOC,vecCodTipDoc.get(jota).toString());}
                   else{vecReg.add(INT_TBL_DAT_COD_TIP_DOC," ");}
                   if(vecDesCorTipDoc.get(jota)!=null) {vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC,vecDesCorTipDoc.get(jota).toString());}
                   else{vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC," ");}
                   if(vecDesLarTipDoc.get(jota)!=null) {vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC,vecDesLarTipDoc.get(jota).toString());}
                   else{vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC," ");}
                   if(vecCodDoc.get(jota)!=null){vecReg.add(INT_TBL_DAT_COD_DOC,vecCodDoc.get(jota).toString());}
                   else{vecReg.add(INT_TBL_DAT_COD_DOC," ");}
                   if(vecNumDoc.get(jota)!=null){vecReg.add(INT_TBL_DAT_NUM_DOC,vecNumDoc.get(jota).toString());}
                   else{vecReg.add(INT_TBL_DAT_NUM_DOC," ");}
                   if(vecFecDoc.get(jota)!=null){vecReg.add(INT_TBL_DAT_FEC_DOC,vecFecDoc.get(jota).toString());}
                   else{vecReg.add(INT_TBL_DAT_FEC_DOC," ");}
                   if(vecNomVeh.get(jota)!=null){vecReg.add(INT_TBL_DAT_DES_LAR_VEH,vecNomVeh.get(jota).toString());}
                   else{vecReg.add(INT_TBL_DAT_DES_LAR_VEH," ");}        
                   if(vecValCal.get(jota)!=null){vecReg.add(INT_TBL_DAT_VAL_CAL,vecValCal.get(jota).toString());}
                   else{vecReg.add(INT_TBL_DAT_VAL_CAL," ");}
                   if(vecValMan.get(jota)!=null){vecReg.add(INT_TBL_DAT_VAL_MAN,vecValMan.get(jota).toString());}
                   else{vecReg.add(INT_TBL_DAT_VAL_MAN," ");}               
                   if(vecValEfe.get(jota)!=null){vecReg.add(INT_TBL_DAT_VAL_EFE,vecValEfe.get(jota).toString());}
                   else{vecReg.add(INT_TBL_DAT_VAL_EFE," ");}
                   if(vecValDoc.get(jota)!=null){vecReg.add(INT_TBL_DAT_VAL_DOC,vecValDoc.get(jota).toString());}
                   else{vecReg.add(INT_TBL_DAT_VAL_DOC," ");}
                   if(vecValDocGas.get(jota)!=null){vecReg.add(INT_TBL_DAT_VAL_DOC_GAS,vecValDocGas.get(jota).toString());}
                   else{vecReg.add(INT_TBL_DAT_VAL_DOC_GAS," ");}
                   vecReg.add(INT_TBL_DAT_CON_TIK,"Boton");
                   if(vecChkCon.get(jota).equals("1")){
                       vecReg.add(INT_TBL_DAT_CHK_CON,true);
                       vecReg.add(INT_TBL_DAT_TRUE_FALSE,"1");
                   }
                   else{
                       vecReg.add(INT_TBL_DAT_CHK_CON,false);
                       vecReg.add(INT_TBL_DAT_TRUE_FALSE,"0");
                   }
                   vecDat.add(vecReg);
                   jota++;
                }
             }     
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
            vecDat.clear();
            objTblTot.calcularTotales();           
            tot_doc=objTblTot.getValueAt(0, 12).toString();
            System.out.println(tot_doc);
            tot_docGas=objTblTot.getValueAt(0, 13).toString();
            System.out.println(tot_docGas);
            if(blnCon){

                     lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
             }
            else{
                 lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            butCon.setText("Consultar");
            pgrSis.setIndeterminate(false);    
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

//    /**
//     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
//     * del mouse (mover el mouse; arrastrar y soltar).
//     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
//     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
//     * resulta muy corto para mostrar leyendas que requieren más espacio.
//     */
    
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_COD_LOC: strMsg="Código del Local"; break;
                case INT_TBL_DAT_COD_TIP_DOC: strMsg="Código del tipo de Documento"; break;
                case INT_TBL_DAT_DES_COR_TIP_DOC: strMsg="Descripción corta del tipo de Documento"; break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC: strMsg="Descripción larga del tipo de Documento"; break;
                case INT_TBL_DAT_COD_DOC: strMsg="Código del documento"; break;
                case INT_TBL_DAT_NUM_DOC: strMsg="Número del documento"; break;
                case INT_TBL_DAT_FEC_DOC: strMsg="Fecha del documento"; break;
                case INT_TBL_DAT_DES_LAR_VEH: strMsg="Descripción larga del vehículo"; break;
                case INT_TBL_DAT_VAL_CAL: strMsg="Valor calculado"; break;
                case INT_TBL_DAT_VAL_MAN: strMsg="Valor manual"; break;
                case INT_TBL_DAT_VAL_EFE: strMsg="Valor en efectivo"; break;
                case INT_TBL_DAT_VAL_DOC: strMsg="Valor del documento"; break;
                case INT_TBL_DAT_VAL_DOC_GAS: strMsg="Valor del documento (Gasolinera)"; break;
                case INT_TBL_DAT_CON_TIK: strMsg="Muestra el 'Ticket de combustible'"; break;
                case INT_TBL_DAT_CHK_CON: strMsg="Conciliado"; break;
                default: strMsg=""; break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
      
//    /**
//     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a utilizar
//     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
//     * debe llenar o corregir.
//     */
    
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    

//    /**
//     * Esta clase iplementa la interface DocumentListener que observa los cambios que
//     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
//     * Se la usa en el sistema para determinar si existe alg�n cambio que se deba grabar
//     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
//     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
//     * presentar� un mensaje advirtiendo que si no guarda los cambios los perder�.
//     */
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
    
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);         
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                 tabFrm.setSelectedIndex(1);
            }
            objThrGUI=null;
        }
    }
    
    ///////////
    ////  
    //REPORTES
    //// 
    ///////////
    
//   /**
//     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
//     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
//     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
//     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
//     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
//     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
//     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
//     */
    private class ZafThreadGUIRpt extends Thread
    {
        private int intIndFun;

        public ZafThreadGUIRpt()
        {
            intIndFun=0;
        }

        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                    butvpre.setEnabled(false);
                    generarRpt(1);
                    butvpre.setEnabled(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                    butvpre.setEnabled(false);
                    generarRpt(2);
                    butvpre.setEnabled(true);
                    break;
            }
            objThrGUIRpt=null;
        }

//        /**
//         * Esta función establece el indice de la función a ejecutar. En la clase Thread
//         * se pueden ejecutar diferentes funciones. Esta función sirve para determinar
//         * la función que debe ejecutar el Thread.
//         * @param indice El indice de la función a ejecutar.
//         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }
//  /**
//     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
//     * @param intTipRpt El tipo de reporte a generar.
//     * <BR>Puede tomar uno de los siguientes valores:
//     * <UL>
//     * <LI>0: Impresión directa.
//     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
//     * <LI>2: Vista preliminar.
//     * </UL>
//     * @return true: Si se pudo generar el reporte.
//     * <BR>false: En el caso contrario.
//     */
    private boolean generarRpt(int intTipRpt)
    {
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con==null) {
                System.out.println("Falla de la conexion...");}
            int codEmp=objParSis.getCodigoEmpresa();
            int codLoc=objParSis.getCodigoLocal();
            int codUsu=objParSis.getCodigoUsuario();
            int codMnu=objParSis.getCodigoMenu();
            objRptSis.cargarListadoReportes(con,codEmp,codLoc,codMnu,codUsu);
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==ZafRptSis.INT_OPC_ACE)
            {
                //Obtener la fecha y hora del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                datFecAux=null;
                intNumTotRpt=objRptSis.getNumeroTotalReportes();           
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            case 454:
                            default:
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.
                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                mapPar.put("strWhere", strWhere);
                                mapPar.put("FecIni", strFecIni);
                                mapPar.put("FecFin", strFecFin);
                                mapPar.put("co_emp", objParSis.getCodigoEmpresa());
                                mapPar.put("tot_doc", tot_doc);
                                mapPar.put("tot_docGas", tot_docGas);
                                mapPar.put("strCamAudRpt", objParSis.getNombreUsuario() + "   " + strFecHorSer + "   " + this.getClass().getName() + "   " + strNomRpt);                                
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
    

//    /**
//     * Esta funci�n muestra un mensaje "showConfirmDialog". Presenta las opciones
//     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
//     * seleccionando una de las opciones que se presentan.
//     */
    
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
   
    
    
    /*
     * llenar la consulta de marcas  
     */
    
    private boolean configurarMarcas()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_mar");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("200");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_mar, a1.tx_deslar";
            strSQL+=" FROM tbm_marVeh a1 WHERE a1.st_reg NOT IN('I','E')";
            strSQL+=" ORDER BY a1.co_mar";
//            System.out.println("configurarMarcas:.." + strSQL);
            vcoMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Marcas", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoMar.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

//    /**
//     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
//     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
//     * una básqueda directa (No se muestra la ventana de consulta a menos que no
//     * exista lo que se está buscando) o presentar la ventana de consulta para que
//     * el usuario seleccione la opcián que desea utilizar.
//     * @param intTipBus El tipo de básqueda a realizar.
//     * @return true: Si no se presentá ningán problema.
//     * <BR>false: En el caso contrario.
//     */
    private boolean mostrarMarcas(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
               case 0: //Mostrar la ventana de consulta.
                    vcoMar.setCampoBusqueda(2);
                    vcoMar.setVisible(true);
                    if (vcoMar.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodMar.setText(vcoMar.getValueAt(1));
                        txtNomMar.setText(vcoMar.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoMar.buscar("a1.co_mar", txtCodMar.getText()))
                    {
                        txtCodMar.setText(vcoMar.getValueAt(1));
                        txtNomMar.setText(vcoMar.getValueAt(2));
                    }
                    else
                    {
                        vcoMar.setCampoBusqueda(0);
                        vcoMar.setCriterio1(11);
                        vcoMar.cargarDatos();
                        vcoMar.setVisible(true);
                        if (vcoMar.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodMar.setText(vcoMar.getValueAt(1));
                            txtNomMar.setText(vcoMar.getValueAt(2));
                        }
                        else
                        {
                            txtCodMar.setText(strCodMar);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoMar.buscar("a1.tx_deslar", txtNomMar.getText()))
                    {
                        txtCodMar.setText(vcoMar.getValueAt(1));
                        txtNomMar.setText(vcoMar.getValueAt(2));
                    }
                    else
                    {
                        vcoMar.setCampoBusqueda(2);
                        vcoMar.setCriterio1(11);
                        vcoMar.cargarDatos();
                        vcoMar.setVisible(true);
                        if (vcoMar.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodMar.setText(vcoMar.getValueAt(1));
                            txtNomMar.setText(vcoMar.getValueAt(3));
                        }
                        else
                        {
                            txtNomMar.setText(strNomMar);
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
    
  
//    /**
//     * Esta función permite actualizar los registros del detalle.
//     * @return true: Si se pudo actualizar los registros.
//     * <BR>false: En el caso contrario.
//     */
    
    
    private boolean actualizarDet()
    {
        int intNumFil, i;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                intNumFil=objTblMod.getRowCountTrue();
                for (i=0; i<intNumFil;i++){
                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
                        //Armar la sentencia SQL.
                       strSQL="";
                        strSQL+="UPDATE tbm_cabTicCom";
                        strSQL+=" SET nd_ValDocGas=" + objTblMod.getValueAt(i,INT_TBL_DAT_VAL_DOC_GAS)+"";
                        strSQL+=" ,co_usrCon=" + objParSis.getCodigoUsuario() +"";
                        strSQL+=" ,fe_con='" + objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos()) +"'";
                        strSQL+=" ,st_con='S'";
                        strSQL+=" WHERE co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC) + "";
                        strSQL+=" AND co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" and co_tipDoc=( SELECT co_tipDoc FROM tbr_tipDocPrg WHERE co_emp="+ objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + " AND co_mnu=" + objParSis.getCodigoMenu() + ");";    
//                        System.out.println(strSQL);
                        stm.executeUpdate(strSQL);
                    }
                }
                stm.close();
                con.close();
                stm=null;
                con=null;
                datFecAux=null;
                //Inicializo el estado de las filas.
                objTblMod.initRowsState();
                objTblMod.setDataModelChanged(false);
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
//     /**
//     * Esta clase hereda de la interface TableModelListener que permite determinar
//     * cambios en las celdas del JTable.
//     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }

    
    /*
     * llenar la consulta de marcas  
     */
    
    private boolean configurarCombustible()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_com");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_com, a1.tx_deslar";
            strSQL+=" FROM tbm_comVeh a1 WHERE a1.st_reg ='A'";
            strSQL+=" ORDER BY a1.co_com";
//            System.out.println("configurarCombustible:.." + strSQL);
            vcoCom=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Combustibles", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCom.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    
    private boolean mostrarLocal(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: 
                    vcoLoc.setVisible(true);
                    if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));//selecciona de popup
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(0);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtCodLoc.setText(strCodLoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoLoc.buscar("a1.tx_nom", txtNomLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    } 
                    else
                    {
                        vcoLoc.setCampoBusqueda(2);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtNomLoc.setText(strNomLoc);
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

private boolean configurarLocal(){
        boolean blnRes=true;
        String aux="";
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_loc");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("374");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){ 
               if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                   strSQL="";
                   strSQL+=" SELECT a1.co_loc ,a1.tx_nom";
                    strSQL+=" FROM tbm_Loc AS a1";
                    strSQL+=" WHERE a1.co_emp>0";
                    strSQL+=" ORDER BY a1.co_loc";
               }
               else{
                   strSQL="";
                   strSQL+=" SELECT a1.co_loc ,a1.tx_nom";
                    strSQL+=" FROM tbm_Loc AS a1";
                    strSQL+=" WHERE a1.co_emp>0 and a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" ORDER BY a1.co_loc";
               }
           }
           else{
                strSQL="";
                strSQL+=" SELECT DISTINCT a3.co_loc as co_loc, a3.tx_nom as tx_nom ";
                strSQL+=" FROM tbr_locPrgUsr as a2";
                strSQL+=" INNER JOIN tbm_loc as a3 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc)";
                strSQL+=" INNER JOIN tbm_emp as a4 ON (a2.co_emprel=a4.co_emp)";
                strSQL+=" WHERE 1=1 and a2.st_reg in ('A','P')";
                strSQL+=" and a2.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" and a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" and a2.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" and a2.co_mnu=" + objParSis.getCodigoMenu();
           strSQL+=" ORDER BY a3.co_loc";
         }
//             System.out.println("LOCALES...."+ strSQL);
            vcoLoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Locales", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoLoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     /*
     * llenar la consulta de Vehiculos
     */
    
    private boolean configurarDocumentos()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipDoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción Corta");
            arlAli.add("Descripción Larga");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("350");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){//Admin
                strSQL="";
                strSQL+=" SELECT DISTINCT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar";
                strSQL+=" FROM tbm_cabTipDoc as a1";
                strSQL+=" INNER JOIN tbr_tipDocPrg as a2 ON (a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.st_reg='A' and a2.co_mnu=3730 and a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" and a1.co_loc=" + objParSis.getCodigoLocal();
                if(txtCodDoc.getText().length()>0)
                strSQL+=" and a1.co_tipDoc=" + txtCodDoc.getText();
                strSQL+=" ORDER BY a1.co_tipDoc";
            }
            else{
                strSQL="";
                strSQL+=" SELECT DISTINCT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar ";
                strSQL+=" FROM tbm_cabTipDoc as a1";
                strSQL+=" INNER JOIN tbr_tipDocPrg as a2 ON (a1.co_emp=a2.co_emp and ";
                strSQL+=" a1.co_loc=a2.co_loc and a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" INNER JOIN tbr_tipDocUsr as a3 ON (a1.co_emp=a3.co_emp and";
                strSQL+=" a1.co_loc=a3.co_loc and a1.co_tipDoc=a3.co_tipDoc and a2.co_mnu=a3.co_mnu)";
                strSQL+=" WHERE a1.st_reg='A' and a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" and a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" and a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" and a3.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" ORDER BY a1.co_tipDoc";
            }
//            System.out.println("configurarDocumentos:.." + strSQL);
            vcoDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Vehiculos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
  //  mostrarDocumentos

private boolean mostrarDocumentos(int intTipBus)
    {
       boolean blnRes=true;
        try
        {
//            System.out.println("mostrarDocumentos::::....");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoDoc.setCampoBusqueda(2);
                    vcoDoc.setVisible(true);
                   if (vcoDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodDoc.setText(vcoDoc.getValueAt(1));
                        txtDesCorDoc.setText(vcoDoc.getValueAt(2));
                        txtNomDoc.setText(vcoDoc.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción Corta del tipo de doc".
                    if (vcoDoc.buscar("a1.tx_desCor", txtDesCorDoc.getText())){
                        txtCodDoc.setText(vcoDoc.getValueAt(1));
                        txtDesCorDoc.setText(vcoDoc.getValueAt(2));
                        txtNomDoc.setText(vcoDoc.getValueAt(3));
                    }
                    else
                    {
                        vcoDoc.setCampoBusqueda(1);
                        vcoDoc.setCriterio1(11);
                        vcoDoc.cargarDatos();
                        vcoDoc.setVisible(true);
                        if (vcoDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodDoc.setText(vcoDoc.getValueAt(1));
                            txtDesCorDoc.setText(vcoDoc.getValueAt(2));
                            txtNomDoc.setText(vcoDoc.getValueAt(3));
                        }
                        else
                        {
                            txtDesCorDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
               case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoDoc.buscar("a1.tx_desLar", txtNomDoc.getText()))
                    {
                         txtCodDoc.setText(vcoDoc.getValueAt(1));
                         txtDesCorDoc.setText(vcoDoc.getValueAt(2));
                         txtNomDoc.setText(vcoDoc.getValueAt(3));
                    }
                    else
                    {
                         vcoDoc.setCampoBusqueda(2);
                        vcoDoc.setCriterio1(11);
                        vcoDoc.cargarDatos();
                        vcoDoc.setVisible(true);
                        if (vcoDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodDoc.setText(vcoDoc.getValueAt(1));
                            txtDesCorDoc.setText(vcoDoc.getValueAt(2));
                            txtNomDoc.setText(vcoDoc.getValueAt(3));
                        }
                        else
                        {
                            txtNomDoc.setText(strDocNom);
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

    /*
     * llenar la consulta de Vehiculos
     */
    
    private boolean configurarVehiculos()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_veh");
            arlCam.add("a1.tx_pla");
            arlCam.add("a1.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Placa");
            arlAli.add("Descripción");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("350");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_veh,a1.tx_pla,a1.tx_desLar";
            strSQL+=" FROM tbm_veh as a1 ";
            strSQL+=" LEFT OUTER JOIN tbm_comVeh as a2 ON (a1.co_com=a2.co_com)";
            strSQL+=" WHERE a1.st_reg ='A' and a1.tx_tipVeh <> 'O'";
            strSQL+=" ORDER BY a1.co_veh";
//            System.out.println("configurarVehiculos:.." + strSQL);
            vcoVeh=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Vehiculos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoVeh.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    
  //  mostrarVehiculos

private boolean mostrarVehiculos(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
//            System.out.println("mostrarVehiculos::::....");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoVeh.setCampoBusqueda(1);
                    vcoVeh.setVisible(true);
                   if (vcoVeh.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodVeh.setText(vcoVeh.getValueAt(1));
                        txtNomVeh.setText(vcoVeh.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Vehiculo".
                    if (vcoVeh.buscar("a1.co_veh", txtCodVeh.getText())){
                         txtCodVeh.setText(vcoVeh.getValueAt(1));
                        txtNomVeh.setText(vcoVeh.getValueAt(3));
                    }
                    else
                    {
                        vcoVeh.setCampoBusqueda(0);
                        vcoVeh.setCriterio1(11);
                        vcoVeh.cargarDatos();
                        vcoVeh.setVisible(true);
                        if (vcoVeh.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodVeh.setText(vcoVeh.getValueAt(1));
                        txtNomVeh.setText(vcoVeh.getValueAt(3));
                        }
                        else
                        {
                            txtCodVeh.setText(strVehCod);
                        }
                    }
                    break;
               case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoVeh.buscar("a1.tx_desLar", txtNomVeh.getText()))
                    {
                         txtCodVeh.setText(vcoVeh.getValueAt(1));
                        txtNomVeh.setText(vcoVeh.getValueAt(3));
                    }
                    else
                    {
                         vcoVeh.setCampoBusqueda(2);
                        vcoVeh.setCriterio1(11);
                        vcoVeh.cargarDatos();
                        vcoVeh.setVisible(true);
                        if (vcoVeh.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodVeh.setText(vcoVeh.getValueAt(1));
                        txtNomVeh.setText(vcoVeh.getValueAt(3));
                        }
                        else
                        {
                            txtNomVeh.setText(strVehNom);
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
//////    
//ZafVen04
/////
/**
     * Esta función permite consultar los locales de acuerdo al siguiente criterio:
     * <UL>
     * <LI>Si se ingresa a la empresa "Grupo" se muestran todos los locales.
     * <LI>Si se ingresa a cualquier otra empresa se muestran sólo los locales pertenecientes a la empresa seleccionada.
     * </UL>
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCom()
    {
        int intCodEmp;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_com, a1.tx_desLar";
                strSQL+=" FROM tbm_comVeh AS a1";
                strSQL+=" ORDER BY a1.co_com";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_COM_LIN,"");
                    vecReg.add(INT_TBL_COM_CHK,Boolean.TRUE);
                    vecReg.add(INT_TBL_COM_COD_COM,rst.getString("co_com"));
                    vecReg.add(INT_TBL_COM_NOM_COM,rst.getString("tx_desLar"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModLoc.setData(vecDat);
                tblLoc.setModel(objTblModLoc);
                vecDat.clear();
                blnMarTodChkTblEmp=false;
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
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblLocMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModLoc.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblLoc.columnAtPoint(evt.getPoint())==INT_TBL_COM_CHK)
            {
                if (blnMarTodChkTblEmp)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(true, i, INT_TBL_COM_CHK);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(false, i, INT_TBL_COM_CHK);
                    }
                    blnMarTodChkTblEmp=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
//     /**
//     * Esta función configura el JTable "tblLoc".
//     * @return true: Si se pudo configurar el JTable.
//     * <BR>false: En el caso contrario.
//     */
    private boolean configurarTblLoc()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_COM_LIN,"");
            vecCab.add(INT_TBL_COM_CHK,"");
            vecCab.add(INT_TBL_COM_COD_COM,"Cód.Com.");
            vecCab.add(INT_TBL_COM_NOM_COM,"Combustible");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModLoc=new ZafTblMod();
            objTblModLoc.setHeader(vecCab);
            tblLoc.setModel(objTblModLoc);
           
            //Configurar JTable: Establecer tipo de selección.
            tblLoc.setRowSelectionAllowed(true);
            tblLoc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblLoc);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblLoc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblLoc.getColumnModel();
            tcmAux.getColumn(INT_TBL_COM_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_COM_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_COM_COD_COM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_COM_NOM_COM).setPreferredWidth(221);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_COM_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblLoc.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblModLoc.addSystemHiddenColumn(INT_TBL_LOC_COD_EMP, tblLoc);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblLoc.getTableHeader().addMouseMotionListener(new ZafMouMotAdaLoc());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblLoc.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblLocMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_COM_CHK);
            objTblModLoc.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblLoc);
            tcmAux.getColumn(INT_TBL_COM_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_COM_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_COM_COD_COM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblLoc);
            tcmAux.getColumn(INT_TBL_COM_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiChk.isChecked())
                    {
//                        objTblMod.setValueAt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_PEN), tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                    }
                    else
                    {
//                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                    }
                }
            });

            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblLoc.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModLoc.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
//     /**
//     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
//     * del mouse (mover el mouse; arrastrar y soltar).
//     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
//     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
//     * resulta muy corto para mostrar leyendas que requieren más espacio.
//     */
    private class ZafMouMotAdaLoc extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblLoc.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_COM_LIN:
                    strMsg="";
                    break;
                case INT_TBL_COM_COD_COM:
                    strMsg="Código del combustible";
                    break;
                case INT_TBL_COM_NOM_COM:
                    strMsg="Descripción larga del combustible";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblLoc.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    private class ButConOcp extends Librerias.ZafTableColBut.ZafTableColBut_uni {
      public ButConOcp(javax.swing.JTable tbl, int intIdx) {
          super(tbl, intIdx, "Documento.");
      }
      public void butCLick() {
          int intCol = tblDat.getSelectedRow();
          llamarVentana( tblDat.getValueAt(intCol, INT_TBL_DAT_COD_DOC).toString());
      }    
  }
    
    
    
 private void llamarVentana(String strCodDoc){
//     System.out.println("llamarVentana::.." + strCodDoc);
           if (objReg != null){
               objReg.dispose();
           }
           ZafVeh09 objReg09 = new  Vehiculos.ZafVeh09.ZafVeh09(objParSis);
          ZafVeh07 objReg = new  Vehiculos.ZafVeh07.ZafVeh07(objParSis,objReg09, strCodDoc);
          
  //ORIGINAL         this.getParent().add(objReg, javax.swing.JLayeredPane.DEFAULT_LAYER);
           this.getParent().add(objReg, javax.swing.JOptionPane.getFrameForComponent(this));
           objReg.show();     
    } 
 
 
 private boolean isCamVal()
    {
        int intNumFilTblLoc, i, j;
        //Validar que esté seleccionada al menos un local.
        intNumFilTblLoc=objTblModLoc.getRowCountTrue();
        i=0;
        for (j=0; j<intNumFilTblLoc; j++)
        {
            if (objTblModLoc.isChecked(j, INT_TBL_COM_CHK))
            {
                i++;
                break;
            }
        }
        if (i==0)
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar al menos un Combustible.<BR>Seleccione un combustible y vuelva a intentarlo.</HTML>");
            tblLoc.requestFocus();
            return false;
        }
        
        if ( (!chkCar.isSelected()) && (!chkMot.isSelected()) && (!chkMon.isSelected()))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de Vehiculo</FONT> es obligatorio.<BR>Seleccione al menos una de las opciones y vuelva a intentarlo.</HTML>");
            chkCar.requestFocus();
            return false;
        }
        return true;
    }
 
 
}
