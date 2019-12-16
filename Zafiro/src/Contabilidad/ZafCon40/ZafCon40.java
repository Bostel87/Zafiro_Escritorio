/*
 * ZafMae41.java
 *
 * Created on July 10, 2007, 11:14 AM
 */


package Contabilidad.ZafCon40;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import javax.swing.SpinnerNumberModel;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import javax.swing.JScrollBar;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;

/**
 *
 * @author  ilino
 */
public class ZafCon40 extends javax.swing.JInternalFrame {
    final int intJspValMin=2006;
    final int intJspValMax=9999;
    final int intJspValIni=2007;
    final int intJspValInc=1;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblModCta, objTblModCtaTot, objTblModDetCta, objTblModDetCtaTot;
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblBus objTblBus;
    javax.swing.JInternalFrame jfrThis;
    private MiToolBar objTooBar;
    private Connection con, conCab, conTmp;
    private Statement stm, stmCab, stmTmp, stmTmp2;
    private ResultSet rst, rstCab, rstTmp, rstTmp2;
    ZafColNumerada objColNum;
    Vector vecCabCta, vecRegCta, vecDatCta, vecAuxCta;
    Vector vecCabDetCta, vecRegDetCta, vecDatDetCta, vecAuxDetCta;
    private ZafTblCelRenLbl objTblCelRenLblDatCta, objTblCelRenLblTotCta;
    private ZafTblCelRenLbl objTblCelRenLblDatDetCta, objTblCelRenLblTotDetCta;
    private ZafTblEdi objTblEdiCta, objTblEdiDetCta;
    private ZafVenCon vcoTipGrp;
    private String strAux, strSQL, strCodGrp, strDesLarGrp;
    private java.util.Date datFecAux;
    private boolean blnHayCam;
    private ZafMouMotAda objMouMotAda;
    private boolean blnPerUsrMod;
    //PARA HABILITAR NUEVAMENTE EL BOTON DE INSERCION CUANDO SE DE CLICK EN BOTON CANCELAR
    private boolean blnPerUsrIns;
        
    /*PARA COLOCAR EN EL ARRAYLIST QUE SE CARGA AL INICIAR EL FORMULARIO PARA TENER TODA LA INFORMACION DEL DETALLE DE LAS CUENTAS SIN IMPORTAR  
     * EL AÑO Y LA CUENTA Y ASI TENER EN MEMORIA ESOS DATOS Y NO HACER MUCHOS ACCESOS A LA DB, LUEGO LO QUE SE HARA ES Q AL MOMENTO DE SELECCIONAR
     * EL AÑO Y CODIGO DE CUENTA SE LO COMPARARA CON LOS REGISTROS Q SE ENCUENTRAN EN EL ARRAYLIST
     */
    final int INT_ARL_DET_ANI=0;
    final int INT_ARL_DET_COD_CTA=1;
    final int INT_ARL_DET_COD_REG=2;
    final int INT_ARL_DET_NOM_DET=3;
    final int INT_ARL_DET_VAL_ENE=4;
    final int INT_ARL_DET_VAL_FEB=5;
    final int INT_ARL_DET_VAL_MAR=6;
    final int INT_ARL_DET_VAL_ABR=7;
    final int INT_ARL_DET_VAL_MAY=8;
    final int INT_ARL_DET_VAL_JUN=9;
    final int INT_ARL_DET_VAL_JUL=10;
    final int INT_ARL_DET_VAL_AGO=11;
    final int INT_ARL_DET_VAL_SEP=12;
    final int INT_ARL_DET_VAL_OCT=13;
    final int INT_ARL_DET_VAL_NOV=14;
    final int INT_ARL_DET_VAL_DIC=15;    
    
    
    
    //PARA LA TABLA DE CUENTA Y TOTALES DE CUENTAS
    final int INT_TBL_DAT_CTA_LIN=0;
    final int INT_TBL_DAT_CTA_COD_CTA=1;
    final int INT_TBL_DAT_CTA_NUM_CTA=2;
    final int INT_TBL_DAT_CTA_NOM_CTA=3;
    final int INT_TBL_DAT_CTA_MES_ENE=4;
    final int INT_TBL_DAT_CTA_OBS_ENE=5;
    final int INT_TBL_DAT_CTA_MES_FEB=6;
    final int INT_TBL_DAT_CTA_OBS_FEB=7;
    final int INT_TBL_DAT_CTA_MES_MAR=8;
    final int INT_TBL_DAT_CTA_OBS_MAR=9;
    final int INT_TBL_DAT_CTA_MES_ABR=10;
    final int INT_TBL_DAT_CTA_OBS_ABR=11;
    final int INT_TBL_DAT_CTA_MES_MAY=12;
    final int INT_TBL_DAT_CTA_OBS_MAY=13;
    final int INT_TBL_DAT_CTA_MES_JUN=14;
    final int INT_TBL_DAT_CTA_OBS_JUN=15;
    final int INT_TBL_DAT_CTA_MES_JUL=16;
    final int INT_TBL_DAT_CTA_OBS_JUL=17;
    final int INT_TBL_DAT_CTA_MES_AGO=18;
    final int INT_TBL_DAT_CTA_OBS_AGO=19;
    final int INT_TBL_DAT_CTA_MES_SEP=20;
    final int INT_TBL_DAT_CTA_OBS_SEP=21;
    final int INT_TBL_DAT_CTA_MES_OCT=22;
    final int INT_TBL_DAT_CTA_OBS_OCT=23;
    final int INT_TBL_DAT_CTA_MES_NOV=24;
    final int INT_TBL_DAT_CTA_OBS_NOV=25;
    final int INT_TBL_DAT_CTA_MES_DIC=26;
    final int INT_TBL_DAT_CTA_OBS_DIC=27;
    final int INT_TBL_DAT_CTA_TOT=28;
    final int INT_TBL_DAT_CTA_EXI=29;
    final int INT_TBL_DAT_CTA_NOD_PAD=30;
    
    //PARA LA TABLA DE DETALLES DE CUENTAS Y TOTALES DE DETALLE DE CUENTAS
    final int INT_TBL_DAT_DET_LIN=0;
    final int INT_TBL_DAT_DET_DET_CTA=1;
    final int INT_TBL_DAT_DET_MES_ENE=2;
    final int INT_TBL_DAT_DET_MES_FEB=3;
    final int INT_TBL_DAT_DET_MES_MAR=4;
    final int INT_TBL_DAT_DET_MES_ABR=5;
    final int INT_TBL_DAT_DET_MES_MAY=6;
    final int INT_TBL_DAT_DET_MES_JUN=7;
    final int INT_TBL_DAT_DET_MES_JUL=8;
    final int INT_TBL_DAT_DET_MES_AGO=9;
    final int INT_TBL_DAT_DET_MES_SEP=10;
    final int INT_TBL_DAT_DET_MES_OCT=11;
    final int INT_TBL_DAT_DET_MES_NOV=12;
    final int INT_TBL_DAT_DET_MES_DIC=13;
    final int INT_TBL_DAT_DET_TOT=14;
    
    private JScrollBar barTblCtaDat, barTblCtaTot;
    private JScrollBar barTblDetCtaDat, barTblDetCtaTot;
    
    private ZafTblCelEdiTxt objTblCelEdiTxtCtaEne;
    private ZafTblCelEdiTxt objTblCelEdiTxtCtaFeb;
    private ZafTblCelEdiTxt objTblCelEdiTxtCtaMar;
    private ZafTblCelEdiTxt objTblCelEdiTxtCtaAbr;
    private ZafTblCelEdiTxt objTblCelEdiTxtCtaMay;
    private ZafTblCelEdiTxt objTblCelEdiTxtCtaJun;
    private ZafTblCelEdiTxt objTblCelEdiTxtCtaJul;
    private ZafTblCelEdiTxt objTblCelEdiTxtCtaAgo;
    private ZafTblCelEdiTxt objTblCelEdiTxtCtaSep;
    private ZafTblCelEdiTxt objTblCelEdiTxtCtaOct;
    private ZafTblCelEdiTxt objTblCelEdiTxtCtaNov;
    private ZafTblCelEdiTxt objTblCelEdiTxtCtaDic;
    
    
    private ZafTblCelEdiTxt objTblCelEdiTxtDetCtaEne;
    private ZafTblCelEdiTxt objTblCelEdiTxtDetCtaFeb;
    private ZafTblCelEdiTxt objTblCelEdiTxtDetCtaMar;
    private ZafTblCelEdiTxt objTblCelEdiTxtDetCtaAbr;
    private ZafTblCelEdiTxt objTblCelEdiTxtDetCtaMay;
    private ZafTblCelEdiTxt objTblCelEdiTxtDetCtaJun;
    private ZafTblCelEdiTxt objTblCelEdiTxtDetCtaJul;
    private ZafTblCelEdiTxt objTblCelEdiTxtDetCtaAgo;
    private ZafTblCelEdiTxt objTblCelEdiTxtDetCtaSep;
    private ZafTblCelEdiTxt objTblCelEdiTxtDetCtaOct;
    private ZafTblCelEdiTxt objTblCelEdiTxtDetCtaNov;
    private ZafTblCelEdiTxt objTblCelEdiTxtDetCtaDic;
    
    
    private ZafTblCelRenBut objTblCelRenButEne;
    private ZafTblCelEdiButDlg objTblCelEdiButEne;
    private ZafTblCelRenBut objTblCelRenButFeb;
    private ZafTblCelEdiButDlg objTblCelEdiButFeb;
    private ZafTblCelRenBut objTblCelRenButMar;
    private ZafTblCelEdiButDlg objTblCelEdiButMar;
    private ZafTblCelRenBut objTblCelRenButAbr;
    private ZafTblCelEdiButDlg objTblCelEdiButAbr;
    private ZafTblCelRenBut objTblCelRenButMay;
    private ZafTblCelEdiButDlg objTblCelEdiButMay;
    private ZafTblCelRenBut objTblCelRenButJun;
    private ZafTblCelEdiButDlg objTblCelEdiButJun;
    private ZafTblCelRenBut objTblCelRenButJul;
    private ZafTblCelEdiButDlg objTblCelEdiButJul;
    private ZafTblCelRenBut objTblCelRenButAgo;
    private ZafTblCelEdiButDlg objTblCelEdiButAgo;
    private ZafTblCelRenBut objTblCelRenButSep;
    private ZafTblCelEdiButDlg objTblCelEdiButSep;
    private ZafTblCelRenBut objTblCelRenButOct;
    private ZafTblCelEdiButDlg objTblCelEdiButOct;
    private ZafTblCelRenBut objTblCelRenButNov;
    private ZafTblCelEdiButDlg objTblCelEdiButNov;
    private ZafTblCelRenBut objTblCelRenButDic;
    private ZafTblCelEdiButDlg objTblCelEdiButDic;
    
    private JScrollBar barDatCta, barDatTot;
    private JScrollBar barDetDatCta, barDetDatTot;
    
    ZafPerUsr objPerUsr;
    
    private ZafCon40_01 objCon40_01Ene, objCon40_01Feb, objCon40_01Mar, objCon40_01Abr, objCon40_01May, objCon40_01Jun;
    private ZafCon40_01 objCon40_01Jul, objCon40_01Ago, objCon40_01Sep, objCon40_01Oct, objCon40_01Nov, objCon40_01Dic;    
    
    //PARA CARGAR LA INFORMACION COMPLETA AL INICIAR EL FORMULARIO PERO PARA GRUPOS DE CUENTAS
    private ArrayList arlRegIniFrm, arlDatIniFrm;
    //PARA CARGAR LA INFORMACION COMPLETA AL INICIAR EL FORMULARIO PERO PARA DETALLE DE CUENTA
    private ArrayList arlRegDetCtaIniFrm, arlDatDetCtaIniFrm;    
    
    //PARA GUARDAR LOS MESES QUE SE ENCUENTRAN CERRADOS
    private ArrayList arlRegMesCie, arlDatMesCie;
    
    final int INT_ARL_CIE_COD_EMP=0;
    final int INT_ARL_CIE_ANI=1;
    final int INT_ARL_CIE_MES=2;
    
    
    private int intFilSelTblCta;
    private int flag;
    
    private String strArlAni, strArlMes;
    
    
    /** Creates new form ZafMae41 */
    public ZafCon40(ZafParSis obj) {
        try{
            initComponents();
            //Inicializar objetos.
            this.objParSis=obj;
            jfrThis=this;
            objParSis=(ZafParSis)obj.clone();
            jspAni.setModel(new SpinnerNumberModel(intJspValIni, intJspValMin, intJspValMax, intJspValInc));

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
    private void initComponents() {//GEN-BEGIN:initComponents
        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        panCabTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCabGrp = new javax.swing.JPanel();
        lblTitTipDoc = new javax.swing.JLabel();
        txtCodGrp = new javax.swing.JTextField();
        txtDesLarGrp = new javax.swing.JTextField();
        butGrp = new javax.swing.JButton();
        lblAni = new javax.swing.JLabel();
        jspAni = new javax.swing.JSpinner();
        chkSumEmp = new javax.swing.JCheckBox();
        chkIngGrp = new javax.swing.JCheckBox();
        panTblDat = new javax.swing.JPanel();
        panTblCta = new javax.swing.JPanel();
        panTblCtaDat = new javax.swing.JPanel();
        spnTblCtaDat = new javax.swing.JScrollPane();
        tblDatCta = new javax.swing.JTable();
        panTblCtaTot = new javax.swing.JPanel();
        spnTblCtaTot = new javax.swing.JScrollPane();
        tblTotCta = new javax.swing.JTable();
        panTblDetCta = new javax.swing.JPanel();
        panTblDetCtaDat = new javax.swing.JPanel();
        spnTblDetCtaDat = new javax.swing.JScrollPane();
        tblDatDetCta = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        butSavDetCta = new javax.swing.JButton();
        panTblDetCtaTot = new javax.swing.JPanel();
        spnTblDetCtaTot = new javax.swing.JScrollPane();
        tblTotDetCta = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        panTooBar = new javax.swing.JPanel();

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

        jPanel1.setLayout(new java.awt.BorderLayout());

        panCab.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(10, 70));
        panCabTit.setLayout(new java.awt.BorderLayout());

        panCabTit.setPreferredSize(new java.awt.Dimension(10, 20));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("jLabel1");
        panCabTit.add(lblTit, java.awt.BorderLayout.CENTER);

        panCab.add(panCabTit, java.awt.BorderLayout.NORTH);

        panCabGrp.setLayout(null);

        lblTitTipDoc.setText("Grupo:");
        panCabGrp.add(lblTitTipDoc);
        lblTitTipDoc.setBounds(4, 10, 60, 15);

        txtCodGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodGrpActionPerformed(evt);
            }
        });
        txtCodGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodGrpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodGrpFocusLost(evt);
            }
        });

        panCabGrp.add(txtCodGrp);
        txtCodGrp.setBounds(64, 6, 57, 20);

        txtDesLarGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarGrpActionPerformed(evt);
            }
        });
        txtDesLarGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarGrpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarGrpFocusLost(evt);
            }
        });

        panCabGrp.add(txtDesLarGrp);
        txtDesLarGrp.setBounds(122, 6, 310, 20);

        butGrp.setText("...");
        butGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGrpActionPerformed(evt);
            }
        });

        panCabGrp.add(butGrp);
        butGrp.setBounds(432, 5, 30, 23);

        lblAni.setText("A\u00f1o:");
        panCabGrp.add(lblAni);
        lblAni.setBounds(520, 8, 40, 15);

        panCabGrp.add(jspAni);
        jspAni.setBounds(570, 6, 80, 20);

        chkSumEmp.setText("Informaci\u00f3n consolidada");
        chkSumEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSumEmpActionPerformed(evt);
            }
        });

        panCabGrp.add(chkSumEmp);
        chkSumEmp.setBounds(60, 30, 270, 16);

        chkIngGrp.setText("Informaci\u00f3n de Grupo");
        chkIngGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIngGrpActionPerformed(evt);
            }
        });

        panCabGrp.add(chkIngGrp);
        chkIngGrp.setBounds(350, 30, 310, 16);

        panCab.add(panCabGrp, java.awt.BorderLayout.CENTER);

        jPanel1.add(panCab, java.awt.BorderLayout.NORTH);

        panTblDat.setLayout(new java.awt.BorderLayout());

        panTblCta.setLayout(new java.awt.BorderLayout());

        panTblCta.setBorder(new javax.swing.border.EtchedBorder());
        panTblCta.setPreferredSize(new java.awt.Dimension(0, 140));
        panTblCtaDat.setLayout(new java.awt.BorderLayout());

        tblDatCta.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTblCtaDat.setViewportView(tblDatCta);

        panTblCtaDat.add(spnTblCtaDat, java.awt.BorderLayout.CENTER);

        panTblCta.add(panTblCtaDat, java.awt.BorderLayout.CENTER);

        panTblCtaTot.setLayout(new java.awt.BorderLayout());

        panTblCtaTot.setPreferredSize(new java.awt.Dimension(10, 35));
        tblTotCta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTblCtaTot.setViewportView(tblTotCta);

        panTblCtaTot.add(spnTblCtaTot, java.awt.BorderLayout.CENTER);

        panTblCta.add(panTblCtaTot, java.awt.BorderLayout.SOUTH);

        panTblDat.add(panTblCta, java.awt.BorderLayout.CENTER);

        panTblDetCta.setLayout(new java.awt.BorderLayout());

        panTblDetCta.setBorder(new javax.swing.border.EtchedBorder());
        panTblDetCta.setPreferredSize(new java.awt.Dimension(0, 150));
        panTblDetCtaDat.setLayout(new java.awt.BorderLayout());

        panTblDetCtaDat.setPreferredSize(new java.awt.Dimension(533, 400));
        tblDatDetCta.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTblDetCtaDat.setViewportView(tblDatDetCta);

        panTblDetCtaDat.add(spnTblDetCtaDat, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(81, 20));
        jPanel3.setLayout(new java.awt.BorderLayout());

        butSavDetCta.setMnemonic('G');
        butSavDetCta.setText("Guardar");
        butSavDetCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSavDetCtaActionPerformed(evt);
            }
        });

        jPanel3.add(butSavDetCta, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3, java.awt.BorderLayout.EAST);

        panTblDetCtaDat.add(jPanel2, java.awt.BorderLayout.SOUTH);

        panTblDetCta.add(panTblDetCtaDat, java.awt.BorderLayout.CENTER);

        panTblDetCtaTot.setLayout(new java.awt.BorderLayout());

        panTblDetCtaTot.setPreferredSize(new java.awt.Dimension(10, 60));
        spnTblDetCtaTot.setPreferredSize(new java.awt.Dimension(452, 402));
        tblTotDetCta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTblDetCtaTot.setViewportView(tblTotDetCta);

        panTblDetCtaTot.add(spnTblDetCtaTot, java.awt.BorderLayout.CENTER);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(10, 26));
        jScrollPane1.setViewportView(txaObs1);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel1.setText("Observaci\u00f3n:   ");
        jPanel4.add(jLabel1, java.awt.BorderLayout.WEST);

        panTblDetCtaTot.add(jPanel4, java.awt.BorderLayout.SOUTH);

        panTblDetCta.add(panTblDetCtaTot, java.awt.BorderLayout.SOUTH);

        panTblDat.add(panTblDetCta, java.awt.BorderLayout.SOUTH);

        jPanel1.add(panTblDat, java.awt.BorderLayout.CENTER);

        panTooBar.setLayout(new java.awt.BorderLayout());

        jPanel1.add(panTooBar, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("tab1", jPanel1);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void chkIngGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIngGrpActionPerformed
        // TODO add your handling code here:
        if(chkIngGrp.isSelected())
            chkSumEmp.setSelected(false);
    }//GEN-LAST:event_chkIngGrpActionPerformed

    private void chkSumEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSumEmpActionPerformed
        // TODO add your handling code here:
        if(chkSumEmp.isSelected())
            chkIngGrp.setSelected(false);
    }//GEN-LAST:event_chkSumEmpActionPerformed

    private void butSavDetCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSavDetCtaActionPerformed
        // TODO add your handling code here:
        if(objTblModDetCta.isDataModelChanged()){
            actualizaCambioDetalle();
            objTblModDetCta.initRowsState();
            mostrarMsgInf("<HTML>La operación guardar detalle se proceso con éxito</HTML>");
        }
        else
            mostrarMsgInf("<HTML>Ud. no ha realizado ningún cambio en el detalle de la cuenta.<BR>Ingrese datos e intente nuevamente</HTML>");
    }//GEN-LAST:event_butSavDetCtaActionPerformed

    private void butGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGrpActionPerformed
        // TODO add your handling code here:
        mostrarVenConGrp(0);
    }//GEN-LAST:event_butGrpActionPerformed

    private void txtDesLarGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarGrpFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarGrp.getText().equalsIgnoreCase(strDesLarGrp))
        {
            if (txtDesLarGrp.getText().equals(""))
            {
                txtCodGrp.setText("");
            }
            else
            {
                mostrarVenConGrp(2);
            }
        }
        else
            txtDesLarGrp.setText(strDesLarGrp);
    }//GEN-LAST:event_txtDesLarGrpFocusLost

    private void txtDesLarGrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarGrpFocusGained
        // TODO add your handling code here:
        strDesLarGrp=txtDesLarGrp.getText();
        txtDesLarGrp.selectAll();
    }//GEN-LAST:event_txtDesLarGrpFocusGained

    private void txtDesLarGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarGrpActionPerformed
        // TODO add your handling code here:
        txtDesLarGrp.transferFocus();
        if(objTooBar.getEstado()!='c')
            cargarDatosCompletosFrm();
    }//GEN-LAST:event_txtDesLarGrpActionPerformed

    private void txtCodGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodGrp.getText().equalsIgnoreCase(strCodGrp))
        {
            if (txtCodGrp.getText().equals(""))
                txtDesLarGrp.setText("");
            else{
                mostrarVenConGrp(1);
            }
        }
        else{
            txtCodGrp.setText(strCodGrp);
        }
    }//GEN-LAST:event_txtCodGrpFocusLost

    private void txtCodGrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpFocusGained
        // TODO add your handling code here:
        strCodGrp=txtCodGrp.getText();
        txtCodGrp.selectAll();
    }//GEN-LAST:event_txtCodGrpFocusGained

    private void txtCodGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodGrpActionPerformed
        // TODO add your handling code here:
        txtCodGrp.transferFocus();
        if(objTooBar.getEstado()!='c')
            cargarDatosCompletosFrm();
    }//GEN-LAST:event_txtCodGrpActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        // TODO add your handling code here:
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
            dispose();
        }        
    }//GEN-LAST:event_exitForm
    

    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }     
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butGrp;
    private javax.swing.JButton butSavDetCta;
    private javax.swing.JCheckBox chkIngGrp;
    private javax.swing.JCheckBox chkSumEmp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jspAni;
    private javax.swing.JLabel lblAni;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblTitTipDoc;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCabGrp;
    private javax.swing.JPanel panCabTit;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panTblCta;
    private javax.swing.JPanel panTblCtaDat;
    private javax.swing.JPanel panTblCtaTot;
    private javax.swing.JPanel panTblDat;
    private javax.swing.JPanel panTblDetCta;
    private javax.swing.JPanel panTblDetCtaDat;
    private javax.swing.JPanel panTblDetCtaTot;
    private javax.swing.JPanel panTooBar;
    private javax.swing.JScrollPane spnTblCtaDat;
    private javax.swing.JScrollPane spnTblCtaTot;
    private javax.swing.JScrollPane spnTblDetCtaDat;
    private javax.swing.JScrollPane spnTblDetCtaTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatCta;
    private javax.swing.JTable tblDatDetCta;
    private javax.swing.JTable tblTotCta;
    private javax.swing.JTable tblTotDetCta;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextField txtCodGrp;
    private javax.swing.JTextField txtDesLarGrp;
    // End of variables declaration//GEN-END:variables
 
    
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            panTooBar.add(objTooBar);
            this.setTitle(objParSis.getNombreMenu() + " v0.1.8b1");
            lblTit.setText(objParSis.getNombreMenu());
            objTooBar.setVisibleConsultar(true);
            objTooBar.setVisibleAceptar(true);
            objTooBar.setVisibleCancelar(true);
            objTooBar.setVisibleModificar(true);
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleEliminar(false);
            txtCodGrp.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarGrp.setBackground(objParSis.getColorCamposObligatorios());
            
            arlDatMesCie=new ArrayList();
