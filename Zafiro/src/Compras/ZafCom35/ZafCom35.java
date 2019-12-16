/*
 * ZafCon06.java
 *
 *  Created on 02 de noviembre de 2005, 11:25 PM
 */
package Compras.ZafCom35;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafDate.ZafDatePicker;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import java.sql.SQLException;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import java.math.BigDecimal;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafPerUsr.ZafPerUsr;



/**
 *
 * @author  Eddye Lino
 */
public class ZafCom35 extends javax.swing.JInternalFrame
{
    //Constantes: Columnas del JTable.
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_COD_EMP=1;
    final int INT_TBL_DAT_COD_REG=2;
    final int INT_TBL_DAT_COD_ITM=3;
    final int INT_TBL_DAT_COD_ITM_MAE=4;
    final int INT_TBL_DAT_COD_NUM_VEC_CON_ITM=5;
    final int INT_TBL_DAT_COD_ALT_ITM=6;
    final int INT_TBL_DAT_NOM_ITM=7;
    final int INT_TBL_DAT_DES_COR_UNI_MED=8;
    final int INT_TBL_DAT_DES_LAR_UNI_MED=9;
    final int INT_TBL_DAT_COD_USU_RES_CON=10;
    final int INT_TBL_DAT_ALI_USU_RES_CON=11;
    final int INT_TBL_DAT_NOM_USU_RES_CON=12;
    final int INT_TBL_DAT_FEC_HOR_SOL_CON=13;
    final int INT_TBL_DAT_FEC_HOR_REA_CON=14;
    final int INT_TBL_DAT_STK_ACT=15;
    final int INT_TBL_DAT_STK_SIS=16;
    final int INT_TBL_DAT_CAN_POR_ING_BOD=17;
    final int INT_TBL_DAT_CAN_POR_EGR_BOD=18;
    final int INT_TBL_DAT_STK_CON=19;
    final int INT_TBL_DAT_DIF_POS=20;
    final int INT_TBL_DAT_DIF_NEG=21;
    final int INT_TBL_DAT_OBS=22;
    final int INT_TBL_DAT_BUT_HIS=23;
   
    private ZafColNumerada objColNum;
    private ZafTblBus objTblBus;
    private ZafDatePicker dtpFecDes,dtpFecHas;

    //Variables generales.
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMená en JTable.
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL, strSQLUpd, strAux, strSQLCon;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                             //true: Continua la ejecucián del hilo.
    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    private ZafDocLis objDocLis;
    private ZafThreadGUI objThrGUI;
    
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    private String strCodBod,  strNomBod;                //Contenido del campo al obtener el foco.
    private ZafVenCon vcoBod;                        //Ventana de consulta "Bodega".
    private String strCodAlt, strNomItm;                //Contenido del campo al obtener el foco.
    private ZafVenCon vcoItm;                           //Ventana de consulta "Item".
    
    
    
    ZafTblCelRenBut objTblCelRenBut;
    ZafTblCelEdiButGen objTblCelEdiButGen;
    
    private ZafSelFec objSelFec, objFecReaCon;


    private ArrayList arlRegNumVec, arlDatNumVec;
    final int INT_ARL_NUM_VEC_COD_EMP=0;
    final int INT_ARL_NUM_VEC_COD_ITM=1;
    final int INT_ARL_NUM_VEC_COD_ITM_MAE=2;
    final int INT_ARL_NUM_VEC_NUM_VEC=3;
    final int INT_ARL_NUM_VEC_EST=4;