//            if(!getMesCie())
//                return false;
            
            if ( ! configuraTablaDatosCtas() )
                blnRes=false;
            configurarVenConGrp();
            arlDatIniFrm=new ArrayList();
            arlDatIniFrm.clear();
            vecDatCta=new Vector();
            vecDatCta.clear();
            
            arlDatDetCtaIniFrm=new ArrayList();
            arlDatDetCtaIniFrm.clear();
            
            if( ! configuraTablaDatosDetalleCtas()  )
                blnRes=false;
            

//            cargarDatosCompletosFrm();
            
            ZafPerUsr objPerUsr = new ZafPerUsr(objParSis);
            if(objPerUsr.isModificarEnabled())
                blnPerUsrMod=true;
            else
                blnPerUsrMod=false;
            
            
            if(objPerUsr.isInsertarEnabled())
                blnPerUsrIns=true;
            else
                blnPerUsrIns=false;
            
            
//            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
//                objTooBar.setVisibleAnular(false);
//                objTooBar.setVisibleEliminar(false);
//                objTooBar.setVisibleInsertar(false);
//                objTooBar.setVisibleImprimir(false);
//                objTooBar.setVisibleVistaPreliminar(false);
//                butSavDetCta.setEnabled(false);
//            }
//            else{
                objTooBar.setVisibleAnular(false);
                objTooBar.setVisibleEliminar(false);
                objTooBar.setVisibleImprimir(false);
                objTooBar.setVisibleVistaPreliminar(false);
//            }
            
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    private boolean configuraTablaDatosCtas(){
        boolean blnRes=true;
            //Configurar JTable: Establecer el modelo.
            vecDatCta=new Vector();    //Almacena los datos           
            vecCabCta=new Vector(31);  //Almacena las cabeceras
            vecCabCta.clear();  
            vecCabCta.add(INT_TBL_DAT_CTA_LIN,"");
            vecCabCta.add(INT_TBL_DAT_CTA_COD_CTA," CÓD.CTA.");
            vecCabCta.add(INT_TBL_DAT_CTA_NUM_CTA,"NÚM.CTA");
            vecCabCta.add(INT_TBL_DAT_CTA_NOM_CTA,"NOMBRE");
            vecCabCta.add(INT_TBL_DAT_CTA_MES_ENE,"ENERO");
            vecCabCta.add(INT_TBL_DAT_CTA_OBS_ENE,"...");
            vecCabCta.add(INT_TBL_DAT_CTA_MES_FEB,"FEBRERO");
            vecCabCta.add(INT_TBL_DAT_CTA_OBS_FEB,"...");
            vecCabCta.add(INT_TBL_DAT_CTA_MES_MAR,"MARZO");
            vecCabCta.add(INT_TBL_DAT_CTA_OBS_MAR,"...");
            vecCabCta.add(INT_TBL_DAT_CTA_MES_ABR,"ABRIL");
            vecCabCta.add(INT_TBL_DAT_CTA_OBS_ABR,"...");
            vecCabCta.add(INT_TBL_DAT_CTA_MES_MAY,"MAYO");
            vecCabCta.add(INT_TBL_DAT_CTA_OBS_MAY,"...");
            vecCabCta.add(INT_TBL_DAT_CTA_MES_JUN,"JUNIO");
            vecCabCta.add(INT_TBL_DAT_CTA_OBS_JUN,"...");
            vecCabCta.add(INT_TBL_DAT_CTA_MES_JUL,"JULIO");
            vecCabCta.add(INT_TBL_DAT_CTA_OBS_JUL,"...");
            vecCabCta.add(INT_TBL_DAT_CTA_MES_AGO,"AGOSTO");
            vecCabCta.add(INT_TBL_DAT_CTA_OBS_AGO,"...");
            vecCabCta.add(INT_TBL_DAT_CTA_MES_SEP,"SEPTIEMBRE");
            vecCabCta.add(INT_TBL_DAT_CTA_OBS_SEP,"...");
            vecCabCta.add(INT_TBL_DAT_CTA_MES_OCT,"OCTUBRE");
            vecCabCta.add(INT_TBL_DAT_CTA_OBS_OCT,"...");
            vecCabCta.add(INT_TBL_DAT_CTA_MES_NOV,"NOVIEMBRE");
            vecCabCta.add(INT_TBL_DAT_CTA_OBS_NOV,"...");
            vecCabCta.add(INT_TBL_DAT_CTA_MES_DIC,"DICIEMBRE");
            vecCabCta.add(INT_TBL_DAT_CTA_OBS_DIC,"...");
            vecCabCta.add(INT_TBL_DAT_CTA_TOT,"TOTALES");
            vecCabCta.add(INT_TBL_DAT_CTA_EXI,"EXISTE");
            vecCabCta.add(INT_TBL_DAT_CTA_NOD_PAD,"NODO PADRE");
            
                        
            objTblModCta=new ZafTblMod();
            objTblModCta.setHeader(vecCabCta);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModCta.setColumnDataType(INT_TBL_DAT_CTA_MES_ENE, objTblModCta.INT_COL_DBL, new Integer(0), null);
            objTblModCta.setColumnDataType(INT_TBL_DAT_CTA_MES_FEB, objTblModCta.INT_COL_DBL, new Integer(0), null);
            objTblModCta.setColumnDataType(INT_TBL_DAT_CTA_MES_MAR, objTblModCta.INT_COL_DBL, new Integer(0), null);
            objTblModCta.setColumnDataType(INT_TBL_DAT_CTA_MES_ABR, objTblModCta.INT_COL_DBL, new Integer(0), null);
            objTblModCta.setColumnDataType(INT_TBL_DAT_CTA_MES_MAY, objTblModCta.INT_COL_DBL, new Integer(0), null);
            objTblModCta.setColumnDataType(INT_TBL_DAT_CTA_MES_JUN, objTblModCta.INT_COL_DBL, new Integer(0), null);
            objTblModCta.setColumnDataType(INT_TBL_DAT_CTA_MES_JUL, objTblModCta.INT_COL_DBL, new Integer(0), null);
            objTblModCta.setColumnDataType(INT_TBL_DAT_CTA_MES_AGO, objTblModCta.INT_COL_DBL, new Integer(0), null);
            objTblModCta.setColumnDataType(INT_TBL_DAT_CTA_MES_SEP, objTblModCta.INT_COL_DBL, new Integer(0), null);
            objTblModCta.setColumnDataType(INT_TBL_DAT_CTA_MES_OCT, objTblModCta.INT_COL_DBL, new Integer(0), null);
            objTblModCta.setColumnDataType(INT_TBL_DAT_CTA_MES_NOV, objTblModCta.INT_COL_DBL, new Integer(0), null);
            objTblModCta.setColumnDataType(INT_TBL_DAT_CTA_MES_DIC, objTblModCta.INT_COL_DBL, new Integer(0), null);
            objTblModCta.setColumnDataType(INT_TBL_DAT_CTA_TOT, objTblModCta.INT_COL_DBL, new Integer(0), null);
            
            
            tblDatCta.setModel(objTblModCta);
            //Configurar JTable: Establecer tipo de selección.
            tblDatCta.setCellSelectionEnabled(true);
            
            tblDatCta.setRowSelectionAllowed(true);
            
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                tblDatCta.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            else
                tblDatCta.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDatCta,INT_TBL_DAT_CTA_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatCta);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatCta.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatCta.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_CTA_LIN).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_CTA_NUM_CTA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_NOM_CTA).setPreferredWidth(120);  
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_ENE).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_FEB).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_MAR).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR).setPreferredWidth(70);            
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_ABR).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY).setPreferredWidth(70);            
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_MAY).setPreferredWidth(5);            
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_JUN).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_JUL).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_AGO).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_SEP).setPreferredWidth(5);            
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_OCT).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_NOV).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_DIC).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_TOT).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CTA_EXI).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CTA_NOD_PAD).setPreferredWidth(20);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatCta.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setResizable(false);
            
//            tcmAux.getColumn(INT_TBL_DAT_CTA_EXI).setWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_EXI).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_EXI).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_EXI).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_EXI).setResizable(false);
            
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDatCta.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAuxCta=new Vector();
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_ENE);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_ENE);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_FEB);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_FEB);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_MAR);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_MAR);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_ABR);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_ABR);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_MAY);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_MAY);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_JUN);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_JUN);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_JUL);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_JUL);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_AGO);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_AGO);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_SEP);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_SEP);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_OCT);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_OCT);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_NOV);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_NOV);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_DIC);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_DIC);
            objTblModCta.setColumnasEditables(vecAuxCta);
            vecAuxCta=null;

            
            //Configurar JTable: Editor de la tabla.
            objTblEdiCta=new ZafTblEdi(tblDatCta);            
                        
            objTblCelRenLblDatCta=new ZafTblCelRenLbl();
            objTblCelRenLblDatCta.setHorizontalAlignment(javax.swing.JLabel.RIGHT);            
            objTblCelRenLblDatCta.setTipoFormato(objTblCelRenLblDatCta.INT_FOR_NUM);
            objTblCelRenLblDatCta.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE).setCellRenderer(objTblCelRenLblDatCta);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB).setCellRenderer(objTblCelRenLblDatCta);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR).setCellRenderer(objTblCelRenLblDatCta);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR).setCellRenderer(objTblCelRenLblDatCta);                        
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY).setCellRenderer(objTblCelRenLblDatCta);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN).setCellRenderer(objTblCelRenLblDatCta);            
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL).setCellRenderer(objTblCelRenLblDatCta);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO).setCellRenderer(objTblCelRenLblDatCta);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP).setCellRenderer(objTblCelRenLblDatCta);                        
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT).setCellRenderer(objTblCelRenLblDatCta);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV).setCellRenderer(objTblCelRenLblDatCta);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC).setCellRenderer(objTblCelRenLblDatCta);
            tcmAux.getColumn(INT_TBL_DAT_CTA_TOT).setCellRenderer(objTblCelRenLblDatCta);
            objTblCelRenLblDatCta=null;
//PARA LA TABLA DE TOTALES                                 
            objTblModCtaTot=new ZafTblMod();
            objTblModCtaTot.setHeader(vecCabCta);                        
            tblTotCta.setModel(objTblModCtaTot);
            //Configurar JTable: Establecer tipo de selección.
            tblTotCta.setRowSelectionAllowed(true);
            tblTotCta.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            objColNum=new ZafColNumerada(tblTotCta,INT_TBL_DAT_CTA_LIN);
            objTblPopMnu=new ZafTblPopMnu(tblTotCta);
            tblTotCta.setAutoResizeMode(tblDatCta.getAutoResizeMode());
            tcmAux=tblTotCta.getColumnModel();        
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblTotCta.setAutoResizeMode(tblDatCta.getAutoResizeMode());
            tcmAux=tblTotCta.getColumnModel();
                        
            tcmAux.getColumn(INT_TBL_DAT_CTA_LIN).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_CTA_NUM_CTA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CTA_NOM_CTA).setPreferredWidth(70);  
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_ENE).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_FEB).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_MAR).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR).setPreferredWidth(70);            
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_ABR).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY).setPreferredWidth(70);            
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_MAY).setPreferredWidth(5);            
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_JUN).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_JUL).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_AGO).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_SEP).setPreferredWidth(5);            
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_OCT).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_NOV).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_OBS_DIC).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CTA_TOT).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CTA_EXI).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CTA_NOD_PAD).setPreferredWidth(20);
            
            
            
            //Configurar JTable: Ocultar columnas del sistema.
//            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setResizable(false);
//
//            tcmAux.getColumn(INT_TBL_DAT_CTA_EXI).setWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_EXI).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_EXI).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_EXI).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_EXI).setResizable(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblTotCta);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblTotCta=new ZafTblCelRenLbl();
            objTblCelRenLblTotCta.setHorizontalAlignment(javax.swing.JLabel.RIGHT);            
            objTblCelRenLblTotCta.setTipoFormato(objTblCelRenLblTotCta.INT_FOR_NUM);
            objTblCelRenLblTotCta.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE).setCellRenderer(objTblCelRenLblTotCta);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB).setCellRenderer(objTblCelRenLblTotCta);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR).setCellRenderer(objTblCelRenLblTotCta);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR).setCellRenderer(objTblCelRenLblTotCta);                        
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY).setCellRenderer(objTblCelRenLblTotCta);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN).setCellRenderer(objTblCelRenLblTotCta);            
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL).setCellRenderer(objTblCelRenLblTotCta);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO).setCellRenderer(objTblCelRenLblTotCta);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP).setCellRenderer(objTblCelRenLblTotCta);                        
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT).setCellRenderer(objTblCelRenLblTotCta);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV).setCellRenderer(objTblCelRenLblTotCta);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC).setCellRenderer(objTblCelRenLblTotCta);
            tcmAux.getColumn(INT_TBL_DAT_CTA_TOT).setCellRenderer(objTblCelRenLblTotCta);
            objTblCelRenLblTotCta=null;
            
            //Configurar JTable: Igualar el ancho de las columnas del JTable de totales con el JTable de totales.
            for (int j=0; j<tblDatCta.getColumnCount(); j++){
                tcmAux.getColumn(j).setWidth(tblDatCta.getColumnModel().getColumn(j).getWidth());
                tcmAux.getColumn(j).setMaxWidth(tblDatCta.getColumnModel().getColumn(j).getMaxWidth());
                tcmAux.getColumn(j).setMinWidth(tblDatCta.getColumnModel().getColumn(j).getMinWidth());
                tcmAux.getColumn(j).setPreferredWidth(tblDatCta.getColumnModel().getColumn(j).getPreferredWidth());
                tcmAux.getColumn(j).setResizable(tblDatCta.getColumnModel().getColumn(j).getResizable());
            }
            tcmAux=null;  
//////FIN TOTALES///                

            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnTblCtaDat.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            tblTotCta.setTableHeader(null);                        
  
            //Adicionar el listener que controla el redimensionamiento de las columnas.
            ZafTblColModLis objTblColModLisCta=new ZafTblColModLis();
            tblDatCta.getColumnModel().addColumnModelListener(objTblColModLisCta);
            
            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
            barDatCta=spnTblCtaDat.getHorizontalScrollBar();
            barDatTot=spnTblCtaTot.getHorizontalScrollBar();
            
            //PARA DESPLAZAMIENTOS DE CELDAS
            barDatCta.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {                    
                    barDatTot.setValue(evt.getValue());
                }
            });
            barDatTot.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barDatCta.setValue(evt.getValue());
                }
            });
            tcmAux=null;

            
            //para hacer q al dar doble click se sombree el contenido de la celda
            objTblCelEdiTxtCtaEne=new ZafTblCelEdiTxt(tblDatCta);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_MES_ENE).setCellEditor(objTblCelEdiTxtCtaEne);
            objTblCelEdiTxtCtaEne.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtCtaEne.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_ENE)){
                            objTblCelEdiTxtCtaEne.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else{
                            if(flag!=0)
                                objTblCelEdiTxtCtaEne.setCancelarEdicion(true);
                            else
                                objTblCelEdiTxtCtaEne.setCancelarEdicion(false);
                        }                        
                    }
                }
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatCta.getSelectedRow();
		    int intColumSel=tblDatCta.getSelectedColumn();
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        if(chkIngGrp.isSelected()){
                            for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                                calcula(i, intColumSel);
                        }
                    }
                    else{
                        for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                            calcula(i, intColumSel);
                    }
                    calculaColumnaTotales(intFilaSel);
                    calculaFilaTotales(intColumSel);
                    calculaTblTotColumnaTotales();
                    

                    
                }
                });

            objTblCelEdiTxtCtaFeb=new ZafTblCelEdiTxt(tblDatCta);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_MES_FEB).setCellEditor(objTblCelEdiTxtCtaFeb);
            objTblCelEdiTxtCtaFeb.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtCtaFeb.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_FEB)){
                            objTblCelEdiTxtCtaFeb.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else{
                            if(flag!=0)
                                objTblCelEdiTxtCtaFeb.setCancelarEdicion(true);
                            else
                                objTblCelEdiTxtCtaFeb.setCancelarEdicion(false);
                        }
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatCta.getSelectedRow();
		    int intColumSel=tblDatCta.getSelectedColumn();
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        if(chkIngGrp.isSelected()){
                            for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                                calcula(i, intColumSel);
                        }
                    }
                    else{
                        for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                            calcula(i, intColumSel);
                    }
                    calculaColumnaTotales(intFilaSel);
                    calculaFilaTotales(intColumSel);
                    calculaTblTotColumnaTotales();
                }
                });
                
            objTblCelEdiTxtCtaMar=new ZafTblCelEdiTxt(tblDatCta);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_MES_MAR).setCellEditor(objTblCelEdiTxtCtaMar);
            objTblCelEdiTxtCtaMar.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtCtaMar.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_MAR)){
                            objTblCelEdiTxtCtaMar.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else{
                            if(flag!=0)
                                objTblCelEdiTxtCtaMar.setCancelarEdicion(true);
                            else
                                objTblCelEdiTxtCtaMar.setCancelarEdicion(false);
                        }                        
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatCta.getSelectedRow();
		    int intColumSel=tblDatCta.getSelectedColumn();
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        if(chkIngGrp.isSelected()){
                            for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                                calcula(i, intColumSel);
                        }
                    }
                    else{
                        for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                            calcula(i, intColumSel);
                    }
                    calculaColumnaTotales(intFilaSel);
                    calculaFilaTotales(intColumSel);
                    calculaTblTotColumnaTotales();
                }
                });
                
            objTblCelEdiTxtCtaAbr=new ZafTblCelEdiTxt(tblDatCta);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_MES_ABR).setCellEditor(objTblCelEdiTxtCtaAbr);
            objTblCelEdiTxtCtaAbr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtCtaAbr.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_ABR)){
                            objTblCelEdiTxtCtaAbr.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else{
                            if(flag!=0)
                                objTblCelEdiTxtCtaAbr.setCancelarEdicion(true);
                            else
                                objTblCelEdiTxtCtaAbr.setCancelarEdicion(false);
                        }
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatCta.getSelectedRow();
		    int intColumSel=tblDatCta.getSelectedColumn();
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        if(chkIngGrp.isSelected()){
                            for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                                calcula(i, intColumSel);
                        }
                    }
                    else{
                        for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                            calcula(i, intColumSel);
                    }
                    calculaColumnaTotales(intFilaSel);
                    calculaFilaTotales(intColumSel);
                    calculaTblTotColumnaTotales();
                }
                });                
                
            objTblCelEdiTxtCtaMay=new ZafTblCelEdiTxt(tblDatCta);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_MES_MAY).setCellEditor(objTblCelEdiTxtCtaMay);
            objTblCelEdiTxtCtaMay.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtCtaMay.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_MAY)){
                            objTblCelEdiTxtCtaMay.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else{
                            if(flag!=0)
                                objTblCelEdiTxtCtaMay.setCancelarEdicion(true);
                            else
                                objTblCelEdiTxtCtaMay.setCancelarEdicion(false);
                        }                        
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatCta.getSelectedRow();
		    int intColumSel=tblDatCta.getSelectedColumn();
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        if(chkIngGrp.isSelected()){
                            for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                                calcula(i, intColumSel);
                        }
                    }
                    else{
                        for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                            calcula(i, intColumSel);
                    }
                    calculaColumnaTotales(intFilaSel);
                    calculaFilaTotales(intColumSel);
                    calculaTblTotColumnaTotales();
                }
                });
                
            objTblCelEdiTxtCtaJun=new ZafTblCelEdiTxt(tblDatCta);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_MES_JUN).setCellEditor(objTblCelEdiTxtCtaJun);
            objTblCelEdiTxtCtaJun.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtCtaJun.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_JUN)){
                            objTblCelEdiTxtCtaJun.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else{
                            if(flag!=0)
                                objTblCelEdiTxtCtaJun.setCancelarEdicion(true);
                            else
                                objTblCelEdiTxtCtaJun.setCancelarEdicion(false);
                        }                        
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatCta.getSelectedRow();
		    int intColumSel=tblDatCta.getSelectedColumn();
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        if(chkIngGrp.isSelected()){
                            for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                                calcula(i, intColumSel);
                        }
                    }
                    else{
                        for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                            calcula(i, intColumSel);
                    }
                    calculaColumnaTotales(intFilaSel);
                    calculaFilaTotales(intColumSel);
                    calculaTblTotColumnaTotales();
                }
                });
                
            objTblCelEdiTxtCtaJul=new ZafTblCelEdiTxt(tblDatCta);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_MES_JUL).setCellEditor(objTblCelEdiTxtCtaJul);
            objTblCelEdiTxtCtaJul.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtCtaJul.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_JUL)){
                            objTblCelEdiTxtCtaJul.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else{
                            if(flag!=0)
                                objTblCelEdiTxtCtaJul.setCancelarEdicion(true);
                            else
                                objTblCelEdiTxtCtaJul.setCancelarEdicion(false);
                        }
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatCta.getSelectedRow();
		    int intColumSel=tblDatCta.getSelectedColumn();
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        if(chkIngGrp.isSelected()){
                            for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                                calcula(i, intColumSel);
                        }
                    }
                    else{
                        for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                            calcula(i, intColumSel);
                    }
                    calculaColumnaTotales(intFilaSel);
                    calculaFilaTotales(intColumSel);
                    calculaTblTotColumnaTotales();
                }
                });
                
            objTblCelEdiTxtCtaAgo=new ZafTblCelEdiTxt(tblDatCta);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_MES_AGO).setCellEditor(objTblCelEdiTxtCtaAgo);
            objTblCelEdiTxtCtaAgo.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtCtaAgo.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_AGO)){
                            objTblCelEdiTxtCtaAgo.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else{
                            if(flag!=0)
                                objTblCelEdiTxtCtaAgo.setCancelarEdicion(true);
                            else
                                objTblCelEdiTxtCtaAgo.setCancelarEdicion(false);
                        }
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatCta.getSelectedRow();
		    int intColumSel=tblDatCta.getSelectedColumn();
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        if(chkIngGrp.isSelected()){
                            for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                                calcula(i, intColumSel);
                        }
                    }
                    else{
                        for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                            calcula(i, intColumSel);
                    }
                    calculaColumnaTotales(intFilaSel);
                    calculaFilaTotales(intColumSel);
                    calculaTblTotColumnaTotales();
                }
                });
                
            objTblCelEdiTxtCtaSep=new ZafTblCelEdiTxt(tblDatCta);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_MES_SEP).setCellEditor(objTblCelEdiTxtCtaSep);
            objTblCelEdiTxtCtaSep.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtCtaSep.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_SEP)){
                            objTblCelEdiTxtCtaSep.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else{
                            if(flag!=0)
                                objTblCelEdiTxtCtaSep.setCancelarEdicion(true);
                            else
                                objTblCelEdiTxtCtaSep.setCancelarEdicion(false);
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatCta.getSelectedRow();
		    int intColumSel=tblDatCta.getSelectedColumn();
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        if(chkIngGrp.isSelected()){
                            for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                                calcula(i, intColumSel);
                        }
                    }
                    else{
                        for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                            calcula(i, intColumSel);
                    }
                    calculaColumnaTotales(intFilaSel);
                    calculaFilaTotales(intColumSel);
                    calculaTblTotColumnaTotales();
                }
                });
                
            objTblCelEdiTxtCtaOct=new ZafTblCelEdiTxt(tblDatCta);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_MES_OCT).setCellEditor(objTblCelEdiTxtCtaOct);
            objTblCelEdiTxtCtaOct.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtCtaOct.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_OCT)){
                            objTblCelEdiTxtCtaOct.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else{
                            if(flag!=0)
                                objTblCelEdiTxtCtaOct.setCancelarEdicion(true);
                            else
                                objTblCelEdiTxtCtaOct.setCancelarEdicion(false);
                        }                        
                    }

                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatCta.getSelectedRow();
		    int intColumSel=tblDatCta.getSelectedColumn();
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        if(chkIngGrp.isSelected()){
                            for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                                calcula(i, intColumSel);
                        }
                    }
                    else{
                        for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                            calcula(i, intColumSel);
                    }
                    calculaColumnaTotales(intFilaSel);
                    calculaFilaTotales(intColumSel);
                    calculaTblTotColumnaTotales();
                }
                });
               
            objTblCelEdiTxtCtaNov=new ZafTblCelEdiTxt(tblDatCta);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_MES_NOV).setCellEditor(objTblCelEdiTxtCtaNov);
            objTblCelEdiTxtCtaNov.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtCtaNov.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_NOV)){
                            objTblCelEdiTxtCtaNov.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else{
                            if(flag!=0)
                                objTblCelEdiTxtCtaNov.setCancelarEdicion(true);
                            else
                                objTblCelEdiTxtCtaNov.setCancelarEdicion(false);
                        }
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatCta.getSelectedRow();
		    int intColumSel=tblDatCta.getSelectedColumn();
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        if(chkIngGrp.isSelected()){
                            for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                                calcula(i, intColumSel);
                        }
                    }
                    else{
                        for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                            calcula(i, intColumSel);
                    }
                    calculaColumnaTotales(intFilaSel);
                    calculaFilaTotales(intColumSel);
                    calculaTblTotColumnaTotales();
                }
                });
                
            objTblCelEdiTxtCtaDic=new ZafTblCelEdiTxt(tblDatCta);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_MES_DIC).setCellEditor(objTblCelEdiTxtCtaDic);
            objTblCelEdiTxtCtaDic.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtCtaDic.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_DIC)){
                            objTblCelEdiTxtCtaDic.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else{
                            if(flag!=0)
                                objTblCelEdiTxtCtaDic.setCancelarEdicion(true);
                            else
                                objTblCelEdiTxtCtaDic.setCancelarEdicion(false);
                        }                        
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatCta.getSelectedRow();
		    int intColumSel=tblDatCta.getSelectedColumn();
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        if(chkIngGrp.isSelected()){
                            for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                                calcula(i, intColumSel);
                        }
                    }
                    else{
                        for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                            calcula(i, intColumSel);
                    }
                    calculaColumnaTotales(intFilaSel);
                    calculaFilaTotales(intColumSel);
                    calculaTblTotColumnaTotales();
                }
                });
                
                
            //Configurar JTable: Establecer el ListSelectionListener.
            javax.swing.ListSelectionModel lsm=tblDatCta.getSelectionModel();
            lsm.addListSelectionListener(new ZafLisSelLis());
            
            
            objTblCelRenButEne=new ZafTblCelRenBut();
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_ENE).setCellRenderer(objTblCelRenButEne);
            objCon40_01Ene=new ZafCon40_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButEne= new ZafTblCelEdiButDlg(objCon40_01Ene);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_ENE).setCellEditor(objTblCelEdiButEne);
            objTblCelEdiButEne.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiButEne.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_ENE)){
                            objTblCelEdiButEne.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else
                            objCon40_01Ene.colokObs(""+objTblModCta.getValueAt(tblDatCta.getSelectedRow(), tblDatCta.getSelectedColumn()));
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatCta.getSelectedRow();
                    int intCol=tblDatCta.getSelectedColumn();
                    objTblModCta.setValueAt(objCon40_01Ene.retornaTexto(), intFil, intCol) ;
                }
            });
            
            objTblCelRenButFeb=new ZafTblCelRenBut();
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_FEB).setCellRenderer(objTblCelRenButFeb);
            objCon40_01Feb=new ZafCon40_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButFeb= new ZafTblCelEdiButDlg(objCon40_01Feb);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_FEB).setCellEditor(objTblCelEdiButFeb);
            objTblCelEdiButFeb.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiButFeb.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_FEB)){
                            objTblCelEdiButFeb.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else
                            objCon40_01Feb.colokObs(""+objTblModCta.getValueAt(tblDatCta.getSelectedRow(), tblDatCta.getSelectedColumn()));                        
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatCta.getSelectedRow();
                    int intCol=tblDatCta.getSelectedColumn();
                    objTblModCta.setValueAt(objCon40_01Feb.retornaTexto(), intFil, intCol) ;
                }
            });

            objTblCelRenButMar=new ZafTblCelRenBut();
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_MAR).setCellRenderer(objTblCelRenButMar);
            objCon40_01Mar=new ZafCon40_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButMar= new ZafTblCelEdiButDlg(objCon40_01Mar);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_MAR).setCellEditor(objTblCelEdiButMar);
            objTblCelEdiButMar.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiButMar.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_MAR)){
                            objTblCelEdiButMar.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else
                            objCon40_01Mar.colokObs(""+objTblModCta.getValueAt(tblDatCta.getSelectedRow(), tblDatCta.getSelectedColumn()));                        
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatCta.getSelectedRow();
                    int intCol=tblDatCta.getSelectedColumn();
                    objTblModCta.setValueAt(objCon40_01Mar.retornaTexto(), intFil, intCol) ;
                }
            });
            
            objTblCelRenButAbr=new ZafTblCelRenBut();
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_ABR).setCellRenderer(objTblCelRenButAbr);
            objCon40_01Abr=new ZafCon40_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButAbr= new ZafTblCelEdiButDlg(objCon40_01Abr);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_ABR).setCellEditor(objTblCelEdiButAbr);
            objTblCelEdiButAbr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiButAbr.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_ABR)){
                            objTblCelEdiButAbr.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else
                            objCon40_01Abr.colokObs(""+objTblModCta.getValueAt(tblDatCta.getSelectedRow(), tblDatCta.getSelectedColumn()));                        
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatCta.getSelectedRow();
                    int intCol=tblDatCta.getSelectedColumn();
                    objTblModCta.setValueAt(objCon40_01Abr.retornaTexto(), intFil, intCol) ;
                }
            });            
            
            objTblCelRenButMay=new ZafTblCelRenBut();
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_MAY).setCellRenderer(objTblCelRenButMay);
            objCon40_01May=new ZafCon40_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButMay= new ZafTblCelEdiButDlg(objCon40_01May);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_MAY).setCellEditor(objTblCelEdiButMay);
            objTblCelEdiButMay.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiButMay.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_MAY)){
                            objTblCelEdiButMay.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else
                            objCon40_01May.colokObs(""+objTblModCta.getValueAt(tblDatCta.getSelectedRow(), tblDatCta.getSelectedColumn()));                        
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatCta.getSelectedRow();
                    int intCol=tblDatCta.getSelectedColumn();
                    objTblModCta.setValueAt(objCon40_01May.retornaTexto(), intFil, intCol) ;
                }
            });

            objTblCelRenButJun=new ZafTblCelRenBut();
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_JUN).setCellRenderer(objTblCelRenButJun);
            objCon40_01Jun=new ZafCon40_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButJun= new ZafTblCelEdiButDlg(objCon40_01Jun);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_JUN).setCellEditor(objTblCelEdiButJun);
            objTblCelEdiButJun.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiButJun.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_JUN)){
                            objTblCelEdiButJun.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else
                            objCon40_01Jun.colokObs(""+objTblModCta.getValueAt(tblDatCta.getSelectedRow(), tblDatCta.getSelectedColumn()));                        
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatCta.getSelectedRow();
                    int intCol=tblDatCta.getSelectedColumn();
                    objTblModCta.setValueAt(objCon40_01Jun.retornaTexto(), intFil, intCol) ;
                }
            });            
            
            objTblCelRenButJul=new ZafTblCelRenBut();
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_JUL).setCellRenderer(objTblCelRenButJul);
            objCon40_01Jul=new ZafCon40_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButJul= new ZafTblCelEdiButDlg(objCon40_01Jul);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_JUL).setCellEditor(objTblCelEdiButJul);
            objTblCelEdiButJul.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiButJul.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_JUL)){
                            objTblCelEdiButJul.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else
                            objCon40_01Jul.colokObs(""+objTblModCta.getValueAt(tblDatCta.getSelectedRow(), tblDatCta.getSelectedColumn()));
                    }

                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatCta.getSelectedRow();
                    int intCol=tblDatCta.getSelectedColumn();
                    objTblModCta.setValueAt(objCon40_01Jul.retornaTexto(), intFil, intCol) ;
                }
            });
            
            objTblCelRenButAgo=new ZafTblCelRenBut();
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_AGO).setCellRenderer(objTblCelRenButAgo);
            objCon40_01Ago=new ZafCon40_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButAgo= new ZafTblCelEdiButDlg(objCon40_01Ago);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_AGO).setCellEditor(objTblCelEdiButAgo);
            objTblCelEdiButAgo.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiButAgo.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_AGO)){
                            objTblCelEdiButAgo.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else
                            objCon40_01Ago.colokObs(""+objTblModCta.getValueAt(tblDatCta.getSelectedRow(), tblDatCta.getSelectedColumn()));
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatCta.getSelectedRow();
                    int intCol=tblDatCta.getSelectedColumn();
                    objTblModCta.setValueAt(objCon40_01Ago.retornaTexto(), intFil, intCol) ;
                }
            });
            
            objTblCelRenButSep=new ZafTblCelRenBut();
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_SEP).setCellRenderer(objTblCelRenButSep);
            objCon40_01Sep=new ZafCon40_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButSep= new ZafTblCelEdiButDlg(objCon40_01Sep);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_SEP).setCellEditor(objTblCelEdiButSep);
            objTblCelEdiButSep.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiButSep.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_SEP)){
                            objTblCelEdiButSep.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else
                            objCon40_01Sep.colokObs(""+objTblModCta.getValueAt(tblDatCta.getSelectedRow(), tblDatCta.getSelectedColumn()));
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatCta.getSelectedRow();
                    int intCol=tblDatCta.getSelectedColumn();
                    objTblModCta.setValueAt(objCon40_01Sep.retornaTexto(), intFil, intCol) ;
                }
            });
            
            objTblCelRenButOct=new ZafTblCelRenBut();
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_OCT).setCellRenderer(objTblCelRenButOct);
            objCon40_01Oct=new ZafCon40_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButOct= new ZafTblCelEdiButDlg(objCon40_01Oct);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_OCT).setCellEditor(objTblCelEdiButOct);
            objTblCelEdiButOct.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiButOct.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_OCT)){
                            objTblCelEdiButOct.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else
                            objCon40_01Oct.colokObs(""+objTblModCta.getValueAt(tblDatCta.getSelectedRow(), tblDatCta.getSelectedColumn()));
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatCta.getSelectedRow();
                    int intCol=tblDatCta.getSelectedColumn();
                    objTblModCta.setValueAt(objCon40_01Oct.retornaTexto(), intFil, intCol) ;
                }
            });
            
            objTblCelRenButNov=new ZafTblCelRenBut();
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_NOV).setCellRenderer(objTblCelRenButNov);
            objCon40_01Nov=new ZafCon40_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButNov= new ZafTblCelEdiButDlg(objCon40_01Nov);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_NOV).setCellEditor(objTblCelEdiButNov);
            objTblCelEdiButNov.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiButNov.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_NOV)){
                            objTblCelEdiButNov.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else
                            objCon40_01Nov.colokObs(""+objTblModCta.getValueAt(tblDatCta.getSelectedRow(), tblDatCta.getSelectedColumn()));                        
                    }

                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatCta.getSelectedRow();
                    int intCol=tblDatCta.getSelectedColumn();
                    objTblModCta.setValueAt(objCon40_01Nov.retornaTexto(), intFil, intCol) ;
                }
            });
            
            objTblCelRenButDic=new ZafTblCelRenBut();
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_DIC).setCellRenderer(objTblCelRenButDic);
            objCon40_01Dic=new ZafCon40_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButDic= new ZafTblCelEdiButDlg(objCon40_01Dic);
            tblDatCta.getColumnModel().getColumn(INT_TBL_DAT_CTA_OBS_DIC).setCellEditor(objTblCelEdiButDic);
            objTblCelEdiButDic.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(tieneCierreAnual()){
                        objTblCelEdiButDic.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCie(INT_TBL_DAT_CTA_MES_DIC)){
                            objTblCelEdiButDic.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                        else
                            objCon40_01Dic.colokObs(""+objTblModCta.getValueAt(tblDatCta.getSelectedRow(), tblDatCta.getSelectedColumn()));                        
                    }

                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatCta.getSelectedRow();
                    int intCol=tblDatCta.getSelectedColumn();
                    objTblModCta.setValueAt(objCon40_01Dic.retornaTexto(), intFil, intCol) ;
                }
            });
            
            objTblModCtaTot.insertRow();
            
        return blnRes;
    }
    
    private boolean configuraTablaDatosDetalleCtas(){
        boolean blnRes=true;
            //Configurar JTable: Establecer el modelo.
            vecDatDetCta=new Vector();    //Almacena los datos           
            vecCabDetCta=new Vector(15);  //Almacena las cabeceras
            vecCabDetCta.clear();  
            vecCabDetCta.add(INT_TBL_DAT_DET_LIN,"");
            vecCabDetCta.add(INT_TBL_DAT_DET_DET_CTA,"DESCRIPCIÓN");
            vecCabDetCta.add(INT_TBL_DAT_DET_MES_ENE,"ENERO");
            vecCabDetCta.add(INT_TBL_DAT_DET_MES_FEB,"FEBRERO");
            vecCabDetCta.add(INT_TBL_DAT_DET_MES_MAR,"MARZO");
            vecCabDetCta.add(INT_TBL_DAT_DET_MES_ABR,"ABRIL");
            vecCabDetCta.add(INT_TBL_DAT_DET_MES_MAY,"MAYO");
            vecCabDetCta.add(INT_TBL_DAT_DET_MES_JUN,"JUNIO");
            vecCabDetCta.add(INT_TBL_DAT_DET_MES_JUL,"JULIO");
            vecCabDetCta.add(INT_TBL_DAT_DET_MES_AGO,"AGOSTO");
            vecCabDetCta.add(INT_TBL_DAT_DET_MES_SEP,"SEPTIEMBRE");
            vecCabDetCta.add(INT_TBL_DAT_DET_MES_OCT,"OCTUBRE");
            vecCabDetCta.add(INT_TBL_DAT_DET_MES_NOV,"NOVIEMBRE");
            vecCabDetCta.add(INT_TBL_DAT_DET_MES_DIC,"DICIEMBRE");
            vecCabDetCta.add(INT_TBL_DAT_DET_TOT,"TOTALES");

            objTblModDetCta=new ZafTblMod();
            objTblModDetCta.setHeader(vecCabDetCta);


            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModDetCta.setColumnDataType(INT_TBL_DAT_DET_MES_ENE, objTblModDetCta.INT_COL_DBL, new Integer(0), null);
            objTblModDetCta.setColumnDataType(INT_TBL_DAT_DET_MES_FEB, objTblModDetCta.INT_COL_DBL, new Integer(0), null);
            objTblModDetCta.setColumnDataType(INT_TBL_DAT_DET_MES_MAR, objTblModDetCta.INT_COL_DBL, new Integer(0), null);
            objTblModDetCta.setColumnDataType(INT_TBL_DAT_DET_MES_ABR, objTblModDetCta.INT_COL_DBL, new Integer(0), null);
            objTblModDetCta.setColumnDataType(INT_TBL_DAT_DET_MES_MAY, objTblModDetCta.INT_COL_DBL, new Integer(0), null);
            objTblModDetCta.setColumnDataType(INT_TBL_DAT_DET_MES_JUN, objTblModDetCta.INT_COL_DBL, new Integer(0), null);
            objTblModDetCta.setColumnDataType(INT_TBL_DAT_DET_MES_JUL, objTblModDetCta.INT_COL_DBL, new Integer(0), null);
            objTblModDetCta.setColumnDataType(INT_TBL_DAT_DET_MES_AGO, objTblModDetCta.INT_COL_DBL, new Integer(0), null);
            objTblModDetCta.setColumnDataType(INT_TBL_DAT_DET_MES_SEP, objTblModDetCta.INT_COL_DBL, new Integer(0), null);
            objTblModDetCta.setColumnDataType(INT_TBL_DAT_DET_MES_OCT, objTblModDetCta.INT_COL_DBL, new Integer(0), null);
            objTblModDetCta.setColumnDataType(INT_TBL_DAT_DET_MES_NOV, objTblModDetCta.INT_COL_DBL, new Integer(0), null);
            objTblModDetCta.setColumnDataType(INT_TBL_DAT_DET_MES_DIC, objTblModDetCta.INT_COL_DBL, new Integer(0), null);
            objTblModDetCta.setColumnDataType(INT_TBL_DAT_DET_TOT, objTblModDetCta.INT_COL_DBL, new Integer(0), null);
            
            tblDatDetCta.setModel(objTblModDetCta);
            //Configurar JTable: Establecer tipo de selección.
            tblDatDetCta.setCellSelectionEnabled(true);
            tblDatDetCta.setRowSelectionAllowed(true);
            
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                tblDatDetCta.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            else
                tblDatDetCta.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDatDetCta,INT_TBL_DAT_DET_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatDetCta);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatDetCta.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatDetCta.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_DET_LIN).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_DET_DET_CTA).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_ENE).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_FEB).setPreferredWidth(70);  
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_MAR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_ABR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_MAY).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_JUN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_JUL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_AGO).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_SEP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_OCT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_NOV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_DIC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_TOT).setPreferredWidth(100);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatDetCta.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
//            objMouMotAda=new ZafMouMotAda();
//            tblDatDetCta.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Establecer columnas editables.
            vecAuxDetCta=new Vector();
            vecAuxDetCta.add("" + INT_TBL_DAT_DET_MES_ENE);
            vecAuxDetCta.add("" + INT_TBL_DAT_DET_MES_FEB);
            vecAuxDetCta.add("" + INT_TBL_DAT_DET_MES_MAR);
            vecAuxDetCta.add("" + INT_TBL_DAT_DET_MES_ABR);
            vecAuxDetCta.add("" + INT_TBL_DAT_DET_MES_MAY);
            vecAuxDetCta.add("" + INT_TBL_DAT_DET_MES_JUN);
            vecAuxDetCta.add("" + INT_TBL_DAT_DET_MES_JUL);
            vecAuxDetCta.add("" + INT_TBL_DAT_DET_MES_AGO);
            vecAuxDetCta.add("" + INT_TBL_DAT_DET_MES_SEP);
            vecAuxDetCta.add("" + INT_TBL_DAT_DET_MES_OCT);
            vecAuxDetCta.add("" + INT_TBL_DAT_DET_MES_NOV);
            vecAuxDetCta.add("" + INT_TBL_DAT_DET_MES_DIC);
            vecAuxDetCta.add("" + INT_TBL_DAT_DET_TOT);
            objTblModDetCta.setColumnasEditables(vecAuxDetCta);
            vecAuxDetCta=null;
            
            //Configurar JTable: Editor de la tabla.
            objTblEdiDetCta=new ZafTblEdi(tblDatDetCta);
            
            objTblCelRenLblDatDetCta=new ZafTblCelRenLbl();
            objTblCelRenLblDatDetCta.setHorizontalAlignment(javax.swing.JLabel.RIGHT);            
            objTblCelRenLblDatDetCta.setTipoFormato(objTblCelRenLblDatDetCta.INT_FOR_NUM);
            objTblCelRenLblDatDetCta.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_ENE).setCellRenderer(objTblCelRenLblDatDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_FEB).setCellRenderer(objTblCelRenLblDatDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_MAR).setCellRenderer(objTblCelRenLblDatDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_ABR).setCellRenderer(objTblCelRenLblDatDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_MAY).setCellRenderer(objTblCelRenLblDatDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_JUN).setCellRenderer(objTblCelRenLblDatDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_JUL).setCellRenderer(objTblCelRenLblDatDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_AGO).setCellRenderer(objTblCelRenLblDatDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_SEP).setCellRenderer(objTblCelRenLblDatDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_OCT).setCellRenderer(objTblCelRenLblDatDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_NOV).setCellRenderer(objTblCelRenLblDatDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_DIC).setCellRenderer(objTblCelRenLblDatDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_TOT).setCellRenderer(objTblCelRenLblDatDetCta);
            objTblCelRenLblDatDetCta=null;
                        
//PARA LA TABLA DE TOTALES                                 
            objTblModDetCtaTot=new ZafTblMod();
            objTblModDetCtaTot.setHeader(vecCabDetCta);                        
            tblTotDetCta.setModel(objTblModDetCtaTot);
            //Configurar JTable: Establecer tipo de selección.
            tblTotDetCta.setRowSelectionAllowed(true);
            tblTotDetCta.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            objColNum=new ZafColNumerada(tblTotDetCta,INT_TBL_DAT_DET_LIN);
            objTblPopMnu=new ZafTblPopMnu(tblTotDetCta);
            tblTotDetCta.setAutoResizeMode(tblDatDetCta.getAutoResizeMode());
            tcmAux=tblTotDetCta.getColumnModel();

            tcmAux.getColumn(INT_TBL_DAT_DET_LIN).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_DET_DET_CTA).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_ENE).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_FEB).setPreferredWidth(70);  
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_MAR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_ABR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_MAY).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_JUN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_JUL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_AGO).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_SEP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_OCT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_NOV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_DIC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_TOT).setPreferredWidth(100);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblTotDetCta);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblTotDetCta=new ZafTblCelRenLbl();
            objTblCelRenLblTotDetCta.setHorizontalAlignment(javax.swing.JLabel.RIGHT);            
            objTblCelRenLblTotDetCta.setTipoFormato(objTblCelRenLblTotDetCta.INT_FOR_NUM);
            objTblCelRenLblTotDetCta.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_ENE).setCellRenderer(objTblCelRenLblTotDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_FEB).setCellRenderer(objTblCelRenLblTotDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_MAR).setCellRenderer(objTblCelRenLblTotDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_ABR).setCellRenderer(objTblCelRenLblTotDetCta);                        
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_MAY).setCellRenderer(objTblCelRenLblTotDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_JUN).setCellRenderer(objTblCelRenLblTotDetCta);            
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_JUL).setCellRenderer(objTblCelRenLblTotDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_AGO).setCellRenderer(objTblCelRenLblTotDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_SEP).setCellRenderer(objTblCelRenLblTotDetCta);                        
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_OCT).setCellRenderer(objTblCelRenLblTotDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_NOV).setCellRenderer(objTblCelRenLblTotDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_MES_DIC).setCellRenderer(objTblCelRenLblTotDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_TOT).setCellRenderer(objTblCelRenLblTotDetCta);
            tcmAux.getColumn(INT_TBL_DAT_DET_TOT).setCellRenderer(objTblCelRenLblTotDetCta);
            objTblCelRenLblTotDetCta=null;
            
            //Configurar JTable: Igualar el ancho de las columnas del JTable de totales con el JTable de totales.
            for (int j=0; j<tblDatDetCta.getColumnCount(); j++){
                tcmAux.getColumn(j).setWidth(tblDatDetCta.getColumnModel().getColumn(j).getWidth());
                tcmAux.getColumn(j).setMaxWidth(tblDatDetCta.getColumnModel().getColumn(j).getMaxWidth());
                tcmAux.getColumn(j).setMinWidth(tblDatDetCta.getColumnModel().getColumn(j).getMinWidth());
                tcmAux.getColumn(j).setPreferredWidth(tblDatDetCta.getColumnModel().getColumn(j).getPreferredWidth());
                tcmAux.getColumn(j).setResizable(tblDatDetCta.getColumnModel().getColumn(j).getResizable());
            }
            tcmAux=null;