    private ZafPerUsr objPerUsr;


    
    /** Crea una nueva instancia de la clase ZafCon06. */
    public ZafCom35(ZafParSis obj){
        try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            arlDatNumVec=new ArrayList();
            objPerUsr=new ZafPerUsr(objParSis);
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

        PanFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        panTodItm = new javax.swing.JPanel();
        optTodItm = new javax.swing.JRadioButton();
        lblBen = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBod = new javax.swing.JButton();
        panFilItm = new javax.swing.JPanel();
        panItmSel = new javax.swing.JPanel();
        optItmSel = new javax.swing.JRadioButton();
        lblItm = new javax.swing.JLabel();
        txtCodAltItm = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        txtCodItm = new javax.swing.JTextField();
        panFilCodAltItm = new javax.swing.JPanel();
        panItmDesHas = new javax.swing.JPanel();
        lblItmDes = new javax.swing.JLabel();
        txtCodAltItmDes = new javax.swing.JTextField();
        lblItmHas = new javax.swing.JLabel();
        txtCodAltItmHas = new javax.swing.JTextField();
        panItmTer = new javax.swing.JPanel();
        lblItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        panFilStk = new javax.swing.JPanel();
        panFilStkDesHas = new javax.swing.JPanel();
        lblItmDes1 = new javax.swing.JLabel();
        txtDifDes = new javax.swing.JTextField();
        lblItmHas1 = new javax.swing.JLabel();
        txtDifHas = new javax.swing.JTextField();
        panFilConStk = new javax.swing.JPanel();
        chkItmStkDif = new javax.swing.JCheckBox();
        chkItmSinCon = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        panFilVar = new javax.swing.JPanel();
        panFecCon = new javax.swing.JPanel();
        panDat = new javax.swing.JPanel();
        chkIncCanIngEgr = new javax.swing.JCheckBox();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        panBotExe = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        lblTit.setPreferredSize(new java.awt.Dimension(39, 11));
        PanFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panFil.setPreferredSize(new java.awt.Dimension(9, 290));
        panFil.setLayout(new java.awt.BorderLayout());

        panTodItm.setPreferredSize(new java.awt.Dimension(0, 34));
        panTodItm.setLayout(null);

        optTodItm.setSelected(true);
        optTodItm.setText("Todos los items");
        optTodItm.setPreferredSize(new java.awt.Dimension(93, 16));
        optTodItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodItmActionPerformed(evt);
            }
        });
        panTodItm.add(optTodItm);
        optTodItm.setBounds(0, 20, 580, 14);

        lblBen.setText("Bodega:");
        lblBen.setToolTipText("Bodega en la que se debe hacer el conteo");
        panTodItm.add(lblBen);
        lblBen.setBounds(6, 0, 60, 20);

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
        panTodItm.add(txtCodBod);
        txtCodBod.setBounds(70, 0, 56, 20);

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
        panTodItm.add(txtNomBod);
        txtNomBod.setBounds(127, 0, 273, 20);

        butBod.setText("...");
        butBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodActionPerformed(evt);
            }
        });
        panTodItm.add(butBod);
        butBod.setBounds(400, 0, 20, 20);

        panFil.add(panTodItm, java.awt.BorderLayout.NORTH);

        panFilItm.setPreferredSize(new java.awt.Dimension(100, 90));
        panFilItm.setRequestFocusEnabled(false);
        panFilItm.setLayout(new java.awt.BorderLayout());

        panItmSel.setPreferredSize(new java.awt.Dimension(100, 36));
        panItmSel.setLayout(null);

        optItmSel.setText("Sólo los items que cumplan el criterio seleccionado");
        optItmSel.setPreferredSize(new java.awt.Dimension(93, 16));
        optItmSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optItmSelActionPerformed(evt);
            }
        });
        panItmSel.add(optItmSel);
        optItmSel.setBounds(0, 0, 580, 16);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Beneficiario");
        panItmSel.add(lblItm);
        lblItm.setBounds(6, 16, 30, 20);

        txtCodAltItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltItmActionPerformed(evt);
            }
        });
        txtCodAltItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmFocusLost(evt);
            }
        });
        panItmSel.add(txtCodAltItm);
        txtCodAltItm.setBounds(70, 16, 56, 20);

        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });
        panItmSel.add(txtNomItm);
        txtNomItm.setBounds(127, 16, 264, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panItmSel.add(butItm);
        butItm.setBounds(391, 16, 20, 20);

        txtCodItm.setEditable(false);
        txtCodItm.setEnabled(false);
        panItmSel.add(txtCodItm);
        txtCodItm.setBounds(49, 16, 20, 20);

        panFilItm.add(panItmSel, java.awt.BorderLayout.NORTH);

        panFilCodAltItm.setLayout(new java.awt.GridLayout(1, 0));

        panItmDesHas.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panItmDesHas.setLayout(null);

        lblItmDes.setText("Desde:");
        panItmDesHas.add(lblItmDes);
        lblItmDes.setBounds(40, 18, 60, 14);

        txtCodAltItmDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusLost(evt);
            }
        });
        panItmDesHas.add(txtCodAltItmDes);
        txtCodAltItmDes.setBounds(86, 14, 100, 20);

        lblItmHas.setText("Hasta:");
        panItmDesHas.add(lblItmHas);
        lblItmHas.setBounds(194, 18, 50, 14);

        txtCodAltItmHas.setVerifyInputWhenFocusTarget(false);
        txtCodAltItmHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusLost(evt);
            }
        });
        panItmDesHas.add(txtCodAltItmHas);
        txtCodAltItmHas.setBounds(240, 14, 100, 20);

        panFilCodAltItm.add(panItmDesHas);

        panItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panItmTer.setLayout(null);

        lblItmTer.setText("Terminan con:");
        panItmTer.add(lblItmTer);
        lblItmTer.setBounds(30, 18, 90, 14);

        txtCodAltItmTer.setVerifyInputWhenFocusTarget(false);
        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(120, 14, 140, 20);

        panFilCodAltItm.add(panItmTer);

        panFilItm.add(panFilCodAltItm, java.awt.BorderLayout.CENTER);

        panFilStk.setPreferredSize(new java.awt.Dimension(100, 48));
        panFilStk.setVerifyInputWhenFocusTarget(false);
        panFilStk.setLayout(new java.awt.BorderLayout());

        panFilStkDesHas.setBorder(javax.swing.BorderFactory.createTitledBorder("Diferencias"));
        panFilStkDesHas.setPreferredSize(new java.awt.Dimension(100, 36));
        panFilStkDesHas.setLayout(null);

        lblItmDes1.setText("Desde:");
        panFilStkDesHas.add(lblItmDes1);
        lblItmDes1.setBounds(40, 14, 60, 14);

        txtDifDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDifDesFocusGained(evt);
            }
        });
        panFilStkDesHas.add(txtDifDes);
        txtDifDes.setBounds(86, 8, 100, 20);

        lblItmHas1.setText("Hasta:");
        panFilStkDesHas.add(lblItmHas1);
        lblItmHas1.setBounds(194, 15, 50, 14);

        txtDifHas.setVerifyInputWhenFocusTarget(false);
        panFilStkDesHas.add(txtDifHas);
        txtDifHas.setBounds(240, 8, 100, 20);

        panFilStk.add(panFilStkDesHas, java.awt.BorderLayout.CENTER);

        panFilConStk.setPreferredSize(new java.awt.Dimension(100, 15));
        panFilConStk.setLayout(null);

        chkItmStkDif.setText("Mostrar sólo los items con diferencias");
        panFilConStk.add(chkItmStkDif);
        chkItmStkDif.setBounds(4, 0, 290, 14);

        chkItmSinCon.setText("Mostrar items que no han sido contados");
        panFilConStk.add(chkItmSinCon);
        chkItmSinCon.setBounds(300, 0, 330, 14);

        panFilStk.add(panFilConStk, java.awt.BorderLayout.SOUTH);

        panFilItm.add(panFilStk, java.awt.BorderLayout.SOUTH);

        panFil.add(panFilItm, java.awt.BorderLayout.CENTER);

        jPanel1.setPreferredSize(new java.awt.Dimension(100, 134));
        jPanel1.setLayout(new java.awt.GridLayout(2, 0));

        panFilVar.setPreferredSize(new java.awt.Dimension(100, 70));
        panFilVar.setLayout(new java.awt.BorderLayout());
        jPanel1.add(panFilVar);

        panFecCon.setLayout(new java.awt.BorderLayout());
        jPanel1.add(panFecCon);

        panFil.add(jPanel1, java.awt.BorderLayout.SOUTH);

        panGen.add(panFil, java.awt.BorderLayout.NORTH);

        panDat.setLayout(new java.awt.BorderLayout());

        chkIncCanIngEgr.setText("Utilizar en el cálculo las cantidades que están por ingresar/egresar");
        chkIncCanIngEgr.setPreferredSize(new java.awt.Dimension(81, 16));
        panDat.add(chkIncCanIngEgr, java.awt.BorderLayout.NORTH);

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

        panDat.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        PanFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(639, 39));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(639, 26));
        panBot.setLayout(new java.awt.BorderLayout());

        panBotExe.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));

        butCon.setMnemonic('C');
        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBotExe.add(butCon);

        butGua.setMnemonic('G');
        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBotExe.add(butGua);

        butCer.setMnemonic('r');
        butCer.setText("Cerrar");
        butCer.setToolTipText("");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBotExe.add(butCer);

        panBot.add(panBotExe, java.awt.BorderLayout.EAST);

        jPanel2.setLayout(new java.awt.BorderLayout());
        panBot.add(jPanel2, java.awt.BorderLayout.CENTER);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 15));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-720)/2, (screenSize.height-470)/2, 720, 470);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
        agregarDocLis();
    }//GEN-LAST:event_formInternalFrameOpened

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

private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
    mostrarVenConItm(0);
    if(txtNomItm.getText().length()>0){
        optItmSel.setSelected(true);
        optTodItm.setSelected(false);
        txtCodAltItmDes.setText("");
        txtCodAltItmHas.setText("");
        txtCodAltItmTer.setText("");
    }
}//GEN-LAST:event_butItmActionPerformed

private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
// TODO add your handling code here:
        if (butCon.getText().equals("Consultar")){
            //objTblTotales.isActivo(false);
            blnCon=true;
            if (objThrGUI==null){
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }            
        }
        else
            blnCon=false;
}//GEN-LAST:event_butConActionPerformed

private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
    if(objTblMod.isDataModelChanged()){
        if(guardarDatos()){
            mostrarMsgInf("<HTML>La información se guardó correctamente.</HTML>");        
        }
        else{
            mostrarMsgInf("<HTML>La información no se pudo guardar.<BR>Verifique y vuelva a intentarlo.</HTML>");
        }        
    }
    else
        mostrarMsgInf("<HTML>No hay información para guardar.<BR>Verifique y vuelva a intentarlo.</HTML>");
}//GEN-LAST:event_butGuaActionPerformed

private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
    exitForm(null);//GEN-LAST:event_butCerActionPerformed
}                                      

private void butBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodActionPerformed
// TODO add your handling code here:
    mostrarVenConBod(0);
    objTblMod.removeAllRows();
}//GEN-LAST:event_butBodActionPerformed

private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
// TODO add your handling code here:
    txtCodBod.transferFocus();
}//GEN-LAST:event_txtCodBodActionPerformed

private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
// TODO add your handling code here:
    strCodBod = txtCodBod.getText();
    txtCodBod.selectAll();

}//GEN-LAST:event_txtCodBodFocusGained

private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
// TODO add your handling code here:
//Validar el contenido de la celda sálo si ha cambiado.
    if (!txtCodBod.getText().equalsIgnoreCase(strCodBod)) {
        if (txtCodBod.getText().equals("")) {
            txtCodBod.setText("");
            txtNomBod.setText("");
        } else {
            mostrarVenConBod(1);
        }
        objTblMod.removeAllRows();
    } else 
        txtCodBod.setText(strCodBod);
}//GEN-LAST:event_txtCodBodFocusLost

private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
// TODO add your handling code here:
    txtNomBod.transferFocus();
}//GEN-LAST:event_txtNomBodActionPerformed

private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
// TODO add your handling code here:
    strNomBod = txtNomBod.getText();
    txtNomBod.selectAll();
}//GEN-LAST:event_txtNomBodFocusGained

private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
// TODO add your handling code here:
//Validar el contenido de la celda sálo si ha cambiado.
    if (!txtNomBod.getText().equalsIgnoreCase(strNomBod)) {
        if (txtNomBod.getText().equals("")) {
            txtCodBod.setText("");
            txtNomBod.setText("");
        } else {
            mostrarVenConBod(2);
        }
        objTblMod.removeAllRows();
    } else 
        txtNomBod.setText(strNomBod);
}//GEN-LAST:event_txtNomBodFocusLost

private void txtCodAltItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltItmActionPerformed
// TODO add your handling code here:
    txtCodAltItm.transferFocus();
}//GEN-LAST:event_txtCodAltItmActionPerformed

private void txtCodAltItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusGained
// TODO add your handling code here:
    strCodAlt=txtCodAltItm.getText();
    txtCodAltItm.selectAll();
}//GEN-LAST:event_txtCodAltItmFocusGained

private void txtCodAltItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusLost
// TODO add your handling code here:
//Validar el contenido de la celda sólo si ha cambiado.
    if (!txtCodAltItm.getText().equalsIgnoreCase(strCodAlt))
    {
        if (txtCodAltItm.getText().equals(""))
        {
            txtCodItm.setText("");
            txtCodAltItm.setText("");
            txtNomItm.setText("");
        }
        else
        {
            mostrarVenConItm(1);
        }
    }
    else
        txtCodAltItm.setText(strCodAlt);
    
    if(txtCodAltItm.getText().length()>0){
        optItmSel.setSelected(true);
        optTodItm.setSelected(false);
        txtCodAltItmDes.setText("");
        txtCodAltItmHas.setText("");
        txtCodAltItmTer.setText("");
    }
}//GEN-LAST:event_txtCodAltItmFocusLost

private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
// TODO add your handling code here:
    txtNomItm.transferFocus();
}//GEN-LAST:event_txtNomItmActionPerformed

private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
// TODO add your handling code here:
    strNomItm=txtNomItm.getText();
    txtNomItm.selectAll();
}//GEN-LAST:event_txtNomItmFocusGained

private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
// TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAltItm.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
            txtNomItm.setText(strNomItm);
        
        if(txtNomItm.getText().length()>0){
            optItmSel.setSelected(true);
            optTodItm.setSelected(false);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }
}//GEN-LAST:event_txtNomItmFocusLost

private void txtCodAltItmDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusGained
// TODO add your handling code here:
    txtCodAltItmDes.selectAll();
}//GEN-LAST:event_txtCodAltItmDesFocusGained

private void txtCodAltItmDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusLost
// TODO add your handling code here:
    if (txtCodAltItmDes.getText().length()>0)
    {
        optItmSel.setSelected(true);
        optTodItm.setSelected(false);
        
        txtCodItm.setText("");
        txtCodAltItm.setText("");
        txtNomItm.setText("");
        txtCodAltItmTer.setText("");
        
        if (txtCodAltItmHas.getText().length()==0)
            txtCodAltItmHas.setText(txtCodAltItmDes.getText());
    }
}//GEN-LAST:event_txtCodAltItmDesFocusLost

private void txtCodAltItmHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmHasFocusGained
// TODO add your handling code here:
    txtCodAltItmHas.selectAll();
}//GEN-LAST:event_txtCodAltItmHasFocusGained

private void txtCodAltItmHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmHasFocusLost
// TODO add your handling code here:
    if (txtCodAltItmHas.getText().length()>0){
        optItmSel.setSelected(true);
        optTodItm.setSelected(false);
    }
}//GEN-LAST:event_txtCodAltItmHasFocusLost

private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
// TODO add your handling code here:
    txtCodAltItmTer.selectAll();
}//GEN-LAST:event_txtCodAltItmTerFocusGained

private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
// TODO add your handling code here:
    if (txtCodAltItmTer.getText().length()>0){
        optItmSel.setSelected(true);
        optTodItm.setSelected(false);
        txtCodAltItmHas.setText("");
        txtCodAltItmDes.setText("");
        txtCodItm.setText("");
        txtCodAltItm.setText("");
        txtNomItm.setText("");
    }
}//GEN-LAST:event_txtCodAltItmTerFocusLost