//////FIN TOTALES///            
            
            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnTblDetCtaDat.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            tblTotDetCta.setTableHeader(null);                        
  
            //Adicionar el listener que controla el redimensionamiento de las columnas.
            ZafTblColModLis objTblColModLisDetCta=new ZafTblColModLis();
            tblDatDetCta.getColumnModel().addColumnModelListener(objTblColModLisDetCta);
            
            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
            barDetDatCta=spnTblDetCtaDat.getHorizontalScrollBar();
            barDetDatTot=spnTblDetCtaTot.getHorizontalScrollBar();
            
            //PARA DESPLAZAMIENTOS DE CELDAS
            barDetDatCta.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {                    
                    barDetDatTot.setValue(evt.getValue());
                }
            });
            barDetDatTot.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barDetDatCta.setValue(evt.getValue());
                }
            });
            tcmAux=null;

            
            
            
            
            //para hacer q al dar doble click se sombree el contenido de la celda
            objTblCelEdiTxtDetCtaEne=new ZafTblCelEdiTxt(tblDatDetCta);
            tblDatDetCta.getColumnModel().getColumn(INT_TBL_DAT_DET_MES_ENE).setCellEditor(objTblCelEdiTxtDetCtaEne);
            objTblCelEdiTxtDetCtaEne.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatDetCta.getSelectedRow();
                    int intCol=tblDatDetCta.getSelectedColumn();
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtDetCtaEne.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCieDetalle(INT_TBL_DAT_DET_MES_ENE)){
                            objTblCelEdiTxtDetCtaEne.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatDetCta.getSelectedRow();
		    int intColumSel=tblDatDetCta.getSelectedColumn();
                    calculaColumnaTotalesDetalle(intFilaSel);
                    calculaFilaTotalesDetalle(intColumSel);
                    calculaTblTotColumnaTotalesDetalle();
                }
                });
                
            objTblCelEdiTxtDetCtaFeb=new ZafTblCelEdiTxt(tblDatDetCta);
            tblDatDetCta.getColumnModel().getColumn(INT_TBL_DAT_DET_MES_FEB).setCellEditor(objTblCelEdiTxtDetCtaFeb);
            objTblCelEdiTxtDetCtaFeb.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatDetCta.getSelectedRow();
                    int intCol=tblDatDetCta.getSelectedColumn();
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtDetCtaFeb.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCieDetalle(INT_TBL_DAT_DET_MES_FEB)){
                            objTblCelEdiTxtDetCtaFeb.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatDetCta.getSelectedRow();
		    int intColumSel=tblDatDetCta.getSelectedColumn();
                    calculaColumnaTotalesDetalle(intFilaSel);
                    calculaFilaTotalesDetalle(intColumSel);
                    calculaTblTotColumnaTotalesDetalle();
                }
                });
                
            objTblCelEdiTxtDetCtaMar=new ZafTblCelEdiTxt(tblDatDetCta);
            tblDatDetCta.getColumnModel().getColumn(INT_TBL_DAT_DET_MES_MAR).setCellEditor(objTblCelEdiTxtDetCtaMar);
            objTblCelEdiTxtDetCtaMar.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatDetCta.getSelectedRow();
                    int intCol=tblDatDetCta.getSelectedColumn();
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtDetCtaMar.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCieDetalle(INT_TBL_DAT_DET_MES_MAR)){
                            objTblCelEdiTxtDetCtaMar.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatDetCta.getSelectedRow();
		    int intColumSel=tblDatDetCta.getSelectedColumn();
                    calculaColumnaTotalesDetalle(intFilaSel);
                    calculaFilaTotalesDetalle(intColumSel);
                    calculaTblTotColumnaTotalesDetalle();
                }
                });
                
            objTblCelEdiTxtDetCtaAbr=new ZafTblCelEdiTxt(tblDatDetCta);
            tblDatDetCta.getColumnModel().getColumn(INT_TBL_DAT_DET_MES_ABR).setCellEditor(objTblCelEdiTxtDetCtaAbr);
            objTblCelEdiTxtDetCtaAbr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatDetCta.getSelectedRow();
                    int intCol=tblDatDetCta.getSelectedColumn();
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtDetCtaAbr.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCieDetalle(INT_TBL_DAT_DET_MES_ABR)){
                            objTblCelEdiTxtDetCtaAbr.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatDetCta.getSelectedRow();
		    int intColumSel=tblDatDetCta.getSelectedColumn();
                    calculaColumnaTotalesDetalle(intFilaSel);
                    calculaFilaTotalesDetalle(intColumSel);
                    calculaTblTotColumnaTotalesDetalle();
                }
                });
                
            objTblCelEdiTxtDetCtaMay=new ZafTblCelEdiTxt(tblDatDetCta);
            tblDatDetCta.getColumnModel().getColumn(INT_TBL_DAT_DET_MES_MAY).setCellEditor(objTblCelEdiTxtDetCtaMay);
            objTblCelEdiTxtDetCtaMay.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatDetCta.getSelectedRow();
                    int intCol=tblDatDetCta.getSelectedColumn();
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtDetCtaMay.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCieDetalle(INT_TBL_DAT_DET_MES_MAY)){
                            objTblCelEdiTxtDetCtaMay.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatDetCta.getSelectedRow();
		    int intColumSel=tblDatDetCta.getSelectedColumn();
                    calculaColumnaTotalesDetalle(intFilaSel);
                    calculaFilaTotalesDetalle(intColumSel);
                    calculaTblTotColumnaTotalesDetalle();
                }
                });
                
            objTblCelEdiTxtDetCtaJun=new ZafTblCelEdiTxt(tblDatDetCta);
            tblDatDetCta.getColumnModel().getColumn(INT_TBL_DAT_DET_MES_JUN).setCellEditor(objTblCelEdiTxtDetCtaJun);
            objTblCelEdiTxtDetCtaJun.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatDetCta.getSelectedRow();
                    int intCol=tblDatDetCta.getSelectedColumn();
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtDetCtaJun.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCieDetalle(INT_TBL_DAT_DET_MES_JUN)){
                            objTblCelEdiTxtDetCtaJun.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatDetCta.getSelectedRow();
		    int intColumSel=tblDatDetCta.getSelectedColumn();
                    calculaColumnaTotalesDetalle(intFilaSel);
                    calculaFilaTotalesDetalle(intColumSel);
                    calculaTblTotColumnaTotalesDetalle();
                }
                });
                
            objTblCelEdiTxtDetCtaJul=new ZafTblCelEdiTxt(tblDatDetCta);
            tblDatDetCta.getColumnModel().getColumn(INT_TBL_DAT_DET_MES_JUL).setCellEditor(objTblCelEdiTxtDetCtaJul);
            objTblCelEdiTxtDetCtaJul.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatDetCta.getSelectedRow();
                    int intCol=tblDatDetCta.getSelectedColumn();
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtDetCtaJul.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCieDetalle(INT_TBL_DAT_DET_MES_JUL)){
                            objTblCelEdiTxtDetCtaJul.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatDetCta.getSelectedRow();
		    int intColumSel=tblDatDetCta.getSelectedColumn();
                    calculaColumnaTotalesDetalle(intFilaSel);
                    calculaFilaTotalesDetalle(intColumSel);
                    calculaTblTotColumnaTotalesDetalle();
                }
                });
                
            objTblCelEdiTxtDetCtaAgo=new ZafTblCelEdiTxt(tblDatDetCta);
            tblDatDetCta.getColumnModel().getColumn(INT_TBL_DAT_DET_MES_AGO).setCellEditor(objTblCelEdiTxtDetCtaAgo);
            objTblCelEdiTxtDetCtaAgo.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatDetCta.getSelectedRow();
                    int intCol=tblDatDetCta.getSelectedColumn();
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtDetCtaAgo.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCieDetalle(INT_TBL_DAT_DET_MES_AGO)){
                            objTblCelEdiTxtDetCtaAgo.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatDetCta.getSelectedRow();
		    int intColumSel=tblDatDetCta.getSelectedColumn();
                    calculaColumnaTotalesDetalle(intFilaSel);
                    calculaFilaTotalesDetalle(intColumSel);
                    calculaTblTotColumnaTotalesDetalle();
                }
                });
                
            objTblCelEdiTxtDetCtaSep=new ZafTblCelEdiTxt(tblDatDetCta);
            tblDatDetCta.getColumnModel().getColumn(INT_TBL_DAT_DET_MES_SEP).setCellEditor(objTblCelEdiTxtDetCtaSep);
            objTblCelEdiTxtDetCtaSep.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatDetCta.getSelectedRow();
                    int intCol=tblDatDetCta.getSelectedColumn();
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtDetCtaSep.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCieDetalle(INT_TBL_DAT_DET_MES_SEP)){
                            objTblCelEdiTxtDetCtaSep.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatDetCta.getSelectedRow();
		    int intColumSel=tblDatDetCta.getSelectedColumn();
                    calculaColumnaTotalesDetalle(intFilaSel);
                    calculaFilaTotalesDetalle(intColumSel);
                    calculaTblTotColumnaTotalesDetalle();
                }
                });
                
            objTblCelEdiTxtDetCtaOct=new ZafTblCelEdiTxt(tblDatDetCta);
            tblDatDetCta.getColumnModel().getColumn(INT_TBL_DAT_DET_MES_OCT).setCellEditor(objTblCelEdiTxtDetCtaOct);
            objTblCelEdiTxtDetCtaOct.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatDetCta.getSelectedRow();
                    int intCol=tblDatDetCta.getSelectedColumn();
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtDetCtaOct.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCieDetalle(INT_TBL_DAT_DET_MES_OCT)){
                            objTblCelEdiTxtDetCtaOct.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatDetCta.getSelectedRow();
		    int intColumSel=tblDatDetCta.getSelectedColumn();
                    calculaColumnaTotalesDetalle(intFilaSel);
                    calculaFilaTotalesDetalle(intColumSel);
                    calculaTblTotColumnaTotalesDetalle();
                }
                });
                
            objTblCelEdiTxtDetCtaNov=new ZafTblCelEdiTxt(tblDatDetCta);
            tblDatDetCta.getColumnModel().getColumn(INT_TBL_DAT_DET_MES_NOV).setCellEditor(objTblCelEdiTxtDetCtaNov);
            objTblCelEdiTxtDetCtaNov.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatDetCta.getSelectedRow();
                    int intCol=tblDatDetCta.getSelectedColumn();
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtDetCtaNov.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCieDetalle(INT_TBL_DAT_DET_MES_NOV)){
                            objTblCelEdiTxtDetCtaNov.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatDetCta.getSelectedRow();
		    int intColumSel=tblDatDetCta.getSelectedColumn();
                    calculaColumnaTotalesDetalle(intFilaSel);
                    calculaFilaTotalesDetalle(intColumSel);
                    calculaTblTotColumnaTotalesDetalle();
                }
                });
                
            objTblCelEdiTxtDetCtaDic=new ZafTblCelEdiTxt(tblDatDetCta);
            tblDatDetCta.getColumnModel().getColumn(INT_TBL_DAT_DET_MES_DIC).setCellEditor(objTblCelEdiTxtDetCtaDic);
            objTblCelEdiTxtDetCtaDic.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatDetCta.getSelectedRow();
                    int intCol=tblDatDetCta.getSelectedColumn();
                    if(tieneCierreAnual()){
                        objTblCelEdiTxtDetCtaDic.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>El año está cerrado</HTML>");
                    }
                    else{
                        if(getMesCieDetalle(INT_TBL_DAT_DET_MES_DIC)){
                            objTblCelEdiTxtDetCtaDic.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El mes está cerrado</HTML>");
                        }
                    }
                }                                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilaSel=tblDatDetCta.getSelectedRow();
		    int intColumSel=tblDatDetCta.getSelectedColumn();
                    calculaColumnaTotalesDetalle(intFilaSel);
                    calculaFilaTotalesDetalle(intColumSel);
                    calculaTblTotColumnaTotalesDetalle();
                }
                });
                
                objTblModDetCtaTot.insertRow();
                
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblDatDetCta.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLis());
        return blnRes;
    }
    
    private class ZafLisSelLis implements javax.swing.event.ListSelectionListener{
        public void valueChanged(javax.swing.event.ListSelectionEvent e){
            javax.swing.ListSelectionModel lsm=(javax.swing.ListSelectionModel)e.getSource();
            int intMax=lsm.getMaxSelectionIndex();
            intFilSelTblCta=intMax;
            System.out.println("LA FILA SELECCIONADA ES: " + intFilSelTblCta);
            String strAux;
            if (!lsm.isSelectionEmpty()){
                    if( !  (cargarDetCta(intFilSelTblCta)))
                        mostrarMsgInf("<HTML>No se pudo cargar correctamente el detalle de la cuenta seleccionada</HTML>");
            }            
        }
    }    
    
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }    
    
    private boolean configurarVenConGrp(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_grp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre Grupo");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("334");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_grp, a1.tx_nom";
            strSQL+=" FROM tbm_grpCta AS a1";
            strSQL+=" WHERE ";
            strSQL+=" a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND st_reg not in ('I', 'E')";
            strSQL+=" ORDER BY a1.co_grp";
            vcoTipGrp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoTipGrp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipGrp.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    private boolean mostrarVenConGrp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTipGrp.setCampoBusqueda(1);
                    vcoTipGrp.show();
                    if (vcoTipGrp.getSelectedButton()==vcoTipGrp.INT_BUT_ACE){
                        txtCodGrp.setText(vcoTipGrp.getValueAt(1));
                        txtDesLarGrp.setText(vcoTipGrp.getValueAt(2));
                        if(objTooBar.getEstado()!='c'){
                            cargarDatosCompletosFrm();
                            
                            if(objTblModCta.getRowCountTrue()>0){
                                
                                if(chkIngGrp.isSelected()){
                                    for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
                                        for(int j=(INT_TBL_DAT_CTA_NOM_CTA); j<(INT_TBL_DAT_CTA_MES_DIC); j++){
                                            j++;
                                            calcula(i, j);
                                    }
                                }                                
                            }
                            
                            
                            
                        }
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipGrp.buscar("a1.co_grp", txtCodGrp.getText()))
                    {
                        txtCodGrp.setText(vcoTipGrp.getValueAt(1));
                        txtDesLarGrp.setText(vcoTipGrp.getValueAt(2));
                    }
                    else
                    {
                        vcoTipGrp.setCampoBusqueda(0);
                        vcoTipGrp.setCriterio1(11);
                        vcoTipGrp.cargarDatos();
                        vcoTipGrp.show();
                        if (vcoTipGrp.getSelectedButton()==vcoTipGrp.INT_BUT_ACE)
                        {
                            txtCodGrp.setText(vcoTipGrp.getValueAt(1));
                            txtDesLarGrp.setText(vcoTipGrp.getValueAt(2));
                        }
                        else
                        {
                            txtCodGrp.setText(strCodGrp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipGrp.buscar("a1.tx_nom", txtDesLarGrp.getText()))
                    {
                        txtCodGrp.setText(vcoTipGrp.getValueAt(1));
                        txtDesLarGrp.setText(vcoTipGrp.getValueAt(2));
                    }
                    else
                    {
                        vcoTipGrp.setCampoBusqueda(1);
                        vcoTipGrp.setCriterio1(11);
                        vcoTipGrp.cargarDatos();
                        vcoTipGrp.show();
                        if (vcoTipGrp.getSelectedButton()==vcoTipGrp.INT_BUT_ACE)
                        {
                            txtCodGrp.setText(vcoTipGrp.getValueAt(1));
                            txtDesLarGrp.setText(vcoTipGrp.getValueAt(2));
                        }
                        else
                        {
                            txtDesLarGrp.setText(strDesLarGrp);
                        }
                    }
                    break;
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean cargarDetCta(int fila){
        boolean blnRes=true;
        String strCodCtaArl="", strAniSel="";        
        int intFilSel=fila;
        String strCodCtaTxt="";
        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            strCodCtaTxt=""+objTblModCta.getValueAt(intFilSel, INT_TBL_DAT_CTA_NUM_CTA);
        else
            strCodCtaTxt=""+objTblModCta.getValueAt(intFilSel, INT_TBL_DAT_CTA_COD_CTA);
        String strJspAni=""+jspAni.getValue();
        flag=0;
        System.out.println("JOSE: " + arlDatDetCtaIniFrm.toString());
        try{
            for(int i=0;i<arlDatDetCtaIniFrm.size();i++){
                strCodCtaArl=objUti.getStringValueAt(arlDatDetCtaIniFrm, i, INT_ARL_DET_COD_CTA)==null?"":objUti.getStringValueAt(arlDatDetCtaIniFrm, i, INT_ARL_DET_COD_CTA);
                strAniSel=objUti.getStringValueAt(arlDatDetCtaIniFrm, i, INT_ARL_DET_ANI)==null?"":objUti.getStringValueAt(arlDatDetCtaIniFrm, i, INT_ARL_DET_ANI);
                if(strJspAni.toString().equals(strAniSel)){
                    if(strCodCtaArl.toString().equals(strCodCtaTxt)){
                        vecRegDetCta=new Vector();
                        vecRegDetCta.add(INT_TBL_DAT_DET_LIN, "");
                        vecRegDetCta.add(INT_TBL_DAT_DET_DET_CTA, "" + objUti.getStringValueAt(arlDatDetCtaIniFrm, i, INT_ARL_DET_NOM_DET));
                        vecRegDetCta.add(INT_TBL_DAT_DET_MES_ENE, "" + objUti.getStringValueAt(arlDatDetCtaIniFrm, i, INT_ARL_DET_VAL_ENE));
                        vecRegDetCta.add(INT_TBL_DAT_DET_MES_FEB, "" + objUti.getStringValueAt(arlDatDetCtaIniFrm, i, INT_ARL_DET_VAL_FEB));
                        vecRegDetCta.add(INT_TBL_DAT_DET_MES_MAR, "" + objUti.getStringValueAt(arlDatDetCtaIniFrm, i, INT_ARL_DET_VAL_MAR));
                        vecRegDetCta.add(INT_TBL_DAT_DET_MES_ABR, "" + objUti.getStringValueAt(arlDatDetCtaIniFrm, i, INT_ARL_DET_VAL_ABR));
                        vecRegDetCta.add(INT_TBL_DAT_DET_MES_MAY, "" + objUti.getStringValueAt(arlDatDetCtaIniFrm, i, INT_ARL_DET_VAL_MAY));
                        vecRegDetCta.add(INT_TBL_DAT_DET_MES_JUN, "" + objUti.getStringValueAt(arlDatDetCtaIniFrm, i, INT_ARL_DET_VAL_JUN));
                        vecRegDetCta.add(INT_TBL_DAT_DET_MES_JUL, "" + objUti.getStringValueAt(arlDatDetCtaIniFrm, i, INT_ARL_DET_VAL_JUL));
                        vecRegDetCta.add(INT_TBL_DAT_DET_MES_AGO, "" + objUti.getStringValueAt(arlDatDetCtaIniFrm, i, INT_ARL_DET_VAL_AGO));
                        vecRegDetCta.add(INT_TBL_DAT_DET_MES_SEP, "" + objUti.getStringValueAt(arlDatDetCtaIniFrm, i, INT_ARL_DET_VAL_SEP));
                        vecRegDetCta.add(INT_TBL_DAT_DET_MES_OCT, "" + objUti.getStringValueAt(arlDatDetCtaIniFrm, i, INT_ARL_DET_VAL_OCT));
                        vecRegDetCta.add(INT_TBL_DAT_DET_MES_NOV, "" + objUti.getStringValueAt(arlDatDetCtaIniFrm, i, INT_ARL_DET_VAL_NOV));
                        vecRegDetCta.add(INT_TBL_DAT_DET_MES_DIC, "" + objUti.getStringValueAt(arlDatDetCtaIniFrm, i, INT_ARL_DET_VAL_DIC));
                        vecRegDetCta.add(INT_TBL_DAT_DET_TOT, "");
                        vecDatDetCta.add(vecRegDetCta);
                        flag++;
                    }
                }
            }
            //Asignar vectores al modelo.
            objTblModDetCta.setData(vecDatDetCta);
            tblDatDetCta.setModel(objTblModDetCta);
            vecDatDetCta.clear();
            
            calculaColumnaTotalesSinListenerDetalle();
            calculaFilaTotalesSinListenerDetalle();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public class MiToolBar extends ZafToolBar{
        
        public MiToolBar(javax.swing.JInternalFrame jfrThis){
            super(jfrThis, objParSis);
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
            chkSumEmp.setEnabled(false);
            chkIngGrp.setEnabled(false);
            return true;
        }
        
        public boolean afterEliminar() {
            return true;
        }
        
        public boolean afterImprimir() {
            return true;
        }
        
        public boolean afterInsertar() {
            return true;
        }
        
        public boolean afterModificar() {
            cargarDatCompletDetalleCuentaFrm();
            return true;
        }
        
        public boolean afterVistaPreliminar() {
            return true;
        }
        
        public boolean anular() {
            return true;
        }
        
        public boolean beforeAceptar() {
            return true;
        }
        
        public boolean beforeAnular() {
            return true;
        }
        
        public boolean beforeCancelar() {
            return true;
        }
        
        public boolean beforeConsultar() {
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                if(    ( !(chkSumEmp.isSelected()) )   &&  (!(chkIngGrp.isSelected()))  ){
                    mostrarMsgInf("<HTML>Debe seleccionar el tipo de información que desea presentar.<BR>Información Consolidada o Información de Grupo</HTML>");
                    return false;
                }
            }
            return true;
        }
        
        public boolean beforeEliminar() {
            return true;
        }
        
        public boolean beforeImprimir() {
            return true;
        }
        
        public boolean beforeInsertar() {
            if (!isCamVal())
                return false;
            return true;
        }
        
        public boolean beforeModificar() {
            
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                if(chkSumEmp.isSelected()){
                    mostrarMsgInf("<HTML>La opción seleccionada presenta información consolidada de las empresas.<BR>No se puede modificar, seleccione opción por grupo para modificar</HTML>");
                    return false;
                }
                if(chkIngGrp.isSelected()){
                    if (!isCamVal())
                        return false;
                }
            }
            else{
                if (!isCamVal())
                    return false;
            }
            return true;
        }
        
        public boolean beforeVistaPreliminar() {
            return true;
        }
        
        public boolean cancelar() {
            return true;
        }
        
        public void clickAceptar() {
        }
        
        public void clickAnterior() {
            blnHayCam=objTblModCta.isDataModelChanged();
            try{
                if (!rstCab.isFirst()){
                    if (blnHayCam){
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
        }
        
        public void clickCancelar() {
            limpiarFrm();
            
            objTblModCtaTot.removeAllRows();
            objTblModCtaTot.insertRow();
            objTblModDetCtaTot.removeAllRows();
            objTblModDetCtaTot.insertRow();
            
            if(blnPerUsrIns){
                objTooBar.setVisibleInsertar(false);
                objTooBar.setEnabledInsertar(false);
                objTooBar.setVisibleInsertar(true);
                objTooBar.setEnabledInsertar(true);
            }
            else{
                objTooBar.setVisibleInsertar(false);
                objTooBar.setEnabledInsertar(false);
            }
            chkIngGrp.setSelected(false);
            chkSumEmp.setSelected(false);
            
        }
        
        public void clickConsultar() {
            butSavDetCta.setEnabled(false);
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                chkSumEmp.setEnabled(true);
                chkIngGrp.setEnabled(true);
                chkSumEmp.setSelected(false);
                chkIngGrp.setSelected(false);
            }
            else{
                chkSumEmp.setEnabled(false);
                chkIngGrp.setEnabled(false);
            }

        }
        
        public void clickEliminar() {
        }
        
        public void clickFin() {
            blnHayCam=objTblModCta.isDataModelChanged();
            try{
                if (!rstCab.isLast()){
                    if (blnHayCam){
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
            blnHayCam=objTblModCta.isDataModelChanged();
            try{
                if (!rstCab.isFirst()){
                    if (blnHayCam){
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
            objTblModCta.setModoOperacion(objTblModCta.INT_TBL_EDI);
            objTblModDetCta.setModoOperacion(objTblModDetCta.INT_TBL_EDI);
            chkSumEmp.setEnabled(false);
            chkIngGrp.setEnabled(false);
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                chkIngGrp.setSelected(true);
            else
                chkIngGrp.setSelected(false);
        }
        
        public void clickModificar() {
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                if(chkSumEmp.isSelected()){
                    butSavDetCta.setEnabled(false);
                    objTblModDetCta.setModoOperacion(objTblModDetCta.INT_TBL_NO_EDI);
                }
                if(chkIngGrp.isSelected()){
                    butSavDetCta.setEnabled(true);
                    objTblModDetCta.setModoOperacion(objTblModDetCta.INT_TBL_EDI);
                }
                    
                chkSumEmp.setEnabled(false);
                chkIngGrp.setEnabled(false);
            }
            else{
                butSavDetCta.setEnabled(true);
                chkIngGrp.setEnabled(false);
                chkSumEmp.setEnabled(false);
                chkIngGrp.setSelected(false);
                chkSumEmp.setSelected(false);
            }
            objTblModCta.setModoOperacion(objTblModCta.INT_TBL_EDI);
            objTblModDetCta.setModoOperacion(objTblModDetCta.INT_TBL_EDI);
        }
        
        public void clickSiguiente() {
            blnHayCam=objTblModCta.isDataModelChanged();
            try{
                if (!rstCab.isLast()){
                    if (blnHayCam){
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
            return true;
        }
        
        public boolean imprimir() {
            return true;
        }
        
        public boolean insertar() {
            if(  (objTooBar.getEstado()=='m') ||  (objTooBar.getEstado()=='x')  ){
                objTooBar.setVisibleInsertar(false);
                objTooBar.clickModificar();
                return false;                
            }
            else{
                if (!insertarReg())
                    return false;
            }
            return true;
        }
        
        public boolean modificar() {
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                if(chkSumEmp.isSelected()){
                    
                }
                if(chkIngGrp.isSelected()){
                    inactivaCamposFrm();
                    if (!actualizarReg())
                        return false;
                }
            }
            else{
                inactivaCamposFrm();
                if (!actualizarReg())
                    return false;
            }
            return true;
        }
        
        public boolean vistaPreliminar() {
            return true;
        }
        
    }
    
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDatCta.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_CTA_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CTA_COD_CTA:
                    strMsg="Código que se le da a la cuenta en Zafiro";
                    break;
                case INT_TBL_DAT_CTA_NUM_CTA:
                    strMsg="Número de la Cuenta Contable";
                    break;
                case INT_TBL_DAT_CTA_NOM_CTA:
                    strMsg="Nombre de la Cuenta Contable";
                    break;
                case INT_TBL_DAT_CTA_MES_ENE:
                    strMsg="Saldo al mes de Enero";
                    break;
                case INT_TBL_DAT_CTA_OBS_ENE:
                    strMsg="Observación del mes de Enero";
                    break;
                case INT_TBL_DAT_CTA_MES_FEB:
                    strMsg="Saldo al mes de Febrero";
                    break;
                case INT_TBL_DAT_CTA_OBS_FEB:
                    strMsg="Observación del mes de Febrero";
                    break;                    
                case INT_TBL_DAT_CTA_MES_MAR:
                    strMsg="Saldo al mes de Marzo";
                    break;
                case INT_TBL_DAT_CTA_OBS_MAR:
                    strMsg="Observación del mes de Marzo";                    
                    break;                    
                case INT_TBL_DAT_CTA_MES_ABR:
                    strMsg="Saldo al mes de Abril";
                    break;
                case INT_TBL_DAT_CTA_OBS_ABR:
                    strMsg="Observación del mes de Abril";                    
                    break;                    
                case INT_TBL_DAT_CTA_MES_MAY:
                    strMsg="Saldo al mes de Mayo";
                    break;
                case INT_TBL_DAT_CTA_OBS_MAY:
                    strMsg="Observación del mes de Mayo";                    
                    break;                                        
                case INT_TBL_DAT_CTA_MES_JUN:
                    strMsg="Saldo al mes de Junio";
                    break;
                case INT_TBL_DAT_CTA_OBS_JUN:
                    strMsg="Observación del mes de Junio";                    
                    break;
                    
                case INT_TBL_DAT_CTA_MES_JUL:
                    strMsg="Saldo al mes de Julio";
                    break;
                case INT_TBL_DAT_CTA_OBS_JUL:
                    strMsg="Observación del mes de Julio";                    
                    break;                                                                               
                case INT_TBL_DAT_CTA_MES_AGO:
                    strMsg="Saldo al mes de Agosto";
                    break;
                case INT_TBL_DAT_CTA_OBS_AGO:
                    strMsg="Observación del mes de Agosto";
                    break;
                case INT_TBL_DAT_CTA_MES_SEP:
                    strMsg="Saldo al mes de Septiembre";
                    break;
                case INT_TBL_DAT_CTA_OBS_SEP:
                    strMsg="Observación del mes de Septiembre";
                    break;                    
                case INT_TBL_DAT_CTA_MES_OCT:
                    strMsg="Saldo al mes de Octubre";
                    break;
                case INT_TBL_DAT_CTA_OBS_OCT:
                    strMsg="Observación del mes de Octubre";
                    break;                                        
                case INT_TBL_DAT_CTA_MES_NOV:
                    strMsg="Saldo al mes de Noviembre";
                    break;                    
                case INT_TBL_DAT_CTA_OBS_NOV:
                    strMsg="Observación del mes de Noviembre";
                    break;                                                            
                case INT_TBL_DAT_CTA_MES_DIC:
                    strMsg="Saldo al mes de Diciembre";
                    break;
                case INT_TBL_DAT_CTA_OBS_DIC:
                    strMsg="Observación del mes de Diciembre";
                    break;                                                                                
                case INT_TBL_DAT_CTA_TOT:
                    strMsg="Suma Total de los meses antes detallados";
                    break;
                case INT_TBL_DAT_CTA_EXI:
                    strMsg="Existe";
                    break;
                case INT_TBL_DAT_CTA_NOD_PAD:
                    strMsg="Nodo Padre";
                    break;
            }
            tblDatCta.getTableHeader().setToolTipText(strMsg);
        }
    }     
    
    private class ZafTblColModLis implements javax.swing.event.TableColumnModelListener{
        public void columnAdded(javax.swing.event.TableColumnModelEvent e){
        }
        
        public void columnMarginChanged(javax.swing.event.ChangeEvent e){
            int intColSel, intAncCol;
            //PARA CUENTAS
            if (tblDatCta.getTableHeader().getResizingColumn()!=null){
                intColSel=tblDatCta.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0){
                    intAncCol=tblDatCta.getColumnModel().getColumn(intColSel).getPreferredWidth();
                    tblTotCta.getColumnModel().getColumn(intColSel).setPreferredWidth(intAncCol);
                }
            }
            

            //PARA DETALLES DE CUENTAS
            if (tblDatDetCta.getTableHeader().getResizingColumn()!=null){
                intColSel=tblDatDetCta.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0){
                    intAncCol=tblDatDetCta.getColumnModel().getColumn(intColSel).getPreferredWidth();
                    tblTotDetCta.getColumnModel().getColumn(intColSel).setPreferredWidth(intAncCol);
                }
            }
        }
        
        public void columnMoved(javax.swing.event.TableColumnModelEvent e){
        }
        
        public void columnRemoved(javax.swing.event.TableColumnModelEvent e){
        }
        
        public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e){
        }
    }     
    
    private void calculaFilaTotales(int columna){
        int j=columna;
        double dblVal=0.00;
            for(int i=0; i<objTblModCta.getRowCountTrue();i++){
                dblVal+=Double.parseDouble(""+(objTblModCta.getValueAt(i, j)==""?"0.00":""+objTblModCta.getValueAt(i, j)));
            }
            tblTotCta.setValueAt(""+ dblVal, 0, j);
            dblVal=0.00;
    }
    
    private void calculaFilaTotalesDetalle(int columna){
        int j=columna;
        double dblVal=0.00;
            for(int i=0; i<objTblModDetCta.getRowCountTrue();i++){
                dblVal+=Double.parseDouble(""+(objTblModDetCta.getValueAt(i, j)==""?"0.00":""+objTblModDetCta.getValueAt(i, j)));
            }
            tblTotDetCta.setValueAt(""+ dblVal, 0, j);
            dblVal=0.00;
    }
    
    private void calculaTblTotColumnaTotales(){
        System.out.println("LLAMA A calculaTblTotColumnaTotales");
        double dblVal=0.00;
            for(int j=INT_TBL_DAT_CTA_MES_ENE; j<=INT_TBL_DAT_CTA_OBS_DIC; j++){
                dblVal+=Double.parseDouble(""+(objTblModCtaTot.getValueAt(0, j)==null?"0.00":""+objTblModCtaTot.getValueAt(0, j)));
                j++;
            }
            tblTotCta.setValueAt(""+ dblVal, 0, INT_TBL_DAT_CTA_TOT);
            dblVal=0.00;
    }
        
    private void calculaTblTotColumnaTotalesDetalle(){
        double dblVal=0.00;
            for(int j=INT_TBL_DAT_DET_MES_ENE; j<=INT_TBL_DAT_DET_MES_DIC; j++){
                dblVal+=Double.parseDouble(""+(objTblModDetCtaTot.getValueAt(0, j)==null?"0.00":""+objTblModDetCtaTot.getValueAt(0, j)));
            }
            tblTotDetCta.setValueAt(""+ dblVal, 0, INT_TBL_DAT_DET_TOT);
            dblVal=0.00;
    }
            
    private void calculaColumnaTotales(int fila){
        
        int i=fila;
        double dblVal=0.00;
        for(int j=INT_TBL_DAT_CTA_MES_ENE; j<=INT_TBL_DAT_CTA_OBS_DIC; j++){
            dblVal+=Double.parseDouble(""+(objTblModCta.getValueAt(i, j)==""?"0.00":""+objTblModCta.getValueAt(i, j)));
            j++;
        }
            tblDatCta.setValueAt(""+ dblVal, i, INT_TBL_DAT_CTA_TOT);
            dblVal=0.00;
    }    
    
    private void calculaColumnaTotalesDetalle(int fila){
        
        int i=fila;
        double dblVal=0.00;
        for(int j=INT_TBL_DAT_DET_MES_ENE; j<=INT_TBL_DAT_DET_MES_DIC; j++){
            dblVal+=Double.parseDouble(""+(objTblModDetCta.getValueAt(i, j)==""?"0.00":""+objTblModDetCta.getValueAt(i, j)));
        }
            tblDatDetCta.setValueAt(""+ dblVal, i, INT_TBL_DAT_DET_TOT);
            dblVal=0.00;
    }
        
    private boolean cargarDatosCompletosFrm(){
        boolean blnRes=true;
        arlDatIniFrm.clear();
        String strObsEne="", strObsFeb="", strObsMar="", strObsAbr="", strObsMay="", strObsJun="", strObsJul="", strObsAgo="";
        String strObsSep="", strObsOct="", strObsNov="", strObsDic="";
        String strTmpAux="";
        try{
            conTmp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            strSQL="";
            strSQL+="SELECT a2.ne_ani";
            strSQL+=" FROM tbr_ctaGrpCta AS a1 INNER JOIN tbm_detEstFinPre AS a2";
            strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" AND a2.ne_ani=" + jspAni.getValue() + "";
            strSQL+=" AND a1.co_grp=" + txtCodGrp.getText() + "";
            strSQL+=" GROUP BY a2.ne_ani";
            if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL)){
                strTmpAux=" AND a4.ne_ani=" + jspAni.getValue() + "";
                
                if(conTmp!=null){
                    stmTmp=conTmp.createStatement();
                    strSQL="";
                    strSQL+="SELECT x.co_emp, x.co_cta, x.tx_codCta, x.tx_desLar, x.ne_pad, x.co_grp, x.tx_nom, x.ne_ani, SUM(x.salEne) AS salEne, SUM(x.salFeb) AS salFeb,";
                    strSQL+=" SUM(x.salMar) AS salMar, SUM(x.salAbr) AS salAbr, SUM(x.salMay) AS salMay, SUM(x.salJun) AS salJun, SUM(x.salJul) AS salJul,";
                    strSQL+=" SUM(x.salAgo) AS salAgo, SUM(x.salSep) AS salSep, SUM(x.salOct) AS salOct, SUM(x.salNov) AS salNov, SUM(x.salDic) AS salDic, x.exi";
                    strSQL+=" FROM(";
                    strSQL+="         SELECT a1.co_emp, a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.ne_pad, a3.co_grp, a3.tx_nom, a4.ne_ani,  CASE WHEN a4.ne_mes=1 THEN sum(a4.nd_val) END AS salEne,";
                    strSQL+="         CASE WHEN a4.ne_mes=2 THEN sum(a4.nd_val) END AS salFeb,";
                    strSQL+="         CASE WHEN a4.ne_mes=3 THEN sum(a4.nd_val) END AS salMar,";
                    strSQL+="         CASE WHEN a4.ne_mes=4 THEN sum(a4.nd_val) END AS salAbr,";
                    strSQL+="         CASE WHEN a4.ne_mes=5 THEN sum(a4.nd_val) END AS salMay,";
                    strSQL+="         CASE WHEN a4.ne_mes=6 THEN sum(a4.nd_val) END AS salJun,";
                    strSQL+="         CASE WHEN a4.ne_mes=7 THEN sum(a4.nd_val) END AS salJul,";
                    strSQL+="         CASE WHEN a4.ne_mes=8 THEN sum(a4.nd_val) END AS salAgo,";
                    strSQL+="         CASE WHEN a4.ne_mes=9 THEN sum(a4.nd_val) END AS salSep,";
                    strSQL+="         CASE WHEN a4.ne_mes=10 THEN sum(a4.nd_val) END AS salOct,";
                    strSQL+="         CASE WHEN a4.ne_mes=11 THEN sum(a4.nd_val) END AS salNov,";
                    strSQL+="         CASE WHEN a4.ne_mes=12 THEN sum(a4.nd_val) END AS salDic,";
                    strSQL+=" 	  CASE WHEN a4.ne_ani is not null THEN 'S' ELSE 'N' END AS exi";
                    strSQL+="         FROM (tbm_plaCta AS a1 INNER JOIN tbr_ctaGrpCta AS a2";
                    strSQL+="    ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta  INNER JOIN tbm_grpCta as a3";
                    strSQL+="      ON a2.co_emp=a3.co_emp AND a2.co_grp=a3.co_grp)";
                    strSQL+="          LEFT OUTER JOIN tbm_detEstFinPre as a4  ON a1.co_emp=a4.co_emp AND a1.co_cta=a4.co_cta" + strTmpAux + "";
                    strSQL+="          WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="          AND a3.co_grp=" + txtCodGrp.getText() + "";
                    strSQL+="        GROUP BY a1.co_emp, a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.ne_pad, a3.co_grp, a3.tx_nom,   a4.ne_ani, a4.ne_mes";
                    strSQL+="     ORDER BY a1.co_emp, a3.co_grp, a1.co_cta, a4.ne_mes";
                    strSQL+="  ) AS x";
                    strSQL+=" GROUP BY x.co_emp, x.co_cta, x.tx_codCta, x.tx_desLar, x.ne_pad, x.co_grp, x.tx_nom, x.ne_ani, x.exi";
                    strSQL+=" ORDER BY x.co_emp, x.co_grp, x.tx_codCta,  x.ne_ani";
                    System.out.println("SQL POR CUENTAS SI AÑO EXISTE: " + strSQL);
                    rstTmp=stmTmp.executeQuery(strSQL);
                    while(rstTmp.next()){
                        vecRegCta=new Vector();
                        vecRegCta.add(INT_TBL_DAT_CTA_LIN, "");
                        vecRegCta.add(INT_TBL_DAT_CTA_COD_CTA, "" + rstTmp.getString("co_cta"));
                        vecRegCta.add(INT_TBL_DAT_CTA_NUM_CTA, "" + rstTmp.getString("tx_codCta"));
                        vecRegCta.add(INT_TBL_DAT_CTA_NOM_CTA, "" + rstTmp.getString("tx_desLar"));
                        stmTmp2=conTmp.createStatement();
                        strAux="";
                        strAux+="SELECT b1.tx_obs1 AS obsEne, b2.tx_obs1 AS obsFeb, b3.tx_obs1 AS obsMar,";
                        strAux+=" b4.tx_obs1 AS obsAbr, b5.tx_obs1 AS obsMay, b6.tx_obs1 AS obsJun,";
                        strAux+=" b7.tx_obs1 AS obsJul, b8.tx_obs1 AS obsAgo, b9.tx_obs1 AS obsSep, b10.tx_obs1 AS obsOct,";
                        strAux+=" b11.tx_obs1 AS obsNov, b12.tx_obs1 AS obsDic";
                        strAux+=" FROM";
                        strAux+=" (";
                        strAux+=" SELECT a2.co_emp, a2.co_cta, a2.tx_obs1";
                        strAux+=" FROM tbm_detEstFinPre AS a2 INNER JOIN tbr_ctaGrpCta AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta)";
                        strAux+=" WHERE a2.co_emp=" + rstTmp.getString("co_emp") + "";
                        strAux+=" AND a2.ne_ani=" + rstTmp.getString("ne_ani") + "";
                        strAux+=" AND a3.co_grp=" + rstTmp.getString("co_grp") + "";
                        strAux+=" AND a2.ne_mes=1";
                        strAux+=" AND a3.co_cta=" + rstTmp.getString("co_cta") + "";
                        strAux+=" ) AS b1";
                        for(int f=2; f<=12;f++){
                            strAux+=" INNER JOIN";
                            strAux+=" (";
                            strAux+=" SELECT a2.co_emp, a2.co_cta, a2.tx_obs1";
                            strAux+=" FROM tbm_detEstFinPre AS a2 INNER JOIN tbr_ctaGrpCta AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta)";
                            strAux+=" WHERE a2.co_emp=" + rstTmp.getString("co_emp") + "";
                            strAux+=" AND a2.ne_ani=" + rstTmp.getString("ne_ani") + "";
                            strAux+=" AND a3.co_grp=" + rstTmp.getString("co_grp") + "";
                            strAux+=" AND a2.ne_mes=" + f + "";
                            strAux+=" AND a3.co_cta=" + rstTmp.getString("co_cta") + "";
                            strAux+=" ) AS b" + f + " ON (b1.co_emp=b" + f + ".co_emp AND b1.co_cta=b" + f + ".co_cta)";
                        }
    //                    System.out.println("SQL PARA OBSERVACIONES: " + strAux);
                        rstTmp2=stmTmp2.executeQuery(strAux);
                        if(rstTmp2.next()){
                            strObsEne="" + ( rstTmp2.getString("obsEne")==null?"":rstTmp2.getString("obsEne") );
                            strObsFeb="" + ( rstTmp2.getString("obsFeb")==null?"":rstTmp2.getString("obsFeb") );
                            strObsMar="" + ( rstTmp2.getString("obsMar")==null?"":rstTmp2.getString("obsMar") );
                            strObsAbr="" + ( rstTmp2.getString("obsAbr")==null?"":rstTmp2.getString("obsAbr") );
                            strObsMay="" + ( rstTmp2.getString("obsMay")==null?"":rstTmp2.getString("obsMay") );
                            strObsJun="" + ( rstTmp2.getString("obsJun")==null?"":rstTmp2.getString("obsJun") );
                            strObsJul="" + ( rstTmp2.getString("obsJul")==null?"":rstTmp2.getString("obsJul") );
                            strObsAgo="" + ( rstTmp2.getString("obsAgo")==null?"":rstTmp2.getString("obsAgo") );
                            strObsSep="" + ( rstTmp2.getString("obsSep")==null?"":rstTmp2.getString("obsSep") );
                            strObsOct="" + ( rstTmp2.getString("obsOct")==null?"":rstTmp2.getString("obsOct") );
                            strObsNov="" + ( rstTmp2.getString("obsNov")==null?"":rstTmp2.getString("obsNov") );
                            strObsDic="" + ( rstTmp2.getString("obsDic")==null?"":rstTmp2.getString("obsDic") );
                        }                    
                        stmTmp2.close();
                        stmTmp2=null;
                        rstTmp2.close();
                        rstTmp2=null;

                        vecRegCta.add(INT_TBL_DAT_CTA_MES_ENE, "" + ( rstTmp.getString("salEne")==null?0.00:rstTmp.getDouble("salEne") ));
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_ENE, "" + strObsEne);
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_FEB, "" + ( rstTmp.getString("salFeb")==null?0.00:rstTmp.getDouble("salFeb") ));
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_FEB, "" + strObsFeb);
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_MAR, "" + ( rstTmp.getString("salMar")==null?0.00:rstTmp.getDouble("salMar") ));
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_MAR, "" + strObsMar);
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_ABR, "" + ( rstTmp.getString("salAbr")==null?0.00:rstTmp.getDouble("salAbr") ));
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_ABR, "" + strObsAbr);
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_MAY, "" + ( rstTmp.getString("salMay")==null?0.00:rstTmp.getDouble("salMay") ));
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_MAY, "" + strObsMay);
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_JUN, "" + ( rstTmp.getString("salJun")==null?0.00:rstTmp.getDouble("salJun") ));
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_JUN, "" + strObsJun);
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_JUL, "" + ( rstTmp.getString("salJul")==null?0.00:rstTmp.getDouble("salJul") ));
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_JUL, "" + strObsJul);
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_AGO, "" + ( rstTmp.getString("salAgo")==null?0.00:rstTmp.getDouble("salAgo") ));
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_AGO, "" + strObsAgo);
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_SEP, "" + ( rstTmp.getString("salSep")==null?0.00:rstTmp.getDouble("salSep") ));
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_SEP, "" + strObsSep);
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_OCT, "" + ( rstTmp.getString("salOct")==null?0.00:rstTmp.getDouble("salOct") ));
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_OCT, "" + strObsOct);
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_NOV, "" + ( rstTmp.getString("salNov")==null?0.00:rstTmp.getDouble("salNov") ));
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_NOV, "" + strObsNov);
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_DIC, "" + ( rstTmp.getString("salDic")==null?0.00:rstTmp.getDouble("salDic") ));
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_DIC, "" + strObsDic);
                        vecRegCta.add(INT_TBL_DAT_CTA_TOT, "");
                        vecRegCta.add(INT_TBL_DAT_CTA_EXI, "" + ( rstTmp.getString("exi")==null?"":rstTmp.getString("exi") ));
                        vecRegCta.add(INT_TBL_DAT_CTA_NOD_PAD, "" + rstTmp.getString("ne_pad")==null?"":rstTmp.getString("ne_pad"));
                        
                        vecDatCta.add(vecRegCta);
                    }
                    cargarDatCompletDetalleCuentaFrm();
                    objTblModCta.setData(vecDatCta);
                    tblDatCta.setModel(objTblModCta);
                    vecDatCta.clear();
                    calculaColumnaTotalesSinListenerCuentas();
                    calculaFilaTotalesSinListenerCuentas();
                    conTmp.close();
                    conTmp=null;
                    stmTmp.close();
                    stmTmp=null;
                    rstTmp.close();
                    rstTmp=null;
//                    objTblModCta.setModoOperacion(objTblModCta.INT_TBL_EDI);
//                    objTblModDetCta.setModoOperacion(objTblModDetCta.INT_TBL_EDI);
                }                
                
                //SI EL AÑO A PRESUPUESTAR NO EXISTE
            }
            else{
                double dblVarIni=0.00;
                if(conTmp!=null){
                    stmTmp=conTmp.createStatement();
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_cta, x.tx_codCta, x.tx_desLar, x.ne_pad, x.co_grp, x.tx_nom, x.exi FROM(";
                    strSQL+="          SELECT a1.co_emp, a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.ne_pad, a3.co_grp, a3.tx_nom,";
                    strSQL+="          CASE WHEN a4.ne_ani is not null THEN 'S' ELSE 'N' END AS exi";
                    strSQL+="          FROM (tbm_plaCta AS a1 INNER JOIN tbr_ctaGrpCta AS a2";
                    strSQL+="          ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta  INNER JOIN tbm_grpCta as a3";
                    strSQL+="          ON a2.co_emp=a3.co_emp AND a2.co_grp=a3.co_grp)";
                    strSQL+="          LEFT OUTER JOIN tbm_detEstFinPre as a4  ON a1.co_emp=a4.co_emp AND a1.co_cta=a4.co_cta AND a4.ne_ani=" + jspAni.getValue() + "";
                    strSQL+="          WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="          AND a3.co_grp=" + txtCodGrp.getText() + "";
                    strSQL+="          GROUP BY a1.co_emp, a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.ne_pad, a3.co_grp, a3.tx_nom, a4.ne_ani";
                    strSQL+="          ORDER BY a1.co_emp, a3.co_grp, a1.tx_codCta";
                    strSQL+="   ) AS x";
                    strSQL+=" GROUP BY x.co_emp, x.co_cta, x.tx_codCta, x.tx_desLar, x.ne_pad, x.co_grp, x.tx_nom, x.exi";
                    strSQL+=" ORDER BY x.co_emp, x.co_grp, x.tx_codCta";
                    System.out.println("SQL POR CUENTAS SI AÑO NO EXISTE: " + strSQL);
                    rstTmp=stmTmp.executeQuery(strSQL);
                    while(rstTmp.next()){
                        vecRegCta=new Vector();
                        vecRegCta.add(INT_TBL_DAT_CTA_LIN, "");
                        vecRegCta.add(INT_TBL_DAT_CTA_COD_CTA, "" + rstTmp.getString("co_cta"));
                        vecRegCta.add(INT_TBL_DAT_CTA_NUM_CTA, "" + rstTmp.getString("tx_codCta"));
                        vecRegCta.add(INT_TBL_DAT_CTA_NOM_CTA, "" + rstTmp.getString("tx_desLar"));
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_ENE, "" + dblVarIni);
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_ENE, "" );
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_FEB, "" + dblVarIni);
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_FEB, "" );
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_MAR, "" + dblVarIni);
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_MAR, "" );
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_ABR, "" + dblVarIni);
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_ABR, "" );
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_MAY, "" + dblVarIni);
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_MAY, "" );
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_JUN, "" + dblVarIni);
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_JUN, "" );
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_JUL, "" + dblVarIni);
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_JUL, "" );
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_AGO, "" + dblVarIni);
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_AGO, "" );
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_SEP, "" + dblVarIni);
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_SEP, "" );
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_OCT, "" + dblVarIni);
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_OCT, "" );
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_NOV, "" + dblVarIni);
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_NOV, "" );
                        vecRegCta.add(INT_TBL_DAT_CTA_MES_DIC, "" + dblVarIni);
                        vecRegCta.add(INT_TBL_DAT_CTA_OBS_DIC, "" );
                        vecRegCta.add(INT_TBL_DAT_CTA_TOT, "");
                        vecRegCta.add(INT_TBL_DAT_CTA_EXI, "" + ( rstTmp.getString("exi")==null?"":rstTmp.getString("exi") ));
                        vecRegCta.add(INT_TBL_DAT_CTA_NOD_PAD, "" + rstTmp.getString("ne_pad")==null?"":rstTmp.getString("ne_pad"));
                        
                        
                        
                        vecDatCta.add(vecRegCta);
                    }
                    cargarDatCompletDetalleCuentaFrm();
                    objTblModCta.setData(vecDatCta);
                    tblDatCta.setModel(objTblModCta);
                    vecDatCta.clear();
                    calculaColumnaTotalesSinListenerCuentas();
                    calculaFilaTotalesSinListenerCuentas();
                    conTmp.close();
                    conTmp=null;
                    stmTmp.close();
                    stmTmp=null;
                    rstTmp.close();
                    rstTmp=null;
                }                
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
    

    ////////////////////////////////
    private boolean cargarDatCompletDetalleCuentaFrm(){
        boolean blnRes=true;
        Connection conDet;
        Statement stmDet;
        ResultSet rstDet;
        arlDatDetCtaIniFrm.clear();
        try{
            conDet=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conDet!=null){
                stmDet=conDet.createStatement();
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT x.ne_ani, x.tx_codCta, x.tx_nom,";
                    strSQL+=" SUM(salEne) AS salEne, SUM(salFeb) AS salFeb, SUM(salMar) AS salMar, SUM(salAbr) AS salAbr,";
                    strSQL+=" SUM(salMay) AS salMay, SUM(salJun) AS salJun, SUM(salJul) AS salJul, SUM(salAgo) AS salAgo, SUM(salSep) AS salSep,";
                    strSQL+=" SUM(salOct) AS salOct, SUM(salNov) AS salNov, SUM(salDic) AS salDic";
                    strSQL+=" FROM(";
                    strSQL+=" 	SELECT a1.ne_ani, a1.co_cta, a2.tx_codCta, a1.tx_nom, a1.co_reg,";
                    strSQL+=" 	CASE WHEN a1.ne_mes=1 THEN SUM(a1.nd_val) END AS salEne,";
                    strSQL+="  	CASE WHEN a1.ne_mes=2 THEN SUM(a1.nd_val) END AS salFeb,";
                    strSQL+="  	CASE WHEN a1.ne_mes=3 THEN SUM(a1.nd_val) END AS salMar,";
                    strSQL+="  	CASE WHEN a1.ne_mes=4 THEN SUM(a1.nd_val) END AS salAbr,";
                    strSQL+="  	CASE WHEN a1.ne_mes=5 THEN SUM(a1.nd_val) END AS salMay,";
                    strSQL+="  	CASE WHEN a1.ne_mes=6 THEN SUM(a1.nd_val) END AS salJun,";
                    strSQL+="  	CASE WHEN a1.ne_mes=7 THEN SUM(a1.nd_val) END AS salJul,";
                    strSQL+="  	CASE WHEN a1.ne_mes=8 THEN SUM(a1.nd_val) END AS salAgo,";
                    strSQL+="  	CASE WHEN a1.ne_mes=9 THEN SUM(a1.nd_val) END AS salSep,";
                    strSQL+="  	CASE WHEN a1.ne_mes=10 THEN SUM(a1.nd_val) END AS salOct,";
                    strSQL+="  	CASE WHEN a1.ne_mes=11 THEN SUM(a1.nd_val) END AS salNov,";
                    strSQL+="  	CASE WHEN a1.ne_mes=12 THEN SUM(a1.nd_val) END AS salDic";
                    strSQL+=" 	FROM tbm_detCtaEstFinPre AS a1 INNER JOIN tbm_plaCta AS a2";
                    strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                    strSQL+=" 	WHERE a1.co_emp<>" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 	GROUP BY a1.ne_ani, a1.co_cta, a2.tx_codCta, a1.tx_nom, a1.co_reg, a1.ne_mes";
                    strSQL+=" ) AS x";
                    strSQL+=" GROUP BY x.ne_ani, x.tx_codCta, x.tx_nom";
                    strSQL+=" ORDER BY x.ne_ani";
                }
                else{
                    strSQL="";
//                    strSQL+="SELECT x.ne_ani, x.co_cta, x.tx_nom, x.co_reg,";
//                    strSQL+=" SUM(salEne) AS salEne, SUM(salFeb) AS salFeb, SUM(salMar) AS salMar, SUM(salAbr) AS salAbr,";
//                    strSQL+=" SUM(salMay) AS salMay, SUM(salJun) AS salJun, SUM(salJul) AS salJul, SUM(salAgo) AS salAgo, SUM(salSep) AS salSep,";
//                    strSQL+=" SUM(salOct) AS salOct, SUM(salNov) AS salNov, SUM(salDic) AS salDic";
//                    strSQL+=" FROM(";
//                    strSQL+="  	SELECT a1.ne_ani, a1.co_cta, a1.tx_nom, a1.co_reg,";
//                    strSQL+="  	CASE WHEN a1.ne_mes=1 THEN SUM(a1.nd_val) END AS salEne,";
//                    strSQL+="  	CASE WHEN a1.ne_mes=2 THEN SUM(a1.nd_val) END AS salFeb,";
//                    strSQL+="  	CASE WHEN a1.ne_mes=3 THEN SUM(a1.nd_val) END AS salMar,";
//                    strSQL+="  	CASE WHEN a1.ne_mes=4 THEN SUM(a1.nd_val) END AS salAbr,";
//                    strSQL+="  	CASE WHEN a1.ne_mes=5 THEN SUM(a1.nd_val) END AS salMay,";
//                    strSQL+="  	CASE WHEN a1.ne_mes=6 THEN SUM(a1.nd_val) END AS salJun,";
//                    strSQL+="  	CASE WHEN a1.ne_mes=7 THEN SUM(a1.nd_val) END AS salJul,";
//                    strSQL+="  	CASE WHEN a1.ne_mes=8 THEN SUM(a1.nd_val) END AS salAgo,";
//                    strSQL+="  	CASE WHEN a1.ne_mes=9 THEN SUM(a1.nd_val) END AS salSep,";
//                    strSQL+="  	CASE WHEN a1.ne_mes=10 THEN SUM(a1.nd_val) END AS salOct,";
//                    strSQL+="  	CASE WHEN a1.ne_mes=11 THEN SUM(a1.nd_val) END AS salNov,";
//                    strSQL+="  	CASE WHEN a1.ne_mes=12 THEN SUM(a1.nd_val) END AS salDic";
//                    strSQL+="  	FROM tbm_detCtaEstFinPre AS a1";
//                    strSQL+="  	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                    strSQL+="  	GROUP BY a1.ne_ani, a1.co_cta, a1.tx_nom, a1.co_reg, a1.ne_mes";
//                    strSQL+=" ) AS x";
//                    strSQL+=" GROUP BY x.ne_ani, x.co_cta, x.tx_nom, x.co_reg";
//                    strSQL+=" ORDER BY x.ne_ani, x.co_cta, x.co_reg";
                    
                    strSQL+="SELECT x.ne_ani, x.co_cta, x.tx_nom, x.co_reg, x.tx_codCta,";
                    strSQL+=" SUM(salEne) AS salEne, SUM(salFeb) AS salFeb, SUM(salMar) AS salMar, SUM(salAbr) AS salAbr,";
                    strSQL+=" SUM(salMay) AS salMay, SUM(salJun) AS salJun, SUM(salJul) AS salJul, SUM(salAgo) AS salAgo, SUM(salSep) AS salSep,";
                    strSQL+=" SUM(salOct) AS salOct, SUM(salNov) AS salNov, SUM(salDic) AS salDic";
                    strSQL+=" FROM(";
                    strSQL+="     SELECT a1.ne_ani, a1.co_cta, a1.tx_nom, a1.co_reg, a2.tx_codCta,";
                    strSQL+=" 	  CASE WHEN a1.ne_mes=1 THEN SUM(a1.nd_val) END AS salEne,";
                    strSQL+="  	  CASE WHEN a1.ne_mes=2 THEN SUM(a1.nd_val) END AS salFeb,";
                    strSQL+="  	  CASE WHEN a1.ne_mes=3 THEN SUM(a1.nd_val) END AS salMar,";
                    strSQL+="  	  CASE WHEN a1.ne_mes=4 THEN SUM(a1.nd_val) END AS salAbr,";
                    strSQL+="  	  CASE WHEN a1.ne_mes=5 THEN SUM(a1.nd_val) END AS salMay,";
                    strSQL+="  	  CASE WHEN a1.ne_mes=6 THEN SUM(a1.nd_val) END AS salJun,";
                    strSQL+="  	  CASE WHEN a1.ne_mes=7 THEN SUM(a1.nd_val) END AS salJul,";
                    strSQL+="  	  CASE WHEN a1.ne_mes=8 THEN SUM(a1.nd_val) END AS salAgo,";
                    strSQL+="  	  CASE WHEN a1.ne_mes=9 THEN SUM(a1.nd_val) END AS salSep,";
                    strSQL+="  	  CASE WHEN a1.ne_mes=10 THEN SUM(a1.nd_val) END AS salOct,";
                    strSQL+="  	  CASE WHEN a1.ne_mes=11 THEN SUM(a1.nd_val) END AS salNov,";
                    strSQL+="  	  CASE WHEN a1.ne_mes=12 THEN SUM(a1.nd_val) END AS salDic";
                    strSQL+="     FROM tbm_detCtaEstFinPre AS a1 INNER JOIN tbm_plaCta AS a2";
                    strSQL+="     ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                    strSQL+="     WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="     GROUP BY a1.ne_ani, a1.co_cta, a1.tx_nom, a1.co_reg, a1.ne_mes, a2.tx_codCta";
                    strSQL+="  ) AS x";
                    strSQL+=" GROUP BY x.ne_ani, x.co_cta, x.tx_nom, x.co_reg, x.tx_codCta";
                    strSQL+=" ORDER BY x.ne_ani, x.co_cta, x.co_reg";
                }
                System.out.println("SQL DEL DETALLE DE LAS CUENTAS: " +strSQL);
                rstDet=stmDet.executeQuery(strSQL);
                while(rstDet.next()){
                    arlRegDetCtaIniFrm=new ArrayList();
                    arlRegDetCtaIniFrm.add(INT_ARL_DET_ANI, rstDet.getString("ne_ani"));
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                        arlRegDetCtaIniFrm.add(INT_ARL_DET_COD_CTA, rstDet.getString("tx_codCta"));
                    else
                        arlRegDetCtaIniFrm.add(INT_ARL_DET_COD_CTA, rstDet.getString("co_cta"));
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                        arlRegDetCtaIniFrm.add(INT_ARL_DET_COD_REG, "");
                    else
                        arlRegDetCtaIniFrm.add(INT_ARL_DET_COD_REG, rstDet.getString("co_reg"));
                    arlRegDetCtaIniFrm.add(INT_ARL_DET_NOM_DET, rstDet.getString("tx_nom"));
                    arlRegDetCtaIniFrm.add(INT_ARL_DET_VAL_ENE, rstDet.getString("salEne"));
                    arlRegDetCtaIniFrm.add(INT_ARL_DET_VAL_FEB, rstDet.getString("salFeb"));
                    arlRegDetCtaIniFrm.add(INT_ARL_DET_VAL_MAR, rstDet.getString("salMar"));
                    arlRegDetCtaIniFrm.add(INT_ARL_DET_VAL_ABR, rstDet.getString("salAbr"));
                    arlRegDetCtaIniFrm.add(INT_ARL_DET_VAL_MAY, rstDet.getString("salMay"));
                    arlRegDetCtaIniFrm.add(INT_ARL_DET_VAL_JUN, rstDet.getString("salJun"));
                    arlRegDetCtaIniFrm.add(INT_ARL_DET_VAL_JUL, rstDet.getString("salJul"));
                    arlRegDetCtaIniFrm.add(INT_ARL_DET_VAL_AGO, rstDet.getString("salAgo"));
                    arlRegDetCtaIniFrm.add(INT_ARL_DET_VAL_SEP, rstDet.getString("salSep"));
                    arlRegDetCtaIniFrm.add(INT_ARL_DET_VAL_OCT, rstDet.getString("salOct"));
                    arlRegDetCtaIniFrm.add(INT_ARL_DET_VAL_NOV, rstDet.getString("salNov"));
                    arlRegDetCtaIniFrm.add(INT_ARL_DET_VAL_DIC, rstDet.getString("salDic"));
                    arlDatDetCtaIniFrm.add(arlRegDetCtaIniFrm);
                }
                System.out.println("SQL DE SELECT DEL FRM PARA DETALLE: " + arlDatDetCtaIniFrm.toString());
                conDet.close();
                conDet=null;
                stmDet.close();
                stmDet=null;
                rstDet.close();
                rstDet=null;
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
        
    private void limpiarFrm(){
        objTblModCta.removeAllRows();
        objTblModDetCta.removeAllRows();
        txtCodGrp.setText("");
        txtDesLarGrp.setText("");
        jspAni.setModel(new SpinnerNumberModel(intJspValIni, intJspValMin, intJspValMax, intJspValInc));
        txtCodGrp.setEnabled(true);
        txtDesLarGrp.setEnabled(true);
        txaObs1.setText("");
    }

    //PARA QUE SUME SIN NECESIDAD QUE SE DISPARE UN EVENTO EN EL RENDERIZADOR DE LA TABLA
    private void calculaFilaTotalesSinListenerCuentas(){
        double dblVal=0.00;
        for(int j=INT_TBL_DAT_CTA_MES_ENE; j<=INT_TBL_DAT_CTA_TOT; j++){
                for(int i=0; i<objTblModCta.getRowCountTrue();i++){
//                    dblVal+=Double.parseDouble(""+(objTblModCta.getValueAt(i, j)==""?"0.00":""+objTblModCta.getValueAt(i, j)));
                    dblVal+=Double.parseDouble(""+(objTblModCta.getValueAt(i, j)==""?"0.00":(objTblModCta.getValueAt(i, j)==null?"0.00":""+objTblModCta.getValueAt(i, j) )));
                }
            tblTotCta.setValueAt(""+ dblVal, 0, j);
            dblVal=0.00;
            j++;
        }
    }
    
    private void calculaColumnaTotalesSinListenerCuentas(){
        double dblVal=0.00;
        for(int i=0; i<objTblModCta.getRowCountTrue(); i++){
            for(int j=INT_TBL_DAT_CTA_MES_ENE; j<=INT_TBL_DAT_CTA_OBS_DIC; j++){
//                dblVal+=Double.parseDouble(""+(objTblModCta.getValueAt(i, j)==""?"0.00":""+objTblModCta.getValueAt(i, j)));
//                dblVal+=Double.parseDouble(""+(objTblModCta.getValueAt(i, j)==""?"0.00":(objTblModCta.getValueAt(i, j)==null?"0.00":""+objTblModCta.getValueAt(i, j) )));
                dblVal+=Double.parseDouble(objUti.codificar(objTblModCta.getValueAt(i, j), 3));
                j++;
            }
            tblDatCta.setValueAt(""+ dblVal, i, INT_TBL_DAT_CTA_TOT);
            dblVal=0.00;
        }
    }    
    
    //PARA QUE SUME SIN NECESIDAD QUE SE DISPARE UN EVENTO EN EL RENDERIZADOR DE LA TABLA
    private void calculaFilaTotalesSinListenerDetalle(){
        double dblVal=0.00;
        for(int j=INT_TBL_DAT_DET_MES_ENE; j<=INT_TBL_DAT_DET_TOT; j++){
                for(int i=0; i<objTblModDetCta.getRowCountTrue();i++){
//                    dblVal+=Double.parseDouble(""+(objTblModDetCta.getValueAt(i, j)==""?"0.00":""+objTblModDetCta.getValueAt(i, j)));
                    dblVal+=Double.parseDouble(""+(objTblModDetCta.getValueAt(i, j)==""?"0.00":(objTblModDetCta.getValueAt(i, j)==null?"0.00":""+objTblModDetCta.getValueAt(i, j) )));
                }
            tblTotDetCta.setValueAt(""+ dblVal, 0, j);
            dblVal=0.00;
        }
    }
    
    private void calculaColumnaTotalesSinListenerDetalle(){
        double dblVal=0.00;
        for(int i=0; i<objTblModDetCta.getRowCountTrue(); i++){
            for(int j=INT_TBL_DAT_DET_MES_ENE; j<=INT_TBL_DAT_DET_MES_DIC; j++){
//                dblVal+=Double.parseDouble(""+(objTblModDetCta.getValueAt(i, j)==""?"0.00":""+objTblModDetCta.getValueAt(i, j)));
                dblVal+=Double.parseDouble(""+(objTblModDetCta.getValueAt(i, j)==""?"0.00":(objTblModDetCta.getValueAt(i, j)==null?"0.00":""+objTblModDetCta.getValueAt(i, j) )));
            }
            tblDatDetCta.setValueAt(""+ dblVal, i, INT_TBL_DAT_DET_TOT);
            dblVal=0.00;
        }
    }
    
    private boolean actualizaCambioDetalle(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if(con!=null){
                if(deleteTbmDetCtaEstFinPre(intFilSelTblCta)){
                    if(updateTbmDetEstFinPre(intFilSelTblCta)){
                        if(insertaTbmDetCtaEstFinPre(intFilSelTblCta)){
                             //SE REFRESCA LA DATA QUE SE TIENE EN EL ARRAYLIST DE FORMA GENERAL POR MOTIVO QUE SE GENERO 
                              // ALGUN CAMBIO EN EL DETALLE Y SE GUARDO
                              // EN LA DB, POR TANTO LO Q SE TENIA EN EL ARRAYLIST HUBO QUE ELIMINARLO Y VOLVER A CARGARLO
                                        con.commit();
                                        blnRes=true;                                        
                                        if(cargarDatCompletDetalleCuentaFrm())
                                            if(refrescaTblCuentaporCambioDetalle(intFilSelTblCta))
                                                calculaColumnaTotales(intFilSelTblCta);
                                                calculaFilaTotalesSinListenerCuentas();
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            con.close();
            con=null;
            }            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean updateTbmDetEstFinPre(int filaCuenta){
        boolean blnRes=true;
        double dlbValMes=0.00;
        int intCodCtaTbl=0;
        int intFilTblCta=filaCuenta;
        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int j=INT_TBL_DAT_DET_MES_ENE; j<=INT_TBL_DAT_DET_MES_DIC; j++){
                    strSQL="";
                    strSQL+=" UPDATE tbm_detestfinpre";
                    dlbValMes=objUti.parseDouble(objTblModDetCtaTot.getValueAt(0,j));
                    strSQL+=" SET nd_val=" + objUti.redondear((dlbValMes),objParSis.getDecimalesBaseDatos()) + ",";
                    strSQL+="fe_ultmod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";
                    strSQL+=" co_usrmod=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE co_emp= " + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND ne_ani=" + jspAni.getValue() + "";
                    intCodCtaTbl=Integer.parseInt("" + ( objTblModCta.getValueAt(intFilTblCta, INT_TBL_DAT_CTA_COD_CTA)==null?"0.00":objTblModCta.getValueAt(intFilTblCta, INT_TBL_DAT_CTA_COD_CTA) )  );
                    strSQL+=" AND co_cta=" + intCodCtaTbl + "";
                    strSQL+=" AND ne_mes=" + (j-1) + "";
                    System.out.println("SQL DE UPDATE TBMDETESTFINPRE: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                datFecAux=null;
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
    
    private boolean deleteTbmDetCtaEstFinPre(int filaCuenta){
        int intFilTblCta=filaCuenta;
        boolean blnRes=true;
        int intCodCtaTbl=0;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="DELETE FROM tbm_detctaestfinpre";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND ne_ani=" + jspAni.getValue() + "";
                intCodCtaTbl=Integer.parseInt("" + ( objTblModCta.getValueAt(intFilTblCta, INT_TBL_DAT_CTA_COD_CTA)==null?"0.00":objTblModCta.getValueAt(intFilTblCta, INT_TBL_DAT_CTA_COD_CTA) )  );
                strSQL+=" AND co_cta=" + intCodCtaTbl + "";
                System.out.println("SQL DE DELETE TBMDETESTFINPRE: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean insertaTbmDetCtaEstFinPre(int filaCuenta){
        boolean blnRes=true;
        double dlbValMes=0.00;
        int k=0;
        int intUltRegSQL=0;
        int intFilTblCta=filaCuenta;
        int intCodCtaTbl=0;
        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT MAX(a1.co_reg)";
                strSQL+=" FROM tbm_detCtaEstFinPre AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.ne_ani=" + jspAni.getValue() + "";
                intCodCtaTbl=Integer.parseInt("" + ( objTblModCta.getValueAt(intFilTblCta, INT_TBL_DAT_CTA_COD_CTA)==null?"0.00":objTblModCta.getValueAt(intFilTblCta, INT_TBL_DAT_CTA_COD_CTA) )  );
                strSQL+=" AND a1.co_cta=" + intCodCtaTbl + "";
                intUltRegSQL=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltRegSQL==-1)
                    return false;
                intUltRegSQL++;
                
                for(int i=0; i<objTblModDetCta.getRowCountTrue(); i++){
                    for(int j=INT_TBL_DAT_DET_MES_ENE; j<=INT_TBL_DAT_DET_MES_DIC; j++){
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_detctaestfinpre(";
                        strSQL+=" co_emp, ne_ani, co_cta, ne_mes, co_reg, tx_nom, nd_val, fe_ultmod, co_usrmod)";
                        strSQL+=" VALUES(";
                        strSQL+="" + objParSis.getCodigoEmpresa() + ",";
                        strSQL+="" + jspAni.getValue() + ",";
                        strSQL+="" + intCodCtaTbl + ",";
                        strSQL+="" + (j-1) + ",";
                        strSQL+="" + intUltRegSQL + ",";//registro
                        strSQL+="" + objUti.codificar(objTblModDetCta.getValueAt(i, INT_TBL_DAT_DET_DET_CTA)) + ",";//nombre
                        dlbValMes=objUti.parseDouble(objTblModDetCta.getValueAt(i,j));
                        strSQL+="" + objUti.redondear((dlbValMes),objParSis.getDecimalesBaseDatos()) + ",";//valor
                        strSQL+="'" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";
                        strSQL+="" + objParSis.getCodigoUsuario() + "";
                        strSQL+=")";
                        System.out.println("SQL DE DETCTAESTFINPRE: " + strSQL);
                        stm.executeUpdate(strSQL);
                    }
                    intUltRegSQL++;
                }
                datFecAux=null;
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
    
    private boolean refrescaTblCuentaporCambioDetalle(int fila){
        boolean blnRes=true;
        int intFilSel=fila;
        System.out.println("EN REFRESCA LA FILA ES: " + intFilSel);
        try{
            objTblModCta.setValueAt("" + objTblModDetCtaTot.getValueAt(0, INT_TBL_DAT_DET_MES_ENE), intFilSel, INT_TBL_DAT_CTA_MES_ENE);
            objTblModCta.setValueAt("" + objTblModDetCtaTot.getValueAt(0, INT_TBL_DAT_DET_MES_FEB), intFilSel, INT_TBL_DAT_CTA_MES_FEB);
            objTblModCta.setValueAt("" + objTblModDetCtaTot.getValueAt(0, INT_TBL_DAT_DET_MES_MAR), intFilSel, INT_TBL_DAT_CTA_MES_MAR);
            objTblModCta.setValueAt("" + objTblModDetCtaTot.getValueAt(0, INT_TBL_DAT_DET_MES_ABR), intFilSel, INT_TBL_DAT_CTA_MES_ABR);
            objTblModCta.setValueAt("" + objTblModDetCtaTot.getValueAt(0, INT_TBL_DAT_DET_MES_MAY), intFilSel, INT_TBL_DAT_CTA_MES_MAY);
            objTblModCta.setValueAt("" + objTblModDetCtaTot.getValueAt(0, INT_TBL_DAT_DET_MES_JUN), intFilSel, INT_TBL_DAT_CTA_MES_JUN);
            objTblModCta.setValueAt("" + objTblModDetCtaTot.getValueAt(0, INT_TBL_DAT_DET_MES_JUL), intFilSel, INT_TBL_DAT_CTA_MES_JUL);
            objTblModCta.setValueAt("" + objTblModDetCtaTot.getValueAt(0, INT_TBL_DAT_DET_MES_AGO), intFilSel, INT_TBL_DAT_CTA_MES_AGO);
            objTblModCta.setValueAt("" + objTblModDetCtaTot.getValueAt(0, INT_TBL_DAT_DET_MES_SEP), intFilSel, INT_TBL_DAT_CTA_MES_SEP);
            objTblModCta.setValueAt("" + objTblModDetCtaTot.getValueAt(0, INT_TBL_DAT_DET_MES_OCT), intFilSel, INT_TBL_DAT_CTA_MES_OCT);
            objTblModCta.setValueAt("" + objTblModDetCtaTot.getValueAt(0, INT_TBL_DAT_DET_MES_NOV), intFilSel, INT_TBL_DAT_CTA_MES_NOV);
            objTblModCta.setValueAt("" + objTblModDetCtaTot.getValueAt(0, INT_TBL_DAT_DET_MES_DIC), intFilSel, INT_TBL_DAT_CTA_MES_DIC);
            //objTblModCta.setValueAt("" + objTblModDetCtaTot.getValueAt(intFilSel, INT_TBL_DAT_DET_MES_ENE), intFilSel, INT_TBL_DAT_CTA_MES_ENE);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean isCamVal(){
        
        if(objTblModDetCta.isDataModelChanged()){
            mostrarMsgInf("<HTML>Ud realizó cambios en el detalle de la cuenta y no guardó los cambios<BR>Guarde los cambios del detalle y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        if (txtCodGrp.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Grupo</FONT> es obligatorio.<BR>Escriba o seleccione un grupo y vuelva a intentarlo.</HTML>");
            txtDesLarGrp.requestFocus();
            return false;
        }
        
        strSQL="";
        strSQL+="SELECT a2.ne_ani";
        strSQL+=" from tbm_cabEstFinPre AS a1 INNER JOIN tbm_cieMenEstFinPre AS a2";
        strSQL+=" ON a1.co_emp=a2.co_emp AND a1.ne_ani=a2.ne_ani";
        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
        strSQL+=" AND a2.ne_ani=" + jspAni.getValue() + "";
        strSQL+=" AND a1.st_cie='A'";
        strSQL+=" GROUP BY a2.ne_ani";
        strSQL+=" ORDER BY a2.ne_ani";
        //SI EL AÑO YA EXISTE
        if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL)){
            if (objTooBar.getEstado()=='n'){
                mostrarMsgInf("<HTML>El año <FONT COLOR=\"blue\">" + jspAni.getValue() + "</FONT> tiene cierre anual. No puede realizar un ingreso porque el año seleccionado está cerrado.<BR>Realice una reapertura anual y vuelva a intentarlo</HTML>");
                return false;
            }
        }
        
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="<HTML>El año <FONT COLOR=\"blue\">" + jspAni.getValue() + "</FONT> ya está presupuestado<br>¿Desea modificar dicho año?</HTML>";
        strSQL="";
        strSQL+="SELECT ne_ani";
        strSQL+=" FROM tbm_cabEstFinPre";
        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
        strSQL+=" AND ne_ani=" + jspAni.getValue() + "";
        if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL)){
            if (objTooBar.getEstado()=='n'){
                if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
                    if(blnPerUsrMod){
                        objTooBar.setVisibleModificar(true);
                        objTooBar.setEnabledModificar(true);
                        objTooBar.setEstado('m');
                    }
                    else{
                        mostrarMsgInf("Ud. no está autorizado para ingresar al modo MODIFICAR");
                        objTooBar.setVisibleModificar(false);
                        objTooBar.setEnabledModificar(false);
                        return false;
                    }
                }
                else{
                    System.out.println("POR LA OPCION NOP");
                    return false;
                }
            }
        }
        return true;
    }
    
    private void inactivaCamposFrm(){
        txtCodGrp.setEnabled(false);
        txtDesLarGrp.setEnabled(false);
        butGrp.setEnabled(false);
        jspAni.setEnabled(false);
    }    
    
    private boolean actualizarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if(updateTbmCabEstFinPre()){
                    if(updateInsertTbmDetEstFinPre()){
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

    private boolean updateTbmCabEstFinPre(){
        boolean blnRes=true;
        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="UPDATE tbm_cabEstFinPre";
                strSQL+=" SET tx_obs1=" + objUti.codificar(txaObs1.getText(),0) + ",";
                strSQL+=" fe_ultmod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";
                strSQL+=" co_usrmod= " + objParSis.getCodigoUsuario() + "";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND ne_ani=" + jspAni.getValue() + "";
                System.out.println("SQL DE UPDATE TBMCABESTFINPRE: " + strSQL);
                stm.executeUpdate(strSQL);
            }
            datFecAux=null;
            stm.close();
            stm=null;
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
    
    private boolean updateInsertTbmDetEstFinPre(){
        boolean blnRes=true;
        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        String strExiTbl="";
        double dlbValMes=0.00;
        int k=0;
        try{
            if(con!=null){
                for(int i=0; i<objTblModCta.getRowCountTrue();i++){
                    strExiTbl=""+objTblModCta.getValueAt(i, INT_TBL_DAT_CTA_EXI);
                    if(strExiTbl.toString().equals("S")){
                        k=0;
                        for(int j=INT_TBL_DAT_CTA_MES_ENE; j<=INT_TBL_DAT_CTA_OBS_DIC; j++){
                            stm=con.createStatement();
                            k++;
                            strSQL="";
                            strSQL+="UPDATE tbm_detEstFinPre";
                            dlbValMes=objUti.parseDouble(objTblModCta.getValueAt(i,j));
                            strSQL+=" SET nd_val=" + objUti.redondear((dlbValMes),objParSis.getDecimalesBaseDatos()) + ",";
                            strSQL+=" tx_obs1=" + objUti.codificar(objTblModCta.getValueAt(i, (j+1)), 0) + ",";
                            strSQL+=" fe_ultmod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";
                            strSQL+=" co_usrmod= " + objParSis.getCodigoUsuario() + "";
                            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+=" AND ne_ani=" + jspAni.getValue() + "";
                            strSQL+=" AND co_cta=" + objTblModCta.getValueAt(i, INT_TBL_DAT_CTA_COD_CTA) + "";
                            strSQL+=" AND ne_mes=" + (k) + "";
                            System.out.println("SQL DE UPDATE TBMDETESTFINPRE: " + strSQL);
                            stm.executeUpdate(strSQL);
                            j++;
                        }                        
                    }
                    else{
                        k=0;
                        for(int j=INT_TBL_DAT_CTA_MES_ENE; j<=INT_TBL_DAT_CTA_OBS_DIC; j++){
                            stm=con.createStatement();
                            k++;
                            strSQL="";
                            strSQL+=" INSERT INTO tbm_detestfinpre(";
                            strSQL+=" co_emp, ne_ani, co_cta, ne_mes, nd_val, tx_obs1, fe_ultmod, co_usrmod)";
                            strSQL+=" VALUES (";
                            strSQL+="" + objParSis.getCodigoEmpresa() + ",";
                            strSQL+="" + jspAni.getValue() + ",";
                            strSQL+="" + objTblModCta.getValueAt(i, INT_TBL_DAT_CTA_COD_CTA) + ",";
                            strSQL+="" + (k) + ",";
                            dlbValMes=objUti.parseDouble(objTblModCta.getValueAt(i,j));
                            strSQL+="" + objUti.redondear((dlbValMes),objParSis.getDecimalesBaseDatos()) + ",";//valor
                            strSQL+="" + objUti.codificar(objTblModCta.getValueAt(i, (j+1)), 0) + ",";
                            strSQL+="'" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";
                            strSQL+="" + objParSis.getCodigoUsuario() + "";
                            strSQL+=")";
                            System.out.println("SQL DE INSERT TBMDETESTFINPRE: " + strSQL);
                            stm.executeUpdate(strSQL);
                            j++;
                        }
                        k=0;
                    }
                }
            }
            datFecAux=null;
            stm.close();
            stm=null;
            
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
   

    private boolean insertarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if(con!=null){
                if(insertaTbmCabEstFinPre()){
                    if(insertaTbmDetEstFinPre()){
                        con.commit();
                        blnRes=true;
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
                con.close();
                con=null;
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
    
    
    private boolean insertaTbmCabEstFinPre(){
        boolean blnRes=true;
        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        System.out.println("POR INSERCION EN CABECERA");
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="INSERT INTO tbm_cabestfinpre(";
                strSQL+=" co_emp, ne_ani, tx_obs1, st_reg, st_cie, fe_ing, fe_ultmod, co_usring, co_usrmod)";
                strSQL+=" VALUES (";
                strSQL+="" + objParSis.getCodigoEmpresa() + ",";
                strSQL+="" + jspAni.getValue() + ",";
                strSQL+="" + objUti.codificar(txaObs1.getText()) + ",";
                strSQL+="'A',";
                strSQL+="'O',";
                strSQL+="'" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";
                strSQL+="'" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";
                strSQL+="" + objParSis.getCodigoUsuario() + ",";
                strSQL+="" + objParSis.getCodigoUsuario() + "";
                strSQL+=")";
                System.out.println("SQL DE INSERCION EN TBM_CABESTFINPRE: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
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
    
    private boolean insertaTbmDetEstFinPre(){
        boolean blnRes=true;
        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        String strExiTbl="";
        double dlbValMes=0.00;
        int k=0;
        try{
            if(con!=null){
                for(int i=0; i<objTblModCta.getRowCountTrue();i++){
                    strExiTbl=""+objTblModCta.getValueAt(i, INT_TBL_DAT_CTA_EXI);
                    k=0;
                    for(int j=INT_TBL_DAT_CTA_MES_ENE; j<=INT_TBL_DAT_CTA_OBS_DIC; j++){
                        stm=con.createStatement();
                        k++;
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_detestfinpre(";
                        strSQL+=" co_emp, ne_ani, co_cta, ne_mes, nd_val, tx_obs1, fe_ultmod, co_usrmod)";
                        strSQL+=" VALUES (";
                        strSQL+="" + objParSis.getCodigoEmpresa() + ",";
                        strSQL+="" + jspAni.getValue() + ",";
                        strSQL+="" + objTblModCta.getValueAt(i, INT_TBL_DAT_CTA_COD_CTA) + ",";
                        strSQL+="" + (k) + ",";
                        dlbValMes=objUti.parseDouble(objTblModCta.getValueAt(i,j));
                        strSQL+="" + objUti.redondear((dlbValMes),objParSis.getDecimalesBaseDatos()) + ",";//valor                        
                        strSQL+="" + objUti.codificar(objTblModCta.getValueAt(i, (j+1)), 0) + ",";
                        strSQL+="'" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";
                        strSQL+="" + objParSis.getCodigoUsuario() + "";
                        strSQL+=")";
                        System.out.println("SQL DE INSERT TBMDETESTFINPRE: " + strSQL);
                        stm.executeUpdate(strSQL);
                        j++;
                    }
                    k=0;
                }
            }
            datFecAux=null;
            stm.close();
            stm=null;
            
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

    
    private boolean consultarReg(){
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try{
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null){
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                if(objParSis.getCodigoEmpresa()!=0){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a2.ne_ani, a1.co_grp";
                    strSQL+=" FROM tbr_ctaGrpCta AS a1 INNER JOIN tbm_detEstFinPre as a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a2.ne_ani=" + jspAni.getValue() + "";
                    strAux=txtCodGrp.getText();
                    if (!strAux.equals(""))
                        strSQL+=" AND co_grp LIKE '" + strAux.replaceAll("'", "''") + "'";
                    strSQL+=" GROUP BY a1.co_emp, a2.ne_ani, a1.co_grp";
                    strSQL+=" ORDER BY a1.co_emp, a2.ne_ani, a1.co_grp";                    
                }
                else{
                    if(chkSumEmp.isSelected()){
                        strSQL="";
                        strSQL+="SELECT " + objParSis.getCodigoEmpresa() + " AS co_emp, a2.ne_ani, a3.tx_nom";
                        strSQL+=" FROM (tbr_ctaGrpCta AS a1 INNER JOIN tbm_grpCta AS a3 ON a1.co_emp=a3.co_emp AND a1.co_grp=a3.co_grp)";
                        strSQL+=" INNER JOIN tbm_detEstFinPre as a2";
                        strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                        strSQL+=" WHERE a1.co_emp IN (0,1,2,3,4)";
                        strSQL+=" AND a2.ne_ani=" + jspAni.getValue() + "";
                        strAux=txtDesLarGrp.getText();
                        if (!strAux.equals("")){
                            strSQL+=" AND (  (a3.tx_nom LIKE TRIM(UPPER('" + strAux.replaceAll("'", "''") + "')))";
                            strSQL+=" OR (a3.tx_nom LIKE TRIM(LOWER('" + txtDesLarGrp.getText() + "')))";
                            strSQL+=" OR (a3.tx_nom LIKE TRIM(INITCAP('" + txtDesLarGrp.getText() + "'))) )";
                        }
                        strSQL+=" GROUP BY a2.ne_ani, a3.tx_nom";
                        strSQL+=" ORDER BY a2.ne_ani, a3.tx_nom";
                    }
                    if(chkIngGrp.isSelected()){
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a2.ne_ani, a1.co_grp";
                        strSQL+=" FROM tbr_ctaGrpCta AS a1 INNER JOIN tbm_detEstFinPre as a2";
                        strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND a2.ne_ani=" + jspAni.getValue() + "";
                        strAux=txtCodGrp.getText();
                        if (!strAux.equals(""))
                            strSQL+=" AND co_grp LIKE '" + strAux.replaceAll("'", "''") + "'";
                        strSQL+=" GROUP BY a1.co_emp, a2.ne_ani, a1.co_grp";
                        strSQL+=" ORDER BY a1.co_emp, a2.ne_ani, a1.co_grp";
                    }
                }
                System.out.println("EN CONSULTAR REG: " + strSQL);
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next()){
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                }
                else{
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
    
    
    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            if (cargarCabReg()){
                if (cargarDetReg()){
                    
//                    if(objTblModCta.getRowCountTrue()>0){
//                        for(int i=(objTblModCta.getRowCountTrue()-1); i>=0; i--)
//                            for(int j=(INT_TBL_DAT_CTA_NOM_CTA+1); j<objTblModCta.getColumnCount(); j++)
//                                calcula(i, j);
//                    }
                    
                    
                }
            }
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }    
        
    
    private boolean cargarCabReg(){
        int intPosRel;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                if(objParSis.getCodigoEmpresa()!=0){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a2.ne_ani, a1.co_grp, a3.tx_nom, b1.tx_obs1";
                    strSQL+=" FROM (tbr_ctaGrpCta AS a1 INNER JOIN tbm_detEstFinPre as a2 ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                    strSQL+=" INNER JOIN tbm_cabEstFinPre as b1 ON a2.co_emp=b1.co_emp AND a2.ne_ani=b1.ne_ani) INNER JOIN tbm_grpCta AS a3";
                    strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_grp=a3.co_grp";
                    strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                    strSQL+=" AND a2.ne_ani=" + rstCab.getString("ne_ani") + "";
                    strSQL+=" AND a1.co_grp=" + rstCab.getString("co_grp") + "";
                    strSQL+=" GROUP BY a1.co_emp, a2.ne_ani, a1.co_grp, a3.tx_nom, b1.tx_obs1";
                    strSQL+=" ORDER BY a1.co_emp, a2.ne_ani, a1.co_grp";                    
                }
                else{
                    if(chkSumEmp.isSelected()){
                        strSQL="";
                        strSQL+="SELECT 0 AS co_grp, " + objParSis.getCodigoEmpresa() + " AS co_emp, a2.ne_ani, a3.tx_nom, b1.tx_obs1";
                        strSQL+=" FROM (tbr_ctaGrpCta AS a1 INNER JOIN tbm_detEstFinPre as a2 ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                        strSQL+=" INNER JOIN tbm_cabEstFinPre as b1 ON a2.co_emp=b1.co_emp AND a2.ne_ani=b1.ne_ani) INNER JOIN tbm_grpCta AS a3";
                        strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_grp=a3.co_grp";
                        strSQL+=" WHERE a1.co_emp IN(0,1,2,3,4)";
                        strSQL+=" AND a2.ne_ani=" + rstCab.getString("ne_ani") + "";
                        strSQL+=" AND (  (a3.tx_nom=TRIM(UPPER('" + rstCab.getString("tx_nom") + "')))";
                        strSQL+=" OR (a3.tx_nom=TRIM(LOWER('" + rstCab.getString("tx_nom") + "')))";
                        strSQL+=" OR (a3.tx_nom=TRIM(INITCAP('" + rstCab.getString("tx_nom") + "'))) )";
                        strSQL+=" GROUP BY a2.ne_ani,a3.tx_nom, b1.tx_obs1";
                        strSQL+=" ORDER BY a2.ne_ani";
                    }
                    if(chkIngGrp.isSelected()){
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a2.ne_ani, a1.co_grp, a3.tx_nom, b1.tx_obs1";
                        strSQL+=" FROM (tbr_ctaGrpCta AS a1 INNER JOIN tbm_detEstFinPre as a2 ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                        strSQL+=" INNER JOIN tbm_cabEstFinPre as b1 ON a2.co_emp=b1.co_emp AND a2.ne_ani=b1.ne_ani) INNER JOIN tbm_grpCta AS a3";
                        strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_grp=a3.co_grp";
                        strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a2.ne_ani=" + rstCab.getString("ne_ani") + "";
                        strSQL+=" AND a1.co_grp=" + rstCab.getString("co_grp") + "";
                        strSQL+=" GROUP BY a1.co_emp, a2.ne_ani, a1.co_grp, a3.tx_nom, b1.tx_obs1";
                        strSQL+=" ORDER BY a1.co_emp, a2.ne_ani, a1.co_grp";  
                    }
                }
                System.out.println("CARGAR CAB REG CONTIENE: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    strAux=rst.getString("ne_ani");
                    jspAni.setModel(new SpinnerNumberModel(Integer.parseInt(strAux), intJspValMin, intJspValMax, intJspValInc));
                    strAux=rst.getString("co_grp");
                    txtCodGrp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nom");
                    txtDesLarGrp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                }
                else{
//                    objTooBar.setEstadoRegistro("Eliminado");
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

    private boolean cargarDetReg(){
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                strSQL="";
                if(objParSis.getCodigoEmpresa()!=0){
                    strSQL+="SELECT b1.co_emp, b1.co_cta, b1.tx_codCta, b1.tx_desLar, b1.ne_pad, b1.nd_val AS salEne, b1.tx_obs1 AS obsEne,";
                    strSQL+=" b2.nd_val AS salFeb, b2.tx_obs1 AS obsFeb, b3.nd_val AS salMar, b3.tx_obs1 AS obsMar, b4.nd_val AS salAbr, b4.tx_obs1 AS obsAbr,";
                    strSQL+=" b5.nd_val AS salMay, b5.tx_obs1 AS obsMay, b6.nd_val AS salJun, b6.tx_obs1 AS obsJun, b7.nd_val AS salJul, b7.tx_obs1 AS obsJul,";
                    strSQL+=" b8.nd_val AS salAgo, b8.tx_obs1 AS obsAgo, b9.nd_val AS salSep, b9.tx_obs1 AS obsSep, b10.nd_val AS salOct, b10.tx_obs1 AS obsOct,";
                    strSQL+=" b11.nd_val AS salNov, b11.tx_obs1 AS obsNov, b12.nd_val AS salDic, b12.tx_obs1 AS obsDic, b1.exi";
                    strSQL+=" FROM";
                    strSQL+=" (";
                    strSQL+=" SELECT a1.co_emp, a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.ne_pad, a2.nd_val, a2.tx_obs1,";
                    strSQL+=" CASE WHEN a2.ne_ani is not null THEN 'S' ELSE 'N' END AS exi";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" INNER JOIN tbm_detEstFinPre AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                    strSQL+=" INNER JOIN tbr_ctaGrpCta AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                    strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                    strSQL+=" AND a2.ne_ani=" + rstCab.getString("ne_ani") + "";
                    strSQL+=" AND a3.co_grp=" + rstCab.getString("co_grp") + "";
                    strSQL+=" AND a2.ne_mes=1";
                    strSQL+=" ) AS b1";
                    for(int j=2; j<=12; j++){
                        strSQL+=" INNER JOIN";
                        strSQL+=" (";
                        strSQL+=" SELECT a1.co_emp, a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.ne_pad, a2.nd_val, a2.tx_obs1,";
                        strSQL+=" CASE WHEN a2.ne_ani is not null THEN 'S' ELSE 'N' END AS exi";
                        strSQL+=" FROM tbm_plaCta AS a1";
                        strSQL+=" INNER JOIN tbm_detEstFinPre AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                        strSQL+=" INNER JOIN tbr_ctaGrpCta AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                        strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a2.ne_ani=" + rstCab.getString("ne_ani") + "";
                        strSQL+=" AND a3.co_grp=" + rstCab.getString("co_grp") + "";
                        strSQL+=" AND a2.ne_mes=" + j + "";
                        strSQL+=" ) AS b" + j + " ON (b1.co_emp=b" + j + ".co_emp AND b1.co_cta=b" + j + ".co_cta)";
                    }
                    strSQL+=" ORDER BY b1.co_emp, b1.tx_codCta";
                }
                else{
                    if(chkSumEmp.isSelected()){
                        strSQL+="SELECT " + objParSis.getCodigoEmpresa() + " AS co_emp, b1.tx_codCta, b1.tx_desLar, SUM(b1.nd_val) AS salEne, b1.tx_obs1 AS obsEne,";
                        strSQL+=" SUM(b2.nd_val) AS salFeb, b2.tx_obs1 AS obsFeb, SUM(b3.nd_val) AS salMar, b3.tx_obs1 AS obsMar, SUM(b4.nd_val) AS salAbr, b4.tx_obs1 AS obsAbr,";
                        strSQL+=" SUM(b5.nd_val) AS salMay, b5.tx_obs1 AS obsMay, SUM(b6.nd_val) AS salJun, b6.tx_obs1 AS obsJun, SUM(b7.nd_val) AS salJul, b7.tx_obs1 AS obsJul,";
                        strSQL+=" SUM(b8.nd_val) AS salAgo, b8.tx_obs1 AS obsAgo, SUM(b9.nd_val) AS salSep, b9.tx_obs1 AS obsSep, SUM(b10.nd_val) AS salOct, b10.tx_obs1 AS obsOct,";
                        strSQL+=" SUM(b11.nd_val) AS salNov, b11.tx_obs1 AS obsNov, SUM(b12.nd_val) AS salDic, b12.tx_obs1 AS obsDic, b1.exi";
                        strSQL+=" FROM";
                        strSQL+=" (";
                        strSQL+=" SELECT a1.co_emp, a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.nd_val, a2.tx_obs1,";
                        strSQL+=" CASE WHEN a2.ne_ani is not null THEN 'S' ELSE 'N' END AS exi";
                        strSQL+=" FROM tbm_plaCta AS a1";
                        strSQL+=" INNER JOIN tbm_detEstFinPre AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                        strSQL+=" INNER JOIN tbr_ctaGrpCta AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                        strSQL+=" INNER JOIN tbm_grpCta AS a4 ON a3.co_emp=a4.co_emp AND a3.co_grp=a4.co_grp";
                        strSQL+=" WHERE a1.co_emp IN(0,1,2,3,4)";
                        strSQL+=" AND a2.ne_ani=" + rstCab.getString("ne_ani") + "";
                        strSQL+=" AND (  (a4.tx_nom=TRIM(UPPER('" + rstCab.getString("tx_nom") + "')))";
                        strSQL+=" OR (a4.tx_nom=TRIM(LOWER('" + rstCab.getString("tx_nom") + "')))";
                        strSQL+=" OR (a4.tx_nom=TRIM(INITCAP('" + rstCab.getString("tx_nom") + "'))) )";
                        strSQL+=" AND a2.ne_mes=1";
                        strSQL+=" ) AS b1";
                        for(int j=2; j<=12; j++){
                            strSQL+=" INNER JOIN";
                            strSQL+=" (";
                            strSQL+=" SELECT a1.co_emp, a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.nd_val, a2.tx_obs1,";
                            strSQL+=" CASE WHEN a2.ne_ani is not null THEN 'S' ELSE 'N' END AS exi";
                            strSQL+=" FROM tbm_plaCta AS a1";
                            strSQL+=" INNER JOIN tbm_detEstFinPre AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                            strSQL+=" INNER JOIN tbr_ctaGrpCta AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                            strSQL+=" INNER JOIN tbm_grpCta AS a4 ON a3.co_emp=a4.co_emp AND a3.co_grp=a4.co_grp";
                            strSQL+=" WHERE a1.co_emp IN(0,1,2,3,4)";
                            strSQL+=" AND a2.ne_ani=" + rstCab.getString("ne_ani") + "";
                            strSQL+=" AND (  (a4.tx_nom=TRIM(UPPER('" + rstCab.getString("tx_nom") + "')))";
                            strSQL+=" OR (a4.tx_nom=TRIM(LOWER('" + rstCab.getString("tx_nom") + "')))";
                            strSQL+=" OR (a4.tx_nom=TRIM(INITCAP('" + rstCab.getString("tx_nom") + "'))) )";
                            strSQL+=" AND a2.ne_mes=" + j + "";
                            strSQL+=" ) AS b" + j + " ON (b1.co_emp=b" + j + ".co_emp AND b1.co_cta=b" + j + ".co_cta)";
                        }
                        strSQL+=" GROUP BY b1.tx_codCta, b1.tx_desLar, b1.tx_obs1, b2.tx_obs1, b1.exi,";
                        strSQL+=" b1.tx_obs1, b2.tx_obs1, b3.tx_obs1, b4.tx_obs1, b5.tx_obs1, b6.tx_obs1,";
                        strSQL+=" b7.tx_obs1, b8.tx_obs1, b9.tx_obs1, b10.tx_obs1, b11.tx_obs1, b12.tx_obs1";
                        strSQL+=" ORDER BY b1.tx_codCta";
                    }
                    if(chkIngGrp.isSelected()){
                        strSQL+="SELECT b1.co_emp, b1.co_cta, b1.tx_codCta, b1.tx_desLar, b1.ne_pad, b1.nd_val AS salEne, b1.tx_obs1 AS obsEne,";
                        strSQL+=" b2.nd_val AS salFeb, b2.tx_obs1 AS obsFeb, b3.nd_val AS salMar, b3.tx_obs1 AS obsMar, b4.nd_val AS salAbr, b4.tx_obs1 AS obsAbr,";
                        strSQL+=" b5.nd_val AS salMay, b5.tx_obs1 AS obsMay, b6.nd_val AS salJun, b6.tx_obs1 AS obsJun, b7.nd_val AS salJul, b7.tx_obs1 AS obsJul,";
                        strSQL+=" b8.nd_val AS salAgo, b8.tx_obs1 AS obsAgo, b9.nd_val AS salSep, b9.tx_obs1 AS obsSep, b10.nd_val AS salOct, b10.tx_obs1 AS obsOct,";
                        strSQL+=" b11.nd_val AS salNov, b11.tx_obs1 AS obsNov, b12.nd_val AS salDic, b12.tx_obs1 AS obsDic, b1.exi";
                        strSQL+=" FROM";
                        strSQL+=" (";
                        strSQL+=" SELECT a1.co_emp, a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.ne_pad, a2.nd_val, a2.tx_obs1,";
                        strSQL+=" CASE WHEN a2.ne_ani is not null THEN 'S' ELSE 'N' END AS exi";
                        strSQL+=" FROM tbm_plaCta AS a1";
                        strSQL+=" INNER JOIN tbm_detEstFinPre AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                        strSQL+=" INNER JOIN tbr_ctaGrpCta AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                        strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a2.ne_ani=" + rstCab.getString("ne_ani") + "";
                        strSQL+=" AND a3.co_grp=" + rstCab.getString("co_grp") + "";
                        strSQL+=" AND a2.ne_mes=1";
                        strSQL+=" ) AS b1";
                        for(int j=2; j<=12; j++){
                            strSQL+=" INNER JOIN";
                            strSQL+=" (";
                            strSQL+=" SELECT a1.co_emp, a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.ne_pad, a2.nd_val, a2.tx_obs1,";
                            strSQL+=" CASE WHEN a2.ne_ani is not null THEN 'S' ELSE 'N' END AS exi";
                            strSQL+=" FROM tbm_plaCta AS a1";
                            strSQL+=" INNER JOIN tbm_detEstFinPre AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                            strSQL+=" INNER JOIN tbr_ctaGrpCta AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                            strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                            strSQL+=" AND a2.ne_ani=" + rstCab.getString("ne_ani") + "";
                            strSQL+=" AND a3.co_grp=" + rstCab.getString("co_grp") + "";
                            strSQL+=" AND a2.ne_mes=" + j + "";
                            strSQL+=" ) AS b" + j + " ON (b1.co_emp=b" + j + ".co_emp AND b1.co_cta=b" + j + ".co_cta)";
                        }
                        strSQL+=" ORDER BY b1.co_emp, b1.tx_codCta";
                    }                    
                }
                System.out.println("EN CARGARDETREG: " + strSQL);
                rst=stm.executeQuery(strSQL);
                vecDatCta.clear();
                while (rst.next()){
                    vecRegCta=new Vector();
                    vecRegCta.add(INT_TBL_DAT_CTA_LIN, "");
                    if(objParSis.getCodigoEmpresa()!=0)
                        vecRegCta.add(INT_TBL_DAT_CTA_COD_CTA, "" + rst.getString("co_cta"));
                    else{
                        if(chkSumEmp.isSelected())
                            vecRegCta.add(INT_TBL_DAT_CTA_COD_CTA, "");
                        else
                            vecRegCta.add(INT_TBL_DAT_CTA_COD_CTA, "" + rst.getString("co_cta"));
                    }
                    vecRegCta.add(INT_TBL_DAT_CTA_NUM_CTA, "" + rst.getString("tx_codCta"));
                    vecRegCta.add(INT_TBL_DAT_CTA_NOM_CTA, "" + rst.getString("tx_desLar"));
                    vecRegCta.add(INT_TBL_DAT_CTA_MES_ENE, "" + objUti.codificar(rst.getString("salEne"),3));
                    vecRegCta.add(INT_TBL_DAT_CTA_OBS_ENE, "" + rst.getString("obsEne")==null?"":rst.getString("obsEne"));
                    vecRegCta.add(INT_TBL_DAT_CTA_MES_FEB, "" + objUti.codificar(rst.getString("salFeb"),3));
                    vecRegCta.add(INT_TBL_DAT_CTA_OBS_FEB, "" + rst.getString("obsFeb")==null?"":rst.getString("obsFeb"));
                    vecRegCta.add(INT_TBL_DAT_CTA_MES_MAR, "" + objUti.codificar(rst.getString("salMar"),3));
                    vecRegCta.add(INT_TBL_DAT_CTA_OBS_MAR, "" + rst.getString("obsMar")==null?"":rst.getString("obsMar"));
                    vecRegCta.add(INT_TBL_DAT_CTA_MES_ABR, "" + objUti.codificar(rst.getString("salAbr"),3));
                    vecRegCta.add(INT_TBL_DAT_CTA_OBS_ABR, "" + rst.getString("obsAbr")==null?"":rst.getString("obsAbr"));
                    vecRegCta.add(INT_TBL_DAT_CTA_MES_MAY, "" + objUti.codificar(rst.getString("salMay"),3));
                    vecRegCta.add(INT_TBL_DAT_CTA_OBS_MAY, "" + rst.getString("obsMay")==null?"":rst.getString("obsMay"));
                    vecRegCta.add(INT_TBL_DAT_CTA_MES_JUN, "" + objUti.codificar(rst.getString("salJun"),3));
                    vecRegCta.add(INT_TBL_DAT_CTA_OBS_JUN, "" + rst.getString("obsJun")==null?"":rst.getString("obsJun"));
                    vecRegCta.add(INT_TBL_DAT_CTA_MES_JUL, "" + objUti.codificar(rst.getString("salJul"),3));
                    vecRegCta.add(INT_TBL_DAT_CTA_OBS_JUL, "" + rst.getString("obsJul")==null?"":rst.getString("obsJul"));
                    vecRegCta.add(INT_TBL_DAT_CTA_MES_AGO, "" + objUti.codificar(rst.getString("salAgo"),3));
                    vecRegCta.add(INT_TBL_DAT_CTA_OBS_AGO, "" + rst.getString("obsAgo")==null?"":rst.getString("obsAgo"));
                    vecRegCta.add(INT_TBL_DAT_CTA_MES_SEP, "" + objUti.codificar(rst.getString("salSep"),3));
                    vecRegCta.add(INT_TBL_DAT_CTA_OBS_SEP, "" + rst.getString("obsSep")==null?"":rst.getString("obsSep"));
                    vecRegCta.add(INT_TBL_DAT_CTA_MES_OCT, "" + objUti.codificar(rst.getString("salOct"),3));
                    vecRegCta.add(INT_TBL_DAT_CTA_OBS_OCT, "" + rst.getString("obsOct")==null?"":rst.getString("obsOct"));
                    vecRegCta.add(INT_TBL_DAT_CTA_MES_NOV, "" + objUti.codificar(rst.getString("salNov"),3));
                    vecRegCta.add(INT_TBL_DAT_CTA_OBS_NOV, "" + rst.getString("obsNov")==null?"":rst.getString("obsNov"));
                    vecRegCta.add(INT_TBL_DAT_CTA_MES_DIC, "" + objUti.codificar(rst.getString("salDic"),3));
                    vecRegCta.add(INT_TBL_DAT_CTA_OBS_DIC, "" + rst.getString("obsDic")==null?"":rst.getString("obsDic"));
                    vecRegCta.add(INT_TBL_DAT_CTA_TOT, "");
                    vecRegCta.add(INT_TBL_DAT_CTA_EXI, "" + rst.getString("exi"));
                    
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        if(chkIngGrp.isSelected())
                            vecRegCta.add(INT_TBL_DAT_CTA_NOD_PAD, "" + rst.getString("ne_pad")==null?"":rst.getString("ne_pad"));
                        if(chkSumEmp.isSelected())
                            vecRegCta.add(INT_TBL_DAT_CTA_NOD_PAD, "");
                    }
                    else
                        vecRegCta.add(INT_TBL_DAT_CTA_NOD_PAD, "" + rst.getString("ne_pad")==null?"":rst.getString("ne_pad"));
                    
                    vecDatCta.add(vecRegCta);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                cargarDatCompletDetalleCuentaFrm();
                objTblModCta.setData(vecDatCta);
                tblDatCta.setModel(objTblModCta);
                vecDatCta.clear();
                calculaColumnaTotalesSinListenerCuentas();
                calculaFilaTotalesSinListenerCuentas();
                objTblModCta.initRowsState();
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
    

    private boolean isRegPro(){
        boolean blnRes=true;
        strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
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
    
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
 
    //EL MES Q SE RECIBE ES EL MES Q SE ENCUENTRA ALMACENADO EN LA DB
    /*
    private boolean cancelaEdicionMes(int mes){
        boolean blnRes=false;
        int intMes=mes;
        String strArlAni="", strArlMes="";
        
        switch(intMes){
            case INT_TBL_DAT_CTA_MES_ENE:
                for(int i=0; i<arlDatMesCie.size(); i++){
                    strArlAni=objUti.getStringValueAt(arlDatMesCie, i, INT_ARL_CIE_ANI);
                    strArlMes=objUti.getStringValueAt(arlDatMesCie, i, INT_ARL_CIE_MES);
                    if(strArlAni.toString().equals(jspAni.getValue())){
                        if(strArlMes.toString().equals("1")){
                            objTblCelEdiTxtEne.setCancelarEdicion(true);
                        }
                    }
                }
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;                
            
        }

        
            vecAuxCta=new Vector();
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_ENE);            
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_ENE);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_FEB);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_FEB);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_MAR);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_MAR);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_ABR);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_ABR);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_MAY);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_MAY);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_JUN);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_JUN);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_JUL);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_JUL);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_AGO);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_AGO);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_SEP);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_SEP);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_OCT);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_OCT);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_NOV);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_NOV);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_MES_DIC);
            vecAuxCta.add("" + INT_TBL_DAT_CTA_OBS_DIC);
//            vecAuxCta.add("" + INT_TBL_DAT_CTA_TOT);
            objTblModCta.setColumnasEditables(vecAuxCta);
            vecAuxCta=null;        
        
        
        
        
        
        
        final int INT_TBL_DAT_CTA_MES_ENE=4;
        final int INT_TBL_DAT_CTA_OBS_ENE=5;
        final int INT_TBL_DAT_CTA_MES_FEB=6;
        final int INT_TBL_DAT_CTA_OBS_FEB=7;
        final int INT_TBL_DAT_CTA_MES_MAR=8;
        final int INT_TBL_DAT_CTA_OBS_MAR=9;
        final int INT_TBL_DAT_CTA_MES_ABR=10;
        final int INT_TBL_DAT_CTA_OBS_ABR=11;
        final int INT_TBL_DAT_CTA_MES_MAY=12;
        final int INT_TBL_DAT_CTA_OBS_MAY=13;
        final int INT_TBL_DAT_CTA_MES_JUN=14;
        final int INT_TBL_DAT_CTA_OBS_JUN=15;
        final int INT_TBL_DAT_CTA_MES_JUL=16;
        final int INT_TBL_DAT_CTA_OBS_JUL=17;
        final int INT_TBL_DAT_CTA_MES_AGO=18;
        final int INT_TBL_DAT_CTA_OBS_AGO=19;
        final int INT_TBL_DAT_CTA_MES_SEP=20;
        final int INT_TBL_DAT_CTA_OBS_SEP=21;
        final int INT_TBL_DAT_CTA_MES_OCT=22;
        final int INT_TBL_DAT_CTA_OBS_OCT=23;
        final int INT_TBL_DAT_CTA_MES_NOV=24;
        final int INT_TBL_DAT_CTA_OBS_NOV=25;
        final int INT_TBL_DAT_CTA_MES_DIC=26;        
        
        
        return blnRes;
    }
    */
    
    
    private boolean getMesCie(int mesTbl){
        boolean blnRes=false;
        int intMes=mesTbl;
        strAux="";
        arlDatMesCie.clear();
        try{
            conTmp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            switch(intMes){
                case INT_TBL_DAT_CTA_MES_ENE:
                    strAux=" AND ne_mes=1";
                    break;
                case INT_TBL_DAT_CTA_MES_FEB:
                    strAux=" AND ne_mes=2";
                    break;
                case INT_TBL_DAT_CTA_MES_MAR:
                    strAux=" AND ne_mes=3";
                    break;
                case INT_TBL_DAT_CTA_MES_ABR:
                    strAux=" AND ne_mes=4";
                    break;
                case INT_TBL_DAT_CTA_MES_MAY:
                    strAux=" AND ne_mes=5";
                    break;
                case INT_TBL_DAT_CTA_MES_JUN:
                    strAux=" AND ne_mes=6";
                    break;
                case INT_TBL_DAT_CTA_MES_JUL:
                    strAux=" AND ne_mes=7";
                    break;
                case INT_TBL_DAT_CTA_MES_AGO:
                    strAux=" AND ne_mes=8";
                    break;
                case INT_TBL_DAT_CTA_MES_SEP:
                    strAux=" AND ne_mes=9";
                    break;
                case INT_TBL_DAT_CTA_MES_OCT:
                    strAux=" AND ne_mes=10";
                    break;
                case INT_TBL_DAT_CTA_MES_NOV:
                    strAux=" AND ne_mes=11";
                    break;
                case INT_TBL_DAT_CTA_MES_DIC:
                    strAux=" AND ne_mes=12";
                    break;
                default:
                    strAux="";
            }
            
            if(conTmp!=null){
                stmTmp=conTmp.createStatement();
                strSQL="";
                strSQL+="SELECT co_emp, ne_ani, ne_mes";
                strSQL+=" FROM tbm_cieMenEstFinPre";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND ne_ani=" + jspAni.getValue() + "";
                strSQL+=strAux;
                strSQL+=" ORDER BY co_emp, ne_ani, ne_mes";
                rstTmp=stmTmp.executeQuery(strSQL);
                while(rstTmp.next()){
                    blnRes=true;
                }
                System.out.println("SQL getMesCie: " + strSQL);
                conTmp.close();
                conTmp=null;
                stmTmp.close();
                stmTmp=null;
                rstTmp.close();
                rstTmp=null;
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
    

    private boolean getMesCieDetalle(int mesTbl){
        boolean blnRes=false;
        int intMes=mesTbl;
        strAux="";
        arlDatMesCie.clear();
        try{
            conTmp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            switch(intMes){
                case INT_TBL_DAT_DET_MES_ENE:
                    strAux=" AND ne_mes=1";
                    break;
                case INT_TBL_DAT_DET_MES_FEB:
                    strAux=" AND ne_mes=2";
                    break;
                case INT_TBL_DAT_DET_MES_MAR:
                    strAux=" AND ne_mes=3";
                    break;
                case INT_TBL_DAT_DET_MES_ABR:
                    strAux=" AND ne_mes=4";
                    break;
                case INT_TBL_DAT_DET_MES_MAY:
                    strAux=" AND ne_mes=5";
                    break;
                case INT_TBL_DAT_DET_MES_JUN:
                    strAux=" AND ne_mes=6";
                    break;
                case INT_TBL_DAT_DET_MES_JUL:
                    strAux=" AND ne_mes=7";
                    break;
                case INT_TBL_DAT_DET_MES_AGO:
                    strAux=" AND ne_mes=8";
                    break;
                case INT_TBL_DAT_DET_MES_SEP:
                    strAux=" AND ne_mes=9";
                    break;
                case INT_TBL_DAT_DET_MES_OCT:
                    strAux=" AND ne_mes=10";
                    break;
                case INT_TBL_DAT_DET_MES_NOV:
                    strAux=" AND ne_mes=11";
                    break;
                case INT_TBL_DAT_DET_MES_DIC:
                    strAux=" AND ne_mes=12";
                    break;
                default:
                    strAux="";
            }
            
            if(conTmp!=null){
                stmTmp=conTmp.createStatement();
                strSQL="";
                strSQL+="SELECT co_emp, ne_ani, ne_mes";
                strSQL+=" FROM tbm_cieMenEstFinPre";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND ne_ani=" + jspAni.getValue() + "";
                strSQL+=strAux;
                strSQL+=" ORDER BY co_emp, ne_ani, ne_mes";
                rstTmp=stmTmp.executeQuery(strSQL);
                while(rstTmp.next()){
                    blnRes=true;
                }
                System.out.println("SQL getMesCieDetalle: " + strSQL);
                conTmp.close();
                conTmp=null;
                stmTmp.close();
                stmTmp=null;
                rstTmp.close();
                rstTmp=null;
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
        
    
    
    private boolean tieneCierreAnual(){
        boolean blnRes=false;
        try{
            strSQL="";
            strSQL+="SELECT st_cie FROM tbm_cabEstFinPre";
            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" AND ne_ani=" + jspAni.getValue() + "";
            strSQL+=" AND st_cie='A'";
            System.out.println("GIGI: " + strSQL);
            if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL)){
                blnRes=true;
                System.out.println("YA ESTA CERRADO EL ANIO");
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    

    private void calcula( int fila, int columna){
        String strNePad=""+objTblModCta.getValueAt(fila, INT_TBL_DAT_CTA_NOD_PAD)==null?"":""+objTblModCta.getValueAt(fila, INT_TBL_DAT_CTA_NOD_PAD);
        String strNePadTbl="";
        String strCtaPadTbl="";
        double dblValMesTbl=0.00;
        double dblValAcuTbl=0.00;
        int intFilCtaPad=0;
        try{
            for(int i=0; i<objTblModCta.getRowCountTrue();i++){
                strNePadTbl=""+objTblModCta.getValueAt(i, INT_TBL_DAT_CTA_NOD_PAD)==null?"":""+objTblModCta.getValueAt(i, INT_TBL_DAT_CTA_NOD_PAD);
                if(strNePad.toString().equals(strNePadTbl)){
                    dblValMesTbl=Double.parseDouble(     objTblModCta.getValueAt(i, columna)==""?"0.00":""+objTblModCta.getValueAt(i, columna)    );
                    dblValAcuTbl+=dblValMesTbl;
                }                        
            }
            for(int i=(objTblModCta.getRowCountTrue()-1); i>=0;i--){
                strCtaPadTbl=""+(objTblModCta.getValueAt(i, INT_TBL_DAT_CTA_COD_CTA)==null?"0":""+objTblModCta.getValueAt(i, INT_TBL_DAT_CTA_COD_CTA));
                if(strNePad.toString().equals(strCtaPadTbl)){
                    intFilCtaPad=i;
                    objTblModCta.setValueAt(""+dblValAcuTbl, intFilCtaPad, columna);
//                    calcula(intFilCtaPad, columna);
                }
            }            
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    
    
    
    
    
}