private void optTodItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodItmActionPerformed
// TODO add your handling code here:
    if(optTodItm.isSelected())
        optItmSel.setSelected(false);
}//GEN-LAST:event_optTodItmActionPerformed

private void optItmSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optItmSelActionPerformed
// TODO add your handling code here:
    if(optItmSel.isSelected())
        optTodItm.setSelected(false);
}//GEN-LAST:event_optItmSelActionPerformed

private void txtDifDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDifDesFocusGained
// TODO add your handling code here:
    if(txtDifDes.getText().length()>0){
        optItmSel.setSelected(true);
        optTodItm.setSelected(false);
    }
}//GEN-LAST:event_txtDifDesFocusGained

/** Cerrar la aplicacián. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butBod;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butItm;
    private javax.swing.JCheckBox chkIncCanIngEgr;
    private javax.swing.JCheckBox chkItmSinCon;
    private javax.swing.JCheckBox chkItmStkDif;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblBen;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblItmDes;
    private javax.swing.JLabel lblItmDes1;
    private javax.swing.JLabel lblItmHas;
    private javax.swing.JLabel lblItmHas1;
    private javax.swing.JLabel lblItmTer;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optItmSel;
    private javax.swing.JRadioButton optTodItm;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panBotExe;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panFecCon;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilCodAltItm;
    private javax.swing.JPanel panFilConStk;
    private javax.swing.JPanel panFilItm;
    private javax.swing.JPanel panFilStk;
    private javax.swing.JPanel panFilStkDesHas;
    private javax.swing.JPanel panFilVar;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panItmDesHas;
    private javax.swing.JPanel panItmSel;
    private javax.swing.JPanel panItmTer;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panTodItm;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAltItm;
    private javax.swing.JTextField txtCodAltItmDes;
    private javax.swing.JTextField txtCodAltItmHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtDifDes;
    private javax.swing.JTextField txtDifHas;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNomItm;
    // End of variables declaration//GEN-END:variables


    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try{
            objUti=new ZafUtil();
            String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
            //Inicializar objetos.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.7.3");
            lblTit.setText(strAux);
            txtCodBod.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBod.setBackground(objParSis.getColorCamposObligatorios());

            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(false);
            objSelFec.setTitulo("Fecha de realización de la orden de conteo");
            panFilVar.add(objSelFec);
            objSelFec.setBounds(0, 66, 472, 72);


            //Configurar ZafSelFec:
            objFecReaCon=new ZafSelFec();
            objFecReaCon.setCheckBoxVisible(true);
            objFecReaCon.setCheckBoxChecked(false);
            objFecReaCon.setTitulo("Fecha de realización del conteo");
            panFecCon.add(objFecReaCon);
            objFecReaCon.setBounds(0, 66, 472, 72);


            //Configurar los JTables.
            configurarTblDat();

            if(objParSis.getCodigoMenu()==1958){
                if(objPerUsr.isOpcionEnabled(1959))
                    butCon.setEnabled(true);
                else
                    butCon.setEnabled(false);
                if(objPerUsr.isOpcionEnabled(1960))
                    butGua.setEnabled(true);
                else
                    butGua.setEnabled(false);
                if(objPerUsr.isOpcionEnabled(1961))
                    butCer.setEnabled(true);
                else
                    butCer.setEnabled(false);
            }

            txtCodItm.setVisible(false);


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
            vecCab=new Vector(24);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"COD.EMP.");
            vecCab.add(INT_TBL_DAT_COD_REG,"COD.REG.");
            vecCab.add(INT_TBL_DAT_COD_ITM,"COD.ITM.");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE,"COD.ITM.MAE.");
            vecCab.add(INT_TBL_DAT_COD_NUM_VEC_CON_ITM,"# CON.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM,"COD.ALT.ITM.");
            
            vecCab.add(INT_TBL_DAT_NOM_ITM,"NOM.ITM");
            vecCab.add(INT_TBL_DAT_DES_COR_UNI_MED,"ALI.UNI.MED.");
            vecCab.add(INT_TBL_DAT_DES_LAR_UNI_MED,"UNI.MED.");
            vecCab.add(INT_TBL_DAT_COD_USU_RES_CON,"COD.USU.RES.");
            vecCab.add(INT_TBL_DAT_ALI_USU_RES_CON,"ALI.USU.RES.");
            vecCab.add(INT_TBL_DAT_NOM_USU_RES_CON,"NOM.USU.RES.");
            vecCab.add(INT_TBL_DAT_FEC_HOR_SOL_CON,"FEC.SOL.CON.");
            vecCab.add(INT_TBL_DAT_FEC_HOR_REA_CON,"FEC.REA.CON.");
            vecCab.add(INT_TBL_DAT_STK_ACT,"STK.ACT.");
            vecCab.add(INT_TBL_DAT_STK_SIS,"STK.SIS.COR.");
            vecCab.add(INT_TBL_DAT_CAN_POR_ING_BOD,"CAN.ING.BOD.");
            vecCab.add(INT_TBL_DAT_CAN_POR_EGR_BOD,"CAN.EGR.BOD.");
            vecCab.add(INT_TBL_DAT_STK_CON,"STK.CON.");
            vecCab.add(INT_TBL_DAT_DIF_POS,"DIF.POS.");
            vecCab.add(INT_TBL_DAT_DIF_NEG,"DIF.NEG.");
            vecCab.add(INT_TBL_DAT_OBS,"OBS.");
            vecCab.add(INT_TBL_DAT_BUT_HIS,"...");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
                        
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            
            
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_NUM_VEC_CON_ITM).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(70);
            
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_UNI_MED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_USU_RES_CON).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_ALI_USU_RES_CON).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NOM_USU_RES_CON).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FEC_HOR_SOL_CON).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FEC_HOR_REA_CON).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_STK_SIS).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAN_POR_ING_BOD).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_POR_EGR_BOD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DIF_POS).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DIF_NEG).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_OBS).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS).setPreferredWidth(20);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_USU_RES_CON).setResizable(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DES_COR_UNI_MED, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_USU_RES_CON, tblDat);
            if(  (objParSis.getCodigoUsuario()==1) || (objParSis.getCodigoUsuario()==21) ){
            }
            else
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_STK_ACT, tblDat);


            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ALI_USU_RES_CON, tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Editor de básqueda.
            objTblBus=new ZafTblBus(tblDat);
            objTblOrd=new ZafTblOrd(tblDat);
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_SIS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_POR_ING_BOD).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_POR_EGR_BOD).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DIF_POS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DIF_NEG).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_OBS);
            vecAux.add("" + INT_TBL_DAT_BUT_HIS);
            objTblMod.setColumnasEditables(vecAux);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            
            configurarVenConBod();
            configurarVenConItm();
            
            
            
            //PARA EL BOTON DE ANEXO DOS, QUE LLAMA A UNA VENTANA DE ESTADáSTICA DE CLIENTES
            objTblCelRenBut=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_HIS).setCellRenderer(objTblCelRenBut);
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_HIS).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    //SE DEBE VALIDAR QUE EXISTAN DATOS EN ESA FILA
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    cargarHistorico(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            
            
            
            
            
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
            
    private class ZafTblColModLis implements javax.swing.event.TableColumnModelListener{
        public void columnAdded(javax.swing.event.TableColumnModelEvent e){
        }
        
        public void columnMarginChanged(javax.swing.event.ChangeEvent e){
            int intColSel, intAncCol;
            //PARA CUENTAS
            if (tblDat.getTableHeader().getResizingColumn()!=null){
                intColSel=tblDat.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0){
                    intAncCol=tblDat.getColumnModel().getColumn(intColSel).getPreferredWidth();
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
    
    /**
     * Esta funcián determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        //Validar el "Tipo de documento".
        if (txtCodBod.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega</FONT> es obligatorio.<BR>Escriba o seleccione una bodega y vuelva a intentarlo.</HTML>");
            txtNomBod.requestFocus();
            return false;
        }              
        return true;
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
     * Esta funcián permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningán problema.
     * <BR>false: En el caso contrario.
     */
    private void limpiarFrm(){
        txtCodBod.setText("");
        txtNomBod.setText("");
        optTodItm.setSelected(true);
        optItmSel.setSelected(false);
        txtCodItm.setText("");
        txtCodAltItm.setText("");
        txtNomItm.setText("");
        txtCodAltItmDes.setText("");
        txtCodAltItmHas.setText("");
        txtCodAltItmTer.setText("");
        txtDifDes.setText("");
        txtDifHas.setText("");
        chkItmStkDif.setSelected(true);
        objTblMod.removeAllRows();
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
        public void changedUpdate(javax.swing.event.DocumentEvent evt) 
        {
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
        txtCodBod.getDocument().addDocumentListener(objDocLis);
        txtNomBod.getDocument().addDocumentListener(objDocLis);
        txtCodItm.getDocument().addDocumentListener(objDocLis);
        txtCodAltItm.getDocument().addDocumentListener(objDocLis);
        txtNomItm.getDocument().addDocumentListener(objDocLis);
        txtCodAltItmDes.getDocument().addDocumentListener(objDocLis);
        txtCodAltItmHas.getDocument().addDocumentListener(objDocLis);
        txtCodAltItmTer.getDocument().addDocumentListener(objDocLis);
        txtDifDes.getDocument().addDocumentListener(objDocLis);
        txtDifHas.getDocument().addDocumentListener(objDocLis);
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
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de empresa";
                    break;
                case INT_TBL_DAT_COD_REG:
                    strMsg="Código de registro";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código de item";
                    break;
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg="Código de item maestro";
                    break;
                case INT_TBL_DAT_COD_NUM_VEC_CON_ITM:
                    strMsg="Número de veces que se ha realizado conteo al item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg="Código alterno de item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre de item";
                    break;
                case INT_TBL_DAT_DES_COR_UNI_MED:
                    strMsg="Alias de unidad de medida";
                    break;
                case INT_TBL_DAT_DES_LAR_UNI_MED:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_COD_USU_RES_CON:
                    strMsg="Código de usuario responsable de conteo";
                    break;
                case INT_TBL_DAT_ALI_USU_RES_CON:
                    strMsg="Alias de usuario responsable de conteo";
                    break;
                case INT_TBL_DAT_NOM_USU_RES_CON:
                    strMsg="Nombre de usuario responsable de conteo";
                    break;
                case INT_TBL_DAT_FEC_HOR_SOL_CON:
                    strMsg="Fecha y hora de solicitud de conteo";
                    break;
                case INT_TBL_DAT_FEC_HOR_REA_CON:
                    strMsg="Fecha y hora de realización de conteo";
                    break;
                case INT_TBL_DAT_STK_ACT:
                    strMsg="Stock de item en el sistema al momento de hacer la consulta";
                    break;
                case INT_TBL_DAT_STK_SIS:
                    strMsg="Stock de item en el sistema al momento de hacer el conteo";
                    break;
                case INT_TBL_DAT_CAN_POR_ING_BOD:
                    strMsg="Cantidad por ingresar a bodega";
                    break;
                case INT_TBL_DAT_CAN_POR_EGR_BOD:
                    strMsg="Cantidad por egresar a bodega";
                    break;
                case INT_TBL_DAT_STK_CON:
                    strMsg="Stock ingresado por conteo";
                    break;
                case INT_TBL_DAT_DIF_POS:
                    strMsg="Diferencia positiva";
                    break;
                case INT_TBL_DAT_DIF_NEG:
                    strMsg="Diferencia negativa";
                    break;
                case INT_TBL_DAT_OBS:
                    strMsg="Observación";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
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
   private class ZafThreadGUI extends Thread{
        public void run(){
            if (!cargarReg()){
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sálo cuando haya datos.
            if (tblDat.getRowCount()>0){
                tabFrm.setSelectedIndex(0);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
   
    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            objTblMod.removeAllRows();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if(isCamVal()){
                    if(cargar_tbmConInv()){
                        if(obtenerNumeroVecesConteoItem()){
                            if(cargarNumeroVecesConteoItem()){

                            }
                        }
                    }
                    con.close();
                    con=null;
                }
            }            
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
   
 
   private boolean cargar_tbmConInv(){
        int intNumTotReg, i;
        boolean blnRes=true;
        BigDecimal bdeStk=new BigDecimal(0);
        String strAuxTmp="";
        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            if (con!=null){
                strAux="";
                if(!  txtCodBod.getText().toString().equals(""))
                    strAux+="   AND co_bod=" + txtCodBod.getText()  + "";
                
                if(!  txtCodItm.getText().toString().equals(""))
                    strAux+="   AND a1.co_itm=" + txtCodItm.getText()  + "";
                
                if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
                    strAuxTmp+=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

                if (txtCodAltItmTer.getText().length()>0){
                    strAuxTmp+=getConSQLAdiCamTer("a1.tx_codAlt", txtCodAltItmTer.getText());
                }
                
                if (txtDifDes.getText().length()>0 || txtDifHas.getText().length()>0)
                    strAux+=" AND (ABS((a1.nd_stkSis-a1.nd_canIngBod+a1.nd_canEgrBod) - a1.nd_stkCon)) BETWEEN " + txtDifDes.getText().replaceAll("'", "''").toLowerCase() + " AND " + txtDifHas.getText().replaceAll("'", "''").toLowerCase() + "";
                
                if(chkItmStkDif.isSelected())
                    strAux+="   AND ((a1.nd_stkSis-a1.nd_canIngBod+a1.nd_canEgrBod) - a1.nd_stkCon) <> 0";

                if(objSelFec.isCheckBoxChecked()){
                    switch (objSelFec.getTipoSeleccion()){
                        case 0: //Búsqueda por rangos
                            strAux+=" AND CAST(a1.fe_solcon AS date) BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAux+=" AND CAST(a1.fe_solcon AS date)<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAux+=" AND CAST(a1.fe_solcon AS date)>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }


                if(objFecReaCon.isCheckBoxChecked()){
                    switch (objFecReaCon.getTipoSeleccion()){
                        case 0: //Búsqueda por rangos
                            strAux+=" AND CAST(a1.fe_reaCon AS date) BETWEEN '" + objUti.formatearFecha(objFecReaCon.getFechaDesde(), objFecReaCon.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objFecReaCon.getFechaHasta(), objFecReaCon.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAux+=" AND CAST(a1.fe_reaCon AS date)<='" + objUti.formatearFecha(objFecReaCon.getFechaHasta(), objFecReaCon.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAux+=" AND CAST(a1.fe_reaCon AS date)>='" + objUti.formatearFecha(objFecReaCon.getFechaDesde(), objFecReaCon.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }


                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a.co_emp, a.co_itm, a.tx_codAlt, a.tx_nomItm, a.co_itmMae, a.tx_desCor,";
                strSQL+=" a.tx_desLar, b.co_reg, b.co_usrResCon, b.tx_usr, b.tx_nom";
                strSQL+=" ,b.fe_solCon, b.fe_reaCon, b.nd_stkSis, b.nd_canIngBod,";
                strSQL+="  b.nd_canEgrBod, b.nd_stkCon, b.tx_obs1";
                if(chkIncCanIngEgr.isSelected())
                    strSQL+="  , ((b.nd_stkSis-b.nd_canIngBod+b.nd_canEgrBod) - b.nd_stkCon) AS nd_dif";
                else
                    strSQL+="  , (b.nd_stkSis - b.nd_stkCon) AS nd_dif";
                strSQL+=", a.nd_stkAct";
                strSQL+="  FROM(";





                strSQL+="     SELECT c1.*, c2.nd_stkAct FROM(";
                strSQL+="          SELECT DISTINCT(a1.co_emp) AS co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm,";
                strSQL+="            c1.co_itmMae, b1.tx_desCor, b1.tx_desLar";
                strSQL+="          FROM ((tbm_inv AS a1 INNER JOIN tbm_var AS b1 ON a1.co_uni=b1.co_reg)";
                strSQL+="           INNER JOIN tbm_equInv AS c1 ON a1.co_emp=c1.co_emp AND a1.co_itm=c1.co_itm)";
                strSQL+="                 INNER JOIN tbm_invBod AS a2";
                strSQL+="          ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                strSQL+="          WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=strAuxTmp;



                ////////////////////////////////////////////
                
                strSQL+=" ) AS c1";
                strSQL+=" INNER JOIN(";
                strSQL+="             SELECT a2.co_itmMae, SUM(a1.nd_stkAct) AS nd_stkAct, SUM(a1.nd_canIngBod) AS nd_canIngBod, SUM(a1.nd_canEgrBod) AS nd_canEgrBod";
                strSQL+="             FROM tbm_invBod AS a1";
                strSQL+="             INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                strSQL+="             INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)";
                strSQL+="             WHERE a3.co_empGrp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="             AND a3.co_bodGrp=" + txtCodBod.getText() + "";
                strSQL+="             GROUP BY a2.co_itmMae";
                strSQL+="     ) as c2";
                strSQL+="     ON c1.co_itmMae=c2.co_itmMae";
                strSQL+="       ) AS a";
                if(chkItmSinCon.isSelected())
                    strSQL+="   LEFT OUTER JOIN";
                else
                    strSQL+="   INNER JOIN";

                strSQL+="         (";



                strSQL+="        SELECT x.*, y.co_usrResCon, y.tx_usr, y.tx_nom, y.fe_reaCon, y.fe_solCon, y.nd_stkSis,";
                strSQL+="                  y.nd_canIngBod, y.nd_canEgrBod, y.nd_stkCon, y.tx_obs1 FROM(";
                strSQL+="                 SELECT co_emp, co_itm, MAX(co_reg) AS co_reg FROM tbm_conInv";
                strSQL+="                  WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND st_conRea NOT IN('I', 'E') AND co_bod=" + txtCodBod.getText() + "";
                strSQL+="                 GROUP BY co_emp, co_itm ORDER BY co_itm) AS x";
                strSQL+="                 INNER JOIN";
                strSQL+="                 (SELECT DISTINCT(co_emp) AS co_emp, a1.co_itm,";
                strSQL+="                    a1.co_reg, a1.co_usrResCon, z1.tx_usr, z1.tx_nom";
                strSQL+="                  , a1.fe_solCon, a1.fe_reaCon, a1.nd_stkSis, a1.nd_canIngBod, a1.nd_canEgrBod, a1.nd_stkCon, a1.tx_obs1";
                strSQL+="                 FROM tbm_conInv AS a1 INNER JOIN tbm_usr AS z1 ON a1.co_usrResCon=z1.co_usr";
                strSQL+="                  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                  AND st_conRea='S'";
                strSQL+=strAux;
                strSQL+="                 ORDER BY co_itm) AS y";
                strSQL+="                 ON x.co_emp=y.co_emp AND x.co_itm=y.co_itm AND x.co_reg=y.co_reg";
                strSQL+="         ORDER BY x.co_itm";






                strSQL+="         ) AS b";
                strSQL+="   ON a.co_emp=b.co_emp AND a.co_itm=b.co_itm";
                strSQL+=" WHERE a.tx_codAlt NOT LIKE '%L'";
                strSQL+=" ORDER BY b.fe_reaCon, a.tx_codAlt";


                System.out.println("SQL cargar_tbmConInv: " + strSQL);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                //pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;                
                while (rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,                 "");
                        vecReg.add(INT_TBL_DAT_COD_EMP,             "" + rst.getObject("co_emp")==null?"":rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_REG,             "" + rst.getObject("co_reg")==null?"":rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_COD_ITM,             "" + rst.getObject("co_itm")==null?"":rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,         "" + rst.getObject("co_itmMae")==null?"":rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_NUM_VEC_CON_ITM, "");
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,         "" + rst.getObject("tx_codAlt")==null?"":rst.getString("tx_codAlt"));
                        
                        vecReg.add(INT_TBL_DAT_NOM_ITM,             "" + rst.getObject("tx_nomItm")==null?"":rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_DES_COR_UNI_MED,     "" + rst.getObject("tx_desCor")==null?"":rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DES_LAR_UNI_MED,     "" + rst.getObject("tx_desLar")==null?"":rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_COD_USU_RES_CON,     "" + rst.getObject("co_usrResCon")==null?"":rst.getString("co_usrResCon"));
                        vecReg.add(INT_TBL_DAT_ALI_USU_RES_CON,     "" + rst.getObject("tx_usr")==null?"":rst.getString("tx_usr"));
                        vecReg.add(INT_TBL_DAT_NOM_USU_RES_CON,     "" + rst.getObject("tx_nom")==null?"":rst.getString("tx_nom"));
                        vecReg.add(INT_TBL_DAT_FEC_HOR_SOL_CON,     "" + rst.getObject("fe_solCon")==null?"":rst.getString("fe_solCon"));
                        vecReg.add(INT_TBL_DAT_FEC_HOR_REA_CON,     "" + rst.getObject("fe_reaCon")==null?"":rst.getString("fe_reaCon"));
                        vecReg.add(INT_TBL_DAT_STK_ACT,             "" + rst.getObject("nd_stkAct")==null?"":rst.getString("nd_stkAct"));
                        vecReg.add(INT_TBL_DAT_STK_SIS,             "" + rst.getObject("nd_stkSis")==null?"":rst.getString("nd_stkSis"));
                        vecReg.add(INT_TBL_DAT_CAN_POR_ING_BOD,     "" + rst.getObject("nd_canIngBod")==null?"":rst.getString("nd_canIngBod"));
                        vecReg.add(INT_TBL_DAT_CAN_POR_EGR_BOD,     "" + rst.getObject("nd_canEgrBod")==null?"":rst.getString("nd_canEgrBod"));
                        vecReg.add(INT_TBL_DAT_STK_CON,             "" + rst.getObject("nd_stkCon")==null?"":rst.getString("nd_stkCon"));
                        vecReg.add(INT_TBL_DAT_DIF_POS,             "");
                        vecReg.add(INT_TBL_DAT_DIF_NEG,             "");
                        vecReg.add(INT_TBL_DAT_OBS,                 "" + rst.getObject("tx_obs1")==null?"":rst.getString("tx_obs1"));
                        vecReg.add(INT_TBL_DAT_BUT_HIS,             "");
                        
                        bdeStk=rst.getObject("nd_dif")==null?new BigDecimal("0"):rst.getBigDecimal("nd_dif");
                        if(bdeStk.compareTo(new BigDecimal(0))>0){
                            bdeStk=bdeStk.abs().multiply(new BigDecimal(-1));
                            vecReg.setElementAt("" + bdeStk, INT_TBL_DAT_DIF_NEG);
                        }
                        else if(bdeStk.compareTo(new BigDecimal(0))<0){
                            bdeStk=bdeStk.abs();
                            vecReg.setElementAt("" + bdeStk, INT_TBL_DAT_DIF_POS);
                        }
                        vecDat.add(vecReg);
                        i++;
                        pgrSis.setValue(i);
                    }
                    else{
                        break;
                    }
                }
                
                rst.close();
                stm.close();
                rst=null;
                stm=null;

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);    
                vecDat.clear();
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
                objTblMod.initRowsState();

                if( ! chkIncCanIngEgr.isSelected()){
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_POR_EGR_BOD, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_POR_ING_BOD, tblDat);
                }
                else{
                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CAN_POR_EGR_BOD, tblDat);
                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CAN_POR_ING_BOD, tblDat);
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
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Responsables de Conteo".
     */
    private boolean configurarVenConBod() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_bod");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código Bodega");
            arlAli.add("Nombre de Bodega");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("334");
            //Armar la sentencia SQL.
//            strSQL = "";
//            strSQL += " SELECT a1.co_bod, a1.tx_nom";
//            strSQL += " FROM tbm_bod AS a1";
//            strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//            strSQL += " ORDER BY a1.co_bod, a1.tx_nom";


            if(objParSis.getCodigoUsuario()==1){
                strSQL = "";
                strSQL += " SELECT a1.co_bod, a1.tx_nom";
                strSQL += " FROM tbm_bod AS a1";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " ORDER BY a1.co_bod, a1.tx_nom";
            }
            else{
                strSQL = "";
                strSQL += " SELECT a1.co_bod, a1.tx_nom";
                strSQL += " FROM tbm_bod AS a1 INNER JOIN tbr_bodLocPrgUsr AS a2";
                strSQL += " ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                strSQL += " AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL += " ORDER BY a1.co_bod, a1.tx_nom";
            }




            vcoBod = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodegas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoBod.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
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
    private boolean mostrarVenConBod(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.

                    vcoBod.setCampoBusqueda(2);
                    vcoBod.show();
                    if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".

                    if (vcoBod.buscar("a1.co_bod", txtCodBod.getText())) {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    } else {
                        vcoBod.setCampoBusqueda(0);
                        vcoBod.setCriterio1(11);
                        vcoBod.cargarDatos();
                        vcoBod.show();
                        if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                        } else {
                            txtCodBod.setText(strCodBod);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".

                    if (vcoBod.buscar("a1.tx_nom", txtNomBod.getText())) {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    } else {
                        vcoBod.setCampoBusqueda(1);
                        vcoBod.setCriterio1(11);
                        vcoBod.cargarDatos();
                        vcoBod.show();
                        if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                        } else {
                            txtNomBod.setText(strNomBod);
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_itm");
            arlCam.add("a1.tx_codAlt");
            arlCam.add("a1.tx_nomItm");
            arlCam.add("a2.tx_desCor");
            arlCam.add("a2.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Alterno");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("350");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor, a2.tx_desLar";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" ORDER BY a1.tx_codAlt";
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
      
    
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConItm(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.show();
                    if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAltItm.getText())){
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    else{
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.show();
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAltItm.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
                        }
                        else{
                            txtCodAltItm.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText())){
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    else{
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.show();
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAltItm.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
                        }
                        else{
                            txtNomItm.setText(strNomItm);
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
     * Esta función obtiene la condición SQL adicional para los campos que "Terminan con".
     * La cadena recibida es separada para formar la condición que se agregará la sentencia SQL.
     * Por ejemplo: 
     * Si strCam="a2.tx_codAlt" y strCad="I, S, L" el resultado sería "AND (a2.tx_codalt LIKE '%I' OR a2.tx_codalt LIKE '%S' OR a2.tx_codalt LIKE '%L')"
     * @param strCam El campo que se utilizará para la condición.
     * @param strCad La cadena que se separará para formar la condición.
     * @return La cadena que contiene la condición SQL .
     */
    private String getConSQLAdiCamTer(String strCam, String strCad)
    {
        byte i;
        String strRes="";
        try
        {
            if (strCad.length()>0)
            {
                java.util.StringTokenizer stkAux=new java.util.StringTokenizer(strCad, ",", false);
                i=0;
                while (stkAux.hasMoreTokens())
                {
                    if (i==0)
                        strRes+=" AND (LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    else
                        strRes+=" OR LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    i++;
                }
                strRes+=")";
            }
        }
        catch (java.util.NoSuchElementException e)
        {
            strRes="";
        }
        return strRes;
    }
    
    
   private boolean guardarDatos(){
       boolean blnRes=true;
       try{
           con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           con.setAutoCommit(false);
           if(con!=null){
               if(guardarObservacion_tbmConInv()){
                   objTblMod.initRowsState();
                   con.commit();
               }
               else{
                   con.rollback();
                   blnRes=false;
               }
               con.close();
               con=null;
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
   
    
    
    private boolean guardarObservacion_tbmConInv(){
        boolean blnRes=true;
        strSQLUpd="";
        String strLin="";
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int i=0;i<objTblMod.getRowCountTrue(); i++){
                    strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                    if(strLin.equals("M")){
                        strSQL="";
                        strSQL+="UPDATE tbm_conInv";
                        strSQL+=" SET tx_obs1=" + objUti.codificar( objTblMod.getValueAt(i, INT_TBL_DAT_OBS) ) + "";
                        strSQL+=" WHERE co_emp=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP) + "";
                        strSQL+=" AND co_reg=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG) + ";";
                        strSQLUpd+=strSQL;
                    }
                }
                System.out.println("guardarObservacion_tbmConInv: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
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
    
    private void cargarHistorico(int fila){
        int intFilSel=fila;
        String strCodItm=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ITM).toString();
        Compras.ZafCom36.ZafCom36 objCom36=new Compras.ZafCom36.ZafCom36(objParSis, txtCodBod.getText(), strCodItm);
        this.getParent().add(objCom36,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objCom36.show();
    }


    private String obtieneCodigoItemsMaestro(){
        String strCodItmMae="";
        String strFecReaCon="";
        int k=0;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                strFecReaCon=objTblMod.getValueAt(i, INT_TBL_DAT_FEC_HOR_REA_CON)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_FEC_HOR_REA_CON).toString();
                if( ! strFecReaCon.equals("")){
                    if(k==0){
                        strCodItmMae=objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString();
                        k++;
                    }
                    else{
                        strCodItmMae+=", ";
                        strCodItmMae+=objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString();
                    }
                    
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strCodItmMae;
    }


    private boolean obtenerNumeroVecesConteoItem(){
        boolean blnRes=true;
        String strCodItmMaeNumVec=obtieneCodigoItemsMaestro();
        arlDatNumVec.clear();
        try{
            if(con!=null){
                if( ! strCodItmMaeNumVec.equals("")){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_itm, a2.co_itmMae, COUNT(*) AS ne_numVecCon";
                    strSQL+="  FROM tbm_conInv AS a1 INNER JOIN tbm_equInv AS a2";
                    strSQL+="  ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strSQL+="  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="  AND a1.co_bod=" + txtCodBod.getText() + "";
                    strSQL+="  AND a1.st_conRea='S'";
                    strSQL+=" AND co_itmMae IN(" + strCodItmMaeNumVec + ")";
                    strSQL+=" GROUP BY a1.co_emp, a1.co_itm, a2.co_itmMae";
                    strSQL+=" ORDER BY ne_numVecCon";
                    System.out.println("obtenerNumeroVecesConteoItem: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegNumVec=new ArrayList();
                        arlRegNumVec.add(INT_ARL_NUM_VEC_COD_EMP,       "" + rst.getString("co_emp"));
                        arlRegNumVec.add(INT_ARL_NUM_VEC_COD_ITM,       "" + rst.getString("co_itm"));
                        arlRegNumVec.add(INT_ARL_NUM_VEC_COD_ITM_MAE,   "" + rst.getString("co_itmMae"));
                        arlRegNumVec.add(INT_ARL_NUM_VEC_NUM_VEC,       "" + rst.getString("ne_numVecCon"));
                        arlRegNumVec.add(INT_ARL_NUM_VEC_EST,           "");
                        arlDatNumVec.add(arlRegNumVec);
                    }
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


    private boolean cargarNumeroVecesConteoItem(){
        boolean blnRes=true;
        String strTblCodEmpNumVec="", strArlCodEmpNumVec="";
        String strTblCodItmNumVec="", strArlCodItmNumVec="";
        String strTblCodItmMaeNumVec="", strArlCodItmMaeNumVec="";
        String strArlEstMaeNumVec="";
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                strTblCodEmpNumVec=objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP).toString();
                strTblCodItmNumVec=objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString();
                strTblCodItmMaeNumVec=objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString();

                for(int j=0;j<arlDatNumVec.size(); j++){
                    strArlCodEmpNumVec=objUti.getObjectValueAt(arlDatNumVec, j, INT_ARL_NUM_VEC_COD_EMP)==null?"":objUti.getStringValueAt(arlDatNumVec, j, INT_ARL_NUM_VEC_COD_EMP);
                    strArlCodItmNumVec=objUti.getObjectValueAt(arlDatNumVec, j, INT_ARL_NUM_VEC_COD_ITM)==null?"":objUti.getStringValueAt(arlDatNumVec, j, INT_ARL_NUM_VEC_COD_ITM);
                    strArlCodItmMaeNumVec=objUti.getObjectValueAt(arlDatNumVec, j, INT_ARL_NUM_VEC_COD_ITM_MAE)==null?"":objUti.getStringValueAt(arlDatNumVec, j, INT_ARL_NUM_VEC_COD_ITM_MAE);
                    strArlEstMaeNumVec=objUti.getObjectValueAt(arlDatNumVec, j, INT_ARL_NUM_VEC_EST)==null?"":objUti.getStringValueAt(arlDatNumVec, j, INT_ARL_NUM_VEC_EST);

                    if(strArlEstMaeNumVec.equals("")){
                        if(strTblCodEmpNumVec.equals(strArlCodEmpNumVec)){
                            if(strTblCodItmNumVec.equals(strArlCodItmNumVec)){
                                if(strTblCodItmMaeNumVec.equals(strArlCodItmMaeNumVec)){
                                    objTblMod.setValueAt(objUti.getStringValueAt(arlDatNumVec, j, INT_ARL_NUM_VEC_NUM_VEC), i, INT_TBL_DAT_COD_NUM_VEC_CON_ITM);
                                    objUti.setStringValueAt(arlDatNumVec, j, INT_ARL_NUM_VEC_EST, "S");
                                    break;
                                }
                            }
                        }

                    }
                }
            }


        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


   
}